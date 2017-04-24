
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.xpath.XPathExpressionException;

import org.bson.BasicBSONObject;
import org.xml.sax.SAXException;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


/**
* @author 266216 
 *
*/
/**
* @author 266216
*
*/

public class XHOM_DemiDisconnect_Validations 
{
                //private static final ScriptingContext ScriptingContext = null;
                //private static final Object Object = null;
                public String XML_AccNumber;
                public String XML_ScenarioName;
                public static String SERVICEorCURRENTSERVICE;
                public String NEWSERVICE;
                public String HOUSE_KEY;
                
               // LibraryRtp Lp=new LibraryRtp();
               // LibraryRtp_UpDown lUP=new LibraryRtp_UpDown();
               // XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
                
                public Connection connection = null;
                public static String rowmsg= null;
                
   // public String[][] aa = new String[10][20];
    //CVR_ValidationCalls cvr=new CVR_ValidationCalls();
    
   
    //------------Methods
    
    
    
public String suspend_COPS_validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
	Application.showMessage("Yamini");
	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
		//Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	   //  String Time= c.get("BaseTime").toString();
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from suspendCops:::";
String rscallpresent="true";
	  //   c.setValue("suspendAccount","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****suspend_COPS_validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SuspendAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SuspendAccount"));
	    // lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("suspendAccount","false"); 
	  
	     
		try
		{
			//Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/SuspendAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=XH.XHOM_reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
        	{
			//rs=lC.reduceThinkTimeRTP(input, c);
        		 while (rs.hasNext() )
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
		      
		            	String readBack= lC.nodeFromKey((((lC.nodeFromKey(recievedata,"<SuspendAccountReturn>","</SuspendAccountReturn>")).replaceAll("&lt;", "<")).replaceAll("&gt;", ">")),"<readBack>","</readBack>");
			            Application.showMessage("readBack is ::"+readBack);
			           
			            
			           
			            if(readBack==null)
			            {
				            addIssues.add("Send Xml readBack is NULL");
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
				            	 addIssues.add("readBack at Send Xml not Validated as "+readBack);
				             }
			            }	
			            
			   
				           
			            String result= lC.nodeFromKey(lC.nodeFromKey(recievedata,"<SuspendAccountReturn>","</SuspendAccountReturn>").replaceAll("&lt;", "<").replaceAll("&gt;", ">"),"<result>","</result>");
			            Application.showMessage("result is ::"+result);
			            
			            if(result==null)
			            {
				            addIssues.add("Send Xml result is NULL");
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
				            	 addIssues.add("result at Send Xml not Validated as "+result);
				             }
			            }	
			            
			            String pc= lC.nodeFromKey(senddata,"<pc>","</pc>");
			            Application.showMessage("pc is ::"+pc);
			            
			            if(pc==null)
			            {
				            addIssues.add("Send Xml pc is NULL");
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
				            	 addIssues.add("pc at Send Xml not Validated as "+pc);
				             }
			            }	

			            String dn= lC.nodeFromKey(senddata,"<dn>","</dn>");
			            Application.showMessage("dn is::"+dn); 
			            
			            if(dn==null)
			            {
				            addIssues.add("Send Xml dn is NULL");
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
				            	 addIssues.add("dn at Send Xml not Validated as "+dn);
				             }
			            }	
			            
		               
		            break;
		            }
		            
		                        
	             }
	         }
        	        }
	         if(v1 * v2 * v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: suspend_COPS_validate is Success[All validation points are Success]");
	         }
	         else
	         {//Yamini
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.report(Issues.concat("WebService Call :: Suspend_COPS is Failed with Validation Errors").concat("**"));
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

public String processHomeSecurityInfo_Validate_Sus(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	
	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     DBCursor  rs;
     DBObject obj = null;
     List<String> addIssues=new ArrayList();
	    String Issues="Issues from processHomesecuritySuspend:::";
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
    // c.setValue("processHomeSecurity","true");
     String rscallpresent="true";
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
     Application.showMessage("----------------------------------------------------");
   //  lC.findThinktimeOperationRTP(input, c);
   //  c.setValue("processHomeSecurity","false");
     HashMap Operation=XH.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));
	try
	{
	//	 Statement st =lC. dBConnect(input, c);	
      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
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
           Application.showMessage("Your Recieve XML is::\n"+senddata);
           Application.showMessage("RECIEVE XML is ::\n"+recievedata);      
	          	           
	            String accountNumber_DDS= lC.nodeFromKey(senddata,"<accountNumber xmlns=\"\">","</accountNumber>");
	            Application.showMessage("accountNumber is ::"+accountNumber_DDS); 	           
	            if(accountNumber_DDS.equals(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber")))
            	{
	            	//Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	//Application.showMessage("SEND XML is :: \n"+senddata);
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
	      
	 	           
	 	            String homeSecurityLOBStatus_DDSsus= lC.nodeFromKey(senddata,"<ns2:homeSecurityLOBStatus>","</ns2:homeSecurityLOBStatus>");
                        Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDSsus); 
	 	            if(homeSecurityLOBStatus_DDSsus==null)
		            {
			            addIssues.add(" homeSecurityLOBStatus is NULL");
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
	 	            
	 	           
	 	         	 	            
	 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<authorizationGuid xmlns=\"\">","</authorizationGuid>");
                      Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
	 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
	 	            
	 	            String responseStatus_DDS= lC.nodeFromKey(recievedata,"<ns2:responseStatus>","</ns2:responseStatus>");
                      Application.showMessage("responseStatus is::"+responseStatus_DDS); 
	 	           if(responseStatus_DDS==null)
		            {
			            addIssues.add(" responseStatus is NULL");
			           
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
			            	 addIssues.add("responseStatus at Send Xml not Validated as "+responseStatus_DDS);
			             }
		            }
	 	                    

	 	            
	            break;
	            }
             }
          }}
         
         if(v1*callFound*v2 ==1)
         {
         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
         }
         else
         {//Yamini
        	 for(int i=0;i<addIssues.size();i++)
        	 {
        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
        	 }
        	 c.report(Issues.concat("WebService Call :: ProcessHomesecurityInfo_Validate is Failed with Validation Errors").concat("**"));
         }
      //   st.close();
         rs.close();
	}	}
    	
		
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }

public String orderUpdate_Validate_Sus(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	
	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
	DBCursor  rs;
	DBObject obj = null;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
	// String xmldata1 ="receive_data";
	 List<String> addIssues=new ArrayList();
	    String Issues="Issues from setOrderstatusSus:::";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
    // c.setValue("OrderUpdate","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********OrderUpdate_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
    // lC.findThinktimeOperationRTP(input, c);
   //  c.setValue("OrderUpdate","false");  
     HashMap Operation=lC.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
	try
	{
		//Statement st =lC. dBConnect(input, c);	
      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=XH.XHOM_reduceThinkTimeRTP(input, c);
		if(rs == null)
    	{
    		rscallpresent = "false";
    	}
    	else
    	{
    		while (rs.hasNext())
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
				            addIssues.add("Send Xml billingOrderId is NULL");
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
				            	 addIssues.add("billingOrderId at Send Xml not Validated as "+billingOrderId);
				             }
			            }
		 	           
		 	            String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
		 	            Application.showMessage("Service is ::"+Service); 
		 	            
		 	           if(Service==null)
			            {
				            addIssues.add("Send Xml Service is NULL");
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
				            	 addIssues.add("Service at Send Xml not Validated as "+Service);
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
	 			            addIssues.add("Send Xml Service is NULL");
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
	 			            	 addIssues.add("Service at Send Xml not Validated as "+Service);
	 			             }
	 		            }	
	 	            }
	 	            String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
	 	            Application.showMessage("inputChannel is ::"+inputChannel);
	 	            
	 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
	 	            Application.showMessage("ordType is ::"+ordType);
	 	            
	 	           if(ordType==null)
		            {
			            addIssues.add("Send Xml ordType is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
		            	 if(ordType.equals("NPD"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with ordType::"+" "+ordType);
			            	 v2=1;
			             }
			             else
			             {
			            	 addIssues.add("ordType at Send Xml not Validated as "+ordType);
			             }
		            }	
	 	            
	 	            
	 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
	 	            Application.showMessage("customerType is ::"+customerType); 
	 	            
	 	           if(customerType==null)
		            {
			            addIssues.add("Send Xml customerType is NULL");
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
			            	 addIssues.add("customerType at Send Xml not Validated as "+customerType);
			             }
		            }	
	 	            
	 	            if(senddata.contains("error"))
		 	         {
		 	        	 addIssues.add("Error in the OrderUpdate Call...!");
		 	         }
		 	         else
		 	         {
		 	        	 Application.showMessage("No errors detected...!");
		 	        	 v5=1;
		 	         }
	 	           
	 	           
	 	          
	 	           /* String errorCode= lC.nodeFromKey(senddata,"</value><value name=\"errorCode\">","</value><value name=\"errorText\">");
	 	            Application.showMessage("errorCode is ::"+errorCode); 
	 	            
	 	           if(errorCode==null)
		            {
			            addIssues.add("Send Xml errorCode is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("customerType from Send Xml   is ::"+" "+errorCode);
		            	 if(errorCode.equals("UCNTRL-RESP-CODE-UCE-15113"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
			            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
			            	 v5=1;
			             }
			             else
			             {
			            	 addIssues.add("errorCode at Send Xml not Validated as "+errorCode);
			             }
		            }	
	 	            */
	 	           	 	           
	            break;
	            }
            }
               
         }
         if(v1*callFound*v2*v3*v4 * v5 ==1)
         {
        	 c.setValue("IsDemi", "false");
         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
         }
         else
         {
        	 c.setValue("IsDemi", "false");
        	//Yamini
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.report(Issues.concat("WebService Call :: setOrderstatus_Validate is Failed with Validation Errors").concat("**"));
	        
         }
       //  st.close();
         rs.close();
	}	
	}
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }

