import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;

public class Negative_Scenarios 
{
	 RTP_ValidationsClass_UpDown vC=new RTP_ValidationsClass_UpDown();
	 RTP_ValidationsClass rV=new RTP_ValidationsClass();
	 LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new LibraryRtp();
	 
	 public static String rowmsg= null;

public void processHomeSecurityInfo_ValidateForNegativeScenarios(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
 // Think time in JVM [waits for 10 secs here]
    
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     c.setValue("processHomeSecurity","true");
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
     Application.showMessage("----------------------------------------------------");
     lr.findThinktimeOperationRTP(input, c);
     c.setValue("processHomeSecurity","false");   
	try
	{
		// Statement st =lC. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' order by creation_time desc)where rownum <= 20");
	    
	    rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            rowmsg=rs.getString(1);
            if(rs.getBlob("receive_data")==null)
            {
            
            	Application.showMessage("Your Recieve XML is NULL \n");
            	
            	String senddata =lC.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            	Application.showMessage("*******Validation Point 1 :: WebServicall-Recieve data Call is successful*******");
            	v5=1;
            }
           /* else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");	
            	String recievedata=lC.extractXml(rs,xmldata1);
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);     
            	
            }*/
            else
            { 
                          
	            String senddata = lC.extractXml(rs,xmldata2);           
	            String recievedata = lC.extractXml(rs,xmldata1);      
	          	           
	            String accountNumber_DDS= lC.nodeFromKey(senddata,"<messagesDDS:accountNumber>","</messagesDDS:accountNumber>");
 	            Application.showMessage("accountNumber is ::"+accountNumber_DDS);
 	            
	            if(accountNumber_DDS.equals(c.getValue("ACC_input")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_DDS);
            		
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	 	            String firstName_DDS= lC.nodeFromKey(senddata,"<typesDDS:firstName>","</typesDDS:firstName>");
	 	            Application.showMessage("firstName is ::"+firstName_DDS);
	 	            
	 	            if(firstName_DDS==null)
		            {
			            c.report("Send Xml FirstName is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+firstName_DDS);
		            	 if(firstName_DDS.equals(c.getValue("FirstName")))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
			            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+firstName_DDS);
			            	 v1=1;
			             }
			             else
			             {
			            	 v1=1;
			            	// c.report("FirstName at Send Xml not Validated as "+firstName_DDS);
			             }
		            }		

	 	            String lastName_DDS= lC.nodeFromKey(senddata,"<typesDDS:lastName>","</typesDDS:lastName>");
	 	            Application.showMessage("lastName is ::"+lastName_DDS); 
	 	            
	 	           if(lastName_DDS==null)
		            {
			            c.report("Send Xml LastName is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+lastName_DDS);
		            	 if(lastName_DDS.equals(c.getValue("LastName")))
			             {
			            	 Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
			            	 Application.showMessage("Validation is Successfull with FirstName::"+" "+lastName_DDS);
			            	 v2=1;
			             }
			             else
			             {
			            	 v2=1;
			            	// c.report("LastName at Send Xml not Validated as "+lastName_DDS);
			             }
		            }

	 	            String homeSecurityLOBStatus_DDS= lC.nodeFromKey(senddata,"<typesDDS:homeSecurityLOBStatus>","</typesDDS:homeSecurityLOBStatus>");
	 	            Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
	 	            if(homeSecurityLOBStatus_DDS==null)
		            {
			            c.report(" homeSecurityLOBStatus is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("homeSecurityLOBStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+homeSecurityLOBStatus_DDS);
		            	 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("PENDING_INSTALL") || homeSecurityLOBStatus_DDS.equalsIgnoreCase("DISCONNECTED"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
			            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
			            	 v3=1;
			             }
		            	 else if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("ACTIVE"))
			             {
		            		 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
			            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
			            	 v3=1;			            
			             }
			             else 
			             {
			            	Application.showMessage("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+homeSecurityLOBStatus_DDS);
			            	continue;
			             }
		            }
	 	            
	 	            String permitRequired_DDS= lC.nodeFromKey(senddata,"<typesDDS:permitRequired>","</typesDDS:permitRequired>");
	 	            Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
	 	           
	 	         	 	            
	 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
	 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
	 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
	 	            
	 	            String responseStatus_DDS= lC.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/esd-directory/14.05/types\">","</typ:responseStatus>");
	 	            Application.showMessage("responseStatus is::"+responseStatus_DDS); 
	 	           if(responseStatus_DDS==null)
		            {
			            c.report(" responseStatus is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("responseStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+responseStatus_DDS);
		            	 if(responseStatus_DDS.equalsIgnoreCase("Success"))
			             {
			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-responseStatus********");
			            	 Application.showMessage("Validation is Successfull with responseStatus::"+" "+responseStatus_DDS);
			            	 v4=1;
			             }
			             else
			             {
			            	 c.report("responseStatus at Send Xml not Validated as "+responseStatus_DDS);
			             }
		            }
	 	                    

	 	            
	            break;
	            }
             }
         }
         
