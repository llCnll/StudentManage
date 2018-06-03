package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dao.ClassesDao;
import dbtools.DB;
import domain.Classes;
import domain.Student;

public class ClassesDaoImpl implements ClassesDao {
	
	private void classesBean(ResultSet rs, Classes classes) throws SQLException{
		classes.setId(rs.getInt("id"));
		classes.setName(rs.getString("name"));
		classes.setGrade(rs.getInt("grade"));
		classes.setMajor(rs.getString("major"));
	}

	public Classes getClassesByCid(String classesId) {
		
		String sql = "select * from classes where id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		Classes classes = null;
		try {
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, classesId);
			System.out.println(pst.toString().split(": ")[1]);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				classes = new Classes();
				this.classesBean(rs,classes);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return classes;
		
	}

	public List<Classes> list() {

		String sql = "select * from classes";
		
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		List<Classes> list = new ArrayList<Classes>();
		
		try {
			pst = conn.prepareStatement(sql);
			System.out.println(pst.toString().split(": ")[1]);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Classes c = new Classes();
				this.classesBean(rs, c);
				list.add(c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return list.size()>0?list:null;
		
	}

	public Classes getClassesByName(String name) {
		
		String sql = "select * from classes where name = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		Classes classes = null;
		try {
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, name);
			System.out.println(pst.toString().split(": ")[1]);
			
			ResultSet rs = pst.executeQuery();
			
			if(rs.next()) {
				classes = new Classes();
				this.classesBean(rs,classes);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return classes;
	}

	public int getCount() {
		
		String sql = "select count(id) from classes";
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

	public List<Classes> list(int index, Integer currentCount, Object[] param) {
		String sql = "select * from classes where ";
		//[0]: id  [1]: name  [2]: classes
		ArrayList<Object> paramlist = new ArrayList<Object>(Arrays.asList(param));
		
		if(param[0] != null && !("").equals(param[0])){
			sql += "&& name = ?";
			paramlist.remove(1);
			paramlist.remove(1);
		}else{
			if(param[1] != null && !("").equals(param[1]) && Integer.parseInt((String) param[1]) > 0){
				paramlist.set(1, paramlist.get(1));
				sql += "&& grade = ?";
			}
			if(param[2] != null && !("").equals(param[2]) && !("-1").equals(param[2])){
				sql += "&& major = ?";
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
		
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		List<Classes> list = new ArrayList<Classes>();
		
		try {
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, param);
			System.out.println(pst.toString().split(": ")[1]);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				Classes cl = new Classes();
				this.classesBean(rs, cl);
				list.add(cl);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return list.size()>0?list:null;
	}

	public List<String> classesGradeSelect() {
		String sql = "select distinct grade from classes";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		List<String> list = new ArrayList<String>();
		try {
			pst = conn.prepareStatement(sql);
			System.out.println(pst.toString().split(": ")[1]);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				list.add(String.valueOf(rs.getInt("grade")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return list;
	}

	public List<String> classesMajorSelect() {
		
		String sql = "select distinct major from classes";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		List<String> list = new ArrayList<String>();
		try {
			pst = conn.prepareStatement(sql);
			System.out.println(pst.toString().split(": ")[1]);
			
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				list.add(rs.getString("major"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return list;
	}

	public boolean studentAdd(Classes cl) {
		
		String sql = "insert into classes values(null,?,?,?)";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		int row = 0;

		try {
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, cl.getName(), cl.getGrade(), cl.getMajor());
			System.out.println(pst.toString().split(": ")[1]);
			row = pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return row > 0? true: false;
	}

	public boolean classesDel(String id) {
		String sql = "delete from classes where id = ?"; 
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

	public Classes studentEdit(String id) {
		
		String sql = "select * from classes where id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		Classes cl = null;
		
		try {
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, id);
			System.out.println(pst.toString().split(": ")[1]);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next()) {
				cl = new Classes();
				this.classesBean(rs, cl);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DB.close(pst, conn);
		}
		
		return cl;
	}

	public boolean studentEditSubmit(Classes cl) {
		
		String sql = "update classes set name = ?, grade = ?, major = ? where id = ?";
		Connection conn = DB.getConnection();
		PreparedStatement pst = null;
		int row = 0;

		try {
			pst = conn.prepareStatement(sql);
			
			DB.fillStatement(pst, cl.getName(), cl.getGrade(), cl.getMajor(), cl.getId());
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
