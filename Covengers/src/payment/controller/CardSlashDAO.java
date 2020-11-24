package payment.controller;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import java.sql.*;
import java.util.*;
import util.security.*;

import java.util.concurrent.ThreadLocalRandom;

public class CardSlashDAO implements InterCardSlashDAO {

  private DataSource ds; // DataSource ds 는 아파치톰캣이 제공하는 DBCP(DB Connection Pool) 이다.
  private Connection conn;
  private PreparedStatement pstmt;
  private ResultSet rs;

  private AES256 aes;


  // 생성자
  public CardSlashDAO() {
    try {
      Context initContext = new InitialContext();
      Context envContext = (Context) initContext.lookup("java:/comp/env");
      ds = (DataSource) envContext.lookup("jdbc/covengers_oracle");
      aes = new AES256(SecretMyKey.KEY);
    } catch (NamingException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  // 사용한 자원을 반납하는 close() 메소드 생성하기
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
  }// end of private void close() {}--------------------------------------

  // 장바구니 번호로 장바구니 테이블을 조회(select)해주는 메소드
  @Override
  public CardSlashVO cardSelectList(String fk_userno, String cartno) throws SQLException {

    CardSlashVO cardPurchaseVO = null;

    String rand = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 999999));

    Calendar currentDate = Calendar.getInstance();
    int currentYear = currentDate.get(Calendar.YEAR);
    int currentM = currentDate.get(Calendar.MONTH) + 1;
    int currentD = currentDate.get(Calendar.DATE);
    String thisYear = Integer.toString(currentYear);
    thisYear = thisYear.substring(2);
    String thisMonth = Integer.toString(currentM);
    String thisDay = Integer.toString(currentD);

    // System.out.println(thisYear + currentM + currentD + rand);
    //String orderno = thisYear + currentM + currentD + rand;
    
    try {

      conn = ds.getConnection();

      String sql =
          " SELECT fk_userno, cartno, fk_productcode, c.pprice, poqty, pprice * poqty AS total_price, fk_optioncode, PRODUCTIMG1, KRPRODUCTNAME, PRODUCTDESCSHORT FROM tbl_product p RIGHT JOIN tbl_cart c ON p.productcode = c.fk_productcode "
              + " WHERE fk_userno = ? AND cartno = ? ";

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, fk_userno);
      pstmt.setString(2, cartno);

      rs = pstmt.executeQuery();

