import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;

public class XHS_resumeFlows_PendingChange 
{
	LibraryRtp lR=new LibraryRtp();
	LibraryRTP_New lN= new LibraryRTP_New();
	RTP_ValidationsClass_UpDown vC = new RTP_ValidationsClass_UpDown();
	ChangeOfServiceClass cos=new ChangeOfServiceClass();
	RestoreClass rS=new RestoreClass();
  
	public void initialization(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
	   	lR.baseMsgid_retrieval(input, c);
	    vC.valuesFromAggregrate(input, c);
	    lR.IterationLogicResumeFlows(input, c);

	  
	}
	
	
	
	public void InstallValidations(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, SQLException, NullPointerException, SAXException, InstantiationException, IllegalAccessException, XPathException
	{
		//simulatorChange(input, c);
		
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
	
	public void CosAndRestoreValidations(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException
	{
		//simulatorChange(input, c); 
		 ChangeOfServiceClass CS = new ChangeOfServiceClass();
		 RTP_SimulatorClass_UpDown sim = new RTP_SimulatorClass_UpDown();
		RTP_ValidationsClass rv=new RTP_ValidationsClass();
		String newService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE");
		String oldService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
		String Suspended_Falg=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SusFlag");
		Application.showMessage("Suspended flag is:" +Suspended_Falg);
		if(c.getValue("IsDemi").equalsIgnoreCase("true"))
			
		{
			
			Application.showMessage("Demi Service");
			if(Suspended_Falg.equalsIgnoreCase("TRUE"))	
			{ 
				Application.showMessage("Suspended flag is true");
		        if(newService.equals(oldService))
					{
				      Application.showMessage("Have Resume calls ..!");
				    // getAccout_ValidateRestore(input, c);
				     sim.simGetAccountValidate_CLS(input, c);
					  //rS.queryLocation_Validate(input, c);
					  rS.Activate_COPS_validate(input, c);
					  rS.processHomeSecurityInfo_Validate(input, c);
					//  rS.RestoreAccount_Validate(input, c);
					  rS.CLS_RestoreAccount_Validate(input, c);
					  orderUpdateResume_Validate(input, c);
					}
				else
				{
					  Application.showMessage("Have Both Resume + COS webservice calls..!");
					  sim.simGetAccountValidate_CLS(input, c);
					 
					  rS.Activate_COPS_validate(input, c);
					  rS.processHomeSecurityInfo_Validate(input, c);
					//  rS.RestoreAccount_Validate(input, c);
					  rS.CLS_RestoreAccount_Validate(input, c);
					  orderUpdateResume_Validate(input, c);
					  		
					 // getAccoutCos_Validate(input, c);
					  cos.cosLogicFlow_CLS(input, c,oldService,newService);
					  orderUpdateCos_Validate(input, c);
				}
		        
			}
			
			else
			{
				Application.showMessage("Suspended flag is false");
				if(newService.equals(oldService))
				{
					
					
			      Application.showMessage("No Resume calls..!");
			      sim.simGetAccountValidate_CLS(input, c);
			     // getAccout_ValidateRestore(input, c);
				 // rS.queryLocation_Validate(input, c);
			     
				  
				}
				
				else
				{
					  Application.showMessage("Have COS webservice calls..!");
					//  getAccout_ValidateRestore(input, c);
					 // sim.simGetAccountValidate_CLS(input, c);
					 // rS.queryLocation_Validate(input, c);
					 // getAccoutCos_Validate(input, c);
					  CS.clsGetAccout_Validatecos(input, c);
					  cos.cosLogicFlow_CLS(input, c,oldService,newService);
					  orderUpdateCos_Validate(input, c);
				}
			}
			
	}
		else
			
			
		{
			 Application.showMessage("Insight service");
			 
			 if(Suspended_Falg.equalsIgnoreCase("TRUE"))		
				{
					if(newService.equals(oldService))
					{
						
				      Application.showMessage("Have only Resume ..!");
				    //  getAccout_ValidateRestore(input, c);
				      sim.simGetAccountValidate_CLS(input, c);
					 // rS.queryLocation_Validate(input, c);
					  rS.processHomeSecurityInfo_Validate(input, c);
					  orderUpdateResume_Validate(input, c);
					  
				    }
					
					else
					{
						
						  Application.showMessage("Have Both Resume + COS webservice calls..!");
						//  getAccout_ValidateRestore(input, c);
						  sim.simGetAccountValidate_CLS(input, c);
						 // rS.queryLocation_Validate(input, c);
						  rS.processHomeSecurityInfo_Validate(input, c);
						  orderUpdateResume_Validate(input, c);
						 // getAccoutCos_Validate(input, c);
						//  CS.clsGetAccout_Validatecos(input, c);
						  cos.cosLogicFlow_CLS(input, c,oldService,newService);
						  orderUpdateCos_Validate(input, c);
					}
				
			
			}
			else
				
				
			 {
				Application.showMessage("Suspended Flag is False");
				if(newService.equals(oldService))
				{
					
				  
			      Application.showMessage("No resume  ..!");
			      sim.simGetAccountValidate_CLS(input, c);
				
				}
				else
				{
					
					  Application.showMessage("Have  COS webservice calls..!");
					  sim.simGetAccountValidate_CLS(input, c);
					//  CS.clsGetAccout_Validatecos_sim(input, c);
					  cos.cosLogicFlow_CLS(input, c,oldService,newService);
					  orderUpdateCos_Validate(input, c);
					  
				}
			}
		}
	}
	
	
	public void simulatorChange(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
	{
		
		//PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
		Application.showMessage("Iam here");
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
        
       
        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: useUcontrolSimulator")+"</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: accountId_Simulator")+"</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+c.getValue("CcentralNum")+"</centralStationAccountNumber><uControlGroupValue>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: uControlGroupValue")+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>ACTIVE</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"+c.getValue("RTPDataSourceCollections", "dB_Simulator: isFoxtrotFlowEnable")+"</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><suspended>1</suspended><delayBetweenSuspendDeactivate>3000</delayBetweenSuspendDeactivate></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
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
	public void getAccout_ValidateRestore(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
	{
		
		 // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	   //  c.setValue("getAccount","true");
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
	   //  lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("getAccount","false"); 
	     
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccount"));


	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
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
	                          
	            	String senddatacls = lC.extractXml(rs,xmldata2);                                        
                    String recievedatacls = lC.extractXml(rs,xmldata1);      
                        String     senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
                        String     recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
   
		         	String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
		         	Application.showMessage("Receive XML after removing naming space:::"+receiveDataTrim);
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
		            	String errorCode=lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/errorCode");
		            	//String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
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
		            		 String group1= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/group");
			 	            //String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
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
			                //String service1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>").trim();
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
		            		 String group1= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/group").trim();
		            		 //String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:internal>").trim();
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
		            	 String accountId_getAcc= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/accountId");
			            //String accountId_getAcc= lC.nodeFromKey(recievedata,"<ns2:accountId>","</ns2:accountId>");
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
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	public void getAccoutCos_Validate(Object input, ScriptingContext c/*String suspendFlag*/) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
	{
		
		 // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     String Time= c.get("BaseTime").toString();
	     c.setValue("getAccount","true");
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
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("getAccount","false"); 
	     
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
			rs=lC.reduceThinkTimeRTP(input, c);
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
		           String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
		           Application.showMessage("receiveDataTrim:::"+receiveDataTrim);
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
		      
//		            	 
		            	//String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
		            	String errorCode=lC.GetValueByXPath(receiveDataTrim, "/GetAccountResponse/errorCode");
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
		                	String centralStationAccountNumber = lC.GetValueByXPath(receiveDataTrim, "/GetAccountResponse/account/premise/centralStationAccountNumber");
			                //String centralStationAccountNumber= lC.nodeFromKey(recievedata,"<ns2:centralStationAccountNumber>","</ns2:centralStationAccountNumber>");
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
		            		 String group1=lC.GetValueByXPath(receiveDataTrim, "/GetAccountResponse/account/group");
			 	            //String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
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
			                //String service1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>").trim();
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
		            		 String group1=lC.GetValueByXPath(receiveDataTrim, "/GetAccountResponse/account/group");
		            		    //String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:internal>").trim();
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
		            	 String accountId_getAcc= lC.GetValueByXPath(receiveDataTrim, "/GetAccountResponse/account/accountId");
			            //String accountId_getAcc= lC.nodeFromKey(recievedata,"<ns2:accountId>","</ns2:accountId>");
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
			             String isSuspend= lC.GetValueByXPath(receiveDataTrim, "/GetAccountResponse/account/accountId ");
			                //String isSuspend= lC.nodeFromKey(recievedata,"<ns2:accountId>","</ns2:accountId>");
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
	       //  st.close();
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
		 // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String service;
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
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
			 	          if(senddata.contains("Hav Market"))
	            	       {
	            	         service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
	 	                     Application.showMessage("Service is ::"+service);		 	                       
	            	       }
	            	    else
	                    	{
	            	    	service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
	            		     Application.showMessage("Service is  :: "+service);
	                    	}
			 	            //String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
			 	            Application.showMessage("Service is ::"+service); 
			 	            
			 	           if(service==null)
				            {
					            c.report("Send Xml Service is NULL");
					            continue;
				            }
				            else
				            {
//				            	 Application.showMessage("Service from Send Xml   is ::"+" "+service);
//				            	 if(service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
//					             {
//					            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
//					            	 Application.showMessage("Validation is Successfull with Service::"+" "+service);
//					            	 v4=1;
//					            	 c.setValue("Product",service);
//					             }
//					             else
//					             {
//					            	 c.report("Service at Send Xml not Validated as "+service);
//					             }
				            	Application.showMessage("Service present");
				            }	
		 	            
		 	            }
		 	            else
		 	            {
		 	            	v1=1;
		 	            	
		 	            	
		 	            	if(senddata.contains("Hav Market"))
		            	       {
		            	         service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
		 	                     Application.showMessage("Service is ::"+service);		 	                       
		            	       }
		            	    else
		                    	{
		            	    	service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
		            		     Application.showMessage("Service is  :: "+service);
		                    	}
		 	 	            Application.showMessage("Service is ::"+service); 
		 	 	            
		 	 	           if(service==null)
		 		            {
		 			            c.report("Send Xml Service is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+service);
//		 		            	 if(service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
//		 			             {
//		 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
//		 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+service);
//		 			            	 v4=1;
//		 			            	 c.setValue("Product",service);
//		 			             }
//		 			             else
//		 			             {
//		 			            	 
//		 			            	 c.report("Service at Send Xml not Validated as "+service);
//		 			             }
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
				            	 Application.showMessage("Validation is Successfull with order type::"+" "+ordType);
				            	 v2=1;
				             }
			            	 
			            	 else if(ordType.equals("CHG"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with order type::"+" "+ordType);
				            	 v2=1;
				             }
				             else
				             {
				            	 Application.showMessage("order type at Send Xml not Validated as "+ordType);
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
	         if(v1*callFound*v3*v5 ==1)
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
		 // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Service;
	     String Time= c.get("BaseTime").toString();
	     c.setValue("OrderUpdate","true");
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********orderUpdateCos_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("OrderUpdate","false");   
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
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
			 	           
			 	          
						/*String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();*/
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
			 	            //String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
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
		 	            	
		 	            	
		 	               // String Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
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
				 	            //String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
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
				            	 Application.showMessage("Validation is Successfull with order type::"+" "+ordType);
				            	 v2=1;
				             }
			            	 
			            	 else if(ordType.equals("RES"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with order type::"+" "+ordType);
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
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
}
