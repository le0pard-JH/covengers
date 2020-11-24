package payment.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import product.model.*;

public class CardDetailLogAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		String method = request.getMethod();
		
		if ( loginuser == null || method.equalsIgnoreCase("GET") ) {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "/product/myCart.com");
		}else {
				
			String orderno = request.getParameter("orderno");
			String pre_order_no = request.getParameter("pre_order_no");
            String soption = request.getParameter("option");
            String sqty = request.getParameter("qty");
            
//            System.out.println("Detail orderno : " + orderno);
//            System.out.println("Detail pre_order_no : " + pre_order_no);
//            System.out.println("soption : " + soption);
//            System.out.println(sqty);
            
            String[] arroption = soption.split(",");
            String[] arrqty = sqty.split(",");
			
			List<String> optionList = new ArrayList<>();
			for (int i = 0; i < arroption.length; i++) {
			  optionList.add(arroption[i]);
			}
			
			List<String> qtyList = new ArrayList<>();
            for (int i = 0; i < arrqty.length; i++) {
              qtyList.add(arrqty[i]);
            }
            
            String clientip = "192.1.1.1";
			
			boolean flag = true;
			
			InterCardSlashDAO cdao = new CardSlashDAO();
			for (int i = 0; i < optionList.size(); i++) {
			  int n = cdao.DetailLog( orderno, loginuser.getUserno(), optionList.get(i), qtyList.get(i), clientip );
			//	int n = cdao.DetailLog( loginuser.getUserno(), optionList.get(i) );
				if (n == 0) {
					flag = false;
				}
			}
			
//			if (flag) System.out.println("detail 성공");
//			else System.out.println("detail 실패");
			
		
		}
		
	}

}
