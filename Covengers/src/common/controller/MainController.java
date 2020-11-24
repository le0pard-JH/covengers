package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController extends AbstractController {

	@Override
	public String toString() {
		return "### 클래스 MainController 의 인스턴스 메소드 toString() 호출";
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("### 확인용 MainController 인스턴스 메소드 excute 호출");

		super.setRedirect(true);
		super.setViewPage("index.up"); // /MyMVC/index.up

	}

}
