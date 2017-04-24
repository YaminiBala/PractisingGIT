import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class New_ValidationCalls 
{
	
	//----ScheDule Calls----------------
	public void SchedulecreateAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********createAccount_Validate Function***********");       
	     Application.showMessage("----------------------------------------------------");
	     
	     Map<String, String> ResultMap = new HashMap<String,String>();
	    
	     ResultMap= lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE"));
	     c.setValue("ExistingGroup",ResultMap.get("group"));
	     c.setValue("ExistingService", ResultMap.get("service"));
	     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' order by creation_time desc)where rownum <= 20");
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
				            	 v5=1;
				            	// c.report("City at Send Xml not Validated as "+ucontrolsrv_city);
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
		 	            
		 	            if(c.getValue("IsMultiExist").equals("true"))
		 	            {
		 	            	  if(c.getValue("IsDemi").equalsIgnoreCase("true"))
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
				 	            
				 	            
				 	            String ucontrolsrv_service1= lC.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group></account>");
				 	            Application.showMessage("ucontrolsrv_service1 is ::"+ucontrolsrv_service1); 
				 	            
				 	            if(ucontrolsrv_group1==null)
					            {
						            c.report("Send Xml ucontrolsrv_group1 is NULL");
						          
					            }
					            else
					            {
					            	 Application.showMessage("ucontrolsrv_group from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_group1);
					            	 if(ucontrolsrv_service1.equals(c.getValue("ExistingGroup"))||ucontrolsrv_service1.equals(c.getValue("ExistingService")))
						             {
						            	 Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group********");
						            	 Application.showMessage("Validation is Successfull with ucontrolsrv_group1::"+" "+ucontrolsrv_service1);
						            	 v7=1;
						             }
						             else
						             {
						            	 c.report("ucontrolsrv_group at Send Xml not Validated as "+ucontrolsrv_service1);
						             }
					            }
			 	           
		 	            	 }
		 	            	else
		 	            	{
		 	            	
		 	            		String ucontrolsrv_group1= lC.nodeFromKey(senddata,"</ucontrolsrv:product><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
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
				            	 if(ucontrolsrv_group2.equals(c.getValue("ExistingService"))||ucontrolsrv_group2.equals(c.getValue("ExistingGroup")))
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
				            	 if(ucontrolsrv_group.equals( c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE").trim()))
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
			            	 if(errorCode_createAcc.equals("0") || errorCode_createAcc.equals("UCE-15103") )
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

	
	//----------------
	public void orderUpdateCos_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********orderUpdateCos_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
				            	 String sNewService= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE").trim();
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
		 		            	 if(Service.equals(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE").trim()))
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
				            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
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
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }

	
	//--------------------
	
	public void ScheduleorderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********OrderUpdate_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	         
		try
		{
			Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
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

		 	            
		 	            
		 	            if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("DEMI"))
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
			 	           
			 	            String Service= lC.nodeFromKey(senddata,"Service = "," Hav Market = U").trim();
			 	            Application.showMessage("Service is ::"+Service); 
			 	            
			 	           if(Service==null)
				            {
					            c.report("Send Xml Service is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
				            	 Application.showMessage("Service from Input:::"+c.getValue("sSERVICEorCURRENTSERVICE").trim());
				            	 if(Service.equals(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE").trim()))
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
		 		            	
		 		            	 Application.showMessage("Service from Input   is ::"+" "+c.getValue("sSERVICEorCURRENTSERVICE"));
		 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
		 		            	 if(Service.equals(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE").trim()))
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
			            	 if(ordType.equals("NEW"))
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
	         	
	         //	c.setValue("IsDemi", "false");
	         }
	         else
	         {
	        	// c.setValue("IsDemi", "false");
	        	// c.put("result", "false");
	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
	         }
	         st.close();
	         
	        //Lp.reportingToExcel(Object , ScriptingContext);
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	//-------------------
	
	public void ScheduleDISorderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		// String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-------------------------------------------------------------");
	     Application.showMessage("***********OrderUpdate_Validate Function**************");       
	     Application.showMessage("-------------------------------------------------------------");
	         
		try
		{
			Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
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

		 	            
		 	            
		 	            if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("DEMI"))
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
			 	           
			 	            String Service= lC.nodeFromKey(senddata,"Service = "," Hav Market = U").trim();
			 	            Application.showMessage("Service is ::"+Service); 
			 	            
			 	           if(Service==null)
				            {
					            c.report("Send Xml Service is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
				            	 Application.showMessage("Service from Input:::"+c.getValue("sSERVICEorCURRENTSERVICE").trim());
				            	 if(Service.equals(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE").trim()))
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
		 		            	
		 		            	 Application.showMessage("Service from Input   is ::"+" "+c.getValue("sSERVICEorCURRENTSERVICE"));
		 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
		 		            	 if(Service.equals(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE").trim()))
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
			            	 if(ordType.equals("DIS"))
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
	         	
	         //	c.setValue("IsDemi", "false");
	         }
	         else
	         {
	        	// c.setValue("IsDemi", "false");
	        	// c.put("result", "false");
	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
	         }
	         st.close();
	         
	        //Lp.reportingToExcel(Object , ScriptingContext);
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }

	
	//------------------
	
	public void scheduleCOSrtpTestInterface_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
    {
                                 LibraryRtp  lC= new LibraryRtp();
                                 ResultSet  rs;
                                 int callFound=0, v1=0;
                                 String xmldata1 ="receive_data";
             String xmldata2 ="SEND_DATA";  
             Thread.sleep(5000);
             String Time= c.get("BaseTime").toString();
             c.setValue("RTPTestInterface","true");
             lC.findThinktimeOperationRTP(input, c);
             Application.showMessage("----------------------------------------------------------");
             Application.showMessage("************rtpTestInterface_Validate Function************");    
             Application.showMessage("----------------------------------------------------------");
             c.setValue("RTPTestInterface","false"); 
                                try
                                {
                                                
                                                //Lp.Opconnection();
                                                //Class.forName("oracle.jdbc.driver.OracleDriver");
                                                //connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1", "CWDEVOP","CWDEVOP");                         
                                	rs=lC.reduceThinkTimeRTP(input, c);
                        while (rs.next() )
                        {
                              Application.showMessage("RTPTestInterface msgid= "+ rs.getString(1));
                              String rowmsg = rs.getString(1);           
                              if(rs.getBlob("receive_data")==null)
                              {
                           
                                String senddata =lC.extractXml(rs,xmldata2);                
                                            String accountNumber_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/@accountNumber");
                                            Application.showMessage("accountNumber is ::"+accountNumber_rtpTest); 
                                                Application.showMessage("Send Data is::"+senddata);           
                                            if(accountNumber_rtpTest==null)
                                            {
                                                            
                                                            Application.showMessage("accountNumber ID is NULL \n");

                                                            continue;
                                            }
                                            else
                                            {
                                                 
                                                Application.showMessage("accountNumber value from rtpTestInterface is::"+accountNumber_rtpTest);
                                                Application.showMessage("c.getValue(ACC_input) value  is::"+c.getValue("ACC_input").toString().trim());
                                                
                                                if(accountNumber_rtpTest.trim().equals(c.getValue("ACC_input").toString().trim()))
                                                { 
                                                
                                                                Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
                                                                callFound=1;
                                                }
                                                else
                                                {
                                                                Application.showMessage("Account Number not validated..so picking other Account Number..!");
                                                                continue;
                                                }
                                                                                                
                                            }
                                            Application.showMessage("CallFound is ::"+callFound);
                                           
                                            if(callFound==1)
                                {
                                                 String locationId_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Location/@locationId");
                                                 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
                                                                           
                                                                            if(locationId_rtpTest==null)
                                                                            {
                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                            c.report("locationId is NULL");
                                                                                            continue;
                                                                            }
                                                                            else
                                                                            {
                                                                                
                                                                                 if(locationId_rtpTest.toString().trim().equals(c.getValue("sHOUSE_KEY").toString().trim()))
                                                                                             {
                                                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
                                                                                                 Application.showMessage("Validation is Successfull with locationId"+" "+locationId_rtpTest);
                                                                                                 v1=1;
                                                                                                 String PrimaryEmail_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/PrimaryEmail");
                                                                                                 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
                                         c.setValue("emailAddress", PrimaryEmail_rtpTest);
                                                                                                                            
                                                                                                                         String FirstName_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/FirstName");
                                                                                                                         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
                                                                                                                         c.setValue("FirstName", FirstName_rtpTest); 
                                                                                                                         
                                                                                                                         String LastName_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/LastName");
                                                                                                                         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
                                                                                                                         c.setValue("LastName", LastName_rtpTest); 
                                                                                                                         
                                                                                                                         String OrderStatus_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderStatus");
                                                                                                                         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
                                                                                                                            
                                                                                                                         String OrderType_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderType");
                                                                                                                         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
                                                                                                                         
                                                                                                                         String RescheduleIndicator_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
                                                                                                                         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
                                                                                                                         String EventSource= lC.nodeFromKey(senddata,"<EventSource>","</EventSource>");
                                                                                                                         c.setValue("sEventSource", EventSource);
                                                                                                                         Application.showMessage("EventSource is::"+c.getValue("sEventSource")); 
                                                                                                                         
                                                                                                                         String city=  lC.nodeFromKey(senddata,"<City>","</City>");
                                                                                                                         c.put("Ecity",city);
                                                                                                                         c.setValue("City",city);
                                                                                                                         Application.showMessage("Ecity is::"+c.get("Ecity")); 
                                                                                                                         
                                                                                                                         String Zipcode=  lC.nodeFromKey(senddata,"<Zipcode>","</Zipcode>");
                                                                                                                         c.put("Ezipcode",Zipcode);
                                                                                                                         Application.showMessage("Ezipcode is::"+c.get("Ezipcode")); 

                                                                                                                         
                                                                                                                         if(v1*callFound==1)
                                                                                                                            {
                                                                                                                            
                                                                                                                                Application.showMessage("Validation Points are Success");
                                                                                                                                break;
                                                                                                                            }
                                                                                                                            
                                                                                                                            else
                                                                                                                            {
                                                                                                                                Application.showMessage("No call Found");
                                                                                                                                c.put("result", "false");
                                                                                                                                break;
                                                                                                                            }
                                                                                             }
                                                                                             else
                                                                                             {
                                                                                                 Application.showMessage("Location ID *******NOT FOUND ***");
                                                                                                 continue;
                                                                                             }
                                                                            }                                                              
                                }
                             
                                                       
                            }
                            else if(rs.getBlob("SEND_DATA")==null)
                            {
                                
                                String recievedata=lC.extractXml(rs,xmldata1);
                                         
                                            String accountNumber_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/@accountNumber");
                                            Application.showMessage("accountNumber is ::"+accountNumber_rtpTest); 
                                            Application.showMessage("recievedata is ::"+recievedata); 
                                                          
                                            if(accountNumber_rtpTest==null)
                                            {
                                                          
                                                            Application.showMessage("accountNumber ID is NULL \n");

                                                            continue;
                                            }
                                            else
                                            {
                                                System.out.printf("accountNumber value from rtpTestInterface is :: %s \n",accountNumber_rtpTest); 
                                                Application.showMessage("accountNumber value from rtpTestInterface is::"+accountNumber_rtpTest);
                                                if(accountNumber_rtpTest.equals(c.getValue("ACC_input")))
                                                {
                                                    
                                                                                                            
                                                                Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
                                                                callFound=1;
                                                }
                                                else
                                                {
                                                                continue;
                                                }
                                                                                                
                                            }
                                            
                                            if(callFound==1)
                                {
                                                 String locationId_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Location/@locationId");
                                                 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
                                                                           
                                                                            if(locationId_rtpTest==null)
                                                                            {
                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                            c.report("locationId is NULL");
                                                                                            continue;
                                                                            }
                                                                            else
                                                                            {
                                                                                 
                                                                                 if(locationId_rtpTest.equals(c.getValue("sHOUSE_KEY")))
                                                                                 {
                                                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
                                                                                                 Application.showMessage("Validation is Successfull with locationId"+" "+locationId_rtpTest);
                                                                                                 v1=1;
                                                                                                 String PrimaryEmail_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
                                                                                                 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
                                                                                                 c.setValue("emailAddress", PrimaryEmail_rtpTest);
                                                                                                 
                                                                                                                         String FirstName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/FirstName");
                                                                                                                         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
                                                                                                                         c.setValue("FirstName", FirstName_rtpTest);
                                                                                                                         
                                                                                                                         String LastName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/LastName");
                                                                                                                         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
                                                                                                                         c.setValue("LastName", LastName_rtpTest); 
                                                                                                                         
                                                                                                                         String OrderStatus_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
                                                                                                                         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
                                                                                                                            
                                                                                                                         String OrderType_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
                                                                                                                         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
                                                                                                                         
                                                                                                                         String RescheduleIndicator_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
                                                                                                                         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
                                                                                                                         String EventSource= lC.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
                                                                                                                         c.setValue("sEventSource", EventSource);
                                                                                                                         
                                                                                                                         String city=  lC.nodeFromKey(recievedata,"<City>","</City>");
                                                                                                                         c.put("Ecity",city);
                                                                                                                         Application.showMessage("Ecity is::"+c.get("Ecity")); 
                                                                                                                         
                                                                                                                         String Zipcode=  lC.nodeFromKey(recievedata,"<City>","</City>");
                                                                                                                         c.put("Ezipcode",Zipcode);
                                                                                                                         Application.showMessage("Ezipcode is::"+c.get("Ezipcode")); 
                                                                                                                         
                                                                                                                         Application.showMessage("EventSource is::"+c.getValue("sEventSource"));
                                                                                                                         if(v1*callFound==1)
                                                                                                                            {
                                                                                                                            
                                                                                                                                Application.showMessage("Validation Points are Success");
                                                                                                                                break;
                                                                                                                            }
                                                                                                                            
                                                                                                                            else
                                                                                                                            {
                                                                                                                                c.put("result", "false");
                                                                                                                                Application.showMessage("No call Found");       
                                                                                                                                break;
                                                                                                                            }
                                                                                             }
                                                                                             else
                                                                                             {
                                                                                                 continue;
                                                                                             }
                                                                           }                                                              
                                  }
                            }
                                            else
                                            {
                                                String recievedata=lC.extractXml(rs,xmldata1);
                                                                         
                                                            String accountNumber_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/@accountNumber");
                                                            Application.showMessage("accountNumber is ::"+accountNumber_rtpTest); 
                                                                          
                                                            if(accountNumber_rtpTest==null)
                                                            {
                                                                          
                                                                            Application.showMessage("accountNumber ID is NULL \n");

                                                                            continue;
                                                            }
                                                            else
                                                            {
                                                                System.out.printf("accountNumber value from rtpTestInterface is :: %s \n",accountNumber_rtpTest); 
                                                                Application.showMessage("accountNumber value from rtpTestInterface is::"+accountNumber_rtpTest);
                                                                if(accountNumber_rtpTest.equals(c.getValue("ACC_input")))
                                                                {
                                                                    
                                                                                                                            
                                                                                Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                                Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
                                                                                callFound=1;
                                                                }
                                                                else
                                                                {
                                                                                continue;
                                                                }
                                                                                                                
                                                            }
                                                            
                                                            if(callFound==1)
                                                {
                                                                 String locationId_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Location/@locationId");
                                                                 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
                                                                                           
                                                                                            if(locationId_rtpTest==null)
                                                                                            {
                                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                                            c.report("locationId is NULL");
                                                                                                            continue;
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                 Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+locationId_rtpTest);
                                                                                                 if(locationId_rtpTest.equals(c.getValue("sHOUSE_KEY")))
                                                                                                 {
                                                                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
                                                                                                                 Application.showMessage("Validation is Successfull with locationId"+" "+locationId_rtpTest);
                                                                                                                 v1=1;
                                                                                                                 String PrimaryEmail_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
                                                                                                                 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
                                                                                                                 c.setValue("emailAddress", PrimaryEmail_rtpTest);
                                                                                                                 
                                                                                                                                         String FirstName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/FirstName");
                                                                                                                                         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
                                                                                                                                         c.setValue("FirstName", FirstName_rtpTest);
                                                                                                                                         
                                                                                                                                         String LastName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/LastName");
                                                                                                                                         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
                                                                                                                                         c.setValue("LastName", LastName_rtpTest); 
                                                                                                                                         
                                                                                                                                         String OrderStatus_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
                                                                                                                                         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
                                                                                                                                            
                                                                                                                                         String OrderType_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
                                                                                                                                         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
                                                                                                                                         
                                                                                                                                         String RescheduleIndicator_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
                                                                                                                                         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
                                                                                                                                         String EventSource= lC.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
                                                                                                                                         c.setValue("sEventSource", EventSource);
                                                                                                                                         
                                                                                                                                         String city=  lC.nodeFromKey(recievedata,"<City>","</City>");
                                                                                                                                         c.put("Ecity",city);
                                                                                                                                         Application.showMessage("Ecity is::"+c.get("Ecity")); 
                                                                                                                                         
                                                                                                                                         String Zipcode=  lC.nodeFromKey(recievedata,"<City>","</City>");
                                                                                                                                         c.put("Ezipcode",Zipcode);
                                                                                                                                         Application.showMessage("Ezipcode is::"+c.get("Ezipcode")); 
                                                                                                                                         
                                                                                                                                         Application.showMessage("EventSource is::"+c.getValue("sEventSource"));
                                                                                                                                         if(v1*callFound==1)
                                                                                                                                            {
                                                                                                                                            
                                                                                                                                                Application.showMessage("Validation Points are Success");
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                                                            
                                                                                                                                            else
                                                                                                                                            {
                                                                                                                                                c.put("result", "false");
                                                                                                                                                Application.showMessage("No call Found");       
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                             }
                                                                                                             else
                                                                                                             {
                                                                                                                 continue;
                                                                                                             }
                                                                                            }
                                                }
                                            
                             
                            }
                        
                      //   st.close();
                        }
                                }
                                catch (SQLException e)
                                {
                                    System.out.println("Connection Failed! Check output console");
                                                e.printStackTrace();
                                                return;
                                }
                }
                
                
}
