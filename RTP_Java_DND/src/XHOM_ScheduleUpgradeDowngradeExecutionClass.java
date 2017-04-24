import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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


public class XHOM_ScheduleUpgradeDowngradeExecutionClass 
{
	LibraryRtp sL =new LibraryRtp();
	LibraryRtp_UpDown lUP= new LibraryRtp_UpDown();
	RTP_ValidationsClass rV=new RTP_ValidationsClass();
    ScheduleUpgradeDowngradeValidation sUD=new ScheduleUpgradeDowngradeValidation();
    RTP_ValidationsClass_UpDown rUD=new RTP_ValidationsClass_UpDown();
    ExecutionClass_R ER=new ExecutionClass_R();
    SuspendClass sus=new SuspendClass();
    XHOM_DemiInstall_Validations DI=new XHOM_DemiInstall_Validations();
    public Connection connection = null;
    public static String rowmsg= null;
    XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
    XHOM_HomeStream_Validations HS=new XHOM_HomeStream_Validations();
    XHOM_BasicMethods BM=new XHOM_BasicMethods();
    LibraryRtp_UpDown lRp = new LibraryRtp_UpDown();
    XHOM_DemiDisconnect_Validations DD = new XHOM_DemiDisconnect_Validations();
	
	
	public void XHOM_ExecutionOrderPartOne(Object input,ScriptingContext c)throws Exception
	{
	  Thread.sleep(40000);
	  String SN=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName");
	  if(SN.equalsIgnoreCase("Insight"))
	  {
		  Application.showMessage("No Need to fetch the ccentral number");
		  String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
		  Application.showMessage("Status needs to be updated is ::"+status);
		  //lUP.storeCCnum(input, c);
		  //sUD.simulatorScheduleupDown(input, c);
		  
		  
	  }
	  else
	  {
		  //sUD.createCMSAccountID_Validate(input, c);
		  DI.createCMSAccountID_Validate(input, c);
		  
		  String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
		  Application.showMessage("Status needs to be updated is ::"+status);
		 // sUD.simulatorScheduleupDown(input, c);
		  DI.storeCCnum(input, c);
		  
	  }
	  
   	}
	
	public void XHOM_ExecutionOrderPartTwo(Object input,ScriptingContext c)throws Exception
	{
		RTP_ValidationsClass_UpDown rvalup=new RTP_ValidationsClass_UpDown();
		 Thread.sleep(2000);
       	 String Sch1= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: getAccountStatus");
       	 String Sch2= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
       	c.put("AddressDetails","notnull");
       	 if(Sch1.equals("ACTIVE")&& (Sch2.equals("ACTIVE")))
       	 {
       	    String Instatus="Open";
       	 
       	DI.queryLocation_Validate(input, c);
            //sUD.getWorkOrderValidate(input, c);
       	    String AccountNumber= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: accountId_Simulator");
       	    Application.showMessage("Validationg Schedule table");
            BM.validateScheduleDisconnect(input, c, Instatus);
            Thread.sleep(3000);
          //  BM.getWorkOrderValidate(input, c);
            //Application.showMessage("Updating Schedule Date to Today's Date");
       	    //BM.updateScheduleDisConnect(input, c, AccountNumber);
    	   	String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	      	//sUD.simulatorScheduleupDown(input, c);
    	    //lUP.storeCCnum(input, c);
	        
	     }
       	 
       	 else
       	 {
       		Application.showMessage("Direct Disconnect");
       		DI.OrderUpdate_tocheckAEE(input, c);
       		
       		// krishna sUD.simGetAccountValidate(input, c);
       		//sUD.simGetAccountValidate_CLS(input, c);
       		HS.CLSgetAccount_Validate(input, c);
       		
       		
       	
       		String UD=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown");
       		if(UD.equalsIgnoreCase("U"))
       		{
       			Application.showMessage("Upgrade calls");
       			//rUD.SimulatorDemicalls(input, c);
       	XHOM_DemicallsforScheduleUpgradeandDowngrade(input, c);
       			/*String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
    	      	sUD.simulatorScheduleupDown(input, c);
*/       		 
       			
       		}
       		else
       		{
       			Application.showMessage("Downgrade Calls");
       			//rUD.simulatorInsightcalls(input, c);
       			XHOM_InsightcallsforScheduleUpgradeandDowngrade(input, c);
       			//String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
    	      	//sUD.simulatorScheduleupDown(input, c);
       			
       		}
       		//lUP.storeCCnum(input, c);
       		
       		
       		
       		 }
       	 
	}
	
