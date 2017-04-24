import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.StatementEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.portable.ApplicationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;

import org.bson.BasicBSONObject;
import com.parasoft.api.Application;
import com.parasoft.api.Context;
import com.parasoft.api.IEvent;
import com.parasoft.api.ScriptingContext;

public class XHOM_LibraryRtp  {
	public String basemsgID = null;
	public Connection connection = null;
	public String xml1;
	public String xml2;
	// public String rowmsg= null;
	public String resultval;
	public static String logger = "";

	
	
	public MongoClient MongoDBConnect(Object input,ScriptingContext c)
	{
		
	
		String host =c.getValue("DB_Host"); // c.getValue("DB_Host");"omocapp-dt-2s.ula.comcast.net"
		String password = c.getValue("dB_Password");
		String username = c.getValue("dB_Username");
		String port = c.getValue("dB_Port");
		String Auth_Db = c.getValue("dB_serviceName");
		
		String dbURI = "mongodb://"+username+":"+password+"@"+host+":"+port+"/"+Auth_Db;                      
	    MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
	   
        return  mongoClient;
		        
	}
	//----------------
	public Statement mysqldbConnect(Object input,ScriptingContext c) throws ClassNotFoundException,InstantiationException, IllegalAccessException, SQLException, IOException
	{
	
		String AccountNumber=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: Accounts");
	 Class.forName("com.mysql.jdbc.Driver").newInstance();
	
	   
	   final String DB_URL = "jdbc:mysql://10.255.159.180:3307/my_sql";

	   //  Database credentials
	   final String USER = "mithun";
	   final String PASS = "Tester123";   
	   String sql; //Truncate my_sql.parasoftentry;
	   System.out.println("Connecting to Database");
	   Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
	      
	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      Statement st = connection.createStatement();
	     
            
             
	      System.out.println("---Validating SQL DB---");
             
	      sql = "delete from my_sql.parasoftentry where AccountNumber = '"+AccountNumber+"'";
	     int i= st.executeUpdate(sql);
	     System.out.println("Truncated Parasoft entry table");
      
	      st.close();
	      connection.close();
	   	
		return st;
	}
	//----yamini
	
	public Statement mysqldbConnectcvr4xi(Object input,ScriptingContext c) throws ClassNotFoundException,InstantiationException, IllegalAccessException, SQLException, IOException
	{
	
		String AccountNumber=c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_AccNumber");
	 Class.forName("com.mysql.jdbc.Driver").newInstance();
	
	   
	   final String DB_URL = "jdbc:mysql://10.255.159.180:3307/my_sql";

	   //  Database credentials
	   final String USER = "mithun";
	   final String PASS = "Tester123";   
	   String sql; //Truncate my_sql.parasoftentry;
	   System.out.println("Connecting to Database");
	   Connection connection = DriverManager.getConnection(DB_URL,USER,PASS);
	      
	      //STEP 4: Execute a query
	      System.out.println("Creating statement...");
	      Statement st = connection.createStatement();
	     
            
             
	      System.out.println("---Validating SQL DB---");
	     // "INSERT INTO Customers VALUES (1001, 'Simpson', 'Mr.', 'Springfield', 2001)"
	      //sql = "INSERT INTO my_sql.parasoftentry VALUES (8210100200752001,0,2017-03-28 20:00:01)";
	      sql = " insert into my_sql.parasoftentry (AccountNumber, TrackingNumber,LastUpdated)"
	    	        + " values (?, ?, ?)";

	    
	      PreparedStatement preparedStmt = connection.prepareStatement(sql);
	      preparedStmt.setString (1, AccountNumber);
	      preparedStmt.setInt (2, 0);
	      preparedStmt.setString   (3, "2017-03-28 20:00:01");
	    
	     // int i= st.executeUpdate(sql);
	     
	      preparedStmt.execute();
	      
	      st.close();
	      connection.close();
	   	
		return st;
	}
	
	public MongoClient MongoDBConnect1(Object input)
	{
		
	
		String host ="bomdb-qc-b1s.ula.comcast.net"; // c.getValue("DB_Host");"omocapp-dt-2s.ula.comcast.net"
		String password = "c0mcast!";
		String username = "xhomuser";
		String port = "27017";
		String Auth_Db = "xhom-qa";
		
		String dbURI = "mongodb://"+username+":"+password+"@"+host+":"+port+"/"+Auth_Db;                      
	    MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
	   
        return  mongoClient;
		        
	}
	//----------Mongo Replica set
	
	
	public MongoClient MongoDBConnectReplicaSet(Object input,ScriptingContext c)
	{
		
	
		String host =c.getValue("DB_Host"); // c.getValue("DB_Host");"omocapp-dt-2s.ula.comcast.net"
		String password = c.getValue("dB_Password");
		String username = c.getValue("dB_Username");
		String port = c.getValue("dB_Port");
		String Auth_Db = c.getValue("dB_serviceName");
		
		String host1 =c.getValue("DB_Host1");   
		Application.showMessage("mongodb://"+username+":"+password+"@"+host+":"+port+","+host1+":"+port+","+"/"+Auth_Db);
		String dbURI = "mongodb://"+username+":"+password+"@"+host+":"+port+","+host1+":"+port+","+"/"+Auth_Db;
	    MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
	   
        return  mongoClient;
		        
	}
	
