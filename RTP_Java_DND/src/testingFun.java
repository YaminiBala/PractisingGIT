import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;




public class testingFun {

	/**
	 * @param args
	 */
	 static String basemsgID = null;
	static LibraryRtp sL=new LibraryRtp();
    RTP_ValidationsClass rV=new RTP_ValidationsClass();
    RTP_SimulatorClass rS=new RTP_SimulatorClass();
    RTP_SimulatorClass_UpDown rU=new RTP_SimulatorClass_UpDown();
    RTP_ValidationsClass_UpDown rVU=new RTP_ValidationsClass_UpDown();
    LibraryRtp_UpDown lRU=new LibraryRtp_UpDown();
    CancelClass cc=new CancelClass();
    int OutPut=1;
    
	public static void main(String[] args) throws ClassNotFoundException, IOException, InterruptedException, XPathException, SAXException, SQLException
	{
		/*LibraryRtp.AddLog("The Test Result is::","N");
		LibraryRtp.AddLog("jamun","Y");
		LibraryRtp.AddLog("radhakrishn","N");
		LibraryRtp.AddLog("Djanko","N");
		System.out.println(LibraryRtp.logger);
		LibraryRtp.logWritter(LibraryRtp.logger, "C:/ALMConfig/log/log.txt");*/
		//baseMsgid_retrieval();
		 // String Time=getBaseTime();
		// String Time=getBaseTime();
		 //Thread.sleep(10000);
		 //OrderUp_Validate( Time,"COM" ,"9999647611522A", "DOT_COM", "DOT_COM", "STD","OP","DDP","NEW","RES");
		// deactivateAccount_Validate();
		  //SettingTrackCode("OP");
		 //deactivateAccount_Validate();
		deactivateAccount_Validate();
		//String a=getUcontrolService("300 + TDP + EV");
		//System.out.println("A::"+a);

	}
	
	public static ResultSet searchDataSet(Statement st,String operation,String Time) throws ClassNotFoundException, SQLException, IOException
	{
		 ResultSet rs;
		
         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+operation+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");       
    
        /* while(rs.next())
         {
        	 String msgiD=rs.getString("msgid");
        	 System.out.println("MsgID::"+msgiD);
         }
        */
        st.close();
         return rs;
	}
	
	 public static String getBaseTime() throws InterruptedException
	   {
		      String Time;
		      Date todayDate = new Date();
			  TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
			  DateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			  todayDateFormat.setTimeZone(timeZone);
			  Time=todayDateFormat.format(todayDate);
			  System.out.println("BaseTime is::"+Time);
			  Thread.sleep(10000);
			  return Time;
	   }
	 
