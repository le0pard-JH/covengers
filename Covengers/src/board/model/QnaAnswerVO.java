package board.model;

public class QnaAnswerVO {

	private String fk_qnaNo; // 작성 번호 
	private String qAdminid; // 답변 관리자 아이디
	private String qAnswer;	 // 답변 내용
	private String qAdate;	 // 답변 일자
	
	
	
	public String getFk_qnaNo() {
		return fk_qnaNo;
	}
	public void setFk_qnaNo(String fk_qnaNo) {
		this.fk_qnaNo = fk_qnaNo;
	}
	public String getqAdminid() {
		return qAdminid;
	}
	public void setqAdminid(String qAdminid) {
		this.qAdminid = qAdminid;
	}
	public String getqAnswer() {
		return qAnswer;
	}
	public void setqAnswer(String qAnswer) {
		this.qAnswer = qAnswer;
	}
	public String getqAdate() {
		return qAdate;
	}
	public void setqAdate(String qAdate) {
		this.qAdate = qAdate;
	}
	

	
	
}
