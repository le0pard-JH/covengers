package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.MemberVO;
import member.model.ShippingAddressVO;

public class ShippingAddressUpdateAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();
		
		if(!"POST".equalsIgnoreCase(method)) { // get으로 들어왔을 때
		
			// 정보를 수정하기 위한 전제조건 : 로그인이 되어있어야함
			if(super.checkLogin(request)) {
				// 로그인이 되었으면
				
				String shipNo = request.getParameter("shipNo");
				String userno = request.getParameter("userno");
				
				HttpSession session = request.getSession();
				MemberVO loginuser = (MemberVO) session.getAttribute("loginuser"); 
				
				if(loginuser.getUserno().equals(userno)) { //로그인한 자신의 회원번호와 넘어온 회원번호 값이 동일한지 확인
					// 로그인한 사용자가 자신의 배송지를 수정하는 경우
				
					InterMemberDAO mdao = new MemberDAO();
					ShippingAddressVO svo = mdao.selectOneAddress(shipNo);
					
					request.setAttribute("svo", svo);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/myPage/shippingAddressUpdate.jsp");
				
				}
				else {
					//로그인한 사용자가 다른사용자의 정보를 수정하려고 시도하는 경우
					String message = "다른사용자의 배송지정보수정 시도는 불가합니다.";
					String loc = "javascript:history.back()";
					
					request.setAttribute("message", message);
					request.setAttribute("loc", loc);
					
					//super.setRedirect(false);
					super.setViewPage("/WEB-INF/msg.jsp");
					return;
				} //end of if(loginuser.getUserno().equals(userno)) {}---------------
				
			} 
			else {
				// 로그인을 안 했으면
				String message = "배송지정보를 수정하기 위해서는 먼저 로그인을 하세요!!!";
				String loc = "javascript:history.back()";
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				//super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				
			} // end of if(super.checkLogin(request)) {}--------------------------
		}
		
		
	}
}
