package admin.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import admin.product.model.AdminProductDAO;
import admin.product.model.InterAdminProductDAO;
import common.controller.AbstractController;

public class KindCheckAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if ("GET".equals(method)) {

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/productRegister.jsp");

		} else {
			String kindcode = request.getParameter("kindcode");
			
			InterAdminProductDAO adao = new AdminProductDAO();
			
			String result = adao.kindCheck(kindcode);
			
			
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("result", result);
			String json = jsonObj.toString();

			// System.out.println(json);

			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}
	}

}
