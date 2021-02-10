package kr.or.houroffice.admin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String AdminMainPage() {
		return "admin/adminMain";
	}//AdminMainPage
	
	//관리자 관리 - 부서별 관리자 리스트 전체 조회
	@RequestMapping(value="/adminSelectPage.ho")
	public String allListMember(Model model) {
		ArrayList<Member> list = aService.selectAllMember();
		if(list != null) {
			model.addAttribute("list",list);
		}
		return "admin/adminContents/adminAdministration";
	}//adminSelectPage
	
	//삭제 조회 - 삭제된 사원 조회
	@RequestMapping(value="/adminDeleteMemberPage.ho")
	public String SelectDeleteMember(HttpSession session, HttpServletRequest request, Model model){
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
	}//SelectDeleteMember
	
	//삭제 조회 - 게시글 조회
	@RequestMapping(value="/adminDeleteNoticePage.ho")
	public String SelectDeleteNotice() {
		return "admin/adminContents/adminDeleteNoticeSelect";
	}//SelectDeleteNotice
	
	//삭제 조회 - 결재안 조회
	@RequestMapping(value="/adminDeleteApprovalPage.ho")
	public String SelectDeleteApproval() {
		return "admin/adminContents/adminDeleteApprovalSelect";
	}//SelectDeleteApproval
	
	//데이터/문서 관리
	@RequestMapping(value="/adminDeleteDataPage.ho")
	public String SelectDeleteData() {
		return "admin/adminContents/adminDeleteData";
	}//SelectDeleteData
		
	//오류 관리 - 비밀번호 초기화
	@RequestMapping(value="/adminPasswordInitPage.ho")
	public String UpdateMemberInitPassword() {
		return "admin/adminContents/adminPasswordInitialization";
	}//UpdateMemberInitPassword
	
	//오류 관리 - 문의 사항
	
}//AdminController
