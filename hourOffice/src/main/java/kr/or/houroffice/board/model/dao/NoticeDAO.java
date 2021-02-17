package kr.or.houroffice.board.model.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.BoardPost;
import kr.or.houroffice.board.model.vo.PartBoard;
import kr.or.houroffice.common.Page;

@Repository("noticeBDAO")
public class NoticeDAO {
	// 공지사항 - 목록 page selectAll
	public List<Object> selectBoardList(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("start", ((Page)map.get("page")).getStartPage());
		map.put("end", ((Page)map.get("page")).getEndPage());
		return sqlSession.selectList("board.allNoticeList",map);
	}
	// 공지사항 - 목록 검색 selectAll
	public List<Object> selectSearchBoardList(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("start", ((Page)map.get("page")).getStartPage());
		map.put("end", ((Page)map.get("page")).getEndPage());
		return sqlSession.selectList("board.allNoticeList",map);
	}
	// 공지사항 - 게시물 select 
	public Object selectOnePost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.selectOne("board.getOnePostNotice",map);
	}
	// 공지사항 - 파일 select
	public BoardFile selectPostFile(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("type", "NOT_");
		return sqlSession.selectOne("board.getPostFile",map);
	}
	// 공지사항 - 새글쓰기 - 고유번호 채번
	public Integer selectNumber(SqlSessionTemplate sqlSession) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("boardType", "ALL_NOTICE_");
		return sqlSession.selectOne("board.selectNumber",map);
	}
	// 공지사항 - 새글쓰기 insert
	public int insertPost(SqlSessionTemplate sqlSession, BoardPost bp) {
		return sqlSession.insert("board.addPostNotice",bp);
	}
	// 공지사항 - 새글쓰기 파일 insert
	public int insertPostFile(SqlSessionTemplate sqlSession, BoardFile pf) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pf", pf);
		map.put("type", "NOTICE_");
		map.put("typeBoard", "NOTICE_");
		return sqlSession.insert("board.insertPostFile",map);
	}
	// 공지사항 - 게시글 삭제
	public int deletePost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "ALL_NOTICE");
		map.put("ynType", "NOT_DEL_YN");
		map.put("dateType", "NOT_DEL_DATE");
		map.put("noType", "NOT_NO");
		return sqlSession.update("board.deletePost",map);
	}
	// 공지사항 - 게시글 수정
	public int updatePost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "ALL_NOTICE"); // 테이블 명
		map.put("type", "NOT_");	// 컬럼 명
		return sqlSession.update("board.modifyPost",map);
	}
	
	// 페이징 처리 네비 
	public Page getPageNavi(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		Page page = (Page)map.get("page");
		
		map.put("boardType", "ALL_NOTICE"); // 게시판 테이블 명
		map.put("delType", "NOT_DEL_YN"); // 게시판 타입에 따른  삭제 여부 컬럼
		
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
				sb.append("<li class='page-list'><a href='/"+howPage+"allNoticePage.ho?currentPage="+(startNavi-1)+"' class='page-link'>◀</a></li>");
			}
							
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==page.getCurrentPage()) {
					sb.append("<li class='page-list'><a href='/"+howPage+"allNoticePage.ho?currentPage="+i+"' class='page-link'><B>"+i+"</B></a></li>");
				}else {
					sb.append("<li class='page-list'><a href='/"+howPage+"allNoticePage.ho?currentPage="+i+"' class='page-link'>"+i+"</a></li>");
				}
			}
					
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a href='/"+howPage+"allNoticePage.ho?currentPage="+(startNavi+1)+"' class='page-link'>▶</a></li>");
			}
		}else{ // 여기는 검색 페이지 페이징처리======================================================================================================
			//searchType=_title&keyword=이&deptCode=D2+
			if(startNavi != 1) { //href='/myReviewNote.rw?libraryOwner="+memberId+"&currentPage="+(startNavi-1)+"'
				sb.append("<li class='page-list'><a href='/"+howPage+"allNoticePage.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&currentPage="+(startNavi-1)+"' class='page-link'>◀</a></li>");
			}
							
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==page.getCurrentPage()) {
					sb.append("<li class='page-list'><a href='/"+howPage+"allNoticePage.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&currentPage="+i+"' class='page-link'><B>"+i+"</B></a></li>");
				}else {
					sb.append("<li class='page-list'><a href='/"+howPage+"allNoticePage.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&currentPage="+i+"' class='page-link'>"+i+"</a></li>");
				}
			}
					
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a href='/"+howPage+"allNoticePage.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&currentPage="+(startNavi+1)+"' class='page-link'>▶</a></li>");
			}
		}
		
		page.setUrl(sb+"");
		
		return page;		
	
	}

	
	// 총 게시물 수
	private int selectCountAllPostList(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.selectOne("board.countPostList", map);
	}
	// 조회수 +1
	public int updateHits(SqlSessionTemplate sqlSession, int postNo) {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("boardType", "ALL_NOTICE"); // 테이블명
		map.put("hitsType", "NOT_HITS");	// 조회수 컬럼
		map.put("noType", "NOT_NO");		// 게시물 고유번호 컬럼
		map.put("postNo", postNo+"");		// 게시물 고유번호 데이터
		map.put("ynType", "NOT_DEL_YN"); 	// 삭제여부 컬럼
		return sqlSession.update("board.countingHits",map);
	}
	// 다음글
	public int selectNextPost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "ALL_NOTICE"); // 테이블명
		map.put("type", "NOT");		// 컬럼 타입
		if(sqlSession.selectOne("board.nextPost",map)!=null){
			return sqlSession.selectOne("board.nextPost",map);
		}
		return 0;
	}
	// 이전글
	public int selectPrevPost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "ALL_NOTICE"); // 테이블명
		map.put("type", "NOT");		// 컬럼 타입
		if(sqlSession.selectOne("board.prevPost",map)!=null){
			return sqlSession.selectOne("board.prevPost",map);
		}
		return 0;
	}
	public int updatePostFile(SqlSessionTemplate sqlSession, BoardFile bf) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("bf", bf);
		map.put("type", "NOT_");
		return sqlSession.insert("board.updatePostFile",map);
	}
	public int deletePostFile(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("BoardType", "NOTICE_");
		map.put("type", "NOT_");
		return sqlSession.delete("board.deletePostFile",map);
	}
	
	
	
	
	

}
