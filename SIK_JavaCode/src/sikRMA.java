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


public class sikRMA 
{
	
	public void readRMAinputExcel(Object input, ScriptingContext c)throws IOException
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
           // String InputAcquisitionMethod = c.getValue("dS_SIK", "Sik_Dotcom: Acquisition Method");
            String InputordType = c.getValue("dS_SIK", "Sik_Dotcom: OrderType");
          
            
            
            
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

  		    
  		  Application.showMessage("InputAccountNumber is ::"+InputAccountNumber);
  		  Application.showMessage("InputserviceReqID is ::"+InputserviceReqID);
  		  Application.showMessage("InputordType is ::"+InputordType);
  		  Application.showMessage("InputorderEntryOption is ::"+InputorderEntryOption);
  		  Application.showMessage("InputLocation_ID is ::"+InputLocation_ID);
         //--------------------------------------------------------------------------
  		  //
  		  //                Reading Additional Fields
  		  //
  		 //--------------------------------------------------------------------------
  		    
  		   c.put("paquistionMethod1", c.getValue("dS_SIK", "Sik_Dotcom: aquistionMethod1"));
  		   c.put("paquistionMethod2", c.getValue("dS_SIK", "Sik_Dotcom: aquistionMethod2"));
  		   c.put("paquistionMethod3", c.getValue("dS_SIK", "Sik_Dotcom: aquistionMethod3"));
  		   c.put("paquistionMethod4", c.getValue("dS_SIK", "Sik_Dotcom: aquistionMethod4"));
  		   c.put("paquistionMethod5", c.getValue("dS_SIK", "Sik_Dotcom: aquistionMethod5"));
  		   c.put("pBillingSystem", c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem"));
  		   
  		   Application.showMessage("aquistionMethod1 is ::"+c.get("paquistionMethod1"));
  		   Application.showMessage("aquistionMethod2 is ::"+c.get("paquistionMethod2"));
  		   Application.showMessage("aquistionMethod3 is ::"+c.get("paquistionMethod3"));
  		   Application.showMessage("aquistionMethod4 is ::"+c.get("paquistionMethod4"));
  		   Application.showMessage("aquistionMethod5 is ::"+c.get("paquistionMethod5"));
  		   Application.showMessage("BillingSystem is ::"+c.get("pBillingSystem"));
		    
	}
	
