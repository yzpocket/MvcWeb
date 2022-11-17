package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
import user.model.UserVO;

public class BoardEditFormAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// 글수정 작성자 맞는지 보기 1 세션얻어오기
		HttpSession session=req.getSession();
		//세션으로부터 로그인된 loginUser통해서 userVO얻기
		UserVO user=(UserVO)session.getAttribute("loginUser");
		if(user==null) {
			req.setAttribute("msg", "로그인해야 글수정이 가능해요");
			req.setAttribute("loc", "javascript:history.back()");
			
			this.setViewPage("message.jsp");
			this.setRedirect(false);
			return;
		}
		
		
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
		
		// 수정글 작성 유저와 로그인 유저가 맞는지 체크하는것 추가-------------------
		if(!vo.getUserid().equals(user.getUserid())) {
			req.setAttribute("msg", "글쓴 사람으로 로그인 해야 글수정이 가능해요");
			req.setAttribute("loc", "javascript:history.back()");
			
			this.setViewPage("message.jsp");
			this.setRedirect(false);
			return;
		}
		
		//4. req에 저장 키값 "board"
		req.setAttribute("board", vo);
		
		//5. viewPage, 전달방식 지정
		this.setViewPage("board/boardEdit.jsp");
		this.setRedirect(false);
		
	}

}
