package user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import user.model.NotUserException;
import user.model.UserDAOMyBatis;
import user.model.UserVO;

public class LoginEndAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String userid=req.getParameter("userid");
		String pwd=req.getParameter("pwd");
		String saveId=req.getParameter("saveId");
		if(userid==null||pwd==null||userid.trim().isEmpty()||pwd.trim().isEmpty()) {
			this.setViewPage("login.do");
			this.setRedirect(true);
			return;
		}
		UserDAOMyBatis dao=new UserDAOMyBatis();
		try {
			UserVO loginUser=dao.loginCheck(userid, pwd);
			//loginuser가 null이 아니라면 회원 인증을 받은 것이므로 세션에 loginUser를 VO객체 통째로 저장.
			//session을 받아오는것이 핵심이다. 내장객체가 없기때문에 HttpSession으로부터 얻어온다.
			HttpSession session=req.getSession();
			if(loginUser!=null) {
				session.setAttribute("loginUser", loginUser);
				//아이디 저장 체크박스에 체크했다면 => 쿠키에 해당 아이디를 저장하자.
				Cookie ck=new Cookie("uid",loginUser.getUserid());
				if(saveId!=null) {
					ck.setMaxAge(7*24*60*60); //쿠키 7일간 저장 
				}else {
					ck.setMaxAge(0); //쿠키 저장 안하고 삭제
				}
				ck.setPath("/");
				res.addCookie(ck); //response에 포함시켜서 쿠키를 보내게 된다.
			}//이렇게 세션에 loginUser밸류를 "loginUser"키에 저장한다.
			this.setViewPage("index.do");
			this.setRedirect(true);
			
		}catch(NotUserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("loc", "javascript:history.back()");
			
			this.setViewPage("message.jsp");
			this.setRedirect(false);
		}
	}

}
