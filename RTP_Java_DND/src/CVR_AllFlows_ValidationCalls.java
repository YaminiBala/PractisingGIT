import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class CVR_AllFlows_ValidationCalls
{
	CVR_ValidationCalls cvr =new CVR_ValidationCalls();
	RTP_ValidationsClass rval=new RTP_ValidationsClass();
	RTP_ValidationsClass_UpDown rvalup=new RTP_ValidationsClass_UpDown();
	RestoreClass  rC=new RestoreClass ();
	RTP_SimulatorClass_UpDown sc=new RTP_SimulatorClass_UpDown();
	SuspendClass sus=new SuspendClass();
	
	
	//*********************Demi Validation********************
	
	
	public void demicalls_CVR(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, NullPointerException, SAXException, InstantiationException, IllegalAccessException, SQLException, XPathException
    {
		
		//rval.demicalls(input, c);
		//rvalup.demicallsReleasefrom30day(input, c);
		/*Thread.sleep(4000);
   	 //demicalls(input, c);
		rvalup.rtpTestInterface_Validate(input, c);
       // getCustomerPermitRequirements_Validate(input,c);
		rvalup.createCMSAccountID_Validate(input, c);
		rvalup.queryLocation_Validate(input,c);
		rvalup.getAccount_Validate(input, c);
        rvalup.processHomeSecurityInfo_Validate(input,c);
        rvalup.SetAccountBasicInformation_Validate(input,c);
        createAccount_Validate(input, c);
        rvalup.responderInfo_Validate(input,c);
        rvalup.SetAccountAuthorityInformation_Validate(input, c);
        rvalup.modifyHomeSecurity_Validate(input, c);
        //orderUpdate_Validate(input,c);
        rvalup.OrderUpdate_tocheckAEE(input, c);*/
		rval.demicalls_Normal(input, c);
         
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
      		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
      		   break;
      	   case 1:
      		   val=  cvr.BNERcallCVR_Validate(input, c);
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
          		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
          		   break;
          	   case 1:
          		   val=  cvr.BNERcallCVR_Validate(input, c);
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
        
	public void CosValidations_CVR(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
    {
    	rval.CosValidations_normal(input, c);
    	
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
         		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
         		   break;
         	   case 1:
         		   val=  cvr.BNERcallCVR_Validate(input, c);
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
          		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
          		   break;
          	   case 1:
          		   val=  cvr.BNERcallCVR_Validate(input, c);
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
    public void CosValidationssameservices_CVR(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
    {
    	
    	rval.CosValidationssameservices(input, c);
    	
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
         		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
         		   break;
         	   case 1:
         		   val=  cvr.BNERcallCVR_Validate(input, c);
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
         		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
         		   break;
         	   case 1:
         		   val=  cvr.BNERcallCVR_Validate(input, c);
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
    
    public void SuspendValidations_CVR(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException 
    {
    	rval.SuspendValidations_normal(input, c);
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
         		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
         		   break;
         	   case 1:
         		   val=  cvr.BNERcallCVR_Validate(input, c);
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
    
    
    
    public void RestoreValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException 
    {
    	//rval.RestoreValidations(input, c);
    	LibraryRtp lR= new LibraryRtp();
    	//lR.LoadValuestoGlobalList(input, c);
    	/*RestoreClass rC=new RestoreClass();
    	Thread.sleep(50000);
    	rC.getAccout_Validate(input, c);
    	rval.queryLocation_Validate(input,c);
    	//processHomeSecurityInfo_Validate(input, c);
    	rC.Activate_COPS_validate(input, c);
    	rC.RestoreAccount_Validate(input, c);
    	rC.orderUpdate_Validate(input, c);*/
    	rval.RestoreValidations_normal(input, c);
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
         		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
         		   break;
         	   case 1:
         		   val=  cvr.BNERcallCVR_Validate(input, c);
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
    
    
    
       
        
    public void CancelValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException
    {
    	rval.CancelValidations_normal(input, c);
    	
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
         		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
         		   break;
         	   case 1:
         		   val=  cvr.BNERcallCVR_Validate(input, c);
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
    
    public void DisconnectCVRValidations(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException
    {
    	ExecutionClass_R er=new ExecutionClass_R();
    	Thread.sleep(20000);
    	
    	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
    {
    	if(c.get("CVR").equals("YES"))
        {
       	cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);          
       	String statusCheck = "Open";
        er.thirtyDisconnectTableValidation(statusCheck, input, c);
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
    	    	 
     }
  	
		
    	else
    	{
    		if(c.get("INSIGHTCVR").equals("YES"))
            {
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
    		       	 
         }
    }
    //----------------
    
    public void DisconnectCVRWithoutReleaseValidations(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException
    {
    	ExecutionClass_R er=new ExecutionClass_R();
    	Thread.sleep(20000);
    	c.put("CompletedDisconnected", "YES");
    	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
    {
    	if(c.getValue("CVR").equals("YES"))
        {
       	cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);          
       	String statusCheck = "Open";
        er.thirtyDisconnectTableValidation(statusCheck, input, c);
        threeDaycvrTableValidation(statusCheck,input,c); 
        c.put("CompletedDisconnectedCVR", "YES");
      
        }
    	else
    	{
    		Application.showMessage(" No need of CVR validation");
    		String statusCheck = "Open";
            er.thirtyDisconnectTableValidation(statusCheck, input, c);
          //  runglobalprocesscvr(input, c);
       	   
    	}    	
    	    	 
     }
  	
		
    	else
    	{
    		if(c.getValue("CVR").equals("YES"))
            {
           	cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);              
           	Thread.sleep(10000);
           	String statusCheck = "Open";
           	threeDaycvrTableValidation(statusCheck,input,c); 
            c.put("CompletedDisconnectedCVR", "YES");
            }
        	else
        	{
        		Application.showMessage(" No need of CVR validation");
        		Thread.sleep(10000);
        		String statusCheck = "Open";
              // 	threeDaycvrTableValidation(statusCheck,input,c); 
             
        	}    
    		       	 
         }
    }
    
    
    
    //-----------------------------------
    public void ScheduleDisconnectCVRValidations(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException
    {
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
    
    
    
    public void InstallValidations(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, SQLException, NullPointerException, XPathException, SAXException, InstantiationException, IllegalAccessException
	{
    	RTP_ValidationsClass_UpDown vC = new RTP_ValidationsClass_UpDown();
    	//Application.showMessage("Yamini");
		if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
		{
			vC.demicalls(input, c);
			
		}
		else
		{
			vC.Insightcalls(input, c);
			
		}
	}
  //*******************Insight Calls Validations**************************** 
    
    
    public void Insightcalls_CVR(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException
    { 
    
    	
    	Thread.sleep(40000);
    	RTP_ValidationsClass vC=new RTP_ValidationsClass();
	     LibraryRtp lR= new LibraryRtp();	     
        String val="true";
        int noofCallsValidated=6;
        int i;
        OUTER1:
        for( i=0;i<=noofCallsValidated;i++)
        {
     	   Application.showMessage("The val of I is"+i);
     	   switch(i)
     	   {
     	   case 0:
     		  val=    rval.rtpTestInterface_Validate(input, c);            		  
     		   break;
     	   case 1:
     		   val=  rval.queryLocation_Validate(input,c);    
     		   break;
     	   case 2:
     		   val= rval.getAccount_Validate(input, c);
     		   break;
     	   case 3:
     		   val= processHomeSecurityInfo_Validateinsight(input,c); 
     		   break;
     	   case 4:
     		   val=  rval.orderUpdate_Validate(input,c);
     		   break;
     	   
     	  
     		   default:
     			   break;
     	   }
     	   if(val.equalsIgnoreCase("false"))
     	   {
     		   c.report("Validation calls couldn't found on time as per dynamic think time so its quiting entire flow validation");
     		   break OUTER1;
     	   }
     	   else
     	   {
     		   continue OUTER1;
     	   }
        }
        if(c.get("INSIGHTCVR").equals("YES"))
        {
       	 cvr.processHomeSecurityInfoCVR_Validate(input, c);
          	 cvr.BNERcallCVR_Validate(input, c);
       	 
        }
    	else
    	{
    		Application.showMessage(" No need of CVR validation");
    	}
    }
    
    public void InsightSuspendValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException 
    {
    	rval.InsightSuspendValidations_CLS(input, c);
    	
    	
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
         		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
         		   break;
         	   case 1:
         		   val=  cvr.BNERcallCVR_Validate(input, c);
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
    public void InsightRestoreValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException 
    {
    	//rval.InsightRestoreValidations(input, c);
    	String val1="true";
        int noofCallsValidated1=2;
        int i1;
        OUTER1:
        for( i1=0;i1<=noofCallsValidated1;i1++)
        {
     	   Application.showMessage("The val of I is"+i1);
     	   switch(i1)
     	   {
     	   case 0:
     		  val1=  rC.getAccout_Validate(input, c);      		  
     		   break;
     	   case 1:
     		   val1=  rval.queryLocation_Validate(input,c);
     		   break;
     	  case 2:
    		   val1=  rC.orderUpdate_Validate(input, c);
    		   break;
     	   
     		   default:
     			   break;
     	   }
     	   if(val1.equalsIgnoreCase("false"))
     	   {
     		   c.report("Validation calls couldnt found on time as per dynamic think time so its quiting entire flow validation");
     		   break OUTER1;
     	   }
     	   else
     	   {
     		   continue OUTER1;
     	   }
        }
        
   	    	//------------------------
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
         		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
         		   break;
         	   case 1:
         		   val=  cvr.BNERcallCVR_Validate(input, c);
         		   break;
         	   
         		   default:
         			   break;
         	   }
         	   if(val.equalsIgnoreCase("false"))
         	   {
         		   c.report("Validation calls couldn't found on time as per dynamic think time so its quiting entire flow validation");
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
    
    public void InsightCancelValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException
    {
    	rval.CancelValidations_cls_normal(input, c);
    	
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
         		  val=  cvr.processHomeSecurityInfoCVR_Validate(input, c);        		  
         		   break;
         	   case 1:
         		   val=  cvr.BNERcallCVR_Validate(input, c);
         		   break;
         	   
         		   default:
         			   break;
         	   }
         	   if(val.equalsIgnoreCase("false"))
         	   {
         		   c.report("Validation calls couldn't found on time as per dynamic think time so its quiting entire flow validation");
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
	
}
