package dbtools;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
	private static String driverClass;
	private static String jdbcUrl;
	private static String user;
	private static String password;
	
	static{
		try {
			ClassLoader loader = DB.class.getClassLoader();
			InputStream in = loader.getResourceAsStream("db.properties");
			Properties prop = new Properties();
			prop.load(in);
			driverClass = prop.getProperty("driverClass");
			jdbcUrl = prop.getProperty("jdbcUrl");
			user = prop.getProperty("user");
			password = prop.getProperty("password");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//连接
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(driverClass);
			conn =  DriverManager.getConnection(jdbcUrl, user, password);		
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void fillStatement(PreparedStatement st, Object... param){
		
		if(param == null){
			return ;
		}else{
			int i = 1;
			for(Object obj : param){
				try {
					st.setObject(i, obj);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}
	}
	
	//关闭资源
	public static void close(PreparedStatement st ,Connection conn){
		close(null, st, conn);
	}
	public static void close(ResultSet rs, PreparedStatement st ,Connection conn){
		try {
			if(rs!=null){
				rs.close();
			}
			if(st!=null){
				st.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