      if (rs.next()) {

        cardPurchaseVO = new CardSlashVO();

        //cardPurchaseVO.setOrder_no(orderno);
        cardPurchaseVO.setFk_userno(rs.getString(1));
        cardPurchaseVO.setCartno(rs.getString(2));
        cardPurchaseVO.setProduct_code(rs.getString(3));
        cardPurchaseVO.setPprice(rs.getInt(4));
        cardPurchaseVO.setPoqty(rs.getInt(5));
        cardPurchaseVO.setTotal_amount(rs.getInt(6));
        cardPurchaseVO.setOption_code(rs.getString(7));
        cardPurchaseVO.setImg(rs.getString(8));
        cardPurchaseVO.setProduct_name(rs.getString(9));
        cardPurchaseVO.setDesc(rs.getString(10));
      }

    } finally {
      close();
    }

    return cardPurchaseVO;
  }// end of public List<CartVO> selectCartList(String userno) throws SQLException

  // prePayLog
  // Pre Payment Log insert 하기
  @Override
  public int prePayLog(Map<String, String> paraMap) throws SQLException {

    int result = 0;

    // 결제 완료 시 insert - pre : status = 1 (default)
    // insert into TBL_PAYMENT_LOG_PRE ( ORDER_NO, USERNO, CHECKOUT, CLIENTIP )
    // values ( '20201123000001', '202011230001', 500000, '192.168.0.1' );

    // 결제 완료 시 insert - detail : status = 1 (default)
    // insert into TBL_PAYMENT_LOG_DETAIL ( FK_ORDER_NO, FK_USERNO, OPTION_CODE, QTY, CLIENTIP )
    // values ( '20201123000001', '202011230001', 'A01', 3, '192.168.0.1' );

    String pre_order_no = paraMap.get("pre_order_no");
    System.out.println("PRE pre_order_no : " + pre_order_no);
    String pre_userno = paraMap.get("pre_userno");
    String pre_checkout = paraMap.get("pre_checkout");
    String pre_clientip = paraMap.get("pre_clientip");

    String detail_fk_order_no = paraMap.get("detail_fk_order_no");
    String detail_fk_userno = paraMap.get("detail_fk_userno");
    String detail_option_code = paraMap.get("detail_option_code");
    String detail_qty = paraMap.get("detail_qty");
    String detail_clientip = paraMap.get("detail_clientip");

    try {
      conn = ds.getConnection();
     // conn.setAutoCommit(false); // 수동커밋으로 전환

      String sql = " INSERT INTO TBL_PAYMENT_LOG_PRE ( ORDER_NO, USERNO, CHECKOUT, CLIENTIP  )"
          + "VALUES ( ?, ?, ?, ? ) ";

      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, pre_order_no);
      pstmt.setString(2, pre_userno);
      pstmt.setString(3, pre_checkout);
      pstmt.setString(4, pre_clientip);

      result = pstmt.executeUpdate();

    } finally {
      close();
    }
    return result;
  } // END OF prePayLog

  // detailPayLog
  // Detail Payment Log insert 하기
  @Override
  public int detailPayLog(Map<String, String> paraMap) throws SQLException {

    int result = 0;

    String pre_order_no = paraMap.get("pre_order_no");
    System.out.println("DETAIL pre_order_no : " + pre_order_no);
    String pre_userno = paraMap.get("pre_userno");
    String pre_checkout = paraMap.get("pre_checkout");
    String pre_clientip = paraMap.get("pre_clientip");

    String detail_fk_order_no = paraMap.get("detail_fk_order_no");
    String detail_fk_userno = paraMap.get("detail_fk_userno");
    String detail_option_code = paraMap.get("detail_option_code");
    String detail_qty = paraMap.get("detail_qty");
    String detail_clientip = paraMap.get("detail_clientip");

    try {
      conn = ds.getConnection();
      conn.setAutoCommit(false); // 수동커밋으로 전환

      String sql =
          " INSERT INTO TBL_PAYMENT_LOG_DETAIL ( FK_ORDER_NO, FK_USERNO, OPTION_CODE, QTY, CLIENTIP ) "
              + "VALUES ( ?, ?, ?, ?, ? ) ";

      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, pre_order_no);
      pstmt.setString(2, detail_fk_userno);
      pstmt.setString(3, detail_option_code);
      pstmt.setString(4, detail_qty);
      pstmt.setString(5, detail_clientip);

      result = pstmt.executeUpdate();

    } finally {
      close();
    }
    return result;
  } // END OF detailPayLog



  ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  // 해당 유저가 저장한 기본 배송지
  @Override
  public List<CardSlashVO> selectAddressList(String puserno) throws SQLException {

    List<CardSlashVO> addressList = new ArrayList<>();

    try {

      conn = ds.getConnection();

      String sql =
          " select ship_seq, receiverName, siteName, postcode, address, detailAddress, extraAddress, mobile, deliveryRequest, status "
              + " from tbl_shipping " + " where fk_userno = ? "
              + " and status = 1 order by status desc ";

      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, puserno);

      rs = pstmt.executeQuery();

      // System.out.println("DAO 주소 확인 0 : " + rs.getString(5));

      while (rs.next()) { // 행이 여러개나오니까 다음행이 없을때까지 반복

        String org_mobile = rs.getString(8);
        String aes_mobile = aes.decrypt(org_mobile);

        CardSlashVO svo = new CardSlashVO();
        svo.setShipNo(rs.getString(1));
        svo.setReceiverName(rs.getString(2));
        svo.setSiteName(rs.getString(3));
        svo.setPostcode(rs.getString(4));
        svo.setAddress(rs.getString(5));
        svo.setDetailAddress(rs.getString(6));
        svo.setExtraAddress(rs.getString(7));
        svo.setMobile(aes_mobile); // 휴대폰번호 복호화
        svo.setDeliveryRequest(rs.getString(9));
        svo.setStatus(rs.getString(10));
        svo.setUserno(puserno);

        addressList.add(svo); // 생성된 memberVO를 memberList에 담아준다.

      }

    } catch (GeneralSecurityException | UnsupportedEncodingException e) {
      e.printStackTrace();
    } finally {
      close();
    }

    return addressList;
  }

  // 구매 금액별 회원 포인트 업데이트
  @Override
  public int pointUpdate(String userno, int point) throws SQLException {
    
//    System.out.println("증가할 포인트 : " + point);

    int result = 0;

    try {
      conn = ds.getConnection();
      conn.setAutoCommit(false); // 수동커밋으로 전환

      String sql = " update tbl_member set point = point + ? where userno = ? ";

      pstmt = conn.prepareStatement(sql);
 
      pstmt.setInt(1, point);
      pstmt.setString(2, userno); 

      result = pstmt.executeUpdate();
      
    } finally {
      close();
    }
    return result;
  };

  // 결제한 물품을 장바구니에서 삭제
  
    @Override public int cartUpdate(String userno, String optioncode) throws SQLException {
    
  //  System.out.println("1 sCartNo : " + optioncode);
    
    int result = 0;
    
    try { conn = ds.getConnection();
    conn.setAutoCommit(false); // 수동커밋으로 전환
    
    String sql = " delete from tbl_cart where fk_userno = ? and FK_OPTIONCODE = ? ";
    
    pstmt = conn.prepareStatement(sql);
    
    pstmt.setString(1, userno); pstmt.setString(2, optioncode);
    
    
    result = pstmt.executeUpdate();
    
    } finally { close(); } return result; }
   
  
  @Override
  public void goRollback() throws SQLException {

    conn = ds.getConnection();
    conn.setAutoCommit(false); // 수동커밋으로 전환
    conn.rollback();
    
  };

  @Override
  public void goCommit() throws SQLException {

    conn = ds.getConnection();
    conn.setAutoCommit(false); // 수동커밋으로 전환
    conn.commit();
    
  };
  
