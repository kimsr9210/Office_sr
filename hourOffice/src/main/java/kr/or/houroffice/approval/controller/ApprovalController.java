package kr.or.houroffice.approval.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import kr.or.houroffice.approval.model.service.ApprovalServiceImpl;
import kr.or.houroffice.approval.model.vo.Approval;
import kr.or.houroffice.approval.model.vo.ApprovalLine;
import kr.or.houroffice.approval.model.vo.ApprovalRef;
import kr.or.houroffice.approval.model.vo.AprFormCCC;
import kr.or.houroffice.approval.model.vo.AprFormHol;
import kr.or.houroffice.approval.model.vo.AprFormLazy;
import kr.or.houroffice.approval.model.vo.AprFormOvt;
import kr.or.houroffice.approval.model.vo.AprLineMember;
import kr.or.houroffice.approval.model.vo.AprListPage;
import kr.or.houroffice.approval.model.vo.CCCForm;
import kr.or.houroffice.common.PageList;
import kr.or.houroffice.member.model.service.AdminMemberService;
import kr.or.houroffice.member.model.vo.Department;
import kr.or.houroffice.member.model.vo.Member;

@Controller
public class ApprovalController {

	@Autowired
	@Qualifier(value="ApprovalService")
	private ApprovalServiceImpl aprService;
	
	@Autowired
	@Qualifier(value="adminMemberService")
	private AdminMemberService amService;
	
