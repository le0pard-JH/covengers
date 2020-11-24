package product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import product.model.InterProductDAO;
import product.model.ProductDAO;
import product.model.ProductVO;

public class ModifyProductAction extends AbstractController{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String productcode = request.getParameter("productcode");
		
		InterProductDAO pdao = new ProductDAO();
		ProductVO pvo = pdao.selectOneProduct(productcode);
		
		request.setAttribute("pvo", pvo);
		super.setViewPage("/WEB-INF/admin/productmodify.jsp");

		
	}

}
