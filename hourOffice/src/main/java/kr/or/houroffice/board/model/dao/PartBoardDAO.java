package kr.or.houroffice.board.model.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.PartBoard;

@Repository("partBDAO")
public class PartBoardDAO {

	// 부서별 게시판  - selectAll
	public List<PartBoard> selectBoardList(SqlSessionTemplate sqlSession, String deptCode) {
		//int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		//int end = currentPage * recordCountPerPage;
		return sqlSession.selectList("board.allPartBoardList",deptCode);
	}

	// 부서별 게시판 - 게시글 등록 - 게시판 번호 채번
	public Object selectNumber(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("board.selectNumber");
		
	}
	// 부서별 게시판 - 게시글 등록 - 게시판 insert
	public int insertPost(SqlSessionTemplate sqlSession, PartBoard pb) {
		return sqlSession.insert("board.addPost", pb);
	}
	// 부서별 게시판 - 게시글 등록 - 파일 insert
	public int insertPostFile(SqlSessionTemplate sqlSession, BoardFile pf) {
		return sqlSession.insert("board.insertPartFile",pf);
	}
	

	

	// 부서별 게시판 - 게시글 등록 - 파일 insert
	// 부서별 게시판 - 게시글 등록 - 댓글 insert
}
