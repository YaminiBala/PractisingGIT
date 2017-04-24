	import java.io.IOException;
import java.io.StringReader;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	import java.util.HashMap;
	import java.util.Map;
	
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
	import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
	
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
	import org.xml.sax.SAXException;
	
	import com.parasoft.api.Application;
	import com.parasoft.api.Context;
	import com.parasoft.api.ScriptingContext;
	import com.parasoft.api.css.Node;
import com.parasoft.api.html.NodeList;
	
	
	public class sikMultipleProductComponent
	{
		public int OutPut=1;
		sikDotcomClass sD=new sikDotcomClass();
		sikLibraryClass sL=new sikLibraryClass();
		/* public Map<String,String> DotcomProductNames()
		    {
		    Map<String,String> map=new HashMap<String,String>();
		    map.put("8000071", "GP_VID-STD");
	    	map.put("8000075", "GP_VID-STD");
	    	map.put("10400135", "GP_XH IWLR-White");
	    	map.put("10400131", "GP_XH Sensor Add On Pack");
	    	map.put("10500041", "GP_XH Eco Starter Pck");
	    	map.put("10400136", "GP_XH IWLR-Almond");
	    	map.put("8000076", "GP_VID-HDDVR");
	    	map.put("8000037", "GP_HSDKIT-NOMODEM");
	    	map.put("42005", "GP_D3 eMTA");
	    	map.put("10400032", "GP_XH Water Sensor");
	    	//Application.showMessage("*******DOTCOM PRODUCT CODE LIST******"+"\n\r"+map.toString());
	    	//Application.showMessage("*******DOTCOM PRODUCT CODE LIST******"+"\n\r"+map.entrySet().toString());
	    	//Application.showMessage("*******DOTCOM PRODUCT CODE LIST******"+"\n\r"+map.values());
	
	
			return map;
	}*/
		 public Map<String,String> DotcomProductNames()
		    {
		    Map<String,String> map=new HashMap<String,String>();
		    map.put("8000071", "GP_HSDKIT-NOMODEM");
	    	map.put("8000075", "GP_HSDKIT-NOMODEM");
	    	map.put("10400135", "GP_HSDKIT-NOMODEM");
	    	map.put("10400131", "GP_HSDKIT-NOMODEM");
	    	map.put("10500041", "GP_HSDKIT-NOMODEM");
	    	map.put("10400136", "GP_HSDKIT-NOMODEM");
	    	map.put("8000076", "GP_HSDKIT-NOMODEM");
	    	map.put("8000037", "GP_HSDKIT-NOMODEM");
	    	map.put("42005", "GP_HSDKIT-NOMODEM");
	    	map.put("10400032", "GP_HSDKIT-NOMODEM");
	    	//Application.showMessage("*******DOTCOM PRODUCT CODE LIST******"+"\n\r"+map.toString());
	    	//Application.showMessage("*******DOTCOM PRODUCT CODE LIST******"+"\n\r"+map.entrySet().toString());
	    	//Application.showMessage("*******DOTCOM PRODUCT CODE LIST******"+"\n\r"+map.values());
	
	
			return map;
	}
		   
	    public String getProductNameFromProductCode(String Product)
	    {
	    	String retValue;
	    	Map<String,String> iMap=new HashMap<String,String>();
	    	iMap=DotcomProductNames();
	    	retValue=iMap.get(Product);
			return retValue;	
	    }
	    public void readValuesFromFile(Object input, ScriptingContext c) throws IOException
	    {
	   	 Application.showMessage("-----------Reading Values from Excel------------");
	   	 
			
				 String Product_Id1=c.getValue("Input_files","SIK_details: Product_code1");
				 String Product_Id2=c.getValue("Input_files","SIK_details: Product_code2");
				 String Product_Id3=c.getValue("Input_files","SIK_details: Product_code3");
				 String Product_Id4=c.getValue("Input_files","SIK_details: Product_code4");
				 String Product_Id5=c.getValue("Input_files","SIK_details: Product_code5");
				 String Product_Id6=c.getValue("Input_files","SIK_details: Product_code6");
				 String Product_Id7=c.getValue("Input_files","SIK_details: Product_code7");
				 String Product_Id8=c.getValue("Input_files","SIK_details: Product_code8");
				 String Product_Id9=c.getValue("Input_files","SIK_details: Product_code9");
				 String Product_Id10=c.getValue("Input_files","SIK_details: Product_code10");
				 Application.showMessage("The Product codes's are::");
				 Application.showMessage(""+Product_Id1+"\n\r"+Product_Id2+"\n\r"+Product_Id3+"\n\r"+Product_Id4+"\n\r"+Product_Id5+"\n\r"+Product_Id6+"\n\r"+Product_Id7+"\n\r"+Product_Id8+"\n\r"+Product_Id9+"\n\r"+Product_Id10);
	    }
	    
	    public void submitOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
		{
			Thread.sleep(30000); // Think time in JVM [waits for 10 secs here]
		     sikLibraryClass sL = new sikLibraryClass();
			 ResultSet  rs;
			 int prod=0;
	          int product=0;
			 int callFound=0,v1=0,v2=0,v3=0,v4=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     String Time= c.get("BaseTime").toString();
		     Context AppContext =Application.getContext();
		     Application.showMessage("-----------------------------------------------------");
		     Application.showMessage("**********submitOrder_Validate Function************");       
		     Application.showMessage("----------------------------------------------------");
		         
			try
			{
				 Statement st =sL. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' order by creation_time desc)where rownum <= 20");
		        
		         while (rs.next())
		         {
		        	
		        
		        	 String rowmsg;
					 rowmsg = rs.getString(1);
			         Application.showMessage("MessageID is::"+rowmsg);
			           
				            
				   
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
			          	           
			            String sik_AccountNumber= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
		 	            Application.showMessage("accountNumber is ::"+sik_AccountNumber); 
		 	            
		 	            if(sik_AccountNumber==null)
		 	            {
		 	             continue;	
		 	            }
		 	            else if(sik_AccountNumber.equals(c.getValue("accountNumber")))
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
			            	 
			            	if(senddata.contains("<sik:E911_acceptance>1</sik:E911_acceptance>"))
			            	{
			            		Application.showMessage("E911 acceptance Validated as 1 for CDV Order");
			            	}
			            	else if(senddata.contains("<sik:E911_acceptance>0</sik:E911_acceptance>"))
			            	{
			            		Application.showMessage("E911 acceptance Validated as 0 for CDV Order");
			            	}
			            	
			            	else
			            	{
			            		Application.showMessage("Is not an CDV Order");
	
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
					            	 Application.showMessage("sik_Email from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+c.getValue("emailAddress").toString());
					            	 if(sik_Email.equalsIgnoreCase(c.getValue("emailAddress")))
						             {
						            	 Application.showMessage("*******Validation Point 4 :: WebServicall-emailAddress********");
						            	 Application.showMessage("Validation is Successfull with emailAddress::"+" "+sik_Email);
						            	 v3=1;
						             }
					            	
						             else 
						             {
						            	Application.showMessage("emailAddress at Send Xml not Validated as "+sik_Email);
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
				            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+sik_FirstName);
				            	 if(sik_FirstName.equals(c.getValue("dS_SIK", "Sik_Dotcom: FirstName")))
					             {
					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
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
				            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+sik_LastName);
				            	 if(sik_LastName.equals(c.getValue("dS_SIK", "Sik_Dotcom: LastName")))
					             {
					            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
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
			 	            
			 	            
			 	            Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));
	
			 	            Application.showMessage("FTA from Get Loc" +c.get("GL_ftaAgent"));
			 	            //Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
			 	            
			 	            
			 	            
			 	         
	
			 	           if(c.getValue("BillingSystem").equalsIgnoreCase("DDP"))
			 	            {
			 	            	
			 	               if(((String)c.get("GL_ftaAgent")).trim().equals("0".concat(sik_FTA.trim())))
			 	               {
			 	            	   Application.showMessage("FTA is been Validated with Get Location ftaAgent As Flow is DDP ::"+sik_FTA);
			 	               }
			 	               else
			 	               {
			 	            	   c.report("FTA is NOT been Validated with Get Location ftaAgent As Flow is DDP ::"+sik_FTA);
	 
			 	               }
			 	            }
			 	            else if(c.getValue("BillingSystem").equalsIgnoreCase("CSG"))
			 	            {
			 	            	if((((String) c.get("GL_FranchiseTaxArea")).trim()).equals(sik_FTA.trim()))
				 	               {
				 	            	   Application.showMessage("FTA is been Validated with Get Location FranchiseTaxArea As Flow is CSG ::"+sik_FTA);
				 	               }
				 	               else
				 	               {
				 	            	   c.report("FTA is NOT been Validated with Get Location FranchiseTaxArea As Flow is CSG ::"+sik_FTA);
		 
				 	               }
			 	            }
			 	            
			 	            String sik_Address1= sL.nodeFromKey(senddata,"<sik:Address1>","</sik:Address1>");
			 	            Application.showMessage("sik_Address1 is ::"+sik_Address1); 
			 	            
			 	            String sik_Address2= sL.nodeFromKey(senddata,"<sik:Address2>","</sik:Address2>");
			 	            Application.showMessage("sik_Address2 is ::"+sik_Address2); 
			 	            
			 	            String sik_City= sL.nodeFromKey(senddata,"<sik:City>","</sik:City>");
			 	            Application.showMessage("sik_City is ::"+sik_City); 
			 	            
			 	            String sik_State= sL.nodeFromKey(senddata,"<sik:State>","</sik:State>");
			 	            Application.showMessage("sik_State is ::"+sik_State); 
			 	            
			 	            String sik_Zip= sL.nodeFromKey(senddata,"<sik:Zip>","</sik:Zip>");
			 	            Application.showMessage("sik_Zip is ::"+sik_Zip); 
			 	            
			 	            String sik_IsAlternateAddress= sL.nodeFromKey(senddata,"<sik:IsAlternateAddress>","</sik:IsAlternateAddress>");
			 	            Application.showMessage("sik_IsAlternateAddress is ::"+sik_IsAlternateAddress); 
			 	            
			 	            //-------------------------------------------------------------------------------------------------------//
			 	            //
			 	            //       					   SIK Address validation module with Alternate address Logic
			 	            //
			 	            //-------------------------------------------------------------------------------------------------------//
			 	            
			 	            if(sik_IsAlternateAddress.equals("1"))
			 	            {
			 	            	if(c.get("SI_Add1")==null || c.get("SI_Add2")==null || c.get("SI_City")==null || c.get("SI_State")==null || c.get("SI_Zip")==null )
			 	            	{
			 	            		if(((String) c.get("SHIP_StreetAddress")).equalsIgnoreCase(sik_Address1))
			 	            		{
			 	            		   Application.showMessage("Street Address of sik is Validated with Shipping Contact as ::" +sik_Address1);
			 	            		}
			 	            		else
			 	            		{
				 	            	 c.report("Street Address of sik is NOT Validated with Shipping Contact as ::" +sik_Address1);
	
			 	            		}
			 	            		
			 	            		if(((String) c.get("SHIP_City")).equalsIgnoreCase(sik_City))
			 	            		{
			 	            		   Application.showMessage("City of sik is Validated with Shipping Contact as ::" +sik_City);
			 	            		}
			 	            		else
			 	            		{
				 	            	 c.report("City of sik is NOT Validated with Shipping Contact as ::" +sik_City);
	
			 	            		}
			 	            		
			 	            		if(((String) c.get("SHIP_State")).equalsIgnoreCase(sik_State))
			 	            		{
			 	            		   Application.showMessage("State of sik is Validated with Shipping Contact as ::" +sik_State);
			 	            		}
			 	            		else
			 	            		{
				 	            	 c.report("State of sik is NOT Validated with Shipping Contact as ::" +sik_State);
	
			 	            		}
			 	            		
			 	            		if(((String) c.get("SHIP_ZipCode")).equalsIgnoreCase(sik_Zip))
			 	            		{
			 	            		   Application.showMessage("ZipCode of sik is Validated with Shipping Contact as ::" +sik_Zip);
			 	            		}
			 	            		else
			 	            		{
				 	            	 c.report("ZipCode of sik is NOT Validated with Shipping Contact as ::" +sik_Zip);
	
			 	            		}
			 	            	}
			 	            	
			 	            	else
			 	            	{
			 	            	   
			 	            		if(((String) c.get("SI_Add1")).equalsIgnoreCase(sik_Address1))
			 	            		{
			 	            		   Application.showMessage("Street Address of sik is Validated with Special Instruction as ::" +sik_Address1);
			 	            		}
			 	            		else
			 	            		{
				 	            	 c.report("Street Address of sik is NOT Validated with Special Instruction as ::" +sik_Address1);
	
			 	            		}
			 	            		
			 	            		if(((String) c.get("SI_City")).equalsIgnoreCase(sik_City))
			 	            		{
			 	            		   Application.showMessage("City of sik is Validated with Special Instruction as ::" +sik_City);
			 	            		}
			 	            		else
			 	            		{
				 	            	 c.report("City of sik is NOT Validated with Special Instruction as ::" +sik_City);
	
			 	            		}
			 	            		
			 	            		if(((String) c.get("SI_State")).equalsIgnoreCase(sik_State))
			 	            		{
			 	            		   Application.showMessage("State of sik is Validated with Shipping Contact as ::" +sik_State);
			 	            		}
			 	            		else
			 	            		{
				 	            	 c.report("State of sik is NOT Validated with Shipping Contact as ::" +sik_State);
	
			 	            		}
			 	            		
			 	            		if(((String) c.get("SI_Zip")).equalsIgnoreCase(sik_Zip))
			 	            		{
			 	            		   Application.showMessage("ZipCode of sik is Validated with Special Instruction as ::" +sik_Zip);
			 	            		}
			 	            		else
			 	            		{
				 	            	 c.report("ZipCode of sik is NOT Validated with Shipping Contact as ::" +sik_Zip);
	
			 	            		}
			 	            	}
			 	            }
			 	            else if(sik_IsAlternateAddress.equals("0"))  
			 	            {
			 	            	if(c.get("StreetName_Loc").toString().equalsIgnoreCase(sik_Address1.trim()))
		 	            		{
		 	            		   Application.showMessage("Street Address of sik is Validated with Get Location as ::" +sik_Address1);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("Street Address of sik is NOT Validated with Get Location as ::" +sik_Address1);
	
		 	            		}
		 	            		
		 	            		if(((String) c.get("GL_City")).equalsIgnoreCase(sik_City))
		 	            		{
		 	            		   Application.showMessage("City of sik is Validated with Get Location as ::" +sik_City);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("City of sik is NOT Validated with Get Location as ::" +sik_City);
	
		 	            		}
		 	            		
		 	            		if(((String) c.get("GL_state")).equalsIgnoreCase(sik_State))
		 	            		{
		 	            		   Application.showMessage("State of sik is Validated with Get Location as ::" +sik_State);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("State of sik is NOT Validated with Get Locationt as ::" +sik_State);
	
		 	            		}
		 	            		
		 	            		if(((String) c.get("GL_zip5")).equalsIgnoreCase(sik_Zip))
		 	            		{
		 	            		   Application.showMessage("ZipCode of sik is Validated with Get Location as ::" +sik_Zip);
		 	            		}
		 	            		else
		 	            		{
			 	            	 c.report("ZipCode of sik is NOT Validated with Get Location as ::" +sik_Zip);
	
		 	            		}	
			 	            }
			 	            else
			 	            {
			 	            	c.report("SIK Address not Validated");	
			 	            }
			 	            
			 	            String sik_ProductCode= sL.nodeFromKey(senddata,"<sik:ProductCode>","</sik:ProductCode>");
			 	            Application.showMessage("sik_ProductCode is ::"+sik_ProductCode); 
			 	            
			 	           if(c.getValue("orderEntryOption").equalsIgnoreCase("OWN"))
			 	          {
			 	            
			 	            Boolean isverified=sL.modemVerification(sik_ProductCode,c,input);
			 	            if (isverified==true)
			 	            {
			 	            	Application.showMessage("Verified Modem as"+sik_ProductCode);
			 	            }
			 	            else
			 	            {
			 	            	Application.showMessage("NOT Verified Modem as"+sik_ProductCode);
			 	            }
			 	             
			 	          } 
			 	            		 	            
			 	            String sik_DNCSIPAddress= sL.nodeFromKey(senddata,"<sik:DNCSIPAddress>","</sik:DNCSIPAddress>");
			 	            Application.showMessage("sik_DNCSIPAddress is ::"+sik_DNCSIPAddress); 
			 	           	 	            
			 	            String sik_HeadEndVendor= sL.nodeFromKey(senddata,"<sik:HeadEndVendor>","</sik:HeadEndVendor>");
			 	            Application.showMessage("sik_HeadEndVendor is ::"+sik_HeadEndVendor); 
			 	            
			 	           if(sik_HeadEndVendor==null)
				            {
					            c.report("Send Xml sik_HeadEndVendor is NULL");
					            continue;
				            }
				            else
				            {
				            	// Application.showMessage("sik_HeadEndVendor from Send Xml  from SIK is ::"+" "+sik_LastName);
				            	 if(sik_HeadEndVendor.equalsIgnoreCase(c.get("GL_headendType").toString()))
					             {
					            	 Application.showMessage("*******Validation Point ## :: WebServicall-sik_HeadEndVendor********");
					            	 Application.showMessage("Validation is Successfull with sik_HeadEndVendor::"+" "+sik_HeadEndVendor);
					            	 v4=1;
					             }
					             else
					             {
					            	 c.report("sik_HeadEndVendor at Send Xml not Validated as "+sik_HeadEndVendor);
					             }
				            }
			 	           
			 	        //-------------------------------------------------------------------------------------------------------//
			 	            //
			 	            //       					   Multiple product Code Section here.
			 	            //
			 	            //-------------------------------------------------------------------------------------------------------//
			 	           
			 	            
			 	            boolean i=true;
			 	             
			 	            if(i)
			 	            {
			 	            	String prod1null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code1").trim();
				 	            Application.showMessage("Product 1::"+prod1null);
				 	           
		                       		 	           
				 	           if( prod1null.isEmpty() || prod1null.equals(null) || prod1null.equals(""))
				 	           {
				 	        	   Application.showMessage("PRODUCT 1 is NULL..!");
				 	           }
				 	           else
				 	           {
			 	            	String Prod1= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code1"));
				 	            Application.showMessage("Product code 1:"+Prod1);
			 	                if(senddata.contains(Prod1))
			 	                {
			 	            	Application.showMessage("Validation is Sucess as Product ::"+Prod1+" exists..!");
			 	            	
			 	                 }
			 	               else
			 	                {
			 	                Application.showMessage("Doesnot Contains product..!!"+ Prod1);
			 	            	c.report("Doesnot Contains product..!!"+ Prod1);
			 	            	prod++;
			 	                }
				 	           }
				 	          String prod2null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code2").trim();
				 	            Application.showMessage("Product 2::"+prod2null);
				 	           
		                       		 	           
				 	           if( prod2null.isEmpty() || prod2null.equals(null) || prod2null.equals(""))
				 	           {
				 	        	   Application.showMessage("PRODUCT 2 is NULL..!");
				 	           }
				 	           else
				 	           {
			 	                String Prod2= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code2"));
			 	               Application.showMessage("Product code 2:"+Prod2);
			 	                 if(senddata.contains(Prod2))
			 	                  {
			 	            	Application.showMessage("Validation is Sucess as Product ::"+Prod2+" exists..!");
			 	                  }
			 	                else
			 	                 {
			 	            	prod++;
			 	            	c.report("Doesnot Contains product..!!"+ Prod2);
			 	                 }
				 	          }
				 	          String prod3null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code3").trim();
				 	            Application.showMessage("Product 3::"+prod3null);
				 	           
		                       		 	           
				 	           if( prod3null.isEmpty() || prod3null.equals(null) || prod3null.equals(""))
				 	           {
				 	        	   Application.showMessage("PRODUCT 3 is NULL..!");
				 	           }
				 	           else
				 	           {
			 	                  String Prod3= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code3"));
			 	                  Application.showMessage("Product code 3:"+Prod3);
			 	                  if(senddata.contains(Prod3))
			 	                  {
			 	            	      Application.showMessage("Validation is Sucess as Product ::"+Prod3+" exists..!");
			 	                  }
			 	                  else
			 	                  {
			 	            	prod++;
			 	            	c.report("Doesnot Contains product..!!"+ Prod3);
			 	                  }
				 	           }
				 	             String prod4null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code4").trim();
				 	            Application.showMessage("Product 4::"+prod4null);
				 	           
		                       		 	           
				 	           if( prod4null.isEmpty() || prod4null.equals(null) || prod4null.equals(""))
				 	           {
				 	        	   Application.showMessage("PRODUCT 4 is NULL..!");
				 	           }
				 	           else
				 	           {
			 	                   String Prod4= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code4"));
			 	                  if(senddata.contains(Prod4))
			 	                 {
			 	            	
			 	            	Application.showMessage("Validation is Sucess as Product ::"+Prod4+" exists..!");
			 	                 }
			 	                else
			 	               {
			 	            	prod++;
			 	            	c.report("Doesnot Contains product..!!"+ Prod4);
			 	                }
				 	           }
			 	            String prod5null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code5").trim();
			 	            Application.showMessage("product 5::"+prod5null);
			 	           
	                       		 	           
			 	           if( prod5null.isEmpty() || prod5null.equals(null) || prod5null.equals(""))
			 	           {
			 	        	   Application.showMessage("PRODUCT 5 is NULL..!");
			 	           }
			 	           else
			 	           {
			 	           String Prod5= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code5"));
			 	          
			 	           
			 	            if(senddata.contains(Prod5))
			 	            {
			 	            	Application.showMessage("Validation is Sucess as Product ::"+Prod5+" exists..!");
			 	            }
			 	            else
			 	            {
			 	            	prod++;
			 	            	c.report("Doesnot Contains product..!!"+ Prod5);
			 	            }
			 	           }
			 	           
			 	           //--------------------Product 6-------------------------
			 	          String prod6null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code6").trim();
			 	            Application.showMessage("product 6::"+prod6null);
			 	           
	                       		 	           
			 	           if( prod6null.isEmpty() || prod6null.equals(null) || prod6null.equals(""))
			 	           {
			 	        	   Application.showMessage("PRODUCT 6 is NULL..!");
			 	           }
			 	           else
			 	           {
			 	           String Prod6= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code6"));
			 	           
			 	            if(senddata.contains(Prod6))
			 	            {
			 	            	
			 	            	Application.showMessage("Validation is Sucess as Product ::"+Prod6+" exists..!");
			 	            }
			 	            else
			 	            {
			 	            	prod++;
			 	            	c.report("Doesnot Contains product..!!"+ Prod6);
			 	            }
			 	           }
			 	           //--------------------Product 7-----------
			 	          String prod7null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code7").trim();
			 	            Application.showMessage("product 7::"+prod7null);
			 	           
	                       		 	           
			 	           if( prod7null.isEmpty() || prod7null.equals(null) || prod7null.equals(""))
			 	           {
			 	        	   Application.showMessage("PRODUCT 7 is NULL..!");
			 	           }
			 	           else
			 	           {
			 	           String Prod7= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code7"));
			 	            if(senddata.contains(Prod7))
			 	            {
			 	            	Application.showMessage("Validation is Sucess as Product ::"+Prod7+" exists..!");
			 	            }
			 	            else
			 	            {
			 	            	prod++;
			 	            	c.report("Doesnot Contains product..!!"+ Prod7);
			 	            }
			 	           }
			 	           //--------------Product 8------------------------
			 	          String prod8null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code8").trim();
			 	            Application.showMessage("product 8::"+prod8null);
			 	           
	                       		 	           
			 	           if( prod8null.isEmpty() || prod8null.equals(null) || prod8null.equals(""))
			 	           {
			 	        	   Application.showMessage("PRODUCT 8 is NULL..!");
			 	           }
			 	           else
			 	           {
			 	           String Prod8= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code8"));
			 	            if(senddata.contains(Prod8))
			 	            {
			 	            	Application.showMessage("Validation is Sucess as Product ::"+Prod8+" exists..!");
			 	            }
			 	            else
			 	            {
			 	            	prod++;
			 	            	c.report("Doesnot Contains product..!!"+ Prod8);
			 	            }
			 	           }
			 	           //-----------------Product 9---------------------
			 	          String prod9null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code9").trim();
			 	            Application.showMessage("product 9::"+prod9null);
			 	           
	                       		 	           
			 	           if( prod9null.isEmpty() || prod9null.equals(null) || prod9null.equals(""))
			 	           {
			 	        	   Application.showMessage("PRODUCT 9 is NULL..!");
			 	           }
			 	           else
			 	           {
			 	           String Prod9= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code9"));
			 	            if(senddata.contains(Prod9))
			 	            {
			 	            	Application.showMessage("Validation is Sucess as Product ::"+Prod9+" exists..!");
			 	            }
			 	            else
			 	            {
			 	            	prod++;
			 	            	c.report("Doesnot Contains product..!!"+ Prod9);
			 	            }
			 	           }
			 	           //------------Product 10----------------------
			 	          String prod10null=c.getValue("dS_SIK", "Sik_Dotcom: Product_code10").trim();
			 	            Application.showMessage("product 10::"+prod10null);
			 	           
	                       		 	           
			 	           if( prod10null.isEmpty() || prod10null.equals(null) || prod10null.equals(""))
			 	           {
			 	        	   Application.showMessage("PRODUCT 10 is NULL..!");
			 	           }
			 	           else
			 	           {
			 	           String Prod10= getProductNameFromProductCode(c.getValue("dS_SIK", "Sik_Dotcom: Product_code10"));
			 	            if(senddata.contains(Prod10))
			 	            {
			 	            	Application.showMessage("Validation is Sucess as Product ::"+Prod10+" exists..!");
			 	            }
			 	            else
			 	            {
			 	            	prod++;
			 	            	c.report("Doesnot Contains product..!!"+ Prod10);
			 	            }
			 	           }
			 	           }
			 	            }
				            
			 	            else
			 	            {
			 	            Application.showMessage("not worked");
			 	            }
			 	         if(product==prod)
			 	         {
			 	        	 Application.showMessage("Multiple available Product component success");
			 	        	 product=1;
			 	        	 Application.showMessage("product="+product);
			 	         }
			 	         else
			 	         {
			 	        	 c.report("Multiple available product component-It is not picking all the product codes from the request");
			 	         }
			 	         
			 	         
			 	           
			 	            
			 	           	
			 	           		 	            
			 	            String sik_E911_acceptance= sL.nodeFromKey(senddata,"<sik:E911_acceptance>","</sik:E911_acceptance>");
			 	            Application.showMessage("sik_E911_acceptance is ::"+sik_E911_acceptance); 
			 	                      
			 	            String sik_AgentID= sL.nodeFromKey(senddata,"<sik:AgentID>","</sik:AgentID>");
			 	            Application.showMessage("sik_AgentID is ::"+sik_AgentID); 
			 	            
			 	            String sik_WorkOrderID= sL.nodeFromKey(senddata,"<sik:WorkOrderID>","</sik:WorkOrderID>");
			 	            Application.showMessage("sik_WorkOrderID is ::"+sik_WorkOrderID); 
			 	           
			 	         	if(recievedata.contains("<OrderID>")) 	
			 	         	{
			 	                
				 	            String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
				 	            Application.showMessage("OrderID is::"+OrderID_sik); 
				 	            AppContext.put("CancelOrderID", OrderID_sik,true);
				 	            c.put("OrderIDToCancel", OrderID_sik); 
				 	           c.put("OrderIDToCancel", OrderID_sik); 
				 	            Application.showMessage("The AppContext Order ID is::"+AppContext.get("CancelOrderID"));
				 	            Application.showMessage("The put Order ID is::"+c.get("OrderID_SIK"));
				 	            
				 	            Integer OrderId=Integer.parseInt(OrderID_sik);
				 	            sL.retOrderId(OrderId);
				 	            c.put("isErr", "false");
			 	         	}
			 	         	
			 	         	else if(recievedata.contains("<cct:code>Order-SubmitOrder-213</cct:code>"))
			 	            {
			 	         		
			 	         		AppContext.put("CancelOrderID", "1234",true);
			 	            	Application.showMessage("Order Id not created");
			 	            	//c.report("ORDER ID NOT FOUND..!");
			 	            	if(c.getValue("dS_SIK", "Sik_Dotcom: IsModify").equalsIgnoreCase("1"))
			 	            	{
			 	            	  Application.showMessage("Modify Order...!");
			 	            	}
			 	            	else
			 	            	{
			 	            		c.report("ORDER ID NOT FOUND..!");	
			 	            	}
			 	            }
			 	         	else if(recievedata.contains("<cct:code>Order-SubmitOrder-211</cct:code>"))
			 	            {
			 	         		c.put("isErr", "true");
			 	         		Application.showMessage("Dotcom accept can order only of 3 same product codes");
			 	         		//c.report("Dotcom accept can order only of 3 same product codes");
			 	            }
			 	      	 	  
			 	      	 	            
			 	            
			            break;
			            }
		             }
		         
		         
		         if(v1*callFound*v2*v3*v4*product ==1)
		         {
		         	Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
		         }
		         else
		         {
		        	 //OutPut=0;
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
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
	    
	    
	    
		         
		         
			 
	                  
		            
	    
	    
	    public void OrderUpdateFlagEnabled_Validate(String INOrdStatus ,String INbillingOrderId,String INinputChannel,String INsalesChannel, String INshipmentType,String INproductType,String INbillingSystem,String INordType,String INcustomerType,String WORKORDER_ID,Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException, ParserConfigurationException 
		{
			 Thread.sleep(30000); // Think time in JVM [waits for 10 secs here]
		     String Time= c.get("BaseTime").toString();
			 sikLibraryClass sL = new sikLibraryClass();
			 ResultSet  rs;
			 String order="COM";
			 int v4=0,v1=0,v2=0,v3=0,v5=0,v6=0,v7=0,v8=0,v9=0,v10=0,v11=0,v12=0,v13=0,v14=0,v15=0,v16=0;
			 int callFound=0;
			 String xmldata1 ="receive_data";
		     String xmldata2 ="SEND_DATA";
		     String currentDate=sL.getsysTime();
		     String AccountNumber=c.getValue("accountNumber");
	         Application.showMessage("Date of execution is ::"+currentDate);
		     Application.showMessage("--------------------------------------------------------");
		     Application.showMessage("***********OrderUpdate_Validate Function**************");       
		     Application.showMessage("--------------------------------------------------------");
		    
			 try
			 {
				 Statement st =sL. dBConnect(input, c);	
		         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate'  and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+Time+"' and USER_DATA2='"+AccountNumber+"' order by creation_time desc)where rownum <= 20");
		         while (rs.next())
		         {
		        	
		        	String rowmsg;
					rowmsg = rs.getString(1);
			        Application.showMessage("MessageID is::"+rowmsg);
			        if(rs.getBlob("SEND_DATA")==null)
		            {
		            	Application.showMessage("Your SEND XML is NULL \n");	
		            	String recievedata=sL.extractXml(rs,xmldata1);
		            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
		            }
		            else if(rs.getBlob("receive_data")==null) 
		            { 
		                          
			            String senddata = sL.extractXml(rs,xmldata2);           
			            Application.showMessage("senddata is ::"+senddata); 
			          
			         
		 	            String billingAccountNo= sL.nodeFromKey(senddata,"<id>","</id>");
			       	    Application.showMessage("billingAccountNo is ::" +billingAccountNo);	       	    
			       	    v1= sL.verificationPoint(billingAccountNo, INbillingOrderId, input, c);
			       	    
			       	    String ordStatus= sL.nodeFromKey(senddata,"<value name=\"ordStatus\">","</value><value name=\"ordType\">");
	                    Application.showMessage("The ordStatus from Request is::"+ordStatus);                       
	                    v2= sL.verificationPoint(ordStatus, INOrdStatus, input, c);
	                    
			            if(v1*v2==1)
	                    {
	                    	callFound=1;
	                    }
	                    else 
	                    {
	                    	continue;
	                    }
	                    
			            if(ordStatus.equals(order))
	                    {
	                    
	                    	sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
	                    }
	                    
			            else
			            {
	                    if(callFound==1)
	                    {
	                    	String sendDataT=sL.RemoveNameSpaces(senddata);
	                    	
	                    	String guid= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[10]");
	                        Application.showMessage("The guid from Request is::"+guid);                       
	                        v3= sL.validationPointIgnoreCase(guid, INbillingOrderId, input, c);
	                        
	                      
	                    	String accountNumber= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[9]");
	                        Application.showMessage("The accountNumber from Request is::"+accountNumber);                       
	                        v4= sL.validationPointIgnoreCase(accountNumber, AccountNumber, input, c);
	                        
	                        String INcorpID= sL.makeCorpIDfromAccountNumber(accountNumber, INbillingSystem);                  
	                       
	                        String corpId= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[8]");
	                        Application.showMessage("The corpId from Request is::"+corpId);                       
	                        v5= sL.validationPointIgnoreCase(corpId, INcorpID, input, c);
	                        
	                        
	                        String billingOrderId= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[7]");
	                        Application.showMessage("The billingOrderId from Request is::"+billingOrderId);                       
	                        v6= sL.validationPointIgnoreCase(billingOrderId, WORKORDER_ID, input, c);
	                        
	                       
	                        String ordType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[6]");
	                        Application.showMessage("The ordType from Request is::"+ordType);                       
	                        v7= sL.validationPointIgnoreCase(ordType, INordType, input, c);
	                    	
	                        
	                       
	                        String ordSource= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[5]");
	                        Application.showMessage("The ordSource from Request is::"+ordSource);                       
	                       
	                        String errorCode= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[4]");
	                        Application.showMessage("The errorCode from Request is::"+errorCode);                       
	                      
	                        
	                        String errorText= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[3]");
	                        Application.showMessage("The errorText from Request is::"+errorText);                       
	                      
	                        
	                        
	                        String customerType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value[2]");
	                        Application.showMessage("The customerType from Request is::"+customerType);                       
	                      
	                        
	                       
	                        String productType= sL.GetValueByXPath(sendDataT, "/comRequest/header/value");
	                        Application.showMessage("The billingSystem from Request is::"+productType);                       
	                        
	                        
	                        
	                        //worklist...........................
	                 
	                    
	                        Application.showMessage("----------------******Worklist******---------------");
	                        
	                        String customerTypewl= sL.GetValueByXPath(sendDataT, "/comRequest/wlRequest/worklist/value");
	                        Application.showMessage("The customerType in worklist from OrderUpdate is::"+customerTypewl);                       
	                     
	                        
	                        String ordStatuswl= sL.GetValueByXPath(sendDataT, "/comRequest/wlRequest/worklist/value[2]");
	                        Application.showMessage("The ordStatus in worklist from Request is::"+ordStatuswl);                       
	                       
	                        
	                        
	                     
	                        String errorCodewl= sL.GetValueByXPath(sendDataT, "/comRequest/wlRequest/worklist/value[3]");
	                        Application.showMessage("The errorCode in worklist from OrderUpdate is::"+errorCodewl);                       
	                      
	                        
	                        
	                       
	                        String detailswl= sL.GetValueByXPath(sendDataT, "/comRequest/wlRequest/worklist/value[4]");
	                        Application.showMessage("The details in worklist from Request is::"+detailswl);                       
	                        
	                       
	                        
	                    }
		            } 
		            }
		 	            
		            break;
		            }
		         if(v1*v2*v3*v4*v5*v6*v7*callFound ==1)
		         {
		        	 OutPut=1;
		         	 Application.showMessage("WebService Call :: OrderUpdate is Success[All validation points are Success]");
	                 
		         }
		         else
		         {
		        	 OutPut=0;
		        	 c.put("result", "false");
		        	 c.report("WebService Call ::Order Update is Failed with Validation Errors");

		         }
		         st.close();
			}	
			catch (SQLException e)
			{
			    System.out.println("Connection Failed! Check output console");
				e.printStackTrace();
				
			}
			 
			// return OutPut; 
		 }
	    
	    
	                        
	                        
	                      
	                        
	                     	                        
	                   
		             
		    
		         
		         
	    
	    
	    public void DotcomWebServiceCalls(Object input, ScriptingContext c) throws Exception
	    {
	  	  Application.showMessage("The Result before the Execution is::"+sD.OutPut);
	  	  Thread.sleep(50000);
	  	  String Flag=c.getValue("dS_SIK","Sik_Dotcom: FlagEnabled");
	  	  Application.showMessage("Flag ::"+Flag);
	  	  String Operation=c.getValue("dS_SIK","Sik_Dotcom: Operation");
	  	  String AccountNumber=c.getValue("dS_SIK", "Sik_Dotcom: AccountNo");
	  	 if(Flag.equalsIgnoreCase("YES"))
	  	 {
	  	  if (c.getValue("acquisitionMethod").equalsIgnoreCase("SHIPPED"))
	  	  {
	  		
	  		  if(Operation.equals("Submit"))
	  			 {
	  				 Application.showMessage("----------------Operation is Submit-------------------------");
	  		  
	  		         Thread.sleep(20000);
	  		  sD.bfcRequest_Validate(input, c);
	  		  sD.getLocation_Validate(input, c);
	  		  Thread.sleep(3000);
	  		  sD.queryContract_Validate(input, c);
	  		  Thread.sleep(7000);
	  		  submitOrder_Validate(input, c);
	  		  sD.modifyServiceableLocation_Validate(input, c);
	  		  sD.oneStopSik_Validate(input, c);
	  		  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
	  		  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
	  		  
	  		  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
	  		  String Channel=c.getValue("dS_SIK", "Sik_Dotcom: XML_Input Channel");
	  		  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, Channel, Channel, "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
	  		  OrderUpdateFlagEnabled_Validate("ERR", INbillingOrderId, Channel, Channel, "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
	  		  Thread.sleep(4000);
	  		  //sD.modifyServiceableLocation_Validate(input, c);
	  		  sD.confirmServiceRequest_Validate(input, c);	
	  			 }
	  		  else
	  			 {
	  				 Application.showMessage("----------------Operation is not Submit-------------------------");
	  				 sD.bfcFailureRequest_Validate(input, c);
	  				 sD.NoDataSetFound(input, c, "sik:OrderSoap/SubmitOrder", AccountNumber);
	  				 sD.NoDataSetFound(input, c, "sik:OrderSoap/GetMarketAvailability", AccountNumber);
	  				 sD.NoDataSetFound(input, c, "ContractServices:ContractServicePort/queryContract", AccountNumber);
	  				 sD.NoDataSetFound(input, c, "ContractServices:ContractServicePort/queryContract", AccountNumber);
	  				 sD.NoDataSetFound(input, c, "ls:LocationServicePort/getLocation", AccountNumber);
	  			 }
	  		  
	  	  }
	  	  
	  	 
	  	  else if (c.getValue("acquisitionMethod").equalsIgnoreCase("PROFESSIONAL_INSTALL"))
	  	  {
	  		  Application.showMessage("Validation for SIK DOTCOM PROFFESSIONAL INSTALL...");
	  		  Thread.sleep(20000);
	  		  sD.bfcRequest_Validate(input, c);
	  		  sD.getLocation_Validate(input, c);
	  		  Thread.sleep(3000);
	  		  sD.queryContract_Validate(input, c);
	  		  Thread.sleep(7000);
	  		  //sD.submitOrder_Validate(input, c);
	  		 // sD.orderUpdate_Validate(input, c);
	  		  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
	  		  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
	  		  
	  		 // sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID,input, c);
	  		 // sD.OrderUpdateFlagEnabled_Validate("COM", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
	  		  sD.modifyServiceableLocation_Validate(input, c);
	  		  sD.oneStopSik_Validate(input, c);
	  		  sD.confirmServiceRequest_Validate(input, c);	
	  		  
	  		 
	  		  
	  	  }
	  	  else
	  	  {
	  		  
	  		  Thread.sleep(10000);
	  		  sD.bfcRequest_Validate(input, c);
	  		  Thread.sleep(20000);
	  		  sD.getLocation_Validate(input, c);
	  		  Thread.sleep(3000);
	  		  sD.queryContract_Validate(input, c);
	  		  Thread.sleep(7000);
	  		  submitOrder_Validate(input, c);
	  		  sD.modifyServiceableLocation_Validate(input, c);
	  		  sD.oneStopSik_Validate(input, c);
	  		  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
	  		  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
	  		  
	  		  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
	  		  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
	  		  OrderUpdateFlagEnabled_Validate("ERR", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
	  		  Thread.sleep(4000);
	  		  //sD.modifyServiceableLocation_Validate(input, c);
	  		  sD.confirmServiceRequest_Validate(input, c);	
	  		  
	  		  if(c.getValue("dS_SIK", "Sik_Dotcom: IsModify").equalsIgnoreCase("1"))
	  		  {
	  			  if(c.getValue("dS_SIK", "Sik_Dotcom: signatureReq").equalsIgnoreCase("true"))
	  			  {
	  				  sD.getOrderDetails_Validate(input, c);
	  				  sD.ModifyOrder_Validate(input, c);
	  			  }
	  			  else if(c.getValue("dS_SIK", "Sik_Dotcom: signatureReq").equalsIgnoreCase("false"))
	  			  {
	  				  sD.ModifyOrder_Validate(input, c); 
	  				  sD.getOrderDetails_Validate(input, c);
	  				  
	  			  }
	  			  else
	  			  {
	  				  
	  			  }
	  		  }
	  		  else
	  		  {
	  			  
	  			 
	  			/*  
	  			  String cycleid=c.getValue("dS_SIK", "Sik_Dotcom: TestSetID");
	  			  String testid =c.getValue("dS_SIK", "Sik_Dotcom: TestCaseID");
	  			  String[] TestCases;
	  			  TestCases=sL.TestCaseGroup(testid,c,input);
	  			  
	  			  if(sD.OutPut==1)
	  			  {
	  				 
	  				 for(int i=0; i<TestCases.length;i++)
	  				 {
	  				  ALMTestResultUpdater.updateRunStatus(cycleid, TestCases[i], "Passed");
	  				  
	  				 }
	  			  }
	  			  else
	  			  {
	  				  for(int i=0; i<TestCases.length;i++)
	  				  {
	  					  
	  					  ALMTestResultUpdater.updateRunStatus(cycleid, TestCases[i], "Failed");
	  					  
	  				  }
	  			  }
	  			  */
	  		  }
	  	  }
	  	  }
	  	 else
	  	 {
	  		  Thread.sleep(10000);
	  		  sD.bfcRequest_Validate(input, c);
	  		  Thread.sleep(20000);
	  		  sD.getLocation_Validate(input, c);
	  		  Thread.sleep(3000);
	  		  sD.queryContract_Validate(input, c);
	  		  Thread.sleep(7000);
	  		  submitOrder_Validate(input, c);
	  		  String WORKORDER_ID=c.getValue("dS_SIK", "Sik_Dotcom: WORKORDER_ID");
	  		  String INbillingOrderId=c.getValue("dS_SIK", "Sik_Dotcom: serviceReqID");
	  		  
	  		  String INbillingSystem=c.getValue("dS_SIK", "Sik_Dotcom: BillingSystem");
	  		  String Channel=c.getValue("dS_SIK", "Sik_Dotcom:  XML_Input Channel");
	  		  sD.OrderUpdateFlagEnabled_Validate("INI", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
	  		  OrderUpdateFlagEnabled_Validate("ERR", INbillingOrderId, "DOT_COM", "DOT_COM", "STD", "SIK", INbillingSystem, "NEW", "RES",WORKORDER_ID, input, c);
	  		  //sD.modifyServiceableLocation_Validate(input, c);
	  		  sD.confirmServiceRequest_Validate(input, c);
	  	 }
	
	    }
	    
	    // order Update
	   
		   
	}
