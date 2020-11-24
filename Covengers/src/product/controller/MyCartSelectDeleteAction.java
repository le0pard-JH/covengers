package product.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.MemberVO;
import payment.controller.CardSlashDAO;
import payment.controller.InterCardSlashDAO;
import product.model.*;

public class MyCartSelectDeleteAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		String method = request.getMethod();
		
		if ( loginuser == null || method.equalsIgnoreCase("GET") ) {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath() + "main.com");
		}else {
				
			String scartno = request.getParameter("scartno");
			
			String[] arrcartno = scartno.split(",");
			
			List<String> cartnoList = new ArrayList<>();
			for (int i = 0; i < arrcartno.length; i++) {
				cartnoList.add(arrcartno[i]);
			}
			
			boolean flag = true;
			
			InterCardSlashDAO cdao = new CardSlashDAO();
			for (int i = 0; i < cartnoList.size(); i++) {
				int n = cdao.deleteOne( loginuser.getUserno(), cartnoList.get(i) );
				if (n == 0) {
					flag = false;
				}
			}
			
			if (flag) System.out.println("삭제 성공");
			else System.out.println("삭제 실패");
			
		
		}
		
	}

}
