package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;

public class ShippingAddressDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		super.header(request);
		
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {
			
			String shipNo = request.getParameter("shipNo");
			
			//System.out.println(shipNo);
			
			InterMemberDAO mdao = new MemberDAO();
			
			boolean isDeleted = mdao.shppingAddressDelete(shipNo);
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("isDeleted", isDeleted);
			
			String json = jsonObj.toString();
			//System.out.println(json);
			
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
			
		}
		else {
			String message = "잘못된 경로로 접근하였습니다.";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
		}

	}

}
