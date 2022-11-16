package board.controller;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;

public class UploadEndAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//컨텐트 타입, 파일크기
		String ctype=req.getContentType();
		long len=req.getContentLengthLong();
//		int len2=req.getContentLength(); //int로도되는데 파일크기가 커서 long으로 많이함
		
		//첨부파일=> post타입이라서 =>request body에 포함되어 오는 파일 데이터를 스트림 연결해서 읽은 후
		//브라우저에 출력해보자.
		ServletInputStream in=req.getInputStream();
		
		byte[] data=new byte[1024]; //배열에 담기 위해 만들고
		int n=0, count=0, total=0;
		String content="<xmp>"; // xmp는 소스 보기 태그다
		while((n=in.read(data))!=-1) { //req끝에 도달 할때까지
			String str=new String(data, 0, n); //0부터 읽은 갯수까지 문자로 만들고
			content+=str; //content에 읽은 문자들을 누적넣는다.
			count++;
			total+=n;
		}
		content+="</xmp>";
		
		//넣은 것들 저장
		req.setAttribute("ctype", ctype);
		req.setAttribute("len", len);
		req.setAttribute("content", content);
		req.setAttribute("count", total);
		
		in.close();
		
		this.setViewPage("board/uploadResult.jsp");
		this.setRedirect(false);
	}

}
