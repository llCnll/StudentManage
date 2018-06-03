package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import utils.DataSourceUtils;
import dao.ClassesDao;
import dao.CourseDao;
import dao.StudentDao;
import dbtools.DB;
import domain.Classes;
import domain.Course;
import domain.Student;

public class StudentDaoImpl implements StudentDao {
	
	private ClassesDao cd = new ClassesDaoImpl();
	private CourseDao cod = new CourseDaoImpl();
	
	private void studentBean(ResultSet rs, Student st) throws SQLException{
		st.setId(rs.getString("id"));
		st.setName(rs.getString("name"));
		st.setPwd(rs.getString("pwd"));
		Classes classes = cd.getClassesByCid(rs.getString("classesId"));
		st.setClasses(classes);
		st.setRoleId(rs.getInt("roleId"));
		
		//封装选课信息
		List<Course> courses = stduentCourse(st.getId());
		st.setCourses(courses);
	}
	
	//登录
	public Student login(String id, String pwd) {

		String sql = "select * from student where id = ? && pwd = ?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Student st = null;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, id, pwd);
			//System.out.println(pst.toString().split(": ")[1]);
			//System.out.printf("select * from student where id = %s && pwd = %s\n", id, pwd);
			
			rs = pst.executeQuery();
			System.out.println(sql);
			
			if(rs.next()) {
				st = new Student();
				this.studentBean(rs, st);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				DataSourceUtils.closeAll(pst, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return st;
	}
	/*public List<Student> list(String id,String name,String classes){
		if(id 不为空)
			 //按id查询
		else if(name 不为空 并且 classes 为0)
			//按name模糊查询
		else if(name 不为空 并且 classes 不为0)
			//按name模糊查询 并且按classes查询
			sql ="select * from student where name like '%"+name+"%' and classes= "+classes;
		String sql="select * from student where ";
		if(id 不为空)
			 sql+="and id="+id;
		if(name不为空)
			  sql +="and name like '%"+name+"%'";
		if(classes 不为0)
			  sql +="and classes='"+classes+"'";
		
		select * from student where id=642001 and name like '%zhangsan%' and classes=1;
		
		sql=sql.replaceAll("where and", "where");
		return null;
	}*/
	//所有非管理用户信息
	public List<Student> listGetSt(Integer index, Integer currentCount, Object... param) {

		String sql = "select * from student where ";
		//[0]: id  [1]: name  [2]: classes
		ArrayList<Object> paramlist = new ArrayList<Object>(Arrays.asList(param));
		
		if(param[0] != null && !("").equals(param[0])){
			sql += "&& id = ?";
			paramlist.remove(1);
			paramlist.remove(1);
		}else{
			if(param[1] != null && !("").equals(param[1])){
				paramlist.set(1, "%"+paramlist.get(1)+"%");
				sql += "&& name like ?";
			}
			if(param[2] != null && !("").equals(param[2]) && Integer.parseInt((String) param[2]) > 0){
				sql += "&& classesId = ?";
			}
		}
		paramlist.removeAll(Collections.singleton(""));
		paramlist.removeAll(Collections.singleton(null));
		paramlist.removeAll(Collections.singleton("-1"));
		
		sql += "&& roleId = 0";
		
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
		List<Student> list = new ArrayList<Student>();
		
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, param);
			//System.out.println(pst.toString().split(": ")[1]);
			System.out.println(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Student st = new Student();
				this.studentBean(rs, st);
				list.add(st);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				DataSourceUtils.closeAll(pst, rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list.size()>0?list:null;
	}
	//所有用户信息
	public List<Student> list(Integer index, Integer currentCount, Object... param) {
		
		String sql = "select * from student where ";
		//[0]: id  [1]: name  [2]: classes
		ArrayList<Object> paramlist = new ArrayList<Object>(Arrays.asList(param));
		
		if(param[0] != null && !("").equals(param[0])){
			sql += "&& id = ?";
			paramlist.remove(1);
			paramlist.remove(1);
		}else{
			if(param[1] != null && !("").equals(param[1])){
				paramlist.set(1, "%"+paramlist.get(1)+"%");
				sql += "&& name like ?";
			}
			if(param[2] != null && !("").equals(param[2]) && Integer.parseInt((String) param[2]) > 0){
				sql += "&& classesId = ?";
			}
		}
		paramlist.removeAll(Collections.singleton(""));
		paramlist.removeAll(Collections.singleton(null));
		paramlist.removeAll(Collections.singleton("-1"));
		
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
		List<Student> list = new ArrayList<Student>();
		
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, param);
			//System.out.println(pst.toString().split(": ")[1]);
			rs = pst.executeQuery();
			System.out.println(sql);
			while(rs.next()) {
				Student st = new Student();
				this.studentBean(rs, st);
				list.add(st);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return list.size()>0?list:null;
	}
	//添加用户
	public boolean studentAdd(Student st) {

		String sql = "insert into student values(?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement pst = null;
		int row = 0;

		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, st.getId(), st.getName(), st.getPwd(), st.getClasses().getId(), st.getRoleId());
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			System.out.println(sql);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return row > 0? true: false;
	}
	//删除用户
	public boolean studentDel(String id) {

		String sql = "delete from student where id = ?"; 
		Connection conn = null;
		PreparedStatement pst = null;
		int row = 0;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, id);
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			System.out.println(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return row > 0? true: false;
	}
	//查找根据id
	public Student studentEdit(String id) {

		String sql = "select * from student where id = ?";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Student st = null;
		
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, id);
			//System.out.println(pst.toString().split(": ")[1]);
			rs = pst.executeQuery();
			System.out.println(sql);
			while(rs.next()) {
				st = new Student();
				this.studentBean(rs, st);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return st;
	}
	//更改用户信息
	public boolean studentEditSubmit(Student st) {
		
		String sql = "update student set name = ?, pwd = ?, classesId = ?, roleId = ? where id = ?";
		Connection conn =null;
		PreparedStatement pst = null;
		int row = 0;

		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, st.getName(), st.getPwd(), st.getClasses().getId(), st.getRoleId(), st.getId());
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			System.out.println(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return row > 0? true: false;
	}
	//获得所有用户数量
	public int getCount() {
		
		String sql = "select count(id) from student";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			//System.out.println(pst.toString().split(": ")[1]);
			
			rs = pst.executeQuery();
			System.out.println(sql);
			if(rs.next()) {
				count = rs.getInt("count(id)");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return count;
	}
	//获得所有用户数量
	public int getCountGetSt() {
		
		String sql = "select count(id) from student where roleId = 0";
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		int count = 0;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			//System.out.println(pst.toString().split(": ")[1]);
			
			rs = pst.executeQuery();
			System.out.println(sql);
			if(rs.next()) {
				count = rs.getInt("count(id)");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return count;
	}

	private List<Course> stduentCourse(String id) {

		String sql = "select * from stcourse where stid = ?";
		
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
			System.out.println(sql);
			while(rs.next()) {
				courseIds.add(rs.getString("cid"));
			}
			for(String courseId : courseIds){
				Course course = cod.courseEdit(courseId);
				courses.add(course);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return courses.size()>0?courses:null;
	}

	public boolean addSelectCourse(String stid, String cid) {
		
		String sql = "insert into stcourse values(null,?,?)";
		Connection conn = null;
		PreparedStatement pst = null;
		int row = 0;

		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, stid, cid);
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			System.out.println(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return row > 0? true: false;
	}

	public boolean delSelectCourse(String stid, String cid) {
		
		String sql = "delete from stcourse where stid = ? && cid = ?"; 
		Connection conn = null;
		PreparedStatement pst = null;
		int row = 0;
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, stid, cid);
			//System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();
			System.out.println(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {e.printStackTrace();}
		}
		
		return row > 0? true: false;
	}

}
