package board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.model.BoardDAOMyBatis;
import board.model.BoardVO;
import common.controller.AbstractAction;
import user.model.UserVO;

public class BoardWriteAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		// [2-1] 파일 업로드 처리 ==> 업로드 디렉토리의 절대경로를 얻어오자. MvcWeb//src/main/webapp/Upload <<의 실제 경로.
		ServletContext application=req.getServletContext(); //절대경로를 얻어 올 수 있는 클래스
		String upDir=application.getRealPath("/Upload"); //webapp"/Upload"의 실제 경로를 대체해서 받아 온다.
		System.out.println("upDir '/Upload'의 실제 경로"+upDir);
		
		// 글쓰기 작성자 바꾸기1 세션얻어오기
		HttpSession session=req.getSession();
		//세션으로부터 로그인된 loginUser통해서 userVO얻기
		UserVO user=(UserVO)session.getAttribute("loginUser");
		if(user==null) {
			req.setAttribute("msg", "로그인해야 글쓰기가 가능해요");
			req.setAttribute("loc", "javascript:history.back()");
			
			this.setViewPage("message.jsp");
			this.setRedirect(false);
			return;
		}
		
		
		MultipartRequest mr=null;
		try {
			mr=new MultipartRequest(req, upDir, 100*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
			System.out.println("파일 업로드 성공!!"); 
		}catch(IOException e) {
			e.printStackTrace();
		}
//  -----------------------업로드 처리를 MultipartRequest 과정으로 바꿨기 때문에 아래 req.getParameter부분은 mr.으로 대체된다.
		
		// [0] post방식일때는 한글처리 부터 
//		req.setCharacterEncoding("UTF-8");
		// [1] subject, content, userid=hong 값을 받아오자
//		String subject=req.getParameter("subject");
//		String content=req.getParameter("content");
//		String userid="hong";
//		String filename=null;
//		long filesize=0;
		String subject=mr.getParameter("subject");
		String content=mr.getParameter("content");
		//세션에서 UserVO통해 받은 user로 변경했음.
		String userid=user.getUserid();
		String filename=mr.getFilesystemName("filename");
		File file=mr.getFile("filename");
		long filesize=0;
		if(file!=null) {
			filesize=file.length();
		}
		
		//유효성체크
		if(subject==null||content==null||userid==null||subject.trim().isEmpty()) {
			this.setViewPage("boardWrite.do");
			this.setRedirect(true); //리다이렉트 방식으로 이동
			return;
		}
		// [2-1] num은 int로 형변환해주는데 num은 시퀀스가 전달할예
		// [3] 1번에서 받은 값 BoardVO에 담아주기
		
//  -----------------------업로드 처리를 MultipartRequest 과정으로 바꿨기 때문에 아래 vo부분의 파일관련 null부분은 위 얻어온 filename, filesize대체된다.

		BoardVO vo=new BoardVO(0, userid, subject, content, null, filename, filesize);
		
		// [4] dao의 updateBoard(vo)
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
