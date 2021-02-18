package kr.or.houroffice.admin.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.admin.model.dao.AdminDAO;
import kr.or.houroffice.admin.model.vo.AdminBoard;
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
		return aDAO.adminSearchModal(sqlSession,keyword);
	}//adminSearchModal
	
	//관리자 관리 - 모달 - 전산관리자 권한 추가 (ajax)
	public int adminUpdateAdRight(List<String> memNoList) {
		return aDAO.adminUpdateAdRight(sqlSession, memNoList);
	}//adminUpdateAdRight

	//관리자 관리 - 모달 - 인사관리자 권한 추가 (ajax)
	public int adminUpdatePeRight(List<String> memNoList) {
		return aDAO.adminUpdatePeRight(sqlSession, memNoList);
	}//adminUpdatePeRight
	
	//관리자 관리 - 모달 - 총무관리자 권한 추가 (ajax)
	public int adminUpdateGeRight(List<String> memNoList) {
		return aDAO.adminUpdateGeRight(sqlSession, memNoList);
	}//adminUpdateGeRight
	
	//관리자 관리 - 선택된 사원 권한 삭제 (ajax)
	public int adminDeleteRight(List<String> memNoList) {
		return aDAO.adminDeleteRight(sqlSession, memNoList);
	}//adminDeleteRight
	
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

	//삭제 조회  - 삭제된 사원 조회 - 삭제된 사원 복원 (ajax)
	public int deleteMemberCancel(List<String> memNoList) {
		return aDAO.deleteMemberCancel(sqlSession, memNoList);
	}//deleteMemberCancel

	//삭제 조회 - 삭제된 사원 조회 - 삭제된 사원 영구 삭제 (ajax)
	public int deleteMember(List<String> memNoList) {
		return aDAO.deleteMember(sqlSession, memNoList);
	}//deleteMember
	
	//삭제 조회 - 삭제된 게시글 조회  - 삭제된 게시글 수
	public int countBoard() {
		return aDAO.countBoard(sqlSession);
	}//countBoard

	//삭제 조회 - 삭제된 게시글 조회
	public ArrayList<AdminBoard> selectDeleteBoard(int currentPage, int recordCountPerPage) {
		return aDAO.selectDeleteBoard(sqlSession,currentPage,recordCountPerPage);
	}//selectDeleteBoard
	
	//삭제 조회 - 삭제된 부서별 게시글 조회 - 페이징 처리
	public String getBoardPageNavi(int currentPage, int recordCountPerPage, int naviCountPerPage) {
		return aDAO.getPageNavi(sqlSession, currentPage,recordCountPerPage,naviCountPerPage);
	}//getPageNavi
	
	//삭제 조회 - 삭제된 부서별 게시글 검색
	public ArrayList<AdminBoard> selectSearchBoard(String searchType, String keyword, int currentPage,
			int recordCountPerPage) {		
		return  (ArrayList<AdminBoard>)aDAO.selectSearchBoard(sqlSession,searchType,keyword,currentPage,recordCountPerPage);
	}//selectSearchBoard
	
	//삭제 조회 - 삭제된 부서별 게시글 검색 - 페이징 처리
	public String searchGetBoardPageNavi(int currentPage, int recordCountPerPage, int naviCountPerPage,
			int searchCount) {
		return aDAO.searchGetPageNavi(sqlSession, currentPage,recordCountPerPage,naviCountPerPage,searchCount);
	}//searchGetPageNavi
	
	//삭제 조회 - 삭제된 부서별 게시글 복원 (ajax)
	public int deleteBoardCancel(List<String> noList) {			
		return aDAO.deleteBoardCancel(sqlSession, noList);
	}//deleteBoardCancel

	//삭제 조회 - 삭제된 부서별 게시글 영구 삭제 (ajax)
	public int deleteBoard(List<String> noList) {		
		return aDAO.deleteBoard(sqlSession, noList);
	}//deleteBoard
	
	//데이터/문서 관리 - 경과된 사원 기록
	public int expireDeleteMember() {
		return aDAO.expireDeleteMember(sqlSession);
	}//expireDeleteMember

	//데이터/문서 관리 - 미경과된 사원 기록
	public int expireNotDeleteMember() {
		return aDAO.expireNotDeleteMember(sqlSession);
	}//expireNotDeleteMember

	//데이터/문서 관리 - 경과된 삭제 대기 게시물
	public int deletePaperCount() {
		return aDAO.deletePaperCount(sqlSession);
	}//deletePaperCount

	//데이터/문서 관리 - 보존 기간 경과된 삭제 대기 게시물
	public int expirePaperCount() {	
		return aDAO.expirePaperCount(sqlSession);
	}//expirePaperCount

	//데이터/문서 관리 - 미경과된 삭제 대기 게시물
	public int deleteNotPaperCount() {
		return aDAO.deleteNotPaperCount(sqlSession);
	}//deleteNotPaperCount

	//데이터/문서 관리 - 보존 기간 미경과된 삭제 대기 게시물
	public int expireNotPaperCount() {
		return aDAO.expireNotPaperCount(sqlSession);
	}//expireNotPaperCount



	

}//AdminService
