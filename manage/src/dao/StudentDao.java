package dao;

import java.util.List;

import domain.Course;
import domain.Student;

public interface StudentDao {

	Student login(String name, String pwd);
	
	List<Student> listGetSt(Integer index, Integer currentCount, Object... param);

	List<Student> list(Integer index, Integer currentCount, Object... param);

	boolean studentAdd(Student st);

	boolean studentDelBack(String id);

	boolean studentDel(String id);

	Student studentEdit(String id);

	boolean studentEditSubmit(Student st);
	
	/*List<Course> stduentCourse(String id);*/

	int getCount();
	
	int getCount(Object... param);
	
	int getCountGetSt();
	
	int getCountGetSt(Object[] param);

	List<Student> listGetSt(Object[] param);

	boolean editPwd(String id, String npwd);

}
