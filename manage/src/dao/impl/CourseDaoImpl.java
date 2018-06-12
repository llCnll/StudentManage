package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import utils.DataSourceUtils;
import dao.CourseDao;
import dbtools.DB;
import domain.Course;

public class CourseDaoImpl implements CourseDao {
	
	private Logger logger = Logger.getLogger(CourseDaoImpl.class);
	
	private void courseBean(ResultSet rs, Course co) throws SQLException {
		co.setId(rs.getInt("id"));
		co.setName(rs.getString("name"));
		co.setCredithour(rs.getFloat("credithour"));
		co.setClasshour(rs.getInt("classhour"));
		co.setPracticehour(rs.getInt("practicehour"));
		co.setRemark(rs.getString("remark"));
	}

	public int getCount() {
		String sql = "select count(id) from course";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			//System.out.println(pst.toString().split(": ")[1]);
			
			rs = pst.executeQuery();
			logger.debug(sql);
			if(rs.next()) {
				count = rs.getInt("count(id)");
			}
			
		} catch (SQLException e) {
			logger.error("课程数量: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return count;
	}
	
	public int getCount(Object[] param) {
		String sql = "select count(id) from course where ";
		
		//[0]: id  [1]: name  [2]: classes
		ArrayList<Object> paramlist = new ArrayList<Object>(Arrays.asList(param));
		
		if(param[0] != null && !("").equals(param[0])){
			sql += "&& id = ?";
			paramlist.remove(1);
		}else{
			if(param[1] != null && !("").equals(param[1])){
				paramlist.set(1, "%"+paramlist.get(1)+"%");
				sql += "&& name like ?";
			}
		}
		paramlist.removeAll(Collections.singleton(""));
		paramlist.removeAll(Collections.singleton(null));
		
		//判断 where 和 where &&
		if(sql.endsWith("where ")){
			sql = sql.substring(0, sql.indexOf("where ")-1);
		}
		if(sql.contains("where &&")){
			sql = sql.replaceAll("where &&", "where");
		}
		param = paramlist.toArray();
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			//System.out.println(pst.toString().split(": ")[1]);
			DB.fillStatement(pst, param);
			rs = pst.executeQuery();
			logger.debug(sql);
			if(rs.next()) {
				count = rs.getInt("count(id)");
			}
			
		} catch (SQLException e) {
			logger.error("课程数量按条件: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return count;
	}

	public List<Course> list(int index, Integer currentCount, Object[] param) {
		String sql = "select * from course where ";
		//[0]: id  [1]: name  [2]: classes
		ArrayList<Object> paramlist = new ArrayList<Object>(Arrays.asList(param));
		
		if(param[0] != null && !("").equals(param[0])){
			sql += "&& id = ?";
			paramlist.remove(1);
		}else{
			if(param[1] != null && !("").equals(param[1])){
				paramlist.set(1, "%"+paramlist.get(1)+"%");
				sql += "&& name like ?";
			}
		}
		paramlist.removeAll(Collections.singleton(""));
		paramlist.removeAll(Collections.singleton(null));
		
		//判断 where 和 where &&
		if(sql.endsWith("where ")){
			sql = sql.substring(0, sql.indexOf("where ")-1);
		}
		if(sql.contains("where &&")){
			sql = sql.replaceAll("where &&", "where");
		}
		
		sql += " limit ?, ?";
		paramlist.add(index);
		paramlist.add(currentCount);
		
		param = paramlist.toArray();
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Course> list = new ArrayList<Course>();
		
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, param);
			//System.out.println(pst.toString().split(": ")[1]);
			rs = pst.executeQuery();
			logger.debug(sql);
			while(rs.next()) {
				Course co = new Course();
				this.courseBean(rs, co);
				list.add(co);
			}
			
		} catch (SQLException e) {
			logger.error("课程列表按条件: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return list.size()>0?list:null;
	}

	public boolean courseAdd(Course co) {
		
		String sql = "insert into course values(?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pst = null;
		int row = 0;

		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, co.getId(), co.getName(), co.getCredithour(), co.getClasshour(), co.getPracticehour(), co.getRemark());
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error("课程添加: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return row > 0? true: false;
	}
	
	public Course courseEdit(String id) {
		
		String sql = "select * from course where id = ?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Course co = null;
		
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, id);
			//System.out.println(pst.toString().split(": ")[1]);
			rs = pst.executeQuery();
			logger.debug(sql);
			while(rs.next()) {
				co = new Course();
				this.courseBean(rs, co);
			}
			
		} catch (SQLException e) {
			logger.error("获得课程信息byId: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		return co;
	}

	public boolean courseEditSubmit(Course co) {
		
		String sql = "update course set name = ?, credithour = ?, classhour = ?, practicehour = ? , remark = ? where id = ?";
		Connection conn = null;
		PreparedStatement pst = null;
		int row = 0;

		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, co.getName(), co.getCredithour(), co.getClasshour(), co.getPracticehour(), co.getRemark(), co.getId());
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error("修改课程信息: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return row > 0? true: false;
	}

	public boolean courseDel(String id) {
		String sql = "delete from course where id = ?"; 
		Connection conn = null;
		PreparedStatement pst = null;
		int row = 0;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, id);
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error("课程删除: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return row > 0? true: false;
	}

	public Course getCourseByName(String name) {
		String sql = "select * from course where name = ?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Course course = null;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, name);
			//System.out.println(pst.toString().split(": ")[1]);
			
			rs = pst.executeQuery();
			logger.debug(sql);
			if(rs.next()) {
				course = new Course();
				this.courseBean(rs,course);
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return course;
	}

	public List<Course> list() {
		
		String sql = "select * from course";
		
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Course> list = new ArrayList<Course>();
		
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			//System.out.println(pst.toString().split(": ")[1]);
			rs = pst.executeQuery();
			logger.debug(sql);
			while(rs.next()) {
				Course c = new Course();
				this.courseBean(rs, c);
				list.add(c);
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return list.size()>0?list:null;
	}

}
