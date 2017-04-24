import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.bson.BasicBSONObject;
import org.json.JSONException;
import org.xml.sax.SAXException;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class XHOM_CVR_AllFlows_ValidationCalls
{
	
	
	
	
	
	XHOM_RTP_ValidationMethods rv=new XHOM_RTP_ValidationMethods();
	XHOM_CVR_ValidationCalls XHCVR=new XHOM_CVR_ValidationCalls();
	XHOM_BasicMethods BM=new XHOM_BasicMethods();
	XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	//XHOM_CVR_AllFlows_ValidationCalls XHCVRAllFlows = new XHOM_CVR_AllFlows_ValidationCalls();
	XHOM_DemiInstall_Validations DI= new XHOM_DemiInstall_Validations();
	public static String rowmsg= null;
	
	//*********************Demi Validation********************
	
	
	public void demicalls_CVR(Object input , ScriptingContext c ) throws Exception
    {
		
		rv.demicalls_Normal(input, c);
		String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "Install", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    	 
   }
	
	public void demicalls_CVR4Xi(Object input , ScriptingContext c ) throws Exception
    {
		
		
		String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVR4xiValidations(input, c, "Install", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    	 
   }
	
	public void demicallsCompletedRestart_CVR(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, InstantiationException, IllegalAccessException, SQLException
    {
        	    	
    	 Thread.sleep(6000);
    	 if(c.get("CVR").equals("YES"))
         {
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
          		  val= XHCVR.processHomeSecurityInfoCVR_Validate(input, c,"ADD");        		  
          		   break;
          	   case 1:
          		   
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
     		Application.showMessage(" No need of CVR validation");
     	}
     }
        
	public void CosValidations_CVR(Object input, ScriptingContext c) throws Exception
    {
    	rv.CosValidations_normal(input, c);
    	
    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "COS", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    }
    public void CosValidationssameservices_CVR(Object input, ScriptingContext c) throws Exception
    {
    	
    	rv.CosValidations_normal(input, c);
    	
    	if( c.getValue("IsDemi").equals("true"))
    	{
    	if(c.get("CVR").equals("YES"))
        {
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
         		  val=  XHCVR.processHomeSecurityInfoCVR_Validate(input, c,"ADD");        		  
         		   break;
         	   case 1:
         		   
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
    		Application.showMessage(" No need of CVR validation");
    	}
    	}
    	else
    	{
    	if(c.get("INSIGHTCVR").equals("YES"))
         {
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
         		  val=  XHCVR.processHomeSecurityInfoCVR_Validate(input, c,"ADD");        		  
         		   break;
         	   case 1:
         		 
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
     		Application.showMessage(" No need of CVR validation");
     	}
    	}
    	 
    }
    
    public void SuspendValidations_CVR(Object input,ScriptingContext c)throws Exception 
    {
    	rv.SuspendValidations_normal(input, c);
    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "Suspend", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    }
    
    
    
    public void RestoreValidations(Object input,ScriptingContext c)throws Exception 
    {
    	
    	rv.RestoreValidations_normal(input, c);
    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
    	if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "Restore", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    }
    
    
    
       
        
    public void CancelValidations(Object input,ScriptingContext c)throws Exception
    {
    	rv.CancelValidations_normal(input, c);
    	
    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "Cancel", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    }     
    
    public void DisconnectCVRValidations(Object input,ScriptingContext c) throws SQLException, Exception
    {
    	
    	Thread.sleep(20000);    	
    	String cvr=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: CVR");
		
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "Disconnect", "REMOVE","0","0","Disconnected");
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    	
    	
    }
    //----------------
    public void DisconnectCVR4XIValidations(Object input,ScriptingContext c) throws SQLException, Exception
    {
    	
    	Thread.sleep(20000);    	
    	String cvr=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: CVR");
		
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR. AllCVR4xiValidations(input, c, "Disconnect", "REMOVE","0","0","Disconnected");
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    	
    	
    }
   
   
    //-----------------------------------
    public void ScheduleDisconnectCVRValidations(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException
    {
    	CVR_ValidationCalls cvr =new CVR_ValidationCalls();
    	SuspendClass sus=new SuspendClass();
    	RTP_SimulatorClass_UpDown sc=new RTP_SimulatorClass_UpDown();
    	ExecutionClass_R er=new ExecutionClass_R();
    	Thread.sleep(20000);
    	ScheduleUpgradeDowngradeValidation sUD=new ScheduleUpgradeDowngradeValidation();
    	New_ValidationCalls ne=new New_ValidationCalls();
    	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Insight"))
    {
    		Application.showMessage("The value of CVR is"+c.getValue("CVR"));
    	if(c.getValue("CVR").equals("YES"))
        {
       	cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);          
       	String statusCheck = "Open";
        er.thirtyDisconnectTableValidation(statusCheck, input, c);
        sUD.validateScheduleDisconnect(input, c, "Cancelled");
        runglobalprocesscvr(input, c);
   	    Thread.sleep(300000);   	  
   		cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
     	cvr.DatasourceBNERcallCVR_Validate(input, c); 
        }
    	else
    	{
    		Application.showMessage(" No need of CVR validation");
    		String statusCheck = "Open";
            er.thirtyDisconnectTableValidation(statusCheck, input, c);
            runglobalprocesscvr(input, c);
       	    Thread.sleep(300000); 
    	} 
    	sc.processHomeSecurityInfo_Validate(input, c);
 	   Thread.sleep(3000);
 	   sus.SuspendAccount_Validate(input, c); 
  	   Thread.sleep(3000);
 	
 	   sc.deactivateAccount_Validate_CLS(input, c);
 	   Thread.sleep(3000);
 	  ne.ScheduleorderUpdate_Validate(input, c);
    	    	 
     }
  	
		
    	else
    	{
    		if(c.getValue("CVR").equals("YES"))
            {
    		Application.showMessage("The value of CVR is"+c.getValue("CVR"));
           	cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);              
           	Thread.sleep(10000);
           	String statusCheck = "Open";
           	threeDaycvrTableValidation(statusCheck,input,c); 
           	runglobalprocesscvr(input, c);
      	    Thread.sleep(300000);       	  
      	    cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
        	cvr.DatasourceBNERcallCVR_Validate(input, c); 
            }
        	else
        	{
        		Application.showMessage(" No need of CVR validation");
        		Thread.sleep(10000);
        		String statusCheck = "Open";
               	threeDaycvrTableValidation(statusCheck,input,c); 
               	runglobalprocesscvr(input, c);
             	Thread.sleep(300000);  
        	} 
    		sc.processHomeSecurityInfo_Validate(input, c);
    		   Thread.sleep(3000);
    		   sus.SuspendAccount_Validate(input, c); 
    	 	Thread.sleep(3000);
    		 sc.deactivateAccount_Validate_CLS(input, c);
    		   Thread.sleep(3000);
    		   ne.ScheduleorderUpdate_Validate(input, c);
    		       	 
         }
    }
    
    
    
    
    //----------------------------
    
    public void runglobalprocesscvr(Object input,ScriptingContext c) throws ClassNotFoundException, IOException
    {
    	LibraryRtp_UpDown p=new LibraryRtp_UpDown();
    	try {

    	    Statement st = p.dBConnect(input, c);
    	    Application.showMessage("--***Truncate Global Process***---");
    	    ResultSet rs = st.executeQuery("truncate table cwglobalprocess");
    	    Application.showMessage("---Successful in truncatiinf Table---");

    	st.close();
    	}
    	catch (SQLException e) {
    	    System.out.println("Connection Failed! Check output console");
    	                e.printStackTrace();

    	 }
    }
    //------CVR table Validations------
    
    public void threeDaycvrTableValidation(String Instatus, Object input,ScriptingContext c) throws ClassNotFoundException, IOException,InterruptedException
    {
    	LibraryRtp_UpDown Rp=new LibraryRtp_UpDown();
         Thread.sleep(3000);

       Map<String, String> getMapData = new HashMap<String, String>();
       getMapData = connectThreeDayCVRDB(input, c);
       String status = getMapData.get("status");
       Application.showMessage("The status in validation process is ::"
                            + status);
       String date = getMapData.get("date");
       Application.showMessage("The date in validation process is ::" + date);
      Boolean p1 = Rp.validationPointIgnoreCase(status, Instatus, input, c);

     }
             

    
    //--------
    
    public Map<String, String> connectThreeDayCVRDB(Object input,ScriptingContext c) throws ClassNotFoundException, IOException,NullPointerException, InterruptedException
    {
    	LibraryRtp_UpDown LT=new LibraryRtp_UpDown();
          Application.showMessage("------------------------------------------------------------------");
        Application.showMessage("*##Connecting to connectTHREEDAYSCVRDISCONNECT retrieving data... ");
        Application.showMessage("------------------------------------------------------------------");
              ResultSet rs;
       String subAccount = c.getValue("ACC_input");
        Application.showMessage("The Account Number is ::" + subAccount);

              String dStatus = null;
              String dDateBegin = null;
    Map<String, String> returnMap = new HashMap<String, String>();
     Thread.sleep(5000);

try {

      Statement st = LT.dBConnect(input, c);

            rs = st.executeQuery("select * from THREEDAYSCVRDISCONNECT where accountnumber='"
                                            + subAccount + "' ");

            while (rs.next()) {

                            dStatus = rs.getString("STATUS");
                            dDateBegin = rs.getString("DATE_HOLD_BEGAN");
                                 }
            Application.showMessage("STATUS is ::" + dStatus);
            Application.showMessage("dDateBegin is ::" + dDateBegin);
            returnMap.put("status", dStatus.trim());
            returnMap.put("date", dDateBegin.trim());
                   
      st.close();
}

catch (SQLException e) {
               System.out.println("Connection Failed! Check output console");
                           e.printStackTrace();

            }
            return returnMap;

}
    
    
    //-----------
    
    
    
    public void InstallValidations(Object input, ScriptingContext c) throws Exception
	{
    	RTP_ValidationsClass_UpDown vC = new RTP_ValidationsClass_UpDown();
    	//Application.showMessage("Yamini");
		if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
		{
			rv.demicalls_Normal(input, c);
			
		}
		else
		{
			//vC.Insightcalls(input, c);
			
		}
	}
  //*******************Insight Calls Validations**************************** 
    
    
    public void Insightcalls_CVR(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException, Exception
    { 
    
    	
    	rv.Insightcalls_cls(input, c);
		String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "Install", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    }
    
    public void InsightSuspendValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException, Exception 
    {
    	RTP_ValidationsClass rval=new RTP_ValidationsClass();
    	rval.InsightSuspendValidations_CLS(input, c);
    	
    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "Suspend", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    	 
   }
    	
  
    public void InsightRestoreValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, Exception 
    {
    	RTP_ValidationsClass rval=new RTP_ValidationsClass();
    	rval.InsightRestoreValidations(input, c);
    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "Restore", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    }
    
    public void InsightCancelValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException, Exception
    {
    	RTP_ValidationsClass rval=new RTP_ValidationsClass();
    	rval.CancelValidations_cls_normal(input, c);
    	
    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
         
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVRValidations(input, c, "Cancel", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    }
    
    
    
    
    //--------------------
    
    public void createAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
    {
    	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
         LibraryRtp lr=new LibraryRtp();
    	 ResultSet  rs;
    	 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
    	 String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String Time= c.get("BaseTime").toString();
         c.setValue("createAccount","true");
         Application.showMessage("-----------------------------------------------------");
         Application.showMessage("***********createAccount_Validate Function***********");       
         Application.showMessage("----------------------------------------------------");
         
         Map<String, String> ResultMap = new HashMap<String,String>();
        
         ResultMap= lC.splitter(c.getValue("RTPNormalScenariosdS", "RTP_InputParams: SERVICEorCURRENTSERVICE"));
        
         c.setValue("ExistingGroup",ResultMap.get("group"));
         c.setValue("ExistingService", ResultMap.get("service"));
         c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
         lr.findThinktimeOperationRTP(input, c);
         c.setValue("createAccount","false");   
    	try
    	{
    		// Statement st =lC. dBConnect(input, c);	
            // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' order by creation_time desc)where rownum <= 20");
    		rs=lr.reduceThinkTimeRTP(input, c);
             while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
             {
            	
            
                String rowmsg = rs.getString(1);
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
          //   st.close();
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
	     LibraryRtp  lC= new LibraryRtp ();
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
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	            String rowmsg = rs.getString(1);
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
			            	 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("ACTIVE"))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
				            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+homeSecurityLOBStatus_DDS);
				             }
			            }
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound*v1==1)
		            {
		      
		 	           

		 	            
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
				            	 v2=1;
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
	         
	         if(v1*callFound*v2 ==1)
	         {
	         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
	         }
	         else
	         {
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
    //--Insight processhome security----
    
    public String processHomeSecurityInfo_Validateinsight(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
    {
    	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
    	 ResultSet  rs;
    	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
    	 String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";

String rscallpresent="true";
         Application.showMessage("-----------------------------------------------------");
         Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
         Application.showMessage("----------------------------------------------------");
         HashMap Operation=lC.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("processHomeSecurityInfo"));
         Application.showMessage("OPERATIONValidation is "+(String) Operation.get("processHomeSecurityInfo")); 
         
    	try
    	{
    		rs=lC.reduceThinkTimeRTP(input, c);
    		if(rs == null)
        	{
        		rscallpresent = "false";
        		
        	
        		
        	}
        	else
{
    		 //Statement st =lC. dBConnect(input, c);	
             //rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
             while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
             {
            	
            
                String rowmsg = rs.getString(1);
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
             
             if(v1*callFound*v2*v3*v4 ==1)
             {
             	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
             }
             else
             {
            	 c.put("result", "false");
            	 c.report("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
             }
             //st.close();
    	}	
    	}
    	catch (SQLException e)
    	{
    	    System.out.println("Connection Failed! Check output console");
    		e.printStackTrace();
    		
    	}
		return rscallpresent;
     }
	//Calls for cv4xi....Sruthi
    public String getAccountFromDDS(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException, JSONException 
    {
                    Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
         LibraryRtp  lC= new LibraryRtp ();
         DBCursor  rs;
         DBObject obj = null;
         int callFound=0,v1=0,v2=0,v3=0;
         String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";
         String rscallpresent="true";
         String Time=c.get("BaseTime").toString();;
 		//Application.showMessage("BaseTime is::"+Time);
         Application.showMessage("-----------------------------------------------------");
         Application.showMessage("*****Get Account for DDS******");       
         Application.showMessage("-----------------------------------------------------");
         HashMap Operation=XH.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("getAccountDDS"));
         Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getAccountDDS"));
         List<String> addIssues=new ArrayList();
 	    String Issues="Issues from NewgetAccountforDDS:::";
             
                    try
                    {
                                  //  Statement st =lC. dBConnect(input, c); 
            // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                    	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
                    	if(rs == null)
                    	{
                    		rscallpresent = "false";
                    		
                    	
                    		
                    	}
                    	else
    {

                    		while (rs.hasNext() )
                            {
                           obj=rs.next();
                              if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
                              {
                     rowmsg=((BasicBSONObject) obj).getString("_id");
                     if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
                     {
                     
                         Application.showMessage("Your Recieve XML is NULL \n");
                         
                         String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                         //REQUEST
                         Application.showMessage("Your Recieve XML is::\n"+senddata);
                     }
                     else if(((BasicBSONObject) obj).getString("REQUEST")==null)
                     {
                         Application.showMessage("Your SEND XML is NULL \n");             
                         String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
                         Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
                     }
                     else
                 
          {
                    	 String senddata=((BasicBSONObject) obj).getString("REQUEST");
                    	 //String senddata =XH.extractAccFromURL(((BasicBSONObject) obj).getString("REQUEST"), input, c);//REQUEST
                         
                     String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
                     Application.showMessage("Your Send XML is::\n"+senddata);
                     Application.showMessage("Receive XML is ::\n"+recievedata);
                     String id= lC.nodeFromKey(senddata,"<BillingAccountNumber xmlns=\"\">","</BillingAccountNumber>");
                                Application.showMessage("id is ::"+id);               
                                if(id.equals(c.getValue("ACC_input")))
                    {
                                 //   Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                 //   Application.showMessage("SEND XML is :: \n"+senddata);                                   
                                    Application.showMessage("*******Validation Point 1 :: WebServicall-DDS-Get Account Call********");
                                    Application.showMessage("Validation is Successfull with Input Account Number"+id);
                                    callFound=1;
                    }
                    else
                    {
                                    continue;
                    }
                    
                                if(callFound==1)
                                {
                          
                                    //String account_number=lC.nodeFromKey(recievedata,"<AccountInfo xmlns:ns2=accountNumber=\"\">","</ns2:accountNumber>");
                                    String account_number=lC.nodeFromKey(recievedata,"<ns2:accountNumber>","</ns2:accountNumber>");
                                               // String errorCode_getAcc= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
                                                Application.showMessage("Account number is ::"+account_number);
                                                v1=1;
                                                
                                                 if(account_number==null)
                                                 {
                                                                addIssues.add("Recieve Xml Account Number is NULL");
                                                                continue;
                                                 }
                                                             
                                               String authguid_ID= lC.nodeFromKey(recievedata,"<ns2:authGuid>","</ns2:authGuid>");
                                               Application.showMessage("authguid_ID"+authguid_ID);
                                               if (!authguid_ID.equalsIgnoreCase("null"))
                                               {
                                            	   c.setValue("authorizationGuid",authguid_ID);
                                                 //String errorMessage_getAcc= lC.nodeFromKey(recievedata,"<errorMessage xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorMessage>");
                                                Application.showMessage("errorMessage is::"+authguid_ID); 
                                                v2=1;
                                               }
                                               else
                                               {
                                            	   addIssues.add("AuthID is not determined"+authguid_ID);
                                               }
                                                String status= lC.nodeFromKey(recievedata,"<ns2:responseStatus>","</ns2:responseStatus>");
                                                Application.showMessage("Status is::"+status); 
                                               if(status.equalsIgnoreCase("Success"))
                                               {
                                            	   Application.showMessage("Status is::"+status); 
                                                   v3=1;
                                               }
                                               else
                                               {
                                            	   addIssues.add("Status is not determined"+status);
                                               }
                                               

                                break;
                                }
                 }
                              }
                            }
                              
             if(v1*callFound*v2*v3==1)
             {
            	 //c.put("NewgetAcc","no");
                    Application.showMessage("WebService Call :: Get Account is Success[All validation points are Success]");
             }
            
             else
	         {//Yamini
            	 c.put("result", "false");
            	 if(callFound!=1)
            	 {
            		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
                     
            	 }
	        	 for(int i=0;i<addIssues.size();i++)
	        	 {
	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
	        	 }
	        	 c.put("NewgetAcc",Issues.concat("WebService Call :: getAccount_Validate is Failed with Validation Errors").concat("**"));
	         }

         //    st.close();
             rs.close();
                    }    
                    
                    }
                    catch (SQLException e)
                    {
                        System.out.println("Connection Failed! Check output console");
                                    e.printStackTrace();
                                  
                    }
    				return rscallpresent;
    }
    
    //CVr4Xi Install flow
    
    /*public void cv4XiInstall(Object input, ScriptingContext c) throws Exception,InterruptedException, ClassNotFoundException, XPathExpressionException, NullPointerException, IOException, ParserConfigurationException, SAXException, JSONException
    {
    	Application.showMessage("Install call for cvr4Xi");
    	 Thread.sleep(4000);
    	 String cvrNo="";
    	 String OrdStatus="";
    	 String Value="";
    	 BM.rtpTestInterface_Validate(input, c); 
    	 XHCVRAllFlows.getAccountFromDDS(input, c);
    	 XHCVR.getEntitilements(input, c, Value);
    	 XHCVR.BNERcallCVR_Validate(input, c, cvrNo, OrdStatus);
    	 DI.orderUpdate_Validate_cvr4xi(input, c,"NEW");
    	 
    	
    }*/

///------------------New code--------------------------
//---------------------------cv4xiDemicalls--------------------
	public void Installcalls_CVR4Xi(Object input , ScriptingContext c ) throws Exception
  {
		String cvr = null;
		String DDS = null;
		String entitlements = null;
		String UpdateBner = null;
		String orderstatus = null;
		String AccountNumber = null;
		//rv.demicalls_Normal(input, c);
		//Application.showMessage(" entering method");
		
		try{
			 cvr=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: CVR");
			 DDS=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: DDS");
			entitlements=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: Entitlements");
			 UpdateBner=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpdateBner");
			orderstatus=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: OrderStatus");
			 AccountNumber=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_AccNumber");
		}
		catch(Exception e)
		{
			
		}
		if(AccountNumber.startsWith("821010"))
		{
       
      if(cvr.equalsIgnoreCase("YES"))
      {
    	  
      	XHCVR.AllCVR4xiValidations(input, c, "Install", DDS,entitlements,UpdateBner,orderstatus);
       }
   	else
   	{
   		Application.showMessage(" No need of CVR validation");
   	}
		}
		else
		{
			Application.showMessage("Corp validation is invalid. No cvr calls");
		}
 }
	
	//-------------------
	
	
	public void Installcalls_CVR4XiDisconnect(Object input , ScriptingContext c ) throws Exception
	  {
			String cvr = null;
			String DDS = null;
			String entitlements = null;
			String UpdateBner = null;
			String orderstatus = null;
			String AccountNumber = null;
			//rv.demicalls_Normal(input, c);
			//Application.showMessage(" entering method");
			try
			{
			
				 cvr=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: CVR");
				 DDS=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: DDS");
				entitlements=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: Entitlements");
				 UpdateBner=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpdateBner");
				orderstatus=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: OrderStatus");
				 AccountNumber=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_AccNumber");
			}
			catch(Exception e){
			
			}
			if(AccountNumber.startsWith("821010"))
			{
	       
	      if(cvr.equalsIgnoreCase("YES"))
	      {
	    	  
	      	XHCVR.AllCVR4xiValidations(input, c, "Install", DDS,entitlements,UpdateBner,orderstatus);
	       }
	   	else
	   	{
	   		Application.showMessage(" No need of CVR validation");
	   	}
			}
			else
			{
				Application.showMessage("Corp validation is invalid. No cvr calls");
			}
	 }
	//--------------------
	public void CosValidations_CVR4xi(Object input, ScriptingContext c) throws Exception
    {
    	//rv.CosValidations_normal(input, c);
    	
    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
		String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
		String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
		String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
		String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
		if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber").startsWith("821010"))
		{
        if(cvr.equalsIgnoreCase("YES"))
        {
        	XHCVR.AllCVR4xiValidations(input, c, "COS", DDS,entitlements,UpdateBner,orderstatus);
         }
     	else
     	{
     		Application.showMessage(" No need of CVR validation");
     	}
    
		}
	else
	{
		Application.showMessage("Corp validation is invalid. No cvr calls");
	}
	}
	 public void SuspendValidations_CVR4xi(Object input,ScriptingContext c)throws Exception 
	    {
	    	//rv.SuspendValidations_normal(input, c);
	    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
			String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
			String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
			String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
			String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
			if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber").startsWith("821010"))
			{
	        if(cvr.equalsIgnoreCase("YES"))
	        {
	        	XHCVR.AllCVR4xiValidations(input, c, "Suspend", DDS,entitlements,UpdateBner,orderstatus);
	         }
	     	else
	     	{
	     		Application.showMessage(" No need of CVR validation");
	     	}
	    }
			else
			{
				Application.showMessage("Corp validation is invalid. No cvr calls");
			}
}
	
	 
	 public void RestoreValidations_cvr4Xi(Object input,ScriptingContext c)throws Exception 
	    {
	    	
	    	//rv.RestoreValidations_normal(input, c);
	    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
			String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
			String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
			String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
			String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
			if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber").startsWith("821010"))
			{
	    	if(cvr.equalsIgnoreCase("YES"))
	        {
	        	XHCVR.AllCVR4xiValidations(input, c, "Restore", DDS,entitlements,UpdateBner,orderstatus);
	         }
	     	else
	     	{
	     		Application.showMessage(" No need of CVR validation");
	     	}
	    }
			{
				Application.showMessage("Corp validation is invalid. No cvr calls");
			}
	    }
	 public void CancelValidations_cvr4Xi(Object input,ScriptingContext c)throws Exception
	    {
	    	//rv.CancelValidations_normal(input, c);
	    	
	    	String cvr=c.getValue("RTPNormalScenariosdS","RTP_InputParams: CVR");
			String DDS=c.getValue("RTPNormalScenariosdS","RTP_InputParams: DDS");
			String entitlements=c.getValue("RTPNormalScenariosdS","RTP_InputParams: Entitlements");
			String UpdateBner=c.getValue("RTPNormalScenariosdS","RTP_InputParams: UpdateBner");
			String orderstatus=c.getValue("RTPNormalScenariosdS","RTP_InputParams: OrderStatus");
			if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber").contains("8210100"))
			{
	        if(cvr.equalsIgnoreCase("YES"))
	        {
	        	XHCVR.AllCVR4xiValidations(input, c, "Cancel", DDS,entitlements,UpdateBner,orderstatus);
	         }
	     	else
	     	{
	     		Application.showMessage(" No need of CVR validation");
	     	}
			}
			else
			{
				Application.showMessage("Corp validation is invalid. No cvr calls");
			}
	    }  
	 
	
	 

}
