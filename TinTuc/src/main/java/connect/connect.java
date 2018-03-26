package connect;
import java.sql.Connection;
import java.sql.DriverManager;


public class connect {
	
	 private static final String DRIVER_JDBC = "com.mysql.jdbc.Driver";
	//	private static final String DRIVER_JDBC = "com.mysql.cj.jdbc.Driver";
	private static final String URL_DB = "jdbc:google:mysql://test2-197607:us-west1:demo/demo?user=root&password=123";
		//private static final String URL_DB = "jdbc:mysql://google/demo?cloudSqlInstance=test3-199201:us-central1:demo&socketFactory=com.google.cloud.sql.mysql.SocketFactory&user=root&password=123";
	// private static final String URL_DB = "jdbc:mysql://localhost/demo";
	    // Pass and User
	    private static final String USER = "root";
	    private static final String PASS = "";

	    private static Connection conn;

	    public static Connection getConnection() {
	        try {
	            Class.forName(DRIVER_JDBC);
	            conn = DriverManager.getConnection(URL_DB);
	         //   conn = DriverManager.getConnection(URL_DB,USER,PASS);
	        } catch (Exception e) {
	            System.out.println("Error connection " + e);
	        }

	        return conn;
	    }
}
