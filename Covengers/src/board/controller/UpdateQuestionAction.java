package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;

public class UpdateQuestionAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);
		
		String method = request.getMethod();
		
		if (method.equalsIgnoreCase("get")) {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/board/qnaList.com");
		}else {
			String qnaNo = request.getParameter("qnaNo");
			String subject = request.getParameter("subject");
			String category = request.getParameter("category");
			String content = request.getParameter("content");
			
			QnaQuestionVO bvo = new QnaQuestionVO();
			bvo.setQnaNo(qnaNo);
			bvo.setqSubject(subject);
			bvo.setqCategory(category);
			bvo.setqContent(content);
			
			InterBoardDAO bdao = new BoardDAO();
			// 입력받은 값들을 tbl_qnaquestion_test2에서 update 해준다.
			int n = bdao.updateQnaQuestion(bvo);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/board/qnaDetail.jsp");
		}
		
	}

}
