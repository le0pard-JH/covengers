package board.model;

public class QnaQuestionVO {

	private String qnaNo;		// 작성번호
	private String qUserno;		// 문의 회원번호
	private String qUserName;	// 문의 회원이름
	private String qSubject;	// 문의 제목
	private String qContent;	// 질문내용
	private String qCategory;	// 질문 카테고리
	private String qDate;		// 질문일자
	private String fk_answerNo;	// 답변번호
	
	
	
	public String getQnaNo() {
		return qnaNo;
	}

	public void setQnaNo(String qnaNo) {
		this.qnaNo = qnaNo;
	}

	public String getFk_answerNo() {
		return fk_answerNo;
	}

	public void setFk_answerNo(String fk_answerNo) {
		this.fk_answerNo = fk_answerNo;
	}

	public String getqUserno() {
		return qUserno;
	}

	public void setqUserno(String qUserno) {
		this.qUserno = qUserno;
	}

	public String getqUserName() {
		return qUserName;
	}

	public void setqUserName(String qUserName) {
		this.qUserName = qUserName;
	}

	public String getqSubject() {
		return qSubject;
	}

	public void setqSubject(String qSubject) {
		this.qSubject = qSubject;
	}

	public String getqContent() {
		return qContent;
	}

	public void setqContent(String qContent) {
		this.qContent = qContent;
	}

	public String getqCategory() {
		return qCategory;
	}

	public void setqCategory(String qCategory) {
		this.qCategory = qCategory;
	}

	public String getqDate() {
		return qDate;
	}

	public void setqDate(String qDate) {
		this.qDate = qDate;
	}

	

	public String getqDateString() {
		
		String date = "";
		date += qDate.substring(0,4);
		date += "년 ";
		date += qDate.substring(4,6);
		date += "월 ";
		date += qDate.substring(6,8);
		date += "일";
		date += qDate.substring(8);
		return date;
	}
	
	
	
	
}
