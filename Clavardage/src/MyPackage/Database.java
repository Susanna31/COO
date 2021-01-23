package MyPackage;
import java.sql.*;

public class Database {
	private Connection con;	
	private String username = "tp_servlet_017";
	private String pswd = "ea0Aijoh";
	
	public Connection init(){
			
		try {
			Class.forName ("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(
                  "jdbc:mysql://srv-bdens.insa-toulouse.fr/tp_servlet_017"
                  , username
                  , pswd);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
}
