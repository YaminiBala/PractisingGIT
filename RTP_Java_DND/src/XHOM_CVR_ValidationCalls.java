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
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;
import org.bson.BasicBSONObject;
import org.json.JSONException;
import org.xml.sax.SAXException;

public class XHOM_CVR_ValidationCalls 
{
	
	private static final String String = null;
	public Connection connection = null;
	public static String rowmsg= null;
	 XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	 XHOM_DemiInstall_Validations DI=new XHOM_DemiInstall_Validations();
	 XHOM_BasicMethods BM=new XHOM_BasicMethods();
	 XHOM_DemiDisconnect_Validations DD=new XHOM_DemiDisconnect_Validations();
	 XHOM_ScheduleUpgradeDowngradeExecutionClass UDE=new XHOM_ScheduleUpgradeDowngradeExecutionClass();
	 XHOM_HomeStream_Validations HS=new XHOM_HomeStream_Validations();
	//LibraryRtp lib=new LibraryRtp();
	//RTP_ValidationsClass vC=new RTP_ValidationsClass();
	 
	 //--------------------
	 
	
	 
	 //-------------------
	 public void AllCVRValidations(Object input,ScriptingContext c,String flows,String DDS,String entitlements,String updteBNERValue,String ordStatus) throws Exception
	 {
		 List<String> cvrIssue=new ArrayList<String>();
		switch(flows)
		{
		case "Install":
			processHomeSecurityInfoCVR_Validate(input, c,DDS);
			getEntitilements(input, c,entitlements);
			if(!entitlements.equals(updteBNERValue))
			{
			BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			}
			break;
			
		case "COS":
			processHomeSecurityInfoCVR_Validate(input, c,DDS);
			getEntitilements(input, c,entitlements);
			if(!entitlements.equals(updteBNERValue))
			{
			BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			}
			break;
			
		case "Suspend":
			processHomeSecurityInfoCVR_Validate(input, c,DDS);
			BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			
			break;
			
		case "Restore":
			processHomeSecurityInfoCVR_Validate(input, c,DDS);
			BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			
			break;
			
		case "Disconnect":
			processHomeSecurityInfoCVR_Validate(input, c,"ADD");//DDS_CVRIssues
			cvrIssue.add(c.get("DDS_CVRIssues").toString());
			BNERcallCVR_Validate_Post(input, c);//BNERPostIssues
			cvrIssue.add(c.get("BNERPostIssues").toString());
			BM.XHOM_threeDaycvrTableValidation("Open", input, c);//threeDaycvrTableValidationIssues
			cvrIssue.add(c.get("threeDaycvrTableValidationIssues").toString());
			BM.changingdateThreeDayCVRTable(input, c);
			Thread.sleep(180000);
			BM.XHOM_threeDaycvrTableValidation("Completed", input, c);//threeDaycvrTableValidationIssues
			cvrIssue.add(c.get("threeDaycvrTableValidationIssues").toString());
		    processHomeSecurityInfoCVR_Validate(input, c,"REMOVE");//DDS_CVRIssues
		    cvrIssue.add(c.get("DDS_CVRIssues").toString());
			BNERcallCVR_Validate(input, c, "0", "Disconnected");//BNERIssues
			cvrIssue.add(c.get("BNERIssues").toString());
			XH.generateReport(input, c, cvrIssue, "Disconnect CVr Functionality-->");
			
			break;
			
		
			
		case "Cancel":
			processHomeSecurityInfoCVR_Validate(input, c,DDS);
			getEntitilements(input, c,entitlements);
			if(!entitlements.equals(updteBNERValue))
			{
			BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			}
			break;
			
		default :
			break;
		}
	 }
	 //---------------------------------------------------
	 
	 
	 public void AllCVR4xiValidations(Object input,ScriptingContext c,String flows,String DDS,String entitlements,String updteBNERValue,String ordStatus) throws Exception
	 {
		 List<String> cvrIssue=new ArrayList<String>();
		 XHOM_CVR_AllFlows_ValidationCalls XHCVRAllFlows = new XHOM_CVR_AllFlows_ValidationCalls();
		switch(flows)
		{
		case "Install":
			//processHomeSecurityInfoCVR_Validate(input, c,DDS);
			BM.rtpTestInterface_Validate(input, c); 
			XHCVRAllFlows.getAccountFromDDS(input,c);
			getEntitilements(input, c,entitlements);
			if(!entitlements.equals(updteBNERValue))
			{
			//BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			}
			DI.orderUpdate_Validate_cvr4xi(input, c,"NEW");
			break;
			
		case "COS":
			BM.rtpTestInterface_Validate(input, c); 
			XHCVRAllFlows.getAccountFromDDS(input,c);
			getEntitilements(input, c,entitlements);
			if(!entitlements.equals(updteBNERValue))
			{
			BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			}
			DI.orderUpdate_Validate_cvr4xi(input, c,"CHG");
			break;
			
		case "Suspend":
			BM.rtpTestInterface_Validate(input, c); 
			HS.getAccoutCos_Validate(input,c);
			HS.SuspendAccount_Validate(input, c);
			DI.orderUpdate_Validate_cvr4xi(input, c,"ERR");
			XHCVRAllFlows.getAccountFromDDS(input,c);
			BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			DI.orderUpdate_Validate_cvr4xi(input, c,"ERR");
			
			break;
			
		case "Restore":
			BM.rtpTestInterface_Validate(input, c); 
			HS.getAccoutCos_Validate(input,c);
			HS.RestoreAccount_Validate(input, c);
			DI.orderUpdate_Validate_cvr4xi(input, c,"ERR");
			XHCVRAllFlows.getAccountFromDDS(input,c);
			BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			DI.orderUpdate_Validate_cvr4xi(input, c,"ERR");
			
			break;
			
		case "Disconnect":
			//processHomeSecurityInfoCVR_Validate(input, c,"ADD");//DDS_CVRIssues
			//cvrIssue.add(c.get("DDS_CVRIssues").toString());
			XHCVRAllFlows.getAccountFromDDS(input,c);
			BNERcallCVR_Validate_Post(input, c);//BNERPostIssues
			cvrIssue.add(c.get("BNERPostIssues").toString());
			BM.XHOM_threeDaycvrTableValidation("Open", input, c);//threeDaycvrTableValidationIssues
			cvrIssue.add(c.get("threeDaycvrTableValidationIssues").toString());
			BM.changingdateThreeDayCVRTable(input, c);
			Thread.sleep(180000);
			BM.XHOM_threeDaycvrTableValidation("Completed", input, c);//threeDaycvrTableValidationIssues
			cvrIssue.add(c.get("threeDaycvrTableValidationIssues").toString());
		  //  processHomeSecurityInfoCVR_Validate(input, c,"REMOVE");//DDS_CVRIssues
		  //  cvrIssue.add(c.get("DDS_CVRIssues").toString());
			BNERcallCVR_Validate(input, c, "0", "Disconnected");//BNERIssues
			cvrIssue.add(c.get("BNERIssues").toString());
			XH.generateReport(input, c, cvrIssue, "Disconnect CVr Functionality-->");
			
			break;
			
		
			
		case "Cancel":
			BM.rtpTestInterface_Validate(input, c); 
			XHCVRAllFlows.getAccountFromDDS(input,c);
			getEntitilements(input, c,entitlements);
			if(!entitlements.equals(updteBNERValue))
			{
			BNERcallCVR_Validate(input, c, updteBNERValue, ordStatus);
			}
			DI.orderUpdate_Validate_cvr4xi(input, c,"CAN");
			break;
			
		default :
			break;
		}
	 }
	 
	 //-------------------
	public String processHomeSecurityInfoCVR_Validate(Object input, ScriptingContext c,String DDS) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	   //  c.setValue("processHomeSecurity","true");
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from DDS_CVR:::";
String rscallpresent="true";
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****processHomeSecurityInfo_Validate for CVR  Function******");       
	     Application.showMessage("----------------------------------------------------");
	   //  lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("processHomeSecurity","false");  
	     HashMap Operation=XH.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));


		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=XH.XHOM_reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{

        		 while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
        	        {
        	       obj=rs.next();
        	          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
        	          {
        	 rowmsg=((BasicBSONObject) obj).getString("_id");
        	 
        	 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
        	           {
        	           
        	               Application.showMessage("Your Recieve XML is NULL \n");
        	               
        	               String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
        	               Application.showMessage("Your Recieve XML is::\n"+senddata);
        	           }
        	           else if(((BasicBSONObject) obj).getString("REQUEST")==null)
        	           {
        	               Application.showMessage("Your SEND XML is NULL \n");             
        	               String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
        	               Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
        	           }
        	           else
        	       
        	{
        	           String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
        	           String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
        	           Application.showMessage("Your send XML is::\n"+senddata);
        	           Application.showMessage("RECIEVE XML is ::\n"+recievedata);   
		          	           
		            String accountNumber_DDS= lC.nodeFromKey(senddata,"<accountNumber xmlns=\"\">","</accountNumber>");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_DDS); 	  
	 	           Application.showMessage("accountNumber is ::"+lC.RemoveNameSpaces(senddata)); 
	 	          Application.showMessage("accountNumber is ::"+lC.nodeFromKey(lC.RemoveNameSpaces(senddata),"<accountNumber>","</accountNumber>")); 
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
		      
		 	            String cvrProductNumber_DDS= lC.nodeFromKey(senddata,"<ns2:cvrFeatureProductNumber>","</ns2:cvrFeatureProductNumber>");
		 	            Application.showMessage("CVR Product Number is ::"+cvrProductNumber_DDS);
		 	            
		 	            
		 	            if(cvrProductNumber_DDS==null)
			            {
				            addIssues.add("CVR Product Number is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("CVR Product Number in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+cvrProductNumber_DDS);
			            	 
			            }		

		 	            String CVRProductActionType= lC.nodeFromKey(senddata,"<ns2:actionType>","</ns2:actionType>");
		 	            Application.showMessage("CVRProductActionType is ::"+CVRProductActionType); 
		 	           
		 	           if(CVRProductActionType==null)
			            {
				            addIssues.add("Send Xml CVRProductActionType is NULL");
				            continue;
			            }
			            else if(CVRProductActionType.trim().equalsIgnoreCase(DDS.trim()))
			            {
			            	 Application.showMessage("*******Validation Point 1 :: WebServicall-CVRProductActionType********");
			            	 Application.showMessage("Validation is Successfull with CVRProductActionType::"+" "+CVRProductActionType);
			            	 v1=1;
			            	}
			            else
			            {
			            	// addIssues.add("Send Xml CVRProductActionType is not validated as "+DDS.trim());
					            continue;
			            }
			            	
			            	
			         
			           

		 	            
			           
				            
		 	            
		 	           
		 	         	 	            
		 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<authorizationGuid xmlns=\"\">","</authorizationGuid>");
		 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
		 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
		 	            c.put("authorizationGuid", authorizationGuid_DDS);
		 	            
		 	            String responseStatus_DDS= lC.nodeFromKey(recievedata,"<ns2:responseStatus>","</ns2:responseStatus>");
		 	            Application.showMessage("responseStatus is::"+responseStatus_DDS); 
		 	           if(responseStatus_DDS==null)
			            {
				            addIssues.add(" responseStatus is NULL");
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
				            	 addIssues.add("responseStatus at Send Xml not Validated as "+responseStatus_DDS);
				             }
			            }
		 	                    

		 	            
		            break;
		            }
	             }
	         }
        	        }
	         
	         if(v1*callFound*v4 ==1)
	         {
	        	 c.put("DDS_CVRIssues", "no");
	         	Application.showMessage("WebService Call ::CVR  processHomeSecurityInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	//Yamini
	        	 if(callFound!=1)
	        	 {
	        		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
	                 
	        	 }
		        	 for(int i=0;i<addIssues.size();i++)
		        	 {
		        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
		        	 }
		        	 c.put("DDS_CVRIssues",Issues.concat("WebService Call :: DDS CVR_Validate is Failed with Validation Errors").concat("**"));
		        
    }
	        // st.close();
	         rs.close();
			
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
	
	
	//-----
	public String getEntitilements(Object input,ScriptingContext c,String Value) throws JSONException, InterruptedException, ClassNotFoundException, IOException
	{
		
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from getEntitlements:::";

String rscallpresent="true";
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****getEntitilement _Validate for CVR  Function******");       
	     Application.showMessage("----------------------------------------------------");
	      HashMap Operation=XH.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("getEntitlement"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getEntitlement"));


		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=XH.XHOM_reduceThinkTimeRTP(input, c);
			if(rs == null)
       	{
       		rscallpresent = "false";      		
       	       		
       	}
       	else
{

       		 while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
       	        {
       	       obj=rs.next();
       	          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
       	          {
       	 rowmsg=((BasicBSONObject) obj).getString("_id");
       	 
       	 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
       	           {
       	           
       	               Application.showMessage("Your Recieve XML is NULL \n");
       	               
       	               String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
       	               Application.showMessage("Your Recieve XML is::\n"+senddata);
       	           }
       	           else if(((BasicBSONObject) obj).getString("REQUEST")==null)
       	           {
       	               Application.showMessage("Your SEND XML is NULL \n");             
       	               String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
       	               Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
       	           }
       	           else
       	       
       	{
       	           String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
       	           String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
       	           Application.showMessage("Your send XML is::\n"+senddata);
       	           Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
       	           	           
		            String authorizationId[]=senddata.split("cvrBillingNotification/");
	 	            Application.showMessage("authorizationId[] is ::"+authorizationId[1].replace("}", " ")); 	           
		            if(authorizationId[1].replace("}", " ").trim().equals( c.getValue("authorizationGuid").trim()))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+authorizationId[1].replace("}", " ").trim());
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	            String entitelmnets=XH.extractingValueFromJSONObject(recievedata,"entitlements", input, c); 
                          Application.showMessage("entitelmnets are ::"+entitelmnets);
		 	            
		 	            
		 	            if(entitelmnets==null)
			            {
				            addIssues.add("CVR entitelmnets is NULL");
				            continue;
			            }
			           
		 	            else if(entitelmnets.trim().equalsIgnoreCase(Value.trim()))
			            {
			            	 Application.showMessage("*******Validation Point 1 :: WebServicall-entitelmnets********");
			            	 Application.showMessage("Validation is Successfull with responseStatus::"+" "+entitelmnets);
			            	 v1=1;
			            	}
			            else
			            {
			            	 addIssues.add("Send Xml CVRProductActionType is not validated as "+Value.trim());
					            continue;
			            }
		 	           break;
		            }
	             }
	         }
       	        }
	         
	         if(v1*callFound ==1)
	         {
	         	Application.showMessage("WebService Call ::CVR  getEntitlement is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	//Yamini
		        	 for(int i=0;i<addIssues.size();i++)
		        	 {
		        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
		        	 }
		        	 c.report(Issues.concat("WebService Call :: cvr getEntitlements_Validate is Failed with Validation Errors").concat("**"));
		        
     }
	        // st.close();
	         rs.close();
			
		}
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
		
	}  	
			            	
			        

		 	            
			           
				           
		 	            
		 	           
		 	         	 	            
		 	          
		 	                    
		 	         
		          
	
	//-------
	
	public void DatasourceprocessHomeSecurityInfoCVR_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	  //   c.setValue("processHomeSecurity","true");
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from DDS_CVR:::";
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
			rs=XH.XHOM_reduceThinkTimeRTP(input, c);
			 while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
		        {
		       obj=rs.next();
		          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
		          {
		 rowmsg=((BasicBSONObject) obj).getString("_id");
		 
		 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
		           {
		           
		               Application.showMessage("Your Recieve XML is NULL \n");
		               
		               String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
		               Application.showMessage("Your Recieve XML is::\n"+senddata);
		           }
		           else if(((BasicBSONObject) obj).getString("REQUEST")==null)
		           {
		               Application.showMessage("Your SEND XML is NULL \n");             
		               String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		               Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		           }
		           else
		       
		{
		           String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
		           String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		           Application.showMessage("Your Recieve XML is::\n"+senddata);
		           Application.showMessage("RECIEVE XML is ::\n"+recievedata);
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
				            addIssues.add("Send Xml CVRProductActionType is NULL");
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
				            addIssues.add("CVR Product Number is NULL");
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
				            addIssues.add(" responseStatus is NULL");
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
				            	 addIssues.add("responseStatus at Send Xml not Validated as "+responseStatus_DDS);
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
	        	//Yamini
		        	 for(int i=0;i<addIssues.size();i++)
		        	 {
		        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
		        	 }
		        	 c.report(Issues.concat("WebService Call :: processHomesecurity CVR_Validate is Failed with Validation Errors").concat("**"));
		         
    }
	       //  st.close();
	         rs.close();
		}	
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
//----------------------------------
	
	
	public void scheduleBNERcallCVR_Validate(Object input, ScriptingContext c,String cvrorderstatus,String cvrvalue,boolean post ) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from updateBNER:::";
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";       
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****BNER CALL  Invoke Rest Service_Validate for CVR  Function******");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("BNERcall"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("BNERcall")); 
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/invokeRestService'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=XH.XHOM_reduceThinkTimeRTP(input, c);
			 while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
		        {
		       obj=rs.next();
		          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
		          {
		 rowmsg=((BasicBSONObject) obj).getString("_id");
		 
		 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
		           {
		           
		               Application.showMessage("Your Recieve XML is NULL \n");
		               
		               String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
		               Application.showMessage("Your Recieve XML is::\n"+senddata);
		           }
		           else if(((BasicBSONObject) obj).getString("REQUEST")==null)
		           {
		               Application.showMessage("Your SEND XML is NULL \n");             
		               String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		               Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		           }
		           else
		       
		{
		           String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
		           String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		           Application.showMessage("Your Recieve XML is::\n"+senddata);
		           Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
		            String senddata1=senddata.replaceAll("&lt;","<");
		            String senddata2=senddata1.replaceAll("&gt;",">");
		            String senddata3=senddata2.replaceAll("&amp;#34;,",":");		            
		            String senddata4=senddata3.replaceAll("&amp;#34;:&amp;#34;",">") ;
		            String senddata5=senddata4.replaceAll("&amp;#34;","<") ;		           
		            String senddata7=senddata5.replaceAll(":<order_status>","</timestamp><order_status>") ;
		            String senddata8=senddata7.replaceAll(":<entitlements<:","</order_status>") ;
		            String senddata9=senddata8.replaceAll("<}}","</cvr><payLoad>") ;
		            Application.showMessage("SEND XML is :: \n"+senddata9.trim());    
	            	         
		                        
		            Application.showMessage("**********ResourcePath Validation**********");
		            String ResourcePath= lC.nodeFromKey(senddata9,"<resourcePath>","</resourcePath>");
	 	            Application.showMessage("ResourcePath is ::"+ResourcePath);
	 	            String path=ResourcePath.replaceAll("/cvrBillingNotification/","");
	 	            Application.showMessage("ResourcePath is ::"+path);
	 	           
		      if(post==false)
		      {
		    	  String TimeStamp=lC.nodeFromKey(senddata9,"<timestamp>","</timestamp>");
	 	          
		            if(TimeStamp.equals("NO Data Fetched from in-between strings..!Check the strings..!"))
	          	{
		            	
	          		continue;
	          	}
	          	else
	          	{
	          		Application.showMessage("*******Validation Point 1 :: WebServicall-InvokeRestService Call********");
	          		Application.showMessage("Validation is Successfull with Resource Path"+ResourcePath);
	          		callFound=1;
	          	}
		            
		            if(callFound==1)
		            {
		            	// String TimeStamp=lC.nodeFromKey(senddata9,"<timestamp>","</timestamp>");
		            	 String cvr=lC.nodeFromKey(senddata9,"<cvr>","</cvr>");
		            	 String OrderStatus=lC.nodeFromKey(senddata9,"<order_status>","</order_status>");
		            	Application.showMessage("TimeStamp is :: "+TimeStamp);
		            	Application.showMessage("cvr from Bner call is :: "+cvr);
		            	Application.showMessage("Order Status  from Bner call is :: "+OrderStatus);
		            //	Boolean cvrValue=cvr.trim().equalsIgnoreCase("cvrvalue").toString().trim());
		            	//Application.showMessage("checking condition :: "+cvrValue);
		            	Application.showMessage("checking condition :: "+c.getValue("IsDemi"));
		            	Application.showMessage("**********CVR Validation**********");            	
		           			        
		            	
		 	           	 if(cvr==null)
			            {
				            addIssues.add("cvr value is NULL");
				            continue;
			            }	        	
		 	        	 	          
		 	        	 else  if(cvr.trim().equalsIgnoreCase(cvrvalue.toString().trim()))
		 	          
		 	           {
		 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
	            		 Application.showMessage("Validation is successful with the No of CVR Products");
	            		 
	            		 v1=1;
		 	           }
		 	          else
	            	  {
	            		 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
	            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
	            		
	            		 addIssues.add("Validation is  not successful with the No of CVR Products");
	                	 }
		            	
		            	//----------       	
		            	 	        	 
		 	        	Application.showMessage("**********Order Status  Validation**********"); 
		            	
		            	
		            	 
		            		if(OrderStatus.equalsIgnoreCase(cvrorderstatus))
		            		{
		            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :"+cvrorderstatus);
		            			v2=1;
		            		}
		            		else
		            		{
		            			addIssues.add("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is not validated as "+cvrorderstatus);
		            		}
		            	 
		            	//----           	
		            
		            		            
		 	        Application.showMessage("********Validation in receive xml******")   ;   
		 	           
		 	        String receivedata1=recievedata.replaceAll("&lt;","<");
		            String receivedata2=receivedata1.replaceAll("&gt;",">"); 
		            String receivedata3=receivedata2.replaceAll("&#34;","");
		            Application.showMessage("Recieve XML is::  \n"+ receivedata3);
		            String status=lC.nodeFromKey( receivedata3,"<responseBody>","</responseBody>");
		          /*  if(status.contains("success"))
		            {
		            Application.showMessage("**************Validation is successful for Receive data with status success*****");
		            v3=1;
		            }
		            else
		            {
		            	addIssues.add("Validation is not successful for Receive data with status success");
		            }*/
		 	          
		            v3=1;
		      }
		      }
		      else
		      {
		    	  String method=lC.nodeFromKey(senddata9,"<method>","</method>");
		    	  if(method.equalsIgnoreCase("POST"))
		    	  {
		    		  callFound=1;
		    		  v1=1;
		    		  v2=1;
		    		  v3=1;
		    		  Application.showMessage("Validation is successful with CVR Post Notification");
		    	  }
		      }
		            break;
		            }
	             }
	         
	         
	         if(v1*callFound*v2*v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: InvokeRestservice is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 //Yamini
		        	 for(int i=0;i<addIssues.size();i++)
		        	 {
		        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
		        	 }
		        	 c.report(Issues.concat("WebService Call :: UpdateBNER_Validate is Failed with Validation Errors").concat("**"));
		         
    }
	       //  st.close();
	         rs.close();
		}
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	 public void DisconnectCVRWithoutReleaseValidations(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException,Exception
	    {
	    	ExecutionClass_R er=new ExecutionClass_R();
	    	Thread.sleep(20000);
	    	c.put("CompletedDisconnected", "YES");
	    	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
	    {
	    	if(c.getValue("CVR").equals("YES"))
	        {
	       	DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);          
	       	String statusCheck = "Open";
	        BM.XHOM_thirtyDisconnectTableValidation(statusCheck, input, c);
	       BM.XHOM_threeDaycvrTableValidation(statusCheck,input,c); 
	 
	        c.put("CompletedDisconnectedCVR", "YES");
	      
	        }
	    	else
	    	{
	    		Application.showMessage(" No need of CVR validation");
	    		String statusCheck = "Open";
	            BM.XHOM_thirtyDisconnectTableValidation(statusCheck, input, c);
	          //  runglobalprocesscvr(input, c);
	       	   
	    	}    	
	    	    	 
	     }
	  	
			
	    	else
	    	{
	    		if(c.getValue("CVR").equals("YES"))
	            {
	           	DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);              
	           	Thread.sleep(10000);
	           	String statusCheck = "Open";
	           	BM.XHOM_threeDaycvrTableValidation(statusCheck,input,c); 
	            c.put("CompletedDisconnectedCVR", "YES");
	            }
	        	else
	        	{
	        		Application.showMessage(" No need of CVR validation");
	        		Thread.sleep(10000);
	        		String statusCheck = "Open";
	              BM.XHOM_threeDaycvrTableValidation(statusCheck,input,c); 
	             
	        	}    
	    		       	 
	         }
	    }
	 
	 public void cvr_scheduleupdown_PendingChangevalidation(Object input, ScriptingContext c) throws Exception

	 {
	 	CVR_AllFlows_ValidationCalls val1=new CVR_AllFlows_ValidationCalls();
	 	c.put("CompletedDisconnected","NO");
	 	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("DISCONNECT"))
	 	{
	 		
	 	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: RemoveInDis").equalsIgnoreCase("no"))
	 	{
	 		BM.changingdate(input,  c); 
	 		Thread.sleep(3000);
	 	//val1.runglobalprocesscvr(input, c); need to changed
	 	Thread.sleep(300000); 
	 XHOM_cvr_ReleasefromScheduleDisconnectableValidate(input, c); 
	 	XHOM_SchedulePCCVRValidate( input, c,"PendingInstall","Open");
	 	}
	 	else
	 	{
	 		
	 	}
	 	}
	 	else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("DISCONNECT1"))
	 	{
	 		
	 	}
	 	}
	 public void cvr_scheduleupdown_CompletedChangevalidation(Object input, ScriptingContext c) throws ClassNotFoundException, NullPointerException, InstantiationException, IllegalAccessException, IOException, InterruptedException, SQLException, SAXException, XPathException,Exception
	 {
	 	CVR_AllFlows_ValidationCalls val1=new CVR_AllFlows_ValidationCalls();
	 	RTP_ValidationsClass_UpDown rv1=new RTP_ValidationsClass_UpDown();
	 	New_ValidationCalls ne1=new New_ValidationCalls();
	 	RTP_ValidationsClass rv=new RTP_ValidationsClass();
	 	ExecutionClass_R er=new ExecutionClass_R();
	 	ChangeOfServiceClass CS = new ChangeOfServiceClass();
	 	LibraryRtp  lC= new LibraryRtp ();
	 	CVR_ValidationCalls  cvr=new CVR_ValidationCalls ();
	 	if(c.get("CompletedDisconnected").equals("NO"))
	 	{
	 	if(c.getValue("RemovedInstall").equals("true"))
	 	{
	 		
	         Thread.sleep(2000);
	         Application.showMessage("---Connecting to DB Table------");
	 		ScheduleDisconnectCVRValidations(input, c);
	 		
	 	}
	 	else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("disconnect1"))
	 	{
	 		Thread.sleep(2000);
	         Application.showMessage("---Connecting to DB Table------");
	 	ScheduleDisconnectCVRValidations(input, c);
	 	}
	 	else
	 	{
	 		//rv1.queryLocation_Validate(input,c);
	 		DI.queryLocation_Validate(input, c);
	 	   	   Thread.sleep(3000);
	 	   UDE.ScheduleorderUpdate_Validate(input, c);
	 	   	   Thread.sleep(3000);
	 	   	XHOM_SchedulePCCVRValidate( input, c,"Active","Open");
	 	
	 	}
	 	}
	 	else
	 	{
	 		if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: logics").equalsIgnoreCase("logic1"))
	 		{
	 		UDE.XHOM_scheduleCOSrtpTestInterface_Validate(input, c);
	 	//	cvr_ReleasefromScheduleDisconnectableValidate(input, c);
	 		BM.XHOM_thirtyDisconnectTableValidation("Completed", input, c);
	 		XHOM_SchedulePCCVRValidate( input, c,"PendingInstall","Open");
	 		if( c.get("CompletedDisconnectedCVR").equals("YES"))
	         {
	         	lC.getBaseTime(input, c);
	         	DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
	          	DatasourceBNERcallCVR_Validate(input, c); 
	         }
	 		}
	 		else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: logics").equalsIgnoreCase("logic2"))
	 		{
	 			//rv.queryLocation_Validate(input,c);
	 			//er.thirtyDisconnectTableValidationSimulator("Cancelled", input, c);
	 			BM.XHOM_thirtyDisconnectTableValidation("Cancelled", input, c);
	 			UDE.XHOM_scheduleCOSrtpTestInterface_Validate(input, c);
	 			Map<String, String> ResultMap = lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
	 		     c.setValue("ExistingGroup",ResultMap.get("group"));
	 		     c.setValue("ExistingService", ResultMap.get("service"));
	 		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	 		     ResultMap=lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE"));
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
	            // krishna CS.cosLogicFlow(input, c); 
	             BM.cosLogicFlow_CLS(input, c, c.getValue("ExistingService"),c.getValue("NewService"));
	             
	             //ne1.orderUpdateCos_Validate(input, c);
	             DI.orderUpdateCos_Validate(input, c);
	             XHOM_SchedulePCCVRValidate( input, c,"PendingInstall","Open");
	             //er.thirtyDisconnectTableValidationSimulator("Cancelled", input, c);
	            BM.XHOM_thirtyDisconnectTableValidation("Cancelled", input, c);
	             if( c.get("CompletedDisconnectedCVR").equals("YES"))
	             {
	             	lC.getBaseTime(input, c);
	             DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
	              	DatasourceBNERcallCVR_Validate(input, c); 
	             }
	             
	 		}
	 		else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: logics").equalsIgnoreCase("logic3"))
	 		{
	 			//rv.CosValidationssameservices(input, c);
	 			Thread.sleep(3000);
	 			//er.thirtyDisconnectTableValidationSimulator("Cancelled", input, c);
	 			BM.XHOM_thirtyDisconnectTableValidation("Cancelled", input, c);
	 			XHOM_SchedulePCCVRValidate( input, c,"PendingInstall","Open");
	 			 if( c.get("CompletedDisconnectedCVR").equals("YES"))
	 	            {
	 	            	lC.getBaseTime(input, c);
	 	            	DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
	 	             	DatasourceBNERcallCVR_Validate(input, c); 
	 	            }
	 		}
	 	}
	 	}

	 public void XHOM_SchedulePCCVRValidate(Object input, ScriptingContext c,String cvrStatus,String cvrtablestatus) throws ClassNotFoundException, NullPointerException, InstantiationException, IllegalAccessException, IOException, InterruptedException, SQLException, SAXException, XPathException
	 {		
	 	CVR_AllFlows_ValidationCalls val1=new CVR_AllFlows_ValidationCalls();
	    	  
	    	   if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: Channel").equalsIgnoreCase("DDP"))
	    	   {
	    		   if(c.getValue("CVRValueIDDDP").equalsIgnoreCase("0") && c.getValue("CVRValueIDCDDP").equalsIgnoreCase("0") && c.getValue("CVRPostIDDDP").equalsIgnoreCase("false") && c.getValue("CVRPostIDCDDP").equalsIgnoreCase("false") )
	    		   {
	    			   Application.showMessage("Account Number does not contain CVR features");
	    		   }
	    		   else
	    		   {
	    			  if(c.getValue("CurrentServicesDDP").equals("0")) 
	    			  {
	    				
	    				  if(c.getValue("CVRPostIDDDP").equals("true"))
	    				  {
	    					//DDSCVR_Validate(input, c);
	    					processHomeSecurityInfoCVR_Validate(input,c,"ADD");
	    					scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueIDDDP"),true ); 
	    					BM.XHOM_threeDaycvrTableValidation(cvrtablestatus, input, c);
	    				  }
	    				  else
	    				  {
	    					//DDSCVR_Validate(input, c);
	    					  processHomeSecurityInfoCVR_Validate(input,c,"ADD");
	    					scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueIDDDP"),false ); 
	    					//val1.threeDaycvrTableValidation(cvrtablestatus, input, c);
	    				  }
	    			  }
	    			  else
	    			  {
	    				  
	    				if(c.getValue("CVRPostIDCDDP").equals("true"))
	  				  {
	    					//DDSCVR_Validate(input, c);
	    					processHomeSecurityInfoCVR_Validate(input,c,"ADD");
	    					BM.XHOM_threeDaycvrTableValidation(cvrtablestatus, input, c);
	  				  }
	  				  else
	  				  {
	  					 //DDSCVR_Validate(input, c);
	  					processHomeSecurityInfoCVR_Validate(input,c,"ADD");
	  					 scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueIDCDDP"),false ); 
	  				//	val1.threeDaycvrTableValidation(cvrtablestatus, input, c);
	  				  }
	    			  }
	    		   }
	    	   }
	    	   else
	    	   {
	    		 if(c.getValue("CVRValueID").equalsIgnoreCase("0") && c.getValue("CVRValueIDC").equalsIgnoreCase("0") &&c.getValue("CVRPostID").equalsIgnoreCase("false") && c.getValue("CVRPostIDC").equalsIgnoreCase("false"))
	  		   {
	  			   Application.showMessage("Account Number does not contain CVR features");
	  		   }
	  		   else
	  		   {
	  			  if(c.getValue("CurrentServices").equals("0")) 
	  			  {
	  				
	  				  if(c.getValue("CVRPostID").equals("true"))
	  				  {
	  					//DDSCVR_Validate(input, c);
	  					processHomeSecurityInfoCVR_Validate(input,c,"ADD");
	  					scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueID"),true ); 
	  					BM.XHOM_threeDaycvrTableValidation(cvrtablestatus, input, c);
	  				  }
	  				  else
	  				  {
	  					//DDSCVR_Validate(input, c);
	  					processHomeSecurityInfoCVR_Validate(input,c,"ADD");
	  					scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueID"),false ); 
	  					BM.XHOM_threeDaycvrTableValidation(cvrtablestatus, input, c);
	  				  }
	  			  }
	  			  else
	  			  {
	  				  
	  				if(c.getValue("CVRPostIDC").equals("true"))
	 				  {
	  					//DDSCVR_Validate(input, c);
	  					processHomeSecurityInfoCVR_Validate(input,c,"ADD");
	 				  }
	 				  else
	 				  {
	 					 //DDSCVR_Validate(input, c);
	 					 processHomeSecurityInfoCVR_Validate(input,c,"ADD");
	 					 scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueIDC"),false ); 
	 					 BM.XHOM_threeDaycvrTableValidation(cvrtablestatus, input, c);
	 				  }
	  			  }
	  		   }
	    	   }
	        
	 	}
	 public void DatasourceBNERcallCVR_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
		     DBCursor  rs;
		     DBObject obj = null;
		     List<String> addIssues=new ArrayList();
			    String Issues="Issues from updateBNER:::";
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
				rs=XH.XHOM_reduceThinkTimeRTP(input, c);
				 while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
			        {
			       obj=rs.next();
			          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
			          {
			 rowmsg=((BasicBSONObject) obj).getString("_id");
			 
			 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
			           {
			           
			               Application.showMessage("Your Recieve XML is NULL \n");
			               
			               String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
			               Application.showMessage("Your Recieve XML is::\n"+senddata);
			           }
			           else if(((BasicBSONObject) obj).getString("REQUEST")==null)
			           {
			               Application.showMessage("Your SEND XML is NULL \n");             
			               String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
			               Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
			           }
			           else
			       
			{
			           String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
			           String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
			           Application.showMessage("Your Recieve XML is::\n"+senddata);
			           Application.showMessage("RECIEVE XML is ::\n"+recievedata);
			            
			            
			            
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
					            addIssues.add("cvr value is NULL");
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
				            		 addIssues.add("Validation is  not successful with the No of CVR Products for Cancel");
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
				            		 addIssues.add("Validation is  not successful with the No of CVR Products for Cancel");
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
				            		 addIssues.add("Validation is  not successful with the No of CVR Products for Cancel");
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
				            		// addIssues.add("Validation is  not successful with the No of CVR Products for Cancel");
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
		            		
		            		 addIssues.add("Validation is  not successful with the No of CVR Products");
		            		// v1=1;
		            	 }
			            	}
			            	
			            	else
			            	{
			            		
			            		
			            		if(cvr==null)
					            {
						            addIssues.add("cvr value is NULL");
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
					            		 addIssues.add("Validation is  not successful with the No of CVR Products for Cancel");
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
					            		 addIssues.add("Validation is  not successful with the No of CVR Products for Cancel");
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
					            		 addIssues.add("Validation is  not successful with the No of CVR Products for Cancel");
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
					            		 addIssues.add("Validation is  not successful with the No of CVR Products for Cancel");
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
			            		 
			            		 addIssues.add("Validation is  not successful with the No of CVR Products");
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
			            			addIssues.add("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is not validated as disconnected");
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
			            //	addIssues.add("Validation is not successful for Receive data with status success");
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
		        	 //Yamini
			        	 for(int i=0;i<addIssues.size();i++)
			        	 {
			        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
			        	 }
			        	 c.report(Issues.concat("WebService Call :: UpdateBNER_Validate is Failed with Validation Errors").concat("**"));
			         
	    }
		       //  st.close();
		         rs.close();
			}	
				 
			}
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}
		 }
		
	 
	 public void XHOM_cvr_ReleasefromScheduleDisconnectableValidate(Object input, ScriptingContext c) throws Exception
	 {
	 	CVR_AllFlows_ValidationCalls val1=new CVR_AllFlows_ValidationCalls();
	 	RTP_ValidationsClass_UpDown rv=new RTP_ValidationsClass_UpDown();
	 	RTP_SimulatorClass_UpDown sc=new RTP_SimulatorClass_UpDown();
	 	SuspendClass sus=new SuspendClass();
	 	RTP_SimulatorClass dis=new RTP_SimulatorClass();
	 	New_ValidationCalls ne=new New_ValidationCalls();
	 	
	 	   DI.queryLocation_Validate(input,c);
	 	   Thread.sleep(3000);
	 	   DI.processHomeSecurityInfo_Validate(input, c);
	 	   Thread.sleep(3000);
	 	  
	 	   HS.SuspendAccount_Validate(input, c);
	  	Thread.sleep(3000);
	 	   
	 	   HS.deactivateAccount_Validate_CLS(input, c);
	 	   Thread.sleep(3000);
	 	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown").equalsIgnoreCase("U"))
	 	{
	 		 
	 	   	   
	 	   	   DI.processHomeSecurityInfo_Validate(input, c);
	 	   	   Thread.sleep(3000);
	 	   	   
	 	   	   HS.CLS_createAccount_Validate(input, c);
	 	   	   Thread.sleep(3000);
	 	   	   DI.SetAccountBasicInformation_Validate(input, c);
	 	   	 Thread.sleep(3000);
	 	   	   DI.responderInfo_Validate(input, c);
	 	   	 Thread.sleep(3000);
	 	   	   DI.SetAccountAuthorityInformation_Validate(input, c);
	 	   	 Thread.sleep(3000);
	 	   	   UDE.ScheduleorderUpdate_Validate(input, c);
	 	   	    Thread.sleep(3000);
	 	   	   
	 	}
	 	else
	 	{
	 		
	    	  
	    	   DD.DisconnectAccount_CANCEL_Validate(input, c);
	    	   Thread.sleep(3000);
	    	  DI.processHomeSecurityInfo_Validate(input, c);
	    	   Thread.sleep(3000);
	    	HS.CLS_createAccount_Validate(input, c);
	    
	 	   Thread.sleep(3000);
	 	  UDE.ScheduleorderUpdate_Validate(input, c);
	   	    Thread.sleep(3000);
	    	   
	        
	 	}
	 }
	 public void ScheduleDisconnectCVRValidations(Object input,ScriptingContext c) throws SQLException, Exception
	    {
	    	ExecutionClass_R er=new ExecutionClass_R();
	    	Thread.sleep(20000);
	    	ScheduleUpgradeDowngradeValidation sUD=new ScheduleUpgradeDowngradeValidation();
	    	New_ValidationCalls ne=new New_ValidationCalls();
	    	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Insight"))
	    {
	    		Application.showMessage("The value of CVR is"+c.getValue("CVR"));
	    	if(c.getValue("CVR").equals("YES"))
	        {
	       DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);          
	       	String statusCheck = "Open";
	        BM.XHOM_thirtyDisconnectTableValidation(statusCheck, input, c);
	        BM.validateScheduleDisconnect(input, c, "Cancelled");
	        //runglobalprocesscvr(input, c); needs to changed 
	   	    Thread.sleep(300000);   	  
	   		DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
	     	DatasourceBNERcallCVR_Validate(input, c); 
	        }
	    	else
	    	{
	    		Application.showMessage(" No need of CVR validation");
	    		String statusCheck = "Open";
	            BM.XHOM_thirtyDisconnectTableValidation(statusCheck, input, c);
	           // runglobalprocesscvr(input, c); needs 
	       	    Thread.sleep(300000); 
	    	} 
	    	DI.processHomeSecurityInfo_Validate(input, c);
	 	   Thread.sleep(3000);
	 	   HS.SuspendAccount_Validate(input, c); 
	  	   Thread.sleep(3000);
	 	
	 	   HS.deactivateAccount_Validate_CLS(input, c);
	 	   Thread.sleep(3000);
	 	  UDE.ScheduleorderUpdate_Validate(input, c);
	    	    	 
	     }
	  	
			
	    	else
	    	{
	    		if(c.getValue("CVR").equals("YES"))
	            {
	    		Application.showMessage("The value of CVR is"+c.getValue("CVR"));
	           	DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);              
	           	Thread.sleep(10000);
	           	String statusCheck = "Open";
	           	BM.XHOM_threeDaycvrTableValidation(statusCheck,input,c); 
	           //	runglobalprocesscvr(input, c); needs to be changed
	      	    Thread.sleep(300000);       	  
	      	    DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
	        	DatasourceBNERcallCVR_Validate(input, c); 
	            }
	        	else
	        	{
	        		Application.showMessage(" No need of CVR validation");
	        		Thread.sleep(10000);
	        		String statusCheck = "Open";
	               BM.XHOM_threeDaycvrTableValidation(statusCheck,input,c); 
	              // 	runglobalprocesscvr(input, c); needs to be changed
	             	Thread.sleep(300000);  
	        	} 
	    		DI.processHomeSecurityInfo_Validate(input, c);
	    		   Thread.sleep(3000);
	    		   HS.SuspendAccount_Validate(input, c); 
	    	 	Thread.sleep(3000);
	    		 HS.deactivateAccount_Validate_CLS(input, c);
	    		   Thread.sleep(3000);
	    		   UDE.ScheduleorderUpdate_Validate(input, c);
	    		       	 
	         }
	    }
	 
	 public String BNERcallCVR_Validate(Object input, ScriptingContext c,String cvrNo,String OrdStatus) throws InterruptedException, ClassNotFoundException, IOException, JSONException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
		     DBCursor  rs;
		     DBObject obj = null;
		     List<String> addIssues=new ArrayList();
			    String Issues="Issues from updateBNER:::";
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
		     Application.showMessage("*****Update BNER CALL  Invoke Rest Service_Validate for CVR  Function******");       
		     Application.showMessage("----------------------------------------------------");
		   //  lC.findThinktimeOperationRTP(input, c);
		  //   c.setValue("invokeRestservice","false"); 
		     HashMap Operation=XH.findingoperations(input, c);
		     c.setValue("OPERATIONVALIDATION",(String) Operation.get("updateBenr"));
		     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("updateBenr"));


			try
			{
				// Statement st =lC. dBConnect(input, c);	
		       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/invokeRestService'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
				rs=XH.XHOM_reduceThinkTimeRTP(input, c);
				while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
		        {
		       obj=rs.next();
		          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
		          {
		 rowmsg=((BasicBSONObject) obj).getString("_id");
		 
		 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
		           {
		           
		               Application.showMessage("Your Recieve XML is NULL \n");
		               
		               String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
		               Application.showMessage("Your Recieve XML is::\n"+senddata);
		           }
		           else if(((BasicBSONObject) obj).getString("REQUEST")==null)
		           {
		               Application.showMessage("Your SEND XML is NULL \n");             
		               String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		               Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		           }
		           else
		       
		{
		           String senddata=((BasicBSONObject) obj).getString("REQUEST").replace("} {", "}and{"); 
		           String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		           Application.showMessage("Your send XML is::\n"+senddata);
		           Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
			          
			            String newSenddata[]=senddata.split("and");
			            
			                Application.showMessage("**********ResourcePath Validation**********");
			            String ResourcePath[]= newSenddata[0].replaceAll("}", "").split("cvrBillingNotification/");
		 	            Application.showMessage("ResourcePath is ::"+ResourcePath[1].trim());
		 	           
		 	         String TimeStamp=XH.extractingValueFromJSONObject(newSenddata[1],"timestamp", input, c); 
                     
		 	        Application.showMessage("authorizationGuid"+c.getValue("authorizationGuid"));
			            if(ResourcePath[1].trim().equals((c.getValue("authorizationGuid"))) )
		            	{
			            	
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-InvokeRestService Call********");
		            		Application.showMessage("Validation is Successfull with Resource Path"+ResourcePath);
		            		callFound=1;
		            		
		            		
		            	}
			            else 
			            {
		            		continue;
		            	}
		            	 if(TimeStamp.equalsIgnoreCase("null") || TimeStamp==null)
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
			            	 String cvr=XH.extractingValueFromJSONObject(newSenddata[1],"cvr", input, c); 
		                      String OrderStatus=XH.extractingValueFromJSONObject(newSenddata[1],"order_status", input, c); 
			            	Application.showMessage("TimeStamp is :: "+TimeStamp);
			            	Application.showMessage("cvr from Bner call is :: "+cvr);
			            	Application.showMessage("Order Status  from Bner call is :: "+OrderStatus);
			            	
			            	Application.showMessage("**********CVR Validation**********");            	
			           			          
			            	  if(cvr.equals(cvrNo))
				 	           {
			 	        		
				 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is successful with the No of CVR Products");
			            		 v1=1;
			 	        		
				 	           }
			 	        	
			 	          else  
			 	        	 
			 	              {
			            		 addIssues.add("Validation is  not successful with the No of CVR Products");
			            		// v1=1;
			            	  }
			            	

			 	        	 
			 	        	Application.showMessage("**********Order Status  Validation**********"); 
			 	        	 if(OrderStatus.equals(OrdStatus))
				 	           {
			 	        		
				 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
			            		 Application.showMessage("Validation is successful with the No of CVR Products");
			            		 v2=1;
			 	        		
				 	           }
			 	        	
			 	          else  
			 	        	 
			 	              {
			            		 addIssues.add("Validation is  not successful with the No of CVR Products");
			            		// v1=1;
			            	  }
			            	
					            
			 	      Application.showMessage("********Validation in receive xml******")   ;   
			 	     
			 	    String receivecvr=XH.extractingValueFromJSONObject(recievedata,"cvr", input, c); 
                    String receiveOrderStatus=XH.extractingValueFromJSONObject(recievedata,"order_status", input, c); 
	            	Application.showMessage("TimeStamp is :: "+TimeStamp);
	            	Application.showMessage("cvr from Bner call is :: "+receivecvr);
	            	Application.showMessage("Order Status  from Bner call is :: "+receiveOrderStatus);
	            	
	            	Application.showMessage("**********CVR Validation**********");            	
	           			          
	            	  if(receivecvr.equals(cvrNo))
		 	           {
	 	        		
		 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
	            		 Application.showMessage("Validation is successful with the No of CVR Products");
	            		 v3=1;
	 	        		
		 	           }
	 	        	
	 	          else  
	 	        	 
	 	              {
	            		 addIssues.add("Validation is  not successful with the No of CVR Products");
	            		// v1=1;
	            	  }
	            	

	 	        	 
	 	        	Application.showMessage("**********Order Status  Validation**********"); 
	 	        	 if(receiveOrderStatus.equals(OrdStatus))
		 	           {
	 	        		
		 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
	            		 Application.showMessage("Validation is successful with the No of CVR Products");
	            		 v4=1;
	 	        		
		 	           }
	 	        	
	 	          else  
	 	        	 
	 	              {
	            		 addIssues.add("Validation is  not successful with the No of CVR Products");
	            		// v1=1;
	            	  }
	            	
		                
			 	    
			            
			 	          
			            
			            break;
			            }
		             }
		         }
		        }
		         
		         if(v1*callFound*v2*v3*v4 ==1)
		         {
		        	 c.put("BNERIssues", "no");
		         	Application.showMessage("WebService Call :: Update Benr is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 c.put("result", "false");
		        	 //Yamini
		        	 if(callFound!=1)
		        	 {
		        		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
		                 
		        	 }
			        	 for(int i=0;i<addIssues.size();i++)
			        	 {
			        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
			        	 }
			        	 c.put("BNERIssues",Issues.concat("WebService Call :: UpdateBNER_Validate is Failed with Validation Errors").concat("**"));
			         
	    }
		       //  st.close();
		         rs.close();
			}	
			
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				
			}
			return rscallpresent;
		 }
	 
	 
	 //-----------------
	 
	 public String BNERcallCVR_Validate_Post(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, JSONException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp  lC= new LibraryRtp ();
		     DBCursor  rs;
		     DBObject obj = null;
		     List<String> addIssues=new ArrayList();
			    String Issues="Issues from updateBNER:::";
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
		     Application.showMessage("*****Update BNER CALL Post Notification Invoke Rest Service_Validate for CVR  Function******");       
		     Application.showMessage("----------------------------------------------------");
		   //  lC.findThinktimeOperationRTP(input, c);
		  //   c.setValue("invokeRestservice","false"); 
		     HashMap Operation=XH.findingoperations(input, c);
		     c.setValue("OPERATIONVALIDATION",(String) Operation.get("updateBenrNotifications"));
		     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("updateBenr"));


			try
			{
				// Statement st =lC. dBConnect(input, c);	
		       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/invokeRestService'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
				rs=XH.XHOM_reduceThinkTimeRTP(input, c);
				while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
		        {
		       obj=rs.next();
		          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
		          {
		 rowmsg=((BasicBSONObject) obj).getString("_id");
		 
		 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
		           {
		           
		               Application.showMessage("Your Recieve XML is NULL \n");
		               
		               String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
		               Application.showMessage("Your Recieve XML is::\n"+senddata);
		           }
		           else if(((BasicBSONObject) obj).getString("REQUEST")==null)
		           {
		               Application.showMessage("Your SEND XML is NULL \n");             
		               String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		               Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		           }
		           else
		       
		{
		           String senddata=((BasicBSONObject) obj).getString("REQUEST").replace("} {", "}and{"); 
		           String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		           Application.showMessage("Your send XML is::\n"+senddata);
		           Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
			          
			            String newSenddata[]=senddata.split("and");
			            
			                Application.showMessage("**********ResourcePath Validation**********");
			            String ResourcePath[]= newSenddata[0].replaceAll("}", "").split("cvrBillingNotification/");
		 	            Application.showMessage("ResourcePath is ::"+ResourcePath[1].trim().replaceAll("/notifications"," ").trim());
		 	           
		 	         String TimeStamp=XH.extractingValueFromJSONObject(newSenddata[1],"timestamp", input, c); 
                  
		 	          
			            if(ResourcePath[1].trim().replaceAll("/notifications"," ").trim().equals((c.get("authorizationGuid"))) )
		            	{
			            	
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-InvokeRestService Call********");
		            		Application.showMessage("Validation is Successfull with Resource Path"+ResourcePath[1].trim().replaceAll("/notifications"," ").trim());
		            		callFound=1;
		            		
		            		
		            	}
			            else 
			            {
		            		continue;
		            	}
		            	 if(TimeStamp.equalsIgnoreCase("null") || TimeStamp==null)
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
			            	 String event=XH.extractingValueFromJSONObject(newSenddata[1],"event", input, c); 
		                     
			            	Application.showMessage("Event  from Bner call is :: "+event);
			            	
			            	
					            
			 	      Application.showMessage("********Validation in receive xml******")   ;   
			 	     
			 	    String receiveevent=XH.extractingValueFromJSONObject(recievedata,"event", input, c); 
                 	Application.showMessage("Event  from Bner call in Receive XML is :: "+receiveevent);
	            	
	            	
		                
			 	    
			            
			 	          
			            
			            break;
			            }
		             }
		         }
		        }
		         
		         if(callFound ==1)
		         {
		        	 c.put("BNERPostIssues", "no");
		         	Application.showMessage("WebService Call :: Update Benr post Notification is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 c.put("result", "false");
		        	 //Yamini
		        	 if(callFound*time!=1)
		        	 {
		        		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
		                 
		        	 }
			        	 for(int i=0;i<addIssues.size();i++)
			        	 {
			        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
			        	 }
			        	 c.put("BNERPostIssues",Issues.concat("WebService Call :: UpdateBNER Post _Validate is Failed with Validation Errors").concat("**"));
			         
	    }
		       //  st.close();
		         rs.close();
			}	
			
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				
			}
			return rscallpresent;
		 }
}

