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
	
	//按条件清空
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Object> paramList = new ArrayList<Object>();
		String stid = request.getParameter("stid");
		String loglevel = request.getParameter("loglevel");
		String createtime = request.getParameter("createtime");
		
		paramList.add(stid);
		paramList.add(loglevel);
		paramList.add(createtime);
		
		Object[] param = paramList.toArray();
		logger.debug("-----正在清空当前条件日志中----");
		boolean flag = ls.del(param);
		String message = "";
		if(flag){
			logger.debug("-----清空当前条件下日志成功----\n");
			message += "清空当前条件下日志成功";
		}else{
			logger.debug("-----清空当前条件下日志失败----\n");
			message += "清空当前条件下日志失败";
		}
		response.setContentType("applicaion/json;charset=utf-8");
		response.getWriter().write("{\"message\":"+"\""+message+"\"}");
		
	}
	//批量删除
	protected void delBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("ids");
		String[] ids = idStr.split(",");
		StringBuffer sb = new StringBuffer();
		logger.info("-----正在批量删除日志中----");
		for(String id: ids){
			boolean flag = ls.logDel(id);
			if(flag){
				logger.info("-----"+id+"删除日志成功----\n");
			}else{
				logger.info("-----"+id+"删除日志失败----\n");
				sb.append(id+"删除失败!");
			}
		}
		if("".equals(sb.toString())){
			sb.append("删除成功!");
		}
		logger.info("-----批量删除日志完成----\n");
		String message = sb.toString();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"message\":"+"\""+message+"\"}");
	}

	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.获得该页的用户信息
		List<Object> paramList = new ArrayList<Object>();
		
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
