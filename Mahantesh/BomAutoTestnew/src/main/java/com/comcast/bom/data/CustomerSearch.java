package com.comcast.bom.data;



import com.comcast.utils.DataTable;


public class CustomerSearch {
	
	public String phoneNumber;
	public String accountNumber;
	public String ticketNumber;
	public String emailUserName;
	public String previousAccountsFromDate;
	public String previousAccountsToDate;
	public String ipAddress;
	public String firstName;
	public String lastName;
	public String postalAddress;
	public String ZipCode;
	public String macAddress;
	public String socialNetworkSiteID;
	public String dnsLookUp;
	public String region;
	
	public CustomerSearch(String phoneNumber, String accountNumber,
			String ticketNumber, String emailUserName,
			String previousAccountsFromDate, String previousAccountsToDate,
			String ipAddress, String firstName, String lastName,
			String postalAddress, String zip, String macAddress,
			String socialNetworkSiteID, String dnsLookUp,String region) {
		super();
		this.phoneNumber = phoneNumber;
		this.accountNumber = accountNumber;
		this.ticketNumber = ticketNumber;
		this.emailUserName = emailUserName;
		this.previousAccountsFromDate = previousAccountsFromDate;
		this.previousAccountsToDate = previousAccountsToDate;
		this.ipAddress = ipAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.postalAddress = postalAddress;
		this.macAddress = macAddress;
		this.socialNetworkSiteID = socialNetworkSiteID;
		this.dnsLookUp = dnsLookUp;
		this.region = region;
	}






	public static CustomerSearch loadFromDatatable(DataTable dataTable){
	return new CustomerSearch(dataTable.getValue("PhoneNumber"),
			                   dataTable.getValue("AccountNumber"),
								dataTable.getValue("TicketNumber"),
								dataTable.getValue("EmailUserName"),
								dataTable.getValue("PreviousAccountsFromDate"),	
								dataTable.getValue("PreviousAccountsToDate"),
								dataTable.getValue("IPAddress"),
								dataTable.getValue("FirstName"),
								dataTable.getValue("LastName"),
								dataTable.getValue("PostalAddress"),
								dataTable.getValue("ZipCode"),
								dataTable.getValue("MACAddress"),
								dataTable.getValue("SocialNetworkSiteID"),
								dataTable.getValue("DNSLookUp"),
								dataTable.getValue("Region"));
	
}
}
