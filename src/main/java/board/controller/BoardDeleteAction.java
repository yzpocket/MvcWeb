package board.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
import user.model.UserVO;

public class BoardDeleteAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 글삭제 작성자 맞는지 보기 1 세션얻어오기
		HttpSession session=req.getSession();
		//세션으로부터 로그인된 loginUser통해서 userVO얻기
		UserVO user=(UserVO)session.getAttribute("loginUser");
		if(user==null) {
			req.setAttribute("msg", "로그인해야 글삭제가 가능해요");
			req.setAttribute("loc", "javascript:history.back()");
			
			this.setViewPage("message.jsp");
			this.setRedirect(false);
			return;
		}
		
		
		
//		[1] 삭제할 글번호 받기
		String numStr=req.getParameter("num");
		
//		[2] 유효성 체크 => boardList.do로 redirect이동
		if(numStr==null||numStr.trim().isEmpty()) {
			this.setViewPage("boardList.do");//널일때 갈 위치 지정
			this.setRedirect(true); //리다이렉트 방식(true)으로 이동
			return;
		}
//		[3] dao의 deleteBoard(num)
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		
//글삭제시 첨부파일도 서버(upDir)에서 삭제하는것 추가------------------------------------------
		//[1]db에서 해당 글 가져오기
		BoardVO vo=dao.viewBoard(Integer.parseInt(numStr.trim()));
		// 삭제글 작성 유저와 로그인 유저가 맞는지 체크하는것 추가-------------------
		if(!vo.getUserid().equals(user.getUserid())) {
			req.setAttribute("msg", "글쓴 사람으로 로그인 해야 글삭제가 가능해요");
			req.setAttribute("loc", "javascript:history.back()");
			
			this.setViewPage("message.jsp");
			this.setRedirect(false);
			return;
		}
		if(vo.getFilename()!=null) { //첨부파일이 있다면
			//서버 /Upload디렉토리에서
			String upDir=req.getServletContext().getRealPath("/Upload");
			//해당 파일을 찾아와서 삭제 대상으로 만들고
			File delFile=new File(upDir, vo.getFilename());
			if(delFile!=null) {//삭제 대상 파일이 있다면
				delFile.delete();//그 파일을 delete()를 수행해서 삭제하자.
			}
		}
		
//----------------------------------------------------------------------------------
		int n=dao.deleteBoard(Integer.parseInt(numStr.trim())); //numStr을 다시 num타입으로 변환해서 해당 글을 삭제
		
//		[4] 실행결과 메시지 및 이동 경로 지정
//		   => req에 저장. msg, loc
		String str=(n>0)?"글 삭제 성공":"실패";
		String loc="boardList.do";
		
		req.setAttribute("msg", str);
		req.setAttribute("loc", loc);
	
		
//		[5] 뷰페이지 지정/이동방식 지정
//		board/boardView.jsp
		//뷰페이지 지정
		this.setViewPage("message.jsp");
		//이동방식 지정
		this.setRedirect(false); //forward
	
	}

}