public String Activate_COPS_validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
{
	
	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	// Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
	 DBCursor  rs;
	 List<String> addIssues=new ArrayList();
	    String Issues="Issues from Activate_COPS:::";
	 DBObject obj = null;
	 int callFound=0,v1=0,v2=0,v3=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
   //  c.setValue("activateAccount","true");
     Application.showMessage("-------------------------------------------------");
     Application.showMessage("*****Activate_COPS_validate Function******");       
     Application.showMessage("-------------------------------------------------");
   ///  lC.findThinktimeOperationRTP(input, c);
    // c.setValue("activateAccount","false"); 
     HashMap Operation=lC.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ActivateAccount"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ActivateAccount"));
     
  
     
	try
	{
		// Statement st =lC. dBConnect(input, c);	
      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/ActivateAccount' order by creation_time desc)where rownum <= 20");
		rs=XH.XHOM_reduceThinkTimeRTP(input, c);
		if(rs == null)
    	{
    		rscallpresent = "false";
    	}
    	else
    	{
		 while (rs.hasNext())
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
	      
	            	String readBack= lC.nodeFromKey((((lC.nodeFromKey(recievedata,"<ActivateAccountReturn>","</ActivateAccountReturn>")).replaceAll("&lt;", "<")).replaceAll("&gt;", ">")),"<readBack>","</readBack>");
			            Application.showMessage("readBack is ::"+readBack);
		           
		            
		           
		            if(readBack==null)
		            {
			            addIssues.add("Send Xml readBack is NULL");
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
			            	 addIssues.add("readBack at Send Xml not Validated as "+readBack);
			             }
		            }	
		            
		            String result= lC.nodeFromKey((((lC.nodeFromKey(recievedata,"<ActivateAccountReturn>","</ActivateAccountReturn>")).replaceAll("&lt;", "<")).replaceAll("&gt;", ">")),"<result>","</result>");
		            Application.showMessage("result is ::"+result);
		            
		            if(result==null)
		            {
			            addIssues.add("Send Xml result is NULL");
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
			            	 addIssues.add("result at Send Xml not Validated as "+result);
			             }
		            }	
		            
		            String pc= lC.nodeFromKey(senddata,"<pc>","</pc>");
		            Application.showMessage("pc is ::"+pc);
		            
		            if(pc==null)
		            {
			            addIssues.add("Send Xml pc is NULL");
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
			            	 addIssues.add("pc at Send Xml not Validated as "+pc);
			             }
		            }	

		            String dn= lC.nodeFromKey(senddata,"<dn>","</dn>");
		            Application.showMessage("dn is::"+dn); 
		            
		            if(dn==null)
		            {
			            addIssues.add("Send Xml dn is NULL");
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
			            	 addIssues.add("dn at Send Xml not Validated as "+dn);
			             }
		            }	
	               
	            break;
	            }
	            
	                        
             }
         }
		        }
         if(v1 ==1)
         {
         	Application.showMessage("WebService Call :: Activate_COPS_validate is Success[All validation points are Success]");
         	c.put("Activate_COPSIssue","no");
         }
         else
         {//Yamini
        	 if(callFound!=1)
        	 {
        		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
                 
        	 }
        	 for(int i=0;i<addIssues.size();i++)
        	 {
        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
        	 }
        	// c.report(Issues.concat("WebService Call :: Activate_Cops is Failed with Validation Errors").concat("**"));
        	 c.put("Activate_COPSIssue", Issues.concat("WebService Call :: Activate_Cops is Failed with Validation Errors").concat("**"));
         }
       //  st.close();
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

public String processHomeSecurityInfo_Validate_Dis(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException
{
	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	Thread.sleep(4000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     DBCursor  rs; 
     DBObject obj = null;

	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     List<String> addIssues=new ArrayList();
	    String Issues="Issues from processHomesecurityDis:::";
String rscallpresent="true";
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
     Application.showMessage("----------------------------------------------------");
     HashMap Operation=XH.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));
	try
	{
		// Statement st =lC. dBConnect(input, c);	
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
		rs=XH.XHOM_reduceThinkTimeRTP(input, c);
		if(rs.equals("null"))
    	{
			 c.put("DDSInstall",c.getValue("OPERATIONVALIDATION")+ " Not found on time");
 		 
 		  Application.showMessage("Entering..");
 		 rscallpresent = "false";
 		
    		rs.close();
    	
    		
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
    	           Application.showMessage("Your Recieve XML is::\n"+senddata);
    	           Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
 
	          	           
	            String accountNumber_DDS= lC.nodeFromKey(senddata,"<accountNumber xmlns=\"\">","</accountNumber>");
 	            Application.showMessage("accountNumber is ::"+accountNumber_DDS); 	           
	            if(accountNumber_DDS.equals(c.getValue("ACC_input")))
            	{
	            	//Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	//Application.showMessage("SEND XML is :: \n"+senddata);
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
	      
	 	           
	 	           


	 	            String homeSecurityLOBStatus_DDS= lC.nodeFromKey(senddata,"<ns2:homeSecurityLOBStatus>","</ns2:homeSecurityLOBStatus>");
	 	            Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
	 	            if(homeSecurityLOBStatus_DDS==null)
		            {
			            addIssues.add(" homeSecurityLOBStatus is NULL");
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
	 	            
	 	            
	 	         	 	            
	 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<authorizationGuid xmlns=\"\">","</authorizationGuid>");
	 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
	 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
	 	            
	 	            String responseStatus_DDS= lC.nodeFromKey(recievedata,"<ns2:responseStatus>","</ns2:responseStatus>");
	 	            Application.showMessage("responseStatus is::"+responseStatus_DDS); 
	 	           if(responseStatus_DDS==null)
		            {
			            addIssues.add(" responseStatus is NULL");
			           
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
			            	 addIssues.add("responseStatus at Send Xml not Validated as "+responseStatus_DDS);
			             }
		            }
	 	                    

	 	            
	            break;
	            }
             }
         }
    	        }
         if(v1*callFound*v2 ==1)
         {
        	 c.put("DDSInstall", "no");
         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
         }
         else
         {
        	 //Yamini
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.put("DDSInstall",Issues.concat("WebService Call :: ProcessHomesecurityInfo_Validate is Failed with Validation Errors").concat("**"));
	         
         }
       //  st.close();
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
public String DisconnectAccount_CANCEL_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
{

	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	 // Think time in JVM [waits for 10 secs here]
    LibraryRtp  lC= new LibraryRtp ();
    DBCursor  rs; 
    DBObject obj = null;

	 int callFound=0,v1=0,v2=0,v3=0;
	 String xmldata1 ="receive_data";
    String xmldata2 ="SEND_DATA";
    List<String> addIssues=new ArrayList();
    String Issues="Issues from DisconnectAccount:::";
String rscallpresent="true";
    Application.showMessage("-------------------------------------------------");
    Application.showMessage("*******DisconnectAccount  Function********");       
    Application.showMessage("-------------------------------------------------");
    System.out.println("-------------------------------------------------");
    System.out.println("*******DisconnectAccount Function********");       
    System.out.println("-------------------------------------------------");
    
    
    HashMap Operation=lC.findingoperations(input, c);
    c.setValue("OPERATIONVALIDATION",(String) Operation.get("DisconnectAccount"));
    Application.showMessage("OPERATIONValidation is "+(String) Operation.get("DisconnectAccount"));
    System.out.println("OPERATIONValidation is "+(String) Operation.get("DisconnectAccount"));
    
	try
	{
		// Statement st =lC. dBConnect(input, c);	
     //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/DisconnectAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
               Application.showMessage("Your send XML is::\n"+senddata);
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
	        String extractVal=lC.nodeFromKey(recievedata,"<DisconnectAccountReturn>","</DisconnectAccountReturn>").replace("&lt;", "<").replace("&gt;", ">");
	            	String readBack= lC.nodeFromKey(lC.nodeFromKey(recievedata,"<DisconnectAccountReturn>","</DisconnectAccountReturn>").replace("&lt;", "<").replace("&gt;", ">"),"<readBack>","</readBack>");
		            Application.showMessage("readBack is ::"+readBack);
		            System.out.println("readBack is ::"+readBack);
		           
		            
		           
		            if(readBack==null)
		            {
			            addIssues.add("Send Xml readBack is NULL");
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
			            	 addIssues.add("readBack at Send Xml not Validated as "+readBack);
			             }
		            }	
		            
		            String result= lC.nodeFromKey(lC.nodeFromKey(recievedata,"<DisconnectAccountReturn>","</DisconnectAccountReturn>").replace("&lt;", "<").replace("&gt;", ">"),"<result>","</result>");
			        
		            Application.showMessage("result is ::"+result);
		            System.out.println("result is ::"+result);
		            if(result==null)
		            {
			            addIssues.add("Send Xml result is NULL");
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
			            	 addIssues.add("result at Send Xml not Validated as "+result);
			             }
		            }	
		            
		            String pc= lC.nodeFromKey(senddata,"<pc>","</pc>");
		            Application.showMessage("pc is ::"+pc);
		            System.out.println("pc is ::"+pc);
		            
		            if(pc==null)
		            {
			            addIssues.add("Send Xml pc is NULL");
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
			            	 addIssues.add("pc at Send Xml not Validated as "+pc);
			             }
		            }	

		            String dn= lC.nodeFromKey(senddata,"<dn>","</dn>");
		            Application.showMessage("dn is::"+dn); 
		            System.out.println("dn is::"+dn); 
		            
		            if(dn==null)
		            {
			            addIssues.add("Send Xml dn is NULL");
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
			            	 addIssues.add("dn at Send Xml not Validated as "+dn);
			             }
		            }	
	               
	            break;
	            }
	            
	                        
            }
        }
        }
        if(v1 ==1)
        {
        	c.put("DisconnectAccountIssues", "no");
        	Application.showMessage("WebService Call :: DisconnectAccount is Success[All validation points are Success]");
        	System.out.println("WebService Call :: DisconnectAccount is Success[All validation points are Success]");
	       
        }
        else
        {//Yamini
        	if(callFound!=1)
       	 {
       		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+c.get("BaseTime"));
                
       	 }
       	 for(int i=0;i<addIssues.size();i++)
       	 {
       		 Issues=Issues.concat(addIssues.get(i)).concat(",");
       	 }
       	c.put("DisconnectAccountIssues",Issues.concat("WebService Call :: DisconnectAccount_Validate is Failed with Validation Errors").concat("**"));
        }
     //   st.close();
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
public String orderUpdate_COD(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException
{
	
	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();

	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     DBCursor  rs; 
     DBObject obj = null;
     List<String> addIssues=new ArrayList();
	    String Issues="Issues from setOrderstatusCOD:::";
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
    	           Application.showMessage("Your Recieve XML is::\n"+senddata);
    	           Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
   
	           
	          	           
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
				            addIssues.add("Send Xml billingOrderId is NULL");
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
				            	 addIssues.add("billingOrderId at Send Xml not Validated as "+billingOrderId);
				             }
			            }
		 	           
		 	            String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
		 	            Application.showMessage("Service is ::"+Service); 
		 	            
		 	           if(Service==null)
			            {
				            addIssues.add("Send Xml Service is NULL");
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
				            	 
				            	 addIssues.add("Service at Send Xml not Validated as "+Service);
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
	 			            addIssues.add("Send Xml Service is NULL");
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
	 			            	
	 			            	 addIssues.add("Service at Send Xml not Validated as "+Service);
	 			             }
	 		            }	
	 	            }
	 	          
	 	            String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
	 	            Application.showMessage("inputChannel is ::"+inputChannel);
	 	            
	 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
	 	            Application.showMessage("ordType is ::"+ordType);
	 	            
	 	           if(ordType==null)
		            {
			            addIssues.add("Send Xml ordType is NULL");
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
			            	 addIssues.add("Order Type at Send Xml not Validated as "+ordType);
			             }
		            }	
	 	            
	 	            
	 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
	 	            Application.showMessage("customerType is ::"+customerType); 
	 	            
	 	           if(customerType==null)
		            {
			            addIssues.add("Send Xml customerType is NULL");
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
			            	 addIssues.add("customerType at Send Xml not Validated as "+customerType);
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
        	 addIssues.add("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
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
	        	//Yamini
		        	 for(int i=0;i<addIssues.size();i++)
		        	 {
		        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
		        	 }
		        	 c.report(Issues.concat("WebService Call :: setOrderstatus_Validate is Failed with Validation Errors").concat("**"));
		        
	         }
	        }
        else
        {
        	
        }
       //  st.close();
        rs.close();
	}
		
	
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

