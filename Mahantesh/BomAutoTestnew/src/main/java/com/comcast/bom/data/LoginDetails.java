package com.comcast.bom.data;

import com.comcast.utils.DataTable;

/**
 * User Login Details 
 *
 */
public class LoginDetails {
	public String userID;
	public String password;
	
	
	public LoginDetails(String emailAddress,String password){
		this.userID = emailAddress;
		this.password = password;
		//System.out.println(this.userID +"::" +this.password);
		
	}
	
	
	
	
	public synchronized static LoginDetails loadFromDatatable(DataTable dataTable, String testName){
		
		
		String arr[] = null;
		try{
		arr = dataTable.getValueForLogin("Username", testName);
		}catch (Exception ex)
		{
			
			ex.printStackTrace();
			throw new RuntimeException();
		}
		
		return new LoginDetails( arr[0],arr[1]);
	}	
}

