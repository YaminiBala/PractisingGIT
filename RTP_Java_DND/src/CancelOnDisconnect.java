
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;




public class CancelOnDisconnect
{
	
	public void IterationLogicUpgradeDownGrade(Object input, ScriptingContext c) throws IOException
	{
		
		
		String sN=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SCENARIO_NUMBER");
		Application.showMessage("Scenario Number is ::"+sN);
		
		String IorD=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName");
		Application.showMessage("Scenario Name is ::"+IorD);
		
		if(sN.equalsIgnoreCase("1"))
    	{
    		c.setValue("IsInstall", "true");
    		c.setValue("IsCancel", "false");
    	}
    	else
    	{
    		c.setValue("IsInstall", "false");
    		c.setValue("IsCancel", "true");	
    	}
		
		if(IorD.equalsIgnoreCase("DEMI"))
		{
			c.setValue("IsDemi", "true");
		}
		else
		{
			c.setValue("IsDemi", "false");
		}
	  
	}


	public void simGetAccountValidate(Object input , ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
	     LibraryRtp lr=new  LibraryRtp();
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
	    /* lr.findThinktimeOperationRTP(input, c);
	     c.setValue("getAccount","false");  */
	     HashMap Operation=lr.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccountSim"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccountSim"));
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' order by creation_time desc)where rownum <= 20");
			rs=lr.reduceThinkTimeRTP(input, c);
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
	                          
		           /* String senddata = lC.extractXml(rs,xmldata2);           
		            String recievedata = lC.extractXml(rs,xmldata1);*/
	            	//Sruthi
	            	String senddatacls = lC.extractXml(rs,xmldata2);                                        
	            	String recievedatacls = lC.extractXml(rs,xmldata1);      
	            	String senddata=lr.clsRemoveAsciiCharacter(senddatacls);  
	            	String recievedata=lr.clsRemoveAsciiCharacter(recievedatacls);
	            	//Sruthi
String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
Application.showMessage("ReceiveDataTrim::::"+receiveDataTrim);
Application.showMessage("senddata::::"+senddata);
		          	           
		            String id= lC.nodeFromKey(senddata,"<resourceId>","</resourceId>");
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
		      
		            	
		 	            String errorCode_getAcc=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/errorcode"); 
		            	//String errorCode_getAcc= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
		 	             Application.showMessage("errorCode is ::"+ errorCode_getAcc);
		 	             
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
			            	 else if(errorCode_getAcc.equalsIgnoreCase("UCE-15101"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
				            	 c.report("Account not Found");
				             }
				             else 
				             {
				            	 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
				             }
			            }		
		 	            
		 	             if(errorCode_getAcc.equalsIgnoreCase("0"))
		 	             {
		 	             String status_getAcc= lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/status");
		 	            c.put("getStatus",status_getAcc);
		 	             Application.showMessage("GetAccount Status is::"+status_getAcc);
		 	              	             
		 	             
		 	            if(c.getValue("IsMultiExist").equals("true"))
		            	 {
			 	            String group1= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
			                Application.showMessage("group1 is::"+group1); 
		            	
			                if(group1 ==null)
			                {
			            	Application.showMessage("Group1 is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+group1);
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
			                
			                
			                String service1= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[2]");
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
				                
		            		 
		            	 }}
			            
		 	            
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
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
		
	}


	public String orderUpdate_COD(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String rscallpresent="true";
	     String Time= c.get("BaseTime").toString();
	     int conVal=0;
	    // c.setValue("OrderUpdate","true");
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********OrderUpdate_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	    // lC.findThinktimeOperationRTP(input, c);
	     //c.setValue("OrderUpdate","false"); 
	     OUTER1:
			   for(int countval=0;countval<=5;countval++)
			   {
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
		
			 HashMap  Operation=lC.findingoperations(input, c);
		     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
		     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
		       
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
	 	           Application.showMessage("accountNumber from Install is ::"+c.getValue("ACC_input")); 
	 	            
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
	            		
	            		
	            		continue ;
	            	}
		            
		            if(callFound==1)
		            {
		      
		 	            

		 	            String vomInstance= lC.nodeFromKey(senddata,"<value name=\"vomInstance\">","</value><value name=\"ordSource\">");
		 	            Application.showMessage("vomInstance is ::"+vomInstance); 

		 	           
		 	            
		 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		 	            {
		 	        	   Application.showMessage("Check"+c.getValue("IsDemi"));
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
		 	            	
		 	            	Application.showMessage("Insight Services");
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
			            	 if(ordType.equals("DIS") || ordType.equals("NEW"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with Order Type::"+" "+ordType);
				            	 v2=1;
				             }
			            	 
			            	 else  if(ordType.equals("CAN") || ordType.equals("NEW"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with Order Type::"+" "+ordType);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("Order Type at Send Xml not Validated as "+ordType);
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

	        if(callFound==1)
	        {
	        	countval=5;
	         if(v1*callFound*v2*v3*v4 ==1)
	         {
	        	 c.setValue("IsDemi", "false");
	         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.setValue("IsDemi", "false");
	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
	         }
	        }
	        else  if(countval==5)
	        {
		         if(v1*callFound*v2*v3*v4 ==1)
		         {
		        	 c.setValue("IsDemi", "false");
		         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 c.setValue("IsDemi", "false");
		        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
		         }
		        }
	        else
	        {
	        	
	        }
	       //  st.close();
		}
			
		
		}
	     
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		if(countval!=5 )
		{
			continue OUTER1;
		}
		else
		{
			break OUTER1;
		}
			   }	    
		
		return rscallpresent;
	 }


	 
}
