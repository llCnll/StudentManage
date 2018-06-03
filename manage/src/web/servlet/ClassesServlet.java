package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import service.ClassesSerivce;
import service.impl.ClassesServiceImpl;

import com.google.gson.Gson;

import domain.Classes;
import domain.PageBean;
import domain.Student;

public class ClassesServlet extends BaseServlet {
	
	private ClassesSerivce cs = new ClassesServiceImpl();
	
	//批量添加
	protected void addBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
		String[] names = request.getParameterValues("name");
		String[] grades = request.getParameterValues("grade");
		String[] majors = request.getParameterValues("major");
		
		List<Classes> list = new ArrayList<Classes>();
		
		for(int i = 0; names != null &&  i < names.length; ++i){
			
			Classes cl = new Classes();
			cl.setName(names[i]);
			cl.setGrade(Integer.parseInt(grades[i]));
			cl.setMajor(majors[i]);
			System.out.println(cl);
			list.add(cl);
		}
		
		List<String> successId = cs.classesAddBatch(list);
		//添加成功的剩下操作还未补全
	}	
	
	//批量删除
	protected void delBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("ids");
		String[] ids = idStr.split(",");
		StringBuffer sb = new StringBuffer();
		for(String id: ids){
			System.out.println("-----正在删除班级中----");
			boolean flag = cs.classesDel(id);
			if(flag){
				System.out.println("-----删除班级成功----\n");
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
	
	//提交修改的用户
	protected void editSumbit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Classes cl = new Classes();
			BeanUtils.populate(cl, request.getParameterMap());
			
			System.out.println("-----正在更改学生信息中----");
			boolean flag = cs.classesEditSubmit(cl);
			
			if(flag){
				System.out.println("-----更改班级信息成功----\n");
				response.sendRedirect(request.getContextPath()+"/jsp/system/list.jsp");
			}else{
				request.setAttribute("editError", "更改失败!");
				System.out.println("-----更改班级信息失败----\n");
				request.getRequestDispatcher("/jsp/system/edit.jsp?id="+cl.getId()).forward(request, response);
			}
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//查询修改的班级
	protected void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println("-----正在查找班级中----");
		Classes cl = cs.classesEdit(id);
		
		if(cl != null){
			Gson gson = new Gson();
			String json = gson.toJson(cl);
			System.out.println("-----查找班级成功----\n");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);
		}else{
			request.setAttribute("checkError", "班级查找失败!");
			System.out.println("-----查找班级失败----\n");
			request.getRequestDispatcher("/jsp/system/add.jsp").forward(request, response);
		}
	}
	
	//删除班级
	protected void del(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		System.out.println("-----正在删除班级中----");
		boolean flag = cs.classesDel(id);
		if(flag){
			System.out.println("-----删除班级成功----\n");
			response.sendRedirect(request.getContextPath()+"/jsp/system/list.jsp");
		}else{
			request.setAttribute("delError", "删除失败!");
			System.out.println("-----删除班级失败----\n");
			request.getRequestDispatcher("/jsp/system/list.jsp").forward(request, response);
		}
	}
	
	//添加班级
	protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Classes cl = new Classes();
			BeanUtils.populate(cl, request.getParameterMap());
			System.out.println("-----正在添加学生信息中----");
			
			boolean flag = cs.classesAdd(cl);
			
			if(flag){
				System.out.println("-----添加班级信息成功----\n");
				response.sendRedirect(request.getContextPath()+"/jsp/system/list.jsp");
			}else{
				request.setAttribute("addError", "添加失败!");
				System.out.println("-----添加班级信息失败----\n");
				request.getRequestDispatcher("/jsp/system/add.jsp").forward(request, response);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//班级列表
	protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Object> paramList = new ArrayList<Object>();
		
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String grade = request.getParameter("grade");
		String major = request.getParameter("major");
		
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
		
		paramList.add(name);
		paramList.add(grade);
		paramList.add(major);
		
		Object[] param = paramList.toArray();
		System.out.println("-----正在获得班级下拉列表中----");
		PageBean<Classes> pageBean = cs.classesList(currentPage, currentCount, param);
		System.out.println("-----班级下拉列表获取成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(pageBean);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(json);
	}
	
	//班级专业下拉框
	protected void majorSelect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----正在获得班级专业下拉列表中----");
		List<String> list = cs.classesMajorSelect();
		System.out.println("-----班级专业下拉列表获取成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(list);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(json);
	}
	
	//年级下拉框
	protected void gradeSelect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----正在获得年级下拉列表中----");
		List<String> list = cs.classesGradeSelect();
		System.out.println("-----年级下拉列表获取成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(list);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(json);
	}
	
	//班级下拉框
	protected void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----正在获得班级下拉列表中----");
		List<Classes> list = cs.classesList();
		System.out.println("-----班级下拉列表获取成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(list);
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(json);
	}

}