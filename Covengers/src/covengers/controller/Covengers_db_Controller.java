package covengers.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractController;

public class Covengers_db_Controller extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("name", "황주호");
		request.setAttribute("name1", request.getContextPath());
		super.setViewPage("/WEB-INF/covengers_db.jsp");
		
	}

}
