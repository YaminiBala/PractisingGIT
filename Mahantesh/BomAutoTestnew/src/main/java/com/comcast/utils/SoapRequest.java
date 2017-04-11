package com.comcast.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Security;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import org.apache.poi.util.SystemOutLogger;
import org.testng.annotations.Test;

import com.comcast.reporting.*;
//import com.cognizant.framework.Status;
import com.comcast.bom.data.CustomerDetails;
import com.comcast.bom.data.DeviceDetails;
import com.comcast.bom.data.LoginDetails;
import com.comcast.bom.data.Ms;

public class SoapRequest extends ComcastTest{

	private static final java.lang.String Orderidd = null;

	public  String Endpoint;
	public  String Request;
	public  String Response;
	public LoginDetails loginInfo;
	public  ResultSet rSet;
	public  int total=0;
	String cibaResponse = null;
	int orderid ;
	String device1 = null;
	String device2=null;
	String device3 = null;
	String device4=null;
	String device5 = null;
	String device6=null;
	 String cmmac = null;
	 String mtamac = null;
	 String cmmac1 = null;
	 String mtamac1 = null;
	 String cmmac2 = null;
	 String mtamac2 = null;
	 String unit1=null;
	 String unit2=null;
	 String unit3=null;
	 String rfmac2=null;
	 int olddeviceID=0;
	 String rfmac=null;
	 
	 int groupid=0;
	 int groupid1=0;
	 int deviceid=0;
	 int deviceid1=0;
	 
	 //String fulfillmentcode=null;
	 String fulfillmentcode1=null;
	 String fulfillmentcode2=null;
	 String fulfillmentcode3=null;
	 String fulfillmentcode4=null;
	 String fulfillmentcode5=null;
	 String fulfillmentcode6=null;
	 int newdeviceID = 0;
	 int prodid=0;
	 int prodidnew=0;
	 int prodidne=0;
	 int prodidnee=0;
	 int device=0;
	 String dvr = null;
	 int devicenew=0;
	 int devicene=0;
	 int devicenee=0;
	 
	 String[] fid = null;
	 
	 String FULFILLMENTCODE=null;
	 String add,add1;
	public SoapRequest(SeleniumReport report)
	{
		this.report=report;
		
	}
	
	
	
	//this function is to submit order via EBIF
	public void submitEBIF()
	{
		CustomerDetails customerDetails = CustomerDetails
				.loadFromDatatable(dataTable);
		
		 try {

				URL url = new URL("http://bomint.u1.app.cloud.comcast.net/bomService/submitAccount");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				
				String inputnew="{\"accountNumber\" : \""+customerDetails.accountNumber+"\",\"sourceSystemId\" : \"EBIF\",\"trackingId\" : 1,\"orderType\" : \"X1\", \"attributes\" : [{ \"key\" : \"EBIFX1Only\", \"value\" : \"true\" } ]}";

				OutputStream os = conn.getOutputStream();
				os.write(inputnew.getBytes());
				
				System.out.println(inputnew);
				os.flush();

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				String output;
				System.out.println("Output from Server .... \n");
				while ((output = br.readLine()) != null) {
					System.out.println(output);
				}

				conn.disconnect();

			  } catch (MalformedURLException e) {

				e.printStackTrace();

			  } catch (IOException e) {

				e.printStackTrace();

			 }

	}

	//function for checking error in Device Write Back Errors table
	public void CheckForErrors() throws Exception
	{
		
		
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        String userName = "offshore";
        String password = "pass2012";
        String url = "jdbc:sqlserver://10.251.108.162:8152;DatabaseName=SIKOMS";
        Connection con = DriverManager.getConnection(url, userName, password);
        Connection connn = DriverManager.getConnection(url, userName, password);
        CustomerDetails customerDetails = CustomerDetails
				.loadFromDatatable(dataTable);
		   String query1="Select OrderID from sikoms.dbo.sikutilitydata where AccountNumber='"+customerDetails.accountNumber+"' order by OrderID Desc";
		                                //Select OrderID from sikoms.dbo.sikutilitydata where AccountNumber='8210100200601828' order by OrderID Desc";  
		   //Connection conn = DriverManager.getConnection("jdbc:sqlserver://10.252.112.168;port=8152","offshore", "pass2012");
		  PreparedStatement statement=connn.prepareStatement(query1);
		   //Statement statement = conn.createStatement();
		  ResultSet rs1 = statement.executeQuery();
		  while(rs1.next())
		  {
			  String Orderidd;
		                  Orderidd=rs1.getString("Orderid");
		                  //Orderid1=Integer.parseInt(Orderid);
		  System.out.println("Order Id"+Orderidd);
		  }
		
         Statement s1 = con.createStatement();
         ResultSet rs3= s1.executeQuery("SELECT  ErrorDescription from sikoms.DeviceWriteBackErrors where OnlineOrdersID='"+Orderidd+"'");

         if(rs3.next())
         {
         	
         	String error=rs.getString("ErrorDescription");
         	report.reportFailEvent("Order not in CLS status", "Error reason is  : "+error);
         	System.out.println(error);
         	
         }
         if(rs.next())
         {
         	
         	String error=rs.getString("ErrorDescription");
        	report.reportFailEvent("Order not in CLS status", "Error reason is  : "+error);
         	System.out.println(error);
         	
         }
         
		
	}

		
	public static ResultSet rs;
	public static ResultSet rs1;
	public static ResultSet rs2;
	public static ResultSet rs3;
	public static ResultSet rs4;

	
	
	/*public void SqlDbconnect()
	{
		
		try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String userName = "offshore";
            String password = "pass2012";
            String url = "jdbc:sqlserver://10.251.108.162:8152;DatabaseName=SIKOMS";
            Connection con = DriverManager.getConnection(url, userName, password);
            Connection con1 = DriverManager.getConnection(url, userName, password);
            Connection con2 = DriverManager.getConnection(url, userName, password);
            
            Connection conn = DriverManager.getConnection(url, userName, password);
            report.updateTestLog("Connection to SQL server_SIK DB", "Connection to SIK DB successful", Status.DONE);
            Statement s1 = con.createStatement();
            Statement s2 = con1.createStatement();
            Statement s3 = con2.createStatement();
            
            
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
           // ResultSet rs2=s3.executeQuery("use SIKOMS");
           
            
            CustomerDetails customerDetails = CustomerDetails
    				.loadFromDatatable(dataTable);
            System.out.println(customerDetails.accountNumber);
            
            ResultSet rs1= s2.executeQuery("Select OnlineOrdersID from sikoms.dbo.OnlineOrdersAccountInfo where Raw_Acct='"+customerDetails.accountNumber+"'");
            
            report.updateTestLog("Fetching device details", "Fetched successfully", Status.DONE);
           
            DeviceDetails deviceDetails= DeviceDetails
    				.loadFromDatatable(dataTable);
          
             device1=deviceDetails.serialno1;
             device2=deviceDetails.serialno2;
             
              device3=deviceDetails.serialno3;
              device4=deviceDetails.serialno4;
              cmmac = deviceDetails.cmMacAddress;
              mtamac = deviceDetails.eSTBAddress;
              cmmac1 = deviceDetails.cmMacAddress1;
              mtamac1 = deviceDetails.eSTBAddress1;
              unit1=deviceDetails.unit1;
              unit2=deviceDetails.unit2;
              rfmac=deviceDetails.rfmac;
              
              
              
            
            while (rs2.next()){
            	//int orderid = rs2.getInt("OrderID");
            	
            	
            	//System.out.println(orderid);
            	
             
              
               
                 
                }
            
            
             while (rs1.next()){
             	orderid= rs1.getInt("OnlineOrdersID");
             	
             	
             	System.out.println("SIK order id:-"+orderid);
                
                 }
             report.updateTestLog("Fetching SIK order ID ", "SIK order ID : "+orderid, Status.DONE);
             
             //taking the sikorder id from the above command and running it for taking fulfillment code and product id
             ResultSet rs = s1.executeQuery("SELECT DISTINCT OOPCD.GROUPID, OOPCD.ID, FG.FULFILLMENTCODE,OOPCD.ONLINEORDERSPRODUCTCODESID FROM SIKOMS.ONLINEORDERSPRODUCTCODEDEVICES OOPCD,SIKOMS.ONLINEORDERSFINISHEDGOODS OOFG, SIKOMS.FINISHEDGOODS FG WHERE OOPCD.ONLINEORDERSPRODUCTCODESID = OOFG.ONLINEORDERSPRODUCTCODESID AND OOFG.FINISHEDGOODID = FG.ID AND OOPCD.ONLINEORDERSPRODUCTCODESID IN (SELECT ID FROM SIKOMS.ONLINEORDERSPRODUCTCODES WHERE ONLINEORDERSID IN ( "+orderid+" )) ORDER BY OOPCD.GROUPID");
             String sqlStmt = "SELECT DISTINCT OOPCD.GROUPID, OOPCD.ID,FG.FULFILLMENTCODE FROM SIKOMS.ONLINEORDERSPRODUCTCODEDEVICES OOPCD,SIKOMS.ONLINEORDERSFINISHEDGOODS OOFG, SIKOMS.FINISHEDGOODS FG WHERE OOPCD.ONLINEORDERSPRODUCTCODESID = OOFG.ONLINEORDERSPRODUCTCODESID AND OOFG.FINISHEDGOODID = FG.ID AND OOPCD.ONLINEORDERSPRODUCTCODESID IN (SELECT ID FROM SIKOMS.ONLINEORDERSPRODUCTCODES WHERE ONLINEORDERSID IN ( "+orderid+" )) ORDER BY OOPCD.GROUPID";        
             rSet = stmt.executeQuery(sqlStmt);
             rSet.last(); 
          
             total = rSet.getRow();     
             System.out.println("total:-"+total);
             int i;
             if (rs.next()){
            	 
            	 int[] gid=new int[total];
            	 int[] pid=new int[total];
            	 
            	  fid=new String[total];
            	// int[] pid=new int[total];
            	 for( i=0;i<total;i++)
            	 {
            		 gid[i]=rs.getInt("GROUPID");
            		pid[i]=rs.getInt("ID");
            		   		 
            		fid[i]=rs.getString("FULFILLMENTCODE");
            		
            	 }
            	
            	
            	if(total==8||total==11||total==10||total==2||total==5||total==9||total==6)
            	{
            	 prodid=gid[0];
            	 prodidnew=gid[0]+1;
            	 prodidne=gid[0]+2;
            	 prodidnee=gid[0]+3;
            	 System.out.println(prodid); 
            	 System.out.println(prodidnew); 
            	 System.out.println(prodidne);
            	 System.out.println(prodidnee);
            	  device=pid[0];
              	devicenew=pid[0]+1;
              	 devicene=pid[0]+2;
            	 devicenee=pid[0]+3;
            	 System.out.println(fid[0]);
            	 if(fid[0]=="VID-DCX3200-MPEG4")
            	 {
            		
            		 dvr= "VID-DCX3200-MPEG4";
            	 }
            	
            	
            	}
            	else
            		System.out.println("test");
            	}
        	 
        con.close();
        con1.close();
        con2.close();
           
                System.out.print("\nconnected");    
       
        } catch (Exception e)
        {
            e.printStackTrace();
        }

	}	
	
	*/
	
	public   void CheckBillerStatusOpen()
	{
		CustomerDetails customerDetails = CustomerDetails
				.loadFromDatatable(dataTable);
		 
		Endpoint = "https://esp-int.cable.comcast.com:443/OrderManagementService/14.12";
		Request="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://xml.comcast.com/types\" xmlns:ser=\"http://xml.comcast.com/ordermanagement/services\" xmlns:typ1=\"http://xml.comcast.com/ordermanagement/types\" xmlns:xsi=\"xsi\">"
		   +"<soapenv:Header>"
		      +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
		         +"<wsse:UsernameToken wsu:Id=\"unt_Y6ubB0yj2glrTw6A\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
		           +" <wsse:Username>sikuser</wsse:Username>"
		           +" <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">uYdKbGS7</wsse:Password>"
		         +"</wsse:UsernameToken>"
		      +"</wsse:Security>"
		     +" <typ:requestHeader>"
		        +" <typ:timestamp>2014-11-18T08:14:06.521Z</typ:timestamp>"
		         +"<sourceSystemId>ESP-GRANDSLAM</sourceSystemId>"
		         +"<sourceSystemUserId>vdarga00c</sourceSystemUserId>"
		         +"<sourceServerId>HQSWL-C000580</sourceServerId>"
		         +"<trackingId>21c41d85-1670-44c3-033e-6d9e4ec8a818</trackingId>"
		     +" </typ:requestHeader>"
		   +"</soapenv:Header>"
		  +" <soapenv:Body>"
		      +"<ser:query>"
		        +" <ser:accountReference>"
		            +"<typ1:accountId>"
		               +"<typ1:accountNumber>"+customerDetails.accountNumber+"</typ1:accountNumber>"
		              +" <typ1:accountReference/>"
		           +" </typ1:accountId>"
		        +" </ser:accountReference>"
		         +"<ser:orderReference>"
		            +"<typ1:OrderId xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		           +" <typ1:OrderStatus>OPEN</typ1:OrderStatus>"
		           +" <typ1:OrderType xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		            +"<typ1:CreationDate xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		            +"<typ1:CompletionDate xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		        +" </ser:orderReference>"
		        +" <ser:resultSpec>"
		            +"<typ1:Ascending xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">true</typ1:Ascending>"
		            +"<typ1:MaxRows xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">1</typ1:MaxRows>"
		            +"<typ1:OrderBy xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		           +" <typ1:StartRow xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">1</typ1:StartRow>"
		        +" </ser:resultSpec>"
		     +" </ser:query>"
		  +"</soapenv:Body>"
		+"</soapenv:Envelope>";

		System.out.println("--------------------------------------");
		System.out.println("CIBA Request : "+Request);
			System.setProperty("java.protocol.handler.pkgs",
				"com.sun.net.ssl.internal.www.protocol");
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		try {

			URL endPoint = new URL(Endpoint);
			HttpURLConnection httpConn = (HttpURLConnection) endPoint
					.openConnection();
			httpConn.setRequestProperty("Content-Type",
					"text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", "http://xml.comcast.com/OMS");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buffer = new byte[Request.length()];
			buffer = Request.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			httpConn.setRequestProperty("Content-Length",
					String.valueOf(b.length));

			// Set the appropriate HTTP parameters.
			PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
			pw.write(Request);
			pw.flush();

			// Ready with sending the request.

			// Read the response.

			httpConn.connect();

			InputStreamReader isr = new InputStreamReader(
					httpConn.getInputStream());
			BufferedReader in = new BufferedReader(isr);

			// Write the SOAP message response to a String.
			while ((Response = in.readLine()) != null) {
				cibaResponse = Response;
			}
			//System.out.println("postCIBAResponse :" +cibaResponse);
			//System.out.println(cibaResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return cibaResponse;
		System.out.println(cibaResponse);
		if(cibaResponse==null)
		{
			report.reportFailEvent("Validating biller status", "Response : null");
			
		}
		String searchForThis1="OPEN";
		System.out.println("Search1="+cibaResponse.toUpperCase().contains(searchForThis1.toUpperCase()));
		
		if(cibaResponse.toUpperCase().contains(searchForThis1.toUpperCase())==true)
				{
			
			report.updateTestLog("Validation of biller status", "Account Status:OPEN ", Status.PASS);	
			
				}
	
	}
		
		
	public   void CheckBillerStatusCompleted()
	{
		
		CustomerDetails customerDetails = CustomerDetails
				.loadFromDatatable(dataTable);
		Endpoint = "https://esp-int.cable.comcast.com:443/OrderManagementService/14.12";
		Request="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:typ=\"http://xml.comcast.com/types\" xmlns:ser=\"http://xml.comcast.com/ordermanagement/services\" xmlns:typ1=\"http://xml.comcast.com/ordermanagement/types\" xmlns:xsi=\"xsi\">"
		   +"<soapenv:Header>"
		      +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
		         +"<wsse:UsernameToken wsu:Id=\"unt_Y6ubB0yj2glrTw6A\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
		           +" <wsse:Username>sikuser</wsse:Username>"
		           +" <wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">uYdKbGS7</wsse:Password>"
		         +"</wsse:UsernameToken>"
		      +"</wsse:Security>"
		     +" <typ:requestHeader>"
		        +" <typ:timestamp>2014-11-18T08:14:06.521Z</typ:timestamp>"
		         +"<sourceSystemId>ESP-GRANDSLAM</sourceSystemId>"
		         +"<sourceSystemUserId>vdarga00c</sourceSystemUserId>"
		         +"<sourceServerId>HQSWL-C000580</sourceServerId>"
		         +"<trackingId>21c41d85-1670-44c3-033e-6d9e4ec8a818</trackingId>"
		     +" </typ:requestHeader>"
		   +"</soapenv:Header>"
		  +" <soapenv:Body>"
		      +"<ser:query>"
		        +" <ser:accountReference>"
		            +"<typ1:accountId>"
		               +"<typ1:accountNumber>"+customerDetails.accountNumber+"</typ1:accountNumber>"
		              +" <typ1:accountReference/>"
		           +" </typ1:accountId>"
		        +" </ser:accountReference>"
		         +"<ser:orderReference>"
		            +"<typ1:OrderId xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		           +" <typ1:OrderStatus>COMPLETE</typ1:OrderStatus>"
		           +" <typ1:OrderType xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		            +"<typ1:CreationDate xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		            +"<typ1:CompletionDate xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		        +" </ser:orderReference>"
		        +" <ser:resultSpec>"
		            +"<typ1:Ascending xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">true</typ1:Ascending>"
		            +"<typ1:MaxRows xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">1</typ1:MaxRows>"
		            +"<typ1:OrderBy xsi:nil=\"true\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"/>"
		           +" <typ1:StartRow xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">1</typ1:StartRow>"
		        +" </ser:resultSpec>"
		     +" </ser:query>"
		  +"</soapenv:Body>"
		+"</soapenv:Envelope>";

		System.out.println("--------------------------------------");
		System.out.println("CIBA Request : "+Request);
			System.setProperty("java.protocol.handler.pkgs",
				"com.sun.net.ssl.internal.www.protocol");
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		try {

			URL endPoint = new URL(Endpoint);
			HttpURLConnection httpConn = (HttpURLConnection) endPoint
					.openConnection();
			httpConn.setRequestProperty("Content-Type",
					"text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", "http://xml.comcast.com/OMS");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buffer = new byte[Request.length()];
			buffer = Request.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			httpConn.setRequestProperty("Content-Length",
					String.valueOf(b.length));

			// Set the appropriate HTTP parameters.
			PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
			pw.write(Request);
			pw.flush();

			// Ready with sending the request.

			// Read the response.

			httpConn.connect();

			InputStreamReader isr = new InputStreamReader(
					httpConn.getInputStream());
			BufferedReader in = new BufferedReader(isr);

			// Write the SOAP message response to a String.
			while ((Response = in.readLine()) != null) {
				cibaResponse = Response;
			}
			//System.out.println("postCIBAResponse :" +cibaResponse);
			//System.out.println(cibaResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return cibaResponse;
		System.out.println(cibaResponse);
		String searchForThis="COMPLETE";
		if(cibaResponse==null||cibaResponse.toUpperCase().contains(searchForThis.toUpperCase())==false)
		{
			report.reportFailEvent("Validating biller status", "Status not in Closed");
			
		}
		
		System.out.println("Search1="+cibaResponse.toUpperCase().contains(searchForThis.toUpperCase()));
		
		if(cibaResponse.toUpperCase().contains(searchForThis.toUpperCase())==true)
				{
			report.updateTestLog("Validation of biller status", "Account Status:COMPLETED ", Status.PASS);
		
				}
	}
		

	public   void hitrequest() throws MalformedURLException {
		System.out.println("\nsoap test");
		Random rn        = new Random();
		int    range     = 9999999 - 1000000 + 1;  
		int    randomNum =  rn.nextInt(range) + 1000000;  // For 7 digit number
		System.out.println(randomNum);

		Random rc = new Random();
		char   c  = (char)(rc.nextInt(26) + 'A');
		System.out.println(c);
		char   c1  = (char)(rc.nextInt(26) + 'A');
		System.out.println(c1);
		

		 add = "0086"+randomNum+"D";    
		 add1 = "FD1F"+randomNum+"A";    
		System.out.println(add);
		System.out.println(add1);
		
		switch(fid[0])
		
		{
		case "VID-DCX3200-MPEG4":	
			
			//Random rn        = new Random();
			//int    range     = 9999999 - 1000000 + 1;  
			//int    randomNum =  rn.nextInt(range) + 1000000;  // For 7 digit number
			System.out.println(randomNum);

			//Random rc = new Random();
			char   c2  = (char)(rc.nextInt(26) + 'A');
			System.out.println(c2);
			char   c3  = (char)(rc.nextInt(26) + 'A');
			System.out.println(c3);
			

			 add = "M132"+randomNum+"D";    
			 add1 = "FD1F"+randomNum+"A";  
			
			
			
				Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

				Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+"<soapenv:Header>"
						+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
						+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
						+"<wsse:Username>Contec</wsse:Username>"
						+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
			       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
			        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
			     +"</wsse:UsernameToken>"
			 +" </wsse:Security>"
			  +"<com:requestHeader>"
			     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
			    +" <com:sourceSystemId/>"
			     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
			     +"<com:sourceServerId>1</com:sourceServerId>"
			     +"<com:trackingId>1</com:trackingId>"
			 +" </com:requestHeader>"
			 +"</soapenv:Header>"
			 +"<soapenv:Body>"
			  +"<com:PostShipment>"
			     +"<com:Request>"
			        +"<com:OrderID>"+orderid+"</com:OrderID>"
			        +"<com:Shipments>"
			           +"<com:Shipment>"
			              +"<com:Carrier>UPS</com:Carrier>"
			             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
			              +"<com:Method>Overnight</com:Method>"
			              +"<com:Packages>"
			                 +"<com:Package>"
			                   +" <com:Cost>25.34</com:Cost>"
			                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
			                   +" <com:ReturnTrackingNumber/>"
			                   +" <com:Weight>2.35</com:Weight>"
			                 +"</com:Package>"
			             +" </com:Packages>"
			                 
			             //for dct mpeg4
			              +"<com:ProductsShipped>"
			                +" <com:ProductShipped>"
			                   +" <com:ProductID>"+groupid+"</com:ProductID>"
			            	                        
			             //       +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
			                    +"<com:DevicesShipped>"
			                      +" <com:DeviceShipped>"
			                        +"  <com:Name/>"
			                         +" <com:Make>MOTO</com:Make>"
			                          +"<com:Model>DCX3200</com:Model>"
			                          +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
			                          +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
			                          +"<com:MACAddress/>"
			                          +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
			                          +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
			                          +"<com:MTAMACAddress/>"
			                          +"<com:MasterSubsidiaryLock/>"
			                          +"<com:USBMACAddress/>"
			                          +"<com:UnitID>"+unit1+"</com:UnitID>"
			                         +" <com:UnitID2/>"
			                         +" <com:Condition>New</com:Condition>"
			                         +" <com:DeviceType>STB</com:DeviceType>"
			                          +"<com:MACAddress2/>"
			                          +"<com:Make2>MOTO</com:Make2>"
			                          +"<com:Model2>MSCC9062</com:Model2>"
			                          +"<com:RFMACAddress/>"
			                          +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
			                      +" </com:DeviceShipped>"
			                    +"</com:DevicesShipped>"
			                     +"</com:ProductShipped>"
			                 
			                     //for dvr
			                     
			                    
				                    +" <com:ProductShipped>"
				                       +" <com:ProductID>"+groupid1+"</com:ProductID>"
				                	                        
				                        +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                          +" <com:DeviceShipped>"
				                            +"  <com:Name/>"
				                             +" <com:Make>MOTO</com:Make>"
				                              +"<com:Model>DCX3400</com:Model>"
				                              +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
				                              +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
				                              +"<com:MACAddress/>"
				                              +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
				                              +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
				                              +"<com:MTAMACAddress/>"
				                              +"<com:MasterSubsidiaryLock/>"
				                              +"<com:USBMACAddress/>"
				                              +"<com:UnitID>"+unit2+"</com:UnitID>"
				                             +" <com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType>STB</com:DeviceType>"
				                              +"<com:MACAddress2/>"
				                              +"<com:Make2>MOTO</com:Make2>"
				                              +"<com:Model2>MSCC9062</com:Model2>"
				                              +"<com:RFMACAddress/>"
				                              +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                        +"</com:DevicesShipped>"
				                         +"</com:ProductShipped>"
				                     
			                 
			                 
			                 
			                 
			                 //for rma drop downs
			               /* +" <com:ProductShipped>"
			                    +"<com:ProductID>"+prodidne+"</com:ProductID>"
			                   +" <com:FulfillmentCode>RMA-DF-LABEL-01</com:FulfillmentCode>"
			                    +"<com:DevicesShipped>"
			                       +"<com:DeviceShipped>"
			                          +"<com:Name/>"
			                          +"<com:Make/>"
			                          +"<com:Model/>"
			                         +" <com:SerialNumber/>"
			                          +"<com:SerialNumber2/>"
			                         +" <com:MACAddress/>"
			                         +" <com:CMACAddress/>"
			                          +"<com:EMACAddress/>"
			                         +" <com:MTAMACAddress/>"
			                         +" <com:MasterSubsidiaryLock/>"
			                         +" <com:USBMACAddress/>"
			                          +"<com:UnitID/>"
			                          +"<com:UnitID2/>"
			                         +" <com:Condition>New</com:Condition>"
			                         +" <com:DeviceType/>"
			                          +"<com:MACAddress2/>"
			                         +" <com:Make2/>"
			                          +"<com:Model2/>"
			                          +"<com:RFMACAddress/>"
			                         +" <com:DeviceID>"+devicene+"</com:DeviceID>"
			                      +" </com:DeviceShipped>"
			                   +" </com:DevicesShipped>"
			                +" </com:ProductShipped>"
			                 +"<com:ProductShipped>"
			                   +" <com:ProductID>"+prodidnee+"</com:ProductID>"
			                    +"<com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
			                   +" <com:DevicesShipped>"
			                       +"<com:DeviceShipped>"
			                         +" <com:Name/>"
			                          +"<com:Make/>"
			                          +"<com:Model/>"
			                          +"<com:SerialNumber/>"
			                          +"<com:SerialNumber2/>"
			                          +"<com:MACAddress/>"
			                          +"<com:CMACAddress/>"
			                          +"<com:EMACAddress/>"
			                          +"<com:MTAMACAddress/>"
			                          +"<com:MasterSubsidiaryLock/>"
			                          +"<com:USBMACAddress/>"
			                          +"<com:UnitID/>"
			                         +" <com:UnitID2/>"
			                          +"<com:Condition>New</com:Condition>"
			                          +"<com:DeviceType/>"
			                          +"<com:MACAddress2/>"
			                          +"<com:Make2/>"
			                         +" <com:Model2/>"
			                          +"<com:RFMACAddress/>"
			                          +"<com:DeviceID>"+devicenee+"</com:DeviceID>"
			                    +"   </com:DeviceShipped>"
			                   +" </com:DevicesShipped>"
			                 +"</com:ProductShipped>"*/
			              +"</com:ProductsShipped>"
			          +" </com:Shipment>"
			        +"</com:Shipments>"
			     +"</com:Request>"
			  +"</com:PostShipment>"
  +" </soapenv:Body>"
+"</soapenv:Envelope>";
				
				
				report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+prodid, Status.PASS);
				
				report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+olddeviceID, Status.PASS);
				
				//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+prodid+1, Status.PASS);
				
				//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+olddeviceID+1, Status.PASS);
				
		//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+prodid+2, Status.PASS);
				
				//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+olddeviceID+2, Status.PASS);
				
				report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
				
				report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
				report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+prodidnew, Status.PASS);
				report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+devicenew, Status.PASS);
				report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
				report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);		

				
				break;
				
				
case "VID-RNG110RF-MPEG4":	
			
			//Random rn        = new Random();
			//int    range     = 9999999 - 1000000 + 1;  
			//int    randomNum =  rn.nextInt(range) + 1000000;  // For 7 digit number
			 
			
			
			
				Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

				Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+"<soapenv:Header>"
						+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
						+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
						+"<wsse:Username>Contec</wsse:Username>"
						+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
			       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
			        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
			     +"</wsse:UsernameToken>"
			 +" </wsse:Security>"
			  +"<com:requestHeader>"
			     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
			    +" <com:sourceSystemId/>"
			     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
			     +"<com:sourceServerId>1</com:sourceServerId>"
			     +"<com:trackingId>1</com:trackingId>"
			 +" </com:requestHeader>"
			 +"</soapenv:Header>"
			 +"<soapenv:Body>"
			  +"<com:PostShipment>"
			     +"<com:Request>"
			        +"<com:OrderID>"+orderid+"</com:OrderID>"
			        +"<com:Shipments>"
			           +"<com:Shipment>"
			              +"<com:Carrier>UPS</com:Carrier>"
			             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
			              +"<com:Method>Overnight</com:Method>"
			              +"<com:Packages>"
			                 +"<com:Package>"
			                   +" <com:Cost>25.34</com:Cost>"
			                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
			                   +" <com:ReturnTrackingNumber/>"
			                   +" <com:Weight>2.35</com:Weight>"
			                 +"</com:Package>"
			             +" </com:Packages>"
			                 
			             //for dct mpeg4
			              +"<com:ProductsShipped>"
			                +" <com:ProductShipped>"
			                   +" <com:ProductID>"+prodid+"</com:ProductID>"
			            	                        
			                    +"<com:FulfillmentCode>VID-DCX3200-MPEG4</com:FulfillmentCode>"
			                    +"<com:DevicesShipped>"
			                      +" <com:DeviceShipped>"
			                        +"  <com:Name/>"
			                         +" <com:Make>MOTO</com:Make>"
			                          +"<com:Model>DCX3200</com:Model>"
			                          +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
			                          +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
			                          +"<com:MACAddress/>"
			                          +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
			                          +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
			                          +"<com:MTAMACAddress/>"
			                          +"<com:MasterSubsidiaryLock/>"
			                          +"<com:USBMACAddress/>"
			                          +"<com:UnitID>"+unit1+"</com:UnitID>"
			                         +" <com:UnitID2/>"
			                         +" <com:Condition>New</com:Condition>"
			                         +" <com:DeviceType>STB</com:DeviceType>"
			                          +"<com:MACAddress2/>"
			                          +"<com:Make2>MOTO</com:Make2>"
			                          +"<com:Model2>MSCC9062</com:Model2>"
			                          +"<com:RFMACAddress/>"
			                          +"<com:DeviceID>"+device+"</com:DeviceID>"
			                      +" </com:DeviceShipped>"
			                    +"</com:DevicesShipped>"
			                     +"</com:ProductShipped>"
			                 
			                     //for dvr
			                     
			                    
				                    +" <com:ProductShipped>"
				                       +" <com:ProductID>"+prodidnew+"</com:ProductID>"
				                	                        
				                        +"<com:FulfillmentCode>VID-DCX3400-MPEG4</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                          +" <com:DeviceShipped>"
				                            +"  <com:Name/>"
				                             +" <com:Make>MOTO</com:Make>"
				                              +"<com:Model>DCX3400</com:Model>"
				                              +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
				                              +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
				                              +"<com:MACAddress/>"
				                              +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
				                              +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
				                              +"<com:MTAMACAddress/>"
				                              +"<com:MasterSubsidiaryLock/>"
				                              +"<com:USBMACAddress/>"
				                              +"<com:UnitID>"+unit2+"</com:UnitID>"
				                             +" <com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType>STB</com:DeviceType>"
				                              +"<com:MACAddress2/>"
				                              +"<com:Make2>MOTO</com:Make2>"
				                              +"<com:Model2>MSCC9062</com:Model2>"
				                              +"<com:RFMACAddress/>"
				                              +"<com:DeviceID>"+devicenew+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                        +"</com:DevicesShipped>"
				                         +"</com:ProductShipped>"
				                     
			                 
			                 
			                 
			                 
			                 //for rma drop downs
			               /* +" <com:ProductShipped>"
			                    +"<com:ProductID>"+prodidne+"</com:ProductID>"
			                   +" <com:FulfillmentCode>RMA-DF-LABEL-01</com:FulfillmentCode>"
			                    +"<com:DevicesShipped>"
			                       +"<com:DeviceShipped>"
			                          +"<com:Name/>"
			                          +"<com:Make/>"
			                          +"<com:Model/>"
			                         +" <com:SerialNumber/>"
			                          +"<com:SerialNumber2/>"
			                         +" <com:MACAddress/>"
			                         +" <com:CMACAddress/>"
			                          +"<com:EMACAddress/>"
			                         +" <com:MTAMACAddress/>"
			                         +" <com:MasterSubsidiaryLock/>"
			                         +" <com:USBMACAddress/>"
			                          +"<com:UnitID/>"
			                          +"<com:UnitID2/>"
			                         +" <com:Condition>New</com:Condition>"
			                         +" <com:DeviceType/>"
			                          +"<com:MACAddress2/>"
			                         +" <com:Make2/>"
			                          +"<com:Model2/>"
			                          +"<com:RFMACAddress/>"
			                         +" <com:DeviceID>"+devicene+"</com:DeviceID>"
			                      +" </com:DeviceShipped>"
			                   +" </com:DevicesShipped>"
			                +" </com:ProductShipped>"
			                 +"<com:ProductShipped>"
			                   +" <com:ProductID>"+prodidnee+"</com:ProductID>"
			                    +"<com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
			                   +" <com:DevicesShipped>"
			                       +"<com:DeviceShipped>"
			                         +" <com:Name/>"
			                          +"<com:Make/>"
			                          +"<com:Model/>"
			                          +"<com:SerialNumber/>"
			                          +"<com:SerialNumber2/>"
			                          +"<com:MACAddress/>"
			                          +"<com:CMACAddress/>"
			                          +"<com:EMACAddress/>"
			                          +"<com:MTAMACAddress/>"
			                          +"<com:MasterSubsidiaryLock/>"
			                          +"<com:USBMACAddress/>"
			                          +"<com:UnitID/>"
			                         +" <com:UnitID2/>"
			                          +"<com:Condition>New</com:Condition>"
			                          +"<com:DeviceType/>"
			                          +"<com:MACAddress2/>"
			                          +"<com:Make2/>"
			                         +" <com:Model2/>"
			                          +"<com:RFMACAddress/>"
			                          +"<com:DeviceID>"+devicenee+"</com:DeviceID>"
			                    +"   </com:DeviceShipped>"
			                   +" </com:DevicesShipped>"
			                 +"</com:ProductShipped>"*/
			              +"</com:ProductsShipped>"
			          +" </com:Shipment>"
			        +"</com:Shipments>"
			     +"</com:Request>"
			  +"</com:PostShipment>"
  +" </soapenv:Body>"
