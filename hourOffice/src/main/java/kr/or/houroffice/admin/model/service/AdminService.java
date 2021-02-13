package kr.or.houroffice.admin.model.service;

import java.util.ArrayList;
import java.util.List;

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

	//관리자 관리 - 모달 - 사원 검색 (ajax)
	public ArrayList<Member> adminSearchModal(String keyword) {
		System.out.println("service : "+keyword);
		return aDAO.adminSearchModal(sqlSession,keyword);
	}//adminSearchModal
	
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

	//삭제 조회 - 삭제된 사원 조회 - 검색
	public ArrayList<Member> selectSearchDeleteMember(String searchType, String keyword, int currentPage,
			int recordCountPerPage) {
		return (ArrayList<Member>)aDAO.selectSearchDeleteMember(sqlSession,searchType,keyword,currentPage,recordCountPerPage);
	}//selectSearchDeleteMember

	//삭제 조회 - 삭제된 사원 조회 - 검색 - 페이징 처리
	public String searchGetPageNavi(int currentPage, int recordCountPerPage, int naviCountPerPage, int searchCount) {
		return aDAO.searchGetPageNavi(sqlSession,currentPage,recordCountPerPage,naviCountPerPage,searchCount);
	}//searchGetPageNavi

	//삭제 조회 - 삭제된 사원 복원 (ajax)
	public int deleteMemberCancel(List<String> memNoList) {
		return aDAO.deleteMemberCancel(sqlSession, memNoList);
	}//deleteMemberCancel

	//삭제 조회 - 삭제된 사원 영구 삭제 (ajax)
	public int deleteMember(List<String> memNoList) {
		return aDAO.deleteMember(sqlSession, memNoList);
	}//deleteMember

	//데이터/문서 관리 - 사원 기록 삭제
	public int selectDeleteMember() {
		return aDAO.selectDeleteMember(sqlSession);
	}//selectDeleteMember

}//AdminService
