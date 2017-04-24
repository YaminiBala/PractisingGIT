import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class sikLiteSR 
{
  sikDotcomClass sD=new sikDotcomClass();
  sikLibraryClass sL =new sikLibraryClass();
  
  public void TestCaseDetermination(Object input,ScriptingContext c) throws IOException
  {
	  String TestCaseName= c.getValue("dS_SIK", "Sik_Dotcom: TestCaseName");
	  c.setValue("TestCaseName", TestCaseName);
	  Application.showMessage("Test Case for LiteSR Excel is :: "+c.getValue("TestCaseName"));
	  
	  String AQmethod=c.getValue("dS_SIK", "Sik_Dotcom: Acquisition Method");
	  c.put("pAQmethod",AQmethod );
	  c.setValue("acquisitionMethod", AQmethod);
	  Application.showMessage("Acquisition Method for LiteSR Excel is :: "+c.get("pAQmethod"));
	  
	  
	  
  }
  
  public void readValuesfromsikExcel(Object input, ScriptingContext c)throws IOException
	{
		 Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
		    String XML_EndPoint=c.getValue("dS_SIK", "sikDBsettings: XML_Enpoint");
		    String DB_Host=c.getValue("dS_SIK", "sikDBsettings: DB_HOST");
		    String dB_Password=c.getValue("dS_SIK", "sikDBsettings: DB_Password");
		    String dB_Username=c.getValue("dS_SIK", "sikDBsettings: DB_UserName");
		    String dB_Port=c.getValue("dS_SIK", "sikDBsettings: DB_Port");
		    String dB_serviceName=c.getValue("dS_SIK", "sikDBsettings: DB_ServiceName");
		    String dB_connectionName=c.getValue("dS_SIK", "sikDBsettings: DB_ConnectionName");
            String InputAccountNumber = c.getValue("dS_SIK", "Sik_Dotcom: AccountNo");
            String InputserviceReqID = c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
            String InputLocation_ID = c.getValue("dS_SIK", "Sik_Dotcom: Location_ID");
            String InputorderEntryOption = c.getValue("dS_SIK", "Sik_Dotcom: orderEntryOption");
            String InputordType = c.getValue("dS_SIK", "Sik_Dotcom: ordType");
            String InputsignatureReq = c.getValue("dS_SIK", "Sik_Dotcom: signatureReq");
            String InputBillingSystem = c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
            String locationID=c.getValue("dS_SIK", "Sik_Dotcom: Location_ID");
                             
		    Application.showMessage("Endpoint is ::"+XML_EndPoint);
		    Application.showMessage("DB_HOST is ::"+DB_Host);
		    Application.showMessage("dB_Password is ::"+dB_Password);
		    Application.showMessage("dB_Username is ::"+dB_Username);
		    Application.showMessage("dB_Port is ::"+dB_Port);
		    Application.showMessage("dB_serviceName is ::"+dB_serviceName);
		    Application.showMessage("dB_connectionName is ::"+dB_connectionName);
		    c.setValue("DB_Host", DB_Host);
		    c.setValue("dB_Password",dB_Password);
		    c.setValue("dB_Username",dB_Username);
		    c.setValue("dB_Port",dB_Port);
		    c.setValue("dB_serviceName",dB_serviceName);
		    c.setValue("XML_EndPoint",XML_EndPoint);	    
		    c.setValue("serviceRequestId", InputserviceReqID);
		    c.setValue("accountNumber", InputAccountNumber);
		    c.setValue("ordType", InputordType);	   
		    c.setValue("orderEntryOption", InputorderEntryOption);
		    c.setValue("locationID", InputLocation_ID);
		    c.setValue("BillingSystem", InputBillingSystem);
		    c.setValue("signatureReq", InputsignatureReq);
		    c.setValue("locationID",locationID);
		    Application.showMessage("Location ID ::"+locationID);
		    
		    c.put("accountNumber", InputAccountNumber);
		  Application.showMessage("InputAccountNumber is ::"+InputAccountNumber);
		  Application.showMessage("InputserviceReqID is ::"+InputserviceReqID);
		  Application.showMessage("InputordType is ::"+InputordType);
		  Application.showMessage("InputorderEntryOption is ::"+InputorderEntryOption);
		  Application.showMessage("InputLocation_ID is ::"+InputLocation_ID);
		  Application.showMessage("InputBillingSystem is ::"+InputBillingSystem);
		  Application.showMessage("InputsignatureReq is ::"+InputsignatureReq);
		    
		    
		    
	}
  
  
  public void TC8_CSG_LITESR_acquiTypeCUST_PICKUP_shipTypePICKUP_AddHSDFeatAO(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
	  
  }
  
  public void TC12_CSG_LITESR_acquisitionType_Prof_Install_ExistHSD_AddPrimaryVideo_AO_Features(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
  }
  
  public void TC9_DDP_SIKLiteSR_acqTypeSHIPPEDAddCDV_Feature_AO(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
  }
  public void TC2_SIK_DDP_SIKLiteSR_acqType_SHIPPED_ExistVideo_AddAOVideo(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
  }
  
  public void TC1_SIK_US3399_DDP_SIK_LiteSR_ADDFeatureOnly(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
  }
  
  public void TC3_acqType_CUSTOMER_PICKUP_AddFeature_AOVideo(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
  }
  
  public void TC11_DDP_acqTypeCUST_PICKUP_shipCUSTPICK_ExistHSD_AddPriVideoAO(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
  }
  
  public void TC10_SIK_CSG_SIK_LiteSR_acqTypeSHIPPED_ExistHSD_AddPrimaryVideo(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
  }
  
  public void TC6_CSG_acqTypeSHIPPED_AddHSDFeature_NonewContractadded(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
  }
  
  public void TC4_CSG_SIKLITE_SR_acqType_CUST_PICKUP_shipTypePICKUP_AddHSD(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
  {
	  
  }
  
  
  
  
  public void bfcRequestLiteSR_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String recievedata_wf=null;
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********bfcRequest_Validate Function**************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("bfcRequest"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("bfcRequest"));
 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:orderProcessorServicePort/bfcRequest'  and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
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
		            		
		            		String senddata_wf= sL.RemoveNameSpaces(senddata);
		            		Application.showMessage("Well-Formed send data is :: "+ senddata_wf);
		            		
		            	    recievedata_wf= sL.RemoveNameSpaces(recievedata);
		            		Application.showMessage("Well-Formed recieve data is :: "+ recievedata_wf);
		            	
		            		
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            
		            
	            	
		            if(callFound==1)
		            {
		            	
		            	//Validations particular to testcases
		            	
		            	
		            	if( recievedata_wf.contains("</lob>"))
		            	{
		            		String lob=sL.GetValueByXPath(recievedata_wf, "/msgInfo/lob");
		            		c.put("pLob", lob);
		            		Application.showMessage("LOB is ::"+c.get("pLob"));
		            	}
		            	else
		            	{
		            		Application.showMessage("LOB is Null");
		            	}
		            	if( recievedata_wf.contains("<name>LocationID</name>"))
		            	{
		            		String LocID=sL.GetValueByXPath(recievedata_wf, "/msgInfo/parameters/parameters[5]/value");
		            		c.put("pLocID", LocID);
		            		Application.showMessage("LocID is ::"+c.get("pLocID"));
		            	}
		            	else
		            	{
		            		Application.showMessage("LocID is Null");
		            	}
		            	
		            	if( recievedata_wf.contains("<name>ordType</name>"))
		            	{
		            		String OrdType=sL.GetValueByXPath(recievedata_wf, "/msgInfo/parameters/parameters[7]/value");
		            		c.put("pOrdType", OrdType);
		            		Application.showMessage("pOrdType is ::"+c.get("pOrdType"));
		            	}
		            	else
		            	{
		            		Application.showMessage("OrdType is Null");
		            	}
		      
		            	
		            	if( recievedata_wf.contains("<shipmentType>"))
		            	{
		            		String pShipmentType=sL.GetValueByXPath(recievedata_wf, "/msgInfo/ServiceRequest/serviceRequestShipment/serviceRequestShipment/shipmentType");
		            		c.put("pShipmentType", pShipmentType);
		            		Application.showMessage("pShipmentType is ::"+c.get("pShipmentType"));
		            	}
		            	else
		            	{
		            		Application.showMessage("pShipmentType is Null");
		            	}
		      
		            	
		            	
		            	if( recievedata_wf.contains("<inputChannel>"))
		            	{
		            		String pInputChannel=sL.GetValueByXPath(recievedata_wf, "/msgInfo/ServiceRequest/inputChannel");
		            		c.put("pInputChannel", pInputChannel);
		            		Application.showMessage("pInputChannel is ::"+c.get("pInputChannel"));
		            	}
		            	else
		            	{
		            		Application.showMessage("pInputChannel is Null");
		            	}
		            	
		            	
		            	if( recievedata_wf.contains("<existingLineOfBusiness>"))
		            	{
		            		String pExistingLineOfBusiness=sL.GetValueByXPath(recievedata_wf, "/msgInfo/ServiceRequest/serviceRequestCustomerService/serviceRequestCustomerService/existingLineOfBusiness/string");
		            		c.put("pExistingLineOfBusiness", pExistingLineOfBusiness);
		            		Application.showMessage("pExistingLineOfBusiness is ::"+c.get("pExistingLineOfBusiness"));
		            	}
		            	
		            	else
		            	{
		            		Application.showMessage("pExistingLineOfBusiness is Null");
		            	}
		            	
		            	
		            	if( recievedata_wf.contains("<customerProductStatus>"))
		            	{
		            		String pCustomerProductStatus=sL.GetValueByXPath(recievedata_wf, "/msgInfo/ServiceRequest/serviceRequestCustomerService/serviceRequestCustomerService[2]/customerProductStatus");
		            		c.put("pCustomerProductStatus", pCustomerProductStatus);
		            		Application.showMessage("pCustomerProductStatus is ::"+c.get("pCustomerProductStatus"));
		            	}
		            	
		            	else
		            	{
		            		Application.showMessage("pCustomerProductStatus is Null");
		            	}
		            	
		            	
		            	
		            	String accountNumber_bfc= sL.GetValueByXPath(recievedata_wf, "/msgInfo/parameters/parameters[4]/value");
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
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
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
  
  
  public void orderUpdateLiteSR_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
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
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate'  and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	//Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	String senddata_wf=sL.RemoveNameSpaces(senddata);
	            	Application.showMessage("Your Well-formed send XML is::\n"+senddata_wf);
	           
	                          
		           
		            
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
	            	
		            String ou_guid= sL.nodeFromKey(senddata,"<value name=\"guid\">","</value></header>");
	 	            Application.showMessage("guid is ::"+ou_guid); 	           
		            if(ou_guid.equals(c.getValue("serviceRequestId")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input serviceRequestId"+ou_guid);
	            		callFound=1;
	            		v1=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
		            if(callFound==1)
		            {
		                
		            	if( senddata_wf.contains("productType"))
		            	{
		            		String productType=sL.GetValueByXPath(senddata_wf, "/comRequest/header/value");
		            		c.put("pProductType", productType);
		            		Application.showMessage("productType is ::"+c.get("pProductType"));
		            		
		            		if(productType.equalsIgnoreCase("DOTCOM"))
		            		{
			            		Application.showMessage("productType is got Validated as ::DOTCOM");

		            		}
		            		else
		            		{
			            		Application.showMessage("productType is NOT Validated as ::DOTCOM");

		            		}
		            	}
		            	else
		            	{
		            		Application.showMessage("productType is Null");
		            	}
		            	
		            	if( senddata_wf.contains("status"))
		            	{
		            		String pStatus=sL.GetValueByXPath(senddata_wf, "/comRequest/service[2]/service/value");
		            		c.put("pStatus", pStatus);
		            		Application.showMessage("status is ::"+c.get("pStatus"));
		            		
		            		if(pStatus.equalsIgnoreCase("ACT"))
		            		{
			            		Application.showMessage("Status is got Validated as ::ACT");

		            		}
		            		else
		            		{
			            		Application.showMessage("Status is NOT Validated as ::ACT");

		            		}
		            	}
		            	else
		            	{
		            		Application.showMessage("status is Null");
		            	}
		            	
		            	
		            	if( senddata_wf.contains("ordStatus"))
		            	{
		            		String pOrdStatus=sL.GetValueByXPath(senddata_wf, "/comRequest/header/value[5]");
		            		c.put("pOrdStatus", pOrdStatus);
		            		Application.showMessage("pOrdStatus is ::"+c.get("pOrdStatus"));
		            		
		            		if(pOrdStatus.equalsIgnoreCase("COM"))
		            		{
			            		Application.showMessage("OrderStatus is got Validated as ::COM");

		            		}
		            		
		            		else if(pOrdStatus.equalsIgnoreCase("INI"))
		            		{
			            		Application.showMessage("OrderStatus is got Validated as ::INI");

		            		}
		            		else
		            		{
			            		Application.showMessage("OrderStatus is NOT Validated as ::COM");

		            		}
		            	}
		            	else
		            	{
		            		Application.showMessage("ordStatus is Null");
		            	}
		            	
		            	
		            	if( senddata_wf.contains("ordSource"))
		            	{
		            		String pOrdSource=sL.GetValueByXPath(senddata_wf, "/comRequest/header/value[6]");
		            		c.put("pOrdSource", pOrdSource);
		            		Application.showMessage("pOrdSource is ::"+c.get("pOrdSource"));
		            	}
		            	else
		            	{
		            		Application.showMessage("ordSource is Null");
		            	}
		            	
		            	
		            	if( senddata_wf.contains("ordType"))
		            	{
		            		String pOrdType=sL.GetValueByXPath(senddata_wf, "/comRequest/header/value[7]");
		            		c.put("pOrdType", pOrdType);
		            		Application.showMessage("pOrdType is ::"+c.get("pOrdType"));
		            		if(pOrdType.equalsIgnoreCase("CHG"))
		            		{
			            		Application.showMessage("pOrdType is got Validated as ::CHG");

		            		}
		            		else
		            		{
			            		Application.showMessage("pOrdType is NOT Validated as ::CHG");

		            		}
		            		
		            	}
		            	else
		            	{
		            		Application.showMessage("ordType is Null");
		            	}
		            	
		            	
		 	            
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
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
  
  
  public void getLocation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********getLocation_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("getLocation"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getLocation"));  
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/getLocation'  and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
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
		 	            
		 	            String streetPreDirection_GT= sL.nodeFromKey(recievedata,"<typ:streetPreDirection>","</typ:streetPreDirection>");
		 	            if(streetPreDirection_GT.isEmpty())
		 	            {
			 	            Application.showMessage("streetPreDirection is Null"); 
			 	            c.put("GL_streetPreDirection", " ");
		 	            }
		 	            else
		 	            {
		 	            Application.showMessage("streetPreDirection is ::"+streetPreDirection_GT); 
		 	            c.put("GL_streetPreDirection", streetPreDirection_GT);
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
		 	             
		 	            
		 	            
		 	            if(c.getValue("BillingSystem").equalsIgnoreCase("DDP"))
		 	            {
		 	            	String ftaAgent= sL.nodeFromKey(recievedata,"<typ:FranchiseTaxArea>","</typ:FranchiseTaxArea>");
			 	            Application.showMessage("ftaAgent is ::"+ftaAgent); 
			 	            c.put("GL_ftaAgent", ftaAgent);
		 	            }
		 	            
		 	            else if(c.getValue("BillingSystem").equalsIgnoreCase("CSG"))
		 	            {
		 	            	String FranchiseTaxArea= sL.nodeFromKey(recievedata,"</typ:AccountCorp><typ:FranchiseTaxArea>","</typ:FranchiseTaxArea>");
			 	            Application.showMessage("FranchiseTaxArea is ::"+FranchiseTaxArea); 
			 	            c.put("GL_FranchiseTaxArea", FranchiseTaxArea);
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
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getLocation_Validate is Failed with Validation Errors");
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
  
  public void confirmServiceRequest_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0, v2 = 0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("********confirmServiceRequest_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ofs:OfferMgmtServicePort/confirmServiceRequest'  and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
			            	 else  if(oft_shoppingAction.equals("CHANGEOFSERVICE"))
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
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::confirmServiceRequest_Validate is Failed with Validation Errors");
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
