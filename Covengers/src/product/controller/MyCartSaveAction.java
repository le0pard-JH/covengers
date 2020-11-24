package product.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.*;

public class MyCartSaveAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		String method = request.getMethod();
		String ctxPath = request.getContextPath();
		
		if ( loginuser == null || method.equalsIgnoreCase("GET") ) {
			// "GET" 방식으로 들어오면 장바구니 페이지로 다시 보낸다.
			super.setRedirect(true);
			super.setViewPage(ctxPath + "/product/myCart.com");
		}else {
			// "POST" 방식으로 들어오면 form에서 입력되어있던 모든 정보를 데이터 베이스에 저장한다.
			
			String cartno = request.getParameter("cartno");
			int poqty = Integer.parseInt(request.getParameter("poqty"));
		
			InterCartDAO cdao = new CartDAO();
		
			int n = cdao.updateCart(poqty, cartno);
			
			if (n == 0) System.out.println("저장 실패");
		    else System.out.println("저장 성공");		
		}
		
		
	}

}
