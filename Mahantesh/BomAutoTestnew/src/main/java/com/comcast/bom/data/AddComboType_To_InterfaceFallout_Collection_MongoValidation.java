package com.comcast.bom.data;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import com.mongodb.ServerAddress;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.*;
import com.mongodb.MongoCredential;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.bson.BasicBSONObject;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
//import com.cognizant.framework.Status;
import com.comcast.reporting.*;

import com.comcast.utils.ComcastTest;
import com.comcast.utils.SeleniumReport;
import com.comcast.utils.SoapRequest;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.DBCursor;

public class AddComboType_To_InterfaceFallout_Collection_MongoValidation extends ComcastTest {

	public CustomerDetails customerDetails;
	public SeleniumReport report;
	public WebDriver driver;
	public SoapRequest SR;
	DBCursor cursor1;
	DBCursor cursor,cursor2;
	public WebDriver fail;
	   DBObject dbObject,dbObject1;
	   Object SWAPTYPE,ERRTEXT,ERRSOURCE,ORDERSTATUS,STATUS;
	   boolean ISELIGIBLE;
	String Error,Error1;
	//DeviceDetails deviceDetails=DeviceDetails.loadFromDatatable(dataTable);
	
	
	public AddComboType_To_InterfaceFallout_Collection_MongoValidation(SeleniumReport report)
	{
		
		this.report=report;
		
	}


	public void COMBO_BOM_INTERFACEFALLOUT_Validation() throws Exception{
		// TODO Auto-generated method stub
		customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		String Errcode=null;
		 String Errtxt=null;
		 String Ordertype=null;
		 String combotype=null;
		 String dbURI="mongodb://bomintuser:bomintuser@bomdb-dt-b1s.ula.comcast.net:27017/?authSource=bulkom-int";	
		 MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		 
		 // Now connect to your databases
	       DB db = mongoClient.getDB("bulkom-int");
	       System.out.println("Connected to database successfully");
	       System.out.println(db.getCollectionNames());
	       Thread.sleep(10000);
	       
	       DBCollection collection=db.getCollection("BOM_INTERFACE_FALLOUT");
	       BasicDBObject whereQuery1 = new BasicDBObject();
	       whereQuery1.put("ACCOUNTNUMBER", customerDetails.accountNumber);
	       BasicDBObject fields = new BasicDBObject();
	       fields.put("ERRORCODE", 1);
	       fields.put("ERRORTEXT", 1);
	       fields.put("ORDERTYPE", 1);
	     //  fields.put("COMBOTYPES", 1);
	       DBCursor cursor = collection.find(whereQuery1, fields);
	      if (cursor.hasNext()) {
	           DBObject obj=cursor.next();
	      Errcode=((BasicBSONObject) obj).getString("ERRORCODE");
	      Errtxt=((BasicBSONObject) obj).getString("ERRORTEXT");
	      Ordertype=((BasicBSONObject) obj).getString("ORDERTYPE");
	   //   combotype=((BasicBSONObject) obj).getString("COMBOTYPES");
	      System.out.println(Errcode);
	      System.out.println(Errtxt);
	      System.out.println(Ordertype);
	   //   report.updateTestLog("ErrorCode in BOM_INTERFACEFALLOUT collection:", "+Errcode+", Status.PASS); 
	    //  report.updateTestLog("ErrorCode in BOM_INTERFACEFALLOUT collection:", "+Errcode+", Status.PASS); 
	      }
	     if(Ordertype.equals("COMBO"))
	     {
	    	 System.out.println("entered");
	    	 DBCollection collection1=db.getCollection("BOM_INTERFACE_FALLOUT");
		       BasicDBObject whereQuery2 = new BasicDBObject(); 
		       whereQuery2.put("ACCOUNTNUMBER", customerDetails.accountNumber);
		       BasicDBObject fields1 = new BasicDBObject();
		       fields.put("COMBOTYPES", 1);
		       DBCursor cursor1 = collection1.find(whereQuery2, fields);
		       if (cursor1.hasNext()) {
		           DBObject obj1=cursor1.next();
		           combotype=((BasicBSONObject) obj1).getString("COMBOTYPES");
		           System.out.println(combotype);
		           if(combotype.equals("X1,CM/EMTA") || combotype.equals("EOL,CM/EMTA"))
		           {
		        	   report.updateTestLog("ErrorCode in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", Errcode, Status.PASS);
			    	   report.updateTestLog("ErrorText in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", Errtxt, Status.PASS);
			    	   report.updateTestLog("OrderType in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", Ordertype, Status.PASS);
			    	   report.updateTestLog("ComboType in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", combotype, Status.PASS);
		        	   System.out.println("pass");
		           }
		           else
		           {
		        	   report.updateTestLog("ErrorCode in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", Errcode, Status.PASS);
			    	   report.updateTestLog("ErrorText in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", Errtxt, Status.PASS);
			    	   report.updateTestLog("OrderType in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", Ordertype, Status.PASS);
			    	   report.updateTestLog("ComboType in BOM_INTERFACEFALLOUT collection","does not exist", Status.FAIL);
		        	   System.out.println("fail");
		           }
	     }
	     }
		       else
		       {
		    	   report.updateTestLog("ErrorCode in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", Errcode, Status.PASS);
		    	   report.updateTestLog("ErrorText in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", Errtxt, Status.PASS);
		    	   report.updateTestLog("OrderType in BOM_INTERFACEFALLOUT collection for "+customerDetails.accountNumber+":", Ordertype, Status.PASS);
		    	   System.out.println("not combo");
		       }
	       }
		
	


