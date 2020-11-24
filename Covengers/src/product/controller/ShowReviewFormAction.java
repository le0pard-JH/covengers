package product.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.*;

public class ShowReviewFormAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();

		HttpSession session = request.getSession();
		// MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		/*if (loginuser != null) {
			if ("post".equalsIgnoreCase(method)) {
				
				if("관리자".equals(loginuser.getName())) {*/
					// 일단 상품 코드 받고
				//	String productcode = (String) request.getAttribute("productcode");
					
					// 주문 코드 받고
				//	String orderno = (String) request.getAttribute("orderno");
					
					InterProductDAO pdao = new ProductDAO();
					
					
					// ProductVO product = pdao.selectOneProduct(productcode);
					
					
					// 임의로 변수 설정 (디비에 들어가는지 확인용)
					String product = "test4";
					String orderno = "test1";
					String loginuser = "2011145556";
					// 변수 설정 끝
				
					
					
					request.setAttribute("product", product);
					request.setAttribute("orderno", orderno);
					request.setAttribute("loginuser", loginuser);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/review/showReview.jsp");
					
					/*
					 * } else { String message = "관리자만 접근 가능합니다."; String loc =
					 * "javascript:history.back()";
					 * 
					 * request.setAttribute("message", message); request.setAttribute("loc", loc);
					 * 
					 * super.setRedirect(false); super.setViewPage("/WEB-INF/msg.jsp"); } } else {
					 * String message = "비정상적인 경로로 접근 하셨습니다~"; String loc =
					 * "javascript:history.back()";
					 * 
					 * request.setAttribute("message", message); request.setAttribute("loc", loc);
					 * 
					 * super.setRedirect(false); super.setViewPage("/WEB-INF/msg.jsp"); }
					 * 
					 * } else { String message = "로그인이 필요합니당"; String loc =
					 * "javascript:history.back()";
					 * 
					 * request.setAttribute("message", message); request.setAttribute("loc", loc);
					 * 
					 * super.setRedirect(false); super.setViewPage("/WEB-INF/msg.jsp"); }
					 */
		

	}

}
