package kr.or.houroffice.member.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import kr.or.houroffice.member.model.service.AdminMemberService;
import kr.or.houroffice.member.model.vo.AcademicAbility;
import kr.or.houroffice.member.model.vo.Career;
import kr.or.houroffice.member.model.vo.Department;
import kr.or.houroffice.member.model.vo.License;
import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.member.model.vo.Military;

@Controller
public class AdminMemberController {
	
	@Resource(name="adminMemberService")
	private AdminMemberService mService;
	
	@Autowired
	ServletContext context; // 파일 업로드시 필요한 객체 - 현재 프로젝트에 대한 정보를 가지고있는 객체
	// 통합사원 ----------------------------------------------------------------------------------------------------------------------
	// 사원 all select
	@RequestMapping(value="/admin_tap_allListMember.ho")
	public String allListMember(HttpSession session, HttpServletRequest request, Model model){
		
		if(session.getAttribute("member")!=null){ // 로그인 되었다면
			
			
			int countAll = mService.selectCountAllMember();
			
			int currentPage; // 현재 페이지값을 가지고 있는 변수 - 페이징 처리를 위한 변수
			if(request.getParameter("currentPage")==null) {
				currentPage = 1;
			}else {
				currentPage = Integer.parseInt(request.getParameter("currentPage"));
			}	
			int recordCountPerPage = 10; // 한 페이지당 몇개의 게시물이 보이게 될 것인지 - 페이징 처리를 위한 변수
			
			ArrayList<Member> list = mService.selectAllMember(currentPage,recordCountPerPage);
			
	
			// 페이징 처리 - 네비
			int naviCountPerPage = 10; // page Navi값이 몇개씩 보여줄 것인지 - 페이징 처리를 위한 변수
			String pageNavi = mService.getPageNavi(currentPage,recordCountPerPage,naviCountPerPage);
			
			
			if(list != null){
				model.addAttribute("countAll",countAll);
				model.addAttribute("list",list);
				model.addAttribute("pageNavi", pageNavi);
			}
			
			return "admin_tap/personnel_department/allListMember";
			
		}else{ return "redirect:/login.jsp"; }// if문 - session
		
	}
	// 통합사원 사원 삭제 update (ajax)
	@RequestMapping(value="/admin_tap_resignMember.ho")
	public void resignMember(@RequestParam(value="memNoList[]") List<String> memNoList,HttpServletResponse response) throws IOException{
		int result = mService.updateMemberResign(memNoList);
		if(result>0){
			response.getWriter().print(true);
		}else {
			response.getWriter().print(false);
		}
	}
	// 통합사원 사원 직위 변경 update (ajax)
	@RequestMapping(value="/admin_tap_changePosition.ho")
	public void changePosition(@RequestParam("memNo") int memNo, @RequestParam("position") String memPosition, HttpServletResponse response) throws IOException{
		int result = mService.updateMemberPosition(memNo, memPosition);
		if(result>0){
			response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}
	}
	// 통합사원 사원 검색 select
	@RequestMapping(value="/admin_tap_search_allListMember.ho")
	public String searchMember(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword, HttpServletRequest request, Model model){
		if(searchType.equals("memNo")){ searchType = "MEM_NO"; // 사번으로 검색
		}else if(searchType.equals("memName")){ searchType = "MEM_NAME"; } // 이름으로 검색
		keyword = "%"+keyword+"%"; // 키워드 LIKE 처리
		
		int countAll = mService.selectCountAllMember();
		
		int currentPage; // 현재 페이지값을 가지고 있는 변수 - 페이징 처리를 위한 변수
		if(request.getParameter("currentPage")==null) {
			currentPage = 1;
		}else {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}	
		int recordCountPerPage = 10; // 한 페이지당 몇개의 게시물이 보이게 될 것인지 - 페이징 처리를 위한 변수
		
		ArrayList<Member> list = mService.selectSearchMember(searchType,keyword,currentPage,recordCountPerPage);
		
		int searchCount = list.size(); // 검색된 수
			// 페이징 처리 - 네비
		int naviCountPerPage = 10; // page Navi값이 몇개씩 보여줄 것인지 - 페이징 처리를 위한 변수
		String pageNavi = mService.searchGetPageNavi(currentPage,recordCountPerPage,naviCountPerPage,searchCount);
		
		if(list != null){
			model.addAttribute("countAll",countAll);
			model.addAttribute("list",list);
			model.addAttribute("pageNavi", pageNavi);
		}
		
		return "admin_tap/personnel_department/allListMember";
	}

