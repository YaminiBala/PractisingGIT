import java.io.IOException;
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

import javax.xml.xpath.XPathException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.RuleContext;
import com.parasoft.api.ScriptingContext;


public class CVR_ScheduleUpdown 
{
	List<Integer> lschangecount=new ArrayList<Integer>();
	List<Integer> lsdisconnectcount=new ArrayList<Integer>();
	List<Integer> lsinstallcount=new ArrayList<Integer>();
	List<Integer> lschangecountvalue=new ArrayList<Integer>();
	List<Integer> lsDisconnectcountvalue=new ArrayList<Integer>();
	List<Integer> lsinstallcountvalue=new ArrayList<Integer>();
	List<String> lsinstallpn=new ArrayList<String>();
	List<String> lsdisconnectpn=new ArrayList<String>();
	List<String> lschangepn=new ArrayList<String>();
	
	
	int NoChange=0;
	int install=0;
	int Disconnect=0;
	
public void cvr_scheduleupdown_validation(Object input, ScriptingContext c) throws ClassNotFoundException, NullPointerException, InstantiationException, IllegalAccessException, IOException, InterruptedException, SQLException, SAXException, XPathException
{
	ScheduleUpgradeDowngradeExecutionClass ec = new ScheduleUpgradeDowngradeExecutionClass();
	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: WO").equals("2"))
	{
	//ec.ExecutionOrderPartThree_CompletedChange(input, c);
	Application.showMessage("CVR VALUE IS"+c.get("CVR"));
	Application.showMessage("INSIGHTCVR VALUE IS"+c.get("INSIGHTCVR"));
	
	if(c.get("CVR").equals("YES") || c.get("INSIGHTCVR").equals("YES"))
    {
		DDSCVR_Validate(input, c);
		//scheduleBNERcallCVR_Validate(input,  c);
    }
	else
	{
		Application.showMessage("No need of CVR Validation");
	}
	}
	else
	{
		ec.ExecutionOrderPartThree_CompletedChange(input, c);
		Application.showMessage("CVR VALUE IS"+c.get("CVR"));
		Application.showMessage("INSIGHTCVR VALUE IS"+c.get("INSIGHTCVR"));
		
		if(c.get("CVR").equals("YES") || c.get("INSIGHTCVR").equals("YES"))
	    {
			DDSCVR_Validate(input, c);
			//scheduleBNERcallCVR_Validate(input,  c);
	    }
		else
		{
			Application.showMessage("No need of CVR Validation");
		}
	}
}

//-----


public void cvr_scheduleupdown_PendingChangevalidation(Object input, ScriptingContext c) throws ClassNotFoundException, NullPointerException, InstantiationException, IllegalAccessException, IOException, InterruptedException, SQLException, SAXException, XPathException
{
	CVR_AllFlows_ValidationCalls val1=new CVR_AllFlows_ValidationCalls();
	c.put("CompletedDisconnected","NO");
	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("DISCONNECT"))
	{
		
	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: RemoveInDis").equalsIgnoreCase("no"))
	{
		changingdate(input,  c);
		Thread.sleep(3000);
	val1.runglobalprocesscvr(input, c);
	Thread.sleep(300000); 
	cvr_ReleasefromScheduleDisconnectableValidate(input, c);
	SchedulePCCVRValidate( input, c,"PendingInstall","Open");
	}
	else
	{
		
	}
	}
	else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("DISCONNECT1"))
	{
		
	}
		
	
	
	}


