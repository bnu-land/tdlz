package cn.edu.bnu.land.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class dataSource {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection;
		connection = DriverManager.getConnection("jdbc:mysql://202.112.95.210:3306/tdlz","root","root");
//		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	
//		String dbur1 = "jdbc:odbc:JSPAccess";
		Properties prop = new Properties();   
		prop.put("charSet", "utf-8");    //解决中文乱码
//		System.out.println("realPath:"+dbur1);
//		return DriverManager.getConnection(dbur1);
		return connection;
	}
}
