
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.bson.BasicBSONObject;
import org.json.JSONException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


/**
* @author 368716 
 *
*/
/**
* @author 368716
*
*/

public class XHOM_DemiInstall_Validations 
{
                //private static final ScriptingContext ScriptingContext = null;
                //private static final Object Object = null;
                public String XML_AccNumber;
                public String XML_ScenarioName;
                public static String SERVICEorCURRENTSERVICE;
                public String NEWSERVICE;
                public String HOUSE_KEY;
                
                LibraryRtp Lp=new LibraryRtp();
                LibraryRtp_UpDown lUP=new LibraryRtp_UpDown();
                XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
                
                public Connection connection = null;
                public static String rowmsg= null;
                
    public String[][] aa = new String[10][20];
    //CVR_ValidationCalls cvr=new CVR_ValidationCalls();
    //-----------------
    
    
    public void InstallValidations(Object input, ScriptingContext c) throws Exception
	{
		//simulatorChange(input, c);
    	XHOM_RTP_ValidationMethods VM=new XHOM_RTP_ValidationMethods();
		if(c.getValue("IsDemi").equalsIgnoreCase("true")  || c.getValue("IsDemi").equalsIgnoreCase("Demi"))
		{
			VM.demicalls(input, c);
		}
		else
		{
			VM.Insightcalls(input, c);
		}
		//simulatorChange(input, c);
	}
    //----------
    
    
   
    
    //------------
   public void getHomeSecurityInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException
    {

        Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
LibraryRtp  lC= new LibraryRtp ();
DBCursor  rs; 
DBObject obj = null;
List<String> addIssues=new ArrayList();
String Issues="Issues from getHomeSecurityInfo:::";
        int callFound=0,v1=0,v2=0,v3=0,v4=0;
        String xmldata1 ="receive_data";
String xmldata2 ="SEND_DATA";

Application.showMessage("-----------------------------------------------------");
Application.showMessage("*****getHomeSecurityInfo_Validate Function******");       
Application.showMessage("----------------------------------------------------");
HashMap Operation=lC.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("getHomeSecurityInfo"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getHomeSecurityInfo"));
        try
        {
                       // Statement st =lC. dBConnect(input, c); 
                        rs=XH.XHOM_reduceThinkTimeRTP(input, c);
 //rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/getHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
    
                       String senddataxl=   lC.RemoveNameSpaces(recievedata);         
                    String accountNumber_DDS= lC.nodeFromKey(senddata,"<messagesDDS:accountNumber>","</messagesDDS:accountNumber>");
                   Application.showMessage("accountNumber is ::"+accountNumber_DDS);                          
                    if(accountNumber_DDS.equals(c.getValue("ACC_input")))
        {
                        Application.showMessage("Recieve XML is::  \n"+ recievedata);
                        Application.showMessage("SEND XML is :: \n"+senddata);
                        Application.showMessage("NewSEND XML is :: \n"+senddataxl);
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
              
                                    

                                  String homeSecurityLOBStatus_DDS= lC.nodeFromKey(senddataxl,"<homeSecurityLOBStatus>","</homeSecurityLOBStatus>");
                    //	String homeSecurityLOBStatus_DDS= lC.GetValueByXPath(senddata, "/mes:GetHomeSecurityInfoResponse/mes:homeSecurityInfo/typ:homeSecurityLOBStatus");
                    			                     
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
                                   
                                    String permitRequired_DDS= lC.nodeFromKey(senddataxl,"<permitRequired>","</permitRequired>");
                                       Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
                                   if(permitRequired_DDS==null)
                                   {
                                	   addIssues.add(" permitRequired_DDS is NULL");
                                       
                                   }
                                   else if(permitRequired_DDS.equalsIgnoreCase("YES") || permitRequired_DDS.equalsIgnoreCase("NO") || permitRequired_DDS.isEmpty() )
                                   {
                                	   Application.showMessage("*******Validation Point 5 :: WebServicall-responseStatus********");
                                       Application.showMessage("Validation is Successfull with permitRequired_DDS::"+" "+permitRequired_DDS);
                                       v2=1;
                                       
                                   }
                                                                    
                                    
                                   
                                    String responseStatus_DDS= lC.nodeFromKey(senddataxl,"<responseStatus>","</responseStatus>");
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
 
 if(callFound*v2*v3*v4 ==1)
 {
        Application.showMessage("WebService Call :: GETHomeSecurityInfo is Success[All validation points are Success]");
 }
 else
 {
         c.put("result", "false");
         addIssues.add("WebService Call ::GETHomeSecurityInfo_Validate is Failed with Validation Errors");
 }
                        }
        }              
        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
                        e.printStackTrace();
                        return;
        }

    }
    
    
    //-------------------------orderUpdate AddressUpdate------------------------
    
    public void orderUpdateAddress_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException
    
    
    
