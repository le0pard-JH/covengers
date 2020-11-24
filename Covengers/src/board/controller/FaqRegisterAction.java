package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.MemberVO;

public class FaqRegisterAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);
		
	    HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	
		String adminno = loginuser.getUserno();
		int loginStatus = loginuser.getStatus();

	//	int loginStatus = 3;
	//	String adminno = "0";
		
		String method = request.getMethod();
		
		if (loginStatus != 3) { // 관리자가 아닌 사람이 들어왔다면
			// faq 목록 페이지로 보낸다.
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/board/faq.com");
		}else { // 관리자가 들어오면 method에 따라 작업을 달리한다.
			
			
			if (method.equalsIgnoreCase("GET")) { // faq.jsp에서 "등록" 버튼을 누르고 처음 들어왔다면
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/board/faqRegister.jsp");
			}else { // form을 작성하고 "POST"방식으로 다시 들어왔다면
				
				String answer = request.getParameter("answer");
				answer = answer.replaceAll("\n", "<br>");
				
				FaqVO fvo = new FaqVO();
				fvo.setFaqQuestion(request.getParameter("question"));
				fvo.setFaqAnswer(answer);
				fvo.setFaqAdminid(adminno);
				
				InterBoardDAO bdao = new BoardDAO();
				int n = bdao.registerFaq(fvo);
				
				if (n == 1) {
					System.out.println("FAQ 등록 성공");
				}
				
				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/board/faq.com");
				
			}
		}// end of if (loginStatus != 3) {}else{}-------------------
		
		
	}

}
