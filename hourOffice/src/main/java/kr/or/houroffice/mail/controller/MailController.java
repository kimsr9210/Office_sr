package kr.or.houroffice.mail.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import kr.or.houroffice.common.PageList;
import kr.or.houroffice.mail.model.service.MailServiceImpl;
import kr.or.houroffice.mail.model.vo.Mail;
import kr.or.houroffice.mail.model.vo.MailFile;
import kr.or.houroffice.mail.model.vo.MailGetter;
import kr.or.houroffice.mail.model.vo.MailInfo;
import kr.or.houroffice.mail.model.vo.MailList;
import kr.or.houroffice.mail.model.vo.MailListPage;
import kr.or.houroffice.mail.model.vo.MailReceive;
import kr.or.houroffice.mail.model.vo.SendingMail;
import kr.or.houroffice.member.model.service.AdminMemberService;
import kr.or.houroffice.member.model.vo.Department;
import kr.or.houroffice.member.model.vo.Member;

@Controller
public class MailController {
	
	@Autowired
	@Qualifier("mailService")
	private MailServiceImpl mailService;
	
	@Autowired
	@Qualifier(value="adminMemberService")
	private AdminMemberService amService;
	
	@Autowired //파일 경로 처리
	private ResourceLoader resourceLoader;
	
	@RequestMapping("/address.ho")
	public String loadAddress(Model model){ //맨처음 호출
		ArrayList<Department> deptList = amService.selectDeptAll(); //부서 정보
		ArrayList<Member> memList = mailService.selectMemberAll();
		
		model.addAttribute("deptList", deptList);
		model.addAttribute("memList",memList);
		
		return "mail/address";
	}
	
	@RequestMapping("/deptAddress.ho")
	public void loadDeptAddress(@RequestParam("deptCode") String deptCode, HttpServletResponse res) throws IOException{
		if(deptCode.length()==0) deptCode = null;
		ArrayList<Member> memList = mailService.selectDeptMember(deptCode);
		
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		
		new Gson().toJson(memList,res.getWriter());
	}

	@RequestMapping("/mailWritePage.ho")
	public String mailWritePage(@SessionAttribute("member") Member m) throws IOException{
		
		return "mail/mailWrite";
	}
	
	@RequestMapping("/sendMail.ho") //메일 보내기
	public String sendMail(@SessionAttribute("member") Member m, SendingMail sm, Model model) throws Exception, IOException{
		//필요한 정보 : 보내는 사람, 받는 사람[], 파일, 제목, 내용, 참조목록[]  
		sm.setSender(m.getMemNo());
		int result = mailService.insertMail(sm);
		
		if(result>0) model.addAttribute("msg", "메일 발송 성공");
		else model.addAttribute("msg", "메일 발송 실패");
		model.addAttribute("location", "/mailList.ho");
		
		return "mail/mailResult";
	}
	
	@RequestMapping("/mailList.ho")
	public ModelAndView mailList(@SessionAttribute("member") Member m, @RequestParam(value="page", defaultValue="1") int page,
			@RequestParam(value="listType", defaultValue="R") String listType, 
			@RequestParam(value="searchType",required=false, defaultValue="") String searchType,
			@RequestParam(value="keyword",required=false,defaultValue="") String keyword, ModelAndView mav){
		//필요정보 : 리스트 타입, 사번, 총 갯수, 메일 정보, 페이지 네비
		
		MailListPage mlp = new MailListPage(); //페이징 처리 정보를 위한 객체
		mlp.setCurrentPage(page);
		mlp.setRecordCountPerPage(10);//한페이지에 10개
		mlp.setNaviCountPerPage(5);//네비에는 5개씩
		mlp.setMemNo(m.getMemNo());//조회할 사번
		mlp.setListType(listType.charAt(0)); //listType도 저장
		
		if(!keyword.isEmpty()){//검색할 내용이 있으면
			mlp.setSearchType(searchType);
			mlp.setKeyword(keyword);
		}
		HashMap<String,Integer> mailCount = null;
		switch(listType.charAt(0)){
		case 'R' : //받은 메일
			mlp.setUrl("/mailList.ho?listType=R");
			mav.setViewName("mail/mailListRec");
			break; 
		case 'S' : //보낸 메일
			mlp.setUrl("/mailList.ho?listType=S");
			mav.setViewName("mail/mailListSend");
			break; 
		case 'F' : //참조 메일
			mlp.setUrl("/mailList.ho?listType=F");
			mav.setViewName("mail/mailListRef");
			break; 
		case 'K' : //보관 메일
			mlp.setUrl("/mailList.ho?listType=K");
			mav.setViewName("mail/mailListKeep");
			break; 
		case 'D' : //삭제 메일
			mlp.setUrl("/mailList.ho?listType=D");
			mav.setViewName("mail/mailListDel");
			break; 
		}
		
		mailCount = mailService.selectMailCount(mlp); //메일 갯수
		PageList list = mailService.selectMailList(mlp);
		
		mav.addObject("mailList", list);
		mav.addObject("mailCount",mailCount);
		return mav;
	}
	
