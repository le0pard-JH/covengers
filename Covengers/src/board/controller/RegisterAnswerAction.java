package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.*;

public class RegisterAnswerAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String method = request.getMethod();
		
		if (method.equalsIgnoreCase("get")) {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/board/qnaList.com");
		}else {
			
			String userno = loginuser.getUserno();
		//	String userno = "0";
		
			String qnaNo = request.getParameter("qnaNo");
			String answer = request.getParameter("answer");
			
			QnaAnswerVO qavo = new QnaAnswerVO();
			qavo.setFk_qnaNo(qnaNo);
			qavo.setqAnswer(answer);
			qavo.setqAdminid(userno);
			
			InterBoardDAO bdao = new BoardDAO();
			int n = bdao.registerAnswer(qavo);
			
			if (n == 1) {
				System.out.println("답변 등록 성공");
			}else {
				System.out.println("답변 등록 실패");
			}
			
		
		}

	}

}
