package board.model;

public class ReviewVO {

	private String reviewNo;  // 리뷰작성번호
	private String orderNo;   // 주문번호
	private String userNo;	  // 회원번호
	private String rContents; // 리뷰내용
	private String rDate;	  // 작성일자
	private int rGrade;		  // 평점
	
	
	public String getReviewNo() {
		return reviewNo;
	}
	public void setReviewNo(String reviewNo) {
		this.reviewNo = reviewNo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getrContents() {
		return rContents;
	}
	public void setrContents(String rContents) {
		this.rContents = rContents;
	}
	public String getrDate() {
		return rDate;
	}
	public void setrDate(String rDate) {
		this.rDate = rDate;
	}
	public int getrGrade() {
		return rGrade;
	}
	public void setrGrade(int rGrade) {
		this.rGrade = rGrade;
	}
	
	
	
	
	
}
