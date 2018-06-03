package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import service.StudentService;
import dao.ClassesDao;
import dao.StudentDao;
import dao.impl.ClassesDaoImpl;
import dao.impl.StudentDaoImpl;
import domain.Course;
import domain.PageBean;
import domain.Student;

public class StudentServiceImpl implements StudentService {
	
	private StudentDao sd = new StudentDaoImpl();
	private ClassesDao cd = new ClassesDaoImpl();

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
		int totalCount = sd.getCountGetSt();
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
		int totalCount = sd.getCount();
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
			}else{
				successId.add("0");
			}
		}
		
		return successId;
	}

	public boolean addSelectCourse(String stid, String cid) {

		boolean flag = sd.addSelectCourse(stid, cid);
		
		return flag;
	}

	public boolean delSelectCourse(String stid, String cid) {
		
		boolean flag = sd.delSelectCourse(stid, cid);
		
		return flag;
	}
	

}
