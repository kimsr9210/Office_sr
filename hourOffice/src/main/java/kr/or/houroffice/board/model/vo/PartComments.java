package kr.or.houroffice.board.model.vo;

import java.sql.Date;

public class PartComments {
	
	private int partComntNo;		// 댓글 고유번호
	private int partNo;				// 게시글 고유번호
	private int memNo;				// 댓글 작성자 사번
	private String partComntWriter;	// 댓글 작성자 이름
	private String partComntEmail;	// 댓글 작성자 이메일
	private Date partComntDate;		// 댓글 작성일
	private String partComnt;		// 댓글 내용
	
	
}