	public void rmaProductCodeVerification(Object input, ScriptingContext c,String prd2,String prd3)
	{
		//String AcqMthd1=c.get("paquistionMethod1");
		if((c.get("paquistionMethod1").toString().equalsIgnoreCase("RMA_LARGE_KIT")) && (c.get("paquistionMethod2").toString().equalsIgnoreCase("RMA_CM_EMTA_KIT")) )
		{
			Application.showMessage("Entering into RMA Product Code Section 1 where Acquistion Methods are RMA_LARGE_KIT and RMA_CM_EMTA_KIT ");
			if(prd2.equalsIgnoreCase("HSD-RMA-MODEM-75") && prd3.equalsIgnoreCase("VID-RMA-DCT2xxx-72"))
			{
				Application.showMessage("Product Code1  Validated as :: " +prd2);	
				Application.showMessage("Product Code2  Validated as :: " +prd3);	
			}
			else
			{
				c.report("Product Code NOT Validated..! Failed at Section 1 of RMA Product code verification");
			}
		}
		
		else if((c.get("paquistionMethod1").toString().equalsIgnoreCase("RMA_SMALL_KIT")) && (c.get("paquistionMethod2").toString().equalsIgnoreCase("RMA_CM_EMTA_KIT")) )
		{
			Application.showMessage("Entering into RMA Product Code Section 2 where Acquistion Methods are RMA_SMALL_KIT and RMA_CM_EMTA_KIT ");
			if(prd3.equalsIgnoreCase("VID-RMA-DCT700-70") && prd2.equalsIgnoreCase("HSD-RMA-MODEM-75"))
			{
				Application.showMessage("Product Code1  Validated as :: " +prd2);	
				Application.showMessage("Product Code2  Validated as :: " +prd3);	
			}
			
			else
			{
				c.report("Product Code NOT Validated..! Failed at Section 2 of RMA Product code verification");
			}
		}
		
		else if((c.get("paquistionMethod1").toString().equalsIgnoreCase("RMA_LABEL_KIT")) && (c.get("paquistionMethod2").toString().equalsIgnoreCase("RMA_CM_EMTA_KIT")) )
		{
			Application.showMessage("Entering into RMA Product Code Section 3 where Acquistion Methods are RMA_LABEL_KIT and RMA_CM_EMTA_KIT ");
			if(prd3.equalsIgnoreCase("RMA-DF-LABEL-01") && prd2.equalsIgnoreCase("HSD-RMA-MODEM-75"))
			{
				Application.showMessage("Product Code1  Validated as :: " +prd2);	
				Application.showMessage("Product Code2  Validated as :: " +prd3);	
			}
			
			else
			{
				c.report("Product Code NOT Validated..! Failed at Section 3 of RMA Product code verification");
			}
		}
		else if((c.get("paquistionMethod1").toString().equalsIgnoreCase("SHIPPED")) && (c.get("paquistionMethod2").toString().equalsIgnoreCase("RMA_LARGE_KIT")) && (c.get("paquistionMethod3").toString().equalsIgnoreCase("RMA_CM_EMTA_KIT")) )
		{
			
			Application.showMessage("Entering into RMA Product Code Section 1 where Acquistion Methods are RMA_LARGE_KIT and RMA_CM_EMTA_KIT ");
			if(prd2.equalsIgnoreCase("HSD-RMA-MODEM-75") && prd3.equalsIgnoreCase("VID-RMA-DCT2xxx-72"))
			{
				Application.showMessage("Product Code1  Validated as :: " +prd2);	
				Application.showMessage("Product Code2  Validated as :: " +prd3);	
			}
			else
			{
				c.report("Product Code NOT Validated..! Failed at Section 1 of RMA Product code verification");
			}
		}
		else if((c.get("paquistionMethod1").toString().equalsIgnoreCase("PROFESSIONAL_INSTALL")) && (c.get("paquistionMethod2").toString().equalsIgnoreCase("RMA_LARGE_KIT")) && (c.get("paquistionMethod3").toString().equalsIgnoreCase("RMA_CM_EMTA_KIT")) )
		{
			
			Application.showMessage("Entering into RMA Product Code Section 1 where Acquistion Methods are RMA_LARGE_KIT and RMA_CM_EMTA_KIT ");
			if(prd2.equalsIgnoreCase("HSD-RMA-MODEM-75") && prd3.equalsIgnoreCase("VID-RMA-DCT2xxx-72"))
			{
				Application.showMessage("Product Code1  Validated as :: " +prd2);	
				Application.showMessage("Product Code2  Validated as :: " +prd3);	
			}
			else
			{
				c.report("Product Code NOT Validated..! Failed at Section 1 of RMA Product code verification");
			}
		}
		else if((c.get("paquistionMethod1").toString().equalsIgnoreCase("RMA_LARGE_KIT")) && (c.get("paquistionMethod2").toString().equalsIgnoreCase("RMA_CM_EMTA_KIT")) && (c.get("paquistionMethod3").toString().equalsIgnoreCase("PROFESSIONAL_INSTALL")) )
		{
			
			Application.showMessage("Entering into RMA Product Code Section 1 where Acquistion Methods are RMA_LARGE_KIT and RMA_CM_EMTA_KIT ");
			if(prd2.equalsIgnoreCase("HSD-RMA-MODEM-75") && prd3.equalsIgnoreCase("VID-RMA-DCT2xxx-72"))
			{
				Application.showMessage("Product Code1  Validated as :: " +prd2);	
				Application.showMessage("Product Code2  Validated as :: " +prd3);	
			}
			else
			{
				c.report("Product Code NOT Validated..! Failed at Section 1 of RMA Product code verification");
			}
		}
		else if((c.get("paquistionMethod1").toString().equalsIgnoreCase("RMA_LARGE_KIT")) && (c.get("paquistionMethod2").toString().equalsIgnoreCase("RMA_CM_EMTA_KIT")) && (c.get("paquistionMethod3").toString().equalsIgnoreCase("PROFESSIONAL_INSTALL"))&& (c.get("paquistionMethod4").toString().equalsIgnoreCase("SHIPPED")) )
		{
			
			Application.showMessage("Entering into RMA Product Code Section 1 where Acquistion Methods are RMA_LARGE_KIT and RMA_CM_EMTA_KIT ");
			if(prd2.equalsIgnoreCase("HSD-RMA-MODEM-75") && prd3.equalsIgnoreCase("VID-RMA-DCT2xxx-72"))
			{
				Application.showMessage("Product Code1  Validated as :: " +prd2);	
				Application.showMessage("Product Code2  Validated as :: " +prd3);	
			}
			else
			{
				c.report("Product Code NOT Validated..! Failed at Section 1 of RMA Product code verification");
			}
		}
		else
		{
			c.report("No Combination of Acquistation Methods Found");
		}
		
	}
	
