package kr.or.houroffice.admin.controller;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

import kr.or.houroffice.admin.model.service.AdminService;
import kr.or.houroffice.admin.model.vo.AdminBoard;
import kr.or.houroffice.member.model.service.AdminMemberService;
import kr.or.houroffice.member.model.vo.Member;

@Controller
public class AdminController {
	
	@Resource(name="adminService")
	private AdminService aService;
	
	@Resource(name="adminMemberService")
	private AdminMemberService mService;

	//관리자 페이지 입장
	@RequestMapping(value="/adminMainPage.ho")
	public String adminMainPage(Model model) throws IOException {
		int expireMemberCount = aService.expireDeleteMember();//데이터/문서 관리 - 경과된 사원 기록 삭제
		int expireNotMemberCount = aService.expireNotDeleteMember();//데이터/문서 관리 - 미경과된 사원 기록 삭제		
		
		model.addAttribute("expireMemberCount",expireMemberCount);
		model.addAttribute("expireNotMemberCount",expireNotMemberCount);
		return "admin/adminMain";
	}//adminMainPage
	
	//관리자 관리 - 부서별 관리자 리스트 전체 조회
	@RequestMapping(value="/adminSelectPage.ho")
	public String allListMember(HttpSession session, Model model) {
		if(session.getAttribute("member")!=null){ //로그인
			ArrayList<Member> list = aService.selectAllMember();
			if(list != null) {
				model.addAttribute("list",list);
			}
			return "admin/adminContents/adminAdministration";
		} else {
			return "redirect:/login.jsp";
		}
	}//allListMember
	
