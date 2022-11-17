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
			String userid=req.getParameter("userid");
			
			UserDAOMyBatis dao=new UserDAOMyBatis();
			
			boolean isUse=dao.idCheck(userid);
			
			String result=(isUse)?"ok":"no";
			
			req.setAttribute("result", result);
			req.setAttribute("userid", userid);
			
			this.setViewPage("member/idCheckResult.jsp"); //위 req에서받은건 여기서 사용 할 수 있다.
			this.setRedirect(false);
		}
		
	}

}
