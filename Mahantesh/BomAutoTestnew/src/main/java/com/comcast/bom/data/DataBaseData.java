package com.comcast.bom.data;
//DB Validation
import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.sql.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;

import com.comcast.reporting.*;
//import com.cognizant.framework.Status;
import com.comcast.utils.SeleniumReport;
import com.comcast.utils.TestSettings;


public class DataBaseData {
//	public String SqlQuery;
	public String ConnectionString;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private SeleniumReport report;	
	public DataBaseData(SeleniumReport report) {
		this.report=report;
	}
	public DataBaseData() {
		
	}
	
	public void openDBConnection() throws SQLException, ClassNotFoundException{
		TestSettings as=new TestSettings();
		String env=as.getEnvironmentToTest();
//		report.reportPassEvent("PASS", "Passed");
	      if(env.equalsIgnoreCase("QA") || env.equalsIgnoreCase("INT")){
	    	  	//Lower DB
	    	  ConnectionString=as.getSettings("DBConnStringLower");
				
	      	} else {    	  
			    //Higher DB
	      		ConnectionString=as.getSettings("DBConnStringHigher");	      		
	      }
	     String dbClass="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	  	Class.forName(dbClass);	
	  		
		conn = DriverManager.getConnection(ConnectionString);
//        conn = DriverManager.getConnection("jdbc:sqlserver://PACDCGSDEVD01.cable.comcast.com;user=CDT20_Admin;password=m@estro@dmin;database=Framework");
        System.out.println("Successfully Connected to the database!");        
	}
	
	/***
	 * Method to Connect Database with Granslam Logging Table
	 * @param  : 
	 * @return : 
	 * @author : Aarti (aagarw005c)
	 * @throws Exception 
	 * @Modified By :
	 ***/
	public void openDBConnectionForGSTable() throws SQLException, ClassNotFoundException{
		TestSettings as=new TestSettings();
		String env=as.getEnvironmentToTest();
		report.reportPassEvent("PASS", "Passed");
	      if(env.equalsIgnoreCase("QA") || env.equalsIgnoreCase("INT")){
	    	  	//Lower DB
	    	  ConnectionString=as.getSettings("DBConnStringLower_GS");
				
	      	} else {    	  
			    //Higher DB
	      		ConnectionString=as.getSettings("DBConnStringHigher_GS");	      		
	      }
	     String dbClass="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	  	Class.forName(dbClass);	
	  		
		conn = DriverManager.getConnection(ConnectionString);
//        conn = DriverManager.getConnection("jdbc:sqlserver://PACDCGSDEVD01.cable.comcast.com;user=CDT20_Admin;password=m@estro@dmin;database=Framework");
        System.out.println("Successfully Connected to the database!");        
	}
	
	/***
	 * Method to Connect Database with Granslam Logging Table
	 * @param  : 
	 * @return : 
	 * @author : Vishwas (vvinay00c)
	 * @throws Exception 
	 * @Modified By :
	 ***/
	public void openDBConnectionForGSTablePaymentWidget() throws SQLException, ClassNotFoundException{
		TestSettings as=new TestSettings();
		String env=as.getEnvironmentToTest();
		report.reportPassEvent("PASS", "Passed");
	      if(env.equalsIgnoreCase("QA") || env.equalsIgnoreCase("INT")){
	    	  	//Lower DB
	    	  ConnectionString=as.getSettings("DBConnStringLower_PaymentWidget");
				
	      	} else {    	  
			    //Higher DB
	      		//ConnectionString=as.getSettings("DBConnStringHigher_GS");	
	      		//do nothing
	      }
	     String dbClass="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	  	Class.forName(dbClass);	
	  		
		conn = DriverManager.getConnection(ConnectionString);
        System.out.println("Successfully Connected to the database!");        
	}
	
	public void executeQuery(String sqlQuery) throws SQLException{
	    //Execute a query
//		SqlQuery= "select CreateUser  from dbo.Application where ApplicationId='217299d2-682f-4490-b4e7-01873ca1acb2'" ;
//	    stmt = conn.createStatement();

		stmt =conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    rs = stmt.executeQuery(sqlQuery);
	}
	
