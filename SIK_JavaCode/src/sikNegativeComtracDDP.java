import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class sikNegativeComtracDDP {
	public void submitOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0,v9=0,v10=0,v11=0,v12=0,tn=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String submitorder="SubmitOrder is failed with Validation errors";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********submitOrder_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
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
	            	String sik_AccountNumber= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:AccountNumber"); 
			       	Application.showMessage("sik_AccountNumber is ::" +sik_AccountNumber);
	            	
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
	 	            Application.showMessage("sik_AccountNumber is ::"+sik_AccountNumber); 
	 	            
	 	            if(sik_AccountNumber==null)
	 	            {
	 	             continue;	
	 	            }
	 	            else if(sik_AccountNumber.equals(c.get("pAccountNumber")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+sik_AccountNumber);
	            		callFound=1;
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
		                   if(cct_code==null)
		                   {
		                	   String sik_OrderID= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
		                	   Application.showMessage("Order Id From SIK is ::"+sik_OrderID);
		                	   c.put("psik_OrderID",sik_OrderID);
		                	   if(sik_OrderID==null)
		                	   {
		                		   continue;
		                	   }

		                   }
		                   else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-213"))
		                   {
		                	   c.put("MFlag","1");
		                	   
			                   String orderid_modify= sL.nodeFromKey(recievedata,"</code><text>Duplicate Order - Single Not Exact Match: Duplicate order detected; matched order #","</text></message></messages");
                               Application.showMessage("Modify Order ID found is :: "+orderid_modify);
                               c.put("MOrderID", orderid_modify);
		                   }
		                   else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-104"))
		                   {
		                	 //  c.put("MFlag","1");
		                	   
			                   String service_error= sL.nodeFromKey(recievedata,"</cct:code><cct:text>","</cct:text></cct:message>");
                               Application.showMessage("Serviceability or configuration error found is :: "+service_error);
                             //  c.put("MOrderID", orderid_modify);
		                   }

		                   String areacode= c.getValue("SwivelDDP","ComtracParameterList: areaCode");
		                   String phone1= c.getValue("SwivelDDP","ComtracParameterList: phone");
		                   String phone2= c.getValue("SwivelDDP","ComtracParameterList: phone2");
		                   if(phone2.equals(null) || phone2.isEmpty() || phone2.equals("0"))
		                   {
		                	   Application.showMessage("Request is processed without second TN. Submit order has to pick the first TN");
		                   }
		                   else
		                   {
		                	   Application.showMessage("Request is processed with TN.But,  Submit order has to pick the first TN");
		                   }
		                  
		                   String phonenumber=areacode.concat(phone1);
		                   
		                		   String sik_telephone= sL.nodeFromKey(senddata,"<sik:Phone>","</sik:Phone>");
		                   Application.showMessage("SIK_Telephone Number is::"+sik_telephone);
		                   if(sik_telephone==null)
				            {
					            c.report(" sik_Telephone is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("sik_Telephone NUmber from Send Xml  from Submit Order is ::"+" "+sik_telephone);
				            	 if(sik_telephone.equalsIgnoreCase(phonenumber))
					             {
				            		 tn=1;
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Telephone NUmber********");
					            	 Application.showMessage("Validation is Successfull with Telephone Number::"+" "+sik_telephone);
					            	// mtc.report(submitorder);
					             }
				            	
					             else 
					             {
					            	 c.report("Telephone Number at Send Xml not Validated");
					            	
					            	Application.showMessage("Telephone Number at Send Xml not Validated as "+sik_telephone);
					            	continue;
					             }
				            }
		            	// String sik_Email= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");
			                String sik_Email= sL.nodeFromKey(senddata,"<sik:Email>","</sik:Email>");

			 	            Application.showMessage("sik_Email is ::"+sik_Email); 
			 	            if(sik_Email==null)
				            {
					            c.report(" sik_Email is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("sik_Email from Send Xml  from Submit Order is ::"+" "+sik_Email);
				            	 if(sik_Email.equalsIgnoreCase((String) c.get("pEmail")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Email********");
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
		 	            Application.showMessage("firstName is ::"+sik_FirstName);
		 	            
		 	            if(sik_FirstName==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("First Name Validation is ::"+" "+sik_FirstName);
			            	 if(sik_FirstName.equals(c.get("pFName")))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: First Name Validation ********");
				            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_FirstName);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("FirstName at Send Xml not Validated as "+sik_FirstName);
				             }
			            }		

		 	            String sik_LastName= sL.nodeFromKey(senddata,"<sik:LastName>","</sik:LastName>");
		 	            Application.showMessage("lastName is ::"+sik_LastName); 
		 	            
		 	           if(sik_LastName==null)
			            {
				            c.report("Send Xml LastName is NULL");
				            continue;
			            }
			            else
			            {
			            	 if(sik_LastName.equals(c.get("pLName")))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-lastName_DDS********");
				            	 Application.showMessage("Validation is Successfull with LastName::"+" "+sik_LastName);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("LastName at Send Xml not Validated as "+sik_LastName);
				             }
			            }

		 	           
		 	            
		 	            String sik_ShipmentPriority= sL.nodeFromKey(senddata,"<sik:ShipmentPriority>","</sik:ShipmentPriority>");
		 	            Application.showMessage("ShipmentPriority is ::"+sik_ShipmentPriority); 
		 	            
		 	            String sik_FTA= sL.nodeFromKey(senddata,"<sik:FTA>","</sik:FTA>");
		 	            Application.showMessage("sik_FTA is ::"+sik_FTA);
		 	            if(sik_FTA.equalsIgnoreCase(c.get("GL_ftaAgent").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 5 :: WebServicall-********");
		 	            	 v4=1;
		 	            	Application.showMessage("FTA got Validated with GetLocation FTA");
		 	            	
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("FTA NOT!! Validated with GetLocation FTA");
		 	            	c.report("FTA NOT Validated with GetLocation FTA");
		 	            }
		 	            
		 	            Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));

		 	           
		 	            //Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
		 	            
		 	           if(c.get("pCorp").equals("01790"))
		 	          {
		 	          	v5=1;
		 	          }
		 	          else
		 	          {
		 	          
		 	            String sik_Address1= sL.nodeFromKey(senddata,"<sik:Address1>","</sik:Address1>");
		 	            Application.showMessage("sik_Address1 is ::"+sik_Address1); 
		 	           Application.showMessage("pAddress1 is ::"+c.get("pAddress1"));
		 	            
		 	            if(sik_Address1.trim().equals(c.get("pAddress1").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 6 :: WebServicall-********");
		 	            	 v5=1;
		 	            	Application.showMessage("Address1 got Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            	c.report("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            }
		 	          }
		 	            
		 	           /* String sik_Address2= sL.nodeFromKey(senddata,"<sik:Address2>","</sik:Address2>");
		 	            Application.showMessage("sik_Address2 is ::"+sik_Address2); 
		 	            if(sik_Address2.trim().equals(c.get("pAddress2").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 7 :: WebServicall-********");
		 	            	 v6=1;
		 	            	Application.showMessage("Address2 got Validated with GetHouse Address2 as"+c.get("pAddress2").toString());
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("Address2 NOT Validated with GetHouse Address2 as"+c.get("pAddress2").toString());
		 	            	c.report("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress2").toString());
		 	            }*/
		 	            
		 	            String sik_City= sL.nodeFromKey(senddata,"<sik:City>","</sik:City>");
		 	            Application.showMessage("sik_City is ::"+sik_City); 
		 	            
		 	            if(sik_City.equalsIgnoreCase(c.get("GL_City").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 8 :: WebServicall-********");
		 	            	 v7=1;
		 	            	Application.showMessage("City got Validated with GetLocation City");
		 	            }
		 	            else
		 	            {
		 	            	c.report("City NOT Validated with GetLocation City");
		 	            }
		 	            
		 	            String sik_State= sL.nodeFromKey(senddata,"<sik:State>","</sik:State>");
		 	            Application.showMessage("sik_State is ::"+sik_State); 
		 	            if(sik_State.equalsIgnoreCase(c.get("GL_state").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 9 :: WebServicall-********");
		 	            	 v8=1;
		 	            	Application.showMessage("State got Validated with GetLocation State");
		 	            }
		 	            else
		 	            {
		 	            	c.report("State NOT Validated with GetLocation State");
		 	            }
		 	            
		 	            
		 	            String sik_Zip= sL.nodeFromKey(senddata,"<sik:Zip>","</sik:Zip>");
		 	            Application.showMessage("sik_Zip is ::"+sik_Zip); 
		 	            
		 	            String sik_IsAlternateAddress= sL.nodeFromKey(senddata,"<sik:IsAlternateAddress>","</sik:IsAlternateAddress>");
		 	            Application.showMessage("sik_IsAlternateAddress is ::"+sik_IsAlternateAddress); 
		 	            
		 	           	 	            
		 	            String sik_ProductCode= sL.nodeFromKey(senddata,"<sik:ProductCode>","</sik:ProductCode>");
		 	            Application.showMessage("sik_ProductCode is ::"+sik_ProductCode); 
		 	            if(sik_ProductCode.equalsIgnoreCase(c.get("pKRateCode3").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 10 :: WebServicall-********");
		 	            	 v9=1;
		 	            	Application.showMessage("sik_ProductCode got Validated with GetLocationProductCode");
		 	            }
		 	            else
		 	            {
		 	            	c.report("sik_ProductCode NOT Validated with GetLocation ProductCode");
		 	            }
		 	            
		 	           	 	            		 	            
		 	                        
		 	            String sik_E911_acceptance= sL.nodeFromKey(senddata,"<sik:E911_acceptance>","</sik:E911_acceptance>");
		 	            Application.showMessage("sik_E911_acceptance is ::"+sik_E911_acceptance); 
		 	                      
		 	            String sik_AgentID= sL.nodeFromKey(senddata,"<sik:AgentID>","</sik:AgentID>");
		 	            Application.showMessage("sik_AgentID is ::"+sik_AgentID); 
		 	            
		 	            String sik_WorkOrderID= sL.nodeFromKey(senddata,"<sik:WorkOrderID>","</sik:WorkOrderID>");
		 	            Application.showMessage("sik_WorkOrderID is ::"+sik_WorkOrderID); 
		 	            if(sik_WorkOrderID.equalsIgnoreCase(c.get("pw_wpcnt").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 13:: WebServicall-********");
		 	            	 v12=1;
		 	            	Application.showMessage("sik_WorkOrderID got Validated with pw_wpcnt");
		 	            }
		 	            else
		 	            {
		 	            	c.report("sik_WorkOrderID NOT Validated with  pw_wpcnt");
		 	            }
		 	         	 	            
		 	            
		 	            
		 	                     
		 	      	 	            
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v4* v5*v7*v8*v9*v12 ==1)
	         {
	         	Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
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
	
	
	public void orderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0, v6=0,v7=0;
		 int myCount=1;
		 String Status=null;
		 //String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********orderUpdate_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     
	     
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
	         while (rs.next())
	         {
	        	
	        	 if(myCount ==1)
	    	     {
	    	    	 
	    	    	 Status="INI";
	    	    	 
	    	     }
	    	     else if(myCount ==2)
	    	     {
	    	    	 Status="ERR"; 
	    	     }
	    	     else
	    	     {
	    	       Application.showMessage("more than two Order Update...!");
	    	     }
	        
	            String rowmsg;
			    rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Send XML is::\n"+senddata);
	           
	                          
		            
		            
			       // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
	            	
	            	

		            String ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
	 	            Application.showMessage("accountNumber is ::"+ou_AccountNumberid); 	
	 	            if(ou_AccountNumberid== null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(ou_AccountNumberid.equals(c.get("pAccountNumber")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
	            		callFound=1;
	            	}
	 	            
	 	           else if(ou_AccountNumberid.equals(c.get("pAccountNumber1")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input Transfer Account Number"+ou_AccountNumberid);
	            		callFound=1;
	            	}
	 	            
	            	else
	            	{
	            		continue;
	            	}
	            	
		           
		            if(callFound==1)
		            {
		      
		 	           String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"customerType\">");
		 	            Application.showMessage("productType is ::"+ou_productType);
		 	            
		 	            if(ou_productType==null)
			            {
				            c.report("Send Xml productType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("productType Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ou_productType);
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

		 	           String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"errorText\">");
		 	            Application.showMessage("CustomerType is ::"+ou_customerType);
		 	            
		 	            if(ou_customerType==null)
			            {
				            c.report("Send Xml CustomerType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("CustomerType Send Xml   is ::"+" "+ou_customerType);
			            	 if(ou_customerType.equalsIgnoreCase("RES"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
				            	 Application.showMessage("Validation is Successfull with ou_customerType::"+" "+ou_customerType);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("ou_customerType at Send Xml not Validated as "+ou_customerType);
				             }
			            }		

		 	           String ou_errorText= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorCode\">");
		 	            Application.showMessage("Error text is ::"+ou_errorText);
		 	            c.put("ErrorTextOu", ou_errorText);
		 	            if(ou_errorText==null)
			            {
				            c.report("Send Xml Error text is NULL");
				            continue;
			            }
			            else
			            {
			            	 
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
				            	 Application.showMessage("Validation is Successfull with ou_errortext::"+" "+ou_errorText);
				            	 v3=1;
				             
				            
			            }		

		 	           String ou_errorcode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"ordStatus\">");
		 	            Application.showMessage("Error code is ::"+ou_errorcode);
		 	            c.put("ErrorCodeOu",ou_errorcode);
		 	            if(ou_errorcode==null)
			            {
				            c.report("Send Xml error code is NULL");
				            continue;
			            }
			            else
			            {
			            	
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
				            	 Application.showMessage("Validation is Successfull with ou_error code::"+" "+ou_errorcode);
				            	 v4=1;
				            
			            }		

		 	       /*     String ou_billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
		 	            Application.showMessage("billingSystem is ::"+ou_billingSystem); 
		 	            
		 	           if(ou_billingSystem==null)
			            {
				            c.report("Send Xml billingSystem is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("BillingSystem from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ou_billingSystem);
			            	 if(ou_billingSystem.equals("DDP"))
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

		 	            
			           
		 	            
		 	            String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"ordType\">");
		 	            Application.showMessage("ordStatus is ::"+ordStatus); 
		 	            if(ordStatus==null)
			            {
				            c.report("Send Xml ordStatus is NULL");
				         
			            }
		 	            else if(ordStatus.equalsIgnoreCase("ERR"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-ordStatus********");
			            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
			            	 v5=1;
			            	 myCount++;
			             }
		 	            
		 	           
			             else
			             {
			                continue;
			             }
		 	         	
		 	           if(c.get("pRequestType").toString().equalsIgnoreCase("install"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("NEW"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v6=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	           else if(c.get("pRequestType").toString().equalsIgnoreCase("cancel"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("CAN"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v6=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	          else if(c.get("pRequestType").toString().equalsIgnoreCase("reschedule"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("RSH"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v6=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	           
		 	         else if(c.get("pRequestType").toString().equalsIgnoreCase("changeofservice"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("CHG"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v6=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	        else if(c.get("pRequestType").toString().equalsIgnoreCase("transfer"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("TRF"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v6=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	           
		 	         else if(c.get("pRequestType").toString().equalsIgnoreCase("restart"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("RST"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v6=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	     
		 	           
		 	            String accountNumber_ou= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
		 	            Application.showMessage("accountNumber is ::"+accountNumber_ou);
		 	            
		 	             if(accountNumber_ou.equals(c.get("pAccountNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
			            	 v7=1;
			             }
		 	             
		 	             else if(accountNumber_ou.equals(c.get("pAccountNumber1")))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
			            	 v7=1;
			             }
		 	            	 
		 	             
			             else
			             {
			            	 c.report("accountNumber at Send Xml not Validated as "+accountNumber_ou);
			             }    
		 	            
		 	            String BillingOrderID= sL.nodeFromKey(senddata,"<value name=\"billingOrderId\">","</value><value name=\"corpId\">");
		 	            Application.showMessage("BillingOrderID is ::"+BillingOrderID); 
		 	            if(BillingOrderID==null)
			            {
				          Application.showMessage("Send Xml BillingOrderID is NULL");
				            
			            }
		 	            Application.showMessage("*********Validate the Worklist Task*********");
		 	          
		 	           String details= sL.nodeFromKey(senddata,"<value name=\"details\">","</value><value name=\"otherNumber\">");
		 	            Application.showMessage("Details from Worklist task is ::"+details); 
		 	            if(details==null)
			            {
				          Application.showMessage("Send Xml Details from Worklist task is NULL");
				            
			            }
		 	           String otherNumber= sL.nodeFromKey(senddata,"<value name=\"otherNumber\">","</value><value name=\"otherInfo\">");
		 	            Application.showMessage("otherNumber from Worklist task is ::"+otherNumber); 
		 	            if(otherNumber==null)
			            {
				          Application.showMessage("Send Xml otherNumber from Worklist task is NULL");
				            
			            }
		 	           String otherInfo= sL.nodeFromKey(senddata,"<value name=\"otherInfo\">","</value></worklist>");
		 	            Application.showMessage("otherInfo from Worklist task is ::"+otherInfo); 
		 	            if(otherInfo==null)
			            {
				          Application.showMessage("Send Xml otherInfo from Worklist task is NULL");
				            
			            }
		 	            
		 	        if(myCount>2)
		 	        {
		            break;
		 	        }
		 	        else
		 	        {
		 	        	continue;
		 	        }
		 	        
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3*v4*v5*v6*v7 ==1)
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
