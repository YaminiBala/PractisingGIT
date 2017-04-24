import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class sikXhs 
{
	
	sikLibraryClass  lC= new sikLibraryClass();
	
	public void queryLocation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
	    
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
	        	
	        
	          //  rowmsg=rs.getString(1);
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
		            if(legacyID.equals(c.getValue("accountNumber")))
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
	        	 c.put("result", "false");
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


	//done till above


	public void getCustomer_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	    
		 ResultSet  rs;
		 int callFound=0,v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****getCustomer_Validate _Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cs:CustomerServicePort/getCustomer' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	          //  rowmsg=rs.getString(1);
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
		          	           
		            String id= lC.nodeFromKey(senddata,"<ct:id>","</ct:id>");
		            Application.showMessage("id is ::"+id); 	           
		            if(id.equals(c.getValue("dS_SIK","Sik_Dotcom: customerID")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
		            	v1=1;
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Get Account Call********");
	            		Application.showMessage("Validation is Successfull with Customer Number"+id);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		            	
//		 	             String errorCode_getAcc= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
//		 	             Application.showMessage("errorCode is ::"+errorCode_getAcc);
		 	             
//		 	             if(errorCode_getAcc==null)
//			             {
//				            c.report("Recieve Xml Account Number is NULL");
//				            continue;
//			             }
//			             else
//			             {
//			            	 Application.showMessage("locationStatus from Send Xml  from queryLocation is ::"+" "+errorCode_getAcc);
//			            	 if(errorCode_getAcc.equalsIgnoreCase("UCE-15101"))
//				             {
//				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
//				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
//				            	 v1=1;
//				             }
//				             else
//				             {
//				            	 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
//				             }
//			               }		
		 	            
		 	             String customerProfileCategory_cust= lC.nodeFromKey(senddata,"<cs:customerProfileCategory>","</cs:customerProfileCategory></cs:getCustomer>");
		 	             Application.showMessage("customerProfileCategory is::"+customerProfileCategory_cust);
		 	             
		 	            String LstName_Cust= lC.nodeFromKey(recievedata,"<n1:lastName xmlns:n1=\"http://xml.comcast.com/common/types\">","</n1:lastName><n2:middleName xsi:nil=\"true\" xmlns:n2=\"http://xml.comcast.com/common/types\" />");
		 	             Application.showMessage("Last name is::"+LstName_Cust); 
		 	             
		 	            String firstName_Cust= lC.nodeFromKey(recievedata,"<n4:firstName xmlns:n4=\"http://xml.comcast.com/common/types\">","</n4:firstName><n5:suffix xsi:nil=\"true\" xmlns:n5=\"http://xml.comcast.com/common/types\" />");
		 	             Application.showMessage("First Name is::"+firstName_Cust); 
		 	             
		 	            String Status= lC.nodeFromKey(recievedata,"<n4:firstName xmlns:n4=\"http://xml.comcast.com/common/types\">","</n4:firstName><n5:suffix xsi:nil=\"true\" xmlns:n5=\"http://xml.comcast.com/common/types\" />");
		 	             Application.showMessage("First Name is::"+firstName_Cust); 

		 	            
		            break;
		            }
	             }
	         }
	         if(v1*callFound==1)
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






	public void processHomeSecurityInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	    
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
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	          //  rowmsg=rs.getString(1);
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
		            if(accountNumber_DDS.equals(c.getValue("accountNumber")))
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
		      
		 	            String firstName_DDS= lC.nodeFromKey(senddata,"<typesDDS:firstName>","</typesDDS:firstName>");
		 	            Application.showMessage("firstName is ::"+firstName_DDS);
		 	            
		 	           /* if(firstName_DDS==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				           // continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+firstName_DDS);
			            	 if(firstName_DDS.equals(c.getValue("FirstName")))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+firstName_DDS);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("FirstName at Send Xml not Validated as "+firstName_DDS);
				             }
			            }		*/

		 	            String lastName_DDS= lC.nodeFromKey(senddata,"<typesDDS:lastName>","</typesDDS:lastName>");
		 	            Application.showMessage("lastName is ::"+lastName_DDS); 
		 	            
		 	         /*  if(lastName_DDS==null)
			            {
				            c.report("Send Xml LastName is NULL");
				           // continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+lastName_DDS);
			            	 if(lastName_DDS.equals(c.getValue("LastName")))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
				            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+lastName_DDS);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("LastName at Send Xml not Validated as "+lastName_DDS);
				             }
			            }
*/
		 	            String homeSecurityLOBStatus_DDS= lC.nodeFromKey(senddata,"<typesDDS:homeSecurityLOBStatus>","</typesDDS:homeSecurityLOBStatus>");
		 	            Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
		 	            if(homeSecurityLOBStatus_DDS==null)
			            {
				            c.report(" homeSecurityLOBStatus is NULL");
				           // continue;
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
		 	           
		 	         	 	            
		 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:ProcessHomeSecurityInfoResponse xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
		 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
		 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
		 	            
		 	            if(recievedata.contains("Success</typ:responseStatus>"))
		 	            {
		 	            	Application.showMessage("Response XML contains the Success Status.!");
		 	            }
		 	                    

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(callFound*v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
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

	
	public void createAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	    
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Map<String, String> ResultMap = new HashMap<String,String>();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********createAccount_Validate Function***********");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	          //  rowmsg=rs.getString(1);
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
		            if(accountId_ucontrolsrv.equals(c.getValue("accountNumber")))
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

		 	           /* String firstName_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:firstName>","</ucontrolsrv:firstName>");
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
			            }	*/
		 	            
		 	          /*  String ucontrolsrv_province= lC.nodeFromKey(senddata,"<ucontrolsrv:province>","</ucontrolsrv:province>");
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
			            }	*/
		 	           
		 	          ResultMap = lC.splitter(c.getValue("dS_SIK", "Sik_Dotcom: Service"));
		 		      c.setValue("ExistingGroup",ResultMap.get("group"));
		 		      c.setValue("ExistingService", ResultMap.get("service"));
		 		      c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
		 	           
		 		      Application.showMessage("Service to be installed is::"+c.getValue("dS_SIK", "Sik_Dotcom: Service"));
		 	            if(c.getValue("IsMultiExist").equals("true"))
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
						            	 v2=1;
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
				            	 if(ucontrolsrv_group2.equals(c.getValue("ExistingService"))||ucontrolsrv_group2.equals(c.getValue("ExistingGroup")))
					             {
					            	 Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group2********");
					            	 Application.showMessage("Validation is Successfull with ucontrolsrv_group2::"+" "+ucontrolsrv_group2);
					            	 v2=1;
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
				            	 if(ucontrolsrv_group.equals(c.getValue("sSERVICEorCURRENTSERVICE")))
					             {
					            	 Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group********");
					            	 Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+ucontrolsrv_group);
					            	 v2=1;
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
				            	 v3=1;
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
	         
	         if(v1*callFound*v2*v3 ==1)
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

	
	
	public void submitOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********submitOrder_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' order by creation_time desc)where rownum <= 20");
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
			            
			    if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);   
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	
	            	String senddata_wf = sL.RemoveNameSpaces(senddata);
	            	Application.showMessage("Well-formed XML is ::"+senddata_wf);
	            	
	            	
		          	           
		            String sik_AccountNumber= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
	 	            Application.showMessage("accountNumber is ::"+sik_AccountNumber); 
	 	            
	 	            if(sik_AccountNumber==null)
	 	            {
	 	             continue;	
	 	            }
	 	            else if(sik_AccountNumber.equals(c.getValue("accountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+sik_AccountNumber);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		            	
		            	String HSDPrd_SIK=sL.GetValueByXPath(senddata_wf, "/SubmitOrder/Request/Products/Product/ProductCode");
		            	Application.showMessage("HSD Product is::"+HSDPrd_SIK);
		            	Application.showMessage("HSD Product from Input is::"+c.getValue("dS_SIK","Sik_Dotcom: HSDProdcutName"));
		            	String HSDprd= c.getValue("dS_SIK","Sik_Dotcom: HSDProdcutName");
		            	Application.showMessage("Input HSDprd Product is::"+HSDprd);
		            	
		            	 if(HSDPrd_SIK==null)
				            {
					            Application.showMessage(" HSDPrd_SIK is NULL");
					            continue;
				            }
				            else
				            {
				            	
				                 if(HSDPrd_SIK.trim().equals(HSDprd.trim()))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: WebServicall-HSDPrd_SIK********");
					            	 Application.showMessage("Validation is Successfull with HSDPrd_SIK::"+" "+HSDPrd_SIK);
					            	 v1=1;
					             }
				            	
					             else 
					             {
					            	Application.showMessage("HSDPrd_SIK at Send Xml not Validated as "+HSDPrd_SIK);
					            	//continue;
					             }
				            }
		            	
		            	String CDVPrd_SIK=sL.GetValueByXPath(senddata_wf, "/SubmitOrder/Request/Products/Product[2]/ProductCode");
		            	Application.showMessage("CDV Product is::"+CDVPrd_SIK);
		            	String CDVprd= c.getValue("dS_SIK","Sik_Dotcom: CDVProductName");
		            	Application.showMessage("Input CDV Product is::"+CDVprd);
		            	 if(CDVPrd_SIK==null)
				            {
					            Application.showMessage(" CDVPrd_SIK is NULL");
					            continue;
				            }
				            else
				            {
				            	 if(CDVPrd_SIK.trim().equals(CDVprd.trim()))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
					            	 Application.showMessage("Validation is Successfull with CDVPrd_SIK::"+" "+CDVPrd_SIK);
					            	 v2=1;
					             }
				            	
					             else 
					             {
					            	Application.showMessage("CDVPrd_SIK at Send Xml not Validated as "+CDVPrd_SIK);
					            	//continue;
					             }
				            }
		            	
		            	String VideoPrd_SIK=sL.GetValueByXPath(senddata_wf, "/SubmitOrder/Request/Products/Product[3]/ProductCode");
		            	Application.showMessage("Video Product is::"+VideoPrd_SIK);
		            	String Videoprd= c.getValue("dS_SIK","Sik_Dotcom: VideoProductName");
		            	 if(VideoPrd_SIK==null)
				            {
					            Application.showMessage(" VideoPrd_SIK is NULL");
					            //continue;
				            }
				            else
				            {
				            	 Application.showMessage("sik_Email from Send Xml  from VideoPrd_SIK is ::"+" "+VideoPrd_SIK);
				            	 if(VideoPrd_SIK.trim().equals(Videoprd.trim()))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: VideoPrd_SIK-VideoPrd_SIK********");
					            	 Application.showMessage("Validation is Successfull with VideoPrd_SIK::"+" "+VideoPrd_SIK);
					            	 v3=1;
					             }
				            	
					             else 
					             {
					            	Application.showMessage("VideoPrd_SIK at Send Xml not Validated as "+VideoPrd_SIK);
					            	//continue;
					             }
				            }
		            	
		            	String XHPrd_SIK=sL.GetValueByXPath(senddata_wf, "/SubmitOrder/Request/Products/Product[4]/ProductCode");
		            	Application.showMessage("XH Product is::"+XHPrd_SIK);
		            	String XHPrd= c.getValue("dS_SIK","Sik_Dotcom: XHSequipmentName");

		            	 if(XHPrd_SIK==null)
				            {
					            Application.showMessage(" XHPrd_SIK is NULL");
					            continue;
				            }
				            else
				            {
				            	
				            	 if(XHPrd_SIK.trim().equals(XHPrd.trim()))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: WebServicall-XHPrd_SIK********");
					            	 Application.showMessage("Validation is Successfull with XHPrd_SIK::"+" "+XHPrd_SIK);
					            	 v3=1;
					             }
				            	
					             else 
					             {
					            	Application.showMessage("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+XHPrd_SIK);
					            	//continue;
					             }
				            }
		            	   
		            	// String sik_Email= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");
			              String sik_Email= sL.nodeFromKey(senddata,"<sik:Email>","</sik:Email>");

			 	            Application.showMessage("sik_Email is ::"+sik_Email); 
			 	            if(sik_Email==null)
				            {
					            c.report(" sik_Email is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("sik_Email from Send Xml  from Input processHomeSecurityInfo_Validate is ::"+" "+c.getValue("dS_SIK","Sik_Dotcom: Email"));
				            	 if(sik_Email.trim().equalsIgnoreCase(c.getValue("dS_SIK","Sik_Dotcom: Email").trim()))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
					            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+sik_Email);
					            	 v3=1;
					             }
				            	
					             else 
					             {
					            	Application.showMessage("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+sik_Email);
					            	//continue;
					             }
				            }
			 	            
		 	           
		 	         	 	            
		 	            
		 	            String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
		 	            Application.showMessage("OrderID is::"+OrderID_sik); 
		 	            c.put("OrderID_SIK", OrderID_sik); 
		 	            Integer OrderId=Integer.parseInt(OrderID_sik);
		 	            sL.retOrderId(OrderId);
		 	            if(OrderID_sik==null)
		 	            {
		 	            	Application.showMessage("Order Id not created");
		 	            	c.report("ORDER ID NOT FOUND..!");
		 	            	
		 	            }
		 	      	 	            
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
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
	
	
	public void orderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********orderUpdate_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	           
	                          
		           
		            
			       // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
	            	
	            	

		            String ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
	 	            Application.showMessage("accountNumber is ::"+ou_AccountNumberid); 	
	 	            if(ou_AccountNumberid== null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(ou_AccountNumberid.equals(c.getValue("accountNumber")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		          
		            if(callFound==1)
		            {
		      
		 	            String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"otherDetails\">");
		 	            Application.showMessage("productType is ::"+ou_productType);
		 	            
		 	            if(ou_productType==null)
			            {
				            c.report("Send Xml productType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("productType Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ou_productType);
			            	 if(ou_productType.equalsIgnoreCase("SIK"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
				            	 Application.showMessage("Validation is Successfull with ou_productType::"+" "+ou_productType);
				            	 v1=1;
				             }
			            	 else if(ou_productType.equalsIgnoreCase("DOTCOM"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
				            	 Application.showMessage("Validation is Successfull with ou_productType::"+" "+ou_productType);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("ou_productType at Send Xml not Validated as "+ou_productType);
				             }
			            }		

		 	            String ou_billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"customerType\">");
		 	            Application.showMessage("billingSystem is ::"+ou_billingSystem); 
		 	            
		 	           if(ou_billingSystem==null)
			            {
				            c.report("Send Xml billingSystem is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("BillingSystem from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ou_billingSystem);
			            	 if(ou_billingSystem.equals(c.getValue("BillingSystem")))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
				            	 Application.showMessage("Validation is Successfull with billingSystem::"+" "+ou_billingSystem);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("billingSystem at Send Xml not Validated as "+ou_billingSystem);
				             }
			            }

		 	            String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"scheduledDate\">");
		 	            Application.showMessage("customerType is ::"+ou_customerType); 
		 	            if(ou_customerType==null)
			            {
				            c.report(" ou_customerType is NULL");
				            
			            }
			           
		 	            
		 	            String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"ordSource\">");
		 	            Application.showMessage("ordStatus is ::"+ordStatus); 
		 	           if(ordStatus==null)
			            {
				            c.report("Send Xml ordStatus is NULL");
				         
			            }
		 	            else if(ordStatus.equals("COM"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
			            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
			            	 v3=1;
			             }
		 	           else if(ordStatus.equals("INI"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
			            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
			            	 v3=1;
			             }
			             else
			             {
			            	 c.report("ordStatus at Send Xml not Validated as "+ordStatus);
			             }
		 	         	
		 	           
		 	           /* String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
		 	            Application.showMessage("ordType is ::"+ordType); 
		 	            if(ordType==null)
			            {
				            c.report("Send Xml ordType is NULL");
				            
			            }
		 	            else if(ordType.equals("NEW"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
			             }   */
		 	            
		 	           

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
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
	
	
	//-------------------------------------------------------------------------
	//
	//                              getMarketAvailability Call
	//
	//-------------------------------------------------------------------------
	
	
	
	
	public void getMarketAvailability_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, SAXException, XPathException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("--------------------------------------------------------------");
	     Application.showMessage("**********getMarketAvailability_Validate Function*************");       
	     Application.showMessage("--------------------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	       			 
		     rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/GetMarketAvailability' order by creation_time desc)where rownum <= 50");
	        
		     if(rs.equals(null))
		     {
		       Application.showMessage("No getMarketAvailability Call..Hence need to validate the sik_get_mrkt_avail_cache DataBase Table entries..!! ");
		     }
		     
		     else
		     {
		     while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1); 
		            String recievedata_wf=sL.RemoveNameSpaces(recievedata);
		            Application.showMessage(recievedata_wf);
		 		    String productCode1= sL.GetValueByXPath(recievedata_wf,"/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness[6]/AvailableProducts/AvailableProduct/AvailableProductComponent/AvailableProductComponent/EPCProductNumber");
                    Application.showMessage("prd Code is "+productCode1);
                    
                    int count1=recievedata_wf.split("<LineOfBusiness>").length;
              	    Application.showMessage("Count1 is "+count1);
              	    
              	 
		           
		            String HSDProdcut= (String) c.get("HSDProdcut");
		            if(HSDProdcut.isEmpty())
		            {
		            	Application.showMessage("HSDProdcut is empty for the current Product Catalouge");
		            }
		            else
		            {
		              if(recievedata.contains(HSDProdcut))
		              {
		            	Application.showMessage("HSDProdcut Code used in input and GETMARKET AVAILABILITY Found as "+HSDProdcut);
		            	
		            	 for(int i=1;i<count1;i++)
		              	  {
		            		 Application.showMessage("i is:"+i);
		              		 String LineOfBussinessXml= sL.GetXmlNodesByXPath(recievedata_wf,"/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness["+i+"]");
		              		 Application.showMessage("Line Of Bussiness XML at" +i+ "position is"+LineOfBussinessXml);
		              		 
		              		/* String LineXml= sL.GetXmlNodesByXPath(LineOfBussinessXml,"/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness["+i+"]");
		              		 Application.showMessage("LineXml Of Bussiness XML at" +i+ "position is"+LineXml);
		              		 */
		              		 int count2=LineOfBussinessXml.split("<AvailableProduct>").length;
		              	     Application.showMessage("Count2 is "+count2);
		              		 

		              		 for(int j=1;j<count2;j++)
		              		 {
		              		   Application.showMessage("j is:"+j);
		              		   String productCode= sL.GetValueByXPath(recievedata_wf,"/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness["+i+"]/AvailableProducts/AvailableProduct["+j+"]/AvailableProductComponent/AvailableProductComponent/EPCProductNumber");
		              		   Application.showMessage("product code at Lib"+productCode);
		              		   if(productCode.isEmpty())
		              		   {
		              			 Application.showMessage("Null data");
		              			 
		              			 
		              		   }
		              		   else if(productCode.equals(HSDProdcut))
		              		   {
		              		
		              		      String productName= sL.GetValueByXPath(recievedata_wf,"/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness["+i+"]/AvailableProducts/AvailableProduct["+j+"]/AvailableProductComponent/AvailableProductComponent/Name");
		                          Application.showMessage("Product from Getmarkat Availabilty call saved is "+productName);  
		              		   }
		              	    }
		              		
		              	  }
		            	//String HSDProdcut_gmk = sL.sikXhsproductValidation(c,input, recievedata_wf,"<LineOfBusiness>","<AvailableProduct>", HSDProdcut);
		            	//Application.showMessage("Product Saved is ::"+ HSDProdcut_gmk);
		              }
		            }
		            
		           /* callFound=1;
		            String VideoProduct= (String) c.get("VideoProduct");
		            if(VideoProduct.isEmpty())
		            {
		            	Application.showMessage("VideoProduct is empty for the current Product Catalouge");
		            }
		            else
		            {
		              if(recievedata.contains(VideoProduct))
		              {
		            	Application.showMessage("VideoProduct Code used in input and GETMARKET AVAILABILITY Found as "+VideoProduct);
		            	String VideoProduct_gmk = sL.sikXhsproductValidation(c,input,recievedata_wf,"<LineOfBusiness>","<AvailableProduct>",VideoProduct);
		            	Application.showMessage("Product Saved is ::"+ VideoProduct_gmk);
		              }
		            }
		            
		            
		            
		            String CDVProduct= (String) c.get("CDVProduct");
		            if(CDVProduct.isEmpty())
		            {
		            	Application.showMessage("CDVProduct is empty for the current Product Catalouge");
		            }
		            else
		            {
		              if(recievedata.contains(CDVProduct))
		              {
		            	Application.showMessage("CDVProduct Code used in input and GETMARKET AVAILABILITY Found as "+CDVProduct);
		            	String CDVProduct_gmk = sL.sikXhsproductValidation(c,input,recievedata_wf,"<LineOfBusiness>","<AvailableProduct>",CDVProduct);
		            	Application.showMessage("Product Saved is ::"+ CDVProduct_gmk);
		              }
		            }
		            
		            
		            String XHSequipment= (String) c.get("XHSequipment");
		            if(XHSequipment.isEmpty())
		            {
		            	Application.showMessage("XHSequipment is empty for the current Product Catalouge");
		            }
		            else
		            {
		              if(recievedata.contains(XHSequipment))
		              {
		            	Application.showMessage("XHSequipment Code used in input and GETMARKET AVAILABILITY Found as "+XHSequipment);
		            	String XHSequipment_gmk = sL.sikXhsproductValidation(c,input,recievedata_wf,"<LineOfBusiness>","<AvailableProduct>",XHSequipment);
		            	Application.showMessage("Product Saved is ::"+ XHSequipment_gmk);
		              }
		            }*/
		            
		    	            
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: getMarketAvailability_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getMarketAvailability_Validate is Failed with Validation Errors");
	        	 Application.showMessage("WebService Call :: getMarketAvailability_Validate is Failed[All validation points are Not Success]");
	         }
	         st.close();
		     }
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	
	public void getMarketAvailabilityModified_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, SAXException, XPathException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("--------------------------------------------------------------");
	     Application.showMessage("**********getMarketAvailability_Validate Function*************");       
	     Application.showMessage("--------------------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	       			 
		     rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/GetMarketAvailability' order by creation_time desc)where rownum <= 50");
	        
		     if(rs.equals(null))
		     {
		       Application.showMessage("No getMarketAvailability Call..Hence need to validate the sik_get_mrkt_avail_cache DataBase Table entries..!! ");
		     }
		     
		     else
		     {
		     while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1); 
		            String recievedata_wf=sL.RemoveNameSpaces(recievedata);
		            Application.showMessage(recievedata_wf);
		 		   
                    
              	 
		           
		            String HSDProdcut= (String) c.get("HSDProdcut");
		            if(HSDProdcut.isEmpty())
		            {
		            	Application.showMessage("HSDProdcut is empty for the current Product Catalouge");
		            }
		            else
		            {
		              if(recievedata.contains(HSDProdcut))
		              {
		            	Application.showMessage("HSDProdcut Code used in input and GETMARKET AVAILABILITY Found as "+HSDProdcut);
		            	
		            	String HSDProdcut_gmk=sL.GetValueByXPath(recievedata_wf, "/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness[8]/AvailableProducts/AvailableProduct/AvailableProductComponent/AvailableProductComponent/Name");
		            	
		            	//String HSDProdcut_gmk = sL.sikXhsproductValidation(c,input, recievedata_wf,"<LineOfBusiness>","<AvailableProduct>", HSDProdcut);
		            	Application.showMessage("Product Saved is ::"+ HSDProdcut_gmk);
		              }
		            }
		            
		            callFound=1;
		            String VideoProduct= (String) c.get("VideoProduct");
		            if(VideoProduct.isEmpty())
		            {
		            	Application.showMessage("VideoProduct is empty for the current Product Catalouge");
		            }
		            else
		            {
		              if(recievedata.contains(VideoProduct))
		              {
		            	Application.showMessage("VideoProduct Code used in input and GETMARKET AVAILABILITY Found as "+VideoProduct);
		            	String VideoProduct_gmk=sL.GetValueByXPath(recievedata_wf, "/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness[2]/AvailableProducts/AvailableProduct/AvailableProductComponent/AvailableProductComponent/Name");
		            	Application.showMessage("Product Saved is ::"+ VideoProduct_gmk);
		              }
		            }
		            
		            
		            
		            String CDVProduct= (String) c.get("CDVProduct");
		            if(CDVProduct.isEmpty())
		            {
		            	Application.showMessage("CDVProduct is empty for the current Product Catalouge");
		            }
		            else
		            {
		              if(recievedata.contains(CDVProduct))
		              {
		            	Application.showMessage("CDVProduct Code used in input and GETMARKET AVAILABILITY Found as "+CDVProduct);
		            	String CDVProduct_gmk=sL.GetValueByXPath(recievedata_wf, "/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness[6]/AvailableProducts/AvailableProduct[2]/AvailableProductComponent/AvailableProductComponent/Name");
		            	Application.showMessage("Product Saved is ::"+ CDVProduct_gmk);
		              }
		            }
		            
		            
		            String XHSequipment= (String) c.get("XHSequipment");
		            if(XHSequipment.isEmpty())
		            {
		            	Application.showMessage("XHSequipment is empty for the current Product Catalouge");
		            }
		            else
		            {
		              if(recievedata.contains(XHSequipment))
		              {
		            	Application.showMessage("XHSequipment Code used in input and GETMARKET AVAILABILITY Found as "+XHSequipment);
		            	String XHSequipment_gmk=sL.GetValueByXPath(recievedata_wf, "/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness/AvailableProducts/AvailableProduct[22]/ProductCode");
		            	Application.showMessage("Product Saved is ::"+ XHSequipment_gmk);
		              }
		            }
		            
		    	            
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: getMarketAvailability_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getMarketAvailability_Validate is Failed with Validation Errors");
	        	 Application.showMessage("WebService Call :: getMarketAvailability_Validate is Failed[All validation points are Not Success]");
	         }
	         st.close();
		     }
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	    //-------------------------------------------------------------------------
		//
		//                              getCustomer Call
		//
		//-------------------------------------------------------------------------

}
