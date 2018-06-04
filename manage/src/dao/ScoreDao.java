package dao;

import java.util.List;

import domain.Course;
import domain.Score;
import domain.Student;

public interface ScoreDao {

	Score studentScore(String id, Integer integer);

	boolean saveScore(Student st);

	boolean addSelectCourse(String stid, String cid);

	boolean delSelectCourse(String stid, String cid);
	
	public List<Course> stduentCourse(String id);
}
