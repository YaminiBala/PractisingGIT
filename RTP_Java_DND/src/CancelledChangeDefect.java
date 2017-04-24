import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class CancelledChangeDefect 
{
	 LibraryRtp  lC= new LibraryRtp();
	 RTP_ValidationsClass rV=new RTP_ValidationsClass();
	 public void initialization(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException
	 {
		lC.printStart(input, c, "initialization Starts..!");
		lC.getBaseTime(input, c); 
		execution(input, c);
		lC.printEnd(input, c, "initialization Ends..!");
	 }
	 
	 public void IgnoreWebserviceCallsValidations(Object input, ScriptingContext c) throws ClassNotFoundException, IOException, InterruptedException, SQLException
	 {
		 lC.printStart(input, c, "Validation Starts..!");
		 rtpTestInterface_Validate(input, c);
		 NoDataSetFound(input, c);
		 lC.printEnd(input, c, "Validation Ends..!");
	 }
	 
	 public void execution(Object input, ScriptingContext c)throws ClassNotFoundException, IOException, InterruptedException
	 {
	  	try
	  	{
	  	
	  		    Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
	  	      
	  	        String XML_AccNumber=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");
	  	        c.setValue("ACC_input",XML_AccNumber);
	  	     	  	       
	  	        Application.showMessage("XML_AccNumber::"+XML_AccNumber);
	  	       
	  	        String HOUSE_KEY=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: HOUSE_KEY");
	  	        Application.showMessage("HOUSE_KEY"+HOUSE_KEY);
	  	    //  String flows=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: Flows");
	  	        
	  	        String XML_EndPoint=c.getValue("RTPNormalScenariosdS", "DbConnections: XML_Enpoint");
	  		    String DB_Host=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_HOST");
	  		    String TestCaseName=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseName");
	  		    String dB_Password=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_Password");
	  		    String dB_Username=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_UserName");
	  		    String dB_Port=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_Port");
	  		    String dB_serviceName=c.getValue("RTPNormalScenariosdS", "DbConnections: DB_ServiceName");
	  		    Application.showMessage("Endpoint is ::"+XML_EndPoint);
	  		    Application.showMessage("DB_HOST is ::"+DB_Host);
	  		    Application.showMessage("TestCaseName is ::"+TestCaseName);
	  		    Application.showMessage("dB_Password is ::"+dB_Password);
	  		    Application.showMessage("dB_Username is ::"+dB_Username);
	  		    Application.showMessage("dB_Port is ::"+dB_Port);
	  		    Application.showMessage("dB_serviceName is ::"+dB_serviceName);
	  		    Application.showMessage("XML_AccNumber is ::"+XML_AccNumber);
	  		   // c.setValue("Flow", flows); 
	  		  c.setValue("sHOUSE_KEY", HOUSE_KEY); 
	  		    c.setValue("ACC_input",XML_AccNumber);
	  		    c.setValue("DB_Host", DB_Host);
	  		    c.setValue("dB_Password",dB_Password);
	  		    c.setValue("dB_Username",dB_Username);
	  		    c.setValue("dB_Port",dB_Port);
	  		    c.setValue("dB_serviceName",dB_serviceName);
	  		    c.setValue("XML_EndPoint",XML_EndPoint);
	  		
	  	   
	  	        Application.showMessage("_______________________________________________");
	  	        
	     	  } 
	  	     catch (Exception e) {
				// TODO: handle exception
			}
	  	        
	  	}
	
	public void rtpTestInterface_Validate(Object input, ScriptingContext c) throws IOException, ClassNotFoundException, InterruptedException
	{
		
		
		 ResultSet  rs;
		 int callFound=0, v1=0;
		 String xmldata1 ="receive_data";
         String xmldata2 ="SEND_DATA";  
         Thread.sleep(5000);
         String Time=(String) c.get("BaseTime");
         Application.showMessage("Time is ::"+Time);
         String operation="eep:XHSEEPOrder/xhsevent";
         
         lC.printStart(input, c, "rtpTestInterface_Validate");
             
		try
		{	
			//eep:XHSEEPOrder/xhsevent
			Statement st =lC.dBConnect(input, c);	
	        rs = st.executeQuery("select * from (select * from cwmessagelog where operation = '"+operation+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");       
	        			
	        while (rs.next())
	        {
	              Application.showMessage("RTPTestInterface msgid= "+ rs.getString(1));
	                      
	              if(rs.getBlob("receive_data")==null)
	              {
	           
	            	String senddata =lC.extractXml(rs,xmldata2);	           
		            String accountNumber_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/@accountNumber");
		            Application.showMessage("accountNumber is ::"+accountNumber_rtpTest); 
		           		          
		            if(accountNumber_rtpTest==null)
		            {
			            
			            Application.showMessage("accountNumber ID is NULL \n");

			            continue;
		            }
		            else
		            {
		            	System.out.printf("accountNumber value from rtpTestInterface is :: %s \n",accountNumber_rtpTest); 
		            	Application.showMessage("accountNumber value from rtpTestInterface is::"+accountNumber_rtpTest);
		            	
		            	if(accountNumber_rtpTest.equals(c.getValue("ACC_input")))
		            	{ 
		            		System.out.printf("Your Recieve XML is NULL \n");
			            	Application.showMessage("Your Recieve XML is NULL \n");
			            	Application.showMessage("SEND XML is  \n"+ senddata);
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            			            	
		            }
		            
		            if(callFound==1)
	            	{
		            	 String locationId_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Location/@locationId");
		            	 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
				           
				            if(locationId_rtpTest==null)
				            {
				            	Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
				            	Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
					            c.report("locationId is NULL");
					            continue;
				            }
				            else
				            {
				            	
				            	 if(locationId_rtpTest.equals(c.getValue("sHOUSE_KEY")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
					            	 Application.showMessage("Validation is Successfull with locationId"+" "+locationId_rtpTest);
					            	 v1=1;
					            	 String PrimaryEmail_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/PrimaryEmail");
					            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
                                     c.setValue("emailAddress", PrimaryEmail_rtpTest);
							            
							         String FirstName_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/FirstName");
							         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
							         c.setValue("FirstName", FirstName_rtpTest); 
							         
							         String LastName_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/Account/LastName");
							         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
							         c.setValue("LastName", LastName_rtpTest); 
							         
							         String OrderStatus_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderStatus");
							         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
							            
							         String OrderType_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/OrderType");
							         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
							         
							         String RescheduleIndicator_rtpTest=lC.xpathValue(senddata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
							         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
							         String EventSource= lC.nodeFromKey(senddata,"<EventSource>","</EventSource>");
							         c.setValue("sEventSource", EventSource);
							         Application.showMessage("EventSource is::"+c.getValue("sEventSource")); 
							         
							         String city=  lC.nodeFromKey(senddata,"<City>","</City>");
							         c.put("Ecity",city);
							         Application.showMessage("Ecity is::"+c.get("Ecity")); 
							         
							         String Zipcode=  lC.nodeFromKey(senddata,"<Zipcode>","</Zipcode>");
							         c.put("Ezipcode",Zipcode);
							         Application.showMessage("Ezipcode is::"+c.get("Ezipcode")); 

							         
							         if(v1*callFound==1)
							            {
							            
							            	Application.showMessage("Validation Points are Success");
							            	break;
							            }
							            
							            else
							            {
							            	Application.showMessage("No call Found");
							            	c.put("result", "false");
							            	break;
							            }
					             }
					             else
					             {
					            	 continue;
					             }
				            }
				         
	            	}
	             
             		       
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	
	            	String recievedata=lC.extractXml(rs,xmldata1);
		         
		            String accountNumber_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/@accountNumber");
		            Application.showMessage("accountNumber is ::"+accountNumber_rtpTest); 
		            	          
		            if(accountNumber_rtpTest==null)
		            {
			          
			            Application.showMessage("accountNumber ID is NULL \n");

			            continue;
		            }
		            else
		            {
		            	System.out.printf("accountNumber value from rtpTestInterface is :: %s \n",accountNumber_rtpTest); 
		            	Application.showMessage("accountNumber value from rtpTestInterface is::"+accountNumber_rtpTest);
		            	if(accountNumber_rtpTest.equals(c.getValue("ACC_input")))
		            	{
		            	    Application.showMessage("Your SEND XML is NULL \n");
		   		            Application.showMessage("Recieve XML is  \n"+ recievedata);
		   		      		            
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
		            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_rtpTest);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            			            	
		            }
		            
		            if(callFound==1)
	            	{
		            	 String locationId_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Location/@locationId");
		            	 Application.showMessage("locationId value from rtpTestInterface is :: "+locationId_rtpTest);
				           
				            if(locationId_rtpTest==null)
				            {
				            	Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
				            	Application.showMessage("locationId value from rtpTestInterface is ::NULL \n");
					            c.report("locationId is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("PERMIT REQUIRED value from getCustomerPermitRequirements is ::"+" "+locationId_rtpTest);
				            	 if(locationId_rtpTest.equals(c.getValue("sHOUSE_KEY")))
				            	 {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-rtpTestInterface********");
					            	 Application.showMessage("Validation is Successfull with locationId"+" "+locationId_rtpTest);
					            	 v1=1;
					            	 String PrimaryEmail_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/PrimaryEmail");
					            	 Application.showMessage("PrimaryEmail is ::"+ PrimaryEmail_rtpTest); 
					            	 c.setValue("emailAddress", PrimaryEmail_rtpTest);
					            	 
							         String FirstName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/FirstName");
							         Application.showMessage("FirstName is ::"+ FirstName_rtpTest); 
							         c.setValue("FirstName", FirstName_rtpTest);
							         
							         String LastName_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/Account/LastName");
							         Application.showMessage("LastName is ::"+ LastName_rtpTest); 
							         c.setValue("LastName", LastName_rtpTest); 
							         
							         String OrderStatus_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderStatus");
							         Application.showMessage("OrderStatus is ::"+ OrderStatus_rtpTest); 
							            
							         String OrderType_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/OrderType");
							         Application.showMessage("OrderType is ::"+ OrderType_rtpTest); 
							         
							         String RescheduleIndicator_rtpTest=lC.xpathValue(recievedata,"//EEPEvent/Body/WorkOrder/RescheduleIndicator");
							         Application.showMessage("RescheduleIndicator is::"+RescheduleIndicator_rtpTest); 
							         String EventSource= lC.nodeFromKey(recievedata,"<EventSource>","</EventSource>");
							         c.setValue("sEventSource", EventSource);
							         
							         String city=  lC.nodeFromKey(recievedata,"<City>","</City>");
							         c.put("Ecity",city);
							         Application.showMessage("Ecity is::"+c.get("Ecity")); 
							         
							         String Zipcode=  lC.nodeFromKey(recievedata,"<City>","</City>");
							         c.put("Ezipcode",Zipcode);
							         Application.showMessage("Ezipcode is::"+c.get("Ezipcode")); 
							         
							         Application.showMessage("EventSource is::"+c.getValue("sEventSource"));
							         if(v1*callFound==1)
							            {
							            
							            	Application.showMessage("Validation Points are Success");
							            	break;
							            }
							            
							            else
							            {
							            	c.put("result", "false");
							            	Application.showMessage("No call Found");	
							            	break;
							            }
					             }
					             else
					             {
					            	 continue;
					             }
				            }				            
	            	  }
	               }
	             
	            }
	         
	       
	        st.close();
		}
		
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	}
	
	
    public void NoDataSetFound(Object input, ScriptingContext c)throws InterruptedException, ClassNotFoundException, IOException, SQLException 
    {
                    
    	ResultSet rs;              
        int v1=0,i=0;
        Thread.sleep(10000);
		String Account=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: XML_AccNumber");
		Application.showMessage("Account is::"+Account);

		String Time=(String) c.get("BaseTime");
		Application.showMessage("BaseTime is::"+Time);
		Statement st =lC.dBConnect(input, c);	
        rs = st.executeQuery("select * from (select * from cwmessagelog where user_data2 = '"+Account+"' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");       
        
		Application.showMessage("The dataSet is::"+rs.toString());
		
		if(rs.getRow()==0)
		{
			Application.showMessage("No Record Sets Found..!");
		}
		else
		{
			while(rs.next())
			{
			      String operation=rs.getString("OPERATION");
			      Application.showMessage("OPeration is ::"+operation);
			      
			    			      
			       if(operation.equals("cmb:commonOrderService/orderUpdate"))
			       {
			           Application.showMessage("Extra call OrderUpdate Found");
			           c.report("Order Update call found..!");
			           v1=1;
			           continue;
			       }
			       else
			       {
			    	   Application.showMessage("Extra call OrderUpdate Found");  
			       }
			      
			      
			     if(v1==1)
			     {
			          c.report("Extra Call Found");
			          break;
			     }
			     else
			     {
			         Application.showMessage(i+"th row data is not an Extra call ");
			     }
			     i++;
			}
          st.close();
		}	
    }
}
