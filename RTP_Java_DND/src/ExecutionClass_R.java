import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;

public class ExecutionClass_R {

                FunctionLibrary_R fR = new FunctionLibrary_R();

                RTP_ValidationsClass_UpDown rVal = new RTP_ValidationsClass_UpDown();
                RTP_SimulatorClass_UpDown rsUP = new RTP_SimulatorClass_UpDown();
                LibraryRtp_UpDown lRp = new LibraryRtp_UpDown();
                RTP_ValidationsClass vC = new RTP_ValidationsClass();
                FunctionLibrary_A fA = new FunctionLibrary_A();
                RTP_SimulatorClass_UpDown rSval = new RTP_SimulatorClass_UpDown();
                ChangeOfServiceClass cos = new ChangeOfServiceClass();
                RTP_SimulatorClass sim = new RTP_SimulatorClass();
                ThirtyDayDisconnect tD = new ThirtyDayDisconnect();
	CancelClass CC= new CancelClass();
	XHS_resumeFlows_PendingChange PR=new XHS_resumeFlows_PendingChange();
	CancelOnDisconnect cD = new CancelOnDisconnect();
	CVR_ScheduleUpdown cvrs=new CVR_ScheduleUpdown();
                String P3;
                
                public void IgnoreUCE15101(Object input,ScriptingContext c) throws ClassNotFoundException, XPathExpressionException, NullPointerException, InterruptedException, IOException, SAXException, SQLException
                {
                	
                	vC.getAccount_Validate(input, c);
                	
                	NoDataSetFound( input,  c);
                	
                }
                
                public void IgnoreUCE15101_CVR(Object input,ScriptingContext c) throws ClassNotFoundException, XPathExpressionException, NullPointerException, InterruptedException, IOException, SAXException, SQLException
                {
                	CVR_ValidationCalls  cv=new CVR_ValidationCalls ();
                	CVR_AllFlows_ValidationCalls validate=new CVR_AllFlows_ValidationCalls();
                	vC.getAccount_Validate(input, c);
                	cv.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
                	cvrs.scheduleBNERcallCVR_Validate(input, c, "null", "0", true);
                	NoDataSetFound( input,  c);
                	validate.threeDaycvrTableValidation("open", input, c);
                }
                
                public void IgnoreUCE15101_CVR_CLS(Object input,ScriptingContext c) throws ClassNotFoundException, XPathExpressionException, NullPointerException, InterruptedException, IOException, SAXException, SQLException
                {
                	CVR_ValidationCalls  cv=new CVR_ValidationCalls ();
                	CVR_AllFlows_ValidationCalls validate=new CVR_AllFlows_ValidationCalls();
                	vC.CLSgetAccount_Validate(input, c);
                	cv.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
                	
                	if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: SCENARIO_NUMBER").equalsIgnoreCase("3"))
                	{
                		cvrs.scheduleBNERcallCVR_Validate(input, c, "null", "0", true);
                    	NoDataSetFound( input,  c);
                    	validate.threeDaycvrTableValidation("open", input, c);
                    	validate.runglobalprocesscvr(input, c);
                   	    Thread.sleep(300000);   	  
                   		cv.DatasourceprocessHomeSecurityInfoCVR_Validate(input, c);
                   		cvrs.scheduleBNERcallCVR_Validate(input, c, "Disconnected", "0", false);
                	}
                	else
                	{
                		cvrs.scheduleBNERcallCVR_Validate(input, c, "Cancelled", "0", false);
                    	NoDataSetFound( input,  c);
                    	validate.threeDaycvrTableValidation("Completed", input, c);
                		
                	}
                }
                public void IgnoreUCE15101_CLS(Object input,ScriptingContext c) throws ClassNotFoundException, XPathExpressionException, NullPointerException, InterruptedException, IOException, SAXException, SQLException
                {
                	vC.CLSgetAccount_Validate(input, c);
                	NoDataSetFound( input,  c);
                }
                
                //-----------
                
