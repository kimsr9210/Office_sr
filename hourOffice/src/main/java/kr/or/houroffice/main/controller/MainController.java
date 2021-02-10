package kr.or.houroffice.main.controller;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.houroffice.approval.model.service.ApprovalServiceImpl;
import kr.or.houroffice.member.model.service.AdminMemberService;
import kr.or.houroffice.member.model.service.MemberService;
import kr.or.houroffice.member.model.vo.Attendance;
import kr.or.houroffice.member.model.vo.Department;
import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.project.model.service.ProjectServiceImpl;
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
	
	@RequestMapping(value="/main.ho")
	public String memberLoginPage(@SessionAttribute("member") Member m, Model model){
		
		ArrayList<Project> publicList = projectS.selectPublicProject(); // 최근 공개 프로젝트
		if(publicList.size()<4){
			for(int i=publicList.size(); i<4; i++){
				Project p = new Project();
				p.setMemCount(0);
				publicList.add(p);
			}
		}
		
		ArrayList<Project> myProject = projectS.selectMyProjectList(m.getMemNo()); //참여중인 프로젝트
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
		
		Attendance atten = memberS.selectAttendanceMember(m.getMemNo()); // 근태 조회
		int alCount = approvalS.selectMyAprLineCount(m.getMemNo()); // 결재할 문서 개수
		ArrayList<Member> companyOC = adminMemberS.selectOrganizationChart(); // 조직도 리스트
		ArrayList<Department> deptList = adminMemberS.selectAllDepartment(); // 부서 목록
		
		
		model.addAttribute("publicList", publicList);
		model.addAttribute("myProject", myProject);
		
		model.addAttribute("atten", atten);
		model.addAttribute("alCount", alCount);
		model.addAttribute("companyOC", companyOC);
		model.addAttribute("deptList", deptList);
		
		return "memberMain";
	}
	
}
