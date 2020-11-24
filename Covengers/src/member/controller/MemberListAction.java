package member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractController;
import member.model.InterMemberDAO;
import member.model.MemberDAO;
import member.model.MemberVO;

public class MemberListAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// System.out.println("컨트롤러 성공");
		
		InterMemberDAO mdao = new MemberDAO();
		
		List<MemberVO> mList = mdao.selectAllMember();
		
		request.setAttribute("mList", mList);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/admin/usertables.jsp");
	}
	
	
}