	public void bfcRequest_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
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
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:orderProcessorServicePort/bfcRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
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
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            
		            
	            	
		            if(callFound==1)
		            {
		      
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
		 	            
		 	           String orderEntryOption_bfc= sL.nodeFromKey(recievedata,"<comt:orderEntryOption>","</comt:orderEntryOption>");
		 	           Application.showMessage("orderEntryOption is ::"+orderEntryOption_bfc); 
		 	           
		 	           String acquisitionMethod_bfc= sL.nodeFromKey(recievedata,"<comt:acquisitionMethod>","</comt:acquisitionMethod>");
		 	           Application.showMessage("acquisitionMethod  is ::"+acquisitionMethod_bfc); 	
		 	           
		 	        
		 	            
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
	     //    st.close();
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
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********queryContract_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("queryContract"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("queryContract")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ContractServices:ContractServicePort/queryContract' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
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
		 	            
		 	            String streetPreDirection_GT= sL.nodeFromKey(recievedata,"<typ:streetPreDirection xsi:nil=\"true\">","</typ:streetPreDirection>");
		 	            Application.showMessage("streetPreDirection is ::"+streetPreDirection_GT); 
		 	            c.put("GL_streetPreDirection", streetPreDirection_GT);
		 	            
		 	            
		 	            
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
	        // st.close();
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
		 int callFound=0,v1=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********submitOrder_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SubmitOrder"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SubmitOrder"));  
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
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
	            	
	            	String wf_senddata=sL.RemoveNameSpaces(senddata);
	            	Application.showMessage("Well-formed Send Data XML is"+wf_senddata);
		          	           
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
	            		v1=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		            	 
		            	
		            	//--------------------------------------------------------------------------
		            	//
		            	//              RMA Product code fetching and verification
		            	//
		            	//--------------------------------------------------------------------------
		            	  String prd1= sL.GetValueByXPath(wf_senddata, "/SubmitOrder/Request/Products/Product/ProductCode");
		            	  String prd2= sL.GetValueByXPath(wf_senddata, "/SubmitOrder/Request/Products/Product[2]/ProductCode");
		            	  String prd3= sL.GetValueByXPath(wf_senddata, "/SubmitOrder/Request/Products/Product[3]/ProductCode");
		            	  
		            	  Application.showMessage("RMA Product 1 is ::" +prd1);
		            	  Application.showMessage("RMA Product 2 is ::" +prd2);
		            	  Application.showMessage("RMA Product 3 is ::" +prd3);
		            	  
		            	 // rmaProductCodeVerification(input,c,prd2,prd3);

                          

		            	
		 	           
		 	            
		 	            String sik_ShipmentPriority= sL.nodeFromKey(senddata,"<sik:ShipmentPriority>","</sik:ShipmentPriority>");
		 	            Application.showMessage("ShipmentPriority is ::"+sik_ShipmentPriority); 
		 	            
