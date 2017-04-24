import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class CancelClass 
{
	LibraryRtp lR=new LibraryRtp();
	RTP_ValidationsClass vC = new RTP_ValidationsClass();
	
	public String getAccout_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;

String rscallpresent="true";
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
		 Application.showMessage("-------------------------------------------------");
		 Application.showMessage("-------------------------------------------------");
	     
	     ResultMap= lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	    
	     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
	     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
	     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	    
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccount"));
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	     //    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
	            	String senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
	            	String recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
	            	Application.showMessage("recievedata is ::\n"+recievedata);
	            	Application.showMessage("senddata is ::\n"+senddata); 
					Application.showMessage("senddatacls is ::\n"+senddatacls); 
		         		           
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
		            	
		            	
		            	 if(c.getValue("IsMultiExist").equals("true"))
		            	 {
			 	            String group1= lC.nodeFromKey(recievedata,"<group>","</group>");
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
			                
			                
			                String service1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>").trim();
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
		            		 String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:internal>").trim();
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
			            String accountId_getAcc= lC.nodeFromKey(recievedata,"<accountId>","</:accountId>");
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
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	
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
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
	                          
		            String senddata = lC.extractXml(rs,xmldata2);           
		            String recievedata = lC.extractXml(rs,xmldata1);      
		          	           
		            String legacyID= lC.nodeFromKey(senddata,"<lt:legacyID>","</lt:legacyID>");	            
		            Application.showMessage("legacyID is ::"+legacyID); 
		            if(legacyID.equals(c.getValue("ACC_input")))
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
		 	            
		 	            String streetName= lC.nodeFromKey(recievedata,"<typ:streetName>","</typ:streetName>").trim();
		 	            Application.showMessage("streetName is :: "+streetName); 
		 	            c.setValue("StreetName", streetName);
		 	            
		 	            String streetSuffix= lC.nodeFromKey(recievedata,"<typ:streetSuffix>","</typ:streetSuffix>").trim();
		 	            Application.showMessage("streetSuffix is :: "+streetSuffix); 
		 	             
		 	            String city= lC.nodeFromKey(recievedata,"<typ:city>","</typ:city>").trim();
		 	            Application.showMessage("city is :: "+city); 
		 	            c.setValue("City", city);
		 	           
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
	
	
	public String processHomeSecurityInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(4000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";

String rscallpresent="true";
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
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
		          	           
		            String accountNumber_DDS= lC.nodeFromKey(senddata,"<messagesDDS:accountNumber>","</messagesDDS:accountNumber>");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_DDS); 	           
		            if(accountNumber_DDS.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_DDS);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	           
		 	           


		 	            String homeSecurityLOBStatus_DDS= lC.nodeFromKey(senddata,"<typesDDS:homeSecurityLOBStatus>","</typesDDS:homeSecurityLOBStatus>");
		 	            Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
		 	            if(homeSecurityLOBStatus_DDS==null)
			            {
				            c.report(" homeSecurityLOBStatus is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("homeSecurityLOBStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+homeSecurityLOBStatus_DDS);
			            	 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("DISCONNECTED"))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
				            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
				            	 v1=1;
				             }
				             else
				             {
				            	 Application.showMessage("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+homeSecurityLOBStatus_DDS);
				            	 continue;
				             }
			            }
		 	            
		 	            String permitRequired_DDS= lC.nodeFromKey(senddata,"<typesDDS:permitRequired>","</typesDDS:permitRequired>");
		 	            Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
		 	           
		 	         	 	            
		 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
		 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
		 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
		 	            
		 	            String responseStatus_DDS= lC.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/esd-directory/14.05/types\">","</typ:responseStatus>");
		 	            Application.showMessage("responseStatus is::"+responseStatus_DDS); 
		 	           if(responseStatus_DDS==null)
			            {
				            c.report(" responseStatus is NULL");
				           
			            }
			            else
			            {
			            	 Application.showMessage("responseStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+responseStatus_DDS);
			            	 if(responseStatus_DDS.equalsIgnoreCase("Success"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-responseStatus********");
				            	 Application.showMessage("Validation is Successfull with responseStatus::"+" "+responseStatus_DDS);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("responseStatus at Send Xml not Validated as "+responseStatus_DDS);
				             }
			            }
		 	                    

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2 ==1)
	         {
	         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
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
	
	
	public String deleteUnactivatedAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(40000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";

String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*******deleteUnactivatedAccount  Function********");       
	     Application.showMessage("-------------------------------------------------");
	     System.out.println("-------------------------------------------------");
	     System.out.println("*******deleteUnactivatedAccount  Function********");       
	     System.out.println("-------------------------------------------------");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("removeAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("removeAccount"));
	     System.out.println("OPERATIONValidation is "+(String) Operation.get("removeAccount"));
	     
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/deleteUnactivatedAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
	            	 System.out.println("Your Recieve XML is NULL \n");
	            	
	            	String senddata =lC.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            	 System.out.println("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	 System.out.println("Your SEND XML is NULL \n");	
	            	String recievedata=lC.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);    
	            	 System.out.println("RECIEVE XML is ::\n"+recievedata);    
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
		            System.out.println("id is ::"+id);
		            
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Suspend Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
	            		 System.out.println("Recieve XML is::  \n"+ recievedata);
	            		 System.out.println("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		 System.out.println("*******Validation Point 1 :: WebServicall-Suspend Call********");
	            		 System.out.println("Validation is Successfull with Input Account Number"+id);
	            		
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		            	String errorCode= lC.nodeFromKey(recievedata,"<errorcode>","</errorcode>");
			            Application.showMessage("errorCode is ::"+errorCode);
			            System.out.println("errorCode is ::"+errorCode);
			           
			            
			           
			            if(errorCode==null)
			            {
				            c.report("Send Xml errorCode is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+errorCode);
			            	 System.out.println("Account Number from Send Xml  from AddGroup is ::"+" "+errorCode);
				            	
			            	 if(errorCode.equals("0"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
				            	 System.out.println("*******Validation Point 2 :: WebServicall-errorCode********");
				            	 System.out.println("Validation is Successfull with errorCode::"+" "+errorCode);
				            	
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
				             }
			            }		

			            String errorMessage= lC.nodeFromKey(recievedata,"<errorMessage xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorMessage>");
			            Application.showMessage("errorMessage is::"+errorMessage); 
			            System.out.println("errorMessage is::"+errorMessage); 
			            
		               
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1 ==1)
	         {
	         	Application.showMessage("WebService Call :: Remove account is Success[All validation points are Success]");
	         	 System.out.println("WebService Call :: Remove account is Success[All validation points are Success]");
	   	     
	         }
	         else
	         {
	        	 c.report("WebService Call :: Remove account is Failed with Validation Errors");
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
	
	//As per remove account_cls
	public String removeAccount_Validate_CLS(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(40000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*******removeAccount_Validate_CLS Function********");       
	     Application.showMessage("-------------------------------------------------");
	     
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("removeAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("removeAccount"));


	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
			// Application.showMessage(" Entered");
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/removeAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
	         Application.showMessage("RS"+rs);
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
	            	 /*String senddata = lC.extractXml(rs,xmldata2);		         
			            String recievedata = lC.extractXml(rs,xmldata1);     */ 
			         		           
			           /* String id= lC.nodeFromKey(senddata,"<id>","</id>");*/
			            Application.showMessage("id is ::"+id);
			            Application.showMessage("id from Input is ::"+c.getValue("ACC_input"));
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Suspend Call********");
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

			            String errorMessage= lC.nodeFromKey(recievedata,"<errorMessage>","</errorMessage>");
			            Application.showMessage("errorMessage is::"+errorMessage); 
			            
		               
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1 ==1)
	         {
	         	Application.showMessage("WebService Call :: Suspend is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: Suspend is Failed with Validation Errors");
	         }
	       //  st.close();
			
	}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	}
	public String DisconnectAccount_CANCEL_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		 // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";

String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*******deleteUnactivatedAccount  Function********");       
	     Application.showMessage("-------------------------------------------------");
	     System.out.println("-------------------------------------------------");
	     System.out.println("*******deleteUnactivatedAccount  Function********");       
	     System.out.println("-------------------------------------------------");
	     
	     
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("DisconnectAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("DisconnectAccount"));
	     System.out.println("OPERATIONValidation is "+(String) Operation.get("DisconnectAccount"));
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/DisconnectAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
	            	System.out.println("Your Recieve XML is NULL \n");
	            	String senddata =lC.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            	System.out.println("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");
	            	System.out.println("Your SEND XML is NULL \n");
	            	String recievedata=lC.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            	System.out.println("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = lC.extractXml(rs,xmldata2);		         
		            String recievedata = lC.extractXml(rs,xmldata1);      
		         		           
		            String id= lC.nodeFromKey(senddata,"<accountNumber>","</accountNumber>");
		            Application.showMessage("Ccentral Number from Cops is ::"+id);
		            System.out.println("Ccentral Number from Cops is ::"+id);
		            
		            if(id.equals(c.getValue("CcentralNum")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Suspend Cops Call********");
	            		Application.showMessage("Validation is Successfull with CCentral Number"+id);
	            		System.out.println("Recieve XML is::  \n"+ recievedata);
	            		System.out.println("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		System.out.println("*******Validation Point 1 :: WebServicall-Suspend Cops Call********");
	            		System.out.println("Validation is Successfull with CCentral Number"+id);
	            	
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		            	String readBack= lC.nodeFromKey(recievedata,"<readBack>","</readBack>");
			            Application.showMessage("readBack is ::"+readBack);
			            System.out.println("readBack is ::"+readBack);
			           
			            
			           
			            if(readBack==null)
			            {
				            c.report("Send Xml readBack is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("readBack from Send Xml  from Suspend Cops  is ::"+" "+readBack);
			            	 System.out.println("readBack from Send Xml  from Suspend Cops  is ::"+" "+readBack);
				            	
			            	 if(readBack.trim().equals("COMCC9O2P5DELETEACCOUNT"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-readBack********");
				            	 Application.showMessage("Validation is Successfull with readBack::"+" "+readBack);
				            	 System.out.println("*******Validation Point 2 :: WebServicall-readBack********");
				            	 System.out.println("Validation is Successfull with readBack::"+" "+readBack);
				            	
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("readBack at Send Xml not Validated as "+readBack);
				             }
			            }	
			            
			            String result= lC.nodeFromKey(recievedata,"<result>","</result>");
			            Application.showMessage("result is ::"+result);
			            System.out.println("result is ::"+result);
			            if(result==null)
			            {
				            c.report("Send Xml result is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("result from Send Xml  from Suspend Cops  is ::"+" "+result);
			            	 System.out.println("result from Send Xml  from Suspend Cops  is ::"+" "+result);
				            	
			            	 if(result.trim().equals("OK"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-result********");
				            	 Application.showMessage("Validation is Successfull with result::"+" "+result);
				            	 System.out.println("*******Validation Point 2 :: WebServicall-result********");
				            	 System.out.println("Validation is Successfull with result::"+" "+result);
				            	
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("result at Send Xml not Validated as "+result);
				             }
			            }	
			            
			            String pc= lC.nodeFromKey(senddata,"<pc>","</pc>");
			            Application.showMessage("pc is ::"+pc);
			            System.out.println("pc is ::"+pc);
			            
			            if(pc==null)
			            {
				            c.report("Send Xml pc is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("pc from Send Xml  from Suspend Cops  is ::"+" "+pc);
			            	 System.out.println("pc from Send Xml  from Suspend Cops  is ::"+" "+pc);
			            	 if(pc.trim().equals("C9O2P5"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-pc********");
				            	 Application.showMessage("Validation is Successfull with pc::"+" "+pc);
				            	 System.out.println("*******Validation Point 2 :: WebServicall-pc********");
				            	 System.out.println("Validation is Successfull with pc::"+" "+pc);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("pc at Send Xml not Validated as "+pc);
				             }
			            }	

			            String dn= lC.nodeFromKey(senddata,"<dn>","</dn>");
			            Application.showMessage("dn is::"+dn); 
			            System.out.println("dn is::"+dn); 
			            
			            if(dn==null)
			            {
				            c.report("Send Xml dn is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("pc from Send Xml  from Suspend Cops  is ::"+" "+dn);
			            	 System.out.println("pc from Send Xml  from Suspend Cops  is ::"+" "+dn);
			            	 if(dn.trim().equals("COMC"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-dn********");
				            	 Application.showMessage("Validation is Successfull with dn::"+" "+dn);
				            	 System.out.println("*******Validation Point 2 :: WebServicall-dn********");
				            	 System.out.println("Validation is Successfull with dn::"+" "+dn);
				            	
				            	 v3=1;
				             }
				             else
				             {
				            	 c.report("dn at Send Xml not Validated as "+dn);
				             }
			            }	
		               
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1 ==1)
	         {
	         	Application.showMessage("WebService Call :: Suspend is Success[All validation points are Success]");
	         	System.out.println("WebService Call :: Suspend is Success[All validation points are Success]");
	 	       
	         }
	         else
	         {
	        	 c.report("WebService Call :: Suspend is Failed with Validation Errors");
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


	
	public String orderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";

String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********OrderUpdate_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	     System.out.println("-------------------------------------------------------------");
	     System.out.println("***********OrderUpdate_Validate Function**************");       
	     System.out.println("-------------------------------------------------------------");
	   
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
	     System.out.println("OPERATIONValidation is "+(String) Operation.get("orderUpdate")); 
	 	
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"'  order by creation_time desc)where rownum <= 20");
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
	            	System.out.println("Your Recieve XML is NULL \n");
	            	
	            	String senddata = lC.extractXml(rs,xmldata2);           
		           
		          	           
	                String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_ou); 
	 	           Application.showMessage("accountNumber from Install is ::"+c.getValue("ACC_input")); 
	 	          System.out.println("accountNumber is ::"+accountNumber_ou); 
	 	         System.out.println("accountNumber from Install is ::"+c.getValue("ACC_input")); 
	 	            
	 	           if(accountNumber_ou==null)
		            {
	 	        	   Application.showMessage("Send Xml billingOrderId is NULL");
	 	        	  System.out.println("Send Xml billingOrderId is NULL");
			            continue;
		            }
	 	           else if(accountNumber_ou.equals(c.getValue("ACC_input")))
	            	{
		            	
		            	Application.showMessage("SEND XML is :: \n"+senddata);
		            	System.out.println("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
	            		Application.showMessage("Validation is Successfull with House Number :"+accountNumber_ou);
	            		System.out.println("*******Validation Point 1 :: WebServicall-Order Update********");
	            		System.out.println("Validation is Successfull with House Number :"+accountNumber_ou);
	            		
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
		 	           System.out.println("vomInstance is ::"+vomInstance); 

		 	           
		 	            
		 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		 	            {
		 	            String billingOrderId= lC.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
		 	            Application.showMessage("billingOrderId is ::"+billingOrderId); 
		 	           System.out.println("billingOrderId is ::"+billingOrderId); 
		 	            
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
					            	 System.out.println("*******Validation Point 2 :: WebServicall-billingOrderId********");
					            	 System.out.println("Validation is Successfull with billingOrderId::"+" "+billingOrderId);
					            	
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("billingOrderId at Send Xml not Validated as "+billingOrderId);
					             }
				            }
			 	           
			 	            String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
			 	            Application.showMessage("Service is ::"+Service); 
			 	           System.out.println("Service is ::"+Service); 
			 	            
			 	           if(Service==null)
				            {
					            c.report("Send Xml Service is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
				            	 System.out.println("Service from Send Xml   is ::"+" "+Service);
					            	
				            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
					             {
					            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
					            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
					            	 System.out.println("*******Validation Point 5 :: WebServicall-Service********");
					            	 System.out.println("Validation is Successfull with Service::"+" "+Service);
					           
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
		 	 	          System.out.println("Service is ::"+Service); 
		 	 	            
		 	 	           if(Service==null)
		 		            {
		 			            c.report("Send Xml Service is NULL");
		 			            continue;
		 		            }
		 		            else
		 		            {
		 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
		 		            	System.out.println("Service from Send Xml   is ::"+" "+Service);
		 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
		 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
		 			            	System.out.println("*******Validation Point 5 :: WebServicall-Service********");
		 			            	System.out.println("Validation is Successfull with Service::"+" "+Service);
		 			            	
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
		 	           System.out.println("inputChannel is ::"+inputChannel);
		 	            
		 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
		 	            Application.showMessage("ordType is ::"+ordType);
		 	           System.out.println("ordType is ::"+ordType);
		 	            
		 	           if(ordType==null)
			            {
				            c.report("Send Xml ordType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
			            	 System.out.println("ordType from Send Xml   is ::"+" "+ordType);
				            	
			            	 if(ordType.equals("CAN"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
				            	 System.out.println("*******Validation Point 3 :: WebServicall-ordType********");
				            	 System.out.println("Validation is Successfull with lt_fireNumber::"+" "+ordType);
				            	
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("lt_fireNumber at Send Xml not Validated as "+ordType);
				             }
			            }	
		 	            
		 	            
		 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
		 	            Application.showMessage("customerType is ::"+customerType); 
		 	           System.out.println("customerType is ::"+customerType); 
		 	            
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
				            	 System.out.println("*******Validation Point 4 :: WebServicall-customerType********");
				            	 System.out.println("Validation is Successfull with customerType::"+" "+customerType);
				            	
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
	        	 c.setValue("IsDemi", "false");
	         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
	         	System.out.println("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
	  	      
	         }
	         else
	         {
	        	 c.setValue("IsDemi", "false");
	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
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
	
	public String getAccout_Validate100TDPEV(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String rscallpresent="true";
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     
	     ResultMap= lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	    
	     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
	     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
	     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccount"));
	     
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	         rs = lC.reduceThinkTimeRTP(input, c);
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
		            	
		            	
//		            	 if(c.getValue("IsMultiExist").equals("true"))
//		            	 {
//			 	            String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
//			                Application.showMessage("group1 is::"+group1); 
//		            	
//			                if(group1 ==null)
//			                {
//			            	Application.showMessage("Group1 is NULL");
//				            //continue;
//			                }
//			                else
//			                {
//			            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
//			            	    if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equals(c.getValue("ExistingService")))
//				                 {
//				            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
//				            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
//				            	 v3=1;
//				                 }
//				                else
//				               {
//				                	v3=0;
//				            	 c.report("group1 at Recieve Xml not Validated as "+group1);
//				               }
//			                }
//			                
//			                
//			                String service1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>").trim();
//			                Application.showMessage("service1 is::"+service1); 
//		            	
//			                if(service1 ==null)
//			                {
//			            	Application.showMessage("service1 is NULL");
//				            //continue;
//			                }
//			                else
//			                {
//			            	    Application.showMessage("service1 from Recieve Xml  from GetAccount is ::"+" "+service1);
//			            	    if(service1.equals(c.getValue("ExistingService")) || service1.equals(c.getValue("ExistingGroup")))
//				                 {
//				            	 Application.showMessage("*******Validation Point 2 :: service1********");
//				            	 Application.showMessage("Validation is Successfull with service1::"+" "+service1);
//				            	 v3=1;
//				                 }
//				                else
//				               {
//				                	v3=0;
//				            	 c.report("group1 at Recieve Xml not Validated as "+service1);
//				               }
//			                }
//			                
//			                
//		            	}
//		            	 
//		            	 else
//		            	 {
//		            		 // for only one group
//		            		 String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:internal>").trim();
//				                Application.showMessage("group1 is::"+group1); 
//			            	
//				                if(group1 ==null)
//				                {
//				            	Application.showMessage("Group1 is NULL");
//					            //continue;
//				                }
//				                else
//				                {
//				            	    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
//				            	    if(group1.equals(c.getValue("ExistingGroup")))
//					                 {
//					            	 Application.showMessage("*******Validation Point 2 :: GroupTier********");
//					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
//					            	 v3=1;
//					                 }
//					                else
//					               {
//					                	v3=0;
//					            	 c.report("group1 at Recieve Xml not Validated as "+group1);
//					               }
//				                }
//				                
//		            		 
//		            	 }
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
	         if(callFound*v1*v2*v4==1)
	         {
	         	Application.showMessage("WebService Call :: GetAccount is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: GetAccount is Failed with Validation Errors");
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
	public String clsGetAccout_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException
	{
		
		Thread.sleep(100000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****CLS GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     String rscallpresent="true";
	     ResultMap= lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	    
	     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
	     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
	     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccount"));

	     
	     
		try
		{
			//Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
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
		            Application.showMessage("receiveDataTrim"+receiveDataTrim);
		            
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
		                	Application.showMessage("V2 is 1");
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
		            			 else if(xfinity.trim().equals(c.getValue("ExistingGroup")) || xfinity.trim().equals(c.getValue("ExistingService")))
		            	
			                {
		            				 v3=1;
		            				 Application.showMessage(" group from receiveXML from GetAccount is::"+ xfinity);
			                }
			                else
			                {
		            				 c.report("Xfinity not validates from  GetAccount is::"+ xfinity);
		            			 }
		            			 Application.showMessage("*******Validation Point 2 :: group********");
		            			          			 
				            	    if(group.trim().equals(c.getValue("ExistingGroup")) || group.trim().equals(c.getValue("ExistingService")))
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
			                
				            	    if(service.equals(c.getValue("ExistingService")) || service.equals(c.getValue("ExistingGroup")))
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
			          			 
				            	    if(group.trim().equals(c.getValue("ExistingGroup")) || group.trim().equals(c.getValue("ExistingService")))
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
				                	v3=0;
				            	    	c.report("group is not validated as " +group);
			                }
				            	    Application.showMessage("*******Validation Point 3 :: service1********");
			                
				            	    if(service.equals(c.getValue("ExistingService")) || service.equals(c.getValue("ExistingGroup")))
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
				            	    if(group1.equals(c.getValue("ExistingGroup")))
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
					            	    Application.showMessage("Xfinity from Recieve Xml  from GetAccount is ::"+" "+xfinity);
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
						            	 //c.report("group1 at Recieve Xml not Validated as "+xfinity);
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
}
