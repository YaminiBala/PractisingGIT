import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class CVR_ValidationCalls 
{
	
	private static final String String = null;
	public Connection connection = null;
	public static String rowmsg= null;
	//LibraryRtp lib=new LibraryRtp();
	//RTP_ValidationsClass vC=new RTP_ValidationsClass();
	public String processHomeSecurityInfoCVR_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	   //  c.setValue("processHomeSecurity","true");

String rscallpresent="true";
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****processHomeSecurityInfo_Validate for CVR  Function******");       
	     Application.showMessage("----------------------------------------------------");
	   //  lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("processHomeSecurity","false");  
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));


		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{

	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	            rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =lC.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            	c.report("Functionality issue :: we should get response in DDS when we process with CVR , but it is throwing no response");
	            	break;
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
		      
		 	            String cvrProductNumber_DDS= lC.nodeFromKey(senddata,"<typesDDS:cvrFeatureProductNumber>","</typesDDS:cvrFeatureProductNumber>");
		 	            Application.showMessage("CVR Product Number is ::"+cvrProductNumber_DDS);
		 	            
		 	            
		 	            if(cvrProductNumber_DDS==null)
			            {
				            c.report("CVR Product Number is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("CVR Product Number in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+cvrProductNumber_DDS);
			            	 
			            }		

		 	            String CVRProductActionType= lC.nodeFromKey(senddata,"<typesDDS:actionType>","</typesDDS:actionType>");
		 	            Application.showMessage("CVRProductActionType is ::"+CVRProductActionType); 
		 	            String Scenario_number=c.getValue("RTPNormalScenariosdS","RTP_InputParams: SCENARIO_NUMBER");
		 	           if(CVRProductActionType==null)
			            {
				            c.report("Send Xml CVRProductActionType is NULL");
				            continue;
			            }
			            else
			            {
			            	if(Scenario_number.equals("1") || Scenario_number.equals("2") || Scenario_number.equals("5") || Scenario_number.equals("8") || Scenario_number.equals("21")) 
			            	{
			            		if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: ServiceChangeActivity1").equalsIgnoreCase("RemovedInstall") || c.getValue("RTPNormalScenariosdS","RTP_InputParams: ServiceChangeActivity2").equalsIgnoreCase("RemovedInstall"))
			            		{
			            			if(CVRProductActionType.equalsIgnoreCase("REMOVE"))
				            		{
				            			
				            			Application.showMessage("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+CVRProductActionType);
				            			v1=1;
				            		}
			            			else if(CVRProductActionType.equalsIgnoreCase("ADD"))
				            		{
				            			Application.showMessage("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+CVRProductActionType);
				            			v1=1;
				            		}
				            		else
				            		{
				            			c.report("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is not Validated as "+" "+CVRProductActionType);
				            		}
			            		}
			            		else
			            		{
			            		if(CVRProductActionType.equalsIgnoreCase("ADD"))
			            		{
			            			Application.showMessage("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+CVRProductActionType);
			            			v1=1;
			            		}
			            		else
			            		{
			            			c.report("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is not Validated as "+" "+CVRProductActionType);
			            		}
			            		}
			            	}
			            	
			            	else if(Scenario_number.equals("11") || Scenario_number.equals("4") ) 
			            	{
			            		if(CVRProductActionType.equalsIgnoreCase("REMOVE"))
			            		{
			            			
			            			Application.showMessage("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+CVRProductActionType);
			            			v1=1;
			            		}
			            		else
			            		{
			            			c.report("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is not Validated as "+" "+CVRProductActionType);
			            		}
			            	}
			         
			            }

		 	            
			           
				            
		 	            
		 	           
		 	         	 	            
		 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
		 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
		 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
		 	            c.put("authorizationGuid", authorizationGuid_DDS);
		 	            
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
				            	 v4=1;
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
	         
	         if(v1*callFound*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
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

	//------
	
	
	public void DatasourceprocessHomeSecurityInfoCVR_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
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
	     Application.showMessage("*****processHomeSecurityInfo_Validate for CVR  Function******");       
	     Application.showMessage("----------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("processHomeSecurity","false"); 
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	            rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =lC.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            	c.report("Functionality issue :: we should get response in DDS when we process with CVR , but it is throwing no response");
	            	break;
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
	            		
	            		String CVRProductActionType= lC.nodeFromKey(senddata,"<typesDDS:actionType>","</typesDDS:actionType>");
		 	            Application.showMessage("CVRProductActionType is ::"+CVRProductActionType); 
		 	            String Scenario_number=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SCENARIO_NUMBER");
		 	           if(CVRProductActionType==null)
			            {
				            c.report("Send Xml CVRProductActionType is NULL");
				            continue;
			            }
			            else
			            {
			            	if(Scenario_number.equals("1") || Scenario_number.equals("3")) 
			            	{
			            		
			            		if(CVRProductActionType.equalsIgnoreCase("ADD") || CVRProductActionType.equalsIgnoreCase("REMOVE"))
			            		{
			            			Application.showMessage("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+CVRProductActionType);
			            			v1=1;
			            		}
			            		else if(Scenario_number.equals("11"))
			            		{
			            			Application.showMessage("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+CVRProductActionType);
			            			v1=1;
			            		}
			            		else
			            		{
			            			
			            		}
			            		
			            	}
			            	
			            	
			         
			            }

		 	            
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound*v1==1)
		            {
		      
		 	            String cvrProductNumber_DDS= lC.nodeFromKey(senddata,"<typesDDS:cvrFeatureProductNumber>","</typesDDS:cvrFeatureProductNumber>");
		 	            Application.showMessage("CVR Product Number is ::"+cvrProductNumber_DDS);
		 	            
		 	            
		 	            if(cvrProductNumber_DDS==null)
			            {
				            c.report("CVR Product Number is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("CVR Product Number in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+cvrProductNumber_DDS);
			            	 
			            }		

		 	            
			           
				            
		 	            
		 	           
		 	         	 	            
		 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
		 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
		 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
		 	            c.put("authorizationGuid", authorizationGuid_DDS);
		 	            
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
				            	 v4=1;
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
	         
	         if(v1*callFound*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
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
//----------------------------------
	
	
	public void DatasourceprocessHomeSecurityInfoCVR_Validate_CLS(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
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
	     Application.showMessage("*****processHomeSecurityInfo_Validate for CVR  Function******");       
	     Application.showMessage("----------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("processHomeSecurity","false"); 
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	            rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	String senddatacls = lC.extractXml(rs,xmldata2);           
                   // String recievedata = lC.extractXml(rs,xmldata1);
                  //  String senddata=lC.clsRemoveAsciiCharacter(senddatacls);
	            	String senddata =lC.clsRemoveAsciiCharacter(senddatacls);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            	c.report("Functionality issue :: we should get response in DDS when we process with CVR , but it is throwing no response");
	            	break;
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedatacls=lC.extractXml(rs,xmldata1);
	            	String recievedata =lC.clsRemoveAsciiCharacter(recievedatacls);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	            	String senddatacls = lC.extractXml(rs,xmldata2);  
	            	String recievedatacls=lC.extractXml(rs,xmldata1);
	            	String senddata =lC.clsRemoveAsciiCharacter(senddatacls);      
	            	String recievedata =lC.clsRemoveAsciiCharacter(recievedatacls);  
		          	           
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
	            		
	            		String CVRProductActionType= lC.nodeFromKey(senddata,"<typesDDS:actionType>","</typesDDS:actionType>");
		 	            Application.showMessage("CVRProductActionType is ::"+CVRProductActionType); 
		 	            String Scenario_number=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SCENARIO_NUMBER");
		 	           if(CVRProductActionType==null)
			            {
				            c.report("Send Xml CVRProductActionType is NULL");
				            continue;
			            }
			            else
			            {
			            	if(Scenario_number.equals("1") || Scenario_number.equals("3")) 
			            	{
			            		
			            		if(CVRProductActionType.equalsIgnoreCase("ADD") || CVRProductActionType.equalsIgnoreCase("REMOVE"))
			            		{
			            			Application.showMessage("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+CVRProductActionType);
			            			v1=1;
			            		}
			            		else
			            		{
			            			//c.report("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is not Validated as "+" "+CVRProductActionType);
			            		}
			            		
			            	}
			            	
			            	
			         
			            }

		 	            
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound*v1==1)
		            {
		      
		 	            String cvrProductNumber_DDS= lC.nodeFromKey(senddata,"<typesDDS:cvrFeatureProductNumber>","</typesDDS:cvrFeatureProductNumber>");
		 	            Application.showMessage("CVR Product Number is ::"+cvrProductNumber_DDS);
		 	            
		 	            
		 	            if(cvrProductNumber_DDS==null)
			            {
				            c.report("CVR Product Number is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("CVR Product Number in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+cvrProductNumber_DDS);
			            	 
			            }		

		 	            
			           
				            
		 	            
		 	           
		 	         	 	            
		 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
		 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
		 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
		 	            c.put("authorizationGuid", authorizationGuid_DDS);
		 	            
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
				            	 v4=1;
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
	         
	         if(v1*callFound*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
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
	//----------------------------
	
	public String BNERcallCVR_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,time=0;

String rscallpresent="true";
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String orderstatus="Completed";
	     String ordertype1="Install";
	     String ordertype2="Restart";
	     String Time= c.get("BaseTime").toString();
	    // c.setValue("invokeRestservice","true");
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****BNER CALL  Invoke Rest Service_Validate for CVR  Function******");       
	     Application.showMessage("----------------------------------------------------");
	   //  lC.findThinktimeOperationRTP(input, c);
	  //   c.setValue("invokeRestservice","false"); 
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("BNERcall"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("BNERcall"));


		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/invokeRestService'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{

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
		            String senddata1=senddata.replaceAll("&lt;","<");
		            String senddata2=senddata1.replaceAll("&gt;",">");
		            String senddata3=senddata2.replaceAll("&amp;#34;,",":");		            
		            String senddata4=senddata3.replaceAll("&amp;#34;:&amp;#34;",">") ;
		            String senddata5=senddata4.replaceAll("&amp;#34;","<") ;		           
		            String senddata7=senddata5.replaceAll(":<order_status>","</timestamp><order_status>") ;
		            String senddata8=senddata7.replaceAll(":<entitlements<:","</order_status>") ;
		            String senddata9=senddata8.replaceAll("<}}","</cvr><payLoad>") ;
		            Application.showMessage("SEND XML is :: \n"+senddata9.trim());
		         //   Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	
		            
		            
		            String Scenario_number=c.getValue("RTPNormalScenariosdS","RTP_InputParams: SCENARIO_NUMBER");
		            String Rtp_Orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_OrderStatus");
		 	           String Rtp_Ordertype=c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_OrderType");
		            Application.showMessage("**********ResourcePath Validation**********");
		            String ResourcePath= lC.nodeFromKey(senddata9,"<resourcePath>","</resourcePath>");
	 	            Application.showMessage("ResourcePath is ::"+ResourcePath);
	 	           String path=ResourcePath.replaceAll("/cvrBillingNotification/","");
	 	          Application.showMessage("ResourcePath is ::"+path);
	 	         String TimeStamp=lC.nodeFromKey(senddata9,"<timestamp>","</timestamp>");
	 	          
		            if(path.trim().equals((c.get("authorizationGuid"))) )
	            	{
		            	
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-InvokeRestService Call********");
	            		Application.showMessage("Validation is Successfull with Resource Path"+ResourcePath);
	            		callFound=1;
	            		
	            		
	            	}
		            else 
		            {
	            		continue;
	            	}
	            	 if(TimeStamp.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
	            	{
	            		continue;
	            	}
	            	 else
	            	 {
	            		 time=1;
	            	 }
	            	
		            if(callFound*time==1)
		            {
		            	
		      
		            	//String TimeStamp=lC.nodeFromKey(senddata9,"<timestamp>","</timestamp>");
		            	 String cvr=lC.nodeFromKey(senddata9,"<cvr>","</cvr>");
		            	 String OrderStatus=lC.nodeFromKey(senddata9,"<order_status>","</order_status>");
		            	Application.showMessage("TimeStamp is :: "+TimeStamp);
		            	Application.showMessage("cvr from Bner call is :: "+cvr);
		            	Application.showMessage("Order Status  from Bner call is :: "+OrderStatus);
		            	Application.showMessage("cvr from logic is :: "+c.get("CVRCOUNT").toString().trim());
		            	Boolean cvrValue=cvr.trim().equalsIgnoreCase(c.get("CVRCOUNT").toString().trim());
		            	Application.showMessage("checking condition :: "+cvrValue);
		            	Application.showMessage("checking condition :: "+c.getValue("IsDemi"));
		            	Application.showMessage("**********CVR Validation**********");            	
		           			          
		            	if( c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_ScenarioName").equalsIgnoreCase("Demi")) 
		            	{
		            		Application.showMessage("cvr from logic is :: "+c.get("CVRCOUNT").toString().trim());
		 	        	 if(cvr==null)
			            {
				            c.report("cvr value is NULL");
				            continue;
			            }
		 	        	else  if(Scenario_number.equals("11"))
			 	           {
		 	        		if(cvr.equals("0"))
		 	        		{
			 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is successful with the No of CVR Products");
		            		 v1=1;
		 	        		}
		 	        		else
		 	        		{
		 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
			            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
		 	        		}
			 	           }
		 	        	
		 	          else  
		 	        	 
		 	          {
		 	        	 if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: ServiceChangeActivity1").equalsIgnoreCase("RemovedInstall") && c.getValue("RTPNormalScenariosdS","RTP_InputParams: ServiceChangeActivity1").equalsIgnoreCase("RemovedInstall"))
		 	        	 {
		 	        		if(cvr.equals("0"))
		 	        		{
			 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is successful with the No of CVR Products");
		            		 v1=1;
		 	        		}
		 	        		else  if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: ServiceChangeActivity1").equalsIgnoreCase("RemovedInstall") || c.getValue("RTPNormalScenariosdS","RTP_InputParams: ServiceChangeActivity1").equalsIgnoreCase("RemovedInstall"))
			 	        	 {
			 	        		if(cvr.equals("1"))
			 	        		{
				 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is successful with the No of CVR Products");
			            		 v1=1;
			 	        		}
			 	        	 }
		 	        		else
		 	        		{
		 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
			            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
		 	        		} 
		 	        	 }
		 	        	 
		 	        	 else  if(cvr.trim().equalsIgnoreCase(c.get("CVRCOUNT").toString().trim()))
		 	          
		 	           {
		 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
	            		 Application.showMessage("Validation is successful with the No of CVR Products");
	            		 
	            		 v1=1;
		 	           }
		 	         else
	            	 {
	            		 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
	            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
	            		
	            		 c.report("Validation is  not successful with the No of CVR Products");
	            		// v1=1;
	            	 }
		            	}
		            	}
		            	else
		            	{
		            		
		            		
		            		if(cvr==null)
				            {
					            c.report("cvr value is NULL");
					            continue;
				            }
			 	        	else  if(Scenario_number.equals("11"))
				 	           {
			 	        		if(cvr.equals("0"))
			 	        		{
				 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is successful with the No of CVR Products");
			            		 v1=1;
			 	        		}
			 	        		else
			 	        		{
			 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
				            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
				            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
			 	        		}
				 	           }
			 	          else  if(cvr.trim().equalsIgnoreCase(c.get("INSIGHTCVRCOUNT").toString().trim()))
			 	           {
			 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is successful with the No of CVR Products");
		            		 
		            		 v1=1;
			 	           }
			 	         else
		            	 {
		            		 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
		            		 
		            		 c.report("Validation is  not successful with the No of CVR Products");
		            		// v1=1;
		            	 }
		            	}

		 	        	 
		 	        	Application.showMessage("**********Order Status  Validation**********"); 
		            	if(Scenario_number.equals("1") || Scenario_number.equals("2") || Scenario_number.equals("8") || Scenario_number.equals("21") ) 
		            	{
		            		if(Rtp_Orderstatus.equalsIgnoreCase(orderstatus) && Rtp_Ordertype.equalsIgnoreCase(ordertype1) || Rtp_Orderstatus.equalsIgnoreCase(orderstatus) && Rtp_Ordertype.equalsIgnoreCase(ordertype2) )
		            		{
		            			if(OrderStatus.equalsIgnoreCase("Active"))
			            		{
			            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :: Active");
			            			v2=1;
			            		}
			            		else
			            		{
			            			c.report("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is NOT VALIDATED AS Active");
			            		}
		            		}
		            		else if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: ServiceChangeActivity1").equalsIgnoreCase("RemovedInstall") || c.getValue("RTPNormalScenariosdS","RTP_InputParams: ServiceChangeActivity2").equalsIgnoreCase("RemovedInstall"))
				            		
		            		{
		            			if(OrderStatus.equals("Cancelled"))
			            		{
			            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :: CANCELLED");
			            			v2=1;
			            		}
		            			else if(OrderStatus.equals("PendingInstall"))
			            		{
			            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :: PendingInstall");
			            			v2=1;
			            		}
			            		else
			            		{
			            			c.report("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is not validated as CANCELLED");
			            		}
		            		}
		            		else
		            		{
		            		if(OrderStatus.equals("PendingInstall"))
		            		{
		            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :: PendingInstall");
		            			v2=1;
		            		}
		            		else
		            		{
		            			c.report("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is NOT VALIDATED AS PendingInstall");
		            		}
		            		}
		            	}
		            	
		            	else if(Scenario_number.equals("11") ) 
		            	{
		            		if(OrderStatus.equals("Cancelled"))
		            		{
		            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :: CANCELLED");
		            			v2=1;
		            		}
		            		else
		            		{
		            			c.report("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is not validated as CANCELLED");
		            		}
		            	}  
		            	else if(Scenario_number.equals("4") ) 
		            	{
		            		if(OrderStatus.equals("Suspended"))
		            		{
		            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :: Suspended");
		            			v2=1;
		            		}
		            		else
		            		{
		            			c.report("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is not validated as Suspended");
		            		}
		            	} 
		            	else if(Scenario_number.equals("5") ) 
		            	{
		            		if(OrderStatus.equals("Resume"))
		            		{
		            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :: Resume");
		            			v2=1;
		            		}
		            		else
		            		{
		            			c.report("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is not validated as Resume");
		            		}
		            	} 
				            
		 	      Application.showMessage("********Validation in receive xml******")   ;   
		 	           
		 	     String receivedata1=recievedata.replaceAll("&lt;","<");
		            String receivedata2=receivedata1.replaceAll("&gt;",">"); 
		            String receivedata3=receivedata2.replaceAll("&#34;","");
		            Application.showMessage("Recieve XML is::  \n"+ receivedata3);
		            String status=lC.nodeFromKey( receivedata3,"<responseBody>","</responseBody>");
		           /* if(status.contains("success"))
		            {
		            Application.showMessage("**************Validation is successful for Receive data with status success*****");
		            v3=1;
		            }
		            else
		            {
		            	c.report("Validation is not successful for Receive data with status success");
		            }*/
		 	          
		            v3=1;  
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: InvokeRestservice is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::InvokeRestservice is Failed with Validation Errors");
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
	
	//-----------------
	
	public void DatasourceBNERcallCVR_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String orderstatus="Completed";
	     String ordertype1="Install";
	     String ordertype2="Restart";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("invokeRestservice","true");
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****BNER CALL  Invoke Rest Service_Validate for CVR  Function******");       
	     Application.showMessage("----------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("invokeRestservice","false");  
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/invokeRestService'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
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
		            String senddata1=senddata.replaceAll("&lt;","<");
		            String senddata2=senddata1.replaceAll("&gt;",">");
		            String senddata3=senddata2.replaceAll("&amp;#34;,",":");		            
		            String senddata4=senddata3.replaceAll("&amp;#34;:&amp;#34;",">") ;
		            String senddata5=senddata4.replaceAll("&amp;#34;","<") ;		           
		            String senddata7=senddata5.replaceAll(":<order_status>","</timestamp><order_status>") ;
		            String senddata8=senddata7.replaceAll(":<entitlements<:","</order_status>") ;
		            String senddata9=senddata8.replaceAll("<}}","</cvr><payLoad>") ;
		            Application.showMessage("SEND XML is :: \n"+senddata9.trim());
		         //   Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	
		            
		            
		            String Scenario_number=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SCENARIO_NUMBER");
		            Application.showMessage("Scenario number is:"+Scenario_number);
		            String Rtp_Orderstatus=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_OrderStatus");
		 	           String Rtp_Ordertype=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_OrderType");
		            Application.showMessage("**********ResourcePath Validation**********");
		            String ResourcePath= lC.nodeFromKey(senddata9,"<resourcePath>","</resourcePath>");
	 	            Application.showMessage("ResourcePath is ::"+ResourcePath);
	 	           String path=ResourcePath.replaceAll("/cvrBillingNotification/","");
	 	          //String path=path1.replaceAll("/notifications","");
	 	          Application.showMessage("ResourcePath is ::"+path);
	 	          
		            if(path.trim().equals((c.get("authorizationGuid")))  || path.replaceAll("/notifications","").trim().equals((c.get("authorizationGuid"))))
	            	{
		            	
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-InvokeRestService Call********");
	            		Application.showMessage("Validation is Successfull with Resource Path"+ResourcePath);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		            	String TimeStamp=lC.nodeFromKey(senddata9,"<timestamp>","</timestamp>");
		            	 String cvr=lC.nodeFromKey(senddata9,"<cvr>","</cvr>");
		            	 String OrderStatus=lC.nodeFromKey(senddata9,"<order_status>","</order_status>");
		            	Application.showMessage("TimeStamp is :: "+TimeStamp);
		            	Application.showMessage("cvr from Bner call is :: "+cvr);
		            	Application.showMessage("Order Status  from Bner call is :: "+OrderStatus);
		            	//Boolean cvrValue=cvr.trim().equalsIgnoreCase(c.get("CVRCOUNT").toString().trim());
		            	//Application.showMessage("checking condition :: "+cvrValue);
		            	Application.showMessage("checking condition :: "+c.getValue("IsDemi"));
		            	Application.showMessage("**********CVR Validation**********");            	
		           			          
		            	if( c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi")) 
		            	{
		 	          
		 	        	 if(cvr==null)
			            {
				            c.report("cvr value is NULL");
				            continue;
			            }
		 	        	else  if(Scenario_number.equals("11"))
			 	           {
		 	        		if(cvr.equals("0"))
		 	        		{
			 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is successful with the No of CVR Products");
		            		 v1=1;
		 	        		}
		 	        		else
		 	        		{
		 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
			            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
		 	        		}
			 	           }
		 	        	else  if(Scenario_number.equals("3"))
			 	           {
		 	        		 if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_PN5orSerInstall_PN2").equals("10400463") || c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_PN6orSerInstall_PN2").equals("10400463") )
		 	        		{
		 	        		if(cvr.equals("1"))
		 	        		{
			 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is successful with the No of CVR Products");
		            		 v1=1;
		 	        		}
		 	        		else
		 	        		{
		 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
			            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
		 	        		}
		 	        		}
		 	        		else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_PN5orSerInstall_PN2").equals("10400463") && c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_PN6orSerInstall_PN2").equals("10400463") )
		 	        		{
		 	        		if(cvr.equals("2"))
		 	        		{
			 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is successful with the No of CVR Products");
		            		 v1=1;
		 	        		}
		 	        		else
		 	        		{
		 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
			            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
		 	        		}
		 	        		}
			 	           
		 	        	else
		 	        	{
		 	        		if(cvr.equals("0"))
		 	        		{
			 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is successful with the No of CVR Products");
		            		 v1=1;
		 	        		}
		 	        		else
		 	        		{
		 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
			            		// c.report("Validation is  not successful with the No of CVR Products for Cancel");
			            		 v1=1;
		 	        		}
		 	        		}
			 	           }
		 	        	
		 	          
		 	        	 
		 	        	 else  if(cvr.trim().equalsIgnoreCase(c.get("CVRCOUNT").toString().trim()))
		 	          
		 	           {
		 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
	            		 Application.showMessage("Validation is successful with the No of CVR Products");
	            		 
	            		 v1=1;
		 	           }
		 	         else
	            	 {
	            		 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
	            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
	            		
	            		 c.report("Validation is  not successful with the No of CVR Products");
	            		// v1=1;
	            	 }
		            	}
		            	
		            	else
		            	{
		            		
		            		
		            		if(cvr==null)
				            {
					            c.report("cvr value is NULL");
					            continue;
				            }
			 	        	else  if(Scenario_number.equals("11"))
				 	           {
			 	        		if(cvr.equals("0"))
			 	        		{
				 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is successful with the No of CVR Products");
			            		 v1=1;
			 	        		}
			 	        		else
			 	        		{
			 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
				            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
				            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
			 	        		}
				 	           }
			 	        	else  if(Scenario_number.equals("3"))
			 	        	{
			 	        		if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_PN5orSerInstall_PN2").equals("10400463") || c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_PN6orSerInstall_PN2").equals("10400463") )
			 	        		{
			 	        		if(cvr.equals("1"))
			 	        		{
				 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is successful with the No of CVR Products");
			            		 v1=1;
			 	        		}
			 	        		else
			 	        		{
			 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
				            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
				            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
			 	        		}
			 	        		}
			 	        		else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_PN5orSerInstall_PN2").equals("10400463") && c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_PN6orSerInstall_PN2").equals("10400463") )
			 	        		{
			 	        		if(cvr.equals("2"))
			 	        		{
				 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is successful with the No of CVR Products");
			            		 v1=1;
			 	        		}
			 	        		else
			 	        		{
			 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
				            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
				            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
			 	        		}
			 	        		}
				 	           
			 	        	else
			 	        	{
			 	        		if(cvr.equals("0"))
			 	        		{
				 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is successful with the No of CVR Products");
			            		 v1=1;
			 	        		}
			 	        		else
			 	        		{
			 	        			Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
				            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
				            		 c.report("Validation is  not successful with the No of CVR Products for Cancel");
			 	        		}
			 	        		}
			 	        	}
				 	           
			 	          else  if(cvr.trim().equalsIgnoreCase(c.get("INSIGHTCVRCOUNT").toString().trim()))
			 	           {
			 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is successful with the No of CVR Products");
		            		 
		            		 v1=1;
			 	           }
			 	         else
		            	 {
		            		 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
		            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
		            		 
		            		 c.report("Validation is  not successful with the No of CVR Products");
		            		// v1=1;
		            	 }
		            	}

		 	        	 
		 	        	Application.showMessage("**********Order Status  Validation**********"); 
		            	
		            	
		            	 if(Scenario_number.equals("3") ) 
		            	{
		            		if(OrderStatus.equals("Disconnected") ||OrderStatus.equals("Active") )
		            		{
		            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :: Disconnected");
		            			v2=1;
		            		}
		            		else
		            		{
		            			c.report("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is not validated as disconnected");
		            		}
		            	}  
		            	
		            	
		            		
		            	
				            
		 	      Application.showMessage("********Validation in receive xml******")   ;   
		 	           
		 	     String receivedata1=recievedata.replaceAll("&lt;","<");
		            String receivedata2=receivedata1.replaceAll("&gt;",">"); 
		            String receivedata3=receivedata2.replaceAll("&#34;","");
		            Application.showMessage("Recieve XML is::  \n"+ receivedata3);
		            String status=lC.nodeFromKey( receivedata3,"<responseBody>","</responseBody>");
		           /* if(status.contains("success"))
		            {
		            Application.showMessage("**************Validation is successful for Receive data with status success*****");
		            v3=1;
		            }
		            else
		            {
		            	v3=1;
		            //	c.report("Validation is not successful for Receive data with status success");
		            }*/
		 	          
		            v3=1; 
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: InvokeRestservice is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::InvokeRestservice is Failed with Validation Errors");
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
	
	//----------------------

	public  List<String> extractPayload(String services,Object input,ScriptingContext c)
	   {
		  
		   String symbol=",";
		   String simSymbol="|";
		   String value= "/";
		  
			   String check=services.replace(symbol, value);
			   List<String> Groupservices=Arrays.asList(check.split(" / "));
			   int size=Groupservices.size();
			   //c.setValue("ExistingGroup",Groupservices.get(0));
			   for(int i=0; i<size;i++)
			   {
				   Application.showMessage(""+Groupservices.get(i).trim());
			   }
			   return Groupservices; 
		  
		
		  
		     
	   }
	 
	
	public void simulatorChangecvr(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
    {
                    
		LibraryRtp lib=new LibraryRtp();       

                    Statement st = lib.dBConnect(input, c);
ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
while (rs.next()) 
{
String beforeSim = rs.getString(2);

Application.showMessage("Db Simulator Before Updation ::"+beforeSim);

}
st.close();
Statement st0 = lib.dBConnect(input, c);
//String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><useNumerexSimulator>1</useNumerexSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsPNsForCVR>10300029</xhsPNsForCVR><benrURL>https://secure.api.comcast.net/cvr-billing/qa/rest</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><useNumerexSimulator>1</useNumerexSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsNonReleaseBPNsForCVR>10300029</xhsNonReleaseBPNsForCVR><xhsReleaseBPNsForCVR>10300029</xhsReleaseBPNsForCVR><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
//String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>1</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><threeDaysDiscDuration>2</threeDaysDiscDuration><threeDaysCVRDiscTableCleanUpDuration>2</threeDaysCVRDiscTableCleanUpDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><SIMInfoICCID>8901640100334741514</SIMInfoICCID><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><oldFlowForAuthId>0</oldFlowForAuthId><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><SIMInfoExResult>0</SIMInfoExResult><getTerminalStatus>RETIRED_NAME</getTerminalStatus><addressUpdateEventTrigger>CSGAccountDetailUpdated,DDPLocationUpdate</addressUpdateEventTrigger><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsPNsForCVR>10600085,10300029</xhsPNsForCVR><benrURL>https://secure.api.comcast.net/cvr-billing/qa/rest</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>PUT</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
st0.executeUpdate(sql);
//<vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType>

st0.close();
Thread.sleep(300000);
Statement st1 = lib.dBConnect(input, c);
ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
while (rs1.next()) 
{
    
String afterSim = rs1.getString(2);
Application.showMessage("Db Simulator After Updation ::"+afterSim);

}
st0.close();


    }
	//--------------
	
	public void simulatorChangecvr_cls(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
    {
LibraryRtp lib = new LibraryRtp();
		
		Connection conn = lib.dBConnect_CLS_1(input, c);
		String name = "homesecurityUtil:hsConfig";
		PreparedStatement pstmt = conn
				.prepareStatement(" update CWFTEMPLATECONFIG set DATA=? where NAME='"
						+ name + "'");
		Statement st = conn.createStatement();
		pstmt.setString(
				1,
				"<hsConfig><name>homesecurityUtil:hsConfig</name><useUControlSimulator>0</useUControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><accountId>8720100010077795</accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>150|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|READY_FOR_ACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><suspended>0</suspended><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>1</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><oldFlowForAuthId>0</oldFlowForAuthId><avoidComcastEmpTag>0</avoidComcastEmpTag><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>1</RemoveBlobForReflow><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><homeStreamHeaderAcceptName>Accept</homeStreamHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><homeStreamAcceptValue>application/json</homeStreamAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><homeStreamConsumerKeyName>oauthConsumerKey</homeStreamConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><homeStreamHeaderConsumerKeyValue>MgkJ3Gby7Xdsxe8hBGCyCwib</homeStreamHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><homeStreamHeaderConsumerSecretName>oauthConsumerSecret</homeStreamHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><homeStreamHeaderConsumerSecretValue>uD15pbTAVDqsauHJdzRJ9MtBQ0davhDJ</homeStreamHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><homeStreamHeaderPartnerIdName>X-PartnerId</homeStreamHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><homeStreamHeaderPartnerIdValue>SSD_XHS_ORDER_PROVISIONING</homeStreamHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><homeStreamHeaderContentTypeName>Content-Type</homeStreamHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><homeStreamHeaderContentTypeValue>application/json</homeStreamHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><xhsNonReleaseBPNsForCVR>10300029</xhsNonReleaseBPNsForCVR><xhsReleaseBPNsForCVR>10300029</xhsReleaseBPNsForCVR><homeStreamRequestResourcePathForSuspendAccount>/suspended</homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount>/restored</homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup>/group/</homeStreamRequestResourcePathForAddAccountGroup><homeStreamRequestResourcePathForRemoveAccountGroup>/group/</homeStreamRequestResourcePathForRemoveAccountGroup><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount><homeStreamRequestResourcePathForUpdateAccountTierGroup>/tiergroup/</homeStreamRequestResourcePathForUpdateAccountTierGroup><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xfinityGroupSecure>XfinityEvents</xfinityGroupSecure><xfinityGroupControl>XfinityEvents-Insight</xfinityGroupControl><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><homeStreamURL>https://homesecurity-acct-qa03.codebig2.net/rest</homeStreamURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue><BENROrderStatusDisconnectedValue>Disconnected</BENROrderStatusDisconnectedValue><DDSActionREMOVEValue>REMOVE</DDSActionREMOVEValue><xhsHomeStreamOperation>deleteUnactivatedAccount,createAccount,suspendAccount,restoreAccount,deactivateAccount,removeAccount,getAccount,updateAccountTierGroup,addAccountGroup,removeAccountGroup,updateAccount</xhsHomeStreamOperation><ValidPNR>0</ValidPNR><isClsGetSimulator>1</isClsGetSimulator><isClsDeactivateSimulator>1</isClsDeactivateSimulator></hsConfig>");
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
           
		
//ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");



    }
	
	//-----------
	public void simulatorremovecvr_cls(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
    {
LibraryRtp lib = new LibraryRtp();
		
		Connection conn = lib.dBConnect_CLS_1(input, c);
		String name = "homesecurityUtil:hsConfig";
		PreparedStatement pstmt = conn
				.prepareStatement(" update CWFTEMPLATECONFIG set DATA=? where NAME='"
						+ name + "'");
		Statement st = conn.createStatement();
		pstmt.setString(
				1,
				"<hsConfig><name>homesecurityUtil:hsConfig</name><useUControlSimulator>0</useUControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>0</useSIMInfoSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><accountId>8720100010077795</accountId><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><uControlGroupValue>150|ThermostatDataProvider-Insight</uControlGroupValue><getAccountStatusConfig>READYFORACTIVATION|READY_FOR_ACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><status>Active</status><suspended>0</suspended><getAccount>1</getAccount><createAccount>1</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>1</suspendAccount><restoreAccount>1</restoreAccount><deleteUnactivatedAccount>1</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><oldFlowForAuthId>0</oldFlowForAuthId><avoidComcastEmpTag>0</avoidComcastEmpTag><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><RemoveBlobForReflow>1</RemoveBlobForReflow><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><homeStreamHeaderAcceptName>Accept</homeStreamHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><homeStreamAcceptValue>application/json</homeStreamAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><homeStreamConsumerKeyName>oauthConsumerKey</homeStreamConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><homeStreamHeaderConsumerKeyValue>MgkJ3Gby7Xdsxe8hBGCyCwib</homeStreamHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><homeStreamHeaderConsumerSecretName>oauthConsumerSecret</homeStreamHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><homeStreamHeaderConsumerSecretValue>uD15pbTAVDqsauHJdzRJ9MtBQ0davhDJ</homeStreamHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><homeStreamHeaderPartnerIdName>X-PartnerId</homeStreamHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><homeStreamHeaderPartnerIdValue>SSD_XHS_ORDER_PROVISIONING</homeStreamHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><homeStreamHeaderContentTypeName>Content-Type</homeStreamHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><homeStreamHeaderContentTypeValue>application/json</homeStreamHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><xhsNonReleaseBPNsForCVR></xhsNonReleaseBPNsForCVR><xhsReleaseBPNsForCVR></xhsReleaseBPNsForCVR><homeStreamRequestResourcePathForSuspendAccount>/suspended</homeStreamRequestResourcePathForSuspendAccount><homeStreamRequestResourcePathForRestoreAccount>/restored</homeStreamRequestResourcePathForRestoreAccount><homeStreamRequestResourcePathForAddAccountGroup>/group/</homeStreamRequestResourcePathForAddAccountGroup><homeStreamRequestResourcePathForRemoveAccountGroup>/group/</homeStreamRequestResourcePathForRemoveAccountGroup><homeStreamRequestResourcePathForDeactivateAccount>/deactivated</homeStreamRequestResourcePathForDeactivateAccount><homeStreamRequestResourcePathForUpdateAccountTierGroup>/tiergroup/</homeStreamRequestResourcePathForUpdateAccountTierGroup><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xfinityGroupSecure>XfinityEvents</xfinityGroupSecure><xfinityGroupControl>XfinityEvents-Insight</xfinityGroupControl><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><homeStreamURL>https://homesecurity-acct-qa03.codebig2.net/rest</homeStreamURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue><BENROrderStatusDisconnectedValue>Disconnected</BENROrderStatusDisconnectedValue><DDSActionREMOVEValue>REMOVE</DDSActionREMOVEValue><xhsHomeStreamOperation>deleteUnactivatedAccount,createAccount,suspendAccount,restoreAccount,deactivateAccount,removeAccount,getAccount,updateAccountTierGroup,addAccountGroup,removeAccountGroup,updateAccount</xhsHomeStreamOperation><ValidPNR>0</ValidPNR><isClsGetSimulator>1</isClsGetSimulator><isClsDeactivateSimulator>1</isClsDeactivateSimulator></hsConfig>");
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
           
		
//ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");



    }
	
	
	
	//----------------

	public void simulatorremovecvr(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
    {
                    
		LibraryRtp lib=new LibraryRtp();       

                    Statement st = lib.dBConnect(input, c);
ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
while (rs.next()) 
{
String beforeSim = rs.getString(2);

Application.showMessage("Db Simulator Before Updation ::"+beforeSim);

}
st.close();
Statement st0 = lib.dBConnect(input, c);
//String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><useNumerexSimulator>1</useNumerexSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsPNsForCVR>10300029</xhsPNsForCVR><benrURL>https://secure.api.comcast.net/cvr-billing/qa/rest</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><useNumerexSimulator>1</useNumerexSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsNonReleaseBPNsForCVR></xhsNonReleaseBPNsForCVR><xhsReleaseBPNsForCVR></xhsReleaseBPNsForCVR><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
//String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>1</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><threeDaysDiscDuration>2</threeDaysDiscDuration><threeDaysCVRDiscTableCleanUpDuration>2</threeDaysCVRDiscTableCleanUpDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><SIMInfoICCID>8901640100334741514</SIMInfoICCID><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><oldFlowForAuthId>0</oldFlowForAuthId><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><SIMInfoExResult>0</SIMInfoExResult><getTerminalStatus>RETIRED_NAME</getTerminalStatus><addressUpdateEventTrigger>CSGAccountDetailUpdated,DDPLocationUpdate</addressUpdateEventTrigger><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsPNsForCVR>10600085,10300029</xhsPNsForCVR><benrURL>https://secure.api.comcast.net/cvr-billing/qa/rest</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>PUT</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
st0.executeUpdate(sql);
//<vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType>

st0.close();
Thread.sleep(300000);
Statement st1 = lib.dBConnect(input, c);
ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
while (rs1.next()) 
{
    
String afterSim = rs1.getString(2);
Application.showMessage("Db Simulator After Updation ::"+afterSim);

}
st0.close();


    }
	
	//-----
	
	public void simulatorremovecvraddressUpdates(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException
    {
                    
		LibraryRtp lib=new LibraryRtp();       

                    Statement st = lib.dBConnect(input, c);
ResultSet rs = st.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
while (rs.next()) 
{
String beforeSim = rs.getString(2);

Application.showMessage("Db Simulator Before Updation ::"+beforeSim);

}
st.close();
Statement st0 = lib.dBConnect(input, c);
//String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><useNumerexSimulator>1</useNumerexSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsPNsForCVR>10300029</xhsPNsForCVR><benrURL>https://secure.api.comcast.net/cvr-billing/qa/rest</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><useNumerexSimulator>1</useNumerexSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><fakeIntradoID>12345</fakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><siteId>123456</siteId><isUCC13_11>0</isUCC13_11><NumerexerrorCode>300</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><scheduleUpAndDowngrade>1</scheduleUpAndDowngrade><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><activeStatus>activated</activeStatus><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><addressUpdateEventTrigger>DDPLocationUpdate,CSGAccountDetailUpdated</addressUpdateEventTrigger><getAccountStatus>ACTIVE</getAccountStatus><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>5J80jncxktN7wWdK12OayVou</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>wTvrnQx9DOUINn0lDjTb4a7O</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsNonReleaseBPNsForCVR></xhsNonReleaseBPNsForCVR><xhsReleaseBPNsForCVR></xhsReleaseBPNsForCVR><benrURL>https://cvr-billing-stg.codebig2.net</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>POST</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent><DDSActionADDValue>ADD</DDSActionADDValue><BENROrderStatusPendingInstallValue>PendingInstall</BENROrderStatusPendingInstallValue><BENROrderStatusActiveValue>Active</BENROrderStatusActiveValue></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
//String sql="update CWFTEMPLATECONFIG set DATA='<hsConfig><name>homesecurityUtil:hsConfig</name><vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><useSimulator>0</useSimulator><useUControlSimulator>0</useUControlSimulator><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType><useIControlSimulator>1</useIControlSimulator><useNumerexSimulator>1</useNumerexSimulator><useSIMInfoSimulator>1</useSIMInfoSimulator><useCOPSSimulator>0</useCOPSSimulator><bhsSchema>QA</bhsSchema><ddsSourceServerId>test</ddsSourceServerId><ddsSourceSystemId>test</ddsSourceSystemId><ddsUserID>PROVUSER</ddsUserID><ddsPwd>provpassword&#033;</ddsPwd><useFakeIntradoID>1</useFakeIntradoID><jsVersion>4.3</jsVersion><jsLicenseKey>4599e007-b05b-4aee-a8f2-2be9e16f5d09</jsLicenseKey><jsChangeTypeOrderFileProcessing>3</jsChangeTypeOrderFileProcessing><jsChangeTypeReconFeedProcessing>1</jsChangeTypeReconFeedProcessing><useOldDDS>0</useOldDDS><triggerEtlInsightFlow>1</triggerEtlInsightFlow><enableUpGradeDwnGrade>1</enableUpGradeDwnGrade><enableSMBFlow>1</enableSMBFlow><discDuration>0</discDuration><discLetterDuration>2</discLetterDuration><threeDaysDiscDuration>2</threeDaysDiscDuration><threeDaysCVRDiscTableCleanUpDuration>2</threeDaysCVRDiscTableCleanUpDuration><thirtyDayDiscTableCleanUpDuration>1</thirtyDayDiscTableCleanUpDuration><newInsightService>INSIGHT</newInsightService><newInsightGroup>insight-base</newInsightGroup><SIMInfoICCID>8901640100334741514</SIMInfoICCID><messageId>2810A32F8EB5354D43B195E6D9300B6D</messageId><getAccountStatusConfig>READYFORACTIVATION|NEW|CREATED</getAccountStatusConfig><messageId_deactivateAcct>F32FFA053D7EE6D7911E433B1DB4D214</messageId_deactivateAcct><messageId_createAccount>2810A32F8EB5354D43B195E6D9300B6E</messageId_createAccount><activationCode>277813</activationCode><getAccount>1</getAccount><createAccount>0</createAccount><deactivateAccount>1</deactivateAccount><siteId>123456</siteId><suspendAccount>0</suspendAccount><restoreAccount>0</restoreAccount><deleteUnactivatedAccount>0</deleteUnactivatedAccount><isUCC13_11>0</isUCC13_11><NumerexerrorCode>0</NumerexerrorCode><Dealer>0123</Dealer><enableInsightFlow_DDP>1</enableInsightFlow_DDP><enableInsightFlow_CSG>1</enableInsightFlow_CSG><enableUpDownGradeFlow>1</enableUpDownGradeFlow><swivelcountryHardCodeToUS>1</swivelcountryHardCodeToUS><isFoxtrotFlowEnable>1</isFoxtrotFlowEnable><DDSRetryCount>3</DDSRetryCount><DDSExceptions>More than 1 authorization was found but was only expecting 1.|ldap.CommunicationException|The security token could not be authenticated or authorized</DDSExceptions><hssPassword>9n777srb</hssPassword><hsdUsername>om_user</hsdUsername><hsdPassword>password&amp;#033;</hsdPassword><hssUsername>opuser</hssUsername><jsUserID>SridharPanchagnula</jsUserID><jsPwd>Nilusri@7</jsPwd><isXHSFlowEnabled>0</isXHSFlowEnabled><AllowedScenarios>1,2,3,4,5,11</AllowedScenarios><ServiceChangeActivity>RemovedInstall</ServiceChangeActivity><isHardCodedCOMMRes>1</isHardCodedCOMMRes><xhsFeedDocCleanUpDuration>30</xhsFeedDocCleanUpDuration><xhsFeedDocStoringEnable>1</xhsFeedDocStoringEnable><oldUCTLCOPSFlow>0</oldUCTLCOPSFlow><oldFlowForAuthId>0</oldFlowForAuthId><avoidComcastEmpTag>0</avoidComcastEmpTag><enableMailSend>0</enableMailSend><scheduleUpAndDowngrade>0</scheduleUpAndDowngrade><RemoveBlobForReflow>0</RemoveBlobForReflow><activeStatus>activated</activeStatus><srvName>350 + ThermostatDataProvider + SecureEV|350 + SecureEV|350 + ThermostatDataProvider + ThermostatDataProvider</srvName><getAccRespSrvName>350 + ThermostatDataProvider|350</getAccRespSrvName><replaceInvSrv>350 + ThermostatDataProvider</replaceInvSrv><ordStatuForJobsCheck>Pending</ordStatuForJobsCheck><scheduleDisconnectReleaseTime>06:00</scheduleDisconnectReleaseTime><autoReflowCount>1</autoReflowCount><SIMInfoExResult>0</SIMInfoExResult><getTerminalStatus>RETIRED_NAME</getTerminalStatus><addressUpdateEventTrigger>CSGAccountDetailUpdated,DDPLocationUpdate</addressUpdateEventTrigger><CVRProductNumbers>10400463</CVRProductNumbers><benrHeaderAcceptName>Accept</benrHeaderAcceptName><benrHeaderAcceptValue>application/json</benrHeaderAcceptValue><benrHeaderConsumerKeyName>oauthConsumerKey</benrHeaderConsumerKeyName><benrHeaderConsumerKeyValue>tct84grrdjjvgwwvd66w9wu3</benrHeaderConsumerKeyValue><benrHeaderConsumerSecretName>oauthConsumerSecret</benrHeaderConsumerSecretName><benrHeaderConsumerSecretValue>MkVSVEbv</benrHeaderConsumerSecretValue><benrHeaderPartnerIdName>X-PartnerId</benrHeaderPartnerIdName><benrHeaderPartnerIdValue>xhstool</benrHeaderPartnerIdValue><benrHeaderContentTypeName>Content-Type</benrHeaderContentTypeName><benrHeaderContentTypeValue>application/json</benrHeaderContentTypeValue><benrRequestResourcePath>cvrBillingNotification</benrRequestResourcePath><benrPNRequestResourcePath>notifications</benrPNRequestResourcePath><xhsPNsForCVR>10600085,10300029</xhsPNsForCVR><benrURL>https://secure.api.comcast.net/cvr-billing/qa/rest</benrURL><benrRequestMethod>PUT</benrRequestMethod><benrPNRequestMethod>PUT</benrPNRequestMethod><maxCVRCount>2</maxCVRCount><benrPostEvent>tag:cvr,2015:event/billing/addedToHoldTable</benrPostEvent></hsConfig>'where NAME='homesecurityUtil:hsConfig'";
st0.executeUpdate(sql);
//<vantageImportDir>/opt/instance/bhs/demi</vantageImportDir><vantageArchiveDir>/opt/instance/bhs/demi_archived</vantageArchiveDir><vantageErrorDir>/opt/instance/bhs/demi_error</vantageErrorDir><inputDir>/opt/instance/bhs/ucc</inputDir><iControlDir>/opt/instance/bhs/icontrol</iControlDir><sendToCOM>1</sendToCOM><logComMsg>1</logComMsg><uccPGM>CM5262SI</uccPGM><uccSimType>S</uccSimType>

st0.close();
Thread.sleep(300000);
Statement st1 = lib.dBConnect(input, c);
ResultSet rs1 = st1.executeQuery("select * from CWFTEMPLATECONFIG where NAME ='homesecurityUtil:hsConfig'");
while (rs1.next()) 
{
    
String afterSim = rs1.getString(2);
Application.showMessage("Db Simulator After Updation ::"+afterSim);

}
st0.close();


    }
	//-----
	
	 public void IterationLogic_CVR(Object input, ScriptingContext c)
     {
		 LibraryRtp lib=new LibraryRtp();
       try
       {

                        
             String sc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER");
             String tc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseName");
             String sN=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_ScenarioName");
             Application.showMessage("_______________________________________________");
             Application.showMessage("Starting TestCase...."+tc);
             Application.showMessage("SCENARIO_NUMBER::"+sc);
             Application.showMessage("Scenario is ::"+sN);
             String Time=lib.getTimeInstance();
             c.setValue("T1", Time);
             c.put("T1", Time);
             Application.showMessage("BaseTime is ::"+Time);
             Thread.sleep(2000);
             
             
      
             if( sc.equals("1"))
             {
                     
                     c.setValue("IsInstall","true");
                     c.setValue("IsCos","false");
                     c.setValue("IsSuspend","false");
                     c.setValue("IsRestore","false");
                     c.setValue("IsCancel","false");
                     c.setValue("IsDisconnect","false");
                     c.setValue("IscompletedInstall","false");
                    c.setValue("IsCossameserv","false");

                     c.put("result", "true");
                     if(sN.equalsIgnoreCase("Demi"))
                     {
                                     c.setValue("IsDemi", "true");
                     }
                     else
                     {
                                     c.setValue("IsDemi", "false");    
                     }
                     Application.showMessage("Itearion flag values"+c.getValue("IsInstall")+c.getValue("IsCos")+c.getValue("IsSuspend")+c.getValue("IsRestore")+c.getValue("IsCancel"));
                     Application.showMessage("Iteration set up is for Install at scenario number ::"+sc);
             }
             else if( sc.equals("2"))
             {
                     c.setValue("IsInstall","false");
                     c.setValue("IsCos","true");
                     c.setValue("IsSuspend","false");
                     c.setValue("IsRestore","false");
                     c.setValue("IsCancel","false");
                     c.setValue("IsDisconnect","false");
                     c.setValue("IscompletedInstall","false");
                     c.setValue("IsCossameserv","false");

                     c.put("result", "true");
                     if(sN.equalsIgnoreCase("Demi"))
                     {
                                     c.setValue("IsDemi", "true");
                     }
                     else
                     {
                                     c.setValue("IsDemi", "false");    
                     }
                     Application.showMessage("Itearion flag values"+c.getValue("IsInstall")+c.getValue("IsCos")+c.getValue("IsSuspend")+c.getValue("IsRestore")+c.getValue("IsCancel"));

                     Application.showMessage("Iteration set up is for COS at scenario number ::"+sc);
             }
             
             else if( sc.equals("4"))
             {
                     c.setValue("IsInstall","false");
                     c.setValue("IsCos","false");
                     c.setValue("IsSuspend","true");
                     c.setValue("IsRestore","false");
                     c.setValue("IsCancel","false");
                     c.setValue("IsDisconnect","false");
                     c.setValue("IscompletedInstall","false");
                     c.setValue("IsCossameserv","false");

                     c.put("result", "true");
                     if(sN.equalsIgnoreCase("Demi"))
                     {
                                     c.setValue("IsDemi", "true");
                     }
                     else
                     {
                                     c.setValue("IsDemi", "false");    
                     }
                     Application.showMessage("Is Demi value"+c.getValue("IsDemi"));
                     Application.showMessage("Iteration set up is for SUSPEND at scenario number ::"+sc);
             }
             
             else if( sc.equals("5"))
             {
                     c.setValue("IsInstall","false");
                     c.setValue("IsCos","false");
                     c.setValue("IsSuspend","false");
                     c.setValue("IsRestore","true");
                     c.setValue("IsCancel","false");
                     c.setValue("IsDisconnect","false");
                     c.setValue("IscompletedInstall","false");
                    c.setValue("IsCossameserv","false");
                     
                     c.put("result", "true");
                     if(sN.equalsIgnoreCase("Demi"))
                     {
                                     c.setValue("IsDemi", "true");
                     }
                     else
                     {
                                     c.setValue("IsDemi", "false");    
                     }
                     Application.showMessage("Iteration set up is for RESORE at scenario number ::"+sc);
             }
             
             else if( sc.equals("3"))
             {
                     c.setValue("IsInstall","false");
                     c.setValue("IsCos","false");
                     c.setValue("IsSuspend","false");
                     c.setValue("IsRestore","false");
                     c.setValue("IsCancel","false");
                     c.setValue("IsDisconnect","true");
                     c.setValue("IscompletedInstall","false");
                     c.setValue("IsCossameserv","false");
                     
                     c.put("result", "true");
                     if(sN.equalsIgnoreCase("Demi"))
                     {
                                     c.setValue("IsDemi", "true");
                     }
                     else
                     {
                                     c.setValue("IsDemi", "false");    
                     }
                     Application.showMessage("Iteration set up is for RESORE at scenario number ::"+sc);
             }
             
             else if( sc.equals("11"))
             {
                     c.setValue("IsInstall","false");
                     c.setValue("IsCos","false");
                     c.setValue("IsSuspend","false");
                     c.setValue("IsRestore","false");
                     c.setValue("IsCancel","true");
                     c.setValue("IsDisconnect","false");
                     c.setValue("IscompletedInstall","false");
                     c.setValue("IsCossameserv","false");

                     c.put("result", "true");
                     if(sN.equalsIgnoreCase("Demi"))
                     {
                                     c.setValue("IsDemi", "true");
                     }
                     else
                     {
                                     c.setValue("IsDemi", "false");    
                     }
                     Application.showMessage("Iteration set up is for CANCEL at scenario number ::"+sc);
             }
             else if( sc.equals("8"))
             {
                     c.setValue("IscompletedInstall","true");
                     c.setValue("IsInstall","false");
                     c.setValue("IsCos","false");
                     c.setValue("IsSuspend","false");
                     c.setValue("IsRestore","false");
                     c.setValue("IsCancel","false");
                     c.setValue("IsDisconnect","false");

                     c.put("result", "true");
                     if(sN.equalsIgnoreCase("Demi"))
                     {
                                     c.setValue("IsDemi", "true");
                     }
                     else
                     {
                                     c.setValue("IsDemi", "false");    
                     }
                     Application.showMessage("Iteration set up is for completed restart at scenario number ::"+sc);
             }
             
             else if( sc.equals("21"))
             {
                     c.setValue("IsCossameserv","true");
                     c.setValue("IsInstall","false");
                     c.setValue("IsCos","false");
                     c.setValue("IsSuspend","false");
                     c.setValue("IsRestore","false");
                     c.setValue("IsCancel","false");
                     c.setValue("IsDisconnect","false");

                     c.put("result", "true");
                     if(sN.equalsIgnoreCase("Demi"))
                     {
                                     c.setValue("IsDemi", "true");
                     }
                     else
                     {
                                     c.setValue("IsDemi", "false");    
                     }
                     Application.showMessage("Iteration set up is for completed restart at scenario number ::"+sc);
             }  
            
           
        }
        catch(Exception e)
        {
     
        }
           
      
       }
     
	 public void savesvaluefrominput(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
	 {
		 RTP_ValidationsClass vC=new RTP_ValidationsClass();
		 
		 if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: Channel").equalsIgnoreCase("DDP"))
		 {
			 Application.showMessage("******DDP Flow******");
			 vC.execution(input,c);
			 cvrDDPIdentification(input,c);
		 }
		 else
		 {
			 
		 vC.execution(input,c);
		 }
		 String orderstatus=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_OrderStatus");
		 String ordertype=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_OrderType");
		 if(orderstatus.equalsIgnoreCase("Completed") && ordertype.equalsIgnoreCase("Change") )
		 {
			 Application.showMessage("***********-------------------------*************");
			 Application.showMessage("It is a sidegrade flow, it should not trigger BNER calls even we send CVR number in Install section");
			 Application.showMessage("***********-------------------------*************");
			 c.put("CVR", "NO");
			 c.put("INSIGHTCVR", "NO");
			 Application.showMessage("So...CVR : NO");
		 }
		 
	 }
	 
	 public void cvrDDPIdentification(Object input,ScriptingContext c) throws IOException
	 {
		 
		 String productNumber1=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_PN3orSerInstall_PN1");
		 
		 String productNumber2=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_PN4orSerInstall_PN2");
		 
		 String DisproductNumber1=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SerDis_PN3");
		 
	     int totalCVRCount=0 ;
	     int finalCVR=0;
	     int finalCVR1=0;
	     int finalCVR2=0;
	     int insighttotalCVRCount=0;
	     
	     
		 
		 if(productNumber1.equals("10400463"))
		 {
			 
			 Application.showMessage("CVR Product Exists...");
		  c.put("cvrCount1",c.getValue("RTPNormalScenariosdS", "RTP_InputParams: CountFieldInstall1"));
		  if(c.get("cvrCount1").equals("1"))
		  {
			  finalCVR=1;
		  }
		  else if(c.get("cvrCount1").equals("2"))
		  {
			  finalCVR=2;
		  }
		  else if(c.get("cvrCount1").equals("3"))
		  {
			  finalCVR=3;
		  }
		  else
		  {
			  finalCVR=0;
		  }
			
			 c.put("productNumber1", true);
		 }
		 else
		 {
			 c.put("productNumber1", false);
		 }
		 if(productNumber2.equals("10400463"))
		 {
			 
			   c.put("cvrCount2",c.getValue("RTPNormalScenariosdS", "RTP_InputParams: CountFieldInstall2"));
			   if(c.get("cvrCount2").equals("1"))
				  {
					  finalCVR1=1;
				  }
				  else if(c.get("cvrCount1").equals("2"))
				  {
					  finalCVR1=2;
				  }
				  else if(c.get("cvrCount1").equals("3"))
				  {
					  finalCVR1=3;
				  }
				  else
				  {
					  finalCVR1=0;
				  }
			c.put("productNumber2", true);
		 }
		 else
		 {
			 c.put("productNumber2", false);
		 }
		 if(DisproductNumber1.equals("10400463"))
		 {
			 
			 c.put("cvrCount3",c.getValue("RTPNormalScenariosdS", "RTP_InputParams: CountfieldDisconnect"));
			 if(c.get("cvrCount3").equals("1"))
			  {
				  finalCVR2=1;
			  }
			  else if(c.get("cvrCount3").equals("2"))
			  {
				  finalCVR2=2;
			  }
			  else if(c.get("cvrCount3").equals("3"))
			  {
				  finalCVR2=3;
			  }
			  else
			  {
				  finalCVR2=0;
			  }
		     c.put("DisproductNumber1", true);
		 }
		 else
		 {
			 c.put("DisproductNumber1", false);
		 }
		 
		 if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("1"))
		 { 
			 
			 
			 
			if(c.get("productNumber1").equals(true) && c.get("productNumber2").equals(true) )
			{
				c.put("RemovedInstall",true);
				if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ServiceChangeActivity1").equalsIgnoreCase("RemovedInstall") && c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ServiceChangeActivity2").equalsIgnoreCase("RemovedInstall") )
				{
					totalCVRCount=0;
				}
				else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ServiceChangeActivity1").equalsIgnoreCase("RemovedInstall") || c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ServiceChangeActivity2").equalsIgnoreCase("RemovedInstall") )
				{
					totalCVRCount=finalCVR;
				}
				
				
			}
			else
			{
				c.put("RemovedInstall",false);
				totalCVRCount=finalCVR;
				
			}
		 }
		 else  if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("2"))
		 {
			if(c.get("productNumber1").equals(true) && c.get("DisproductNumber1").equals(true) )
			{
				totalCVRCount=finalCVR-finalCVR2;
				c.put("RemovedInstall",false);
			}
			else
			{
				totalCVRCount=finalCVR;
				c.put("RemovedInstall",false);
			}
		 }
		 else  if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("21"))
		 {
			 if(c.get("productNumber1").equals(true) && c.get("DisproductNumber1").equals(true) )
				{
					totalCVRCount=finalCVR-finalCVR2;
					c.put("RemovedInstall",false);
				}
				else
				{
					totalCVRCount=finalCVR;
					c.put("RemovedInstall",false);
				}
		 }
		 else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("8"))
		 {
			 if(c.get("productNumber1").equals(true) && c.get("DisproductNumber1").equals(true) )
				{
					totalCVRCount=finalCVR-finalCVR1;
					c.put("RemovedInstall",false);
				}
				else
				{
					totalCVRCount=finalCVR;
					c.put("RemovedInstall",false);
				}
		 }
		 else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("4") )
		 {
			 
			if(c.get("productNumber1").equals(true)  )
			{
				 
				totalCVRCount=finalCVR;
				Application.showMessage("Total Cvr Count is"+totalCVRCount);
				c.put("RemovedInstall",false);
			}
		 }
		 else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("5") )
		 {
			if(c.get("productNumber1").equals(true)  )
			{
			
				totalCVRCount=finalCVR;
				Application.showMessage("Total Cvr Count is"+totalCVRCount);
				c.put("RemovedInstall",false);
			}
		 }
		 else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER").equals("11") )
		 {
			if(c.get("productNumber1").equals(true)  )
			{
			
				totalCVRCount=finalCVR;
				Application.showMessage("Total Cvr Count is"+totalCVRCount);
				c.put("RemovedInstall",false);
			}
		 }
		 //--------------count Validation
		  
		 if(c.get("RemovedInstall").equals(true))
		 {
			 if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_ScenarioName").equalsIgnoreCase("Demi"))
			 {
				 c.put("CVR", "YES");
				 c.put("CVRCOUNT", totalCVRCount);
			 }
			 else
			 {
				 c.put("INSIGHTCVR", "YES");
				 c.put("INSIGHTCVRCOUNT", totalCVRCount);
			 }
		 }
		 
		 else  if(totalCVRCount > 0)
		 {
			 if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_ScenarioName").equalsIgnoreCase("Demi"))
			 {
				/* if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE").equals("350"))
				 {
					 int cvr350=totalCVRCount + 1;
					 totalCVRCount=cvr350;
					 Application.showMessage("Total Cvr Count for service 350 is"+totalCVRCount);
					 Application.showMessage(" CVR Eligible is"+c.get("CVR"));
					 Application.showMessage(" CVR Count is"+c.get("CVRCOUNT"));
				 }*/
				 c.put("CVR", "YES");
				 c.put("CVRCOUNT", totalCVRCount);
				 Application.showMessage(" CVR Eligible is"+c.get("CVR"));
				 Application.showMessage(" CVR Count is"+c.get("CVRCOUNT"));
				
			 }
			 else
			 {
				 insighttotalCVRCount=totalCVRCount + 1;
				 totalCVRCount=insighttotalCVRCount;
				 c.put("INSIGHTCVR", "YES");
				 c.put("INSIGHTCVRCOUNT", totalCVRCount);
				 Application.showMessage("Insight CVR Eligible is"+c.get("INSIGHTCVR"));
				 Application.showMessage("Insight CVR Count is"+c.get("INSIGHTCVRCOUNT"));
				 
			 }
		 }
		 else
		 {
			 
		 }
	 }
	 
	 public void getcvrcode(Object input,ScriptingContext c) throws IOException
	 {
		 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Channel").equalsIgnoreCase("DDP"))
		 {
			 DatasourceCVRIdentification(input,c);
		 }
		 else
		 {
		 List<String> ProductNumbers = new ArrayList<String>();
         int payload=0;
       int insightpayload=0;
       Application.showMessage("_______________________________________________");
    ProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN1orCurrentSer_PN1")) ;
    ProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN2orCurrentSer_PN2")) ;
    ProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN3orSerInstall_PN1")) ;
    ProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN4orSerInstall_PN2")) ;
     Application.showMessage("_______________________________________________");
    
     for(int i=0;i<4;i++)
    {
                    if(ProductNumbers.get(i).equals("10400463"))
                    {
                                    payload++;
                                    //c.put("CVR", "YES");
                    }
                    else
                    {
                                    //c.put("CVR", "NO");
                                    
                    }
    }
     if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
		{
    	 c.setValue("IsDemi","true");
		}
     else
     {
    	 c.setValue("IsDemi","false");
     }
    if( c.getValue("IsDemi").equals("false"))
    {
                     insightpayload=payload+1;
    }
     if(payload > 0)
     {
                    c.put("CVR", "YES");
     }
     else
     {
                    c.put("CVR", "NO");
     }
    if(insightpayload > 0)
     {
                    c.put("INSIGHTCVR", "YES");
     }
     else
     {
                    c.put("INSIGHTCVR", "NO");
     }
     Application.showMessage("_______________________________________________");
    c.put("CVRCOUNT", payload);
    c.put("INSIGHTCVRCOUNT", insightpayload);
    if( c.getValue("IsDemi").equals("true"))
    {
    Application.showMessage("CVR Count is ;: "+c.get("CVRCOUNT"));
    Application.showMessage("CVR  is ;: "+c.get("CVR"));
    }
    else
    {
                    Application.showMessage("CVR Count is ;: "+c.get("INSIGHTCVRCOUNT"));
                    Application.showMessage("CVR  is ;: "+c.get("INSIGHTCVR"));
    }
            //return sc;
     Application.showMessage("_______________________________________________");
		 }
		 
	 }
	 //---------------------------------------
	 
	 public void DatasourceCVRIdentification(Object input,ScriptingContext c) throws IOException
	 {
         String productNumber1=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN3orSerInstall_PN1"); 			 
	     int totalCVRCount=0 ;
	     int finalCVR=0;
	     int finalCVR1=0;
	     int finalCVR2=0;
	     int insighttotalCVRCount=0; 
	      
		 if(productNumber1.equals("10400463"))
		 {
			 
			 
		  c.put("cvrCount1",c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: CountFieldInstall1"));
		  if(c.get("cvrCount1").equals("1"))
		  {
			  finalCVR=1;
		  }
		  else if(c.get("cvrCount1").equals("2"))
		  {
			  finalCVR=2;
		  }
		  else if(c.get("cvrCount1").equals("3"))
		  {
			  finalCVR=3;
		  }
		  else
		  {
			  finalCVR=0;
		  }
			
			 c.put("productNumber1", true);
		 }
		 else
		 {
			 c.put("productNumber1", false);
		 }
		
		 
		  if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SCENARIO_NUMBER").equals("3") )
		 {
			if(c.get("productNumber1").equals(true)  )
			{
			
				totalCVRCount=finalCVR;
				Application.showMessage("Total Cvr Count is"+totalCVRCount);
			}
		 }
		 //--------------count Validation
		  if(totalCVRCount > 0)
		 {
			 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
			 {
				 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE").equals("350"))
				 {
					 int cvr350=totalCVRCount + 1;
					 totalCVRCount=cvr350;
					 Application.showMessage("Total Cvr Count for service 350 is"+totalCVRCount);
					 Application.showMessage(" CVR Eligible is"+c.get("CVR"));
					 Application.showMessage(" CVR Count is"+c.get("CVRCOUNT"));
				 }
				 c.put("CVR", "YES");
				 c.put("CVRCOUNT", totalCVRCount);
				 Application.showMessage(" CVR Eligible is"+c.get("CVR"));
				 Application.showMessage(" CVR Count is"+c.get("CVRCOUNT"));
				
			 }
			 else
			 {
				 insighttotalCVRCount=totalCVRCount + 1;
				 totalCVRCount=insighttotalCVRCount;
				 c.put("INSIGHTCVR", "YES");
				 c.put("INSIGHTCVRCOUNT", totalCVRCount);
				 Application.showMessage("Insight CVR Eligible is"+c.get("INSIGHTCVR"));
				 Application.showMessage("Insight CVR Count is"+c.get("INSIGHTCVRCOUNT"));
				 
			 }
		 }
		 else
		 {
			 
		 }
	 }
	 
	 //--SHEDULE UPGRADE/DOWNGRADE
	 
	 
	 public void getcvrcodeSCHEDULE(Object input,ScriptingContext c) throws IOException
	 {
		 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Channel").equalsIgnoreCase("DDP"))
		 {
			 scheduleDatasourceCVRIdentification(input,c);
		 }
		 else
		 {
		 List<String> ProductNumbers = new ArrayList<String>();
         int payload=0;
       int insightpayload=0;
       Application.showMessage("_______________________________________________");
    ProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN3orSerInstall_PN1")) ;
    ProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN4orSerInstall_PN2")) ;
    ProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN5orSerInstall_PN2")) ;
    ProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN6orSerInstall_PN2")) ;
     Application.showMessage("_______________________________________________");
    
     for(int i=0;i<4;i++)
    {
                    if(ProductNumbers.get(i).equals("10400463"))
                    {
                                    payload++;
                                    //c.put("CVR", "YES");
                    }
                    else
                    {
                                    //c.put("CVR", "NO");
                                    
                    }
    }
     if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
		{
    	 c.setValue("IsDemi","true");
		}
     else
     {
    	 c.setValue("IsDemi","false");
     }
    if( c.getValue("IsDemi").equals("false"))
    {
                     insightpayload=payload+1;
    }
     if(payload > 0)
     {
                    c.put("CVR", "YES");
     }
     else
     {
                    c.put("CVR", "NO");
     }
    if(insightpayload > 0)
     {
                    c.put("INSIGHTCVR", "YES");
     }
     else
     {
                    c.put("INSIGHTCVR", "NO");
     }
     Application.showMessage("_______________________________________________");
    c.put("CVRCOUNT", payload);
    c.put("INSIGHTCVRCOUNT", insightpayload);
    if( c.getValue("IsDemi").equals("true"))
    {
    Application.showMessage("CVR Count is ;: "+c.get("CVRCOUNT"));
    Application.showMessage("CVR  is ;: "+c.get("CVR"));
    }
    else
    {
                    Application.showMessage("InsightCVR Count is ;: "+c.get("INSIGHTCVRCOUNT"));
                    Application.showMessage("InsightCVR  is ;: "+c.get("INSIGHTCVR"));
    }
            //return sc;
     Application.showMessage("_______________________________________________");
	 }
	 }
	 
	 
	 //schedule DDP Logic
	 
	 public void scheduleDatasourceCVRIdentification(Object input,ScriptingContext c) throws IOException
	 {
         String productNumber1=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_PN5orSerInstall_PN2"); 			 
	     int totalCVRCount=0 ;
	     int finalCVR=0;
	     int finalCVR1=0;
	     int finalCVR2=0;
	     int insighttotalCVRCount=0; 
	      
		 if(productNumber1.equals("10400463"))
		 {
			 
			 
		  c.put("cvrCount1",c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: CountFieldInstall2"));
		  if(c.get("cvrCount1").equals("1"))
		  {
			  finalCVR=1;
		  }
		  else if(c.get("cvrCount1").equals("2"))
		  {
			  finalCVR=2;
		  }
		  else if(c.get("cvrCount1").equals("3"))
		  {
			  finalCVR=3;
		  }
		  else
		  {
			  finalCVR=0;
		  }
			
			 c.put("productNumber1", true);
		 }
		 else
		 {
			 c.put("productNumber1", false);
		 }
		
		 
		  if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SCENARIO_NUMBER").equals("3") )
		 {
			if(c.get("productNumber1").equals(true)  )
			{
			
				totalCVRCount=finalCVR;
				Application.showMessage("Total Cvr Count is"+totalCVRCount);
			}
		 }
		 //--------------count Validation
		  if(totalCVRCount > 0)
		 {
			 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
			 {
				/* if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE").equals("350"))
				 {
					 int cvr350=totalCVRCount + 1;
					 totalCVRCount=cvr350;
					 Application.showMessage("Total Cvr Count for service 350 is"+totalCVRCount);
					 Application.showMessage(" CVR Eligible is"+c.get("CVR"));
					 Application.showMessage(" CVR Count is"+c.get("CVRCOUNT"));
				 }*/
				 c.put("CVR", "YES");
				 c.put("CVRCOUNT", totalCVRCount);
				 Application.showMessage(" CVR Eligible is"+c.get("CVR"));
				 Application.showMessage(" CVR Count is"+c.get("CVRCOUNT"));
				
			 }
			 else
			 {
				 insighttotalCVRCount=totalCVRCount + 1;
				 totalCVRCount=insighttotalCVRCount;
				 c.put("INSIGHTCVR", "YES");
				 c.put("INSIGHTCVRCOUNT", totalCVRCount);
				 Application.showMessage("Insight CVR Eligible is"+c.get("INSIGHTCVR"));
				 Application.showMessage("Insight CVR Count is"+c.get("INSIGHTCVRCOUNT"));
				 
			 }
		 }
		 else
		 {
			 c.put("CVR", "NO");
			 c.put("INSIGHTCVR", "NO");
		 }
	 }
}

