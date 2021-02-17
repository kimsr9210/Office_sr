package kr.or.houroffice.board.model.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.type.IntegerTypeHandler;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.PartBoard;
import kr.or.houroffice.board.model.vo.PartComments;
import kr.or.houroffice.common.Page;
import kr.or.houroffice.member.model.vo.Member;

@Repository("partBDAO")
public class PartBoardDAO {

	// 부서별 게시판  - selectAll
	public List<Object> selectBoardList(SqlSessionTemplate sqlSession, String deptCode, Page page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("deptCode", deptCode);
		map.put("start", page.getStartPage());
		map.put("end", page.getEndPage());
		return sqlSession.selectList("board.allPartBoardList",map);
	}
	// 부서별 게시판  - select - 검색 select
	public List<Object> selectSearchBoardList(SqlSessionTemplate sqlSession, HashMap<String, Object> map) { 
		// map 안에 들어 있는 데이터 deptCode / page객체 / searchType / keyword / - 추가 start / end
		Page page = (Page)map.get("page");
		map.put("start", page.getStartPage());
		map.put("end", page.getEndPage());
		return sqlSession.selectList("board.allPartBoardList",map);
	}
	
	// 부서별 게시판  - 게시물 - select
	public Object selectOnePost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		return sqlSession.selectOne("board.getOnePostPartBoard",map);
	}
	// 부서별 게시판  - 게시물 - 파일 select
	public BoardFile selectPostFile(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("type", "PART_");
		return sqlSession.selectOne("board.getPostFile",map);
	}
	// 부서별 게시판  - 게시물 - 삭제 delete
	public int deletePost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "PART_BOARD");
		map.put("ynType", "PART_DEL_YN");
		map.put("dateType", "PART_DEL_DATE");
		map.put("noType", "PART_NO");
		return sqlSession.update("board.deletePost",map);
	}
	// 부서별 게시판  - 게시물 - 댓글 select
	public List<Object> selectPostComments(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		Page page = (Page)map.get("page");
		map.put("start", page.getStartPage());
		map.put("end", page.getEndPage());
		return sqlSession.selectList("board.getPostComntList",map);
	}
	// 부서별 게시판  - 게시물 - 댓글 insert
	public int insertPostComnt(SqlSessionTemplate sqlSession, PartComments comnt) {
		return sqlSession.insert("board.addComnt",comnt);
	}
	// 댓글 수정
	public int updateComnt(SqlSessionTemplate sqlSession, int comntNo, String comnt) {
		PartComments pc = new PartComments();
		pc.setPartComntNo(comntNo);
		pc.setPartComnt(comnt);
		return sqlSession.update("board.updateComnt",pc);
	}
	// 댓글 삭제
	public int deleteComnt(SqlSessionTemplate sqlSession, int comntNo) {
		return sqlSession.delete("board.deleteComnt",comntNo);
	}
	
	
	// 페이징 처리 네비
	public Page getPageNavi(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		Page page = (Page)map.get("page");
		String pageDeptCode = (String)map.get("deptCode");
		
		map.put("boardType", "PART_BOARD"); // 게시판 테이블 명
		map.put("delType", "PART_DEL_YN"); // 게시판 타입에 따른  삭제 여부 컬럼
		
		int postTotalCount = selectCountAllPostList(sqlSession,map); // 전체 게시물을 구하기 위한 메소드
		page.setPostTotalCount(postTotalCount); // 전체 게시물 수 셋팅
		// 생성될 페이지 개수 구하기
						
		int pageTotalCount = page.getPageTotalCount(); // 전체페이지를 저장하는 변수
		// 현재 페이지 번호 구하기
		// startNavi = ((현재페이지-1)/보여질 navi개수) * 보여질navi개수 +1;
		int startNavi = page.getStartNavi();
		// endNavi = 시작navi번호 + 보여질 navi개수 - 1;
		int endNavi = page.getEndNavi();
				
		// 이제 pageNavi의 모양을 구성해야 함
		
		StringBuilder sb = new StringBuilder();
		if(map.get("searchType")==null){
			// 만약 첫번째 pageNavi가 아니라면 '<' 모양을 추가해라 (첫번째 pageNavi이면 추가하지 말아라)
			if(startNavi != 1) { //href='/myReviewNote.rw?libraryOwner="+memberId+"&currentPage="+(startNavi-1)+"'
				sb.append("<li class='page-list'><a href='/allPartBoardPage.ho?deptCode="+pageDeptCode+"&currentPage="+(startNavi-1)+"' class='page-link'>◀</a></li>");
			}
							
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==page.getCurrentPage()) {
					sb.append("<li class='page-list'><a href='/allPartBoardPage.ho?deptCode="+pageDeptCode+"&currentPage="+i+"' class='page-link'><B>"+i+"</B></a></li>");
				}else {
					sb.append("<li class='page-list'><a href='/allPartBoardPage.ho?deptCode="+pageDeptCode+"&currentPage="+i+"' class='page-link'>"+i+"</a></li>");
				}
			}
					
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a href='/allPartBoardPage.ho?deptCode="+pageDeptCode+"&currentPage="+(startNavi+1)+"' class='page-link'>▶</a></li>");
			}
		}else{ // 여기는 검색 페이지 페이징처리======================================================================================================
			//searchType=_title&keyword=이&deptCode=D2+
			if(startNavi != 1) { //href='/myReviewNote.rw?libraryOwner="+memberId+"&currentPage="+(startNavi-1)+"'
				sb.append("<li class='page-list'><a href='/searchPartBoard.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&deptCode="+pageDeptCode+"&currentPage="+(startNavi-1)+"' class='page-link'>◀</a></li>");
			}
							
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==page.getCurrentPage()) {
					sb.append("<li class='page-list'><a href='/searchPartBoard.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&deptCode="+pageDeptCode+"&currentPage="+i+"' class='page-link'><B>"+i+"</B></a></li>");
				}else {
					sb.append("<li class='page-list'><a href='/searchPartBoard.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&deptCode="+pageDeptCode+"&currentPage="+i+"' class='page-link'>"+i+"</a></li>");
				}
			}
					
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a href='/searchPartBoard.ho?searchType="+map.get("searchTypeOrg")+"&keyword="+map.get("keywordOrg")+"&deptCode="+pageDeptCode+"&currentPage="+(startNavi+1)+"' class='page-link'>▶</a></li>");
			}
		}
		
		page.setUrl(sb+"");
		
		return page;		
	
	}
	
	// 페이징 처리 네비 - 댓글
	public Page getComntPageNavi(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		String pageDeptCode = (String)map.get("deptCode");
		int partNo = (Integer)map.get("postNo");
		Page page = (Page)map.get("page");
		map.put("boardType", "PART_COMMENTS");
		map.remove("post");
		int postTotalCount = (Integer)map.get("comntCount");
		page.setPostTotalCount(postTotalCount); // 전체 게시물 수 셋팅
		// 생성될 페이지 개수 구하기
						
		int pageTotalCount = page.getPageTotalCount(); // 전체페이지를 저장하는 변수
		// 현재 페이지 번호 구하기
		// startNavi = ((현재페이지-1)/보여질 navi개수) * 보여질navi개수 +1;
		int startNavi = page.getStartNavi();
		// endNavi = 시작navi번호 + 보여질 navi개수 - 1;
		int endNavi = page.getEndNavi();
				
		// 이제 pageNavi의 모양을 구성해야 함
		
		StringBuilder sb = new StringBuilder();
		
		if(startNavi != 1) { //href='/myReviewNote.rw?libraryOwner="+memberId+"&currentPage="+(startNavi-1)+"'
			sb.append("<li class='page-list'><a href='page-link' href='/postInPartBoard.ho?deptCode="+pageDeptCode+"&partNo="+partNo+"&currentPage="+(startNavi-1)+"&pageCheck=U' class='page-link'>◀</a></li>");
		}
						
		for(int i=startNavi; i<=endNavi; i++) {
			if(i==page.getCurrentPage()) {
				sb.append("<li class='page-list'><a href='/postInPartBoard.ho?deptCode="+pageDeptCode+"&partNo="+partNo+"&currentPage="+i+"&pageCheck=U' class='page-link'><B>"+i+"</B></a></li>");
			}else {
				sb.append("<li class='page-list'><a href='/postInPartBoard.ho?deptCode="+pageDeptCode+"&partNo="+partNo+"&currentPage="+i+"&pageCheck=U' class='page-link'>"+i+"</a></li>");
			}
		}
				
		//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
		if(endNavi != pageTotalCount) {
			sb.append("<li class='page-list'><a href='/postInPartBoard.ho?deptCode="+pageDeptCode+"&partNo="+partNo+"&currentPage="+(startNavi+1)+"&pageCheck=U' class='page-link'>▶</a></li>");
		}
		page.setUrl(sb+"");
		return page;
	}
	
	// 부서별 게시판 - 게시글 등록 - 게시판 번호 채번
	public Object selectNumber(SqlSessionTemplate sqlSession) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("boardType", "PART_BOARD_");
		return sqlSession.selectOne("board.selectNumber",map);
		
	}
	// 부서별 게시판 - 게시글 등록 - 게시판 insert
	public int insertPost(SqlSessionTemplate sqlSession, PartBoard pb) {
		return sqlSession.insert("board.addPostPartBoard", pb);
	}
	// 부서별 게시판 - 게시글 등록 - 파일 insert
	public int insertPostFile(SqlSessionTemplate sqlSession, BoardFile pf) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("pf", pf);
		map.put("type", "PART_");
		map.put("typeBoard", "PART_BOARD_");
		return sqlSession.insert("board.insertPostFile",map);
	}


	// 부서별 게시판 - 게시글 수정 - update
	public int updatePost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "PART_BOARD"); // 테이블 명
		map.put("type", "PART_");	// 컬럼 명
		map.put("deptCode", ((PartBoard)map.get("pb")).getDeptCode());
		return sqlSession.update("board.modifyPostPartBoard",map);
	}
	// 부서별 게시판 - 게시글 수정 - 파일 update
	public int updatePostFile(SqlSessionTemplate sqlSession, BoardFile bf) {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put("bf", bf);
		map.put("BoardType", "PART_");
		map.put("type", "PART_");
		return sqlSession.insert("board.updatePostFile",map);
	}
	// 부서별 게시판 - 게시글 수정 - 파일 delete
	public int deletePostFile(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("BoardType", "PART_");
		map.put("type", "PART_");
		return sqlSession.delete("board.deletePostFile",map);
	}
	
	
	// 총 게시물 수
	public int selectCountAllPostList(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		// All - 부서코드 + 보드타입 + delYN
		// 검색 - 부서코드 + 키워드 + 검색타입 + 보드타입 + delYN
		
		return sqlSession.selectOne("board.countPostList", map);
	}
	// 총 댓글 수 
	public int selectComntCount(SqlSessionTemplate sqlSession, int partNo) {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("boardType", "PART_COMMENTS");
		map.put("comntPostNo", partNo+"");
		return sqlSession.selectOne("board.countPostList", map);
	}
	// 조회수 +1
	public int updateHits(SqlSessionTemplate sqlSession, int postNo) {
		HashMap<String,String> map = new HashMap<String, String>();
		map.put("boardType", "PART_BOARD"); // 테이블명
		map.put("hitsType", "PART_HITS");	// 조회수 컬럼
		map.put("noType", "PART_NO");		// 게시물 고유번호 컬럼
		map.put("postNo", postNo+"");		// 게시물 고유번호 데이터
		map.put("ynType", "PART_DEL_YN"); 	// 삭제여부 컬럼
		return sqlSession.update("board.countingHits",map);
	}
	// 다음글 postNo
	public int selectNextPost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "PART_BOARD"); // 테이블명
		map.put("type", "PART");		// 컬럼 타입
		if(sqlSession.selectOne("board.nextPost",map)!=null){
			return sqlSession.selectOne("board.nextPost",map);
		}
		return 0;
	}
	// 이전 글 postNo
	public int selectPrevPost(SqlSessionTemplate sqlSession, HashMap<String, Object> map) {
		map.put("boardType", "PART_BOARD"); // 테이블명
		map.put("type", "PART");		// 컬럼 타입
		if(sqlSession.selectOne("board.prevPost",map)!=null){
			return sqlSession.selectOne("board.prevPost",map);
		}
		return 0;
	}
	
	
	
	
}