	//관리자 관리 - 모달 - 사원 검색 (ajax)
	@RequestMapping(value="/adminSearchModal.ho")
	public void searchModal(@RequestParam("keyword") String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException{
		keyword = "%"+keyword+"%";; //키워드 LIKE 처리
		ArrayList<Member> list = aService.adminSearchModal(keyword);
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		new Gson().toJson(list,response.getWriter());
	}//searchModal
	
	//관리자 관리 - 모달 - 전산관리자 권한 추가 (ajax)
	@RequestMapping(value="/adminUpdateAdRight.ho")
	public void adminUpdateAdRight(@RequestParam(value="memNoList[]") List<String> memNoList, HttpServletResponse response) throws IOException{
		int result = aService.adminUpdateAdRight(memNoList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//adminUpdateAdRight
	
	//관리자 관리 - 모달 - 인사관리자 권한 추가 (ajax)
	@RequestMapping(value="/adminUpdatePeRight.ho")
	public void adminUpdatePeRight(@RequestParam(value="memNoList[]") List<String> memNoList, HttpServletResponse response) throws IOException{
		int result = aService.adminUpdatePeRight(memNoList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//adminUpdatePeRight.ho
	
	//관리자 관리 - 모달 - 총무관리자 권한 추가 (ajax)
	@RequestMapping(value="/adminUpdateGeRight.ho")
	public void adminUpdateGeRight(@RequestParam(value="memNoList[]") List<String> memNoList, HttpServletResponse response) throws IOException{
		int result = aService.adminUpdateGeRight(memNoList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//adminUpdateGeRight
		
	//관리자 관리 - 선택된 사원 권한 삭제 (ajax)
	@RequestMapping(value="/adminDeleteRight.ho")
	public void adminDeleteRight(@RequestParam(value="memNoList[]") List<String> memNoList, HttpServletResponse response) throws IOException{
		int result = aService.adminDeleteRight(memNoList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//adminDeleteRight
	
	//삭제 조회 - 삭제된 사원 조회
	@RequestMapping(value="/adminDeleteMemberPage.ho")
	public String selectDeleteMember(HttpSession session, HttpServletRequest request, Model model){
		if(session.getAttribute("member")!=null){ //로그인
			
			int countAll = aService.countDeleteMember();//삭제된 사원 수
			int currentPage; //현재 페이지 값을 가지고 있는 변수 - 페이징 처리를 위한 변수
				
				if(request.getParameter("currentPage")==null){
					currentPage=1;
				} else {
					currentPage = Integer.parseInt(request.getParameter("currentPage"));
				}
			int recordCountPerPage = 10; // 1 페이지당 10개의 게시물 (페이징 처리)
			ArrayList<Member> list = aService.selectDeleteMember(currentPage,recordCountPerPage);
			
			//페이지 네비
			int naviCountPerPage = 10; // 페이지 네비 10개씩 (페이징 처리)
			String pageNavi = aService.getPageNavi(currentPage,recordCountPerPage,naviCountPerPage);
			
				if(list != null) {
					model.addAttribute("countAll",countAll);
					model.addAttribute("list",list);
					model.addAttribute("pageNavi",pageNavi);
				}
				
				return "admin/adminContents/adminDeleteMemberSelect";
		} else {
			return "redirect:/login.jsp";
		}
	}//selectDeleteMember
	
	//삭제 조회 - 삭제된 사원 검색
	@RequestMapping(value="/adminSearchDeleteMember.ho")
	public String searchDeleteMember(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword, HttpServletRequest request, Model model){
		
			if(searchType.equals("memNo")){ //사번 검색
				searchType="MEM_NO";
			}else if(searchType.equals("memName")) { //이름 검색
				searchType="MEM_NAME";
			}else if(searchType.equals("memPosition")) { //직위 검색
				searchType="MEM_POSITION";
			}else if(searchType.equals("deptName")) { //부서 검색
				searchType="DEPT_NAME";
			}
		
		keyword = "%"+keyword+"%"; //유사 keyword 검색 처리	
		int countAll = aService.countDeleteMember();//삭제된 사원 수
		int currentPage; //현재 페이지 값을 가지고 있는 변수 - 페이징 처리를 위한 변수
		
			if(request.getParameter("currentPage")==null){
				currentPage=1;
			} else {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
			
		int recordCountPerPage = 10; // 1 페이지당 10개의 게시물 (페이징 처리)
		ArrayList<Member> list = aService.selectSearchDeleteMember(searchType,keyword,currentPage,recordCountPerPage);
		int searchCount = list.size(); //검색된 수
		
		//페이지 네비
		int naviCountPerPage = 10; // 페이지 네비 10개씩 (페이징 처리)
		String pageNavi = aService.searchGetPageNavi(currentPage,recordCountPerPage,naviCountPerPage,searchCount);
	
			if(list != null) {
				model.addAttribute("countAll",countAll);
				model.addAttribute("searchCount",searchCount);
				model.addAttribute("list",list);
				model.addAttribute("pageNavi",pageNavi);
			}
		return "admin/adminContents/adminDeleteMemberSelect";
	}//searchDeleteMember
	
	//삭제 조회 - 삭제된 사원 복원 (ajax)
	@RequestMapping(value="/adminDeleteMemberCancel.ho")
	public void deleteMemberCancel(@RequestParam(value="memNoList[]") List<String> memNoList, HttpServletResponse response) throws IOException{
		int result = aService.deleteMemberCancel(memNoList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//deleteMemberCancel
	
	//삭제 조회 - 삭제된 사원 영구 삭제 (ajax)
	@RequestMapping(value="/adminDeleteMember.ho")
	public void deleteMember(@RequestParam(value="memNoList[]") List<String> memNoList, HttpServletResponse response) throws IOException{
		int result = aService.deleteMember(memNoList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//deleteMember
	
	//삭제 조회 - 게시글 조회
	@RequestMapping(value="/adminDeleteNoticePage.ho")
	public String selectDeleteNotice(HttpSession session, HttpServletRequest request, Model model) {
		if(session.getAttribute("member")!=null){ //로그인
			
			int countBoard = aService.countBoard();//삭제된 부서별 게시판 수	
			int currentPage; //현재 페이지 값을 가지고 있는 변수 - 페이징 처리를 위한 변수
				
				if(request.getParameter("currentPage")==null){
					currentPage=1;
				} else {
					currentPage = Integer.parseInt(request.getParameter("currentPage"));
				}
			int recordCountPerPage = 10; // 1 페이지당 10개의 게시물 (페이징 처리)
			ArrayList<AdminBoard> list = aService.selectDeleteBoard(currentPage,recordCountPerPage);
			
			//페이지 네비
			int naviCountPerPage = 10; // 페이지 네비 10개씩 (페이징 처리)
			String pageNavi = aService.getBoardPageNavi(currentPage,recordCountPerPage,naviCountPerPage);
			
				if(list != null) {
					model.addAttribute("countBoard",countBoard);
					model.addAttribute("list",list);
					model.addAttribute("pageNavi",pageNavi);
				}
				
				return "admin/adminContents/adminDeleteNoticeSelect";
		} else {
			return "redirect:/login.jsp";
		}
	}//selectDeleteNotice
	
	//삭제 조회 - 삭제된 게시글 검색
	@RequestMapping(value="/adminSearchBoard.ho")
	public String adminSearchBoard(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword, HttpServletRequest request, Model model){
			
		if(searchType.equals("title")) { //제목 검색
			searchType="title";
		}else if(searchType.equals("writer")) { //작성자 검색
			searchType="writer";
		}
		keyword = "%"+keyword+"%"; //유사 keyword 검색 처리	
		int countBoard = aService.countBoard();//삭제된 부서별 게시판 수	
		int currentPage; //현재 페이지 값을 가지고 있는 변수 - 페이징 처리를 위한 변수
			
			if(request.getParameter("currentPage")==null){
				currentPage=1;
			} else {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}
				
		int recordCountPerPage = 10; // 1 페이지당 10개의 게시물 (페이징 처리)
		ArrayList<AdminBoard> list = aService.selectSearchBoard(searchType,keyword,currentPage,recordCountPerPage);
		int searchCount = list.size(); //검색된 수
			
		//페이지 네비
		int naviCountPerPage = 10; // 페이지 네비 10개씩 (페이징 처리)
		String pageNavi = aService.searchGetBoardPageNavi(currentPage,recordCountPerPage,naviCountPerPage,searchCount);
		
			if(list != null) {
				model.addAttribute("countBoard",countBoard);
				model.addAttribute("searchCount",searchCount);
				model.addAttribute("list",list);
				model.addAttribute("pageNavi",pageNavi);
			}
		return "admin/adminContents/adminDeleteNoticeSelect";
	}//adminSearchBoard
	
	//삭제 조회 - 삭제된 부서별 게시글 복원 (ajax)
	@RequestMapping(value="/adminDeleteBoardCancel.ho")
	public void deleteBoardCancel(@RequestParam(value="noList[]") List<String> noList, HttpServletResponse response) throws IOException{
		int result = aService.deleteBoardCancel(noList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//deleteBoardCancel
		
	//삭제 조회 - 삭제된 부서별 게시글 영구 삭제 (ajax)
	@RequestMapping(value="/adminDeleteBoard.ho")
	public void deleteBoard(@RequestParam(value="noList[]") List<String> noList, HttpServletResponse response) throws IOException{
		int result = aService.deleteBoard(noList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//deleteBoard
	
	//삭제 조회 - 결재안 조회
	@RequestMapping(value="/adminDeleteApprovalPage.ho")
	public String selectDeleteApproval() {
		return "admin/adminContents/adminDeleteApprovalSelect";
	}//selectDeleteApproval
	
	//데이터/문서 관리
	@RequestMapping(value="/adminDeleteDataPage.ho")
	public String expireDeleteMember(HttpServletResponse response, Model model) throws IOException {
		int expireMemberCount = aService.expireDeleteMember();//경과된 사원 기록
		int expireNotMemberCount = aService.expireNotDeleteMember();//미경과된 사원 기록
		
		int deletePaperCount = aService.deletePaperCount();//경과된 삭제 대기 게시물
		int expirePaperCount = aService.expirePaperCount();//보존 기간 경과된 삭제 대기 게시물
		int paperCount = deletePaperCount + expirePaperCount;//게시물 기간 경과
		
		int deleteNotPaperCount = aService.deleteNotPaperCount();//미경과된 삭제 대기 게시물
		int expireNotPaperCount = aService.expireNotPaperCount();//보존 기간 미경과된 삭제 대기 게시물
		int paperNotCount = deleteNotPaperCount + expireNotPaperCount;//게시물 기간 미경과
		
		model.addAttribute("expireMemberCount",expireMemberCount);
		model.addAttribute("expireNotMemberCount",expireNotMemberCount);
		
		model.addAttribute("deletePaperCount",deletePaperCount);
		model.addAttribute("expirePaperCount",expirePaperCount);
		model.addAttribute("paperCount",paperCount);
		
		model.addAttribute("deleteNotPaperCount",deleteNotPaperCount);
		model.addAttribute("expireNotPaperCount",expireNotPaperCount);
		model.addAttribute("paperNotCount",paperNotCount);
		
		return "admin/adminContents/adminDeleteData";
	}//expireDeleteMember
		
	//오류 관리 - 비밀번호 초기화
	@RequestMapping(value="/adminPasswordInitPage.ho")
	public String updateMemberInitPassword() {
		return "admin/adminContents/adminPasswordInitialization";
	}//updateMemberInitPassword
	
	//오류 관리 - 문의 사항
	
}//AdminController
