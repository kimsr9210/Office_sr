package kr.or.houroffice.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.houroffice.board.model.service.PartBoardService;
import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.PartBoard;
import kr.or.houroffice.board.model.vo.PartComments;
import kr.or.houroffice.common.Page;
import kr.or.houroffice.member.model.service.AdminMemberService;
import kr.or.houroffice.member.model.vo.Department;
import kr.or.houroffice.member.model.vo.Member;

@Controller
public class PartBoardController {
	
	@Autowired
	ServletContext context;
	
	@Resource(name="adminMemberService")
	private AdminMemberService mService;
	
	@Resource(name="partBService")
	private PartBoardService bService;
	
	private Page page;
	
	// 부서별 게시판 all select
	@RequestMapping(value="/allPartBoardPage.ho")
	public String postListPage(Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m!=null){
			if(m.getDeptCode()!=null){
				
				Page page = createPage(request,10,10); // (request , 한 페이지당 게시물수 , 한 페이지당 보여줄 네비 수)
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("deptCode", m.getDeptCode());
				map.put("page", page);
				// 리스트
				List<Object> list = bService.selectBoardList(map);
				// 페이지네비
				Page pageNavi = bService.getPageNavi(map);
				
				model.addAttribute("list",list);
				model.addAttribute("pageNavi",pageNavi);
				
				return "part_board/allPartBoardPage";
			} // 부서가 없는 사람
			Calendar cal = Calendar.getInstance();
			int todayMon = cal.get(Calendar.MONTH) + 1;
			
			model.addAttribute("msg","부서가 없는 사람은 부서별 게시판을 이용할 수 없습니다.");
			model.addAttribute("location","/main.ho?todayMon="+todayMon);
				
			return "result";
			
			
		} // 로그인 안 한 사람
		return "redirect:login.jsp";
		
	}
	// 부서별 게시판 all select - 검색 select
	@RequestMapping(value="/searchPartBoard.ho")
	public String searchBoard(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword, 
							@RequestParam("deptCode") String pageDeptCode, Model model, HttpServletRequest request, HttpServletResponse response, @SessionAttribute("member") Member m)
	{
		response.setHeader("Content-Type", "text/html;charset=utf-8");
		if(m!=null && pageDeptCode.equals(m.getDeptCode())){
			Page page = createPage(request,10,10); // (request , 한 페이지당 게시물수 , 한 페이지당 보여줄 네비 수)
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deptCode", m.getDeptCode());
			map.put("page", page);
			if(!searchType.equals("both")){
				map.put("searchType", "part_"+searchType); // 쿼리문 데이터
			}else{
				map.put("searchType", searchType); // 쿼리문 데이터
			}
			map.put("type", "PART_");
			map.put("keyword", "%"+keyword+"%"); // 쿼리문 데이터
			map.put("searchTypeOrg", searchType); // 네비 데이터
			map.put("keywordOrg", keyword); // 네비 데이터
			
			// 리스트
			List<Object> list = bService.selectSearchBoardList(map);
			// 페이지네비
			Page pageNavi = bService.getPageNavi(map);
			
			model.addAttribute("list",list);
			model.addAttribute("pageNavi",pageNavi);
		}
		return "part_board/allPartBoardPage";
	}
	
	// 부서별 게시판 - 게시글 one select
	@RequestMapping(value="/postInPartBoard.ho")
	public String partBoard(PartBoard pb, Model model, 
						HttpServletRequest request, @SessionAttribute("member") Member m)
	{
		if(m != null && pb.getDeptCode().equals(m.getDeptCode())){
			// 조회수 +1
			bService.updateHits(pb.getPartNo());
			return onePost(pb.getDeptCode(),pb.getPartNo(),model,request,m);
		}
		model.addAttribute("msg","잘못된 접근입니다.");
		return "part_board/allPartBoardPage";
	}
	private String onePost(String pageDeptCode, int partNo, Model model, HttpServletRequest request, Member m) {
		// 댓글 등록하면 해당 페이지를 제 로딩하려고 했는데 
		// 이렇게 분리하지 않으면 조회수가 댓글 등록할 때마다 오름 ;;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("deptCode", pageDeptCode);
		map.put("postNo", partNo);
		PartBoard pb = (PartBoard)bService.selectOnePost(map); // 게시글 정보
		
		// 다음글
		int nextPostNo = bService.selectNextPost(map);
		// 이전글
		int prevPostNo = bService.selectPrevPost(map);
		
		// 게시글 댓글
		int comntCount = bService.selectComntCount(partNo);
		
		Page comntPage = createPage(request, 5, 10);
		map.put("page", comntPage);
		map.put("comntPostNo", partNo);
		map.put("comntCount", comntCount);
		List<Object> comntList = bService.selectPostComments(map);
		Page pageNavi = bService.getComntPageNavi(map);
		
		model.addAttribute("pb",pb);
		model.addAttribute("comntList",comntList);
		model.addAttribute("comntCount",comntCount);
		model.addAttribute("nextPost",nextPostNo);
		model.addAttribute("prevPost",prevPostNo);
		model.addAttribute("pageNavi",pageNavi);
		return "part_board/partBoard";
	}
	// 부서별 게시판 - 게시글 삭제
	@RequestMapping(value="/deltetPostPartBoard.ho")
	public void deletePost(@RequestParam("memNo") int memNo, @RequestParam("postNo") int postNo, HttpServletResponse response, 
							@SessionAttribute("member") Member m) throws IOException{
		if(m != null){
			HashMap<String,Object> map = new HashMap<String, Object>();
			int[] postNoList = {postNo}; // 동적 쿼리문 보낼 때를 위해...배열로 담아서.. 
			map.put("memNo",memNo);
			map.put("postNo", postNoList);
			int result = bService.deletePost(map);
			if(result>0){
				response.getWriter().print(true);
			}
		}
		response.getWriter().print(false);
	}
	//  부서별 게시판 - 댓글 등록
	@RequestMapping(value="/writeComntPartBoard.ho")
	public String writeComnt(PartComments comnt, Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m != null){
			comnt.setMemNo(m.getMemNo());
			comnt.setPartComntWriter(m.getMemName());
			comnt.setPartComntEmail(m.getMemEmail());
			int result = bService.insertPostComnt(comnt);
			if(result<=0){
				model.addAttribute("msg","댓글 작성이 실패하였습니다. \n지속적인 문제 발생시 관리자에 문의하세요.");
			}
			model.addAttribute("location","/postInPartBoardRe.ho?deptCode="+m.getDeptCode()+"&partNo="+comnt.getPartNo());
			return "result";
		}else{ // 비로그인 시 
			return "redirect:login.jsp";
		}
		
	}
	// 댓글 등록 후 페이지 재로딩 메소드
	@RequestMapping(value="/postInPartBoardRe.ho")
	public String partBoard2(PartBoard pb, Model model, 
						HttpServletRequest request, @SessionAttribute("member") Member m)
	{
		if(m != null && pb.getDeptCode().equals(m.getDeptCode())){
			
			return onePost(pb.getDeptCode(),pb.getPartNo(),model,request,m);
		}
		model.addAttribute("msg","잘못된 접근입니다.");
		return "part_board/allPartBoardPage";
	}
	// 댓글 수정 (ajax)
	@RequestMapping(value="/modifyPostComntPartBoard.ho",produces = "application/text; charset=utf8")
	public @ResponseBody String updateComnt(@RequestParam("comntNo") int comntNo, @RequestParam("writerNo") int writerNo, @RequestParam("comnt") String comnt, 
						HttpServletResponse response, @SessionAttribute("member") Member m) throws UnsupportedEncodingException, IOException{ 
		comnt = URLDecoder.decode(comnt, "UTF-8"); // 한글 디코딩
		if(writerNo==m.getMemNo()){
			int result = bService.updateComnt(comntNo,comnt);
			if(result>0){
				return "true";
			}
			return "댓글 수정에 실패했습니다. \n지속적인 실패시 관리자에 문의하세요.";
		}
		return "본인이 아닙니다만";
	}
	
	// 댓글 삭제 (ajax)
	@RequestMapping(value="/deletePostComntPartBoard.ho",produces = "application/text; charset=utf8")
	public @ResponseBody String deleteComment(@RequestParam("comntNo") int comntNo, @RequestParam("writerNo") int writerNo, HttpServletResponse response,
							@SessionAttribute("member") Member m) throws IOException{
		if(writerNo==m.getMemNo()){
			int result = bService.deleteComnt(comntNo);
			if(result>0){
				return "true";
			}
			return "댓글 삭제에 실패했습니다. \n지속적인 실패시 관리자에 문의하세요.";
		}
		return "본인이 아닙니다만";
	}
	
	// 부서별 게시판 - 새글쓰기 page
	@RequestMapping(value="/writePostPartBoard.ho")
	public String writePost(@RequestParam("deptCode") String pageDeptCode, Model model, @SessionAttribute("member") Member m){
		String deptCode = m.getDeptCode().replaceAll(" ", ""); // 공백제거
		if(m!=null && pageDeptCode.equals(deptCode)){
			ArrayList<Department> deptList = mService.selectDeptAll();
			for(Department dept : deptList){
				if(dept.getDeptCode().equals(pageDeptCode+" ")) model.addAttribute("deptName", dept.getDeptName());
				// 부서 이름 거르기
			}
			return "part_board/writePostPartBoard";
		}
		return "redirect:login.jsp";
	}
	// 부서별 게시판 - 게시글 등록 insert
	@RequestMapping(value="/savePostPartBoard.ho")
	public String addPost(Model model, HttpServletRequest request, @SessionAttribute("member") Member m) throws IOException, Exception{
		if(m!=null){
			
			MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
			
			MultipartFile file = multi.getFile("attachedFile");
			
			 String path="";
	         UUID randomeUUID = UUID.randomUUID();
	         String organizedfilePath="";        
	         if(file!=null){
	        
	          //System.out.println("파라미터명" + file.getName());
	          //System.out.println("파일크기" + file.getSize());
	          //System.out.println("파일 존재" + file.isEmpty());
	          //System.out.println("오리지날 파일 이름" + file.getOriginalFilename());
	        
	          // 파일이 업로드 되는 경로
	          path = context.getRealPath("/");
	          path = path.replace("\\target\\m2e-wtp\\web-resources", "");
	          String uploadPath = "src\\main\\webapp\\resources\\file\\part_board\\";
	          path = path + uploadPath;
	         
	          //System.out.println(path);
	          
	          InputStream inputStream = null;
	          OutputStream outputStream = null;
	          
	              if (file.getSize() > 0) {
	                  inputStream = file.getInputStream();
	                  File realUploadDir = new File(path);
	                  
	                  if (!realUploadDir.exists()) {
	                      realUploadDir.mkdirs();//폴더생성.
	                  }
	                  
	                  
	                  organizedfilePath = path + randomeUUID + "_" + file.getOriginalFilename();
	                  System.out.println(organizedfilePath);//파일이 저장된경로 + 파일 명
	                  
	                  outputStream = new FileOutputStream(organizedfilePath);
	 
	                  int readByte = 0;
	                  byte[] buffer = new byte[8192];
	 
	                  while ((readByte = inputStream.read(buffer, 0, 8120)) != -1) {
	                      outputStream.write(buffer, 0, readByte); //파일 생성 ! 
	                  }
	              }
	              
	         }
			
			// 위의 코드까지 하면 파일 업로드는 완료
			
			// 게시판 insert 비즈니스 로직
			PartBoard pb = new PartBoard(); 
			
			pb.setPartTitle(multi.getParameter("partTitle"));	// 제목
			pb.setDeptCode(m.getDeptCode());					// 부서코드
			pb.setMemNo(m.getMemNo());							// 사번
			pb.setPartWriter(m.getMemName());					// 작성자
			pb.setPartContent(multi.getParameter("partContent"));//글내용
			//System.out.println(pb.getPartTitle()+" / "+pb.getDeptCode()+" / "+pb.getMemNo()+" / "+pb.getPartWriter()+" / "+pb.getPartContent());
			
			// 비즈니스 로직
			int partNo = bService.insertPost(pb);
			// 게시글 고유번호
			if(partNo>0){
				
				if(file.getSize() > 0){
				
					// 서버에 실제로 업로드 된 파일이름 가져오기
					String originalFileName = file.getOriginalFilename();
					
					long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
					
					// 파일 리네임
					File fileSave = new File(organizedfilePath); // 파일 연결
					File copyFile = new File(path+"\\"+m.getDeptCode()+partNo+"_"+currentTime+"_ho");
					String changedFileName = m.getDeptCode()+partNo+"_"+currentTime+"_ho"; // DB에 저장할 파일 이름
			
					//System.out.println("변경전 파일 이름 : " + organizedfilePath);
					//System.out.println("변경된 파일 이름 : " +path+"\\"+m.getDeptCode()+partNo+"_"+currentTime+"_ho");
					
					//아래 로직은 파일을 새로만들어서 데이터를 이동하는 작업
					FileInputStream fis = new FileInputStream(fileSave); //읽을파일
		            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
		            
		            int fileByte = 0; 
		            // fis.read()가 -1 이면 파일을 다 읽은것
		            while((fileByte = fis.read()) != -1) {
		                fos.write(fileByte);
		            }
		            //자원사용종료
		            fis.close();
		            fos.close();
					
		            fileSave.delete(); //기존 원본 파일 삭제
					
					// File 객체를 통해 파일이름이 변경되면 새롭게 연결하는 파일 객체가 필요함
					File reNameFile = new File(path+"\\"+changedFileName); // 이름이 바뀌여 다시 연결해줌
					String filePath = reNameFile.getPath(); // 경로
					// 해당 업로드된 file의 사이즈
					long fileSize = reNameFile.length();
					
					BoardFile pf  = new BoardFile();
					pf.setPostNo(partNo);
					pf.setOrigName(originalFileName);
					pf.setChgName(changedFileName);
					pf.setFilePath(filePath);
					pf.setFileSize(fileSize);
					
					bService.insertPostFile(pf); // 파일 DB에 저장
				} // 파일 null이 아니면 디비 저장 if 문
				
				model.addAttribute("msg","글 등록이 성공하였습니다.");
				
			}else{
				model.addAttribute("msg","글 등록에 실패하였습니다. 지속적인 실패시 관리자에 문의하세요.");
			}// 게시글 디비 저장 실패 if 문
			
			model.addAttribute("location","/allPartBoardPage.ho");
			
		}else{
			return "redirect:login.jsp";
		} // 로그인을 하지 않았다면
		return "result";
	}
	
	// 부서별 게시판 update 페이지
	@RequestMapping(value="/partBoardModify.ho")
	public String partBoardModify(PartBoard pb, Model model, @SessionAttribute("member") Member m){
		if(m!=null){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("deptCode", pb.getDeptCode()+" "); // 넘어오는 데이터가 ㅜ 공백이 없음.....하...
			map.put("postNo", pb.getPartNo());
			PartBoard result = (PartBoard)bService.selectOnePost(map);
			
			model.addAttribute("pb",result);
		}
		return "part_board/partBoardModify";
	}
	// 부서별 게시판 update 
	@RequestMapping(value="/updatePostPartBoard.ho")
	public String updatePostPartBoad(Model model, HttpServletRequest request, @SessionAttribute("member") Member m) throws IOException, Exception{
		
		if(m!=null){
			
			MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
			
			MultipartFile file = multi.getFile("attachedFile");
			
			 String path="";
	         UUID randomeUUID = UUID.randomUUID();
	         String organizedfilePath="";        
	         if(file!=null){
	        
	          //System.out.println("파라미터명" + file.getName());
	          //System.out.println("파일크기" + file.getSize());
	          //System.out.println("파일 존재" + file.isEmpty());
	          //System.out.println("오리지날 파일 이름" + file.getOriginalFilename());
	        
	          // 파일이 업로드 되는 경로
	          path = context.getRealPath("/");
		      path = path.replace("\\target\\m2e-wtp\\web-resources", "");
		      String uploadPath = "src\\main\\webapp\\resources\\file\\part_board\\";
		      path = path + uploadPath;
		     
		      System.out.println(path);
		          
	          InputStream inputStream = null;
	          OutputStream outputStream = null;
	          
	              if (file.getSize() > 0) {
	                  inputStream = file.getInputStream();
	                  File realUploadDir = new File(path);
	                  
	                  if (!realUploadDir.exists()) {
	                      realUploadDir.mkdirs();//폴더생성.
	                  }
	                  
	                  
	                  organizedfilePath = path + randomeUUID + "_" + file.getOriginalFilename();
	                  System.out.println(organizedfilePath);//파일이 저장된경로 + 파일 명
	                  
	                  outputStream = new FileOutputStream(organizedfilePath);
	 
	                  int readByte = 0;
	                  byte[] buffer = new byte[8192];
	 
	                  while ((readByte = inputStream.read(buffer, 0, 8120)) != -1) {
	                      outputStream.write(buffer, 0, readByte); //파일 생성 ! 
	                  }
	              }
	              
	         }
			
			// 위의 코드까지 하면 파일 업로드는 완료
			
			String fileNo = multi.getParameter("fileNo");
			
			if(Integer.parseInt(multi.getParameter("memNo"))==m.getMemNo()){
				
				// 게시판 insert 비즈니스 로직
				PartBoard pb = new PartBoard(); 
				
				pb.setPartNo(Integer.parseInt(multi.getParameter("partNo")));	// 게시글 번호	
				pb.setPartTitle(multi.getParameter("partTitle"));				// 제목
				pb.setDeptCode(multi.getParameter("deptCode"));					// 부서코드
				pb.setMemNo(Integer.parseInt(multi.getParameter("memNo")));		// 사번
				pb.setPartWriter(m.getMemName());								// 작성자
				pb.setPartContent(multi.getParameter("partContent"));			//글내용
				//System.out.println(pb.getPartNo()+" / "+pb.getPartTitle()+" / "+pb.getDeptCode()+" / "+pb.getMemNo()+" / "+pb.getPartWriter()+" / "+pb.getPartContent());
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("pb", pb);
				int result = bService.updatePost(map);
				
				if(result>0){
					
					if(file.getSize() > 0){
						// 2. 파일이 있는 경우
						// 2-1) 원래 파일이 있는 경우 - update
						// 2-2) 원래 파일이 없는 경우 - insert
						
						// 서버에 실제로 업로드 된 파일이름 가져오기
						String originalFileName = file.getOriginalFilename();
						
						long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
						
						// 파일 리네임
						File fileSave = new File(organizedfilePath); // 파일 연결
						File copyFile = new File(path+"\\"+m.getDeptCode()+pb.getPartNo()+"_"+currentTime+"_ho"); // 새로 만든 파일 연렬
						String changedFileName = m.getDeptCode()+pb.getPartNo()+"_"+currentTime+"_ho"; // DB에 저장할 파일 이름
						
						//아래 로직은 파일을 새로만들어서 데이터를 이동하는 작업
						FileInputStream fis = new FileInputStream(fileSave); //읽을파일
			            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일
			            
			            int fileByte = 0; 
			            // fis.read()가 -1 이면 파일을 다 읽은것
			            while((fileByte = fis.read()) != -1) {
			                fos.write(fileByte);
			            }
			            //자원사용종료
			            fis.close();
			            fos.close();
						
			            fileSave.delete(); //기존 원본 파일 삭제
						
						// File 객체를 통해 파일이름이 변경되면 새롭게 연결하는 파일 객체가 필요함
						File reNameFile = new File(path+"\\"+changedFileName); // 이름이 바뀌여 다시 연결해줌
						String filePath = reNameFile.getPath(); // 경로
						// 해당 업로드된 file의 사이즈
						long fileSize = reNameFile.length();
						
						BoardFile pf  = new BoardFile();
						pf.setFileNo(Integer.parseInt(fileNo));
						pf.setPostNo(pb.getPartNo());
						pf.setOrigName(originalFileName);
						pf.setChgName(changedFileName);
						pf.setFilePath(filePath);
						pf.setFileSize(fileSize);
						
						if(!fileNo.equals("0")){ // 기존에 파일이 있었다면
							// 2-1))
							bService.updatePostFile(pf); // 파일 DB에 updqte 저장
							
						}else{
							// 2-2))
							bService.insertPostFile(pf); // 파일 DB에 insert저장
						}
					}else{
						// 1. 파일이 없는 경우
						// 1-1) 원래 파일이 있는 경우 - delete 
						// 1-2) 원래 파일이 없는 경우 - 아무것도 안 해도 됨
						// 1-3) 파일 첨부 수정을 안 한 경우
						if(!fileNo.equals("0")){ // 기존에 파일이 있었다면
							
							if(multi.getParameter("havefile")==null){
								// 1-1))
								HashMap<String, Object> mapf = new HashMap<String, Object>();
								mapf.put("fileNo", Integer.parseInt(fileNo));
								mapf.put("postNo", pb.getPartNo());
								
								bService.deletePostFile(mapf);
							}
							// 1-3))
						}
						// 1-2))
					}
					
					model.addAttribute("msg","글 수정이 성공하였습니다.");
					model.addAttribute("location","/postInPartBoard.ho?deptCode="+pb.getDeptCode()+"&partNo="+pb.getPartNo());
				}else{
					model.addAttribute("msg","글 수정에 실패하였습니다. \n지속적인 실패시 관리자에 문의하세요.");
					model.addAttribute("location","/partBoardModify.ho?deptCode="+pb.getDeptCode()+"&partNo="+pb.getPartNo());
				}
				return "result";
			}
			
		}
		return "redirect:login.jsp";
	}
	
	
	// 페이징처리 할 때 필요한 페이지 객체 만들기
	private Page createPage(HttpServletRequest request,int RCPP, int NCPP){
		page = new Page();
		
		int currentPage; // 현재 페이지값을 가지고 있는 변수 - 페이징 처리를 위한 변수
		if(request.getParameter("currentPage")==null) {
			page.setCurrentPage(1);
		}else {
			page.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
		}
		page.setRecordCountPerPage(RCPP); // 한 페이지당 몇개의 게시물이 보이게 될 것인지 - 페이징 처리를 위한 변수
		page.setNaviCountPerPage(NCPP); // page Navi값이 몇개씩 보여줄 것인지 - 페이징 처리(네비)를 위한 변수
		
		return page;
	}
	// 파일 다운로드
	@RequestMapping(value="/downloadFilePartBoard.ho")
	public void downloadFile(PartBoard pb, HttpServletResponse response) throws IOException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("postNo", pb.getPartNo());
		map.put("fileNo", pb.getFileNo());
		BoardFile bf = bService.selectPostFile(map);

		if(bf != null){
			// 가져온 파일 정보를 가지고 해당 파일을 물리적으로 접근
			File file = new File(bf.getFilePath());
			// 웹 브라우저를 통해 문자열이 아닌 데이터가 전송되려면 바이너리 타입으로 처리
			response.setContentType("application/octet-stream");
			// 파일의 사이즈를 전달
			response.setContentLength((int)bf.getFileSize());
			// os에 맞게 인코딩
			// windows는 기본적으로 ISO-8859-1
			String fileName = new String(bf.getOrigName().getBytes(), "ISO-8859-1");
			
			// 파일이름을 header를 통해 전달
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			
			
			// 위 코드까지는 파일이름 + 파일을 전송할수잇는 웹 환경을 셋팅
			
			// 아래코드는 실제 파일이 가지고 있는 데이터를 보내는 작업
			// 해당되는 파일의 데이터를 가져올 수있는 통로
			FileInputStream fileIn = new FileInputStream(file);
			// 클라이언트에게 데이터를 전달하는 통로
			ServletOutputStream out = response.getOutputStream();
			
			// 4KByte 씩 처리
			byte[] outputByte = new byte[4096];
			
			// inputStream으로 데이터를 읽어다가 output 스트림으로 전송
			while(fileIn.read(outputByte,0,4096) != -1){
				out.write(outputByte,0,4096);
			}
			fileIn.close();
			out.close();
		}
	}
}
