package admin.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.InterProductDAO;
import product.model.KindVO;
import product.model.ProductDAO;
import product.model.ProductVO;

public class ProductModifyAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String productcode = request.getParameter("productcode");
		

		// if(loginuser != null && "0".equals(loginuser.getUserno())) {

		super.getKindList(request);
		super.getCategoryList(request);
		super.getProductList(request);
		
		InterProductDAO pdao = new ProductDAO();
		
		ProductVO pvo = pdao.selectOneProduct(productcode);

		String[] pCodeList = pvo.getProductcode().split("-");
		String cateCode = pCodeList[1];
				
		KindVO kindvo = pdao.selectOneKind(cateCode);
		
		request.setAttribute("pvo", pvo);
		request.setAttribute("kindvo", kindvo);
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/productModify.jsp");
		// }
		
	}
	
	

}