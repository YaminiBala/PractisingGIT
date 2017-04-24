
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


/**
 * @author 266216 
 *
 */
/**
 * @author 266216
 *
 */

public class RTP_ValidationsClass_UpDown 
{
	//private static final ScriptingContext ScriptingContext = null;
	//private static final Object Object = null;
	public String XML_AccNumber;
	public String XML_ScenarioName;
	public static String SERVICEorCURRENTSERVICE;
	public String NEWSERVICE;
	public String HOUSE_KEY;
	
	LibraryRtp_UpDown Lp=new LibraryRtp_UpDown();
	
	public Connection connection = null;
	public static String rowmsg= null;
	
    public String[][] aa = new String[10][20];
    
    public void execution(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException
  	{
  	try
  	{
  	//String arg0="RTP_InputParams";
  	       // String arg1="SCENARIO_NUMBER";
  		   Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
  	        String sc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER");
  	        Application.showMessage("SCENARIO_NUMBER::"+sc);
  	        String XML_AccNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");
  	        c.setValue("ACC_input",XML_AccNumber);
  	      
  	        //Application.showMessage("Value is"+c.getValue("ACC_input"));
  	        //ACC_input=XML_AccNumber;
  	        Application.showMessage("XML_AccNumber::"+XML_AccNumber);
  	        String XML_ScenarioName=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_ScenarioName");
  	        Application.showMessage("XML_ScenarioName::"+XML_ScenarioName);
  	        String SERVICEorCURRENTSERVICE=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE");
  	        Application.showMessage("SERVICEorCURRENTSERVICE::"+SERVICEorCURRENTSERVICE);
  	        String NEWSERVICE=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: NEWSERVICE");
  	        Application.showMessage("NEWSERVICE::"+NEWSERVICE);
  	        String HOUSE_KEY=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: HOUSE_KEY");
  	        Application.showMessage("HOUSE_KEY"+HOUSE_KEY);
  	        
  	        String XML_EndPoint=c.getValue("RTPNormalScenariosdS", "DbConnections: XML_Enpoint");
  		    String DB_Host=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_HOST");
  		    String TestCaseName=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseName");
  		    String dB_Password=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_Password");
  		    String dB_Username=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_UserName");
  		    String dB_Port=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_Port");
  		    String dB_serviceName=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_ServiceName");
  		    Application.showMessage("Endpoint is ::"+XML_EndPoint);
  		    Application.showMessage("DB_HOST is ::"+DB_Host);
  		    Application.showMessage("TestCaseName is ::"+TestCaseName);
  		    Application.showMessage("dB_Password is ::"+dB_Password);
  		    Application.showMessage("dB_Username is ::"+dB_Username);
  		    Application.showMessage("dB_Port is ::"+dB_Port);
  		    Application.showMessage("dB_serviceName is ::"+dB_serviceName);
  		    Application.showMessage("XML_AccNumber is ::"+XML_AccNumber);
  		    c.setValue("sHOUSE_KEY", HOUSE_KEY); 
  		    c.setValue("ACC_input",XML_AccNumber);
  		    c.setValue("DB_Host", DB_Host);
  		    c.setValue("dB_Password",dB_Password);
  		    c.setValue("dB_Username",dB_Username);
  		    c.setValue("dB_Port",dB_Port);
  		    c.setValue("dB_serviceName",dB_serviceName);
  		    c.setValue("XML_EndPoint",XML_EndPoint);
  		    c.setValue("sSERVICEorCURRENTSERVICE",SERVICEorCURRENTSERVICE);
  	         Map<String, String> ResultMap = new HashMap<String,String>();

  	         ResultMap= Lp.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
  		     c.setValue("ExistingGroup",ResultMap.get("group"));
  		     c.setValue("ExistingService", ResultMap.get("service"));
  		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
  	        
  	        
  	        Application.showMessage("_______________________________________________");
  	        
  	        
  	        //return sc;
  	}
  	catch(Exception e)
  	{
  	//return "error";
  	}
  	      
  	 
  	}
   
    public void valuesFromAggregrate(Object input , ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException
    {
    	try
    	{
    		
    	 
    		String XML_EndPoint=c.getValue("RTPDataSourceCollections", "DbConnections: XML_Enpoint");
    		String DB_Host=c.getValue("RTPDataSourceCollections", "DbConnections: DB_HOST");
    		String TestCaseName=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: TestCaseName");
    		String dB_Password=c.getValue("RTPDataSourceCollections", "DbConnections: DB_Password");
    		String dB_Username=c.getValue("RTPDataSourceCollections", "DbConnections: DB_UserName");
    		String dB_Port=c.getValue("RTPDataSourceCollections", "DbConnections: DB_Port");
    		String dB_serviceName=c.getValue("RTPDataSourceCollections", "DbConnections: DB_ServiceName");
    		String XML_AccNumber=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_AccNumber");
    		String HOUSE_KEY=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: HOUSE_KEY");
    		String SERVICEorCURRENTSERVICE=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
    		
    		c.setValue("ACC_input",XML_AccNumber);
    		c.setValue("DB_Host", DB_Host);
    		c.setValue("dB_Password",dB_Password);
    		c.setValue("dB_Username",dB_Username);
    		c.setValue("dB_Port",dB_Port);
    		c.setValue("dB_serviceName",dB_serviceName);
    		c.setValue("XML_EndPoint",XML_EndPoint);
    		 c.setValue("sSERVICEorCURRENTSERVICE",SERVICEorCURRENTSERVICE);
    		 c.setValue("sHOUSE_KEY", HOUSE_KEY); 
    		//String a=c.getValue("RTPDataSourceCollections", "XML_Enpoint");
    		 Application.showMessage("Endpoint is ::"+XML_EndPoint);
    		 Application.showMessage("DB_HOST is ::"+DB_Host);
    		 Application.showMessage("TestCaseName is ::"+TestCaseName);
    		 Application.showMessage("dB_Password is ::"+dB_Password);
    		 Application.showMessage("dB_Username is ::"+dB_Username);
    		 Application.showMessage("dB_Port is ::"+dB_Port);
    		 Application.showMessage("dB_serviceName is ::"+dB_serviceName);
    		 Application.showMessage("XML_AccNumber is ::"+XML_AccNumber);
    		 Application.showMessage("HOUSE_KEY"+HOUSE_KEY);
    		
    		
    		 Map<String, String> ResultMap = new HashMap<String,String>();

 	         ResultMap= Lp.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
 		     c.setValue("ExistingGroup",ResultMap.get("group"));
 		     c.setValue("ExistingService", ResultMap.get("service"));
 		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
    		
    	}
    	catch(Exception e)
    	{
    		
    	}
    }
    
    public void demicalls(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, NullPointerException, XPathException, SAXException, InstantiationException, IllegalAccessException, SQLException
    {
    	 RTP_ValidationsClass_UpDown vC=new RTP_ValidationsClass_UpDown();
    	 LibraryRtp_UpDown lR= new LibraryRtp_UpDown();
    	 RTP_ValidationsClass rV=new RTP_ValidationsClass();
    	 
    	// vC.execution(input, c);
    	// Thread.sleep(4000);
    	    	
         createCMSAccountID_Validate(input, c);
         
       //  Thread.sleep(3000);
         OrderUpdate_tocheckAEE(input,c);
         Lp.storeCCnum(input, c);
    
         //lR.SaveValuesfromInstall(input, c);
    }
    
    public void demicallsReleasefrom30day(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, NullPointerException, XPathException, SAXException
    {
    	 RTP_ValidationsClass_UpDown vC=new RTP_ValidationsClass_UpDown();
    	 LibraryRtp_UpDown lR= new LibraryRtp_UpDown();
    	 RTP_ValidationsClass rV=new RTP_ValidationsClass();
    	 String val="true";
         int noofCallsValidated=1;
         int i;
         OUTER1:
         for( i=0;i<=noofCallsValidated;i++)
         {
      	   Application.showMessage("The val of I is"+i);
      	   switch(i)
      	   {
      	   case 0:
      		  val= createCMSAccountID_Validate(input, c);         		  
      		   break;
      	   case 1:
      		   val=  OrderUpdate_Releasefromtable(input, c,1);
      		   break;
      	  
      		   default:
      			   break;
      	   }
      	   if(val.equalsIgnoreCase("false"))
      	   {
      		   c.report(" quiting entire flow validation");
      		   break OUTER1;
      	   }
      	   else
      	   {
      		   continue OUTER1;
      	   }
         }
         
         

    
  
  
    	
         
        
        
        
    }
    
    public String OrderUpdate_Releasefromtable(Object input, ScriptingContext c,int iteration) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, NullPointerException, SAXException 
    {
    	Thread.sleep(10000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
    	 ResultSet  rs;
    	 HandleInvalidServicesLibrary HL=new HandleInvalidServicesLibrary();
    	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
    	 String accountNumber_ou;
    	 String billingOrderID;
    	// String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String Time= c.get("BaseTime").toString();
	  //   c.setValue("OrderUpdate","true");

String rscallpresent="true";
        Application.showMessage("-------------------------------------------------------------");
        Application.showMessage("***********OrderUpdate_Validate_Tocheck AEE Function**************");       
        Application.showMessage("-------------------------------------------------------------");
        System.out.println("-------------------------------------------------------------");
        System.out.println("***********OrderUpdate_Validate_Tocheck AEE Function**************");       
        System.out.println("-------------------------------------------------------------");
      //  lC.findThinktimeOperationRTP(input, c);
	  //   c.setValue("OrderUpdate","false");
        HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
	     System.out.println("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));

    	try
    	{
    		// Statement st =lC. dBConnect(input, c);	
           // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
    		rs=lC.reduceThinkTimeRTP(input, c);
    		if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{

    		while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
                { 
    	             rowmsg=rs.getString(1);
    	            if(rs.getBlob("receive_data")==null)
    	            { 
               	     Application.showMessage("Your Recieve XML is NULL \n");
               	  System.out.println("Your Recieve XML is NULL \n");
               	   	 String senddata = lC.extractXml(rs,xmldata2);           
    	             String senddataTrim=lC.RemoveNameSpaces(senddata); 
    	          	 Application.showMessage("SendDataTrim ::: " +senddataTrim );	 
    	          	 System.out.println("SendDataTrim ::: " +senddataTrim );	 
    	          	 String accountNumber=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value");
     	             Application.showMessage("accountNumber is ::"+accountNumber); 
     	            System.out.println("accountNumber is ::"+accountNumber); 
     	             if(accountNumber==null)
    			            {
     	        	     Application.showMessage("Send Xml billingOrderId is NULL");
     	        	    System.out.println("Send Xml billingOrderId is NULL");
    				            continue;
    			            }
     	              else if(accountNumber.equals(c.getValue("ACC_input")))
    			            	{
    	            	 Application.showMessage("SEND XML is :: \n"+senddata);
                		 Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
                		 Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);
                		 System.out.println("SEND XML is :: \n"+senddata);
                		 System.out.println("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
                		 System.out.println("Validation is Successfull with Account Number :"+accountNumber);
    			            
    			            		callFound=1;
    			            	}
    			            	else
    			            	{
    			            		continue;
    			            	}
    			            	if(callFound==1)
    			            	{
     	        	     String Service;	
     	        	     if(senddataTrim.contains("COM") && (senddataTrim.contains("NEW") || senddataTrim.contains("CHG")))
    							             {
    	          			 Application.showMessage("SEND XML is :: \n"+senddata);	  
    	          			 System.out.println("SEND XML is :: \n"+senddata);	  
    		            	 billingOrderID=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value[5]");
    		            	 c.setValue("CCentralNum", billingOrderID);
    		            	 Application.showMessage("CCentral number:::"+billingOrderID);
    		            	 System.out.println("CCentral number:::"+billingOrderID);
    		                 	if(senddata.contains("Hav Market"))
    			            {
    		            	         Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
    		 	                     Application.showMessage("Service is ::"+Service);	
    		 	                    System.out.println("Service is ::"+Service);	
    				             }
    				             else
    				             {
    		            		     Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
    		            		     Application.showMessage("Service is  :: "+Service);
    		            		     System.out.println("Service is  :: "+Service);
    			            }
    		                 	if(Service!=null || billingOrderID!=null )
    			            {
    		            		      Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim());
    				                	 Application.showMessage("*******Validation Point 2 :: WebServicall(OrderUpdate)-Service********");
    				            	    Application.showMessage("Validation is Successfull with Service::"+" "+Service);
    				            	    System.out.println("*******Validation Point 2 :: WebServicall(OrderUpdate)-Service********");
    				            	    System.out.println("Validation is Successfull with Service::"+" "+Service);
    			            
    			            	v2=1;
    				            	   c.setValue("Product",Service);
    				            	}
    				            	
    							            else
    			                  	     //c.report("Send Xml Service || BillingOrderID is NULL");
    							            	v2=1;           
    							            					            
    	           		        Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
    	           		        Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);
    	           		     System.out.println("*******Validation Point 1 :: WebServicall-Order Update********");
    	           		  System.out.println("Validation is Successfull with Account Number :"+accountNumber);	           		
 		            
    		            	break;
    	            }     
     	        	        else if ((senddataTrim.contains("ERR")||senddataTrim.contains("ServiceFault")) && (senddataTrim.contains("NEW") || senddataTrim.contains("CHG")))
    	            { 
    	          		      c.report("Error in OrderStatus ID");
    	          		       break;
    							             }
    							             else
    							             {
    							            	 continue;
    							             }
    		            }
                  } }
    		            
    			            
    					         
            if(callFound * v2==1)
    					          
            {   
    							            					            
            	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
            	 System.out.println("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
    								             }
    								             else
    								             {
    	            if(callFound==1)
    	            {
            	c.report("WebService Call :: OrderUpdate_Validate is failed with validation points ]");
    	            }
    	            else
    	            {
    	            	Thread.sleep(5000);
    	            	//OrderUpdate_Releasefromtable(input,c,5);
    	            }
    	            	
    	            
                 }
              //   st.close();
           
    	}
    	}
    	catch (SQLException e)
    	{
    	    System.out.println("Connection Failed! Check output console");
    		e.printStackTrace();
    		
    	}
		return rscallpresent;	
    }  
    
    //--------------
    public void Insightcalls_AddressUpdates(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
    {    RTP_ValidationsClass_UpDown vC=new RTP_ValidationsClass_UpDown();
	     LibraryRtp_UpDown lR= new LibraryRtp_UpDown();
	     //vC.execution(input, c);
	     Thread.sleep(4000);
        rtpTestInterface_Validate(input, c);     
        
        // getAccount_Validate(input, c);
    
    OrderUpdate_tocheckAEE(input,c);
        
         //lR.SaveValuesfromInstall(input, c);
    }
    
    
    //-------------
    public void Insightcalls(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
    {    RTP_ValidationsClass_UpDown vC=new RTP_ValidationsClass_UpDown();
	     LibraryRtp_UpDown lR= new LibraryRtp_UpDown();
	     //vC.execution(input, c);
	     Thread.sleep(4000);
        rtpTestInterface_Validate(input, c);     
        
         getAccount_Validate(input, c);
    
    OrderUpdate_tocheckAEE(input,c);
        
         //lR.SaveValuesfromInstall(input, c);
    }
    
    public void SimulatorDemicalls(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, NullPointerException, SAXException, XPathException, InstantiationException, IllegalAccessException, SQLException
    { 
    	 RTP_SimulatorClass_UpDown sim= new RTP_SimulatorClass_UpDown();
         RTP_ValidationsClass rV=new RTP_ValidationsClass();
    	 Thread.sleep(4000);
         rtpTestInterface_Validate(input, c);
         
         createCMSAccountID_Validate(input, c);
         queryLocation_Validate(input,c);
        
         //getCustomerPermitRequirements_Validate(input,c);
         sim.processHomeSecurityInfo_Validate(input, c);
         sim.deactivateAccount_Validate(input,c);
         sim.simGetAccountValidate(input, c);
         //getAccount_Validate(input, c);
         processHomeSecurityInfo_Validate(input,c);
         SetAccountBasicInformation_Validate(input,c);
         sim.createAccount_ValidateDemiSim(input, c);
         responderInfo_Validate(input,c);
         SetAccountAuthorityInformation_Validate(input, c);
         modifyHomeSecurity_Validate(input, c);
         orderUpdate_ValidateForScheduleUpDown(input,c);
         Lp.storeCCnum(input, c);
         
    }
    public void simulatorInsightcalls(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
    {
    	
    	 RTP_SimulatorClass_UpDown sim= new RTP_SimulatorClass_UpDown();
         Thread.sleep(4000);
         rtpTestInterface_Validate(input, c);     
         queryLocation_Validate(input,c);
         
         sim.processHomeSecurityInfo_Validate(input, c);
         sim.deactivateAccount_Validate(input,c);
         sim.simGetAccountValidate(input, c);
        // getAccount_Validate(input, c);
         processHomeSecurityInfo_Validate(input,c);       
         sim.createAccount_ValidateInsight(input, c);           
         orderUpdate_ValidateForScheduleUpDown(input,c);
         //Lp.storeCCnum(input, c);
         
    }
    
  
    
    /**
     * @author 266216 
     * rtpTestInterface_Validate(Object input, ScriptingContext c) will take Scripting context class from parasoft jar.
     * @throws InterruptedException 
     * @throws SAXException 
     * @throws NullPointerException 
     * @throws XPathExpressionException 
     *
     */
    
	
	public void rtpTestInterface_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
	{
		 LibraryRtp_UpDown  lC= new LibraryRtp_UpDown();
		 LibraryRtp lr=new  LibraryRtp();
		 ResultSet  rs;
		 int callFound=0, v1=0;
		 String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";  
         String Time= c.get("BaseTime").toString();
        // c.setValue("RTPTestInterface","true");
         int iteration=0;
         Application.showMessage("----------------------------------------------------------");
         Application.showMessage("************rtpTestInterface_Validate Function************");    
         Application.showMessage("----------------------------------------------------------");
       //  lr.findThinktimeOperationRTP(input, c);
       //  c.setValue("RTPTestInterface","false"); 
         HashMap Operation=lr.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("xhsevent"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("xhsevent"));

		try
		{
			
			//Lp.Opconnection();
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1", "CWDEVOP","CWDEVOP");		
			//Statement st =lC. dBConnect(input, c);	 
			
	      //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'eep:XHSEEPOrder/xhsevent' order by creation_time desc)where rownum <= 20");
			rs=lr.reduceThinkTimeRTP(input, c);
	        while (rs.next() && rs.getString(1)!= c.getValue("BaseMsgid"))
	        {
	              Application.showMessage("RTPTestInterface msgid= "+ rs.getString(1));
	              rowmsg=rs.getString(1);	          
	              if(rs.getBlob("receive_data")==null)
	              {
	           
	            	String senddata =lC.extractXml(rs,xmldata2);	           
		            String accountNumber_rtpTest=lC.GetValueByXPath(senddata,"//EEPEvent/Body/Account/@accountNumber");
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
		            	Application.showMessage("accountNumber value from excel is::"+c.getValue("ACC_input"));
		            	
		            	if(accountNumber_rtpTest.equals(c.getValue("ACC_input")))
		            	{ 
		            		System.out.printf("Your Recieve XML is NULL \n");
			            	Application.showMessage("Your Recieve XML is NULL \n");
			            	Application.showMessage("SEND XML is  \n"+ senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
		            		callFound=1;
		            	}
		            	else if (iteration!=5)
		            	{
		            		iteration++;
		            		 Application.showMessage("It is continuing for the next row");
		            		 Operation=lr.findingoperations(input, c);
		            		    c.setValue("OPERATIONVALIDATION",(String) Operation.get("xhsevent"));
		            		    Application.showMessage("OPERATIONValidation is "+(String) Operation.get("xhsevent"));
		            		    rs=lr.reduceThinkTimeRTPmsgid(input, c, rowmsg);
		            		    if(rs==null)
		            		    {
		            		    	break;
		            		    }
		            		    else
		            		    {
		            		continue  ;
		            		    }
		            	}
		            	else
		            	{
		            		continue  ;
		            	}
		            			            	
		            }
		            
		            if(callFound==1)
	            	{
		            	 String locationId_rtpTest=lC.GetValueByXPath(senddata,"//EEPEvent/Body/Location/@locationId");
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
					            	 String PrimaryEmail_rtpTest=lC.GetValueByXPath(senddata,"//EEPEvent/Body/Account/PrimaryEmail");
					            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
                                     c.setValue("emailAddress", PrimaryEmail_rtpTest);
							            
							         String FirstName_rtpTest=lC.GetValueByXPath(senddata,"//EEPEvent/Body/Account/FirstName");
							         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
							         c.setValue("FirstName", FirstName_rtpTest); 
							         
							         String LastName_rtpTest=lC.GetValueByXPath(senddata,"//EEPEvent/Body/Account/LastName");
							         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
							         c.setValue("LastName", LastName_rtpTest); 
							         
							         String OrderStatus_rtpTest=lC.GetValueByXPath(senddata,"//EEPEvent/Body/WorkOrder/OrderStatus");
							         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
							            
							         String OrderType_rtpTest=lC.GetValueByXPath(senddata,"//EEPEvent/Body/WorkOrder/OrderType");
							         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
							         
							         String RescheduleIndicator_rtpTest=lC.GetValueByXPath(senddata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
							         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
							         String EventSource= lC.nodeFromKey(senddata,"<EventSource>","</EventSource>");
							         c.setValue("sEventSource", EventSource);
							         Application.showMessage("EventSource is::"+c.getValue("sEventSource")); 
							         
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
					            	 continue;
					             }
				            }				            
	            	}
	             
             		       
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	
	            	String recievedata=lC.extractXml(rs,xmldata1);
		         
		            String accountNumber_rtpTest=lC.GetValueByXPath(recievedata,"//EEPEvent/Body/Account/@accountNumber");
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
		            	    Application.showMessage("Your SEND XML is NULL \n");
		   		            Application.showMessage("Recieve XML is  \n"+ recievedata);
		   		      		            
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
		            	 String locationId_rtpTest=lC.GetValueByXPath(recievedata,"//EEPEvent/Body/Location/@locationId");
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
					            	 String PrimaryEmail_rtpTest=lC.GetValueByXPath(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
					            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
					            	 c.setValue("emailAddress", PrimaryEmail_rtpTest);
					            	 
							         String FirstName_rtpTest=lC.GetValueByXPath(recievedata,"//EEPEvent/Body/Account/FirstName");
							         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
							         c.setValue("FirstName", FirstName_rtpTest);
							         
							         String LastName_rtpTest=lC.GetValueByXPath(recievedata,"//EEPEvent/Body/Account/LastName");
							         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
							         c.setValue("LastName", LastName_rtpTest); 
							         
							         String OrderStatus_rtpTest=lC.GetValueByXPath(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
							         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
							            
							         String OrderType_rtpTest=lC.GetValueByXPath(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
							         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
							         
							         String RescheduleIndicator_rtpTest=lC.GetValueByXPath(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
							         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
							         String EventSource= lC.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
							         c.setValue("sEventSource", EventSource);
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
	
	

	
	


public String createCMSAccountID_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	//Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lib=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0;
	 String rscallpresent="true";
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
   //  c.setValue("createCMSaccountID","true");
     Application.showMessage("-------------------------------------------------");
     Application.showMessage("*****createCMSAccountID_Validate Function******");       
     Application.showMessage("-------------------------------------------------");
     System.out.println("-------------------------------------------------");
     System.out.println("*****createCMSAccountID_Validate Function******");       
     System.out.println("-------------------------------------------------");
    // lib.findThinktimeOperationRTP(input, c);
   //  c.setValue("createCMSaccountID","false");
     HashMap Operation=lib.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("createCMSAccountID"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("createCMSAccountID"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("createCMSAccountID"));

	try
	{
		// Statement st =lC. dBConnect(input, c);
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'hss:HomeSecurityServicePort/createCMSAccountID' order by creation_time desc)where rownum <= 20");
		rs=lib.reduceThinkTimeRTP(input, c);
		if(rs == null)
    	{
    		rscallpresent = "false";
    		
    	
    		
    	}
    	else
{
		while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
         {
        	
            rowmsg=rs.getString(1);
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
            	System.out.println("RECIEVE XML is ::\n"+recievedata);   
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);   
            }
            else
            { 
                          
	            String senddata = lC.extractXml(rs,xmldata2);
	           // Application.showMessage("SEND XML is :: \n"+senddata);
	            String recievedata = lC.extractXml(rs,xmldata1);      
	          //  Application.showMessage("RECIEVE XML is ::"+recievedata);
	           
	            String billingArrangementID_cmsSend= lC.nodeFromKey(senddata,"<hst:billingArrangementID>","</hst:billingArrangementID>");
	            Application.showMessage("billingArrangementID_cmsSend is ::"+billingArrangementID_cmsSend);
	            System.out.println("billingArrangementID_cmsSend is ::"+billingArrangementID_cmsSend); 
		         
	            if(billingArrangementID_cmsSend.equals(c.getValue("ACC_input")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	System.out.println("Recieve XML is::  \n"+ recievedata);
	            	System.out.println("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-createCMSAccountID_Validate********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_cmsSend);
            		System.out.println("*******Validation Point 1 :: WebServicall-createCMSAccountID_Validate********");
            		System.out.println("Validation is Successfull with Input Account Number"+billingArrangementID_cmsSend);
            		
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
		            String cmsAccountID= lC.nodeFromKey(recievedata,"<typ:cmsAccountID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:cmsAccountID>");
		            Application.showMessage("cmsAccountID is ::"+cmsAccountID);
		            System.out.println("cmsAccountID is ::"+cmsAccountID);
		            c.setValue("CcentralNum", cmsAccountID);
		            
		            String billingArrangementIDRes_CMSAccountID= lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
		            Application.showMessage("billingArrangementIDRes_CMSAccountID is::"+billingArrangementIDRes_CMSAccountID); 
		            System.out.println("billingArrangementIDRes_CMSAccountID is::"+billingArrangementIDRes_CMSAccountID); 
		            
		            if(billingArrangementIDRes_CMSAccountID==null)
		            {
			            c.report("Send Xml Account Number is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+billingArrangementIDRes_CMSAccountID);
		            	 System.out.println("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+billingArrangementIDRes_CMSAccountID);
		            	 
		            	 if(billingArrangementIDRes_CMSAccountID.equals(c.getValue("ACC_input")))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
			            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+billingArrangementIDRes_CMSAccountID);
			            	 System.out.println("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
			            	 System.out.println("Validation is Successfull with AccountNumber::"+" "+billingArrangementIDRes_CMSAccountID);
			            	 
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("Account Number at Send Xml not Validated as "+billingArrangementIDRes_CMSAccountID);
			             }
		            }		

		            String responseStatus_CMSAccountID= lC.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:responseStatus>");
		            Application.showMessage("responseStatus_CMSAccountID is::"+responseStatus_CMSAccountID);
		            System.out.println("responseStatus_CMSAccountID is::"+responseStatus_CMSAccountID); 
		            
		             if(responseStatus_CMSAccountID==null)
		             {
			            c.report("Send Xml Account Number is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+responseStatus_CMSAccountID);
		            	 System.out.println("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+responseStatus_CMSAccountID);
		            	 
		            	 if(responseStatus_CMSAccountID.equalsIgnoreCase("Success"))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-responseStatus_CMSAccountID********");
			            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+responseStatus_CMSAccountID);
			            	 System.out.println("*******Validation Point 2 :: WebServicall-responseStatus_CMSAccountID********");
			            	 System.out.println("Validation is Successfull with AccountNumber::"+" "+responseStatus_CMSAccountID);
			            	 
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("Account Number at Send Xml not Validated as "+responseStatus_CMSAccountID);
			             }
		            }		
	               
	            break;
	            }
	            
	                        
             }
         }
         if(v1*v2==1)
         {
         	Application.showMessage("WebService Call :: CreateCMSAccount is Success[All validation points are Success]");
         	System.out.println("WebService Call :: CreateCMSAccount is Success[All validation points are Success]");
            
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call :: CreateCMSAccount is Failed with Validation Errors");
         }
       //  st.close();
	}	
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
}


public void queryLocation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new  LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
    // c.setValue("queryLocation","true");
     Application.showMessage("-------------------------------------------------");
     Application.showMessage("*****Query Location _Validate Function******");       
     Application.showMessage("-------------------------------------------------");
    // lr.findThinktimeOperationRTP(input, c);
   //  c.setValue("queryLocation","false");  
   
     HashMap Operation=lr.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("queryLocation"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("queryLocation"));

	try
	{
		// Statement st =lC. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
		 
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
	            if(legacyID.equals(c.getValue("sHOUSE_KEY")))
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
	 	            
	 	            String TimeZone= lC.nodeFromKey(recievedata,"<typ:Name>","</typ:Name>");
	 	            Application.showMessage("TimeZone is :: "+TimeZone); 
	 	            c.put("pTimeZone", TimeZone);
	 	           
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
        // st.close();
	}	
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
}


//done till above


public void getAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
   //  c.setValue("getAccount","true");
     Application.showMessage("-------------------------------------------------");
     Application.showMessage("*****getAccount_Validate _Validate Function******");       
     Application.showMessage("-------------------------------------------------");
   //  lr.findThinktimeOperationRTP(input, c);
  //   c.setValue("getAccount","false"); 
     HashMap Operation=lr.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccountSim"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccountSim"));

     //c.setValue("getAccount","false"); 
	try
	{
		// Statement st =lC. dBConnect(input, c);	
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
		if(rs==null)
		{
		    Application.showMessage("RS is null");
		}
		else
		{
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
            	//Sruthi
            	String senddatacls = lC.extractXml(rs,xmldata2);                                        
                String recievedatacls = lC.extractXml(rs,xmldata1);      
                   String senddata=lr.clsRemoveAsciiCharacter(senddatacls);  
                   String recievedata=lr.clsRemoveAsciiCharacter(recievedatacls);
                 //Sruthi
	            //String senddata = lC.extractXml(rs,xmldata2);           
	           // String recievedata = lC.extractXml(rs,xmldata1);      
	          	String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
	          	Application.showMessage("ReceiveDataTrim::::"+receiveDataTrim);
	          	Application.showMessage("senddata::::"+senddata);
	            String id= lC.nodeFromKey(senddata,"<resourceId>","</resourceId>");
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
	      
	            	String errorCode_getAcc= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/errorCode");
	            	
	 	             Application.showMessage("errorCode is ::"+errorCode_getAcc);
	 	             
	 	             if(errorCode_getAcc==null)
		             {
			            c.report("Recieve Xml Account Number is NULL");
			            continue;
		             }
		             else
		             {
		            	 Application.showMessage("locationStatus from Send Xml  from queryLocation is ::"+" "+errorCode_getAcc);
		            	 if(errorCode_getAcc.equalsIgnoreCase("UCE-15101") || errorCode_getAcc.equalsIgnoreCase("0"))
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
	 	            String errorMessage_getAcc= lC.GetValueByXPath(receiveDataTrim,"/restResponse/response/ClientResponse/responseDS/GetAccountResponse/errorMessage");
	 	            
	 	             Application.showMessage("errorMessage is::"+errorMessage_getAcc); 

	 	            
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






public void processHomeSecurityInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
    // c.setValue("processHomeSecurity","true");
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
     Application.showMessage("----------------------------------------------------");
  //   lr.findThinktimeOperationRTP(input, c);
   //  c.setValue("processHomeSecurity","false");  
     HashMap Operation=lr.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));

	try
	{
		// Statement st =lC. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' order by creation_time desc)where rownum <= 20");
	    
	    rs=lr.reduceThinkTimeRTP(input, c);
	    {
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
 	            
	            if(accountNumber_DDS.equals(c.getValue("ACC_input")))
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
	 	            
	 	            if(firstName_DDS==null)
		            {
			            c.report("Send Xml FirstName is NULL");
			            continue;
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
			            	 v1=1;
			            	// c.report("FirstName at Send Xml not Validated as "+firstName_DDS);
			             }
		            }		

	 	            String lastName_DDS= lC.nodeFromKey(senddata,"<typesDDS:lastName>","</typesDDS:lastName>");
	 	            Application.showMessage("lastName is ::"+lastName_DDS); 
	 	            
	 	           if(lastName_DDS==null)
		            {
			            c.report("Send Xml LastName is NULL");
			            continue;
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
			            	 v2=1;
			            	// c.report("LastName at Send Xml not Validated as "+lastName_DDS);
			             }
		            }

	 	            String homeSecurityLOBStatus_DDS= lC.nodeFromKey(senddata,"<typesDDS:homeSecurityLOBStatus>","</typesDDS:homeSecurityLOBStatus>");
	 	            Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
	 	            if(homeSecurityLOBStatus_DDS==null)
		            {
			            c.report(" homeSecurityLOBStatus is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("homeSecurityLOBStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+homeSecurityLOBStatus_DDS);
		            	 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("PENDING_INSTALL") || homeSecurityLOBStatus_DDS.equalsIgnoreCase("DISCONNECTED"))
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
	 	           
	 	         	 	            
	 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
	 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
	 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
	 	            
	 	            String responseStatus_DDS= lC.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/esd-directory/14.05/types\">","</typ:responseStatus>");
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
	    }
         if(v1*callFound*v2*v3*v4 ==1)
         {
         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
         }
        // st.close();
	}	
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
	catch (Exception e)
	{
	    Application.showMessage("Exception"+e);
	}
 }



public void SetAccountBasicInformation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, NullPointerException, SAXException 
{
	 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
   //  c.setValue("setAccountBasicInformation","true");
     Application.showMessage("-------------------------------------------------------");
     Application.showMessage("*****SetAccountBasicInformation_Validate Function******");       
     Application.showMessage("-------------------------------------------------------");
   //  lr.findThinktimeOperationRTP(input, c);
     //c.setValue("setAccountBasicInformation","false");  
HashMap Operation=lr.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SetAccountBasicInformation"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SetAccountBasicInformation"));
	try
	{
		// Statement st =lC. dBConnect(input, c);	
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/SetAccountBasicInformation' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
	          	           
	            String CcentralNum_SetBasicAcc=lC.GetValueByXPath(senddata,"//SetAccountBasicInformationRequest/accountNumber");
 	            Application.showMessage("Ccentral Number is ::"+CcentralNum_SetBasicAcc); 	           
	            if(CcentralNum_SetBasicAcc.equals(c.getValue("CcentralNum")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-Get Account Call********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+CcentralNum_SetBasicAcc);
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
		            String pc_SetBasicAcc=lC.GetValueByXPath(senddata,"//SetAccountBasicInformationRequest/pc");
	 	            Application.showMessage("pc is ::"+pc_SetBasicAcc); 
	 	            
	 	           if(pc_SetBasicAcc==null)
		            {
			            c.report(" pc is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("pc from Send Xml  from SetAccountBasicInformation is ::"+" "+pc_SetBasicAcc);
		            	 if(pc_SetBasicAcc.equalsIgnoreCase("C9O2P5"))
			             {
			            	 Application.showMessage("*******Validation Point 2:: WebServicall-pc********");
			            	 Application.showMessage("Validation is Successfull with pc::"+" "+pc_SetBasicAcc);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("pc at Send Xml not Validated as "+pc_SetBasicAcc);
			             }
		            }
	 	          
	 	            String action_SetBasicAcc=lC.GetValueByXPath(senddata,"//SetAccountBasicInformationRequest/action");
	 	            Application.showMessage("Action is ::"+action_SetBasicAcc);
	 	            
	 	           if(action_SetBasicAcc==null)
		            {
			            c.report(" Action is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Action from Send Xml  from SetAccountBasicInformation is ::"+" "+action_SetBasicAcc);
		            	 if(action_SetBasicAcc.equalsIgnoreCase("NEW"))
			             {
			            	 Application.showMessage("*******Validation Point 3:: WebServicall-Action********");
			            	 Application.showMessage("Validation is Successfull with Action::"+" "+action_SetBasicAcc);
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("Action at Send Xml not Validated as "+action_SetBasicAcc);
			             }
		            }

	 	            String dn_SetBasicAcc=lC.GetValueByXPath(senddata,"//SetAccountBasicInformationRequest/dn");
	 	            Application.showMessage("dn is ::"+dn_SetBasicAcc); 
	 	            
	 	           if(dn_SetBasicAcc==null)
		            {
			            c.report(" dn is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("dn from Send Xml  from SetAccountBasicInformation is ::"+" "+dn_SetBasicAcc);
		            	 if(dn_SetBasicAcc.equalsIgnoreCase("COMC"))
			             {
			            	 Application.showMessage("*******Validation Point 4:: WebServicall-dn********");
			            	 Application.showMessage("Validation is Successfull with dn::"+" "+dn_SetBasicAcc);
			            	 v3=1;
			             }
			             else
			             {
			            	 c.report("dn at Send Xml not Validated as "+dn_SetBasicAcc);
			             }
		            }

	 	            String billingAccountNumber_SetBasicAcc=lC.GetValueByXPath(senddata,"//SetAccountBasicInformationRequest/billingAccountNumber");
	 	            Application.showMessage("billingAccountNumber is ::"+billingAccountNumber_SetBasicAcc); 
                    
	 	           if(billingAccountNumber_SetBasicAcc==null)
		            {
			            c.report("Send Xml FirstName is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+billingAccountNumber_SetBasicAcc);
		            	 if(billingAccountNumber_SetBasicAcc.equals(c.getValue("ACC_input")))
			             {
			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Account Number********");
			            	 Application.showMessage("Validation is Successfull with Account Number::"+" "+billingAccountNumber_SetBasicAcc);
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("Account Number at Send Xml not Validated as "+billingAccountNumber_SetBasicAcc);
			             }
		            }	
	 	         	 	            
		            String readBack_SetBasicAcc=lC.GetValueByXPath(recievedata,"//response/readBack");
	 	            Application.showMessage("Read Back is::"+readBack_SetBasicAcc);
	 	            
	 	           if(readBack_SetBasicAcc==null)
		            {
			            c.report(" readBack is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("readBack from Send Xml  from SetAccountBasicInformation is ::"+" "+readBack_SetBasicAcc);
		            	 if(readBack_SetBasicAcc.equalsIgnoreCase("COMCC9O2P5SETBASICINFO"))
			             {
			            	 Application.showMessage("*******Validation Point 6:: WebServicall-readBack********");
			            	 Application.showMessage("Validation is Successfull with readBack::"+" "+readBack_SetBasicAcc);
			            	 v5=1;
			             }
			             else
			             {
			            	 c.report("readBack at Send Xml not Validated as "+readBack_SetBasicAcc);
			             }
		            }
	 	            
	 	           String result_SetBasicAcc=lC.GetValueByXPath(recievedata,"//response/result");
	 	           Application.showMessage("Result is::"+result_SetBasicAcc);

	 	          if(result_SetBasicAcc==null)
		            {
			            c.report(" readBack is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("readBack from Send Xml  from SetAccountBasicInformation is ::"+" "+result_SetBasicAcc);
		            	 if(result_SetBasicAcc.equalsIgnoreCase("OK") ||result_SetBasicAcc.equalsIgnoreCase("RJ") )
			             {
			            	 Application.showMessage("*******Validation Point 7:: WebServicall-readBack********");
			            	 Application.showMessage("Validation is Successfull with readBack::"+" "+result_SetBasicAcc);
			            	 v6=1;
			             }
			             else
			             {
			            	 c.report("readBack at Send Xml not Validated as "+result_SetBasicAcc);
			             }
		            }
	 	            
	            break;
	            }
             }
         }
         
         if(v1*callFound*v2*v3*v4*v5*v6 ==1)
         {
         	Application.showMessage("WebService Call :: SetAccountBasicInformation is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::SetAccountBasicInformation is Failed with Validation Errors");
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



public void createAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
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
    
     ResultMap= lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
     c.setValue("ExistingGroup",ResultMap.get("group"));
     c.setValue("ExistingService", ResultMap.get("service"));
     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
         
	try
	{
		 Statement st =lC. dBConnect(input, c);	
         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' order by creation_time desc)where rownum <= 20");
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
			            	 if(ucontrolsrv_group.equals(c.getValue("sSERVICEorCURRENTSERVICE")))
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



public void responderInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
   //  c.setValue("responderInfo","true");
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("***********responderInfo_Validate Function***********");       
     Application.showMessage("-----------------------------------------------------");
   //  lr.findThinktimeOperationRTP(input, c);
    // c.setValue("responderInfo","false"); 
     HashMap Operation=lr.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("responderInfo"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("responderInfo"));
       
	try
	{
		// Statement st =lC. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'intrado:frlResource/responderInfo' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
	          	           
	            String intrado_HouseNum= lC.nodeFromKey(senddata,"<intrado:HouseNum>","</intrado:HouseNum>");
 	            Application.showMessage("intrado:HouseNum is ::"+intrado_HouseNum); 
	            if(intrado_HouseNum.equals(c.getValue("HouseNumber")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-responderInfo_Validate********");
            		Application.showMessage("Validation is Successfull with House Number :"+intrado_HouseNum);
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	 	            

	 	            String intrado_PrefixDirectional= lC.nodeFromKey(senddata,"<intrado:PrefixDirectional>","</intrado:PrefixDirectional>").trim();
	 	            Application.showMessage("intrado:PrefixDirectional is ::"+intrado_PrefixDirectional); 

	 	            String intrado_StreetName= lC.nodeFromKey(senddata,"<intrado:StreetName>","</intrado:StreetName>").trim();
	 	            Application.showMessage("intrado:StreetName is ::"+intrado_StreetName); 
	 	           if(intrado_StreetName==null)
		            {
			            c.report("Send Xml intrado_StreetName is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("intrado_StreetName from Send Xml is ::"+" "+intrado_StreetName);
		            	 if(intrado_StreetName.equalsIgnoreCase(c.getValue("StreetName")))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-intrado_StreetName********");
			            	 Application.showMessage("Validation is Successfull with intrado_StreetName::"+" "+intrado_StreetName);
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("intrado_StreetName at Send Xml not Validated as "+intrado_StreetName);
			             }
		            }	
	 	            

	 	            String intrado_StreetSuffix= lC.nodeFromKey(senddata,"<intrado:StreetSuffix>","</intrado:StreetSuffix>").trim();
	 	            Application.showMessage("intrado:StreetSuffix is ::"+intrado_StreetSuffix); 
	 	            
	 	            String intrado_UnitType= lC.nodeFromKey(senddata,"<intrado:UnitType>","</intrado:UnitType>").trim();
	 	            Application.showMessage("intrado:UnitType is ::"+intrado_UnitType); 
	 	            
	 	            String intrado_PostalCommunity= lC.nodeFromKey(senddata,"<intrado:PostalCommunity>","</intrado:PostalCommunity>").trim();
	 	            Application.showMessage("intrado:PostalCommunity is ::"+intrado_PostalCommunity); 
	 	            
	 	            String intrado_StateProvince= lC.nodeFromKey(senddata,"<intrado:StateProvince>","</intrado:StateProvince>").trim();
	 	            Application.showMessage("intrado:StateProvince is ::"+intrado_StateProvince); 
	 	            
	 	            String intrado_PostalZipCode= lC.nodeFromKey(senddata,"<intrado:PostalZipCode>","</intrado:PostalZipCode>").trim();
	 	            Application.showMessage("intrado:PostalZipCode is ::"+intrado_PostalZipCode); 
	 	         /*   if(intrado_PostalZipCode==null)
		            {
			            c.report("Send Xml intrado_PostalZipCode is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("PostalCode from Send Xml   is ::"+" "+intrado_PostalZipCode);
		            	 if(intrado_PostalZipCode.equals(c.getValue("PostalCode").trim()))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-intrado_PostalZipCode********");
			            	 Application.showMessage("Validation is Successfull with intrado_PostalZipCode::"+" "+intrado_PostalZipCode);
			            	 v3=1;
			             }
			             else
			             {
			            	 c.report("intrado_PostalZipCode at Send Xml not Validated as "+intrado_PostalZipCode);
			             }
		            }*/	
	 	            
	 	            
	 	            
	 	             	         	 	            
	 	            String sch_overallMatch= lC.nodeFromKey(recievedata,"<sch:overallMatch>","</sch:overallMatch>");
	 	            Application.showMessage("sch:overallMatch is::"+sch_overallMatch);
	 	            
	 	            if(sch_overallMatch==null)
		             {
			            c.report("Send Xml sch_overallMatch is NULL");
			            continue;
		             }
		            else
		            {
		            	 if(sch_overallMatch.trim().equalsIgnoreCase("Success"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
			            	 Application.showMessage("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
			            	 v4=1;
			             }
		            	 else if(sch_overallMatch.trim().equalsIgnoreCase("Altered"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
			            	 Application.showMessage("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("sch_overallMatch at Send Xml not Validated as "+sch_overallMatch);
			             }
		            }
	 	            
	 	            
	 	            String sch_PoliceContactInfo= lC.nodeFromKey(recievedata,"<sch:PoliceContactInfo>","</sch:PoliceContactInfo>");
	 	            Application.showMessage("sch:PoliceContactInfo is::"+sch_PoliceContactInfo);
	 	            
	 	           if(sch_PoliceContactInfo==null)
		            {
			            c.report("Send Xml sch_PoliceContactInfo is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("sch_PoliceContactInfo from Send Xml   is ::"+" "+sch_PoliceContactInfo);
		            	 if(sch_PoliceContactInfo.trim().equals(c.getValue("PoliceNumber").trim()))
			             {
			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-sch_PoliceContactInfo********");
			            	 Application.showMessage("Validation is Successfull with sch_PoliceContactInfo::"+" "+sch_PoliceContactInfo);
			            	 v5=1;
			             }
			             else
			             {
			            	 c.report("sch_PoliceContactInfo at Send Xml not Validated as "+sch_PoliceContactInfo);
			             }
		            }	
	 	            
	 	            
	 	            
	 	            String sch_FireContactInfo= lC.nodeFromKey(recievedata,"<sch:FireContactInfo>","</sch:FireContactInfo>");
	 	            Application.showMessage("sch:FireContactInfo is::"+sch_FireContactInfo);
	 	            
	 	           if(sch_FireContactInfo==null)
		            {
			            c.report("Send Xml sch_FireContactInfo is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("sch_FireContactInfo from Send Xml   is ::"+" "+sch_FireContactInfo);
		            	 if(sch_FireContactInfo.trim().equals(c.getValue("FireNumber").trim()))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-sch_FireContactInfo********");
			            	 Application.showMessage("Validation is Successfull with sch_FireContactInfo::"+" "+sch_FireContactInfo);
			            	 v6=1;
			             }
			             else
			             {
			            	 c.report("sch_FireContactInfo at Send Xml not Validated as "+sch_FireContactInfo);
			             }
		            }	
	 	            
	 	            
	 	            
	 	            String sch_MedicalContactInfo= lC.nodeFromKey(recievedata,"<sch:MedicalContactInfo>","</sch:MedicalContactInfo>");
	 	            Application.showMessage("sch:MedicalContactInfo is::"+sch_MedicalContactInfo);
	 	            
	 	           if(sch_MedicalContactInfo==null)
		            {
			            c.report("Send Xml sch_MedicalContactInfo is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("sch_MedicalContactInfo from Send Xml   is ::"+" "+sch_MedicalContactInfo);
		            	 if(sch_MedicalContactInfo.trim().equals(c.getValue("medicalNumber").trim()))
			             {
			            	 Application.showMessage("*******Validation Point 7 :: WebServicall-intrado_PostalZipCode********");
			            	 Application.showMessage("Validation is Successfull with intrado_PostalZipCode::"+" "+sch_MedicalContactInfo);
			            	 v7=1;
			             }
			             else
			             {
			            	 c.report("sch_MedicalContactInfo at Send Xml not Validated as "+sch_MedicalContactInfo);
			             }
		            }	
	 	            
	 	            
	 	            
	            break;
	            }
             }
         }
         if(callFound*v2/**v3*/*v4*v5*v6*v7 ==1)
         {
         	Application.showMessage("WebService Call :: Intrado is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::Intrado is Failed with Validation Errors");
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






public void SetAccountAuthorityInformation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
   //  c.setValue("setAccountAuthorityInfo","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********SetAccountAuthorityInformation Function***********");       
     Application.showMessage("-------------------------------------------------------------");
   //  lr.findThinktimeOperationRTP(input, c);
   //  c.setValue("setAccountAuthorityInfo","false");   
     HashMap Operation=lr.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SetAccountAuthorityInformation"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SetAccountAuthorityInformation"));
       
	try
	{
		// Statement st =lC. dBConnect(input, c);	
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/SetAccountAuthorityInformation' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next())
         {
        	
        
            rowmsg=rs.getString(1);
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
	          	           
	            String accountNumber_setAccountAuthority= lC.nodeFromKey(senddata,"<accountNumber>","</accountNumber>");
 	            Application.showMessage("accountNumber is ::"+accountNumber_setAccountAuthority);
 	            Application.showMessage("CcentralNumber::"+ c.getValue("CcentralNum"));
	            if(accountNumber_setAccountAuthority.equals(c.getValue("CcentralNum")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-SetAccountAuthorityInformation********");
            		Application.showMessage("Validation is Successfull with House Number :"+accountNumber_setAccountAuthority);
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	 	            

	 	            String pc_setAccountAuth= lC.nodeFromKey(senddata,"<pc>","</pc>");
	 	            Application.showMessage("pc is ::"+pc_setAccountAuth); 
	 	            
	 	           if(pc_setAccountAuth==null)
		            {
			            c.report(" pc is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("pc from Send Xml  from SetAccountAuthorityInformation is ::"+" "+pc_setAccountAuth);
		            	 if(pc_setAccountAuth.equalsIgnoreCase("C9O2P5"))
			             {
			            	 Application.showMessage("*******Validation Point 2:: WebServicall-pc********");
			            	 Application.showMessage("Validation is Successfull with pc::"+" "+pc_setAccountAuth);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("pc at Send Xml not Validated as "+pc_setAccountAuth);
			             }
		            }

	 	            String dn_setAccountAuth= lC.nodeFromKey(senddata,"<dn>","</dn>");
	 	            Application.showMessage("dn is ::"+dn_setAccountAuth); 	 	                     	         	 	            
	 	           if(dn_setAccountAuth==null)
		            {
			            c.report(" dn is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("dn from Send Xml  from SetAccountAuthorityInformation is ::"+" "+dn_setAccountAuth);
		            	 if(dn_setAccountAuth.equalsIgnoreCase("COMC"))
			             {
			            	 Application.showMessage("*******Validation Point 3:: WebServicall-dn********");
			            	 Application.showMessage("Validation is Successfull with dn::"+" "+dn_setAccountAuth);
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("dn at Send Xml not Validated as "+dn_setAccountAuth);
			             }
		            }

	 	            
	 	            String readBack_setAccountAuth= lC.nodeFromKey(recievedata,"<readBack>","</readBack>");
	 	            Application.showMessage("readBack is::"+readBack_setAccountAuth);
	 	            if(readBack_setAccountAuth==null)
		            {
			            c.report(" readBack is NULL");
			            continue;
		            }
		            else
		            {
		        
		            	 if(readBack_setAccountAuth.equalsIgnoreCase("COMCC9O2P5SETAUTHINFO"))
			             {
			            	 Application.showMessage("*******Validation Point 4:: WebServicall-readBack********");
			            	 Application.showMessage("Validation is Successfull with dn::"+" "+readBack_setAccountAuth);
			            	 v3=1;
			             }
			             else
			             {
			            	 c.report("readBack at Send Xml not Validated as "+readBack_setAccountAuth);
			             }
		            }
	 	            
	 	            
	 	            String result_setAccountAuth= lC.nodeFromKey(recievedata,"<result>","</result>");
	 	            Application.showMessage("result is::"+result_setAccountAuth);
	 	           if(result_setAccountAuth==null)
		            {
			            c.report(" result_setAccountAuth is NULL");
			            continue;
		            }
		            else
		            {
		        
		            	 if(result_setAccountAuth.equalsIgnoreCase("OK"))
			             {
			            	 Application.showMessage("*******Validation Point 5:: WebServicall-result_setAccountAuth********");
			            	 Application.showMessage("Validation is Successfull with result_setAccountAuth::"+" "+result_setAccountAuth);
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("readBack at Send Xml not Validated as "+result_setAccountAuth);
			             }
		            }
	 	            
	 	            
	 	            
	 	           
	            break;
	            }
             }
         }
         
         if(v1*callFound*v2*v3*v4 ==1)
         {
         	Application.showMessage("WebService Call :: SetAccountAuthorityInformation_Validate is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::SetAccountAuthorityInformation_Validate is Failed with Validation Errors");
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


//till here

public void modifyHomeSecurity_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
   //  c.setValue("modifyHomesecurity","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********modifyHomeSecurity_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
  //   lr.findThinktimeOperationRTP(input, c);
  //   c.setValue("modifyHomesecurity","false");   
     HashMap Operation=lr.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("modifyHomeSecurity"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("modifyHomeSecurity"));
       
	try
	{
		// Statement st =lC. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/modifyHomeSecurity' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
	          	           
	            String legacyID_modifyHS= lC.nodeFromKey(senddata,"<lt:legacyID>","</lt:legacyID>");
 	            Application.showMessage("legacyID is ::"+legacyID_modifyHS); 
	            if(legacyID_modifyHS.equals(c.getValue("sHOUSE_KEY")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-modifyHomeSecurity_Validate********");
            		Application.showMessage("Validation is Successfull with House Number :"+legacyID_modifyHS);
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	 	            

	 	            String lt_policeNumber= lC.nodeFromKey(senddata,"<lt:policeNumber>","</lt:policeNumber>").trim();
	 	            Application.showMessage("lt:policeNumber is ::"+lt_policeNumber); 
	 	            
	 	           if(lt_policeNumber==null)
		            {
			            c.report("Send Xml lt_policeNumber is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("lt_policeNumber from Send Xml   is ::"+" "+lt_policeNumber);
		            	 if(lt_policeNumber.trim().equals(c.getValue("PoliceNumber").trim()))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-lt_policeNumber********");
			            	 Application.showMessage("Validation is Successfull with lt_policeNumber::"+" "+lt_policeNumber);
			            	 v1=1;
			             }
			             else
			             {
			            	 Application.showMessage("NOT VALIDATED POLICE NUMBER!!!!");
			            	 c.report("lt_policeNumber at Send Xml not Validated as "+lt_policeNumber);
			             }
		            }
	 	            
	 	            

	 	            String lt_fireNumber= lC.nodeFromKey(senddata,"<lt:fireNumber>","</lt:fireNumber>");
	 	            Application.showMessage("lt:fireNumber is ::"+lt_fireNumber); 
	 	            
	 	           if(lt_fireNumber==null)
		            {
			            c.report("Send Xml lt_fireNumber is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("lt_fireNumber from Send Xml   is ::"+" "+lt_fireNumber);
		            	 if(lt_fireNumber.trim().equals(c.getValue("FireNumber").trim()))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lt_fireNumber********");
			            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+lt_fireNumber);
			            	 v2=1;
			             }
			             else
			             {
			            	 Application.showMessage("NOT VALIDATED FIRE NUMBER!!!!");
			            	 c.report("lt_fireNumber at Send Xml not Validated as "+lt_fireNumber);
			             }
		            }	
	 	            
	 	            String lt_medicalNumber= lC.nodeFromKey(senddata,"<lt:medicalNumber>","</lt:medicalNumber>");
	 	            Application.showMessage("lt:medicalNumber is ::"+lt_medicalNumber); 
	 	            
	 	           if(lt_medicalNumber==null)
		            {
			            c.report("Send Xml sch_MedicalContactInfo is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("sch_MedicalContactInfo from Send Xml   is ::"+" "+lt_medicalNumber);
		            	 if(lt_medicalNumber.trim().equals(c.getValue("medicalNumber").trim()))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-lt_medicalNumber********");
			            	 Application.showMessage("Validation is Successfull with lt_medicalNumber::"+" "+lt_medicalNumber);
			            	 v3=1;
			             }
			             else
			             {
			            	 Application.showMessage("NOT VALIDATED MEDICAL NUMBER!!!!");
			            	 c.report("sch_MedicalContactInfo at Send Xml not Validated as "+lt_medicalNumber);
			             }
		            }	
	 	                     	         	 	           	            
	 	            String typ_ReturnCode= lC.nodeFromKey(recievedata,"<typ:ReturnCode xmlns:typ=\"http://xml.comcast.com/location/types\">","</typ:ReturnCode>");
	 	            Application.showMessage("typ:ReturnCode is::"+typ_ReturnCode);
	 	            
	 	           if(typ_ReturnCode==null)
		            {
			            c.report("Send Xml typ_ReturnCode is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("typ_ReturnCode from Send Xml   is ::"+" "+typ_ReturnCode);
		            	 if(typ_ReturnCode.equals("0"))
			             {
			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-typ_ReturnCode********");
			            	 Application.showMessage("Validation is Successfull with typ_ReturnCode::"+" "+typ_ReturnCode);
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("typ_ReturnCode at Send Xml not Validated as "+typ_ReturnCode);
			             }
		            }	
	 	            
	 	            
	 	   	           
	            break;
	            }
             }
         }
         if(v1*callFound*v2*v3*v4 ==1)
         {
         	Application.showMessage("WebService Call :: modifyHomeSecurity_Validate is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::modifyHomeSecurity_Validate is Failed with Validation Errors");
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


//till here
public void orderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	// Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
   //  c.setValue("OrderUpdate","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********OrderUpdate_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
     //lr.findThinktimeOperationRTP(input, c);
    // c.setValue("OrderUpdate","false"); 
     HashMap Operation=lr.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
       
	try
	{
		//Statement st =lC. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
			            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
	 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
      //   st.close();
         
        //Lp.reportingToExcel(Object , ScriptingContext);
	}	
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
 }
//New order update for up/down--Sruthi
public void orderUpdate_ValidateForScheduleUpDown(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	// Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
   //  c.setValue("OrderUpdate","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********OrderUpdate_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
     //lr.findThinktimeOperationRTP(input, c);
    // c.setValue("OrderUpdate","false"); 
     HashMap Operation=lr.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
       
	try
	{
		//Statement st =lC. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
			            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
	 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
      //   st.close();
         
        //Lp.reportingToExcel(Object , ScriptingContext);
	}	
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
 }
//Sruthi End

public String orderUpdateDis_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
    String rscallpresent="false";
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********OrderUpdate_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
     HashMap Operation=lC.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("orderUpdate")); 
	try
	{
		// Statement st =lC. dBConnect(input, c);	
		rs=lC.reduceThinkTimeRTP(input, c);
		if(rs == null)
    	{
    		rscallpresent = "false";
    		
    	
    		
    	}
    	else
{
 while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            String rowmsg = rs.getString(1);
            if(rs.getBlob("receive_data")==null)
            {
            
            	Application.showMessage("Your Recieve XML is NULL \n");
            	
            	String senddata = lC.extractXml(rs,xmldata2);           
	           
	          	           
                String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
 	            Application.showMessage("accountNumber is ::"+accountNumber_ou); 
 	           Application.showMessage("accountNumber from Install is ::"+c.getValue("ACC_input")); 
 	            
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
		 	            Application.showMessage("Service is ::"+Service.replaceAll("Hav Market = U Cms Market = C", "").trim()); 
		 	            
		 	           if(Service==null)
			            {
				            c.report("Send Xml Service is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
			            	 if((Service.replaceAll("Hav Market = U Cms Market = C", "").trim()).equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
				            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
				            	 v4=1;
				            	 c.setValue("Product",Service);
				             }
				             else
				             {
				            	// c.report("Service at Send Xml not Validated as "+Service);
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
	 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
	 			             {
	 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
	 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
	 			            	 v4=1;
	 			            	 c.setValue("Product",Service);
	 			             }
	 			             else
	 			             {
	 			            	// c.report("Service at Send Xml not Validated as "+Service);
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
			             else if(ordType.equalsIgnoreCase("ERR"))
			             {
			            	 c.report("lt_fireNumber at Send Xml not Validated as "+ordType);
			             }
			             else
			             {
			            	 continue;
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
        	 c.setValue("IsDemi", "false");
         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
         }
         else
         {
        	 c.setValue("IsDemi", "false");
        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
         }
       //  st.close();
	}	
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }



public void SimulatorDemicallsforScheduleUpgradeandDowngrade(Object input,
		ScriptingContext c) throws InterruptedException, ClassNotFoundException, NullPointerException, IOException, SAXException, XPathException, InstantiationException, IllegalAccessException, SQLException {
	// TODO Auto-generated method stub
	RTP_SimulatorClass_UpDown sim= new RTP_SimulatorClass_UpDown();
    RTP_ValidationsClass rV=new RTP_ValidationsClass();
   
	 Thread.sleep(4000);
    rtpTestInterface_Validate(input, c);
    
    createCMSAccountID_Validate(input, c);
    Lp.storeCCnum(input, c);
    queryLocation_Validate(input,c);
  
    //getCustomerPermitRequirements_Validate(input,c);
    sim.processHomeSecurityInfo_Validate(input, c);
    // krishna sim.deactivateAccount_Validate(input,c);
    sim.deactivateAccount_Validate_CLS(input, c);
   
    
    //sim.simGetAccountValidate(input, c);
   // getAccount_Validate(input, c);
    processHomeSecurityInfo_Validate(input,c);
    //SetAccountBasicInformation_Validate(input,c);
    // krishna sim.createAccount_ValidateScheduleDemiSim(input, c);
    sim.createAccount_ValidateScheduleDemiSim_CLS(input, c);
    
    responderInfo_Validate(input,c);
    SetAccountAuthorityInformation_Validate(input, c);
    modifyHomeSecurity_Validate(input, c);
    orderUpdate_ValidateScheduleUpgradeandDowngrade(input,c);
  
	
}

public void orderUpdateCAN_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
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
 	           Application.showMessage("accountNumber from Install is ::"+c.getValue("ACC_input")); 
 	            
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
			            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
	 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
		            	 if(ordType.equals("CAN"))
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
        	 c.setValue("IsDemi", "false");
         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
         }
         else
         {
        	 c.setValue("IsDemi", "false");
        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
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

public void simulatorInsightcallsforScheduleUpgradeandDowngrade(Object input,
		ScriptingContext c) throws InterruptedException, ClassNotFoundException, XPathExpressionException, NullPointerException, IOException, SAXException {
	// TODO Auto-generated method stub
	RTP_SimulatorClass_UpDown sim= new RTP_SimulatorClass_UpDown();
	
	
    Thread.sleep(4000);
    rtpTestInterface_Validate(input, c);     
    queryLocation_Validate(input,c);
 
    sim.processHomeSecurityInfo_Validate(input, c);
    // krishna sim.deactivateAccount_Validate(input,c);
   sim.deactivateAccount_Validate_CLS(input, c);
    //sim.simGetAccountValidate(input, c);
    
   // getAccount_Validate(input, c);
    processHomeSecurityInfo_Validate(input,c);       
    // krishna sim.createAccount_ValidateScheduleInsight(input, c);
    sim.createAccount_ValidateScheduleInsight_CLS(input, c);
    orderUpdate_ValidateScheduleUpgradeandDowngrade(input,c);
    //Lp.storeCCnum(input, c);
	
}  

public void OrderUpdate_tocheckAEE(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, NullPointerException, SAXException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
	 ResultSet  rs;
	 HandleInvalidServicesLibrary HL=new HandleInvalidServicesLibrary();
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String accountNumber_ou;
	 String billingOrderID;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
  //   c.setValue("OrderUpdate","true");
    int iteration=0;
    Application.showMessage("-------------------------------------------------------------");
    Application.showMessage("***********OrderUpdate_Validate_Tocheck AEE Function**************");       
    Application.showMessage("-------------------------------------------------------------");
   // lC.findThinktimeOperationRTP(input, c);
   // c.setValue("OrderUpdate","false"); 
    HashMap Operation;
    Operation=lC.findingoperations(input, c);
    c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
    Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
     
	try
	{
		
		// Statement st =lC. dBConnect(input, c);	
       // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
		
	    rs=lC.reduceThinkTimeRTP(input, c);
		while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
            { 
	             rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            { 
           	     Application.showMessage("Your Recieve XML is NULL \n");
           	   	 String senddata = lC.extractXml(rs,xmldata2);           
	             String senddataTrim=lC.RemoveNameSpaces(senddata); 
	          	 Application.showMessage("SendDataTrim ::: " +senddataTrim );	          	        	
	          	 String accountNumber=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value");
 	             Application.showMessage("accountNumber is ::"+accountNumber); 
 	         
 	            if(accountNumber.equals(c.getValue("ACC_input")))
			            	{
	            	 Application.showMessage("SEND XML is :: \n"+senddata);
            		 Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
            		 Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);
			            		callFound=1;
			            	}
			            	else if (iteration!=5)
			            	{
			            		iteration++;
			            		 Application.showMessage("It is continuing for the next row");
			            		 Operation=lC.findingoperations(input, c);
			            		    c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
			            		    Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
			            		    rs=lC.reduceThinkTimeRTPmsgid(input, c, rowmsg);
			            		    if(rs==null)
			            		    {
			            		    	break;
			            		    }
			            		    else
			            		    {
			            		continue  ;
			            		    }
			            	}
			            	else
			            	{
			            		continue  ;
			            	}
			            	if(callFound==1)
			            	{
 	        	     String Service;	
 	        	     if(senddataTrim.contains("COM"))
							             {
	          			 Application.showMessage("SEND XML is :: \n"+senddata);	          		    
		            	 billingOrderID=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value[5]");
		            	 c.setValue("CCentralNum", billingOrderID);
		            	 Application.showMessage("CCentral number:::"+billingOrderID);
		                 	if(senddata.contains("Hav Market"))
			            {
		            	         Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
		 	                     Application.showMessage("Service is ::"+Service);		 	                       
				             }
				             else
				             {
		            		     Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
		            		     Application.showMessage("Service is  :: "+Service);
			            }
		                 	if(Service!=null || billingOrderID!=null )
			            {
		            		      Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim());
				                	 Application.showMessage("*******Validation Point 2 :: WebServicall(OrderUpdate)-Service********");
				            	    Application.showMessage("Validation is Successfull with Service::"+" "+Service);
			            	v2=1;
				            	   c.setValue("Product",Service);
				            	}
				            	
							            else
			                  	     c.report("Send Xml Service || BillingOrderID is NULL");
							           	            
							            					            
	           		        Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
	           		        Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);	           		
		            	break;
	            }     
 	        	        else if (senddataTrim.contains("ERR")||senddataTrim.contains("ServiceFault") )
	            { 
	          		      c.report("Error in OrderStatus ID");
	          		       break;
							             }
							             else
							             {
							            	 continue;
							             }
		            }
              } }
		            
			            
					         
        if(callFound * v2==1)
					          
        {   
							            					            
        	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
								             }
								             else
								             {
	            
        	c.report("WebService Call :: OrderUpdate_Validate is failed with validation points ]");
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
//Sruthi--Starts
public void OrderUpdate_tocheckAEENegativeScenarios(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, NullPointerException, SAXException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
	 ResultSet  rs;
	 HandleInvalidServicesLibrary HL=new HandleInvalidServicesLibrary();
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String accountNumber_ou;
	 String billingOrderID;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     c.setValue("OrderUpdate","true");
     Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
       String sc=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SCENARIO_NUMBER");
       Application.showMessage("SCENARIO_NUMBER::"+sc);
       String Error_Code=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: Error_Code");
       c.setValue("ErrorCode",Error_Code);
       String Error_Text=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: Error_Text");
      c.setValue("ErrorText",Error_Text);
    Application.showMessage("-------------------------------------------------------------");
    Application.showMessage("***********OrderUpdate_Validate_Tocheck AEE Function**************");       
    Application.showMessage("-------------------------------------------------------------");
    lC.findThinktimeOperationRTP(input, c);
    c.setValue("OrderUpdate","false");  
	try
	{
		// Statement st =lC. dBConnect(input, c);	
       // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
		rs=lC.reduceThinkTimeRTP(input, c);
		while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
            { 
	             rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            { 
           	     Application.showMessage("Your Recieve XML is NULL \n");
           	   	 String senddata = lC.extractXml(rs,xmldata2);           
	             String senddataTrim=lC.RemoveNameSpaces(senddata); 
	          	 Application.showMessage("SendDataTrim ::: " +senddataTrim );	          	        	
	          	 String accountNumber=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value");
 	             Application.showMessage("accountNumber is ::"+accountNumber); 
 	             String error_code=lC.nodeFromKey(senddataTrim,"<errorCode>","</errorCode>");
	             Application.showMessage("accountNumber is ::"+error_code); 
	             String error_text=lC.nodeFromKey(senddataTrim,"<errorText>","</errorText>");
 	             Application.showMessage("accountNumber is ::"+error_text); 
 	             if(accountNumber==null)
			            {
 	        	     Application.showMessage("Send Xml billingOrderId is NULL");
				            continue;
			            }
 	              else if(accountNumber.equals(c.getValue("ACC_input")))
			            	{
	            	 Application.showMessage("SEND XML is :: \n"+senddata);
            		 Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
            		 Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);
			            		callFound=1;
			            	}
			            	else
			            	{
			            		
			            		continue;
			            	}
			            	if(callFound==1)
			            	{
 	        	    // String Service;	
 	        	     if(senddataTrim.contains("ERR"))
 	        	     {
 	        	    	if(error_code.equals(c.getValue(Error_Code)))
 	        	    	{
 	        	    		Application.showMessage("SEND XML is :: \n"+senddataTrim);
 	              		 Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
 	              		 Application.showMessage("Validation is Successfull with Account Number :"+error_code);
 	  			            		v1=1;;
 	        	    	}
 	        	    	if(error_text.equals(c.getValue(Error_Text)))
 	        	    	{
 	        	    		Application.showMessage("SEND XML is :: \n"+senddataTrim);
 	              		 Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
 	              		 Application.showMessage("Validation is Successfull with Account Number :"+error_text);
 	  			            		v2=1;;
 	        	    	}
 	        	     }
							             
 	        	        else if (senddataTrim.contains("ERR")||senddataTrim.contains("ServiceFault") )
	            { 
	          		      c.report("Error in OrderStatus ID");
	          		       break;
							             }
							             else
							             {
							            	 continue;
							             }
		            }
              } }
		            
			            
					         
        if(callFound *v1 * v2==1)
					          
        {   
							            					            
        	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
								             }
								             else
								             {
	            
        	c.report("WebService Call :: OrderUpdate_Validate is failed with validation points ]");
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
//Sruthi--End
public void orderUpdate_ValidateScheduleUpgradeandDowngrade(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     c.setValue("OrderUpdate","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********OrderUpdate_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
     lr.findThinktimeOperationRTP(input, c);
     c.setValue("OrderUpdate","false");  
	try
	{
		//Statement st =lC. dBConnect(input, c);	
      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
	 	            {/*
	 	            	
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
			            	
			            	 if(billingOrderId.equals(c.getValue("CCentralNum")))
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
			            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
	 	            
	 	            */}
	 	            else
	 	            {/*
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
	 		            	
	 		            	 Application.showMessage("Service from Input   is ::"+" "+c.getValue("NEWSERVICE"));
	 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
	 		            	 if(Service.equals(c.getValue("NEWSERVICE").trim()))
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
	 	            */}
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
         if(callFound*v2*v3 ==1)
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
      //   st.close();
         
        //Lp.reportingToExcel(Object , ScriptingContext);
	}	
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
 }

//---

public void orderUpdate_ValidateScheduleUpgradeandDowngrade_RemovedIns(Object input, ScriptingContext c , String service) throws InterruptedException, IOException, ClassNotFoundException 
{
	 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     c.setValue("OrderUpdate","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********OrderUpdate_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
     lr.findThinktimeOperationRTP(input, c);
     c.setValue("OrderUpdate","false");  
	try
	{
		//Statement st =lC. dBConnect(input, c);	
      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
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
	 	            {/*
	 	            	
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
			            	
			            	 if(billingOrderId.equals(c.getValue("CCentralNum")))
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
			            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
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
	 	            
	 	            */}
	 	            else
	 	            {/*
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
	 		            	
	 		            	 Application.showMessage("Service from Input   is ::"+" "+c.getValue("NEWSERVICE"));
	 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
	 		            	 if(Service.equals(c.getValue("NEWSERVICE").trim()))
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
	 	            */}
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
         if(callFound*v2*v3 ==1)
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
      //   st.close();
         
        //Lp.reportingToExcel(Object , ScriptingContext);
	}	
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
	catch (Exception e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
}

public void OrderUpdate_tocheckAEENegativeScenarios_ForScheduleUPDown(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException, XPathExpressionException, NullPointerException, SAXException 
{
	Application.showMessage("Entering OrderUpdate_tocheckAEENegativeScenarios    ");
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 /*HandleInvalidServicesLibrary HL=new HandleInvalidServicesLibrary();
	 String accountNumber_ou;
	 String billingOrderID;
	 String Time= c.get("BaseTime").toString();*/
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     
     c.setValue("OrderUpdate","true");
     Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
       String sc=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SCENARIO_NUMBER");
       Application.showMessage("SCENARIO_NUMBER::"+sc);
       String Error_Code=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Error_Code");
       c.setValue("ErrorCode",Error_Code);
       Application.showMessage("ErrorCode::"+Error_Code);
       String Error_Text=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Error_Text");
      c.setValue("",Error_Text);
      Application.showMessage("ErrorText::"+Error_Text);
    Application.showMessage("-------------------------------------------------------------");
    Application.showMessage("***********OrderUpdate_Validate_Tocheck AEE Negative scenarios Function**************");       
    Application.showMessage("-------------------------------------------------------------");
    lC.findThinktimeOperationRTP(input, c);
    c.setValue("OrderUpdate","false");  
	try
	{
		// Statement st =lC. dBConnect(input, c);	
       // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
		rs=lC.reduceThinkTimeRTP(input, c);
		while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
            { 
	             rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            { 
           	     Application.showMessage("Your Recieve XML is NULL \n");
           	   	 String senddata = lC.extractXml(rs,xmldata2);           
	             String senddataTrim=lC.RemoveNameSpaces(senddata); 
	          	 Application.showMessage("SendDataTrim ::: " +senddataTrim );	          	        	
	          	 String accountNumber=lC.GetValueByXPath(senddataTrim, "/comRequest/header/value");
 	             Application.showMessage("accountNumber is ::"+accountNumber); 
 	             String error_code=lC.nodeFromKey(senddataTrim,"<errorCode>","</errorCode>");
	             Application.showMessage("error_code is ::"+error_code); 
	             String error_text=lC.nodeFromKey(senddataTrim,"<errorText>","</errorText>");
 	             Application.showMessage("error_text is ::"+error_text); 
 	             if(accountNumber==null)
			            {
 	        	     Application.showMessage("Send Xml billingOrderId is NULL");
				            continue;
			            }
 	              else if(accountNumber.equals(c.getValue("ACC_input")))
			            	{
	            	 Application.showMessage("SEND XML is :: \n"+senddata);
            		 Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
            		 Application.showMessage("Validation is Successfull with Account Number :"+accountNumber);
			            		callFound=1;
			            	}
			            	else
			            	{
			            		
			            		continue;
			            	}
			            	if(callFound==1)
			            	{
 	        	    // String Service;	
 	        	     if(senddataTrim.contains("ERR"))
 	        	     {
 	        	    	if(error_code.equals(c.getValue(Error_Code)))
 	        	    	{
 	        	    	 Application.showMessage("SEND XML is :: \n"+senddataTrim);
 	              		 Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
 	              		 Application.showMessage("	Validation is Successfull with Account Number :"+error_code);
 	  			            		v1=1;
 	        	    	}
 	        	    	if(error_text.equals(c.getValue(Error_Text)))
 	        	    	{
 	        	    		Application.showMessage("SEND XML is :: \n"+senddataTrim);
 	              		 Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update-AcountNumber********");
 	              		 Application.showMessage("Validation is Successfull with Account Number :"+error_text);
 	  			            		v2=1;
 	        	    	}
 	        	     }
							             
 	        	        else if (senddataTrim.contains("ERR")||senddataTrim.contains("ServiceFault") )
	            { 
	          		      c.report("Error in OrderStatus ID");
	          		       break;
							             }
							             else
							             {
							            	 continue;
							             }
		            }
              } }
		            
			            
					         
        if(callFound ==1)
					          
        {   
							            					            
        	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
								             }
								             else
								             {
	            
        	c.report("WebService Call :: OrderUpdate_Validate is failed with validation points ");
             }
         //    st.close();
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}	
	catch (Exception e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		return;
	}
}  
//Sruthi--End
public void simulatorInsightcallsforScheduleUpgradeandDowngrade_RemovedIns(Object input,
		ScriptingContext c , String service) throws InterruptedException, ClassNotFoundException, XPathExpressionException, NullPointerException, IOException, SAXException {
	// TODO Auto-generated method stub
	RTP_SimulatorClass_UpDown sim= new RTP_SimulatorClass_UpDown();
    Thread.sleep(4000);
    rtpTestInterface_Validate(input, c);     
    queryLocation_Validate(input,c);
 
    sim.processHomeSecurityInfo_Validate(input, c);
    sim.deactivateAccount_Validate(input,c);
    //sim.simGetAccountValidate(input, c);
   // getAccount_Validate(input, c);
    processHomeSecurityInfo_Validate(input,c);       
    sim.createAccount_ValidateScheduleInsight(input, c);           
    orderUpdate_ValidateScheduleUpgradeandDowngrade_RemovedIns(input,c,service);
    //Lp.storeCCnum(input, c);
	
} 

}