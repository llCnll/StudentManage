package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import service.CourseService;
import service.impl.CourseServiceImpl;

import com.google.gson.Gson;

import domain.Course;
import domain.PageBean;

public class CourseServlet extends BaseServlet {
	
	private CourseService cos = new CourseServiceImpl();
	private Logger logger = Logger.getLogger(CourseServlet.class);
	
	//批量删除
	protected void delBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("ids");
		String[] ids = idStr.split(",");
		StringBuffer sb = new StringBuffer();
		for(String id: ids){
			logger.info("-----正在删除课程中----");
			boolean flag = cos.courseDel(id);
			if(flag){
				logger.info("-----删除课程成功----\n");
			}else{
				sb.append(id+"删除失败!");
			}
		}
		if("".equals(sb.toString())){
			sb.append("删除成功!");
		}
		String message = sb.toString();
		System.out.println("{\"message\":"+"\""+message+"\"}");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"message\":"+"\""+message+"\"}");
	}	
	
	//批量添加
	protected void addBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String[] ids = request.getParameterValues("id");
		String[] names = request.getParameterValues("name");
		String[] credithours = request.getParameterValues("credithour");
		String[] classhours = request.getParameterValues("classhour");
		String[] practicehours = request.getParameterValues("practicehour");
		String[] remarks = request.getParameterValues("remark");
		
		List<Course> list = new ArrayList<Course>();
		logger.info("-----正在批量添加课程中----");
		for(int i = 0; ids != null &&  i < ids.length; ++i){
			
			Course co = new Course();
			co.setId(Integer.parseInt(ids[i]));
			co.setName(names[i]);
			co.setCredithour(Float.parseFloat(credithours[i]));
			co.setClasshour(Integer.parseInt(classhours[i]));
			co.setPracticehour(Integer.parseInt(practicehours[i]));
			co.setRemark(remarks[i]);
			list.add(co);
		}
		
		List<String> successId = cos.courseAddBatch(list);
		logger.info("-----"+successId+"批量添加课程成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(successId);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json);
	}
	
	//删除课程
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		logger.info("-----正在删除课程中----");
		boolean flag = cos.courseDel(id);
		if(flag){
			logger.info("-----删除课程成功----\n");
			response.sendRedirect(request.getContextPath()+"/jsp/course/list.jsp");
		}else{
			request.setAttribute("error", "删除失败!");
			logger.info("-----删除课程失败----\n");
			request.getRequestDispatcher("/jsp/course/list.jsp").forward(request, response);
		}
	}
	
	//提交修改的课程
	protected void editSumbit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Course co = new Course();
			BeanUtils.populate(co, request.getParameterMap());
			
			logger.info("-----正在更改课程信息中----");
			boolean flag = cos.courseEditSubmit(co);
			
			if(flag){
				logger.info("-----更改课程信息成功----\n");
				request.setAttribute("message", co.getName()+"更改成功!");
				request.getRequestDispatcher("/jsp/course/list.jsp").forward(request, response);
			}else{
				request.setAttribute("error", "更改失败!");
				logger.info("-----更改课程信息失败----\n");
				request.getRequestDispatcher("/jsp/course/edit.jsp").forward(request, response);
			}
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
		}
	}
	
	//查询修改的课程
	protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		logger.info("-----正在查找课程中----");
		Course co = cos.courseEdit(id);
		
		if(co != null){
			Gson gson = new Gson();
			String json = gson.toJson(co);
			logger.info("-----查找课程成功----\n");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}else{
			request.setAttribute("checkError", "课程查找失败!");
			logger.info("-----查找课程失败----\n");
			request.getRequestDispatcher("/jsp/course/add.jsp").forward(request, response);
		}
	}
	
	//添加课程
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Course co = new Course();
			BeanUtils.populate(co, request.getParameterMap());
			co.setId(Integer.parseInt(request.getParameter("cid")));
			logger.info("-----正在添加课程信息中----");
			
			boolean flag = cos.courseAdd(co);
			
			if(flag){
				logger.info("-----添加课程信息成功----\n");
				request.setAttribute("message", co.getName()+"添加成功!");
				request.getRequestDispatcher("/jsp/course/list.jsp").forward(request, response);
			}else{
				request.setAttribute("error", "添加失败!");
				logger.info("-----添加课程信息失败----\n");
				request.getRequestDispatcher("/jsp/course/add.jsp").forward(request, response);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
		}
	}
	
	//课程列表
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Object> paramList = new ArrayList<Object>();
		
		request.setCharacterEncoding("UTF-8");
		
		String cid = request.getParameter("cid");
		String name = request.getParameter("name");
		
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
		
		paramList.add(cid);
		paramList.add(name);
		
		Object[] param = paramList.toArray();
		logger.info("-----正在获得课程列表中----");
		PageBean<Course> pageBean = cos.courseList(currentPage, currentCount, param);
		logger.info("-----获取课程列表成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(pageBean);
		/*response.setCharacterEncoding("UTF-8");*/
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(json);
	}
	//课程下拉框
	protected void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("-----正在获得课程下拉列表中----");
		List<Course> list = cos.coursesSelectList();
		logger.info("-----课程下拉列表获取成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(list);
		response.setContentType("text/json; charset=UTF-8");
		response.getWriter().write(json);
	}
	
}
