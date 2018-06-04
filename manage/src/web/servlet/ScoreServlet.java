package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import service.ScoreService;
import service.impl.ScoreServiceImpl;
import domain.Course;
import domain.Score;
import domain.Student;

public class ScoreServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
       
    public ScoreServlet() {
        super();
    }
    
    private ScoreService scs = new ScoreServiceImpl();
    
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