+"</soapenv:Envelope>";
				
				
				report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+prodid, Status.PASS);
				
				report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+olddeviceID, Status.PASS);
				
				//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+prodid+1, Status.PASS);
				
				//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+olddeviceID+1, Status.PASS);
				
		//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+prodid+2, Status.PASS);
				
				//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+olddeviceID+2, Status.PASS);
				
				report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
				
				report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
				report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+prodidnew, Status.PASS);
				report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+devicenew, Status.PASS);
				report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
				report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);		

				
				break;
				
					
				
				
				
				
				
				
		case "VID-DCX3400-MPEG4":		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";
		 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				   +"<soapenv:Header>"
				      +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				        +" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				            +"<wsse:Username>Contec</wsse:Username>"
				            +"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
				           +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
				            +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
				         +"</wsse:UsernameToken>"
				     +" </wsse:Security>"
				      +"<com:requestHeader>"
				         +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
				        +" <com:sourceSystemId/>"
				         +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
				         +"<com:sourceServerId>1</com:sourceServerId>"
				         +"<com:trackingId>1</com:trackingId>"
				     +" </com:requestHeader>"
				   +"</soapenv:Header>"
				   +"<soapenv:Body>"
				      +"<com:PostShipment>"
				         +"<com:Request>"
				            +"<com:OrderID>"+orderid+"</com:OrderID>"
				            +"<com:Shipments>"
				               +"<com:Shipment>"
				                  +"<com:Carrier>UPS</com:Carrier>"
				                 +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
				                  +"<com:Method>Overnight</com:Method>"
				                  +"<com:Packages>"
				                     +"<com:Package>"
				                       +" <com:Cost>25.34</com:Cost>"
				                       +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
				                       +" <com:ReturnTrackingNumber/>"
				                       +" <com:Weight>2.35</com:Weight>"
				                     +"</com:Package>"
				                 +" </com:Packages>"
				                     
				                 //for dct mpeg4
				                  +"<com:ProductsShipped>"
				                    +" <com:ProductShipped>"
				                       +" <com:ProductID>"+prodid+"</com:ProductID>"
				                	                        
				                        +"<com:FulfillmentCode>VID-DCX3400-MPEG4</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                          +" <com:DeviceShipped>"
				                            +"  <com:Name/>"
				                             +" <com:Make>MOTO</com:Make>"
				                              +"<com:Model>DCX3400</com:Model>"
				                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
				                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
				                              +"<com:MACAddress/>"
				                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
				                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
				                              +"<com:MTAMACAddress/>"
				                              +"<com:MasterSubsidiaryLock/>"
				                              +"<com:USBMACAddress/>"
				                              +"<com:UnitID>"+unit1+"</com:UnitID>"
				                             +" <com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType>STB</com:DeviceType>"
				                              +"<com:MACAddress2/>"
				                              +"<com:Make2>MOTO</com:Make2>"
				                              +"<com:Model2>MSCC9062</com:Model2>"
				                              +"<com:RFMACAddress/>"
				                              +"<com:DeviceID>"+device+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                        +"</com:DevicesShipped>"
				                         +"</com:ProductShipped>"
				                     
				                         //for dvr
				                         
				                        
						                    +" <com:ProductShipped>"
						                       +" <com:ProductID>"+prodidnew+"</com:ProductID>"
						                	                        
						                        +"<com:FulfillmentCode>VID-DCX3200-MPEG4</com:FulfillmentCode>"
						                        +"<com:DevicesShipped>"
						                          +" <com:DeviceShipped>"
						                            +"  <com:Name/>"
						                             +" <com:Make>MOTO</com:Make>"
						                              +"<com:Model>DCX3200</com:Model>"
						                              +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
						                              +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
						                              +"<com:MACAddress/>"
						                              +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
						                              +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
						                              +"<com:MTAMACAddress/>"
						                              +"<com:MasterSubsidiaryLock/>"
						                              +"<com:USBMACAddress/>"
						                              +"<com:UnitID>"+unit2+"</com:UnitID>"
						                             +" <com:UnitID2/>"
						                             +" <com:Condition>New</com:Condition>"
						                             +" <com:DeviceType>STB</com:DeviceType>"
						                              +"<com:MACAddress2/>"
						                              +"<com:Make2>MOTO</com:Make2>"
						                              +"<com:Model2>MSCC9062</com:Model2>"
						                              +"<com:RFMACAddress/>"
						                              +"<com:DeviceID>"+devicenew+"</com:DeviceID>"
						                          +" </com:DeviceShipped>"
						                        +"</com:DevicesShipped>"
						                         +"</com:ProductShipped>"
						                     
				                     
				                     
				                     
				                     
				                     //for rma drop downs
				                   /* +" <com:ProductShipped>"
				                        +"<com:ProductID>"+prodidne+"</com:ProductID>"
				                       +" <com:FulfillmentCode>RMA-DF-LABEL-01</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                           +"<com:DeviceShipped>"
				                              +"<com:Name/>"
				                              +"<com:Make/>"
				                              +"<com:Model/>"
				                             +" <com:SerialNumber/>"
				                              +"<com:SerialNumber2/>"
				                             +" <com:MACAddress/>"
				                             +" <com:CMACAddress/>"
				                              +"<com:EMACAddress/>"
				                             +" <com:MTAMACAddress/>"
				                             +" <com:MasterSubsidiaryLock/>"
				                             +" <com:USBMACAddress/>"
				                              +"<com:UnitID/>"
				                              +"<com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType/>"
				                              +"<com:MACAddress2/>"
				                             +" <com:Make2/>"
				                              +"<com:Model2/>"
				                              +"<com:RFMACAddress/>"
				                             +" <com:DeviceID>"+devicene+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                       +" </com:DevicesShipped>"
				                    +" </com:ProductShipped>"
				                     +"<com:ProductShipped>"
				                       +" <com:ProductID>"+prodidnee+"</com:ProductID>"
				                        +"<com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
				                       +" <com:DevicesShipped>"
				                           +"<com:DeviceShipped>"
				                             +" <com:Name/>"
				                              +"<com:Make/>"
				                              +"<com:Model/>"
				                              +"<com:SerialNumber/>"
				                              +"<com:SerialNumber2/>"
				                              +"<com:MACAddress/>"
				                              +"<com:CMACAddress/>"
				                              +"<com:EMACAddress/>"
				                              +"<com:MTAMACAddress/>"
				                              +"<com:MasterSubsidiaryLock/>"
				                              +"<com:USBMACAddress/>"
				                              +"<com:UnitID/>"
				                              +"<com:UnitID2/>"
				                              +"<com:Condition>New</com:Condition>"
				                              +"<com:DeviceType/>"
				                              +"<com:MACAddress2/>"
				                              +"<com:Make2/>"
				                              +"<com:Model2/>"
				                              +"<com:RFMACAddress/>"
				                              +"<com:DeviceID>"+devicenee+"</com:DeviceID>"
				                        +"   </com:DeviceShipped>"
				                       +" </com:DevicesShipped>"
				                     +"</com:ProductShipped>"*/
				                  +"</com:ProductsShipped>"
				              +" </com:Shipment>"
				            +"</com:Shipments>"
				         +"</com:Request>"
				      +"</com:PostShipment>"
				  +" </soapenv:Body>"
				+"</soapenv:Envelope>";
					
				
		 
		 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+prodid, Status.DONE);
			
			report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+olddeviceID, Status.DONE);
			
			//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+prodid+1, Status.DONE);
			
			//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+olddeviceID+1, Status.DONE);
			
	//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+prodid+2, Status.DONE);
			
			//report.updateTestLog("The device id for RMA-DF device ", "Fetched successfully as : "+olddeviceID+2, Status.DONE);
			
			report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.DONE);		
			
			report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.DONE);		
			
			report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+prodidnew, Status.DONE);
			
			report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+devicenew, Status.DONE);
			
			report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.DONE);		
			
			report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.DONE);	
				
				break;
				
				
		case "VID-Xi3":	
			Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

			Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+"<soapenv:Header>"
					+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
					+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
					+"<wsse:Username>Contec</wsse:Username>"
					+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		     +"</wsse:UsernameToken>"
		 +" </wsse:Security>"
		  +"<com:requestHeader>"
		     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		    +" <com:sourceSystemId/>"
		     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		     +"<com:sourceServerId>1</com:sourceServerId>"
		     +"<com:trackingId>1</com:trackingId>"
		 +" </com:requestHeader>"
		 +"</soapenv:Header>"
		 +"<soapenv:Body>"
		  +"<com:PostShipment>"
		     +"<com:Request>"
		        +"<com:OrderID>"+orderid+"</com:OrderID>"
		        +"<com:Shipments>"
		           +"<com:Shipment>"
		              +"<com:Carrier>UPS</com:Carrier>"
		             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
		              +"<com:Method>Overnight</com:Method>"
		              +"<com:Packages>"
		                 +"<com:Package>"
		                   +" <com:Cost>25.34</com:Cost>"
		                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		                   +" <com:ReturnTrackingNumber/>"
		                   +" <com:Weight>2.35</com:Weight>"
		                 +"</com:Package>"
		             +" </com:Packages>"
		                 
		             //for dct mpeg4
		             +"<com:ProductsShipped>"
                     +"<com:ProductShipped>"
                        +"<com:ProductID>"+prodid+"</com:ProductID>"
                        +"<com:FulfillmentCode>VID-Xi3</com:FulfillmentCode>"
                       +" <com:DevicesShipped>"
                          +" <com:DeviceShipped>"
                             +" <com:Name/>"
                             +" <com:Make>PA</com:Make>"
                             +" <com:Model>PX032ANI</com:Model>"
                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                            +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
                             
                              
                       
                              +"<com:MasterSubsidiaryLock/>"
                             +" <com:USBMACAddress/>"
                            
                              +"<com:Condition>New</com:Condition>"
                              +"<com:DeviceType>STB</com:DeviceType>"
                          
                            
                            
                              
                              +"<com:DeviceID>"+device+"</com:DeviceID>"
                           +"</com:DeviceShipped>"
                        +"</com:DevicesShipped>"
                     +"</com:ProductShipped>"
		                     //for dvr
		                     
		                    
			                    +" <com:ProductShipped>"
			                       +" <com:ProductID>"+prodidnew+"</com:ProductID>"
			                	                        
			                        +"<com:FulfillmentCode>VID-X1SWap-MX011ANM</com:FulfillmentCode>"
			                        +"<com:DevicesShipped>"
			                          +" <com:DeviceShipped>"
			                            +"  <com:Name/>"
			                             +" <com:Make>MOTO</com:Make>"
			                              +"<com:Model>MX011ANM</com:Model>"
			                              +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
			                              +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
			                              +"<com:MACAddress/>"
			                              +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
			                              +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
			                              +"<com:MTAMACAddress/>"
			                              +"<com:MasterSubsidiaryLock/>"
			                              +"<com:USBMACAddress/>"
			                              +"<com:UnitID>"+unit2+"</com:UnitID>"
			                             +" <com:UnitID2/>"
			                             +" <com:Condition>New</com:Condition>"
			                             +" <com:DeviceType>STB</com:DeviceType>"
			                              +"<com:MACAddress2/>"
			                              +"<com:Make2>MOTO</com:Make2>"
			                              +"<com:Model2>MSCC9063</com:Model2>"
			                              +"<com:RFMACAddress/>"
			                              +"<com:DeviceID>"+devicenew+"</com:DeviceID>"
			                          +" </com:DeviceShipped>"
			                        +"</com:DevicesShipped>"
			                         +"</com:ProductShipped>"
			                     
		                 
		                 
		                 
		                 
		                 //for rma drop downs
		              
		               /*  +"<com:ProductShipped>"
		                   +" <com:ProductID>"+prodidne+"</com:ProductID>"
		                    +"<com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
		                   +" <com:DevicesShipped>"
		                       +"<com:DeviceShipped>"
		                         +" <com:Name/>"
		                          +"<com:Make/>"
		                          +"<com:Model/>"
		                          +"<com:SerialNumber/>"
		                          +"<com:SerialNumber2/>"
		                          +"<com:MACAddress/>"
		                          +"<com:CMACAddress/>"
		                          +"<com:EMACAddress/>"
		                          +"<com:MTAMACAddress/>"
		                          +"<com:MasterSubsidiaryLock/>"
		                          +"<com:USBMACAddress/>"
		                          +"<com:UnitID/>"
		                         +" <com:UnitID2/>"
		                          +"<com:Condition>New</com:Condition>"
		                          +"<com:DeviceType/>"
		                          +"<com:MACAddress2/>"
		                          +"<com:Make2/>"
		                         +" <com:Model2/>"
		                          +"<com:RFMACAddress/>"
		                          +"<com:DeviceID>"+devicene+"</com:DeviceID>"
		                    +"   </com:DeviceShipped>"
		                   +" </com:DevicesShipped>"
		                 +"</com:ProductShipped>"*/
		              +"</com:ProductsShipped>"
		          +" </com:Shipment>"
		        +"</com:Shipments>"
		     +"</com:Request>"
		  +"</com:PostShipment>"
+" </soapenv:Body>"
+"</soapenv:Envelope>";
			
			
			
			report.updateTestLog("Fetching DCT device ", "Product ID : "+prodid, Status.DONE);
			
			report.updateTestLog("Fetching DCT device ", "Device ID : "+device, Status.DONE);
			
			report.updateTestLog("Fetching DVR device ", "Product ID : "+prodidnew, Status.DONE);

			
			report.updateTestLog("Fetching DVR device  ", "Device ID : "+devicenew, Status.DONE);
			
			//report.updateTestLog("Fetching RMA easy returns  ", "Product ID : "+prodidne, Status.PASS);
			
			//report.updateTestLog("The device id for RMA-DF device ", "Device ID : "+devicene, Status.PASS);
			
	
			
			report.updateTestLog("Fetching DCT device Host serial#  ", "Host serial# : "+device1, Status.PASS);		
			
			//report.updateTestLog("Fetching DCT device MCard serial#  ", "MCard serial# : "+device2, Status.PASS);
			
			report.updateTestLog("Fetching DVR device Host serial#  ", "Host serial# : "+device3, Status.PASS);		
			report.updateTestLog("Fetching DVR device MCard serial#   ", "MCard serial# : "+device4, Status.PASS);		
			
			
			
			break;
			
		case "VID-X1Swap-AX013ANC":	
			Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

			Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+"<soapenv:Header>"
					+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
					+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
					+"<wsse:Username>Contec</wsse:Username>"
					+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		     +"</wsse:UsernameToken>"
		 +" </wsse:Security>"
		  +"<com:requestHeader>"
		     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		    +" <com:sourceSystemId/>"
		     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		     +"<com:sourceServerId>1</com:sourceServerId>"
		     +"<com:trackingId>1</com:trackingId>"
		 +" </com:requestHeader>"
		 +"</soapenv:Header>"
		 +"<soapenv:Body>"
		  +"<com:PostShipment>"
		     +"<com:Request>"
		        +"<com:OrderID>"+orderid+"</com:OrderID>"
		        +"<com:Shipments>"
		           +"<com:Shipment>"
		              +"<com:Carrier>UPS</com:Carrier>"
		             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
		              +"<com:Method>Overnight</com:Method>"
		              +"<com:Packages>"
		                 +"<com:Package>"
		                   +" <com:Cost>25.34</com:Cost>"
		                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		                   +" <com:ReturnTrackingNumber/>"
		                   +" <com:Weight>2.35</com:Weight>"
		                 +"</com:Package>"
		             +" </com:Packages>"
		                 
 +"<com:ProductsShipped>"
		                     //for dvr
		                     
		                    
			                    +" <com:ProductShipped>"
			                       +" <com:ProductID>"+prodid+"</com:ProductID>"
			                	                        
			                        +"<com:FulfillmentCode>VID-X1SWap-MX011ANM</com:FulfillmentCode>"
			                        +"<com:DevicesShipped>"
			                          +" <com:DeviceShipped>"
			                            +"  <com:Name/>"
			                             +" <com:Make>MOTO</com:Make>"
			                              +"<com:Model>MX011ANM</com:Model>"
			                              +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
			                              +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
			                              +"<com:MACAddress/>"
			                              +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
			                              +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
			                              +"<com:MTAMACAddress/>"
			                              +"<com:MasterSubsidiaryLock/>"
			                              +"<com:USBMACAddress/>"
			                              +"<com:UnitID>"+unit1+"</com:UnitID>"
			                             +" <com:UnitID2/>"
			                             +" <com:Condition>New</com:Condition>"
			                             +" <com:DeviceType>STB</com:DeviceType>"
			                              +"<com:MACAddress2/>"
			                              +"<com:Make2>MOTO</com:Make2>"
			                              +"<com:Model2>MSCC9063</com:Model2>"
			                              +"<com:RFMACAddress/>"
			                              +"<com:DeviceID>"+device+"</com:DeviceID>"
			                          +" </com:DeviceShipped>"
			                        +"</com:DevicesShipped>"
			                         +"</com:ProductShipped>"
			                     
		                 
			                         //for dct 
			    		             +"<com:ProductsShipped>"
			                         +"<com:ProductShipped>"
			                            +"<com:ProductID>"+prodidnew+"</com:ProductID>"
			                            +"<com:FulfillmentCode>VID-Xi3</com:FulfillmentCode>"
			                           +" <com:DevicesShipped>"
			                              +" <com:DeviceShipped>"
			                                 +" <com:Name/>"
			                                 +" <com:Make>PA</com:Make>"
			                                 +" <com:Model>PX032ANI</com:Model>"
			                                  +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
			                                +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
			                                 
			                                  
			                           
			                                  +"<com:MasterSubsidiaryLock/>"
			                                 +" <com:USBMACAddress/>"
			                                
			                                  +"<com:Condition>New</com:Condition>"
			                                  +"<com:DeviceType>STB</com:DeviceType>"
			                              
			                                
			                                
			                                  
			                                  +"<com:DeviceID>"+devicenew+"</com:DeviceID>"
			                               +"</com:DeviceShipped>"
			                            +"</com:DevicesShipped>"
			                         +"</com:ProductShipped>"
		                 
		                 
		                 //for rma drop downs
		              
		                 +"<com:ProductShipped>"
		                   +" <com:ProductID>"+prodidne+"</com:ProductID>"
		                    +"<com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
		                   +" <com:DevicesShipped>"
		                       +"<com:DeviceShipped>"
		                         +" <com:Name/>"
		                          +"<com:Make/>"
		                          +"<com:Model/>"
		                          +"<com:SerialNumber/>"
		                          +"<com:SerialNumber2/>"
		                          +"<com:MACAddress/>"
		                          +"<com:CMACAddress/>"
		                          +"<com:EMACAddress/>"
		                          +"<com:MTAMACAddress/>"
		                          +"<com:MasterSubsidiaryLock/>"
		                          +"<com:USBMACAddress/>"
		                          +"<com:UnitID/>"
		                         +" <com:UnitID2/>"
		                          +"<com:Condition>New</com:Condition>"
		                          +"<com:DeviceType/>"
		                          +"<com:MACAddress2/>"
		                          +"<com:Make2/>"
		                         +" <com:Model2/>"
		                          +"<com:RFMACAddress/>"
		                          +"<com:DeviceID>"+devicene+"</com:DeviceID>"
		                    +"   </com:DeviceShipped>"
		                   +" </com:DevicesShipped>"
		                 +"</com:ProductShipped>"
		              +"</com:ProductsShipped>"
		          +" </com:Shipment>"
		        +"</com:Shipments>"
		     +"</com:Request>"
		  +"</com:PostShipment>"
+" </soapenv:Body>"
+"</soapenv:Envelope>";
			
			
			
			report.updateTestLog("Fetching DCT device ", "Product ID : "+prodid, Status.DONE);
			
			report.updateTestLog("Fetching DCT device ", "Device ID : "+device, Status.DONE);
			
			report.updateTestLog("Fetching DVR device ", "Product ID : "+prodidnew, Status.DONE);

			
			report.updateTestLog("Fetching DVR device  ", "Device ID : "+devicenew, Status.DONE);
			
			//report.updateTestLog("Fetching RMA easy returns  ", "Product ID : "+prodidne, Status.PASS);
			
			//report.updateTestLog("The device id for RMA-DF device ", "Device ID : "+devicene, Status.PASS);
			
	
			
			report.updateTestLog("Fetching DCT device Host serial#  ", "Host serial# : "+device1, Status.PASS);		
			
			//report.updateTestLog("Fetching DCT device MCard serial#  ", "MCard serial# : "+device2, Status.PASS);
			
			report.updateTestLog("Fetching DVR device Host serial#  ", "Host serial# : "+device3, Status.PASS);		
			report.updateTestLog("Fetching DVR device MCard serial#   ", "MCard serial# : "+device4, Status.PASS);		
			
			
			
			break;			
			
			
		case "WG-XB3-DPC3941T-CDV-BATT":	
			Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

			Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+"<soapenv:Header>"
					+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
					+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
					+"<wsse:Username>Contec</wsse:Username>"
					+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		     +"</wsse:UsernameToken>"
		 +" </wsse:Security>"
		  +"<com:requestHeader>"
		     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		    +" <com:sourceSystemId/>"
		     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		     +"<com:sourceServerId>1</com:sourceServerId>"
		     +"<com:trackingId>1</com:trackingId>"
		 +" </com:requestHeader>"
		 +"</soapenv:Header>"
		 +"<soapenv:Body>"
		  +"<com:PostShipment>"
		     +"<com:Request>"
		        +"<com:OrderID>"+orderid+"</com:OrderID>"
		        +"<com:Shipments>"
		           +"<com:Shipment>"
		              +"<com:Carrier>UPS</com:Carrier>"
		             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
		              +"<com:Method>Overnight</com:Method>"
		              +"<com:Packages>"
		                 +"<com:Package>"
		                   +" <com:Cost>25.34</com:Cost>"
		                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		                   +" <com:ReturnTrackingNumber/>"
		                   +" <com:Weight>2.35</com:Weight>"
		                 +"</com:Package>"
		             +" </com:Packages>"
		                 
 +"<com:ProductsShipped>"
		                     //for dvr
		                     
		                    
			                    +" <com:ProductShipped>"
			                       +" <com:ProductID>"+prodid+"</com:ProductID>"
			                	                        
			                        +"<com:FulfillmentCode>WG-XB3-DPC3941T-CDV-BATT</com:FulfillmentCode>"
			                        +"<com:DevicesShipped>"
			                          +" <com:DeviceShipped>"
			                            +"  <com:Name/>"
			                             +" <com:Make>CI</com:Make>"
			                              +"<com:Model>DPC3939</com:Model>"
			                              +"<com:SerialNumber>"+add1+"</com:SerialNumber>"
			                             
			                              +"<com:MACAddress/>"
			                              +"<com:CMACAddress>"+add1+"</com:CMACAddress>"
			                              +"<com:MTAMACAddress>"+add+"</com:MTAMACAddress>"
			                             
			                              +"<com:MasterSubsidiaryLock/>"
			                              +"<com:USBMACAddress/>"
			                             
			                             +" <com:UnitID2/>"
			                             +" <com:Condition>New</com:Condition>"
			                             +" <com:DeviceType>eMTA</com:DeviceType>"
			                              +"<com:MACAddress2/>"
			                             
			                              +"<com:RFMACAddress/>"
			                              +"<com:DeviceID>"+device+"</com:DeviceID>"
			                          +" </com:DeviceShipped>"
			                        +"</com:DevicesShipped>"
			                         +"</com:ProductShipped>"
			                     
		                 
			                        
		              +"</com:ProductsShipped>"
		          +" </com:Shipment>"
		        +"</com:Shipments>"
		     +"</com:Request>"
		  +"</com:PostShipment>"
