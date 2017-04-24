import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.lang.reflect.InvocationHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.bson.BasicBSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.jpos.iso.ISODate;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;
import com.parasoft.api.*;
import com.mongodb.QueryBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;


public class XHOM_BasicMethods
{
	//private static final ScriptingContext ScriptingContext = null;
	
	LibraryRtp_UpDown lRp = new LibraryRtp_UpDown();
	 XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
	 public static String rowmsg= null;
	 LibraryRtp sL =new LibraryRtp();
	
	
	 XHOM_HomeStream_Validations  HS =new XHOM_HomeStream_Validations();
	 XHOM_DemiInstall_Validations DIV = new XHOM_DemiInstall_Validations();
	 XHOM_DemiDisconnect_Validations DIS=new XHOM_DemiDisconnect_Validations();
	
	 //
	
	
	

 public void XHOM_thirtyDisconnectTableValidation(String Instatus, Object input,
             ScriptingContext c) throws ClassNotFoundException, IOException,
 InterruptedException, Exception, SQLException 
 {
		Thread.sleep(3000);
		String IccID = "";
		Map<String, String> getMapData = new HashMap<String, String>();
		getMapData = XHOM_connectThirtyDayDisconnectDB(input, c);
		String status = getMapData.get("status");
		Application.showMessage("The status in validation process is ::"
				+ status);
		System.out.println("The status in validation process is ::" + status);
		String date = getMapData.get("date");
		Application.showMessage("The date in validation process is ::" + date);
		System.out.println("The date in validation process is ::" + date);

		String service = getMapData.get("service");
		Application.showMessage("The service in validation process is ::"
				+ service);
		System.out.println("The service in validation process is ::" + service);
		String dataInputIccID = c.getValue("RTPDataSourceCollections",
				"RTP_UpDownInputs: iccId");
		/* TestCaseName TC5_US442585 change started */
		if (getMapData.get("ICCID") != null && dataInputIccID != null) {
			IccID = getMapData.get("ICCID");
			Application.showMessage("The IccId in validation process is ::"
					+ IccID);

			System.out.println("IccID in input Sheet" + dataInputIccID);
			System.out.println("The IccId in validation process is ::" + IccID);

			Application.showMessage("IccID in input Sheet" + dataInputIccID);

			Boolean p1 = lRp.validationPointIgnoreCase(status, Instatus, input,
					c);
			if(p1.TRUE)
			  {
				 c.put("ThirtyTableValidationIssues", "no") ;
			  }
			  else
			  {
				  c.put("ThirtyTableValidationIssues", "Validation is NOT Successfull with Input Data..!!! ::"+ status + "and" + Instatus) ; 
			  }

			String InService = c.getValue("RTPDataSourceCollections",
					"RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
			// Boolean p2 = lRp.validationPoint(service, InService, input, c);
			String P3 = lRp.validationPointIccID(IccID, dataInputIccID, input, c);
			Application.showMessage("P3 value:::: " + P3);
			System.out.println("P3 value:::: " + P3);
		} else {
			Application.showMessage("IccId is null ::" + IccID);
			System.out.println("IccId is null ::" + IccID);
		}
		
		/* TestCaseName TC5_US442585 change Completed */
	}
	 
//---------------------------
 public void XHOM_ScheduleDisconnectChangingDate(Object input,
         ScriptingContext c) throws ClassNotFoundException, IOException,
InterruptedException, Exception, SQLException 
{
	 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
     Date date=new Date();
     DBCursor  rs,rs1;
     MongoClient mongodb;
     DBCollection coll;
	    DB db = null;
	    DBObject query = null;
	    UpdateResult retrieveData ;	
	 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
     Calendar cal = Calendar.getInstance();
     Calendar cal1 = Calendar.getInstance();
    
     cal.add(Calendar.DATE, -1);    
     dateFormat.format(cal.getTime());
     //cal1.add(Calendar.DATE, -1);    
     dateFormat.format(cal1.getTime());
     String[] existingCreationDate = null;
	Application.showMessage("Date is"+dateFormat.format(cal.getTime()));
	Application.showMessage("Date is"+dateFormat.format(cal1.getTime()));
     try
     {
		mongodb =XH.MongoDBConnectReplicaSet(input, c);
		db= mongodb.getDB("xhom-qa");  
		Set<String> collections = db.getCollectionNames();
		coll = db.getCollection("XHOM_SCHEDULE_DISCONNECT"); 
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ACCOUNTNUMBER", c.getValue("ACC_input"));
		DBCursor qu = coll.find(searchQuery).limit(1);
		
		BasicDBObject newDocument = new BasicDBObject();
		
		SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US); 
		String date1 = dateFormat.format(cal.getTime()).concat("T01:00:00+0000");
		Application.showMessage("Date is"+ISO8601DATEFORMAT.parse(date1));
		String date2 = dateFormat.format(cal1.getTime()).concat("T01:00:00+0000");
		Application.showMessage("Date is"+ISO8601DATEFORMAT.parse(date2));

		//newDocument.append("$set", new BasicDBObject().append("CREATIONDATE", dateFormat.format(cal.getTime()).concat("T00:00:00.000Z")).append("SCHEDULEDATE", dateFormat.format(cal1.getTime()).concat("T00:00:00.000Z")));

		newDocument.append("$set", new BasicDBObject().append("CREATIONDATE",ISO8601DATEFORMAT.parse(date1)).append("SCHEDULEDATE",ISO8601DATEFORMAT.parse(date2)));		
		Application.showMessage("searchquery is"+searchQuery);
		Application.showMessage("updatequery is"+newDocument);
		coll.update(searchQuery, newDocument);
		
		
		
		mongodb.close();
     }
     catch(Exception e)
     {
    	 Application.showMessage("Exception occurred" +e);
     }
}
 
 
 //------------------
 
