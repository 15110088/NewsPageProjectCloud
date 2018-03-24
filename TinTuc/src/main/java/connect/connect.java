package connect;
import java.sql.Connection;
import java.sql.DriverManager;


public class connect {
	 private static final String DRIVER_JDBC = "com.mysql.jdbc.Driver";
	    private static final String URL_DB = "jdbc:mysql://35.230.89.36/demo";
	    //private static final String URL_DB = "jdbc:mysql://localhost/demo";
	    // Pass and User
	    private static final String USER = "root";
	    private static final String PASS = "";

	    private static Connection conn;

	    public static Connection getConnection() {
	        try {
	            Class.forName(DRIVER_JDBC);
	            conn = DriverManager.getConnection(URL_DB, USER, PASS);
	        } catch (Exception e) {
	            System.out.println("Error connection " + e);
	        }

	        return conn;
	    }
}
