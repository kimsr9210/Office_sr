package kr.or.houroffice.board.model.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.board.model.dao.CompanyRuleDAO;
import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.BoardPost;
import kr.or.houroffice.common.Page;

@Service("companyRuleService")
public class CompanyRuleService implements BoardService{
	
	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Resource(name="companyRuleDAO")
	private CompanyRuleDAO bDAO;

	@Override
	public List<Object> selectBoardList(HashMap<String, Object> map) {
		return bDAO.selectBoardList(sqlSession,map);
	}

	@Override
	public List<Object> selectSearchBoardList(HashMap<String, Object> map) {
		return bDAO.selectSearchBoardList(sqlSession,map);
	}

	@Override
	public Object selectOnePost(HashMap<String, Object> map) {
		return bDAO.selectOnePost(sqlSession,map);
	}

	@Override
	public BoardFile selectPostFile(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletePostFile(HashMap<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertPostFile(BoardFile pf) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Page getPageNavi(HashMap<String, Object> map) {
		return bDAO.getPageNavi(sqlSession,map);
	}

	@Override
	public int updateHits(int postNo) {
		// TODO Auto-generated method stub
		return 0;
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
		return bDAO.insertPost(sqlSession,bp);
	}
}
