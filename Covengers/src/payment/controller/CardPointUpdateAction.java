package payment.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.*;

public class CardPointUpdateAction extends AbstractController {

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();
    MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

    String method = request.getMethod();

    if (loginuser == null || method.equalsIgnoreCase("GET")) {
      super.setRedirect(true);
      super.setViewPage(request.getContextPath() + "/product/myCart.com");
    } else {

      String userno = loginuser.getUserno();
      int point = Integer.parseInt(request.getParameter("point"));

      System.out.println(userno);
      System.out.println(point);

      boolean flag = true;

      InterCardSlashDAO cdao = new CardSlashDAO();
      int n = cdao.pointUpdate(userno, point);

      if (n == 0) {
        flag = false;
      }

      if (flag) {
       // System.out.println("point 성공"); 
        }
      else {
       // System.out.println("detail 실패"); 
      }


    }

  }

}
