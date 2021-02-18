package kr.or.houroffice.project.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.member.model.service.MemberService;
import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.project.model.service.ProjectServiceImpl;
import kr.or.houroffice.project.model.vo.Project;
import kr.or.houroffice.project.model.vo.ProjectBoard;
import kr.or.houroffice.project.model.vo.ProjectCode;
import kr.or.houroffice.project.model.vo.ProjectComment;
import kr.or.houroffice.project.model.vo.ProjectFavorite;
import kr.or.houroffice.project.model.vo.ProjectFileData;
import kr.or.houroffice.project.model.vo.ProjectMember;
import kr.or.houroffice.project.model.vo.ProjectPlan;
import kr.or.houroffice.project.model.vo.ProjectRequest;
import kr.or.houroffice.project.model.vo.ProjectWork;

@Controller
public class ProjectController {

	@Autowired
	@Qualifier(value = "projectService")
	private ProjectServiceImpl pService;

	@Autowired
	@Qualifier(value = "memberService")
	private MemberService mService;

	@RequestMapping(value = "/projectList.do")
	public String projectList() {
		return "project/projectList";
	}
	
	@Autowired
	ServletContext context;

	// 프로젝트 전체 리스트
	@RequestMapping(value = "/projectAllList.ho")
	public ModelAndView projectAllList(@SessionAttribute("member") Member member) {
		// 내가 참가한 프로젝트 가져오기
		ArrayList<Project> myProjectList = pService.selectMyProjectList(member.getMemNo());

		// 공개 프로젝트 정보 출력
		ArrayList<Project> publicAllList = pService.selectPublicProject();

		// 요청받은 프로젝트 목록
		ArrayList<Project> requestList = pService.selectProjectRequestMember(member.getMemNo());

		// 모든 인원
		ArrayList<Member> allMemberList = mService.selectAllMemberList();

		// 공개프로젝트 - 참가 프로젝트
		ArrayList<Project> publicList = new ArrayList<Project>();
		for (int i = 0; i < publicAllList.size(); i++) {
			Project p = publicAllList.get(i);
			for (Project mp : myProjectList) {
				if (mp.getProNo() == p.getProNo()) {
					publicAllList.remove(i);
				}
			}
		}

		// 즐겨찾는 목록 가져오기
		ArrayList<Project> favoriteList = pService.selectProjectFavoriteList(member.getMemNo());

		ModelAndView mav = new ModelAndView();
		mav.addObject("myList", myProjectList);
		mav.addObject("publicList", publicAllList);
		mav.addObject("favoriteList", favoriteList);
		mav.addObject("requestList", requestList);
		mav.addObject("allMemberList", allMemberList);
		mav.setViewName("project/projectAllList");
		return mav;
	}

	// 프로젝트 진행중 리스트
	@RequestMapping(value = "/projectProgressList.ho")
	public ModelAndView projectProgressList(@SessionAttribute("member") Member member) {
		// 내가 참가한 프로젝트 가져오기
		ArrayList<Project> myProjectList = pService.selectMyProjectList(member.getMemNo());

		// 공개 프로젝트 정보 출력
		ArrayList<Project> publicAllList = pService.selectPublicProject();

		// 요청받은 프로젝트 목록
		ArrayList<Project> requestList = pService.selectProjectRequestMember(member.getMemNo());

		// 모든 인원
		ArrayList<Member> allMemberList = mService.selectAllMemberList();

		// 공개프로젝트 - 참가 프로젝트
		ArrayList<Project> publicList = new ArrayList<Project>();
		for (int i = 0; i < publicAllList.size(); i++) {
			Project p = publicAllList.get(i);
			for (Project mp : myProjectList) {
				if (mp.getProNo() == p.getProNo()) {
					publicAllList.remove(i);
				}
			}
		}

		// 즐겨찾는 목록 가져오기
		ArrayList<Project> favoriteList = pService.selectProjectFavoriteList(member.getMemNo());

		ModelAndView mav = new ModelAndView();
		mav.addObject("myList", myProjectList);
		mav.addObject("publicList", publicAllList);
		mav.addObject("favoriteList", favoriteList);
		mav.addObject("requestList", requestList);
		mav.addObject("allMemberList", allMemberList);
		mav.setViewName("project/projectProgressList");
		return mav;
	}

	// 프로젝트 완료 리스트
	@RequestMapping(value = "/projectComplateList.ho")
	public ModelAndView projectComplateList(@SessionAttribute("member") Member member) {
		// 내가 참가한 프로젝트 가져오기
		ArrayList<Project> myProjectList = pService.selectMyProjectList(member.getMemNo());

		// 공개 프로젝트 정보 출력
		ArrayList<Project> publicAllList = pService.selectPublicProject();

		// 요청받은 프로젝트 목록
		ArrayList<Project> requestList = pService.selectProjectRequestMember(member.getMemNo());

		// 모든 인원
		ArrayList<Member> allMemberList = mService.selectAllMemberList();

		// 공개프로젝트 - 참가 프로젝트
		ArrayList<Project> publicList = new ArrayList<Project>();
		for (int i = 0; i < publicAllList.size(); i++) {
			Project p = publicAllList.get(i);
			for (Project mp : myProjectList) {
				if (mp.getProNo() == p.getProNo()) {
					publicAllList.remove(i);
				}
			}
		}

		// 즐겨찾는 목록 가져오기
		ArrayList<Project> favoriteList = pService.selectProjectFavoriteList(member.getMemNo());

		ModelAndView mav = new ModelAndView();
		mav.addObject("myList", myProjectList);
		mav.addObject("publicList", publicAllList);
		mav.addObject("favoriteList", favoriteList);
		mav.addObject("requestList", requestList);
		mav.addObject("allMemberList", allMemberList);
		mav.setViewName("project/projectComplateList");
		return mav;
	}

