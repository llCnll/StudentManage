package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.CourseDao;
import dao.ScoreDao;
import dao.StudentDao;
import dao.impl.CourseDaoImpl;
import dao.impl.ScoreDaoImpl;
import dao.impl.StudentDaoImpl;
import domain.Course;
import domain.PageBean;
import domain.Student;
import service.ScoreService;

public class ScoreServiceImpl implements ScoreService {
	
	private StudentDao sd = new StudentDaoImpl();
	private ScoreDao scd = new ScoreDaoImpl();
	private CourseDao cod = new CourseDaoImpl();

	public boolean saveScore(Student st) {

		boolean flag = scd.saveScore(st);
		
		return flag;
	}

	public List<String> scoreAddBatch(List<Student> list) {
		
		List<String> successId = new ArrayList<String>();
		for(Student st : list){
			st.getCourses().get(0).setId(cod.getCourseByName(st.getCourses().get(0).getName()).getId());
			boolean flag = saveScore(st);
			if(flag){
				successId.add(st.getId());
			}else{
				successId.add("0");
			}
		}
		return successId;
	}

	public PageBean<Student> getGpa(Integer currentPage, Integer currentCount, Object[] param) {
		//封装pageBean
		PageBean<Student> pageBean = new PageBean<Student>();
		//当前页数
		pageBean.setCurrentCount(currentCount);
		//当前条数
		pageBean.setCurrentPage(currentPage);
		//总条数
		int totalCount = sd.getCountGetSt();
		pageBean.setTotalCount(totalCount);
		//总页数
		int totalPage = (int)Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//数据
		List<Student> list = sd.listGetSt((currentPage-1)*currentCount, currentCount, param);
		
		for(Student st : list) {
			Float gpa = getGpa(st.getCourses());
			st.setGpa(gpa);
		}
		
		pageBean.setList(list);
		
		return pageBean;
	}
	
	private Float getGpa(List<Course> list) {
		
		Float totalCredithour = 0.0f;
		Float gpa = 0.0f;
		if(list != null) {
			for(Course course: list) {
				// 计算总绩点
				totalCredithour += course.getCredithour();
				//计算已得绩点
				Float score1 = course.getScore().getScore1();
				Float score2 = course.getScore().getScore2();
				Float score3 = course.getScore().getScore3();
	
				if(score1 != null && score1 >= 60) {
					gpa += course.getCredithour()*(score1-50);
				}else if(score2 != null && score2 >= 60){
					gpa += course.getCredithour()*(score2-50);
				}else if(score3 != null && score3 >= 60) {
					gpa += course.getCredithour()*(score3-50);
				}
			}
		}
		if(totalCredithour != 0) {
			gpa = gpa/10/totalCredithour;
		}else {
			gpa = null;
		}
		
		return gpa;
	}
	
}
