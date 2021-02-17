package kr.or.houroffice.board.model.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.board.model.dao.NoticeDAO;
import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.BoardPost;
import kr.or.houroffice.common.Page;

@Service("noticeBService")
public class NoticeService implements BoardService{
	
	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Resource(name="noticeBDAO")
	private NoticeDAO bDAO;

	@Override // 목록 select
	public List<Object> selectBoardList(HashMap<String, Object> map) {
		return bDAO.selectBoardList(sqlSession,map);
	}

	@Override // 검색 목록 select 
	public List<Object> selectSearchBoardList(HashMap<String, Object> map) {
		return bDAO.selectSearchBoardList(sqlSession,map);
	}

	@Override // 게시물 select 
	public Object selectOnePost(HashMap<String, Object> map) {
		return bDAO.selectOnePost(sqlSession,map);
	}

	@Override
	public BoardFile selectPostFile(HashMap<String, Object> map) {
		return bDAO.selectPostFile(sqlSession,map);
	}

	@Override
	public int deletePost(HashMap<String, Object> map) {
		return bDAO.deletePost(sqlSession,map);
	}

	@Override
	public int updatePost(HashMap<String, Object> map) {
		return bDAO.updatePost(sqlSession,map);
	}

	@Override
	public int updatePostFile(BoardFile bf) {
		return bDAO.updatePostFile(sqlSession,bf);
	}

	@Override
	public int deletePostFile(HashMap<String, Object> map) {
		return bDAO.deletePostFile(sqlSession,map);
	}
	
	@Override // 파일 insert
	public int insertPostFile(BoardFile pf) {
		return bDAO.insertPostFile(sqlSession,pf);
	}

	@Override
	public Page getPageNavi(HashMap<String, Object> map) {
		return bDAO.getPageNavi(sqlSession,map);
	}

	@Override
	public int updateHits(int postNo) {
		return bDAO.updateHits(sqlSession,postNo);
	}

	@Override
	public int selectNextPost(HashMap<String, Object> map) {
		return bDAO.selectNextPost(sqlSession,map);
	}

	@Override
	public int selectPrevPost(HashMap<String, Object> map) {
		return bDAO.selectPrevPost(sqlSession,map);
	}

	public int insertPost(BoardPost bp) {
		int postNo = (Integer)bDAO.selectNumber(sqlSession); // 채번
		bp.setPostNo(postNo);
		int result = bDAO.insertPost(sqlSession,bp);
		if(result>0){
			return postNo;
		}
		return 0;
	}

	

}
