package board.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.*;
import common.controller.AbstractController;
import member.model.MemberVO;

public class QnaDetailAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");

		
	//	String loginuser = "1234";
	//	String status = "2";
		
		if (loginuser == null) { // 로그인 하지 않은 회원이라면 
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/board/qnaList.com");
		}else { // 로그인한 회원이라면
			String userno = loginuser.getUserno();
			int status = loginuser.getStatus();
		//	String userno = "2011189295";
		//	String userno = "sdf5";
			
			String selectNo = request.getParameter("selectNo");
			
			InterBoardDAO bdao = new BoardDAO();
			QnaQuestionVO qqvo = bdao.selectQnaDetail(selectNo, userno);
			if (qqvo == null && status != 3) { // 만일 열람하는 회원이 해당 게시물을 올린 회원이 아니면서 관리자도 아니라면
				
				String message = "본인의 문의만 열람할 수 있습니다.";
				String loc = "javascript:history.back();";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}else{// 만일 열람하는 회원이 게시물을 올린 회원이라면
				request.setAttribute("status", status);
				
				List<String> cateList = bdao.selectQnaCategory();
				request.setAttribute("cateList", cateList);
				request.setAttribute("qqvo", qqvo);
				
				QnaAnswerVO qavo = bdao.selectAnswer(selectNo); // 해당 작성번호로 답변을 조회한다.
				request.setAttribute("qavo", qavo);
				
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/board/qnaDetail.jsp");
				
			}
		}

		
		
		
		
		
	}	
		
}
