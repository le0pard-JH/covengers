package product.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.*;

public class MyCartAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
/*	//	MemberVO loginuser = new MemberVO();
		loginuser.setName("권오윤");
		loginuser.setIdle(0);
		// 세션으로 로그인 되었는지 확인!!
		loginuser.setUserno("2011149316");
		session.setAttribute("loginuser", loginuser);
*/		
		// 조건 자체를 로그인이 되어있는지 안되어 있는지 확인한후 로그인이 되어있으면 로그인한 회원과 들어온 회원이 일치하는지 확인도 해야함
		// 그렇게 하고 난후 로그인 한 회원과 조회하는 회원이 일치한다면 페이지를 띄워주어야 한다.
		if (loginuser == null) {
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/myPage/myPage_emptyCart.jsp");
		}else {
			session.setAttribute("userno", loginuser.getUserno());
			
		    InterCartDAO cdao = new CartDAO();
			List<CartVO> cartList = cdao.selectCartList(loginuser.getUserno());

			request.setAttribute("cartList", cartList);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/myPage/myPage_cart.jsp");
		}
		
		
	}

}
