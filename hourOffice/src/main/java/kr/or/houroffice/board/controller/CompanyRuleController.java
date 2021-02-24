package kr.or.houroffice.board.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.or.houroffice.board.model.service.CompanyRuleService;
import kr.or.houroffice.board.model.vo.BoardPost;
import kr.or.houroffice.common.Page;
import kr.or.houroffice.member.model.vo.Member;

@Controller
public class CompanyRuleController {

	@Resource(name="companyRuleService")
	private CompanyRuleService bService;
	
	private Page page;
	
	// 사용자 사내규정 - 목록
	@RequestMapping(value="/allCompanyRulePage.ho")
	public String postListPage(Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m!=null){
			HashMap<String, Object> map = listPage(request,"client");
			
			model.addAttribute("list",map.get("list"));
			model.addAttribute("pageNavi",map.get("pageNavi"));
			return "company_rule/allCompanyRulePage";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 사내규정 목록 검색
	@RequestMapping(value="/searchCompanyRule.ho")
	public String searchRule(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword,
							Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m!=null){
				
			HashMap<String, Object> map = searchListPage(searchType,keyword,request,"client");
			
			model.addAttribute("list",map.get("list"));
			model.addAttribute("pageNavi",map.get("pageNavi"));
			
			return "company_rule/allCompanyRulePage";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 사용자 사내규정 - 게시글 
	@RequestMapping(value="/companyRule.ho")
	public String companyRule(@RequestParam("ruleNo") int postNo, Model model, @SessionAttribute("member") Member m){
		if(m!=null){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("postNo", postNo);
			BoardPost bp = (BoardPost)bService.selectOnePost(map); // 게시글 정보
			if(bp!=null){
				// 다음글
				int nextPostNo = bService.selectNextPost(map);
				// 이전글
				int prevPostNo = bService.selectPrevPost(map);
				
				model.addAttribute("bp",bp);
				model.addAttribute("nextPost",nextPostNo);
				model.addAttribute("prevPost",prevPostNo);
			
				return "company_rule/companyRule";
			}// 찾는 게시물이 없다면 ↓
			model.addAttribute("msg","해당 게시물이 없습니다.");
			model.addAttribute("location","/allNoticePage.ho");
			return "result";
			
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	
	// 관리자 ------------------------------------------------------------------------------
	
	// 관리자 사내규정 - 목록
	@RequestMapping(value="/admin_tap_allCompanyRulePage.ho")
	public String adminPostListPage(Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				HashMap<String, Object> map = listPage(request,"admin");
				
				model.addAttribute("list",map.get("list"));
				model.addAttribute("pageNavi",map.get("pageNavi"));
				
				return "admin_tap/general_affairs_department/allCompanyRulePage";
			}// 관리자 권한이 없다면 ↓
			Calendar cal = Calendar.getInstance();
			int todayMon = cal.get(Calendar.MONTH) + 1;
			model.addAttribute("location","/main.ho?todayMon="+todayMon);
			model.addAttribute("msg","접근 권한이 없습니다.");
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 사내규정 목록 검색
	@RequestMapping(value="/admin_tap_searchCompanyRule.ho")
	public String adminSearchRule(@RequestParam("searchType") String searchType, @RequestParam("keyword") String keyword,
							Model model, HttpServletRequest request, @SessionAttribute("member") Member m){
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				
				HashMap<String, Object> map = searchListPage(searchType,keyword,request,"admin");
				
				model.addAttribute("list",map.get("list"));
				model.addAttribute("pageNavi",map.get("pageNavi"));
				
				return "admin_tap/general_affairs_department/allCompanyRulePage";
			}// 관리자 권한이 없다면 ↓
			Calendar cal = Calendar.getInstance();
			int todayMon = cal.get(Calendar.MONTH) + 1;
			model.addAttribute("location","/main.ho?todayMon="+todayMon);
			model.addAttribute("msg","접근 권한이 없습니다.");
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
		
	// 관리자 사내규정 - 새글쓰기
	@RequestMapping(value="/admin_tap_writeCompanyRule.ho")
	public String adminWritePostPage(Model model, @SessionAttribute("member") Member m){
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				return "admin_tap/general_affairs_department/writePostCompanyRule";
			}// 관리자 권한이 없다면 ↓
			Calendar cal = Calendar.getInstance();
			int todayMon = cal.get(Calendar.MONTH) + 1;
			model.addAttribute("location","/main.ho?todayMon="+todayMon);
			model.addAttribute("msg","접근 권한이 없습니다.");
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 사내규정 게시글 insert
	@RequestMapping(value="/savePostCompanyRule.ho")
	public String addPost(BoardPost bp, Model model, @SessionAttribute("member") Member m){
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				int result = bService.insertPost(bp);
				
				if(result>0){
					model.addAttribute("msg","새 글이 등록되었습니다.");
				}else{ // 게시글 디비 저장에 실패했다면 ↓
					model.addAttribute("msg","새 글 등록에 실패하였습니다. 지속적인 실패시 관리자에 문의하세요.");
				}
				model.addAttribute("location","/admin_tap_allCompanyRulePage.ho");
			}else{// 관리자 권한이 없다면 ↓
				Calendar cal = Calendar.getInstance();
				int todayMon = cal.get(Calendar.MONTH) + 1;
				model.addAttribute("location","/main.ho?todayMon="+todayMon);
				model.addAttribute("msg","접근 권한이 없습니다.");
			}
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 사내규정 - 게시글 수정 page
	@RequestMapping(value="/admin_tap_companyRuleModify.ho")
	public String adminCompanyRule(@RequestParam("ruleNo") int postNo, Model model, @SessionAttribute("member") Member m){
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("postNo", postNo);
				BoardPost bp = (BoardPost)bService.selectOnePost(map); // 게시글 정보
				if(bp!=null){
					model.addAttribute("bp",bp);
				
					return "admin_tap/general_affairs_department/companyRuleModify";
				}// 찾는 게시물이 없다면 ↓
				model.addAttribute("msg","해당 게시물이 없습니다.");
				model.addAttribute("location","/allNoticePage.ho");
			}else{// 관리자 권한이 없다면 ↓
				Calendar cal = Calendar.getInstance();
				int todayMon = cal.get(Calendar.MONTH) + 1;
				model.addAttribute("location","/main.ho?todayMon="+todayMon);
				model.addAttribute("msg","접근 권한이 없습니다.");
			}
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	// 사내규정 - 게시글 수정
	@RequestMapping(value="/admin_tap_updatePostCompanyRule.ho")
	public String adminUpdatePostCompanyRule(BoardPost bp, Model model, HttpServletRequest request, @SessionAttribute("member") Member m) throws IOException{
		if(m!=null){
			if(m.getMemRightCode()=='D'){
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("bp", bp);
				int result = bService.updatePost(map);
				if(result>0){
					model.addAttribute("msg","글 수정이 성공하였습니다.");
					model.addAttribute("location","/admin_tap_companyRuleModify.ho?ruleNo="+bp.getPostNo());
				}else{// 글 수정에 실패했다면 ↓
					model.addAttribute("msg","글 수정에 실패하였습니다. \n지속적인 실패시 관리자에 문의하세요.");
					model.addAttribute("location","/admin_tap_companyRuleModify.ho?ruleNo="+bp.getPostNo());
				}
			}else{// 관리자 권한이 없다면 ↓
				Calendar cal = Calendar.getInstance();
				int todayMon = cal.get(Calendar.MONTH) + 1;
				model.addAttribute("location","/main.ho?todayMon="+todayMon);
				model.addAttribute("msg","접근 권한이 없습니다.");
			}
			return "result";
		}// 로그인을 하지 않았다면 ↓
		return "redirect:login.jsp";
	}
	
	// 사내규정 게시글 삭제(ajax)
	@RequestMapping(value="/admin_tap_deleteCompanyRule.ho")
	public void adminDeletePost(@RequestParam(value="postNoList[]") List<String> postNoList,HttpServletResponse response, @SessionAttribute("member") Member m) throws IOException{
		if(m.getMemRightCode()=='D'){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("postNo", postNoList);
			int result =  bService.deletePost(map);
			if(result>0){
				response.getWriter().print(true);
			}else {
				response.getWriter().print(false);
			}
		}
	}
	// 목록 뽑아오는 공통 메소드
	private HashMap<String, Object> listPage(HttpServletRequest request, String viewType){
		Page page = createPage(request,10,10); // (request , 한 페이지당 게시물수 , 한 페이지당 보여줄 네비 수)
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("viewType", viewType);
		
		List<Object> list = bService.selectBoardList(map);
		Page pageNavi = bService.getPageNavi(map);
		
		map.put("list", list);
		map.put("pageNavi", pageNavi);
		return map;
	}
		
		// 검색 목록 뽑아오는 공통 메소드
	private HashMap<String, Object> searchListPage(String searchType, String keyword, HttpServletRequest request, String viewType){
		Page page = createPage(request,10,10);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		map.put("viewType", viewType);
		if(!searchType.equals("both")){
			map.put("searchType", "RULE_"+searchType); // 쿼리문 데이터
		}else{
			map.put("searchType", searchType); // 쿼리문 데이터
		}
		map.put("type", "RULE_");
		map.put("keyword", "%"+keyword+"%"); // 쿼리문 데이터
		map.put("searchTypeOrg", searchType); // 네비 데이터
		map.put("keywordOrg", keyword); // 네비 데이터
		
		List<Object> list = bService.selectSearchBoardList(map);
		Page pageNavi = bService.getPageNavi(map);
		
		map.put("list", list);
		map.put("pageNavi", pageNavi);
		return map;
	}
	// 페이징처리 할 때 필요한 페이지 객체 만들기
	private Page createPage(HttpServletRequest request,int RCPP, int NCPP){
		page = new Page();
		
		int currentPage; // 현재 페이지값을 가지고 있는 변수 - 페이징 처리를 위한 변수
		if(request.getParameter("currentPage")==null) {
			page.setCurrentPage(1);
		}else {
			page.setCurrentPage(Integer.parseInt(request.getParameter("currentPage")));
		}
		page.setRecordCountPerPage(RCPP); // 한 페이지당 몇개의 게시물이 보이게 될 것인지 - 페이징 처리를 위한 변수
		page.setNaviCountPerPage(NCPP); // page Navi값이 몇개씩 보여줄 것인지 - 페이징 처리(네비)를 위한 변수
	
		return page;
	}
	
}
