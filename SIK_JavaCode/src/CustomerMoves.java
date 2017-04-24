import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class CustomerMoves 
{
   sikLibraryClass sL=new sikLibraryClass();
   
    String OutPut = "Passed";
   
   public void custMovesInitializationCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
   {
	   String T1=sL.getTimeInstance();
		 Application.showMessage("The start time is ::"+T1);
		 c.put("T1",T1);
		 sL.getBaseTime(input, c);
  	 readCustomermovesExcel(input, c);
  	 sL.baseMsgid_retrieval(input, c);
   	 
   }
   
   public void updateALM(Object input, ScriptingContext c) throws Exception
   {
	   
	   String testSetID= c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: TestSetID");
	   String testCaseID=c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: TestCaseID");
	   Application.showMessage("Test Set ID working on is ::"+testSetID);
	   Application.showMessage("Test CaseID from ALM to update is  ::"+testCaseID);
	   
	 /*  Application.showMessage("Status to Update ALM is   ::"+ OutPut);
	   
	   com.comcast.neto.alm.ALMTestResultUpdater.updateRunStatus(testSetID, testCaseID, OutPut);*/

	  /* if(OutPut.equals("Passed"))
	   {
	   com.comcast.neto.alm.ALMTestResultUpdater.updateRunStatus(testSetID, testCaseID, "Passed");
	   }
	   else
	   {
	   com.comcast.neto.alm.ALMTestResultUpdater.updateRunStatus(testSetID, testCaseID, "No Run");   
	   }*/
	   	   	     	   
   }
   
   public void custMovesWebserviceCalls(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
   {	 
  	 
	     Application.showMessage("__________________________________________________________________");
		 Application.showMessage("\n");
		 Application.showMessage("Validation starts here..!");
		 Application.showMessage("__________________________________________________________________");
		 Thread.sleep(5000);
		 bfcRequest_Validate(input, c);
		 Thread.sleep(5000);
		// queryItem_Validate(input, c);
		 orderUpdateNegative_Validate(input, c);		 
		// updateALM(input, c);
   	 
   }
   
   public void readCustomermovesExcel(Object input, ScriptingContext c) throws Exception, Throwable,ClassNotFoundException, InterruptedException, IOException, XPathException, SAXException
   {
	   
	 String TcName,xmlName,Acc1,Acc2,LocID;
	 String dB_PasswordCommon,dB_UsernameCommon,XML_EndPoint,dB_serviceName,dB_Port,dB_Username,dB_Password,DB_Host,serviceRequestID;
	 
	 TcName=c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: TestCaseName");
	 xmlName=c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: InputXML");
	 Acc1=c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: AccountNo1");
	 Acc2=c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: AccountNo2");
	 LocID=c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: LocationID");
	 serviceRequestID=c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: serviceRequestID");
	 
	 dB_PasswordCommon=c.getValue("Aggregate_CustMoves", "ds_dataBase: Common_DBPassword");
	 dB_UsernameCommon=c.getValue("Aggregate_CustMoves", "ds_dataBase: Common_DBUserName");
	 XML_EndPoint=c.getValue("Aggregate_CustMoves", "ds_dataBase: XML_Enpoint");
	 dB_Port=c.getValue("Aggregate_CustMoves", "ds_dataBase: DB_Port");
	 dB_Username=c.getValue("Aggregate_CustMoves", "ds_dataBase: DB_UserName");
	 dB_Password=c.getValue("Aggregate_CustMoves", "ds_dataBase: DB_Password");
	 dB_serviceName=c.getValue("Aggregate_CustMoves", "ds_dataBase: DB_ServiceName");
	 DB_Host=c.getValue("Aggregate_CustMoves", "ds_dataBase: DB_HOST");
	 
	 

	 
	 c.put("pAcc1", Acc1);
	 c.put("pAcc2", Acc2);
	 c.put("pLocID", LocID);
	 c.put("pserviceRequestID", serviceRequestID);
	 c.setValue("InputXML",xmlName);
	 c.setValue("dB_PasswordCommon",dB_PasswordCommon);
	 c.setValue("dB_UsernameCommon",dB_UsernameCommon);
	 c.setValue("XML_EndPoint",XML_EndPoint);
	 c.setValue("dB_Port",dB_Port);
	 c.setValue("dB_Username",dB_Username);
	 c.setValue("dB_Password",dB_Password);
	 c.setValue("DB_Host",DB_Host);
	 c.setValue("dB_serviceName",dB_serviceName);
	 
	 
	 
	 Application.showMessage("__________________________________________________________________");
	 Application.showMessage("\n");
	 Application.showMessage("Test Case ::"+TcName+" "+"starts now...Reading Input Excel Data..!");
	 Application.showMessage("__________________________________________________________________");
	 Application.showMessage("The Environment connected is ::"+c.getValue("Aggregate_CustMoves","ds_dataBase: DB_ConnectionName"));
	 Application.showMessage("XML Category  is::"+c.getValue("InputXML"));
	 Application.showMessage("serviceRequestID is::"+c.get("pserviceRequestID"));
	 Application.showMessage("Destination Account Number is::"+c.get("pAcc1"));
	 Application.showMessage("Source Account Number is::"+c.get("pAcc2"));
	 Application.showMessage("Location ID  is::"+c.get("pLocID"));
	 
	 
	 
   }
   
   //QueryItem Validation Function
   //MoveCustomerEquipment
   
   public void bfcRequest_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("Time::"+Time);
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********bfcRequest_Validate Function**************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("bfcRequest"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("bfcRequest"));  
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:orderProcessorServicePort/bfcRequest'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	            String rowmsg;
				rowmsg = rs.getString(1);
	            Application.showMessage("MessageID is ::"+rowmsg);
	           
	            	
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);      
		            Application.showMessage("senddata is ::"+senddata); 
		            Application.showMessage("recievedata is ::"+recievedata); 
		          
	 	            
	 	            
		           
		            	String accountNumber_bfc= sL.nodeFromKey(recievedata,"AccountNo</bfcopcom:name><bfcopcom:value>","</bfcopcom:value></bfcopcom:parameters><bfcopcom:parameters><bfcopcom:name>LocationID");
		 	            Application.showMessage("accountNumber is ::"+accountNumber_bfc); 	
		 	            if(accountNumber_bfc==null)
			            {
				            c.report("Send Xml accountNumber is NULL");
				            continue;
			            }
		 	            else
		 	            {
				            if(accountNumber_bfc.equals(c.get("pAcc1")))
			            	{
				            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
				            	Application.showMessage("SEND XML is :: \n"+senddata);
			            		//System.out.printf("SEND XML is %s \n",senddata);
			            		Application.showMessage("*******Validation Point 2 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
			            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_bfc);
			            	    v1=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            
		 	            }
		 	            
		 	            
		 	            
		            break;
		            }
	             }
	    
	         
	         if(v1 ==1)
	         {
	         	Application.showMessage("WebService Call :: bfcRequest_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 OutPut="Failed";
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
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
   
   public void queryItem_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********queryContract_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'Inventoryservices:InventoryServicePort/queryItem' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
	         while (rs.next())
	         {
	        	
	        	  String rowmsg;
	              rowmsg = rs.getString(1);
	              Application.showMessage("MessageID is ::"+rowmsg);
		            
		        if(rowmsg.equals(c.getValue("BaseMsgid")))break;
		        
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                callFound=1;          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);   
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
		          	           
		           /* String accountNumber_QC= sL.nodeFromKey(senddata,"<ContractTypes:accountNumber>","</ContractTypes:accountNumber>");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_QC); 
	 	            if (accountNumber_QC==null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(accountNumber_QC.equals(c.getValue("accountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_QC);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	           // String maxRows= sL.GetValueByXPath(senddata,"/ContractServices:queryContract/ContractServices:resultSpec/ContractTypes:maxRows");
		            	String maxRows = sL.nodeFromKey(senddata,"<ContractTypes:maxRows>","</ContractTypes:maxRows>");

		            	Application.showMessage("maxRows is ::"+maxRows);
		 	            
		 	            if(maxRows==null)
			            {
				            c.report("Send Xml maxRows is NULL");
				            continue;
			            }
		            	
		 	            
		            break;
		            }*/
	             }
	         }
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: queryContract_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::queryContract_Validate is Failed with Validation Errors");
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
   
   
   
   public void moveCustomerEquipment_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
  	{
  		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
  	     sikLibraryClass sL = new sikLibraryClass();
  		 ResultSet  rs;
  		 int callFound=0;
  		 String xmldata1 ="receive_data";
  	     String xmldata2 ="SEND_DATA";
  	     String Time= c.get("BaseTime").toString();
  	     Application.showMessage("-----------------------------------------------------");
  	     Application.showMessage("*****moveCustomerEquipment_Validate Function*********");       
  	     Application.showMessage("----------------------------------------------------");
  	         
  		try
  		{
  			 Statement st =sL. dBConnect(input, c);	
  	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cs:CustomerServicePort/moveCustomerEquipment' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
  	         while (rs.next())
  	         {
  	        	
  	        	 String rowmsg;
  	             rowmsg = rs.getString(1);
  	             Application.showMessage("MessageID is ::"+rowmsg);
  		            
  		        if(rowmsg.equals(c.getValue("BaseMsgid")))break;
  		        
  	            if(rs.getBlob("receive_data")==null)
  	            {
  	            
  	            	Application.showMessage("Your Recieve XML is NULL \n");
  	            	
  	            	String senddata =sL.extractXml(rs,xmldata2);
  	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
  	            }
  	            else if(rs.getBlob("SEND_DATA")==null)
  	            {
  	            	Application.showMessage("Your SEND XML is NULL \n");	
  	            	String recievedata=sL.extractXml(rs,xmldata1);
  	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
  	            }
  	            else
  	            { 
  	                callFound=1;          
  		            String senddata = sL.extractXml(rs,xmldata2);           
  		            String recievedata = sL.extractXml(rs,xmldata1);
  		            String senddata_wf=sL.RemoveNameSpaces(senddata);
		            String recievedata_wf=sL.RemoveNameSpaces(recievedata);
  		            Application.showMessage("Recieve XML is::  \n"+ recievedata_wf);
  	            	Application.showMessage("SEND XML is :: \n"+senddata_wf);
  		            
  		          	String WorkOrdNumber=sL.GetValueByXPath(senddata_wf, "//cs:moveCustomerEquipment/cs:deviceMoveRequest/ct:workOrderNumber");
  	            	Application.showMessage("The Service Request ID Fetched from moveCustomerEquipment is ::"+WorkOrdNumber);  	            	
  		          
  	 	            if (WorkOrdNumber==null)
  	 	            {
  	 	            	continue;
  	 	            }
  	 	            else if(WorkOrdNumber.equals(c.get("pserviceRequestID")))
  	            	{
  		            	
  	            		Application.showMessage("*******Validation Point 1 :: WebServicall-pserviceRequestID Call********");
  	            		Application.showMessage("Validation is Successfull with Input serviceRequestID"+c.get("pserviceRequestID"));
  	            		callFound=1;
  	            	}
  	            	else
  	            	{
  	            		continue;
  	            	}
  	            	
  		            if(callFound==1)
  		            {
  		                 
  		              	
  		 	         		           
  		            }
  	             }
  	         }
  	         
  	         if(callFound ==1)
  	         {
  	         	Application.showMessage("WebService Call :: queryContract_Validate is Success[All validation points are Success]");
  	         }
  	         else
  	         {
  	        	 OutPut="Failed";
  	        	 c.put("result", "false");
  	        	 c.report("WebService Call ::queryContract_Validate is Failed with Validation Errors");
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
   
   
   
   public void orderUpdateNegative_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
   {
          Thread.sleep(8000); // Think time in JVM [waits for 10 secs here]
          sikLibraryClass sL = new sikLibraryClass();
          ResultSet  rs;
          int callFound=0,v1=0,v2=0,v3=0;
          String xmldata1 ="receive_data";
          String xmldata2 ="SEND_DATA";
          String Time= c.get("BaseTime").toString();
         Application.showMessage("-----------------------------------------------------");
         Application.showMessage("**********orderUpdate_Validate Function************");       
         Application.showMessage("----------------------------------------------------");
         HashMap Operation=sL.findingoperations(input, c);
         c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
         	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));

          try
          {
         //   Statement st =sL. dBConnect(input, c);  
         //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
        	  rs=sL.reduceThinkTimeSIK(input, c);
            while (rs.next())
            {
                 
           
             String rowmsg;
             rowmsg = rs.getString(1);
             Application.showMessage("MessageID is ::"+rowmsg);
              if(rs.getBlob("receive_data")==null)
               {
               
                 Application.showMessage("Your Recieve XML is NULL \n");
                 
                 String senddata =sL.extractXml(rs,xmldata2);
                 Application.showMessage("Your Recieve XML is::\n"+senddata);
                
                  String senddata_wf = sL.RemoveNameSpaces(senddata);
                  Application.showMessage("Well formed XML is "+senddata_wf);
                             
                  String id= sL.GetValueByXPath(senddata_wf, "/comRequest/id");
                 //String errsrc= sL.nodeFromKey(senddata,"<value name=\"errorSource\">","</value><value name=\"orderType\">");
                  Application.showMessage("Service Request id  is ::"+id);    
                  String serID=c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: serviceRequestID"); 
                  if(id== null)
                  {
                    continue;
                  }
                  else if(id.equals(serID))
                  {
                     
                     //System.out.printf("SEND XML is %s \n",senddata);
                     Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
                     Application.showMessage("Validation is Successfull with Input ServiceRequst ID::"+id);
                     callFound=1;
                 
                  }
                  else
                  {
                     continue;
                  }
                        
                 

                     String ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
                     Application.showMessage("accountNumber is ::"+ou_AccountNumberid);    
                      if(ou_AccountNumberid== null)
                     {
                       continue;
                     }
                     else if(ou_AccountNumberid.equals(c.get("pAcc1")))
                     {
                        
                        //System.out.printf("SEND XML is %s \n",senddata);
                        Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
                        Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
                     
                        v1=1;
                     }
                   
                 

                      if(callFound==1)
                      {
                
                             String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"ordStatus\">");
                            Application.showMessage("productType is ::"+ou_productType);
                            
                             if(ou_productType==null)
                             {
                                   c.report("Send Xml productType is NULL");
                                   continue;
                             }
                             else
                             {
                                Application.showMessage("productType Send  is ::"+" "+ou_productType);
                                if(ou_productType.equalsIgnoreCase("CMOVES"))
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



                          
                            
                            
                            
                           
                             String OrdStatus= sL.GetValueByXPath(senddata_wf, "//comRequest/header/value[2]");
                             Application.showMessage("Order Status is ::"+OrdStatus); 
                             
                    
                  
                             String errCode= sL.GetValueByXPath(senddata_wf, "/comRequest/wlRequest/worklist/value[2]");
                             Application.showMessage("Error Code is ::"+errCode);
                             String Error_Code= c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: errorCode");
                                  Application.showMessage("Error Code from input is ::"+Error_Code);
                             c.put("ErrorCodeOu",errCode);
                             
                             if(errCode.equalsIgnoreCase(Error_Code))
                             {
                            	 Application.showMessage("Error Code Validated with Input ErrorCode..!");
                            	 v3=1;
                             }
                             else
                             {
                            	Application.showMessage("Error Code Validation Failure..!!!");
                             }
                          
                             String errmsg= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[3]");
                            //String errmsg= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorSource\">");
                             Application.showMessage("Error Message  is ::"+errmsg); 
                             c.put("ErrorTextOu",errmsg);
                          
                             String errsrc= sL.GetValueByXPath(senddata_wf, "//comRequest/device/device/value[4]");
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
 	        	   OutPut="Failed";
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
   
   
   
   public void CommonDBValidate(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, SQLException
   {
          sikLibraryClass  sL= new sikLibraryClass ();
          String serID=c.getValue("Aggregate_CustMoves", "ds_CustomerMoves: serviceRequestID");
          ResultSet rs1;
          Date todayDate = new Date();
          TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
          DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
          todayDateFormat.setTimeZone(timeZone);
          String strTodayDate = todayDateFormat.format(todayDate);
          Application.showMessage("Todays Date as per EST is::"+strTodayDate); 
          String Time= c.get("BaseTime").toString();
          try
          {
                 
                 Statement st1 =sL.dBConnectCommon(input, c);
                 
                
                 //Application.showMessage("Account Number is "+c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber"));
                
                 rs1 = st1.executeQuery("select * from(select * from cwpworklist where VOMORDERKEY ='"+serID+"' and to_char(CREATION_DATE, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"')where rownum < 2");
                 if(rs1==null)
                 {
                       Application.showMessage("Null Record set found in WorKlist DB for AccountNumber'"+c.get("pAcc1"));
                 }
         
            while(rs1.next())
          {
            String errorCode= rs1.getString("ERRORCODE");
            String errorSource=rs1.getString("ERRORSOURCE");
            Application.showMessage("Error Source from common is" +errorSource);
            String errorText=rs1.getString("ERRORTEXT");
            String PARTICIPANTOPERATION=rs1.getString("PARTICIPANTOPERATION");
         //String BILLINGSYSTEM=rs1.getString("BILLINGSYSTEM");
            String SERVICETYPE=rs1.getString("SERVICETYPE");
            String ORDER_ID=rs1.getString("ORDER_ID");
            Application.showMessage("Error Code is ::"+errorCode);
            Application.showMessage("errorSource is ::"+errorSource);
            Application.showMessage("PARTICIPANTOPERATION is ::"+PARTICIPANTOPERATION);
            Application.showMessage("errorText is ::"+errorText);
           // Application.showMessage("BILLINGSYSTEM is ::"+BILLINGSYSTEM);
            Application.showMessage("SERVICETYPE is ::"+SERVICETYPE);
            Application.showMessage("ORDER_ID is ::"+ORDER_ID);
            
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
            
            if(errorSource.equals(c.get("ErrorSourceOu")))
            {
                  Application.showMessage("Error SOURCE from order update is validated successfully with error Source from common DB"); 
            }
            else
            {
                  Application.showMessage("ERROR SOURCE IS NOT VALIDATED");
 	        	 

            }
            
            
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
   
  
}