public String processHomeSecurityInfo_Validate_Res(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
// Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     DBCursor  rs = null;
     DBObject obj = null;
int callFound=0,v1=0,v2=0,v3=0,v4=0;
String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     List<String> addIssues=new ArrayList();
	    String Issues="Issues from processHomesecurityRes:::";
     String rscallpresent="true";
   //  c.setValue("processHomeSecurity","true");
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
     Application.showMessage("----------------------------------------------------");
    // lC.findThinktimeOperationRTP(input, c);
    // c.setValue("processHomeSecurity","false");  
     HashMap Operation=XH.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));
try
{
// Statement st =lC. dBConnect(input, c); 
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
for(int i=0;i<5;i++)
{
if(i==0)
{
rs=XH.XHOM_reduceThinkTimeRTP(input, c);
}
else
{
rs=XH.XHOM_reduceThinkTimeRTPmsgid(input, c,  c.get("RowMsg").toString());
}
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
c.put("RowMsg", ((BasicBSONObject) obj).getString("_id"));
Application.showMessage("RowMsg"+rowmsg);
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
                    
           String accountNumber_DDS= lC.nodeFromKey(senddata,"<accountNumber xmlns=\"\">","</accountNumber>");
         Application.showMessage("accountNumber is ::"+accountNumber_DDS);           
           if(accountNumber_DDS.equals(c.getValue("ACC_input")))
            {
            //Application.showMessage("Recieve XML is::  \n"+ recievedata);
            //Application.showMessage("SEND XML is :: \n"+senddata);
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
     
          

           String homeSecurityLOBStatus_DDS= lC.nodeFromKey(senddata,"<ns2:homeSecurityLOBStatus>","</ns2:homeSecurityLOBStatus>");
                     Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
           if(homeSecurityLOBStatus_DDS==null)
           {
           addIssues.add(" homeSecurityLOBStatus is NULL");
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
            addIssues.add("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+homeSecurityLOBStatus_DDS);
            }
           }
           
           
                   
           String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<authorizationGuid xmlns=\"\">","</authorizationGuid>");
                      Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
           c.setValue("authorizationGuid", authorizationGuid_DDS);
           
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
            v2=1;
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
         if(v1*callFound*v2 ==1)
         {
          Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
          c.put("DDS_Res","no");
          break;
          }
         else
         {
         
        // addIssues.add("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
         
         }
       //  st.close();
         rs.close();
} 
}
if(v1*callFound*v2 !=1)
         {
	//Yamini
	
	if(callFound!=1)
	 {
		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
        
	 }
   	 for(int i=0;i<addIssues.size();i++)
   	 {
   		 Issues=Issues.concat(addIssues.get(i)).concat(",");
   	 }
   	// c.report(Issues.concat("WebService Call :: processhomesecurity_Validate is Failed with Validation Errors").concat("**"));
   c.put("DDS_Res",Issues.concat("WebService Call :: processhomesecurity_Validate is Failed with Validation Errors").concat("**"));
         
          }
}
catch (SQLException e)
{
   System.out.println("Connection Failed! Check output console");
e.printStackTrace();
}
return rscallpresent;
 }


public String orderUpdate_Validate_Res(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	
	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     DBCursor  rs;
     DBObject obj = null;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
     List<String> addIssues=new ArrayList();
	    String Issues="Issues from setOrderstatusRes:::";
    // c.setValue("OrderUpdate","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********OrderUpdate_Validate Function**************");       
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
		rs=XH.XHOM_reduceThinkTimeRTP(input, c);
		if(rs == null)
    	{
    		rscallpresent = "false";
    		
    	
    		
    	}
    	else
    	{
    		while (rs.hasNext())
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
				            addIssues.add("Send Xml billingOrderId is NULL");
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
				            	 addIssues.add("billingOrderId at Send Xml not Validated as "+billingOrderId);
				             }
			            }
		 	           
		 	            String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
		 	            Application.showMessage("Service is ::"+Service); 
		 	            
		 	           if(Service==null)
			            {
				            addIssues.add("Send Xml Service is NULL");
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
				            	 addIssues.add("Service at Send Xml not Validated as "+Service);
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
	 			            addIssues.add("Send Xml Service is NULL");
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
	 			            	 addIssues.add("Service at Send Xml not Validated as "+Service);
	 			             }
	 		            }	
	 	            }
	 	          
	 	            String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
	 	            Application.showMessage("inputChannel is ::"+inputChannel);
	 	            
	 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
	 	            Application.showMessage("ordType is ::"+ordType);
	 	            
	 	           if(ordType==null)
		            {
			            addIssues.add("Send Xml ordType is NULL");
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
			            addIssues.add("Send Xml customerType is NULL");
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
			            	 addIssues.add("customerType at Send Xml not Validated as "+customerType);
			             }
		            }	
	 	            
	 	         if(senddata.contains("error"))
	 	         {
	 	        	 addIssues.add("Error in the OrderUpdate Call...!");
	 	         }
	 	         else
	 	         {
	 	        	 Application.showMessage("No errors detected...!");
	 	        	 v5=1;
	 	         }
	 	           
	 	        /*  String errorCode= lC.nodeFromKey(senddata,"</value><value name=\"errorCode\">","</value><value name=\"errorText\">");
	 	          Application.showMessage("errorCode is ::"+errorCode); 
	 	            
	 	           if(errorCode==null)
		            {
			            addIssues.add("Send Xml errorCode is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("customerType from Send Xml   is ::"+" "+errorCode);
		            	 if(errorCode.equals("UCNTRL-RESP-CODE-UCE-15113"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
			            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
			            	 v5=1;
			             }
			             else
			             {
			            	 addIssues.add("errorCode at Send Xml not Validated as "+errorCode);
			             }
		            }	*/
	 	            
	 	           	 	           
	            break;
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
        	//Yamini
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.report(Issues.concat("WebService Call :: setOrderstatus_Validate is Failed with Validation Errors").concat("**"));
	            }
      //   st.close();
         rs.close();
	}
	}
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }

