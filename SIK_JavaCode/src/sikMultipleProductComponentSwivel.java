import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class sikMultipleProductComponentSwivel
{
	int rate=0;
    int ratecodegroup=0;
    Boolean isErr=false;
	sikCSGENImsgClass sC=new sikCSGENImsgClass();
	
	public void csgEniWebServiceCalls(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
	  {
	         
	         String Isnegative=c.getValue("SwivelCSG", "sik_SwivelCSG: IsNegative");
	         if(Isnegative.equals("1"))
	         {
	                Thread.sleep(4000);
	                sC.csgENImsg_Validate(input, c);
	                sC.orderUpdateMissingInfo_Validate(input, c);
	                c.setValue("CancelReq", "NO");
	                
	         }
	         else if(Isnegative.equals("2"))
	         {
	                Thread.sleep(4000);
	                sC.csgENImsg_Validate(input, c);
	                c.setValue("CancelReq", "NO");
	         }
	         
	         else
	         {
	         Thread.sleep(4000);
	         sC.csgENImsg_Validate(input, c);
	         sC.QueryLocation_Validate(input, c);
	       //  Thread.sleep(7000);        
	         submitOrder_Validate(input, c);
	         if(isErr.equals(true))
	         {
	        	orderUpdate_Validate(input, c);
	         }
	         else
	         {
	         sC.orderUpdate_Validate(input, c);
	         }
	         }
	  }
	
	public void submitOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
    {
           Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
         sikLibraryClass sL = new sikLibraryClass();
           ResultSet  rs;
           int callFound=0,v1=0,v2=0,v3=0,v4=0;
           String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
        
         Application.showMessage("-----------------------------------------------------");
         Application.showMessage("**********submitOrder_Validate Function************");       
         Application.showMessage("----------------------------------------------------");
         HashMap Operation=sL.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("SubmitOrder"));
         	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SubmitOrder"));
             
           try
           {
               //   Statement st =sL. dBConnect(input, c);  
           //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
        	   rs=sL.reduceThinkTimeSIK(input, c);
             while (rs.next())
             {
                  
            
                   String rowmsg;
                        rowmsg = rs.getString(1);
                              
                      if(rowmsg.equals(c.getValue("BaseMsgid")))break;
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
                       Application.showMessage("Recieve XML is::  \n"+ recievedata);
                  Application.showMessage("SEND XML is :: \n"+senddata);
                                    
                       String sik_AccountNumber= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
                  
                  //String sik_AccountNumber = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:AccountNumber");
                  
                       Application.showMessage("accountNumber is ::"+sik_AccountNumber); 
                       
                       if(sik_AccountNumber==null)
                      {
                       continue;     
                      }
                      else if(sik_AccountNumber.equals(c.getValue("accountNumber")))
                  {
                         Application.showMessage("Recieve XML is::  \n"+ recievedata);
                         Application.showMessage("SEND XML is :: \n"+senddata);
                         //System.out.printf("SEND XML is %s \n",senddata);
                         Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
                         Application.showMessage("Validation is Successfull with Input Account Number"+sik_AccountNumber);
                         callFound=1;
                         v1=1;
                  }
                  else
                  {
                         continue;
                  }
                  
                       if(callFound==1)
                       {
                              String cct_code= sL.nodeFromKey(recievedata,"<cct:message><cct:code>","</cct:code><cct:text>");
                              String cct_text= sL.nodeFromKey(recievedata,"</cct:code><cct:text>","</cct:text></cct:message>");
                              Application.showMessage("MOdify Text is :: "+cct_text);
                              if(cct_code.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                              {
                                    String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
                                          Application.showMessage("OrderID is::"+OrderID_sik); 
                                          if(OrderID_sik.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                          {
                                         	 c.setValue("CancelReq", "NO");
                                          }
                                          else
                                          {
                                           c.put("pOrderID_sik", OrderID_sik);
                                           c.setValue("OrderIDToCancel",OrderID_sik );
                                           c.setValue("CancelReq", "YES");
                                          }
                              }
                              else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-213"))
                              {
                                   c.put("MFlag","1");
                                   c.setValue("CancelReq", "NO");
                                   
                                     String orderid_modify= sL.nodeFromKey(recievedata,"</code><text>Duplicate Order - Single Not Exact Match: Duplicate order detected; matched order #","</text></message></messages");
                            Application.showMessage("Modify Order ID found is :: "+orderid_modify);
                            c.put("MOrderID", orderid_modify);
                              }
                              else if(cct_code.equalsIgnoreCase("Order-SubmitOrder-211"))
  			 	            {
                            	  isErr=true; 
  			 	         		//c.put("isErr", "true");
  			 	         		Application.showMessage("CSGSWIVEL accept can order only of 4 same product codes");
  			 	         		//c.report("Dotcom accept can order only of 3 same product codes");
  			 	            }
  			 	      	 	  
                              else
                              {
                             	  c.setValue("CancelReq", "NO");
                                   Application.showMessage(" Failure for Submit..!");
                                   break;
                              }

                            
                           // String sik_Email= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");
                                  String sik_Email= sL.nodeFromKey(senddata,"<sik:Email>","</sik:Email>");
                                  
                                  //String sik_Email = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");

                                    Application.showMessage("sik_Email is ::"+c.getValue("Email")); 
                                     if(sik_Email==null)
                                    {
                                           c.report(" sik_Email is NULL");
                                           continue;
                                    }
                                    else
                                    {
                                       Application.showMessage("sik_Email from Send Xml  from Submit Order is ::"+" "+sik_Email);
                                       if(sik_Email.trim().equalsIgnoreCase(c.getValue("Email").trim()))
                                            {
                                              Application.showMessage("*******Validation Point 4 :: WebServicall-Email********");
                                              Application.showMessage("Validation is Successfull with Email::"+" "+sik_Email);
                                              v3=1;
                                            }
                                      
                                            else 
                                            {
                                             Application.showMessage("Email at Send Xml not Validated as "+sik_Email);
                                             continue;
                                            }
                                    }
                                    
                              String sik_FirstName= sL.nodeFromKey(senddata,"<sik:FirstName>","</sik:FirstName>");
                             
                             //String sik_FirstName = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:FirstName");
                            
                              Application.showMessage("firstName is ::"+sik_FirstName);
                             
                              if(sik_FirstName==null)
                              {
                                    c.report("Send Xml FirstName is NULL");
                                    continue;
                              }
                              else
                              {
                                 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+sik_FirstName);
                                 if(sik_FirstName.equals(c.getValue("Fname")))
                                     {
                                       Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
                                       Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_FirstName);
                                       v2=1;
                                     }
                                     else
                                     {
                                       c.report("FirstName at Send Xml not Validated as "+sik_FirstName);
                                     }
                              }        

                             String sik_LastName= sL.nodeFromKey(senddata,"<sik:LastName>","</sik:LastName>");
                            //String sik_LastName = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:LastName");
                             Application.showMessage("lastName is ::"+sik_LastName); 
                              
                             if(sik_LastName==null)
                              {
                                    c.report("Send Xml LastName is NULL");
                                    continue;
                              }
                              else
                              {
                                 if(sik_LastName.equals(c.getValue("Lname")))
                                     {
                                       Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
                                       Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_LastName);
                                       v4=1;
                                     }
                                     else
                                     {
                                       c.report("LastName at Send Xml not Validated as "+sik_LastName);
                                     }
                              }
                             
                           //-------------------------------------------------------------------------------------------------------//
     		 	            //
     		 	            //       					   Multiple product Code Section here.
     		 	            //
     		 	            //-------------------------------------------------------------------------------------------------------//
     		 	           
     		 	            
     		 	            boolean i=true;
     		 	             
     		 	            if(i)
     		 	            {
     		 	            	String rate1null=c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode1").trim();
     			 	            Application.showMessage("Ratecode 1::"+rate1null);
     			 	           if( rate1null.isEmpty() || rate1null.equals(null) || rate1null.equals(""))
     			 	           {
     			 	        	   Application.showMessage("Ratecode 1 is NULL..!");
     			 	           }
     			 	           else
     			 	           {
     		 	            	String ratecode1= c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode1").trim();
     		 	            
     		 	            	if(senddata.contains(ratecode1))
     		 	                {
     		 	            	Application.showMessage("Validation is Sucess as ratecode ::"+ratecode1+" exists..!");
     		 	                }
     			 	               else
     			 	                {
     			 	                Application.showMessage("Doesnot Contains ratecode1..!!"+ ratecode1);
     			 	            	c.report("Doesnot Contains ratecode1..!!"+ ratecode1);
     			 	            rate++;
     			 	                }
     				 	           }
     			 	          // -------------
     			 	          String rate2null=c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode2").trim();
     			 	            Application.showMessage("Ratecode 2::"+rate2null);
     			 	           if( rate2null.isEmpty() || rate2null.equals(null) || rate2null.equals(""))
     			 	           {
     			 	        	   Application.showMessage("Ratecode 2 is NULL..!");
     			 	           }
     			 	           else
     			 	           {
     		 	            	String ratecode2= c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode2").trim();
     		 	            	if(senddata.contains(ratecode2))
     		 	                {
     		 	            	Application.showMessage("Validation is Sucess as ratecode ::"+ratecode2+" exists..!");
     		 	                }
     			 	               else
     			 	                {
     			 	                Application.showMessage("Doesnot Contains ratecode2..!!"+ ratecode2);
     			 	            	c.report("Doesnot Contains ratecode2..!!"+ ratecode2);
     			 	            rate++;
     			 	                }
     				 	           }
     			 	           //----------------------------
     			 	          String rate3null=c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode3").trim();
     			 	            Application.showMessage("Ratecode 3::"+rate3null);
     			 	           if( rate3null.isEmpty() || rate3null.equals(null) || rate3null.equals(""))
     			 	           {
     			 	        	   Application.showMessage("Ratecode 3 is NULL..!");
     			 	           }
     			 	           else
     			 	           {
     		 	            	String ratecode3= c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode3").trim();
     		 	            	if(senddata.contains(ratecode3))
     		 	                {
     		 	            	Application.showMessage("Validation is Sucess as ratecode ::"+ratecode3+" exists..!");
     		 	                }
     			 	               else
     			 	                {
     			 	                Application.showMessage("Doesnot Contains ratecode3..!!"+ ratecode3);
     			 	            	c.report("Doesnot Contains ratecode3..!!"+ ratecode3);
     			 	            rate++;
     			 	                }
     				 	           }
     			 	           //-----------------------------------------
     			 	          String rate4null=c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode4").trim();
     			 	            Application.showMessage("Ratecode 4::"+rate1null);
     			 	           if( rate4null.isEmpty() || rate4null.equals(null) || rate4null.equals(""))
     			 	           {
     			 	        	   Application.showMessage("Ratecode 4 is NULL..!");
     			 	           }
     			 	           else
     			 	           {
     		 	            	String ratecode4= c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode4").trim();
     		 	            	if(senddata.contains(ratecode4))
     		 	                {
     		 	            	Application.showMessage("Validation is Sucess as ratecode ::"+ratecode4+" exists..!");
     		 	                }
     			 	               else
     			 	                {
     			 	                Application.showMessage("Doesnot Contains ratecode4..!!"+ ratecode4);
     			 	            	c.report("Doesnot Contains ratecode4..!!"+ ratecode4);
     			 	            rate++;
     			 	                }
     				 	           }
     			 	           
     			 	           //-----------------------------------
     			 	          String rate5null=c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode5").trim();
     			 	            Application.showMessage("Ratecode 5::"+rate1null);
     			 	           if( rate5null.isEmpty() || rate5null.equals(null) || rate5null.equals(""))
     			 	           {
     			 	        	   Application.showMessage("Ratecode 5 is NULL..!");
     			 	           }
     			 	           else
     			 	           {
     		 	            	String ratecode5= c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode5").trim();
     		 	            	if(senddata.contains(ratecode5))
     		 	                {
     		 	            	Application.showMessage("Validation is Sucess as ratecode ::"+ratecode5+" exists..!");
     		 	                }
     			 	               else
     			 	                {
     			 	                Application.showMessage("Doesnot Contains ratecode5..!!"+ ratecode5);
     			 	            	c.report("Doesnot Contains ratecode5..!!"+ ratecode5);
     			 	            rate++;
     			 	                }
     				 	           }
     			 	           //----------------------------------------
     			 	          String rate6null=c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode6").trim();
     			 	            Application.showMessage("Ratecode 6::"+rate6null);
     			 	           if( rate6null.isEmpty() || rate6null.equals(null) || rate6null.equals(""))
     			 	           {
     			 	        	   Application.showMessage("Ratecode 6 is NULL..!");
     			 	           }
     			 	           else
     			 	           {
     		 	            	String ratecode6= c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode6").trim();
     		 	            	if(senddata.contains(ratecode6))
     		 	                {
     		 	            	Application.showMessage("Validation is Sucess as ratecode ::"+ratecode6+" exists..!");
     		 	                }
     			 	               else
     			 	                {
     			 	                Application.showMessage("Doesnot Contains ratecode6..!!"+ ratecode6);
     			 	            	c.report("Doesnot Contains ratecode6..!!"+ ratecode6);
     			 	            rate++;
     			 	                }
     				 	           }
     			 	           //-------------------------------------------------
     			 	          String rate7null=c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode7").trim();
     			 	            Application.showMessage("Ratecode 7::"+rate1null);
     			 	           if( rate7null.isEmpty() || rate7null.equals(null) || rate7null.equals(""))
     			 	           {
     			 	        	   Application.showMessage("Ratecode 7 is NULL..!");
     			 	           }
     			 	           else
     			 	           {
     		 	            	String ratecode7= c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode7").trim();
     		 	            	if(senddata.contains(ratecode7))
     		 	                {
     		 	            	Application.showMessage("Validation is Sucess as ratecode ::"+ratecode7+" exists..!");
     		 	                }
     			 	               else
     			 	                {
     			 	                Application.showMessage("Doesnot Contains ratecode7..!!"+ ratecode7);
     			 	            	c.report("Doesnot Contains ratecode7..!!"+ ratecode7);
     			 	            rate++;
     			 	                }
     				 	           
     			 	           }
     			 	           //-------------
     			 	         String rate8null=c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode8").trim();
  			 	            Application.showMessage("Ratecode 8::"+rate8null);
  			 	           if( rate8null.isEmpty() || rate8null.equals(null) || rate8null.equals(""))
  			 	           {
  			 	        	   Application.showMessage("Ratecode 8 is NULL..!");
  			 	           }
  			 	           else
  			 	           {
  		 	            	String ratecode8= c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode8").trim();
  		 	            	if(senddata.contains(ratecode8))
  		 	                {
  		 	            	Application.showMessage("Validation is Sucess as ratecode ::"+ratecode8+" exists..!");
  		 	                }
  			 	               else
  			 	                {
  			 	                Application.showMessage("Doesnot Contains ratecode8..!!"+ ratecode8);
  			 	            	c.report("Doesnot Contains ratecode8..!!"+ ratecode8);
  			 	            rate++;
  			 	                }
  				 	           
  			 	           }
  			 	           //------------
  			 	        String rate9null=c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode9").trim();
			 	            Application.showMessage("Ratecode 9::"+rate9null);
			 	           if( rate9null.isEmpty() || rate9null.equals(null) || rate9null.equals(""))
			 	           {
			 	        	   Application.showMessage("Ratecode 9 is NULL..!");
			 	           }
			 	           else
			 	           {
		 	            	String ratecode9= c.getValue("SwivelCSG", "sik_SwivelCSG: KTCode9").trim();
		 	            	if(senddata.contains(ratecode9))
		 	                {
		 	            	Application.showMessage("Validation is Sucess as ratecode ::"+ratecode9+" exists..!");
		 	                }
			 	               else
			 	                {
			 	                Application.showMessage("Doesnot Contains ratecode9..!!"+ ratecode9);
			 	            	c.report("Doesnot Contains ratecode9..!!"+ ratecode9);
			 	            rate++;
			 	                }
				 	           
			 	           }
     		 	            }
     		 	           else
     		 	            {
     		 	            Application.showMessage("not worked");
     		 	            }
     		 	         if(ratecodegroup==rate)
     		 	         {
     		 	        	 Application.showMessage("Multiple available Rate code for Swivel CSG success");
     		 	        	ratecodegroup=1;
     		 	        	// Application.showMessage("product="+product);
     		 	         }
     		 	         else
     		 	         {
     		 	        	 c.report("Multiple available Rate code-It is not picking all the Rate codes from the request");
     		 	         }
     		 	                   

                           //--------------------------------------------------------------------------- 
                              
                              String sik_ShipmentPriority= sL.nodeFromKey(senddata,"<sik:ShipmentPriority>","</sik:ShipmentPriority>");
                             Application.showMessage("ShipmentPriority is ::"+sik_ShipmentPriority); 
                              
                              //String sik_FTA= sL.nodeFromKey(senddata,"<sik:FTA>","</sik:FTA>");
                             //Application.showMessage("sik_FTA is ::"+sik_FTA);
                             
                              
                              
                              
                              Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));

                             //Application.showMessage("FTA from Get Loc" +c.get("GL_ftaAgent"));
                             //Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
                             

                           
                              String sik_Address1= sL.nodeFromKey(senddata,"<sik:Address1>","</sik:Address1>");
                             Application.showMessage("sik_Address1 is ::"+sik_Address1); 
                              
                             // String sik_Address2= sL.nodeFromKey(senddata,"<sik:Address2>","</sik:Address2>");
                            //Application.showMessage("sik_Address2 is ::"+sik_Address2); 
                              
                              String sik_City= sL.nodeFromKey(senddata,"<sik:City>","</sik:City>");
                             Application.showMessage("sik_City is ::"+sik_City); 
                              
                              String sik_State= sL.nodeFromKey(senddata,"<sik:State>","</sik:State>");
                             Application.showMessage("sik_State is ::"+sik_State); 
                              
                              String sik_Zip= sL.nodeFromKey(senddata,"<sik:Zip>","</sik:Zip>");
                             Application.showMessage("sik_Zip is ::"+sik_Zip); 
                              
                              String sik_IsAlternateAddress= sL.nodeFromKey(senddata,"<sik:IsAlternateAddress>","</sik:IsAlternateAddress>");
                             Application.showMessage("sik_IsAlternateAddress is ::"+sik_IsAlternateAddress); 
                              
                              
                              
                              String sik_ProductCode= sL.nodeFromKey(senddata,"<sik:ProductCode>","</sik:ProductCode>");
                             Application.showMessage("sik_ProductCode is ::"+sik_ProductCode); 
                                                                               
                              String sik_DNCSIPAddress= sL.nodeFromKey(senddata,"<sik:DNCSIPAddress>","</sik:DNCSIPAddress>");
                             Application.showMessage("sik_DNCSIPAddress is ::"+sik_DNCSIPAddress); 
                                                   
                              String sik_HeadEndVendor= sL.nodeFromKey(senddata,"<sik:HeadEndVendor>","</sik:HeadEndVendor>");
                             Application.showMessage("sik_HeadEndVendor is ::"+sik_HeadEndVendor); 
                                                          
                              String sik_E911_acceptance= sL.nodeFromKey(senddata,"<sik:E911_acceptance>","</sik:E911_acceptance>");
                             Application.showMessage("sik_E911_acceptance is ::"+sik_E911_acceptance); 
                                        
                              String sik_AgentID= sL.nodeFromKey(senddata,"<sik:AgentID>","</sik:AgentID>");
                             Application.showMessage("sik_AgentID is ::"+sik_AgentID); 
                              
                              String sik_WorkOrderID= sL.nodeFromKey(senddata,"<sik:WorkOrderID>","</sik:WorkOrderID>");
                             Application.showMessage("sik_WorkOrderID is ::"+sik_WorkOrderID); 
                              c.put("psik_WorkOrderID", sik_WorkOrderID);  
                                                   
                              
                             
                                            
                              
                       break;
                       }
                 }
             }
             
             if(v1*callFound*v2*v3*v4*ratecodegroup ==1)
             {
                  Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
             }
             else
             {
                   c.put("result", "false");
                   c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
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
	
	public void orderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
    {
                
                Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
                sikLibraryClass sL = new sikLibraryClass();
                
                ResultSet  rs;
                int callFound=0,v1=0,v2=0,v3=0,v4=0;
                String xmldata1 ="receive_data";
                String xmldata2 ="SEND_DATA";
               
                Application.showMessage("-----------------------------------------------------");
                Application.showMessage("**********orderUpdate_Validate Function************");       
                Application.showMessage("----------------------------------------------------");
                    
                  try
                  {
                         Statement st =sL. dBConnect(input, c);  
                    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                    while (rs.next())
                    {
                         
                   
                      String rowmsg;
                            rowmsg = rs.getString(1);
                                     
                            if(rowmsg.equals(c.getValue("BaseMsgid")))break;
                       if(rs.getBlob("receive_data")==null)
                       {
                       
                         Application.showMessage("Your Recieve XML is NULL \n");
                         
                         String senddata =sL.extractXml(rs,xmldata2);
                         Application.showMessage("Your Recieve XML is::\n"+senddata);
                      
                                     
                             
                              
                                // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
                         
                         

                              String ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
                             Application.showMessage("accountNumber is ::"+ou_AccountNumberid);    
                              if(ou_AccountNumberid== null)
                             {
                               continue;
                             }
                             else if(ou_AccountNumberid.equals(c.getValue("accountNumber")))
                         {
                                
                                //System.out.printf("SEND XML is %s \n",senddata);
                                Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
                                Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
                                callFound=1;
                         }
                         else
                         {
                                continue;
                         }
                         
                             // String ou_guid= sL.nodeFromKey(senddata,"<value name=\"guid\">","</value><value name=\"address\">");
                            // Application.showMessage("guid is ::"+ou_guid);           
////                          if(ou_guid.equals(c.getValue("serviceRequestId")))
////                     {
////                            
////                            //System.out.printf("SEND XML is %s \n",senddata);
////                            Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update Call********");
////                            Application.showMessage("Validation is Successfull with Input serviceRequestId"+ou_guid);
////                            callFound=1;
////                     }
//                       else
//                       {
//                              continue;
//                       }
                              if(callFound==1)
                              {
                            	  String sendDataT=sL.RemoveNameSpaces(senddata);
                                     String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"customerType\">");
                                    Application.showMessage("productType is ::"+ou_productType);
                                    
                                     if(ou_productType==null)
                                     {
                                           c.report("Send Xml productType is NULL");
                                           continue;
                                     }
                                     else
                                     {
                                        Application.showMessage("productType Send  is ::"+" "+ou_productType);
                                        if(ou_productType.equalsIgnoreCase("SIK"))
                                            {
                                              Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
                                              Application.showMessage("Validation is Successfull with ou_productType::"+" "+ou_productType);
                                              v1=1;
                                            }
                                            else
                                            {
                                              c.report("ou_productType at Send Xml not Validated as "+ou_productType);
                                            }
                                     }        

                                   /* String ou_billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
                                    Application.showMessage("billingSystem is ::"+ou_billingSystem); 
                                     
                                    if(ou_billingSystem==null)
                                     {
                                           c.report("Send Xml billingSystem is NULL");
                                           continue;
                                     }
                                     else
                                     {
                                        Application.showMessage("BillingSystem from Send Xml is ::"+" "+ou_billingSystem);
                                        if(ou_billingSystem.equals("CSG"))
                                            {
                                              Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
                                              Application.showMessage("Validation is Successfull with billingSystem::"+" "+ou_billingSystem);
                                              v2=1;
                                            }
                                            else
                                            {
                                              c.report("billingSystem at Send Xml not Validated as "+ou_billingSystem);
                                            }
                                     }*/
                                     
                                     //-------------
                                     String ErrorText= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorCode\">");
                                     Application.showMessage("Error Text is ::"+ErrorText); 
                                     String Errorcode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"ordStatus\">");
                                     Application.showMessage("Error code is ::"+Errorcode);
                                     if(ErrorText==null||Errorcode==null) 
                                      {
                                            c.report("Send Xml error message  is NULL");
                                         
                                      }
                                     else 
                                       {
                                         Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                         Application.showMessage("Validation is Successfull with ErrorMessages::");
                                         v2=1;
                                       }
                                      
                                       
                                       //-----------------------

                                    String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"errorText\">");
                                    Application.showMessage("customerType is ::"+ou_customerType); 
                                     if(ou_customerType==null)
                                     {
                                           c.report(" ou_customerType is NULL");
                                           
                                     }
                                    
                                     
                                     String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"ordType\">");
                                    Application.showMessage("ordStatus is ::"+ordStatus); 
                                    if(ordStatus==null)
                                     {
                                           c.report("Send Xml ordStatus is NULL");
                                        
                                     }
                                    else if(ordStatus.equals("ERR"))
                                      {
                                        Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                        Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                        v3=1;
                                      }
                                     
                                      else
                                      {
                                        c.report("ordStatus at Send Xml not Validated as "+ordStatus);
                                      }
                                      
                                    
                                     String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
                                    Application.showMessage("ordType is ::"+ordType); 
                                     if(ordType==null)
                                     {
                                           c.report("Send Xml ordType is NULL");
                                           
                                     }
                                    else if(ordType.equals("NEW"))
                                      {
                                        Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                        Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
                                        v4=1;
                                      }
                                      else
                                      {
                                        c.report("ordStatus at Send Xml not Validated as "+ordType);
                                      }   
                                     
//                                  String accountNumber_ou= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
//                                  Application.showMessage("accountNumber is ::"+accountNumber_ou); 
//                                  if(accountNumber_ou.equals(c.getValue("accountNumber")))
//                                    {
//                                      Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
//                                      Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
//                                      v4=1;
//                                    }
//                                    else
//                                    {
//                                      c.report("accountNumber at Send Xml not Validated as "+accountNumber_ou);
//                                    }    
                                     
                                   /*  String order_id= sL.nodeFromKey(senddata,"<value name=\"shipOrderId\">","</value><value name=\"submitDate\">");
                                    Application.showMessage("action is ::"+order_id); 
                                     if(order_id.equals(c.get("pOrderID_sik")))
                                     {
                                         Application.showMessage("Order ID validation is success");
                                           
                                     }*/
                                    
                                   //worklist...........................
                	                 
             	                    Application.showMessage("----------------******Worklist******---------------");
         	                        
         	                        
         	                        String customerTypewl= sL.GetValueByXPath(sendDataT, "/comRequest/wlRequest/worklist/value");
         	                        Application.showMessage("The customerType in worklist from OrderUpdate is::"+customerTypewl);                       
         	                     
         	                        
         	                        String ordStatuswl= sL.GetValueByXPath(sendDataT, "/comRequest/wlRequest/worklist/value[2]");
         	                        Application.showMessage("The ordStatus in worklist from Request is::"+ordStatuswl);                       
         	                       
         	                        
         	                        
         	                     
         	                        String errorCodewl= sL.GetValueByXPath(sendDataT, "/comRequest/wlRequest/worklist/value[3]");
         	                        Application.showMessage("The errorCode in worklist from OrderUpdate is::"+errorCodewl);                       
         	                      
         	                        
         	                        
         	                       
         	                        String detailswl= sL.GetValueByXPath(sendDataT, "/comRequest/wlRequest/worklist/value[4]");
         	                        Application.showMessage("The details in worklist from Request is::"+detailswl);  

                                    
                              break;
                              }
                        }
                    }
                    
                    if(v1*v2*v3 ==1)
                    {
                         Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
                    }
                    else
                    {
                          c.put("result", "false");
                          c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
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
