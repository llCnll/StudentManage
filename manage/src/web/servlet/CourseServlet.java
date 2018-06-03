package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.google.gson.Gson;

import domain.Classes;
import domain.Course;
import domain.PageBean;
import domain.Student;
import service.CourseService;
import service.impl.CourseServiceImpl;

public class CourseServlet extends BaseServlet {
	
	private CourseService cos = new CourseServiceImpl();
	
	//批量删除
	protected void delBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("ids");
		String[] ids = idStr.split(",");
		StringBuffer sb = new StringBuffer();
		for(String id: ids){
			System.out.println("-----正在删除课程中----");
			boolean flag = cos.courseDel(id);
			if(flag){
				System.out.println("-----删除课程成功----\n");
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
		
		for(int i = 0; ids != null &&  i < ids.length; ++i){
			
			Course co = new Course();
			co.setId(Integer.parseInt(ids[i]));
			co.setName(names[i]);
			co.setCredithour(Float.parseFloat(credithours[i]));
			co.setClasshour(Integer.parseInt(classhours[i]));
			co.setPracticehour(Integer.parseInt(practicehours[i]));
			co.setRemark(remarks[i]);
			System.out.println(co);
			list.add(co);
		}
		
		List<String> successId = cos.courseAddBatch(list);
		
	}
	
	//删除课程
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println("-----正在删除课程中----");
		boolean flag = cos.courseDel(id);
		if(flag){
			System.out.println("-----删除课程成功----\n");
			response.sendRedirect(request.getContextPath()+"/jsp/course/list.jsp");
		}else{
			request.setAttribute("delError", "删除失败!");
			System.out.println("-----删除课程失败----\n");
			request.getRequestDispatcher("/jsp/course/list.jsp").forward(request, response);
		}
	}
	
	//提交修改的课程
	protected void editSumbit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Course co = new Course();
			BeanUtils.populate(co, request.getParameterMap());
			
			System.out.println("-----正在更改课程信息中----");
			boolean flag = cos.courseEditSubmit(co);
			
			if(flag){
				System.out.println("-----更改课程信息成功----\n");
				response.sendRedirect(request.getContextPath()+"/jsp/course/list.jsp");
			}else{
				request.setAttribute("addError", "更改失败!");
				System.out.println("-----更改课程信息失败----\n");
				request.getRequestDispatcher("/jsp/course/edit.jsp?id="+co.getId()).forward(request, response);
			}
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//查询修改的课程
	protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println("-----正在查找课程中----");
		Course co = cos.courseEdit(id);
		
		if(co != null){
			Gson gson = new Gson();
			String json = gson.toJson(co);
			System.out.println("-----查找课程成功----\n");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}else{
			request.setAttribute("checkError", "课程查找失败!");
			System.out.println("-----查找课程失败----\n");
			request.getRequestDispatcher("/jsp/course/add.jsp").forward(request, response);
		}
	}
	
	//添加班级
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Course co = new Course();
			BeanUtils.populate(co, request.getParameterMap());
			System.out.println("-----正在添加课程信息中----");
			
			boolean flag = cos.courseAdd(co);
			
			if(flag){
				System.out.println("-----添加课程信息成功----\n");
				response.sendRedirect(request.getContextPath()+"/jsp/course/list.jsp");
			}else{
				request.setAttribute("addError", "添加失败!");
				System.out.println("-----添加课程信息失败----\n");
				request.getRequestDispatcher("/jsp/course/add.jsp").forward(request, response);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//用户列表
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Object> paramList = new ArrayList<Object>();
		
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
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
		
		paramList.add(id);
		paramList.add(name);
		
		Object[] param = paramList.toArray();
		System.out.println("-----正在获得课程列表中----");
		PageBean<Course> pageBean = cos.courseList(currentPage, currentCount, param);
		System.out.println("-----获取课程列表成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(pageBean);
		/*response.setCharacterEncoding("UTF-8");*/
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(json);
	}
	
	
}
