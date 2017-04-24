import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class ThirtyDayDisconnect 
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
     
      sL.baseMsgid_retrieval(input, c);
      rV.valuesFromAggregrate(input, c);
      sL.IterationLogic30DayDisconnect(input, c);
      Thread.sleep(5000);
    	 
    }
    
    public void ExecutionOrderPartOne(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException, InstantiationException, IllegalAccessException
    {
    	   	
      	// Thread.sleep(7000);
    	 rV.demicalls(input, c);
    	 /*String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
 		 Application.showMessage("Status needs to be updated is ::"+status);*/
    	 //sL.simulatorChange30DayDisconnect(status, input, c);//run siminfosim
    	// Thread.sleep(50000);
    	 
    }
    public void ExecutionOrderPartSimInfo(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException, InstantiationException, IllegalAccessException
    {
    	   	
      	 Thread.sleep(7000);
      	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		 Application.showMessage("Status needs to be updated is ::"+status);
    	 sL.simulatorChange30DayDisconnectSimInfo(status, input, c);//run siminfosim
    	 rV.demicalls(input, c);	 
    	 Thread.sleep(50000);
    	 
    }
    public void ExecutionOrderPartTwo_ACTIVE(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
    {
    	
    	
      	 Thread.sleep(2000);
      	 rV.queryLocation_Validate(input, c);
      	 rS.simGetAccountValidate(input, c);
    	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
      	 String Instatus="Open";
    	 thirtyDisconnectTableValidation(Instatus,input, c);
    	 Thread.sleep(3000);
    	 //may have to run simulator settings according to scenarios.
    	 runGlobalProcess(input, c);
    	 Thread.sleep(10000);
    	//disconnect demicalls.
    	 String ComStatus="Completed";
    	 rU.deactivateAccount_Validate(input, c);
    	 rU.processHomeSecurityInfo_Validate(input, c); 
    	 cc.DisconnectAccount_CANCEL_Validate(input, c);
    	 
    	// SimInfoEx_Validate(input, c);
    	 rVU.orderUpdateDis_Validate(input, c);
    	 
    	 //edit Terminbal or numerix
    	 //DisconnectAccount
    	 thirtyDisconnectTableValidation(ComStatus,input, c);//status completed.
    	 sL.simulatorChange30DayDisconnect(status, input, c);
    	 //status completed.
    	 
    }
    
    
    public void ExecutionOrderPartTwo_SimINFO(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
    {
    	
    	
      	 Thread.sleep(2000);
      	 rV.queryLocation_Validate(input, c);
      	 rS.simGetAccountValidate(input, c);
    	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
      	 String Instatus="Open";
    	 thirtyDisconnectTableValidation(Instatus,input, c);
    	 Thread.sleep(3000);
    	 //may have to run simulator settings according to scenarios.
    	 runGlobalProcess(input, c);
    	 Thread.sleep(10000);
    	//disconnect demicalls.
    	 String ComStatus="Completed";
    	 rU.deactivateAccount_Validate(input, c);
    	 rU.processHomeSecurityInfo_Validate(input, c); 
    	 cc.DisconnectAccount_CANCEL_Validate(input, c);
    	 
    	 SimInfoEx_Validate(input, c);    	 
    	 rVU.orderUpdateDis_Validate(input, c);
    	 String SimInfoStatusInput= c.getValue("RTPDataSourceCollections","dB_Simulator: SimInfoStatus");
    	 if(SimInfoStatusInput.equals("GOODSTANDING")||SimInfoStatusInput.equals("TESTMODE")||SimInfoStatusInput.equals("AUTOACTIVATE")||SimInfoStatusInput.equals("RMA")||SimInfoStatusInput.equals("SUSPENDED")||SimInfoStatusInput.equals("NWSUSPENDED")||SimInfoStatusInput.equals("PENDING"))
    	 {    
    		 
    	    Application.showMessage("SimInfo Status is ::"+SimInfoStatusInput);
    	    NumerixCos_Validate(input, c);
    	    thirtyDisconnectTableValidation(ComStatus,input, c);//status completed.
    	    sL.simulatorChange30DayDisconnectSimInfo(status, input, c);
    	 }
    	 else
    	 {
     	    
    		 Application.showMessage("NO SimInfo Status ");

    		 thirtyDisconnectTableValidation(ComStatus,input, c);//status completed.
        	 sL.simulatorChange30DayDisconnectSimInfo(status, input, c); 
    	 }
  
    	 //status completed.
    	 
    }
    
    public void ExecutionOrderPartTwo_OTHERS(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
    {
    	
    	
    	 Thread.sleep(2000);
      	 rV.queryLocation_Validate(input, c);
      	 rS.simGetAccountValidate(input, c);
    	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
      	 String Instatus="Open";
    	 thirtyDisconnectTableValidation(Instatus,input, c);
    	 /*String status2= c.getValue("RTPDataSourceCollections","dB_Simulator: statusIter2");
    	 sL.simulatorChange30DayDisconnect(status2, input, c);*/
    	 Thread.sleep(50000);
    	 //may have to run simulator settings according to scenarios.
    	 runGlobalProcess(input, c);
    	//disconnect demicalls.
    	 Thread.sleep(10000);
    	 String ComStatus="Completed";
    	 cc.processHomeSecurityInfo_Validate(input, c);
    	 cc.deleteUnactivatedAccount_Validate(input, c);
    	 cc.DisconnectAccount_CANCEL_Validate(input, c);
    	 cc.orderUpdate_Validate(input, c);
    	 thirtyDisconnectTableValidation(ComStatus,input, c);//status completed.
    	 //sL.simulatorChange30DayDisconnect(status, input, c);
    	 
    }
    
    
    public void thirtyDisconnectTableValidation(String Instatus,Object input,ScriptingContext c) throws ClassNotFoundException, IOException
    {
    	
    	Map<String,String> getMapData=new HashMap <String,String>();
   	    getMapData=connectThirtyDayDisconnectDB(input, c);
   	    String status=getMapData.get("status"); 	    
   	    Application.showMessage("The status in validation process is ::"+status);
   	    String date= getMapData.get("date");
   	    Application.showMessage("The date in validation process is ::"+date);
   	    String service= getMapData.get("service");
   	    Application.showMessage("The service in validation process is ::"+service);
     	//String isLetter= getMapData.get("isLetter");
     	//Application.showMessage("The isLetter in validation process is ::"+isLetter);
     	int p1=sL.validationPointIgnoreCase(status, Instatus, input, c);
     	String InService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
     	int p2=sL.validationPoint(service, InService, input, c);
     	
    	
    	
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
	
	
	
	public Map<String,String> connectThirtyDayDisconnectDB(Object input, ScriptingContext c)throws ClassNotFoundException, IOException
	{
		  Application.showMessage("------------------------------------------------------------------");
		  Application.showMessage("*##Connecting to connectThirtyDayDisconnectDB retrieving data... ");       
		  Application.showMessage("------------------------------------------------------------------");
		  ResultSet rs;
		  String AccountNumber=c.getValue("CcentralNum");
          String subAccount=c.getValue("ACC_input");
          Application.showMessage("The C-Central Number is ::"+AccountNumber);
          Application.showMessage("The Account Number is ::"+subAccount);
          
		  String dStatus = null;
		  String dDateBegin = null;
		  String dService=null;
		  String dIsLetterProcessed=null;
		  Map<String,String> returnMap=new HashMap <String,String>();
		  
		  try
 		  {
			  
	          Statement st = sL.dBConnect(input, c);
	         
  	          rs = st.executeQuery("select * from thirtydaysdisconnect where accountnumber='"+AccountNumber+"' and subaccount='"+subAccount+"'"); 
  	   
  	          while (rs.next())
	          {
  	        	
  	        	dStatus=rs.getString("STATUS");
  	        	dDateBegin=rs.getString("DATE_HOLD_BEGAN");
  	        	dService=rs.getString("SERVICE");
  	        	dIsLetterProcessed=rs.getString("ISLETTERPROCESSED");
  	      	 
  	        	
	          }
  	          Application.showMessage("STATUS is ::"+dStatus);
  	          Application.showMessage("dDateBegin is ::"+dDateBegin);
  	          Application.showMessage("dService is ::"+dService);
  	          //Application.showMessage("dIsLetterProcessed is ::"+dIsLetterProcessed);
  	          returnMap.put("status",dStatus.trim());
  	          returnMap.put("date",dDateBegin.trim());
  	          returnMap.put("service",dService.trim());
  	         // returnMap.put("isLetter",dIsLetterProcessed.trim());
  	          
  	         
  	          st.close();
 		  }		
		  
		  
    	 catch (SQLException e)
 		 {
 		    System.out.println("Connection Failed! Check output console");
 			e.printStackTrace();
 			
 		 }
		return returnMap;
     
	}
	
	public void SimInfoEx_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
		 ResultSet  rs;
		 int callFound=0,v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****SimInfoEx_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:NumerexSimulatorLogMsg/SIMInfoEx' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	             String rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =lC.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=lC.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = lC.extractXml(rs,xmldata2);           
		            String recievedata = lC.extractXml(rs,xmldata1);      
		          	           
		            String sUnitKey= lC.nodeFromKey(senddata,"<sUnitKey>","<sUnitKey>");	            
		            Application.showMessage("sUnitKey is ::"+sUnitKey);
		            String CcentralNum=c.getValue("CcentralNum");
		            if(sUnitKey.equals(CcentralNum))
	            	{
		            	
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-sUnitKey********");
	            		Application.showMessage("Validation is Successfull with Input C-Central Number"+sUnitKey);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	            String SIMInfoExResult= lC.nodeFromKey(recievedata,"<SIMInfoExResult>","</SIMInfoExResult>");
		 	            Application.showMessage("SIMInfoExResult is ::"+SIMInfoExResult);
		 	           
		 	            
		 	            
		 	            String sICCID= lC.nodeFromKey(recievedata,"<sICCID>","</sICCID>");
		 	            Application.showMessage("sICCID is :: "+sICCID); 
		 	            
		 	           if(sICCID==null)
			             {
				            c.report("Send Xml sStatus is NULL");
				            continue;
			            }
			            else
			            {
			            	 String iccId=c.getValue("RTPDataSourceCollections", "dB_Simulator: iccId");
			            	 Application.showMessage("iccId Status from input is::"+iccId);
			            	 if(sICCID.equalsIgnoreCase(iccId))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-sICCID********");
				            	 Application.showMessage("Validation is Successfull with iccId::"+" "+sICCID);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("iccId at Send Xml not Validated as "+sICCID);
				            	 v1=0;
				             }
			            }		
		 	            
		 	           String sStatus= lC.nodeFromKey(recievedata,"<sStatus>","</sStatus>");
		 	            Application.showMessage("sStatus is :: "+sStatus); 
		 	            
		 	           if(sStatus==null)
			             {
				            c.report("Send Xml sStatus is NULL");
				            continue;
			            }
			            else
			            {
			            	 String SimStatus=c.getValue("RTPDataSourceCollections", "dB_Simulator: SimInfoStatus");
			            	 Application.showMessage("Simulator Status from input is::"+SimStatus);
			            	 if(sStatus.equalsIgnoreCase(SimStatus))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-SimInfoStatus********");
				            	 Application.showMessage("Validation is Successfull with sStatus::"+" "+sStatus);
				            	 v1=1;
				             }
				             else
				             {
				            	 v1=0;
				            	 c.report("sStatus at Send Xml not Validated as "+sStatus);
				             }
			            }		

		 	           
		 	           
		            break;
		            }
	             }
	         }
	         if(v1*callFound==1)
	         {
	         	Application.showMessage("WebService Call :: SimInfoEx_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call :: SimInfoEx_Validate is Failed with Validation Errors");
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
	
	
	public void JasperCos_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException  
    {
           
           Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
           LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
           ResultSet  rs;
           String xmldata1 ="receive_data";
	       String xmldata2 ="SEND_DATA";
	       int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
	       Application.showMessage("----------------------------------------------------------");
	       Application.showMessage("*****jasper Function******");    
	       Application.showMessage("----------------------------------------------------------");
     // Application.showMessage("Value is"+c.getValue("ACC_input"));
           try
           {
             Statement st =lC. dBConnect(input, c);
             rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'jaspersrv:TerminalPortType/EditTerminal' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
            
             while (rs.next() && rs.getString(1)!= c.getValue("BaseMsgid"))
             {
                       
                       String rowmsg = rs.getString(1);             
                       if(rs.getBlob("receive_data")==null)
                       {
                       
                         System.out.printf("Your Recieve XML is NULL \n");
                         Application.showMessage("Your Recieve XML is NULL \n");
                         String senddata =lC.extractXml(rs,xmldata2);
                            
                         Application.showMessage("SEND XML is  \n"+ senddata);                                        
                       }
                       else if(rs.getBlob("SEND_DATA")==null)
                       {
                         System.out.printf("Your SEND XML is NULL \n");  
                         String recievedata=lC.extractXml(rs,xmldata1);
                              System.out.printf("RECIEVE XML is \n",recievedata);
                              Application.showMessage("Your Recieve XML is NULL \n");
                              Application.showMessage("Recieve XML is  \n"+ recievedata);                                                          
                             String billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
                                                 
                              if(billingArrangementID_rtp==null)
                              {
                                    Application.showMessage("billingArrangementID value is NULL");
                                    
                                  // billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");

                                    
                                    continue;
                              }
                              else
                              {
                                Application.showMessage("billingArrangementID value from v is::"+billingArrangementID_rtp);
                                Application.showMessage(c.getValue("ACC_input"));
                                Application.showMessage(billingArrangementID_rtp);
                                if(billingArrangementID_rtp == c.getValue("ACC_input"))
                                {
                                       Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                       Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_rtp);
                                }
                                else
                                {
                                       continue;
                                }                                                             
                              }                     
                       }
                       else
                              {
                         
                              String recievedata = lC.extractXml(rs,xmldata1); 
                          // Application.showMessage("Recieve XML is  \n"+ recievedata);
                             
                              
                              String senddata = lC.extractXml(rs,xmldata2);
                              System.out.printf("SEND XML is %s \n",senddata);
                             
                              
                              String ICCID = lC.nodeFromKey(senddata,"<jaspersrv:iccid>","</jaspersrv:iccid><jaspersrv:targetValue>");
                                 
                                  
                              if(ICCID==null)
                              {
                                    System.out.printf("ICCID value from JasperCall is ::NULL \n");
                                    continue;
                              }
                              else
                              {
                                
                                Application.showMessage("ICCID value from JasperCall is::"+ICCID);
                                //Application.showMessage(c.getValue("ACC_input"));
                                //Application.showMessage(billingArrangementID_getCus);
                                
                                if(ICCID.equals(c.getValue("RTPDataSourceCollections","dB_Simulator: iccId")))
                                {
                                       Application.showMessage("ICCID msgid= "+ rs.getString(1));
                                       Application.showMessage("Recieve XML is  \n"+ recievedata);
                                       Application.showMessage("SEND XML is  \n"+ senddata);
                                       Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                       Application.showMessage("Validation is Successfull with Input ICCID"+ICCID);
                                       callFound=1;
                                }
                                else
                                {
                                       continue;
                                }
                                
                                if(callFound==1)
                                {
                                        String Jasp_version= lC.nodeFromKey(senddata,"<jaspersrv:version>","</jaspersrv:version><jaspersrv:licenseKey>");
                                               
                                        Application.showMessage("SEND Xml Jasp_version is ::"+Jasp_version);
                                        
                                        String Jas_licenseKey = lC.nodeFromKey(senddata,"<jaspersrv:licenseKey>","</jaspersrv:licenseKey><jaspersrv:iccid>");
                                        Application.showMessage("SEND Xml Jas_licenseKey is ::"+Jas_licenseKey); 
                                                 

                                        String Jas_targetValue= lC.nodeFromKey(senddata,"<jaspersrv:targetValue>","</jaspersrv:targetValue>");
                                               
                                        Application.showMessage("SEND Xml Jas_targetValue is ::"+Jas_targetValue);
                                        
                                        String Jas_changeType = lC.nodeFromKey(senddata,"<jaspersrv:changeType>","</jaspersrv:changeType></jaspersrv:EditTerminalRequest>");
                                        Application.showMessage("SEND Xml Jas_changeType is ::"+Jas_changeType); 
                                                 
                                
                                /*recv data*/
                                
                                        String Jas_Version = lC.nodeFromKey(recievedata,"<ns2:version xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">","</ns2:version><ns2:build xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
                                        Application.showMessage("SEND Xml Jas_Version is ::"+Jas_Version); 
                                
                                        String Jas_build = lC.nodeFromKey(recievedata,"<ns2:build xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">","</ns2:build><ns2:timestamp xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
                                        Application.showMessage("SEND Xml Jas_build is ::"+Jas_build); 
                                        
                                        String Jas_iccid = lC.nodeFromKey(recievedata,"<ns2:iccid xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">","</ns2:iccid><ns2:effectiveDate xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
                                        Application.showMessage("SEND Xml Jas_iccid is ::"+Jas_iccid); 
                                        
                                }        
                                                 
                               // ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));                       
                        }
                       
                             }
               
            Application.showMessage("********WebService Call::Jasper Valiadted ********");   
                              st.close();

                   }}
           catch (SQLException e)
           {
               Application.showMessage("Connection Failed! Check output console");
                  e.printStackTrace();
                  return;
           }
}

    public void NumerixCos_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
    
    {
                  
           Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
           LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
           LibraryRtp lr=new  LibraryRtp();
           ResultSet  rs;
           String xmldata1 ="receive_data";
           String xmldata2 ="SEND_DATA";
           int callFound=0;
           String Time= c.get("BaseTime").toString();
        //   c.setValue("Numerex","true");
           Application.showMessage("----------------------------------------------------------");
           Application.showMessage("*****Numerix Function******");    
           Application.showMessage("----------------------------------------------------------");
     // Application.showMessage("Value is"+c.getValue("ACC_input"));
         //  lr.findThinktimeOperationRTP(input, c);
        //   c.setValue("Numerex","false"); 
           HashMap Operation=lr.findingoperations(input, c);
     	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("deactivateUnit"));
     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("deactivateUnit"));
     	     
     	  System.out.println("OPERATIONValidation is "+(String) Operation.get("deactivateUnit"));
  	     
           try
           {
                 // Statement st =lC. dBConnect(input, c);
                //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:NumerexSimulatorLogMsg/deactivateUnit' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
        	   rs=lr.reduceThinkTimeRTP(input, c);
                  while (rs.next() && rs.getString(1)!= c.getValue("BaseMsgid"))
                   {
                       
                        if(rs.getBlob("receive_data")==null)
                        {
                       
                         System.out.printf("Your Recieve XML is NULL \n");
                         Application.showMessage("Your Recieve XML is NULL \n");
                         String senddata =lC.extractXml(rs,xmldata2);
                         Application.showMessage("SEND XML is  \n"+ senddata);                                        
                         }
                       else if(rs.getBlob("SEND_DATA")==null)
                       {
                         String recievedata=lC.extractXml(rs,xmldata1);
                         Application.showMessage("Your Recieve XML is NULL \n");
                         Application.showMessage("Recieve XML is  \n"+ recievedata);                                                          
                         String billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
                                                 
                         if(billingArrangementID_rtp==null)
                          {
                            Application.showMessage("billingArrangementID value is NULL");
                                    
                            continue;
                          }
                          else
                          {
                             Application.showMessage("billingArrangementID value from v is::"+billingArrangementID_rtp);
                             Application.showMessage(c.getValue("ACC_input"));
                             Application.showMessage(billingArrangementID_rtp);
                             if(billingArrangementID_rtp == c.getValue("ACC_input"))
                              {
                                       Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                       Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_rtp);
                              }
                              else
                              {
                                       continue;
                              }                                                             
                          }                     
                       }
                       else
                          {
                         
                             String recievedata = lC.extractXml(rs,xmldata1); 
                          // Application.showMessage("Recieve XML is  \n"+ recievedata);
                              System.out.printf("RECIEVE XML is %s \n",recievedata); 
                              
                              String senddata = lC.extractXml(rs,xmldata2);
                              System.out.printf("SEND XML is %s \n",senddata);
                             
                              
                              String CCentrl =lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey></Numerex:deactivateUnit>"); 
                                 
                                  
                              if(CCentrl.equals(null))
                              {
                                    System.out.printf("Call not found \n");
                                   continue;
                              }
                              else
                              {
                                
                                Application.showMessage("Ccentral number fetched from numerix cal is::"+CCentrl);
                                Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                                //Application.showMessage(c.getValue("ACC_input"));
                                //Application.showMessage(billingArrangementID_getCus);
                                
                                
                                /* String Numerx_sUnitKey= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                        
                                 Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey);
                                */
                                
                                if(CCentrl.equals(c.getValue("CcentralNum")))
                                {
                                    
                                       Application.showMessage("Numerix call found"+CCentrl);
                                       callFound=1;
                                }
                                else
                                {
                                       continue;
                                }
                                
                                /*if(callFound==1)
                                {
                                       String Numerx_iAccountId= lC.nodeFromKey(senddata,"<Numerex:iAccountId>","</Numerex:iAccountId>");
                                               
                                        Application.showMessage("SEND Xml Numerx_iAccountId is ::"+Numerx_iAccountId);
                                        
                             String Numerx_sUnitKey1= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                               
                                        Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey1);
                                
                                recv data
                                
                                        String Numerix_activateUnitResult = lC.nodeFromKey(recievedata,"<activateUnitResult xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</activateUnitResult>");
                                        Application.showMessage("SEND Xml Numerix_activateUnitResult is ::"+Numerix_activateUnitResult); 
                                
                                        String Numerix_iErrorCode = lC.nodeFromKey(recievedata,"<iErrorCode xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</iErrorCode>");
                                        Application.showMessage("SEND Xml Numerix_iErrorCode is ::"+Numerix_iErrorCode); 
                                        
                                        String Numerix_sErrorMsg = lC.nodeFromKey(recievedata," <sErrorMsg xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</sErrorMsg></activateUnitResponse>");
                                        Application.showMessage("SEND Xml Numerix_sErrorMsg is ::"+Numerix_sErrorMsg); 
                                        
                                }        */
                                                 
                               // ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));                       
                               }
                       
                             }
               
                      Application.showMessage("********WebService Call::Numerix Valiadted ********");  
                          //    st.close();

                   }
             
           
           
           }
           catch (SQLException e)
           {
               Application.showMessage("Connection Failed! Check output console");
                  e.printStackTrace();
                  return;
           }
      } 

	
	
}
