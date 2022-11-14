package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;

public class BoardWriteAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setCharacterEncoding("UTF-8");
		//subject, content, userid=hong 값을 받아오자
		String subject=req.getParameter("subject");
		String content=req.getParameter("content");
		String userid="hong";
		
		//유효성체크
		if(subject==null||content==null||userid==null||subject.trim().isEmpty()) {
			this.setViewPage("boardWrite.do");
			this.setRedirect(true); //리다이렉트 방식으로 이동
			return;
		}
		BoardVO vo=new BoardVO(0, userid, subject, content, null, null, 0);
		
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		
		int n=dao.insertBoard(vo);
		
		String str=(n>0)?"글쓰기 성공":"실패";
		String loc=(n>0)?"boardList.do":"javascript:history.back()";
		
		req.setAttribute("msg", str);
		req.setAttribute("loc", loc);
		
		//뷰페이지 지정
		this.setViewPage("message.jsp");
		//이동방식 지정
		this.setRedirect(false); //forward
	}

}
