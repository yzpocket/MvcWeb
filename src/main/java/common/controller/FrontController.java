package common.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*FrontController : *.do 패턴의 모든 요청을 받아들인다.
 * - Command.properties 파일에 있는 매핑 정보를 읽어들여 해당 요청uri와 매핑되어 있는
 *   SubController(XXXAction)을 찾아 객체화 한 뒤 해당 객체의 메소드(execute)를 호출한다.
 * - 서브 컨트롤러는 해당 작업을 수행한 뒤에 다시 FrontController로 돌아와 보여줘야 할 View
 *   페이지(JSP) 정보를 넘긴다.
 * - FrontController는 해당 뷰페이지로 이동시킨다. (forward방식 이동 or redirect방식 이동)    
 * */

@WebServlet(
		urlPatterns = { "*.do" }, 
		initParams = { 
				@WebInitParam(name = "config", 
						value = "/Users/inyongkim/Documents/myjava/Workspace/MvcWeb/src/main/webapp/WEB-INF/Command.properties")
		})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private HashMap<String, Object> cmdMap=new HashMap<>();
    
	@Override
	public void init(ServletConfig conf) throws ServletException{
		System.out.println("init()호출됨..");
		String props=conf.getInitParameter("config");
		System.out.println("props=="+props);
		//java day10 PropertiesTest에서 했던 내용 그대로
		Properties pr=new Properties();
		try {
			FileInputStream fis=new FileInputStream(props);
			pr.load(fis);
			//Command.properties 파일 정보를 Properties 객체로 옮긴다. ********
			if(fis!=null) fis.close();
//			String val=pr.getProperty("/index.do");
//			System.out.println("val="+val);
			Set<Object> set=pr.keySet();
			if(set==null) return;
			for(Object key:set) {
				String cmd=key.toString();//key값 "/index.do"이런것들 찾는지 확인.
				String className=pr.getProperty(cmd);// 위 key값에 따라 뒷 value값(클래스) 나타나는지 확인
				if(className!=null) {
					className=className.trim(); //메모리 올릴것이라 혹시 있을 수 있는 공백제거
				}
				System.out.println(cmd+": "+className);
				//className을 실제 객체로 인스턴스화
				Class<?> cls=Class.forName(className);//위에선 단순히 문자열이었는데 *******클래스 형태로 담기고*******
				Object cmdInstance =cls.getDeclaredConstructor().newInstance();//여기서 ****오브젝트타입으로 변경**** 된다
				
				/////////////////////////////여기까지가 ****key,value를 해시맵에 넣는**** 작업한 것들/////
				cmdMap.put(cmd, cmdInstance); 
				/////////////////////////////////////////////////////////////
				
			}//for----
			System.out.println("cmdMap.size():"+cmdMap.size());
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	
	private void process(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
		//1. 클라이언트 요청 URI를 분석해보자.
//		String uri=req.getRequestURI();
//		System.out.println("uri: "+uri); //uri에서 컨텍스트 위치는 제외하고 그 이후 /~.do가 필요하다.
//		String myctx=req.getContextPath(); //컨텍스트 위치 "/MvcWeb"
//		int len=myctx.length(); // 컨텍스트 는 7자리 이다.
//		String cmd=uri.substring(len); //uri - 7자리(컨텍스트)로 원하는 /~.do만 얻어올 수 있음.
		
		//위 과정을 이렇게 쓸수 있게 기능이 생김
		String cmd=req.getServletPath(); 
		System.out.println("cmd==>"+cmd); //원하는것만 뽑은지 확인.
		
		Object instance = cmdMap.get(cmd); //해쉬맵에서 ******아까 저장한 cmd 객체를 반환 받아옴 *******
		if(instance==null) {//객체가 없다면
			System.out.println("Action이 null");
			throw new ServletException("Action이 null입니다");
		}
		
		//null이 아니라면
		System.out.println("instance==>"+instance);
		///////////////꺼낸 액션은 object타입이기 때문에 abstactAction으로 형변환//////////////
		AbstractAction action=(AbstractAction)instance;
		///////////////////////////////////////////////////////////
		try {
			//////[[[[[[[[ 최종적으로  아래 명령으로 액션들을 불러올 수 있다.]]]]]]]]]
			action.execute(req, res);
			//execute()는 컨트롤러 로직을 수행한 뒤 view페이지랑 이동 방식을 지정한다
			String viewPage=action.getViewPage();
			boolean isRedirect=action.isRedirect();
			System.out.println("viewPage: "+viewPage);

			if(viewPage==null) {
				viewPage="index.jsp";
			}
			if(isRedirect) {
				//redirect 방식으로 페이지 이동
				res.sendRedirect(viewPage);
			}else {
				//forward 방식으로 페이지 이동
				RequestDispatcher disp=req.getRequestDispatcher(viewPage);
				disp.forward(req, res);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

}
