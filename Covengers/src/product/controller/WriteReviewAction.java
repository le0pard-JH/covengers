package product.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import product.model.InterProductDAO;
import product.model.ProductDAO;

public class WriteReviewAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();

		if ("POST".equals(method)) {
			String orderno = request.getParameter("orderno");
			String productcode = request.getParameter("productcode");
			String userno = request.getParameter("userno");
			String rcontent = request.getParameter("rcontent");
			
			rcontent = rcontent.replaceAll("<", "&lt;");
			rcontent = rcontent.replaceAll(">", "&gt;");
			rcontent = rcontent.replaceAll("\r\n", "<br>");
			
			String rgrade = request.getParameter("rgrade");
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("orderno", orderno);
			paraMap.put("productcode", productcode);
			paraMap.put("userno", userno);
			paraMap.put("rcontents", rcontent);
			paraMap.put("rgrade", rgrade);
			
			InterProductDAO pdao = new ProductDAO();
			
			int n = pdao.writeReview(paraMap);
			
			String message = "";
			
			if (n == 0) {  // 시스템문제
				message = "시스템 적으로 문제가 발생했습니다.<br>리뷰 입력에 실패하셨습니다.";
			} else if(n == 1) {  // 정상적으로 인서트
				message = "리뷰등록에 성공하셨습니다!";
			} else if (n == 2) {  // 이미 작성한 유저
				message = "이미 작성하신 상품의 리뷰입니다.<br>주문당 하나의 리뷰만 가능하세요.";
			} else {  // 외계생물
				message = "이건 절대 뜰 수 없지만 그냥넣음";
			}
			
			request.setAttribute("n", n);
			request.setAttribute("message", message);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/review/reviewResult.jsp");
			
		} else {

			String message = "비정상적인 경로로 접근 하셨습니다~";
			String loc = "javascript:history.back()";

			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}
