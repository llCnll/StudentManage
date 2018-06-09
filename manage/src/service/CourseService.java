package service;

import java.util.List;

import domain.Course;
import domain.PageBean;

public interface CourseService {

	PageBean<Course> courseList(Integer currentPage, Integer currentCount, Object[] param);

	boolean courseAdd(Course co);

	Course courseEdit(String id);

	boolean courseEditSubmit(Course co);

	boolean courseDel(String id);

	List<String> courseAddBatch(List<Course> list);

	List<Course> coursesSelectList();

}
