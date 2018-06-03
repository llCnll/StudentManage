package dao;

import java.util.List;

import domain.Course;

public interface CourseDao {

	int getCount();

	List<Course> list(int i, Integer currentCount, Object[] param);

	boolean courseAdd(Course co);

	Course courseEdit(String id);

	boolean courseEditSubmit(Course co);

	boolean courseDel(String id);
	
}