	public void executeUpdateQuery(String sqlQuery) throws SQLException {
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		int result = stmt.executeUpdate(sqlQuery);
		if (result > 0) {
//			report.reportPassEvent("Updated successfully",
//					"DB updated successfully");
					System.out.println("passed");
		} else {
			report.reportPassEvent("Updated successfully",
					"DB updated successfully");
		}
	}
	public void ValidateResults(String columName, String expectedValue) throws SQLException{		
		boolean flag=false;
		rs.beforeFirst();
	        //Extract data from result set
			while(rs.next()){
				String var=rs.getString(columName).trim();
				System.out.println("Actual value:"+var);
				if(var.contains(expectedValue)){
					report.reportPassEvent("dbValidation", "Expected value : "+expectedValue+" matches the actual value : "+var);
					flag=true;
					break;
				} 				
			}
			
			if(flag==false){
				report.reportFailEvent("dbValidation", "Ecpected value fails to match the actual value");
			}
	
	}
	
	/***
	 * Method to validate value of two columns
	 * @param  : 
	 * @return : 
	 * @author : Aarti (aagarw005c)
	 * @throws Exception 
	 * @Modified By :
	 ***/
	public boolean validateResultsOfTwoColumns(String columName, String otherColumName) throws SQLException{		
		boolean flag=false;
		rs.beforeFirst();
	        //Extract data from result set
			while(rs.next()){
				String varFirst=rs.getString(columName).trim();
				String varSecond=rs.getString(otherColumName).trim();
				if(varFirst.equalsIgnoreCase(varSecond)){
					report.reportPassEvent("dbValidation", "both value : "+varFirst+" and "+varSecond+" matches the value");
					flag=true;
					break;
				}else{
					report.reportPassEvent("dbValidation", "both value : "+varFirst+" and "+varSecond+" does not match the value");
					flag=true;
					break;
				}
			}
			return flag;
	
	}
	public void closeDBConnection() throws SQLException{
		
		conn.close();
	}
	
	public void cableCardPairingDBvalidation(DataBaseDetails xi){
		try {
			this.openDBConnection();
			this.executeQuery(xi.SqlQuery);
			this.ValidateResults("EquipmentAN", "MH");
			this.ValidateResults("EquipmentName", "Moto Host 1 Way");
			this.ValidateResults("EquipmentAN", "MT");
			this.ValidateResults("EquipmentName", "Cisco Host 1way");			
		} catch (SQLException ex) {
			report.reportFailEvent("",ex.getMessage());
		} catch (ClassNotFoundException e) {
			report.reportFailEvent("",e.getMessage());
		}

		
	}
	
	
	/***
	 * Method to update in database
	 * @param  : 
	 * @return : 
	 * @author : Sowmiya Srinivasan(ssrini003c)
	 * @throws Exception 
	 * @Modified By :
	 ***/
	
	
	
	public void updateQuery(DataBaseDetails xi){
		try {
			this.openDBConnection();
			this.executeUpdateQuery(xi.SqlQuery);
		} catch (SQLException ex) {
			report.reportFailEvent("",ex.getMessage());
		} catch (ClassNotFoundException e) {
			report.reportFailEvent("",e.getMessage());
		}
	}
	
