import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.comcast.neto.alm.ALMTestResultUpdater;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;
import com.parasoft.api.javascript.NewExpression;


public class sikExecutionClass
{
	sikDotcomClass sD=new sikDotcomClass();
	sikLibraryClass sL=new sikLibraryClass();
	sikCSGENImsgClass sC=new sikCSGENImsgClass();
	sikComtracDDP sCom=new sikComtracDDP();
	sikRMA sRMA=new sikRMA();
	sikXhs sX=new sikXhs();
	sikLiteSR sLR= new sikLiteSR();
	sikXhsNegativeAndMiscellenousBundle sB=new sikXhsNegativeAndMiscellenousBundle();
	
 public void sikCSGeniInitializationCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {

	 String T1=sL.getTimeInstance();
	 Application.showMessage("The start time is ::"+T1);
	 c.put("T1",T1);
	 sL.readValuesfromswivelCSGExcel(input, c);
	 sL.baseMsgid_retrieval(input, c);
	 sL.getBaseTime(input, c);
	 Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
	 Thread.sleep(2000);
 }
 
 
 public void sikCSGeniInitializationRetailCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {

	 String T1=sL.getTimeInstance();
	 Application.showMessage("The start time is ::"+T1);
	 c.put("T1",T1);
	 sC.readValuesfromsikRetailswivelCSGExcel(input, c);
	 sL.baseMsgid_retrieval(input, c);
	 sL.getBaseTime(input, c);
	 Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
	 Thread.sleep(1000);
 }
 
 public void NegativesikXhsCSGeniInitializationCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {
	
	 String T1=sL.getTimeInstance();
	 Application.showMessage("The start time is ::"+T1);
	 c.put("T1",T1);
	 sB.readValuesfromswivelCSGxhsExcel(input, c);
	 sL.baseMsgid_retrieval(input, c);
	 sL.getBaseTime(input, c);
	 Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
	 
 }
 
 public void sikRMAInitializationCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {
	 sL.baseMsgid_retrieval(input, c);
	 String T1=sL.getTimeInstance();
	 Application.showMessage("The start time is ::"+T1);
	 c.put("T1",T1);
	 sRMA.readRMAinputExcel(input, c);
	
	 sL.getBaseTime(input, c);
	 Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
	 
 }
 
 public void sikDotcomInitializationCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {
	 sL.getBaseTime(input, c);
	 String baseTime=c.get("BaseTime").toString();
	 c.setValue("Time", baseTime);
	 String T1=sL.getTimeInstance();
	 Application.showMessage("The start time is ::"+T1);
	 c.put("T1",T1);
	 String Rerun= c.getValue("dS_SIK", "Sik_Dotcom: Re-Run");
	 if(Rerun.equalsIgnoreCase("YES"))
	 {
		//sL.baseMsgid_retrieval(input, c);

	     c.setValue("RUN", Rerun);
	     sL.readValuesfromsikExcel(input, c);
	   	   
	  Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
	 }
	 else
	 {
		Application.showMessage("SKIPPED AS NO RUN STATUS");
	 }
	Thread.sleep(2000); 
 }
 
 public void sikDDPComtracInitializationCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {
	 sL.getBaseTime(input, c);
	 String baseTime=c.get("BaseTime").toString();
	 String T1=sL.getTimeInstance();
	 Application.showMessage("The start time is ::"+T1);
	 c.put("T1",T1);
	 c.setValue("Time", baseTime);
	 sL.readValuesfromComtracDDPExcel(input, c);
	 sL.IterationLogicSwivelDDP(input, c);
	 //sL.baseMsgid_retrieval(input, c);
	
	 Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
		 
 }
 
 public void sikXhsInitializationCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {
	 sL.getBaseTime(input, c);
	 String T1=sL.getTimeInstance();
	 Application.showMessage("The start time is ::"+T1);
	 c.put("T1",T1);
	 sL.readValuesfromsikXhsExcel(input, c);
	// sL.IterationLogicSwivelDDP(input, c);
	 sL.baseMsgid_retrieval(input, c);
	
	 Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
		 
 }
 
 
 public void ComtracDDPXhsInitializationCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {
	
	 String T1=sL.getTimeInstance();
	 Application.showMessage("The start time is ::"+T1);
	 c.put("T1",T1);
	 sL.readValuesfromComtracDDPXHSExcel(input, c);
	//.sL.IterationLogicSwivelDDP(input, c);
	 sL.baseMsgid_retrieval(input, c);
	 sL.getBaseTime(input, c);
	 Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
		 
 }
 
 
 
