package common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 */
@WebFilter({ "/*" })
public class EncodingFilter extends HttpFilter implements Filter {
       

	public void destroy() {
	}

//[3]사전처리나 사후 처리 할 일이 있으면 doFIlter()메서드 안에서 구현한다.
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//위 FilterChain 이 chain으로 받아진다
		//[4]POST 방식 일 때 한글 처리를 사전에 처리해보자.
		request.setCharacterEncoding("UTF-8");
		System.out.println("EncodingFilter의 doFilter()호출됨...");
		
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
