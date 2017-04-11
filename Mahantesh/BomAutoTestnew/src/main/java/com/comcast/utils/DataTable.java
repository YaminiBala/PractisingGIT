package com.comcast.utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.internal.Base64Encoder;


/**
 * Datatable class for fetching value from the excel sheet
 *
 */
public class DataTable {
	protected Hashtable<String, String> testData;
	public static HashMap<String, Hashtable<String, String>> dataMap = new HashMap<String, Hashtable<String, String>>();
	public  String testName;
	TestSettings lgn = new TestSettings();
	public DataTable(String testName) {
		this.testName=testName;
		loadTestData(testName);
		
	}
	public DataTable(String Role, String sheetname) 
	{
			this.testName=Role;
			loadTestData(testName,sheetname);		
	
	}
	public void loadTestData(String testName,String sheetname) {
		HSSFWorkbook hssfWorkbook = getWorkBook(testName);	
		//HSSFSheet dataSheet = getSheet(hssfWorkbook);
		HSSFSheet sheet = hssfWorkbook.getSheet(sheetname);
		ArrayList<String> columNames= getColumnNames(sheet);
		Row testDataRow= getTestDataRowForTestCase(sheet);
		if(testDataRow==null){
			throw new RuntimeException("Unable to find testdata row for " + testName);
		}
		testData=prepareTestDataRowHashTable(columNames,testDataRow);
		dataMap.put(testName, testData);
	}
	private synchronized void loadTestData(String testName) {
		HSSFWorkbook hssfWorkbook = getWorkBook(testName);	
		System.out.println("loadTestData test name:" + testName);
		HSSFSheet dataSheet = getSheet(hssfWorkbook);
		//System.out.println("dataSheet:"+dataSheet);
		ArrayList<String> columNames= getColumnNames(dataSheet);
		System.out.println("columNames :  "+columNames);
		Row testDataRow= getTestDataRowForTestCase(dataSheet);
		//System.out.println("row::::"+testDataRow);
		if(testDataRow==null){
			throw new RuntimeException("Unable to find testdata row for " + testName);
		}
		testData=prepareTestDataRowHashTable(columNames,testDataRow);
		dataMap.put(testName, testData);
		System.out.println("loadTestData  testData  === "+testData);
		System.out.println("dataMap    === "+dataMap);
	}
	
	/**
	 * Get value from the data sheet
	 * @param key column name in the data sheet
	 * @return 
	 */
	public String getValue(String key){
		testData = dataMap.get(testName);
		return testData.get(key);
	}

	private HSSFSheet getSheet(HSSFWorkbook hssfWorkbook) {
		
		TestSettings testSetting= new TestSettings();
		String sheetName=testSetting.getSheetName();
		System.out.println("Sheet Name:: "+sheetName);
		//HSSFSheet sheet = hssfWorkbook.getSheetAt(0); 
		HSSFSheet sheet = hssfWorkbook.getSheet(sheetName);
		//System.out.println("sheet::"+sheet);
		return sheet;
	}

	private HSSFWorkbook getWorkBook(String testName) {
		//TestSettings testSetting= new TestSettings();
		//String path=TestUtils.getRelativePath() + File.separator + FrameworkConstants.DATA_FOLDER + File.separator + testSetting.getEnvironment()+ File.separator +FrameworkConstants.TEST_DATA_SHEET_NAME;
		String path=TestUtils.getRelativePath() + "//src//test//resources" + File.separator +FrameworkConstants.TEST_DATA_SHEET_NAME;
		System.out.println("Path::"+path);
		FileInputStream fileInputStream;
		try {
			fileInputStream = new FileInputStream(path);
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);
			//System.out.println("file Name:"+hssfWorkbook);
			fileInputStream.close();
			return hssfWorkbook;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	private Hashtable<String, String> prepareTestDataRowHashTable(
			ArrayList<String> columNames, Row testDataRow) {
		ArrayList<String> cellvalues=new ArrayList<String>();
		
		for(Cell cell:testDataRow){
			cellvalues.add(cell.getStringCellValue());
		}
				
		Hashtable<String, String> testDataRowHashTable= new Hashtable<String,String>();
		
		try{
			/*for(Cell cell:testDataRow){
				i++; 
				if (i<(columNames.size()-5)){
					String columnName=columNames.get(cell.getColumnIndex());
					System.out.print(i+" "+columnName+" ");
					String columnValue=cell.getStringCellValue();
					//testDataRowHashTable.put(columnName, columnValue);
					System.out.println(columnValue);
				}else{
					break;
				}*/
			for (int i=0 ;i<columNames.size()&& i<cellvalues.size();i++){
				String columnName=columNames.get(i);
				String columnValue=cellvalues.get(i);
				/*System.out.println("columnName ->"+columnName);
				System.out.println("columnValue ->"+columnValue);*/
				testDataRowHashTable.put(columnName, columnValue);
			}
		}catch(Exception e){
			System.out.println(e.toString());
		}
		return testDataRowHashTable;
	}

	private ArrayList<String> getColumnNames(Sheet testDataSheet) {
		ArrayList<String> columnNameList= new ArrayList<String>();
		Row row = testDataSheet.getRow(0);
		//System.out.println("Row::"+ testDataSheet.getRow(0));
		for (Cell cell : row) {
			
			columnNameList.add(cell.getStringCellValue());
			//System.out.println("columnNameList:"+columnNameList);
		}
		System.out.println("columnNameList     : "+columnNameList);
		return columnNameList;
	}

		
	private Row getTestDataRowForTestCase(Sheet testDataSheet) {
		for (Row row : testDataSheet) {
			if (IsRequiredTestCaseRow(row,this.testName)) {
				//System.out.println("row:"+row);
				//System.out.println("test Name ->(getTestDataRowForTestCase)"+this.testName);
				return row;
			} 
		}		
		return null;
	}

	private boolean IsRequiredTestCaseRow(Row row, String testCaseName) {
		Cell testCaseIdCell= row.getCell(0);
		//System.out.println("testCaseName::"+testCaseName);
		String testCaseId=testCaseIdCell.getStringCellValue();
		//System.out.println("testCaseId::"+testCaseId);
		if (testCaseId.trim().equals(testCaseName)) {
			return true;
		}
		return false;
	}
	public  String[] getValueForLogin(String key, String testName)
	{
		testData = dataMap.get(testName);	
		System.out.println("getValueForLogin testName  ::  "+testName);
		String[] arr = new String[4];
	String role = testData.get(key);	
System.out.println(role);
	String sheetname = "USER";
		
		try{
		DataTable userData = new DataTable(role, sheetname);
		String encodedpwd = userData.testData.get("Password");
		String decodedpwd = lgn.DecodeString(encodedpwd);
		arr[0] = userData.testData.get("Username");
		arr[1] = decodedpwd;
		System.out.println(arr[0]+" user details - "+arr[1]);
		}catch (Exception ex)
		{
			
			ex.printStackTrace();
			throw new RuntimeException();
			
		}
		return arr;
	}
	public String DecodeString(String input) {
		if (input != null && !input.equals(""))
			return new String(new Base64Encoder().decode(input));
		else
			return "";
	}
}