+" </soapenv:Body>"
+"</soapenv:Envelope>";
			
			
			
			report.updateTestLog("Fetching EMTA device ", "Product ID : "+prodid, Status.DONE);
			
			report.updateTestLog("Fetching EMTA device ", "Device ID : "+device, Status.DONE);
			
			
			
	
			
			report.updateTestLog("Fetching EMTA device Serial#  ", "Serial# : "+add1, Status.PASS);		
			report.updateTestLog("Fetching CMMAC device Serial#  ", "Serial# : "+add1, Status.PASS);		
			report.updateTestLog("Fetching MTA device Serial#  ", "Serial# : "+add, Status.PASS);		
			
			//report.updateTestLog("Fetching DCT device MCard serial#  ", "MCard serial# : "+device2, Status.PASS);
			
			
			
			
			
			break;			
		case "VID-SMRNG150BNMD":		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";
		 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				   +"<soapenv:Header>"
				      +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				        +" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				            +"<wsse:Username>Contec</wsse:Username>"
				            +"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
				           +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
				            +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
				         +"</wsse:UsernameToken>"
				     +" </wsse:Security>"
				      +"<com:requestHeader>"
				         +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
				        +" <com:sourceSystemId/>"
				         +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
				         +"<com:sourceServerId>1</com:sourceServerId>"
				         +"<com:trackingId>1</com:trackingId>"
				     +" </com:requestHeader>"
				   +"</soapenv:Header>"
				   +"<soapenv:Body>"
				      +"<com:PostShipment>"
				         +"<com:Request>"
				            +"<com:OrderID>"+orderid+"</com:OrderID>"
				            +"<com:Shipments>"
				               +"<com:Shipment>"
				                  +"<com:Carrier>UPS</com:Carrier>"
				                 +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
				                  +"<com:Method>Overnight</com:Method>"
				                  +"<com:Packages>"
				                     +"<com:Package>"
				                       +" <com:Cost>25.34</com:Cost>"
				                       +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
				                       +" <com:ReturnTrackingNumber/>"
				                       +" <com:Weight>2.35</com:Weight>"
				                     +"</com:Package>"
				                 +" </com:Packages>"
				                     
				                 //for dct X1
				                  +"<com:ProductsShipped>"
				                    +" <com:ProductShipped>"
				                       +" <com:ProductID>"+prodid+"</com:ProductID>"
				                	                        
				                        +"<com:FulfillmentCode>VID-SMRNG150BNMD</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                          +" <com:DeviceShipped>"
				                            +"  <com:Name/>"
				                             +" <com:Make>SAM</com:Make>"
				                              +"<com:Model>SR150BNM</com:Model>"
				                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
				                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
				                              +"<com:MACAddress/>"
				                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
				                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
				                              +"<com:MTAMACAddress/>"
				                              +"<com:MasterSubsidiaryLock/>"
				                              +"<com:USBMACAddress/>"
				                              +"<com:UnitID>"+unit1+"</com:UnitID>"
				                             +" <com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType>STB</com:DeviceType>"
				                              +"<com:MACAddress2/>"
				                              +"<com:Make2>MOTO</com:Make2>"
				                              +"<com:Model2>MSCC9063</com:Model2>"
				                              +"<com:RFMACAddress/>"
				                              +"<com:DeviceID>"+device+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                        +"</com:DevicesShipped>"
				                         +"</com:ProductShipped>"
				                     
				                       
				                     
				                     
				                     //for rma drop downs
				                    +" <com:ProductShipped>"
				                        +"<com:ProductID>"+prodidnew+"</com:ProductID>"
				                       +" <com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                           +"<com:DeviceShipped>"
				                              +"<com:Name/>"
				                              +"<com:Make/>"
				                              +"<com:Model/>"
				                             +" <com:SerialNumber/>"
				                              +"<com:SerialNumber2/>"
				                             +" <com:MACAddress/>"
				                             +" <com:CMACAddress/>"
				                              +"<com:EMACAddress/>"
				                             +" <com:MTAMACAddress/>"
				                             +" <com:MasterSubsidiaryLock/>"
				                             +" <com:USBMACAddress/>"
				                              +"<com:UnitID/>"
				                              +"<com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType/>"
				                              +"<com:MACAddress2/>"
				                             +" <com:Make2/>"
				                              +"<com:Model2/>"
				                              +"<com:RFMACAddress/>"
				                             +" <com:DeviceID>"+devicenew+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                       +" </com:DevicesShipped>"
				                    +" </com:ProductShipped>"
				                    
				                  +"</com:ProductsShipped>"
				              +" </com:Shipment>"
				            +"</com:Shipments>"
				         +"</com:Request>"
				      +"</com:PostShipment>"
				  +" </soapenv:Body>"
				+"</soapenv:Envelope>";
					
				
		case "VID-SMRNG150BNCD":		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";
		 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				   +"<soapenv:Header>"
				      +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				        +" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				            +"<wsse:Username>Contec</wsse:Username>"
				            +"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
				           +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
				            +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
				         +"</wsse:UsernameToken>"
				     +" </wsse:Security>"
				      +"<com:requestHeader>"
				         +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
				        +" <com:sourceSystemId/>"
				         +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
				         +"<com:sourceServerId>1</com:sourceServerId>"
				         +"<com:trackingId>1</com:trackingId>"
				     +" </com:requestHeader>"
				   +"</soapenv:Header>"
				   +"<soapenv:Body>"
				      +"<com:PostShipment>"
				         +"<com:Request>"
				            +"<com:OrderID>"+orderid+"</com:OrderID>"
				            +"<com:Shipments>"
				               +"<com:Shipment>"
				                  +"<com:Carrier>UPS</com:Carrier>"
				                 +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
				                  +"<com:Method>Overnight</com:Method>"
				                  +"<com:Packages>"
				                     +"<com:Package>"
				                       +" <com:Cost>25.34</com:Cost>"
				                       +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
				                       +" <com:ReturnTrackingNumber/>"
				                       +" <com:Weight>2.35</com:Weight>"
				                     +"</com:Package>"
				                 +" </com:Packages>"
				                     
				                 //for dct X1
				                  +"<com:ProductsShipped>"
				                    +" <com:ProductShipped>"
				                       +" <com:ProductID>"+prodid+"</com:ProductID>"
				                	                        
				                        +"<com:FulfillmentCode>VID-SMRNG150BNMD</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                          +" <com:DeviceShipped>"
				                            +"  <com:Name/>"
				                             +" <com:Make>SAM</com:Make>"
				                              +"<com:Model>SR150BNM</com:Model>"
				                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
				                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
				                              +"<com:MACAddress/>"
				                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
				                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
				                              +"<com:MTAMACAddress/>"
				                              +"<com:MasterSubsidiaryLock/>"
				                              +"<com:USBMACAddress/>"
				                              +"<com:UnitID>"+unit1+"</com:UnitID>"
				                             +" <com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType>STB</com:DeviceType>"
				                              +"<com:MACAddress2/>"
				                              +"<com:Make2>MOTO</com:Make2>"
				                              +"<com:Model2>MSCC9063</com:Model2>"
				                              +"<com:RFMACAddress/>"
				                              +"<com:DeviceID>"+device+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                        +"</com:DevicesShipped>"
				                         +"</com:ProductShipped>"
				                     
				                       
				                     
				                     
				                     //for rma drop downs
				                    +" <com:ProductShipped>"
				                        +"<com:ProductID>"+prodidnew+"</com:ProductID>"
				                       +" <com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                           +"<com:DeviceShipped>"
				                              +"<com:Name/>"
				                              +"<com:Make/>"
				                              +"<com:Model/>"
				                             +" <com:SerialNumber/>"
				                              +"<com:SerialNumber2/>"
				                             +" <com:MACAddress/>"
				                             +" <com:CMACAddress/>"
				                              +"<com:EMACAddress/>"
				                             +" <com:MTAMACAddress/>"
				                             +" <com:MasterSubsidiaryLock/>"
				                             +" <com:USBMACAddress/>"
				                              +"<com:UnitID/>"
				                              +"<com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType/>"
				                              +"<com:MACAddress2/>"
				                             +" <com:Make2/>"
				                              +"<com:Model2/>"
				                              +"<com:RFMACAddress/>"
				                             +" <com:DeviceID>"+devicenew+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                       +" </com:DevicesShipped>"
				                    +" </com:ProductShipped>"
				                    
				                  +"</com:ProductsShipped>"
				              +" </com:Shipment>"
				            +"</com:Shipments>"
				         +"</com:Request>"
				      +"</com:PostShipment>"
				  +" </soapenv:Body>"
				+"</soapenv:Envelope>";
					
		case "VID-PCRNG150BNCD":		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";
		 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				   +"<soapenv:Header>"
				      +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				        +" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				            +"<wsse:Username>Contec</wsse:Username>"
				            +"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
				           +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
				            +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
				         +"</wsse:UsernameToken>"
				     +" </wsse:Security>"
				      +"<com:requestHeader>"
				         +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
				        +" <com:sourceSystemId/>"
				         +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
				         +"<com:sourceServerId>1</com:sourceServerId>"
				         +"<com:trackingId>1</com:trackingId>"
				     +" </com:requestHeader>"
				   +"</soapenv:Header>"
				   +"<soapenv:Body>"
				      +"<com:PostShipment>"
				         +"<com:Request>"
				            +"<com:OrderID>"+orderid+"</com:OrderID>"
				            +"<com:Shipments>"
				               +"<com:Shipment>"
				                  +"<com:Carrier>UPS</com:Carrier>"
				                 +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
				                  +"<com:Method>Overnight</com:Method>"
				                  +"<com:Packages>"
				                     +"<com:Package>"
				                       +" <com:Cost>25.34</com:Cost>"
				                       +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
				                       +" <com:ReturnTrackingNumber/>"
				                       +" <com:Weight>2.35</com:Weight>"
				                     +"</com:Package>"
				                 +" </com:Packages>"
				                     
				                 //for dct X1
				                  +"<com:ProductsShipped>"
				                    +" <com:ProductShipped>"
				                       +" <com:ProductID>"+prodid+"</com:ProductID>"
				                	                        
				                        +"<com:FulfillmentCode>VID-SMRNG150BNMD</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                          +" <com:DeviceShipped>"
				                            +"  <com:Name/>"
				                             +" <com:Make>SAM</com:Make>"
				                              +"<com:Model>SR150BNM</com:Model>"
				                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
				                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
				                              +"<com:MACAddress/>"
				                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
				                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
				                              +"<com:MTAMACAddress/>"
				                              +"<com:MasterSubsidiaryLock/>"
				                              +"<com:USBMACAddress/>"
				                              +"<com:UnitID>"+unit1+"</com:UnitID>"
				                             +" <com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType>STB</com:DeviceType>"
				                              +"<com:MACAddress2/>"
				                              +"<com:Make2>MOTO</com:Make2>"
				                              +"<com:Model2>MSCC9063</com:Model2>"
				                              +"<com:RFMACAddress/>"
				                              +"<com:DeviceID>"+device+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                        +"</com:DevicesShipped>"
				                         +"</com:ProductShipped>"
				                     
				                       
				                     
				                     
				                     //for rma drop downs
				                    +" <com:ProductShipped>"
				                        +"<com:ProductID>"+prodidnew+"</com:ProductID>"
				                       +" <com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                           +"<com:DeviceShipped>"
				                              +"<com:Name/>"
				                              +"<com:Make/>"
				                              +"<com:Model/>"
				                             +" <com:SerialNumber/>"
				                              +"<com:SerialNumber2/>"
				                             +" <com:MACAddress/>"
				                             +" <com:CMACAddress/>"
				                              +"<com:EMACAddress/>"
				                             +" <com:MTAMACAddress/>"
				                             +" <com:MasterSubsidiaryLock/>"
				                             +" <com:USBMACAddress/>"
				                              +"<com:UnitID/>"
				                              +"<com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType/>"
				                              +"<com:MACAddress2/>"
				                             +" <com:Make2/>"
				                              +"<com:Model2/>"
				                              +"<com:RFMACAddress/>"
				                             +" <com:DeviceID>"+devicenew+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                       +" </com:DevicesShipped>"
				                    +" </com:ProductShipped>"
				                    
				                  +"</com:ProductsShipped>"
				              +" </com:Shipment>"
				            +"</com:Shipments>"
				         +"</com:Request>"
				      +"</com:PostShipment>"
				  +" </soapenv:Body>"
				+"</soapenv:Envelope>";
					
				
		case "VID-PCRNG150BNMD":		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";
		 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				   +"<soapenv:Header>"
				      +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				        +" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				            +"<wsse:Username>Contec</wsse:Username>"
				            +"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
				           +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
				            +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
				         +"</wsse:UsernameToken>"
				     +" </wsse:Security>"
				      +"<com:requestHeader>"
				         +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
				        +" <com:sourceSystemId/>"
				         +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
				         +"<com:sourceServerId>1</com:sourceServerId>"
				         +"<com:trackingId>1</com:trackingId>"
				     +" </com:requestHeader>"
				   +"</soapenv:Header>"
				   +"<soapenv:Body>"
				      +"<com:PostShipment>"
				         +"<com:Request>"
				            +"<com:OrderID>"+orderid+"</com:OrderID>"
				            +"<com:Shipments>"
				               +"<com:Shipment>"
				                  +"<com:Carrier>UPS</com:Carrier>"
				                 +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
				                  +"<com:Method>Overnight</com:Method>"
				                  +"<com:Packages>"
				                     +"<com:Package>"
				                       +" <com:Cost>25.34</com:Cost>"
				                       +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
				                       +" <com:ReturnTrackingNumber/>"
				                       +" <com:Weight>2.35</com:Weight>"
				                     +"</com:Package>"
				                 +" </com:Packages>"
				                     
				                 //for dct X1
				                  +"<com:ProductsShipped>"
				                    +" <com:ProductShipped>"
				                       +" <com:ProductID>"+prodid+"</com:ProductID>"
				                	                        
				                        +"<com:FulfillmentCode>VID-SMRNG150BNMD</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                          +" <com:DeviceShipped>"
				                            +"  <com:Name/>"
				                             +" <com:Make>SAM</com:Make>"
				                              +"<com:Model>SR150BNM</com:Model>"
				                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
				                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
				                              +"<com:MACAddress/>"
				                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
				                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
				                              +"<com:MTAMACAddress/>"
				                              +"<com:MasterSubsidiaryLock/>"
				                              +"<com:USBMACAddress/>"
				                              +"<com:UnitID>"+unit1+"</com:UnitID>"
				                             +" <com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType>STB</com:DeviceType>"
				                              +"<com:MACAddress2/>"
				                              +"<com:Make2>MOTO</com:Make2>"
				                              +"<com:Model2>MSCC9063</com:Model2>"
				                              +"<com:RFMACAddress/>"
				                              +"<com:DeviceID>"+device+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                        +"</com:DevicesShipped>"
				                         +"</com:ProductShipped>"
				                     
				                       
				                     
				                     
				                     //for rma drop downs
				                    +" <com:ProductShipped>"
				                        +"<com:ProductID>"+prodidnew+"</com:ProductID>"
				                       +" <com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
				                        +"<com:DevicesShipped>"
				                           +"<com:DeviceShipped>"
				                              +"<com:Name/>"
				                              +"<com:Make/>"
				                              +"<com:Model/>"
				                             +" <com:SerialNumber/>"
				                              +"<com:SerialNumber2/>"
				                             +" <com:MACAddress/>"
				                             +" <com:CMACAddress/>"
				                              +"<com:EMACAddress/>"
				                             +" <com:MTAMACAddress/>"
				                             +" <com:MasterSubsidiaryLock/>"
				                             +" <com:USBMACAddress/>"
				                              +"<com:UnitID/>"
				                              +"<com:UnitID2/>"
				                             +" <com:Condition>New</com:Condition>"
				                             +" <com:DeviceType/>"
				                              +"<com:MACAddress2/>"
				                             +" <com:Make2/>"
				                              +"<com:Model2/>"
				                              +"<com:RFMACAddress/>"
				                             +" <com:DeviceID>"+devicenew+"</com:DeviceID>"
				                          +" </com:DeviceShipped>"
				                       +" </com:DevicesShipped>"
				                    +" </com:ProductShipped>"
				                    
				                  +"</com:ProductsShipped>"
				              +" </com:Shipment>"
				            +"</com:Shipments>"
				         +"</com:Request>"
				      +"</com:PostShipment>"
				  +" </soapenv:Body>"
				+"</soapenv:Envelope>";
					
												
											

			
				default:System.out.println("wrong choice");
				break;
				
				
		}
		
		
		/*if(fid[0]=="VID-SMRNG150BNMD"||fid[0]=="VID-SMRNG150BNCD"||fid[0]=="VID-PCRNG150BNCD"||fid[0]=="VID-PCRNG150BNCD")
		{
			System.out.println("if statement");
			Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";
			 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					   +"<soapenv:Header>"
					      +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
					        +" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
					            +"<wsse:Username>Contec</wsse:Username>"
					            +"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
					           +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
					            +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
					         +"</wsse:UsernameToken>"
					     +" </wsse:Security>"
					      +"<com:requestHeader>"
					         +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
					        +" <com:sourceSystemId/>"
					         +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
					         +"<com:sourceServerId>1</com:sourceServerId>"
					         +"<com:trackingId>1</com:trackingId>"
					     +" </com:requestHeader>"
					   +"</soapenv:Header>"
					   +"<soapenv:Body>"
					      +"<com:PostShipment>"
					         +"<com:Request>"
					            +"<com:OrderID>"+orderid+"</com:OrderID>"
					            +"<com:Shipments>"
					               +"<com:Shipment>"
					                  +"<com:Carrier>UPS</com:Carrier>"
					                 +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
					                  +"<com:Method>Overnight</com:Method>"
					                  +"<com:Packages>"
					                     +"<com:Package>"
					                       +" <com:Cost>25.34</com:Cost>"
					                       +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
					                       +" <com:ReturnTrackingNumber/>"
					                       +" <com:Weight>2.35</com:Weight>"
					                     +"</com:Package>"
					                 +" </com:Packages>"
					                     
					                 //for dct X1
					                  +"<com:ProductsShipped>"
					                    +" <com:ProductShipped>"
					                       +" <com:ProductID>"+prodid+"</com:ProductID>"
					                	                        
					                        +"<com:FulfillmentCode>VID-SMRNG150BNMD</com:FulfillmentCode>"
					                        +"<com:DevicesShipped>"
					                          +" <com:DeviceShipped>"
					                            +"  <com:Name/>"
					                             +" <com:Make>SAM</com:Make>"
					                              +"<com:Model>SR150BNM</com:Model>"
					                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
					                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
					                              +"<com:MACAddress/>"
					                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
					                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
					                              +"<com:MTAMACAddress/>"
					                              +"<com:MasterSubsidiaryLock/>"
					                              +"<com:USBMACAddress/>"
					                              +"<com:UnitID>"+unit1+"</com:UnitID>"
					                             +" <com:UnitID2/>"
					                             +" <com:Condition>New</com:Condition>"
					                             +" <com:DeviceType>STB</com:DeviceType>"
					                              +"<com:MACAddress2/>"
					                              +"<com:Make2>MOTO</com:Make2>"
					                              +"<com:Model2>MSCC9063</com:Model2>"
					                              +"<com:RFMACAddress/>"
					                              +"<com:DeviceID>"+device+"</com:DeviceID>"
					                          +" </com:DeviceShipped>"
					                        +"</com:DevicesShipped>"
					                         +"</com:ProductShipped>"
					                     
					                       
					                     
					                     
					                     //for rma drop downs
					                    +" <com:ProductShipped>"
					                        +"<com:ProductID>"+prodidnew+"</com:ProductID>"
					                       +" <com:FulfillmentCode>RMA-DF-LABEL-01</com:FulfillmentCode>"
					                        +"<com:DevicesShipped>"
					                           +"<com:DeviceShipped>"
					                              +"<com:Name/>"
					                              +"<com:Make/>"
					                              +"<com:Model/>"
					                             +" <com:SerialNumber/>"
					                              +"<com:SerialNumber2/>"
					                             +" <com:MACAddress/>"
					                             +" <com:CMACAddress/>"
					                              +"<com:EMACAddress/>"
					                             +" <com:MTAMACAddress/>"
					                             +" <com:MasterSubsidiaryLock/>"
					                             +" <com:USBMACAddress/>"
					                              +"<com:UnitID/>"
					                              +"<com:UnitID2/>"
					                             +" <com:Condition>New</com:Condition>"
					                             +" <com:DeviceType/>"
					                              +"<com:MACAddress2/>"
					                             +" <com:Make2/>"
					                              +"<com:Model2/>"
					                              +"<com:RFMACAddress/>"
					                             +" <com:DeviceID>"+devicenew+"</com:DeviceID>"
					                          +" </com:DeviceShipped>"
					                       +" </com:DevicesShipped>"
					                    +" </com:ProductShipped>"
					                    
					                  +"</com:ProductsShipped>"
					              +" </com:Shipment>"
					            +"</com:Shipments>"
					         +"</com:Request>"
					      +"</com:PostShipment>"
					  +" </soapenv:Body>"
					+"</soapenv:Envelope>";
						
					
			
		}
	
				
		
				*/
		
				
		System.out.println("--------------------------------------");
		System.out.println("CIBA Request : "+this.Request);
			System.setProperty("java.protocol.handler.pkgs",
				"com.sun.net.ssl.internal.www.protocol");
		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		try {

			URL endPoint = new URL(Endpoint);
			HttpURLConnection httpConn = (HttpURLConnection) endPoint
					.openConnection();
			httpConn.setRequestProperty("Content-Type",
					"text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", "http://comcastonline.com/PostShipment");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buffer = new byte[Request.length()];
			buffer = Request.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			httpConn.setRequestProperty("Content-Length",
					String.valueOf(b.length));

			// Set the appropriate HTTP parameters.
			PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
			pw.write(Request);
			pw.flush();

			// Ready with sending the request.

			// Read the response.

			httpConn.connect();

			InputStreamReader isr = new InputStreamReader(
					httpConn.getInputStream());
			BufferedReader in = new BufferedReader(isr);

			// Write the SOAP message response to a String.
			while ((Response = in.readLine()) != null) {
				cibaResponse = Response;
			}
			//System.out.println("postCIBAResponse :" +cibaResponse);
			//System.out.println(cibaResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//return cibaResponse;
		System.out.println(cibaResponse);
		if(cibaResponse==null)
		{
			report.reportFailEvent("Response is null from the soap request", "After hitting soap request it is giving null response");
		}
	}

	
	public void hitCMEMTARequest() throws Exception
	{
		
		int groupidnew = 0;
		int deviceidnew = 0;
		String Orderidnew = null;
		String fulfillmentcodenew = null;
		 String Host ="jdbc:sqlserver://sikdb-dt-1i.cable.comcast.com;port=8152";
		 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		 Connection conn = DriverManager.getConnection(Host,"offshore", "pass2012");
		 System.out.println("Connection success");

		 report.updateTestLog("Connection to SQL server_SIK DB", "Connection to SIK DB successful", Status.DONE);
		 report.updateTestLog("Fetching device details", "Fetched successfully", Status.DONE);
		 CustomerDetails customerDetails = CustomerDetails
		 .loadFromDatatable(dataTable);
		String querynew="Select OnlineOrdersID from sikoms.dbo.OnlineOrdersAccountInfo where Other_Raw_Acct='"+customerDetails.accountNumber+"'";
		
		PreparedStatement statementnew=conn.prepareStatement(querynew);
		//Statement statement = conn.createStatement();
		ResultSet rsnew = statementnew.executeQuery();
		if(rsnew.next())
		{
		
		Orderidnew = rsnew.getString("OnlineOrdersID");
	
		System.out.println("Order Id"+Orderidnew);
		
		}

		report.updateTestLog("Fetching SIK order ID ", "SIK order ID : "+Orderidnew, Status.DONE);

		String query1new="SELECT DISTINCT OOPCD.GROUPID,OOPCD.ID, FG.FULFILLMENTCODE FROM SIKOMS.SIKOMS.ONLINEORDERSPRODUCTCODEDEVICES OOPCD,SIKOMS.SIKOMS.ONLINEORDERSFINISHEDGOODS OOFG, SIKOMS.SIKOMS.FINISHEDGOODS FG WHERE OOPCD.ONLINEORDERSPRODUCTCODESID = OOFG.ONLINEORDERSPRODUCTCODESID AND OOFG.FINISHEDGOODID = FG.ID AND OOFG.PRIORITY = 1 AND OOPCD.ONLINEORDERSPRODUCTCODESID IN (SELECT ID FROM SIKOMS.SIKOMS.ONLINEORDERSPRODUCTCODES WHERE ONLINEORDERSID IN ('"+Orderidnew+"')) ORDER BY OOPCD.GROUPID";
		PreparedStatement statement1new=conn.prepareStatement(query1new);
		ResultSet rs1new = statement1new.executeQuery();

		if(rs1new.next())
		{

		 groupidnew = Integer.parseInt(rs1new.getString("GROUPID"));
		System.out.println("Group id of EMTA:"+groupidnew);
		 deviceidnew = Integer.parseInt(rs1new.getString("ID"));
		System.out.println("Device id of EMTA:"+deviceidnew);
		 fulfillmentcodenew = rs1new.getString("FULFILLMENTCODE");
		System.out.println("Fulfillment code of EMTA:"+fulfillmentcodenew);

		}
		 
		if(fulfillmentcodenew.equals("WG-XB3-DPC3941T-CDV-BATT")||fulfillmentcodenew.equals("WG-XB3-DPC3941-CDV-BAT"))
		{
		
			Random rn        = new Random();
			int    range     = 9999999 - 1000000 + 1;  
			int    randomNum =  rn.nextInt(range) + 1000000;  // For 7 digit number
			System.out.println(randomNum);

			Random rc = new Random();
			char   c  = (char)(rc.nextInt(26) + 'A');
			System.out.println(c);
			char   c1  = (char)(rc.nextInt(26) + 'A');
			System.out.println(c1);
			

			 add = "0086"+randomNum+"D";    
			 add1 = "FD1F"+randomNum+"A";    
			System.out.println(add);
			System.out.println(add1);
		
			Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

			Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+"<soapenv:Header>"
					+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
					+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
					+"<wsse:Username>Contec</wsse:Username>"
					+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		     +"</wsse:UsernameToken>"
		 +" </wsse:Security>"
		  +"<com:requestHeader>"
		     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		    +" <com:sourceSystemId/>"
		     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		     +"<com:sourceServerId>1</com:sourceServerId>"
		     +"<com:trackingId>1</com:trackingId>"
		 +" </com:requestHeader>"
		 +"</soapenv:Header>"
		 +"<soapenv:Body>"
		  +"<com:PostShipment>"
		     +"<com:Request>"
		        +"<com:OrderID>"+Orderidnew+"</com:OrderID>"
		        +"<com:Shipments>"
		           +"<com:Shipment>"
		              +"<com:Carrier>UPS</com:Carrier>"
		             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
		              +"<com:Method>Overnight</com:Method>"
		              +"<com:Packages>"
		                 +"<com:Package>"
		                   +" <com:Cost>25.34</com:Cost>"
		                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		                   +" <com:ReturnTrackingNumber/>"
		                   +" <com:Weight>2.35</com:Weight>"
		                 +"</com:Package>"
		             +" </com:Packages>"
		                 
		+"<com:ProductsShipped>"
		                     //for dvr
		                     
		                    
			                    +" <com:ProductShipped>"
			                       +" <com:ProductID>"+groupidnew+"</com:ProductID>"
			                	                        
			                        +"<com:FulfillmentCode>"+fulfillmentcodenew+"</com:FulfillmentCode>"
			                        +"<com:DevicesShipped>"
			                          +" <com:DeviceShipped>"
			                            +"  <com:Name/>"
			                             +" <com:Make>CI</com:Make>"
			                              +"<com:Model>DPC3939</com:Model>"
			                              +"<com:SerialNumber>"+add1+"</com:SerialNumber>"
			                             
			                              +"<com:MACAddress/>"
			                              +"<com:CMACAddress>"+add1+"</com:CMACAddress>"
			                              +"<com:MTAMACAddress>"+add+"</com:MTAMACAddress>"
			                             
			                              +"<com:MasterSubsidiaryLock/>"
			                              +"<com:USBMACAddress/>"
			                             
			                             +" <com:UnitID2/>"
			                             +" <com:Condition>New</com:Condition>"
			                             +" <com:DeviceType>eMTA</com:DeviceType>"
			                              +"<com:MACAddress2/>"
			                             
			                              +"<com:RFMACAddress/>"
			                              +"<com:DeviceID>"+deviceidnew+"</com:DeviceID>"
			                          +" </com:DeviceShipped>"
			                        +"</com:DevicesShipped>"
			                         +"</com:ProductShipped>"
			                     
		                 
			                        
		              +"</com:ProductsShipped>"
		          +" </com:Shipment>"
		        +"</com:Shipments>"
		     +"</com:Request>"
		  +"</com:PostShipment>"
		+" </soapenv:Body>"
		+"</soapenv:Envelope>";
			
			System.out.println("--------------------------------------");
			System.out.println("CIBA Request : "+this.Request);
				System.setProperty("java.protocol.handler.pkgs",
					"com.sun.net.ssl.internal.www.protocol");
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			try {

				URL endPoint = new URL(Endpoint);
				HttpURLConnection httpConn = (HttpURLConnection) endPoint
						.openConnection();
				httpConn.setRequestProperty("Content-Type",
						"text/xml; charset=utf-8");
				httpConn.setRequestProperty("SOAPAction", "http://comcastonline.com/PostShipment");
				httpConn.setRequestMethod("POST");
				httpConn.setDoOutput(true);
				httpConn.setDoInput(true);

				ByteArrayOutputStream bout = new ByteArrayOutputStream();
				byte[] buffer = new byte[Request.length()];
				buffer = Request.getBytes();
				bout.write(buffer);
				byte[] b = bout.toByteArray();
				httpConn.setRequestProperty("Content-Length",
						String.valueOf(b.length));

				// Set the appropriate HTTP parameters.
				PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
				pw.write(Request);
				pw.flush();

				// Ready with sending the request.

				// Read the response.

				httpConn.connect();

				InputStreamReader isr = new InputStreamReader(
						httpConn.getInputStream());
				BufferedReader in = new BufferedReader(isr);

				// Write the SOAP message response to a String.
				while ((Response = in.readLine()) != null) {
					cibaResponse = Response;
				}
				//System.out.println("postCIBAResponse :" +cibaResponse);
				//System.out.println(cibaResponse);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//return cibaResponse;
			System.out.println(cibaResponse);
			if(cibaResponse==null)
			{
				report.reportFailEvent("Response is null from the soap request", "After hitting soap request it is giving null response");
			}
			}
	report.updateTestLog("Fetching EMTA device ", "Product ID : "+groupidnew, Status.DONE);
			
			report.updateTestLog("Fetching EMTA device ", "Device ID : "+deviceidnew, Status.DONE);
			
			report.updateTestLog("Fetching EMTA device Serial#  ", "Serial# : "+add1, Status.PASS);		
			report.updateTestLog("Fetching CMMAC device Serial#  ", "Serial# : "+add1, Status.PASS);		
			report.updateTestLog("Fetching MTA device Serial#  ", "Serial# : "+add, Status.PASS);	
			
		}
	
	
	public void HitNewRequest() throws MalformedURLException, Exception
	{
		
		 DeviceDetails deviceDetails= DeviceDetails
 				.loadFromDatatable(dataTable);
       
          device1=deviceDetails.serialno1;
          device2=deviceDetails.serialno2;
          
           device3=deviceDetails.serialno3;
           device4=deviceDetails.serialno4;
           cmmac = deviceDetails.cmMacAddress;
           mtamac = deviceDetails.eSTBAddress;
           cmmac1 = deviceDetails.cmMacAddress1;
           mtamac1 = deviceDetails.eSTBAddress1;
           unit1=deviceDetails.unit1;
           unit2=deviceDetails.unit2;
           rfmac=deviceDetails.rfmac;
           device5=deviceDetails.serialno5;
           device6=deviceDetails.serialno6;
           unit3=deviceDetails.unit3;
           cmmac2=deviceDetails.cmMacAddress2;
           mtamac2=deviceDetails.eSTBAddress2;
           rfmac2=deviceDetails.rfmac2;
           
           System.out.println(device6);
			String Orderid=""; 
			int groupid=0;
			int tempgroupid=0;
			int groupid1=0;
			int tempgroupid1;
			int groupid2 = 0;
			int deviceid2 = 0;
			String fulfillmentcode=null;
			String fulfillmentcode1 = null;
			String fulfillmentcode3 = null;
			int deviceid=0;
			int deviceid1 = 0;
			String productid;
			String productid1;
			
			String deviceid3 = null;
			String groupid3 = null;
			String deviceid4 = null;
			String groupid4 = null;
			String deviceid5 = null;
			String groupid5 = null;
			String deviceid6 = null;
			String groupid6= null;
		  
			  String Host ="jdbc:sqlserver://sikdb-dt-1i.cable.comcast.com;port=8152";
			  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			  Connection conn = DriverManager.getConnection(Host,"offshore", "pass2012");
			  System.out.println("Connection success");
			  CustomerDetails customerDetails = CustomerDetails
						.loadFromDatatable(dataTable);

			  String query="Select OnlineOrdersID from sikoms.dbo.OnlineOrdersAccountInfo where Other_Raw_Acct='"+customerDetails.accountNumber+"'";
			                              //Select OrderID from sikoms.dbo.sikutilitydata where AccountNumber='8210100200601828' order by OrderID Desc";  
			  //Connection conn = DriverManager.getConnection("jdbc:sqlserver://10.252.112.168;port=8152","offshore", "pass2012");
			  PreparedStatement statement=conn.prepareStatement(query);
			  //Statement statement = conn.createStatement();
			  ResultSet rs = statement.executeQuery();
			  if(rs.next())
			  {
			                Orderid=rs.getString("OnlineOrdersID");
			                //Orderid1=Integer.parseInt(Orderid);
			  System.out.println("Order Id"+Orderid);
	             report.updateTestLog("Fetching SIK order ID ", "SIK order ID : "+Orderid, Status.DONE);

			  }

			 

			  String query1="SELECT DISTINCT OOPCD.GROUPID,OOPCD.ID, FG.FULFILLMENTCODE FROM SIKOMS.SIKOMS.ONLINEORDERSPRODUCTCODEDEVICES OOPCD,SIKOMS.SIKOMS.ONLINEORDERSFINISHEDGOODS OOFG, SIKOMS.SIKOMS.FINISHEDGOODS FG WHERE OOPCD.ONLINEORDERSPRODUCTCODESID = OOFG.ONLINEORDERSPRODUCTCODESID AND OOFG.FINISHEDGOODID = FG.ID AND OOFG.PRIORITY = 1 AND OOPCD.ONLINEORDERSPRODUCTCODESID IN (SELECT ID FROM SIKOMS.SIKOMS.ONLINEORDERSPRODUCTCODES WHERE ONLINEORDERSID IN ('"+Orderid+"')) ORDER BY OOPCD.GROUPID";
			  PreparedStatement statement1=conn.prepareStatement(query1);
			  ResultSet rs1 = statement1.executeQuery();

			  if(rs1.next())
			  {
			                
			              groupid=Integer.parseInt(rs1.getString("GROUPID"));
			  System.out.println("Group id of cm:"+groupid);
			  deviceid=Integer.parseInt(rs1.getString("ID"));
			  System.out.println("Device id of cm:"+deviceid);
			  fulfillmentcode=rs1.getString("FULFILLMENTCODE");
			  System.out.println("Fulfillment code of emta:"+fulfillmentcode);

			  if(rs1.next()!=false)
			  {
			                
			              groupid1=Integer.parseInt(rs1.getString("GROUPID"));
			  System.out.println("Group id of dvr:"+groupid1);
			  deviceid1=Integer.parseInt(rs1.getString("ID"));
			  System.out.println("Device id of dvr:"+deviceid1);
			  fulfillmentcode1=rs1.getString("FULFILLMENTCODE");
			  System.out.println("Fulfillment code of dvr:"+fulfillmentcode1);


			  if(rs1.next()!=false)
			  {
			                
			              groupid2=Integer.parseInt(rs1.getString("GROUPID"));
			  System.out.println("Group id of dct:"+groupid2);
			  deviceid2=Integer.parseInt(rs1.getString("ID"));
			  System.out.println("Device id of dct:"+deviceid2);
			  fulfillmentcode2=rs1.getString("FULFILLMENTCODE");
			  System.out.println("Fulfillment code of dct:"+fulfillmentcode2);}
			  String query2="Select FulfillmentCode from sikoms.OnlineOrdersFinishedGoods where Full='RMA-EasyReturns' and FulfillmentCode='"+fulfillmentcode+"'";

			  if(rs1.next() != false){
			              
			                 groupid1=Integer.parseInt(rs1.getString("GROUPID"));
			                  System.out.println("Group id of dvr:"+groupid1);
			                  deviceid1=Integer.parseInt(rs1.getString("ID"));
			                  System.out.println("Device id of dvr:"+deviceid1);
			                  fulfillmentcode1=rs1.getString("FULFILLMENTCODE");
			                  System.out.println("Fulfillment code of dvr:"+fulfillmentcode1);
			  }
			    
			  }

			  }
			  String query2="SELECT DISTINCT OOPCD.GROUPID,OOPCD.ID,FG.FULFILLMENTCODE FROM SIKOMS.SIKOMS.ONLINEORDERSPRODUCTCODEDEVICES OOPCD,SIKOMS.SIKOMS.ONLINEORDERSFINISHEDGOODS OOFG, SIKOMS.SIKOMS.FINISHEDGOODS FG WHERE OOPCD.ONLINEORDERSPRODUCTCODESID = OOFG.ONLINEORDERSPRODUCTCODESID AND OOFG.FINISHEDGOODID = FG.ID AND OOFG.PRIORITY = 3 AND OOPCD.ONLINEORDERSPRODUCTCODESID IN (SELECT ID FROM SIKOMS.SIKOMS.ONLINEORDERSPRODUCTCODES WHERE ONLINEORDERSID IN ('"+Orderid+"')) ORDER BY OOPCD.GROUPID";
			  PreparedStatement statement2=conn.prepareStatement(query2);
			  ResultSet rs2 = statement2.executeQuery();
			  if(rs2.next())
			  {
			              rs2.getString("GROUPID");
			              rs2.getString("ID");
			              rs2.getString("FULFILLMENTCODE");
			              if(rs2.next() != false)
			              {
			            	  groupid3=rs2.getString("GROUPID");
			            			  System.out.println("groupid of third device"+groupid3);
                         deviceid3= rs2.getString("ID");
                        		 System.out.println("device id of third device"+deviceid3);
                          fulfillmentcode3=rs2.getString("FULFILLMENTCODE");
                        		  System.out.println("fulfillment code of third device"+fulfillmentcode3);
			              }
			              if(rs2.next() != false)
			              {
			            	  groupid4=rs2.getString("GROUPID");
	            			  System.out.println("group id of fourth device"+groupid4);
                 deviceid4= rs2.getString("ID");
                		 System.out.println("device id of fourth device"+deviceid4);
                  fulfillmentcode4=rs2.getString("FULFILLMENTCODE");
                		  System.out.println("fulfillment code of fourth device"+fulfillmentcode4);
			              }
			              if(rs2.next() != false)
			              {
			            	  groupid5=rs2.getString("GROUPID");
	            			  System.out.println("group id of fifth device"+groupid5);
                 deviceid5= rs2.getString("ID");
                		 System.out.println("device id of fifth device"+deviceid5);
                  fulfillmentcode5=rs2.getString("FULFILLMENTCODE");
                		  System.out.println("fulfillment code of fifth device"+fulfillmentcode5);
			              }
			              if(rs2.next() != false)
			              {
			            	  groupid6=rs2.getString("GROUPID");
	            			  System.out.println("group id of sixth device"+groupid6);
                 deviceid6= rs2.getString("ID");
                		 System.out.println("device id of sixth device"+deviceid6);
                  fulfillmentcode6=rs2.getString("FULFILLMENTCODE");
                		  System.out.println("fulfillment code of sixth device"+fulfillmentcode6);
			              }
			  }



System.out.println("1"+fulfillmentcode);
System.out.println("2"+fulfillmentcode1);
System.out.println("3"+fulfillmentcode2);
System.out.println("4"+fulfillmentcode3);
System.out.println("5"+fulfillmentcode4);
System.out.println("6"+fulfillmentcode5);
System.out.println("7"+fulfillmentcode6);



System.out.println("1"+deviceid1);
System.out.println("2"+deviceid2);
System.out.println("3"+deviceid3);




//csg combo mpeg4eolcmemta moto
if(("WG-XB3-DPC3941T-CDV-BATT".equals(fulfillmentcode))&&("VID-MOTRNG200BN-MPEG4".equals(fulfillmentcode1))&&("VID-RNG110RF-MPEG4".equals(fulfillmentcode2)||"VID-MOTRNG150N-MPEG4".equals(fulfillmentcode2)))
{
	
	Random rn        = new Random();
	int    range     = 9999999 - 1000000 + 1;  
	int    randomNum =  rn.nextInt(range) + 1000000;  // For 7 digit number
	System.out.println(randomNum);

	Random rc = new Random();
	char   c  = (char)(rc.nextInt(26) + 'A');
	System.out.println(c);
	char   c1  = (char)(rc.nextInt(26) + 'A');
	System.out.println(c1);
	

	 add = "0086"+randomNum+"D";    
	 add1 = "FD1F"+randomNum+"A";    
	System.out.println(add);
	System.out.println(add1);
	System.out.println("ttest");
	Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
     +"</wsse:UsernameToken>"
 +" </wsse:Security>"
  +"<com:requestHeader>"
     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
    +" <com:sourceSystemId/>"
     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
     +"<com:sourceServerId>1</com:sourceServerId>"
     +"<com:trackingId>1</com:trackingId>"
 +" </com:requestHeader>"
 +"</soapenv:Header>"
 +"<soapenv:Body>"
  +"<com:PostShipment>"
     +"<com:Request>"
        +"<com:OrderID>"+Orderid+"</com:OrderID>"
        +"<com:Shipments>"
           +"<com:Shipment>"
              +"<com:Carrier>UPS</com:Carrier>"
             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
              +"<com:Method>Overnight</com:Method>"
              +"<com:Packages>"
                 +"<com:Package>"
                   +" <com:Cost>25.34</com:Cost>"
                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
                   +" <com:ReturnTrackingNumber/>"
                   +" <com:Weight>2.35</com:Weight>"
                 +"</com:Package>"
             +" </com:Packages>"
                 
+"<com:ProductsShipped>"
                
                     
                    
	                    +" <com:ProductShipped>"
	                       +" <com:ProductID>"+groupid+"</com:ProductID>"
	                	                        
	                        +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
	                        +"<com:DevicesShipped>"
	                          +" <com:DeviceShipped>"
	                            +"  <com:Name/>"
	                             +" <com:Make>CI</com:Make>"
	                              +"<com:Model>DPC3939</com:Model>"
	                              +"<com:SerialNumber>"+add1+"</com:SerialNumber>"
	                             
	                              +"<com:MACAddress/>"
	                              +"<com:CMACAddress>"+add1+"</com:CMACAddress>"
	                              +"<com:MTAMACAddress>"+add+"</com:MTAMACAddress>"
	                             
	                              +"<com:MasterSubsidiaryLock/>"
	                              +"<com:USBMACAddress/>"
	                             
	                             +" <com:UnitID2/>"
	                             +" <com:Condition>New</com:Condition>"
	                             +" <com:DeviceType>eMTA</com:DeviceType>"
	                              +"<com:MACAddress2/>"
	                             
	                              +"<com:RFMACAddress/>"
	                              +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
	                          +" </com:DeviceShipped>"
	                        +"</com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                     
	                         
              //dvr
	                         +" <com:ProductShipped>"
	                         +" <com:ProductID>"+groupid1+"</com:ProductID>"
	                  	                        
	                          +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
	                          +"<com:DevicesShipped>"
	                            +" <com:DeviceShipped>"
	                              +"  <com:Name/>"
	                               +" <com:Make>MOTO</com:Make>"
	                                +"<com:Model>DCX3400</com:Model>"
	                                +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
	                                +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
	                                +"<com:MACAddress/>"
	                                +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
	                                +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
	                                +"<com:MTAMACAddress/>"
	                                +"<com:MasterSubsidiaryLock/>"
	                                +"<com:USBMACAddress/>"
	                                +"<com:UnitID>"+unit1+"</com:UnitID>"
	                               +" <com:UnitID2/>"
	                               +" <com:Condition>New</com:Condition>"
	                               +" <com:DeviceType>STB</com:DeviceType>"
	                                +"<com:MACAddress2/>"
	                                +"<com:Make2>MOTO</com:Make2>"
	                                +"<com:Model2>MSCC9062</com:Model2>"
	                                +"<com:RFMACAddress/>"
	                                +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
	                            +" </com:DeviceShipped>"
	                          +"</com:DevicesShipped>"
	                           +"</com:ProductShipped>"
	                          
	                           //dct
	                     
	                           +" <com:ProductShipped>"
	                              +" <com:ProductID>"+(groupid2)+"</com:ProductID>"
	                       	                        
	                               +"<com:FulfillmentCode>"+fulfillmentcode2+"</com:FulfillmentCode>"
	                               +"<com:DevicesShipped>"
	                                 +" <com:DeviceShipped>"
	                                   +"  <com:Name/>"
	                                    +" <com:Make>MOTO</com:Make>"
	                                     +"<com:Model>DCX3200</com:Model>"
	                                     +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
	                                     +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
	                                     +"<com:MACAddress/>"
	                                     +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
	                                     +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
	                                     +"<com:MTAMACAddress/>"
	                                     +"<com:MasterSubsidiaryLock/>"
	                                     +"<com:USBMACAddress/>"
	                                     +"<com:UnitID>"+unit2+"</com:UnitID>"
	                                    +" <com:UnitID2/>"
	                                    +" <com:Condition>New</com:Condition>"
	                                    +" <com:DeviceType>STB</com:DeviceType>"
	                                     +"<com:MACAddress2/>"
	                                     +"<com:Make2>MOTO</com:Make2>"
	                                     +"<com:Model2>MSCC9062</com:Model2>"
	                                     +"<com:RFMACAddress/>"
	                                     +"<com:DeviceID>"+(deviceid2)+"</com:DeviceID>"
	                                 +" </com:DeviceShipped>"
	                               +"</com:DevicesShipped>"
	                                +"</com:ProductShipped>"
	                               
	                   
              
	                        
              +"</com:ProductsShipped>"
          +" </com:Shipment>"
        +"</com:Shipments>"
     +"</com:Request>"
  +"</com:PostShipment>"
+" </soapenv:Body>"
+"</soapenv:Envelope>";
	
	 report.updateTestLog("The product id for EMTA device ", "Fetched successfully as : "+groupid, Status.PASS);

	 report.updateTestLog("The device id for EMTA device ", "Fetched successfully as : "+deviceid, Status.PASS);

	 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);

	 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);

	 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid2, Status.PASS);

	 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid2, Status.PASS);
	
	 report.updateTestLog("The serial no of EMTA device ", "Fetched successfully as : "+add1, Status.PASS);		
	 
	 report.updateTestLog("The serial no of DCT device ", "Fetched successfully as : "+device3, Status.PASS);		

	 report.updateTestLog("The serial no of DCT device ", "Fetched successfully as : "+device4, Status.PASS);		

	 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device1, Status.PASS);		
	 
	 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device2, Status.PASS);	
	 	
	
}

//mpeg4dvr

