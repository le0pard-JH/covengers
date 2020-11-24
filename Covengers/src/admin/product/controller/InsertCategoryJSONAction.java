package admin.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import admin.product.model.AdminProductDAO;
import admin.product.model.InterAdminProductDAO;
import common.controller.AbstractController;

public class InsertCategoryJSONAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();

		if ("GET".equals(method)) {

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/productRegister.jsp");

		} else {
			
			String categorycode = request.getParameter("categorycode");
			String fk_kindcode = request.getParameter("fk_kindcode");
			String encategoryname = request.getParameter("encategoryname");
			String krcategoryname = request.getParameter("krcategoryname");
			
			
			
			InterAdminProductDAO adao = new AdminProductDAO();

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("categorycode", categorycode);
			paraMap.put("fk_kindcode", fk_kindcode);
			paraMap.put("encategoryname", encategoryname);
			paraMap.put("krcategoryname", krcategoryname);

			int result = adao.insertProductCategory(paraMap);
			
			
			
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
