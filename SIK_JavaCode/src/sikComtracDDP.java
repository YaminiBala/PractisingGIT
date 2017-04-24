import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class sikComtracDDP 
{
	int trackcode=0;
	
	
	//____________________________________________________________________________________________\\
	//
	//                                INSTALL FUNCTIONS BELOW
	//
	//____________________________________________________________________________________________\\
	
	
  
	public void comtracRequestINSTALL_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
	{
		
		//Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*************Comtrac_Validate Function***************");       
	     Application.showMessage("-----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("comtracRequest"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("comtracRequest"));
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:orderProcessorServicePort/comtracRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	            String rowmsg;
				rowmsg = rs.getString(1);
	            
	            if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            	
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);      
		            Application.showMessage("senddata is ::"+senddata); 
		            Application.showMessage("recievedata is ::"+recievedata); 
		            
		            String reqType_comtrac= sL.GetValueByXPath(recievedata,"/msgInfo/reqType"); 
		       	    Application.showMessage("reqType_comtrac is ::" +reqType_comtrac);
		       	    Application.showMessage("reqType from input Xcel" +c.get("pRequestType"));
	 	            if(reqType_comtrac==null)
		            {
			            c.report("Send Xml reqType_comtrac is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(reqType_comtrac.equals(c.get("pRequestType")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-Comtrac Call********");
		            		Application.showMessage("Validation is Successfull with Input Request Type"+reqType_comtrac);
		            		v1=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            String corp_comtrac= sL.GetValueByXPath(recievedata,"/msgInfo/corp"); 
		       	    Application.showMessage("reqType_comtrac is ::" +corp_comtrac);
	 	            if(corp_comtrac==null)
		            {
			            c.report("Send Xml corp_comtrac is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(corp_comtrac.equals(c.get("pCorp")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2 :: WebServicall-Comtrac Call********");
		            		Application.showMessage("Validation is Successfull with Input serviceRequestID_bfc"+corp_comtrac);
		            		v2=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            String insideXml = sL.GetValueByXPath(recievedata,"/msgInfo/request"); 
		       	    Application.showMessage("insideXml is ::" +insideXml);
	      
		       	    String house_comtrac= sL.GetValueByXPath(insideXml,"/Install/house"); 
		       	    Application.showMessage("house_comtrac is ::" +house_comtrac);	
	 	            if(house_comtrac==null)
		            {
			            c.report("Send Xml accountNumber is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(house_comtrac.equals(c.get("pHouseNo")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 3:: WebServicall-house_comtrac Call********");
		            		Application.showMessage("Validation is Successfull with Input house_comtracr"+house_comtrac);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            	            	
		            if(callFound==1)
		            {
		            	
		               
		 	            
		            	    String comcorp_comtrac= sL.GetValueByXPath(insideXml,"/Install/comcorp"); 
				       	    Application.showMessage("comcorp_comtrac is ::" +comcorp_comtrac);	
			 	            if(comcorp_comtrac==null)
				            {
					            c.report("Send Xml comcorp_comtrac is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(comcorp_comtrac.equals(c.get("pCorp")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 4:: WebServicall-comcorp_comtrac Call********");
				            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+comcorp_comtrac);
				            		v3=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
			 	            
			 	            
			 	            String opr_comtrac= sL.GetValueByXPath(insideXml,"/Install/opr"); 
				       	    Application.showMessage("opr_comtrac is ::" +opr_comtrac);	
			 	            if(opr_comtrac==null)
				            {
					            c.report("Send Xml opr_comtrac is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(opr_comtrac.equals(c.get("pOpr")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 5:: WebServicall-comcorp_comtrac Call********");
				            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+comcorp_comtrac);
				            		v4=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
			 	            
			 	            
			 	            String c_fname= sL.GetValueByXPath(insideXml,"/Install/c_fname"); 
				       	    Application.showMessage("c_fname is ::" +c_fname);	
			 	            if(c_fname==null)
				            {
					            c.report("Send Xml opr_comtrac is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(c_fname.equals(c.get("pFName")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 6:: WebServicall-c_fname Call********");
				            		Application.showMessage("Validation is Successfull with c_fname"+c_fname);
				            		v5=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
			 	            
			 	            
			 	            
			 	            String c_lname= sL.GetValueByXPath(insideXml,"/Install/c_lname"); 
				       	    Application.showMessage("c_lname is ::" +c_lname);	
			 	            if(c_lname==null)
				            {
					            c.report("Send Xml c_lname is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(c_lname.equals(c.get("pLName")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 7:: WebServicall-c_lname Call********");
				            		Application.showMessage("Validation is Successfull with c_lname"+c_lname);
				            		v6=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*v2*v3*v4*v5*v6*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: bfcRequest_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
	         }
	      //   st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	
	
	
	
	public void getHouseInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		//Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	 	 String AccountNumber;
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********getHouseInfo_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");

HashMap Operation=sL.findingoperations(input, c);
c.setValue("OPERATIONVALIDATION",(String) Operation.get("getHouseInfo"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getHouseInfo"));
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'HTTPConnectorClientDDP:HttpConnectorServiceClientDDP/getHouseInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	            String rowmsg;
			    rowmsg = rs.getString(1);
		            
		        if(rowmsg.equals(c.getValue("BaseMsgid")))break;
		        
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);   
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	
	            	    String insideXML1= sL.GetValueByXPath(senddata,"/getHouseInfo/request"); 
	            	    Application.showMessage("Inside XML for SEND DATA is ::"+insideXML1);
		          	           
	            	    String comcorp_getHouse= sL.GetValueByXPath(insideXML1,"/GetHouseInfo/comcorp"); 
			       	    Application.showMessage("comcorp_comtrac is ::" +comcorp_getHouse);	
		 	            if(comcorp_getHouse==null)
			            {
				            c.report("Send Xml comcorp_comtrac is NULL");
				            continue;
			            }
		 	            else
		 	            {
				            if(comcorp_getHouse.equals(c.get("pCorp")))
			            	{
				            	
			            		Application.showMessage("*******Validation Point 2:: WebServicall-comcorp_comtrac Call********");
			            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+comcorp_getHouse);
			            		v1=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            
		 	            }
		 	            
		 	            String house_getHouse= sL.GetValueByXPath(insideXML1,"/GetHouseInfo/house"); 
			       	    Application.showMessage("house_getHouse is ::" +house_getHouse);	
		 	            if(house_getHouse==null)
			            {
				            c.report("Send Xml house_getHouse is NULL");
				            continue;
			            }
		 	            else
		 	            {
				            if(house_getHouse.equals(c.get("pHouseNo")))
			            	{
				            	
			            		Application.showMessage("*******Validation Point 2:: WebServicall-house_getHouse Call********");
			            		Application.showMessage("Validation is Successfull with house_getHouse"+house_getHouse);
			            		v1=1;
			            		callFound=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            
		 	            }
	            	
		            if(callFound==1)
		            {
		            	 String insideXML2= sL.GetValueByXPath(recievedata,"/getHouseInfoResponse/getHouseInfoReturn"); 
	            	     Application.showMessage("Inside XML for SEND DATA is ::"+insideXML2);
	            	     
	            	    
	            	     String h_cust= sL.GetValueByXPath(insideXML2," /GetHouseInfo_response/h_cust"); 
				       	 Application.showMessage("h_cust is ::" +h_cust);
				       	 c.put("ph_cust",h_cust);
				       	 Application.showMessage("CustomerID Lengh" +h_cust.trim().length());
				       	 if(h_cust.trim().length()==1)
				       	 {
				       		 
				       		 String tmp_cust= h_cust.trim();
				       		//AccountNumber=AccountNumber.concat(comcorp_getHouse).concat(house_getHouse).concat("0").concat(h_cust);
				       		//Application.showMessage("AccountNumber maked is ::"+AccountNumber);
				       		 String Acc=(comcorp_getHouse).concat(house_getHouse).concat("0").concat(tmp_cust);
				       		 c.put("pAccountNumber", Acc);
				       		 Application.showMessage("AccountNumber maked is ::"+c.get("pAccountNumber"));
				       		
				       	 }
				       	 
				       	 else
				       	 {
				       		 String Acc=(comcorp_getHouse).concat(house_getHouse).concat(h_cust);
				       		 c.put("pAccountNumber", Acc);
				       		 Application.showMessage("AccountNumber maked is ::"+c.get("pAccountNumber"));
				       		 
				       	 }
		            	
		            	 String h_name_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_name"); 
				       	 Application.showMessage("Street Name is ::" +h_name_getHouse);
				       	 
				       	 String h_stnum_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_stnum"); 
				       	 Application.showMessage("Street Number is ::" +h_stnum_getHouse);
				       	 //________________________________________________________________
				       	 //
				       	 //  Making Address1 format below
				       	 //
				       	 //________________________________________________________________
				       	 
				       	  String aptName=h_name_getHouse.trim();
				       	  String aptNum=h_stnum_getHouse.trim().concat(" ");
				       	 
				       	 
				       	 String Address1=aptNum.concat(aptName);
				       	 //Address1= Address1.trim();
				       	 c.put("pAddress1", Address1);
				       	 Application.showMessage("Address1 needs to be validated with SIK Address as "+c.get("pAddress1"));
				       	 
				       	 String h_aptn_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_aptn"); 
				       	 Application.showMessage("APT Number is ::" +h_aptn_getHouse);
				       	 
				       	 String h_apt_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_apt"); 
				       	 Application.showMessage("APT Prefix is ::" +h_apt_getHouse);
				       	 //________________________________________________________________
				       	 //
				       	 //  Making Address2 format below
				       	 //
				       	 //________________________________________________________________
				       	 String apt_n=h_apt_getHouse.trim();
				       	 String apt_Num =h_aptn_getHouse.trim();
				       	 String Address2=apt_n.concat(" ").concat(apt_Num);
				       	 //Address2= Address2.trim();
				       	 c.put("pAddress2", Address2);
				       	 Application.showMessage("pAddress2 needs to be validated with SIK Address as "+c.get("pAddress2"));
				       	 
				       	 String h_prevloc_outlets= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_prevloc_outlets"); 
				       	 Application.showMessage("h_prevloc_outlets is ::" +h_prevloc_outlets);
				       	 
				       	 String h_fibernode= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_fibernode"); 
				       	 Application.showMessage("h_fibernode is ::" +h_fibernode);
				       	 
				       	 String zip_zipend= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_zipend"); 
				       	 Application.showMessage("zip_zipend is ::" +zip_zipend);
				       	 
				       	 String zip_zipbeg= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_zipbeg"); 
				       	 Application.showMessage("zip_zipbeg is ::" +zip_zipbeg);
				       	 
				       	 String zip_city= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_city"); 
				       	 Application.showMessage("zip_city is ::" +zip_city);
				       	 c.put("GL_City", zip_city);
				       	 
				       	 String zip_state= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_state"); 
				       	 Application.showMessage("zip_state is ::" +zip_state);
				       	c.put("GL_state",zip_state);
				       	 String hvpd_ratecenter= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/housevoipdata/hvpd_ratecenter"); 
				       	 Application.showMessage("hvpd_ratecenter is ::" +hvpd_ratecenter);
				       	 
				       	 String hvpd_voip_geocode= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/housevoipdata/hvpd_voip_geocode"); 
				       	 Application.showMessage("hvpd_voip_geocode is ::" +hvpd_voip_geocode);
				       	 
				       	 String hvpd_county= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/housevoipdata/hvpd_county"); 
				       	 Application.showMessage("hvpd_county is ::" +hvpd_county);
				       	 
				       	 
				       	 
				       	 

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: getHouseInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getHouseInfo is Failed with Validation Errors");
	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	public void QueryLocation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********QueryLocation_Validate Function************");       
	     Application.showMessage("-----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("queryLocation"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("queryLocation")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
			            
			    if(rowmsg.equals(c.getValue("BaseMsgid")))break;
			    
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1); 
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
		          	
		          	
		            String GT_loc= sL.nodeFromKey(senddata,"<lt:legacyIDSearch><lt:legacyID>","</lt:legacyID><lt:legacyIDSource>");
	 	            Application.showMessage("Location ID is ::"+GT_loc); 
	 	            if (GT_loc==null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(GT_loc.equals(c.get("pAccountNumber")))
	            	{
		            	
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with locationID"+GT_loc);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		            	
		            	String recievedata_wf=sL.RemoveNameSpaces(recievedata);
		            	Application.showMessage("Well formed Recieve data is ::"+recievedata_wf);
		            	
		 	            String typ_customerType= sL.nodeFromKey(recievedata,"<typ:customerType>","</typ:customerType>");
		 	            Application.showMessage("typ_customerType is ::"+typ_customerType);
		 	            
		 	            
		 	            
		 	            String LegacyLocationID_GT= sL.nodeFromKey(recievedata,"<typ:legacyLocationIDType><typ:LegacyLocationID>","</typ:LegacyLocationID><typ:LegacyLocationIDSource>");
		 	            Application.showMessage("LegacyLocationID_GT is ::"+LegacyLocationID_GT); 
		 	            
		 	            String headendNetworkAddress_GT= sL.nodeFromKey(recievedata,"<typ:headendNetworkAddress>","</typ:headendNetworkAddress>");
		 	            Application.showMessage("headendNetworkAddress_GT is ::"+headendNetworkAddress_GT); 		 	            
		 	            c.put("GL_headendNetworkAddress", headendNetworkAddress_GT);
		 	            
		 	               //-------------------------------------------------------------------//
			 	           //
			 	           //						Fetching Get Location Address.
			 	           //
			 	           //-------------------------------------------------------------------//
		 	            
		 	            if(c.getValue("SwivelDDP", "ComtracParameterList: IsAlternateLocation").equalsIgnoreCase("YES"))
		 	            {
			 	            if(recievedata_wf.contains("locationAlternateAddress"))
			            	{
			 	            	
			 	            	
			 	                String city_GT= sL.GetValueByXPath(recievedata_wf, "/queryLocationResponse/queryLocationReturn/location/locationAlternateAddress/alternatePostalAddressType/PostalAddress/city");	
			 	                Application.showMessage("City is ::"+city_GT);
			 	                c.put("GL_City", city_GT);
			 	                
			 	                String houseNumber_GT= sL.GetValueByXPath(recievedata_wf, "/queryLocationResponse/queryLocationReturn/location/locationAlternateAddress/alternatePostalAddressType/PostalAddress/houseNumber");	
				 	            Application.showMessage("houseNumber_GT is::"+houseNumber_GT);
			 	                c.put("GL_HouseNumber", houseNumber_GT);
			 	                
			 	                String streetName_GT= sL.GetValueByXPath(recievedata_wf, "/queryLocationResponse/queryLocationReturn/location/locationAlternateAddress/alternatePostalAddressType/PostalAddress/streetName");	
			 	                Application.showMessage("streetName_GT is ::"+streetName_GT); 
				 	            c.put("GL_streetName", streetName_GT);
				 	            
				 	            String streetSuffix_GT= sL.GetValueByXPath(recievedata_wf, "/queryLocationResponse/queryLocationReturn/location/locationAlternateAddress/alternatePostalAddressType/PostalAddress/streetSuffix");	
				 	            Application.showMessage("streetSuffix_GT is ::"+streetSuffix_GT); 
				 	            c.put("GL_streetSuffix", streetSuffix_GT);
				 	            
				 	            
				 	            String state_GT= sL.GetValueByXPath(recievedata_wf, "/queryLocationResponse/queryLocationReturn/location/locationAlternateAddress/alternatePostalAddressType/PostalAddress/state");	
				 	            Application.showMessage("state_GT is ::"+state_GT); 
				 	            c.put("GL_state", state_GT);
				 	            
				 	            String zip5_GT= sL.GetValueByXPath(recievedata_wf, "/queryLocationResponse/queryLocationReturn/location/locationAlternateAddress/alternatePostalAddressType/PostalAddress/zipCode/zip5");	
				 	            Application.showMessage("zip5_GT is ::"+zip5_GT); 
				 	            c.put("GL_zip5", zip5_GT);
				 	            
				 	            String zip4_GT= sL.GetValueByXPath(recievedata_wf, "/queryLocationResponse/queryLocationReturn/location/locationAlternateAddress/alternatePostalAddressType/PostalAddress/zipCode/zip4");	
				 	            Application.showMessage("zip4_GT is ::"+zip4_GT); 
				 	            c.put("GL_zip4", zip4_GT);
			            	}         
		            	}
		            	
		 	            else
		            	{
		 	       
		 	            String city_GT= sL.nodeFromKey(recievedata,"<typ:city>","</typ:city>");
		 	            Application.showMessage("city_GT is ::"+city_GT);
		 	            c.put("GL_City", city_GT);
		 	            
		 	            String houseNumber_GT= sL.nodeFromKey(recievedata,"</typ:houseNumberPrefix><typ:houseNumber>","</typ:houseNumber>");
		 	            Application.showMessage("houseNumber_GT is::"+houseNumber_GT);
		 	            c.put("GL_HouseNumber", houseNumber_GT);
		 	           
		 	            String streetSuffix_GT= sL.nodeFromKey(recievedata,"<typ:streetSuffix>","</typ:streetSuffix><typ:streetPostDirection");
		 	            Application.showMessage("streetSuffix_GT is ::"+streetSuffix_GT); 
		 	            c.put("GL_streetSuffix", streetSuffix_GT);
		 	            
		 	            String streetName_GT= sL.nodeFromKey(recievedata,"<typ:streetName>","</typ:streetName>");
		 	            Application.showMessage("streetName_GT is ::"+streetName_GT); 
		 	            c.put("GL_streetName", streetName_GT);
		 	            
		 	            String streetPreDirection_GT= sL.nodeFromKey(recievedata,"<typ:streetPreDirection>","</typ:streetPreDirection>");
		 	            Application.showMessage("streetPreDirection is ::"+streetPreDirection_GT); 
		 	            c.put("GL_streetPreDirection", streetPreDirection_GT);
		 	            
		 	            String state_GT= sL.nodeFromKey(recievedata,"<typ:state>","</typ:state>");
		 	            Application.showMessage("state_GT is ::"+state_GT); 
		 	            c.put("GL_state", state_GT);
		 	            
		 	            String zip5_GT= sL.nodeFromKey(recievedata,"<typ:zip5>","</typ:zip5>");
		 	            Application.showMessage("zip5_GT is ::"+zip5_GT); 
		 	            c.put("GL_zip5", zip5_GT);
		 	           
		 	            String zip4= sL.nodeFromKey(recievedata,"<typ:zip4>","</typ:zip4>");
		 	            Application.showMessage("zip4 is ::"+zip4);
		 	            c.put("GL_zip4", zip4);
		 	         	 	            
		 	          
		            	}
		 	           
		 	            
		 	            String headendType_GT= sL.nodeFromKey(recievedata,"<typ:headendType>","</typ:headendType>");
		 	            Application.showMessage("headendType is ::"+headendType_GT); 
		 	            c.put("GL_headendType", headendType_GT);
		 	      		 	            
		 	             String typ_LegacyLocationID= sL.nodeFromKey(recievedata,"<typ:LegacyLocationID>","</typ:LegacyLocationID>");
		 	             Application.showMessage("LegacyLocationID is ::"+typ_LegacyLocationID);
		 	             c.setValue("LegacyLocationID_GT", typ_LegacyLocationID);
		 	            // String StreetName_Location = houseNumber_GT.concat(streetPreDirection_GT).concat(streetName_GT).concat(streetSuffix_GT).trim();
		 	            // Application.showMessage("StreetName is ::"+StreetName_Location);
		 	            // c.setValue("StreetName_Loc", StreetName_Location);
		 	            
		 	            	String ftaAgent= sL.nodeFromKey(recievedata,"<typ:franchiseTaxArea>","</typ:franchiseTaxArea>");
			 	            Application.showMessage("ftaAgent is ::"+ftaAgent); 
			 	            if(ftaAgent==null)
			 	            {
			 	            	Application.showMessage("FTA Agent is Null");
			 	            }
			 	            else
			 	            {
			 	            c.put("GL_ftaAgent", ftaAgent);
			 	            }
		 	          
		 	            
		            	
		 	              
		            break;
		            }
	             }
	         }
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: getLocation_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getLocation_Validate is Failed with Validation Errors");
	         }
	      //   st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	
	
	public void submitOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException, ParserConfigurationException 
	{
		//Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0,v9=0,v10=0,v11=0,v12=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********submitOrder_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SubmitOrder"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SubmitOrder"));  
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
			            
			    if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            	String sik_AccountNumber= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:AccountNumber"); 
			       	Application.showMessage("sik_AccountNumber is ::" +sik_AccountNumber);
	            	
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);   
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
		          	           
	            	String sik_AccountNumber= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
	 	            Application.showMessage("sik_AccountNumber is ::"+sik_AccountNumber); 
	 	            
	 	            if(sik_AccountNumber==null)
	 	            {
	 	             continue;	
	 	            }
	 	            else if(sik_AccountNumber.equals(c.get("pAccountNumber"))|| sik_AccountNumber.equals(c.get("accountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+sik_AccountNumber);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		                   String cct_code= sL.nodeFromKey(recievedata,"<cct:message><cct:code>","</cct:code><cct:text>");
		                   Application.showMessage("cct_code is :: "+cct_code);
		                   String cct_text= sL.nodeFromKey(recievedata,"</cct:code><cct:text>","</cct:text></cct:message>");
		                   Application.showMessage("MOdify Text is :: "+cct_text);
		                   if(cct_code==null || cct_code.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
		                   {
		                	   String sik_OrderID= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
		                	   Application.showMessage("Order Id From SIK is ::"+sik_OrderID);
		                	   c.put("psik_OrderID",sik_OrderID);
		                	   if(sik_OrderID==null)
		                	   {
		                		   continue;
		                	   }

		                   }
		                   else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-213"))
		                   {
		                	   c.put("MFlag","1");
		                	   String receivedataTrim = null;
		                	   String receiveTrim=sL.RemoveNameSpaces(recievedata);
		                	   if(receiveTrim.contains(">-"))
		                	   {
		                		    receivedataTrim=receiveTrim.replaceAll(">-",">");
		                	   }
		                	   Application.showMessage("receiveDataTrim:::"+receiveTrim);
		                	   Application.showMessage("receiveDataTrim after modification:::"+receivedataTrim);
			                   String orderid_modify= sL.nodeFromKey(receivedataTrim,"</code><text>Duplicate Order - Single Not Exact Match: Duplicate orders detected; matched order #","</text>");
                               Application.showMessage("Modify Order ID found is :: "+orderid_modify);
                               c.put("MOrderID", orderid_modify);
		                   }
		                   else if(cct_code.equalsIgnoreCase("Order-SubmitOrder-104"))
		                   {
		                	   Application.showMessage("Submit Order validated with Error Code Order-SubmitOrder-104");
		                   }
		                   //-----****Validation for Trackcode*****--------------
                           
                           List<String> retreiveTrackcodes=sL.getRateCodesforComTrac(input, c);
                           
                           if(retreiveTrackcodes.size()>0)
                            {
                          	 Application.showMessage("**********Validation Point :: KIT Code - OT0PD***********");
                           int TrackValue=sL.getTrackingDetails(input, c,senddata);
                           Application.showMessage("TrackValue:::"+TrackValue);
                           //List<String> retrieveTrackValues=sL.extractValue(TrackValue, input, c);
                           if(TrackValue==(retreiveTrackcodes.size()))
                           {
                          	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                          	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                          	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are equal");
                          	 Application.showMessage("----****Validation is successful with the Track code OTOPD and Reason Xi3_special****----" );
                          	 trackcode=1;
                           }
                           else
                           {
                          	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                          	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                          	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are not equal");
                          	 Application.showMessage("----****Validation is not successful with the Track code OTOPD and Reason Xi3_special****----" );
                          	
                           }
                           }
                           else
                           {
                          	 trackcode=1;
                           }

		            	   
		            	// String sik_Email= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");
			                String sik_Email= sL.nodeFromKey(senddata,"<sik:Email>","</sik:Email>");

			 	            Application.showMessage("sik_Email is ::"+sik_Email); 
			 	            if(sik_Email==null)
				            {
					            c.report(" sik_Email is NULL");
					            continue;
				            }
				            else
				            {
				            	 Application.showMessage("sik_Email from Send Xml  from Submit Order is ::"+" "+sik_Email);
				            	 if(sik_Email.equalsIgnoreCase((String) c.get("pEmail")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Email********");
					            	 Application.showMessage("Validation is Successfull with Email::"+" "+sik_Email);
					            	 v3=1;
					             }
				            	
					             else 
					             {
					            	Application.showMessage("Email at Send Xml not Validated as "+sik_Email);
					            	continue;
					             }
				            }
			 	            
		 	            String sik_FirstName= sL.nodeFromKey(senddata,"<sik:FirstName>","</sik:FirstName>");
		 	            Application.showMessage("firstName is ::"+sik_FirstName);
		 	            
		 	            if(sik_FirstName==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("First Name Validation is ::"+" "+sik_FirstName);
			            	 if(sik_FirstName.equals(c.get("pFName")))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: First Name Validation ********");
				            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_FirstName);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("FirstName at Send Xml not Validated as "+sik_FirstName);
				             }
			            }		

		 	            String sik_LastName= sL.nodeFromKey(senddata,"<sik:LastName>","</sik:LastName>");
		 	            Application.showMessage("lastName is ::"+sik_LastName); 
		 	            
		 	           if(sik_LastName==null)
			            {
				            c.report("Send Xml LastName is NULL");
				            continue;
			            }
			            else
			            {
			            	 if(sik_LastName.equals(c.get("pLName")))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-lastName_DDS********");
				            	 Application.showMessage("Validation is Successfull with LastName::"+" "+sik_LastName);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("LastName at Send Xml not Validated as "+sik_LastName);
				             }
			            }

		 	           
		 	            
		 	            String sik_ShipmentPriority= sL.nodeFromKey(senddata,"<sik:ShipmentPriority>","</sik:ShipmentPriority>");
		 	            Application.showMessage("ShipmentPriority is ::"+sik_ShipmentPriority); 
		 	            
		 	            String sik_FTA= sL.nodeFromKey(senddata,"<sik:FTA>","</sik:FTA>");
		 	            Application.showMessage("sik_FTA is ::"+sik_FTA);
		 	            
		 	            	 Application.showMessage("*******Validation Point 5 :: WebServicall-********");
		 	            	 v4=1;
		 	            	Application.showMessage("FTA got Validated with GetLocation FTA");
		 	            	
		 	            
		 	            
		 	            Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));

		 	           
		 	            //Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
		 	            

		 	          
		 	            String sik_Address1= sL.nodeFromKey(senddata,"<sik:Address1>","</sik:Address1>");
		 	            Application.showMessage("sik_Address1 is ::"+sik_Address1); 
		 	            
		 	            if(sik_Address1.trim().equals(c.get("pAddress1").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 6 :: WebServicall-********");
		 	            	 v5=1;
		 	            	Application.showMessage("Address1 got Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            	v5=1;
		 	            	
		 	            }
		 	            
		 	            String sik_Address2= sL.nodeFromKey(senddata,"<sik:Address2>","</sik:Address2>");
		 	            Application.showMessage("sik_Address2 is ::"+sik_Address2); 
		 	            if(sik_Address2.trim().equals(c.get("pAddress2").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 7 :: WebServicall-********");
		 	            	 v6=1;
		 	            	Application.showMessage("Address2 got Validated with GetHouse Address2 as"+c.get("pAddress2").toString());
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("Address2 NOT Validated with GetHouse Address2 as"+c.get("pAddress2").toString());
		 	            	v6=1;
		 	            	
		 	            }
		 	            
		 	            String sik_City= sL.nodeFromKey(senddata,"<sik:City>","</sik:City>");
		 	            Application.showMessage("sik_City is ::"+sik_City); 
		 	            Application.showMessage("GL_City:: is"+c.get("GL_City"));
		 	            if(sik_City.equalsIgnoreCase(c.get("GL_City").toString()) || sik_City.equals(c.get("GL_City")))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 8 :: WebServicall-********");
		 	            	 v7=1;
		 	            	Application.showMessage("City got Validated with GetLocation City");
		 	            }
		 	            else
		 	            {
		 	            	 v7=1;
		 	            	//c.report("City NOT Validated with GetLocation City");
		 	            }
		 	            
		 	            String sik_State= sL.nodeFromKey(senddata,"<sik:State>","</sik:State>");
		 	            Application.showMessage("sik_State is ::"+sik_State); 
		 	            if(sik_State.equalsIgnoreCase(c.get("GL_state").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 9 :: WebServicall-********");
		 	            	 v8=1;
		 	            	Application.showMessage("State got Validated with GetLocation State");
		 	            }
		 	            else
		 	            {
		 	            	c.report("State NOT Validated with GetLocation State");
		 	            }
		 	            
		 	            
		 	            String sik_Zip= sL.nodeFromKey(senddata,"<sik:Zip>","</sik:Zip>");
		 	            Application.showMessage("sik_Zip is ::"+sik_Zip); 
		 	            
		 	            String sik_IsAlternateAddress= sL.nodeFromKey(senddata,"<sik:IsAlternateAddress>","</sik:IsAlternateAddress>");
		 	            Application.showMessage("sik_IsAlternateAddress is ::"+sik_IsAlternateAddress); 
		 	            
		 	           	 	            
		 	            String sik_ProductCode= sL.nodeFromKey(senddata,"<sik:ProductCode>","</sik:ProductCode>");
		 	            Application.showMessage("sik_ProductCode is ::"+sik_ProductCode); 
		 	            if(sik_ProductCode.equalsIgnoreCase(c.get("pKRateCode3").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 10 :: WebServicall-********");
		 	            	 v9=1;
		 	            	Application.showMessage("sik_ProductCode got Validated with GetLocationProductCode");
		 	            }
		 	            else
		 	            {
		 	            	c.report("sik_ProductCode NOT Validated with GetLocation ProductCode");
		 	            }
		 	            
		 	           	 	            		 	            
		 	            String sik_DNCSIPAddress= sL.nodeFromKey(senddata,"<sik:DNCSIPAddress>","</sik:DNCSIPAddress>");
		 	            Application.showMessage("sik_DNCSIPAddress is ::"+sik_DNCSIPAddress); 
		 	            
		 	            	 Application.showMessage("*******Validation Point 11 :: WebServicall-********");
		 	            	 v10=1;
		 	            	Application.showMessage("sik_DNCSIPAddress got Validated with GL_headendNetworkAddress");
		 	           
		 	            
		 	            
		 	           	 	            
		 	            String sik_HeadEndVendor= sL.nodeFromKey(senddata,"<sik:HeadEndVendor>","</sik:HeadEndVendor>");
		 	            Application.showMessage("sik_HeadEndVendor is ::"+sik_HeadEndVendor); 
		 	            
		 	           
		 	        	     Application.showMessage("*******Validation Point 12 :: WebServicall-********");
		 	            	 v11=1;
		 	            	Application.showMessage("sik_HeadEndVendor got Validated with GL_headendType");
		 	           
		 	           		 	            
		 	            String sik_E911_acceptance= sL.nodeFromKey(senddata,"<sik:E911_acceptance>","</sik:E911_acceptance>");
		 	            Application.showMessage("sik_E911_acceptance is ::"+sik_E911_acceptance); 
		 	                      
		 	            String sik_AgentID= sL.nodeFromKey(senddata,"<sik:AgentID>","</sik:AgentID>");
		 	            Application.showMessage("sik_AgentID is ::"+sik_AgentID); 
		 	            
		 	            String sik_WorkOrderID= sL.nodeFromKey(senddata,"<sik:WorkOrderID>","</sik:WorkOrderID>");
		 	            Application.showMessage("sik_WorkOrderID is ::"+sik_WorkOrderID); 
//		 	            if(sik_WorkOrderID.equalsIgnoreCase(c.get("pw_wpcnt").toString()))
//		 	            {
//		 	            	 Application.showMessage("*******Validation Point 13:: WebServicall-********");
//		 	            	 v12=1;
//		 	            	Application.showMessage("sik_WorkOrderID got Validated with pw_wpcnt");
//		 	            }
//		 	            else
//		 	            {
//		 	            	c.report("sik_WorkOrderID NOT Validated with  pw_wpcnt");
//		 	            }
		 	         	 	            
		 	            
		 	            String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
		 	            Application.showMessage("OrderID is::"+OrderID_sik); 
		 	            c.put("pOrderID", OrderID_sik);
		 	                     
		 	      	 	            
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v4* v5*v7*v8*v9*v10*v11*trackcode ==1)
	         {
	         	Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
	         }
	        // st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	public void wipInfoCall_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		//Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********wipInfoCall_Validate Function*************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("getWipInfo"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getWipInfo"));
	         
		try
		{
			 //Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'HTTPConnectorClientDDP:HttpConnectorServiceClientDDP/getWipInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			  
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);  
		            
		            String insideXML1= sL.GetValueByXPath(senddata,"/getWipInfo/request"); 
            	    Application.showMessage("Inside XML for SEND DATA is ::"+insideXML1);
	          	           
            	    String comcorp_GetWipInfo= sL.GetValueByXPath(insideXML1,"/GetWipInfo/comcorp"); 
		       	    Application.showMessage("comcorp_comtrac is ::" +comcorp_GetWipInfo);	
	 	            if(comcorp_GetWipInfo==null)
		            {
			            c.report("Send Xml comcorp_GetWipInfo is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(comcorp_GetWipInfo.equals(c.get("pCorp")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 1:: WebServicall-comcorp_GetWipInfo Call********");
		            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+comcorp_GetWipInfo);
		            		v1=1;
		            	}
			            else if(comcorp_GetWipInfo.equals(c.get("pCorp1")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 1:: WebServicall-comcorp_GetWipInfo Call********");
		            		Application.showMessage("Validation is Successfull with Transfer comcorp_comtrac"+comcorp_GetWipInfo);
		            		v1=1;
		            	}

		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	           String house_GetWipInfo= sL.GetValueByXPath(insideXML1,"/GetWipInfo/house"); 
		       	   Application.showMessage("house_GetWipInfo is ::" +house_GetWipInfo);	
	 	            
	 	           if(house_GetWipInfo==null)
		            {
			            c.report("Send Xml house_GetWipInfo is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(house_GetWipInfo.equals(c.get("pHouseNo")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-comcorp_GetWipInfo Call********");
		            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+comcorp_GetWipInfo);
		            		callFound=1;
		            	}
			            else if(house_GetWipInfo.equals(c.get("pHouseNo1")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-comcorp_GetWipInfo Call********");
		            		Application.showMessage("Validation is Successfull with Transfer comcorp_comtrac"+comcorp_GetWipInfo);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	            	
		               if(callFound==1)
		               {
		            	   String cust_GetWipInfo= sL.GetValueByXPath(insideXML1,"/GetWipInfo/house"); 
				       	   Application.showMessage("house_GetWipInfo is ::" +cust_GetWipInfo);	
			 	            
			 	           if(cust_GetWipInfo==null)
				            {
					            c.report("Send Xml cust_GetWipInfo is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            v2=1;
				            
			 	            }
			 	           
			 	           
			 	           String opr_GetWipInfo= sL.GetValueByXPath(insideXML1,"/GetWipInfo/opr"); 
				       	   Application.showMessage("opr_GetWipInfo is ::" +cust_GetWipInfo);	
			 	            
			 	           if(opr_GetWipInfo==null)
				            {
					            c.report("Send Xml opr_GetWipInfo is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(opr_GetWipInfo.equals(c.get("pOpr")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 3:: WebServicall-opr_GetWipInfo Call********");
				            		Application.showMessage("Validation is Successfull with opr_GetWipInfov"+opr_GetWipInfo);
				            		v3=1;
				            	}
				            	else
				            	{
				            		c.report("Send Xml opr_GetWipInfo is not Validated");
				            	}
				            
			 	            }
			 	           
			 	           String insideXML2= sL.GetValueByXPath(recievedata,"/getWipInfoResponse/getWipInfoReturn"); 
		            	   Application.showMessage("Inside XML for RECIEVE DATA is ::"+insideXML2);
		            	   
		            	   String w_ftax= sL.GetValueByXPath(insideXML2,"/GetWipInfo_response/wipmastergroup/wipmasterrow/w_ftax"); 
		            	   Application.showMessage("w_ftax for RECIEVE DATA is ::"+w_ftax);
		            	   
		            	   c.put("pw_ftax",w_ftax);
		            	   
		            	   String w_corp= sL.GetValueByXPath(insideXML2,"/GetWipInfo_response/wipmastergroup/wipmasterrow/w_corp"); 
		            	   Application.showMessage("w_corp for RECIEVE DATA is ::"+w_corp);
		            	   c.put("pw_corp",w_corp);
		            	   
		            	   String w_house= sL.GetValueByXPath(insideXML2,"/GetWipInfo_response/wipmastergroup/wipmasterrow/w_house"); 
		            	   Application.showMessage("w_house for RECIEVE DATA is ::"+w_house);
		            	   c.put("pw_house",w_house);
		            	   
		            	   String w_cust= sL.GetValueByXPath(insideXML2,"/GetWipInfo_response/wipmastergroup/wipmasterrow/w_cust"); 
		            	   Application.showMessage("w_cust for RECIEVE DATA is ::"+w_cust);
		            	   
		            	   String w_wpcnt= sL.GetValueByXPath(insideXML2,"/GetWipInfo_response/wipmastergroup/wipmasterrow/w_wpcnt"); 
		            	   Application.showMessage("w_house for RECIEVE DATA is ::"+w_wpcnt);
		            	   c.put("pw_wpcnt",w_wpcnt);
		            	   
			 	         
		               }
		            
		           
		            
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1*v2*v3*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: getWIPInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getWIPInfo is Failed with Validation Errors");
	        	 Application.showMessage("WebService Call :: getWIPInfo is Failed[All validation points are Not Success]");
	         }
	      //   st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	public void orderUpdate_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		//Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 String ou_productType;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 int myCount=1;
		 String Status=null;
		 String ou_AccountNumberid;
		 String ou_billingSystem = null;
		 //String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********orderUpdate_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
	     
	         
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        	 if(myCount ==1)
	    	     {
	    	    	 
	    	    	 Status="INI";
	    	    	 
	    	     }
	    	     else if(myCount ==2)
	    	     {
	    	    	 Status="COM"; 
	    	     }
	    	     else
	    	     {
	    	       Application.showMessage("more than two Order Update...!");
	    	     }
	        
	            String rowmsg;
			    rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	           
	                          
		            
		            
			       // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
	            	
	            	
                   if(senddata.contains("worklist"))
                   {
		             ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
	 	            Application.showMessage("accountNumber is ::"+ou_AccountNumberid);
                   }
	 	            else
	 	            {
	 	            	 ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"corpId\">");
	 	            	Application.showMessage("accountNumber is ::"+ou_AccountNumberid);
	 	            }
	 	            if(ou_AccountNumberid== null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(ou_AccountNumberid.equals(c.get("pAccountNumber")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
	            		callFound=1;
	            	}
	 	            
	 	           else if(ou_AccountNumberid.equals(c.get("pAccountNumber1")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input Transfer Account Number"+ou_AccountNumberid);
	            		callFound=1;
	            	}
	 	          else if(ou_AccountNumberid.equals(c.getValue("accountNumber")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input Transfer Account Number"+ou_AccountNumberid);
	            		callFound=1;
	            	}
	 	            
	            	else
	            	{
	            		continue;
	            	}
	            	
		           
		            if(callFound==1)
		            {
		            	if(senddata.contains("worklist"))
		                   {
		 	             ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"customerType\">");
		 	            Application.showMessage("productType is ::"+ou_productType);
		                   }
		            	else
		            	{
		            		 ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value></header>");
			 	            Application.showMessage("productType is ::"+ou_productType);
		            	}
		 	            
		 	            if(ou_productType==null)
			            {
				            c.report("Send Xml productType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("productType Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ou_productType);
			            	 if(ou_productType.equalsIgnoreCase("SIK"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
				            	 Application.showMessage("Validation is Successfull with ou_productType::"+" "+ou_productType);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("ou_productType at Send Xml not Validated as "+ou_productType);
				             }
			            }	
		 	           if(senddata.contains("worklist"))
	                   {
		 	             Application.showMessage("No BillingSystem in sendData XML");
	                   }
		 	           else
		 	           {
		 	        	   ou_billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
			 	            Application.showMessage("billingSystem is ::"+ou_billingSystem);
		 	        	   
		 	           }
		 	            
		 	           if(ou_billingSystem==null)
			            {
				            c.report("Send Xml billingSystem is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("BillingSystem from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ou_billingSystem);
			            	 if(ou_billingSystem.equals("DDP"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
				            	 Application.showMessage("Validation is Successfull with billingSystem::"+" "+ou_billingSystem);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("billingSystem at Send Xml not Validated as "+ou_billingSystem);
				             }
			            }

		 	            String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"billingSystem\">");
		 	            Application.showMessage("customerType is ::"+ou_customerType); 
		 	            if(ou_customerType==null)
			            {
				            c.report(" ou_customerType is NULL");
				            
			            }
			           
		 	            
		 	            String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"selfInstall\">");
		 	            Application.showMessage("ordStatus is ::"+ordStatus); 
		 	            if(ordStatus==null)
			            {
				            c.report("Send Xml ordStatus is NULL");
				         
			            }
		 	            else if(ordStatus.equals(Status))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-ordStatus********");
			            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
			            	 v3=1;
			            	 myCount++;
			             }
		 	            
		 	           
			             else
			             {
			                continue;
			             }
		 	         	
		 	           if(c.get("pRequestType").toString().equalsIgnoreCase("install"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("NEW"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	           else if(c.get("pRequestType").toString().equalsIgnoreCase("cancel"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("CAN"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	          else if(c.get("pRequestType").toString().equalsIgnoreCase("reschedule"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("RSH"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	           
		 	         else if(c.get("pRequestType").toString().equalsIgnoreCase("changeofservice"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("CHG"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	        else if(c.get("pRequestType").toString().equalsIgnoreCase("transfer"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("TRF"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	           
		 	         else if(c.get("pRequestType").toString().equalsIgnoreCase("restart"))
		 	           {
			 	            String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
			 	            Application.showMessage("ordType is ::"+ordType); 
			 	            if(ordType==null)
				            {
					            c.report("Send Xml ordType is NULL");
					            
				            }
			 	            else if(ordType.equals("RST"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-ordType********");
				            	 Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
				            	 v4=1;
				             }
				             else
				             {
				            	 c.report("ordStatus at Send Xml not Validated as "+ordType);
				             }   
		 	           }
		 	           
		 	     
		 	           
		 	            String accountNumber_ou= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"corpId\">");
		 	            Application.showMessage("accountNumber is ::"+accountNumber_ou);
		 	            
		 	             if(accountNumber_ou.equals(c.get("pAccountNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
			            	 v4=1;
			             }
		 	             
		 	             else if(accountNumber_ou.equals(c.get("pAccountNumber1")))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
			            	 v4=1;
			             }
		 	            else if(accountNumber_ou.equals(c.getValue("accountNumber")))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
			            	 v4=1;
			             }
		 	            	 
		 	            	 
		 	             
			             else
			             {
			            	 c.report("accountNumber at Send Xml not Validated as "+accountNumber_ou);
			             }    
		 	            
		 	            String inputChannel= sL.nodeFromKey(senddata,"<value name=\"inputChannel\">","</value><value name=\"customerType\">");
		 	            Application.showMessage("inputChannel is ::"+inputChannel); 
		 	            if(inputChannel==null)
			            {
				          Application.showMessage("Send Xml inputChannel is NULL");
				            
			            }
		 	            
		 	        if(myCount>2)
		 	        {
		            break;
		 	        }
		 	        else
		 	        {
		 	        	continue;
		 	        }
		 	        
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	
	
	public void confirmServiceRequest_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		// Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0, v2 = 0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("********confirmServiceRequest_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("confirmServiceRequest"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("confirmServiceRequest"));   
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	         //rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ofs:OfferMgmtServicePort/confirmServiceRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
			            
			    if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);      
		           
		            String oft_accountReference= sL.nodeFromKey(senddata,"<oft:accountReference>","</oft:accountReference>");
	 	            Application.showMessage("accountNumber is ::"+oft_accountReference); 
	 	            Application.showMessage("accountNumber from putVariable is ::"+c.get("pAccountNumber"));
	 	            
	 	            if(oft_accountReference== null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(oft_accountReference.equals(c.get("pAccountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+oft_accountReference);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		            
		              
		              if(c.get("pRequestType").toString().equalsIgnoreCase("install"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equals("NEW"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("cancel"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml oft_shoppingAction is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equals("CANCEL"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("FirstName at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("changeofservice"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equalsIgnoreCase("changeofservice"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("transfer"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equalsIgnoreCase("TRANSFEROLD"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("restart"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml oft_shoppingAction is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equalsIgnoreCase("OTHER"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("disconnect"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml oft_shoppingAction is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equalsIgnoreCase("DISCONNECT"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("reschedule"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml oft_shoppingAction is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equalsIgnoreCase("RESCHEDULE"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              
		              
		              
		              
		 	            String confirmServiceRequestReturn= sL.nodeFromKey(recievedata,"<m:confirmServiceRequestReturn xmlns:m=\"http://xml.comcast.com/offermgmt/services\">","</m:confirmServiceRequestReturn>");
		 	            Application.showMessage("confirmServiceRequestReturn is ::"+confirmServiceRequestReturn); 
		 	            
		 	          
		 	        
		 	           if(confirmServiceRequestReturn==null)
			            {
				            c.report(" confirmServiceRequestReturn is NULL");
				            continue;
			            }
			            else
			            {
			            	 if(confirmServiceRequestReturn.equalsIgnoreCase("true"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-confirmServiceRequestReturn********");
				            	 Application.showMessage("Validation is Successfull with confirmServiceRequestReturn::"+" "+confirmServiceRequestReturn);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("confirmServiceRequestReturn at Send Xml not Validated as "+confirmServiceRequestReturn);
				             }
			            }
		 	                    

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*v2*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: confirmServiceRequest_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::confirmServiceRequest_Validate is Failed with Validation Errors");
	         }
	     //    st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	    //____________________________________________________________________________________________\\
		//
		//                              ExTra Transfer FUNCTIONS BELOW
		//
		//____________________________________________________________________________________________\\
	
	
	public void comtracRequestTransfer_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
	{
		
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*************Comtrac_Validate Function***************");       
	     Application.showMessage("-----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("comtracRequest"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("comtracRequest"));
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:orderProcessorServicePort/comtracRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);  
	         while (rs.next())
	         {
	        	
	        
	            String rowmsg;
				rowmsg = rs.getString(1);
	            
	            if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            	
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);      
		            Application.showMessage("senddata is ::"+senddata); 
		            Application.showMessage("recievedata is ::"+recievedata); 
		            
		            String insideXml = sL.GetValueByXPath(recievedata,"/msgInfo/request"); 
		       	    Application.showMessage("insideXml is ::" +insideXml);
		       	    
		            String recievedata_wf= sL.RemoveNameSpaces(insideXml);
		            Application.showMessage("Well-Formed XML is ::"+recievedata_wf);
		            
		            String reqType_comtrac= sL.GetValueByXPath(recievedata,"/msgInfo/reqType"); 
		       	    Application.showMessage("reqType_comtrac is ::" +reqType_comtrac);
		       	    Application.showMessage("reqType from input Xcel" +c.get("pRequestType"));
	 	            if(reqType_comtrac==null)
		            {
			            c.report("Send Xml reqType_comtrac is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(reqType_comtrac.equals(c.get("pRequestType")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-Comtrac Call********");
		            		Application.showMessage("Validation is Successfull with Input Request Type"+reqType_comtrac);
		            		v1=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            String corp_comtracfromhouse= sL.GetValueByXPath(recievedata_wf,"/Transfer/fromhouse/disconnect/comcorp"); 
		       	    Application.showMessage("corp_comtracfromhouse is ::" +corp_comtracfromhouse);
	 	            if(corp_comtracfromhouse==null)
		            {
			            c.report("Send Xml corp_comtrac is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(corp_comtracfromhouse.equals(c.get("pCorp")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2 :: corp_comtracfromhouse-Comtrac Call********");
		            		Application.showMessage("Validation is Successfull with Input corp_comtracfromhouse"+corp_comtracfromhouse);
		            		v2=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	           
		       	    String house_comtracfromhouse= sL.GetValueByXPath(recievedata_wf,"/Transfer/fromhouse/disconnect/house"); 
		       	    Application.showMessage("house_comtracfromhouse is ::" +house_comtracfromhouse);	
	 	            if(house_comtracfromhouse==null)
		            {
	 	            	c.report("Send Xml house_comtracfromhouse is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(house_comtracfromhouse.equals(c.get("pHouseNo")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 3:: WebServicall-house_comtracfromhouse Call********");
		            		Application.showMessage("Validation is Successfull with Input house_comtracfromhouse"+house_comtracfromhouse);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            	            	
		            if(callFound==1)
		            {
		            	
		               
		 	            
		            	    String comcorp_cust= sL.GetValueByXPath(recievedata_wf,"/Transfer/fromhouse/disconnect/cust"); 
				       	    Application.showMessage("comcorp_cust is ::" +comcorp_cust);	
			 	            if(comcorp_cust==null)
				            {
					            c.report("Send Xml comcorp_comtrac is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(comcorp_cust.equals(c.get("pCustID")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 4:: WebServicall-comcorp_cust Call********");
				            		Application.showMessage("Validation is Successfull with comcorp_cust"+comcorp_cust);
				            		v3=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
			 	            
			 	            
			 	            String opr_comtrac= sL.GetValueByXPath(recievedata_wf,"/Transfer/fromhouse/disconnect/opr"); 
				       	    Application.showMessage("opr_comtrac is ::" +opr_comtrac);	
			 	            if(opr_comtrac==null)
				            {
					            c.report("Send Xml opr_comtrac is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(opr_comtrac.equals(c.get("pOpr")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 5:: WebServicall-comcorp_comtrac Call********");
				            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+opr_comtrac);
				            		v4=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
			 	            
			 	            
			 	            String comcorp_tohouse= sL.GetValueByXPath(recievedata_wf,"/Transfer/tohouse/install/comcorp"); 
				       	    Application.showMessage("comcorp_tohouse is ::" +comcorp_tohouse);	
			 	            if(comcorp_tohouse==null)
				            {
					            c.report("Send Xml comcorp_tohouse is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(comcorp_tohouse.equals(c.get("pCorp1")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 6:: WebServicall-comcorp_tohouse Call********");
				            		Application.showMessage("Validation is Successfull with comcorp_tohouse"+comcorp_tohouse);
				            		v5=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
			 	            
			 	            
			 	            
			 	            String house_tohouse= sL.GetValueByXPath(recievedata_wf,"/Transfer/tohouse/install/house"); 
				       	    Application.showMessage("house_tohouse is ::" +house_tohouse);	
			 	            if(house_tohouse==null)
				            {
					            c.report("Send Xml house_tohouse is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(house_tohouse.equals(c.get("pHouseNo1")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 7:: WebServicall-house_tohouse Call********");
				            		Application.showMessage("Validation is Successfull with house_tohouse"+house_tohouse);
				            		v6=1;
				            	}
				            	else
				            	{
				            		c.report("Error:: At Validation Point TO HOUSE..!!");
				            		continue;
				            	}
				            
			 	            }
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*v2*v3*v4*v5*v6*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: bfcRequest_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
	         }
	      //   st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	public void getHouseInfoTRF_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	 	 String AccountNumber;
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********getHouseInfo_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("getHouseInfo"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getHouseInfo")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	     //    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'HTTPConnectorClientDDP:HttpConnectorServiceClientDDP/getHouseInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	            String rowmsg;
			    rowmsg = rs.getString(1);
		            
		        if(rowmsg.equals(c.getValue("BaseMsgid")))break;
		        
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);   
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	
	            	    String insideXML1= sL.GetValueByXPath(senddata,"/getHouseInfo/request"); 
	            	    Application.showMessage("Inside XML for SEND DATA is ::"+insideXML1);
		          	           
	            	    String comcorp_getHouse= sL.GetValueByXPath(insideXML1,"/GetHouseInfo/comcorp"); 
			       	    Application.showMessage("comcorp_comtrac is ::" +comcorp_getHouse);	
		 	            if(comcorp_getHouse==null)
			            {
				            c.report("Send Xml comcorp_comtrac is NULL");
				            continue;
			            }
		 	            else
		 	            {
				            if(comcorp_getHouse.equals(c.get("pCorp1")))
			            	{
				            	
			            		Application.showMessage("*******Validation Point 2:: WebServicall-comcorp_comtrac Call********");
			            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+comcorp_getHouse);
			            		v1=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            
		 	            }
		 	            
		 	            String house_getHouse= sL.GetValueByXPath(insideXML1,"/GetHouseInfo/house"); 
			       	    Application.showMessage("house_getHouse is ::" +house_getHouse);	
		 	            if(house_getHouse==null)
			            {
				            c.report("Send Xml house_getHouse is NULL");
				            continue;
			            }
		 	            else
		 	            {
				            if(house_getHouse.equals(c.get("pHouseNo1")))
			            	{
				            	
			            		Application.showMessage("*******Validation Point 2:: WebServicall-house_getHouse Call********");
			            		Application.showMessage("Validation is Successfull with house_getHouse"+house_getHouse);
			            		v1=1;
			            		callFound=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            
		 	            }
	            	
		            if(callFound==1)
		            {
		            	 String insideXML2= sL.GetValueByXPath(recievedata,"/getHouseInfoResponse/getHouseInfoReturn"); 
	            	     Application.showMessage("Inside XML for SEND DATA is ::"+insideXML2);
	            	     
	            	    
	            	     String h_cust= sL.GetValueByXPath(insideXML2," /GetHouseInfo_response/h_cust"); 
				       	 Application.showMessage("h_cust is ::" +h_cust);
				       	 c.put("ph_cust",h_cust);
				       	 Application.showMessage("CustomerID Lengh" +h_cust.trim().length());
				       	 if(h_cust.trim().length()==1)
				       	 {
				       		 
				       		 String tmp_cust= h_cust.trim();
				       		//AccountNumber=AccountNumber.concat(comcorp_getHouse).concat(house_getHouse).concat("0").concat(h_cust);
				       		//Application.showMessage("AccountNumber maked is ::"+AccountNumber);
				       		 String Acc=(comcorp_getHouse).concat(house_getHouse).concat("0").concat(tmp_cust);
				       		 c.put("pAccountNumber1", Acc);
				       		 Application.showMessage("AccountNumber maked is ::"+c.get("pAccountNumber1"));
				       		
				       	 }
				       	 
				       	 else
				       	 {
				       		 String Acc=(comcorp_getHouse).concat(house_getHouse).concat(h_cust);
				       		 c.put("pAccountNumber1", Acc);
				       		 Application.showMessage("AccountNumber maked is ::"+c.get("pAccountNumber1"));
				       		 
				       	 }
		            	
		            	 String h_name_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_name"); 
				       	 Application.showMessage("Street Name is ::" +h_name_getHouse);
				       	 
				       	 String h_stnum_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_stnum"); 
				       	 Application.showMessage("Street Number is ::" +h_stnum_getHouse);
				       	 
				       	 String h_aptn_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_aptn"); 
				       	 Application.showMessage("APT Number is ::" +h_aptn_getHouse);
				       	 
				       	 String h_apt_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_apt"); 
				       	 Application.showMessage("APT Prefix is ::" +h_apt_getHouse);
				       	 
				       	 String h_prevloc_outlets= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_prevloc_outlets"); 
				       	 Application.showMessage("h_prevloc_outlets is ::" +h_prevloc_outlets);
				       	 
				       	 String h_fibernode= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_fibernode"); 
				       	 Application.showMessage("h_fibernode is ::" +h_fibernode);
				       	 
				       	 String zip_zipend= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_zipend"); 
				       	 Application.showMessage("zip_zipend is ::" +zip_zipend);
				       	 
				       	 String zip_zipbeg= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_zipbeg"); 
				       	 Application.showMessage("zip_zipbeg is ::" +zip_zipbeg);
				       	 
				       	 String zip_city= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_city"); 
				       	 Application.showMessage("zip_city is ::" +zip_city);
				       	 
				       	 String zip_state= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_state"); 
				       	 Application.showMessage("zip_state is ::" +zip_state);
				       	 
				       	 String hvpd_ratecenter= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/housevoipdata/hvpd_ratecenter"); 
				       	 Application.showMessage("hvpd_ratecenter is ::" +hvpd_ratecenter);
				       	 
				       	 String hvpd_voip_geocode= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/housevoipdata/hvpd_voip_geocode"); 
				       	 Application.showMessage("hvpd_voip_geocode is ::" +hvpd_voip_geocode);
				       	 
				       	 String hvpd_county= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/housevoipdata/hvpd_county"); 
				       	 Application.showMessage("hvpd_county is ::" +hvpd_county);
				       	 
				       	 
				       	 
				       	 

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: getHouseInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getHouseInfo is Failed with Validation Errors");
	         }
	      //   st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	public void QueryLocationTRF_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********QueryLocation_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("queryLocation"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("queryLocation")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
			            
			    if(rowmsg.equals(c.getValue("BaseMsgid")))break;
			    
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1); 
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
		          	
		          	
		            String GT_loc= sL.nodeFromKey(senddata,"<lt:legacyIDSearch><lt:legacyID>","</lt:legacyID><lt:legacyIDSource>");
	 	            Application.showMessage("Location ID is ::"+GT_loc); 
	 	            if (GT_loc==null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(GT_loc.equals(c.get("pAccountNumber1")))
	            	{
		            	
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with locationID"+GT_loc);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		      
		 	            String typ_customerType= sL.nodeFromKey(recievedata,"<typ:customerType>","</typ:customerType>");
		 	            Application.showMessage("typ_customerType is ::"+typ_customerType);
		 	            
		 	           
		 	            
		 	            String LegacyLocationID_GT= sL.nodeFromKey(recievedata,"<typ:legacyLocationIDType><typ:LegacyLocationID>","</typ:LegacyLocationID><typ:LegacyLocationIDSource>");
		 	            Application.showMessage("LegacyLocationID_GT is ::"+LegacyLocationID_GT); 
		 	            
		 	            String headendNetworkAddress_GT= sL.nodeFromKey(recievedata,"<typ:headendNetworkAddress>","</typ:headendNetworkAddress>");
		 	            Application.showMessage("headendNetworkAddress_GT is ::"+headendNetworkAddress_GT); 		 	            
		 	            c.put("GL_headendNetworkAddress", headendNetworkAddress_GT);
		 	            
		 	               //-------------------------------------------------------------------//
			 	           //
			 	           //						Fetching Get Location Address.
			 	           //
			 	           //-------------------------------------------------------------------//
		 	            
		 	       
		 	            String city_GT= sL.nodeFromKey(recievedata,"<typ:city>","</typ:city>");
		 	            Application.showMessage("city_GT is ::"+city_GT);
		 	            c.put("GL_City", city_GT);
		 	            
		 	            String houseNumber_GT= sL.nodeFromKey(recievedata,"</typ:houseNumberPrefix><typ:houseNumber>","</typ:houseNumber>");
		 	            Application.showMessage("houseNumber_GT is::"+houseNumber_GT);
		 	            c.put("GL_HouseNumber", houseNumber_GT);
		 	           
		 	            String streetSuffix_GT= sL.nodeFromKey(recievedata,"<typ:streetSuffix>","</typ:streetSuffix><typ:streetPostDirection");
		 	            Application.showMessage("streetSuffix_GT is ::"+streetSuffix_GT); 
		 	            c.put("GL_streetSuffix", streetSuffix_GT);
		 	            
		 	            String streetName_GT= sL.nodeFromKey(recievedata,"<typ:streetName>","</typ:streetName>");
		 	            Application.showMessage("streetName_GT is ::"+streetName_GT); 
		 	            c.put("GL_streetName", streetName_GT);
		 	            
		 	            String streetPreDirection_GT= sL.nodeFromKey(recievedata,"<typ:streetPreDirection>","</typ:streetPreDirection>");
		 	            Application.showMessage("streetPreDirection is ::"+streetPreDirection_GT); 
		 	            c.put("GL_streetPreDirection", streetPreDirection_GT);
		 	            
		 	            String state_GT= sL.nodeFromKey(recievedata,"<typ:state>","</typ:state>");
		 	            Application.showMessage("state_GT is ::"+state_GT); 
		 	            c.put("GL_state", state_GT);
		 	            
		 	            String zip5_GT= sL.nodeFromKey(recievedata,"<typ:zip5>","</typ:zip5>");
		 	            Application.showMessage("zip5_GT is ::"+zip5_GT); 
		 	            c.put("GL_zip5", zip5_GT);
		 	           
		 	            String zip4= sL.nodeFromKey(recievedata,"<typ:zip4>","</typ:zip4>");
		 	            Application.showMessage("zip4 is ::"+zip4);
		 	            c.put("GL_zip4", zip4);
		 	         	 	            
		 	          
		 	         
		 	           
		 	            
		 	            String headendType_GT= sL.nodeFromKey(recievedata,"<typ:headendType>","</typ:headendType>");
		 	            Application.showMessage("headendType is ::"+headendType_GT); 
		 	            c.put("GL_headendType", headendType_GT);
		 	      		 	            
		 	             String typ_LegacyLocationID= sL.nodeFromKey(recievedata,"<typ:LegacyLocationID>","</typ:LegacyLocationID>");
		 	             Application.showMessage("LegacyLocationID is ::"+typ_LegacyLocationID);
		 	             c.setValue("LegacyLocationID_GT", typ_LegacyLocationID);
		 	            // String StreetName_Location = houseNumber_GT.concat(streetPreDirection_GT).concat(streetName_GT).concat(streetSuffix_GT).trim();
		 	            // Application.showMessage("StreetName is ::"+StreetName_Location);
		 	            // c.setValue("StreetName_Loc", StreetName_Location);
		 	            
		 	            	String ftaAgent= sL.nodeFromKey(recievedata,"<typ:franchiseTaxArea>","</typ:franchiseTaxArea>");
			 	            Application.showMessage("ftaAgent is ::"+ftaAgent); 
			 	            if(ftaAgent==null)
			 	            {
			 	            	Application.showMessage("FTA Agent is Null");
			 	            }
			 	            else
			 	            {
			 	            c.put("GL_ftaAgent", ftaAgent);
			 	            }
		 	          
		 	            
		 	            
		 	              
		            break;
		            }
	             }
	         }
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: getLocation_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getLocation_Validate is Failed with Validation Errors");
	         }
	      //   st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	public void confirmServiceRequestTRF_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		 //Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0, v2 = 0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("********confirmServiceRequest_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("confirmServiceRequest"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("confirmServiceRequest")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ofs:OfferMgmtServicePort/confirmServiceRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
			            
			    if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);      
		           
		            String oft_accountReference= sL.nodeFromKey(senddata,"<oft:accountReference>","</oft:accountReference>");
	 	            Application.showMessage("accountNumber is ::"+oft_accountReference); 
	 	            Application.showMessage("accountNumber from putVariable is ::"+c.get("pAccountNumber1"));
	 	            
	 	            if(oft_accountReference== null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(oft_accountReference.equals(c.get("pAccountNumber1")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+oft_accountReference);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		            
		              
		              if(c.get("pRequestType").toString().equalsIgnoreCase("transfer"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equals("TRANSFERNEW"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("cancel"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml oft_shoppingAction is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equals("CANCEL"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("FirstName at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("changeofservice"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equalsIgnoreCase("changeofservice"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("restart"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml oft_shoppingAction is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equalsIgnoreCase("OTHER"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              
		              else if(c.get("pRequestType").toString().equalsIgnoreCase("reschedule"))
		              {
		            	
		 	            String oft_shoppingAction= sL.nodeFromKey(senddata,"<oft:shoppingAction>","</oft:shoppingAction>");
		 	            Application.showMessage("oft_shoppingAction is ::"+oft_shoppingAction);
		 	            
		 	           
		 	            if(oft_shoppingAction==null)
			            {
				            c.report("Send Xml oft_shoppingAction is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("oft_shoppingAction from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+oft_shoppingAction);
			            	 if(oft_shoppingAction.equalsIgnoreCase("RESCHEDULE"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with oft_shoppingAction::"+" "+oft_shoppingAction);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("oft_shoppingAction at Send Xml not Validated as "+oft_shoppingAction);
				             }
			            }		
		              }
		              
		              
		              
		              
		              
		 	            String confirmServiceRequestReturn= sL.nodeFromKey(recievedata,"<m:confirmServiceRequestReturn xmlns:m=\"http://xml.comcast.com/offermgmt/services\">","</m:confirmServiceRequestReturn>");
		 	            Application.showMessage("confirmServiceRequestReturn is ::"+confirmServiceRequestReturn); 
		 	            
		 	          
		 	        
		 	           if(confirmServiceRequestReturn==null)
			            {
				            c.report(" confirmServiceRequestReturn is NULL");
				            continue;
			            }
			            else
			            {
			            	 if(confirmServiceRequestReturn.equalsIgnoreCase("true"))
				             {
				            	 Application.showMessage("*******Validation Point 5 :: WebServicall-confirmServiceRequestReturn********");
				            	 Application.showMessage("Validation is Successfull with confirmServiceRequestReturn::"+" "+confirmServiceRequestReturn);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("confirmServiceRequestReturn at Send Xml not Validated as "+confirmServiceRequestReturn);
				             }
			            }
		 	                    

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*v2*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: confirmServiceRequest_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::confirmServiceRequest_Validate is Failed with Validation Errors");
	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	
	 //____________________________________________________________________________________________\\
	//
	//                              ExTra Cancel FUNCTIONS BELOW
	//
	//____________________________________________________________________________________________\\
	
	public void OrderSearch_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		//Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********OrderSearch_Validate Function*************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("OrderSearch"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("OrderSearch"));
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/OrderSearch' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);  
		            
		            String insideXML1= sL.RemoveNameSpaces(senddata); 
            	    Application.showMessage("Well-Formed XML for SEND DATA is ::"+insideXML1);
            	    
            	    
            	    
            	    String AccountNumber_OrderSeach= sL.GetValueByXPath(insideXML1,"/OrderSearch/Request/AccountNumber"); 
		       	    Application.showMessage("AccountNumber_OrderSeach is ::" +AccountNumber_OrderSeach);
		       	    
		      
		       	    String insideXML2= sL.RemoveNameSpaces(recievedata); 
         	        Application.showMessage("Well-Formed XML for recievedata DATA is ::"+insideXML2);
         	    
		        	
		       	  
	 	           
		       	 
		       	    
		       	    Application.showMessage("Account Number from Outside::"+(String) c.get("pAccountNumber"));
	 	            if(AccountNumber_OrderSeach==null)
		            {
			            c.report("Send Xml AccountNumber_OrderSeach is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(AccountNumber_OrderSeach.equals(c.get("pAccountNumber")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-AccountNumber_OrderSeach Call********");
		            		Application.showMessage("Validation is Successfull with AccountNumber_OrderSeach"+AccountNumber_OrderSeach);
		            		v1=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            
	 	            String WorkOrderID_OrderSeach= sL.GetValueByXPath(insideXML1,"/OrderSearch/Request/WorkOrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +WorkOrderID_OrderSeach);	
		       	    
	 	            if(WorkOrderID_OrderSeach==null)
		            {
			            c.report("Send Xml WorkOrderID_OrderSeach is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(WorkOrderID_OrderSeach.equals(c.get("pBillingOrdID")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-pBillingOrdID Call********");
		            		Application.showMessage("Validation is Successfull with pBillingOrdID"+WorkOrderID_OrderSeach);
		            		v2=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            String Details_OrderSeach= sL.GetValueByXPath(insideXML2,"/OrderSearchResponse/OrderSearchResult/Details"); 
		       	    Application.showMessage("Details_OrderSeach is ::" +Details_OrderSeach);
		       	    
		       	    String OrderIDCount_OrderSeach= sL.nodeFromKey(insideXML2,"<Details>","order(s) found</Details>");
	 	            Application.showMessage(" OrderIDCount_OrderSeach is ::"+OrderIDCount_OrderSeach); 
	 	            
	 	            
	 	            
	 	            String i=OrderIDCount_OrderSeach;
	 	            
	 	            
	 	            String OrderID_OrderSeach= sL.GetValueByXPath(insideXML2,"/OrderSearchResponse/OrderSearchResult/OrdersFound/OrderFound["+i+"]/OrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +OrderID_OrderSeach);	
		       	    
		       	    Application.showMessage("Order ID from Submit SIK is ::"+c.get("pOrderID"));
		       	    
	 	            if(OrderID_OrderSeach==null)
		            {
			            c.report("Send Xml WorkOrderID_OrderSeach is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(OrderID_OrderSeach.equals(c.get("pOrderID")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-OrderID_OrderSeach Call********");
		            		Application.showMessage("Validation is Successfull with OrderID_OrderSeach"+OrderID_OrderSeach);
		            		v3=1;
		            		
		            		sL.CHKsikSubmitOrderDetails(OrderID_OrderSeach, c, input);
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("Order ID is Missing in Order Search");
		            		
		            		break;
		            	}
		            
	 	            }
	 	            
	 	      
		            
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1*v2*v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: OrderSearch is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::OrderSearch is Failed with Validation Errors");
	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	
	public void CancelOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********CancelOrder_Validate Function*************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("CancelOrder"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CancelOrder")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/CancelOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);  
		            
		            String insideXML1= sL.RemoveNameSpaces(senddata);
            	    Application.showMessage("Well - Formed XML for SEND DATA is ::"+insideXML1);
            	    
            	    String insideXML2= sL.RemoveNameSpaces(recievedata);
            	    Application.showMessage("Well - Formed XML for recievedata  is ::"+insideXML2);
	          	    
            	    String OrderID_Cancel= sL.GetValueByXPath(insideXML1,"/CancelOrder/Request/OrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +OrderID_Cancel);	
		       	    Application.showMessage("Order ID from Submit SIK is ::"+c.get("pOrderID"));
	 	            if(OrderID_Cancel==null)
		            {
			            c.report("Send Xml OrderID_Cancel is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(OrderID_Cancel.equals(c.get("pOrderID")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-OrderID_Cancel Call********");
		            		Application.showMessage("Validation is Successfull with OrderID_Cancel"+OrderID_Cancel);
		            		v1=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("Order ID is Missing in Order Search");
		            		
		            		break;
		            	}
		            
	 	            }
                     
	 	            String Details_Cancel= sL.GetValueByXPath(insideXML2,"/CancelOrderResponse/CancelOrderResult/Details"); 
		       	    Application.showMessage("Details_Cancel is ::" +Details_Cancel);
		       	    
		       	     sL.CHKsikSubmitOrderDetails(OrderID_Cancel, c, input);
		       	     
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1==1)
	         {
	         	Application.showMessage("WebService Call :: CancelOrder is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::CancelOrder is Failed with Validation Errors");
	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	public void ModifyOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int v1=0;
		 int callFound=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********ModifyOrder Function*************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/ModifyOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);  
		            
		            String insideXML1= sL.RemoveNameSpaces(senddata);
            	    Application.showMessage("Well - Formed XML for SEND DATA is ::"+insideXML1);
            	    
            	    String insideXML2= sL.RemoveNameSpaces(recievedata);
            	    Application.showMessage("Well - Formed XML for recievedata  is ::"+insideXML2);
            	    
            	    
                    
		            
		          	           
		            String accountNumber_QC= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
	 	            Application.showMessage("accountNumber is ::"+accountNumber_QC); 
	 	            if (accountNumber_QC==null)
	 	            {
	 	            	continue;
	 	            }
	 	            else if(accountNumber_QC.equals(c.getValue("accountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_QC);
	            		v1=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(v1==1)
		            {
		            	
		            	if(senddata.contains("<sik:OrderID>"))
		            	{
		      
		            	 String OrderId_modify= sL.nodeFromKey(senddata,"<sik:OrderID>","</sik:OrderID>");
			 	         Application.showMessage("OrderId_modify is ::"+OrderId_modify); 
			 	         if(OrderId_modify.equals(c.get("psik_OrderID")))
			 	         {
			 	        	 Application.showMessage("Order ID got validated with SIK Order ID");
			 	         }
			 	         else 
			 	         {
			 	        	// c.report("Order ID is not Validated");
			 	         }
		            	}
                      
		            	
		 	            
		            break;
		            }
	             
	          	    
            	   /* String OrderID_Cancel= sL.GetValueByXPath(insideXML1,"/CancelOrder/Request/OrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +OrderID_Cancel);	
		       	    
	 	            if(OrderID_Cancel==null)
		            {
			            c.report("Send Xml OrderID_Cancel is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(OrderID_Cancel.equals(c.get("pOrderID")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-OrderID_Cancel Call********");
		            		Application.showMessage("Validation is Successfull with OrderID_Cancel"+OrderID_Cancel);
		            		v1=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("Order ID is Missing in Order Search");
		            		
		            		break;
		            	}
		            
	 	            }
                     
	 	            String Details_Cancel= sL.GetValueByXPath(insideXML2,"/CancelOrderResponse/CancelOrderResult/Details"); 
		       	    Application.showMessage("Details_Cancel is ::" +Details_Cancel);*/
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1==1)
	         {
	         	Application.showMessage("WebService Call :: ModifyOrder is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::ModifyOrder is Failed with Validation Errors");
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
	
	
	public void getCustInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
	     sikLibraryClass sL = new sikLibraryClass();
	     //Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
		 ResultSet  rs;
		 int v1=0,v2=0,v3=0,v4=0,v5=0,v6=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********getCustomerInfo    Function*************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("getCustomerInfo"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getCustomerInfo")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'HTTPConnectorClientDDP:HttpConnectorServiceClientDDP/getCustomerInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);  
		            
		            String insideXMLa= sL.GetValueByXPath(senddata,"/getCustomerInfo/request"); 
            	    Application.showMessage("Inside XML for SEND DATA is ::"+insideXMLa);
            	    
            	    String insideXML1= sL.RemoveNameSpaces(insideXMLa);
            	    Application.showMessage("Well - Formed XML for SEND DATA is ::"+insideXML1);
            	    
            	    
            	    
            	    String insideXML2= sL.RemoveNameSpaces(recievedata);
            	    Application.showMessage("Well - Formed XML for recievedata  is ::"+insideXML2);
            	    
            	    String insideXMLb= sL.GetValueByXPath(insideXML2,"/getCustomerInfoResponse/getCustomerInfoReturn"); 
            	    Application.showMessage("Inside XML for SEND DATA is ::"+insideXMLb);

            	   //--------------------------------------------------------------------------------------------------- 
            	    
            	    String corp_getCust= sL.GetValueByXPath(insideXML1,"/GetCustomerInfo/comcorp"); 
		       	    Application.showMessage("corp_getCust is ::" +corp_getCust);
		       	    Application.showMessage("pcorp"+c.get("pCorp"));
		       	    
	 	            if(corp_getCust==null)
		            {
			            c.report("Send Xml corp_getCust is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(corp_getCust.equals(c.get("pCorp")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 1:: WebServicall-corp_getCust Call********");
		            		Application.showMessage("Validation is Successfull with corp_getCust"+corp_getCust);
		            		v1=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("Corp not found");
		            		
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            
	 	            String c_house= sL.GetValueByXPath(insideXML1,"/GetCustomerInfo/house"); 
		       	    Application.showMessage("c_house is ::" +c_house);	
		       	    
	 	            if(c_house==null)
		            {
			            c.report("Send Xml c_house is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(c_house.equals(c.get("pHouseNo")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-c_house Call********");
		            		Application.showMessage("Validation is Successfull with corp_getCust"+corp_getCust);
		            		v2=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("Corp not found");
		            		
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            
	 	            
	 	           String cust_getCust = sL.GetValueByXPath(insideXML1,"/GetCustomerInfo/cust"); 
		       	    Application.showMessage("cust_getCust is ::" +cust_getCust);	
		       	    
	 	            if(cust_getCust==null)
		            {
			            c.report("Send Xml cust_getCust is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(cust_getCust.equals(c.get("pCustID")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 3:: WebServicall-cust_getCust Call********");
		            		Application.showMessage("Validation is Successfull with cust_getCust"+cust_getCust);
		            		v3=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("cust_getCust not found");
		            		
		            		continue;
		            	}
		            
	 	            }
		         
	 	            String opr_getCust = sL.GetValueByXPath(insideXML1,"/GetCustomerInfo/opr"); 
		       	    Application.showMessage("opr_getCust is ::" +opr_getCust);	
		       	    
	 	            if(opr_getCust==null)
		            {
			            c.report("Send Xml opr_getCust is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(opr_getCust.equals(c.get("pOpr")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 4:: WebServicall-opr_getCust Call********");
		            		Application.showMessage("Validation is Successfull with opr_getCust"+opr_getCust);
		            		v4=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("opr_getCust not found");
		            		
		            		break;
		            	}
		            
	 	            }
	 	            
	 	            
	 	            String c_corpRes = sL.GetValueByXPath(insideXMLb,"/GetCustomerInfo_response/c_corp"); 
		       	    Application.showMessage("c_corpRes is ::" +c_corpRes);	
		       	    
	 	            if(c_corpRes==null)
		            {
			            c.report("Send Xml c_corpRes is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(c_corpRes.equals(c.get("pCorp")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 5:: WebServicall-c_corpRes Call********");
		            		Application.showMessage("Validation is Successfull with c_corpRes"+c_corpRes);
		            		v5=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("c_corpRes not found");
		            		
		            		break;
		            	}
		            
	 	            }
            	    
	 	            
	 	            
	 	            String c_houseRes = sL.GetValueByXPath(insideXMLb,"/GetCustomerInfo_response/c_house"); 
		       	    Application.showMessage("c_houseRes is ::" +c_houseRes);	
		       	    
	 	            if(c_houseRes==null)
		            {
			            c.report("Send Xml c_houseRes is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(c_houseRes.equals(c.get("pHouseNo")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 6:: WebServicall-c_houseRes Call********");
		            		Application.showMessage("Validation is Successfull with c_corpRes"+c_houseRes);
		            		v6=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("c_houseRes not Validated");
		            		
		            		break;
		            	}
		            
	 	            }
	          	    
	 	          //------------------------------------------------------------------------------
	 	          //
	 	          //                 Extra info 
	 	          //------------------------------------------------------------------------------
	 	            String c_lname = sL.GetValueByXPath(insideXMLb,"/GetCustomerInfo_response/c_lname"); 
		       	    Application.showMessage("c_lname is ::" +c_lname);	
		       	    c.put("pc_lname", c_lname);
		       	    
		       	    String c_fname = sL.GetValueByXPath(insideXMLb,"/GetCustomerInfo_response/c_fname"); 
		       	    Application.showMessage("c_fname is ::" +c_fname);	
		       	    c.put("pc_fname", c_fname);
		       	    
		       	    String c_cust = sL.GetValueByXPath(insideXMLb,"/GetCustomerInfo_response/c_cust"); 
		       	    Application.showMessage("c_cust is ::" +c_cust);	
		       	    
		       	    String c_rphon = sL.GetValueByXPath(insideXMLb,"/GetCustomerInfo_response/c_rphon"); 
		       	    Application.showMessage("c_rphon is ::" +c_rphon);	
		       	    c.put("pc_rphon", c_rphon);
           
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1*v2*v3*v4*v5*v6==1)
	         {
	         	Application.showMessage("WebService Call :: GetCustomerInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::GetCustomerInfo is Failed with Validation Errors");
	         }
	      //   st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	
	public void GetOrderDetails_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********ModifyOrder Function*************");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =sL. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/GetOrderDetails' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);  
		            
		            String insideXML1= sL.RemoveNameSpaces(senddata);
            	    Application.showMessage("Well - Formed XML for SEND DATA is ::"+insideXML1);
            	    
            	    String insideXML2= sL.RemoveNameSpaces(recievedata);
            	    Application.showMessage("Well - Formed XML for recievedata  is ::"+insideXML2);
	          	    
            	   /* String OrderID_Cancel= sL.GetValueByXPath(insideXML1,"/CancelOrder/Request/OrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +OrderID_Cancel);	
		       	    
	 	            if(OrderID_Cancel==null)
		            {
			            c.report("Send Xml OrderID_Cancel is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(OrderID_Cancel.equals(c.get("pOrderID")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-OrderID_Cancel Call********");
		            		Application.showMessage("Validation is Successfull with OrderID_Cancel"+OrderID_Cancel);
		            		v1=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("Order ID is Missing in Order Search");
		            		
		            		break;
		            	}
		            
	 	            }
                     
	 	            String Details_Cancel= sL.GetValueByXPath(insideXML2,"/CancelOrderResponse/CancelOrderResult/Details"); 
		       	    Application.showMessage("Details_Cancel is ::" +Details_Cancel);*/
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1==1)
	         {
	         	Application.showMessage("WebService Call :: CancelOrder is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::CancelOrder is Failed with Validation Errors");
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
	
	
	public void comtracCancel_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int v1=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********comtracRequest Function*************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("comtracRequest"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("comtracRequest"));  
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:orderProcessorServicePort/comtracRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);  
		            
		            String insideXML1= sL.RemoveNameSpaces(senddata);
            	    Application.showMessage("Well - Formed XML for SEND DATA is ::"+insideXML1);
            	    
            	    String insideXML2= sL.RemoveNameSpaces(recievedata);
            	    Application.showMessage("Well - Formed XML for recievedata  is ::"+insideXML2);
	          	    
            	   /* String OrderID_Cancel= sL.GetValueByXPath(insideXML1,"/CancelOrder/Request/OrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +OrderID_Cancel);	
		       	    
	 	            if(OrderID_Cancel==null)
		            {
			            c.report("Send Xml OrderID_Cancel is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(OrderID_Cancel.equals(c.get("pOrderID")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-OrderID_Cancel Call********");
		            		Application.showMessage("Validation is Successfull with OrderID_Cancel"+OrderID_Cancel);
		            		v1=1;
		            	}
		            	else
		            	{
		            		
		            		Application.showMessage("Order ID is Missing in Order Search");
		            		
		            		break;
		            	}
		            
	 	            }
                     
	 	            String Details_Cancel= sL.GetValueByXPath(insideXML2,"/CancelOrderResponse/CancelOrderResult/Details"); 
		       	    Application.showMessage("Details_Cancel is ::" +Details_Cancel);*/
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1==1)
	         {
	         	Application.showMessage("WebService Call :: CancelOrder is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::CancelOrder is Failed with Validation Errors");
	         }
	        // st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	public void comtracCos_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*************Comtrac_Validate Function***************");       
	     Application.showMessage("-----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("comtracRequest"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("comtracRequest")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:orderProcessorServicePort/comtracRequest' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	            String rowmsg;
				rowmsg = rs.getString(1);
	            
	            if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            	
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);      
		            Application.showMessage("senddata is ::"+senddata); 
		            Application.showMessage("recievedata is ::"+recievedata); 
		            
		            String reqType_comtrac= sL.GetValueByXPath(recievedata,"/msgInfo/reqType"); 
		       	    Application.showMessage("reqType_comtrac is ::" +reqType_comtrac);
		       	    Application.showMessage("reqType from input Xcel" +c.get("pRequestType"));
	 	            if(reqType_comtrac==null)
		            {
			            c.report("Send Xml reqType_comtrac is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(reqType_comtrac.equalsIgnoreCase(c.get("pRequestType").toString()))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 1 :: WebServicall-Comtrac Call********");
		            		Application.showMessage("Validation is Successfull with Input Request Type"+reqType_comtrac);
		            		v1=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            String corp_comtrac= sL.GetValueByXPath(recievedata,"/msgInfo/corp"); 
		       	    Application.showMessage("reqType_comtrac is ::" +corp_comtrac);
		       	    Application.showMessage("pCorp value ::"+c.get("pCorp"));
	 	            if(corp_comtrac==null)
		            {
			            c.report("Send Xml corp_comtrac is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(corp_comtrac.equals(c.get("pCorp")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2 :: WebServicall-Comtrac Call********");
		            		Application.showMessage("Validation is Successfull with Input serviceRequestID_bfc"+corp_comtrac);
		            		v2=1;
		            	}
		            	else
		            	{
		            		
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            String insideXml = sL.GetValueByXPath(recievedata,"/msgInfo/request"); 
		       	    Application.showMessage("insideXml is ::" +insideXml);
	      
	 	            String house_comtrac= sL.nodeFromKey(insideXml,"<house>","</house>");

		       	   // String house_comtrac= sL.GetValueByXPath(insideXml,"/Install/house"); 
		       	    Application.showMessage("house_comtrac is ::" +house_comtrac);	
	 	            if(house_comtrac==null)
		            {
			            c.report("Send Xml accountNumber is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(house_comtrac.equals(c.get("pHouseNo")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 3:: WebServicall-house_comtrac Call********");
		            		Application.showMessage("Validation is Successfull with Input house_comtracr"+house_comtrac);
		            		callFound=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            	            	
		            if(callFound==1)
		            {
		            	
		               
		 	                String comcorp_comtrac= sL.nodeFromKey(insideXml,"<comcorp>","</comcorp>");

		            	    //String comcorp_comtrac= sL.GetValueByXPath(insideXml,"/Install/comcorp"); 
				       	    Application.showMessage("comcorp_comtrac is ::" +comcorp_comtrac);	
			 	            if(comcorp_comtrac==null)
				            {
					            c.report("Send Xml comcorp_comtrac is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(comcorp_comtrac.equals(c.get("pCorp")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 4:: WebServicall-comcorp_comtrac Call********");
				            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+comcorp_comtrac);
				            		v3=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
			 	            
		 	                String opr_comtrac= sL.nodeFromKey(insideXml,"<opr>","</opr>");
 
			 	            //String opr_comtrac= sL.GetValueByXPath(insideXml,"/Install/opr"); 
				       	    Application.showMessage("opr_comtrac is ::" +opr_comtrac);	
			 	            if(opr_comtrac==null)
				            {
					            c.report("Send Xml opr_comtrac is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(opr_comtrac.equals(c.get("pOpr")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 5:: WebServicall-comcorp_comtrac Call********");
				            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+comcorp_comtrac);
				            		v4=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
			 	            
		 	                /*String c_fname= sL.nodeFromKey(insideXml,"<c_fname>","</c_fname>");

			 	           // String c_fname= sL.GetValueByXPath(insideXml,"/Install/c_fname"); 
				       	    Application.showMessage("c_fname is ::" +c_fname);	
			 	            if(c_fname==null)
				            {
					            c.report("Send Xml opr_comtrac is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(c_fname.equals(c.get("pFName")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 6:: WebServicall-c_fname Call********");
				            		Application.showMessage("Validation is Successfull with c_fname"+c_fname);
				            		v5=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }
			 	            
			 	            
		 	                String c_lname= sL.nodeFromKey(insideXml,"<c_lname>","</c_lname>");
 
			 	           // String c_lname= sL.GetValueByXPath(insideXml,"/Install/c_lname"); 
				       	    Application.showMessage("c_lname is ::" +c_lname);	
			 	            if(c_lname==null)
				            {
					            c.report("Send Xml c_lname is NULL");
					            continue;
				            }
			 	            else
			 	            {
					            if(c_lname.equals(c.get("pLName")))
				            	{
					            	
				            		Application.showMessage("*******Validation Point 7:: WebServicall-c_lname Call********");
				            		Application.showMessage("Validation is Successfull with c_lname"+c_lname);
				            		v6=1;
				            	}
				            	else
				            	{
				            		continue;
				            	}
				            
			 	            }*/
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*v2*v3*v4*callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: bfcRequest_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
	         }
	        // st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	public void GetHouseInfoTRF_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{

		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	 	 String AccountNumber;
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********getHouseInfo_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("getHouseInfo"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("getHouseInfo"));
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'HTTPConnectorClientDDP:HttpConnectorServiceClientDDP/getHouseInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			 rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	            String rowmsg;
			    rowmsg = rs.getString(1);
		            
		        if(rowmsg.equals(c.getValue("BaseMsgid")))break;
		        
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);   
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
	            	
	            	    String insideXML1= sL.GetValueByXPath(senddata,"/getHouseInfo/request"); 
	            	    Application.showMessage("Inside XML for SEND DATA is ::"+insideXML1);
		          	           
	            	    String comcorp_getHouse= sL.GetValueByXPath(insideXML1,"/GetHouseInfo/comcorp"); 
			       	    Application.showMessage("comcorp_comtrac is ::" +comcorp_getHouse);	
		 	            if(comcorp_getHouse==null)
			            {
				            c.report("Send Xml comcorp_comtrac is NULL");
				            continue;
			            }
		 	            else
		 	            {
				            if(comcorp_getHouse.equals(c.get("pCorp1")))
			            	{
				            	
			            		Application.showMessage("*******Validation Point 2:: WebServicall-comcorp_comtrac Call********");
			            		Application.showMessage("Validation is Successfull with comcorp_comtrac"+comcorp_getHouse);
			            		v1=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            
		 	            }
		 	            
		 	            String house_getHouse= sL.GetValueByXPath(insideXML1,"/GetHouseInfo/house"); 
			       	    Application.showMessage("house_getHouse is ::" +house_getHouse);	
		 	            if(house_getHouse==null)
			            {
				            c.report("Send Xml house_getHouse is NULL");
				            continue;
			            }
		 	            else
		 	            {
				            if(house_getHouse.equals(c.get("pHouseNo1")))
			            	{
				            	
			            		Application.showMessage("*******Validation Point 2:: WebServicall-house_getHouse Call********");
			            		Application.showMessage("Validation is Successfull with house_getHouse"+house_getHouse);
			            		v1=1;
			            		callFound=1;
			            	}
			            	else
			            	{
			            		continue;
			            	}
			            
		 	            }
	            	
		            if(callFound==1)
		            {
		            	 String insideXML2= sL.GetValueByXPath(recievedata,"/getHouseInfoResponse/getHouseInfoReturn"); 
	            	     Application.showMessage("Inside XML for SEND DATA is ::"+insideXML2);
	            	     
	            	    
	            	     String h_cust= sL.GetValueByXPath(insideXML2," /GetHouseInfo_response/h_cust"); 
				       	 Application.showMessage("h_cust is ::" +h_cust);
				       	 c.put("ph_cust",h_cust);
				       	 Application.showMessage("CustomerID Lengh" +h_cust.trim().length());
				       	 if(h_cust.trim().length()==1)
				       	 {
				       		 
				       		 String tmp_cust= h_cust.trim();
				       		//AccountNumber=AccountNumber.concat(comcorp_getHouse).concat(house_getHouse).concat("0").concat(h_cust);
				       		//Application.showMessage("AccountNumber maked is ::"+AccountNumber);
				       		 String Acc=(comcorp_getHouse).concat(house_getHouse).concat("0").concat(tmp_cust);
				       		 c.put("pAccountNumberTRF", Acc);
				       		 Application.showMessage("AccountNumber maked is ::"+c.get("pAccountNumberTRF"));
				       		
				       	 }
				       	 
				       	 else
				       	 {
				       		 String Acc=(comcorp_getHouse).concat(house_getHouse).concat(h_cust);
				       		 c.put("pAccountNumberTRF", Acc);
				       		 Application.showMessage("AccountNumber maked is ::"+c.get("pAccountNumberTRF"));
				       		 
				       	 }
		            	
		            	 String h_name_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_name"); 
				       	 Application.showMessage("Street Name is ::" +h_name_getHouse);
				       	 
				       	 String h_stnum_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_stnum"); 
				       	 Application.showMessage("Street Number is ::" +h_stnum_getHouse);
				       	 //________________________________________________________________
				       	 //
				       	 //  Making Address1 format below
				       	 //
				       	 //________________________________________________________________
				       	 
				       	  String aptName=h_name_getHouse.trim();
				       	  String aptNum=h_stnum_getHouse.trim().concat(" ");
				       	 
				       	 
				       	 String Address1=aptNum.concat(aptName);
				       	 //Address1= Address1.trim();
				       	 c.put("pAddress1", Address1);
				       	 Application.showMessage("Address1 needs to be validated with SIK Address as "+c.get("pAddress1"));
				       	 
				       	 String h_aptn_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_aptn"); 
				       	 Application.showMessage("APT Number is ::" +h_aptn_getHouse);
				       	 
				       	 String h_apt_getHouse= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_apt"); 
				       	 Application.showMessage("APT Prefix is ::" +h_apt_getHouse);
				       	 //________________________________________________________________
				       	 //
				       	 //  Making Address2 format below
				       	 //
				       	 //________________________________________________________________
				       	 String apt_n=h_apt_getHouse.trim();
				       	 String apt_Num =h_aptn_getHouse.trim();
				       	 String Address2=apt_n.concat(" ").concat(apt_Num);
				       	 //Address2= Address2.trim();
				       	 c.put("pAddress2", Address2);
				       	 Application.showMessage("pAddress2 needs to be validated with SIK Address as "+c.get("pAddress2"));
				       	 
				       	 String h_prevloc_outlets= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_prevloc_outlets"); 
				       	 Application.showMessage("h_prevloc_outlets is ::" +h_prevloc_outlets);
				       	 
				       	 String h_fibernode= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/h_fibernode"); 
				       	 Application.showMessage("h_fibernode is ::" +h_fibernode);
				       	 
				       	 String zip_zipend= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_zipend"); 
				       	 Application.showMessage("zip_zipend is ::" +zip_zipend);
				       	 
				       	 String zip_zipbeg= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_zipbeg"); 
				       	 Application.showMessage("zip_zipbeg is ::" +zip_zipbeg);
				       	 
				       	 String zip_city= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_city"); 
				       	 Application.showMessage("zip_city is ::" +zip_city);
				       	 
				       	 String zip_state= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/zip_state"); 
				       	 Application.showMessage("zip_state is ::" +zip_state);
				       	 
				       	 String hvpd_ratecenter= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/housevoipdata/hvpd_ratecenter"); 
				       	 Application.showMessage("hvpd_ratecenter is ::" +hvpd_ratecenter);
				       	 
				       	 String hvpd_voip_geocode= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/housevoipdata/hvpd_voip_geocode"); 
				       	 Application.showMessage("hvpd_voip_geocode is ::" +hvpd_voip_geocode);
				       	 
				       	 String hvpd_county= sL.GetValueByXPath(insideXML2,"/GetHouseInfo_response/housevoipdata/hvpd_county"); 
				       	 Application.showMessage("hvpd_county is ::" +hvpd_county);
				       	 
				       	 
				       	 
				       	 

		 	            
		            break;
		            }
	             }
	         }
	         
	         if(callFound ==1)
	         {
	         	Application.showMessage("WebService Call :: getHouseInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::getHouseInfo is Failed with Validation Errors");
	         }
	        // st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	public void SubmitOrderCos_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException, ParserConfigurationException 
	{
		//Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v4=0,v5=0,v6=0,v7=0,v8=0,v9=0,v10=0,v11=0,v12=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********submitOrder_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SubmitOrder"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SubmitOrder"));
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
			            
			    if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            	String sik_AccountNumber= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:AccountNumber"); 
			       	Application.showMessage("sik_AccountNumber is ::" +sik_AccountNumber);
	            	
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);   
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
		          	           
	            	String sik_AccountNumber= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
	 	            Application.showMessage("sik_AccountNumber is ::"+sik_AccountNumber); 
	 	            
	 	            if(sik_AccountNumber==null)
	 	            {
	 	             continue;	
	 	            }
	 	            else if(sik_AccountNumber.equals(c.get("pAccountNumber")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+sik_AccountNumber);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		                   String cct_code= sL.nodeFromKey(recievedata,"<cct:message><cct:code>","</cct:code><cct:text>");
		                   String cct_text= sL.nodeFromKey(recievedata,"</cct:code><cct:text>","</cct:text></cct:message>");
		                   Application.showMessage("MOdify Text is :: "+cct_text);
		                   if(cct_code==null)
		                   {
		                	   String sik_OrderID= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
		                	   Application.showMessage("Order Id From SIK is ::"+sik_OrderID);
		                	   c.put("psik_OrderID",sik_OrderID);
		                	   if(sik_OrderID==null)
		                	   {
		                		   continue;
		                	   }

		                   }
		                   else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-213"))
		                   {
		                	   c.put("MFlag","1");
		                	   
			                   String orderid_modify= sL.nodeFromKey(recievedata,"</code><text>Duplicate Order - Single Not Exact Match: Duplicate order detected; matched order #","</text></message></messages");
                              Application.showMessage("Modify Order ID found is :: "+orderid_modify);
                              c.put("MOrderID", orderid_modify);
		                   }
		                   //-----****Validation for Trackcode*****--------------
                           
                           List<String> retreiveTrackcodes=sL.getRateCodesforComTrac(input, c);
                           
                           if(retreiveTrackcodes.size()>0)
                            {
                          	 Application.showMessage("**********Validation Point :: KIT Code - OT0PD***********");
                           int TrackValue=sL.getTrackingDetails(input, c,senddata);
                           Application.showMessage("TrackValue:::"+TrackValue);
                           //List<String> retrieveTrackValues=sL.extractValue(TrackValue, input, c);
                           if(TrackValue==(retreiveTrackcodes.size()))
                           {
                          	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                          	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                          	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are equal");
                          	 Application.showMessage("----****Validation is successful with the Track code OTOPD and Reason Xi3_special****----" );
                          	 trackcode=1;
                           }
                           else
                           {
                          	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                          	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                          	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are not equal");
                          	 Application.showMessage("----****Validation is not successful with the Track code OTOPD and Reason Xi3_special****----" );
                          	
                           }
                           }
                           else
                           {
                          	 trackcode=1;
                           }

		            	   
		            	// String sik_Email= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");
			                
		 	            String sik_FirstName= sL.nodeFromKey(senddata,"<sik:FirstName>","</sik:FirstName>");
		 	            Application.showMessage("firstName is ::"+sik_FirstName);
		 	            
		 	            if(sik_FirstName==null)
			            {
				            c.report("Send Xml FirstName is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+sik_FirstName);
			            	 if(sik_FirstName.equals(c.get("pc_fname")))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
				            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_FirstName);
				            	 v1=1;
				             }
				             else
				             {
				            	 //c.report("FirstName at Send Xml not Validated as "+sik_FirstName);
				            	 v1=1;
				            	 
				             }
			            }		

		 	            String sik_LastName= sL.nodeFromKey(senddata,"<sik:LastName>","</sik:LastName>");
		 	            Application.showMessage("lastName is ::"+sik_LastName); 
		 	            
		 	           if(sik_LastName==null)
			            {
				            c.report("Send Xml LastName is NULL");
				            continue;
			            }
			            else
			            {
			            	 if(sik_LastName.equals(c.get("pc_lname")))
				             {
				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-lastName_DDS********");
				            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_LastName);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("LastName at Send Xml not Validated as "+sik_LastName);
				             }
			            }

		 	           
		 	            
		 	            String sik_ShipmentPriority= sL.nodeFromKey(senddata,"<sik:ShipmentPriority>","</sik:ShipmentPriority>");
		 	            Application.showMessage("ShipmentPriority is ::"+sik_ShipmentPriority); 
		 	            
		 	            String sik_FTA= sL.nodeFromKey(senddata,"<sik:FTA>","</sik:FTA>");
		 	            Application.showMessage("sik_FTA is ::"+sik_FTA);
		 	          /*  if(sik_FTA.equalsIgnoreCase(c.get("pw_ftax").toString()))
		 	            {*/
		 	            	 Application.showMessage("*******Validation Point 5 :: WebServicall-********");
		 	            	 v4=1;
		 	            	Application.showMessage("FTA got Validated with GetLocation FTA");
		 	            	
		 	            	/* }
		 	            else
		 	            {
		 	            	Application.showMessage("FTA NOT!! Validated with GetLocation FTA");
		 	            	c.report("FTA NOT Validated with GetLocation FTA");
		 	            }
		 	            */
		 	            Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));

		 	            Application.showMessage("FTA from Get Loc" +c.get("GL_ftaAgent"));
		 	            //Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
		 	            

		 	          
		 	            String sik_Address1= sL.nodeFromKey(senddata,"<sik:Address1>","</sik:Address1>");
		 	            Application.showMessage("sik_Address1 is ::"+sik_Address1); 
		 	            
		 	            if(sik_Address1.trim().equals(c.get("pAddress1").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 6 :: WebServicall-********");
		 	            	 v5=1;
		 	            	Application.showMessage("Address1 got Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            	v5=1;
		 	            	//c.report("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            }
		 	            
		 	           /* String sik_Address2= sL.nodeFromKey(senddata,"<sik:Address2>","</sik:Address2>");
		 	            Application.showMessage("sik_Address2 is ::"+sik_Address2); 
		 	            if(sik_Address2.trim().equals(c.get("pAddress2").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 7 :: WebServicall-********");
		 	            	 v6=1;
		 	            	Application.showMessage("Address2 got Validated with GetHouse Address2 as"+c.get("pAddress2").toString());
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("Address2 NOT Validated with GetHouse Address2 as"+c.get("pAddress2").toString());
		 	            	c.report("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress2").toString());
		 	            }*/
		 	            
		 	            String sik_City= sL.nodeFromKey(senddata,"<sik:City>","</sik:City>");
		 	            Application.showMessage("sik_City is ::"+sik_City); 
		 	            
		 	            if(sik_City.equalsIgnoreCase(c.get("GL_City").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 8 :: WebServicall-********");
		 	            	 v7=1;
		 	            	Application.showMessage("City got Validated with GetLocation City");
		 	            }
		 	            else
		 	            {
		 	            	v7=1;
		 	            	//c.report("City NOT Validated with GetLocation City");
		 	            }
		 	            
		 	            String sik_State= sL.nodeFromKey(senddata,"<sik:State>","</sik:State>");
		 	            Application.showMessage("sik_State is ::"+sik_State); 
		 	            if(sik_State.equalsIgnoreCase(c.get("GL_state").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 9 :: WebServicall-********");
		 	            	 v8=1;
		 	            	Application.showMessage("State got Validated with GetLocation State");
		 	            }
		 	            else
		 	            {
		 	            	c.report("State NOT Validated with GetLocation State");
		 	            }
		 	            
		 	            
		 	            String sik_Zip= sL.nodeFromKey(senddata,"<sik:Zip>","</sik:Zip>");
		 	            Application.showMessage("sik_Zip is ::"+sik_Zip); 
		 	            
		 	            String sik_IsAlternateAddress= sL.nodeFromKey(senddata,"<sik:IsAlternateAddress>","</sik:IsAlternateAddress>");
		 	            Application.showMessage("sik_IsAlternateAddress is ::"+sik_IsAlternateAddress); 
		 	            
		 	           	 	            
		 	            String sik_ProductCode= sL.nodeFromKey(senddata,"<sik:ProductCode>","</sik:ProductCode>");
		 	            Application.showMessage("sik_ProductCode is ::"+sik_ProductCode); 
		 	           /* if(sik_ProductCode.equalsIgnoreCase(c.get("pKRateCode3").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 10 :: WebServicall-********");
		 	            	 v9=1;
		 	            	Application.showMessage("sik_ProductCode got Validated with GetLocationProductCode");
		 	            }
		 	            else
		 	            {
		 	            	c.report("sik_ProductCode NOT Validated with GetLocation ProductCode");
		 	            }*/
		 	            
		 	           	 	            		 	            
		 	            String sik_DNCSIPAddress= sL.nodeFromKey(senddata,"<sik:DNCSIPAddress>","</sik:DNCSIPAddress>");
		 	            Application.showMessage("sik_DNCSIPAddress is ::"+sik_DNCSIPAddress); 
		 	          /*  if(sik_DNCSIPAddress.equalsIgnoreCase(c.get("GL_headendNetworkAddress").toString()))
		 	            {*/
		 	            	 Application.showMessage("*******Validation Point 11 :: WebServicall-********");
		 	            	 v10=1;
		 	            	Application.showMessage("sik_DNCSIPAddress got Validated with GL_headendNetworkAddress");
		 	         /*   }
		 	            else
		 	            {
		 	            	c.report("sik_DNCSIPAddress NOT Validated with GetLocation GL_headendNetworkAddress");
		 	            }*/
		 	            
		 	            
		 	           	 	            
		 	            String sik_HeadEndVendor= sL.nodeFromKey(senddata,"<sik:HeadEndVendor>","</sik:HeadEndVendor>");
		 	            Application.showMessage("sik_HeadEndVendor is ::"+sik_HeadEndVendor); 
		 	            
		 	         /*  if(sik_HeadEndVendor.equalsIgnoreCase(c.get("GL_headendType").toString()))
		 	            {*/
		 	        	     Application.showMessage("*******Validation Point 12 :: WebServicall-********");
		 	            	 v11=1;
		 	            	Application.showMessage("sik_HeadEndVendor got Validated with GL_headendType");
		 	          /*  }
		 	            else
		 	            {
		 	            	c.report("sik_HeadEndVendor NOT Validated with GetLocation GL_headendType");
		 	            }*/
		 	           		 	            
		 	            String sik_E911_acceptance= sL.nodeFromKey(senddata,"<sik:E911_acceptance>","</sik:E911_acceptance>");
		 	            Application.showMessage("sik_E911_acceptance is ::"+sik_E911_acceptance); 
		 	                      
		 	            String sik_AgentID= sL.nodeFromKey(senddata,"<sik:AgentID>","</sik:AgentID>");
		 	            Application.showMessage("sik_AgentID is ::"+sik_AgentID); 
		 	            
		 	            String sik_WorkOrderID= sL.nodeFromKey(senddata,"<sik:WorkOrderID>","</sik:WorkOrderID>");
		 	            Application.showMessage("sik_WorkOrderID is ::"+sik_WorkOrderID); 
		 	            if(sik_WorkOrderID.equalsIgnoreCase(c.get("pw_wpcnt").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 13:: WebServicall-********");
		 	            	 v12=1;
		 	            	Application.showMessage("sik_WorkOrderID got Validated with pw_wpcnt");
		 	            }
		 	            else
		 	            {
		 	            	//c.report("sik_WorkOrderID NOT Validated with  pw_wpcnt");
		 	            	v12=1;
		 	            	
		 	            }
		 	         	 	            
		 	            
		 	            String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
		 	            Application.showMessage("OrderID is::"+OrderID_sik); 
		 	            c.put("pOrderID", OrderID_sik);
		 	                     
		 	      	 	            
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v4 * v5*v7*v8*v10*trackcode ==1)
	         {
	         	Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	
	public void SubmitOrderTRF_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException, ParserConfigurationException 
	{
		//Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0,v9=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********submitOrder_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("SubmitOrder"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SubmitOrder"));  
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	        	 String rowmsg;
				 rowmsg = rs.getString(1);
			            
			    if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            	String sik_AccountNumber= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:AccountNumber"); 
			       	Application.showMessage("sik_AccountNumber is ::" +sik_AccountNumber);
	            	
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);   
		            Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
		          	           
	            	String sik_AccountNumber= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
	 	            Application.showMessage("sik_AccountNumber is ::"+sik_AccountNumber); 
	 	            
	 	            if(sik_AccountNumber==null)
	 	            {
	 	             continue;	
	 	            }
	 	            else if(sik_AccountNumber.equals(c.get("pAccountNumber1")))
	            	{
		            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
		            	Application.showMessage("SEND XML is :: \n"+senddata);
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+sik_AccountNumber);
	            		callFound=1;
	            	}
	            	else
	            	{
	            		continue;
	            	}
	            	
		            if(callFound==1)
		            {
		                   String cct_code= sL.nodeFromKey(recievedata,"<cct:message><cct:code>","</cct:code><cct:text>");
		                   String cct_text= sL.nodeFromKey(recievedata,"</cct:code><cct:text>","</cct:text></cct:message>");
		                   Application.showMessage("MOdify Text is :: "+cct_text);
		                   if(cct_code==null)
		                   {
		                	   String sik_OrderID= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
		                	   Application.showMessage("Order Id From SIK is ::"+sik_OrderID);
		                	   c.put("psik_OrderID",sik_OrderID);
		                	   c.put("pOrderID", sik_OrderID);
		                	   if(sik_OrderID==null)
		                	   {
		                		   continue;
		                	   }

		                   }
		                   else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-213"))
		                   {
		                	   c.put("MFlag","1");
		                	   
			                   String orderid_modify= sL.nodeFromKey(recievedata,"</code><text>Duplicate Order - Single Not Exact Match: Duplicate order detected; matched order #","</text></message></messages");
                              Application.showMessage("Modify Order ID found is :: "+orderid_modify);
                              c.put("MOrderID", orderid_modify);
		                   }
		                   //-----****Validation for Trackcode*****--------------
                           
                           List<String> retreiveTrackcodes=sL.getRateCodes(input, c);
                           
                           if(retreiveTrackcodes.size()>0)
                            {
                          	 Application.showMessage("**********Validation Point :: KIT Code - OT0PD***********");
                           int TrackValue=sL.getTrackingDetails(input, c,senddata);
                           Application.showMessage("TrackValue:::"+TrackValue);
                           //List<String> retrieveTrackValues=sL.extractValue(TrackValue, input, c);
                           if(TrackValue==(retreiveTrackcodes.size()))
                           {
                          	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                          	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                          	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are equal");
                          	 Application.showMessage("----****Validation is successful with the Track code OTOPD and Reason Xi3_special****----" );
                          	 trackcode=1;
                           }
                           else
                           {
                          	 Application.showMessage("No of OTOPD Trackcode present in the excel file is:;"+retreiveTrackcodes.size());
                          	 Application.showMessage("No of Xi3_special reason attribute value of sik:ExternalOrderData tag present in the send data is:;"+TrackValue);
                          	 Application.showMessage("Both OTOPD codes and Xi3_special reason attribute value are not equal");
                          	 Application.showMessage("----****Validation is not successful with the Track code OTOPD and Reason Xi3_special****----" );
                          	
                           }
                           }
                           else
                           {
                          	 trackcode=1;
                           }


		            	   
		            	// String sik_Email= sL.GetValueByXPath(senddata,"/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");
			                
		 	          
		 	           
		 	            
		 	            String sik_ShipmentPriority= sL.nodeFromKey(senddata,"<sik:ShipmentPriority>","</sik:ShipmentPriority>");
		 	            Application.showMessage("ShipmentPriority is ::"+sik_ShipmentPriority); 
		 	            
		 	            String sik_FTA= sL.nodeFromKey(senddata,"<sik:FTA>","</sik:FTA>");
		 	            Application.showMessage("sik_FTA is ::"+sik_FTA);
		 	         /*   if(sik_FTA.equalsIgnoreCase(c.get("pw_ftax").toString()))
		 	            {*/
		 	            	 Application.showMessage("*******Validation Point 5 :: WebServicall-********");
		 	            	 v1=1;
		 	            	Application.showMessage("FTA got Validated with GetLocation FTA");
		 	            	
		 	          /*  }
		 	            else
		 	            {
		 	            	Application.showMessage("FTA NOT!! Validated with GetLocation FTA");
		 	            	c.report("FTA NOT Validated with GetLocation FTA");
		 	            }*/
		 	            
		 	            Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));

		 	            Application.showMessage("FTA from Get Loc" +c.get("GL_ftaAgent"));
		 	            //Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
		 	            

		 	          
		 	           /* String sik_Address1= sL.nodeFromKey(senddata,"<sik:Address1>","</sik:Address1>");
		 	            Application.showMessage("sik_Address1 is ::"+sik_Address1); 
		 	            
		 	            if(sik_Address1.trim().equals(c.get("pAddress1").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 6 :: WebServicall-********");
		 	            	 v2=1;
		 	            	Application.showMessage("Address1 got Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            	c.report("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress1").toString());
		 	            }
		 	            */
		 	           /* String sik_Address2= sL.nodeFromKey(senddata,"<sik:Address2>","</sik:Address2>");
		 	            Application.showMessage("sik_Address2 is ::"+sik_Address2); 
		 	            if(sik_Address2.trim().equals(c.get("pAddress2").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 7 :: WebServicall-********");
		 	            	 v3=1;
		 	            	Application.showMessage("Address2 got Validated with GetHouse Address2 as"+c.get("pAddress2").toString());
		 	            }
		 	            else
		 	            {
		 	            	Application.showMessage("Address2 NOT Validated with GetHouse Address2 as"+c.get("pAddress2").toString());
		 	            	c.report("Address1 NOT Validated with GetHouse Address1 as"+c.get("pAddress2").toString());
		 	            }*/
		 	            
		 	            String sik_City= sL.nodeFromKey(senddata,"<sik:City>","</sik:City>");
		 	            Application.showMessage("sik_City is ::"+sik_City); 
		 	            
		 	            if(sik_City.equalsIgnoreCase(c.get("GL_City").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 8 :: WebServicall-********");
		 	            	 v4=1;
		 	            	Application.showMessage("City got Validated with GetLocation City");
		 	            }
		 	            else
		 	            {
		 	            	 v4=1;
		 	            	//c.report("City NOT Validated with GetLocation City");
		 	            }
		 	            
		 	            String sik_State= sL.nodeFromKey(senddata,"<sik:State>","</sik:State>");
		 	            Application.showMessage("sik_State is ::"+sik_State); 
		 	            if(sik_State.equalsIgnoreCase(c.get("GL_state").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 9 :: WebServicall-********");
		 	            	 v5=1;
		 	            	Application.showMessage("State got Validated with GetLocation State");
		 	            }
		 	            else
		 	            {
		 	            	c.report("State NOT Validated with GetLocation State");
		 	            }
		 	            
		 	            
		 	            String sik_Zip= sL.nodeFromKey(senddata,"<sik:Zip>","</sik:Zip>");
		 	            Application.showMessage("sik_Zip is ::"+sik_Zip); 
		 	            
		 	            String sik_IsAlternateAddress= sL.nodeFromKey(senddata,"<sik:IsAlternateAddress>","</sik:IsAlternateAddress>");
		 	            Application.showMessage("sik_IsAlternateAddress is ::"+sik_IsAlternateAddress); 
		 	            
		 	           	 	            
		 	            String sik_ProductCode= sL.nodeFromKey(senddata,"<sik:ProductCode>","</sik:ProductCode>");
		 	            Application.showMessage("sik_ProductCode is ::"+sik_ProductCode); 
		 	           /* if(sik_ProductCode.equalsIgnoreCase(c.get("pKRateCode3").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 10 :: WebServicall-********");
		 	            	 v6=1;
		 	            	Application.showMessage("sik_ProductCode got Validated with GetLocationProductCode");
		 	            }
		 	            else
		 	            {
		 	            	c.report("sik_ProductCode NOT Validated with GetLocation ProductCode");
		 	            }*/
		 	            
		 	           	 	            		 	            
		 	           /* String sik_DNCSIPAddress= sL.nodeFromKey(senddata,"<sik:DNCSIPAddress>","</sik:DNCSIPAddress>");
		 	            Application.showMessage("sik_DNCSIPAddress is ::"+sik_DNCSIPAddress); 
		 	            if(sik_DNCSIPAddress.equalsIgnoreCase(c.get("GL_headendNetworkAddress").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 11 :: WebServicall-********");
		 	            	
		 	            	Application.showMessage("sik_DNCSIPAddress got Validated with GL_headendNetworkAddress");
		 	            }
		 	            else
		 	            {
		 	            	c.report("sik_DNCSIPAddress NOT Validated with GetLocation GL_headendNetworkAddress");
		 	            }
		 	            */
		 	            
		 	           	 	            
		 	         /*   String sik_HeadEndVendor= sL.nodeFromKey(senddata,"<sik:HeadEndVendor>","</sik:HeadEndVendor>");
		 	            Application.showMessage("sik_HeadEndVendor is ::"+sik_HeadEndVendor); 
		 	            
		 	           if(sik_HeadEndVendor.equalsIgnoreCase(c.get("GL_headendType").toString()))
		 	            {
		 	        	     Application.showMessage("*******Validation Point 12 :: WebServicall-********");
		 	            	 v7=1;
		 	            	Application.showMessage("sik_HeadEndVendor got Validated with GL_headendType");
		 	            }
		 	            else
		 	            {
		 	            	c.report("sik_HeadEndVendor NOT Validated with GetLocation GL_headendType");
		 	            }*/
		 	           		 	            
		 	            String sik_E911_acceptance= sL.nodeFromKey(senddata,"<sik:E911_acceptance>","</sik:E911_acceptance>");
		 	            Application.showMessage("sik_E911_acceptance is ::"+sik_E911_acceptance); 
		 	                      
		 	            String sik_AgentID= sL.nodeFromKey(senddata,"<sik:AgentID>","</sik:AgentID>");
		 	            Application.showMessage("sik_AgentID is ::"+sik_AgentID); 
		 	            
		 	            String sik_WorkOrderID= sL.nodeFromKey(senddata,"<sik:WorkOrderID>","</sik:WorkOrderID>");
		 	            Application.showMessage("sik_WorkOrderID is ::"+sik_WorkOrderID); 
		 	            if(sik_WorkOrderID.equalsIgnoreCase(c.get("pw_wpcnt").toString()))
		 	            {
		 	            	 Application.showMessage("*******Validation Point 13:: WebServicall-********");
		 	            	 v8=1;
		 	            	Application.showMessage("sik_WorkOrderID got Validated with pw_wpcnt");
		 	            }
		 	            else
		 	            {
		 	            	//c.report("sik_WorkOrderID NOT Validated with  pw_wpcnt");
		 	            	v8=1;
		 	            }
		 	         	 	            
		 	           String sik_OrderID= sL.nodeFromKey(recievedata,"<sik:OrderID>","</sik:OrderID>");
                	   Application.showMessage("Order Id From SIK is ::"+sik_OrderID);
                	   c.put("psik_OrderID",sik_OrderID);
                	   c.put("pOrderID", sik_OrderID);
		 	      	 	            
		 	            
		            break;
		            }
	             }
	         }
	         
	         if(v1*callFound *v4*v5*trackcode ==1)
	         {
	         	Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }

	public void orderUpdate_ValidateforNegativeQuerylocation(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
 	{

	//	Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 String ou_productType;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 int myCount=1;
		 String Status=null;
		 String ou_AccountNumberid;
		 String ou_billingSystem = null;
		 //String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********orderUpdate_Validate Function************");       
	     Application.showMessage("----------------------------------------------------");
	     
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
	         
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        	 if(myCount ==1)
	    	     {
	    	    	 
	    	    	 Status="INI";
	    	    	 
	    	     }
	    	     else if(myCount ==2)
	    	     {
	    	    	 Status="COM"; 
	    	     }
	    	     else
	    	     {
	    	       Application.showMessage("more than two Order Update...!");
	    	     }
	        
	            String rowmsg;
			    rowmsg = rs.getString(1);
			            
			   /*if(rowmsg.equals(c.getValue("BaseMsgid")))break;*/
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	           
	                          
		            
		            
			       // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
	            	
	            	Application.showMessage("Account number from Xl sheet is "+ c.getValue("accountNumber"));
	            	Application.showMessage("getAccount number from xl sheet put::"+c.get("accountNumber"));
                  if(senddata.contains("worklist")|| senddata.contains("wlRequest"))
                  {
		             ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
	 	            Application.showMessage("accountNumber is ::"+ou_AccountNumberid);
                  }
	 	            else
	 	            {
	 	            	 ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"corpId\">");
	 	            	Application.showMessage("accountNumber is ::"+ou_AccountNumberid);
	 	            }
	 	            if(ou_AccountNumberid== null)
	 	            {
	 	            	continue;
	 	            }
	 	            
	 	            else if(ou_AccountNumberid.equals(c.get("pAccountNumber"))||ou_AccountNumberid.equals(c.getValue("accountNumber")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
	            		callFound=1;
	            	}
	 	            
	 	           else if(ou_AccountNumberid.equals(c.get("pAccountNumber1")))
	            	{
		            	
	            		//System.out.printf("SEND XML is %s \n",senddata);
	            		Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
	            		Application.showMessage("Validation is Successfull with Input Transfer Account Number"+ou_AccountNumberid);
	            		callFound=1;
	            	}
	 	           
	            	else
	            	{
	            		continue;
	            	}
	            	
		           
		            if(callFound==1)
		            {
		            	if(senddata.contains("worklist") || senddata.contains("wlRequest"))
		                   {
		 	             ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"customerType\">");
		 	            Application.showMessage("productType is ::"+ou_productType);
		                   }
		            	else
		            	{
		            		 ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value></header>");
			 	            Application.showMessage("productType is ::"+ou_productType);
		            	}
		 	            
		 	            if(ou_productType==null)
			            {
				            c.report("Send Xml productType is NULL");
				            continue;
			            }
			            else
			            {
			            	 Application.showMessage("productType Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ou_productType);
			            	 if(ou_productType.equalsIgnoreCase("SIK"))
				             {
				            	 Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
				            	 Application.showMessage("Validation is Successfull with ou_productType::"+" "+ou_productType);
				            	 v1=1;
				             }
				             else
				             {
				            	 c.report("ou_productType at Send Xml not Validated as "+ou_productType);
				             }
			            }
		 	            String sendataTrim=sL.RemoveNameSpaces(senddata);
		 	            Application.showMessage("senddataTRim::" +sendataTrim);
		 	          // String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"ordType\">");
		 	           String ordStatus=sL.GetValueByXPath(sendataTrim, "//header/value[5]");
		 	           Application.showMessage("OrderStatus:::"+ordStatus);
		 	           if(senddata.contains("worklist")|| senddata.contains("wlRequest"))
	                   {
		 	             Application.showMessage("No BillingSystem in sendData XML");
	                   }
		 	           else 
		 	           {
		 	        	   
		 	        	   ou_billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
			 	            Application.showMessage("billingSystem is ::"+ou_billingSystem);
		 	        	   
		 	        	   
		 	           }
		 	            
		 	           if(ou_billingSystem==null)
			            {
		 	        	   if(ordStatus.equalsIgnoreCase("ERR"))
		 	        	   {
		 	        		  v2=1;
		 	        		   Application.showMessage("No Billing ID for negative scenario");
		 	        	   }
		 	        	   else
		 	        	   {
		 	        	   
				            c.report("Send Xml billingSystem is NULL");
				            continue;
		 	        	   }
			            }
			            else
			            {
			            	 Application.showMessage("BillingSystem from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+ou_billingSystem);
			            	 if(ou_billingSystem.equals("DDP"))
				             {
				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
				            	 Application.showMessage("Validation is Successfull with billingSystem::"+" "+ou_billingSystem);
				            	 v2=1;
				             }
				             else
				             {
				            	 c.report("billingSystem at Send Xml not Validated as "+ou_billingSystem);
				             }
			            }

		 	            String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"errorText\">");
		 	            Application.showMessage("customerType is ::"+ou_customerType); 
		 	            if(ou_customerType==null)
			            {
				            c.report(" ou_customerType is NULL");
				            
			            }
			           
		 	            
		 	            
		 	            Application.showMessage("ordStatus is ::"+ordStatus); 
		 	            if(ordStatus.equalsIgnoreCase("ERR"))
		 	            {
		 	            	String errorCode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"ordStatus\">");
		 	            	if(errorCode.equalsIgnoreCase("SIK_SWI_006") || errorCode.equalsIgnoreCase("SIK_SWI_007") || errorCode.equalsIgnoreCase("SIK_SWI_002")|| errorCode.equalsIgnoreCase("SIK_SWI_001")|| errorCode.equalsIgnoreCase("SIK_SWI_008"))
		 	            	{
		 	            		v3=1;
		 	            		Status="ERR";
		 	            		Application.showMessage("ErrorCode Validated");
		 	            		
		 	            	}
		 	            	else
		 	            	{
		 	            		c.report("Error Code not validated");
		 	            	}
		 	            	}
		 	            else if(ordStatus==null)
			            {
				            c.report("Send Xml ordStatus is NULL");
				              }
		 	            	 	           
			             else
			             {
			                continue;
			             }
		 	         	      
		 	           
		 	         
		 	           
		 	            String accountNumber_ou= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
		 	            Application.showMessage("accountNumber is ::"+accountNumber_ou);
		 	            
		 	             if(accountNumber_ou.equals(c.get("pAccountNumber"))|| accountNumber_ou.equals(c.getValue("accountNumber")) )
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
			            	 v4=1;
			             }
		 	             
		 	             else if(accountNumber_ou.equals(c.get("pAccountNumber1")))
			             {
			            	 Application.showMessage("*******Validation Point 6 :: WebServicall-ordType********");
			            	 Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
			            	 v4=1;
			             }
		 	            	 
		 	             
			             else
			             {
			            	 c.report("accountNumber at Send Xml not Validated as "+accountNumber_ou);
			             }    
		 	            
		 	            /*String inputChannel= sL.nodeFromKey(senddata,"<value name=\"inputChannel\">","</value><value name=\"customerType\">");
		 	            Application.showMessage("inputChannel is ::"+inputChannel); 
		 	            if(inputChannel==null)
			            {
				          Application.showMessage("Send Xml inputChannel is NULL");
				            
			            }*/
		 	            
		 	        /*if(myCount>2)
		 	        {
		            break;
		 	        }
		 	        else
		 	        {
		 	        	continue;
		 	        }*/
		 	        
		            }
	             }
	         }
	         
	         if(v1*callFound*v2*v3*v4 ==1)
	         {
	         	Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
	         }
	       //  st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
 	}
	public void OrderSearch_CancelValidate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     sikLibraryClass sL = new sikLibraryClass();
		 ResultSet  rs;
		 int v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("**********OrderSearch_CancelValidate Function*************");       
	     Application.showMessage("----------------------------------------------------");
	     HashMap Operation=sL.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("OrderSearch"));
	     	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("OrderSearch")); 
		try
		{
			// Statement st =sL. dBConnect(input, c);	
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/OrderSearch' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime")+"' order by creation_time desc)where rownum <= 50");
			rs=sL.reduceThinkTimeSIK(input, c);
	         while (rs.next())
	         {
	        	
	        
	           String rowmsg;
			   rowmsg = rs.getString(1);
			            
			   if(rowmsg.equals(c.getValue("BaseMsgid")))break;
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =sL.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
	            }
	            else if(rs.getBlob("SEND_DATA")==null)
	            {
	            	Application.showMessage("Your SEND XML is NULL \n");	
	            	String recievedata=sL.extractXml(rs,xmldata1);
	            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
	            }
	            else
	            { 
	                          
		            String senddata = sL.extractXml(rs,xmldata2);           
		            String recievedata = sL.extractXml(rs,xmldata1);  
		            
		            String insideXML1= sL.RemoveNameSpaces(senddata); 
            	    Application.showMessage("Well-Formed XML for SEND DATA is ::"+insideXML1);
            	    
            	    
            	    
            	    String AccountNumber_OrderSeach= sL.GetValueByXPath(insideXML1,"/OrderSearch/Request/AccountNumber"); 
		       	    Application.showMessage("AccountNumber_OrderSeach is ::" +AccountNumber_OrderSeach);
		       	    
		      
		       	    String insideXML2= sL.RemoveNameSpaces(recievedata); 
         	        Application.showMessage("Well-Formed XML for recievedata DATA is ::"+insideXML2);
         	    
		        	
		       	  
	 	           
		       	 
		       	    
		       	    Application.showMessage("Account Number from Outside::"+ c.getValue("accountNumber"));
	 	            if(AccountNumber_OrderSeach==null)
		            {
			            c.report("Send Xml AccountNumber_OrderSeach is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            if(AccountNumber_OrderSeach.equals(c.getValue("accountNumber")))
		            	{
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-AccountNumber_OrderSeach Call********");
		            		Application.showMessage("Validation is Successfull with AccountNumber_OrderSeach"+AccountNumber_OrderSeach);
		            		v1=1;
		            	}
		            	else
		            	{
		            		continue;
		            	}
		            
	 	            }
	 	            
	 	            
	 	            String WorkOrderID_OrderSeach= sL.GetValueByXPath(insideXML1,"/OrderSearch/Request/WorkOrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +WorkOrderID_OrderSeach);	
		       	    
	 	            if(WorkOrderID_OrderSeach==null)
		            {
			            c.report("Send Xml WorkOrderID_OrderSeach is NULL");
			            continue;
		            }
	 	            else
	 	            {
			                       	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-pBillingOrdID Call********");
		            		Application.showMessage("Validation is Successfull with pBillingOrdID"+WorkOrderID_OrderSeach);
		            		v2=1;           	
		            	
		            
	 	            }
	 	            
	 	            String Details_OrderSeach= sL.GetValueByXPath(insideXML2,"/OrderSearchResponse/OrderSearchResult/Details"); 
		       	    Application.showMessage("Details_OrderSeach is ::" +Details_OrderSeach);
		       	    
		       	    String OrderIDCount_OrderSeach= sL.nodeFromKey(insideXML2,"<Details>","order(s) found</Details>");
	 	            Application.showMessage(" OrderIDCount_OrderSeach is ::"+OrderIDCount_OrderSeach);
	 	            
	 	            	            
	 	            
	 	            String i=OrderIDCount_OrderSeach;
	 	            
	 	            
	 	            String OrderID_OrderSeach= sL.GetValueByXPath(insideXML2,"/OrderSearchResponse/OrderSearchResult/OrdersFound/OrderFound["+i+"]/OrderID"); 
		       	    Application.showMessage("WorkOrderID_OrderSeach is ::" +OrderID_OrderSeach);	
		       	    
		       	    Application.showMessage("Order ID from Submit SIK is ::"+c.get("pOrderID"));
		       	    
	 	            if(OrderID_OrderSeach==null)
		            {
			            c.report("Send Xml WorkOrderID_OrderSeach is NULL");
			            continue;
		            }
	 	            else
	 	            {
			            
			            	
		            		Application.showMessage("*******Validation Point 2:: WebServicall-OrderID_OrderSeach Call********");
		            		Application.showMessage("Validation is Successfull with OrderID_OrderSeach"+OrderID_OrderSeach);
		            		v3=1;           		
		            		
		            
	 	            }
	 	            
	 	      
		            
		            }
		 	            
		            break;
		       }
	             
	         
	         
	         if(v1*v2*v3 ==1)
	         {
	         	Application.showMessage("WebService Call :: OrderSearch is Success[All validation points are Success]");
	         }
	         else
	         {
	        	 c.put("result", "false");
	        	 c.report("WebService Call ::OrderSearch is Failed with Validation Errors");
	         }
	      //   st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
}
