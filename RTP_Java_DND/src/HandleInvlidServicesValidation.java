
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

public class HandleInvlidServicesValidation
{
	//private static final ScriptingContext ScriptingContext = null;
	//private static final Object Object = null;
	public String XML_AccNumber;
	public String XML_ScenarioName;
	public static String SERVICEorCURRENTSERVICE;
	public String NEWSERVICE;
	public String HOUSE_KEY;
	
	LibraryRtp Lp=new LibraryRtp();
	HandleInvalidServicesLibrary lb=new HandleInvalidServicesLibrary();
	
	//RTPNormalCalls nc=new RTPNormalCalls();
	
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
    
    public void demicallsservices(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException
    {
    
    	 

    	 RTP_ValidationsClass vC=new RTP_ValidationsClass();
    	 
         
         String val="true";
         int noofCallsValidated=11;
         int i;
         OUTER1:
         for( i=0;i<=noofCallsValidated;i++)
         {
      	   Application.showMessage("The val of I is"+i);
      	   switch(i)
      	   {
      	   case 0:
      		  val= rtpTestInterface_Validate_Validate(input, c);         		  
      		   break;
      	   case 1:
      		 //  val=  getCustomerPermitRequirements_Validate(input,c);
      		   break;
      	   case 2:
      		   val=  createCMSAccountID_Validate(input, c);
      		   break;
      	   case 3:
      		   val= queryLocation_Validate(input,c);
      		   break;
      	   case 4:
      		 //  val=  getAccount_Validate(input, c);
      		   break;
      	   case 5:
      		   val=  processHomeSecurityInfo_Validate(input,c);
      		   break;
      	   case 6:
      		   val=   SetAccountBasicInformation_Validate(input,c);
      		   break;
      	   case 7:
      		  // val=   lb.createAccount_Validate(input, c);
      		   break;
      	   case 8:
      		   val=   responderInfo_Validate(input,c);
      		   break;
      	   case 9:
      		   val=  SetAccountAuthorityInformation_Validate(input, c);
      		   break;
      	   case 10:
      		   val=   modifyHomeSecurity_Validate(input, c);
      		   break;
      	   case 11:
      		   val=   orderUpdate_Validate(input,c);
      		   break;
      		   default:
      			   break;
      	   }
      	   if(val.equalsIgnoreCase("false"))
      	   {
      		   c.report("Validation calls could found on time as per dynamic think time so its quiting entire flow validation");
      		   break OUTER1;
      	   }
      	   else
      	   {
      		   continue OUTER1;
      	   }
         }
         
         

    
  
  
        
        
         
        
         
         
         
        
        
         
         
        
        
    }
    
    public void Disdemicallsservices(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException
    {
    
    	 Thread.sleep(40000);

    	 RTP_ValidationsClass vC=new RTP_ValidationsClass();
    	 
         rtpTestInterface_Validate_Validate(input, c);
        //.sleep(25000);
         getCustomerPermitRequirements_Validate(input,c);
         createCMSAccountID_Validate(input, c);
         queryLocation_Validate(input,c);
         Thread.sleep(30000);
         getAccount_Validate(input, c);
         processHomeSecurityInfo_Validate(input,c);
         SetAccountBasicInformation_Validate(input,c);
         lb.DiscreateAccount_Validate(input, c);
         responderInfo_Validate(input,c);
         SetAccountAuthorityInformation_Validate(input, c);
         modifyHomeSecurity_Validate(input, c);
         DisorderUpdate_Validate(input,c);
         //lR.SaveValuesfromInstall(input, c);
    }
    
    public void Insightcalls(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException
    { 
    
    	 Thread.sleep(40000);
    	RTP_ValidationsClass vC=new RTP_ValidationsClass();
	    
         rtpTestInterface_Validate(input, c);     
         queryLocation_Validate(input,c);
         
         getAccount_Validate(input, c);
         processHomeSecurityInfo_Validate(input,c);       
                       
         orderUpdate_Validate(input,c);
         
    }
    
    public void SimulatorDemicalls(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
    { 
    	Thread.sleep(40000);
    	 RTP_SimulatorClass sim= new RTP_SimulatorClass();
    	 CancelClass canClass= new CancelClass();
         rtpTestInterface_Validate(input, c);
         Thread.sleep(30000);
         getCustomerPermitRequirements_Validate(input,c);
         createCMSAccountID_Validate(input, c);
         queryLocation_Validate(input,c);
         Thread.sleep(40000);
         canClass.processHomeSecurityInfo_Validate(input, c);
         sim.deactivateAccount_Validate(input,c);
         sim.simGetAccountValidate(input, c);
         Thread.sleep(30000);
         getAccount_Validate(input, c);
         processHomeSecurityInfo_Validate(input,c);
         SetAccountBasicInformation_Validate(input,c);
         createAccount_Validate(input, c);
         responderInfo_Validate(input,c);
         SetAccountAuthorityInformation_Validate(input, c);
         modifyHomeSecurity_Validate(input, c);
         orderUpdate_Validate(input,c);
    }
    public void simulatorInsightcalls(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException
    {
    	
    	 RTP_SimulatorClass sim= new RTP_SimulatorClass();
    	 CancelClass canClass= new CancelClass();
         rtpTestInterface_Validate(input, c);     
         queryLocation_Validate(input,c);
         Thread.sleep(30000);
         canClass.processHomeSecurityInfo_Validate(input, c);
         sim.deactivateAccount_Validate(input,c);
         sim.simGetAccountValidate(input, c);
         //getAccount_Validate(input, c);
         processHomeSecurityInfo_Validate(input,c);       
         createAccount_Validate(input, c);                
         orderUpdate_Validate(input,c);
    }
    
    public String NormalgetAccout_ValidateSus(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, SAXException
    {
                    
                     // Think time in JVM [waits for 10 secs here]
         
                    ResultSet  rs;
                    int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
                    String xmldata1 ="receive_data";
                    String rscallpresent="true";
         String xmldata2 ="SEND_DATA";
         String Time= c.get("BaseTime").toString();
        // c.setValue("getAccount","true");
         Map<String, String> ResultMap = new HashMap<String,String>();
         String ServicesValue=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE");
                    List<String> GroupTier=lb.extractServices(ServicesValue,input,c);
         Application.showMessage("-------------------------------------------------");
         Application.showMessage("*****GetAccount_normal_Validate Function******");       
         Application.showMessage("-------------------------------------------------");
         
         if(ServicesValue.isEmpty() || ServicesValue.equals(null))
         {
                     Application.showMessage("Current Service is not parameterized in Excel");
         }
         else if(ServicesValue.contains("+"))
         {
                    c.setValue("IsMultiExist","true");
                    lb.extractServices(ServicesValue,input,c);
         }
         else
         {
                     c.setValue("IsMultiExist","false");
                     c.setValue("ExistingGroup",ServicesValue);
         }
      //   Lp.findThinktimeOperationRTP(input, c);
      //   c.setValue("getAccount","false"); 
         HashMap Operation=Lp.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccountSim"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccountSim"));

         
                    try
                    {
                                //    Statement st =Lp. dBConnect(input, c);  
          //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
                    	rs=Lp.reduceThinkTimeRTP(input, c);
                    	if(rs == null)
                    	{
                    		rscallpresent = "false";
                    		
                    	
                    		
                    	}
                    	else
{
             while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
             {
                    
                String rowmsg = rs.getString(1);
                if(rs.getBlob("receive_data")==null)
                {
                
                    Application.showMessage("Your Recieve XML is NULL \n");
                    
                    String senddata =Lp.extractXml(rs,xmldata2);
                    Application.showMessage("Your Recieve XML is::\n"+senddata);
                }
                else if(rs.getBlob("SEND_DATA")==null)
                {
                    Application.showMessage("Your SEND XML is NULL \n");             
                    String recievedata=Lp.extractXml(rs,xmldata1);
                    Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
                }
                else
                { 
                              
                                String senddata = Lp.extractXml(rs,xmldata2);                                 
                                String recievedata = Lp.extractXml(rs,xmldata1);      
                                                               
                                String id= Lp.nodeFromKey(senddata,"<resourceId>","</resourceId>");
                                Application.showMessage("id is ::"+id);
                                Application.showMessage("id from Input is ::"+c.getValue("ACC_input"));
                                
                                if(id.equals(c.getValue("ACC_input")))
                    {
                                    Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                    Application.showMessage("SEND XML is :: \n"+senddata);
                                    //System.out.printf("SEND XML is %s \n",senddata);
                                    Application.showMessage("*******Validation Point 1 :: WebServicall-GetAccountChangeOfService_Validate********");
                                    Application.showMessage("Validation is Successfull with Input Account Number"+id);
                                    callFound=1;
                    }
                    else
                    {
                                    continue;
                    }
                    
                                if(callFound==1)
                                {
                          
//                                 String group1=lC.xpathValue(recievedata,"//ns3:GetAccountResponse/account/ns2:group");
//                                                        Application.showMessage("group1 is::"+group1);
                                    String errorCode= Lp.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
                                    Application.showMessage("errorCode is::"+errorCode); 
                    
                                    if(errorCode ==null)
                                    {
                                    Application.showMessage("errorCode is NULL");
                                                //continue;
                                    }
                                    else
                                    {
                                        Application.showMessage("errorCode from Recieve Xml  from GetAccount is ::"+" "+errorCode);
                                        if(errorCode.trim().equals("0"))
                                                     {
                                                     Application.showMessage("*******Validation Point 2 :: errorCode********");
                                                     Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
                                                     v1=1;
                                                     }
                                                    else
                                                   {
                                                     c.report("errorCode at Recieve Xml not Validated as "+errorCode);
                                                   }
                                    }
                                    
                                    if(c.getValue("IsDemi").equalsIgnoreCase("true"))
                                               {
                                                    String centralStationAccountNumber= Lp.nodeFromKey(recievedata,"<ns2:centralStationAccountNumber>","</ns2:centralStationAccountNumber>");
                                                    Application.showMessage("centralStationAccountNumber is::"+centralStationAccountNumber); 
                                    
                                                    if(centralStationAccountNumber ==null)
                                                    {
                                                    Application.showMessage("centralStationAccountNumber is NULL");
                                                                //continue;
                                                    }
                                                    else
                                                    {
                                                        Application.showMessage("centralStationAccountNumber from Recieve Xml  from GetAccount is ::"+" "+centralStationAccountNumber);
                                                        if(centralStationAccountNumber.trim().equals(c.getValue("CcentralNum")))
                                                                     {
                                                                     Application.showMessage("*******Validation Point 3 :: centralStationAccountNumber********");
                                                                     Application.showMessage("Validation is Successfull with centralStationAccountNumber::"+" "+centralStationAccountNumber);
                                                                     v2=1;
                                                                     }
                                                                    else
                                                                   {
                                                                     c.report("centralStationAccountNumber at Recieve Xml not Validated as "+centralStationAccountNumber);
                                                                   }
                                                    }
                                               }
                                    else
                                    {
                                                    v2=1;     
                                    }
                                    
                                     String recievedataT=lb.RemoveNameSpaces(recievedata);
                                    Application.showMessage("receice data"+recievedataT);
                                                String status_getAcc= Lp.nodeFromKey(recievedata,"<ns2:status>","</ns2:status>");
                                                Application.showMessage("GetAccount Status is::"+status_getAcc); 
                                                 //--------------------Group and services validation------------------------
                    /*if(c.getValue("IsMultiExist").equals("true"))
                      {
                                                               
                                     if(GroupTier.size()==3)
                                    {
                                                    
                                                    //String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
                //String Tier1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:group>");
                                                               // String Tier2= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>");
                                                                String group1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                                                String Tier1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[2]");
                String Tier2=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[3]");
                                                                 Application.showMessage("group1 is::"+group1);   
                 Boolean group=(group1.trim()).equals(c.getValue("ExistingGroup"));
                 Application.showMessage("group"+group);
                 Boolean FirstTier=(Tier1.trim()).equals(GroupTier.get(1).trim() ) || (Tier1.trim()).equals(GroupTier.get(2).trim());
                 Application.showMessage("group"+FirstTier);
                 Boolean SecondTier=(Tier2.trim()).equals(GroupTier.get(1).trim() ) || (Tier2.trim()).equals(GroupTier.get(2).trim());
                 Application.showMessage("group"+SecondTier);
                 if(group1 ==null || Tier1 ==null || Tier2==null)
                                                                    {
                                                                    Application.showMessage("Group1 and Tiers are NULL");
                                                                                //continue;
                                                                    }
                                                                    else
                                                                    {
                                                                          Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
                                                                         // String check=c.getValue("ExistingGroup");
                                                                          if((group==true) && (FirstTier==true) && (SecondTier==true))
                                                                                       {
                                                                                       Application.showMessage("*******Validation Point 4 :: Group********");
                                                                                       Application.showMessage("Validation is Successfull with Group::"+group1);
                                                                                       Application.showMessage("Validation is Successfull with Tier1::"+Tier1);
                                                                                       Application.showMessage("Validation is Successfull with Tier2::"+Tier2);
                                                                                       Application.showMessage("-----*****Validation is Successfull with Groups and Services*****--------");
                                                                                       v3=1;
                                                                                        }
                                                                                    else
                                                                                       {
                                                                                                    v3=0;
                                                                                     c.report("group1 at Recieve Xml not Validated as "+group1);
                                                                                       }
                                                                    }
                                                                     }           
                                                    
                            else 
                                                 {
                                    
                                String group1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                String Tier1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[2]");
         Application.showMessage("group1 is::"+group1);   
         Boolean group=(group1.trim()).equals(c.getValue("ExistingGroup"));
         Application.showMessage("group"+group);
         Boolean FirstTier=(Tier1.trim()).equals(GroupTier.get(1).trim());
         Application.showMessage("group"+FirstTier);
         
         if(group1 ==null || Tier1 ==null)
                                    {
                                    Application.showMessage("Group1 and Tiers are NULL");
                                                //continue;
                                    }
                                    else
                                    {
                                          Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
                                         // String check=c.getValue("ExistingGroup");
                                          if((group==true) && (FirstTier==true))
                                                       {
                                                       Application.showMessage("*******Validation Point 4 :: Group********");
                                                       Application.showMessage("Validation is Successfull with Group::"+group1);
                                                       Application.showMessage("Validation is Successfull with Tier1::"+Tier1);
                                                       
                                                       Application.showMessage("-----*****Validation is Successfull with Groups and Services*****--------");
                                                       v3=1;
                                                        }
                                                    else
                                                       {
                                                                    v3=0;
                                                     c.report("group1 at Recieve Xml not Validated as "+group1);
                                                       }
                                    }
                                                        
             }
}
                      
                                                                          
                                                     else
                                     {
                                                     // for only one group
                                                       // String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group></account>").trim();
                                                                    String group1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                                                    Application.showMessage("group1 is::"+group1); 
                                                    
                                                                    if(group1 ==null)
                                                                    {
                                                                    Application.showMessage("Group1 is NULL");
                                                                                //continue;
                                                                    }
                                                                    else
                                                                    {
                                                                                    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
                                                                        if(group1.trim().equals(c.getValue("ExistingGroup")))
                                                                                     {
                                                                                     Application.showMessage("*******Validation Point 4 :: GroupTier********");
                                                                                     Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
                                                                                     v3=1;
                                                                                     }
                                                                                    else
                                                                                   {
                                                                                                    v3=0;
                                                                                     c.report("group1 at Recieve Xml not Validated as "+group1);
                                                                                   }
                                                                    }
                                                                    
                                                     
                                     }*/
                                                String accountId_getAcc= Lp.nodeFromKey(recievedata,"<ns2:accountId>","</ns2:accountId>");
                                                Application.showMessage("accountId is::"+accountId_getAcc); 
                                                 if(accountId_getAcc==null)
                                                 {
                                                                c.report("Send Xml Account Number is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+accountId_getAcc);
                                                     if(accountId_getAcc.equals(c.getValue("ACC_input")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 2 :: WebServicall-responseStatus_CMSAccountID********");
                                                                     Application.showMessage("Validation is Successfull with AccountNumber::"+" "+accountId_getAcc);
                                                                     v4=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     c.report("Account Number at Send Xml not Validated as "+accountId_getAcc);
                                                                 }
                                                }                  
                                   
                                break;
                                }
                                
                                            
                 }
             }
             if(v1*v2*v4==1)
             {
                    Application.showMessage("WebService Call :: GetAccount is Success[All validation points are Success]");
             }
             else
             {
                     c.report("WebService Call :: GetAccount is Failed with Validation Errors");
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
                                                                                                      
      
    public String VirtualizegetAccout_ValidateSus(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, SAXException
    {
                    
                    Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
                    String rscallpresent="true";
                    ResultSet  rs;
                    int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
                    String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String Time= c.get("BaseTime").toString();
        // c.setValue("getAccount","true");
         Map<String, String> ResultMap = new HashMap<String,String>();
         String ServicesValue=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE");
                    List<String> GroupTier=lb.extractServices(ServicesValue,input,c);
         Application.showMessage("-------------------------------------------------");
         Application.showMessage("*****GetAccount_normal_Validate Function******");       
         Application.showMessage("-------------------------------------------------");
         
         if(ServicesValue.isEmpty() || ServicesValue.equals(null))
         {
                     Application.showMessage("Current Service is not parameterized in Excel");
         }
         else if(ServicesValue.contains("+"))
         {
                    c.setValue("IsMultiExist","true");
                    lb.extractServices(ServicesValue,input,c);
         }
         else
         {
                     c.setValue("IsMultiExist","false");
                     c.setValue("ExistingGroup",ServicesValue);
         }
       //  Lp.findThinktimeOperationRTP(input, c);
       //  c.setValue("getAccount","false"); 
         HashMap Operation=Lp.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("ClSgetAccountSim"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccountSim"));

         
                    try
                    {
                                //    Statement st =Lp. dBConnect(input, c);  
          //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
                    	rs=Lp.reduceThinkTimeRTP(input, c);
                    	if(rs == null)
                    	{
                    		rscallpresent = "false";
                    		
                    	
                    		
                    	}
                    	else
{

             while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
             {
                    
                String rowmsg = rs.getString(1);
                if(rs.getBlob("receive_data")==null)
                {
                
                    Application.showMessage("Your Recieve XML is NULL \n");
                    
                    String senddata =Lp.extractXml(rs,xmldata2);
                    Application.showMessage("Your Recieve XML is::\n"+senddata);
                }
                else if(rs.getBlob("SEND_DATA")==null)
                {
                    Application.showMessage("Your SEND XML is NULL \n");             
                    String recievedata=Lp.extractXml(rs,xmldata1);
                    Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
                }
                else
                { 
                              
                                String senddata = Lp.extractXml(rs,xmldata2);                                 
                                String recievedata = Lp.extractXml(rs,xmldata1);      
                                                               
                                String id= Lp.nodeFromKey(senddata,"<resourceId>","</resourceId>");
                                Application.showMessage("id is ::"+id);
                                Application.showMessage("id from Input is ::"+c.getValue("ACC_input"));
                                
                                if(id.equals(c.getValue("ACC_input")))
                    {
                                    Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                    Application.showMessage("SEND XML is :: \n"+senddata);
                                    //System.out.printf("SEND XML is %s \n",senddata);
                                    Application.showMessage("*******Validation Point 1 :: WebServicall-GetAccountChangeOfService_Validate********");
                                    Application.showMessage("Validation is Successfull with Input Account Number"+id);
                                    callFound=1;
                    }
                    else
                    {
                                    continue;
                    }
                    
                                if(callFound==1)
                                {
                          
//                                 String group1=lC.xpathValue(recievedata,"//ns3:GetAccountResponse/account/ns2:group");
//                                                        Application.showMessage("group1 is::"+group1);
                                	String errorCode= Lp.nodeFromKey(recievedata,"<errorCode xmlns=\"\">","</errorCode>");
                                    //String errorCode= Lp.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
                                    Application.showMessage("errorCode is::"+errorCode); 
                    
                                    if(errorCode ==null)
                                    {
                                    Application.showMessage("errorCode is NULL");
                                                //continue;
                                    }
                                    else
                                    {
                                        Application.showMessage("errorCode from Recieve Xml  from GetAccount is ::"+" "+errorCode);
                                        if(errorCode.trim().equals("0"))
                                                     {
                                                     Application.showMessage("*******Validation Point 2 :: errorCode********");
                                                     Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
                                                     v1=1;
                                                     }
                                                    else
                                                   {
                                                     c.report("errorCode at Recieve Xml not Validated as "+errorCode);
                                                   }
                                    }
                                    
                                    if(c.getValue("IsDemi").equalsIgnoreCase("true"))
                                               {
                                                    String centralStationAccountNumber= Lp.nodeFromKey(recievedata,"<ns2:centralStationAccountNumber>","</ns2:centralStationAccountNumber>");
                                                    Application.showMessage("centralStationAccountNumber is::"+centralStationAccountNumber); 
                                    
                                                    if(centralStationAccountNumber ==null)
                                                    {
                                                    Application.showMessage("centralStationAccountNumber is NULL");
                                                                //continue;
                                                    }
                                                    else
                                                    {
                                                        Application.showMessage("centralStationAccountNumber from Recieve Xml  from GetAccount is ::"+" "+centralStationAccountNumber);
                                                        if(centralStationAccountNumber.trim().equals(c.getValue("CcentralNum")))
                                                                     {
                                                                     Application.showMessage("*******Validation Point 3 :: centralStationAccountNumber********");
                                                                     Application.showMessage("Validation is Successfull with centralStationAccountNumber::"+" "+centralStationAccountNumber);
                                                                     v2=1;
                                                                     }
                                                                    else
                                                                   {
                                                                     c.report("centralStationAccountNumber at Recieve Xml not Validated as "+centralStationAccountNumber);
                                                                   }
                                                    }
                                               }
                                    else
                                    {
                                                    v2=1;     
                                    }
                                    
                                     String recievedataT=lb.RemoveNameSpaces(recievedata);
                                    Application.showMessage("receice data"+recievedataT);
                                                String status_getAcc= Lp.nodeFromKey(recievedata,"<ns2:status xmlns=\"\">","</ns2:status>");
                                                Application.showMessage("GetAccount Status is::"+status_getAcc); 
                                                 //--------------------Group and services validation------------------------
                    if(c.getValue("IsMultiExist").equals("true"))
                      {
                                                               
                                     if(GroupTier.size()==3)
                                    {
                                                    
                                                    //String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
                //String Tier1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:group>");
                                                               // String Tier2= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>");
                                                                String group1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                                                String Tier1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[2]");
                String Tier2=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[3]");
                                                                 Application.showMessage("group1 is::"+group1);   
                 Boolean group=(group1.trim()).equals(c.getValue("ExistingGroup"));
                 Application.showMessage("group"+group);
                 Boolean FirstTier=(Tier1.trim()).equals(GroupTier.get(1).trim() ) || (Tier1.trim()).equals(GroupTier.get(2).trim());
                 Application.showMessage("group"+FirstTier);
                 Boolean SecondTier=(Tier2.trim()).equals(GroupTier.get(1).trim() ) || (Tier2.trim()).equals(GroupTier.get(2).trim());
                 Application.showMessage("group"+SecondTier);
                 if(group1 ==null || Tier1 ==null || Tier2==null)
                                                                    {
                                                                    Application.showMessage("Group1 and Tiers are NULL");
                                                                                //continue;
                                                                    }
                                                                    else
                                                                    {
                                                                          Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
                                                                         // String check=c.getValue("ExistingGroup");
                                                                          if((group==true) && (FirstTier==true) && (SecondTier==true))
                                                                                       {
                                                                                       Application.showMessage("*******Validation Point 4 :: Group********");
                                                                                       Application.showMessage("Validation is Successfull with Group::"+group1);
                                                                                       Application.showMessage("Validation is Successfull with Tier1::"+Tier1);
                                                                                       Application.showMessage("Validation is Successfull with Tier2::"+Tier2);
                                                                                       Application.showMessage("-----*****Validation is Successfull with Groups and Services*****--------");
                                                                                       v3=1;
                                                                                        }
                                                                                    else
                                                                                       {
                                                                                                    v3=0;
                                                                                     c.report("group1 at Recieve Xml not Validated as "+group1);
                                                                                       }
                                                                    }
                                                                     }           
                                                    
                            else 
                                                 {
                                    
                                String group1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                String Tier1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[2]");
         Application.showMessage("group1 is::"+group1);   
         Boolean group=(group1.trim()).equals(c.getValue("ExistingGroup"));
         Application.showMessage("group"+group);
         Boolean FirstTier=(Tier1.trim()).equals(GroupTier.get(1).trim());
         Application.showMessage("group"+FirstTier);
         
         if(group1 ==null || Tier1 ==null)
                                    {
                                    Application.showMessage("Group1 and Tiers are NULL");
                                                //continue;
                                    }
                                    else
                                    {
                                          Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
                                         // String check=c.getValue("ExistingGroup");
                                          if((group==true) && (FirstTier==true))
                                                       {
                                                       Application.showMessage("*******Validation Point 4 :: Group********");
                                                       Application.showMessage("Validation is Successfull with Group::"+group1);
                                                       Application.showMessage("Validation is Successfull with Tier1::"+Tier1);
                                                       
                                                       Application.showMessage("-----*****Validation is Successfull with Groups and Services*****--------");
                                                       v3=1;
                                                        }
                                                    else
                                                       {
                                                                    v3=0;
                                                     c.report("group1 at Recieve Xml not Validated as "+group1);
                                                       }
                                    }
                                                        
             }
}
                      
                                                                          
                                                     else
                                     {
                                                     // for only one group
                                                       // String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group></account>").trim();
                                                                    String group1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                                                    Application.showMessage("group1 is::"+group1); 
                                                    
                                                                    if(group1 ==null)
                                                                    {
                                                                    Application.showMessage("Group1 is NULL");
                                                                                //continue;
                                                                    }
                                                                    else
                                                                    {
                                                                                    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
                                                                        if(group1.trim().equals(c.getValue("ExistingGroup")))
                                                                                     {
                                                                                     Application.showMessage("*******Validation Point 4 :: GroupTier********");
                                                                                     Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
                                                                                     v3=1;
                                                                                     }
                                                                                    else
                                                                                   {
                                                                                                    v3=0;
                                                                                     c.report("group1 at Recieve Xml not Validated as "+group1);
                                                                                   }
                                                                    }
                                                                    
                                                     
                                     }
                                                String accountId_getAcc= Lp.nodeFromKey(recievedata,"<ns2:accountId xmlns=\"\">","</ns2:accountId>");
                                                Application.showMessage("accountId is::"+accountId_getAcc); 
                                                 if(accountId_getAcc==null)
                                                 {
                                                                c.report("Send Xml Account Number is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+accountId_getAcc);
                                                     if(accountId_getAcc.equals(c.getValue("ACC_input")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 2 :: WebServicall-responseStatus_CMSAccountID********");
                                                                     Application.showMessage("Validation is Successfull with AccountNumber::"+" "+accountId_getAcc);
                                                                     v4=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     c.report("Account Number at Send Xml not Validated as "+accountId_getAcc);
                                                                 }
                                                }                  
                                   
                                break;
                                }
                                
                                            
                 }
             }
             if(v1*v2*v3*v4==1)
             {
                    Application.showMessage("WebService Call :: GetAccount is Success[All validation points are Success]");
             }
             else
             {
                     c.report("WebService Call :: GetAccount is Failed with Validation Errors");
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
                                                                                                      
             
    
    public void VirtualizegetAccout_ValidateDisconnect(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, XPathExpressionException, SAXException
    {
                    
                    Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
         
                    ResultSet  rs;
                    int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
                    String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String Time= c.get("BaseTime").toString();
         c.setValue("getAccount","true");
         Map<String, String> ResultMap = new HashMap<String,String>();
         String ServicesValue=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
                    List<String> GroupTier=lb.extractServices(ServicesValue,input,c);
         Application.showMessage("-------------------------------------------------");
         Application.showMessage("*****GetAccount_normal_Validate Function******");       
         Application.showMessage("-------------------------------------------------");
         
         if(ServicesValue.isEmpty() || ServicesValue.equals(null))
         {
                     Application.showMessage("Current Service is not parameterized in Excel");
         }
         else if(ServicesValue.contains("+"))
         {
                    c.setValue("IsMultiExist","true");
                    lb.extractServices(ServicesValue,input,c);
         }
         else
         {
                     c.setValue("IsMultiExist","false");
                     c.setValue("ExistingGroup",ServicesValue);
         }
         Lp.findThinktimeOperationRTP(input, c);
         c.setValue("getAccount","false"); 
         
                    try
                    {
                              //      Statement st =Lp. dBConnect(input, c);  
          //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 100");
                    	rs=Lp.reduceThinkTimeRTP(input, c);
             while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
             {
                    
                String rowmsg = rs.getString(1);
                if(rs.getBlob("receive_data")==null)
                {
                
                    Application.showMessage("Your Recieve XML is NULL \n");
                    
                    String senddata =Lp.extractXml(rs,xmldata2);
                    Application.showMessage("Your Recieve XML is::\n"+senddata);
                }
                else if(rs.getBlob("SEND_DATA")==null)
                {
                    Application.showMessage("Your SEND XML is NULL \n");             
                    String recievedata=Lp.extractXml(rs,xmldata1);
                    Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
                }
                else
                { 
                              
                                String senddata = Lp.extractXml(rs,xmldata2);                                 
                                String recievedata = Lp.extractXml(rs,xmldata1);      
                                                               
                                String id= Lp.nodeFromKey(senddata,"<id>","</id>");
                                Application.showMessage("id is ::"+id);
                                Application.showMessage("id from Input is ::"+c.getValue("ACC_input"));
                                
                                if(id.equals(c.getValue("ACC_input")))
                    {
                                    Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                    Application.showMessage("SEND XML is :: \n"+senddata);
                                    //System.out.printf("SEND XML is %s \n",senddata);
                                    Application.showMessage("*******Validation Point 1 :: WebServicall-GetAccountChangeOfService_Validate********");
                                    Application.showMessage("Validation is Successfull with Input Account Number"+id);
                                    callFound=1;
                    }
                    else
                    {
                                    continue;
                    }
                    
                                if(callFound==1)
                                {
                          
//                                 String group1=lC.xpathValue(recievedata,"//ns3:GetAccountResponse/account/ns2:group");
//                                                        Application.showMessage("group1 is::"+group1);
                                	String errorCode= Lp.nodeFromKey(recievedata,"<errorCode xmlns=\"\">","</errorCode>");
                                    //String errorCode= Lp.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
                                    Application.showMessage("errorCode is::"+errorCode); 
                    
                                    if(errorCode ==null)
                                    {
                                    Application.showMessage("errorCode is NULL");
                                                //continue;
                                    }
                                    else
                                    {
                                        Application.showMessage("errorCode from Recieve Xml  from GetAccount is ::"+" "+errorCode);
                                        if(errorCode.trim().equals("0"))
                                                     {
                                                     Application.showMessage("*******Validation Point 2 :: errorCode********");
                                                     Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
                                                     v1=1;
                                                     }
                                                    else
                                                   {
                                                     c.report("errorCode at Recieve Xml not Validated as "+errorCode);
                                                   }
                                    }
                                    
                                    if(c.getValue("IsDemi").equalsIgnoreCase("true"))
                                               {
                                                    String centralStationAccountNumber= Lp.nodeFromKey(recievedata,"<ns2:centralStationAccountNumber>","</ns2:centralStationAccountNumber>");
                                                    Application.showMessage("centralStationAccountNumber is::"+centralStationAccountNumber); 
                                    
                                                    if(centralStationAccountNumber ==null)
                                                    {
                                                    Application.showMessage("centralStationAccountNumber is NULL");
                                                                //continue;
                                                    }
                                                    else
                                                    {
                                                        Application.showMessage("centralStationAccountNumber from Recieve Xml  from GetAccount is ::"+" "+centralStationAccountNumber);
                                                        if(centralStationAccountNumber.trim().equals(c.getValue("CcentralNum")))
                                                                     {
                                                                     Application.showMessage("*******Validation Point 3 :: centralStationAccountNumber********");
                                                                     Application.showMessage("Validation is Successfull with centralStationAccountNumber::"+" "+centralStationAccountNumber);
                                                                     v2=1;
                                                                     }
                                                                    else
                                                                   {
                                                                     c.report("centralStationAccountNumber at Recieve Xml not Validated as "+centralStationAccountNumber);
                                                                   }
                                                    }
                                               }
                                    else
                                    {
                                                    v2=1;     
                                    }
                                    
                                     String recievedataT=lb.RemoveNameSpaces(recievedata);
                                    Application.showMessage("receice data"+recievedataT);
                                                String status_getAcc= Lp.nodeFromKey(recievedata,"<ns2:status xmlns=\"\">","</ns2:status>");
                                                Application.showMessage("GetAccount Status is::"+status_getAcc); 
                                                 //--------------------Group and services validation------------------------
                    if(c.getValue("IsMultiExist").equals("true"))
                      {
                                                               
                                     if(GroupTier.size()==3)
                                    {
                                                    
                                                    //String group1= lC.nodeFromKey(recievedata,"</ns2:product><ns2:group>","</ns2:group><ns2:group>");
                //String Tier1= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:group>");
                                                               // String Tier2= lC.nodeFromKey(recievedata,"</ns2:group><ns2:group>","</ns2:group><ns2:internal>");
                                                                String group1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                                                String Tier1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[2]");
                String Tier2=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[3]");
                                                                 Application.showMessage("group1 is::"+group1);   
                 Boolean group=(group1.trim()).equals(c.getValue("ExistingGroup"));
                 Application.showMessage("group"+group);
                 Boolean FirstTier=(Tier1.trim()).equals(GroupTier.get(1).trim() ) || (Tier1.trim()).equals(GroupTier.get(2).trim());
                 Application.showMessage("group"+FirstTier);
                 Boolean SecondTier=(Tier2.trim()).equals(GroupTier.get(1).trim() ) || (Tier2.trim()).equals(GroupTier.get(2).trim());
                 Application.showMessage("group"+SecondTier);
                 if(group1 ==null || Tier1 ==null || Tier2==null)
                                                                    {
                                                                    Application.showMessage("Group1 and Tiers are NULL");
                                                                                //continue;
                                                                    }
                                                                    else
                                                                    {
                                                                          Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
                                                                         // String check=c.getValue("ExistingGroup");
                                                                          if((group==true) && (FirstTier==true) && (SecondTier==true))
                                                                                       {
                                                                                       Application.showMessage("*******Validation Point 4 :: Group********");
                                                                                       Application.showMessage("Validation is Successfull with Group::"+group1);
                                                                                       Application.showMessage("Validation is Successfull with Tier1::"+Tier1);
                                                                                       Application.showMessage("Validation is Successfull with Tier2::"+Tier2);
                                                                                       Application.showMessage("-----*****Validation is Successfull with Groups and Services*****--------");
                                                                                       v3=1;
                                                                                        }
                                                                                    else
                                                                                       {
                                                                                                    v3=0;
                                                                                     c.report("group1 at Recieve Xml not Validated as "+group1);
                                                                                       }
                                                                    }
                                                                     }           
                                                    
                            else 
                                                 {
                                    
                                String group1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                String Tier1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group[2]");
         Application.showMessage("group1 is::"+group1);   
         Boolean group=(group1.trim()).equals(c.getValue("ExistingGroup"));
         Application.showMessage("group"+group);
         Boolean FirstTier=(Tier1.trim()).equals(GroupTier.get(1).trim());
         Application.showMessage("group"+FirstTier);
         
         if(group1 ==null || Tier1 ==null)
                                    {
                                    Application.showMessage("Group1 and Tiers are NULL");
                                                //continue;
                                    }
                                    else
                                    {
                                          Application.showMessage("Group--Recieve Xml  from GetAccount is ::"+c.getValue("ExistingGroup"));
                                         // String check=c.getValue("ExistingGroup");
                                          if((group==true) && (FirstTier==true))
                                                       {
                                                       Application.showMessage("*******Validation Point 4 :: Group********");
                                                       Application.showMessage("Validation is Successfull with Group::"+group1);
                                                       Application.showMessage("Validation is Successfull with Tier1::"+Tier1);
                                                       
                                                       Application.showMessage("-----*****Validation is Successfull with Groups and Services*****--------");
                                                       v3=1;
                                                        }
                                                    else
                                                       {
                                                                    v3=0;
                                                     c.report("group1 at Recieve Xml not Validated as "+group1);
                                                       }
                                    }
                                                        
             }
}
                      
                                                                          
                                                     else
                                     {
                                                     // for only one group
                                                       // String group1= lC.nodeFromKey(recievedata,"</suspended><group>","</group></account>").trim();
                                                                    String group1=lb.GetValueByXPath(recievedataT, "/GetAccountResponse/account/group");
                                                                    Application.showMessage("group1 is::"+group1); 
                                                    
                                                                    if(group1 ==null)
                                                                    {
                                                                    Application.showMessage("Group1 is NULL");
                                                                                //continue;
                                                                    }
                                                                    else
                                                                    {
                                                                                    Application.showMessage("GroupTier from Recieve Xml  from GetAccount is ::"+" "+group1);
                                                                        if(group1.trim().equals(c.getValue("ExistingGroup")))
                                                                                     {
                                                                                     Application.showMessage("*******Validation Point 4 :: GroupTier********");
                                                                                     Application.showMessage("Validation is Successfull with GroupTier::"+" "+group1);
                                                                                     v3=1;
                                                                                     }
                                                                                    else
                                                                                   {
                                                                                                    v3=0;
                                                                                     c.report("group1 at Recieve Xml not Validated as "+group1);
                                                                                   }
                                                                    }
                                                                    
                                                     
                                     }
                                                String accountId_getAcc= Lp.nodeFromKey(recievedata,"<ns2:accountId xmlns=\"\">","</ns2:accountId>");
                                                Application.showMessage("accountId is::"+accountId_getAcc); 
                                                 if(accountId_getAcc==null)
                                                 {
                                                                c.report("Send Xml Account Number is NULL");
                                                                continue;
                                                }
                                                else
                                                {
                                                     Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+accountId_getAcc);
                                                     if(accountId_getAcc.equals(c.getValue("ACC_input")))
                                                                 {
                                                                     Application.showMessage("*******Validation Point 2 :: WebServicall-responseStatus_CMSAccountID********");
                                                                     Application.showMessage("Validation is Successfull with AccountNumber::"+" "+accountId_getAcc);
                                                                     v4=1;
                                                                 }
                                                                 else
                                                                 {
                                                                     c.report("Account Number at Send Xml not Validated as "+accountId_getAcc);
                                                                 }
                                                }                  
                                   
                                break;
                                }
                                
                                            
                 }
             }
             if(v1*v2*v3*v4==1)
             {
                    Application.showMessage("WebService Call :: GetAccount is Success[All validation points are Success]");
             }
             else
             {
                     c.report("WebService Call :: GetAccount is Failed with Validation Errors");
             }
        //     st.close();
                    }              
                    catch (SQLException e)
                    {
                        System.out.println("Connection Failed! Check output console");
                                    e.printStackTrace();
                                    return;
                    }
    }           
                                                                                                      
             
                
    public void cossimulatorto1(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, SQLException, InterruptedException
    {
    	if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolSimulator").equals("1"))
    	{
    		lb.InvalidServicessimulatorChange1(input, c);
    	}
    	else
    	{
    		Application.showMessage("No need of simulatior...");
    	}
    }
    public void cossimulatorto0(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, SQLException, InterruptedException
    {
    	if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolSimulator").equals("1"))
    	{
    		lb.InvalidServicessimulatorChange0(input, c);
    	}
    	else
    	{
    		Application.showMessage("No need of simulatior...");
    	}
    }
    public void CosValidationsservices(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, SAXException
    {
    	
    	
    	
    	ChangeOfServiceClass CS = new ChangeOfServiceClass();
    	HandleInvalidServices hI=new HandleInvalidServices();
    	if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolSimulator").equals("0"))
    	{
    	Thread.sleep(40000);
    	NormalgetAccout_ValidateSus(input,c);
    	//CS.getAccoutCos_Validate(input, c);
    	queryLocation_Validate(input,c); 
    	Application.showMessage("***Change Of service Validation calls*****");
    	Application.showMessage("***Current service is*****"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"));
    	Application.showMessage("***New Service is*****"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"));
    	List<String> addAccountgroup=lb.addAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"), input, c);
    	List<String> removeAccountgroup=lb.removeAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), input, c);
    	Application.showMessage(""+c.getValue("IsUpdateTier"));
    	Application.showMessage(""+c.getValue("IsAddaccountgroup"));
    	Application.showMessage("Value here in function is : : "+c.getValue("IsRemoveaccountgroup"));
    	if(c.getValue("IsUpdateTier").equals("true"))
    			{
    		Application.showMessage("***Update Tier Group Validation calls*****");
    		updateTier_Validate(c.getValue("updatedvalue"),input,c);
    			}
    	else
    	{
    		Application.showMessage("...");
    	}
    	if(c.getValue("IsAddaccountgroup").equals("true"))
		{
    		Application.showMessage("***addAccountgroup Validation calls*****");
    		for(int i=0;i<addAccountgroup.size();i++)
    		{
    			String addServices=addAccountgroup.get(i);
    		AddGroup_Validate(addServices,input, c);
    		}
		}
    	else
    	{
    		Application.showMessage("...");
    	}
    	if(c.getValue("IsRemoveaccountgroup").equals("true"))
		{
    		Application.showMessage("***removeAccountgroup Validation calls*****");
    		for(int i=0;i<removeAccountgroup.size();i++)
    		{
    			String addServices=removeAccountgroup.get(i);
    		RemoveGroup_Validate(addServices,input,c);
		    }
		}
    	else
    	{
    		Application.showMessage("...");
    	}
    	//CS.cosLogicFlow(input, c);   	
    	//CS.cosLogicFlow(input, c);   	
    	//CS.orderUpdateCos_Validate(input, c);
    	lb.orderUpdate_ValidateCOS(input, c);
	}
    	
    	else
    	{
    		Thread.sleep(40000);
    		//lb.SimgetAccout_ValidateSus(input,c);
    		VirtualizegetAccout_ValidateSus(input,c);
        	queryLocation_Validate(input,c);
        	Application.showMessage("***Change Of service Validation calls*****");
        	Application.showMessage("***Current service is*****"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"));
        	Application.showMessage("***New Service is*****"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"));
        	/*List<String> addAccountgroup=lb.addAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolServiceSusRes"), input, c);
        	List<String> removeAccountgroup=lb.removeAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolServiceSusRes"), input, c);
        	if(c.getValue("IsUpdateTier").equals("true"))
        			{
        		Application.showMessage("***Update Tier Group Validation calls*****");
        		updateTier_Validate(c.getValue("updatedvalue"),input,c);
        			}
        	else
        	{
        		Application.showMessage("...");
        	}
        	if(c.getValue("IsAddaccountgroup").equals("true"))
			{
        		Application.showMessage("***addAccountgroup Validation calls*****");
        		for(int i=0;i<addAccountgroup.size();i++)
        		{
        			String addServices=addAccountgroup.get(i);
        		AddGroup_Validate(addServices,input, c);
        		}
			}
        	else
        	{
        		Application.showMessage("...");
        	}
        	if(c.getValue("IsRemoveaccountgroup").equals("true"))
			{
        		Application.showMessage("***removeAccountgroup Validation calls*****");
        		for(int i=0;i<removeAccountgroup.size();i++)
        		{
        			String addServices=removeAccountgroup.get(i);
        		RemoveGroup_Validate(addServices,input,c);
			    }
			}
        	else
        	{
        		Application.showMessage("...");
        	}*/
        	//CS.cosLogicFlow(input, c);   	
        	//CS.cosLogicFlow(input, c);   	
        	//CS.orderUpdateCos_Validate(input, c);
        	lb.orderUpdate_ValidateCOS(input, c);
    	}
    	 
    }
    
    public void CosValidationsservices100TDPEV(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, SAXException
    {
    	
    	
    	SuspendClass Su= new SuspendClass();
    	ChangeOfServiceClass CS = new ChangeOfServiceClass();
    	HandleInvalidServices hI=new HandleInvalidServices();
     	Thread.sleep(40000);
    	//NormalgetAccout_ValidateSus(input,c);
     	Su.getAccout_ValidateSus100TDPEV(input, c);
    	Application.showMessage("***Change Of service Validation calls*****");
    	Application.showMessage("***Current service is*****"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"));
    	Application.showMessage("***New Service is*****"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"));
    	List<String> addAccountgroup=lb.addAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"), input, c);
    	List<String> removeAccountgroup=lb.removeAccountServices100TDPEV(c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), input, c);
    	Application.showMessage(""+c.getValue("IsUpdateTier"));
    	Application.showMessage(""+c.getValue("IsAddaccountgroup"));
    	Application.showMessage(""+c.getValue("IsRemoveaccountgroup"));
    	if(c.getValue("IsUpdateTier").equals("true"))
    			{
    		Application.showMessage("***Update Tier Group Validation calls*****");
    		updateTier_Validate(c.getValue("updatedvalue"),input,c);
    		
    			}
    	else
    	{
    		Application.showMessage("...");
    	}
    	if(c.getValue("IsAddaccountgroup").equals("true"))
		{
    		Application.showMessage("***addAccountgroup Validation calls*****");
    		for(int i=0;i<addAccountgroup.size();i++)
    		{
    			String addServices=addAccountgroup.get(i);
    		    AddGroup_Validate(addServices,input, c);
    		}
		}
    	else
    	{
    		Application.showMessage("...");
    	}
    	if(c.getValue("IsRemoveaccountgroup").equals("true"))
		{
    		Application.showMessage("***removeAccountgroup Validation calls*****");
    		for(int i=0;i<removeAccountgroup.size();i++)
    		{
    			String addServices=removeAccountgroup.get(i);
    		    RemoveGroup_Validate(addServices,input,c);
		    }
    		
    	 Application.showMessage("End of RemoveAccount group");
		}
    	else
    	{
    		Application.showMessage("...");
    	}
    	
    	lb.orderUpdate_ValidateCOS100TDPEV(input, c);
	}
    	
    	
    	 
    
    public void updateTier_Validate(String getService,Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	    
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	    // c.setValue("UpdateTier","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****updateTier_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	    // Lp.findThinktimeOperationRTP(input, c);
	   //  c.setValue("UpdateTier","false"); 
	    
	     HashMap Operation=Lp.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_updateAccountTierGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_updateAccountTierGroup"));

	     
		try
		{
			// Statement st =Lp. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/updateAccountTierGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=Lp.reduceThinkTimeRTP(input, c);
	         while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
	         {
	        	
	            String rowmsg = rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =Lp.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=Lp.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
	            	String senddatacls = Lp.extractXml(rs,xmldata2);                                        
                    String recievedatacls = Lp.extractXml(rs,xmldata1);      
                        String senddata=Lp.clsRemoveAsciiCharacter(senddatacls);  
                        String recievedata=Lp.clsRemoveAsciiCharacter(recievedatacls);

	            	
	            	//  String senddata = Lp.extractXml(rs,xmldata2);		         
		          //  String recievedata = Lp.extractXml(rs,xmldata1);      
		         		           
		            String id= Lp.nodeFromKey(senddata,"<id>","</id>");
		            Application.showMessage("id is ::"+id);
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-updateTier_Validate********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
			            String errorCode= Lp.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
			            Application.showMessage("errorCode is ::"+errorCode);
			           
			            
			           
			            if(errorCode==null)
			            {
				            c.report("Send Xml errorCode is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from UpdateTierGroup is ::"+" "+errorCode);
			            	 if(errorCode.equals("0"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
				             }
			            }		

			            String tierGroup= Lp.nodeFromKey(senddata,"<tierGroup>","</tierGroup>");
			            Application.showMessage("tierGroup is::"+tierGroup);
			            Application.showMessage("getService is::"+getService); 
			            
			             if(tierGroup==null)
			             {
				            c.report("Send tierGroup is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("tierGroup from Send Xml  from UpdateTierGroup is ::"+" "+tierGroup);
			            	 if(tierGroup.equals(getService))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-UpdateTierGroup********");
				            	 Application.showMessage("Validation is Successfull with tierGroup::"+" "+tierGroup);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("tierGroup at Send Xml not Validated as "+tierGroup);
				             }
			            }		
		               
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1*v2==1)
	         {
	         	Application.showMessage("WebService Call :: UpdateTierGroup is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: updateTier_Validate is Failed with Validation Errors");
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
	
	
	public void AddGroup_Validate(String getService,Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	    
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	   //  c.setValue("addAccountGroup","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****AddGroup_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	   //  Lp.findThinktimeOperationRTP(input, c);
	  //   c.setValue("addAccountGroup","false"); 
	     HashMap Operation=Lp.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_addAccountGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_addAccountGroup"));

		try
		{
			// Statement st =Lp. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/addAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=Lp.reduceThinkTimeRTP(input, c);
	         while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
	         {
	        	
	            String rowmsg = rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =Lp.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=Lp.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
	            	String senddatacls = Lp.extractXml(rs,xmldata2);                                        
                    String recievedatacls = Lp.extractXml(rs,xmldata1);      
                        String     senddata=Lp.clsRemoveAsciiCharacter(senddatacls);  
                        String     recievedata=Lp.clsRemoveAsciiCharacter(recievedatacls);

	            	
	            	//  String senddata = Lp.extractXml(rs,xmldata2);		         
		          // String recievedata = Lp.extractXml(rs,xmldata1);      
		         		           
		            String id= Lp.nodeFromKey(senddata,"<id>","</id>");
		            Application.showMessage("id is ::"+id);
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-AddGroup_Validate********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		            	    String errorCode= Lp.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
				            Application.showMessage("errorCode is ::"+errorCode);
				           
				            
				           
				            if(errorCode==null)
				            {
					            c.report("Send Xml errorCode is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+errorCode);
				            	 if(errorCode.equals("0"))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
					            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
					            	 v1=1;
					             }
					             else
					             {
					            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
					             }
				            }		

				            String group= Lp.nodeFromKey(senddata,"<group>","</group>");
				            Application.showMessage("tierGroup is::"+group); 
				             if(group==null)
				             {
					            c.report("Send group is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("group from Send Xml  from UpdateTierGroup is ::"+" "+group);
				            	 if(group.equals(getService))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-AddGroup********");
					            	 Application.showMessage("Validation is Successfull with group::"+" "+group);
					            	 v2=1;
					             }
					             else
					             {
					            	 c.report("NewService at Send Xml not Validated as "+group);
					             }
				            }	
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1*v2==1)
	         {
	         	Application.showMessage("WebService Call :: AddGroup is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: AddGroup_Validate is Failed with Validation Errors");
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
	
	
	public void RemoveGroup_Validate(String getService,Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
	    
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	  //   c.setValue("removeAccountgroup","true");
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****RemoveGroup_Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	 //    Lp.findThinktimeOperationRTP(input, c);
	  //   c.setValue("removeAccountgroup","false"); 
	     
	     HashMap Operation=Lp.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_removeAccountGroup"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_removeAccountGroup"));

	     
		try
		{
			// Statement st =Lp. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/removeAccountGroup' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=Lp.reduceThinkTimeRTP(input, c);
	         while (rs.next()&& rs.getString(1) != c.getValue("BaseMsgid"))
	         {
	        	
	            String rowmsg = rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =Lp.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=Lp.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
	            	String senddatacls = Lp.extractXml(rs,xmldata2);                                        
                    String recievedatacls = Lp.extractXml(rs,xmldata1);      
                        String senddata=Lp.clsRemoveAsciiCharacter(senddatacls);  
                        String recievedata=Lp.clsRemoveAsciiCharacter(recievedatacls);

	            	
	            	// String senddata = Lp.extractXml(rs,xmldata2);		         
		          //  String recievedata = Lp.extractXml(rs,xmldata1);      
		         		           
		            String id= Lp.nodeFromKey(senddata,"<id>","</id>");
		            Application.showMessage("id is ::"+id);
		            if(id.equals(c.getValue("ACC_input")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-RemoveGroup_Validate********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+id);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		            	String errorCode= Lp.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
			            Application.showMessage("errorCode is ::"+errorCode);
			           
			            
			           
			            if(errorCode==null)
			            {
				            c.report("Send Xml errorCode is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from AddGroup is ::"+" "+errorCode);
			            	 if(errorCode.equals("0"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
				            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("errorCode at Send Xml not Validated as "+errorCode);
				             }
			            }		

			            String group= Lp.nodeFromKey(senddata,"<group>","</group>");
			            Application.showMessage("tierGroup is::"+group); 
			             if(group==null)
			             {
				            c.report("Send group is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("group from Send Xml  from removeAccountGroup is ::"+" "+group);
			            	 Application.showMessage("GetSevice Value"+getService);
			            	 if(group.equals(getService))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-AddGroup********");
				            	 Application.showMessage("Validation is Successfull with group::"+" "+group);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("NewService at Send Xml not Validated as "+group);
				             }
			            }	
		               
		            break;
		            }
		            
		                        
	             }
	         }
	         if(v1*v2==1)
	         {
	         	Application.showMessage("WebService Call :: RemoveGroup_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.report("WebService Call :: RemoveGroup_Validate is Failed with Validation Errors");
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
	
    public void SuspendValidationsservices(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, SAXException 
    {
    	
    	SuspendClass sC=new SuspendClass();
    	
    	String val="true";
        int noofCallsValidated=4;
        int i;
        OUTER1:
        for( i=0;i<=noofCallsValidated;i++)
        {
     	   Application.showMessage("The val of I is"+i);
     	   switch(i)
     	   {
     	   case 0:
     		  val= VirtualizegetAccout_ValidateSus(input,c);             		  
     		   break;
     	   case 1:
     		   val=queryLocation_Validate(input,c);
     		   break;
     	   case 2:
     		   val= sC.processHomeSecurityInfo_Validate(input, c);
     		   break;
     	   case 3:
     		   val= sC.suspend_COPS_validate(input, c);
     		   break;
     	   case 4:
     		   val=  lb.orderUpdate_Validatesus(input, c);
     		   break;
     	   
     		   default:
     			   break;
     	   }
     	   if(val.equalsIgnoreCase("false"))
     	   {
     		   c.report("Validation calls could found on time as per dynamic think time so its quiting entire flow validation");
     		   break OUTER1;
     	   }
     	   else
     	   {
     		   continue OUTER1;
     	   }
        }
        
        
  	
    	
    	
    }
    
    public void InsightSuspendValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException 
    {
    	    	
    	SuspendClass sC=new SuspendClass();
    	Thread.sleep(50000);
    	//sC.getAccout_ValidateSus(input, c);
    	lb.SimgetAccout_ValidateSus(input,c);
    	queryLocation_Validate(input,c);
    	sC.processHomeSecurityInfo_Validate(input, c);
    	//sC.suspend_COPS_validate(input, c);
    	//sC.SuspendAccount_Validate(input, c);
    	//sC.orderUpdate_Validate(input, c);
    	lb.orderUpdate_Validatesus(input, c);
    }
    
    public void RestoreValidationsservices(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException 
    {
    	 
    	RestoreClass rC=new RestoreClass();
    	
    	String val="true";
        int noofCallsValidated=4;
        int i;
        OUTER1:
        for( i=0;i<=noofCallsValidated;i++)
        {
     	   Application.showMessage("The val of I is"+i);
     	   switch(i)
     	   {
     	   case 0:
     		  val= VirtualizegetAccout_ValidateSus(input,c);            		  
     		   break;
     	   case 1:
     		   val= queryLocation_Validate(input,c);
     		   break;
     	   case 2:
     		   val= rC.processHomeSecurityInfo_Validate(input, c);
     		   break;
     	   case 3:
     		   val= rC.Activate_COPS_validate(input, c);
     		   break;
     	   case 4:
     		   val=  lb.orderUpdate_ValidateRes(input, c);
     		   break;
     	  
     		 
     		   default:
     			   break;
     	   }
     	   if(val.equalsIgnoreCase("false"))
     	   {
     		   c.report("Validation calls could found on time as per dynamic think time so its quiting entire flow validation");
     		   break OUTER1;
     	   }
     	   else
     	   {
     		   continue OUTER1;
     	   }
        }
       	
    	
    }
    
    public void InsightRestoreValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException 
    {
    	
    	
    	RestoreClass rC=new RestoreClass();
    	Thread.sleep(40000);
    	//rC.getAccout_Validate(input, c);
    	lb.SimgetAccout_ValidateSus(input,c);
    	queryLocation_Validate(input,c);
    	rC.processHomeSecurityInfo_Validate(input, c);
    	//rC.Activate_COPS_validate(input, c);
    	//rC.RestoreAccount_Validate(input, c);
    	//rC.orderUpdate_Validate(input, c);
    	lb.orderUpdate_ValidateRes(input, c);
    }
    
    public void HandleInvalidCancelValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, SAXException
    {
    	if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: UcontrolSimulator").equals("1"))
    	{
    	
    	CancelClass cC=new CancelClass();
    	String val="true";
        int noofCallsValidated=5;
        int i;
        OUTER1:
        for( i=0;i<=noofCallsValidated;i++)
        {
     	   Application.showMessage("The val of I is"+i);
     	   switch(i)
     	   {
     	   case 0:
     		  val= VirtualizegetAccout_ValidateSus(input,c);            		  
     		   break;
     	   case 1:
     		   val= queryLocation_Validate(input,c);
     		   break;
     	   case 2:
     		   val= cC.processHomeSecurityInfo_Validate(input, c);
     		   break;
     	   case 3:
     		   val= cC.deleteUnactivatedAccount_Validate(input, c);
     		   break;
     	   case 4:
     		   val=  cC.DisconnectAccount_CANCEL_Validate(input, c);
     		   break;
     	   case 5:
     		   val= lb.orderUpdate_ValidateCancel(input, c);
     		   break;
     	   
     		   default:
     			   break;
     	   }
     	   if(val.equalsIgnoreCase("false"))
     	   {
     		   c.report("Validation calls could found on time as per dynamic think time so its quiting entire flow validation");
     		   break OUTER1;
     	   }
     	   else
     	   {
     		   continue OUTER1;
     	   }
        }
        
         	
    	
    	
    	}
    	else
    	{
    		
        	CancelClass cC=new CancelClass();
        	String val="true";
            int noofCallsValidated=5;
            int i;
            OUTER1:
            for( i=0;i<=noofCallsValidated;i++)
            {
         	   Application.showMessage("The val of I is"+i);
         	   switch(i)
         	   {
         	   case 0:
         		  val= NormalgetAccout_ValidateSus(input,c);          		  
         		   break;
         	   case 1:
         		   val= queryLocation_Validate(input,c);
         		   break;
         	   case 2:
         		   val= cC.processHomeSecurityInfo_Validate(input, c);
         		   break;
         	   case 3:
         		   val= cC.deleteUnactivatedAccount_Validate(input, c);
         		   break;
         	   case 4:
         		   val=  cC.DisconnectAccount_CANCEL_Validate(input, c);
         		   break;
         	   case 5:
         		   val= lb.orderUpdate_ValidateCancel(input, c);
         		   break;
         	   
         		   default:
         			   break;
         	   }
         	   if(val.equalsIgnoreCase("false"))
         	   {
         		   c.report("Validation calls could found on time as per dynamic think time so its quiting entire flow validation");
         		   break OUTER1;
         	   }
         	   else
         	   {
         		   continue OUTER1;
         	   }
            }
            
             	
        	        	
      	
    	
    	}
    	
    }
    
    public void InsightCancelValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException
    {
    	
    	CancelClass cC=new CancelClass();
    	//lR.LoadValuestoGlobalList(input, c);
    	Thread.sleep(50000);
    	cC.getAccout_Validate(input, c);
    	queryLocation_Validate(input,c);
    	cC.processHomeSecurityInfo_Validate(input, c);
    	cC.deleteUnactivatedAccount_Validate(input, c);
    	//cC.DisconnectAccount_CANCEL_Validate(input, c);
    	cC.orderUpdate_Validate(input, c);
    	
    }
    
    /**
     * @author 266216 
     * rtpTestInterface_Validate(Object input, ScriptingContext c) will take Scripting context class from parasoft jar.
     * @throws InterruptedException 
     *
     */
    
	
    public void rtpTestInterface_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
    {
    		 
    		 ResultSet  rs;
    		 int callFound=0, v1=0;
    		 String xmldata1 ="receive_data";
             String xmldata2 ="SEND_DATA";  
             Thread.sleep(7000);
             
             Application.showMessage("----------------------------------------------------------");
             Application.showMessage("************rtpTestInterface_Validate Function************");    
             Application.showMessage("----------------------------------------------------------");
                 
    		try
    		{
    			
    			//Lp.Opconnection();
    			//Class.forName("oracle.jdbc.driver.OracleDriver");
    			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1", "CWDEVOP","CWDEVOP");		
    			Statement st =Lp. dBConnect(input, c);	 
    			
    	        rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'eep:XHSEEPOrder/xhsevent' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 28");
    	        Application.showMessage("Check");
    	        while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
    	        {
    	        	Application.showMessage("Check");
    	              Application.showMessage("RTPTestInterface msgid= "+ rs.getString(1));
    	              rowmsg=rs.getString(1);	          
    	              if(rs.getBlob("receive_data")==null)
    	              {
    	           
    	            	String senddata =Lp.extractXml(rs,xmldata2);	           
    		            String accountNumber_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Account/@accountNumber");
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
    		            	 String locationId_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Location/@locationId");
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
    					            	 String PrimaryEmail_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Account/PrimaryEmail");
    					            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
                                         c.setValue("emailAddress", PrimaryEmail_rtpTest);
    							            
    							         String FirstName_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Account/FirstName");
    							         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
    							         c.setValue("FirstName", FirstName_rtpTest); 
    							         
    							         String LastName_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Account/LastName");
    							         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
    							         c.setValue("LastName", LastName_rtpTest); 
    							         
    							         String OrderStatus_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderStatus");
    							         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
    							            
    							         String OrderType_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderType");
    							         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
    							         
    							         String RescheduleIndicator_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
    							         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
    							         String EventSource= Lp.nodeFromKey(senddata,"<EventSource>","</EventSource>");
    							         c.setValue("sEventSource", EventSource);
    							         Application.showMessage("EventSource is::"+c.getValue("sEventSource")); 
    							         
    							         String city=  Lp.nodeFromKey(senddata,"<City>","</City>");
    							         c.put("Ecity",city);
    							         Application.showMessage("Ecity is::"+c.get("Ecity")); 
    							         
    							         String Zipcode=  Lp.nodeFromKey(senddata,"<Zipcode>","</Zipcode>");
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
    	            	
    	            	String recievedata=Lp.extractXml(rs,xmldata1);
    		         
    		            String accountNumber_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/@accountNumber");
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
    		            	 String locationId_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Location/@locationId");
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
    					            	 String PrimaryEmail_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
    					            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
    					            	 c.setValue("emailAddress", PrimaryEmail_rtpTest);
    					            	 
    							         String FirstName_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/FirstName");
    							         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
    							         c.setValue("FirstName", FirstName_rtpTest);
    							         
    							         String LastName_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/LastName");
    							         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
    							         c.setValue("LastName", LastName_rtpTest); 
    							         
    							         String OrderStatus_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
    							         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
    							            
    							         String OrderType_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
    							         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
    							         
    							         String RescheduleIndicator_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
    							         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
    							         String EventSource= Lp.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
    							         c.setValue("sEventSource", EventSource);
    							         
    							         String city=  Lp.nodeFromKey(recievedata,"<City>","</City>");
    							         c.put("Ecity",city);
    							         Application.showMessage("Ecity is::"+c.get("Ecity")); 
    							         
    							         String Zipcode=  Lp.nodeFromKey(recievedata,"<City>","</City>");
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
    		            	String recievedata=Lp.extractXml(rs,xmldata1);
    				         
    			            String accountNumber_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/@accountNumber");
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
    			            	 String locationId_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Location/@locationId");
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
    						            	 String PrimaryEmail_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
    						            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
    						            	 c.setValue("emailAddress", PrimaryEmail_rtpTest);
    						            	 
    								         String FirstName_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/FirstName");
    								         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
    								         c.setValue("FirstName", FirstName_rtpTest);
    								         
    								         String LastName_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/LastName");
    								         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
    								         c.setValue("LastName", LastName_rtpTest); 
    								         
    								         String OrderStatus_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
    								         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
    								            
    								         String OrderType_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
    								         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
    								         
    								         String RescheduleIndicator_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
    								         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
    								         String EventSource= Lp.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
    								         c.setValue("sEventSource", EventSource);
    								         
    								         String city=  Lp.nodeFromKey(recievedata,"<City>","</City>");
    								         c.put("Ecity",city);
    								         Application.showMessage("Ecity is::"+c.get("Ecity")); 
    								         
    								         String Zipcode=  Lp.nodeFromKey(recievedata,"<City>","</City>");
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
	
	

	
	public String getCustomerPermitRequirements_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException
	{
		  // Think time in JVM [waits for 10 secs here]
		 
		 ResultSet  rs;
		 String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
         String Time= c.get("BaseTime").toString();
         String rscallpresent="true";
        // c.setValue("getCustomerPermitRequirements","true");
         Application.showMessage("----------------------------------------------------------");
         Application.showMessage("*****getCustomerPermitRequirements_Validate Function******");    
         Application.showMessage("----------------------------------------------------------");
         System.out.println("----------------------------------------------------------");
         System.out.println("*****getCustomerPermitRequirements_Validate Function******");    
         System.out.println("----------------------------------------------------------");
        // Application.showMessage("Value is"+c.getValue("ACC_input"));
        // Lp.findThinktimeOperationRTP(input, c);
       //  c.setValue("getCustomerPermitRequirements","false"); 
         HashMap Operation=Lp.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("getCustomerPermitRequirements"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getCustomerPermitRequirements"));
	     System.out.println("OPERATIONValidation is "+(String) Operation.get("getCustomerPermitRequirements"));

		try
		{
		//	 Statement st =Lp. dBConnect(input, c);
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'hss:HomeSecurityServicePort/getCustomerPermitRequirements' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=Lp.reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{
	         while (rs.next())
		        {
		            
		             rowmsg=rs.getString(1);
		             Application.showMessage("RowMsgID::"+rowmsg);
		             Application.showMessage("RowMsgID::"+rowmsg);
		            if(rs.getBlob("receive_data")==null)
		            {
		            
		            	System.out.printf("Your Recieve XML is NULL \n");
		            	Application.showMessage("Your Recieve XML is NULL \n");
		            	String senddata =Lp.extractXml(rs,xmldata2);
			            System.out.printf("SEND XML is  \n",senddata );	
			            Application.showMessage("SEND XML is  \n"+ senddata);	               		       
		            }
		            else if(rs.getBlob("SEND_DATA")==null)
		            {
		            	System.out.printf("Your SEND XML is NULL \n");	
		            	String recievedata=Lp.extractXml(rs,xmldata1);
			            System.out.printf("RECIEVE XML is \n",recievedata);
			            Application.showMessage("Your Recieve XML is NULL \n");
			            Application.showMessage("Recieve XML is  \n"+ recievedata);	
			            System.out.println("Your Recieve XML is NULL \n");
			            System.out.println("Recieve XML is  \n"+ recievedata);	
		            }     
			           			    
		            else
		            { 
		            
		                String recievedata = Lp.extractXml(rs,xmldata1); 
		              Application.showMessage("Recieve XML is  \n"+ recievedata);
		              System.out.println("Recieve XML is  \n"+ recievedata);
			           // Application.showMessage("RECIEVE XML is %s \n",recievedata); 
			            
			            String senddata = Lp.extractXml(rs,xmldata2);
			            //System.out.printf("SEND XML is %s \n",senddata);
			            if(c.getValue("sEventSource").equalsIgnoreCase("DDP"))
			            {
				            String SendAccount_getCus = Lp.nodeFromKey(senddata,"<hst:billingArrangementID>","</hst:billingArrangementID>");
				            Application.showMessage("SEND Xml Account Number is ::"+SendAccount_getCus);
				            System.out.println("SEND Xml Account Number is ::"+SendAccount_getCus);
				            if(SendAccount_getCus==null)
				            {
					            c.report("Send Xml Account Number is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+SendAccount_getCus);
				            	 System.out.println("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+SendAccount_getCus);
				            	 if(SendAccount_getCus.equals(c.getValue("ACC_input")))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: WebServicall-getCustomerPermitRequirements********");
					            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+SendAccount_getCus);
					            	 System.out.println("*******Validation Point 4 :: WebServicall-getCustomerPermitRequirements********");
					            	 System.out.println("Validation is Successfull with AccountNumber::"+" "+SendAccount_getCus);
					            	 v3=1;
					             }
					             else
					             {
					            	 c.report("Account Number at Send Xml not Validated as "+SendAccount_getCus);
					             }
				            }
				            
				            String billingArrangementID_getCus = Lp.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
					         
					          
				            if(billingArrangementID_getCus==null)
				            {
					            System.out.printf("billingArrangementID_getCus value from getCustomerPermitRequirements is ::NULL \n");
					            continue;
				            }
				            else
				            {
				            	
				            	Application.showMessage("billingArrangementID value from getCustomerPermitRequirements is::"+billingArrangementID_getCus);
				            	System.out.println("billingArrangementID value from getCustomerPermitRequirements is::"+billingArrangementID_getCus);
				            	//Application.showMessage(c.getValue("ACC_input"));
				            	//Application.showMessage(billingArrangementID_getCus);
				            	
				            	if(billingArrangementID_getCus.equals(c.getValue("ACC_input")))
				            	{
				            	    Application.showMessage("getCustomerPermitRequirements msgid= "+ rs.getString(1));
				            		Application.showMessage("Recieve XML is  \n"+ recievedata);
				            		Application.showMessage("SEND XML is  \n"+ senddata);
				            		Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
				            		Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_getCus);
				            		System.out.println("getCustomerPermitRequirements msgid= "+ rs.getString(1));
				            		System.out.println("Recieve XML is  \n"+ recievedata);
				            		System.out.println("SEND XML is  \n"+ senddata);
				            		System.out.println("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
				            		System.out.println("Validation is Successfull with Input Account Number"+billingArrangementID_getCus);
				            		callFound=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            	
				            	if(callFound==1)
				            	{
				            		 String permitreq_getCus= Lp.nodeFromKey(recievedata,"<typ:permitFee>","</typ:permitFee>");
							           
							            if(permitreq_getCus==null)
							            {
							            	Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::NULL \n");
								            System.out.printf("PERMIT REQUIRED value from getCustomerPermitRequirements is ::NULL \n");
								            c.report("PERMIT REQUIRED is NULL");
								            continue;
							            }
							            else
							            {
							            	 Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+permitreq_getCus);
							            	 System.out.println("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+permitreq_getCus);
							            	 if(permitreq_getCus.equals("0"))
								             {
								            	 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
								            	 Application.showMessage("Validation is Successfull with PERMIT REQUIRED"+" "+permitreq_getCus);
								            	 System.out.println("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
								            	 System.out.println("Validation is Successfull with PERMIT REQUIRED"+" "+permitreq_getCus);
								            	 v1=1;
								             }
								             else
								             {
								            	 continue;
								             }
							            }
							           	            
							            					            
							            String responseStatus_getCus = Lp.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:responseStatus>");
							            if(responseStatus_getCus==null)
							            {
								            c.report("Response Status is NULL");
								            continue;
							            }
							            else
							            {
							            	 Application.showMessage("responseStatus value from getCustomerPermitRequirements is ::"+" "+responseStatus_getCus);
							            	 System.out.println("responseStatus value from getCustomerPermitRequirements is ::"+" "+responseStatus_getCus);
							            	 if(responseStatus_getCus.equalsIgnoreCase("Success"))
								             {
								            	 Application.showMessage("*******Validation Point 3 :: WebServicall-rtpTestInterface********");
								            	 Application.showMessage("Validation is Successfull with responseStatus::"+" "+responseStatus_getCus);
								            	 System.out.println("*******Validation Point 3 :: WebServicall-rtpTestInterface********");
								            	 System.out.println("Validation is Successfull with responseStatus::"+" "+responseStatus_getCus);
								            	 v2=1;
								             }
								             else
								             {
								            	 continue;
								             }
							            } 
							            
				            	}
				            }
			            }
			            
			            else if(c.getValue("sEventSource").equalsIgnoreCase("CSG"))
			            {
				            String SendAccount_getCus = Lp.nodeFromKey(senddata,"<hst:csgHouseKey>","</hst:csgHouseKey>");
				            Application.showMessage("SEND Xml Account Number is ::"+SendAccount_getCus);
				            System.out.println("SEND Xml Account Number is ::"+SendAccount_getCus);
				            if(SendAccount_getCus==null)
				            {
					            c.report("Send Xml Account Number is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+SendAccount_getCus);
				            	 System.out.println("Account Number from Send Xml  from getCustomerPermitRequirements is ::"+" "+SendAccount_getCus);
				            	 if(SendAccount_getCus.equals(c.getValue("sHOUSE_KEY")))
					             {
					            	 Application.showMessage("*******Validation Point 4 :: WebServicall-getCustomerPermitRequirements********");
					            	 Application.showMessage("Validation is Successfull with sHOUSE_KEY::"+" "+SendAccount_getCus);
					            	 System.out.println("*******Validation Point 4 :: WebServicall-getCustomerPermitRequirements********");
					            	 System.out.println("Validation is Successfull with sHOUSE_KEY::"+" "+SendAccount_getCus);
					            	 v3=1;
					             }
					             else
					             {
					            	 c.report("House Key at Send Xml not Validated as "+SendAccount_getCus);
					             }
				            }
				            
				            String ErrorCode= Lp.nodeFromKey(recievedata, "<typ:messageCode>", "</typ:messageCode>");
				            if(ErrorCode.equalsIgnoreCase("HOMESECURITYSERVICE-113"))
				            {
				            	Application.showMessage("HOMESECURITY ERROR");
				            	System.out.println("HOMESECURITY ERROR");
				            	callFound=1;
				            	v1=1;
				            	v2=1;
				            	v3=1;
				            }
				            else
				            {
				            	String billingArrangementID_getCus = Lp.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
						         
						          
					            if(billingArrangementID_getCus==null)
					            {
						            System.out.printf("billingArrangementID_getCus value from getCustomerPermitRequirements is ::NULL \n");
						            continue;
					            }
					            else
					            {
					            	
					            	Application.showMessage("billingArrangementID value from getCustomerPermitRequirements is::"+billingArrangementID_getCus);
					            	System.out.println("billingArrangementID value from getCustomerPermitRequirements is::"+billingArrangementID_getCus);
					            	//Application.showMessage(c.getValue("ACC_input"));
					            	//Application.showMessage(billingArrangementID_getCus);
					            	
					            	if(billingArrangementID_getCus.equals(c.getValue("ACC_input")))
					            	{
					            	    Application.showMessage("getCustomerPermitRequirements msgid= "+ rs.getString(1));
					            		Application.showMessage("Recieve XML is  \n"+ recievedata);
					            		Application.showMessage("SEND XML is  \n"+ senddata);
					            		Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
					            		Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_getCus);
					            		System.out.println("getCustomerPermitRequirements msgid= "+ rs.getString(1));
					            		System.out.println("Recieve XML is  \n"+ recievedata);
					            		System.out.println("SEND XML is  \n"+ senddata);
					            		System.out.println("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
					            		System.out.println("Validation is Successfull with Input Account Number"+billingArrangementID_getCus);
					            		callFound=1;
					            	}
					            	else
					            	{
					            		continue;
					            	}
					            	
					            	if(callFound==1)
					            	{
					            		 String permitreq_getCus= Lp.nodeFromKey(recievedata,"<typ:permitFee>","</typ:permitFee>");
								           
								            if(permitreq_getCus==null)
								            {
								            	Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::NULL \n");
									            System.out.printf("PERMIT REQUIRED value from getCustomerPermitRequirements is ::NULL \n");
									            c.report("PERMIT REQUIRED is NULL");
									            continue;
								            }
								            else
								            {
								            	 Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+permitreq_getCus);
								            	 System.out.println("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+permitreq_getCus);
								            	 if(permitreq_getCus.equals("0"))
									             {
									            	 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
									            	 Application.showMessage("Validation is Successfull with PERMIT REQUIRED"+" "+permitreq_getCus);
									            	 System.out.println("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
									            	 System.out.println("Validation is Successfull with PERMIT REQUIRED"+" "+permitreq_getCus);
									            	 v1=1;
									             }
									             else
									             {
									            	 continue;
									             }
								            }
								           	            
								            					            
								            String responseStatus_getCus = Lp.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:responseStatus>");
								            if(responseStatus_getCus==null)
								            {
									            c.report("Response Status is NULL");
									            continue;
								            }
								            else
								            {
								            	 Application.showMessage("responseStatus value from getCustomerPermitRequirements is ::"+" "+responseStatus_getCus);
								            	 System.out.println("responseStatus value from getCustomerPermitRequirements is ::"+" "+responseStatus_getCus);
								            	 if(responseStatus_getCus.equalsIgnoreCase("Success"))
									             {
									            	 Application.showMessage("*******Validation Point 3 :: WebServicall-rtpTestInterface********");
									            	 Application.showMessage("Validation is Successfull with responseStatus::"+" "+responseStatus_getCus);
									            	 System.out.println("*******Validation Point 3 :: WebServicall-rtpTestInterface********");
									            	 System.out.println("Validation is Successfull with responseStatus::"+" "+responseStatus_getCus);
									            	 v2=1;
									             }
									             else
									             {
									            	 continue;
									             }
								            } 
								            
					            	}
					            }
				            }	
			            }			            			            	
			            }
			            		           
			            if(v1*v2*v3==1)
			            {
			            	ValidationSuccess=1;
			            	Application.showMessage("Validation Points are Success");
			            	System.out.println("Validation Points are Success");
			            	break;
			            }
		            
					// ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));    		       
		             }
		            
		           
		            
		         
	             if(ValidationSuccess*callFound==0)
	             {
	            	c.report("WebService call getCustomerPermitRequirements not Validated");
	            	c.put("result", "false");
	             }
	             else
	             {
	            	Application.showMessage("********WebService Call::getCustomerPermitRequirements Valiadted ********");
	            	System.out.println("********WebService Call::getCustomerPermitRequirements Valiadted ********");	
	             }
	          //   st.close();
		}
		}
		
		catch (SQLException e)
		{
		    Application.showMessage("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
	}




public String createCMSAccountID_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	 // Think time in JVM [waits for 10 secs here]
    
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
    // c.setValue("createCMSaccountID","true");
     Application.showMessage("-------------------------------------------------");
     Application.showMessage("*****createCMSAccountID_Validate Function******");       
     Application.showMessage("-------------------------------------------------");
     System.out.println("-------------------------------------------------");
     System.out.println("*****createCMSAccountID_Validate Function******");       
     System.out.println("-------------------------------------------------");
    // Lp.findThinktimeOperationRTP(input, c);
    // c.setValue("createCMSaccountID","false");
     HashMap Operation=Lp.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("createCMSAccountID"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("createCMSAccountID"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("createCMSAccountID"));

	try
	{
		// Statement st =Lp. dBConnect(input, c);
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'hss:HomeSecurityServicePort/createCMSAccountID' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"'order by creation_time desc)where rownum <= 20");
		rs=Lp.reduceThinkTimeRTP(input, c);
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
            	String senddata =Lp.extractXml(rs,xmldata2);
            	System.out.println("Your Recieve XML is::\n"+senddata);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");
            	Application.showMessage("Your SEND XML is NULL \n");
            	String recievedata=Lp.extractXml(rs,xmldata1);
            	System.out.println("RECIEVE XML is ::\n"+recievedata);   
            	System.out.println("RECIEVE XML is ::\n"+recievedata);   
            }
            else
            { 
                          
	            String senddata = Lp.extractXml(rs,xmldata2);
	           // Application.showMessage("SEND XML is :: \n"+senddata);
	            String recievedata = Lp.extractXml(rs,xmldata1);      
	          //  Application.showMessage("RECIEVE XML is ::"+recievedata);
	           
	            String billingArrangementID_cmsSend= Lp.nodeFromKey(senddata,"<hst:billingArrangementID>","</hst:billingArrangementID>");
	            Application.showMessage("billingArrangementID_cmsSend is ::"+billingArrangementID_cmsSend); 
	            System.out.println("billingArrangementID_cmsSend is ::"+billingArrangementID_cmsSend); 
	            if(billingArrangementID_cmsSend.equals(c.getValue("ACC_input")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-createCMSAccountID_Validate********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_cmsSend);
            		System.out.println("Recieve XML is::  \n"+ recievedata);
            		System.out.println("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
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
	      
		            String cmsAccountID= Lp.nodeFromKey(recievedata,"<typ:cmsAccountID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:cmsAccountID>");
		            Application.showMessage("cmsAccountID is ::"+cmsAccountID);
		            System.out.println("cmsAccountID is ::"+cmsAccountID);
		            c.setValue("CcentralNum", cmsAccountID);
		            
		            String billingArrangementIDRes_CMSAccountID= Lp.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
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

		            String responseStatus_CMSAccountID= Lp.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:responseStatus>");
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


public String queryLocation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	 // Think time in JVM [waits for 10 secs here]
     
	 ResultSet  rs;
	 int callFound=0,v1=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
    // c.setValue("queryLocation","true");
     Application.showMessage("-------------------------------------------------");
     Application.showMessage("*****Query Location _Validate Function******");       
     Application.showMessage("-------------------------------------------------");
     System.out.println("-------------------------------------------------");
     System.out.println("*****Query Location _Validate Function******");       
     System.out.println("-------------------------------------------------");
    // Lp.findThinktimeOperationRTP(input, c);
    // c.setValue("queryLocation","false"); 
     HashMap Operation=Lp.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("queryLocation"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("queryLocation"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("queryLocation"));
     
     

	try
	{
		// Statement st =Lp. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=Lp.reduceThinkTimeRTP(input, c);
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
            	
            	String senddata =Lp.extractXml(rs,xmldata2);
            	Application.showMessage("Your Send XML is::\n"+senddata);
            	System.out.println("Your Send XML is::\n"+senddata);
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");	
            	System.out.println("Your SEND XML is NULL \n");	
            	String recievedata=Lp.extractXml(rs,xmldata1);
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);
            	System.out.println("RECIEVE XML is ::\n"+recievedata);
            }
            else
            { 
                          
	            String senddata = Lp.extractXml(rs,xmldata2);           
	            String recievedata = Lp.extractXml(rs,xmldata1);      
	          	           
	            String legacyID= Lp.nodeFromKey(senddata,"<lt:legacyID>","</lt:legacyID>");	            
	            Application.showMessage("legacyID is ::"+legacyID); 
	            System.out.println("legacyID is ::"+legacyID); 
	            if(legacyID.equals(c.getValue("sHOUSE_KEY")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-createCMSAccountID_Validate********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+legacyID);
            		System.out.println("Recieve XML is::  \n"+ recievedata);
            		System.out.println("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		System.out.println("*******Validation Point 1 :: WebServicall-createCMSAccountID_Validate********");
            		System.out.println("Validation is Successfull with Input Account Number"+legacyID);
            		
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	 	            String locationID= Lp.nodeFromKey(recievedata,"<typ:locationID xmlns:typ=\"http://xml.comcast.com/location/types\">","</typ:locationID>");
	 	            Application.showMessage("locationID is ::"+locationID);
	 	           System.out.println("locationID is ::"+locationID);
	 	            c.setValue("LocationID", locationID);
	 	            
	 	            
	 	            String locationStatus= Lp.nodeFromKey(recievedata,"<typ:locationStatus xmlns:typ=\"http://xml.comcast.com/location/types\">","</typ:locationStatus>");
	 	            Application.showMessage("locationStatus is :: "+locationStatus);
	 	           System.out.println("locationStatus is :: "+locationStatus);
	 	            
	 	           if(locationStatus==null)
		             {
			            c.report("Send Xml Account Number is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("locationStatus from Send Xml  from queryLocation is ::"+" "+locationStatus);
		            	 System.out.println("locationStatus from Send Xml  from queryLocation is ::"+" "+locationStatus);
		            	 if(locationStatus.equalsIgnoreCase("Complete"))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-locationStatus********");
			            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+locationStatus);
			            	 System.out.println("*******Validation Point 2 :: WebServicall-locationStatus********");
			            	 System.out.println("Validation is Successfull with AccountNumber::"+" "+locationStatus);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("locationStatus at Send Xml not Validated as "+locationStatus);
			             }
		            }		

	 	            String houseNumber= Lp.nodeFromKey(recievedata,"<typ:houseNumber>","</typ:houseNumber>");
	 	            Application.showMessage("houseNumber is :: "+houseNumber); 
	 	           System.out.println("houseNumber is :: "+houseNumber); 
	 	            c.setValue("HouseNumber", houseNumber);
	 	            
	 	            String QpostalCode= Lp.nodeFromKey(recievedata,"<typ:zip5>","</typ:zip5>");
	 	            Application.showMessage("houseNumber is :: "+houseNumber);
	 	           System.out.println("houseNumber is :: "+houseNumber);
	 	            c.put("QpostalCode", QpostalCode);
	 	            
	 	            String streetName= Lp.nodeFromKey(recievedata,"<typ:streetName>","</typ:streetName>").trim();
	 	            Application.showMessage("streetName is :: "+streetName); 
	 	           System.out.println("streetName is :: "+streetName); 
	 	            c.setValue("StreetName", streetName);
	 	            
	 	            String streetSuffix= Lp.nodeFromKey(recievedata,"<typ:streetSuffix>","</typ:streetSuffix>").trim();
	 	            Application.showMessage("streetSuffix is :: "+streetSuffix); 
	 	           System.out.println("streetSuffix is :: "+streetSuffix); 
	 	             
	 	            String city= Lp.nodeFromKey(recievedata,"<typ:city>","</typ:city>").trim();
	 	            Application.showMessage("city is :: "+city); 
	 	           System.out.println("city is :: "+city); 
	 	            c.setValue("City", city);
	 	            c.put("city",city);
	 	           
	 	            String policeNumber= Lp.nodeFromKey(recievedata,"<typ:policeNumber>","</typ:policeNumber>");
	 	            Application.showMessage("policeNumber is :: "+policeNumber); 
	 	           System.out.println("policeNumber is :: "+policeNumber); 
	 	            c.setValue("PoliceNumber", policeNumber);
	 	           
	 	            String Loc_fireNumber= Lp.nodeFromKey(recievedata,"<typ:fireNumber>","</typ:fireNumber>");
	 	            Application.showMessage("fireNumber is :: "+Loc_fireNumber); 
	 	           System.out.println("fireNumber is :: "+Loc_fireNumber); 
	 	            c.setValue("FireNumber", Loc_fireNumber);
	 	           
	 	            String Loc_medicalNumber= Lp.nodeFromKey(recievedata,"<typ:medicalNumber>","</typ:medicalNumber>");
	 	            Application.showMessage("medicalNumber is :: "+Loc_medicalNumber); 
	 	           System.out.println("medicalNumber is :: "+Loc_medicalNumber); 
	 	            c.setValue("medicalNumber", Loc_medicalNumber);
	            break;
	            }
             }
         }
         if(v1*callFound==1)
         {
         	Application.showMessage("WebService Call :: Query Location is Success[All validation points are Success]");
         	System.out.println("WebService Call :: Query Location is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call :: Query Location is Failed with Validation Errors");
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


//done till above


public String getAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	 // Think time in JVM [waits for 10 secs here]
     
	 ResultSet  rs;
	 int callFound=0,v1=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
    // c.setValue("getAccount","true");
     Application.showMessage("-------------------------------------------------");
     Application.showMessage("*****getAccount_Validate _Validate Function******");       
     Application.showMessage("-------------------------------------------------");
     System.out.println("-------------------------------------------------");
     System.out.println("*****getAccount_Validate _Validate Function******");       
     System.out.println("-------------------------------------------------");
    // Lp.findThinktimeOperationRTP(input, c);
    // c.setValue("getAccount","false"); 
     HashMap Operation=Lp.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("UcontrolgetAccount"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ClSgetAccountSim"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("ClSgetAccountSim"));

	try
	{
		// Statement st =Lp. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=Lp.reduceThinkTimeRTP(input, c);
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
            	
            	String senddata =Lp.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            	System.out.println("Your Recieve XML is::\n"+senddata);
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");	
            	System.out.println("Your SEND XML is NULL \n");	
            	String recievedata=Lp.extractXml(rs,xmldata1);
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);  
            	System.out.println("RECIEVE XML is ::\n"+recievedata);  
            }
            else
            { 
                          
	            String senddata = Lp.extractXml(rs,xmldata2);           
	            String recievedata = Lp.extractXml(rs,xmldata1);      
	          	           
	            String id= Lp.nodeFromKey(senddata,"<resourceId>","</resourceId>");
	            Application.showMessage("id is ::"+id); 
	            System.out.println("id is ::"+id); 
	            if(id.equals(c.getValue("ACC_input")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
	            	System.out.println("*******Validation Point 1 :: WebServicall-Get Account Call********");
	            	System.out.println("Validation is Successfull with Input Account Number"+id);
	            	System.out.println("*******Validation Point 1 :: WebServicall-Get Account Call********");
	            	System.out.println("Validation is Successfull with Input Account Number"+id);
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	            	
	 	             String errorCode_getAcc= Lp.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
	 	             Application.showMessage("errorCode is ::"+errorCode_getAcc);
	 	            System.out.println("errorCode is ::"+errorCode_getAcc);
	 	             
	 	             if(errorCode_getAcc==null)
		             {
			            c.report("Recieve Xml Account Number is NULL");
			            continue;
		             }
		             else
		             {
		            	 Application.showMessage("locationStatus from Send Xml  from queryLocation is ::"+" "+errorCode_getAcc);
		            	 System.out.println("locationStatus from Send Xml  from queryLocation is ::"+" "+errorCode_getAcc);
		            	 if(errorCode_getAcc.equalsIgnoreCase("UCE-15101"))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
			            	 Application.showMessage("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
			            	 System.out.println("*******Validation Point 2 :: WebServicall-errorCode_getAcc********");
			            	 System.out.println("Validation is Successfull with errorCode::"+" "+errorCode_getAcc);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("errorCode_getAcc at Send Xml not Validated as "+errorCode_getAcc);
			             }
		            }		
	 	            
	 	             String errorMessage_getAcc= Lp.nodeFromKey(recievedata,"<errorMessage xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorMessage>");
	 	             Application.showMessage("errorMessage is::"+errorMessage_getAcc); 
	 	            System.out.println("errorMessage is::"+errorMessage_getAcc); 

	 	            
	            break;
	            }
             }
         }
         if(v1*callFound==1)
         {
         	Application.showMessage("WebService Call :: Get Account is Success[All validation points are Success]");
         	System.out.println("WebService Call :: Get Account is Success[All validation points are Success]");
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
		
	}
	return rscallpresent;
}






public String processHomeSecurityInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	 // Think time in JVM [waits for 10 secs here]
    
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
    // c.setValue("processHomeSecurity","true");
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
     Application.showMessage("----------------------------------------------------");
     System.out.println("-----------------------------------------------------");
     System.out.println("*****processHomeSecurityInfo_Validate Function******");       
     System.out.println("----------------------------------------------------");
   //  Lp.findThinktimeOperationRTP(input, c);
    // c.setValue("processHomeSecurity","false");
     HashMap Operation=Lp.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo"));


	try
	{
		// Statement st =Lp. dBConnect(input, c);	
      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=Lp.reduceThinkTimeRTP(input, c);
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
            	
            	String senddata =Lp.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            	System.out.println("Your Recieve XML is::\n"+senddata);
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");
            	System.out.println("Your SEND XML is NULL \n");
            	String recievedata=Lp.extractXml(rs,xmldata1);
            	System.out.println("RECIEVE XML is ::\n"+recievedata); 
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
            }
            else
            { 
                          
	            String senddata = Lp.extractXml(rs,xmldata2);           
	            String recievedata = Lp.extractXml(rs,xmldata1);      
	          	           
	            String accountNumber_DDS= Lp.nodeFromKey(senddata,"<messagesDDS:accountNumber>","</messagesDDS:accountNumber>");
 	            Application.showMessage("accountNumber is ::"+accountNumber_DDS);
 	           System.out.println("accountNumber is ::"+accountNumber_DDS);
	            if(accountNumber_DDS.equals(c.getValue("ACC_input")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	System.out.println("Recieve XML is::  \n"+ recievedata);
	            	System.out.println("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_DDS);
            		System.out.println("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
            		System.out.println("Validation is Successfull with Input Account Number"+accountNumber_DDS);
            		
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	 	            String firstName_DDS= Lp.nodeFromKey(senddata,"<typesDDS:firstName>","</typesDDS:firstName>");
	 	            Application.showMessage("firstName is ::"+firstName_DDS);
	 	           System.out.println("firstName is ::"+firstName_DDS);
	 	            
	 	            if(firstName_DDS==null)
		            {
			            c.report("Send Xml FirstName is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+firstName_DDS);
		            	 System.out.println("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+firstName_DDS);
		            	 if(firstName_DDS.equals(c.getValue("FirstName")))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
			            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+firstName_DDS);
			            	 System.out.println("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
			            	 System.out.println("Validation is Successfull with FirstName::"+" "+firstName_DDS);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("FirstName at Send Xml not Validated as "+firstName_DDS);
			             }
		            }		

	 	            String lastName_DDS= Lp.nodeFromKey(senddata,"<typesDDS:lastName>","</typesDDS:lastName>");
	 	            Application.showMessage("lastName is ::"+lastName_DDS); 
	 	           System.out.println("lastName is ::"+lastName_DDS); 
	 	            
	 	           if(lastName_DDS==null)
		            {
			            c.report("Send Xml LastName is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+lastName_DDS);
		            	 System.out.println("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+lastName_DDS);
		            	 if(lastName_DDS.equals(c.getValue("LastName")))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
			            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+lastName_DDS);
			            	 System.out.println("*******Validation Point 3 :: WebServicall-lastName_DDS********");
			            	 System.out.println("Validation is Successfull with FirstName::"+" "+lastName_DDS);
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("LastName at Send Xml not Validated as "+lastName_DDS);
			             }
		            }

	 	            String homeSecurityLOBStatus_DDS= Lp.nodeFromKey(senddata,"<typesDDS:homeSecurityLOBStatus>","</typesDDS:homeSecurityLOBStatus>");
	 	            Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
	 	           System.out.println("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
	 	            if(homeSecurityLOBStatus_DDS==null)
		            {
			            c.report(" homeSecurityLOBStatus is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("homeSecurityLOBStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+homeSecurityLOBStatus_DDS);
		            	 System.out.println("homeSecurityLOBStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+homeSecurityLOBStatus_DDS);
		            	 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("PENDING_INSTALL"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
			            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
			            	 System.out.println("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
			            	 System.out.println("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
			            	 v3=1;
			             }
		            	 else if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("ACTIVE"))
			             {
		            		 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
			            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
			            	 System.out.println("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
			            	 System.out.println("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
			            	 v3=1;			            
			             }
			             else 
			             {
			            	Application.showMessage("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+homeSecurityLOBStatus_DDS);
			            	System.out.println("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+homeSecurityLOBStatus_DDS);
			            	continue;
			             }
		            }
	 	            
	 	            String permitRequired_DDS= Lp.nodeFromKey(senddata,"<typesDDS:permitRequired>","</typesDDS:permitRequired>");
	 	            Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
	 	           System.out.println("permitRequired is ::"+permitRequired_DDS); 
	 	           
	 	         	 	            
	 	            String authorizationGuid_DDS= Lp.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
	 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
	 	           System.out.println("authorizationGuid is::"+authorizationGuid_DDS);
	 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
	 	            
	 	            String responseStatus_DDS= Lp.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/esd-directory/14.05/types\">","</typ:responseStatus>");
	 	            Application.showMessage("responseStatus is::"+responseStatus_DDS); 
	 	           System.out.println("responseStatus is::"+responseStatus_DDS); 
	 	           if(responseStatus_DDS==null)
		            {
			            c.report(" responseStatus is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("responseStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+responseStatus_DDS);
		            	 System.out.println("responseStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+responseStatus_DDS);
		            	 if(responseStatus_DDS.equalsIgnoreCase("Success"))
			             {
			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-responseStatus********");
			            	 Application.showMessage("Validation is Successfull with responseStatus::"+" "+responseStatus_DDS);
			            	 System.out.println("*******Validation Point 5 :: WebServicall-responseStatus********");
			            	 System.out.println("Validation is Successfull with responseStatus::"+" "+responseStatus_DDS);
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
         
         if(v1*callFound*v2*v3*v4 ==1)
         {
         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
         	System.out.println("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
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



public String SetAccountBasicInformation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	 // Think time in JVM [waits for 10 secs here]
    
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
    // c.setValue("setAccountBasicInformation","true");
     Application.showMessage("-------------------------------------------------------");
     Application.showMessage("*****SetAccountBasicInformation_Validate Function******");       
     Application.showMessage("-------------------------------------------------------");
     System.out.println("-------------------------------------------------------");
     System.out.println("*****SetAccountBasicInformation_Validate Function******");       
     System.out.println("-------------------------------------------------------");
   //  Lp.findThinktimeOperationRTP(input, c);
   //  c.setValue("setAccountBasicInformation","false");
     HashMap Operation=Lp.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SetAccountBasicInformation"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SetAccountBasicInformation"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("SetAccountBasicInformation"));

	try
	{
		// Statement st =Lp. dBConnect(input, c);	
      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/SetAccountBasicInformation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=Lp.reduceThinkTimeRTP(input, c);
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
            	
            	String senddata =Lp.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            	System.out.println("Your Recieve XML is::\n"+senddata);
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");	
            	System.out.println("Your SEND XML is NULL \n");	
            	String recievedata=Lp.extractXml(rs,xmldata1);
            	System.out.println("RECIEVE XML is ::\n"+recievedata);    
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);    
            }
            else
            { 
                          
	            String senddata = Lp.extractXml(rs,xmldata2);           
	            String recievedata = Lp.extractXml(rs,xmldata1);      
	          	           
	            String CcentralNum_SetBasicAcc=Lp.xpathValue(senddata,"//SetAccountBasicInformationRequest/accountNumber");
 	            Application.showMessage("Ccentral Number is ::"+CcentralNum_SetBasicAcc); 
 	           System.out.println("Ccentral Number is ::"+CcentralNum_SetBasicAcc); 
	            if(CcentralNum_SetBasicAcc.equals(c.getValue("CcentralNum")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	System.out.println("Recieve XML is::  \n"+ recievedata);
	            	System.out.println("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-Get Account Call********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+CcentralNum_SetBasicAcc);
            		System.out.println("*******Validation Point 1 :: WebServicall-Get Account Call********");
            		System.out.println("Validation is Successfull with Input Account Number"+CcentralNum_SetBasicAcc);
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
		            String pc_SetBasicAcc=Lp.xpathValue(senddata,"//SetAccountBasicInformationRequest/pc");
	 	            Application.showMessage("pc is ::"+pc_SetBasicAcc); 
	 	           System.out.println("pc is ::"+pc_SetBasicAcc); 
	 	            
	 	           if(pc_SetBasicAcc==null)
		            {
			            c.report(" pc is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("pc from Send Xml  from SetAccountBasicInformation is ::"+" "+pc_SetBasicAcc);
		            	 System.out.println("pc from Send Xml  from SetAccountBasicInformation is ::"+" "+pc_SetBasicAcc);
		            	 if(pc_SetBasicAcc.equalsIgnoreCase("C9O2P5"))
			             {
			            	 Application.showMessage("*******Validation Point 2:: WebServicall-pc********");
			            	 Application.showMessage("Validation is Successfull with pc::"+" "+pc_SetBasicAcc);
			            	 System.out.println("*******Validation Point 2:: WebServicall-pc********");
			            	 System.out.println("Validation is Successfull with pc::"+" "+pc_SetBasicAcc);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("pc at Send Xml not Validated as "+pc_SetBasicAcc);
			             }
		            }
	 	          
	 	            String action_SetBasicAcc=Lp.xpathValue(senddata,"//SetAccountBasicInformationRequest/action");
	 	            Application.showMessage("Action is ::"+action_SetBasicAcc);
	 	           System.out.println("Action is ::"+action_SetBasicAcc);
	 	            
	 	           if(action_SetBasicAcc==null)
		            {
			            c.report(" Action is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Action from Send Xml  from SetAccountBasicInformation is ::"+" "+action_SetBasicAcc);
		            	 System.out.println("Action from Send Xml  from SetAccountBasicInformation is ::"+" "+action_SetBasicAcc);
		            	 if(action_SetBasicAcc.equalsIgnoreCase("NEW"))
			             {
			            	 Application.showMessage("*******Validation Point 3:: WebServicall-Action********");
			            	 Application.showMessage("Validation is Successfull with Action::"+" "+action_SetBasicAcc);
			            	 System.out.println("*******Validation Point 3:: WebServicall-Action********");
			            	 System.out.println("Validation is Successfull with Action::"+" "+action_SetBasicAcc);
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("Action at Send Xml not Validated as "+action_SetBasicAcc);
			             }
		            }

	 	            String dn_SetBasicAcc=Lp.xpathValue(senddata,"//SetAccountBasicInformationRequest/dn");
	 	            Application.showMessage("dn is ::"+dn_SetBasicAcc); 
	 	           System.out.println("dn is ::"+dn_SetBasicAcc); 
	 	            
	 	           if(dn_SetBasicAcc==null)
		            {
			            c.report(" dn is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("dn from Send Xml  from SetAccountBasicInformation is ::"+" "+dn_SetBasicAcc);
		            	 System.out.println("dn from Send Xml  from SetAccountBasicInformation is ::"+" "+dn_SetBasicAcc);
		            	 if(dn_SetBasicAcc.equalsIgnoreCase("COMC"))
			             {
			            	 Application.showMessage("*******Validation Point 4:: WebServicall-dn********");
			            	 Application.showMessage("Validation is Successfull with dn::"+" "+dn_SetBasicAcc);
			            	 System.out.println("*******Validation Point 4:: WebServicall-dn********");
			            	 System.out.println("Validation is Successfull with dn::"+" "+dn_SetBasicAcc);
			            	 v3=1;
			             }
			             else
			             {
			            	 c.report("dn at Send Xml not Validated as "+dn_SetBasicAcc);
			             }
		            }

	 	            String billingAccountNumber_SetBasicAcc=Lp.xpathValue(senddata,"//SetAccountBasicInformationRequest/billingAccountNumber");
	 	            Application.showMessage("billingAccountNumber is ::"+billingAccountNumber_SetBasicAcc); 
	 	           System.out.println("billingAccountNumber is ::"+billingAccountNumber_SetBasicAcc); 
                    
	 	           if(billingAccountNumber_SetBasicAcc==null)
		            {
			            c.report("Send Xml FirstName is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+billingAccountNumber_SetBasicAcc);
		            	 System.out.println("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+billingAccountNumber_SetBasicAcc);
		            	 if(billingAccountNumber_SetBasicAcc.equals(c.getValue("ACC_input")))
			             {
			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Account Number********");
			            	 Application.showMessage("Validation is Successfull with Account Number::"+" "+billingAccountNumber_SetBasicAcc);
			            	 System.out.println("*******Validation Point 5 :: WebServicall-Account Number********");
			            	 System.out.println("Validation is Successfull with Account Number::"+" "+billingAccountNumber_SetBasicAcc);
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("Account Number at Send Xml not Validated as "+billingAccountNumber_SetBasicAcc);
			             }
		            }	
	 	         	 	            
		            String readBack_SetBasicAcc=Lp.xpathValue(recievedata,"//response/readBack");
	 	            Application.showMessage("Read Back is::"+readBack_SetBasicAcc);
	 	           System.out.println("Read Back is::"+readBack_SetBasicAcc);
	 	            
	 	           if(readBack_SetBasicAcc==null)
		            {
			            c.report(" readBack is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("readBack from Send Xml  from SetAccountBasicInformation is ::"+" "+readBack_SetBasicAcc);
		            	 System.out.println("readBack from Send Xml  from SetAccountBasicInformation is ::"+" "+readBack_SetBasicAcc);
		            	 if(readBack_SetBasicAcc.equalsIgnoreCase("COMCC9O2P5SETBASICINFO"))
			             {
			            	 Application.showMessage("*******Validation Point 6:: WebServicall-readBack********");
			            	 Application.showMessage("Validation is Successfull with readBack::"+" "+readBack_SetBasicAcc);
			            	 System.out.println("*******Validation Point 6:: WebServicall-readBack********");
			            	 System.out.println("Validation is Successfull with readBack::"+" "+readBack_SetBasicAcc);
			            	 v5=1;
			             }
			             else
			             {
			            	 c.report("readBack at Send Xml not Validated as "+readBack_SetBasicAcc);
			             }
		            }
	 	            
	 	           String result_SetBasicAcc=Lp.xpathValue(recievedata,"//response/result");
	 	           Application.showMessage("Result is::"+result_SetBasicAcc);
	 	          System.out.println("Result is::"+result_SetBasicAcc);

	 	          if(result_SetBasicAcc==null)
		            {
			            c.report(" readBack is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("readBack from Send Xml  from SetAccountBasicInformation is ::"+" "+result_SetBasicAcc);
		            	 if(result_SetBasicAcc.equalsIgnoreCase("OK"))
			             {
			            	 Application.showMessage("*******Validation Point 7:: WebServicall-readBack********");
			            	 Application.showMessage("Validation is Successfull with readBack::"+" "+result_SetBasicAcc);
			            	 System.out.println("*******Validation Point 7:: WebServicall-readBack********");
			            	 System.out.println("Validation is Successfull with readBack::"+" "+result_SetBasicAcc);
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
         	System.out.println("WebService Call :: SetAccountBasicInformation is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::SetAccountBasicInformation is Failed with Validation Errors");
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






public String responderInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     
	 ResultSet  rs;
	 int callFound=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
    // c.setValue("responderInfo","true");
     String rscallpresent="true";
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("***********responderInfo_Validate Function***********");       
     Application.showMessage("-----------------------------------------------------");
     System.out.println("-----------------------------------------------------");
     System.out.println("***********responderInfo_Validate Function***********");       
     System.out.println("-----------------------------------------------------");
   //  Lp.findThinktimeOperationRTP(input, c);
   //  c.setValue("responderInfo","false"); 
     HashMap Operation=Lp.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("responderInfo"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("responderInfo"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("responderInfo"));

	try
	{
		// Statement st =Lp. dBConnect(input, c);	
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'intrado:frlResource/responderInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=Lp.reduceThinkTimeRTP(input, c);
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
            	
            	String senddata =Lp.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            	System.out.println("Your Recieve XML is::\n"+senddata);
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");
            	System.out.println("Your SEND XML is NULL \n");
            	String recievedata=Lp.extractXml(rs,xmldata1);
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
            	System.out.println("RECIEVE XML is ::\n"+recievedata); 
            }
            else
            { 
                          
	            String senddata = Lp.extractXml(rs,xmldata2);           
	            String recievedata = Lp.extractXml(rs,xmldata1);      
	          	           
	            String intrado_HouseNum= Lp.nodeFromKey(senddata,"<intrado:HouseNum>","</intrado:HouseNum>");
 	            Application.showMessage("intrado:HouseNum is ::"+intrado_HouseNum); 
 	           System.out.println("intrado:HouseNum is ::"+intrado_HouseNum); 
	            if(intrado_HouseNum.equals(c.getValue("HouseNumber")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	System.out.println("Recieve XML is::  \n"+ recievedata);
	            	System.out.println("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-responderInfo_Validate********");
            		Application.showMessage("Validation is Successfull with House Number :"+intrado_HouseNum);
            		System.out.println("*******Validation Point 1 :: WebServicall-responderInfo_Validate********");
            		System.out.println("Validation is Successfull with House Number :"+intrado_HouseNum);
            		
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	 	            

	 	            String intrado_PrefixDirectional= Lp.nodeFromKey(senddata,"<intrado:PrefixDirectional>","</intrado:PrefixDirectional>").trim();
	 	            Application.showMessage("intrado:PrefixDirectional is ::"+intrado_PrefixDirectional);
	 	           System.out.println("intrado:PrefixDirectional is ::"+intrado_PrefixDirectional);

	 	            String intrado_StreetName= Lp.nodeFromKey(senddata,"<intrado:StreetName>","</intrado:StreetName>").trim();
	 	            Application.showMessage("intrado:StreetName is ::"+intrado_StreetName); 
	 	           System.out.println("intrado:StreetName is ::"+intrado_StreetName); 
	 	           if(intrado_StreetName==null)
		            {
			            c.report("Send Xml intrado_StreetName is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("intrado_StreetName from Send Xml is ::"+" "+intrado_StreetName);
		            	 System.out.println("intrado_StreetName from Send Xml is ::"+" "+intrado_StreetName);
		            	 if(intrado_StreetName.equalsIgnoreCase(c.getValue("StreetName")))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-intrado_StreetName********");
			            	 Application.showMessage("Validation is Successfull with intrado_StreetName::"+" "+intrado_StreetName);
			            	 System.out.println("*******Validation Point 2 :: WebServicall-intrado_StreetName********");
			            	 System.out.println("Validation is Successfull with intrado_StreetName::"+" "+intrado_StreetName);
			            	
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("intrado_StreetName at Send Xml not Validated as "+intrado_StreetName);
			             }
		            }	
	 	            

	 	            String intrado_StreetSuffix= Lp.nodeFromKey(senddata,"<intrado:StreetSuffix>","</intrado:StreetSuffix>").trim();
	 	            Application.showMessage("intrado:StreetSuffix is ::"+intrado_StreetSuffix); 
	 	           System.out.println("intrado:StreetSuffix is ::"+intrado_StreetSuffix); 
	 	            
	 	            String intrado_UnitType= Lp.nodeFromKey(senddata,"<intrado:UnitType>","</intrado:UnitType>").trim();
	 	            Application.showMessage("intrado:UnitType is ::"+intrado_UnitType); 
	 	           System.out.println("intrado:UnitType is ::"+intrado_UnitType); 
	 	            
	 	            String intrado_PostalCommunity= Lp.nodeFromKey(senddata,"<intrado:PostalCommunity>","</intrado:PostalCommunity>").trim();
	 	            Application.showMessage("intrado:PostalCommunity is ::"+intrado_PostalCommunity); 
	 	           System.out.println("intrado:PostalCommunity is ::"+intrado_PostalCommunity); 
	 	            
	 	            String intrado_StateProvince= Lp.nodeFromKey(senddata,"<intrado:StateProvince>","</intrado:StateProvince>").trim();
	 	            Application.showMessage("intrado:StateProvince is ::"+intrado_StateProvince); 
	 	           System.out.println("intrado:StateProvince is ::"+intrado_StateProvince); 
	 	            
	 	            String intrado_PostalZipCode= Lp.nodeFromKey(senddata,"<intrado:PostalZipCode>","</intrado:PostalZipCode>").trim();
	 	            Application.showMessage("intrado:PostalZipCode is ::"+intrado_PostalZipCode); 
	 	           System.out.println("intrado:PostalZipCode is ::"+intrado_PostalZipCode); 
	 	            if(intrado_PostalZipCode==null)
		            {
			            c.report("Send Xml intrado_PostalZipCode is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("PostalCode from Send Xml   is ::"+" "+intrado_PostalZipCode);
		            	 System.out.println("PostalCode from Send Xml   is ::"+" "+intrado_PostalZipCode);
		            	 if(intrado_PostalZipCode.equals(c.get("QpostalCode").toString().trim()))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-intrado_PostalZipCode********");
			            	 Application.showMessage("Validation is Successfull with intrado_PostalZipCode::"+" "+intrado_PostalZipCode);
			            	 System.out.println("*******Validation Point 3 :: WebServicall-intrado_PostalZipCode********");
			            	 System.out.println("Validation is Successfull with intrado_PostalZipCode::"+" "+intrado_PostalZipCode);
			            	
			            	 v3=1;
			             }
			             else
			             {
			            	 c.report("intrado_PostalZipCode at Send Xml not Validated as "+intrado_PostalZipCode);
			             }
		            }	
	 	            
	 	            
	 	            
	 	             	         	 	            
	 	            String sch_overallMatch= Lp.nodeFromKey(recievedata,"<sch:overallMatch>","</sch:overallMatch>");
	 	            Application.showMessage("sch:overallMatch is::"+sch_overallMatch);
	 	           System.out.println("sch:overallMatch is::"+sch_overallMatch);
	 	            
	 	            if(sch_overallMatch==null)
		             {
			            c.report("Send Xml sch_overallMatch is NULL");
			            continue;
		             }
		            else
		            {
		            	 if(sch_overallMatch.equalsIgnoreCase("Success"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
			            	 Application.showMessage("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
			            	 System.out.println("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
			            	 System.out.println("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
			            	 
			            	 v4=1;
			             }
		            	 else if(sch_overallMatch.equalsIgnoreCase("Altered"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
			            	 Application.showMessage("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
			            	 System.out.println("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
			            	 System.out.println("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
			            	 
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("sch_overallMatch at Send Xml not Validated as "+sch_overallMatch);
			             }
		            }
	 	            
	 	            
	 	            String sch_PoliceContactInfo= Lp.nodeFromKey(recievedata,"<sch:PoliceContactInfo>","</sch:PoliceContactInfo>");
	 	            Application.showMessage("sch:PoliceContactInfo is::"+sch_PoliceContactInfo);
	 	           System.out.println("sch:PoliceContactInfo is::"+sch_PoliceContactInfo);
	 	            
	 	           if(sch_PoliceContactInfo==null)
		            {
			            c.report("Send Xml sch_PoliceContactInfo is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("sch_PoliceContactInfo from Send Xml   is ::"+" "+sch_PoliceContactInfo);
		            	 System.out.println("sch_PoliceContactInfo from Send Xml   is ::"+" "+sch_PoliceContactInfo);
			            	
		            	 if(sch_PoliceContactInfo.equals(c.getValue("PoliceNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-sch_PoliceContactInfo********");
			            	 Application.showMessage("Validation is Successfull with sch_PoliceContactInfo::"+" "+sch_PoliceContactInfo);
			            	 System.out.println("*******Validation Point 5 :: WebServicall-sch_PoliceContactInfo********");
			            	 System.out.println("Validation is Successfull with sch_PoliceContactInfo::"+" "+sch_PoliceContactInfo);
			            	
			            	 v5=1;
			             }
			             else
			             {
			            	 c.report("sch_PoliceContactInfo at Send Xml not Validated as "+sch_PoliceContactInfo);
			             }
		            }	
	 	            
	 	            
	 	            
	 	            String sch_FireContactInfo= Lp.nodeFromKey(recievedata,"<sch:FireContactInfo>","</sch:FireContactInfo>");
	 	            Application.showMessage("sch:FireContactInfo is::"+sch_FireContactInfo);
	 	           System.out.println("sch:FireContactInfo is::"+sch_FireContactInfo);
	 	            
	 	           if(sch_FireContactInfo==null)
		            {
			            c.report("Send Xml sch_FireContactInfo is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("sch_FireContactInfo from Send Xml   is ::"+" "+sch_FireContactInfo);
		            	 System.out.println("sch_FireContactInfo from Send Xml   is ::"+" "+sch_FireContactInfo);
		            	 
		            	 if(sch_FireContactInfo.equals(c.getValue("FireNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-sch_FireContactInfo********");
			            	 Application.showMessage("Validation is Successfull with sch_FireContactInfo::"+" "+sch_FireContactInfo);
			            	 System.out.println("*******Validation Point 6 :: WebServicall-sch_FireContactInfo********");
			            	 System.out.println("Validation is Successfull with sch_FireContactInfo::"+" "+sch_FireContactInfo);
			            	
			            	 v6=1;
			             }
			             else
			             {
			            	 c.report("sch_FireContactInfo at Send Xml not Validated as "+sch_FireContactInfo);
			             }
		            }	
	 	            
	 	            
	 	            
	 	            String sch_MedicalContactInfo= Lp.nodeFromKey(recievedata,"<sch:MedicalContactInfo>","</sch:MedicalContactInfo>");
	 	            Application.showMessage("sch:MedicalContactInfo is::"+sch_MedicalContactInfo);
	 	           System.out.println("sch:MedicalContactInfo is::"+sch_MedicalContactInfo);
	 	            
	 	           if(sch_MedicalContactInfo==null)
		            {
			            c.report("Send Xml sch_MedicalContactInfo is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("sch_MedicalContactInfo from Send Xml   is ::"+" "+sch_MedicalContactInfo);
		            	 System.out.println("sch_MedicalContactInfo from Send Xml   is ::"+" "+sch_MedicalContactInfo);
		            	 
		            	 if(sch_MedicalContactInfo.equals(c.getValue("medicalNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 7 :: WebServicall-intrado_PostalZipCode********");
			            	 Application.showMessage("Validation is Successfull with intrado_PostalZipCode::"+" "+sch_MedicalContactInfo);
			            	 System.out.println("*******Validation Point 7 :: WebServicall-intrado_PostalZipCode********");
			            	 System.out.println("Validation is Successfull with intrado_PostalZipCode::"+" "+sch_MedicalContactInfo);
			            	 
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
         if(callFound*v2*v3*v4*v5*v6*v7 ==1)
         {
         	Application.showMessage("WebService Call :: Intrado is Success[All validation points are Success]");
         	System.out.println("WebService Call :: Intrado is Success[All validation points are Success]");
            
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::Intrado is Failed with Validation Errors");
         }
    //     st.close();
	}	
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }






public String SetAccountAuthorityInformation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
    
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
    // c.setValue("setAccountAuthorityInfo","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********SetAccountAuthorityInformation Function***********");       
     Application.showMessage("-------------------------------------------------------------");
     System.out.println("-------------------------------------------------------------");
     System.out.println("***********SetAccountAuthorityInformation Function***********");       
     System.out.println("-------------------------------------------------------------");
   //  Lp.findThinktimeOperationRTP(input, c);
   //  c.setValue("setAccountAuthorityInfo","false"); 
     HashMap Operation=Lp.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SetAccountAuthorityInformation"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SetAccountAuthorityInformation"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("SetAccountAuthorityInformation"));


	try
	{
		// Statement st =Lp. dBConnect(input, c);	
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'COPSServices:Comcast/SetAccountAuthorityInformation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=Lp.reduceThinkTimeRTP(input, c);
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
            	
            	String senddata =Lp.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            	System.out.println("Your Recieve XML is::\n"+senddata);
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");	
            	System.out.println("Your SEND XML is NULL \n");	
            	String recievedata=Lp.extractXml(rs,xmldata1);
            	System.out.println("RECIEVE XML is ::\n"+recievedata);   
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);   
            }
            else
            { 
                          
	            String senddata = Lp.extractXml(rs,xmldata2);           
	            String recievedata = Lp.extractXml(rs,xmldata1);      
	          	           
	            String accountNumber_setAccountAuthority= Lp.nodeFromKey(senddata,"<accountNumber>","</accountNumber>");
 	            Application.showMessage("accountNumber is ::"+accountNumber_setAccountAuthority);
 	           System.out.println("accountNumber is ::"+accountNumber_setAccountAuthority); 
	            if(accountNumber_setAccountAuthority.equals(c.getValue("CcentralNum")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
	            	System.out.println("*******Validation Point 1 :: WebServicall-SetAccountAuthorityInformation********");
	            	System.out.println("Validation is Successfull with House Number :"+accountNumber_setAccountAuthority);
	            	System.out.println("*******Validation Point 1 :: WebServicall-SetAccountAuthorityInformation********");
	            	System.out.println("Validation is Successfull with House Number :"+accountNumber_setAccountAuthority);
            		
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	 	            

	 	            String pc_setAccountAuth= Lp.nodeFromKey(senddata,"<pc>","</pc>");
	 	            Application.showMessage("pc is ::"+pc_setAccountAuth); 
	 	           System.out.println("pc is ::"+pc_setAccountAuth); 
	 	            
	 	           if(pc_setAccountAuth==null)
		            {
			            c.report(" pc is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("pc from Send Xml  from SetAccountAuthorityInformation is ::"+" "+pc_setAccountAuth);
		            	 System.out.println("pc from Send Xml  from SetAccountAuthorityInformation is ::"+" "+pc_setAccountAuth);
			            	
		            	 if(pc_setAccountAuth.equalsIgnoreCase("C9O2P5"))
			             {
			            	 Application.showMessage("*******Validation Point 2:: WebServicall-pc********");
			            	 Application.showMessage("Validation is Successfull with pc::"+" "+pc_setAccountAuth);
			            	 System.out.println("*******Validation Point 2:: WebServicall-pc********");
			            	 System.out.println("Validation is Successfull with pc::"+" "+pc_setAccountAuth);
			            	
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("pc at Send Xml not Validated as "+pc_setAccountAuth);
			             }
		            }

	 	            String dn_setAccountAuth= Lp.nodeFromKey(senddata,"<dn>","</dn>");
	 	            Application.showMessage("dn is ::"+dn_setAccountAuth); 	
	 	           System.out.println("dn is ::"+dn_setAccountAuth); 	
	 	           if(dn_setAccountAuth==null)
		            {
			            c.report(" dn is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("dn from Send Xml  from SetAccountAuthorityInformation is ::"+" "+dn_setAccountAuth);
		            	 System.out.println("dn from Send Xml  from SetAccountAuthorityInformation is ::"+" "+dn_setAccountAuth);
		            	 
		            	 if(dn_setAccountAuth.equalsIgnoreCase("COMC"))
			             {
			            	 Application.showMessage("*******Validation Point 3:: WebServicall-dn********");
			            	 Application.showMessage("Validation is Successfull with dn::"+" "+dn_setAccountAuth);
			            	 System.out.println("*******Validation Point 3:: WebServicall-dn********");
			            	 System.out.println("Validation is Successfull with dn::"+" "+dn_setAccountAuth);
			            	
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("dn at Send Xml not Validated as "+dn_setAccountAuth);
			             }
		            }

	 	            
	 	            String readBack_setAccountAuth= Lp.nodeFromKey(recievedata,"<readBack>","</readBack>");
	 	            Application.showMessage("readBack is::"+readBack_setAccountAuth);
	 	           System.out.println("readBack is::"+readBack_setAccountAuth);
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
			            	 System.out.println("*******Validation Point 4:: WebServicall-readBack********");
			            	 System.out.println("Validation is Successfull with dn::"+" "+readBack_setAccountAuth);
			            	 
			            	 v3=1;
			             }
			             else
			             {
			            	 c.report("readBack at Send Xml not Validated as "+readBack_setAccountAuth);
			             }
		            }
	 	            
	 	            
	 	            String result_setAccountAuth= Lp.nodeFromKey(recievedata,"<result>","</result>");
	 	            Application.showMessage("result is::"+result_setAccountAuth);
	 	           System.out.println("result is::"+result_setAccountAuth);
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
			            	 System.out.println("*******Validation Point 5:: WebServicall-result_setAccountAuth********");
			            	 System.out.println("Validation is Successfull with result_setAccountAuth::"+" "+result_setAccountAuth);
			            	 
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
         	Application.showMessage("WebService Call :: Intrado is Success[All validation points are Success]");
         	System.out.println("WebService Call :: Intrado is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::Intrado is Failed with Validation Errors");
         }
        // st.close();
	}
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }


//till here

public String modifyHomeSecurity_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	 // Think time in JVM [waits for 10 secs here]
    
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
    // c.setValue("modifyHomesecurity","true");
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********modifyHomeSecurity_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
   //  Lp.findThinktimeOperationRTP(input, c);
   //  c.setValue("modifyHomesecurity","false");
     HashMap Operation=Lp.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("modifyHomeSecurity"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("modifyHomeSecurity"));

	try
	{
		 //Statement st =Lp. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/modifyHomeSecurity' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=Lp.reduceThinkTimeRTP(input, c);
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
            	
            	String senddata =Lp.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");	
            	String recievedata=Lp.extractXml(rs,xmldata1);
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
            }
            else
            { 
                          
	            String senddata = Lp.extractXml(rs,xmldata2);           
	            String recievedata = Lp.extractXml(rs,xmldata1);      
	          	           
	            String legacyID_modifyHS= Lp.nodeFromKey(senddata,"<lt:legacyID>","</lt:legacyID>");
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
	      
	 	            

	 	            String lt_policeNumber= Lp.nodeFromKey(senddata,"<lt:policeNumber>","</lt:policeNumber>");
	 	            Application.showMessage("lt:policeNumber is ::"+lt_policeNumber); 
	 	            
	 	           if(lt_policeNumber==null)
		            {
			            c.report("Send Xml lt_policeNumber is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("lt_policeNumber from Send Xml   is ::"+" "+lt_policeNumber);
		            	 if(lt_policeNumber.equals(c.getValue("PoliceNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-lt_policeNumber********");
			            	 Application.showMessage("Validation is Successfull with lt_policeNumber::"+" "+lt_policeNumber);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("lt_policeNumber at Send Xml not Validated as "+lt_policeNumber);
			             }
		            }
	 	            
	 	            

	 	            String lt_fireNumber= Lp.nodeFromKey(senddata,"<lt:fireNumber>","</lt:fireNumber>");
	 	            Application.showMessage("lt:fireNumber is ::"+lt_fireNumber); 
	 	            
	 	           if(lt_fireNumber==null)
		            {
			            c.report("Send Xml lt_fireNumber is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("lt_fireNumber from Send Xml   is ::"+" "+lt_fireNumber);
		            	 if(lt_fireNumber.equals(c.getValue("FireNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lt_fireNumber********");
			            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+lt_fireNumber);
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("lt_fireNumber at Send Xml not Validated as "+lt_fireNumber);
			             }
		            }	
	 	            
	 	            String lt_medicalNumber= Lp.nodeFromKey(senddata,"<lt:medicalNumber>","</lt:medicalNumber>");
	 	            Application.showMessage("lt:medicalNumber is ::"+lt_medicalNumber); 
	 	            
	 	           if(lt_medicalNumber==null)
		            {
			            c.report("Send Xml sch_MedicalContactInfo is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("sch_MedicalContactInfo from Send Xml   is ::"+" "+lt_medicalNumber);
		            	 if(lt_medicalNumber.equals(c.getValue("medicalNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-lt_medicalNumber********");
			            	 Application.showMessage("Validation is Successfull with lt_medicalNumber::"+" "+lt_medicalNumber);
			            	 v3=1;
			             }
			             else
			             {
			            	 c.report("sch_MedicalContactInfo at Send Xml not Validated as "+lt_medicalNumber);
			             }
		            }	
	 	                     	         	 	           	            
	 	            String typ_ReturnCode= Lp.nodeFromKey(recievedata,"<typ:ReturnCode xmlns:typ=\"http://xml.comcast.com/location/types\">","</typ:ReturnCode>");
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
     //    st.close();
	}
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }


//till here
public String orderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	 // Think time in JVM [waits for 10 secs here]
     
	 ResultSet  rs;
	 String vService=null;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     String rscallpresent="true";
   //  c.setValue("OrderUpdate","true");
     HandleInvalidServices hI=new HandleInvalidServices();
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********OrderUpdate_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
     System.out.println("-------------------------------------------------------------");
     System.out.println("***********OrderUpdate_Validate Function**************");       
     System.out.println("-------------------------------------------------------------");
    // Lp.findThinktimeOperationRTP(input, c);
    // c.setValue("OrderUpdate","false");
     HashMap Operation=Lp.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
     System.out.println("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
	try
	{
	//	Statement st =Lp. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=Lp.reduceThinkTimeRTP(input, c);
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
            	
            	String senddata = Lp.extractXml(rs,xmldata2);           
	           
	          	           
                String accountNumber_ou= Lp.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
 	            Application.showMessage("accountNumber is ::"+accountNumber_ou); 
 	           System.out.println("accountNumber is ::"+accountNumber_ou); 
 	           if(accountNumber_ou==null)
	            {
 	        	   Application.showMessage("Send Xml billingOrderId is NULL");
 	        	  System.out.println("Send Xml billingOrderId is NULL");
		            continue;
	            }
 	           else if(accountNumber_ou.equals(c.getValue("ACC_input")))
            	{
	            	
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
            		Application.showMessage("Validation is Successfull with House Number :"+accountNumber_ou);
            		System.out.println("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		System.out.println("*******Validation Point 1 :: WebServicall-Order Update********");
            		System.out.println("Validation is Successfull with House Number :"+accountNumber_ou);
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
	            
	            if(callFound==1)
	            {
	      
	 	            

	 	            String vomInstance= Lp.nodeFromKey(senddata,"<value name=\"vomInstance\">","</value><value name=\"ordSource\">");
	 	            Application.showMessage("vomInstance is ::"+vomInstance); 
	 	           System.out.println("vomInstance is ::"+vomInstance); 

	 	            
	 	            
	 	            if(c.getValue("IsDemi").equalsIgnoreCase("true"))
	 	            {
	 	            	
	 	            String ordSource= Lp.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
		 	        Application.showMessage("ordSource is ::"+ordSource); 
		 	       System.out.println("ordSource is ::"+ordSource); 
	 	            String billingOrderId= Lp.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
	 	            Application.showMessage("billingOrderId is ::"+billingOrderId); 
	 	           System.out.println("billingOrderId is ::"+billingOrderId); 
	 	            
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
				            	 System.out.println("*******Validation Point 2 :: WebServicall-billingOrderId********");
				            	 System.out.println("Validation is Successfull with billingOrderId::"+" "+billingOrderId);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("billingOrderId at Send Xml not Validated as "+billingOrderId);
				             }
			            }
		 	           
		 	            String Service= Lp.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
		 	            Application.showMessage("Service is ::"+Service);
		 	           System.out.println("Service is ::"+Service);
		 	            //need update
		 	           if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ScenarioType").toString().equals(null))
		 	           {
		 	        	  vService =c.getValue("sSERVICEorCURRENTSERVICE").trim();
		 	        	 Application.showMessage("The service is to be Validated..."+vService);
		 	        	System.out.println("The service is to be Validated..."+vService);
		 	           }
		 	           else if(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: ScenarioType").toString().equalsIgnoreCase("Handle Invalid service"))
		 	           {
		 	        	  vService = hI.SetServiceIdentificationRule(input, c);
		   		    	  Application.showMessage("The service is to be Validated..."+vService);  
		   		    	System.out.println("The service is to be Validated..."+vService);  
		 	           }
		 	           else
		 	           {
		 	        	  vService =c.getValue("sSERVICEorCURRENTSERVICE").trim();
		 	        	 Application.showMessage("The service is to be Validated..."+vService);
		 	        	System.out.println("The service is to be Validated..."+vService);
		 	           }
		 	           if(Service==null)
			            {
				            c.report("Send Xml Service is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
			            	 System.out.println("Service from Send Xml   is ::"+" "+Service);
			            	 if(Service.equals(vService))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
				            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
				            	 System.out.println("*******Validation Point 5 :: WebServicall-Service********");
				            	 System.out.println("Validation is Successfull with Service::"+" "+Service);
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
	 	            	
	 	            	
	 	                String Service= Lp.nodeFromKey(senddata,"Service = ","</note><description>").trim();
	 	 	            Application.showMessage("Service is ::"+Service); 
	 	 	          System.out.println("Service is ::"+Service); 
	 	 	            
	 	 	           if(Service==null)
	 		            {
	 			            c.report("Send Xml Service is NULL");
	 			            continue;
	 		            }
	 		            else
	 		            {
	 		            	
	 		            	 Application.showMessage("Service from Input   is ::"+" "+c.getValue("sSERVICEorCURRENTSERVICE"));
	 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
	 		            	System.out.println("Service from Input   is ::"+" "+c.getValue("sSERVICEorCURRENTSERVICE"));
	 		            	System.out.println("Service from Send Xml   is ::"+" "+Service);
	 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
	 			             {
	 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
	 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
	 			            	System.out.println("*******Validation Point 5 :: WebServicall-Service********");
	 			            	System.out.println("Validation is Successfull with Service::"+" "+Service);
	 			            	 v4=1;
	 			            	 c.setValue("Product",Service);
	 			             }
	 			             else
	 			             {
	 			            	 c.report("Service at Send Xml not Validated as "+Service);
	 			             }
	 		            }	
	 	            }
	 	            String inputChannel= Lp.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
	 	            Application.showMessage("inputChannel is ::"+inputChannel);
	 	           System.out.println("inputChannel is ::"+inputChannel);
	 	            
	 	            String ordType= Lp.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
	 	            Application.showMessage("ordType is ::"+ordType);
	 	           System.out.println("ordType is ::"+ordType);
	 	            
	 	           if(ordType==null)
		            {
			            c.report("Send Xml ordType is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
		            	 System.out.println("ordType from Send Xml   is ::"+" "+ordType);
		            	 if(ordType.equals("NEW"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
			            	 System.out.println("*******Validation Point 3 :: WebServicall-ordType********");
			            	 System.out.println("Validation is Successfull with lt_fireNumber::"+" "+ordType);
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("lt_fireNumber at Send Xml not Validated as "+ordType);
			             }
		            }	
	 	           
	 	           
	 	            String ordStatus= Lp.nodeFromKey(senddata,"</value><value name=\"ordStatus\">","</value><value name=\"customerType\">");
	 	            Application.showMessage("ordType is ::"+ordType);
	 	           System.out.println("ordType is ::"+ordType);
	 	            
	 	            if(ordStatus==null)
		            {
			            c.report("Send Xml ordStatus is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("ordStatus from Send Xml   is ::"+" "+ordStatus);
		            	 System.out.println("ordStatus from Send Xml   is ::"+" "+ordStatus);
		            	 if(ordStatus.equals("COM"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
			            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
			            	 System.out.println("*******Validation Point 3 :: WebServicall-ordStatus********");
			            	 System.out.println("Validation is Successfull with ordStatus::"+" "+ordStatus);
			            	 
			             }
		            	 else if(ordStatus.equals("ERR"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
			            	 System.out.println("*******Validation Point 3 :: WebServicall-ordStatus********");
			            	 c.report("Validation with ERROR ordStatus:: "+ordStatus);
			            	 
			             }
			             else
			             {
			            	 c.report("ordStatus at Send Xml not Validated as "+ordStatus);
			             }
		            }	
	 	            
	 	            
	 	            String customerType= Lp.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
	 	            Application.showMessage("customerType is ::"+customerType); 
	 	           System.out.println("customerType is ::"+customerType); 
	 	            
	 	           if(customerType==null)
		            {
			            c.report("Send Xml customerType is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("customerType from Send Xml   is ::"+" "+customerType);
		            	 System.out.println("customerType from Send Xml   is ::"+" "+customerType);
		            	 if(customerType.equals("RES"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
			            	 Application.showMessage("Validation is Successfull with customerType::"+" "+customerType);
			            	 System.out.println("*******Validation Point 4 :: WebServicall-customerType********");
			            	 System.out.println("Validation is Successfull with customerType::"+" "+customerType);
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
         	System.out.println("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
         //	c.setValue("IsDemi", "false");
         }
         else
         {
        	// c.setValue("IsDemi", "false");
        	// c.put("result", "false");
        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
         }
   //      st.close();
         
        //Lp.reportingToExcel(Object , ScriptingContext);
	}	
	}
	catch (SQLException e)
	{
	    System.out.println("Connection Failed! Check output console");
		e.printStackTrace();
		
	}
	return rscallpresent;
 }
public void DisorderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     
	 ResultSet  rs;
	 String vService=null;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	// String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     HandleInvalidServices hI=new HandleInvalidServices();
     Application.showMessage("-------------------------------------------------------------");
     Application.showMessage("***********DISOrderUpdate_Validate Function**************");       
     Application.showMessage("-------------------------------------------------------------");
         
	try
	{
		Statement st =Lp. dBConnect(input, c);	
         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
            if(rs.getBlob("receive_data")==null)
            {
            
            	Application.showMessage("Your Recieve XML is NULL \n");
            	
            	String senddata = Lp.extractXml(rs,xmldata2);           
	           
	          	           
                String accountNumber_ou= Lp.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
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
	      
	 	            

	 	            String vomInstance= Lp.nodeFromKey(senddata,"<value name=\"vomInstance\">","</value><value name=\"ordSource\">");
	 	            Application.showMessage("vomInstance is ::"+vomInstance); 

	 	            
	 	            
	 	            if(c.getValue("IsDemi").equalsIgnoreCase("true"))
	 	            {
	 	            	
	 	            String ordSource= Lp.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
		 	        Application.showMessage("ordSource is ::"+ordSource); 
	 	            String billingOrderId= Lp.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
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
		 	           
		 	            String Service= Lp.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
		 	            Application.showMessage("Service is ::"+Service);
		 	            //need update
		 	           if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: ScenarioType").toString().equals(null))
		 	           {
		 	        	  vService =c.getValue("sSERVICEorCURRENTSERVICE").trim();
		 	        	 Application.showMessage("The service is to be Validated..."+vService);
		 	           }
		 	           else if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: ScenarioType").toString().equalsIgnoreCase("Handle Invalid service"))
		 	           {
		 	        	  vService = hI.SetServiceIdentificationRule(input, c);
		   		    	  Application.showMessage("The service is to be Validated..."+vService);  
		 	           }
		 	           else
		 	           {
		 	        	  vService =c.getValue("sSERVICEorCURRENTSERVICE").trim();
		 	        	 Application.showMessage("The service is to be Validated..."+vService);
		 	           }
		 	           if(Service==null)
			            {
				            c.report("Send Xml Service is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
			            	 if(Service.equals(vService))
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
	 	            	
	 	            	
	 	                String Service= Lp.nodeFromKey(senddata,"Service = ","</note><description>").trim();
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
	 	            String inputChannel= Lp.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
	 	            Application.showMessage("inputChannel is ::"+inputChannel);
	 	            
	 	            String ordType= Lp.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
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
	 	           
	 	           
	 	            String ordStatus= Lp.nodeFromKey(senddata,"</value><value name=\"ordStatus\">","</value><value name=\"customerType\">");
	 	            Application.showMessage("ordType is ::"+ordType);
	 	            
	 	            if(ordStatus==null)
		            {
			            c.report("Send Xml ordStatus is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("ordStatus from Send Xml   is ::"+" "+ordStatus);
		            	 if(ordStatus.equals("COM"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
			            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
			            	 
			             }
		            	 else if(ordStatus.equals("ERR"))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
			            	 c.report("Validation with ERROR ordStatus:: "+ordStatus);
			            	 
			             }
			             else
			             {
			            	 c.report("ordStatus at Send Xml not Validated as "+ordStatus);
			             }
		            }	
	 	            
	 	            
	 	            String customerType= Lp.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
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

public void createAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
    
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
    
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("***********createAccount_Validate Function***********");       
     Application.showMessage("----------------------------------------------------");
         
	try
	{
		 Statement st =Lp. dBConnect(input, c);	
         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
           String rowmsg=rs.getString(1);
            if(rs.getBlob("receive_data")==null)
            {
            
            	Application.showMessage("Your Recieve XML is NULL \n");
            	
            	String senddata =Lp.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");	
            	String recievedata=Lp.extractXml(rs,xmldata1);
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
            }
            else
            { 
                          
	            String senddata = Lp.extractXml(rs,xmldata2);           
	            String recievedata = Lp.extractXml(rs,xmldata1);      
	          	           
	            String accountId_ucontrolsrv = Lp.nodeFromKey(senddata,"<ucontrolsrv:accountId>","</ucontrolsrv:accountId>");
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
	      
	 	            String status_ucontrolsrv= Lp.nodeFromKey(senddata,"<ucontrolsrv:status>","</ucontrolsrv:status>");
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

	 	            String firstName_ucontrolsrv= Lp.nodeFromKey(senddata,"<ucontrolsrv:firstName>","</ucontrolsrv:firstName>");
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

	 	            String lastName_ucontrolsrv= Lp.nodeFromKey(senddata,"<ucontrolsrv:lastName>","</ucontrolsrv:lastName>");
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

	 	            String ucontrolsrv_phoneNumber= Lp.nodeFromKey(senddata,"<ucontrolsrv:phoneNumber>","</ucontrolsrv:phoneNumber>");
	 	            Application.showMessage("ucontrolsrv:phoneNumber is ::"+ucontrolsrv_phoneNumber); 
	 	            c.setValue("phoneNumber", ucontrolsrv_phoneNumber);
	 	            
	 	            String ucontrolsrv_emailAddress= Lp.nodeFromKey(senddata,"<ucontrolsrv:emailAddress>","</ucontrolsrv:emailAddress>");
	 	            Application.showMessage("ucontrolsrv:emailAddress is ::"+ucontrolsrv_emailAddress); 
	 	          
	 	            if(ucontrolsrv_emailAddress==null)
		            {
			            c.report("Send Xml emailAddress is NULL");
			            
		            }
		            else
		            {
		            	
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
	 	            
	 	            String ucontrolsrv_address1= Lp.nodeFromKey(senddata,"<ucontrolsrv:address1>","</ucontrolsrv:address1>");
	 	            Application.showMessage("ucontrolsrv:address1 is ::"+ucontrolsrv_address1); 
	 	            c.setValue("Address", ucontrolsrv_address1);
	 	           
	 	            String ucontrolsrv_city= Lp.nodeFromKey(senddata,"<ucontrolsrv:city>","</ucontrolsrv:city>");
	 	            Application.showMessage("City is ::"+ucontrolsrv_city); 
	 	            
	 	           if(ucontrolsrv_city==null)
		            {
			            c.report("Send Xml City is NULL");
			          
		            }
		            else
		            {
		            	 Application.showMessage("City from Send Xml  from ucontrolsrv_city is ::"+" "+ucontrolsrv_city);
		            	 if(ucontrolsrv_city.equalsIgnoreCase(c.get("Ecity").toString().trim()))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
			            	 Application.showMessage("Validation is Successfull with City::"+" "+ucontrolsrv_city);
			            	 v5=1;
			             }
			             else if(ucontrolsrv_city.equalsIgnoreCase(c.get("city").toString().trim()))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
			            	 Application.showMessage("Validation is Successfull with City::"+" "+ucontrolsrv_city);
			            	 v5=1;
			            	 
			             }
		            	 
			             else
			             {
			            	 c.report("City at Send Xml not Validated as "+ucontrolsrv_city); 
			             }
		            }	
	 	            
	 	            String ucontrolsrv_province= Lp.nodeFromKey(senddata,"<ucontrolsrv:province>","</ucontrolsrv:province>");
	 	            Application.showMessage("ucontrolsrv:province is ::"+ucontrolsrv_province); 
	 	            
	 	            String ucontrolsrv_postalCode= Lp.nodeFromKey(senddata,"<ucontrolsrv:postalCode>","</ucontrolsrv:postalCode>");
	 	            Application.showMessage("ucontrolsrv:postalCode is ::"+ucontrolsrv_postalCode); 
	 	            
	 	            c.setValue("PostalCode", ucontrolsrv_postalCode);
	 	            
	 	            String ucontrolsrv_portalUserSSOId= Lp.nodeFromKey(senddata,"<ucontrolsrv:portalUserSSOId>","</ucontrolsrv:portalUserSSOId>");
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
	 	          /* String CreateAcc_Services=c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE");
	 	           String Create_ValidServices=SetServiceIdentificationRule(CreateAcc_Services,input,c);*/
	 	            
	 	            if(c.getValue("IsMultiExist").equals("true"))
	 	            {
	 	            	if(c.getValue("IsDemi").equalsIgnoreCase("true"))
	 	            	{
		 	            	String ucontrolsrv_group1= Lp.nodeFromKey(senddata,"</ucontrolsrv:portalUserSSOId><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
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
	 	            	else
	 	            	{
	 	            	
	 	            		String ucontrolsrv_group1= Lp.nodeFromKey(senddata,"</ucontrolsrv:product><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
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
		 	           
		 	            String ucontrolsrv_group2= Lp.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group></account>");
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
	 	            
		 	            String ucontrolsrv_group= Lp.nodeFromKey(senddata,"<ucontrolsrv:group>","</ucontrolsrv:group>");
		 	            Application.showMessage("ucontrolsrv:group is ::"+ucontrolsrv_group); 
		 	            
		 	           if(ucontrolsrv_group==null)
			            {
				            c.report("Send Xml ucontrolsrv_group is NULL");
				          
			            }
			            else
			            {
			            	 Application.showMessage("ucontrolsrv_group from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_group);
			            	 if(ucontrolsrv_group.equals(c.getValue("ExistingGroup")))//sSERVICEorCURRENTSERVICE
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
	 	             	         	 	            
	 	            String errorCode_createAcc= Lp.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
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
	 	            
	 	            
	 	            String accountId_createAcc= Lp.nodeFromKey(recievedata,"<accountId xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</accountId>");
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
public String rtpTestInterface_Validate_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
{
		
		 ResultSet  rs;
		 int callFound=0, v1=0;
		 String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";  
         Thread.sleep(5000);
         String rscallpresent="true";
         String Time= c.get("BaseTime").toString();
      //   c.setValue("RTPTestInterface","true");
         Application.showMessage("----------------------------------------------------------");
         Application.showMessage("************rtpTestInterface_Validate Validate Function************");    
         Application.showMessage("----------------------------------------------------------");
         System.out.println("----------------------------------------------------------");
         System.out.println("************rtpTestInterface_Validate Validate Function************");    
         System.out.println("----------------------------------------------------------");
       //  Lp.findThinktimeOperationRTP(input, c);
       //  c.setValue("RTPTestInterface","false");
         HashMap Operation=Lp.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("xhsevent"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("xhsevent"));
	     System.out.println("OPERATIONValidation is "+(String) Operation.get("xhsevent"));

		try
		{
			
			//Lp.Opconnection();
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1", "CWDEVOP","CWDEVOP");		
		//	Statement st =Lp. dBConnect(input, c);	 
			
	     //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'eep:XHSEEPOrder/xhsevent' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >= '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=Lp.reduceThinkTimeRTP(input, c);
			if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{
	        while (rs.next())
	        {
	              Application.showMessage("RTPTestInterface msgid= "+ rs.getString(1));
	              System.out.println("RTPTestInterface msgid= "+ rs.getString(1));
	              rowmsg=rs.getString(1);	          
	              if(rs.getBlob("receive_data")==null)
	              {
	           
	            	String senddata =Lp.extractXml(rs,xmldata2);	           
		            String accountNumber_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Account/@accountNumber");
		            Application.showMessage("accountNumber is ::"+accountNumber_rtpTest); 
		           	Application.showMessage("Send Data is::"+senddata);	
		           	System.out.println("accountNumber is ::"+accountNumber_rtpTest); 
		           	System.out.println("Send Data is::"+senddata);	
		            if(accountNumber_rtpTest==null)
		            {
			            
			            Application.showMessage("accountNumber ID is NULL \n");
			            System.out.println("accountNumber ID is NULL \n");

			            continue;
		            }
		            else
		            {
		            	 
		            	Application.showMessage("accountNumber value from rtpTestInterface is::"+accountNumber_rtpTest);
		            	Application.showMessage("c.getValue(ACC_input) value  is::"+c.getValue("ACC_input").toString().trim());
		            	System.out.println("accountNumber value from rtpTestInterface is::"+accountNumber_rtpTest);
		            	System.out.println("c.getValue(ACC_input) value  is::"+c.getValue("ACC_input").toString().trim());
		            	
		            	if(accountNumber_rtpTest.trim().equals(c.getValue("ACC_input").toString().trim()))
		            	{ 
		            	
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
		            		System.out.println("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
		            		System.out.println("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		Application.showMessage("Account Number not validated..so picking other Account Number..!");
		            		System.out.println("Account Number not validated..so picking other Account Number..!");
		            		continue;
		            	}
		            			            	
		            }
		            Application.showMessage("CallFound is ::"+callFound);
		            System.out.println("CallFound is ::"+callFound);
		           
		            if(callFound==1)
	            	{
		            	 String locationId_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Location/@locationId");
		            	 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
		            	 System.out.println("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
				           
				            if(locationId_rtpTest==null)
				            {
				            	Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
				            	System.out.println("locationId value from rtpTestInterface is ::NULL \n");
					            c.report("locationId is NULL");
					            continue;
				            }
				            else
				            {
				            	
				            	 if(locationId_rtpTest.toString().trim().equals(c.getValue("sHOUSE_KEY").toString().trim()))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
					            	 Application.showMessage("Validation is Successfull with locationId"+" "+locationId_rtpTest);
					            	 System.out.println("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
					            	 System.out.println("Validation is Successfull with locationId"+" "+locationId_rtpTest);
					            	 v1=1;
					            	 String PrimaryEmail_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Account/PrimaryEmail");
					            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
					            	 System.out.println("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
                                     c.setValue("emailAddress", PrimaryEmail_rtpTest);
							            
							         String FirstName_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Account/FirstName");
							         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
							         System.out.println("FirstName is ::"+ FirstName_rtpTest); 
							         c.setValue("FirstName", FirstName_rtpTest); 
							         
							         String LastName_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/Account/LastName");
							         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
							         System.out.println("LastName is ::"+ LastName_rtpTest); 
							         c.setValue("LastName", LastName_rtpTest); 
							         
							         String OrderStatus_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderStatus");
							         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
							         System.out.println("OrderStatus is ::"+ OrderStatus_rtpTest); 
							            
							         String OrderType_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderType");
							         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
							         System.out.println("OrderType is ::"+ OrderType_rtpTest); 
							         
							         String RescheduleIndicator_rtpTest=Lp.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
							         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
							         System.out.println("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
							         String EventSource= Lp.nodeFromKey(senddata,"<EventSource>","</EventSource>");
							         c.setValue("sEventSource", EventSource);
							         Application.showMessage("EventSource is::"+c.getValue("sEventSource")); 
							         System.out.println("EventSource is::"+c.getValue("sEventSource")); 
							         
							         String city=  Lp.nodeFromKey(senddata,"<City>","</City>");
							         c.put("Ecity",city);
							         Application.showMessage("Ecity is::"+c.get("Ecity")); 
							         System.out.println("Ecity is::"+c.get("Ecity")); 
							         
							         String Zipcode=  Lp.nodeFromKey(senddata,"<Zipcode>","</Zipcode>");
							         c.put("Ezipcode",Zipcode);
							         Application.showMessage("Ezipcode is::"+c.get("Ezipcode")); 
							         System.out.println("Ezipcode is::"+c.get("Ezipcode")); 

							         
							         if(v1*callFound==1)
							            {
							            
							            	Application.showMessage("Validation Points are Success");
							            	System.out.println("Validation Points are Success");
							            	break;
							            }
							            
							            else
							            {
							            	Application.showMessage("No call Found");
							            	System.out.println("No call Found");
							            	c.put("result", "false");
							            	break;
							            }
					             }
					             else
					             {
					            	 Application.showMessage("Location ID *******NOT FOUND ***");
					            	 System.out.println("Location ID *******NOT FOUND ***");
					            	 continue;
					             }
				            }				            
	            	}
	             
             		       
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	
	            	String recievedata=Lp.extractXml(rs,xmldata1);
		         
		            String accountNumber_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/@accountNumber");
		            Application.showMessage("accountNumber is ::"+accountNumber_rtpTest); 
		            Application.showMessage("recievedata is ::"+recievedata); 
		            System.out.println("accountNumber is ::"+accountNumber_rtpTest); 
		            System.out.println("recievedata is ::"+recievedata); 
		            	          
		            if(accountNumber_rtpTest==null)
		            {
			          
			            Application.showMessage("accountNumber ID is NULL \n");
			            System.out.println("accountNumber ID is NULL \n");

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
		            		System.out.println("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
		            		System.out.println("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            			            	
		            }
		            
		            if(callFound==1)
	            	{
		            	 String locationId_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Location/@locationId");
		            	 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
		            	 System.out.println("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
				           
				            if(locationId_rtpTest==null)
				            {
				            	Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
				            	System.out.println("locationId value from rtpTestInterface is ::NULL \n");
					            c.report("locationId is NULL");
					            continue;
				            }
				            else
				            {
				            	 
				            	 if(locationId_rtpTest.equals(c.getValue("sHOUSE_KEY")))
				            	 {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
					            	 Application.showMessage("Validation is Successfull with locationId"+" "+locationId_rtpTest);
					            	 System.out.println("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
					            	 System.out.println("Validation is Successfull with locationId"+" "+locationId_rtpTest);
					            	 v1=1;
					            	 String PrimaryEmail_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
					            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
					            	 System.out.println("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
					            	 c.setValue("emailAddress", PrimaryEmail_rtpTest);
					            	 
							         String FirstName_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/FirstName");
							         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
							         System.out.println("FirstName is ::"+ FirstName_rtpTest); 
							         c.setValue("FirstName", FirstName_rtpTest);
							         
							         String LastName_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/LastName");
							         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
							         System.out.println("LastName is ::"+ LastName_rtpTest); 
							         c.setValue("LastName", LastName_rtpTest); 
							         
							         String OrderStatus_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
							         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
							         System.out.println("OrderStatus is ::"+ OrderStatus_rtpTest); 
							            
							            
							         String OrderType_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
							         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
							         System.out.println("OrderType is ::"+ OrderType_rtpTest); 
							         
							         String RescheduleIndicator_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
							         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
							         System.out.println("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
							         String EventSource= Lp.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
							         c.setValue("sEventSource", EventSource);
							         
							         String city=  Lp.nodeFromKey(recievedata,"<City>","</City>");
							         c.put("Ecity",city);
							         System.out.println("Ecity is::"+c.get("Ecity")); 
							         
							         String Zipcode=  Lp.nodeFromKey(recievedata,"<City>","</City>");
							         c.put("Ezipcode",Zipcode);
							         Application.showMessage("Ezipcode is::"+c.get("Ezipcode")); 
							         System.out.println("Ezipcode is::"+c.get("Ezipcode")); 
							         
							         Application.showMessage("EventSource is::"+c.getValue("sEventSource"));
							         System.out.println("EventSource is::"+c.getValue("sEventSource"));
							         if(v1*callFound==1)
							            {
							            
							            	Application.showMessage("Validation Points are Success");
							            	System.out.println("Validation Points are Success");
							            	break;
							            }
							            
							            else
							            {
							            	c.put("result", "false");
							            	Application.showMessage("No call Found");	
							            	System.out.println("No call Found");	
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
		            	String recievedata=Lp.extractXml(rs,xmldata1);
				         
			            String accountNumber_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/@accountNumber");
			            Application.showMessage("accountNumber is ::"+accountNumber_rtpTest); 
			            System.out.println("accountNumber is ::"+accountNumber_rtpTest); 
			            	          
			            if(accountNumber_rtpTest==null)
			            {
				          
				            Application.showMessage("accountNumber ID is NULL \n");
				            System.out.println("accountNumber ID is NULL \n");

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
			            		System.out.println("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
			            		System.out.println("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
			            		callFound=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            			            	
			            }
			            
			            if(callFound==1)
		            	{
			            	 String locationId_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Location/@locationId");
			            	 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
			            	 System.out.println("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
					           
					            if(locationId_rtpTest==null)
					            {
					            	Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
					            	System.out.println("locationId value from rtpTestInterface is ::NULL \n");
						            c.report("locationId is NULL");
						            continue;
					            }
					            else
					            {
					            	 Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+locationId_rtpTest);
					            	 System.out.println("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+locationId_rtpTest);
					            	 if(locationId_rtpTest.equals(c.getValue("sHOUSE_KEY")))
					            	 {
						            	 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
						            	 Application.showMessage("Validation is Successfull with locationId"+" "+locationId_rtpTest);
						            	 System.out.println("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
						            	 System.out.println("Validation is Successfull with locationId"+" "+locationId_rtpTest);
						            	 v1=1;
						            	 String PrimaryEmail_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
						            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
						            	 System.out.println("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
						            	 c.setValue("emailAddress", PrimaryEmail_rtpTest);
						            	 
								         String FirstName_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/FirstName");
								         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
								         System.out.println("FirstName is ::"+ FirstName_rtpTest); 
								         c.setValue("FirstName", FirstName_rtpTest);
								         
								         String LastName_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/Account/LastName");
								         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
								         System.out.println("LastName is ::"+ LastName_rtpTest); 
								         c.setValue("LastName", LastName_rtpTest); 
								         
								         String OrderStatus_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
								         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
								         System.out.println("OrderStatus is ::"+ OrderStatus_rtpTest); 
								            
								         String OrderType_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
								         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
								         System.out.println("OrderType is ::"+ OrderType_rtpTest); 
								         
								         String RescheduleIndicator_rtpTest=Lp.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
								         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
								         System.out.println("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
								         String EventSource= Lp.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
								         c.setValue("sEventSource", EventSource);
								         
								         String city=  Lp.nodeFromKey(recievedata,"<City>","</City>");
								         c.put("Ecity",city);
								         Application.showMessage("Ecity is::"+c.get("Ecity")); 
								         System.out.println("Ecity is::"+c.get("Ecity")); 
								         
								         String Zipcode=  Lp.nodeFromKey(recievedata,"<City>","</City>");
								         c.put("Ezipcode",Zipcode);
								         Application.showMessage("Ezipcode is::"+c.get("Ezipcode"));
								         System.out.println("Ezipcode is::"+c.get("Ezipcode"));
								         
								         Application.showMessage("EventSource is::"+c.getValue("sEventSource"));
								         System.out.println("EventSource is::"+c.getValue("sEventSource"));
								         if(v1*callFound==1)
								            {
								            
								            	Application.showMessage("Validation Points are Success");
								            	System.out.println("Validation Points are Success");
								            	break;
								            }
								            
								            else
								            {
								            	c.put("result", "false");
								            	Application.showMessage("No call Found");	
								            	System.out.println("No call Found");	
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
	        
	       //  st.close();
	        }
		}
		}
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			
		}
		return rscallpresent;
		
	}

public void InvaliddisconnectValidationsservices(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, SAXException 
{
	Application.showMessage("---**Disconnect Validations**-------");
	
	String Disorderstatus="Completed";
	String Disordertype1="Disconnect";
	String DisorderType2="Change";
	String Disstatus="ACTIVE";
	String xmlorderstatus=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_OrderStatus");
	Application.showMessage("order status::"+xmlorderstatus);
	String xmlordertype=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_OrderType");
	Application.showMessage("order type::"+xmlordertype);
	String simorderstatus=c.getValue("RTPDataSourceCollections","dB_Simulator: status");
	Application.showMessage("status::"+simorderstatus);
	Boolean ordertypevalue=xmlordertype.equals(Disordertype1) || xmlordertype.equals(DisorderType2);
	//Application.showMessage("order value::"+ordertypevalue);
	if(xmlorderstatus.equals(Disorderstatus) && ordertypevalue==true && Disstatus.equalsIgnoreCase(simorderstatus))
	{
		//Application.showMessage("---**Disconnect Validations**-------");
	//lR.LoadValuestoGlobalList(input, c);
	
	//SuspendClass sC=new SuspendClass();
	Thread.sleep(50000);
	//lb.SimgetAccout_ValidateDis(input,c);
	VirtualizegetAccout_ValidateSus( input,  c);
	//queryLocation_Validate(input,c);
	String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
 	String Instatus="Open";
    InvalidthirtyDisconnectTableValidation(Instatus,input, c);
	//sC.processHomeSecurityInfo_Validate(input, c);
	//sC.suspend_COPS_validate(input, c);
	//lb.SIMSuspendAccount_Validate(input, c);
	//lb.orderUpdate_Validatesus(input, c);
	}
	else
	{
		
		CancelClass cC=new CancelClass();
    	//lR.LoadValuestoGlobalList(input, c);
    	Thread.sleep(50000);
    	//cC.getAccout_Validate(input, c);
    	//lb.SimgetAccout_ValidateDis(input,c);
    	VirtualizegetAccout_ValidateDisconnect( input,  c);
    	//queryLocation_Validate(input,c);
    	cC.processHomeSecurityInfo_Validate(input, c);
    	cC.deleteUnactivatedAccount_Validate(input, c);
    	cC.DisconnectAccount_CANCEL_Validate(input, c);
    	//cC.orderUpdate_Validate(input, c);
    	//lb.orderUpdate_ValidateCancel(input, c);
    	
	}
}
public void InvalidthirtyDisconnectTableValidation(String Instatus,Object input,ScriptingContext c) throws ClassNotFoundException, IOException
{
	ThirtyDayDisconnect tdd=new ThirtyDayDisconnect();
	
	Map<String,String> getMapData=new HashMap <String,String>();
	    getMapData=tdd.connectThirtyDayDisconnectDB(input, c);
	    String status=getMapData.get("status"); 	    
	    Application.showMessage("The status in validation process is ::"+status);
	    String date= getMapData.get("date");
	    Application.showMessage("The date in validation process is ::"+date);
	    String service= "350 + ThermostatDataProvider";
	    Application.showMessage("The service in validation process is ::"+service);
 	//String isLetter= getMapData.get("isLetter");
 	//Application.showMessage("The isLetter in validation process is ::"+isLetter);
 	int p1=Lp.validationPointIgnoreCase(status, Instatus, input, c);
 	String InService="350 + ThermostatDataProvider";
 	int p2=Lp.validationPoint(service, InService, input, c);
 	if(p1==1 && p2==1)
 	{
 	Application.showMessage("Validation is successful with Disconnect");
 	}
 	else
 	{
 		Application.showMessage("Account number is not updated in Database....So, Validation is not successful with Disconnect");
 		c.report("Account number is not updated in Database....So, Validation is not successful with Disconnect");
 	}
 	
	
	
}
public void NormalCosValidationsservices(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, SAXException
{
            
            
            //lR.LoadValuestoGlobalList(input, c);
            ChangeOfServiceClass CS = new ChangeOfServiceClass();
            HandleInvalidServices hI=new HandleInvalidServices();
            
            Thread.sleep(40000);
            //CS.getAccoutCos_Validate(input, c);
            
          // nc.NormalgetAccout_ValidateSus(input, c);
            queryLocation_Validate(input,c);
            Application.showMessage("***Change Of service Validation calls*****");
            //List<String> addAccountgroup=lb.addAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: UcontrolServiceSusRes"), input, c);
            List<String> addAccountgroup=lb.addAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"), input, c);
            List<String> removeAccountgroup=lb.removeAccountServices(c.getValue("RTPNormalScenariosdS","RTP_InputParams: NEWSERVICE"), c.getValue("RTPNormalScenariosdS","RTP_InputParams: SERVICEorCURRENTSERVICE"), input, c);
            if(c.getValue("IsUpdateTier").equals("true"))
                                            {
                            Application.showMessage("***Update Tier Group Validation calls*****");
                            updateTier_Validate(c.getValue("updatedvalue"),input,c);
                                            }
            else
            {
                            Application.showMessage("OP should not trigger update tier call");
            }
            if(c.getValue("IsAddaccountgroup").equals("true"))
                                            {
                            Application.showMessage("***addAccountgroup Validation calls*****");
                            for(int i=0;i<addAccountgroup.size();i++)
                            {
                                            String addServices=addAccountgroup.get(i);
                            AddGroup_Validate(addServices,input, c);
                            }
                                            }
            else
            {
                            Application.showMessage("OP should not triggere add account group call");
            }
            if(c.getValue("IsRemoveaccountgroup").equals("true"))
                                            {
                            Application.showMessage("***removeAccountgroup Validation calls*****");
                            for(int i=0;i<removeAccountgroup.size();i++)
                            {
                                            String addServices=removeAccountgroup.get(i);
                            RemoveGroup_Validate(addServices,input,c);
                                                }
                                            }
            else
            {
                            Application.showMessage("OP should not triggere Remove account group call");
            }
            //CS.cosLogicFlow(input, c);        
            //CS.cosLogicFlow(input, c);        
            //CS.orderUpdateCos_Validate(input, c);
            lb.orderUpdate_ValidateCOS(input, c);
            }
             
}
