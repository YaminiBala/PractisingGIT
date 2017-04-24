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


public class ChangeOfServiceValidation 
{
	HandleInvalidServicesLibrary library=new HandleInvalidServicesLibrary();
	RTP_ValidationsClass sv=new RTP_ValidationsClass();
	 public void updateTier_Validate(String getService,Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
		{
			
			Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
			 ResultSet  rs;
			 int callFound=0,v1=0,v2=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		    
		     Application.showMessage("-------------------------------------------------");
		     Application.showMessage("*****updateTier_Validate Function******");       
		     Application.showMessage("-------------------------------------------------");
		     
		    
		  
		     
			try
			{
				 Statement st =lC. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/updateAccountTierGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
			            if(id.equals(c.getValue("ACC_input")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-updateTier_Validate********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            	
			            if(callFound==1)
			            {
			      
				            String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
				            Application.showMessage("errorCode is ::"+errorCode);
				           
				            
				           
				            if(errorCode==null)
				            {
					            c.report("Send Xml errorCode is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Account Number from Send Xml  from UpdateTierGroup is ::"+" "+errorCode);
				            	 if(errorCode.equals("0"))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
					            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
					             }
				            }		

				            String tierGroup= lC.nodeFromKey(senddata,"<tierGroup>","</tierGroup>");
				            Application.showMessage("tierGroup is::"+tierGroup); 
				             if(tierGroup==null)
				             {
					            c.report("Send tierGroup is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("tierGroup from Send Xml  from UpdateTierGroup is ::"+" "+tierGroup);
				            	 if(tierGroup.equals(getService))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-UpdateTierGroup********");
					            	 Application.showMessage("Validation is Successfull with tierGroup::"+" "+tierGroup);
					            	 v2=1;
					             }
					             else
					             {
					            	 c.report("tierGroup at Send Xml not Validated as "+tierGroup);
					             }
				            }		
			               
			            break;
			            }
			            
			                        
		             }
		         }
		         if(v1*v2==1)
		         {
		         	Application.showMessage("WebService Call :: UpdateTierGroup is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 c.report("WebService Call :: updateTier_Validate is Failed with Validation Errors");
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
		
		
		public void AddGroup_Validate(String getService,Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
		{
			
			Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
			 ResultSet  rs;
			 int callFound=0,v1=0,v2=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     Application.showMessage("-------------------------------------------------");
		     Application.showMessage("*****AddGroup_Validate Function******");       
		     Application.showMessage("-------------------------------------------------");
		  
		     
			try
			{
				 Statement st =lC. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/addAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
			            if(id.equals(c.getValue("ACC_input")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-AddGroup_Validate********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            	
			            if(callFound==1)
			            {
			      
			            	    String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
					            Application.showMessage("errorCode is ::"+errorCode);
					           
					            
					           
					            if(errorCode==null)
					            {
						            c.report("Send Xml errorCode is NULL");
						            continue;
					            }
					            else
					            {
					            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+errorCode);
					            	 if(errorCode.equals("0"))
						             {
						            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
						            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
						            	 v1=1;
						             }
						             else
						             {
						            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
						             }
					            }		

					            String group= lC.nodeFromKey(senddata,"<group>","</group>");
					            Application.showMessage("tierGroup is::"+group); 
					             if(group==null)
					             {
						            c.report("Send group is NULL");
						            continue;
					            }
					            else
					            {
					            	 Application.showMessage("group from Send Xml  from UpdateTierGroup is ::"+" "+group);
					            	 if(group.equals(getService))
						             {
						            	 Application.showMessage("*******Validation Point 2 :: WebServicall-AddGroup********");
						            	 Application.showMessage("Validation is Successfull with group::"+" "+group);
						            	 v2=1;
						             }
						             else
						             {
						            	 c.report("NewService at Send Xml not Validated as "+group);
						             }
					            }	
			            break;
			            }
			            
			                        
		             }
		         }
		         if(v1*v2==1)
		         {
		         	Application.showMessage("WebService Call :: AddGroup is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 c.report("WebService Call :: AddGroup_Validate is Failed with Validation Errors");
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
		
		
		public void RemoveGroup_Validate(String getService,Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
		{
			
			Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
			 ResultSet  rs;
			 int callFound=0,v1=0,v2=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		
		     Application.showMessage("-------------------------------------------------");
		     Application.showMessage("*****RemoveGroup_Validate Function******");       
		     Application.showMessage("-------------------------------------------------");
		     
		     
		  
		     
			try
			{
				 Statement st =lC. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/removeAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
			            if(id.equals(c.getValue("ACC_input")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-RemoveGroup_Validate********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            	
			            if(callFound==1)
			            {
			      
			            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
				            Application.showMessage("errorCode is ::"+errorCode);
				           
				            
				           
				            if(errorCode==null)
				            {
					            c.report("Send Xml errorCode is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+errorCode);
				            	 if(errorCode.equals("0"))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
					            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
					             }
				            }		

				            String group= lC.nodeFromKey(senddata,"<group>","</group>");
				            Application.showMessage("tierGroup is::"+group); 
				             if(group==null)
				             {
					            c.report("Send group is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("group from Send Xml  from UpdateTierGroup is ::"+" "+group);
				            	 if(group.equals(getService))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-AddGroup********");
					            	 Application.showMessage("Validation is Successfull with group::"+" "+group);
					            	 v2=1;
					             }
					             else
					             {
					            	 c.report("NewService at Send Xml not Validated as "+group);
					             }
				            }	
			               
			            break;
			            }
			            
			                        
		             }
		         }
		         if(v1*v2==1)
		         {
		         	Application.showMessage("WebService Call :: RemoveGroup_Validate is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 c.report("WebService Call :: RemoveGroup_Validate is Failed with Validation Errors");
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
		
		 public void NormalCosValidationsservices(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, SAXException
		 {
	            
	            LibraryRtp lR= new LibraryRtp();
	            //lR.LoadValuestoGlobalList(input, c);
	            //ChangeOfServiceClass CS = new ChangeOfServiceClass();
	            HandleInvalidServices hI=new HandleInvalidServices();
	            
	            Thread.sleep(40000);
	            //CS.getAccoutCos_Validate(input, c);
	            
	          //  nc.NormalgetAccout_ValidateSus(input, c);
	            sv.queryLocation_Validate(input,c);
	            Application.showMessage("***Change Of service Validation calls*****");
	            //List<String> addAccountgroup=lb.addAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolServiceSusRes"), input, c);
	            List<String> addAccountgroup=library.addAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"), input, c);
	            List<String> removeAccountgroup=library.removeAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"), input, c);
	            if(c.getValue("IsUpdateTier").equals("true"))
	                                            {
	                            Application.showMessage("***Update Tier Group Validation calls*****");
	                            updateTier_Validate(c.getValue("updatedvalue"),input,c);
	                                            }
	            else
	            {
	                            Application.showMessage("OP should not trigger update tier call");
	            }
	            if(c.getValue("IsAddaccountgroup").equals("true"))
	                                            {
	                            Application.showMessage("***addAccountgroup Validation calls*****");
	                            for(int i=0;i<addAccountgroup.size();i++)
	                            {
	                                            String addServices=addAccountgroup.get(i);
	                                            AddGroup_Validate(addServices,input, c);
	                            }
	                                            }
	            else
	            {
	                            Application.showMessage("OP should not triggere add account group call");
	            }
	            if(c.getValue("IsRemoveaccountgroup").equals("true"))
	                                            {
	                            Application.showMessage("***removeAccountgroup Validation calls*****");
	                            for(int i=0;i<removeAccountgroup.size();i++)
	                            {
	                                            String addServices=removeAccountgroup.get(i);
	                                            RemoveGroup_Validate(addServices,input,c);
	                                                }
	                                            }
	            else
	            {
	                            Application.showMessage("OP should not triggere Remove account group call");
	            }
	            //CS.cosLogicFlow(input, c);        
	            //CS.cosLogicFlow(input, c);        
	            //CS.orderUpdateCos_Validate(input, c);
	            library.orderUpdate_ValidateCOS(input, c);
	            }
		 
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
                               
//                                      String group1=lC.xpathValue(recievedata,"//ns3:GetAccountResponse/account/ns2:group");
//                                                             Application.showMessage("group1 is::"+group1);
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
		 
		 public void check(Object input,ScriptingContext c)
         {
        	 Application.showMessage("Yamini");
         }
        
}
