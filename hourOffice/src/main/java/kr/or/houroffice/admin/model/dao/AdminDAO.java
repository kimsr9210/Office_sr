package kr.or.houroffice.admin.model.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import kr.or.houroffice.admin.model.vo.AdminBoard;
import kr.or.houroffice.approval.model.vo.Approval;
import kr.or.houroffice.member.model.vo.Member;

@Repository("adminDAO")
public class AdminDAO {
	
	//관리자 관리 - 부서별 관리자 리스트 전체 조회
	public ArrayList<Member> selectAllMember(SqlSessionTemplate sqlSession) {
		List list = sqlSession.selectList("admin.allMemberList");
		return (ArrayList<Member>)list;
	}//selectAllMember
	
	//관리자 관리 - 모달 - 사원 검색 (ajax)
	public ArrayList<Member> adminSearchModal(SqlSessionTemplate sqlSession, String keyword) {
		List list = sqlSession.selectList("admin.adminSearchModal",keyword);
		return (ArrayList<Member>)list;
	}//adminSearchModal
	
	//관리자 관리 - 모달 - 전산관리자 권한 추가 (ajax)
	public int adminUpdateAdRight(SqlSessionTemplate sqlSession, List<String> memNoList) {
		return sqlSession.update("admin.adminUpdateAdRight",memNoList);
	}//adminUpdateAdRight

	//관리자 관리 - 모달 - 인사관리자 권한 추가 (ajax)
	public int adminUpdatePeRight(SqlSessionTemplate sqlSession, List<String> memNoList) {
		return sqlSession.update("admin.adminUpdatePeRight",memNoList);
	}//adminUpdatePeRight

	//관리자 관리 - 모달 - 총무관리자 권한 추가 (ajax)
	public int adminUpdateGeRight(SqlSessionTemplate sqlSession, List<String> memNoList) {
		return sqlSession.update("admin.adminUpdateGeRight",memNoList);
	}//adminUpdateGeRight
	
	//관리자 관리 - 선택된 사원 권한 삭제 (ajax)
	public int adminDeleteRight(SqlSessionTemplate sqlSession, List<String> memNoList) {
		return sqlSession.update("admin.adminDeleteRight",memNoList);
	}//adminDeleteRight
	
