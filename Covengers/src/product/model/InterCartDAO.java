package product.model;

import java.sql.SQLException;
import java.util.List;

public interface InterCartDAO {

	// 회원번호로 장바구니 테이블을 조회(select)해주는 메소드(회원 번호에 일치하는 행이 없다면 List의 size()는 0이 됨.)
	List<CartVO> selectCartList(String fk_userno) throws SQLException;

	// 회원번호로 장바구니에 있는 로그인한 유저의 모든 데이터를 삭제해주는 메소드
	int cartDeleteAll(String fk_userno) throws SQLException;

	// 장바구니에서 상품을 개별 삭제해주는 메소드.
	int deleteOne(String fk_userno, String cartno);
	
	// 장바구니 번호와 수량을 받아 물건 개수와 합계 금액을 업데이트 해주는 메소드.
	int updateCart(int poqty, String cartno) throws SQLException;
	
	
	
}