                public void NoDataSetFound(Object input, ScriptingContext c)throws InterruptedException, ClassNotFoundException, IOException, SQLException 
                {
                	LibraryRtp  lC= new LibraryRtp ();
                                
                	ResultSet rs;              
                    int v1=0,i=0;
                    Thread.sleep(10000);
            		String Account=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_AccNumber");
            		Application.showMessage("Account is::"+Account);

            		String Time=(String) c.get("BaseTime");
            		Application.showMessage("BaseTime is::"+Time);
            		Statement st =lC.dBConnect(input, c);	
                    rs = st.executeQuery("select * from (select * from cwmessagelog where user_data2 = '"+Account+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");       
                    
            		Application.showMessage("The dataSet is::"+rs.toString());
            		
            		if(rs.getRow()==0)
            		{
            			Application.showMessage("No Record Sets Found..!");
            		}
            		else
            		{
            			while(rs.next())
            			{
            			      String operation=rs.getString("OPERATION");
            			      Application.showMessage("OPeration is ::"+operation);
            			      
            			    			      
            			       if(operation.equals("cmb:commonOrderService/orderUpdate"))
            			       {
            			           Application.showMessage("Extra call OrderUpdate Found");
            			           c.report("Order Update call found..!");
            			           v1=1;
            			           continue;
            			       }
            			       else
            			       {
            			    	   Application.showMessage("Extra call OrderUpdate Found");  
            			       }
            			      
            			      
            			     if(v1==1)
            			     {
            			          c.report("Extra Call Found");
            			          break;
            			     }
            			     else
            			     {
            			         Application.showMessage(i+"th row data is not an Extra call ");
            			     }
            			     i++;
            			}
                      st.close();
            		}	
                }
                //-----------------

                public void SusResStatusCheckValidation_ExceutionOrderPartOne(Object input,
                                                ScriptingContext c) throws ClassNotFoundException, IOException,
                                                InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException, InstantiationException, IllegalAccessException {
        Thread.sleep(6000);
                                vC.rtpTestInterface_Validate(input, c);
                                //vC.queryLocation_Validate(input, c);
                
                                rsUP.simGetAccountValidate(input, c);
                                //fA.processHomeSecurityInfo_Active_Validate(input, c);
                                //vC.createAccount_Validate(input, c);
                                Thread.sleep(10000);
                                //vC.orderUpdate_Validate(input, c);
                                vC.OrderUpdate_tocheckAEE(input, c);
                
                                //lRp.simulatorChange(input, c);

                }

                /*--------------------------------------------------------------------------------------------
                * 
                 *                                                                            Part one Fetching C-Central No
                * 
                 =----------------------------------------------------------------------------------------------------*/

                public void partone(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException, InstantiationException, IllegalAccessException, XPathExpressionException, NullPointerException, SAXException {
                	RTP_ValidationsClass_UpDown  rtpv=new RTP_ValidationsClass_UpDown ();
                	fR.createCMSAccountID_Validate(input, c);
                	rtpv.OrderUpdate_tocheckAEE(input, c);
                                String Ccnum = c.getValue("CcentralNum");

                                Application.showMessage("CcentralNumber:::" + Ccnum);

                                String Accountnumber = c.getValue("ACC_input");
                                Application.showMessage("Accountnumber:::" + Accountnumber);
                                lRp.mysqldbConnect(Ccnum, Accountnumber);
                                /*String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");
                                Application.showMessage("Status needs to be updated is ::" + status);*/
                                //lRp.simulatorChange30DayDisconnectHandleInvalidService(status, input, c);
                                Thread.sleep(50000);
                }

                public void Gprs_with_IccID(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException {
                                Thread.sleep(8000);
                                vC.queryLocation_Validate(input, c);
                                String statusCheck = "Open";
                                thirtyDisconnectTableValidation(statusCheck, input, c);
                                String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");
                                Application.showMessage("Status needs to be updated is ::" + status);
                             //   lRp.simulatorChange30DayDisconnectHandleInvalidService(status, input, c);
                                Thread.sleep(50000);

                                String IccID = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: iccId");
                                Application
                                                                .showMessage("IccID needs to be updated IN 30 DAY TABLE is ::"
                                                                                                + IccID);

                }

                public void SusResStatusCheckValidation(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException, XPathExpressionException, NullPointerException,
			SAXException, InstantiationException, IllegalAccessException {

		Application.showMessage("Validation starts for Demi....");
		Thread.sleep(10000);
		rSval.simGetAccountValidate(input, c);
		Thread.sleep(10000);
		// rSval.simGetAccountValidate(input, c);
		vC.queryLocation_Validate(input, c);
		c.put("Scenario", c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: SCENARIO_NUMBER"));
		if (c.get("Scenario").equals("4")) {
			Application.showMessage("during SUSPEND  validation");
			c.put("Status", c.getValue("RTPDataSourceCollections",
					"RTP_UpDownInputs: Status"));
			if (c.get("Status").equals("activated")) {
				fR.SuspendAccount_Validate(input, c);
				processHomeSecurityInfo_Validatesusres(input, c);
				fR.orderUpdate_Validate_SupressErrorSus(input, c);
			}

			else {
				processHomeSecurityInfo_Validatesusres(input, c);
				fR.orderUpdate_Validate_SupressErrorSus(input, c);
			}
		}

		else if (c.get("Scenario").equals("5")) {

			Application.showMessage("during Restore  validation");
			c.put("Status", c.getValue("RTPDataSourceCollections",
					"RTP_UpDownInputs: Status"));
			if (c.get("Status").equals("activated")) {
				Application.showMessage("during RESTORE  validation");
				fR.RestoreAccount_Validate(input, c);
				processHomeSecurityInfo_Validatesusres(input, c);
				fR.orderUpdate_Validate_IgnoreWorklistRestore(input, c);
			}

			else {

				processHomeSecurityInfo_Validatesusres(input, c);
				fR.orderUpdate_Validate_IgnoreWorklistRestore(input, c);

			}

		}

		else if (c.get("Scenario").equals("11")) {
			Application.showMessage("during CANCEL  validation");
			fR.deleteUnactivatedAccount_Validate(input, c);
			fR.DisconnectAccount_CANCEL_Validate(input, c);
			fR.processHomeSecurityInfo_Validate(input, c);
			rVal.orderUpdate_Validate(input, c);

		}
		// lRp.simulatorChange(input, c);
		lRp.storeCCnum(input, c);
		Thread.sleep(150000);

	}
	
	public void processHomeSecurityInfo_Validatesusres(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     String Time= c.get("BaseTime").toString();
	     c.setValue("processHomeSecurity","true");
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	     lC.findThinktimeOperationRTP(input, c);
	     c.setValue("processHomeSecurity","false");    
		try
		{
			// Statement st =lC. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' order by creation_time desc)where rownum <= 100");
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
			            	 if (c.get("Scenario").equals("4"))
			            	 {
			            	 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("SUSPENDED"))
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
			            	 else if (c.get("Scenario").equals("5"))
			            	 {
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
		 	            
		 	            String permitRequired_DDS= lC.nodeFromKey(senddata,"<typesDDS:permitRequired>","</typesDDS:permitRequired>");
		 	            Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
		 	           
		 	         	 	            
		 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata," <mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
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
	        // st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	

                public void ExecutionOrderPartOne_HandleInvalidService(Object input,
                                                ScriptingContext c) throws ClassNotFoundException, IOException,
                                                InterruptedException, SQLException, NullPointerException,
                                                XPathException, SAXException, InstantiationException, IllegalAccessException {

                               //  Thread.sleep(10000);
                                 rVal.demicalls(input, c);
                                /*String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");
                                Application.showMessage("Status needs to be updated is ::" + status);
                                                lRp.simulatorChange30DayDisconnectHandleInvalidService(status, input, c);
                                String Ccnum = c.getValue("CcentralNum");

                                Application.showMessage("CcentralNumber:::" + Ccnum);

                                String Accountnumber = c.getValue("ACC_input");
                                Application.showMessage("Accountnumber:::" + Accountnumber);
                                lRp.mysqldbConnect(Ccnum, Accountnumber);*/
                              //   Thread.sleep(50000);

                    }

                public void ExecutionOrderPartTwo_HandleInvalidService(Object input,
                                                ScriptingContext c) throws ClassNotFoundException, IOException,
                                                InterruptedException, SQLException {

                                 Thread.sleep(8000);
                                 vC.queryLocation_Validate(input, c);
                                String statusCheck = "Open";
                                thirtyDisconnectTableValidation(statusCheck, input, c);
                                /*String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");
                                Application.showMessage("Status needs to be updated is ::" + status);
                                lRp.simulatorChange30DayDisconnectHandleInvalidService(status, input, c);*/
                                     Thread.sleep(50000);

                    }

                public void ExecutionOrderPartOne_CosACTIVE(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException, NullPointerException, XPathException, SAXException, InstantiationException, IllegalAccessException {

                                
                                 rVal.demicallsReleasefrom30day(input, c);
                               
                                Thread.sleep(5000);

                    }
                //----------------
                
                public void ExecutionOrderPartOne_CosACTIVE30day(Object input, ScriptingContext c)
                        throws ClassNotFoundException, IOException, InterruptedException,
                        SQLException, NullPointerException, XPathException, SAXException, InstantiationException, IllegalAccessException {

        
         rVal.demicalls(input, c);
       
        Thread.sleep(5000);

}
                
                
                //------------------
	
	 public void ExecutionOrderPartOne_CosACTIVETerminalDetails(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, XPathException, SAXException, InstantiationException, IllegalAccessException
	    {
	    	   	
	      
		 LibraryRtp lC =new LibraryRtp();
		// lC.printtxtfile(input, c, "ReleaseFromThirytTable", "getTerminaldetails.txt");
	      	 rVal.demicallsReleasefrom30day(input, c);
	    
	    	
	    }
	    

                public void ExecutionOrderPartTwo_CosACTIVE(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException {

                               //  Thread.sleep(10000);
                                 vC.queryLocation_Validate(input, c);
                                
                                 vC.getCustomerPermitRequirements_Validate(input, c);
                                 Thread.sleep(5000);
                                String statusCheck = "Open";
                                thirtyDisconnectTableValidation(statusCheck, input, c);

                    }

                public void ExecutionOrderPartThree_CosACTIVE(Object input,
                                                ScriptingContext c) throws ClassNotFoundException, IOException,
			InterruptedException, SQLException, ParseException, XPathExpressionException, NullPointerException, SAXException {

                                 Thread.sleep(10000);
                               
		
                              //   vC.queryLocation_Validate(input, c);
                                String sN = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: SCENARIO_NUMBER");
                                String ExistingService=c.getValue("RTPDataSourceCollections",
                                        "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
                                String NewService=c.getValue("RTPDataSourceCollections",
                                        "RTP_UpDownInputs: NEWSERVICE");
                                if (sN.equals("2")) {
                                                 cos.cosLogicFlow_CLS(input, c, ExistingService, NewService);
                                                
                                } else {

                                                 vC.orderUpdate_Validate(input, c);
                                 }

                                String ComStatus = "Cancelled";// status u need to check against the
                                                                                                                                                                // 30Day Table

                                thirtyDisconnectTableValidation(ComStatus, input, c);

                                     Thread.sleep(3000);

                    }

                // ------------------------------------------------------------GET
                // TERMINALDETAILS-------------------------------------------------//

                public void ExecutionOrdergetTerminalDetailsParttwo(Object input,
                                                ScriptingContext c) throws ClassNotFoundException, IOException,
                                                InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException {
                                Thread.sleep(30000);
                                 vC.queryLocation_Validate(input, c);
                                // rS.simGetAccountValidate(input, c);
                                String Instatus = "Open";
                                thirtyDisconnectTableValidation(Instatus, input, c);
                                
                                 runGlobalProcess(input, c);
                                Thread.sleep(60000);
                                String ComStatus = "Completed";
                                // sL.simulatorChange30DayDisconnect(status, input, c);
	      	// sim.deactivateAccount_Validate(input, c);
                                rSval.processHomeSecurityInfo_Validate(input, c);
                                 sim.DisconnectAccount_CANCEL_Validate(input, c);
                                 rVal.orderUpdateDis_Validate(input, c);
                                 getTerminalDetails_Validate(input, c);
                                //String getTerminalDetailsStatus ="RETIRED_NAME";
                                String getTerminalDetailsStatus = c.getValue(
                                                                "RTPDataSourceCollections", "RTP_UpDownInputs: getTerminalStatus");
                                if (getTerminalDetailsStatus.equals("RETIRED_NAME")) {
                                                Application.showMessage("No EDIT TERMINAL Call ");
                                                // thirtyDisconnectTableValidation(ComStatus,input, c);//status
                                                // completed.

                                } else {

                                                 Jasper_Validate(input, c);

                                 }

                                String status ="ACTIVE";
                                /*String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");*/
                                //lRp.simulatorChange30DayDisconnectgetTerminalDetails(status, input, c);
                                thirtyDisconnectTableValidation(ComStatus, input, c);// status
                                                                                                                                                                                                                                                                // completed.

                    }
	
	 public void ExecutionOrdergetTerminalDetailsParttwowithSIMULATOR(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
	    {
	    	
		 Thread.sleep(9000);
		 LibraryRtp lC =new LibraryRtp();
		// lC.printtxtfile(input, c, "ReleaseFromThirytTable", "getTerminaldetails.txt");
		 CancelOnDisconnect cd=new CancelOnDisconnect();
		     String disconnectstatus=c.getValue("RTPDataSourceCollections","dB_Simulator: afterDisconnectStatus");
	    	 vC.queryLocation_Validate(input, c);
	    	 vC.getCustomerPermitRequirements_Validate(input, c);
	      	 //rS.simGetAccountValidate(input, c);
	      	 String Instatus="Open";
	      	 Thread.sleep(5000);
	      	 Application.showMessage("Waited for 5 seconds");
	      	System.out.println("Waited for 5 seconds");
	    	 thirtyDisconnectTableValidation(Instatus,input, c);
	    	 Thread.sleep(3000);
	    	
	       	 runGlobalProcess(input, c);
	    	 Thread.sleep(300000);
	    	 if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId").equalsIgnoreCase("8901640100735449527"))
	    	 {
	    		 tD.NumerixCos_Validate(input, c);
	    	 }
	    	 else
	    	 {
	    		 getTerminalDetails_Validate(input, c);
		    	 String getTerminalDetailsStatus= c.getValue("RTPDataSourceCollections","dB_Simulator: getTerminalStatus");
		    	 if(getTerminalDetailsStatus.equals("RETIRED_NAME"))
		    	 {    
		    		Application.showMessage("No EDIT TERMINAL Call ");
		    		System.out.println("No EDIT TERMINAL Call ");
		    		 Jasper_Validate(input, c);	   
		    	 }
		    	 else
		    	 {
		     	    		 Jasper_Validate(input, c);
		    				    	 }
	    	 }
	    	 if(disconnectstatus.equalsIgnoreCase("ACTIVE"))
	    	 {
	      	 String ComStatus="Completed";
	    	 //sL.simulatorChange30DayDisconnect(status, input, c);
	      	// sim.deactivateAccount_Validate(input, c);
	      //	Thread.sleep(3000);
	      	// rSval.processHomeSecurityInfo_Validate(input, c); 
	      	 sim.DisconnectAccount_CANCEL_Validate(input, c);
	      	 //rVal.orderUpdateDis_Validate(input, c);
	      	// cd.orderUpdate_COD(input, c);
	    	 
	    	 
	    	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
	    	
	    	 thirtyDisconnectTableValidation(ComStatus,input, c);//status completed.
	    	 }
	    	 
	    	 else
	    	 {
	    		// rSval.processHomeSecurityInfo_Validate(input, c); 
		      	// sim.DisconnectAccount_CANCEL_Validate(input, c);
	    		// CC.queryLocation_Validate(input, c);
	    		// CC.processHomeSecurityInfo_Validate(input, c);
	    		 
	    		 CC.DisconnectAccount_CANCEL_Validate(input, c);
	    		 CC.deleteUnactivatedAccount_Validate(input, c);
	    		 CC.orderUpdate_Validate(input, c);
	    		 //lRp.simulatorChange30DayDisconnectgetTerminalDetails(disconnectstatus, input, c);
	    	 }
	    }
	 
	 public void simulatorOff(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, SQLException, InterruptedException
	 {
		 String disconnectstatus="active";
		 lRp.simulatorChange30DayDisconnectgetTerminalDetails(disconnectstatus, input, c);
	 }
	 
	 public void ExecutionOrderPartOne_simInfoEX(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, XPathException, SAXException, InstantiationException, IllegalAccessException
	    {
	    	   	
	      	 Thread.sleep(7000);
	      	// rVal.demicalls(input, c);
	      	 rVal.demicallsReleasefrom30day(input, c);
	    	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
	 		 Application.showMessage("Status needs to be updated is ::"+status);
	 		 lRp.simulatorChange30DayDisconnectsimInfoEX(status, input, c);
	    	 Thread.sleep(50000);
	    	
	    }
	    
	 
	 public void ExecutionOrdersimInfoEXParttwowithSIMULATOR(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
	    {
	    	
	    	
	    	 
	    	 //vC.queryLocation_Validate(input, c);
	      	 //rS.simGetAccountValidate(input, c);
	      	 String Instatus="Open";
	      	Thread.sleep(9000);
	    	 thirtyDisconnectTableValidation(Instatus,input, c);
	    	 Thread.sleep(5000);
	       	 runGlobalProcess(input, c);
	    	 Thread.sleep(500000);
	      	 String ComStatus="Completed";
	    	 //sL.simulatorChange30DayDisconnect(status, input, c);
	      	// sim.deactivateAccount_Validate(input, c);
	      	 rSval.processHomeSecurityInfo_Validate(input, c); 
	      	 sim.DisconnectAccount_CANCEL_Validate(input, c);
	      	// rVal.orderUpdateDis_Validate(input, c);
	      	//cD.orderUpdate_COD(input, c);
	    	// getTerminalDetails_Validate(input, c);
	    	 String SIMINFOEXSTATUS= c.getValue("RTPDataSourceCollections","dB_Simulator: SimInfoEXStatus");
	    	 if(SIMINFOEXSTATUS.equalsIgnoreCase("GOODSTANDING") || SIMINFOEXSTATUS.equalsIgnoreCase("TESTMODE") || SIMINFOEXSTATUS.equalsIgnoreCase("AUTOACTIVATE") || SIMINFOEXSTATUS.equalsIgnoreCase("RMA") || SIMINFOEXSTATUS.equalsIgnoreCase("SUSPENDED") || SIMINFOEXSTATUS.equalsIgnoreCase("NWSUSPENDED") || SIMINFOEXSTATUS.equalsIgnoreCase("PENDING"))
	    	 {    
	    		 tD.NumerixCos_Validate(input, c);
	    		
	       	    //thirtyDisconnectTableValidation(ComStatus,input, c);//status completed.
	    	   
	    	 }
	    	 else
	    	 {
	    		 Application.showMessage("No NUMEREX CALL ");
	    		// Jasper_Validate(input, c);
	    		
	        	
	    	 }
	    	 
	    	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
	    	// lRp.simulatorChange30DayDisconnectgetTerminalDetails(status, input, c);
	    	 Thread.sleep(6000);
	    	 thirtyDisconnectTableValidation(ComStatus,input, c);//status completed.
	    		    	 
	    }
	    

                // --------------------------------------END OF GET TERMINAL
                // DETAILS--------------------------------------------------------------------------------------------//

                // *********************************************** Start of Deceased
                // Customer
                // ****************************************************************//

                public void ExecutionOrderPartOne_Deceased(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException, NullPointerException, XPathException, SAXException, InstantiationException, IllegalAccessException {

                                Thread.sleep(3000);
                                 rVal.demicalls(input, c);
                                /*String status="ACTIVE";
                                String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");
                                Application.showMessage("Status needs to be updated is ::" + status);// this                                                                                                                                                                                                                                                                                                     // is
                                String Ccnum=c.getValue("CcentralNum");
Application.showMessage("CcentralNumber::::"+Ccnum);
String accountNumber=c.getValue("ACC_input");
Application.showMessage("Accountnumber::::"+accountNumber);
lRp.mysqldbConnect(Ccnum, accountNumber);                */                                                                                                                                                                                                                                                           // to
                                                                                                                                                                                                                                                                                                                                // change
                                                                                                                                                                                                                                                                                                                                // the
                                                                                                                                                                                                                                                                                                                                // simulator
                                                                                                                                                                                                                                                                                                                                // settings
                                //lRp.simulatorChangeDeceasedCustomer(status, input, c);
                                     Thread.sleep(7000);

                      }

                public void ExecutionOrderPartTwo_Deceased(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException, XPathExpressionException, NullPointerException, SAXException {

                                 Thread.sleep(7000);
                                                 
                                /*String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");*/
                                //lRp.simulatorChangeDeceasedCustomer(status, input, c);
                                vC.queryLocation_Validate(input, c);
                                // rS.simGetAccountValidate(input, c);
                                String changeValue = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: ChangeReason");
                                if (changeValue.equals("Deceased"))

                                 {
                                                // disconnect calls.
                                                sim.deactivateAccount_Validate(input, c);
                                                rSval.processHomeSecurityInfo_Validate(input, c);
                                                sim.DisconnectAccount_CANCEL_Validate(input, c);
                                                rVal.orderUpdateDis_Validate(input, c);
                                 }

                                else {

                                                String Instatus = "Open";
                                                thirtyDisconnectTableValidation(Instatus, input, c);
                                 Thread.sleep(3000);

                    }

                    }
	
	 public void ExecutionOrderPartOne_Deceasedsimulator(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, NullPointerException, XPathException, SAXException
	    {
		 LibraryRtp lC =new LibraryRtp();
		// lC.printtxtfile(input, c, "ReleaseFromThirytTable", "deceasedsimulator.txt");
	      	// Thread.sleep(30000);
	      	 rVal.demicallsReleasefrom30day(input, c);
	      	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
			 Application.showMessage("Status needs to be updated is ::"+status);
			System.out.println("Status needs to be updated is ::"+status);//this is to change the simulator settings
			 lRp.simulatorChangeDeceasedCustomer_CLS(input, c);
	   	     Thread.sleep(7000);
	    	
	      }
	    
	    public void ExecutionOrderPartTwo_DeceasedSimulator(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException
	    {
	    	
	    	 Thread.sleep(7000);
	    	 LibraryRtp lC =new LibraryRtp();
			// lC.printtxtfile(input, c, "ReleaseFromThirytTable", "deceasedsimulator.txt");
	    	 String status= c.getValue("RTPDataSourceCollections","dB_Simulator: status");
	    	// lRp.simulatorChangeDeceasedCustomer(status, input, c);
	    	 //vC.queryLocation_Validate(input, c);
	      	 //rS.simGetAccountValidate(input, c);
	         String changeValue= c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: ChangeReason");
	      	 if(changeValue.equals("Deceased"))
	      		 
	      	 {
	      		 //disconnect calls.
	      		//sim.deactivateAccount_Validate(input, c);
	      		Thread.sleep(7000);
		      	rSval.processHomeSecurityInfo_Validate(input, c); 
		      	sim.DisconnectAccount_CANCEL_Validate(input, c);
		      	rVal.orderUpdateDis_Validate(input, c);
		      	//cD.orderUpdate_COD(input, c);
	      	 }
	      	 
	      	 else
	      	 {
	      		 
	      		 
	           	 String Instatus="Open";
	           	vC.queryLocation_Validate(input, c);
	           	vC.getCustomerPermitRequirements_Validate(input, c);
	           	Thread.sleep(9000);
	        	 thirtyDisconnectTableValidation(Instatus,input, c);
	        	 Thread.sleep(9000);
	        	 
	      	 }
	      	
	       
	    	 
	    }
	    

                // ******************************************END Of DECEASED CUSTOMER
                // **************************************************************************//
	    
	    public void thirtyDisconnectTableValidationSimulator(String Instatus,Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
	    {
	    	Thread.sleep(9000);
	    	Map<String,String> getMapData=new HashMap <String,String>();
	   	    getMapData=connectThirtyDayDisconnectDB(input, c);
	   	    String status=getMapData.get("status"); 	    
	   	    Application.showMessage("The status in validation process is ::"+status);
	   	    String date= getMapData.get("date");
	   	    Application.showMessage("The date in validation process is ::"+date);
	   	    String service= getMapData.get("service");
	   	    Application.showMessage("The service in validation process is ::"+service);
	     	Boolean p1=lRp.validationPointIgnoreCase(status, Instatus, input, c);
	     	String InService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
	     	Boolean p2=lRp.validationPoint(service, InService, input, c);
	     	
	    	    	
	    }

                public void thirtyDisconnectTableValidation(String Instatus, Object input,
                                                ScriptingContext c) throws ClassNotFoundException, IOException,
                                                InterruptedException {
                                Thread.sleep(3000);
                                String IccID="";
                                Map<String, String> getMapData = new HashMap<String, String>();
                                getMapData = connectThirtyDayDisconnectDB(input, c);
                                String status = getMapData.get("status");
                                Application.showMessage("The status in validation process is ::"
                                                                + status);
                                System.out.println("The status in validation process is ::"
                                        + status);
                                String date = getMapData.get("date");
                                Application.showMessage("The date in validation process is ::" + date);
                                System.out.println("The date in validation process is ::" + date);
                                
                                String service = getMapData.get("service");
                                Application.showMessage("The service in validation process is ::"
                                                                + service);
                                System.out.println("The service in validation process is ::"
                                        + service);
                                String dataInputIccID = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: iccId");
                                /* TestCaseName TC5_US442585 change started */
                                if(getMapData.get("ICCID")!=null && dataInputIccID !=null ){
                                IccID = getMapData.get("ICCID");
                                Application.showMessage("The IccId in validation process is ::"
                                                                + IccID);
                                
                                System.out.println("IccID in input Sheet"+dataInputIccID);
                                System.out.println("The IccId in validation process is ::"
                                        + IccID);
        
        Application.showMessage("IccID in input Sheet"+dataInputIccID);

                                Boolean p1 = lRp.validationPointIgnoreCase(status, Instatus, input, c);
                                String InService = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
		//Boolean p2 = lRp.validationPoint(service, InService, input, c);
                                P3 = lRp.validationPointIccID(IccID, dataInputIccID, input, c);
                                Application.showMessage("P3 value:::: "+ P3);
                                System.out.println("P3 value:::: "+ P3);
                                }
                                else
                                {
                                                Application.showMessage("IccId is null ::"
                                                                                + IccID);
                                               System.out.println("IccId is null ::"
                                                        + IccID);
                                }              
                                /* TestCaseName TC5_US442585 change Completed */                                
                    }
                public Map<String, String> connectThirtyDayDisconnectDB(Object input,
                                                
                                                ScriptingContext c) throws ClassNotFoundException, IOException,
			NullPointerException, InterruptedException {
                                Application
                                                                .showMessage("------------------------------------------------------------------");
                                Application
                                                                .showMessage("*##Connecting to connectThirtyDayDisconnectDB retrieving data... ");
                                Application
                                                                .showMessage("------------------------------------------------------------------");
                                                  ResultSet rs;
                                String AccountNumber = c.getValue("CcentralNum");
                                String subAccount = c.getValue("ACC_input");
                                Application.showMessage("The C-Central Number is ::" + AccountNumber);
                                Application.showMessage("The Account Number is ::" + subAccount);

                                                  String dStatus = null;
                                                  String dDateBegin = null;
                                String dService = null;
                                String dICCID = null;
                                Map<String, String> returnMap = new HashMap<String, String>();
		Thread.sleep(5000);

                                try {

                                          Statement st = lRp.dBConnect(input, c);

                                                rs = st.executeQuery("select * from thirtydaysdisconnect where accountnumber='"
                                                                                + AccountNumber + "' and subaccount='" + subAccount + "'");
                                            
                                	

                                                while (rs.next()) {

                                                                dStatus = rs.getString("STATUS");
                                                                dDateBegin = rs.getString("DATE_HOLD_BEGAN");
                                                                dService = rs.getString("SERVICE");
                                                                dICCID = rs.getString("ICCID");

                                          }
                                                Application.showMessage("STATUS is ::" + dStatus);
                                                Application.showMessage("dDateBegin is ::" + dDateBegin);
                                                Application.showMessage("dService is ::" + dService);
                                                Application.showMessage("dICCID is ::" + dICCID);
                                                returnMap.put("status", dStatus.trim());
                                                returnMap.put("date", dDateBegin.trim());
                                                returnMap.put("service", dService.trim());
                                                returnMap.put("ICCID", dICCID);

                                          st.close();
                                }

                                catch (SQLException e) {
                                                   System.out.println("Connection Failed! Check output console");
                                                               e.printStackTrace();

                                                }
                                                return returnMap;

                                }

                public void runGlobalProcess(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException {
                                Application
                                                                .showMessage("------------------------------------------------------------------------------------------------------");
                                Application
                                                                .showMessage("*##Connecting to cwglobalprocess and cwmdtypes tables, retrieving process id and deletion of the same ");
                                Application
                                                                .showMessage("------------------------------------------------------------------------------------------------------");
                                  
                                System.out.println
                                ("------------------------------------------------------------------------------------------------------");

                                System.out.println("*##Connecting to cwglobalprocess and cwmdtypes tables, retrieving process id and deletion of the same ");
                                System.out.println("------------------------------------------------------------------------------------------------------");
  
                                ResultSet rs;
                                                  String gProcessid = null;
                                int nRs = 0, nRs1 = 0;
                                try {

                                          Statement st = lRp.dBConnect(input, c);

                                                rs = st.executeQuery("select process_id from cwglobalprocess,cwmdtypes where  typename ='vantage:disconnectAfter30Days' and typeid =process_metadatype");

                                                while (rs.next()) {

                                                                gProcessid = rs.getString(1);
                                          }
                                                Application.showMessage("gProcessid is ::" + gProcessid);
                                                System.out.println("gProcessid is ::" + gProcessid);

                                                nRs = st.executeUpdate("Delete from cwglobalprocess where process_id ='"
                                                                                + gProcessid + "'");
                                                Application
                                                                                .showMessage("No :of records Deleted from cwglobalprocess Table::"
                                                                                		
                                                                                                                + nRs);
                                                System.out.println("No :of records Deleted from cwglobalprocess Table::" + nRs);
                  
                                                if (nRs == 0) {
                                                                Application.showMessage("Zero Number of records Deleted from cwglobalprocess...Error..!!");
                                                                System.out.println("Zero Number of records Deleted from cwglobalprocess...Error..!!");
                                                } else {
                                                                Application.showMessage("Number of records Deleted from cwglobalprocess is ::"+ nRs);     
                                                                System.out.println("Number of records Deleted from cwglobalprocess is ::"+ nRs);                                                                                                                                


                                          }
                                                nRs1 = st
                                                                                .executeUpdate("Delete from cwpactivity where process_id ='"
                                                                                                                + gProcessid + "'");
                                                Application.showMessage("No :of records Deleted from cwpactivity Table::"  + nRs1);             
                                                System.out.println("No :of records Deleted from cwpactivity Table::"  + nRs1);             
                                                if (nRs1 == 0) {
                                                                Application.showMessage("Zero Number of records Deleted from cwpactivity...Error..!!");
                                                                System.out.println("Zero Number of records Deleted from cwpactivity...Error..!!");
                                                  c.report("Zero Number of records Deleted from cwpactivity...Error..!!");
                                                } else {
                                                                Application.showMessage("Number of records Deleted from cwpactivity is ::"
                                                                                                                                + nRs1);
                                                                System.out.println("Number of records Deleted from cwpactivity is ::"
                                                                        + nRs1);

                                          }
                                                Application.showMessage("------------------------------------------------------------------------------------------------------");
                                                System.out.println("------------------------------------------------------------------------------------------------------");


                                          st.close();
                                }

                                catch (SQLException e) {
                                                   System.out.println("Connection Failed! Check output console");
                                                               e.printStackTrace();

                                                }

                                }

                public void getTerminalDetails_Validate(Object input, ScriptingContext c)
                                                throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException {
                                                Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
                                LibraryRtp_UpDown lC = new LibraryRtp_UpDown();
                                LibraryRtp lr=new LibraryRtp();
                                                ResultSet  rs;
                                int callFound = 0, v1 = 0;
                                String xmldata1 = "receive_data";
                                String xmldata2 = "SEND_DATA";
                                String Time= c.get("BaseTime").toString();
                            //    c.setValue("getTerminalDetails","true");

                                Application .showMessage("-------------------------------------------------");
                                Application .showMessage("*****getTerminalDetails_Validate Function******");
                                Application .showMessage("-------------------------------------------------");
                                System.out.println("-------------------------------------------------");
                                System.out.println("*****getTerminalDetails_Validate Function******");
                                System.out.println("-------------------------------------------------");
                           
                              //  lr.findThinktimeOperationRTP(input, c);
                              //  c.setValue("getTerminalDetails","false");
                                HashMap Operation=lr.findingoperations(input, c);
                       	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("GetTerminalDetails"));
                       	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("GetTerminalDetails"));
                       	     
                       	  System.out.println("OPERATIONValidation is "+(String) Operation.get("GetTerminalDetails"));
                    	     
                    	     
                       	  
                                try {
                                            //    Statement st = lC.dBConnect(input, c);
                                            //    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'jaspersrv:TerminalPortType/GetTerminalDetails' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"
                                	
                                                                        //        + c.get("BaseTime").toString()
                                                                       //         + "' order by creation_time desc)where rownum <= 20");
                                	rs=lr.reduceThinkTimeRTP(input, c);
                                                while (rs.next() && rs.getString(1) != c.getValue("BaseMsgid")) {

                                                                String rowmsg = rs.getString(1);
                                                                if (rs.getBlob("receive_data") == null) {

                                                Application.showMessage("Your Recieve XML is NULL \n");
                                                System.out.println("Your Recieve XML is NULL \n");

                                                                                String senddata = lC.extractXml(rs, xmldata2);
                                                                                Application.showMessage("Your Recieve XML is::\n" + senddata);
                                                                                System.out.println("Your Recieve XML is::\n"
                                                                                                                                + senddata);
                                                                } else if (rs.getBlob("SEND_DATA") == null) {
                                                                                Application.showMessage("Your SEND XML is NULL \n");
                                                                                System.out.println("Your SEND XML is NULL \n");
                                                                                String recievedata = lC.extractXml(rs, xmldata1);
                                                                                Application.showMessage("RECIEVE XML is ::\n" + recievedata);
                                                                                System.out.println("RECIEVE XML is ::\n" + recievedata);
                                                                } else {

                                                                                String senddata = lC.extractXml(rs, xmldata2);
                                                                                String recievedata = lC.extractXml(rs, xmldata1);
                                                                                String receiveDataTrim=lC.RemoveNameSpaces(recievedata);
                                                                                Application.showMessage("receiveDataTrim:::"+ receiveDataTrim);
                                                                                System.out.println("receiveDataTrim:::"+ receiveDataTrim);
                                                                                String correlationId = lC
                                                                                                                .nodeFromKey(
                                                                                                                                                recievedata,
                                                                                                                                                "<ns2:correlationId xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">",
                                                                                                                                                "</ns2:correlationId>");
                                                                                Application.showMessage("getTerminalDetails_Validate is ::"
                                                                                                                + correlationId);
                                                                                System.out.println("getTerminalDetails_Validate is ::"
                                                                                        + correlationId);
                                                                                String sICCID = lC.nodeFromKey(recievedata, "<ns2:iccid>",
                                                                                                                "</ns2:iccid>");
                                                                                Application.showMessage("sICCID is :: " + sICCID);
                                                                                System.out.println("sICCID is :: " + sICCID);

                                                                                if (sICCID == null) {
                                                                                            c.report("Send Xml sStatus is NULL");
                                                                                            continue;
                                                                                } else {
                                                                                                String iccId = c.getValue("RTPDataSourceCollections",
                                                                                                                                "RTP_UpDownInputs: iccId");
                                                                                                Application.showMessage("iccId Status from input is::"
                                                                                                                                + iccId);
                                                                                                System.out.println("iccId Status from input is::"
                                                                                                        + iccId);
                                                                                                if (sICCID.equalsIgnoreCase(iccId)) {
                                                                                                                Application.showMessage("*******Validation Point 2 :: WebServicall-sICCID********");
                                                                                                                Application .showMessage("Validation is Successfull with iccId::"+ " " + sICCID);
                                                                                                                System.out.println("*******Validation Point 2 :: WebServicall-sICCID********");
                                                                                                                System.out.println("Validation is Successfull with iccId::"
                                                                                                                                                                                         + " " + sICCID);
                                                                                                                v1 = 1;
                                                                                                                callFound = 1;
                                                                                                } else {
                                                                                                                c.report("iccId at Send Xml not Validated as "
                                                                                                                                                + sICCID);
                                                                                                                v1 = 0;
                                                                            }
                                                                                             }

                                                                                if (callFound == 1) {

                                                                                                String sStatus = lC.nodeFromKey(recievedata,
                                                                                                                                "<ns2:status>", "</ns2:status>");
                                                                                                Application.showMessage("sStatus is :: " + sStatus);
                                                                                                System.out.println("sStatus is :: " + sStatus);

                                                                                                if (sStatus == null) {
                                                                                            c.report("Send Xml sStatus is NULL");
                                                                                            continue;
                                                                                                } else {
                                                                                                                String getTerminalDetailStatus = c.getValue(
                                                                                                                                                "RTPDataSourceCollections",
                                                                                                                                                "RTP_UpDownInputs: getTerminalStatus");
                                                                                                                Application.showMessage("Simulator Status from input is::" + getTerminalDetailStatus);
                                                                                                                System.out.println("Simulator Status from input is::" + getTerminalDetailStatus);
                                                                                                                if (sStatus
                                                                                                                                                .equalsIgnoreCase(getTerminalDetailStatus)) {
                                                                                                                                Application  .showMessage("*******Validation Point 2 :: WebServicall-SimInfoStatus********");
                                                                                                                                System.out.println("*******Validation Point 2 :: WebServicall-SimInfoStatus********");
                                                                                                                                Application                                                                                                                                                                .showMessage("Validation is Successfull with sStatus::"
                                                                                                                                                                                                + " " + sStatus);
                                                                                                                                v1 = 1;
                                                                                                                } else {
                                                                                                                                v1 = 1;
                                                                                                                              //  c.report("sStatus at Send Xml not Validated as "
                                                                                                                                                             //   + sStatus);
                                                                            }
                                                                                             }

                                                                                                String terminalId = lC.nodeFromKey(recievedata,
                                                                                                                                "<ns2:terminalId>", "</ns2:terminalId>");
                                                                                                Application.showMessage("terminalId is::" + " "
                                                                                                                                + terminalId);
                                                                                                System.out.println("terminalId is::" + " "
                                                                                                        + terminalId);

                                                                                                String dateActivated = lC.nodeFromKey(recievedata,
                                                                                                                                "<ns2:dateActivated>", "</ns2:dateActivated>");
                                                                                                Application.showMessage("date Activated is::" + " "
                                                                                                                                + dateActivated);
                                                                                                System.out.println("date Activated is::" + " "
                                                                                                        + dateActivated);

                                                                                                String dateAdded=lC.GetValueByXPath(receiveDataTrim, "/GetTerminalDetailsResponse/terminals/terminal/dateAdded");
                                                                                                /*String dateAdded = lC.GetValueByXPath(sXml, sxpath)(recievedata,
                                                                                                                                "<ns2:dateAdded>", "<ns2:dateAdded>");*/
                                                                                                Application.showMessage("date Added is::" + " "
                                                                                                                                + dateAdded);
                                                                                                System.out.println("date Added is::" + " "
                                                                                                        + dateAdded);
                                                                                                String dateModified=lC.GetValueByXPath(receiveDataTrim, "/GetTerminalDetailsResponse/terminals/terminal/dateModified");
                                                                                                /*String dateModified = lC.nodeFromKey(recievedata,
                                                                                                                                "<ns2:dateModified>", "</ns2:dateModified>");*/
                                                                                                Application.showMessage("date Modified is::" + " "
                                                                                                                                + dateModified);
                                                                                                System.out.println("date Modified is::" + " "
                                                                                                        + dateModified);


                                                                                                String dateShipped=lC.GetValueByXPath(receiveDataTrim, "/GetTerminalDetailsResponse/terminals/terminal/dateShipped");
                                                                                                /*String dateShipped = lC.nodeFromKey(recievedata,
                                                                                                                                "<ns2:dateShipped>", "</ns2:dateShipped>");*/
                                                                                                Application.showMessage("date Shipped is::" + " "
                                                                                                                                + dateShipped);
                                                                                                System.out.println("date Shipped is::" + " "
                                                                                                        + dateShipped);
                                                                                                String termStartDate=lC.GetValueByXPath(receiveDataTrim, "/GetTerminalDetailsResponse/terminals/terminal/rating/termStartDate");
                                                                                                /*String termStartDate = lC.nodeFromKey(recievedata,
                                                                                                                                "<ns2:termStartDate>", "</ns2:termStartDate>");*/
                                                                                                Application.showMessage("term StartDate is::" + " "
                                                                                                                                + termStartDate);
                                                                                                System.out.println("term StartDate is::" + " "
                                                                                                        + termStartDate);
                                                                                                String termEndDate=lC.GetValueByXPath(receiveDataTrim, "/GetTerminalDetailsResponse/terminals/terminal/rating/termEndDate");
                                                                                                /*String termEndDate = lC.nodeFromKey(recievedata,
                                                                                                                                "<ns2:termEndDate>", "</ns2:termEndDate>");*/
                                                                                                Application.showMessage("term EndDate is::" + " "
                                                                                                                                + termEndDate);
                                                                                                System.out.println("term EndDate is::" + " "
                                                                                                        + termEndDate);


                                                                                                String renewalPolicy = lC.nodeFromKey(recievedata,
                                                                                                                                "<ns2:renewalPolicy>", "</ns2:renewalPolicy>");
                                                                                                Application.showMessage("renewal Policy is::" + " "
                                                                                                                                + renewalPolicy);
                                                                                                System.out.println("renewal Policy is::" + " "
                                                                                                        + renewalPolicy);

                                                                                                String simNotes = lC.nodeFromKey(recievedata,
                                                                                                                                "<ns2:simNotes>", "</ns2:simNotes>");
                                                                                                Application.showMessage("sim Notes is::" + " "
                                                                                                                                + simNotes);
                                                                                                System.out.println("sim Notes is::" + " "
                                                                                                        + simNotes);
                                                                                                String messageId = lC.nodeFromKey(senddata,
                                                                                                                                "<jaspersrv:messageId>",
                                                                                                                                "</jaspersrv:messageId>");
                                                                                                Application.showMessage("messageId is::" + " "
                                                                                                                                + messageId);
                                                                                                System.out.println("messageId is::" + " "
                                                                                                        + messageId);

                                                                                                String version = lC.nodeFromKey(senddata,
                                                                                                                                "<jaspersrv:version>", "</jaspersrv:version>");
                                                                                                Application.showMessage("version is::" + " " + version);
                                                                                                System.out.println("version is::" + " " + version);

                                                                                                String licenseKey = lC.nodeFromKey(senddata,
                                                                                                                                "<jaspersrv:licenseKey>",
                                                                                                                                "</jaspersrv:licenseKey>");
                                                                                                Application.showMessage("licenseKey is::" + " "
                                                                                                                                + licenseKey);
                                                                                                System.out.println("licenseKey is::" + " "
                                                                                                        + licenseKey);

                                                            break;
                                                            }
                                             }
                                         }
                                                if (v1 * callFound == 1) {
                                                                Application .showMessage("WebService Call :: getTerminalDetails_Validate is Success[All validation points are Success]");
                                                                System.out.println("WebService Call :: getTerminalDetails_Validate is Success[All validation points are Success]");
                                                } else {
                                                 c.put("result", "false");
                                                 c.report("WebService Call :: getTerminalDetails_Validate is Failed with Validation Errors");
                                         }
                                      //   st.close();
                                } catch (SQLException e) {
                                                    System.out.println("Connection Failed! Check output console");
                                                                e.printStackTrace();
                                                                return;
                                                }
                                }

                public void Jasper_Validate(Object input, ScriptingContext c)
                                                throws InterruptedException, IOException, ClassNotFoundException {

                                // Think time in JVM [waits for 10 secs here]
                                LibraryRtp_UpDown lC = new LibraryRtp_UpDown();
                                LibraryRtp lr=new  LibraryRtp();
                                ResultSet rs;
                                String xmldata1 = "receive_data";
                                String xmldata2 = "SEND_DATA";
                                int callFound = 0;
                                String Time= c.get("BaseTime").toString();
                            //    c.setValue("Jasper","true");
                                Application .showMessage("----------------------------------------------------------");
                                                               
                                Application.showMessage("*****jasper Function******");
                                Application.showMessage("----------------------------------------------------------");
                                                                
                            //    lr.findThinktimeOperationRTP(input, c);
                            //    c.setValue("Jasper","false"); 
                                HashMap Operation=lr.findingoperations(input, c);
                          	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("EditTerminal"));
                          	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("EditTerminal"));
                          	     
                
                                try {
                                           //     Statement st = lC.dBConnect(input, c);
                                           //     rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'jaspersrv:TerminalPortType/EditTerminal' order by creation_time desc)where rownum <= 20");
                                	rs=lr.reduceThinkTimeRTP(input, c);

                                                while (rs.next()) {

                                                                String rowmsg = rs.getString(1);
                                                                if (rs.getBlob("receive_data") == null) {

                                                                                System.out.printf("Your Recieve XML is NULL \n");
                                                                                Application.showMessage("Your Recieve XML is NULL \n");
                                                                                String senddata = lC.extractXml(rs, xmldata2);

                                                                                Application.showMessage("SEND XML is  \n" + senddata);
                                                                } else if (rs.getBlob("SEND_DATA") == null) {
                                                                                System.out.printf("Your SEND XML is NULL \n");
                                                                                String recievedata = lC.extractXml(rs, xmldata1);
                                                                                System.out.printf("RECIEVE XML is \n", recievedata);
                                                                                Application.showMessage("Your Recieve XML is NULL \n");
                                                                                Application.showMessage("Recieve XML is  \n" + recievedata);

                                                                } else {

                                                                                String recievedata = lC.extractXml(rs, xmldata1);
                                                                                String senddata = lC.extractXml(rs, xmldata2);
                                                                                System.out.printf("SEND XML is %s \n", senddata);

                                                                                String ICCID = lC.nodeFromKey(senddata,
                                                                                                                "<jaspersrv:iccid>",
                                                                                                                "</jaspersrv:iccid><jaspersrv:targetValue>");

                                                                                if (ICCID == null) {
                                                                                                System.out.printf("ICCID value from JasperCall is ::NULL \n");
                                                                                                continue;
                                                                                } else {

                                                                                                Application.showMessage("ICCID value from JasperCall is::"  + ICCID);
                                                                                                System.out.printf("ICCID value from JasperCall is::"  + ICCID);

                                                                                                if (ICCID.contains("8901650")) {
                                                                                                                Application.showMessage("ICCID msgid= " + rs.getString(1));
                                                                                                                Application.showMessage("Recieve XML is  \n"  + recievedata);
                                                                                                                Application.showMessage("SEND XML is  \n"  + senddata);
                                                                                                                Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                                                                Application .showMessage("Validation is Successfull with Input ICCID"
                                                                                                                                                                                + ICCID);
                                                                                                                
                                                                                                               System.out.println("ICCID msgid= " + rs.getString(1));
                                                                                                               System.out.println("Recieve XML is  \n"  + recievedata);
                                                                                                               System.out.println("SEND XML is  \n"  + senddata);
                                                                                                               System.out.println("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                                                                                               System.out.println("Validation is Successfull with Input ICCID"
                                                                                                                                                                                + ICCID);
                                                                                                             
                                                                                                                callFound = 1;
                                                                                                } else {
                                                                                                                continue;
                                                                                                }

                                                                                                if (callFound == 1) {
                                                                                                                String Jasp_version = lC
                                                                                                                                                .nodeFromKey(senddata,
                                                                                                                                                                                "<jaspersrv:version>",
                                                                                                                                                                                "</jaspersrv:version><jaspersrv:licenseKey>");

                                                                                                                Application.showMessage("SEND Xml Jasp_version is ::"+ Jasp_version);
                                                                                                                System.out.println("SEND Xml Jasp_version is ::"+ Jasp_version);

                                                                                                                String Jas_licenseKey = lC.nodeFromKey(senddata,
                                                                                                                                                "<jaspersrv:licenseKey>",
                                                                                                                                                "</jaspersrv:licenseKey><jaspersrv:iccid>");
                                                                                                                Application .showMessage("SEND Xml Jas_licenseKey is ::" + Jas_licenseKey);
                                                                                                                System.out.println("SEND Xml Jas_licenseKey is ::" + Jas_licenseKey);
                                                                                                                      
                                                                                                                                                                                                                                

                                                                                                                String Jas_targetValue = lC.nodeFromKey(senddata,
                                                                                                                                                "<jaspersrv:targetValue>",
                                                                                                                                                "</jaspersrv:targetValue>");

                                                                                                                Application .showMessage("SEND Xml Jas_targetValue is ::" + Jas_targetValue);
                                                                                                                System.out.println("SEND Xml Jas_targetValue is ::" + Jas_targetValue);
                                                                                                                                             
                                                                                                                                                                               

                                                                                                                String Jas_changeType = lC
                                                                                                                                                .nodeFromKey(senddata,
                                                                                                                                                                                "<jaspersrv:changeType>",
                                                                                                                                                                                "</jaspersrv:changeType></jaspersrv:EditTerminalRequest>");
                                                                                                                Application.showMessage("SEND Xml Jas_changeType is ::"   + Jas_changeType);
                                                                                                                System.out.println("SEND Xml Jas_changeType is ::"   + Jas_changeType);
                                                                                                                                               
                                                                                                                                                                             

                                                                                                                String Jas_Version = lC
                                                                                                                                                .nodeFromKey(
                                                                                                                                                                                recievedata,
                                                                                                                                                                                "<ns2:version xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">",
                                                                                                                                                                                "</ns2:version><ns2:build xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
                                                                                                                Application.showMessage("SEND Xml Jas_Version is ::" + Jas_Version);
                                                                                                                System.out.println("SEND Xml Jas_Version is ::" + Jas_Version);


                                                                                                                                                
                                                                                                                                                                               
                                                                                                                String Jas_build = lC
                                                                                                                                                .nodeFromKey(
                                                                                                                                                                                recievedata,
                                                                                                                                                                                "<ns2:build xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">",
                                                                                                                                                                                "</ns2:build><ns2:timestamp xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
                                                                                                                Application.showMessage("SEND Xml Jas_build is ::"
                                                                                                                                                + Jas_build);
                                                                                                                System.out.println("SEND Xml Jas_build is ::"
                                                                                                                        + Jas_build);

                                                                                                                String Jas_iccid = lC
                                                                                                                                                .nodeFromKey(
                                                                                                                                                                                recievedata,
                                                                                                                                                                                "<ns2:iccid xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">",
                                                                                                                                                                                "</ns2:iccid><ns2:effectiveDate xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
                                                                                                                Application.showMessage("SEND Xml Jas_iccid is ::"
                                                                                                                                                + Jas_iccid);
                                                                                                                System.out.println("SEND Xml Jas_iccid is ::"
                                                                                                                        + Jas_iccid);

                                                                                                }

                                                                                }

                                                                }

                                                                Application.showMessage("********WebService Call::Jasper Valiadted ********");
                                                                System.out.println("********WebService Call::Jasper Valiadted ********");
                                                             //   st.close();

                                                }
                                } catch (SQLException e) {
                                                Application.showMessage("Connection Failed! Check output console");
                                                System.out.println("Connection Failed! Check output console");
                                                e.printStackTrace();
                                                return;
                                }
                }

                // --------------------------------------------------- 30 day
                // Downgrade---------------------------

                public void thirtyDay_Downgrade(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException, XPathExpressionException, NullPointerException,
                                                SAXException {

                                RTP_ValidationsClass_UpDown RV = new RTP_ValidationsClass_UpDown();
                                simulatorInsightcalls(input, c);
                                /*String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");*/
                                String ComStatus = "Completed";
                                thirtyDisconnectTableValidation(ComStatus, input, c);// status
                                                                                                                                                                                                                                                                // completed.
                                /*lRp.simulatorChange30DayDisconnect(status, input, c);*/
                                /* TestCaseName TC5_US442585 change start */
                                if(P3!=null)
                                {
                                if (P3.startsWith("8901650")) {
                                                Jasper_Validate(input, c);
                                } else if (P3.startsWith("8901640")) {
                                                tD.NumerixCos_Validate(input, c);
                                }
                                }
                                else {
                                                Application.showMessage("ICCID is Null");
                                }
                                /* TestCaseName TC5_US442585 change end */

                                
                }

                public void simulatorInsightcalls(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                XPathExpressionException, NullPointerException, SAXException {

                                RTP_SimulatorClass_UpDown sim = new RTP_SimulatorClass_UpDown();
                                Thread.sleep(4000);
                                vC.rtpTestInterface_Validate(input, c);
                                vC.queryLocation_Validate(input, c);
                               
                                sim.processHomeSecurityInfo_Validate(input, c);
                               // sim.deactivateAccount_Validate(input, c);
                                sim.deactivateAccount_Validate_CLS(input, c);
                                sim.simGetAccountValidate_CLS(input, c);
                               
                                vC.processHomeSecurityInfo_Validate(input, c);
		//vC.createAccount_Validate(input, c);
                                // sim.createAccount_ValidateInsight(input, c);
		cD.orderUpdate_COD(input, c);

                }

                public void ExecutionOrderPartTwo_Downgrade(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException {

                                Thread.sleep(10000);
                                vC.queryLocation_Validate(input, c);
                                // vC.queryLocation_Validate(input, c);

                                String statusCheck = "Open";
                                thirtyDisconnectTableValidation(statusCheck, input, c);

                }

                // ----------------------------------- 30 day cos and
                // restore--------------------------------

                public void ExecutionOrderPartTwo_CosSuSpendFlag(Object input,
                                                ScriptingContext c) throws ClassNotFoundException, IOException,
			InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException, ParserConfigurationException
			{
		
                                XHS_resumeFlows_PendingChange RF = new XHS_resumeFlows_PendingChange();
                                String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");
                                RTP_ValidationsClass_UpDown RV = new RTP_ValidationsClass_UpDown();
                                RF.CosAndRestoreValidations(input, c);

                                String ComStatus = "Cancelled";
                                lRp.simulatorChange30DayDisconnect(status, input, c);
                                thirtyDisconnectTableValidation(ComStatus, input, c);// status
                                                                                                                                                                                                                                                                // completed.

                }

                // -----------------------------transfer
                // flag------------------------------------------------------

                public void ExecutionOrderPartTwo_TransferFlag(Object input,
                                                ScriptingContext c) throws ClassNotFoundException, IOException,
                                                InterruptedException, SQLException, XPathExpressionException, NullPointerException, SAXException {

                                /*
                                * Thread.sleep(20000); rV.queryLocation_Validate(input, c);
                                * rS.simGetAccountValidate(input, c);
                                * rU.deactivateAccount_Validate(input, c);
                                * rU.processHomeSecurityInfo_Validate(input, c);
                                * cc.DisconnectAccount_CANCEL_Validate(input, c);
                                * rVU.orderUpdateDis_Validate(input, c);
                                */
                                RTP_ValidationsClass rV = new RTP_ValidationsClass();
                                RTP_ValidationsClass_UpDown rU = new RTP_ValidationsClass_UpDown();
                                RTP_SimulatorClass_UpDown rS = new RTP_SimulatorClass_UpDown();
                                FunctionLibrary_A fA = new FunctionLibrary_A();
                                CancelClass cc = new CancelClass();
                               // Thread.sleep(2000);

                               // rV.queryLocation_Validate(input, c);
                                rS.simGetAccountValidate_CLS(input, c);
                                String changeValue = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: TransferFlag");
                                Application.showMessage("Transfer flag Value is::"
                                                                + c.getValue("RTPDataSourceCollections",
                                                                                                "RTP_UpDownInputs: TransferFlag"));
                                if (changeValue.equalsIgnoreCase("true"))

                                {
                                                // disconnect calls.
                                                rS.deactivateAccount_Validate_CLS(input, c);
                                                // rU.processHomeSecurityInfo_Validate(input, c);
                                                cc.DisconnectAccount_CANCEL_Validate(input, c);
                                                fA.orderUpdateDis_Validate(input, c);
                                }

                                else {
                                	rV.queryLocation_Validate(input, c);
                                	rV.getCustomerPermitRequirements_Validate(input, c);
                                	Thread.sleep(5000);
                                                String Instatus = "Open";
                                                thirtyDisconnectTableValidation(Instatus, input, c);
                                                Thread.sleep(3000);

                                }

                                /*String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");
                                lRp.simulatorChange30DayDisconnect(status, input, c);*/

                }

                public void ExecutionOrderPartTwo_ACTIVE(Object input, ScriptingContext c)
                                                throws ClassNotFoundException, IOException, InterruptedException,
                                                SQLException, XPathExpressionException, NullPointerException, SAXException {
                                vC.queryLocation_Validate(input, c);
                                // vC.queryLocation_Validate(input, c);

                                String statusCheck = "Open";
                                thirtyDisconnectTableValidation(statusCheck, input, c);

                                runGlobalProcess(input, c);
                                Thread.sleep(2000);

                                rSval.simGetAccountValidate(input, c);
                                vC.queryLocation_Validate(input, c);

                                String status = c.getValue("RTPDataSourceCollections",
                                                                "RTP_UpDownInputs: status");
                                String Instatus = "Open";
                                thirtyDisconnectTableValidation(Instatus, input, c);
                                if(P3!=null)
                                {
                                if (P3.startsWith("8901650")) {
                                                Jasper_Validate(input, c);
                                } else if (P3.startsWith("8901640")) {
                                                tD.NumerixCos_Validate(input, c);
                                }} 
                                else {
                                                Application.showMessage("ICCID is Null");
                                }

                                

                                Thread.sleep(3000);
                                // may have to run simulator settings according to scenarios.

                                Thread.sleep(10000);
                                // disconnect demicalls.
                                String ComStatus = "Completed";
                                rSval.deactivateAccount_Validate(input, c);
                                rSval.processHomeSecurityInfo_Validate(input, c);
                                sim.DisconnectAccount_CANCEL_Validate(input, c);
                                rVal.orderUpdateDis_Validate(input, c);
                                // DisconnectAccount
                                thirtyDisconnectTableValidation(ComStatus, input, c);// status
                                                                                                                                                                                                                                                                // completed.
                                lRp.simulatorChange30DayDisconnect(status, input, c);
                                // status completed.

                }
                public void thirtyDisconnectTableValidationFORScheduleTable(String Instatus,Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
                                              {
                                Thread.sleep(9000);
                                Map<String,String> getMapData=new HashMap <String,String>();
                                    getMapData=connectThirtyDayDisconnectDB(input, c);
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
                
                  public void LocationFail(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, SQLException, InterruptedException
                   {

                                      RTP_ValidationsClass rV = new RTP_ValidationsClass(); 
                                      LibraryRtp LR=new LibraryRtp();
                                     // rV.rtpTestInterface_Validate(input, c);
                          orderUpdate_Validate_LocationError(input, c);
                          LR.CommonDBValidate(input, c);

                   }
                  
                  public void orderUpdate_Validate_LocationError(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
                                {
                                                Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
                                     LibraryRtp  lC= new LibraryRtp ();
                                                ResultSet  rs;
                                                int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0;
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
                                                           if(accountNumber_ou==null)
                                                            {
                                                                  Application.showMessage("Send Xml Account Number is NULL");
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


                                                                           String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
                                                                                       Application.showMessage("ordSource is ::"+ordSource); 
                                                                                        if(ordSource.equals("OP"))
                                                                                       {
                                                                                               Application.showMessage("Valid Order Source");
                                                                                       }
                                                                                       
                                                                                        else
                                                                                       {
                                                                                               c.report("Order source is Invalid");
                                                                                       }
                                                                                       
                                                                                        
                                                                                       String ordStatus= lC.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"customerType\">");
                                                                                       Application.showMessage("ordStatus is ::"+ordStatus); 
                                                                                        if(ordStatus.equals("ERR"))
                                                                                       {
                                                                                               Application.showMessage("Valid Order Status");
                                                                                       }
                                                                                       
                                                                                        else
                                                                                       {
                                                                                               c.report("Order Status is Invalid");
                                                                                       }
                                                                                       
                                                                                        String ordType= lC.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"active\">");
                                                                                       Application.showMessage("ordType is ::"+ordType); 
                                                                                        

                                                                           String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
                                                                           Application.showMessage("inputChannel is ::"+inputChannel);
                                                                                                           
                                                                            
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
                                                                           
                                                                           //_______________________________________________________________________//
                                                                          //
                                                                          //                                                                 WorkList Validations
                                                                          //
                                                                          //_______________________________________________________________________//
                                                                          
                                                                           
                                                                           
                                                                           //---------------------------Error code validation----------------------------------------//
                                                                          
                                                                         
                                                                           
                                                                           
                                                                           
                                                                            String errorCode= lC.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"errorText\">");
                                                                           Application.showMessage("errorCode is ::"+errorCode); 
                                                                            
                                                                            //For common DB validation
                                                                              c.put("ErrorCodeOu", errorCode);
                                                                          
                                                                         // Error text from excel
                                                                             // c.put("ErrorCode_Excel",c.getValue("RTPNormalScenariosdS","ErrorCode"));
                                                                           
                                                                                           if(errorCode==null)
                                                                                            {
                                                                                                            c.report("Send Xml Error Code is NULL");
                                                                                                            continue;
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                
                                                                                                 if(errorCode.equals("XH-ELOC-001"))
                                                                                                             {
                                                                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-ErrorCode********");
                                                                                                                 Application.showMessage("Validation is Successfull with Error Code::"+" "+errorCode);
                                                                                                                 v5=1;
                                                                                                             }
                                                                                                             else
                                                                                                             {
                                                                                                                 c.report("Error Code at Send Xml not Validated as "+errorCode);
                                                                                                             }
                                                                                            }
                                                                                          
                                                                                           
                                                                                           
                                                                                           
                                                                                           //-------------------------Error text validation-----------------------------------------//
                                                                                          
                                                                                           
                                                                                          String errorText= lC.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorSource\">");
                                                                                           Application.showMessage("Error Text is ::"+errorText); 
                                                                                           //For Common DB validation
                                                                                           c.put("ErrorTextOu", errorText);
                                                                                           
                                                                                            
                                                                                            //read from Excel ----
                                                                                          // c.put("ErrorText_Excel",c.getValue("RTPNormalScenariosdS","ErrorText"));
                                                                                                          if(errorText==null)
                                                                                                            {
                                                                                                                            c.report("Send Xml Error Code is NULL");
                                                                                                                            continue;
                                                                                                            }
                                                                                                            else
                                                                                                            {
                                                                                                                
                                                                                                                 if(errorText.equals("Missing service address"))
                                                                                                                             {
                                                                                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-Error Text********");
                                                                                                                                 Application.showMessage("Validation is Successfull with Error Text::"+" "+errorText);
                                                                                                                                 v6=1;
                                                                                                                             }
                                                                                                                             else
                                                                                                                             {
                                                                                                                                 c.report("Error Text at Send Xml not Validated as "+errorText);
                                                                                                                             }
                                                                                                            }
                                                                                                          
                                                                                                           
                                                                                                           
                                                                                                        //-------------------------Error Source  validation-----------------------------------------//
                                                                                                          
                                                                                                           
                                                                                                          String errorSource= lC.nodeFromKey(senddata,"<value name=\"errorSource\">","</value><value name=\"details\">");
                                                                                                           Application.showMessage("Error Source is ::"+errorSource); 
                                                                                                            
                                                                                                         //For Common DB validation
                                                                                                           c.put("ErrorSourceOu", errorSource);
                                                                                                                                                                                           
                                                                                                            
                                                                                                            
                                                                                                            //read from Excel ----
                                                                                                          // c.put("ErrorSource_Excel",c.getValue("RTPNormalScenariosdS","ErrorSource"));
                                                                                                           
                                                                                                                           if(errorSource==null)
                                                                                                                            {
                                                                                                                                            c.report("Send Xml Error Source is NULL");
                                                                                                                                            continue;
                                                                                                                            }
                                                                                                                            else
                                                                                                                            {
                                                                                                                                
                                                                                                                                if(errorSource.equals("BHS.Fallout"))
                                                                                                                                             {
                                                                                                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-Error Source********");
                                                                                                                                                 Application.showMessage("Validation is Successfull with Error Source::"+" "+errorSource);
                                                                                                                                                 v7=1;
                                                                                                                                             }
                                                                                                                                             else
                                                                                                                                             {
                                                                                                                                                 c.report("Error Source at Send Xml not Validated as "+errorSource);
                                                                                                                                             }
                                                                                                                            }
                                                                                                                          
                                                                                                                           
                                                                                                                           
                                                                                                                          String details= lC.nodeFromKey(senddata,"<value name=\"details\">","</value><value name=\"orderType\">");
                                                                                                                         Application.showMessage("Detail  is ::"+details);
                                                                                                                          
                                                                                                                           
                                                                                                                           
                                                                            
                                                                                                           
                                                            break;
                                                            }
                                            }
                                               
                                         }
                                         if(callFound ==1)
                                         {
                                                Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
                                         //   c.setValue("IsDemi", "false");
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
                
}