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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
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

import org.xml.sax.SAXException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.comcast.reporting.*;

import com.comcast.utils.ComcastTest;
import com.comcast.utils.SeleniumReport;
import com.comcast.utils.SoapRequest;
import com.comcast.utils.TestSettings;
import com.comcast.utils.TestUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.DBCursor;

public class Mongoconnect extends ComcastTest {

	public CustomerDetails customerDetails;
	public SeleniumReport report;
	public SoapRequest SR;
	DBCursor cursor1;
	DBCursor cursor, cursor2;
	public WebDriver fail;
	DBObject dbObject, dbObject1;
	Object SWAPTYPE, ERRTEXT, ERRSOURCE, ORDERSTATUS, STATUS;
	boolean ISELIGIBLE;
	String Error, Error1;
	DeviceDetails deviceDetails = DeviceDetails.loadFromDatatable(dataTable);
	public String Endpoint;
	public String Request;
	public String Response;
	public String workorderid;

	public Mongoconnect(SeleniumReport report) {

		this.report = report;

	}

	public void preCondition_X1() throws Exception {
		customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		String iseligible = null;
		String swaptype = null;
		String orderdate = null;
		String dbURI = new TestSettings().getSettings("DBconnURL");
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));

		// Now connect to your databases
		DB db = mongoClient.getDB("bulkom-qa");
		System.out.println("Connected to database successfully");
		System.out.println(db.getCollectionNames());
		Thread.sleep(5000);

		DBCollection collection = db.getCollection("BOM_SWAPELIGIBLEACCOUNT");
		BasicDBObject whereQuery1 = new BasicDBObject();
		whereQuery1.put("ACCOUNTNUMBER", customerDetails.accountNumber);
		BasicDBObject fields = new BasicDBObject();
		fields.put("ISELIGIBLE", 1);
		fields.put("SWAPTYPE", 1);
		DBCursor cursor = collection.find(whereQuery1, fields);

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			iseligible = ((BasicBSONObject) obj).getString("ISELIGIBLE");
			swaptype = ((BasicBSONObject) obj).getString("SWAPTYPE");
			System.out.println(iseligible);
			System.out.println(swaptype);
			if (iseligible.equals("true")) {
				report.reportPassEvent("Iseligible value", "Eligibility is " + iseligible + " for the swap type "
						+ swaptype + " for the account " + customerDetails.accountNumber + "");

			} else {
				report.reportFailEvent("Iseligible value", "Eligibility is " + iseligible + " for the swap type "
						+ swaptype + " for the account " + customerDetails.accountNumber + "");
			}

		}

	}

	public void postsubmission_CheckEligibility_X1() throws InterruptedException {
		// TODO Auto-generated method stub

		customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		String iseligible = null;
		String orderchannel = null;
		String orderdate = null;
		String swaptype = null;
		String dbURI = "mongodb://bomqauser:bomqauser@bomdb-dt-b1s.ula.comcast.net:27017/?authSource=bulkom-qa";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));

		// Now connect to your databases
		DB db = mongoClient.getDB("bulkom-qa");
		System.out.println("Connected to database successfully");
		System.out.println(db.getCollectionNames());
		Thread.sleep(10000);

		DBCollection collection = db.getCollection("BOM_SWAPELIGIBLEACCOUNT");
		BasicDBObject whereQuery1 = new BasicDBObject();
		whereQuery1.put("ACCOUNTNUMBER", customerDetails.accountNumber);
		BasicDBObject fields = new BasicDBObject();
		fields.put("ISELIGIBLE", 1);
		fields.put("ORDERCHANNEL", 1);
		fields.put("ORDERDATE", 1);
		fields.put("SWAPTYPE", 1);
		DBCursor cursor = collection.find(whereQuery1, fields);
		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			iseligible = ((BasicBSONObject) obj).getString("ISELIGIBLE");
			orderchannel = ((BasicBSONObject) obj).getString("ORDERCHANNEL");
			orderdate = ((BasicBSONObject) obj).getString("ORDERDATE");
			swaptype = ((BasicBSONObject) obj).getString("SWAPTYPE");
			System.out.println(iseligible);
			System.out.println(orderchannel);
			System.out.println(orderdate);
			if (iseligible.equals("false")) {
				if (swaptype.equals("X1")) {
					report.reportPassEvent("Validate Swaptype and Eligibility Post Swap", "The swap type is: " + swaptype + "," + "The eligibility value is: " + iseligible);
					report.reportPassEvent("Validate Orderchannel", "The Orderchannel is:" + orderchannel);
					report.reportPassEvent("Validate OrderDate", "The OrderDate is:" + orderdate);

				}
				if (swaptype.equals("MPEG4")) {
					report.reportPassEvent("Validate Swaptype and Eligibility Post Swap", "The swap type is: " + swaptype + "," + "The eligibility value is: " + iseligible);
				}
			} else {
				report.reportFailEvent("Iseligible value for  " + customerDetails.accountNumber + ":", iseligible);
			}

		}

	}

	public void BOM_ORDER_Validation() throws Exception {
		// TODO Auto-generated method stub
		String Testname = Thread.currentThread().getStackTrace()[1].getMethodName();
		System.out.println("Method name" + Thread.currentThread().getStackTrace()[1].getMethodName());
		customerDetails = CustomerDetails.loadFromDatatable(dataTable);
		String orderstatus1 = null;

		String ordertype = null;
		int tagCount = Integer.parseInt(customerDetails.DCTCount) + Integer.parseInt(customerDetails.DVRCount);
		System.out.println("tagcount:" + tagCount);

		String dbURI = "mongodb://bomqauser:bomqauser@bomdb-dt-b1s.ula.comcast.net:27017/?authSource=bulkom-qa";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		// Now connect to your databases
		DB db = mongoClient.getDB("bulkom-qa");
		// report.updateTestLog("Connecting to mongo db for validation",
		// "connected successfully",);
		System.out.println("Connected to database successfully");
		System.out.println(db.getCollectionNames());
		Thread.sleep(10000);
		// to check the status of the order
		DBCollection collection = db.getCollection("BOM_ORDER");
		BasicDBObject whereQuery1 = new BasicDBObject();
		whereQuery1.put("ACCOUNTNUMBER", customerDetails.accountNumber);
		BasicDBObject fields = new BasicDBObject();
		fields.put("ORDERSTATUS", 1);
		fields.put("ORDERTYPE", 1);
		// retrieveData.sort(new
		// BasicDBObject("ORDERCREATIONDATE",-1)).limit(1);
		// fields.put("_id", 1);
		fields.put("ORDERCREATIONDATE", 1);

		// db.collection.find()._addSpecial( "$orderby", { age : -1 } )
		// db.collection.find( { $query: {}, $orderby: { age : -1 } } )
		DBCursor cursor = collection.find(whereQuery1, fields);
		// cursor.sort(new BasicDBObject("_id", -1)).limit(1);
		cursor.sort(new BasicDBObject("ORDERCREATIONDATE", -1)).limit(1);

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			orderstatus1 = ((BasicBSONObject) obj).getString("ORDERSTATUS");
			ordertype = ((BasicBSONObject) obj).getString("ORDERTYPE");
			System.out.println(ordertype);
			if (ordertype.equals("X1")) {
				report.reportPassEvent("Validate Ordertype", "Order is submitted for " + ordertype + " swap type");

			} else {
				report.reportFailEvent("Validate Ordertype", "Order is submitted for " + ordertype + " swap type");
			}
			System.out.println(orderstatus1);

			if (orderstatus1.equals("SUB")) {

				report.reportPassEvent("Validating Orderstatus of the account " + customerDetails.accountNumber, "Order in" + orderstatus1 + "");

			} else {
				report.reportFailEvent("Validating Orderstatus of the account :" + customerDetails.accountNumber,
						"Order in " + orderstatus1 + "");
				System.out.println("validation failed");

			}

		}
		// TO retrive order item
		BasicDBObject whereQuery = new BasicDBObject();
		// whereQuery.put("ACCOUNTNUMBER", "8210100200643226");
		// BasicDBObject fields = new BasicDBObject();
		whereQuery.put("ACCOUNTNUMBER", customerDetails.accountNumber);
		whereQuery.put("ORDERSTATUS", "SUB");
		DBCursor retrieveData = collection.find(whereQuery);
		System.out.println(retrieveData);
		while (retrieveData.hasNext()) {
			BasicDBObject obj = (BasicDBObject) retrieveData.next();

			System.out.println(obj);

			String outt = obj.toString();
			JSONObject obj1 = new JSONObject((outt));

			JSONArray jsonArray = obj1.getJSONArray("ORDER_ITEMDETAILS");

			int i = jsonArray.length();
			System.out.println("ORDER_ITEMDETAILS length:" + i);
			if (tagCount == 1) {
				System.out.println("tag2 validation");
				System.out.println("ORDER_ITEMDETAILS length:" + i);
				String ratecodes1 = jsonArray.getJSONObject((i - 3)).getString("RATECODES");
				System.out.println("rate code frm db:" + ratecodes1);

				if (ratecodes1.equals(customerDetails.RateCodesDVR)) {
					System.out.println("validating rate codes");
					report.reportPassEvent("Validate Rate Codes", "Rate codes are " + ratecodes1 + "");

				} else {
					System.out.println("Error while validating rate codes");
					report.reportFailEvent("Validate Rate Codes", "Rate codes are " + ratecodes1 + "");

				}
			}

			if (tagCount == 2) {
				System.out.println("tag2 validation");
				System.out.println("ORDER_ITEMDETAILS length:" + i);
				String ratecodes1 = jsonArray.getJSONObject((i - 3)).getString("RATECODES");
				System.out.println("rate code frm db:" + ratecodes1);
				String ratecodes2 = jsonArray.getJSONObject((i - 1)).getString("RATECODES");
				System.out.println("rate code frm db:" + ratecodes2);

				if (ratecodes1.equals(customerDetails.RateCodesDVR)
						&& ratecodes2.equals(customerDetails.RateCodesDCT)) {
					System.out.println("validating rate codes");
					report.reportPassEvent("Validate Rate Codes", "Rate codes are " + ratecodes1 + "");
					report.reportPassEvent("Validate Rate Codes", "Rate codes are " + ratecodes2 + "");
				} else {
					System.out.println("Error while validating rate codes");
					report.reportFailEvent("Validate Rate Codes", "Rate codes are " + ratecodes1 + "");
					report.reportFailEvent("Validate Rate Codes", "Rate codes are " + ratecodes2 + "");
				}
			} else if (tagCount == 3) {
				System.out.println("tag3 validation");

				String ratecodes1 = jsonArray.getJSONObject((i - 4)).getString("RATECODES");
				System.out.println(ratecodes1);
				String ratecodes2 = jsonArray.getJSONObject((i - 2)).getString("RATECODES");
				System.out.println(ratecodes2);
				String ratecodes3 = jsonArray.getJSONObject((i - 1)).getString("RATECODES");
				System.out.println(ratecodes3);
				if (ratecodes1.equals(customerDetails.RateCodesDVR)
						&& ratecodes2.equals(customerDetails.RateCodesDCT)) {
					System.out.println("validating rate codes");
					report.reportPassEvent("Validate Rate Codes", "Rate codes are " + ratecodes1 + "");
					report.reportPassEvent("Validate Rate Codes", "Rate codes are " + ratecodes2 + "");
					report.reportPassEvent("Validate Rate Codes", "Rate codes are " + ratecodes3 + "");

				} else {
					System.out.println("Error while validating rate codes");
					report.reportFailEvent("Validate Rate Codes", "Rate codes are " + ratecodes1 + "");
					report.reportFailEvent("Validate Rate Codes", "Rate codes are " + ratecodes2 + "");
					report.reportFailEvent("Validate Rate Codes", "Rate codes are " + ratecodes3 + "");

				}
			}

		}

	}

	public void AMS_Validation() throws Exception {
		// TODO Auto-generated method stub
		String sendData_wf = null;

		String recieve_wf = null;

		int mr = 0, cr = 0, mcb = 0, ccb = 0, mcr = 0, ccr = 0;

		String serviceType = customerDetails.serviceType;
		String dbURI = "mongodb://bomqauser:bomqauser@bomdb-dt-b1s.ula.comcast.net:27017/?authSource=bulkom-qa";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		// Now connect to your databases
		DB db = mongoClient.getDB("bulkom-qa");
		DBCollection coll = db.getCollection("BOM_MESSAGELOGGING");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("ACCOUNTNUMBER", customerDetails.accountNumber);
		whereQuery.put("OPERATION", "AccountManagementService/GetBillingArrangement");

		BasicDBObject fields = new BasicDBObject();
		fields.put("RESPONSE", 1);
		fields.put("RECEIVETIME", 1);
		fields.put("REQUEST", 1);

		DBCursor retrieveData = coll.find(whereQuery);
		retrieveData.sort(new BasicDBObject("RECEIVETIME", -1)).limit(1);

		// BasicDBObject obj;
		while (retrieveData.hasNext()) {
			DBObject obj = retrieveData.next();
			String SendData = ((BasicBSONObject) obj).getString("REQUEST");
			String RecieveData = ((BasicBSONObject) obj).getString("RESPONSE");
			sendData_wf = XML_Parsing.RemoveNameSpaces(SendData);
			recieve_wf = XML_Parsing.RemoveNameSpaces(RecieveData);

		}
		String legacyLocationID = XML_Parsing.GetValueByXPath(recieve_wf, ".//legacyLocationID");

		System.out.println("legacyLocationID:" + legacyLocationID);

		String legacyCustomerID = XML_Parsing.GetValueByXPath(recieve_wf, ".//legacyCustomerID");

		System.out.println("legacyCustomerID:" + legacyCustomerID);
		String phoneNumber = XML_Parsing.GetValueByXPath(recieve_wf, ".//phoneNumber");

		System.out.println("Phone number:" + phoneNumber);
		String motoOrCisco = customerDetails.MotoORCisco;
		String Location_ID_expected = customerDetails.Location_ID;
		System.out.println("Location ID Expected" + Location_ID_expected);

		if (motoOrCisco.equalsIgnoreCase("MOTO") && serviceType.equalsIgnoreCase("Residential")
				&& (legacyLocationID).equals(Location_ID_expected))

		{

			System.out.println("PASSED:getBillingArngmnt() for MOTO Residential");
			report.reportPassEvent("PASSED:getBillingArngmnt() for MOTO Residential ", Location_ID_expected);
			++mr;

		}

		if (motoOrCisco.equalsIgnoreCase("MOTO") && serviceType.equalsIgnoreCase("Residential") && mr != 1)

		{
			report.reportFailEvent("FAILED:getBillingArngmnt() for MOTO Residential ", Location_ID_expected + "");
		}

		if (motoOrCisco.equalsIgnoreCase("Cisco") && serviceType.equalsIgnoreCase("Residential")
				&& (legacyLocationID).equals(Location_ID_expected))

		{

			report.reportPassEvent("PASSED:getBillingArngmnt() for Cisco Residential ", Location_ID_expected + "");

			++cr;

		}

		if (motoOrCisco.equalsIgnoreCase("Cisco") && serviceType.equalsIgnoreCase("Residential") && cr != 1)

		{
			report.reportPassEvent("FAILED:getBillingArngmnt() for Cisco Residential ", Location_ID_expected + "");
		}

		mongoClient.close();

	}

	public void Qss_Validation() throws Exception {
		// TODO Auto-generated method stub
		String sendData_wf = null;

		String recieve_wf = null;

		int mr = 0, cr = 0, mcb = 0, ccb = 0, mcr = 0, ccr = 0, flagCheck = 0;

		String serviceType = customerDetails.serviceType;
		String dbURI = "mongodb://bomqauser:bomqauser@bomdb-dt-b1s.ula.comcast.net:27017/?authSource=bulkom-qa";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		// Now connect to your databases
		DB db = mongoClient.getDB("bulkom-qa");
		DBCollection coll = db.getCollection("BOM_MESSAGELOGGING");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("ACCOUNTNUMBER", customerDetails.accountNumber);
		whereQuery.put("OPERATION", "CSService/QueryServicesSummary-REST");

		BasicDBObject fields = new BasicDBObject();
		fields.put("RESPONSE", 1);
		fields.put("_id", 1);
		fields.put("REQUEST", 1);

		DBCursor retrieveData = coll.find(whereQuery);
		retrieveData.sort(new BasicDBObject("_id", -1)).limit(1);
		while (retrieveData.hasNext()) {
			DBObject obj = retrieveData.next();
			String SendData = ((BasicBSONObject) obj).getString("REQUEST");
			String RecieveData = ((BasicBSONObject) obj).getString("RESPONSE");
			JSONObject recievedata = new JSONObject(RecieveData);
			JSONArray service_array = recievedata.getJSONArray("services");
			for (int i = 0; i < service_array.length(); i++) {
				JSONObject description = service_array.getJSONObject(i);
				String ratecode = description.getString("rateCode");
				System.out.println("ratecode added to account in biller :: " + ratecode);
				String descriptionVal = description.getString("description");
				System.out.println("description Of the ratecode added :: " + descriptionVal);
				String quantity = description.getString("quantity");
			}
		}
		mongoClient.close();
	}

	public void QDS_Validation() throws Exception {
		// TODO Auto-generated method stub
		String sendData_wf = null, outletVal = null, manufacturerCodeVal = null, modelCodeVal = null;
		HashSet<String> outletSet = new HashSet<String>();
		String recieve_wf = null;
		ArrayList<String> deviceTypeDBList = new ArrayList<String>();
		ArrayList<String> modelCodeDBList = new ArrayList<String>();
		int mr = 0, cr = 0, mcb = 0, ccb = 0, mcr = 0, ccr = 0, flagCheck = 0;

		String dbURI = "mongodb://bomqauser:bomqauser@bomdb-dt-b1s.ula.comcast.net:27017/?authSource=bulkom-qa";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		DB db = mongoClient.getDB("bulkom-qa");

		/* Retrieve device models from Db */

		DBCollection deviceDBList = db.getCollection("BOM_SWAPMODELS");
		BasicDBObject deviceModelQuery = new BasicDBObject();
		deviceModelQuery.put("MODELTYPE", "X1");
		BasicDBObject retrieveDeviceFields = new BasicDBObject();
		retrieveDeviceFields.put("DEVICETYPE", 1);
		retrieveDeviceFields.put("MODELCODE", 1);

		DBCursor retrieveDeviceData = deviceDBList.find(deviceModelQuery, retrieveDeviceFields);
		while (retrieveDeviceData.hasNext()) {
			DBObject obj = retrieveDeviceData.next();
			String DeviceType = ((BasicBSONObject) obj).getString("DEVICETYPE");
			String Modelcode = ((BasicBSONObject) obj).getString("MODELCODE");
			deviceTypeDBList.add(DeviceType);
			modelCodeDBList.add(Modelcode);

		}
		report.reportPassEvent("DB Validaton", "Device list Retrieved successfuly");
		DBCollection coll = db.getCollection("BOM_MESSAGELOGGING");
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("ACCOUNTNUMBER", customerDetails.accountNumber);
		whereQuery.put("OPERATION", "CSService/QueryDevicesSummary-REST");
		BasicDBObject fields = new BasicDBObject();
		fields.put("RESPONSE", 1);
		fields.put("RECEIVETIME", 1);
		fields.put("REQUEST", 1);

		DBCursor retrieveData = coll.find(whereQuery);
		retrieveData.sort(new BasicDBObject("RECEIVETIME", -1)).limit(1);
		while (retrieveData.hasNext()) {
			DBObject obj = retrieveData.next();
			sendData_wf = ((BasicBSONObject) obj).getString("REQUEST");
			recieve_wf = ((BasicBSONObject) obj).getString("RESPONSE");
			JSONObject recievedata = new JSONObject(recieve_wf);
			JSONArray devices_array = recievedata.getJSONArray("devices");
			for (int i = 0; i < devices_array.length(); i++) {
				JSONObject description = devices_array.getJSONObject(i);
				outletVal = description.getString("outlet");
				System.out.println("OUTLET :: " + outletVal);
				manufacturerCodeVal = description.getString("manufacturerCode");
				System.out.println("Manufacturer Code Val :: " + manufacturerCodeVal);
				modelCodeVal = description.getString("modelCode");
				System.out.println("modelCodeVal :: " + modelCodeVal);
				outletSet.add(outletVal);
				if (deviceTypeDBList.size() > 0 && modelCodeDBList.size() > 0) {
					for (int k = 0; k < deviceTypeDBList.size(); k++)
						if (modelCodeVal.equalsIgnoreCase(modelCodeDBList.get(k))) {
							System.out.println("Validating for outlet: " + outletVal);
							System.out.println(
									" Device --> manufacturerCode--->" + manufacturerCodeVal + "  Device Type-->  "
											+ deviceTypeDBList.get(k) + "  modelCode-->  " + modelCodeDBList.get(k));
							report.reportPassEvent("Device Details Validation",
									"Validating for outlet: " + outletVal + "," + "manufacturerCode: "
											+ manufacturerCodeVal + "," + "Device Type: " + deviceTypeDBList.get(k)
											+ "," + "modelCode: " + modelCodeDBList.get(k));
						}
				}
			}
		}
		if (deviceTypeDBList.size() > 0)
			report.reportPassEvent("DB Validation", "Query Device Summary Success");
		else
			report.reportFailEvent("Query Device Summary FAILED", "" + "");

		mongoClient.close();
	}

	public void BulkomDBCleanup() {

		try {
			String dbURI = "mongodb://bomqauser:bomqauser@bomdb-dt-b1s.ula.comcast.net:27017,bomdb-qc-b1s.ula.comcast.net:27017/bulkom-qa";
			MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
			DB db = mongoClient.getDB("bulkom-qa");
			System.out.println("CLEANING ACCOUNT NO::" + customerDetails.accountNumber);
			DBCollection coll = db.getCollection("BOM_SWAPELIGIBLEACCOUNT");
			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("ACCOUNTNUMBER", customerDetails.accountNumber);

			BasicDBObject updateQuery = new BasicDBObject();
			updateQuery.append("$set",
					new BasicDBObject("ISELIGIBLE", true).append("ORDERDATE", null).append("ORDERCHANNEL", null));
			/*
			 * updateQuery.append("$set", new
			 * BasicDBObject().append("ORDERDATE", null));
			 * updateQuery.append("$set", new
			 * BasicDBObject().append("ORDERCHANNEL", null));
			 */
			System.out.println("update_query" + updateQuery);
			coll.update(whereQuery, updateQuery, false, true);

			DBCollection coll1 = db.getCollection("BOM_ORDER");

			BasicDBObject whereQuery1 = new BasicDBObject();
			whereQuery1.put("ACCOUNTNUMBER", customerDetails.accountNumber);

			BasicDBObject updateQuery1 = new BasicDBObject();
			updateQuery1.append("$set", new BasicDBObject("ORDERSTATUS", "CAN"));

			System.out.println("update_query1" + updateQuery1);

			coll1.update(whereQuery1, updateQuery1, false, true);
			report.reportPassEvent("Account Cleaned successfully:", customerDetails.accountNumber);

			mongoClient.close();
		} catch (Exception e) {
			report.reportFailEvent("Mongo DB Clean up","Exception Ocured :"+ e.getMessage());
		}
	}

	public String billerCancelRequest(String accNo) {
		String XMLSendPostString = null;
		try {
			File htmlXMLTemplate1 = new File(
					TestUtils.getRelativePath() + "\\src\\test\\resources\\htmlXMLTemplate1.xml");
			XMLSendPostString = FileUtils.readFileToString(htmlXMLTemplate1);
			XMLSendPostString = XMLSendPostString.replaceAll("&AccountNumber", customerDetails.accountNumber);
			System.out.println("Request: " + XMLSendPostString);

		} catch (Exception e) {

		}
		return XMLSendPostString;
	}

	public String getOmsorderID() {
		String omsWorkOrdrId = null;
		String dbURI = "mongodb://bomqauser:bomqauser@bomdb-dt-b1s.ula.comcast.net:27017/?authSource=bulkom-qa";
		MongoClient mongoClient = new MongoClient(new MongoClientURI(dbURI));
		DB db = mongoClient.getDB("bulkom-qa");
		DBCollection coll = db.getCollection("BOM_ORDER");
		BasicDBObject whereQuery = new BasicDBObject();
		// Application.showMessage("ACCOUNT NUMBER"+AccountNumber);
		whereQuery.put("ACCOUNTNUMBER", customerDetails.accountNumber);
		BasicDBObject fields = new BasicDBObject();
		fields.put("OMSORDERID", 1);
		DBCursor retrieveData = coll.find(whereQuery, fields);
		retrieveData.sort(new BasicDBObject("_id", -1)).limit(1);

		while (retrieveData.hasNext()) {
			DBObject obj = retrieveData.next();
			omsWorkOrdrId = ((BasicBSONObject) obj).getString("OMSORDERID");
			workorderid = omsWorkOrdrId;
		}

		if (null == omsWorkOrdrId) {
			report.reportFailEvent("OMSWorkOrderID NULL", workorderid);
		}
		report.reportPassEvent("OMSWorkOrderID Fetched Successfully", workorderid);

		mongoClient.close();
		return workorderid;

	}

	public String getSikRequest(String acntNumber, String omsorderid) {
		String XMLSendPostString = null;
		try {
			File htmlXMLTemplate1 = new File(TestUtils.getRelativePath() + "\\src\\test\\resources\\sikorderid.xml");
			XMLSendPostString = FileUtils.readFileToString(htmlXMLTemplate1);
			XMLSendPostString = XMLSendPostString.replaceAll("&AccountNumber", customerDetails.accountNumber);
			XMLSendPostString = XMLSendPostString.replaceAll("&omsWrkOrdrId", omsorderid);
			System.out.println("Request: " + XMLSendPostString);

		} catch (Exception e) {

		}
		return XMLSendPostString;
	}

	public void Getsikorderid() throws XPathExpressionException, NullPointerException, SAXException, IOException {
		String accountNumber = customerDetails.accountNumber;
		workorderid = getOmsorderID();
		String xmlSendPostString_sik = getSikRequest(accountNumber, workorderid);
		String endPoint_sik = "http://siktoolsint-b.cable.comcast.com/sikoms/11.04/Order.asmx";
		String response = sendARequest_sik(endPoint_sik, xmlSendPostString_sik);
		String response_sik = XML_Parsing.RemoveNameSpaces(response);
		String sikworkorderid = XML_Parsing.GetValueByXPath(response_sik, ".//OrderID");
		if (sikworkorderid.equalsIgnoreCase("NOSIK"))
			report.reportFailEvent("Sik order id not in response.OP/SIK environment issue", sikworkorderid);

		report.reportPassEvent("SIKWORKORDER ID", sikworkorderid);

	}

	public String sendARequest_sik(String endpoint, String xmlRequest) {
		this.Endpoint = endpoint;
		this.Request = xmlRequest;
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
		try {

			// Set HTTP parameters.
			URL endPoint = new URL(this.Endpoint);
			HttpURLConnection httpConn = (HttpURLConnection) endPoint.openConnection();
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", "http://comcastonline.com/OrderSearch");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buffer = new byte[Request.length()];
			buffer = Request.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));

			// Send a SOAP UI request.
			PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
			pw.write(this.Request);
			pw.flush();

			// Get the response in a input stream.
			httpConn.connect();
			InputStream is = null;
			try {
				is = httpConn.getInputStream();
			} catch (Exception e) {
				is = httpConn.getErrorStream();
			}

			// Convert the xml response to a String
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer);
			String theString = writer.toString();
			System.out.println("\nResponse" + theString);
			report.reportPassEvent("Account Cleaned successfully Biller:", customerDetails.accountNumber);
			return theString;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			report.reportFailEvent("Respnse from service", "Requested route does not exist");
		}
		return null;
	}

	public String sendARequest_sikcancel(String endpoint, String xmlRequest) {
		this.Endpoint = endpoint;
		this.Request = xmlRequest;
		System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");
		try {

			// Set HTTP parameters.
			URL endPoint = new URL(this.Endpoint);
			HttpURLConnection httpConn = (HttpURLConnection) endPoint.openConnection();
			httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			httpConn.setRequestProperty("SOAPAction", "AccountCancelDisconnect");
			httpConn.setRequestMethod("POST");
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);

			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] buffer = new byte[Request.length()];
			buffer = Request.getBytes();
			bout.write(buffer);
			byte[] b = bout.toByteArray();
			httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));

			// Send a SOAP UI request.
			PrintWriter pw = new PrintWriter(httpConn.getOutputStream());
			pw.write(this.Request);
			pw.flush();

			// Get the response in a input stream.
			httpConn.connect();
			InputStream is = null;
			try {
				is = httpConn.getInputStream();
			} catch (Exception e) {
				is = httpConn.getErrorStream();
			}

			// Convert the xml response to a String
			StringWriter writer = new StringWriter();
			IOUtils.copy(is, writer);
			String theString = writer.toString();
			System.out.println("\nResponse" + theString);

			return theString;
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			report.reportFailEvent("Respnse from service", "Requested route does not exist");
		}
		return null;
	}
}
