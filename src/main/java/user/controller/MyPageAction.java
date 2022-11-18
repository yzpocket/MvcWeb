package user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
public class MyPageAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		// "/user/myPage.do" 로 맵핑되었다 ==> "/member/myPage.jsp"가 물리적 경로
		this.setViewPage("../member/myPage.jsp"); //
		this.setRedirect(false);
	}

}
