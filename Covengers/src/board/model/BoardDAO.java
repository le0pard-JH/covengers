package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import util.security.Sha256;

public class BoardDAO implements InterBoardDAO {

	private DataSource ds;  	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	
	// 생성자
	public BoardDAO() {
		try {
	         Context initContext = new InitialContext();
	         Context envContext  = (Context)initContext.lookup("java:/comp/env");
	         ds = (DataSource)envContext.lookup("jdbc/covengers_oracle");
	      } catch(NamingException e) {
	         e.printStackTrace();
	      }
	}

	
	// 사용한 자원을 반납하는 close() 메소드 생성하기
	private void close() {
		      try {
		         if(rs != null)    {rs.close(); rs=null;}
		         if(pstmt != null) {pstmt.close(); pstmt=null;}
		         if(conn != null)  {conn.close(); conn=null;}
		      } catch(SQLException e) {
		         e.printStackTrace();
		      }
	}// end of private void close() {}--------------------------------------


	
	// ====== FAQ 메서드 ====== 
	// FAQ 리스트를 select하여 보여주는 메서드
	@Override
	public List<FaqVO> selectFaqList() throws SQLException {

		List<FaqVO> faqlist = new ArrayList<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = "SELECT faqno, faqquestion, faqanswer, faqadminno "+
						 "FROM tbl_faq_test "+ 
						 "ORDER BY faqno ASC ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				FaqVO fvo = new FaqVO();
				fvo.setFaqNo(rs.getInt(1));
				fvo.setFaqQuestion(rs.getString(2));
				fvo.setFaqAnswer(rs.getString(3));
				fvo.setFaqAdminid(rs.getString(4));
				
				faqlist.add(fvo);
			}
			
		} finally {
			close();
		}
		
		return faqlist;
	}// end of public List<FaqVO> selectFaqList() throws SQLException {}-------------------------------


	// FAQ를 insert 하는 메서드
	@Override
	public int registerFaq(FaqVO fvo) throws SQLException {

		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "INSERT INTO tbl_faq_test(faqno, faqquestion, faqanswer, faqadminno)\n"+
						 "VALUES(seq_faqno.NEXTVAL, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fvo.getFaqQuestion());
			pstmt.setString(2, fvo.getFaqAnswer());
			pstmt.setString(3, fvo.getFaqAdminid());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}// end of public int registerFaq(FaqVO fvo) throws SQLException {}------------------------------


	// 게시글 번호로 게시물을 조회(select)하여 리턴하는 메서드
	@Override
	public FaqVO selectOneFaq(String requestFaqNo) throws SQLException {

		FaqVO fvo = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "SELECT faqno, faqquestion, faqanswer, faqadminno "+
						 "FROM tbl_faq_test "+
						 "WHERE faqno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, requestFaqNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				fvo = new FaqVO();
				fvo.setFaqNo(rs.getInt(1));
				fvo.setFaqQuestion(rs.getString(2));
				fvo.setFaqAnswer(rs.getString(3));
				fvo.setFaqAdminid(rs.getString(4));
			}
			
		} finally {
			close();
		}
		
		return fvo;
	}// end of public FaqVO selectOneFaq(String requestFaqNo) throws SQLException {}--------------------------


	// FAQ 게시물을 수정하는 메서드
	@Override
	public int updateFaq(FaqVO fvo) throws SQLException {

		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "UPDATE tbl_faq_test SET faqquestion = ?, faqanswer = ?, faqadminno = ? "+
						 "WHERE faqno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fvo.getFaqQuestion());
			pstmt.setString(2, fvo.getFaqAnswer());
			pstmt.setString(3, fvo.getFaqAdminid());
			pstmt.setInt(4, fvo.getFaqNo());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}// end of public int updateFaq(FaqVO fvo) throws SQLException {}-----------------------------------------


	// 요청한 faqNo를 가진 게시물을 삭제해주는 메서드
	@Override
	public int deleteFaq(String requestFaqNo) throws SQLException {
		
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "DELETE FROM tbl_faq_test "+
					"WHERE faqno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(requestFaqNo));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		
		return result;
	}// end of public int deleteFaq(String requestFaqNo) throws SQLException {}-------------------------------


	// 모든 FAQ 게시물을 삭제해주는 메서드
	@Override
	public int deleteAllFaq() throws SQLException {
		
		int result = 0;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "DELETE FROM tbl_faq_test";
			
			pstmt = conn.prepareStatement(sql);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
		
	}// end of public int deleteAllFaq() throws SQLException {}------------------------------------------------


	////////////////////////////////////////////////////////////////////////////////////////
	// ====== QnA 메서드 ====== 

	// QnA 카테고리 컬럼을 조회해오는 메서드
	@Override
	public List<String> selectQnaCategory() throws SQLException {

		List<String> cateList = new ArrayList<>();
		
		try {

			conn = ds.getConnection();
			
			String sql = "SELECT column_name "+
					"FROM ALL_TAB_COLUMNS "+
					"WHERE TABLE_NAME='TBL_QNA_CATEGORY_TEST'";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				cateList.add(rs.getString(1));
			}
			
			
		} finally {
			close();
		}
		
		return cateList;
	}// end of public List<String> selectQnaCategory() throws SQLException {}------------------------


	// 사용자가 고른 카테고리의 값에 따라 설명을 조회(select)해오는 메서드
	@Override
	public String selectCateExplain(String category) throws SQLException {
		
		String explain = "";
		
		try {
			
			conn = ds.getConnection();

			String sql = " SELECT ";
			sql += category;
			sql += " FROM TBL_QNA_CATEGORY_TEST ";
			
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				explain = rs.getString(1);
			}
			
		} finally {
			close();
		}
		
		return explain;
	}// end of public String selectCateExplain(String category) throws SQLException {}---------------------------


	// 회원이 작성한 QnA 정보를 insert 하는 메서드
	@Override
	public int registerQuestion(QnaQuestionVO qqvo) throws SQLException {

		int result = 0;
		
		try {
			
			conn = ds.getConnection(); 
			

			String sql = " INSERT INTO tbl_qnaquestion_test2(qnano, fk_userno, qusername, qsubject, qcontent, qcategory) "+
						 " VALUES(seq_qnano.NEXTVAL, ?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qqvo.getqUserno());
			pstmt.setString(2, qqvo.getqUserName());
			pstmt.setString(3, qqvo.getqSubject());
			pstmt.setString(4, qqvo.getqContent());
			pstmt.setString(5, qqvo.getqCategory());
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}// end of public int registerQuestion(QnaQuestionVO qqvo) throws SQLException {}-----------------------------------

/*	
	// QnA 게시물 리스트를 select하여 보여주는 메서드
	@Override
	public List<QnaQuestionVO> selectQnaList() throws SQLException {

		List<QnaQuestionVO> questionList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "SELECT qnano, fk_userno, qusername, qsubject, to_char(qDate, 'yyyymmdd hh24:mi') "+
						 "FROM tbl_qnaquestion_test2 ";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaQuestionVO qqvo = new QnaQuestionVO();
				qqvo.setQnaNo(Integer.toString(rs.getInt(1)));
				qqvo.setqUserno(rs.getString(2));
				qqvo.setqUserName(rs.getString(3));
				qqvo.setqSubject(rs.getString(4));
				qqvo.setqDate(rs.getString(5));
				
				questionList.add(qqvo);
			}
			
		} finally {
			close();
		}
		
		return questionList;
	}// end of public List<QnaQuestionVO> selectQnaList() throws SQLException {}----------------------------------------
*/
	// QnA 게시물 리스트를 select하여 보여주는 메서드
	@Override
	public List<QnaQuestionVO> selectQnaList(String currentShowPageNo) throws SQLException {

		List<QnaQuestionVO> questionList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "select qnano, qusername, qdate\n"+
					"from\n"+
					"(\n"+
					"    select rownum AS RNO, qnano, qusername, qdate\n"+
					"    from\n"+
					"    (\n"+
					"        select qnano, qusername, to_char(qdate, 'yyyymmdd hh24:mi') AS qdate\n"+
					"        from tbl_qnaquestion_test2\n"+
					"        order by qnano desc\n"+
					"    )V\n"+
					")T\n"+
					"where rno between ? and ?";
			
			pstmt = conn.prepareStatement(sql);
			
			int currentShowPage = Integer.parseInt(currentShowPageNo);
			int sizePerPage = 10; // 한 페이지당 화면상에 보여줄 게시판 개수는 10.
			
			pstmt.setInt(1, (currentShowPage * sizePerPage) - (sizePerPage - 1)); // 공식
			pstmt.setInt(2, (currentShowPage * sizePerPage)); // 공식
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnaQuestionVO qqvo = new QnaQuestionVO();
				qqvo.setQnaNo(Integer.toString(rs.getInt(1)));
			//	qqvo.setqUserno(rs.getString(2));
				qqvo.setqUserName(rs.getString(2));
			//	qqvo.setqSubject(rs.getString(4));
				qqvo.setqDate(rs.getString(3));
				
				questionList.add(qqvo);
			}
			
		} finally {
			close();
		}
		
		return questionList;
	}// end of public List<QnaQuestionVO> selectQnaList() throws SQLException {}----------------------------------------


	// 입력받은 게시물 번호와 해당 게시물의 회원번호를 비교하여 일치하는 행을 조회(select)하는 메서드
	@Override
	public QnaQuestionVO selectQnaDetail(String selectNo, String userNo) throws SQLException {

		QnaQuestionVO qqvo = null;
		try {
			
			conn = ds.getConnection();
			
			String sql = "SELECT qnano, qusername, qsubject, qcontent, qcategory, to_char(qDate, 'yyyymmdd hh24:mi')\n"+
						 "FROM tbl_qnaquestion_test2\n"+
						 "WHERE qnano = ? AND fk_userno = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(selectNo));
			pstmt.setString(2, userNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qqvo = new QnaQuestionVO();
				qqvo.setQnaNo(rs.getString(1));
				qqvo.setqUserno(userNo);
				qqvo.setqUserName(rs.getString(2));
				qqvo.setqSubject(rs.getString(3));
				qqvo.setqContent(rs.getString(4));
				qqvo.setqCategory(rs.getString(5));
				qqvo.setqDate(rs.getString(6));
			}
			
			
		} finally {
			close();
		}
		
		return qqvo;
	}// end of public QnaQuestionVO selectQnaPwd(String selectNo, String pwd) throws SQLException {}-----------------------------


	// 입력받은 게시물 번호로 tbl_qnaboard의 해당 답변(tbl_qnaanswer)을 조회하는 메서드
	@Override
	public QnaAnswerVO selectAnswer(String selectNo) throws SQLException {

		QnaAnswerVO qavo = null;
		
		try {
			
			conn = ds.getConnection();
			
			String sql = "SELECT fk_adminno, answer, adate "+
						 "FROM tbl_qnaanswer_test2 "+
						 "WHERE fk_qnano = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(selectNo));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				qavo = new QnaAnswerVO();
				qavo.setFk_qnaNo(selectNo);
				qavo.setqAdminid(rs.getString(1));
				qavo.setqAnswer(rs.getString(2));
				qavo.setqAdate(rs.getString(3));
			}
			
		} finally {
			close();
		}
		
		return qavo;
	}// end of public QnaAnswerVO selectAnswer(String selectNo) throws SQLException {}-------------------------


	// 입력받은 값들을 tbl_qnaquestion_test2에서 수정(update)하는 메서드 
	@Override
	public int updateQnaQuestion(QnaQuestionVO bvo) throws SQLException {

		int result = 0;
		try {
			
			conn = ds.getConnection();
			
			String sql = "UPDATE tbl_qnaquestion_test2 SET qsubject = ?, qcontent = ?, qcategory = ?\n"+
					"WHERE qnano = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bvo.getqSubject());
			pstmt.setString(2, bvo.getqContent());
			pstmt.setString(3, bvo.getqCategory());
			pstmt.setInt(4, Integer.parseInt(bvo.getQnaNo()));
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		return result;
	}// end of public int updateQnaQuestion(QnaQuestionVO bvo) throws SQLException {}--------------------------------
	
	
	// 회원이 요청한 게시물을 삭제(delete)해주는 메서드
	@Override
	public int deleteQuestion(String qnaNo) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = "DELETE FROM tbl_qnaquestion_test2\n"+
					"WHERE qnaNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(qnaNo));
			
			result = pstmt.executeUpdate();
		} finally {
			close();
		}
		
		return result;
	}// end of public int deleteQuestion(String qnaNo) throws SQLException {}--------------------
		
	
	// 관리자 번호와 답변을 가지고 답변을 등록(insert) 해주는 메서드
	@Override
	public int registerAnswer(QnaAnswerVO qavo) throws SQLException {

		int result = 0;
		
		try {
			conn = ds.getConnection();

			String sql = "INSERT INTO tbl_qnaanswer_test2(fk_qnano, fk_adminno, answer)\n"+
						 "VALUES(?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(qavo.getFk_qnaNo()));
			pstmt.setString(2, qavo.getqAdminid());
			pstmt.setString(3, qavo.getqAnswer());
			
			result = pstmt.executeUpdate();
		} finally {
			close();
		}
		
		return result;
	}// end of public int registerAnswer(QnaAnswerVO qavo) throws SQLException {}-------------------------
		
		
	// 게시물 번호와 답변 내용을 가지고 수정(update)해주는 메서드
	@Override
	public int updateAnswer(QnaAnswerVO qavo) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();

			String sql = "UPDATE tbl_qnaanswer_test2 SET fk_adminno = ?, answer = ? "
					+ " WHERE fk_qnano = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, qavo.getqAdminid());
			pstmt.setString(2, qavo.getqAnswer());
			pstmt.setInt(3, Integer.parseInt(qavo.getFk_qnaNo()));
			
			result = pstmt.executeUpdate();
		} finally {
			close();
		}
		return result;
	}// end of public int updateAnswer(QnaAnswerVO qavo) throws SQLException {|------------------------------
	
	
	// 답변 게시물 번호로 답변 게시물을 삭제(delete)하는 메서드
	@Override
	public int deleteAnswer(String qnaNo) throws SQLException {

		int result = 0;
		try {
			conn = ds.getConnection();

			String sql = "DELETE FROM tbl_qnaanswer_test2 "
					+ " WHERE fk_qnano = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(qnaNo));
			
			result = pstmt.executeUpdate();
		} finally {
			close();
		}
		return result;
	}// end of public int deleteAnswer(String qnaNo) throws SQLException {}-------------------------------
	
	
	// 페이지바를 만들기 위해서 총 페이지수 알아오기(select)
	@Override
	public int getTotalPage() throws SQLException {

		int totalPage = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = "SELECT ceil(count(*)/10) "+ // 10이 sizePerPage
					"FROM tbl_qnaquestion_test2 ";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			rs.next();
			
			totalPage = rs.getInt(1);
		} finally {
			close();
		}
		return totalPage;
	}// end of public int getTotalPage() throws SQLException {}--------------------------
	
	
	   // 답변 테이블에서 fk_qnano만 조회(select)해오는 메서드
	   @Override
	   public List<String> selectIsAnswer() throws SQLException {

	      List<String> answerList = new ArrayList<>();
	      
	      try {
	         conn = ds.getConnection();
	         
	         String sql = " select fk_qnano "+
	                   " from tbl_qnaanswer_test2";
	         
	         pstmt = conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	            answerList.add(rs.getString(1));
	         }
	         
	         
	      } finally {
	         close();
	      }
	      
	      return answerList;
	   }// end of public List<String> selectIsAnswer() throws SQLException {} ---------------------------
	   
	
}
