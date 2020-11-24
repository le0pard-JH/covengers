package product.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.InterProductDAO;
import product.model.ProductDAO;
import product.model.ProductVO;

public class ProductDisplayAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		super.header(request);
		
		String method = request.getMethod();
		
		String category = request.getParameter("categorycode") == null ? "101": request.getParameter("categorycode");
		
		InterProductDAO pdao = new ProductDAO();
		
		List<ProductVO> productList = pdao.selectProductList(category);
		
		boolean viewBool = false;
		String viewPage = "";
		super.goBackURL(request);
		
		if (productList.size() == 0) {// 해당하는 카테고리가 없는 경우
			// 메인페이지로 페이지 이동을 시켜준다.
			String message = "검색하신 제품은 존재하지 않습니다.";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
		//	super.setRedirect(false);
			viewPage ="/WEB-INF/msg.jsp";
			
		} else {  // 해당하는 카테고리가 있는 경우
			// 해당하는 카테고리의 뷰페이지로 이동시켜준다.
			
			ProductVO product = productList.get(0);
			
			
			request.setAttribute("productList", productList);
			request.setAttribute("product", product);
			
			viewPage = "/WEB-INF/product/productDisplay.jsp";
		}
		
		super.setRedirect(viewBool);
		super.setViewPage(viewPage);
	}
	
}
