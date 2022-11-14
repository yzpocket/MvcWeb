package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SampleAction extends AbstractAction{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
	throws Exception{
		System.out.println("SampleAction의 execute()호출됨.");
		
		req.setAttribute("msg", "From SampleAction 안녕 Sample");
		
		//뷰페이지 지정
		this.setViewPage("/template.jsp");
		
		//이동방식 지정
		this.setRedirect(false); //forward
	}
}
