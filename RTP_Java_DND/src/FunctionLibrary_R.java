import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;

/*
 * This Class file is developed and maintained by Ramya Upadhyaya 
 * Comcast NT id:: rupadh002c
 * 
 */

public class FunctionLibrary_R 
{
	
	public static String rowmsg= null;
	public void processHomeSecurityInfo_ValidateSu(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' order by creation_time desc)where rownum <= 100");
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
		            if(accountNumber_DDS.equals(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber")))
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
		      
		 	           
		 	            String homeSecurityLOBStatus_DDSsus= lC.nodeFromKey(senddata,"<typesDDS:homeSecurityLOBStatus>","</typesDDS:homeSecurityLOBStatus>");
		 	            Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDSsus); 
		 	            if(homeSecurityLOBStatus_DDSsus==null)
			            {
				            c.report(" homeSecurityLOBStatus is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("homeSecurityLOBStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+homeSecurityLOBStatus_DDSsus);
			            	 if(homeSecurityLOBStatus_DDSsus.equalsIgnoreCase("SUSPENDED"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-homeSecurityLOBStatus********");
				            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDSsus);
				            	 v1=1;
				             }
				             else
				             {
				            	 Application.showMessage("Not Validated homeSecurityLOBStatus_DDS as SUSPENDED");
				            	Application.showMessage("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+homeSecurityLOBStatus_DDSsus);
				            	 continue;
				             }
			            }
		 	            
//		 	            String permitRequired_DDS= lC.nodeFromKey(senddata,"<typesDDS:permitRequired>","</typesDDS:permitRequired>");
//		 	            Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
//		 	           
//		 	         	 	            
//		 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/12.06/messages\">","</mes:authorizationGuid>");
//		 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
//		 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
//		 	            
//		 	            String responseStatus_DDS= lC.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/esd-directory/12.06/types\">","</typ:responseStatus>");
//		 	            Application.showMessage("responseStatus is::"+responseStatus_DDS); 
//		 	           if(responseStatus_DDS==null)
//			            {
//				            c.report(" responseStatus is NULL");
//				           
//			            }
//			            else
//			            {
//			            	 Application.showMessage("responseStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+responseStatus_DDS);
//			            	 if(responseStatus_DDS.equalsIgnoreCase("Success"))
//				             {
//				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-responseStatus********");
//				            	 Application.showMessage("Validation is Successfull with responseStatus::"+" "+responseStatus_DDS);
//				            	 v2=1;
//				             }
//				             else
//				             {
//				            	 c.report("responseStatus at Send Xml not Validated as "+responseStatus_DDS);
//				             }
//			            }
		 	                    

		 	            
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
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }

	public void createCMSAccountID_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
    {
          Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
          ResultSet  rs;
          int callFound=0;
          String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String Time= c.get("BaseTime").toString();
         c.setValue("createCMSaccountID","true");
         Application.showMessage("-------------------------------------------------");
         Application.showMessage("*****createCMSAccountID_Validate Function******");       
         Application.showMessage("-------------------------------------------------");
         lC.findThinktimeOperationRTP(input, c);
         c.setValue("createCMSaccountID","false");
          try
          {
              //  Statement st =lC. dBConnect(input, c);
            // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'hss:HomeSecurityServicePort/createCMSAccountID' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"'order by creation_time desc)where rownum <= 20");
        	  rs=lC.reduceThinkTimeRTP(input, c);
        	  while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
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
                    
	
                      String billingArrangementID_cmsSend= lC.nodeFromKey(senddata,"<hst:billingArrangementID>","</hst:billingArrangementID>");
                      Application.showMessage("billingArrangementID_cmsSend is ::"+billingArrangementID_cmsSend); 
                      if(billingArrangementID_cmsSend.equals(c.getValue("ACC_input")))
                      {
                            Application.showMessage("Recieve XML is::  \n"+ recievedata);
                            Application.showMessage("SEND XML is :: \n"+senddata);
                          Application.showMessage("*******Validation Point 1 :: WebServicall-createCMSAccountID_Validate********");
                            Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_cmsSend);
                            callFound=1;
                      }
                      else
                      {
                            continue;
                      }
                      
                      if(callFound==1)
                      {
                
                            String cmsAccountID= lC.nodeFromKey(recievedata,"<typ:cmsAccountID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:cmsAccountID>");
                            Application.showMessage("cmsAccountID is ::"+cmsAccountID);
                            c.setValue("CcentralNum", cmsAccountID);
                            
                            String billingArrangementIDRes_CMSAccountID= lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
                            Application.showMessage("billingArrangementIDRes_CMSAccountID is::"+billingArrangementIDRes_CMSAccountID); 
                                  
                      }
                      
                                  
                 }
             }
            //   st.close();
          }     
          catch (SQLException e)
          {
              System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                return;
          }
    }
    

	public void SuspendAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("suspendAccount","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****SuspendAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("suspendAccount","false"); 
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/suspendAccount' order by creation_time desc)where rownum <= 20");
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
	                          
		            String senddata = lC.extractXml(rs,xmldata2);		         
		            String recievedata = lC.extractXml(rs,xmldata1);      
		         		           
		            String id= lC.nodeFromKey(senddata,"<id>","</id>");
		            Application.showMessage("id is ::"+id);
		            
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
			            	 if(errorCode.equals("UCE-15113"))
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

			            String errorMessage= lC.nodeFromKey(recievedata,"<errorMessage xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorMessage>");
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
			return;
		}
	}
	
	public void suspend_COPS_validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****suspend_COPS_validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     
	     
	  
	     
		try
		{
			Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/SuspendAccount' order by creation_time desc)where rownum <= 20");
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
		         		           
		            String id= lC.nodeFromKey(senddata,"<accountNumber>","</accountNumber>");
		            Application.showMessage("Ccentral Number from Cops is ::"+id);
		            
		            if(id.equals(c.getValue("CcentralNum")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Suspend Cops Call********");
	            		Application.showMessage("Validation is Successfull with CCentral Number"+id);
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
			           
			            
			           
			            if(readBack==null)
			            {
				            c.report("Send Xml readBack is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("readBack from Send Xml  from Suspend Cops  is ::"+" "+readBack);
			            	 if(readBack.equals("COMCC9O2P5SUSPENDACCOUNT"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-readBack********");
				            	 Application.showMessage("Validation is Successfull with readBack::"+" "+readBack);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("readBack at Send Xml not Validated as "+readBack);
				             }
			            }	
			            
			            String result= lC.nodeFromKey(recievedata,"<result>","</result>");
			            Application.showMessage("result is ::"+result);
			            
			            if(result==null)
			            {
				            c.report("Send Xml result is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("result from Send Xml  from Suspend Cops  is ::"+" "+result);
			            	 if(result.trim().equals("OK"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-result********");
				            	 Application.showMessage("Validation is Successfull with result::"+" "+result);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("result at Send Xml not Validated as "+result);
				             }
			            }	
			            
			            String pc= lC.nodeFromKey(senddata,"<pc>","</pc>");
			            Application.showMessage("pc is ::"+pc);
			            
			            if(pc==null)
			            {
				            c.report("Send Xml pc is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("pc from Send Xml  from Suspend Cops  is ::"+" "+pc);
			            	 if(pc.trim().equals("C9O2P5"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-pc********");
				            	 Application.showMessage("Validation is Successfull with pc::"+" "+pc);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("pc at Send Xml not Validated as "+pc);
				             }
			            }	

			            String dn= lC.nodeFromKey(senddata,"<dn>","</dn>");
			            Application.showMessage("dn is::"+dn); 
			            
			            if(dn==null)
			            {
				            c.report("Send Xml dn is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("dn from Send Xml  from Suspend Cops  is ::"+" "+dn);
			            	 if(dn.trim().equals("COMC"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-dn********");
				            	 Application.showMessage("Validation is Successfull with dn::"+" "+dn);
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
	         if(v1 * v2 * v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: suspend_COPS_validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: suspend_COPS_validate is Failed with Validation Errors");
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
	
		
	
	public void orderUpdate_Validate_SupressErrorSus(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("OrderUpdate","true");
	       Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********OrderUpdate_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("OrderUpdate","false"); 
		try
		{
		//	Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
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

		 	            String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
		 	            Application.showMessage("ordSource is ::"+ordSource); 
		 	            
		 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		 	            {
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
					            	 v2=1;
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
		 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
		 			             {
		 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
		 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
		 			            	 v2=1;
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
			            	 if(ordType.equals("NPD"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordType::"+" "+ordType);
				            	 v4=1;
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
				            	 v5=1;
				             }
				             else
				             {
				            	 c.report("customerType at Send Xml not Validated as "+customerType);
				             }
			            }	
		 	            

		            }
	            }
	               
	         
	         if(v1*callFound*v2*v4 * v5 ==1)
	         {
	        	 c.setValue("IsDemi", "false");
	         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.setValue("IsDemi", "false");
	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
	         }
	        // st.close();
		}	
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	public void queryLocation_ValidateRes(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
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
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation' order by creation_time desc)where rownum <= 20");
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
	
	
	public void processHomeSecurityInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("processHomeSecurity","true");
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("processHomeSecurity","false");  
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' order by creation_time desc)where rownum <= 100");
			rs=lC.reduceThinkTimeRTP(input, c);
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
			            	 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("ACTIVE"))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
				            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+homeSecurityLOBStatus_DDS);
				             }
			            }
		 	            
		 	            String permitRequired_DDS= lC.nodeFromKey(senddata,"<typesDDS:permitRequired>","</typesDDS:permitRequired>");
		 	            Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
		 	           
		 	         	 	            
		 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata," <mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
		 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
		 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
		 	            
		 	            String responseStatus_DDS= lC.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/esd-directory/14.05/types\">","</typ:responseStatus>");
		 	            Application.showMessage("responseStatus is::"+responseStatus_DDS); 
		 	           if(responseStatus_DDS==null)
			            {
				            c.report(" responseStatus is NULL");
				            continue;
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
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	public void RestoreAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****RestoreAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     
	     
	  
	     
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/restoreAccount' order by creation_time desc)where rownum <= 20");
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
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-RestoreAccount_Validate Call********");
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
			            	 Application.showMessage("Account Number from Send Xml  from Suspend is ::"+" "+errorCode);
			            	 if(errorCode.equals("UCE-15113"))
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

			            String errorMessage= lC.nodeFromKey(senddata,"<errorMessage xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorMessage>");
			            Application.showMessage("errorMessage is::"+errorMessage); 
			            
		               
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1 ==1)
	         {
	         	Application.showMessage("WebService Call :: RestoreAccount_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: RestoreAccount_Validate is Failed with Validation Errors");
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
	
	public void Activate_COPS_validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****Activate_COPS_validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     
	     
	  
	     
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/ActivateAccount' order by creation_time desc)where rownum <= 20");
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
		         		           
		            String id= lC.nodeFromKey(senddata,"<accountNumber>","</accountNumber>");
		            Application.showMessage("Ccentral Number from Cops is ::"+id);
		            
		            if(id.equals(c.getValue("CcentralNum")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Suspend Cops Call********");
	            		Application.showMessage("Validation is Successfull with CCentral Number"+id);
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
			           
			            
			           
			            if(readBack==null)
			            {
				            c.report("Send Xml readBack is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from Suspend Cops  is ::"+" "+readBack);
			            	 if(readBack.equals("COMCC9O2P5ACTIVATEACCOUNT"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-readBack********");
				            	 Application.showMessage("Validation is Successfull with readBack::"+" "+readBack);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("readBack at Send Xml not Validated as "+readBack);
				             }
			            }	
			            
			            String result= lC.nodeFromKey(recievedata,"<result>","</result>");
			            Application.showMessage("result is ::"+result);
			            
			            if(result==null)
			            {
				            c.report("Send Xml result is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("result from Send Xml  from Suspend Cops  is ::"+" "+result);
			            	 if(result.equals("OK"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-result********");
				            	 Application.showMessage("Validation is Successfull with result::"+" "+result);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("result at Send Xml not Validated as "+result);
				             }
			            }	
			            
			            String pc= lC.nodeFromKey(senddata,"<pc>","</pc>");
			            Application.showMessage("pc is ::"+pc);
			            
			            if(pc==null)
			            {
				            c.report("Send Xml pc is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("pc from Send Xml  from Suspend Cops  is ::"+" "+pc);
			            	 if(pc.equals("C9O2P5"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-pc********");
				            	 Application.showMessage("Validation is Successfull with pc::"+" "+pc);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("pc at Send Xml not Validated as "+pc);
				             }
			            }	

			            String dn= lC.nodeFromKey(senddata,"<dn>","</dn>");
			            Application.showMessage("dn is::"+dn); 
			            
			            if(dn==null)
			            {
				            c.report("Send Xml dn is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("pc from Send Xml  from Suspend Cops  is ::"+" "+dn);
			            	 if(dn.equals("COMC"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-dn********");
				            	 Application.showMessage("Validation is Successfull with dn::"+" "+dn);
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
	         	Application.showMessage("WebService Call :: Activate_COPS_validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: Activate_COPS_validate is Failed with Validation Errors");
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

	
	
	
	public void orderUpdate_Validate_IgnoreWorklistRestore(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("OrderUpdate","true");
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********OrderUpdate_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("OrderUpdate","false"); 
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
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

		 	            String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
		 	            Application.showMessage("ordSource is ::"+ordSource); 
		 	            
		 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		 	            {
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
			            	 if(ordType.equals("RES"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
				            	 v2=1;
				             }
				             else
				             {
				            	 Application.showMessage("lt_fireNumber at Send Xml not Validated as "+ordType);
				            	 continue;
				            	 
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

		            }
	               
	         }
	         
	         if(v1*callFound*v2*v3*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
	         	c.setValue("IsDemi", "false");
	         }
	         else
	         {
	        	 c.setValue("IsDemi", "false");
	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
	         }
	        // st.close();
		}
	         
		}
		
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	public void deleteUnactivatedAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("deleteUnactivatedAccount","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*******deleteUnactivatedAccount  Function********");       
	     Application.showMessage("-------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("deleteUnactivatedAccount","false"); 
	     
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/deleteUnactivatedAccount' order by creation_time desc)where rownum <= 20");
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
	                          
		            String senddata = lC.extractXml(rs,xmldata2);		         
		            String recievedata = lC.extractXml(rs,xmldata1);      
		         		           
		            String id= lC.nodeFromKey(senddata,"<id>","</id>");
		            Application.showMessage("id is ::"+id);
		            
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

			            String errorMessage= lC.nodeFromKey(recievedata,"<errorMessage xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorMessage>");
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
			return;
		}
	}
	
	
	public void DisconnectAccount_CANCEL_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("DisconnectAccount","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*******deleteUnactivatedAccount  Function********");       
	     Application.showMessage("-------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("DisconnectAccount","false"); 
	     
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/DisconnectAccount' order by creation_time desc)where rownum <= 20");
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
	                          
		            String senddata = lC.extractXml(rs,xmldata2);		         
		            String recievedata = lC.extractXml(rs,xmldata1);      
		         		           
		            String id= lC.nodeFromKey(senddata,"<accountNumber>","</accountNumber>");
		            Application.showMessage("Ccentral Number from Cops is ::"+id);
		            
		            if(id.equals(c.getValue("CcentralNum")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Suspend Cops Call********");
	            		Application.showMessage("Validation is Successfull with CCentral Number"+id);
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
			           
			            
			           
			            if(readBack==null)
			            {
				            c.report("Send Xml readBack is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("readBack from Send Xml  from Suspend Cops  is ::"+" "+readBack);
			            	 if(readBack.trim().equals("COMCC9O2P5DELETEACCOUNT"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-readBack********");
				            	 Application.showMessage("Validation is Successfull with readBack::"+" "+readBack);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("readBack at Send Xml not Validated as "+readBack);
				             }
			            }	
			            
			            String result= lC.nodeFromKey(recievedata,"<result>","</result>");
			            Application.showMessage("result is ::"+result);
			            
			            if(result==null)
			            {
				            c.report("Send Xml result is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("result from Send Xml  from Suspend Cops  is ::"+" "+result);
			            	 if(result.trim().equals("OK"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-result********");
				            	 Application.showMessage("Validation is Successfull with result::"+" "+result);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("result at Send Xml not Validated as "+result);
				             }
			            }	
			            
			            String pc= lC.nodeFromKey(senddata,"<pc>","</pc>");
			            Application.showMessage("pc is ::"+pc);
			            
			            if(pc==null)
			            {
				            c.report("Send Xml pc is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("pc from Send Xml  from Suspend Cops  is ::"+" "+pc);
			            	 if(pc.trim().equals("C9O2P5"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-pc********");
				            	 Application.showMessage("Validation is Successfull with pc::"+" "+pc);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("pc at Send Xml not Validated as "+pc);
				             }
			            }	

			            String dn= lC.nodeFromKey(senddata,"<dn>","</dn>");
			            Application.showMessage("dn is::"+dn); 
			            
			            if(dn==null)
			            {
				            c.report("Send Xml dn is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("pc from Send Xml  from Suspend Cops  is ::"+" "+dn);
			            	 if(dn.trim().equals("COMC"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-dn********");
				            	 Application.showMessage("Validation is Successfull with dn::"+" "+dn);
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
			return;
		}
	}

	 public void SimInfoEx_Error(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
     {
            Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
          LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
            ResultSet  rs;
            int callFound=0,v1=0;
            String xmldata1 ="receive_data";
          String xmldata2 ="SEND_DATA";
         
          Application.showMessage("-------------------------------------------------");
          Application.showMessage("*****SimInfoEx_Validate Function******");       
          Application.showMessage("-------------------------------------------------");
              
          try
  		{
  			 Statement st =lC. dBConnect(input, c);
  		      rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:NumerexSimulatorLogMsg/SIMInfoEx' order by creation_time desc)where rownum <= 20");
  	         while (rs.next() && rs.getString(1)!= c.getValue("BaseMsgid"))
  		        {
  		            
  		             String rowmsg = rs.getString(1);	          
  		            	    		            	String senddata =lC.extractXml(rs,xmldata2);
  			            System.out.printf("SEND XML is  \n",senddata );	
  			            Application.showMessage("SEND XML is  \n"+ senddata);
                
                
                   Application.showMessage("Your SEND XML is NULL \n");   
                   String recievedata=lC.extractXml(rs,xmldata1);
                   Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
                       
                 { 
                               
                        String senddata1 = lC.extractXml(rs,xmldata2);           
                        String recievedata1 = lC.extractXml(rs,xmldata1);      
                                     
                        String sUnitKey= lC.nodeFromKey(senddata1,"<sUnitKey>","<sUnitKey>");              
                        Application.showMessage("sUnitKey is ::"+sUnitKey);
                        String CcentralNum=c.getValue("CcentralNum");
                        if(sUnitKey.equals(CcentralNum))
                   {
                          
                          Application.showMessage("*******Validation Point 1 :: WebServicall-sUnitKey********");
                          Application.showMessage("Validation is Successfull with Input C-Central Number"+sUnitKey);
                          callFound=1;
                   }
                   else
                   {
                          continue;
                   }
                   
                        if(callFound==1)
                        {
                  
                               String SIMInfoExResult= lC.nodeFromKey(recievedata1,"<SIMInfoExResult>","</SIMInfoExResult>");
                              Application.showMessage("SIMInfoExResult is ::"+SIMInfoExResult);
                             
                               
                               
                               String sICCID= lC.nodeFromKey(recievedata1,"<sICCID>","</sICCID>");
                              Application.showMessage("sICCID is :: "+sICCID); 
                               
                              if(sICCID==null)
                                {
                                     c.report("Send Xml sStatus is NULL");
                                     continue;
                               }
                               else
                               {
                                  String iccId=c.getValue("RTPDataSourceCollections", "dB_Simulator: iccId");
                                  Application.showMessage("iccId Status from input is::"+iccId);
                                  if(sICCID.equalsIgnoreCase(iccId))
                                      {
                                        Application.showMessage("*******Validation Point 2 :: WebServicall-sICCID********");
                                        Application.showMessage("Validation is Successfull with iccId::"+" "+sICCID);
                                        v1=1;
                                      }
                                      else
                                      {
                                        c.report("iccId at Send Xml not Validated as "+sICCID);
                                        v1=0;
                                      }
                               }        
                              
                              String sStatus= lC.nodeFromKey(recievedata1,"<sStatus>","</sStatus>");
                              Application.showMessage("sStatus is :: "+sStatus); 
                               
                              if(sStatus==null)
                                {
                                     c.report("Send Xml sStatus is NULL");
                                     continue;
                               }
                               else
                               {
                                  String SimStatus=c.getValue("RTPDataSourceCollections", "dB_Simulator: SimInfoStatus");
                                  Application.showMessage("Simulator Status from input is::"+SimStatus);
                                  if(sStatus.equalsIgnoreCase(SimStatus))
                                      {
                                        Application.showMessage("*******Validation Point 2 :: WebServicall-SimInfoStatus********");
                                        Application.showMessage("Validation is Successfull with sStatus::"+" "+sStatus);
                                        v1=1;
                                      }
                                      else
                                      {
                                        v1=0;
                                        c.report("sStatus at Send Xml not Validated as "+sStatus);
                                      }
                               }        

                             
                              
                        break;
                        }
                  }
              }
              if(v1*callFound==1)
              {
                   Application.showMessage("WebService Call :: SimInfoEx_Validate is Success[All validation points are Success]");
              }
              else
              {
                    c.put("result", "false");
                    c.report("WebService Call :: SimInfoEx_Validate is Failed with Validation Errors");
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