	public void ExecutionOrderPartTwo_Other(Object input,ScriptingContext c)throws Exception
	{
		RTP_ValidationsClass_UpDown rvalup=new RTP_ValidationsClass_UpDown();
		    Application.showMessage("---------------Into ExecutionOrderPartTwo_Other-----------");
		    Thread.sleep(5000);
		 //   sUD.createCMSAccountID_Validate(input, c);
		 //   sUD.OrderUpdate_tocheckAEE(input, c);
		  
       	    DI.queryLocation_Validate(input, c);
            //String AccountNumber= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: accountId_Simulator");
       	    Application.showMessage("Validationg Schedule table");
       	    String Instatus="Open";
       	    Application.showMessage("Status needs to be validated is :" +Instatus);
            BM.validateScheduleDisconnect(input, c, Instatus);
            Thread.sleep(2000);
            BM.XHOM_ScheduleDisconnectAfterUpdatingTableChangingDate(input, c);
            BM.getWorkOrderValidate(input, c);
         DI.storeCCnum(input, c);
            /*String SimUpdateReq=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SimChangeReq");
            Application.showMessage("Simulator change required: " +SimUpdateReq);
            if(SimUpdateReq.equalsIgnoreCase("Yes"))
            {
            	Application.showMessage("Update simulator again");
            	//sUD.simulatorScheduleupDownServiceUpdate(input, c);
            }
            
            else
            {
            	Application.showMessage("No Need of simulator updation");
            }*/
            
            	        	    
     }
	//----------------
	
	 public String addOneToEEPDate(Object input,ScriptingContext c) throws IOException, ParseException 
	    {
	        
	        Application.showMessage("----EEP Date Parsing---");
	        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-YY");
	        Calendar cal = Calendar.getInstance();
	           
	       
	    	String EEPDate= dateFormat.format(cal.getTime());;
	        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	        String s1= EEPDate.replace("T"," ");
	        String s2=s1.replace("Z","");
	       // Application.showMessage("Parsing String to Date" +s2);//
	        Date db=dateFormat.parse(s2);
	        Calendar cal1=Calendar.getInstance();
	        
	        cal1.setTime(db);
	         cal1.add( Calendar.DATE, 1 );
	          java.util.Date utilDate = cal1.getTime();
	      
	        String EDate= dateFormat.format(utilDate);
	        System.out.println("After Replace Edate::"+EDate);
	        
	       DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	       Date Ed=formatter.parse(EDate);
	       
	       String e=formatter.format(Ed);
	       System.out.println("formatted Date::"+e);
	 
	        
	           
	        return e;
	    }
	    
