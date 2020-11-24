package member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;

public class PointUpdateAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String Rcode = request.getParameter("Rcode");
		
		System.out.println(">>> 확인용 Rcode : " +Rcode);
		
		InterMemberDAO mdao = new MemberDAO();
		int updaterow = mdao.pointUpdate(Rcode); // 업데이트 완료되면 1반환(업데이트된 행 개수)
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("updaterow", updaterow);
		
		System.out.println(updaterow);
		
		String json = jsonObj.toString();
		
		request.setAttribute("json", json); // {"updaterow":1} 
		System.out.println("PointUpdateAction json : " + json);
		
		//super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