	// 사원 등록 ------------------------------------------------------------------------------------------------------------------------
	// 사원 등록 - view 페이지
	@RequestMapping(value="/admin_tap_memberJoin.ho")
	public String memberJoin(Model model, HttpSession session){
		
		if(session.getAttribute("member")!=null){
			ArrayList<Department> deptList = mService.selectDeptAll();
			
			model.addAttribute("deptList",deptList);
			return "admin_tap/personnel_department/memberJoin";
		}else{
			return "redirect:login.jsp";
		}
		
	}
	// 사원 등록 - insert
	@RequestMapping(value="/memberSingUp.ho")
	public String memberSingUp(HttpSession session, HttpServletRequest request, Model model) throws IOException{
		
		if(session.getAttribute("member")!=null){
		
			// 파일이 업로드 되는 경로
			String uploadPath = "/resources/images/profile/";
			// 최대 파일 사이즈를 정하기 위한 값
			int uploadFileSizeLimit = 10*1024*1024; // 최대 10MB
			// 파일 이름 인코딩 값
			String encType = "UTF-8";
			// ServletContext를 이용하여 실제 저장 경로를 가져와야 함
			String realUploadPath = context.getRealPath(uploadPath);
			
			// MultipartRequest 객체 생성 (생성하면서 마지막 5번째 정책 선정 객체 만들기)
			MultipartRequest multi = new MultipartRequest(request,realUploadPath,uploadFileSizeLimit,encType,new DefaultFileRenamePolicy());
			
			// 이 위까지 하면 이미지 업로드 완료
			// 멀티파트리퀘스트를 이용하면 매개변수를 객체로 받아올 수 없음 (방법을 모름)
			// 그래서 모든 정보를 하나하나 객체에 넣으준다
			// request.getParameter() 를 이용 하면 모두 null 값을 반환
			// multi.getParameter() 를 이용 해야 매개변수를 가져올 수 있음
			
			// 프로필사진 이름 만들기
			// 확장자 추출
			String memProfile = multi.getFilesystemName("memProfile");
			int pos = memProfile.lastIndexOf( "." );
			String ext = memProfile.substring( pos + 1 );
			// 시간 포맷 및 현재 시간값 가져오기
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss"); // 포맷 만들기
			long currentTime = Calendar.getInstance().getTimeInMillis(); // 현재 시간값 가져오기
			String profileRename = formatter.format(currentTime); // 시간값만 일단 넣어둠 
			
			// 멤버 
			Member m = new Member();
			
			m.setMemPosition(multi.getParameter("memPosition"));
			m.setDeptCode(multi.getParameter("deptCode"));
			m.setMemName(multi.getParameter("memName"));
			m.setMemBirth(Date.valueOf(multi.getParameter("memBirth1")+"-"+multi.getParameter("memBirth2")+"-"+multi.getParameter("memBirth3")));
			m.setMemGender(multi.getParameter("memGender").charAt(0));
			m.setMemAddress("("+multi.getParameter("memAddress1")+") "+multi.getParameter("memAddress2"));
			m.setMemPhone(multi.getParameter("memPhone1")+multi.getParameter("memPhone2")+multi.getParameter("memPhone3"));
			m.setMemProfile(profileRename+"."+ext);
			
			//System.out.println(m.getMemPosition()+" / "+m.getDeptCode()+" / "+m.getMemName()+" / "+m.getMemBirth()+" / "+m.getMemGender()+" / "+m.getMemAddress()+" / "+m.getMemPhone()+" / "+m.getMemProfile());
			
			
			// 학력
			ArrayList<AcademicAbility> acaList = new ArrayList<AcademicAbility>();
			
			for(int i=0; i<multi.getParameterValues("acaSchoolName").length; i++){
				if(multi.getParameterValues("acaSchoolName")[i].equals("") && multi.getParameterValues("acaMajorName")[i].equals("")){  }else{
					// 만약 학교명 과 전공명이 빈 값이 아니라면 실행 (디비에 넣겠다는 의미)
					AcademicAbility aca = new AcademicAbility();
					
					if(multi.getParameterValues("acaEnrollDate")[i].charAt(0)!='0'){ aca.setAcaEnrollDate(Date.valueOf(multi.getParameterValues("acaEnrollDate")[i])); }
					if(multi.getParameterValues("acaGradDate")[i].charAt(0)!='0'){ aca.setAcaGradDate(Date.valueOf(multi.getParameterValues("acaGradDate")[i])); }
					aca.setAcaSchoolName(multi.getParameterValues("acaSchoolName")[i]);
					aca.setAcaMajorName(multi.getParameterValues("acaMajorName")[i]);
					aca.setAcaGrad(multi.getParameterValues("acaGrad")[i]);
						//System.out.println(aca.getAcaEnrollDate()+" / "+aca.getAcaGradDate()+" / "+aca.getAcaSchoolName()+" / "+aca.getAcaMajorName()+" / "+aca.getAcaGrad());
					acaList.add(aca);
				}
			}
			
					
			
			// 자격증
			ArrayList<License> licList = new ArrayList<License>();
			
			for(int j=0; j<multi.getParameterValues("licName").length; j++){
				
				if(multi.getParameterValues("licName")[j].equals("") && multi.getParameterValues("licOrigin")[j].equals("")){  }else{
					// 만약 자격증명 과 시행처가 빈 값이 아니라면 실행 (디비에 넣겠다는 의미)
					License lic = new License();
					
					if(multi.getParameterValues("licDate")[j].charAt(0)!='0'){ lic.setLicDate(Date.valueOf(multi.getParameterValues("licDate")[j])); }
					lic.setLicName(multi.getParameterValues("licName")[j]);
					lic.setLicOrigin(multi.getParameterValues("licOrigin")[j]);
						//System.out.println(lic.getLicDate()+" / "+lic.getLicName()+" / "+lic.getLicOrigin());
					licList.add(lic);
				}
			}
			
			// 경력
			ArrayList<Career> carList = new ArrayList<Career>();
									
			for(int k=0; k<multi.getParameterValues("carPlace").length; k++){
				if(multi.getParameterValues("carPlace")[k].equals("") && multi.getParameterValues("carPosition")[k].equals("")){  }else{
					// 만약 회사명 과 직위가 빈 값이 아니라면 실행 (디비에 넣겠다는 의미)
					Career car = new Career();
								
					if(multi.getParameterValues("carJoinDate")[k].charAt(0)!='0'){ car.setCarJoinDate(Date.valueOf(multi.getParameterValues("carJoinDate")[k])); }
					if(multi.getParameterValues("carResignDate")[k].charAt(0)!='0'){ car.setCarResignDate(Date.valueOf(multi.getParameterValues("carResignDate")[k])); }
					car.setCarPlace(multi.getParameterValues("carPlace")[k]);
					car.setCarPosition(multi.getParameterValues("carPosition")[k]);
					car.setCarContent(multi.getParameterValues("carContent")[k]);
						//System.out.println(car.getCarJoinDate()+" / "+car.getCarResignDate()+" / "+car.getCarPlace()+" / "+car.getCarPosition()+" / "+car.getCarContent());
					carList.add(car);
				}
			}	
			
			// 병력
			Military mil = new Military();
			mil.setMilServiceType(multi.getParameter("milServiceType"));
			if(multi.getParameter("milJoinDate").charAt(0)!='0'){ mil.setMilJoinDate(Date.valueOf(multi.getParameter("milJoinDate"))); }
			if(multi.getParameter("milLeaveDate").charAt(0)!='0'){ mil.setMilLeaveDate(Date.valueOf(multi.getParameter("milLeaveDate"))); }
			mil.setMilReason(multi.getParameter("milReason"));
				//System.out.println(mil.getMilJoinDate()+" / "+mil.getMilLeaveDate()+" / "+mil.getMilReason());
			// member 객제, 학력 객체, 자격증 객체, 경력 객체, 병력 객체 넘겨주고 비즈니스 로직 처리
			int result = mService.insertMember(m,acaList,licList,carList,mil); // 사번 리턴
			
			if(result>0){
				// 파일 리네임
				File file = new File(realUploadPath+"\\"+multi.getFilesystemName("memProfile")); // 파일 연결
				file.renameTo(new File(realUploadPath+"\\"+profileRename+"_"+result+"."+ext)); // 실제 경로에 있는 파일 이름을 바꿈
				model.addAttribute("msg", "사원 등록을 완료하였습니다.");
			}else{
				model.addAttribute("msg", "사원 등록을 실패하였습니다. \n지속적인 실패 시 관리자에 문의하세요.");
			}
			model.addAttribute("location", "/admin_tap_allListMember.ho");
			return "result";
		}else{
			return "redirect:login.jsp";
		}
	}
	
		
	// 사원 정보 ------------------------------------------------------------------------------------------------------------------------
	// 사원 정보 select
	@RequestMapping(value="/admin_tap_memberInfo.ho")
	public String memberInfo(Member m, HttpSession session, Model model){
		Member oneMem = mService.selectOneMember(m); // MEMBER 테이블
		
		if(oneMem!=null){
			
			ArrayList<Department> deptList = mService.selectDeptAll();
			ArrayList<AcademicAbility> acaList = mService.selectOneMemberAca(m); // 학력
			ArrayList<License> licList = mService.selectOneMemberLic(m); // 자격증
			ArrayList<Career> carList = mService.selectOneMemberCar(m); // 경력
			Military mil = mService.selectOneMemberMil(m);
			
			//System.out.println(acaList.get(0).getMemNo()+" / "+acaList.get(0).getAcaEnrollDate()+" / "+acaList.get(0).getAcaGradDate()+" / "+acaList.get(0).getAcaSchoolName()+" / "+acaList.get(0).getAcaMajorName()+" / "+acaList.get(0).getAcaGrad());
			//System.out.println(licList.get(0).getMemNo()+" / "+licList.get(0).getLicDate()+" / "+licList.get(0).getLicName()+" / "+licList.get(0).getLicOrigin());
			//System.out.println(carList.get(0).getMemNo()+" / "+carList.get(0).getCarJoinDate()+" / "+carList.get(0).getCarResignDate()+" / "+carList.get(0).getCarPlace()+" / "+carList.get(0).getCarPosition()+" / "+carList.get(0).getCarContent());
			//System.out.println(mil.getMemNo()+" / "+mil.getMilJoinDate()+" / "+mil.getMilLeaveDate()+" / "+mil.getMilServiceType()+" / "+mil.getMilReason());
			
			
			model.addAttribute("deptList",deptList);
			model.addAttribute("member",oneMem);
			model.addAttribute("acaList",acaList);
			model.addAttribute("licList",licList);
			model.addAttribute("carList",carList);
			model.addAttribute("mil",mil);
			return "admin_tap/personnel_department/memberInfo";
		}else{
			model.addAttribute("msg","해당 사원의 정보가 없습니다. \n지속적인 문제발생시 관리자에 문의하세요.");
			model.addAttribute("location","/admin_tap_allListMember.ho");
			return "result";
		}
		/*
		if(session.getAttribute("member")!=null){
			return "admin_tap/personnel_department/memberInfo";
		}else{
			return "redirect:login.jsp";
		}
		*/
	}
	// 사원 정보 update
	@RequestMapping(value="/admin_tap_modifyMemberInfo.ho")
	public String modifyMemberInfo(Member m, HttpServletRequest request, Model model){
		String[] tbl = {"ACADEMIC_ABILITY","LICENSE","CAREER","MILITARY"}; // delete 할 테이블 명 
		Member member = mService.selectOneMember(m);
		
		if(member!=null){
			int result = mService.updateMemberInfo(m,tbl); // 먼저 전 데이터 삭제
			// 학력
			ArrayList<AcademicAbility> acaList = new ArrayList<AcademicAbility>();
					
			for(int i=0; i<request.getParameterValues("acaSchoolName").length; i++){
				if(request.getParameterValues("acaSchoolName")[i].equals("") && request.getParameterValues("acaMajorName")[i].equals("")){  }else{
					// 만약 학교명 과 전공명이 빈 값이 아니라면 실행 (디비에 넣겠다는 의미)
					AcademicAbility aca = new AcademicAbility();
					
					aca.setMemNo(m.getMemNo());
					if(request.getParameterValues("acaEnrollDate")[i].charAt(0)!='0'){ aca.setAcaEnrollDate(Date.valueOf(request.getParameterValues("acaEnrollDate")[i])); }
					if(request.getParameterValues("acaGradDate")[i].charAt(0)!='0'){ aca.setAcaGradDate(Date.valueOf(request.getParameterValues("acaGradDate")[i])); }
					aca.setAcaSchoolName(request.getParameterValues("acaSchoolName")[i]);
					aca.setAcaMajorName(request.getParameterValues("acaMajorName")[i]);
					aca.setAcaGrad(request.getParameterValues("acaGrad")[i]);
						//System.out.println(aca.getAcaEnrollDate()+" / "+aca.getAcaGradDate()+" / "+aca.getAcaSchoolName()+" / "+aca.getAcaMajorName()+" / "+aca.getAcaGrad());
					acaList.add(aca);
				}
			}	
					
			// 자격증
			ArrayList<License> licList = new ArrayList<License>();
					
			for(int j=0; j<request.getParameterValues("licName").length; j++){
				if(request.getParameterValues("licName")[j].equals("") && request.getParameterValues("licOrigin")[j].equals("")){  }else{
					// 만약 자격증명 과 시행처가 빈 값이 아니라면 실행 (디비에 넣겠다는 의미)
					License lic = new License();
					
					lic.setMemNo(m.getMemNo());
					if(request.getParameterValues("licDate")[j].charAt(0)!='0'){ lic.setLicDate(Date.valueOf(request.getParameterValues("licDate")[j])); }
					lic.setLicName(request.getParameterValues("licName")[j]);
					lic.setLicOrigin(request.getParameterValues("licOrigin")[j]);
						//System.out.println(lic.getLicDate()+" / "+lic.getLicName()+" / "+lic.getLicOrigin());
					licList.add(lic);
				}
			}
			
			// 경력
			ArrayList<Career> carList = new ArrayList<Career>();
			
			for(int k=0; k<request.getParameterValues("carPlace").length; k++){
				if(request.getParameterValues("carPlace")[k].equals("") && request.getParameterValues("carPosition")[k].equals("")){  }else{
					// 만약 회사명 과 직위가 빈 값이 아니라면 실행 (디비에 넣겠다는 의미)
					Career car = new Career();
					
					car.setMemNo(m.getMemNo());
					if(request.getParameterValues("carJoinDate")[k].charAt(0)!='0'){ car.setCarJoinDate(Date.valueOf(request.getParameterValues("carJoinDate")[k])); }
					if(request.getParameterValues("carResignDate")[k].charAt(0)!='0'){ car.setCarResignDate(Date.valueOf(request.getParameterValues("carResignDate")[k])); }
					car.setCarPlace(request.getParameterValues("carPlace")[k]);
					car.setCarPosition(request.getParameterValues("carPosition")[k]);
					car.setCarContent(request.getParameterValues("carContent")[k]);
						//System.out.println(car.getCarJoinDate()+" / "+car.getCarResignDate()+" / "+car.getCarPlace()+" / "+car.getCarPosition()+" / "+car.getCarContent());
					carList.add(car);
				}
			}	
			
			// 병력
			Military mil = new Military();
			mil.setMemNo(m.getMemNo());
			mil.setMilServiceType(request.getParameter("milServiceType"));
			if(request.getParameter("milJoinDate").charAt(0)!='0'){ mil.setMilJoinDate(Date.valueOf(request.getParameter("milJoinDate"))); }
			if(request.getParameter("milLeaveDate").charAt(0)!='0'){ mil.setMilLeaveDate(Date.valueOf(request.getParameter("milLeaveDate"))); }
			mil.setMilReason(request.getParameter("milReason"));
				//System.out.println(mil.getMilJoinDate()+" / "+mil.getMilLeaveDate()+" / "+mil.getMilReason());
			
		
			int insertInfo = mService.insertNewMemberInfo(acaList,licList,carList,mil); // insert
			
			int lengCount = acaList.size()+licList.size()+carList.size()+1; // insert를 모두 성공했을 경우의 수
			
			if(insertInfo>=lengCount){
				model.addAttribute("msg","정보를 변경하였습니다.");
			}else{
				model.addAttribute("msg","정보 변경을 실패하였습니다. \n지속적인 실패시 관리자에 문의하세요");
			}
			model.addAttribute("location","/admin_tap_memberInfo.ho?memNo="+m.getMemNo());
			
		}else{
			model.addAttribute("msg","해당 사원의 정보가 없습니다.");
			model.addAttribute("location","/admin_tap_memberInfo.ho"); // 통합사원 목록으로 이동
		}
		return "result";
	}
	// 조직도 ------------------------------------------------------------------------------------------------------------------------
	// 조직도 select
	@RequestMapping(value="/admin_tap_organizationChart.ho")
	public String organizationChart(HttpSession session, Model model){
		
		ArrayList<Member> list = mService.selectOrganizationChart();
		
		//이 아래 forEach 구문은 DEPT_CODE안에 있는 공백 제거하는 코드
		for(Member m : list){
			if(m.getDeptCode()!=null){
				m.setDeptCode(m.getDeptCode().replaceAll(" ", ""));
			}
		}
		
		model.addAttribute("list",list);
		/*
		if(session.getAttribute("member")!=null){
			//return "admin_tap/personnel_department/organizationChart";
		}else{
			return "redirect:login.jsp";
		}*/
		return "admin_tap/personnel_department/organizationChart";
	}
	// 사원 부서 이동 update
	@RequestMapping(value="/admin_tap_changeDepartment.ho")
	public String changePosition(@RequestParam(value="memNo") int[] memNo, @RequestParam String deptCode, Model model, HttpSession session){
		int result = mService.updateMemberDepartment(memNo,deptCode);
		if(result>0){
			return organizationChart(session,model);
			
		}else{
			model.addAttribute("msg","부서이동이 실패하였습니다. \n지속적인 오류시 관리자에 문의하세요.");
			model.addAttribute("location","/admin_tap_organizationChart.ho");
			return "result";
		}
	}
	
