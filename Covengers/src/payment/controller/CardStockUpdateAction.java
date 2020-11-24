package payment.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.*;

public class CardStockUpdateAction extends AbstractController {

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();
    MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

    String method = request.getMethod();

    if (loginuser == null || method.equalsIgnoreCase("GET")) {
      super.setRedirect(true);
      super.setViewPage(request.getContextPath() + "/product/myCart.com");
    } else {

      String option = request.getParameter("option");
      String qty = request.getParameter("qty");
      
      System.out.println("stock option : " + option);
      System.out.println("stock qty : " + qty);
      
      String[] arroption = option.split(",");
      String[] arrqty = qty.split(",");
      
      List<String> optionList = new ArrayList<>();
      for (int i = 0; i < arroption.length; i++) {
        optionList.add(arroption[i]);
      }
      
      List<String> qtyList = new ArrayList<>();
      for (int i = 0; i < arrqty.length; i++) {
        qtyList.add(arrqty[i]);
      }
      
      boolean flag = true;
      
      InterCardSlashDAO cdao = new CardSlashDAO();
      for (int i = 0; i < optionList.size(); i++) {
          int n = cdao.stockUpdate( optionList.get(i), qtyList.get(i) );
          if (n == 0) {
              flag = false;
          }
      }
      
//      if (flag) System.out.println("재고 업데이트 성공");
//      else System.out.println("재고 업데이트 실패");
      
  
  }
  
}

}

