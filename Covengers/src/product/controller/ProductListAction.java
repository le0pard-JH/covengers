package product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import product.model.InterProductDAO;
import product.model.ProductDAO;
import product.model.ProductVO;

public class ProductListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		InterProductDAO pdao = new ProductDAO();
		List<ProductVO> pList = pdao.selectAll();
		
		// System.out.println(pList.size());
		request.setAttribute("pList", pList);
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/producttables.jsp");
		
	}

}