	// 프로젝트 완료 리스트
	@RequestMapping(value = "/projectLikeList.ho")
	public ModelAndView projectLikeList(@SessionAttribute("member") Member member) {
		// 내가 참가한 프로젝트 가져오기
		ArrayList<Project> myProjectList = pService.selectMyProjectList(member.getMemNo());

		// 공개 프로젝트 정보 출력
		ArrayList<Project> publicAllList = pService.selectPublicProject();

		// 요청받은 프로젝트 목록
		ArrayList<Project> requestList = pService.selectProjectRequestMember(member.getMemNo());

		// 모든 인원
		ArrayList<Member> allMemberList = mService.selectAllMemberList();

		// 공개프로젝트 - 참가 프로젝트
		ArrayList<Project> publicList = new ArrayList<Project>();
		for (int i = 0; i < publicAllList.size(); i++) {
			Project p = publicAllList.get(i);
			for (Project mp : myProjectList) {
				if (mp.getProNo() == p.getProNo()) {
					publicAllList.remove(i);
				}
			}
		}

		// 즐겨찾는 목록 가져오기
		ArrayList<Project> favoriteList = pService.selectProjectFavoriteList(member.getMemNo());

		ModelAndView mav = new ModelAndView();
		mav.addObject("myList", myProjectList);
		mav.addObject("publicList", publicAllList);
		mav.addObject("favoriteList", favoriteList);
		mav.addObject("requestList", requestList);
		mav.addObject("allMemberList", allMemberList);
		mav.setViewName("project/projectLikeList");
		return mav;
	}

	// 프로젝트 상세 페이지
	@RequestMapping(value = "/projectDetail.ho")
	public ModelAndView projectDetail(@RequestParam int proNo, @SessionAttribute("member") Member member,
			@RequestParam String boardType) {
		// 프로젝트 정보 가져오기
		Project p = pService.selectOneProject(proNo);

		// 프로젝트 게시물 가져오기
		ArrayList<ProjectBoard> boardList = pService.selectProjectBoardList(proNo);

		// 프로젝트 게시물의 댓글 가져오기
		ArrayList<ProjectComment> postCommentList = pService.selectBoardCommentList(proNo);

		// 프로젝트 게시물 작성자 정보 가져오기
		ArrayList<Member> boardMemberList = mService.selectProjectBoardMemberList(proNo);

		// 프로젝트 코드 작성자 정보 가져오기
		ArrayList<Member> codeMemberList = mService.selectProjectCodeMemberList(proNo);

		// 프로젝트 멤버 리스트 가져오기
		ArrayList<Member> projectMemberList = mService.selectProjectMemberList(proNo);
		
		// 프로젝트 관리자 목록 가져오기
		ArrayList<ProjectMember> projectMgrList = pService.selectProjectMemberList(proNo);

		// 즐겨찾기 목록 가져오기
		ArrayList<Project> favoriteList = pService.selectProjectFavoriteList(member.getMemNo());

		// 프로젝트 코드 게시물 리스트 가져오기
		ArrayList<ProjectCode> codeList = pService.selectProjectCodeList(proNo);

		// 프로젝트 코드 게시물 댓글 가져오기
		ArrayList<ProjectComment> codeCommentList = pService.selectCodeCommentList(proNo);

		// 프로젝트 일정 게시물 가져오기
		ArrayList<ProjectWork> projectWorkList = pService.selectProjectWorkList(proNo);

		// 프로젝트 일정 댓글 가져오기
		ArrayList<ProjectComment> workCommentList = pService.selectWorkCommentList(proNo);

		// 프로젝트 일정 작성자 정보 가져오기
		ArrayList<Member> workMemberList = mService.selectProjectWorkMemberList(proNo);

		// 프로젝트 파일 목록 가져오기
		ArrayList<ProjectFileData> fileList = pService.selectProjectFileList(proNo);

		// 프로젝트 작성자 정보 가져오기
		ArrayList<Member> fileMemberList = mService.selectProjectFileMemberList(proNo);

		// 멤버 전체 목록 가져오기
		ArrayList<Member> allMemberList = mService.selectAllMemberList();

		// 요청 멤버 번호 리스트 가져오기
		ArrayList<ProjectRequest> requestList = pService.selectProjectRequestList(proNo);

		// BY 진원 -일정 목록 가져오기
		ArrayList<ProjectPlan> planList = pService.selectPlanList(proNo);

		ModelAndView mav = new ModelAndView();
		mav.addObject("project", p);
		mav.addObject("boardList", boardList);
		mav.addObject("fileList", fileList);
		mav.addObject("boardMemberList", boardMemberList);
		mav.addObject("codeMemberList", codeMemberList);
		mav.addObject("projectMemberList", projectMemberList);
		mav.addObject("projectMgrList", projectMgrList);
		mav.addObject("postCommentList", postCommentList);
		mav.addObject("codeCommentList", codeCommentList);
		mav.addObject("projectWorkList", projectWorkList);
		mav.addObject("workCommentList", workCommentList);
		mav.addObject("workMemberList", workMemberList);
		mav.addObject("fileMemberList", fileMemberList);
		mav.addObject("allMemberList", allMemberList);
		mav.addObject("favoriteList", favoriteList);
		mav.addObject("requestList", requestList);
		mav.addObject("codeList", codeList);
		mav.addObject("boardType", boardType);
		mav.setViewName("project/projectDetail");
		mav.addObject("planList", planList);

		return mav;
	}