public String orderUpdate_Validate_Can(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	
	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     DBCursor  rs;
     DBObject obj = null;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     List<String> addIssues=new ArrayList();
	    String Issues="Issues from setOrderstatusCan:::";
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
		rs=XH.XHOM_reduceThinkTimeRTP(input, c);
		if(rs == null)
    	{
    		rscallpresent = "false";
    		
    	
    		
    	}
    	else
{
    		 while (rs.hasNext())
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
				            addIssues.add("Send Xml billingOrderId is NULL");
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
				            	 addIssues.add("billingOrderId at Send Xml not Validated as "+billingOrderId);
				             }
			            }
		 	           
		 	            String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
		 	            Application.showMessage("Service is ::"+Service); 
		 	           System.out.println("Service is ::"+Service); 
		 	            
		 	           if(Service==null)
			            {
				            addIssues.add("Send Xml Service is NULL");
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
				            	 addIssues.add("Service at Send Xml not Validated as "+Service);
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
	 			            addIssues.add("Send Xml Service is NULL");
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
	 			            	 addIssues.add("Service at Send Xml not Validated as "+Service);
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
			            addIssues.add("Send Xml ordType is NULL");
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
			            	 addIssues.add("lt_fireNumber at Send Xml not Validated as "+ordType);
			             }
		            }	
	 	            
	 	            
	 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
	 	            Application.showMessage("customerType is ::"+customerType); 
	 	           System.out.println("customerType is ::"+customerType); 
	 	            
	 	           if(customerType==null)
		            {
			            addIssues.add("Send Xml customerType is NULL");
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
			            	 addIssues.add("customerType at Send Xml not Validated as "+customerType);
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
        	 //Yamini
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.report(Issues.concat("WebService Call :: setOrderstatus_Validate is Failed with Validation Errors").concat("**"));
	        
         }
       //  st.close();
         rs.close();
	}	
	}
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }

public void getTerminalDetails_Validate(Object input, ScriptingContext c)
        throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException {
        Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
        
        XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
LibraryRtp_UpDown lC = new LibraryRtp_UpDown();
LibraryRtp lr=new LibraryRtp();
DBCursor  rs;
DBObject obj = null;
int callFound = 0, v1 = 0;
String xmldata1 = "receive_data";
String xmldata2 = "SEND_DATA";
String Time= c.get("BaseTime").toString();
//    c.setValue("getTerminalDetails","true");
List<String> addIssues=new ArrayList();
String Issues="Issues from getTerminalDetails:::";
Application .showMessage("-------------------------------------------------");
Application .showMessage("*****getTerminalDetails_Validate Function******");
Application .showMessage("-------------------------------------------------");
System.out.println("-------------------------------------------------");
System.out.println("*****getTerminalDetails_Validate Function******");
System.out.println("-------------------------------------------------");

//  lr.findThinktimeOperationRTP(input, c);
//  c.setValue("getTerminalDetails","false");
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("GetTerminalDetails"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("GetTerminalDetails"));

System.out.println("OPERATIONValidation is "+(String) Operation.get("GetTerminalDetails"));



try
{
    //    Statement st = lC.dBConnect(input, c);
    //    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'jaspersrv:TerminalPortType/GetTerminalDetails' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"

                                //        + c.get("BaseTime").toString()
                               //         + "' order by creation_time desc)where rownum <= 20");
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
       Application.showMessage("Your send XML is::\n"+senddata);
       Application.showMessage("RECIEVE XML is ::\n"+recievedata);
                                        
                                        String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
                                        Application.showMessage("receiveDataTrim:::"+ receiveDataTrim);
                                        System.out.println("receiveDataTrim:::"+ receiveDataTrim);
                                        String correlationId = lC.nodeFromKey( recievedata,"<correlationId xmlns=\"\">","</correlationId>");
                                        Application.showMessage("getTerminalDetails_Validate is ::"+ correlationId);
                                        System.out.println("getTerminalDetails_Validate is ::"
                                                + correlationId);
                                        String sICCID = lC.nodeFromKey(recievedata, "<iccid>","</iccid>");
                                        Application.showMessage("sICCID is :: " + sICCID);
                                        System.out.println("sICCID is :: " + sICCID);

                                        if (sICCID == null) {
                                                    addIssues.add("Send Xml sStatus is NULL");
                                                    continue;
                                        } else {
                                                        String iccId = c.getValue("RTPDataSourceCollections",
                                                                                        "RTP_UpDownInputs: iccId");
                                                        Application.showMessage("iccId Status from input is::"
                                                                                        + iccId);
                                                        System.out.println("iccId Status from input is::"
                                                                + iccId);
                                                        if (sICCID.equalsIgnoreCase(iccId)) {
                                                                        Application.showMessage("*******Validation Point 2 :: WebServicall-sICCID********");
                                                                        Application .showMessage("Validation is Successfull with iccId::"+ " " + sICCID);
                                                                        System.out.println("*******Validation Point 2 :: WebServicall-sICCID********");
                                                                        System.out.println("Validation is Successfull with iccId::"
                                                                                                                                                 + " " + sICCID);
                                                                        v1 = 1;
                                                                        callFound = 1;
                                                        } else {
                                                                        addIssues.add("iccId at Send Xml not Validated as "
                                                                                                        + sICCID);
                                                                        v1 = 0;
                                    }
                                                     }

                                        if (callFound == 1) {

                                                        String sStatus = lC.nodeFromKey(recievedata,
                                                                                        "<status>","</status>");
                                                        Application.showMessage("sStatus is :: " + sStatus);
                                                        System.out.println("sStatus is :: " + sStatus);

                                                        if (sStatus == null) {
                                                    addIssues.add("Send Xml sStatus is NULL");
                                                    continue;
                                                        } else {
                                                                        String getTerminalDetailStatus = c.getValue(
                                                                                                        "RTPDataSourceCollections",
                                                                                                        "RTP_UpDownInputs: getTerminalStatus");
                                                                        Application.showMessage("Simulator Status from input is::" + getTerminalDetailStatus);
                                                                        System.out.println("Simulator Status from input is::" + getTerminalDetailStatus);
                                                                        if (sStatus.equalsIgnoreCase(getTerminalDetailStatus)) {
                                                                                        Application  .showMessage("*******Validation Point 2 :: WebServicall-SimInfoStatus********");
                                                                                        System.out.println("*******Validation Point 2 :: WebServicall-SimInfoStatus********");
                                                                                        Application                                                                                                                                                                .showMessage("Validation is Successfull with sStatus::"
                                                                                                                                                        + " " + sStatus);
                                                                                        v1 = 1;
                                                                        } else {
                                                                                        v1 = 1;
                                                                                      //  addIssues.add("sStatus at Send Xml not Validated as "
                                                                                                                     //   + sStatus);
                                    }
                                                     }

                                                        String terminalId = lC.nodeFromKey(recievedata,
                                                                                        "<terminalId>", "</terminalId>");
                                                        Application.showMessage("terminalId is::" + " "
                                                                                        + terminalId);
                                                        System.out.println("terminalId is::" + " "
                                                                + terminalId);

                                                        String dateActivated = lC.nodeFromKey(recievedata,
                                                                                        "<dateActivated>", "</dateActivated>");
                                                        Application.showMessage("date Activated is::" + " "
                                                                                        + dateActivated);
                                                        System.out.println("date Activated is::" + " "
                                                                + dateActivated);

                                                        String dateAdded=lC.nodeFromKey(recievedata,"<dateAdded>", "</dateAdded>");
                                                        /*String dateAdded = lC.GetValueByXPath(sXml, sxpath)(recievedata,
                                                                                        "<ns2:dateAdded>", "<ns2:dateAdded>");*/
                                                        Application.showMessage("date Added is::" + " "+ dateAdded);
                                                        System.out.println("date Added is::" + " "
                                                                + dateAdded);
                                                        String dateModified=lC.nodeFromKey(recievedata,"<dateAdded>", "</dateAdded>");
                                                        /*String dateModified = lC.nodeFromKey(recievedata,
                                                                                        "<ns2:dateModified>", "</ns2:dateModified>");*/
                                                        Application.showMessage("date Modified is::" + " "
                                                                                        + dateModified);
                                                        System.out.println("date Modified is::" + " "
                                                                + dateModified);


                                                        String dateShipped=lC.nodeFromKey(recievedata,"<dateShipped>", "</dateShipped>");
                                                        
                                                        /*String dateShipped = lC.nodeFromKey(recievedata,
                                                                                        "<ns2:dateShipped>", "</ns2:dateShipped>");*/
                                                        Application.showMessage("date Shipped is::" + " "
                                                                                        + dateShipped);
                                                        System.out.println("date Shipped is::" + " "
                                                                + dateShipped);
                                                        String termStartDate=lC.nodeFromKey(recievedata,"<termStartDate>", "</termStartDate>");
                                                        /*String termStartDate = lC.nodeFromKey(recievedata,
                                                                                        "<ns2:termStartDate>", "</ns2:termStartDate>");*/
                                                        Application.showMessage("term StartDate is::" + " "
                                                                                        + termStartDate);
                                                        System.out.println("term StartDate is::" + " "
                                                                + termStartDate);
                                                        String termEndDate=lC.nodeFromKey(recievedata,"<termEndDate>", "</termEndDate>");
                                                             /*String termEndDate = lC.nodeFromKey(recievedata,
                                                                                        "<ns2:termEndDate>", "</ns2:termEndDate>");*/
                                                        Application.showMessage("term EndDate is::" + " "
                                                                                        + termEndDate);
                                                        System.out.println("term EndDate is::" + " "
                                                                + termEndDate);


                                                        String renewalPolicy = lC.nodeFromKey(recievedata,
                                                                                        "<renewalPolicy>", "</renewalPolicy>");
                                                        Application.showMessage("renewal Policy is::" + " "
                                                                                        + renewalPolicy);
                                                        System.out.println("renewal Policy is::" + " "
                                                                + renewalPolicy);

                                                        String simNotes = lC.nodeFromKey(recievedata,
                                                                                        "<simNotes>", "</simNotes>");
                                                        Application.showMessage("sim Notes is::" + " "
                                                                                        + simNotes);
                                                        System.out.println("sim Notes is::" + " "
                                                                + simNotes);
                                                        String messageId = lC.nodeFromKey(senddata,
                                                                                        "<messageId xmlns=\"\">",
                                                                                        "</messageId>");
                                                        Application.showMessage("messageId is::" + " "
                                                                                        + messageId);
                                                        System.out.println("messageId is::" + " "
                                                                + messageId);

                                                        String version = lC.nodeFromKey(senddata,
                                                                                        "<version xmlns=\"\">", "</version>");
                                                        Application.showMessage("version is::" + " " + version);
                                                        System.out.println("version is::" + " " + version);

                                                        String licenseKey = lC.nodeFromKey(senddata,
                                                                                        "<licenseKey xmlns=\"\">",
                                                                                        "</licenseKey>");
                                                        Application.showMessage("licenseKey is::" + " "
                                                                                        + licenseKey);
                                                        System.out.println("licenseKey is::" + " "
                                                                + licenseKey);

                    break;
                    }
     }
 }
    }
        if (v1 * callFound == 1) {
        	c.put("getTerminalDetailsIssues", "no");
                        Application .showMessage("WebService Call :: getTerminalDetails_Validate is Success[All validation points are Success]");
                        System.out.println("WebService Call :: getTerminalDetails_Validate is Success[All validation points are Success]");
        } else {
         c.put("result", "false");
         //Yamini
         if(callFound!=1)
    	 {
    		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+c.get("BaseTime"));
             
    	 }
        	 for(int i=0;i<addIssues.size();i++)
        	 {
        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
        	 }
        	 c.put("getTerminalDetailsIssues",Issues.concat("WebService Call :: getTerminalDetails_Validate is Failed with Validation Errors").concat("**"));
        
 }
    