    {
    	

		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs; 
	     DBObject obj = null;
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from orderUpdateAddress:::";
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
					            	 v1=1;
					            	// addIssues.add("billingOrderId at Send Xml not Validated as "+billingOrderId);
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
				            addIssues.add("Send Xml ordType is NULL");
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
	        	 addIssues.add("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
	        	 c.setValue("IsRemoveGroup", "false");
	        	 c.setValue("IsUpdateTier", "false");
	        	 c.setValue("IsAddGroup", "false");
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
    
    
    //-------------------quiery location validation method-------------------
    
    
    
    public String queryLocation_Validate(Object input, ScriptingContext c) throws Exception 
    {
                    Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
         DBCursor  rs;
         DBObject obj = null;
                    int callFound=0,v1=0;
                    String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String Time= c.get("BaseTime").toString();
         String  rscallpresent="true";
         List<String> addIssues=new ArrayList();
		    String Issues="Issues from queryLocation:::";
       //  c.setValue("queryLocation","true");
         Application.showMessage("-------------------------------------------------");
         Application.showMessage("*****Query Location _Validate Function******");       
         Application.showMessage("-------------------------------------------------");
       //  lC.findThinktimeOperationRTP(input, c);
      //   c.setValue("queryLocation","false");
         HashMap Operation=lC.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("queryLocation"));
         Application.showMessage("OPERATIONValidation is "+(String) Operation.get("queryLocation"));
                    try
                    {
                                 //   Statement st =lC. dBConnect(input, c); 
          //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation ='ls:LocationServicePort/queryLocation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                    	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
                    	
                    	if(rs == null)
                    	{
                    		c.put("queryLocationIssues",addIssues.add(c.getValue("OPERATIONVALIDATION")+ " Not found on time"));
                 		  
                 		  
                 		 rscallpresent = "false";
                 		 rs.close();
                    		
                    	
                    		
                    	}
                    	else
                    	{
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
                         c.put("addTimeZone", "US_EASTERN"); 
                         c.put("copsTimeZone", "E"); 
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
                     Application.showMessage("Your Send XML is::\n"+senddata);
                     Application.showMessage("RECIEVE XML is ::\n"+recievedata);   
                                     String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
                                     Application.showMessage("receiveDataTrim:::"+receiveDataTrim);
                                String legacyID= lC.nodeFromKey(senddata,"<legacyID>","</legacyID>");                    
                                Application.showMessage("legacyID is ::"+legacyID); 
                                if(legacyID.equals(c.getValue("sHOUSE_KEY")))
                    {
                                 //   Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                //    Application.showMessage("SEND XML is :: \n"+senddata);
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
                          
                                                String locationID= lC.nodeFromKey(recievedata,"<locationID>","</locationID>");
                                               Application.showMessage("locationID is ::"+locationID);
                                               c.setValue("LocationID", locationID);
                                               
                                                
                                                String locationStatus= lC.nodeFromKey(recievedata,"<locationStatus>","</locationStatus>");
                                               Application.showMessage("locationStatus is :: "+locationStatus); 
                                                
                                               if(locationStatus==null)
                                                 {
                                                                addIssues.add("Send Xml Account Number is NULL");
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
                                                                     addIssues.add("locationStatus at Send Xml not Validated as "+locationStatus);
                                                                 }
                                                }                  

                                               String houseNumber= lC.nodeFromKey(recievedata,"<houseNumber>","</houseNumber>");
                                               Application.showMessage("houseNumber is :: "+houseNumber);
                                               if(!houseNumber.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                               {
                                                c.setValue("HouseNumber", houseNumber);
                                               }
                                               else
                                               {
                                            	   c.setValue("HouseNumber", "");  
                                               }
                                               
                                               
                                               String streetPreDirection= lC.nodeFromKey(recievedata,"<streetPreDirection>","</streetPreDirection>");
                                               Application.showMessage("streetPreDirection is :: "+streetPreDirection);
                                               if(!streetPreDirection.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                               {
                                                c.put("streetPreDirection", streetPreDirection);
                                               }
                                               else
                                               {
                                            	   c.put("streetPreDirection", "");  
                                               }
                                               
                                                String QpostalCode= lC.nodeFromKey(recievedata,"<zip5>","</zip5>");
                                                String QpostalCode1= lC.nodeFromKey(recievedata,"<zip4>","</zip4>");
                                               Application.showMessage("QpostalCode is :: "+QpostalCode); 
                                                c.put("QpostalCode", QpostalCode);
                                               
                                                String streetName= lC.nodeFromKey(recievedata,"<streetName>","</streetName>").trim();
                                               Application.showMessage("streetName is :: "+streetName); 
                                               if(!streetName.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                               {
                                            	   c.setValue("StreetName", streetName);
                                               }
                                               else
                                               {
                                            	   c.setValue("StreetName", "");
                                               }
                                                
                                               
                                                String streetSuffix= lC.nodeFromKey(recievedata,"<streetSuffix>","</streetSuffix>").trim();
                                               Application.showMessage("streetSuffix is :: "+streetSuffix); 
                                               if(!streetSuffix.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                               {
                                            	   c.put("streetSuffix", streetSuffix);
                                               }
                                               else
                                               {
                                            	   c.put("streetSuffix", "");
                                               }
                                               
                                               
                                               //--------------
                                               
                                               String UnitType= lC.nodeFromKey(recievedata,"<unitType>","</unitType>").trim();
                                               Application.showMessage("UnitType is :: "+UnitType); 
                                               if(!UnitType.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                               {
                                            	   c.put("UnitType", UnitType);
                                               }
                                               else
                                               {
                                            	   c.put("UnitType", "");
                                               }
                                               
                                               
                                               String UnitNumber= lC.nodeFromKey(recievedata,"<unitValue>","</unitValue>").trim();
                                               Application.showMessage("UnitNumber is :: "+UnitNumber); 
                                               if(!UnitNumber.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                               {
                                            	   c.put("UnitNumber", UnitNumber);
                                               }
                                               else
                                               {
                                            	   c.put("UnitNumber", "");
                                               }
                                               
                                               
                                               String PostalCommunity= lC.nodeFromKey(recievedata,"<city>","</city>").trim();
                                               Application.showMessage("PostalCommunity is :: "+PostalCommunity); 
                                               c.put("PostalCommunity", PostalCommunity);
                                               
                                               String StateProvince= lC.nodeFromKey(recievedata,"<state>","</state>").trim();
                                               Application.showMessage("StateProvince is :: "+StateProvince); 
                                               c.put("StateProvince", StateProvince);
                                            //   
                                               String Dst=lC.nodeFromKey(recievedata,"<DST>","</DST>").trim();
                                               if(StateProvince.equalsIgnoreCase("AZ"))
                                               {
                                            	   if(Dst.equalsIgnoreCase("Y"))
                                            	   {
                                            		   c.put("addTimeZone","US_ARIZONE"); 
                                                       c.put("copsTimeZone", "E"); 
                                            	   }
                                            	   else
                                            	   {
                                            		   c.put("addTimeZone","US_MOUNTAIN"); 
                                                       c.put("copsTimeZone", "E"); 
                                            	   }
                                            	   
                                               }
                                               else
                                               {
                                               Map map=XH.gettingTimeZone(input, c);
                                               
                                               String TimeZone= lC.nodeFromKey(recievedata,"<Name>","</Name>").trim();
                                               Application.showMessage("TimeZone is :: "+TimeZone); 
                                               c.put("addTimeZone", map.get(TimeZone)); 
                                               c.put("copsTimeZone", map.get(TimeZone).toString().charAt(3)); 
                                               }
                                               
                                               
                                               
                                               
                                               
                                               //----------------
                                                 
                                                String city= lC.nodeFromKey(recievedata,"<city>","</city>").trim();
                                               Application.showMessage("city is :: "+city); 
                                               
                                              // c.put("Ecity",city);
                                              if(c.get("AddressDetails").equals("null"))
                                              {
                                            	
                                            	  c.put("Ecity",city); 
                                            	  c.setValue("City", city);// c.put("streetPreDirection",
                                                  c.put("city",city);
                                                  c.put("State",StateProvince);
                                                  c.put("Address",c.getValue("HouseNumber").concat(" ").concat((String) c.get("streetPreDirection")).concat(" ").concat( c.getValue("StreetName")).concat(" ").concat((String) c.get("streetSuffix")).concat(" ").concat((String) c.get("UnitType")).concat(" ").concat((String) c.get("UnitNumber")).trim());
                                                  c.put("addAddress",c.getValue("HouseNumber").concat(" ").concat((String) c.get("streetPreDirection")).concat(" ").concat( c.getValue("StreetName")).concat(" ").concat((String) c.get("streetSuffix")).concat(" ").concat((String) c.get("UnitType")).concat(" ").concat((String) c.get("UnitNumber")).trim());
                                                  
                                                  c.put("Ezipcode",QpostalCode.concat("-").concat(QpostalCode1));
                                              }
                                               
                                              String policeNumber= lC.nodeFromKey(recievedata,"<policeNumber>","</policeNumber>");
                                               Application.showMessage("policeNumber is :: "+policeNumber); 
                                                c.setValue("PoliceNumber", policeNumber);
                                              
                                                String Loc_fireNumber= lC.nodeFromKey(recievedata,"<fireNumber>","</fireNumber>");
                                               Application.showMessage("fireNumber is :: "+Loc_fireNumber); 
                                                c.setValue("FireNumber", Loc_fireNumber);
                                              
                                                String Loc_medicalNumber= lC.nodeFromKey(recievedata,"<medicalNumber>","</medicalNumber>");
                                               Application.showMessage("medicalNumber is :: "+Loc_medicalNumber); 
                                                c.setValue("medicalNumber", Loc_medicalNumber);
                                break;
                                }
                 }
             }
                    	}
             if(v1*callFound==1)
             {
            	 c.put("queryLocationIssues", "no");
                    Application.showMessage("WebService Call :: Query Location is Success[All validation points are Success]");
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
	        	 c.put("queryLocationIssues", Issues.concat("WebService Call :: Query Location_Validate is Failed with Validation Errors").concat("||"));
	        
                     c.put("result", "false");
                    
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

   //----------------------DDS Validation method---------------------------
    
    public String processHomeSecurityInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
    {
                    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
         DBCursor  rs; 
         DBObject obj = null;

                    int callFound=0,v1=0,v2=0,v3=0,v4=0,accno=0,firstname=0;
                    String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String rscallpresent="true";
         int continuVal=0;
         List<String> addIssues=new ArrayList();
		    String Issues="Issues from processHomesecurityInfo:::";
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
                         Application.showMessage("Your Send XML is::\n"+senddata);
                         Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
     
                                               
                                String accountNumber_DDS= lC.nodeFromKey(senddata,"<accountNumber xmlns=\"\">","</accountNumber>");
                               Application.showMessage("accountNumber is ::"+accountNumber_DDS);   
                               String firstName_DDS= lC.nodeFromKey(senddata,"<ns2:firstName>","</ns2:firstName>");
                               Application.showMessage("firstName is ::"+firstName_DDS);
                                if(accountNumber_DDS.equals(c.getValue("ACC_input")))
                    {
                                   // Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                   // Application.showMessage("SEND XML is :: \n"+senddata);
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
                          
                                                                

                                               String lastName_DDS= lC.nodeFromKey(senddata,"<ns2:lastName>","</ns2:lastName>");
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
                                               
                                                String permitRequired_DDS= lC.nodeFromKey(senddata,"<ns2:permitRequired>","/<ns2:permitRequired>");
                                               Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
                                               
                                                                                
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
            	 c.put("DDSDisIssues", "no");
	                Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
	       }
             else
             {
            	 c.put("result", "false");
	        	 if(callFound!=1)
	        	 {
	        		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+c.get("BaseTime"));
	                 
	        	 }
		        	 for(int i=0;i<addIssues.size();i++)
		        	 {
		        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
		        	 }
		        	 c.put("DDSDisIssues",Issues.concat("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors").concat("**"));
		         
	                      }
    		}
    		else if( countval==5)	
    		{
    	         
    	         if(v1*callFound*v2*v3*v4 ==1)
    	         {
    	        	 c.put("DDSDisIssues", "no");
    	                Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
    	         }
    	         else
    	         {
    	        	 c.put("result", "false");
    	        	 if(callFound!=1)
    	        	 {
    	        		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+c.get("BaseTime"));
    	                 
    	        	 }
    		        	 for(int i=0;i<addIssues.size();i++)
    		        	 {
    		        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
    		        	 }
    		        	 c.put("DDSDisIssues",Issues.concat("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors").concat("**"));
    		         
    	                
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
    
    //---------------------------modifyhomesecurity validation method------------------
    
    
    public String modifyHomeSecurity_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
    {
                    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
         DBCursor  rs; 
         DBObject obj = null;

                    int callFound=0,v1=0,v2=0,v3=0,v4=0;
                    String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         List<String> addIssues=new ArrayList();
		    String Issues="Issues from modifyHomesecurity:::";
    String rscallpresent="true";
         Application.showMessage("-------------------------------------------------------------");
         Application.showMessage("***********modifyHomeSecurity_Validate Function**************");       
         Application.showMessage("-------------------------------------------------------------");
         HashMap Operation=lC.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("modifyHomeSecurity"));
         Application.showMessage("OPERATIONValidation is "+(String) Operation.get("modifyHomeSecurity"));
                    try
                    {
                                //    Statement st =lC. dBConnect(input, c); 
           //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/modifyHomeSecurity' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
    
                                               
                                String legacyID_modifyHS= lC.nodeFromKey(senddata,"<legacyID>","</legacyID>");
                               Application.showMessage("legacyID is ::"+legacyID_modifyHS); 
                                if(legacyID_modifyHS.equals(c.getValue("sHOUSE_KEY")))
                    {
                                   // Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                 //   Application.showMessage("SEND XML is :: \n"+senddata);
                                    //System.out.printf("SEND XML is %s \n",senddata);
                                    Application.showMessage("*******Validation Point 1 :: WebServicall-modifyHomeSecurity_Validate********");
                                    Application.showMessage("Validation is Successfull with House Number :"+legacyID_modifyHS);
                                    callFound=1;
                    }
                    else
                    {
                                    continue;
                    }
                    
                                if(callFound==1)
                                {
                          
                                                

                                               String lt_policeNumber= lC.nodeFromKey(senddata,"<policeNumber>","</policeNumber>");
                                               Application.showMessage("lt:policeNumber is ::"+lt_policeNumber); 
                                                
                                               if(lt_policeNumber==null)
                                                {
                                                                addIssues.add("Send Xml lt_policeNumber is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("lt_policeNumber from Send Xml   is ::"+" "+lt_policeNumber);
                                                     if(lt_policeNumber.equals(c.getValue("PoliceNumber")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 2 :: WebServicall-lt_policeNumber********");
                                                                     Application.showMessage("Validation is Successfull with lt_policeNumber::"+" "+lt_policeNumber);
                                                                     v1=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("lt_policeNumber at Send Xml not Validated as "+lt_policeNumber);
                                                                 }
                                                }
                                               
                                                

                                               String lt_fireNumber= lC.nodeFromKey(senddata,"<fireNumber>","</fireNumber>");
                                               Application.showMessage("lt:fireNumber is ::"+lt_fireNumber); 
                                                
                                               if(lt_fireNumber==null)
                                                {
                                                                addIssues.add("Send Xml lt_fireNumber is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("lt_fireNumber from Send Xml   is ::"+" "+lt_fireNumber);
                                                     if(lt_fireNumber.equals(c.getValue("FireNumber")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 3 :: WebServicall-lt_fireNumber********");
                                                                     Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+lt_fireNumber);
                                                                     v2=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("lt_fireNumber at Send Xml not Validated as "+lt_fireNumber);
                                                                 }
                                                }  
                                               
                                                String lt_medicalNumber= lC.nodeFromKey(senddata,"<medicalNumber>","</medicalNumber>");
                                               Application.showMessage("lt:medicalNumber is ::"+lt_medicalNumber); 
                                                
                                               if(lt_medicalNumber==null)
                                                {
                                                                addIssues.add("Send Xml sch_MedicalContactInfo is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("sch_MedicalContactInfo from Send Xml   is ::"+" "+lt_medicalNumber);
                                                     if(lt_medicalNumber.equals(c.getValue("medicalNumber")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 4 :: WebServicall-lt_medicalNumber********");
                                                                     Application.showMessage("Validation is Successfull with lt_medicalNumber::"+" "+lt_medicalNumber);
                                                                     v3=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("sch_MedicalContactInfo at Send Xml not Validated as "+lt_medicalNumber);
                                                                 }
                                                }  
                                                                                                                               
                                                String typ_ReturnCode= lC.nodeFromKey(recievedata,"<ReturnCode>","</ReturnCode>");
                                               Application.showMessage("typ:ReturnCode is::"+typ_ReturnCode);
                                               
                                               if(typ_ReturnCode==null)
                                                {
                                                                addIssues.add("Send Xml typ_ReturnCode is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("typ_ReturnCode from Send Xml   is ::"+" "+typ_ReturnCode);
                                                     if(typ_ReturnCode.equals("0"))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 5 :: WebServicall-typ_ReturnCode********");
                                                                     Application.showMessage("Validation is Successfull with typ_ReturnCode::"+" "+typ_ReturnCode);
                                                                     v4=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("typ_ReturnCode at Send Xml not Validated as "+typ_ReturnCode);
                                                                 }
                                                }  
                                               
                                                
                                                               
                                break;
                                }
                 }
             }
                    	}
             if(v1*callFound*v2*v3*v4 ==1)
             {
                    Application.showMessage("WebService Call :: modifyHomeSecurity_Validate is Success[All validation points are Success]");
             }
             else
             {
                     c.put("result", "false");
                     addIssues.add("WebService Call ::modifyHomeSecurity_Validate is Failed with Validation Errors");
             }
         //    st.close();
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
//-------------------------------responder info validation method----------------------------
    
    public String responderInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
    {
                    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
         DBCursor  rs; 
         DBObject obj = null;
         List<String> addIssues=new ArrayList();
		    String Issues="Issues from responderInfo:::";
                    int callFound=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0,v9=0,v10=0,v11=0;
                    String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";

    String rscallpresent="true";
         Application.showMessage("-----------------------------------------------------");
         Application.showMessage("***********responderInfo_Validate Function***********");       
         Application.showMessage("-----------------------------------------------------");
         HashMap Operation=lC.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("responderInfo"));
         Application.showMessage("OPERATIONValidation is "+(String) Operation.get("responderInfo"));
                    try
                    {
                            //        Statement st =lC. dBConnect(input, c); 
           //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'intrado:frlResource/responderInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
                     Application.showMessage("Your Send XML is::\n"+senddata);
                     Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
 
                                               
                                String intrado_HouseNum= lC.nodeFromKey(senddata,"<HouseNum>","</HouseNum>");
                               Application.showMessage("intrado:HouseNum is ::"+intrado_HouseNum); 
                                if(intrado_HouseNum.equals(c.getValue("HouseNumber")))
                    {
                                   // Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                  //  Application.showMessage("SEND XML is :: \n"+senddata);
                                    //System.out.printf("SEND XML is %s \n",senddata);
                                    Application.showMessage("*******Validation Point 1 :: WebServicall-responderInfo_Validate********");
                                    Application.showMessage("Validation is Successfull with House Number :"+intrado_HouseNum);
                                    callFound=1;
                    }
                    else
                    {
                                    continue;
                    }
                    
                                if(callFound==1)
                                {
                          
                                                

                                               String intrado_PrefixDirectional= lC.nodeFromKey(senddata,"<PrefixDirectional>","</PrefixDirectional>").trim();
                                               Application.showMessage("intrado:PrefixDirectional is ::"+intrado_PrefixDirectional); 

                                               String intrado_StreetName= lC.nodeFromKey(senddata,"<StreetName>","</StreetName>").trim();
                                               Application.showMessage("intrado:StreetName is ::"+intrado_StreetName); 
                                               if(intrado_StreetName==null)
                                                {
                                                                addIssues.add("Send Xml intrado_StreetName is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("intrado_StreetName from Send Xml is ::"+" "+intrado_StreetName);
                                                     if(intrado_StreetName.equalsIgnoreCase(c.getValue("StreetName")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 2 :: WebServicall-intrado_StreetName********");
                                                                     Application.showMessage("Validation is Successfull with intrado_StreetName::"+" "+intrado_StreetName);
                                                                     v2=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("intrado_StreetName at Send Xml not Validated as "+intrado_StreetName);
                                                                 }
                                                }  
                                               

                                               String intrado_StreetSuffix= lC.nodeFromKey(senddata,"<StreetSuffix>","</StreetSuffix>").trim();
                                               Application.showMessage("intrado:StreetSuffix is ::"+intrado_StreetSuffix);
                                               if(intrado_StreetSuffix==null)
                                               {
                                                               addIssues.add("Send Xml intrado_StreetSuffix is NULL");
                                                               continue;
                                               }
                                               else
                                               {
                                                    Application.showMessage("intrado_StreetSuffix from Send Xml   is ::"+" "+intrado_StreetSuffix);
                                                    if(intrado_StreetSuffix.equals(c.get("streetSuffix").toString().trim()))
                                                                {
                                                                    Application.showMessage("*******Validation Point 3 :: WebServicall-intrado_StreetSuffix********");
                                                                    Application.showMessage("Validation is Successfull with intrado_StreetSuffix::"+" "+intrado_StreetSuffix);
                                                                    v3=1;
                                                                }
                                                                else
                                                                {
                                                                    addIssues.add("intrado_StreetSuffix at Send Xml not Validated as "+intrado_StreetSuffix);
                                                                }
                                               }  
                                               
                                               
                                                
                                                String intrado_UnitType= lC.nodeFromKey(senddata,"<UnitType>","</UnitType>").trim();
                                               Application.showMessage("intrado:UnitType is ::"+intrado_UnitType); 
                                               if(intrado_UnitType==null)
                                               {
                                                               addIssues.add("Send Xml intrado_UnitType is NULL");
                                                               continue;
                                               }
                                               else
                                               {
                                                    Application.showMessage("intrado_UnitType from Send Xml   is ::"+" "+intrado_UnitType);
                                                    if(intrado_UnitType.equals(c.get("UnitType").toString().trim()))
                                                                {
                                                                    Application.showMessage("*******Validation Point 4 :: WebServicall-intrado_UnitType********");
                                                                    Application.showMessage("Validation is Successfull with intrado_UnitType::"+" "+intrado_UnitType);
                                                                    v4=1;
                                                                }
                                                                else
                                                                {
                                                                    addIssues.add("intrado_UnitType at Send Xml not Validated as "+intrado_UnitType);
                                                                }
                                               }  
                                               
                                               
                                                
                                                String intrado_PostalCommunity= lC.nodeFromKey(senddata,"<PostalCommunity>","</PostalCommunity>").trim();
                                               Application.showMessage("intrado:PostalCommunity is ::"+intrado_PostalCommunity);
                                               if(intrado_PostalCommunity==null)
                                               {
                                                               addIssues.add("Send Xml intrado_PostalCommunity is NULL");
                                                               continue;
                                               }
                                               else
                                               {
                                                    Application.showMessage("intrado_PostalCommunity from Send Xml   is ::"+" "+intrado_PostalCommunity);
                                                    if(intrado_PostalCommunity.equals(c.get("PostalCommunity").toString().trim()))
                                                                {
                                                                    Application.showMessage("*******Validation Point 5 :: WebServicall-intrado_PostalCommunity********");
                                                                    Application.showMessage("Validation is Successfull with intrado_PostalCommunity::"+" "+intrado_PostalCommunity);
                                                                    v5=1;
                                                                }
                                                                else
                                                                {
                                                                    addIssues.add("intrado_PostalCommunity at Send Xml not Validated as "+intrado_PostalCommunity);
                                                                }
                                               }  
                                               
                                               
                                                
                                                String intrado_StateProvince= lC.nodeFromKey(senddata,"<StateProvince>","</StateProvince>").trim();
                                               Application.showMessage("intrado:StateProvince is ::"+intrado_StateProvince); 
                                               if(intrado_StateProvince==null)
                                               {
                                                               addIssues.add("Send Xml intrado_StateProvinceis NULL");
                                                               continue;
                                               }
                                               else
                                               {
                                                    Application.showMessage("intrado_StateProvince from Send Xml   is ::"+" "+intrado_StateProvince);
                                                    if(intrado_StateProvince.equals(c.get("StateProvince").toString().trim()))
                                                                {
                                                                    Application.showMessage("*******Validation Point 6 :: WebServicall-intrado_StateProvince********");
                                                                    Application.showMessage("Validation is Successfull with intrado_StateProvince::"+" "+intrado_StateProvince);
                                                                    v6=1;
                                                                }
                                                                else
                                                                {
                                                                    addIssues.add("intrado_StateProvince at Send Xml not Validated as "+intrado_StateProvince);
                                                                }
                                               }  
                                               
                                               
                                                
                                                String intrado_PostalZipCode= lC.nodeFromKey(senddata,"<PostalZipCode>","</PostalZipCode>").trim();
                                               Application.showMessage("intrado:PostalZipCode is ::"+intrado_PostalZipCode); 
                                                if(intrado_PostalZipCode==null)
                                                {
                                                                addIssues.add("Send Xml intrado_PostalZipCode is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("PostalCode from Send Xml   is ::"+" "+intrado_PostalZipCode);
                                                     if(intrado_PostalZipCode.equals(c.get("QpostalCode").toString().trim()))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 7 :: WebServicall-intrado_PostalZipCode********");
                                                                     Application.showMessage("Validation is Successfull with intrado_PostalZipCode::"+" "+intrado_PostalZipCode);
                                                                     v7=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("intrado_PostalZipCode at Send Xml not Validated as "+intrado_PostalZipCode);
                                                                 }
                                                }  
                                               
                                                
                                                
                                                                                                
                                                String sch_overallMatch= lC.nodeFromKey(recievedata,"<overallMatch>","</overallMatch>");
                                               Application.showMessage("sch:overallMatch is::"+sch_overallMatch);
                                               
                                                if(sch_overallMatch==null)
                                                 {
                                                                addIssues.add("Send Xml sch_overallMatch is NULL");
                                                                continue;
                                                 }
                                                else
                                                {
                                                     if(sch_overallMatch.equalsIgnoreCase("Success"))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 8 :: WebServicall-sch_overallMatch********");
                                                                     Application.showMessage("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
                                                                     v8=1;
                                                                 }
                                                     else if(sch_overallMatch.equalsIgnoreCase("Altered"))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 8 :: WebServicall-sch_overallMatch********");
                                                                     Application.showMessage("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
                                                                     v8=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("sch_overallMatch at Send Xml not Validated as "+sch_overallMatch);
                                                                 }
                                                }
                                               
                                                
                                                String sch_PoliceContactInfo= lC.nodeFromKey(recievedata,"<PoliceContactInfo>","</PoliceContactInfo>");
                                               Application.showMessage("sch:PoliceContactInfo is::"+sch_PoliceContactInfo);
                                               
                                               if(sch_PoliceContactInfo==null)
                                                {
                                                                addIssues.add("Send Xml sch_PoliceContactInfo is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("sch_PoliceContactInfo from Send Xml   is ::"+" "+sch_PoliceContactInfo);
                                                     if(sch_PoliceContactInfo.equals(c.getValue("PoliceNumber")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 9 :: WebServicall-sch_PoliceContactInfo********");
                                                                     Application.showMessage("Validation is Successfull with sch_PoliceContactInfo::"+" "+sch_PoliceContactInfo);
                                                                     v9=1;
                                                                 }
                                                                 else
                                                                 {
                                                                	 c.setValue("PoliceNumber",sch_PoliceContactInfo);
                                                                	 v9=1;
                                                                   //  addIssues.add("sch_PoliceContactInfo at Send Xml not Validated as "+sch_PoliceContactInfo);
                                                                 }
                                                }  
                                               
                                                
                                                
                                                String sch_FireContactInfo= lC.nodeFromKey(recievedata,"<FireContactInfo>","</FireContactInfo>");
                                               Application.showMessage("sch:FireContactInfo is::"+sch_FireContactInfo);
                                               
                                               if(sch_FireContactInfo==null)
                                                {
                                                                addIssues.add("Send Xml sch_FireContactInfo is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("sch_FireContactInfo from Send Xml   is ::"+" "+sch_FireContactInfo);
                                                     if(sch_FireContactInfo.equals(c.getValue("FireNumber")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 10 :: WebServicall-sch_FireContactInfo********");
                                                                     Application.showMessage("Validation is Successfull with sch_FireContactInfo::"+" "+sch_FireContactInfo);
                                                                     v10=1;
                                                                 }
                                                                 else
                                                                 {
                                                                	 c.setValue("FireNumber",sch_FireContactInfo);
                                                                	 v10=1;
                                                                  //   addIssues.add("sch_FireContactInfo at Send Xml not Validated as "+sch_FireContactInfo);
                                                                }
                                                }  
                                               
                                                
                                                
                                                String sch_MedicalContactInfo= lC.nodeFromKey(recievedata,"<MedicalContactInfo>","</MedicalContactInfo>");
                                               Application.showMessage("sch:MedicalContactInfo is::"+sch_MedicalContactInfo);
                                               
                                               if(sch_MedicalContactInfo==null)
                                                {
                                                                addIssues.add("Send Xml sch_MedicalContactInfo is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("sch_MedicalContactInfo from Send Xml   is ::"+" "+sch_MedicalContactInfo);
                                                     if(sch_MedicalContactInfo.equals(c.getValue("medicalNumber")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 11 :: WebServicall-intrado_medicalNumber********");
                                                                     Application.showMessage("Validation is Successfull with intrado_medicalNumber::"+" "+sch_MedicalContactInfo);
                                                                     v11=1;
                                                                 }
                                                                 else
                                                                 {
                                                                	 c.setValue("medicalNumber",sch_MedicalContactInfo);
                                                                    // addIssues.add("sch_MedicalContactInfo at Send Xml not Validated as "+sch_MedicalContactInfo);
                                                                	 v11=1;
                                                                 }
                                                }  
                                               
                                                
                                                
                                break;
                                }
                 }
             }
                    	}
             if(callFound*v2*v3*v4*v5*v6*v7*v8*v9*v10*v11 ==1)
             {
                    Application.showMessage("WebService Call :: Intrado is Success[All validation points are Success]");
             }
             else
             {
                     c.put("result", "false");
                     addIssues.add("WebService Call ::Intrado is Failed with Validation Errors");
             }
         //    st.close();
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

//----------------------------setAccountAuthorityInfo validation method------------------
    
    public String  SetAccountAuthorityInformation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, JSONException 
    {
                    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
         DBCursor  rs; 
         DBObject obj = null;
         int copsAuth=0;
         List<String> addIssues=new ArrayList();
		    String Issues="Issues from setAccountAuthorityInformation:::";
                    int callFound=0,v1=0,v2=0,v3=0,v4=0;
                    String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String rscallpresent="true";
         Application.showMessage("-------------------------------------------------------------");
         Application.showMessage("***********SetAccountAuthorityInformation Function***********");       
         Application.showMessage("-------------------------------------------------------------");
         HashMap Operation=lC.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("SetAccountAuthorityInformation"));
         Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SetAccountAuthorityInformation"));
                    try
                    {
                           //         Statement st =lC. dBConnect(input, c); 
          //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/SetAccountAuthorityInformation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
     
                                               
                                String accountNumber_setAccountAuthority= lC.nodeFromKey(senddata,"<accountNumber>","</accountNumber>");
                               Application.showMessage("accountNumber is ::"+accountNumber_setAccountAuthority); 
                                if(accountNumber_setAccountAuthority.equals(c.getValue("CcentralNum")))
                    {
                                   // Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                   // Application.showMessage("SEND XML is :: \n"+senddata);
                                    //System.out.printf("SEND XML is %s \n",senddata);
                                    Application.showMessage("*******Validation Point 1 :: WebServicall-SetAccountAuthorityInformation********");
                                    Application.showMessage("Validation is Successfull with House Number :"+accountNumber_setAccountAuthority);
                                    callFound=1;
                    }
                    else
                    {
                                    continue;
                    }
                    
                                if(callFound==1)
                                {
                          
                                                

                                               String pc_setAccountAuth= lC.nodeFromKey(senddata,"<pc>","</pc>");
                                               Application.showMessage("pc is ::"+pc_setAccountAuth); 
                                                
                                               if(pc_setAccountAuth==null)
                                                {
                                                                addIssues.add(" pc is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("pc from Send Xml  from SetAccountAuthorityInformation is ::"+" "+pc_setAccountAuth);
                                                     if(pc_setAccountAuth.equalsIgnoreCase("C9O2P5"))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 2:: WebServicall-pc********");
                                                                     Application.showMessage("Validation is Successfull with pc::"+" "+pc_setAccountAuth);
                                                                     v1=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("pc at Send Xml not Validated as "+pc_setAccountAuth);
                                                                 }
                                                }

                                               String dn_setAccountAuth= lC.nodeFromKey(senddata,"<dn>","</dn>");
                                               Application.showMessage("dn is ::"+dn_setAccountAuth);                                                                                                          
                                               if(dn_setAccountAuth==null)
                                                {
                                                                addIssues.add(" dn is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("dn from Send Xml  from SetAccountAuthorityInformation is ::"+" "+dn_setAccountAuth);
                                                     if(dn_setAccountAuth.equalsIgnoreCase("COMC"))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 3:: WebServicall-dn********");
                                                                     Application.showMessage("Validation is Successfull with dn::"+" "+dn_setAccountAuth);
                                                                     v2=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("dn at Send Xml not Validated as "+dn_setAccountAuth);
                                                                 }
                                                }
                                               
                                               //----------
                                               
                                               String inxml=lC.nodeFromKey(senddata,"<inXML>","</inXML>").replace("&lt;", "<").replace("&gt;", ">");  
                                               Application.showMessage("In XML Values is"+inxml);
                                               //-----------
                                               
                                               copsAuth=XH.ValidatingFields(input, c, "copsAuth", inxml);
                                               
                                               
                                               
                                               
                                               
                                               //-----------

                                               
                                                String readBack_setAccountAuth= lC.nodeFromKey(lC.nodeFromKey(recievedata,"<SetAccountAuthorityInformationReturn>","</SetAccountAuthorityInformationReturn>").replace("&lt;","<").replace("&gt;",">"),"<readBack>","</readBack>");   
                                                 Application.showMessage("readBack is::"+readBack_setAccountAuth);
                                               if(readBack_setAccountAuth==null)
                                                {
                                                                addIssues.add(" readBack is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                            
                                                     if(readBack_setAccountAuth.equalsIgnoreCase("COMCC9O2P5SETAUTHINFO"))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 4:: WebServicall-readBack********");
                                                                     Application.showMessage("Validation is Successfull with dn::"+" "+readBack_setAccountAuth);
                                                                     v3=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("readBack at Send Xml not Validated as "+readBack_setAccountAuth);
                                                                 }
                                                }
                                               
                                                
                                                String result_setAccountAuth= lC.nodeFromKey(lC.nodeFromKey(recievedata,"<SetAccountAuthorityInformationReturn>","</SetAccountAuthorityInformationReturn>").replace("&lt;","<").replace("&gt;",">"),"<result>","</result>");   
                                                    Application.showMessage("result is::"+result_setAccountAuth);
                                              if(result_setAccountAuth==null)
                                                {
                                                                addIssues.add(" result_setAccountAuth is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                            
                                                     if(result_setAccountAuth.equalsIgnoreCase("OK"))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 5:: WebServicall-result_setAccountAuth********");
                                                                     Application.showMessage("Validation is Successfull with result_setAccountAuth::"+" "+result_setAccountAuth);
                                                                     v4=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     addIssues.add("readBack at Send Xml not Validated as "+result_setAccountAuth);
                                                                 }
                                                }
                                               
                                                
                                                
                                               
                                break;
                                }
                 }
             }
                    	}
             
             if(v1*callFound*v2*v3*v4*copsAuth ==1)
             {
                    Application.showMessage("WebService Call :: setAccountAuthorityInfo is Success[All validation points are Success]");
             }
             else
             {
                     c.put("result", "false");
                     addIssues.add("WebService Call ::setAccountAuthorityInfo  is Failed with Validation Errors");
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


    
//----------------------------setAccounBasicInfo validation method--------------------
    public String SetAccountBasicInformation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException
    
    {

        Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
LibraryRtp  lC= new LibraryRtp ();
DBCursor  rs; 
DBObject obj = null;
List<String> addIssues=new ArrayList();
String Issues="Issues from setAccountBasicInformation:::";
        int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,inxmlval=0;
        String xmldata1 ="receive_data";
String xmldata2 ="SEND_DATA";
String rscallpresent = "true";
Application.showMessage("-------------------------------------------------------");
Application.showMessage("*****SetAccountBasicInformation_Validate Function******");       
Application.showMessage("-------------------------------------------------------");
HashMap Operation=lC.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("SetAccountBasicInformation"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SetAccountBasicInformation"));
        try
        {
                     //   Statement st =lC. dBConnect(input, c); 
//   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/SetAccountBasicInformation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
        	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
        	if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		lC.CallsFoundResultSet(input, c);
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
            // Application.showMessage("Your Send XML is::\n"+senddata);
            // Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
   
                                   
                    String CcentralNum_SetBasicAcc=lC.nodeFromKey(senddata,"<accountNumber>","</accountNumber>");
                   Application.showMessage("Ccentral Number is ::"+CcentralNum_SetBasicAcc);               
                    if(CcentralNum_SetBasicAcc.equals(c.getValue("CcentralNum")))
        {
                        Application.showMessage("Recieve XML is::  \n"+ recievedata);
                        Application.showMessage("SEND XML is :: \n"+senddata);
                        //System.out.printf("SEND XML is %s \n",senddata);
                        Application.showMessage("*******Validation Point 1 :: WebServicall-Get Account Call********");
                        Application.showMessage("Validation is Successfull with Input Account Number"+CcentralNum_SetBasicAcc);
                        callFound=1;
        }
        else
        {
                        continue;
        }
        
                    if(callFound==1)
                    {
              
                                    String pc_SetBasicAcc=lC.nodeFromKey(senddata,"<pc>","</pc>");
                                   Application.showMessage("pc is ::"+pc_SetBasicAcc); 
                                    
                                   if(pc_SetBasicAcc==null)
                                    {
                                                    addIssues.add(" pc is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("pc from Send Xml  from SetAccountBasicInformation is ::"+" "+pc_SetBasicAcc);
                                         if(pc_SetBasicAcc.equalsIgnoreCase("C9O2P5"))
                                                     {
                                                         Application.showMessage("*******Validation Point 2:: WebServicall-pc********");
                                                         Application.showMessage("Validation is Successfull with pc::"+" "+pc_SetBasicAcc);
                                                         v1=1;
                                                     }
                                                     else
                                                     {
                                                         addIssues.add("pc at Send Xml not Validated as "+pc_SetBasicAcc);
                                                     }
                                    }
                                 
                                    String action_SetBasicAcc=lC.nodeFromKey(senddata,"<action>","</action>");
                                   Application.showMessage("Action is ::"+action_SetBasicAcc);
                                   
                                   if(action_SetBasicAcc==null)
                                    {
                                                    addIssues.add(" Action is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("Action from Send Xml  from SetAccountBasicInformation is ::"+" "+action_SetBasicAcc);
                                         if(action_SetBasicAcc.equalsIgnoreCase("EDIT") || action_SetBasicAcc.equalsIgnoreCase("NEW"))
                                                     {
                                                         Application.showMessage("*******Validation Point 3:: WebServicall-Action********");
                                                         Application.showMessage("Validation is Successfull with Action::"+" "+action_SetBasicAcc);
                                                         v2=1;
                                                     }
                                                     else
                                                     {
                                                         addIssues.add("Action at Send Xml not Validated as "+action_SetBasicAcc);
                                                     }
                                    }

                                   String dn_SetBasicAcc=lC.nodeFromKey(senddata,"<dn>","</dn>");
                                   Application.showMessage("dn is ::"+dn_SetBasicAcc); 
                                    
                                   if(dn_SetBasicAcc==null)
                                    {
                                                    addIssues.add(" dn is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("dn from Send Xml  from SetAccountBasicInformation is ::"+" "+dn_SetBasicAcc);
                                         if(dn_SetBasicAcc.equalsIgnoreCase("COMC"))
                                                     {
                                                         Application.showMessage("*******Validation Point 4:: WebServicall-dn********");
                                                         Application.showMessage("Validation is Successfull with dn::"+" "+dn_SetBasicAcc);
                                                         v3=1;
                                                     }
                                                     else
                                                     {
                                                         addIssues.add("dn at Send Xml not Validated as "+dn_SetBasicAcc);
                                                     }
                                    }

                                   String billingAccountNumber_SetBasicAcc=lC.nodeFromKey(senddata,"<billingAccountNumber>","</billingAccountNumber>");   
                                   Application.showMessage("billingAccountNumber is ::"+billingAccountNumber_SetBasicAcc); 
            
                                   if(billingAccountNumber_SetBasicAcc==null)
                                    {
                                                    addIssues.add("Send Xml FirstName is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+billingAccountNumber_SetBasicAcc);
                                         if(billingAccountNumber_SetBasicAcc.equals(c.getValue("ACC_input")))
                                                     {
                                                         Application.showMessage("*******Validation Point 5 :: WebServicall-Account Number********");
                                                         Application.showMessage("Validation is Successfull with Account Number::"+" "+billingAccountNumber_SetBasicAcc);
                                                         v4=1;
                                                     }
                                                     else
                                                     {
                                                         addIssues.add("Account Number at Send Xml not Validated as "+billingAccountNumber_SetBasicAcc);
                                                     }
                                    }  
                                   
                                   String inxml=lC.nodeFromKey(senddata,"<inXML>","</inXML>").replace("&lt;", "<").replace("&gt;", ">");  
                                   Application.showMessage("In XML Values is"+inxml);
                                   //-----------
                                   List<String> cops=XH.listOfCOPSfields(input, c);
                                   Map copsValue=XH.COPsVal(input, c);
                                   for(int i=0;i<cops.size();i++)
                                   {
                                	   String copsfield=lC.nodeFromKey(inxml,"<"+cops.get(i)+">","</"+cops.get(i)+">").trim();
                                	   Application.showMessage(cops.get(i) +" from setAccountBasicInfo request in InXML is"+copsfield);
                                	   Application.showMessage(cops.get(i) +" from  request  is"+copsValue.get(cops.get(i)).toString().trim());
                                	   if(copsfield.equalsIgnoreCase(copsValue.get(cops.get(i)).toString()))
                                	   {
                                		   Application.showMessage("*******Validation Point in InXML:: WebServicall********");
                                           Application.showMessage("Validation is Successfull with "+cops.get(i)+"::"+" "+copsfield);
                                           inxmlval=1;
                                	   }
                                	   else
                                	   {
                                		   addIssues.add("Validation is not Successfull with "+cops.get(i)+"::"+" "+copsfield);
                                		   inxmlval=0;
                                		   break;
                                	   }
                                   }
                                   
                                   
                                   
                                   
                                   
                                   //-----------
                                                                   
                                    String readBack_SetBasicAcc=lC.nodeFromKey(lC.nodeFromKey(recievedata,"<SetAccountBasicInformationReturn>","</SetAccountBasicInformationReturn>").replace("&lt;","<").replace("&gt;",">"),"<readBack>","</readBack>");   
                                   
                                   Application.showMessage("Read Back is::"+readBack_SetBasicAcc);
                                   
                                   if(readBack_SetBasicAcc==null)
                                    {
                                                    addIssues.add(" readBack is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("readBack from Send Xml  from SetAccountBasicInformation is ::"+" "+readBack_SetBasicAcc);
                                         if(readBack_SetBasicAcc.equalsIgnoreCase("COMCC9O2P5SETBASICINFO"))
                                                     {
                                                         Application.showMessage("*******Validation Point 6:: WebServicall-readBack********");
                                                         Application.showMessage("Validation is Successfull with readBack::"+" "+readBack_SetBasicAcc);
                                                         v5=1;
                                                     }
                                                     else
                                                     {
                                                         addIssues.add("readBack at Send Xml not Validated as "+readBack_SetBasicAcc);
                                                     }
                                    }
                                   
                                   String result_SetBasicAcc=lC.nodeFromKey(lC.nodeFromKey(recievedata,"<SetAccountBasicInformationReturn>","</SetAccountBasicInformationReturn>").replace("&lt;","<").replace("&gt;",">"),"<result>","</result>");   
                                   
                                  Application.showMessage("Result is::"+result_SetBasicAcc);

                                 if(result_SetBasicAcc==null)
                                    {
                                                    addIssues.add(" readBack is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("readBack from Send Xml  from SetAccountBasicInformation is ::"+" "+result_SetBasicAcc);
                                         if(result_SetBasicAcc.equalsIgnoreCase("OK"))
                                                     {
                                                         Application.showMessage("*******Validation Point 7:: WebServicall-readBack********");
                                                         Application.showMessage("Validation is Successfull with readBack::"+" "+result_SetBasicAcc);
                                                         v6=1;
                                                     }
                                                     else
                                                     {
                                                         addIssues.add("readBack at Send Xml not Validated as "+result_SetBasicAcc);
                                                     }
                                    }
                                   
                    break;
                    }
     }
 }
        	}
 
 if(v1*callFound*v2*v3*v4*v5*v6*inxmlval ==1)
 {
        Application.showMessage("WebService Call :: SetAccountBasicInformation is Success[All validation points are Success]");
 }
 else
 {
         c.put("result", "false");
         addIssues.add("WebService Call ::SetAccountBasicInformation is Failed with Validation Errors");
 }
//    st.close();
 rs.close();
        }    
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
                        e.printStackTrace();
                        
        }
     Application.showMessage("rscallpresent"+rscallpresent);
		return rscallpresent;

    }

//-----------------------------geodeticquiery validation method----------------
    
    public String geodeticQuery_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
    {

	      
	       Thread.sleep(8000); // Think time in JVM [waits for 10 secs here]
	       LibraryRtp  lC= new LibraryRtp ();
	       DBCursor  rs; 
	       DBObject obj = null;
	       List<String> addIssues=new ArrayList();
		    String Issues="Issues from geodetivQuery:::";
	       int callFound=0,v1=0;
	       String xmldata1 ="receive_data";
	       String xmldata2 ="SEND_DATA";

String rscallpresent="true";
	    Application.showMessage("-----------------------------------------------------");
	    Application.showMessage("***********geodeticQuery_Validate Function***********");       
	    Application.showMessage("-----------------------------------------------------");
	    HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("geodeticQuery"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("geodeticQuery"));

	       try
	       {
	              //Statement st =lC. dBConnect(input, c);   
	              
	              String acc = c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber");
	              Application.showMessage("Account number is ::"+acc);
	              
	      //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'intrado:frlResource/geodeticQuery' and USER_DATA2 ='"+acc+"'order by creation_time desc)where rownum <= 20");
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

	    
	              
	              String sch_ResponderType= lC.nodeFromKey(recievedata,"<ResponderType>","</ResponderType>");
	              Application.showMessage("sch_ResponderType is::"+sch_ResponderType);
	              
	              if(sch_ResponderType==null)
	              {
	                    addIssues.add("Send Xml sch_ResponderType is NULL");
	              /*   continue;*/
	              }
	             else
	             {
	              if(sch_ResponderType.equalsIgnoreCase("Failure"))
	                  {
	                      Application.showMessage("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
	                      Application.showMessage("Validation is Successfull with sch_ResponderType::"+" "+sch_ResponderType);
	                      v1=1;
	                      break;
	                  }
	             
	                  else
	                  {
	                       int OutPut = 0;
	                      addIssues.add("sch_ResponderType at Send Xml not Validated as "+sch_ResponderType);
	                  }
	              }
	                          
	           }
	             
	         }
	             
	        
	       }
	       }
	       }
	       catch (SQLException e)
	       {
	           System.out.println("Connection Failed! Check output console");
	              e.printStackTrace();
	              
	       }
	       
	       if(v1==1)
	       {
	              Application.showMessage("WebService Call :: geodeticquiery is Success[All validation points are Success]");
	       }
	       else
	       {
	              addIssues.add("Validation failed with Errors...!");
	              
	       }
		return rscallpresent;
	
    
    }
    
    
    
//----------------------------createCMSAccountId validation method--------------------
    
    public String createCMSAccountID_Validate(Object input, ScriptingContext c) throws Exception
    {

        Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
LibraryRtp  lC= new LibraryRtp ();
DBCursor  rs; 
DBObject obj = null;
List<String> addIssues=new ArrayList();
String Issues="Issues from createCMSAccountID:::";

        int callFound=0,v1=0,v2=0;
        String xmldata1 ="receive_data";
String xmldata2 ="SEND_DATA";
String Time= c.get("BaseTime").toString();
String rscallpresent="true";
//  c.setValue("createCMSaccountID","true");
Application.showMessage("-------------------------------------------------");
Application.showMessage("*****createCMSAccountID_Validate Function******");       
Application.showMessage("-------------------------------------------------");
//  lC.findThinktimeOperationRTP(input, c);
//  c.setValue("createCMSaccountID","false");
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("createCMSAccountID"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("createCMSAccountID"));
        try
        {
                     //   Statement st =lC. dBConnect(input, c);
//  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'hss:HomeSecurityServicePort/createCMSAccountID' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"'order by creation_time desc)where rownum <= 20");
        	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
        	if(rs==null)
        	{
        		c.put("createCMSAccountIDIssue", addIssues.add(c.getValue("OPERATIONVALIDATION")+ " Not found on time"));
        		 
        		  Application.showMessage("Entering here");
        		 rscallpresent = "false";
        		// rs.close();
        		
        	
        		
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
                 Application.showMessage("Your Send XML is::\n"+senddata);
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
             Application.showMessage("Your Send XML is::\n"+senddata);
             Application.showMessage("RECIEVE XML is ::\n"+recievedata); 

                   
                    String billingArrangementID_cmsSend= lC.nodeFromKey(senddata,"<billingArrangementID>","</billingArrangementID>");
                    Application.showMessage("billingArrangementID_cmsSend is ::"+billingArrangementID_cmsSend); 
                    if(billingArrangementID_cmsSend.equals(c.getValue("ACC_input")))
        {
                      //  Application.showMessage("Recieve XML is::  \n"+ recievedata);
                     //   Application.showMessage("SEND XML is :: \n"+senddata);
                        //System.out.printf("SEND XML is %s \n",senddata);
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
              
                                    String cmsAccountID= lC.nodeFromKey(recievedata,"<cmsAccountID>","</cmsAccountID>");
                                    Application.showMessage("cmsAccountID is ::"+cmsAccountID);
                                    c.setValue("CcentralNum", cmsAccountID);
                                    
                                    String billingArrangementIDRes_CMSAccountID= lC.nodeFromKey(recievedata,"<billingArrangementID>","</billingArrangementID>");
                                    Application.showMessage("billingArrangementIDRes_CMSAccountID is::"+billingArrangementIDRes_CMSAccountID); 
                    
                                    if(billingArrangementIDRes_CMSAccountID==null)
                                    {
                                    	addIssues.add("Send Xml Account Number is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+billingArrangementIDRes_CMSAccountID);
                                         if(billingArrangementIDRes_CMSAccountID.equals(c.getValue("ACC_input")))
                                                     {
                                                         Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
                                                         Application.showMessage("Validation is Successfull with AccountNumber::"+" "+billingArrangementIDRes_CMSAccountID);
                                                         v1=1;
                                                     }
                                                     else
                                                     {
                                                    	 addIssues.add("Account Number at Send Xml not Validated as "+billingArrangementIDRes_CMSAccountID);
                                                     }
                                    }                  

                                    String responseStatus_CMSAccountID= lC.nodeFromKey(recievedata,"<responseStatus>","</responseStatus>");
                                    Application.showMessage("responseStatus_CMSAccountID is::"+responseStatus_CMSAccountID); 
                                     if(responseStatus_CMSAccountID==null)
                                     {
                                                    addIssues.add("Send Xml Account Number is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+responseStatus_CMSAccountID);
                                         if(responseStatus_CMSAccountID.equalsIgnoreCase("Success"))
                                                     {
                                                         Application.showMessage("*******Validation Point 2 :: WebServicall-responseStatus_CMSAccountID********");
                                                         Application.showMessage("Validation is Successfull with AccountNumber::"+" "+responseStatus_CMSAccountID);
                                                         v2=1;
                                                     }
                                                     else
                                                     {
                                                    	 addIssues.add("Account Number at Send Xml not Validated as "+responseStatus_CMSAccountID);
                                                     }
                                    }                  
                       
                    break;
                    }
                    
                                
     }
 }
        	}
 if(v1*v2==1)
 {
        Application.showMessage("WebService Call :: CreateCMSAccount is Success[All validation points are Success]");
        c.put("createCMSAccountIDIssue", "no");
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
	// c.report(Issues.concat("WebService Call :: CreateCMSAccount ID  is Failed with Validation Errors").concat("**"));
	 c.put("createCMSAccountIDIssue", Issues.concat("WebService Call :: CreateCMSAccount ID  is Failed with Validation Errors").concat("**"));
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
    
//----------------------------Order Update validation method--------------------
    public String orderUpdate_Validate(Object input, ScriptingContext c,String orderType,String servceTobefind) throws Exception
    
    {

        Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
LibraryRtp  lC= new LibraryRtp ();
DBCursor  rs; 
DBObject obj = null;
List<String> addIssues=new ArrayList();
String Issues="Issues from orderUpdate:::";
        String vService=null;
        int callFound=0,v1=0,v2=0,v3=0,v4=0,status=0;
        String rscallpresent="true";
String xmldata2 ="SEND_DATA";
String Time= c.get("BaseTime").toString();
Application.showMessage("-------------------------------------------------------------");
Application.showMessage("***********setOrderStatus_Validate Function**************");       
Application.showMessage("-------------------------------------------------------------");
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("setOrderStatus"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("setOrderStatus"));
        try
        {
                     //  Statement st =lC. dBConnect(input, c);  
//   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
        	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
        	
        	if(rs.equals("null"))
        	                                	{
        		
        		
        		c.put("setOrderstatusIssue", addIssues.add(c.getValue("OPERATIONVALIDATION")+ " Not found on time"));
     		  
     		  
     		 rscallpresent = "false";
     		// rs.close();
     		
        	                                		
        	                                	
        	                                		
        	                                	}
        	                                	else
        	{
        	                                		 while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
        	                                	        {
        	                                	       obj=rs.next();
        	                                	          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
        	                                	          {
        	                                	 rowmsg=((BasicBSONObject) obj).getString("_id");

        	                                	  if(((BasicBSONObject) obj).getString("REQUEST")==null)
        	                                     {
        	                                         Application.showMessage("Your SEND XML is NULL \n");             
        	                                         String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
        	                                         Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
        	                                     }
        	                                     else
        	                                 
        	                          {
        	                                     String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
        	                                   //  String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
        	                                     Application.showMessage("Your send XML is::\n"+senddata);
        	                                   //  Application.showMessage("RECIEVE XML is ::\n"+recievedata); 

                                   
       // String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
        	                                     String senddataTrim=lC.RemoveNameSpaces(senddata);
        	                                     Application.showMessage("Your send XML Trim  is::\n"+senddataTrim);
        	                                     String accountNumber_ou= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'accountNumber' ]/@value");
        	                                     Application.showMessage("accountNumber is ::"+accountNumber_ou); 
        	                                  
                   if(accountNumber_ou==null)
                    {
                          Application.showMessage("Send Xml billingOrderId is NULL");
                                    continue;
                    }
                  else if(accountNumber_ou.equals(c.getValue("ACC_input")))
        {
                        
                       // Application.showMessage("SEND XML is :: \n"+senddata);
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
              
                    	

                                   String vomInstance= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                       Application.showMessage("vomInstance is ::"+vomInstance); 

                                   
                                    
                                    if(c.getValue("IsDemi").equalsIgnoreCase("true"))
                                   {
                                       
                                    String ordSource= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                         Application.showMessage("ordSource is ::"+ordSource); 
                                    String billingOrderId= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'billingOrderId' ]/@value");
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
                                                  
                                                    String Service= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'SHIPPING' ]/elementData[@name = 'append' ]/@value");
                                                   String service1[]=Service.split("Service =");
                                                   String service2[]=service1[1].split("Hav Market");
                                                       Application.showMessage("Service is ::"+service2[0].trim());
                                                       Application.showMessage("servceTobefind is ::"+servceTobefind.trim());
                                                  
                                                         if(service2[0].trim().equals(servceTobefind.trim()))
                                                                     {
                                                                         Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
                                                                         Application.showMessage("Validation is Successfull with Service::"+" "+service2[0].trim());
                                                                         v4=1;
                                                                         c.setValue("Product",Service);
                                                                     }
                                                                     else
                                                                     {
                                                                         addIssues.add("Service at Send Xml not Validated as "+service2[0]);
                                                                     }
                                                     
                                   
                                    }
                                   else
                                   {
                                       v1=1;
                                       
                                        
                                        String Service= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'SHIPPING' ]/elementData[@name = 'append' ]/@value");
                                            Application.showMessage("Service is ::"+Service); 
                                            String service1[]=Service.split("Service =");
                                            String service2[]=service1[1].split("Hav Market");
                                                Application.showMessage("Service is ::"+service2[0].trim());
                                                Application.showMessage("servceTobefind is ::"+servceTobefind.trim());
                                                    
                                                   if(Service==null)
                                                   {
                                                                   addIssues.add("Send Xml Service is NULL");
                                                                   continue;
                                                   }
                                                   else
                                                   {
                                                       
                                                         Application.showMessage("Service from Input   is ::"+" "+servceTobefind);
                                                        Application.showMessage("Service from Send Xml   is ::"+" "+Service);
                                                        if(service2[0].trim().equals(servceTobefind.trim()))
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
                                   String inputChannel= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'inputChannel' ]/@value");
                                      Application.showMessage("inputChannel is ::"+inputChannel);
                                   
                                    String ordType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordType' ]/@value");
                                      Application.showMessage("ordType is ::"+ordType);
                                   
                                   if(ordType==null)
                                    {
                                                    addIssues.add("Send Xml ordType is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
                                         if(ordType.equalsIgnoreCase(orderType))
                                                     {
                                                         Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                                         Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
                                                         v2=1;
                                                     }
                                                     else
                                                     {
                                                         addIssues.add("lt_fireNumber at Send Xml not Validated as "+ordType);
                                                     }
                                    }  
                                  
                                   
                             String ordStatus= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordStatus' ]/@value");
                               Application.showMessage("ordStatus is ::"+ordStatus);
                               
                               if(ordStatus==null)
                                {
                                                addIssues.add("Send Xml ordStatus is NULL");
                                                continue;
                               }
                                else
                                {
                                     Application.showMessage("ordStatus from Send Xml   is ::"+" "+ordStatus);
                                     if(ordStatus.equals("COM"))
                                                 {
                                                     Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                                     Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                                    status=1;
                                                 }
                                     else if(ordStatus.equals("ERR"))
                                                 {
                                                     Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                                     addIssues.add("Validation with ERROR ordStatus:: "+ordStatus);
                                                     
                                                 }
                                                 else
                                                 {
                                                     addIssues.add("ordStatus at Send Xml not Validated as "+ordStatus);
                                                 }
                                }  
                                   
                                    
                                    String customerType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'customerType' ]/@value");
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
        	                                	        
        	}
 if(v1*callFound*v2*v3*v4*status ==1)
 {
        Application.showMessage("WebService Call :: setOrderstatus_Validate is Success[All validation points are Success]");
        c.put("setOrderstatusIssue", "no");
 //   c.setValue("IsDemi", "false");
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
	// c.report(Issues.concat("WebService Call :: setOrderStatus is Failed with Validation Errors").concat("**"));
	 c.put("setOrderstatusIssue", Issues.concat("WebService Call :: setOrderStatus is Failed with Validation Errors").concat("|||"));
 }
//   st.close();
 rs.close();
 
//Lp.reportingToExcel(Object , ScriptingContext);
         
        }
        }

        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
                        e.printStackTrace();
                                       }
		return rscallpresent;
        

    }
    
//----------------------
 public String ErrororderUpdate_Validate(Object input, ScriptingContext c,String orderType,String servceTobefind,String taskErrorcode) throws Exception
    
    {

        Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
LibraryRtp  lC= new LibraryRtp ();
DBCursor  rs; 
DBObject obj = null;
List<String> addIssues=new ArrayList();
String Issues="Issues from ErrororderUpdateValidate:::";
        String vService=null;
        int callFound=0,v1=0,v2=0,v3=0,v4=0,status=0,taskErrcod=0,TaskErrSo=0;
        String rscallpresent="true";
String xmldata2 ="SEND_DATA";
String Time= c.get("BaseTime").toString();
Application.showMessage("-------------------------------------------------------------");
Application.showMessage("***********Error setOrderStatus_Validate Function**************");       
Application.showMessage("-------------------------------------------------------------");
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("setOrderStatus"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("setOrderStatus"));
        try
        {
                     //  Statement st =lC. dBConnect(input, c);  
//   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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

        	                                	  if(((BasicBSONObject) obj).getString("REQUEST")==null)
        	                                     {
        	                                         Application.showMessage("Your SEND XML is NULL \n");             
        	                                         String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
        	                                         Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
        	                                     }
        	                                     else
        	                                 
        	                          {
        	                                     String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
        	                                   //  String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
        	                                     Application.showMessage("Your send XML is::\n"+senddata);
        	                                   //  Application.showMessage("RECIEVE XML is ::\n"+recievedata); 

                                   
       // String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
        	                                     String senddataTrim=lC.RemoveNameSpaces(senddata);
        	                                     Application.showMessage("Your send XML Trim  is::\n"+senddataTrim);
        	                                     String accountNumber_ou= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'accountNumber' ]/@value");
        	                                     Application.showMessage("accountNumber is ::"+accountNumber_ou); 
        	                                  
                   if(accountNumber_ou==null)
                    {
                          Application.showMessage("Send Xml billingOrderId is NULL");
                                    continue;
                    }
                  else if(accountNumber_ou.equals(c.getValue("ACC_input")))
        {
                        
                       // Application.showMessage("SEND XML is :: \n"+senddata);
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
              
                    	

                                   String vomInstance= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                       Application.showMessage("vomInstance is ::"+vomInstance); 

                                   
                                    
                                    if(c.getValue("IsDemi").equalsIgnoreCase("true"))
                                   {
                                       
                                    String ordSource= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                         Application.showMessage("ordSource is ::"+ordSource); 
                                    String billingOrderId= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'billingOrderId' ]/@value");
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
                                                  
                                                    String Service= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'SHIPPING' ]/elementData[@name = 'append' ]/@value");
                                                   String service1[]=Service.split("Service =");
                                                   String service2[]=service1[1].split("Hav Market");
                                                       Application.showMessage("Service is ::"+service2[0].trim());
                                                       Application.showMessage("servceTobefind is ::"+servceTobefind.trim());
                                                  
                                                         if(service2[0].trim().equals(servceTobefind.trim()))
                                                                     {
                                                                         Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
                                                                         Application.showMessage("Validation is Successfull with Service::"+" "+service2[0].trim());
                                                                         v4=1;
                                                                         c.setValue("Product",Service);
                                                                     }
                                                                     else
                                                                     {
                                                                         addIssues.add("Service at Send Xml not Validated as "+service2[0]);
                                                                     }
                                                     
                                   
                                    }
                                   else
                                   {
                                       v1=1;
                                       
                                        
                                        String Service= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'SHIPPING' ]/elementData[@name = 'append' ]/@value");
                                            Application.showMessage("Service is ::"+Service); 
                                            String service1[]=Service.split("Service =");
                                            String service2[]=service1[1].split("Hav Market");
                                                Application.showMessage("Service is ::"+service2[0].trim());
                                                Application.showMessage("servceTobefind is ::"+servceTobefind.trim());
                                                    
                                                   if(Service==null)
                                                   {
                                                                   addIssues.add("Send Xml Service is NULL");
                                                                   continue;
                                                   }
                                                   else
                                                   {
                                                       
                                                         Application.showMessage("Service from Input   is ::"+" "+servceTobefind);
                                                        Application.showMessage("Service from Send Xml   is ::"+" "+Service);
                                                        if(service2[0].trim().equals(servceTobefind.trim()))
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
                                   String inputChannel= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'inputChannel' ]/@value");
                                      Application.showMessage("inputChannel is ::"+inputChannel);
                                   
                                    String ordType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordType' ]/@value");
                                      Application.showMessage("ordType is ::"+ordType);
                                   
                                   if(ordType==null)
                                    {
                                                    addIssues.add("Send Xml ordType is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
                                         Application.showMessage("orderType from Send Xml   is ::"+" "+orderType);
                                         if(ordType.equalsIgnoreCase(orderType))
                                                     {
                                                         Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                                         Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
                                                         v2=1;
                                                     }
                                                     else
                                                     {
                                                         addIssues.add("lt_fireNumber at Send Xml not Validated as "+ordType);
                                                     }
                                    }  
                                  
                                   
                             String ordStatus= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordStatus' ]/@value");
                               Application.showMessage("ordStatus is ::"+ordStatus);
                               
                               if(ordStatus==null)
                                {
                                                addIssues.add("Send Xml ordStatus is NULL");
                                                continue;
                               }
                                else
                                {
                                     Application.showMessage("ordStatus from Send Xml   is ::"+" "+ordStatus);
                                     if(ordStatus.equals("COM"))
                                                 {
                                                     Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                                     Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                                    status=1;TaskErrSo=1;taskErrcod=1;
                                                 }
                                     else if(ordStatus.equals("ERR"))
                                                 {
                                    	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                         Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                        status=1;
                                        
                                        //Task Validate
                                        
                                        String TaskErrorcode= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'TASK' ]/elementData[@name = 'errorCode' ]/@value");
                                        Application.showMessage("ordStatus is ::"+ordStatus);
                                        
                                        if(TaskErrorcode==null)
                                         {
                                                         addIssues.add("Send Xml TaskErrorcode is NULL");
                                                         continue;
                                        }
                                         else
                                         {
                                              Application.showMessage("TaskErrorcode from Send Xml   is ::"+" "+TaskErrorcode);
                                              if(TaskErrorcode.equals(taskErrorcode))
                                                          {
                                                              Application.showMessage("*******Validation Point 3 :: WebServicall-TaskErrorcode********");
                                                              Application.showMessage("Validation is Successfull with TaskErrorcode::"+" "+TaskErrorcode);
                                                              taskErrcod=1;
                                                          }
                                              
                                                          else
                                                          {
                                                              addIssues.add("TaskErrorcode at Send Xml not Validated as "+TaskErrorcode);
                                                          }
                                         } 
                                        
                                        
                                        String TaskErrorSource= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'TASK' ]/elementData[@name = 'errorSource' ]/@value");
                                        Application.showMessage("ordStatus is ::"+ordStatus);
                                        
                                        if(TaskErrorSource==null)
                                         {
                                                         addIssues.add("Send Xml TaskErrorSource is NULL");
                                                         continue;
                                        }
                                         else
                                         {
                                              Application.showMessage("TaskErrorSource from Send Xml   is ::"+" "+TaskErrorSource);
                                              if(TaskErrorSource.equals("XHOM.Fallout") || TaskErrorSource.equals("XHOM.Other"))
                                                          {
                                                              Application.showMessage("*******Validation Point 3 :: WebServicall-TaskErrorSource********");
                                                              Application.showMessage("Validation is Successfull with TaskErrorSource::"+" "+TaskErrorSource);
                                                              TaskErrSo=1;
                                                          }
                                              
                                                          else
                                                          {
                                                              addIssues.add("TaskErrorSource at Send Xml not Validated as "+TaskErrorSource);
                                                          }
                                         } 
                                                 }
                                                 else
                                                 {
                                                     addIssues.add("ordStatus at Send Xml not Validated as "+ordStatus);
                                                 }
                                } 
                               
                               //Task Validation
                              
                               //--------
                                   
                                    
                                    String customerType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'customerType' ]/@value");
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
        	                                	        }
 if(v1*callFound*v2*v3*v4*status*taskErrcod*TaskErrSo ==1)
 {
        Application.showMessage("WebService Call :: setOrderstatus_Validate is Success[All validation points are Success]");
 //   c.setValue("IsDemi", "false");
        c.put("errorSetOrderSttaus","no");
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
	// c.report(Issues.concat("WebService Call :: setOrderstatus is Failed with Validation Errors").concat("**"));
	 c.put("errorSetOrderSttaus", Issues.concat("WebService Call :: setOrderstatus is Failed with Validation Errors").concat("|||"));
 }
//   st.close();
 rs.close();
 
//Lp.reportingToExcel(Object , ScriptingContext);
         
        }
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
                        e.printStackTrace();
                                       }
		return rscallpresent;
        

    }
    
    
    
    //----------------------
    
    public String retreiveAttributesValues(String senddata,String path) throws Exception

    {

    	 List<String> addIssues=new ArrayList();
		    String Issues="Issues from retreiveattributes:::";

            DocumentBuilderFactory documentumentBuilderFactory = DocumentBuilderFactory.newInstance();

            documentumentBuilderFactory.setNamespaceAware(true);

            DocumentBuilder documentumentBuilder = documentumentBuilderFactory.newDocumentBuilder();

            Document document = documentumentBuilder.parse(senddata);
            Application.showMessage("Righty attribute is : " + document);

            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();           

            XPathExpression expr = xpath.compile(path);

            String names = (String) expr.evaluate(document, XPathConstants.STRING);

            Application.showMessage("Righty attribute is : " + names);



            //get the type attribute of cricketers with role = 'Bowler'

         /*   expr = xpath.compile("//cricketer[role='Bowler']/@type");

            NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

            for (int i = 0; i < nodes.getLength(); i++)

                    System.out.println("Righty attribute is : " + nodes.item(i).getNodeValue());*/
			return names;



    }


   
//----------------------------getcustomer permit requirements validation method--------------------
    public String getCustomerPermitRequirements_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException
    {

        Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
        LibraryRtp  lC= new LibraryRtp ();
        DBCursor  rs; 
        DBObject obj = null;
        List<String> addIssues=new ArrayList();
	    String Issues="Issues from getCustomerPermitRequirements:::";
        String xmldata1 ="receive_data";
String xmldata2 ="SEND_DATA";
int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
String Time= c.get("BaseTime").toString();
String rscallpresent = "true";
Application.showMessage("----------------------------------------------------------");
Application.showMessage("*****getCustomerPermitRequirements_Validate Function******");    
Application.showMessage("----------------------------------------------------------");

HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("getCustomerPermitRequirements"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getCustomerPermitRequirements"));
        try
        {
            //            Statement st =lC. dBConnect(input, c);
//    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'hss:HomeSecurityServicePort/getCustomerPermitRequirements' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
        	               Application.showMessage("Your Send XML is::\n"+senddata);
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
        	           Application.showMessage("Your Send XML is::\n"+senddata);
        	           Application.showMessage("RECIEVE XML is ::\n"+recievedata); 

                                    if(c.getValue("sEventSource").equalsIgnoreCase("DDP"))
                                    {
                                                    String SendAccount_getCus = lC.nodeFromKey(senddata,"<billingArrangementID>","</billingArrangementID>");
                                                    Application.showMessage("SEND Xml Account Number is ::"+SendAccount_getCus);
                                                    if(SendAccount_getCus==null)
                                                    {
                                                                    addIssues.add("Send Xml Account Number is NULL");
                                                                    continue;
                                                    }
                                                    else
                                                    {
                                                         Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+SendAccount_getCus);
                                                         Application.showMessage("Account Number from acc_input is ::"+" "+c.getValue("ACC_input"));
                                                         
                                                         if(SendAccount_getCus.equals(c.getValue("ACC_input")))
                                                                     {
                                                                         Application.showMessage("*******Validation Point 4 :: WebServicall-getCustomerPermitRequirements********");
                                                                         Application.showMessage("Validation is Successfull with AccountNumber::"+" "+SendAccount_getCus);
                                                                         v3=1;
                                                                     }
                                                                     else
                                                                     {
                                                                       //  addIssues.add("Account Number at Send Xml not Validated as "+SendAccount_getCus);
                                                                     }
                                                    }
                                                    
                                                    String billingArrangementID_getCus = lC.nodeFromKey(recievedata,"<billingArrangementID>","</billingArrangementID>");
                                                                 
                                                                  
                                                    if(billingArrangementID_getCus==null)
                                                    {
                                                                    System.out.printf("billingArrangementID_getCus value from getCustomerPermitRequirements is ::NULL \n");
                                                                    continue;
                                                    }
                                                    else
                                                    {
                                                        
                                                        Application.showMessage("billingArrangementID value from getCustomerPermitRequirements is::"+billingArrangementID_getCus);
                                                        //Application.showMessage(c.getValue("ACC_input"));
                                                        //Application.showMessage(billingArrangementID_getCus);
                                                        
                                                        if(billingArrangementID_getCus.equals(c.getValue("ACC_input")))
                                                        {
                                                           // Application.showMessage("getCustomerPermitRequirements msgid= "+ rs.getString(1));
                                                                        Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                        Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_getCus);
                                                                        callFound=1;
                                                        }
                                                        else
                                                        {
                                                                        continue;
                                                        }
                                                        
                                                        if(callFound==1)
                                                        {
                                                                         String permitreq_getCus= lC.nodeFromKey(recievedata,"<permitFee>","</permitFee>");
                                                                                                   
                                                                                                    if(permitreq_getCus==null)
                                                                                                    {
                                                                                                        Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::NULL \n");
                                                                                                                    System.out.printf("PERMIT REQUIRED value from getCustomerPermitRequirements is ::NULL \n");
                                                                                                                    addIssues.add("PERMIT REQUIRED is NULL");
                                                                                                                    continue;
                                                                                                    }
                                                                                                    else
                                                                                                    {
                                                                                                         Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+permitreq_getCus);
                                                                                                         if(permitreq_getCus.equals("0"))
                                                                                                                     {
                                                                                                                         Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
                                                                                                                         Application.showMessage("Validation is Successfull with PERMIT REQUIRED"+" "+permitreq_getCus);
                                                                                                                         v1=1;
                                                                                                                     }
                                                                                                                     else
                                                                                                                     {
                                                                                                                         continue;
                                                                                                                     }
                                                                                                    }
                                                                                                                    
                                                                                                                                                                                    
                                                                                                    String responseStatus_getCus = lC.nodeFromKey(recievedata,"<responseStatus>","</responseStatus>");
                                                                                                    if(responseStatus_getCus==null)
                                                                                                    {
                                                                                                                    addIssues.add("Response Status is NULL");
                                                                                                                    continue;
                                                                                                    }
                                                                                                    else
                                                                                                    {
                                                                                                         Application.showMessage("responseStatus value from getCustomerPermitRequirements is ::"+" "+responseStatus_getCus);
                                                                                                         if(responseStatus_getCus.equalsIgnoreCase("Success"))
                                                                                                                     {
                                                                                                                         Application.showMessage("*******Validation Point 3 :: WebServicall-rtpTestInterface********");
                                                                                                                        Application.showMessage("Validation is Successfull with responseStatus::"+" "+responseStatus_getCus);
                                                                                                                         v2=1;
                                                                                                                     }
                                                                                                                     else
                                                                                                                     {
                                                                                                                         continue;
                                                                                                                    }
                                                                                                    } 
                                                                                                    
                                                        }
                                                    }
                                    }
                                    
                                    else if(c.getValue("sEventSource").equalsIgnoreCase("CSG"))
                                    {
                                                    String SendAccount_getCus = lC.nodeFromKey(senddata,"<csgHouseKey>","</csgHouseKey>");
                                                    Application.showMessage("SEND Xml Account Number is ::"+SendAccount_getCus);
                                                    if(SendAccount_getCus==null)
                                                    {
                                                                    addIssues.add("Send Xml Account Number is NULL");
                                                                    continue;
                                                    }
                                                    else
                                                    {
                                                         Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+SendAccount_getCus);
                                                         if(SendAccount_getCus.equals(c.getValue("sHOUSE_KEY")))
                                                                     {
                                                                         Application.showMessage("*******Validation Point 4 :: WebServicall-getCustomerPermitRequirements********");
                                                                         Application.showMessage("Validation is Successfull with sHOUSE_KEY::"+" "+SendAccount_getCus);
                                                                         v3=1;
                                                                     }
                                                                     else
                                                                     {
                                                                         addIssues.add("House Key at Send Xml not Validated as "+SendAccount_getCus);
                                                                     }
                                                    }
                                                    
                                                    String ErrorCode= lC.nodeFromKey(recievedata, "<messageCode>", "</messageCode>");
                                                    
                                                    String ErrorCodeText= lC.nodeFromKey(recievedata, "<messageText>", "</messageText>");
                                                    Application.showMessage("HomeSecurity Error"+ErrorCodeText);
                                                   
                                                    if(ErrorCode.equalsIgnoreCase("HOMESECURITYSERVICE-113"))
                                                    {
                                                        Application.showMessage("HOMESECURITY ERROR");
                                                        callFound=1;
                                                        v1=1;
                                                        v2=1;
                                                        v3=1;
                                                    }
                                                    else if(ErrorCodeText.equalsIgnoreCase("HOMESECURITYSERVICE-601"))
                                                    {
                                                        Application.showMessage("HOMESECURITY ERROR");
                                                        callFound=1;
                                                        v1=1;
                                                        v2=1;
                                                        v3=1;
                                                    }
                                                    else
                                                    {
                                                        String billingArrangementID_getCus = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
                                                                                 
                                                                                  
                                                                    if(billingArrangementID_getCus==null)
                                                                    {
                                                                                    System.out.printf("billingArrangementID_getCus value from getCustomerPermitRequirements is ::NULL \n");
                                                                                    continue;
                                                                    }
                                                                    else
                                                                    {
                                                                        
                                                                        Application.showMessage("billingArrangementID value from getCustomerPermitRequirements is::"+billingArrangementID_getCus);
                                                                        //Application.showMessage(c.getValue("ACC_input"));
                                                                        //Application.showMessage(billingArrangementID_getCus);
                                                                        
                                                                        if(billingArrangementID_getCus.equals(c.getValue("ACC_input")))
                                                                        {
                                                                            //Application.showMessage("getCustomerPermitRequirements msgid= "+ rs.getString(1));
                                                                                        Application.showMessage("Recieve XML is  \n"+ recievedata);
                                                                                        Application.showMessage("SEND XML is  \n"+ senddata);
                                                                                        Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                                        Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_getCus);
                                                                                        callFound=1;
                                                                        }
                                                                        else
                                                                        {
                                                                                        continue;
                                                                        }
                                                                        
                                                                        if(callFound==1)
                                                                        {
                                                                                         String permitreq_getCus= lC.nodeFromKey(recievedata,"<typ:permitFee>","</typ:permitFee>");
                                                                                                                   
                                                                                                                    if(permitreq_getCus==null)
                                                                                                                    {
                                                                                                                        Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::NULL \n");
                                                                                                                                    System.out.printf("PERMIT REQUIRED value from getCustomerPermitRequirements is ::NULL \n");
                                                                                                                                    addIssues.add("PERMIT REQUIRED is NULL");
                                                                                                                                    continue;
                                                                                                                    }
                                                                                                                    else
                                                                                                                    {
                                                                                                                         Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+permitreq_getCus);
                                                                                                                         if(permitreq_getCus.equals("0"))
                                                                                                                                     {
                                                                                                                                         Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
                                                                                                                                         Application.showMessage("Validation is Successfull with PERMIT REQUIRED"+" "+permitreq_getCus);
                                                                                                                                         v1=1;
                                                                                                                                     }
                                                                                                                                     else
                                                                                                                                     {
                                                                                                                                         continue;
                                                                                                                                     }
                                                                                                                    }
                                                                                                                                    
                                                                                                                                                                                                    
                                                                                                                    String responseStatus_getCus = lC.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:responseStatus>");
                                                                                                                    if(responseStatus_getCus==null)
                                                                                                                    {
                                                                                                                                    addIssues.add("Response Status is NULL");
                                                                                                                                    continue;
                                                                                                                    }
                                                                                                                    else
                                                                                                                    {
                                                                                                                         Application.showMessage("responseStatus value from getCustomerPermitRequirements is ::"+" "+responseStatus_getCus);
                                                                                                                         if(responseStatus_getCus.equalsIgnoreCase("Success"))
                                                                                                                                     {
                                                                                                                                         Application.showMessage("*******Validation Point 3 :: WebServicall-rtpTestInterface********");
                                                                                                                                         Application.showMessage("Validation is Successfull with responseStatus::"+" "+responseStatus_getCus);
                                                                                                                                         v2=1;
                                                                                                                                     }
                                                                                                                                     else
                                                                                                                                     {
                                                                                                                                         continue;
                                                                                                                                     }
                                                                                                                    } 
                                                                                                                    
                                                                        }
                                                                    }
                                                    }  
                                    }                                                                                                  
                                    }
                
                                                                   
                                    if(v1*v2*v3==1)
                                    {
                                        ValidationSuccess=1;
                                        Application.showMessage("getCustomerpermitRequirements Validation Points are Success");
                                        break;
                                    }
                    
                                                        // ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));                                 
                     }
                    
                   
                    
        	}
     if(ValidationSuccess*callFound==0)
     {

//Yamini
    	 if(callFound!=1)
    	 {
    		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+c.get("BaseTime"));
             
    	 }
        	 for(int i=0;i<addIssues.size();i++)
        	 {
        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
        	 }
        	 c.put("getCustomerPermitReqIssues",Issues.concat("WebService Call :: getCustomerPermitrequirements is Failed with Validation Errors").concat("**"));
         
     }
     else
     {
    	 c.put("getCustomerPermitReqIssues", "no");
        Application.showMessage("********WebService Call::getCustomerPermitRequirements Valiadted ********");      
     }
//     st.close();
     rs.close();
        }
        }
        catch (SQLException e)
        {
            Application.showMessage("Connection Failed! Check output console");
                        e.printStackTrace();
                       
        }
		return rscallpresent;
       

    }
    

    
//----------------------------DDS.processHomesecurity-COS validation method--------------------
    
    
    {
    	
    }
     
  //----------------------------Order Update-COS validation method--------------------

    public String orderUpdateCos_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException
    {

		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs; 
	     DBObject obj = null;
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from orderUpdateCOS:::";
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
		 		            	 if(Service.equals(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE").trim()))
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
			            	 if(ordType.equals("CHG"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with Order Type::"+" "+ordType);
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
	         {//Yamini
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.report(Issues.concat("WebService Call :: setOrderstatus is Failed with Validation Errors").concat("**"));
	         
	        	  c.setValue("IsRemoveGroup", "false");
	        	 c.setValue("IsUpdateTier", "false");
	        	 c.setValue("IsAddGroup", "false");
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
    
    //-------------------Responder Info Failed validation method-------------------
    
    public String responderInfofailed_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
	       Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	       DBCursor  rs;
	       int callFound=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0;
	       String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     DBObject obj=null;
String rscallpresent="true";
List<String> addIssues=new ArrayList();
String Issues="Issues from responderInfoFailedValidation:::";
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********responderInfo failed response_Validate Function***********");       
	     Application.showMessage("-----------------------------------------------------");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("responderInfo"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("responderInfo"));

	       try
	       {
	              
	             //  Statement st =lC. dBConnect(input, c);  
	                     
	              String acc = c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber");
	              Application.showMessage("Account number is ::"+acc);
	              
	               
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'intrado:frlResource/responderInfo' and USER_DATA2 ='"+acc+"' order by creation_time desc)where rownum <= 20");
	              rs=XH.XHOM_reduceThinkTimeRTP(input, c);
	              if(rs == null)
              	{
              		rscallpresent = "false";
              		
              	
              		
              	}
              	else
{

            		while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
                    {
                   obj = rs.next();
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
             Application.showMessage("Your Send XML is::\n"+senddata);
             Application.showMessage("RECIEVE XML is ::\n"+recievedata); 

	                                                             
	                          String sch_overallMatch= lC.nodeFromKey(recievedata,"<overallMatch>","</overallMatch>");
	                         Application.showMessage("sch:overallMatch is::"+sch_overallMatch);
	                         
	                          if(sch_overallMatch==null)
	                           {
	                                 addIssues.add("Send Xml sch_overallMatch is NULL");
	                                 continue;
	                           }
	                          else
	                          {
	                             if(sch_overallMatch.equalsIgnoreCase("Failure"))
	                                  {
	                                    Application.showMessage("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
	                                    Application.showMessage("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
	                                    v4=1;
	                                  }
	                                  else
	                                  {
	                                    addIssues.add("sch_overallMatch at Send Xml not Validated as "+sch_overallMatch);
	                                  }
	                          }
	                         
	                         
	                          
	                   break;
	                   }
	             }
                    }
	   
	       if(v4==1) { 
	              Application.showMessage("WebService Call :: Intrado is Success[All validation points are Success]");
	         }
	       else
	         {//Yamini
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.report(Issues.concat("WebService Call :: Intrado is Failed with Validation Errors").concat("**"));
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
    
   
    
    //-------------------OrderUpdate_tocheckAEE validation method-------------------
    public String OrderUpdate_tocheckAEE(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, NullPointerException, SAXException
    {

        Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
LibraryRtp lib=new LibraryRtp();
List<String> addIssues=new ArrayList();
String Issues="Issues from orderUpdate_AEE:::";
        ResultSet  rs;
        HandleInvalidServicesLibrary HL=new HandleInvalidServicesLibrary();
        int callFound=0,v1=0,v2=0,v3=0,v4=0;
        String accountNumber_ou;
        String billingOrderID;
        // String xmldata1 ="receive_data";
String xmldata2 ="SEND_DATA";
String Time= c.get("BaseTime").toString();
// c.setValue("OrderUpdate","true");
String rscallpresent="true";
Application.showMessage("-------------------------------------------------------------");
Application.showMessage("***********OrderUpdate_Validate_Tocheck AEE Function**************");       
Application.showMessage("-------------------------------------------------------------");
//  lib.findThinktimeOperationRTP(input, c);
//  c.setValue("OrderUpdate","false"); 
HashMap Operation=lib.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
        try
        {
                     //   Statement st =lC. dBConnect(input, c);  
//  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
        	rs=lib.reduceThinkTimeRTP(input, c);
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
                         String senddata = lC.extractXml(rs,xmldata2);           
                     String senddataTrim=lC.RemoveNameSpaces(senddata); 
                         Application.showMessage("SendDataTrim ::: " +senddataTrim );                                              
                         String accountNumber=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value");
                    Application.showMessage("accountNumber is ::"+accountNumber); 
                     if(accountNumber==null)
                       {
                            Application.showMessage("Send Xml billingOrderId is NULL");
                                     continue;
                       }
                     else if(accountNumber.equals(c.getValue("ACC_input")))
           {
                         Application.showMessage("SEND XML is :: \n"+senddata);
                         Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
                         Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);
                         callFound=1;
           }
          else
           {
                        continue;
           }
                    if(callFound==1)
                      {
                            String Service;              
                            if(senddataTrim.contains("COM"))
                                {
                                                         Application.showMessage("SEND XML is :: \n"+senddata);                                             
                                         billingOrderID=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value[5]");
                                         c.setValue("CCentralNum", billingOrderID);
                                         c.setValue("CcentralNum", billingOrderID);
                                         Application.showMessage("CCentral number:::"+billingOrderID);
                                                        if(senddata.contains("Hav Market"))
                                               {
                                                 Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
                                                            Application.showMessage("Service is ::"+Service);                                                           
                                               }
                                            else
                                                        {
                                                             Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
                                                             Application.showMessage("Service is  :: "+Service);
                                                        }
                                                        if(Service!=null || billingOrderID!=null )
                                                        {
                                                              Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim());
                                                                                         Application.showMessage("*******Validation Point 2 :: WebServicall(OrderUpdate)-Service********");
                                                                            Application.showMessage("Validation is Successfull with Service::"+" "+Service);
                                                                            v2=1;
                                                                           c.setValue("Product",Service);
                                                        }
                                                                                        
                                                                    else
                                                                             addIssues.add("Send Xml Service || BillingOrderID is NULL");
                                                                                                                                                   
                                                                        
                                                Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
                                                Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);                                                
                                    break;
                                  }        
                                else if (senddataTrim.contains("ERR")||senddataTrim.contains("ServiceFault") )
                                  {                                                    
                                              addIssues.add("Error in OrderStatus ID");
                                               break;
                                               }
                               else
                               {
                                       continue;
                               }
                            }
      } }
        	}
                      
                         
        
if(callFound * v2==1)
        
{   
        
        Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
}
else
{//Yamini
	 for(int i=0;i<addIssues.size();i++)
	 {
		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	 }
	 c.report(Issues.concat("WebService Call :: setOrderStatus is Failed with Validation Errors").concat("**"));
}
 //    st.close();
rs.close();
}
        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
                        e.printStackTrace();
                        
        }
		return rscallpresent;              

    }
    //--------------------------------storeCCnum-------------------------------------------
    public Statement mysqldbConnect(String Ccnum,String accountNumber) throws ClassNotFoundException, SQLException, IOException, InstantiationException, IllegalAccessException
	{ Statement st = null;
		try{
	
	 Class.forName("com.mysql.jdbc.Driver");
	
	   
	   final String DB_URL = "jdbc:mysql://10.255.159.180:3307/my_sql";

	   //  Database credentials
	   final String USER = "mithun";
	   final String PASS = "Tester123";   
	   String sql;
	   System.out.println("Connecting to Database");
	   Application.showMessage("Connecting to Database");
	      connection = DriverManager.getConnection(DB_URL,USER,PASS);
	      
	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      Application.showMessage("Creating statement...");
	     st = connection.createStatement();
	     
            
             
	     Application.showMessage("Records to be Updated::");
              System.out.println("Records to be Updated::");
              Application.showMessage("update my_sql.userentrynew set cCentralID='"+Ccnum+"'where AccountNumber="+accountNumber);
	      sql = "update my_sql.userentrynew set cCentralID='"+Ccnum+"'where AccountNumber='"+accountNumber+"'";
	     int i= st.executeUpdate(sql);
            System.out.println("Records Updated::" + i);
            Application.showMessage("Records Updated::" + i);
	      st.close();
	      connection.close();
	}
	catch (ClassNotFoundException e) { // 
        Application.showMessage("Driver not found " + e);
    } catch (SQLException e) {
    	Application.showMessage("Connect not possible" + e);
    }
	   	
		return st;
	}
    
    public void storeCCnum(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, SQLException, InterruptedException, InstantiationException, IllegalAccessException
	{
		
		//PreparedStatement ps=  connection.prepareStatement("my_temp_proc(?)");
		String accountNumber=c.getValue("ACC_input");
		String Ccnum = c.getValue("CcentralNum");
		Application.showMessage("AccountNumber::"+accountNumber);
		Application.showMessage("cCentralnumber::"+Ccnum);
		mysqldbConnect(Ccnum, accountNumber);
	}
    
    public void orderUpdate_Validate_IgnoreWorklistRestore(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs; 
	     DBObject obj = null;
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from orderUpdate:::";
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
		        	 c.report(Issues.concat("WebService Call :: setOrderstatus is Failed with Validation Errors").concat("**"));
		          }
	        // st.close();
	         rs.close();
		}
	         
		}
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
	     DBCursor  rs; 
	     DBObject obj = null;
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from orderUpdate:::";
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
					            	 v2=1;
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
		 			            	 v2=1;
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
				            	 v4=1;
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
				            	 v5=1;
				             }
				             else
				             {
				            	 addIssues.add("customerType at Send Xml not Validated as "+customerType);
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
	        	//Yamini
		        	 for(int i=0;i<addIssues.size();i++)
		        	 {
		        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
		        	 }
		        	 c.report(Issues.concat("WebService Call :: setOrderstatus is Failed with Validation Errors").concat("**"));
		            }
	        // st.close();
	         rs.close();
		}	
		}
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 
    }
	
    
  
    
    //----------------------------orderUpdate_Validate_LocationError validation method------------------------------
    public void orderUpdate_Validate_LocationError(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException
    {

        Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
LibraryRtp  lC= new LibraryRtp ();
DBCursor  rs; 
DBObject obj = null;
List<String> addIssues=new ArrayList();
String Issues="Issues from orderUpdate:::";
        int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0;
        // String xmldata1 ="receive_data";
String xmldata2 ="SEND_DATA";

Application.showMessage("-------------------------------------------------------------");
Application.showMessage("***********OrderUpdate_Validate Function**************");       
Application.showMessage("-------------------------------------------------------------");
 
        try
        {
                        Statement st =lC. dBConnect(input, c); 
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
        
                   
                                   
        String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
                   Application.showMessage("accountNumber is ::"+accountNumber_ou); 
                   if(accountNumber_ou==null)
                    {
                          Application.showMessage("Send Xml Account Number is NULL");
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
                                                if(ordSource.equals("OP"))
                                               {
                                                       Application.showMessage("Valid Order Source");
                                               }
                                               
                                                else
                                               {
                                                       addIssues.add("Order source is Invalid");
                                               }
                                               
                                                
                                               String ordStatus= lC.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"customerType\">");
                                               Application.showMessage("ordStatus is ::"+ordStatus); 
                                                if(ordStatus.equals("ERR"))
                                               {
                                                       Application.showMessage("Valid Order Status");
                                               }
                                               
                                                else
                                               {
                                                       addIssues.add("Order Status is Invalid");
                                               }
                                               
                                                String ordType= lC.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"active\">");
                                               Application.showMessage("ordType is ::"+ordType); 
                                                

                                   String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
                                   Application.showMessage("inputChannel is ::"+inputChannel);
                                                                   
                                    
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
                                   
                                   //_______________________________________________________________________//
                                  //
                                  //                                                                 WorkList Validations
                                  //
                                  //_______________________________________________________________________//
                                  
                                   
                                   
                                   //---------------------------Error code validation----------------------------------------//
                                  
                                 
                                   
                                   
                                   
                                    String errorCode= lC.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"errorText\">");
                                   Application.showMessage("errorCode is ::"+errorCode); 
                                    
                                    //For common DB validation
                                      c.put("ErrorCodeOu", errorCode);
                                  
                                 // Error text from excel
                                     // c.put("ErrorCode_Excel",c.getValue("RTPNormalScenariosdS","ErrorCode"));
                                   
                                                   if(errorCode==null)
                                                    {
                                                                    addIssues.add("Send Xml Error Code is NULL");
                                                                    continue;
                                                    }
                                                    else
                                                    {
                                                        
                                                         if(errorCode.equals("XH-ELOC-001"))
                                                                     {
                                                                         Application.showMessage("*******Validation Point 2 :: WebServicall-ErrorCode********");
                                                                         Application.showMessage("Validation is Successfull with Error Code::"+" "+errorCode);
                                                                         v5=1;
                                                                     }
                                                                     else
                                                                     {
                                                                         addIssues.add("Error Code at Send Xml not Validated as "+errorCode);
                                                                     }
                                                    }
                                                  
                                                   
                                                   
                                                   
                                                   //-------------------------Error text validation-----------------------------------------//
                                                  
                                                   
                                                  String errorText= lC.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorSource\">");
                                                   Application.showMessage("Error Text is ::"+errorText); 
                                                   //For Common DB validation
                                                   c.put("ErrorTextOu", errorText);
                                                   
                                                    
                                                    //read from Excel ----
                                                  // c.put("ErrorText_Excel",c.getValue("RTPNormalScenariosdS","ErrorText"));
                                                                  if(errorText==null)
                                                                    {
                                                                                    addIssues.add("Send Xml Error Code is NULL");
                                                                                    continue;
                                                                    }
                                                                    else
                                                                    {
                                                                        
                                                                         if(errorText.equals("Missing service address"))
                                                                                     {
                                                                                         Application.showMessage("*******Validation Point 2 :: WebServicall-Error Text********");
                                                                                         Application.showMessage("Validation is Successfull with Error Text::"+" "+errorText);
                                                                                         v6=1;
                                                                                     }
                                                                                     else
                                                                                     {
                                                                                         addIssues.add("Error Text at Send Xml not Validated as "+errorText);
                                                                                     }
                                                                    }
                                                                  
                                                                   
                                                                   
                                                                //-------------------------Error Source  validation-----------------------------------------//
                                                                  
                                                                   
                                                                  String errorSource= lC.nodeFromKey(senddata,"<value name=\"errorSource\">","</value><value name=\"details\">");
                                                                   Application.showMessage("Error Source is ::"+errorSource); 
                                                                    
                                                                 //For Common DB validation
                                                                   c.put("ErrorSourceOu", errorSource);
                                                                                                                                                   
                                                                    
                                                                    
                                                                    //read from Excel ----
                                                                  // c.put("ErrorSource_Excel",c.getValue("RTPNormalScenariosdS","ErrorSource"));
                                                                   
                                                                                   if(errorSource==null)
                                                                                    {
                                                                                                    addIssues.add("Send Xml Error Source is NULL");
                                                                                                    continue;
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        
                                                                                        if(errorSource.equals("BHS.Fallout"))
                                                                                                     {
                                                                                                         Application.showMessage("*******Validation Point 2 :: WebServicall-Error Source********");
                                                                                                         Application.showMessage("Validation is Successfull with Error Source::"+" "+errorSource);
                                                                                                         v7=1;
                                                                                                     }
                                                                                                     else
                                                                                                     {
                                                                                                         addIssues.add("Error Source at Send Xml not Validated as "+errorSource);
                                                                                                     }
                                                                                    }
                                                                                  
                                                                                   
                                                                                   
                                                                                  String details= lC.nodeFromKey(senddata,"<value name=\"details\">","</value><value name=\"orderType\">");
                                                                                 Application.showMessage("Detail  is ::"+details);
                                                                                  
                                                                                   
                                                                                   
                                    
                                                                   
                    break;
                    }
    }
       
 }
 if(callFound ==1)
 {
        Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
 //   c.setValue("IsDemi", "false");
 }
 else
 {//Yamini
	 for(int i=0;i<addIssues.size();i++)
	 {
		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	 }
	 c.report(Issues.concat("WebService Call :: setOrderstatus is Failed with Validation Errors").concat("**"));
 }
 st.close();
 