//장바구니에서 상품을 개별 삭제해주는 메소드.
  @Override
  public int deleteOne(String Fk_userno, String cartno) throws SQLException{
        
        int result = 0;
        
        try {
           conn = ds.getConnection();
           //conn.setAutoCommit(false); // 수동커밋으로 전환
           
           String sql = " DELETE FROM TBL_CART " + 
                        " WHERE Fk_userno = ? AND FK_OPTIONCODE = ? ";
           
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

  // detail log insert
  @Override
  public int DetailLog(String orderno, String userno, String code, String qty, String clientip) throws SQLException {

//    System.out.println("DAO DEtail " + orderno);
//    System.out.println(userno);
//    System.out.println(code);
//    System.out.println(qty);
//    System.out.println(clientip);

    int result = 0;
    
    try {
       conn = ds.getConnection();
       
       String sql = " INSERT INTO TBL_PAYMENT_LOG_DETAIL ( FK_ORDER_NO, FK_USERNO, OPTION_CODE, QTY, CLIENTIP ) "+
           " VALUES ( ?, ?, ?, ?, ? ) ";
       
       pstmt = conn.prepareStatement(sql);
       
       pstmt.setString(1, orderno);
       pstmt.setString(2, userno);
       pstmt.setString(3, code);
       pstmt.setString(4, qty); 
       pstmt.setString(5, clientip);
       
       result = pstmt.executeUpdate();
       
    } catch( Exception e) {
       e.printStackTrace();
    } finally {
       close();
    }
    
    return result;
    
  }
  
//detail log insert
@Override
 public int stockUpdate(String option, String qty) throws SQLException {

   int result = 0;
   
   try {
      conn = ds.getConnection();
      
      String sql = " update TBL_OPTION set stock = stock - ? where optioncode = ? ";
      
      pstmt = conn.prepareStatement(sql);
      
      pstmt.setInt(1, Integer.parseInt(qty));
      pstmt.setString(2, option);
      
      result = pstmt.executeUpdate();
      
   } catch( Exception e) {
      e.printStackTrace();
   } finally {
      close();
   }
   
   return result;
   
 }



}
