package com.comcast.bom.data;


import com.comcast.utils.DataTable;

public class CustomerDetails {
	
	public String phoneNumber;
	public String accountNumber;
	public String MotoORCisco;
	public String Location_ID;
	public String targetAccountNumber;
	public String ticketNumber;
	public String emailUserName;
	public String previousAccountsFromDate;
	public String previousAccountsToDate;
	public String ipAddress;
	public String firstName;
	public String lastName;
	public String postalAddress;
	public String zipCode;
	public String macAddress;
	public String socialNetworkSiteID;
	public String dnsLookUp;
	public String customerErrorMsg;
	public String invalidFirstName;
	public String invalidLastName;
	public String multiFirstName;
	public String multiLastName;
	public String invalidAccntNos;
	public String RNGOnAccount;
	public String XGRateCodes;
	public String Xi3RateCodes;
	public String DVRCount;
	public String DCTCount;
	public String RateCodesDVR;
	public String RateCodesDCT;
	public String serviceType;
	
	
	//last updated on 1/20/2017 by dnages002c for counting no of RNG on account
	public String invalidFNLNErrMsg;
	public CustomerDetails(String phoneNumber, String accountNumber,String MotoORCisco,String Location_ID,
			String targetAccountNumber,String ticketNumber, String emailUserName,
			String previousAccountsFromDate, String previousAccountsToDate,
			String ipAddress, String firstName, String lastName,
			String postalAddress, String zipCode, String macAddress,
			String socialNetworkSiteID, String dnsLookUp,String customerErrorMsg,
			String invalidFirstName,String invalidLastName,String multiFirstName,
			String multiLastName,String invalidAccntNos,String invalidFNLNErrMsg,String RNGOnAccount,String XGRateCodes,String Xi3RateCodes,String DVRCount,String DCTCount, String RateCodesDVR, String RateCodesDCT,String serviceType) {
		super();
		this.phoneNumber = phoneNumber;
		this.accountNumber = accountNumber;
		this.MotoORCisco = MotoORCisco;
		this.Location_ID = Location_ID;
		this.targetAccountNumber = targetAccountNumber;
		this.ticketNumber = ticketNumber;
		this.emailUserName = emailUserName;
		this.previousAccountsFromDate = previousAccountsFromDate;
		this.previousAccountsToDate = previousAccountsToDate;
		this.ipAddress = ipAddress;
		this.firstName = firstName;
		this.lastName = lastName;
		this.postalAddress = postalAddress;
		this.zipCode = zipCode;
		this.macAddress = macAddress;
		this.socialNetworkSiteID = socialNetworkSiteID;
		this.dnsLookUp = dnsLookUp;
		this.customerErrorMsg = customerErrorMsg;
		this.invalidFirstName = invalidFirstName;
		this.invalidLastName = invalidLastName;
		this.multiFirstName = multiFirstName;
		this.multiLastName = multiLastName;
		this.invalidAccntNos = invalidAccntNos;
		this.invalidFNLNErrMsg=invalidFNLNErrMsg;
		this.RNGOnAccount=RNGOnAccount;
		this.XGRateCodes=XGRateCodes;
		this.Xi3RateCodes=Xi3RateCodes;
		this.DCTCount=DCTCount;
		this.DVRCount=DVRCount;
		this.RateCodesDVR=RateCodesDVR;
		this.RateCodesDCT=RateCodesDCT;
		this.serviceType=serviceType;
		
	}



	public static CustomerDetails loadFromDatatable(DataTable dataTable){

	return new CustomerDetails(dataTable.getValue("PhoneNumber"),
			                   dataTable.getValue("AccountNumber"),
			                   dataTable.getValue("MotoORCisco"),
			                   dataTable.getValue("Location_ID"),
			                   dataTable.getValue("TargetAccountNumber"),
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
								dataTable.getValue("CustomerErrorMsg"),
								dataTable.getValue("InvalidFirstName"),
								dataTable.getValue("InvalidLastName"),
								dataTable.getValue("MultiFirstName"),
								dataTable.getValue("MultiLastName"),
								dataTable.getValue("InvalidAccntNos"),
								dataTable.getValue("InvalidFNLNErrMsg"),
								dataTable.getValue("RNGOnAccount"),
								dataTable.getValue("XGRateCodes"),
								dataTable.getValue("Xi3RateCodes"),
								dataTable.getValue("DCTCount"),
								dataTable.getValue("DCTCount"),
								dataTable.getValue("RateCodesDVR"),
								dataTable.getValue("RateCodesDCT"),
								dataTable.getValue("serviceType"));
								
	
}
}