	@RequestMapping("/mailView.ho")
	public String loadMailPage(@SessionAttribute("member") Member m, @RequestParam("mailNo") int mailNo, 
			@RequestParam("other") int memNo, @RequestParam(value="readYN", required=false, defaultValue="Y") String readYN, 
			@RequestParam(value="listType", defaultValue="R") String listType, Model model){
		//필요 정보 : 기본 메일 정보, 받은 사람에 대한 정보. 참조/수신 구분 필요.
		MailReceive mr = new MailReceive();
		mr.setMailNo(mailNo);
		mr.setMemNo(memNo);
		
		if(listType.length()==2){ mr.setListType(listType.charAt(0));
		}else{ mr.setListType(listType.charAt(0)); }
		
		if(readYN.charAt(0)=='N'){//읽음 처리
			mr.setReadYN('Y');
			mailService.updateReadYN(mr);
		}
		
		MailGetter mg = mailService.selectOneMail(mr);
		model.addAttribute("mail", mg);
		model.addAttribute("listType",listType); 
		return "mail/mailView";
	}
	
	@RequestMapping("/changeReadYN.ho")
	public void chagngeReadYN(@SessionAttribute("member") Member m, @RequestParam("readYN") String readYN, @RequestParam("mailNo") int mailNo, 
			@RequestParam(value="listType", defaultValue="R") String listType, HttpServletResponse res) throws IOException{
		MailReceive mr = new MailReceive();
		mr.setMemNo(m.getMemNo());
		mr.setMailNo(mailNo);
		mr.setReadYN(readYN.charAt(0));
		mr.setListType(listType.charAt(0)); //참조인지 수신인지 구분
		int result = mailService.updateReadYN(mr);

		if(result>0) res.getWriter().print(true);
		else res.getWriter().print(false); 
	}
	
	@RequestMapping("/changeKeepYN.ho")
	public void chagngeKeepYN(@SessionAttribute("member") Member m, @RequestParam("keepYN") String keepYN, @RequestParam("mailNo") int mailNo, 
			@RequestParam(value="listType", defaultValue="R") String listType, HttpServletResponse res) throws IOException{
		MailReceive mr = new MailReceive();
		mr.setMemNo(m.getMemNo());
		mr.setMailNo(mailNo);
		mr.setKeepYN(keepYN.charAt(0));
		mr.setListType(listType.charAt(0)); //참조인지 수신인지 구분
		
		int result = mailService.updateKeepYN(mr);

		if(result>0) res.getWriter().print(true);
		else res.getWriter().print(false); 
	}
	
	@RequestMapping("/deleteMail.ho") //메일 삭제 - 수신함/참조함/발신함만(단일 테이블 or 1개 처리)
	public void deleteMail(@SessionAttribute("member") Member m, @RequestParam("mailNoList") int[] mailNoList,  
			@RequestParam(value="listType", defaultValue="R") String listType, HttpServletResponse res) throws IOException{
		MailInfo mi = new MailInfo();
		mi.setMemNo(m.getMemNo());
		mi.setMailNoList(mailNoList);
		mi.setListType(listType.charAt(0));
		
		int result = mailService.deleteMail(mi);
		
		if(result>0) res.getWriter().print(true);
		else res.getWriter().print(false); 
	}
	
	@RequestMapping("/sendToOne.ho") //메일 답장
	public String sendToOne(@SessionAttribute("member") Member m, @RequestParam("memNo") int memNo, 
			@RequestParam("memName") String memName, Model model){
		model.addAttribute("memNo", memNo);
		model.addAttribute("memName", memName);
		
		return "mail/mailWriteToOne";
	}
	
	@RequestMapping("/transferMail.ho") //메일 전달
	public String transferMail(@SessionAttribute("member") Member m, @RequestParam("mailNo") int mailNo, Model model){
		MailGetter mg = mailService.selectTransferMail(mailNo);
		model.addAttribute("mail", mg);
		return "mail/mailTransfer";
	}
	
	@RequestMapping("/resendMail.ho") //재발송
	public void resendMail(@SessionAttribute("member") Member m, 
			@RequestParam("mailNoList") int[] mailNoList, HttpServletResponse res) throws IOException{
		MailInfo mi = new MailInfo();
		mi.setMemNo(m.getMemNo());
		mi.setMailNoList(mailNoList);
		
		int result = mailService.insertResendMail(mi);
		
		if(result>0){ res.getWriter().print(true);
		}else{ res.getWriter().print(false); }
	}
	
