import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.Context;
import com.parasoft.api.ScriptingContext;


public class sikDotcomClass 
{
	
	public int OutPut=1;
	public int trackcode=0;
	public void bfcRequest_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException, SQLException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 
		 int callFound=0,v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********bfcRequest_Validate Function**************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("bfcRequest"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("bfcRequest"));
	         
		try
		{
			//ResultSet Operation=sL.reduceThinkTimeSIK(input, c);
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:orderProcessorServicePort/bfcRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
			 rs=sL.reduceThinkTimeSIK(input, c);
	       
	         while (rs.next())
	         {	        
	            String rowmsg;
				rowmsg = rs.getString(1);
	            Application.showMessage("MessageID is::"+rowmsg);	            	
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
		            Application.showMessage("senddata is ::"+senddata); 
		            Application.showMessage("recievedata is ::"+recievedata); 
		            String receivedataTrim=sL.RemoveNameSpaces(recievedata);
		            Application.showMessage("receiveDataTrim:::"+receivedataTrim);
		            
		            //String accountNumber_DDS=sL.GetValueByXPath(recievedata,"/bfcopcom:msgInfo/bfcopcom:parameters/bfcopcom:parameters[2]/bfcopcom:value");
		            String serviceRequestID_bfc1= sL.nodeFromKey(recievedata,"<comt:serviceRequestID>","</comt:serviceRequestID>");
		            String serviceRequestID_bfc=sL.GetValueByXPath(receivedataTrim,"/msgInfo/ServiceRequest/serviceRequestID");
	 	            Application.showMessage("serviceRequestID_bfc is ::"+serviceRequestID_bfc);
	 	            Application.showMessage("serviceID from xl sheet::"+c.getValue("serviceRequestId"));
	 	            if(serviceRequestID_bfc==null)
		            {
			            c.report("Send Xml serviceRequestID is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(serviceRequestID_bfc.equals(c.getValue("serviceRequestId")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
		            		Application.showMessage("Validation is Successfull with Input serviceRequestID_bfc"+serviceRequestID_bfc);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            
		            
	            	
		            if(callFound==1)
		            {
		      
		            	String accountNumber_bfc1= sL.nodeFromKey(recievedata,"AccountNo</bfcopcom:name><bfcopcom:value>","</bfcopcom:value></bfcopcom:parameters><bfcopcom:parameters><bfcopcom:name>LocationID");
		            	String accountNumber_bfc=sL.GetValueByXPath(receivedataTrim, "/msgInfo/parameters/parameters[4]/value");
		 	            Application.showMessage("accountNumber is ::"+accountNumber_bfc); 	
		 	            if(accountNumber_bfc==null)
			            {
				            c.report("Send Xml accountNumber is NULL");
				            continue;
			            }
		 	            else
		 	            {
				            if(accountNumber_bfc.equals(c.getValue("accountNumber")))
			            	{
				            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
				            	Application.showMessage("SEND XML is :: \n"+senddata);
			            		//System.out.printf("SEND XML is %s \n",senddata);
			            		Application.showMessage("*******Validation Point 2 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
			            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_bfc);
			            	    v1=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            
		 	            }
		 	            
		 	            
		 	            if(recievedata.contains("billingSystem"))
		 	            {
		 	            	String billingSys=sL.nodeFromKey(recievedata, "billingSystem</bfcopcom:name><bfcopcom:value>", "</bfcopcom:value></bfcopcom:parameters><bfcopcom:parameters><bfcopcom:name>marketID");
		 	            	c.setValue("BillingSystem", billingSys);
		 	            }
		 	           String orderEntryOption_bfc= sL.nodeFromKey(recievedata,"<comt:orderEntryOption>","</comt:orderEntryOption>");
		 	           Application.showMessage("orderEntryOption is ::"+orderEntryOption_bfc); 
		 	           
		 	           String acquisitionMethod_bfc= sL.nodeFromKey(recievedata,"<comt:acquisitionMethod>","</comt:acquisitionMethod>");
		 	           Application.showMessage("acquisitionMethod  is ::"+acquisitionMethod_bfc); 	
		 	           
		 	           //-------------------------------------------------------------------//
		 	           //
		 	           //						Fetching Special Instruction Address.
		 	           //
		 	           //-------------------------------------------------------------------//
		 	           
		 	           
		 	           String Address1_bfc= sL.nodeFromKey(recievedata,"<comt:string>Address1:","</comt:string><comt:string>Address2:");
		 	           Application.showMessage("Address1 is ::"+Address1_bfc); 
		 	           c.put("SI_Add1", Address1_bfc);
		 	           
		 	           String Address2_bfc= sL.nodeFromKey(recievedata,"<comt:string>Address2:","</comt:string><comt:string>City:");
		 	           Application.showMessage("Address2 is ::"+Address2_bfc); 
		 	           c.put("SI_Add2", Address2_bfc);
		 	           
		 	           String City_bfc= sL.nodeFromKey(recievedata,"</comt:string><comt:string>City:","</comt:string><comt:string>State:");
		 	           Application.showMessage("City_bfc is ::"+City_bfc); 
		 	           c.put("SI_City", City_bfc);
		 	           
		 	           String State_bfc= sL.nodeFromKey(recievedata,"</comt:string><comt:string>State:","</comt:string><comt:string>Zip:");
		 	           Application.showMessage("State_bfc is ::"+State_bfc); 
		 	           c.put("SI_State", State_bfc);
		 	           
		 	           String Zip_bfc= sL.nodeFromKey(recievedata,"</comt:string><comt:string>Zip:","</comt:string></comt:specialInstruction><comt:serviceRequestSchedule>");
		 	           Application.showMessage("Zip is ::"+Zip_bfc); 
		 	           c.put("SI_Zip", Zip_bfc);
		 	           
		 	           String shipTogether_bfc= sL.nodeFromKey(recievedata,"<typ:shipTogether>","</typ:shipTogether>");
		 	           Application.showMessage("shipTogether is ::"+shipTogether_bfc); 
		 	           
		 	           String emailAddress_bfc= sL.nodeFromKey(recievedata,"<comt:emailAddress>","</comt:emailAddress>");
		 	           Application.showMessage("emailAddress is ::"+emailAddress_bfc); 
		 	           c.setValue("emailAddress", emailAddress_bfc);
		 	           
		 	           String comt_firstName= sL.nodeFromKey(recievedata,"<comt:firstName>","</comt:firstName>");
		 	           Application.showMessage("comt_firstName is ::"+comt_firstName); 
		 	           c.setValue("FirstName", comt_firstName);
		 	           
		 	           String comt_lastName= sL.nodeFromKey(recievedata,"<comt:lastName>","</comt:lastName>");
		 	           Application.showMessage("comt_lastName is ::"+comt_lastName); 
		 	           c.setValue("LastName", comt_lastName);
		 	           
		 	          String recieve_wf= sL.RemoveNameSpaces(recievedata);
                      Application.showMessage("Wellformed XML is ::"+recieve_wf);
                     String bfc_phone = sL.nodeFromKey(recievedata,"<comt:phoneNumber>","</comt:phoneNumber>");
                    //  String bfc_phone = sL.GetValueByXPath(recieve_wf, "/msgInfo/CustomerProfile/PhoneContact/telephoneNumber/phoneNumber");

                      Application.showMessage("bfc_phone is ::"+bfc_phone); 
                       
                      // String bfc_areaCode= sL.GetValueByXPath(recieve_wf, "/msgInfo/CustomerProfile/PhoneContact/telephoneNumber/areaCode");

                      String bfc_areaCode= sL.nodeFromKey(recievedata,"<comt:areaCode>","</comt:areaCode>");
                      Application.showMessage("bfc_areaCode is ::"+bfc_areaCode);
                      
                       String Cphone= bfc_areaCode.trim()+bfc_phone.trim();
                      Application.showMessage("Complete phone number is ::"+Cphone);
                      c.put("Cphone", Cphone);

		 	           
		 	           //-------------------------------------------------------------------//
		 	           //
		 	           //						Fetching Shipping Contact Address.
		 	           //
		 	           //-------------------------------------------------------------------//
		 	      
		 	           String ship_streetAddress= sL.nodeFromKey(recievedata,"<streetAddress>","</streetAddress>");
		 	           Application.showMessage("shipping_streetAddress is ::"+ship_streetAddress); 
		 	           c.put("SHIP_StreetAddress", ship_streetAddress);
		 	           
		 	           String ship_city= sL.nodeFromKey(recievedata,"<city>","</city>");
		 	           Application.showMessage("shipping_city is ::"+ship_city); 
		 	           c.put("SHIP_City", ship_city);
		 	           
		 	           String ship_state= sL.nodeFromKey(recievedata,"<state>","</state>");
		 	           Application.showMessage("ship_state is ::"+ship_state); 
		 	           c.put("SHIP_State", ship_state);
		 	           
		 	           String ship_zipCode= sL.nodeFromKey(recievedata,"<zipCode>","</zipCode>");
		 	           Application.showMessage("shipping_streetAddress is ::"+ship_zipCode); 
		 	           c.put("SHIP_ZipCode", ship_zipCode);
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: bfcRequest_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
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
	
	public void bfcFailureRequest_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v0=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********bfcRequest_Validate Function**************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("bfcRequest"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("bfcRequest")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:orderProcessorServicePort/bfcRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");

			 rs=sL.reduceThinkTimeSIK(input, c);
	       
	         while (rs.next())
	         {	        
	            String rowmsg;
				rowmsg = rs.getString(1);
	            Application.showMessage("MessageID is::"+rowmsg);	            	
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
		            Application.showMessage("senddata is ::"+senddata); 
		            Application.showMessage("recievedata is ::"+recievedata); 
		            
		            //String accountNumber_DDS=sL.GetValueByXPath(recievedata,"/bfcopcom:msgInfo/bfcopcom:parameters/bfcopcom:parameters[2]/bfcopcom:value");
		            String serviceRequestID_bfc= sL.nodeFromKey(recievedata,"<comt:serviceRequestID>","</comt:serviceRequestID>");
	 	            Application.showMessage("serviceRequestID_bfc is ::"+serviceRequestID_bfc); 	
	 	            if(serviceRequestID_bfc==null)
		            {
			            c.report("Send Xml serviceRequestID is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(serviceRequestID_bfc.equals(c.getValue("serviceRequestId")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
		            		Application.showMessage("Validation is Successfull with Input serviceRequestID_bfc"+serviceRequestID_bfc);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            
		            
	            	
		            if(callFound==1)
		            {
		            	Map<String, String> bfcSatausResponseMap=c.getGeneratedDataSourceValues();
		            	String bfcSatausResponse=bfcSatausResponseMap.get("bfcStatus");
		            	Application.showMessage("Status is ::"+bfcSatausResponse);
		            	if(bfcSatausResponse.equalsIgnoreCase("Failure"))
		            	{
		            		v0=1;
		            	}
		      
		            	String accountNumber_bfc= sL.nodeFromKey(recievedata,"AccountNo</bfcopcom:name><bfcopcom:value>","</bfcopcom:value></bfcopcom:parameters><bfcopcom:parameters><bfcopcom:name>LocationID");
		 	            Application.showMessage("accountNumber is ::"+accountNumber_bfc); 	
		 	            if(accountNumber_bfc==null)
			            {
				            c.report("Send Xml accountNumber is NULL");
				            continue;
			            }
		 	            else
		 	            {
				            if(accountNumber_bfc.equals(c.getValue("accountNumber")))
			            	{
				            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
				            	Application.showMessage("SEND XML is :: \n"+senddata);
			            		//System.out.printf("SEND XML is %s \n",senddata);
			            		Application.showMessage("*******Validation Point 2 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
			            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_bfc);
			            	    v1=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            
		 	            }
		 	            
		 	            
		 	            if(recievedata.contains("billingSystem"))
		 	            {
		 	            	String billingSys=sL.nodeFromKey(recievedata, "billingSystem</bfcopcom:name><bfcopcom:value>", "</bfcopcom:value></bfcopcom:parameters><bfcopcom:parameters><bfcopcom:name>marketID");
		 	            	c.setValue("BillingSystem", billingSys);
		 	            }
		 	           String orderEntryOption_bfc= sL.nodeFromKey(recievedata,"<comt:orderEntryOption>","</comt:orderEntryOption>");
		 	           Application.showMessage("orderEntryOption is ::"+orderEntryOption_bfc); 
		 	           
		 	           String acquisitionMethod_bfc= sL.nodeFromKey(recievedata,"<comt:acquisitionMethod>","</comt:acquisitionMethod>");
		 	           Application.showMessage("acquisitionMethod  is ::"+acquisitionMethod_bfc); 	
		 	           
		 	           //-------------------------------------------------------------------//
		 	           //
		 	           //						Fetching Special Instruction Address.
		 	           //
		 	           //-------------------------------------------------------------------//
		 	           
		 	           
		 	           String Address1_bfc= sL.nodeFromKey(recievedata,"<comt:string>Address1:","</comt:string><comt:string>Address2:");
		 	           Application.showMessage("Address1 is ::"+Address1_bfc); 
		 	           c.put("SI_Add1", Address1_bfc);
		 	           
		 	           String Address2_bfc= sL.nodeFromKey(recievedata,"<comt:string>Address2:","</comt:string><comt:string>City:");
		 	           Application.showMessage("Address2 is ::"+Address2_bfc); 
		 	           c.put("SI_Add2", Address2_bfc);
		 	           
		 	           String City_bfc= sL.nodeFromKey(recievedata,"</comt:string><comt:string>City:","</comt:string><comt:string>State:");
		 	           Application.showMessage("City_bfc is ::"+City_bfc); 
		 	           c.put("SI_City", City_bfc);
		 	           
		 	           String State_bfc= sL.nodeFromKey(recievedata,"</comt:string><comt:string>State:","</comt:string><comt:string>Zip:");
		 	           Application.showMessage("State_bfc is ::"+State_bfc); 
		 	           c.put("SI_State", State_bfc);
		 	           
		 	           String Zip_bfc= sL.nodeFromKey(recievedata,"</comt:string><comt:string>Zip:","</comt:string></comt:specialInstruction><comt:serviceRequestSchedule>");
		 	           Application.showMessage("Zip is ::"+Zip_bfc); 
		 	           c.put("SI_Zip", Zip_bfc);
		 	           
		 	           String shipTogether_bfc= sL.nodeFromKey(recievedata,"<typ:shipTogether>","</typ:shipTogether>");
		 	           Application.showMessage("shipTogether is ::"+shipTogether_bfc); 
		 	           
		 	           String emailAddress_bfc= sL.nodeFromKey(recievedata,"<comt:emailAddress>","</comt:emailAddress>");
		 	           Application.showMessage("emailAddress is ::"+emailAddress_bfc); 
		 	           c.setValue("emailAddress", emailAddress_bfc);
		 	           
		 	           String comt_firstName= sL.nodeFromKey(recievedata,"<comt:firstName>","</comt:firstName>");
		 	           Application.showMessage("comt_firstName is ::"+comt_firstName); 
		 	           c.setValue("FirstName", comt_firstName);
		 	           
		 	           String comt_lastName= sL.nodeFromKey(recievedata,"<comt:lastName>","</comt:lastName>");
		 	           Application.showMessage("comt_lastName is ::"+comt_lastName); 
		 	           c.setValue("LastName", comt_lastName);
		 	           
		 	          String recieve_wf= sL.RemoveNameSpaces(recievedata);
                      Application.showMessage("Wellformed XML is ::"+recieve_wf);
                     //String bfc_phone = sL.nodeFromKey(recievedata,"<comt:phoneNumber>","</comt:phoneNumber>");
                      String bfc_phone = sL.GetValueByXPath(recieve_wf, "/msgInfo/CustomerProfile/PhoneContact/telephoneNumber/phoneNumber");

                      Application.showMessage("bfc_phone is ::"+bfc_phone); 
                       
                       String bfc_areaCode= sL.GetValueByXPath(recieve_wf, "/msgInfo/CustomerProfile/PhoneContact/telephoneNumber/areaCode");

                      //String bfc_areaCode= sL.nodeFromKey(recievedata,"<comt:areaCode>","</comt:areaCode>");
                      Application.showMessage("bfc_areaCode is ::"+bfc_areaCode);
                      
                       String Cphone= bfc_areaCode.trim()+bfc_phone.trim();
                      Application.showMessage("Complete phone number is ::"+Cphone);
                      c.put("Cphone", Cphone);

		 	           
		 	           //-------------------------------------------------------------------//
		 	           //
		 	           //						Fetching Shipping Contact Address.
		 	           //
		 	           //-------------------------------------------------------------------//
		 	      
		 	           String ship_streetAddress= sL.nodeFromKey(recievedata,"<streetAddress>","</streetAddress>");
		 	           Application.showMessage("shipping_streetAddress is ::"+ship_streetAddress); 
		 	           c.put("SHIP_StreetAddress", ship_streetAddress);
		 	           
		 	           String ship_city= sL.nodeFromKey(recievedata,"<city>","</city>");
		 	           Application.showMessage("shipping_city is ::"+ship_city); 
		 	           c.put("SHIP_City", ship_city);
		 	           
		 	           String ship_state= sL.nodeFromKey(recievedata,"<state>","</state>");
		 	           Application.showMessage("ship_state is ::"+ship_state); 
		 	           c.put("SHIP_State", ship_state);
		 	           
		 	           String ship_zipCode= sL.nodeFromKey(recievedata,"<zipCode>","</zipCode>");
		 	           Application.showMessage("shipping_streetAddress is ::"+ship_zipCode); 
		 	           c.put("SHIP_ZipCode", ship_zipCode);
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*v0*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: bfcRequest_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
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
	
	



	
	
	
	public void queryContract_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(27000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("Time:::"+Time);
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********queryContract_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("queryContract"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("queryContract"));
		try
		{
		  // Statement st =sL. dBConnect(input, c);	
	        
			//rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ContractServices:ContractServicePort/queryContract' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
			 rs=sL.reduceThinkTimeSIK(input, c);
	       
			while (rs.next())
	         {
	        	
	        	    String rowmsg;
					rowmsg = rs.getString(1);
		            Application.showMessage("MessageID is::"+rowmsg);
		        
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
		          	           
		            String accountNumber_QC= sL.nodeFromKey(senddata,"<ContractTypes:accountNumber>","</ContractTypes:accountNumber>");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_QC); 
	 	            if (accountNumber_QC==null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(accountNumber_QC.equals(c.getValue("accountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_QC);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	           // String maxRows= sL.GetValueByXPath(senddata,"/ContractServices:queryContract/ContractServices:resultSpec/ContractTypes:maxRows");
		            	String maxRows = sL.nodeFromKey(senddata,"<ContractTypes:maxRows>","</ContractTypes:maxRows>");

		            	Application.showMessage("maxRows is ::"+maxRows);
		 	            
		 	            if(maxRows==null)
			            {
				            c.report("Send Xml maxRows is NULL");
				            continue;
			            }
		            	/*String response = sL.nodeFromKey(recievedata,"</typ:termAspectValue><typ:response>","</typ:response><typ:finalTermText");
		            	Application.showMessage("response is ::"+response);

		            	if(response==null)
			            {
				            c.report("Send Xml response is NULL");
				            continue;
			            }
		            	
		            	else if(response.equalsIgnoreCase("ACCEPTED"))
		            	{
		            		Application.showMessage("response is Validated as ::"+response);
		            	}
		            	else
		            	{
		            		c.report("Response Not validated");
		            	}*/

		 	            
		            break;
		            }
	             }
	         }
			 
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: queryContract_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::queryContract_Validate is Failed with Validation Errors");
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
	
	
	public void oneStopSik_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	   
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********oneStopSik_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("OneStopSIK"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("OneStopSIK"));
		try
		{
		 //  Statement st =sL. dBConnect(input, c);	
	        
			//rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:oneStopSik/processOneStopSIK' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTimeMSL")+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	       
			while (rs.next())
	         {        	
	        	    String rowmsg;
	        	    
					rowmsg = rs.getString(1);
		            Application.showMessage("MessageID is::"+rowmsg);
		        
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);	            	
	            	String senddata_wf=sL.RemoveNameSpaces(senddata);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata_wf);
	            	String AccountNumber=sL.GetValueByXPath(senddata_wf, ".//parameters[4]/value");
                    int v1= sL.validationPoint(AccountNumber, c.getValue("accountNumber"), input, c);
                    if(v1==1)
                    {
                    	Application.showMessage("OneStopSIK is present...!");
                    	Application.showMessage("OneStopSIK found after MSL Call!");
                        
                    }
                    else
                    {
                    	c.report("Not Found OnestopSIK...!");
                    }
	            }
	            else
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	String recievedata_wf=sL.RemoveNameSpaces(recievedata);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata_wf); 
	            	String AccountNumber=sL.GetValueByXPath(recievedata_wf, ".//parameters[4]/value");
                    int v1= sL.validationPoint(AccountNumber, c.getValue("accountNumber"), input, c);
                    if(v1==1)
                    {
                    	Application.showMessage("OneStopSIK is present...!");
                    	callFound=1;
                    }
                    else
                    {
                    	c.report("Not Found OnestopSIK...!");
                    }
	            
	            }
	            
	         }
			 
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: oneStopSIK is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::oneStopSIK is Failed with Validation Errors");
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
	
	public void getLocation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********getLocation_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("getLocation"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getLocation"));

	         
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/getLocation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 50");
			 rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
		         Application.showMessage("MessageID is::"+rowmsg);
		           			            
			  
			    
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
	            	String locationID=c.getValue("locationID");
	            	Application.showMessage("Locataion ID from xl Sheet::"+locationID);
		          	           
		            String GT_loc= sL.nodeFromKey(senddata,"<ls:GetLocation><lt:locationID>","</lt:locationID></ls:GetLocation></ls:getLocation>");
	 	            Application.showMessage("Location ID is ::"+GT_loc); 
	 	            if (GT_loc==null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(GT_loc.equals(c.getValue("locationID")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with locationID"+GT_loc);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	            String locationStatus_GT= sL.nodeFromKey(recievedata,"<typ:locationStatus xmlns:typ=\"http://xml.comcast.com/location/types\">","</typ:locationStatus>");
		 	            Application.showMessage("locationStatus is ::"+locationStatus_GT);
		 	            
		 	           
		 	            
		 	            String LegacyLocationID_GT= sL.nodeFromKey(recievedata,"<typ:legacyLocationIDType><typ:LegacyLocationID>","</typ:LegacyLocationID><typ:LegacyLocationIDSource>");
		 	            Application.showMessage("LegacyLocationID_GT is ::"+LegacyLocationID_GT); 
		 	            
		 	            String headendNetworkAddress_GT= sL.nodeFromKey(recievedata,"<typ:headendNetworkAddress>","</typ:headendNetworkAddress>");
		 	            Application.showMessage("headendNetworkAddress_GT is ::"+headendNetworkAddress_GT); 		 	            
		 	            c.put("GL_headendNetworkAddress", headendNetworkAddress_GT);
		 	            
		 	               //-------------------------------------------------------------------//
			 	           //
			 	           //						Fetching Get Location Address.
			 	           //
			 	           //-------------------------------------------------------------------//
		 	            
		 	       
		 	            String city_GT= sL.nodeFromKey(recievedata,"<typ:city>","</typ:city>");
		 	            Application.showMessage("city_GT is ::"+city_GT);
		 	            c.put("GL_City", city_GT);
		 	            
		 	            String houseNumber_GT= sL.nodeFromKey(recievedata,"</typ:houseNumberPrefix><typ:houseNumber>","</typ:houseNumber>");
		 	            Application.showMessage("houseNumber_GT is::"+houseNumber_GT);
		 	            c.put("GL_HouseNumber", houseNumber_GT);
		 	           
		 	            String streetSuffix_GT= sL.nodeFromKey(recievedata,"<typ:streetSuffix>","</typ:streetSuffix><typ:streetPostDirection");
		 	            Application.showMessage("streetSuffix_GT is ::"+streetSuffix_GT); 
		 	            c.put("GL_streetSuffix", streetSuffix_GT);
		 	            
		 	            String streetName_GT= sL.nodeFromKey(recievedata,"<typ:streetName>","</typ:streetName>");
		 	            Application.showMessage("streetName_GT is ::"+streetName_GT); 
		 	            c.put("GL_streetName", streetName_GT);
		 	            
		 	            String streetPreDirection_GT=null;
		 	            
                        if(recievedata.contains("<typ:streetPreDirection xsi:nil=\"true\">"))
                        {
			 	           streetPreDirection_GT= sL.nodeFromKey(recievedata,"<typ:streetPreDirection xsi:nil=\"true\">","</typ:streetPreDirection>");
			 	            Application.showMessage("streetPreDirection is ::"+streetPreDirection_GT); 
			 	            if(streetPreDirection_GT.isEmpty())
			 	            {
			 	            	c.put("GL_streetPreDirection", "");	
			 	            }
			 	            else
			 	            {
			 	            c.put("GL_streetPreDirection", streetPreDirection_GT);
			 	            }
                        }
                        else  if(recievedata.contains("<typ:streetPreDirection>"))
                        {
			 	            streetPreDirection_GT= sL.nodeFromKey(recievedata,"<typ:streetPreDirection>","</typ:streetPreDirection>");
			 	            Application.showMessage("streetPreDirection is ::"+streetPreDirection_GT); 
			 	            if(streetPreDirection_GT.isEmpty())
			 	            {
			 	            	c.put("GL_streetPreDirection", "");	
			 	            }
			 	            else
			 	            {
			 	            c.put("GL_streetPreDirection", streetPreDirection_GT);
			 	            }
                        }
                        
                        else
                        {
                        	Application.showMessage("Recieve Data doesnt Contain the Street Predirecrtion TAG...!! Error...!Tag Missing..!");
                        }
		 	            
		 	            
		 	            //----------------------------------------------------------------------------------
		 	            //
		 	            //
		 	            //              Making Address1
		 	            //
		 	            //----------------------------------------------------------------------------------
		 	            
		 	             String hno_GT=houseNumber_GT.trim();
		 	             String strtpre_GT=streetPreDirection_GT;
		 	             String strtname_GT=streetName_GT.trim();
		 	             String strtSuf_GT=streetSuffix_GT.trim();
		 	             if(strtpre_GT.equals(""))
		 	             {
		 	            	Application.showMessage("strtpre_GT is null");
		 	            	strtpre_GT= strtpre_GT.concat(" ");
		 	             }
		 	            
		 	            
		 	             String StreetName_Location = hno_GT.concat("  ").concat(strtpre_GT).concat(strtname_GT).concat("  ").concat(strtSuf_GT);
		 	             Application.showMessage("StreetName is ::"+StreetName_Location);
		 	             c.put("StreetName_Loc", StreetName_Location);
		 	            
		 	           
		 	            
		 	            String state_GT= sL.nodeFromKey(recievedata,"<typ:state>","</typ:state>");
		 	            Application.showMessage("state_GT is ::"+state_GT); 
		 	            c.put("GL_state", state_GT);
		 	            
		 	            String zip5_GT= sL.nodeFromKey(recievedata,"<typ:zip5>","</typ:zip5>");
		 	            Application.showMessage("zip5_GT is ::"+zip5_GT); 
		 	            c.put("GL_zip5", zip5_GT);
		 	           
		 	            String zip4= sL.nodeFromKey(recievedata,"<typ:zip4>","</typ:zip4>");
		 	            Application.showMessage("zip4 is ::"+zip4);
		 	            c.put("GL_zip4", zip4);
		 	         	 	            
		 	          
		 	         
		 	           
		 	            
		 	            String headendType_GT= sL.nodeFromKey(recievedata,"<typ:headendType>","</typ:headendType>");
		 	            Application.showMessage("headendType is ::"+headendType_GT); 
		 	            c.put("GL_headendType", headendType_GT);
		 	      		 	            
		 	             String typ_LegacyLocationID= sL.nodeFromKey(recievedata,"<typ:LegacyLocationID>","</typ:LegacyLocationID>");
		 	             Application.showMessage("LegacyLocationID is ::"+typ_LegacyLocationID);
		 	             c.setValue("LegacyLocationID_GT", typ_LegacyLocationID);
		 	             
		 	           if(recievedata.contains("<typ:Agent>")) 
		 	           {
		 	        	    String FranchiseTaxArea= sL.nodeFromKey(recievedata,"<typ:Agent>","</typ:Agent>");
			 	            Application.showMessage("FranchiseTaxArea is ::"+FranchiseTaxArea); 
			 	            c.put("GL_FranchiseTaxArea", FranchiseTaxArea);  
		 	           }
		 	           else if(recievedata.contains("<typ:FranchiseTaxArea>"))		 	        	   
		 	           {
		 	        	    String ftaAgent= sL.nodeFromKey(recievedata,"<typ:FranchiseTaxArea>","</typ:FranchiseTaxArea>");
			 	            Application.showMessage("ftaAgent is ::"+ftaAgent); 
			 	            c.put("GL_ftaAgent", ftaAgent);  
		 	           }
		 	           
		 	           else if(recievedata.contains("</typ:AccountCorp><typ:FranchiseTaxArea>"))
		 	           {
		 	        	
		 	        	    String FranchiseTaxArea= sL.nodeFromKey(recievedata,"</typ:AccountCorp><typ:FranchiseTaxArea>","</typ:FranchiseTaxArea>");
			 	            Application.showMessage("FranchiseTaxArea is ::"+FranchiseTaxArea); 
			 	            c.put("GL_FranchiseTaxArea", FranchiseTaxArea);
			 	       }
		 	           
		 	           else
		 	           {
		 	        	  Application.showMessage("No FTA Found...!!"); 
		 	           }
		 	            
		 	          
		 	            
		 	            
		 	              
		            break;
		            }
	             }
	         }
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: getLocation_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getLocation_Validate is Failed with Validation Errors");
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
	public void getLocation_Validate_NegativeCalls(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 int v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********getLocation_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("getLocation"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getLocation"));
	     
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/getLocation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);   
	       
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
		         Application.showMessage("MessageID is::"+rowmsg);
		           			            
			  
			    
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
		          	           
		            String GT_loc= sL.nodeFromKey(senddata,"<ls:GetLocation><lt:locationID>","</lt:locationID></ls:GetLocation></ls:getLocation>");
	 	            Application.showMessage("Location ID is ::"+GT_loc); 
	 	            if (GT_loc==null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(GT_loc.equals(c.getValue("locationID")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with locationID"+GT_loc);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            { 
		            	String code =  sL.nodeFromKey(recievedata,"<cct:code>","</cct:code>");
		            	if(code.equalsIgnoreCase("LOC-QL-401"))
		            	{
		            		v1=1;
		            		Application.showMessage("Location services validated");
		            	}
		            	else
		            	{
		            		c.report("Location ID not validated, please check details");
		            	}
		            }
		            break;     
	             
	            }
	         
	         if(callFound ==1 )
	         {
	         	Application.showMessage("WebService Call :: getLocation_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getLocation_Validate is Failed with Validation Errors");
	         }
	       //  st.close();
	            }
	              
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}

	
	
	
	public void submitOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException, ParserConfigurationException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Context AppContext =Application.getContext();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********submitOrder_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SubmitOrder"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SubmitOrder"));

		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	        
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
		         Application.showMessage("MessageID is::"+rowmsg);
		           
			            
			   
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
		            	 
		            	if(recievedata.contains("<OrderID")) 	
		 	         	{
		 	            
			 	            String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
			 	            Application.showMessage("OrderID is::"+OrderID_sik); 
			 	 
			 	             c.setValue("OrderID",OrderID_sik );
			 	            //c.put("OrderID_SIK", OrderID_sik); 
			 	            //Integer OrderId=Integer.parseInt(OrderID_sik);
			 	           // sL.retOrderId(OrderId);
		 	         	}
		 	         	
		 	         	else if(recievedata.contains("<cct:code>Order-SubmitOrder-213</cct:code>")||recievedata.contains("<cct:code>Order-SubmitOrder-118</cct:code>"))
		 	            {
		 	         		String receivedataTrim = null;
		                	   String receiveTrim=sL.RemoveNameSpaces(recievedata);
		                	   if(receiveTrim.contains(">-"))
		 	            	{
		                		    receivedataTrim=receiveTrim.replaceAll(">-",">");
		 	            	}
		 	            	else
		 	            	{
		                		   receivedataTrim= receiveTrim;
		 	            	}
		                	   Application.showMessage("receiveDataTrim:::"+receiveTrim);
		                	   Application.showMessage("receiveDataTrim after modification:::"+receivedataTrim);
			                   String orderid_modify= sL.nodeFromKey(receivedataTrim,"</code><text>Duplicate Order - Single Not Exact Match: Duplicate order detected; matched order #","</text>");
                        Application.showMessage("Modify Order ID found is :: "+orderid_modify);
                        c.put("MOrderID", orderid_modify);
                        c.put("OrderID_SIK", orderid_modify);
                          }
		 	            
		 	         	else
		 	         	{
		 	         		c.report("ORDER ID NOT FOUND..!");
		 	            }
		            	//-----****Validation for Trackcode*****--------------
                        
                        List<String> retreiveTrackcodes=sL.getRateCodesforDotComTrac(input, c);
                        
                        if(retreiveTrackcodes.size()>0)
                         {
                       	 Application.showMessage("**********Validation Point :: KIT Code - OT0PD***********");
                        int TrackValue=sL.getTrackingDetails(input, c,senddata);
                        Application.showMessage("TrackValue:::"+TrackValue);
                        //List<String> retrieveTrackValues=sL.extractValue(TrackValue, input, c);
                        if(TrackValue==(retreiveTrackcodes.size()))
                        {
                       	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                       	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                       	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are equal");
                       	 Application.showMessage("----****Validation is successful with the Track code OTOPD and Reason Xi3_special****----" );
                       	 trackcode=1;
                        }
                        else
                        {
                       	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                       	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                       	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are not equal");
                       	 Application.showMessage("----****Validation is not successful with the Track code OTOPD and Reason Xi3_special****----" );
                       	
                        }
                        }
                        else
                        {
                       	 trackcode=1;
                        }


		 	      	 	            
		            	
		            	
		            	if(senddata.contains("<sik:E911_acceptance>1</sik:E911_acceptance>"))
		            	{
		            		Application.showMessage("E911 acceptance Validated as 1 for CDV Order");
		            	}
		            	else if(senddata.contains("<sik:E911_acceptance>0</sik:E911_acceptance>"))
		            	{
		            		Application.showMessage("E911 acceptance Validated as 0 for CDV Order");
		            	}
		            	
		            	else
		            	{
		            		Application.showMessage("Is not an CDV Order");

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
				            	 Application.showMessage("sik_Email from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+c.getValue("emailAddress").toString());
				            	 if(sik_Email.equalsIgnoreCase(c.getValue("emailAddress")))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: WebServicall-emailAddress********");
					            	 Application.showMessage("Validation is Successfull with emailAddress::"+" "+sik_Email);
					            	 
					             }
				            	
					             else 
					             {
					            	Application.showMessage("emailAddress at Send Xml not Validated as "+sik_Email);
					            	
					             }
				            }
			 	            
		 	            String sik_FirstName= sL.nodeFromKey(senddata,"<sik:FirstName>","</sik:FirstName>");
		 	            Application.showMessage("firstName is ::"+sik_FirstName);
		 	            
		 	            if(sik_FirstName==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+sik_FirstName);
			            	 if(sik_FirstName.equals(c.getValue("FirstName")))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_FirstName);
				            	 v1=1;
				             }
				             else
				             {
				            	 v1=1;
				            	// c.report("FirstName at Send Xml not Validated as "+sik_FirstName);
				             }
			            }		

		 	            String sik_LastName= sL.nodeFromKey(senddata,"<sik:LastName>","</sik:LastName>");
		 	            Application.showMessage("lastName is ::"+sik_LastName); 
		 	            
		 	           if(sik_LastName==null)
			            {
				            c.report("Send Xml LastName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+sik_LastName);
			            	 if(sik_LastName.equals(c.getValue("LastName")))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
				            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_LastName);
				            	 v2=1;
				             }
				             else
				             {
				            	 v2=1;
				            	// c.report("LastName at Send Xml not Validated as "+sik_LastName);
				             }
			            }

		 	           
		 	            
		 	            String sik_ShipmentPriority= sL.nodeFromKey(senddata,"<sik:ShipmentPriority>","</sik:ShipmentPriority>");
		 	            Application.showMessage("ShipmentPriority is ::"+sik_ShipmentPriority); 
		 	            
		 	            String sik_FTA= sL.nodeFromKey(senddata,"<sik:FTA>","</sik:FTA>");
		 	            Application.showMessage("sik_FTA is ::"+sik_FTA);
		 	            
		 	            
		 	            Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));

		 	            Application.showMessage("FTA from Get Loc" +c.get("GL_ftaAgent"));
		 	            //Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
		 	            
		 	            
		 	            
		 	         

		 	           if(c.getValue("BillingSystem").equalsIgnoreCase("DDP"))
		 	            {
		 	            	
		 	               if(((String)c.get("GL_ftaAgent")).trim().equals("0".concat(sik_FTA.trim())))
		 	               {
		 	            	   Application.showMessage("FTA is been Validated with Get Location ftaAgent As Flow is DDP ::"+sik_FTA);
		 	               }
		 	               else
		 	               {
		 	            	   c.report("FTA is NOT been Validated with Get Location ftaAgent As Flow is DDP ::"+sik_FTA);
 
		 	               }
		 	            }
		 	            else if(c.getValue("BillingSystem").equalsIgnoreCase("CSG"))
		 	            {
		 	            	if((((String) c.get("GL_FranchiseTaxArea")).trim()).equals(sik_FTA.trim()))
			 	               {
			 	            	   Application.showMessage("FTA is been Validated with Get Location FranchiseTaxArea As Flow is CSG ::"+sik_FTA);
			 	               }
			 	               else
			 	               {
			 	            	   c.report("FTA is NOT been Validated with Get Location FranchiseTaxArea As Flow is CSG ::"+sik_FTA);
	 
			 	               }
		 	            }
		 	            
		 	            String sik_Address1= sL.nodeFromKey(senddata,"<sik:Address1>","</sik:Address1>");
		 	            Application.showMessage("sik_Address1 is ::"+sik_Address1); 
		 	            
		 	            String sik_Address2= sL.nodeFromKey(senddata,"<sik:Address2>","</sik:Address2>");
		 	            Application.showMessage("sik_Address2 is ::"+sik_Address2); 
		 	            
		 	            String sik_City= sL.nodeFromKey(senddata,"<sik:City>","</sik:City>");
		 	            Application.showMessage("sik_City is ::"+sik_City); 
		 	            
		 	            String sik_State= sL.nodeFromKey(senddata,"<sik:State>","</sik:State>");
		 	            Application.showMessage("sik_State is ::"+sik_State); 
		 	            
		 	            String sik_Zip= sL.nodeFromKey(senddata,"<sik:Zip>","</sik:Zip>");
		 	            Application.showMessage("sik_Zip is ::"+sik_Zip); 
		 	            
		 	            String sik_IsAlternateAddress= sL.nodeFromKey(senddata,"<sik:IsAlternateAddress>","</sik:IsAlternateAddress>");
		 	            Application.showMessage("sik_IsAlternateAddress is ::"+sik_IsAlternateAddress); 
		 	            
		 	            //-------------------------------------------------------------------------------------------------------//
		 	            //
		 	            //       					   SIK Address validation module with Alternate address Logic
		 	            //
		 	            //-------------------------------------------------------------------------------------------------------//
		 	            
		 	            if(sik_IsAlternateAddress.equals("1"))
		 	            {
		 	            	if(c.get("SI_Add1")==null || c.get("SI_Add2")==null || c.get("SI_City")==null || c.get("SI_State")==null || c.get("SI_Zip")==null )
		 	            	{
		 	            		if(((String) c.get("SHIP_StreetAddress")).equalsIgnoreCase(sik_Address1))
		 	            		{
		 	            		   Application.showMessage("Street Address of sik is Validated with Shipping Contact as ::" +sik_Address1);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("Street Address of sik is NOT Validated with Shipping Contact as ::" +sik_Address1);

		 	            		}
		 	            		
		 	            		if(((String) c.get("SHIP_City")).equalsIgnoreCase(sik_City))
		 	            		{
		 	            		   Application.showMessage("City of sik is Validated with Shipping Contact as ::" +sik_City);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("City of sik is NOT Validated with Shipping Contact as ::" +sik_City);

		 	            		}
		 	            		
		 	            		if(((String) c.get("SHIP_State")).equalsIgnoreCase(sik_State))
		 	            		{
		 	            		   Application.showMessage("State of sik is Validated with Shipping Contact as ::" +sik_State);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("State of sik is NOT Validated with Shipping Contact as ::" +sik_State);

		 	            		}
		 	            		
		 	            		if(((String) c.get("SHIP_ZipCode")).equalsIgnoreCase(sik_Zip))
		 	            		{
		 	            		   Application.showMessage("ZipCode of sik is Validated with Shipping Contact as ::" +sik_Zip);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("ZipCode of sik is NOT Validated with Shipping Contact as ::" +sik_Zip);

		 	            		}
		 	            	}
		 	            	
		 	            	else
		 	            	{
		 	            	   
		 	            		if(((String) c.get("SI_Add1")).equalsIgnoreCase(sik_Address1))
		 	            		{
		 	            		   Application.showMessage("Street Address of sik is Validated with Special Instruction as ::" +sik_Address1);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("Street Address of sik is NOT Validated with Special Instruction as ::" +sik_Address1);

		 	            		}
		 	            		
		 	            		if(((String) c.get("SI_City")).equalsIgnoreCase(sik_City))
		 	            		{
		 	            		   Application.showMessage("City of sik is Validated with Special Instruction as ::" +sik_City);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("City of sik is NOT Validated with Special Instruction as ::" +sik_City);

		 	            		}
		 	            		
		 	            		if(((String) c.get("SI_State")).equalsIgnoreCase(sik_State))
		 	            		{
		 	            		   Application.showMessage("State of sik is Validated with Shipping Contact as ::" +sik_State);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("State of sik is NOT Validated with Shipping Contact as ::" +sik_State);

		 	            		}
		 	            		
		 	            		if(((String) c.get("SI_Zip")).equalsIgnoreCase(sik_Zip))
		 	            		{
		 	            		   Application.showMessage("ZipCode of sik is Validated with Special Instruction as ::" +sik_Zip);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("ZipCode of sik is NOT Validated with Shipping Contact as ::" +sik_Zip);

		 	            		}
		 	            	}
		 	            }
		 	            else if(sik_IsAlternateAddress.equals("0"))  
		 	            {
		 	            	if(c.get("StreetName_Loc").toString().equalsIgnoreCase(sik_Address1.trim()))
	 	            		{
	 	            		   Application.showMessage("Street Address of sik is Validated with Get Location as ::" +sik_Address1);
	 	            		}
	 	            		else
	 	            		{
		 	            	 //c.report("Street Address of sik is NOT Validated with Get Location as ::" +sik_Address1);
	 	            			Application.showMessage("Street Address of sik is  not Validated with Get Location as ::" +sik_Address1);
	 	            		}
	 	            		
	 	            		if(((String) c.get("GL_City")).equalsIgnoreCase(sik_City))
	 	            		{
	 	            		   Application.showMessage("City of sik is Validated with Get Location as ::" +sik_City);
	 	            		}
	 	            		else
	 	            		{
		 	            	 c.report("City of sik is NOT Validated with Get Location as ::" +sik_City);

	 	            		}
	 	            		
	 	            		if(((String) c.get("GL_state")).equalsIgnoreCase(sik_State))
	 	            		{
	 	            		   Application.showMessage("State of sik is Validated with Get Location as ::" +sik_State);
	 	            		}
	 	            		else
	 	            		{
		 	            	 c.report("State of sik is NOT Validated with Get Locationt as ::" +sik_State);

	 	            		}
	 	            		
	 	            		if(((String) c.get("GL_zip5")).equalsIgnoreCase(sik_Zip))
	 	            		{
	 	            		   Application.showMessage("ZipCode of sik is Validated with Get Location as ::" +sik_Zip);
	 	            		}
	 	            		else
	 	            		{
		 	            	 c.report("ZipCode of sik is NOT Validated with Get Location as ::" +sik_Zip);

	 	            		}	
		 	            }
		 	            else
		 	            {
		 	            	c.report("SIK Address not Validated");	
		 	            }
		 	            
		 	            String sik_ProductCode= sL.nodeFromKey(senddata,"<sik:ProductCode>","</sik:ProductCode>");
		 	            Application.showMessage("sik_ProductCode is ::"+sik_ProductCode); 
		 	            
		 	           if(c.getValue("orderEntryOption").equalsIgnoreCase("OWN"))
		 	          {
		 	            
		 	            Boolean isverified=sL.modemVerification(sik_ProductCode,c,input);
		 	            if (isverified==true)
		 	            {
		 	            	Application.showMessage("Verified Modem as"+sik_ProductCode);
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("NOT Verified Modem as"+sik_ProductCode);
		 	            }
		 	             
		 	          } 
		 	            		 	            
		 	            String sik_DNCSIPAddress= sL.nodeFromKey(senddata,"<sik:DNCSIPAddress>","</sik:DNCSIPAddress>");
		 	            Application.showMessage("sik_DNCSIPAddress is ::"+sik_DNCSIPAddress); 
		 	           	 	            
		 	            String sik_HeadEndVendor= sL.nodeFromKey(senddata,"<sik:HeadEndVendor>","</sik:HeadEndVendor>");
		 	            Application.showMessage("sik_HeadEndVendor is ::"+sik_HeadEndVendor); 
		 	            
		 	           if(sik_HeadEndVendor==null)
			            {
				            c.report("Send Xml sik_HeadEndVendor is NULL");
				            continue;
			            }
			            else
			            {
			            	// Application.showMessage("sik_HeadEndVendor from Send Xml  from SIK is ::"+" "+sik_LastName);
			            	 if(sik_HeadEndVendor.equalsIgnoreCase(c.get("GL_headendType").toString()))
				             {
				            	 Application.showMessage("*******Validation Point ## :: WebServicall-sik_HeadEndVendor********");
				            	 Application.showMessage("Validation is Successfull with sik_HeadEndVendor::"+" "+sik_HeadEndVendor);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("sik_HeadEndVendor at Send Xml not Validated as "+sik_HeadEndVendor);
				             }
			            }
		 	           		 	            
		 	            String sik_E911_acceptance= sL.nodeFromKey(senddata,"<sik:E911_acceptance>","</sik:E911_acceptance>");
		 	            Application.showMessage("sik_E911_acceptance is ::"+sik_E911_acceptance); 
		 	                      
		 	            String sik_AgentID= sL.nodeFromKey(senddata,"<sik:AgentID>","</sik:AgentID>");
		 	            Application.showMessage("sik_AgentID is ::"+sik_AgentID); 
		 	            
		 	            String sik_WorkOrderID= sL.nodeFromKey(senddata,"<sik:WorkOrderID>","</sik:WorkOrderID>");
		 	            Application.showMessage("sik_WorkOrderID is ::"+sik_WorkOrderID); 
		 	           
		 	         	if(recievedata.contains("<OrderID")) 	
		 	         	{
		 	                
			 	            String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
			 	            Application.showMessage("OrderID is::"+OrderID_sik);
			 	           v3=1;
			 	            //AppContext.put("CancelOrderID", OrderID_sik,true);
			 	           // c.put("OrderID_SIK", OrderID_sik); 
			 	            //Application.showMessage("The AppContext Order ID is::"+AppContext.get("CancelOrderID"));
			 	            //Application.showMessage("The put Order ID is::"+c.get("OrderID_SIK"));
			 	            c.setValue("OrderIDToCancel", OrderID_sik);
			 	            			 	            
		 	         	}
		 	         	
		 	         	else if(recievedata.contains("<cct:code>Order-SubmitOrder-213</cct:code>") || recievedata.contains("<cct:code>Order-SubmitOrder-118</cct:code>"))
		 	            {
		 	         		
		 	         		//AppContext.put("CancelOrderID", "1234",true);
		 	            	//Application.showMessage("Order Id not created");
		 	            	//c.report("ORDER ID NOT FOUND..!");
		 	            	if(c.getValue("dS_SIK", "Sik_Dotcom: IsModify").equalsIgnoreCase("1"))
		 	            	{
		 	            	  Application.showMessage("Modify Order...!");
		 	            	 v3=1;
		 	            	}
		 	            	else
		 	            	{
		 	            		c.report("ORDER ID NOT FOUND..!");	
		 	            	}
		 	            }
		 	      	 	            
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3*v4*trackcode ==1)
	         {
	         	Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
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
	
	
	public void ModifyOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException, ParserConfigurationException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********ModifyOrder_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ModifyOrder"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ModifyOrder"));
	     
	     
	     
	        
	         
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/ModifyOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	       
	         while (rs.next())
	         {
	        	
	        	 String rowmsg;
			     rowmsg = rs.getString(1);
		         Application.showMessage("MessageID is::"+rowmsg);
		           	        
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
		          	           
		            String accountNumber_QC= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_QC); 
	 	            if (accountNumber_QC==null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(accountNumber_QC.equals(c.getValue("accountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_QC);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		            	
		            	if(senddata.contains("<sik:OrderID>"))
		            	{
		      
		            	 String OrderId_modify= sL.nodeFromKey(senddata,"<sik:OrderID>","</sik:OrderID>");
			 	         Application.showMessage("OrderId_modify is ::"+OrderId_modify); 
			 	         if(OrderId_modify.equals(c.get("OrderID_SIK")))
			 	         {
			 	        	 Application.showMessage("Order ID got validated with SIK Order ID");
			 	         }
			 	         else 
			 	         {
			 	        	 c.report("Order ID is not Validated");
			 	         }
		            	}
                      List<String> retreiveTrackcodes=sL.getRateCodesforDotComTrac(input, c);
                        
                        if(retreiveTrackcodes.size()>0)
                         {
                       	 Application.showMessage("**********Validation Point :: KIT Code - OT0PD***********");
                        int TrackValue=sL.getTrackingDetails(input, c,senddata);
                        Application.showMessage("TrackValue:::"+TrackValue);
                        //List<String> retrieveTrackValues=sL.extractValue(TrackValue, input, c);
                        if(TrackValue==(retreiveTrackcodes.size()))
                        {
                       	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                       	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                       	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are equal");
                       	 Application.showMessage("----****Validation is successful with the Track code OTOPD and Reason Xi3_special****----" );
                       	 trackcode=1;
                        }
                        else
                        {
                       	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                       	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                       	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are not equal");
                       	 Application.showMessage("----****Validation is not successful with the Track code OTOPD and Reason Xi3_special****----" );
                       	
                        }
                        }
                        else
                        {
                       	 trackcode=1;
                        }


		            	
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(callFound*trackcode ==1)
	         {
	         	Application.showMessage("WebService Call :: Modify_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::Modify_Validate is Failed with Validation Errors");
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
	
	
	public void getOrderDetails_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********getOrderDetails_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("GetOrderDetails"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("GetOrderDetails"));
   
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/GetOrderDetails' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	       
	         while (rs.next())
	         {
	        	
	        	 String rowmsg;
					rowmsg = rs.getString(1);
		            Application.showMessage("MessageID is::"+rowmsg);
		           
		        
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
		          	           
		            String accountNumber_QC= sL.nodeFromKey(senddata,"<ContractTypes:accountNumber>","</ContractTypes:accountNumber>");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_QC); 
	 	            if (accountNumber_QC==null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(accountNumber_QC.equals(c.getValue("accountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_QC);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	           // String maxRows= sL.GetValueByXPath(senddata,"/ContractServices:queryContract/ContractServices:resultSpec/ContractTypes:maxRows");
		            	String maxRows = sL.nodeFromKey(senddata,"<ContractTypes:maxRows>","</ContractTypes:maxRows>");

		            	Application.showMessage("maxRows is ::"+maxRows);
		 	            
		 	            if(maxRows==null)
			            {
				            c.report("Send Xml maxRows is NULL");
				            continue;
			            }
		            	/*String response = sL.nodeFromKey(recievedata,"</typ:termAspectValue><typ:response>","</typ:response><typ:finalTermText");
		            	Application.showMessage("response is ::"+response);

		            	if(response==null)
			            {
				            c.report("Send Xml response is NULL");
				            continue;
			            }
		            	
		            	else if(response.equalsIgnoreCase("ACCEPTED"))
		            	{
		            		Application.showMessage("response is Validated as ::"+response);
		            	}
		            	else
		            	{
		            		c.report("Response Not validated");
		            	}*/

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: queryContract_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::queryContract_Validate is Failed with Validation Errors");
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
	
	public void modifyServiceableLocation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs,rs1;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------------------");
	     Application.showMessage("**********modifyServiceableLocation_Validate Function************");       
	     Application.showMessage("------------------------------------------------------------------");
	     String BillingSys=c.getValue("dS_SIK","Sik_Dotcom: BillingSystem");
	     Application.showMessage("Billing system from input is::"+BillingSys);
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("modifyServiceableLocation"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("modifyServiceableLocation")); 
		try
		{
			// Statement st =sL.dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cs:CustomerServicePort/modifyServiceableLocation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	       
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
					rowmsg = rs.getString(1);
		            Application.showMessage("MessageID is::"+rowmsg);
		           
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
		            
		            
		            
		            if(c.getValue("dS_SIK","Sik_Dotcom: BillingSystem").equalsIgnoreCase("DDP"))  	
		            {  
		            	
			             String ct_legacyAccountID= sL.nodeFromKey(senddata,"<ct:legacyAccountID>","</ct:legacyAccountID>");
		 	             Application.showMessage("accountNumber is ::"+ct_legacyAccountID); 
		 	            if(ct_legacyAccountID== null)
		 	            {
		 	            	continue;
		 	            }
		 	            else if(ct_legacyAccountID.equals(c.getValue("accountNumber")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+ct_legacyAccountID);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            	
			            if(callFound==1)
			            {
			      
			            	Statement st1 =sL. dBConnect(input, c);
			   	            rs1 = st1.executeQuery("select to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss')AS btime from cwmessagelog where msgid='"+rowmsg+"'");
			   	            while(rs.next())
			   	            {
	                        String BaseTime=rs1.getString("btime");
	                        c.put("BaseTimeMSL", BaseTime);
	                        Application.showMessage("The new BaseTime is ::"+c.get("BaseTime"));
	                        rs1.close();
	                        st1.close();
	                        break;
			   	            }
			 	            String ct_locationID= sL.nodeFromKey(senddata,"<ct:locationID>","</ct:locationID>");
			 	            Application.showMessage("ct_locationID is ::"+ct_locationID);
			 	            
			 	            if(ct_locationID==null)
				            {
					            c.report("Send Xml ct_locationID is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ct_locationID);
				            	 if(ct_locationID.equals(c.getValue("locationID")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-ct_locationID********");
					            	 Application.showMessage("Validation is Successfull with ct_locationID::"+" "+ct_locationID);
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("ct_locationID at Send Xml not Validated as "+ct_locationID);
					             }
				            }		
		 	           
		            }
		            
		            else if(c.getValue("dS_SIK","Sik_Dotcom: BillingSystem").equalsIgnoreCase("CSG"))		            	
		            {           
			             String ct_legacyLocationID= sL.nodeFromKey(senddata,"<ct:legacyLocationID>","</ct:legacyLocationID>");
		 	             Application.showMessage("ct_legacyLocationID is ::"+ct_legacyLocationID);
		 	            if(ct_legacyLocationID== null)
		 	            {
		 	            	continue;
		 	            }
		 	            
		 	            else if(ct_legacyLocationID.equals(c.getValue("LegacyLocationID_GT")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+ct_legacyLocationID);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            	
			            if(callFound==1)
			            {
			      
			 	            String ct_locationIDmsl= sL.nodeFromKey(senddata,"<ct:locationID>","</ct:locationID>");
			 	            Application.showMessage("ct_locationID is ::"+ct_locationIDmsl);
			 	            
			 	            if(ct_locationIDmsl==null)
				            {
					            c.report("Send Xml ct_locationID is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ct_locationIDmsl);
				            	 if(ct_locationIDmsl.equals(c.getValue("locationID")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-ct_locationID********");
					            	 Application.showMessage("Validation is Successfull with ct_locationID::"+" "+ct_locationIDmsl);
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("ct_locationID at Send Xml not Validated as "+ct_locationIDmsl);
					             }
				            }		
		 	           
		             }
		            
		            }
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: modifyServiceableLocation_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::modifyServiceableLocation_Validate is Failed with Validation Errors");
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
	
	public void OrderUpdateFlagEnabled_Validate(String INOrdStatus ,String INbillingOrderId,String INinputChannel,String INsalesChannel, String INshipmentType,String INproductType,String INbillingSystem,String INordType,String INcustomerType,String WORKORDER_ID,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
	{
		Thread.sleep(10000); // Think time in JVM [waits for 10 secs here]
	     
		 String Time= c.get("BaseTime").toString();
		 sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int v4=0,v1=0,v2=0,v3=0,v5=0,v6=0,v7=0,v8=0,v9=0,v10=0,v11=0,v12=0,v13=0,v14=0,v15=0,v16=0;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String currentDate=sL.getsysTime();
	     String AccountNumber=c.getValue("accountNumber");
         Application.showMessage("Date of execution is ::"+currentDate);
	     Application.showMessage("--------------------------------------------------------");
	     Application.showMessage("***********OrderUpdate_Validate Function**************");       
	     Application.showMessage("--------------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
	    
		 try
		 {
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate'  and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
			 rs=sL.reduceThinkTimeSIK(input, c);
	        
	         while (rs.next())
	         {
	        	
	        	 String rowmsg;
					rowmsg = rs.getString(1);
		            Application.showMessage("MessageID is::"+rowmsg);
		                  	
	            
	            if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else if(rs.getBlob("receive_data")==null) 
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		           
		            Application.showMessage("senddata is ::"+senddata); 
		          
		        
		            
	 	            String billingAccountNo= sL.nodeFromKey(senddata,"<id>","</id>");
		       	    Application.showMessage("billingAccountNo is ::" +billingAccountNo);	       	    
		       	    v1= sL.verificationPoint(billingAccountNo, INbillingOrderId, input, c);
		       	                     
	 	            String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
                    Application.showMessage("The ordStatus from Request is::"+ordStatus);                       
                    v2= sL.verificationPoint(ordStatus, INOrdStatus, input, c);
                    
                   
                    if(v1*v2==1)
                    {
                    	callFound=1;
                    }
                    else
                    {
                    	continue;
                    }
                    
                    if(callFound==1)
                    {
                        String sendDataT=sL.RemoveNameSpaces(senddata);
                    	//String guid= sL.nodeFromKey(senddata,"<value name=\"guid\">","</value><value name=\"accountNumber\">");
                    	String guid= sL.GetValueByXPath(sendDataT, "/comRequest/header/value");
                        Application.showMessage("The guid from Request is::"+guid);                       
                        v3= sL.validationPointIgnoreCase(guid, INbillingOrderId, input, c);
                        
                       // String accountNumber= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"corpId\">");
                    	String accountNumber= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[2]");
                        Application.showMessage("The accountNumber from Request is::"+accountNumber);                       
                        v4= sL.validationPointIgnoreCase(accountNumber, AccountNumber, input, c);
                        
                        String INcorpID= sL.makeCorpIDfromAccountNumber(accountNumber, INbillingSystem);                  
                       // String corpId= sL.nodeFromKey(senddata,"<value name=\"corpId\">","</value><value name=\"billingOrderId\">");
                        String corpId= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[3]");
                        Application.showMessage("The corpId from Request is::"+corpId);                       
                        v5= sL.validationPointIgnoreCase(corpId, INcorpID, input, c);
                        
                        //String billOrder= INbillingOrderId.substring(accountNumber.length());
                        //String billingOrderId= sL.nodeFromKey(senddata,"<value name=\"billingOrderId\">","</value><value name=\"ordType\">");
//                        String billingOrderId= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[4]");
//                        Application.showMessage("The billingOrderId from Request is::"+billingOrderId);                       
//                        v6= sL.validationPointIgnoreCase(billingOrderId, WORKORDER_ID, input, c);
                        
                       // String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
                        String ordType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[5]");
                        Application.showMessage("The ordType from Request is::"+ordType);                       
                        v7= sL.validationPointIgnoreCase(ordType, INordType, input, c);
                    	
                        
                       // String ordSource= sL.nodeFromKey(senddata,"<value name=\"ordSource\">","</value><value name=\"ordStatus\">");
                        String ordSource= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[6]");
                        Application.showMessage("The ordSource from Request is::"+ordSource);                       
                        v8= sL.validationPointIgnoreCase(ordSource, "OP", input, c);
                        
                       // String selfInstall= sL.nodeFromKey(senddata,"<value name=\"selfInstall\">","</value><value name=\"inputChannel\">");
                        String selfInstall= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[8]");
                        Application.showMessage("The selfInstall from Request is::"+selfInstall);                       
                        v9= sL.validationPointIgnoreCase(selfInstall, "1", input, c);
                        
                        //String inputChannel= sL.nodeFromKey(senddata,"<value name=\"inputChannel\">","</value><value name=\"customerType\">");
                        String inputChannel= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[9]");
                        Application.showMessage("The inputChannel from Request is::"+inputChannel);                       
                        v10= sL.validationPointIgnoreCase(inputChannel, INinputChannel, input, c);
                        
                        //String customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"billingSystem\">");
                        String customerType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[10]");
                        Application.showMessage("The customerType from Request is::"+customerType);                       
                        v11= sL.validationPointIgnoreCase(customerType, INcustomerType, input, c);
                        
                       // String billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
                        String billingSystem= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[11]");
                        Application.showMessage("The billingSystem from Request is::"+billingSystem);                       
                        v12= sL.validationPointIgnoreCase(billingSystem, INbillingSystem, input, c);
                        
                        //String active= sL.nodeFromKey(senddata,"<value name=\"active\">","</value><value name=\"salesChannel\">");
                        String active= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[12]");
                        Application.showMessage("The active from Request is::"+active);                       
                        v13= sL.validationPointIgnoreCase(active, "1", input, c);
                        
                        
                        //String salesChannel= sL.nodeFromKey(senddata,"<value name=\"salesChannel\">","</value><value name=\"otherInfo\">");
                        String salesChannel= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[13]");
                        Application.showMessage("The salesChannel from Request is::"+salesChannel);                       
                        v14= sL.validationPointIgnoreCase(salesChannel, INsalesChannel, input, c);
                        
                       // String productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value></header>");
                        String productType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[16]");
                        Application.showMessage("The productType from Request is::"+productType);                       
                        v15= sL.validationPointIgnoreCase(productType, INproductType, input, c);
                        
                       // String street1= sL.nodeFromKey(senddata,"<value name=\"street1\">","</value><value name=\"street2\">");
                        String street1= sL.GetValueByXPath(sendDataT, "/comRequest/address/value");
                        Application.showMessage("The street1 from Request is::"+street1);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                       // String street2= sL.nodeFromKey(senddata,"<value name=\"street2\">","</value><value name=\"city\">");
                        String street2= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[2]");
                        Application.showMessage("The street2 from Request is::"+street2);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                       // String city= sL.nodeFromKey(senddata,"<value name=\"city\">","</value><value name=\"state\">");
                        String city= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[3]");
                        Application.showMessage("The city from Request is::"+city);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                       // String state= sL.nodeFromKey(senddata,"<value name=\"state\">","</value><value name=\"zip\">");
                        String state= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[4]");
                        Application.showMessage("The state from Request is::"+state);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        //String address= sL.nodeFromKey(senddata,"<value name=\"address\">","</value><value name=\"guid\">");
                        String address= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value");
                        Application.showMessage("The address from OrderUpdate is::"+address);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        //String zip= sL.nodeFromKey(senddata,"<value name=\"zip\">","</value><value name=\"zip4\">");
                        String zip= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[5]");
                        Application.showMessage("The zip from Request is::"+zip);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                       /* String PhoneNumber= sL.GetValueByXPath(sendDataT, "/comRequest/customer/value[4]");
                        Application.showMessage("The PhoneNumber from BFC Request is::"+c.get("Cphone").toString());                       

                        Application.showMessage("The PhoneNumber from Request is::"+PhoneNumber);                       
                        v2= sL.validationPointIgnoreCase(PhoneNumber, c.get("Cphone").toString(), input, c);
                       
                        
                        
                        //String zip4= sL.nodeFromKey(senddata,"<value name=\"zip4\">","</value><value name=\"franchiseTaxArea\">");
                        String zip4= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[6]");
                        Application.showMessage("The zip4 from Request is::"+zip4);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        //String franchiseTaxArea= sL.nodeFromKey(senddata,"<value name=\"franchiseTaxArea\">","</value><value name=\"selfInstall\">");
                        String franchiseTaxArea= sL.GetValueByXPath(sendDataT, "/comRequest/address/value[7]");
                        Application.showMessage("The franchiseTaxArea from Request is::"+franchiseTaxArea);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        
                       // String firstName= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
                        String firstName= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value[3]");
                        Application.showMessage("The firstName from Request is::"+firstName);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        
                        
                        //String lastName= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
                        String lastName= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value[4]");
                        Application.showMessage("The lastName from Request is::"+lastName);                       
                       // v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        //String shipmentType= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
                        String shipmentType= sL.GetValueByXPath(sendDataT, "/comRequest/shipping/value[6]");
                        Application.showMessage("The shipmentType from Request is::"+shipmentType);                       
                        //v2= sL.validationPointIgnoreCase(ordStatus, INOrdStatus, input, c);
                        
                        String note= sL.nodeFromKey(senddata,"<note><note>","</note><description>");
                        Application.showMessage("The note from Request is::"+note);                       
                        //= sL.validationPointIgnoreCase(note, "Order Validated", input, c);
                        
                        String desc= sL.nodeFromKey(senddata,"</note><description>","</description><append>");
                        Application.showMessage("The Description from Request is::"+desc);                       
                       // v2= sL.validationPointIgnoreCase(desc, "DOTCOM: Order Validated Successfully", input, c);
                        
                        
                        */
                        
                        
                        
                        
                    }
                    
		 	            
		            break;
		            }
	             }
	    
	         
	         if(v1*v2*v3*v4*v5*callFound ==1)
	         {
	        	 OutPut=1;
	         	 Application.showMessage("WebService Call :: OrderUpdate is Success[All validation points are Success]");
                 
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::Order Update is Failed with Validation Errors");

	         }
	         //st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		 
		// return OutPut; 
	 }
	
	public void orderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********orderUpdate_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	     
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
					rowmsg = rs.getString(1);
		            Application.showMessage("MessageID is::"+rowmsg);
		           
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	           
	                          
		           
		            
			       // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
	            	
	            	

		            String ou_AccountNumberid= sL.nodeFromKey(senddata,"<id>","</id>");
	 	            Application.showMessage("accountNumber is ::"+ou_AccountNumberid); 	
	 	            if(ou_AccountNumberid== null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(ou_AccountNumberid.equals(c.getValue("serviceRequestId")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
	            		
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            String ou_guid= sL.nodeFromKey(senddata,"</instance><id>","</id><requestTime>");
	 	            Application.showMessage("guid is ::"+ou_guid); 	           
		            if(ou_guid.equals(c.getValue("serviceRequestId")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input serviceRequestId"+ou_guid);
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
		 	            
		 	            String accountNumber_ou= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
		 	            Application.showMessage("accountNumber is ::"+accountNumber_ou); 
		 	            if(accountNumber_ou.equals(c.getValue("accountNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("accountNumber at Send Xml not Validated as "+accountNumber_ou);
			             }    
		 	            
		 	            String action= sL.nodeFromKey(senddata,"<value name=\"action\">","</value><value name=\"guid\">");
		 	            Application.showMessage("action is ::"+action); 
		 	            if(action==null)
			            {
				          Application.showMessage("Send Xml ordType is NULL");
				            
			            }
		 	            
		 	           

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
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
	
	
	public void confirmServiceRequest_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0, v2 = 0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("********confirmServiceRequest_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("confirmServiceRequest"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("confirmServiceRequest"));  
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ofs:OfferMgmtServicePort/confirmServiceRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	     
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
					rowmsg = rs.getString(1);
		            Application.showMessage("MessageID is::"+rowmsg);
		           
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
		           
		            String oft_accountReference= sL.nodeFromKey(senddata,"<oft:accountReference>","</oft:accountReference>");
	 	            Application.showMessage("accountNumber is ::"+oft_accountReference); 
	 	            if(oft_accountReference== null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(oft_accountReference.equals(c.getValue("accountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+oft_accountReference);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equals("NEW"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("FirstName at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		

		 	            String confirmServiceRequestReturn= sL.nodeFromKey(recievedata,"<m:confirmServiceRequestReturn xmlns:m=\"http://xml.comcast.com/offermgmt/services\">","</m:confirmServiceRequestReturn>");
		 	            Application.showMessage("confirmServiceRequestReturn is ::"+confirmServiceRequestReturn); 
		 	            
		 	          
		 	        
		 	           if(confirmServiceRequestReturn==null)
			            {
				            c.report(" confirmServiceRequestReturn is NULL");
				            continue;
			            }
			            else
			            {
			            	 if(confirmServiceRequestReturn.equalsIgnoreCase("true"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-confirmServiceRequestReturn********");
				            	 Application.showMessage("Validation is Successfull with confirmServiceRequestReturn::"+" "+confirmServiceRequestReturn);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("confirmServiceRequestReturn at Send Xml not Validated as "+confirmServiceRequestReturn);
				             }
			            }
		 	                   
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*v2*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: confirmServiceRequest_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::confirmServiceRequest_Validate is Failed with Validation Errors");
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
	
	
	 public void NoDataSetFound(Object input, ScriptingContext c,String INOperation,String AccountNumber)throws InterruptedException, ClassNotFoundException, IOException, SQLException 
	    {
	                    
	    	ResultSet rs;              
	        int v1=0,i=0;
	        Thread.sleep(10000);
			
			sikLibraryClass sL = new sikLibraryClass();
			String Time=(String) c.get("BaseTime");
			Application.showMessage("BaseTime is::"+Time);
			Statement st =sL.dBConnect(input, c);	
	        rs = st.executeQuery("select * from (select * from cwmessagelog where user_data2 = '"+AccountNumber+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");       
	       
				while(rs.next())
				{
				     
					  String operation=rs.getString("OPERATION");
				      Application.showMessage("OPeration is ::"+operation);
				      
				    			      
				       if(operation.equals(INOperation))
				       {
				           Application.showMessage("Extra call Found for Operation::"+INOperation);
				           c.report("Extra call Found for Operation::"+INOperation);
				           v1=1;
				           continue;
				       }
				       else
				       {
				    	   Application.showMessage("No Extra call found for Operation::"+INOperation);  
				       }
				      
				      
				     if(v1==1)
				     {
				          c.report("Extra Call Found");
				          break;
				     }
				     else
				     {
				         Application.showMessage(i+"th row data is not an Extra call ");
				     }
				     i++;
				}
	          st.close();
				
	    }
}