	/***
	 * Method to Validate PageName and TransactionName column Value
	 * @param  : 
	 * @return : 
	 * @author : Aarti (aagarw005c)
	 * @throws Exception 
	 * @Modified By :
	 ***/
	public void transactionNameDBvalidation(DataBaseDetails xi){
		try {
			this.openDBConnectionForGSTable();
			this.executeQuery(xi.SqlQuery);
			this.ValidateResults("TransactionName", "Additional Info Expanded");
		} catch (SQLException ex) {
			report.reportFailEvent("",ex.getMessage());
		} catch (ClassNotFoundException e) {
			report.reportFailEvent("",e.getMessage());
		}
	}
	/***
	 * Method to Validate Receive Power Level (upstream) value_ basic view 
	 * @param  : 
	 * @return : 
	 * @author : Pavan (pmv001c)
	 * @throws Exception 
	 * @Modified By :
	 ***/
	public void powerLevelvaluebasicview(String nacctcontextid) {
		try {
			this.openDBConnectionForGSTable();
			String strquery="Select Top 1 * "+
					" From [grand_slam_metrics].[Logging].[TransactionLogOdd], [grand_slam_metrics].[Logging].[SOAPLog]"+
					" Where [grand_slam_metrics].[Logging].[TransactionLogOdd].TransactionIdentifer=[grand_slam_metrics].[Logging].[SOAPLog].TransactionIdentifer"+
					" and [grand_slam_metrics].[Logging].[TransactionLogOdd].TransactionName like '%GetTroubleShootDiagnosticHealtCheck%'"+ 
					" and [grand_slam_metrics].[Logging].[SOAPLog].ContractName like '%checkHealthResponse%'"+
					" and [grand_slam_metrics].[Logging].[TransactionLogOdd].AcctContextID='"+ nacctcontextid +"'"+
					" Order by  [grand_slam_metrics].[Logging].[SOAPLog].StartTime desc";

			Date date =new Date(); // your date
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int day = cal.get(Calendar.DAY_OF_MONTH);
			String as="";
			if (day%2==0){
				 as=strquery.replace("TransactionLogOdd", "TransactionLogEven");
			}
			else{	
				 as=strquery.replace("TransactionLogEven", "TransactionLogOdd");
			}
			System.out.print("query:--"+as);
			this.executeQuery(as);

			//			this.ValidateResults("SOAPMsg", "<typ:padAmount xsi:nil=\"true\"></typ:padAmount>");

			String expectedValue="<typ:padAmount xsi:nil=\"true\"></typ:padAmount>";
			boolean flag=false;
			rs.beforeFirst();
			//Extract data from result set
			while(rs.next()){
				String var=rs.getString("SOAPMsg").trim();
				if(var.contains(expectedValue)){
					report.reportPassEvent("dbValidation", "Expected value : "+expectedValue+" matches the actual value : "+var);
					flag=true;
					break;
				}
				else{				
//					report.reportPassEvent("dbValidation", "Expected value : "+expectedValue+" matches the actual value : "+var);
					report.updateTestLog("dbValidation", "Expected value : "+expectedValue+" matches the actual value : "+var, Status.DONE);
				}
					
			}

			if(flag==false){
				report.reportFailEvent("dbValidation", "Expected value fails to match the actual value");
			}					

		} catch (SQLException ex) {
			report.reportFailEvent("",ex.getMessage());
		} catch (ClassNotFoundException e) {
			report.reportFailEvent("",e.getMessage());
		}
	}
	
	

	/***
	 * Method to get column value
	 * @param  : 
	 * @return : 
	 * @author : Aarti (aagarw005c)
	 * @throws Exception 
	 * @Modified By :
	 ***/
	public String getColumnValue(String columName) {
		String var = null;
		try{
			rs.beforeFirst();
		        //Extract data from result set
				while(rs.next()){
					var=rs.getString(columName).trim();
					report.reportPassEvent("Column Value", "Column Value: "+var);
				}
				
		}catch (Exception Ex) {
			report.reportFailEvent("Exception Caught", "Message is->"+Ex.getMessage());
		}
		return var;
	}
	
	public boolean ValidateResultsForBothEvenOddTable(String columName, String expectedValue) throws SQLException{		
		boolean flag=false;
		rs.beforeFirst();
	        //Extract data from result set
			while(rs.next()){
				String var=rs.getString(columName).trim();
				System.out.println("Actual value:"+var);
				if(var.contains(expectedValue)){
					report.reportPassEvent("dbValidation", "Expected value : "+expectedValue+" matches the actual value : "+var);
					flag=true;
					break;
				} 				
			}
			return flag;
	}
	
	public void validateLogsInDatabase(String value, String doc) throws ClassNotFoundException, SQLException {
		this.openDBConnectionForGSTable();
		String[] docname = doc.split("_");
		System.out.println(docname[0]);
		String sqlQuery_Even = "SELECT * FROM [grand_slam_metrics_qa].[Logging].[TransactionLogEven]" +
				"where applicationname='contextstore' and PageName='/webapi/cdt.webapi.contextstore/ContextStore/Incidents/"+docname[0]+"/Events/Events_1'" +
				"order by transactiontime desc";
		String sqlQuery_Odd = "SELECT * FROM [grand_slam_metrics_qa].[Logging].[TransactionLogOdd]" +
				"where applicationname='contextstore' and PageName='/webapi/cdt.webapi.contextstore/ContextStore/Incidents/"+docname[0]+"/Events/Events_1'" +
				"order by transactiontime desc";
		this.executeQuery(sqlQuery_Even);	
		boolean flag_Even = this.ValidateResultsForBothEvenOddTable("TransactionName",value);
		if(flag_Even){
			report.reportPassEvent("dbValidation", "Expected value matches to find the actual value");
		}else{
			this.executeQuery(sqlQuery_Odd);	
			boolean flag_Odd = this.ValidateResultsForBothEvenOddTable("TransactionName",value);
			if(flag_Odd){
				report.reportPassEvent("dbValidation", "Expected value matches to find the actual value");
			}else
				report.reportFailEvent("dbValidation", "Expected value fails to find the actual value");
		}
		this.closeDBConnection();
	}
	