	//-------------------------Common DB connection--------------------------
	
	public MongoClient MongoDBConnect_Common(Object input,ScriptingContext c) throws Exception
			
			{
				
		Application.showMessage(c.getValue("RTPNormalScenariosdS","DbConnections: CommonDB_UserName"));
		String host = c.getValue("RTPNormalScenariosdS","DbConnections: DB_HOST").trim();
		String password = c.getValue("RTPNormalScenariosdS","DbConnections: CommonDB_Password").trim();
		String username = c.getValue("RTPNormalScenariosdS","DbConnections: CommonDB_UserName").trim();
		String port = c.getValue("RTPNormalScenariosdS","DbConnections: DB_Port").trim();
		String sid = c.getValue("RTPNormalScenariosdS","DbConnections: DB_ServiceName").trim();
		
		String dbURI = "mongodb://"+username+":"+password+"@"+host+":"+port+"/"+sid;                      
	    MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
	    
        return  mongoClient;
	
			}
	//Yamini's deleting old records from Mongo DB retreivng values
	
	 public void DBvaluesFromAggregrate(Object input , ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException
	    {
	                try
	                {
	                                
	                 
	                                String XML_EndPoint=c.getValue("RTPDataSourceCollections", "DbConnections: XML_Enpoint");
	                                Application.showMessage("XML Endpoint"+c.getValue("RTPDataSourceCollections", "DbConnections: XML_Enpoint"));
	                                String DB_Host=c.getValue("RTPDataSourceCollections", "DbConnections: DB_HOST");
	                                String DB_Host1=c.getValue("RTPDataSourceCollections", "DbConnections: DB_HOST1");
	                                String dB_Password=c.getValue("RTPDataSourceCollections", "DbConnections: DB_Password");
	                                String dB_Username=c.getValue("RTPDataSourceCollections", "DbConnections: DB_UserName");
	                                String dB_Port=c.getValue("RTPDataSourceCollections", "DbConnections: DB_Port");
	                                String dB_serviceName=c.getValue("RTPDataSourceCollections", "DbConnections: DB_ServiceName");
	                                String XML_AccNumber=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: Accounts");
	                                
	                                c.setValue("ACC_input",XML_AccNumber);
	                              //  c.setValue("ACC_input","8210100200658315");
	                                c.setValue("DB_Host", DB_Host);
	                              //  c.setValue("DB_Host", "bomdb-dt-b1s.ula.comcast.net");
	                                c.setValue("DB_Host1", DB_Host1);
	                              //  c.setValue("DB_Host1", "bomdb-qc-b1s.ula.comcast.net");
	                                c.setValue("dB_Password",dB_Password);
	                               // c.setValue("dB_Password","c0mcast!");
	                                c.setValue("dB_Username",dB_Username);
	                               // c.setValue("dB_Username","xhomuser");
	                                c.setValue("dB_Port",dB_Port);
	                               // c.setValue("dB_Port","27017");
	                                c.setValue("dB_serviceName",dB_serviceName);
	                               // c.setValue("dB_serviceName","xhom-qa");
	                                c.setValue("XML_EndPoint",XML_EndPoint);
	                              //  c.setValue("XML_EndPoint","http://10.252.115.191:8001/cwf/services/RTPTestInterface");
	                                
	                                 Application.showMessage("Endpoint is ::"+XML_EndPoint);
	                                 Application.showMessage("DB_HOST is ::"+DB_Host);
	                                
	                                 Application.showMessage("dB_Password is ::"+dB_Password);
	                                 Application.showMessage("dB_Username is ::"+dB_Username);
	                                 Application.showMessage("dB_Port is ::"+dB_Port);
	                                 Application.showMessage("dB_serviceName is ::"+dB_serviceName);
	                                 Application.showMessage("XML_AccNumber is ::"+XML_AccNumber);
	                                 
	                                
	                                
	                                 
	                                    
	                                    
	                                    
	                                    
	                                    
	                                
	                }
	                catch(Exception e)
	                {
	                                
	                }
	    }
	    
		
	//--Yamini's --Dynamic way of Handling Think Time--
	
	
	
	public DBCursor XHOM_reduceThinkTimeRTP(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();
		int count=0;
	    int waitingtime=0;
	    int thinktime=0;		
		DBCursor retrieveData ;		
	    MongoClient mongodb;
	    DBCollection coll;
	    DB db = null;
	    DBObject query = null;
	   // String Time= c.get("BaseTime").toString();
	  //  XHOM_getBaseTime(input,  c);
	    String Time= c.get("BaseTime").toString();
		 
		 //------------------
		/* String check="2016-09-24 07:31:00";
		 String[] basetime1=check.split("\\s+");		
		 String Time=basetime1[0].concat("T").concat(basetime1[1]).concat(".221Z");
		 org.joda.time.format.DateTimeFormatter srtparser = ISODateTimeFormat.dateTime();
		 DateTime srtresult = srtparser.parseDateTime(Time);  
		 Application.showMessage("srtresult"+srtresult);*/
	     Application.showMessage("Time:::"+c.get("BaseTime"));
	   Application.showMessage("Operation is:::"+c.getValue("OPERATIONVALIDATION"));	     
	   System.out.println("Operation is:::"+c.getValue("OPERATIONVALIDATION"));
	   
	     do
			{
				Thread.sleep(1000);
				mongodb =lC.MongoDBConnect(input, c);
				db= mongodb.getDB("xhom-qa");  //BOMQA1
				Set<String> collections = db.getCollectionNames(); 
	            //Application.showMessage(collections+"\n"); 				 
				coll = db.getCollection("XHOM_MESSAGELOGGING");  //BOM_MESSAGELOGGING
				BasicDBObject whereQuery = new BasicDBObject();  
			//c.put("BaseTime", "Mon Sep 26 15:04:34 IST 2016");
		    if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("xhom:listener-service/BEPQueueListener")))
		    		{	   
		      
		         query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(c.getValue("ACC_input")).get(),new QueryBuilder().start().put("OPERATION").greaterThanEquals(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("RECEIVETIME").greaterThanEquals(c.get("BaseTime")).get()).get();
		    	//query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is("9992062274003").get(),new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get()).get();
			                    
		    		}
		    else if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("com:comOrderService/setOrderStatus")))
		    {
		    	query = QueryBuilder.start().and(new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
		        
		    }    
		    else
		    {
		    	query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(c.getValue("ACC_input")).get(),new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
		    	//query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is("9992062274003").get(),new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get()).get();
			          
		    }   
		  //  Application.showMessage("Querying is"+query);  
		    BasicDBObject fields = new BasicDBObject();		     
		    fields.put("SENDTIME",1);
		    retrieveData = coll.find(query).sort(fields).limit(20);     
		  //  Application.showMessage("QueryingrETRIEVEdATA is"+query);  
		     
		   
		    if(retrieveData.count()==0)
		    {
		    	   waitingtime++;
					count++;					
					//retrieveData=null;
					if(count<=119)
					{
					retrieveData.close();
					}
					else
					{
						retrieveData=null;
						
						
					}
		    }
		    else
		     {
		    	   break;
		     }
		      
			 }while(count < 120);
	     int timing=waitingtime + 1;
			Application.showMessage("Waited for "+timing+"seconds");
			System.out.println("Waited for "+timing+"seconds"+retrieveData);
			if(retrieveData==null)
			{
				mongodb.close();
				
			}
			
			return retrieveData;
	}
	
	
	//---------------Yamini
	
	public Map gettingTimeZone(Object input,ScriptingContext c)
	{
		Map map=new HashMap();
		map.put("Eastern Daylight Time", "US_EASTERN");
		map.put("Atlantic Daylight Time", "US_ATLANTIC");
		map.put("Central Daylight Time", "US_CENTRAL");
		map.put("Mountain Daylight Time", "US_MOUNTAIN");
		map.put("Pacific Daylight Time", "US_PACIFIC");
		map.put("Alaskan Daylight Time", "US_ALASKAN");
		map.put("Eastern Standard Time", "US_EASTERN");
		map.put("Atlantic Standard Time", "US_ATLANTIC");
		map.put("Central Standard Time", "US_CENTRAL");
		map.put("Mountain Standard Time", "US_MOUNTAIN");
		map.put("Pacific Standard Time", "US_PACIFIC");
		map.put("Alaskan Standard Time", "US_ALASKAN");
		map.put("Hawaiian Standard Time", "US_HAWAIIAN");
		return map;
	}
	
	//---------
	
	public List<String> listOfCOPSfields(Object input,ScriptingContext c)
	{
		List<String> setAccountBasicinfo=new ArrayList<String>();
		setAccountBasicinfo.add("accountType");
		setAccountBasicinfo.add("address");
		setAccountBasicinfo.add("city");
		setAccountBasicinfo.add("name");
		setAccountBasicinfo.add("state");
		setAccountBasicinfo.add("timeZone");
		setAccountBasicinfo.add("zipcode");
		setAccountBasicinfo.add("ctvPhone1");
		return setAccountBasicinfo;
	}
	
	//--------------
	
	
	public List<String> listOfAUTHfields(Object input,ScriptingContext c)
	{
		List<String> setAccountAUTHinfo=new ArrayList<String>();
		setAccountAUTHinfo.add("police");
		setAccountAUTHinfo.add("fire");
		setAccountAUTHinfo.add("medical");
		
		return setAccountAUTHinfo;
	}
	
	
	//-----------
	
	public List<String> listOfaddAccfields(Object input,ScriptingContext c)
	{
		List<String> addAcc=new ArrayList<String>();
		addAcc.add("lastName");
		addAcc.add("creator");
		addAcc.add("country");
		addAcc.add("province");
		addAcc.add("city");
		addAcc.add("address1");
		addAcc.add("postalCode");
		addAcc.add("timeZone");
		addAcc.add("accountId");
		addAcc.add("firstName");
		addAcc.add("emailAddress");
		addAcc.add("phoneNumber");
		addAcc.add("status");
		if(c.get("XML_ScenarioName").toString().equalsIgnoreCase("Demi"))
		{
		addAcc.add("centralStationAccountNumber");		
		}
		return addAcc;
	}
	
	//------------
	public Map addAccVal(Object input,ScriptingContext c)
	{
		Map addAccount=new HashMap();
		addAccount.put("lastName",c.getValue("LastName"));
		addAccount.put("creator","XHOM");
		addAccount.put("country","US");
		addAccount.put("province",c.get("State"));
		addAccount.put("city",c.get("Ecity"));
		addAccount.put("address1",c.get("addAddress").toString().trim());
		addAccount.put("postalCode",c.get("Ezipcode"));
		addAccount.put("timeZone", c.get("addTimeZone"));
		addAccount.put("accountId",c.getValue("ACC_input"));
		addAccount.put("firstName",c.getValue("FirstName"));
		addAccount.put("emailAddress",c.getValue("emailAddress"));		
		addAccount.put("phoneNumber",c.get("PhoneNumber"));
		addAccount.put("status","NEW");
		addAccount.put("centralStationAccountNumber", c.getValue("CcentralNum"));
		return addAccount;
	}
	
	//--------------
	
	public Map COPsVal(Object input,ScriptingContext c)
	{
		Map setAccountBasicinfo=new HashMap();
		setAccountBasicinfo.put("accountType","R");
		setAccountBasicinfo.put("address",c.get("Address"));
		setAccountBasicinfo.put("city",c.get("Ecity"));
		setAccountBasicinfo.put("name",c.getValue("FirstName").concat(" ").concat(c.getValue("LastName")));
		setAccountBasicinfo.put("state",c.get("State"));
		setAccountBasicinfo.put("timeZone", c.get("copsTimeZone"));
		setAccountBasicinfo.put("zipcode",c.get("Ezipcode"));
		setAccountBasicinfo.put("ctvPhone1",c.get("PhoneNumber"));
		return setAccountBasicinfo;
	}
	//-----------
	
	public Map AUTHsVal(Object input,ScriptingContext c)
	{
		Map setAccountAUTHinfo=new HashMap();
		setAccountAUTHinfo.put("police",c.getValue("PoliceNumber"));
		setAccountAUTHinfo.put("fire",c.getValue("FireNumber"));
		setAccountAUTHinfo.put("medical",c.getValue("medicalNumber"));
		
		return setAccountAUTHinfo;
	}
	//----------
	
	public int ValidatingFields(Object input,ScriptingContext c,String methodname,String xml) throws JSONException
	{
		
		LibraryRtp lC=new LibraryRtp();
		List<String> methods = null;
		List<String> notEqual = new ArrayList<String>();
		Map methodsValue=null;
		String field=null;
		int status=0;
		String printVal="Issues :";
		int Notsatisfied=0;
		switch(methodname)
		{
		case "copsBasic":
			methods=listOfCOPSfields(input, c);
			methodsValue=COPsVal(input, c);
			break;
			
		case "addAccount":
			methods=listOfaddAccfields(input, c);
			methodsValue=addAccVal(input, c);
			break;
			
		case "copsAuth":
			methods=listOfAUTHfields(input, c);
			methodsValue=AUTHsVal(input, c);
			break;
			
			default:
				break;
			
		}
		// Application.showMessage("Field is"+extractingValueFromJSONObject(xml,"accountId", input, c));
          for(int i=0;i<methods.size();i++)
             {
        	  if(methodname.equalsIgnoreCase("addAccount"))
              {
        		  Application.showMessage("Value of I is"+i);
             	   field=extractingValueFromJSONObject(xml,methods.get(i), input, c);
              }
        	  else
        	  {
        		  field=lC.nodeFromKey(xml,"<"+methods.get(i)+">","</"+methods.get(i)+">").trim();
              	  
        	  }
          	   Application.showMessage(methods.get(i) +" from "+methodname+" request in InXML is"+" "+field);
          	   Application.showMessage(methods.get(i) +" from  request  is "+" "+methodsValue.get(methods.get(i)).toString().trim());
          	   
          	   if(field.equalsIgnoreCase(methodsValue.get(methods.get(i)).toString()))
          	   {
          		   Application.showMessage("*******Validation Point in InXML:: WebServicall********");
                     Application.showMessage("Validation is Successfull with "+methods.get(i)+"::"+" "+field);
                     status=1;
          	   }
          	   else
          	   {
          		 Notsatisfied++;
          		notEqual.add("Validation is not Successfull with "+methods.get(i)+"::"+" "+field);
          		 
          		   
          	   }
             }
         
         if( Notsatisfied!=0)
         {
        	 status=0;
        	 for(int i=0;i<notEqual.size();i++)
        	 {
        		 printVal=printVal.concat(notEqual.get(i).concat(","));
        	 }
        	 c.report(printVal);
         }
		return status;
         
	}
	
	
	//--------------
	
	public DBCursor XHOM_reduceThinkTimeRTPmsgid(Object input,ScriptingContext c,String rowmsg) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
	XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();
	int count=0;
	   int waitingtime=0;
	   int thinktime=0; 
	DBCursor retrieveData ; 
	   MongoClient mongodb;
	   DBCollection coll;
	   DB db = null;
	   DBObject query = null;
	   org.bson.types.ObjectId id= new org.bson.types.ObjectId(rowmsg);  
	
	   String Time= c.get("BaseTime").toString(); 
	  Application.showMessage("Time:::"+c.get("BaseTime"));
	  Application.showMessage("Operation is:::"+c.getValue("OPERATIONVALIDATION"));     
	  System.out.println("Operation is:::"+c.getValue("OPERATIONVALIDATION"));
	  
	    do
	{
	    Thread.sleep(1000);
	mongodb =lC.MongoDBConnect(input, c);
	db= mongodb.getDB("xhom-qa");  
	Set<String> collections = db.getCollectionNames();              
	coll = db.getCollection("XHOM_MESSAGELOGGING");  
	BasicDBObject whereQuery = new BasicDBObject();  
	  if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("xhom:listener-service/BEPQueueListener")))
	    {
	      query = QueryBuilder.start().and(new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("_id").notEquals(rowmsg).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
	    
	    }
	   else if((c.getValue("OPERATIONVALIDATION").equalsIgnoreCase("cmb:commonOrderService/orderUpdate")))
	   {
	    query = QueryBuilder.start().and(new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("_id").notEquals(new QueryBuilder().start().put("ObjectId").is(c.get("rowmsg")).get()).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
	    }
	   else
	   {
	    //query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(c.getValue("ACC_input")).get(),new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("_id").notEquals(new QueryBuilder().start().put("ObjectId").is(c.get("rowmsg")).get()).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
	    query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(c.getValue("ACC_input")).get(),new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("_id").notEquals(id).get(),new QueryBuilder().start().put("SENDTIME").greaterThanEquals(c.get("BaseTime")).get()).get();
	    //query = QueryBuilder.start().and(new QueryBuilder().start().put("OPERATION").is(c.getValue("OPERATIONVALIDATION")).get(),new QueryBuilder().start().put("_id").notEquals(id).get()).get();
		//   
	        }
	 
	     

	  Application.showMessage("Querying is"+query);  
	   BasicDBObject fields = new BasicDBObject();     
	   fields.put("SENDTIME",1);
	   retrieveData = coll.find(query).sort(fields).limit(20);     
	   Application.showMessage("QueryingrETRIEVEdATA is"+retrieveData.count());  
	    
	  
	   if(retrieveData.count()==0)
	   {
	      waitingtime++;
	count++; 
	retrieveData=null;
	   }
	   else
	    {
	      break;
	    }
	     
	}while(count < 5);
	    int timing=waitingtime + 1;
	Application.showMessage("Waited for "+timing+"seconds"+retrieveData);
	System.out.println("Waited for "+timing+"seconds"+retrieveData);
	return retrieveData;
	}
	
	

	//---------------
	
	
	public void deleteAllCollectionsInMongoDB(Object input,ScriptingContext c,String collectionName) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
	XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();
	int count=0;
	   int waitingtime=0;
	   int thinktime=0; 
	 WriteResult retrieveData ; 
	   MongoClient mongodb;
	   DBCollection coll;
	   DB db = null;
	   DBObject query = null;  
	
	   
	  mongodb =lC.MongoDBConnectReplicaSet(input, c);
	  db= mongodb.getDB("xhom-qa"); 
	  coll = db.getCollection(collectionName); 
	  query = QueryBuilder.start().and(new QueryBuilder().start().put("ACCOUNTNUMBER").is(c.getValue("ACC_input")).get()).get();
	  Application.showMessage("Querying is"+query);  
	   BasicDBObject fields = new BasicDBObject(); 
	   retrieveData = coll.remove(query);  
	   mongodb.close();
	  
	   
	
	   
	}
	
	//----------------
	
	
	public void deleteCollectionsInMongoDB(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
	XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();
	
	lC.deleteAllCollectionsInMongoDB(input, c, "XHOM_MESSAGELOGGING");
	
	   
	}
	
	public void deleteTHREEdAYScvrCollectionsInMongoDB(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
	XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();
	
	lC.deleteAllCollectionsInMongoDB(input, c, "XHOM_THREE_DAYS_CVR_DISCONNECT");
	
	   
	}
	public void deleteScheduleTableCollectionsInMongoDB(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
	XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();
	
	lC.deleteAllCollectionsInMongoDB(input, c, "XHOM_SCHEDULE_DISCONNECT");
	
	   
	}
	
	public void deleteTHIRTYdAYSCollectionsInMongoDB(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
	XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();
	
	lC.deleteAllCollectionsInMongoDB(input, c, "XHOM_THIRTY_DAYS_DISCONNECT");
	
	   
	}
	
	public void deletescheduleDisconnCollectionsInMongoDB(Object input,ScriptingContext c) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
	XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();
	
	lC.deleteAllCollectionsInMongoDB(input, c, "XHOM_SCHEDULE_DISCONNECT");
	
	   
	}
	
	
	
	
	//----------
	
	private Object ObjectId(String rowmsg) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//-------------
	//--Yaminis code-Dont delete this. this for taking reports commonly
	
	 public void generateReport(Object input , ScriptingContext c,List<String> issues,String reportName ) throws Exception
	    {
	    	List<String> issuesReport=issues ;
			List<String> ConsoleissuesReport=new ArrayList<String>();
			String reports="||";
			String Overallreports="||";   	
			int k=1;
			
	    	for(int i=0;i<issuesReport.size();i++)
			{
	    		Application.showMessage("Issues reports"+issuesReport.get(i).toString());
				if(!(issuesReport.get(i).toString()).equalsIgnoreCase("no"))
				{
					ConsoleissuesReport.add(issuesReport.get(i).toString());
				}
			}
			for(int j=0;j<ConsoleissuesReport.size();j++)
			{
				
				Application.showMessage("ConsoleissuesReport"+ConsoleissuesReport.get(j).toString());
	//Overallreports=reports.concat(ConsoleissuesReport.get(j).toString()).concat("::");
				Overallreports=Overallreports.concat(k+")"+" "+ConsoleissuesReport.get(j).toString().concat("\r\n"));
				k++;
				
			}
			if(ConsoleissuesReport.size()!=0)
			{
			c.report(reportName+" "+c.getValue("ACC_input")+"\r\n"+Overallreports);
			}      
		 
	    }
	
	 //----------------
	
	public DBCursor connectingDB(Object input,ScriptingContext c,String DBName,String TableName,String Account) throws InterruptedException, ClassNotFoundException, SQLException, IOException
	{
		XHOM_LibraryRtp  lC= new XHOM_LibraryRtp ();				
		DBCursor retrieveData ;		
	    MongoClient mongodb;
	    DBCollection coll;
	    DB db = null;
	    DBObject query = null;
	    Thread.sleep(1000);
		mongodb =lC.MongoDBConnectReplicaSet(input, c);
		db= mongodb.getDB(DBName);  
		Set<String> collections = db.getCollectionNames(); 	            				 
		coll = db.getCollection(TableName); 
		BasicDBObject whereQuery = new BasicDBObject();		
		query = new QueryBuilder().start().put("ACCOUNTNUMBER").is(Account).get();	
		BasicDBObject fields = new BasicDBObject();		     
	    fields.put("SENDTIME",-1);
	    retrieveData = coll.find(query).sort(fields).limit(20);  
    	return retrieveData; 
		 
	     
				
	}
	//-------Yamini
	
	public String getTrackingDetails(Object input, ScriptingContext c,
			String senddata) throws ParserConfigurationException, SAXException, IOException {
		int count = 0;
		Node elemNode = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	       DocumentBuilder builder = factory.newDocumentBuilder();
	       InputSource is = new InputSource(new StringReader(senddata));
	       Document doc = builder.parse(is);
	       Application.showMessage("Root element :" + doc.getDocumentElement().getNodeName());
	       if (doc.hasChildNodes()) {

				NodeList nodeList = doc.getElementsByTagName("Location");
				for (int i = 0; i < nodeList.getLength(); i++) {

					 elemNode = nodeList.item(i);

					if (elemNode.getNodeName() == "Location") {		

						Application.showMessage("\nNode Name =" + elemNode.getNodeName());					

						Application.showMessage("Node Content =" + elemNode.getTextContent());
						if (elemNode.getTextContent().contains("OT0PD")&& elemNode.getTextContent().contains("Xi3_special")) {
							count++;
						}
						else
						{
							c.report("Special Rate Code not Matched");
						}
						Application.showMessage("countValue:::" + count);
					}


			}
		
		
	}
	       return elemNode.getAttributes().toString();     
}
	
	//------------End of Mongo Dynamic think time..............
	
	public void checking(Object input,ScriptingContext c) throws JSONException, Exception
	{
	String sCurrentLine;
	String inputJson=null;
	BufferedReader br = new BufferedReader(new FileReader("C:\\json.txt"));
	while ((sCurrentLine = br.readLine()) != null) {
	Application.showMessage("File is"+sCurrentLine);
	inputJson = sCurrentLine;
	}
	//JSONParseResponse(inputJson,input, c);
	String extarctingValue=extractingValueFromJSONObject(inputJson,"status",input, c);
	String extarctingArray=extractingValueFromJSONArray(inputJson,"emergencyContact",input, c);
	String ext1=extarctingArray.replace("[", "");
	String ext2=ext1.replace("]", "");
	String extractnew=extractingValueFromJSONObject(ext2,"number",input, c);
	Application.showMessage("ExtarctingValue is....."+extractnew);
	}
	
	//------------------Trying for JSON Validations---------------
	
	public List<String> splittingJSONObject(String inputJson,Object input,ScriptingContext c) throws JSONException
	{
	List<String> jsonresponses=new ArrayList<String>();
	List<String> jsonobjnames=new ArrayList<String>();
	List<String> jsonarrays=new ArrayList<String>();
	jsonresponses.add(inputJson);
	 
	for(int i=0;i<jsonresponses.size();i++)
	{ 
	JSONObject jsonResponse = new JSONObject(jsonresponses.get(i));
	Iterator<?> keys1 = jsonResponse.keys();
	while( keys1.hasNext() )
	{
	String key = (String)keys1.next();
	if ( jsonResponse.get(key) instanceof JSONObject )
	{
	    jsonobjnames.add(key);              
	               jsonresponses.add(jsonResponse.getJSONObject(key).toString());                 
	}
	else if ( jsonResponse.get(key) instanceof JSONArray )     
	{
	jsonobjnames.add(key);  
	jsonarrays.add(key+":&"+jsonResponse.getJSONArray(key).toString());
	                
	 }
	} 
	 }
	 
	return jsonresponses;
	}
	//---------------------
	public List<String> splittingJSONArray(String inputJson,Object input,ScriptingContext c) throws JSONException
	{
	List<String> jsonresponses=new ArrayList<String>();
	List<String> jsonobjnames=new ArrayList<String>();
	List<String> jsonarrays=new ArrayList<String>();
	jsonresponses.add(inputJson);
	 
	for(int i=0;i<jsonresponses.size();i++)
	{ 
	JSONObject jsonResponse = new JSONObject(jsonresponses.get(i));
	Iterator<?> keys1 = jsonResponse.keys();
	while( keys1.hasNext() )
	{
	String key = (String)keys1.next();
	if ( jsonResponse.get(key) instanceof JSONObject )
	{
	    jsonobjnames.add(key);              
	               jsonresponses.add(jsonResponse.getJSONObject(key).toString());                 
	}
	else if ( jsonResponse.get(key) instanceof JSONArray )     
	{
	jsonobjnames.add(key);  
	jsonarrays.add(key+":&"+jsonResponse.getJSONArray(key).toString());
	                
	 }
	} 
	 }
	 
	return jsonarrays;
	}
	//-----------------------------
	public String extractingValueFromJSONObject(String inputJson,String fieldName, Object input,ScriptingContext c) throws JSONException
	{
	List<String> extractjsonresponses=splittingJSONObject( inputJson, input, c);
	
	String fieldValue="null";
	Integer fieldValue2;
	long fieldValue1;
	for(int i=0;i<extractjsonresponses.size();i++)
	{
	 
	JSONObject js = new JSONObject(extractjsonresponses.get(i));
	try
	{
	fieldValue=js.getString(fieldName);
	}
	catch(Exception e)
	{
		try
		{
		fieldValue1=(Long)js.get(fieldName);
		fieldValue=Long.toString(fieldValue1);
		}
		catch(Exception e1)
		{
			try
			{
			fieldValue2=(Integer)js.get(fieldName);
			Application.showMessage("fieldValue2"+fieldValue2);
			fieldValue=Integer.toString(fieldValue2);
			Application.showMessage("fieldValue---->"+fieldValue);
			}
			catch(Exception e2)
			{
			}
		}
	}
	}
	return fieldValue;
	}
	
	public String extractingValueFromJSONArray(String inputJson,String fieldName, Object input,ScriptingContext c) throws JSONException
	{
	List<String> extractjsonArrays=splittingJSONArray(inputJson, input, c);
	Map <String,String> map=new HashMap<String,String>();
	String fieldValue=null;
	for(int i=0;i<extractjsonArrays.size();i++)
	{
	String array=extractjsonArrays.get(i);
	String[] parts = array.split(":&");
	String part1 = parts[0]; // 004
	String part2 = parts[1];
	map.put(part1, part2);
	 
	}
	fieldValue=(String) map.get(fieldName);
	return fieldValue;
	}
	
		       

	public String getAccount_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, NullPointerException, SAXException 
	{
	                Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	     DBCursor  rs;
	                int callFound=0,v1=0;
	                String xmldata1 ="receive_data";
	                int i=0;
	     String xmldata2 ="SEND_DATA";
	    String rscallpresent="true";
	    String rowmsg = null;
	    DBObject obj = null;
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****getAccount_Validate _Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("xhsevent"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("xhsevent")); 
	                try
	                {
	                	rs=XHOM_reduceThinkTimeRTP(input, c);
	                	if(rs == null)
	                	{
	                		rscallpresent = "false";
	                		
	                	
	                		
	                	}
	                	else
	                	{
	                		
	                		  
	                		
	         while (rs.hasNext() /*&& ((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid")*/)
	         {
	        	 
	        	 obj=rs.next();
         		if(((BasicBSONObject) obj).getString("_id")!= c.getValue("BaseMsgid"))
         		{
       		  Application.showMessage("Row Message check one first"+ obj);
	        	 rowmsg=((BasicBSONObject) obj).getString("_id");
	        	 Application.showMessage("Row Message"+ rowmsg);
	        	 if(((BasicBSONObject) obj).getString("RESPONSE")==null)  //RESPONSE
		            {
		            
		                Application.showMessage("Your Recieve XML is NULL \n");
		                
		                String senddata =((BasicBSONObject) obj).getString("REQUEST");//REQUEST
		                Application.showMessage("Your Recieve XML is::\n"+senddata);
		            }
		            else if(((BasicBSONObject) obj).getString("REQUEST")==null)
		            {
		                Application.showMessage("Your SEND XML is NULL \n");             
		                String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		                Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		            }
		            else
	        	 {
		            	String senddata=((BasicBSONObject) obj).getString("REQUEST"); 
		            	String recievedata=((BasicBSONObject) obj).getString("RESPONSE");
		            	Application.showMessage("Your Recieve XML is::\n"+senddata);
		            	Application.showMessage("RECIEVE XML is ::\n"+recievedata); 
		            	
						if( i != 2)
		            	{
							i++;
							//obj=rs.next();
		            		continue;
		            	}
						else
						{
							break;
						}
	        	 }
         		}
	         }
	                	}
	        
	       
	}
	                catch (Exception e)
	                {
	                    System.out.println("Connection Failed! Check output console");
	                                e.printStackTrace();
	                                
	                }
					return rscallpresent;
	}
	                	
	                	
	//------------------
			
			
		 public void XHOM_baseMsgid_retrieval(Object input, ScriptingContext c)
			throws ClassNotFoundException, IOException, InterruptedException {

		 DBCollection coll;
		 DB db = null;
		XHOM_getBaseTime(input, c);
		Thread.sleep(1000);
		MongoClient mg=MongoDBConnect(input, c);
		
		db=mg.getDB("xhom-qa");
		
		coll=db.getCollection("XHOM_MESSAGELOGGING");
	
		
		BasicDBObject field = new BasicDBObject();
		BasicDBObject query = new BasicDBObject();		
		field.put("SENDTIME", -1);
		
		DBCursor cursor = db.getCollection("XHOM_MESSAGELOGGING").find(query).sort(field).limit(1);
	
		
		while (cursor.hasNext()) {
			
		    BasicDBObject obj = (BasicDBObject) cursor.next();		   
		   c.setValue("BaseMsgid", obj.getString("_id"));
			Application.showMessage("Base Message ID is ::"
					+ c.getValue("BaseMsgid"));
		}
	

	}

	