//Lp.reportingToExcel(Object , ScriptingContext);
        }   
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
                        e.printStackTrace();
                        return;
        }

    }
    
    //----------------------------orderUpdateFOX_Validate validation method--------------------
    //Sruthi
public String orderUpdate_ValidateforScheduleUpgradedowngrade(Object input, ScriptingContext c,String orderType,String servceTobefind) throws Exception
    
    {

        Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
LibraryRtp  lC= new LibraryRtp ();
DBCursor  rs; 
DBObject obj = null;

        String vService=null;
        int callFound=0,v1=0,v2=0,v3=0,v4=0,status=0;
        String rscallpresent="true";
String xmldata2 ="SEND_DATA";

Application.showMessage("-------------------------------------------------------------");
Application.showMessage("***********setOrderStatus_Validate Function**************");       
Application.showMessage("-------------------------------------------------------------");
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("setOrderStatus"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("setOrderStatus"));
        try
        {
                     //  Statement st =lC. dBConnect(input, c);  
//   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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

        	                                	  if(((BasicBSONObject) obj).getString("REQUEST")==null)
        	                                     {
        	                                         Application.showMessage("Your SEND XML is NULL \n");             
        	                                         String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
        	                                         Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
        	                                     }
        	                                     else
        	                                 
        	                          {
        	                                     String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
        	                                   //  String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
        	                                     Application.showMessage("Your send XML is::\n"+senddata);
        	                                   //  Application.showMessage("RECIEVE XML is ::\n"+recievedata); 

                                   
       // String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
        	                                     String senddataTrim=lC.RemoveNameSpaces(senddata);
        	                                     Application.showMessage("Your send XML Trim  is::\n"+senddataTrim);
        	                                     String accountNumber_ou= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'accountNumber' ]/@value");
        	                                     Application.showMessage("accountNumber is ::"+accountNumber_ou); 
        	                                  
                   if(accountNumber_ou==null)
                    {
                          Application.showMessage("Send Xml billingOrderId is NULL");
                                    continue;
                    }
                  else if(accountNumber_ou.equals(c.getValue("ACC_input")))
        {
                        
                       // Application.showMessage("SEND XML is :: \n"+senddata);
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
              
                    	

                                   String vomInstance= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                       Application.showMessage("vomInstance is ::"+vomInstance); 

                                   
                                    
                                    if(c.getValue("IsDemi").equalsIgnoreCase("true"))
                                   {
                                       
                                    String ordSource= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                         Application.showMessage("ordSource is ::"+ordSource); 
                                    String billingOrderId= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'billingOrderId' ]/@value");
                                      Application.showMessage("billingOrderId is ::"+billingOrderId); 
                                    
                                                   if(billingOrderId==null)
                                                    {
                                                                    c.report("Send Xml billingOrderId is NULL");
                                                                    continue;
                                                    }
                                                    else
                                                    {
                                                        
                                                         if(billingOrderId.equals(c.getValue("CcentralNum")) || billingOrderId.equalsIgnoreCase("null") )
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
                                                  
                                                    String Service= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'SHIPPING' ]/elementData[@name = 'append' ]/@value");
                                                   String service1[]=Service.split("Service =");
                                                   String service2[]=service1[1].split("Hav Market");
                                                       Application.showMessage("Service is ::"+service2[0].trim());
                                                       Application.showMessage("servceTobefind is ::"+servceTobefind.trim());
                                                  
                                                         if(service2[0].trim().equals(servceTobefind.trim()))
                                                                     {
                                                                         Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
                                                                         Application.showMessage("Validation is Successfull with Service::"+" "+service2[0].trim());
                                                                         v4=1;
                                                                         c.setValue("Product",Service);
                                                                     }
                                                                     else
                                                                     {
                                                                         c.report("Service at Send Xml not Validated as "+service2[0]);
                                                                     }
                                                     
                                   
                                    }
                                   else
                                   {v1=1;
                                   
                                   
                                   String Service= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'SHIPPING' ]/elementData[@name = 'append' ]/@value");
                                       Application.showMessage("Service is ::"+Service); 
                                       String service1[]=Service.split("Service =");
                                       String service2[]=service1[1].split("Hav Market");
                                           Application.showMessage("Service is ::"+service2[0].trim());
                                           Application.showMessage("servceTobefind is ::"+servceTobefind.trim());
                                               
                                              if(Service==null)
                                              {
                                                              c.report("Send Xml Service is NULL");
                                                              continue;
                                              }
                                              else
                                              {
                                                  
                                                    Application.showMessage("Service from Input   is ::"+" "+servceTobefind);
                                                   Application.showMessage("Service from Send Xml   is ::"+" "+Service);
                                                   if(service2[0].trim().equals(servceTobefind.trim()))
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
                                              }  }
                                   String inputChannel= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'inputChannel' ]/@value");
                                      Application.showMessage("inputChannel is ::"+inputChannel);
                                   
                                    String ordType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordType' ]/@value");
                                      Application.showMessage("ordType is ::"+ordType);
                                   
                                   if(ordType==null)
                                    {
                                                    c.report("Send Xml ordType is NULL");
                                                    continue;
                                    }
                                    else
                                    {
                                         Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
                                         if(ordType.equalsIgnoreCase(orderType))
                                                     {
                                                         Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                                         Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
                                                         v2=1;
                                                     }
                                                     else
                                                     {
                                                         c.report("lt_fireNumber at Send Xml not Validated as "+ordType);
                                                     }
                                    }  
                                  
                                   
                             String ordStatus= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordStatus' ]/@value");
                               Application.showMessage("ordStatus is ::"+ordStatus);
                               
                               if(ordStatus==null)
                                {
                                                c.report("Send Xml ordStatus is NULL");
                                                continue;
                               }
                                else
                                {
                                     Application.showMessage("ordStatus from Send Xml   is ::"+" "+ordStatus);
                                     if(ordStatus.equals("COM"))
                                                 {
                                                     Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                                     Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                                    status=1;
                                                 }
                                     else if(ordStatus.equals("ERR"))
                                                 {
                                                     Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                                     Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                                     status=1;
                                                     
                                                 }
                                                 else
                                                 {
                                                     c.report("ordStatus at Send Xml not Validated as "+ordStatus);
                                                 }
                                }  
                                   
                                    
                                    String customerType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'customerType' ]/@value");
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
        	                                	        }
 if(v1*callFound*v2*v3*v4*status ==1)
 {
        Application.showMessage("WebService Call :: setOrderstatus_Validate is Success[All validation points are Success]");
 //   c.setValue("IsDemi", "false");
 }
 else
 {
        // c.setValue("IsDemi", "false");
        // c.put("result", "false");
         c.report("WebService Call ::setOrderstatus_Validate is Failed with Validation Errors");
 }