	// 프로젝트 게시물 작성 페이지
	@RequestMapping(value = "/projectBoardWrite.ho")
	public ModelAndView projectBoardWrite(@RequestParam int proNo, @SessionAttribute("member") Member member) {

		// 프로젝트 정보 가져오기
		Project p = pService.selectOneProject(proNo);
		// 멤버 전체 목록 가져오기
		ArrayList<Member> allMemberList = mService.selectAllMemberList();

		// 요청 멤버 번호 리스트 가져오기
		ArrayList<ProjectRequest> requestList = pService.selectProjectRequestList(proNo);

		// 프로젝트 게시물 가져오기
		ArrayList<ProjectBoard> boardList = pService.selectProjectBoardList(proNo);

		// 프로젝트 게시물의 댓글 가져오기
		ArrayList<ProjectComment> commentList = pService.selectBoardCommentList(proNo);

		// 프로젝트 게시물 작성자 정보 가져오기
		ArrayList<Member> boardMemberList = mService.selectProjectBoardMemberList(proNo);

		// 프로젝트 멤버 리스트 가져오기
		ArrayList<Member> projectMemberList = mService.selectProjectMemberList(proNo);

		// 프로젝트 관리자 목록 가져오기
		ArrayList<ProjectMember> projectMgrList = pService.selectProjectMemberList(proNo);

		// 즐겨찾기 목록 가져오기
		ArrayList<Project> favoriteList = pService.selectProjectFavoriteList(member.getMemNo());

		ModelAndView mav = new ModelAndView();
		mav.addObject("project", p);
		mav.addObject("boardList", boardList);
		mav.addObject("boardMemberList", boardMemberList);
		mav.addObject("projectMemberList", projectMemberList);
		mav.addObject("projectMgrList", projectMgrList);
		mav.addObject("commentList", commentList);
		mav.addObject("favoriteList", favoriteList);
		mav.addObject("allMemberList", allMemberList);
		mav.addObject("requestList", requestList);
		mav.setViewName("project/projectBoardWrite");
		return mav;
	}

	// 새프로젝트 만들기
	@RequestMapping(value = "/createProject.ho")
	public ModelAndView createProject(@RequestParam String proSubject, @RequestParam String proExp,
			@RequestParam String publicYN, HttpSession session) {

		Project p = new Project();
		p.setProSubject(proSubject);
		p.setProExp(proExp);
		if (publicYN.equals("on")) {
			publicYN = "Y";
		} else {
			publicYN = "N";
		}

		p.setPublicYN(publicYN.charAt(0));
		Member m = (Member) session.getAttribute("member");

		p.setMemNo(m.getMemNo());

		int result = pService.insertProject(p);
		ModelAndView mav = new ModelAndView();
		if (result > 0) {
			// 생성한 프로젝트의 넘버가져오기
			Project project = pService.selectOneProjectSubject(p.getProSubject());
			ProjectMember pm = new ProjectMember();
			pm.setProNo(project.getProNo());
			pm.setMemNo(m.getMemNo());

			// 프로젝트 멤버 테이블에 추가
			int insertMemberResult = pService.insertProjectMemberAdmin(pm);
			if (insertMemberResult > 0) {
				System.out.println("프로젝트 생성자 멤버 테이블 추가 완료");
			} else {
				System.out.println("프로젝트 생성자 멤버 테이블 추가 실패");
			}
			mav.addObject("msg", "프로젝트 생성 완료");
		} else {
			mav.addObject("msg", "프로젝트 생성 실패");
		}
		mav.addObject("location", "/projectAllList.ho");
		mav.setViewName("result");

		return mav;
	}

