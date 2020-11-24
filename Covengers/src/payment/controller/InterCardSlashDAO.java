package payment.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import member.model.MemberVO;
import member.model.ShippingAddressVO;

public interface InterCardSlashDAO {

  // 장바구니에서 결제 페이지로 정보를 전송하는 메소드
  CardSlashVO cardSelectList(String fk_userno, String cartno) throws SQLException;

  // 기본 배송지 조회
  List<CardSlashVO> selectAddressList(String userno) throws SQLException;

  // 주문 내역 Pre Log Insert
  int prePayLog(Map<String, String> paraMap) throws SQLException;

  // 주문 내역 Detail Log Insert
  int detailPayLog(Map<String, String> paraMap) throws SQLException;

  // 구매 금액별 회원 포인트 업데이트
  int pointUpdate(String userno, int point) throws SQLException;

  // 결제한 물품을 장바구니에서 삭제
  int cartUpdate(String userno, String sCartNo) throws SQLException;

  void goCommit() throws SQLException;

  // 구매 목록을 카트에서 삭제
  int deleteOne(String Fk_userno, String cartno) throws SQLException;

  // DetailLog insert
  int DetailLog(String orderno, String optionno, String code, String qty, String clientip)
      throws SQLException;

  void goRollback() throws SQLException;

  // 재고 수량 업데이트
  int stockUpdate(String option, String qty) throws SQLException;



  // 결제전 유저가 결제하려고 선택한 상품의 리스트를 가져오는 메소드
  // List<CardSlashVO> prePayCartList(String fk_userno, String arrcartno) throws SQLException;



}
