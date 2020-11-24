package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.MemberVO;

public class FaqUpdateAction extends AbstractController {

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
			
			InterBoardDAO bdao = new BoardDAO();
			
			if (method.equalsIgnoreCase("GET")) { // faq.jsp에서 "수정" 버튼을 누르고 처음 들어왔다면
				String requestFaqNo = request.getParameter("requestFaqNo");
				FaqVO fvo = bdao.selectOneFaq(requestFaqNo);
				
				// <br>로 저장되어있는 문자는 모두 \n으로 변경해준다.
				String answer = fvo.getFaqAnswer();
				answer.replaceAll("<br>", "\n");
				
				fvo.setFaqAnswer(answer);
				
				request.setAttribute("fvo", fvo);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/board/faqRegister.jsp");
			}else { // form을 작성하고 "POST"방식으로 다시 들어왔다면
				
			//	System.out.println("faqNo 확인용 : " + request.getParameter("faqNo"));
				
				// \n 줄개행 문자를 모두 <br>로 변경해준다.
				String answer = request.getParameter("answer");
				answer = answer.replaceAll("\n", "<br>");
				
				FaqVO fvo = new FaqVO();
				fvo.setFaqNo( Integer.parseInt(request.getParameter("faqNo")) );
				fvo.setFaqQuestion(request.getParameter("question"));
				fvo.setFaqAnswer(answer);
				fvo.setFaqAdminid(adminno);
				
				int n = bdao.updateFaq(fvo);
				
				if (n == 1) {
					System.out.println("FAQ 수정 성공");
				}
				
				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/board/faq.com");
				
			}
		}// end of if (loginStatus != 3) {}else{}-------------------
		
	}

}
