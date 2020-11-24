package covengers.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import admin.management.model.InterManagementDAO;
import admin.management.model.ManagementDAO;
import common.controller.AbstractController;
import member.model.MemberVO;

public class AdminAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

		String message = "";
		String loc = "";

		// if (loginuser != null) { // 로그인 한 유저가 있을 경우
		// if ("관리자".equals(loginuser.getName())) { // 로그인 한 회원의 이름이 관리자인 경우

		InterManagementDAO mdao = new ManagementDAO();

		Map<String, String> managementInfo = mdao.getManagementInfo(); // 관리자가 필요한 모든 정보를 다 불러온다.

		List<Map<String, String>> infoList = mdao.getListManagementInfo();

		request.setAttribute("managementInfo", managementInfo);
		request.setAttribute("infoList", infoList);
		/*
		 * } else { // 관리자로 로그인 하지 않았을 경우 message = "관리자 로그인이 필요합니당"; loc =
		 * "javascript:history.back()";
		 * 
		 * request.setAttribute("message", message); request.setAttribute("loc", loc);
		 * 
		 * super.setRedirect(false); super.setViewPage("/WEB-INF/msg.jsp");
		 * 
		 * }
		 * 
		 * } else { // 로그인 한 유저가 없을 경우 message = "로그인부터 하고 와..."; loc =
		 * "javascript:history.back()";
		 * 
		 * request.setAttribute("message", message); request.setAttribute("loc", loc);
		 * 
		 * super.setRedirect(false); super.setViewPage("/WEB-INF/msg.jsp"); }
		 */

		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/covengers_admin.jsp");

	}

}
