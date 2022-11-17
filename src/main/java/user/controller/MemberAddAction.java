package user.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;
import user.model.UserDAOMyBatis;
import user.model.UserVO;

public class MemberAddAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setCharacterEncoding("UTF-8");
		//[1]이름, 아이디, 비밀번호, 연락처, 우편번호, 주소1, 주소2
		String name=req.getParameter("name");
		String userid=req.getParameter("userid");
		String pwd=req.getParameter("pwd");
		String hp1=req.getParameter("hp1");
		String hp2=req.getParameter("hp2");
		String hp3=req.getParameter("hp3");
		String post=req.getParameter("post");
		String addr1=req.getParameter("addr1");
		String addr2=req.getParameter("addr2");
		//[2]유효성 체크 => joinForm.do로 보내기
		if(name==null||userid==null||pwd==null||name.trim().isEmpty()||userid.trim().isEmpty()||pwd.trim().isEmpty()) {
			this.setViewPage("joinForm.do");
			this.setRedirect(true); //리다이렉트 방식으로 이동
			return;
		}
		//[3]UserVO에 담아주기
		UserVO vo=new UserVO(0, name, userid, pwd, hp1, hp2, hp3, post, addr1, addr2, null, 1000, 0, "");

		//[4]UserDAOMyBatis의 insertUser(vo)
		UserDAOMyBatis dao=new UserDAOMyBatis();
		int n=dao.insertUser(vo);

		//[5]회원가입 성공, 실패 후 정하고 성공시 로그인 페이지 이동
		String str=(n>0)?"회원 가입 성공-로그인 페이지로 이동":"가입 실패";
		String loc=(n>0)?"Login.do":"javascript:history.back()";
		
		req.setAttribute("msg", str);
		req.setAttribute("loc", loc);
		
		//뷰페이지 지정
		this.setViewPage("message.jsp");
		//이동방식 지정
		this.setRedirect(false);
	}

}
