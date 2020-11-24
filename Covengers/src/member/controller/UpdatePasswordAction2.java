package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;

public class UpdatePasswordAction2 extends AbstractController{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		
		String password = request.getParameter("password");
		String email = request.getParameter("email");

		System.out.println(email);
		System.out.println(password);
		
		InterMemberDAO mdao = new MemberDAO();
		
		int n = mdao.updatePassword(email, password);
		
		request.setAttribute("email", email);

		
		super.setViewPage("/WEB-INF/login/changePW_modal.jsp");

	}

}