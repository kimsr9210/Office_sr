package kr.or.houroffice.board.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.board.model.vo.BoardPost;
import kr.or.houroffice.common.Page;

@Repository("companyRuleDAO")
public class CompanyRuleDAO {

	public int insertPost(SqlSessionTemplate sqlSession, BoardPost bp) {
		return sqlSession.insert("board.addPostCompanyRule",bp);
	}

	public List<Object> selectBoardList(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("start", ((Page)map.get("page")).getStartPage());
		map.put("end", ((Page)map.get("page")).getEndPage());
		return sqlSession.selectList("board.allCompanyRuleList",map);
	}
	public List<Object> selectSearchBoardList(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("start", ((Page)map.get("page")).getStartPage());
		map.put("end", ((Page)map.get("page")).getEndPage());
		return sqlSession.selectList("board.allCompanyRuleList",map);
	}

	public Page getPageNavi(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		Page page = (Page)map.get("page");
		
		map.put("boardType", "COMPANY_RULE"); // 게시판 테이블 명
		
		int postTotalCount = selectCountAllPostList(sqlSession,map); // 전체 게시물을 구하기 위한 메소드
		page.setPostTotalCount(postTotalCount); // 전체 게시물 수 셋팅
		int pageTotalCount = page.getPageTotalCount(); // 전체페이지를 저장하는 변수
		// 현재 페이지 번호 구하기
		// startNavi = ((현재페이지-1)/보여질 navi개수) * 보여질navi개수 +1;
		int startNavi = page.getStartNavi();
		// endNavi = 시작navi번호 + 보여질 navi개수 - 1;
		int endNavi = page.getEndNavi();
		
		String howPage = null; // view 페이지 선택
		if(((String)map.get("viewType")).equals("client")){
			howPage = "";
		}else{
			howPage = "admin_tap_";
		}
		
		StringBuilder sb = new StringBuilder();
		if(map.get("searchType")==null){
			// 만약 첫번째 pageNavi가 아니라면 '<' 모양을 추가해라 (첫번째 pageNavi이면 추가하지 말아라)
			
			if(startNavi != 1) { //href='/myReviewNote.rw?libraryOwner="+memberId+"&currentPage="+(startNavi-1)+"'
				sb.append("<li class='page-list'><a href='/"+howPage+"allCompanyRulePage.ho?currentPage="+(startNavi-1)+"' class='page-link'>◀</a></li>");
			}
							
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==page.getCurrentPage()) {
					sb.append("<li class='page-list'><a href='/"+howPage+"allCompanyRulePage.ho?currentPage="+i+"' class='page-link'><B>"+i+"</B></a></li>");
				}else {
					sb.append("<li class='page-list'><a href='/"+howPage+"allCompanyRulePage.ho?currentPage="+i+"' class='page-link'>"+i+"</a></li>");
				}
			}
					
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a href='/"+howPage+"allCompanyRulePage.ho?currentPage="+(startNavi+1)+"' class='page-link'>▶</a></li>");
			}
		}else{ // 여기는 검색 페이지 페이징처리======================================================================================================
			//searchType=_title&keyword=이&deptCode=D2+
			if(startNavi != 1) { //href='/myReviewNote.rw?libraryOwner="+memberId+"&currentPage="+(startNavi-1)+"'
				sb.append("<li class='page-list'><a href='/"+howPage+"allCompanyRulePage.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&currentPage="+(startNavi-1)+"' class='page-link'>◀</a></li>");
			}
							
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==page.getCurrentPage()) {
					sb.append("<li class='page-list'><a href='/"+howPage+"allCompanyRulePage.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&currentPage="+i+"' class='page-link'><B>"+i+"</B></a></li>");
				}else {
					sb.append("<li class='page-list'><a href='/"+howPage+"allCompanyRulePage.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&currentPage="+i+"' class='page-link'>"+i+"</a></li>");
				}
			}
					
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a href='/"+howPage+"allCompanyRulePage.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&currentPage="+(startNavi+1)+"' class='page-link'>▶</a></li>");
			}
		}
		
		page.setUrl(sb+"");
		
		return page;
	}
	
	
	// 총 게시물 수
	private int selectCountAllPostList(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.selectOne("board.countPostList", map);
	}

	public Object selectOnePost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.selectOne("board.getOnePostCompanyRule",map);
	}

	public int selectNextPost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "COMPANY_RULE"); // 테이블명
		map.put("type", "RULE");		// 컬럼 타입
		if(sqlSession.selectOne("board.nextPost",map)!=null){
			return sqlSession.selectOne("board.nextPost",map);
		}
		return 0;
	}

	public int selectPrevPost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "COMPANY_RULE"); // 테이블명
		map.put("type", "RULE");		// 컬럼 타입
		if(sqlSession.selectOne("board.prevPost",map)!=null){
			return sqlSession.selectOne("board.prevPost",map);
		}
		return 0;
	}

	public int updatePost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "COMPANY_RULE"); // 테이블 명
		map.put("type", "RULE_");	// 컬럼 명
		return sqlSession.update("board.modifyPost",map);
	}

	public int deletePost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.update("board.deleteRule",map);
	}
	
}
