package kr.or.houroffice.approval.model.vo;

import kr.or.houroffice.common.Page;

public class AprListPage extends Page{
	private int memNo; //조회할 사번
	private String deptCode; //조회할 부서
	private String aprStatus; //결재 상태에 따른 필터링 
	
	//검색을 위한 필드
	private String searchType; //title, member
	private String keyword;
	
	public AprListPage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AprListPage(int currentPage, int recordCountPerPage, int naviCountPerPage, int startPage, int endPage,
			int postTotalCount, int pageTotalCount, int startNavi, int endNavi, String url) {
		super(currentPage, recordCountPerPage, naviCountPerPage, startPage, endPage, postTotalCount, pageTotalCount, startNavi,
				endNavi, url);
		// TODO Auto-generated constructor stub
	}
	
	public AprListPage(int memNo, String deptCode, String aprStatus, String searchType, String keyword) {
		super();
		this.memNo = memNo;
		this.deptCode = deptCode;
		this.aprStatus = aprStatus;
		this.searchType = searchType;
		this.keyword = keyword;
	}
	public int getMemNo() {
		return memNo;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getAprStatus() {
		return aprStatus;
	}
	public void setAprStatus(String aprStatus) {
		this.aprStatus = aprStatus;
	}
	public String getSearchType() {
		return searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
}
