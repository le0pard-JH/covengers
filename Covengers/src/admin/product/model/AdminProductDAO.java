package admin.product.model;

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

import product.model.*;

public class AdminProductDAO implements InterAdminProductDAO {
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public AdminProductDAO() {
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/covengers_oracle");
		} catch (NamingException e) {
			e.printStackTrace();
		}

	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 종류 테이블에 인서트 시키기
	@Override
	public int insertProductKind(Map<String, String> paraMap) throws SQLException {
		int result = 0;

		try {
			conn = ds.getConnection();

			String sql = "select kindcode, enkindname, krkindname " + "	  from tbl_kind " + "   where enkindname = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paraMap.get("enkindname"));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 3;
				return result;
			}

			sql = "select kindcode, enkindname, krkindname " + "	  from tbl_kind " + "   where krkindname = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paraMap.get("krkindname"));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 4;
				return result;
			}

			sql = "insert into tbl_kind (kindcode, enkindname, krkindname)\n" 
			+ "values (?, ?, ?)";

			pstmt = conn.prepareStatement(sql);

			pstmt.setNString(1, paraMap.get("kindcode"));
			pstmt.setNString(2, paraMap.get("enkindname"));
			pstmt.setNString(3, paraMap.get("krkindname"));

			result = pstmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			result = 2;
		} finally {

			close();
		}

		return result;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public int insertProductCategory(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();

			String sql = "select encategoryname"
			+ "	  from tbl_category "
			+ "   where encategoryname = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paraMap.get("encategoryname"));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 3;
				return result;
			}

			sql = "select krcategoryname " 
			+ "	  from tbl_category "
			+ "   where krcategoryname = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paraMap.get("krcategoryname"));

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 4;
				return result;
			}

			sql = " insert into tbl_category (categorycode, fk_kindcode, encategoryname, krcategoryname) \n"
			+  "values (?, ?, ?, ?) ";
			
			

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, paraMap.get("categorycode"));
			pstmt.setString(2, paraMap.get("fk_kindcode"));
			pstmt.setString(3, paraMap.get("encategoryname"));
			pstmt.setString(4, paraMap.get("krcategoryname"));

			result = pstmt.executeUpdate();

		} catch (SQLIntegrityConstraintViolationException e) {
			result = 2;
		} finally {

			close();
		}

		return result;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<KindVO> selectKindList() throws SQLException {
		List<KindVO> kindList = new ArrayList<>();
		try {

			conn = ds.getConnection();

			String sql = "select kindcode , enkindname, krkindname\n" 
			+ "from tbl_kind";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				KindVO kind = new KindVO();

				kind.setKindcode(rs.getString(1));
				kind.setEnkindname(rs.getString(2));
				kind.setKrkindname(rs.getString(3));

				kindList.add(kind);
			}

		} finally {

			close();

		}
		return kindList;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<CategoryVO> selectCategoryList() throws SQLException {
		List<CategoryVO> categoryList = new ArrayList<>();
		try {

			conn = ds.getConnection();
			
			String sql = "select categorycode, fk_kindcode, encategoryname, krcategoryname, enkindname, krkindname \n"+
					"from tbl_category c join tbl_kind k "
					+ " on c.fk_kindcode = k.kindcode "
					+ "order by 1";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				CategoryVO category = new CategoryVO();

				category.setCategorycode(rs.getString(1));
				
				KindVO kindvo = new KindVO();
				kindvo.setKindcode(rs.getString(2));
				kindvo.setEnkindname(rs.getString(5));
				kindvo.setKrkindname(rs.getString(6));
				
				category.setKindvo(kindvo);
				category.setEncategoryname(rs.getString(3));
				category.setKrcategoryname(rs.getString(4));

				categoryList.add(category);
			}

		} finally {

			close();

		}
		return categoryList;

	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<ProductVO> selectProductList() throws SQLException {
		List<ProductVO> productList = new ArrayList<>();
		
		try {

			conn = ds.getConnection();
			
			String sql = " select productcode, enproductname, krproductname "
					+ " from tbl_product "
					+ " order by 1 ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductVO product = new ProductVO();
				
				product.setProductcode(rs.getString(1));
				product.setEnproductname(rs.getString(2));
				product.setKrproductname(rs.getString(3));
				
				productList.add(product);
				
			}

		} finally {

			close();

		}
		
		return productList;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<OptionVO> selectOptionList() throws SQLException {
		List<CategoryVO> categoryList = new ArrayList<>();
		return null;
	}
	///////////////////////////////////////// //////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public List<EachGoodsVO> selectEachGoodsList() throws SQLException {
		List<CategoryVO> categoryList = new ArrayList<>();
		return null;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@Override
	public String getLastCategorycode(Map<String, String> paraMap) throws SQLException {
		
		String result = "";
		try {
			conn = ds.getConnection();
			
			String sql = "";
			
			// 상품 카테고리 등록에서 옵션을 선택한 경우 
			// 카테고리에 등록되어진 마지막 번호의 상품을 가져오기
			if (paraMap.get("selectedCategoryOption") == null) {
				// System.out.println("디비 상품 카테고리 등록 선택" + paraMap.get("selectedKindOption"));
				sql = " select max(categorycode) "
						+ " from tbl_category "
						+ " where fk_kindcode = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paraMap.get("selectedKindOption"));
				
				 		
			} 
			
			// 상품 등록에서 옵션을 선택한 경우
			else if (paraMap.get("selectedKindOption") == null) {
				// System.out.println("디비 상품 등록 선택" + paraMap.get("selectedCategoryOption"));
				sql = "select max(productcode)\n"+
						"from tbl_product\n"+
						"where fk_categorycode = ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, paraMap.get("selectedCategoryOption"));
			}
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString(1);
			} 
			
		} finally {
			close();
		}
		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String getKindCategorycode(Map<String, String> paraMap) throws SQLException {
		String result = "";
		try {
			conn = ds.getConnection();
			
			String sql = " select fk_kindcode, categorycode "
					+ " from tbl_category "
					+ " where categorycode = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("selectedCategoryOption"));
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result += rs.getString(1) + "-" + rs.getString(2) + "-";
			}
		} finally {
			close();
		}
		
		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String getOptioncodeAction(Map<String, String> paraMap) throws SQLException {
		String result = "";
		try {
			conn = ds.getConnection();
			
			String sql = " select max(optioncode)\n "+
					" from tbl_option\n "+
					" where fk_productcode = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, paraMap.get("selectedProductcode"));
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getString(1);
				
			}
		} finally {
			close();
		}
		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public int insertProductOption(Map<String, String> paraMap) throws SQLException {
		int result = 0;
		
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			
			String sql = " insert into tbl_option (optioncode, fk_productcode, optionname, addprice, stock) \n"
			+  " values (?, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, paraMap.get("optioncode"));
			pstmt.setString(2, paraMap.get("fk_productcode"));
			pstmt.setString(3, paraMap.get("optionname"));
			pstmt.setString(4, paraMap.get("addprice"));
			pstmt.setNString(5, paraMap.get("qty"));
			
			int result1 = pstmt.executeUpdate();  // 옵션을 인서트 해준다.
			
			/////////////////////////////////////////////////////////////////////////
			
			String optioncode = paraMap.get("optioncode");
			int temp1 = Integer.parseInt(optioncode.substring(12)) - 1;
			optioncode = optioncode.substring(0,12) + temp1;
			
			sql = " select nvl(max(eachgoodscode), 'null')\n"+
					" from tbl_each_goods\n"+
					" where fk_optioncode = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, optioncode);
			
			rs = pstmt.executeQuery();
			
			
			String maxgoods = "";
			
			if(rs.next()) {
				maxgoods = rs.getString(1);
			}
			
			int qty = Integer.parseInt(paraMap.get("qty"));
			
			
			
			int result2 = 1;
			
			if ("null".equals(maxgoods)) {
				
					maxgoods = paraMap.get("optioncode") + "-1000";
					
			}
			
			String temp = maxgoods.substring(16);
			int int_temp = Integer.parseInt(temp);
			
			for (int i = 0 ; i < qty; i ++) {
				int_temp += 1;
				
				temp = String.valueOf(int_temp);
				
				
				if (temp.length() == 1) {
					temp = "00" + temp;
				} else if(temp.length() == 2) {
					temp = "0" + temp;
				} else if( temp.length() == 3 && int_temp < 1000) {  // 세자리 숫자에 값이 1000보다 작은 경우
					temp = temp;
				} else {  // 네자리 숫자가 나오면 이것도 생각좀 해보자구
					temp = "999";
				}
				
				maxgoods = maxgoods.substring(0, 16) + temp;
				
				sql = " insert into tbl_each_goods (eachgoodscode, fk_optioncode) "
						+ " values (?, ?)";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, maxgoods);
				pstmt.setString(2, paraMap.get("optioncode"));
				
				result2 = result2 * pstmt.executeUpdate();
			}
			
			
			
			if (result1 * result2 == 1) {
				result = 1;
				conn.commit();
			} else {
				result = 3;
				conn.rollback();
			}
			
			
			

		} catch (SQLIntegrityConstraintViolationException e) {
			result = 2;
		} finally {

			close();
		}

		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public int insertProduct(ProductVO product) throws SQLException {
		int result = 0;
		try {
			conn = ds.getConnection();
			
			
			String sql = "select enproductname"
					+ "	  from tbl_product "
					+ "   where enproductname = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, product.getEnproductname());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 3;
				return result;
			}

			sql = "select krproductname"
					+ "	  from tbl_product "
					+ "   where krproductname = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, product.getKrproductname());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = 4;
				return result;
			}
			//////////////////////////////////////////////////////////////////////////////////////////////////////////		
					
			sql = "insert into tbl_product(productcode, fk_kindcode, fk_categorycode, enproductname, krproductname, productimg1, productimg2,\n"+
					"price, saleprice, origin, productdescshort, manufacturedate, expiredate, productdesc1, productdesc2, ingredient, precautions)\n"+
					"values (?, ?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?)\n";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, product.getProductcode());
			pstmt.setString(2, product.getFk_kindcode());
			pstmt.setString(3, product.getFk_categorycode());
			pstmt.setString(4, product.getEnproductname());
			pstmt.setString(5, product.getKrproductname());
			pstmt.setString(6, product.getProductimg1());
			pstmt.setString(7, product.getProductimg2());
			pstmt.setInt(8, product.getPrice());
			pstmt.setInt(9, product.getSaleprice());
			pstmt.setString(10, product.getOrigin());
			pstmt.setString(11, product.getProductdescshort());
			pstmt.setString(12, product.getManufacturedate());
			pstmt.setString(13, product.getExpiredate());
			pstmt.setString(14, product.getProductdesc1());
			pstmt.setString(15, product.getProductdesc2());
			pstmt.setString(16, product.getIngredient());
			pstmt.setString(17, product.getPrecautions());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			result = 2;
		} finally {
			close();
		}
		return result;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String kindCheck(String kindcode) throws SQLException {
		String result = null;
		
		
		try {
			conn = ds.getConnection();
			
			String sql = " select kindcode from tbl_kind where kindcode = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, kindcode);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				result = rs.getString(1);
			}
			
		} finally {
			close();
		}
		return result;
		
	}

}
