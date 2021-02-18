package kr.or.houroffice.board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.houroffice.board.model.service.NoticeService;
import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.BoardPost;
import kr.or.houroffice.common.Page;
import kr.or.houroffice.member.model.vo.Member;

@Controller
public class NoticeController {
	
	@Autowired
	ServletContext context; // 파일 업로드를 위한 파일의 정보를 가지고 있는 객체
	
	@Resource(name="noticeBService")
	private NoticeService bService;
	
	private Page page;
	
	// 사용자 공지사항 목록 
	@RequestMapping(value="/allNoticePage.ho") // view page
	public String postListPage(Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m!=null){
			HashMap<String, Object> map = listPage(request,"client");
			
			model.addAttribute("list",map.get("list"));
			model.addAttribute("pageNavi",map.get("pageNavi"));
			
			return "notice/allNoticePage";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 사용자 공지사항 목록 검색
	@RequestMapping(value="/searchNotice.ho")
	public String searchNotice(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword,
									Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m!=null){
			HashMap<String, Object> map = searchListPage(searchType,keyword,request,"client");
			
			model.addAttribute("list",map.get("list"));
			model.addAttribute("pageNavi",map.get("pageNavi"));
			
			return "notice/allNoticePage";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 사용자 공지사항 게시글 select One
	@RequestMapping(value="/notice.ho") // view page 
	public String notice(@RequestParam("notNo") int postNo, Model model, @SessionAttribute("member") Member m){
		if(m!=null){
			bService.updateHits(postNo);
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("postNo", postNo);
			BoardPost bp = (BoardPost)bService.selectOnePost(map); // 게시글 정보
			
			if(bp != null){
				// 다음글
				int nextPostNo = bService.selectNextPost(map);
				// 이전글
				int prevPostNo = bService.selectPrevPost(map);
				
				model.addAttribute("bp",bp);
				model.addAttribute("nextPost",nextPostNo);
				model.addAttribute("prevPost",prevPostNo);
				
				return "notice/notice";
			}// 찾는 게시물이 없다면 ↓
			model.addAttribute("msg","해당 게시물이 없습니다.");
			model.addAttribute("location","/allNoticePage.ho");
			return "result";
			
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 파일 다운로드
	@RequestMapping(value="/downloadFileNotice.ho")
	public void downloadFile(@RequestParam("notNo") int postNo, @RequestParam("fileNo") int fileNo, 
							HttpServletResponse response) throws IOException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("postNo", postNo);
		map.put("fileNo", fileNo);
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
	
	//admin ---------------------------------------------------------------------------
	// 공지사항 - 전체 목록
	@RequestMapping(value="/admin_tap_allNoticePage.ho") // view page
	public String adminPostListPage(Model model, HttpServletRequest request, @SessionAttribute("member") Member m){ 
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				
				HashMap<String, Object> map = listPage(request,"admin");
				
				model.addAttribute("list",map.get("list"));
				model.addAttribute("pageNavi",map.get("pageNavi"));
				
				return "admin_tap/general_affairs_department/allNoticePage";
			}// 관리자 권한이 없다면 ↓
			Calendar cal = Calendar.getInstance();
			int todayMon = cal.get(Calendar.MONTH) + 1;
			model.addAttribute("location","/main.ho?todayMon="+todayMon);
			model.addAttribute("msg","접근 권한이 없습니다.");
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 공지사항 - 검색
	@RequestMapping(value="/admin_tap_searchNotice.ho")
	public String adminsSearchNotice(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword,
									Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				
				HashMap<String, Object> map = searchListPage(searchType,keyword,request,"admin");
				
				model.addAttribute("list",map.get("list"));
				model.addAttribute("pageNavi",map.get("pageNavi"));
				
				return "admin_tap/general_affairs_department/allNoticePage";
			}// 관리자 권한이 없다면 ↓
			Calendar cal = Calendar.getInstance();
			int todayMon = cal.get(Calendar.MONTH) + 1;
			model.addAttribute("location","/main.ho?todayMon="+todayMon);
			model.addAttribute("msg","접근 권한이 없습니다.");
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 공지사항 - 새글쓰기
	@RequestMapping(value="/admin_tap_noticeWrite.ho") // view page
	public String adminWritePostPage(Model model, @SessionAttribute("member") Member m){
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				return "admin_tap/general_affairs_department/writePostNotice";
			}// 관리자 권한이 없다면 ↓
			Calendar cal = Calendar.getInstance();
			int todayMon = cal.get(Calendar.MONTH) + 1;
			model.addAttribute("location","/main.ho?todayMon="+todayMon);
			model.addAttribute("msg","접근 권한이 없습니다.");
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 공지사항 - 새글 등록 insert
	@RequestMapping(value="/savePostNotice.ho")
	public String addPost(Model model, HttpServletRequest request, @SessionAttribute("member") Member m) throws IOException,Exception{
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				
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
			      String uploadPath = "src\\main\\webapp\\resources\\file\\notice\\";
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
		                  //System.out.println(organizedfilePath);//파일이 저장된경로 + 파일 명
		                  
		                  outputStream = new FileOutputStream(organizedfilePath);
		 
		                  int readByte = 0;
		                  byte[] buffer = new byte[8192];
		 
		                  while ((readByte = inputStream.read(buffer, 0, 8120)) != -1) {
		                      outputStream.write(buffer, 0, readByte); //파일 생성 ! 
		                  }
		              }
		              
		         }
				
				// 위의 코드까지 하면 파일 업로드는 완료
						
				// 위의 코드까지 하면 파일 업로드는 완료
				BoardPost bp = new BoardPost();
				bp.setTitle(multi.getParameter("title"));	// 제목
				bp.setDeptCode(m.getDeptCode());				// 부서코드
				bp.setMemNo(m.getMemNo());						// 사번
				bp.setWriter(m.getMemName());					// 작성자
				bp.setContent(multi.getParameter("content"));//글내용
				//System.out.println(bp.getTitle()+" / "+bp.getDeptCode()+" / "+bp.getMemNo()+" / "+bp.getWriter()+" / "+bp.getContent());
				int postNo = bService.insertPost(bp);
				if(postNo>0){
					if(file.getSize() > 0){
						
						// 서버에 실제로 업로드 된 파일이름 가져오기
						String originalFileName = file.getOriginalFilename();
						
						long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
						
						// 파일 리네임
						File fileSave = new File(organizedfilePath); // 파일 연결
						File copyFile = new File(path+"\\"+"N"+postNo+"_"+currentTime+"_ho"); // 새로 만든 파일 연렬
						String changedFileName = "N"+postNo+"_"+currentTime+"_ho"; // DB에 저장할 파일 이름
						
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
						pf.setPostNo(postNo);
						pf.setOrigName(originalFileName);
						pf.setChgName(changedFileName);
						pf.setFilePath(filePath);
						pf.setFileSize(fileSize);
						
						bService.insertPostFile(pf); // 파일 DB에 저장
					}
					model.addAttribute("msg","새 글이 등록되었습니다.");
				}else{ // 게시글 디비 저장에 실패했다면 ↓
					model.addAttribute("msg","새 글 등록에 실패하였습니다. 지속적인 실패시 관리자에 문의하세요.");
				}
				model.addAttribute("location","/admin_tap_allNoticePage.ho");
			}else{// 관리자 권한이 없다면 ↓
				Calendar cal = Calendar.getInstance();
				int todayMon = cal.get(Calendar.MONTH) + 1;
				model.addAttribute("location","/main.ho?todayMon="+todayMon);
				model.addAttribute("msg","접근 권한이 없습니다.");
			}
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	
	// 공지사항 - 글 수정
	@RequestMapping(value="/admin_tap_modifyNotice.ho") // view page
	public String adminModifyPost(@RequestParam("notNo") int postNo, Model model, @SessionAttribute("member") Member m){
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("postNo", postNo);
				BoardPost bp = (BoardPost)bService.selectOnePost(map); // 게시글 정보
				
				if(bp != null){
					model.addAttribute("bp",bp);
					return "admin_tap/general_affairs_department/noticeModify";
				}// 찾는 게시물이 없다면 ↓
				model.addAttribute("msg","해당 게시물이 없습니다.");
				model.addAttribute("location","/allNoticePage.ho");
				return "result";
				
			}// 관리자 권한이 없다면 ↓
			Calendar cal = Calendar.getInstance();
			int todayMon = cal.get(Calendar.MONTH) + 1;
			model.addAttribute("location","/main.ho?todayMon="+todayMon);
			model.addAttribute("msg","접근 권한이 없습니다.");
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 공지사항 글 수정
	@RequestMapping(value="/admin_tap_updatePostNotice.ho")
	public String adminUpdatePostNotice(Model model, HttpServletRequest request, @SessionAttribute("member") Member m) throws IOException,Exception{
		
		if(m!=null){
			
			if(m.getMemRightCode()=='D'){
				
				MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;
				
				MultipartFile file = multi.getFile("attachedFile");
				
				 String path="";
		         UUID randomeUUID = UUID.randomUUID();
		         String organizedfilePath="";  
		         System.out.println(file.getOriginalFilename());
		         if(file!=null){
		        
		          //System.out.println("파라미터명" + file.getName());
		          //System.out.println("파일크기" + file.getSize());
		          //System.out.println("파일 존재" + file.isEmpty());
		          //System.out.println("오리지날 파일 이름" + file.getOriginalFilename());
		        
		          // 파일이 업로드 되는 경로
		          path = context.getRealPath("/");
			      path = path.replace("\\target\\m2e-wtp\\web-resources", "");
			      String uploadPath = "src\\main\\webapp\\resources\\file\\notice\\";
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
		                  //System.out.println(organizedfilePath);//파일이 저장된경로 + 파일 명
		                  
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
				
				// 게시판 insert 비즈니스 로직
				BoardPost bp = new BoardPost(); 
				
				bp.setPostNo(Integer.parseInt(multi.getParameter("notNo")));	// 게시글 번호	
				bp.setTitle(multi.getParameter("title"));						// 제목
				bp.setContent(multi.getParameter("content"));				//글내용
				//System.out.println(pb.getPartNo()+" / "+pb.getPartTitle()+" / "+pb.getDeptCode()+" / "+pb.getMemNo()+" / "+pb.getPartWriter()+" / "+pb.getPartContent());
				
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("bp", bp);
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
						File copyFile = new File(path+"\\"+"N"+bp.getPostNo()+"_"+currentTime+"_ho"); // 새로 만든 파일 연렬
						String changedFileName = "N"+bp.getPostNo()+"_"+currentTime+"_ho"; // DB에 저장할 파일 이름
						
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
						pf.setPostNo(bp.getPostNo());
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
					}else{// 파일이 없다면 ↓
						// 1. 파일이 없는 경우
						// 1-1) 원래 파일이 있는 경우 - delete 
						// 1-2) 원래 파일이 없는 경우 - 아무것도 안 해도 됨
						// 1-3) 파일 첨부 수정을 안 한 경우
						if(!fileNo.equals("0")){ // 기존에 파일이 있었다면
							
							if(multi.getParameter("havefile")==null){
								// 1-1))
								HashMap<String, Object> mapf = new HashMap<String, Object>();
								mapf.put("fileNo", Integer.parseInt(fileNo));
								mapf.put("postNo", bp.getPostNo());
								
								bService.deletePostFile(mapf);
							}
							// 1-3))
						}
						// 1-2))
					}// 글 수정에 성공했다면 ↓
					model.addAttribute("msg","글 수정이 성공하였습니다.");
					model.addAttribute("location","/admin_tap_modifyNotice.ho?notNo="+bp.getPostNo());
				}else{// 글 수정에 실패했다면 ↓
					model.addAttribute("msg","글 수정에 실패하였습니다. \n지속적인 실패시 관리자에 문의하세요.");
					model.addAttribute("location","/admin_tap_modifyNotice.ho?notNo="+bp.getPostNo());
				}
			}else{// 관리자 권한이 없다면 ↓
				Calendar cal = Calendar.getInstance();
				int todayMon = cal.get(Calendar.MONTH) + 1;
				model.addAttribute("location","/main.ho?todayMon="+todayMon);
				model.addAttribute("msg","접근 권한이 없습니다.");
			}
			return "result";
			
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	
	// 공지사항 - 글 삭제 (ajax) - update
	@RequestMapping(value="/admin_tap_deleteNotice.ho")
	public void deletePost(@RequestParam(value="postNoList[]") List<String> postNoList,HttpServletResponse response, @SessionAttribute("member") Member m) throws IOException{
		if(m.getMemRightCode()=='D'){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("postNo", postNoList);
			int result =  bService.deletePost(map);
			if(result>0){
				response.getWriter().print(true);
			}else {
				response.getWriter().print(false);
			}
		}
	}
	
	// 목록 뽑아오는 공통 메소드
	private HashMap<String, Object> listPage(HttpServletRequest request, String viewType){
		Page page = createPage(request,10,10); // (request , 한 페이지당 게시물수 , 한 페이지당 보여줄 네비 수)
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("viewType", viewType);
		
		List<Object> list = bService.selectBoardList(map);
		Page pageNavi = bService.getPageNavi(map);
		
		map.put("list", list);
		map.put("pageNavi", pageNavi);
		return map;
	}
	// 검색 목록 뽑아오는 공통 메소드
	private HashMap<String, Object> searchListPage(String searchType, String keyword, HttpServletRequest request, String viewType){
		Page page = createPage(request,10,10);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("viewType", viewType);
		if(!searchType.equals("both")){
			map.put("searchType", "NOT_"+searchType); // 쿼리문 데이터
		}else{
			map.put("searchType", searchType); // 쿼리문 데이터
		}
		map.put("type", "NOT_");
		map.put("keyword", "%"+keyword+"%"); // 쿼리문 데이터
		map.put("searchTypeOrg", searchType); // 네비 데이터
		map.put("keywordOrg", keyword); // 네비 데이터
		
		List<Object> list = bService.selectSearchBoardList(map);
		Page pageNavi = bService.getPageNavi(map);
		
		map.put("list", list);
		map.put("pageNavi", pageNavi);
		return map;
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
	
}