//   st.close();
 rs.close();
 
//Lp.reportingToExcel(Object , ScriptingContext);
         
        }
        }
        catch (SQLException e)
        {
            System.out.println("Connection Failed! Check output console");
                        e.printStackTrace();
                                       }
		return rscallpresent;
        

    }

///Order_update for AddressUpdate
public String orderUpdate_Validate_AddressUpdate(Object input, ScriptingContext c,String orderType,String servceTobefind) throws Exception

{

    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
LibraryRtp  lC= new LibraryRtp ();
DBCursor  rs; 
DBObject obj = null;

    String vService=null;
    int callFound=0,v1=0,v2=0,v3=0,v4=0,status=0;
    String rscallpresent="true";
String xmldata2 ="SEND_DATA";

Application.showMessage("-------------------------------------------------------------");
Application.showMessage("***********setOrderStatus_Validate Function**************");       
Application.showMessage("-------------------------------------------------------------");
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("setOrderStatus"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("setOrderStatus"));
    try
    {
                 //  Statement st =lC. dBConnect(input, c);  
//rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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

    	                                	  if(((BasicBSONObject) obj).getString("REQUEST")==null)
    	                                     {
    	                                         Application.showMessage("Your SEND XML is NULL \n");             
    	                                         String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
    	                                         Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
    	                                     }
    	                                     else
    	                                 
    	                          {
    	                                     String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
    	                                   //  String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
    	                                     Application.showMessage("Your send XML is::\n"+senddata);
    	                                   //  Application.showMessage("RECIEVE XML is ::\n"+recievedata); 

                               
   // String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
    	                                     String senddataTrim=lC.RemoveNameSpaces(senddata);
    	                                     Application.showMessage("Your send XML Trim  is::\n"+senddataTrim);
    	                                     String accountNumber_ou= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'accountNumber' ]/@value");
    	                                     Application.showMessage("accountNumber is ::"+accountNumber_ou); 
    	                                  
               if(accountNumber_ou==null)
                {
                      Application.showMessage("Send Xml billingOrderId is NULL");
                                continue;
                }
              else if(accountNumber_ou.equals(c.getValue("ACC_input")))
    {
                    
                   // Application.showMessage("SEND XML is :: \n"+senddata);
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
          
                	

                               String vomInstance= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                   Application.showMessage("vomInstance is ::"+vomInstance); 

                               
                                
                                if(c.getValue("IsDemi").equalsIgnoreCase("true"))
                               {
                                   
                                String ordSource= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                     Application.showMessage("ordSource is ::"+ordSource); 
                                String billingOrderId= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'billingOrderId' ]/@value");
                                  Application.showMessage("billingOrderId is ::"+billingOrderId); 
                                
                                               if(billingOrderId==null)
                                                {
                                                                c.report("Send Xml billingOrderId is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                    
                                                     if(billingOrderId.equals(c.getValue("CcentralNum")) || billingOrderId.equalsIgnoreCase("null") || !billingOrderId.equalsIgnoreCase("null"))
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
                                              
                                               
                               
                                }
                               else
                            	   
                               {
                            	   v1=1;
                               
                               
                               
                                          }  }
                               String inputChannel= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'inputChannel' ]/@value");
                                  Application.showMessage("inputChannel is ::"+inputChannel);
                               
                                String ordType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordType' ]/@value");
                                  Application.showMessage("ordType is ::"+ordType);
                               
                               if(ordType==null)
                                {
                                                c.report("Send Xml ordType is NULL");
                                                continue;
                                }
                                else
                                {
                                     Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
                                     Application.showMessage("orderType from Send Xml   is ::"+" "+orderType);
                                     if(ordType.equalsIgnoreCase(orderType))
                                                 {
                                                     Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                                     Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
                                                     v2=1;
                                                 }
                                                 else
                                                 {
                                                     c.report("lt_fireNumber at Send Xml not Validated as "+ordType);
                                                 }
                                }  
                              
                               
                         String ordStatus= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordStatus' ]/@value");
                           Application.showMessage("ordStatus is ::"+ordStatus);
                           
                           if(ordStatus==null)
                            {
                                            c.report("Send Xml ordStatus is NULL");
                                            continue;
                           }
                            else
                            {
                                 Application.showMessage("ordStatus from Send Xml   is ::"+" "+ordStatus);
                                 if(ordStatus.equals("COM"))
                                             {
                                                 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                                 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                                status=1;
                                             }
                                 else if(ordStatus.equals("ERR"))
                                             {
                                                 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                                 c.report("Validation with ERROR ordStatus:: "+ordStatus);
                                                 
                                             }
                                 
                                             else
                                             {
                                                 c.report("ordStatus at Send Xml not Validated as "+ordStatus);
                                             }
                            }  
                               
                                
                                String customerType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'customerType' ]/@value");
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
    	                                	        }