		 	            String sik_FTA= sL.nodeFromKey(senddata,"<sik:FTA>","</sik:FTA>");
		 	            Application.showMessage("sik_FTA is ::"+sik_FTA);
		 	            
		 	            
		 	            Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));

		 	            Application.showMessage("FTA from Get Loc" +c.get("GL_ftaAgent"));
		 	            Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
		 	            

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
		 	            	 c.report("Street Address of sik is NOT Validated with Get Location as ::" +sik_Address1);

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
		 	            
		 	         	 	            
		 	            
		 	            String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
		 	            Application.showMessage("OrderID is::"+OrderID_sik);
		 	            c.setValue("OrderIDToCancel", OrderID_sik);
		 	            if(OrderID_sik==null)
		 	            {
		 	            	Application.showMessage("Order Id not created");
		 	                //c.report("ORDER ID NOT FOUND..!");
			 	            String messageError_sik= sL.nodeFromKey(recievedata,"<cct:message><cct:code>","</cct:code><cct:text>");
			 	            Application.showMessage("The Error SIK Message is ::"+messageError_sik);
			 	            
			 	            String ErrorText_sik= sL.nodeFromKey(recievedata,"</cct:code><cct:text>","</cct:text></cct:message>");
			 	            Application.showMessage("The Error SIK Text is ::"+ErrorText_sik);
			 	            
			 	            if(ErrorText_sik.equalsIgnoreCase("Order-SubmitOrder-104"))
			 	            {
			 	            	Application.showMessage("RMA Negative scenario is validated with SIK error");
			 	            }
			 	            else
			 	            {
			 	            	c.report("Error Scenario for RMA not VAlidated..!");
			 	            }
			 	            
			 	           		 	            

		 	            }
		 	      	 	            
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
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
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********modifyServiceableLocation_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cs:CustomerServicePort/modifyServiceableLocation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
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
		            
		            if(c.getValue("BillingSystem").equalsIgnoreCase("DDP"))
		            	
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
		            
		            else if(c.getValue("BillingSystem").equalsIgnoreCase("CSG"))
		            	
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
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::modifyServiceableLocation_Validate is Failed with Validation Errors");
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
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********orderUpdate_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
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
	            	
		            String ou_guid= sL.nodeFromKey(senddata,"<value name=\"guid\">","</value><value name=\"accountNumber\">");
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
		      
		 	            String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"billingSystem\">");
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

		 	            String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","<value name=\"ordStatus\">");
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
		 	         	
		 	           
		 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
		 	            Application.showMessage("ordType is ::"+ordType); 
		 	            if(ordType==null)
			            {
				            c.report("Send Xml ordType is NULL");
				            
			            }
		 	            else if(ordType.equals("CHANGEOFSERVICE"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
			             }   
		 	            
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
	
	public void OrderUpdateFlagEnabled_Validate(String INOrdStatus ,String INbillingOrderId,String INinputChannel,String INsalesChannel, String INshipmentType,String INproductType,String INbillingSystem,String INordType,String INcustomerType,String WORKORDER_ID,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
	{
		//Thread.sleep(10000); // Think time in JVM [waits for 10 secs here]
	     
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
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate'  and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
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
                    v2= sL.verificationPoint(ordStatus, "COM", input, c);
                    
                   
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
                        String billingOrderId= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[4]");
                        Application.showMessage("The billingOrderId from Request is::"+billingOrderId);                       
                        v6= sL.validationPointIgnoreCase(billingOrderId, WORKORDER_ID, input, c);
                        
                       // String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
                        String ordType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[5]");
                        Application.showMessage("The ordType from Request is::"+ordType);                       
                        v7= sL.validationPointIgnoreCase(ordType, "CHG", input, c);
                    	
                        
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
                        v2= sL.validationPointIgnoreCase(PhoneNumber, c.get("Cphone").toString(), input, c);*/
                       
                        
                        
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
                        
                        
                        
                        
                        
                        
                        
                    }
                    
		 	            
		            break;
		            }
	             }
	    
	         
	         if(v1*v2*v3*v4*v5*v6*v7*v8*v9*v10*v11*v12*v13*v14*v15*callFound ==1)
	         {
	        	
	         	 Application.showMessage("WebService Call :: OrderUpdate is Success[All validation points are Success]");
                 
	         }
	         else
	         {
	        	
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::Order Update is Failed with Validation Errors");

	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		 
		// return OutPut; 
	 }
	
	public void confirmServiceRequest_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		 Thread.sleep(6000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0, v2 = 0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("********confirmServiceRequest_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ofs:OfferMgmtServicePort/confirmServiceRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
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
			            	 if(oft_shoppingAction.equals("CHG"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-oft_shoppingAction********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
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
