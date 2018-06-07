package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import utils.DataSourceUtils;


import dao.CourseDao;
import dao.ScoreDao;
import dbtools.DB;
import domain.Course;
import domain.Score;
import domain.Student;

public class ScoreDaoImpl implements ScoreDao {
	
	private CourseDao cod = new CourseDaoImpl();
	private Logger logger = Logger.getLogger(ScoreDaoImpl.class);
	
	private void scoreBean(ResultSet rs, Score score) throws SQLException {
		score.setId(rs.getLong("id"));
		score.setSemester(rs.getString("semester"));
		score.setScore1(rs.getObject("score1") == null?null:rs.getFloat("score1"));
		score.setScore2(rs.getObject("score2") == null?null:rs.getFloat("score2"));
		score.setScore3(rs.getObject("score3") == null?null:rs.getFloat("score3"));
	}

	public Score studentScore(String stid, Integer cid) {
		
		String sql = "select * from score where studentid = ? && courseId = ?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Score score = null;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, stid, cid);
			//System.out.println(pst.toString().split(": ")[1]);
			//System.out.printf("select * from student where id = %s && pwd = %s\n", id, pwd);
			
			rs = pst.executeQuery();
			logger.debug(sql);
			
			if(rs.next()) {
				score = new Score();
				this.scoreBean(rs, score);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			try {
				DataSourceUtils.closeAll(pst, rs);
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
		}
		return score;
	}

	public boolean saveScore(Student st) {
		
		String sql = "update score set id = ?, semester = ?, score1 = ?, score2 = ?, score3 = ? where studentid = ? && courseid = ?";
		
		Connection conn =null;
		PreparedStatement pst = null;
		int row = 0;

		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, Long.parseLong(st.getId()+""+st.getCourses().get(0).getId()), st.getCourses().get(0).getScore().getSemester(), st.getCourses().get(0).getScore().getScore1(), st.getCourses().get(0).getScore().getScore2(), st.getCourses().get(0).getScore().getScore3(), st.getId(), st.getCourses().get(0).getId());
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage());}
		}
		
		return row > 0? true: false;
	}
	
	public boolean addSelectCourse(String stid, String cid) {
		
		String sql = "insert into score values(?,?,?,null,null,null,null)";
		Connection conn = null;
		PreparedStatement pst = null;
		int row = 0;

		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, Long.parseLong( stid+""+cid), stid, cid);
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage());}
		}
		
		return row > 0? true: false;
	}

	public boolean delSelectCourse(String stid, String cid) {
		
		String sql = "delete from score where studentid = ? && courseid = ?"; 
		Connection conn = null;
		PreparedStatement pst = null;
		int row = 0;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, stid, cid);
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage());}
		}
		
		return row > 0? true: false;
	}
	
	public List<Course> stduentCourse(String id) {

		String sql = "select * from score where studentid = ?";
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		List<String> courseIds = new ArrayList<String>();
		List<Course> courses = new ArrayList<Course>();
		
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, id);
			//System.out.println(pst.toString().split(": ")[1]);
			rs = pst.executeQuery();
			logger.debug(sql);
			while(rs.next()) {
				courseIds.add(rs.getString("courseid"));
			}
			for(String courseId : courseIds){
				Course course = cod.courseEdit(courseId);
				courses.add(course);
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage());}
		}
		
		return courses.size()>0?courses:null;
	}


}
