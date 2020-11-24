package admin.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import admin.product.model.AdminProductDAO;
import admin.product.model.InterAdminProductDAO;
import common.controller.AbstractController;

public class InsertOptionJSONAction extends AbstractController {
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if ("GET".equals(method)) {

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/productRegister.jsp");

		} else {
			String optioncode = request.getParameter("optioncode");
			String fk_productcode = request.getParameter("fk_productcode");
			String optionname = request.getParameter("optionname");
			String addprice = request.getParameter("addprice");
			String qty = request.getParameter("qty");
			
			
			
			InterAdminProductDAO adao = new AdminProductDAO();

			Map<String, String> paraMap = new HashMap<>();
			
			paraMap.put("optioncode", optioncode);
			paraMap.put("fk_productcode", fk_productcode);
			paraMap.put("optionname", optionname);
			paraMap.put("addprice", addprice);
			paraMap.put("qty", qty);

			int result = adao.insertProductOption(paraMap);
			
			
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
