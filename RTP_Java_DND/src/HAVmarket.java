import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.xpath.XPathException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class HAVmarket 
{
	    
	    LibraryRtp sL = new LibraryRtp();
	    RTP_ValidationsClass rV=new RTP_ValidationsClass();
	    SuspendClass sClass=new SuspendClass();
	    public String OutPut="Passed";

	   
	   
	    public void Initialization(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
	    {
	    	
	    
            rV.execution(input, c);
	        sL.baseMsgid_retrieval(input, c);
	        sL.IterationLogic(input, c);
	        TestCaseDetails(input, c);
	        String T1=sL.getTimeInstance();
	   	    Application.showMessage("The start time is ::"+T1);
	   	    c.put("T1",T1);
	        
	     // sL.IterationLogic30DayDisconnect(input, c);
	    	 
	     }
	 
	    public void HAVMarketWebServiceCalls(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathException, SAXException
	    {
	    	
	        String sc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER");
	        String CcentralNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: CcentralNumber");
	        String INservice= c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE");
	        String IsError= c.getValue("RTPNormalScenariosdS", "RTP_InputParams: NegativeScenario");

	        
	    	if(sc.equals("1"))
	    	{
	    		 Application.showMessage("New Install");
	    		// rV.demicalls(input, c);
	    		LibraryRtp.AddLog("***********Install Validations**************", "Y");   
	    		//rV.createCMSAccountID_Validate(input, c);
	    		//rV.orderUpdate_Validate(input, c);
	    		simulatorHAV(input, c);
	    		Thread.sleep(300000);
	    		LibraryRtp.AddLog("***********___________________**************", "Y");
	    		
	    	}
	    	
	    	else if(sc.equals("3"))
	    	{
			      LibraryRtp.AddLog("***********Disconnect Validations**************", "Y");    
			         Thread.sleep(10000);
			       // sClass.queryLocation_Validate(input, c);
		    		// sClass.processHomeSecurityInfo_Validate(input, c);
		    	     Jasper_Validate(input, c);
		    		// FindSites_Validate(CcentralNumber,input, c); 
			      //   SetSiteStatus_Validate("Terminated", input, c);
			       //  OutService_valiadtion(CcentralNumber,input, c);
			       //  OrderUpdate_Validate(CcentralNumber, INservice, "DIS", input, c);
	    		
			      LibraryRtp.AddLog("***********___________________**************", "Y");
	    	}
	    	else if(sc.equals("4"))
	    	{
	    		 LibraryRtp.AddLog("***********Suspend Validations**************", "Y"); 
	    		 
	    		 if(IsError.equalsIgnoreCase("Error"))
	    		 {
	    			 Thread.sleep(30000);
	    			// sClass.queryLocation_Validate(input, c);
		    		// sClass.processHomeSecurityInfo_Validate(input, c);
		    	     Jasper_Validate(input, c);
		    		 FindSites_Validate(CcentralNumber,input, c); 
			         SetSiteStatus_Validate("Suspended", input, c);
			         OutService_valiadtion(CcentralNumber,input, c);
			         
			         OrderUpdateNeg_Validate(CcentralNumber, INservice, "ERR", input, c);
			        
	    		 }
	    		 else
	    		 {
	    			 
	    			 
	    			 
	    			 Thread.sleep(10000);
	    		// sClass.queryLocation_Validate(input, c);
	    		// sClass.processHomeSecurityInfo_Validate(input, c);
	    	     Jasper_Validate(input, c);
	    		 FindSites_Validate(CcentralNumber,input, c); 
		         SetSiteStatus_Validate("Suspended", input, c);
		         OutService_valiadtion(CcentralNumber,input, c);
		        // sClass.orderUpdate_Validate(input, c);
		         OrderUpdate_Validate(CcentralNumber, INservice, "NPD", input, c);
		         LibraryRtp.AddLog("***********___________________**************", "Y");
	    		 }
	    	}
	    	
	    	else if(sc.equals("5"))
	    	{
	    		RestoreClass Rc=new RestoreClass();
	    		Thread.sleep(10000);
	    		LibraryRtp.AddLog("***********Restore Validations**************", "Y");
	    	//	 sClass.queryLocation_Validate(input, c);
	    		// Rc.processHomeSecurityInfo_Validate(input, c);
	    	     Jasper_Validate(input, c);
	    		 FindSites_Validate(CcentralNumber,input, c); 
		         SetSiteStatus_Validate("Active", input, c);
		         OutService_valiadtion(CcentralNumber,input, c);
		         OrderUpdate_Validate(CcentralNumber, INservice, "RES", input, c);
	    		
	    		LibraryRtp.AddLog("***********___________________**************", "Y");
	    		
	    	} 
	    	else if(sc.equals("11"))
			{
	    		Thread.sleep(10000);	
				LibraryRtp.AddLog("***********Cancel no Validations**************", "Y"); 
				//	rV.CancelValidations(input, c);
					
			    LibraryRtp.AddLog("***********___________________**************", "Y");
					
			}
			else
			{
				
				
					c.report("No relevant call scenario Number Found, go and change Scenario Number!!");
			}
	    	
	    	
	}
	    	
	     
	    
	    
	    public void TestCaseDetails(Object input,ScriptingContext c) throws IOException
	    {
	    	c.put("testSetID",c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestSetID"));
  	        Application.showMessage("TestSetID is::"+c.get("testSetID"));
  	        
  	        c.put("testCaseID",c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseID"));
	        Application.showMessage("testCaseID is::"+c.get("testCaseID"));
  	       
	    	
	    }
	    
	    
	
		public void SetSiteStatus_Validate(String INAccountStatus,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     
			 ResultSet  rs;
			 int v4=0,v1=0,v2=0,v3=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     String AccountNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");
		     String Time= c.get("BaseTime").toString();
		     c.setValue("SetSiteStatus","true");
		     Application.showMessage("--------------------------------------------------------");
		     Application.showMessage("***********SetSiteStatus_Validate Function**************");       
		     Application.showMessage("--------------------------------------------------------");
		      LibraryRtp.AddLog("***********SetSiteStatus_Validate Function**************", "Y");  
		      sL.findThinktimeOperationRTP(input, c);
		      c.setValue("SetSiteStatus","false"); 
			 try
			 {
				// Statement st =sL. dBConnect(input, c);	
		      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:IControlSimulatorLogMsg/SetSiteStatus'  and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
				 rs=sL.reduceThinkTimeRTP(input, c);
		         while (rs.next())
		         {
		        	
		        
		            
		         
		            	
		            if(rs.getBlob("receive_data")==null)
		            {
		            
		            	Application.showMessage("Your Recieve XML is NULL \n");
		            	
		            	String senddata =sL.extractXml(rs,xmldata2);
		            	Application.showMessage("Your Recieve XML is::\n"+senddata);
		            }
		            else if(rs.getBlob("SEND_DATA")==null)
		            {
		            	Application.showMessage("Your SEND XML is NULL \n");	
		            	String recievedata=sL.extractXml(rs,xmldata1);
		            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		            }
		            else
		            { 
		                          
			            String senddata = sL.extractXml(rs,xmldata2);           
			            String recievedata = sL.extractXml(rs,xmldata1);      
			            Application.showMessage("senddata is ::"+senddata); 
			            Application.showMessage("recievedata is ::"+recievedata); 
			            LibraryRtp.AddLog("senddata is ::"+senddata, "N");
			            LibraryRtp.AddLog("recievedata is ::"+recievedata, "N");
			           
		 	            
			            String siteID= sL.nodeFromKey(senddata,"<icontrolsrv:SiteId>","</icontrolsrv:SiteId>");
                        Application.showMessage("The siteID is::"+siteID);                        
                        v1= sL.validationPoint(siteID, "123456", input, c);
                        
                        String AccountStatus= sL.nodeFromKey(senddata,"<icontrolsrv:AccountStatus>","</icontrolsrv:AccountStatus>");
                        Application.showMessage("The AccountStatus is::"+AccountStatus);                       
                        v2= sL.validationPoint(AccountStatus, INAccountStatus, input, c);
                        
                        String Success= sL.nodeFromKey(recievedata,"<Success>","</Success>");
                        Application.showMessage("The Success is::"+Success);
                        v3= sL.validationPoint(Success, "1", input, c);

                        String ResultCode= sL.nodeFromKey(recievedata,"<ResultCode>","</ResultCode>");
                        Application.showMessage("The ResultCode is::"+ResultCode);
                        v4= sL.validationPoint(ResultCode, "OK", input, c);
		 	            
			 	            
			            break;
			            }
		             }
		    
		         
		         if(v1*v2*v3*v4 ==1)
		         {
		         	Application.showMessage("WebService Call :: SetSiteStatus_Validate is Success[All validation points are Success]");
                    LibraryRtp.AddLog("WebService Call :: SetSiteStatus_Validate is Success[All validation points are Success]", "Y");
		         }
		         else
		         {
		        	 OutPut="Failed";
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::SetSiteStatus_Validate is Failed with Validation Errors");
	                 LibraryRtp.AddLog("WebService Call ::SetSiteStatus_Validate is Failed with Validation Errors", "Y");

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
	 
	    
	    public void FindSites_Validate(String CcentralNumber,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	        String AccountNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");

			 ResultSet  rs;
			 int v4=0,v1=0,v2=0,v3=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     String Time= c.get("BaseTime").toString();
		     c.setValue("FindSites","true");
		     Application.showMessage("--------------------------------------------------------");
		     Application.showMessage("***********FindSites_Validate Function**************");       
		     Application.showMessage("--------------------------------------------------------");
		      LibraryRtp.AddLog("***********FindSites_Validate Function**************", "Y");
		      sL.findThinktimeOperationRTP(input, c);
		      c.setValue("FindSites","false"); 
			try
			{
				// Statement st =sL. dBConnect(input, c);	
		       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:IControlSimulatorLogMsg/FindSites' and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
				rs=sL.reduceThinkTimeRTP(input, c);
		         while (rs.next())
		         {
		        	
		        
		           /* int rowmsg;
					rowmsg = Integer.valueOf(rs.getString(1));
					Application.showMessage("The Base msgid is ::"+Baseid);
					Application.showMessage("Current Row msgid is ::"+rowmsg);
					LibraryRtp.AddLog("The Base msgid is ::"+Baseid, "N");
		            
		            if(Baseid>=rowmsg)break;*/
		            	
		            if(rs.getBlob("receive_data")==null)
		            {
		            
		            	Application.showMessage("Your Recieve XML is NULL \n");
		            	
		            	String senddata =sL.extractXml(rs,xmldata2);
		            	Application.showMessage("Your Recieve XML is::\n"+senddata);
		            }
		            else if(rs.getBlob("SEND_DATA")==null)
		            {
		            	Application.showMessage("Your SEND XML is NULL \n");	
		            	String recievedata=sL.extractXml(rs,xmldata1);
		            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		            }
		            else
		            { 
		                          
			            String senddata = sL.extractXml(rs,xmldata2);           
			            String recievedata = sL.extractXml(rs,xmldata1);      
			            Application.showMessage("senddata is ::"+senddata); 
			            Application.showMessage("recievedata is ::"+recievedata); 
			            LibraryRtp.AddLog("senddata is ::"+senddata, "N");
			            LibraryRtp.AddLog("recievedata is ::"+recievedata, "N");
			           
		 	            
			            String CmsId= sL.nodeFromKey(senddata,"<icontrolsrv:CmsId>","</icontrolsrv:CmsId>");
                        Application.showMessage("The CmsId is::"+CmsId);                        
                        v1= sL.validationPoint(CmsId, CcentralNumber, input, c);
                        
                        String Partner= sL.nodeFromKey(senddata,"<icontrolsrv:Partner>","</icontrolsrv:Partner>");
                        Application.showMessage("The Partner is::"+Partner);                       
                        v2= sL.validationPoint(Partner, "comcast", input, c);
                        
                        String Success= sL.nodeFromKey(recievedata,"<Success>","</Success>");
                        Application.showMessage("The Success is::"+Success);
                        v3= sL.validationPoint(Success, "1", input, c);

                        String ResultCode= sL.nodeFromKey(recievedata,"<ResultCode>","</ResultCode>");
                        Application.showMessage("The ResultCode is::"+ResultCode);
                        v4= sL.validationPoint(ResultCode, "OK", input, c);
		 	            
			 	            
			            break;
			            }
		             }
		    
		         
		         if(v1*v2*v3*v4 ==1)
		         {
		         	Application.showMessage("WebService Call :: FindSites is Success[All validation points are Success]");
                    LibraryRtp.AddLog("WebService Call :: FindSites is Success[All validation points are Success]", "Y");
		         }
		         else
		         {
		        	 OutPut="Failed";
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::FindSites is Failed with Validation Errors");
	                 LibraryRtp.AddLog("WebService Call ::FindSites is Failed with Validation Errors", "Y");

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
	 
	    
	    public void OutService_valiadtion(String INCcentralNumber,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     
			 ResultSet  rs;
			 int v1=0,v2=0,v3=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     String AccountNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");
		     String Time= c.get("BaseTime").toString();
		     c.setValue("OutService","true");
		     Application.showMessage("--------------------------------------------------------");
		     Application.showMessage("***********OutService Function**************");       
		     Application.showMessage("--------------------------------------------------------");
		      LibraryRtp.AddLog("***********OutService Function**************", "Y");    
		      sL.findThinktimeOperationRTP(input, c);
		      c.setValue("OutService","false"); 
			try
			{
				// Statement st =sL. dBConnect(input, c);	
		       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'UCCService13_11:SiteImportSoap/OutService' and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
				rs=sL.reduceThinkTimeRTP(input, c);
		         while (rs.next())
		         {
		        	
		        
		         
		            if(rs.getBlob("receive_data")==null)
		            {
		            
		            	Application.showMessage("Your Recieve XML is NULL \n");
		            	
		            	String senddata =sL.extractXml(rs,xmldata2);
		            	Application.showMessage("Your Recieve XML is::\n"+senddata);
		            }
		            else if(rs.getBlob("SEND_DATA")==null)
		            {
		            	Application.showMessage("Your SEND XML is NULL \n");	
		            	String recievedata=sL.extractXml(rs,xmldata1);
		            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		            }
		            else
		            { 
		                          
			            String senddata = sL.extractXml(rs,xmldata2);           
			            String recievedata = sL.extractXml(rs,xmldata1);      
			            Application.showMessage("senddata is ::"+senddata); 
			            Application.showMessage("recievedata is ::"+recievedata); 
			            LibraryRtp.AddLog("senddata is ::"+senddata, "N");
			            LibraryRtp.AddLog("recievedata is ::"+recievedata, "N");
			           
		 	            
			            String TransmitterCode= sL.nodeFromKey(senddata,"<UCCService13_11:TransmitterCode>","</UCCService13_11:TransmitterCode>");
                        Application.showMessage("The TransmitterCode is::"+TransmitterCode);                        
                        v1= sL.validationPoint(TransmitterCode, INCcentralNumber, input, c);
                        
                        String UserName= sL.nodeFromKey(senddata,"<UCCService13_11:UserName>","</UCCService13_11:UserName>");
                        Application.showMessage("The UserName is::"+UserName);                       
                        v2= sL.validationPoint(UserName, "Comcast", input, c);
                        
                        
            	        String sc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER");
            	        if(sc.equalsIgnoreCase("3"))
            	        {
                            String OOSCategory= sL.nodeFromKey(senddata,"<UCCService13_11:OOSCategory>","</UCCService13_11:OOSCategory>");
                            Application.showMessage("OOSCategory is ::"+OOSCategory);
            	        }
           		     
            	        if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: NegativeScenario").equalsIgnoreCase("ERROR"))
            	        {
            	        	String ErrorNum= sL.nodeFromKey(recievedata,"<ErrorNum>","</ErrorNum>");
                            Application.showMessage("The ErrorNum is::"+ErrorNum);
                            v3= sL.validationPoint(ErrorNum, "1209", input, c);
                            
                            String errText= sL.nodeFromKey(recievedata,"<ErrorMessage>","</ErrorMessage>");
                            Application.showMessage("The errText is::"+errText);
                            v3= sL.validationPoint(errText, "Invalid Transmitter Code "+INCcentralNumber, input, c);
                            
                            
                            
            	        }
            	        else
            	        {
            	        	String ErrorNum= sL.nodeFromKey(recievedata,"<ErrorNum>","</ErrorNum>");
                            Application.showMessage("The ErrorNum is::"+ErrorNum);
                            v3= sL.validationPoint(ErrorNum, "0", input, c);
            	        }
		 	            
			 	            
			            break;
			            }
		             }
		    
		         
		         if(v1*v2*v3 ==1)
		         {
		         	Application.showMessage("WebService Call :: OutService is Success[All validation points are Success]");
                    LibraryRtp.AddLog("WebService Call :: OutService is Success[All validation points are Success]", "Y");
		         }
		         else
		         {
		        	 OutPut="Failed";
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::OutService is Failed with Validation Errors");
	                 LibraryRtp.AddLog("WebService Call ::OutService is Failed with Validation Errors", "Y");

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
	    
	    public void simulatorHAV(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
		{
			
			//PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
  	         String s=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: CcentralNumber");
  	         String Acc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");
             String ser=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE");
			Statement st = sL.dBConnect(input, c);
	        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs.next()) 
	        {
	         String beforeSim = rs.getString(2);
	        //c.setValue("BaseMsgid", basemsgID);
	        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
	        
	        }
	        st.close();
	        Statement st0 = sL.dBConnect(input, c);
	        
	       
	        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>1</useSimulator><useUControlSimulator>1</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><UpDownGradeForDotcom>0</UpDownGradeForDotcom><enableSMBFlow>1</enableSMBFlow><discDuration>0</discDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>"+Acc+"</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber>"+s+"</centralStationAccountNumber><uControlGroupValue>"+ser+"</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>0</deactivateAccount><siteId>123456</siteId><LineOfService>XHS</LineOfService><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>1</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><UCCuserName>Comcast</UCCuserName><UCCpassword>UCCtest1</UCCpassword><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><AOservicesProductIds>10097</AOservicesProductIds><peripheralProductsIds>40031</peripheralProductsIds><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isDotcomXhsSIKLogEnabled>1</isDotcomXhsSIKLogEnabled><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><autoReflowCount>1</autoReflowCount></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
	        st0.executeUpdate(sql);
	       
	        st0.close();
	        Thread.sleep(300000);
	        Statement st1 = sL.dBConnect(input, c);
	        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
	        while (rs1.next()) 
	        {
	         String afterSim = rs1.getString(2);
	        
	        Application.showMessage("Db Simulator After Updation ::"+afterSim);
	        
	        }
	        st0.close();
	     
	        
		}
	    
	    
	    
	    public void OrderUpdate_Validate(String CcentralNum,String INservice, String OrderType,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
			LibraryRtp  lC= new LibraryRtp ();
			 ResultSet  rs;
			 int v4=0,v1=0,v2=0,v3=0,v5=0,v6=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     String AccountNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");

		     Application.showMessage("--------------------------------------------------------");
		     Application.showMessage("***********Order Update Validation Function**************");       
		     Application.showMessage("--------------------------------------------------------");
		      LibraryRtp.AddLog("***********Order Update Validation Function**************", "Y");    
			 try
			 {
				 Statement st =sL. dBConnect(input, c);	
				 rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
				 while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
		         {
		        	
		        
		            String rowmsg = rs.getString(1);
		            /*if(rs.getBlob("receive_data")==null)
		            {*/
		            
		            	Application.showMessage("Your Recieve XML is NULL \n");
		            		
		            	String senddata = lC.extractXml(rs,xmldata2);  
		        	
		        
		 	                          
			          //  String senddata = sL.extractXml(rs,xmldata2);           
			          //  String recievedata = lC.extractXml(rs,xmldata1);      
			            Application.showMessage("senddata is ::"+senddata); 
			            //Application.showMessage("recievedata is ::"+recievedata); 
			            LibraryRtp.AddLog("senddata is ::"+senddata, "N");
			          //  LibraryRtp.AddLog("recievedata is ::"+recievedata, "N");
			           
		 	            
			            String billingOrderId= sL.nodeFromKey(senddata,"<value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
                        Application.showMessage("The billingOrderId is::"+billingOrderId);                        
                        v1= sL.validationPoint(billingOrderId, CcentralNum, input, c);
                        
                        String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"active\">");
                        Application.showMessage("The ordType is::"+ordType);                        
                        v2= sL.validationPoint(ordType, OrderType, input, c);
                        
                        String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"customerType\">");
                        Application.showMessage("The ordStatus is::"+billingOrderId);                        
                        v3= sL.validationPoint(ordStatus, "COM", input, c);

                        String service= sL.nodeFromKey(senddata," Service =","  Hav Market = I ").trim();
                        Application.showMessage("The service is::"+service);                        
                      //  v4= sL.validationPoint(service, INservice, input, c);
                        
                        String HavMarket= sL.nodeFromKey(senddata," Hav Market = "," Cms Market").trim();
                        Application.showMessage("The HavMarket is::"+HavMarket);                        
                        v5= sL.validationPoint(HavMarket, "I", input, c);
                        
                        String CmsMarket= sL.nodeFromKey(senddata," Cms Market = ","</note><description>").trim();
                        Application.showMessage("The CmsMarket is::"+CmsMarket);                        
                        v6= sL.validationPoint(CmsMarket, "U", input, c);
                     
		 	            
			 	            
			            break;
			            }
		             
		    
		         
		         if(v1*v2*v3*v5*v6 ==1)
		         {
		         	Application.showMessage("WebService Call :: SetSiteStatus_Validate is Success[All validation points are Success]");
                    LibraryRtp.AddLog("WebService Call :: SetSiteStatus_Validate is Success[All validation points are Success]", "Y");
		         }
		         else
		         {
		        	 OutPut="Failed";
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::SetSiteStatus_Validate is Failed with Validation Errors");
	                 LibraryRtp.AddLog("WebService Call ::SetSiteStatus_Validate is Failed with Validation Errors", "Y");

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
	    
	    
	    public void OrderUpdateNeg_Validate(String CcentralNum,String INservice, String OrderType,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     
			 ResultSet  rs;
			 int v4=0,v1=0,v2=0,v3=0,v5=0,v6=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     String AccountNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");

		     Application.showMessage("--------------------------------------------------------");
		     Application.showMessage("***********OrderUpdateNeg_Validate**************");       
		     Application.showMessage("--------------------------------------------------------");
		      LibraryRtp.AddLog("***********OrderUpdateNeg_Validate**************", "Y");    
			 try
			 {
				 Statement st =sL. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:IControlSimulatorLogMsg/SetSiteStatus'  and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
		         while (rs.next())
		         {
		        	
		        
		 	                          
			            String senddata = sL.extractXml(rs,xmldata2);           
			            String recievedata = sL.extractXml(rs,xmldata1);      
			            Application.showMessage("senddata is ::"+senddata); 
			            Application.showMessage("recievedata is ::"+recievedata); 
			            LibraryRtp.AddLog("senddata is ::"+senddata, "N");
			            LibraryRtp.AddLog("recievedata is ::"+recievedata, "N");
			           
		 	            
			            String billingOrderId= sL.nodeFromKey(senddata,"<value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
                        Application.showMessage("The billingOrderId is::"+billingOrderId);                        
                        v1= sL.validationPoint(billingOrderId, CcentralNum, input, c);
                        
                        String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"active\">");
                        Application.showMessage("The ordType is::"+ordType);                        
                        v2= sL.validationPoint(ordType, OrderType, input, c);
                        
                        String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"customerType\">");
                        Application.showMessage("The ordStatus is::"+billingOrderId);                        
                        v3= sL.validationPoint(ordStatus, "COM", input, c);

                        String service= sL.nodeFromKey(senddata,"PST Service = "," Hav Market = ");
                        Application.showMessage("The service is::"+service);                        
                        v4= sL.validationPoint(service, INservice, input, c);
                        
                        String HavMarket= sL.nodeFromKey(senddata," Hav Market = "," Cms Market");
                        Application.showMessage("The HavMarket is::"+HavMarket);                        
                        v5= sL.validationPoint(HavMarket, "I", input, c);
                        
                        String CmsMarket= sL.nodeFromKey(senddata,"Cms Market = ","</note><description>");
                        Application.showMessage("The CmsMarket is::"+HavMarket);                        
                        v6= sL.validationPoint(CmsMarket, "U", input, c);
                        
                        String errorCode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"errorText\">");
                        Application.showMessage("The errorCode is::"+errorCode);                        
                        v6= sL.validationPoint(errorCode, "UCC-RESP-CODE-1209", input, c);
                        
                        String errorText= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorSource\">");
                        Application.showMessage("The errorText is::"+errorText);                        
                        v6= sL.validationPoint(errorText, "Invalid Transmitter Code "+CcentralNum, input, c);
                        
                        String errorSource= sL.nodeFromKey(senddata,"<value name=\"errorSource\">","</value><value name=\"details\">");
                        Application.showMessage("The errorSource is::"+errorSource);                        
                        v6= sL.validationPoint(errorSource, "BHS.Fallout", input, c);
		 	            
			 	            
			            break;
			            }
		             
		    
		         
		         if(v1*v2*v3*v4*v5*v6 ==1)
		         {
		         	Application.showMessage("WebService Call :: SetSiteStatus_Validate is Success[All validation points are Success]");
                    LibraryRtp.AddLog("WebService Call :: SetSiteStatus_Validate is Success[All validation points are Success]", "Y");
		         }
		         else
		         {
		        	 OutPut="Failed";
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::SetSiteStatus_Validate is Failed with Validation Errors");
	                 LibraryRtp.AddLog("WebService Call ::SetSiteStatus_Validate is Failed with Validation Errors", "Y");

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
	    
	    public void Jasper_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException  
	    {
	           
	    	String AccountNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");

	           Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	           LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
	           LibraryRtp lr=new LibraryRtp();
	           ResultSet  rs;
	           String xmldata1 ="receive_data";
	              String xmldata2 ="SEND_DATA";
	              String Time= c.get("BaseTime").toString();
	              c.setValue("Jasper","true");
	              int callFound=0;
	              Application.showMessage("----------------------------------------------------------");
	              Application.showMessage("*****jasper Function******");    
	              Application.showMessage("----------------------------------------------------------");
	     // Application.showMessage("Value is"+c.getValue("ACC_input"));
	              lr.findThinktimeOperationRTP(input, c);
	              c.setValue("Jasper","false");
	            try
	             {
	          //   Statement st =lC. dBConnect(input, c);
	         //    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'jaspersrv:TerminalPortType/EditTerminal' and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
	            	rs=lr.reduceThinkTimeRTP(input, c);
	            
	             while (rs.next())
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
	                                          
	                        }
	                              else
	                              {
	                         
	                              String recievedata = lC.extractXml(rs,xmldata1);	                             
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
	                                
	                                if(ICCID.equals("8901650507770007899"))
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
	                                                 
	                                
	                               
	                                
	                                        String Jas_Version = lC.nodeFromKey(recievedata,"<ns2:version xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">","</ns2:version><ns2:build xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
	                                        Application.showMessage("SEND Xml Jas_Version is ::"+Jas_Version); 
	                                
	                                        String Jas_build = lC.nodeFromKey(recievedata,"<ns2:build xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">","</ns2:build><ns2:timestamp xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
	                                        Application.showMessage("SEND Xml Jas_build is ::"+Jas_build); 
	                                        
	                                        String Jas_iccid = lC.nodeFromKey(recievedata,"<ns2:iccid xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">","</ns2:iccid><ns2:effectiveDate xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
	                                        Application.showMessage("SEND Xml Jas_iccid is ::"+Jas_iccid); 
	                                        
	                                    }        
	                                                 
	                                 }
	                       
	                               }
	               
	                               Application.showMessage("********WebService Call::Jasper Valiadted ********");   
	                            //  st.close();

	                   }
	            }
	           catch (SQLException e)
	           {
	               Application.showMessage("Connection Failed! Check output console");
	                  e.printStackTrace();
	                  return;
	           }
	}
	    
	    
	    
	    
	    
	    public void GetTerminalDetails_Validate(String INiccid,String licenseKey,String INstatus,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     
			 ResultSet  rs;
			 int v1=0,v2=0,v3=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     String AccountNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");

		     Application.showMessage("--------------------------------------------------------");
		     Application.showMessage("***********GetTerminalDetails_Validate Function**************");       
		     Application.showMessage("--------------------------------------------------------");
		      LibraryRtp.AddLog("***********GetTerminalDetails_Validate Function**************", "Y");    
			 try
			 {
				 Statement st =sL. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:IControlSimulatorLogMsg/SetSiteStatus'  and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
		         while (rs.next())
		         {
		        	
		        
		            
		         
		            	
		            if(rs.getBlob("receive_data")==null)
		            {
		            
		            	Application.showMessage("Your Recieve XML is NULL \n");
		            	
		            	String senddata =sL.extractXml(rs,xmldata2);
		            	Application.showMessage("Your Recieve XML is::\n"+senddata);
		            }
		            else if(rs.getBlob("SEND_DATA")==null)
		            {
		            	Application.showMessage("Your SEND XML is NULL \n");	
		            	String recievedata=sL.extractXml(rs,xmldata1);
		            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		            }
		            else
		            { 
		                          
			            String senddata = sL.extractXml(rs,xmldata2);           
			            String recievedata = sL.extractXml(rs,xmldata1);      
			            Application.showMessage("senddata is ::"+senddata); 
			            Application.showMessage("recievedata is ::"+recievedata); 
			            LibraryRtp.AddLog("senddata is ::"+senddata, "N");
			            LibraryRtp.AddLog("recievedata is ::"+recievedata, "N");
			           
		 	            
			            String jaspersrv_iccid= sL.nodeFromKey(senddata,"<jaspersrv:iccid>","</jaspersrv:iccid>");
                        Application.showMessage("The jaspersrv_iccid is::"+jaspersrv_iccid);                        
                        v1= sL.validationPoint(jaspersrv_iccid,INiccid, input, c);
                        
                        String jaspersrv_licenseKey= sL.nodeFromKey(senddata,"<jaspersrv:licenseKey>","</jaspersrv:licenseKey>");
                        Application.showMessage("The jaspersrv_licenseKey is::"+jaspersrv_licenseKey);                       
                        v2= sL.validationPoint(jaspersrv_licenseKey,licenseKey, input, c);
                        
                        String status= sL.nodeFromKey(recievedata,"<ns2:status>","</ns2:status>");
                        Application.showMessage("The status is::"+status);
                        v3= sL.validationPoint(status, INstatus, input, c);

                      
		 	            
			 	            
			            break;
			            }
		             }
		    
		         
		         if(v1*v2*v3 ==1)
		         {
		         	Application.showMessage("WebService Call :: SetSiteStatus_Validate is Success[All validation points are Success]");
                    LibraryRtp.AddLog("WebService Call :: SetSiteStatus_Validate is Success[All validation points are Success]", "Y");
		         }
		         else
		         {
		        	 OutPut="Failed";
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::SetSiteStatus_Validate is Failed with Validation Errors");
	                 LibraryRtp.AddLog("WebService Call ::SetSiteStatus_Validate is Failed with Validation Errors", "Y");

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

	 
}
