package service.impl;

import java.util.ArrayList;
import java.util.List;

import service.StudentService;
import dao.ClassesDao;
import dao.CourseDao;
import dao.ScoreDao;
import dao.StudentDao;
import dao.impl.ClassesDaoImpl;
import dao.impl.CourseDaoImpl;
import dao.impl.ScoreDaoImpl;
import dao.impl.StudentDaoImpl;
import domain.Course;
import domain.PageBean;
import domain.Student;

public class StudentServiceImpl implements StudentService {
	
	private StudentDao sd = new StudentDaoImpl();
	private ClassesDao cd = new ClassesDaoImpl();
	private CourseDao cod =new CourseDaoImpl();
	private ScoreDao scd =new ScoreDaoImpl();

	public Student login(String id, String pwd) {

		Student st = sd.login(id, pwd);
		return st;
	}

	public PageBean<Student> studentList(Integer currentPage, Integer currentCount, Object... param) {
		
		//封装pageBean
		PageBean<Student> pageBean = new PageBean<Student>();
		//当前页数
		pageBean.setCurrentCount(currentCount);
		//当前条数
		pageBean.setCurrentPage(currentPage);
		//总条数
		int totalCount = sd.getCountGetSt(param);
		pageBean.setTotalCount(totalCount);
		//总页数
		int totalPage = (int)Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//数据
		List<Student> list = sd.listGetSt((currentPage-1)*currentCount, currentCount, param);
		
		pageBean.setList(list);
		
		return pageBean;
	}
	public PageBean<Student> studentListAll(Integer currentPage, Integer currentCount, Object... param) {
		
		//封装pageBean
		PageBean<Student> pageBean = new PageBean<Student>();
		//当前页数
		pageBean.setCurrentCount(currentCount);
		//当前条数
		pageBean.setCurrentPage(currentPage);
		//总条数
		int totalCount = sd.getCount(param);
		pageBean.setTotalCount(totalCount);
		//总页数
		int totalPage = (int)Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//数据
		List<Student> list = sd.list((currentPage-1)*currentCount, currentCount, param);
		pageBean.setList(list);
		
		return pageBean;
	}

	public boolean studentAdd(Student st) {

		boolean flag = sd.studentAdd(st);
		
		return flag;
	}

	public boolean studentDelBack(String id) {
		
		boolean flag = sd.studentDelBack(id);
		
		return flag;
	}
	public boolean studentDel(String id) {

		boolean flag = sd.studentDel(id);
		
		return flag;
	}

	public Student studentEdit(String id) {

		Student st = sd.studentEdit(id);
		
		return st;
	}

	public boolean studentEditSubmit(Student st) {
		
		boolean flag = sd.studentEditSubmit(st);
		
		return flag;
	}

	public List<String> studentAddBatch(List<Student> list) {

		List<String> successId = new ArrayList<String>();
		for(Student st : list){
			st.setClasses(cd.getClassesByName(st.getClasses().getName()));
			boolean flag = studentAdd(st);
			if(flag){
				successId.add(st.getId());
			}
		}
		
		return successId;
	}

	public boolean addSelectCourse(String stid, String cid) {
		Float sum = 0.0f;
		//判断已选学分
		Student st = sd.studentEdit(stid);
		if(st.getCourses() != null){
			for(Course c : st.getCourses()){
				sum += c.getCredithour();
			}
		}
		
		//判断该科目学分
		Course co = cod.courseEdit(cid);
		
		//是否可以选课
		boolean flag = false;
		if(sum + co.getCredithour() <= 15){
			flag = scd.addSelectCourse(stid, cid);
		}
		
		return flag;
	}

	public boolean delSelectCourse(String stid, String cid) {
		
		boolean flag = scd.delSelectCourse(stid, cid);
		
		return flag;
	}

	public boolean studenteditPwd(String id, String opwd, String npwd) {
		
		//查询该用户
		Student st = sd.studentEdit(id);
		//判断opwd是否为null, 是null直接修改
		if(opwd == null || st.getPwd().equals(opwd)){
			boolean flag = sd.editPwd(id, npwd);
			return flag;
		}else{
			return false;
		}
	}
}
		
