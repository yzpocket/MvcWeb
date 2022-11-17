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

public class BoardEditAction extends AbstractAction {

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
		
		
		
		
		// [2-1] 파일 업로드 처리 ==> 업로드 디렉토리의 절대경로를 얻어오자. MvcWeb//src/main/webapp/Upload <<의 실제 경로.
		ServletContext application=req.getServletContext(); //절대경로를 얻어 올 수 있는 클래스
		String upDir=application.getRealPath("/Upload"); //webapp"/Upload"의 실제 경로를 대체해서 받아 온다.
		System.out.println("upDir '/Upload'의 실제 경로"+upDir);
		
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
		
		// [1] num, userid, subject, content, filename 값 받기
//		String numStr=req.getParameter("num");
//		String userid="hong";
//		String subject=req.getParameter("subject");
//		String content=req.getParameter("content");
//		String filename=null;
//		long filesize=0;
		String numStr=mr.getParameter("num");
		String subject=mr.getParameter("subject");
		String content=mr.getParameter("content");
		//세션에서 UserVO통해 받은 user로 변경했음.
		String userid=user.getUserid();
		String filename=mr.getFilesystemName("filename");
		File file=mr.getFile("filename");
		long filesize=0;
		if(file!=null) {//첨부한 파일이 있다면
			filesize=file.length();
			//예전에 첨부한 파일명 얻기
			String old_file=mr.getParameter("old_file");
			if(old_file!=null&& !old_file.trim().isEmpty()) {//예전에 첨부한 파일이 있다면
				//서버에서 예전 파일을 지우자
				File delFile=new File(upDir, old_file); //예전 파일 객체를 생성해서
				if(delFile!=null) { //이것이 있다면
					boolean b=delFile.delete(); //삭제 delete()를 실행하자
					System.out.println("파일 삭제 여부: "+b); //삭제 되었는지 boolean을 보자.
				}
			}
		}
		
		// [2] 유효성 체크 (num, subject,userid)
		if(numStr==null||subject==null||userid==null||
				numStr.trim().isEmpty()||subject.trim().isEmpty()||userid.trim().isEmpty()) {
			this.setViewPage("boardList.do");
			this.setRedirect(true); //리다이렉트 방식으로 이동
			return;
		}
		// [2-1] num은 int로 형변환해준다.
		int num=Integer.parseInt(numStr.trim());
		// [3] 1번에서 받은 값 BoardVO에 담아주기
		BoardVO vo=new BoardVO(num, userid, subject, content, null, filename, filesize);
		
		// [4] dao의 updateBoard(vo)
		BoardDAOMyBatis dao=new BoardDAOMyBatis();
		
		// [5] req에 메시지, 이동경로 저장
		int n=dao.updateBoard(vo);
		
		String str=(n>0)?"글수정 성공":"실패";
		String loc="boardList.do";
		
		req.setAttribute("msg", str);
		req.setAttribute("loc", loc);
		
		//뷰페이지 지정
		this.setViewPage("message.jsp");
		//이동방식 지정
		this.setRedirect(false);
	}

}
