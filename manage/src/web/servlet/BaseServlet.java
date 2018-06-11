package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.junit.Test;

import utils.DataSourceUtils;
@SuppressWarnings("all")
public class BaseServlet extends HttpServlet {
	
	private Logger logger = Logger.getLogger(this.getClass());

	protected void service(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

		try {
			
			String methodName = request.getParameter("method");
			Class clazz = this.getClass();
			
			Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			
			method.invoke(this, request,response);
			
			DataSourceUtils.closeConnection();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
		} 
	
	}
	
}
