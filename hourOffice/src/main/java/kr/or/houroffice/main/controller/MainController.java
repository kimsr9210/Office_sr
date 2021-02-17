package kr.or.houroffice.main.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.google.gson.Gson;

import kr.or.houroffice.approval.model.service.ApprovalServiceImpl;
import kr.or.houroffice.common.Page;
import kr.or.houroffice.main.model.service.MainServiceImpl;
import kr.or.houroffice.main.model.vo.AllNotice;
import kr.or.houroffice.main.model.vo.CompanyRule;
import kr.or.houroffice.main.model.vo.MainMailGetter;
import kr.or.houroffice.main.model.vo.MainMailPage;
import kr.or.houroffice.member.model.service.AdminMemberService;
import kr.or.houroffice.member.model.service.MemberService;
import kr.or.houroffice.member.model.vo.Attendance;
import kr.or.houroffice.member.model.vo.Department;
import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.project.model.service.ProjectServiceImpl;
import kr.or.houroffice.project.model.vo.MonthlyProject;
import kr.or.houroffice.project.model.vo.Project;
import kr.or.houroffice.project.model.vo.ProjectBoard;
import kr.or.houroffice.project.model.vo.ProjectComment;

@Controller
public class MainController {
	@Resource(name="memberService")
	private MemberService memberS;
	
	@Resource(name="projectService")
	private ProjectServiceImpl projectS;
	
	@Resource(name="ApprovalService")
	private ApprovalServiceImpl approvalS;
	
	@Resource(name="adminMemberService")
	private AdminMemberService adminMemberS;
	
	@Resource(name="mainService")
	private MainServiceImpl mainS;
	
	@RequestMapping(value="/main.ho")
	public String memberLoginPage(@SessionAttribute("member") Member m, Model model, HttpServletRequest request) throws SQLException, IOException{
		
		//최근 공지사항
		ArrayList<AllNotice> noticeList = mainS.selectAllNotice();
		
		// 최근 공개 프로젝트
		ArrayList<Project> publicList = projectS.selectPublicProject();
		if(publicList.size()<4){
			for(int i=publicList.size(); i<4; i++){
				Project p = new Project();
				p.setMemCount(0);
				publicList.add(p);
			}
		}
		
		//참여중인 프로젝트
		ArrayList<Project> myProject = projectS.selectMyProjectList(m.getMemNo());
		if(!(myProject.isEmpty())){
			
			//프로젝트 게시물 가져오기
			ArrayList<ProjectBoard> boardList = projectS.selectProjectBoardList(myProject.get(0).getProNo()); // 최근 게시물
			model.addAttribute("boardList", boardList);
			
			if(!(boardList.isEmpty())){
				//해당 프로젝트 게시물 작성자 정보 가져오기
				Member boardWirter = memberS.selectOneAsMemNo(boardList.get(0).getMemNo());
				model.addAttribute("boardWirter", boardWirter);
				
				//해당 프로젝트 게시물의 댓글 가져오기
				ArrayList<ProjectComment> commentList = projectS.selectOneBoardComment(boardList.get(0).getBoardNo());
				model.addAttribute("commentList", commentList);
			}
		}
		
		// 즐겨찾기 목록
		ArrayList<Project> favorList = projectS.selectProjectFavoriteList(m.getMemNo());
		
		int monthly = 4; // 5개월 전까지(이번달은 세지 않음)
		int todayMon = Integer.parseInt(request.getParameter("todayMon"));
		ArrayList<MonthlyProject> monthlyList = projectS.selectMonthlyProject(monthly);
		
		if(monthlyList.isEmpty()){
			//System.out.println("---통계가 비어있습니다.---");
			for(int i=0;i<=monthly;i++){
				//System.out.println("0 처리중");
				MonthlyProject mp = new MonthlyProject(todayMon,0);
				todayMon--;
				if(todayMon==0){
					todayMon=12;
				}
				monthlyList.add(mp);
			}
		}else{
			//System.out.println("---통계가 있습니다!!---");
			int index = 0;
			int firstMon = monthlyList.get(index).getMonthly();
			while(firstMon!=todayMon){
				//System.out.println("접두 생성중");
				MonthlyProject preMP = new MonthlyProject();
				preMP.setMonthly(todayMon);
				preMP.setMonthlyCount(0);
				monthlyList.add(index, preMP);
				index++;
				todayMon--;
				if(todayMon==0){
					todayMon=12;
				}
			}
			
			int size = monthlyList.size();
			int lastMon = monthlyList.get(size-1).getMonthly();
			while(size!=monthly+1){
				//System.out.println("접미 생성중");
				MonthlyProject sufMP = new MonthlyProject();
				lastMon--;
				if(lastMon==0){
					lastMon=12;
				}
				sufMP.setMonthly(lastMon);
				sufMP.setMonthlyCount(0);
				monthlyList.add(sufMP);
				size++;
			}
			
		}
		
		//사내규정
		Page ruleP = new Page();
		ruleP.setCurrentPage(1);
		ruleP.setRecordCountPerPage(4);
		ArrayList<CompanyRule> ruleList = mainS.selectAllRule(ruleP);
		
		//메일 목록
		MainMailPage mmp = new MainMailPage();
		mmp.setMemNo(m.getMemNo());
		mmp.setCurrentPage(1);
		mmp.setRecordCountPerPage(3);
		ArrayList<MainMailGetter> mailList = mainS.selectAllMail(mmp);
		
		int alCount = approvalS.selectMyAprLineCount(m.getMemNo()); // 결재할 문서 개수
		Attendance atten = memberS.selectAttendanceMember(m.getMemNo()); // 근태 조회
		ArrayList<Member> companyOC = adminMemberS.selectOrganizationChart(); // 조직도 리스트
		ArrayList<Department> deptList = adminMemberS.selectAllDepartment(); // 부서 목록
		
		
		
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("publicList", publicList);
		model.addAttribute("myProject", myProject);
		model.addAttribute("favorList", favorList);
		model.addAttribute("monthlyList", monthlyList);
		model.addAttribute("ruleList", ruleList);
		model.addAttribute("mailList", mailList);
		
		model.addAttribute("atten", atten);
		model.addAttribute("alCount", alCount);
		model.addAttribute("companyOC", companyOC);
		model.addAttribute("deptList", deptList);
		
		return "memberMain";
	}
	
	@RequestMapping("/moveRulePage.ho")
	public void moveRulePage(@RequestParam("rulePage") String rulePage, HttpServletResponse response) throws IOException{
		int currentPage = Integer.parseInt(rulePage);
		
		Page page = new Page();
		
		page.setCurrentPage(currentPage);
		page.setRecordCountPerPage(4);
		
		ArrayList<CompanyRule> ajaxRule = mainS.selectAllRule(page);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		new Gson().toJson(ajaxRule,out);
	}
	
	@RequestMapping("/moveMailPage.ho")
	public void moveMailPage(@SessionAttribute("member") Member m, @RequestParam("mailPage") String mailPage, HttpServletResponse response) throws IOException{
		int currentPage = Integer.parseInt(mailPage);
		
		MainMailPage mmp = new MainMailPage();
		mmp.setMemNo(m.getMemNo());
		mmp.setCurrentPage(currentPage);
		mmp.setRecordCountPerPage(3);
		
		ArrayList<MainMailGetter> ajaxMail = mainS.selectAllMail(mmp);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		new Gson().toJson(ajaxMail,out);
	}
	
}