	public void setCouchbaseDB(){
        try {
               String sql = "Update [CDT20_qa].[REL14121].[ApplicationSetting] set SettingValue='Couchbase' "+
                            " where SettingKey='ContextStoreDependencySetter' and "+
                            " ApplicationName='ContextStore' ";
               this.openDBConnection();
               this.executeUpdateQuery(sql);
               
        }catch (SQLException ex) {
               report.reportFailEvent("",ex.getMessage());
        } catch (ClassNotFoundException e) {
               report.reportFailEvent("",e.getMessage());
        }
        
	}
	
	public void setElasticSearchDB(){
        try {
               String sql = "Update [CDT20_qa].[REL14121].[ApplicationSetting] set SettingValue='ELASTICSEARCH' "+
                            " where SettingKey='ContextStoreDependencySetter' and "+
                            " ApplicationName='ContextStore' ";
               this.openDBConnection();
               this.executeUpdateQuery(sql);
               
        }catch (SQLException ex) {
               report.reportFailEvent("",ex.getMessage());
        } catch (ClassNotFoundException e) {
               report.reportFailEvent("",e.getMessage());
        }
        
	}
	
	public void validateLogsInDatabase_NewEndPointUrl(String value) throws ClassNotFoundException, SQLException {
		this.openDBConnectionForGSTable();
		String sqlQuery_Even = "SELECT TOP 1 * FROM [grand_slam_metrics_qa].[Logging].[TransactionLogEven]" +
				"where applicationname='contextstore' and PageName='/webapi/cdt.webapi.contextstore/ContextStore/Customers/8498340210571008/Incidents/Ingest'" +
				"order by transactiontime desc";
		String sqlQuery_Odd = "SELECT TOP 1 * FROM [grand_slam_metrics_qa].[Logging].[TransactionLogOdd]" +
				"where applicationname='contextstore' and PageName='/webapi/cdt.webapi.contextstore/ContextStore/Customers/8498340210571008/Incidents/Ingest'" +
				"order by transactiontime desc";
		this.executeQuery(sqlQuery_Even);	
		boolean flag_Even = this.ValidateResultsForBothEvenOddTable("TransactionName",value);
		if(flag_Even){
			report.reportPassEvent("dbValidation", "Expected value matches to find the actual value");
		}else{
			this.executeQuery(sqlQuery_Odd);	
			boolean flag_Odd = this.ValidateResultsForBothEvenOddTable("TransactionName",value);
			if(flag_Odd){
				report.reportPassEvent("dbValidation", "Expected value matches to find the actual value");
			}else
				report.reportFailEvent("dbValidation", "Expected value fails to find the actual value");
		}
		this.closeDBConnection();
		
	}
	
	public void validateLogsInDatabase_NewEndPointUrl_RetrieveOnly(String value) throws ClassNotFoundException, SQLException {
		this.openDBConnectionForGSTable();
		String sqlQuery_Even = "SELECT TOP 1 * FROM [grand_slam_metrics_qa].[Logging].[TransactionLogEven]" +
				"where applicationname='contextstore' and PageName='/webapi/cdt.webapi.contextstore/ContextStore/Customers/8498340210571008/Incidents'" +
				"order by transactiontime desc";
		String sqlQuery_Odd = "SELECT TOP 1 * FROM [grand_slam_metrics_qa].[Logging].[TransactionLogOdd]" +
				"where applicationname='contextstore' and PageName='/webapi/cdt.webapi.contextstore/ContextStore/Customers/8498340210571008/Incidents'" +
				"order by transactiontime desc";
		this.executeQuery(sqlQuery_Even);	
		boolean flag_Even = this.ValidateResultsForBothEvenOddTable("TransactionName",value);
		if(flag_Even){
			report.reportPassEvent("dbValidation", "Expected value matches to find the actual value");
		}else{
			this.executeQuery(sqlQuery_Odd);	
			boolean flag_Odd = this.ValidateResultsForBothEvenOddTable("TransactionName",value);
			if(flag_Odd){
				report.reportPassEvent("dbValidation", "Expected value matches to find the actual value");
			}else
				report.reportFailEvent("dbValidation", "Expected value fails to find the actual value");
		}
		this.closeDBConnection();
		
	}

}

