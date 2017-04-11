package com.comcast.bom.data;
import com.comcast.utils.DataTable;

public class DataBaseDetails {
	public String SqlQuery;	
	
	
	public DataBaseDetails(String sqlString) {		
		this.SqlQuery = sqlString;		
		
	}
	public static DataBaseDetails loadFromDatatable(DataTable dataTable){
		return new DataBaseDetails(dataTable.getValue("SqlQuery"));
	}

	}
