package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.*;

public class ReviewRegisterOneAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		// 로그인 하지 않은 채로 들어왔을 때,
		if (loginuser == null) {
			String message = "로그인이 필요합니다.";
			String loc = "javascript:history.back();";

			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}else {
		// 로그인 후 들어왔을 때,
			String userno = loginuser.getUserno();
			
			InterBoardDAO bdao = new BoardDAO();
		//	List<> = bdao.selectPerchaseList(userno);
			
			
		}
		
		
	}

}
