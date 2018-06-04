package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DataSourceUtils;
import dao.ScoreDao;
import dbtools.DB;
import domain.Score;

public class ScoreDaoImpl implements ScoreDao {
	
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
			System.out.println(sql);
			
			if(rs.next()) {
				score = new Score();
				this.scoreBean(rs, score);
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
		return score;
	}


}
