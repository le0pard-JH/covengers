package product.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import product.model.CartDAO;
import product.model.CartVO;
import product.model.InterCartDAO;

public class ShowUserCartAction extends AbstractController  {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		super.header(request);
		
		String userno = request.getParameter("userno");
		
		InterCartDAO cdao = new CartDAO();
		List<CartVO> cartList = cdao.selectCartList(userno);

		request.setAttribute("cartList", cartList);
		

		super.setRedirect(false);
		super.setViewPage("/WEB-INF/myPage/myPage_cart.jsp");
		
	}

}
