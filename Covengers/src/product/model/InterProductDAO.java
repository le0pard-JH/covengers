package product.model;

import java.sql.SQLException;
import java.util.*;

public interface InterProductDAO {
	// 카테고리별로 상품을 보여주기 위해 카테고리에 맞는 상품을 리스트로 리턴해 주는 메소드
	List<ProductVO> selectProductList(String categorycode) throws SQLException;
	
	
	// 상품 디테일페이지를 위한 특정한 상품 1개를 가져오는 메소드
	ProductVO selectOneProduct(String productcode) throws SQLException;


	// 선택한 상품의 옵션을 조회해서 리스트로 백백~ 메소드
	List<OptionVO> selectedProductOption(ProductVO product) throws SQLException;
	
	
	// 선택한 상품과 옵션과 수량을 장바구니 db넣어주는 메소드
	int insertIntoCart(Map<String, String> paraMap) throws SQLException;
	
	// 선택한 상품과 관련된 다른 상품들을 디피함
		List<ProductVO> selectOtherProduct(Map<String, String> paraMap) throws SQLException;

		//작성한 리뷰를 인서트 시킴
		int writeReview(Map<String, String> paraMap) throws SQLException;

		// 리뷰리스트 조회
		List<Map<String, String>> getReviewList(String currentShowPageNo) throws SQLException;
		
		// 페이징 처리를 위한 전체 페이지 조회
		int getTotalPage() throws SQLException;

		// 상품별 리뷰리스트를 가져와 3개만 보여주기
		List<Map<String, String>> getProductReviewList(ProductVO product) throws SQLException;

		// 한가지 주문의 한가지 상품에 대해서 사용자가 작성한 리뷰를 볼 수 있는 메소드
		Map<String, String> getOneReview(String reviewno) throws SQLException;
	
	// 전체 상품 조회
	List<ProductVO> selectAll() throws SQLException;

	// 상품 카테고리 조회
	KindVO selectOneKind(String categoryCode) throws SQLException;

    // 201121 박지현추가
	// 관리자가 메인페이지 추천 상품으로 지정한 상품의 정보 불러오기
	List<ProductVO> productInfoForAD() throws SQLException;

	// DB에서 카테고리 정보 가져오기
	List<ProductVO> categoryInfo() throws SQLException;

}