	//삭제 조회 - 삭제된 사원 조회
	public ArrayList<Member> selectDeleteMember(SqlSessionTemplate sqlSession, int currentPage,  int recordCountPerPage) {
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
	public String getPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage, int naviCountPerPage) {
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
				sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteMemberPage.ho?currentPage="+(startNavi-1)+"'>◀</a></li>");
			}
			
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==currentPage) {
					sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteMemberPage.ho?currentPage="+i+"'><B>"+i+"</B></a></li>");
				} else {
					sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteMemberPage.ho?currentPage="+i+"'>"+i+"</a></li>");
				}
			}
			
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteMemberPage.ho="+(startNavi+1)+"'>▶</a></li>");
			}
			
		return sb+"";
	}//getPageNavi

	//삭제 조회 - 삭제된 사원 조회 - 검색
	public List selectSearchDeleteMember(SqlSessionTemplate sqlSession, String searchType, String keyword, int currentPage, int recordCountPerPage) {
		//페이징 처리
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;
				
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		List list = sqlSession.selectList("admin.selectSearchDeleteMember", map);
		
		return list;
	}//selectSearchDeleteMember

	//삭제 조회 - 삭제된 사원 조회 - 검색 - 페이징 처리
	public String searchGetPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage, int naviCountPerPage, int searchCount, String searchType, String keyword) {
		// 현재 변수
		// currentPage			: 현재 페이지
		// recordCountPerPage	: 1 페이지당 보여질 게시물의 개수
		// naviCountPerPage		: pageNavi가 몇개씩 보여질 것인지에 대한 변수
				
		//생성될 페이지 개수
		int pageTotalCount; //전체 페이지
			if(searchCount % recordCountPerPage > 0) { //마지막 페이지 숫자
				pageTotalCount = searchCount / recordCountPerPage +1;
			} else {
				pageTotalCount = searchCount / recordCountPerPage +0;
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
				sb.append("<li class='page-list'><a class='page-link' href='/adminSearchDeleteMember.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+(startNavi-1)+"'>◀</a></li>");
			}
					
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==currentPage) {
					sb.append("<li class='page-list'><a class='page-link' href='/adminSearchDeleteMember.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+i+"'><B>"+i+"</B></a></li>");
				} else {
					sb.append("<li class='page-list'><a class='page-link' href='/adminSearchDeleteMember.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+i+"'>"+i+"</a></li>");
				}
			}
					
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a class='page-link' href='/adminSearchDeleteMember.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+(startNavi+1)+"'>▶</a></li>");
			}
					
		return sb+"";
	}//searchGetPageNavi

	//삭제 조회  - 삭제된 사원 조회 - 삭제된 사원 복원 (ajax)
	public int deleteMemberCancel(SqlSessionTemplate sqlSession, List<String> memNoList) {
		return sqlSession.update("admin.deleteMemberCancel",memNoList);
	}//deleteMemberCancel

	//삭제 조회 - 삭제된 사원 조회 - 삭제된 사원 영구 삭제 (ajax)
	public int deleteMember(SqlSessionTemplate sqlSession, List<String> memNoList) {
		return sqlSession.delete("admin.deleteMember",memNoList);
	}//deleteMember

	//삭제 조회 - 삭제된 게시글 조회 - 삭제된 게시판 수
	public int countBoard(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("admin.countBoard");
	}//countBoard

	//삭제 조회 - 삭제된 게시글 조회
	public ArrayList<AdminBoard> selectDeleteBoard(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage) {
		//페이징 처리
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;
				
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		List list = sqlSession.selectList("admin.selectDeleteBoard", map);
		return (ArrayList<AdminBoard>)list;
	}//selectDeleteBoard

	//삭제 조회 - 삭제된 부서별 게시글 조회 - 페이징 처리
	public String getBoardPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage, int naviCountPerPage) {
		// 현재 변수
		// currentPage			: 현재 페이지
		// recordCountPerPage	: 1 페이지당 보여질 게시물의 개수
		// naviCountPerPage		: pageNavi가 몇개씩 보여질 것인지에 대한 변수
		int postTotalCount = countBoard(sqlSession); //전체 게시물 갯수
			
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
				sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteNoticePage.ho?currentPage="+(startNavi-1)+"'>◀</a></li>");
			}
				
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==currentPage) {
					sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteNoticePage.ho?currentPage="+i+"'><B>"+i+"</B></a></li>");
				} else {
					sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteNoticePage.ho?currentPage="+i+"'>"+i+"</a></li>");
				}
			}
				
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteNoticePage.ho?currentPage="+(startNavi+1)+"'>▶</a></li>");
			}
				
		return sb+"";
	}//getPageNavi
	
	//삭제 조회 - 삭제된 부서별 게시글 검색
	public List selectSearchBoard(SqlSessionTemplate sqlSession, String searchType, String keyword, int currentPage, int recordCountPerPage) {
		//페이징 처리
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;
						
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		List list = sqlSession.selectList("admin.selectSearchBoard", map);
		return list;		
	}//selectSearchBoard
		
	//삭제 조회 - 삭제된 부서별 게시글 검색 - 페이징 처리
	public String searchGetBoardPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage, int naviCountPerPage, int searchCount, String searchType, String keyword) {
		// 현재 변수
		// currentPage			: 현재 페이지
		// recordCountPerPage	: 1 페이지당 보여질 게시물의 개수
		// naviCountPerPage		: pageNavi가 몇개씩 보여질 것인지에 대한 변수
						
		//생성될 페이지 개수
		int pageTotalCount; //전체 페이지
			if(searchCount % recordCountPerPage > 0) { //마지막 페이지 숫자
				pageTotalCount = searchCount / recordCountPerPage +1;
			} else {
				pageTotalCount = searchCount / recordCountPerPage +0;
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
				sb.append("<li class='page-list'><a class='page-link' href='/adminSearchBoard.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+(startNavi-1)+"'>◀</a></li>");
			}
							
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==currentPage) {
					sb.append("<li class='page-list'><a class='page-link' href='/adminSearchBoard.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+i+"'><B>"+i+"</B></a></li>");
				} else {
					sb.append("<li class='page-list'><a class='page-link' href='/adminSearchBoard.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+i+"'>"+i+"</a></li>");
				}
			}
							
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a class='page-link' href='/adminSearchBoard.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+(startNavi+1)+"'>▶</a></li>");
			}
							
		return sb+"";
	}//searchGetPageNavi

	//삭제 조회 - 삭제된 부서별 게시글 복원 (ajax)
	public int deleteBoardCancel(SqlSessionTemplate sqlSession, List<String> noList) {		
		return sqlSession.update("admin.deleteBoardCancel",noList);
	}//deleteBoardCancel
	
	//삭제 조회 - 삭제된 부서별 게시글 영구 삭제 (ajax)
	public int deleteBoard(SqlSessionTemplate sqlSession, List<String> noList) {		
		return sqlSession.update("admin.deleteBoard",noList);
	}//deleteBoard

	//삭제 조회 - 삭제된 결재안 조회  - 삭제된 결재안 수
	public int countDeleteApproval(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("admin.countDeleteApproval");
	}//countDeleteApproval

	//삭제 조회 - 삭제된 결재안 조회
	public ArrayList<Approval> selectDeleteApproval(SqlSessionTemplate sqlSession, int currentPage,
			int recordCountPerPage) {
		//페이징 처리
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;
						
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("end", end);
		List list = sqlSession.selectList("admin.selectDeleteApproval", map);
		return (ArrayList<Approval>)list;
	}//selectDeleteApproval

	//삭제 조회 - 삭제된 결재안 조회 - 페이징 처리
	public String getApprovalPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage,
			int naviCountPerPage) {
		// 현재 변수
		// currentPage			: 현재 페이지
		// recordCountPerPage	: 1 페이지당 보여질 게시물의 개수
		// naviCountPerPage		: pageNavi가 몇개씩 보여질 것인지에 대한 변수
		int postTotalCount = countDeleteApproval(sqlSession); //전체 게시물 갯수
					
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
				sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteApprovalPage.ho?currentPage="+(startNavi-1)+"'>◀</a></li>");
			}
						
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==currentPage) {
					sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteApprovalPage.ho?currentPage="+i+"'><B>"+i+"</B></a></li>");
				} else {
					sb.append("<li class='page-list'><a class='page-link' href='/adminDeleteApprovalPage.ho?currentPage="+i+"'>"+i+"</a></li>");
				}
			}
						
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a href='/adminDeleteApprovalPage.ho?currentPage="+(startNavi+1)+"'>▶</a></li>");
			}
						
		return sb+"";
	}//getApprovalPageNavi

	//삭제 조회 - 삭제된 결재안 검색
	public List selectSearchDeleteApproval(SqlSessionTemplate sqlSession, String searchType,
			String keyword, int currentPage, int recordCountPerPage) {
		//페이징 처리
		int start = currentPage * recordCountPerPage - (recordCountPerPage-1);
		int end = currentPage * recordCountPerPage;
						
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("searchType", searchType);
		map.put("keyword", keyword);
		map.put("start", start);
		map.put("end", end);
		List list = sqlSession.selectList("admin.selectSearchDeleteApproval", map);
		return list;
	}//selectSearchDeleteApproval

	//삭제 조회 - 삭제된 결재안 검색 - 페이징 처리
	public String searchGetApprovalPageNavi(SqlSessionTemplate sqlSession, int currentPage, int recordCountPerPage,
			int naviCountPerPage, int searchCount, String searchType, String keyword) {
		// 현재 변수
		// currentPage			: 현재 페이지
		// recordCountPerPage	: 1 페이지당 보여질 게시물의 개수
		// naviCountPerPage		: pageNavi가 몇개씩 보여질 것인지에 대한 변수
								
		//생성될 페이지 개수
		int pageTotalCount; //전체 페이지
			if(searchCount % recordCountPerPage > 0) { //마지막 페이지 숫자
				pageTotalCount = searchCount / recordCountPerPage +1;
			} else {
				pageTotalCount = searchCount / recordCountPerPage +0;
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
				sb.append("<li class='page-list'><a class='page-link' href='/adminSearchApproval.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+(startNavi-1)+"'>◀</a></li>");
			}
									
			for(int i=startNavi; i<=endNavi; i++) {
				if(i==currentPage) {
					sb.append("<li class='page-list'><a class='page-link' href='/adminSearchApproval.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+i+"'><B>"+i+"</B></a></li>");
				} else {
					sb.append("<li class='page-list'><a class='page-link' href='/adminSearchApproval.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+i+"'>"+i+"</a></li>");
				}
			}
									
			//만약 마지막 pageNavi가 아니라면 '>' 모양을 추가해라
			if(endNavi != pageTotalCount) {
				sb.append("<li class='page-list'><a class='page-link' href='/adminSearchApproval.ho?searchType="+searchType+"&keyword="+keyword+"&currentPage="+(startNavi+1)+"'>▶</a></li>");
			}
									
		return sb+"";
	}//searchGetApprovalPageNavi

	//삭제 조회 - 삭제된 결재안 복원 (ajax)
	public int deleteApprovalCancel(SqlSessionTemplate sqlSession, List<String> appNoList) {
		return sqlSession.update("admin.deleteApprovalCancel",appNoList);
	}//deleteApprovalCancel

	//삭제 조회 - 삭제된 부서별 게시글 영구 삭제 (ajax)
	public int deleteApproval(SqlSessionTemplate sqlSession, List<String> appNoList) {
		return sqlSession.update("admin.deleteApproval",appNoList);
	}//deleteApproval	
	
	//데이터/문서 관리 - 사원 기록 삭제
	public int expireDeleteMember(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("admin.expireDeleteMember");
	}//countDeleteMember

	//데이터/문서 관리 - 미경과된 사원 기록 삭제
	public int expireNotDeleteMember(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("admin.expireNotDeleteMember");
	}//expireNotDeleteMember

	//데이터/문서 관리 - 경과된 삭제 대기 게시물
	public int deletePaperCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("admin.deletePaperCount");
	}//deletePaperCount

	//데이터/문서 관리 - 보존 기간 경과된 삭제 대기 게시물
	public int expirePaperCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("admin.expirePaperCount");
	}//expirePaperCount

	//데이터/문서 관리 - 미경과된 삭제 대기 게시물
	public int deleteNotPaperCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("admin.deleteNotPaperCount");
	}//deleteNotPaperCount

	//데이터/문서 관리 - 보존 기간 미경과된 삭제 대기 게시물
	public int expireNotPaperCount(SqlSessionTemplate sqlSession) {
		return sqlSession.selectOne("admin.expireNotPaperCount");
	}//expireNotPaperCount

}//AdminDAO
