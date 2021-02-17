package kr.or.houroffice.board.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.board.model.dao.PartBoardDAO;
import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.PartBoard;
import kr.or.houroffice.board.model.vo.PartComments;
import kr.or.houroffice.common.Page;
import kr.or.houroffice.member.model.vo.Member;

@Service("partBService")
public class PartBoardService implements BoardService{
	
	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Resource(name="partBDAO")
	private PartBoardDAO bDAO;
	
	
	@Override // 부서별 게시판 - 목록 selectAll
	public List<Object> selectBoardList(HashMap<String, Object> map) {
		return bDAO.selectBoardList(sqlSession,(String)map.get("deptCode"),(Page)map.get("page"));
	}
	@Override // 부서별 게시판 - 목록 select - 검색 List
	public List<Object> selectSearchBoardList(HashMap<String, Object> map) {
		return bDAO.selectSearchBoardList(sqlSession,map);
	}
	
	@Override // 부서별 게시판 - 게시글 select
	public Object selectOnePost(HashMap<String,Object> map) {
		return bDAO.selectOnePost(sqlSession,map);
	}
	@Override // 부서별 게시판 - 게시글 - 파일 select
	public BoardFile selectPostFile(HashMap<String, Object> map) {
		return bDAO.selectPostFile(sqlSession,map);
	}
	@Override // 부서별 게시판 - 게시글 - 삭제 delete
	public int deletePost(HashMap<String, Object> map) {
		return bDAO.deletePost(sqlSession,map);
	}
	// 부서별 게시판 - 게시글 - 댓글 select
	public List<Object> selectPostComments(HashMap<String, Object> map) {
		return bDAO.selectPostComments(sqlSession,map);
	}
	// 게시글 - 댓글 총 수
	public int selectComntCount(int partNo) {
		return bDAO.selectComntCount(sqlSession,partNo);
	}
	// 부서별 게시판 - 게시글 - 댓글 insert
	public int insertPostComnt(PartComments comnt) {
		return bDAO.insertPostComnt(sqlSession,comnt);
	}
	
	// 부서별 게시판 - 게시글 등록 - 게시글 insert
	public int insertPost(PartBoard pb) {
		int partNo = (Integer)bDAO.selectNumber(sqlSession);
		pb.setPartNo(partNo);
		int result = bDAO.insertPost(sqlSession,pb);
		if(result>0){
			return partNo;
		}
		return 0;
	}
	@Override// 부서별 게시판 - 게시글 등록 - 파일 insert
	public int insertPostFile(BoardFile pf) {
		return bDAO.insertPostFile(sqlSession,pf);
	}
	
	@Override // 부서별 게시판 - 게시글 수정 - update
	public int updatePost(HashMap<String, Object> map) {
		return bDAO.updatePost(sqlSession,map);
	}
	@Override // 부서별 게시판 - 게시글 수정 - 파일 update
	public int updatePostFile(BoardFile bf) {
		return bDAO.updatePostFile(sqlSession,bf);
	}
	@Override // 부서별 게시판 - 게시글 수정 - 파일 delete
	public int deletePostFile(HashMap<String, Object> map) {
		return bDAO.deletePostFile(sqlSession,map);
	}
	
	
	@Override // 페이징처리 네비만들기
	public Page getPageNavi(HashMap<String, Object> map) {
		return bDAO.getPageNavi(sqlSession,map);
	}
	// 댓글 페이징처리 네비만들기
	public Page getComntPageNavi(HashMap<String, Object> map) {
		return bDAO.getComntPageNavi(sqlSession,map);
	}
	@Override // 게시글 조회수 +1
	public int updateHits(int postNo) {
		return bDAO.updateHits(sqlSession,postNo);
	}
	@Override // 다음글
	public int selectNextPost(HashMap<String, Object> map) {
		return bDAO.selectNextPost(sqlSession,map);
	}
	@Override // 이전글
	public int selectPrevPost(HashMap<String, Object> map) {
		return bDAO.selectPrevPost(sqlSession,map);
	}
	// 댓글 삭제
	public int deleteComnt(int comntNo) {
		return bDAO.deleteComnt(sqlSession,comntNo);
	}
	public int updateComnt(int comntNo, String comnt) {
		return bDAO.updateComnt(sqlSession,comntNo,comnt);
	}

}
