package admin.management.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ManagementDAO implements InterManagementDAO {
	private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public ManagementDAO() {
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
	@Override
	public Map<String, String> getManagementInfo() throws SQLException {
		
		Map<String, String> managementInfo = new HashMap<String, String>();
		
			try {
				conn = ds.getConnection();
				
				//////////////////////// 이번주 주간 총 판매액
				String sql = "select to_char(paydate, 'yyyy-ww') as weekOfYear, sum(total) as totalSalesPriceByWeek\n"+
						"    from tbl_payment_log \n"+
						"    where to_char(paydate, 'yyyy-ww') = to_char(sysdate,'yyyy-ww')\n"+
						"    group by to_char(paydate, 'yyyy-ww')";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					managementInfo.put("weekOfYear", rs.getString(1));
					managementInfo.put("totalSalesPriceByWeek", rs.getString(2));
				}
				
				//////////////////////// 이번달  총 판매액 
				sql = "select to_char(paydate, 'yyyy-mm') as monthOfYear, sum(total)totalSalesPriceByMonth\n"+
						"    from tbl_payment_log \n"+
						"    where to_char(paydate, 'yyyy-mm') = to_char(sysdate,'yyyy-mm')\n"+
						"    group by to_char(paydate, 'yyyy-mm')";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					managementInfo.put("monthOfYear", rs.getString(1));
					managementInfo.put("totalSalesPriceByMonth", rs.getString(2));
				}
				
				//////////////////// 총 등록된 회원수
				
				sql = "select count(*) as totalMember\n"+
						"from tbl_member\n"+
						"where userno != '0'";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					managementInfo.put("totalMember", rs.getString(1));
				}
				
				////////////////////총 등록된 상품수(옵션제외)
				
				sql = "select count(*) as totalProduct\n"+
						"from tbl_product\n"+
						"where productstatus != 0\n";
						
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					managementInfo.put("totalProduct", rs.getString(1));
				}
				
				
				
				////////////////////이번달 주문왕 (주문수)
				
				sql = "select fk_userno, name, orderqty\n"+
						"from \n"+
						"    (\n"+
						"        select fk_userno, count(*) as orderqty\n"+
						"        from tbl_payment_log \n"+
						"        where to_char(paydate, 'yyyy-mm') = to_char(sysdate, 'yyyy-mm')\n"+
						"        group by fk_userno\n"+
						"    ) v join \n"+
						"    (select userno, name\n"+
						"    from tbl_member\n"+
						"    where status = 1) m\n"+
						"on v.fk_userno = m.userno\n"+
						"order by orderqty desc";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					managementInfo.put("buyUserno", rs.getString(1));
					managementInfo.put("buyName", rs.getString(2));
					managementInfo.put("totalOrderQty", rs.getString(3));
				}
				
				
				////////////////////이번달 flex (구매액)
				
				sql = "select fk_userno, name, totalflex\n"+
						"from \n"+
						"(\n"+
						"    select fk_userno, sum(total) as totalflex\n"+
						"    from tbl_payment_log\n"+
						"    where to_char(paydate, 'yyyy-mm') = to_char(sysdate, 'yyyy-mm')\n"+
						"    group by fk_userno\n"+
						") v\n"+
						"join\n"+
						"(select userno, name\n"+
						"from tbl_member\n"+
						"where status = 1) m\n"+
						"on v.fk_userno = m.userno\n"+
						"order by totalflex desc";
				
				pstmt = conn.prepareStatement(sql);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					managementInfo.put("flexUserno", rs.getString(1));
					managementInfo.put("flexName", rs.getString(2));
					managementInfo.put("totalFlex", rs.getString(3));
				}
				
			} finally {
			
				close();
			}
		
		return managementInfo;
		
		
		
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Map<String, String>> getListManagementInfo() throws SQLException {
		List<Map<String, String>> infoList = new ArrayList<>();
		try {
			conn = ds.getConnection();
		
			String sql = "select krcategoryname, V.fk_categorycode, countPerCategory\n"+
					 "from\n"+ "(select fk_categorycode, count(*) as countPerCategory\n"+
					 "from tbl_product\n"+ "where productstatus > 0\n"+
					 "group by fk_categorycode\n"+ ") v join\n"+
					 "(select categorycode, krcategoryname\n"+ "from tbl_category\n"+ ") c\n"+
					 "on v.fk_categorycode = c.categorycode\n"+ "order by 2";
			
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
 
			while (rs.next()) { 
				Map<String, String> map = new HashMap<String, String>();
				
				map.put("krcategoryname", rs.getString(1));
				map.put("categorycode", rs.getString(2));
				map.put("countPerCategory", rs.getString(3)); 
				
				infoList.add(map);
				
 
			}
		
		
		
		} finally {
			close();
		}
////////////////////카테고리별 등록된 상품 갯수
		
	
	 
	
	
	
		return infoList;
	}
}
