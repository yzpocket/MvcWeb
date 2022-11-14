package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;

public class BoardWriteFormAction extends AbstractAction {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
	throws Exception{
		System.out.println("BoardWriteFormAction의 execute()호출됨.");
		req.setAttribute("msg", "안녕 BoardWriteForm");
		
		//뷰페이지 지정
		this.setViewPage("/board/boardWrite.jsp");
		//이동방식 지정
		this.setRedirect(false); //forward
	}
}
