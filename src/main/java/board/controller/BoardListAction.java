package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import common.controller.AbstractAction;

public class BoardListAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		int count=dao.getTotalCount();
		
		req.setAttribute("totalCount", count);
		
		//뷰페이지 지정
		this.setViewPage("board/boardList.jsp");
		//이동방식 지정
		this.setRedirect(false); //forward
	}

}