//   st.close();
	  rs.close();
} catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
                        e.printStackTrace();
                        return;
        }
}

public void Jasper_Validate(Object input, ScriptingContext c)
        throws InterruptedException, IOException, ClassNotFoundException {

// Think time in JVM [waits for 10 secs here]
LibraryRtp_UpDown lC = new LibraryRtp_UpDown();
LibraryRtp lr=new  LibraryRtp();
XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
DBCursor  rs;
DBObject obj = null;
String xmldata1 = "receive_data";
String xmldata2 = "SEND_DATA";
int callFound = 0;
String rscallpresent="true";
String Time= c.get("BaseTime").toString();
List<String> addIssues=new ArrayList();
String Issues="Issues from JasperValidate:::";
//    c.setValue("Jasper","true");
Application .showMessage("----------------------------------------------------------");
                       
Application.showMessage("*****jasper Function******");
Application.showMessage("----------------------------------------------------------");
        int v1=0;                
//    lr.findThinktimeOperationRTP(input, c);
//    c.setValue("Jasper","false"); 
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("EditTerminal"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("EditTerminal"));


try {
   //     Statement st = lC.dBConnect(input, c);
   //     rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'jaspersrv:TerminalPortType/EditTerminal' order by creation_time desc)where rownum <= 20");
	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
if(rs==null)
{
	c.put("JasVal",addIssues.add(c.getValue("OPERATIONVALIDATION")+ " Not found on time"));
	 
	  
	 rscallpresent = "false";
	 rs.close();
}
	while (rs.hasNext())
    {
   obj=rs.next();
      if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
      {
rowmsg=((BasicBSONObject) obj).getString("_id");
c.put("JasRowMsg", rowmsg);

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

                                        String ICCID = lC.nodeFromKey(senddata,
                                                         				"<iccid xmlns=\"\">",
                                                                        "</iccid>");

                                        if (ICCID == null) {
                                                        System.out.printf("ICCID value from JasperCall is ::NULL \n");
                                                        continue;
                                        } else {

                                                        Application.showMessage("ICCID value from JasperCall is::"  + ICCID);
                                                        System.out.printf("ICCID value from JasperCall is::"  + ICCID);

                                                        if (ICCID.contains("8901650")) {
                                                                      //  Application.showMessage("ICCID msgid= " + rs.getString(1));
                                                                        Application.showMessage("Recieve XML is  \n"  + recievedata);
                                                                        Application.showMessage("SEND XML is  \n"  + senddata);
                                                                        Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                        Application .showMessage("Validation is Successfull with Input ICCID"
                                                                                                                                        + ICCID);
                                                                        
                                                                      // System.out.println("ICCID msgid= " + rs.getString(1));
                                                                       System.out.println("Recieve XML is  \n"  + recievedata);
                                                                       System.out.println("SEND XML is  \n"  + senddata);
                                                                       System.out.println("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                       System.out.println("Validation is Successfull with Input ICCID"
                                                                                                                                        + ICCID);
                                                                     
                                                                        callFound = 1;
                                                        } else {
                                                                        continue;
                                                        }

                                                        if (callFound == 1) {
                                                                        String Jasp_version = lC.nodeFromKey(senddata,
                                                                                                                                        "<version xmlns=\"\">",
                                                                                                                                        "</version>");

                                                                        Application.showMessage("SEND Xml Jasp_version is ::"+ Jasp_version);
                                                                        System.out.println("SEND Xml Jasp_version is ::"+ Jasp_version);

                                                                        String Jas_licenseKey = lC.nodeFromKey(senddata,
                                                                                "<licenseKey xmlns=\"\">",
                                                                                "</licenseKey>");
                                                                        Application .showMessage("SEND Xml Jas_licenseKey is ::" + Jas_licenseKey);
                                                                        System.out.println("SEND Xml Jas_licenseKey is ::" + Jas_licenseKey);
                                                                              
                                                                                                                                                                                        

                                                                        String Jas_targetValue = lC.nodeFromKey(senddata,
                                                                                                        "<targetValue xmlns=\"\">",
                                                                                                        "</targetValue>");

                                                                        Application .showMessage("SEND Xml Jas_targetValue is ::" + Jas_targetValue);
                                                                        System.out.println("SEND Xml Jas_targetValue is ::" + Jas_targetValue);
                                                                                                     
                                                                                                                                       

                                                                        String Jas_changeType = lC
                                                                                                        .nodeFromKey(senddata,
                                                                                                                                        "<changeType xmlns=\"\">",
                                                                                                                                        "</changeType>");
                                                                        Application.showMessage("SEND Xml Jas_changeType is ::"   + Jas_changeType);
                                                                        System.out.println("SEND Xml Jas_changeType is ::"   + Jas_changeType);
                                                                                                       
                                                                                                                                     
String faultString=  lC.nodeFromKey(recievedata,  "<faultstring>","</faultstring>");  
if(faultString.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
{
	v1=1;
	
}
else
{//Yamini
	
	addIssues.add("Validation is not Successfull in Jasper call. it throws error code");
    
	 }
                                                                       

                                    break;                    }

                                        }

                        }
      }
    }
if(v1==1)
{
                        Application.showMessage("********WebService Call::Jasper Valiadted ********");
                        System.out.println("********WebService Call::Jasper Valiadted ********");
                        c.put("JasVal","no");
                       }
                       else
                       {//Yamini
                       	if(callFound!=1)
                       	 {
                       		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
                               
                       	 }
                       	 for(int i=0;i<addIssues.size();i++)
                       	 {
                       		 Issues=Issues.concat(addIssues.get(i)).concat(",");
                       	 }
                       	// c.report(Issues.concat("WebService Call :: Jasper_Validate is Failed with Validation Errors").concat("**"));
                       	 c.put("JasVal", Issues.concat("WebService Call :: Jasper_Validate is Failed with Validation Errors").concat("**"));
                       }
                                                                                              

                     //   st.close();
rs.close();

        }
    

 catch (SQLException e) {
        Application.showMessage("Connection Failed! Check output console");
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
        return;
}
}

//-------------