    public static String getUcontrolService(String data)
    {
    	String service;
    	return service= data.replaceAll(" \\+ ", "|");
    	
    }
	  public static  void SettingTrackCode(String TrackCode)throws IOException, ClassNotFoundException, SQLException, InterruptedException
	  {
		    Statement st = dBConnect();
	        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='swivelSIK:swivelSIKConfig'");
	        while (rs.next()) 
	        {
	         String beforeSim = rs.getString("DATA");
	        //c.setValue("BaseMsgid", basemsgID);
	         System.out.println("Db Simulator Before Updation ::"+beforeSim);
	         
	        }
	        st.close();
	       
	        Statement st0 = dBConnect();
	        String sql="update CWFTEMPLATECONFIG set DATA='<swivelSIKConfig><name>swivelSIK:swivelSIKConfig</name><cancelOrdErrorsForRetry>Order-CancelOrder-501</cancelOrdErrorsForRetry><getOrdErrorsForRetry>Order-GetOrderForModification-604</getOrdErrorsForRetry><sikSerivceConfigErrors>Order-SubmitOrder-103|Order-SubmitOrder-104|Order-SubmitOrder-201|Order-SubmitOrder-202|Order-SubmitOrder-212</sikSerivceConfigErrors><subOrdSinOrdExactMatchError>Order-SubmitOrder-212</subOrdSinOrdExactMatchError><subOrdSinOrdNotExactMatchError>Order-SubmitOrder-213</subOrdSinOrdNotExactMatchError><subOrdMulOrdExistsError>Order-SubmitOrder-211</subOrdMulOrdExistsError><sikmaxRetryCount>3</sikmaxRetryCount><sikmaxRetryInterval>2</sikmaxRetryInterval><sikretryIntervalForServiceRetry>5</sikretryIntervalForServiceRetry><sikpauseIntervalForServiceRetry>120</sikpauseIntervalForServiceRetry><sikretryLimitBeforeQueuePause>3</sikretryLimitBeforeQueuePause><lsmaxRetryCount>2</lsmaxRetryCount><lsmaxRetryInterval>5</lsmaxRetryInterval><lsretryIntervalForServiceRetry>10</lsretryIntervalForServiceRetry><lspauseIntervalForServiceRetry>120</lspauseIntervalForServiceRetry><lsretryLimitBeforeQueuePause>5</lsretryLimitBeforeQueuePause><csgOperatorToggle>1</csgOperatorToggle><RMA_CM_EMTA_KIT>HSD-RMA-MODEM-75</RMA_CM_EMTA_KIT><RMA_SMALL_KIT>VID-RMA-DCT700-70</RMA_SMALL_KIT><RMA_LARGE_KIT>VID-RMA-DCT2xxx-72</RMA_LARGE_KIT><RMA_LABEL_KIT>RMA-DF-LABEL-01</RMA_LABEL_KIT><RMA_AcquisitionMethods>RMA_CM_EMTA_KIT,RMA_LABEL_KIT,RMA_LARGE_KIT,RMA_SMALL_KIT</RMA_AcquisitionMethods><CSGSIKOperator>ZZO|ZZQ</CSGSIKOperator><enableDDPSIKRSHWorkOrder>1</enableDDPSIKRSHWorkOrder><DTATrackCode>"+TrackCode+"</DTATrackCode><DTAChannel>DTA</DTAChannel></swivelSIKConfig>' where NAME='swivelSIK:swivelSIKConfig'";
	        st0.executeUpdate(sql);
	       
	    
	        st0.close();
	        Thread.sleep(240000);
	        Statement st1 = dBConnect();
	        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='swivelSIK:swivelSIKConfig'");
	        while (rs1.next()) 
	        {
	        	
	         String afterSim = rs1.getString("DATA");
	         System.out.println("Db Simulator After Updation ::"+afterSim);
	        
	        }
	        st1.close();
	  }

	
	public static int validationPoint(String ValidateData1, String ValidateData2)
	{
		
		int result;
		
		if(ValidateData1.equals(null)||ValidateData2.equals(null))
		{
		   System.out.println("One of the Validation Data entered is NULL...!");
		   result = 0;
		}
		else
		{
			if(ValidateData1.equals(ValidateData2))
			{
				System.out.println("Validation is Successfull with Input Data ::"+ " "+ValidateData1+" and "+" "+ValidateData2);
				
				result=1;
			}
			else
			{
				System.out.println("Validation is NOT Successfull with Input Data..!!! :: "+ValidateData1+" and "+ValidateData2);
		
				result=0;
			}
		}
		return result;
		
	}
	
	
	public static int verificationPoint(String ValidateData1, String ValidateData2)
	{
		
		int result;
		
		if(ValidateData1.equals(null)||ValidateData2.equals(null))
		{
		   System.out.println("One of the Validation Data entered is NULL...!");
		   result = 0;
		}
		else
		{
			if(ValidateData1.equals(ValidateData2))
			{
				System.out.println("Validation is Successfull with Input Data ::"+ " "+ValidateData1+" and "+" "+ValidateData2);
				
				result=1;
			}
			else
			{
				System.out.println("Validation is NOT Successfull with Input Data..!!! :: "+ValidateData1+" and "+ValidateData2);
		
				result=0;
			}
		}
		return result;
		
	}
	
	
	
	
	public static int validationPointIgnoreCase(String ValidateData1, String ValidateData2)
	{
		int result;
		if(ValidateData1.equals(null)||ValidateData2.equals(null))
		{
		   System.out.println("One of the Validation Data entered is NULL...!");
		   result = 0;
		}
		else
		{
			if(ValidateData1.equalsIgnoreCase(ValidateData2))
			{
				System.out.println("Validation is Successfull with Input Data ::"+ValidateData1+"and"+ValidateData2);
				
				result=1;
			}
			else
			{
				System.out.println("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+"and"+ValidateData2);
		
				result=0;
			}
		}
		return result;
		
	}
	
	
	public static int ORvalidationPoint(String ValidateData1, String ValidateData2,String ValidateData3, Object input , ScriptingContext c)
	{
		int result;
		
		if(ValidateData1.equals(null)||ValidateData2.equals(null))
		{
		   System.out.println("One of the Validation Data entered is NULL...!");
		   result = 0;
		}
		else
		{
			if(ValidateData1.equals(ValidateData2))
			{
				System.out.println("Validation is Successfull with Input Data ::"+ ValidateData1+" and "+ ValidateData2);
				
				result=1;
			}
			else if(ValidateData1.equals(ValidateData3))
			{
				System.out.println("Validation is Successfull with Input Data ::"+ValidateData2+" and "+ValidateData3);
				
				result=1;
			}
			else
			{
				System.out.println("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+" and "+ValidateData2);
		
				result=0;
			}
		}
		
		return result;
		
	}
	
	
	public static int ANDvalidationPoint(String ValidateData1, String ValidateData2,String ValidateData3, Object input , ScriptingContext c)
	{
		int result;
		if(ValidateData1.equals(null)||ValidateData2.equals(null))
		{
		   System.out.println("One of the Validation Data entered is NULL...!");
		   result = 0;
		}
		else
		{
			if(ValidateData1.equals(ValidateData2) & (ValidateData1.equals(ValidateData3)))
			{
				System.out.println("Validation is Successfull with Input Data ::" +ValidateData1+ " and "+ValidateData2 +" and "+ValidateData3);
				
				result=1;
			}
			
			else
			{
				System.out.println("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+" and "+ValidateData2);
		
				result=0;
			}
		}
		
		return result;
		
	}
	
	public static String makeCorpIDfromAccountNumber(String AccountNumber,String INbillingSystem)
	{
		String INcorpID;
		 if(INbillingSystem.equalsIgnoreCase("DDP"))
         {
         	INcorpID= AccountNumber.substring(0, 5);
         }
         
         else
         {
         	INcorpID= AccountNumber.substring(0, 6);
         }
		return INcorpID;
	}
	
	
	  public static String getsysTime()
	    {
	    	 Date todayDate = new Date();
	         TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
	         DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
	         todayDateFormat.setTimeZone(timeZone);
	         String strTodayDate = todayDateFormat.format(todayDate);
	         System.out.println("Todays Date as per EST is::"+strTodayDate); 
	         return strTodayDate;
	    }
	    public static String getYesterdayDateString() 
	    {
	        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YY");
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DATE, -1);    
	        return dateFormat.format(cal.getTime());
	    }
	    
	    public static void baseMsgid_retrieval() throws ClassNotFoundException, IOException
	       {      
	              Date creationtime =null;
	              
	              try
	              {
	                     
	            Statement st = dBConnect();
	            ResultSet rs = st.executeQuery("select msgid,creation_time from (select msgid,creation_time from cwmessagelog order by creation_time desc) where rownum < 2");
	             
	               while (rs.next()) 
	               {
	               basemsgID = rs.getString(1);
	               creationtime = rs.getDate("CREATION_TIME");
	               
	              // System.out.println("Base Message ID is ::"+c.getValue("BaseMsgid"));
	               System.out.println("Base Message ID is ::"+basemsgID);       
	               System.out.println("Date is "+creationtime);   
	              // System.out.println("Date is "+creationtime);
	               }
	               if(basemsgID ==null)
	               {
	                 System.out.println("No MsgId Found"); 
	               }
	               else
	               {
	                System.out.printf("MsgId Found is %s\n",basemsgID);  
	                
	               }
	               st.close();                            
	              } 
	              catch (SQLException e)
	              {
	                  System.out.println("Connection Failed! Check output console");
	                     e.printStackTrace();
	                     return;
	              }
	              
	              
	       }

	    
	    public static Statement dBConnect() throws ClassNotFoundException, SQLException, IOException
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1", "CWDEVOP","CWDEVOP");	
			
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.33:1521:psymqa1", "CWQA1OP","qa1opw1nt3r");
			
			String host = "10.252.115.36";
		    String password = "CWDEVOP";
			String username = "CWDEVOP";
			String port ="1521";
			String sid = "PSYMDEV1";
			
			//String host = "10.252.115.34";
			//String password = "qa4opw1nt3r";
			//String username = "CWQA4OP";
			//String port ="1555";
			//String sid = "PSYMIN1";
			System.out.println("The Values are::"+host+":"+port+":"+sid+":" +username+password);
			
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port+":"+sid +"", username,password);
		    
			Statement st = connection.createStatement();
			return st;
		}
	    