 public void LiteSRInitialisation(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {
	 sL.getBaseTime(input, c);
	 String T1=sL.getTimeInstance();
	 Application.showMessage("The start time is ::"+T1);
	 c.put("T1",T1);
	 sLR.TestCaseDetermination(input, c);
	 sLR.readValuesfromsikExcel(input, c);
	// sL.IterationLogicSwivelDDP(input, c);
	 sL.baseMsgid_retrieval(input, c);

	 Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
	 Thread.sleep(2000);	 
 }
 
 public void DDPComtrac_xhsCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {
	// sB.csgENImsg_Validate(input, c);
	  Thread.sleep(3000);
	  comtracDDPServiceInstallCalls(input, c);
	  Thread.sleep(3000);
	 
	  sX.createAccount_Validate(input, c);
	  sX.processHomeSecurityInfo_Validate(input, c);
	  sX.orderUpdate_Validate(input, c);
	 
	
 }
 
 
 public void csgNegativexhsCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {
	// sB.csgENImsg_Validate(input, c);
	 String IsNegative= (String) c.get("pIsNegative");
	 if(IsNegative.equalsIgnoreCase("YES"))
	 {
	    Thread.sleep(15000);
		int result= sB.orderUpdateNegative_Validate(input, c);
		
	 }
	 else
	 {
	 sB.OrderSearch_Validate(input, c);
	 sB.CancelOrder_Validate(input, c);
	 }
 }
 
 public void sikLiteSRCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
 {String AQMethod=(String) c.get("pAQmethod");
	Thread.sleep(30000);
	 
	if(AQMethod.equalsIgnoreCase("PICKUP"))
	{
		sLR.bfcRequestLiteSR_Validate(input, c);
		//sD.queryContract_Validate(input, c);
	
	}
	else if(AQMethod.equalsIgnoreCase("PROFESSIONAL_INSTALL"))
	{
		sLR.bfcRequestLiteSR_Validate(input, c);
		//sD.queryContract_Validate(input, c);
		
	}
	
	else if(AQMethod.equalsIgnoreCase("SHIPPED"))
	{
		sLR.bfcRequestLiteSR_Validate(input, c);
		//sD.queryContract_Validate(input, c);
		sLR.getLocation_Validate(input, c);
		sD.submitOrder_Validate(input, c);
		//sLR.orderUpdateLiteSR_Validate(input, c);
		//sLR.confirmServiceRequest_Validate(input, c);
	}
	
	else if(AQMethod.equalsIgnoreCase("CUSTOMER_PICKUP"))
	{
		sLR.bfcRequestLiteSR_Validate(input, c);
		//sD.queryContract_Validate(input, c);
		
	}
	else
	{
		sLR.bfcRequestLiteSR_Validate(input, c);
		//sD.queryContract_Validate(input, c);
		sLR.getLocation_Validate(input, c);
		sLR.orderUpdateLiteSR_Validate(input, c);
		//sLR.confirmServiceRequest_Validate(input, c);
	}
	}
 
 public void DotcomCSGWebServiceCalls(Object input, ScriptingContext c) throws Exception
 {
	 Thread.sleep(20000);
	  String AccountNumber=c.getValue("dS_SIK", "Sik_Dotcom: AccountNo");
	  
	  sD.bfcRequest_Validate(input, c);
	  sD.getLocation_Validate(input, c);
	  Thread.sleep(3000);
	  //sD.queryContract_Validate(input, c);
	  sD.NoDataSetFound(input, c, "sik:OrderSoap/SubmitOrder", AccountNumber);//For Submit Order
	  sD.NoDataSetFound(input, c, "sik:OrderSoap/GetMarketAvailability", AccountNumber);
	  sD.NoDataSetFound(input, c, "ContractServices:ContractServicePort/queryContract", AccountNumber);
	  sD.orderUpdate_Validate(input, c);
 }
	
  
  
  public void DotcomWebServiceCalls(Object input, ScriptingContext c) throws Exception
  {
	  Application.showMessage("The Result before the Execution is::"+sD.OutPut);
	  Thread.sleep(50000);
	  String Flag=c.getValue("dS_SIK","Sik_Dotcom: FlagEnabled");
	  Application.showMessage("Flag ::"+Flag);
	  String Operation=c.getValue("dS_SIK","Sik_Dotcom: Operation");
	  String AccountNumber=c.getValue("dS_SIK", "Sik_Dotcom: AccountNo");
	 if(Flag.equalsIgnoreCase("YES"))
	 {
	  if (c.getValue("acquisitionMethod").equalsIgnoreCase("SHIPPED"))
	  {
		
		  if(Operation.equals("Submit"))
			 {
				 Application.showMessage("----------------Operation is Submit-------------------------");
		  
		  Thread.sleep(2000);
		  sD.bfcRequest_Validate(input, c);
		  sD.getLocation_Validate(input, c);
		//  Thread.sleep(3000);
		 // sD.queryContract_Validate(input, c);
		  //Thread.sleep(7000);
		  sD.submitOrder_Validate(input, c);
		  sD.modifyServiceableLocation_Validate(input, c);
		  sD.oneStopSik_Validate(input, c);
		  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
		  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
		  
		  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
		  String Channel=c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel");
		  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, Channel, Channel, "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
		  sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, Channel, Channel, "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
		 // Thread.sleep(4000);
		  //sD.modifyServiceableLocation_Validate(input, c);
		  sD.confirmServiceRequest_Validate(input, c);	
			 }
		  else
			 {
				 Application.showMessage("----------------Operation is not Submit-------------------------");
				 sD.bfcFailureRequest_Validate(input, c);
				 sD.NoDataSetFound(input, c, "sik:OrderSoap/SubmitOrder", AccountNumber);
				 sD.NoDataSetFound(input, c, "sik:OrderSoap/GetMarketAvailability", AccountNumber);
				 sD.NoDataSetFound(input, c, "ContractServices:ContractServicePort/queryContract", AccountNumber);
				 sD.NoDataSetFound(input, c, "ContractServices:ContractServicePort/queryContract", AccountNumber);
				 sD.NoDataSetFound(input, c, "ls:LocationServicePort/getLocation", AccountNumber);
			 }
		  
	  }
	  
	 
	  else if (c.getValue("acquisitionMethod").equalsIgnoreCase("PROFESSIONAL_INSTALL"))
	  {
		  Application.showMessage("Validation for SIK DOTCOM PROFFESSIONAL INSTALL...");
		  Thread.sleep(2000);
		  sD.bfcRequest_Validate(input, c);
		  sD.getLocation_Validate(input, c);
		 // Thread.sleep(3000);
		//  sD.queryContract_Validate(input, c);
		//  Thread.sleep(7000);
		  //sD.submitOrder_Validate(input, c);
		 // sD.orderUpdate_Validate(input, c);
		  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
		  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
		  
		 // sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID,input, c);
		 // sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
		  sD.modifyServiceableLocation_Validate(input, c);
		  sD.oneStopSik_Validate(input, c);
		  sD.confirmServiceRequest_Validate(input, c);	
		  
		 
		  
	  }
	  else
	  {
		  
		  Thread.sleep(2000);
		  sD.bfcRequest_Validate(input, c);
		 // Thread.sleep(20000);
		  sD.getLocation_Validate(input, c);
		//  Thread.sleep(3000);
		 // sD.queryContract_Validate(input, c);
		 // Thread.sleep(7000);
		  sD.submitOrder_Validate(input, c);
		  sD.modifyServiceableLocation_Validate(input, c);
		  sD.oneStopSik_Validate(input, c);
		  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
		  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
		  
		  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
		  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel"), c.getValue("dS_SIK", "Sik_Dotcom: XML_Sales Channel"), "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
		  sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel"), c.getValue("dS_SIK", "Sik_Dotcom: XML_Sales Channel"), "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
		 // Thread.sleep(4000);
		  //sD.modifyServiceableLocation_Validate(input, c);
		  sD.confirmServiceRequest_Validate(input, c);	
		  
		  if(c.getValue("dS_SIK", "Sik_Dotcom: IsModify").equalsIgnoreCase("1"))
		  {
			  if(c.getValue("dS_SIK", "Sik_Dotcom: signatureReq").equalsIgnoreCase("true"))
			  {
				  sD.getOrderDetails_Validate(input, c);
				  sD.ModifyOrder_Validate(input, c);
			  }
			  else if(c.getValue("dS_SIK", "Sik_Dotcom: signatureReq").equalsIgnoreCase("false"))
			  {
				  sD.ModifyOrder_Validate(input, c); 
				  sD.getOrderDetails_Validate(input, c);
				  
			  }
			  else
			  {
				  
			  }
		  }
		  else
		  {
			  
			 
			/*  
			  String cycleid=c.getValue("dS_SIK", "Sik_Dotcom: TestSetID");
			  String testid =c.getValue("dS_SIK", "Sik_Dotcom: TestCaseID");
			  String[] TestCases;
			  TestCases=sL.TestCaseGroup(testid,c,input);
			  
			  if(sD.OutPut==1)
			  {
				 
				 for(int i=0; i<TestCases.length;i++)
				 {
				  ALMTestResultUpdater.updateRunStatus(cycleid, TestCases[i], "Passed");
				  
				 }
			  }
			  else
			  {
				  for(int i=0; i<TestCases.length;i++)
				  {
					  
					  ALMTestResultUpdater.updateRunStatus(cycleid, TestCases[i], "Failed");
					  
				  }
			  }
			  */
		  }
	  }
	  }
	 else
	 {
		  Thread.sleep(2000);
		  sD.bfcRequest_Validate(input, c);
		//  Thread.sleep(20000);
		  sD.getLocation_Validate(input, c);
		//  Thread.sleep(3000);
		 // sD.queryContract_Validate(input, c);
		 // Thread.sleep(7000);
		  sD.submitOrder_Validate(input, c);
		  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
		  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
		  
		  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
		  String Channel=c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel");
		  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
		  sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
		  //sD.modifyServiceableLocation_Validate(input, c);
		  sD.confirmServiceRequest_Validate(input, c);
	 }

  }
  
  
  
  public void xhsDotcomIgnoreSIK_WebServiceCalls(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
  {
	  Application.showMessage("Validation for SIK DOTCOM-XHS NEW INSTALL...");
	  Thread.sleep(30000);
	  //sD.bfcRequest_Validate(input, c);
	  sD.queryContract_Validate(input, c);
	  sD.getLocation_Validate(input, c);
	  sX.getMarketAvailabilityModified_Validate(input, c);
	  sX.submitOrder_Validate(input, c);		  
	  sX.orderUpdate_Validate(input, c);
	  Thread.sleep(7000); 		
	  sD.modifyServiceableLocation_Validate(input, c);
	  sD.confirmServiceRequest_Validate(input, c);		
	 // sD.NoDataSetFound(input, c, "ucontrolsrv:AccountServicePortType/createAccount", AccountNumber);
	  //sD.NoDataSetFound(input, c, "srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo", AccountNumber);
	  //sD.NoDataSetFound(input, c, "", AccountNumber);
	 // sD.NoDataSetFound(input, c, "", AccountNumber);

  }
  
  public void xhsDotcomWebServiceCalls(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
  {
	  
	  if (c.getValue("ordType").equalsIgnoreCase("NEW"))
	  {
		  //Dotcom calls here
		  Application.showMessage("Validation for SIK DOTCOM-XHS NEW INSTALL...");
		  Thread.sleep(30000);
		  //sD.bfcRequest_Validate(input, c);
		  sD.queryContract_Validate(input, c);
		  sD.getLocation_Validate(input, c);
		  sX.getMarketAvailabilityModified_Validate(input, c);
		  sX.submitOrder_Validate(input, c);		  
		  sX.orderUpdate_Validate(input, c);
		  Thread.sleep(7000); 		
		  sD.modifyServiceableLocation_Validate(input, c);
		  sD.confirmServiceRequest_Validate(input, c);		  
		  //xhs calls here		  
		  sX.getCustomer_Validate(input, c);
		  sX.queryLocation_Validate(input, c);
		  sX.createAccount_Validate(input, c);
		  sX.processHomeSecurityInfo_Validate(input, c);
		 		  
	  }
	  else
	  {
		  Application.showMessage("Validation for SIK DOTCOM-XHS COS...");
		  Thread.sleep(10000);
		  //sD.bfcRequest_Validate(input, c);
		  sD.queryContract_Validate(input, c);
		  sD.getLocation_Validate(input, c);
		  sX.getMarketAvailabilityModified_Validate(input, c);
		  sX.submitOrder_Validate(input, c);		  
		  sX.orderUpdate_Validate(input, c);
		  Thread.sleep(7000); 		
		  sD.modifyServiceableLocation_Validate(input, c);
		  sD.confirmServiceRequest_Validate(input, c);		  
		  //xhs calls here		  
		  sX.getCustomer_Validate(input, c);
		  sX.queryLocation_Validate(input, c);
		  sX.createAccount_Validate(input, c);
		  sX.processHomeSecurityInfo_Validate(input, c);
	  }

  }
  
  public void RMAsikWebserviceCalls(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
  {
	  
	 
	  Thread.sleep(30000);
	  sRMA.bfcRequest_Validate(input, c);
	  sRMA.getLocation_Validate(input, c);
	  //Thread.sleep(3000);
	 // sRMA.queryContract_Validate(input, c);
	 // Thread.sleep(30000);
	  sRMA.submitOrder_Validate(input, c);
	  //sRMA.orderUpdate_Validate(input, c);
	  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
	  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
	 // sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", "DDP", "CHG", "RES",WORKORDER_ID, input, c);

	  sRMA.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", "DDP", "CHG", "RES",WORKORDER_ID, input, c);
	 // sRMA.modifyServiceableLocation_Validate(input, c);
	  //sRMA.confirmServiceRequest_Validate(input, c);
	 
  }
  
  public void csgEniWebServiceCallsNew(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException, ParserConfigurationException
  {
         
         String Isnegative=c.getValue("SwivelCSG", "sik_SwivelCSG: IsNegative");
         if(Isnegative.equals("1"))
         {
                Thread.sleep(8000);
                sC.csgENImsg_Validate(input, c);
                sC.orderUpdateMissingInfo_Validate(input, c);
                
                c.setValue("CancelReq", "NO");
                
         }
         else if(Isnegative.equals("2"))
         {
                Thread.sleep(8000);
                sC.csgENImsg_Validate(input, c);
                c.setValue("CancelReq", "NO");
         }
         
         else
         {
         Thread.sleep(8000);
         sC.csgENImsg_Validate(input, c);
         sC.QueryLocation_Validate(input, c);
         Thread.sleep(7000);        
         sC.submitOrder_Validate(input, c);
         sC.orderUpdate_Validate(input, c);
         }
       
  }
  public void csgRetailEniWebServiceCalls(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException, ParserConfigurationException
  {
	  Application.showMessage("Value for isNegative is::"+c.get("isNegative").toString());
       if(c.get("isNegative").equals("YES"))
       {
                Thread.sleep(8000);
                sC.csgENImsg_Validate(input, c);
                //sC.QueryLocation_Validate(input, c); 
                Thread.sleep(10000);
                sC.orderUpdateNegative_Validate(input, c);
                c.setValue("CancelReq", "NO");
        }
         
        else
        {
       
         Thread.sleep(7000);
         sC.csgENImsg_Validate(input, c);
         sC.QueryLocation_Validate(input, c);
         Thread.sleep(3000);        
         sC.submitOrder_Validate(input, c);
         sC.orderUpdate_Validate(input, c);
       
        
        }
  }

  //_____________________________ CSG SWIVEL______________________________________________________//
	 
	 
	 public void csgEniWebServiceCallsR(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException, ParserConfigurationException
	  {
		  
		  String Isnegative=c.getValue("SwivelCSG", "sik_SwivelCSG: IsNegative");
		  if(Isnegative.equals("1"))
		  {
			  Thread.sleep(4000);
			  sC.csgENImsg_Validate(input, c);
			  orderUpdateMissingInfo_Validate(input, c);
			 // sC.orderUpdate_Validate(input, c);
			
			  
		  }
		  else if(Isnegative.equals("2"))
		  {
			  Thread.sleep(4000);
			  sC.csgENImsg_Validate(input, c);
		  }
		  
		  else
		  {
			  Thread.sleep(4000);
			  sC.csgENImsg_Validate(input, c);
			  sC.QueryLocation_Validate(input, c);
			 // Thread.sleep(4000);	  
			  sC.submitOrder_Validate(input, c);
			  sC.orderUpdate_Validate(input, c);
		  }
		 
	  }
	  public void csgRetailEniWebServiceCallsR(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException, ParserConfigurationException
	  {
		 if(c.get("isNegative").equals("YES"))
		 {
			  Thread.sleep(4000);
			  sC.csgENImsg_Validate(input, c);
			 // Thread.sleep(10000);
			  orderUpdateNegative_Validate(input, c);
			  
		 }
		  
		 else{
		 
		  Thread.sleep(4000);
		  sC.csgENImsg_Validate(input, c);
		  sC.QueryLocation_Validate(input, c);
		  //Thread.sleep(3000);	  
		  sC.submitOrder_Validate(input, c);
		  sC.orderUpdate_Validate(input, c);
		 
		 
		 }
	  }
	  //--------------------- End of CSG Swivel-------------------------------------------//
  
  public void comtracDDPServiceInstallCalls(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException, ParserConfigurationException
  {
	
    String reqType = (String) c.get("pRequestType");	
	if(reqType.equalsIgnoreCase("install"))
	{ 

	  Thread.sleep(4000);
	  sCom.comtracRequestINSTALL_Validate(input, c);
	//  Thread.sleep(10000);
	  sCom.getHouseInfo_Validate(input, c);
	 // sCom.QueryLocation_Validate(input, c);
	  sCom.wipInfoCall_Validate(input, c);
	  sCom.submitOrder_Validate(input, c);
	  sCom.orderUpdate_Validate(input, c);
	  sCom.confirmServiceRequest_Validate(input, c);	 
	  
	}
	
	if(reqType.equalsIgnoreCase("reschedule"))
	{
	  Thread.sleep(4000);
	  sCom.comtracCos_Validate(input, c);
	 // Thread.sleep(20000);
	  sCom.getHouseInfo_Validate(input, c);
	 // Thread.sleep(15000);
	  //sCom.QueryLocation_Validate(input, c);
	  sCom.getCustInfo_Validate(input, c);
	  sCom.wipInfoCall_Validate(input, c);
	  sCom.SubmitOrderCos_Validate(input, c);
	  sCom.orderUpdate_Validate(input, c);
	  sCom.confirmServiceRequest_Validate(input, c);	 

	}
	
	if(reqType.equalsIgnoreCase("cancel"))
	{
	  Thread.sleep(4000);
	  //sCom.comtracRequestINSTALL_Validate(input, c);
	 // Thread.sleep(10000);
	  sCom.OrderSearch_Validate(input, c);
	  sCom.CancelOrder_Validate(input, c);	  
	  sCom.orderUpdate_Validate(input, c);
	  
	  sCom.confirmServiceRequest_Validate(input, c);	 
      c.clear();
	}
	
	if(reqType.equalsIgnoreCase("transfer"))
	{
	  Thread.sleep(4000);
	
	  sCom.comtracRequestTransfer_Validate(input, c);
	  //Thread.sleep(10000);
	  sCom.getHouseInfoTRF_Validate(input, c);
	  sCom.getHouseInfo_Validate(input, c);
	  //sCom.QueryLocationTRF_Validate(input, c);
	  sCom.wipInfoCall_Validate(input, c);
	  sCom.SubmitOrderTRF_Validate(input, c);
	  sCom.orderUpdate_Validate(input, c);
	  sCom.confirmServiceRequest_Validate(input, c);
	  sCom.confirmServiceRequestTRF_Validate(input, c);
	  

	}
	
	if(reqType.equalsIgnoreCase("restart"))
	{
		Thread.sleep(4000);
		  sCom.comtracCos_Validate(input, c);
		 // Thread.sleep(20000);
		  sCom.getHouseInfo_Validate(input, c);
		//  Thread.sleep(15000);
		  //sCom.QueryLocation_Validate(input, c);
		  sCom.getCustInfo_Validate(input, c);
		  sCom.wipInfoCall_Validate(input, c);
		  sCom.SubmitOrderCos_Validate(input, c);
		  sCom.orderUpdate_Validate(input, c);
		  sCom.confirmServiceRequest_Validate(input, c);	 
	  //c.clear();
	}
	
	if(reqType.equalsIgnoreCase("changeofservice"))
	{
		  Thread.sleep(4000);
		  sCom.comtracCos_Validate(input, c);
		 // Thread.sleep(20000);
		  sCom.getHouseInfo_Validate(input, c);
		 // Thread.sleep(15000);
		 // sCom.QueryLocation_Validate(input, c);
		  sCom.getCustInfo_Validate(input, c);
		  sCom.wipInfoCall_Validate(input, c);
		  sCom.SubmitOrderCos_Validate(input, c);
		  sCom.orderUpdate_Validate(input, c);
		  sCom.confirmServiceRequest_Validate(input, c);	  

	}
	
	if(reqType.equalsIgnoreCase("disconnect"))
	{
	  Thread.sleep(4000);
	 // sCom.comtracCos_Validate(input, c);
	 // Thread.sleep(10000);
	  sCom.getHouseInfo_Validate(input, c);
	 
	  sCom.confirmServiceRequest_Validate(input, c);	 

	}
	
  }
  /*-----------------------------------------------------------------
   * 
   * 							Dotcom_XHS
   * 
   ------------------------------------------------------------------*/
  
  public void Dotcom_XHS(Object input, ScriptingContext c) throws Exception
  {
	   String ProductID=c.getValue("dS_SIK","Sik_Dotcom: ProductID").trim();// fetching channel value sent in request
       Application.showMessage("ProductID ::"+ProductID);
       String AO_Services=c.getValue("dS_SIK","Sik_Dotcom: AO Services").trim();// fetching swap value sent in request
       Application.showMessage("AO_Services ::"+AO_Services);
       DTAOM_trackCode DT= new DTAOM_trackCode();
       boolean productid1=ProductID.equals("40031");
       boolean services1=AO_Services.equals("10097");
       Application.showMessage("productid1::"+productid1);
       Application.showMessage("services1::"+services1);
       if(productid1==true && services1==true)
        {
    	   Application.showMessage("BOTH PRODUCT ID AND AO_SERVICES CONFIGURED");
    	   
    	     // no xhs calls submit order success-----------------
    	      //Application.showMessage("The Result before the Execution is::"+sD.OutPut);
    		  Thread.sleep(100000);
    		
    		  String Flag=c.getValue("dS_SIK","Sik_Dotcom: FlagEnabled");
    		  Application.showMessage("Flag ::"+Flag);
    		  String Operation=c.getValue("dS_SIK","Sik_Dotcom: Operation");
    		  String AccountNumber=c.getValue("dS_SIK", "Sik_Dotcom: AccountNo");
    		
    		 
    			  Thread.sleep(10000);
    			//  sD.bfcRequest_Validate(input, c);
    			  Thread.sleep(20000);
    			  sD.getLocation_Validate(input, c);
    			  Thread.sleep(3000);
    			  sD.queryContract_Validate(input, c);
    			  Thread.sleep(7000);
    			 // sD.submitOrder_Validate(input, c);
    			  DT.submitOrder_Validate_xhs(input, c);
    			  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
    			  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
    			  Thread.sleep(10000);

    			  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
    			 
    			  DT.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
      			  DT.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
      			 //sD.modifyServiceableLocation_Validate(input, c);
    			  sD.confirmServiceRequest_Validate(input, c);
    			  DT.getDatasetUsingUserData2(input, c); // validating no XHS calls are made
        } 
       else 
       {
    	   Application.showMessage("NOT CONFIGURED ");
    	// getmarket failure and worklist in common---------------
    	   Application.showMessage("The Result before the Execution is::"+sD.OutPut);
  		   Thread.sleep(50000);
  		
  		   String Flag=c.getValue("dS_SIK","Sik_Dotcom: FlagEnabled");
  		   Application.showMessage("Flag ::"+Flag);
  		   String Operation=c.getValue("dS_SIK","Sik_Dotcom: Operation");
  		   String AccountNumber=c.getValue("dS_SIK", "Sik_Dotcom: AccountNo");
  		
  		 
  			  Thread.sleep(10000);
  			//  sD.bfcRequest_Validate(input, c);
  			  Thread.sleep(20000);
  			  sD.getLocation_Validate(input, c);
  			  Thread.sleep(3000);
  			 // sD.queryContract_Validate(input, c);
  			  Thread.sleep(100000);
  			 // sD.submitOrder_Validate(input, c);
  			//  sD.submitOrder_Validate(input, c);
  			  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
  			  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
  			  

  			  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
  			
  			 
  			  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
  			  sD.OrderUpdateFlagEnabled_Validate("ERR", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
  			  //sD.modifyServiceableLocation_Validate(input, c);
  			 // sD.confirmServiceRequest_Validate(input, c);
    	   
    	   
       }
     
	  
	  
  }
  
  /*---------------------------------------------------------------------------------
   * 
   * 									DTA
   * 
   ---------------------------------------------------------------------------------*/
  
  public void DotcomWebServiceCallsDTA(Object input, ScriptingContext c) throws Exception
  {
	  Application.showMessage("The Result before the Execution is::"+sD.OutPut);
	  Thread.sleep(100000);
	  DTAOM_trackCode DTA=new DTAOM_trackCode();
	  String Flag=c.getValue("dS_SIK","Sik_Dotcom: FlagEnabled");
	  Application.showMessage("Flag ::"+Flag);
	  String Operation=c.getValue("dS_SIK","Sik_Dotcom: Operation");
	  String AccountNumber=c.getValue("dS_SIK", "Sik_Dotcom: AccountNo");
	
	 
		  Thread.sleep(2000);
		  sD.bfcRequest_Validate(input, c);
		 // Thread.sleep(20000);
		  sD.getLocation_Validate(input, c);
		//  Thread.sleep(3000);
		 // sD.queryContract_Validate(input, c);
		//  Thread.sleep(7000);
		 // sD.submitOrder_Validate(input, c);
		  DTA.submitOrder_Validate(input, c);
		  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
		  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
		  

		  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
		  String Channel=c.getValue("dS_SIK", "Sik_Dotcom: DTA_Channel");
		  Application.showMessage("Channel ::"+Channel);
		  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, Channel, Channel, "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
		  sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, Channel, Channel, "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
		  //sD.modifyServiceableLocation_Validate(input, c);
		  sD.confirmServiceRequest_Validate(input, c);

  }
  
  
  
  //--------------------- CSG SWIVEL ADDITIONAL FUNCTIONS-----------------------//
  public void orderUpdateMissingInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		//Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
		 String Bid=c.getValue("BaseMsgid");
		 long Baseid=Long.parseLong(Bid);
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********orderUpdate_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
		       
	         while (rs.next())
	         {
	        	
	        
	        	 long rowmsg;
	     		 rowmsg = Long.parseLong(rs.getString(1));
	     		 Application.showMessage("The Base msgid is ::"+Baseid);
	     		 Application.showMessage("Current Row msgid is ::"+rowmsg);
			            
	     		if(Baseid == rowmsg)break;
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
		 	        
		 	          //  String ordType= sL.GetValueByXPath(senddata_wf, "/header/value[6]");
		 	          // String ordType= sL.GetValueByXPath(senddata, "/cod:comRequest/header/value[6]");
		 	           
		 	        /*    String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\"/>");
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
			             else
			             {
			            	 c.report("orderType at Send Xml not Validated as "+ordType);
			             }   */
		 	           v3=1;
		 	            String details= sL.nodeFromKey(senddata,"<value name=\"details\">","</value></worklist>");
		 	            Application.showMessage("Details  is ::"+details); 
                      
		 	           
		 	            String errCode= sL.GetValueByXPath(senddata_wf, "/comRequest/wlRequest/worklist/value[3]");

		 	           // String errCode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"details\">");
		 	            Application.showMessage("Error Code is ::"+errCode); 
		 	            c.put("ErrorCodeOu",errCode);
		 	          
		 	           //String errmsg= sL.GetValueByXPath(senddata_wf, "//cod:comRequest/header/value[3]");
		 	           String errmsg= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorCode\">");
		 	            Application.showMessage("Error Message  is ::"+errmsg); 
		 	            c.put("ErrorTextOu",errmsg);
		 	          
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
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
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

	
	
	
	
	
	public void CommonDBValidate(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, SQLException
	{
		 sikLibraryClass  lC= new sikLibraryClass ();
		 ResultSet rs1;
		  
		
		try
		{
			
			 Statement st1 =lC.dBConnectCommon(input, c);
			 
			
	         //Application.showMessage("Account Number is "+c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber"));
	         String Acc= c.getValue("SwivelCSG", "sik_SwivelCSG: AccountId_ITEMLIST");
			 rs1 = st1.executeQuery("select * from(select * from cwpworklist where ACCOUNTNUMBER ='"+Acc+"'order by creation_date desc)where rownum < 2");
			 if(rs1==null)
			 {
				 Application.showMessage("Null Record set found in WorKlist DB for AccountNumber'"+Acc);
			 }
	      
	         while(rs1.next())
	   {
	         String errorCode= rs1.getString("ERRORCODE");
          // String errorSource=rs1.getString("ERRORSOURCE");
          // Application.showMessage("Error Source from common is" +errorSource);
           String errorText=rs1.getString("ERRORTEXT");
           String PARTICIPANTOPERATION=rs1.getString("PARTICIPANTOPERATION");
          // String BILLINGSYSTEM=rs1.getString("BILLINGSYSTEM");
           String SERVICETYPE=rs1.getString("SERVICETYPE");
           String ORDER_ID=rs1.getString("ORDER_ID");
	         Application.showMessage("Error Code is ::"+errorCode);
	        // Application.showMessage("errorSource is ::"+errorSource);
	         Application.showMessage("PARTICIPANTOPERATION is ::"+PARTICIPANTOPERATION);
	         Application.showMessage("errorText is ::"+errorText);
	        //Application.showMessage("BILLINGSYSTEM is ::"+BILLINGSYSTEM);
	         //Application.showMessage("SERVICETYPE is ::"+SERVICETYPE);
	        // Application.showMessage("ORDER_ID is ::"+ORDER_ID);
	          //String errMade=KittyperRateCodeTableValidation(input, c);
	           
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
	         
//	         if(errorSource.equals(c.get("ErrorSourceOu")))
//	         {
//	        	 Application.showMessage("Error SOURCE from order update is validated successfully with error Source from common DB"); 
//	         }
//	         else
//	         {
//	        	 Application.showMessage("ERROR SOURCE IS NOT VALIDATED");
//	         }
//	         
	         
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
  
	public void orderUpdateNegative_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		
		ResultSet  rs;
		String Bid=c.getValue("BaseMsgid");
		long Baseid=Long.parseLong(Bid);
		

		 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********orderUpdate_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	        	 long rowmsg;
	     		 rowmsg = Long.parseLong(rs.getString(1));
	     		 Application.showMessage("The Base msgid is ::"+Baseid);
	     		 Application.showMessage("Current Row msgid is ::"+rowmsg);
			            
	     		if(Baseid==rowmsg)break;

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
		 	            String ordType1= sL.GetValueByXPath(senddata_wf, "//comRequest/header/value[6]");
		 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
		 	            Application.showMessage("ordType is ::"+ordType); 
		 	           Application.showMessage("ordType is ::"+ordType1);
		 	            if(ordType==null)
			            {
				            c.report("Send Xml ordType is NULL");
				            
			            }
		 	            else if(ordType.equals("ERR"))
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
                        
		 	           
		 	            String errCode= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[3]");

		 	           // String errCode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"details\">");
		 	            Application.showMessage("Error Code is ::"+errCode); 
		 	            c.put("ErrorCodeOu",errCode);
		 	          
		 	            String errmsg= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[4]");
		 	            //String errmsg= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorSource\">");
		 	            Application.showMessage("Error Message  is ::"+errmsg); 
		 	            c.put("ErrorTextOu",errmsg);
//		 	            //String errMade=KittyperRateCodeTableValidation(input, c);
//		 	            if(errMade.equalsIgnoreCase(errmsg))
//		 	            {
//		 	            	Application.showMessage("Error Message got validated with MapBase value as:"+errMade);
//		 	          
//		 	            }
//		 	            else
//		 	            {
//		 	            	c.report("Error Message failed::"+errMade);
//		 	            }
		 	          
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
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
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
	
	public void comtracDDPNegativeQueryLocationCalls(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException, ParserConfigurationException
	  {
	
    String reqType = (String) c.get("pRequestType");
    String scenariotype=c.getValue("SwivelDDP", "ComtracParameterList: ScenarioType");
    Application.showMessage("scenarioType::"+scenariotype);
	if(reqType.equalsIgnoreCase("install"))
	{ 

	  if(scenariotype.equalsIgnoreCase("Querylocation"))
	  {
		Thread.sleep(4000);
	  sCom.comtracRequestINSTALL_Validate(input, c);
	 // Thread.sleep(40000);
	  sCom.getHouseInfo_Validate(input, c);
	 // Thread.sleep(40000);
	  sCom.QueryLocation_Validate(input, c);
	//  Thread.sleep(40000);
	  sCom.wipInfoCall_Validate(input, c);
	//  Thread.sleep(40000);
	  //sCom.submitOrder_Validate(input, c);
	  sCom.orderUpdate_ValidateforNegativeQuerylocation(input, c);
	  }
	  else if(scenariotype.equalsIgnoreCase("SubmitOrder"))
	  {
		  Thread.sleep(4000);
		  sCom.comtracRequestINSTALL_Validate(input, c);
		//  Thread.sleep(30000);
		  sCom.getHouseInfo_Validate(input, c);
		//  Thread.sleep(20000);
		  sCom.QueryLocation_Validate(input, c);
		 // Thread.sleep(20000);
		  sCom.wipInfoCall_Validate(input, c);
		//  Thread.sleep(60000);
		 sCom.submitOrder_Validate(input, c);
		// Thread.sleep(30000);
		  sCom.orderUpdate_ValidateforNegativeQuerylocation(input, c);  
	  }
	  else
	  {
		  Thread.sleep(4000);
		  sCom.comtracRequestINSTALL_Validate(input, c);
		 // Thread.sleep(20000);
		  sCom.getHouseInfo_Validate(input, c);
		//  Thread.sleep(20000);
		  sCom.QueryLocation_Validate(input, c);
		//  Thread.sleep(40000);
		  sCom.wipInfoCall_Validate(input, c);
		//  Thread.sleep(60000);
		 sCom.submitOrder_Validate(input, c);
		// Thread.sleep(60000);
		 sCom.ModifyOrder_Validate(input, c);
		// Thread.sleep(40000);
		  sCom.orderUpdate_ValidateforNegativeQuerylocation(input, c);
	  }
	  //sCom.confirmServiceRequest_Validate(input, c);	 
	  
	}
	
	if(reqType.equalsIgnoreCase("reschedule"))
	{
	  Thread.sleep(4000);
	  sCom.comtracCos_Validate(input, c);
	//  Thread.sleep(20000);
	  sCom.getHouseInfo_Validate(input, c);
	//  Thread.sleep(50000);
	  //sCom.QueryLocation_Validate(input, c);
	  sCom.getCustInfo_Validate(input, c);
	//  Thread.sleep(50000);
	  sCom.wipInfoCall_Validate(input, c);
	  sCom.SubmitOrderCos_Validate(input, c);
	//  Thread.sleep(50000);
	  sCom.orderUpdate_Validate(input, c);
	  sCom.confirmServiceRequest_Validate(input, c);	 

	}
	
	if(reqType.equalsIgnoreCase("cancel"))
	{
	  Thread.sleep(4000);
	  //sCom.comtracRequestINSTALL_Validate(input, c);
	 // Thread.sleep(50000);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  
	  //sCom.OrderSea                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               rch_Validate(input, c);
	 sCom.OrderSearch_CancelValidate(input, c);
	 // Thread.sleep(60000);
	  sCom.orderUpdate_Validate(input, c);
	  
	 // sCom.confirmServiceRequest_Validate(input, c);	 
      c.clear();
	}
	
	/*if(reqType.equalsIgnoreCase("transfer"))
	{
	  Thread.sleep(8000);
	
	  sCom.comtracRequestTransfer_Validate(input, c);
	  Thread.sleep(10000);
	  sCom.getHouseInfoTRF_Validate(input, c);
	  sCom.getHouseInfo_Validate(input, c);
	  //sCom.QueryLocationTRF_Validate(input, c);
	  sCom.wipInfoCall_Validate(input, c);
	  sCom.SubmitOrderTRF_Validate(input, c);
	  sCom.orderUpdate_Validate(input, c);
	  sCom.confirmServiceRequest_Validate(input, c);
	  sCom.confirmServiceRequestTRF_Validate(input, c);
	  

	}*/
	
	if(reqType.equalsIgnoreCase("restart"))
	{
		Thread.sleep(4000);
		  sCom.comtracCos_Validate(input, c);
		//  Thread.sleep(20000);
		  sCom.getHouseInfo_Validate(input, c);
		 // Thread.sleep(50000);
		  //sCom.QueryLocation_Validate(input, c);
		  sCom.getCustInfo_Validate(input, c);
		 // Thread.sleep(50000);
		  sCom.wipInfoCall_Validate(input, c);
		//  Thread.sleep(50000);
		  sCom.SubmitOrderCos_Validate(input, c);
		//  Thread.sleep(50000);
		  sCom.orderUpdate_Validate(input, c);
		  sCom.confirmServiceRequest_Validate(input, c);	 
	  //c.clear();
	}
	
	if(reqType.equalsIgnoreCase("changeofservice"))
	{
		  Thread.sleep(2000);
		  sCom.comtracCos_Validate(input, c);
		//  Thread.sleep(20000);
		  sCom.getHouseInfo_Validate(input, c);
		//  Thread.sleep(15000);
		 // sCom.QueryLocation_Validate(input, c);
		  //sCom.getCustInfo_Validate(input, c);
		 // sCom.wipInfoCall_Validate(input, c);
		  //sCom.SubmitOrderCos_Validate(input, c);
		  //sCom.orderUpdate_Validate(input, c);
		 // sCom.confirmServiceRequest_Validate(input, c);	  

	}
	
	/*if(reqType.equalsIgnoreCase("disconnect"))
	{
	  Thread.sleep(8000);
	 // sCom.comtracCos_Validate(input, c);
	  Thread.sleep(10000);
	  sCom.getHouseInfo_Validate(input, c);
	 
	  sCom.confirmServiceRequest_Validate(input, c);	 

	}*/
	  }
  
	public void DotcomWebServiceCallsforNegativeQueryLocation(Object input, ScriptingContext c) throws Exception
	  {
		  Application.showMessage("The Result before the Execution is::"+sD.OutPut);
		  Thread.sleep(50000);
		  String Flag=c.getValue("dS_SIK","Sik_Dotcom: FlagEnabled");
		  Application.showMessage("Flag ::"+Flag);
		  String Operation=c.getValue("dS_SIK","Sik_Dotcom: Operation");
		  String AccountNumber=c.getValue("dS_SIK", "Sik_Dotcom: AccountNo");
		  String modifyOrder=c.getValue("dS_SIK","Sik_Dotcom: IsModify");
		 if(Flag.equalsIgnoreCase("YES"))
		 {
		  if (c.getValue("acquisitionMethod").equalsIgnoreCase("SHIPPED"))
		  {
			
			  if(Operation.equals("Submit"))
				 {
					 Application.showMessage("----------------Operation is Submit-------------------------");
			  
			  Thread.sleep(50000);
			  sD.bfcRequest_Validate(input, c);
			  if(modifyOrder.equalsIgnoreCase("1"))
			  {
				  sD.getLocation_Validate(input, c);
				  sD.submitOrder_Validate(input, c);
				  sD.ModifyOrder_Validate(input, c);
				  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
				  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
				  
				  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
				  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel"), c.getValue("dS_SIK", "Sik_Dotcom: XML_Sales Channel"), "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
				  Thread.sleep(50000);
				  sCom.orderUpdate_ValidateforNegativeQuerylocation(input, c);
				  //sD.OrderUpdateFlagEnabled_Validate("ERR", INbillingOrderId, c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel"), c.getValue("dS_SIK", "Sik_Dotcom: XML_Sales Channel"), "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
				  
			  }
			  else 
			  {
				  sD.getLocation_Validate_NegativeCalls(input, c);  
				  Thread.sleep(50000);
				  sCom.orderUpdate_ValidateforNegativeQuerylocation(input, c);
			  }
			  
			  //sD.getLocation_Validate(input, c);
			 
			  //sD.queryContract_Validate(input, c);
			  /*Thread.sleep(50000);
			  
			  Application.showMessage("ModifyOrder Id value::"+modifyOrder);
			  if(modifyOrder.equalsIgnoreCase("0"))
			  {
				 sD.submitOrder_Validate(input, c); 
				 
			  }
			  else if (modifyOrder.equalsIgnoreCase("1"))
			  {
				  
			  }*/
			 // sD.submitOrder_Validate(input, c);
			 // sD.modifyServiceableLocation_Validate(input, c);
			  //sD.oneStopSik_Validate(input, c);
			  /*String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
			  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
			  
			  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
			  String Channel=c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel");
			  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, Channel, Channel, "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
			  sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, Channel, Channel, "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
			  */
			  
			  //Thread.sleep(50000);
			  //sCom.orderUpdate_ValidateforNegativeQuerylocation(input, c);
			  //sD.modifyServiceableLocation_Validate(input, c);
			  //sD.confirmServiceRequest_Validate(input, c);	
				 }
			  else if (Operation.equals("GMA"))
			  {
				  Thread.sleep(40000);
				  sD.bfcRequest_Validate(input, c);
				  Thread.sleep(5000);
				  sD.getLocation_Validate(input, c);
			  Thread.sleep(50000);
			  sCom.orderUpdate_ValidateforNegativeQuerylocation(input, c);
				  
				  
				 }
			  else
				 {
					 Application.showMessage("----------------Operation is not Submit-------------------------");
					 sD.bfcFailureRequest_Validate(input, c);
					 sD.NoDataSetFound(input, c, "sik:OrderSoap/SubmitOrder", AccountNumber);
					 sD.NoDataSetFound(input, c, "sik:OrderSoap/GetMarketAvailability", AccountNumber);
					 sD.NoDataSetFound(input, c, "ContractServices:ContractServicePort/queryContract", AccountNumber);
					 sD.NoDataSetFound(input, c, "ContractServices:ContractServicePort/queryContract", AccountNumber);
					 sD.NoDataSetFound(input, c, "ls:LocationServicePort/getLocation", AccountNumber);
				 }
			  
		  }
		  
		 
		  else if (c.getValue("acquisitionMethod").equalsIgnoreCase("PROFESSIONAL_INSTALL"))
		  {
			  Application.showMessage("Validation for SIK DOTCOM PROFFESSIONAL INSTALL...");
			  Thread.sleep(20000);
			  sD.bfcRequest_Validate(input, c);
			  sD.getLocation_Validate(input, c);
			  Thread.sleep(3000);
			//  sD.queryContract_Validate(input, c);
			  Thread.sleep(7000);
			  //sD.submitOrder_Validate(input, c);
			 // sD.orderUpdate_Validate(input, c);
			  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
			  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
			  
			 // sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID,input, c);
			 // sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
			  sD.modifyServiceableLocation_Validate(input, c);
			  sD.oneStopSik_Validate(input, c);
			  sD.confirmServiceRequest_Validate(input, c);	
			  
			 
			  
		  }
		  else
		  {
			  
			  Thread.sleep(10000);
			  sD.bfcRequest_Validate(input, c);
			  Thread.sleep(20000);
			  sD.getLocation_Validate(input, c);
			  Thread.sleep(3000);
			 // sD.queryContract_Validate(input, c);
			  Thread.sleep(7000);
			  sD.submitOrder_Validate(input, c);
			  sD.modifyServiceableLocation_Validate(input, c);
			  sD.oneStopSik_Validate(input, c);
			  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
			  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
			  
			  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
			  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel"), c.getValue("dS_SIK", "Sik_Dotcom: XML_Sales Channel"), "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
			  sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel"), c.getValue("dS_SIK", "Sik_Dotcom: XML_Sales Channel"), "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
			  Thread.sleep(4000);
			  //sD.modifyServiceableLocation_Validate(input, c);
			  sD.confirmServiceRequest_Validate(input, c);	
			  
			  if(c.getValue("dS_SIK", "Sik_Dotcom: IsModify").equalsIgnoreCase("1"))
			  {
				  if(c.getValue("dS_SIK", "Sik_Dotcom: signatureReq").equalsIgnoreCase("true"))
				  {
					  sD.getOrderDetails_Validate(input, c);
					  sD.ModifyOrder_Validate(input, c);
				  }
				  else if(c.getValue("dS_SIK", "Sik_Dotcom: signatureReq").equalsIgnoreCase("false"))
				  {
					  sD.ModifyOrder_Validate(input, c); 
					  sD.getOrderDetails_Validate(input, c);
					  
				  }
				  else
				  {
					  
				  }
			  }
			  else
			  {
				  
				 
				/*  
				  String cycleid=c.getValue("dS_SIK", "Sik_Dotcom: TestSetID");
				  String testid =c.getValue("dS_SIK", "Sik_Dotcom: TestCaseID");
				  String[] TestCases;
				  TestCases=sL.TestCaseGroup(testid,c,input);
				  
				  if(sD.OutPut==1)
				  {
					 
					 for(int i=0; i<TestCases.length;i++)
					 {
					  ALMTestResultUpdater.updateRunStatus(cycleid, TestCases[i], "Passed");
					  
					 }
				  }
				  else
				  {
					  for(int i=0; i<TestCases.length;i++)
					  {
						  
						  ALMTestResultUpdater.updateRunStatus(cycleid, TestCases[i], "Failed");
						  
					  }
				  }
				  */
			  }
		  }
		  }
		 else
		 {
			  Thread.sleep(10000);
			  sD.bfcRequest_Validate(input, c);
			  Thread.sleep(20000);
			  sD.getLocation_Validate(input, c);
			  Thread.sleep(3000);
			 // sD.queryContract_Validate(input, c);
			  Thread.sleep(7000);
			  sD.submitOrder_Validate(input, c);
			  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
			  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
			  
			  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
			  String Channel=c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel");
			  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
			  sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
			  //sD.modifyServiceableLocation_Validate(input, c);
			  sD.confirmServiceRequest_Validate(input, c);
		 }

	  }
	public void sikLiteSRCallsforNegativeQuery(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
	 {
		 
		String AQMethod=(String) c.get("pAQmethod");
		String scenariotype=c.getValue("dS_SIK", "Sik_Dotcom: ScenarioType");
	    Application.showMessage("scenarioType::"+scenariotype);
		Thread.sleep(30000);
		 
		if(AQMethod.equalsIgnoreCase("PICKUP"))
		{
			sLR.bfcRequestLiteSR_Validate(input, c);
			//sD.queryContract_Validate(input, c);
		
		}
		else if(AQMethod.equalsIgnoreCase("PROFESSIONAL_INSTALL"))
		{
			sLR.bfcRequestLiteSR_Validate(input, c);
			//sD.queryContract_Validate(input, c);
			
		}
		
		else if(AQMethod.equalsIgnoreCase("SHIPPED"))
		{
			if(scenariotype.equalsIgnoreCase("QueryLocation"))
			{
			sLR.bfcRequestLiteSR_Validate(input, c);
			//Thread.sleep(50000);
			//sD.queryContract_Validate(input, c);
			sD.getLocation_Validate_NegativeCalls(input, c);
			//sD.submitOrder_Validate(input, c);
			//Thread.sleep(50000);
			sCom.orderUpdate_ValidateforNegativeQuerylocation(input, c);
			//sLR.orderUpdateLiteSR_Validate(input, c);
			//sLR.confirmServiceRequest_Validate(input, c);
			}
			else if(scenariotype.equalsIgnoreCase("SubmitOrder"))
			{
				sLR.bfcRequestLiteSR_Validate(input, c);
				//Thread.sleep(50000);
				//sD.queryContract_Validate(input, c);
				sD.getLocation_Validate(input, c);
				//Thread.sleep(70000);
				sD.submitOrder_Validate(input, c);
				//Thread.sleep(50000);
				sCom.orderUpdate_ValidateforNegativeQuerylocation(input, c);
				//sLR.orderUpdateLiteSR_Validate(input, c);
				//sLR.confirmServiceRequest_Validate(input, c);	
			}
		}
		
		else if(AQMethod.equalsIgnoreCase("CUSTOMER_PICKUP"))
		{
			sLR.bfcRequestLiteSR_Validate(input, c);
			//sD.queryContract_Validate(input, c);
			
		}
		else
		{
			sLR.bfcRequestLiteSR_Validate(input, c);
			//sD.queryContract_Validate(input, c);
			sLR.getLocation_Validate(input, c);
			sLR.orderUpdateLiteSR_Validate(input, c);
			//sLR.confirmServiceRequest_Validate(input, c);
		}
		
		 
	 }
	public void csgEniWebServiceCallsNegativeScenario(Object input, ScriptingContext c) throws ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException, ParserConfigurationException
	  {
		  
		  String Isnegative=c.getValue("SwivelCSG", "sik_SwivelCSG: IsNegative");
		  String Modify=c.getValue("SwivelCSG", "sik_SwivelCSG: MODIFYCALL");
		  if(Isnegative.equals("1"))
		  {
			  Thread.sleep(4000);
			  sC.csgENImsg_Validate(input, c);
			  //Thread.sleep(80000);
			  sC.QueryLocation_Validate(input, c);
			 // Thread.sleep(40000);	  
			  sC.submitOrder_Validate(input, c);
			 // Thread.sleep(40000);
			  sC.ModifyOrder_Validate(input, c);
			 // Thread.sleep(40000);
			  sC.orderUpdateNegative_SubmitOrderValidate(input, c);
		  } // sC.orderUpdate_Validate(input, c);
		  else
		  {
			  if(Modify.equalsIgnoreCase("NO"))
			  {
		  Thread.sleep(4000);
		  sC.csgENImsg_Validate(input, c);
		 // Thread.sleep(8000);
		  sC.QueryLocation_Validate(input, c);
		 // Thread.sleep(4000);	  
		  sC.submitOrder_Validate(input, c);
		  sC.orderUpdateNegative_SubmitOrderValidate(input, c);
			  }
			  else
			  {
				  Thread.sleep(4000);
				  sC.csgENImsg_Validate(input, c);
				 // Thread.sleep(80000);
				  sC.QueryLocation_Validate(input, c);
				 // Thread.sleep(40000);	  
				  sC.submitOrder_Validate(input, c);
				 // Thread.sleep(40000);
				  sC.ModifyOrder_Validate(input, c);
				  sC.orderUpdate_Validate(input, c);
			  }
		  }
}
public void csgWebServiceCallsDTA(Object input, ScriptingContext c) throws Exception
	  {
		  Application.showMessage("The Result before the Execution is::"+sD.OutPut);
		  Thread.sleep(100000);
		  DTAOM_trackCode DTA=new DTAOM_trackCode();
		  //String Flag=c.getValue("dS_SIK","sik_DTACSG: FlagEnabled");
		  //Application.showMessage("Flag ::"+Flag);
		  //String Operation=c.getValue("dS_SIK","sik_DTACSG: Operation");
		  String AccountNumber=c.getValue("SwivelCSG", "sik_SwivelCSG: AccountId_ITEMLIST");
		  Thread.sleep(4000);
		  DTA.csgENImsg_Validate(input, c);
		  sC.QueryLocation_Validate(input, c);
		  Thread.sleep(8000);	  
		 /* //sC.submitOrder_Validate(input, c);
		 // sC.orderUpdate_Validate(input, c);
			 
			  sD.bfcRequest_Validate(input, c);
			  Thread.sleep(20000);
			  sD.getLocation_Validate(input, c);
			  Thread.sleep(3000);
			  sD.queryContract_Validate(input, c);
			  Thread.sleep(7000);*/
			 // sD.submitOrder_Validate(input, c);
			  DTA.submitOrder_Validate(input, c);
			  sC.orderUpdate_Validate(input, c);

	  }
	public void sikCSKDTAInitializationCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
	 {

		 String T1=sL.getTimeInstance();
		 Application.showMessage("The start time is ::"+T1);
		 c.put("T1",T1);
		 sL.readValuesfromDTACSGExcel(input, c);
		 sL.baseMsgid_retrieval(input, c);
		 sL.getBaseTime(input, c);
		 Application.showMessage("The Base Time retrieved is ::"+c.get("BaseTime").toString());
		 Thread.sleep(2000);
	 }

}
