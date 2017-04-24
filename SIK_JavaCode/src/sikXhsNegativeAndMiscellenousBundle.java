import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class sikXhsNegativeAndMiscellenousBundle
{

	int Output=1;
	
	
	public void readValuesfromswivelCSGxhsExcel(Object input, ScriptingContext c)throws IOException
	{
		 Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
		    String XML_EndPoint=c.getValue("SwivelCSG", "Swivel_csgDBSettings: XML_Enpoint");
		    String DB_Host=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_HOST");
		    String dB_Password=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_Password");
		    String dB_Username=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_UserName");
		    String dB_Port=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_Port");
		    String dB_serviceName=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_ServiceName");
		    String dB_connectionName=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_ConnectionName");
		    String dB_PasswordCommon=c.getValue("SwivelCSG", "Swivel_csgDBSettings: Common_DBPassword");
		    String dB_UsernameCommon=c.getValue("SwivelCSG", "Swivel_csgDBSettings: Common_DBUserName");
            String InputAccountNumber = c.getValue("SwivelCSG", "sik_SwivelCSG: AccountId_ITEMLIST");
            String InputOrderID = c.getValue("SwivelCSG", "sik_SwivelCSG: OrderID");
            String InputLocation_ID = c.getValue("SwivelCSG", "sik_SwivelCSG: Location_ID");
            String InputEmail = c.getValue("SwivelCSG", "sik_SwivelCSG: Email");
            String InputFname = c.getValue("SwivelCSG", "sik_SwivelCSG: FirstName");
            String InputLname = c.getValue("SwivelCSG", "sik_SwivelCSG: LastName");
            String InputcustType = c.getValue("SwivelCSG", "sik_SwivelCSG: custType");
            String InputCustomerId = c.getValue("SwivelCSG", "sik_SwivelCSG: CustomerId");
            String InputTN = c.getValue("SwivelCSG", "sik_SwivelCSG: TN");
            String ErrCode=  c.getValue("SwivelCSG", "sik_SwivelCSG: ErrorCodeExpected");
            c.put("pErrorCode", ErrCode);
            String IsNegative=c.getValue("SwivelCSG", "sik_SwivelCSG: NegativeFlow");
            c.put("pIsNegative",IsNegative);
            
            String reqType=c.getValue("SwivelCSG", "sik_SwivelCSG: reqType");
            c.setValue("reqType",reqType);
            c.put("pReqType",reqType);
            
            String Group0=c.getValue("SwivelCSG", "sik_SwivelCSG: Group0");
            c.put("pGroup0",Group0);
            
            String Group1=c.getValue("SwivelCSG", "sik_SwivelCSG: Group1");
            c.put("pGroup1",Group1);
            
            
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
  		    c.setValue("dB_PasswordCommon",dB_PasswordCommon);
  		    c.setValue("dB_UsernameCommon",dB_UsernameCommon);
  		    
  		    
  		    c.setValue("OrderID", InputOrderID);
  		    c.setValue("accountNumber", InputAccountNumber);
  		    c.setValue("Email", InputEmail);
  		    c.setValue("Fname", InputFname);
  		    c.setValue("Lname", InputLname);
  		    c.setValue("locationID", InputLocation_ID);
  		    c.setValue("custType", InputcustType);
  		    c.setValue("CustomerId", InputCustomerId);
  		    c.setValue("TN", InputTN);
  		    
  		    
  		  Application.showMessage("InputAccountNumber is ::"+InputAccountNumber);
  		  Application.showMessage("InputOrderID is ::"+InputOrderID);
  		  Application.showMessage("InputEmail is ::"+InputEmail);
  		  Application.showMessage("InputFname is ::"+InputFname);
  		  Application.showMessage("InputLname is ::"+InputLname);
  		  Application.showMessage("InputLocation_ID is ::"+InputLocation_ID);
  		  Application.showMessage("InputcustType is ::"+InputcustType);
  		  Application.showMessage("InputCustomerId is ::"+InputCustomerId);
  		  Application.showMessage("InputTN is ::"+InputTN);
  		  Application.showMessage("ErrCode is ::"+ErrCode);
  		  Application.showMessage("reqType is ::"+reqType);
  		  Application.showMessage("Group0 is ::"+Group0);
  		  Application.showMessage("Group1 is ::"+Group1);
  		  Application.showMessage("Wheteher Negative Flow::?? is ::"+IsNegative);
  		    
  		    
		    
	}
	
	public int csgENImsg_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int LocNull=0;
		 int OrderIdNull=0;
		 int PhoneNull=0;
		 int CustInfoNull=0;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********csgENImsg_Validate Function**************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:CSGENIMessageServicePort/processCSGENIMessage' order by creation_time desc)where rownum <= 20");
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
		            
		               String OrderId_csgeni= sL.nodeFromKey(recievedata,"<OrderId>","</OrderId>");
		 	           Application.showMessage("Order ID is ::"+OrderId_csgeni); 
		 	           
		 	           if(OrderId_csgeni==null)
			            {
		 	        	    OrderIdNull=1;
		 	        	    c.put("pOrderIdNull", OrderIdNull);
		 	        	    Application.showMessage("OrderID is empty for the customer..!!");
				            continue;
			            }
		 	            else
		 	            {
				            if(OrderId_csgeni.equals(c.getValue("OrderID")))
			            	{
				            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
				            	Application.showMessage("SEND XML is :: \n"+senddata);
			            		//System.out.printf("SEND XML is %s \n",senddata);
			            		Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
			            		Application.showMessage("Validation is Successfull with Order ID"+OrderId_csgeni);
			            	    v2=1;
			            	    callFound=1;
			            	}
			            	else
			            	{
			            		c.report("OrderID Validation Failed!!!");
			            		break;
			            	}
			            
		 	            }
		 	      if(callFound==1)
			      {
		 	    	String recievedata_wf=sL.RemoveNameSpaces(recievedata);
		 	    	Application.showMessage("Well-Formed Recieve XML is::"+ recievedata_wf);
		 	    	
		 	    	String senddata_wf=sL.RemoveNameSpaces(senddata);
		 	    	Application.showMessage("Well-Formed Send XML is::"+ senddata_wf);
		 	    	
		 	    	String  accId_csgeni=sL.GetValueByXPath(recievedata_wf, "//msg/body/OrderDetailUpdated/AccountList/Account/AccountId");
		            //String accId_csgeni= sL.nodeFromKey(recievedata,"<Account><AccountId>","</AccountId><Classification>");
	 	            Application.showMessage("accountNumber is ::"+accId_csgeni); 	
	 	           
	 	            if(accId_csgeni==null)
		            {
			            c.report("Send Xml accId_csgeni is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(accId_csgeni.equals(c.getValue("accountNumber")))
		            	{
			            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
			            	Application.showMessage("SEND XML is :: \n"+senddata);
		            		//System.out.printf("SEND XML is %s \n",senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
		            		Application.showMessage("Validation is Successfull with Input accountNumber"+accId_csgeni);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	    		      
		            	String LocationId_csgeni= sL.nodeFromKey(recievedata,"<Location><LocationId>","</LocationId><Address>");
		 	            Application.showMessage("accountNumber is ::"+LocationId_csgeni); 	
		 	            if(LocationId_csgeni==null)
			            {
		 	               Application.showMessage("LocationID is empty for the customer..!!");
		 	            	LocNull=1;
		 	            	c.put("pLocNull", LocNull);
				            continue;
			            }
		 	            else
		 	            {
				            if(LocationId_csgeni.equals(c.getValue("locationID")))
			            	{
				            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
				            	Application.showMessage("SEND XML is :: \n"+senddata);
			            		//System.out.printf("SEND XML is %s \n",senddata);
			            		Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
			            		Application.showMessage("Validation is Successfull with locationID"+LocationId_csgeni);
			            	    v1=1;
			            	}
			            	else
			            	{
			            		
			            		c.report("locationID Validation Failed!!!");
			            		break;
			            	}
			            
		 	            }
		 	            
		 	          
		 	           
		 	           String Homeph_csgeni= sL.nodeFromKey(recievedata,"<Phone><Home>","</Home><Business>");
		 	           Application.showMessage("Home Phone is ::"+Homeph_csgeni); 
		 	           
		 	           String Bussinessph_csgeni= sL.nodeFromKey(recievedata,"</Home><Business>","</Business></Phone>");
		 	           Application.showMessage("Bussiness Phone is ::"+Bussinessph_csgeni); 
		 	           
		 	           if(Homeph_csgeni==null && Bussinessph_csgeni==null)
			            {
		 	        	   
		 	        	     Application.showMessage("Home and Bussiness Phone Numbers are empty for the customer..!!");
		 	        	     PhoneNull=1;
		 	        	    c.put("pPhoneNull", PhoneNull);
				             continue;
			            }
		 	            
		 	           
		 	           String CustomerId_csgeni= sL.nodeFromKey(recievedata,"<CustomerId>","</CustomerId>");
		 	           Application.showMessage("CustomerId is ::"+OrderId_csgeni); 
		 	           
		 	           String Firstn_csgeni= sL.nodeFromKey(recievedata,"<Name><First>","</First><Last>");
		 	           Application.showMessage("First Name is ::"+OrderId_csgeni); 
		 	           
		 	           String LastN_csgeni= sL.nodeFromKey(recievedata,"</First><Last>","</Last>");
		 	           Application.showMessage("Last Name is ::"+OrderId_csgeni); 
		 	           
		 	           if(CustomerId_csgeni==null || Firstn_csgeni==null ||LastN_csgeni==null  )
			            {
		 	        	    CustInfoNull=1;
		 	        	    c.put("pCustInfoNull", CustInfoNull);
				            continue;
			            }
		 	            else
		 	            {
				            if(CustomerId_csgeni.equals(c.getValue("CustomerId")))
			            	{
				            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
				            	Application.showMessage("SEND XML is :: \n"+senddata);
			            		//System.out.printf("SEND XML is %s \n",senddata);
			            		Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
			            		Application.showMessage("Validation is Successfull with Customer ID"+CustomerId_csgeni);
			            	    v3=1;
			            	}
			            	else
			            	{
			            		c.report("CustomerId Validation Failed!!!");
			            	}
			            
		 	            }
		 	           
		 	        
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
	        	 Output=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
	         }
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		 return Output;
		
	 }
	
	
	public int OrderSearch_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********OrderSearch_Validate Function*************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/OrderSearch' order by creation_time desc)where rownum <= 10");
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
		            
		            String insideXML1= sL.RemoveNameSpaces(senddata); 
            	    Application.showMessage("Well-Formed XML for SEND DATA is ::"+insideXML1);
            	    
            	    
            	    
            	    String AccountNumber_OrderSeach= sL.GetValueByXPath(insideXML1,"/OrderSearch/Request/AccountNumber"); 
		       	    Application.showMessage("AccountNumber_OrderSeach is ::" +AccountNumber_OrderSeach);
		       	    
		      
		       	    String insideXML2= sL.RemoveNameSpaces(recievedata); 
         	        Application.showMessage("Well-Formed XML for recievedata DATA is ::"+insideXML2);
         	    
		        	
		       	  
	 	           
		       	 
		       	    
		       	    Application.showMessage("Account Number from Outside::"+c.getValue("accountNumber"));
	 	            if(AccountNumber_OrderSeach==null)
		            {
			            c.report("Send Xml AccountNumber_OrderSeach is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(AccountNumber_OrderSeach.equals(c.getValue("accountNumber")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-AccountNumber_OrderSeach Call********");
		            		Application.showMessage("Validation is Successfull with AccountNumber_OrderSeach"+AccountNumber_OrderSeach);
		            		v1=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            
	 	            String WorkOrderID_OrderSeach= sL.GetValueByXPath(insideXML1,"/OrderSearch/Request/WorkOrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +WorkOrderID_OrderSeach);	
		       	    
	 	            if(WorkOrderID_OrderSeach==null)
		            {
			            c.report("Send Xml WorkOrderID_OrderSeach is NULL");
			            continue;
		            }
	 	            else
	 	            {
	 	            	if(WorkOrderID_OrderSeach.equalsIgnoreCase(c.getValue("OrderID")))
	 	            	{
	 	            		Application.showMessage("Validation of WorkOrderID is Successfull with Input");
	 	            		v2=1;
	 	            	}
	 	            	else
	 	            	{
	 	            		Application.showMessage("WorkOrderId Validation is NOT Successfull");
	 	            	}
	 	            }
	 	            
	 	            
	 	            String Details_OrderSeach= sL.GetValueByXPath(insideXML2,"/OrderSearchResponse/OrderSearchResult/Details"); 
		       	    Application.showMessage("Details_OrderSeach is ::" +Details_OrderSeach);
		       	    
		       	    String OrderIDCount_OrderSeach= sL.nodeFromKey(insideXML2,"<Details>","order(s) found</Details>");
	 	            Application.showMessage(" OrderIDCount_OrderSeach is ::"+OrderIDCount_OrderSeach); 
	 	            
	 	            
	 	            
	 	            String i=OrderIDCount_OrderSeach;
	 	            
	 	            if(i.trim().equalsIgnoreCase("0"))
	 	            {
	 	            	Application.showMessage("Zero Orders Found.. So no Cancel call and Further Calls");
	 	            	v3=1;
	 	            }
	 	            else
	 	            {
	 	            
	 	            String OrderID_OrderSeach= sL.GetValueByXPath(insideXML2,"/OrderSearchResponse/OrderSearchResult/OrdersFound/OrderFound["+i+"]/OrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +OrderID_OrderSeach);	
	 	            
		       	    //Application.showMessage("Order ID from Submit SIK is ::"+c.get("psik_OrderID"));
		       	    
	 	            if(OrderID_OrderSeach==null)
		            {
			            c.report("Send Xml WorkOrderID_OrderSeach is NULL");
			            continue;
		            }
	 	            else
	 	            {
	 	            	c.put("pOrderID_OrderSeach", OrderID_OrderSeach);
	 	            	v3=1;
	 	            }
	 	            }
	 	           /* else
	 	            {
			            if(OrderID_OrderSeach.equals(c.get("psik_OrderID")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-OrderID_OrderSeach Call********");
		            		Application.showMessage("Validation is Successfull with OrderID_OrderSeach"+OrderID_OrderSeach);
		            		v3=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("Order ID is Missing in Order Search");
		            		
		            		break;
		            	}
		            
	 	            }*/
	 	            
	 	      
		            
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1*v2*v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: OrderSearch is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 Output=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::OrderSearch is Failed with Validation Errors");
	         }
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		 return Output;
	 }
	
	
	
	public int CancelOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********CancelOrder_Validate Function*************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/CancelOrder' order by creation_time desc)where rownum <= 50");
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
		            
		            String insideXML1= sL.RemoveNameSpaces(senddata);
            	    Application.showMessage("Well - Formed XML for SEND DATA is ::"+insideXML1);
            	    
            	    String insideXML2= sL.RemoveNameSpaces(recievedata);
            	    Application.showMessage("Well - Formed XML for recievedata  is ::"+insideXML2);
	          	    
            	    String OrderID_Cancel= sL.GetValueByXPath(insideXML1,"/CancelOrder/Request/OrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +OrderID_Cancel);	
		       	  //  Application.showMessage("Order ID from Submit SIK is ::"+c.get("psik_OrderID"));
	 	            if(OrderID_Cancel==null)
		            {
			            c.report("Send Xml OrderID_Cancel is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(OrderID_Cancel.equals(c.get("pOrderID_OrderSeach")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-OrderID_Cancel Call********");
		            		Application.showMessage("Validation is Successfull with OrderID_Cancel"+OrderID_Cancel);
		            		v1=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("Order ID is Missing in Order Search");
		            		
		            		break;
		            	}
		            
	 	            }
                     
	 	            String Details_Cancel= sL.GetValueByXPath(insideXML2,"/CancelOrderResponse/CancelOrderResult/Details"); 
		       	    Application.showMessage("Details_Cancel is ::" +Details_Cancel);
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1==1)
	         {
	         	Application.showMessage("WebService Call :: CancelOrder is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 Output=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::CancelOrder is Failed with Validation Errors");
	         }
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		 return Output;
	 }
	
	
	//-------------------------------------------------------------------------------------
	
	
	
	
	public int processHomeSecurityInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	    sikLibraryClass sL=new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	          //  rowmsg=rs.getString(1);
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
		          	           
		            String accountNumber_DDS= sL.nodeFromKey(senddata,"<messagesDDS:accountNumber>","</messagesDDS:accountNumber>");
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
		      
		 	            String firstName_DDS= sL.nodeFromKey(senddata,"<typesDDS:firstName>","</typesDDS:firstName>");
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

		 	            String lastName_DDS= sL.nodeFromKey(senddata,"<typesDDS:lastName>","</typesDDS:lastName>");
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
		 	            String homeSecurityLOBStatus_DDS= sL.nodeFromKey(senddata,"<typesDDS:homeSecurityLOBStatus>","</typesDDS:homeSecurityLOBStatus>");
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
		 	            
		 	            String permitRequired_DDS= sL.nodeFromKey(senddata,"<typesDDS:permitRequired>","</typesDDS:permitRequired>");
		 	            Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
		 	           
		 	         	 	            
		 	            String authorizationGuid_DDS= sL.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/12.06/messages\">","</mes:authorizationGuid>");
		 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
		 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
		 	            
		 	            String responseStatus_DDS= sL.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/esd-directory/12.06/types\">","</typ:responseStatus>");
		 	            Application.showMessage("responseStatus is::"+responseStatus_DDS); 
		 	           if(responseStatus_DDS==null)
			            {
				            c.report(" responseStatus is NULL");
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
				            	 c.report("responseStatus at Send Xml not Validated as "+responseStatus_DDS);
				             }
			            }
		 	                    

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(callFound*v3*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 Output=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
	         }
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		 return Output;
	 }

	
	public int createAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		sikLibraryClass sL=new sikLibraryClass();
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
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	          //  rowmsg=rs.getString(1);
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
		          	           
		            String accountId_ucontrolsrv = sL.nodeFromKey(senddata,"<ucontrolsrv:accountId>","</ucontrolsrv:accountId>");
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
		      
		 	            String status_ucontrolsrv= sL.nodeFromKey(senddata,"<ucontrolsrv:status>","</ucontrolsrv:status>");
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

		 	           /* String firstName_ucontrolsrv= sL.nodeFromKey(senddata,"<ucontrolsrv:firstName>","</ucontrolsrv:firstName>");
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

		 	            String lastName_ucontrolsrv= sL.nodeFromKey(senddata,"<ucontrolsrv:lastName>","</ucontrolsrv:lastName>");
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

		 	            String ucontrolsrv_phoneNumber= sL.nodeFromKey(senddata,"<ucontrolsrv:phoneNumber>","</ucontrolsrv:phoneNumber>");
		 	            Application.showMessage("ucontrolsrv:phoneNumber is ::"+ucontrolsrv_phoneNumber); 
		 	            c.setValue("phoneNumber", ucontrolsrv_phoneNumber);
		 	            
		 	            String ucontrolsrv_emailAddress= sL.nodeFromKey(senddata,"<ucontrolsrv:emailAddress>","</ucontrolsrv:emailAddress>");
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
		 	            
		 	            String ucontrolsrv_address1= sL.nodeFromKey(senddata,"<ucontrolsrv:address1>","</ucontrolsrv:address1>");
		 	            Application.showMessage("ucontrolsrv:address1 is ::"+ucontrolsrv_address1); 
		 	            c.setValue("Address", ucontrolsrv_address1);
		 	           
		 	            String ucontrolsrv_city= sL.nodeFromKey(senddata,"<ucontrolsrv:city>","</ucontrolsrv:city>");
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
		 	            
		 	          /*  String ucontrolsrv_province= sL.nodeFromKey(senddata,"<ucontrolsrv:province>","</ucontrolsrv:province>");
		 	            Application.showMessage("ucontrolsrv:province is ::"+ucontrolsrv_province); 
		 	            
		 	            String ucontrolsrv_postasLode= sL.nodeFromKey(senddata,"<ucontrolsrv:postasLode>","</ucontrolsrv:postasLode>");
		 	            Application.showMessage("ucontrolsrv:postasLode is ::"+ucontrolsrv_postasLode); 
		 	            
		 	            c.setValue("PostasLode", ucontrolsrv_postasLode);
		 	            
		 	            String ucontrolsrv_portalUserSSOId= sL.nodeFromKey(senddata,"<ucontrolsrv:portalUserSSOId>","</ucontrolsrv:portalUserSSOId>");
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
		 	        /*   
		 	          ResultMap = .splitter(c.getValue("dS_SIK", "Sik_Dotcom: Service"));
		 		      c.setValue("ExistingGroup",ResultMap.get("group"));
		 		      c.setValue("ExistingService", ResultMap.get("service"));
		 		      c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
		 	           */
		 		      Application.showMessage("Service to be installed is::"+c.getValue("dS_SIK", "Sik_Dotcom: Service"));
		 	            if(c.getValue("IsMultiExist").equals("true"))
		 	            {
		 	            	
		 	            	
		 	            		String ucontrolsrv_group1= sL.nodeFromKey(senddata,"</ucontrolsrv:product><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
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
		 	            	
			 	           
			 	            String ucontrolsrv_group2= sL.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group></account>");
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
		 	            
			 	            String ucontrolsrv_group= sL.nodeFromKey(senddata,"<ucontrolsrv:group>","</ucontrolsrv:group>");
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
		 	             	         	 	            
		 	            String errorCode_createAcc= sL.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
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
		 	            
		 	            
		 	            String accountId_createAcc= sL.nodeFromKey(recievedata,"<accountId xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</accountId>");
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
	        	 Output=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::CreateAccount is Failed with Validation Errors");
	         }
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		 return Output;
	 }

	
	
	public int submitOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
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
	        	 Output=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
	         }
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		
		}
		 return Output;
	 }
	
	
	public int orderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
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
	        	 Output=0;
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
	         }
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		 return Output;
	 }
	
	public void CommonDBValidate(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, SQLException
    {
           sikLibraryClass  sL= new sikLibraryClass ();
           ResultSet rs1;
           Date todayDate = new Date();
           TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
           DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
           todayDateFormat.setTimeZone(timeZone);
           String strTodayDate = todayDateFormat.format(todayDate);
           Application.showMessage("Todays Date as per EST is::"+strTodayDate); 
           
           try
           {
                  
                  Statement st1 =sL.dBConnectCommon(input, c);
                  
                  Application.showMessage("I am here");
                  //Application.showMessage("Account Number is "+c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber"));
              String Acc=c.getValue("SwivelCSG","sik_SwivelCSG: AccountId_ITEMLIST");
                  rs1 = st1.executeQuery("select * from(select * from cwpworklist where ACCOUNTNUMBER ='"+Acc+"' AND creation_date > '"+strTodayDate+"')where rownum < 2");
                  if(rs1==null)
                  {
                        Application.showMessage("Null Record set found in WorKlist DB for AccountNumber'"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber"));
                  }
          
             while(rs1.next())
       {
             String errorCode= rs1.getString("ERRORCODE");
          String errorSource=rs1.getString("ERRORSOURCE");
          Application.showMessage("Error Source from common is" +errorSource);
          String errorText=rs1.getString("ERRORTEXT");
          String PARTICIPANTOPERATION=rs1.getString("PARTICIPANTOPERATION");
          //String BILLINGSYSTEM=rs1.getString("BILLINGSYSTEM");
          String SERVICETYPE=rs1.getString("SERVICETYPE");
          String ORDER_ID=rs1.getString("ORDER_ID");
             Application.showMessage("Error Code is ::"+errorCode);
             Application.showMessage("errorSource is ::"+errorSource);
             Application.showMessage("PARTICIPANTOPERATION is ::"+PARTICIPANTOPERATION);
             Application.showMessage("errorText is ::"+errorText);
            // Application.showMessage("BILLINGSYSTEM is ::"+BILLINGSYSTEM);
             Application.showMessage("SERVICETYPE is ::"+SERVICETYPE);
             Application.showMessage("ORDER_ID is ::"+ORDER_ID);
             
             Application.showMessage("****** Validating Common Data Base worklisting********");
             if(errorText.equals(c.get("ErrorTextOu")))
             {
                   Application.showMessage("Error text from order update is validated successfully with error text from common DB");
             }
             else
             {
                   Application.showMessage("ERROR TEXT IS NOT VALIDATED");
             }
             
             if(errorCode.equals(c.get("ErrorCodeOu")))
             {
                   Application.showMessage("Error code from order update is validated successfully with error code from common DB"); 
             }
             else
             {
                   Application.showMessage("ERROR CODE IS NOT VALIDATED");
             }
             
             if(errorSource.equals(c.get("ErrorSRCOu")))
             {
                   Application.showMessage("Error SOURCE from order update is validated successfully with error Source from common DB"); 
             }
             else
             {
                   Application.showMessage("ERROR SOURCE IS NOT VALIDATED");
             }
             
             
       }
             
             
             
             st1.close();
           }
           catch (SQLException e)
           {
               System.out.println("Connection Failed! Check output console");
                  e.printStackTrace();
                  return;
           }
           
           
    }

	
	public int orderUpdateNegative_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
    {
           Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
         sikLibraryClass sL = new sikLibraryClass();
           ResultSet  rs;
           int callFound=0,v1=0,v2=0,v3=0;
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
                         v1=1;
                  }
                  else
                  {
                         continue;
                  }
                  

                       if(callFound==1)
                       {
                 
                              String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"customerType\">");
                             Application.showMessage("productType is ::"+ou_productType);
                             
                              if(ou_productType==null)
                              {
                                    c.report("Send Xml productType is NULL");
                                    continue;
                              }
                              else
                              {
                                 Application.showMessage("productType Send  is ::"+" "+ou_productType);
                                 if(ou_productType.equalsIgnoreCase("SIK"))
                                     {
                                       Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
                                      Application.showMessage("Validation is Successfull with ou_productType::"+" "+ou_productType);
                                       v2=1;
                                     }
                                     else
                                     {
                                       c.report("ou_productType at Send Xml not Validated as "+ou_productType);
                                     }
                              }        



                             String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"errorText\">");
                             Application.showMessage("customerType is ::"+ou_customerType); 
                              if(ou_customerType==null)
                              {
                                    c.report(" ou_customerType is NULL");
                                    
                              }
                             
                             
                              String senddata_wf = sL.RemoveNameSpaces(senddata);
                             Application.showMessage("Well formed XML is "+senddata_wf);
                             String ordType= sL.GetValueByXPath(senddata_wf, "//comRequest/header/value[5]");
                            // String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
                             Application.showMessage("ordType is ::"+ordType); 
                              if(ordType==null)
                              {
                                    c.report("Send Xml ordType is NULL");
                                    
                              }
                               else if(ordType.equals("NEW"))
                               {
                                 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
                                 v3=1;
                               }
                               else if(ordType.equals("COS"))
                               {
                                 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
                                 v3=1;
                               }
                               else
                               {
                                 c.report("orderType at Send Xml not Validated as "+ordType);
                               }   
                              
                              String details= sL.nodeFromKey(senddata,"<value name=\"details\">","</value><value name=\"errorText\">");
                             Application.showMessage("Details  is ::"+details); 
                     
                             
                              String errCode= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[2]");

                            // String errCode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"details\">");
                             Application.showMessage("Error Code is ::"+errCode); 
                              c.put("ErrorCodeOu",errCode);
                           
                              String errmsg= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[4]");
                             //String errmsg= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorSource\">");
                             Application.showMessage("Error Message  is ::"+errmsg); 
                              c.put("ErrorTextOu",errmsg);
                           
                              String errsrc= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[5]");
                             //String errsrc= sL.nodeFromKey(senddata,"<value name=\"errorSource\">","</value><value name=\"orderType\">");
                             Application.showMessage("Error Source  is ::"+errsrc); 
                              
                              c.put("ErrorSourceOu",errsrc);
                            
                              CommonDBValidate(input, c);
                            

                             
                       break;
                       }
                 }
             }
             
             if(v1*callFound*v2*v3==1)
             {
                  Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
             }
             else
             {
            	   Output=0;
                   c.put("result", "false");
                   c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
             }
             st.close();
           }      
           catch (SQLException e)
           {
               System.out.println("Connection Failed! Check output console");
                  e.printStackTrace();
               
           }
           return Output;
    }

	
}
