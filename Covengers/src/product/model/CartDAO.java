package product.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


public class CartDAO implements InterCartDAO {

	private DataSource ds;  	// DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	
	// 생성자
	public CartDAO() {
		try {
	         Context initContext = new InitialContext();
	         Context envContext  = (Context)initContext.lookup("java:/comp/env");
	         ds = (DataSource)envContext.lookup("jdbc/covengers_oracle");
	      } catch(NamingException e) {
	         e.printStackTrace();
	      }
	}

	
	// 사용한 자원을 반납하는 close() 메소드 생성하기
		private void close() {
		      try {
		         if(rs != null)    {rs.close(); rs=null;}
		         if(pstmt != null) {pstmt.close(); pstmt=null;}
		         if(conn != null)  {conn.close(); conn=null;}
		      } catch(SQLException e) {
		         e.printStackTrace();
		      }
	}// end of private void close() {}--------------------------------------
	
	
	// 회원번호로 장바구니 테이블을 조회(select)해주는 메소드
	@Override
	public List<CartVO> selectCartList(String fk_userno) throws SQLException {

		List<CartVO> cartList = new ArrayList<>();
		
		try {
			
			conn = ds.getConnection();

			String sql = "SELECT cartno, fk_productcode, fk_optioncode, krproductname, productimg1, c.pprice, poqty\n"+
						 "FROM TBL_PRODUCT P RIGHT JOIN tbl_cart c\n"+
						 "ON p.productcode = c.fk_productcode\n"+
						 "WHERE fk_userno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fk_userno);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				CartVO cart = new CartVO();
				
				cart.setFk_userno(fk_userno);
				cart.setCartno(rs.getString(1));
				cart.setFk_productcode(rs.getString(2));
				cart.setFk_optioncode(rs.getString(3));
				cart.setKrproductname(rs.getString(4));
				cart.setProductimg1(rs.getString(5));
				cart.setPprice(rs.getInt(6));
				cart.setPoqty(rs.getInt(7));
				cart.setTotalprice(); 
				
				cartList.add(cart);
			}

		} finally {
			close();
		}
		
		return cartList;
	}// end of public List<CartVO> selectCartList(String userno) throws SQLException {}-----------------------


	// 회원번호로 장바구니에 있는 로그인한 유저의 모든 데이터를 삭제해주는 메소드
	@Override
	public int cartDeleteAll(String fk_userno) throws SQLException {
		
		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			String sql = " DELETE FROM TBL_CART "+
						 " WHERE fk_userno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, fk_userno);
			
		//	System.out.println(fk_userno);
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}


	// 장바구니 번호와 수량을 받아 물건 개수와 합계 금액을 업데이트 해주는 메소드.
	@Override
	public int updateCart(int poqty, String cartno) throws SQLException {

		int result = 0;
		
		try {
			conn = ds.getConnection();
			
			
			String sql = " UPDATE TBL_CART SET poqty = ? "+
						 " WHERE cartno = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, poqty); 
			pstmt.setString(2, cartno); 
			
			
			result = pstmt.executeUpdate();
			
		} finally {
			close();
		}
		
		return result;
	}


	// 장바구니에서 상품을 개별 삭제해주는 메소드.
	@Override
	public int deleteOne(String Fk_userno, String cartno) {
	      
	      int result = 0;
	      
	      try {
	         conn = ds.getConnection();
	         
	         String sql = " DELETE FROM TBL_CART " + 
	        		 	  " WHERE Fk_userno = ? AND cartno = ? ";
	         
	         pstmt = conn.prepareStatement(sql);
	         
	         pstmt.setString(1, Fk_userno);
	         pstmt.setString(2, cartno);
	         
	         result = pstmt.executeUpdate();
	         
	      } catch( Exception e) {
	         e.printStackTrace();
	      } finally {
	         close();
	      }
	      
	      return result;
		
	}


	
	
	

}