	// 부서 추가 insert
	@RequestMapping(value="/admin_tap_addDepartment.ho")
	public String addDepartment(@RequestParam("deptCode") String deptCode, @RequestParam("deptName") String deptName, Model model){
		int result = mService.insertDepartment(deptCode,deptName);
		
		if(result>0){
			model.addAttribute("msg","새로운 부서를 추가하였습니다.");
		}else{
			model.addAttribute("msg","새로운 부서 추가를 실패하였습니다. \n지속적인 오류시 관리자에 문의하세요.");
		}
		model.addAttribute("location", "/admin_tap_organizationChart.ho");
		return "result";
	}
	
	// 부서 이름 수정 update (ajax)
	@RequestMapping(value="/admin_tap_modifyDepartmentName.ho")
	public void modifyDepartmentName(@RequestParam("deptCode") String deptCode, @RequestParam("deptName") String deptName, HttpServletResponse response) throws IOException{
		deptCode = deptCode+" "; // DB에는 끝에 공백이 들어가 있어서 조직도에 뿌려줄 때 빼준 공백을 다시 넣어줌...
								// 추후에 DB를 수정하게 되면 해당 코드는 없어져야함..
		int result = mService.updateDepartmentName(deptCode,deptName);
		if(result>0){
			response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}
		
	}
	
	// 부서 삭제 update
	@RequestMapping(value="/admin_tap_dropDepartment.ho")
	public String dropDepartment(@RequestParam("deptCode") String deptCode, Model model, HttpSession session){
		int result = mService.updateDepartmentDelete(deptCode+" ");
		if(result>0){
			return organizationChart(session,model);
		}else{System.out.println("aa");
			model.addAttribute("msg","해당 부서 삭제를 실패하였습니다. \n지속적인 오류시 관리자에 문의하세요.");
			model.addAttribute("location","/admin_tap_organizationChart.ho");
			return "result";
		}
	}
	
	
}
