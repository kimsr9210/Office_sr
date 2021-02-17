package kr.or.houroffice.board.model.service;

import java.util.HashMap;
import java.util.List;

import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.common.Page;

public interface BoardService {
	// 게시판 - 목록 selectAll
	public List<Object> selectBoardList(HashMap<String, Object> map);
	// 게시판 - 검색 List
	public List<Object> selectSearchBoardList(HashMap<String, Object> map);
	// 게시판 - 게시글 one select
	public Object selectOnePost(HashMap<String,Object> map);
	// 게시판 - 게시글 파일 select
	public BoardFile selectPostFile(HashMap<String, Object> map);
	// 게시판 - 게시글 one 삭제 update
	public int deletePost(HashMap<String, Object> map);
	// 게시판 - 게시글 수정 update
	public int updatePost(HashMap<String, Object> map);
	// 게시판 - 게시글 수정 파일 update
	public int updatePostFile(BoardFile bf);
	// 게시판 - 게시글 수정 파일 delete
	public int deletePostFile(HashMap<String, Object> map);
	// 게시판 - 게시글 파일 insert
	public int insertPostFile(BoardFile pf);
	
	// 페이징 처리 메소드
	public Page getPageNavi(HashMap<String, Object> map);
	// 조회수 +1
	public int updateHits(int postNo);
	// 다음글
	public int selectNextPost(HashMap<String, Object> map);
	// 이전글
	public int selectPrevPost(HashMap<String, Object> map);
	
}