         if(v1*callFound*v2*v3*v4*v5 ==1)
         {
         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
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

public void queryLocation_ValidateForNegativeScenarios(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new  LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     c.setValue("queryLocation","true");
     Application.showMessage("-------------------------------------------------");
     Application.showMessage("*****Query Location _Validate Function******");       
     Application.showMessage("-------------------------------------------------");
     lr.findThinktimeOperationRTP(input, c);
     c.setValue("queryLocation","false");  
	try
	{
		// Statement st =lC. dBConnect(input, c);	
        // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
     
            rowmsg=rs.getString(1);
            Application.showMessage("Sruthiii");  
            if(rs.getBlob("receive_data")==null)
            {
            
            	Application.showMessage("Your Recieve XML is NULL \n");
            	
            	String senddata =lC.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            	Application.showMessage("*******Validation Point  :: WebServicall-recieve data _Validate is successfull********");
            	v2=1;
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	Application.showMessage("Your SEND XML is NULL \n");             
                String recievedata=lC.extractXml(rs,xmldata1);
                Application.showMessage("RECIEVE XML is ::\n"+recievedata);     
            }
            else
            { 
                          
	            String senddata = lC.extractXml(rs,xmldata2);           
	            String recievedata = lC.extractXml(rs,xmldata1);      
	          	           
	            String legacyID= lC.nodeFromKey(senddata,"<lt:legacyID>","</lt:legacyID>");	            
	            Application.showMessage("legacyID is ::"+legacyID); 
	            if(legacyID.equals(c.getValue("sHOUSE_KEY")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-createCMSAccountID_Validate********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+legacyID);
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	      
	 	            String locationID= lC.nodeFromKey(recievedata,"<typ:locationID xmlns:typ=\"http://xml.comcast.com/location/types\">","</typ:locationID>");
	 	            Application.showMessage("locationID is ::"+locationID);
	 	            c.setValue("LocationID", locationID);
	 	            
	 	            
	 	            String locationStatus= lC.nodeFromKey(recievedata,"<typ:locationStatus xmlns:typ=\"http://xml.comcast.com/location/types\">","</typ:locationStatus>");
	 	            Application.showMessage("locationStatus is :: "+locationStatus); 
	 	            
	 	           if(locationStatus==null)
		             {
			            c.report("Send Xml Account Number is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("locationStatus from Send Xml  from queryLocation is ::"+" "+locationStatus);
		            	 if(locationStatus.equalsIgnoreCase("Complete"))
			             {
			            	 Application.showMessage("*******Validation Point 2 :: WebServicall-locationStatus********");
			            	 Application.showMessage("Validation is Successfull with AccountNumber::"+" "+locationStatus);
			            	 v1=1;
			             }
			             else
			             {
			            	 c.report("locationStatus at Send Xml not Validated as "+locationStatus);
			             }
		            }		

	 	            String houseNumber= lC.nodeFromKey(recievedata,"<typ:houseNumber>","</typ:houseNumber>");
	 	            Application.showMessage("houseNumber is :: "+houseNumber); 
	 	            c.setValue("HouseNumber", houseNumber);
	 	            
	 	            String streetName= lC.nodeFromKey(recievedata,"<typ:streetName>","</typ:streetName>").trim();
	 	            Application.showMessage("streetName is :: "+streetName); 
	 	            c.setValue("StreetName", streetName);
	 	            
	 	            String streetSuffix= lC.nodeFromKey(recievedata,"<typ:streetSuffix>","</typ:streetSuffix>").trim();
	 	            Application.showMessage("streetSuffix is :: "+streetSuffix); 
	 	             
	 	            String city= lC.nodeFromKey(recievedata,"<typ:city>","</typ:city>").trim();
	 	            Application.showMessage("city is :: "+city); 
	 	            c.setValue("City", city);
	 	           
	 	            String policeNumber= lC.nodeFromKey(recievedata,"<typ:policeNumber>","</typ:policeNumber>");
	 	            Application.showMessage("policeNumber is :: "+policeNumber); 
	 	            c.setValue("PoliceNumber", policeNumber);
	 	            
	 	            String TimeZone= lC.nodeFromKey(recievedata,"<typ:Name>","</typ:Name>");
	 	            Application.showMessage("TimeZone is :: "+TimeZone); 
	 	            c.put("pTimeZone", TimeZone);
	 	           
	 	            String Loc_fireNumber= lC.nodeFromKey(recievedata,"<typ:fireNumber>","</typ:fireNumber>");
	 	            Application.showMessage("fireNumber is :: "+Loc_fireNumber); 
	 	            c.setValue("FireNumber", Loc_fireNumber);
	 	           
	 	            String Loc_medicalNumber= lC.nodeFromKey(recievedata,"<typ:medicalNumber>","</typ:medicalNumber>");
	 	            Application.showMessage("medicalNumber is :: "+Loc_medicalNumber); 
	 	            c.setValue("medicalNumber", Loc_medicalNumber);
	            break;
	            }
             }
         }
         if(callFound==1)
         {
         	Application.showMessage("WebService Call :: Query Location is Success[All validation points are Success]");
         }
         else
         {
        	 c.put("result", "false");
        	 c.report("WebService Call :: Query Location is Failed with Validation Errors");
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
public void responderInfo_ValidateForNegativeScenarios(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
                Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
     LibraryRtp  lC= new LibraryRtp ();
                ResultSet  rs;
                int callFound=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0;
                String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     c.setValue("responderInfo","true");
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("***********responderInfo_Validate Function***********");       
     Application.showMessage("-----------------------------------------------------");
     lr.findThinktimeOperationRTP(input, c);
     c.setValue("responderInfo","false");   
                try
                {
                             //   Statement st =lC. dBConnect(input, c); 
       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'intrado:frlResource/responderInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
                	rs=lr.reduceThinkTimeRTP(input, c);
         Application.showMessage("rs"+rs);
         while (rs.next())
         {
                
        	 Application.showMessage("Entering while -----------------------------------------------------");
            rowmsg=rs.getString(1);
            if(rs.getBlob("receive_data")==null)
            {
            
                Application.showMessage("Your Recieve XML is NULL \n");
                
                String senddata =lC.extractXml(rs,xmldata2);
                Application.showMessage("Your Recieve XML is::\n"+senddata);
                v7=1;
            }
            /*else if(rs.getBlob("SEND_DATA")==null)
            {
                Application.showMessage("Your SEND XML is NULL \n");             
                String recievedata=lC.extractXml(rs,xmldata1);
                Application.showMessage("RECIEVE XML is ::\n"+recievedata);       
            }*/
            else
            { 
                          
                            String senddata = lC.extractXml(rs,xmldata2);           
                            String recievedata = lC.extractXml(rs,xmldata1);      
                                           
                            String intrado_HouseNum= lC.nodeFromKey(senddata,"<intrado:HouseNum>","</intrado:HouseNum>");
                           Application.showMessage("intrado:HouseNum is ::"+intrado_HouseNum); 
                            if(intrado_HouseNum.equals(c.getValue("HouseNumber")))
                {
                                Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                Application.showMessage("SEND XML is :: \n"+senddata);
                                //System.out.printf("SEND XML is %s \n",senddata);
                                Application.showMessage("*******Validation Point 1 :: WebServicall-responderInfo_Validate********");
                                Application.showMessage("Validation is Successfull with House Number :"+intrado_HouseNum);
                                callFound=1;
                }
                else
                {
                                continue;
                }
                
                            if(callFound==1)
                            {
                      
                                            

                                           String intrado_PrefixDirectional= lC.nodeFromKey(senddata,"<intrado:PrefixDirectional>","</intrado:PrefixDirectional>").trim();
                                           Application.showMessage("intrado:PrefixDirectional is ::"+intrado_PrefixDirectional); 

                                           String intrado_StreetName= lC.nodeFromKey(senddata,"<intrado:StreetName>","</intrado:StreetName>").trim();
                                           Application.showMessage("intrado:StreetName is ::"+intrado_StreetName); 
                                           if(intrado_StreetName==null)
                                            {
                                                            c.report("Send Xml intrado_StreetName is NULL");
                                                            continue;
                                            }
                                            else
                                            {
                                                 Application.showMessage("intrado_StreetName from Send Xml is ::"+" "+intrado_StreetName);
                                                 if(intrado_StreetName.equalsIgnoreCase(c.getValue("StreetName")))
                                                             {
                                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-intrado_StreetName********");
                                                                 Application.showMessage("Validation is Successfull with intrado_StreetName::"+" "+intrado_StreetName);
                                                                 v2=1;
                                                             }
                                                             else
                                                             {
                                                                 c.report("intrado_StreetName at Send Xml not Validated as "+intrado_StreetName);
                                                             }
                                            }  
                                           

                                           String intrado_StreetSuffix= lC.nodeFromKey(senddata,"<intrado:StreetSuffix>","</intrado:StreetSuffix>").trim();
                                           Application.showMessage("intrado:StreetSuffix is ::"+intrado_StreetSuffix); 
                                            
                                            String intrado_UnitType= lC.nodeFromKey(senddata,"<intrado:UnitType>","</intrado:UnitType>").trim();
                                           Application.showMessage("intrado:UnitType is ::"+intrado_UnitType); 
                                            
                                            String intrado_PostalCommunity= lC.nodeFromKey(senddata,"<intrado:PostalCommunity>","</intrado:PostalCommunity>").trim();
                                           Application.showMessage("intrado:PostalCommunity is ::"+intrado_PostalCommunity); 
                                            
                                            String intrado_StateProvince= lC.nodeFromKey(senddata,"<intrado:StateProvince>","</intrado:StateProvince>").trim();
                                           Application.showMessage("intrado:StateProvince is ::"+intrado_StateProvince); 
                                            
                                            String intrado_PostalZipCode= lC.nodeFromKey(senddata,"<intrado:PostalZipCode>","</intrado:PostalZipCode>").trim();
                                           Application.showMessage("intrado:PostalZipCode is ::"+intrado_PostalZipCode); 
                                            if(intrado_PostalZipCode==null)
                                            {
                                                            c.report("Send Xml intrado_PostalZipCode is NULL");
                                                            continue;
                                            }
                                            else
                                            {
                                                 Application.showMessage("PostalCode from Send Xml   is ::"+" "+intrado_PostalZipCode);
                                                 if(intrado_PostalZipCode.equals(c.get("QpostalCode").toString().trim()))
                                                             {
                                                                 Application.showMessage("*******Validation Point 3 :: WebServicall-intrado_PostalZipCode********");
                                                                 Application.showMessage("Validation is Successfull with intrado_PostalZipCode::"+" "+intrado_PostalZipCode);
                                                                 v3=1;
                                                             }
                                                             else
                                                             {
                                                                 c.report("intrado_PostalZipCode at Send Xml not Validated as "+intrado_PostalZipCode);
                                                             }
                                            }  
                                           
                                            
                                            
                                                                                            
                                            String sch_overallMatch= lC.nodeFromKey(recievedata,"<sch:overallMatch>","</sch:overallMatch>");
                                           Application.showMessage("sch:overallMatch is::"+sch_overallMatch);
                                           
                                            if(sch_overallMatch==null)
                                             {
                                                            c.report("Send Xml sch_overallMatch is NULL");
                                                            continue;
                                             }
                                            else
                                            {
                                                 if(sch_overallMatch.equalsIgnoreCase("Success"))
                                                             {
                                                                 Application.showMessage("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
                                                                 Application.showMessage("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
                                                                 v4=1;
                                                             }
                                                 else if(sch_overallMatch.equalsIgnoreCase("Altered"))
                                                             {
                                                                 Application.showMessage("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
                                                                 Application.showMessage("Validation is Successfull with sch_overallMatch::"+" "+sch_overallMatch);
                                                                 v4=1;
                                                             }
                                                             else
                                                             {
                                                                 c.report("sch_overallMatch at Send Xml not Validated as "+sch_overallMatch);
                                                             }
                                            }
                                           
                                            
                                            String sch_PoliceContactInfo= lC.nodeFromKey(recievedata,"<sch:PoliceContactInfo>","</sch:PoliceContactInfo>");
                                           Application.showMessage("sch:PoliceContactInfo is::"+sch_PoliceContactInfo);
                                           
                                           if(sch_PoliceContactInfo==null)
                                            {
                                                            c.report("Send Xml sch_PoliceContactInfo is NULL");
                                                            continue;
                                            }
                                            else
                                            {
                                                 Application.showMessage("sch_PoliceContactInfo from Send Xml   is ::"+" "+sch_PoliceContactInfo);
                                                 if(sch_PoliceContactInfo.equals(c.getValue("PoliceNumber")))
                                                             {
                                                                 Application.showMessage("*******Validation Point 5 :: WebServicall-sch_PoliceContactInfo********");
                                                                 Application.showMessage("Validation is Successfull with sch_PoliceContactInfo::"+" "+sch_PoliceContactInfo);
                                                                 v5=1;
                                                             }
                                                             else
                                                             {
                                                                 c.report("sch_PoliceContactInfo at Send Xml not Validated as "+sch_PoliceContactInfo);
                                                             }
                                            }  
                                           
                                            
                                            
                                            String sch_FireContactInfo= lC.nodeFromKey(recievedata,"<sch:FireContactInfo>","</sch:FireContactInfo>");
                                           Application.showMessage("sch:FireContactInfo is::"+sch_FireContactInfo);
                                           
                                           if(sch_FireContactInfo==null)
                                            {
                                                            c.report("Send Xml sch_FireContactInfo is NULL");
                                                            continue;
                                            }
                                            else
                                            {
                                                 Application.showMessage("sch_FireContactInfo from Send Xml   is ::"+" "+sch_FireContactInfo);
                                                 if(sch_FireContactInfo.equals(c.getValue("FireNumber")))
                                                             {
                                                                 Application.showMessage("*******Validation Point 6 :: WebServicall-sch_FireContactInfo********");
                                                                 Application.showMessage("Validation is Successfull with sch_FireContactInfo::"+" "+sch_FireContactInfo);
                                                                 v6=1;
                                                             }
                                                             else
                                                             {
                                                                 c.report("sch_FireContactInfo at Send Xml not Validated as "+sch_FireContactInfo);
                                                            }
                                            }  
                                           
                                            
                                            
                                            String sch_MedicalContactInfo= lC.nodeFromKey(recievedata,"<sch:MedicalContactInfo>","</sch:MedicalContactInfo>");
                                           Application.showMessage("sch:MedicalContactInfo is::"+sch_MedicalContactInfo);
                                           
                                           if(sch_MedicalContactInfo==null)
                                            {
                                                            c.report("Send Xml sch_MedicalContactInfo is NULL");
                                                            continue;
                                            }
                                            else
                                            {
                                                 Application.showMessage("sch_MedicalContactInfo from Send Xml   is ::"+" "+sch_MedicalContactInfo);
                                                 if(sch_MedicalContactInfo.equals(c.getValue("medicalNumber")))
                                                             {
                                                                 Application.showMessage("*******Validation Point 7 :: WebServicall-intrado_PostalZipCode********");
                                                                 Application.showMessage("Validation is Successfull with intrado_PostalZipCode::"+" "+sch_MedicalContactInfo);
                                                                 v7=1;
                                                             }
                                                             else
                                                             {
                                                                 c.report("sch_MedicalContactInfo at Send Xml not Validated as "+sch_MedicalContactInfo);
                                                             }
                                            }  
                                           
                                            
                                            
                            break;
                            }
             }
         }
         if(callFound*v2*v4*v5*v6*v7 ==1)
         {
                Application.showMessage("WebService Call :: Intrado is Success[All validation points are Success]");
         }
         else
         {
                 c.put("result", "false");
                 c.report("WebService Call ::Intrado is Failed with Validation Errors");
         }
         //st.close();
                }              
                catch (SQLException e)
                {
                    System.out.println("Connection Failed! Check output console");
                                e.printStackTrace();
                                return;
                }
}
public void processHomeSecurityInfo_Validate_DDS(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
{
	 // Think time in JVM [waits for 10 secs here]
     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
     LibraryRtp lr=new  LibraryRtp();
	 ResultSet  rs;
	 int callFound=0,v1=0,v2=0,v3=0,v4=0;
	 String xmldata1 ="receive_data";
     String xmldata2 ="SEND_DATA";
     String Time= c.get("BaseTime").toString();
     c.setValue("processHomeSecurity","true");
     Application.showMessage("-----------------------------------------------------");
     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
     Application.showMessage("----------------------------------------------------");
     lr.findThinktimeOperationRTP(input, c);
     c.setValue("processHomeSecurity","false");    
	try
	{
		// Statement st =lC. dBConnect(input, c);	
      //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
		rs=lr.reduceThinkTimeRTP(input, c);
         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
         {
        	
        
            String rowmsg = rs.getString(1);
            if(rs.getBlob("receive_data")==null)
            {
            
            	Application.showMessage("Your Recieve XML is NULL \n");
            	
            	String senddata =lC.extractXml(rs,xmldata2);
            	Application.showMessage("Your Recieve XML is::\n"+senddata);
            	v3=1;
            }
            else if(rs.getBlob("SEND_DATA")==null)
            {
            	/*Application.showMessage("Your SEND XML is NULL \n");	
            	String recievedata=lC.extractXml(rs,xmldata1);
            	Application.showMessage("RECIEVE XML is ::\n"+recievedata);      */ 
            }
            else
            { 
                          
	            String senddata = lC.extractXml(rs,xmldata2);           
	            String recievedata = lC.extractXml(rs,xmldata1);      
	          	           
	            String accountNumber_DDS= lC.nodeFromKey(senddata,"<messagesDDS:accountNumber>","</messagesDDS:accountNumber>");
 	            Application.showMessage("accountNumber is ::"+accountNumber_DDS); 	           
	            if(accountNumber_DDS.equals(c.getValue("ACC_input")))
            	{
	            	Application.showMessage("Recieve XML is::  \n"+ recievedata);
	            	Application.showMessage("SEND XML is :: \n"+senddata);
            		//System.out.printf("SEND XML is %s \n",senddata);
            		Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
            		Application.showMessage("Validation is Successfull with Input Account Number"+accountNumber_DDS);
            		callFound=1;
            	}
            	else
            	{
            		continue;
            	}
            	
	            if(callFound==1)
	            {
	            	String homeSecurityLOBStatus_DDS= lC.nodeFromKey(senddata,"<typesDDS:homeSecurityLOBStatus>","</typesDDS:homeSecurityLOBStatus>");
	 	            Application.showMessage("homeSecurityLOBStatus is ::"+homeSecurityLOBStatus_DDS); 
	 	            if(homeSecurityLOBStatus_DDS==null)
		            {
			            c.report(" homeSecurityLOBStatus is NULL");
			            continue;
		            }
		            else
		            {
		            	 Application.showMessage("homeSecurityLOBStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+homeSecurityLOBStatus_DDS);
		            	 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("DISCONNECTED"))
			             {
			            	 Application.showMessage("*******Validation Point 4 :: WebServicall-homeSecurityLOBStatus********");
			            	 Application.showMessage("Validation is Successfull with homeSecurityLOBStatus::"+" "+homeSecurityLOBStatus_DDS);
			            	 v1=1;
			             }
			             else
			             {
			            	 Application.showMessage("homeSecurityLOBStatus_DDS at Send Xml not Validated as "+homeSecurityLOBStatus_DDS);
			            	 continue;
			             }
		            }
	 	            
	 	            String permitRequired_DDS= lC.nodeFromKey(senddata,"<typesDDS:permitRequired>","</typesDDS:permitRequired>");
	 	            Application.showMessage("permitRequired is ::"+permitRequired_DDS); 
	 	           
	 	         	 	            
	 	            String authorizationGuid_DDS= lC.nodeFromKey(recievedata,"<mes:authorizationGuid xmlns:mes=\"http://xml.comcast.com/esd-directory/14.05/messages\">","</mes:authorizationGuid>");
	 	            Application.showMessage("authorizationGuid is::"+authorizationGuid_DDS);
	 	            c.setValue("authorizationGuid", authorizationGuid_DDS);
	 	            
	 	            String responseStatus_DDS= lC.nodeFromKey(recievedata,"<typ:responseStatus xmlns:typ=\"http://xml.comcast.com/esd-directory/14.05/types\">","</typ:responseStatus>");
	 	            Application.showMessage("responseStatus is::"+responseStatus_DDS); 
	 	           if(responseStatus_DDS==null)
		            {
			            c.report(" responseStatus is NULL");
			           
		            }
		            else
		            {
		            	 Application.showMessage("responseStatus from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+responseStatus_DDS);
		            	 if(responseStatus_DDS.equalsIgnoreCase("Success"))
			             {
			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-responseStatus********");
			            	 Application.showMessage("Validation is Successfull with responseStatus::"+" "+responseStatus_DDS);
			            	 v2=1;
			             }
			             else
			             {
			            	 c.report("responseStatus at Send Xml not Validated as "+responseStatus_DDS);
			             }
		            }
	 	                    

	 	            
	            break;
	            }
             }
         }
         
         if(v1*callFound*v2 ==1)
         {
         	Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
         }
         else
         {
        	 c.report("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
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
}

