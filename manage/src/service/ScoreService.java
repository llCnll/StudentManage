package service;

import java.util.List;

import domain.PageBean;
import domain.Student;

public interface ScoreService {

	boolean saveScore(Student st);

	List<String> scoreAddBatch(List<Student> list);

	PageBean<Student> getGpa(Integer currentPage, Integer currentCount, Object[] param);

	List<Student> getGpa(Object[] param);

}
