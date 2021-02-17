package kr.or.houroffice.resource.model.vo;

public class ResourcePage {
	private int currentPage; //조회할 현재 페이지
	private int recordCountPerPage; //한페이지에 출력할 갯수
	private int naviCountPerPage; //한 페이지에 보이는 네비의 갯수
	
	//현재 페이지에 보여줄 게시물을 가져오기 위해 시작페이지와 끝페이지 설정
	private int startPage; //getter만
	private int endPage; //getter만
	
	private int postTotalCount; //게시물의 총 갯수
	
	private int pageTotalCount; //생성할 페이지 갯수 getter만
	
	private int startNavi; //페이지네비 시작 번호
	private int endNavi; //페이지네비 끝 번호
	
	private String url; //네비에 연결할 url
	private String category;
	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public ResourcePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResourcePage(int currentPage, int recordCountPerPage, int naviCountPerPage, int startPage, int endPage,
			int postTotalCount, int pageTotalCount, int startNavi, int endNavi, String url) {
		super();
		this.currentPage = currentPage;
		this.recordCountPerPage = recordCountPerPage;
		this.naviCountPerPage = naviCountPerPage;
		this.startPage = startPage;
		this.endPage = endPage;
		this.postTotalCount = postTotalCount;
		this.pageTotalCount = pageTotalCount;
		this.startNavi = startNavi;
		this.endNavi = endNavi;
		this.url = url;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getRecordCountPerPage() {
		return recordCountPerPage;
	}

	public int getNaviCountPerPage() {
		return naviCountPerPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setRecordCountPerPage(int recordCountPerPage) {
		this.recordCountPerPage = recordCountPerPage;
	}

	public void setNaviCountPerPage(int naviCountPerPage) {
		this.naviCountPerPage = naviCountPerPage;
	}

	public int getStartPage() {
		return currentPage * recordCountPerPage - (recordCountPerPage-1);
	}

	public int getEndPage() {
		return currentPage * recordCountPerPage;
	}

	public int getPostTotalCount() {
		return postTotalCount;
	}

	public void setPostTotalCount(int postTotalCount) {
		this.postTotalCount = postTotalCount;
		
		if(postTotalCount % recordCountPerPage>0) { //마지막 페이지는 게시물 갯수가 적은 경우 
			pageTotalCount = (postTotalCount / recordCountPerPage)+1 ;
		}else { //한 페이지에 게시물이 꽉차는 경우
			pageTotalCount = (postTotalCount / recordCountPerPage)+0 ;
		}
	}
	
	public int getPageTotalCount(){
		return pageTotalCount;
	}

	public int getStartNavi() {
		startNavi = ((currentPage-1) / naviCountPerPage) * naviCountPerPage+1; 
		return startNavi;
	}

	public int getEndNavi() {
		endNavi = startNavi+ naviCountPerPage -1;
		if(endNavi > pageTotalCount) {
			endNavi = pageTotalCount;
		}
		return endNavi; 
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
