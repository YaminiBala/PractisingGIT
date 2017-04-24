import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;



import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class ScheduleUpDownOLd 
{
	
	    LibraryRtp sL=new LibraryRtp();
	    RTP_ValidationsClass rV=new RTP_ValidationsClass();
	    RTP_SimulatorClass rS=new RTP_SimulatorClass();
	    RTP_SimulatorClass_UpDown rU=new RTP_SimulatorClass_UpDown();
	    RTP_ValidationsClass_UpDown rVU=new RTP_ValidationsClass_UpDown();
	    LibraryRtp_UpDown lRU=new LibraryRtp_UpDown();
	    CancelClass cc=new CancelClass();
	   
	    
	    public void Initialization(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
	    {
	      sL.getBaseTime(input, c);
	      rV.valuesFromAggregrate(input, c);
	      sL.baseMsgid_retrieval(input, c);
	      sL.IterationLogic30DayDisconnect(input, c);
	    	 
	    }
	    
	    public void ExecutionOrderPartOne(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, InstantiationException, IllegalAccessException, SAXException
	    {
	    	  	    
	    	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
	 		 Application.showMessage("Status needs to be updated is ::"+status);
	 		 simulatorScheduleupDown(status, input, c);
	    	 rV.demicalls(input, c);
	    }
	    
	       
	    
	    
	    public void simulatorScheduleupDown(String status,Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
		{
		
			Statement st = sL.dBConnect(input, c);
	        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs.next()) 
	        {
	          String beforeSim = rs.getString(2);
	       
	          Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
	        
	        }
	        st.close();
	        Statement st0 = sL.dBConnect(input, c);
	        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: useUcontrolSimulator")+"</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: uControlGroupValue")+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"+status+"</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: isFoxtrotFlowEnable")+"</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        st0.executeUpdate(sql);
	       
	    
	        st0.close();
	        Thread.sleep(240000);
	        Statement st1 = sL.dBConnect(input, c);
	        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs1.next()) 
	        {
	        	
	         String afterSim = rs1.getString(2);
	         Application.showMessage("Db Simulator After Updation ::"+afterSim);
	        
	        }
	        st0.close();
	     
	        
		}
	    
	    public void ExecutionOrderwithGlobalProcess(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
	    {
	    	
	    	
	      	 Thread.sleep(2000);
	      	 rV.queryLocation_Validate(input, c);
	      	 rS.simGetAccountValidate(input, c);
	    	 //String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
	      	 String Instatus="Open";
	      	 validateScheduleDisconnect(input, c, Instatus);
	    	 Thread.sleep(3000);
	    	 //may have to run simulator settings according to scenarios.
	    	 runGlobalProcess(input, c);
	    	 Thread.sleep(10000);
	    	//disconnect demicalls.
	    	 simulatorScheduleupDown("ACTIVE", input, c);
	    	 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Disconnect_XML_OrderStatus").equalsIgnoreCase("Cancelled"))   
	    	 {
	    		 String ComStatus="Cancelled"; 
	    		 validateScheduleDisconnect(input, c, "Cancelled");
	    	 }
	    	 else
	    	 {
	    		 
	    		 String ComStatus="Completed";
		    	 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Insight"))
		    	 {
		          
		    	 // rVU.simulatorInsightcalls(input, c);
		    	 }
		    	 else
		    	 {
		    		//.SimulatorDemicalls(input, c);
		    	 }
		    	 validateScheduleDisconnect(input, c, "Completed");
	    		 
	    	 }
	    	 
	    	//status completed.
	    	 //sL.simulatorChange30DayDisconnect("ACTIVE", input, c);
	    	 
	    	 //status completed.
	    	 
	    }
	    
	    public void ExecutionOrderwithOutGlobalProcess1(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
	    {
	    	 Thread.sleep(30000);
	    	 String Instatus="Open";
	      	 validateScheduleDisconnect(input, c, Instatus);
	      	 Thread.sleep(2000);
	      	 rV.queryLocation_Validate(input, c);
	      	 rS.simGetAccountValidate(input, c);
	    	 //String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
	      	 
	    	
	    	 //may have to run simulator settings according to scenarios.
	    	
	    	 Thread.sleep(10000);
	    	//disconnect demicalls	    	
	    	 
	    }
	   
	    public void ExecutionOrderwithOutGlobalProcess2(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
	    {  
	    	
	    	Application.showMessage("The Value for OrderStatus is::"+c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Disconnect_XML_OrderStatus"));
	    	simulatorScheduleupDown("ACTIVE", input, c);
	    	 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Disconnect_XML_OrderStatus").equalsIgnoreCase("Cancelled"))   
	    	 {
	    		 String ComStatus="Cancelled"; 
	    		 validateScheduleDisconnect(input, c, "Cancelled");
	    	 }
	    	 else
	    	 {
		       String ComStatus="Completed";
			   	 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Insight"))
			   	 {
			   
			   	 // rVU.simulatorInsightcalls(input, c);
			   	 }
			   	 else
			   	 {
			   	// rVU.SimulatorDemicalls(input, c);
			   	 }
			   	 validateScheduleDisconnect(input, c, ComStatus);
			   	//status completed.
			   	 //sL.simulatorChange30DayDisconnect("ACTIVE", input, c);
			   	 simulatorScheduleupDown("ACTIVE", input, c);
			   	 //status completed.
			} 
	    }
	    
    public void validateScheduleDisconnect(Object input, ScriptingContext c, String inStatus) throws ClassNotFoundException, IOException
    {
    	
    	String Acc=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_AccNumber");
    	Map<String,String> getMap=new HashMap <String,String>();
    	getMap=scheduleDisconnect(input, c, Acc);
    	
    	//Validating Status
    	String Status=getMap.get("status");
    	sL.validationPointIgnoreCase(Status, inStatus, input, c);
    	
    	String creationDate=getMap.get("creationDate");
    	String inschDate=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: ScheduledDate");
    	String scheduleDate=getMap.get("scheduleDate");
    	Application.showMessage("Schedule Date is ::"+scheduleDate);
    	sL.validationPointIgnoreCase(creationDate, inschDate, input, c);
    	
    	String inDisService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
    	String disconnectService=getMap.get("disconnectService");
    	Application.showMessage("disconnectService is::"+disconnectService);
    	sL.validationPointIgnoreCase(disconnectService, inDisService, input, c);
    	
    	String inService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE");
    	String installService=getMap.get("installService");
    	Application.showMessage("installService is::"+installService);
    	sL.validationPointIgnoreCase(installService, inService, input, c);
    	
    	String timeZone=getMap.get("timeZone");
    	String inTimeZone=(String) c.get("pTimeZone");
    	Application.showMessage("TimeZone is::"+inTimeZone);
    	sL.validationPointIgnoreCase(timeZone, inTimeZone, input, c);
    	
    	    	
    }
    
    
	    
    public String getsysTime()
    {
    	 Date todayDate = new Date();
         TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
         DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
         todayDateFormat.setTimeZone(timeZone);
         String strTodayDate = todayDateFormat.format(todayDate);
         //System.out.println("Todays Date as per EST is::"+strTodayDate); 
         return strTodayDate;
    }
    public String getYesterdayDateString() 
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YY");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);    
        return dateFormat.format(cal.getTime());
    }
    
    
    public String getTommorowDateString() 
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YY");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);    
        return dateFormat.format(cal.getTime());
    }
 
    public int updateScheduleDisConnect(Object input, ScriptingContext c,String AccountNumber) throws ClassNotFoundException, SQLException, IOException
    {
    	 
		  int nRs1=0;  
		  Statement st = sL.dBConnect(input, c);
		  String Tday=getsysTime();
	      String Yday=getYesterdayDateString();
		  nRs1=st.executeUpdate("update scheduledisconnect set CREATIONDATE='"+Yday+"',SCHEDULEDATE='"+Tday+"'where accountnumber='"+AccountNumber+"'");
    	  if(nRs1==0)
    	  {
    		  Application.showMessage("NO Record got updated..!!!");
    		  c.report("NO Record got updated..!!!");
    
    	  }
    	  else
    	  {
    		  Application.showMessage("Number of records updated is ::"+nRs1);
    	  }
    	
    	return nRs1;
    }
	public Map<String,String> scheduleDisconnect(Object input, ScriptingContext c,String AccountNumber)throws ClassNotFoundException, IOException
	{
		  Application.showMessage("------------------------------------------------------------------");
		  Application.showMessage("*##Connecting to connectThirtyDayDisconnectDB retrieving data... ");       
		  Application.showMessage("------------------------------------------------------------------");
		  
		  ResultSet rs;
		  
		  String dStatus = null;
		  String dCREATIONDATE = null;
		  String dSCHEDULEDATE=null;
		  String dDISCONNECTSERVICE =null;
		  String dINSTALLSERVICE=null;
		  
		  String dTIMEZONEUTCOFFSET=null;
		  
		  Map<String,String> returnMap=new HashMap <String,String>();
		  
		  try
 		  {
			  
	          Statement st = sL.dBConnect(input, c);
  	          rs = st.executeQuery("select * from scheduledisconnect where accountnumber='"+AccountNumber+"' "); 
  	   
  	          while (rs.next())
	          {
  	        	
  	        	dStatus=rs.getString("STATUS");
  	        	dCREATIONDATE=rs.getString("CREATIONDATE");
  	        	dSCHEDULEDATE=rs.getString("SCHEDULEDATE");
  	        	dDISCONNECTSERVICE=rs.getString("DISCONNECTSERVICE");
  	        	dINSTALLSERVICE=rs.getString("INSTALLSERVICE");
  	        	dTIMEZONEUTCOFFSET=rs.getString("TIMEZONEUTCOFFSET");
  	        	//dIsLetterProcessed=rs.getString("ISLETTERPROCESSED");
  	      	 
  	        	
	          }
  	          Application.showMessage("STATUS is ::"+dStatus);
  	          Application.showMessage("dCREATIONDATE is ::"+dCREATIONDATE);
  	          Application.showMessage("dSCHEDULEDATE is ::"+dSCHEDULEDATE);
  	          Application.showMessage("dDISCONNECTSERVICE is ::"+dDISCONNECTSERVICE);
  	          Application.showMessage("dINSTALLSERVICE is ::"+dINSTALLSERVICE);
  	          Application.showMessage("dTIMEZONEUTCOFFSET is ::"+dTIMEZONEUTCOFFSET);
  	          
  	          
  	          returnMap.put("status",dStatus.trim());
  	          returnMap.put("creationDate",dCREATIONDATE.trim());
  	          returnMap.put("scheduleDate",dSCHEDULEDATE.trim());
  	          returnMap.put("disconnectService",dDISCONNECTSERVICE.trim());
  	          returnMap.put("installService",dINSTALLSERVICE.trim());
  	          returnMap.put("timeZone",dTIMEZONEUTCOFFSET.trim());
  	         
  	          st.close();
 		  }		
		  
		  
    	 catch (SQLException e)
 		 {
 		    System.out.println("Connection Failed! Check output console");
 			e.printStackTrace();
 			
 		 }
		return returnMap;
     
	}
	
	
	public void runGlobalProcess(Object input,ScriptingContext c)throws ClassNotFoundException, IOException
	{
		  Application.showMessage("------------------------------------------------------------------------------------------------------");
		  Application.showMessage("*##Connecting to cwglobalprocess and cwmdtypes tables, retrieving process id and deletion of the same ");       
		  Application.showMessage("------------------------------------------------------------------------------------------------------");
		  ResultSet rs;
		  String gProcessid = null;
		  int nRs=0,nRs1=0;
		  try
 		  {
			  
	          Statement st = sL.dBConnect(input, c);

  	          rs = st.executeQuery("select process_id from cwglobalprocess,cwmdtypes where  typename ='vantage:disconnectAfter30Days' and typeid =process_metadatype"); 
  	   
  	          while (rs.next())
	          {
  	        	
  	        	gProcessid=rs.getString(1);
	          }
  	          Application.showMessage("gProcessid is ::"+gProcessid);
  	          
  	          nRs=st.executeUpdate("Delete from cwglobalprocess where process_id ='"+gProcessid+"'");
  	          Application.showMessage("No :of records Deleted from cwglobalprocess Table::"+nRs);
  	          if(nRs==0)
  	          {
  	        	  Application.showMessage("Zero Number of records Deleted from cwglobalprocess...Error..!!");
  	          }
  	          else
  	          {
  	        	  Application.showMessage("Number of records Deleted from cwglobalprocess is ::"+nRs);
 
  	          }
  	          nRs1=st.executeUpdate("Delete from cwpactivity where process_id ='"+gProcessid+"'");
  	          Application.showMessage("No :of records Deleted from cwpactivity Table::"+nRs1);
  	          if(nRs1==0)
	          {
	        	  Application.showMessage("Zero Number of records Deleted from cwpactivity...Error..!!");
	        	  c.report("Zero Number of records Deleted from cwpactivity...Error..!!");
	          }
	          else
	          {
	        	  Application.showMessage("Number of records Deleted from cwpactivity is ::"+nRs1);
    
	        	  
	          }
  			  Application.showMessage("------------------------------------------------------------------------------------------------------");

  	          st.close();
 		  }		
		  
		  
    	 catch (SQLException e)
 		 {
 		    System.out.println("Connection Failed! Check output console");
 			e.printStackTrace();
 			
 		 }
     
	}
	
	
}
