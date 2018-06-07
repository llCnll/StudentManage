package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import service.LogService;
import service.impl.LogServiceImpl;

import com.google.gson.Gson;

import domain.Log;
import domain.PageBean;

public class LogServlet extends BaseServlet {
	
	private Logger logger = Logger.getLogger(LogServlet.class);
	private LogService ls = new LogServiceImpl();

	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.获得该页的用户信息
		List<Object> paramList = new ArrayList<Object>();
		
		request.setCharacterEncoding("UTF-8");
		
		String stid = request.getParameter("stid");
		String loglevel = request.getParameter("loglevel");
		String createtime = request.getParameter("createtime");
		
		String currentPageStr = request.getParameter("page");
		String currentCountStr = request.getParameter("currentCount");
		if(currentPageStr == null || "".equals(currentPageStr)) {
			currentPageStr = "1";
		}
		if(currentCountStr == null || "".equals(currentCountStr)) {
			currentCountStr = "5";//默认值 每页显示5个
		}
		Integer currentPage = Integer.parseInt(currentPageStr);
		Integer currentCount = Integer.parseInt(currentCountStr);
		System.out.println("currentPage:"+currentPage+" currentCount:"+ currentCount);
		
		paramList.add(stid);
		paramList.add(loglevel);
		paramList.add(createtime);
		
		Object[] param = paramList.toArray();
		logger.debug("-----正在加载日志中----");
		PageBean<Log> pageBean = ls.logList(currentPage, currentCount, param);
		logger.debug("-----日志加载成功----\n");
		
		Gson gson = new Gson();
		String json = gson.toJson(pageBean);
		/*response.setCharacterEncoding("UTF-8");*/
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(json);
		
	}

}
