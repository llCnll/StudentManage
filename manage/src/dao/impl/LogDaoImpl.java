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
import dao.LogDao;
import dbtools.DB;
import domain.Log;
import domain.Student;

public class LogDaoImpl implements LogDao {
	
	private Logger logger = Logger.getLogger(LogDaoImpl.class);
	
	private void logBean(ResultSet rs, Log log) throws SQLException {
		log.setClazz(rs.getString("class"));
		log.setCreatetime(rs.getString("createtime"));
		log.setId(rs.getInt("id"));
		log.setLoglevel(rs.getString("loglevel"));
		log.setMethod(rs.getString("method"));
		log.setMsg(rs.getString("msg"));
		Student st = new Student();
		st.setId(rs.getString("stid"));
		log.setStudent(st);
	}

	public int getCount(Object... param) {
		String sql = "select count(id) from log where class not like 'com.mchange%'";
		
		//[0]: stid  [1]: loglevel  [2]: createtime
		ArrayList<Object> paramlist = new ArrayList<Object>(Arrays.asList(param));
		if(param[0] != null && !("").equals(param[0])){
			sql += "&& stid = ?";
		}
		if(param[1] != null && !("").equals(param[1]) &&!("-1").equals(param[1])){
			sql += "&& loglevel like ?";
		}
		if(param[2] != null && !("").equals(param[2])){
			paramlist.set(2, "%"+paramlist.get(2)+"%");
			sql += "&& createtime like ?";
		}
		paramlist.removeAll(Collections.singleton(""));
		paramlist.removeAll(Collections.singleton(null));
		paramlist.removeAll(Collections.singleton("-1"));
		
		//判断 where 和 where &&
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
			DB.fillStatement(pst, param);
			//System.out.println(pst.toString().split(": ")[1]);
			rs = pst.executeQuery();
			logger.debug(sql);
			if(rs.next()) {
				count = rs.getInt("count(id)");
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return count;
	}

	public List<Log> list(int index, Integer currentCount, Object... param) {
		String sql = "select * from log where class not like 'com.mchange%'";
		//[0]: stid  [1]: loglevel  [2]: createtime
		ArrayList<Object> paramlist = new ArrayList<Object>(Arrays.asList(param));
		if(param[0] != null && !("").equals(param[0])){
			sql += "&& stid = ?";
		}
		if(param[1] != null && !("").equals(param[1]) &&!("-1").equals(param[1])){
			sql += "&& loglevel like ?";
		}
		if(param[2] != null && !("").equals(param[2])){
			paramlist.set(2, "%"+paramlist.get(2)+"%");
			sql += "&& createtime like ?";
		}
		paramlist.removeAll(Collections.singleton(""));
		paramlist.removeAll(Collections.singleton(null));
		paramlist.removeAll(Collections.singleton("-1"));
		
		//判断 where 和 where &&
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
		List<Log> list = new ArrayList<Log>();
		
		try {
			conn = DataSourceUtils.getConnection();
			pst = conn.prepareStatement(sql);
			DB.fillStatement(pst, param);
			//System.out.println(pst.toString().split(": ")[1]);
			rs = pst.executeQuery();
			logger.debug(sql);
			while(rs.next()) {
				Log log = new Log();
				this.logBean(rs, log);
				list.add(log);
			}
			
		} catch (SQLException e) {
			logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));
		}finally{
			try {DataSourceUtils.closeAll(pst, rs);} catch (SQLException e) {logger.error(e.getMessage().replaceAll("'", "\\\\\\\'"));}
		}
		
		return list.size()>0?list:null;
	}


}
