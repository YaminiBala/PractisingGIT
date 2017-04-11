package com.comcast.bom.data;

import com.comcast.utils.DataTable;

public class CustomerAccountDetails {

	public String accountNumber;
	public String firstName;
	public String lastName;
	public String address;
	public String cityStateZip;
	public String region;
	public String customerPhone;
	
	public CustomerAccountDetails(String accountNumber, String firstName,
			String lastName, String address, String cityStateZip,
			String region, String customerPhone) {
		
		this.accountNumber = accountNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.cityStateZip = cityStateZip;
		this.region = region;
		this.customerPhone = customerPhone;
	}
	public static CustomerAccountDetails loadFromDatatable(DataTable dataTable){
		return new CustomerAccountDetails(dataTable.getValue("AccountNumber"),
			                   dataTable.getValue("FirstName"),
								dataTable.getValue("LastName"),
								dataTable.getValue("CustomerAddress"),
								dataTable.getValue("CustomerCityStateZip"),
								dataTable.getValue("CustomerRegion"), 
								dataTable.getValue("CustomerPhone"));
		
	}
}
