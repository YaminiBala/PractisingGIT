
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.xpath.XPathExpressionException;

import org.bson.BasicBSONObject;
import org.json.JSONException;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
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

public class XHOM_HomeStream_Validations 
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
//----------------------------------uPDATEaCCOUNT vALIDATION---------------------------------
    
    
    public void UpdateAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException, Exception
   
    {

        Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]

        
        int callFound=0,v1=0;
        
DBCursor  rs;
DBObject obj = null;
String rscallpresent="true";
Application.showMessage("----------------------------------------------------");
Application.showMessage("*****Update Account_Validate _Validate Function******");       
Application.showMessage("-----------------------------------------------------");
HashMap Operation=XH.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("updateAccount"));
Application.showMessage("OPERATIONValidation is "+(String) Operation.get("updateAccount"));
 
        try
        {
                        //Statement st =lC. dBConnect(input, c); 
 //rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/restoreAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
        	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
        	if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
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
         
         
             
             String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
             //REQUEST
             Application.showMessage("Your Send XML is::\n"+senddata);
         }
         else if(((BasicBSONObject) obj).getString("REQUEST")==null)
         {
           
             String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
             Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
         }
         else
         {
   
                  
        	 String senddata =((BasicBSONObject) obj).getString("REQUEST");
             String recievedata=((BasicBSONObject) obj).getString("RESPONSE");  
             Application.showMessage("Recieve XML is::  \n"+ recievedata);
             Application.showMessage("SEND XML is :: \n"+senddata);
    	 String id= XH.extractingValueFromJSONObject(senddata,"accountId", input, c); 
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
                    	Application.showMessage("entering into Callfound");
                        String fname=  XH.extractingValueFromJSONObject(senddata,"firstName", input, c); 
                        Application.showMessage("fname is ::"+fname);
                         
                        String lname= XH.extractingValueFromJSONObject(senddata,"lastName", input, c); 
                        Application.showMessage("lname is ::"+lname);
                                                     
                        String PhoneNumber= XH.extractingValueFromJSONObject(senddata,"phoneNumber", input, c); 
                        Application.showMessage("PhoneNumber is ::"+PhoneNumber);           
                        
                        String address1= XH.extractingValueFromJSONObject(senddata,"address1", input, c); 
                        Application.showMessage("address1 is ::"+address1);
                        
                                                   
                        String city= XH.extractingValueFromJSONObject(senddata,"city", input, c); 
                        Application.showMessage("city is ::"+city); 
                        
                        String Province= XH.extractingValueFromJSONObject(senddata,"province", input, c); 
                        Application.showMessage("Province is ::"+Province); 
                        
                       
                        String cCentral= XH.extractingValueFromJSONObject(senddata,"centralStationAccountNumber", input, c); 
                        Application.showMessage("cCentral Number is ::"+cCentral); 
                        
                        
                        
                        String PortalSSoID= XH.extractingValueFromJSONObject(senddata,"portalUserSSOId", input, c); 
                        Application.showMessage("PortalSSoID is ::"+PortalSSoID); 
                        
                        String Product= XH.extractingValueFromJSONObject(senddata,"product", input, c); 
                        Application.showMessage("Product is ::"+Product); 
                        
                        String group= XH.extractingValueFromJSONArray(senddata,"group", input, c); 
                        Application.showMessage("group is ::"+group); 
                        v1=1;
   break;
                    }
                    
                    
     }
 }
                }
 if(callFound*v1==1)
 {
        Application.showMessage("WebService Call :: updateAccount call is Success[All validation points are Success]");
 }
 else
 {
         c.put("result", "false");
         c.report("WebService Call ::updateAccount is Failed with Validation Errors");
 }
               
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
    
                

        //------------Methods

       
    
    //---------getAccount New Method--------------
    
    public String CLSgetAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException, JSONException 
    {
                    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
         DBCursor  rs;
         DBObject obj = null;
                    int callFound=0,v1=0;
                    String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String rscallpresent="true";
         String Time= c.get("BaseTime").toString();
         Application.showMessage("-----------------------------------------------------");
         Application.showMessage("*****CLS_getAccount_Validate _Validate Function******");       
         Application.showMessage("-----------------------------------------------------");
         HashMap Operation=XH.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccount"));
         Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccount"));
         List<String> addIssues=new ArrayList();
 	    String Issues="Issues from NewgetAccount:::";
             
                    try
                    {
                                  //  Statement st =lC. dBConnect(input, c); 
            // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                    	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
                    	if(rs == null)
                    	{
                    		rscallpresent = "false";
                    		
                    	
                    		
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
                         
                         String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                         //REQUEST
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
                    	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                         
                     String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
                     Application.showMessage("Your Send XML is::\n"+senddata);
                     Application.showMessage("Receive XML is ::\n"+recievedata);
                     String id= senddata.trim();
                                Application.showMessage("id is ::"+id);               
                                if(id.equals(c.getValue("ACC_input")))
                    {
                                 //   Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                 //   Application.showMessage("SEND XML is :: \n"+senddata);                                   
                                    Application.showMessage("*******Validation Point 1 :: WebServicall-CLS-Get Account Call********");
                                    Application.showMessage("Validation is Successfull with Input Account Number"+id);
                                    callFound=1;
                    }
                    else
                    {
                                    continue;
                    }
                    
                                if(callFound==1)
                                {
                          
                                    String errorCode_getAcc=XH.extractingValueFromJSONObject(recievedata,"uceError", input, c); 
                                               // String errorCode_getAcc= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
                                                Application.showMessage("errorCode is ::"+errorCode_getAcc);
                                                
                                                 if(errorCode_getAcc==null)
                                                 {
                                                                addIssues.add("Recieve Xml Account Number is NULL");
                                                                continue;
                                                 }
                                                else
                                                 {
                                                     Application.showMessage("Error code from Send Xml   is ::"+" "+errorCode_getAcc);
                                                     if(errorCode_getAcc.equalsIgnoreCase("UCE-15101"))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
                                                                     Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
                                                                     v1=1;
                                                                 }
                                                                 
																 else if(errorCode_getAcc.equalsIgnoreCase("null"))
                                                                 {
                                                                	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
                                                                     Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
                                                                     v1=1;
                                                                 }
																 else
                                                                 {
                                                                     addIssues.add("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
                                                                 }
                                                }                  
                                               String errorMessage_getAcc= XH.extractingValueFromJSONObject(recievedata,"uceErrorMessage", input, c); 
                                                 //String errorMessage_getAcc= lC.nodeFromKey(recievedata,"<errorMessage xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorMessage>");
                                                Application.showMessage("errorMessage is::"+errorMessage_getAcc); 

                                               //Application.showMessage(v1);
                                break;
                                }
                 }
                              }
                            }
                              
             if(v1*callFound==1)
             {
            	 c.put("NewgetAcc","no");
                    Application.showMessage("WebService Call :: Get Account is Success[All validation points are Success]");
             }
            
             else
	         {//Yamini
            	 c.put("result", "false");
            	 if(callFound!=1)
            	 {
            		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
                     
            	 }
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.put("NewgetAcc",Issues.concat("WebService Call :: getAccount_Validate is Failed with Validation Errors").concat("**"));
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
//---------------New Method-Resultmap ExistingAccounts.
    
    public void returnExistingAcc(Object input,ScriptingContext c,String ExistingService)
    {
LibraryRtp lC=new LibraryRtp();
Application.showMessage("Existing service coming to ResultMap is"+ExistingService);
		  Map<String, String> ResultMap = lC.splitter(ExistingService);
		     c.setValue("ExistingGroup",ResultMap.get("group"));
		     c.setValue("ExistingService", ResultMap.get("service"));
		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
		     Application.showMessage("Existing gri "+ c.getValue("ExistingGroup"));
		     Application.showMessage("Existing gri "+c.getValue("ExistingService"));
		     Application.showMessage("Existing gri "+c.getValue("IsMultiExist"));
    }
    
    public void returnNewAcc(Object input,ScriptingContext c,String NewService)
    {
    	LibraryRtp lC=new LibraryRtp();
		  Map<String, String> ResultMap = lC.splitter(NewService);
		     c.setValue("ExistingGroup",ResultMap.get("group"));
		     c.setValue("ExistingService", ResultMap.get("service"));
		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
    }
    
   //------------Existing getAccounts
    
    public String getAccoutCos_Validate(Object input, ScriptingContext c/*String suspendFlag*/) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, JSONException
	{
		
		 // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
         DBObject obj = null;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from getAccountCOSValidate:::";
String rscallpresent="true";
	   //  c.setValue("getAccount","true");
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****GetAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     
	       
			     
			     Application.showMessage("NewGroup::"+c.get("NewGrp"));
			     Application.showMessage("NewService::"+ c.get("NewSrv"));
			     Application.showMessage("ExistingGroup::"+c.get("ExistingGrp"));
			     Application.showMessage("ExistingService::"+c.get("ExistingSrv"));
			     Application.showMessage("Have both Group and Service in Existing::"+c.get("IsMulti"));
                 
			     Application.showMessage("Have both Group and Service in New:"+c.get("IsMultiNew"));
		  
			      
			     HashMap Operation=XH.findingoperations(input, c);
			     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccount"));
			     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccount"));
	     
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
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
                 
                 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
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
            	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);
             String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
             Application.showMessage("Your Recieve XML is::\n"+senddata);
             Application.showMessage("RECIEVE XML is ::\n"+recievedata);
            String id=senddata.trim(); 
		            Application.showMessage("id is ::"+id);
		            Application.showMessage("id from Input is ::"+c.getValue("ACC_input"));
		            
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	//Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	//Application.showMessage("SEND XML is :: \n"+senddata);
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
		            	
	            	String centralStationAccountNumber= XH.extractingValueFromJSONObject(recievedata,"centralStationAccountNumber", input, c);
	            	String Product= XH.extractingValueFromJSONObject(recievedata,"product", input, c);
	            	 String status_getAcc= XH.extractingValueFromJSONObject(recievedata, "status", input, c);
	            			 c.put("getStatus",status_getAcc);
		 	             Application.showMessage("GetAccount Status is::"+status_getAcc);       
		                if(c.getValue("IsDemi").equalsIgnoreCase("true"))
		 	            {
		                	 if(Product ==null)
				                {
		                		 addIssues.add("Product field from getAccount is NULL");
					            //continue;
				                }
				                else
				                {
				            	    Application.showMessage(" Product from Recieve Xml  from GetAccount is ::"+" "+ Product);
				            	    if( Product.trim().equalsIgnoreCase("CONVERGE"))
					                 {
					            	 Application.showMessage("*******Validation Point 2 :: Product********");
					            	 Application.showMessage("Validation is Successfull with Product::"+" "+Product);
					            	 v1=1;
					                 }
					                else
					               {
					                	addIssues.add("Product at Recieve Xml not Validated as "+Product+"For Demi services");
					               }
				                }
			 	            
					        
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
				                	addIssues.add("centralStationAccountNumber at Recieve Xml not Validated as "+centralStationAccountNumber+"For Demi services");
				               }
			                }
		 	            }
		                else
		                {
		                	
		                	if(Product ==null)
			                {
		                		addIssues.add("Product field from getAccount is NULL");
				            //continue;
			                }
			                else
			                {
			            	    Application.showMessage(" Product from Recieve Xml  from GetAccount is ::"+" "+ Product);
			            	    if( Product.trim().equalsIgnoreCase("INSIGHT"))
				                 {
				            	 Application.showMessage("*******Validation Point 2 :: Product********");
				            	 Application.showMessage("Validation is Successfull with Product::"+" "+Product);
				            	 v1=1;
				                 }
				                else
				               {
				                	addIssues.add("Product at Recieve Xml not Validated as "+Product+"For Demi services");
				               }
			                }
		                /*	if(centralStationAccountNumber.trim().equals("null"))
			                 {
			            	 Application.showMessage("*******Validation Point 3 :: centralStationAccountNumber********");
			            	 Application.showMessage("Validation is Successfull with centralStationAccountNumber::"+" "+centralStationAccountNumber);
			            	 v2=1;
			                 }
			                else
			               {
			            	 addIssues.add("centralStationAccountNumber at Recieve Xml not Validated as "+centralStationAccountNumber+"For Insight services");
			               }	*/
		                	v2=1;
		                }
		            	
		            	 	 String extractinggroup=XH.extractingValueFromJSONArray(recievedata, "group", input, c); 
		            	 	Application.showMessage("extractinggroup"+extractinggroup);
		            		 String remBracketsgroup=extractinggroup.replace("[", "").replace("]", "");
		            		 String[] groupValues=remBracketsgroup.split(",");
		            		 Application.showMessage("groupValues"+groupValues.length);
		            		 Application.showMessage("Is multi Exist"+c.getValue("IsMultiExist"));
		            		 if(c.getValue("IsMultiExist").equals("true"))
			            	 {
		            		 for(int i=0;i<3;i++)
		            		 {
		            			 if(groupValues.length!=3)
		            			 {
		            				 addIssues.add("Validation is not successfull with Group field:getAccount");
		            				 break;
		            			 }
		            			 
		            		 String group1=groupValues[i].replace("\"","") ;
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
			            	    if(c.getValue("IsDemi").equalsIgnoreCase("true"))
			            	    {
			            	    if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equals(c.getValue("ExistingService")) || group1.trim().equals("XfinityEvents"))
				                 {
				            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
				            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
				            	 v3=1;
				                 }
				                else
				               {
				                	v3=0;
				                	addIssues.add("group1 at Recieve Xml not Validated as "+group1);
				               }
			            	    }
			            	    else
			            	    {
			            	    	 if(c.getValue("IsDemi").equalsIgnoreCase("true"))
					            	    {
			            	    		 if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equals(c.getValue("ExistingService")) || group1.trim().equalsIgnoreCase("XfinityEvents-Insight"))
						                 {
						            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
						            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
						            	 v3=1;
						                 }
						                else
						               {
						                	v3=0;
						                	addIssues.add("group1 at Recieve Xml not Validated as "+group1);
						               }	
					            	    }
			            	    	 else
			            	    	 {
			            	    		 if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equals(c.getValue("ExistingService")) || group1.trim().equalsIgnoreCase("XfinityEvents-Insight"))
						                 {
						            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
						            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
						            	 v3=1;
						                 }
						                else
						               {
						                	v3=0;
						                	addIssues.add("group1 at Recieve Xml not Validated as "+group1);
						               } 
			            	    	 }
			            	    }
			                }
			                
			                
		            		 }
			                
			                
		            	}
		            	 
		            	 else
		            	 {
		            		 for(int i=0;i<2;i++)
		            		 {
		            			 
		            			 if(groupValues.length!=2)
		            			 {
		            				 addIssues.add("Validation is not successfull with Group field:getAccount");
		            				 break;
		            			 }
		            		 String group1=groupValues[i].replace("\"", "") ;
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
			            	    if(c.getValue("IsDemi").equalsIgnoreCase("true"))
			            	    {
			            	    if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equalsIgnoreCase("XfinityEvents"))
				                 {
				            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
				            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
				            	 v3=1;
				                 }
				                else
				               {
				                	v3=0;
				                	addIssues.add("group1 at Recieve Xml not Validated as "+group1);
				               }
			            	    }
			            	    else
			            	    {
			            	    	 if(group1.trim().equals(c.getValue("ExistingGroup")) || group1.trim().equalsIgnoreCase("XfinityEvents-Insight"))
					                 {
					            	 Application.showMessage("*******Validation Point 4 :: GroupTier********");
					            	 Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
					            	 v3=1;
					                 }
					                else
					               {
					                	v3=0;
					                	addIssues.add("group1 at Recieve Xml not Validated as "+group1);
					               }
			            	    }
			                }
			                
			                
		            		 }
		            		 
		            	 }
			            String accountId_getAcc= XH.extractingValueFromJSONObject(recievedata, "accountId", input, c);
			            		Application.showMessage("accountId is::"+accountId_getAcc); 
			             if(accountId_getAcc==null)
			             {
				            addIssues.add("Send Xml Account Number is NULL");
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
				            	 addIssues.add("Account Number at Send Xml not Validated as "+accountId_getAcc);
				             }
			            }
			             
			                String isSuspend= XH.extractingValueFromJSONObject(recievedata, "accountId", input, c);
			            	   Application.showMessage("isSuspend is::"+isSuspend); 
				             if(isSuspend==null)
				             {
				            	 addIssues.add("Send Xml isSuspend is NULL");
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
					            	 addIssues.add("Account Number at Send Xml not Validated as "+accountId_getAcc);
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
	         	c.put("getAccountExistingIssue", "no");
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
	        	// addIssues.add(Issues.concat("WebService Call :: getaccount_Validate is Failed with Validation Errors").concat("**"));
	        	 c.put("getAccountExistingIssue", Issues.concat("WebService Call :: getaccount_Validate is Failed with Validation Errors").concat("**"));
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

    
    //-------addAccount------------
    public String CLS_createAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, NullPointerException, SAXException, JSONException 
    {
                    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
                    LibraryRtp  lC= new LibraryRtp ();
                    DBCursor  rs;
                    DBObject obj = null;
                    int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
                    String xmldata1 ="receive_data";
                    String xmldata2 ="SEND_DATA";
                    String rscallpresent="true";
                    int payload=0;
                    int groupEvents=0;
                    int serviceEvents=0;
                    String[] service=null;
         Application.showMessage("---------------------------------------------------------");
         Application.showMessage("***********CLS_addAccount_Validate Function***********");       
         Application.showMessage("---------------------------------------------------------");
         HashMap Operation=XH.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClScreateAccount"));
         Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClScreateAccount"));

         List<String> addIssues=new ArrayList();
 	    String Issues="Issues from addAccount:::";

                    try
                    {
                                    
                                   // Statement st =lC. dBConnect(input, c); 
             //rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/addAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
           //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/addAccount'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                    	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
                    	if(rs == null)
                    	{
                    		rscallpresent = "false";
                    		
                    	
                    		
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
                         
                         String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                         //REQUEST
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
                    	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                         
                     String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
                     String senddataPayLoad =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
                     
                     Application.showMessage("Your Send XML is::\n"+senddata);
                     Application.showMessage("RECIEVE XML is ::\n"+recievedata);
                                                String accountId_ucontrolsrv =senddata.trim();
                                                Application.showMessage("accountNumber is ::"+accountId_ucontrolsrv);     
                                                if(accountId_ucontrolsrv.equals(c.getValue("ACC_input")))
                                    {
                                                   
                                                    callFound=1;
                                    }
                                                else
                                                {
                                                    continue;
                                                }
                                                if(callFound==1)
                                {
                               

                                              
                                                   String errorCode_createAcc= XH.extractingValueFromJSONObject(recievedata,"errorCode", input, c);
                                	               Application.showMessage("errorCode is::"+errorCode_createAcc);
                                               
                                              
                                                     Application.showMessage("errorCode_createAcc from Send Xml  is ::"+" "+errorCode_createAcc);
                                                     if(errorCode_createAcc==null || errorCode_createAcc.equalsIgnoreCase("null") )
                                                                 {
                                                                     Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
                                                                     Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
                                                                     v1=1;
                                                                 }
                                                     else if(errorCode_createAcc.equalsIgnoreCase("UCE-15103"))
                                                     {
                                                    	 Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
                                                         Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
                                                         v1=1;
                                                     }
                                                     else if(errorCode_createAcc.equalsIgnoreCase("Failed to create user Entry"))
                                                     {
                                                    	 Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
                                                         Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
                                                         v1=1;
                                                     }
                                                                 else
                                                                 {
                                                                     addIssues.add("errorCode_createAcc at Send Xml not Validated as "+errorCode_createAcc);
                                                                 }
                                                
                                               
                                                
                                                String accountId_createAcc= XH.extractingValueFromJSONObject(recievedata,"accountId", input, c);
                                               Application.showMessage("accountId is::"+accountId_createAcc); 
                                               Application.showMessage("senddataPayLoad is::"+senddataPayLoad); 
                                               
                                              payload=XH.ValidatingFields(input, c, "addAccount", senddataPayLoad);
                                              
                                              //  group Validations
                                              
                                              String[] group= XH.extractingValueFromJSONArray(senddataPayLoad,"group", input, c).replace("[","").replace("]", "").replace("\"","").split(",");
                                             for(int i=0;i<group.length;i++)
                                             {
                                            	 Application.showMessage("Xfinity events group is"+group[i]);
                                            	 if(c.get("XML_ScenarioName").toString().equalsIgnoreCase("Demi"))
                                            	 {
                                            	if( group[i].equalsIgnoreCase("XfinityEvents"))
                                            			{
                                            		groupEvents=1;
                                            		break;
                                            			}
                                            	 }
                                            	 else{
                                            		 if( group[i].equalsIgnoreCase("XfinityEvents-Insight"))
                                         			{
                                         		groupEvents=1;
                                         		break;
                                         			}
                                            	 }
                                             }
                                             
                                              
                                          if(c.getValue("sSERVICEorCURRENTSERVICE").contains("+"))
                                        		  {
                                        	  Map<String, String> ResultMap = lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
                             			     c.setValue("ExistingGroup",ResultMap.get("group"));
                             			     c.setValue("ExistingService", ResultMap.get("service"));
                             			
                                        	 /* Application.showMessage("Current Service"+c.getValue("sSERVICEorCURRENTSERVICE"));
                                        	 
                                        	   service=c.getValue("sSERVICEorCURRENTSERVICE").split("//+");
                                        	   Application.showMessage("Current Service1"+service[0]);
                                         	  Application.showMessage("Current Service3"+service[1]);*/
                                          	
                                        	   for(int j=0;j<group.length;j++)
                                            	 {
                                        		   
                                            	if(  c.getValue("ExistingGroup").equalsIgnoreCase(group[j]) ||  c.getValue("ExistingService").equalsIgnoreCase(group[j]))
                                            			{
                                            		 	serviceEvents=1;
                                            		break;
                                            			}
                                            	 }
                                        		  }
                                          else
                                          {
                                        	   String services = c.getValue("sSERVICEorCURRENTSERVICE").trim(); 
                                        	  for(int j=0;j<group.length;j++)
                                          	 {
                                          	if( services.equalsIgnoreCase(group[j]))
                                          			{
                                          		  serviceEvents=1;
                                          		break;
                                          			}
                                          	 }
                                          }
                                          
                                          
                                     	 
                                        
                                        	 
                                              
                                        if(groupEvents==0)  
                                        {
                                        	addIssues.add("Validation is not sucessful with xfinityevents in group section");
                                        }
                                        if(serviceEvents==0)  
                                        {
                                        	addIssues.add("Validation is not sucessful with Services in group section");
                                            
                                        }
                                               
                                               
                                               
                                                        

                                               
                                break;
                                }
                 }
                              }
                            }
             
             
             if(v1*callFound*payload*groupEvents*serviceEvents ==1)
             {
                    Application.showMessage("WebService Call :: addAccount is Success[All validation points are Success]");
             }
             else
             {
                     c.put("result", "false");
                    //Yamini
        	        	 for(int i=0;i<addIssues.size();i++)
        	        	 {
        	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
        	        	 }
        	        	 c.report(Issues.concat("WebService Call :: AddGroup_Validate is Failed with Validation Errors").concat("**"));
        	         
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
    
    //------addAccount Error----------------
    
    
    public String CLS_createAccount_ErrorValidate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, NullPointerException, SAXException, JSONException 
    {
                    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
                    LibraryRtp  lC= new LibraryRtp ();
                    DBCursor  rs;
                    DBObject obj = null;
                    int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
                    String xmldata1 ="receive_data";
                    String xmldata2 ="SEND_DATA";
                    String rscallpresent="true";
                    int payload=0;
                    int groupEvents=0;
                    int serviceEvents=0;
                    String[] service=null;
         Application.showMessage("---------------------------------------------------------");
         Application.showMessage("***********CLS_addAccount_Validate Function***********");       
         Application.showMessage("---------------------------------------------------------");
         HashMap Operation=XH.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClScreateAccount"));
         Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClScreateAccount"));

         List<String> addIssues=new ArrayList();
 	    String Issues="Issues from addAccount:::";

                    try
                    {
                                    
                                   // Statement st =lC. dBConnect(input, c); 
             //rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/addAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
           //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/addAccount'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                    	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
                    	if(rs == null)
                    	{
                    		rscallpresent = "false";
                    		
                    	
                    		
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
                         
                         String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                         //REQUEST
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
                    	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                         
                     String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
                     String senddataPayLoad =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
                     
                     Application.showMessage("Your Send XML is::\n"+senddata);
                     Application.showMessage("RECIEVE XML is ::\n"+recievedata);
                                                String accountId_ucontrolsrv =senddata.trim();
                                                Application.showMessage("accountNumber is ::"+accountId_ucontrolsrv);     
                                                if(accountId_ucontrolsrv.equals(c.getValue("ACC_input")))
                                    {
                                                   
                                                    callFound=1;
                                    }
                                                else
                                                {
                                                    continue;
                                                }
                                                if(callFound==1)
                                {
                               

                                              
                                                   String errorCode_createAcc= XH.extractingValueFromJSONObject(recievedata,"errorCode", input, c);
                                	               Application.showMessage("errorCode is::"+errorCode_createAcc);
                                               
                                              
                                                     Application.showMessage("errorCode_createAcc from Send Xml  is ::"+" "+errorCode_createAcc);
                                                     if(errorCode_createAcc==null || errorCode_createAcc.equalsIgnoreCase("null") )
                                                                 {
                                                                     Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
                                                                     Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
                                                                     v1=1;
                                                                 }
                                                     else if(errorCode_createAcc.equalsIgnoreCase("UCE-15122")  || errorCode_createAcc.equalsIgnoreCase("UCE-15103"))
                                                     {
                                                    	 Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
                                                         Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
                                                         v1=1;
                                                     }
                                                                 else
                                                                 {
                                                                     addIssues.add("errorCode_createAcc at Send Xml not Validated as "+errorCode_createAcc);
                                                                 }
                                                
                                               
                                                
                                                
                                              
                                             
                                          
                                     	 
                                        
                                        	 
                                     
                                               
                                               
                                                        

                                               
                                break;
                                }
                 }
                              }
                            }
             
             
             if(v1*callFound ==1)
             {
            	 c.put("addAccountErrorIssues", "no");
                    Application.showMessage("WebService Call :: addAccount is Success[All validation points are Success]");
             }
             else
             {
                     c.put("result", "false");
                    //Yamini
        	        	 for(int i=0;i<addIssues.size();i++)
        	        	 {
        	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
        	        	 }
        	        	 c.put("addAccountErrorIssues",Issues.concat("WebService Call :: addAccount_Validate is Failed with Validation Errors").concat("**"));
        	         
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
    
    //-----------Suspend Account----------
    
    public String SuspendAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, JSONException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****SuspendAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from SuspendAccount:::";
	     HashMap Operation=XH.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_suspendAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_suspendAccount"));
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/suspendAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=XH.XHOM_reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
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
             
             String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
             //REQUEST
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
        	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
             
         String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
         Application.showMessage("Your Recieve XML is::\n"+senddata);
         Application.showMessage("RECIEVE XML is ::\n"+recievedata);    
		         		           
		            String id= senddata.trim();
		            Application.showMessage("id is ::"+id);
		            
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	//Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	//Application.showMessage("SEND XML is :: \n"+senddata);
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
		      
		            	String errorCode= XH.extractingValueFromJSONObject(recievedata,"errorCode", input, c);
            	          Application.showMessage("errorCode is ::"+errorCode);
			           
			            
			           
			            if(errorCode==null)
			            {
				            addIssues.add("Send Xml errorCode is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+errorCode);
			            	 if(errorCode.equals("null"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
				            	 v1=1;
				            	 String status= XH.extractingValueFromJSONObject(recievedata,"status", input, c);
						            Application.showMessage("status is::"+status); 
				             }
			            	 else  if(errorCode.equals("UCE-15113"))
			            	 {
			            		 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
				            	 v1=1;
			            		 String errorMessage= XH.extractingValueFromJSONObject(recievedata,"errorMessage", input, c);
						            Application.showMessage("errorMessage is::"+errorMessage); 
			            	 }
				             else
				             {
				            	 addIssues.add("errorCode at Send Xml not Validated as "+errorCode);
				             }
			            }		

			            
			            
		               
		            break;
		            }
		            
		                        
	             }
	         }
                }
	         if(v1 ==1)
	         {
	        	 c.put("suspendAccountIssues", "no");
	         	Application.showMessage("WebService Call :: Suspend is Success[All validation points are Success]");
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
	        	 c.put("suspendAccountIssues", Issues.concat("WebService Call :: Suspend_Validate is Failed with Validation Errors").concat("**"));
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
    //-----------deactivate Account------------
    
    public void deactivateAccount_Validate_CLS(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, JSONException
	{
		
		 // Think time in JVM [waits for 10 secs here]
	     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
	     LibraryRtp lr=new LibraryRtp();
	     DBCursor  rs;
         DBObject obj = null;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from deactivateAccount:::";
	   //  c.setValue("deactivateAccount","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("******deactivateAccount_Validate Function********");       
	     Application.showMessage("-------------------------------------------------");
	 //    lr.findThinktimeOperationRTP(input, c);
	 //    c.setValue("deactivateAccount","false"); 
	     HashMap Operation=XH.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("deactivateAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("deactivateAccount"));

	     
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/deactivateAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=XH.XHOM_reduceThinkTimeRTP(input, c);
			while (rs.hasNext() )
            {
           obj=rs.next();
              if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
              {
     rowmsg=((BasicBSONObject) obj).getString("_id");
     if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
     {
     
         Application.showMessage("Your Recieve XML is NULL \n");
         
         String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
         //REQUEST
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
    	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
         
     String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
     Application.showMessage("Your Recieve XML is::\n"+senddata);
     Application.showMessage("RECIEVE XML is ::\n"+recievedata);
                        Application.showMessage("senddata is ::"+senddata);
                        Application.showMessage("recievedata is ::"+recievedata);
		         		           
		            String id= senddata.trim();
		            Application.showMessage("id is ::"+id);
		            
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-deactivateAccount********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		            	 String errorCode_createAcc= XH.extractingValueFromJSONObject(recievedata,"errorCode", input, c);
      	               Application.showMessage("errorCode is::"+errorCode_createAcc);
                     
                     if(errorCode_createAcc==null)
                      {
                    	 Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
                         Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
                         v1=1;
                                     
                      }
                      else
                      {
                           Application.showMessage("errorCode_createAcc from Send Xml  is ::"+" "+errorCode_createAcc);
                           if(errorCode_createAcc.equals("null"))
                                       {
                                           Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
                                           Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
                                           v1=1;
                                       }
                                       else
                                       {
                                           addIssues.add("errorCode_createAcc at Send Xml not Validated as "+errorCode_createAcc);
                                       }
                      }  
                     
                      
                      String accountId_createAcc= XH.extractingValueFromJSONObject(recievedata,"accountId", input, c);
                     Application.showMessage("accountId is::"+accountId_createAcc); 
                              

                
		      
		            		
			           
		               
		            break;
		            }
		            
		                        
	             }
	         }
            }
	         if(callFound*v1 ==1)
	         {
	        	 c.put("deactivateAccountIssues", "no");
	         	Application.showMessage("WebService Call :: deactivateAccount is Success[All validation points are Success]");
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
	        	 c.put("deactivateAccountIssues",Issues.concat("WebService Call :: deactivateAccount_Validate is Failed with Validation Errors").concat("**"));
	         }

	      //   st.close();
	         rs.close();
		
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
    
    
    //-----check----------------
    
    
    public void check(Object input,ScriptingContext c)
    {
    	DBCursor retrieveData ;		
	    MongoClient mongodb;
	    DBCollection coll;
	    DB db = null;
	    input="Fri Oct 07 16:29:49 IST 2016";
	    Date date = new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000L);
	    c.put("BaseTime", date);
	    DBObject obj = null;
	    DBObject query = null;
	    String path1="https://homesecurity-acct-qa03.codebig2.net/rest/9992059341814";
	   
    	String path="{https://homesecurity-acct-qa03.codebig2.net/rest/8720101010092417/group/150}";
    	String idStr = path.replaceAll("[{}]", "").substring(path.lastIndexOf('/'));
    	Application.showMessage("Id String is"+XH.extractAccFromURL(path, input, c));
    	
    	mongodb=XH.MongoDBConnect(input, c);
    	db= mongodb.getDB("XHOMQA1");  				 
		coll = db.getCollection("XHOM_MESSAGELOGGING");
		     
      //  query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is("8720101017128900").get(),new QueryBuilder().start().put("OPERATION").is("getAccount").get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals("2016-10-07T02:15:10.095Z").get()).get();
		 query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is("8720101017128900").get(),new QueryBuilder().start().put("OPERATION").is("getAccount").get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
	          BasicDBObject fields = new BasicDBObject();		     
	    fields.put("SENDTIME",1);
	    retrieveData = coll.find(query).sort(fields).limit(20); 
	    DBCursor  rs=retrieveData;
	    Application.showMessage("Entering here..."+rs.count());
	    Application.showMessage("Entering here1..."+query);
	    while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
        {
	    	Application.showMessage("Entering here2...");
       obj=rs.next();
          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
          {
 rowmsg=((BasicBSONObject) obj).getString("_id");
 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
 {
 
     Application.showMessage("Your Recieve XML is NULL \n");
     
     String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
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
// String senddataurl=((BasicBSONObject) obj).getString("REQUEST"); 
 String senddata= XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);
 
 String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
 Application.showMessage("Your SENDDATA XML is::\n"+senddata);
 Application.showMessage("Your Recieve XML is::\n"+recievedata);
    }
          }}}
    
	public void updateTier_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, JSONException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	    // c.setValue("UpdateTier","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****updateTier_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from updateTier:::";
	  //   lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("UpdateTier","false");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_updateAccountTierGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_updateAccountTierGroup"));
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/updateAccountTierGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
    	 //Sruthi
    	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);
    	 String senddataURL = ((BasicBSONObject) obj).getString("REQUEST");
    	 String data = senddataURL.substring(senddataURL.lastIndexOf("/")+1);
    	 Application.showMessage("data in  XML is ::\n"+data); 
    	 String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
    	 Application.showMessage("Your Recieve XML is::\n"+senddata);
    	 Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
    	
	            String id= senddata.trim();
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
		      //Added by Sruthi
			            String errorCode= XH.extractingValueFromJSONObject(recievedata, "errorCode", input, c);
			            Application.showMessage("errorCode is ::"+errorCode);
			           
			            
			           
			            if(errorCode==null)
			            {
				            addIssues.add("Send Xml errorCode is NULL");
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
				            	 addIssues.add("errorCode at Send Xml not Validated as "+errorCode);
				             }
			            }		

			            String tierGroup= XH.extractingValueFromJSONArray(senddataURL, "tiergroup", input, c);
			            Application.showMessage("tierGroup is::"+tierGroup); 
			             if(tierGroup==null)
			             {
				            addIssues.add("Send tierGroup is NULL");
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
				            	 addIssues.add("tierGroup at Send Xml not Validated as "+tierGroup);
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
	         {//Yamini
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.report(Issues.concat("WebService Call ::updateTier_Validate is Failed with Validation Errors").concat("**"));
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
    
    
    public String AddGroup_Validate(Object input, ScriptingContext c,String service) throws IOException, ClassNotFoundException, InterruptedException, JSONException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    List<String> addIssues=new ArrayList();
	    String Issues="Issues from addAccount:::";
	    String Time= c.get("BaseTime").toString();
	     String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****AddGroup_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	    
	     HashMap Operation=XH.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_addAccountGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_addAccountGroup"));
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/addAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
        	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c); 
	String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
	Application.showMessage("Your Recieve XML is::\n"+senddata);
	Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
	

		            Application.showMessage("SEND XML is ::\n"+senddata);   
		            Application.showMessage("receive XML is ::\n"+recievedata); 
		         		           
		            String id= senddata.trim();
		            Application.showMessage("id is ::"+id);
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	//Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	//Application.showMessage("SEND XML is :: \n"+senddata);
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
		            	 
		            	String accountId=XH.extractingValueFromJSONObject(recievedata, "accountId", input, c);

			            if( accountId==null)
			            {
			            	addIssues.add("Send Xml  accountId is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+ accountId);
			            	 if( accountId.equalsIgnoreCase(id))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall- accountId-Receive data********");
				            	 Application.showMessage("Validation is Successfull with  accountId::"+" "+ accountId);
				            	 v1=1;
				             }
				             else
				             {
				            	 addIssues.add(" accountId at Send Xml not Validated as in addAccountGroup "+ accountId);
				             }
			            }
		            	    String errorCode= XH.extractingValueFromJSONObject(recievedata, "errorCode", input, c);
				            Application.showMessage("errorCode is ::"+errorCode);
				           
				            
				            if(errorCode==null || errorCode.equalsIgnoreCase("null") )
				            {
				            	Application.showMessage("*******Validation Point 3 :: WebServicall-errorCode********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
				            	 System.out.println("*******Validation Point 3 :: WebServicall-errorCode********");
				            	 System.out.println("Validation is Successfull with errorCode::"+" "+errorCode);
				            	
				            	 v2=1;
				            }
				            else
				            {
				            	   	
				            	 v2=0;
				            	 addIssues.add("addAccountGroup is failed with the error code:: "+errorCode);
					            	 String errorMessage= XH.extractingValueFromJSONObject(recievedata, "errorMessage", input, c);
							            Application.showMessage("errorMessage is::"+errorMessage); 
							            System.out.println("errorMessage is::"+errorMessage); 
							            
					            
				            }		
				            		

				            String[] group= ((BasicBSONObject) obj).getString("REQUEST").split("group/");
				            Application.showMessage("AddAccountGroup is::"+group[1].replace("}", "")); 
				             if(group==null)
				             {
					            addIssues.add("Send group is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("group from Send Xml  from AddAccountGroup is ::"+" "+group[1].replace("}", ""));
				            	 if(group[1].replace("}", "").trim().equalsIgnoreCase(service.trim()))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: WebServicall-AddGroup********");
					            	 Application.showMessage("Validation is Successfull with group::"+" "+group[1].replace("}", ""));
					            	 v3=1;
					             }
					             else
					             {
					            	 v3=0;
					            	 addIssues.add("NewService at Send Xml not Validated as "+group[1].replace("}", ""));
					             }
				            }	
		            break;
		            }
		            
		                        
	             }
	         }
                }
	         if(v1*v2*v3==1)
	         {
	         	Application.showMessage("WebService Call :: AddGroup is Success[All validation points are Success]");
	         	 c.put("addGroupIssues","no");
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
	        	// addIssues.add(Issues.concat("WebService Call :: AddGroup_Validate is Failed with Validation Errors").concat("**"));
	        	 c.put("addGroupIssues", Issues.concat("WebService Call :: AddGroup_Validate is Failed with Validation Errors").concat("**"));
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
	
    public String RemoveGroup_Validate_CLS(Object input, ScriptingContext c,String service) throws IOException, ClassNotFoundException, InterruptedException, JSONException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String rscallpresent="true";
	     String Time= c.get("BaseTime").toString();
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from removeAccountGroup:::";
		   
	    // c.setValue("removeAccountgroup","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****RemoveAccountGroup_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	    // lC.findThinktimeOperationRTP(input, c);
	     //c.setValue("removeAccountgroup","false"); 
	     HashMap Operation=XH.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_removeAccountGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_removeAccountGroup"));
	     
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/removeAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			
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
 
{          //Sruthi
    	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);
    	 String senddataURL = ((BasicBSONObject) obj).getString("REQUEST");
    	 String data=senddataURL.substring(senddataURL.lastIndexOf("/")+1);
    	 Application.showMessage("data in  XML is ::\n"+data); 
    	 String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
    	 Application.showMessage("Your Recieve XML is::\n"+senddata);
    	 Application.showMessage("RECIEVE XML is ::\n"+recievedata);    
		            String id= senddata.trim();
		            Application.showMessage("id is ::"+id);
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	
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
		      
		            	 
		            	String accountId=XH.extractingValueFromJSONObject(recievedata, "accountId", input, c);

			            if( accountId==null)
			            {
			            	addIssues.add("Send Xml  accountId is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+ accountId);
			            	 if( accountId.equalsIgnoreCase(id))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall- accountId-Receive data********");
				            	 Application.showMessage("Validation is Successfull with  accountId::"+" "+ accountId);
				            	 v1=1;
				             }
				             else
				             {
				            	 addIssues.add(" accountId at Send Xml not Validated as in addAccountGroup "+ accountId);
				             }
			            }
		            	    String errorCode= XH.extractingValueFromJSONObject(recievedata, "errorCode", input, c);
				            Application.showMessage("errorCode is ::"+errorCode);
				           
				            
				            if(errorCode==null || errorCode.equalsIgnoreCase("null"))
				            {
				            	Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
				            	 System.out.println("*******Validation Point 2 :: WebServicall-errorCode********");
				            	 System.out.println("Validation is Successfull with errorCode::"+" "+errorCode);
				            	
				            	 v2=1;
				            }
				            else
				            {
				            	   	
				            	 v2=0;
				            	 addIssues.add("addAccountGroup is failed with the error code:: "+errorCode);
					            	 String errorMessage= XH.extractingValueFromJSONObject(recievedata, "errorMessage", input, c);
							            Application.showMessage("errorMessage is::"+errorMessage); 
							            System.out.println("errorMessage is::"+errorMessage); 
							            
					            
				            }		
				            		

				            String[] group= ((BasicBSONObject) obj).getString("REQUEST").split("group/");
				            Application.showMessage("AddAccountGroup is::"+group[1].replace("}", "")); 
				             if(group==null)
				             {
					            addIssues.add("Send group is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("group from Send Xml  from AddAccountGroup is ::"+" "+group[1].replace("}", ""));
				            	 if(group[1].replace("}", "").trim().equalsIgnoreCase(service.trim()))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-AddGroup********");
					            	 Application.showMessage("Validation is Successfull with group::"+" "+group[1].replace("}", ""));
					            	 v3=1;
					             }
					             else
					             {
					            	 v3=0;
					            	 addIssues.add("NewService at Send Xml not Validated as "+group[1].replace("}", ""));
					             }
				            }	
		            break;
		            }
		            
		                        
	             }
	         }
                }
	         if(v1*v2*v3==1)
	         {
	         	Application.showMessage("WebService Call :: removeAccountGroup is Success[All validation points are Success]");
	         	c.put("removeAccGrpIssues","no");
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
	        	// addIssues.add(Issues.concat("WebService Call :: removeAccountGroup_Validate is Failed with Validation Errors"));
	        	c.put("removeAccGrpIssues", Issues.concat("WebService Call :: removeAccountGroup_Validate is Failed with Validation Errors").concat("**"));
	   	      
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
    
    public String RestoreAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, JSONException
	{
		
	 // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	    // c.setValue("restoreAccount","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****RestoreAccount_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     String rscallpresent="true";
	    // lC.findThinktimeOperationRTP(input, c);
	    // c.setValue("restoreAccount","false");
	     HashMap Operation=XH.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_restoreAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_restoreAccount"));
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from restoreAccount:::";

	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/restoreAccount'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
                    	  //Sruthi
            String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);
           	String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
           	Application.showMessage("Your Recieve XML is::\n"+senddata);
           	Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
		            String id= senddata.trim();
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
		      //Added by Sruthi
		            	String errorCode= XH.extractingValueFromJSONObject(recievedata, "errorCode", input, c);
			            Application.showMessage("errorCode is ::"+errorCode);
			           
			            
			           
			            if(errorCode==null)
			            {
				            addIssues.add("Send Xml errorCode is NULL");
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
				            	 addIssues.add("errorCode at Send Xml not Validated as "+errorCode);
				             }
			            }		

			          
			            String errorMessage= XH.extractingValueFromJSONObject(recievedata, "errorMessage", input, c);
			            Application.showMessage("errorMessage is::"+errorMessage); 
			            
		               
		            break;
		            }
		            
		                        
	             }
	         }
                }
	         if(v1 ==1)
	         {
	         	Application.showMessage("WebService Call :: RestoreAccount_Validate is Success[All validation points are Success]");
	         	c.put("restoreAccountIssues","no");
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
	        	// c.report(Issues.concat("WebService Call :: restoreaccount_Validate is Failed with Validation Errors").concat("**"));
	        	 c.put("restoreAccountIssues", Issues.concat("WebService Call :: restoreaccount_Validate is Failed with Validation Errors").concat("**"));
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
	
    public String deleteUnactivatedAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, JSONException
	{
		
		Thread.sleep(40000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	     DBObject obj = null;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from removeAccount:::";
	     String rscallpresent="true";
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*******deleteUnactivatedAccount  Function********");       
	     Application.showMessage("-------------------------------------------------");
	     System.out.println("-------------------------------------------------");
	     System.out.println("*******deleteUnactivatedAccount  Function********");       
	     System.out.println("-------------------------------------------------");
	     HashMap Operation=XH.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("removeAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("removeAccount"));
	     System.out.println("OPERATIONValidation is "+(String) Operation.get("removeAccount"));
	     
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/deleteUnactivatedAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=XH.XHOM_reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
				c.put("removeAccountIssues",c.getValue("OPERATIONVALIDATION")+ " Not found on time");
 		 
 		  
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
        	
    String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);
	String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
	Application.showMessage("Your Recieve XML is::\n"+senddata);
	Application.showMessage("RECIEVE XML is ::\n"+recievedata); 

   
		            
	String id= senddata.trim();
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
		      
		            	String errorCode= XH.extractingValueFromJSONObject(recievedata, "errorCode", input, c);
			            Application.showMessage("errorCode is ::"+errorCode);
			            System.out.println("errorCode is ::"+errorCode);
			           
			            
			           //Sruthi
			            if(errorCode.equalsIgnoreCase("null"))
			            {
			            	Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
			            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
			            	 System.out.println("*******Validation Point 2 :: WebServicall-errorCode********");
			            	 System.out.println("Validation is Successfull with errorCode::"+" "+errorCode);
			            	
			            	 v1=1;
			            }
			            else
			            {
			            	   	
			            	 
				            	 addIssues.add("RemoveAccount is failed with the error code:: "+errorCode);
				            	 String errorMessage= XH.extractingValueFromJSONObject(recievedata, "errorMessage", input, c);
						            Application.showMessage("errorMessage is::"+errorMessage); 
						            System.out.println("errorMessage is::"+errorMessage); 
						            
				            
			            }		
			            
			            
		               
		            break;
		            }
		            
		                        
	             }
	         }}}
	         if(v1*callFound ==1)
	         {
	        	 c.put("removeAccountIssues", "no");
	         	Application.showMessage("WebService Call :: Remove account is Success[All validation points are Success]");
	         	 System.out.println("WebService Call :: Remove account is Success[All validation points are Success]");
	   	     
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
	        	 c.put("removeAccountIssues", Issues.concat("WebService Call :: remove account_Validate is Failed with Validation Errors").concat("**"));
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
    
public void addAccountScheduleUpgradeDowngrade(Object input, ScriptingContext c) throws Exception
{
	 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     DBCursor  rs;
     DBObject obj = null;
                int callFound=0,v1=0;
                String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String rscallpresent="true";
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****CLS_addAccountScheduleUpgradeDowngrade_Validate _Validate Function******");       
     Application.showMessage("-----------------------------------------------------");
     HashMap Operation=XH.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClScreateAccount"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClScreateAccount"));

         
                try
                {
                              //  Statement st =lC. dBConnect(input, c); 
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
                	if(rs == null)
                	{
                		rscallpresent = "false";
                		
                	
                		
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
                     
                     String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                     //REQUEST
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
                	 String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                     
                 String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
                 Application.showMessage("Your Send XML is::\n"+senddata);
                 Application.showMessage("Receive XML is ::\n"+recievedata);
                 String id= senddata.trim();
                            Application.showMessage("id is ::"+id);               
                            if(id.equals(c.getValue("ACC_input")))
                {
                             //   Application.showMessage("Recieve XML is::  \n"+ recievedata);
                             //   Application.showMessage("SEND XML is :: \n"+senddata);                                   
                                Application.showMessage("*******Validation Point 1 :: WebServicall-CLS-Get Account Call********");
                                Application.showMessage("Validation is Successfull with Input Account Number"+id);
                                callFound=1;
                }
                else
                {
                                continue;
                }
                
                            if(callFound==1)
                            {
                      
                                String errorCode_getAcc=XH.extractingValueFromJSONObject(recievedata,"errorCode", input, c); 
                                           // String errorCode_getAcc= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
                                            Application.showMessage("errorCode is ::"+errorCode_getAcc);
                                            
                                             if(errorCode_getAcc==null)
                                             {
                                                            c.report("Recieve Xml Account Number is NULL");
                                                            continue;
                                             }
                                            else
                                             {
                                                 Application.showMessage("Error code from Send Xml   is ::"+" "+errorCode_getAcc);
                                                 if(errorCode_getAcc.equalsIgnoreCase("UCE-15103") ||errorCode_getAcc.equalsIgnoreCase("UCE-15122") )
                                                             {
                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
                                                                 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
                                                                 v1=1;
                                                             }
                                                             
															 else if(errorCode_getAcc.equalsIgnoreCase("null"))
                                                             {
                                                            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
                                                                 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
                                                                 v1=1;
                                                             }
															 else
                                                             {
                                                                 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
                                                             }
                                            }                  
                                           String errorMessage_getAcc= XH.extractingValueFromJSONObject(recievedata,"errorMessage", input, c); 
                                             //String errorMessage_getAcc= lC.nodeFromKey(recievedata,"<errorMessage xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorMessage>");
                                            Application.showMessage("errorMessage is::"+errorMessage_getAcc); 

                                           //Application.showMessage(v1);
                            break;
                            }
             }
                          }
                        }
                          
         if(v1*callFound==1)
         {
                Application.showMessage("WebService Call :: Add Account is Success[All validation points are Success]");
         }
         else
         {
                 c.put("result", "false");
                 c.report("WebService Call ::Add Account is Failed with Validation Errors");
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
				

}
}
        
    
