import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.HashMap;

import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;

public class SuspendAccForDisconnect {

	XHS_resumeFlows_PendingChange rP;
	LibraryRTP_New lR2 = new LibraryRTP_New();
	RTP_ValidationsClass_UpDown vC = new RTP_ValidationsClass_UpDown();
	RTP_SimulatorClass_UpDown rU = new RTP_SimulatorClass_UpDown();
	CancelClass cc = new CancelClass();
	CancelOnDisconnect cD = new CancelOnDisconnect();

	public void initialization(Object input, ScriptingContext c)
			throws IOException, ClassNotFoundException, InterruptedException {

		lR2.getBaseTime(input, c);
		String t1 = LibraryRTP_New.getTimeInstance(input, c);
		c.put("T1", t1);
		Application.showMessage(c.get("T1").toString());
		Thread.sleep(1000);
		vC.valuesFromAggregrate(input, c);
		lR2.baseMsgid_retrieval(input, c);
		lR2.IterationLogic30DayDisconnect(input, c);
		/*
		 * c.put("getStatus", c.getValue("RTPDataSourceCollections",
		 * "dB_Simulator: status"));
		 * Application.showMessage("Status is ::"+c.get
		 * ("getStatus").toString());
		 */

	}

	public void InstallValidations_Demi(Object input, ScriptingContext c)
			throws IOException, ClassNotFoundException, InterruptedException,
			SQLException, NullPointerException,
			SAXException, InstantiationException, IllegalAccessException, XPathException {

		vC.demicalls(input, c);

		String Ccnum = c.getValue("CcentralNum");

		Application.showMessage("CcentralNumber:::" + Ccnum);

		String Accountnumber = c.getValue("ACC_input");
		Application.showMessage("Accountnumber:::" + Accountnumber);
		lR2.mysqldbConnect(Ccnum, Accountnumber);
		Thread.sleep(3000);

	}

	public void InstallValidations_Insight(Object input, ScriptingContext c)
			throws IOException, ClassNotFoundException, InterruptedException,
			SQLException, XPathExpressionException, NullPointerException,
			SAXException, InstantiationException, IllegalAccessException {
		vC.Insightcalls(input, c);
		String Ccnum = c.getValue("CcentralNum");

		Application.showMessage("CcentralNumber:::" + Ccnum);

		String Accountnumber = c.getValue("ACC_input");
		Application.showMessage("Accountnumber:::" + Accountnumber);
		lR2.mysqldbConnect(Ccnum, Accountnumber);
		/*String status = c.getValue("RTPDataSourceCollections",
				"dB_Simulator: status");

		lR2.simulatorChange30DayDisconnect(status, input, c);*/
		Thread.sleep(3000);
	}

	
	
	
	
	
	
	
	//---------------------------- Cancel on Disconnect-------------------------------------
	