	    public static Map<String,String> scheduleDisconnect(String Acc,String createDate,String scheduleDate)throws ClassNotFoundException, IOException
		{
			 System.out.println("------------------------------------------------------------------");
			 System.out.println("*##Connecting to connectThirtyDayDisconnectDB retrieving data... ");       
			 System.out.println("------------------------------------------------------------------");
			  
			  ResultSet rs;
			  int nRs1=0;     
			  String dStatus = null;
			  String dCREATIONDATE = null;
			  String dSCHEDULEDATE=null;
			  String dDISCONNECTSERVICE =null;
			  String dINSTALLSERVICE=null;
			  
			  String dTIMEZONEUTCOFFSET=null;
			  
			  Map<String,String> returnMap=new HashMap <String,String>();
			  String AccountNumber=Acc;
			  try
	 		  {
				  
		          Statement st = dBConnect();
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
	  	         System.out.println("STATUS is ::"+dStatus);
	  	         System.out.println("dCREATIONDATE is ::"+dCREATIONDATE);
	  	         System.out.println("dSCHEDULEDATE is ::"+dSCHEDULEDATE);
	  	         System.out.println("dDISCONNECTSERVICE is ::"+dDISCONNECTSERVICE);
	  	         System.out.println("dINSTALLSERVICE is ::"+dINSTALLSERVICE);
	  	         System.out.println("dTIMEZONEUTCOFFSET is ::"+dTIMEZONEUTCOFFSET);
	  	          
	  	       
	  	          returnMap.put("status",dStatus.trim());
	  	          returnMap.put("creationDate",dCREATIONDATE.trim());
	  	          returnMap.put("scheduleDate",dSCHEDULEDATE.trim());
	  	          returnMap.put("disconnectService",dDISCONNECTSERVICE.trim());
	  	          returnMap.put("installService",dINSTALLSERVICE.trim());
	  	          returnMap.put("timeZone",dTIMEZONEUTCOFFSET.trim());
	  	          
	  	          nRs1=st.executeUpdate("update scheduledisconnect set CREATIONDATE='"+createDate+"',SCHEDULEDATE='"+scheduleDate+"'where accountnumber='"+AccountNumber+"'");
	        	  System.out.println("Number of rows updated is::"+nRs1); 
	        	  returnMap.put("recordsUpdated",Integer.toString(nRs1));
	  	          st.close();
	 		  }	
			  catch (SQLException e)
		 		 {
		 		    System.out.println("Connection Failed! Check output console");
		 			e.printStackTrace();
		 			
		 		 }
				return returnMap;
		     
			}
			  
