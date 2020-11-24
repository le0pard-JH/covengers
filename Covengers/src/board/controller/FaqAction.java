package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.MemberVO;

public class FaqAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		super.header(request);
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	//	MemberVO loginuser = (MemberVO)session.setAttribute("loginuser", loginuser);
		
		int loginStatus = loginuser.getStatus();
		
//		int loginStatus = 3;
		InterBoardDAO bdao = new BoardDAO();
		List<FaqVO> faqList = bdao.selectFaqList();
		
		request.setAttribute("faqList", faqList);
		request.setAttribute("loginStatus", loginStatus);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/board/faq.jsp");
		
	}

}
