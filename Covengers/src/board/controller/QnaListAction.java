package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.*;
import common.controller.AbstractController;

public class QnaListAction extends AbstractController {

   @SuppressWarnings("null")
   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

	  super.header(request);
	   
      // 페이징 처리하기
      // currentShowPageNo는 보고자 하는 페이지 번호이다.  
      String currentShowPageNo = request.getParameter("currentShowPageNo");
      
      // 만일 null이라면 터음 들어온 것이므로 "1"로 설정.
      if (currentShowPageNo == null) {
         currentShowPageNo = "1";
      }
      
      // 숫자로 형변환 시, 오류가 나면 문자 입력이거나 int형 초과 숫자입력으로 이 역시 "1"로 설정.
      try {
         Integer.parseInt(currentShowPageNo);
      }catch (Exception e) {
         currentShowPageNo = "1";
      }
      
      InterBoardDAO bdao = new BoardDAO();
      List<QnaQuestionVO> questionList = bdao.selectQnaList(currentShowPageNo);
      
      // 답변 테이블에서 fk_qnano만 조회(select)해오는 메서드
      List<String> answerList = bdao.selectIsAnswer();
      
      request.setAttribute("answerList", answerList);
      request.setAttribute("questionList", questionList);
      
      // 총 페이지 개수 알아오기
      int totalPage = bdao.getTotalPage();
      
      
      super.setRedirect(false);
      super.setViewPage("/WEB-INF/board/qnaList.jsp");
      
      String pageBar = "";
      
      int blockSize = 10;
      
      int loop = 1;
      
      int pageNo = 0;
      
      pageNo = ( (Integer.parseInt(currentShowPageNo) - 1) / blockSize ) * blockSize + 1;
      
      // [맨처음][이전] 만들기 
      if ( pageNo != 1 ) {
         pageBar += "&nbsp;<a href='qnaList.com?currentShowpage=1'>[맨처음]</a>&nbsp;";
         pageBar += "&nbsp;<a href='qnaList.com?currentShowpage="+ (pageNo-1) +"'>[이전]</a>&nbsp;";
      }

      while( !(loop > blockSize || pageNo > totalPage ) ) {
            
            if( pageNo == Integer.parseInt(currentShowPageNo) ) {
               pageBar += "&nbsp;<span style='border:solid 1px gray; color:red; padding: 2px 4px;'>"+pageNo+"</span>&nbsp;";  
            }
            else {
               pageBar += "&nbsp;<a href='qnaList.com?currentShowPageNo="+pageNo+"'>"+pageNo+"</a>&nbsp;"; 
            }
            
            loop++;   // 1 2 3 4 5 6 7 8 9 10 
                      
            pageNo++; //  1  2  3  4  5  6  7  8  9 10 
                      // 11 12 13 14 15 16 17 18 19 20 
                      // 21 
      }// end of while------------------------
      
      // **** [다음][마지막] 만들기 **** //
       // pageNo ==> 11
       if( !( pageNo > totalPage ) ) {
          pageBar += "&nbsp;<a href='qnaList.com?currentShowPageNo="+pageNo+"'>[다음]</a>&nbsp;";
          pageBar += "&nbsp;<a href='qnaList.com?currentShowPageNo="+totalPage+"'>[마지막]</a>&nbsp;";  
       }
      
       request.setAttribute("pageBar", pageBar);
      
       super.setViewPage("/WEB-INF/board/qnaList.jsp");
   }

}