if(("VID-MOTRNG200BN-MPEG4".equals(fulfillmentcode)||"VID-MOTRNG200BN-MPEG4".equals(fulfillmentcode))&&("RMA-DF-LABEL-01".equals(fulfillmentcode3))&&"RMA-EasyReturns".equals(fulfillmentcode4))
	{
		
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	     +"</wsse:UsernameToken>"
	 +" </wsse:Security>"
	  +"<com:requestHeader>"
	     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	    +" <com:sourceSystemId/>"
	     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	     +"<com:sourceServerId>1</com:sourceServerId>"
	     +"<com:trackingId>1</com:trackingId>"
	 +" </com:requestHeader>"
	 +"</soapenv:Header>"
	 +"<soapenv:Body>"
	  +"<com:PostShipment>"
	     +"<com:Request>"
	        +"<com:OrderID>"+Orderid+"</com:OrderID>"
	        +"<com:Shipments>"
	           +"<com:Shipment>"
	              +"<com:Carrier>UPS</com:Carrier>"
	             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	              +"<com:Method>Overnight</com:Method>"
	              +"<com:Packages>"
	                 +"<com:Package>"
	                   +" <com:Cost>25.34</com:Cost>"
	                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	                   +" <com:ReturnTrackingNumber/>"
	                   +" <com:Weight>2.35</com:Weight>"
	                 +"</com:Package>"
	             +" </com:Packages>"
	                 
	             //for dvr
               
	             +"<com:ProductsShipped>"
               +" <com:ProductShipped>"
                  +" <com:ProductID>"+groupid+"</com:ProductID>"
           	                        
                   +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
                   +"<com:DevicesShipped>"
                     +" <com:DeviceShipped>"
                       +"  <com:Name/>"
                        +" <com:Make>MOTO</com:Make>"
                         +"<com:Model>DCX3400</com:Model>"
                         +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                         +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
                         +"<com:MACAddress/>"
                         +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
                         +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
                         +"<com:MTAMACAddress/>"
                         +"<com:MasterSubsidiaryLock/>"
                         +"<com:USBMACAddress/>"
                         +"<com:UnitID>"+unit1+"</com:UnitID>"
                        +" <com:UnitID2/>"
                        +" <com:Condition>New</com:Condition>"
                        +" <com:DeviceType>STB</com:DeviceType>"
                         +"<com:MACAddress2/>"
                         +"<com:Make2>MOTO</com:Make2>"
                         +"<com:Model2>MSCC9062</com:Model2>"
                         +"<com:RFMACAddress/>"
                         +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                     +" </com:DeviceShipped>"
                   +"</com:DevicesShipped>"
                    +"</com:ProductShipped>"
                
           
	          
	                 
	                 
	                 //for rma drop downs
	                +" <com:ProductShipped>"
	                    +"<com:ProductID>"+(groupid3)+"</com:ProductID>"
	                   +" <com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
	                    +"<com:DevicesShipped>"
	                       +"<com:DeviceShipped>"
	                          +"<com:Name/>"
	                          +"<com:Make/>"
	                          +"<com:Model/>"
	                         +" <com:SerialNumber/>"
	                          +"<com:SerialNumber2/>"
	                         +" <com:MACAddress/>"
	                         +" <com:CMACAddress/>"
	                          +"<com:EMACAddress/>"
	                         +" <com:MTAMACAddress/>"
	                         +" <com:MasterSubsidiaryLock/>"
	                         +" <com:USBMACAddress/>"
	                          +"<com:UnitID/>"
	                          +"<com:UnitID2/>"
	                         +" <com:Condition>New</com:Condition>"
	                         +" <com:DeviceType/>"
	                          +"<com:MACAddress2/>"
	                         +" <com:Make2/>"
	                          +"<com:Model2/>"
	                          +"<com:RFMACAddress/>"
	                         +" <com:DeviceID>"+(deviceid3)+"</com:DeviceID>"
	                      +" </com:DeviceShipped>"
	                   +" </com:DevicesShipped>"
	                +" </com:ProductShipped>"
	                 +"<com:ProductShipped>"
	                   +" <com:ProductID>"+(groupid4)+"</com:ProductID>"
	                    +"<com:FulfillmentCode>"+fulfillmentcode4+"</com:FulfillmentCode>"
	                   +" <com:DevicesShipped>"
	                       +"<com:DeviceShipped>"
	                         +" <com:Name/>"
	                          +"<com:Make/>"
	                          +"<com:Model/>"
	                          +"<com:SerialNumber/>"
	                          +"<com:SerialNumber2/>"
	                          +"<com:MACAddress/>"
	                          +"<com:CMACAddress/>"
	                          +"<com:EMACAddress/>"
	                          +"<com:MTAMACAddress/>"
	                          +"<com:MasterSubsidiaryLock/>"
	                          +"<com:USBMACAddress/>"
	                          +"<com:UnitID/>"
	                         +" <com:UnitID2/>"
	                          +"<com:Condition>New</com:Condition>"
	                          +"<com:DeviceType/>"
	                          +"<com:MACAddress2/>"
	                          +"<com:Make2/>"
	                         +" <com:Model2/>"
	                          +"<com:RFMACAddress/>"
	                          +"<com:DeviceID>"+(deviceid4)+"</com:DeviceID>"
	                    +"   </com:DeviceShipped>"
	                   +" </com:DevicesShipped>"
	                 +"</com:ProductShipped>"
	              +"</com:ProductsShipped>"
	          +" </com:Shipment>"
	        +"</com:Shipments>"
	     +"</com:Request>"
	  +"</com:PostShipment>"
	+" </soapenv:Body>"
	+"</soapenv:Envelope>";
		
		report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);

		 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);

		
		 report.updateTestLog("The product id for "+fulfillmentcode3, "Fetched successfully as : "+groupid3, Status.PASS);

		 report.updateTestLog("The device id for "+fulfillmentcode3, "Fetched successfully as : "+deviceid3, Status.PASS);

		 report.updateTestLog("The product id for "+fulfillmentcode4, "Fetched successfully as : "+groupid4, Status.PASS);

		 report.updateTestLog("The device id for "+fulfillmentcode4, "Fetched successfully as : "+deviceid4, Status.PASS);

		 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device1, Status.PASS);		

		 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device2, Status.PASS);		
		
		
	}
      

//combo x1cmemta csg cisco
if((fulfillmentcode.equals("WG-XB3-DPC3941T-CDV-BATT")||fulfillmentcode.equals("WG-XB3-DPC3941T-X1CDV-BAT"))&&(fulfillmentcode1.equals("VID-Xi3"))&&(fulfillmentcode2.equals("VID-X1SWap-MX011ANM")))
{
	
	Random rn        = new Random();
	int    range     = 9999999 - 1000000 + 1;  
	int    randomNum =  rn.nextInt(range) + 1000000;  // For 7 digit number
	System.out.println(randomNum);

	Random rc = new Random();
	char   c  = (char)(rc.nextInt(26) + 'A');
	System.out.println(c);
	char   c1  = (char)(rc.nextInt(26) + 'A');
	System.out.println(c1);
	
	

	 add = "0086"+randomNum+"D";    
	 add1 = "FD1F"+randomNum+"A";    
	System.out.println(add);
	System.out.println(add1);
	System.out.println("ttest1");
	Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
     +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
      +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
   +"</wsse:UsernameToken>"
+" </wsse:Security>"
+"<com:requestHeader>"
   +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
  +" <com:sourceSystemId/>"
   +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
   +"<com:sourceServerId>1</com:sourceServerId>"
   +"<com:trackingId>1</com:trackingId>"
+" </com:requestHeader>"
+"</soapenv:Header>"
+"<soapenv:Body>"
+"<com:PostShipment>"
   +"<com:Request>"
      +"<com:OrderID>"+Orderid+"</com:OrderID>"
      +"<com:Shipments>"
         +"<com:Shipment>"
            +"<com:Carrier>UPS</com:Carrier>"
           +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
            +"<com:Method>Overnight</com:Method>"
            +"<com:Packages>"
               +"<com:Package>"
                 +" <com:Cost>25.34</com:Cost>"
                 +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
                 +" <com:ReturnTrackingNumber/>"
                 +" <com:Weight>2.35</com:Weight>"
               +"</com:Package>"
           +" </com:Packages>"
               
+"<com:ProductsShipped>"
              
                   
                  
	                    +" <com:ProductShipped>"
	                       +" <com:ProductID>"+groupid+"</com:ProductID>"
	                	                        
	                        +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
	                        +"<com:DevicesShipped>"
	                          +" <com:DeviceShipped>"
	                            +"  <com:Name/>"
	                             +" <com:Make>CI</com:Make>"
	                              +"<com:Model>DPC3939</com:Model>"
	                              +"<com:SerialNumber>"+add1+"</com:SerialNumber>"
	                             
	                              +"<com:MACAddress/>"
	                              +"<com:CMACAddress>"+add1+"</com:CMACAddress>"
	                              +"<com:MTAMACAddress>"+add+"</com:MTAMACAddress>"
	                             
	                              +"<com:MasterSubsidiaryLock/>"
	                              +"<com:USBMACAddress/>"
	                             
	                             +" <com:UnitID2/>"
	                             +" <com:Condition>New</com:Condition>"
	                             +" <com:DeviceType>eMTA</com:DeviceType>"
	                              +"<com:MACAddress2/>"
	                             
	                              +"<com:RFMACAddress/>"
	                              +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
	                          +" </com:DeviceShipped>"
	                        +"</com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                     
	       
	                          
	                           //dct
	                     
	                           +"<com:ProductShipped>"
	       	                +"<com:ProductID>"+groupid1+"</com:ProductID>"
	       	                +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
	       	               +" <com:DevicesShipped>"
	       	                  +" <com:DeviceShipped>"
	       	                     +" <com:Name/>"
	       	                     +" <com:Make>PA</com:Make>"
	       	                     +" <com:Model>PX032ANI</com:Model>"
	       	                      +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
	       	                    +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
	       	                  
	       	                      +"<com:MasterSubsidiaryLock/>"
	       	                     +" <com:USBMACAddress/>"
	       	                    
	       	                      +"<com:Condition>New</com:Condition>"
	       	                      +"<com:DeviceType>STB</com:DeviceType>"
	       	                    +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
	       	                   +"</com:DeviceShipped>"
	       	                +"</com:DevicesShipped>"
	       	             +"</com:ProductShipped>"
	       	                
	       	             
	                     
	                     //dvr
	       	                         +" <com:ProductShipped>"
	       	                         +" <com:ProductID>"+groupid2+"</com:ProductID>"
	       	                  	                        
	       	                          +"<com:FulfillmentCode>VID-X1SWap-MX011ANC</com:FulfillmentCode>"
	       	                          +"<com:DevicesShipped>"
	       	                            +" <com:DeviceShipped>"
	       	                              +"  <com:Name/>"
	       	                               +" <com:Make>MOTO</com:Make>"
	       	                                +"<com:Model>MX011ANC</com:Model>"
	       	                                +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
	       	                                +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
	       	                                +"<com:MACAddress/>"
	       	                                +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
	       	                                +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
	       	                                +"<com:MTAMACAddress/>"
	       	                                +"<com:MasterSubsidiaryLock/>"
	       	                                +"<com:USBMACAddress/>"
	       	                                +"<com:UnitID/>"
	       	                               +" <com:UnitID2/>"
	       	                               +" <com:Condition>New</com:Condition>"
	       	                               +" <com:DeviceType>STB</com:DeviceType>"
	       	                                +"<com:MACAddress2/>"
	       	                                +"<com:Make2>SAC</com:Make2>"
	       	                                +"<com:Model2>MCARD</com:Model2>"
	       	                                +"<com:RFMACAddress>"+rfmac2+"</com:RFMACAddress>"
	       	                                +"<com:DeviceID>"+deviceid2+"</com:DeviceID>"
	       	                            +" </com:DeviceShipped>"
	       	                          +"</com:DevicesShipped>"
	       	                           +"</com:ProductShipped>"
	       	                          
	                        
            +"</com:ProductsShipped>"
        +" </com:Shipment>"
      +"</com:Shipments>"
   +"</com:Request>"
+"</com:PostShipment>"
+" </soapenv:Body>"
+"</soapenv:Envelope>";
	
	 report.updateTestLog("The product id for EMTA device ", "Fetched successfully as : "+groupid, Status.PASS);

	 report.updateTestLog("The device id for EMTA device ", "Fetched successfully as : "+deviceid, Status.PASS);

	 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid2, Status.PASS);

	 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid2, Status.PASS);

	 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid2, Status.PASS);

	 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid2, Status.PASS);
	
	 report.updateTestLog("The serial no of EMTA device ", "Fetched successfully as : "+add1, Status.PASS);		
	 
	 report.updateTestLog("The serial no of DCT device ", "Fetched successfully as : "+device1, Status.PASS);		

	 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
	 
	 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
	 	
	
}


//eol ddp
if("VID-DF-DCH70-50-B".equals(fulfillmentcode))
{
System.out.println("test19");	

Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
+"<soapenv:Header>"
+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
 +"<wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
+"<wsse:Username>Contec</wsse:Username>"
+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
+"<wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
+"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
+"</wsse:UsernameToken>"
+"</wsse:Security>"
+"<com:requestHeader>"
+"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
+"<com:sourceSystemId/>"
+"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
+"<com:sourceServerId>1</com:sourceServerId>"
+"<com:trackingId>1</com:trackingId>"
+"</com:requestHeader>"
+"</soapenv:Header>"
+"<soapenv:Body>"
+"<com:PostShipment>"
+"<com:Request>"
+"<com:OrderID>"+Orderid+"</com:OrderID>"
+"<com:Shipments>"
+"<com:Shipment>"
  +"<com:Carrier>UPS</com:Carrier>"
 +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
 +" <com:Method>Overnight</com:Method>"
  +"<com:Packages>"
    +" <com:Package>"
        +"<com:Cost>25.34</com:Cost>"
       +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
        +"<com:ReturnTrackingNumber/>"
        +"<com:Weight>2.35</com:Weight>"
     +"</com:Package>"
  +"</com:Packages>"
     
+"<com:ProductsShipped>"
    
+"<com:ProductShipped>"
+"<com:ProductID>"+groupid+"</com:ProductID>"
               
+"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
+"<com:DevicesShipped>"
+"<com:DeviceShipped>"
 +"<com:Name/>"
 +"<com:Make>MOTO</com:Make>"
 +"<com:Model>DCX3200</com:Model>"
+" <com:SerialNumber>"+device1+"</com:SerialNumber>"
 +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
 +"<com:MACAddress/>"
 +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
 +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
 +"<com:MTAMACAddress/>"
+" <com:MasterSubsidiaryLock/>"
 +"<com:USBMACAddress/>"
+"<com:UnitID>"+unit1+"</com:UnitID>"
 +"<com:UnitID2/>"
 +"<com:Condition>New</com:Condition>"
 +"<com:DeviceType>STB</com:DeviceType>"
 +"<com:MACAddress2/>"
 +"<com:Make2>MOTO</com:Make2>"
+" <com:Model2>MSCC9062</com:Model2>"
 +"<com:RFMACAddress/>"
 +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
+"</com:DeviceShipped>"
+"</com:DevicesShipped>"
+"</com:ProductShipped>"

+"<com:ProductShipped>"
+"<com:ProductID>"+(groupid+1)+"</com:ProductID>"
+"<com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
+"<com:DevicesShipped>"
+"<com:DeviceShipped>"
+"<com:Name/>"
+"<com:Make/>"
+"<com:Model/>"
+"<com:SerialNumber/>"
+"<com:SerialNumber2/>"
+"<com:MACAddress/>"
+"<com:CMACAddress/>"
+"<com:EMACAddress/>"
+"<com:MTAMACAddress/>"
+"<com:MasterSubsidiaryLock/>"
+"<com:USBMACAddress/>"
+"<com:UnitID/>"
+"<com:UnitID2/>"
+"<com:Condition>New</com:Condition>"
+"<com:DeviceType/>"
+"<com:MACAddress2/>"
+"<com:Make2/>"
+"<com:Model2/>"
+"<com:RFMACAddress/>"
+"<com:DeviceID>"+(deviceid+1)+"</com:DeviceID>"
+"</com:DeviceShipped>"
+"</com:DevicesShipped>"
+"</com:ProductShipped>"


+"</com:ProductsShipped>"
+"</com:Shipment>"
+"</com:Shipments>"
+"</com:Request>"
+"</com:PostShipment>"
+"</soapenv:Body>"
+"</soapenv:Envelope>";


report.updateTestLog("The product id for EOL device ", "Fetched successfully as : "+groupid, Status.PASS);

report.updateTestLog("The device id for EOL device ", "Fetched successfully as : "+deviceid, Status.PASS);


report.updateTestLog("The product id for RMA-EasyReturns", "Fetched successfully as : "+(groupid+1), Status.PASS);

report.updateTestLog("The device id for RMA-EasyReturns", "Fetched successfully as : "+(deviceid+1), Status.PASS);


report.updateTestLog("The serial no of EOL device ", "Fetched successfully as : "+device1, Status.PASS);		

report.updateTestLog("The serial no of EOL device ", "Fetched successfully as : "+device2, Status.PASS);		


}


//ddp x1dvrdctdct
if("VID-SMRNG150BNMD".equals(fulfillmentcode3)&&"VID-PCRNG150BNCD".equals(fulfillmentcode1)&&"VID-PCRNG150BNCD".equals(fulfillmentcode)&&"VID-X1SWap-MX011ANM".equals(fulfillmentcode2)&&"RMA-EasyReturns".equals(fulfillmentcode5))
{
	System.out.println("testtestddp");
	 Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	   +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	    +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	 +"</wsse:UsernameToken>"
	+" </wsse:Security>"
	+"<com:requestHeader>"
	 +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	+" <com:sourceSystemId/>"
	 +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	 +"<com:sourceServerId>1</com:sourceServerId>"
	 +"<com:trackingId>1</com:trackingId>"
	+" </com:requestHeader>"
	+"</soapenv:Header>"
	+"<soapenv:Body>"
	+"<com:PostShipment>"
	 +"<com:Request>"
	    +"<com:OrderID>"+Orderid+"</com:OrderID>"
	    +"<com:Shipments>"
	       +"<com:Shipment>"
	          +"<com:Carrier>UPS</com:Carrier>"
	         +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	          +"<com:Method>Overnight</com:Method>"
	          +"<com:Packages>"
	             +"<com:Package>"
	               +" <com:Cost>25.34</com:Cost>"
	               +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	               +" <com:ReturnTrackingNumber/>"
	               +" <com:Weight>2.35</com:Weight>"
	             +"</com:Package>"
	         +" </com:Packages>"
	             
	         //for dct mpeg4
	          +"<com:ProductsShipped>"
	            +" <com:ProductShipped>"
	               +" <com:ProductID>"+groupid+"</com:ProductID>"
	        	                        
	                +"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
	                +"<com:DevicesShipped>"
	                  +" <com:DeviceShipped>"
	                    +"  <com:Name/>"
	                     +" <com:Make>SAM</com:Make>"
	                      +"<com:Model>SR150BNM</com:Model>"
	                      +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
	                      +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
	                      +"<com:MACAddress/>"
	                      +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
	                      +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
	                      +"<com:MTAMACAddress/>"
	                      +"<com:MasterSubsidiaryLock/>"
	                      +"<com:USBMACAddress/>"
	                      +"<com:UnitID>"+unit1+"</com:UnitID>"
	                     +" <com:UnitID2/>"
	                     +" <com:Condition>New</com:Condition>"
	                     +" <com:DeviceType>STB</com:DeviceType>"
	                      +"<com:MACAddress2/>"
	                      +"<com:Make2>MOTO</com:Make2>"
	                      +"<com:Model2>MSCC9063</com:Model2>"
	                      +"<com:RFMACAddress/>"
	                      +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
	                  +" </com:DeviceShipped>"
	                +"</com:DevicesShipped>"
	                 +"</com:ProductShipped>"
	             
	                 
	                 //dct
	                 +" <com:ProductShipped>"
		               +" <com:ProductID>"+groupid1+"</com:ProductID>"
		        	                        
		                +"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
		                +"<com:DevicesShipped>"
		                  +" <com:DeviceShipped>"
		                    +"  <com:Name/>"
		                     +" <com:Make>SAM</com:Make>"
		                      +"<com:Model>SR150BNM</com:Model>"
		                      +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
		                      +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
		                      +"<com:MACAddress/>"
		                      +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
		                      +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
		                      +"<com:MTAMACAddress/>"
		                      +"<com:MasterSubsidiaryLock/>"
		                      +"<com:USBMACAddress/>"
		                      +"<com:UnitID>"+unit2+"</com:UnitID>"
		                     +" <com:UnitID2/>"
		                     +" <com:Condition>New</com:Condition>"
		                     +" <com:DeviceType>STB</com:DeviceType>"
		                      +"<com:MACAddress2/>"
		                      +"<com:Make2>MOTO</com:Make2>"
		                      +"<com:Model2>MSCC9063</com:Model2>"
		                      +"<com:RFMACAddress/>"
		                      +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
		                  +" </com:DeviceShipped>"
		                +"</com:DevicesShipped>"
		                 +"</com:ProductShipped>"
	                 
	                 //for dvr
	                 
	                
	                    +" <com:ProductShipped>"
	                       +" <com:ProductID>"+groupid2+"</com:ProductID>"
	                	                        
	                        +"<com:FulfillmentCode>"+fulfillmentcode2+"</com:FulfillmentCode>"
	                        +"<com:DevicesShipped>"
	                          +" <com:DeviceShipped>"
	                            +"  <com:Name/>"
	                             +" <com:Make>MOTO</com:Make>"
	                              +"<com:Model>MX011ANM</com:Model>"
	                              +"<com:SerialNumber>"+device5+"</com:SerialNumber>"
	                              +"<com:SerialNumber2>"+device6+"</com:SerialNumber2>"
	                              +"<com:MACAddress/>"
	                              +"<com:CMACAddress>"+cmmac2+"</com:CMACAddress>"
	                              +"<com:EMACAddress>"+mtamac2+"</com:EMACAddress>"
	                              +"<com:MTAMACAddress/>"
	                              +"<com:MasterSubsidiaryLock/>"
	                              +"<com:USBMACAddress/>"
	                              +"<com:UnitID>"+unit3+"</com:UnitID>"
	                             +" <com:UnitID2/>"
	                             +" <com:Condition>New</com:Condition>"
	                             +" <com:DeviceType>STB</com:DeviceType>"
	                              +"<com:MACAddress2/>"
	                              +"<com:Make2>MOTO</com:Make2>"
	                              +"<com:Model2>MSCC9063</com:Model2>"
	                              +"<com:RFMACAddress/>"
	                              +"<com:DeviceID>"+deviceid2+"</com:DeviceID>"
	                          +" </com:DeviceShipped>"
	                        +"</com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                        
	                         +"<com:ProductShipped>"
	                         +" <com:ProductID>"+groupid5+"</com:ProductID>"
	                         +"<com:FulfillmentCode>"+fulfillmentcode5+"</com:FulfillmentCode>"
	                         +" <com:DevicesShipped>"
	                            +"<com:DeviceShipped>"
	                              +" <com:Name/>"
	                               +"<com:Make/>"
	                               +"<com:Model/>"
	                               +"<com:SerialNumber/>"
	                               +"<com:SerialNumber2/>"
	                               +"<com:MACAddress/>"
	                               +"<com:CMACAddress/>"
	                               +"<com:EMACAddress/>"
	                               +"<com:MTAMACAddress/>"
	                               +"<com:MasterSubsidiaryLock/>"
	                               +"<com:USBMACAddress/>"
	                               +"<com:UnitID/>"
	                              +" <com:UnitID2/>"
	                               +"<com:Condition>New</com:Condition>"
	                               +"<com:DeviceType/>"
	                               +"<com:MACAddress2/>"
	                               +"<com:Make2/>"
	                              +" <com:Model2/>"
	                               +"<com:RFMACAddress/>"
	                               +"<com:DeviceID>"+deviceid5+"</com:DeviceID>"
	                         +"   </com:DeviceShipped>"
	                         +" </com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                         
	     +"</com:ProductsShipped>"
	     +" </com:Shipment>"
	    +"</com:Shipments>"
	    +"</com:Request>"
	    +"</com:PostShipment>"
	    +" </soapenv:Body>"
	    +"</soapenv:Envelope>";
	 
	

	 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid2, Status.PASS);

	 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid2, Status.PASS);

	 report.updateTestLog("The product id for DCT1 device ", "Fetched successfully as : "+groupid, Status.PASS);

	 report.updateTestLog("The device id for DCT1 device ", "Fetched successfully as : "+deviceid, Status.PASS);
	 
	 report.updateTestLog("The product id for DCT1 device ", "Fetched successfully as : "+groupid, Status.PASS);

	 report.updateTestLog("The device id for DCT1 device ", "Fetched successfully as : "+deviceid, Status.PASS);
	
	 report.updateTestLog("The serial no of EMTA device ", "Fetched successfully as : "+add1, Status.PASS);		
	 
	 report.updateTestLog("The serial no of DCT1 device ", "Fetched successfully as : "+device1, Status.PASS);	
	 report.updateTestLog("The serial no of DCT1 device ", "Fetched successfully as : "+device2, Status.PASS);	
	 
	 report.updateTestLog("The serial no of DCT2 device ", "Fetched successfully as : "+device3, Status.PASS);	
	 report.updateTestLog("The serial no of DCT2 device ", "Fetched successfully as : "+device4, Status.PASS);	

	 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device5, Status.PASS);		
	 
	 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device6, Status.PASS);	
	 	
	
	 
	 
	
	
}


//x1ddp dvr dct
if("VID-X1SWap-MX011ANM".equals(fulfillmentcode1)&&"VID-PCRNG150BNCD".equals(fulfillmentcode)&&"RMA-EasyReturns".equals(fulfillmentcode4))
{
	System.out.println("testtestddp");
	 Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	   +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	    +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	 +"</wsse:UsernameToken>"
	+" </wsse:Security>"
	+"<com:requestHeader>"
	 +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	+" <com:sourceSystemId/>"
	 +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	 +"<com:sourceServerId>1</com:sourceServerId>"
	 +"<com:trackingId>1</com:trackingId>"
	+" </com:requestHeader>"
	+"</soapenv:Header>"
	+"<soapenv:Body>"
	+"<com:PostShipment>"
	 +"<com:Request>"
	    +"<com:OrderID>"+Orderid+"</com:OrderID>"
	    +"<com:Shipments>"
	       +"<com:Shipment>"
	          +"<com:Carrier>UPS</com:Carrier>"
	         +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	          +"<com:Method>Overnight</com:Method>"
	          +"<com:Packages>"
	             +"<com:Package>"
	               +" <com:Cost>25.34</com:Cost>"
	               +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	               +" <com:ReturnTrackingNumber/>"
	               +" <com:Weight>2.35</com:Weight>"
	             +"</com:Package>"
	         +" </com:Packages>"
	             
	         
	         //for dvr
             
             
             +" <com:ProductShipped>"
                +" <com:ProductID>"+groupid+"</com:ProductID>"
         	                        
                 +"<com:FulfillmentCode>VID-PCRNG150BNMD</com:FulfillmentCode>"
                 +"<com:DevicesShipped>"
                   +" <com:DeviceShipped>"
                     +"  <com:Name/>"
                      +" <com:Make>MOTO</com:Make>"
                       +"<com:Model>MX011ANM</com:Model>"
                       +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                       +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
                       +"<com:MACAddress/>"
                       +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
                       +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
                       +"<com:MTAMACAddress/>"
                       +"<com:MasterSubsidiaryLock/>"
                       +"<com:USBMACAddress/>"
                       +"<com:UnitID>"+unit1+"</com:UnitID>"
                      +" <com:UnitID2/>"
                      +" <com:Condition>New</com:Condition>"
                      +" <com:DeviceType>STB</com:DeviceType>"
                       +"<com:MACAddress2/>"
                       +"<com:Make2>MOTO</com:Make2>"
                       +"<com:Model2>MSCC9063</com:Model2>"
                       +"<com:RFMACAddress/>"
                       +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                   +" </com:DeviceShipped>"
                 +"</com:DevicesShipped>"
                  +"</com:ProductShipped>"
	         
	         
	         //for dct mpeg4
	          +"<com:ProductsShipped>"
	            +" <com:ProductShipped>"
	               +" <com:ProductID>"+groupid1+"</com:ProductID>"
	        	                        
	                +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
	                +"<com:DevicesShipped>"
	                  +" <com:DeviceShipped>"
	                    +"  <com:Name/>"
	                     +" <com:Make>SAM</com:Make>"
	                      +"<com:Model>SR150BNM</com:Model>"
	                      +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
	                      +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
	                      +"<com:MACAddress/>"
	                      +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
	                      +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
	                      +"<com:MTAMACAddress/>"
	                      +"<com:MasterSubsidiaryLock/>"
	                      +"<com:USBMACAddress/>"
	                      +"<com:UnitID>"+unit2+"</com:UnitID>"
	                     +" <com:UnitID2/>"
	                     +" <com:Condition>New</com:Condition>"
	                     +" <com:DeviceType>STB</com:DeviceType>"
	                      +"<com:MACAddress2/>"
	                      +"<com:Make2>MOTO</com:Make2>"
	                      +"<com:Model2>MSCC9063</com:Model2>"
	                      +"<com:RFMACAddress/>"
	                      +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
	                  +" </com:DeviceShipped>"
	                +"</com:DevicesShipped>"
	                 +"</com:ProductShipped>"
	             
	               
	               
	                        
	                         +"<com:ProductShipped>"
	                         +" <com:ProductID>"+groupid4+"</com:ProductID>"
	                         +"<com:FulfillmentCode>"+fulfillmentcode4+"</com:FulfillmentCode>"
	                         +" <com:DevicesShipped>"
	                            +"<com:DeviceShipped>"
	                              +" <com:Name/>"
	                               +"<com:Make/>"
	                               +"<com:Model/>"
	                               +"<com:SerialNumber/>"
	                               +"<com:SerialNumber2/>"
	                               +"<com:MACAddress/>"
	                               +"<com:CMACAddress/>"
	                               +"<com:EMACAddress/>"
	                               +"<com:MTAMACAddress/>"
	                               +"<com:MasterSubsidiaryLock/>"
	                               +"<com:USBMACAddress/>"
	                               +"<com:UnitID/>"
	                              +" <com:UnitID2/>"
	                               +"<com:Condition>New</com:Condition>"
	                               +"<com:DeviceType/>"
	                               +"<com:MACAddress2/>"
	                               +"<com:Make2/>"
	                              +" <com:Model2/>"
	                               +"<com:RFMACAddress/>"
	                               +"<com:DeviceID>"+deviceid4+"</com:DeviceID>"
	                         +"   </com:DeviceShipped>"
	                         +" </com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                         
	     +"</com:ProductsShipped>"
	     +" </com:Shipment>"
	    +"</com:Shipments>"
	    +"</com:Request>"
	    +"</com:PostShipment>"
	    +" </soapenv:Body>"
	    +"</soapenv:Envelope>";
	
	
}

//x1dvrdct csg
if("VID-X1SWap-MX011ANM".equals(fulfillmentcode1)&&"VID-Xi3".equals(fulfillmentcode)&&"RMA-EasyReturns".equals(fulfillmentcode3))
{
	System.out.println("testtestddp1");
	 Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	   +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	    +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	 +"</wsse:UsernameToken>"
	+" </wsse:Security>"
	+"<com:requestHeader>"
	 +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	+" <com:sourceSystemId/>"
	 +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	 +"<com:sourceServerId>1</com:sourceServerId>"
	 +"<com:trackingId>1</com:trackingId>"
	+" </com:requestHeader>"
	+"</soapenv:Header>"
	+"<soapenv:Body>"
	+"<com:PostShipment>"
	 +"<com:Request>"
	    +"<com:OrderID>"+Orderid+"</com:OrderID>"
	    +"<com:Shipments>"
	       +"<com:Shipment>"
	          +"<com:Carrier>UPS</com:Carrier>"
	         +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	          +"<com:Method>Overnight</com:Method>"
	          +"<com:Packages>"
	             +"<com:Package>"
	               +" <com:Cost>25.34</com:Cost>"
	               +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	               +" <com:ReturnTrackingNumber/>"
	               +" <com:Weight>2.35</com:Weight>"
	             +"</com:Package>"
	         +" </com:Packages>"
	             
+"<com:ProductsShipped>"
	         //for dvr
             
             
             +" <com:ProductShipped>"
                +" <com:ProductID>"+groupid1+"</com:ProductID>"
         	                        
                 +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
                 +"<com:DevicesShipped>"
                   +" <com:DeviceShipped>"
                     +"  <com:Name/>"
                      +" <com:Make>MOTO</com:Make>"
                       +"<com:Model>MX011ANM</com:Model>"
                       +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
                       +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
                       +"<com:MACAddress/>"
                       +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
                       +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
                       +"<com:MTAMACAddress/>"
                       +"<com:MasterSubsidiaryLock/>"
                       +"<com:USBMACAddress/>"
                       +"<com:UnitID>"+unit1+"</com:UnitID>"
                      +" <com:UnitID2/>"
                      +" <com:Condition>New</com:Condition>"
                      +" <com:DeviceType>STB</com:DeviceType>"
                       +"<com:MACAddress2/>"
                       +"<com:Make2>MOTO</com:Make2>"
                       +"<com:Model2>MSCC9063</com:Model2>"
                       +"<com:RFMACAddress/>"
                       +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
                   +" </com:DeviceShipped>"
                 +"</com:DevicesShipped>"
                  +"</com:ProductShipped>"
	         
	         
	         //for dct mpeg4

                  +"<com:ProductShipped>"
                     +"<com:ProductID>"+groupid+"</com:ProductID>"
                     +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
                    +" <com:DevicesShipped>"
                       +" <com:DeviceShipped>"
                          +" <com:Name/>"
                          +" <com:Make>PA</com:Make>"
                          +" <com:Model>PX032ANI</com:Model>"
                           +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                         +"<com:RFMACAddress>"+rfmac2+"</com:RFMACAddress>"
                          
                           
                    
                           +"<com:MasterSubsidiaryLock/>"
                          +" <com:USBMACAddress/>"
                         
                           +"<com:Condition>New</com:Condition>"
                           +"<com:DeviceType>STB</com:DeviceType>"
                       
                         
                         
                           
                           +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                        +"</com:DeviceShipped>"
                     +"</com:DevicesShipped>"
                  +"</com:ProductShipped>"
                         
     	                 
	               
	               
	                        
	                         +"<com:ProductShipped>"
	                         +" <com:ProductID>"+groupid3+"</com:ProductID>"
	                         +"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
	                         +" <com:DevicesShipped>"
	                            +"<com:DeviceShipped>"
	                              +" <com:Name/>"
	                               +"<com:Make/>"
	                               +"<com:Model/>"
	                               +"<com:SerialNumber/>"
	                               +"<com:SerialNumber2/>"
	                               +"<com:MACAddress/>"
	                               +"<com:CMACAddress/>"
	                               +"<com:EMACAddress/>"
	                               +"<com:MTAMACAddress/>"
	                               +"<com:MasterSubsidiaryLock/>"
	                               +"<com:USBMACAddress/>"
	                               +"<com:UnitID/>"
	                              +" <com:UnitID2/>"
	                               +"<com:Condition>New</com:Condition>"
	                               +"<com:DeviceType/>"
	                               +"<com:MACAddress2/>"
	                               +"<com:Make2/>"
	                              +" <com:Model2/>"
	                               +"<com:RFMACAddress/>"
	                               +"<com:DeviceID>"+deviceid3+"</com:DeviceID>"
	                         +"   </com:DeviceShipped>"
	                         +" </com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                         
	     +"</com:ProductsShipped>"
	     +" </com:Shipment>"
	    +"</com:Shipments>"
	    +"</com:Request>"
	    +"</com:PostShipment>"
	    +" </soapenv:Body>"
	    +"</soapenv:Envelope>";
	
	
}






//x1 CSG cisco dvr dct
if("VID-Xi3".equals(fulfillmentcode)&&"RMA-EasyReturns".equals(fulfillmentcode3)&&fulfillmentcode2==null)
{
	System.out.println("test88");
	Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
     +"</wsse:UsernameToken>"
 +" </wsse:Security>"
  +"<com:requestHeader>"
     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
    +" <com:sourceSystemId/>"
     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
     +"<com:sourceServerId>1</com:sourceServerId>"
     +"<com:trackingId>1</com:trackingId>"
 +" </com:requestHeader>"
 +"</soapenv:Header>"
 +"<soapenv:Body>"
  +"<com:PostShipment>"
     +"<com:Request>"
        +"<com:OrderID>"+Orderid+"</com:OrderID>"
        +"<com:Shipments>"
           +"<com:Shipment>"
              +"<com:Carrier>UPS</com:Carrier>"
             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
              +"<com:Method>Overnight</com:Method>"
              +"<com:Packages>"
                 +"<com:Package>"
                   +" <com:Cost>25.34</com:Cost>"
                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
                   +" <com:ReturnTrackingNumber/>"
                   +" <com:Weight>2.35</com:Weight>"
                 +"</com:Package>"
             +" </com:Packages>"
                 
 +"<com:ProductsShipped>"
             //for dvr
             
             
             +" <com:ProductShipped>"
                +" <com:ProductID>"+groupid1+"</com:ProductID>"
         	                        
                 +"<com:FulfillmentCode>VID-X1SWap-MX011ANC</com:FulfillmentCode>"
                 +"<com:DevicesShipped>"
                   +" <com:DeviceShipped>"
                     +"  <com:Name/>"
                      +" <com:Make>MOTO</com:Make>"
                       +"<com:Model>MX011ANC</com:Model>"
                       +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
                       +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
                       +"<com:MACAddress/>"
                       +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
                       +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
                       +"<com:MTAMACAddress/>"
                       +"<com:MasterSubsidiaryLock/>"
                       +"<com:USBMACAddress/>"
                       +"<com:UnitID/>"
                      +" <com:UnitID2/>"
                      +" <com:Condition>New</com:Condition>"
                      +" <com:DeviceType>STB</com:DeviceType>"
                       +"<com:MACAddress2/>"
                       +"<com:Make2>SAC</com:Make2>"
                       +"<com:Model2>MCARD</com:Model2>"
                       +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
                       +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
                   +" </com:DeviceShipped>"
                 +"</com:DevicesShipped>"
                  +"</com:ProductShipped>"
                 
             //for dct mpeg4
        
             +"<com:ProductShipped>"
                +"<com:ProductID>"+groupid+"</com:ProductID>"
                +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
               +" <com:DevicesShipped>"
                  +" <com:DeviceShipped>"
                     +" <com:Name/>"
                     +" <com:Make>PA</com:Make>"
                     +" <com:Model>PX032ANI</com:Model>"
                      +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                    +"<com:RFMACAddress>"+rfmac2+"</com:RFMACAddress>"
                     
                      
               
                      +"<com:MasterSubsidiaryLock/>"
                     +" <com:USBMACAddress/>"
                    
                      +"<com:Condition>New</com:Condition>"
                      +"<com:DeviceType>STB</com:DeviceType>"
                  
                    
                    
                      
                      +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                   +"</com:DeviceShipped>"
                +"</com:DevicesShipped>"
             +"</com:ProductShipped>"
                    
	                 
	         //rma
             +" <com:ProductShipped>"
             +"<com:ProductID>"+groupid3+"</com:ProductID>"
             +" <com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
             +"<com:DevicesShipped>"
                +"<com:DeviceShipped>"
                   +"<com:Name/>"
                   +"<com:Make/>"
                   +"<com:Model/>"
                  +" <com:SerialNumber/>"
                   +"<com:SerialNumber2/>"
                  +" <com:MACAddress/>"
                  +" <com:CMACAddress/>"
                   +"<com:EMACAddress/>"
                  +" <com:MTAMACAddress/>"
                  +" <com:MasterSubsidiaryLock/>"
                  +" <com:USBMACAddress/>"
                   +"<com:UnitID/>"
                   +"<com:UnitID2/>"
                  +" <com:Condition>New</com:Condition>"
                  +" <com:DeviceType/>"
                   +"<com:MACAddress2/>"
                  +" <com:Make2/>"
                   +"<com:Model2/>"
                   +"<com:RFMACAddress/>"
                  +" <com:DeviceID>"+deviceid3+"</com:DeviceID>"
               +" </com:DeviceShipped>"
             +" </com:DevicesShipped>"
             +" </com:ProductShipped>"
            
	                    
	                       
	                    +"</com:ProductsShipped>"
	                +" </com:Shipment>"
	              +"</com:Shipments>"
	           +"</com:Request>"
	        +"</com:PostShipment>"
	      +" </soapenv:Body>"
	      +"</soapenv:Envelope>";
	System.out.println("test5");
	
	report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);

	 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);

	 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);

	 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);

	
	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		

	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		

	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
	 
	
}