//--
public void cvr_scheduleupdown_CompletedChangevalidation(Object input, ScriptingContext c) throws ClassNotFoundException, NullPointerException, InstantiationException, IllegalAccessException, IOException, InterruptedException, SQLException, SAXException, XPathException
{
	CVR_AllFlows_ValidationCalls val1=new CVR_AllFlows_ValidationCalls();
	RTP_ValidationsClass_UpDown rv1=new RTP_ValidationsClass_UpDown();
	New_ValidationCalls ne1=new New_ValidationCalls();
	RTP_ValidationsClass rv=new RTP_ValidationsClass();
	ExecutionClass_R er=new ExecutionClass_R();
	ChangeOfServiceClass CS = new ChangeOfServiceClass();
	LibraryRtp  lC= new LibraryRtp ();
	CVR_ValidationCalls  cvr=new CVR_ValidationCalls ();
	if(c.get("CompletedDisconnected").equals("NO"))
	{
	if(c.getValue("RemovedInstall").equals("true"))
	{
		
        Thread.sleep(2000);
        Application.showMessage("---Connecting to DB Table------");
		val1.ScheduleDisconnectCVRValidations(input, c);
		
	}
	else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: STEP").equalsIgnoreCase("disconnect1"))
	{
		Thread.sleep(2000);
        Application.showMessage("---Connecting to DB Table------");
		val1.ScheduleDisconnectCVRValidations(input, c);
	}
	else
	{
		rv1.queryLocation_Validate(input,c);
	   	   Thread.sleep(3000);
	   	ne1.ScheduleorderUpdate_Validate(input, c);
	   	   Thread.sleep(3000);
	   	SchedulePCCVRValidate( input, c,"Active","Open");
	
	}
	}
	else
	{
		if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: logics").equalsIgnoreCase("logic1"))
		{
		ne1.scheduleCOSrtpTestInterface_Validate(input, c);
		cvr_ReleasefromScheduleDisconnectableValidate(input, c);
		er.thirtyDisconnectTableValidation("Completed", input, c);
		SchedulePCCVRValidate( input, c,"PendingInstall","Open");
		if( c.get("CompletedDisconnectedCVR").equals("YES"))
        {
        	lC.getBaseTime(input, c);
        	cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
         	cvr.DatasourceBNERcallCVR_Validate(input, c); 
        }
		}
		else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: logics").equalsIgnoreCase("logic2"))
		{
			//rv.queryLocation_Validate(input,c);
			er.thirtyDisconnectTableValidationSimulator("Cancelled", input, c);
			ne1.scheduleCOSrtpTestInterface_Validate(input, c);
			Map<String, String> ResultMap = lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
		     c.setValue("ExistingGroup",ResultMap.get("group"));
		     c.setValue("ExistingService", ResultMap.get("service"));
		     c.setValue("IsMultiExist", ResultMap.get("IsMulti"));
		     ResultMap=lC.splitter(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE"));
		     c.setValue("NewGroup",ResultMap.get("group"));
		     c.setValue("NewService", ResultMap.get("service"));
		    // c.setValue("IsSingleGroup", ResultMap.get("IsSingle"));
		     c.setValue("IsMultiNew", ResultMap.get("IsMulti"));
		     
		     
		     Application.showMessage("NewGroup::"+c.getValue("NewGroup"));
		     Application.showMessage("NewService::"+c.getValue("NewService"));
		     Application.showMessage("ExistingGroup::"+c.getValue("ExistingGroup"));
		     Application.showMessage("ExistingService::"+c.getValue("ExistingService"));
		     Application.showMessage("Have both Group and Service in Existing::"+c.getValue("IsMultiExist"));
		     Application.showMessage("Have both Group and Service in New:"+c.getValue("IsMultiNew"));
           // krishna CS.cosLogicFlow(input, c); 
            CS.cosLogicFlow_CLS(input, c, c.getValue("ExistingService"),c.getValue("NewService"));
            ne1.orderUpdateCos_Validate(input, c);
            SchedulePCCVRValidate( input, c,"PendingInstall","Open");
            er.thirtyDisconnectTableValidationSimulator("Cancelled", input, c);
            if( c.get("CompletedDisconnectedCVR").equals("YES"))
            {
            	lC.getBaseTime(input, c);
            	cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
             	cvr.DatasourceBNERcallCVR_Validate(input, c); 
            }
            
		}
		else if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: logics").equalsIgnoreCase("logic3"))
		{
			//rv.CosValidationssameservices(input, c);
			Thread.sleep(3000);
			er.thirtyDisconnectTableValidationSimulator("Cancelled", input, c);
			SchedulePCCVRValidate( input, c,"PendingInstall","Open");
			 if( c.get("CompletedDisconnectedCVR").equals("YES"))
	            {
	            	lC.getBaseTime(input, c);
	            	cvr.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
	             	cvr.DatasourceBNERcallCVR_Validate(input, c); 
	            }
		}
	}
	}


//---

public void cvr_ReleasefromScheduleDisconnectableValidate(Object input, ScriptingContext c) throws ClassNotFoundException, NullPointerException, InstantiationException, IllegalAccessException, IOException, InterruptedException, SQLException, SAXException, XPathException
{
	CVR_AllFlows_ValidationCalls val1=new CVR_AllFlows_ValidationCalls();
	RTP_ValidationsClass_UpDown rv=new RTP_ValidationsClass_UpDown();
	RTP_SimulatorClass_UpDown sc=new RTP_SimulatorClass_UpDown();
	SuspendClass sus=new SuspendClass();
	RTP_SimulatorClass dis=new RTP_SimulatorClass();
	New_ValidationCalls ne=new New_ValidationCalls();
	
	   rv.queryLocation_Validate(input,c);
	   Thread.sleep(3000);
	   sc.processHomeSecurityInfo_Validate(input, c);
	   Thread.sleep(3000);
	   sus.SuspendAccount_Validate(input, c); 
 	Thread.sleep(3000);
	   
	   sc.deactivateAccount_Validate_CLS(input, c);
	   Thread.sleep(3000);
	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown").equalsIgnoreCase("U"))
	{
		 
	   	   
	   	   rv.processHomeSecurityInfo_Validate(input, c);
	   	   Thread.sleep(3000);
	   	   
	   	   sc.createAccount_ValidateScheduleDemiSim_CLS(input, c);
	   	   Thread.sleep(3000);
	   	   rv.SetAccountBasicInformation_Validate(input, c);
	   	 Thread.sleep(3000);
	   	   rv.responderInfo_Validate(input, c);
	   	 Thread.sleep(3000);
	   	   rv.SetAccountAuthorityInformation_Validate(input, c);
	   	 Thread.sleep(3000);
	   	   ne.ScheduleorderUpdate_Validate(input, c);
	   	    Thread.sleep(3000);
	   	   
	}
	else
	{
		
   	  
   	   dis.DisconnectAccount_CANCEL_Validate(input, c);
   	   Thread.sleep(3000);
   	   rv.processHomeSecurityInfo_Validate(input, c);
   	   Thread.sleep(3000);
   	sc.createAccount_ValidateScheduleInsight_CLS(input, c);
   
	   Thread.sleep(3000);
	   ne.ScheduleorderUpdate_Validate(input, c);
  	    Thread.sleep(3000);
   	   
       
	}
}
//----------


public void SchedulePCCVRValidate(Object input, ScriptingContext c,String cvrStatus,String cvrtablestatus) throws ClassNotFoundException, NullPointerException, InstantiationException, IllegalAccessException, IOException, InterruptedException, SQLException, SAXException, XPathException
{		
	CVR_AllFlows_ValidationCalls val1=new CVR_AllFlows_ValidationCalls();
   	  
   	   if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: Channel").equalsIgnoreCase("DDP"))
   	   {
   		   if(c.getValue("CVRValueIDDDP").equalsIgnoreCase("0") && c.getValue("CVRValueIDCDDP").equalsIgnoreCase("0") && c.getValue("CVRPostIDDDP").equalsIgnoreCase("false") && c.getValue("CVRPostIDCDDP").equalsIgnoreCase("false") )
   		   {
   			   Application.showMessage("Account Number does not contain CVR features");
   		   }
   		   else
   		   {
   			  if(c.getValue("CurrentServicesDDP").equals("0")) 
   			  {
   				
   				  if(c.getValue("CVRPostIDDDP").equals("true"))
   				  {
   					DDSCVR_Validate(input, c);
   					scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueIDDDP"),true ); 
   					val1.threeDaycvrTableValidation(cvrtablestatus, input, c);
   				  }
   				  else
   				  {
   					DDSCVR_Validate(input, c);
   					scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueIDDDP"),false ); 
   					//val1.threeDaycvrTableValidation(cvrtablestatus, input, c);
   				  }
   			  }
   			  else
   			  {
   				  
   				if(c.getValue("CVRPostIDCDDP").equals("true"))
 				  {
   					DDSCVR_Validate(input, c);
   					val1.threeDaycvrTableValidation(cvrtablestatus, input, c);
 				  }
 				  else
 				  {
 					 DDSCVR_Validate(input, c);
 					 scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueIDCDDP"),false ); 
 				//	val1.threeDaycvrTableValidation(cvrtablestatus, input, c);
 				  }
   			  }
   		   }
   	   }
   	   else
   	   {
   		 if(c.getValue("CVRValueID").equalsIgnoreCase("0") && c.getValue("CVRValueIDC").equalsIgnoreCase("0") &&c.getValue("CVRPostID").equalsIgnoreCase("false") && c.getValue("CVRPostIDC").equalsIgnoreCase("false"))
 		   {
 			   Application.showMessage("Account Number does not contain CVR features");
 		   }
 		   else
 		   {
 			  if(c.getValue("CurrentServices").equals("0")) 
 			  {
 				
 				  if(c.getValue("CVRPostID").equals("true"))
 				  {
 					DDSCVR_Validate(input, c);
 					scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueID"),true ); 
 					val1.threeDaycvrTableValidation(cvrtablestatus, input, c);
 				  }
 				  else
 				  {
 					DDSCVR_Validate(input, c);
 					scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueID"),false ); 
 					val1.threeDaycvrTableValidation(cvrtablestatus, input, c);
 				  }
 			  }
 			  else
 			  {
 				  
 				if(c.getValue("CVRPostIDC").equals("true"))
				  {
 					DDSCVR_Validate(input, c);
				  }
				  else
				  {
					 DDSCVR_Validate(input, c);
					 scheduleBNERcallCVR_Validate(input, c,cvrStatus,c.getValue("CVRValueIDC"),false ); 
					 val1.threeDaycvrTableValidation(cvrtablestatus, input, c);
				  }
 			  }
 		   }
   	   }
       
	}





//------
public void changingdate(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
{

	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     Date date=new Date();
	 ResultSet  rs,rs1;
	 DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
     Calendar cal = Calendar.getInstance();
     Calendar cal1 = Calendar.getInstance();
     cal.add(Calendar.DATE, -2);    
     dateFormat.format(cal.getTime());
     cal1.add(Calendar.DATE, -1);    
     dateFormat.format(cal1.getTime());
	
	Application.showMessage("Date is"+dateFormat.format(cal.getTime()));
	Application.showMessage("Date is"+dateFormat.format(cal1.getTime()));
     
	try
	{
		//Application.showMessage("Account number is"+c.get("ACC_input"));
		Application.showMessage("Account number is"+c.getValue("ACC_input"));
		 Statement st =lC. dBConnect(input, c);	
		
         rs = st.executeQuery("update SCHEDULEDISCONNECT set CREATIONDATE ='"+dateFormat.format(cal.getTime())+"',SCHEDULEDATE='"+dateFormat.format(cal1.getTime())+"' where ACCOUNTNUMBER='"+c.getValue("ACC_input")+"'");
         rs1=st.executeQuery("commit");
         st.close();
        	}	
        	catch (SQLException e)
        	{
        	    System.out.println("Connection Failed! Check output console");
        		e.printStackTrace();
        		return;
        	} 
}
//------

public void ExecutionOrderPartTwo_Other(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, SAXException, XPathException, ParseException, InstantiationException, IllegalAccessException
{
	ScheduleUpgradeDowngradeValidation sUD=new ScheduleUpgradeDowngradeValidation();
	CVR_AllFlows_ValidationCalls val=new CVR_AllFlows_ValidationCalls();
	    Application.showMessage("---------------Into ExecutionOrderPartTwo_Other-----------");
	    Thread.sleep(5000);
	    //sUD.createCMSAccountID_Validate(input, c);
	   // sUD.OrderUpdate_tocheckAEE(input, c);
   	    String Value=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Channel");
        //String AccountNumber= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: accountId_Simulator");
   	    Application.showMessage("Validationg Schedule table");
   	    String Instatus="Open";
   	    Application.showMessage("Status needs to be validated is :" +Instatus);
        sUD.validateScheduleDisconnect(input, c, Instatus);
        Thread.sleep(2000);
        changingdate( input, c);
        Thread.sleep(2000);
        sUD.getWorkOrderValidate(input, c);
        val.runglobalprocesscvr(input, c);
   	    Thread.sleep(300000); 
   	 IterationlogicScheduleUP(Value,input,c);
    
        
        	        	    
 }
//----
public void getDisconnectCVR(Object input,ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
{
	CVR_ValidationCalls val=new CVR_ValidationCalls();
	
	val.getcvrcodeSCHEDULE(input, c);
	Application.showMessage("DDP Iteration Logic");
	 if(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Channel").equalsIgnoreCase("DDP"))
	 {
		 int count1=0;
		 int disservice=0;
		 
	 String disProduct1=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SerDis_PN3");
	 if(disProduct1.equals("10400463"))
	 {
		 String discountfield=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: CountFieldInstall3");
		 if(discountfield.equals("1"))
		 {
			 count1=1;
		 }else if(discountfield.equals("2"))
		 {
			 count1=2;
		 }else if(discountfield.equals("3"))
		 {
			 count1=3;
		 }
		 else
		 {
			 count1=4;
		 }
		 c.put("Disconnectservices", true);
	 }
	 else
	 {
		 c.put("Disconnectservices", false);
	 }
	 Application.showMessage("Finding services...."+c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName"));
	 if( c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_ScenarioName").equalsIgnoreCase("Demi"))
	 {
		 Application.showMessage("Demi services....");
	 	if(c.get("CVR").equals("YES"))
	     {
	 		if(c.get("Disconnectservices").equals(true))
		     {
	 		disservice=(int) c.get("CVRCOUNT")-count1;
	 		Application.showMessage("Disservice value is"+disservice);
		     }
	 		else
		 	{
		 		Application.showMessage("No need of CVR Validation");
		 	}
	     }
	 	
	 	if(disservice>0)
	 	{
	 		 c.put("CVR", "YES");
	 		 c.put("CVRCOUNT", disservice);
	 	}
	 	else
	 	{
	 		 c.put("CVR", "NO");
	 	}
	 }
	 else
	 {
		 Application.showMessage("Insight services....");
	 	if(c.get("INSIGHTCVR").equals("YES"))
	     {
	 		if(c.get("Disconnectservices").equals(true))
		     {
	 		disservice=(int) c.get("INSIGHTCVRCOUNT")-count1;
	 		Application.showMessage("Disservice value is"+disservice);
	 		
	     }
	 	else
	 	{
	 		Application.showMessage("No need of CVR Validation");
	 	}
	     }
	 	if(disservice>0)
	 	{
	 		 c.put("INSIGHTCVR", "YES");
	 		 c.put("INSIGHTCVRCOUNT", disservice);
	 	}
	 	else
	 	{
	 		 c.put("INSIGHTCVR", "NO");
	 	}
	 }
	 }
	 else
	 {
	List<String> DisProductNumbers = new ArrayList<String>();
    int dispayload=0;
    int Insightdispayload=0;
  int disservice=0;
  Application.showMessage("_______________________________________________");
DisProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SerDis_PN1")) ;
DisProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SerDis_PN2")) ;
DisProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SerDis_PN3")) ;
DisProductNumbers.add(c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SerDis_PN4")) ;
Application.showMessage("_______________________________________________");
for(int i=0;i<4;i++)
{
                if(DisProductNumbers.get(i).equals("10400463"))
                {
                	dispayload++;
                                //c.put("CVR", "YES");
                }
                else
                {
                                //c.put("CVR", "NO");
                                
                }
}
Insightdispayload=dispayload + 1;
Application.showMessage("DisPayLoad value is"+dispayload);

if( c.getValue("IsDemi").equals("true"))
{

	if(c.get("CVR").equals("YES"))
    {
		disservice=(int) c.get("CVRCOUNT")-Insightdispayload;
		Application.showMessage("Disservice value is"+disservice);
		//DDSCVR_Validate(input, c);
		//scheduleBNERcallCVR_Validate(input,  c);
    }
	else
	{
		Application.showMessage("No need of CVR Validation");
	}
	if(disservice>0)
	{
		 c.put("CVR", "YES");
		 c.put("CVRCOUNT", disservice);
	}
	else
	{
		 c.put("CVR", "NO");
	}
}
else
{
	if(c.get("INSIGHTCVR").equals("YES"))
    {
		disservice=(int) c.get("INSIGHTCVRCOUNT")-dispayload;
		Application.showMessage("Disservice value is"+disservice);
		
    }
	else
	{
		Application.showMessage("No need of CVR Validation");
	}
	if(disservice>0)
	{
		 c.put("INSIGHTCVR", "YES");
		 c.put("INSIGHTCVRCOUNT", disservice);
	}
	else
	{
		 c.put("INSIGHTCVR", "NO");
	}
}
}
	
	//Application.showMessage("INSIGHTCVRCOUNT is"+c.get("INSIGHTCVRCOUNT"))	  ;
              
}
//-----
public void getDisconnectCVR1(Object input,ScriptingContext c) throws IOException
{
	CVR_ValidationCalls val1=new CVR_ValidationCalls();
	
	val1.getcvrcodeSCHEDULE(input, c);
	
		  
              
}

//------


public void DDSCVR_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     
    
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****processHomeSecurityInfo_Validate for CVR  Function******");       
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
            	c.report("Functionality issue :: we should get response in DDS when we process with CVR , but it is throwing no response");
            	break;
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
 	           String CVRProductActionType= lC.nodeFromKey(senddata,"<typesDDS:actionType>","</typesDDS:actionType>");
	            Application.showMessage("CVRProductActionType is ::"+CVRProductActionType); 
	            String Scenario_number=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SCENARIO_NUMBER");
	            if(accountNumber_DDS.equals(c.getValue("ACC_input")) && (CVRProductActionType.equalsIgnoreCase("ADD") || CVRProductActionType.equalsIgnoreCase("REMOVE")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_DDS);
            		callFound=1;
            		
            		//String CVRProductActionType= lC.nodeFromKey(senddata,"<typesDDS:actionType>","</typesDDS:actionType>");
	 	            Application.showMessage("CVRProductActionType is ::"+CVRProductActionType); 
	 	          //  String Scenario_number=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SCENARIO_NUMBER");
	 	           if(CVRProductActionType==null)
		            {
			            c.report("Send Xml CVRProductActionType is NULL");
			            continue;
		            }
		            else
		            {
		            	if(Scenario_number.equals("1") || Scenario_number.equals("3")) 
		            	{
		            		
		            		if(CVRProductActionType.equalsIgnoreCase("ADD") || CVRProductActionType.equalsIgnoreCase("REMOVE"))
		            		{
		            			Application.showMessage("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+CVRProductActionType);
		            			v1=1;
		            		}
		            		else
		            		{
		            			//c.report("CVR Product ACTIONtYPE in Send Xml  from processHomeSecurityInfo_Validate is not Validated as "+" "+CVRProductActionType);
		            		}
		            		
		            	}
		            	
		            	
		         
		            }

	 	            
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound*v1==1)
	            {
	      
	 	            String cvrProductNumber_DDS= lC.nodeFromKey(senddata,"<typesDDS:cvrFeatureProductNumber>","</typesDDS:cvrFeatureProductNumber>");
	 	            Application.showMessage("CVR Product Number is ::"+cvrProductNumber_DDS);
	 	            
	 	            
	 	            if(cvrProductNumber_DDS==null)
		            {
			            c.report("CVR Product Number is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("CVR Product Number in Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+cvrProductNumber_DDS);
		            	 
		            }		

	 	            
		           
			            
	 	            
	 	           
	 	         	 	            
	 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
	 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
	 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
	 	            c.put("authorizationGuid", authorizationGuid_DDS);
	 	            
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
         
         if(v1*callFound*v4 ==1)
         {
         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
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

//----

public void scheduleBNERcallCVR_Validate(Object input, ScriptingContext c,String cvrorderstatus,String cvrvalue,boolean post ) throws InterruptedException, ClassNotFoundException, IOException 
{
	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
	 ResultSet  rs;
	 
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";       
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****BNER CALL  Invoke Rest Service_Validate for CVR  Function******");       
     Application.showMessage("----------------------------------------------------");
     HashMap Operation=lC.findingoperations(input, c);
     c.setValue("OPERATIONVALIDATION",(String) Operation.get("BNERcall"));
     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("BNERcall")); 
	try
	{
		// Statement st =lC. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:RestClient/invokeRestService'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		 rs=lC.reduceThinkTimeRTP(input, c);
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
	            String senddata1=senddata.replaceAll("&lt;","<");
	            String senddata2=senddata1.replaceAll("&gt;",">");
	            String senddata3=senddata2.replaceAll("&amp;#34;,",":");		            
	            String senddata4=senddata3.replaceAll("&amp;#34;:&amp;#34;",">") ;
	            String senddata5=senddata4.replaceAll("&amp;#34;","<") ;		           
	            String senddata7=senddata5.replaceAll(":<order_status>","</timestamp><order_status>") ;
	            String senddata8=senddata7.replaceAll(":<entitlements<:","</order_status>") ;
	            String senddata9=senddata8.replaceAll("<}}","</cvr><payLoad>") ;
	            Application.showMessage("SEND XML is :: \n"+senddata9.trim());    
            	         
	                        
	            Application.showMessage("**********ResourcePath Validation**********");
	            String ResourcePath= lC.nodeFromKey(senddata9,"<resourcePath>","</resourcePath>");
 	            Application.showMessage("ResourcePath is ::"+ResourcePath);
 	            String path=ResourcePath.replaceAll("/cvrBillingNotification/","");
 	            Application.showMessage("ResourcePath is ::"+path);
 	           
	      if(post==false)
	      {
	    	  String TimeStamp=lC.nodeFromKey(senddata9,"<timestamp>","</timestamp>");
 	          
	            if(TimeStamp.equals("NO Data Fetched from in-between strings..!Check the strings..!"))
          	{
	            	
          		continue;
          	}
          	else
          	{
          		Application.showMessage("*******Validation Point 1 :: WebServicall-InvokeRestService Call********");
          		Application.showMessage("Validation is Successfull with Resource Path"+ResourcePath);
          		callFound=1;
          	}
	            
	            if(callFound==1)
	            {
	            	// String TimeStamp=lC.nodeFromKey(senddata9,"<timestamp>","</timestamp>");
	            	 String cvr=lC.nodeFromKey(senddata9,"<cvr>","</cvr>");
	            	 String OrderStatus=lC.nodeFromKey(senddata9,"<order_status>","</order_status>");
	            	Application.showMessage("TimeStamp is :: "+TimeStamp);
	            	Application.showMessage("cvr from Bner call is :: "+cvr);
	            	Application.showMessage("Order Status  from Bner call is :: "+OrderStatus);
	            //	Boolean cvrValue=cvr.trim().equalsIgnoreCase("cvrvalue").toString().trim());
	            	//Application.showMessage("checking condition :: "+cvrValue);
	            	Application.showMessage("checking condition :: "+c.getValue("IsDemi"));
	            	Application.showMessage("**********CVR Validation**********");            	
	           			        
	            	
	 	           	 if(cvr==null)
		            {
			            c.report("cvr value is NULL");
			            continue;
		            }	        	
	 	        	 	          
	 	        	 else  if(cvr.trim().equalsIgnoreCase(cvrvalue.toString().trim()))
	 	          
	 	           {
	 	        	 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
            		 Application.showMessage("Validation is successful with the No of CVR Products");
            		 
            		 v1=1;
	 	           }
	 	          else
            	  {
            		 Application.showMessage("*******Validation Point 2 :: WebServicall-InvokeRestService Call********");
            		 Application.showMessage("Validation is  not successful with the No of CVR Products");
            		
            		 c.report("Validation is  not successful with the No of CVR Products");
                	 }
	            	
	            	//----------       	
	            	 	        	 
	 	        	Application.showMessage("**********Order Status  Validation**********"); 
	            	
	            	
	            	 
	            		if(OrderStatus.equalsIgnoreCase(cvrorderstatus))
	            		{
	            			Application.showMessage("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is :"+cvrorderstatus);
	            			v2=1;
	            		}
	            		else
	            		{
	            			c.report("ORDERSTATUS in Send Xml  from InvokeRestService_Validate is not validated as "+cvrorderstatus);
	            		}
	            	 
	            	//----           	
	            
	            		            
	 	        Application.showMessage("********Validation in receive xml******")   ;   
	 	           
	 	        String receivedata1=recievedata.replaceAll("&lt;","<");
	            String receivedata2=receivedata1.replaceAll("&gt;",">"); 
	            String receivedata3=receivedata2.replaceAll("&#34;","");
	            Application.showMessage("Recieve XML is::  \n"+ receivedata3);
	            String status=lC.nodeFromKey( receivedata3,"<responseBody>","</responseBody>");
	          /*  if(status.contains("success"))
	            {
	            Application.showMessage("**************Validation is successful for Receive data with status success*****");
	            v3=1;
	            }
	            else
	            {
	            	c.report("Validation is not successful for Receive data with status success");
	            }*/
	 	          
	            v3=1;
	      }
	      }
	      else
	      {
	    	  String method=lC.nodeFromKey(senddata9,"<method>","</method>");
	    	  if(method.equalsIgnoreCase("POST"))
	    	  {
	    		  callFound=1;
	    		  v1=1;
	    		  v2=1;
	    		  v3=1;
	    		  Application.showMessage("Validation is successful with CVR Post Notification");
	    	  }
	      }
	            break;
	            }
             }
         
         
         if(v1*callFound*v2*v3 ==1)
         {
         	Application.showMessage("WebService Call :: InvokeRestservice is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::InvokeRestservice is Failed with Validation Errors");
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

//------

public void AddScheduleCVRvalues(Object input,ScriptingContext c)
{
	
	lschangepn.add(c.getValue("NoChangepn1"));
	lschangepn.add(c.getValue("NoChangepn2"));
	lsinstallpn.add(c.getValue("Installpn1"));
	lsinstallpn.add(c.getValue("Installpn2"));
	lsinstallpn.add(c.getValue("Installpn3"));
	lsinstallpn.add(c.getValue("Installpn4"));
	lsdisconnectpn.add(c.getValue("Disconnectpn1"));
	lsdisconnectpn.add(c.getValue("Disconnectpn2"));
	lsdisconnectpn.add(c.getValue("Disconnectpn3"));
	lsdisconnectpn.add(c.getValue("Disconnectpn4"));
	
	lschangecount.add(new Integer(c.getValue("NoChangeCount1")));
	lschangecount.add(new Integer(c.getValue("NoChangeCount2")));
	
	lsinstallcount.add(new Integer(c.getValue("InstallCount1")));
	lsinstallcount.add(new Integer(c.getValue("InstallCount2")));
	lsinstallcount.add(new Integer(c.getValue("InstallCount3")));
	lsinstallcount.add(new Integer(c.getValue("InstallCount4")));
	lsdisconnectcount.add(new Integer(c.getValue("DisCount1")));
	lsdisconnectcount.add(new Integer(c.getValue("DisCount2")));
	lsdisconnectcount.add(new Integer(c.getValue("DisCount3")));
	lsdisconnectcount.add(new Integer(c.getValue("DisCount4")));
	
}



public void RetrievinglogicSchedule(String value,Object input,ScriptingContext c)
{
	
	
	AddScheduleCVRvalues(input,c);	
	for(int i=0;i<lschangepn.size();i++)
	{
		
		if(lschangepn.get(i).equals("10400463") || lschangepn.get(i).equals("10300029"))
		{		
		  if(value.equals("DDP"))
			{
				lschangecountvalue.add(lschangecount.get(i));			
			}
			else
			{
			NoChange++;
			}
		}
	}
	
   	for(int i=0;i<lsinstallpn.size();i++)
	{
		if(lsinstallpn.get(i).equals("10400463") || lsinstallpn.get(i).equals("10300029"))
		{
			if(value.equals("DDP"))
		
		{
				lsinstallcountvalue.add(lsinstallcount.get(i));			
		}
		else
		{
			install++;
		}
		}
	}
	for(int i=0;i<lsdisconnectpn.size();i++)
	{
		if(lsdisconnectpn.get(i).equals("10400463") || lsdisconnectpn.get(i).equals("10300029"))
		{
			if(value.equals("DDP"))
			{
				lsDisconnectcountvalue.add(lsdisconnectcount.get(i));
				
			}
			else
			{
			Disconnect++;
			}
		}
	}
	//--------------
	for(int i=0;i<lschangecountvalue.size();i++)
	{
		
			NoChange=NoChange + lschangecountvalue.get(i);
			
	}
	for(int i=0;i<lsinstallcountvalue.size();i++)
	{
		
		install=install + lsinstallcountvalue.get(i);
			
	}
	for(int i=0;i<lsDisconnectcountvalue.size();i++)
	{
		
			Disconnect=Disconnect + lsDisconnectcountvalue.get(i);
			
	}
	
	
}
public void IterationlogicScheduleUP(String value,Object input,ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException
{
	RetrievinglogicSchedule(value,input, c);
	if(Disconnect==1 && install==1)
	{
		//---
	}
	else if(Disconnect==1 && install==2)
	{
		scheduleBNERcallCVR_Validate(input, c,"ACTIVE","2" ,false);
	}
	else if(Disconnect==2 && install==2)
	{
		//----
	}
	else if(Disconnect==1 && install==1 && NoChange==1)
	{
		//---
	}
	else if(Disconnect==2 && install==1)
	{
		scheduleBNERcallCVR_Validate(input, c,"ACTIVE","1" ,false);
	}
	else if(Disconnect==1 && NoChange==1)
	{
		scheduleBNERcallCVR_Validate(input, c,"ACTIVE","1",false );
	}
}
}
