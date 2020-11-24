package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.CartDAO;
import product.model.InterCartDAO;

public class MyCartDeleteOneAction extends AbstractController{

   @Override
   public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      HttpSession session = request.getSession();
      MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
      
      String method = request.getMethod();
      
      if ( loginuser == null || method.equalsIgnoreCase("GET") ) {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/product/myCart.com");
	  }else {
	      
	      String cartno = request.getParameter("cartno");
	      
	      InterCartDAO cdao = new CartDAO();
	      int n = cdao.deleteOne(loginuser.getUserno(), cartno);
	      
	      if (n == 0) System.out.println("삭제 실패");
	      else System.out.println("삭제 성공");
	      
	      super.setRedirect(false);
	      super.setViewPage("/WEB-INF/MyPage/myPage_cart.jsp");
	      
	  }
   
   }
   
   

}