package user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import user.model.UserDAOMyBatis;

public class IdCheckAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String method=req.getMethod();
		if(method.equalsIgnoreCase("get")) { //get방식일때
			
			this.setViewPage("member/idCheck.jsp");
			this.setRedirect(false);
		}else { 							//post방식일때
			String userid=req.getParameter("userid");//아이디를 받고
			
			UserDAOMyBatis dao=new UserDAOMyBatis();//아이디를 db에 넘기면
			
			boolean isUse=dao.idCheck(userid);//dao.idCheck에 따라이것이 맞으면 
			
			String result=(isUse)?"ok":"no";//isUse가 맞으면 ok하고 아래로, 아니면 no
			
			req.setAttribute("result", result); //result에 ok또는 no가 저장
			req.setAttribute("userid", userid); //그 id는 저장.
			
			this.setViewPage("member/idCheckResult.jsp"); //위 req에서받은건 여기서 사용 할 수 있다.
			this.setRedirect(false);
		}
		
	}

}
