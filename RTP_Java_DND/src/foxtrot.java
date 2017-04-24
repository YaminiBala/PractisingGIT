import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class foxtrot {

                public int OutPut=1;
                
                
                LibraryRtp  lC= new LibraryRtp ();
                
                
                
                public String orderUpdateFOX_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
                {
                                Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
                     LibraryRtp  lC= new LibraryRtp ();
                                ResultSet  rs;
                                int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0;
                                // String xmldata1 ="receive_data";
                     String xmldata2 ="SEND_DATA";

String rscallpresent="true";
                     Application.showMessage("-------------------------------------------------------------");
                     Application.showMessage("***********OrderUpdate_Validate Function**************");       
                     Application.showMessage("-------------------------------------------------------------");
                     HashMap Operation=lC.findingoperations(input, c);
            	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
            	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));

                                try
                                {
                                              //  Statement st =lC. dBConnect(input, c); 
                      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
                                	rs=lC.reduceThinkTimeRTP(input, c);
                                	if(rs == null)
                                	{
                                		rscallpresent = "false";
                                		
                                	
                                		
                                	}
                                	else
{
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

                                                           
                                                            
                                                            //if(c.getValue("IsDemi").equalsIgnoreCase("true"))
                                                           
                                                                
                                                            String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
                                                                       Application.showMessage("ordSource is ::"+ordSource); 
                                                                        
                                                           /* String billingOrderId= lC.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
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
                                                                          }*/
                                                                          
                                                                          /*  String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
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
                                                                            }  */
                                                           
                                                            
                                                           /* else
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
                                                                               
                                                                                 Application.showMessage("Service from Input   is ::"+" "+c.getValue("sSERVICEorCURRENTSERVICE"));
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
                                                           }*/
                                                           String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
                                                           Application.showMessage("inputChannel is ::"+inputChannel);
                                                           
                                                           /* String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
                                                           Application.showMessage("ordType is ::"+ordType);
                                                           
                                                           if(ordType==null)
                                                            {
                                                                            c.report("Send Xml ordType is NULL");
                                                                            continue;
                                                            }
                                                            else
                                                            {
                                                                 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
                                                                 if(ordType.equals("NEW"))
                                                                             {
                                                                                 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                                                                 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
                                                                                 v2=1;
                                                                             }
                                                                             else
                                                                             {
                                                                                 c.report("lt_fireNumber at Send Xml not Validated as "+ordType);
                                                                             }
                                                            }*/              
                                                           
                                                            
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
                                                           
                                                           //_______________________________________________________________________//
                                                          //
                                                          //                                                                 WorkList Validations
                                                          //
                                                          //_______________________________________________________________________//
                                                          
                                                           
                                                           
                                                           //---------------------------Error code validation----------------------------------------//
                                                          
                                                            
                                                           
                                                            String errorCode= lC.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"errorText\">");
                                                           Application.showMessage("errorCode is ::"+errorCode); 
                                                            
                                                            //For common DB validation
                                                              c.put("ErrorCodeOu", errorCode);
                                                             
                                                               if(errorCode.equalsIgnoreCase("HS-RTP-DEMI-02"))
                                                              {
                                                                 Application.showMessage("Error code Validated as HS-RTP-DEMI-02");
                                                                 Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
                                                                                                         
                                                               }
                                                                                                
                                                                                                /*if((boolean) (ErrorCodeOu="HS-RTP-DEMI-02"))
                                                              {
                                                                                                                Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
                                                                                          
                                                               }*/
                                                          
                                                         // Error text from excel
                                                             // c.put("ErrorCode_Excel",c.getValue("RTPNormalScenariosdS","ErrorCode"));
                                                           
                                                                          /* if(errorCode==null)
                                                                            {
                                                                                            c.report("Send Xml Error Code is NULL");
                                                                                            continue;
                                                                            }
                                                                            else
                                                                            {
                                                                                
                                                                                 if(errorCode.equals(c.get("ErrorCode")))
                                                                                             {
                                                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-ErrorCode********");
                                                                                                 Application.showMessage("Validation is Successfull with Error Code::"+" "+errorCode);
                                                                                                 v5=1;
                                                                                             }
                                                                                             else
                                                                                             {
                                                                                                 c.report("Error Code at Send Xml not Validated as "+errorCode);
                                                                                             }
                                                                            }
                                                                          
                                                                           */
                                                                          
                                                                           
                                                                           //-------------------------Error text validation-----------------------------------------//
                                                                          
                                                                           
                                                                          String errorText= lC.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorSource\">");
                                                                           Application.showMessage("Error Text is ::"+errorText); 
                                                                           //For Common DB validation
                                                                           c.put("ErrorTextOu", errorText);
                                                                           
                                                                            
                                                                            //read from Excel ----
                                                                          // c.put("ErrorText_Excel",c.getValue("RTPNormalScenariosdS","ErrorText"));
                                                                                          /*if(errorText==null)
                                                                                            {
                                                                                                            c.report("Send Xml Error Code is NULL");
                                                                                                            continue;
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                
                                                                                                 if(errorText.equals(c.get("ErrorText")))
                                                                                                             {
                                                                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-Error Text********");
                                                                                                                 Application.showMessage("Validation is Successfull with Error Text::"+" "+errorText);
                                                                                                                 v6=1;
                                                                                                                 Application.showMessage("I am here");
                                                                                                             }
                                                                                                             else
                                                                                                             {
                                                                                                                 c.report("Error Text at Send Xml not Validated as "+errorText);
                                                                                                             }
                                                                                            }
                                                                                          
                                                                                           
                                                                                           */
                                                                                       //-------------------------Error Source  validation-----------------------------------------//
                                                                                          
                                                                                           
                                                                                          String errorSource= lC.nodeFromKey(senddata,"<value name=\"errorSource\">","</value><value name=\"details\">");
                                                                                           Application.showMessage("Error Source is ::"+errorSource); 
                                                                                            
                                                                                         //For Common DB validation
                                                                                           c.put("ErrorSourceOu", errorSource);
                                                                                                                                                                           
                                                                                            
                                                                                            
                                                                                            //read from Excel ----
                                                                                          // c.put("ErrorSource_Excel",c.getValue("RTPNormalScenariosdS","ErrorSource"));
                                                                                           
                                                                                                          /* if(errorSource==null)
                                                                                                            {
                                                                                                                            c.report("Send Xml Error Source is NULL");
                                                                                                                            continue;
                                                                                                            }
                                                                                                            else
                                                                                                            {
                                                                                                                
                                                                                                                if(errorSource.equals(c.get("ErrorSource")))
                                                                                                                             {
                                                                                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-Error Source********");
                                                                                                                                 Application.showMessage("Validation is Successfull with Error Source::"+" "+errorSource);
                                                                                                                                 v7=1;
                                                                                                                                 Application.showMessage("I am here");
                                                                                                                             }
                                                                                                                             else
                                                                                                                             {
                                                                                                                                 c.report("Error Source at Send Xml not Validated as "+errorSource);
                                                                                                                             }
                                                                                                            }
                                                                                                          */
                                                                                                          
                                                                                                           
                                                                                                         /* String details= lC.nodeFromKey(senddata,"  <value name=\"details\">","</value>");
                                                                                                         Application.showMessage("Detail  is ::"+details);*/
                                                                                                                  
                                                                                                           
                                                            
                                                                                           
                                            break;
                                            }
                            }
                               
                         }}
                                }
                        /* if(v1*callFound*v5 ==1)
                         {
                                Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
                         //   c.setValue("IsDemi", "false");
                         }
                         else
                         {
                                // c.setValue("IsDemi", "false");
                                // c.put("result", "false");
                                 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
                         }
                         st.close();
                         
                        //Lp.reportingToExcel(Object , ScriptingContext);
                                }              */
                                catch (SQLException e)
                                {
                                    System.out.println("Connection Failed! Check output console");
                                                e.printStackTrace();
                                               
                                }
								return rscallpresent;}
                public void CommonDBValidate(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, SQLException
                {
                                LibraryRtp  lC= new LibraryRtp ();
                                ResultSet rs1;
                                  
                                
                                try
                                {
                                                
                                                Statement st1 =lC.dBConnectCommon(input, c);
                                                
                                                
                         //Application.showMessage("Account Number is "+c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber"));
                         String Acc=c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber");
                                                rs1 = st1.executeQuery("select * from(select * from cwpworklist where ACCOUNTNUMBER ='"+Acc+"'order by creation_date desc)where rownum < 2");
                                                if(rs1==null)
                                                {
                                                                Application.showMessage("Null Record set found in WorKlist DB for AccountNumber'"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber"));
                                                }
                      
                         while(rs1.next())
                   {
                         String errorCode= rs1.getString("ERRORCODE");
             String errorSource=rs1.getString("ERRORSOURCE");
             Application.showMessage("Error Source from common is" +errorSource);
             String errorText=rs1.getString("ERRORTEXT");
             String PARTICIPANTOPERATION=rs1.getString("PARTICIPANTOPERATION");
             String BILLINGSYSTEM=rs1.getString("BILLINGSYSTEM");
             String SERVICETYPE=rs1.getString("SERVICETYPE");
             String ORDER_ID=rs1.getString("ORDER_ID");
                         Application.showMessage("Error Code is ::"+errorCode);
                         Application.showMessage("errorSource is ::"+errorSource);
                         Application.showMessage("PARTICIPANTOPERATION is ::"+PARTICIPANTOPERATION);
                         Application.showMessage("errorText is ::"+errorText);
                         Application.showMessage("BILLINGSYSTEM is ::"+BILLINGSYSTEM);
                         Application.showMessage("SERVICETYPE is ::"+SERVICETYPE);
                         Application.showMessage("ORDER_ID is ::"+ORDER_ID);
                         
                         Application.showMessage("****** Validating Common Data Base worklisting********");
                         if(errorText.equals(c.get("ErrorTextOu")))
                         {
                                 Application.showMessage("Error text from order update is validated successfully with error text from common DB");
                         }
                         else
                         {
                                 Application.showMessage("ERROR TEXT IS NOT VALIDATED");
                         }
                         
                         if(errorCode.equals(c.get("ErrorCodeOu")))
                         {
                                 Application.showMessage("Error code from order update is validated successfully with error code from common DB"); 
                         }
                         else
                         {
                                 Application.showMessage("ERROR CODE IS NOT VALIDATED");
                         }
                         
                         if(errorSource.equals(c.get("ErrorSourceOu")))
                         {
                                 Application.showMessage("Error SOURCE from order update is validated successfully with error Source from common DB"); 
                         }
                         else
                         {
                                 Application.showMessage("ERROR SOURCE IS NOT VALIDATED");
                         }
                         
                         
                   }
                         
                         
                         
                         st1.close();
                                }
                                catch (SQLException e)
                                {
                                    System.out.println("Connection Failed! Check output console");
                                                e.printStackTrace();
                                                return;
                                }
                                                

}
                
                public void simulatorFOXfromParamerList(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
                {
                                
                                //PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
                                LibraryRtp  lC= new LibraryRtp ();
                                Statement st =lC.dBConnect(input, c);
        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs.next()) 
        {
         String beforeSim = rs.getString(2);
        //c.setValue("BaseMsgid", basemsgID);
        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
        
        }
        st.close();
        Statement st0 = lC.dBConnect(input, c);
        
       
        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101017052874</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber></centralStationAccountNumber><uControlGroupValue></uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status></status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>"+c.getValue("RTPNormalScenariosdS", "RTP_InputParams: Foxtrot")+"</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
     
       
        
        st0.executeUpdate(sql);
       
      // ps.setString(1, sql);
       
       //ps.execute();
      
        st0.close();
        Thread.sleep(300000);
        Statement st1 = lC.dBConnect(input, c);
        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs1.next()) 
        {
         String afterSim = rs1.getString(2);
        
        Application.showMessage("Db Simulator After Updation ::"+afterSim);
        
        }
        st0.close();
     
        
                }
                
                public void simulatorFOX1(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
                {
                                
                                //PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
                                LibraryRtp  lC= new LibraryRtp ();
                                Statement st =lC.dBConnect(input, c);
        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs.next()) 
        {
         String beforeSim = rs.getString(2);
        //c.setValue("BaseMsgid", basemsgID);
        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
        
        }
        st.close();
        Statement st0 = lC.dBConnect(input, c);
        
       
        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101017052874</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber></centralStationAccountNumber><uControlGroupValue></uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status></status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
     
       
        
        st0.executeUpdate(sql);
       
      // ps.setString(1, sql);
       
       //ps.execute();
      
        st0.close();
        Thread.sleep(300000);
        Statement st1 = lC.dBConnect(input, c);
        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs1.next()) 
        {
         String afterSim = rs1.getString(2);
        
        Application.showMessage("Db Simulator After Updation ::"+afterSim);
        
        }
        st0.close();
     
        
                }
                
                public void simulatorFOX0(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
                {
                                
                                //PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
                                
                                Statement st = lC.dBConnect(input, c);
        ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs.next()) 
        {
         String beforeSim = rs.getString(2);
        //c.setValue("BaseMsgid", basemsgID);
        Application.showMessage("Db Simulator Before Updation ::"+beforeSim);
        
        }
        st.close();
        Statement st0 = lC.dBConnect(input, c);
        
       
        String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><accountId>8720101017052874</accountId><iccId>8901650507770007899</iccId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><centralStationAccountNumber></centralStationAccountNumber><uControlGroupValue></uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status></status><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><countryHardCodeToUS>1</countryHardCodeToUS><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>0</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend></hsConfig>' where NAME='homesecurityUtil:hsConfig'";
     
       
        
        st0.executeUpdate(sql);
       
      // ps.setString(1, sql);
       
       //ps.execute();
      
        st0.close();
        Thread.sleep(300000);
        Statement st1 = lC.dBConnect(input, c);
        ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
        while (rs1.next()) 
        {
         String afterSim = rs1.getString(2);
        
        Application.showMessage("Db Simulator After Updation ::"+afterSim);
        
        }
        st0.close();
     
        
                }
                
                
                
                }




