package com.comcast.bom.data;

import com.comcast.utils.DataTable;

public class DeviceDetails {
	
	public String serialno1;
	public String serialno2;
	public String friendlyName;
	public String cmMacAddress;
	public String eSTBAddress;
	public String serialno3;
	public String serialno4;
	public String cmMacAddress1;
	public String eSTBAddress1;
	
	public String unit1;
	public String unit2;
	public String rfmac;
	public String tt;
	public String tt1;
	public String IPAdress;
	public String rateCodesDCT;
	public String rateCodesDVR;
	public String rateCodeCMEMTA;
	public String serialno5;
	public String serialno6;
	public String unit3;
	public String cmMacAddress2;
	public String eSTBAddress2;
	public String rfmac2;
	
	
	
	
	public DeviceDetails(String serialno1,String serialno2,String cmMacAddress,String eSTBAddress,String serialno3,String serialno4,String cmMacAddress1,String eSTBAddress1,String unit1,String unit2,String rfmac,String serialno5,String serialno6,String unit3,String cmMacAddress2,String eSTBAddress2,String rfmac2,String rateCodesDCT,String rateCodesDVR,String rateCodeCMEMTA){
		
		this.serialno1 = serialno1;
		this.serialno2= serialno2;
		this.cmMacAddress=cmMacAddress;
		this.eSTBAddress=eSTBAddress;
		this.serialno3=serialno3;
		this.serialno4=serialno4;
		this.cmMacAddress1=cmMacAddress1;
		this.eSTBAddress1=eSTBAddress1;
		this.unit1=unit1;	
		this.unit2=unit2;
		this.rfmac=rfmac;
		this.serialno5=serialno5;
		this.serialno6=serialno6;
		this.unit3=unit3;
		this.cmMacAddress2=cmMacAddress2;
		this.eSTBAddress2=eSTBAddress2;
		this.rfmac2=rfmac2;
		this.rateCodesDCT=rateCodesDCT;
		this.rateCodesDVR=rateCodesDVR;
		this.rateCodeCMEMTA=rateCodeCMEMTA;
		
		
	}

	public static DeviceDetails loadFromDatatable(DataTable dataTable) {

		return new DeviceDetails(dataTable.getValue("SerialNum1"),
				dataTable.getValue("SerialNum2"),
				dataTable.getValue("CMMACAddress"),
				dataTable.getValue("ESTBAddress"),
				dataTable.getValue("SerialNum3"),
				dataTable.getValue("SerialNum4"),
				dataTable.getValue("CMMACAddress1"),
				dataTable.getValue("ESTBAddress1"),
				dataTable.getValue("Unitadd1"),
				dataTable.getValue("Unitadd2"),
				dataTable.getValue("RFMAC"),
				dataTable.getValue("SerialNum5"),
				dataTable.getValue("SerialNum6"),
				dataTable.getValue("Unitadd3"),
				dataTable.getValue("CMMACAddress2"),
				dataTable.getValue("ESTBAddress2"),
				dataTable.getValue("RFMAC2"),
				dataTable.getValue("RateCodesDCT"),
				dataTable.getValue("RateCodesDVR"),
				dataTable.getValue("RateCodeCMEMTA")
				
				
				)
				
				;
	}
}
