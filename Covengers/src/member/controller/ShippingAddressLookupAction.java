package member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.MemberVO;
import member.model.ShippingAddressVO;

public class ShippingAddressLookupAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);
		
		// == 로그인 했을때만 조회가능 == //
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null) { //로그인이 돼있을 경우
			
			InterMemberDAO mdao = new MemberDAO();
			
			String userno = loginuser.getUserno();
			
			int numberOfShippingAddress = mdao.numberOfShippingAddress(loginuser.getUserno());
			
			List<ShippingAddressVO> addressList = mdao.selectAddressList(userno);
			
			request.setAttribute("addressList", addressList);
			request.setAttribute("numberOfShippingAddress", numberOfShippingAddress);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/myPage/shippingAddressLookup.jsp");
		}
		else { //로그인을 안 한 경우
			
			super.goBackURL(request);
			
			String message = "로그인 후 이용해주세요.";
			String loc = "/Covengers/main.com"; //메인이 아니라 모달창을 띄워줘야될 거 같은데 어떻게하지??
			
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}
	}

}