/*if(("WG-XB3-DPC3941T-CDV-BATT".equals(fulfillmentcode))&&("VID-MOTRNG200BN-MPEG4".equals(fulfillmentcode1))&&("VID-RNG110RF-MPEG4".equals(fulfillmentcode2)||"VID-MOTRNG150N-MPEG4".equals(fulfillmentcode2)))
{
	
	Random rn        = new Random();
	int    range     = 9999999 - 1000000 + 1;  
	int    randomNum =  rn.nextInt(range) + 1000000;  // For 7 digit number
	System.out.println(randomNum);

	Random rc = new Random();
	char   c  = (char)(rc.nextInt(26) + 'A');
	System.out.println(c);
	char   c1  = (char)(rc.nextInt(26) + 'A');
	System.out.println(c1);
	

	 add = "0086"+randomNum+"D";    
	 add1 = "FD1F"+randomNum+"A";    
	System.out.println(add);
	System.out.println(add1);
	System.out.println("ttest");
	Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
     +"</wsse:UsernameToken>"
 +" </wsse:Security>"
  +"<com:requestHeader>"
     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
    +" <com:sourceSystemId/>"
     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
     +"<com:sourceServerId>1</com:sourceServerId>"
     +"<com:trackingId>1</com:trackingId>"
 +" </com:requestHeader>"
 +"</soapenv:Header>"
 +"<soapenv:Body>"
  +"<com:PostShipment>"
     +"<com:Request>"
        +"<com:OrderID>"+Orderid+"</com:OrderID>"
        +"<com:Shipments>"
           +"<com:Shipment>"
              +"<com:Carrier>UPS</com:Carrier>"
             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
              +"<com:Method>Overnight</com:Method>"
              +"<com:Packages>"
                 +"<com:Package>"
                   +" <com:Cost>25.34</com:Cost>"
                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
                   +" <com:ReturnTrackingNumber/>"
                   +" <com:Weight>2.35</com:Weight>"
                 +"</com:Package>"
             +" </com:Packages>"
                 
+"<com:ProductsShipped>"
                
                     
                    
	                    +" <com:ProductShipped>"
	                       +" <com:ProductID>"+groupid+"</com:ProductID>"
	                	                        
	                        +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
	                        +"<com:DevicesShipped>"
	                          +" <com:DeviceShipped>"
	                            +"  <com:Name/>"
	                             +" <com:Make>CI</com:Make>"
	                              +"<com:Model>DPC3939</com:Model>"
	                              +"<com:SerialNumber>"+add1+"</com:SerialNumber>"
	                             
	                              +"<com:MACAddress/>"
	                              +"<com:CMACAddress>"+add1+"</com:CMACAddress>"
	                              +"<com:MTAMACAddress>"+add+"</com:MTAMACAddress>"
	                             
	                              +"<com:MasterSubsidiaryLock/>"
	                              +"<com:USBMACAddress/>"
	                             
	                             +" <com:UnitID2/>"
	                             +" <com:Condition>New</com:Condition>"
	                             +" <com:DeviceType>eMTA</com:DeviceType>"
	                              +"<com:MACAddress2/>"
	                             
	                              +"<com:RFMACAddress/>"
	                              +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
	                          +" </com:DeviceShipped>"
	                        +"</com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                     
	                         
              //dvr
	                         +" <com:ProductShipped>"
	                         +" <com:ProductID>"+groupid1+"</com:ProductID>"
	                  	                        
	                          +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
	                          +"<com:DevicesShipped>"
	                            +" <com:DeviceShipped>"
	                              +"  <com:Name/>"
	                               +" <com:Make>MOTO</com:Make>"
	                                +"<com:Model>DCX3400</com:Model>"
	                                +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
	                                +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
	                                +"<com:MACAddress/>"
	                                +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
	                                +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
	                                +"<com:MTAMACAddress/>"
	                                +"<com:MasterSubsidiaryLock/>"
	                                +"<com:USBMACAddress/>"
	                                +"<com:UnitID>"+unit1+"</com:UnitID>"
	                               +" <com:UnitID2/>"
	                               +" <com:Condition>New</com:Condition>"
	                               +" <com:DeviceType>STB</com:DeviceType>"
	                                +"<com:MACAddress2/>"
	                                +"<com:Make2>MOTO</com:Make2>"
	                                +"<com:Model2>MSCC9062</com:Model2>"
	                                +"<com:RFMACAddress/>"
	                                +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
	                            +" </com:DeviceShipped>"
	                          +"</com:DevicesShipped>"
	                           +"</com:ProductShipped>"
	                          
	                           //dct
	                     
	                           +" <com:ProductShipped>"
	                              +" <com:ProductID>"+(groupid2)+"</com:ProductID>"
	                       	                        
	                               +"<com:FulfillmentCode>"+fulfillmentcode2+"</com:FulfillmentCode>"
	                               +"<com:DevicesShipped>"
	                                 +" <com:DeviceShipped>"
	                                   +"  <com:Name/>"
	                                    +" <com:Make>MOTO</com:Make>"
	                                     +"<com:Model>DCX3200</com:Model>"
	                                     +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
	                                     +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
	                                     +"<com:MACAddress/>"
	                                     +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
	                                     +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
	                                     +"<com:MTAMACAddress/>"
	                                     +"<com:MasterSubsidiaryLock/>"
	                                     +"<com:USBMACAddress/>"
	                                     +"<com:UnitID>"+unit2+"</com:UnitID>"
	                                    +" <com:UnitID2/>"
	                                    +" <com:Condition>New</com:Condition>"
	                                    +" <com:DeviceType>STB</com:DeviceType>"
	                                     +"<com:MACAddress2/>"
	                                     +"<com:Make2>MOTO</com:Make2>"
	                                     +"<com:Model2>MSCC9062</com:Model2>"
	                                     +"<com:RFMACAddress/>"
	                                     +"<com:DeviceID>"+(deviceid2)+"</com:DeviceID>"
	                                 +" </com:DeviceShipped>"
	                               +"</com:DevicesShipped>"
	                                +"</com:ProductShipped>"
	                               
	                   
              
	                        
              +"</com:ProductsShipped>"
          +" </com:Shipment>"
        +"</com:Shipments>"
     +"</com:Request>"
  +"</com:PostShipment>"
+" </soapenv:Body>"
+"</soapenv:Envelope>";
	
	 report.updateTestLog("The product id for EMTA device ", "Fetched successfully as : "+groupid, Status.PASS);

	 report.updateTestLog("The device id for EMTA device ", "Fetched successfully as : "+deviceid, Status.PASS);

	 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);

	 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);

	 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid2, Status.PASS);

	 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid2, Status.PASS);
	
	 report.updateTestLog("The serial no of EMTA device ", "Fetched successfully as : "+add1, Status.PASS);		
	 
	 report.updateTestLog("The serial no of DCT device ", "Fetched successfully as : "+device3, Status.PASS);		

	 report.updateTestLog("The serial no of DCT device ", "Fetched successfully as : "+device4, Status.PASS);		

	 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device1, Status.PASS);		
	 
	 report.updateTestLog("The serial no of DVR device ", "Fetched successfully as : "+device2, Status.PASS);	
	 	
	
}

*/


/*System.out.println("test2");
 if(fulfillmentcode3.isEmpty()&&"VID-X1SWap-MX011ANM".equals(fulfillmentcode))
 {
	 System.out.println("test2");
	 Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	   +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	    +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	 +"</wsse:UsernameToken>"
	+" </wsse:Security>"
	+"<com:requestHeader>"
	 +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	+" <com:sourceSystemId/>"
	 +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	 +"<com:sourceServerId>1</com:sourceServerId>"
	 +"<com:trackingId>1</com:trackingId>"
	+" </com:requestHeader>"
	+"</soapenv:Header>"
	+"<soapenv:Body>"
	+"<com:PostShipment>"
	 +"<com:Request>"
	    +"<com:OrderID>"+Orderid+"</com:OrderID>"
	    +"<com:Shipments>"
	       +"<com:Shipment>"
	          +"<com:Carrier>UPS</com:Carrier>"
	         +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	          +"<com:Method>Overnight</com:Method>"
	          +"<com:Packages>"
	             +"<com:Package>"
	               +" <com:Cost>25.34</com:Cost>"
	               +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	               +" <com:ReturnTrackingNumber/>"
	               +" <com:Weight>2.35</com:Weight>"
	             +"</com:Package>"
	         +" </com:Packages>"
	             
	         //for dct mpeg4
	          +"<com:ProductsShipped>"
	            +" <com:ProductShipped>"
	               +" <com:ProductID>"+groupid+"</com:ProductID>"
	        	                        
	                +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
	                +"<com:DevicesShipped>"
	                  +" <com:DeviceShipped>"
	                    +"  <com:Name/>"
	                     +" <com:Make>MOTO</com:Make>"
	                      +"<com:Model>DCX3200</com:Model>"
	                      +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
	                      +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
	                      +"<com:MACAddress/>"
	                      +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
	                      +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
	                      +"<com:MTAMACAddress/>"
	                      +"<com:MasterSubsidiaryLock/>"
	                      +"<com:USBMACAddress/>"
	                      +"<com:UnitID></com:UnitID>"
	                     +" <com:UnitID2/>"
	                     +" <com:Condition>New</com:Condition>"
	                     +" <com:DeviceType>STB</com:DeviceType>"
	                      +"<com:MACAddress2/>"
	                      +"<com:Make2>MOTO</com:Make2>"
	                      +"<com:Model2>MSCC9062</com:Model2>"
	                      +"<com:RFMACAddress/>"
	                      +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
	                  +" </com:DeviceShipped>"
	                +"</com:DevicesShipped>"
	                 +"</com:ProductShipped>"
	             
	                 //for dvr
	                 
	                
	                    +" <com:ProductShipped>"
	                       +" <com:ProductID>"+groupid1+"</com:ProductID>"
	                	                        
	                        +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
	                        +"<com:DevicesShipped>"
	                          +" <com:DeviceShipped>"
	                            +"  <com:Name/>"
	                             +" <com:Make>MOTO</com:Make>"
	                              +"<com:Model>DCX3400</com:Model>"
	                              +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
	                              +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
	                              +"<com:MACAddress/>"
	                              +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
	                              +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
	                              +"<com:MTAMACAddress/>"
	                              +"<com:MasterSubsidiaryLock/>"
	                              +"<com:USBMACAddress/>"
	                              +"<com:UnitID></com:UnitID>"
	                             +" <com:UnitID2/>"
	                             +" <com:Condition>New</com:Condition>"
	                             +" <com:DeviceType>STB</com:DeviceType>"
	                              +"<com:MACAddress2/>"
	                              +"<com:Make2>MOTO</com:Make2>"
	                              +"<com:Model2>MSCC9062</com:Model2>"
	                              +"<com:RFMACAddress/>"
	                              +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
	                          +" </com:DeviceShipped>"
	                        +"</com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                         +" <com:ProductShipped>"
	                         +"<com:ProductID>"+groupid2+"</com:ProductID>"
	                         +" <com:FulfillmentCode>"+fulfillmentcode2+"</com:FulfillmentCode>"
	                         +"<com:DevicesShipped>"
	                            +"<com:DeviceShipped>"
	                               +"<com:Name/>"
	                               +"<com:Make/>"
	                               +"<com:Model/>"
	                              +" <com:SerialNumber/>"
	                               +"<com:SerialNumber2/>"
	                              +" <com:MACAddress/>"
	                              +" <com:CMACAddress/>"
	                               +"<com:EMACAddress/>"
	                              +" <com:MTAMACAddress/>"
	                              +" <com:MasterSubsidiaryLock/>"
	                              +" <com:USBMACAddress/>"
	                               +"<com:UnitID/>"
	                               +"<com:UnitID2/>"
	                              +" <com:Condition>New</com:Condition>"
	                              +" <com:DeviceType/>"
	                               +"<com:MACAddress2/>"
	                              +" <com:Make2/>"
	                               +"<com:Model2/>"
	                               +"<com:RFMACAddress/>"
	                              +" <com:DeviceID>"+deviceid2+"</com:DeviceID>"
	                           +" </com:DeviceShipped>"
	                         +" </com:DevicesShipped>"
	                         +" </com:ProductShipped>"
	                         +"<com:ProductShipped>"
	                         +" <com:ProductID>"+groupid3+"</com:ProductID>"
	                         +"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
	                         +" <com:DevicesShipped>"
	                            +"<com:DeviceShipped>"
	                              +" <com:Name/>"
	                               +"<com:Make/>"
	                               +"<com:Model/>"
	                               +"<com:SerialNumber/>"
	                               +"<com:SerialNumber2/>"
	                               +"<com:MACAddress/>"
	                               +"<com:CMACAddress/>"
	                               +"<com:EMACAddress/>"
	                               +"<com:MTAMACAddress/>"
	                               +"<com:MasterSubsidiaryLock/>"
	                               +"<com:USBMACAddress/>"
	                               +"<com:UnitID/>"
	                              +" <com:UnitID2/>"
	                               +"<com:Condition>New</com:Condition>"
	                               +"<com:DeviceType/>"
	                               +"<com:MACAddress2/>"
	                               +"<com:Make2/>"
	                              +" <com:Model2/>"
	                               +"<com:RFMACAddress/>"
	                               +"<com:DeviceID>"+deviceid3+"</com:DeviceID>"
	                         +"   </com:DeviceShipped>"
	                         +" </com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                         
	     +"</com:ProductsShipped>"
	     +" </com:Shipment>"
	    +"</com:Shipments>"
	    +"</com:Request>"
	    +"</com:PostShipment>"
	    +" </soapenv:Body>"
	    +"</soapenv:Envelope>";
	 
	 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);

	 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);

	 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);

	 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);

	 report.updateTestLog("The product id for "+fulfillmentcode2, "Fetched successfully as : "+groupid2, Status.PASS);

	 report.updateTestLog("The device id for "+fulfillmentcode2, "Fetched successfully as : "+deviceid2, Status.PASS);

	 report.updateTestLog("The product id for "+fulfillmentcode3, "Fetched successfully as : "+groupid3, Status.PASS);

	 report.updateTestLog("The device id for "+fulfillmentcode3, "Fetched successfully as : "+deviceid3, Status.PASS);

	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		

	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		

	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
	 
 }
   */

System.out.println("test3");
if("VID-Xi3".equals(fulfillmentcode)&&fulfillmentcode1==null&&fulfillmentcode2==null)
{
       System.out.println("test3");
      Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

      Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
                    +"<soapenv:Header>"
                    +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
                    +" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
                    +"<wsse:Username>Contec</wsse:Username>"
                    +"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
        +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
         +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
      +"</wsse:UsernameToken>"
  +" </wsse:Security>"
   +"<com:requestHeader>"
      +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
     +" <com:sourceSystemId/>"
      +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
      +"<com:sourceServerId>1</com:sourceServerId>"
      +"<com:trackingId>1</com:trackingId>"
  +" </com:requestHeader>"
  +"</soapenv:Header>"
  +"<soapenv:Body>"
   +"<com:PostShipment>"
      +"<com:Request>"
         +"<com:OrderID>"+Orderid+"</com:OrderID>"
         +"<com:Shipments>"
            +"<com:Shipment>"
               +"<com:Carrier>UPS</com:Carrier>"
              +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
               +"<com:Method>Overnight</com:Method>"
               +"<com:Packages>"
                  +"<com:Package>"
                    +" <com:Cost>25.34</com:Cost>"
                    +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
                    +" <com:ReturnTrackingNumber/>"
                    +" <com:Weight>2.35</com:Weight>"
                  +"</com:Package>"
              +" </com:Packages>"
                  
              //for dct mpeg4
              +"<com:ProductsShipped>"
              +"<com:ProductShipped>"
                 +"<com:ProductID>"+groupid+"</com:ProductID>"
                 +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
                +" <com:DevicesShipped>"
                   +" <com:DeviceShipped>"
                      +" <com:Name/>"
                      +" <com:Make>PA</com:Make>"
                      +" <com:Model>PX032ANI</com:Model>"
                       +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                     +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
                      
                       
                
                       +"<com:MasterSubsidiaryLock/>"
                      +" <com:USBMACAddress/>"
                     
                       +"<com:Condition>New</com:Condition>"
                       +"<com:DeviceType>STB</com:DeviceType>"
                   
                     
                     
                       
                       +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                    +"</com:DeviceShipped>"
                 +"</com:DevicesShipped>"
              +"</com:ProductShipped>"
                     
              
/*+" <com:ProductShipped>"
+"<com:ProductID>"+(groupid+1)+"</com:ProductID>"
+" <com:FulfillmentCode>"+fulfillmentcode2+"</com:FulfillmentCode>"
+"<com:DevicesShipped>"
   +"<com:DeviceShipped>"
      +"<com:Name/>"
      +"<com:Make/>"
      +"<com:Model/>"
     +" <com:SerialNumber/>"
      +"<com:SerialNumber2/>"
     +" <com:MACAddress/>"
     +" <com:CMACAddress/>"
      +"<com:EMACAddress/>"
     +" <com:MTAMACAddress/>"
     +" <com:MasterSubsidiaryLock/>"
     +" <com:USBMACAddress/>"
      +"<com:UnitID/>"
      +"<com:UnitID2/>"
     +" <com:Condition>New</com:Condition>"
     +" <com:DeviceType/>"
      +"<com:MACAddress2/>"
     +" <com:Make2/>"
      +"<com:Model2/>"
      +"<com:RFMACAddress/>"
     +" <com:DeviceID>"+(deviceid+1)+"</com:DeviceID>"
  +" </com:DeviceShipped>"
+" </com:DevicesShipped>"
+" </com:ProductShipped>"
+"<com:ProductShipped>"
+" <com:ProductID>"+(groupid+2)+"</com:ProductID>"
+"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
+" <com:DevicesShipped>"
   +"<com:DeviceShipped>"
     +" <com:Name/>"
      +"<com:Make/>"
      +"<com:Model/>"
      +"<com:SerialNumber/>"
      +"<com:SerialNumber2/>"
      +"<com:MACAddress/>"
      +"<com:CMACAddress/>"
      +"<com:EMACAddress/>"
      +"<com:MTAMACAddress/>"
      +"<com:MasterSubsidiaryLock/>"
      +"<com:USBMACAddress/>"
      +"<com:UnitID/>"
     +" <com:UnitID2/>"
      +"<com:Condition>New</com:Condition>"
      +"<com:DeviceType/>"
      +"<com:MACAddress2/>"
      +"<com:Make2/>"
     +" <com:Model2/>"
      +"<com:RFMACAddress/>"
      +"<com:DeviceID>"+(deviceid1+2)+"</com:DeviceID>"
+"   </com:DeviceShipped>"
+" </com:DevicesShipped>"
+"</com:ProductShipped>"*/
                               
               +"</com:ProductsShipped>"
           +" </com:Shipment>"
         +"</com:Shipments>"
      +"</com:Request>"
   +"</com:PostShipment>"
+" </soapenv:Body>"
+"</soapenv:Envelope>";
      
       report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);
       report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
       report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);        
       report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);        


}

 /*System.out.println("test3");
 if(fulfillmentcode==("VID-Xi3")&&fulfillmentcode1==null&&fulfillmentcode2!=null)
 {
	 System.out.println("test3");
 	Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

 	Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
 			+"<soapenv:Header>"
 			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
 			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
 			+"<wsse:Username>Contec</wsse:Username>"
 			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
        +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
         +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
      +"</wsse:UsernameToken>"
  +" </wsse:Security>"
   +"<com:requestHeader>"
      +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
     +" <com:sourceSystemId/>"
      +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
      +"<com:sourceServerId>1</com:sourceServerId>"
      +"<com:trackingId>1</com:trackingId>"
  +" </com:requestHeader>"
  +"</soapenv:Header>"
  +"<soapenv:Body>"
   +"<com:PostShipment>"
      +"<com:Request>"
         +"<com:OrderID>"+Orderid+"</com:OrderID>"
         +"<com:Shipments>"
            +"<com:Shipment>"
               +"<com:Carrier>UPS</com:Carrier>"
              +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
               +"<com:Method>Overnight</com:Method>"
               +"<com:Packages>"
                  +"<com:Package>"
                    +" <com:Cost>25.34</com:Cost>"
                    +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
                    +" <com:ReturnTrackingNumber/>"
                    +" <com:Weight>2.35</com:Weight>"
                  +"</com:Package>"
              +" </com:Packages>"
                  
              //for dct mpeg4
              +"<com:ProductsShipped>"
              +"<com:ProductShipped>"
                 +"<com:ProductID>"+groupid+"</com:ProductID>"
                 +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
                +" <com:DevicesShipped>"
                   +" <com:DeviceShipped>"
                      +" <com:Name/>"
                      +" <com:Make>PA</com:Make>"
                      +" <com:Model>PX032ANI</com:Model>"
                       +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                     +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
                      
                       
                
                       +"<com:MasterSubsidiaryLock/>"
                      +" <com:USBMACAddress/>"
                     
                       +"<com:Condition>New</com:Condition>"
                       +"<com:DeviceType>STB</com:DeviceType>"
                   
                     
                     
                       
                       +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                    +"</com:DeviceShipped>"
                 +"</com:DevicesShipped>"
              +"</com:ProductShipped>"
                     
              
+" <com:ProductShipped>"
+"<com:ProductID>"+groupid2+"</com:ProductID>"
+" <com:FulfillmentCode>"+fulfillmentcode2+"</com:FulfillmentCode>"
+"<com:DevicesShipped>"
   +"<com:DeviceShipped>"
      +"<com:Name/>"
      +"<com:Make/>"
      +"<com:Model/>"
     +" <com:SerialNumber/>"
      +"<com:SerialNumber2/>"
     +" <com:MACAddress/>"
     +" <com:CMACAddress/>"
      +"<com:EMACAddress/>"
     +" <com:MTAMACAddress/>"
     +" <com:MasterSubsidiaryLock/>"
     +" <com:USBMACAddress/>"
      +"<com:UnitID/>"
      +"<com:UnitID2/>"
     +" <com:Condition>New</com:Condition>"
     +" <com:DeviceType/>"
      +"<com:MACAddress2/>"
     +" <com:Make2/>"
      +"<com:Model2/>"
      +"<com:RFMACAddress/>"
     +" <com:DeviceID>"+deviceid2+"</com:DeviceID>"
  +" </com:DeviceShipped>"
+" </com:DevicesShipped>"
+" </com:ProductShipped>"
+"<com:ProductShipped>"
+" <com:ProductID>"+groupid3+"</com:ProductID>"
+"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
+" <com:DevicesShipped>"
   +"<com:DeviceShipped>"
     +" <com:Name/>"
      +"<com:Make/>"
      +"<com:Model/>"
      +"<com:SerialNumber/>"
      +"<com:SerialNumber2/>"
      +"<com:MACAddress/>"
      +"<com:CMACAddress/>"
      +"<com:EMACAddress/>"
      +"<com:MTAMACAddress/>"
      +"<com:MasterSubsidiaryLock/>"
      +"<com:USBMACAddress/>"
      +"<com:UnitID/>"
     +" <com:UnitID2/>"
      +"<com:Condition>New</com:Condition>"
      +"<com:DeviceType/>"
      +"<com:MACAddress2/>"
      +"<com:Make2/>"
     +" <com:Model2/>"
      +"<com:RFMACAddress/>"
      +"<com:DeviceID>"+deviceid3+"</com:DeviceID>"
+"   </com:DeviceShipped>"
+" </com:DevicesShipped>"
+"</com:ProductShipped>"
 	                         
               +"</com:ProductsShipped>"
           +" </com:Shipment>"
         +"</com:Shipments>"
      +"</com:Request>"
   +"</com:PostShipment>"
 +" </soapenv:Body>"
 +"</soapenv:Envelope>";
 	
 	report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);
 	 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
 	 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);
 	 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);
 	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
 	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
 	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
 	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	


 }
   */
/*if(fulfillmentcode.equals("VID-Xi3")&&fulfillmentcode1!=null&&fulfillmentcode2!="RMA-EasyReturns"&&fulfillmentcode3==null)
{
System.out.println("test4");
	Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
     +"</wsse:UsernameToken>"
 +" </wsse:Security>"
  +"<com:requestHeader>"
     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
    +" <com:sourceSystemId/>"
     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
     +"<com:sourceServerId>1</com:sourceServerId>"
     +"<com:trackingId>1</com:trackingId>"
 +" </com:requestHeader>"
 +"</soapenv:Header>"
 +"<soapenv:Body>"
  +"<com:PostShipment>"
     +"<com:Request>"
        +"<com:OrderID>"+Orderid+"</com:OrderID>"
        +"<com:Shipments>"
           +"<com:Shipment>"
              +"<com:Carrier>UPS</com:Carrier>"
             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
              +"<com:Method>Overnight</com:Method>"
              +"<com:Packages>"
                 +"<com:Package>"
                   +" <com:Cost>25.34</com:Cost>"
                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
                   +" <com:ReturnTrackingNumber/>"
                   +" <com:Weight>2.35</com:Weight>"
                 +"</com:Package>"
             +" </com:Packages>"
                 
             //for dct mpeg4
             +"<com:ProductsShipped>"
             +"<com:ProductShipped>"
                +"<com:ProductID>"+groupid+"</com:ProductID>"
                +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
               +" <com:DevicesShipped>"
                  +" <com:DeviceShipped>"
                     +" <com:Name/>"
                     +" <com:Make>PA</com:Make>"
                     +" <com:Model>PX032ANI</com:Model>"
                      +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                    +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
                     
                      
               
                      +"<com:MasterSubsidiaryLock/>"
                     +" <com:USBMACAddress/>"
                    
                      +"<com:Condition>New</com:Condition>"
                      +"<com:DeviceType>STB</com:DeviceType>"
                  
                    
                    
                      
                      +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                   +"</com:DeviceShipped>"
                +"</com:DevicesShipped>"
             +"</com:ProductShipped>"
                     //for dvr
                     
                    
	                    +" <com:ProductShipped>"
	                       +" <com:ProductID>"+groupid1+"</com:ProductID>"
	                	                        
	                        +"<com:FulfillmentCode>VID-X1SWap-MX011ANM</com:FulfillmentCode>"
	                        +"<com:DevicesShipped>"
	                          +" <com:DeviceShipped>"
	                            +"  <com:Name/>"
	                             +" <com:Make>MOTO</com:Make>"
	                              +"<com:Model>MX011ANM</com:Model>"
	                              +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
	                              +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
	                              +"<com:MACAddress/>"
	                              +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
	                              +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
	                              +"<com:MTAMACAddress/>"
	                              +"<com:MasterSubsidiaryLock/>"
	                              +"<com:USBMACAddress/>"
	                              +"<com:UnitID>"+unit2+"</com:UnitID>"
	                             +" <com:UnitID2/>"
	                             +" <com:Condition>New</com:Condition>"
	                             +" <com:DeviceType>STB</com:DeviceType>"
	                              +"<com:MACAddress2/>"
	                              +"<com:Make2>MOTO</com:Make2>"
	                              +"<com:Model2>MSCC9063</com:Model2>"
	                              +"<com:RFMACAddress/>"
	                              +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
	                          +" </com:DeviceShipped>"
	                        +"</com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                         
              +"</com:ProductsShipped>"
          +" </com:Shipment>"
        +"</com:Shipments>"
     +"</com:Request>"
  +"</com:PostShipment>"
+" </soapenv:Body>"
+"</soapenv:Envelope>";
	
	report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);
	 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
	 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);
	 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);
	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	


}*/
/* System.out.println("test5");
	if(fulfillmentcode1.equals("VID-X1SWap-MX011ANM")&&fulfillmentcode.equals("VID-Xi3")&&fulfillmentcode3!=null)
	{
		System.out.println("test5");
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	     +"</wsse:UsernameToken>"
	 +" </wsse:Security>"
	  +"<com:requestHeader>"
	     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	    +" <com:sourceSystemId/>"
	     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	     +"<com:sourceServerId>1</com:sourceServerId>"
	     +"<com:trackingId>1</com:trackingId>"
	 +" </com:requestHeader>"
	 +"</soapenv:Header>"
	 +"<soapenv:Body>"
	  +"<com:PostShipment>"
	     +"<com:Request>"
	        +"<com:OrderID>"+Orderid+"</com:OrderID>"
	        +"<com:Shipments>"
	           +"<com:Shipment>"
	              +"<com:Carrier>UPS</com:Carrier>"
	             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	              +"<com:Method>Overnight</com:Method>"
	              +"<com:Packages>"
	                 +"<com:Package>"
	                   +" <com:Cost>25.34</com:Cost>"
	                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	                   +" <com:ReturnTrackingNumber/>"
	                   +" <com:Weight>2.35</com:Weight>"
	                 +"</com:Package>"
	             +" </com:Packages>"
	                 
	             //for dct mpeg4
	             +"<com:ProductsShipped>"
	             +"<com:ProductShipped>"
	                +"<com:ProductID>"+groupid+"</com:ProductID>"
	                +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
	               +" <com:DevicesShipped>"
	                  +" <com:DeviceShipped>"
	                     +" <com:Name/>"
	                     +" <com:Make>PA</com:Make>"
	                     +" <com:Model>PX032ANI</com:Model>"
	                      +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
	                    +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
	                     
	                      
	               
	                      +"<com:MasterSubsidiaryLock/>"
	                     +" <com:USBMACAddress/>"
	                    
	                      +"<com:Condition>New</com:Condition>"
	                      +"<com:DeviceType>STB</com:DeviceType>"
	                  
	                    
	                    
	                      
	                      +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
	                   +"</com:DeviceShipped>"
	                +"</com:DevicesShipped>"
	             +"</com:ProductShipped>"
	                     //for dvr
	                     
	                    
		                    +" <com:ProductShipped>"
		                       +" <com:ProductID>"+groupid1+"</com:ProductID>"
		                	                        
		                        +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
		                        +"<com:DevicesShipped>"
		                          +" <com:DeviceShipped>"
		                            +"  <com:Name/>"
		                             +" <com:Make>MOTO</com:Make>"
		                              +"<com:Model>MX011ANM</com:Model>"
		                              +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
		                              +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
		                              +"<com:MACAddress/>"
		                              +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
		                              +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
		                              +"<com:MTAMACAddress/>"
		                              +"<com:MasterSubsidiaryLock/>"
		                              +"<com:USBMACAddress/>"
		                              +"<com:UnitID>"+unit2+"</com:UnitID>"
		                             +" <com:UnitID2/>"
		                             +" <com:Condition>New</com:Condition>"
		                             +" <com:DeviceType>STB</com:DeviceType>"
		                              +"<com:MACAddress2/>"
		                              +"<com:Make2>MOTO</com:Make2>"
		                              +"<com:Model2>MSCC9063</com:Model2>"
		                              +"<com:RFMACAddress/>"
		                              +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
		                          +" </com:DeviceShipped>"
		                        +"</com:DevicesShipped>"
		                         +"</com:ProductShipped>"
		                        
		                   //rma      
		                         +"<com:ProductShipped>"
		                         +" <com:ProductID>"+(groupid3)+"</com:ProductID>"
		                          +"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
		                         +" <com:DevicesShipped>"
		                             +"<com:DeviceShipped>"
		                               +" <com:Name/>"
		                                +"<com:Make/>"
		                                +"<com:Model/>"
		                                +"<com:SerialNumber/>"
		                                +"<com:SerialNumber2/>"
		                                +"<com:MACAddress/>"
		                                +"<com:CMACAddress/>"
		                                +"<com:EMACAddress/>"
		                                +"<com:MTAMACAddress/>"
		                                +"<com:MasterSubsidiaryLock/>"
		                                +"<com:USBMACAddress/>"
		                                +"<com:UnitID/>"
		                               +" <com:UnitID2/>"
		                                +"<com:Condition>New</com:Condition>"
		                                +"<com:DeviceType/>"
		                                +"<com:MACAddress2/>"
		                                +"<com:Make2/>"
		                               +" <com:Model2/>"
		                                +"<com:RFMACAddress/>"
		                                +"<com:DeviceID>"+(deviceid3)+"</com:DeviceID>"
		                          +"   </com:DeviceShipped>"
		                         +" </com:DevicesShipped>"
		                       +"</com:ProductShipped>"
		                       
		                    +"</com:ProductsShipped>"
		                +" </com:Shipment>"
		              +"</com:Shipments>"
		           +"</com:Request>"
		        +"</com:PostShipment>"
		      +" </soapenv:Body>"
		      +"</soapenv:Envelope>";
		System.out.println("test5");
		
		report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);

		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);

		 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);

		 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);

		 report.updateTestLog("The product id for "+fulfillmentcode3, "Fetched successfully as : "+groupid3, Status.PASS);

		 report.updateTestLog("The device id for "+fulfillmentcode3, "Fetched successfully as : "+deviceid3, Status.PASS);

		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		

		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		

		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
		 
		
	}
        */         
              
	/*System.out.println("test6");
	if("VID-X1SWap-MX011ANM".equals(fulfillmentcode1)&&"VID-Xi3".equals(fulfillmentcode)&&"RMA-EasyReturns".equals(fulfillmentcode3))
	{
		System.out.println("test6");
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	     +"</wsse:UsernameToken>"
	 +" </wsse:Security>"
	  +"<com:requestHeader>"
	     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	    +" <com:sourceSystemId/>"
	     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	     +"<com:sourceServerId>1</com:sourceServerId>"
	     +"<com:trackingId>1</com:trackingId>"
	 +" </com:requestHeader>"
	 +"</soapenv:Header>"
	 +"<soapenv:Body>"
	  +"<com:PostShipment>"
	     +"<com:Request>"
	        +"<com:OrderID>"+Orderid+"</com:OrderID>"
	        +"<com:Shipments>"
	           +"<com:Shipment>"
	              +"<com:Carrier>UPS</com:Carrier>"
	             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	              +"<com:Method>Overnight</com:Method>"
	              +"<com:Packages>"
	                 +"<com:Package>"
	                   +" <com:Cost>25.34</com:Cost>"
	                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	                   +" <com:ReturnTrackingNumber/>"
	                   +" <com:Weight>2.35</com:Weight>"
	                 +"</com:Package>"
	             +" </com:Packages>"
	                 
     +"<com:ProductsShipped>"
	             //for dvr
                 
                 
                 +" <com:ProductShipped>"
                    +" <com:ProductID>"+groupid+"</com:ProductID>"
             	                        
                     +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
                     +"<com:DevicesShipped>"
                       +" <com:DeviceShipped>"
                         +"  <com:Name/>"
                          +" <com:Make>MOTO</com:Make>"
                           +"<com:Model>MX011ANM</com:Model>"
                           +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                           +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
                           +"<com:MACAddress/>"
                           +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
                           +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
                           +"<com:MTAMACAddress/>"
                           +"<com:MasterSubsidiaryLock/>"
                           +"<com:USBMACAddress/>"
                           +"<com:UnitID>"+unit1+"</com:UnitID>"
                          +" <com:UnitID2/>"
                          +" <com:Condition>New</com:Condition>"
                          +" <com:DeviceType>STB</com:DeviceType>"
                           +"<com:MACAddress2/>"
                           +"<com:Make2>MOTO</com:Make2>"
                           +"<com:Model2>MSCC9063</com:Model2>"
                           +"<com:RFMACAddress/>"
                           +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                       +" </com:DeviceShipped>"
                     +"</com:DevicesShipped>"
                      +"</com:ProductShipped>"
                     
	             //for dct mpeg4
	        
	             +"<com:ProductShipped>"
	                +"<com:ProductID>"+groupid1+"</com:ProductID>"
	                +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
	               +" <com:DevicesShipped>"
	                  +" <com:DeviceShipped>"
	                     +" <com:Name/>"
	                     +" <com:Make>PA</com:Make>"
	                     +" <com:Model>PX032ANI</com:Model>"
	                      +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
	                    +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
	                     
	                      
	               
	                      +"<com:MasterSubsidiaryLock/>"
	                     +" <com:USBMACAddress/>"
	                    
	                      +"<com:Condition>New</com:Condition>"
	                      +"<com:DeviceType>STB</com:DeviceType>"
	                  
	                    
	                    
	                      
	                      +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
	                   +"</com:DeviceShipped>"
	                +"</com:DevicesShipped>"
	             +"</com:ProductShipped>"
	                    
		                 
		                       
		                    +"</com:ProductsShipped>"
		                +" </com:Shipment>"
		              +"</com:Shipments>"
		           +"</com:Request>"
		        +"</com:PostShipment>"
		      +" </soapenv:Body>"
		      +"</soapenv:Envelope>";
		System.out.println("test5");
		
		report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);

		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);

		 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);

		 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);

		
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		

		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		

		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
		 
		
	}
                 */
	
	//for x1dvrdctdct
	
	if("VID-Xi3".equals(fulfillmentcode1)&&"VID-Xi3".equals(fulfillmentcode)&&"VID-X1SWap-MX011ANM".equals(fulfillmentcode2)&&"RMA-EasyReturns".equals(fulfillmentcode3))
	{
		System.out.println("testnew");
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	     +"</wsse:UsernameToken>"
	 +" </wsse:Security>"
	  +"<com:requestHeader>"
	     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	    +" <com:sourceSystemId/>"
	     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	     +"<com:sourceServerId>1</com:sourceServerId>"
	     +"<com:trackingId>1</com:trackingId>"
	 +" </com:requestHeader>"
	 +"</soapenv:Header>"
	 +"<soapenv:Body>"
	  +"<com:PostShipment>"
	     +"<com:Request>"
	        +"<com:OrderID>"+Orderid+"</com:OrderID>"
	        +"<com:Shipments>"
	           +"<com:Shipment>"
	              +"<com:Carrier>UPS</com:Carrier>"
	             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	              +"<com:Method>Overnight</com:Method>"
	              +"<com:Packages>"
	                 +"<com:Package>"
	                   +" <com:Cost>25.34</com:Cost>"
	                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	                   +" <com:ReturnTrackingNumber/>"
	                   +" <com:Weight>2.35</com:Weight>"
	                 +"</com:Package>"
	             +" </com:Packages>"
	                 
     +"<com:ProductsShipped>"
     
     //for dct mpeg4
     
     +"<com:ProductShipped>"
        +"<com:ProductID>"+groupid+"</com:ProductID>"
        +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
       +" <com:DevicesShipped>"
          +" <com:DeviceShipped>"
             +" <com:Name/>"
             +" <com:Make>PA</com:Make>"
             +" <com:Model>PX032ANI</com:Model>"
              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
            +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
             
              
       
              +"<com:MasterSubsidiaryLock/>"
             +" <com:USBMACAddress/>"
            
              +"<com:Condition>New</com:Condition>"
              +"<com:DeviceType>STB</com:DeviceType>"
          
            
            
              
              +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
           +"</com:DeviceShipped>"
        +"</com:DevicesShipped>"
     +"</com:ProductShipped>"
            
     
+"<com:ProductShipped>"
+"<com:ProductID>"+groupid1+"</com:ProductID>"
+"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
+" <com:DevicesShipped>"
+" <com:DeviceShipped>"
+" <com:Name/>"
+" <com:Make>PA</com:Make>"
+" <com:Model>PX032ANI</com:Model>"
+"<com:SerialNumber>"+device2+"</com:SerialNumber>"
+"<com:RFMACAddress>"+rfmac2+"</com:RFMACAddress>"

+"<com:MasterSubsidiaryLock/>"
+" <com:USBMACAddress/>"

+"<com:Condition>New</com:Condition>"
+"<com:DeviceType>STB</com:DeviceType>"

+"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
+"</com:DeviceShipped>"
+"</com:DevicesShipped>"
+"</com:ProductShipped>"
             

	             //for dvr
                 
                 
                 +" <com:ProductShipped>"
                    +" <com:ProductID>"+groupid2+"</com:ProductID>"
             	                        
                     +"<com:FulfillmentCode>"+fulfillmentcode2+"</com:FulfillmentCode>"
                     +"<com:DevicesShipped>"
                       +" <com:DeviceShipped>"
                         +"  <com:Name/>"
                          +" <com:Make>MOTO</com:Make>"
                           +"<com:Model>MX011ANM</com:Model>"
                           +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
                           +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
                           +"<com:MACAddress/>"
                           +"<com:CMACAddress>"+cmmac2+"</com:CMACAddress>"
                           +"<com:EMACAddress>"+mtamac2+"</com:EMACAddress>"
                           +"<com:MTAMACAddress/>"
                           +"<com:MasterSubsidiaryLock/>"
                           +"<com:USBMACAddress/>"
                           +"<com:UnitID>"+unit2+"</com:UnitID>"
                          +" <com:UnitID2/>"
                          +" <com:Condition>New</com:Condition>"
                          +" <com:DeviceType>STB</com:DeviceType>"
                           +"<com:MACAddress2/>"
                           +"<com:Make2>MOTO</com:Make2>"
                           +"<com:Model2>MSCC9063</com:Model2>"
                           +"<com:RFMACAddress/>"
                           +"<com:DeviceID>"+deviceid2+"</com:DeviceID>"
                       +" </com:DeviceShipped>"
                     +"</com:DevicesShipped>"
                      +"</com:ProductShipped>"
                     
	            
 +" <com:ProductShipped>"
 +"<com:ProductID>"+(groupid3)+"</com:ProductID>"
+" <com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
 +"<com:DevicesShipped>"
    +"<com:DeviceShipped>"
       +"<com:Name/>"
       +"<com:Make/>"
       +"<com:Model/>"
      +" <com:SerialNumber/>"
       +"<com:SerialNumber2/>"
      +" <com:MACAddress/>"
      +" <com:CMACAddress/>"
       +"<com:EMACAddress/>"
      +" <com:MTAMACAddress/>"
      +" <com:MasterSubsidiaryLock/>"
      +" <com:USBMACAddress/>"
       +"<com:UnitID/>"
       +"<com:UnitID2/>"
      +" <com:Condition>New</com:Condition>"
      +" <com:DeviceType/>"
       +"<com:MACAddress2/>"
      +" <com:Make2/>"
       +"<com:Model2/>"
       +"<com:RFMACAddress/>"
      +" <com:DeviceID>"+(deviceid3)+"</com:DeviceID>"
   +" </com:DeviceShipped>"
+" </com:DevicesShipped>"
+" </com:ProductShipped>"
		                       
		                    +"</com:ProductsShipped>"
		                +" </com:Shipment>"
		              +"</com:Shipments>"
		           +"</com:Request>"
		        +"</com:PostShipment>"
		      +" </soapenv:Body>"
		      +"</soapenv:Envelope>";
				
		report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);

		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
		 
		 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid1, Status.PASS);

		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid1, Status.PASS);

		 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid2, Status.PASS);

		 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid2, Status.PASS);

		
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		

		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		

		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
		 
		
	}
	
	

