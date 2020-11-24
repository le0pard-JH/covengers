package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.ShippingAddressVO;
import my.util.MyUtil;

public class ShippingAddressUpdateEndAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			String shipNo = request.getParameter("shipNo");
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
			
			if(status == null) {
				status = "0";
			}
			
			
			ShippingAddressVO svo = new ShippingAddressVO(shipNo, userno, receiverName, siteName, postcode, address, detailAddress, extraAddress, mobile, deliveryRequest, status);
			
			InterMemberDAO mdao = new MemberDAO(); 
			int n = mdao.updateShippingAddress(svo);
			String message = "";
			String loc = "";
			
			if(n==1) {
			
				message = "회원정보 수정 성공!!";
				loc = "javascript:opener.location.reload(), self.close()";
				
			}
			else {
				message = "회원정보 수정 실패";
				loc = "javascript:history.back()";
			}
			
			    request.setAttribute("message", message);
		        request.setAttribute("loc", loc);
		        
		        super.setViewPage("/WEB-INF/msg.jsp");
			//System.out.println("확인용 : " +name);
			
		}
			
	}

}