public void AnotherJasper_Validate(Object input, ScriptingContext c)
        throws InterruptedException, IOException, ClassNotFoundException {

// Think time in JVM [waits for 10 secs here]
LibraryRtp_UpDown lC = new LibraryRtp_UpDown();
LibraryRtp lr=new  LibraryRtp();
XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
List<String> jasper=new ArrayList<String>();
DBCursor  rs;
DBObject obj = null;
String xmldata1 = "receive_data";
String xmldata2 = "SEND_DATA";
int callFound = 0,targetVal=0,jasRec=0;
String Time= c.get("BaseTime").toString();
List<String> addIssues=new ArrayList();
String Issues="Issues from AnotherJasperValidate:::";
//    c.setValue("Jasper","true");
Application .showMessage("----------------------------------------------------------");
                       
Application.showMessage("*****jasper Function******");
Application.showMessage("----------------------------------------------------------");
        int v1=0;                
//    lr.findThinktimeOperationRTP(input, c);
//    c.setValue("Jasper","false"); 
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("EditTerminal"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("EditTerminal"));


try {
   //     Statement st = lC.dBConnect(input, c);
   //     rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'jaspersrv:TerminalPortType/EditTerminal' order by creation_time desc)where rownum <= 20");
	
		
			rs=XH.XHOM_reduceThinkTimeRTPmsgid(input, c, c.get("JasRowMsg").toString());
	//rs=XH.XHOM_reduceThinkTimeRTPmsgid(input, c, "58610db15306d82a5dc81809");

	while (rs.hasNext())
    {
   obj=rs.next();
      if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
      {
rowmsg=((BasicBSONObject) obj).getString("_id");
c.put("row", rowmsg);

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

                                        String ICCID = lC.nodeFromKey(senddata,
                                                         				"<iccid xmlns=\"\">",
                                                                        "</iccid>");

                                        if (ICCID == null) {
                                                        System.out.printf("ICCID value from JasperCall is ::NULL \n");
                                                        continue;
                                        } else {

                                                        Application.showMessage("ICCID value from JasperCall is::"  + ICCID);
                                                        System.out.printf("ICCID value from JasperCall is::"  + ICCID);

                                                        if (ICCID.contains("8901650")) {
                                                                      
                                                                        Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                        Application .showMessage("Validation is Successfull with Input ICCID"
                                                                                                                                        + ICCID);
                                                                        
                                                                      // System.out.println("ICCID msgid= " + rs.getString(1));
                                                                       System.out.println("Recieve XML is  \n"  + recievedata);
                                                                       System.out.println("SEND XML is  \n"  + senddata);
                                                                       System.out.println("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                       System.out.println("Validation is Successfull with Input ICCID"
                                                                                                                                        + ICCID);
                                                                     
                                                                        callFound = 1;
                                                        } else {
                                                                        continue;
                                                        }

                                                        if (callFound == 1) {
                                                                        String Jasp_version = lC.nodeFromKey(senddata,
                                                                                                                                        "<version xmlns=\"\">",
                                                                                                                                        "</version>");

                                                                        Application.showMessage("SEND Xml Jasp_version is ::"+ Jasp_version);
                                                                        System.out.println("SEND Xml Jasp_version is ::"+ Jasp_version);

                                                                        String Jas_licenseKey = lC.nodeFromKey(senddata,
                                                                                                        "<licenseKey xmlns=\"\">",
                                                                                                        "</licenseKey>");
                                                                        Application .showMessage("SEND Xml Jas_licenseKey is ::" + Jas_licenseKey);
                                                                        System.out.println("SEND Xml Jas_licenseKey is ::" + Jas_licenseKey);
                                                                              
                                                                                                                                                                                        

                                                                        String Jas_targetValue = lC.nodeFromKey(senddata,
                                                                                                        "<targetValue xmlns=\"\">",
                                                                                                        "</targetValue>");
                                                                        Application .showMessage("SEND Xml Jas_targetValue is ::" + Jas_targetValue);
                                                                        System.out.println("SEND Xml Jas_targetValue is ::" + Jas_targetValue);
                                                                        if(Jas_targetValue.equalsIgnoreCase(c.getValue("ACC_input").concat("-").concat(c.getValue("CcentralNum"))))
                                                                        {
                                                                        	targetVal=1;
                                                                        	 Application .showMessage("**********Japer Validation for Completed Transactions Install/Chang*********");
                                                                        	 Application .showMessage("Validation is succesfful with Jasper target value which is coming as AccountNumber-CentralNumber"+Jas_targetValue);
                                                                        	 
                                                                                
                                                                        }
                                                        

                                                                        
                                                                                                     
                                                        }      
                                        }

                                                                        String Jas_changeType = lC.nodeFromKey(senddata,"<changeType xmlns=\"\">","</changeType>");
                                                                        Application.showMessage("SEND Xml Jas_changeType is ::"   + Jas_changeType);
                                                                        System.out.println("SEND Xml Jas_changeType is ::"   + Jas_changeType);
                                                                                                       
                                                                                                                                     
String faultString=  lC.nodeFromKey(recievedata,  "<faultstring>","</faultstring>");  
if(faultString.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
{
	v1=1;
	
}
else
{
	addIssues.add("Jasper Throws error response");
	
}

String correlationId=  lC.nodeFromKey(recievedata,  "<correlationId xmlns=\"\">","</correlationId>"); 
jasper.add(correlationId);
String version=  lC.nodeFromKey(recievedata,  "<version xmlns=\"\">","</version>");
jasper.add(version);
String build=  lC.nodeFromKey(recievedata,  "<build xmlns=\"\">","</build>"); 
jasper.add(build);
String timestamp=  lC.nodeFromKey(recievedata,  "<timestamp xmlns=\"\">","</timestamp>"); 
jasper.add(timestamp);
String iccid=  lC.nodeFromKey(recievedata,  "<iccid xmlns=\"\">","</iccid>"); 
jasper.add(iccid);
String effectiveDate=  lC.nodeFromKey(recievedata,  "<effectiveDate xmlns=\"\">","</effectiveDate>"); 
jasper.add(effectiveDate);

for(int i=0;i<jasper.size();i++)
{
	if(jasper.get(i).trim().equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
	{
		addIssues.add("Jasper receive datat is not successful  in"+jasper.get(i).trim()+"Validation");
	}
	else
	{
		jasRec++;
	}
}
break;
                                                        }                                                               

                                                    }

                                        

    }  
      
    
if(v1*targetVal==1 && jasRec==6)
{
                        Application.showMessage("********WebService Call::Jasper Valiadted ********");
                        System.out.println("********WebService Call::Jasper Valiadted ********");
                        c.put("AnotherJasVal","no");
}

                       else
                       {//Yamini
                       	if(callFound!=1)
                       	 {
                       		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
                               
                       	 }
                       	 for(int i=0;i<addIssues.size();i++)
                       	 {
                       		 Issues=Issues.concat(addIssues.get(i)).concat(",");
                       	 }
                       	// c.report(Issues.concat("WebService Call :: Jasper_Validate is Failed with Validation Errors").concat("**"));
                       	 c.put("AnotherJasVal", Issues.concat("WebService Call :: Jasper_Validate is Failed with Validation Errors").concat("**"));
                       }
                     //   st.close();
rs.close();

        
	
}
    

 catch (SQLException e) {
        Application.showMessage("Connection Failed! Check output console");
        System.out.println("Connection Failed! Check output console");
        e.printStackTrace();
        return;
}
}






//------------

public void NumerixCos_Validate(Object input, ScriptingContext c) throws Exception 

{
              
       Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
       LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
       LibraryRtp lr=new  LibraryRtp();
       XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
       DBCursor  rs;
       DBObject obj = null;
       String xmldata1 ="receive_data";
       String xmldata2 ="SEND_DATA";
       int callFound=0;
       String Time= c.get("BaseTime").toString();
       List<String> addIssues=new ArrayList();
	    String Issues="Issues from Numerex:::";
    //   c.setValue("Numerex","true");
       Application.showMessage("----------------------------------------------------------");
       Application.showMessage("*****Numerix Function******");    
       Application.showMessage("----------------------------------------------------------");
 // Application.showMessage("Value is"+c.getValue("ACC_input"));
     //  lr.findThinktimeOperationRTP(input, c);
    //   c.setValue("Numerex","false"); 
       HashMap Operation=XH.findingoperations(input, c);
 	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("deactivateUnit"));
 	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("deactivateUnit"));
 	     
 	  System.out.println("OPERATIONValidation is "+(String) Operation.get("deactivateUnit"));
	     
       try
       {
             // Statement st =lC. dBConnect(input, c);
            //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:NumerexSimulatorLogMsg/deactivateUnit' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
    	   rs=XH.XHOM_reduceThinkTimeRTP(input, c);
    	   if(rs==null)
    	   {
    		   addIssues.add(c.getValue("OPERATIONVALIDATION")+ " Not found on time");
    		   XH.generateReport(input, c, addIssues, "Calls are not Present-->");
    		   rs.close();
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
                       Application.showMessage("Your SEND XML is::\n"+senddata);
                       Application.showMessage("RECIEVE XML is ::\n"+recievedata);
                         
                          
                          String CCentrl =lC.nodeFromKey(senddata,"<sUnitKey xmlns=\"\">","</sUnitKey>"); 
                             
                              
                          if(CCentrl.equals(null))
                          {
                                System.out.printf("Call not found \n");
                               continue;
                          }
                          else
                          {
                            
                            Application.showMessage("Ccentral number fetched from numerix cal is::"+CCentrl);
                            Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                            //Application.showMessage(c.getValue("ACC_input"));
                            //Application.showMessage(billingArrangementID_getCus);
                            
                            
                            /* String Numerx_sUnitKey= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                    
                             Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey);
                            */
                            
                            if(CCentrl.equals(c.getValue("CcentralNum")))
                            {
                                
                                   Application.showMessage("Numerix call found"+CCentrl);
                                   callFound=1;
                            }
                            else
                            {
                                   continue;
                            }
                            
                            if(callFound==1)
                            {
                                   String Numerx_iAccountId= lC.nodeFromKey(senddata,"<iAccountId xmlns=\"\">","</iAccountId>");
                                           
                                    Application.showMessage("SEND Xml Numerx_iAccountId is ::"+Numerx_iAccountId);
                                    
                         
                            
                         //   recv data
                            
                                    String Numerix_activateUnitResult = lC.nodeFromKey(recievedata,"<deactivateUnitResult xmlns=\"\">","</deactivateUnitResult>");
                                    Application.showMessage("SEND Xml Numerix_activateUnitResult is ::"+Numerix_activateUnitResult); 
                            
                                    String Numerix_iErrorCode = lC.nodeFromKey(recievedata,"<iErrorCode xmlns=\"\">","</iErrorCode>");
                                    Application.showMessage("SEND Xml Numerix_iErrorCode is ::"+Numerix_iErrorCode); 
                                    
                                    String Numerix_sErrorMsg = lC.nodeFromKey(recievedata,"<sErrorMsg xmlns=\"\">","</sErrorMsg>");
                                    Application.showMessage("SEND Xml Numerix_sErrorMsg is ::"+Numerix_sErrorMsg); 
                                    break;
                                    
                            }        
                                             
                           // ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));                       
                           }
                   
                         }
             }
           }
    	   if(callFound==1)
    		   {
    		   c.put("NumeDeactivateUnitIssues", "no");
    		   Application.showMessage("********WebService Call::Numerix Valiadted ********");  
    		   }
    	   else
	         {//Yamini
    		   
    		   if(callFound!=1)
    			 {
    				 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+c.get("BaseTime"));
    		         
    			 }
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.put("NumeDeactivateUnitIssues",Issues.concat("WebService Call :: Numerex_Validate is Failed with Validation Errors").concat("**"));
	         }
                      //    st.close();
                  rs.close();

               
    	   }
           }
       
       
       catch (SQLException e)
       {
           Application.showMessage("Connection Failed! Check output console");
              e.printStackTrace();
              return;
       }
   
}