if("VID-DCX3200-MPEG4".equals(fulfillmentcode)||"VID-RNG110RF-MPEG4".equals(fulfillmentcode)&&fulfillmentcode3.isEmpty()&&fulfillmentcode2.isEmpty())
{
	System.out.println("test6");
	Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

	Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
			+"<soapenv:Header>"
			+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
			+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
			+"<wsse:Username>Contec</wsse:Username>"
			+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
     +"</wsse:UsernameToken>"
 +" </wsse:Security>"
  +"<com:requestHeader>"
     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
    +" <com:sourceSystemId/>"
     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
     +"<com:sourceServerId>1</com:sourceServerId>"
     +"<com:trackingId>1</com:trackingId>"
 +" </com:requestHeader>"
 +"</soapenv:Header>"
 +"<soapenv:Body>"
  +"<com:PostShipment>"
     +"<com:Request>"
        +"<com:OrderID>"+Orderid+"</com:OrderID>"
        +"<com:Shipments>"
           +"<com:Shipment>"
              +"<com:Carrier>UPS</com:Carrier>"
             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
              +"<com:Method>Overnight</com:Method>"
              +"<com:Packages>"
                 +"<com:Package>"
                   +" <com:Cost>25.34</com:Cost>"
                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
                   +" <com:ReturnTrackingNumber/>"
                   +" <com:Weight>2.35</com:Weight>"
                 +"</com:Package>"
             +" </com:Packages>"
                 
             //for dct mpeg4
              +"<com:ProductsShipped>"
                +" <com:ProductShipped>"
                   +" <com:ProductID>"+groupid+"</com:ProductID>"
            	                        
                    +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
                    +"<com:DevicesShipped>"
                      +" <com:DeviceShipped>"
                        +"  <com:Name/>"
                         +" <com:Make>MOTO</com:Make>"
                          +"<com:Model>DCX3200</com:Model>"
                          +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                          +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
                          +"<com:MACAddress/>"
                          +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
                          +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
                          +"<com:MTAMACAddress/>"
                          +"<com:MasterSubsidiaryLock/>"
                          +"<com:USBMACAddress/>"
                          +"<com:UnitID>"+unit1+"</com:UnitID>"
                         +" <com:UnitID2/>"
                         +" <com:Condition>New</com:Condition>"
                         +" <com:DeviceType>STB</com:DeviceType>"
                          +"<com:MACAddress2/>"
                          +"<com:Make2>MOTO</com:Make2>"
                          +"<com:Model2>MSCC9062</com:Model2>"
                          +"<com:RFMACAddress/>"
                          +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                      +" </com:DeviceShipped>"
                    +"</com:DevicesShipped>"
                     +"</com:ProductShipped>"
                 
                     //for dvr
                     
                    
	                    +" <com:ProductShipped>"
	                       +" <com:ProductID>"+groupid1+"</com:ProductID>"
	                	                        
	                        +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
	                        +"<com:DevicesShipped>"
	                          +" <com:DeviceShipped>"
	                            +"  <com:Name/>"
	                             +" <com:Make>MOTO</com:Make>"
	                              +"<com:Model>DCX3400</com:Model>"
	                              +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
	                              +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
	                              +"<com:MACAddress/>"
	                              +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
	                              +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
	                              +"<com:MTAMACAddress/>"
	                              +"<com:MasterSubsidiaryLock/>"
	                              +"<com:USBMACAddress/>"
	                              +"<com:UnitID>"+unit2+"</com:UnitID>"
	                             +" <com:UnitID2/>"
	                             +" <com:Condition>New</com:Condition>"
	                             +" <com:DeviceType>STB</com:DeviceType>"
	                              +"<com:MACAddress2/>"
	                              +"<com:Make2>MOTO</com:Make2>"
	                              +"<com:Model2>MSCC9062</com:Model2>"
	                              +"<com:RFMACAddress/>"
	                              +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
	                          +" </com:DeviceShipped>"
	                        +"</com:DevicesShipped>"
	                         +"</com:ProductShipped>"
	                     
               
              +"</com:ProductsShipped>"
          +" </com:Shipment>"
        +"</com:Shipments>"
     +"</com:Request>"
  +"</com:PostShipment>"
+" </soapenv:Body>"
+"</soapenv:Envelope>";
	
	report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);
	 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
	 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);
	 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);
	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
	 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
	 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
}
	
