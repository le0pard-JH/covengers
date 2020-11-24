package payment.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.MemberVO;
import member.model.ShippingAddressVO;
import product.model.CartDAO;
import product.model.CartVO;
import product.model.InterCartDAO;

public class CardSlashAction extends AbstractController {

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();
    MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

    // 로그인이 되어 있지 않으면
    if (loginuser.getUserno() == null) {
      super.setRedirect(false);
      super.setViewPage("/WEB-INF/myPage/myPage_emptyCart.jsp");
    } else {

      session.setAttribute("userno", loginuser.getUserno());
      String userno = loginuser.getUserno();

      // 장바구니 > cardSlash
      String spCartNo = request.getParameter("purchasecartno");
      String spOptionNo = request.getParameter("purchaseoption");
      String sumPrice = request.getParameter("sumPrice");
      String deliveryCharge = request.getParameter("deliveryCharge");
      String totalCost = request.getParameter("totalCost");
      
//      System.out.println("spCartNo : " + spCartNo);
//      System.out.println("spOptionNo : " + spOptionNo);
//      System.out.println("sumPrice : " + sumPrice);
//      System.out.println("deliveryCharge : " + deliveryCharge);
//      System.out.println("totalCost : " + totalCost);

      // 장바구니에서 보내준 String 카트 번호를 배열로 전환
      String[] arrcartno = spCartNo.split(",");

      // 장바구니 번호를 조회해서 List : cartnoList 에 저장
      List<String> cartnoList = new ArrayList<>();
      for (int i = 0; i < arrcartno.length; i++) {
        cartnoList.add(arrcartno[i]);
      }

      boolean flag = true;

      InterCardSlashDAO cardslashdao = new CardSlashDAO();
      List<CardSlashVO> csv = new ArrayList<CardSlashVO>();

      // cartnoList 의 정보를 조회해 보여준다
      for (int i = 0; i < cartnoList.size(); i++) {
        csv.add(cardslashdao.cardSelectList(loginuser.getUserno(), cartnoList.get(i)));
      }

      // 기존 장바구니 String 변환
      request.setAttribute("spCartNo", spCartNo);
      request.setAttribute("spOptionNo", spOptionNo);
      request.setAttribute("sumPrice", sumPrice);
      request.setAttribute("deliveryCharge", deliveryCharge);
      request.setAttribute("totalCost", totalCost); // totalCost
      

      request.setAttribute("CardSlashList", csv);
      request.setAttribute("detail_option_code", csv); // 옵션 코드 테스트 1

      // 주문번호를 생성하기 위한 함수 생성
      Calendar currentDate = Calendar.getInstance();
      int currentYear = currentDate.get(Calendar.YEAR);
      int currentM = currentDate.get(Calendar.MONTH) + 1;
      int currentD = currentDate.get(Calendar.DATE);
      String thisYear = Integer.toString(currentYear);
      thisYear = thisYear.substring(2);
      String thisMonth = Integer.toString(currentM);
      String thisDay = Integer.toString(currentD);
      String rand = Integer.toString(ThreadLocalRandom.current().nextInt(100000, 999999));
      
      String orderno = thisYear + thisMonth + thisDay + rand;
      

      // 회원 테이블에서 기본 배송지 조회
      List<CardSlashVO> addressList = cardslashdao.selectAddressList(userno);
      request.setAttribute("addressList", addressList);
      
//      System.out.println("Action orderno : " + orderno);
      
      // Pre & Detail SET UP
      request.setAttribute("pre_order_no", orderno);
      request.setAttribute("pre_userno", userno);
      request.setAttribute("pre_checkout", totalCost); // pre_checkout
      request.setAttribute("pre_checkout", totalCost); // pre_checkout
      

      super.setRedirect(false);
      super.setViewPage("/WEB-INF/pay/card_slash.jsp");
    }


  }

}
