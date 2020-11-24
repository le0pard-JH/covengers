package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.MemberVO;

public class DeleteAnswerAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


		/*		
 			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		*/
		String method = request.getMethod();
		
		if (method.equalsIgnoreCase("get")) {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/board/qnaList.com");
		}else {
			
		
			String qnaNo = request.getParameter("qnaNo");
			
			InterBoardDAO bdao = new BoardDAO();
			int n = bdao.deleteAnswer(qnaNo);
			
			if (n == 1) {
				System.out.println("답변 삭제 성공");
			}else {
				System.out.println("답변 삭제 실패");
			}
		}
		
		
	}

}
