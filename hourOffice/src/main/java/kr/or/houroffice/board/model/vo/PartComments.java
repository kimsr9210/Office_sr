package kr.or.houroffice.board.model.vo;

import java.sql.Timestamp;

public class PartComments {
	
	private int partComntNo;		// 댓글 고유번호
	private int partNo;				// 게시글 고유번호
	private int memNo;				// 댓글 작성자 사번
	private String partComntWriter;	// 댓글 작성자 이름
	private String partComntEmail;	// 댓글 작성자 이메일
	private Timestamp partComntDate;		// 댓글 작성일
	private String partComnt;		// 댓글 내용
	private String memPosition;		// 댓글 작성자 직위
	private String memProfile;		// 댓글 작성자 프로필사진
	public PartComments() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PartComments(int partComntNo, int partNo, int memNo, String partComntWriter, String partComntEmail,
			Timestamp partComntDate, String partComnt, String memPosition, String memProfile) {
		super();
		this.partComntNo = partComntNo;
		this.partNo = partNo;
		this.memNo = memNo;
		this.partComntWriter = partComntWriter;
		this.partComntEmail = partComntEmail;
		this.partComntDate = partComntDate;
		this.partComnt = partComnt;
		this.memPosition = memPosition;
		this.memProfile = memProfile;
	}
	public int getPartComntNo() {
		return partComntNo;
	}
	public void setPartComntNo(int partComntNo) {
		this.partComntNo = partComntNo;
	}
	public int getPartNo() {
		return partNo;
	}
	public void setPartNo(int partNo) {
		this.partNo = partNo;
	}
	public int getMemNo() {
		return memNo;
	}
	public void setMemNo(int memNo) {
		this.memNo = memNo;
	}
	public String getPartComntWriter() {
		return partComntWriter;
	}
	public void setPartComntWriter(String partComntWriter) {
		this.partComntWriter = partComntWriter;
	}
	public String getPartComntEmail() {
		return partComntEmail;
	}
	public void setPartComntEmail(String partComntEmail) {
		this.partComntEmail = partComntEmail;
	}
	public Timestamp getPartComntDate() {
		return partComntDate;
	}
	public void setPartComntDate(Timestamp partComntDate) {
		this.partComntDate = partComntDate;
	}
	public String getPartComnt() {
		return partComnt;
	}
	public void setPartComnt(String partComnt) {
		this.partComnt = partComnt;
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
	
}
