package admin.product.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import admin.product.model.*;
import common.controller.AbstractController;

public class InsertKindJSONAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String method = request.getMethod();

		if ("GET".equals(method)) {

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/productRegister.jsp");

		} else {
			String kindcode = request.getParameter("kindcode");
			String enkindname = request.getParameter("enkindname");
			String krkindname = request.getParameter("krkindname");

			InterAdminProductDAO adao = new AdminProductDAO();

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("kindcode", kindcode);
			paraMap.put("enkindname", enkindname);
			paraMap.put("krkindname", krkindname);

			int result = adao.insertProductKind(paraMap);
			
			
			
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
