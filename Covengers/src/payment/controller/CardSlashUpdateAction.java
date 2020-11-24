package payment.controller;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.*;
import product.model.*;

public class CardSlashUpdateAction extends AbstractController {

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    HttpSession session = request.getSession();
    MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
    String method = request.getMethod();

    String sCartNo = null;
    String order_no = request.getParameter("pre_order_no");
    String userno = loginuser.getUserno();

    // Pre SET UP
    String pre_order_no = request.getParameter("pre_order_no");
    String pre_userno = request.getParameter("pre_userno");
    String pre_checkout = request.getParameter("pre_checkout");
    String pre_clientip = request.getRemoteAddr();

    System.out.println("Pre pre_order_no : " + pre_order_no);

    // Detail SET UP
    String detail_fk_order_no = pre_order_no;
    String detail_fk_userno = pre_userno;
    String detail_option_code = request.getParameter("detail_option_code"); // option tag required
    String detail_qty = request.getParameter("detail_qty"); // qty tag required
    String detail_clientip = pre_clientip;

    // Pre Map SET
    Map<String, String> paraMap = new HashMap<>();
    paraMap.put("pre_order_no", pre_order_no);
    paraMap.put("pre_userno", pre_userno);
    paraMap.put("pre_checkout", pre_checkout);
    paraMap.put("pre_clientip", pre_clientip);

    // System.out.println("[Update Pre]");
    // System.out.println("[Update] pre_order_no : " + pre_order_no);
    // System.out.println("[Update] pre_userno : " + pre_userno);
    // System.out.println("[Update] pre_checkout : " + pre_checkout);
    // System.out.println("[Update] pre_clientip : " + pre_clientip);
    // System.out.println("[END]");

    // Detail Map SET
    paraMap.put("detail_fk_order_no", detail_fk_order_no);
    paraMap.put("detail_fk_userno", detail_fk_userno);
    paraMap.put("detail_option_code", detail_option_code);
    paraMap.put("detail_qty", detail_qty);
    paraMap.put("detail_clientip", detail_clientip);

    // System.out.println("[Update Detail]");
    // System.out.println("[Update] detail_fk_order_no : " + detail_fk_order_no);
    // System.out.println("[Update] detail_fk_userno : " + detail_fk_userno);
    // System.out.println("[Update] detail_option_code : " + detail_option_code);
    // System.out.println("[Update] detail_qty : " + detail_qty);
    // System.out.println("[Update] detail_clientip : " + detail_clientip);
    // System.out.println("[END]");

    if (loginuser == null || method.equalsIgnoreCase("GET")) {
      super.setRedirect(true);
      super.setViewPage(request.getContextPath() + "/product/myCart.com");
    } else {

      // Pre Insert
      InterCardSlashDAO cardDAO = new CardSlashDAO();
      int preRes = cardDAO.prePayLog(paraMap);
 //     System.out.println("Update 결제 성공여부 : " + preRes);

      if (preRes == 1) {
        // int detailRes = cardDAO.DetailLog(pre_order_no, optionno, code, qty, clientip)

        String scartno = request.getParameter("scartno");

        // String[] arrcartno = scartno.split(",");
        String[] arrcartno = null;

        // List<String> cartnoList = new ArrayList<>();
        // for (int i = 0; i < arrcartno.length; i++) {
        // cartnoList.add(arrcartno[i]);
        // }

        boolean flag = true;

        // InterCardSlashDAO cdao = new CardSlashDAO();
        // for (int i = 0; i < cartnoList.size(); i++) {

        // int n = cdao.detailPayLog(paraMap);
        // int n = cdao.deleteOne( loginuser.getUserno(), cartnoList.get(i) );
        // if (n == 0) {
        // flag = false;
        // }
        // }

//        if (flag)
//          System.out.println("삭제 성공");
//        else
//          System.out.println("삭제 실패");


      }

      String message = "";
      String loc = "";
      if (preRes == 1) {
        message = loginuser.getName() + "결제 성공";
        loc = request.getContextPath() + "/main.com";
      } else {
        message = "결제 실패";
        loc = "javascript:history.back()";
      }


      request.setAttribute("message", message);
      request.setAttribute("loc", loc);

      super.setRedirect(false);
      super.setViewPage("/WEB-INF/msg.jsp");
    }
  }

}