	public void BOM_SWAPELIGIBLEACCOUNT_Validation() throws Exception{
		// TODO Auto-generated method stub
		customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		String iseligible=null;
		String orderchannel=null;
		String orderdate=null;
		String dbURI="mongodb://bomintuser:bomintuser@bomdb-dt-b1s.ula.comcast.net:27017/?authSource=bulkom-int";	
		 MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		 
		 // Now connect to your databases
	       DB db = mongoClient.getDB("bulkom-int");
	       System.out.println("Connected to database successfully");
	       System.out.println(db.getCollectionNames());
	       Thread.sleep(10000);
	       
	       DBCollection collection=db.getCollection("BOM_SWAPELIGIBLEACCOUNT");
	       BasicDBObject whereQuery1 = new BasicDBObject();
	       whereQuery1.put("ACCOUNTNUMBER", customerDetails.accountNumber);
	       BasicDBObject fields = new BasicDBObject();
	       fields.put("ISELIGIBLE", 1);
	       fields.put("ORDERCHANNEL",1);
	       fields.put("ORDERDATE",1);
	       DBCursor cursor = collection.find(whereQuery1, fields);
	       while (cursor.hasNext()) {
	           DBObject obj=cursor.next();
	           iseligible=((BasicBSONObject) obj).getString("ISELIGIBLE");
	           orderchannel=((BasicBSONObject) obj).getString("ORDERCHANNEL");
	           orderdate=((BasicBSONObject) obj).getString("ORDERDATE");
	     System.out.println(iseligible);
	     System.out.println(orderchannel);
	     System.out.println(orderdate);
	     if(iseligible.equals("FALSE"))
	     {
	    	 report.updateTestLog("Iseligible value for  "+customerDetails.accountNumber+":", "+iseligible+", Status.PASS);
	    	 report.updateTestLog("Orderchannel value for  "+customerDetails.accountNumber+":", "+orderchannel+", Status.PASS);
	    	 report.updateTestLog("OrderDate value for  "+customerDetails.accountNumber+":", "+orderchannel+", Status.PASS);
	     }
	     else
	     {
	    	 report.updateTestLog("Iseligible value for  "+customerDetails.accountNumber+":", "+iseligible+", Status.FAIL);
	     }
	     
	       }
		
	}


	public void BOM_SWAPELIGIBLEACCOUNT_For_OrderExist_Validation() throws Exception {
		// TODO Auto-generated method stub
		customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		String iseligible=null;
		String swaptype=null;
		String orderdate=null;
		String dbURI="mongodb://bomintuser:bomintuser@bomdb-dt-b1s.ula.comcast.net:27017/?authSource=bulkom-int";	
		 MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		 
		 // Now connect to your databases
	       DB db = mongoClient.getDB("bulkom-int");
	       System.out.println("Connected to database successfully");
	       System.out.println(db.getCollectionNames());
	       Thread.sleep(10000);
	       
	       DBCollection collection=db.getCollection("BOM_SWAPELIGIBLEACCOUNT");
	       BasicDBObject whereQuery1 = new BasicDBObject();
	       whereQuery1.put("ACCOUNTNUMBER", customerDetails.accountNumber);
	       BasicDBObject fields = new BasicDBObject();
	       fields.put("ISELIGIBLE", 1);
	       fields.put("SWAPTYPE", 1);
	       DBCursor cursor = collection.find(whereQuery1, fields);
	   
	       while (cursor.hasNext()) {
	           DBObject obj=cursor.next();
	           iseligible=((BasicBSONObject) obj).getString("ISELIGIBLE");
	           swaptype=((BasicBSONObject) obj).getString("SWAPTYPE");
	           System.out.println(iseligible);
	           System.out.println(swaptype);
	 
	     if(iseligible.equals("True"))
	     {
	    	 report.updateTestLog("Iseligible value for  "+customerDetails.accountNumber+":", "for the swap type "+swaptype+" "+iseligible+"", Status.PASS);
	    	
	     }
	     else
	     {
	    	 report.updateTestLog("Iseligible value for  "+customerDetails.accountNumber+":", "for the swap type "+swaptype+" "+iseligible+"", Status.FAIL);
	     }
	     
	       }
		
	}
}

	
