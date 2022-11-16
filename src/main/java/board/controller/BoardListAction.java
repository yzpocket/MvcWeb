package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;

public class BoardListAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
//페이징 처리로 추가되는 작업--------[6]-------------------------------

		//[6] 현재 보여줄 페이지(cpage) 값 받아오기.
		String cpStr=req.getParameter("cpage");
		if(cpStr==null||cpStr.trim().isEmpty()) { //cpage가 널이라면.
			cpStr="1"; // 1페이지를 기본 값으로 설정
		}//잘넘어왔으면 그 값을 cpage값으로 선언
		int cpage=Integer.parseInt(cpStr.trim());
		if(cpage<1){ //cpage가 널이라면.
			cpage=1; // 1페이지를 기본 값으로 설정
		}
//페이징 처리로 추가되는 작업--------[6] 아래 6-1,6-2 ... 이동------------------------------
		
		
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
//페이징 처리로 추가되는 작업----------------------------------------
		//[1] 총 게시글 수 구하기---------
		int totalCount=dao.getTotalCount();
		//[2] 한 페이지당 보여줄 목록 개수 정하기
		int pageSize=5; //5개씩나오게 지정해봤다.
		//[3] 페이지 수 구하기
		/*
		 * totalCount		// pageSize		//	pageCount
			1~4/5개					5개					1
			6~9/10					5개					2
			1-14\15					5개					3

			처럼 나타난다 그래서

			if(totalCount%pageSize==0){
				pageCount=totalCount/pageSize;
			}else{
				pageCount=totalCount/pageSize +1; 을해주면 됨.
			}
			
			하지만 간단히
			
			pageCount=(totalCount-1)/pageSize +1; 과 같다.
		 * 
		 * */
		int pageCount=(totalCount-1)/pageSize +1;
		
		if(pageCount<=0) {
			pageCount=1;
		}
		//[6-1]
		if(cpage>pageCount) { //cpage가 pageCount보다 크면
			cpage=pageCount;//마지막 페이지로 지정.
		}
		//[6-2]구간의 시작값과 끝 값을 변수로 선언한다.
		int end=cpage * pageSize;
		int start= end-(pageSize-1);
		
		
		
		//[4] 페이지 수 만큼 jsp(=>boardList.jsp)페이지로 이동 
		//페이지 네비게이션을 만든다. 1,2 , ... 이렇게.. jsp에서 링크를 생성[4],[5] 완료 했다면. 맨위에서 [6]번 진행 
		
		List<BoardVO> boardArr=dao.listBoard(start, end);
		
		req.setAttribute("boardArr", boardArr);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pagesize", pageSize);
		req.setAttribute("pageCount", pageCount);
		//[6-4] 현재페이지 추가.
		req.setAttribute("cpage", cpage);
		
		//뷰페이지 지정
		this.setViewPage("board/boardList.jsp");
		//이동방식 지정
		this.setRedirect(false); //forward
	}

}