 public void XHOM_ScheduleDisconnectAfterUpdatingTableChangingDate(Object input,
         ScriptingContext c) throws ClassNotFoundException, IOException,
InterruptedException, Exception, SQLException 
{
	 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
     Date date=new Date();
     DBCursor  rs,rs1;
     MongoClient mongodb;
     DBCollection coll;
	    DB db = null;
	    DBObject query = null;
	    UpdateResult retrieveData ;	
	 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
     Calendar cal = Calendar.getInstance();
     Calendar cal1 = Calendar.getInstance();
    
    // cal.add(Calendar.DATE, -1);    
     dateFormat.format(cal.getTime());
     cal1.add(Calendar.DATE, +1);    
     dateFormat.format(cal1.getTime());
     c.setValue("scheduleDate",dateFormat.format(cal1.getTime()));
     String[] existingCreationDate = null;
	Application.showMessage("Date is"+dateFormat.format(cal.getTime()));
	Application.showMessage("Date is"+dateFormat.format(cal1.getTime()));
     try
     {
		mongodb =XH.MongoDBConnectReplicaSet(input, c);
		db= mongodb.getDB("xhom-qa");  
		Set<String> collections = db.getCollectionNames();
		coll = db.getCollection("XHOM_SCHEDULE_DISCONNECT"); 
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ACCOUNTNUMBER", c.getValue("ACC_input"));
		DBCursor qu = coll.find(searchQuery).limit(1);
		
		BasicDBObject newDocument = new BasicDBObject();
		
		SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US); 
		String date1 = dateFormat.format(cal.getTime()).concat("T01:00:00+0000");
		Application.showMessage("Date is"+ISO8601DATEFORMAT.parse(date1));
		String date2 = dateFormat.format(cal1.getTime()).concat("T01:00:00+0000");
		Application.showMessage("Date is"+ISO8601DATEFORMAT.parse(date2));

		//newDocument.append("$set", new BasicDBObject().append("CREATIONDATE", dateFormat.format(cal.getTime()).concat("T00:00:00.000Z")).append("SCHEDULEDATE", dateFormat.format(cal1.getTime()).concat("T00:00:00.000Z")));

		newDocument.append("$set", new BasicDBObject().append("CREATIONDATE",ISO8601DATEFORMAT.parse(date1)).append("SCHEDULEDATE",ISO8601DATEFORMAT.parse(date2)));		
		Application.showMessage("searchquery is"+searchQuery);
		Application.showMessage("updatequery is"+newDocument);
		coll.update(searchQuery, newDocument);
		
		
		
		mongodb.close();
     }
     catch(Exception e)
     {
    	 Application.showMessage("Exception occurred" +e);
     }
}
 
 
 
 
 
 
 //----------------------------
	/*public Map<String, String> XHOM_connectThirtyDayDisconnectDB(Object input,
             
             ScriptingContext c) throws ClassNotFoundException, IOException,
			NullPointerException, InterruptedException
			{
		Application
				.showMessage("------------------------------------------------------------------");
		Application
				.showMessage("*##Connecting to connectThirtyDayDisconnectDB retrieving data... ");
		Application
				.showMessage("------------------------------------------------------------------");
		DBCursor retrieveData;
        DBObject obj = null;
        MongoClient mongodb;
        DB db = null;
        DBCollection coll;
        DBObject query = null;
        DBCursor rs ;
		String AccountNumber = c.getValue("CcentralNum");
		String subAccount = c.getValue("ACC_input");
		Application.showMessage("The C-Central Number is ::" + AccountNumber);
		Application.showMessage("The Account Number is ::" + subAccount);
		String result =null;
		String dStatus = null;
		String dDateBegin = null;
		String dService = null;
		String dICCID = null;
		Map<String, String> returnMap = new HashMap<String, String>();
		Thread.sleep(5000);
try{
		mongodb=XH.MongoDBConnect(input, c);
		db= mongodb.getDB("xhom-qa");  //BOMQA1
		Set<String> collections = db.getCollectionNames(); 
        Application.showMessage(collections+"\n"); 			 
		coll = db.getCollection("XHOM_THIRTY_DAYS_DISCONNECT");
		
		//BasicDBObject whereQuery = new BasicDBObject();
		query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(subAccount).get(),new QueryBuilder().start().put("CMSACCOUNTID").is(AccountNumber).get()).get();
		Application.showMessage("whereQuery is :"+query); 
		BasicDBObject fields = new BasicDBObject(); 	   
		fields.put("SENDTIME", 1); 
		rs = coll.find(query).sort(fields).limit(20); 
		//DBCursor rs= retrieveData;
		
		Application.showMessage("Cursor:"+rs); 
		Application.showMessage("Entering here..."+rs.count());
        

		while (rs.hasNext()) 
		{
			obj=rs.next();	
			Application.showMessage("obj IS"+obj);
			dStatus = ((BasicBSONObject) obj).getString("STATUS");
			dDateBegin = ((BasicBSONObject) obj).getString("DATE_HOLD_BEGAN");
			dService = ((BasicBSONObject) obj).getString("SERVICE");
			dICCID = ((BasicBSONObject) obj).getString("ICCID");
			}
		Application.showMessage("STATUS is ::" + dStatus);
		Application.showMessage("dDateBegin is ::" + dDateBegin);
		Application.showMessage("dService is ::" + dService);
		Application.showMessage("dICCID is ::" + dICCID);
		returnMap.put("status", dStatus.trim());
		returnMap.put("date", dDateBegin.trim());
		returnMap.put("service", dService.trim());
		returnMap.put("ICCID", dICCID);
}
	catch(Exception e){          
	Application.showMessage("ERROR"+e.getClass().getName() + ": " + e.getMessage()); 
}
		return returnMap;

	}*/
 
 
 
 
 public Map<String, String> XHOM_connectThirtyDayDisconnectDB(Object input,
         
         ScriptingContext c) throws ClassNotFoundException, IOException,
		NullPointerException, InterruptedException
		{
	Application
			.showMessage("------------------------------------------------------------------");
	Application
			.showMessage("*##Connecting to connectThirtyDayDisconnectDB retrieving data... ");
	Application
			.showMessage("------------------------------------------------------------------");
	DBCursor retrieveData;
    DBObject obj = null;
    MongoClient mongodb;
    DB db = null;
    DBCollection coll;
    DBObject query = null;
    int count=0;
    int waitingtime=0;
    DBCursor rs;
	String AccountNumber = c.getValue("CcentralNum");
	String subAccount = c.getValue("ACC_input");
	Application.showMessage("The C-Central Number is ::" + AccountNumber);
	Application.showMessage("The Account Number is ::" + subAccount);
	String result =null;
	String dStatus = null;
	String dDateBegin = null;
	String dService = null;
	String dICCID = null;
	Map<String, String> returnMap = new HashMap<String, String>();
	Thread.sleep(5000);
try{
	
	 do
		{
			Thread.sleep(1000);
	mongodb=XH.MongoDBConnect(input, c);
	db= mongodb.getDB("xhom-qa");
	 				 
	coll = db.getCollection("XHOM_THIRTY_DAYS_DISCONNECT");
	//BasicDBObject whereQuery = new BasicDBObject();
	query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(subAccount).get(),new QueryBuilder().start().put("CMSACCOUNTID").is(AccountNumber).get()).get();
	Application.showMessage("whereQuery is :"+query); 
	BasicDBObject fields = new BasicDBObject(); 	   
	fields.put("SENDTIME", 1); 
	retrieveData = coll.find(query).sort(fields).limit(20); 
	rs= retrieveData;
	
	if(rs.count()==0)
    {
    	   waitingtime++;
			count++;					
			//retrieveData=null;
			if(count<=119)
			{
				rs.close();
			}
			else
			{
				rs=null;
			}
    }
    else
     {
    	while (rs.hasNext()) 
    	{
    		obj=rs.next();	
    		Application.showMessage("obj IS"+obj);
    		dStatus = ((BasicBSONObject) obj).getString("STATUS");
    		dDateBegin = ((BasicBSONObject) obj).getString("DATE_HOLD_BEGAN");
    		dService = ((BasicBSONObject) obj).getString("SERVICE");
    		dICCID = ((BasicBSONObject) obj).getString("ICCID");
    		}
    	Application.showMessage("STATUS is ::" + dStatus);
    	Application.showMessage("dDateBegin is ::" + dDateBegin);
    	Application.showMessage("dService is ::" + dService);
    	Application.showMessage("dICCID is ::" + dICCID);
    	returnMap.put("status", dStatus.trim());
    	returnMap.put("date", dDateBegin.trim());
    	returnMap.put("service", dService.trim());
    	returnMap.put("ICCID", dICCID);
    	   break;
     }
		}while(count < 120);
	rs.close();
}
catch(Exception e){          
Application.showMessage("ERROR"+e.getClass().getName() + ": " + e.getMessage()); 
}
	return returnMap;

}
	//----------------------------CommonDBValidate validation method------------------------------
	    
	    public void CommonDBValidate(Object input, ScriptingContext c)
				throws Exception 
				{
	    	DBCursor retrieveData ;	
			MongoClient mongodb;
		    DBCollection coll;
		    DB db = null;
		    DBObject query = null;
		    DBObject obj = null;
			int v1 = 0, i = 0;
			Thread.sleep(10000);
			String Time = (String) c.get("BaseTime");
			Application.showMessage("BaseTime is::" + Time);
			mongodb =XH.MongoDBConnect(input, c);
			db= mongodb.getDB("XHOMQA1");  
			coll = db.getCollection("XHOM_MESSAGELOGGING"); 
			ResultSet rs1;
			Date todayDate = new Date();
			TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
			DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
			todayDateFormat.setTimeZone(timeZone);
			String strTodayDate = todayDateFormat.format(todayDate);
			Application.showMessage("Todays Date as per EST is::" + strTodayDate);

			try {

				mongodb=XH.MongoDBConnect_Common(input, c);
				String Acc = c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber");
				
				query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(Acc).get(),new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
				
				if (query == null)
				{
					Application.showMessage("Null Record set found in WorKlist DB for AccountNumber'"+ c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber"));
				}
				
				Application.showMessage("whereQuery is :"+query); 
				BasicDBObject fields = new BasicDBObject(); 	   
				fields.put("SENDTIME", 1); 
				retrieveData = coll.find(query).sort(fields).limit(20); 
				
				
				while (retrieveData.hasNext()) {
					obj=retrieveData.next();	
					String errorCode = ((BasicBSONObject) obj).getString("ERRORCODE");
					String errorSource = ((BasicBSONObject) obj).getString("ERRORSOURCE");
					Application.showMessage("Error Source from common is"
							+ errorSource);
					String errorText = ((BasicBSONObject) obj).getString("ERRORTEXT");
					String PARTICIPANTOPERATION = ((BasicBSONObject) obj)
							.getString("PARTICIPANTOPERATION");
					String BILLINGSYSTEM = ((BasicBSONObject) obj).getString("BILLINGSYSTEM");
					String SERVICETYPE = ((BasicBSONObject) obj).getString("SERVICETYPE");
					String ORDER_ID = ((BasicBSONObject) obj).getString("ORDER_ID");
					Application.showMessage("Error Code is ::" + errorCode);
					Application.showMessage("errorSource is ::" + errorSource);
					Application.showMessage("PARTICIPANTOPERATION is ::"
							+ PARTICIPANTOPERATION);
					Application.showMessage("errorText is ::" + errorText);
					Application.showMessage("BILLINGSYSTEM is ::" + BILLINGSYSTEM);
					Application.showMessage("SERVICETYPE is ::" + SERVICETYPE);
					Application.showMessage("ORDER_ID is ::" + ORDER_ID);

					Application
							.showMessage("****** Validating Common Data Base worklisting********");
					if (errorText.equals(c.get("ErrorTextOu")))
					{
						Application
								.showMessage("Error text from order update is validated successfully with error text from common DB");
					} 
					else
					{
						Application.showMessage("ERROR TEXT IS NOT VALIDATED");
					}

					if (errorCode.equals(c.get("ErrorCodeOu")))
					{
						Application
								.showMessage("Error code from order update is validated successfully with error code from common DB");
					} 
					else 
					{
						Application.showMessage("ERROR CODE IS NOT VALIDATED");
					}

					if (errorSource.equals(c.get("ErrorSourceOu")))
					{
						Application
								.showMessage("Error SOURCE from order update is validated successfully with error Source from common DB");
					} 
					else
					{
						Application.showMessage("ERROR SOURCE IS NOT VALIDATED");
					}

				}

			
			} catch (SQLException e)
			{
				System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				return;
			}

		}
	    
	 ////--------------------------------------------------------------------------------------////
	 
	 public void NoDataSetFound(Object input, ScriptingContext c,
				String Account, String Operation) throws InterruptedException,
				ClassNotFoundException, IOException, SQLException
				{
		 List<String> addIssues=new ArrayList();
		    String Issues="Issues from NodataSetFound:::";
			DBCursor retrieveData ;	
			MongoClient mongodb;
		    DBCollection coll;
		    DB db = null;
		    DBObject query = null;
		    DBObject obj = null;
			int v1 = 0, i = 0;
			Thread.sleep(10000);
			//String Time = (String) c.get("BaseTime");
			String Time = c.get("BaseTime").toString();
			Application.showMessage("BaseTime is::" + Time);
			mongodb =XH.MongoDBConnect(input, c);
			db= mongodb.getDB("XHOMQA1");  
			coll = db.getCollection("XHOM_MESSAGELOGGING");  
			/*rs = st.executeQuery("select * from (select * from cwmessagelog where user_data2 = '"
					+ Account
					+ "' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"
					+ Time + "' order by creation_time desc)where rownum <= 20");*/
			if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("eep:XHSEEPOrder/xhsevent")))
    		{
    	   query = QueryBuilder.start().and(new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("_id").notEquals(rowmsg).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
	    	
    		}
    else if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("cmb:commonOrderService/orderUpdate")))
    {
    	query = QueryBuilder.start().and(new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("_id").notEquals(rowmsg).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
    	}
    else
    {
    	query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(c.getValue("ACC_input")).get(),new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
         }
			 Application.showMessage("Querying is"+query);  
			    BasicDBObject fields = new BasicDBObject();		     
			    fields.put("SENDTIME",1);
			    retrieveData = coll.find(query).sort(fields).limit(20);     
			    Application.showMessage("QueryingrETRIEVEdATA is"+retrieveData.count());  
	 
			//Application.showMessage("The dataSet is::" + rs.toString());

			/*if (retrieveData.getRow() == 0) {
				Application.showMessage("No Record Sets Found..!");
			} */
			if (retrieveData.count() == 0) {
				Application.showMessage("No Record Sets Found..!");
			}
			else 
			{
				while (retrieveData.hasNext()) 
				{
					obj=retrieveData.next();	
					//String operation = rs.getString("OPERATION");
					String operation = ((BasicBSONObject) obj).getString("OPERATION");
					Application.showMessage("OPeration is ::" + operation);

					if (operation.equals(Operation)) {
						Application.showMessage("Extra call OrderUpdate Found");
						addIssues.add("Order Update call found..!");
						v1 = 1;

					} else {
						Application
								.showMessage("Extra call OrderUpdate Found for operation ::"
										+ Operation);
					}

					if (v1 == 1) {
						addIssues.add("Extra Call Found for Operation ::" + Operation);
						break;
					} else {
						Application.showMessage(i
								+ "th row data is not an Extra call ");
					}
					i++;
				}
				//st.close();
			}
		
				}
