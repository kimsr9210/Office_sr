package kr.or.houroffice.admin.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.member.model.vo.Member;

@Repository("adminDAO")
public class AdminDAO {
	
	//관리자 관리 - 부서별 관리자 리스트 전체 조회
	public ArrayList<Member> selectAllMember(SqlSessionTemplate sqlSession) {
		List list = sqlSession.selectList("admin.allMemberList");
		return (ArrayList<Member>)list;
	}//selectAllMember
	
	//삭제 조회 - 삭제된 사원 조회
	public ArrayList<Member> selectDeleteMember(SqlSessionTemplate sqlSession, int currentPage,
			int recordCountPerPage) {
		
		//페이징 처리
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		List list = sqlSession.selectList("admin.selectDeleteMember", map);
		return (ArrayList<Member>)list;
	}//selectDeleteMember

	//삭제 조회 - 삭제된 사원 조회 - 삭제된 사원 수
	public int countDeleteMember(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("admin.countDeleteMemberCount");
	}//countDeleteMember
	
	//삭제 조회 - 삭제된 사원 조회 - 페이징 처리
	public String getPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage,
			int naviCountPerPage) {
		
		// 현재 변수
		// currentPage			: 현재 페이지
		// recordCountPerPage	: 1 페이지당 보여질 게시물의 개수
		// naviCountPerPage		: pageNavi가 몇개씩 보여질 것인지에 대한 변수
		int postTotalCount = countDeleteMember(sqlSession); //전체 게시물 갯수
		
		//생성될 페이지 개수
		int pageTotalCount; //전체 페이지
			if(postTotalCount % recordCountPerPage > 0) { //마지막 페이지 숫자
				pageTotalCount = postTotalCount / recordCountPerPage +1;
			} else {
				pageTotalCount = postTotalCount / recordCountPerPage +0;
			}
		
		//현재 페이지 번호
		int startNavi = ((currentPage -1) / naviCountPerPage) * naviCountPerPage +1;
		
		//마지막 페이지 번호
		int endNavi = startNavi + naviCountPerPage -1;
			//마지막 페이지 번호가 총 페이지 수보다 높을 때
			if(endNavi > pageTotalCount) {
				endNavi = pageTotalCount;
			}
		
		//pageNavi 모양 구성
		StringBuilder sb = new StringBuilder();
		
			//만약 첫번째 pageNavi가 아니라면 '<' 모양을 추가해라
			if(startNavi != 1) {
				sb.append("<a class='page-link' href='/adminDeleteMemberPage.ho?currentPage="+(startNavi-1)+"'><</a>");
			}
			
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==currentPage) {
					sb.append("<a class='page-link' href='/adminDeleteMemberPage.ho?currentPage="+i+"'><B>"+i+"</B></a>");
				} else {
					sb.append("<a class='page-link' href='/adminDeleteMemberPage.ho?currentPage="+i+"'>"+i+"</a>");
				}
			}
			
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라
			if(endNavi != pageTotalCount) {
				sb.append("<a class='page-link' href='/adminDeleteMemberPage.ho?currentPage="+(startNavi+1)+"'>></a>");
			}
			
		return sb+"";
	}//getPageNavi

}//AdminDAO
