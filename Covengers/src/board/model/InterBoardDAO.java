package board.model;

import java.sql.SQLException;
import java.util.List;

public interface InterBoardDAO {

	// FAQ 게시물 리스트를 select하여 보여주는 메서드
	List<FaqVO> selectFaqList() throws SQLException;

	// FAQ 게시물을 insert 하는 메서드
	int registerFaq(FaqVO fvo) throws SQLException;

	// FAQ 게시글 번호로 게시물을 조회(select)하여 리턴하는 메서드
	FaqVO selectOneFaq(String requestFaqNo) throws SQLException;

	// FAQ 게시물을 수정하는 메서드
	int updateFaq(FaqVO fvo) throws SQLException;

	// 요청한 faqNo를 가진 게시물을 삭제해주는 메서드
	int deleteFaq(String requestFaqNo) throws SQLException;

	// 모든 FAQ 게시물을 삭제해주는 메서드
	int deleteAllFaq() throws SQLException;

	// QnA 카테고리 컬럼을 조회해오는 메서드
	List<String> selectQnaCategory() throws SQLException;

	// 회원이 고른 카테고리의 값에 따라 설명을 조회(select)해오는 메서드
	String selectCateExplain(String category) throws SQLException;

	// 회원이 작성한 QnA 정보를 insert 하는 메서드
	int registerQuestion(QnaQuestionVO qqvo) throws SQLException;

	// QnA 게시물 리스트를 select하여 보여주는 메서드
//	List<QnaQuestionVO> selectQnaList() throws SQLException;
	List<QnaQuestionVO> selectQnaList(String currentShowPageNo) throws SQLException;

	// 입력받은 게시물 번호와 해당 게시물의 회원번호를 비교하여 일치하는 행을 조회(select)하는 메서드
	QnaQuestionVO selectQnaDetail(String selectNo, String userNo) throws SQLException;

	// 입력받은 게시물 번호로 tbl_qnaboard의 해당 답변(tbl_qnaanswer)을 조회하는 메서드
	QnaAnswerVO selectAnswer(String selectNo) throws SQLException;

	// 입력받은 값들을 tbl_qnaquestion_test2에서 수정(update)하는 메서드 
	int updateQnaQuestion(QnaQuestionVO bvo) throws SQLException;

	// 회원이 요청한 게시물을 삭제(delete)해주는 메서드
	int deleteQuestion(String qnaNo) throws SQLException;

	// 관리자 번호와 게시물 번호 답변을 가지고 답변을 등록(insert) 해주는 메서드
	int registerAnswer(QnaAnswerVO qavo) throws SQLException;

	// 게시물 번호와 답변 내용을 가지고 수정(update)해주는 메서드
	int updateAnswer(QnaAnswerVO qavo) throws SQLException;

	// 답변 게시물 번호로 답변 게시물을 삭제(delete)하는 메서드
	int deleteAnswer(String qnaNo) throws SQLException;

	// 페이지바를 만들기 위해서 총 페이지수 알아오기(select)
	int getTotalPage() throws SQLException;

	// 답변 테이블에서 fk_qnano만 조회(select)해오는 메서드
	List<String> selectIsAnswer()throws SQLException;
	
	
}