public void XHOM_threeDaycvrTableValidation(String Instatus, Object input,ScriptingContext c) throws ClassNotFoundException, IOException,InterruptedException
{
	LibraryRtp_UpDown Rp=new LibraryRtp_UpDown();
     Thread.sleep(3000);

   Map<String, String> getMapData = new HashMap<String, String>();
   getMapData = XHOM_connectThreeDayCVRDB(input, c);
   String status = getMapData.get("status");
   Application.showMessage("The status in validation process is ::"
                        + status);
   String date = getMapData.get("date");
   Application.showMessage("The date in validation process is ::" + date);
  Boolean p1 = Rp.validationPointIgnoreCase(status, Instatus, input, c);
  if(p1.TRUE)
  {
	 c.put("threeDaycvrTableValidationIssues", "no") ;
  }
  else
  {
	  c.put("threeDaycvrTableValidationIssues", "Validation is NOT Successfull with Input Data..!!! ::"+ status + "and" + Instatus) ; 
  }

 }
         
//---------

public void changingdateThreeDayCVRTable(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
{

	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
     Date date=new Date();
     DBCursor  rs,rs1;
     MongoClient mongodb;
     DBCollection coll;
	    DB db = null;
	    DBObject query = null;
	    UpdateResult retrieveData ;	
	 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
     Calendar cal = Calendar.getInstance();
     Calendar cal1 = Calendar.getInstance();
     cal.add(Calendar.DATE, -3);    
     dateFormat.format(cal.getTime());
     cal1.add(Calendar.DATE, -1);    
     dateFormat.format(cal1.getTime());
     String[] existingCreationDate = null;
	Application.showMessage("Date is"+dateFormat.format(cal.getTime()));
	Application.showMessage("Date is"+dateFormat.format(cal1.getTime()));
     try
     {
		mongodb =XH.MongoDBConnectReplicaSet(input, c);
		db= mongodb.getDB("xhom-qa");  
		Set<String> collections = db.getCollectionNames();
		coll = db.getCollection("XHOM_THREE_DAYS_CVR_DISCONNECT"); 
		
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ACCOUNTNUMBER", c.getValue("ACC_input"));
		DBCursor qu = coll.find(searchQuery).limit(1);
		
		BasicDBObject newDocument = new BasicDBObject();
		
		SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US); 
		String date1 = dateFormat.format(cal.getTime()).concat("T01:00:00+0000");
		Application.showMessage("Date is"+ISO8601DATEFORMAT.parse(date1));
	
		newDocument.append("$set", new BasicDBObject().append("DATE_HOLD_BEGAN",ISO8601DATEFORMAT.parse(date1)));		
		Application.showMessage("searchquery is"+searchQuery);
		Application.showMessage("updatequery is"+newDocument);
		coll.update(searchQuery, newDocument);
		
		
		
		mongodb.close();
     }
     catch(Exception e)
     {
    	 Application.showMessage("Exception occurred" +e);
     }
}



//----------


public void changingMonthsThirtyDaysTable(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
{

	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
     XHOM_LibraryRtp XH=new XHOM_LibraryRtp();
     Date date=new Date();
     DBCursor  rs,rs1;
     MongoClient mongodb;
     DBCollection coll;
	    DB db = null;
	    DBObject query = null;
	    UpdateResult retrieveData ;	
	 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
     Calendar cal = Calendar.getInstance();
     Calendar cal1 = Calendar.getInstance();
     cal.add(Calendar.MONTH, -1);    
     dateFormat.format(cal.getTime());
     cal1.add(Calendar.DATE, -1);    
     dateFormat.format(cal1.getTime());
     String[] existingCreationDate = null;
	Application.showMessage("Date is"+dateFormat.format(cal.getTime()));
	Application.showMessage("Date is"+dateFormat.format(cal1.getTime()));
     try
     {
		mongodb =XH.MongoDBConnectReplicaSet(input, c);
		db= mongodb.getDB("xhom-qa");  
		Set<String> collections = db.getCollectionNames();
		
		coll = db.getCollection("XHOM_THIRTY_DAYS_DISCONNECT");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("ACCOUNTNUMBER", c.getValue("ACC_input"));
		DBCursor qu = coll.find(searchQuery).limit(1);
		
		BasicDBObject newDocument = new BasicDBObject();
		
		SimpleDateFormat ISO8601DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US); 
		String date1 = dateFormat.format(cal.getTime()).concat("T01:00:00+0000");
		Application.showMessage("Date is"+ISO8601DATEFORMAT.parse(date1));
	
		newDocument.append("$set", new BasicDBObject().append("DATE_HOLD_BEGAN",ISO8601DATEFORMAT.parse(date1)));		
		Application.showMessage("searchquery is"+searchQuery);
		Application.showMessage("updatequery is"+newDocument);
		coll.update(searchQuery, newDocument);
		
		
		
		mongodb.close();
     }
     catch(Exception e)
     {
    	 Application.showMessage("Exception occurred" +e);
     }
}







//-----------



