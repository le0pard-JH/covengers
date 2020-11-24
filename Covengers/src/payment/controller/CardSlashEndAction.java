package payment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;

public class CardSlashEndAction extends AbstractController {

  @Override
  public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    // 아임포트 결제창을 사용하기 위한 전제조건은 먼저 로그인을 해야 하는 것이다.
    if (super.checkLogin(request)) {
      // 로그인을 했으면

      String userno = request.getParameter("userno");

      HttpSession session = request.getSession();
      MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");

      // 유져가 로그인 후 결제를 시도하는 경우
      if (loginuser.getUserno().equals(userno)) {

        String cardSlashTotal = request.getParameter("total_amount");
        String email = loginuser.getEmail();
        String name = loginuser.getName();
        String mobile = loginuser.getMobile();
        String scartno = request.getParameter("scartno");
        String purchasecartno = request.getParameter("purchasecartno");
       
        request.setAttribute("cardSlashTotal", cardSlashTotal);
        request.setAttribute("email", loginuser.getEmail());
        request.setAttribute("name", loginuser.getName());
        request.setAttribute("userid", userno);
        request.setAttribute("mobile", mobile);
        request.setAttribute("scartno", scartno);
        request.setAttribute("purchasecartno", purchasecartno);
        
//        System.out.println("END userno : " + userno);
//        System.out.println("END email : " + email);
//        System.out.println("END name : " + name);
//        System.out.println("END cardSlashTotal : " + cardSlashTotal);
//        System.out.println("END mobile : " + mobile);
//        System.out.println("END scartno : " + scartno);
//        System.out.println("END purchasecartno : " + purchasecartno);

        // super.setRedirect(false);
        super.setViewPage("/WEB-INF/pay/paymentGateway.jsp");
      } else {
        // 로그인이 되어 있지 않은 경우 혹은 다른 사용자로 접근 시도 하는 경우
        String message = "비정상적인 접근 : 다른 사용자 접근";
        String loc = "javascript:history.back()";

        request.setAttribute("message", message);
        request.setAttribute("loc", loc);

        // super.setRedirect(false);
        super.setViewPage("/WEB-INF/msg.jsp");
        return;
      }
    } else {
      // 로그인이 되어 있지 않으면
      String message = "비정상적인 접근 : 로그인 필요";
      String loc = "javascript:history.back()";

      request.setAttribute("message", message);
      request.setAttribute("loc", loc);

      // super.setRedirect(false);
      super.setViewPage("/WEB-INF/msg.jsp");
    }

  }

}
