package admin.product.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import admin.product.model.AdminProductDAO;
import admin.product.model.InterAdminProductDAO;
import common.controller.AbstractController;

public class GetOptioncodeAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		
		
		if ("GET".equals(method)) {

			super.setRedirect(false);
			super.setViewPage("/WEB-INF/admin/productRegister.jsp");

		} else {
			String selectedProductcode = request.getParameter("selectedProductcode");
			
			InterAdminProductDAO adao = new AdminProductDAO();
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("selectedProductcode", selectedProductcode);
			
			JSONObject jsonObj = new JSONObject();
			String result = adao.getOptioncodeAction(paraMap);
			
			if ( result == null ) {  // 옵션에 대한 선택값이 아무것도 나오지 않았을 경우
										// 첫번째 옵션을 등록 할 수 있도록 해준다.
				result = selectedProductcode + "-S01";
			} else {
				String temp = result.substring(12);  // PF-104-002-S01 에서  PF-104-002-S 까지 잘라준다.
				try {
					
					int a = Integer.parseInt(temp) + 1;
					String b = String.valueOf(a);
					
					if (b.length() == 1) {  // 한자리수
						temp = "0" + b;					
					} else if(b.length() == 2) {  // 두자리수
						temp = b;
					} else {  // 세자리수 이상
						// 세자리수 이상이면 어떻게 처리해야할지 생각해보자!
						temp = "00";
					}
					
					result = result.substring(0, 12) + temp;
				} catch (Exception e) {
					result = "";
				}
				
				
			}
			
			jsonObj.put("result", result);
			String json = jsonObj.toString();
			request.setAttribute("json", json);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/jsonview.jsp");
		}
	}

}
