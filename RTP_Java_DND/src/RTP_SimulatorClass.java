import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class RTP_SimulatorClass 
{
	
	public void simGetAccountValidate(Object input , ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****getAccount_Validate _Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     ResultMap= lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
	     c.setValue("NewGroup",ResultMap.get("group"));
	     c.setValue("NewService", ResultMap.get("service"));
	    // c.setValue("IsSingleGroup", ResultMap.get("IsSingle"));
	     c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
	     ResultMap=lC.splitter(c.getValue("Product"));
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	     
	     
	     
	     Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
	     Application.showMessage("NewService::"+c.getValue("NewService"));
	     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
	     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
	     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
	     Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
		            String receiveDataTrim = lC.RemoveNameSpaces(recievedata);
		            Application.showMessage("receiveDataTrim:::"+receiveDataTrim);
		            String id= lC.nodeFromKey(senddata,"<id>","</id>");
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
		      
		            	 String errorCode_getAcc=lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/errorCode");
		 	             //String errorCode_getAcc= lC.nodeFromKey(receiveDataTrim,"<errorCode>","</errorCode>");
		 	             Application.showMessage("errorCode is ::"+errorCode_getAcc);
		 	             
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
				             else
				             {
				            	 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
				             }
			            }		
		 	            String status_getAcc= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/status");
		 	             //String status_getAcc= lC.nodeFromKey(receiveDataTrim,"<status>","</status>");
		 	             Application.showMessage("GetAccount Status is::"+status_getAcc); 
		 	            if(c.getValue("IsMultiExist").equals("true"))
		            	 {
		 	            	String group1= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/group");
			 	            //String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group><group>");
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
			                
			                String service1= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/group[2]").trim();
			                //String service1= lC.nodeFromKey(recievedata,"</group><group>","</group></account>").trim();
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
		            		 String group1= lC.GetValueByXPath(receiveDataTrim,"/GetAccountResponse/account/group".trim());
		            		    //String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group></account>").trim();
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
			            
		 	            
		            break;
		            }
	             }
	         }
	         if(v1*v3*callFound==1)
	         {
	         	Application.showMessage("WebService Call :: Get Account is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::Get Account is Failed with Validation Errors");
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
	
	
	public void deactivateAccount_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("******deactivateAccount_Validate Function********");       
	     Application.showMessage("-------------------------------------------------");
	     
	     
	  
	     
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/deactivateAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
		            String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
		            Application.showMessage("receiveDataTrim::::"+receiveDataTrim);
		            String id= lC.nodeFromKey(senddata,"<id>","</id>");
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
		               // String errorCode=lC.GetValueByXPath(receiveDataTrim, "/DeactivateAccountResponse/errorCode");
		            	String errorCode= lC.nodeFromKey(receiveDataTrim,"<errorCode>","</errorCode>");
			            Application.showMessage("errorCode is ::"+errorCode);
			           
			            
			           
			            if(errorCode==null)
			            {
				            c.report("Send Xml errorCode is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from deactivateAccount is ::"+" "+errorCode);
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

			            String accountIdRes= lC.nodeFromKey(receiveDataTrim,"<accountId>","</accountId>");
			            Application.showMessage("accountId from respose of Deactivate Account  is::"+accountIdRes); 
			            
		               
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1 ==1)
	         {
	         	Application.showMessage("WebService Call :: deactivateAccount is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: deactivateAccount is Failed with Validation Errors");
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
	
	public void createAccount_ValidateInsight(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	   
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********createAccount_Validate Function***********");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
		          	           
		            String accountId_ucontrolsrv = lC.nodeFromKey(senddata,"<ucontrolsrv:accountId>","</ucontrolsrv:accountId>");
	 	            Application.showMessage("accountNumber is ::"+accountId_ucontrolsrv); 	           
		            if(accountId_ucontrolsrv.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-createAccount_Validate********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+accountId_ucontrolsrv);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	            String status_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:status>","</ucontrolsrv:status>");
		 	            Application.showMessage("ucontrolsrv:status is ::"+status_ucontrolsrv); 
		 	            
		 	           if(status_ucontrolsrv==null)
			            {
				            c.report(" ucontrolsrv:status is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("ucontrolsrv:status from Send Xml  from createAccount_Validate is ::"+" "+status_ucontrolsrv);
			            	 if(status_ucontrolsrv.equalsIgnoreCase("NEW"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-ucontrolsrv:status********");
				            	 Application.showMessage("Validation is Successfull with ucontrolsrv:status::"+" "+status_ucontrolsrv);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("ucontrolsrv:status at Send Xml not Validated as "+status_ucontrolsrv);
				             }
			            }					

		 	            String firstName_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:firstName>","</ucontrolsrv:firstName>");
		 	            Application.showMessage("ucontrolsrv:firstName is ::"+firstName_ucontrolsrv); 
		 	            
		 	           if(firstName_ucontrolsrv==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	
			            	 if(firstName_ucontrolsrv.equals(c.getValue("FirstName")))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ucontrolsrv:firstName********");
				            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+firstName_ucontrolsrv);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("FirstName at Send Xml not Validated as "+firstName_ucontrolsrv);
				             }
			            }

		 	            String lastName_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:lastName>","</ucontrolsrv:lastName>");
		 	            Application.showMessage("ucontrolsrv:lastName is ::"+lastName_ucontrolsrv); 
		 	            
		 	           if(lastName_ucontrolsrv==null)
			            {
				            c.report("Send Xml ucontrolsrv:lastName is NULL");
				            continue;
			            }
			            else
			            {
			            	
			            	 if(lastName_ucontrolsrv.equals(c.getValue("LastName")))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-ucontrolsrv:firstName********");
				            	 Application.showMessage("Validation is Successfull with ucontrolsrv:lastName::"+" "+lastName_ucontrolsrv);
				            	 v3=1;
				             }
				             else
				             {
				            	 c.report("ucontrolsrv:lastName at Send Xml not Validated as "+lastName_ucontrolsrv);
				             }
			            }

		 	            String ucontrolsrv_phoneNumber= lC.nodeFromKey(senddata,"<ucontrolsrv:phoneNumber>","</ucontrolsrv:phoneNumber>");
		 	            Application.showMessage("ucontrolsrv:phoneNumber is ::"+ucontrolsrv_phoneNumber); 
		 	            c.setValue("phoneNumber", ucontrolsrv_phoneNumber);
		 	            
		 	            String ucontrolsrv_emailAddress= lC.nodeFromKey(senddata,"<ucontrolsrv:emailAddress>","</ucontrolsrv:emailAddress>");
		 	            Application.showMessage("ucontrolsrv:emailAddress is ::"+ucontrolsrv_emailAddress); 
		 	          
		 	            if(ucontrolsrv_emailAddress==null)
			            {
				            c.report("Send Xml emailAddress is NULL");
				            
			            }
			            else
			            {
			            	 Application.showMessage("City from Send Xml  from ucontrolsrv_emailAddress is ::"+" "+ucontrolsrv_emailAddress);
			            	 if(ucontrolsrv_emailAddress.equalsIgnoreCase(c.getValue("emailAddress")))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ucontrolsrv_emailAddress********");
				            	 Application.showMessage("Validation is Successfull with ucontrolsrv_emailAddress::"+" "+ucontrolsrv_emailAddress);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("ucontrolsrv_emailAddress at Send Xml not Validated as "+ucontrolsrv_emailAddress);
				             }
			            }	
		 	            
		 	            String ucontrolsrv_address1= lC.nodeFromKey(senddata,"<ucontrolsrv:address1>","</ucontrolsrv:address1>");
		 	            Application.showMessage("ucontrolsrv:address1 is ::"+ucontrolsrv_address1); 
		 	            c.setValue("Address", ucontrolsrv_address1);
		 	           
		 	            String ucontrolsrv_city= lC.nodeFromKey(senddata,"<ucontrolsrv:city>","</ucontrolsrv:city>");
		 	            Application.showMessage("City is ::"+ucontrolsrv_city); 
		 	            
		 	           if(ucontrolsrv_city==null)
			            {
				            c.report("Send Xml City is NULL");
				          
			            }
			            else
			            {
			            	 Application.showMessage("City from Send Xml  from ucontrolsrv_city is ::"+" "+ucontrolsrv_city);
			            	 if(ucontrolsrv_city.equalsIgnoreCase(c.getValue("City")))
				             {
				            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
				            	 Application.showMessage("Validation is Successfull with City::"+" "+ucontrolsrv_city);
				            	 v5=1;
				             }
				             else
				             {
				            	 c.report("City at Send Xml not Validated as "+ucontrolsrv_city);
				             }
			            }	
		 	            
		 	            String ucontrolsrv_province= lC.nodeFromKey(senddata,"<ucontrolsrv:province>","</ucontrolsrv:province>");
		 	            Application.showMessage("ucontrolsrv:province is ::"+ucontrolsrv_province); 
		 	            
		 	            String ucontrolsrv_postalCode= lC.nodeFromKey(senddata,"<ucontrolsrv:postalCode>","</ucontrolsrv:postalCode>");
		 	            Application.showMessage("ucontrolsrv:postalCode is ::"+ucontrolsrv_postalCode); 
		 	            
		 	            c.setValue("PostalCode", ucontrolsrv_postalCode);
		 	            
		 	            String ucontrolsrv_portalUserSSOId= lC.nodeFromKey(senddata,"<ucontrolsrv:portalUserSSOId>","</ucontrolsrv:portalUserSSOId>");
		 	            Application.showMessage("ucontrolsrv:portalUserSSOId is ::"+ucontrolsrv_portalUserSSOId); 
		 	            
		 	           if(ucontrolsrv_portalUserSSOId==null)
			            {
				            c.report("Send Xml ucontrolsrv_portalUserSSOId is NULL");
				           
			            }
			            else
			            {
			            	 Application.showMessage("portalUserSSOId from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_portalUserSSOId);
			            	 if(ucontrolsrv_portalUserSSOId.equalsIgnoreCase(c.getValue("authorizationGuid")))
				             {
				            	 Application.showMessage("*******Validation Point 7 :: WebServicall-ucontrolsrv_portalUserSSOId********");
				            	 Application.showMessage("Validation is Successfull with ucontrolsrv_portalUserSSOId::"+" "+ucontrolsrv_portalUserSSOId);
				            	 v6=1;
				             }
				             else
				             {
				            	 c.report("ucontrolsrv_portalUserSSOId at Send Xml not Validated as "+ucontrolsrv_portalUserSSOId);
				             }
			            }
		 	           
		 	          Map<String, String> ResultMap = new HashMap<String,String>();
		 		      ResultMap= lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
		 	          c.setValue("ExistingGroup",ResultMap.get("group"));
		 		      c.setValue("ExistingService", ResultMap.get("service"));
		 		      c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
		 	           
		 	            
		 	            if(c.getValue("IsMultiExist").equals("true"))
		 	            {
		 	            	String ucontrolsrv_group1= lC.nodeFromKey(senddata,"</ucontrolsrv:portalUserSSOId><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
			 	            Application.showMessage("ucontrolsrv:group1 is ::"+ucontrolsrv_group1); 
			 	            
			 	           if(ucontrolsrv_group1==null)
				            {
					            c.report("Send Xml ucontrolsrv_group1 is NULL");
					          
				            }
				            else
				            {
				            	 Application.showMessage("ucontrolsrv_group from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_group1);
				            	 if(ucontrolsrv_group1.equals(c.getValue("ExistingGroup"))||ucontrolsrv_group1.equals(c.getValue("ExistingService")))
					             {
					            	 Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group********");
					            	 Application.showMessage("Validation is Successfull with ucontrolsrv_group1::"+" "+ucontrolsrv_group1);
					            	 v7=1;
					             }
					             else
					             {
					            	 c.report("ucontrolsrv_group at Send Xml not Validated as "+ucontrolsrv_group1);
					             }
				            }
			 	           
			 	            String ucontrolsrv_group2= lC.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group></account>");
			 	            Application.showMessage("ucontrolsrv:group2 is ::"+ucontrolsrv_group2); 
			 	           if(ucontrolsrv_group2==null)
				            {
					            c.report("Send Xml ucontrolsrv_group1 is NULL");
					          
				            }
				            else
				            {
				            	 Application.showMessage("ucontrolsrv_group from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_group2);
				            	 if(ucontrolsrv_group2.equals(c.getValue("ExistingService"))||ucontrolsrv_group1.equals(c.getValue("ExistingGroup")))
					             {
					            	 Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group2********");
					            	 Application.showMessage("Validation is Successfull with ucontrolsrv_group2::"+" "+ucontrolsrv_group2);
					            	 v7=1;
					             }
					             else
					             {
					            	 c.report("ucontrolsrv_group2 at Send Xml not Validated as "+ucontrolsrv_group2);
					             }
				            }
			 	            
			 	            
		 	            }
		 	            
		 	            else
		 	            {
		 	            
			 	            String ucontrolsrv_group= lC.nodeFromKey(senddata,"<ucontrolsrv:group>","</ucontrolsrv:group>");
			 	            Application.showMessage("ucontrolsrv:group is ::"+ucontrolsrv_group); 
			 	            
			 	           if(ucontrolsrv_group==null)
				            {
					            c.report("Send Xml ucontrolsrv_group is NULL");
					          
				            }
				            else
				            {
				            	 Application.showMessage("ucontrolsrv_group from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_group);
				            	 if(ucontrolsrv_group.equals(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE")))
					             {
					            	 Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group********");
					            	 Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+ucontrolsrv_group);
					            	 v7=1;
					             }
					             else
					             {
					            	 c.report("ucontrolsrv_group at Send Xml not Validated as "+ucontrolsrv_group);
					             }
				            }	
		 	            }
		 	             	         	 	            
		 	            String errorCode_createAcc= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
		 	            Application.showMessage("errorCode is::"+errorCode_createAcc);
		 	            
		 	           if(errorCode_createAcc==null)
			            {
				            c.report("Send Xml errorCode_createAcc is NULL");
				           
			            }
			            else
			            {
			            	 Application.showMessage("errorCode_createAcc from Send Xml  is ::"+" "+errorCode_createAcc);
			            	 if(errorCode_createAcc.equals("0"))
				             {
				            	 Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
				            	 Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
				            	 v8=1;
				             }
				             else
				             {
				            	 c.report("errorCode_createAcc at Send Xml not Validated as "+errorCode_createAcc);
				             }
			            }	
		 	            
		 	            
		 	            String accountId_createAcc= lC.nodeFromKey(recievedata,"<accountId xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</accountId>");
		 	            Application.showMessage("accountId is::"+accountId_createAcc); 
		 	                    

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3*v4*v5*v6*v7*v8 ==1)
	         {
	         	Application.showMessage("WebService Call :: CreateAccount is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::CreateAccount is Failed with Validation Errors");
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
	
	public void DisconnectAccount_CANCEL_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		// Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	    // c.setValue("DisconnectAccount","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*******DisconnectAccount  Function********");       
	     Application.showMessage("-------------------------------------------------");
	     System.out.println("-------------------------------------------------");
	     System.out.println("*******DisconnectAccount  Function********");       
	     System.out.println("-------------------------------------------------");
	    // lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("DisconnectAccount","false"); 
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("DisconnectAccount"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("DisconnectAccount"));
	     System.out.println("OPERATIONValidation is "+(String) Operation.get("DisconnectAccount"));
	     
	     
	     
	  
	     
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/DisconnectAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
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
				            	
			            	 if(result.trim().equals("OK") || result.trim().equals("RJ"))
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
	         	Application.showMessage("WebService Call :: DisconnectAccount is Success[All validation points are Success]");
	         	 System.out.println("WebService Call :: DisconnectAccount is Success[All validation points are Success]");
	    	    
	         }
	         else
	         {
	        	 c.report("WebService Call :: DisconnectAccount is Failed with Validation Errors");
	         }
	        // st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	


}
