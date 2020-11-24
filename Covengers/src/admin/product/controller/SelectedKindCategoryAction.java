package admin.product.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import admin.product.model.AdminProductDAO;
import admin.product.model.InterAdminProductDAO;
import common.controller.AbstractController;
import product.model.CategoryVO;

public class SelectedKindCategoryAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();

		if ("GET".equals(method)) {

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/productRegister.jsp");

		} else {
			// 상품 카테고리 등록 창에서 상품 종류를 선택할때 사용
			String selectedKindOption = request.getParameter("selectedKindOption");
			
			// 상품 등록 창에서 상품 카테고리를 선택할 경우
			String selectedCategoryOption = request.getParameter("selectedCategoryOption");
			
			
			InterAdminProductDAO adao = new AdminProductDAO();

			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("selectedKindOption", selectedKindOption);
			paraMap.put("selectedCategoryOption", selectedCategoryOption);
			
			
			JSONObject jsonObj = new JSONObject();
			
			// 상품 카테고리 등록에서 옵션을 선택한 경우 
			// 카테고리에 등록되어진 마지막 번호의 상품을 가져오기
			if (paraMap.get("selectedCategoryOption") == null) {
				String result = adao.getLastCategorycode(paraMap);
				jsonObj.put("result", result);
				String json = jsonObj.toString();

				// System.out.println(json);
				// System.out.println("카테고리 선택 " + json);
				request.setAttribute("json", json);
				 		
			} 
			// 상품 등록에서 옵션을 선택한 경우
			else if (paraMap.get("selectedKindOption") == null) {
				String result = adao.getLastCategorycode(paraMap);
		
				if (result == null) {
					result = adao.getKindCategorycode(paraMap);
				}
				
				jsonObj.put("result", result);
				String json = jsonObj.toString();

				// System.out.println(json);

				// System.out.println("상품 선택 " + json);
				request.setAttribute("json", json);
			}
			
			
			
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}
	}

}