public Map<String, String> XHOM_connectThreeDayCVRDB(Object input,ScriptingContext c) throws ClassNotFoundException, IOException,NullPointerException, InterruptedException
{
	LibraryRtp_UpDown LT=new LibraryRtp_UpDown();
      Application.showMessage("------------------------------------------------------------------");
    Application.showMessage("*##Connecting to connectTHREEDAYSCVRDISCONNECT retrieving data... ");
    Application.showMessage("------------------------------------------------------------------");
    DBCursor retrieveData;
    DBObject obj = null;
    MongoClient mongodb;
    DB db = null;
    DBCollection coll;
    DBObject query = null;
    String result=null;
    String dStatus = null;
    String dDateBegin = null;
   String subAccount = c.getValue("ACC_input");
    Application.showMessage("The Account Number is ::" + subAccount);
    Map<String, String> returnMap = new HashMap<String, String>();
    Thread.sleep(5000);

try {
	mongodb=XH.MongoDBConnect(input, c);
	db= mongodb.getDB("xhom-qa");  				 
	coll = db.getCollection("XHOM_THREE_DAYS_CVR_DISCONNECT");
	//BasicDBObject whereQuery = new BasicDBObject();
	query = QueryBuilder.start().put("ACCOUNTNUMBER").is(subAccount).get();
	Application.showMessage("whereQuery is :"+query); 
	BasicDBObject fields = new BasicDBObject(); 	   
	fields.put("SENDTIME", 1); 
	retrieveData = coll.find(query).sort(fields).limit(20); 
	DBCursor rs= retrieveData;
	Application.showMessage("Entering here..."+rs.count());
    
	Application.showMessage("Cursor:"+rs); 

	while (rs.hasNext()) 
	{
		obj=rs.next();	
		Application.showMessage("cursor.next()"+obj);
		dStatus = ((BasicBSONObject) obj).getString("STATUS");
		dDateBegin = ((BasicBSONObject) obj).getString("DATE_HOLD_BEGAN");
		
		}
        Application.showMessage("STATUS is ::" + dStatus);
        Application.showMessage("dDateBegin is ::" + dDateBegin);
        returnMap.put("status", dStatus.trim());
        returnMap.put("date", dDateBegin.trim());

}
	catch(Exception e){          
	Application.showMessage("ERROR"+e.getClass().getName() + ": " + e.getMessage()); 

 }
 return returnMap;

}


	public String rtpTestInterface_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException, ParserConfigurationException, SAXException, XPathExpressionException, NullPointerException
    {
                                 LibraryRtp  lC= new LibraryRtp();
                                 DBCursor  rs;
                                 DBObject obj = null;
                                 int callFound=0, v1=0;
                                 String xmldata1 ="receive_data";
             String xmldata2 ="SEND_DATA";  
             Thread.sleep(5000);
             List<String> addIssues=new ArrayList();
     	    String Issues="Issues from xhsEvent:::";
     	   String Time= c.get("BaseTime").toString();
             Application.showMessage("----------------------------------------------------------");
             Application.showMessage("************rtpTestInterface_Validate Function************");    
             Application.showMessage("----------------------------------------------------------");
             HashMap Operation=XH.findingoperations(input, c);
    	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("xhsevent"));
    	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("xhsevent"));

                                try
                                {
                                                
                                	rs=XH.XHOM_reduceThinkTimeRTP(input, c);
                        
                                	
                                	while (rs.hasNext())
                        	        {
                        	       obj=rs.next();
                        	          if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
                        	          {
                        	 rowmsg=((BasicBSONObject) obj).getString("_id");
                        	 if(((BasicBSONObject) obj).getString("RESPONSE")==null || ((BasicBSONObject) obj).getString("RESPONSE")=="{null}")  //RESPONSE
                             {
                             
                                 Application.showMessage("Your Recieve XML is NULL \n");
                                 
                                 String senddata =lC.RemoveNameSpaces(((BasicBSONObject) obj).getString("REQUEST"));//REQUEST
                                 Application.showMessage("Your Recieve XML is::\n"+senddata);
                                           
                                String accountNumber_rtpTest=lC.nodeFromKey(senddata,"<AccountNumber>","</AccountNumber>");
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
                                                 String locationId_rtpTest=lC.GetValueByXPath(senddata, "/EEPEvent/Body/Location/@locationId");//XH.getTrackingDetails(input, c, senddata);////EEPEvent/Body/Location/@locationId
                                                 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
                                                                           
                                                                            if(locationId_rtpTest==null)
                                                                            {
                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                            addIssues.add("locationId is NULL");
                                                                                            continue;
                                                                            }
                                                                            else
                                                                            {
                                                                                
                                                                                 if(locationId_rtpTest.toString().trim().equals(c.getValue("sHOUSE_KEY").toString().trim()) || (locationId_rtpTest.toString().trim()).equalsIgnoreCase("XPath ERROR.Either NULL or Xpath needs to be Fixed"))
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
                                                                                                                         
                                                                                                                         String PhoneNumber=  lC.nodeFromKey(senddata,"<HomePhone>","</HomePhone>");
                                                                                                                         c.put("PhoneNumber",PhoneNumber);
                                                                                                                         Application.showMessage("PhoneNumber is::"+c.get("PhoneNumber")); 
                                                                                                                         
                                                                                                                        
                                                                                                                         
                                                                                                                         
                                                                                                                        //------------Yamini
                                                                                                                         if((locationId_rtpTest.toString().trim()).equalsIgnoreCase("XPath ERROR.Either NULL or Xpath needs to be Fixed"))
                                                                                                                         {
                                                                                                                        	 c.put("AddressDetails", "null");
                                                                                                                         }
                                                                                                                         else
                                                                                                                         {
                                                                                                                             
                                                                                                                         try
                                                                                                                         {
                                                                                                                        	 
                                                                                                                        	 
                                                                                                                        	 String Zipcode=  lC.nodeFromKey(senddata,"<Zipcode>","</Zipcode>");
                                                                                                                        	 
                                                                                                                             if(Zipcode.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!")	)
                                                                                                                             {
                                                                                                                             	c.put("Ezipcode","null");
                                                                                                                             }
                                                                                                                             
                                                                                                                             else if(Zipcode.length()>5)
                                                                                                                              {
                                                                                                                            	 String s =Character.toString( Zipcode.charAt(0)),s1=Character.toString( Zipcode.charAt(5));
                                                                                                                            	 
                                                                                                                             	 for(int i=1;i<Zipcode.length();i++)
                                                                                                                             	 {
                                                                                                                             		 if(i<5)
                                                                                                                             		 {
                                                                                                                             		s=s.concat(Character.toString(Zipcode.charAt(i)));
                                                                                                                             		 }
                                                                                                                             		 else
                                                                                                                             		 {
                                                                                                                             			if(i!=5)
                                                                                                                             			{
                                                                                                                             			s1=s1.concat(Character.toString(Zipcode.charAt(i)));
                                                                                                                             			}
                                                                                                                             		 }
                                                                                                                             		 
                                                                                                                             	 }
                                                                                                                             	 c.put("Ezipcode",s.concat("-").concat(s1));
                                                                                                                              }
                                                                                                                              else
                                                                                                                              {
                                                                                                                             	 c.put("Ezipcode",Zipcode);
                                                                                                                              }
                                                                                                                              Application.showMessage("Ezipcode is::"+c.get("Ezipcode")); 
                                                                                                                        	 String Address=  lC.nodeFromKey(senddata,"<AddressLine1>","</AddressLine1>");
                                                                                                                             c.put("Address",Address);
                                                                                                                             c.put("addAddress",Address);
                                                                                                                             Application.showMessage("Address is::"+c.get("Address")); 
                                                                                                                             
                                                                                                                             String State=  lC.nodeFromKey(senddata,"<State>","</State>");
                                                                                                                             c.put("State",State);
                                                                                                                             Application.showMessage("State is::"+c.get("State")); 
                                                                                                                             
                                                                                                                             String city=  lC.nodeFromKey(senddata,"<City>","</City>");
                                                                                                                             c.put("Ecity",city);
                                                                                                                             c.setValue("City",city);
                                                                                                                             Application.showMessage("Ecity is::"+c.get("Ecity")); 
                                                                                                                             if(Address.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                                                                                                             {
                                                                                                                            	 c.put("AddressDetails", "null");
                                                                                                                             }
                                                                                                                             else
                                                                                                                             {
                                                                                                                            	 c.put("AddressDetails", "Notnull");
                                                                                                                             }
                                                                                                                             
                                                                                                                         }
                                                                                                                         catch(Exception e)
                                                                                                                         {
                                                                                                                        	
                                                                                                                         }
                                                                                                                         }
                                                                                                                        

                                                                                                                         
                                                                                                                         if(v1*callFound==1)
                                                                                                                         {
                                                                                                                     	 c.put("xhsEventIssues","no");
                                                                                                                             Application.showMessage("Validation Points are Success");
                                                                                                                            
                                                                                                                             break;
                                                                                                                         }
                                                                                                                         
                                                                                                                         else
                                                                                                                         {
                                                                                                                         	//Yamini
                                                                                                                         	 c.put("result", "false");
                                                                                                                         	 Application.showMessage("No call Found"); 
                                                                                                                         	 if(callFound!=1)
                                                                                                                         	 {
                                                                                                                         		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
                                                                                                                                  
                                                                                                                         	 }
                                                                                                                	        	 for(int i=0;i<addIssues.size();i++)
                                                                                                                	        	 {
                                                                                                                	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
                                                                                                                	        	 }
                                                                                                                	        	// c.report(Issues.concat("WebService Call :: AddGroup_Validate is Failed with Validation Errors").concat("**"));
                                                                                                                	         
                                                                                                                            c.put("xhsEventIssues", Issues.concat("WebService Call :: xhsEvent_Validate is Failed with Validation Errors").concat("**"));
                                                                                                                                   
                                                                                                                             break;
                                                                                                                         }
                                                                                             }
                                                                                
                                                                                
                                                                                             else
                                                                                             {//XPath ERROR.Either NULL or Xpath needs to be Fixed
                                                                                                 Application.showMessage("Location ID *******NOT FOUND ***");
                                                                                                 continue;
                                                                                             }
                                                                            }                                                              
                                }
                             
                                                       
                            }
                           /* else if(rs.getBlob("SEND_DATA")==null)
                            {
                              
                   
                               String recievedata=lC.extractXml(rs,xmldata1);*/
                        	 
                        	 else if(((BasicBSONObject) obj).getString("REQUEST")==null  || ((BasicBSONObject) obj).getString("REQUEST")=="{null}")
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
                                                                                            addIssues.add("locationId is NULL");
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
                                                                                                                     	 c.put("xhsEventIssues","no");
                                                                                                                             Application.showMessage("Validation Points are Success");
                                                                                                                            
                                                                                                                             break;
                                                                                                                         }
                                                                                                                         
                                                                                                                         else
                                                                                                                         {
                                                                                                                         	//Yamini
                                                                                                                         	 c.put("result", "false");
                                                                                                                         	 Application.showMessage("No call Found"); 
                                                                                                                         	 if(callFound!=1)
                                                                                                                         	 {
                                                                                                                         		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
                                                                                                                                  
                                                                                                                         	 }
                                                                                                                	        	 for(int i=0;i<addIssues.size();i++)
                                                                                                                	        	 {
                                                                                                                	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
                                                                                                                	        	 }
                                                                                                                	        	// c.report(Issues.concat("WebService Call :: AddGroup_Validate is Failed with Validation Errors").concat("**"));
                                                                                                                	         
                                                                                                                            c.put("xhsEventIssues", Issues.concat("WebService Call :: xhsEvent_Validate is Failed with Validation Errors").concat("**"));
                                                                                                                                   
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
                                                //String recievedata=lC.extractXml(rs,xmldata1);
                                            	
                                            	 String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
                                                 String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
                                                 Application.showMessage("Your Recieve XML is::\n"+senddata);
                                                 Application.showMessage("RECIEVE XML is ::\n"+recievedata);
                                                                         
                                                            String accountNumber_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/@accountNumber");
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
                                                                 String locationId_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Location/@locationId");
                                                                 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
                                                                                           
                                                                                            if(locationId_rtpTest==null)
                                                                                            {
                                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                                Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
                                                                                                            addIssues.add("locationId is NULL");
                                                                                                            continue;
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                                
                                                                                                if(locationId_rtpTest.toString().trim().equals(c.getValue("sHOUSE_KEY").toString().trim()) || (locationId_rtpTest.toString().trim()).equalsIgnoreCase("XPath ERROR.Either NULL or Xpath needs to be Fixed"))
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
                                                                                                                                        
                                                                                                                                        String PhoneNumber=  lC.nodeFromKey(senddata,"<HomePhone>","</HomePhone>");
                                                                                                                                        c.put("PhoneNumber",PhoneNumber);
                                                                                                                                        Application.showMessage("PhoneNumber is::"+c.get("PhoneNumber")); 
                                                                                                                                        
                                                                                                                                       
                                                                                                                                        
                                                                                                                                        
                                                                                                                                       //------------Yamini
                                                                                                                                        if((locationId_rtpTest.toString().trim()).equalsIgnoreCase("XPath ERROR.Either NULL or Xpath needs to be Fixed"))
                                                                                                                                        {
                                                                                                                                       	 c.put("AddressDetails", "null");
                                                                                                                                        }
                                                                                                                                        else
                                                                                                                                        {
                                                                                                                                            
                                                                                                                                        try
                                                                                                                                        {
                                                                                                                                       	 
                                                                                                                                       	 
                                                                                                                                       	 String Zipcode=  lC.nodeFromKey(senddata,"<Zipcode>","</Zipcode>");
                                                                                                                                       	 
                                                                                                                                            if(Zipcode.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!")	)
                                                                                                                                            {
                                                                                                                                            	c.put("Ezipcode","null");
                                                                                                                                            }
                                                                                                                                            
                                                                                                                                            else if(Zipcode.length()>5)
                                                                                                                                             {
                                                                                                                                           	 String s =Character.toString( Zipcode.charAt(0)),s1=Character.toString( Zipcode.charAt(5));
                                                                                                                                           	 
                                                                                                                                            	 for(int i=1;i<Zipcode.length();i++)
                                                                                                                                            	 {
                                                                                                                                            		 if(i<5)
                                                                                                                                            		 {
                                                                                                                                            		s=s.concat(Character.toString(Zipcode.charAt(i)));
                                                                                                                                            		 }
                                                                                                                                            		 else
                                                                                                                                            		 {
                                                                                                                                            			if(i!=5)
                                                                                                                                            			{
                                                                                                                                            			s1=s1.concat(Character.toString(Zipcode.charAt(i)));
                                                                                                                                            			}
                                                                                                                                            		 }
                                                                                                                                            		 
                                                                                                                                            	 }
                                                                                                                                            	 c.put("Ezipcode",s.concat("-").concat(s1));
                                                                                                                                             }
                                                                                                                                             else
                                                                                                                                             {
                                                                                                                                            	 c.put("Ezipcode",Zipcode);
                                                                                                                                             }
                                                                                                                                             Application.showMessage("Ezipcode is::"+c.get("Ezipcode")); 
                                                                                                                                       	 String Address=  lC.nodeFromKey(senddata,"<AddressLine1>","</AddressLine1>");
                                                                                                                                            c.put("Address",Address);
                                                                                                                                            c.put("addAddress",Address);
                                                                                                                                            Application.showMessage("Address is::"+c.get("Address")); 
                                                                                                                                            
                                                                                                                                            String State=  lC.nodeFromKey(senddata,"<State>","</State>");
                                                                                                                                            c.put("State",State);
                                                                                                                                            Application.showMessage("State is::"+c.get("State")); 
                                                                                                                                            
                                                                                                                                            String city=  lC.nodeFromKey(senddata,"<City>","</City>");
                                                                                                                                            c.put("Ecity",city);
                                                                                                                                            c.setValue("City",city);
                                                                                                                                            Application.showMessage("Ecity is::"+c.get("Ecity")); 
                                                                                                                                            if(Address.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                                                                                                                            {
                                                                                                                                           	 c.put("AddressDetails", "null");
                                                                                                                                            }
                                                                                                                                            else
                                                                                                                                            {
                                                                                                                                           	 c.put("AddressDetails", "Notnull");
                                                                                                                                            }
                                                                                                                                            
                                                                                                                                        }
                                                                                                                                        catch(Exception e)
                                                                                                                                        {
                                                                                                                                       	
                                                                                                                                        }
                                                                                                                                        }
                                                                                                                                       

                                                                                                                                        
                                                                                                                                        if(v1*callFound==1)
                                                                                                                                        {
                                                                                                                                    	 c.put("xhsEventIssues","no");
                                                                                                                                            Application.showMessage("Validation Points are Success");
                                                                                                                                           
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                                                        
                                                                                                                                        else
                                                                                                                                        {
                                                                                                                                        	//Yamini
                                                                                                                                        	 c.put("result", "false");
                                                                                                                                        	 Application.showMessage("No call Found"); 
                                                                                                                                        	 if(callFound!=1)
                                                                                                                                        	 {
                                                                                                                                        		 addIssues.add("Validation is not Successfull with Input Account Number so it didnt go to the further validation.This  XMLs fetched and validated in greater than "+Time);
                                                                                                                                                 
                                                                                                                                        	 }
                                                                                                                               	        	 for(int i=0;i<addIssues.size();i++)
                                                                                                                               	        	 {
                                                                                                                               	        		 Issues=Issues.concat(addIssues.get(i)).concat(",");
                                                                                                                               	        	 }
                                                                                                                               	        	// c.report(Issues.concat("WebService Call :: AddGroup_Validate is Failed with Validation Errors").concat("**"));
                                                                                                                               	         
                                                                                                                                           c.put("xhsEventIssues", Issues.concat("WebService Call :: xhsEvent_Validate is Failed with Validation Errors").concat("**"));
                                                                                                                                                  
                                                                                                                                            break;
                                                                                                                                        }
                                                                                                            }
                                                                                               
                                                                                               
                                                                                                            else
                                                                                                            {//XPath ERROR.Either NULL or Xpath needs to be Fixed
                                                                                                                Application.showMessage("Location ID *******NOT FOUND ***");
                                                                                                                continue;
                                                                                                            }
                                                                                           }
                                                }
                                            
                             
                            }
                        
                       //  st.close();
                        	 rs.close();
                        }
                                }
                                }
                                catch (SQLException e)
                                {
                                    System.out.println("Connection Failed! Check output console");
                                                e.printStackTrace();
                                               
                                }
								return "true";
                }
	
	public void getWorkOrderValidate(Object input , ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, ParseException
	{
		XHOM_ScheduleUpgradeDowngradeExecutionClass sud=new XHOM_ScheduleUpgradeDowngradeExecutionClass();
		
		 Application.showMessage("---------------------------------------------------------------------------------"); 
		 Application.showMessage("******** Into getWorkOrder Validation ********");
		 Application.showMessage("---------------------------------------------------------------------------------"); 
		 Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     ScheduleUpgradeDowngradeValidation SUDV=new ScheduleUpgradeDowngradeValidation();
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from getWorkorder:::";
	     int count=0;
		    int waitingtime=0;
		    
		    int thinktime=0;		
			DBCursor rs ;		
		    MongoClient mongodb;
		    DBCollection coll;
		    DB db = null;
		    DBObject query = null;
		    DBObject obj = null;
		 int callFound=0,v1=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     
	     String Time= c.get("BaseTime").toString();
	   //  c.setValue("getWorkOrder","true");
	     String scheduleDate=c.getValue("scheduleDate");
	     
	     Application.showMessage("Schedule Date Before Parsing: " + scheduleDate);
	    // String scheduleDateValidate= SUDV.trimScheduleDate(scheduleDate,input, c);
	     String scheduleDateValidate= c.getValue("scheduleDate");
	     Application.showMessage("Schedule Date After Parsing: " + scheduleDateValidate);
	     String EEPDate  =sud.addOneToEEPDate(input, c);
	     Application.showMessage("EEP Date is: " +EEPDate);
	     String TomoDate = SUDV.getTommorowDateString();
	     Application.showMessage("Tomorrow date is"+TomoDate);
	    // lC.findThinktimeOperationRTP(input, c);
	   //  c.setValue("getWorkOrder","false");  
	        
	     
	 	try
		{
			/* Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'SchedulingService:SchedulingServicePort/getWorkOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");*/
	 		Thread.sleep(1000);
			mongodb =XH.MongoDBConnect(input, c);
			db= mongodb.getDB("xhom-qa");  //BOMQA1
			Set<String> collections = db.getCollectionNames(); 
            //Application.showMessage(collections+"\n"); 				 
			coll = db.getCollection("XHOM_MESSAGELOGGING");  //BOM_MESSAGELOGGING
			BasicDBObject whereQuery = new BasicDBObject();
			query = QueryBuilder.start().and(new QueryBuilder().start().put("OPERATION").is("SchedulingService:SchedulingServicePort/getWorkOrder").get(),new QueryBuilder().start().put("CREATION_TIME").greaterThanEquals(c.get("BaseTime")).get()).get();
			
			BasicDBObject fields = new BasicDBObject();		     
		    fields.put("SENDTIME",1);
		    rs = coll.find(query).sort(fields).limit(20);
		   
	         
	         //rs=lC.reduceThinkTimeRTP(input, c);
	         while (rs.hasNext())
	         {
	        	 
	        	 obj=rs.next();
	             if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
	             {
	    rowmsg=((BasicBSONObject) obj).getString("_id");
	            //String rowmsg = rs.getString(1);
	        	 
	           // if(rs.getBlob("receive_data")==null)
	    if(((BasicBSONObject) obj).getString("RESPONSE")==null)
	            {
	            
	            	Application.showMessage("No Response from getWorkOrder... Your Recieve XML is NULL \n");
	            	Application.showMessage("Schedule date is:" +scheduleDateValidate);
	            
	            	if(EEPDate.equals(null))
	            	{ 
	            		Application.showMessage("No Date in EEP...Update Date to next day's Date");
	            		if(scheduleDateValidate.equals(TomoDate))
	            	       {
	            			
	            		   Application.showMessage("No response from GWO:Date Updated to Tomorrow's Date: Date validated");
	            		   
	            	        }
	            	
	            		else
	            		   {
	            			  
	            			  addIssues.add("No response from GWO:Date is NOT Updated to Tomorrow's Date: Date is not validated");
	            			  
	            		   }
	            	}
	            		
	            		
	            	else 
	            	{  
	            		Application.showMessage("DATE FROM EEP");
						if (scheduleDateValidate.equals(EEPDate))
	            	      {
	            			
	            		   Application.showMessage("No response from GWO:Date from EEP ... DATE is Validated");
	            		   
	            	      }
	            	
	            		else
	            		  {
	            			
	            			addIssues.add("No response from GWO:Date from EEP ... Date is not validated");
	            			
	            		  }
	            	}
	            	            	            	
	              
	            }
	            
	            else
	            {
	               Application.showMessage("Error Response from getWorkOrder...");          
		          // String recievedata = lC.extractXml(rs,xmldata1);
	               String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		           if(recievedata.contains("serviceFault"))
		           {
		        	   if(EEPDate.equals(null))
		            	{ 
		            		Application.showMessage("Update Date to next day's Date");
		            		if(scheduleDateValidate.equals(TomoDate))
		            	       {
		            			
		            		   Application.showMessage("Error response from GWO: Update date to tomorrow's Date... Date validated");
		            		   
		            	        }
		            	
		            		else
		            		   {
		            			  
		            			  addIssues.add("Error response from GWO: Updattion of date to tomorrow's Date Failed... Date is not validated");
		            			  
		            		   }
		            	}
		            		
		            		
		            	else 
		            	{  
		            		Application.showMessage("DATE should be  FROM EEP");
		            		 if(scheduleDateValidate.equals(EEPDate))
		            	      {
		            			
		            		   Application.showMessage("Error response from GWO: Date from EEP...DATE is Validated");
		            		   
		            	      }
		            	
		            		else
		            		  {
		            			
		            			addIssues.add("Error response from GWO:Date is not from EEP ..Date is not validated");
		            			
		            		  }
		            	}
		           }
		           else
		           {
		        	   Application.showMessage("DATE is from Scheduling Service Validation");
		        	   String date= lC.nodeFromKey(recievedata,"<typ:date>","</typ:date>");
		        	   if(scheduleDateValidate.equals(date))
	            	      {
	            			
	            		   Application.showMessage("Date from Scheduling service: DATE is Validated");
	            		   
	            	      }
	            	
	            		else
	            		  {
	            			
	            			addIssues.add("Date from Scheduling service:Date is not validated");
	            			
	            		  }
		        	   
		           }
	            	
	            }
	            
	            
	           }      	
		           
	       //  st.close();
		}	
		}
		catch (Exception e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 	
	 	 Application.showMessage("---------------------------------------------------------------------------------"); 
		 Application.showMessage("******** Exiting from getWorkOrder Validation ********");
		 Application.showMessage("---------------------------------------------------------------------------------"); 
		
	}

	public void Initialization(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
    {
		
		RTP_ValidationsClass rV=new RTP_ValidationsClass();
     // sL.getBaseTime(input, c);
		XH.XHOM_getBaseTime(input, c);
      String T1=sL.getTimeInstance();
      c.put("T1", T1);
      rV.valuesFromAggregrate(input, c);
      sL.IterationLogic30DayDisconnect(input, c);
    	 
    }
	

	public void cosLogicFlow_CLS(Object input, ScriptingContext c,String ExistingService,String NewService) throws IOException, ClassNotFoundException, InterruptedException, JSONException
	{	
		ChangeOfServiceClass csv = new ChangeOfServiceClass();
 List<String> existingservice=csv.extractServices(ExistingService, input, c);
	     List<String> newservice=csv.extractServices(NewService, input, c);
	     List<String> removeaccountgroup=new ArrayList<String>();
	     List<String> addaccountgroup=new ArrayList<String>();
	     List<String> earlierservice=new ArrayList<String>();
	     
	     if(existingservice.get(0).trim().equalsIgnoreCase(newservice.get(0).trim()))
	     {
	    	 Application.showMessage("Both the Tiers are same...No need of UpdateTierCall Validation");
	     }
	     else
	     {
	    	 Application.showMessage("calls to be Validated is UpdateTierGroup for the Tier"+newservice.get(0));
	    	 //updateTier_Validate_CLS(input,  c,newservice.get(0));
	     }
	     if(existingservice.size()>=newservice.size())
	     {
	    	for(int i=1;i<existingservice.size();i++)
	    	{
	    		Application.showMessage("newservice.size()="+newservice.size());
	    		
	    		if(newservice.size()==1)
	    		{	    			
	    			removeaccountgroup.add(existingservice.get(i));
	    		}
	    		else
	    			{
	    			for(int j=1;j<newservice.size();j++)
	    			
	    		{
	    			if(existingservice.get(i).trim().equalsIgnoreCase(newservice.get(j).trim()))
	    			{
	    				if(removeaccountgroup.contains(existingservice.get(i).trim()))
	    				{
	    					removeaccountgroup.remove(existingservice.get(i).trim());
	    				}
	    				if(addaccountgroup.contains(newservice.get(j).trim()))
	    				{
	    					addaccountgroup.remove(newservice.get(j).trim());
	    				}
	    				earlierservice.add(existingservice.get(i).trim());
	    			}
	    			else
	    			{
	    				if(earlierservice.contains(existingservice.get(i).trim()))
	    				{
	    					addaccountgroup.add(newservice.get(j).trim());
	    				}
	    				else if(earlierservice.contains(newservice.get(j).trim()))
	    				{
	    				removeaccountgroup.add(existingservice.get(i).trim());
	    				}
	    				else
	    				{
	    				addaccountgroup.add(newservice.get(j).trim());
	    				removeaccountgroup.add(existingservice.get(i).trim());
	    				}
	    			}
	    		}
	    			}
	    	}
	     }
	     else
	    	 {for(int k=1;k<newservice.size();k++)
	    	{
	    		if(existingservice.size()==1)
	    		{
	    			addaccountgroup.add(newservice.get(k));	
	    		}
	    		else
	    		{
	    		for(int l=1;l<existingservice.size();l++)
	    		{
	    			if(newservice.get(k).trim().equalsIgnoreCase(existingservice.get(l).trim()))
	    			{
	    				if(addaccountgroup.contains(newservice.get(k).trim()))
	    				{
	    					addaccountgroup.remove(newservice.get(k).trim());
	    				}
	    				if(removeaccountgroup.contains(existingservice.get(l).trim()))
	    				{
	    					removeaccountgroup.remove(existingservice.get(l).trim());
	    				}
	    			
	    			}
	    			else
	    			{
	    				addaccountgroup.add(newservice.get(k).trim());
	    				removeaccountgroup.add(existingservice.get(l).trim());
	    			}
	    		}
	    		}
	    	}
	    	
	    	 }
	     for(int m=0;m<addaccountgroup.size();m++)
	     {
	    	 String addServices=addaccountgroup.get(m).trim();
	    	 Application.showMessage("Calls to be Validated is addAccountGroup for the service"+addaccountgroup.get(m));
	    	 HS.AddGroup_Validate(input,  c,addServices);
	    	
				c.put("removeAccGrpIssues","no");
	     }
	     for(int n=0;n<removeaccountgroup.size();n++)
	     {
	    	 String removeservices=removeaccountgroup.get(n).trim();
	    	 Application.showMessage("Calls to be Validated is removeAccountGroup for the service"+removeaccountgroup.get(n));
	    	 HS.RemoveGroup_Validate_CLS(input,  c,removeservices);
	    	 c.put("addGroupIssues","no");
	    	 }
	     if(addaccountgroup.size()==0 && removeaccountgroup.size()==0)
	     {
	    	 c.put("Status","NEW");
	     }
	     else
	     {
	    	 c.put("Status","CHG");
	     }
	    	 
	    		 
	    	 
	    	 
	     }
	    
//-------------Yamini---------------
	
	public void validateScheduleDisconnect(Object input, ScriptingContext c, String inStatus) throws ClassNotFoundException, IOException, InterruptedException
    {
    	
		Thread.sleep(5000);
    	String Acc=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: XML_AccNumber");
    	Map<String,String> getMap=new HashMap <String,String>();
    	getMap=scheduleDisconnect(input, c, "xhom-qa","XHOM_SCHEDULE_DISCONNECT",Acc);
    	
    	//Validating Status
    	String Status=getMap.get("status");
    	sL.validationPointIgnoreCase(Status, inStatus, input, c);
    	
    	String creationDate=getMap.get("creationDate");
    	Application.showMessage("Schedule Date is ::"+creationDate);
    	String inschDate=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: ScheduledDate");
    	String scheduleDate=getMap.get("scheduleDate");
    	Application.showMessage("Schedule Date is ::"+scheduleDate);
    	//sL.validationPointIgnoreCase(scheduleDate, inschDate, input, c);
    	c.setValue("scheduleDate", scheduleDate);
    	
    	String inDisService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: SERVICEorCURRENTSERVICE");
    	String disconnectService=getMap.get("disconnectService");
    	Application.showMessage("disconnectService is::"+disconnectService);
    	sL.validationPointIgnoreCase(disconnectService, inDisService, input, c);
    	
    	String inService=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE");
    	String installService=getMap.get("installService");
    	Application.showMessage("installService is::"+installService);
    	sL.validationPointIgnoreCase(installService, inService, input, c);
    	
    	
    	
    	    	
    }
		//-----------
	
	public Map<String,String> scheduleDisconnect(Object input, ScriptingContext c,String DBName,String TableName,String AccountNumber)throws ClassNotFoundException, IOException
	{
		  Application.showMessage("------------------------------------------------------------------");
		  Application.showMessage("*##Connecting to SCHEDULE DISCONNECT TABLE... ");       
		  Application.showMessage("------------------------------------------------------------------");
		  
		  DBCursor rs = null;
		  DBObject obj= null;
		  String dStatus = null;
		  String dCREATIONDATE = null;
		  String dSCHEDULEDATE=null;
		  String dDISCONNECTSERVICE =null;
		  String dINSTALLSERVICE=null; 
		   
		  Map<String,String> returnMap=new HashMap <String,String>();
		  
		  try
 		  {
			  
	         
  	          rs = XH.connectingDB(input, c, DBName, TableName,AccountNumber);
  	   
  	          while (rs.hasNext())
	          {
  	        	 obj = rs.next(); 	         
  	
  	        	dStatus=((BasicBSONObject) obj).getString("STATUS");
  	        	dCREATIONDATE=((BasicBSONObject) obj).getString("CREATIONDATE");
  	        	dSCHEDULEDATE=((BasicBSONObject) obj).getString("SCHEDULEDATE");
  	        	dDISCONNECTSERVICE=((BasicBSONObject) obj).getString("DISCONNECTSERVICE");
  	        	dINSTALLSERVICE=((BasicBSONObject) obj).getString("INSTALLSERVICE");
  	        	
  	      	 
  	        	
	          }
  	          Application.showMessage("STATUS is ::"+dStatus);
  	          Application.showMessage("dCREATIONDATE is ::"+dCREATIONDATE);
  	          Application.showMessage("dSCHEDULEDATE is ::"+dSCHEDULEDATE);
  	          Application.showMessage("dDISCONNECTSERVICE is ::"+dDISCONNECTSERVICE);
  	          Application.showMessage("dINSTALLSERVICE is ::"+dINSTALLSERVICE);
  	          //Application.showMessage("dTIMEZONEUTCOFFSET is ::"+dTIMEZONEUTCOFFSET);
  	          
  	          
  	          returnMap.put("status",dStatus.trim());
  	          returnMap.put("creationDate",dCREATIONDATE.trim());
  	          returnMap.put("scheduleDate",dSCHEDULEDATE.trim());
  	          returnMap.put("disconnectService",dDISCONNECTSERVICE.trim());
  	          returnMap.put("installService",dINSTALLSERVICE.trim());
  	          //returnMap.put("timeZone",dTIMEZONEUTCOFFSET.trim());
  	         
  	          
 		  }		
		  
		  
    	 catch (Exception e)
 		 {
 		    System.out.println("Connection Failed! Check output console");
 			e.printStackTrace();
 			
 		 }
		  
		  Application.showMessage("------------------------------------------------------------------");
		  Application.showMessage("*##End of SCHEDULE DISCONNECT TABLE... ");       
		  Application.showMessage("------------------------------------------------------------------");
		  rs.close();
		  return returnMap;
     
	}
	
	//-----------------
	
	public void changingdate(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
	{

		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     Date date=new Date();
	     DBCursor  rs,rs1;
	     MongoClient mongodb;
	     DBCollection coll;
		    DB db = null;
		    DBObject query = null;
		    UpdateResult retrieveData ;	
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     Calendar cal = Calendar.getInstance();
	     Calendar cal1 = Calendar.getInstance();
	     cal.add(Calendar.DATE, -2);    
	     dateFormat.format(cal.getTime());
	     cal1.add(Calendar.DATE, -1);    
	     dateFormat.format(cal1.getTime());
	     String[] existingCreationDate = null;
		Application.showMessage("Date is"+dateFormat.format(cal.getTime()));
		Application.showMessage("Date is"+dateFormat.format(cal1.getTime()));
	     try
	     {
			mongodb =XH.MongoDBConnect(input, c);
			db= mongodb.getDB("XHOMQA1");  
			Set<String> collections = db.getCollectionNames();
			coll = db.getCollection("XHOM_SCHEDULE_DISCONNECT"); 
			
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("ACCOUNTNUMBER", c.getValue("ACC_input"));
			DBCursor qu = coll.find(searchQuery).limit(1);
				
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set", new BasicDBObject().append("CREATIONDATE", dateFormat.format(cal.getTime()).concat("T00:00:00.000Z")).append("SCHEDULEDATE", dateFormat.format(cal1.getTime()).concat("T00:00:00.000Z")));

			
			Application.showMessage("searchquery is"+searchQuery);
			Application.showMessage("updatequery is"+newDocument);
			coll.update(searchQuery, newDocument);
			
			mongodb.close();
	     }
	     catch(Exception e)
	     {
	    	 Application.showMessage("Exception occurred" +e);
	     }
	}

	//------------------
	
	public int updateScheduleDisConnect(Object input, ScriptingContext c,String AccountNumber) throws ClassNotFoundException, SQLException, IOException, InterruptedException
    {
    	 
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     int nRs1 = 0;
	     Date date=new Date();
	     DBCursor  rs,rs1;
	     MongoClient mongodb;
	     DBCollection coll;
	     List<String> addIssues=new ArrayList();
		    String Issues="Issues from UpdateScheduleDisconnect:::";
		    DB db = null;
		    DBObject query = null;
		    UpdateResult retrieveData ;	
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	     Calendar cal = Calendar.getInstance();
	     Calendar cal1 = Calendar.getInstance();
	     cal.add(Calendar.DATE, -2);    
	     dateFormat.format(cal.getTime());
	     cal1.add(Calendar.DATE, -1);    
	     dateFormat.format(cal1.getTime());
	     String[] existingCreationDate = null;
		Application.showMessage("Date is"+dateFormat.format(cal.getTime()));
		Application.showMessage("Date is"+dateFormat.format(cal1.getTime()));
	    
			mongodb =XH.MongoDBConnect(input, c);
			db= mongodb.getDB("xhom-qa");  
			Set<String> collections = db.getCollectionNames();
			coll = db.getCollection("XHOM_SCHEDULE_DISCONNECT"); 
			
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("ACCOUNTNUMBER", AccountNumber);
			DBCursor qu = coll.find(searchQuery).limit(1);
				
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.append("$set", new BasicDBObject().append("CREATIONDATE", dateFormat.format(cal.getTime()).concat("T00:00:00.000Z")).append("SCHEDULEDATE", dateFormat.format(cal1.getTime()).concat("T00:00:00.000Z")));

			
			Application.showMessage("searchquery is"+searchQuery);
			Application.showMessage("updatequery is"+newDocument);
			WriteResult Writeresult = coll.update(searchQuery, newDocument);
			nRs1=Writeresult.getN();
			Application.showMessage("REsult set::" +nRs1);
    	  if(nRs1==0)
    	  {
    		  Application.showMessage("NO Record got updated..!!!");
    		  addIssues.add("NO Record got updated..!!!");
    
    	  }
    	  else
    	  {
    		  Application.showMessage("Number of records updated is ::"+nRs1);
    	  }
    	  
    	  Application.showMessage("------------------------------------------------------------------");
    	  Application.showMessage("End of Schedule Disconnet table updation");
    	  Application.showMessage("------------------------------------------------------------------");
    	  
    	  return nRs1;
    }
	
	//-------------------
	
	public void cvr_ReleasefromScheduleDisconnectableValidate(Object input, ScriptingContext c) throws ClassNotFoundException, NullPointerException, InstantiationException, IllegalAccessException, IOException, InterruptedException, SQLException, SAXException, XPathException, Exception
	{
		XHOM_ScheduleUpgradeDowngradeExecutionClass SUDE = new XHOM_ScheduleUpgradeDowngradeExecutionClass();
		
		DIV.queryLocation_Validate(input,c);
		   Thread.sleep(3000);
		   DIV.processHomeSecurityInfo_Validate(input, c);
		   Thread.sleep(3000);
		   HS.SuspendAccount_Validate(input, c); 
	 	Thread.sleep(3000);
		   
	 	HS.deactivateAccount_Validate_CLS(input, c);
		   Thread.sleep(3000);
		if(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: UpOrDown").equalsIgnoreCase("U"))
		{
			 
		   	   
			DIV.processHomeSecurityInfo_Validate(input, c);
		   	   Thread.sleep(3000);
		   	   
		   	   //sc.createAccount_ValidateScheduleDemiSim_CLS(input, c);
		   	HS.CLS_createAccount_Validate(input, c);
		   	   Thread.sleep(3000);
		   	DIV.SetAccountBasicInformation_Validate(input, c);
		   	 Thread.sleep(3000);
		   	DIV.responderInfo_Validate(input, c);
		   	 Thread.sleep(3000);
		   	DIV.SetAccountAuthorityInformation_Validate(input, c);
		   	 Thread.sleep(3000);
		   	SUDE.ScheduleorderUpdate_Validate(input, c);
		   	    Thread.sleep(3000);
		   	   
		}
		else
		{
			
	   	  
		//	DIS.DisconnectAccount_CANCEL_Validate(input, c);
	   	   Thread.sleep(3000);
	   	   DIV.processHomeSecurityInfo_Validate(input, c);
	   	   Thread.sleep(3000);
	   	  // sc.createAccount_ValidateScheduleInsight_CLS(input, c);
	   	HS.CLS_createAccount_Validate(input, c);
	   
		   Thread.sleep(3000);
		   SUDE.ScheduleorderUpdate_Validate(input, c);
	  	    Thread.sleep(3000);
	   	   
	       
		}
	}
	public void check(Object input) throws ClassNotFoundException, IOException, InterruptedException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		
		
char[] charArray = {'e', 'x', 'c', ',','e', 'x', 'c', 'e','p','t','i','o','n' };

Application.showMessage("Finding1"+input);
		String text = new String(charArray);
		
		Application .showMessage(text);
		//webtool.scripting.MethodToolContext@4966db3e
	  
		// Ignore_initialization(input,c);
		
	
		
	}
	
	
	
	
	
	
	
	//------------------
	 public void Ignore_initialization(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
	 {
		 
		
		 
		// Application.showMessage("Input"+input);
		// Application.showMessage("ScriptingContext"+c);
		// ScriptingContext c=new com.parasoft.api.ScriptingContext();
		 Application.showMessage("Yamini");
		 System.out.println("Check");
		 XH.MongoDBConnect1(input);
		 CancelledChangeDefect cc=new CancelledChangeDefect();
		 sL.printStart(input,c, "initialization Starts..!");
		XH.XHOM_getBaseTime(input, c);
		cc.execution(input, c);
		sL.printEnd(input, c, "initialization Ends..!");
	 }
	
	
	//------------
	public void NoDataSetFound(Object input, ScriptingContext c)throws InterruptedException, ClassNotFoundException, IOException, SQLException 
    {
		XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();
		int count=0,v1=0,i=0;
	    int waitingtime=0;
	    int thinktime=0;	
	    DBObject obj = null;
		DBCursor retrieveData ;		
	    MongoClient mongodb;
	    DBCollection coll;
	    DB db = null;
	    DBObject query = null;	   
	   
	    String Time= c.get("BaseTime").toString();
	    List<String> addIssues=new ArrayList();
	    String Issues="Issues from addAccount:::";
	    
	   
	    
		mongodb =lC.MongoDBConnect(input, c);				 
		db= mongodb.getDB("xhom-qa");  				 
		Set<String> collections = db.getCollectionNames(); 				
		coll = db.getCollection("XHOM_MESSAGELOGGING");				
		BasicDBObject whereQuery = new BasicDBObject(); 	
		query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(c.getValue("ACC_input")).get(),new QueryBuilder().start().put("RECEIVETIME").greaterThanEquals(c.get("BaseTime")).get()).get();
		    	
		        
		    BasicDBObject fields = new BasicDBObject();		     
		    fields.put("SENDTIME",1);
		    retrieveData = coll.find(query).sort(fields).limit(20);     
		 
		   
		    if(retrieveData.count()==0)
		    {
		    	Application.showMessage("No Record Sets Found..!");
					retrieveData.close();
					
		    }
		    else
		     {
		    	
			while(retrieveData.hasNext())
			{
				 obj=retrieveData.next();
			      //String operation=rs.getString("OPERATION");
				String operation = ((BasicBSONObject) obj).getString("OPERATION");
			      Application.showMessage("OPeration is ::"+operation);
			      
			    			      
			       if(operation.equals("cos:commonOrderService/setOrderStatus"))
			       {
			           Application.showMessage("Extra call setOrderstatus Found");
			           addIssues.add("setOrderstatus call found..!");
			           v1=1;
			           continue;
			       }
			       else
			       {
			    	   Application.showMessage("Extra call setOrderstatus Found");  
			       }
			      
			      
			     if(v1==1)
			     {
			          addIssues.add("Extra Call Found");
			          for(int ii=0;ii<addIssues.size();ii++)
			        	 {
			        		 Issues=Issues.concat(addIssues.get(ii)).concat(",");
			        	 }
			          c.put("IgnoreWorklistsIssues", Issues.concat("WebService Call :: Ignore worklist scenario is Failed with Validation Errors").concat("||"));
			        
			          break;
			     }
			     else
			     {
			    	//Yamini
			        	 
			    	 Application.showMessage(i+"th row data is not an Extra call ");
				     i++;
				     c.put("IgnoreWorklistsIssues", "no");
			     }
			     
			}
			retrieveData.close();
		}	
    }

	
	
	
}
