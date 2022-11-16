package board.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest; //cos.jar를 프로젝트/WEB-INF/lib아래 추가함
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.controller.AbstractAction;

public class UploadEndAction2 extends AbstractAction {
	
	String upDir="/Users/inyongkim/Documents";
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//컨텐트 타입, 파일 크기
		//cos.jar넣고 아래 MultipartRequest()객체를 생성하고 임포트하면 된다.
		//()안 매개변수에
		//upDir			로 위에 선언한 경로로 파일을 저장 할 수 있다.(경로를 바꾸면 됨)
		//"utf-8"		로 한글깨짐 오류 해결 가능
		//100*1024*1024 	로 용량제한 할 수 있음 (현재는 100MB로 설정)
		//디폴트는 파일을 덮어쓰게 된다. 덮어쓰지 못하게 하려면,
		//new DefaultFileRenamePolicy() 로 업로드한 파일이 동일한 경우, 파일명+인덱스+확장자 인덱스번호를 붙여서 덮어쓰지 못하게 한다.
		MultipartRequest mr=new MultipartRequest(req, upDir, 100*1024*1024, "UTF-8", new DefaultFileRenamePolicy());
		System.out.println("파일 업로드 성공: "+upDir+"에서 확인하세요");
		
		//올린이 이름을 받아와보자.
		//String name=req.getParameter("name"); //올린이 이름받아오려면 이렇게 받으면 안된다.[X]
		String name=mr.getParameter("name");
		System.out.println("name: "+name);
		
		//첨부파일명을 받아와보자.
		//String fname=mr.getParameter("fname"); //파일명은 또 이렇게하면 안된다.[X]
		String fname=mr.getFilesystemName("fname"); //이렇게 받아와야 한다.
		System.out.println("fname: "+fname);
		
		//첨부파일크기를 받아와보자.
		File file=mr.getFile("fname"); //file타입으로 첨부된 파일을 변환해서.
		long fsize=0;
		if(file!=null) { //첨부했다면 체크하고 
			fsize=file.length(); //length가 파일 크기를 말해준다.
		}
		
		req.setAttribute("content", "파일 업로드 성공: "+upDir+"에서 확인하세요");
		req.setAttribute("name", name); //이름 저장
		req.setAttribute("fname", fname); //파일이름 저장
		req.setAttribute("fsize", fsize); //파일 크기 저장
		
		
		this.setViewPage("board/uploadResult.jsp");
		this.setRedirect(false);
	}

}
