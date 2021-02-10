package kr.or.houroffice.board.model.vo;

import java.sql.Timestamp;

public class BoardFile {

	private int fileNo;				// 파일 고유번호
	private int postNo;				// 게시물 번호
	private String origName;		// 파일 오리지널 이름
	private String chgName;			// 파일 바뀐 이름
	private String path;			// 파일 저장 경로
	private long size;				// 파일 크기
	private Timestamp uploadDate;	// 파일 업로드일
	public BoardFile() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardFile(int fileNo, int postNo, String origName, String chgName, String path, long size,
			Timestamp uploadDate) {
		super();
		this.fileNo = fileNo;
		this.postNo = postNo;
		this.origName = origName;
		this.chgName = chgName;
		this.path = path;
		this.size = size;
		this.uploadDate = uploadDate;
	}
	public int getFileNo() {
		return fileNo;
	}
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	public int getPostNo() {
		return postNo;
	}
	public void setPostNo(int postNo) {
		this.postNo = postNo;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public Timestamp getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Timestamp uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	
}
