import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class ChangeOfServiceClass 
{
	LibraryRtp lR=new LibraryRtp();
	RTP_ValidationsClass vC = new RTP_ValidationsClass();
	
	
	
	
	public String getAccoutCos_Validate(Object input, ScriptingContext c/*String suspendFlag*/) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();

String rscallpresent="true";
	   //  c.setValue("getAccount","true");
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     
		 
			  ResultMap= lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
			     c.setValue("ExistingGroup",ResultMap.get("group"));
			     c.setValue("ExistingService", ResultMap.get("service"));
			     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
			     ResultMap=lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: NEWSERVICE"));
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
		  
			   //  lC.findThinktimeOperationRTP(input, c);
			   //  c.setValue("getAccount","false"); 
			     HashMap Operation=lC.findingoperations(input, c);
			     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccount"));
			     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccount"));
	     
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
			rs=lC.reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
        	{
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
	                //Sruthi          
	            	String senddatacls = lC.extractXml(rs,xmldata2);                                        
	            	String recievedatacls = lC.extractXml(rs,xmldata1);      
	            	String senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
	            	String recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
	            	//Sruthi
	            	Application.showMessage("senddata is ::"+senddata);
		          String receiveDataTrim = lC.RemoveNameSpaces(recievedata);
		          Application.showMessage("ReceiveDataTrim:::"+receiveDataTrim);
		            String id= lC.nodeFromKey(senddata,"<resourceId>","</resourceId>");
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
		            	String errorCode=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/errorCode"); 
		            //	String errorCode= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
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
		                	String centralStationAccountNumber= lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/premise/centralStationAccountNumber");
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
		            		 String group1= lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
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
			                
			                
			                String service1= lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[2]");
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
		            		    String group1= lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
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
			            String accountId_getAcc= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/accountId");
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
			             
			                String isSuspend= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/accountId");
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
        	}
	         if(v1*v2*v3*v4==1)
	         {
	         	Application.showMessage("WebService Call :: GetAccount is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: GetAccount is Failed with Validation Errors");
	         }
	        // st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	}
	
	//----------------
	public String clsGetAccout_Validatecos(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException
	{
		
		Thread.sleep(100000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     String rscallpresent="true";
	    // c.setValue("getAccount_CLS","true");
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****CLS GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     List<String> grpserv=extractServices(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"),input,c);
	     if(grpserv.size()==3)
	     {
	    	 c.setValue("ExistingGroup",grpserv.get(0));
		     c.setValue("ExistingService", grpserv.get(1));
		     c.setValue("ExistingService1", grpserv.get(2));
		     c.setValue("IsMultiExist", "true");
		     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
		     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
		     Application.showMessage("ExistingService1::"+c.getValue("ExistingService1"));
		     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	     }
	     else
	    	 {
	    	 ResultMap= lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
	    	
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	    
	     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
	     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
	     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	    	 }
	   //  lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("getAccount_CLS","false");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccount"));


	     
	     
		try
		{
			//Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
			rs=lC.reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{

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
		         	String 	 senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
		         	String 	 recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
		         	String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
		            String id= lC.nodeFromKey(senddata,"<resourceId>","</resourceId>");
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
		            	
		            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
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
			                String centralStationAccountNumber= lC.nodeFromKey(recievedata,"<centralStationAccountNumber>","</centralStationAccountNumber>");
			                Application.showMessage("centralStationAccountNumber is::"+centralStationAccountNumber); 
		            	
			                if(centralStationAccountNumber ==null)
			                {
			            	Application.showMessage("centralStationAccountNumber is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("centralStationAccountNumber from Recieve Xml  from GetAccount is ::"+" "+centralStationAccountNumber);
			            	    Application.showMessage("Ccentral number from Order update call :::"+c.getValue("CcentralNum"));
			            	    if(centralStationAccountNumber.trim().equals(c.getValue("CcentralNum")))
			            	    //if(centralStationAccountNumber.trim().equals("C99239914"))
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
		            	int count=lC.getProductDetails(input, c, recievedata,"group");
		            	 if(c.getValue("IsMultiExist").equals("true"))
		            	 {
		            		 if(count>=3)
		            		 {
		            			 String xfinity=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
		            			 String service=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[2]");
		            			 String group=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[3]");
		            			 Application.showMessage("GroupTier from receiveXML from GetAccount is::"+ group);
		            			 Application.showMessage("ServiceTier from receiveXML from GetAccount is::"+ service);
		            			 Application.showMessage("*******Validation Point 1 :: xfinity********");
		            			 if(xfinity.equalsIgnoreCase("XfinityEvents")||xfinity.equalsIgnoreCase("XfinityEvents-Insight"))
		            			 {
		            				 v3=1;
		            				 Application.showMessage(" xfinity from receiveXML from GetAccount is::"+ xfinity);
		            			 }
		            			 else if(xfinity.trim().equals(c.getValue("ExistingGroup").trim()) || xfinity.trim().equals(c.getValue("ExistingService").trim())  || xfinity.trim().equals(c.getValue("ExistingService1").trim()))
		            	
			                {
		            				 v3=1;
		            				 Application.showMessage(" group from receiveXML from GetAccount is::"+ xfinity);
			                }
			                else
			                {
		            				 c.report("Xfinity not validates from  GetAccount is::"+ xfinity);
		            			 }
		            			 Application.showMessage("*******Validation Point 2 :: group********");
		            			          			 
				            	    if(group.trim().equals(c.getValue("ExistingGroup").trim()) || group.trim().equals(c.getValue("ExistingService").trim()) || group.trim().equals(c.getValue("ExistingService1").trim()))
				                 {
				            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group);
				            	 v3=1;
				                 }
				            	    else if(group.equalsIgnoreCase("XfinityEvents")||group.equalsIgnoreCase("XfinityEvents-Insight"))
				               {
				            	    	v3=1;
				            	    	Application.showMessage("Validation is Successfull with GroupTier::"+" "+group);
				               }
				            	    else
				            	    {
				            	    	 c.report("group not validates from  GetAccount is::"+ group);
			                }
				            	    Application.showMessage("*******Validation Point 3 :: service1********");
			                
				            	    if(service.trim().equals(c.getValue("ExistingService").trim()) || service.trim().equals(c.getValue("ExistingService1").trim()) || service.trim().equals(c.getValue("ExistingGroup").trim()))
				                 {
			                
				            	 Application.showMessage("Validation is Successfull with service1::"+" "+service);
				            	 v3=1;
				                 }else if(service.equalsIgnoreCase("XfinityEvents")||service.equalsIgnoreCase("XfinityEvents-Insight"))
			                {
				                	 Application.showMessage("Validation is Successfull with Xfinity::"+" "+service);
					            	 v3=1; 
			                }
			                else
			                {
				                	v3=0;
				                	c.report("service is not validated as " +service);
		            		 } }
		            		 else
		            		 {
		            			 String service=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
		            			 String group=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[2]");
		            			 Application.showMessage("GroupTier from receiveXML from GetAccount is::"+ group);
		            			 Application.showMessage("ServiceTier from receiveXML from GetAccount is::"+ service);
		            			 Application.showMessage("*******Validation Point 2 :: group********");
			          			 
				            	    if(group.trim().equals(c.getValue("ExistingGroup").trim()) || group.trim().equals(c.getValue("ExistingService").trim()))
					                 {
					            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group);
					            	 v3=1;
					                 }
				            	    else if(group.equalsIgnoreCase("XfinityEvents")||group.equalsIgnoreCase("XfinityEvents-Insight"))
				                 {
				            	    	Application.showMessage("*******Validation Point 4 :: GroupTier********");
						            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group);
				            	 v3=1;
				                 }
				                else
				               {
				                	v3=1;
				            	    //	c.report("group is not validated as " +group);
			                }
				            	    Application.showMessage("*******Validation Point 3 :: service1********");
			                
				            	    if(service.trim().equals(c.getValue("ExistingService").trim()) || service.trim().equals(c.getValue("ExistingGroup").trim()))
				                 {
			                
				            	 Application.showMessage("Validation is Successfull with service1::"+" "+service);
				            	 v3=1;
				                 }
				            	    else if(service.equalsIgnoreCase("XfinityEvents")||service.equalsIgnoreCase("XfinityEvents-Insight"))
						               {
						            		 Application.showMessage("Validation is Successfull with Xfinity::"+" "+service);
							            	 v3=1;
						               }
				                else
				               {
				                	v3=0;
				                	c.report("service is not validated as " +service);
		            		 }  
		            		 }
		            	}
		            	 
		            	 else
		            	 {
		            		 // for only one group
		            		 String group1= lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
		            		 String xfinity= lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[2]");
				                Application.showMessage("group1 is::"+group1); 
			            	
				                if(group1 ==null)
				                {
				            	Application.showMessage("Group1 is NULL");
					            //continue;
				                }
				                else
				                {
				            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
				            	    if(group1.trim().equals(c.getValue("ExistingGroup").trim()))
					                 {
					            	 Application.showMessage("*******Validation Point 2 :: GroupTier********");
					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
					            	 v3=1;
					                 }
					                else if (group1.equalsIgnoreCase("XfinityEvents")||group1.equalsIgnoreCase("XfinityEvents-Insight"))
					               {
					                	Application.showMessage("*******Validation Point 2 :: GroupTier********");
						            	 Application.showMessage("Validation is Successfull with xfinity::"+" "+group1);
						            	 v3=1;	
					               }
					                else{
					                	v3=0;
					            	 c.report("group1 at Recieve Xml not Validated as "+group1);
					               } }
				            	    if(xfinity ==null)
					                {
					            	Application.showMessage("Group1 is NULL");
						            //continue;
					                }
					                else
					                {
					            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+xfinity);
					            	    if(xfinity.equals(c.getValue("ExistingGroup")))
						                 {
						            	 Application.showMessage("*******Validation Point 2 :: GroupTier********");
						            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+xfinity);
						            	 v3=1;
						                 }
						                else if (xfinity.equalsIgnoreCase("XfinityEvents")||xfinity.equalsIgnoreCase("XfinityEvents-Insight"))
						               {
						                	Application.showMessage("*******Validation Point 2 :: GroupTier********");
							            	 Application.showMessage("Validation is Successfull with xfinity::"+" "+xfinity);
							            	 v3=1;	
						               }
						                else{
						                	v3=1;
						            	// c.report("group1 at Recieve Xml not Validated as "+xfinity);
					               }
				                }
				                
		            		 
		            	 }
			            String accountId_getAcc= lC.nodeFromKey(recievedata,"<accountId>","</accountId>");
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
	      //   st.close();
		}	
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	}

	//----------
	
	public String clsGetAccout_Validatecos_sim(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException
	{
		
		Thread.sleep(100000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     String rscallpresent="true";
	    // c.setValue("getAccount_CLS","true");
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****CLS GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     List<String> grpserv=extractServices(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"),input,c);
	     if(grpserv.size()==3)
	     {
	    	 c.setValue("ExistingGroup",grpserv.get(0));
		     c.setValue("ExistingService", grpserv.get(1));
		     c.setValue("ExistingService1", grpserv.get(2));
		     c.setValue("IsMultiExist", "true");
		     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
		     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
		     Application.showMessage("ExistingService1::"+c.getValue("ExistingService1"));
		     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	     }
	     else
	    	 {
	    	 ResultMap= lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
	    	
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	    
	     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
	     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
	     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	    	 }
	   //  lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("getAccount_CLS","false");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccountSim"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccountSim"));


	     
	     
		try
		{
			//Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
			rs=lC.reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{

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
		         	String 	 senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
		         	String 	 recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
		         	String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
		            String id= lC.nodeFromKey(senddata,"<resourceId>","</resourceId>");
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
		            	
		            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
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
			                String centralStationAccountNumber= lC.nodeFromKey(recievedata,"<centralStationAccountNumber>","</centralStationAccountNumber>");
			                Application.showMessage("centralStationAccountNumber is::"+centralStationAccountNumber); 
		            	
			                if(centralStationAccountNumber ==null)
			                {
			            	Application.showMessage("centralStationAccountNumber is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage("centralStationAccountNumber from Recieve Xml  from GetAccount is ::"+" "+centralStationAccountNumber);
			            	    Application.showMessage("Ccentral number from Order update call :::"+c.getValue("CcentralNum"));
			            	    if(centralStationAccountNumber.trim().equals(c.getValue("CcentralNum")))
			            	    //if(centralStationAccountNumber.trim().equals("C99239914"))
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
		            	int count=lC.getProductDetails(input, c, recievedata,"group");
		            	 if(c.getValue("IsMultiExist").equals("true"))
		            	 {
		            		 if(count>=3)
		            		 {
		            			 String xfinity=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
		            			 String service=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[2]");
		            			 String group=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[3]");
		            			 Application.showMessage("GroupTier from receiveXML from GetAccount is::"+ group);
		            			 Application.showMessage("ServiceTier from receiveXML from GetAccount is::"+ service);
		            			 Application.showMessage("*******Validation Point 1 :: xfinity********");
		            			 if(xfinity.equalsIgnoreCase("XfinityEvents")||xfinity.equalsIgnoreCase("XfinityEvents-Insight"))
		            			 {
		            				 v3=1;
		            				 Application.showMessage(" xfinity from receiveXML from GetAccount is::"+ xfinity);
		            			 }
		            			 else if(xfinity.trim().equals(c.getValue("ExistingGroup").trim()) || xfinity.trim().equals(c.getValue("ExistingService").trim())  || xfinity.trim().equals(c.getValue("ExistingService1").trim()))
		            	
			                {
		            				 v3=1;
		            				 Application.showMessage(" group from receiveXML from GetAccount is::"+ xfinity);
			                }
			                else
			                {
		            				 c.report("Xfinity not validates from  GetAccount is::"+ xfinity);
		            			 }
		            			 Application.showMessage("*******Validation Point 2 :: group********");
		            			          			 
				            	    if(group.trim().equals(c.getValue("ExistingGroup").trim()) || group.trim().equals(c.getValue("ExistingService").trim()) || group.trim().equals(c.getValue("ExistingService1").trim()))
				                 {
				            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group);
				            	 v3=1;
				                 }
				            	    else if(group.equalsIgnoreCase("XfinityEvents")||group.equalsIgnoreCase("XfinityEvents-Insight"))
				               {
				            	    	v3=1;
				            	    	Application.showMessage("Validation is Successfull with GroupTier::"+" "+group);
				               }
				            	    else
				            	    {
				            	    	 c.report("group not validates from  GetAccount is::"+ group);
			                }
				            	    Application.showMessage("*******Validation Point 3 :: service1********");
			                
				            	    if(service.trim().equals(c.getValue("ExistingService").trim()) || service.trim().equals(c.getValue("ExistingService1").trim()) || service.trim().equals(c.getValue("ExistingGroup").trim()))
				                 {
			                
				            	 Application.showMessage("Validation is Successfull with service1::"+" "+service);
				            	 v3=1;
				                 }else if(service.equalsIgnoreCase("XfinityEvents")||service.equalsIgnoreCase("XfinityEvents-Insight"))
			                {
				                	 Application.showMessage("Validation is Successfull with Xfinity::"+" "+service);
					            	 v3=1; 
			                }
			                else
			                {
				                	v3=0;
				                	c.report("service is not validated as " +service);
		            		 } }
		            		 else
		            		 {
		            			 String service=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
		            			 String group=lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[2]");
		            			 Application.showMessage("GroupTier from receiveXML from GetAccount is::"+ group);
		            			 Application.showMessage("ServiceTier from receiveXML from GetAccount is::"+ service);
		            			 Application.showMessage("*******Validation Point 2 :: group********");
			          			 
				            	    if(group.trim().equals(c.getValue("ExistingGroup").trim()) || group.trim().equals(c.getValue("ExistingService").trim()))
					                 {
					            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group);
					            	 v3=1;
					                 }
				            	    else if(group.equalsIgnoreCase("XfinityEvents")||group.equalsIgnoreCase("XfinityEvents-Insight"))
				                 {
				            	    	Application.showMessage("*******Validation Point 4 :: GroupTier********");
						            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group);
				            	 v3=1;
				                 }
				                else
				               {
				                	v3=1;
				            	    //	c.report("group is not validated as " +group);
			                }
				            	    Application.showMessage("*******Validation Point 3 :: service1********");
			                
				            	    if(service.trim().equals(c.getValue("ExistingService").trim()) || service.trim().equals(c.getValue("ExistingGroup").trim()))
				                 {
			                
				            	 Application.showMessage("Validation is Successfull with service1::"+" "+service);
				            	 v3=1;
				                 }
				            	    else if(service.equalsIgnoreCase("XfinityEvents")||service.equalsIgnoreCase("XfinityEvents-Insight"))
						               {
						            		 Application.showMessage("Validation is Successfull with Xfinity::"+" "+service);
							            	 v3=1;
						               }
				                else
				               {
				                	v3=0;
				                	c.report("service is not validated as " +service);
		            		 }  
		            		 }
		            	}
		            	 
		            	 else
		            	 {
		            		 // for only one group
		            		 String group1= lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group");
		            		 String xfinity= lC.GetValueByXPath(receiveDataTrim, "/restResponse/response/ClientResponse/responseDS/GetAccountResponse/account/group[2]");
				                Application.showMessage("group1 is::"+group1); 
			            	
				                if(group1 ==null)
				                {
				            	Application.showMessage("Group1 is NULL");
					            //continue;
				                }
				                else
				                {
				            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
				            	    if(group1.trim().equals(c.getValue("ExistingGroup").trim()))
					                 {
					            	 Application.showMessage("*******Validation Point 2 :: GroupTier********");
					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
					            	 v3=1;
					                 }
					                else if (group1.equalsIgnoreCase("XfinityEvents")||group1.equalsIgnoreCase("XfinityEvents-Insight"))
					               {
					                	Application.showMessage("*******Validation Point 2 :: GroupTier********");
						            	 Application.showMessage("Validation is Successfull with xfinity::"+" "+group1);
						            	 v3=1;	
					               }
					                else{
					                	v3=0;
					            	 c.report("group1 at Recieve Xml not Validated as "+group1);
					               } }
				            	    if(xfinity ==null)
					                {
					            	Application.showMessage("Group1 is NULL");
						            //continue;
					                }
					                else
					                {
					            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+xfinity);
					            	    if(xfinity.equals(c.getValue("ExistingGroup")))
						                 {
						            	 Application.showMessage("*******Validation Point 2 :: GroupTier********");
						            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+xfinity);
						            	 v3=1;
						                 }
						                else if (xfinity.equalsIgnoreCase("XfinityEvents")||xfinity.equalsIgnoreCase("XfinityEvents-Insight"))
						               {
						                	Application.showMessage("*******Validation Point 2 :: GroupTier********");
							            	 Application.showMessage("Validation is Successfull with xfinity::"+" "+xfinity);
							            	 v3=1;	
						               }
						                else{
						                	v3=1;
						            	// c.report("group1 at Recieve Xml not Validated as "+xfinity);
					               }
				                }
				                
		            		 
		            	 }
			            String accountId_getAcc= lC.nodeFromKey(recievedata,"<accountId>","</accountId>");
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
	      //   st.close();
		}	
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	}

	
	//----------
	
	public void cosLogicFlow(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
	     
	     if(c.getValue("NewGroup").equals(c.getValue("ExistingGroup")))
	     {
	    	 if(c.getValue("ExistingService").equals(" "))
	    	 {
	    		c.setValue("IsAddGroup", "true");  // only AddGroup call needs to be validated
	    		Application.showMessage("Calls to be validated is:: AddGroup");
	    		AddGroup_Validate(input, c);
	    		//------------------------------
	    		c.setValue("IsRemoveGroup", "false");
	    	    c.setValue("IsUpdateTier", "false");
	    		
	    		
	    	 }
	    	 else if(c.getValue("NewService").equals(" "))
	    	 {
	    		 c.setValue("IsRemoveGroup", "true");  // only RemoveGroup call needs to be validated
	    		 Application.showMessage("Calls to be validated is:: RemoveGroup");
	    		 RemoveGroup_Validate(input, c);
	    		//------------------------------
		    	 c.setValue("IsAddGroup", "false");
		    	 c.setValue("IsUpdateTier", "false");
	    	 }
	    	 else
	    	 {
	    		 c.setValue("IsAddGroup", "true");
	    		 c.setValue("IsRemoveGroup", "true"); // both Add and RemoveGroup call needs to be validated
	    		 Application.showMessage("Calls to be validated is:: AddGroup and RemoveGroup");
	    		 AddGroup_Validate(input, c);
	    		 RemoveGroup_Validate(input, c);
	    		//-------------------------------	
		    	  c.setValue("IsUpdateTier", "false");
	    	 }
	     }
	     
	     else
	     {
	    	 if(c.getValue("ExistingService").equals(c.getValue("NewService")))
	    	 {
	    		 c.setValue("IsUpdateTier", "true");
	    		 Application.showMessage("Calls to be validated is:: UpdateTier");
	    		 updateTier_Validate(input, c);
	    		//------------------------------
		    		c.setValue("IsRemoveGroup", "false");
		    	    c.setValue("IsAddGroup", "false");
	    		 
	    	 }
	    	 else if(c.getValue("NewService").equals(" "))
	    	 {
	    		 c.setValue("IsRemoveGroup", "true"); 
	    		 c.setValue("IsUpdateTier", "true"); 
	    		 Application.showMessage("Calls to be validated is:: UpdateTier and RemoveGroup");
	    		 RemoveGroup_Validate(input, c);
	    		 updateTier_Validate(input, c);
	    		//------------------------------
		    	  c.setValue("IsAddGroup", "false");
	    		 	 
	    	 }
	    	 
	    	 else if(c.getValue("ExistingService").equals(" "))
	    	 {
	    		 c.setValue("IsAddGroup", "true"); 
	    		 c.setValue("IsUpdateTier", "true");
	    		 Application.showMessage("Calls to be validated is:: UpdateTier and AddGroup");
	    		 AddGroup_Validate(input, c);
	    		 updateTier_Validate(input, c);
	    		//------------------------------
		    	 c.setValue("IsRemoveGroup", "false");
		    
	    		 	 
	    	 }
	    	 
	    	 else
	    	 {
	    		 c.setValue("IsAddGroup", "true"); 
	    		 c.setValue("IsUpdateTier", "true"); 
	    		 c.setValue("IsRemoveGroup", "true"); 
	    		 Application.showMessage("Calls to be validated is:: UpdateTier , RemoveGroup and AddGroup");
	    		 AddGroup_Validate(input, c);
	    		 RemoveGroup_Validate(input, c);
	    		 updateTier_Validate(input, c);
	    	 }
	     }
	}
	
	//-------------------------
	 public  List<String> extractServices(String services,Object input,ScriptingContext c)
	   {
		  
		   String symbol="+";
		   String simSymbol="|";
		   String value= "/";
		   if(services.contains(simSymbol))
		   {
		   String check=services.replace(simSymbol, value);
		   List<String> Groupservices=Arrays.asList(check.split("/"));
		   int size=Groupservices.size();
		   c.setValue("ExistingGroup",Groupservices.get(0));
		   for(int i=0; i<size;i++)
		   {
			   Application.showMessage("Services "+i+" are::"+Groupservices.get(i).trim());
		   }
		   return Groupservices; 
		   }
		  
		   else if(services.contains(symbol))
		   {
			   String check=services.replace(symbol, value);
			   List<String> Groupservices=Arrays.asList(check.split(" / "));
			   int size=Groupservices.size();
			   c.setValue("ExistingGroup",Groupservices.get(0));
			   for(int i=0; i<size;i++)
			   {
				   Application.showMessage("Services "+i+" are::"+Groupservices.get(i).trim());
			   }
			   return Groupservices; 
		   }
		   else 
		   {
			   
			   List<String> Groupservices=new ArrayList<String>();
			   Groupservices.add(services);
			   int size=Groupservices.size();
			   c.setValue("ExistingGroup",Groupservices.get(0));
			   for(int i=0; i<size;i++)
			   {
				   Application.showMessage("Services "+i+" are::"+Groupservices.get(i).trim());
			   }
			   return Groupservices; 
		   }
		   
	   }
	
	//-------------------------
	
	public void cosLogicFlow_CLS(Object input, ScriptingContext c,String ExistingService,String NewService) throws IOException, ClassNotFoundException, InterruptedException
	{
		
	     List<String> existingservice=extractServices(ExistingService, input, c);
	     List<String> newservice=extractServices(NewService, input, c);
	     List<String> removeaccountgroup=new ArrayList<String>();
	     List<String> addaccountgroup=new ArrayList<String>();
	     List<String> earlierservice=new ArrayList<String>();
	     
	     if(existingservice.get(0).trim().equalsIgnoreCase(newservice.get(0).trim()))
	     {
	    	 Application.showMessage("Both the Tiers are same...No need of UpdateTierCall Validation");
	     }
	     else
	     {
	    	 Application.showMessage("calls to be Validated is UpdateTierGroup for the Tier"+newservice.get(0));
	    	 updateTier_Validate_CLS(input,  c,newservice.get(0));
	     }
	     if(existingservice.size()>=newservice.size())
	     {
	    	for(int i=1;i<existingservice.size();i++)
	    	{
	    		Application.showMessage("newservice.size()="+newservice.size());
	    		
	    		if(newservice.size()==1)
	    		{
	    			
	    			removeaccountgroup.add(existingservice.get(i));
	    		}
	    		else
	    			{
	    			for(int j=1;j<newservice.size();j++)
	    			
	    		{
	    			if(existingservice.get(i).trim().equalsIgnoreCase(newservice.get(j).trim()))
	    			{
	    				if(removeaccountgroup.contains(existingservice.get(i).trim()))
	    				{
	    					removeaccountgroup.remove(existingservice.get(i).trim());
	    				}
	    				if(addaccountgroup.contains(newservice.get(j).trim()))
	    				{
	    					addaccountgroup.remove(newservice.get(j).trim());
	    				}
	    				earlierservice.add(existingservice.get(i).trim());
	    			}
	    			else
	    			{
	    				if(earlierservice.contains(existingservice.get(i).trim()))
	    				{
	    					addaccountgroup.add(newservice.get(j).trim());
	    				}
	    				else if(earlierservice.contains(newservice.get(j).trim()))
	    				{
	    				removeaccountgroup.add(existingservice.get(i).trim());
	    				}
	    				else
	    				{
	    				addaccountgroup.add(newservice.get(j).trim());
	    				removeaccountgroup.add(existingservice.get(i).trim());
	    				}
	    			}
	    		}
	    			}
	    	}
	     }
	     else
	    	 {for(int k=1;k<newservice.size();k++)
	    	{
	    		if(existingservice.size()==1)
	    		{
	    			addaccountgroup.add(newservice.get(k));	
	    		}
	    		else
	    		{
	    		for(int l=1;l<existingservice.size();l++)
	    		{
	    			if(newservice.get(k).trim().equalsIgnoreCase(existingservice.get(l).trim()))
	    			{
	    				if(addaccountgroup.contains(newservice.get(k).trim()))
	    				{
	    					addaccountgroup.remove(newservice.get(k).trim());
	    				}
	    				if(removeaccountgroup.contains(existingservice.get(l).trim()))
	    				{
	    					removeaccountgroup.remove(existingservice.get(l).trim());
	    				}
	    			
	    			}
	    			else
	    			{
	    				addaccountgroup.add(newservice.get(k).trim());
	    				removeaccountgroup.add(existingservice.get(l).trim());
	    			}
	    		}
	    		}
	    	}
	    	
	    	 }
	     for(int m=0;m<addaccountgroup.size();m++)
	     {
	    	 String addServices=addaccountgroup.get(m).trim();
	    	 Application.showMessage("Calls to be Validated is addAccountGroup for the service"+addaccountgroup.get(m));
	    	 AddGroup_Validate_CLS(input,  c,addServices);
	     }
	     for(int n=0;n<removeaccountgroup.size();n++)
	     {
	    	 String removeservices=removeaccountgroup.get(n).trim();
	    	 Application.showMessage("Calls to be Validated is removeAccountGroup for the service"+removeaccountgroup.get(n));
	    	 RemoveGroup_Validate_CLS(input,  c,removeservices);
	    	 }
	    	 
	    		 
	    	 
	    	 
	     }
	
	
	
	
	//--------------------------
	public String updateTier_Validate_CLS(Object input, ScriptingContext c,String tier) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	    // c.setValue("UpdateTier_CLS","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****updateTier_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     String rscallpresent="true";
	     //lC.findThinktimeOperationRTP(input, c);
	    // c.setValue("UpdateTier_CLS","false"); 
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_updateAccountTierGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_updateAccountTierGroup"));


	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/updateAccountTierGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{
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
		            String senddata = lC.clsRemoveAsciiCharacter(senddatacls);		         
		            String recievedata = lC.clsRemoveAsciiCharacter(recievedatacls);   
		         		           
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
		      
			            String errorCode= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
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
			            	 if(tierGroup.equalsIgnoreCase(tier))
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
	       //  st.close();
		}	
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	}
	
	
	//-------------
	
	public String AddGroup_Validate_CLS(Object input, ScriptingContext c,String service) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****AddGroup_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_addAccountGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_addAccountGroup"));


	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/addAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			
		     rs=lC.reduceThinkTimeRTP(input, c);
		     if(rs == null)
         	{
         		rscallpresent = "false";
         		
         	
         		
         	}
         	else
{
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
		            String senddata = lC.clsRemoveAsciiCharacter(senddatacls);		         
		            String recievedata = lC.clsRemoveAsciiCharacter(recievedatacls); 
		            Application.showMessage("SEND XML is ::\n"+senddata);   
		            Application.showMessage("receive XML is ::\n"+recievedata); 
		         		           
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
		      
		            	    String errorCode= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
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
				            Application.showMessage("AddAccountGroup is::"+group); 
				             if(group==null)
				             {
					            c.report("Send group is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("group from Send Xml  from AddAccountGroup is ::"+" "+group);
				            	 if(group.trim().equalsIgnoreCase(service.trim()))
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
	       //  st.close();
		}
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	}
	
	
	//-------------
	public String RemoveGroup_Validate_CLS(Object input, ScriptingContext c,String service) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****RemoveGroup_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_removeAccountGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_removeAccountGroup"));

	     
	  
	     
		try
		{
			String Time= c.get("BaseTime").toString();
		    
			// Statement st =lC. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/removeAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{
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
			            String senddata = lC.clsRemoveAsciiCharacter(senddatacls);		         
			            String recievedata = lC.clsRemoveAsciiCharacter(recievedatacls);   
			            Application.showMessage("Send XML is ::\n"+senddata);     
			            Application.showMessage("RECIEVE XML is ::\n"+recievedata);     
		            String id= lC.nodeFromKey(recievedata,"<accountId>","</accountId>");
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
		      
		            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
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

			            String group= lC.nodeFromKey(senddata,"<payLoad>","</payLoad>");
			            Application.showMessage("removeAccountGroup is::"+group); 
			             if(group==null)
			             {
				            c.report("Send group is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("group from Send Xml  from removeAccountGroup is ::"+" "+group);
			            	 if(group.contains(service))
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
	        // st.close();
		}	
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	}
	
	//-----------
	
	public void updateTier_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	    // c.setValue("UpdateTier","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****updateTier_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     
	  //   lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("UpdateTier","false");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_updateAccountTierGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_updateAccountTierGroup"));
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/updateAccountTierGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
	                          
	            	//Sruthi
	            	String senddatacls = lC.extractXml(rs,xmldata2);                                        
	            	String recievedatacls = lC.extractXml(rs,xmldata1);      
	            	String senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
	            	String recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
	            	//Sruthi
		         		           
		            String id= lC.nodeFromKey(senddata,"<resouceId>","</resouceId>");
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
		      
			            String errorCode= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
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
			            	 if(tierGroup.equals(c.getValue("NewGroup")))
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
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	
	public void AddGroup_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	    // c.setValue("addAccountGroup","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****AddGroup_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	    // lC.findThinktimeOperationRTP(input, c);
	    // c.setValue("addAccountGroup","false"); 
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_addAccountGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_addAccountGroup"));
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/addAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
	                 //Sruthi         
	            	String senddatacls = lC.extractXml(rs,xmldata2);                                        
	            	String recievedatacls = lC.extractXml(rs,xmldata1);      
	            	String senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
	            	String recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);    
	            	 //Sruthi           
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
		      
		            	    String errorCode= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
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
				            	 if(group.equals(c.getValue("NewService")))
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
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	
	public void RemoveGroup_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	    // c.setValue("removeAccountgroup","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****RemoveGroup_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	    // lC.findThinktimeOperationRTP(input, c);
	     //c.setValue("removeAccountgroup","false"); 
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_removeAccountGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_removeAccountGroup"));
	     
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/removeAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			
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
	                          
	            	//Sruthi
	            	String senddatacls = lC.extractXml(rs,xmldata2);                                        
	            	String recievedatacls = lC.extractXml(rs,xmldata1);      
	            	String senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
	            	String recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
	            	//Sruthi
		         		           
		            String id= lC.nodeFromKey(senddata,"<resourceId>","</resourceId>");
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
		      
		            	String errorCode= lC.nodeFromKey(recievedata,"<errorCode>","</errorCode>");
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
			            	 if(group.equals(c.getValue("ExistingService")))
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
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	
	public String orderUpdateCos_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	   //  c.setValue("OrderUpdate","true");
	     String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********orderUpdateCos_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	    // lC.findThinktimeOperationRTP(input, c);
	    // c.setValue("OrderUpdate","false");  
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
				            	// HandleInvalidServices hI=new HandleInvalidServices();
				            	 String sNewService= c.getValue("RTPNormalScenariosdS", "RTP_InputParams: NEWSERVICE").trim();
				            	 if(Service.equals(sNewService.trim()))
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
		 		            	 if(Service.equals(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE").trim()))
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
				            	 Application.showMessage("Validation is Successfull with Order Type::"+" "+ordType);
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
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	 }
//----
	
	public void UpdateAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException 
	{
	                Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	                ResultSet  rs;
	                int callFound=0,v1=0;
	                String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("----------------------------------------------------");
	     Application.showMessage("*****Update Account_Validate _Validate Function******");       
	     Application.showMessage("-----------------------------------------------------");
	         
	                try
	                {
	                                Statement st =lC. dBConnect(input, c); 
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/restoreAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
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
	                                String senddataTrim=lC.RemoveNameSpaces(senddata);
	                                Application.showMessage("receiveDataTrim:::"+receiveDataTrim);
	                                Application.showMessage("senddataTrim:::"+senddataTrim);
	                            String id= lC.nodeFromKey(senddata,"<ucontrolsrv:accountId>","</ucontrolsrv:accountId>");
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
	                      
	                                String fname= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/firstName");
	                                Application.showMessage("fname is ::"+fname);
	                                 
	                                String lname= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/lastName");
	                                Application.showMessage("lname is ::"+lname);
	                                                             
	                                String PhoneNumber= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/phoneNumber");
	                                Application.showMessage("PhoneNumber is ::"+PhoneNumber);           
	                                
	                                String address1= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/address/address1");
	                                Application.showMessage("address1 is ::"+address1);
	                                
	                                                           
	                                String city= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/address/city");
	                                Application.showMessage("city is ::"+city); 
	                                
	                                String Province= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/address/province");
	                                Application.showMessage("Province is ::"+Province); 
	                                
	                               
	                                String cCentral= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/centralStationAccountNumber");
	                                Application.showMessage("cCentral Number is ::"+cCentral); 
	                                
	                                
	                                
	                                String PortalSSoID= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/portalUserSSOId");
	                                Application.showMessage("PortalSSoID is ::"+PortalSSoID); 
	                                
	                                String Product= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/group");
	                                Application.showMessage("Product is ::"+Product); 
	                                
	                                String group= lC.GetValueByXPath(senddataTrim, "/UpdateAccountRequest/account/group[2]");
	                                Application.showMessage("group is ::"+group); 
	                                
	                                
	                                
	                                
	                                
	                                
	                             
	                            break;
	                            }
	             }
	         }
	         if(callFound==1)
	         {
	                Application.showMessage("WebService Call :: updateAccount call is Success[All validation points are Success]");
	         }
	         else
	         {
	                 c.put("result", "false");
	                 c.report("WebService Call ::updateAccount is Failed with Validation Errors");
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

	
	//-----
	
	public void orderUpdateAddress_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
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
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
					            	 v1=1;
					            	// c.report("billingOrderId at Send Xml not Validated as "+billingOrderId);
					             }
				            }
			 	           
			 	         
		 	            
		 	            }
		 	            else
		 	            {
		 	            	v1=1;
		 	            	
		 	            	
		 	                
		 	 	            
		 	 	           
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
				            	 Application.showMessage("Validation is Successfull with Order Type::"+" "+ordType);
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
	         if(v1*callFound*v2*v3 ==1)
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

	
	//----
}
