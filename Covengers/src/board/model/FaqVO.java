package board.model;

public class FaqVO {

	private int faqNo;			// FAQ 번호
	private String faqQuestion;	// 질문 내용
	private String faqAnswer;	// 답변 내용
	private String faqAdminid;	// 등록 관리자 아이디
	
	public int getFaqNo() {
		return faqNo;
	}
	public void setFaqNo(int faqNo) {
		this.faqNo = faqNo;
	}
	public String getFaqQuestion() {
		return faqQuestion;
	}
	public void setFaqQuestion(String faqQuestion) {
		this.faqQuestion = faqQuestion;
	}
	public String getFaqAnswer() {
		return faqAnswer;
	}
	public void setFaqAnswer(String faqAnswer) {
		this.faqAnswer = faqAnswer;
	}
	public String getFaqAdminid() {
		return faqAdminid;
	}
	public void setFaqAdminid(String faqAdminid) {
		this.faqAdminid = faqAdminid;
	}
	
	
	
	
	
}