if(v1*callFound*v2*v3*status ==1)
{
    Application.showMessage("WebService Call :: setOrderstatus_Validate is Success[All validation points are Success]");
//   c.setValue("IsDemi", "false");
}
else
{
    // c.setValue("IsDemi", "false");
    // c.put("result", "false");
     c.report("WebService Call ::setOrderstatus_Validate is Failed with Validation Errors");
}
//st.close();
rs.close();

//Lp.reportingToExcel(Object , ScriptingContext);
     
    
    }
    catch (SQLException e)
    {
        System.out.println("Connection Failed! Check output console");
                    e.printStackTrace();
                                   }
	return rscallpresent;
    

}
//SetOrderStatus for cvr4Xi
public String orderUpdate_Validate_cvr4xi(Object input, ScriptingContext c,String orderType) throws Exception

{

    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
LibraryRtp  lC= new LibraryRtp ();
DBCursor  rs; 
DBObject obj = null;

    String vService=null;
    int callFound=0,v1=0,v2=0,v3=0,v4=0,status=0;
    String rscallpresent="true";
String xmldata2 ="SEND_DATA";

Application.showMessage("-------------------------------------------------------------");
Application.showMessage("***********setOrderStatus_Validate Function**************");       
Application.showMessage("-------------------------------------------------------------");
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("setOrderStatus"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("setOrderStatus"));
    try
    {
                 //  Statement st =lC. dBConnect(input, c);  
//rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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

    	                                	  if(((BasicBSONObject) obj).getString("REQUEST")==null)
    	                                     {
    	                                         Application.showMessage("Your SEND XML is NULL \n");             
    	                                         String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
    	                                         Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
    	                                     }
    	                                     else
    	                                 
    	                          {
    	                                     String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
    	                                   //  String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
    	                                     Application.showMessage("Your send XML is::\n"+senddata);
    	                                   //  Application.showMessage("RECIEVE XML is ::\n"+recievedata); 

                               
   // String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
    	                                     String senddataTrim=lC.RemoveNameSpaces(senddata);
    	                                     Application.showMessage("Your send XML Trim  is::\n"+senddataTrim);
    	                                     String accountNumber_ou= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'accountNumber' ]/@value");
    	                                     Application.showMessage("accountNumber is ::"+accountNumber_ou); 
    	                                  
               if(accountNumber_ou==null)
                {
                      Application.showMessage("Send Xml billingOrderId is NULL");
                                continue;
                }
              else if(accountNumber_ou.equals(c.getValue("ACC_input")))
    {
                    
                   // Application.showMessage("SEND XML is :: \n"+senddata);
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
          
                	

                               String vomInstance= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                   Application.showMessage("vomInstance is ::"+vomInstance); 

                               
                                
                                if(c.getValue("IsDemi").equalsIgnoreCase("true"))
                               {
                                   
                                String ordSource= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordSource' ]/@value");
                                     Application.showMessage("ordSource is ::"+ordSource); 
                                String billingOrderId= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'billingOrderId' ]/@value");
                                  Application.showMessage("billingOrderId is ::"+billingOrderId); 
                                
                                               if(billingOrderId==null)
                                                {
                                                                c.report("Send Xml billingOrderId is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                    
                                                     if(billingOrderId.equals(c.getValue("CcentralNum")) || billingOrderId.equalsIgnoreCase("null") || !billingOrderId.equalsIgnoreCase("null"))
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
                                              
                                               
                               
                                }
                               else
                            	   
                               {
                            	   v1=1;
                               
                               
                               
                                          }  }
                               String inputChannel= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'inputChannel' ]/@value");
                                  Application.showMessage("inputChannel is ::"+inputChannel);
                               
                                String ordType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordType' ]/@value");
                                  Application.showMessage("ordType is ::"+ordType);
                               
                               if(ordType==null)
                                {
                                                c.report("Send Xml ordType is NULL");
                                                continue;
                                }
                                else
                                {
                                     Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
                                     Application.showMessage("orderType from Send Xml   is ::"+" "+orderType);
                                     if(ordType.equalsIgnoreCase(orderType))
                                                 {
                                                     Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                                     Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
                                                     v2=1;
                                                 }
                                                 else
                                                 {
                                                     c.report("lt_fireNumber at Send Xml not Validated as "+ordType);
                                                 }
                                }  
                              
                               
                         String ordStatus= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'ordStatus' ]/@value");
                           Application.showMessage("ordStatus is ::"+ordStatus);
                           
                           if(ordStatus==null)
                            {
                                            c.report("Send Xml ordStatus is NULL");
                                            continue;
                           }
                            else
                            {
                                 Application.showMessage("ordStatus from Send Xml   is ::"+" "+ordStatus);
                                 if(ordStatus.equals("COM"))
                                             {
                                                 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                                 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                                status=1;
                                             }
                                 else if(ordStatus.equals("ERR"))
                                             {
                                                 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                                 c.report("Validation with ERROR ordStatus:: "+ordStatus);
                                                 
                                             }
                                 
                                             else
                                             {
                                                 c.report("ordStatus at Send Xml not Validated as "+ordStatus);
                                             }
                            }  
                               
                                
                                String customerType= lC.GetValueByXPath(senddataTrim, "//orderElement[@type = 'HEADER' ]/elementData[@name = 'customerType' ]/@value");
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
    	                                	        }
if(v1*callFound*v2*v3*status ==1)
{
    Application.showMessage("WebService Call :: setOrderstatus_Validate is Success[All validation points are Success]");
//   c.setValue("IsDemi", "false");
}
else
{
   
     c.report("WebService Call ::setOrderstatus_Validate is Failed with Validation Errors");
}
//st.close();
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