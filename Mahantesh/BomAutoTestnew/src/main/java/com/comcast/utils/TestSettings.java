package com.comcast.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.internal.Base64Encoder;

/**
 * Read Test settings from framework.propeties files
 *
 */
public class TestSettings {
	Properties properties;
	String key;
	long value;
	public TestSettings(){
		loadProperties();
	}
	/**
	 * Get property value 
	 * @param key property key
	 * @return value of the property
	 */
	public String getSettings(String key){
		return properties.getProperty(key);
	}
	
	/**
	 * Get property value 
	 * @param key property key
	 * @return 
	 * @return value of the property
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public  void setSettings(String key,long value) throws FileNotFoundException, IOException{
		this.key=key;
		this.value=value;
		properties.store(new FileOutputStream("framework.properties"), null);

	}
	/**
	 * Get the browser name in which tests needs to be run
	 */
	public String getBrowser(){
		String browser= System.getenv("browser");
		if(browser==null || browser == ""){
			browser=properties.getProperty("browser","firefox");
		}
		return browser;
	}
		
	
	/**
	 *	Get the environment under test 
	 */
	public String getEnvironmentToTest(){
		String test= System.getenv("test");
		if(test==null || test == ""){
			test=properties.getProperty("test","UAT");
		}
		return test;
	}
	
	public String getOracleVersion(){
		String version = System.getenv("version");
		if(version==null || version == ""){
			version=properties.getProperty("version","8.5");
		}
		return version;
		
	}
	
	
	public String getApplicationUserName()
	{
		if(getEnvironmentToTest().equals("UAT"))
		{
			
			 File file = new File("D:\\BomAutoTestnew\\BomAutoTestnew\\src\\test\\resources\\framework.properties");
			  
				FileInputStream fileInput = null;
				try {
					fileInput = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Properties prop = new Properties();
				
				//load properties file
				try {
					prop.load(fileInput);
				} catch (IOException e) {
					e.printStackTrace();
				}
		System.out.println("Username is :-"+ getSettings("bomUserName"));
		}
		return getSettings("bomUserName");
		
		
	}
	
	public String getApplicationPassword()
	{
		if(getEnvironmentToTest().equals("UAT"))
		{
			
		}
		return getSettings("bomPassword");
		
		
	}
	
	
	
	
	/**
	 *	Get the URL of the application under test 
	 */
	public String getApplicationURL(){
		if(getEnvironmentToTest().equals("UAT")){
			System.out.println("BOM Url is " + getSettings("bom_uat_url"));
			return getSettings("bom_uat_url");
		}else if(getEnvironmentToTest().equals("SOAK")){
			return getSettings("bom_soak_url");
		}else if(getEnvironmentToTest().equals("PROD")){
			return getSettings("bom_prod_url");
		}else if(getEnvironmentToTest().equals("STG")){
			return getSettings("bom_stg_url");
		}else if(getEnvironmentToTest().equals("NEWUAT")){
			return getSettings("bom_uat_url");
		}else if(getEnvironmentToTest().equals("SUITE")){
			return getSettings("bom_suite_env_url");
		}else if(getEnvironmentToTest().equals("INT")){
			return getSettings("bom_int_url");
		}else{
			return getSettings("bom_qa_url");
		}
		
	}
	
	public String smartConnectUrl(){
		if(getEnvironmentToTest().equals("QA")){
			return getSettings("smart_QA");
		}else{
			return getSettings("url_dataBase");
		}
	}
	/**
	 *	Get the URL of the Information Manager application  
	 */
	public String getIMURL(){
		
		if(getEnvironmentToTest().equals("QA")){
			return getSettings("im_qa_url");
		}
		else{
			return getSettings("im_uat_url");
		}
		
	
	}
	public String getEnvironment() {
		return getSettings("Environment");
	}
	/**
	 * Load properties from framework.properties file
	 */
	public void loadProperties() {
		properties= new Properties();
		try {
			String relativePath=TestUtils.getRelativePath();
			properties.load(new FileInputStream(relativePath + "//src//test//resources"+ File.separator +"framework.properties"));
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSheetName() {
		if(getEnvironmentToTest().equals("UAT") || (getEnvironmentToTest().equals("NEWUAT"))){
			return getSettings("uat_SheetName");
		}else if((getEnvironmentToTest().equals("SOAK"))||(getEnvironmentToTest().equals("PROD"))){
			return getSettings("soak_SheetName");
		}
		else if((getEnvironmentToTest().equals("STG"))){
			return getSettings("stg_SheetName");
		}
		else if((getEnvironmentToTest().equals("SUITE"))){
			return getSettings("suite_SheetName");
		}
		else{
			return getSettings("qa_SheetName");
		}
	}
	
	/**
     *             Get the URL of the Maestro Components application  
     */
	public String getMCURL(){
	                     
	     if(getEnvironmentToTest().equals("UAT")){
	                     return getSettings("maestro_uat_url");
	     }
	     else{
	                     return getSettings("maestro_qa_url");
	     }                              
	     
	}
	
	/**
	 *	Get the URL of the Diagnostics widget
	 */	
	public String getITGURL(){
		if(getEnvironmentToTest().equals("UAT")){
			return getSettings("ITG_uat_url");
		}
		else{
			return getSettings("ITG_qa_url");
		}
	}
	
	/**
	 *	Get the URL of .com ITG
	 */	
	public String getCOMITGURL(){
		if(getEnvironmentToTest().equals("UAT")){
			return getSettings("com_ITG_uat_url");
		}else if(getEnvironmentToTest().equals("SOAK")){
			return getSettings("com_ITG_soak_url");
		}		
		else{
			return getSettings("com_ITG_qa_url");
		}
	}
	
	/**
	 *	Get the URL of the HTML ITG widget
	 */	
	public String getHTMLITGURL(){
		if(getEnvironmentToTest().equals("UAT")){
			return getSettings("HTML_Mobflow_uat_url");
		}else if(getEnvironmentToTest().equals("SOAK")){
			return getSettings("HTML_Mobflow_soak_url");
		}else{
			return getSettings("HTML_Mobflow_qa_url");
		}
	}
	
	
	/**
	 *	Get the URL of the Information Manager application  
	 */
	public String getRCURL(){
		
		if(getEnvironmentToTest().equals("QA")){
			return getSettings("RC_qa_url");
		}
		else{
			return getSettings("RC_uat_url");
		}
		
	
	}
	
	/**
     *             Get the URL of the gmail  
     */
	public String getGmailURL(){
	   return getSettings("gmail_url");
	}
	
	//QC Details
	
	public String getQCUrl() {
		return getSettings("qcURL");
	}
	
	public String getQCUserName() {
		return getSettings("qcUserName");
	}
	
	public String getQCPassword() {
		return getSettings("qcPassword");
	}
	
	public String getQCDomain() {
		return getSettings("qcDomain");
	}
	public String getQCProject() {
		return getSettings("qcProject");
	}
	public String getQCTestLabPath() {
		return getSettings("qcTestLabPath");
	}
	public String getQCUpdate() {
		return getSettings("qcUpdate");
	}
	
	public String DecodeString(String input) {
		if (input != null && !input.equals(""))
			return new String(new Base64Encoder().decode(input));
		else
			return "";
	}
}
