import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.xml.xpath.XPathException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class ScheduleUpgradeDowngradeExecutionClass 
{
	LibraryRtp sL =new LibraryRtp();
	LibraryRtp_UpDown lUP= new LibraryRtp_UpDown();
	RTP_ValidationsClass rV=new RTP_ValidationsClass();
    ScheduleUpgradeDowngradeValidation sUD=new ScheduleUpgradeDowngradeValidation();
    RTP_ValidationsClass_UpDown rUD=new RTP_ValidationsClass_UpDown();
   
    ExecutionClass_R ER=new ExecutionClass_R();
    SuspendClass sus=new SuspendClass();
	
	public void Initialization(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
    {
		
      sL.getBaseTime(input, c);
      String T1=sL.getTimeInstance();
      c.put("T1", T1);
      rV.valuesFromAggregrate(input, c);
      sL.IterationLogic30DayDisconnect(input, c);
    	 
    }
	public void ExecutionOrderPartOne(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, InstantiationException, IllegalAccessException
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
		  sUD.createCMSAccountID_Validate(input, c);
		  String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
		  Application.showMessage("Status needs to be updated is ::"+status);
		 // sUD.simulatorScheduleupDown(input, c);
		  lUP.storeCCnum(input, c);
		  
	  }
	  
   	}
	
	public void ExecutionOrderPartTwo(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, ParseException, InstantiationException, IllegalAccessException
	{
		RTP_ValidationsClass_UpDown rvalup=new RTP_ValidationsClass_UpDown();
		 Thread.sleep(2000);
       	 String Sch1= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: getAccountStatus");
       	 String Sch2= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
       	 if(Sch1.equals("ACTIVE")&& (Sch2.equals("ACTIVE")))
       	 {
       	    String Instatus="Open";
       	  //  sUD.createCMSAccountID_Validate(input, c);
       	// sUD.OrderUpdate_tocheckAEE(input, c);
       	rvalup.queryLocation_Validate(input, c);
            //sUD.getWorkOrderValidate(input, c);
       	    String AccountNumber= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: accountId_Simulator");
       	    Application.showMessage("Validationg Schedule table");
            sUD.validateScheduleDisconnect(input, c, Instatus);
            Thread.sleep(3000);
            sUD.getWorkOrderValidate(input, c);
            //Application.showMessage("Updating Schedule Date to Today's Date");
       	    sUD.updateScheduleDisConnect(input, c, AccountNumber);
    	   	String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	      	//sUD.simulatorScheduleupDown(input, c);
    	    //lUP.storeCCnum(input, c);
	        
	     }
       	 
       	 else
       	 {
       		Application.showMessage("Direct Disconnect");
       		sUD.OrderUpdate_tocheckAEE(input, c);
       		// krishna sUD.simGetAccountValidate(input, c);
       		sUD.simGetAccountValidate_CLS(input, c);
       	
       		String UD=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown");
       		if(UD.equalsIgnoreCase("U"))
       		{
       			Application.showMessage("Upgrade calls");
       			//rUD.SimulatorDemicalls(input, c);
       			rUD.SimulatorDemicallsforScheduleUpgradeandDowngrade(input, c);
       			/*String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
    	      	sUD.simulatorScheduleupDown(input, c);
*/       		 
       			
       		}
       		else
       		{
       			Application.showMessage("Downgrade Calls");
       			//rUD.simulatorInsightcalls(input, c);
       			rUD.simulatorInsightcallsforScheduleUpgradeandDowngrade(input, c);
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
		  
       	    rvalup.queryLocation_Validate(input, c);
            //String AccountNumber= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: accountId_Simulator");
       	    Application.showMessage("Validationg Schedule table");
       	    String Instatus="Open";
       	    Application.showMessage("Status needs to be validated is :" +Instatus);
            sUD.validateScheduleDisconnect(input, c, Instatus);
            Thread.sleep(2000);
           
            sUD.getWorkOrderValidate(input, c);
          lUP.storeCCnum(input, c);
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
	public void SchedulePendingChangeValidation(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, ParseException, InstantiationException, IllegalAccessException
	{
		if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("DISCONNECT"))
		{
			ExecutionPendingChangeValidation(input, c);
			
		}
		else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("DISCONNECT1"))
		{
			ExecutionOrderPartTwo_Disconnect( input,c);
		}
	}
	
	//--------------
	
	public void ExecutionPendingChangeValidation(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, ParseException, InstantiationException, IllegalAccessException
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
            sUD.validateScheduleDisconnect(input, c, Instatus);
            Thread.sleep(2000);
            sUD.getWorkOrderValidate(input, c);
            if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown").equalsIgnoreCase("U"))
        	{
            	//Thread.sleep(10000);
            	ne.scheduleCOSrtpTestInterface_Validate(input, c);
            	Thread.sleep(3000);
            	sUD.createCMSAccountID_Validate(input, c);
            	Thread.sleep(3000);
            	rvalup.queryLocation_Validate(input, c);
            	Thread.sleep(3000);
            	cs.getCustomerPermitRequirements_Validate(input, c);
            	Thread.sleep(3000);
                lUP.storeCCnum(input, c);
        	}
            else
            {
            	//Thread.sleep(10000);
            	ne.scheduleCOSrtpTestInterface_Validate(input, c);
            	Thread.sleep(3000);
            	rvalup.queryLocation_Validate(input, c);
            	Thread.sleep(3000);
            	lUP.storeCCnum(input, c);
            }
           
            
            	        	    
     }
	
	
	
	
	
	//----------------------
       	 
	public void ExecutionOrderPartTwo_Disconnect(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, ParseException
	{
		
		    Application.showMessage("---------------Into ExecutionOrderPartTwo_Disconnect-----------");
		    Thread.sleep(5000);
       	    Application.showMessage("Validationg 30Day table");
       	    String Instatus="Open";
       	    Application.showMessage("Status needs to be validated is :" +Instatus);
       	    ER.thirtyDisconnectTableValidationFORScheduleTable(Instatus, input, c);
            Thread.sleep(2000);
            
            	        	    
     }
	
	public void ExecutionOrderPartThree_Disconnect(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, ParseException
	{
		String Instatus;
		    Application.showMessage("---------------Into ExecutionOrderPartThree_Disconnect-----------");
		    Thread.sleep(5000);
       	    //sUD.createCMSAccountID_Validate(input, c);
            //String AccountNumber= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: accountId_Simulator");
       	    Application.showMessage("Validationg Schedule table");
       	    Instatus ="Open";
       	    Application.showMessage("Status needs to be validated is :" +Instatus);
            sUD.validateScheduleDisconnect(input, c, Instatus);
            Thread.sleep(2000);
            sUD.getWorkOrderValidate(input, c);
            Instatus="Scheduled";
       	    Application.showMessage("Status needs to be validated is :" +Instatus);
       	    ER.thirtyDisconnectTableValidationFORScheduleTable(Instatus, input, c);
            //sUD.simulatorScheduleupDown(input, c);
            
            	        	    
     }
       	
	
	
	public void ExecutionOrderPartThree_CompletedChange(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, InstantiationException, IllegalAccessException
    	{
    		
    		    Thread.sleep(2000);
         	    String UD=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown");
           		if(UD.equalsIgnoreCase("U"))
           		{
           			Application.showMessage("Upgrade calls");
           			rUD.SimulatorDemicallsforScheduleUpgradeandDowngrade(input, c);
           			
           		}
           		else
           		{
           			Application.showMessage("Downgrade Calls");
           			rUD.simulatorInsightcallsforScheduleUpgradeandDowngrade(input, c);
           			
           		}
           	 //String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
 	      	 //sUD.simulatorScheduleupDown(input, c);
           		//lUP.storeCCnum(input, c);
           		
           		          		
           		           	 
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
		        ER.thirtyDisconnectTableValidationFORScheduleTable(OpenStatus, input, c);
		        String Instatus="Cancelled";
		        sUD.validateScheduleDisconnect(input, c, Instatus);
		        String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	      	    //sUD.simulatorScheduleupDown(input, c);
		        
	      	
		    }
		    
		    else
		    {
		    	Application.showMessage("Insight Service... Validate disconnect calls");
		        String Instatus="Cancelled";
		        sUD.validateScheduleDisconnect(input, c, Instatus);
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
		       sUD.validateScheduleDisconnect(input, c, Instatus);
		       //sUD.simulatorScheduleupDown(input, c);
		       lUP.storeCCnum(input, c);
		   }
		   
		   
     }
	
	public void ExecutionOrderPartThree_CompletedChangeOther(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, InstantiationException, IllegalAccessException
	{
		
		    Thread.sleep(8000);
		    String Instatus="Completed";
	        sUD.validateScheduleDisconnect(input, c, Instatus);
	        Application.showMessage("------------------------------------------");
            Application.showMessage("Update the Service");
            Application.showMessage("------------------------------------------");
            //sUD.simulatorScheduleupDown(input, c);
            String UD=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown");
       		if(UD.equalsIgnoreCase("U"))
       		{
       			Application.showMessage("Upgrade calls");
       			rUD.SimulatorDemicallsforScheduleUpgradeandDowngrade(input, c);
       			//rUD.SimulatorDemicalls(input, c);
       			
       		}
       		else
       		{
       			Application.showMessage("Downgrade Calls");
       			rUD.simulatorInsightcallsforScheduleUpgradeandDowngrade(input, c);
       			//rUD.simulatorInsightcalls(input, c);
       			
       		}
            lUP.storeCCnum(input, c);
       		
       		          		
       		           	 
}
	
	public void ExecutionOrderPartFour_CancelledChange(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, InstantiationException, IllegalAccessException
	{
		
		    Thread.sleep(4000);
		    sUD.OrderUpdate_tocheckAEE(input, c);
       		lUP.storeCCnum(input, c);
       		//sUD.simulatorScheduleupDown(input, c);
       		
       		          		
       		           	 
}
	//Sruthi_Start
		public void ExecutionOrderPartTwo_RemovedIns(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, ParseException, InstantiationException, IllegalAccessException
		{
			
			 Thread.sleep(2000);
	       	 String Sch1= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: getAccountStatus");
	       	 String Sch2= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	       
	       	 if(Sch1.equals("ACTIVE")&& (Sch2.equals("ACTIVE")))
	       	 {
	       	    String Instatus="Open";
	       	    sUD.createCMSAccountID_Validate(input, c);
	       	 sUD.OrderUpdate_tocheckAEE(input, c);
	            //sUD.getWorkOrderValidate(input, c);
	       	    String AccountNumber= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: accountId_Simulator");
	       	    Application.showMessage("Validationg Schedule table");
	            sUD.validateScheduleDisconnect(input, c, Instatus);
	            Thread.sleep(3000);
	            sUD.getWorkOrderValidate(input, c);
	            //Application.showMessage("Updating Schedule Date to Today's Date");
	       	    sUD.updateScheduleDisConnect(input, c, AccountNumber);
	    	   	String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
		      	//sUD.simulatorScheduleupDown(input, c);
	    	    //lUP.storeCCnum(input, c);
		        
		     }
	       	 
	       	 else
	       	 {
	       		Application.showMessage("Direct Disconnect");
	       		sUD.OrderUpdate_tocheckAEE(input, c);
	       		sUD.simGetAccountValidate(input, c);
	       	
	       		String UD=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown");
	       		if(UD.equalsIgnoreCase("U"))
	       		{
	       			Application.showMessage("Upgrade calls");
	       			//rUD.SimulatorDemicalls(input, c);
	       		
	       			rUD.SimulatorDemicallsforScheduleUpgradeandDowngrade(input, c);
	       			
	       			/*String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	    	      	sUD.simulatorScheduleupDown(input, c);
	*/       		 
	       			
	       		}
	       		else
	       		{
	       			Application.showMessage("Downgrade Calls");
	       			//rUD.simulatorInsightcalls(input, c);
	       		 String service = c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
	       			rUD.simulatorInsightcallsforScheduleUpgradeandDowngrade_RemovedIns(input, c ,service );
	       			//String status= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: status");
	    	      	//sUD.simulatorScheduleupDown(input, c);
	       			
	       			
	       		}
	       		//lUP.storeCCnum(input, c);
	       		
	       		
	       		
	       		 }
	       	 
		} 	
       	
}
