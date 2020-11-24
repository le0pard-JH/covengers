package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.MemberVO;
import util.security.AES256;


public class SearchEmailAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		InterMemberDAO mdao = new MemberDAO();
		String email = mdao.searchEamil(name, password);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("email", email);
		
		String json = jsonObj.toString();
		System.out.println(json);
		request.setAttribute("json", json);
		
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
	}

}
