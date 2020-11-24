package admin.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import product.model.InterProductDAO;
import product.model.ProductDAO;
import product.model.ProductVO;

public class MainProductRegisterAction2 extends AbstractController{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
		String productCode = request.getParameter("productCode");
		
		InterProductDAO pdao = new ProductDAO();
		ProductVO pvo = pdao.selectOneProduct(productCode);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("pvo", pvo);
		
		String json = jsonObj.toString();

		request.setAttribute("json", json);
		request.setAttribute("pvo", pvo);
		
		
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
		
		
		
	}

}