//mpeg4 dvrdctdct
if(("VID-RNG110RF-MPEG4".equals(fulfillmentcode1)||"VID-MOTRNG150N-MPEG4".equals(fulfillmentcode1))&&("VID-MOTRNG200BN-MPEG4".equals(fulfillmentcode)||"VID-MOTRNG200BN-MPEG4".equals(fulfillmentcode))&&("VID-RNG110RF-MPEG4".equals(fulfillmentcode2)||"VID-MOTRNG150N-MPEG4".equals(fulfillmentcode2))&&"RMA-DF-LABEL-01".equals(fulfillmentcode5)&&"RMA-EasyReturns".equals(fulfillmentcode6))
	{
		System.out.println("test7");
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	     +"</wsse:UsernameToken>"
	 +" </wsse:Security>"
	  +"<com:requestHeader>"
	     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	    +" <com:sourceSystemId/>"
	     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	     +"<com:sourceServerId>1</com:sourceServerId>"
	     +"<com:trackingId>1</com:trackingId>"
	 +" </com:requestHeader>"
	 +"</soapenv:Header>"
	 +"<soapenv:Body>"
	  +"<com:PostShipment>"
	     +"<com:Request>"
	        +"<com:OrderID>"+Orderid+"</com:OrderID>"
	        +"<com:Shipments>"
	           +"<com:Shipment>"
	              +"<com:Carrier>UPS</com:Carrier>"
	             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	              +"<com:Method>Overnight</com:Method>"
	              +"<com:Packages>"
	                 +"<com:Package>"
	                   +" <com:Cost>25.34</com:Cost>"
	                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	                   +" <com:ReturnTrackingNumber/>"
	                   +" <com:Weight>2.35</com:Weight>"
	                 +"</com:Package>"
	             +" </com:Packages>"
	                 
	             //for dvr
                 
	             +"<com:ProductsShipped>"
                 +" <com:ProductShipped>"
                    +" <com:ProductID>"+groupid+"</com:ProductID>"
             	                        
                     +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
                     +"<com:DevicesShipped>"
                       +" <com:DeviceShipped>"
                         +"  <com:Name/>"
                          +" <com:Make>MOTO</com:Make>"
                           +"<com:Model>DCX3400</com:Model>"
                           +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
                           +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
                           +"<com:MACAddress/>"
                           +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
                           +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
                           +"<com:MTAMACAddress/>"
                           +"<com:MasterSubsidiaryLock/>"
                           +"<com:USBMACAddress/>"
                           +"<com:UnitID>"+unit1+"</com:UnitID>"
                          +" <com:UnitID2/>"
                          +" <com:Condition>New</com:Condition>"
                          +" <com:DeviceType>STB</com:DeviceType>"
                           +"<com:MACAddress2/>"
                           +"<com:Make2>MOTO</com:Make2>"
                           +"<com:Model2>MSCC9062</com:Model2>"
                           +"<com:RFMACAddress/>"
                           +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
                       +" </com:DeviceShipped>"
                     +"</com:DevicesShipped>"
                      +"</com:ProductShipped>"
                  
             
	                 
	             //for dct mpeg4
	             
	                +" <com:ProductShipped>"
	                   +" <com:ProductID>"+groupid1+"</com:ProductID>"
	            	                        
	                    +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
	                    +"<com:DevicesShipped>"
	                      +" <com:DeviceShipped>"
	                        +"  <com:Name/>"
	                         +" <com:Make>MOTO</com:Make>"
	                          +"<com:Model>DCX3200</com:Model>"
	                          +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
	                          +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
	                          +"<com:MACAddress/>"
	                          +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
	                          +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
	                          +"<com:MTAMACAddress/>"
	                          +"<com:MasterSubsidiaryLock/>"
	                          +"<com:USBMACAddress/>"
	                          +"<com:UnitID>"+unit2+"</com:UnitID>"
	                         +" <com:UnitID2/>"
	                         +" <com:Condition>New</com:Condition>"
	                         +" <com:DeviceType>STB</com:DeviceType>"
	                          +"<com:MACAddress2/>"
	                          +"<com:Make2>MOTO</com:Make2>"
	                          +"<com:Model2>MSCC9062</com:Model2>"
	                          +"<com:RFMACAddress/>"
	                          +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
	                      +" </com:DeviceShipped>"
	                    +"</com:DevicesShipped>"
	                     +"</com:ProductShipped>"
	                 
	                     //for dct mpeg4
	   	           
	   	                +" <com:ProductShipped>"
	   	                   +" <com:ProductID>"+groupid2+"</com:ProductID>"
	   	            	                        
	   	                    +"<com:FulfillmentCode>"+fulfillmentcode2+"</com:FulfillmentCode>"
	   	                    +"<com:DevicesShipped>"
	   	                      +" <com:DeviceShipped>"
	   	                        +"  <com:Name/>"
	   	                         +" <com:Make>MOTO</com:Make>"
	   	                          +"<com:Model>DCX3200</com:Model>"
	   	                          +"<com:SerialNumber>"+device5+"</com:SerialNumber>"
	   	                          +"<com:SerialNumber2>"+device6+"</com:SerialNumber2>"
	   	                          +"<com:MACAddress/>"
	   	                          +"<com:CMACAddress>"+cmmac2+"</com:CMACAddress>"
	   	                          +"<com:EMACAddress>"+mtamac2+"</com:EMACAddress>"
	   	                          +"<com:MTAMACAddress/>"
	   	                          +"<com:MasterSubsidiaryLock/>"
	   	                          +"<com:USBMACAddress/>"
	   	                          +"<com:UnitID>"+unit3+"</com:UnitID>"
	   	                         +" <com:UnitID2/>"
	   	                         +" <com:Condition>New</com:Condition>"
	   	                         +" <com:DeviceType>STB</com:DeviceType>"
	   	                          +"<com:MACAddress2/>"
	   	                          +"<com:Make2>MOTO</com:Make2>"
	   	                          +"<com:Model2>MSCC9062</com:Model2>"
	   	                          +"<com:RFMACAddress/>"
	   	                          +"<com:DeviceID>"+deviceid2+"</com:DeviceID>"
	   	                      +" </com:DeviceShipped>"
	   	                    +"</com:DevicesShipped>"
	   	                     +"</com:ProductShipped>"
	   	                 
	   	                     
	                 
	                 
	                 
	                 //for rma drop downs
	                +" <com:ProductShipped>"
	                    +"<com:ProductID>"+(groupid5)+"</com:ProductID>"
	                   +" <com:FulfillmentCode>"+fulfillmentcode5+"</com:FulfillmentCode>"
	                    +"<com:DevicesShipped>"
	                       +"<com:DeviceShipped>"
	                          +"<com:Name/>"
	                          +"<com:Make/>"
	                          +"<com:Model/>"
	                         +" <com:SerialNumber/>"
	                          +"<com:SerialNumber2/>"
	                         +" <com:MACAddress/>"
	                         +" <com:CMACAddress/>"
	                          +"<com:EMACAddress/>"
	                         +" <com:MTAMACAddress/>"
	                         +" <com:MasterSubsidiaryLock/>"
	                         +" <com:USBMACAddress/>"
	                          +"<com:UnitID/>"
	                          +"<com:UnitID2/>"
	                         +" <com:Condition>New</com:Condition>"
	                         +" <com:DeviceType/>"
	                          +"<com:MACAddress2/>"
	                         +" <com:Make2/>"
	                          +"<com:Model2/>"
	                          +"<com:RFMACAddress/>"
	                         +" <com:DeviceID>"+(deviceid5)+"</com:DeviceID>"
	                      +" </com:DeviceShipped>"
	                   +" </com:DevicesShipped>"
	                +" </com:ProductShipped>"
	                 +"<com:ProductShipped>"
	                   +" <com:ProductID>"+(groupid6)+"</com:ProductID>"
	                    +"<com:FulfillmentCode>"+fulfillmentcode6+"</com:FulfillmentCode>"
	                   +" <com:DevicesShipped>"
	                       +"<com:DeviceShipped>"
	                         +" <com:Name/>"
	                          +"<com:Make/>"
	                          +"<com:Model/>"
	                          +"<com:SerialNumber/>"
	                          +"<com:SerialNumber2/>"
	                          +"<com:MACAddress/>"
	                          +"<com:CMACAddress/>"
	                          +"<com:EMACAddress/>"
	                          +"<com:MTAMACAddress/>"
	                          +"<com:MasterSubsidiaryLock/>"
	                          +"<com:USBMACAddress/>"
	                          +"<com:UnitID/>"
	                         +" <com:UnitID2/>"
	                          +"<com:Condition>New</com:Condition>"
	                          +"<com:DeviceType/>"
	                          +"<com:MACAddress2/>"
	                          +"<com:Make2/>"
	                         +" <com:Model2/>"
	                          +"<com:RFMACAddress/>"
	                          +"<com:DeviceID>"+(deviceid6)+"</com:DeviceID>"
	                    +"   </com:DeviceShipped>"
	                   +" </com:DevicesShipped>"
	                 +"</com:ProductShipped>"
	              +"</com:ProductsShipped>"
	          +" </com:Shipment>"
	        +"</com:Shipments>"
	     +"</com:Request>"
	  +"</com:PostShipment>"
	+" </soapenv:Body>"
	+"</soapenv:Envelope>";
		
		report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid1, Status.PASS);

		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid1, Status.PASS);

		 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid2, Status.PASS);

		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid2, Status.PASS);

		 
		 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid, Status.PASS);

		 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid, Status.PASS);

		 report.updateTestLog("The product id for "+fulfillmentcode5, "Fetched successfully as : "+groupid5, Status.PASS);

		 report.updateTestLog("The device id for "+fulfillmentcode5, "Fetched successfully as : "+deviceid5, Status.PASS);

		 report.updateTestLog("The product id for "+fulfillmentcode6, "Fetched successfully as : "+groupid6, Status.PASS);

		 report.updateTestLog("The device id for "+fulfillmentcode6, "Fetched successfully as : "+deviceid6, Status.PASS);

		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device3, Status.PASS);		

		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device4, Status.PASS);		
		 
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device5, Status.PASS);		

		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device6, Status.PASS);		


		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device1, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device2, Status.PASS);	
		 
		
	}
        
	
	
	if(("VID-DCX3400-MPEG4".equals(fulfillmentcode)||"VID-MOTRNG200BN-MPEG4".equals(fulfillmentcode))&&fulfillmentcode3.isEmpty()&&fulfillmentcode2.isEmpty())
	{
		System.out.println("test8");
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	     +"</wsse:UsernameToken>"
	 +" </wsse:Security>"
	  +"<com:requestHeader>"
	     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	    +" <com:sourceSystemId/>"
	     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	     +"<com:sourceServerId>1</com:sourceServerId>"
	     +"<com:trackingId>1</com:trackingId>"
	 +" </com:requestHeader>"
	 +"</soapenv:Header>"
	 +"<soapenv:Body>"
	  +"<com:PostShipment>"
	     +"<com:Request>"
	        +"<com:OrderID>"+Orderid+"</com:OrderID>"
	        +"<com:Shipments>"
	           +"<com:Shipment>"
	              +"<com:Carrier>UPS</com:Carrier>"
	             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	              +"<com:Method>Overnight</com:Method>"
	              +"<com:Packages>"
	                 +"<com:Package>"
	                   +" <com:Cost>25.34</com:Cost>"
	                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	                   +" <com:ReturnTrackingNumber/>"
	                   +" <com:Weight>2.35</com:Weight>"
	                 +"</com:Package>"
	             +" </com:Packages>"
	                 
	            
	                     //for dvr
	                     
	                    
		                    +" <com:ProductShipped>"
		                       +" <com:ProductID>"+groupid+"</com:ProductID>"
		                	                        
		                        +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
		                        +"<com:DevicesShipped>"
		                          +" <com:DeviceShipped>"
		                            +"  <com:Name/>"
		                             +" <com:Make>MOTO</com:Make>"
		                              +"<com:Model>DCX3400</com:Model>"
		                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
		                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                              +"<com:MACAddress/>"
		                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                              +"<com:MTAMACAddress/>"
		                              +"<com:MasterSubsidiaryLock/>"
		                              +"<com:USBMACAddress/>"
		                              +"<com:UnitID>"+unit1+"</com:UnitID>"
		                             +" <com:UnitID2/>"
		                             +" <com:Condition>New</com:Condition>"
		                             +" <com:DeviceType>STB</com:DeviceType>"
		                              +"<com:MACAddress2/>"
		                              +"<com:Make2>MOTO</com:Make2>"
		                              +"<com:Model2>MSCC9062</com:Model2>"
		                              +"<com:RFMACAddress/>"
		                              +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                          +" </com:DeviceShipped>"
		                        +"</com:DevicesShipped>"
		                         +"</com:ProductShipped>"
		                     
	                 
	                 
	                 
	                 
	                 //for rma drop downs
	               /* +" <com:ProductShipped>"
	                    +"<com:ProductID>"+prodidne+"</com:ProductID>"
	                   +" <com:FulfillmentCode>RMA-DF-LABEL-01</com:FulfillmentCode>"
	                    +"<com:DevicesShipped>"
	                       +"<com:DeviceShipped>"
	                          +"<com:Name/>"
	                          +"<com:Make/>"
	                          +"<com:Model/>"
	                         +" <com:SerialNumber/>"
	                          +"<com:SerialNumber2/>"
	                         +" <com:MACAddress/>"
	                         +" <com:CMACAddress/>"
	                          +"<com:EMACAddress/>"
	                         +" <com:MTAMACAddress/>"
	                         +" <com:MasterSubsidiaryLock/>"
	                         +" <com:USBMACAddress/>"
	                          +"<com:UnitID/>"
	                          +"<com:UnitID2/>"
	                         +" <com:Condition>New</com:Condition>"
	                         +" <com:DeviceType/>"
	                          +"<com:MACAddress2/>"
	                         +" <com:Make2/>"
	                          +"<com:Model2/>"
	                          +"<com:RFMACAddress/>"
	                         +" <com:DeviceID>"+devicene+"</com:DeviceID>"
	                      +" </com:DeviceShipped>"
	                   +" </com:DevicesShipped>"
	                +" </com:ProductShipped>"
	                 +"<com:ProductShipped>"
	                   +" <com:ProductID>"+prodidnee+"</com:ProductID>"
	                    +"<com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
	                   +" <com:DevicesShipped>"
	                       +"<com:DeviceShipped>"
	                         +" <com:Name/>"
	                          +"<com:Make/>"
	                          +"<com:Model/>"
	                          +"<com:SerialNumber/>"
	                          +"<com:SerialNumber2/>"
	                          +"<com:MACAddress/>"
	                          +"<com:CMACAddress/>"
	                          +"<com:EMACAddress/>"
	                          +"<com:MTAMACAddress/>"
	                          +"<com:MasterSubsidiaryLock/>"
	                          +"<com:USBMACAddress/>"
	                          +"<com:UnitID/>"
	                         +" <com:UnitID2/>"
	                          +"<com:Condition>New</com:Condition>"
	                          +"<com:DeviceType/>"
	                          +"<com:MACAddress2/>"
	                          +"<com:Make2/>"
	                         +" <com:Model2/>"
	                          +"<com:RFMACAddress/>"
	                          +"<com:DeviceID>"+devicenee+"</com:DeviceID>"
	                    +"   </com:DeviceShipped>"
	                   +" </com:DevicesShipped>"
	                 +"</com:ProductShipped>"*/
	              +"</com:ProductsShipped>"
	          +" </com:Shipment>"
	        +"</com:Shipments>"
	     +"</com:Request>"
	  +"</com:PostShipment>"
	+" </soapenv:Body>"
	+"</soapenv:Envelope>";
		
		report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);
		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
		 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);
		 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
	}
           
	
	if("VID-DCX3400-MPEG4".equals(fulfillmentcode)&&"VID-DCX3400-MPEG4".equals(fulfillmentcode1))
	{
		System.out.println("test9");
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	     +"</wsse:UsernameToken>"
	 +" </wsse:Security>"
	  +"<com:requestHeader>"
	     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	    +" <com:sourceSystemId/>"
	     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	     +"<com:sourceServerId>1</com:sourceServerId>"
	     +"<com:trackingId>1</com:trackingId>"
	 +" </com:requestHeader>"
	 +"</soapenv:Header>"
	 +"<soapenv:Body>"
	  +"<com:PostShipment>"
	     +"<com:Request>"
	        +"<com:OrderID>"+Orderid+"</com:OrderID>"
	        +"<com:Shipments>"
	           +"<com:Shipment>"
	              +"<com:Carrier>UPS</com:Carrier>"
	             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	              +"<com:Method>Overnight</com:Method>"
	              +"<com:Packages>"
	                 +"<com:Package>"
	                   +" <com:Cost>25.34</com:Cost>"
	                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	                   +" <com:ReturnTrackingNumber/>"
	                   +" <com:Weight>2.35</com:Weight>"
	                 +"</com:Package>"
	             +" </com:Packages>"
	                 
	            
	                     //for dvr
	                     
	                    
		                    +" <com:ProductShipped>"
		                       +" <com:ProductID>"+groupid+"</com:ProductID>"
		                	                        
		                        +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
		                        +"<com:DevicesShipped>"
		                          +" <com:DeviceShipped>"
		                            +"  <com:Name/>"
		                             +" <com:Make>MOTO</com:Make>"
		                              +"<com:Model>DCX3400</com:Model>"
		                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
		                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                              +"<com:MACAddress/>"
		                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                              +"<com:MTAMACAddress/>"
		                              +"<com:MasterSubsidiaryLock/>"
		                              +"<com:USBMACAddress/>"
		                              +"<com:UnitID>"+unit1+"</com:UnitID>"
		                             +" <com:UnitID2/>"
		                             +" <com:Condition>New</com:Condition>"
		                             +" <com:DeviceType>STB</com:DeviceType>"
		                              +"<com:MACAddress2/>"
		                              +"<com:Make2>MOTO</com:Make2>"
		                              +"<com:Model2>MSCC9062</com:Model2>"
		                              +"<com:RFMACAddress/>"
		                              +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                          +" </com:DeviceShipped>"
		                        +"</com:DevicesShipped>"
		                         +"</com:ProductShipped>"
		                     
  +" <com:ProductShipped>"
  +" <com:ProductID>"+groupid1+"</com:ProductID>"
                       
   +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
   +"<com:DevicesShipped>"
     +" <com:DeviceShipped>"
       +"  <com:Name/>"
        +" <com:Make>MOTO</com:Make>"
         +"<com:Model>DCX3400</com:Model>"
         +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
         +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
         +"<com:MACAddress/>"
         +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
         +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
         +"<com:MTAMACAddress/>"
         +"<com:MasterSubsidiaryLock/>"
         +"<com:USBMACAddress/>"
         +"<com:UnitID>"+unit2+"</com:UnitID>"
        +" <com:UnitID2/>"
        +" <com:Condition>New</com:Condition>"
        +" <com:DeviceType>STB</com:DeviceType>"
         +"<com:MACAddress2/>"
         +"<com:Make2>MOTO</com:Make2>"
         +"<com:Model2>MSCC9062</com:Model2>"
         +"<com:RFMACAddress/>"
         +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
     +" </com:DeviceShipped>"
   +"</com:DevicesShipped>"
    +"</com:ProductShipped>"
	                 
	                 
	                 
	                
	              +"</com:ProductsShipped>"
	          +" </com:Shipment>"
	        +"</com:Shipments>"
	     +"</com:Request>"
	  +"</com:PostShipment>"
	+" </soapenv:Body>"
	+"</soapenv:Envelope>";
		
		report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);
		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
		 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);
		 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
	}
	
	
	
	
	
	
	if("VID-DCX3400-MPEG4".equals(fulfillmentcode)&&"RMA-EasyReturns".equals(fulfillmentcode2))
	{
		System.out.println("test11");
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	     +"</wsse:UsernameToken>"
	 +" </wsse:Security>"
	  +"<com:requestHeader>"
	     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	    +" <com:sourceSystemId/>"
	     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	     +"<com:sourceServerId>1</com:sourceServerId>"
	     +"<com:trackingId>1</com:trackingId>"
	 +" </com:requestHeader>"
	 +"</soapenv:Header>"
	 +"<soapenv:Body>"
	  +"<com:PostShipment>"
	     +"<com:Request>"
	        +"<com:OrderID>"+Orderid+"</com:OrderID>"
	        +"<com:Shipments>"
	           +"<com:Shipment>"
	              +"<com:Carrier>UPS</com:Carrier>"
	             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	              +"<com:Method>Overnight</com:Method>"
	              +"<com:Packages>"
	                 +"<com:Package>"
	                   +" <com:Cost>25.34</com:Cost>"
	                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	                   +" <com:ReturnTrackingNumber/>"
	                   +" <com:Weight>2.35</com:Weight>"
	                 +"</com:Package>"
	             +" </com:Packages>"
	                 
	            
	                     //for dvr
	                     
	                    
		                    +" <com:ProductShipped>"
		                       +" <com:ProductID>"+groupid+"</com:ProductID>"
		                	                        
		                        +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
		                        +"<com:DevicesShipped>"
		                          +" <com:DeviceShipped>"
		                            +"  <com:Name/>"
		                             +" <com:Make>MOTO</com:Make>"
		                              +"<com:Model>DCX3400</com:Model>"
		                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
		                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                              +"<com:MACAddress/>"
		                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                              +"<com:MTAMACAddress/>"
		                              +"<com:MasterSubsidiaryLock/>"
		                              +"<com:USBMACAddress/>"
		                              +"<com:UnitID>"+unit1+"</com:UnitID>"
		                             +" <com:UnitID2/>"
		                             +" <com:Condition>New</com:Condition>"
		                             +" <com:DeviceType>STB</com:DeviceType>"
		                              +"<com:MACAddress2/>"
		                              +"<com:Make2>MOTO</com:Make2>"
		                              +"<com:Model2>MSCC9062</com:Model2>"
		                              +"<com:RFMACAddress/>"
		                              +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                          +" </com:DeviceShipped>"
		                        +"</com:DevicesShipped>"
		                         +"</com:ProductShipped>"
		                     
 	                 
	                 //for rma drop downs
	                +" <com:ProductShipped>"
	                    +"<com:ProductID>"+groupid1+"</com:ProductID>"
	                   +" <com:FulfillmentCode>RMA-DF-LABEL-01</com:FulfillmentCode>"
	                    +"<com:DevicesShipped>"
	                       +"<com:DeviceShipped>"
	                          +"<com:Name/>"
	                          +"<com:Make/>"
	                          +"<com:Model/>"
	                         +" <com:SerialNumber/>"
	                          +"<com:SerialNumber2/>"
	                         +" <com:MACAddress/>"
	                         +" <com:CMACAddress/>"
	                          +"<com:EMACAddress/>"
	                         +" <com:MTAMACAddress/>"
	                         +" <com:MasterSubsidiaryLock/>"
	                         +" <com:USBMACAddress/>"
	                          +"<com:UnitID/>"
	                          +"<com:UnitID2/>"
	                         +" <com:Condition>New</com:Condition>"
	                         +" <com:DeviceType/>"
	                          +"<com:MACAddress2/>"
	                         +" <com:Make2/>"
	                          +"<com:Model2/>"
	                          +"<com:RFMACAddress/>"
	                         +" <com:DeviceID>"+deviceid1+"</com:DeviceID>"
	                      +" </com:DeviceShipped>"
	                   +" </com:DevicesShipped>"
	                +" </com:ProductShipped>"
	                 +"<com:ProductShipped>"
	                   +" <com:ProductID>"+groupid2+"</com:ProductID>"
	                    +"<com:FulfillmentCode>"+fulfillmentcode2+"</com:FulfillmentCode>"
	                   +" <com:DevicesShipped>"
	                       +"<com:DeviceShipped>"
	                         +" <com:Name/>"
	                          +"<com:Make/>"
	                          +"<com:Model/>"
	                          +"<com:SerialNumber/>"
	                          +"<com:SerialNumber2/>"
	                          +"<com:MACAddress/>"
	                          +"<com:CMACAddress/>"
	                          +"<com:EMACAddress/>"
	                          +"<com:MTAMACAddress/>"
	                          +"<com:MasterSubsidiaryLock/>"
	                          +"<com:USBMACAddress/>"
	                          +"<com:UnitID/>"
	                         +" <com:UnitID2/>"
	                          +"<com:Condition>New</com:Condition>"
	                          +"<com:DeviceType/>"
	                          +"<com:MACAddress2/>"
	                          +"<com:Make2/>"
	                         +" <com:Model2/>"
	                          +"<com:RFMACAddress/>"
	                          +"<com:DeviceID>"+deviceid2+"</com:DeviceID>"
	                    +"   </com:DeviceShipped>"
	                   +" </com:DevicesShipped>"
	                 +"</com:ProductShipped>"
	              +"</com:ProductsShipped>"
	          +" </com:Shipment>"
	        +"</com:Shipments>"
	     +"</com:Request>"
	  +"</com:PostShipment>"
	+" </soapenv:Body>"
	+"</soapenv:Envelope>";
		
		report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);
		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
		 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid1, Status.PASS);
		 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid1, Status.PASS);
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device3, Status.PASS);		
		 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device4, Status.PASS);	
	}
	
	
	/*if((fulfillmentcode==("VID-PCRNG150BNCD"))&&(fulfillmentcode1==(null))&&(fulfillmentcode2==(null))&&(fulfillmentcode3==(null)))
	{
		System.out.println("test12");
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
		   +"<soapenv:Header>"
		      +"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
		         +"<wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
		            +"<wsse:Username>Contec</wsse:Username>"
		            +"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		            +"<wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		            +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		       +"  </wsse:UsernameToken>"
		     +" </wsse:Security>"
		      +"<com:requestHeader>"
		        +" <com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		         +"<com:sourceSystemId/>"
		         +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		         +"<com:sourceServerId>1</com:sourceServerId>"
		         +"<com:trackingId>1</com:trackingId>"
		      +"</com:requestHeader>"
		   +"</soapenv:Header>"
		   +"<soapenv:Body>"
		      +"<com:PostShipment>"
		         +"<com:Request>"
		           +" <com:OrderID>"+Orderid+"</com:OrderID>"
		            +"<com:Shipments>"
		              +" <com:Shipment>"
		                 +" <com:Carrier>UPS</com:Carrier>"
		                  +"<com:Date>2010-04-06T18:34:04Z</com:Date>"
		                 +" <com:Method>Overnight</com:Method>"
		                  +"<com:Packages>"
		                     +"<com:Package>"
		                        +"<com:Cost>25.34</com:Cost>"
		                        +"<com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		                       +" <com:ReturnTrackingNumber/>"
		                        +"<com:Weight>2.35</com:Weight>"
		                     +"</com:Package>"
		                  +"</com:Packages>"
		                  +"<com:ProductsShipped>"
		                    +" <com:ProductShipped>"
		                       +" <com:ProductID>"+groupid+"</com:ProductID>"
		                        +"<com:FulfillmentCode>VID-SMRNG150BNMD</com:FulfillmentCode>"
		                        +"<com:DevicesShipped>"
		                           +"<com:DeviceShipped>"
		                            +"  <com:Name/>"
		                             +" <com:Make>SAM</com:Make>"
		                             +" <com:Model>SR150BNM</com:Model>"
		                             +" <com:SerialNumber>"+device1+"</com:SerialNumber>"
		                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                              +"<com:MACAddress/>"
		                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                              +"<com:MTAMACAddress/>"
		                              +"<com:MasterSubsidiaryLock/>"
		                             +" <com:USBMACAddress/>"
		                              +"<com:UnitID>"+unit1+"</com:UnitID>"
		                              +"<com:UnitID2/>"
		                              +"<com:Condition>New</com:Condition>"
		                              +"<com:DeviceType>STB</com:DeviceType>"
		                              +"<com:MACAddress2/>"
		                             +" <com:Make2>MOTO</com:Make2>"
		                              +"<com:Model2>MSCC9062</com:Model2>"
		                             +" <com:RFMACAddress/>"
		                              +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                           +"</com:DeviceShipped>"
		                        +"</com:DevicesShipped>"
		                    +" </com:ProductShipped>"
		                    
		                     +"<com:ProductShipped>"
		                       +" <com:ProductID>"+(groupid+1)+"</com:ProductID>"
		                        +"<com:FulfillmentCode>RMA-EasyReturns</com:FulfillmentCode>"
		                        +"<com:DevicesShipped>"
		                           +"<com:DeviceShipped>"
		                              +"<com:Name/>"
		                              +"<com:Make/>"
		                              +"<com:Model/>"
		                              +"<com:SerialNumber/>"
		                             +" <com:SerialNumber2/>"
		                              +"<com:MACAddress/>"
		                              +"<com:CMACAddress/>"
		                             +" <com:EMACAddress/>"
		                              +"<com:MTAMACAddress/>"
		                              +"<com:MasterSubsidiaryLock/>"
		                              +"<com:USBMACAddress/>"
		                              +"<com:UnitID/>"
		                              +"<com:UnitID2/>"
		                             +" <com:Condition>New</com:Condition>"
		                             +" <com:DeviceType/>"
		                              +"<com:MACAddress2/>"
		                              +"<com:Make2/>"
		                              +"<com:Model2/>"
		                              +"<com:RFMACAddress/>"
		                             +" <com:DeviceID>"+(deviceid+1)+"</com:DeviceID>"
		                          +" </com:DeviceShipped>"
		                       +" </com:DevicesShipped>"
		                    +" </com:ProductShipped>"
		                    
		                 +" </com:ProductsShipped>"
		              +" </com:Shipment>"
		            +"</com:Shipments>"
		        +" </com:Request>"
		     +" </com:PostShipment>"
		   +"</soapenv:Body>"
		+"</soapenv:Envelope>";
		report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);
		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);	
		 report.updateTestLog("The product id for RMA-EasyReturns", "Fetched successfully as : "+(groupid+1), Status.PASS);
		 report.updateTestLog("The device id for RMA-EasyReturns", "Fetched successfully as : "+(deviceid+1), Status.PASS);
		 
	}
	*/
	
	if("VID-RNG110RF-MPEG4".equals(fulfillmentcode)&&"RMA-DF-LABEL-01".equals(fulfillmentcode4)&&"RMA-EasyReturns".equals(fulfillmentcode5)&&(fulfillmentcode6.isEmpty()))
	{
		System.out.println("test13");
		Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
	       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
	        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
	     +"</wsse:UsernameToken>"
	 +" </wsse:Security>"
	  +"<com:requestHeader>"
	     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
	    +" <com:sourceSystemId/>"
	     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
	     +"<com:sourceServerId>1</com:sourceServerId>"
	     +"<com:trackingId>1</com:trackingId>"
	 +" </com:requestHeader>"
	 +"</soapenv:Header>"
	 +"<soapenv:Body>"
	  +"<com:PostShipment>"
	     +"<com:Request>"
	        +"<com:OrderID>"+Orderid+"</com:OrderID>"
	        +"<com:Shipments>"
	           +"<com:Shipment>"
	              +"<com:Carrier>UPS</com:Carrier>"
	             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
	              +"<com:Method>Overnight</com:Method>"
	              +"<com:Packages>"
	                 +"<com:Package>"
	                   +" <com:Cost>25.34</com:Cost>"
	                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
	                   +" <com:ReturnTrackingNumber/>"
	                   +" <com:Weight>2.35</com:Weight>"
	                 +"</com:Package>"
	             +" </com:Packages>"
	                 
	             //for dct mpeg4
	              +"<com:ProductsShipped>"
	                +" <com:ProductShipped>"
	                   +" <com:ProductID>"+groupid+"</com:ProductID>"
	            	                        
	                    +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
	                    +"<com:DevicesShipped>"
	                      +" <com:DeviceShipped>"
	                        +"  <com:Name/>"
	                         +" <com:Make>MOTO</com:Make>"
	                          +"<com:Model>DCX3200</com:Model>"
	                          +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
	                          +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
	                          +"<com:MACAddress/>"
	                          +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
	                          +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
	                          +"<com:MTAMACAddress/>"
	                          +"<com:MasterSubsidiaryLock/>"
	                          +"<com:USBMACAddress/>"
	                          +"<com:UnitID>"+unit1+"</com:UnitID>"
	                         +" <com:UnitID2/>"
	                         +" <com:Condition>New</com:Condition>"
	                         +" <com:DeviceType>STB</com:DeviceType>"
	                          +"<com:MACAddress2/>"
	                          +"<com:Make2>MOTO</com:Make2>"
	                          +"<com:Model2>MSCC9062</com:Model2>"
	                          +"<com:RFMACAddress/>"
	                          +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
	                      +" </com:DeviceShipped>"
	                    +"</com:DevicesShipped>"
	                     +"</com:ProductShipped>"
	                 
  +"<com:ProductShipped>"
  +" <com:ProductID>"+(groupid3)+"</com:ProductID>"
   +"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
   +"<com:DevicesShipped>"
      +"<com:DeviceShipped>"
         +"<com:Name/>"
         +"<com:Make/>"
         +"<com:Model/>"
         +"<com:SerialNumber/>"
        +" <com:SerialNumber2/>"
         +"<com:MACAddress/>"
         +"<com:CMACAddress/>"
        +" <com:EMACAddress/>"
         +"<com:MTAMACAddress/>"
         +"<com:MasterSubsidiaryLock/>"
         +"<com:USBMACAddress/>"
         +"<com:UnitID/>"
         +"<com:UnitID2/>"
        +" <com:Condition>New</com:Condition>"
        +" <com:DeviceType/>"
         +"<com:MACAddress2/>"
         +"<com:Make2/>"
         +"<com:Model2/>"
         +"<com:RFMACAddress/>"
        +" <com:DeviceID>"+(deviceid3)+"</com:DeviceID>"
     +" </com:DeviceShipped>"
  +" </com:DevicesShipped>"
+" </com:ProductShipped>"
  

  +"<com:ProductShipped>"
  +" <com:ProductID>"+(groupid4)+"</com:ProductID>"
   +"<com:FulfillmentCode>"+fulfillmentcode4+"</com:FulfillmentCode>"
   +"<com:DevicesShipped>"
      +"<com:DeviceShipped>"
         +"<com:Name/>"
         +"<com:Make/>"
         +"<com:Model/>"
         +"<com:SerialNumber/>"
        +" <com:SerialNumber2/>"
         +"<com:MACAddress/>"
         +"<com:CMACAddress/>"
        +" <com:EMACAddress/>"
         +"<com:MTAMACAddress/>"
         +"<com:MasterSubsidiaryLock/>"
         +"<com:USBMACAddress/>"
         +"<com:UnitID/>"
         +"<com:UnitID2/>"
        +" <com:Condition>New</com:Condition>"
        +" <com:DeviceType/>"
         +"<com:MACAddress2/>"
         +"<com:Make2/>"
         +"<com:Model2/>"
         +"<com:RFMACAddress/>"
        +" <com:DeviceID>"+(deviceid4)+"</com:DeviceID>"
     +" </com:DeviceShipped>"
  +" </com:DevicesShipped>"
+" </com:ProductShipped>"
	                     
	           
	              +"</com:ProductsShipped>"
	          +" </com:Shipment>"
	        +"</com:Shipments>"
	     +"</com:Request>"
	  +"</com:PostShipment>"
	+" </soapenv:Body>"
	+"</soapenv:Envelope>";
		
		report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);
		 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
			report.updateTestLog("The product id for RMA-DF-LABEL-01 ", "Fetched successfully as : "+(groupid3), Status.PASS);
			 report.updateTestLog("The device id for RMA-DF-LABEL-01 ", "Fetched successfully as : "+(deviceid3), Status.PASS);
				report.updateTestLog("The product id for "+fulfillmentcode4, "Fetched successfully as : "+(groupid4), Status.PASS);
				report.updateTestLog("The product id for "+fulfillmentcode4, "Fetched successfully as : "+(deviceid4), Status.PASS);

	
	
	}
	
	
	
	
	if("VID-PCRNG150BNCD".equals(fulfillmentcode)&&"RMA-EasyReturns".equals(fulfillmentcode3))
	 {
		 System.out.println("testdct");
		 Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		   +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		    +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		 +"</wsse:UsernameToken>"
		+" </wsse:Security>"
		+"<com:requestHeader>"
		 +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		+" <com:sourceSystemId/>"
		 +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		 +"<com:sourceServerId>1</com:sourceServerId>"
		 +"<com:trackingId>1</com:trackingId>"
		+" </com:requestHeader>"
		+"</soapenv:Header>"
		+"<soapenv:Body>"
		+"<com:PostShipment>"
		 +"<com:Request>"
		    +"<com:OrderID>"+Orderid+"</com:OrderID>"
		    +"<com:Shipments>"
		       +"<com:Shipment>"
		          +"<com:Carrier>UPS</com:Carrier>"
		         +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
		          +"<com:Method>Overnight</com:Method>"
		          +"<com:Packages>"
		             +"<com:Package>"
		               +" <com:Cost>25.34</com:Cost>"
		               +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		               +" <com:ReturnTrackingNumber/>"
		               +" <com:Weight>2.35</com:Weight>"
		             +"</com:Package>"
		         +" </com:Packages>"
		             
   +"<com:ProductsShipped>"
   
		         //for dct mpeg4
		       
		            +" <com:ProductShipped>"
		               +" <com:ProductID>"+groupid+"</com:ProductID>"
		        	                        
		                +"<com:FulfillmentCode>VID-SMRNG150BNMD</com:FulfillmentCode>"
		                +"<com:DevicesShipped>"
		                  +" <com:DeviceShipped>"
		                    +"  <com:Name/>"
		                     +" <com:Make>MOTO</com:Make>"
		                      +"<com:Model>DCX3200</com:Model>"
		                      +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
		                      +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                      +"<com:MACAddress/>"
		                      +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                      +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                      +"<com:MTAMACAddress/>"
		                      +"<com:MasterSubsidiaryLock/>"
		                      +"<com:USBMACAddress/>"
		                      +"<com:UnitID>"+unit1+"</com:UnitID>"
		                     +" <com:UnitID2/>"
		                     +" <com:Condition>New</com:Condition>"
		                     +" <com:DeviceType>STB</com:DeviceType>"
		                      +"<com:MACAddress2/>"
		                      +"<com:Make2>MOTO</com:Make2>"
		                      +"<com:Model2>MSCC9062</com:Model2>"
		                      +"<com:RFMACAddress/>"
		                      +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                  +" </com:DeviceShipped>"
		                +"</com:DevicesShipped>"
		                 +"</com:ProductShipped>"
		                
		                 +"<com:ProductShipped>"
		           	  +" <com:ProductID>"+(groupid3)+"</com:ProductID>"
		           	  +"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
		           	  +" <com:DevicesShipped>"
		           	     +"<com:DeviceShipped>"
		           	       +" <com:Name/>"
		           	        +"<com:Make/>"
		           	        +"<com:Model/>"
		           	        +"<com:SerialNumber/>"
		           	        +"<com:SerialNumber2/>"
		           	        +"<com:MACAddress/>"
		           	        +"<com:CMACAddress/>"
		           	        +"<com:EMACAddress/>"
		           	        +"<com:MTAMACAddress/>"
		           	        +"<com:MasterSubsidiaryLock/>"
		           	        +"<com:USBMACAddress/>"
		           	        +"<com:UnitID/>"
		           	       +" <com:UnitID2/>"
		           	        +"<com:Condition>New</com:Condition>"
		           	        +"<com:DeviceType/>"
		           	        +"<com:MACAddress2/>"
		           	        +"<com:Make2/>"
		           	       +" <com:Model2/>"
		           	        +"<com:RFMACAddress/>"
		           	        +"<com:DeviceID>"+(deviceid3)+"</com:DeviceID>"
		           	  +"   </com:DeviceShipped>"
		           	  +" </com:DevicesShipped>"
		           	  +"</com:ProductShipped>"
		                 
		                         
		     +"</com:ProductsShipped>"
		     +" </com:Shipment>"
		    +"</com:Shipments>"
		    +"</com:Request>"
		    +"</com:PostShipment>"
		    +" </soapenv:Body>"
		    +"</soapenv:Envelope>";
		 
		
		 
	 }
	 
	
	//commercial 2dcts
	if("VID-COMM-RNG110RF-MPEG4".equals(fulfillmentcode)&&"VID-COMM-RNG110RF-MPEG4".equals(fulfillmentcode1)&&"RMA-CommEasyReturns".equals(fulfillmentcode4))
	{
		System.out.println("testtest");
		 Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		   +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		    +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		 +"</wsse:UsernameToken>"
		+" </wsse:Security>"
		+"<com:requestHeader>"
		 +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		+" <com:sourceSystemId/>"
		 +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		 +"<com:sourceServerId>1</com:sourceServerId>"
		 +"<com:trackingId>1</com:trackingId>"
		+" </com:requestHeader>"
		+"</soapenv:Header>"
		+"<soapenv:Body>"
		+"<com:PostShipment>"
		 +"<com:Request>"
		    +"<com:OrderID>"+Orderid+"</com:OrderID>"
		    +"<com:Shipments>"
		       +"<com:Shipment>"
		          +"<com:Carrier>UPS</com:Carrier>"
		         +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
		          +"<com:Method>Overnight</com:Method>"
		          +"<com:Packages>"
		             +"<com:Package>"
		               +" <com:Cost>25.34</com:Cost>"
		               +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		               +" <com:ReturnTrackingNumber/>"
		               +" <com:Weight>2.35</com:Weight>"
		             +"</com:Package>"
		         +" </com:Packages>"
		             
		         //for dct mpeg4
		          +"<com:ProductsShipped>"
		            +" <com:ProductShipped>"
		               +" <com:ProductID>"+groupid+"</com:ProductID>"
		        	                        
		                +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
		                +"<com:DevicesShipped>"
		                  +" <com:DeviceShipped>"
		                    +"  <com:Name/>"
		                     +" <com:Make>MOTO</com:Make>"
		                      +"<com:Model>DCX3200</com:Model>"
		                      +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
		                      +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                      +"<com:MACAddress/>"
		                      +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                      +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                      +"<com:MTAMACAddress/>"
		                      +"<com:MasterSubsidiaryLock/>"
		                      +"<com:USBMACAddress/>"
		                      +"<com:UnitID>"+unit1+"</com:UnitID>"
		                     +" <com:UnitID2/>"
		                     +" <com:Condition>New</com:Condition>"
		                     +" <com:DeviceType>STB</com:DeviceType>"
		                      +"<com:MACAddress2/>"
		                      +"<com:Make2>MOTO</com:Make2>"
		                      +"<com:Model2>MSCC9062</com:Model2>"
		                      +"<com:RFMACAddress/>"
		                      +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                  +" </com:DeviceShipped>"
		                +"</com:DevicesShipped>"
		                 +"</com:ProductShipped>"
		             
		                 
		                 //dct
		                 +" <com:ProductShipped>"
			               +" <com:ProductID>"+groupid1+"</com:ProductID>"
			        	                        
			                +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
			                +"<com:DevicesShipped>"
			                  +" <com:DeviceShipped>"
			                    +"  <com:Name/>"
			                     +" <com:Make>MOTO</com:Make>"
			                      +"<com:Model>DCX3200</com:Model>"
			                      +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
			                      +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
			                      +"<com:MACAddress/>"
			                      +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
			                      +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
			                      +"<com:MTAMACAddress/>"
			                      +"<com:MasterSubsidiaryLock/>"
			                      +"<com:USBMACAddress/>"
			                      +"<com:UnitID>"+unit2+"</com:UnitID>"
			                     +" <com:UnitID2/>"
			                     +" <com:Condition>New</com:Condition>"
			                     +" <com:DeviceType>STB</com:DeviceType>"
			                      +"<com:MACAddress2/>"
			                      +"<com:Make2>MOTO</com:Make2>"
			                      +"<com:Model2>MSCC9062</com:Model2>"
			                      +"<com:RFMACAddress/>"
			                      +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
			                  +" </com:DeviceShipped>"
			                +"</com:DevicesShipped>"
			                 +"</com:ProductShipped>"
		                 
		                 
		                         +"<com:ProductShipped>"
		                         +" <com:ProductID>"+groupid4+"</com:ProductID>"
		                         +"<com:FulfillmentCode>"+fulfillmentcode4+"</com:FulfillmentCode>"
		                         +" <com:DevicesShipped>"
		                            +"<com:DeviceShipped>"
		                              +" <com:Name/>"
		                               +"<com:Make/>"
		                               +"<com:Model/>"
		                               +"<com:SerialNumber/>"
		                               +"<com:SerialNumber2/>"
		                               +"<com:MACAddress/>"
		                               +"<com:CMACAddress/>"
		                               +"<com:EMACAddress/>"
		                               +"<com:MTAMACAddress/>"
		                               +"<com:MasterSubsidiaryLock/>"
		                               +"<com:USBMACAddress/>"
		                               +"<com:UnitID/>"
		                              +" <com:UnitID2/>"
		                               +"<com:Condition>New</com:Condition>"
		                               +"<com:DeviceType/>"
		                               +"<com:MACAddress2/>"
		                               +"<com:Make2/>"
		                              +" <com:Model2/>"
		                               +"<com:RFMACAddress/>"
		                               +"<com:DeviceID>"+deviceid4+"</com:DeviceID>"
		                         +"   </com:DeviceShipped>"
		                         +" </com:DevicesShipped>"
		                         +"</com:ProductShipped>"
		                         
		     +"</com:ProductsShipped>"
		     +" </com:Shipment>"
		    +"</com:Shipments>"
		    +"</com:Request>"
		    +"</com:PostShipment>"
		    +" </soapenv:Body>"
		    +"</soapenv:Envelope>";
		
		 report.updateTestLog("The product id for DCT1 device ", "Fetched successfully as : "+groupid, Status.PASS);

		 report.updateTestLog("The device id for DCT1 device ", "Fetched successfully as : "+deviceid, Status.PASS);

		 report.updateTestLog("The product id for DCT2 device ", "Fetched successfully as : "+groupid, Status.PASS);

		 report.updateTestLog("The device id for DCT2 device ", "Fetched successfully as : "+deviceid, Status.PASS);

		 
		 report.updateTestLog("The product id for "+fulfillmentcode3, "Fetched successfully as : "+groupid3, Status.PASS);

		 report.updateTestLog("The device id for "+fulfillmentcode3, "Fetched successfully as : "+deviceid3, Status.PASS);

		
		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		

		 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
 
	
		 
		 
		
	}

	
	//csg commercial eol video only dct2000
	if("VID-COMM-RNG110RF-MPEG4".equals(fulfillmentcode)&&"RMA-CommEasyReturns".equals(fulfillmentcode3))
	{
		System.out.println("testtest");
		 Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		   +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		    +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		 +"</wsse:UsernameToken>"
		+" </wsse:Security>"
		+"<com:requestHeader>"
		 +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		+" <com:sourceSystemId/>"
		 +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		 +"<com:sourceServerId>1</com:sourceServerId>"
		 +"<com:trackingId>1</com:trackingId>"
		+" </com:requestHeader>"
		+"</soapenv:Header>"
		+"<soapenv:Body>"
		+"<com:PostShipment>"
		 +"<com:Request>"
		    +"<com:OrderID>"+Orderid+"</com:OrderID>"
		    +"<com:Shipments>"
		       +"<com:Shipment>"
		          +"<com:Carrier>UPS</com:Carrier>"
		         +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
		          +"<com:Method>Overnight</com:Method>"
		          +"<com:Packages>"
		             +"<com:Package>"
		               +" <com:Cost>25.34</com:Cost>"
		               +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		               +" <com:ReturnTrackingNumber/>"
		               +" <com:Weight>2.35</com:Weight>"
		             +"</com:Package>"
		         +" </com:Packages>"
		             
		         //for dct mpeg4
		          +"<com:ProductsShipped>"
		            +" <com:ProductShipped>"
		               +" <com:ProductID>"+groupid+"</com:ProductID>"
		        	                        
		                +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
		                +"<com:DevicesShipped>"
		                  +" <com:DeviceShipped>"
		                    +"  <com:Name/>"
		                     +" <com:Make>MOTO</com:Make>"
		                      +"<com:Model>DCX3200</com:Model>"
		                      +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
		                      +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                      +"<com:MACAddress/>"
		                      +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                      +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                      +"<com:MTAMACAddress/>"
		                      +"<com:MasterSubsidiaryLock/>"
		                      +"<com:USBMACAddress/>"
		                      +"<com:UnitID>"+unit1+"</com:UnitID>"
		                     +" <com:UnitID2/>"
		                     +" <com:Condition>New</com:Condition>"
		                     +" <com:DeviceType>STB</com:DeviceType>"
		                      +"<com:MACAddress2/>"
		                      +"<com:Make2>MOTO</com:Make2>"
		                      +"<com:Model2>MSCC9062</com:Model2>"
		                      +"<com:RFMACAddress/>"
		                      +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                  +" </com:DeviceShipped>"
		                +"</com:DevicesShipped>"
		                 +"</com:ProductShipped>"
		             
		                 
		               
		                 
		                         +"<com:ProductShipped>"
		                         +" <com:ProductID>"+groupid3+"</com:ProductID>"
		                         +"<com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
		                         +" <com:DevicesShipped>"
		                            +"<com:DeviceShipped>"
		                              +" <com:Name/>"
		                               +"<com:Make/>"
		                               +"<com:Model/>"
		                               +"<com:SerialNumber/>"
		                               +"<com:SerialNumber2/>"
		                               +"<com:MACAddress/>"
		                               +"<com:CMACAddress/>"
		                               +"<com:EMACAddress/>"
		                               +"<com:MTAMACAddress/>"
		                               +"<com:MasterSubsidiaryLock/>"
		                               +"<com:USBMACAddress/>"
		                               +"<com:UnitID/>"
		                              +" <com:UnitID2/>"
		                               +"<com:Condition>New</com:Condition>"
		                               +"<com:DeviceType/>"
		                               +"<com:MACAddress2/>"
		                               +"<com:Make2/>"
		                              +" <com:Model2/>"
		                               +"<com:RFMACAddress/>"
		                               +"<com:DeviceID>"+deviceid3+"</com:DeviceID>"
		                         +"   </com:DeviceShipped>"
		                         +" </com:DevicesShipped>"
		                         +"</com:ProductShipped>"
		                         
		     +"</com:ProductsShipped>"
		     +" </com:Shipment>"
		    +"</com:Shipments>"
		    +"</com:Request>"
		    +"</com:PostShipment>"
		    +" </soapenv:Body>"
		    +"</soapenv:Envelope>";
		
		 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid, Status.PASS);

			 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid, Status.PASS);

			 report.updateTestLog("The product id for "+fulfillmentcode3, "Fetched successfully as : "+groupid3, Status.PASS);

			 report.updateTestLog("The device id for "+fulfillmentcode3, "Fetched successfully as : "+deviceid3, Status.PASS);

			
			 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device1, Status.PASS);		

			 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device2, Status.PASS);		
	 
		
	}
	
	
	
	System.out.println("testtest1");
	//for mpeg4 dvr dct
	if("VID-RNG110RF-MPEG4".equals(fulfillmentcode1)&&"RMA-DF-LABEL-01".equals(fulfillmentcode4))
	{
		System.out.println("testtest1");
		 Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

		 Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+"<soapenv:Header>"
				+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
				+"<wsse:Username>Contec</wsse:Username>"
				+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		   +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		    +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		 +"</wsse:UsernameToken>"
		+" </wsse:Security>"
		+"<com:requestHeader>"
		 +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		+" <com:sourceSystemId/>"
		 +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		 +"<com:sourceServerId>1</com:sourceServerId>"
		 +"<com:trackingId>1</com:trackingId>"
		+" </com:requestHeader>"
		+"</soapenv:Header>"
		+"<soapenv:Body>"
		+"<com:PostShipment>"
		 +"<com:Request>"
		    +"<com:OrderID>"+Orderid+"</com:OrderID>"
		    +"<com:Shipments>"
		       +"<com:Shipment>"
		          +"<com:Carrier>UPS</com:Carrier>"
		         +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
		          +"<com:Method>Overnight</com:Method>"
		          +"<com:Packages>"
		             +"<com:Package>"
		               +" <com:Cost>25.34</com:Cost>"
		               +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		               +" <com:ReturnTrackingNumber/>"
		               +" <com:Weight>2.35</com:Weight>"
		             +"</com:Package>"
		         +" </com:Packages>"
		             
		    
		          +"<com:ProductsShipped>"
		          
		          //for dvr
		                 
		                
		                    +" <com:ProductShipped>"
		                       +" <com:ProductID>"+groupid+"</com:ProductID>"
		                	                        
		                        +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
		                        +"<com:DevicesShipped>"
		                          +" <com:DeviceShipped>"
		                            +"  <com:Name/>"
		                             +" <com:Make>MOTO</com:Make>"
		                              +"<com:Model>DCX3200</com:Model>"
		                              +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
		                              +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                              +"<com:MACAddress/>"
		                              +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                              +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                              +"<com:MTAMACAddress/>"
		                              +"<com:MasterSubsidiaryLock/>"
		                              +"<com:USBMACAddress/>"
		                              +"<com:UnitID>"+unit1+"</com:UnitID>"
		                             +" <com:UnitID2/>"
		                             +" <com:Condition>New</com:Condition>"
		                             +" <com:DeviceType>STB</com:DeviceType>"
		                              +"<com:MACAddress2/>"
		                              +"<com:Make2>MOTO</com:Make2>"
		                              +"<com:Model2>MSCC9062</com:Model2>"
		                              +"<com:RFMACAddress/>"
		                              +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                          +" </com:DeviceShipped>"
		                        +"</com:DevicesShipped>"
		                         +"</com:ProductShipped>"
		             
		                         //for dct mpeg4
		            +" <com:ProductShipped>"
		               +" <com:ProductID>"+groupid1+"</com:ProductID>"
		        	                        
		                +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
		                +"<com:DevicesShipped>"
		                  +" <com:DeviceShipped>"
		                    +"  <com:Name/>"
		                     +" <com:Make>MOTO</com:Make>"
		                      +"<com:Model>DCX3400</com:Model>"
		                      +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
		                      +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
		                      +"<com:MACAddress/>"
		                      +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
		                      +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
		                      +"<com:MTAMACAddress/>"
		                      +"<com:MasterSubsidiaryLock/>"
		                      +"<com:USBMACAddress/>"
		                      +"<com:UnitID>"+unit2+"</com:UnitID>"
		                     +" <com:UnitID2/>"
		                     +" <com:Condition>New</com:Condition>"
		                     +" <com:DeviceType>STB</com:DeviceType>"
		                      +"<com:MACAddress2/>"
		                      +"<com:Make2>MOTO</com:Make2>"
		                      +"<com:Model2>MSCC9062</com:Model2>"
		                      +"<com:RFMACAddress/>"
		                      +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
		                  +" </com:DeviceShipped>"
		                +"</com:DevicesShipped>"
		                 +"</com:ProductShipped>"
		             
		              
		                 
		                         +"<com:ProductShipped>"
		                         +" <com:ProductID>"+groupid4+"</com:ProductID>"
		                         +"<com:FulfillmentCode>"+fulfillmentcode4+"</com:FulfillmentCode>"
		                         +" <com:DevicesShipped>"
		                            +"<com:DeviceShipped>"
		                              +" <com:Name/>"
		                               +"<com:Make/>"
		                               +"<com:Model/>"
		                               +"<com:SerialNumber/>"
		                               +"<com:SerialNumber2/>"
		                               +"<com:MACAddress/>"
		                               +"<com:CMACAddress/>"
		                               +"<com:EMACAddress/>"
		                               +"<com:MTAMACAddress/>"
		                               +"<com:MasterSubsidiaryLock/>"
		                               +"<com:USBMACAddress/>"
		                               +"<com:UnitID/>"
		                              +" <com:UnitID2/>"
		                               +"<com:Condition>New</com:Condition>"
		                               +"<com:DeviceType/>"
		                               +"<com:MACAddress2/>"
		                               +"<com:Make2/>"
		                              +" <com:Model2/>"
		                               +"<com:RFMACAddress/>"
		                               +"<com:DeviceID>"+deviceid4+"</com:DeviceID>"
		                         +"   </com:DeviceShipped>"
		                         +" </com:DevicesShipped>"
		                         +"</com:ProductShipped>"
		                         
		                         

		                         +"<com:ProductShipped>"
		                         +" <com:ProductID>"+groupid5+"</com:ProductID>"
		                         +"<com:FulfillmentCode>"+fulfillmentcode5+"</com:FulfillmentCode>"
		                         +" <com:DevicesShipped>"
		                            +"<com:DeviceShipped>"
		                              +" <com:Name/>"
		                               +"<com:Make/>"
		                               +"<com:Model/>"
		                               +"<com:SerialNumber/>"
		                               +"<com:SerialNumber2/>"
		                               +"<com:MACAddress/>"
		                               +"<com:CMACAddress/>"
		                               +"<com:EMACAddress/>"
		                               +"<com:MTAMACAddress/>"
		                               +"<com:MasterSubsidiaryLock/>"
		                               +"<com:USBMACAddress/>"
		                               +"<com:UnitID/>"
		                              +" <com:UnitID2/>"
		                               +"<com:Condition>New</com:Condition>"
		                               +"<com:DeviceType/>"
		                               +"<com:MACAddress2/>"
		                               +"<com:Make2/>"
		                              +" <com:Model2/>"
		                               +"<com:RFMACAddress/>"
		                               +"<com:DeviceID>"+deviceid5+"</com:DeviceID>"
		                         +"   </com:DeviceShipped>"
		                         +" </com:DevicesShipped>"
		                         +"</com:ProductShipped>"
		                         
		     +"</com:ProductsShipped>"
		     +" </com:Shipment>"
		    +"</com:Shipments>"
		    +"</com:Request>"
		    +"</com:PostShipment>"
		    +" </soapenv:Body>"
		    +"</soapenv:Envelope>";
		
		
	}

	
	//mpeg4 dvrdct ddp
	if(("VID-RNG110RF-MPEG4".equals(fulfillmentcode1)||"VID-MOTRNG150N-MPEG4".equals(fulfillmentcode1)||"VID-SARNG150-MPEG4".equals(fulfillmentcode1))&&("VID-MOTRNG200BN-MPEG4".equals(fulfillmentcode)||"VID-SARNG200-MPEG4".equals(fulfillmentcode))&&"RMA-EasyReturns".equals(fulfillmentcode3))
		{
			System.out.println("test7");
			Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

			Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
					+"<soapenv:Header>"
					+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
					+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
					+"<wsse:Username>Contec</wsse:Username>"
					+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
		       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
		        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
		     +"</wsse:UsernameToken>"
		 +" </wsse:Security>"
		  +"<com:requestHeader>"
		     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
		    +" <com:sourceSystemId/>"
		     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
		     +"<com:sourceServerId>1</com:sourceServerId>"
		     +"<com:trackingId>1</com:trackingId>"
		 +" </com:requestHeader>"
		 +"</soapenv:Header>"
		 +"<soapenv:Body>"
		  +"<com:PostShipment>"
		     +"<com:Request>"
		        +"<com:OrderID>"+Orderid+"</com:OrderID>"
		        +"<com:Shipments>"
		           +"<com:Shipment>"
		              +"<com:Carrier>UPS</com:Carrier>"
		             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
		              +"<com:Method>Overnight</com:Method>"
		              +"<com:Packages>"
		                 +"<com:Package>"
		                   +" <com:Cost>25.34</com:Cost>"
		                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
		                   +" <com:ReturnTrackingNumber/>"
		                   +" <com:Weight>2.35</com:Weight>"
		                 +"</com:Package>"
		             +" </com:Packages>"
		                 
		             //for dvr
	                 
		             +"<com:ProductsShipped>"
	                 +" <com:ProductShipped>"
	                    +" <com:ProductID>"+groupid+"</com:ProductID>"
	             	                        
	                     +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
	                     +"<com:DevicesShipped>"
	                       +" <com:DeviceShipped>"
	                         +"  <com:Name/>"
	                          +" <com:Make>MOTO</com:Make>"
	                           +"<com:Model>DCX3400</com:Model>"
	                           +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
	                           +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
	                           +"<com:MACAddress/>"
	                           +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
	                           +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
	                           +"<com:MTAMACAddress/>"
	                           +"<com:MasterSubsidiaryLock/>"
	                           +"<com:USBMACAddress/>"
	                           +"<com:UnitID>"+unit1+"</com:UnitID>"
	                          +" <com:UnitID2/>"
	                          +" <com:Condition>New</com:Condition>"
	                          +" <com:DeviceType>STB</com:DeviceType>"
	                           +"<com:MACAddress2/>"
	                           +"<com:Make2>MOTO</com:Make2>"
	                           +"<com:Model2>MSCC9062</com:Model2>"
	                           +"<com:RFMACAddress/>"
	                           +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
	                       +" </com:DeviceShipped>"
	                     +"</com:DevicesShipped>"
	                      +"</com:ProductShipped>"
	                  
	             
		                 
		             //for dct mpeg4
		             
		                +" <com:ProductShipped>"
		                   +" <com:ProductID>"+groupid1+"</com:ProductID>"
		            	                        
		                    +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
		                    +"<com:DevicesShipped>"
		                      +" <com:DeviceShipped>"
		                        +"  <com:Name/>"
		                         +" <com:Make>MOTO</com:Make>"
		                          +"<com:Model>DCX3200</com:Model>"
		                          +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
		                          +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
		                          +"<com:MACAddress/>"
		                          +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
		                          +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
		                          +"<com:MTAMACAddress/>"
		                          +"<com:MasterSubsidiaryLock/>"
		                          +"<com:USBMACAddress/>"
		                          +"<com:UnitID>"+unit2+"</com:UnitID>"
		                         +" <com:UnitID2/>"
		                         +" <com:Condition>New</com:Condition>"
		                         +" <com:DeviceType>STB</com:DeviceType>"
		                          +"<com:MACAddress2/>"
		                          +"<com:Make2>MOTO</com:Make2>"
		                          +"<com:Model2>MSCC9062</com:Model2>"
		                          +"<com:RFMACAddress/>"
		                          +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
		                      +" </com:DeviceShipped>"
		                    +"</com:DevicesShipped>"
		                     +"</com:ProductShipped>"
		                 
		                  
		   	                     
		                 
		                 
		                 
		                 //for rma drop downs
		                 
		                  /*   +" <com:ProductShipped>"
			                    +"<com:ProductID>"+(groupid1+2)+"</com:ProductID>"
			                   +" <com:FulfillmentCode>RMA-DF-LABEL-01</com:FulfillmentCode>"
			                    +"<com:DevicesShipped>"
			                       +"<com:DeviceShipped>"
			                          +"<com:Name/>"
			                          +"<com:Make/>"
			                          +"<com:Model/>"
			                         +" <com:SerialNumber/>"
			                          +"<com:SerialNumber2/>"
			                         +" <com:MACAddress/>"
			                         +" <com:CMACAddress/>"
			                          +"<com:EMACAddress/>"
			                         +" <com:MTAMACAddress/>"
			                         +" <com:MasterSubsidiaryLock/>"
			                         +" <com:USBMACAddress/>"
			                          +"<com:UnitID/>"
			                          +"<com:UnitID2/>"
			                         +" <com:Condition>New</com:Condition>"
			                         +" <com:DeviceType/>"
			                          +"<com:MACAddress2/>"
			                         +" <com:Make2/>"
			                          +"<com:Model2/>"
			                          +"<com:RFMACAddress/>"
			                         +" <com:DeviceID>"+(deviceid1+2)+"</com:DeviceID>"
			                      +" </com:DeviceShipped>"
			                   +" </com:DevicesShipped>"
			                +" </com:ProductShipped>"
			                
			                 */
		                
		                
		                
		                
		                
		                +" <com:ProductShipped>"
		                    +"<com:ProductID>"+(groupid3)+"</com:ProductID>"
		                   +" <com:FulfillmentCode>"+fulfillmentcode3+"</com:FulfillmentCode>"
		                    +"<com:DevicesShipped>"
		                       +"<com:DeviceShipped>"
		                          +"<com:Name/>"
		                          +"<com:Make/>"
		                          +"<com:Model/>"
		                         +" <com:SerialNumber/>"
		                          +"<com:SerialNumber2/>"
		                         +" <com:MACAddress/>"
		                         +" <com:CMACAddress/>"
		                          +"<com:EMACAddress/>"
		                         +" <com:MTAMACAddress/>"
		                         +" <com:MasterSubsidiaryLock/>"
		                         +" <com:USBMACAddress/>"
		                          +"<com:UnitID/>"
		                          +"<com:UnitID2/>"
		                         +" <com:Condition>New</com:Condition>"
		                         +" <com:DeviceType/>"
		                          +"<com:MACAddress2/>"
		                         +" <com:Make2/>"
		                          +"<com:Model2/>"
		                          +"<com:RFMACAddress/>"
		                         +" <com:DeviceID>"+(deviceid3)+"</com:DeviceID>"
		                      +" </com:DeviceShipped>"
		                   +" </com:DevicesShipped>"
		                +" </com:ProductShipped>"
		                
		                
		              
		              
		              +"</com:ProductsShipped>"
		          +" </com:Shipment>"
		        +"</com:Shipments>"
		     +"</com:Request>"
		  +"</com:PostShipment>"
		+" </soapenv:Body>"
		+"</soapenv:Envelope>";
			
			report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid, Status.PASS);

			 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid, Status.PASS);

			 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid1, Status.PASS);

			 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid1, Status.PASS);

			 report.updateTestLog("The product id for "+fulfillmentcode3, "Fetched successfully as : "+groupid3, Status.PASS);

			 report.updateTestLog("The device id for "+fulfillmentcode3, "Fetched successfully as : "+deviceid3, Status.PASS);

			
			 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device1, Status.PASS);		

			 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device2, Status.PASS);		

			 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device3, Status.PASS);		
			 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device4, Status.PASS);	
			 
			
		}
	        
	//mpeg4 dvrdct csg moto
		if(("VID-RNG110RF-MPEG4".equals(fulfillmentcode1)||"VID-MOTRNG150N-MPEG4".equals(fulfillmentcode1))&&("VID-MOTRNG200BN-MPEG4".equals(fulfillmentcode))&&"RMA-EasyReturns".equals(fulfillmentcode5)&&"RMA-DF-LABEL-01".equals(fulfillmentcode4))
			{
				System.out.println("test7");
				Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

				Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+"<soapenv:Header>"
						+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
						+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
						+"<wsse:Username>Contec</wsse:Username>"
						+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
			       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
			        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
			     +"</wsse:UsernameToken>"
			 +" </wsse:Security>"
			  +"<com:requestHeader>"
			     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
			    +" <com:sourceSystemId/>"
			     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
			     +"<com:sourceServerId>1</com:sourceServerId>"
			     +"<com:trackingId>1</com:trackingId>"
			 +" </com:requestHeader>"
			 +"</soapenv:Header>"
			 +"<soapenv:Body>"
			  +"<com:PostShipment>"
			     +"<com:Request>"
			        +"<com:OrderID>"+Orderid+"</com:OrderID>"
			        +"<com:Shipments>"
			           +"<com:Shipment>"
			              +"<com:Carrier>UPS</com:Carrier>"
			             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
			              +"<com:Method>Overnight</com:Method>"
			              +"<com:Packages>"
			                 +"<com:Package>"
			                   +" <com:Cost>25.34</com:Cost>"
			                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
			                   +" <com:ReturnTrackingNumber/>"
			                   +" <com:Weight>2.35</com:Weight>"
			                 +"</com:Package>"
			             +" </com:Packages>"
			                 
			             //for dvr
		                 
			             +"<com:ProductsShipped>"
		                 +" <com:ProductShipped>"
		                    +" <com:ProductID>"+groupid+"</com:ProductID>"
		             	                        
		                     +"<com:FulfillmentCode>"+fulfillmentcode+"</com:FulfillmentCode>"
		                     +"<com:DevicesShipped>"
		                       +" <com:DeviceShipped>"
		                         +"  <com:Name/>"
		                          +" <com:Make>MOTO</com:Make>"
		                           +"<com:Model>DCX3400</com:Model>"
		                           +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
		                           +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                           +"<com:MACAddress/>"
		                           +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                           +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                           +"<com:MTAMACAddress/>"
		                           +"<com:MasterSubsidiaryLock/>"
		                           +"<com:USBMACAddress/>"
		                           +"<com:UnitID>"+unit1+"</com:UnitID>"
		                          +" <com:UnitID2/>"
		                          +" <com:Condition>New</com:Condition>"
		                          +" <com:DeviceType>STB</com:DeviceType>"
		                           +"<com:MACAddress2/>"
		                           +"<com:Make2>MOTO</com:Make2>"
		                           +"<com:Model2>MSCC9062</com:Model2>"
		                           +"<com:RFMACAddress/>"
		                           +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                       +" </com:DeviceShipped>"
		                     +"</com:DevicesShipped>"
		                      +"</com:ProductShipped>"
		                  
		             
			                 
			             //for dct mpeg4
			             
			                +" <com:ProductShipped>"
			                   +" <com:ProductID>"+groupid1+"</com:ProductID>"
			            	                        
			                    +"<com:FulfillmentCode>"+fulfillmentcode1+"</com:FulfillmentCode>"
			                    +"<com:DevicesShipped>"
			                      +" <com:DeviceShipped>"
			                        +"  <com:Name/>"
			                         +" <com:Make>MOTO</com:Make>"
			                          +"<com:Model>DCX3200</com:Model>"
			                          +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
			                          +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
			                          +"<com:MACAddress/>"
			                          +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
			                          +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
			                          +"<com:MTAMACAddress/>"
			                          +"<com:MasterSubsidiaryLock/>"
			                          +"<com:USBMACAddress/>"
			                          +"<com:UnitID>"+unit2+"</com:UnitID>"
			                         +" <com:UnitID2/>"
			                         +" <com:Condition>New</com:Condition>"
			                         +" <com:DeviceType>STB</com:DeviceType>"
			                          +"<com:MACAddress2/>"
			                          +"<com:Make2>MOTO</com:Make2>"
			                          +"<com:Model2>MSCC9062</com:Model2>"
			                          +"<com:RFMACAddress/>"
			                          +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
			                      +" </com:DeviceShipped>"
			                    +"</com:DevicesShipped>"
			                     +"</com:ProductShipped>"
			                 
			               
			                 //for rma drop downs
			                +" <com:ProductShipped>"
			                    +"<com:ProductID>"+(groupid4)+"</com:ProductID>"
			                   +" <com:FulfillmentCode>"+fulfillmentcode4+"</com:FulfillmentCode>"
			                    +"<com:DevicesShipped>"
			                       +"<com:DeviceShipped>"
			                          +"<com:Name/>"
			                          +"<com:Make/>"
			                          +"<com:Model/>"
			                         +" <com:SerialNumber/>"
			                          +"<com:SerialNumber2/>"
			                         +" <com:MACAddress/>"
			                         +" <com:CMACAddress/>"
			                          +"<com:EMACAddress/>"
			                         +" <com:MTAMACAddress/>"
			                         +" <com:MasterSubsidiaryLock/>"
			                         +" <com:USBMACAddress/>"
			                          +"<com:UnitID/>"
			                          +"<com:UnitID2/>"
			                         +" <com:Condition>New</com:Condition>"
			                         +" <com:DeviceType/>"
			                          +"<com:MACAddress2/>"
			                         +" <com:Make2/>"
			                          +"<com:Model2/>"
			                          +"<com:RFMACAddress/>"
			                         +" <com:DeviceID>"+(deviceid4)+"</com:DeviceID>"
			                      +" </com:DeviceShipped>"
			                   +" </com:DevicesShipped>"
			                +" </com:ProductShipped>"
			                   
+" <com:ProductShipped>"
+"<com:ProductID>"+(groupid5)+"</com:ProductID>"
+" <com:FulfillmentCode>"+fulfillmentcode5+"</com:FulfillmentCode>"
+"<com:DevicesShipped>"
   +"<com:DeviceShipped>"
      +"<com:Name/>"
      +"<com:Make/>"
      +"<com:Model/>"
     +" <com:SerialNumber/>"
      +"<com:SerialNumber2/>"
     +" <com:MACAddress/>"
     +" <com:CMACAddress/>"
      +"<com:EMACAddress/>"
     +" <com:MTAMACAddress/>"
     +" <com:MasterSubsidiaryLock/>"
     +" <com:USBMACAddress/>"
      +"<com:UnitID/>"
      +"<com:UnitID2/>"
     +" <com:Condition>New</com:Condition>"
     +" <com:DeviceType/>"
      +"<com:MACAddress2/>"
     +" <com:Make2/>"
      +"<com:Model2/>"
      +"<com:RFMACAddress/>"
     +" <com:DeviceID>"+(deviceid5)+"</com:DeviceID>"
  +" </com:DeviceShipped>"
+" </com:DevicesShipped>"
+" </com:ProductShipped>"
			                
			              +"</com:ProductsShipped>"
			          +" </com:Shipment>"
			        +"</com:Shipments>"
			     +"</com:Request>"
			  +"</com:PostShipment>"
			+" </soapenv:Body>"
			+"</soapenv:Envelope>";
				
				

				 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid, Status.PASS);

				 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid, Status.PASS);

				 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid1, Status.PASS);

				 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid1, Status.PASS);
				 
				 report.updateTestLog("The product id for "+fulfillmentcode4, "Fetched successfully as : "+groupid4, Status.PASS);

				 report.updateTestLog("The device id for "+fulfillmentcode4, "Fetched successfully as : "+deviceid4, Status.PASS);
				 
				 report.updateTestLog("The product id for "+fulfillmentcode5, "Fetched successfully as : "+groupid5, Status.PASS);

				 report.updateTestLog("The device id for "+fulfillmentcode5, "Fetched successfully as : "+deviceid5, Status.PASS);

			

				 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device1, Status.PASS);		

				 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device2, Status.PASS);		

				 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device3, Status.PASS);		
				 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device4, Status.PASS);	
				 
				
			}
		

        
	//mpeg4 dvrdct csg cisco
		if(("VID-MOTRNG150N-MPEG4".equals(fulfillmentcode1)||"VID-MOTRNG150N-MPEG4".equals(fulfillmentcode1))&&("VID-DCX3400-MPEG4".equals(fulfillmentcode))&&"RMA-EasyReturns".equals(fulfillmentcode5)&&"RMA-DF-LABEL-01".equals(fulfillmentcode4))
			{
				System.out.println("test71");
				Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

				Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
						+"<soapenv:Header>"
						+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
						+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
						+"<wsse:Username>Contec</wsse:Username>"
						+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
			       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
			        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
			     +"</wsse:UsernameToken>"
			 +" </wsse:Security>"
			  +"<com:requestHeader>"
			     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
			    +" <com:sourceSystemId/>"
			     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
			     +"<com:sourceServerId>1</com:sourceServerId>"
			     +"<com:trackingId>1</com:trackingId>"
			 +" </com:requestHeader>"
			 +"</soapenv:Header>"
			 +"<soapenv:Body>"
			  +"<com:PostShipment>"
			     +"<com:Request>"
			        +"<com:OrderID>"+Orderid+"</com:OrderID>"
			        +"<com:Shipments>"
			           +"<com:Shipment>"
			              +"<com:Carrier>UPS</com:Carrier>"
			             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
			              +"<com:Method>Overnight</com:Method>"
			              +"<com:Packages>"
			                 +"<com:Package>"
			                   +" <com:Cost>25.34</com:Cost>"
			                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
			                   +" <com:ReturnTrackingNumber/>"
			                   +" <com:Weight>2.35</com:Weight>"
			                 +"</com:Package>"
			             +" </com:Packages>"
			                 
			             //for dvr
		                 
			             +"<com:ProductsShipped>"
		                 +" <com:ProductShipped>"
		                    +" <com:ProductID>"+groupid+"</com:ProductID>"
		             	                        
		                     +"<com:FulfillmentCode>VID-SARNG200-MPEG4</com:FulfillmentCode>"
		                     +"<com:DevicesShipped>"
		                       +" <com:DeviceShipped>"
		                         +"  <com:Name/>"
		                          +" <com:Make>SAC</com:Make>"
		                           +"<com:Model>SARN200N</com:Model>"
		                           +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
		                           +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
		                           +"<com:MACAddress/>"
		                           +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
		                           +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
		                           +"<com:MTAMACAddress/>"
		                           +"<com:MasterSubsidiaryLock/>"
		                           +"<com:USBMACAddress/>"
		                          // +"<com:UnitID/>"
		                           +"<com:UnitID/>"
		                          +" <com:UnitID2/>"
		                          +" <com:Condition>New</com:Condition>"
		                          +" <com:DeviceType>STB</com:DeviceType>"
		                           +"<com:MACAddress2/>"
		                           +"<com:Make2>SAC</com:Make2>"
		                           +"<com:Model2>MCARD</com:Model2>"
		                           +"<com:RFMACAddress>"+rfmac+"</com:RFMACAddress>"
		                           +"<com:DeviceID>"+deviceid+"</com:DeviceID>"
		                       +" </com:DeviceShipped>"
		                     +"</com:DevicesShipped>"
		                      +"</com:ProductShipped>"
		                  
		             
			                 
			             //for dct mpeg4
			             
			                +" <com:ProductShipped>"
			                   +" <com:ProductID>"+groupid1+"</com:ProductID>"
			            	                        
			                    +"<com:FulfillmentCode>VID-SARNG150-MPEG4</com:FulfillmentCode>"
			                    +"<com:DevicesShipped>"
			                      +" <com:DeviceShipped>"
			                        +"  <com:Name/>"
			                         +" <com:Make>SAC</com:Make>"
			                          +"<com:Model>SARN150N</com:Model>"
			                          +"<com:SerialNumber>"+device3+"</com:SerialNumber>"
			                          +"<com:SerialNumber2>"+device4+"</com:SerialNumber2>"
			                          +"<com:MACAddress/>"
			                          +"<com:CMACAddress>"+cmmac1+"</com:CMACAddress>"
			                          +"<com:EMACAddress>"+mtamac1+"</com:EMACAddress>"
			                          +"<com:MTAMACAddress/>"
			                          +"<com:MasterSubsidiaryLock/>"
			                          +"<com:USBMACAddress/>"
			                         // +"<com:UnitID/>"
			                         +"<com:UnitID/>"
			                         +" <com:UnitID2/>"
			                         +" <com:Condition>New</com:Condition>"
			                         +" <com:DeviceType>STB</com:DeviceType>"
			                          +"<com:MACAddress2/>"
			                          +"<com:Make2>SAC</com:Make2>"
			                          +"<com:Model2>MCARD</com:Model2>"
			                          +"<com:RFMACAddress>"+rfmac2+"</com:RFMACAddress>"
			                          +"<com:DeviceID>"+deviceid1+"</com:DeviceID>"
			                      +" </com:DeviceShipped>"
			                    +"</com:DevicesShipped>"
			                     +"</com:ProductShipped>"
			                 
			               
			                 //for rma drop downs
			                +" <com:ProductShipped>"
			                    +"<com:ProductID>"+(groupid4)+"</com:ProductID>"
			                   +" <com:FulfillmentCode>"+fulfillmentcode4+"</com:FulfillmentCode>"
			                    +"<com:DevicesShipped>"
			                       +"<com:DeviceShipped>"
			                          +"<com:Name/>"
			                          +"<com:Make/>"
			                          +"<com:Model/>"
			                         +" <com:SerialNumber/>"
			                          +"<com:SerialNumber2/>"
			                         +" <com:MACAddress/>"
			                         +" <com:CMACAddress/>"
			                          +"<com:EMACAddress/>"
			                         +" <com:MTAMACAddress/>"
			                         +" <com:MasterSubsidiaryLock/>"
			                         +" <com:USBMACAddress/>"
			                          +"<com:UnitID/>"
			                          +"<com:UnitID2/>"
			                         +" <com:Condition>New</com:Condition>"
			                         +" <com:DeviceType/>"
			                          +"<com:MACAddress2/>"
			                         +" <com:Make2/>"
			                          +"<com:Model2/>"
			                          +"<com:RFMACAddress/>"
			                         +" <com:DeviceID>"+(deviceid4)+"</com:DeviceID>"
			                      +" </com:DeviceShipped>"
			                   +" </com:DevicesShipped>"
			                +" </com:ProductShipped>"
			                   
+" <com:ProductShipped>"
+"<com:ProductID>"+(groupid5)+"</com:ProductID>"
+" <com:FulfillmentCode>"+fulfillmentcode5+"</com:FulfillmentCode>"
+"<com:DevicesShipped>"
   +"<com:DeviceShipped>"
      +"<com:Name/>"
      +"<com:Make/>"
      +"<com:Model/>"
     +" <com:SerialNumber/>"
      +"<com:SerialNumber2/>"
     +" <com:MACAddress/>"
     +" <com:CMACAddress/>"
      +"<com:EMACAddress/>"
     +" <com:MTAMACAddress/>"
     +" <com:MasterSubsidiaryLock/>"
     +" <com:USBMACAddress/>"
      +"<com:UnitID/>"
      +"<com:UnitID2/>"
     +" <com:Condition>New</com:Condition>"
     +" <com:DeviceType/>"
      +"<com:MACAddress2/>"
     +" <com:Make2/>"
      +"<com:Model2/>"
      +"<com:RFMACAddress/>"
     +" <com:DeviceID>"+(deviceid5)+"</com:DeviceID>"
  +" </com:DeviceShipped>"
+" </com:DevicesShipped>"
+" </com:ProductShipped>"
			                
			              +"</com:ProductsShipped>"
			          +" </com:Shipment>"
			        +"</com:Shipments>"
			     +"</com:Request>"
			  +"</com:PostShipment>"
			+" </soapenv:Body>"
			+"</soapenv:Envelope>";
				
				

				 report.updateTestLog("The product id for DVR device ", "Fetched successfully as : "+groupid, Status.PASS);

				 report.updateTestLog("The device id for DVR device ", "Fetched successfully as : "+deviceid, Status.PASS);

				 report.updateTestLog("The product id for DCT device ", "Fetched successfully as : "+groupid1, Status.PASS);

				 report.updateTestLog("The device id for DCT device ", "Fetched successfully as : "+deviceid1, Status.PASS);
				 
				 report.updateTestLog("The product id for "+fulfillmentcode4, "Fetched successfully as : "+groupid4, Status.PASS);

				 report.updateTestLog("The device id for "+fulfillmentcode4, "Fetched successfully as : "+deviceid4, Status.PASS);
				 
				 report.updateTestLog("The product id for "+fulfillmentcode5, "Fetched successfully as : "+groupid5, Status.PASS);

				 report.updateTestLog("The device id for "+fulfillmentcode5, "Fetched successfully as : "+deviceid5, Status.PASS);

			

				 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device1, Status.PASS);		

				 report.updateTestLog("The serial no DVR device ", "Fetched successfully as : "+device2, Status.PASS);		

				 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device3, Status.PASS);		
				 report.updateTestLog("The serial no DCT device ", "Fetched successfully as : "+device4, Status.PASS);	
				 
				
			}
		

		
		

