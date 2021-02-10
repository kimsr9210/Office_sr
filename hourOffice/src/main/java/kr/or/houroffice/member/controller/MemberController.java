package kr.or.houroffice.member.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.houroffice.common.AjaxTemplate;
import kr.or.houroffice.member.model.service.MemberService;
import kr.or.houroffice.member.model.vo.Attendance;
import kr.or.houroffice.member.model.vo.Member;

@Controller
public class MemberController {

	@Resource(name="memberService")
	private MemberService mService;
	
	@RequestMapping(value="/memberLoginPage.ho")
	public String memberLoginPage(){ // 인덱스 페이지에서 로그인페이지으로 이동용 메소드
		return "redirect:/login.jsp";
	}
	
	@RequestMapping(value="/memberLogin.ho")
	public String loginMember(HttpSession session, Model model, Member m){ //로그인 메소드
		Member member = mService.loginMember(m);
		
		if(member != null){
			session.setAttribute("member", member);
			return "redirect:/login.jsp";
		}else{
			model.addAttribute("msg", "로그인 실패");
			model.addAttribute("location", "/memberLoginPage.ho");
			return "result";
		}
	}
	
	@RequestMapping(value="/memberLogout.ho")
	public String logoutMember(HttpSession session){ // 로그아웃 메소드
		session.invalidate();
		return "redirect:/index.jsp";
	}
	
	@RequestMapping(value="/startWork.ho")// 출근 기록
	public void workStartMember(@SessionAttribute("member") Member m, HttpServletResponse response) throws IOException { 
		
		int result = mService.insertAttendanceMember(m.getMemNo());
		AjaxTemplate.resultTF(result, response);
		
	}
	
	@RequestMapping(value="/endWork.ho")// 퇴근 기록
	public void workEndMember(@SessionAttribute("member") Member m, Attendance atten, HttpServletResponse response) throws IOException { 
		atten.setMemNo(m.getMemNo());
		
		int result = mService.updateAttendanceMember(atten);
		AjaxTemplate.resultTF(result, response);
	}
	

}