	   /* public static void AddLog(String Log,String space)
		{
			if(space.equalsIgnoreCase("Y"))
			{
			logger=logger+"\r\n"+Log+"\r\n";
			}
			else if(space.equalsIgnoreCase("N"))
			{
			logger=logger+Log+"\r\n";	
			}
			else
			{
			logger=logger+Log+"\n";		
			}
		}*/
		
	  /*  public static void logWritter(String log, String FileName) throws IOException
		{
			File file = new File(FileName);
	        //System.out.println("The data is ::"+FileData);
	        
	        if (!file.exists()) 
	        {
	            file.createNewFile();
	                                       
	        }

	            FileWriter fw = new FileWriter(file.getAbsoluteFile());
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(log);
	            bw.close();
										

		}
*/
	  /*  public static void finddata()
	    {
	    	ResultSet rs1;
	    	Statement st1 = dBConnect();
	            //rs1 = st1.executeQuery("select to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss')AS btime from cwmessagelog where msgid='"+rowmsg+"'");
            String BaseTime=rs1.getString("btime");
           
            Application.showMessage("The new BaseTime is:"+c.get("BaseTime"));
            rs1.close();
            st1.close();
	    }
	    */
	    public static void deactivateAccount_Validate() throws ClassNotFoundException, InterruptedException, SQLException, IOException, XPathException, SAXException
		{
			
			//Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
			 ResultSet  rs;
			 int callFound=0,v1=0,v2=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		
		     System.out.println("-------------------------------------------------");
		     System.out.println("******deactivateAccount_Validate Function********");       
		     System.out.println("-------------------------------------------------");
		     
		     
		  
		     
				 Statement st =dBConnect();	
		         rs = st.executeQuery("select * from cwmessagelog where msgid='70093866271323'");
		         while (rs.next())
		         {
		        	
		            String rowmsg = rs.getString(1);
		            String user_data1=rs.getString("USER_DATA1");
		            System.out.println("USER data 1 :"+user_data1);
		          /*  String senddata =sL.extractXml(rs,xmldata2);
	            	System.out.println("Your Recieve XML is::\n"+senddata);
	            	String send_wf= sL.RemoveNameSpaces(senddata);
	            	System.out.println("Wellformed sendXML::"+send_wf);*/
	            	String recieveData =sL.extractXml(rs,xmldata1);
	            	System.out.println("Your Recieve XML is::\n"+recieveData);
	            	String recieve_wf= sL.RemoveNameSpaces(recieveData);
	            	System.out.println("Wellformed sendXML::"+recieve_wf);
	            	
	            	String OrderIDCSG=sL.GetValueByXPath(recieve_wf, ".//OrderID");
	            	System.out.println("OrderID  is ::"+OrderIDCSG);
	            	validationPointIgnoreCase(OrderIDCSG, "OrderID");
	            	
	            	String Senddata =sL.extractXml(rs,xmldata2);
	            	System.out.println("Your Send XML is::\n"+Senddata);
	            	String Senddata_wf= sL.RemoveNameSpaces(Senddata);
	            	System.out.println("Wellformed sendXML::"+Senddata_wf);
	            		            	//String CcentralNum1=sL.GetValueByXPath(send_wf, ".//centralStationAccountNumber");
	            	String OrderType=sL.GetValueByXPath(Senddata_wf, "/SubmitOrder/Request/ExternalOrderData/@OrderType");
	            	System.out.println("OrderType  is ::"+OrderType);
	            	validationPointIgnoreCase(OrderType, "INSTALL");
	            	
	            	String ShipmentPriority=sL.GetValueByXPath(Senddata_wf, ".//ShipmentPriority");
	            	System.out.println("ShipmentPriority  is ::"+ShipmentPriority);
	            	validationPointIgnoreCase(ShipmentPriority, "STANDARD");
	            	
	            	String AccountNumber=sL.GetValueByXPath(Senddata_wf, ".//AccountNumber");
	            	System.out.println("AccountNumber  is ::"+AccountNumber);
	            	validationPointIgnoreCase(AccountNumber, "8919100010654243");
	            	
	            	String FirstName=sL.GetValueByXPath(Senddata_wf, ".//FirstName");
	            	System.out.println("FirstName  is ::"+FirstName);
	            	validationPointIgnoreCase(FirstName, "INSTALL");
	            	
	            	String LastName=sL.GetValueByXPath(Senddata_wf, ".//LastName");
	            	System.out.println("LastName  is ::"+LastName);
	            	validationPointIgnoreCase(LastName, "INSTALL");
	            	
	            	String Phone=sL.GetValueByXPath(Senddata_wf, ".//Phone");
	            	System.out.println("Phone  is ::"+Phone);
	            	validationPointIgnoreCase(Phone, "INSTALL");
	            	
	            	String Email=sL.GetValueByXPath(Senddata_wf, ".//Email");
	            	System.out.println("Email  is ::"+Email);
	            	validationPointIgnoreCase(Email, "INSTALL");
	            	
	            	
	            	
	            	String OrderID=sL.GetValueByXPath(recieve_wf, ".//OrderID");
	            	System.out.println("OrderID  is ::"+OrderID);
	            	validationPointIgnoreCase(OrderID, "INSTALL");
	            	
	            	/*String Email=sL.GetValueByXPath(Senddata_wf, ".//Email");
	            	System.out.println("Email  is ::"+Email);
	            	validationPointIgnoreCase(Email, "INSTALL");*/
	            	
	            	
	            	String Address1=sL.GetValueByXPath(Senddata_wf, ".//Address1");
	            	System.out.println("Address1  is ::"+Address1);
	            	validationPointIgnoreCase(Address1, "Address1");
	            	
	            	String City=sL.GetValueByXPath(Senddata_wf, ".//City");
	            	System.out.println("City  is ::"+City);
	            	validationPointIgnoreCase(City, "INSTALL");
	            	
	            	String State=sL.GetValueByXPath(Senddata_wf, ".//State");
	            	System.out.println("State  is ::"+State);
	            	validationPointIgnoreCase(State, "INSTALL");
	            	
	            	String Zip=sL.GetValueByXPath(Senddata_wf, ".//Zip");
	            	System.out.println("Zip  is ::"+Zip);
	            	validationPointIgnoreCase(Zip, "INSTALL");
	            	
	            	String IsAlternateAddress=sL.GetValueByXPath(Senddata_wf, ".//IsAlternateAddress");
	            	System.out.println("IsAlternateAddress  is ::"+IsAlternateAddress);
	            	validationPointIgnoreCase(IsAlternateAddress, "INSTALL");
	            	
	            	String DNCSIPAddress=sL.GetValueByXPath(Senddata_wf, ".//DNCSIPAddress");
	            	System.out.println("DNCSIPAddress  is ::"+DNCSIPAddress);
	            	validationPointIgnoreCase(DNCSIPAddress, "INSTALL");
	            	
	            	String HeadEndVendor=sL.GetValueByXPath(Senddata_wf, ".//HeadEndVendor");
	            	System.out.println("HeadEndVendor  is ::"+HeadEndVendor);
	            	validationPointIgnoreCase(HeadEndVendor, "INSTALL");
	            	
	            	String E911_acceptance=sL.GetValueByXPath(Senddata_wf, ".//E911_acceptance");
	            	System.out.println("E911_acceptance  is ::"+E911_acceptance);
	            	validationPointIgnoreCase(E911_acceptance, "INSTALL");
	            	
	            	String AgentID=sL.GetValueByXPath(Senddata_wf, ".//AgentID");
	            	System.out.println("AgentID  is ::"+AgentID);
	            	validationPointIgnoreCase(AgentID, "INSTALL");
	            	
	            	String WorkOrderID=sL.GetValueByXPath(Senddata_wf, ".//WorkOrderID");
	            	System.out.println("WorkOrderID  is ::"+WorkOrderID);
	            	validationPointIgnoreCase(WorkOrderID, "INSTALL");
	            	
	            	String DuplicateOverride=sL.GetValueByXPath(Senddata_wf, ".//DuplicateOverride");
	            	System.out.println("E911_acceptance  is ::"+E911_acceptance);
	            	validationPointIgnoreCase(DuplicateOverride, "INSTALL");
	            	
	            	String ProductCode1=sL.GetValueByXPath(Senddata_wf, "/SubmitOrder/Request/Products/Product/ProductCode");
	            	System.out.println("ProductCode1  is ::"+ProductCode1);
	            	validationPointIgnoreCase(ProductCode1, "INSTALL");
	            	
	            	String ProductCode2=sL.GetValueByXPath(Senddata_wf, "/SubmitOrder/Request/Products/Product[2]/ProductCode");
	            	System.out.println("ProductCode2  is ::"+ProductCode2);
	            	validationPointIgnoreCase(ProductCode2, "INSTALL");

	            	
	            	
	            	
	            	//System.out.println("The AccountNumber  is ::"+AccountNumber);
		         }
		       
		         st.close();
				
			/*catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}*/
		}
	    
	    
	    
