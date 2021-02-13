package kr.or.houroffice.admin.controller;

import java.io.IOException;
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

import kr.or.houroffice.admin.model.service.AdminService;
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
	public String adminMainPage() {
		return "admin/adminMain";
	}//adminMainPage
	
	//관리자 관리 - 부서별 관리자 리스트 전체 조회
	@RequestMapping(value="/adminSelectPage.ho")
	public String allListMember(Model model) {
		ArrayList<Member> list = aService.selectAllMember();
		if(list != null) {
			model.addAttribute("list",list);
		}
		return "admin/adminContents/adminAdministration";
	}//allListMember
	
	//관리자 관리 - 모달 - 사원 검색 (ajax)
	@RequestMapping(value="/adminSearchModal.ho")
	public void searchModal(@RequestParam("keyword") String keyword, HttpServletRequest request, HttpServletResponse response) throws IOException{
		keyword = "%"+keyword+"%";; //키워드 LIKE 처리
		System.out.println("controller :" + keyword);
		ArrayList<Member> list = aService.adminSearchModal(keyword);
		System.out.println("controller :" + list);
	}//searchModal
	
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
	public String searchDeleteMember(@RequestParam("searchType") String searchType, 
			@RequestParam("keyword") String keyword, HttpServletRequest request,
			Model model){
		
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
	public void deleteMemberCancel(@RequestParam(value="memNoList[]") List<String> memNoList,
			HttpServletResponse response) throws IOException{
		int result = aService.deleteMemberCancel(memNoList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//deleteMemberCancel
	
	//삭제 조회 - 삭제된 사원 영구 삭제 (ajax)
	@RequestMapping(value="/adminDeleteMember.ho")
	public void deleteMember(@RequestParam(value="memNoList[]") List<String> memNoList,
			HttpServletResponse response) throws IOException{
		int result = aService.deleteMember(memNoList);
		if(result>0) {
			response.getWriter().print(true);
		} else {
			response.getWriter().print(false);
		}
	}//deleteMember
	
	//삭제 조회 - 게시글 조회
	@RequestMapping(value="/adminDeleteNoticePage.ho")
	public String selectDeleteNotice() {
		return "admin/adminContents/adminDeleteNoticeSelect";
	}//selectDeleteNotice
	
	//삭제 조회 - 결재안 조회
	@RequestMapping(value="/adminDeleteApprovalPage.ho")
	public String selectDeleteApproval() {
		return "admin/adminContents/adminDeleteApprovalSelect";
	}//selectDeleteApproval
	
	//데이터/문서 관리
	@RequestMapping(value="/adminDeleteDataPage.ho")
	public String selectDeleteData(Model model) {
		//(보존기간 경과) 사원 기록 삭제
		int deleteMemberCount = aService.selectDeleteMember();
		model.addAttribute("deleteMemberCount",deleteMemberCount);
		return "admin/adminContents/adminDeleteData";
	}//selectDeleteData
		
	//오류 관리 - 비밀번호 초기화
	@RequestMapping(value="/adminPasswordInitPage.ho")
	public String updateMemberInitPassword() {
		return "admin/adminContents/adminPasswordInitialization";
	}//updateMemberInitPassword
	
	//오류 관리 - 문의 사항
	
}//AdminController
