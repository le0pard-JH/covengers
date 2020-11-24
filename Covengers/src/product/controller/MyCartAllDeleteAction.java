package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.*;

public class MyCartAllDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		if (loginuser.getUserno() == null) {
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/main.com");
			
		}else {
			
			String userno = loginuser.getUserno();
		//	System.out.println("1 : "+userno);
			
			InterCartDAO cdao = new CartDAO();
			int n = cdao.cartDeleteAll(userno);
			if (n == 0) {
				String message = "전체삭제에 실패하였습니다.";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setViewPage("/WEB-INF/msg.jsp");
			}else {
				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/product/myCart.com");
			}
			
			
		}
		
		
	}

}
