package admin.product.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;
import product.model.InterProductDAO;
import product.model.ProductDAO;

public class UpdateProductAction extends AbstractController{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String kindcodeInProduct = request.getParameter("selectedKindcodeInProduct");
		String enproductname = request.getParameter("enproductname");
		String krproductname = request.getParameter("krproductname");
		String price = request.getParameter("price");
		String saleprice = request.getParameter("saleprice");
		String origin = request.getParameter("origin");
		String productdescshort = request.getParameter("productdescshort");
		String manufacturedate = request.getParameter("manufacturedate");
		String expiredate = request.getParameter("expiredate");
		String productdesc1 = request.getParameter("productdesc1");
		String productdesc2 = request.getParameter("productdesc2");
		String ingredient = request.getParameter("ingredient");
		String precautions = request.getParameter("precautions");

		
		
		
	}
	
	
}