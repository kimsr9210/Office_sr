package kr.or.houroffice.personnel.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.common.PageList;
import kr.or.houroffice.common.Sha256Util;
import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.personnel.model.dao.PersonnelDAO;
import kr.or.houroffice.personnel.model.vo.Contact;
import kr.or.houroffice.personnel.model.vo.MemDept;

@Service("parsonnelService")
public class PersonnelServiceImpl implements PersonnelService {

	@Autowired
	@Qualifier(value = "PersonnelDAO")
	private PersonnelDAO pDAO;
	
	@Autowired
	Sha256Util sha256Util;

	@Autowired // 의존성주입
	@Qualifier(value = "sqlSessionTemplate") // 세션
	private SqlSessionTemplate sqlSession;

	// 사내 주소록, 검색(search)
	public PageList<Member> addbookSearch(HttpServletRequest request) {

		/*
		 * if(request.getParameter("currentPage")==null) //보내주는 값이 없을때에는 {
		 * currentPage = 1; //1페이지를 보여주세요 }else { //자유게시판을 사용하는 유저가 2page로 이동하기
		 * 위해서 2page를 클릭하게 된다면? currentPage =
		 * Integer.parseInt(request.getParameter("currentPage")); //request 요청 }
		 */

		// 페이지 네비
		int naviCountPerPage = 5; // PageNavi 값이 몇개씩 보여줄 것인지
		int recordCountPerpage = 10; // 한 페이지당 몇개씩 게시물이 보이게 할 것인지를 정함.

		// 웹상에서 가져온 데이터 저장
		String selectBox = request.getParameter("selectBox");
		String searchText = request.getParameter("searchText");

		System.out.println(searchText); // 가져온 결과값 출력

		// 페이징처리
		int currentPage;
		if (request.getParameter("currentPage") == null) // 보내주는 값이 없을때에는
		{
			currentPage = 1; // 1페이지를 보여주세요
		} else { // 자유게시판을 사용하는 유저가 2page로 이동하기 위해서 2page를 클릭하게 된다면?
			currentPage = Integer.parseInt(request.getParameter("currentPage")); // request
																					// 요청
		}

		int cot = pDAO.addbookCount(sqlSession, selectBox, searchText);
		ArrayList<Member> list = pDAO.addbookSearch(sqlSession, selectBox, searchText, currentPage, recordCountPerpage,
				naviCountPerPage);
		String pageNavi = pDAO.getPageNavi(sqlSession, "addbook", selectBox, searchText, currentPage,
				recordCountPerpage, naviCountPerPage, cot);

		System.out.println(pageNavi);

		PageList<Member> pn = new PageList<Member>();
		pn.setList(list);
		pn.setPageNavi(pageNavi);
		return pn;
	}

	// 개인주소록, 검색(search)
	public PageList<Contact> myaddbook(HttpServletRequest request, int memNo) {

		int naviCountPerPage = 5; // PageNavi 값이 몇개씩 보여줄 것인지
		int recordCountPerpage = 10; // 한 페이지당 몇개씩 게시물이 보이게 할 것인지를 정함.

		// 웹상에서 가져온 데이터 저장
		String selectBox = request.getParameter("selectBox");
		String searchText = request.getParameter("searchText");

		System.out.println(searchText); // 가져온 결과값 출력

		// 페이징처리
		int currentPage;
		if (request.getParameter("currentPage") == null) // 보내주는 값이 없을때에는
		{
			currentPage = 1; // 1페이지를 보여주세요
		} else { // 자유게시판을 사용하는 유저가 2page로 이동하기 위해서 2page를 클릭하게 된다면?
			currentPage = Integer.parseInt(request.getParameter("currentPage")); // request
																					// 요청
		}

		int cot = pDAO.myAddbookCount(sqlSession, selectBox, searchText, memNo);
		ArrayList<Contact> list = pDAO.myaddbookSearch(sqlSession, selectBox, searchText, memNo, currentPage,
				recordCountPerpage, naviCountPerPage);
		String pageNavi = pDAO.getPageNavi(sqlSession, "myaddbook", selectBox, searchText, currentPage,
				recordCountPerpage, naviCountPerPage, cot);

		PageList<Contact> pn = new PageList<Contact>();
		pn.setList(list);
		pn.setPageNavi(pageNavi);

		return pn;
	}

	// 개인주소록에서 다이얼로그에 받아온 값
	public void insertMyaddbook(Map<String, Object> params) {
		pDAO.insertMyaddbook(sqlSession, params);
	}

	// 개인주소록 수정(update)
	public void updateMyaddbook(Map<String, Object> params) {
		pDAO.updateMyaddbook(sqlSession, params);
	}

	// 개인주소록 삭제(update)
	public void deleteMyaddbook(String ck) {
		String[] arr = ck.split(",");	// arr => {53,56}
		for(int i=0; i<arr.length;i++){
			pDAO.deleteMyaddbook(sqlSession, arr[i]);
		}
	}

	// 내 개인정보 (마이페이지)
	public MemDept mypage(int memNo) {
		MemDept md = pDAO.mypage(sqlSession, memNo);
		return md;
	}

	// 인사정보
	public ArrayList<MemDept> information(int memNo) {
		ArrayList<MemDept> list = pDAO.information(sqlSession, memNo);
		return list;
	}

	public void mypageChange(Map<String, Object> map) {
		pDAO.mypageChange(sqlSession,map);
	}

	public int inforPwChange(HttpServletRequest request, int memNo) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memNo", memNo);
		map.put("memPwd", sha256Util.encryData(request.getParameter("memPwd")));
		map.put("updateMemPwd", sha256Util.encryData(request.getParameter("updateMemPwd")));
		
		int result = pDAO.checkPwd(sqlSession,map);
		if(result > 0){
			pDAO.inforPwChange(sqlSession,map);
		}
		return result;
	}

	public int photoUpdate(int memNo) {
		int result = pDAO.photoUpdate(sqlSession,memNo);
		return result;
		
	}

}
