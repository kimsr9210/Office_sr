package kr.or.houroffice.member.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.jdbc.SQL;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.member.model.vo.AcademicAbility;
import kr.or.houroffice.member.model.vo.Career;
import kr.or.houroffice.member.model.vo.Department;
import kr.or.houroffice.member.model.vo.License;
import kr.or.houroffice.member.model.vo.Member;
import kr.or.houroffice.member.model.vo.Military;

@Repository("adminMemberDAO")
public class AdminMemberDAO {
	
	// 관리자탭 (인사관리) - 통합사원 - 사원 목록 select
	public int selectCountAllMember(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("member.allMemberCount");
	}
	
	// 전체 멤버 list - select
	public ArrayList<Member> selectAllMember(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage) {
		// 페이징 처리 수식
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		List list = sqlSession.selectList("member.allMemberList", map);
		
		return (ArrayList<Member>)list;
	}
	// 관리자탭 (인사관리) - 통합사원 - 사원 목록 - 페이징 처리 메소드
	public String getPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage, int naviCountPerPage) {
		// 현재 변수 재확인
		// currentPage			: 현재 페이지를 가지고 있는 변수
		// recordCountPerPage	: 한 페이지당 보여질 게시물의 개수
		// naviCountPerPage		: pageNavi가 몇개씩 보여질 것인지에 대한 변수
		int postTotalCount = selectCountAllMember(sqlSession); // 전체 게시물을 구하기 위한 메소드
				
		// 생성될 페이지 개수 구하기
				
		int pageTotalCount; // 전체페이지를 저장하는 변수
		if(postTotalCount % recordCountPerPage > 0){ // 마지막 페이지 숫자 정하는 코드
			pageTotalCount = postTotalCount / recordCountPerPage + 1;
			// ex) pageTotalCount = 108 / 5 + 1 -> 22 Page
		}else {
			pageTotalCount = postTotalCount / recordCountPerPage + 0;
			// ex) pageTotalCount = 105 / 5 + 0 -> 21 Page
		}
				
		// 현재 페이지 번호 구하기
		// startNavi = ((현재페이지-1)/보여질 navi개수) * 보여질navi개수 +1;
		int startNavi = ((currentPage -1) / naviCountPerPage) * naviCountPerPage +1;
		// endNavi = 시작navi번호 + 보여질 navi개수 - 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		// 마지막 페이지 번호가 총 페이지 수보다 높을 때
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
				
		// 이제 pageNavi의 모양을 구성해야 함
				
		StringBuilder sb = new StringBuilder();
		
		// 만약 첫번째 pageNavi가 아니라면 '<' 모양을 추가해라 (첫번째 pageNavi이면 추가하지 말아라)
		if(startNavi != 1) { //href='/myReviewNote.rw?libraryOwner="+memberId+"&currentPage="+(startNavi-1)+"'
			sb.append("<li class='page-list'><a class='page-link' href='/admin_tap_allListMember.ho?currentPage="+(startNavi-1)+"'>◀</a></li>");
		}
						
		for(int i=startNavi; i<=endNavi; i++) {
			if(i==currentPage) {
				sb.append("<li class='page-list'><a class='page-link' href='/admin_tap_allListMember.ho?currentPage="+i+"'><B>"+i+"</B></a></li>");
			}else {
				sb.append("<li class='page-list'><a class='page-link' href='/admin_tap_allListMember.ho?currentPage="+i+"'>"+i+"</a></li>");
			}
		}
				
		//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
		if(endNavi != pageTotalCount) {
			sb.append("<li class='page-list'><a class='page-link' href='/admin_tap_allListMember.ho?currentPage="+(startNavi+1)+"'>▶</a></li>");
		}
						
		return sb+"";
	}
	// 관리자탭 (인사관리) - 통합사원 - 사원 직위 변경 update
	public int updateMemberPosition(SqlSessionTemplate sqlSession, int memNo, String memPosition) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("memNo", memNo);
		map.put("memPosition", memPosition);
		return sqlSession.update("member.changePosition",map);
		
	}
	// 관리자탭 (인사관리) - 통합사원 - 사원 삭제 update
	public int updateMemberResign(SqlSessionTemplate sqlSession, List<String> memNoList) {
			return sqlSession.update("member.resignMember",memNoList);
	}
	
	// 관리자탭 (인사관리) - 통합사원 - 검색 - select
	public List selectSearchMember(SqlSessionTemplate sqlSession, String searchType, String keyword, int currentPage, int recordCountPerPage) {
		// 페이징 처리 수식
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;		
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		List list = sqlSession.selectList("member.searchMember", map);
		return list;
	}
	// 관리자탭 (인사관리) - 통합사원 - 검색 / 페이징 처리 메소드
	public String searchGetPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage, int naviCountPerPage, int searchCount) {
		// 현재 변수 재확인
		// currentPage			: 현재 페이지를 가지고 있는 변수
		// recordCountPerPage	: 한 페이지당 보여질 게시물의 개수
		// naviCountPerPage		: pageNavi가 몇개씩 보여질 것인지에 대한 변수
				
		// 생성될 페이지 개수 구하기
					
		int pageTotalCount; // 전체페이지를 저장하는 변수
		if(searchCount % recordCountPerPage > 0){ // 마지막 페이지 숫자 정하는 코드
			pageTotalCount = searchCount / recordCountPerPage + 1;
			// ex) pageTotalCount = 108 / 5 + 1 -> 22 Page
		}else {
			pageTotalCount = searchCount / recordCountPerPage + 0;
			// ex) pageTotalCount = 105 / 5 + 0 -> 21 Page
		}
				
		// 현재 페이지 번호 구하기
		// startNavi = ((현재페이지-1)/보여질 navi개수) * 보여질navi개수 +1;
		int startNavi = ((currentPage -1) / naviCountPerPage) * naviCountPerPage +1;
		// endNavi = 시작navi번호 + 보여질 navi개수 - 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		// 마지막 페이지 번호가 총 페이지 수보다 높을 때
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
				
	// 이제 pageNavi의 모양을 구성해야 함
				
		StringBuilder sb = new StringBuilder();
		
			// 만약 첫번째 pageNavi가 아니라면 '<' 모양을 추가해라 (첫번째 pageNavi이면 추가하지 말아라)
		if(startNavi != 1) { //href='/myReviewNote.rw?libraryOwner="+memberId+"&currentPage="+(startNavi-1)+"'
			sb.append("<li class='page-list'><a class='page-link' href='/admin_tap_search_allListMember.ho?currentPage="+(startNavi-1)+"'>◀</a></li>");
		}
						
		for(int i=startNavi; i<=endNavi; i++) {
			if(i==currentPage) {
				sb.append("<li class='page-list'><a class='page-link' href='/admin_tap_search_allListMember.ho?currentPage="+i+"'><B>"+i+"</B></a></li>");
			}else {
				sb.append("<li class='page-list'><a class='page-link' href='/admin_tap_search_allListMember.ho?currentPage="+i+"'>"+i+"</a></li>");
			}
		}
				
		//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라 (마지막 pageNavi이면 추가하지 말아라)
		if(endNavi != pageTotalCount) {
			sb.append("<li class='page-list'><a class='page-link' href='/admin_tap_search_allListMember.ho?currentPage="+(startNavi+1)+"'>▶</a></li>");
		}
						
		return sb+"";
	}
	
	// 사원 등록 ----------------------------------------------------------------------------------------------------------
	///////////////////////////////////////  사원 등록 메소드   ////////////////////////////////////////
	
	// 관리자탭 (인사관리) - 사원 등록 / 사번 채번 메소드 
	public int selectInsertMemberNo(SqlSessionTemplate sqlSession){
		return sqlSession.selectOne("member.choiceMemberNo");
	}
	// 관리자탭 (인사관리) - 사원 등록
	public int insertMember(SqlSessionTemplate sqlSession, Member m) {		
		return sqlSession.insert("member.signUpMember",m);
	}
	// 관리자탭 (인사관리) - 사원 등록 - 학력 insert
	public int insertInfoAca(SqlSessionTemplate sqlSession, ArrayList<AcademicAbility> acaList) {
		return sqlSession.insert("member.insertAcademicAbility",acaList);
	}
	// 관리자탭 (인사관리) - 사원 등록 - 자격증 insert
	public int insertInfoLic(SqlSessionTemplate sqlSession, ArrayList<License> licList) {
		return sqlSession.insert("member.insertLicense",licList);
	}
	// 관리자탭 (인사관리) - 사원 등록 - 경력 insert
	public int insertInfoCar(SqlSessionTemplate sqlSession, ArrayList<Career> carList) {
		return sqlSession.insert("member.insertCareer",carList);
	}
	// 관리자탭 (인사관리) - 사원 등록 - 병력 insert
	public int insertInfoMil(SqlSessionTemplate sqlSession, Military mil) {
		return sqlSession.insert("member.insertMilitary",mil);
	}
	////////////////////////////////////////사원 등록 메소드   ////////////////////////////////////////

	// 사원 정보 ----------------------------------------------------------------------------------------------------------
	// 관리자탭 (인사관리) - 부서 select
	public List selectDeptAll(SqlSessionTemplate sqlSession) {
		return sqlSession.selectList("member.deptAll");
	}
	// 관리자탭 (인사관리) - 사원 정보 select
	public Member selectOneMember(SqlSessionTemplate sqlSession, Member m) {
		return (Member)sqlSession.selectOne("member.selectOneAsMemNo",m);
	}
	// 관리자탭 (인사관리) - 사원 정보 - 학력 select
	public List selectOneMemberAca(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectList("member.oneMemberInfoAca",m);
	}
	// 관리자탭 (인사관리) - 사원 정보 - 자격증 select
	public List selectOneMemberLic(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectList("member.oneMemberInfoLic",m);
	}
	// 관리자탭 (인사관리) - 사원 정보 - 경력 select
	public List selectOneMemberCar(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectList("member.oneMemberInfoCar",m);
	}
	// 관리자탭 (인사관리) - 사원 정보 - 병력 select
	public Military selectOneMemberMil(SqlSessionTemplate sqlSession, Member m) {
		return sqlSession.selectOne("member.oneMemberInfoMil",m);
	}
	// 관리자탭 (인사관리) - 사원 정보 - 사원 정보 변경 - all delete
	public int updateDeleteMemberInfo(SqlSessionTemplate sqlSession, Member m, String[] tbl) {
		int result = 0;
		for(int i=0; i<tbl.length; i++){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("memNo", m.getMemNo());
			map.put("tableName", tbl[i]);
			result += sqlSession.delete("member.allDeleteMemberInfo",map); // 성공했으면 +1
		}
		return result;
	}
	
	// 조직도 ----------------------------------------------------------------------------------------------------------
	// 관리자탭 (인사관리) - 조직도 select
	public ArrayList<Member> selectOrganizationChart(SqlSessionTemplate sqlSession) {
		List list = sqlSession.selectList("member.selectDeptList");
		return (ArrayList<Member>)list;
	}
	// 관리자탭 (인사관리) - 조직도 - 사원 부서 이동 update
	public int updateMemberDepartment(SqlSessionTemplate sqlSession, int[] memNo, String deptCode) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("memNo", memNo);
		map.put("deptCode", deptCode);
		return sqlSession.update("member.changeDepartment",map);
	}
	// 관리자탭 (인사관리) - 조직도 - 부서 추가 insert
	public int insertDepartment(SqlSessionTemplate sqlSession, String deptCode, String deptName) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("deptCode", deptCode);
		map.put("deptName", deptName);
		return sqlSession.insert("member.addDepartment",map);
	}
	// 관리자탭 (인사관리) - 조직도 - 부서 이름 수정 update
	public int updateDepartmentName(SqlSessionTemplate sqlSession, String deptCode, String deptName) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("deptCode", deptCode);
		map.put("deptName", deptName);
		return sqlSession.update("member.modifyDepartment",map);
	}
	// 관리자탭 (인사관리) - 조직도 - 부서 삭제 update
	public int updateDepartmentDelete(SqlSessionTemplate sqlSession, String deptCode) {
		return sqlSession.update("member.deleteDepartment",deptCode);
	}

	//BY 진원   조직도 - 부서목록
	public ArrayList<Department> selectAllDepartment(SqlSessionTemplate sqlSession) {
		List<Department> list = sqlSession.selectList("member.selectAllDepartment");
		return (ArrayList<Department>) list;
	}
	//BY 다빈 조직도 - 부서목록 (삭제한것까지 모두)
	public ArrayList<Department> selectAllDeptCode(SqlSessionTemplate sqlSession) {
		List<Department> list = sqlSession.selectList("member.selectAllDeptCode");
		return (ArrayList<Department>) list;
	}

	

	

	

	

	

	

	


}
