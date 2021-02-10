package kr.or.houroffice.board.model.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.board.model.dao.PartBoardDAO;
import kr.or.houroffice.board.model.vo.BoardFile;
import kr.or.houroffice.board.model.vo.PartBoard;

@Service("partBService")
public class PartBoardService {
	
	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Resource(name="partBDAO")
	private PartBoardDAO bDAO;
	
	// 부서별 게시판 - 게시글 selectAll
	public ArrayList<PartBoard> selectBoardList(String deptCode) {
		return (ArrayList<PartBoard>)bDAO.selectBoardList(sqlSession,deptCode);
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
	// 부서별 게시판 - 게시글 등록 - 파일 insert
	public int insertPostFile(BoardFile pf) {
		return bDAO.insertPostFile(sqlSession,pf);
	}
	

}
