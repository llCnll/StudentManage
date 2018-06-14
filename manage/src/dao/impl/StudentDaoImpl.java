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
import dao.ClassesDao;
import dao.ScoreDao;
import dao.StudentDao;
import dbtools.DB;
import domain.Classes;
import domain.Course;
import domain.Score;
import domain.Student;

public class StudentDaoImpl implements StudentDao {
	
	private ClassesDao cd = new ClassesDaoImpl();
	private ScoreDao scd = new ScoreDaoImpl();
	private Logger logger = Logger.getLogger(StudentDaoImpl.class);
	
	private void studentBean(ResultSet rs, Student st) throws SQLException{
		st.setId(rs.getString("id"));
		st.setName(rs.getString("name"));
		st.setPwd(rs.getString("pwd"));
		Classes classes = cd.getClassesByCid(rs.getString("classesId"));
		st.setClasses(classes);
		st.setRoleId(rs.getInt("roleId"));
		st.setFlag(rs.getInt("flag"));
		
		//封装选课信息
		List<Course> courses = scd.stduentCourse(st.getId());
		//已知选课的前提下, 封装成绩信息
		if(courses != null){
			for(Course course : courses){
				Score score = scd.studentScore(st.getId(), course.getId());
				course.setScore(score);
			}
		}
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
			logger.debug(sql);
			
			if(rs.next()) {
				st = new Student();
				this.studentBean(rs, st);
			}
			
		} catch (Exception e) {
			logger.error("用户登陆: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {
				DataSourceUtils.closeAll(pst, rs);
			} catch (SQLException e) {
				logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
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
	//获得非管理用户信息 分页 
	public List<Student> listGetSt(Integer index, Integer currentCount, Object... param) {

		String sql = "select * from student s where ";
		//[0]: id  [1]: courses [2]: name  [3]: classes
		ArrayList<Object> paramlist = new ArrayList<Object>(Arrays.asList(param));
		
		if(param[0] != null && !("").equals(param[0])){
			sql += "&& s.id = ?";
			paramlist.remove(1);
			paramlist.remove(1);
			paramlist.remove(1);
		}else{
			if(param[1] != null && !("").equals(param[1]) && Integer.parseInt((String) param[1]) > 0){
				sql = sql.substring(0, 24);
				sql += ", score sc where s.id = sc.studentid && sc.courseId = ?";
			}
			if(param[2] != null && !("").equals(param[2])){
				paramlist.set(2, "%"+paramlist.get(2)+"%");
				sql += "&& s.name like ?";
			}
			if(param[3] != null && !("").equals(param[3]) && Integer.parseInt((String) param[3]) > 0){
				sql += "&& s.classesId = ?";
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
			logger.debug(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Student st = new Student();
				this.studentBean(rs, st);
				list.add(st);
			}
			
		} catch (SQLException e) {
			logger.error("获得非管理用户信息 分页: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {
				DataSourceUtils.closeAll(pst, rs);
			} catch (SQLException e) {
				logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
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
			logger.debug(sql);
			while(rs.next()) {
				Student st = new Student();
				this.studentBean(rs, st);
				list.add(st);
			}
			
		} catch (SQLException e) {
			logger.error("所有用户信息: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
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
			logger.debug(sql);
			
		} catch (SQLException e) {
			logger.error("添加用户: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return row > 0? true: false;
	}
	//激活删除用户
	public boolean studentDelBack(String id) {
		
		String sql = "update student set flag = 1 where id = ?"; 
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
			logger.error("删除用户: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return row > 0? true: false;
	}
	//删除用户
	public boolean studentDel(String id) {

		String sql = "update student set flag = 0 where id = ?"; 
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
			logger.error("删除用户: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
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
			logger.debug(sql);
			while(rs.next()) {
				st = new Student();
				this.studentBean(rs, st);
			}
			
		} catch (SQLException e) {
			logger.error("获得用户信息byId: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
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
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error("修改用户信息: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, null);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
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
			logger.debug(sql);
			if(rs.next()) {
				count = rs.getInt("count(id)");
			}
			
		} catch (SQLException e) {
			logger.error("获得所有用户数量: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return count;
	}
	//获得所有用户数量
	public int getCount(Object... param) {
		
		String sql = "select count(id) from student where ";
		
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
		
		param = paramlist.toArray();
		
		//判断 where 和 where &&
		if(sql.endsWith("where ")){
			sql = sql.substring(0, sql.indexOf("where ")-1);
		}
		if(sql.contains("where &&")){
			sql = sql.replaceAll("where &&", "where");
		}
		
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
			logger.error("所有用户数量: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return count;
	}
	//获得所有学生数量
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
			logger.debug(sql);
			if(rs.next()) {
				count = rs.getInt("count(id)");
			}
			
		} catch (SQLException e) {
			logger.error("获得所有学生数量: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return count;
	}
	//获得所有学生数量 按条件
	public int getCountGetSt(Object[] param) {
		
		String sql = "select count(name) from student s where roleId = 0 ";
		
		//[0]: id  [1]: courses [2]: name  [3]: classes
		ArrayList<Object> paramlist = new ArrayList<Object>(Arrays.asList(param));
		
		if(param[0] != null && !("").equals(param[0])){
			sql += "&& id = ?";
			paramlist.remove(1);
			paramlist.remove(1);
			paramlist.remove(1);
		}else{
			if(param[1] != null && !("").equals(param[1]) && Integer.parseInt((String) param[1]) > 0){
				sql = sql.substring(0, 34);
				sql += ", score sc where s.id = sc.studentid && sc.courseId = ? && s.roleId = 0 ";
			}
			if(param[2] != null && !("").equals(param[2])){
				paramlist.set(2, "%"+paramlist.get(2)+"%");
				sql += "&& s.name like ?";
			}
			if(param[3] != null && !("").equals(param[3]) && Integer.parseInt((String) param[3]) > 0){
				sql += "&& s.classesId = ?";
			}
		}
		paramlist.removeAll(Collections.singleton(""));
		paramlist.removeAll(Collections.singleton(null));
		paramlist.removeAll(Collections.singleton("-1"));
		
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
				count = rs.getInt("count(name)");
			}
			
		} catch (SQLException e) {
			logger.error("获得所有学生数量 按条件: "+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return count;
	}
	//所有学生信息
	public List<Student> listGetSt(Object[] param) {
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
			logger.debug(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				Student st = new Student();
				this.studentBean(rs, st);
				list.add(st);
			}
			
		} catch (SQLException e) {
			logger.error("所有学生信息:"+e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {
				DataSourceUtils.closeAll(pst, rs);
			} catch (SQLException e) {
				logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
			}
		}
		
		return list.size()>0?list:null;
	}
}
