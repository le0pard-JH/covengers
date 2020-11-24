package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.ShippingAddressVO;
import my.util.MyUtil;

public class ShippingAddressAddAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);
		
		String method = request.getMethod(); // 전송되어온 메소드가 뭔지 받아옴
		
		if("GET".equals(method)) {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/myPage/shippingAddressAdd.jsp");
			
		} else {
			
			String userno = request.getParameter("userno");
			String receiverName = request.getParameter("receiverName");
			String siteName = request.getParameter("siteName");
			String postcode = request.getParameter("postcode");
			String address = request.getParameter("address");
			String detailAddress = request.getParameter("detailAddress");
			String extraAddress = request.getParameter("extraAddress");
			String mobile = request.getParameter("mobile");
			String deliveryRequest = request.getParameter("deliveryRequest");
			String status = request.getParameter("status");
			
			receiverName = MyUtil.secureCode(receiverName);
			siteName = MyUtil.secureCode(siteName);
			postcode = MyUtil.secureCode(postcode);
			address = MyUtil.secureCode(address);
			detailAddress = MyUtil.secureCode(detailAddress);
			extraAddress = MyUtil.secureCode(extraAddress);
			deliveryRequest = MyUtil.secureCode(deliveryRequest);
			
			if(!"1".equals(status)) {
				status = "0";
			}
			
			ShippingAddressVO ship = new ShippingAddressVO(userno, receiverName, siteName, postcode, address, detailAddress, extraAddress, mobile, deliveryRequest, status);
			
			//System.out.println(email, receiverName, siteName, postcode, address, detailAddress, extraAddress, mobile, deliveryRequest, status);
			
			InterMemberDAO mdao = new MemberDAO();
			int n = mdao.addShippingAddress(ship);
			
			String message = "";
			String loc = "";
			
			if(n == 1) {
				message = "배송지 등록 성공";
				loc = "javascript:opener.location.reload(), self.close()"; // 시작페이지
			}
			else {
				message = "배송지 등록 실패";
				loc = "javascript:history.back()"; // 자바스크립트를 이용한 이전페이지로 이동
			}
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			// super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
		}

	}

}