	@RequestMapping("/fileDownload.ho")
	public void fileDownload(@SessionAttribute("member") Member m, @RequestParam("attachNo") int attachNo,
			HttpServletResponse response) throws IOException{
		MailFile mf = mailService.selectOneFile(attachNo);
		
		if(mf!=null) {
			String realUploadPath = resourceLoader.getResource("/").getURI().getPath(); //webapp까지의 실제 위치

			//파일이름+파일 전송을 위한 웹환경 셋팅			
			File file = new File(realUploadPath + mf.getFilePath()); //파일 경로를 가져와서(상대경로) 물리적으로 접근함
			response.setContentType("application/octet-stream");//웹브라우저를 통해서 데이터를 Binary타입으로 처리
			response.setContentLengthLong(mf.getFileSize());//파일의 사이즈
			String fileName = new String(mf.getOriginalFilename().getBytes(),"ISO-8859-1");//파일 이름 인코딩
			response.setHeader("Content-Disposition", "attachment;filename="+fileName);//파일이름을 http header를 통해서 전달

			//실제 파일이 가지고 있는 데이터를 보내는 작업
			FileInputStream fileIn = new FileInputStream(file);//파일 데이터를 가져올 수 있는 Stream 생성.
			OutputStream out = response.getOutputStream();//클라이언트에게 전달할 통로 생성
			byte[] outputByte = new byte[4096];//4KB씩 처리
			
			//inputStream으로 데이터를 읽어다가 output스트림으로 전송하기
			while(fileIn.read(outputByte,0,4096)!=-1) {	//4kb 읽어서 처리
				out.write(outputByte,0,4096);
			}
			
			fileIn.close();
			out.close();
			
		}else {	response.getWriter().print("<script>alert('파일을 다운받을 수 없습니다.');</script>"); }
		
	}
	
	//일괄 처리 기능
	@RequestMapping("/allChange.ho")
	public void allChangeMailList(@SessionAttribute("member") Member m, @RequestParam("listType") String[] listType, 
			@RequestParam("mailNoList") int[] mailNoList, @RequestParam("ptype") String ptype, HttpServletResponse res) throws IOException{

		//수신 메일과 참조메일 분리 처리
		MailList mlc = new MailList();
		MailList mlf = new MailList();
		mlc.setMemNo(m.getMemNo());
		mlf.setMemNo(m.getMemNo());
		mlc.setListType('R');
		mlf.setListType('F');
		
		ArrayList<Integer> recMailNoList = new ArrayList<Integer>();
		ArrayList<Integer> refMailNoList = new ArrayList<Integer>();
		
		if(listType.length==1){//수신메일함이나 참조메일함의 경우, 일괄 등록
			if(listType[0].charAt(0)=='R'){
				for(int num : mailNoList) recMailNoList.add(num);
			}else if(listType[0].charAt(0)=='F'){
				for(int num : mailNoList) refMailNoList.add(num);
			}
		}else{//그외 메일함의 경우 리스트 타입을 개별 확인해서 처리
			for(int i=0; i<listType.length; i++){
				if(listType[i].charAt(0)=='R')	recMailNoList.add(mailNoList[i]);
				else if(listType[i].charAt(0)=='F') refMailNoList.add(mailNoList[i]);
			}
		}
		
		mlc.setMailNoList(recMailNoList);
		mlf.setMailNoList(refMailNoList);
		
		int result1 = 0;
		int result2 = 0;
		
		switch(ptype.charAt(0)){
		case 'D' : 
			if(recMailNoList.size()>0) result1 = mailService.deleteMailList(mlc); //일괄 삭제
			if(refMailNoList.size()>0) result2 = mailService.deleteMailList(mlf); //일괄 삭제
			break;
		case 'T' : 
			if(recMailNoList.size()>0) result1 = mailService.updateRestoreMailList(mlc); //일괄 복구
			if(refMailNoList.size()>0) result2 = mailService.updateRestoreMailList(mlf); //일괄 복구
			break;
		case 'P' : 
			if(recMailNoList.size()>0) result1 = mailService.deletePermMailList(mlc); //일괄 영구 삭제
			if(refMailNoList.size()>0) result2 = mailService.deletePermMailList(mlf); //일괄 영구 삭제
			break;
		case 'R' : 
			if(recMailNoList.size()>0) result1 = mailService.updateReadMailList(mlc); //일괄 읽음 처리
			if(refMailNoList.size()>0) result2 = mailService.updateReadMailList(mlf); //일괄 읽음 처리
		}
		
		if(result1+result2>0) res.getWriter().print(true);
		else res.getWriter().print(false); 
	}
}