public String orderUpdateDis_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
     DBCursor  rs;
     DBObject obj = null;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 List<String> addIssues=new ArrayList();
	    String Issues="Issues from setOrderstatusDis:::";
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
    String rscallpresent="false";
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********OrderUpdate_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
     HashMap Operation=lC.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("orderUpdate")); 
	try
	{
		// Statement st =lC. dBConnect(input, c);	
		rs=XH.XHOM_reduceThinkTimeRTP(input, c);
		
		if(rs == null)
    	{
    		rscallpresent = "false";
    		
    	
    		
    	}
    	else
{
    		while (rs.hasNext())
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
            		continue;
            	}
	            
	            if(callFound==1)
	            {
	      
	 	            

	 	            String vomInstance= lC.nodeFromKey(senddata,"<value name=\"vomInstance\">","</value><value name=\"ordSource\">");
	 	            Application.showMessage("vomInstance is ::"+vomInstance); 

	 	           
	 	            
	 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
	 	            {
	 	            String billingOrderId= lC.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
	 	            Application.showMessage("billingOrderId is ::"+billingOrderId); 
	 	            
		 	           if(billingOrderId==null)
			            {
				            addIssues.add("Send Xml billingOrderId is NULL");
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
				            	 addIssues.add("billingOrderId at Send Xml not Validated as "+billingOrderId);
				             }
			            }
		 	           
		 	          String Service= lC.nodeFromKey(senddata,"Service = "," Hav Market = U").trim();
		 	            Application.showMessage("Service is ::"+Service.replaceAll("Hav Market = U Cms Market = C", "").trim()); 
		 	            
		 	           if(Service==null)
			            {
				            addIssues.add("Send Xml Service is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
			            	 if((Service.replaceAll("Hav Market = U Cms Market = C", "").trim()).equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
				            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
				            	 v4=1;
				            	 c.setValue("Product",Service);
				             }
				             else
				             {
				            	// addIssues.add("Service at Send Xml not Validated as "+Service);
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
	 			            addIssues.add("Send Xml Service is NULL");
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
	 			            	// addIssues.add("Service at Send Xml not Validated as "+Service);
	 			             }
	 		            }	
	 	            }
	 	          
	 	            String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
	 	            Application.showMessage("inputChannel is ::"+inputChannel);
	 	            
	 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
	 	            Application.showMessage("ordType is ::"+ordType);
	 	            
	 	           if(ordType==null)
		            {
			            addIssues.add("Send Xml ordType is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
		            	 if(ordType.equals("DIS"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
			            	 v2=1;
			             }
			             else if(ordType.equalsIgnoreCase("ERR"))
			             {
			            	 addIssues.add("lt_fireNumber at Send Xml not Validated as "+ordType);
			             }
			             else
			             {
			            	 continue;
			             }
		            }	
	 	            
	 	            
	 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
	 	            Application.showMessage("customerType is ::"+customerType); 
	 	            
	 	           if(customerType==null)
		            {
			            addIssues.add("Send Xml customerType is NULL");
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
			            	 addIssues.add("customerType at Send Xml not Validated as "+customerType);
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
         }
         else
         {
        	 c.setValue("IsDemi", "false");
        	//Yamini
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.report(Issues.concat("WebService Call :: setOrderstatus_Validate is Failed with Validation Errors").concat("**"));
	         }
       //  st.close();
         rs.close();
	}	
	}
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }

public String processHomeSecurityInfo_Validate_Ins(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
                Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
     DBCursor  rs;
     DBObject obj = null;
                int callFound=0,v1=0,v2=0,v3=0,v4=0,accno=0,firstname=0;
                String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String rscallpresent="true";
     int continuVal=0;List<String> addIssues=new ArrayList();
	    String Issues="Issues from processHomesecurityIns:::";
    
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
     Application.showMessage("----------------------------------------------------");
     
     OUTER1:
		   for(int countval=0;countval<=5;countval++)
		   {
			 
                try
                {
                           //     Statement st =lC. dBConnect(input, c); 
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                	HashMap Operation=XH.findingoperations(input, c);
                    
                    c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
                    Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));
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
                           Application.showMessage("Your Recieve XML is::\n"+senddata);
                           Application.showMessage("RECIEVE XML is ::\n"+recievedata);     
                                           
                            String accountNumber_DDS= lC.nodeFromKey(senddata,"<messagesDDS:accountNumber>","</messagesDDS:accountNumber>");
                           Application.showMessage("accountNumber is ::"+accountNumber_DDS);   
                           String firstName_DDS= lC.nodeFromKey(senddata,"<typesDDS:firstName>","</typesDDS:firstName>");
                           Application.showMessage("firstName is ::"+firstName_DDS);
                            if(accountNumber_DDS.equals(c.getValue("ACC_input")))
                {
                                Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                Application.showMessage("SEND XML is :: \n"+senddata);
                                //System.out.printf("SEND XML is %s \n",senddata);
                               Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
                                Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_DDS);
                                accno=1;
                                                         
                                 
                                      Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+firstName_DDS);
                                      if(firstName_DDS.equalsIgnoreCase(c.getValue("FirstName")))
                                                  {
                                                      Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
                                                      Application.showMessage("Validation is Successfull with FirstName::"+" "+firstName_DDS);
                                                      v1=1;
                                                      firstname=1;
                                                  }
                                                  else
                                                  {
                                                	  
                                                	  continue;
                                                  		}
                                                  	
                                                    //  addIssues.add("FirstName at Send Xml not Validated as "+firstName_DDS);
                                                  
                                 }
                
                else
                {
                                continue;
                }
                
                            if(accno*firstname==1)
                            {
                            	callFound=1;
                            }
                            if(callFound==1)
                            {
                      
                                                            

                                           String lastName_DDS= lC.nodeFromKey(senddata,"<typesDDS:lastName>","</typesDDS:lastName>");
                                           Application.showMessage("lastName is ::"+lastName_DDS); 
                                            
                                           if(lastName_DDS==null)
                                            {
                                                            addIssues.add("Send Xml LastName is NULL");
                                                            continue;
                                            }
                                            else
                                            {
                                                 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+lastName_DDS);
                                                 if(lastName_DDS.equalsIgnoreCase(c.getValue("LastName")))
                                                             {
                                                                 Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
                                                                 Application.showMessage("Validation is Successfull with FirstName::"+" "+lastName_DDS);
                                                                 v2=1;
                                                             }
                                                             else
                                                             {
                                                                 addIssues.add("LastName at Send Xml not Validated as "+lastName_DDS);
                                                             }
                                            }

                                           String homeSecurityLOBStatus_DDS= lC.nodeFromKey(senddata,"<typesDDS:homeSecurityLOBStatus>","</typesDDS:homeSecurityLOBStatus>");
                                           Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
                                            if(homeSecurityLOBStatus_DDS==null)
                                            {
                                                           addIssues.add(" homeSecurityLOBStatus is NULL");
                                                            continue;
                                            }
                                            else
                                            {
                                                 Application.showMessage("homeSecurityLOBStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+homeSecurityLOBStatus_DDS);
                                                 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("PENDING_INSTALL"))
                                                             {
                                                                 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
                                                                 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
                                                                 v3=1;
                                                             }
                                                 else if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("ACTIVE"))
                                                             {
                                                                 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
                                                                 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
                                                                 v3=1;                                                
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
                
		if(callFound==1)	
		{
			countval=5;
         if(v1*callFound*v2*v3*v4 ==1)
         {
                Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
         }
         else
         {
                 c.put("result", "false");
                 addIssues.add("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
         }
		}
		else if( countval==5)	
		{
	         
	         if(v1*callFound*v2*v3*v4 ==1)
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
	    	        	 c.report(Issues.concat("WebService Call :: processHomesecurity_Validate is Failed with Validation Errors").concat("**"));
	    	           }
			}
		else
		{
			 
		}
       //  st.close();
		  rs.close();
                
                }
                }
                catch (SQLException e)
                {
                    System.out.println("Connection Failed! Check output console");
                                e.printStackTrace();
                                
                }
                
		   
		   
}
				return rscallpresent;
}


