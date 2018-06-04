package service.impl;

import java.util.ArrayList;
import java.util.List;

import service.ScoreService;
import dao.CourseDao;
import dao.ScoreDao;
import dao.impl.CourseDaoImpl;
import dao.impl.ScoreDaoImpl;
import domain.Student;

public class ScoreServiceImpl implements ScoreService {
	
	private ScoreDao scd = new ScoreDaoImpl();
	private CourseDao cod = new CourseDaoImpl();

	public boolean saveScore(Student st) {

		boolean flag = scd.saveScore(st);
		
		return flag;
	}

	public List<String> scoreAddBatch(List<Student> list) {
		
		List<String> successId = new ArrayList<String>();
		for(Student st : list){
			st.getCourses().get(0).setId(cod.getCourseByName(st.getCourses().get(0).getName()).getId());
			boolean flag = saveScore(st);
			if(flag){
				successId.add(st.getId());
			}else{
				successId.add("0");
			}
		}
		return successId;
	}

}
