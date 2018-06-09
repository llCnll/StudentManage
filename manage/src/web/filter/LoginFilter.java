package web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import domain.Student;

public class LoginFilter implements Filter {

    public LoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		//获取根目录所对应的绝对路径
        String currentURL = req.getRequestURI();
        //截取到当前文件名用于比较
        String targetURL = currentURL.substring(currentURL.indexOf("/",1),currentURL.length());
        //System.out.println("currentURL: "+ currentURL);
        //System.out.println("targetURL: "+ targetURL);
        String method = req.getParameter("method");
        String signup = req.getParameter("signup");
        //System.out.println("method: "+ method);
        //如果session不为空就返回该session，如果为空就返回null
        HttpSession session = req.getSession(false);
        if(!"/login.jsp".equals(targetURL)){
        	
        	if(method != null && "login".equals(method)){
        		chain.doFilter(request, response);
        		return ;
        	}
        	//放行images下文件
        	if(targetURL.startsWith("/images")){
        		chain.doFilter(request, response);
        		return ;
        	}
        	//放行signup true的情况
        	if(signup != null && signup.equals("true")){
        		chain.doFilter(request, response);
        		return ;
        	}
        	
            //判断当前页面是否是重顶次昂后的登陆页面页面，如果是就不做session的判断，防止死循环
            if(session==null||session.getAttribute("student")==null){
                //如果session为空表示用户没有登陆就重定向到login.jsp页面
                resp.sendRedirect(req.getContextPath()+"/login.jsp");
                return;
            }
        }
        
        
        if(session != null && session.getAttribute("student") != null){
        	Student st = (Student) session.getAttribute("student");
        	MDC.put("stid", st.getId());
        }
		
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