	//----------------
	public void SchedulePendingChangeValidation(Object input,ScriptingContext c)throws Exception
	{
		if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("DISCONNECT"))
		{
			XHOM_ExecutionPendingChangeValidation(input, c);
			
		}
		else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("DISCONNECT1"))
		{
			XHOM_ExecutionOrderPartTwo_Disconnect( input,c);
		}
	}
	
	//--------------
	
	public void XHOM_ExecutionPendingChangeValidation(Object input,ScriptingContext c)throws Exception
	{
		RTP_ValidationsClass_UpDown rvalup=new RTP_ValidationsClass_UpDown();
		RTP_ValidationsClass cs=new RTP_ValidationsClass();
		New_ValidationCalls ne=new New_ValidationCalls();
		    Application.showMessage("---------------Into ExecutionOrderPartTwo_Other-----------");
		    Thread.sleep(50000);
		    c.put("CompletedDisconnected", "NO");
       	    Application.showMessage("Validationg Schedule table");
       	    String Instatus="Open";
       	    Application.showMessage("Status needs to be validated is :" +Instatus);
           BM.validateScheduleDisconnect(input, c, Instatus);
            Thread.sleep(2000);
            BM.getWorkOrderValidate(input, c);
            if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown").equalsIgnoreCase("U"))
        	{
            	//Thread.sleep(10000);
            	XHOM_scheduleCOSrtpTestInterface_Validate(input, c);
            	Thread.sleep(3000);
            	//sUD.createCMSAccountID_Validate(input, c);
            	DI.createCMSAccountID_Validate(input, c);
            	Thread.sleep(3000);
            	//rvalup.queryLocation_Validate(input, c);
            	DI.queryLocation_Validate(input, c);
            	Thread.sleep(3000);
            	//cs.getCustomerPermitRequirements_Validate(input, c);
            	DI.getCustomerPermitRequirements_Validate(input, c);
            	Thread.sleep(3000);
                DI.storeCCnum(input, c);
        	}
            else
            {
            	//Thread.sleep(10000);
            	XHOM_scheduleCOSrtpTestInterface_Validate(input, c);
            	Thread.sleep(3000);
            	//rvalup.queryLocation_Validate(input, c);
            	DI.queryLocation_Validate(input, c);
            	Thread.sleep(3000);
            	DI.storeCCnum(input, c);
            }
           
            
            	        	    
     }
	
	
	
	
	
	//----------------------
       	 
	public void XHOM_ExecutionOrderPartTwo_Disconnect(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, ParseException
	{
		
		    Application.showMessage("---------------Into ExecutionOrderPartTwo_Disconnect-----------");
		    Thread.sleep(5000);
       	    Application.showMessage("Validationg 30Day table");
       	    String Instatus="Open";
       	    Application.showMessage("Status needs to be validated is :" +Instatus);
       	    XHOM_thirtyDisconnectTableValidationFORScheduleTable(Instatus, input, c);
            Thread.sleep(2000);
            
            	        	    
     }
	
	public void XHOM_ExecutionOrderPartThree_Disconnect(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, ParseException
	{
		String Instatus;
		    Application.showMessage("---------------Into ExecutionOrderPartThree_Disconnect-----------");
		    Thread.sleep(5000);
       	    //sUD.createCMSAccountID_Validate(input, c);
            //String AccountNumber= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: accountId_Simulator");
       	    Application.showMessage("Validationg Schedule table");
       	    Instatus ="Open";
       	    Application.showMessage("Status needs to be validated is :" +Instatus);
           BM.validateScheduleDisconnect(input, c, Instatus);
            Thread.sleep(2000);
           // BM.getWorkOrderValidate(input, c);
            Instatus="Scheduled";
       	    Application.showMessage("Status needs to be validated is :" +Instatus);
       	    XHOM_thirtyDisconnectTableValidationFORScheduleTable(Instatus, input, c);
            //sUD.simulatorScheduleupDown(input, c);
            
            	        	    
     }
       	
	public void XHOM_ExecutionOrderPartThree_CompletedChange(Object input,ScriptingContext c)throws Exception
    	{  		
  Thread.sleep(2000);
         	    String UD=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown");
           		if(UD.equalsIgnoreCase("U"))
           		{
           			Application.showMessage("Upgrade calls");
           			XHOM_DemicallsforScheduleUpgradeandDowngrade(input, c);
           			
           		}
           		else
           		{
           			Application.showMessage("Downgrade Calls");
           			XHOM_InsightcallsforScheduleUpgradeandDowngrade(input, c);
           			
           		}
           	 //String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
 	      	 //sUD.simulatorScheduleupDown(input, c);
           		//lUP.storeCCnum(input, c);
           		
           		          		
           		           	 
	}
	
	public void XHOM_ScheduleDisconnect_CompletedChange(Object input,ScriptingContext c)throws Exception
    	{
		BM.rtpTestInterface_Validate(input, c);
    		
    		
		DI.orderUpdate_Validate(input, c, "NEW",c.get("NEWSERVICE").toString());//errorSetOrderSttaus
		
           		          		
           		           	 
	}
	
	//-----------------
	
	public void XHOM_ScheduleDisconnect_ReleasesFromTable(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, Exception
	{
		BM.XHOM_ScheduleDisconnectChangingDate(input, c);
		Thread.sleep(180000);
		 String UD=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown");
    		if(UD.equalsIgnoreCase("U"))
    		{
    			Application.showMessage("Upgrade calls");
    			XHOM_DemicallsforScheduleUpgradeandDowngrade(input, c);
    			
    		}
    		else
    		{
    			Application.showMessage("Downgrade Calls");
    			XHOM_InsightcallsReleasedFromScheduleUpgradeandDowngrade(input, c);
    			
    		}
    		BM.validateScheduleDisconnect(input, c, "Completed");
	}
	
	public void ExecutionOrderPartThree_CompletedDisc(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, InstantiationException, IllegalAccessException
	{
		
		    Thread.sleep(8000);
		   // sUD.simGetAccountValidate(input, c);
		    String statusGetAcc=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
		    
		   if(statusGetAcc.equals("ACTIVE"))
		   {
		    	
		   
		    String scenarioName=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName");
		    if(scenarioName.equalsIgnoreCase("Demi"))
		    {
		    	
		    	Application.showMessage("Demi Service: Validate 30Day table" );
		        String OpenStatus="Open";
		        XHOM_thirtyDisconnectTableValidationFORScheduleTable(OpenStatus, input, c);
		        String Instatus="Cancelled";
		        BM.validateScheduleDisconnect(input, c, Instatus);
		        String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	      	    //sUD.simulatorScheduleupDown(input, c);
		        
	      	
		    }
		    
		    else
		    {
		    	Application.showMessage("Insight Service... Validate disconnect calls");
		        String Instatus="Cancelled";
		        BM.validateScheduleDisconnect(input, c, Instatus);
		        String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	      	    //sUD.simulatorScheduleupDown(input, c);
		        
		    }
		    
		   }
		   else
		   {
			   Application.showMessage("---------------------------------------------------------------------------");
			   Application.showMessage("No need of 30Day table validation as GetAccount status is other than ACTIVE");
			   Application.showMessage("---------------------------------------------------------------------------");
			   String Instatus="Open";
		       BM.validateScheduleDisconnect(input, c, Instatus);
		       //sUD.simulatorScheduleupDown(input, c);
		       DI.storeCCnum(input, c);
		   }
		   
		   
     }
	
	public void XHOM_ExecutionOrderPartThree_CompletedChangeOther(Object input,ScriptingContext c)throws Exception
	{
		
		    Thread.sleep(8000);
		    String Instatus="Completed";
	        BM.validateScheduleDisconnect(input, c, Instatus);
	        Application.showMessage("------------------------------------------");
            Application.showMessage("Update the Service");
            Application.showMessage("------------------------------------------");
            //sUD.simulatorScheduleupDown(input, c);
            String UD=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown");
       		if(UD.equalsIgnoreCase("U"))
       		{
       			Application.showMessage("Upgrade calls");
       			XHOM_DemicallsforScheduleUpgradeandDowngrade(input, c);
       			//rUD.SimulatorDemicalls(input, c);
       			
       		}
       		else
       		{
       			Application.showMessage("Downgrade Calls");
       			XHOM_InsightcallsforScheduleUpgradeandDowngrade(input, c);
       			//rUD.simulatorInsightcalls(input, c);
       			
       		}
            DI.storeCCnum(input, c);
       		
       		          		
       		           	 
}
	
	public void XHOM_ExecutionOrderPartFour_CancelledChange(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, InstantiationException, IllegalAccessException
	{
		
		    Thread.sleep(4000);
		    DI.OrderUpdate_tocheckAEE(input, c);
       		DI.storeCCnum(input, c);
       		//sUD.simulatorScheduleupDown(input, c);
       		
       		          		
       		           	 
}
	//Sruthi_Start
		public void ExecutionOrderPartTwo_RemovedIns(Object input,ScriptingContext c)throws Exception
		{
			
			 Thread.sleep(2000);
	       	 String Sch1= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: getAccountStatus");
	       	 String Sch2= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	       
	       	 if(Sch1.equals("ACTIVE")&& (Sch2.equals("ACTIVE")))
	       	 {
	       	    String Instatus="Open";
	       	   DI.createCMSAccountID_Validate(input, c);
	       	 DI.OrderUpdate_tocheckAEE(input, c);
	            //sUD.getWorkOrderValidate(input, c);
	       	    String AccountNumber= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: accountId_Simulator");
	       	    Application.showMessage("Validationg Schedule table");
	           BM.validateScheduleDisconnect(input, c, Instatus);
	            Thread.sleep(3000);
	            BM.getWorkOrderValidate(input, c);
	            //Application.showMessage("Updating Schedule Date to Today's Date");
	       	    BM.updateScheduleDisConnect(input, c, AccountNumber);
	    	   	String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
		      	//sUD.simulatorScheduleupDown(input, c);
	    	    //lUP.storeCCnum(input, c);
		        
		     }
	       	 
	       	 else
	       	 {
	       		Application.showMessage("Direct Disconnect");
	       		DI.OrderUpdate_tocheckAEE(input, c);
	       		HS.CLSgetAccount_Validate(input, c);
	       	
	       		String UD=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown");
	       		if(UD.equalsIgnoreCase("U"))
	       		{
	       			Application.showMessage("Upgrade calls");
	       			//rUD.SimulatorDemicalls(input, c);
	       		
	       			XHOM_DemicallsforScheduleUpgradeandDowngrade(input, c);
	       			
	       			/*String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	    	      	sUD.simulatorScheduleupDown(input, c);
	*/       		 
	       			
	       		}
	       		else
	       		{
	       			Application.showMessage("Downgrade Calls");
	       			//rUD.simulatorInsightcalls(input, c);
	       		 String service = c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
	       			XHOM_InsightcallsforScheduleUpgradeandDowngrade_RemovedIns(input, c ,service );
	       			//String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	    	      	//sUD.simulatorScheduleupDown(input, c);
	       			
	       			
	       		}
	       		//lUP.storeCCnum(input, c);
	       		
	       		
	       		
	       		 }
	       	 
		} 	
		public void XHOM_DemicallsforScheduleUpgradeandDowngrade(Object input,
				ScriptingContext c) throws Exception {
			// TODO Auto-generated method stub
			RTP_SimulatorClass_UpDown sim= new RTP_SimulatorClass_UpDown();
		    RTP_ValidationsClass rV=new RTP_ValidationsClass();
			List<String> DisUpgradeIssues=new ArrayList<String>();
			XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
					BM.rtpTestInterface_Validate(input, c);//xhsEventIssues
					DisUpgradeIssues.add(c.get("xhsEventIssues").toString());
					DD.processHomeSecurityInfo_Validate_Dis(input, c);//DDSInstall
					DisUpgradeIssues.add(c.get("DDSInstall").toString());
					HS.SuspendAccount_Validate(input, c);//suspendAccountIssues
					DisUpgradeIssues.add(c.get("suspendAccountIssues").toString());
					HS.deactivateAccount_Validate_CLS(input, c);//deactivateAccountIssues
					DisUpgradeIssues.add(c.get("deactivateAccountIssues").toString());
					//DD.DisconnectAccount_CANCEL_Validate(input, c);//DisconnectAccountIssues
					//DisUpgradeIssues.add(c.get("DisconnectAccountIssues").toString());
					DI.queryLocation_Validate(input, c);//queryLocationIssues
					DisUpgradeIssues.add(c.get("queryLocationIssues").toString());
					DI.processHomeSecurityInfo_Validate(input, c);//DDSDisIssues
					DisUpgradeIssues.add(c.get("DDSDisIssues").toString());
					DI.createCMSAccountID_Validate(input, c);
					HS.CLS_createAccount_ErrorValidate(input, c);//addAccountErrorIssues
					 DI.responderInfo_Validate(input,c);
					   DI.SetAccountAuthorityInformation_Validate(input, c);
					   DI.modifyHomeSecurity_Validate(input, c);
					   DisUpgradeIssues.add(c.get("addAccountErrorIssues").toString());
					DI.ErrororderUpdate_Validate(input, c, "NEW",c.get("NEWSERVICE").toString(), "UCNTRL-RESP-CODE-UCE-15122");//errorSetOrderSttaus
					DisUpgradeIssues.add(c.get("errorSetOrderSttaus").toString());
		
		    
			
		}
 
		public void XHOM_InsightcallsforScheduleUpgradeandDowngrade(Object input,
				ScriptingContext c) throws Exception {
			// TODO Auto-generated method stub
			RTP_SimulatorClass_UpDown sim= new RTP_SimulatorClass_UpDown();
		    RTP_ValidationsClass rV=new RTP_ValidationsClass();
			List<String> DisUpgradeIssues=new ArrayList<String>();
			XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
					BM.rtpTestInterface_Validate(input, c);//xhsEventIssues
					DisUpgradeIssues.add(c.get("xhsEventIssues").toString());
					DD.processHomeSecurityInfo_Validate_Dis(input, c);//DDSInstall
					DisUpgradeIssues.add(c.get("DDSInstall").toString());
					HS.SuspendAccount_Validate(input, c);//suspendAccountIssues
					DisUpgradeIssues.add(c.get("suspendAccountIssues").toString());
					HS.deactivateAccount_Validate_CLS(input, c);//deactivateAccountIssues
					DisUpgradeIssues.add(c.get("deactivateAccountIssues").toString());
					DD.DisconnectAccount_CANCEL_Validate(input, c);//DisconnectAccountIssues
					DisUpgradeIssues.add(c.get("DisconnectAccountIssues").toString());
					DI.queryLocation_Validate(input, c);//queryLocationIssues
					DisUpgradeIssues.add(c.get("queryLocationIssues").toString());
					DI.processHomeSecurityInfo_Validate(input, c);//DDSDisIssueos
					DisUpgradeIssues.add(c.get("DDSDisIssues").toString());
				//	DI.createCMSAccountID_Validate(input, c);
					HS.CLS_createAccount_ErrorValidate(input, c);//addAccountErrorIssues
					// DI.responderInfo_Validate(input,c);
					//   DI.SetAccountAuthorityInformation_Validate(input, c);
					//   DI.modifyHomeSecurity_Validate(input, c);
					   DisUpgradeIssues.add(c.get("addAccountErrorIssues").toString());
					DI.ErrororderUpdate_Validate(input, c, "NEW",c.get("NEWSERVICE").toString(), "UCNTRL-RESP-CODE-UCE-15122");//errorSetOrderSttaus
					DisUpgradeIssues.add(c.get("errorSetOrderSttaus").toString());
		
		    
			
		}
 
		public void XHOM_InsightcallsReleasedFromScheduleUpgradeandDowngrade(Object input,
				ScriptingContext c) throws Exception {
			
			
			List<String> DisDowngradeIssues=new ArrayList<String>();
			XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
					BM.rtpTestInterface_Validate(input, c);//xhsEventIssues
					DisDowngradeIssues.add(c.get("xhsEventIssues").toString());
					DD.processHomeSecurityInfo_Validate_Dis(input, c);//DDSInstall
					DisDowngradeIssues.add(c.get("DDSInstall").toString());
					HS.SuspendAccount_Validate(input, c);//suspendAccountIssues
					DisDowngradeIssues.add(c.get("suspendAccountIssues").toString());
					HS.deactivateAccount_Validate_CLS(input, c);//deactivateAccountIssues
					DisDowngradeIssues.add(c.get("deactivateAccountIssues").toString());
					DD.DisconnectAccount_CANCEL_Validate(input, c);//DisconnectAccountIssues
					DisDowngradeIssues.add(c.get("DisconnectAccountIssues").toString());
					DI.queryLocation_Validate(input, c);//queryLocationIssues
					DisDowngradeIssues.add(c.get("queryLocationIssues").toString());
					DI.processHomeSecurityInfo_Validate(input, c);//DDSDisIssues
					DisDowngradeIssues.add(c.get("DDSDisIssues").toString());
					
					HS.CLS_createAccount_ErrorValidate(input, c);//addAccountErrorIssues
					DisDowngradeIssues.add(c.get("addAccountErrorIssues").toString());
					DI.ErrororderUpdate_Validate(input, c, "NEW",c.get("NEWSERVICE").toString(), "UCNTRL-RESP-CODE-UCE-15122");//errorSetOrderSttaus
					DisDowngradeIssues.add(c.get("errorSetOrderSttaus").toString());
				
			
		} 
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//------------------------
		public void XHOM_orderUpdate_ValidateScheduleUpgradeandDowngrade(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
		{
			 // Think time in JVM [waits for 10 secs here]
		     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
		     LibraryRtp lr=new LibraryRtp();
			 DBCursor  rs;
			 DBObject obj = null;
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
				//rs=lr.reduceThinkTimeRTP(input, c);
				rs=XH.XHOM_reduceThinkTimeRTP(input, c);
				
				while (rs.hasNext())
		        {
		       obj=rs.next();
		          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
		          {
		 rowmsg=((BasicBSONObject) obj).getString("_id");
		 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
	     {
	     
	         Application.showMessage("Your Recieve XML is NULL \n");
	         
	         String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
	         Application.showMessage("Your Recieve XML is::\n"+senddata);           
			           
			          	           
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
			}
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}
		 }
		
		public void XHOM_scheduleCOSrtpTestInterface_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	    {
	                                 LibraryRtp  lC= new LibraryRtp();
	                                 //ResultSet  rs;
	                                 int callFound=0, v1=0;
	                                 String xmldata1 ="receive_data";
	             String xmldata2 ="SEND_DATA";  
	             Thread.sleep(5000);
	             String Time= c.get("BaseTime").toString();
	             c.setValue("RTPTestInterface","true");
	             DBCursor  rs;
	             DBObject obj = null;
	             lC.findThinktimeOperationRTP(input, c);
	             Application.showMessage("----------------------------------------------------------");
	             Application.showMessage("************rtpTestInterface_Validate Function************");    
	             Application.showMessage("----------------------------------------------------------");
	             c.setValue("RTPTestInterface","false"); 
	                                try
	                                {
	                                                
	                                                //Lp.Opconnection();
	                                                //Class.forName("oracle.jdbc.driver.OracleDriver");
	                                                //connection = DriverManager.getConnection("jdbc:oracle:thin:@10.252.115.36:1521:PSYMDEV1", "CWDEVOP","CWDEVOP");                         
	                                	//rs=lC.reduceThinkTimeRTP(input, c);
	                                	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
	                        while (rs.hasNext() )
	                        {
	                             /* Application.showMessage("RTPTestInterface msgid= "+ rs.getString(1));
	                              String rowmsg = rs.getString(1);           
	                              if(rs.getBlob("receive_data")==null)*/
	                        	obj=rs.next();
	                            if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
	                            {
	                   rowmsg=((BasicBSONObject) obj).getString("_id");
	                   if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
	                              {
	                           
	                               // String senddata =lC.extractXml(rs,xmldata2); 
	                	   
	                	   Application.showMessage("Your Recieve XML is NULL \n");
	                       String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
	                       Application.showMessage("Your Recieve XML is::\n"+senddata);
	                       
	                                            String accountNumber_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/@accountNumber");
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
	                                                 String locationId_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Location/@locationId");
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
	                                                                                                 String PrimaryEmail_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/PrimaryEmail");
	                                                                                                 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
	                                         c.setValue("emailAddress", PrimaryEmail_rtpTest);
	                                                                                                                            
	                                                                                                                         String FirstName_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/FirstName");
	                                                                                                                         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
	                                                                                                                         c.setValue("FirstName", FirstName_rtpTest); 
	                                                                                                                         
	                                                                                                                         String LastName_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/LastName");
	                                                                                                                         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
	                                                                                                                         c.setValue("LastName", LastName_rtpTest); 
	                                                                                                                         
	                                                                                                                         String OrderStatus_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderStatus");
	                                                                                                                         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
	                                                                                                                            
	                                                                                                                         String OrderType_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderType");
	                                                                                                                         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
	                                                                                                                         
	                                                                                                                         String RescheduleIndicator_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
	                                                                                                                         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
	                                                                                                                         String EventSource= lC.nodeFromKey(senddata,"<EventSource>","</EventSource>");
	                                                                                                                         c.setValue("sEventSource", EventSource);
	                                                                                                                         Application.showMessage("EventSource is::"+c.getValue("sEventSource")); 
	                                                                                                                         
	                                                                                                                         String city=  lC.nodeFromKey(senddata,"<City>","</City>");
	                                                                                                                         c.put("Ecity",city);
	                                                                                                                         c.setValue("City",city);
	                                                                                                                         Application.showMessage("Ecity is::"+c.get("Ecity")); 
	                                                                                                                         
	                                                                                                                         String Zipcode=  lC.nodeFromKey(senddata,"<Zipcode>","</Zipcode>");
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
	                         /*   else if(rs.getBlob("SEND_DATA")==null)
	                            {
	                                
	                                String recievedata=lC.extractXml(rs,xmldata1);*/
	                   else if(((BasicBSONObject) obj).getString("REQUEST")==null)
	                   {
	                       Application.showMessage("Your SEND XML is NULL \n");             
	                       String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
	                       Application.showMessage("RECIEVE XML is ::\n"+recievedata);                       
	                   
	                                            String accountNumber_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/@accountNumber");
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
	                                                 String locationId_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Location/@locationId");
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
	                                                                                                 String PrimaryEmail_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
	                                                                                                 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
	                                                                                                 c.setValue("emailAddress", PrimaryEmail_rtpTest);
	                                                                                                 
	                                                                                                                         String FirstName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/FirstName");
	                                                                                                                         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
	                                                                                                                         c.setValue("FirstName", FirstName_rtpTest);
	                                                                                                                         
	                                                                                                                         String LastName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/LastName");
	                                                                                                                         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
	                                                                                                                         c.setValue("LastName", LastName_rtpTest); 
	                                                                                                                         
	                                                                                                                         String OrderStatus_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
	                                                                                                                         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
	                                                                                                                            
	                                                                                                                         String OrderType_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
	                                                                                                                         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
	                                                                                                                         
	                                                                                                                         String RescheduleIndicator_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
	                                                                                                                         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
	                                                                                                                         String EventSource= lC.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
	                                                                                                                         c.setValue("sEventSource", EventSource);
	                                                                                                                         
	                                                                                                                         String city=  lC.nodeFromKey(recievedata,"<City>","</City>");
	                                                                                                                         c.put("Ecity",city);
	                                                                                                                         Application.showMessage("Ecity is::"+c.get("Ecity")); 
	                                                                                                                         
	                                                                                                                         String Zipcode=  lC.nodeFromKey(recievedata,"<City>","</City>");
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
	                                               // String recievedata=lC.extractXml(rs,xmldata1);
	                                            	String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
	                                                String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
	                                                Application.showMessage("Your Recieve XML is::\n"+senddata);
	                                                Application.showMessage("RECIEVE XML is ::\n"+recievedata);
	                                                                         
	                                                            String accountNumber_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/@accountNumber");
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
	                                                                 String locationId_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Location/@locationId");
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
	                                                                                                                 String PrimaryEmail_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
	                                                                                                                 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
	                                                                                                                 c.setValue("emailAddress", PrimaryEmail_rtpTest);
	                                                                                                                 
	                                                                                                                                         String FirstName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/FirstName");
	                                                                                                                                         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
	                                                                                                                                         c.setValue("FirstName", FirstName_rtpTest);
	                                                                                                                                         
	                                                                                                                                         String LastName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/LastName");
	                                                                                                                                         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
	                                                                                                                                         c.setValue("LastName", LastName_rtpTest); 
	                                                                                                                                         
	                                                                                                                                         String OrderStatus_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
	                                                                                                                                         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
	                                                                                                                                            
	                                                                                                                                         String OrderType_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
	                                                                                                                                         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
	                                                                                                                                         
	                                                                                                                                         String RescheduleIndicator_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
	                                                                                                                                         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
	                                                                                                                                         String EventSource= lC.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
	                                                                                                                                         c.setValue("sEventSource", EventSource);
	                                                                                                                                         
	                                                                                                                                         String city=  lC.nodeFromKey(recievedata,"<City>","</City>");
	                                                                                                                                         c.put("Ecity",city);
	                                                                                                                                         Application.showMessage("Ecity is::"+c.get("Ecity")); 
	                                                                                                                                         
	                                                                                                                                         String Zipcode=  lC.nodeFromKey(recievedata,"<City>","</City>");
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
	                        
	                      //   st.close();
	                        }
	                                }
	                                }
	                                catch (SQLException e)
	                                {
	                                    System.out.println("Connection Failed! Check output console");
	                                                e.printStackTrace();
	                                                return;
	                                }
	                }
	    
		public void XHOM_thirtyDisconnectTableValidationFORScheduleTable(String Instatus,Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
        {
			Thread.sleep(9000);
			Map<String,String> getMapData=new HashMap <String,String>();
				getMapData=BM.XHOM_connectThirtyDayDisconnectDB(input, c);
				String status=getMapData.get("status");              
				Application.showMessage("The status in validation process is ::"+status);
				String date= getMapData.get("date");
				Application.showMessage("The date in validation process is ::"+date);
				String service= getMapData.get("service");
				Application.showMessage("The service in validation process is ::"+service);
				Boolean p1=lRp.validationPointIgnoreCase(status, Instatus, input, c);
				String InService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
				//Boolean p2=lRp.validationPoint(service, InService, input, c);


}
		public void XHOM_InsightcallsforScheduleUpgradeandDowngrade_RemovedIns(Object input,
				ScriptingContext c , String service) throws Exception {
			// TODO Auto-generated method stub
			RTP_SimulatorClass_UpDown sim= new RTP_SimulatorClass_UpDown();
		    Thread.sleep(4000);
		    BM.rtpTestInterface_Validate(input, c);     
		    DI.queryLocation_Validate(input,c);
		 
		    DI.processHomeSecurityInfo_Validate(input, c);
		    HS.deactivateAccount_Validate_CLS(input, c);
		    //sim.simGetAccountValidate(input, c);
		   // getAccount_Validate(input, c);
		    DI.processHomeSecurityInfo_Validate(input,c);       
		    HS.CLS_createAccount_Validate(input, c)  ;         
		    XHOM_orderUpdate_ValidateScheduleUpgradeandDowngrade_RemovedIns(input,c,service);
		    //Lp.storeCCnum(input, c);
			
		} 
		public void XHOM_orderUpdate_ValidateScheduleUpgradeandDowngrade_RemovedIns(Object input, ScriptingContext c , String service) throws InterruptedException, IOException, ClassNotFoundException 
		{
			 // Think time in JVM [waits for 10 secs here]
		     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
		     LibraryRtp lr=new LibraryRtp();
		     DBCursor  rs;
		     DBObject obj = null;
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
				rs=XH.XHOM_reduceThinkTimeRTP(input, c);
		         while (rs.hasNext())
		         {
		        	
		        
		        	 obj=rs.next();
		             if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
		             {
		    rowmsg=((BasicBSONObject) obj).getString("_id");
		    
		    if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
	           {
	           
	               Application.showMessage("Your Recieve XML is NULL \n");
	               
	               String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
	               Application.showMessage("Your Recieve XML is::\n"+senddata);           
			           
			          	           
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
		public void ScheduleorderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
		{
			Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
		     DBCursor  rs;
		     DBObject obj = null;
			 int callFound=0,v1=0,v2=0,v3=0,v4=0;
			// String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		    
		     Application.showMessage("-------------------------------------------------------------");
		     Application.showMessage("***********OrderUpdate_Validate Function**************");       
		     Application.showMessage("-------------------------------------------------------------");
		         
			try
			{
				/*Statement st =lC. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
		        */
				
				rs=XH.XHOM_reduceThinkTimeRTP(input, c);
				
				while (rs.hasNext())
		         {
					obj=rs.next();
			          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
			          {
			 rowmsg=((BasicBSONObject) obj).getString("_id");
		        
		           
			 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
	           {
	           
	               Application.showMessage("Your Recieve XML is NULL \n");
	               
	               String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
	               Application.showMessage("Your Recieve XML is::\n"+senddata);           
			           
			          	           
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

			 	            
			 	            
			 	            if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("DEMI"))
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
					            	 if(Service.equals(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE").trim()))
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
			 		            	 if(Service.equals(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE").trim()))
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
		        // st.close();
		         
		        //Lp.reportingToExcel(Object , ScriptingContext);
			}
				
			}
			
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}
		 }


}
