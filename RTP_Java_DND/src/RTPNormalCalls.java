import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class RTPNormalCalls 
{
	public static String rowmsg= null;
                HandleInvalidServicesLibrary library=new HandleInvalidServicesLibrary ();
               // HandleInvlidServicesValidation validate=new HandleInvlidServicesValidation();
                SuspendClass sc=new SuspendClass();
                public void NormalgetAccout_ValidateSus(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, SAXException
                {
                                
                                Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
                     LibraryRtp  lC= new LibraryRtp ();
                                ResultSet  rs;
                                int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
                                String xmldata1 ="receive_data";
                     String xmldata2 ="SEND_DATA";
                     Map<String, String> ResultMap = new HashMap<String,String>();
                     String ServicesValue=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE");
                                List<String> GroupTier=library.extractServices(ServicesValue,input,c);
                     Application.showMessage("-------------------------------------------------");
                     Application.showMessage("*****GetAccount_normal_Validate Function******");       
                     Application.showMessage("-------------------------------------------------");
                     
                     if(ServicesValue.isEmpty() || ServicesValue.equals(null))
                     {
                                 Application.showMessage("Current Service is not parameterized in Excel");
                     }
                     else if(ServicesValue.contains("+"))
                     {
                                c.setValue("IsMultiExist","true");
                                library.extractServices(ServicesValue,input,c);
                     }
                     else
                     {
                                 c.setValue("IsMultiExist","false");
                                 c.setValue("ExistingGroup",ServicesValue);
                     }
                     
                     
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
                                      
//                                             String group1=lC.xpathValue(recievedata,"//ns3:GetAccountResponse/account/ns2:group");
//                                                                    Application.showMessage("group1 is::"+group1);
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
                                                
                                                 String recievedataT=library.RemoveNameSpaces(recievedata);
                                                Application.showMessage("receice data"+recievedataT);
                                                            String status_getAcc= lC.nodeFromKey(recievedata,"<ns2:status>","</ns2:status>");
                                                            Application.showMessage("GetAccount Status is::"+status_getAcc); 
                                                             //--------------------Group and services validation------------------------
                                if(c.getValue("IsMultiExist").equals("true"))
                                  {
                                                                           
                                                 if(GroupTier.size()==3)
                                                {
                                                                
                                                                //String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
                            //String Tier1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:group>");
                                                                           // String Tier2= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>");
                                                                            String group1=library.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                                                            String Tier1=library.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[2]");
                            String Tier2=library.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[3]");
                                                                             Application.showMessage("group1 is::"+group1);   
                             Boolean group=(group1.trim()).equals(c.getValue("ExistingGroup"));
                             Application.showMessage("group"+group);
                             Boolean FirstTier=(Tier1.trim()).equals(GroupTier.get(1).trim() ) || (Tier1.trim()).equals(GroupTier.get(2).trim());
                             Application.showMessage("group"+FirstTier);
                             Boolean SecondTier=(Tier2.trim()).equals(GroupTier.get(1).trim() ) || (Tier2.trim()).equals(GroupTier.get(2).trim());
                             Application.showMessage("group"+SecondTier);
                             if(group1 ==null || Tier1 ==null || Tier2==null)
                                                                                {
                                                                                Application.showMessage("Group1 and Tiers are NULL");
                                                                                            //continue;
                                                                                }
                                                                                else
                                                                                {
                                                                                      Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
                                                                                     // String check=c.getValue("ExistingGroup");
                                                                                      if((group==true) && (FirstTier==true) && (SecondTier==true))
                                                                                                   {
                                                                                                   Application.showMessage("*******Validation Point 4 :: Group********");
                                                                                                   Application.showMessage("Validation is Successfull with Group::"+group1);
                                                                                                   Application.showMessage("Validation is Successfull with Tier1::"+Tier1);
                                                                                                   Application.showMessage("Validation is Successfull with Tier2::"+Tier2);
                                                                                                   Application.showMessage("-----*****Validation is Successfull with Groups and Services*****--------");
                                                                                                   v3=1;
                                                                                                    }
                                                                                                else
                                                                                                   {
                                                                                                                v3=0;
                                                                                                 c.report("group1 at Recieve Xml not Validated as "+group1);
                                                                                                   }
                                                                                }
                                                                                 }           
                                                                
                                        else 
                                                             {
                                                
                                            String group1=library.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                            String Tier1=library.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[2]");
                     Application.showMessage("group1 is::"+group1);   
                     Boolean group=(group1.trim()).equals(c.getValue("ExistingGroup"));
                     Application.showMessage("group"+group);
                     Boolean FirstTier=(Tier1.trim()).equals(GroupTier.get(1).trim());
                     Application.showMessage("group"+FirstTier);
                     
                     if(group1 ==null || Tier1 ==null)
                                                {
                                                Application.showMessage("Group1 and Tiers are NULL");
                                                            //continue;
                                                }
                                                else
                                                {
                                                      Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
                                                     // String check=c.getValue("ExistingGroup");
                                                      if((group==true) && (FirstTier==true))
                                                                   {
                                                                   Application.showMessage("*******Validation Point 4 :: Group********");
                                                                   Application.showMessage("Validation is Successfull with Group::"+group1);
                                                                   Application.showMessage("Validation is Successfull with Tier1::"+Tier1);
                                                                   
                                                                   Application.showMessage("-----*****Validation is Successfull with Groups and Services*****--------");
                                                                   v3=1;
                                                                    }
                                                                else
                                                                   {
                                                                                v3=0;
                                                                 c.report("group1 at Recieve Xml not Validated as "+group1);
                                                                   }
                                                }
                                                                    
                         }
}
                                  
                                                                                      
                                                                 else
                                                 {
                                                                 // for only one group
                                                                   // String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group></account>").trim();
                                                                                String group1=library.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                                                                Application.showMessage("group1 is::"+group1); 
                                                                
                                                                                if(group1 ==null)
                                                                                {
                                                                                Application.showMessage("Group1 is NULL");
                                                                                            //continue;
                                                                                }
                                                                                else
                                                                                {
                                                                                                Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
                                                                                    if(group1.trim().equals(c.getValue("ExistingGroup")))
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
                                                                                                                  
                                                                               
                                                                             
                                                             
                                                              
                                                                
                                                
                                      
                 public void NormalSuspendValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException 
                    {
                                LibraryRtp lR= new LibraryRtp();
                                //lR.LoadValuestoGlobalList(input, c);
                                SuspendClass sC=new SuspendClass();
                                Thread.sleep(50000);
                        // sc.getAccout_ValidateSus(input, c);
                               NormalgetAccout_ValidateSus(input,c);
                                sC.processHomeSecurityInfo_Validate(input, c);
                                sC.suspend_COPS_validate(input, c);
                                sC.SuspendAccount_Validate(input, c);
                                sC.orderUpdate_Validate(input, c);
                    }
                public void NormalRestoreValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException 
                    {
                                LibraryRtp lR= new LibraryRtp();
                                //lR.LoadValuestoGlobalList(input, c);
                                RestoreClass rC=new RestoreClass();
                                Thread.sleep(50000);
                                NormalgetAccout_ValidateSus(input,c);
                                rC.processHomeSecurityInfo_Validate(input, c);
                                rC.Activate_COPS_validate(input, c);
                                rC.RestoreAccount_Validate(input, c);
                                rC.orderUpdate_Validate(input, c);
                    }
                public void NormalCancelValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException
                    {
                                LibraryRtp lR= new LibraryRtp();
                                CancelClass cC=new CancelClass();
                                //lR.LoadValuestoGlobalList(input, c);
                                Thread.sleep(50000);
                                NormalgetAccout_ValidateSus(input,c);
                                cC.processHomeSecurityInfo_Validate(input, c);
                                cC.deleteUnactivatedAccount_Validate(input, c);
                                cC.DisconnectAccount_CANCEL_Validate(input, c);
                                cC.orderUpdate_Validate(input, c);
                                
                    }
                    
                 public void demicallsservices(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException
                    {
                	 
                	
                	 if(c.getValue("IsDemi").equals(true))
                			 {
                                 Thread.sleep(40000);

                                 RTP_ValidationsClass vC=new RTP_ValidationsClass();
                                 LibraryRtp lR= new LibraryRtp();
                                // vC.execution(input, c);
                                vC.rtpTestInterface_Validate(input, c);
                        //.sleep(25000);
                                 vC.getCustomerPermitRequirements_Validate(input,c);
                                 vC.createCMSAccountID_Validate(input, c);
                                 vC.queryLocation_Validate(input,c);
                         Thread.sleep(30000);
                         vC.getAccount_Validate(input, c);
                         vC.processHomeSecurityInfo_Validate(input,c);
                         vC.SetAccountBasicInformation_Validate(input,c);
                         library.createAccount_Validate(input, c);
                         vC.responderInfo_Validate(input,c);
                         vC.SetAccountAuthorityInformation_Validate(input, c);
                         vC.modifyHomeSecurity_Validate(input, c);
                         vC.orderUpdate_Validate(input,c);
                         //lR.SaveValuesfromInstall(input, c);
                			 }
                	 else
                	 {
                	 Thread.sleep(40000);
         	    	RTP_ValidationsClass vC=new RTP_ValidationsClass();
         		     LibraryRtp lR= new LibraryRtp();
         		     //vC.execution(input, c);
         		     vC.rtpTestInterface_Validate(input, c);     
         		     queryLocation_Validate(input,c);
         	         //Thread.sleep(30000);
         		     getAccount_Validate(input, c);
         		     vC.processHomeSecurityInfo_Validate(input,c);       
         		    library.createAccount_Validate(input, c);               
         		     vC.orderUpdate_Validate(input,c); 
                    }
                    }
                 
                 //---------------cos validation--------------
                 public void NormalcosValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException, SQLException
                 {
                               //  HandleInvlidServicesValidation HL=  new HandleInvlidServicesValidation();
                           //  HL.NormalCosValidationsservices(input, c);
                             
                 }
                 public void queryLocation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
                 {
                                 Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
                      LibraryRtp  lC= new LibraryRtp ();
                                 ResultSet  rs;
                                 int callFound=0,v1=0;
                                 String xmldata1 ="receive_data";
                      String xmldata2 ="SEND_DATA";
                     
                      Application.showMessage("-------------------------------------------------");
                      Application.showMessage("*****Query Location _Validate Function******");       
                      Application.showMessage("-------------------------------------------------");
                          
                                 try
                                 {
                                                 Statement st =lC. dBConnect(input, c); 
                          rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                          while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
                          {
                                 
                         
                             rowmsg=rs.getString(1);
                             if(rs.getBlob("receive_data")==null)
                             {
                             
                                 Application.showMessage("Your Recieve XML is NULL \n");
                                 
                                 String senddata =lC.extractXml(rs,xmldata2);
                                 Application.showMessage("Your Send XML is::\n"+senddata);
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
                                                            
                                             String legacyID= lC.nodeFromKey(senddata,"<lt:legacyID>","</lt:legacyID>");                    
                                             Application.showMessage("legacyID is ::"+legacyID); 
                                             if(legacyID.equals(c.getValue("sHOUSE_KEY")))
                                 {
                                                 Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                                 Application.showMessage("SEND XML is :: \n"+senddata);
                                                 //System.out.printf("SEND XML is %s \n",senddata);
                                                 Application.showMessage("*******Validation Point 1 :: WebServicall-createCMSAccountID_Validate********");
                                                 Application.showMessage("Validation is Successfull with Input Account Number"+legacyID);
                                                 callFound=1;
                                 }
                                 else
                                 {
                                                 continue;
                                 }
                                 
                                             if(callFound==1)
                                             {
                                       
                                                             String locationID= lC.nodeFromKey(recievedata,"<typ:locationID xmlns:typ=\"http://xml.comcast.com/location/types\">","</typ:locationID>");
                                                            Application.showMessage("locationID is ::"+locationID);
                                                            c.setValue("LocationID", locationID);
                                                            
                                                             
                                                             String locationStatus= lC.nodeFromKey(recievedata,"<typ:locationStatus xmlns:typ=\"http://xml.comcast.com/location/types\">","</typ:locationStatus>");
                                                            Application.showMessage("locationStatus is :: "+locationStatus); 
                                                             
                                                            if(locationStatus==null)
                                                              {
                                                                             c.report("Send Xml Account Number is NULL");
                                                                             continue;
                                                             }
                                                             else
                                                             {
                                                                  Application.showMessage("locationStatus from Send Xml  from queryLocation is ::"+" "+locationStatus);
                                                                  if(locationStatus.equalsIgnoreCase("Complete"))
                                                                              {
                                                                                  Application.showMessage("*******Validation Point 2 :: WebServicall-locationStatus********");
                                                                                  Application.showMessage("Validation is Successfull with AccountNumber::"+" "+locationStatus);
                                                                                  v1=1;
                                                                              }
                                                                              else
                                                                              {
                                                                                  c.report("locationStatus at Send Xml not Validated as "+locationStatus);
                                                                              }
                                                             }                  

                                                            String houseNumber= lC.nodeFromKey(recievedata,"<typ:houseNumber>","</typ:houseNumber>");
                                                            Application.showMessage("houseNumber is :: "+houseNumber); 
                                                             c.setValue("HouseNumber", houseNumber);
                                                            
                                                             String QpostalCode= lC.nodeFromKey(recievedata,"<typ:zip5>","</typ:zip5>");
                                                            Application.showMessage("houseNumber is :: "+houseNumber); 
                                                             c.put("QpostalCode", QpostalCode);
                                                            
                                                             String streetName= lC.nodeFromKey(recievedata,"<typ:streetName>","</typ:streetName>").trim();
                                                            Application.showMessage("streetName is :: "+streetName); 
                                                             c.setValue("StreetName", streetName);
                                                            
                                                             String streetSuffix= lC.nodeFromKey(recievedata,"<typ:streetSuffix>","</typ:streetSuffix>").trim();
                                                            Application.showMessage("streetSuffix is :: "+streetSuffix); 
                                                              
                                                             String city= lC.nodeFromKey(recievedata,"<typ:city>","</typ:city>").trim();
                                                            Application.showMessage("city is :: "+city); 
                                                             c.setValue("City", city);
                                                            c.put("city",city);
                                                           
                                                             String policeNumber= lC.nodeFromKey(recievedata,"<typ:policeNumber>","</typ:policeNumber>");
                                                            Application.showMessage("policeNumber is :: "+policeNumber); 
                                                             c.setValue("PoliceNumber", policeNumber);
                                                           
                                                             String Loc_fireNumber= lC.nodeFromKey(recievedata,"<typ:fireNumber>","</typ:fireNumber>");
                                                            Application.showMessage("fireNumber is :: "+Loc_fireNumber); 
                                                             c.setValue("FireNumber", Loc_fireNumber);
                                                           
                                                             String Loc_medicalNumber= lC.nodeFromKey(recievedata,"<typ:medicalNumber>","</typ:medicalNumber>");
                                                            Application.showMessage("medicalNumber is :: "+Loc_medicalNumber); 
                                                             c.setValue("medicalNumber", Loc_medicalNumber);
                                             break;
                                             }
                              }
                          }
                          if(v1*callFound==1)
                          {
                                 Application.showMessage("WebService Call :: Query Location is Success[All validation points are Success]");
                          }
                          else
                          {
                                  c.put("result", "false");
                                  c.report("WebService Call :: Query Location is Failed with Validation Errors");
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


                 //done till above


                 public void getAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
                 {
                                 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
                      LibraryRtp  lC= new LibraryRtp ();
                                 ResultSet  rs;
                                 int callFound=0,v1=0;
                                 String xmldata1 ="receive_data";
                      String xmldata2 ="SEND_DATA";
                     
                      Application.showMessage("-------------------------------------------------");
                      Application.showMessage("*****getAccount_Validate _Validate Function******");       
                      Application.showMessage("-------------------------------------------------");
                          
                                 try
                                 {
                                                 Statement st =lC. dBConnect(input, c); 
                          rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                          while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
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
                                       
                                                 
                                                              String errorCode_getAcc= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
                                                             Application.showMessage("errorCode is ::"+errorCode_getAcc);
                                                             
                                                              if(errorCode_getAcc==null)
                                                              {
                                                                             c.report("Recieve Xml Account Number is NULL");
                                                                             continue;
                                                              }
                                                              else
                                                              {
                                                                  Application.showMessage("locationStatus from Send Xml  from queryLocation is ::"+" "+errorCode_getAcc);
                                                                  if(errorCode_getAcc.equalsIgnoreCase("UCE-15101"))
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
                                                            
                                                              String errorMessage_getAcc= lC.nodeFromKey(recievedata,"<errorMessage xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorMessage>");
                                                             Application.showMessage("errorMessage is::"+errorMessage_getAcc); 

                                                            
                                             break;
                                             }
                              }
                          }
                          if(v1*callFound==1)
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

     
}
