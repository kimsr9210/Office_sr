package kr.or.houroffice.board.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.houroffice.board.model.service.PartBoardService;
import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.PartBoard;
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
	
	// 부서별 게시판 all select
	@RequestMapping(value="/allPartBoardPage.ho")
	public String allPartBoardPage(Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m!=null){
			
			int countAll = mService.selectCountAllMember();
			
			int currentPage; // 현재 페이지값을 가지고 있는 변수 - 페이징 처리를 위한 변수
			if(request.getParameter("currentPage")==null) {
				currentPage = 1;
			}else {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}	
			int recordCountPerPage = 10; // 한 페이지당 몇개의 게시물이 보이게 될 것인지 - 페이징 처리를 위한 변수
			
			
			
			ArrayList<PartBoard> list = bService.selectBoardList(m.getDeptCode());
			
			
			
			// 페이징 처리 - 네비
			int naviCountPerPage = 10; // page Navi값이 몇개씩 보여줄 것인지 - 페이징 처리를 위한 변수
			//String pageNavi = bService.getPageNavi(currentPage,recordCountPerPage,naviCountPerPage);
			
			
			
			
			
			
			model.addAttribute("list",list);
		}else{
			return "redirect:login.jsp";
		}
		return "/part_board/allPartBoardPage";
	}
	
	// 부서별 게시판 one select
	@RequestMapping(value="/partBoard.ho")
	public String partBoard(){
		return "/part_board/partBoard";
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
			return "/part_board/writePostPartBoard";
		}
		return "redirect:login.jsp";
	}
	// 부서별 게시판 - 게시글 등록 insert
	@RequestMapping(value="/savePostPartBoard.ho")
	public String addPost(Model model, HttpServletRequest request, @SessionAttribute("member") Member m) throws IOException{
		
		if(m!=null){
			// 파일이 업로드 되는 경로
			String uploadPath = "/resources/file/part_board/";
	
			// 최대 파일 사이즈를 정하기 위한 값
			int uploadFileSizeLimit = 10*1024*1024; // 최대 10MB 까지 업로드 가능
	
			// 파일 이름 인코딩 값
			String encType = "UTF-8"; 
			
			// 정보를 가지고 있는 객체
			// @Autowired	ServletContext context;
			
			// context.getRealPath(); -> WebContent까지의 절대 경로 (실제경로)
			String realUploadPath = context.getRealPath(uploadPath);
			
			// MultipartRequest 객체 생성 (생성하면서 마지막 5번째 정책 설정 객체 만들기)
			MultipartRequest multi = new MultipartRequest(request, // 1. request
																realUploadPath, // 2. 실제 업로드 되는 경로 
																uploadFileSizeLimit, // 3. 최대 파일 사이즈 크기
																encType, // 4. 인코딩 타입
																new DefaultFileRenamePolicy()); // 5. 중복 이름 정책
			
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
				
				if(multi.getFilesystemName("attachedFile")!=null){
				
					// 서버에 실제로 업로드 된 파일이름 가져오기
					String originalFileName = multi.getFilesystemName("attachedFile");
					
					long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
					
					// File 객체는 경로를 통해서 해당 파일을 연결하는 객체
					File file = new File(realUploadPath+"\\"+originalFileName);
					// File 객체가 가지고 있는 renameTo 메소드를 통해서 파일의이름을 바꿀 수 잇음
					file.renameTo(new File(realUploadPath+"\\"+m.getDeptCode()+partNo+"_"+currentTime+"_ho")); // 실제 경로에 있는 파일 이름을 바꿈
					String changedFileName = m.getDeptCode()+partNo+"_"+currentTime+"_ho"; // DB에 저장할 파일 이름
					// File 객체를 통해 파일이름이 변경되면 새롭게 연결하는 파일 객체가 필요함
					File reNameFile = new File(realUploadPath+"\\"+changedFileName); // 이름이 바뀌여 다시 연결해줌
					String filePath = reNameFile.getPath(); // 경로
					// 해당 업로드된 file의 사이즈
					long fileSize = reNameFile.length();
					
					BoardFile pf  = new BoardFile();
					pf.setPostNo(partNo);
					pf.setOrigName(originalFileName);
					pf.setChgName(changedFileName);
					pf.setPath(filePath);
					pf.setSize(fileSize);
					
					bService.insertPostFile(pf); // 파일 DB에 저장
				} // 파일 null이 아니면 디비 저장 if 문
				
				model.addAttribute("msg","성공");
				
			}else{
				model.addAttribute("msg","실패");
			}// 게시글 디비 저장 실패 if 문
			
			model.addAttribute("location","/allPartBoardPage.ho");
			
		}else{
			return "redirect:login.jsp";
		} // 로그인을 하지 않았다면
		return "result";
	}
	
	// 부서별 게시판 update
	@RequestMapping(value="/partBoardModify.ho")
	public String partBoardModify(){
		return "/part_board/partBoardModify";
	}

}
