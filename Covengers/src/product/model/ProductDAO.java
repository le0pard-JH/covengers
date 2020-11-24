package product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import util.security.AES256;
import util.security.SecretMyKey;

public class ProductDAO implements InterProductDAO {
	
	private DataSource ds;  // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private AES256 aes;
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public ProductDAO() {
		try {
			Context initContext = new InitialContext();
		    Context envContext  = (Context)initContext.lookup("java:/comp/env");
		    ds = (DataSource)envContext.lookup("jdbc/covengers_oracle");
		    aes = new AES256(SecretMyKey.KEY);
		} catch(NamingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void close() {
		try {
			if(rs != null)    {rs.close();    rs=null;}
			if(pstmt != null) {pstmt.close(); pstmt=null;}
			if(conn != null)  {conn.close();  conn=null;}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 상품 카테고리별로 보여주기
	@Override
	public List<ProductVO> selectProductList(String categorycode) throws SQLException {
		
		List<ProductVO> productList =  new ArrayList<ProductVO>();
		
		try {
			conn = ds.getConnection();
			
			String sql = "select p.productcode, c.encategoryname, c.krcategoryname,"
					+ "enproductname, krproductname\n"+
					", productimg1, saleprice,  productdescshort \n"+
					"from tbl_product p left join tbl_category c\n"+
					"on p.fk_categorycode = c.categorycode\n"+
					"where p.fk_categorycode = ? "
					+ "order by 1 ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categorycode);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductVO product = new ProductVO();
				
				product.setProductcode(rs.getString(1));
				product.setEncategoryname(rs.getString(2));
				product.setKrcategoryname(rs.getString(3));
				
				product.setEnproductname(rs.getString(4));
				product.setKrproductname(rs.getString(5));
				product.setProductimg1(rs.getString(6));
				// product.setProductimg2(rs.getString(9));
				// product.setPrice(rs.getInt(10));
				product.setSaleprice(rs.getInt(7));
				// product.setOrigin(rs.getString(12));
				product.setProductdescshort(rs.getString(8));
				// product.setProductinputdate(rs.getString(14));
				// product.setManufacturedate(rs.getString(15));
				// product.setExpiredate(rs.getString(16));
				////product.setProductdesc1(rs.getString(17));
				// product.setProductdesc2(rs.getString(18));
				// product.setIngredient(rs.getString(19));
				// product.setPrecautions(rs.getString(20));
				// product.setProductstatus(rs.getInt(21));
				
				
				
				productList.add(product);
				
			}
		} finally {
			close();
		}
		
		
		

		return productList;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public ProductVO selectOneProduct(String productcode) throws SQLException {
		
		ProductVO product = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = "select p.productcode, c.encategoryname, c.krcategoryname, enproductname, krproductname\n"+
					", productimg1, productimg2, saleprice, origin, productdescshort\n"+
					", to_char(manufacturedate, 'yyyy-mm-dd') as manufacturedate, to_char(expiredate, 'yyyy-mm-dd') as expiredate,"
					+ " productdesc1, productdesc2, ingredient, precautions, p.fk_categorycode \n"+
					"from tbl_product p left join tbl_category c\n"+
					"on p.fk_categorycode = c.categorycode\n"+
					"where p.productstatus = 1 and p.productcode = ?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productcode);
			
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				product = new ProductVO();
				
				product.setProductcode(rs.getString(1));
				product.setEncategoryname(rs.getString(2));
				product.setKrcategoryname(rs.getString(3));
				
				product.setEnproductname(rs.getString(4));
				product.setKrproductname(rs.getString(5));
				product.setProductimg1(rs.getString(6));
				product.setProductimg2(rs.getString(7));
				// product.setPrice(rs.getInt(10));
				product.setSaleprice(rs.getInt(8));
				product.setOrigin(rs.getString(9));
				product.setProductdescshort(rs.getString(10));
				// product.setProductinputdate(rs.getString(14));
				product.setManufacturedate(rs.getString(11));
				product.setExpiredate(rs.getString(12));
				product.setProductdesc1(rs.getString(13));
				product.setProductdesc2(rs.getString(14));
				product.setIngredient(rs.getString(15));
				product.setPrecautions(rs.getString(16));
				product.setFk_categorycode(rs.getString(17));
				// product.setProductstatus(rs.getInt(21));
			}
			
		} finally {
			close();
		}
		
		
		return product;
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<OptionVO> selectedProductOption(ProductVO product) throws SQLException {
		List<OptionVO> optionList = new ArrayList<>();
		
		
		try {
			String productcode = product.getProductcode();
			
			conn = ds.getConnection();
			
			// stock = 0인 부분은 stock != 0으로 바꾸어 주어야 한다!
			String sql = "select optioncode, fk_productcode, optionname, addprice \n"+
					"from tbl_option\n"+
					"where stock != 0 and fk_productcode = ?" + "order by addprice";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, productcode);
			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OptionVO option = new OptionVO();
				option.setOptioncode(rs.getString(1));
				option.setFk_productcode(rs.getString(2));
				option.setOptionname(rs.getString(3));
				option.setAddprice(rs.getInt(4));
				
				optionList.add(option);
			}
			
		} finally {
		
			close();
		}
		return optionList;
	
	}	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	@Override
	public int insertIntoCart(Map<String, String> paraMap) throws SQLException {
		int result = 0;

		try {
			conn = ds.getConnection();

			String sql = " select cartno " + " from TBL_CART "
					+ " where fk_userno = ? and fk_productcode = ? and fk_optioncode = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paraMap.get("userno"));
			pstmt.setString(2, paraMap.get("productcode"));
			pstmt.setString(3, paraMap.get("optioncode"));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				int cartno = rs.getInt(1);
				int poqty = Integer.parseInt(paraMap.get("poqty"));

				sql = " update TBL_CART set poqty = poqty + ? " + " where cartno = ? ";
				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, poqty);
				pstmt.setInt(2, cartno);

				result = pstmt.executeUpdate();

			} else {

				sql = "insert into TBL_CART (cartno, fk_userno, fk_productcode, poqty, fk_optioncode, pprice)\n"
						+ "values(seq_cartno.nextval, ?, ?, ?, ?, ?)";

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, paraMap.get("userno"));
				pstmt.setString(2, paraMap.get("productcode"));
				pstmt.setInt(3, Integer.parseInt(paraMap.get("poqty")));
				pstmt.setString(4, paraMap.get("optioncode"));
				pstmt.setInt(5, Integer.parseInt(paraMap.get("pprice")));

				result = pstmt.executeUpdate();
			}

		} finally {
			close();
		}
		return result;

	}
	
	@Override
	public List<ProductVO> selectOtherProduct(Map<String, String> paraMap) throws SQLException {
		List<ProductVO> productList = new ArrayList<>();

		try {

			conn = ds.getConnection();

			String sql = " select productcode, krproductname, productimg1" + " from tbl_product "
					+ " where productcode != ? and fk_categorycode = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paraMap.get("productcode"));
			pstmt.setString(2, paraMap.get("categorycode"));

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductVO product = new ProductVO();

				product.setProductcode(rs.getString(1));
				product.setKrproductname(rs.getString(2));
				product.setProductimg1(rs.getString(3));

				productList.add(product);

			}

		} finally {
			close();
		}

		return productList;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int writeReview(Map<String, String> paraMap) throws SQLException {
		int result = 0;

		try {

			conn = ds.getConnection();

			String sql = "insert into tbl_review_test(reviewno, fk_orderno, fk_productcode, fk_userno, rcontents, rgrade)\n"
					+ "values(rseq.nextval, ?, ?, ?, ?, ?)";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paraMap.get("orderno"));
			pstmt.setString(2, paraMap.get("productcode"));
			pstmt.setString(3, paraMap.get("userno"));
			pstmt.setString(4, paraMap.get("rcontents"));
			pstmt.setString(5, paraMap.get("rgrade"));

			result = pstmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			result = 2;
		} finally {
			close();

		}

		return result;
	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Map<String, String>> getReviewList(String currentShowPageNo) throws SQLException {
		List<Map<String, String>> reviewList = new ArrayList<>();

		try {

			conn = ds.getConnection();

			String sql = "select reviewno, fk_orderno, fk_productcode, fk_userno, rcontents, rdate, rgrade, email\n"
					+ "from\n" + "(\n"
					+ "    select rownum AS RNO, reviewno, fk_orderno, fk_productcode, fk_userno, rcontents, rdate, rgrade, email\n"
					+ "    from\n" + "    (\n"
					+ "        select reviewno, fk_orderno, fk_productcode, fk_userno, rcontents, to_char(rdate, 'yyyy-mm-dd') as rdate, rgrade, email \n"
					+ " 		from tbl_review_test r join tbl_member m \n" + " 		on r.fk_userno = m.userno\n"
					+ "		order by 1 desc\n" + "    )V\n" + ")T\n" + "where rno between ? and ?";

			// String sql = "select reviewno, fk_orderno, fk_productcode, fk_userno,
			// rcontents, to_char(rdate, 'yyyy-mm-dd') as rdate, rgrade, m.email\n"+
			// "from tbl_review_test r join tbl_member m\n"+
			// "on r.fk_userno = m.userno\n"+
			// "order by 1";

			pstmt = conn.prepareStatement(sql);

			int currentShowPage = Integer.parseInt(currentShowPageNo);
			int sizePerPage = 10; // 한 페이지당 화면상에 보여줄 게시판 개수는 10.

			pstmt.setInt(1, (currentShowPage * sizePerPage) - (sizePerPage - 1)); // 공식
			pstmt.setInt(2, (currentShowPage * sizePerPage)); // 공식

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Map<String, String> review = new HashMap<>();

				review.put("reviewno", rs.getString(1));
				review.put("fk_orderno", rs.getString(2));
				review.put("fk_productcode", rs.getString(3));
				review.put("fk_userno", rs.getString(4));
				review.put("rcontents", rs.getString(5));
				review.put("rdate", rs.getString(6));
				review.put("rgrade", rs.getString(7));
				review.put("email",  aes.decrypt(rs.getString(8)));

				reviewList.add(review);

			}

		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			e.printStackTrace();
		} finally {
			close();

		}

		return reviewList;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public int getTotalPage() throws SQLException {

		int totalPage = 0;

		try {
			conn = ds.getConnection();

			String sql = "SELECT ceil(count(*)/10) " + // 10이 sizePerPage
					"FROM tbl_review_test ";

			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			rs.next();

			totalPage = rs.getInt(1);
		} finally {
			close();
		}
		return totalPage;
	}// end of public int getTotalPage() throws SQLException
		// {}--------------------------

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Map<String, String>> getProductReviewList(ProductVO product) throws SQLException {
		List<Map<String, String>> reviewList = new ArrayList<>();
		
		try {

			conn = ds.getConnection();

			String sql = "select reviewno, fk_orderno, fk_productcode, fk_userno, rcontents, rdate, rgrade, email\n"
					+ "from\n" + "(\n"
					+ "    select rownum AS RNO, reviewno, fk_orderno, fk_productcode, fk_userno, rcontents, rdate, rgrade, email\n"
					+ "    from\n" + "    (\n"
					+ "        select reviewno, fk_orderno, fk_productcode, fk_userno, rcontents, to_char(rdate, 'yyyy-mm-dd') as rdate, rgrade, email \n"
					+ " 		from tbl_review_test r join tbl_member m \n" + " 		on r.fk_userno = m.userno where fk_productcode = ?\n"
					+ "		order by 1 desc\n" + "    )V\n" + ")T\n" + "where rno between 1 and 3";

			

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, product.getProductcode());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				Map<String, String> review = new HashMap<>();

				review.put("reviewno", rs.getString(1));
				review.put("fk_orderno", rs.getString(2));
				review.put("fk_productcode", rs.getString(3));
				review.put("fk_userno", rs.getString(4));
				review.put("rcontents", rs.getString(5));
				review.put("rdate", rs.getString(6));
				review.put("rgrade", rs.getString(7));
				review.put("email", aes.decrypt(rs.getString(8)));

				reviewList.add(review);

			}

		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();

		}
		
		return reviewList;
	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public Map<String, String> getOneReview(String reviewno) throws SQLException {
		Map<String, String> review = new HashMap<>();
		
		try {
			conn = ds.getConnection();
			
			String sql = "select reviewno, fk_orderno, fk_productcode, fk_userno, rcontents, to_char(rdate,'yyyy-mm-dd') as rdate, rgrade, email, krproductname\n"+
					"from tbl_review_test r join tbl_product p\n"+
					"on r.fk_productcode = p.productcode\n"+
					"join tbl_member m\n"+
					"on r.fk_userno = m.userno\n"+
					"where r.reviewno = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, reviewno);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				review.put("reviewno", rs.getString(1));
				review.put("fk_orderno", rs.getString(2));
				review.put("fk_productcode", rs.getString(3));
				review.put("fk_userno", rs.getString(4));
				review.put("rcontents", rs.getString(5));
				review.put("rdate", rs.getString(6));
				review.put("rgrade", rs.getString(7));
				review.put("email", aes.decrypt(rs.getString(8)));
				review.put("krproductname", rs.getString(9));
			}
			
			
		} catch (UnsupportedEncodingException | GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return review;
	}
	@Override
	public List<ProductVO> selectAll() {
		List<ProductVO> pList = new ArrayList<>();
		
		try {			
			conn = ds.getConnection();
			
			// stock = 0인 부분은 stock != 0으로 바꾸어 주어야 한다!
			String sql = " select * from TBL_PRODUCT ";
			
			pstmt = conn.prepareStatement(sql);			
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductVO pvo = new ProductVO();
				pvo.setProductcode(rs.getString(1));
				pvo.setFk_kindcode(rs.getString(2));
				pvo.setFk_categorycode(rs.getString(3));
				pvo.setEnproductname(rs.getString(4));
				pvo.setKrproductname(rs.getString(5));
				pvo.setProductimg1(rs.getNString(6));
				pvo.setProductimg2(rs.getNString(7));
				pvo.setPrice(rs.getInt(8));
				pvo.setSaleprice(rs.getInt(9));
				pvo.setOrigin(rs.getNString(10));
				pvo.setProductdescshort(rs.getNString(11));
				pvo.setProductinputdate(rs.getString(12));
				pvo.setManufacturedate(rs.getString(13));
				pvo.setExpiredate(rs.getString(14));
				pvo.setProductdesc1(rs.getString(15));
				pvo.setProductdesc2(rs.getString(16));
				pvo.setIngredient(rs.getString(17));
				pvo.setPrecautions(rs.getString(18));
				pvo.setProductstatus(rs.getInt(19));
				
				pList.add(pvo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		
			close();
		}
		
		return pList;
	}

	@Override
	public KindVO selectOneKind(String categoryCode) throws SQLException {
		
		KindVO kvo = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = "select fk_kindcode, encategoryname, krcategoryname\n"+
					"from tbl_category\n"+
					"where categorycode = ?";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, categoryCode);
			
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				kvo = new KindVO();
				
				
				kvo.setKindcode(rs.getString(1));
				kvo.setEnkindname(rs.getString(2));
				kvo.setKrkindname(rs.getString(3));
			}
			
			

			
			
		} finally {
			close();
		}
		
		
		return kvo;
	}

    ////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 201121 박지현추가
	// 관리자가 메인페이지 추천 상품으로 지정한 상품의 정보 불러오기
	@Override
	public List<ProductVO> productInfoForAD() throws SQLException {
		
		ResultSet rs2 = null;
		List<ProductVO> plist = new ArrayList<>();
		ProductVO pvo = null;
		
		try {
			conn = ds.getConnection();
			
			String sql = " select fk_productcode from tbl_adproduct "; 
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery(); //adproduct테이블조회
			
			while(rs.next()) {	//나온행 개수대로 반복
				pvo = new ProductVO();	//productVO생성
				
				pvo.setProductcode(rs.getString(1));	//pvo에 그 행의 fk_productcode 넣어주기 
				
				sql = " select enproductname, krproductname, productimg1, productimg2, productdescshort "
						+ " from tbl_product "
						+ " where productcode = ? ";
				
				pstmt = conn.prepareStatement(sql); 
				
				pstmt.setString(1, pvo.getProductcode()); //아까 pvo에 넣어둔 productcode로 product테이블 조회하기
				
				rs2 = pstmt.executeQuery();
				
				if(rs2.next()) {
					pvo.setEnproductname(rs2.getString(1));
					pvo.setKrproductname(rs2.getString(2));
					pvo.setProductimg1(rs2.getString(3));
					pvo.setProductimg2(rs2.getString(4));
					pvo.setProductdescshort(rs2.getString(5));
					
//					System.out.println(pvo.getEnproductname());
//					System.out.println(pvo.getKrproductname());
//					System.out.println(pvo.getProductimg1());
//					System.out.println(pvo.getProductimg2());
//					System.out.println(pvo.getProductdescshort());
					
					plist.add(pvo);
				}// end of if()---------------------------------
				
				} // end of while()-------------------------------
			
			} finally {
				close();
				if(rs2 != null)    {rs2.close();    rs2=null;}
			}
		
		return plist;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	// DB에서 카테고리 정보 가져오기
	@Override
	public List<ProductVO> categoryInfo() throws SQLException {

		List<ProductVO> categoryInfo = new ArrayList<>();
		ProductVO pvo = null;
		
		try {
			
		
			conn = ds.getConnection();
			
			String sql = " select categorycode, fk_kindcode, encategoryname, krcategoryname "
					+ " from tbl_category "
					+ " order by 1 "; 
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery(); //카테고리 테이블조회
			
			
			while(rs.next()) {	//나온행 개수대로 반복
				pvo = new ProductVO();	//productVO생성
				
				
				pvo.setFk_categorycode(rs.getString(1));
				pvo.setFk_kindcode(rs.getString(2));
				pvo.setEncategoryname(rs.getString(3));
				pvo.setKrcategoryname(rs.getString(4));
					

				categoryInfo.add(pvo);	
				
				}// end of while()---------------------------------
			
		} finally {
			close();
		}
		
		return categoryInfo;
	}
		
	
	

    
}
