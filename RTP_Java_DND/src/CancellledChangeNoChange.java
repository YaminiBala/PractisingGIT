import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;

public class CancellledChangeNoChange 
{
	LibraryRtp lR=new LibraryRtp();
	LibraryRtp_UpDown lUP=new LibraryRtp_UpDown();
	RTP_ValidationsClass_UpDown vC = new RTP_ValidationsClass_UpDown();
	RTP_SimulatorClass_UpDown RT = new RTP_SimulatorClass_UpDown();

	


	ChangeOfServiceClass cos=new ChangeOfServiceClass();
	RestoreClass rS=new RestoreClass();
	RTP_ValidationsClass rV=new RTP_ValidationsClass();
  
	public void initialization(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
	   	lR.getBaseTime(input, c);
	   	String T1=lR.getTimeInstance();
	   	c.put("T1", T1);
	    vC.valuesFromAggregrate(input, c);
	    lR.IterationLogicResumeFlows(input, c);
        Thread.sleep(2000);
	  
	}
	
	
	
	public void InstallValidations(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, SQLException, NullPointerException, XPathException, SAXException, InstantiationException, IllegalAccessException
	{
		
		if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		{
			vC.demicalls(input, c);
			
		}
		else
		{
			vC.Insightcalls(input, c);
			
		}
		//simulatorChange(input, c);
		
	}
	
	public void RestoreValidations(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException, InstantiationException, IllegalAccessException
	{
		String SusFlag= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SuspendedFlag");
		Application.showMessage("Suspended Flag is ::"+SusFlag);
		
		String Acc=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_AccNumber");
		Application.showMessage("Account Number is  ::"+Acc);
	    //String newService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE");
		//String oldService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
		if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		{
			
		 if (SusFlag.equals("1"))
		 {
	      Application.showMessage("Have  Resume ..!");
	      RT.simGetAccountValidate(input, c);
		  rS.queryLocation_Validate(input, c);
		  rS.Activate_COPS_validate(input, c);
		  rS.processHomeSecurityInfo_Validate(input, c);
		  rS.RestoreAccount_Validate(input, c);
		  orderUpdateResume_Validate(input, c);
		  lUP.storeCCnum(input, c);
		  //simulatorChange(input, c); 
		 }
		 else
		 {
			 Application.showMessage("!..NO Restore calls..!");
			 rS.queryLocation_Validate(input, c);	
			 RT.simGetAccountValidate(input, c);
			 lR.NoDataSetFound(input, c, Acc, "cmb:commonOrderService/orderUpdate");
			 lR.NoDataSetFound(input, c, Acc, "COPSServices:Comcast/ActivateAccount");
			 lR.NoDataSetFound(input, c, Acc, "ucontrolsrv:AccountServicePortType/restoreAccount");
			 lR.NoDataSetFound(input, c, Acc, "srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo");
		 }
		
		}
		
		else if(c.getValue("IsDemi").equalsIgnoreCase("false"))
		{
		 if (SusFlag.equals("1"))
		 {
	      Application.showMessage("Have  Resume ..!");
	      RT.simGetAccountValidate(input, c);
		  rS.queryLocation_Validate(input, c);
		  //rS.Activate_COPS_validate(input, c);
		  rS.processHomeSecurityInfo_Validate(input, c);
		 // rS.RestoreAccount_Validate(input, c);
		  orderUpdateResume_Validate(input, c);
		  lUP.storeCCnum(input, c);
		  //simulatorChange(input, c); 
		 }
		 else if(SusFlag.equals("0"))
		 {
			 Application.showMessage("!..NO Restore calls..!");
			 rS.queryLocation_Validate(input, c);	
			 RT.simGetAccountValidate(input, c);
			 lR.NoDataSetFound(input, c, Acc, "cmb:commonOrderService/orderUpdate");
			 lR.NoDataSetFound(input, c, Acc, "COPSServices:Comcast/ActivateAccount");
			 lR.NoDataSetFound(input, c, Acc, "ucontrolsrv:AccountServicePortType/restoreAccount");
			 lR.NoDataSetFound(input, c, Acc, "srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo");
		 }
		 
		 else
		 {
			 Application.showMessage("!..NO Restore calls..!");
			// rS.queryLocation_Validate(input, c);	
			 //RT.simGetAccountValidate(input, c);
			 lR.NoDataSetFound(input, c, Acc, "cmb:commonOrderService/orderUpdate");
			 lR.NoDataSetFound(input, c, Acc, "COPSServices:Comcast/ActivateAccount");
			 lR.NoDataSetFound(input, c, Acc, "ucontrolsrv:AccountServicePortType/restoreAccount");
			 lR.NoDataSetFound(input, c, Acc, "srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo");
		 }
		}
	}
	
	
	public void simulatorChange(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		
		//PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
		String SusFlag= c.getValue("RTPDataSourceCollections","dB_Simulator: SuspendedFlag");
		Application.showMessage("Suspended Flag is ::"+SusFlag);
		Statement st = lR.dBConnect(input, c);
        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs.next()) 
        {
         String beforeSim = rs.getString(2);
        //c.setValue("BaseMsgid", basemsgID);
        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
        
        }
        st.close();
        Statement st0 = lR.dBConnect(input, c);
        
       
        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: useUcontrolSimulator")+"</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: uControlGroupValue")+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>ACTIVE</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: isFoxtrotFlowEnable")+"</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><suspended>"+SusFlag+"</suspended></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
        st0.executeUpdate(sql);
       
      // ps.setString(1, sql);
       
       //ps.execute();
      
        st0.close();
        Thread.sleep(300000);
        Statement st1 = lR.dBConnect(input, c);
        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs1.next()) 
        {
         String afterSim = rs1.getString(2);
        
        Application.showMessage("Db Simulator After Updation ::"+afterSim);
        
        }
        st0.close();
     
        
	}
	public void getAccout_ValidateRestore(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
		String oldService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");

	     ResultMap= lC.splitter(oldService.trim());
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	   
	     
	     
	     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
	     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
	     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	    
	     
	     
	     
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:SimulatorUcontrolClient/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
	         while (rs.next())
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
		         	String wf_senddata = lC.RemoveNameSpaces(senddata);
		         	String wf_recievedata = lC.RemoveNameSpaces(recievedata);
		         	
		            String id= lC.nodeFromKey(senddata,"<id>","</id>");
		            Application.showMessage("id is ::"+id);
		            Application.showMessage("id from Input is ::"+c.getValue("ACC_input"));
		            
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-GetAccountChangeOfService_Validate********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
//		            	 String group1=lC.xpathValue(recievedata,"//ns3:GetAccountResponse/account/ns2:group");
//			 	         Application.showMessage("group1 is::"+group1);
		            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
		                Application.showMessage("errorCode is::"+errorCode); 
	            	
		                if(errorCode ==null)
		                {
		            	Application.showMessage("errorCode is NULL");
			            //continue;
		                }
		                else
		                {
		            	    Application.showMessage("errorCode from Recieve Xml  from GetAccount is ::"+" "+errorCode);
		            	    if(errorCode.trim().equals("0"))
			                 {
			            	 Application.showMessage("*******Validation Point 2 :: errorCode********");
			            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
			            	 v1=1;
			                 }
			                else
			               {
			            	 c.report("errorCode at Recieve Xml not Validated as "+errorCode);
			               }
		                }
		                
		                if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		 	            {
			                String centralStationAccountNumber= lC.nodeFromKey(recievedata,"<ns2:centralStationAccountNumber>","</ns2:centralStationAccountNumber>");
			                Application.showMessage("centralStationAccountNumber is::"+centralStationAccountNumber); 
		            	
			                if(centralStationAccountNumber ==null)
			                {
			            	Application.showMessage("centralStationAccountNumber is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("centralStationAccountNumber from Recieve Xml  from GetAccount is ::"+" "+centralStationAccountNumber);
			            	    if(centralStationAccountNumber.trim().equals(c.getValue("CcentralNum")))
				                 {
				            	 Application.showMessage("*******Validation Point 3 :: centralStationAccountNumber********");
				            	 Application.showMessage("Validation is Successfull with centralStationAccountNumber::"+" "+centralStationAccountNumber);
				            	 v2=1;
				                 }
				                else
				               {
				            	 c.report("centralStationAccountNumber at Recieve Xml not Validated as "+centralStationAccountNumber);
				               }
			                }
		 	            }
		                else
		                {
		                	v2=1;	
		                }
		            	
		            	
		            	 if(c.getValue("IsMultiExist").equals("true"))
		            	 {
			 	            String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
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
			                
			                
			                String service1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>").trim();
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
		            		 String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:internal>").trim();
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
			            String accountId_getAcc= lC.nodeFromKey(recievedata,"<ns2:accountId>","</ns2:accountId>");
			            Application.showMessage("accountId is::"+accountId_getAcc); 
			             if(accountId_getAcc==null)
			             {
				            c.report("Send Xml Account Number is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+accountId_getAcc);
			            	 if(accountId_getAcc.equals(c.getValue("ACC_input")))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-responseStatus_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+accountId_getAcc);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("Account Number at Send Xml not Validated as "+accountId_getAcc);
				             }
			            }		
		               
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1*v2*v3*v4==1)
	         {
	         	Application.showMessage("WebService Call :: GetAccount is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: GetAccount is Failed with Validation Errors");
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
	
	public void getAccoutCos_Validate(Object input, ScriptingContext c/*String suspendFlag*/) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     
	     String newService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE");
		 String oldService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
	     ResultMap= lC.splitter(oldService.trim());
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	     ResultMap=lC.splitter(newService.trim());
	     c.setValue("NewGroup",ResultMap.get("group"));
	     c.setValue("NewService", ResultMap.get("service"));
	    // c.setValue("IsSingleGroup", ResultMap.get("IsSingle"));
	     c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
	     
	     
	     Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
	     Application.showMessage("NewService::"+c.getValue("NewService"));
	     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
	     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
	     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	     Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));
	     
	     
	     
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
	         while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
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
		            Application.showMessage("id from Input is ::"+c.getValue("ACC_input"));
		            
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-GetAccountChangeOfService_Validate********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
//		            	 String group1=lC.xpathValue(recievedata,"//ns3:GetAccountResponse/account/ns2:group");
//			 	         Application.showMessage("group1 is::"+group1);
		            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
		                Application.showMessage("errorCode is::"+errorCode); 
	            	
		                if(errorCode ==null)
		                {
		            	Application.showMessage("errorCode is NULL");
			            //continue;
		                }
		                else
		                {
		            	    Application.showMessage("errorCode from Recieve Xml  from GetAccount is ::"+" "+errorCode);
		            	    if(errorCode.trim().equals("0"))
			                 {
			            	 Application.showMessage("*******Validation Point 2 :: errorCode********");
			            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
			            	 v1=1;
			                 }
			                else
			               {
			            	 c.report("errorCode at Recieve Xml not Validated as "+errorCode);
			               }
		                }
		                if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		 	            {
			                String centralStationAccountNumber= lC.nodeFromKey(recievedata,"<ns2:centralStationAccountNumber>","</ns2:centralStationAccountNumber>");
			                Application.showMessage("centralStationAccountNumber is::"+centralStationAccountNumber); 
		            	
			                if(centralStationAccountNumber ==null)
			                {
			            	Application.showMessage("centralStationAccountNumber is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("centralStationAccountNumber from Recieve Xml  from GetAccount is ::"+" "+centralStationAccountNumber);
			            	    if(centralStationAccountNumber.trim().equals(c.getValue("CcentralNum")))
				                 {
				            	 Application.showMessage("*******Validation Point 3 :: centralStationAccountNumber********");
				            	 Application.showMessage("Validation is Successfull with centralStationAccountNumber::"+" "+centralStationAccountNumber);
				            	 v2=1;
				                 }
				                else
				               {
				            	 c.report("centralStationAccountNumber at Recieve Xml not Validated as "+centralStationAccountNumber);
				               }
			                }
		 	            }
		                else
		                {
		                	v2=1;	
		                }
		            	
		            	 if(c.getValue("IsMultiExist").equals("true"))
		            	 {
			 	            String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
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
			                
			                
			                String service1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>").trim();
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
		            		    String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:internal>").trim();
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
			            String accountId_getAcc= lC.nodeFromKey(recievedata,"<ns2:accountId>","</ns2:accountId>");
			            Application.showMessage("accountId is::"+accountId_getAcc); 
			             if(accountId_getAcc==null)
			             {
				            c.report("Send Xml Account Number is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+accountId_getAcc);
			            	 if(accountId_getAcc.equals(c.getValue("ACC_input")))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-responseStatus_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+accountId_getAcc);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("Account Number at Send Xml not Validated as "+accountId_getAcc);
				             }
			            }
			             
			                String isSuspend= lC.nodeFromKey(recievedata,"<ns2:accountId>","</ns2:accountId>");
				            Application.showMessage("isSuspend is::"+isSuspend); 
				             if(isSuspend==null)
				             {
					            c.report("Send Xml isSuspend is NULL");
					            continue;
				             }
				             else
				             {
				            	 Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+accountId_getAcc);
				            	 if(isSuspend.equals(c.getValue("ACC_input")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-isSuspend********");
					            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+isSuspend);
					            	 v4=1;
					             }
					             else
					             {
					            	 c.report("Account Number at Send Xml not Validated as "+accountId_getAcc);
					             }
				             }
				             
			             
			             
		               
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1*v2*v3*v4==1)
	         {
	         	Application.showMessage("WebService Call :: GetAccount is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: GetAccount is Failed with Validation Errors");
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

	public void orderUpdateResume_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("OrderUpdate","true");
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********OrderUpdateResume_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("OrderUpdate","false"); 
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
	         while (rs.next())
	         {
	        	
	        
	            String rowmsg = rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata = lC.extractXml(rs,xmldata2);           
		           
		          	           
	                String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_ou); 
	 	           if(accountNumber_ou==null)
		            {
	 	        	   Application.showMessage("Send Xml billingOrderId is NULL");
			            continue;
		            }
	 	           else if(accountNumber_ou.equals(c.getValue("ACC_input")))
	            	{
		            	
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
	            		Application.showMessage("Validation is Successfull with House Number :"+accountNumber_ou);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
		            
		            if(callFound==1)
		            {
		      
		 	            

		 	            String vomInstance= lC.nodeFromKey(senddata,"<value name=\"vomInstance\">","</value><value name=\"ordSource\">");
		 	            Application.showMessage("vomInstance is ::"+vomInstance); 

		 	            String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
		 	            Application.showMessage("ordSource is ::"+ordSource); 
		 	            
		 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		 	            {
		 	            String billingOrderId= lC.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
		 	            Application.showMessage("billingOrderId is ::"+billingOrderId); 
		 	            
			 	           if(billingOrderId==null)
				            {
					            c.report("Send Xml billingOrderId is NULL");
					            continue;
				            }
				            else
				            {
				            	
				            	 if(billingOrderId.equals(c.getValue("CcentralNum")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingOrderId********");
					            	 Application.showMessage("Validation is Successfull with billingOrderId::"+" "+billingOrderId);
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("billingOrderId at Send Xml not Validated as "+billingOrderId);
					             }
				            }
			 	           
			 	            String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
			 	            Application.showMessage("Service is ::"+Service); 
			 	            
			 	           if(Service==null)
				            {
					            c.report("Send Xml Service is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
				            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
					             {
					            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
					            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
					            	 v4=1;
					            	 c.setValue("Product",Service);
					             }
					             else
					             {
					            	 c.report("Service at Send Xml not Validated as "+Service);
					             }
				            }	
		 	            
		 	            }
		 	            else
		 	            {
		 	            	v1=1;
		 	            	
		 	            	
		 	                String Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
		 	 	            Application.showMessage("Service is ::"+Service); 
		 	 	            
		 	 	           if(Service==null)
		 		            {
		 			            c.report("Send Xml Service is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
		 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
		 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
		 			            	 v4=1;
		 			            	 c.setValue("Product",Service);
		 			             }
		 			             else
		 			             {
		 			            	 c.report("Service at Send Xml not Validated as "+Service);
		 			             }
		 		            }	
		 	            }
		 	          
		 	            String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
		 	            Application.showMessage("inputChannel is ::"+inputChannel);
		 	            
		 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
		 	            Application.showMessage("ordType is ::"+ordType);
		 	            
		 	           if(ordType==null)
			            {
				            c.report("Send Xml ordType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
			            	 if(ordType.equals("RES"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
				            	 v2=1;
				             }
				             else
				             {
				            	 Application.showMessage("lt_fireNumber at Send Xml not Validated as "+ordType);
				            	 continue;
				            	 
				             }
			            }	
		 	            
		 	            
		 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
		 	            Application.showMessage("customerType is ::"+customerType); 
		 	            
		 	           if(customerType==null)
			            {
				            c.report("Send Xml customerType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("customerType from Send Xml   is ::"+" "+customerType);
			            	 if(customerType.equals("RES"))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
				            	 Application.showMessage("Validation is Successfull with customerType::"+" "+customerType);
				            	 v3=1;
				             }
				             else
				             {
				            	 c.report("customerType at Send Xml not Validated as "+customerType);
				             }
			            }	
		 	            
		 	         if(senddata.contains("error"))
		 	         {
		 	        	 c.report("Error in the OrderUpdate Call...!");
		 	         }
		 	         else
		 	         {
		 	        	 Application.showMessage("No errors detected...!");
		 	        	 v5=1;
		 	         }
		 	           
		 	        /*  String errorCode= lC.nodeFromKey(senddata,"</value><value name=\"errorCode\">","</value><value name=\"errorText\">");
		 	          Application.showMessage("errorCode is ::"+errorCode); 
		 	            
		 	           if(errorCode==null)
			            {
				            c.report("Send Xml errorCode is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("customerType from Send Xml   is ::"+" "+errorCode);
			            	 if(errorCode.equals("UCNTRL-RESP-CODE-UCE-15113"))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
				            	 v5=1;
				             }
				             else
				             {
				            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
				             }
			            }	*/
		 	            
		 	           	 	           
		            break;
		            }
	            }
	               
	         }
	         if(v1*callFound*v2*v3*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
	         	c.setValue("IsDemi", "false");
	         }
	         else
	         {
	        	 c.setValue("IsDemi", "false");
	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	public void orderUpdateCos_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********orderUpdateCos_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	            String rowmsg = rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata = lC.extractXml(rs,xmldata2);           
		           
		          	           
	                String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_ou); 
	 	            if(accountNumber_ou==null)
		            {
	 	        	   Application.showMessage("Send Xml billingOrderId is NULL");
			            continue;
		             }
	 	           else if(accountNumber_ou.equals(c.getValue("ACC_input")))
	            	{
		            	
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
	            		Application.showMessage("Validation is Successfull with House Number :"+accountNumber_ou);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
		            
		            if(callFound==1)
		            {
		      
		 	            

		 	            String vomInstance= lC.nodeFromKey(senddata,"<value name=\"vomInstance\">","</value><value name=\"ordSource\">");
		 	            Application.showMessage("vomInstance is ::"+vomInstance); 

		 	           
		 	            
		 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		 	            {
		 	        	   
		 	        	  String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
			 	          Application.showMessage("ordSource is ::"+ordSource); 
		 	              String billingOrderId= lC.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
		 	              Application.showMessage("billingOrderId is ::"+billingOrderId); 
		 	            
			 	           if(billingOrderId==null)
				            {
					            c.report("Send Xml billingOrderId is NULL");
					            continue;
				            }
				            else
				            {
				            	
				            	 if(billingOrderId.equals(c.getValue("CcentralNum")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingOrderId********");
					            	 Application.showMessage("Validation is Successfull with billingOrderId::"+" "+billingOrderId);
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("billingOrderId at Send Xml not Validated as "+billingOrderId);
					             }
				            }
			 	           
			 	            String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market =").trim();
			 	            Application.showMessage("Service is ::"+Service); 
			 	   		    String newService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE");

			 	           if(Service==null)
				            {
					            c.report("Send Xml Service is NULL");
					            continue;
				            }
				            else
				            {
				            	 if(Service.equals(newService.trim()))
					             {
					            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
					            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
					            	 v4=1;
					            	 c.setValue("Product",Service);
					             }
					             else
					             {
					            	 c.report("Service at Send Xml not Validated as "+Service);
					             }
				            }	
		 	            
		 	            }
		 	            else
		 	            {
		 	            	v1=1;
		 	            	
		 	            	
		 	                String Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
		 	 	            Application.showMessage("Service is ::"+Service); 
		 	 	            
		 	 	           if(Service==null)
		 		            {
		 			            c.report("Send Xml Service is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
		 		            	 if(Service.equals(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE").trim()))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
		 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
		 			            	 v4=1;
		 			            	 c.setValue("Product",Service);
		 			             }
		 			             else
		 			             {
		 			            	 c.report("Service at Send Xml not Validated as "+Service);
		 			             }
		 		            }	
		 	            }
		 	          
		 	            String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
		 	            Application.showMessage("inputChannel is ::"+inputChannel);
		 	            
		 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
		 	            Application.showMessage("ordType is ::"+ordType);
		 	            
		 	           if(ordType==null)
			            {
				            c.report("Send Xml ordType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
			            	 if(ordType.equals("CHG"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("ordType at Send Xml not Validated as "+ordType);
				             }
			            }	
		 	            
		 	            
		 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
		 	            Application.showMessage("customerType is ::"+customerType); 
		 	            
		 	           if(customerType==null)
			            {
				            c.report("Send Xml customerType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("customerType from Send Xml   is ::"+" "+customerType);
			            	 if(customerType.equals("RES"))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
				            	 Application.showMessage("Validation is Successfull with customerType::"+" "+customerType);
				            	 v3=1;
				             }
				             else
				             {
				            	 c.report("customerType at Send Xml not Validated as "+customerType);
				             }
			            }	
		 	            
		 	            
		 	            
		 	           	 	           
		            break;
		            }
	            }
	               
	         }
	         if(v1*callFound*v2*v3*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
	         	c.setValue("IsRemoveGroup", "false");
	         	c.setValue("IsUpdateTier", "false");
	         	c.setValue("IsAddGroup", "false");
	         	
	         }
	         else
	         {
	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
	        	 c.setValue("IsRemoveGroup", "false");
	        	 c.setValue("IsUpdateTier", "false");
	        	 c.setValue("IsAddGroup", "false");
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
	
	
	public void OrderUpdateFlagEnabled_Validate(String INOrdStatus ,String INbillingOrderId,String INinputChannel,String INsalesChannel, String INshipmentType,String INproductType,String INbillingSystem,String INordType,String INcustomerType,String WORKORDER_ID,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
	{
		Thread.sleep(10000); // Think time in JVM [waits for 10 secs here]
	     
		 String Time= c.get("BaseTime").toString();
		 LibraryRtp sL=new LibraryRtp();
		 ResultSet  rs;
		 int v4=0,v1=0,v2=0,v3=0,v5=0,v6=0,v7=0,v8=0,v9=0,v10=0,v11=0,v12=0,v13=0,v14=0,v15=0,v16=0;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     //String currentDate=sL.getsysTime();
	     String AccountNumber=c.getValue("accountNumber");
        // Application.showMessage("Date of execution is ::"+currentDate);
	     Application.showMessage("--------------------------------------------------------");
	     Application.showMessage("***********OrderUpdate_Validate Function**************");       
	     Application.showMessage("--------------------------------------------------------");
	    
		 try
		 {
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate'  and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
	        
	         while (rs.next())
	         {
	        	
	        	 String rowmsg;
					rowmsg = rs.getString(1);
		            Application.showMessage("MessageID is::"+rowmsg);
		                  	
	            
	            if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else if(rs.getBlob("receive_data")==null) 
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		           
		            Application.showMessage("senddata is ::"+senddata); 
		          
		           
		            
	 	            String billingAccountNo= sL.nodeFromKey(senddata,"<id>","</id>");
		       	    Application.showMessage("billingAccountNo is ::" +billingAccountNo);	       	    
		       	    v1= sL.verificationPoint(billingAccountNo, INbillingOrderId, input, c);
		       	                     
	 	            String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
                    Application.showMessage("The ordStatus from Request is::"+ordStatus);                       
                    v2= sL.verificationPoint(ordStatus, INOrdStatus, input, c);
                    
                   
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
                        String sendDataT=sL.RemoveNameSpaces(senddata);
                    	//String guid= sL.nodeFromKey(senddata,"<value name=\"guid\">","</value><value name=\"accountNumber\">");
                    	String guid= sL.GetValueByXPath(sendDataT, "/comRequest/header/value");
                        Application.showMessage("The guid from Request is::"+guid);                       
                        v3= sL.validationPointIgnoreCase(guid, INbillingOrderId, input, c);
                        
                       // String accountNumber= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"corpId\">");
                    	String accountNumber= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[2]");
                        Application.showMessage("The accountNumber from Request is::"+accountNumber);                       
                        v4= sL.validationPointIgnoreCase(accountNumber, AccountNumber, input, c);
                        
                        String INcorpID= sL.makeCorpIDfromAccountNumber(accountNumber, INbillingSystem);                  
                       // String corpId= sL.nodeFromKey(senddata,"<value name=\"corpId\">","</value><value name=\"billingOrderId\">");
                        String corpId= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[3]");
                        Application.showMessage("The corpId from Request is::"+corpId);                       
                        v5= sL.validationPointIgnoreCase(corpId, INcorpID, input, c);
                        
                        //String billOrder= INbillingOrderId.substring(accountNumber.length());
                        //String billingOrderId= sL.nodeFromKey(senddata,"<value name=\"billingOrderId\">","</value><value name=\"ordType\">");
                        String billingOrderId= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[4]");
                        Application.showMessage("The billingOrderId from Request is::"+billingOrderId);                       
                        v6= sL.validationPointIgnoreCase(billingOrderId, WORKORDER_ID, input, c);
                        
                       // String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
                        String ordType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[5]");
                        Application.showMessage("The ordType from Request is::"+ordType);                       
                        v7= sL.validationPointIgnoreCase(ordType, INordType, input, c);
                    	
                        
                       // String ordSource= sL.nodeFromKey(senddata,"<value name=\"ordSource\">","</value><value name=\"ordStatus\">");
                        String ordSource= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[6]");
                        Application.showMessage("The ordSource from Request is::"+ordSource);                       
                        v8= sL.validationPointIgnoreCase(ordSource, "OP", input, c);
                        
                       // String selfInstall= sL.nodeFromKey(senddata,"<value name=\"selfInstall\">","</value><value name=\"inputChannel\">");
                        String selfInstall= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[8]");
                        Application.showMessage("The selfInstall from Request is::"+selfInstall);                       
                        v9= sL.validationPointIgnoreCase(selfInstall, "1", input, c);
                        
                        //String inputChannel= sL.nodeFromKey(senddata,"<value name=\"inputChannel\">","</value><value name=\"customerType\">");
                        String inputChannel= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[9]");
                        Application.showMessage("The inputChannel from Request is::"+inputChannel);                       
                        v10= sL.validationPointIgnoreCase(inputChannel, INinputChannel, input, c);
                        
                        //String customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"billingSystem\">");
                        String customerType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[10]");
                        Application.showMessage("The customerType from Request is::"+customerType);                       
                        v11= sL.validationPointIgnoreCase(customerType, INcustomerType, input, c);
                        
                       // String billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
                        String billingSystem= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[11]");
                        Application.showMessage("The billingSystem from Request is::"+billingSystem);                       
                        v12= sL.validationPointIgnoreCase(billingSystem, INbillingSystem, input, c);
                        
                        //String active= sL.nodeFromKey(senddata,"<value name=\"active\">","</value><value name=\"salesChannel\">");
                        String active= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[12]");
                        Application.showMessage("The active from Request is::"+active);                       
                        v13= sL.validationPointIgnoreCase(active, "1", input, c);
                        
                        
                        //String salesChannel= sL.nodeFromKey(senddata,"<value name=\"salesChannel\">","</value><value name=\"otherInfo\">");
                        String salesChannel= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[13]");
                        Application.showMessage("The salesChannel from Request is::"+salesChannel);                       
                        v14= sL.validationPointIgnoreCase(salesChannel, INsalesChannel, input, c);
                        
                       // String productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value></header>");
                        String productType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[16]");
                        Application.showMessage("The productType from Request is::"+productType);                       
                        v15= sL.validationPointIgnoreCase(productType, INproductType, input, c);
                        
                       // String street1= sL.nodeFromKey(senddata,"<value name=\"street1\">","</value><value name=\"street2\">");
                        String street1= sL.GetValueByXPath(sendDataT, "/comRequest/address/value");
                        Application.showMessage("The street1 from Request is::"+street1);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                       // String street2= sL.nodeFromKey(senddata,"<value name=\"street2\">","</value><value name=\"city\">");
                        String street2= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[2]");
                        Application.showMessage("The street2 from Request is::"+street2);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                       // String city= sL.nodeFromKey(senddata,"<value name=\"city\">","</value><value name=\"state\">");
                        String city= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[3]");
                        Application.showMessage("The city from Request is::"+city);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                       // String state= sL.nodeFromKey(senddata,"<value name=\"state\">","</value><value name=\"zip\">");
                        String state= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[4]");
                        Application.showMessage("The state from Request is::"+state);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        //String address= sL.nodeFromKey(senddata,"<value name=\"address\">","</value><value name=\"guid\">");
                        String address= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value");
                        Application.showMessage("The address from OrderUpdate is::"+address);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        //String zip= sL.nodeFromKey(senddata,"<value name=\"zip\">","</value><value name=\"zip4\">");
                        String zip= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[5]");
                        Application.showMessage("The zip from Request is::"+zip);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        String PhoneNumber= sL.GetValueByXPath(sendDataT, "/comRequest/customer/value[4]");
                        Application.showMessage("The PhoneNumber from BFC Request is::"+c.get("Cphone").toString());                       

                        Application.showMessage("The PhoneNumber from Request is::"+PhoneNumber);                       
                        v2= sL.validationPointIgnoreCase(PhoneNumber, c.get("Cphone").toString(), input, c);
                       
                        
                        
                        //String zip4= sL.nodeFromKey(senddata,"<value name=\"zip4\">","</value><value name=\"franchiseTaxArea\">");
                        String zip4= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[6]");
                        Application.showMessage("The zip4 from Request is::"+zip4);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        //String franchiseTaxArea= sL.nodeFromKey(senddata,"<value name=\"franchiseTaxArea\">","</value><value name=\"selfInstall\">");
                        String franchiseTaxArea= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[7]");
                        Application.showMessage("The franchiseTaxArea from Request is::"+franchiseTaxArea);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        
                       // String firstName= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
                        String firstName= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value[3]");
                        Application.showMessage("The firstName from Request is::"+firstName);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        
                        //String lastName= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
                        String lastName= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value[4]");
                        Application.showMessage("The lastName from Request is::"+lastName);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        //String shipmentType= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
                        String shipmentType= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value[6]");
                        Application.showMessage("The shipmentType from Request is::"+shipmentType);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        String note= sL.nodeFromKey(senddata,"<note><note>","</note><description>");
                        Application.showMessage("The note from Request is::"+note);                       
                        //= sL.validationPointIgnoreCase(note, "Order Validated", input, c);
                        
                        String desc= sL.nodeFromKey(senddata,"</note><description>","</description><append>");
                        Application.showMessage("The Description from Request is::"+desc);                       
                       // v2= sL.validationPointIgnoreCase(desc, "DOTCOM: Order Validated Successfully", input, c);
                        
                        
                        
                        
                        
                        
                        
                    }
                    
		 	            
		            break;
		            }
	             }
	    
	         
	         if(v1*v2*v3*v4*v5*v6*v7*v8*v9*v10*v11*v12*v13*v14*v15*callFound ==1)
	         {
	        	 
	         	 Application.showMessage("WebService Call :: OrderUpdate is Success[All validation points are Success]");
                 
	         }
	         else
	         {
	        	
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::Order Update is Failed with Validation Errors");

	         }
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		 
		// return OutPut; 
	 }
	
}