	//결재 리스트 출력
	@RequestMapping(value="/approvalList.ho")
	public ModelAndView approvalList(@SessionAttribute("member") Member m, @RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="aprStatus", required=false, defaultValue="") String aprStatus, 
			@RequestParam(value="listType", defaultValue="R") String listType, 
			@RequestParam(value="searchType",required=false, defaultValue="") String searchType,
			@RequestParam(value="keyword",required=false,defaultValue="") String keyword, ModelAndView mav){
		AprListPage alp = new AprListPage(); //페이징 처리 정보를 위한 객체
		alp.setCurrentPage(page);
		alp.setRecordCountPerPage(10);//한페이지에 10개
		alp.setNaviCountPerPage(5);//네비에는 5개씩
		
		if(!aprStatus.isEmpty()) alp.setAprStatus(aprStatus); //결재 상태에 따른 필터링 정보
		
		if(!keyword.isEmpty()){//검색할 내용이 있으면
			alp.setSearchType(searchType);
			alp.setKeyword(keyword);
		}
		
		switch(listType.charAt(0)){
		case 'R' : //결재 요청함
			alp.setMemNo(m.getMemNo());
			alp.setUrl("/approvalList.ho?listType=R"); //네비에 넣을 url
			mav.setViewName("approval/aprListRequest");
			break;
		case 'W' : //결재 대기함
			alp.setMemNo(m.getMemNo());
			alp.setUrl("/approvalList.ho?listType=W");
			mav.setViewName("approval/aprListWait");
			break;
		case 'F' : //참조 결재함
			alp.setMemNo(m.getMemNo());
			alp.setUrl("/approvalList.ho?listType=F");
			mav.setViewName("approval/aprListRef");
			break;
		case 'D' : //부서결재함
			alp.setDeptCode(m.getDeptCode());
			alp.setUrl("/approvalList.ho?listType=D");
			mav.setViewName("approval/aprListDept");
			break;
		}
		PageList pl = aprService.selectApprovalList(alp,listType.charAt(0));
		mav.addObject("aprList", pl);
		
		return mav;
	}
	
	//결재 폼 로드
	@RequestMapping(value="/approvalForm.ho")
	public ModelAndView approvalForm(@SessionAttribute("member") Member m, @RequestParam("docuType") String docuType, ModelAndView mav){
		mav.addObject("today",LocalDate.now().toString()); //오늘 날짜 구하기
		ArrayList<AprLineMember> list = null;
		
		switch(docuType.charAt(0)){
		case 'H' : list = aprService.selectMyDeptPeople(m); //연차신청
			mav.setViewName("approval/aprFormHol");
			break;
		case 'O' : list = aprService.selectMyDeptPeople(m); //연장근무신청
			mav.setViewName("approval/aprFormOvt");
			break;
		case 'L' : list = aprService.selectMyDeptPeople(m); //지각불참사유
			mav.setViewName("approval/aprFormLazy");
			break;
		case 'C' : list = aprService.selectCCCLinePeople(m); //법인카드사용신청
			mav.setViewName("approval/aprFormCCC");
			break;
		}
		mav.addObject("aprLineList",list);
		ArrayList<Department> deptList = amService.selectDeptAll(); //부서 정보
		mav.addObject("deptList", deptList);
		
		return mav;
	}
	
	//결재문서 입력
	@RequestMapping(value="/insertAprCCC.ho") //법인카드사용 입력
	public String approvalInsertAprCCC(@SessionAttribute("member") Member m, AprFormCCC afc, Model model){
		//lockYN urgencyYN aprLine, aprRef
		//title, ovtType, startHour, startMinute, endHour, endMinute, totalHour, reasons
		afc.setMemNo(m.getMemNo());
		afc.setMemName(m.getMemName());
		afc.setDeptCode(m.getDeptCode());
		afc.setDocuType('C'); //문서양식 설정
		
		int result = aprService.insertAprForm(afc);
		
		if(result>0) model.addAttribute("msg", "전자결재 등록 성공");
		else model.addAttribute("msg", "전자결재 등록 실패");
		model.addAttribute("location", "/approvalList.ho");
		
		return "approval/aprResult";
	}
	
	@RequestMapping(value="/insertAprHol.ho") //연차신청 입력
	public String approvalInsertAprHol(@SessionAttribute("member") Member m, AprFormHol afh, Model model){
		//lockYN urgencyYN aprLine, aprRef
		//title, ovtType, startHour, startMinute, endHour, endMinute, totalHour, reasons
		afh.setMemNo(m.getMemNo());
		afh.setMemName(m.getMemName());
		afh.setDeptCode(m.getDeptCode());
		afh.setDocuType('H'); //문서양식 설정

		int result = aprService.insertAprForm(afh);
		
		if(result>0) model.addAttribute("msg", "전자결재 등록 성공");
		else model.addAttribute("msg", "전자결재 등록 실패");
		model.addAttribute("location", "/approvalList.ho");
		
		return "approval/aprResult";
	}
	
	@RequestMapping(value="/insertAprLazy.ho") //지각불참사유 입력
	public String approvalInsertAprLazy(@SessionAttribute("member") Member m, AprFormLazy afl, Model model){
		//lockYN urgencyYN aprLine, aprRef
		//title, ovtType, startHour, startMinute, endHour, endMinute, totalHour, reasons
		afl.setMemNo(m.getMemNo());
		afl.setMemName(m.getMemName());
		afl.setDeptCode(m.getDeptCode());
		afl.setDocuType('L'); //문서양식 설정
		
		int result = aprService.insertAprForm(afl);
		
		if(result>0) model.addAttribute("msg", "전자결재 등록 성공");
		else model.addAttribute("msg", "전자결재 등록 실패");
		model.addAttribute("location", "/approvalList.ho");
		
		return "approval/aprResult";
	}
	
	@RequestMapping(value="/insertAprOvt.ho") //연장근무 입력
	public String approvalInsertAprOvt(@SessionAttribute("member") Member m, AprFormOvt afo, Model model){
		//lockYN urgencyYN aprLine, aprRef
		//title, ovtType, startHour, startMinute, endHour, endMinute, totalHour, reasons
		afo.setMemNo(m.getMemNo());
		afo.setMemName(m.getMemName());
		afo.setDeptCode(m.getDeptCode());
		afo.setDocuType('O'); //문서양식 설정

		int result = aprService.insertAprForm(afo);
		
		if(result>0) model.addAttribute("msg", "전자결재 등록 성공");
		else model.addAttribute("msg", "전자결재 등록 실패");
		model.addAttribute("location", "/approvalList.ho");
		
		return "approval/aprResult";
	}
	
	//결재 문서 수정
	@RequestMapping(value="/updateAprCCC.ho") //법인카드사용 수정
	public String approvalUpdateAprCCC(@SessionAttribute("member") Member m, AprFormCCC afc, Model model){
		afc.setMemNo(m.getMemNo());

		int result = aprService.updateAprForm(afc);
		
		if(result>0)model.addAttribute("msg", "전자결재 문서 수정 성공");
		else model.addAttribute("msg", "전자결재 문서 수정 실패");
		model.addAttribute("location", "/approvalList.ho");
		
		return "approval/aprResult";
	}
	
	@RequestMapping(value="/updateAprHol.ho") //연차신청 수정
	public String approvalUpdateAprHol(@SessionAttribute("member") Member m, AprFormHol afh, Model model){
		afh.setMemNo(m.getMemNo());
		int result = aprService.updateAprForm(afh);
		
		if(result>0)model.addAttribute("msg", "전자결재 문서 수정 성공");
		else model.addAttribute("msg", "전자결재 문서 수정 실패");
		model.addAttribute("location", "/approvalList.ho");
		
		return "approval/aprResult";
	}
	
	@RequestMapping(value="/updateAprLazy.ho") //지각불참사유 수정
	public String approvalUpdateAprLazy(@SessionAttribute("member") Member m, AprFormLazy afl, Model model){
		afl.setMemNo(m.getMemNo());

		int result = aprService.updateAprForm(afl);
		
		if(result>0)model.addAttribute("msg", "전자결재 문서 수정 성공");
		else model.addAttribute("msg", "전자결재 문서 수정 실패");
		model.addAttribute("location", "/approvalList.ho");
		
		return "approval/aprResult";
	}
	
	@RequestMapping(value="/updateAprOvt.ho") //연장근무 수정
	public String approvalUpdateAprOvt(@SessionAttribute("member") Member m, AprFormOvt afo, Model model){
		afo.setMemNo(m.getMemNo());

		int result = aprService.updateAprForm(afo);
		
		if(result>0)model.addAttribute("msg", "전자결재 문서 수정 성공");
		else model.addAttribute("msg", "전자결재 문서 수정 실패");
		model.addAttribute("location", "/approvalList.ho");
		
		return "approval/aprResult";
	}
	
	@RequestMapping(value="/aprProcess.ho") //결재 대기
	public ModelAndView approvalProcess(@RequestParam("docuNo") int docuNo, @RequestParam("docuType") String docuType, ModelAndView mav){
		switch(docuType.charAt(0)){
		case 'H' : //연차신청
			AprFormHol afh = aprService.selectOneAprHol(docuNo);
			mav.addObject("docu", afh); 
			mav.setViewName("approval/aprViewHol");
			break;
		case 'O' : //연장근무
			AprFormOvt afo = aprService.selectOneAprOvt(docuNo);
			mav.addObject("docu", afo);
			mav.setViewName("approval/aprViewOvt");
			break;
		case 'L' : //지각불참
			AprFormLazy afl = aprService.selectOneAprLazy(docuNo);
			mav.addObject("docu", afl);
			mav.setViewName("approval/aprViewLazy");
			break;
		case 'C' : //법인카드
			AprFormCCC afc = aprService.selectOneAprCCC(docuNo);
			mav.addObject("docu", afc);
			mav.setViewName("approval/aprViewCCC");
			break;
		}
		
		ArrayList<ApprovalLine> al = aprService.selectOneAprLine(docuNo);
		mav.addObject("aprLine", al);
		ArrayList<ApprovalRef> af = aprService.selectOneAprRef(docuNo); 
		ArrayList<Integer> aprNoList = new ArrayList<Integer>(); //결재선과 결재참조 사번 확보

		for(ApprovalLine apr : al) aprNoList.add(apr.getMemNo());
		for(ApprovalRef apr : af) aprNoList.add(apr.getMemNo());
		mav.addObject("aprNoList", aprNoList);
		
		return mav;
	}
	
	//결재 처리
	@RequestMapping(value="/aprMark.ho")
	public String aprMark(@SessionAttribute("member") Member m, ApprovalLine al, 
			@RequestParam(value="cardType", required=false, defaultValue="0") int cardType, 
			@RequestParam("docuType") String docuType, Model model){
		al.setMemNo(m.getMemNo());
		
		if(cardType!=0){ //결재시 카드 선택 처리
			CCCForm cf = new CCCForm();
			cf.setDocuNo(al.getDocuNo());
			cf.setCardType(cardType);
			aprService.updateCardType(cf); //결재시 카드를 선택한 경우만
		}
		
		int result = aprService.insertAprMark(al, docuType.charAt(0));
		if(result>0) model.addAttribute("msg", "전자결재 처리 성공");
		else model.addAttribute("msg", "전자결재 처리 실패");
		model.addAttribute("location", "/approvalList.ho?listType=W");
		
		return "approval/aprResult";
	}
	
	//결재 완료 조회 View
	@RequestMapping(value="/approval.ho") 
	public ModelAndView approvalView(@RequestParam("docuNo") int docuNo, @RequestParam("docuType") String docuType, ModelAndView mav){

		switch(docuType.charAt(0)){
		case 'H' : //연차신청
			AprFormHol afh = aprService.selectOneAprHol(docuNo);
			mav.addObject("docu", afh); 
			mav.setViewName("approval/aprViewHol");
			break;
		case 'O' : //연장근무
			AprFormOvt afo = aprService.selectOneAprOvt(docuNo);
			mav.addObject("docu", afo);
			mav.setViewName("approval/aprViewOvt");
			break;
		case 'L' : //지각불참
			AprFormLazy afl = aprService.selectOneAprLazy(docuNo);
			mav.addObject("docu", afl);
			mav.setViewName("approval/aprViewLazy");
			break;
		case 'C' : //법인카드
			AprFormCCC afc = aprService.selectOneAprCCC(docuNo);
			mav.addObject("docu", afc);
			mav.setViewName("approval/aprViewCCC");
			break;
		}
		
		//결재선 정보,결재참조정보
		ArrayList<ApprovalLine> al = aprService.selectOneAprLine(docuNo);
		mav.addObject("aprLine", al);
		ArrayList<ApprovalRef> af = aprService.selectOneAprRef(docuNo); 
		ArrayList<Integer> aprNoList = new ArrayList<Integer>(); //결재선과 결재참조 사번 확보

		for(ApprovalLine apr : al) aprNoList.add(apr.getMemNo());
		for(ApprovalRef apr : af) aprNoList.add(apr.getMemNo());
		mav.addObject("aprNoList", aprNoList);
		
		return mav;
	}
	
	//결재 진행중 문서 삭제
	@RequestMapping(value="/deleteApproval.ho")
	public String deleteApproval(@SessionAttribute("member") Member m, @RequestParam("docuNo") int docuNo, Model model){
		//결재가 대기중인 문서에 대해서만 삭제.
		//자기것만 삭제 => 보안정책.
		Approval apr = new Approval();
		apr.setMemNo(m.getMemNo());
		apr.setDocuNo(docuNo);
		
		int result = aprService.deleteApproval(apr);

		if(result>0) model.addAttribute("msg", "전자결재 문서 삭제 성공");
		else model.addAttribute("msg", "전자결재 문서 삭제 실패");
		model.addAttribute("location", "/approvalList.ho");
		
		return "approval/aprResult";
	}
	
	//결재 진행중 문서 수정 양식 가져오기
	@RequestMapping(value="/loadAprForm.ho")
	public ModelAndView loadApprovalForm(@SessionAttribute("member") Member m, @RequestParam("docuNo") int docuNo, 
			@RequestParam("docuType") String docuType, ModelAndView mav){
		ArrayList<AprLineMember> list = null;
		switch(docuType.charAt(0)){
		case 'H' : 
			AprFormHol afh = aprService.selectOneAprHol(docuNo);
			mav.addObject("docu", afh);
			list = aprService.selectMyDeptPeople(m); //연차신청
			mav.setViewName("approval/aprLoadHol");
		break;
		case 'O' : 
			AprFormOvt afo = aprService.selectOneAprOvt(docuNo);
			mav.addObject("docu", afo);
			list = aprService.selectMyDeptPeople(m); //연장근무신청
			mav.setViewName("approval/aprLoadOvt");
			break;
		case 'L' : 
			AprFormLazy afl = aprService.selectOneAprLazy(docuNo);
			mav.addObject("docu", afl);
			list = aprService.selectMyDeptPeople(m); //지각불참사유
			mav.setViewName("approval/aprLoadLazy");
			break;
		case 'C' : 
			AprFormCCC afc = aprService.selectOneAprCCC(docuNo);
			mav.addObject("docu", afc);
			list = aprService.selectCCCLinePeople(m); //법인카드사용신청
			mav.setViewName("approval/aprLoadCCC");
			break;
		}
		mav.addObject("aprLineList",list); //결재선 입력
		ArrayList<ApprovalLine> al = aprService.selectOneAprLine(docuNo); //이미 입력한 결재선 정보
		mav.addObject("aprLine", al);
		ArrayList<ApprovalRef> af = aprService.selectOneAprRef(docuNo); //이미 입력한 참조 정보
		mav.addObject("aprRef",af);
		return mav;
	}
	
}
