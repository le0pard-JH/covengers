package board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import board.model.QnaQuestionVO;
import common.controller.AbstractController;
import member.model.MemberVO;

public class QnaRegisterAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
	//	String loginuser = "2011189295";
	//	String name = "신호연";
		
		// 로그인 하지 않은 유저라면
		if (loginuser == null) {
			String message = "로그인이 필요합니다!!";
			String loc = request.getContextPath()+"/board/qnaList.com";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setViewPage("/WEB-INF/msg.jsp");
		}else { 
		// 로그인한 유저라면	
			String method = request.getMethod();
			
	//		String userno = "2011189295";
			String userno = loginuser.getUserno();
			String name = loginuser.getName();
			
			if (method.equalsIgnoreCase("GET")) { // GET 방식으로 들어왔을 때
				
				InterBoardDAO bdao = new BoardDAO();
				// QnA 카테고리 컬럼을 조회해오는 메서드
				List<String> cateList = bdao.selectQnaCategory();
				
				request.setAttribute("userno", userno);
				request.setAttribute("name", name);
				request.setAttribute("cateList", cateList);
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/board/qnaRegister.jsp");
			}else { // POST 방식으로 다시 한번 들어왔을 때

				userno = request.getParameter("userno");
				name = request.getParameter("username");
				String subject = request.getParameter("subject");
				String category = request.getParameter("category");
				String content = request.getParameter("content");
				
				// 만일 기본 제공했던 설명문을 지우지 않고 넘어왔다면 설명문 부분만 없애고 저장해줌.
		//		content = content.substring(content.indexOf("1."));
				
				QnaQuestionVO qqvo = new QnaQuestionVO();
				qqvo.setqUserno(userno);
				qqvo.setqUserName(name);
				qqvo.setqSubject(subject);
				qqvo.setqCategory(category);
				qqvo.setqContent(content);

				InterBoardDAO bdao = new BoardDAO();
				int n = bdao.registerQuestion(qqvo);
				
				String message = "";
				String loc = "";
				if (n == 1) {
					System.out.println("QnA 등록 성공");
					message = "게시물이 정상적으로 등록되었습니다.";
					loc = request.getContextPath() + "/board/qnaList.com";
				}else {
					System.out.println("QnA 등록 실패");
					message = "게시물이 등록되지 않았습니다.";
					loc = "javascript:history.back();";
				}
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
			}
			
		}
		
		
		
		
	}

}