	    public static int OrderUp_Validate(String Time,String INOrdStatus ,String INbillingOrderId,String INinputChannel,String INsalesChannel, String INshipmentType,String INproductType,String INbillingSystem,String INordType,String INcustomerType) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException, SQLException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     
			
			 LibraryRtp sL = new LibraryRtp();
			 ResultSet  rs;
			 int v4=0,v1=0,v2=0,v3=0,v5=0,v6=0,v7=0;
			 int callFound=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		    // String currentDate=sL.getsysTime();
		     String AccountNumber="9999647611522";
		     String operation= "cmb:commonOrderService/orderUpdate";
		     String Times="2015-02-12 01:43:00";
	         //System.out.println("Date of execution is ::"+currentDate);
		     System.out.println("--------------------------------------------------------");
		     System.out.println("***********OrderUpdate_Validate Function**************");       
		     System.out.println("--------------------------------------------------------");
		     System.out.println();
			 try
			 {
				 Statement st =dBConnect();	
		        /* rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");*/
			     rs=searchDataSet(st,operation, Times);
				 
		        // rs.first();
		         while (rs.next())
		         {
		        	System.out.println("Data is::"+rs.getString("MSGID"));
		                    	
		            if(rs.getBlob("receive_data")==null)
		            {
		            
		            	System.out.println("Your Recieve XML is NULL \n");
		            	
		            	String senddata =sL.extractXml(rs,xmldata2);
		            	System.out.println("Your Recieve XML is::\n"+senddata);
		           
		                          
			           /*
			            String recievedata = sL.extractXml(rs,xmldata1);      
			            System.out.println("senddata is ::"+senddata); 
			            System.out.println("recievedata is ::"+recievedata); */
			        
			            
		 	            String billingAccountNo= sL.nodeFromKey(senddata,"<id>","</id>");
			       	    System.out.println("billingAccountNo is ::" +billingAccountNo);	       	    
			       	    v1= verificationPoint(billingAccountNo, INbillingOrderId);
			       	                     
		 	            String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
	                    System.out.println("The ordStatus from Request is::"+ordStatus);                       
	                    v2= verificationPoint(ordStatus, INOrdStatus);
	                    
	                    String sendDataT=sL.RemoveNameSpaces(senddata);
	                    System.out.println("data1 is::"+sendDataT);
	                    String data1= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[2]");
	                    System.out.println("data1 is::"+data1);
	                    
	                    String data2= sL.GetValueByXPath(sendDataT, "/comRequest/customer/value");
	                    System.out.println("data2 is::"+data2);
	                    String data3= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value");
	                    System.out.println("data3 is::"+data3);
	                    String data4= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value[6]");
	                    System.out.println("data4 is::"+data4);
	                    String data5= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[8]");
	                    System.out.println("data5 is::"+data5);

	                    
	                   
	                    if(v1*v2==1)
	                    {
	                    	callFound=1;
	                    }
	                    else
	                    {
	                    	continue;
	                    }
	                    
	                    if(callFound==1)
	                    {
	                    	
	                    	String guid= sL.nodeFromKey(senddata,"<value name=\"guid\">","</value><value name=\"accountNumber\">");
	                        System.out.println("The guid from Request is::"+guid);                       
	                        v2= validationPointIgnoreCase(guid, INbillingOrderId);
	                        
	                        String accountNumber= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"corpId\">");
	                        System.out.println("The accountNumber from Request is::"+accountNumber);                       
	                        v2= validationPointIgnoreCase(accountNumber, AccountNumber);
	                        
	                        String INcorpID= makeCorpIDfromAccountNumber(accountNumber, INbillingSystem);                  
	                        String corpId= sL.nodeFromKey(senddata,"<value name=\"corpId\">","</value><value name=\"billingOrderId\">");
	                        System.out.println("The corpId from Request is::"+corpId);                       
	                        v2= validationPointIgnoreCase(corpId, INcorpID);
	                        
	                        String billOrder= INbillingOrderId.substring(accountNumber.length());
	                        String billingOrderId= sL.nodeFromKey(senddata,"<value name=\"billingOrderId\">","</value><value name=\"ordType\">");
	                        System.out.println("The billingOrderId from Request is::"+billingOrderId);                       
	                        v2= validationPointIgnoreCase(billingOrderId, billOrder);
	                        
	                        String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
	                        System.out.println("The ordType from Request is::"+ordType);                       
	                        v2= validationPointIgnoreCase(ordType, INordType);
	                    	
	                        
	                        String ordSource= sL.nodeFromKey(senddata,"<value name=\"ordSource\">","</value><value name=\"ordStatus\">");
	                        System.out.println("The ordSource from Request is::"+ordSource);                       
	                        v2= validationPointIgnoreCase(ordSource, "OP");
	                        
	                        String selfInstall= sL.nodeFromKey(senddata,"<value name=\"selfInstall\">","</value><value name=\"inputChannel\">");
	                        System.out.println("The selfInstall from Request is::"+selfInstall);                       
	                        v2= validationPointIgnoreCase(selfInstall, "1");
	                        
	                        String inputChannel= sL.nodeFromKey(senddata,"<value name=\"inputChannel\">","</value><value name=\"customerType\">");
	                        System.out.println("The inputChannel from Request is::"+inputChannel);                       
	                        v2= validationPointIgnoreCase(inputChannel, INinputChannel);
	                        
	                        String customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"billingSystem\">");
	                        System.out.println("The customerType from Request is::"+customerType);                       
	                        v2= validationPointIgnoreCase(customerType, INcustomerType);
	                        
	                        String billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
	                        System.out.println("The billingSystem from Request is::"+billingSystem);                       
	                        v2= validationPointIgnoreCase(billingSystem, INbillingSystem);
	                        
	                        String active= sL.nodeFromKey(senddata,"<value name=\"active\">","</value><value name=\"salesChannel\">");
	                        System.out.println("The active from Request is::"+active);                       
	                        v2= validationPointIgnoreCase(active, "1");
	                        
	                        
	                        String salesChannel= sL.nodeFromKey(senddata,"<value name=\"salesChannel\">","</value><value name=\"otherInfo\">");
	                        System.out.println("The salesChannel from Request is::"+salesChannel);                       
	                        v2=validationPointIgnoreCase(salesChannel, INsalesChannel);
	                        
	                        String productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value></header>");
	                        System.out.println("The productType from Request is::"+productType);                       
	                        v2=validationPointIgnoreCase(productType, INproductType);
	                        
	                        String street1= sL.nodeFromKey(senddata,"<value name=\"street1\">","</value><value name=\"street2\">");
	                        System.out.println("The street1 from Request is::"+street1);                       
	                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        
	                        String street2= sL.nodeFromKey(senddata,"<value name=\"street2\">","</value><value name=\"city\">");
	                        System.out.println("The street2 from Request is::"+street2);                       
	                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        
	                        String city= sL.nodeFromKey(senddata,"<value name=\"city\">","</value><value name=\"state\">");
	                        System.out.println("The city from Request is::"+city);                       
	                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        
	                        String state= sL.nodeFromKey(senddata,"<value name=\"state\">","</value><value name=\"zip\">");
	                        System.out.println("The state from Request is::"+state);                       
	                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        
	                        String address= sL.nodeFromKey(senddata,"<value name=\"address\">","</value><value name=\"guid\">");
	                        System.out.println("The address from OrderUpdate is::"+address);                       
	                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        
	                        String zip= sL.nodeFromKey(senddata,"<value name=\"zip\">","</value><value name=\"zip4\">");
	                        System.out.println("The zip from Request is::"+zip);                       
	                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        
	                        String zip4= sL.nodeFromKey(senddata,"<value name=\"zip4\">","</value><value name=\"franchiseTaxArea\">");
	                        System.out.println("The zip4 from Request is::"+zip4);                       
	                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        
	                        String franchiseTaxArea= sL.nodeFromKey(senddata,"<value name=\"franchiseTaxArea\">","</value><value name=\"selfInstall\">");
	                        System.out.println("The ordStatus from Request is::"+ordStatus);                       
	                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        
	                        
	                        String firstName= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
	                        System.out.println("The ordStatus from Request is::"+ordStatus);                       
	                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        
	                        
	                        String lastName= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
	                        System.out.println("The ordStatus from Request is::"+ordStatus);                       
	                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        String shipmentType= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
	                        System.out.println("The ordStatus from Request is::"+ordStatus);                       
	                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus);
	                        
	                        String note= sL.nodeFromKey(senddata,"<note><note>","</note><description>");
	                        System.out.println("The note from Request is::"+note);                       
	                        v2= validationPointIgnoreCase(note, "Order Submitted");
	                        
	                        
	                        
	                        
	                        
	                        
	                        
	                    }
	                    
			 	            
			            break;
			            }
		             }
		    
		         st.close();
		         if(v1*v2*v3*v4*v5*v6*v7*callFound ==1)
		         {
		        	
		         	 System.out.println("WebService Call :: SetSiteStatus_Validate is Success[All validation points are Success]");
	                 
		         }
		         else
		         {
		        	
		        	
		        	 System.out.println("WebService Call ::SetSiteStatus_Validate is Failed with Validation Errors");

		         }
		        
			}	
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				
			}
			 
			return 0;
		 }
}
	    