System.out.println("--------------------------------------");
System.out.println("CIBA Request : "+this.Request);
	System.setProperty("java.protocol.handler.pkgs",
		"com.sun.net.ssl.internal.www.protocol");
Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
try {

	URL endPoint = new URL(Endpoint);
	HttpURLConnection httpConn = (HttpURLConnection) endPoint
			.openConnection();
	httpConn.setRequestProperty("Content-Type",
			"text/xml; charset=utf-8");
	httpConn.setRequestProperty("SOAPAction", "http://comcastonline.com/PostShipment");
	httpConn.setRequestMethod("POST");
	httpConn.setDoOutput(true);
	httpConn.setDoInput(true);

	ByteArrayOutputStream bout = new ByteArrayOutputStream();
	byte[] buffer = new byte[Request.length()];
	buffer = Request.getBytes();
	bout.write(buffer);
	byte[] b = bout.toByteArray();
	httpConn.setRequestProperty("Content-Length",
			String.valueOf(b.length));

	// Set the appropriate HTTP parameters.
	PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
	pw.write(Request);
	pw.flush();

	// Ready with sending the request.

	// Read the response.

	httpConn.connect();

	InputStreamReader isr = new InputStreamReader(
			httpConn.getInputStream());
	BufferedReader in = new BufferedReader(isr);

	// Write the SOAP message response to a String.
	while ((Response = in.readLine()) != null) {
		cibaResponse = Response;
	}
	//System.out.println("postCIBAResponse :" +cibaResponse);
	//System.out.println(cibaResponse);
} catch (Exception e) {
	e.printStackTrace();
}
//return cibaResponse;
System.out.println(cibaResponse);
if(cibaResponse==null)
{
	report.reportFailEvent("Response is null from the soap request", "After hitting soap request it is giving null response");
}
}



	public void HitNewRequest1() throws MalformedURLException, Exception {
		// TODO Auto-generated method stub
		
		 DeviceDetails deviceDetails= DeviceDetails
				.loadFromDatatable(dataTable);
      
         device1=deviceDetails.serialno1;
         device2=deviceDetails.serialno2;
         
          device3=deviceDetails.serialno3;
          device4=deviceDetails.serialno4;
          cmmac = deviceDetails.cmMacAddress;
          mtamac = deviceDetails.eSTBAddress;
          cmmac1 = deviceDetails.cmMacAddress1;
          mtamac1 = deviceDetails.eSTBAddress1;
          unit1=deviceDetails.unit1;
          unit2=deviceDetails.unit2;
          rfmac=deviceDetails.rfmac;
          device5=deviceDetails.serialno5;
          device6=deviceDetails.serialno6;
          unit3=deviceDetails.unit3;
          cmmac2=deviceDetails.cmMacAddress2;
          mtamac2=deviceDetails.eSTBAddress2;
          rfmac2=deviceDetails.rfmac2;
          
          System.out.println(device6);
			String Orderid=""; 
			int groupid=0;
			int tempgroupid=0;
			int groupid1=0;
			int tempgroupid1;
			int groupid2 = 0;
			int deviceid2 = 0;
			String fulfillmentcode=null;
			String fulfillmentcode1 = null;
			String fulfillmentcode3 = null;
			int deviceid=0;
			int deviceid1 = 0;
			String productid;
			String productid1;
			
			String deviceid3 = null;
			String groupid3 = null;
			String deviceid4 = null;
			String groupid4 = null;
			String deviceid5 = null;
			String groupid5 = null;
			String deviceid6 = null;
			String groupid6= null;
		  
			
		
		  
		
		    
		    
		    
			  String Host ="jdbc:sqlserver://sikdb-dt-1i.cable.comcast.com;port=8152";
			  Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			  Connection conn = DriverManager.getConnection(Host,"offshore", "pass2012");
			  System.out.println("Connection success");
			  CustomerDetails customerDetails = CustomerDetails
						.loadFromDatatable(dataTable);

			  String query="Select OnlineOrdersID from sikoms.dbo.OnlineOrdersAccountInfo where Other_Raw_Acct='"+customerDetails.accountNumber+"'";
			                              //Select OrderID from sikoms.dbo.sikutilitydata where AccountNumber='8210100200601828' order by OrderID Desc";  
			  //Connection conn = DriverManager.getConnection("jdbc:sqlserver://10.252.112.168;port=8152","offshore", "pass2012");
			  PreparedStatement statement=conn.prepareStatement(query);
			  //Statement statement = conn.createStatement();
			  ResultSet rs = statement.executeQuery();
			 while(rs.next())
			  {
			                Orderid=rs.getString("OnlineOrdersID");
			                //Orderid1=Integer.parseInt(Orderid);
			  System.out.println("Order Id"+Orderid);
	             report.updateTestLog("Fetching SIK order ID ", "SIK order ID : "+Orderid, Status.DONE);

			  }
			  


				String query1="SELECT DISTINCT OOPCD.GROUPID,OOPCD.ID, FG.FULFILLMENTCODE FROM SIKOMS.SIKOMS.ONLINEORDERSPRODUCTCODEDEVICES OOPCD,SIKOMS.SIKOMS.ONLINEORDERSFINISHEDGOODS OOFG, SIKOMS.SIKOMS.FINISHEDGOODS FG WHERE OOPCD.ONLINEORDERSPRODUCTCODESID = OOFG.ONLINEORDERSPRODUCTCODESID AND OOFG.FINISHEDGOODID = FG.ID AND OOFG.PRIORITY = 3 AND OOPCD.ONLINEORDERSPRODUCTCODESID IN (SELECT ID FROM SIKOMS.SIKOMS.ONLINEORDERSPRODUCTCODES WHERE ONLINEORDERSID IN ('"+Orderid+"')) ORDER BY OOPCD.GROUPID";
			  PreparedStatement statement1=conn.prepareStatement(query1);
			  ResultSet rs1 = statement1.executeQuery();
			  ArrayList<String> list = new ArrayList<String>();
			  while(rs1.next())
			  {
				  list.add(rs1.getString("GROUPID"));
				  list.add(rs1.getString("FULFILLMENTCODE")); 
				  list.add(rs1.getString("ID")); 
				 
				  			  
			 
			  }
			
			 String [] itemnameArray = new String [list.size()];

	            list.toArray(itemnameArray);
	            System.out.println(list.size());
                  for (int i=0;i<list.size();i++)
                  {
	            System.out.println("test:"+itemnameArray[i]);
                  }
                  Endpoint = "http://siktoolsint.cable.comcast.com/sikoms/11.04/fulfillment.asmx";

  				Request = "<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"
  						+"<soapenv:Header>"
  						+"<wsse:Security soapenv:mustUnderstand=\"1\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
  						+" <wsse:UsernameToken wsu:Id=\"UsernameToken-3207045\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"
  						+"<wsse:Username>Contec</wsse:Username>"
  						+"<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">gdgCiq4Ef2HTM+BvQFp9kQ==</wsse:Password>"
  			       +" <wsse:Nonce>m7Bq6dOQcEdXmR43mc430Q==</wsse:Nonce>"
  			        +"<wsu:Created>2010-03-23T17:17:00.723Z</wsu:Created>"
  			     +"</wsse:UsernameToken>"
  			 +" </wsse:Security>"
  			  +"<com:requestHeader>"
  			     +"<com:timestamp>2010-03-16T18:28:36Z</com:timestamp>"
  			    +" <com:sourceSystemId/>"
  			     +"<com:sourceSystemUserId>1</com:sourceSystemUserId>"
  			     +"<com:sourceServerId>1</com:sourceServerId>"
  			     +"<com:trackingId>1</com:trackingId>"
  			 +" </com:requestHeader>"
  			 +"</soapenv:Header>"
  			 +"<soapenv:Body>"
  			  +"<com:PostShipment>"
  			     +"<com:Request>"
  			        +"<com:OrderID>"+Orderid+"</com:OrderID>"
  			        +"<com:Shipments>"
  			           +"<com:Shipment>"
  			              +"<com:Carrier>UPS</com:Carrier>"
  			             +" <com:Date>2010-04-06T18:34:04Z</com:Date>"
  			              +"<com:Method>Overnight</com:Method>"
  			              +"<com:Packages>"
  			                 +"<com:Package>"
  			                   +" <com:Cost>25.34</com:Cost>"
  			                   +" <com:TrackingNumber>1HZSE657843898734287</com:TrackingNumber>"
  			                   +" <com:ReturnTrackingNumber/>"
  			                   +" <com:Weight>2.35</com:Weight>"
  			                 +"</com:Package>"
  			             +" </com:Packages>"
  			                 
  			             //for dct mpeg4
  			              +"<com:ProductsShipped>"
  			                +" <com:ProductShipped>"
  			                   +" <com:ProductID>"+itemnameArray[0]+"</com:ProductID>"
  			            	                        
  			                    +"<com:FulfillmentCode>"+itemnameArray[1]+"</com:FulfillmentCode>"
  			                    +"<com:DevicesShipped>"
  			                      +" <com:DeviceShipped>"
  			                        +"  <com:Name/>"
  			                         +" <com:Make>SAM</com:Make>"
  			                          +"<com:Model>SR150BNM</com:Model>"
  			                          +"<com:SerialNumber>"+device1+"</com:SerialNumber>"
  			                          +"<com:SerialNumber2>"+device2+"</com:SerialNumber2>"
  			                          +"<com:MACAddress/>"
  			                          +"<com:CMACAddress>"+cmmac+"</com:CMACAddress>"
  			                          +"<com:EMACAddress>"+mtamac+"</com:EMACAddress>"
  			                          +"<com:MTAMACAddress/>"
  			                          +"<com:MasterSubsidiaryLock/>"
  			                          +"<com:USBMACAddress/>"
  			                          +"<com:UnitID>"+unit1+"</com:UnitID>"
  			                         +" <com:UnitID2/>"
  			                         +" <com:Condition>New</com:Condition>"
  			                         +" <com:DeviceType>STB</com:DeviceType>"
  			                          +"<com:MACAddress2/>"
  			                          +"<com:Make2>MOTO</com:Make2>"
  			                          +"<com:Model2>MSCC9063</com:Model2>"
  			                          +"<com:RFMACAddress/>"
  			                          +"<com:DeviceID>"+itemnameArray[2]+"</com:DeviceID>"
  			                      +" </com:DeviceShipped>"
  			                    +"</com:DevicesShipped>"
  			                     +"</com:ProductShipped>"
  			                 
  			                  
  			                 //for rma drop downs
  			             
  			                 +"<com:ProductShipped>"
  			                   +" <com:ProductID>"+itemnameArray[3]+"</com:ProductID>"
  			                    +"<com:FulfillmentCode>"+itemnameArray[4]+"</com:FulfillmentCode>"
  			                   +" <com:DevicesShipped>"
  			                       +"<com:DeviceShipped>"
  			                         +" <com:Name/>"
  			                          +"<com:Make/>"
  			                          +"<com:Model/>"
  			                          +"<com:SerialNumber/>"
  			                          +"<com:SerialNumber2/>"
  			                          +"<com:MACAddress/>"
  			                          +"<com:CMACAddress/>"
  			                          +"<com:EMACAddress/>"
  			                          +"<com:MTAMACAddress/>"
  			                          +"<com:MasterSubsidiaryLock/>"
  			                          +"<com:USBMACAddress/>"
  			                          +"<com:UnitID/>"
  			                         +" <com:UnitID2/>"
  			                          +"<com:Condition>New</com:Condition>"
  			                          +"<com:DeviceType/>"
  			                          +"<com:MACAddress2/>"
  			                          +"<com:Make2/>"
  			                         +" <com:Model2/>"
  			                          +"<com:RFMACAddress/>"
  			                          +"<com:DeviceID>"+itemnameArray[5]+"</com:DeviceID>"
  			                    +"   </com:DeviceShipped>"
  			                   +" </com:DevicesShipped>"
  			                 +"</com:ProductShipped>"
  			              +"</com:ProductsShipped>"
  			          +" </com:Shipment>"
  			        +"</com:Shipments>"
  			     +"</com:Request>"
  			  +"</com:PostShipment>"
    +" </soapenv:Body>"
  +"</soapenv:Envelope>";


  				System.out.println("--------------------------------------");
  				System.out.println("CIBA Request : "+this.Request);
  					System.setProperty("java.protocol.handler.pkgs",
  						"com.sun.net.ssl.internal.www.protocol");
  				Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
  				try {

  					URL endPoint = new URL(Endpoint);
  					HttpURLConnection httpConn = (HttpURLConnection) endPoint
  							.openConnection();
  					httpConn.setRequestProperty("Content-Type",
  							"text/xml; charset=utf-8");
  					httpConn.setRequestProperty("SOAPAction", "http://comcastonline.com/PostShipment");
  					httpConn.setRequestMethod("POST");
  					httpConn.setDoOutput(true);
  					httpConn.setDoInput(true);

  					ByteArrayOutputStream bout = new ByteArrayOutputStream();
  					byte[] buffer = new byte[Request.length()];
  					buffer = Request.getBytes();
  					bout.write(buffer);
  					byte[] b = bout.toByteArray();
  					httpConn.setRequestProperty("Content-Length",
  							String.valueOf(b.length));

  					// Set the appropriate HTTP parameters.
  					PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
  					pw.write(Request);
  					pw.flush();

  					// Ready with sending the request.

  					// Read the response.

  					httpConn.connect();

  					InputStreamReader isr = new InputStreamReader(
  							httpConn.getInputStream());
  					BufferedReader in = new BufferedReader(isr);

  					// Write the SOAP message response to a String.
  					while ((Response = in.readLine()) != null) {
  						cibaResponse = Response;
  					}
  					//System.out.println("postCIBAResponse :" +cibaResponse);
  					//System.out.println(cibaResponse);
  				} catch (Exception e) {
  					e.printStackTrace();
  				}
  				//return cibaResponse;
  				System.out.println(cibaResponse);
  				if(cibaResponse==null)
  				{
  					report.reportFailEvent("Response is null from the soap request", "After hitting soap request it is giving null response");
  				}
  				

			  }
	
	

	}

