package DBValidation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DBValidate {
	
	public void DBValidations() throws ClassNotFoundException, SQLException, IOException, InstantiationException, IllegalAccessException
	{
		FileOutputStream f = new FileOutputStream("C:\\OCRscreenshots\\DBValidation.txt");
	     
	     System.setOut(new PrintStream(f));
		System.out.println("---Validating Datbase Initial setups---");
		mysqldbConnect() ;
		
		oracleDBValidate("C:/Users/441870/workspace/CancelAccounts.xlsx","RTPENV",1);
		oracleDBValidate("C:/Users/441870/workspace/CancelAccounts.xlsx","RTPENV",2);
		oracleDBValidate("C:/Users/441870/workspace/CancelAccounts.xlsx","RTPENV",3);
		oracleDBValidate("C:/Users/441870/workspace/CancelAccounts.xlsx","RTPENV",4);
	}
	
	public Statement mysqldbConnect() throws ClassNotFoundException,InstantiationException, IllegalAccessException, SQLException
	{
	
	 Class.forName("com.mysql.jdbc.Driver").newInstance();
	
	   
	   final String DB_URL = "jdbc:mysql://10.255.159.180:3307/my_sql";

	   //  Database credentials
	   final String USER = "mithun";
	   final String PASS = "Tester12";   
	   String sql; //Truncate my_sql.parasoftentry;
	   System.out.println("Connecting to Database");
	   Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
	      
	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      Statement st = connection.createStatement();
	     
            
             
	      System.out.println("---Validating SQL DB---");
             
	      sql = "Truncate my_sql.parasoftentry";
	     int i= st.executeUpdate(sql);
	     System.out.println("Truncated Parasoft entry table");
      
	      st.close();
	      connection.close();
	   	
		return st;
	}
	
	//------------------------------------------------------
	
	public Statement dBConnect(String FilePath,String sheetname,int rows)
			throws ClassNotFoundException, SQLException, IOException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		FileInputStream fs = new FileInputStream(FilePath);	
		XSSFWorkbook wb = new XSSFWorkbook(fs);
		XSSFSheet sheet = wb.getSheetAt(1);
		Row row = sheet.getRow(rows); 
		Cell cell ;
		String host = row.getCell(2).toString();
		String password =  row.getCell(4).toString();
		String username =  row.getCell(3).toString();
		String port =  row.getCell(5).toString().replace(".0", "").trim();
		
		String sid =  row.getCell(6).toString();
		
		System.out.println("The Values are::" + host + ":" + port + ":"
				+ sid + ":" + username + password);

		Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host
				+ ":" + port + ":" + sid + "", username, password);

		Statement st = connection.createStatement();
		return st;
	}
	
	//---------------------------------------------------------------

	
	public void oracleDBValidate(String FilePath,String sheetname,int rows)
			throws ClassNotFoundException, SQLException, IOException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Statement st =dBConnect(FilePath,sheetname,rows);
		System.out.println("---Validating Oracle DB---");
		ResultSet rs = st.executeQuery("truncate table thirtydaysdisconnect");
		System.out.println("1 ) truncate table thirtydaysdisconnect");
		ResultSet rs1 = st.executeQuery("truncate table threedayscvrdisconnect");
		System.out.println("2 ) truncate table threedayscvrdisconnect");
		ResultSet rs2 = st.executeQuery("truncate table scheduledisconnect");
		System.out.println("3 ) truncate table scheduledisconnect");
		st.close();
	}
	
}