//--------------
		 
		 public HashMap findingoperations(Object input,ScriptingContext c)
		    {
		    	HashMap map = new HashMap();
		    	
		    	
		    	 map.put("ClSgetAccount","hs:homeStream/getAccount");
		    	 map.put("getAccountDDS","srvDDS:DirectoryDataService/getAccount");
		    	 map.put("ClSgetAccountSim","hs:homeStream/getAccount");
		    	 map.put("ClScreateAccount","hs:homeStream/addAccount");
		    	 map.put("removeAccount","hs:homeStream/removeAccount");
		    	 map.put("updateAccount","hs:homeStream/updateAccount");
		    	 map.put("BNERcall","invokeRestService");
		    	 map.put("CLS_DDS","invokeRestService");
		    	 map.put("createCMSAccountID", "hss:HomeSecurityService/createCMSAccountID");
		    	 map.put("getCustomerPermitRequirements","hss:HomeSecurityService/getCustomerPermitRequirements");
		    	 map.put("processHomeSecurityInfo", "srvDDS:DirectoryDataService/processHomeSecurityInfo");
		    	 map.put("xhsevent", "xhom:listener-service/BEPQueueListener");
		    	 map.put("setOrderStatus", "cos:commonOrderService/setOrderStatus");
		    	 map.put("getEntitlement", "BENR:RestClient/getEntitlement");
		    	 map.put("updateBenr", "BENR:RestClient/updateBenr");
		    	 map.put("updateBenrNotifications", "BENR:RestClient/notifications");
		    	 
		    	 	//--COS CLS calls
		    	 map.put("CLS_addAccountGroup", "hs:homeStream/addAccountGroup");
		    	 map.put("CLS_removeAccountGroup", "hs:homeStream/removeAccountGroup");
		    	 map.put("CLS_updateAccountTierGroup", "hs:homeStream/updateAccountTierGroup");
		    	 
		    	//--suspend CLS  calls
		    	 map.put("CLS_suspendAccount", "hs:homeStream/suspendAccount");
		    	//JasperServices:Comcast/EditTerminal
		    	 map.put("EditTerminal", "JasperServices:Comcast/EditTerminal");
		    	 
		    	 // --restore CLS calls
		    	 map.put("CLS_restoreAccount", "hs:homeStream/restoreAccount");
		    	//nUMEREX
		    	 
		    	 map.put("deactivateUnit", "Numerex:NMRXCCSoap/deactivateUnit");
		    	 
		    	 //jASPER
		    	 
		    	 map.put("GetTerminalDetails","JasperServices:Comcast/GetTerminalDetails");
		    	//--cancel CLS calls
		    	 map.put("removeAccount", "hs:homeStream/removeAccount");
		    	 map.put("deactivateAccount", "hs:homeStream/deactivateAccount");
		    	 map.put("deactivateAccountSim", "hs:homeStream/deactivateAccount");
					
		    	 
		    	 return map;
		    }
		    
	
	
	
	//------------------
	public String XHOM_getBaseTime(Object input, ScriptingContext c) {
		String Time;
		
		Date todayDate = new Date();
		
		TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
		
		DateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		
		todayDateFormat.setTimeZone(timeZone);
		
		Time = todayDateFormat.format(todayDate);
		
		
		c.put("BaseTime", todayDate);
		
		Application.showMessage("The Base Time retrieved is ::"
				+ c.get("BaseTime"));
	//	Application.showMessage("Yamini8");
		c.put("T1", Time);
		return Time;
	}
	
	//----------------
	
	public String ThirtyDay_getBaseTime(Object input, ScriptingContext c) throws InterruptedException {
		String Time;

		Date todayDate = new Date();
		TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
		DateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		todayDateFormat.setTimeZone(timeZone);
		Time = todayDateFormat.format(todayDate);
		c.put("ThirtyTime", todayDate);
		Application.showMessage("The Base Time retrieved is ::"
				+ c.get("ThirtyTime"));
		//c.put("T1", Time);
		Thread.sleep(50000);
		return Time;
	}
	
	//-----------
	
	public String extractAccFromURL(String URL, Object input, ScriptingContext c)
	{
		String AccNo = null;
		Pattern p = Pattern.compile("(\\d+)");
   	 Matcher m = p.matcher(URL);
   	 
   	while(m.find()) {
   		if(m.group().length() >=13)
   		{
   			AccNo=m.group();
   		}
   	 }
		return AccNo;
		
	}
	//---------------

	public String getTimeInstance() {
		String Time;
		Date todayDate = new Date();
		TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
		DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY HH:mm:ss");
		todayDateFormat.setTimeZone(timeZone);
		Time = todayDateFormat.format(todayDate);
		return Time;
	}

	
	

}
