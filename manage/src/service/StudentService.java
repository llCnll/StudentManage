package service;

import java.util.List;

import domain.Course;
import domain.PageBean;
import domain.Student;

public interface StudentService {

	Student login(String name, String password);

	PageBean<Student> studentList(Integer currentPage, Integer currentCount, Object... param);
	
	PageBean<Student> studentListAll(Integer currentPage, Integer currentCount, Object... param);

	boolean studentAdd(Student st);

	boolean studentDel(String id);

	Student studentEdit(String id);

	boolean studentEditSubmit(Student st);

	List<String> studentAddBatch(List<Student> list);

	boolean addSelectCourse(String stid, String cid);

	boolean delSelectCourse(String stid, String cid);

}
