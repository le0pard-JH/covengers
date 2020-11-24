package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;

public class SearchUserAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		InterMemberDAO mdao = new MemberDAO();
		String n = mdao.searchUser(name, email);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("n", n);
		
		String json = jsonObj.toString();
		System.out.println(json);
		request.setAttribute("json", json);
		
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
	}

}
