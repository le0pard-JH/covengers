package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;

public class UpdatePasswordAction extends AbstractController{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		System.out.println("컨트롤러 성공");
		
		String email = request.getParameter("email2");
		String name = request.getParameter("name1");
		System.out.println(email);

		
		
		InterMemberDAO mdao = new MemberDAO();
		int n = mdao.updatePassword(email, name);
		
		request.setAttribute("email", email);
		request.setAttribute("name", name);
		
		
		super.setViewPage("/WEB-INF/login/passwordFind.jsp");
		
	}

}
