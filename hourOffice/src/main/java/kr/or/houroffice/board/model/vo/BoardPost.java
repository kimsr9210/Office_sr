package kr.or.houroffice.board.model.vo;

import java.sql.Timestamp;

public class BoardPost {
	private int postNo;				// 게시글 번호
	private String title;			// 게시글 제목
	private Timestamp postingDate;	// 게시일
	private int memNo;				// 사번
	private String writer;			// 이름
	private String content;			// 게시글 내용
	private int hits;				// 게시글 조회수
	private char delYN;				// 게시글 삭제여부
	private Timestamp delDate;		// 게시글 삭제일
	
	private String deptCode;		// 부서코드
	private String deptName;		// 부서이름
	private String memPosition;		// 직위
	private String memProfile;		// 프로필사진
	
	private int fileNo;				// 파일 고유번호
	private String origName;		// 파일 오리지널 이름
	private String chgName;			// 파일 바뀐 이름
	private String filePath;		// 파일 저장 경로
	private long fileSize;			// 파일 크기
	private Timestamp uploadDate;	// 파일 업로드일
	public BoardPost() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardPost(int postNo, String title, Timestamp postingDate, int memNo, String writer, String content,
			int hits, char delYN, Timestamp delDate, String deptCode, String deptName, String memPosition,
			String memProfile, int fileNo, String origName, String chgName, String filePath, long fileSize,
			Timestamp uploadDate) {
		super();
		this.postNo = postNo;
		this.title = title;
		this.postingDate = postingDate;
		this.memNo = memNo;
		this.writer = writer;
		this.content = content;
		this.hits = hits;
		this.delYN = delYN;
		this.delDate = delDate;
		this.deptCode = deptCode;
		this.deptName = deptName;
		this.memPosition = memPosition;
		this.memProfile = memProfile;
		this.fileNo = fileNo;
		this.origName = origName;
		this.chgName = chgName;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.uploadDate = uploadDate;
	}
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(Timestamp postingDate) {
		this.postingDate = postingDate;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public char getDelYN() {
		return delYN;
	}
	public void setDelYN(char delYN) {
		this.delYN = delYN;
	}
	public Timestamp getDelDate() {
		return delDate;
	}
	public void setDelDate(Timestamp delDate) {
		this.delDate = delDate;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getMemPosition() {
		return memPosition;
	}
	public void setMemPosition(String memPosition) {
		this.memPosition = memPosition;
	}
	public String getMemProfile() {
		return memProfile;
	}
	public void setMemProfile(String memProfile) {
		this.memProfile = memProfile;
	}
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public String getOrigName() {
		return origName;
	}
	public void setOrigName(String origName) {
		this.origName = origName;
	}
	public String getChgName() {
		return chgName;
	}
	public void setChgName(String chgName) {
		this.chgName = chgName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public Timestamp getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}


}
