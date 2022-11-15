package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;

public class BoardEditFormAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//1. 수정할 글번호 받기
		String numStr=req.getParameter("num"); //히든으로 넘어온 num

		//2. 유효성 체크
		if(numStr==null||numStr.trim().isEmpty()) {
			this.setViewPage("boardList.do");//널일때 갈 위치 지정
			this.setRedirect(true); //리다이렉트 방식(true)으로 이동
			return;
		}
		//3. DAO를 통해서 viewBoard(num)을 받아서 BoardVO에 전달
		BoardDAOMyBatis dao=new BoardDAOMyBatis(); //DAO객체생성
		int num=Integer.parseInt(numStr.trim()); //numStr을 다시 num타입으로 변환
		BoardVO vo=dao.viewBoard(num);
		//4. req에 저장 키값 "board"
		req.setAttribute("board", vo);
		
		//5. viewPage, 전달방식 지정
		this.setViewPage("board/boardEdit.jsp");
		this.setRedirect(false);
		
	}

}
