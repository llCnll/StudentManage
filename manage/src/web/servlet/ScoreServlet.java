package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import domain.Course;
import domain.PageBean;
import domain.Score;
import domain.Student;
import service.ScoreService;
import service.impl.ScoreServiceImpl;

public class ScoreServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
       
    public ScoreServlet() {
        super();
    }
    
    private ScoreService scs = new ScoreServiceImpl();
    
    //获取绩点
    protected void getGpa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
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
		System.out.println("-----正在获得学生绩点列表中----");
		PageBean<Student> pageBean = scs.getGpa(currentPage, currentCount, param);
		System.out.println("-----获取学生绩点列表成功----\n");
		Gson gson = new Gson();
		String json = gson.toJson(pageBean);
		/*response.setCharacterEncoding("UTF-8");*/
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().write(json);
    }	
    //批量添加
  	protected void addBactch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  			
  		String[] ids = request.getParameterValues("id");
  		String[] courseNames = request.getParameterValues("courseName");
  		String[] semesters = request.getParameterValues("semester");
  		String[] score1s = request.getParameterValues("score1");
  		String[] score2s = request.getParameterValues("score2");
  		String[] score3s = request.getParameterValues("score3");
  		
  		List<Student> list = new ArrayList<Student>();
  		System.out.println("-----正在批量录入成绩中----");
  		for(int i = 0; ids != null &&  i < ids.length; ++i){
  			
  			Student st = new Student();
  			Course c = new Course();
  			Score sc = new Score();
  			
  			st.setId(ids[i]);
  			c.setName(courseNames[i]);
  			String score1 = score1s[i];
  			String score2 = score2s[i];
  			String score3 = score3s[i];
  			sc.setScore1((score1 != null&&!"".equals(score1))?Float.parseFloat(score1):null);
  			sc.setScore2((score2 != null&&!"".equals(score2))?Float.parseFloat(score2):null);
  			sc.setScore3((score3 != null&&!"".equals(score3))?Float.parseFloat(score3):null);
  			sc.setSemester(semesters[i]);
  			c.setScore(sc);
  			st.setCourses(new ArrayList<Course>());
  			st.getCourses().add(c);
  			
  			System.out.println(st);
  			list.add(st);
  		}
  		
  		List<String> successId = scs.scoreAddBatch(list);
  		System.out.println("-----批量录入课程成功----\n");
  	}	
    
    //保存一个学生的成绩
	protected void saveScore(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Student st = new Student();
		Course c = new Course();
		Score sc = new Score();
		
		st.setId(request.getParameter("id"));
		c.setId(Integer.parseInt(request.getParameter("cid")));
		String score1 = request.getParameter("score1");
		String score2 = request.getParameter("score2");
		String score3 = request.getParameter("score3");
		sc.setScore1((score1 != null&&!"".equals(score1))?Float.parseFloat(score1):null);
		sc.setScore2((score2 != null&&!"".equals(score2))?Float.parseFloat(score2):null);
		sc.setScore3((score3 != null&&!"".equals(score3))?Float.parseFloat(score3):null);
		sc.setSemester(request.getParameter("semester"));
		c.setScore(sc);
		st.setCourses(new ArrayList<Course>());
		st.getCourses().add(c);
		System.out.println("-----正在录入学生成绩中----");
		boolean flag = scs.saveScore(st);
		
		if(flag){
			System.out.println("-----录入学生成绩成功----\n");
			request.setAttribute("message", "录入成绩成功!");
			request.getRequestDispatcher("/jsp/score/list.jsp").forward(request, response);;
		}else{
			request.setAttribute("error", "录入成绩失败!");
			System.out.println("-----录入学生成绩失败----\n");
			request.getRequestDispatcher("/jsp/score/edit.jsp").forward(request, response);
		}
	}

}
