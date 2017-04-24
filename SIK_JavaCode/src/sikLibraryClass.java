import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import oracle.sql.CHAR;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;
import java.util.regex.*;


public class sikLibraryClass 
{
  
	public String basemsgID = null;
    public Connection connection = null;
	public String xml1;
	public String xml2;
	//public String rowmsg= null;
	public String resultval;
	
	
	
	
	public void Opconnection()
	{
		System.out.println("-------- Oracle JDBC Connection Testing ------");
		Application.showMessage("-------- Oracle JDBC Connection Testing ------");
		
		 
		try 
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//Class.forName("jdbc:oracle:thin.OracleDriver");	
		} 
		catch (ClassNotFoundException e) 
		{
			System.out.println("Connection Failed as No JDBC Driver Intialized");
			e.printStackTrace();
			return;
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
		//Connection connection = null;
		
	}
	 public String getBaseTime(Object input, ScriptingContext c) throws InterruptedException
	   {
		      String Time;
		      Date todayDate = new Date();
			  TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
			  DateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
			  todayDateFormat.setTimeZone(timeZone);
			  Time=todayDateFormat.format(todayDate);
			  c.put("BaseTime",Time.trim());
			  Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
			  Thread.sleep(2000);
			  return Time;
	   }
	public void EnvironmentErrorReport(Object input , ScriptingContext c, String CallName)
	{
		c.report("The response is Null and webservice .."+CallName+" is down");
		c.getDataSourceRowIndex();
	}
	public Statement dBConnect(Object input , ScriptingContext c) throws ClassNotFoundException, SQLException, IOException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		String host = c.getValue("DB_Host");
		String password = c.getValue("dB_Password");
		String username = c.getValue("dB_Username");
		String port = c.getValue("dB_Port");
		String sid = c.getValue("dB_serviceName");
		
		
		connection = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port+":"+sid +"", username,password);
	    
		Statement st = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			    ResultSet.CONCUR_READ_ONLY);
		return st;
	}
	
	
	public Statement CommondBConnect(Object input , ScriptingContext c) throws ClassNotFoundException, SQLException, IOException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		String host = c.getValue("DB_Host");
		String password = c.getValue("dB_PasswordCommon");
		String username = c.getValue("dB_UsernameCommon");
		String port = c.getValue("dB_Port");
		String sid = c.getValue("dB_serviceName");
		
		
		connection = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port+":"+sid +"", username,password);
	    
		Statement st = connection.createStatement();
		return st;
	}
    public Statement dBConnectCommon(Object input , ScriptingContext c) throws ClassNotFoundException, SQLException, IOException
    {
           Class.forName("oracle.jdbc.driver.OracleDriver");
          
    
           
           //connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1", "CWDEVOP","CWDEVOP");      
           
           //connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.33:1521:psymqa1", "CWQA1OP","qa1opw1nt3r");
           String host = c.getValue("dB_CommonHost");
   		String password = c.getValue("dB_PasswordCommon");
   		String username = c.getValue("dB_UsernameCommon");
   		String port = c.getValue("dB_CommonPort");
   		String sid = c.getValue("dB_serviceCommon");
          
           Application.showMessage("Debug statement"+host+""+password+""+username+""+port+""+sid);
           
           connection = DriverManager.getConnection("jdbc:oracle:thin:@"+host+":"+port+":"+sid +"", username,password);
           Statement st1 = connection.createStatement();
           
           return st1;
    }
    
    //-----
  //--Yamini's --Dynamic way of Handling Think Time--
	
  	public ResultSet reduceThinkTimeSIK(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, SQLException, IOException
  	{
  		sikLibraryClass  lC= new sikLibraryClass ();
  		int count=0;
  	     int waitingtime=0;
  	     int thinktime=0;
  		 ResultSet  rs;
  		 Statement st;
  	     String Time= c.get("BaseTime").toString();
  	     Application.showMessage("Time:::"+Time);
  	     Application.showMessage("Operation is:::"+c.getValue("OPERATIONVALIDATION"));
  	     do
  			{
  				Thread.sleep(1000);
  		    st =lC.dBConnect(input, c)	;
  	        
  			//rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ContractServices:ContractServicePort/queryContract' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
  		    if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("eep:XHSEEPOrder/xhsevent")))
  		    		{
  		    	rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >= '"+Time+"' order by creation_time desc)where rownum <= 20");
  				
  		    		}
  		    else
  		    {
  		    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
  		    }
  			
  		//	Application.showMessage("select * from (select * from cwmessagelog where operation = '"+c.getValue("OPERATIONVALIDATION")+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >= '"+Time+"' order by creation_time desc)where rownum <= 20");
  	    
  			

  			if(!rs.isBeforeFirst())
  	         {
  	        	
  				//Application.showMessage("Yes");
  				waitingtime++;
  				count++;
  				
  	         }		
  					else
  					{//Application.showMessage("Yamini1");
  						break;
  					}
  			
  			
  			
  			
  			st.close();
  			
  	         }while(count < 120);
  	     int timing=waitingtime + 1;
  			Application.showMessage("Waited for "+timing+"seconds");
  			return rs;
  	}
  	
    
    public HashMap findingoperations(Object input,ScriptingContext c)
    {
    	HashMap map = new HashMap();
    	 map.put("bfcRequest","op:orderProcessorServicePort/bfcRequest");
    	 map.put("getLocation","ls:LocationServicePort/getLocation");
    	 map.put("queryContract","ContractServices:ContractServicePort/queryContract");
    	 map.put("SubmitOrder","sik:OrderSoap/SubmitOrder");
    	 map.put("modifyServiceableLocation","cs:CustomerServicePort/modifyServiceableLocation");
    	 map.put("OneStopSIK","op:oneStopSik/processOneStopSIK");
    	 map.put("orderUpdate","cmb:commonOrderService/orderUpdate");
    	 map.put("confirmServiceRequest","ofs:OfferMgmtServicePort/confirmServiceRequest");
    	 map.put("GetOrderDetails","sik:OrderSoap/GetOrderDetails");
    	 map.put("ModifyOrder","sik:OrderSoap/ModifyOrder");
    	 map.put("CSGENIMessage","op:CSGENIMessageServicePort/processCSGENIMessage");
    	 map.put("queryLocation","ls:LocationServicePort/queryLocation");
    	 map.put("comtracRequest","op:orderProcessorServicePort/comtracRequest");
    	 map.put("getHouseInfo","HTTPConnectorClientDDP:HttpConnectorServiceClientDDP/getHouseInfo");
    	 map.put("getWipInfo","HTTPConnectorClientDDP:HttpConnectorServiceClientDDP/getWipInfo");
    	 map.put("getCustomerInfo","HTTPConnectorClientDDP:HttpConnectorServiceClientDDP/getCustomerInfo");
    	 map.put("OrderSearch","sik:OrderSoap/OrderSearch");
    	 map.put("CancelOrder","sik:OrderSoap/CancelOrder");
    	 return map;
    }
    
    
    //-----

    public String CancelXML(Object input , ScriptingContext c) throws ClassNotFoundException, SQLException, IOException
	{
    	String xml = null;
    	String bfcPassword="h-8E2P!fN+n40aI$/D9oBt=7!J1r3g";
    	String CancelOrderID=(String) c.get("OrderID_SIK");
    	Application.showMessage("The Cancel order ID is::"+CancelOrderID);
    	
    	xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
    	"<soapenv:Envelope xmlns:com=\"http://comcastonline.com/\""+
    	   "xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\">"+
    	 "<soapenv:Header>"+
    	  "<com:requestHeader>"+
    	   "<com:timestamp>2012-10-23T14:50:31-04:00</com:timestamp>"+
    	   "<com:sourceSystemId>1</com:sourceSystemId>"+
    	   "<com:sourceSystemUserId>1</com:sourceSystemUserId>"+
    	   "<com:sourceServerId>1</com:sourceServerId>"+
    	   "<com:trackingId>1</com:trackingId>"+
    	   "</com:requestHeader>"+
    	   "<wsse:Security"+
    	   "xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\""+
    	    "xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\">"+
    	   "<wsse:UsernameToken"+
    	     "xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"+
    	    "<wsse:Username>BFC</wsse:Username>"+
    	    "<wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">"+bfcPassword+"</wsse:Password>"+
    	   "</wsse:UsernameToken>"+
    	  "</wsse:Security>"+
    	 "</soapenv:Header>"+
    	 "<soapenv:Body>"+
    	  "<com:CancelOrder>"+
    	   "<com:Request>"+
    	    "<com:OrderID>"+CancelOrderID+"</com:OrderID>"+
    	   "</com:Request>"+
    	  "</com:CancelOrder>"+
    	 "</soapenv:Body>"+
    	"</soapenv:Envelope>";
    	
		 return xml;
		
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
            String ProductCode1=c.getValue("dS_SIK", "Sik_Dotcom: ProductCode");
            String ProductCode2=c.getValue("dS_SIK", "Sik_Dotcom: ProductCode1");
            String InputLocation_ID = c.getValue("dS_SIK", "Sik_Dotcom: Location_ID");
            String InputorderEntryOption = c.getValue("dS_SIK", "Sik_Dotcom: orderEntryOption");
            String InputAcquisitionMethod = c.getValue("dS_SIK", "Sik_Dotcom: Acquisition Method");
            String InputordType = c.getValue("dS_SIK", "Sik_Dotcom: ordType");
            String InputsignatureReq = c.getValue("dS_SIK", "Sik_Dotcom: signatureReq");
            String InputBillingSystem = c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
            
            
            
           // Application.showMessage("ProductCode1::"+ProductCode1);
           // Application.showMessage("ProductCode2::"+ProductCode2);
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
  		    c.setValue("acquisitionMethod", InputAcquisitionMethod);
  		    c.setValue("orderEntryOption", InputorderEntryOption);
  		    c.setValue("locationID", InputLocation_ID);
  		    c.setValue("BillingSystem", InputBillingSystem);
  		    c.setValue("signatureReq", InputsignatureReq);
  		    
  		  Application.showMessage("InputAccountNumber is ::"+InputAccountNumber);
  		  Application.showMessage("InputserviceReqID is ::"+InputserviceReqID);
  		  Application.showMessage("InputordType is ::"+InputordType);
  		  Application.showMessage("InputAcquisitionMethod is ::"+InputAcquisitionMethod);
  		  Application.showMessage("InputorderEntryOption is ::"+InputorderEntryOption);
  		  Application.showMessage("InputLocation_ID is ::"+InputLocation_ID);
  		  Application.showMessage("InputBillingSystem is ::"+InputBillingSystem);
  		  Application.showMessage("InputsignatureReq is ::"+InputsignatureReq);
  		    
  		    
		    
	}
	
	
	public void readValuesfromsikXhsExcel(Object input, ScriptingContext c)throws IOException
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
            String InputAcquisitionMethod = c.getValue("dS_SIK", "Sik_Dotcom: acquisitionMethod");
            String InputordType = c.getValue("dS_SIK", "Sik_Dotcom: ordType");
            String InputsignatureReq = c.getValue("dS_SIK", "Sik_Dotcom: signatureReq");
            String InputBillingSystem = c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
            
            String customerID = c.getValue("dS_SIK", "Sik_Dotcom: customerID");
            String HSDProdcut = c.getValue("dS_SIK", "Sik_Dotcom: HSDProdcut");
            String VideoProduct = c.getValue("dS_SIK", "Sik_Dotcom: VideoProduct");
            String Service = c.getValue("dS_SIK", "Sik_Dotcom: Service");
            String XHSService = c.getValue("dS_SIK", "Sik_Dotcom: XHSService");
            String XHSFeature = c.getValue("dS_SIK", "Sik_Dotcom: XHSFeature");
            String XHSequipment = c.getValue("dS_SIK", "Sik_Dotcom: XHSequipment");
            String CDVProduct = c.getValue("dS_SIK", "Sik_Dotcom: CDVProduct");
            String XHSorderEntry = c.getValue("dS_SIK", "Sik_Dotcom: XHSorderEntry");
            
            c.put("customerID", customerID);
            c.put("HSDProdcut", HSDProdcut);
            c.put("VideoProduct", VideoProduct);
            c.put("Service", Service);
            c.put("XHSService", XHSService);
            c.put("XHSFeature", XHSFeature);
            c.put("XHSequipment", XHSequipment);
            c.put("CDVProduct", CDVProduct); 
            c.put("XHSorderEntry", XHSorderEntry);
            
                      
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
  		    c.setValue("acquisitionMethod", InputAcquisitionMethod);
  		    c.setValue("orderEntryOption", InputorderEntryOption);
  		    c.setValue("locationID", InputLocation_ID);
  		    c.setValue("BillingSystem", InputBillingSystem);
  		    c.setValue("signatureReq", InputsignatureReq);
  		    
  		  Application.showMessage("InputAccountNumber is ::"+InputAccountNumber);
  		  Application.showMessage("InputserviceReqID is ::"+InputserviceReqID);
  		  Application.showMessage("InputordType is ::"+InputordType);
  		  Application.showMessage("InputAcquisitionMethod is ::"+InputAcquisitionMethod);
  		  Application.showMessage("InputorderEntryOption is ::"+InputorderEntryOption);
  		  Application.showMessage("InputLocation_ID is ::"+InputLocation_ID);
  		  Application.showMessage("InputBillingSystem is ::"+InputBillingSystem);
  		  Application.showMessage("InputsignatureReq is ::"+InputsignatureReq);
  		  Application.showMessage("customerID is ::"+c.get("customerID"));
  		  Application.showMessage("HSDProdcut is ::"+c.get("HSDProdcut"));
  		  Application.showMessage("VideoProduct is ::"+c.get("VideoProduct"));
  		  Application.showMessage("Service is ::"+c.get("Service"));
  		  Application.showMessage("XHSService is ::"+c.get("XHSService"));
  		  Application.showMessage("XHSFeature is ::"+c.get("XHSFeature"));
  		  Application.showMessage("XHSequipment is ::"+c.get("XHSequipment"));
  		  Application.showMessage("CDVProduct is ::"+c.get("CDVProduct"));
  		  Application.showMessage("XHSorderEntry is ::"+c.get("XHSorderEntry"));


  		    
  		    
		    
	}

	
	
	public void readValuesfromswivelCSGExcel(Object input, ScriptingContext c)throws IOException
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
		    String dB_serviceCommon=c.getValue("SwivelCSG", "Swivel_csgDBSettings: Common_ServiceName");
		    String dB_CommonPort=c.getValue("SwivelCSG", "Swivel_csgDBSettings: Common_DBPort");
		    String dB_CommonHost=c.getValue("SwivelCSG", "Swivel_csgDBSettings: Common_DBHost");
		    
            String InputAccountNumber = c.getValue("SwivelCSG", "sik_SwivelCSG: AccountId_ITEMLIST");
            String InputOrderID = c.getValue("SwivelCSG", "sik_SwivelCSG: OrderID");
            String InputLocation_ID = c.getValue("SwivelCSG", "sik_SwivelCSG: LocationId");
            String InputEmail = c.getValue("SwivelCSG", "sik_SwivelCSG: Email");
            String InputFname = c.getValue("SwivelCSG", "sik_SwivelCSG: Fname");
            String InputLname = c.getValue("SwivelCSG", "sik_SwivelCSG: Lname");
            String InputcustType = c.getValue("SwivelCSG", "sik_SwivelCSG: custType");
            String InputCustomerId = c.getValue("SwivelCSG", "sik_SwivelCSG: CustomerId");
            String InputTN = c.getValue("SwivelCSG", "sik_SwivelCSG: TN");
            String isModify=c.getValue("SwivelCSG", "sik_SwivelCSG: MODIFYCALL");
            c.put("isModify", isModify);
            Application.showMessage("IsModify is ::"+isModify);
            String companyname=c.getValue("SwivelCSG", "sik_SwivelCSG: companyname");
            Application.showMessage("Companyname::"+companyname);
            c.setValue(companyname, companyname);
            
            
            
            
            
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
  		    c.setValue("dB_serviceCommon", dB_serviceCommon);
  		    c.setValue("dB_CommonPort", dB_CommonPort);
  		    c.setValue("dB_CommonHost", dB_CommonHost);
  		    
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
  		    
  		    
		    
	}
	
	
	
	
	
	public void readValuesfromComtracDDPExcel(Object input, ScriptingContext c)throws IOException
	{
		
		//
		 Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
		 
		    String XML_EndPoint=c.getValue("SwivelDDP", "Swivel_csgDBSettings: XML_Enpoint");
		    String DB_Host=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_HOST");
		    String dB_Password=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_Password");
		    String dB_Username=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_UserName");
		    String dB_Port=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_Port");
		    String dB_serviceName=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_ServiceName");
		    String dB_connectionName=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_ConnectionName");
		    String DB_Hostcommon=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_HOST_common");
		    String dB_Portcommon=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_Port_common");
		    String dB_serviceNamecommon=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_ServiceName_Common");
		    String dB_connectionNamecommon=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_ConnectionName");
		    String dB_PasswordCommon=c.getValue("SwivelDDP", "Swivel_csgDBSettings: Common_DBPassword");
		    String dB_UsernameCommon=c.getValue("SwivelDDP", "Swivel_csgDBSettings: Common_DBUserName");
		    String corp1=c.getValue("SwivelDDP", "ComtracParameterList: corp");
		    String houseno=c.getValue("SwivelDDP", "ComtracParameterList: houseno");
		    String custID=c.getValue("SwivelDDP", "ComtracParameterList: CustID");
		    /*String scenariotype=c.getValue("SwivelDDP", "ComtracParameterList: ScenarioType");
		    Application.showMessage("scenariotype::"+scenariotype);*/
		    String accountnumber=corp1.concat(houseno);
		    String accountNumber2=accountnumber.concat(custID);
		    Application.showMessage("accountNumber2::" +accountNumber2);
		    Application.showMessage("accountNumber1::" +accountnumber);
		    
		    Application.showMessage("Endpoint is ::"+XML_EndPoint);
		    Application.showMessage("DB_HOST is ::"+DB_Host);
		    Application.showMessage("dB_Password is ::"+dB_Password);
		    Application.showMessage("dB_Username is ::"+dB_Username);
		    Application.showMessage("dB_Port is ::"+dB_Port);
		    Application.showMessage("dB_serviceName is ::"+dB_serviceName);
		    Application.showMessage("dB_connectionName is ::"+dB_connectionName);
		    Application.showMessage("Common DB Username is ::"+dB_UsernameCommon);
		    Application.showMessage("Common DB Password is ::"+dB_PasswordCommon);
		    c.setValue("DB_Host", DB_Host);
  		    c.setValue("dB_Password",dB_Password);
  		    c.setValue("dB_Username",dB_Username);
  		    c.setValue("dB_Port",dB_Port);
  		    c.setValue("dB_serviceName",dB_serviceName);
  		    c.setValue("XML_EndPoint",XML_EndPoint);
  		    c.setValue("dB_PasswordCommon",dB_PasswordCommon);
  		    c.setValue("dB_UsernameCommon",dB_UsernameCommon);
  		  c.setValue("dB_HostCommon",DB_Hostcommon);
  		  c.setValue("accountNumber", accountNumber2);
		    c.setValue("dB_portCommon",dB_Portcommon);
		    c.setValue("dB_servicenameCommon",dB_serviceNamecommon);
  		   
		    String corp=c.getValue("SwivelDDP", "ComtracParameterList: corp");
		    if(corp.equals("1790"))
		    {
		    	c.put("pCorp", "01790");
		    }
		    else
		    {
		    	c.put("pCorp", c.getValue("SwivelDDP", "ComtracParameterList: corp"));
		    }
		    
		  
            c.put("pRequestType", c.getValue("SwivelDDP", "ComtracParameterList: reqType"));
           // c.put("pCorp", c.getValue("SwivelDDP", "ComtracParameterList: corp"));
            c.put("pHouseNo", c.getValue("SwivelDDP", "ComtracParameterList: houseno"));
            c.put("pCustID", c.getValue("SwivelDDP", "ComtracParameterList: CustID"));
            c.put("pOpr", c.getValue("SwivelDDP", "ComtracParameterList: opr"));
            c.put("pCorp1", c.getValue("SwivelDDP", "ComtracParameterList: corp1"));
            c.put("pHouseNo1", c.getValue("SwivelDDP", "ComtracParameterList: houseno1"));
            c.put("pCustID1", c.getValue("SwivelDDP", "ComtracParameterList: custid1"));
            c.put("pFName", c.getValue("SwivelDDP", "ComtracParameterList: fname"));
            c.put("pLName", c.getValue("SwivelDDP", "ComtracParameterList: lname"));
            c.put("pAreaCode", c.getValue("SwivelDDP", "ComtracParameterList: areaCode"));
            c.put("pPhone", c.getValue("SwivelDDP", "ComtracParameterList: phone"));
            c.put("pEmail", c.getValue("SwivelDDP", "ComtracParameterList: email"));
            c.put("pWRateCode", c.getValue("SwivelDDP", "ComtracParameterList: wor_rate_Code"));
            c.put("pKRateCode1", c.getValue("SwivelDDP", "ComtracParameterList: KitRate_Code1"));
            c.put("pKRateCode2", c.getValue("SwivelDDP", "ComtracParameterList: KitRate_Code2"));
            c.put("pKRateCode3", c.getValue("SwivelDDP", "ComtracParameterList: KitRate_Code3"));
            c.put("pBillingOrdID", c.getValue("SwivelDDP", "ComtracParameterList: BillingOrdID"));
            c.put("pIsHttpCon", c.getValue("SwivelDDP", "ComtracParameterList: IsHttpCon"));
            c.put("pTestCaseName", c.getValue("SwivelDDP", "ComtracParameterList: TestCaseName"));
  

             
		   
		    
		    
		    Application.showMessage("pRequestType is ::"+c.get("pRequestType"));
		    Application.showMessage("pCorp is ::"+c.get("pCorp"));
		    Application.showMessage("pHouseNo is ::"+c.get("pHouseNo"));
		    Application.showMessage("pCustID is ::"+c.get("pCustID"));
		    Application.showMessage("pOpr is ::"+c.get("pOpr"));
		    Application.showMessage("pCorp1 is ::"+c.get("pCorp1"));
		    Application.showMessage("pHouseNo1 is ::"+c.get("pHouseNo1"));
		    Application.showMessage("pCustID1 is ::"+c.get("pCustID1"));
		    Application.showMessage("pFName is ::"+c.get("pFName"));
		    Application.showMessage("pLName is ::"+c.get("pLName"));
		    Application.showMessage("pAreaCode is ::"+c.get("pAreaCode"));
		    Application.showMessage("pPhone is ::"+c.get("pPhone"));
		    Application.showMessage("pEmail is ::"+c.get("pEmail"));
		    Application.showMessage("pWRateCode is ::"+c.get("pWRateCode"));
		    Application.showMessage("pKRateCode1 is ::"+c.get("pKRateCode1"));
		    Application.showMessage("pKRateCode2 is ::"+c.get("pKRateCode2"));
		    Application.showMessage("pKRateCode3 is ::"+c.get("pKRateCode3"));
		    Application.showMessage("pBillingOrdID is ::"+c.get("pBillingOrdID"));
		    Application.showMessage("pIsHttpCon is ::"+c.get("pIsHttpCon"));
		    Application.showMessage("pTestCaseName is ::"+c.get("pTestCaseName"));
		   
  		       
	}	
	
	
	public void readValuesfromComtracDDPXHSExcel(Object input, ScriptingContext c)throws IOException
	{
		
		// c.clear();
		 Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
		 
		    String XML_EndPoint=c.getValue("SwivelDDP", "Swivel_csgDBSettings: XML_Enpoint");
		    String DB_Host=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_HOST");
		    String dB_Password=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_Password");
		    String dB_Username=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_UserName");
		    String dB_Port=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_Port");
		    String dB_serviceName=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_ServiceName");
		    String dB_connectionName=c.getValue("SwivelDDP", "Swivel_csgDBSettings: DB_ConnectionName");
		    String dB_PasswordCommon=c.getValue("SwivelDDP", "Swivel_csgDBSettings: Common_DBPassword");
		    String dB_UsernameCommon=c.getValue("SwivelDDP", "Swivel_csgDBSettings: Common_DBUserName");
		    
		    
		    Application.showMessage("Endpoint is ::"+XML_EndPoint);
		    Application.showMessage("DB_HOST is ::"+DB_Host);
		    Application.showMessage("dB_Password is ::"+dB_Password);
		    Application.showMessage("dB_Username is ::"+dB_Username);
		    Application.showMessage("dB_Port is ::"+dB_Port);
		    Application.showMessage("dB_serviceName is ::"+dB_serviceName);
		    Application.showMessage("dB_connectionName is ::"+dB_connectionName);
		    Application.showMessage("Common DB Username is ::"+dB_UsernameCommon);
		    Application.showMessage("Common DB Password is ::"+dB_PasswordCommon);
		    c.setValue("DB_Host", DB_Host);
  		    c.setValue("dB_Password",dB_Password);
  		    c.setValue("dB_Username",dB_Username);
  		    c.setValue("dB_Port",dB_Port);
  		    c.setValue("dB_serviceName",dB_serviceName);
  		    c.setValue("XML_EndPoint",XML_EndPoint);
  		    c.setValue("dB_PasswordCommon",dB_PasswordCommon);
  		    c.setValue("dB_UsernameCommon",dB_UsernameCommon);
		    
		  
            c.put("pRequestType", c.getValue("SwivelDDP", "ComtracParameterList: reqType"));
            c.put("pCorp", c.getValue("SwivelDDP", "ComtracParameterList: corp"));
            c.put("pHouseNo", c.getValue("SwivelDDP", "ComtracParameterList: houseno"));
            c.put("pCustID", c.getValue("SwivelDDP", "ComtracParameterList: custid"));
           // c.put("pOpr", c.getValue("SwivelDDP", "ComtracParameterList: opr"));
            c.put("pCorp1", c.getValue("SwivelDDP", "ComtracParameterList: corp1"));
            c.put("pHouseNo1", c.getValue("SwivelDDP", "ComtracParameterList: houseno1"));
            c.put("pCustID1", c.getValue("SwivelDDP", "ComtracParameterList: custid1"));
            c.put("pFName", c.getValue("SwivelDDP", "ComtracParameterList: fname"));
            c.put("pLName", c.getValue("SwivelDDP", "ComtracParameterList: lname"));
            c.put("pAreaCode", c.getValue("SwivelDDP", "ComtracParameterList: areaCode"));
            c.put("pPhone", c.getValue("SwivelDDP", "ComtracParameterList: phone"));
            c.put("pEmail", c.getValue("SwivelDDP", "ComtracParameterList: email"));
            c.put("pXHKitCode", c.getValue("SwivelDDP", "ComtracParameterList: XHKitCode"));
            c.put("pEqCode", c.getValue("SwivelDDP", "ComtracParameterList: EqCode"));
            c.put("pSIKKitCode1", c.getValue("SwivelDDP", "ComtracParameterList: SIKKitCode1"));
            c.put("pServiceCode1", c.getValue("SwivelDDP", "ComtracParameterList: ServiceCode1"));
            c.put("pSIKKitCode2", c.getValue("SwivelDDP", "ComtracParameterList: SIKKitCode2"));
            c.put("pServiceCode2", c.getValue("SwivelDDP", "ComtracParameterList: ServiceCode2"));
            c.put("pTestCaseName", c.getValue("SwivelDDP", "ComtracParameterList: TestCaseName"));
  

             
		   
		    
		    
		    Application.showMessage("pRequestType is ::"+c.get("pRequestType"));
		    Application.showMessage("pCorp is ::"+c.get("pCorp"));
		    Application.showMessage("pHouseNo is ::"+c.get("pHouseNo"));
		    Application.showMessage("pCustID is ::"+c.get("pCustID"));
		    Application.showMessage("pOpr is ::"+c.get("pOpr"));
		    Application.showMessage("pCorp1 is ::"+c.get("pCorp1"));
		    Application.showMessage("pHouseNo1 is ::"+c.get("pHouseNo1"));
		    Application.showMessage("pCustID1 is ::"+c.get("pCustID1"));
		    Application.showMessage("pFName is ::"+c.get("pFName"));
		    Application.showMessage("pLName is ::"+c.get("pLName"));
		    Application.showMessage("pAreaCode is ::"+c.get("pAreaCode"));
		    Application.showMessage("pPhone is ::"+c.get("pPhone"));
		    Application.showMessage("pEmail is ::"+c.get("pEmail"));
		    Application.showMessage("pXHKitCode is ::"+c.get("pXHKitCode"));
		    Application.showMessage("pEqCode is ::"+c.get("pEqCode"));
		    Application.showMessage("pSIKKitCode1 is ::"+c.get("pSIKKitCode1"));
		    Application.showMessage("pServiceCode1 is ::"+c.get("pServiceCode1"));
		    Application.showMessage("pSIKKitCode2 is ::"+c.get("pSIKKitCode2"));
		    Application.showMessage("pServiceCode2 is ::"+c.get("pServiceCode2"));
		    Application.showMessage("pTestCaseName is ::"+c.get("pTestCaseName"));
		   
  		       
	}	
	
	
	public void IterationLogicUpgradeDownGrade(Object input, ScriptingContext c) throws IOException
	{
		String sN=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName");
		Application.showMessage("Scenario is ::"+sN);
		if(sN.equalsIgnoreCase("Demi"))
    	{
    		c.setValue("IsDemi", "true");
    		
    	}
    	else
    	{
    		c.setValue("IsDemi", "false");	
    	}
	}
	
	public void IterationLogicSwivelDDP(Object input, ScriptingContext c) throws IOException
	{
		String reqType = (String) c.get("pRequestType");
		
		if(reqType.equalsIgnoreCase("install"))
    	{
    		c.setValue("InstallFlag", "true");
    		c.setValue("RshFlag", "false");
    		c.setValue("TransferFlag", "false");
    		c.setValue("DisConnectFlag", "false");
    		c.setValue("RestartFlag", "false");
    		c.setValue("CosFlag", "false");
    		c.setValue("CancelFlag", "false");
    	
    	}
    	else if(reqType.equalsIgnoreCase("reschedule"))
    	{
    		c.setValue("RshFlag", "true");	
    		c.setValue("InstallFlag", "false");
    		c.setValue("TransferFlag", "false");
    		c.setValue("DisConnectFlag", "false");
    		c.setValue("RestartFlag", "false");
    		c.setValue("CosFlag", "false");
    		c.setValue("CancelFlag", "false");
    	}
    	else if(reqType.equalsIgnoreCase("cancel"))
    	{
    		c.setValue("RshFlag", "false");	
    		c.setValue("InstallFlag", "false");
    		c.setValue("TransferFlag", "false");
    		c.setValue("DisConnectFlag", "false");
    		c.setValue("RestartFlag", "false");
    		c.setValue("CosFlag", "false");
    		c.setValue("CancelFlag", "true");
    	}
		
    	else if(reqType.equalsIgnoreCase("transfer"))
    	{
    		c.setValue("RshFlag", "false");	
    		c.setValue("InstallFlag", "false");
    		c.setValue("TransferFlag", "true");
    		c.setValue("DisConnectFlag", "false");
    		c.setValue("RestartFlag", "false");
    		c.setValue("CosFlag", "false");
    		c.setValue("CancelFlag", "false");
    	}
		
    	else if(reqType.equalsIgnoreCase("changeofservice"))
    	{
    		c.setValue("RshFlag", "false");	
    		c.setValue("InstallFlag", "false");
    		c.setValue("TransferFlag", "false");
    		c.setValue("DisConnectFlag", "false");
    		c.setValue("RestartFlag", "false");
    		c.setValue("CosFlag", "true");
    		c.setValue("CancelFlag", "false");
    	}
		
    	else if(reqType.equalsIgnoreCase("disconnect"))
    	{
    		c.setValue("RshFlag", "false");	
    		c.setValue("InstallFlag", "false");
    		c.setValue("TransferFlag", "false");
    		c.setValue("DisConnectFlag", "true");
    		c.setValue("RestartFlag", "false");
    		c.setValue("CosFlag", "false");
    		c.setValue("CancelFlag", "false");
    	}
		
    	else if(reqType.equalsIgnoreCase("restart"))
    	{
    		c.setValue("RshFlag", "false");	
    		c.setValue("InstallFlag", "false");
    		c.setValue("TransferFlag", "false");
    		c.setValue("DisConnectFlag", "false");
    		c.setValue("RestartFlag", "true");
    		c.setValue("CosFlag", "false");
    		c.setValue("CancelFlag", "false");
    	}
		
		
		
	}
	
	
	 
	public void reportingToExcel(Object input, ScriptingContext c)
	{
		
		
		if(c.get("result").equals("true"))
		{		
			//c.setValue("RESULT","PASSED");
			Application.showMessage("TestCase is a PASS");
		}
		else
		{
			//c.setValue("RESULT","FAILED");	
			Application.showMessage("Test Case is a Failure");
			
		}
	}
	
	
	
	
	
	
	public void baseMsgid_retrieval(Object input, ScriptingContext c) throws ClassNotFoundException, IOException
	{	
		
		
		try
		{
			
            Statement st = dBConnect(input, c);
            ResultSet rs = st.executeQuery("select msgid from (select msgid from cwmessagelog order by creation_time desc) where rownum < 2");
	        while (rs.next()) 
	        {
	        basemsgID = rs.getString(1);
	        c.setValue("BaseMsgid", basemsgID);
	        Application.showMessage("Base Message ID is ::"+c.getValue("BaseMsgid"));
	        
	        }
	        if(basemsgID ==null)
	        {
	          System.out.println("No MsgId Found");	
	        }
	        else
	        {
	         System.out.printf("MsgId Found is %s\n",basemsgID);		
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
	
	public int validationPointIgnoreCase(String ValidateData1, String ValidateData2, Object input , ScriptingContext c)
	{
		int result;
		if(ValidateData1.trim().equalsIgnoreCase(ValidateData2.trim()))
		{
			Application.showMessage("Validation is Successfull with Input Data ::"+ValidateData1+" and "+ValidateData2);
			
			result=1;
		}
		else
		{
			c.report("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+" and "+ValidateData2);
	
			result=0;
		}
		
		return result;
		
	}
	
	
	public int ORvalidationPoint(String ValidateData1, String ValidateData2,String ValidateData3, Object input , ScriptingContext c)
	{
		int result;
		if(ValidateData1.trim().equals(ValidateData2.trim()))
		{
			Application.showMessage("Validation is Successfull with Input Data ::"+ValidateData1+"and"+ValidateData2);
			
			result=1;
		}
		else if(ValidateData1.trim().equals(ValidateData3.trim()))
		{
			Application.showMessage("Validation is Successfull with Input Data ::"+ValidateData2+"and"+ValidateData3);
			
			result=1;
		}
		else
		{
			c.report("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+"and"+ValidateData2);
	
			result=0;
		}
		
		return result;
		
	}
	
	
	public int ANDvalidationPoint(String ValidateData1, String ValidateData2,String ValidateData3, Object input , ScriptingContext c)
	{
		int result;
		if(ValidateData1.trim().equals(ValidateData2.trim()) & (ValidateData1.trim().equals(ValidateData3.trim())))
		{
			Application.showMessage("Validation is Successfull with Input Data ::"+ValidateData1+"and"+ValidateData2 +"and"+ValidateData3);
			
			result=1;
		}
		
		else
		{
			c.report("Validation is NOT Successfull with Input Data..!!! ::"+ValidateData1+"and"+ValidateData2);
	
			result=0;
		}
		
		return result;
		
	}
	
	
	public String extractXml(ResultSet rs1,String data) throws SQLException 
	{	
	
		Blob blob = rs1.getBlob(data);
        byte[] bdata = blob.getBytes(1, (int) blob.length());
        xml1 = new String(bdata);      
		return(xml1);	
	}
	
	public String RemoveNameSpaces(String sIpStr) 
	{
        String sRet = sIpStr;
        Pattern pP = Pattern.compile("<[a-zA-Z]*:");
        Matcher mM = pP.matcher(sRet);
        sRet = mM.replaceAll("<");

        pP = Pattern.compile("</[a-zA-Z]*:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll("</");
        
        pP = Pattern.compile(" xmlns.*?(\"|\').*?(\"|\')");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(""); 

        pP = Pattern.compile(" xsi:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
       // bfcopcom
        pP = Pattern.compile("sik:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll("");
        
        pP = Pattern.compile("bfcopcom:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        
        pP = Pattern.compile("comt:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        
        pP = Pattern.compile("cod:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll("");
        
        pP = Pattern.compile("cct:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll("");
               
        pP = Pattern.compile("SignatureRequired=\"FALSE\"");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        
        pP = Pattern.compile("type=\" SingleComponentProduct\"");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        
        return sRet;
   }

	public String GetValueByXPath(String sXml, String sxpath) throws SAXException, IOException, XPathExpressionException,NullPointerException
	{
	       String val=null;
	       try
	       {
	              //sXml.replaceAll(" xsi:", " ");
	            InputSource source = new InputSource(new StringReader(sXml));
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	            factory.setNamespaceAware(false);    
	            factory.setIgnoringElementContentWhitespace(false);
	      
	            DocumentBuilder builder;        
	            Document doc = null;
	       
	               builder = factory.newDocumentBuilder();  
	               doc = builder.parse(source);
	              XPathFactory xpathFactory = XPathFactory.newInstance();            
	            XPath xpath = xpathFactory.newXPath(); 
	            XPathExpression expr = xpath.compile(sxpath);
	            Node oNode= (Node) expr.evaluate(doc,XPathConstants.NODE);
	            if( oNode==null)
	            {
	               val="XPath ERROR.Either NULL or Xpath needs to be Fixed"; 
	            }
	            else
	            {
	               val =oNode.getTextContent(); 
	 
	            }
	          
	            xpath=null; expr=null; oNode=null; doc=null; builder=null;
	            
	        }
	       catch (NullPointerException | ParserConfigurationException | SAXException | IOException e) 
	        {           
	               e.printStackTrace();       
	        }
	       
	              return val;
	              
	 }










	public String nodeFromKey(String xmlStr,String str1,String str2) throws NullPointerException
	       {
	           if(xmlStr.contains(str1) && xmlStr.contains(str2))
	           {
	                 int startPosition=0;
	                 int endPosition=0;
	                 startPosition = xmlStr.indexOf(str1) + str1.length();
	              
	                 if(startPosition==-1)
	                 {
	                        System.out.printf("No Value found for ::%s\n",str1);
	               return(null);         
	                 }          
	                 else if(xmlStr.indexOf(str1)==-1)
	                 {
	                        System.out.printf("No Value found for ::%s\n",str1);
	          }
	              
	                 endPosition = xmlStr.indexOf(str2, startPosition); 
	              if(endPosition==-1)
	                 {
	                     System.out.printf("No Value found for ::%s\n",str2);        
	                 }          
	                 String resultval = xmlStr.substring(startPosition, endPosition); 
	                 if(resultval==null)
	                 {
	                     return "NO Data Fetched from in-between strings..!Check the strings..!";
	                 }
	                 else
	                 return (resultval);
	            }
	           else
	           {
	              return "NO Data Fetched from in-between strings..!Check the strings..!"; 
	           }
	}
	

 
 
 public String GetXmlNodesByXPath(String sXml, String sxpath) throws SAXException, IOException, XPathExpressionException
 {
	 String val=null;
	 try
	 {
		 //sXml.replaceAll(" xsi:", " ");
	     InputSource source = new InputSource(new StringReader(sXml));
	     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	     factory.setNamespaceAware(false);    
	     factory.setIgnoringElementContentWhitespace(false);
      
	     DocumentBuilder builder;        
	     Document doc = null;
	 
		 builder = factory.newDocumentBuilder();  
		 doc = builder.parse(source);
		 XPathFactory xpathFactory = XPathFactory.newInstance();            
	     XPath xpath = xpathFactory.newXPath(); 
	     XPathExpression expr = xpath.compile(sxpath);
	     NodeList oNode= (NodeList) expr.evaluate(doc,XPathConstants.STRING);
	     val=oNode.toString();
	   
	     xpath=null; expr=null; oNode=null; doc=null; builder=null;
	     
	 }
	 catch (ParserConfigurationException | SAXException | IOException e) 
	 {           
		 e.printStackTrace();       
	 }
	 
	 return val;
	 
 }
 
 public void printStart(Object input , ScriptingContext c,String Data)
	{
		 Application.showMessage("----------------------------------------------------------");
      Application.showMessage("************"+Data+"************");    
      Application.showMessage("----------------------------------------------------------");
	}
	
	public void printEnd(Object input , ScriptingContext c,String Data)
	{
		 Application.showMessage("----------------------------------------------------------");
      Application.showMessage("************"+Data+"************");    
      Application.showMessage("----------------------------------------------------------");
	}
 
 public void makeString()
 {
	 String Xml="<Ayyo><Ammo \"Install\">GotIt</Ammo></Ayyo>";

	 char[] XmlData= Xml.toCharArray();
     for (int i=0;i<XmlData.length;i++)
     {
    	 if(XmlData[i]=='"')
    	 {
    		
    		String resetStr= XmlData.toString();
    		
    		resetStr = resetStr.substring(0,i)+"/"+resetStr.substring(i+1); 
    		Application.showMessage(resetStr);
    	 }
     }
 }
 
  public String enumcheck(String action)
  {
	  String value;
	 
	  Map<String,String> enumMap=new HashMap <String,String>();
	  
	  enumMap.put("DISCONNECT", "DIS");
	  enumMap.put("ADD", "NEW");
	  enumMap.put("CHANGE", "CHG");
	  enumMap.put("NOCHANGE", "NC");
	  enumMap.put("cancel", "CAN");
	  enumMap.put("changeofservice", "CHG");
	  enumMap.put("install", "NEW");
	  enumMap.put("restart", "RST");
	  enumMap.put("transfer", "TRF");
	  enumMap.put("troublecall", "TBC");
	  enumMap.put("ACTIVE", "ACT");
	  enumMap.put("DISCONNECTED", "DIS");
	  enumMap.put("PENDING", "PACT");
	  enumMap.put("PENDING", "PINS");
	  enumMap.put("PENDING", "PSHP");
	  
	  value = enumMap.get(action);
	  
	  
	  return value;	  
	  
  }
  
  public String sikXhsproductValidation(ScriptingContext c,Object input,String xml,String data1,String data2,String prdCode) throws XPathException, SAXException, IOException
  {
	  Application.showMessage("Am here");
	  String productName = null;
	  int count1=xml.split(data1).length;
	  Application.showMessage("Count1 is "+count1);
	 
	  for(int i=1;i<count1;i++)
	  {
		  
		 String LineOfBussinessXml= GetValueByXPath(xml,"/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness["+i+"]");
   		 Application.showMessage("Line Of Bussiness XML at" +i+ "position is"+LineOfBussinessXml);
   		 
   		 int count2=LineOfBussinessXml.split("<AvailableProduct>").length;
   	     Application.showMessage("Count2 is "+count2);
   		 
		 for(int j=1;j<count2;j++)
		 {
		   String productCode= GetValueByXPath(xml,"/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness["+i+"]/AvailableProducts/AvailableProduct["+j+"]/AvailableProductComponent/AvailableProductComponent/EPCProductNumber");
		   Application.showMessage("product code at Lib"+productCode);
		   if(productCode.equals(null))
		   {
			  continue; 
		   }
		   else if(productCode.equals(prdCode))
		   {
		
		    productName= GetValueByXPath(xml,"/GetMarketAvailabilityResponse/GetMarketAvailabilityResult/LinesOfBusiness/LineOfBusiness["+i+"]/AvailableProducts/AvailableProduct["+j+"]/AvailableProductComponent/AvailableProductComponent/Name");
            Application.showMessage("Product from Getmarkat Availabilty call saved is "+productName);  
		   }
	    }
		
	  }
	return productName;
  }
 
  public Boolean modemVerification(String prdCode,ScriptingContext c,Object input) throws ClassNotFoundException, IOException
  {
	  
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**************MODEM VERIFICATION HERE****************");       
	     Application.showMessage("-----------------------------------------------------");
	  
	     Boolean modemSuccess = null;
         ResultSet rs;  
         
        
        	try
     		{
        		 Statement st = dBConnect(input, c);		
     	         rs = st.executeQuery("select NAME, DATA from cwftemplateconfig where NAME = 'bfcopcom:bfcConfig'");
     	         while (rs.next())
     	         {
     	        	
     	        
     	            String DATA;
     	            
     	            DATA = rs.getString(2);
     	            Application.showMessage("Config XML is ::"+DATA);
     	            String ModemName = nodeFromKey(DATA,"<sikOwnedModemProductCode>","</sikOwnedModemProductCode>");
     	            Application.showMessage("Modem is ::"+ModemName);
     	            if(prdCode.equalsIgnoreCase(ModemName))
     	            {
     	            	Application.showMessage("SIK MOdem Installed is Validated from cwftemplateconfig Table as::"+ModemName);
     	            	modemSuccess=true;
     	            }
     	            else
     	            {
     	            	Application.showMessage("SIK MOdem Installed is **NOT**!! Validated from cwftemplateconfig Table as::"+ModemName);

     	            	modemSuccess=false;
     	            }
     	            
     	         }
     		}
        	 catch (SQLException e)
     		{
     		    System.out.println("Connection Failed! Check output console");
     			e.printStackTrace();
     			return modemSuccess;
     		}
         
	
	     return modemSuccess;
	  
  }
  
 
 public void run(Object input, ScriptingContext c) throws XPathExpressionException, SAXException, IOException, ClassNotFoundException
 {
	 //String value= GetValueByXPath("<a><b>first B</b><b>Sec B</b><b>Third B</b></a>","/a/b[2]"); 
	 //Application.showMessage("value is ::" +value);
	 
	 //Boolean j= modemVerification(null, c, input);
	 readValuesfromsikExcel(input, c);
	// Boolean modemSuccess = null;
     ResultSet rs;  
     
    
    	 try
 		{
    		 Application.showMessage("Am here1");
    		 Statement st3 = dBConnect(input, c);
    		 Application.showMessage("Am here1");
 	         rs = st3.executeQuery("select NAME, DATA from cwftemplateconfig where NAME = 'bfcopcom:bfcConfig'");
 	         Application.showMessage("Am here2");
 	         while (rs.next())
 	         {
 	        	 Application.showMessage("Am here3");
 	        
 	            String DATA=null;
 	            
 	            DATA = rs.getString(2);
 	            Application.showMessage("Config XML is ::"+DATA);
 	            String ModemName = nodeFromKey(DATA,"<sikOwnedModemProductCode>","</sikOwnedModemProductCode>");
 	            Application.showMessage("Modem is ::"+ModemName);
 	            /*if(prdCode.equalsIgnoreCase(ModemName))
 	            {
 	            	Application.showMessage("SIK MOdem Installed is Validated from cwftemplateconfig Table as::"+ModemName);
 	            	modemSuccess=true;
 	            }
 	            else
 	            {
 	            	Application.showMessage("SIK MOdem Installed is **NOT**!! Validated from cwftemplateconfig Table as::"+ModemName);

 	            	modemSuccess=false;
 	            }*/
 	           rs.close(); 
 	           st3.close();
 	         }
 		}
    	 
    	 catch (SQLException e)
  		{
  		    System.out.println("Connection Failed! Check output console");
  			e.printStackTrace();
  			//return modemSuccess;
  		}
    
	// String val= RemoveNameSpaces("RemoveNameSpaces");
	// Application.showMessage(val);
	 
	 
 }
	
	
	
	
	@SuppressWarnings("null")
	public String makeAccount(Object object,Object object2,Object object3)
	{
		String AccountNum = null;
	     AccountNum= AccountNum.concat((String) object).concat((String) object2).concat((String) object3);
		Application.showMessage("Account Number is"+AccountNum);
		return AccountNum;
	}

	public int retOrderId(Integer Ord1)
	{
				
		return Ord1;
		
	}
	
	public long orderid(Object input, ScriptingContext c)
	{
		 int orderid=(int) c.get("OrderID_SIK");
		 return orderid & 0x00000000ffffffffL;
	}
	
	public Map<String,String> kittyPerRateCodes()
	{
		
		Map<String,String> kitratesCode=new HashMap <String,String>();
		kitratesCode.put("NOT ST051", "SI028 OR SI049");
		kitratesCode.put("NOT ST052", "SI017 OR SI043");
		kitratesCode.put("NOT ST053", "SI015 OR SI041");
		kitratesCode.put("NOT ST054", "SI014 OR SI040");
		kitratesCode.put("NOT ST055", "SI016 OR SI042");
		kitratesCode.put("NOT ST056", "SI064 OR SI065");
		kitratesCode.put("NOT ST057", "SI066 OR SI067");
		return kitratesCode;
	
	}
	
	public  Map<String,String> splitter(String input)
	{
		
		//String input ="350 + ThermostatDataProvider";
		//String input ="350";
		Map<String,String> retMap=new HashMap <String,String>();
		String service=" ";
		String group =" ";
	    boolean i =input.contains("+");
	    String Singlegroup="false";
	    String Multigroup="false";
	    int y=0;
	    if (i==true)
		{
	    	Multigroup="true";
	    	
	    	
	    	
	
	    	for( y=0;input.charAt(y)!='+';y++)
	    	{
	    		String temp=String.valueOf(input.charAt(y));
	    		group= group.concat(temp).trim();
	    		
	    		
	    	}
			for(y=y+2;y<input.length();y++)
			{
				String temp=String.valueOf(input.charAt(y));
				service=service.concat(temp).trim();
			}
			//Application.showMessage("TierGroup is ::"+group);
			//Application.showMessage("TierService is ::"+service);
			retMap.put("group",group.trim());
			retMap.put("service",service.trim());
			retMap.put("IsMulti",Multigroup.trim());
			retMap.put("IsSingle",Singlegroup.trim());
			
		
		}
	    else
	    {
	    	
	    	Singlegroup="true";
	    	retMap.put("group",input.trim());
	    	retMap.put("service"," ");
	    	retMap.put("IsMulti",Multigroup.trim());
			retMap.put("IsSingle",Singlegroup.trim());
	    	
	    	Application.showMessage("Only Tier Group is there");
	    }
		return retMap;
		
		
		
	}
	
	public int validationPoint(String ValidateData1, String ValidateData2, Object input , ScriptingContext c)
	{
		
		int result;
		
		if(ValidateData1.equals(null)||ValidateData2.equals(null))
		{
		   Application.showMessage("One of the Validation Data entered is NULL...!");
		   result = 0;
		}
		else
		{
			if(ValidateData1.equals(ValidateData2))
			{
				Application.showMessage("Validation is Successfull with Input Data ::"+ " "+ValidateData1+" and "+" "+ValidateData2);
				
				result=1;
			}
			else
			{
				c.report("Validation is NOT Successfull with Input Data..!!! :: "+ValidateData1+" and "+ValidateData2);
		
				result=0;
			}
		}
		return result;
		
	}
	
	
	public int verificationPoint(String ValidateData1, String ValidateData2, Object input , ScriptingContext c)
	{
		
		int result;
		
		if(ValidateData1.equals(null)||ValidateData2.equals(null))
		{
		   Application.showMessage("One of the Validation Data entered is NULL...!");
		   result = 0;
		}
		else
		{
			if(ValidateData1.equals(ValidateData2))
			{
				Application.showMessage("Validation is Successfull with Input Data ::"+ " "+ValidateData1+" and "+" "+ValidateData2);
				
				result=1;
			}
			else
			{
				Application.showMessage("Validation is NOT Successfull with Input Data..!!! :: "+ValidateData1+" and "+ValidateData2);
		
				result=0;
			}
		}
		return result;
		
	}
	
	
	
	
	
	
	public String makeCorpIDfromAccountNumber(String AccountNumber,String INbillingSystem)
	{
		String INcorpID;
		 if(INbillingSystem.equalsIgnoreCase("DDP"))
         {
         	INcorpID= AccountNumber.substring(0, 5);
         }
         
         else
         {
         	INcorpID= AccountNumber.substring(0, 6);
         }
		return INcorpID;
	}
	
	public void CHKsikSubmitOrderDetails(String Orderid,ScriptingContext c,Object input) throws ClassNotFoundException, IOException
	{
		  Application.showMessage("-----------------------------------------------------");
		  Application.showMessage("**************ORDER ID CHECK IN SIK HERE****************");       
		  Application.showMessage("----------------------------------------------------");
		  ResultSet rs;
		  try
   		  {
			  Date todayDate = new Date();
	          TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
	          DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
	          todayDateFormat.setTimeZone(timeZone);
	          String strTodayDate = todayDateFormat.format(todayDate);
	          Application.showMessage("Todays Date as per EST is::"+strTodayDate);
              
      		 Statement st = dBConnect(input, c);		
   	         rs = st.executeQuery("select * from SIKSUBMITORDERDETAILS  where woid ='"+Orderid+"' AND CREATION_TIME >'"+strTodayDate+"'");
   	         
   	         if (rs.first()==false)
   	         {
   	        	Application.showMessage("There is no data in SIK table for corresponding Workorderid for todays DATE"); 
   	         }
   	         else
   	         {
	   	         while (rs.next())
	   	         {
	   	        		   	        
	   	            String DATA;	   	            
	   	            DATA = rs.getString("CANCEL");
	   	            if(DATA.equals("1"))
	   	            {
	   	              Application.showMessage("Cancel flag is set to 1");	
	   	            }
	   	            else
	   	            {
	   	              Application.showMessage("Cancel flag is set to 0");	
	   	            }
	   	            	   	         	   	            
	   	          }
   	          }
   		  }
      	 catch (SQLException e)
   		 {
   		    System.out.println("Connection Failed! Check output console");
   			e.printStackTrace();
   			
   		 }
       
		  
		  
	}
	
	
	public String getsysTime()
    {
    	 Date todayDate = new Date();
         TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
         DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
         todayDateFormat.setTimeZone(timeZone);
         String strTodayDate = todayDateFormat.format(todayDate);
         //System.out.println("Todays Date as per EST is::"+strTodayDate); 
         return strTodayDate;
    }
    public String getYesterdayDateString() 
    {
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YY");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);    
        return dateFormat.format(cal.getTime());
    }
	
    public String[] TestCaseGroup(String TestCasedata,ScriptingContext c,Object input)
	{
	   
	    String[] TestCase = TestCasedata.split(",");
	    for (int i = 0; i < TestCase.length; ++i)
	    {
	      Application.showMessage(i+" TestCaseID::" + TestCase[i]);
	    }
	    
		return TestCase;
	}
    
    
	  public String getTimeInstance()
	   {
		      String Time;
		      Date todayDate = new Date();
			  TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
			  DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY HH:mm:ss");
			  todayDateFormat.setTimeZone(timeZone);
			  Time=todayDateFormat.format(todayDate);
			  return Time;
	   }
	   
	   public String getDurationInMinutes(String T1,String T2) throws ParseException
	   {
		   // String Duration;
		    String Duration1;
		    SimpleDateFormat format = new SimpleDateFormat("dd-MMM-YY HH:mm:ss");
		    Date d1 = null;
		    Date d2 = null;
		    d1=format.parse(T1);
		    d2=format.parse(T2);
		    long diff = d2.getTime() - d1.getTime();
		    long mins=diff/(1000*60);
		    long secs1=diff%(1000*60);
		    long sSecs=secs1/100;
		    long mSecs=secs1%10;
		    long secs=sSecs+mSecs;
		    
		   /* long diffSeconds = diff / 1000 % 60;
		    long diffMinutes = diff / (60 * 1000) % 60;
		    long diffHours = diff / (60 * 60 * 1000);
		    Duration= diffHours+" "+diffMinutes+" "+diffSeconds;*/
		    
		    Duration1=mins+"."+secs;
		   return Duration1;
	   }
	   
	   public void logWritter(String log, String FileName) throws IOException
		{
			File file = new File(FileName);
	        //Application.showMessage("The data is ::"+FileData);
	        
	        if (!file.exists()) 
	        {
	            file.createNewFile();
	                                       
	        }
	            
	            BufferedReader br=new BufferedReader(new FileReader(file));
	            String Data=br.readLine();
	            String Line;
	            while ((Line = br.readLine()) != null)   
	            {
	            	
	            	Data=Data+"\r\n"+Line;
	            }	            
	            FileWriter fw = new FileWriter(file.getAbsoluteFile());
	            BufferedWriter bw = new BufferedWriter(fw);
	            bw.write(Data+"\r\n"+ log);
	            bw.close();
		}
	   
	   public void UpdateDurationText(String T1,String T2,String TC, String FileName) throws ParseException, IOException
	   {
		    String Duration=getDurationInMinutes(T1, T2);
			System.out.println("Duration is ::"+Duration);
			String Log= T2+","+TC+","+Duration;
			logWritter(Log, FileName);
	   }
	   
	   public String getAttributeName(Object input,ScriptingContext c, String xmldata,String tagname,String attributename) throws ParserConfigurationException, SAXException, IOException
	   {
		   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	       DocumentBuilder builder = factory.newDocumentBuilder();
	       InputSource is = new InputSource(new StringReader(xmldata));
	       Document doc = builder.parse(is);
	       String attribute = null;
	       

	       NodeList nodeList = doc.getElementsByTagName(tagname);
	        
	       for (int i = 0; i < nodeList.getLength(); i++) {                
	           Node node = nodeList.item(i);
	           
	           if (node.hasAttributes()) {
	          	
	               Attr attr = (Attr) node.getAttributes().getNamedItem(attributename);
	              
	               if (attr != null) {
	              	 
	                   attribute= attr.getValue(); 
	                  // String check=attr.getNodeValue();
	                  // String check1=attr.getNodeName();
	                   Application.showMessage("Reason:: "+attribute); 
	                  // Application.showMessage("attribute: "+check); 
	                  // Application.showMessage("attribute: "+check1); 
	               }
	           }
	       }
		return attribute;

	   }
	   
	   public  List<String> extractValue(String services,Object input,ScriptingContext c)
	   {
		   List<String> Groupservices = null;
		  // String symbol="+";
		   String simSymbol=",";
		   String value= "/";
		   if(services.contains(simSymbol))
		   {
		   String check=services.replace(simSymbol, value);
		    Groupservices=Arrays.asList(check.split("/"));
		   }
		   else
		   {
			   Groupservices.add(services);
		   }
		   return Groupservices; 
	   }   
		  
	   public  List<String> getRateCodes(Object input,ScriptingContext c) throws IOException
	   {
		   List<String> ratecodes=new ArrayList();
		   List<String> OTOPDratecodes=new ArrayList();
		   ratecodes.add(c.getValue("SwivelCSG","sik_SwivelCSG: KTCode1"));
		   ratecodes.add(c.getValue("SwivelCSG","sik_SwivelCSG: KTCode2"));
		   ratecodes.add(c.getValue("SwivelCSG","sik_SwivelCSG: KTCode3"));
		   ratecodes.add(c.getValue("SwivelCSG","sik_SwivelCSG: KTCode4"));
		   ratecodes.add(c.getValue("SwivelCSG","sik_SwivelCSG: KTCode5"));
		   ratecodes.add(c.getValue("SwivelCSG","sik_SwivelCSG: KTCode6"));
		   ratecodes.add(c.getValue("SwivelCSG","sik_SwivelCSG: ShipCode1"));
		   ratecodes.add(c.getValue("SwivelCSG","sik_SwivelCSG: ShipCode2"));
		   ratecodes.add(c.getValue("SwivelCSG","sik_SwivelCSG: ShipCode3"));
		   for(int i=0;i<ratecodes.size();i++)
		   {
			   if((ratecodes.get(i)).equalsIgnoreCase("OT0PD"))
					   {
				   OTOPDratecodes.add(ratecodes.get(i));
					   }
			   
		   }
		  // String symbol="+";
		return OTOPDratecodes ;
	   }
	public int getTrackingDetails(Object input, ScriptingContext c,
			String senddata) throws ParserConfigurationException, SAXException, IOException {
		int count = 0;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	       DocumentBuilder builder = factory.newDocumentBuilder();
	       InputSource is = new InputSource(new StringReader(senddata));
	       Document doc = builder.parse(is);
	       Application.showMessage("Root element :" + doc.getDocumentElement().getNodeName());
	       if (doc.hasChildNodes()) {

				NodeList nodeList = doc.getElementsByTagName("sik:TrackingCode");
				for (int i = 0; i < nodeList.getLength(); i++) {

					Node elemNode = nodeList.item(i);

					if (elemNode.getNodeName() == "sik:TrackingCode") {		

						Application.showMessage("\nNode Name =" + elemNode.getNodeName());					

						Application.showMessage("Node Content =" + elemNode.getTextContent());
						if (elemNode.getTextContent().contains("OT0PD")&& elemNode.getTextContent().contains("Xi3_special")) {
							count++;
						}
						else
						{
							c.report("Special Rate Code not Matched");
						}
						Application.showMessage("countValue:::" + count);
					}


			}
		
		
	}
	       return count;     
}
	public List<String> getRateCodesforComTrac(Object input, ScriptingContext c) throws IOException {
		// TODO Auto-generated method stub
		 List<String> ratecodes=new ArrayList();
		   List<String> OTOPDratecodes=new ArrayList();
		   Application.showMessage(c.getValue("SwivelDDP","ComtracParameterList: wor_rate_Code"));
		   Application.showMessage(c.getValue("SwivelDDP","ComtracParameterList: KitRate_Code1"));
		   
		   ratecodes.add(c.getValue("SwivelDDP","ComtracParameterList: wor_rate_Code"));
		   ratecodes.add(c.getValue("SwivelDDP","ComtracParameterList: KitRate_Code1"));
		   //ratecodes.add(c.getValue("ComtracParameterList","ComtracParameterList: KitRate_Code2"));
		   //ratecodes.add(c.getValue("ComtracParameterList","ComtracParameterList: KitRate_Code3"));
		   
		   for(int i=0;i<ratecodes.size();i++)
		   {
			   if((ratecodes.get(i)).equalsIgnoreCase("OT0PD"))
					   {
				   OTOPDratecodes.add(ratecodes.get(i));
					   }
			   
		   }
		  // String symbol="+";
		return OTOPDratecodes ;
	   }
	public List<String> getRateCodesforDotComTrac(Object input,
			ScriptingContext c) throws IOException {
		// TODO Auto-generated method stub
		List<String> ratecodes=new ArrayList();
		   List<String> OTOPDratecodes=new ArrayList();
		   Application.showMessage(c.getValue("dS_SIK", "Sik_Dotcom: ProductCode"));
		   Application.showMessage(c.getValue("dS_SIK", "Sik_Dotcom: ProductCode"));
		   
		   ratecodes.add(c.getValue("dS_SIK", "Sik_Dotcom: ProductCode"));
		   //ratecodes.add(c.getValue("dS_SIK", "Sik_Dotcom: ProductCode"));
		   //ratecodes.add(c.getValue("ComtracParameterList","ComtracParameterList: KitRate_Code2"));
		   //ratecodes.add(c.getValue("ComtracParameterList","ComtracParameterList: KitRate_Code3"));
		   
		   for(int i=0;i<ratecodes.size();i++)
		   {
			   if((ratecodes.get(i)).equalsIgnoreCase("OT0PD"))
					   {
				   OTOPDratecodes.add(ratecodes.get(i));
					   }
			   
		   }
		  // String symbol="+";
		return OTOPDratecodes ;
	   }
	public void readValuesfromDTACSGExcel(Object input, ScriptingContext c) throws IOException{
		// TODO Auto-generated method stub
		
		{
			 Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
			    String XML_EndPoint=c.getValue("SwivelCSG", "sikDBsettings: XML_Enpoint");
			    String DB_Host=c.getValue("SwivelCSG", "sikDBsettings: DB_HOST");
			    String dB_Password=c.getValue("SwivelCSG", "sikDBsettings: DB_Password");
			    String dB_Username=c.getValue("SwivelCSG", "sikDBsettings: DB_UserName");
			    String dB_Port=c.getValue("SwivelCSG", "sikDBsettings: DB_Port");
			    String dB_serviceName=c.getValue("SwivelCSG", "sikDBsettings: DB_ServiceName");
			    String dB_connectionName=c.getValue("SwivelCSG", "sikDBsettings: DB_ConnectionName");
			    String dB_PasswordCommon=c.getValue("SwivelCSG", "sikDBsettings: Common_DBPassword");
			    String dB_UsernameCommon=c.getValue("SwivelCSG", "sikDBsettings: Common_DBUserName");
	            String InputAccountNumber = c.getValue("SwivelCSG", "sik_SwivelCSG: AccountId_ITEMLIST");
	            String InputOrderID = c.getValue("SwivelCSG", "sik_SwivelCSG: OrderID");
	            String InputLocation_ID = c.getValue("SwivelCSG", "sik_SwivelCSG: LocationId");
	            String InputEmail = c.getValue("SwivelCSG", "sik_SwivelCSG: Email");
	            String InputFname = c.getValue("SwivelCSG", "sik_SwivelCSG: Fname");
	            String InputLname = c.getValue("SwivelCSG", "sik_SwivelCSG: Lname");
	            String InputcustType = c.getValue("SwivelCSG", "sik_SwivelCSG: custType");
	            String InputCustomerId = c.getValue("SwivelCSG", "sik_SwivelCSG: CustomerId");
	            String InputTN = c.getValue("SwivelCSG", "sik_SwivelCSG: TN");
	            String isModify=c.getValue("SwivelCSG", "sik_SwivelCSG: csgDTA");
	               
	            c.setValue("csgDTA", isModify);
	            Application.showMessage("CSGDTA yes or no::"+isModify);
	            c.put("csgDTA", isModify);
	            Application.showMessage("IsModify is ::"+isModify);
	            String companyname=c.getValue("SwivelCSG", "sik_SwivelCSG: companyname");
	            Application.showMessage("Companyname::"+companyname);
	        
	            c.setValue("companyname", companyname);
	            
	            
	            
	            
	            
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
	  		    
	  		    
	  		    
	  		    c.setValue("OrderID", InputOrderID);
	  		    c.setValue("accountNumber", InputAccountNumber);
	  		    c.setValue("emailAddress", InputEmail);
	  		    c.setValue("FirstName", InputFname);
	  		    c.setValue("LastName", InputLname);
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
	  		    
	  		    
			    
		}
		
	}
	
}
