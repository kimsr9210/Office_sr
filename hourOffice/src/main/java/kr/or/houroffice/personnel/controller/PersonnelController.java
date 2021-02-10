package kr.or.houroffice.personnel.controller;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.personnel.model.service.PersonnelServiceImpl;
import kr.or.houroffice.personnel.model.vo.MemDept;

@Controller
public class PersonnelController {
	
	@Autowired
	@Qualifier(value="parsonnelService")
	private PersonnelServiceImpl pService;
	
	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	//사원 전체 주소록 
	@RequestMapping(value = "/addbook.ho") 
	public ModelAndView addBook() {
		ArrayList<Member> list = pService.selectAddbook();

		// 데이터를 반환 + 페이지를 이동
		// 1. Model // 데이터 반환만 하기 때문에 return 구문을 통해서 이동할 페이지를 명시 해야 함
		// 2. ModelAndView // 데이터 반환 + 페이지 이동을 정의할 수 있으며, return시 ModelAndView를
		// 리턴해주어야 함
		ModelAndView mav = new ModelAndView();

		mav.addObject("list", list);
		mav.setViewName("personnel/addbook"); // ViewResolver에 의해서 경로가 최종 완성됨
		return mav;
	}

	@RequestMapping(value = "/myaddbook.ho")
	public String mybook(HttpSession session, HttpServletRequest request ) {	
		return "personnel/myaddbook";}
	
	@RequestMapping(value = "/myaddbook.ho", method=RequestMethod.POST)
	public String mybook(@RequestBody Map<String, Object> params, HttpSession session, HttpServletRequest request ) {
		//다이얼 로그에서 가져온 결과값 출력
		System.out.println(params);
		
		int result = pService.insertMyaddbook(params);
		
		
		return "personnel/myaddbook";
	}

	@RequestMapping(value = "/mypage.ho")
	public String mypage() {
		return "personnel/mypage";
	}
	
	//내인사정보 , 쿼리문다시하기 !
	@RequestMapping(value = "/information.ho")
	public String information(@SessionAttribute("member") Member m, HttpSession session) {
		
		MemDept memDept = pService.information(m.getMemNo());
//
//		//get 꺼내옴 , set 놓다/보내줌 , put 저장 
		session.setAttribute("memDept", memDept);
		
		return "personnel/information";
	}
	
	//사원 전체 주소록 검색(search) 결과
	@RequestMapping(value = "/addbookSearch.ho")
	public String addbookSearch(Member m, HttpServletRequest request, Model model){
		
		//비즈니스로직
		ArrayList<Member> list = pService.addbookSearch(request);
		
		//결과값 보내주기 ! ㅜ_ㅜ
		if(list != null){
			//model.addAttribute("countAll",countAll);
			//model.addAttribute("pageNavi", pageNavi);
			model.addAttribute("list",list);	
		}
		return "personnel/addbook";
	}

}
