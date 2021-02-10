package kr.or.houroffice.admin.model.service;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.admin.model.dao.AdminDAO;
import kr.or.houroffice.member.model.dao.AdminMemberDAO;
import kr.or.houroffice.member.model.vo.Member;

@Service("adminService")
public class AdminService {

	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Resource(name="adminDAO")
	private AdminDAO aDAO;
	
	//관리자 관리 - 부서별 관리자 리스트 전체 조회
	public ArrayList<Member> selectAllMember() {
		return aDAO.selectAllMember(sqlSession);
	}//selectAllMember

	//삭제 조회 - 삭제된 사원 조회
	public ArrayList<Member> selectDeleteMember(int currentPage, int recordCountPerPage) {
		return aDAO.selectDeleteMember(sqlSession,currentPage,recordCountPerPage);
	}//selectDeleteMember
	
	//삭제 조회 - 삭제된 사원 조회 - 삭제된 사원 수
	public int countDeleteMember() {
		return aDAO.countDeleteMember(sqlSession);
	}//countDeleteMember
	
	//삭제 조회 - 삭제된 사원 조회 - 페이징 처리
	public String getPageNavi(int currentPage, int recordCountPerPage, int naviCountPerPage) {
		return aDAO.getPageNavi(sqlSession, currentPage,recordCountPerPage,naviCountPerPage);
	}//getPageNavi


}//AdminService
