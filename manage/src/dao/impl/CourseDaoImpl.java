package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dao.CourseDao;
import dbtools.DB;
import domain.Classes;
import domain.Course;
import domain.Student;
import utils.DataSourceUtils;

public class CourseDaoImpl implements CourseDao {
	
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
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		int count = 0;
		try {
			pst = conn.prepareStatement(sql);
			System.out.println(pst.toString().split(": ")[1]);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt("count(id)");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
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
		
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		List<Course> list = new ArrayList<Course>();
		
		try {
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, param);
			System.out.println(pst.toString().split(": ")[1]);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Course co = new Course();
				this.courseBean(rs, co);
				list.add(co);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return list.size()>0?list:null;
	}

	public boolean courseAdd(Course co) {
		
		String sql = "insert into course values(?,?,?,?,?,?)";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		int row = 0;

		try {
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, co.getId(), co.getName(), co.getCredithour(), co.getClasshour(), co.getPracticehour(), co.getRemark());
			System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return row > 0? true: false;
	}

	public Course courseEdit(String id) {
		
		String sql = "select * from course where id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		Course co = null;
		
		try {
			
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, id);
			System.out.println(pst.toString().split(": ")[1]);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				co = new Course();
				this.courseBean(rs, co);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		return co;
	}

	public boolean courseEditSubmit(Course co) {
		
		String sql = "update course set name = ?, credithour = ?, classhour = ?, practicehour = ? , remark = ? where id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		int row = 0;

		try {
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, co.getName(), co.getCredithour(), co.getClasshour(), co.getPracticehour(), co.getRemark(), co.getId());
			System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return row > 0? true: false;
	}

	public boolean courseDel(String id) {
		String sql = "delete from course where id = ?"; 
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		int row = 0;
		try {
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, id);
			System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return row > 0? true: false;
	}

}
