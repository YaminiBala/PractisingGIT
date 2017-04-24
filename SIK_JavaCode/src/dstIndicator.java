import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class dstIndicator 
{
	sikLibraryClass lC=new sikLibraryClass();
	sikCSGENImsgClass sC=new sikCSGENImsgClass();
	sikXhs sX=new sikXhs();
	
	public void dstCalls(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathException, Exception 
	{
		if(c.get("pReqType").equals("S"))
		{
		Thread.sleep(20000);
		sC.csgENImsg_Validate(input, c);
		queryLocation_Validate(input, c);				
		sC.submitOrder_Validate(input, c);
		sC.orderUpdate_Validate(input, c);
		sX.processHomeSecurityInfo_Validate(input, c);
		createAccount_Validate(input, c);
		sX.orderUpdate_Validate(input, c);
		}
		else if(c.get("pReqType").equals("X"))
		{
			Application.showMessage("Cancel is processed...!!!");
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
		 	           

                         String Group0=c.getValue("SwivelCSG", "Group0");
                         Application.showMessage("Group 0 is ::"+Group0);
                         
                         String Group1=c.getValue("SwivelCSG", "Group1");
                         Application.showMessage("Group 1 is ::"+Group1);
                         
		 	           
		 		     
		 	            if(Group1.isEmpty()==false)
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
					            	 if(ucontrolsrv_group1.equals(Group0)||ucontrolsrv_group1.equals(Group1))
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
				            	 if(ucontrolsrv_group2.equals(Group0)||ucontrolsrv_group2.equals(Group1))
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
				            	 if(ucontrolsrv_group.equals(Group0))
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
			            	 if(errorCode_createAcc.equals("UCE-15103"))
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
		            Application.showMessage("Account Number from input is ::"+c.getValue("locationID"));
		            if(legacyID.equals(c.getValue("locationID")))
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
}
