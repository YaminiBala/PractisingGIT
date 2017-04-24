import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
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


public class ScheduleUpgradeDowngradeValidation
{
   LibraryRtp sL=new LibraryRtp();
   public static String rowmsg= null;
	
   
   public void IterationLogic(Object input, ScriptingContext c) throws IOException
	{
		String step=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: STEP");
		Application.showMessage("STEP is ::"+step);
		c.setValue("STEP", step);
		
	}
	public void simulatorScheduleupDown(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
	
		String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		String getAccountStatus=c.getValue("RTPDataSourceCollections","dB_Simulator: getAccountStatus");
		Statement st = sL.dBConnect(input, c);
        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs.next()) 
        {
          String beforeSim = rs.getString(2);
       
          Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
        
        }
        st.close();
        Statement st0 = sL.dBConnect(input, c);
        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: useUcontrolSimulator")+"</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: uControlGroupValue")+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"+status+"</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: isFoxtrotFlowEnable")+"</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><activeStatus>activated</activeStatus><getAccountStatus>"+getAccountStatus+"</getAccountStatus><addressUpdateEventTrigger>CSGAccountDetailUpdated,DDPLocationUpdate</addressUpdateEventTrigger></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
        st0.executeUpdate(sql);
       
    
        st0.close();
        Thread.sleep(400000);
        Statement st1 = sL.dBConnect(input, c);
        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs1.next()) 
        {
        	
         String afterSim = rs1.getString(2);
         Application.showMessage("Db Simulator After Updation ::"+afterSim);
        
        }
        st0.close();
     
        
	}
	
	public void simulatorScheduleupDownServiceUpdate(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
	
		String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		String getAccountStatus=c.getValue("RTPDataSourceCollections","dB_Simulator: getAccountStatus");
		Statement st = sL.dBConnect(input, c);
        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs.next()) 
        {
          String beforeSim = rs.getString(2);
       
          Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
        
        }
        st.close();
        Statement st0 = sL.dBConnect(input, c);
        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: useUcontrolSimulator")+"</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>C99923618</centralStationAccountNumber><uControlGroupValue>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: uControlGroupValue")+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>"+status+"</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: isFoxtrotFlowEnable")+"</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><activeStatus>activated</activeStatus><getAccountStatus>"+getAccountStatus+"</getAccountStatus><addressUpdateEventTrigger>CSGAccountDetailUpdated,DDPLocationUpdate</addressUpdateEventTrigger></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
        st0.executeUpdate(sql);
       
    
        st0.close();
        Thread.sleep(40000);
        Statement st1 = sL.dBConnect(input, c);
        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs1.next()) 
        {
        	
         String afterSim = rs1.getString(2);
         Application.showMessage("Db Simulator After Updation ::"+afterSim);
        
        }
        st0.close();
     
        
	}
	
	public void createCMSAccountID_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		  // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("createCMSaccountID","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****createCMSAccountID_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("createCMSaccountID","false");     
		try
		{
			// Statement st =lC. dBConnect(input, c);
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'hss:HomeSecurityServicePort/createCMSAccountID' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"'order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
	         while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
	         {
	        	
	            rowmsg=rs.getString(1);
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
		          
		           
		            String billingArrangementID_cmsSend= lC.nodeFromKey(senddata,"<hst:billingArrangementID>","</hst:billingArrangementID>");
		            Application.showMessage("billingArrangementID_cmsSend is ::"+billingArrangementID_cmsSend); 
		            if(billingArrangementID_cmsSend.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	    Application.showMessage("*******Validation Point 1 :: WebServicall-createCMSAccountID_Validate********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_cmsSend);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
			            String cmsAccountID= lC.nodeFromKey(recievedata,"<typ:cmsAccountID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:cmsAccountID>");
			            Application.showMessage("cmsAccountID is ::"+cmsAccountID);
			            c.setValue("CcentralNum", cmsAccountID);
			            
			            String billingArrangementIDRes_CMSAccountID= lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
			            Application.showMessage("billingArrangementIDRes_CMSAccountID is::"+billingArrangementIDRes_CMSAccountID); 
		                        
		            }
		            
		                        
	             }
	         }
		  //   st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	/*public void simGetAccountValidate(Object input , ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****getAccount_Validate _Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     ResultMap= lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
	     c.setValue("NewGroup",ResultMap.get("group"));
	     c.setValue("NewService", ResultMap.get("service"));
	    // c.setValue("IsSingleGroup", ResultMap.get("IsSingle"));
	     c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
	     ResultMap=lC.splitter(c.getValue("Product"));
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	     
	     
	     
	     Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
	     Application.showMessage("NewService::"+c.getValue("NewService"));
	     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
	     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
	     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	     Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	            String rowmsg = rs.getString(1);
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
		          	           
		            String id= lC.nodeFromKey(senddata,"<id>","</id>");
		            Application.showMessage("id is ::"+id); 	           
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Get Account Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		            	
		 	             String errorCode_getAcc= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
		 	             Application.showMessage("errorCode is ::"+errorCode_getAcc);
		 	             
		 	             if(errorCode_getAcc==null)
			             {
				            c.report("Recieve Xml Account Number is NULL");
				            continue;
			             }
			             else
			             {
			            	 
			            	 if(errorCode_getAcc.equalsIgnoreCase("0"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
				             }
			            }		
		 	            
		 	             String status_getAcc= lC.nodeFromKey(recievedata,"<status>","</status>");
		 	             Application.showMessage("GetAccount Status is::"+status_getAcc); 
		 	            if(c.getValue("IsMultiExist").equals("true"))
		            	 {
			 	            String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group><group>");
			                Application.showMessage("group1 is::"+group1); 
		            	
			                if(group1 ==null)
			                {
			            	Application.showMessage("Group1 is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
			            	    if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equals(c.getValue("ExistingService")))
				                 {
				            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
				            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
				            	 v3=1;
				                 }
				                else
				               {
				                	v3=0;
				            	 c.report("group1 at Recieve Xml not Validated as "+group1);
				               }
			                }
			                
			                
			                String service1= lC.nodeFromKey(recievedata,"</group><group>","</group></account>").trim();
			                Application.showMessage("service1 is::"+service1); 
		            	
			                if(service1 ==null)
			                {
			            	Application.showMessage("service1 is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("service1 from Recieve Xml  from GetAccount is ::"+" "+service1);
			            	    if(service1.equals(c.getValue("ExistingService")) || service1.equals(c.getValue("ExistingGroup")))
				                 {
				            	 Application.showMessage("*******Validation Point 2 :: service1********");
				            	 Application.showMessage("Validation is Successfull with service1::"+" "+service1);
				            	 v3=1;
				                 }
				                else
				               {
				                	v3=0;
				            	 c.report("group1 at Recieve Xml not Validated as "+service1);
				               }
			                }
			                
			                
		            	}
		            	 
		            	 else
		            	 {
		            		 // for only one group
		            		    String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group></account>").trim();
				                Application.showMessage("group1 is::"+group1); 
			            	
				                if(group1 ==null)
				                {
				            	Application.showMessage("Group1 is NULL");
					            //continue;
				                }
				                else
				                {
				            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
				            	    if(group1.equals(c.getValue("ExistingGroup")))
					                 {
					            	 Application.showMessage("*******Validation Point 2 :: GroupTier********");
					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
					            	 v3=1;
					                 }
					                else
					               {
					                	v3=0;
					            	 c.report("group1 at Recieve Xml not Validated as "+group1);
					               }
				                }
				                
		            		 
		            	 }
			            
		 	            
		            break;
		            }
	             }
	         }
	         if(v1*v3*callFound==1)
	         {
	         	Application.showMessage("WebService Call :: Get Account is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::Get Account is Failed with Validation Errors");
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
	*/
	public void simGetAccountValidate_CLS(Object input , ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException
	{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
    LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
    LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v3=0;
	 String xmldata1 ="receive_data";
    String xmldata2 ="SEND_DATA";
    String Time= c.get("BaseTime").toString();
    //krishna c.setValue("getAccount","true");
    Application.showMessage("-----------------------------------------------------");
    Application.showMessage("*****getAccount_Validate_CLS _Validate Function******");       
    Application.showMessage("-----------------------------------------------------");
    Map<String, String> ResultMap = new HashMap<String,String>();
    ResultMap= lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
    c.setValue("NewGroup",ResultMap.get("group"));
    c.setValue("NewService", ResultMap.get("service"));
    Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
    Application.showMessage("NewService::"+c.getValue("NewService"));
    c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
   
    ResultMap=lC.splitter(c.getValue("Product"));
    c.setValue("ExistingGroup",ResultMap.get("group"));
    c.setValue("ExistingService", ResultMap.get("service"));
    c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
    

    
   
    Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
    Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
    Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
    Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));
    // krishna lr.findThinktimeOperationRTP(input, c);
    // krishna c.setValue("getAccount","false"); 
    
    HashMap Operation=lr.findingoperations(input, c);
    c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccountSim"));
    Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccountSim"));
	try
	{
		// Statement st =lC. dBConnect(input, c);	
      //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
        while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
        {
       	
       
           String rowmsg = rs.getString(1);
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
                         
	           // krishna  String senddata = lC.extractXml(rs,xmldata2);           
	           // krishna String recievedata = lC.extractXml(rs,xmldata1); 
        	   
        	   String senddatacls = lC.extractXml(rs,xmldata2);                                       
               String recievedatacls = lC.extractXml(rs,xmldata1);     
                    String     senddata=lr.clsRemoveAsciiCharacter(senddatacls); 
                    String     recievedata=lr.clsRemoveAsciiCharacter(recievedatacls);
                    String id= lr.nodeFromKey(senddata,"<resourceId>","</resourceId>"); 
                 

	            // krishna String id= lC.nodeFromKey(senddata,"<id>","</id>");
	            Application.showMessage("id is ::"+id); 	           
	            if(id.equals(c.getValue("ACC_input")))
           	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
           		//System.out.printf("SEND XML is %s \n",senddata);
           		Application.showMessage("*******Validation Point 1 :: WebServicall-Get Account Call********");
           		Application.showMessage("Validation is Successfull with Input Account Number"+id);
           		callFound=1;
           	}
           	else
           	{
           		continue;
           	}
           	
	            if(callFound==1)
	            {
	      
	            	
	 	           String receiveDataTrim=lr.RemoveNameSpaces(recievedata);
	 	         
	 	           String errorCode_getAcc=lr.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/errorCode");
	            	//1String errorCode_getAcc= lC.nodeFromKey(receiveDataTrim,"<errorCode>","</errorCode>");
	 	             Application.showMessage("errorCode is ::"+errorCode_getAcc);
	 	             
	 	             if(errorCode_getAcc==null)
		             {
			            c.report("Recieve Xml Account Number is NULL");
			            continue;
		             }
		             else
		             {
		            	 
		            	 if(errorCode_getAcc.equalsIgnoreCase("0"))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
			            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
			             }
		            }		
	 	            
	 	             String status_getAcc= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/status");
	 	             Application.showMessage("GetAccount Status is::"+status_getAcc); 
	 	             
	 	             
	 	             
	 	            if(c.getValue("IsMultiExist").equals("true"))
	            	 {
	 	            	String group1= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
	 	            	//String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group><group>");
		                Application.showMessage("group1 is::"+group1); 
	            	
		                if(group1 ==null)
		                {
		            	Application.showMessage("Group1 is NULL");
			            //continue;
		                }
		                else
		                {
		            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
		            	    if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equals(c.getValue("ExistingService")))
			                 {
			            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
			            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
			            	 v3=1;
			                 }
			                else
			               {
			                	v3=0;
			                	
			            	 c.report("group1 at Recieve Xml not Validated as "+group1);
			               }
		                }
		                
		                String service1= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[2]").trim();
		                //String service1= lC.nodeFromKey(recievedata,"</group><group>","</group></account>").trim();
		                Application.showMessage("service1 is::"+service1); 
	            	
		                if(service1 ==null)
		                {
		            	Application.showMessage("service1 is NULL");
			            //continue;
		                }
		                else
		                {
		            	    Application.showMessage("service1 from Recieve Xml  from GetAccount is ::"+" "+service1);
		            	    if(service1.equals(c.getValue("ExistingService")) || service1.equals(c.getValue("ExistingGroup")))
			                 {
			            	 Application.showMessage("*******Validation Point 2 :: service1********");
			            	 Application.showMessage("Validation is Successfull with service1::"+" "+service1);
			            	 v3=1;
			                 }
			                else
			               {
			                	v3=0;
			            	 c.report("group1 at Recieve Xml not Validated as "+service1);
			               }
		                }
		                
		                
	            	}
	            	 
	            	 else
	            	 {
	            		 // for only one group
	            		 String group1= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group".trim());
	            		    //String group1= lC.nodeFromKey(recievedata,"</premise><group>","</group></account>").trim();
			                Application.showMessage("group1 is::"+group1); 
		            	
			                if(group1 ==null)
			                {
			            	Application.showMessage("Group1 is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
			            	    if(group1.equals(c.getValue("ExistingGroup")))
				                 {
				            	 Application.showMessage("*******Validation Point 2 :: GroupTier********");
				            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
				            	 v3=1;
				                 }
				                else
				               {
				                	v3=0;
				            	 c.report("group1 at Recieve Xml not Validated as "+group1);
				               }
			                }
			                
	            		 
	            	 }
		            
	 	            
	            break;
	            }
            }
        }
        if(v1*v3*callFound==1)
        {
        	Application.showMessage("WebService Call :: Get Account is Success[All validation points are Success]");
        }
        else
        {
       	 c.put("result", "false");
       	 c.report("WebService Call ::Get Account is Failed with Validation Errors");
        }
       // st.close();
	}	
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
	
}
	//-----------------------Krishna--------------------
	public void simGetAccountValidate(Object input , ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException
	{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
    LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
    LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v3=0;
	 String xmldata1 ="receive_data";
    String xmldata2 ="SEND_DATA";
    String Time= c.get("BaseTime").toString();
    c.setValue("getAccount","true");
    Application.showMessage("-------------------------------------------------");
    Application.showMessage("*****getAccount_Validate _Validate Function******");       
    Application.showMessage("-------------------------------------------------");
    Map<String, String> ResultMap = new HashMap<String,String>();
    ResultMap= lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
    c.setValue("NewGroup",ResultMap.get("group"));
    c.setValue("NewService", ResultMap.get("service"));
    Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
    Application.showMessage("NewService::"+c.getValue("NewService"));
    c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
   
    ResultMap=lC.splitter(c.getValue("Product"));
    c.setValue("ExistingGroup",ResultMap.get("group"));
    c.setValue("ExistingService", ResultMap.get("service"));
    c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
    
    
    
   
    Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
    Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
    Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
    Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));
    lr.findThinktimeOperationRTP(input, c);
    c.setValue("getAccount","false");     
	try
	{
		// Statement st =lC. dBConnect(input, c);	
      //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
        while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
        {
       	
       
           String rowmsg = rs.getString(1);
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
	          	           
	            String id= lC.nodeFromKey(senddata,"<id>","</id>");
	            Application.showMessage("id is ::"+id); 	           
	            if(id.equals(c.getValue("ACC_input")))
           	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
           		//System.out.printf("SEND XML is %s \n",senddata);
           		Application.showMessage("*******Validation Point 1 :: WebServicall-Get Account Call********");
           		Application.showMessage("Validation is Successfull with Input Account Number"+id);
           		callFound=1;
           	}
           	else
           	{
           		continue;
           	}
           	
	            if(callFound==1)
	            {
	      
	            	
	 	           String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
	 	           String errorCode_getAcc=lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/errorCode");
	            	//1String errorCode_getAcc= lC.nodeFromKey(receiveDataTrim,"<errorCode>","</errorCode>");
	 	             Application.showMessage("errorCode is ::"+errorCode_getAcc);
	 	             
	 	             if(errorCode_getAcc==null)
		             {
			            c.report("Recieve Xml Account Number is NULL");
			            continue;
		             }
		             else
		             {
		            	 
		            	 if(errorCode_getAcc.equalsIgnoreCase("0"))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
			            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
			             }
		            }		
	 	            
	 	             String status_getAcc= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/status");
	 	             Application.showMessage("GetAccount Status is::"+status_getAcc); 
	 	             
	 	             
	 	             
	 	            if(c.getValue("IsMultiExist").equals("true"))
	            	 {
	 	            	String group1= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/group");
	 	            	//String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group><group>");
		                Application.showMessage("group1 is::"+group1); 
	            	
		                if(group1 ==null)
		                {
		            	Application.showMessage("Group1 is NULL");
			            //continue;
		                }
		                else
		                {
		            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
		            	    if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equals(c.getValue("ExistingService")))
			                 {
			            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
			            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
			            	 v3=1;
			                 }
			                else
			               {
			                	v3=0;
			            	 c.report("group1 at Recieve Xml not Validated as "+group1);
			               }
		                }
		                
		                String service1= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/group[2]").trim();
		                //String service1= lC.nodeFromKey(recievedata,"</group><group>","</group></account>").trim();
		                Application.showMessage("service1 is::"+service1); 
	            	
		                if(service1 ==null)
		                {
		            	Application.showMessage("service1 is NULL");
			            //continue;
		                }
		                else
		                {
		            	    Application.showMessage("service1 from Recieve Xml  from GetAccount is ::"+" "+service1);
		            	    if(service1.equals(c.getValue("ExistingService")) || service1.equals(c.getValue("ExistingGroup")))
			                 {
			            	 Application.showMessage("*******Validation Point 2 :: service1********");
			            	 Application.showMessage("Validation is Successfull with service1::"+" "+service1);
			            	 v3=1;
			                 }
			                else
			               {
			                	v3=0;
			            	 c.report("group1 at Recieve Xml not Validated as "+service1);
			               }
		                }
		                
		                
	            	}
	            	 
	            	 else
	            	 {
	            		 // for only one group
	            		 String group1= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/group".trim());
	            		    //String group1= lC.nodeFromKey(recievedata,"</premise><group>","</group></account>").trim();
			                Application.showMessage("group1 is::"+group1); 
		            	
			                if(group1 ==null)
			                {
			            	Application.showMessage("Group1 is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
			            	    if(group1.equals(c.getValue("ExistingGroup")))
				                 {
				            	 Application.showMessage("*******Validation Point 2 :: GroupTier********");
				            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
				            	 v3=1;
				                 }
				                else
				               {
				                	v3=0;
				            	 c.report("group1 at Recieve Xml not Validated as "+group1);
				               }
			                }
			                
	            		 
	            	 }
		            
	 	            
	            break;
	            }
            }
        }
        if(v1*v3*callFound==1)
        {
        	Application.showMessage("WebService Call :: Get Account is Success[All validation points are Success]");
        }
        else
        {
       	 c.put("result", "false");
       	 c.report("WebService Call ::Get Account is Failed with Validation Errors");
        }
       // st.close();
	}	
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
	
}
	//--------------------------------------------------

	
	public void validateScheduleDisconnect(Object input, ScriptingContext c, String inStatus) throws ClassNotFoundException, IOException, InterruptedException
    {
    	
		Thread.sleep(5000);
    	String Acc=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_AccNumber");
    	Map<String,String> getMap=new HashMap <String,String>();
    	getMap=scheduleDisconnect(input, c, Acc);
    	
    	//Validating Status
    	String Status=getMap.get("status");
    	sL.validationPointIgnoreCase(Status, inStatus, input, c);
    	
    	String creationDate=getMap.get("creationDate");
    	Application.showMessage("Schedule Date is ::"+creationDate);
    	String inschDate=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: ScheduledDate");
    	String scheduleDate=getMap.get("scheduleDate");
    	Application.showMessage("Schedule Date is ::"+scheduleDate);
    	//sL.validationPointIgnoreCase(scheduleDate, inschDate, input, c);
    	c.setValue("scheduleDate", scheduleDate);
    	
    	String inDisService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
    	String disconnectService=getMap.get("disconnectService");
    	Application.showMessage("disconnectService is::"+disconnectService);
    	sL.validationPointIgnoreCase(disconnectService, inDisService, input, c);
    	
    	String inService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE");
    	String installService=getMap.get("installService");
    	Application.showMessage("installService is::"+installService);
    	sL.validationPointIgnoreCase(installService, inService, input, c);
    	
    	//Thread.sleep(1000);
    	//String timeZone=getMap.get("timeZone");
    	//String inTimeZone=(String) c.get("pTimeZone");
    	//Application.showMessage("TimeZone is::"+inTimeZone);
    	//sL.validationPointIgnoreCase(timeZone, inTimeZone, input, c);
    	
    	    	
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
    
    public String addOneToEEPDate(Object input,ScriptingContext c) throws IOException, ParseException 
    {
        
        Application.showMessage("----EEP Date Parsing---");
    	String EEPDate= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: ScheduledDate");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        String s1= EEPDate.replace("T"," ");
        String s2=s1.replace("Z","");
       // Application.showMessage("Parsing String to Date" +s2);//
        Date db=dateFormat.parse(s2);
        Calendar cal=Calendar.getInstance();
        
        cal.setTime(db);
         cal.add( Calendar.DATE, 1 );
          java.util.Date utilDate = cal.getTime();
      
        String EDate= dateFormat.format(utilDate);
        System.out.println("After Replace Edate::"+EDate);
        
       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       Date Ed=formatter.parse(EDate);
       
       String e=formatter.format(Ed);
       System.out.println("formatted Date::"+e);
 
        
           
        return e;
    }
    
    public String trimScheduleDate(String trimScheduleDate,Object input,ScriptingContext c) throws IOException, ParseException 
    {
        
       Application.showMessage("----Scheduling Date Parsing---");
        String SD=trimScheduleDate;
       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
       Date Ed=formatter.parse(SD);
       String e=formatter.format(Ed);
       System.out.println("formatted Date::"+e);
       Application.showMessage("formatted Date::"+e);
       return e;
    }
    
    
    
   
 
    public int updateScheduleDisConnect(Object input, ScriptingContext c,String AccountNumber) throws ClassNotFoundException, SQLException, IOException
    {
    	 
    	  Application.showMessage("------------------------------------------------------------------");
		  Application.showMessage("*##Updating Schedule Disconnect Date... ");       
		  Application.showMessage("------------------------------------------------------------------");
    	  int nRs1; 
		  Statement st = sL.dBConnect(input, c);
		  String Tday=getsysTime();
	      String Yday=getYesterdayDateString();
	      Application.showMessage("Today's date is::" +Tday);
	      Application.showMessage("Yesterday's date is::" +Yday);
	     Application.showMessage("update scheduledisconnect set CREATIONDATE='"+Yday+"',SCHEDULEDATE='"+Tday+"'where accountnumber="+AccountNumber);
		  
		  nRs1=st.executeUpdate("update scheduledisconnect set CREATIONDATE='"+Yday+"',SCHEDULEDATE='"+Tday+"'where accountnumber="+AccountNumber);
		  Application.showMessage("REsult set::" +nRs1);
    	  if(nRs1==0)
    	  {
    		  Application.showMessage("NO Record got updated..!!!");
    		  c.report("NO Record got updated..!!!");
    
    	  }
    	  else
    	  {
    		  Application.showMessage("Number of records updated is ::"+nRs1);
    	  }
    	  
    	  Application.showMessage("------------------------------------------------------------------");
    	  Application.showMessage("End of Schedule Disconnet table updation");
    	  Application.showMessage("------------------------------------------------------------------");
    	  
    	  return nRs1;
    }
	public Map<String,String> scheduleDisconnect(Object input, ScriptingContext c,String AccountNumber)throws ClassNotFoundException, IOException
	{
		  Application.showMessage("------------------------------------------------------------------");
		  Application.showMessage("*##Connecting to SCHEDULE DISCONNECT TABLE... ");       
		  Application.showMessage("------------------------------------------------------------------");
		  
		  ResultSet rs;
		  
		  String dStatus = null;
		  String dCREATIONDATE = null;
		  String dSCHEDULEDATE=null;
		  String dDISCONNECTSERVICE =null;
		  String dINSTALLSERVICE=null;
		  
		 // String dTIMEZONEUTCOFFSET=null;
		  
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
  	        	//dTIMEZONEUTCOFFSET=rs.getString("TIMEZONEUTCOFFSET");
  	        	//dIsLetterProcessed=rs.getString("ISLETTERPROCESSED");
  	      	 
  	        	
	          }
  	          Application.showMessage("STATUS is ::"+dStatus);
  	          Application.showMessage("dCREATIONDATE is ::"+dCREATIONDATE);
  	          Application.showMessage("dSCHEDULEDATE is ::"+dSCHEDULEDATE);
  	          Application.showMessage("dDISCONNECTSERVICE is ::"+dDISCONNECTSERVICE);
  	          Application.showMessage("dINSTALLSERVICE is ::"+dINSTALLSERVICE);
  	          //Application.showMessage("dTIMEZONEUTCOFFSET is ::"+dTIMEZONEUTCOFFSET);
  	          
  	          
  	          returnMap.put("status",dStatus.trim());
  	          returnMap.put("creationDate",dCREATIONDATE.trim());
  	          returnMap.put("scheduleDate",dSCHEDULEDATE.trim());
  	          returnMap.put("disconnectService",dDISCONNECTSERVICE.trim());
  	          returnMap.put("installService",dINSTALLSERVICE.trim());
  	          //returnMap.put("timeZone",dTIMEZONEUTCOFFSET.trim());
  	         
  	          st.close();
 		  }		
		  
		  
    	 catch (SQLException e)
 		 {
 		    System.out.println("Connection Failed! Check output console");
 			e.printStackTrace();
 			
 		 }
		  
		  Application.showMessage("------------------------------------------------------------------");
		  Application.showMessage("*##End of SCHEDULE DISCONNECT TABLE... ");       
		  Application.showMessage("------------------------------------------------------------------");
		  
		  return returnMap;
     
	}
	
	public void getWorkOrderValidate(Object input , ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, ParseException
	{
		
		
		 Application.showMessage("---------------------------------------------------------------------------------"); 
		 Application.showMessage("******** Into getWorkOrder Validation ********");
		 Application.showMessage("---------------------------------------------------------------------------------"); 
		 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	   //  c.setValue("getWorkOrder","true");
	     String scheduleDate=c.getValue("scheduleDate");
	     Application.showMessage("Schedule Date Before Parsing: " + scheduleDate);
	     String scheduleDateValidate= trimScheduleDate(scheduleDate,input, c);
	     Application.showMessage("Schedule Date After Parsing: " + scheduleDateValidate);
	     String EEPDate  = addOneToEEPDate(input, c);
	     Application.showMessage("EEP Date is: " +EEPDate);
	     String TomoDate = getTommorowDateString();
	     Application.showMessage("Tomorrow Date is"+TomoDate);
	    // lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("getWorkOrder","false");  
	        
	     
	 	try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'SchedulingService:SchedulingServicePort/getWorkOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
	 		//rs=lC.reduceThinkTimeRTP(input, c);
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	 
	        
	            String rowmsg = rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("No Response from getWorkOrder... Your Recieve XML is NULL \n");
	            	Application.showMessage("Schedule date is:" +scheduleDateValidate);
	            
	            	if(EEPDate.equals(null))
	            	{ 
	            		Application.showMessage("No Date in EEP...Update Date to next day's Date");
	            		if(scheduleDateValidate.equals(TomoDate))
	            	       {
	            			
	            		   Application.showMessage("No response from GWO:Date Updated to Tomorrow's Date: Date validated");
	            		   
	            	        }
	            	
	            		else
	            		   {
	            			  
	            			  c.report("No response from GWO:Date is NOT Updated to Tomorrow's Date: Date is not validated");
	            			  
	            		   }
	            	}
	            		
	            		
	            	else 
	            	{  
	            		Application.showMessage("DATE FROM EEP");
						if (scheduleDateValidate.equals(EEPDate))
	            	      {
	            			
	            		   Application.showMessage("No response from GWO:Date from EEP ... DATE is Validated");
	            		   
	            	      }
	            	
	            		else
	            		  {
	            			
	            			c.report("No response from GWO:Date from EEP ... Date is not validated");
	            			
	            		  }
	            	}
	            	            	            	
	              
	            }
	            
	            else
	            {
	               Application.showMessage("Error Response from getWorkOrder...");          
		           String recievedata = lC.extractXml(rs,xmldata1);  
		           if(recievedata.contains("serviceFault"))
		           {
		        	   if(EEPDate.equals(null))
		            	{ 
		            		Application.showMessage("Update Date to next day's Date");
		            		if(scheduleDateValidate.equals(TomoDate))
		            	       {
		            			
		            		   Application.showMessage("Error response from GWO: Update date to tomorrow's Date... Date validated");
		            		   
		            	        }
		            	
		            		else
		            		   {
		            			  
		            			  c.report("Error response from GWO: Updattion of date to tomorrow's Date Failed... Date is not validated");
		            			  
		            		   }
		            	}
		            		
		            		
		            	else 
		            	{  
		            		Application.showMessage("DATE should be  FROM EEP");
		            		 if(scheduleDateValidate.equals(EEPDate))
		            	      {
		            			
		            		   Application.showMessage("Error response from GWO: Date from EEP...DATE is Validated");
		            		   
		            	      }
		            	
		            		else
		            		  {
		            			
		            			c.report("Error response from GWO:Date is not from EEP ..Date is not validated");
		            			
		            		  }
		            	}
		           }
		           else
		           {
		        	   Application.showMessage("DATE is from Scheduling Service Validation");
		        	   String date= lC.nodeFromKey(recievedata,"<typ:date>","</typ:date>");
		        	   if(scheduleDateValidate.equals(date))
	            	      {
	            			
	            		   Application.showMessage("Date from Scheduling service: DATE is Validated");
	            		   
	            	      }
	            	
	            		else
	            		  {
	            			
	            			c.report("Date from Scheduling service:Date is not validated");
	            			
	            		  }
		        	   
		           }
	            	
	            }
	            
	            
	           }      	
		           
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 	
	 	 Application.showMessage("---------------------------------------------------------------------------------"); 
		 Application.showMessage("******** Exiting from getWorkOrder Validation ********");
		 Application.showMessage("---------------------------------------------------------------------------------"); 
		
	}
	public void OrderUpdate_tocheckAEE(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, NullPointerException, SAXException 
	{
		 // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 HandleInvalidServicesLibrary HL=new HandleInvalidServicesLibrary();
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String accountNumber_ou;
		 String billingOrderID;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("OrderUpdate","true");
	    Application.showMessage("-------------------------------------------------------------");
	    Application.showMessage("***********OrderUpdate_Validate_Tocheck AEE Function**************");       
	    Application.showMessage("-------------------------------------------------------------");
	    lC.findThinktimeOperationRTP(input, c);
	    c.setValue("OrderUpdate","false");    
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	     //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	            { 
		             rowmsg=rs.getString(1);
		            if(rs.getBlob("receive_data")==null)
		            { 
	           	     Application.showMessage("Your Recieve XML is NULL \n");
	           	   	 String senddata = lC.extractXml(rs,xmldata2);           
		             String senddataTrim=lC.RemoveNameSpaces(senddata); 
		          	 Application.showMessage("SendDataTrim ::: " +senddataTrim );	          	        	
		          	 String accountNumber=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value");
	 	             Application.showMessage("accountNumber is ::"+accountNumber); 
	 	             if(accountNumber==null)
				            {
	 	        	     Application.showMessage("Send Xml billingOrderId is NULL");
					            continue;
				            }
	 	              else if(accountNumber.equals(c.getValue("ACC_input")))
				            	{
		            	 Application.showMessage("SEND XML is :: \n"+senddata);
	            		 Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
	            		 Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);
				            		callFound=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            	if(callFound==1)
				            	{
	 	        	     String Service;	
	 	        	     if(senddataTrim.contains("COM"))
								             {
		          			 Application.showMessage("SEND XML is :: \n"+senddata);	          		    
			            	 billingOrderID=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value[5]");
			            	 c.setValue("CCentralNum", billingOrderID);
			            	 Application.showMessage("CCentral number:::"+billingOrderID);
			                 	if(senddata.contains("Hav Market"))
				            {
			            	         Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
			 	                     Application.showMessage("Service is ::"+Service);		 	                       
					             }
					             else
					             {
			            		     Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
			            		     Application.showMessage("Service is  :: "+Service);
				            }
			                 	if(Service!=null || billingOrderID!=null )
				            {
			            		      Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim());
					                	 Application.showMessage("*******Validation Point 2 :: WebServicall(OrderUpdate)-Service********");
					            	    Application.showMessage("Validation is Successfull with Service::"+" "+Service);
				            	v2=1;
					            	   c.setValue("Product",Service);
					            	}
					            	
								            else
				                  	     c.report("Send Xml Service || BillingOrderID is NULL");
								           	            
								            					            
		           		        Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
		           		        Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);	           		
			            	break;
		            }     
	 	        	        else if (senddataTrim.contains("ERR")||senddataTrim.contains("ServiceFault") )
		            { 
		          		      c.report("Error in OrderStatus ID");
		          		       break;
								             }
								             else
								             {
								            	 continue;
								             }
			            }
	              } }
			            
				            
						         
	        if(callFound * v2==1)
						          
	        {   
								            					            
	        	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
									             }
									             else
									             {
		            
	        	c.report("WebService Call :: OrderUpdate_Validate is failed with validation points ]");
	             }
	         //    st.close();
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}	
	}
	
	
}		