	// 일반 게시글 올리기
	@RequestMapping(value = "/insertProjectBoard.ho")
	public ModelAndView insertProjectBoard(HttpServletRequest request, @SessionAttribute("member") Member m) throws Exception {
		int memNo = m.getMemNo();
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;

		
		int proNo = Integer.parseInt(multi.getParameter("proNo"));
		String boardText = multi.getParameter("boardText");
		if(boardText.equals("")){
			boardText=" ";
		}
		MultipartFile file = multi.getFile("imgFile");
		String changedImgFileName = "";
		String path = "";
		UUID randomeUUID = UUID.randomUUID();
		String organizedfilePath = "";
		if (file != null) {

			System.out.println("파라미터명" + file.getName());
			System.out.println("파일크기" + file.getSize());
			System.out.println("파일 존재" + file.isEmpty());
			System.out.println("오리지날 파일 이름" + file.getOriginalFilename());

			// 파일이 업로드 되는 경로
			path = context.getRealPath("/");
			path = path.replace("\\target\\m2e-wtp\\web-resources", "");
			String uploadPath = "src\\main\\webapp\\resources\\file\\project\\";
			path = path + uploadPath;

			System.out.println(path);

			InputStream inputStream = null;
			OutputStream outputStream = null;

			if (file.getSize() > 0) {
				inputStream = file.getInputStream();
				File realUploadDir = new File(path);

				if (!realUploadDir.exists()) {
					realUploadDir.mkdirs();// 폴더생성.
				}

				organizedfilePath = path + randomeUUID + "_" + file.getOriginalFilename();
				System.out.println(organizedfilePath);// 파일이 저장된경로 + 파일 명

				outputStream = new FileOutputStream(organizedfilePath);

				int readByte = 0;
				byte[] buffer = new byte[8192];

				while ((readByte = inputStream.read(buffer, 0, 8120)) != -1) {
					outputStream.write(buffer, 0, readByte); // 파일 생성 !
				}
			}

		}
		// 위의 코드까지 하면 파일 업로드는 완료
		if(file.getSize() > 0){
			
			// 서버에 실제로 업로드 된 파일이름 가져오기
			String originalImgFileName = file.getOriginalFilename();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // 포멧만들기
			long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
			Timestamp uploadTime = Timestamp.valueOf(formatter.format(currentTime));
			
			// 파일 리네임
			File fileSave = new File(organizedfilePath); // 파일 연결
			File copyFile = new File(path+"\\"+currentTime+"_ho");
			changedImgFileName = currentTime+"_ho"; // DB에 저장할 파일 이름
	
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
			File reNameFile = new File(path+"\\"+changedImgFileName); // 이름이 바뀌여 다시 연결해줌
			String filePath = reNameFile.getPath(); // 경로
			// 해당 업로드된 file의 사이즈
			long fileSize = reNameFile.length();
			
			System.out.println(changedImgFileName);
			ProjectFileData pfd = new ProjectFileData();
			pfd.setProNo(proNo);
			pfd.setMemNo(memNo);
			pfd.setOriginalFileName(originalImgFileName);
			pfd.setChangedFileName(changedImgFileName);
			pfd.setFilePath(filePath);
			pfd.setFileSize(fileSize);
			pfd.setUploadTime(uploadTime);
			int resultFile = pService.insertProjectBoardFile(pfd);
			
		} // 파일 null이 아니면 디비 저장 if 문
		
		//두번째 파일 업로드
		file = multi.getFile("file");
		path = "";
		randomeUUID = UUID.randomUUID();
		organizedfilePath = "";
		if (file != null) {

			System.out.println("파라미터명" + file.getName());
			System.out.println("파일크기" + file.getSize());
			System.out.println("파일 존재" + file.isEmpty());
			System.out.println("오리지날 파일 이름" + file.getOriginalFilename());

			// 파일이 업로드 되는 경로
			path = context.getRealPath("/");
			path = path.replace("\\target\\m2e-wtp\\web-resources", "");
			String uploadPath = "src\\main\\webapp\\resources\\file\\project\\";
			path = path + uploadPath;

			System.out.println(path);

			InputStream inputStream = null;
			OutputStream outputStream = null;

			if (file.getSize() > 0) {
				inputStream = file.getInputStream();
				File realUploadDir = new File(path);

				if (!realUploadDir.exists()) {
					realUploadDir.mkdirs();// 폴더생성.
				}

				organizedfilePath = path + randomeUUID + "_" + file.getOriginalFilename();
				System.out.println(organizedfilePath);// 파일이 저장된경로 + 파일 명

				outputStream = new FileOutputStream(organizedfilePath);

				int readByte = 0;
				byte[] buffer = new byte[8192];

				while ((readByte = inputStream.read(buffer, 0, 8120)) != -1) {
					outputStream.write(buffer, 0, readByte); // 파일 생성 !
				}
			}

		}
		
		if(file.getSize() > 0){
			
			// 서버에 실제로 업로드 된 파일이름 가져오기
			String originalFileName = file.getOriginalFilename();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // 포멧만들기
			long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
			Timestamp uploadTime = Timestamp.valueOf(formatter.format(currentTime));
			
			// 파일 리네임
			File fileSave = new File(organizedfilePath); // 파일 연결
			File copyFile = new File(path+"\\"+currentTime+"_ho");
			String changedFileName = currentTime+"_ho"; // DB에 저장할 파일 이름
	
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
			
			ProjectFileData pfd = new ProjectFileData();
			pfd.setProNo(proNo);
			pfd.setMemNo(memNo);
			pfd.setOriginalFileName(originalFileName);
			pfd.setChangedFileName(changedFileName);
			pfd.setFilePath(filePath);
			pfd.setFileSize(fileSize);
			pfd.setUploadTime(uploadTime);
			int resultFile = pService.insertProjectBoardFile(pfd);
			
		}
		
		
		ProjectBoard pb = new ProjectBoard();
		pb.setProNo(proNo);
		pb.setBoardText(boardText);
		pb.setImgName(changedImgFileName);
		pb.setMemNo(m.getMemNo());
		int result = pService.insertProjectBoard(pb);
		System.out.println(boardText);
		ModelAndView mav = new ModelAndView();
		
		if (result > 0) {
			mav.addObject("msg", "게시물 작성 완료");
		} else {
			mav.addObject("msg", "게시물 작성 실패");
		}
		mav.addObject("proNo", pb.getProNo());
		mav.addObject("boardType", "post");
		mav.setViewName("project/projectDetailResult");
		return mav;

	}

