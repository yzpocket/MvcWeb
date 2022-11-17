package user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;

public class LogoutAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//로그아웃 처리방법
		//session을 받아오는것이 핵심이다. 내장객체가 없기때문에 HttpSession으로부터 얻어온다.
		HttpSession session=req.getSession();
		//[1] 세션에 저장된 모든 변수를 제거하는 방법 => 권장
		session.invalidate();			
		this.setViewPage("index.do");
		this.setRedirect(true);
	}

}
