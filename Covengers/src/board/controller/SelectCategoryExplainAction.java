package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import board.model.BoardDAO;
import board.model.InterBoardDAO;
import common.controller.AbstractController;

public class SelectCategoryExplainAction extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		super.header(request);

		String category = request.getParameter("category");
		
		InterBoardDAO bdao = new BoardDAO();
		
		// 사용자가 고른 카테고리의 값에 따라 설명을 조회(select)해오는 메서드
		String explain = bdao.selectCateExplain(category);
		
		explain = explain.replaceAll("<br>", "\r\n");
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("explain", explain);
		
		String json = jsonObj.toString(); // JSON 형식으로 String화 한다.
		System.out.println(json);
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
