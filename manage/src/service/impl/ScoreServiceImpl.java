package service.impl;

import dao.ScoreDao;
import dao.impl.ScoreDaoImpl;
import domain.Student;
import service.ScoreService;

public class ScoreServiceImpl implements ScoreService {
	
	private ScoreDao scd = new ScoreDaoImpl();

	public boolean saveScore(Student st) {

		boolean flag = scd.saveScore(st);
		
		return flag;
	}

}
