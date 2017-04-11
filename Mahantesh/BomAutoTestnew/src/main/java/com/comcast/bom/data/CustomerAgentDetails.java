package com.comcast.bom.data;

import com.comcast.utils.DataTable;

public class CustomerAgentDetails {

	public String agentFirstName;
	public String agentLastName;

	
	public CustomerAgentDetails(String agentFirstName, String agentLastName) {
		

		this.agentFirstName = agentFirstName;
		this.agentLastName = agentLastName;

	}
	public static CustomerAgentDetails loadFromDatatable(DataTable dataTable){
		return new CustomerAgentDetails(dataTable.getValue("AgentFirstName"),
			                   dataTable.getValue("AgentLastName"));
		
	}
}
