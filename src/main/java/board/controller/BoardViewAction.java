package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;

public class BoardViewAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
//		[1] 글번호 받기
		String numStr=req.getParameter("num");
		
//		[2] 유효성 체크 => boardList.do로 redirect이동
		if(numStr==null||numStr.trim().isEmpty()) {
			this.setViewPage("boardList.do");//널일때 갈 위치 지정
			this.setRedirect(true); //리다이렉트 방식(true)으로 이동
			return;
		}
//		[3] dao의 viewBoard(num) 호출==>BoardVO를 반환
		int num=Integer.parseInt(numStr.trim()); //numStr을 다시 num타입으로 변환
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		BoardVO vo=dao.viewBoard(num);
		//=> req에 저장하기=> "board"라는 key값으로
		req.setAttribute("board", vo);
	
		
//		[4] 뷰페이지 지정/이동방식 지정
//		board/boardView.jsp
		//뷰페이지 지정
		this.setViewPage("board/boardView.jsp");
		//이동방식 지정
		this.setRedirect(false); //forward
	}

}
