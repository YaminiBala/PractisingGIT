import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.json.JSONException;
import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class XHOM_RTP_ValidationMethods 
{
	
	
		XHOM_DemiInstall_Validations DIV = new XHOM_DemiInstall_Validations();
		XHOM_HomeStream_Validations HS=new XHOM_HomeStream_Validations();      
	XHOM_BasicMethods BM=new XHOM_BasicMethods();
	XHOM_CVR_ValidationCalls cvrv = new XHOM_CVR_ValidationCalls();
	XHOM_DemiDisconnect_Validations DD=new XHOM_DemiDisconnect_Validations();
	//----Yamini
public void AddressUpdate_demicallsCLS(Object input , ScriptingContext c ) throws Exception
	{

	    Thread.sleep(4000);
        BM.rtpTestInterface_Validate( input,  c);
        HS.getAccoutCos_Validate(input, c);
         
        DIV.getHomeSecurityInfo_Validate( input, c);
        DIV.processHomeSecurityInfo_Validate(input,c);
       
        if(c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_ScenarioName").equalsIgnoreCase("Demi"))
        {
        DIV.queryLocation_Validate(input,c);
       	DIV.SetAccountBasicInformation_Validate(input,c);
        DIV.SetAccountBasicInformation_Validate(input,c);
        HS.UpdateAccount_Validate(input, c);
        DIV.responderInfo_Validate(input,c);
        DIV.SetAccountAuthorityInformation_Validate(input, c);
        DIV.modifyHomeSecurity_Validate(input, c);
        DIV.orderUpdate_Validate_AddressUpdate(input,c,"CHG",c.getValue("sSERVICEorCURRENTSERVICE"));
        }
        else
        {
        	HS.UpdateAccount_Validate(input, c);
        	DIV.orderUpdate_Validate_AddressUpdate(input,c,"CHG",c.getValue("sSERVICEorCURRENTSERVICE"));
        }



	}
	
//---------------

public void Insightcalls_cls(Object input , ScriptingContext c ) throws Exception
	 {
		 
		    
         Thread.sleep(40000);
        RTP_ValidationsClass vC=new RTP_ValidationsClass();
             LibraryRtp lR= new LibraryRtp();
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
		  val= BM.rtpTestInterface_Validate(input, c);           		  
		   break;
	   case 1:
		   val=   HS.CLSgetAccount_Validate(input, c);
		   break;
	   case 2:
		   val=  DIV.queryLocation_Validate(input,c); 
		   
		   break;
	   case 3:
		   val=  DIV.processHomeSecurityInfo_Validate(input,c);
		   break;
	   case 4:
		   val=  HS.CLS_createAccount_Validate(input, c);
		   break;
	   case 5:
		   val=  DIV.orderUpdate_Validate(input,c,"NEW",c.getValue("sSERVICEorCURRENTSERVICE"));
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

 public void RestoreValidations_CLS(Object input,ScriptingContext c)throws Exception 
	    {
	                Thread.sleep(50000);
	                HS.getAccoutCos_Validate(input, c);
	                DIV.queryLocation_Validate(input,c);
	                DD.processHomeSecurityInfo_Validate_Res(input, c);
	                DD.Activate_COPS_validate(input, c);
	                HS.RestoreAccount_Validate(input, c);
	                DD.orderUpdate_Validate_Res(input, c);
	    }
	 
public void InsightSuspendValidations_CLS(Object input,ScriptingContext c)throws Exception 
	    {
	    
	                
	                          
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
	             		  val=  HS.CLSgetAccount_Validate(input, c);       		  
	             		   break;
	             	   case 1:
	             		   val=  DIV.queryLocation_Validate(input,c);
	             		   break;
	             	   case 2:
	             		   val=   DD.processHomeSecurityInfo_Validate_Sus(input, c);
	             		   break;
	             	   case 3:
	             		  //val=  HS.SuspendAccount_Validate(input, c);
	             		   break;
	             	   case 4:
	             		   val=  DIV.orderUpdate_Validate(input, c,"NPD",c.getValue("sSERVICEorCURRENTSERVICE"));
	             		   val="true";
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
	
	 public void Insightcalls(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, Exception
	    {    
		 
		     Thread.sleep(4000);
		     BM.rtpTestInterface_Validate(input, c);     
		     HS.CLSgetAccount_Validate(input, c);
			 DIV. orderUpdate_ValidateforScheduleUpgradedowngrade(input, c,"NEW",c.getValue("sSERVICEorCURRENTSERVICE"));

		   //  DIV.orderUpdate_Validate(input, c,"NEW",c.getValue("sSERVICEorCURRENTSERVICE"));
		     InsightcallsReport(input , c );
    
	    }
	 
	 //---------------
	 
	 public void InsightcallsReport(Object input , ScriptingContext c ) throws Exception
	    {
		 
		 XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	   
	    	
		 List<String> InsightcallsReport=new ArrayList<String>();
		 InsightcallsReport.add(c.get("xhsEventIssues").toString());
		 InsightcallsReport.add(c.get("NewgetAcc").toString());
		// InsightcallsReport.add(c.get("setOrderstatusIssue").toString());  
		 XH.generateReport(input, c, InsightcallsReport, "InsightInstall Functionality Validation-->");
			
	    	  
		 
	    }
	 
	 //----------
	 
	
	 public void IgnoreWebserviceCallsValidations(Object input, ScriptingContext c) throws Exception
	 {
		 LibraryRtp lC=new LibraryRtp();
		 XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
		 lC.printStart(input, c, "Validation Starts..!");
		 BM.rtpTestInterface_Validate(input, c);
		BM.NoDataSetFound(input, c);
		 lC.printEnd(input, c, "Validation Ends..!");
		List<String> IgnoreIssue=new ArrayList<String>();
		IgnoreIssue.add(c.get("xhsEventIssues").toString());
		
		IgnoreIssue.add(c.get("IgnoreWorklistsIssues").toString());
		
		 XH.generateReport(input, c, IgnoreIssue, "Ignore Worklists Issues-->");
		
	 }
	 
	 
	 //------------
	 public void CosJasperORnumerix(Object input,ScriptingContext c) throws Exception

     {
//Thread.sleep(40000);
//	DIV.orderUpdateCos_Validate(input, c);
		 XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
         List<String> JasorNumIssues=new ArrayList<String>();

            Application.showMessage("ICCID Value is::"+c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId"));

            if (c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId").equalsIgnoreCase("8901650507770007899"))
            {
                   
            	DD.Jasper_Validate(input, c);//JasVal
            	JasorNumIssues.add(c.get("JasVal").toString());
            	DD.AnotherJasper_Validate(input, c);//AnotherJasVal
            	JasorNumIssues.add(c.get("AnotherJasVal").toString());
            	 if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SERVICEorCURRENTSERVICE").equalsIgnoreCase(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE")))
            	 {
            	 DIV.orderUpdate_Validate(input, c, "NEW", c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SERVICEorCURRENTSERVICE"));
            	 //setOrderstatusIssue
            	 JasorNumIssues.add(c.get("setOrderstatusIssue").toString());
            	 }
            	 else
            	 {
            	 DIV.orderUpdate_Validate(input, c, "CHG", c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE"));
            	 //setOrderstatusIssue
            	 JasorNumIssues.add(c.get("setOrderstatusIssue").toString());
            	 }
            }
         
             else if (c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId").equalsIgnoreCase("8901640100735449527"))
             {
            	 DD.Numerix_Validate(input, c);//Numerex
            	 JasorNumIssues.add(c.get("Numerex").toString());
            	
            	 DD.Numerix_ValidateAddAssociation(input, c);//NumerexaddAsso
            	 JasorNumIssues.add(c.get("NumerexaddAsso").toString());
            	 if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SERVICEorCURRENTSERVICE").equalsIgnoreCase(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE")))
            	 {
            	 DIV.ErrororderUpdate_Validate(input, c, "NEW", c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SERVICEorCURRENTSERVICE"),"NUMEREX-RESP-CODE-300");
            	 //errorSetOrderSttaus
            	 JasorNumIssues.add(c.get("errorSetOrderSttaus").toString());
            	 }
            	 else
            	 {
            	 DIV.orderUpdate_Validate(input, c, "CHG", c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE"));
            	 JasorNumIssues.add(c.get("setOrderstatusIssue").toString());
            	 }
            	 
            	 
             }
            else
            {
                   Application.showMessage("iccid is not equal to any of the above "+c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId"));
            }
            
     XH.generateReport(input, c, JasorNumIssues, "Jsper or numerx Validate in Completed Transactions-->");
                                                     
                             }
	 
	 //------------------
	 
	
	 
	 //----------------
	 
	 public void demicalls_Intrado(Object input , ScriptingContext c ) throws Exception
	 {

	            //Thread.sleep(40000);
	         
	            BM.rtpTestInterface_Validate(input, c);
	       
	            DIV.queryLocation_Validate(input, c);
	            DIV.responderInfo_Validate(input, c);
	            DIV.OrderUpdate_tocheckAEE(input, c);
 
	   }
     


	 public void demicalls_DDS(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, InstantiationException, IllegalAccessException, SQLException, ParserConfigurationException
	 {

	            
		 BM.rtpTestInterface_Validate(input, c);
		 DIV.processHomeSecurityInfo_Validate(input, c);
	    DIV.OrderUpdate_tocheckAEE(input, c);
	    
	    
	   }





















            
 


	 public void demicalls_Normal(Object input , ScriptingContext c ) throws Exception
	    {
	    
	                
	                 RTP_ValidationsClass vC=new RTP_ValidationsClass();
	                 LibraryRtp_UpDown lR= new LibraryRtp_UpDown();
	                 RTP_ValidationsClass rV=new RTP_ValidationsClass();
	                         
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
	            		  val= BM.rtpTestInterface_Validate(input, c);             		  
	            		   break;
	            	   case 1:
	            		   val= DIV.getCustomerPermitRequirements_Validate(input,c);
	            		   break;
	            	   case 2:
	            		   val= DIV.createCMSAccountID_Validate(input, c);
	            		   break;
	            	   case 3:
	            		   val= DIV.queryLocation_Validate(input,c);
	            		   break;
	            	   case 4:
	            		   val=  HS.CLSgetAccount_Validate(input, c);
	            		   break;
	            	   case 5:
	            		   val=  DIV.processHomeSecurityInfo_Validate(input,c);
	            		   break;
	            	   case 6:
	            		   val=   HS.CLS_createAccount_Validate(input, c);;
	            		   break;
	            	   case 7:
	            		   val=  DIV.responderInfo_Validate(input,c);;
	            		   break;
	            	   case 8:
	            		   val=  DIV.SetAccountBasicInformation_Validate(input,c);
	            		   break;
	            	   case 9:
	            		   val=  DIV.SetAccountAuthorityInformation_Validate(input, c);;
	            		   break;
	            	   case 10:
	            		   val=   DIV.modifyHomeSecurity_Validate(input, c);;
	            		   break;
	            	   case 11:
	            		   val= DIV.orderUpdate_Validate(input, c,"NEW",c.getValue("sSERVICEorCURRENTSERVICE"));
	            		   break;
	            		   default:
	            			   break;
	            	   }
	            	   if(val.equalsIgnoreCase("false"))
	            	   {
	            		   c.report("Quiting Validating calls if prior validation calls not available within time in Environment- Validation calls could not found on time as per dynamic think time so its not validating further calls and quiting entire flow validation");
	            		   break OUTER1;
	            	   }
	            	   else
	            	   {
	            		   continue OUTER1;
	            	   }
	               }
	               
	               

	          
	        
	        
	             }
	 
	 
	 //------------
	 
	 
	 public void demicallsgeodetic_normal(Object input , ScriptingContext c ) throws Exception
     {
            Thread.sleep(24000);
            RTP_ValidationsClass vC=new RTP_ValidationsClass();
            FunctionLibrary_A fA=new FunctionLibrary_A();
            foxtrot FT=new foxtrot();
            LibraryRtp lR= new LibraryRtp();
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
         		  val= BM.rtpTestInterface_Validate(input, c);              		  
         		   break;
         	   case 1:
         		   val=  DIV.createCMSAccountID_Validate(input, c);  
         		   break;
         	   case 2:
         		   val=  HS.CLSgetAccount_Validate(input, c);
         		   break;
         	   case 3:
         		   val= DIV.responderInfofailed_Validate(input, c);
         		   break;
         	   case 4:
         		   val=  DIV.geodeticQuery_Validate(input, c);
         		   break;
         	   case 5:
         		   val= "TRUE"/*  FT.orderUpdateFOX_Validate(input, c)*/;
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
	 
	 //---------------
	 
	 
	 
	 
	 
	 public void demicalls(Object input , ScriptingContext c ) throws Exception
	    {
	    
	                 
		 DIV.createCMSAccountID_Validate(input, c);	      
		 DIV.orderUpdate_Validate(input, c,"NEW",c.getValue("sSERVICEorCURRENTSERVICE"));
	         DIV.storeCCnum(input, c);
	         demicallsReport(input , c );
	    }
	 
	 //-----------
	 public void demicallsReport(Object input , ScriptingContext c ) throws Exception
	    {
		 
		 XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	   
	    	
		 List<String> demicallsReport=new ArrayList<String>();
		 demicallsReport.add(c.get("createCMSAccountIDIssue").toString());
		 demicallsReport.add(c.get("setOrderstatusIssue").toString());  
		 XH.generateReport(input, c, demicallsReport, "DemiInstall Functionality Validation-->");
			
	    	  
		 
	    }
	
	 
	 //-----------
	 
	 public void CosValidations_normal(Object input, ScriptingContext c) throws Exception
	    {
	    	 LibraryRtp lR= new LibraryRtp();
	    	ChangeOfServiceClass CS = new ChangeOfServiceClass();
	        HandleInvalidServices hI=new HandleInvalidServices();    
	    	String val="true";
	        int noofCallsValidated=2;
	        int i;
	        OUTER1:
	        for( i=0;i<=noofCallsValidated;i++)
	        {
	     	   Application.showMessage("The val of I is"+i);
	     	   switch(i)
	     	   {
	     	   case 0:
	     		  val= HS.getAccoutCos_Validate(input, c) ;        		  
	     		   break;
	     	   
	     	   case 1:
	     		   BM.cosLogicFlow_CLS(input, c, c.getValue("sSERVICEorCURRENTSERVICE"),(String)c.get("NEWSERVICE"));
	     		      
	     		   break;
	     	   case 2:
	     		   val= DIV.orderUpdate_Validate(input, c,(String)c.get("Status"),(String)c.get("NEWSERVICE"));
	     		   break;
	     	   
	     		   default:
	     			   break;
	     	   }
	     	   if(val.equalsIgnoreCase("false"))
	     	   {
	     		  c.report("Quiting Validating calls if prior validation calls not available within time in Environment- Validation calls could not found on time as per dynamic think time so its not validating further calls and quiting entire flow validation"); break OUTER1;
	     	   }
	     	   else
	     	   {
	     		   continue OUTER1;
	     	   }
	        }
	        
}
	 
	 //--------------
	 
	 
	  public void SuspendValidations_normal(Object input,ScriptingContext c)throws Exception 
	    {
	                LibraryRtp lR= new LibraryRtp();
	                //lR.LoadValuestoGlobalList(input, c);
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
	             		  val=  HS.getAccoutCos_Validate(input, c)    ;    		  
	             		   break;
	             	  
	             	   case 1:
	             		   val=  DD.processHomeSecurityInfo_Validate_Sus(input, c);
	             		   break;
	             	   case 2:
	             		   val=  DD.suspend_COPS_validate(input, c);
	             		   break;
	             	   case 3:
	             		   val=  HS.SuspendAccount_Validate(input, c);
	             		   break;
	             	   case 4:
	             		   val= DIV.orderUpdate_Validate(input, c,"NPD",c.getValue("sSERVICEorCURRENTSERVICE"));
	             		   break;
	             	  
	             		   default:
	             			   break;
	             	   }
	             	   if(val.equalsIgnoreCase("false"))
	             	   {
	             		  c.report("Quiting Validating calls if prior validation calls not available within time in Environment- Validation calls could not found on time as per dynamic think time so its not validating further calls and quiting entire flow validation");  break OUTER1;
	             	   }
	             	   else
	             	   {
	             		   continue OUTER1;
	             	   }
	                }
	                
}
	  
	  //-------------------------
	  
	  
	  public void RestoreValidations_normal(Object input,ScriptingContext c)throws Exception 
	    {
	                LibraryRtp lR= new LibraryRtp();
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
	             		  val=  HS.getAccoutCos_Validate(input, c) ;      		  
	             		   break;
	             	   
	             	   case 1:
	             		   val=  DD.processHomeSecurityInfo_Validate_Res(input, c);
	             		   break;
	             	   case 2:
	             		   val=  DD.Activate_COPS_validate(input, c);
	             		   break;
	             	   case 3:
	             		   val=   HS.RestoreAccount_Validate(input, c);
	             		   break;
	             	   case 4:
	             		   val=DIV.orderUpdate_Validate(input, c,"RES",c.getValue("sSERVICEorCURRENTSERVICE"));
	             		   break;
	             	   
	             		   default:
	             			   break;
	             	   }
	             	   if(val.equalsIgnoreCase("false"))
	             	   {
	             		  c.report("Quiting Validating calls if prior validation calls not available within time in Environment- Validation calls could not found on time as per dynamic think time so its not validating further calls and quiting entire flow validation"); break OUTER1;
	             	   }
	             	   else
	             	   {
	             		   continue OUTER1;
	             	   }
	                }
	    }   
	                

	    //-----------
	  
	  public void CancelValidations_normal(Object input,ScriptingContext c)throws Exception
	    {
		  Application.showMessage("Entering");
	                LibraryRtp lR= new LibraryRtp();
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
	             		  val= HS.getAccoutCos_Validate(input, c);	  
	             		   break;
	             	   case 1:
	             		   val=  DIV.queryLocation_Validate(input,c);
	             		   break;
	             	   case 2:
	             		   val=  DD.processHomeSecurityInfo_Validate_Dis(input, c);
	             		   break;
	             	   case 3:
	             		   val= HS.deleteUnactivatedAccount_Validate(input, c);
	             		   break;
	             	   case 4:
	             		   val= DD.DisconnectAccount_CANCEL_Validate(input, c);
	             		   break;
	             	   case 5:
	             		   val=  DIV.orderUpdate_Validate(input, c,"CAN",c.getValue("sSERVICEorCURRENTSERVICE"));
	             		   break;
	             	   
	             		   default:
	             			   break;
	             	   }
	             	   if(val.equalsIgnoreCase("false"))
	             	   {
	             		  c.report("Quiting Validating calls if prior validation calls not available within time in Environment- Validation calls could not found on time as per dynamic think time so its not validating further calls and quiting entire flow validation");break OUTER1;
	             	   }
	             	   else
	             	   {
	             		   continue OUTER1;
	             	   }
	                }
	    }
	                
	                
//----------------------
	  public void Insightcalls_CVR(Object input , ScriptingContext c ) throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException, Exception
	    { 
	    
	    	
	    	Thread.sleep(40000);
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
	     		  val=    BM.rtpTestInterface_Validate(input, c);            		  
	     		   break;
	     	   case 1:
	     		   val=  DIV.queryLocation_Validate(input,c);    
	     		   break;
	     	   case 2:
	     		   val= HS.CLSgetAccount_Validate(input, c);
	     		   break;
	     	   case 3:
	     		   val= DIV.processHomeSecurityInfo_Validate(input,c); 
	     		   break;
	     		   //Update as per requirement
	     	   /*case 4:
	     		   val=  rval.orderUpdate_Validate(input,c);
	     		   break;*/
	     	   
	     	  
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
	        	//cvrv.processHomeSecurityInfoCVR_Validate(input, c);
	        	
	       	 
	        }
	    	else
	    	{
	    		Application.showMessage(" No need of CVR validation");
	    	}
	    }
		
	 public void CosValidations_CVR(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
	    {
		 //Add once done by yamini
	    	//rval.CosValidations_normal(input, c);
	    	
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
	         		  val=  cvrv.processHomeSecurityInfoCVR_Validate(input, c,"ADD");        		  
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
	          		  val=  cvrv.processHomeSecurityInfoCVR_Validate(input, c,"ADD");        		  
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
	  
	     public void InsightRestoreValidations_CLS(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException, Exception 
	    {
	                LibraryRtp lR= new LibraryRtp();
	                
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
	             		  val=   HS.CLSgetAccount_Validate(input, c);            		  
	             		   break;
	             	   case 1:
	             		   val=  DIV.queryLocation_Validate(input,c);
	             		   break;
	             	   case 2:
	             		   val=  DD.processHomeSecurityInfo_Validate_Res(input, c);
	             		   break;
	             	   case 3:
	             		//   val=  rC.CLS_RestoreAccount_Validate(input, c);       
	             		   break;
	             	   case 4:
	             		  val=DIV.orderUpdate_Validate(input, c,"RES",c.getValue("sSERVICEorCURRENTSERVICE"));
	             		  
	             		   
	                       
	             		   break;
	             	
	             		   default:
	             			   break;
	             	   }
	             	   if(val.equalsIgnoreCase("false"))
	             	   {
	             		   c.report("Validation calls couldnt found on time as per dynamic think time so its quiting entire flow validation");
	             		   break OUTER1;
	             	   }
	             	   else
	             	   {
	             		   continue OUTER1;
	             	   }
	                }
	    }
	 
	   public void InsightCancelValidations(Object input,ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException, Exception, NullPointerException, SAXException, JSONException
	    {
	              
	                Thread.sleep(50000);
	                HS.CLSgetAccount_Validate(input, c);               
	               DIV.queryLocation_Validate(input,c);
	                DD.processHomeSecurityInfo_Validate_Dis(input, c);
	                HS.deleteUnactivatedAccount_Validate(input, c);
	                //cC.DisconnectAccount_CANCEL_Validate(input, c);
	                DIV.orderUpdate_Validate(input, c,"CAN",c.getValue("sSERVICEorCURRENTSERVICE"));
	                /*if(c.get("INSIGHTCVR").equals("YES"))
	        {
	                cvr.processHomeSecurityInfoCVR_Validate(input, c);
	                 cvr.BNERcallCVR_Validate(input, c);
	                 
	        }
	                else
	                {
	                                Application.showMessage(" No need of CVR validation");
	                }
	                */
	    }
       
	    public void CancelValidations_cls(Object input,ScriptingContext c)throws Exception
	    {
		 CancelClass cC=new CancelClass();
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
	             		  val=  HS.CLSgetAccount_Validate(input, c);         		  
	             		   break;
	             	   case 1:
	             		   val=  DIV.queryLocation_Validate(input,c);
	             		   break;
	             	   case 2:
	             		   val=  DIV.processHomeSecurityInfo_Validate(input, c);
	             		   break;
	             	   case 3:
	             		   val=  HS.deleteUnactivatedAccount_Validate(input, c);  
	             		   break;
	             	   /*case 4:
	             		   val=     cC.orderUpdate_Validate(input, c);
	             		   break;*/
	             	  
	             	   
	             		   default:
	             			   break;
	             	   }
	             	   if(val.equalsIgnoreCase("false"))
	             	   {
	             		  c.report("Quiting Validating calls if prior validation calls not available within time in Environment- Validation calls could not found on time as per dynamic think time so its not validating further calls and quiting entire flow validation");break OUTER1;
	             	   }
	             	   else
	             	   {
	             		   continue OUTER1;
	             	   }
	                }
	                
	                

	           
	         
	         
	       
	    }
	         
	         
	               
	               
	               
	        
	  
	  
}
