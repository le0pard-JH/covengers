package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import common.controller.AbstractController;

public class DeleteQuestionAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();
		
		if (method.equalsIgnoreCase("get")) {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/board/qnaList.com");
		}else {
			String qnaNo = request.getParameter("qnaNo");
			System.out.println(qnaNo);
			
			InterBoardDAO bdao = new BoardDAO();
			// qna번호를 받아 테이블에서 해당 행을 삭제한다.
			int n = bdao.deleteQuestion(qnaNo);
			
			if (n == 1) {
				System.out.println("게시물 삭제 성공");
			}else {
				System.out.println("게시물 삭제 실패");
			}
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/board/qnaList.com");
			
		}
		
		
	}

}
