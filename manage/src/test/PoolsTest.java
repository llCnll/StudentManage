package test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import utils.DataSourceUtils;
import dbtools.DB;

public class PoolsTest extends Thread{

	public void run() {
		int i = 0;
		for(i = 0; i < 10; ++i){
			System.out.println(Thread.currentThread().getName()+"正在获取链接"+i);
			Connection conn = null;
			PreparedStatement pst = null;
			ResultSet rs = null;
			try {
				conn= DataSourceUtils.getConnection();
				String sql = "select * from student where id=16422001";
				pst = conn.prepareStatement(sql);
				rs = pst.executeQuery();
				rs.next();
				System.out.println(rs.getString("id"));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				DataSourceUtils.closeAll(pst, rs);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("===结束====\n");
			
		}
	}
	
	@Test
	public void fun(){
		for(int i = 0; i < 50; ++i){
			System.out.println(i+"线程启动中...");
			new PoolsTest().start();
		}
	}

}
