package product.controller;

import java.util.*;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;

import product.model.*;

public class ProductDetailAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		super.header(request);
		
		HttpSession session = request.getSession();
		
		String method = request.getMethod();
		
		String productcode = "";
		
		if ((String)session.getAttribute("productcode") == null) {
			productcode = request.getParameter("productcode");
		}else {
			productcode = (String)session.getAttribute("productcode");
			String check = "1";
			request.setAttribute("check", check);
			
		}
		
		InterProductDAO pdao = new ProductDAO();
		ProductVO product = pdao.selectOneProduct(productcode);
		

		if ((String)session.getAttribute("productcode") != null) {
			session.removeAttribute("productcode");
		}
		
		String viewPage = "";
		boolean viewBool = false;
		
		if (product == null) {  // 가지고 올 상품의 번호가 데이터 베이스에 없는 경우
			// 조말론 홈페이지에선 이런방식으로 접근했을 경우
			// 메인페이지로 가게끔 설정이 되어있음
			// 상품의 디피창으로 넘어가도록 한다.
			
			viewBool = true;
			viewPage = request.getContextPath() + "/product/productDisplay.com";
			
		} else {  // 상품이 데이터베이스 내부에서 조회가 되었을 경우
			
			// 리뷰 테이블에서 리뷰정보를 가져와야 함!!
			
			List<Map<String, String>> reviewList = pdao.getProductReviewList(product);
			
			List<OptionVO> optionList = pdao.selectedProductOption(product);
			
			productcode = product.getProductcode();
			String categorycode = product.getFk_categorycode();
			
			Map<String, String> paraMap = new HashMap<>();
			
			paraMap.put("productcode", productcode);
			paraMap.put("categorycode", categorycode);
			
			
			List<ProductVO> productList = pdao.selectOtherProduct(paraMap);
			
			
			
			request.setAttribute("product", product);
			request.setAttribute("optionList", optionList);
			request.setAttribute("optionListSize", optionList.size());	
			request.setAttribute("productList", productList);
			request.setAttribute("reviewList", reviewList);
			
			// 리스트 사이즈가 0일때 테스트용
			//request.setAttribute("optionListSize", 0);				
			
			
			
			viewPage = "/WEB-INF/product/productDetail.jsp";

			
		}
		
		
		super.setRedirect(viewBool);
		super.setViewPage(viewPage);
	}

}
