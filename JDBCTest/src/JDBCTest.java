import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCTest {
	public JDBCTest(){
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/StudentGrades?user=root&password="
												+ " FROM Student s, Class c, Grade g"
												+ " WHERE s.studentID=g.studentID AND"
												+ " c.classID=g.classID;");
			st = conn.createStatement(); 
			rs = st.executeQuery("SELECT studentID, fname, lname FROM Student");
			while(rs.next()){
				int studentID = rs.getInt("studentID");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				System.out.println(studentID + ": " + fname + " " + lname);
			}
		}catch(ClassNotFoundException cnfe){
			System.out.println("cnfe: " + cnfe.getMessage());
			cnfe.printStackTrace();
		}catch(SQLException sqle){
			System.out.println("cnfe: " + sqle.getMessage());
			sqle.printStackTrace();
		}finally{
			try {
				st.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String [] args){
		new JDBCTest();
	}
}