	public void CancelOnDiscPart1(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, InterruptedException,
			SQLException, ParseException, NullPointerException, SAXException, InstantiationException, IllegalAccessException, XPathException {
		
	String XML_ScenarioName = c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName");
	if(XML_ScenarioName.equalsIgnoreCase("DEMI"))
	{
		InstallValidations_Demi(input, c);
			
	}
	else
	{
		InstallValidations_Insight(input, c);
	}
		
		
		
	}
	
	public void CancelOnDiscPart2(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, InterruptedException,
			SQLException, ParseException, XPathExpressionException,
			NullPointerException, SAXException {
		
	String XML_ScenarioName = c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_ScenarioName");
	if(XML_ScenarioName.equalsIgnoreCase("DEMI"))
	{
		ExecutionOrder_Demi(input, c);
			
	}
	else
	{
		ExecutionOrder_Insight(input, c);
	}
		
		
		
	}
	
	public void ExecutionOrder_Demi(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, InterruptedException,
			SQLException, ParseException, XPathExpressionException,
			NullPointerException, SAXException {

		Thread.sleep(5000);

		//if (c.get("getStatus").toString().equalsIgnoreCase("ACTIVE"))
			/*
			 * if("ACTIVE".equalsIgnoreCase("ACTIVE")) {
			 */

			cD.simGetAccountValidate(input, c);
			if (c.get("getStatus").toString().equalsIgnoreCase("ACTIVE"))
			{
		SuspendAccount_Validate(input, c);
		rU.deactivateAccount_Validate(input, c);
		rU.processHomeSecurityInfo_Validate(input, c);
		//cc.DisconnectAccount_CANCEL_Validate(input, c);
		cD.orderUpdate_COD(input, c);
			}
		// String status=
		// c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		// lR2.simulatorChange30DayDisconnect(status, input, c);

		
		 else { cD.simGetAccountValidate(input, c);
		 cc.deleteUnactivatedAccount_Validate(input, c);
		 cc.processHomeSecurityInfo_Validate(input, c);
		 cc.DisconnectAccount_CANCEL_Validate(input, c);
		 cc.orderUpdate_Validate(input, c); 
		 //String status=c.getValue("RTPDataSourceCollections","dB_Simulator: status");
		 //lR2.simulatorChange30DayDisconnect(status, input, c);
		  
		  }
		 
	}

	public void ExecutionOrder_Insight(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, InterruptedException,
			SQLException, ParseException, XPathExpressionException,
			NullPointerException, SAXException {

		Thread.sleep(5000);
		cD.simGetAccountValidate(input, c);
		if (c.get("getStatus").toString().equalsIgnoreCase("ACTIVE")) {

			/*String status = c.getValue("RTPDataSourceCollections",
					"dB_Simulator: status");
			lR2.simulatorChange30DayDisconnect(status, input, c);*/
			
			SuspendAccount_Validate(input, c);
			rU.deactivateAccount_Validate(input, c);
			rU.processHomeSecurityInfo_Validate(input, c);
			cD.orderUpdate_COD(input, c);

		}

		else {
			/*String status = c.getValue("RTPDataSourceCollections",
					"dB_Simulator: status");
			lR2.simulatorChange30DayDisconnect(status, input, c);*/
			//cD.simGetAccountValidate(input, c);
			cc.deleteUnactivatedAccount_Validate(input, c);
			cc.processHomeSecurityInfo_Validate(input, c);
			cD.orderUpdate_COD(input, c);

		}

	}
	
	
	public void ExecutionOrder_Insight100TDPEV(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException,
			SQLException, ParseException, XPathExpressionException,
			NullPointerException, SAXException 
			
			{
		
		    Thread.sleep(5000);
			
			String val="true";
            int noofCallsValidated=3;
            int i;
            OUTER1:
            for( i=0;i<=noofCallsValidated;i++)
            {
         	   Application.showMessage("The val of I is"+i);
         	   switch(i)
         	   {
         	   case 0:
         		  val= rU.simGetAccountValidate100TDPEV(input, c);           		  
         		   break;
         	   case 1:
         		   val=rU.deactivateAccount_Validate(input, c);
         		   break;
         	   case 2:
         		   val= cc.processHomeSecurityInfo_Validate(input, c);
         		   break;
         	   case 3:
         		   val= cD.orderUpdate_COD(input, c);
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

	

	public void SuspendAccount_Validate(Object input, ScriptingContext c)
			throws IOException, ClassNotFoundException, InterruptedException,
			XPathExpressionException, NullPointerException, SAXException {

		Thread.sleep(10000); // Think time in JVM [waits for 10 secs here]
		LibraryRtp lC = new LibraryRtp();
		ResultSet rs, rs1;
		int callFound = 0, v1 = 0, v2 = 0;
		String xmldata1 = "receive_data";
		String xmldata2 = "SEND_DATA";
		String Time= c.get("BaseTime").toString();
	     c.setValue("suspendAccount","true");
		Application
				.showMessage("-------------------------------------------------");
		Application.showMessage("*****SuspendAccount_Validate Function******");
		Application
				.showMessage("-------------------------------------------------");
		/*lC.findThinktimeOperationRTP(input, c);
	    c.setValue("suspendAccount","false");*/
		HashMap Operation=lC.findingoperations(input, c);
		c.setValue("OPERATIONVALIDATION",(String) Operation.get("CLS_suspendAccount"));
		Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CLS_suspendAccount"));
		try {
			//Statement st = lC.dBConnect(input, c);
			//rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/suspendAccount' order by creation_time desc)where rownum <= 20");
			rs=lC.reduceThinkTimeRTP(input, c);
			while (rs.next()) {

				String rowmsg = rs.getString(1);
				// String SUSBaseMsgID=rs.getString(to_char(CREATION_TIME,
				// 'yyyy-mm-dd hh24:mi:ss'));
				// c.put("SUSBaseMsgID", )
				if (rs.getBlob("receive_data") == null) {

					Application.showMessage("Your Recieve XML is NULL \n");

					String senddata = lC.extractXml(rs, xmldata2);
					Application.showMessage("Your Recieve XML is::\n"
							+ senddata);
				} else if (rs.getBlob("SEND_DATA") == null) {
					Application.showMessage("Your SEND XML is NULL \n");
					String recievedata = lC.extractXml(rs, xmldata1);
					Application
							.showMessage("RECIEVE XML is ::\n" + recievedata);
				} else {

					//Sruthi
					String senddatacls = lC.extractXml(rs,xmldata2);                                        
					String recievedatacls = lC.extractXml(rs,xmldata1);      
					String senddata=lC.clsRemoveAsciiCharacter(senddatacls);  
					String recievedata=lC.clsRemoveAsciiCharacter(recievedatacls);
					//Sruthi
					String receiveDataTrim = lC.RemoveNameSpaces(recievedata);
					Application.showMessage("ReceiveData Trim :::"
							+ receiveDataTrim);

					String id = lC.nodeFromKey(senddata, "<id>", "</id>");
					Application.showMessage("id is ::" + id);

					if (id.equals(c.getValue("ACC_input"))) {
						Application.showMessage("Recieve XML is::  \n"
								+ recievedata);
						Application.showMessage("SEND XML is :: \n" + senddata);
						// System.out.printf("SEND XML is %s \n",senddata);
						Application
								.showMessage("*******Validation Point 1 :: WebServicall-Suspend Call********");
						Application
								.showMessage("Validation is Successfull with Input Account Number"
										+ id);
						callFound = 1;
					} else {
						continue;
					}

					if (callFound == 1) {
						//Thread.sleep(2000);
						Statement st1 = lC.dBConnect(input, c);
						Application.showMessage("before Connnecting to DB");
						Application.showMessage("RowMsg ID:::" + rowmsg);
						/*rs1 = st1
								.executeQuery("select to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss')AS btime from cwmessagelog where msgid='"
										+ rowmsg + "'");
						while (rs1.next()) {

							String rowmsg1 = rs1.getString(1);
							Application.showMessage(" New Base Time:::"
									+ rowmsg1);
							c.put("BaseTime", rowmsg1);

						}

						rs1.close();
						st1.close();*/
						String errorCode = lC.nodeFromKey(receiveDataTrim,
								"<errorCode>", "</errorCode>");
						// String errorCode=
						// lC.GetValueByXPath(receiveDataTrim,"/errorCode");
						Application.showMessage("errorCode is ::" + errorCode);

						if (errorCode == null) {
							c.report("Send Xml errorCode is NULL");
							continue;
						} else {
							Application
									.showMessage("Account Number from Send Xml  from AddGroup is ::"
											+ " " + errorCode);
							if (errorCode.equals("UCE-15113")) {
								Application
										.showMessage("*******Validation Point 2 :: WebServicall-errorCode********");
								Application
										.showMessage("Validation is Successfull with errorCode::"
												+ " " + errorCode);
								v1 = 1;
							} else if (errorCode.equals("UCE-15101")) {
								Application
										.showMessage("ErrorCode not validated:");
							} else {
								c.report("errorCode at Send Xml not Validated as "
										+ errorCode);
							}
						}
						String errorMessage = lC.nodeFromKey(receiveDataTrim,
								"<errorMessage>", "</errorMessage>");
						// String errorMessage=
						// lC.GetValueByXPath(receiveDataTrim,"/errorMessage");
						Application.showMessage("errorMessage is::"
								+ errorMessage);

						break;
					}

				}
			}
			if (v1 * callFound == 1) {
				Application
						.showMessage("WebService Call :: Suspend is Success[All validation points are Success]");
			} else {
				c.report("WebService Call :: Suspend is Failed with Validation Errors");
			}
			//st.close();
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}

}