public void Numerix_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 

{
       
       Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
       LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
       LibraryRtp lr=new  LibraryRtp();
       XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
       DBCursor  rs;
       DBObject obj = null;
       String xmldata1 ="receive_data";
  String xmldata2 ="SEND_DATA";
  List<String> addIssues=new ArrayList();
  String Issues="Issues from Numerex_Validate:::";
  String Time= c.get("BaseTime").toString();
  int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
  Application.showMessage("----------------------------------------------------------");
  Application.showMessage("*****Numerix Function******");    
  Application.showMessage("----------------------------------------------------------");
 // Application.showMessage("Value is"+c.getValue("ACC_input"));
  HashMap Operation=lr.findingoperations(input, c);
   c.setValue("OPERATIONVALIDATION",(String) Operation.get("activateUnit"));
   Application.showMessage("OPERATIONValidation is "+(String) Operation.get("activateUnit"));
   
       try
       {
            /* Statement st =lC. dBConnect(input, c);
         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'Numerex:NMRXCCSoap/activateUnit' order by creation_time desc)where rownum <= 20");
        */  rs=XH.XHOM_reduceThinkTimeRTP(input, c);
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
          Application.showMessage("Your SEND XML is::\n"+senddata);
          Application.showMessage("RECIEVE XML is ::\n"+recievedata);
                                                         
                         String CCentrl =lC.nodeFromKey(senddata,"<sUnitKey xmlns=\"\">","</sUnitKey>"); 
                        if(CCentrl.equals(null))
                        {
                        	Application.showMessage("CCentrl value is NULL");
                               System.out.printf("Call not found \n");
                              continue;
                        }
                        
                         else
                         {
                        	 Application.showMessage("Ccentral number fetched from numerix activateUnit is::"+CCentrl);
                             Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                             
                             if(CCentrl.equals(c.getValue("CcentralNum")))
                             {
                                 
                                    Application.showMessage("Numerix activateUnit call found"+CCentrl);
                                    callFound=1;
                             }
                             else
                             {
                                    continue;
                             }
                             
                          
                                                                                    
                         }    
                        if(callFound==1)
                        {
                               String Numerx_iAccountId= lC.nodeFromKey(senddata,"<iAccountId xmlns=\"\">","</iAccountId>");
                                        
                                Application.showMessage("SEND Xml Numerx_iAccountId is ::"+Numerx_iAccountId);
                                
                  /* String Numerx_sUnitKey1= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                        
                                Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey1);
                        */
                        /*recv data*/
                        
                                String Numerix_activateUnitResult = lC.nodeFromKey(recievedata,"<activateUnitResult xmlns=\"\">","</activateUnitResult>");
                                Application.showMessage("SEND Xml Numerix_activateUnitResult is ::"+Numerix_activateUnitResult); 
                        
                                String Numerix_iErrorCode = lC.nodeFromKey(recievedata,"<iErrorCode xmlns=\"\">","</iErrorCode>");
                                Application.showMessage("SEND Xml Numerix_iErrorCode is ::"+Numerix_iErrorCode); 
                                c.put("Numerix_iErrorCode", Numerix_iErrorCode);
                                
                                String Numerix_sErrorMsg = lC.nodeFromKey(recievedata,"<sErrorMsg xmlns=\"\">","</sErrorMsg>");
                                Application.showMessage("SEND Xml Numerix_sErrorMsg is ::"+Numerix_sErrorMsg); 
                                break;
                                
                        }  
                        
                   }
         }
                   
       }
 	  if(callFound==1)
 	  {
        Application.showMessage("********WebService Call::Numerex Valiadted ********"); 
        c.put("Numerex","no");
 	  }
 	 
 		 else
         {//Yamini
 			 
 			if(callFound!=1)
 			 {
 				 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
 		         
 			 }
        	 for(int i=0;i<addIssues.size();i++)
        	 {
        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
        	 }
        	 c.put("Numerex",Issues.concat("WebService Call :: Numerex_Validate is Failed with Validation Errors").concat("|||"));
         }
 	  
                          //st.close();
        rs.close();

               
         
       }
       
             
       catch (SQLException e)
       {
             //OutPut=0;
           Application.showMessage("Connection Failed! Check output console");
             e.printStackTrace();
             return;
       }
}

//--Yamini--

public void Numerix_ValidateAddAssociation(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 

{
       
       Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
       LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
       LibraryRtp lr=new  LibraryRtp();
       XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
       DBCursor  rs;
       DBObject obj = null;
       String xmldata1 ="receive_data";
  String xmldata2 ="SEND_DATA";
  int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
  List<String> addIssues=new ArrayList();
  String Time= c.get("BaseTime").toString();
  String Issues="Issues from Numerex_addAssociation:::";
  Application.showMessage("----------------------------------------------------------");
  Application.showMessage("*****Numerix Function******");    
  Application.showMessage("----------------------------------------------------------");
 // Application.showMessage("Value is"+c.getValue("ACC_input"));
  HashMap Operation=lr.findingoperations(input, c);
   c.setValue("OPERATIONVALIDATION",(String) Operation.get("addAssociation"));
   Application.showMessage("OPERATIONValidation is "+(String) Operation.get("addAssociation"));
   
       try
       {
            /* Statement st =lC. dBConnect(input, c);
         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'Numerex:NMRXCCSoap/activateUnit' order by creation_time desc)where rownum <= 20");
        */  rs=XH.XHOM_reduceThinkTimeRTP(input, c);
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
          Application.showMessage("Your SEND XML is::\n"+senddata);
          Application.showMessage("RECIEVE XML is ::\n"+recievedata);
                                                         
                         String CCentrl =lC.nodeFromKey(senddata,"<sDICE xmlns=\"\">","</sDICE>"); 
                        if(CCentrl.equals(null))
                        {
                        	Application.showMessage("CCentrl value is NULL");
                               System.out.printf("Call not found \n");
                              continue;
                        }
                        
                         else
                         {
                        	 Application.showMessage("Ccentral number fetched from numerix activateUnit is::"+CCentrl);
                             Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                             
                             if(CCentrl.equals(c.getValue("CcentralNum")))
                             {
                                 
                                    Application.showMessage("Numerix activateUnit call found"+CCentrl);
                                    callFound=1;
                             }
                             else
                             {
                                    continue;
                             }
                             
                          
                                                                                    
                         }    
                        if(callFound==1)
                        {
                               String Numerx_iAccountId= lC.nodeFromKey(senddata,"<iAccountId xmlns=\"\">","</iAccountId>");
                                        
                                Application.showMessage("SEND Xml Numerx_iAccountId is ::"+Numerx_iAccountId);
                                
                                String Numerx_iccid= lC.nodeFromKey(senddata,"<sICCID xmlns=\"\">","</sICCID>");
                                
                                Application.showMessage("SEND Xml Numerx_iccid is ::"+Numerx_iccid);
                                
                                String Numerx_iAccountNo= lC.nodeFromKey(senddata,"<sAcctNbr xmlns=\"\">","</sAcctNbr>");
                                
                                Application.showMessage("SEND Xml Numerx_iAccountNo is ::"+Numerx_iAccountNo);
                                
                  /* String Numerx_sUnitKey1= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                        
                                Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey1);
                        */
                        /*recv data*/
                        
                                String Numerix_addAssociationResult = lC.nodeFromKey(recievedata,"<addAssociationResult xmlns=\"\">","</addAssociationResult>");
                                Application.showMessage("SEND Xml Numerix_addAssociationResult is ::"+Numerix_addAssociationResult); 
                        
                                String Numerix_iErrorCode = lC.nodeFromKey(recievedata,"<iErrorCode xmlns=\"\">","</iErrorCode>");
                                Application.showMessage("SEND Xml Numerix_iErrorCode is ::"+Numerix_iErrorCode); 
                                c.put("Numerix_iErrorCodeAdd", Numerix_iErrorCode);
                                if(Numerix_iErrorCode.equalsIgnoreCase("0"))
                                {
                                	v1=1;
                                	Application.showMessage("***********Numerex Add Association error code Validation*******");
                                	Application.showMessage("Validation is successsfull in Numerex Add Association error code Validation"+Numerix_iErrorCode);
                                }
                                else
                                {
                                	addIssues.add("Validation is not successsfull in Numerex Add Association error code"+" "+Numerix_iErrorCode);
                                    
                                }
                                
                                String Numerix_sErrorMsg = lC.nodeFromKey(recievedata,"<sErrorMsg xmlns=\"\">","</sErrorMsg>");
                                Application.showMessage("SEND Xml Numerix_sErrorMsg is ::"+Numerix_sErrorMsg); 
                                break;
                                
                        }  
                        
                   }
         }
                   
       }
 	  if(callFound*v1==1)
 	  {
        Application.showMessage("********WebService Call::Numerex Valiadted ********"); 
        c.put("NumerexaddAsso","no");
 	  }
 	 
 		 else
         {//Yamini
 			 
 			if(callFound!=1)
 			 {
 				 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
 		         
 			 }
        	 for(int i=0;i<addIssues.size();i++)
        	 {
        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
        	 }
        	 c.put("NumerexaddAsso",Issues.concat("WebService Call :: Numerex_Validate is Failed with Validation Errors").concat("|||"));
         }
 	  
                          //st.close();
        rs.close();

               
         
       }
       
             
       catch (SQLException e)
       {
             //OutPut=0;
           Application.showMessage("Connection Failed! Check output console");
             e.printStackTrace();
             return;
       }
}





}