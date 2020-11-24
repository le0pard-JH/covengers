package product.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.*;

public class ProductPutCartAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		
		String message = "";
		String loc = "";
		
		if (loginuser != null ) {  // 로그인이 되어있는 상태로 장바구니에 상품을 넣을경우
			
			if ( "POST".equals(method)) {  // 버튼을 누르고 정상적으로 포스트 방식으로 들어왔을 경우
				String userno = loginuser.getUserno();
				String productcode = request.getParameter("productcode");
				String poqty = request.getParameter("poqty");
				String optioncode = request.getParameter("optioncode");
				String pprice = request.getParameter("pprice");
				
				
				Map<String, String> paraMap = new HashMap<>();
				
				paraMap.put("userno", userno);
				paraMap.put("productcode", productcode);
				paraMap.put("poqty", poqty);
				paraMap.put("optioncode", optioncode);
				paraMap.put("pprice", pprice);
				
				InterProductDAO pdao = new ProductDAO();
				int n = pdao.insertIntoCart(paraMap);
				
				// request.setAttribute("paraMap", paraMap);
				session.setAttribute("productcode", productcode);
				
				if (n== 1) {  // 인서트든 업데이트든 성공을 했을경우 
					
					super.setRedirect(true);
					super.setViewPage(request.getContextPath() + "/product/productDetail.com");
				} else {  // 인서트 혹은 업데이트에 실패했을 경우
					message = "문제발생으로 인해 장바구니 등록에 실패했어요";
					loc="javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
				}
				
			} else {  // 유저가 장난을 치면서 겟방식으로 들어왔을 경우 메인페이지로 이동시켜 준다.
				super.setRedirect(true);
				super.setViewPage(request.getContextPath() + "/");
			}
		} else {  // 로그인이 되어있지 않은 상태에서 장바구니에 상품을 넣을경우
			// 로그인이 필요합니다라는 말을 띄운후 이전페이지로 돌려보내준다.
			message = "로그인이 필요합니다.";
			loc="javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	} 

}