	// 프로젝트 즐겨찾기 등록 ajax
	@RequestMapping(value = "/insertProjectFavor.ho")
	public void insertProjectFavor(ProjectFavorite pf, HttpServletResponse response) throws IOException {

		int result = pService.insertProjectFavor(pf);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}

	}

	// 프로젝트 즐겨찾기 삭제 ajax
	@RequestMapping(value = "/deleteProjectFavor.ho")
	public void deleteProjectFavor(ProjectFavorite pf, HttpServletResponse response) throws IOException {

		int result = pService.deleteProjectFavor(pf);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}

	}

	// 게시물 댓글 작성
	@RequestMapping(value = "/insertBoardComment.ho")
	public ModelAndView insertBoardComment(ProjectComment pc, @RequestParam int proNo, @RequestParam String boardType) {
		
		if(pc.getCommentCon().equals("")){
			pc.setCommentCon("&nbsp;");
		}
		int result = pService.insertBoardComment(pc);
		ModelAndView mav = new ModelAndView();
		if (result > 0) {
			System.out.println("댓글 작성 성공");
		} else {
			System.out.println("댓글 작성 실패");
		}
		mav.addObject("proNo", proNo);
		mav.addObject("boardType", boardType);
		mav.setViewName("project/commentResult");

		return mav;
	}

	// 게시물 댓글 수정
	@RequestMapping(value = "/updateProjectComment.ho")
	public ModelAndView updateProjectComment(ProjectComment pc, @RequestParam int proNo,
			@RequestParam String boardType) {

		int result = pService.updateProjectComment(pc);
		ModelAndView mav = new ModelAndView();
		if (result > 0) {
			System.out.println("댓글 수정 성공");
		} else {
			System.out.println("댓글 수정 실패");
		}
		mav.addObject("proNo", proNo);
		mav.addObject("boardType", boardType);
		mav.setViewName("project/commentResult");

		return mav;
	}

	// 게시물 댓글 삭제
	@RequestMapping(value = "/deleteProjectComment.ho")
	public void deleteProjectComment(@RequestParam int commentNo, HttpServletResponse response) throws IOException {

		int result = pService.deleteProjectComment(commentNo);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 프로젝트 수정
	@RequestMapping(value = "/updateProject.ho")
	public ModelAndView updateProject(@RequestParam String proSubject, @RequestParam String proExp,
			@RequestParam String publicYN, @RequestParam int proNo, @RequestParam int memNo, HttpSession session) {
		Member m = (Member) session.getAttribute("member");
		Project p = new Project();

		ModelAndView mav = new ModelAndView();
		// 세션과 프로젝트 생성자가 같다면
		if (m.getMemNo() == memNo) {
			if (publicYN.equals("on")) {
				publicYN = "Y";
			} else {
				publicYN = "N";
			}
			p.setProSubject(proSubject);
			p.setProExp(proExp);
			p.setPublicYN(publicYN.charAt(0));
			p.setMemNo(m.getMemNo());
			p.setProNo(proNo);
			int result = pService.updateProject(p);
			if (result > 0) {
				mav.addObject("msg", "프로젝트 수정 완료");
			} else {
				mav.addObject("msg", "프로젝트 수정 실패");
			}
			mav.addObject("proNo", proNo);
			mav.addObject("boardType", "post");
			mav.setViewName("project/projectDetailResult");
		} else {
			mav.addObject("msg", "프로젝트 생성자만 수정이 가능합니다.");
			mav.addObject("proNo", proNo);
			mav.addObject("boardType", "post");
			mav.setViewName("project/projectDetailResult");
		}
		return mav;
	}

	// 게시물 수정
	@RequestMapping(value = "/updateProjectBoard.ho")
	public ModelAndView updateProjectBoard(ProjectBoard pb) {

		System.out.println(pb);
		int result = pService.updateProjectBoard(pb);
		ModelAndView mav = new ModelAndView();

		if (result > 0) {
			mav.addObject("msg", "게시물 수정 완료");
		} else {
			mav.addObject("msg", "게시물 수정 실패");
		}
		mav.addObject("proNo", pb.getProNo());
		mav.addObject("boardType", "post");
		mav.setViewName("project/projectDetailResult");
		return mav;
	}

	// 게시물 삭제
	@RequestMapping(value = "/deleteProjectBoard.ho")
	public void deleteProjectBoard(@RequestParam int boardNo, HttpServletResponse response) throws IOException {

		int result = pService.deleteProjectBoard(boardNo);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 프로젝트 나가기
	@RequestMapping(value = "/updateProjectMemberExit.ho")
	public void updateProjectMemberExit(ProjectMember pm, HttpServletResponse response) throws IOException {

		// 프로젝트 참가 수 감소
		Project pro = pService.selectOneProject(pm.getProNo());
		pro.setMemCount((pro.getMemCount() - 1));
		
		int result = pService.updateProjectMemberExit(pm);
		Project p = pService.selectOneProject(pm.getProNo());
		if (result > 0) {
			int memCount = pro.getMemCount();
			p.setMemCount(memCount);
			pService.updateProjectMemberCount(p);
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 프로젝트 삭제
	@RequestMapping(value = "/deleteProject.ho")
	public void updateProject(@RequestParam int proNo, HttpServletResponse response) throws IOException {
		int result = pService.deleteProject(proNo);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 관리자 설정
	@RequestMapping(value = "/updateProjectMgrSet.ho")
	public void updateProjectMgrSet(ProjectMember pm, HttpServletResponse response) throws IOException {
		int result = pService.updateProjectMgrSet(pm);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 관리자 설정 해제
	@RequestMapping(value = "/updateProjectMgrCancel.ho")
	public void updateProjectMgrCancel(ProjectMember pm, HttpServletResponse response) throws IOException {
		int result = pService.updateProjectMgrCancel(pm);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 일정 게시물 올리기
	@RequestMapping(value = "/insertProjectPlan.ho")
	public ModelAndView insertProjectPlan(ProjectPlan pp) {
		int result = pService.insertProjectPlan(pp);
		ModelAndView mav = new ModelAndView();

		if (result > 0) {
			mav.addObject("msg", "일정 작성 완료");
		} else {
			mav.addObject("msg", "일정 작성 실패");
		}
		mav.addObject("proNo", pp.getProNo());
		mav.addObject("boardType", "plan");
		mav.setViewName("project/projectDetailResult");
		return mav;

	}

	@RequestMapping(value = "/updateProjectComplate.ho")
	public void updateProjectComplate(Project p, HttpServletResponse response) throws IOException {

		if (p.getCompYN() == 'Y') {
			p.setCompYN('N');
		} else {
			p.setCompYN('Y');
		}

		int result = pService.updateProjectComplate(p);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	@RequestMapping(value="/insertProjectCode.ho")
	public ModelAndView insertProjectCode(HttpServletRequest request, HttpSession session, @SessionAttribute("member") Member m)throws Exception {
		
		int memNo = m.getMemNo();
		MultipartHttpServletRequest multi = (MultipartHttpServletRequest) request;

		
		int proNo = Integer.parseInt(multi.getParameter("proNo"));
		String boardText = multi.getParameter("boardText");
		String codeText = multi.getParameter("codeText");
		if(boardText.equals("")){
			boardText=" ";
		}
		if(codeText.equals("")){
			boardText=" ";
		}
		MultipartFile file = multi.getFile("imgFile");
		String changedImgFileName = "";
		String path = "";
		UUID randomeUUID = UUID.randomUUID();
		String organizedfilePath = "";
		if (file != null) {

			System.out.println("파라미터명" + file.getName());
			System.out.println("파일크기" + file.getSize());
			System.out.println("파일 존재" + file.isEmpty());
			System.out.println("오리지날 파일 이름" + file.getOriginalFilename());

			// 파일이 업로드 되는 경로
			path = context.getRealPath("/");
			path = path.replace("\\target\\m2e-wtp\\web-resources", "");
			String uploadPath = "src\\main\\webapp\\resources\\file\\project\\";
			path = path + uploadPath;

			System.out.println(path);

			InputStream inputStream = null;
			OutputStream outputStream = null;

			if (file.getSize() > 0) {
				inputStream = file.getInputStream();
				File realUploadDir = new File(path);

				if (!realUploadDir.exists()) {
					realUploadDir.mkdirs();// 폴더생성.
				}

				organizedfilePath = path + randomeUUID + "_" + file.getOriginalFilename();
				System.out.println(organizedfilePath);// 파일이 저장된경로 + 파일 명

				outputStream = new FileOutputStream(organizedfilePath);

				int readByte = 0;
				byte[] buffer = new byte[8192];

				while ((readByte = inputStream.read(buffer, 0, 8120)) != -1) {
					outputStream.write(buffer, 0, readByte); // 파일 생성 !
				}
			}

		}
		// 위의 코드까지 하면 파일 업로드는 완료
		if(file.getSize() > 0){
			
			// 서버에 실제로 업로드 된 파일이름 가져오기
			String originalImgFileName = file.getOriginalFilename();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // 포멧만들기
			long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
			Timestamp uploadTime = Timestamp.valueOf(formatter.format(currentTime));
			
			// 파일 리네임
			File fileSave = new File(organizedfilePath); // 파일 연결
			File copyFile = new File(path+"\\"+currentTime+"_ho");
			changedImgFileName = currentTime+"_ho"; // DB에 저장할 파일 이름
			
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
			File reNameFile = new File(path+"\\"+changedImgFileName); // 이름이 바뀌여 다시 연결해줌
			String filePath = reNameFile.getPath(); // 경로
			// 해당 업로드된 file의 사이즈
			long fileSize = reNameFile.length();
			
			System.out.println(changedImgFileName);
			ProjectFileData pfd = new ProjectFileData();
			pfd.setProNo(proNo);
			pfd.setMemNo(memNo);
			pfd.setOriginalFileName(originalImgFileName);
			pfd.setChangedFileName(changedImgFileName);
			pfd.setFilePath(filePath);
			pfd.setFileSize(fileSize);
			pfd.setUploadTime(uploadTime);
			pService.insertProjectBoardFile(pfd);
			
		} // 파일 null이 아니면 디비 저장 if 문
		
		//두번째 파일 업로드
		file = multi.getFile("file");
		path = "";
		randomeUUID = UUID.randomUUID();
		organizedfilePath = "";
		if (file != null) {

			System.out.println("파라미터명" + file.getName());
			System.out.println("파일크기" + file.getSize());
			System.out.println("파일 존재" + file.isEmpty());
			System.out.println("오리지날 파일 이름" + file.getOriginalFilename());

			// 파일이 업로드 되는 경로
			path = context.getRealPath("/");
			path = path.replace("\\target\\m2e-wtp\\web-resources", "");
			String uploadPath = "src\\main\\webapp\\resources\\file\\project\\";
			path = path + uploadPath;

			System.out.println(path);

			InputStream inputStream = null;
			OutputStream outputStream = null;

			if (file.getSize() > 0) {
				inputStream = file.getInputStream();
				File realUploadDir = new File(path);

				if (!realUploadDir.exists()) {
					realUploadDir.mkdirs();// 폴더생성.
				}

				organizedfilePath = path + randomeUUID + "_" + file.getOriginalFilename();
				System.out.println(organizedfilePath);// 파일이 저장된경로 + 파일 명

				outputStream = new FileOutputStream(organizedfilePath);

				int readByte = 0;
				byte[] buffer = new byte[8192];

				while ((readByte = inputStream.read(buffer, 0, 8120)) != -1) {
					outputStream.write(buffer, 0, readByte); // 파일 생성 !
				}
			}

		}
		
		if(file.getSize() > 0){
			
			// 서버에 실제로 업로드 된 파일이름 가져오기
			String originalFileName = file.getOriginalFilename();
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); // 포멧만들기
			long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
			Timestamp uploadTime = Timestamp.valueOf(formatter.format(currentTime));
			
			// 파일 리네임
			File fileSave = new File(organizedfilePath); // 파일 연결
			File copyFile = new File(path+"\\"+currentTime+"_ho");
			String changedFileName = currentTime+"_ho"; // DB에 저장할 파일 이름
	
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
			
			ProjectFileData pfd = new ProjectFileData();
			pfd.setProNo(proNo);
			pfd.setMemNo(memNo);
			pfd.setOriginalFileName(originalFileName);
			pfd.setChangedFileName(changedFileName);
			pfd.setFilePath(filePath);
			pfd.setFileSize(fileSize);
			pfd.setUploadTime(uploadTime);
			pService.insertProjectBoardFile(pfd);
			
		}
		
		String[] pTag = codeText.split("\n");
		for (int i = 0; i < pTag.length; i++) {

			Pattern pattern2 = Pattern.compile("[<][a-zA-Z]+");
			Matcher matcher2 = pattern2.matcher(pTag[i]);
			while (matcher2.find()) {
				String method2 = matcher2.group().substring(0, 1);
				pTag[i] = pTag[i].replace(method2, "< ");
			}
			
			
			
			pTag[i] = pTag[i].replace("	", "&nbsp;&nbsp;&nbsp;&nbsp;");
			
			pTag[i] = pTag[i].replace("class", "<span class=\"codeYellow\">class</span>");

			pTag[i] = pTag[i].replace("package", "<span class=\"codeYellow\">package</span>");
			pTag[i] = pTag[i].replace("public", "<span class=\"codeYellow\">public</span>");
			pTag[i] = pTag[i].replace("void", "<span class=\"codeYellow\">void</span>");
			pTag[i] = pTag[i].replace("this", "<span class=\"codeYellow\">this</span>");
			pTag[i] = pTag[i].replace("super", "<span class=\"codeYellow\">super</span>");
			pTag[i] = pTag[i].replace("implements", "<span class=\"codeYellow\">implements</span>");
			pTag[i] = pTag[i].replace("while", "<span class=\"codeYellow\">while</span>");
			pTag[i] = pTag[i].replace("private", "<span class=\"codeYellow\">private</span>");
			pTag[i] = pTag[i].replace("switch", "<span class=\"codeYellow\">switch</span>");
			pTag[i] = pTag[i].replace("case", "<span class=\"codeYellow\">case</span>");
			pTag[i] = pTag[i].replace("new", "<span class=\"codeYellow\">new</span>");
			pTag[i] = pTag[i].replace("break", "<span class=\"codeYellow\">break</span>");
			pTag[i] = pTag[i].replace("default", "<span class=\"codeYellow\">default</span>");
			pTag[i] = pTag[i].replace("for", "<span class=\"codeYellow\">for</span>");
			pTag[i] = pTag[i].replace("return", "<span class=\"codeYellow\">return</span>");
			pTag[i] = pTag[i].replace("import", "<span class=\"codeYellow\">import</span>");
			pTag[i] = pTag[i].replace("else", "<span class=\"codeYellow\">else</span>");
			pTag[i] = pTag[i].replace("if", "<span class=\"codeYellow\">if</span>");
			pTag[i] = pTag[i].replace("try", "<span class=\"codeYellow\">try</span>");
			pTag[i] = pTag[i].replace("catch", "<span class=\"codeYellow\">catch</span>");
			pTag[i] = pTag[i].replace("finally", "<span class=\"codeYellow\">finally</span>");
			pTag[i] = pTag[i].replace("close", "<span class=\"codeYellow\">close</span>");

			pTag[i] = pTag[i].replace("true", "<span class=\"codeRed\">true</span>");
			pTag[i] = pTag[i].replace("false", "<span class=\"codeRed\">false</span>");
			
			Pattern pattern = Pattern.compile("[a-zA-Z0-9]+[(]");
			Matcher matcher = pattern.matcher(pTag[i]);
			while (matcher.find()) {
				String method = matcher.group().substring(0, matcher.group().length() - 1);
				pTag[i] = pTag[i].replace(method, "<span class=\"codeBlue\">" + method + "</span>");
			}
			
			Pattern pattern3 = Pattern.compile("[/][/][a-zA-Z0-9]+");
			Matcher matcher3 = pattern3.matcher(pTag[i]);
			while (matcher3.find()) {
				String method3 = matcher3.group().substring(0,  matcher3.group().length());
				pTag[i] = pTag[i].replace(method3, "<span class=\"codeGray\">" + method3 + "</span>");
			}

			pTag[i] = "<pre class=\"codeLineNumber" + i + "\"><div class=\"codeLine\">"+(i+1)+"</div>" + pTag[i] + "</pre>";

			if (i == 0) {
				codeText = pTag[i];
			} else {
				codeText += pTag[i];
			}
		}
		ProjectCode pc = new ProjectCode();
		pc.setProNo(proNo);
		pc.setMemNo(m.getMemNo());
		pc.setCodeText(codeText);
		pc.setBoardText(boardText);
		pc.setImgName(changedImgFileName);
		int result = pService.insertProjectCode(pc);
		System.out.println(boardText);
		ModelAndView mav = new ModelAndView();
		if(result>0){
			mav.addObject("msg", "게시물 작성 완료");
		}else{
			mav.addObject("msg", "게시물 작성 실패");
		}
		mav.addObject("proNo", pc.getProNo());
		mav.addObject("boardType", "code");
		mav.setViewName("project/projectDetailResult");
		return mav;
	}

	// 코드 게시물 수정
	@RequestMapping(value = "/updateProjectCode.ho")
	public ModelAndView updateProjectCode(ProjectCode pc) {

		int result = pService.updateProjectCode(pc);
		ModelAndView mav = new ModelAndView();

		if (result > 0) {
			mav.addObject("msg", "게시물 수정 완료");
		} else {
			mav.addObject("msg", "게시물 수정 실패");
		}
		mav.addObject("proNo", pc.getProNo());
		mav.addObject("boardType", "code");
		mav.setViewName("project/projectDetailResult");
		return mav;
	}

	// 코드 게시물 삭제
	@RequestMapping(value = "/deleteProjectCode.ho")
	public void deleteProjectCode(@RequestParam int boardNo, HttpServletResponse response) throws IOException {

		int result = pService.deleteProjectCode(boardNo);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 할일 작성
	@RequestMapping(value = "/insertProjectWork.ho")
	public ModelAndView insertProjectWork(ProjectWork pw, HttpSession session) {
		
		int result = pService.insertProjectWork(pw);
		ModelAndView mav = new ModelAndView();
		if (result > 0) {
			mav.addObject("msg", "게시물 작성 성공");
		} else {
			mav.addObject("msg", "게시물 작성 실패");
		}
		mav.addObject("proNo", pw.getProNo());
		mav.addObject("boardType", "work");
		mav.setViewName("project/projectDetailResult");

		return mav;

	}

	// 일정 게시물 수정
	@RequestMapping(value = "/updateProjectWork.ho")
	public ModelAndView updateProjectWork(ProjectWork pw) {

		int result = pService.updateProjectWork(pw);
		ModelAndView mav = new ModelAndView();

		if (result > 0) {
			mav.addObject("msg", "게시물 수정 완료");
		} else {
			mav.addObject("msg", "게시물 수정 실패");
		}
		mav.addObject("proNo", pw.getProNo());
		mav.addObject("boardType", "work");
		mav.setViewName("project/projectDetailResult");
		return mav;
	}

	// 할일 게시물 삭제
	@RequestMapping(value = "/deleteProjectWork.ho")
	public void deleteProjectWork(@RequestParam int boardNo, HttpServletResponse response) throws IOException {

		int result = pService.deleteProjectWork(boardNo);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 할일 체크
	@RequestMapping(value = "/updateProjectWorkCheck.ho")
	public void updateProjectWorkCheck(@RequestParam int workNo, @RequestParam String text,
			@RequestParam String workCon, @RequestParam int max, @RequestParam int workComp, HttpSession session,
			HttpServletResponse response) throws IOException {
		Member m = (Member) session.getAttribute("member");

		String commentCon = "'" + text + "' 항목 완료!";
		ProjectComment pc = new ProjectComment();
		pc.setBoardNo(workNo);
		pc.setMemNo(m.getMemNo());
		pc.setCommentCon(commentCon);
		pService.insertBoardComment(pc);

		workCon = workCon.replace(text, text + "Checked");
		workComp = workComp + 1;

		ProjectWork pw = new ProjectWork();
		pw.setWorkNo(workNo);
		pw.setWorkCon(workCon);
		pw.setWorkComp(workComp);
		int result = pService.updateProjectWorkCheck(pw);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 할일 체크 해제
	@RequestMapping(value = "/updateProjectWorkUnCheck.ho")
	public void updateProjectWorkUnCheck(@RequestParam int workNo, @RequestParam String text,
			@RequestParam String workCon, @RequestParam int max, @RequestParam int workComp, HttpSession session,
			HttpServletResponse response) throws IOException {
		Member m = (Member) session.getAttribute("member");

		String commentCon = "'" + text + "' 항목 완료 취소.";
		ProjectComment pc = new ProjectComment();
		pc.setBoardNo(workNo);
		pc.setMemNo(m.getMemNo());
		pc.setCommentCon(commentCon);
		pService.insertBoardComment(pc);

		workCon = workCon.replace(text + "Checked", text);
		workComp = workComp - 1;

		ProjectWork pw = new ProjectWork();
		pw.setWorkNo(workNo);
		pw.setWorkCon(workCon);
		pw.setWorkComp(workComp);

		int result = pService.updateProjectWorkCheck(pw);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 프로젝트 파일 다운로드
	@RequestMapping(value = "/projectFileDownload.ho")
	public void projectFileDownload(@RequestParam int fileNo, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		ProjectFileData pfd = pService.selectOneProjectFile(fileNo);
		if (pfd != null) {

			File file = new File(pfd.getFilePath());

			// 웹 브라우저를 통해 문자열(String)이 아닌 데이터가 전송되려면 Binary 타입으로 처리해야 함
			response.setContentType("application/octet-stream");

			// 파일의 사이즈를 전달해주어야 함
			response.setContentLength((int) pfd.getFileSize());

			// 사용자에게 전달할 파일의 이름을 인코딩 해주어야함
			// 이때,. 파일은 해당 컴퓨터의 OS 포맷에 맞게 인코딩해주어야함
			// windows는 기본적으로 ISO-8859-1
			String fileName = new String(pfd.getOriginalFileName().getBytes(), "ISO-8859-1");

			// 파일 이름을 http header를 통해서 전달
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

			// 위 코드까지는 파일이름 + 파일을 전송할 수 있는 웹 환경을 셋팅 했다고 보면 됨
			// 아래 코드부터는 실제 파일이 가지고 있는 데이터들을 보내는 작업

			// 해당되는 파일의 데이터를 가져올 수 있는 통로(InputStream 생성)
			FileInputStream fileIn = new FileInputStream(file);

			// 클라이언트에게 데이터를 전달할 통로 생성 (outputStream 생성)
			ServletOutputStream out = response.getOutputStream();

			// 4KByte 씩 처리
			byte[] outputByte = new byte[4096];

			// InputStream으로 데이터를 읽어다가 output 스트림으로 전송하기
			while (fileIn.read(outputByte, 0, 4096) != -1) {
				out.write(outputByte, 0, 4096);
			}
			fileIn.close();
			out.close();
		} else {

		}
	}

	// 파일 삭제
	@RequestMapping(value = "/deleteProjectFile.ho")
	public void deleteProjectFile(@RequestParam int fileNo, HttpServletResponse response) throws IOException {

		int result = pService.deleteProjectFile(fileNo);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 프로젝트 초대 요청
	@RequestMapping(value = "/insertProjectRequest.ho")
	public void insertProjectRequest(@RequestParam int memNo, @RequestParam int proNo, HttpSession session,
			HttpServletResponse response) throws IOException {
		Member m = (Member) session.getAttribute("member");
		ProjectRequest pr = new ProjectRequest();
		pr.setProNo(proNo);
		pr.setRequestMem(m.getMemNo());
		pr.setResponseMem(memNo);
		int result = pService.insertProjectRequest(pr);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 프로젝트 초대 요청 취소
	@RequestMapping(value = "/deleteProjectRequest.ho")
	public void deleteProjectRequest(@RequestParam int memNo, @RequestParam int proNo, HttpSession session,
			HttpServletResponse response) throws IOException {
		Member m = (Member) session.getAttribute("member");
		ProjectRequest pr = new ProjectRequest();
		pr.setProNo(proNo);
		pr.setResponseMem(memNo);
		int result = pService.deleteProjectRequest(pr);
		if (result > 0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}

	// 프로젝트 참가하기
	@RequestMapping(value = "/projectJoin.ho")
	public ModelAndView projectJoin(@RequestParam int proNo, HttpSession session) {
		// 프로젝트 참가수 인원 늘리기
		Project p = pService.selectOneProject(proNo);
		int memCount = p.getMemCount() + 1;
		p.setMemCount(memCount);
		pService.updateProjectMemberCount(p);

		Member m = (Member) session.getAttribute("member");
		// Request delete
		ProjectRequest pr = new ProjectRequest();
		pr.setProNo(proNo);
		pr.setResponseMem(m.getMemNo());
		int result = pService.deleteProjectRequest(pr);

		// 프로젝트 멤버 추가
		ProjectMember pm = new ProjectMember();
		pm.setMemNo(m.getMemNo());
		pm.setProNo(proNo);
		int projectResult = pService.insertProjectMember(pm);

		ModelAndView mav = new ModelAndView();
		if (result > 0 && projectResult > 0) {
			mav.addObject("msg", "프로젝트 참가 완료");
		} else {
			mav.addObject("msg", "프로젝트 참가 실패");
		}
		mav.addObject("location", "/projectAllList.ho");
		mav.setViewName("result");

		return mav;
	}

	// 프로젝트 참가하기
	@RequestMapping(value = "/projectRefusal.ho")
	public ModelAndView projectRefusal(@RequestParam int proNo, HttpSession session) {

		Member m = (Member) session.getAttribute("member");
		// Request delete
		ProjectRequest pr = new ProjectRequest();
		pr.setProNo(proNo);
		pr.setResponseMem(m.getMemNo());
		int result = pService.deleteProjectRequest(pr);

		ModelAndView mav = new ModelAndView();
		if (result > 0) {
			mav.addObject("msg", "해당 프로젝트의 참가를 거절하였습니다.");
		} else {
			mav.addObject("msg", "프롸젝트 거절 실패");
		}
		mav.addObject("location", "/projectAllList.ho");
		mav.setViewName("result");
		return mav;
	}
}
