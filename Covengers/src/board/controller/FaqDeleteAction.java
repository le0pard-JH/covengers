package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.MemberVO;

public class FaqDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
	//	String adminno = loginuser.getUserno();
		int loginStatus = loginuser.getStatus();

		
	//	int loginStatus = 3;
	//	String adminno = "0";
		
		String method = request.getMethod();
		
		if (loginStatus != 3 || method.equalsIgnoreCase("GET")) { // 관리자가 아닌 사람이 들어왔거나 get방식으로 들어왔다면
			// faq 목록 페이지로 보낸다.
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/board/faq.com");
		}else { // 관리자가 들어오면 method에 따라 작업을 달리한다.
			
				String requestFaqNo = request.getParameter("requestFaqNo");
				
				InterBoardDAO bdao = new BoardDAO();
				
				// 요청한 faqNo를 가진 게시물을 삭제해줌 
				int n = bdao.deleteFaq(requestFaqNo);
				
				if (n == 1) {
					System.out.println("FAQ 삭제 성공");
				}
				
				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/board/faq.com");
				
		}// end of if (loginStatus != 3) {}else{}-------------------
		
	}

}
