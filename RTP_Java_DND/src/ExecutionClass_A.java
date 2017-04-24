import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;



/*
* This Class only for the Execution flow and just for exposing the main flows
* This Class file is developed and maintained by Ashwini A.L 
 * Comcast ntid:: alaksh001c
* 
 */
public class ExecutionClass_A 
{
                
                
                public void demicallsgeodetic(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, ParseException, SQLException, XPathExpressionException, NullPointerException, SAXException
    {
           Thread.sleep(24000);
           RTP_ValidationsClass vC=new RTP_ValidationsClass();
           FunctionLibrary_A fA=new FunctionLibrary_A();
           foxtrot FT=new foxtrot();
           LibraryRtp lR= new LibraryRtp();
           vC.rtpTestInterface_Validate(input, c);
        //   vC.getCustomerPermitRequirements_Validate(input,c);
           vC.createCMSAccountID_Validate(input, c);
          // vC.queryLocation_Validate(input,c);
           vC.getAccount_Validate(input, c);
                                //  vC.createAccount_Validate(input, c);
                                   fA.responderInfofailed_Validate(input,c);
                                   fA.geodeticQuery_Validate(input,c);
                                //  vC.orderUpdate_Validate(input,c);
                                   FT.orderUpdateFOX_Validate(input, c);
                       /* nR.orderUpdate_Validate(input, c);*/
                        // FT.CommonDBValidate(input, c);
  
     }
                //-------------------
                public void demicallsgeodetic_normal(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, ParseException, SQLException, XPathExpressionException, NullPointerException, SAXException
                {
                       Thread.sleep(24000);
                       RTP_ValidationsClass vC=new RTP_ValidationsClass();
                       FunctionLibrary_A fA=new FunctionLibrary_A();
                       foxtrot FT=new foxtrot();
                       LibraryRtp lR= new LibraryRtp();
                       String val="true";
                       int noofCallsValidated=5;
                       int i;
                       OUTER1:
                       for( i=0;i<=noofCallsValidated;i++)
                       {
                    	   Application.showMessage("The val of I is"+i);
                    	   switch(i)
                    	   {
                    	   case 0:
                    		  val= vC.rtpTestInterface_Validate(input, c);              		  
                    		   break;
                    	   case 1:
                    		   val=  vC.createCMSAccountID_Validate(input, c);  
                    		   break;
                    	   case 2:
                    		   val=  vC.getAccount_Validate(input, c); 
                    		   break;
                    	   case 3:
                    		   val= fA.responderInfofailed_Validate(input,c);
                    		   break;
                    	   case 4:
                    		   val=  fA.geodeticQuery_Validate(input,c);
                    		   break;
                    	   case 5:
                    		   val=   FT.orderUpdateFOX_Validate(input, c);
                    		   break;
                    	  
                    		   default:
                    			   break;
                    	   }
                    	   if(val.equalsIgnoreCase("false"))
                    	   {
                    		   c.report("Validation calls could found on time as per dynamic think time so its quiting entire flow validation");
                    		   break OUTER1;
                    	   }
                    	   else
                    	   {
                    		   continue OUTER1;
                    	   }
                       }
                       
                       

                  
                
                
                                        
                                         
                                                                 
                                               
                                              
                                            
                                              
                                   
                                  
              
                 }
                
                //----------------------
                
                public void FOxtrort(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
                {
                       foxtrot FT=new foxtrot();
                        RTP_ValidationsClass rV=new RTP_ValidationsClass();
                        Thread.sleep(10000);
                         rV.rtpTestInterface_Validate(input, c);
                        //rV.getAccount_Validate(input, c);
                         rV.queryLocation_Validate(input, c);
                         FT.orderUpdateFOX_Validate(input, c);
                       /* nR.orderUpdate_Validate(input, c);*/
                       //  FT.CommonDBValidate(input, c);
                }


               
                

	public void FOX1(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
	{
		String FC= c.getValue("RTPNormalScenariosdS","RTP_InputParams: FoxtrotChange");
	      if(FC.equals("Yes"))
	      {      
		foxtrot FT=new foxtrot();
	          RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
	          LibraryRtp lR=new LibraryRtp();
	          lR.baseMsgid_retrieval(input, c);
	          //FT.simulatorFOX1(input, c);
	          dBConnectSimulator_CLS_1(input, c);
	      }
	      else
	      {
	    	  Application.showMessage("No need to change foxtrot");
	    	  RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
		        LibraryRtp lR=new LibraryRtp(); 
			       
		         lR.baseMsgid_retrieval(input, c);
	      }
	         
	}
	public void FOX1_RTP(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
	{
		String FC= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: FoxtrotChange");
	      if(FC.equals("Yes"))
	      {      
		foxtrot FT=new foxtrot();
	          RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
	          LibraryRtp lR=new LibraryRtp();
	          lR.baseMsgid_retrieval(input, c);
	          FT.simulatorFOX1(input, c);
	      }
	      else
	      {
	    	  Application.showMessage("No need to change foxtrot");
	    	  RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
		        LibraryRtp lR=new LibraryRtp(); 
			       
		         lR.baseMsgid_retrieval(input, c);
	      }
	         
	}
	public void FOX0_RTP(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
	{
		 String FC= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: FoxtrotChange");
	      if(FC.equals("Yes"))
	      {
		foxtrot FT=new foxtrot();
	        RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
	        LibraryRtp lR=new LibraryRtp(); 
		       
	         lR.baseMsgid_retrieval(input, c);
	         FT.simulatorFOX0(input, c);
	      }
	      else
	      {
	    	  Application.showMessage("No need to change foxtrot");
	    	  RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
		        LibraryRtp lR=new LibraryRtp(); 
			       
		         lR.baseMsgid_retrieval(input, c);
	      }
	}
	      public void FOX0(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
	  	{
	  		 String FC= c.getValue("RTPNormalScenariosdS","RTP_InputParams: FoxtrotChange");
	  	      if(FC.equals("Yes"))
	  	      {
	  		foxtrot FT=new foxtrot();
	  	        RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
	  	        LibraryRtp lR=new LibraryRtp(); 
	  		       
	  	         lR.baseMsgid_retrieval(input, c);
	  	       dBConnectSimulator_CLS_2(input, c);
	  	         //FT.simulatorFOX0(input, c);
	  	      }
	  	      else
	  	      {
	  	    	  Application.showMessage("No need to change foxtrot");
	  	    	  RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
	  		        LibraryRtp lR=new LibraryRtp(); 
	  			       
	  		         lR.baseMsgid_retrieval(input, c);
	  	      }
	         
	}



                public void FOxtrortCOS(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
                {
                       
                                                foxtrot FT=new foxtrot();
                        RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
                        LibraryRtp lR=new LibraryRtp();
                       
                                rV.rtpTestInterface_Validate(input, c);
                                rV.queryLocation_Validate(input, c);
                                FT.orderUpdateFOX_Validate(input, c);
                                FT.CommonDBValidate(input, c);
                       
                }

  /*
   *   DST Execution....
   * 
   * 
   */
                
                
                public void DSTdemi(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, ParseException, XPathExpressionException, NullPointerException, SAXException
                {
                       RTP_ValidationsClass_UpDown vC=new RTP_ValidationsClass_UpDown();
                       LibraryRtp_UpDown lR= new LibraryRtp_UpDown();
                       RTP_ValidationsClass rV=new RTP_ValidationsClass();
                       FunctionLibrary_A fA=new FunctionLibrary_A();
                       
                       
                       Thread.sleep(4000);
                        rV.rtpTestInterface_Validate(input, c);
                        rV.getCustomerPermitRequirements_Validate(input,c);
                        rV.getAccount_Validate(input, c);
                        rV.createCMSAccountID_Validate(input, c);
                    
                        rV.processHomeSecurityInfo_Validate(input,c);
                        rV.SetAccountBasicInformation_Validate(input,c);
                        fA.queryLocation_ValidateDST(input,c);
                        fA.createAccount_ValidateDST(input, c);
                        rV.responderInfo_Validate(input,c);
                        rV.SetAccountAuthorityInformation_Validate(input, c);
                        rV.modifyHomeSecurity_Validate(input, c);
                        rV.orderUpdate_Validate(input,c);
                     
                }
/*
* 
 *  US332454 update status of XH LOB to A once OP recives completed install order.. Execution Class
*/
                
                public void CompletedInstallInsight(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, ParseException, XPathExpressionException, NullPointerException, SAXException
                { 
                         
                     RTP_ValidationsClass vC=new RTP_ValidationsClass();
                     FunctionLibrary_A fA=new FunctionLibrary_A();
                     //LibraryRtp lR= new LibraryRtp();
                     vC.rtpTestInterface_Validate(input, c);     
                     vC.queryLocation_Validate(input,c);
                     vC.getAccount_Validate(input, c);
                     fA.processHomeSecurityInfo_Active_Validate(input,c);       
                     vC.createAccount_Validate(input, c);                
                     vC.orderUpdate_Validate(input,c);
                     
                }
                
                /*
                * 
                 * No WorkListing
                * 
                 */
                public void NoWorklisting(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
                {
                                   
                       RTP_ValidationsClass rV=new RTP_ValidationsClass(); 
                       FunctionLibrary_A fA=new FunctionLibrary_A();
                       rV.rtpTestInterface_Validate(input, c);
                       fA.getDatasetUsingUserData2(input, c);
                         
                }
                
                /*
                * 
                 *             Numerix and Jasper 
                 * 
                 */
                public void cosintitialization(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
                   {  
                                   LibraryRtp_UpDown lR=new LibraryRtp_UpDown();
                                   lR.simulatorChange(input, c);
                                   lR.baseMsgid_retrieval(input, c);
                                  
                   }
                
                public void CosJasperORnumerix(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException

                {
                                   LibraryRtp_UpDown lR=new LibraryRtp_UpDown();
                       FunctionLibrary_A fA=new FunctionLibrary_A(); 
           ChangeOfServiceClass cS=new ChangeOfServiceClass();
           RTP_SimulatorClass rS=new RTP_SimulatorClass();
           RTP_ValidationsClass_UpDown VU= new RTP_ValidationsClass_UpDown();
          // fA.processHomeSecurityInfoA_Validate(input, c);
         //  cS.getAccoutCos_Validate(input, c);
        // cS.cosLogicFlow(input, c);
           Thread.sleep(40000);
           fA.orderUpdateCos_Validate(input, c);
                     

                       Application.showMessage("ICCID Value is::"+c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId"));

                       if (c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId").equalsIgnoreCase("8901650507770007899"))
                       {
                              
                        fA.Jasper_Validate(input, c);
                       }
                    
                        else if (c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId").equalsIgnoreCase("8901640100735449527"))
                        {
                             fA.Numerix_Validate(input, c);
                        }
                       else
                       {
                              Application.showMessage("iccid is not equal to any of the above "+c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId"));
                       }
                       
                    //   lR.simulatorChange(input, c);
                                                                
                                        }
                public void NumerixSuspendUnit(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException

                {
                       LibraryRtp_UpDown lR=new LibraryRtp_UpDown();
                       FunctionLibrary_A fA=new FunctionLibrary_A(); 
                       ChangeOfServiceClass cS=new ChangeOfServiceClass();
                       RTP_SimulatorClass rS=new RTP_SimulatorClass();
                       RTP_ValidationsClass_UpDown VU= new RTP_ValidationsClass_UpDown();
                       Thread.sleep(40000);
                       fA.Numerix_SuspendUnit(input, c);
                       VU.OrderUpdate_tocheckAEENegativeScenarios(input, c);
                                                                
                }
                
                public void NumerixRestoreUnit(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException

                {
                       LibraryRtp_UpDown lR=new LibraryRtp_UpDown();
                       FunctionLibrary_A fA=new FunctionLibrary_A(); 
                       ChangeOfServiceClass cS=new ChangeOfServiceClass();
                       RTP_SimulatorClass rS=new RTP_SimulatorClass();
                       RTP_ValidationsClass_UpDown VU= new RTP_ValidationsClass_UpDown();
                       Thread.sleep(40000);
                       fA.Numerix_ResumeUnit(input, c);
                       VU.OrderUpdate_tocheckAEENegativeScenarios(input, c);
                    
                                                                
                }
                public void Cancel(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
                   {  
                                   
                                 
                                   
                                   CancelClass cC=new CancelClass();
                                   cC.getAccout_Validate(input, c);
                                   cC.deleteUnactivatedAccount_Validate(input, c);
                                   cC.processHomeSecurityInfo_Validate(input, c);
                                   cC.orderUpdate_Validate(input, c);
                                
                                  
                   }

                public void dBConnectSimulator_CLS_1(Object input, ScriptingContext c)
            			throws ClassNotFoundException, SQLException, IOException,
            			InterruptedException {
            		
            		LibraryRtp lib = new LibraryRtp();
            		
            		Connection conn = lib.dBConnect_CLS_1(input, c);
            		String name = "homesecurityUtil:hsConfig";
            		PreparedStatement pstmt = conn
            				.prepareStatement(" update CWFTEMPLATECONFIG set DATA=? where NAME='"
            						+ name + "'");
            		Statement st = conn.createStatement();
            		pstmt.setString(
            				1,
            				"<hsConfig><name>homesecurityUtil:hsConfig</name><useUControlSimulator>0</useUControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><accountId>8720100010077795</accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>150|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|READY_FOR_ACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><suspended>0</suspended><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>1</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><homeStreamHeaderAcceptName>Accept</homeStreamHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><homeStreamAcceptValue>application/json</homeStreamAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><homeStreamConsumerKeyName>oauthConsumerKey</homeStreamConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><homeStreamHeaderConsumerKeyValue>MgkJ3Gby7Xdsxe8hBGCyCwib</homeStreamHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><homeStreamHeaderConsumerSecretName>oauthConsumerSecret</homeStreamHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><homeStreamHeaderConsumerSecretValue>uD15pbTAVDqsauHJdzRJ9MtBQ0davhDJ</homeStreamHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><homeStreamHeaderPartnerIdName>X-PartnerId</homeStreamHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><homeStreamHeaderPartnerIdValue>SSD_XHS_ORDER_PROVISIONING</homeStreamHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><homeStreamHeaderContentTypeName>Content-Type</homeStreamHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><homeStreamHeaderContentTypeValue>application/json</homeStreamHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><homeStreamRequestResourcePathForSuspendAccount>/suspended</homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount>/restored</homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup>/group/</homeStreamRequestResourcePathForAddAccountGroup><homeStreamRequestResourcePathForRemoveAccountGroup>/group/</homeStreamRequestResourcePathForRemoveAccountGroup><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount><homeStreamRequestResourcePathForUpdateAccountTierGroup>/tiergroup/</homeStreamRequestResourcePathForUpdateAccountTierGroup><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xfinityGroupSecure>XfinityEvents</xfinityGroupSecure><xfinityGroupControl>XfinityEvents-Insight</xfinityGroupControl><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><homeStreamURL>https://homesecurity-acct-qa03.codebig2.net/rest</homeStreamURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue><BENROrderStatusDisconnectedValue>Disconnected</BENROrderStatusDisconnectedValue><DDSActionREMOVEValue>REMOVE</DDSActionREMOVEValue><xhsHomeStreamOperation>deleteUnactivatedAccount,createAccount,suspendAccount,restoreAccount,deactivateAccount,removeAccount,getAccount,updateAccountTierGroup,addAccountGroup,removeAccountGroup</xhsHomeStreamOperation><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount></hsConfig>");
            		;
            		// pstmt.executeQuery();

            		pstmt.executeUpdate();
            		conn.setAutoCommit(true);

            		ResultSet rs = st
            				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
            		while (rs.next()) {
            			String beforeSim = rs.getString(1);

            			Application.showMessage("Db Simulator Before Updation ::"
            					+ beforeSim);

            		}
            		st.close();

            		// <vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType>

            		Thread.sleep(300000);
            		Statement st1 = lib.dBConnect(input, c);
            		ResultSet rs1 = st1
            				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
            		while (rs1.next()) {

            			String afterSim = rs1.getString(2);
            			Application
            					.showMessage("Db Simulator After Updation ::" + afterSim);

            		}

            	} 
                public void dBConnectSimulator_CLS_2(Object input, ScriptingContext c)
            			throws ClassNotFoundException, SQLException, IOException,
            			InterruptedException {
            		
            		LibraryRtp lib = new LibraryRtp();
            		
            		Connection conn = lib.dBConnect_CLS_1(input, c);
            		String name = "homesecurityUtil:hsConfig";
            		PreparedStatement pstmt = conn
            				.prepareStatement(" update CWFTEMPLATECONFIG set DATA=? where NAME='"
            						+ name + "'");
            		Statement st = conn.createStatement();
            		pstmt.setString(
            				1,
            				"<hsConfig><name>homesecurityUtil:hsConfig</name><useUControlSimulator>0</useUControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><accountId>8720100010077795</accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>150|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|READY_FOR_ACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><suspended>0</suspended><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>1</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>0</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><homeStreamHeaderAcceptName>Accept</homeStreamHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><homeStreamAcceptValue>application/json</homeStreamAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><homeStreamConsumerKeyName>oauthConsumerKey</homeStreamConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><homeStreamHeaderConsumerKeyValue>MgkJ3Gby7Xdsxe8hBGCyCwib</homeStreamHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><homeStreamHeaderConsumerSecretName>oauthConsumerSecret</homeStreamHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><homeStreamHeaderConsumerSecretValue>uD15pbTAVDqsauHJdzRJ9MtBQ0davhDJ</homeStreamHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><homeStreamHeaderPartnerIdName>X-PartnerId</homeStreamHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><homeStreamHeaderPartnerIdValue>SSD_XHS_ORDER_PROVISIONING</homeStreamHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><homeStreamHeaderContentTypeName>Content-Type</homeStreamHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><homeStreamHeaderContentTypeValue>application/json</homeStreamHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><homeStreamRequestResourcePathForSuspendAccount>/suspended</homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount>/restored</homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup>/group/</homeStreamRequestResourcePathForAddAccountGroup><homeStreamRequestResourcePathForRemoveAccountGroup>/group/</homeStreamRequestResourcePathForRemoveAccountGroup><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount><homeStreamRequestResourcePathForUpdateAccountTierGroup>/tiergroup/</homeStreamRequestResourcePathForUpdateAccountTierGroup><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xfinityGroupSecure>XfinityEvents</xfinityGroupSecure><xfinityGroupControl>XfinityEvents-Insight</xfinityGroupControl><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><homeStreamURL>https://homesecurity-acct-qa03.codebig2.net/rest</homeStreamURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue><BENROrderStatusDisconnectedValue>Disconnected</BENROrderStatusDisconnectedValue><DDSActionREMOVEValue>REMOVE</DDSActionREMOVEValue><xhsHomeStreamOperation>deleteUnactivatedAccount,createAccount,suspendAccount,restoreAccount,deactivateAccount,removeAccount,getAccount,updateAccountTierGroup,addAccountGroup,removeAccountGroup</xhsHomeStreamOperation><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount></hsConfig>");
            		;
            		// pstmt.executeQuery();

            		pstmt.executeUpdate();
            		conn.setAutoCommit(true);

            		ResultSet rs = st
            				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
            		while (rs.next()) {
            			String beforeSim = rs.getString(1);

            			Application.showMessage("Db Simulator Before Updation ::"
            					+ beforeSim);

            		}
            		st.close();

            		// <vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType>

            		Thread.sleep(300000);
            		Statement st1 = lib.dBConnect(input, c);
            		ResultSet rs1 = st1
            				.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
            		while (rs1.next()) {

            			String afterSim = rs1.getString(2);
            			Application
            					.showMessage("Db Simulator After Updation ::" + afterSim);

            		}

            	} 
}

