package service.impl;

import java.util.ArrayList;
import java.util.List;

import dao.CourseDao;
import dao.impl.CourseDaoImpl;
import domain.Course;
import domain.PageBean;
import domain.Student;
import service.CourseService;

public class CourseServiceImpl implements CourseService {
	
	private CourseDao cod = new CourseDaoImpl();

	public PageBean<Course> courseList(Integer currentPage, Integer currentCount, Object[] param) {
		//封装pageBean
		PageBean<Course> pageBean = new PageBean<Course>();
		//当前页数
		pageBean.setCurrentCount(currentCount);
		//当前条数
		pageBean.setCurrentPage(currentPage);
		//总条数
		int totalCount = cod.getCount();
		pageBean.setTotalCount(totalCount);
		//总页数
		int totalPage = (int)Math.ceil(1.0*totalCount/currentCount);
		pageBean.setTotalPage(totalPage);
		//数据
		List<Course> list = cod.list((currentPage-1)*currentCount, currentCount, param);
		pageBean.setList(list);
		
		//System.out.println(pageBean);
		
		return pageBean;
	}

	public boolean courseAdd(Course co) {
		
		boolean flag = cod.courseAdd(co);
		
		return flag;
	}

	public Course courseEdit(String id) {
		
		Course co = cod.courseEdit(id);
		
		return co;
	}

	public boolean courseEditSubmit(Course co) {
		
		boolean flag = cod.courseEditSubmit(co);
		
		return flag;
	}

	public boolean courseDel(String id) {
		
		boolean flag = cod.courseDel(id);
		
		return flag;
	}

	public List<String> courseAddBatch(List<Course> list) {
		
		List<String> successId = new ArrayList<String>();
		for(Course co : list){
			boolean flag = courseAdd(co);
			if(flag){
				successId.add(String.valueOf(co.getId()));
			}else{
				successId.add("0");
			}
		}
		
		return successId;
	}

}
