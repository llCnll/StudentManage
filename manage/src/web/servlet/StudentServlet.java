package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.MDC;
import org.apache.log4j.Logger;
import org.junit.Test;

import service.StudentService;
import service.impl.StudentServiceImpl;

import com.google.gson.Gson;

import domain.Classes;
import domain.PageBean;
import domain.Student;

public class StudentServlet extends BaseServlet {
	
	private StudentService ss = new StudentServiceImpl();
	
	private static Logger logger = Logger.getLogger(StudentServlet.class);
	
	//返回查询人的信息--> 修改选课信息 --> 转发到jsp/course/list.jsp
	protected void returnSt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		logger.info("-----查询学生中----");
		Student st = ss.studentEdit(id);
		
		if(st != null){
			request.setAttribute("selectStudent", st);
			logger.info("-----查询学生成功----\n");
			String choose = request.getParameter("goto");
			if("score".equals(choose)){
				request.getRequestDispatcher("jsp/score/edit.jsp").forward(request, response);
			}else{
				request.getRequestDispatcher("jsp/course/list.jsp").forward(request, response);
			}
		}else{
			;//若不存在
		}
		
	}
	
	//删除选课信息
	protected void delSelectCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String cid = request.getParameter("cid");
		String stid = request.getParameter("stid");
		
		logger.info("-----学生退课中----");
		boolean flag = ss.delSelectCourse(stid, cid);
		
		response.setContentType("application/json; charset=utf-8");
		if(flag){
			logger.info("-----学生退课成功----\n");
			response.getWriter().write("{\"message\":"+"\"退课成功!\"}");
		}else{
			logger.info("-----学生退课失败----\n");
			response.getWriter().write("{\"message\":"+"\"退课失败!\"}");
		}
		//如果操作的是个人信息, 需要更新session的值直接set
		HttpSession session = request.getSession();
		updateSession(session, stid);
	}
	
	//添加选课信息
	protected void addSelectCourse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String stid = request.getParameter("stid");
		String cid = request.getParameter("cid");
		
		logger.info("-----学生选课中----\n");
		
		boolean flag = ss.addSelectCourse(stid, cid);
		
		response.setContentType("application/json; charset=utf-8");
		if(flag){
			logger.info("-----学生选课成功----\n");
			response.getWriter().write("{\"message\":"+"\"选课成功!\"}");
		}else{
			logger.info("-----学生选课失败, 查看学分是否已选满15分----\n");
			response.getWriter().write("{\"message\":"+"\"选课失败, 查看学分是否已选满15分或者其他原因!\"}");
		}
		
		//如果操作的是个人信息, 需要更新session的值直接set
		HttpSession session = request.getSession();
		updateSession(session, stid);
	}
	//管理员查询所有选课情况
	protected void selectCourseList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//1.获得该页的用户信息
		List<Object> paramList = new ArrayList<Object>();
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("cid");
		String name = request.getParameter("name");
		String classes = request.getParameter("classes");
		
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
		
		paramList.add(id);
		paramList.add(name);
		paramList.add(classes);
		
		Object[] param = paramList.toArray();
		logger.info("-----正在获得学生选课情况中----");
		PageBean<Student> pageBean = ss.studentList(currentPage, currentCount, param);
		logger.info("-----获取学生选课情况成功----\n");
		
		Gson gson = new Gson();
		String json = gson.toJson(pageBean);
		/*response.setCharacterEncoding("UTF-8");*/
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().write(json);
	}	
	
	//批量删除
	protected void delBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("ids");
		String[] ids = idStr.split(",");
		StringBuffer sb = new StringBuffer();
		for(String id: ids){
			logger.info("-----正在删除学生中----");
			boolean flag = ss.studentDel(id);
			if(flag){
				logger.info("-----删除学生成功----\n");
			}else{
				sb.append(id+"删除失败!");
			}
		}
		if("".equals(sb.toString())){
			sb.append("删除成功!");
		}
		String message = sb.toString();
		response.setCharacterEncoding("utf-8");
		response.getWriter().write("{\"message\":"+"\""+message+"\"}");
	}	
	
	//批量添加
	protected void addBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String[] ids = request.getParameterValues("id");
		String[] names = request.getParameterValues("name");
		String[] pwds = request.getParameterValues("pwd");
		String[] classesNames = request.getParameterValues("classesName");
		String[] roleIds = request.getParameterValues("roleId");
		
		List<Student> list = new ArrayList<Student>();
		logger.info("-----正在批量添加用户中----");
		for(int i = 0; ids != null &&  i < ids.length; ++i){
			
			Student st = new Student();
			st.setId(ids[i]);
			st.setName(names[i]);
			st.setPwd(pwds[i]);
			st.setRoleId("普通用户".equals(roleIds[i])?0:1);
			Classes classes = new Classes();
			classes.setName(classesNames[i]);
			st.setClasses(classes);
			list.add(st);
		}
		
		List<String> successId = ss.studentAddBatch(list);
		logger.info("-----批量添加用户成功----\n");
	}	
	//提交修改的用户
	protected void editSumbit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Student st = new Student();
			BeanUtils.populate(st, request.getParameterMap());
			Classes classes = new Classes();
			classes.setId(Integer.parseInt(request.getParameter("classesId")));
			st.setClasses(classes);
			
			logger.info("-----正在更改学生信息中----");
			boolean flag = ss.studentEditSubmit(st);
			
			if(flag){
				logger.info("-----更改学生信息成功----\n");
				response.sendRedirect(request.getContextPath()+"/jsp/customer/list.jsp");
			}else{
				request.setAttribute("error", "更改失败!");
				logger.info("-----更改学生信息失败----\n");
				request.getRequestDispatcher("/jsp/customer/edit.jsp?id="+st.getId()).forward(request, response);
			}
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
	//查询修改的用户
	protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		logger.info("-----正在查找学生中----");
		Student st = ss.studentEdit(id);
		
		if(st != null){
			Gson gson = new Gson();
			String json = gson.toJson(st);
			logger.info("-----查找学生成功----\n");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}else{
			request.setAttribute("error", "用户查找失败!");
			logger.info("-----查找学生失败----\n");
			request.getRequestDispatcher("/jsp/customer/add.jsp").forward(request, response);
		}
	}
	//注册用户
	protected void signup(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				Student st = new Student();
				BeanUtils.populate(st, request.getParameterMap());
				Classes classes = new Classes();
				classes.setId(Integer.parseInt(request.getParameter("classesId")));
				st.setClasses(classes);
				
				logger.info("-----正在注册学生信息中----");
				boolean flag = ss.studentAdd(st);
				String message = "";
				if(flag){
					logger.info("-----注册学生信息成功----\n");
					message = "注册成功";
				}else{
					logger.info("-----注册学生信息失败----\n");
					message = "注册失败";
				}
				logger.info("{\"message\":"+"\""+message+"\"}");
				response.setContentType("applicaion/json;charset=utf-8");
				response.getWriter().write("{\"message\":"+"\""+message+"\"}");
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
	}
	//添加用户
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Student st = new Student();
			BeanUtils.populate(st, request.getParameterMap());
			Classes classes = new Classes();
			classes.setId(Integer.parseInt(request.getParameter("classesId")));
			st.setClasses(classes);
			
			logger.info("-----正在添加学生信息中----");
			boolean flag = ss.studentAdd(st);
			
			if(flag){
				logger.info("-----添加学生信息成功----\n");
				request.setAttribute("message", st.getId()+"添加成功!");
				request.getRequestDispatcher("/jsp/customer/list.jsp").forward(request, response);
			}else{
				request.setAttribute("error", "添加失败!");
				logger.info("-----添加学生信息失败----\n");
				request.getRequestDispatcher("/jsp/customer/add.jsp").forward(request, response);
			}
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
	}
	//删除用户
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		logger.info("-----正在删除学生中----");
		boolean flag = ss.studentDel(id);
		if(flag){
			logger.info("-----删除学生成功----\n");
			request.setAttribute("message", id+"删除成功!");
			request.getRequestDispatcher("/jsp/customer/list.jsp").forward(request, response);;
		}else{
			request.setAttribute("error", id+"删除失败!");
			logger.info("-----删除学生失败----\n");
			request.getRequestDispatcher("/jsp/customer/list.jsp").forward(request, response);
		}
	}
	//用户列表
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Object> paramList = new ArrayList<Object>();
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String classes = request.getParameter("classes");
		
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
		
		paramList.add(id);
		paramList.add(name);
		paramList.add(classes);
		
		Object[] param = paramList.toArray();
		logger.info("-----正在获得学生列表中----");
		PageBean<Student> pageBean = ss.studentListAll(currentPage, currentCount, param);
		logger.info("-----获取学生列表成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(pageBean);
		/*response.setCharacterEncoding("UTF-8");*/
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(json);
	}
	
	//用户退出
	protected void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		logger.info("-----正在退出中-----");
		session.removeAttribute("student");
		logger.info("-----退出成功-----\n");
		response.sendRedirect(request.getContextPath()+"/login.jsp");
	}
	//用户登陆
	protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		HttpSession session = request.getSession();
		MDC.put("stid", id);
		logger.info("-----正在登录中-----");
		Student st = ss.login(id, pwd);
		if(st != null){
			session.setAttribute("student", st);
			logger.info("-----登录成功-----\n");
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}else{
			request.setAttribute("error", "登陆失败用户或密码错误!");
			logger.info("-----登录失败-----\n");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}
	
	private void updateSession(HttpSession session, String id){
		Student student = (Student) session.getAttribute("student");
		System.out.println("-----更新session中-----");
		if(id.equals(student.getId())){
			student = ss.studentEdit(id);
			System.out.println("-----更新session成功-----\n");
			session.setAttribute("student", student);
		}else{
			System.out.println("-----无需更新session-----\n");
		}
	}
}
