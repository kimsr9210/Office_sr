package kr.or.houroffice.member.model.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import kr.or.houroffice.member.model.dao.AdminMemberDAO;
import kr.or.houroffice.member.model.vo.AcademicAbility;
import kr.or.houroffice.member.model.vo.Career;
import kr.or.houroffice.member.model.vo.Department;
import kr.or.houroffice.member.model.vo.License;
import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.member.model.vo.Military;

@Service("adminMemberService")
public class AdminMemberService {
	
	@Autowired
	@Qualifier(value="sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;
	
	@Resource(name="adminMemberDAO")
	private AdminMemberDAO mDAO;
	
	// 멤버 전체 수 구하는 메소드
	public int selectCountAllMember() {
		return mDAO.selectCountAllMember(sqlSession);
		
	}
	// 통합사원 - 사원 목록 - select
	public ArrayList<Member> selectAllMember(int currentPage, int recordCountPerPage) {
		return mDAO.selectAllMember(sqlSession,currentPage,recordCountPerPage);
	}
	// 통합사원 - 사원 목록 - 페이징 처리
	public String getPageNavi(int currentPage, int recordCountPerPage, int naviCountPerPage) {
		return mDAO.getPageNavi(sqlSession,currentPage,recordCountPerPage,naviCountPerPage);
	}
	// 통합사원 - 사원 직위 변경 - update
	public int updateMemberPosition(int memNo, String memPosition) {
		return mDAO.updateMemberPosition(sqlSession,memNo,memPosition);
	}
	// 통합사원 - 사원 삭제 - update
	public int updateMemberResign(List<String> memNoList) {
		return mDAO.updateMemberResign(sqlSession,memNoList);
	}
	
	// 통합사원 - 검색 - select
	public ArrayList<Member> selectSearchMember(String searchType, String keyword, int currentPage, int recordCountPerPage) {
		return (ArrayList<Member>)mDAO.selectSearchMember(sqlSession,searchType,keyword,currentPage,recordCountPerPage);
	}
	// 통합사원 - 검색 - 페이징 처리
	public String searchGetPageNavi(int currentPage, int recordCountPerPage, int naviCountPerPage, int searchCount) {
		return mDAO.searchGetPageNavi(sqlSession,currentPage,recordCountPerPage,naviCountPerPage,searchCount);
	}
		
	// 사원 등록 -----------------------------------------------------------------------------------------------------------------
	public boolean insertMember(Member m, ArrayList<AcademicAbility> acaList, ArrayList<License> licList, ArrayList<Career> carList, Military mil) {
		
		// 사번 채번
		int memNo = mDAO.selectInsertMemberNo(sqlSession);
		// m 객체에 사번 넣어줌
		m.setMemNo(memNo);
		// member insert
		int result = mDAO.insertMember(sqlSession,m);
		// insert 성공 시
		if(result>0){
			// 학력에 사번 넣어줌
			for(AcademicAbility aca : acaList){
				aca.setMemNo(memNo);
			}
			// 자격증에 사번 넣어줌
			for(License lic : licList){
				lic.setMemNo(memNo);
			}
			// 경력에 사번 넣어줌
			for(Career car : carList){
				car.setMemNo(memNo);
			}
			// 병력에 사번 넣어줌
			mil.setMemNo(memNo);
			
			mDAO.insertInfoAca(sqlSession,acaList);
			mDAO.insertInfoLic(sqlSession,licList);
			mDAO.insertInfoCar(sqlSession,carList);
			mDAO.insertInfoMil(sqlSession,mil);
			
			return true; // true 반환
		}
		
		return false; // 실패시 false 반환
	}
	
	// 사원 정보 -----------------------------------------------------------------------------------------------------------------
	// 사원 정보 - select
	public Member selectOneMember(Member m) {
		return mDAO.selectOneMember(sqlSession,m);
	}
	// 사원 정보 - 학력 select
	public ArrayList<AcademicAbility> selectOneMemberAca(Member m) {
		return (ArrayList<AcademicAbility>)mDAO.selectOneMemberAca(sqlSession,m);
	}
	// 사원 정보 - 자격증 select
	public ArrayList<License> selectOneMemberLic(Member m) {
		return (ArrayList<License>)mDAO.selectOneMemberLic(sqlSession,m);
	}
	// 사원 정보 - 경력 select
	public ArrayList<Career> selectOneMemberCar(Member m) {
		return (ArrayList<Career>)mDAO.selectOneMemberCar(sqlSession,m);
	}
	// 사원 정보 - 병력 select
	public Military selectOneMemberMil(Member m) {
		return mDAO.selectOneMemberMil(sqlSession,m);
	}
	
	// 조직도 -----------------------------------------------------------------------------------------------------------------
	// 조직도 - select
	public ArrayList<Member> selectOrganizationChart() {
		return mDAO.selectOrganizationChart(sqlSession);
	}
	// 조직도 - 사원 부서 이동 - update
	public int updateMemberDepartment(int[] memNo, String deptCode) {
		return mDAO.updateMemberDepartment(sqlSession,memNo,deptCode);
	}
	// 조직도 - 부서 추가 - insert
	public int insertDepartment(String deptCode, String deptName) {
		return mDAO.insertDepartment(sqlSession,deptCode,deptName);
	}
	// 조직도 - 부서 이름 수정 - update
	public int updateDepartmentName(String deptCode, String deptName) {
		return mDAO.updateDepartmentName(sqlSession,deptCode,deptName);
			
	}
	// 조직도 - 부서 삭제 - update
	public int updateDepartmentDelete(String deptCode) {
		return mDAO.updateDepartmentDelete(sqlSession,deptCode);
	}
	
	//BY 진원   조직도 - 부서목록
	public ArrayList<Department> selectAllDepartment(){
		return mDAO.selectAllDepartment(sqlSession);
	}
	
	
	
	
	
	
	
	
	

}
