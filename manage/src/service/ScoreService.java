package service;

import java.util.List;

import domain.Student;

public interface ScoreService {

	boolean saveScore(Student st);

	List<String> scoreAddBatch(List<Student> list);

}
