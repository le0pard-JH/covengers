package common.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import admin.product.model.AdminProductDAO;
import admin.product.model.InterAdminProductDAO;
import member.model.MemberVO;
import my.util.MyUtil;
import product.model.CategoryVO;
import product.model.EachGoodsVO;
import product.model.InterProductDAO;
import product.model.KindVO;
import product.model.OptionVO;
import product.model.ProductDAO;
import product.model.ProductVO;

public abstract class AbstractController implements InterCommand {
	InterAdminProductDAO adao = new AdminProductDAO();
	/*
	 * === 다음의 나오는 것은 우리끼리한 약속이다. ===
	 * 
	 * ※ view 단 페이지(.jsp)로 이동시 forward 방법(dispatcher)으로 이동시키고자 한다라면 자식클래스에서는 부모클래스에서
	 * 생성해둔 메소드 호출시 아래와 같이 하면 되게끔 한다.
	 * 
	 * super.setRedirect(false); super.setViewPage("/WEB-INF/index.jsp");
	 * 
	 * 
	 * ※ URL 주소를 변경하여 페이지 이동시키고자 한다라면 즉, sendRedirect 를 하고자 한다라면 자식클래스에서는 부모클래스에서
	 * 생성해둔 메소드 호출시 아래와 같이 하면 되게끔 한다.
	 * 
	 * super.setRedirect(true); super.setViewPage("registerMember.up");
	 */

	private boolean isRedirect = false;
	// isRedirect 의 값이 false 이라면 view 단 페이지 (.jsp) 로 foward
	// true 라면 sendRedirect 로 전송

	private String viewPage;
	// viewPage 는 isRedirect 의 경로 이다.
	// true 시에만 작동

	public boolean isRedirect() {
		return isRedirect;
	}

	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}

	public String getViewPage() {
		return viewPage;
	}

	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void getKindList(HttpServletRequest request) throws SQLException {
		List<KindVO> kindList = adao.selectKindList();
		request.setAttribute("kindList", kindList);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void getCategoryList(HttpServletRequest request) throws SQLException {
		List<CategoryVO> categoryList = adao.selectCategoryList();
		request.setAttribute("categoryList", categoryList);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void getProductList(HttpServletRequest request) throws SQLException {
		List<ProductVO> productList = adao.selectProductList();
		request.setAttribute("productList", productList);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void getOptionList(HttpServletRequest request) throws SQLException {
		List<OptionVO> OptionList = adao.selectOptionList();
		request.setAttribute("OptionList", OptionList);
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void getEachGoodsList(HttpServletRequest request) throws SQLException {
		List<EachGoodsVO> EachGoodsVO = adao.selectEachGoodsList();
		request.setAttribute("EachGoodsVO", EachGoodsVO);
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 로그인 유무를 검사해서 로그인 했으면 true를 리턴해주고 로그인을 안했으면 false를 리턴해주도록 한다.
	public boolean checkLogin(HttpServletRequest request) {

		HttpSession session = request.getSession(); // 세션불러오기
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser"); // 세션에 저장된 value값을 key값을 넣어 가져오기

		if (loginuser != null) {
			// 로그인 한 경우
			return true;
		} else {
			// 로그인 안한 경우
			return false;
		}
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

// 로그인을 하면 시작페이지로 가지 않고 방금 보았던 그 페이지에 그대로 돌아가기 위함
	public void goBackURL(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("goBackURL", MyUtil.getCurrentURL(request));
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	//헤더에 정보를 넘겨주기 위한 메소드
	public void header(HttpServletRequest request) {
		
		try {
			InterProductDAO pdao = new ProductDAO();
			
			List<ProductVO> categoryInfo = new ArrayList<>();
			categoryInfo = pdao.categoryInfo();
			
			request.setAttribute("categoryInfo", categoryInfo);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}