import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;

/*
 * This Class file is developed and maintained by Ashwini A.L 
 * Comcast ntid:: alaksh001c
 * 
 */

public class FunctionLibrary_A 
{
	
	/*
	 * geoditic Validations are written in this section...
	 * 
	 * 
	 */
	
	public String geodeticQuery_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
	      
	       Thread.sleep(8000); // Think time in JVM [waits for 10 secs here]
	       LibraryRtp  lC= new LibraryRtp ();
	       ResultSet  rs;
	       int callFound=0,v1=0;
	       String xmldata1 ="receive_data";
	       String xmldata2 ="SEND_DATA";

String rscallpresent="true";
	    Application.showMessage("-----------------------------------------------------");
	    Application.showMessage("***********geodeticQuery_Validate Function***********");       
	    Application.showMessage("-----------------------------------------------------");
	    HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("geodeticQuery"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("geodeticQuery"));

	       try
	       {
	              //Statement st =lC. dBConnect(input, c);   
	              
	              String acc = c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber");
	              Application.showMessage("Account number is ::"+acc);
	              
	      //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'intrado:frlResource/geodeticQuery' and USER_DATA2 ='"+acc+"'order by creation_time desc)where rownum <= 20");
	              rs=lC.reduceThinkTimeRTP(input, c);
	              if(rs == null)
              	{
              		rscallpresent = "false";
              		
              	
              		
              	}
              	else
{
	       
	       while (rs.next())
	       {
	      
	          
	          String rowmsg = rs.getString(1);
	          if(rs.getBlob("receive_data")==null)
	          {
	          
	              Application.showMessage("Your Recieve XML is NULL \n");
	              
	              String senddata =lC.extractXml(rs,xmldata2);
	              Application.showMessage("Your Recieve XML is::\n"+senddata);
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
	              Application.showMessage("Recieve XMl is ::"+recievedata);
	              Application.showMessage("Send XMl is ::"+senddata);
	    
	              
	              String sch_ResponderType= lC.nodeFromKey(recievedata,"<sch:ResponderType>","</sch:ResponderType>");
	              Application.showMessage("sch_ResponderType is::"+sch_ResponderType);
	              
	              if(sch_ResponderType==null)
	              {
	                    c.report("Send Xml sch_ResponderType is NULL");
	              /*   continue;*/
	              }
	             else
	             {
	              if(sch_ResponderType.equalsIgnoreCase("Failure"))
	                  {
	                      Application.showMessage("*******Validation Point 4 :: WebServicall-sch_overallMatch********");
	                      Application.showMessage("Validation is Successfull with sch_ResponderType::"+" "+sch_ResponderType);
	                      v1=1;
	                      break;
	                  }
	             
	                  else
	                  {
	                       int OutPut = 0;
	                      c.report("sch_ResponderType at Send Xml not Validated as "+sch_ResponderType);
	                  }
	              }
	                          
	           }
	             
	         }
	             
	        
	       }
	       }
	       catch (SQLException e)
	       {
	           System.out.println("Connection Failed! Check output console");
	              e.printStackTrace();
	              
	       }
	       
	       if(v1==1)
	       {
	              Application.showMessage("WebService Call :: geodeticquiery is Success[All validation points are Success]");
	       }
	       else
	       {
	              c.report("Validation failed with Errors...!");
	              
	       }
		return rscallpresent;
	}

	/*
	 *  Validating responder info Negative validations
	 * 
	 */
	
	public String responderInfofailed_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
	       Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	       ResultSet  rs;
	       int callFound=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0;
	       String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";

String rscallpresent="true";
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********responderInfo failed response_Validate Function***********");       
	     Application.showMessage("-----------------------------------------------------");
	     HashMap Operation=lC.findingoperations(input, c);
	     c.setValue("OPERATIONVALIDATION",(String) Operation.get("responderInfo"));
	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("responderInfo"));

	       try
	       {
	              
	             //  Statement st =lC. dBConnect(input, c);  
	                     
	              String acc = c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber");
	              Application.showMessage("Account number is ::"+acc);
	              
	               
	       //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'intrado:frlResource/responderInfo' and USER_DATA2 ='"+acc+"' order by creation_time desc)where rownum <= 20");
	              rs=lC.reduceThinkTimeRTP(input, c);
	              if(rs == null)
              	{
              		rscallpresent = "false";
              		
              	
              		
              	}
              	else
{
	         while (rs.next())
	         {
	              
	            
	            String rowmsg = rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	              Application.showMessage("Your Recieve XML is NULL \n");
	              
	              String senddata =lC.extractXml(rs,xmldata2);
	              Application.showMessage("Your Recieve XML is::\n"+senddata);
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
	                Application.showMessage("Recieve XMl is ::"+recievedata);
	                Application.showMessage("Send XMl is ::"+senddata);
	                   
	                                
	                   /*String intrado_HouseNum= lC.nodeFromKey(senddata,"<intrado:HouseNum>","</intrado:HouseNum>");
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
	              else*/
	            /*       {
	                     continue;
	              }*/
	              
	                 /*  if(callFound==1)*/
	                   /*{*/
	             
	                          
	/*
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
	                             if(intrado_PostalZipCode.equals(c.getValue("PostalCode").trim()))
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
	                         
	                          */
	                         
	                                                             
	                          String sch_overallMatch= lC.nodeFromKey(recievedata,"<sch:overallMatch>","</sch:overallMatch>");
	                         Application.showMessage("sch:overallMatch is::"+sch_overallMatch);
	                         
	                          if(sch_overallMatch==null)
	                           {
	                                 c.report("Send Xml sch_overallMatch is NULL");
	                                 continue;
	                           }
	                          else
	                          {
	                             if(sch_overallMatch.equalsIgnoreCase("Failure"))
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
	                         
	                          
	                         /* String sch_PoliceContactInfo= lC.nodeFromKey(recievedata,"<sch:PoliceContactInfo>","</sch:PoliceContactInfo>");
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
	                         */
	                         
	                          
	                   break;
	                   }
	             }
	         
	         /*if(callFound*v2*v3*v4*v5*v6*v7 ==1)*/
	         {
	              Application.showMessage("WebService Call :: Intrado is Success[All validation points are Success]");
	         }
	       /*  else
	         {
	               c.put("result", "false");
	               c.report("WebService Call ::Intrado is Failed with Validation Errors");
	         }*/
	       //  st.close();
	       }
	       }     
	       catch (SQLException e)
	       {
	           System.out.println("Connection Failed! Check output console");
	              e.printStackTrace();
	              
	       }
		return rscallpresent;
	}

	
	/*
	 * 
	 *        DST Scenarios Validation Functions
	 * 
	 * 
	 */

	
	public void createAccount_ValidateDST(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
	       Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	       ResultSet  rs;
	       int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0, v9=0;
	       String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Object l,m;
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********createAccount_Validate Function***********");       
	     Application.showMessage("----------------------------------------------------");
	         
	       try
	       {
	              Statement st =lC. dBConnect(input, c);  
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/createAccount' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	              
	        
	          //  rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	              Application.showMessage("Your Recieve XML is NULL \n");
	              
	              String senddata =lC.extractXml(rs,xmldata2);
	              Application.showMessage("Your Recieve XML is::\n"+senddata);
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
	                                
	                   String accountId_ucontrolsrv = lC.nodeFromKey(senddata,"<ucontrolsrv:accountId>","</ucontrolsrv:accountId>");
	                  Application.showMessage("accountNumber is ::"+accountId_ucontrolsrv);            
	                   if(accountId_ucontrolsrv.equals(c.getValue("ACC_input")))
	              {
	                     Application.showMessage("Recieve XML is::  \n"+ recievedata);
	                     Application.showMessage("SEND XML is :: \n"+senddata);
	                     //System.out.printf("SEND XML is %s \n",senddata);
	                     Application.showMessage("*******Validation Point 1 :: WebServicall-createAccount_Validate********");
	                     Application.showMessage("Validation is Successfull with Input Account Number"+accountId_ucontrolsrv);
	                     callFound=1;
	              }
	              else
	              {
	                     continue;
	              }
	              
	                   if(callFound==1)
	                   {
	             
	                          String status_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:status>","</ucontrolsrv:status>");
	                         Application.showMessage("ucontrolsrv:status is ::"+status_ucontrolsrv); 
	                          
	                         if(status_ucontrolsrv==null)
	                          {
	                                 c.report(" ucontrolsrv:status is NULL");
	                                 continue;
	                          }
	                          else
	                          {
	                             Application.showMessage("ucontrolsrv:status from Send Xml  from createAccount_Validate is ::"+" "+status_ucontrolsrv);
	                             if(status_ucontrolsrv.equalsIgnoreCase("NEW"))
	                                  {
	                                    Application.showMessage("*******Validation Point 2 :: WebServicall-ucontrolsrv:status********");
	                                    Application.showMessage("Validation is Successfull with ucontrolsrv:status::"+" "+status_ucontrolsrv);
	                                    v1=1;
	                                  }
	                                  else
	                                  {
	                                    c.report("ucontrolsrv:status at Send Xml not Validated as "+status_ucontrolsrv);
	                                  }
	                          }                            

	                         String firstName_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:firstName>","</ucontrolsrv:firstName>");
	                         Application.showMessage("ucontrolsrv:firstName is ::"+firstName_ucontrolsrv); 
	                          
	                         if(firstName_ucontrolsrv==null)
	                          {
	                                 c.report("Send Xml FirstName is NULL");
	                                 continue;
	                          }
	                          else
	                          {
	                            
	                             if(firstName_ucontrolsrv.equals(c.getValue("FirstName")))
	                                  {
	                                    Application.showMessage("*******Validation Point 3 :: WebServicall-ucontrolsrv:firstName********");
	                                    Application.showMessage("Validation is Successfull with FirstName::"+" "+firstName_ucontrolsrv);
	                                    v2=1;
	                                  }
	                                  else
	                                  {
	                                    c.report("FirstName at Send Xml not Validated as "+firstName_ucontrolsrv);
	                                  }
	                          }

	                         String lastName_ucontrolsrv= lC.nodeFromKey(senddata,"<ucontrolsrv:lastName>","</ucontrolsrv:lastName>");
	                         Application.showMessage("ucontrolsrv:lastName is ::"+lastName_ucontrolsrv); 
	                          
	                         if(lastName_ucontrolsrv==null)
	                          {
	                                 c.report("Send Xml ucontrolsrv:lastName is NULL");
	                                 continue;
	                          }
	                          else
	                          {
	                            
	                             if(lastName_ucontrolsrv.equals(c.getValue("LastName")))
	                                  {
	                                    Application.showMessage("*******Validation Point 4 :: WebServicall-ucontrolsrv:firstName********");
	                                    Application.showMessage("Validation is Successfull with ucontrolsrv:lastName::"+" "+lastName_ucontrolsrv);
	                                    v3=1;
	                                  }
	                                  else
	                                  {
	                                    c.report("ucontrolsrv:lastName at Send Xml not Validated as "+lastName_ucontrolsrv);
	                                  }
	                          }

	                         String ucontrolsrv_phoneNumber= lC.nodeFromKey(senddata,"<ucontrolsrv:phoneNumber>","</ucontrolsrv:phoneNumber>");
	                         Application.showMessage("ucontrolsrv:phoneNumber is ::"+ucontrolsrv_phoneNumber); 
	                          c.setValue("phoneNumber", ucontrolsrv_phoneNumber);
	                         
	                          String ucontrolsrv_emailAddress= lC.nodeFromKey(senddata,"<ucontrolsrv:emailAddress>","</ucontrolsrv:emailAddress>");
	                         Application.showMessage("ucontrolsrv:emailAddress is ::"+ucontrolsrv_emailAddress); 
	                        
	                          if(ucontrolsrv_emailAddress==null)
	                          {
	                                 c.report("Send Xml emailAddress is NULL");
	                                 
	                          }
	                          else
	                          {
	                             Application.showMessage("City from Send Xml  from ucontrolsrv_emailAddress is ::"+" "+ucontrolsrv_emailAddress);
	                             if(ucontrolsrv_emailAddress.equalsIgnoreCase(c.getValue("emailAddress")))
	                                  {
	                                    Application.showMessage("*******Validation Point 5 :: WebServicall-ucontrolsrv_emailAddress********");
	                                    Application.showMessage("Validation is Successfull with ucontrolsrv_emailAddress::"+" "+ucontrolsrv_emailAddress);
	                                    v4=1;
	                                  }
	                                  else
	                                  {
	                                    c.report("ucontrolsrv_emailAddress at Send Xml not Validated as "+ucontrolsrv_emailAddress);
	                                  }
	                          } 
	                         
	                          String ucontrolsrv_address1= lC.nodeFromKey(senddata,"<ucontrolsrv:address1>","</ucontrolsrv:address1>");
	                         Application.showMessage("ucontrolsrv:address1 is ::"+ucontrolsrv_address1); 
	                          c.setValue("Address", ucontrolsrv_address1);
	                        
	                         
	                       //--------------------------------------------------------------------DST time zone check in create acc call--------------------------
	                              String timeZone= lC.nodeFromKey(senddata,"<ucontrolsrv:timeZone>","</ucontrolsrv:timeZone>");
	                                Application.showMessage("timeZone is ::"+timeZone); 
	                                               
	                                 l=c.get("DSTFlag");
	                             
	                                m= c.get("State");
	                             
	                                if(((String) c.get("DSTFlag")).trim().equals("Y"))
	                                 {
	                                   
	                                     if(((String) c.get("State")).trim().equals("AZ"))
	                                    {
	                                         
	                                           if(timeZone.equalsIgnoreCase("US/Arizona"))
	                                            {
	                                                  Application.showMessage("timeZone is validated as US/Arizona where DST indicator is Y and state is AZ" );  
	                                            }
	                                           else
	                                           {
	                                                 c.report("timeZone is not validated as US/Arizona where DST indicator is Y and state is AZ" );  
	                                            }  
	                                     }
	                                    
	                                     else if(c.get("State").equals("NJ/PA"))
	                                    {
	                                          if(timeZone.equalsIgnoreCase("US/Eastern"))
	                                            {
	                                                  Application.showMessage("timeZone is validated as US/Eastern where DST indicator is Y and state is NJ/PA");  
	                                            }
	                                           else
	                                           {
	                                                 c.report("timeZone is validated as US/Eastern where DST indicator is Y and state is NJ/PA" );  
	                                            } 
	                                     }
	                                    else
	                                    {
	                                          Application.showMessage("invalid combination of state and DST Flag");  
	                                             c.report("invalid combination");
	                                    }
	                                     
	                                      
	                   }
	                               
	                                
	                                else if(c.get("DSTFlag").equals("N"))
	                                            {
	                                     
	                                             
	                                      if(c.get("State").equals("AZ"))
	                                     
	                                                 {
	                                     
	                                                         
	                                            if(timeZone.equalsIgnoreCase("US/Mountain"))
	                                            {
	                                                  Application.showMessage("timeZone is validated as US/Mountain where DST indicator is N and state is AZ" );  
	                                            }
	                                           else
	                                           {
	                                                 c.report("timeZone is validated as US/Mountain where DST indicator is N and state is AZ" );  
	                                            }
	                                            
	                                             
	                                                 }}
	                                      
	                               
	                                else
	                               {
	                                    Application.showMessage("invalid combination of state and DST Flag");  
	                                      c.report("invalid combination");
	                               }
	                               //---------------------368716------------------------------------------------------------------  
	                        
	                               String ucontrolsrv_city= lC.nodeFromKey(senddata,"<ucontrolsrv:city>","</ucontrolsrv:city>");
	                                Application.showMessage("City is ::"+ucontrolsrv_city); 
	                                
	                                if(ucontrolsrv_city==null)
	                          {
	                                 c.report("Send Xml City is NULL");
	                               
	                          }
	                          else
	                          {
	                             Application.showMessage("City from Send Xml  from ucontrolsrv_city is ::"+" "+ucontrolsrv_city);
	                             if(ucontrolsrv_city.equalsIgnoreCase(c.getValue("City")))
	                                  {
	                                    Application.showMessage("*******Validation Point 6 :: WebServicall-ucontrolsrv_city********");
	                                    Application.showMessage("Validation is Successfull with City::"+" "+ucontrolsrv_city);
	                                    v5=1;
	                                  }
	                                  else
	                                  {
	                                    c.report("City at Send Xml not Validated as "+ucontrolsrv_city);
	                                  }
	                          } 
	                         
	                          String ucontrolsrv_province= lC.nodeFromKey(senddata,"<ucontrolsrv:province>","</ucontrolsrv:province>");
	                         Application.showMessage("ucontrolsrv:province is ::"+ucontrolsrv_province); 
	                          
	                          String ucontrolsrv_postalCode= lC.nodeFromKey(senddata,"<ucontrolsrv:postalCode>","</ucontrolsrv:postalCode>");
	                         Application.showMessage("ucontrolsrv:postalCode is ::"+ucontrolsrv_postalCode); 
	                          
	                          c.setValue("PostalCode", ucontrolsrv_postalCode);
	                         
	                          String ucontrolsrv_portalUserSSOId= lC.nodeFromKey(senddata,"<ucontrolsrv:portalUserSSOId>","</ucontrolsrv:portalUserSSOId>");
	                         Application.showMessage("ucontrolsrv:portalUserSSOId is ::"+ucontrolsrv_portalUserSSOId); 
	                          
	                         if(ucontrolsrv_portalUserSSOId==null)
	                          {
	                                 c.report("Send Xml ucontrolsrv_portalUserSSOId is NULL");
	                                
	                          }
	                          else
	                          {
	                             Application.showMessage("portalUserSSOId from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_portalUserSSOId);
	                             if(ucontrolsrv_portalUserSSOId.equalsIgnoreCase(c.getValue("authorizationGuid")))
	                                  {
	                                    Application.showMessage("*******Validation Point 7 :: WebServicall-ucontrolsrv_portalUserSSOId********");
	                                    Application.showMessage("Validation is Successfull with ucontrolsrv_portalUserSSOId::"+" "+ucontrolsrv_portalUserSSOId);
	                                    v6=1;
	                                  }
	                                  else
	                                  {
	                                    c.report("ucontrolsrv_portalUserSSOId at Send Xml not Validated as "+ucontrolsrv_portalUserSSOId);
	                                  }
	                          } 
	                         
	                          if(c.getValue("IsMultiExist").equals("true"))
	                         {
	                           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
	                           {
	                                  String ucontrolsrv_group1= lC.nodeFromKey(senddata,"</ucontrolsrv:portalUserSSOId><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
	                                       Application.showMessage("ucontrolsrv:group1 is ::"+ucontrolsrv_group1); 
	                                        
	                                        if(ucontrolsrv_group1==null)
	                                       {
	                                              c.report("Send Xml ucontrolsrv_group1 is NULL");
	                                            
	                                       }
	                                       else
	                                       {
	                                          Application.showMessage("ucontrolsrv_group from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_group1);
	                                          if(ucontrolsrv_group1.equals(c.getValue("ExistingGroup"))||ucontrolsrv_group1.equals(c.getValue("ExistingService")))
	                                               {
	                                                 Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group********");
	                                                 Application.showMessage("Validation is Successfull with ucontrolsrv_group1::"+" "+ucontrolsrv_group1);
	                                                 v7=1;
	                                               }
	                                               else
	                                               {
	                                                 c.report("ucontrolsrv_group at Send Xml not Validated as "+ucontrolsrv_group1);
	                                               }
	                                       }
	                               
	                             }
	                           else
	                           {
	                           
	                                   String ucontrolsrv_group1= lC.nodeFromKey(senddata,"</ucontrolsrv:product><ucontrolsrv:group>","</ucontrolsrv:group><ucontrolsrv:group>");
	                                       Application.showMessage("ucontrolsrv:group1 is ::"+ucontrolsrv_group1); 
	                                        
	                                        if(ucontrolsrv_group1==null)
	                                       {
	                                              c.report("Send Xml ucontrolsrv_group1 is NULL");
	                                            
	                                       }
	                                       else
	                                       {
	                                          Application.showMessage("ucontrolsrv_group from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_group1);
	                                          if(ucontrolsrv_group1.equals(c.getValue("ExistingGroup")))
	                                          {
	                                                 Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group********");
	                                                 Application.showMessage("Validation is Successfull with ucontrolsrv_group1::"+" "+ucontrolsrv_group1);
	                                                 v7=1;
	                                               }
	                                               else if(ucontrolsrv_group1.equals(c.getValue("ExistingService")))
	                                               {
	                                                 Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group********");
	                                                 Application.showMessage("Validation is Successfull with ucontrolsrv_group1::"+" "+ucontrolsrv_group1);
	                                                 v7=1;
	                                               }
	                                               else
	                                               {
	                                                 c.report("ucontrolsrv_group at Send Xml not Validated as "+ucontrolsrv_group1);
	                                               }
	                                       }
	                           }
	                               
	                                 String ucontrolsrv_group2= lC.nodeFromKey(senddata,"</ucontrolsrv:group><ucontrolsrv:group>","</ucontrolsrv:group></account>");
	                                Application.showMessage("ucontrolsrv:group2 is ::"+ucontrolsrv_group2); 
	                                if(ucontrolsrv_group2==null)
	                                 {
	                                       c.report("Send Xml ucontrolsrv_group1 is NULL");
	                                     
	                                 }
	                                 else
	                                 {
	                                    Application.showMessage("ucontrolsrv_group from Send Xml  from ucontrolsrv_portalUserSSOId is ::"+" "+ucontrolsrv_group2);
	                                    if(ucontrolsrv_group2.equals(c.getValue("ExistingService"))||ucontrolsrv_group2.equals(c.getValue("ExistingGroup")))
	                                        {
	                                          Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group2********");
	                                          Application.showMessage("Validation is Successfull with ucontrolsrv_group2::"+" "+ucontrolsrv_group2);
	                                          v7=1;
	                                        }
	                                        else
	                                        {
	                                          c.report("ucontrolsrv_group2 at Send Xml not Validated as "+ucontrolsrv_group2);
	                                        }
	                                 }
	                                
	                                 
	                          }
	                         
	                          else
	                         {
	                         
	                                 String ucontrolsrv_group= lC.nodeFromKey(senddata,"<ucontrolsrv:group>","</ucontrolsrv:group>");
	                                Application.showMessage("ucontrolsrv:group is ::"+ucontrolsrv_group); 
	                                 
	                                if(ucontrolsrv_group==null)
	                                 {
	                                       c.report("Send Xml ucontrolsrv_group is NULL");
	                                     
	                                 }
	                                 else
	                                 {
	                                    Application.showMessage("ucontrolsrv_group from Input ::"+" "+c.getValue("sSERVICEorCURRENTSERVICE"));
	                                    if(ucontrolsrv_group.equals(c.getValue("sSERVICEorCURRENTSERVICE")))
	                                        {
	                                          Application.showMessage("*******Validation Point 8 :: WebServicall-ucontrolsrv_group********");
	                                          Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+ucontrolsrv_group);
	                                          v7=1;
	                                        }
	                                        else
	                                        {
	                                          c.report("ucontrolsrv_group at Send Xml not Validated as "+ucontrolsrv_group);
	                                        }
	                                 } 
	                         }
	                                                            
	                          String errorCode_createAcc= lC.nodeFromKey(recievedata,"<errorCode xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</errorCode>");
	                         Application.showMessage("errorCode is::"+errorCode_createAcc);
	                         
	                         if(errorCode_createAcc==null)
	                          {
	                                 c.report("Send Xml errorCode_createAcc is NULL");
	                                
	                          }
	                          else
	                          {
	                             Application.showMessage("errorCode_createAcc from Send Xml  is ::"+" "+errorCode_createAcc);
	                             if(errorCode_createAcc.equals("0"))
	                                  {
	                                    Application.showMessage("*******Validation Point 9 :: WebServicall-errorCode_createAcc********");
	                                    Application.showMessage("Validation is Successfull with ucontrolsrv_group::"+" "+errorCode_createAcc);
	                                    v8=1;
	                                  }
	                                  else
	                                  {
	                                    c.report("errorCode_createAcc at Send Xml not Validated as "+errorCode_createAcc);
	                                  }
	                          } 
	                         
	                          
	                          String accountId_createAcc= lC.nodeFromKey(recievedata,"<accountId xmlns:ns3=\"http://www.ucontrol.com/integration/accountService/v1/type\" xmlns:ns2=\"http://www.ucontrol.com/integration/schema/commonType/v1\">","</accountId>");
	                         Application.showMessage("accountId is::"+accountId_createAcc); 
	                          
	                               if(c.getValue("IsDemi").equalsIgnoreCase("true"))
	                               {
	                                  String Activation_code= lC.nodeFromKey(senddata,"<ucontrolsrv:activationCode>","</ucontrolsrv:activationCode>");
	                                           Application.showMessage("Activation_code is::"+Activation_code);
	                                       
	                                           if(Activation_code==null)
	                                          {  
	                                               c.report("Send Xml Activation_code is NULL");
	                                             
	                                           }
	                                          else
	                                          {
	                                         
	                                           if(Activation_code.equals(c.getValue("ACC_input")))
	                                               {
	                                                 Application.showMessage("*******Validation Point 10 :: WebServicall-Activation_code********");
	                                                 Application.showMessage("Validation is Successfull with Activation_code::"+" "+Activation_code);
	                                                 v9=1;
	                                               }
	                                               else
	                                               {
	                                                 c.report("Activation_code at Send Xml not Validated as "+Activation_code);
	                                               }
	                                         }      
	                               }
	                              else
	                              {
	                                   Application.showMessage("No Validation for Activation Code as Current Scenario is Insight>>");
	                                   v9=1;
	                              }
	                                   

	                         
	                   break;
	                   }
	             }
	         }
	         
	         if(v1*callFound*v2*v3*v4*v5*v6*v7*v8*v9 ==1)
	         {
	              Application.showMessage("WebService Call :: CreateAccount is Success[All validation points are Success]");
	         }
	         else
	         {
	               //OutPut=0;
	               c.put("result", "false");
	               c.report("WebService Call ::CreateAccount is Failed with Validation Errors");
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

	public void addAssociation(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
	       Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	       ResultSet  rs;
	       int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0, v9=0;
	       String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Object l,m;
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********addAssociation Validate Function***********");       
	     Application.showMessage("----------------------------------------------------");
	         
	       try
	       {
	              Statement st =lC. dBConnect(input, c);  
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:NumerexSimulatorLogMsg/addAssociation' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	              
	        
	          //  rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	              Application.showMessage("Your Recieve XML is NULL \n");
	              
	              String senddata =lC.extractXml(rs,xmldata2);
	              Application.showMessage("Your Recieve XML is::\n"+senddata);
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
	                                
	                   String AccountID = lC.nodeFromKey(senddata,"Numerex:sAcctNbr>","</Numerex:sAcctNbr>");
	                  Application.showMessage("accountNumber is ::"+AccountID);            
	                   if(AccountID.equals(c.getValue("ACC_input")))
	              {
	                     Application.showMessage("Recieve XML is::  \n"+ recievedata);
	                     Application.showMessage("SEND XML is :: \n"+senddata);
	                     //System.out.printf("SEND XML is %s \n",senddata);
	                     Application.showMessage("*******Validation Point 1 :: WebServicall-addAssociation_Validate********");
	                     Application.showMessage("Validation is Successfull with Input Account Number"+AccountID);
	                     callFound=1;
	              }
	              else
	              {
	                     continue;
	              }
	              
	                   if(callFound==1)
	                   {
	             
	                          String ICCID= lC.nodeFromKey(senddata,"<Numerex:sICCID>","</Numerex:sICCID>");
	                         Application.showMessage("ICCID from sent data is ::"+ICCID); 
	                          
	                         if(ICCID==null)
	                          {
	                                 c.report(" ICCID from sent data is NULL");
	                                 continue;
	                          }
	                          else
	                          {
	                             
	                                    v1=1;
	                                  
	                     }
	                         
	                         String CCentrl =lC.nodeFromKey(senddata,"<Numerex:sDICE>","</Numerex:sDICE>"); 
                             
                             
                             if(CCentrl.equals(null))
                             {
                                   c.report(" Ccentral number is NULL"); 
                                   continue;
                             }
                             else
                             {
                               
                               Application.showMessage("Ccentral number fetched from numerix addAssociation is::"+CCentrl);
                               Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                                
                               
                               if(CCentrl.equals(c.getValue("CcentralNum")))
                               {
                                   
                                      Application.showMessage("Numerix addAssociation call found"+CCentrl);
                                      v2=1;
                               }
                               else
                               {
                                      continue;
                               }
                               
                               String ErrorCode= lC.nodeFromKey(recievedata,"<iErrorCode>","</iErrorCode>");
  	                         Application.showMessage("Error code from sent data is ::"+ErrorCode);                           

	                        
	                   break;
	                   }
	             }
	         
	         
	         if(v1*callFound*v2 ==1)
	         {
	              Application.showMessage("WebService Call :: addAssociation is Success[All validation points are Success]");
	         }
	         else
	         {
	               //OutPut=0;
	               c.put("result", "false");
	               c.report("WebService Call ::addAssociation is Failed with Validation Errors");
	         }
	         st.close();
	          
			    }}}
				    
	       catch (SQLException e)
	       {
	           System.out.println("Connection Failed! Check output console");
	              e.printStackTrace();
	              return;
	       }
	}

	public void Jasper_Edit_Terminal(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
	{
	       Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	       ResultSet  rs;
	       int callFound=0,v1=0,v2=0,v3=0,v4=0,v5=0,v6=0,v7=0,v8=0, v9=0;
	       String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	     Object l,m;
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("***********Jasper Validate Function***********");       
	     Application.showMessage("----------------------------------------------------");
	         
	       try
	       {
	              Statement st =lC. dBConnect(input, c);  
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:NumerexSimulatorLogMsg/addAssociation' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	              
	        
	          //  rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	              Application.showMessage("Your Recieve XML is NULL \n");
	              
	              String senddata =lC.extractXml(rs,xmldata2);
	              Application.showMessage("Your Recieve XML is::\n"+senddata);
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
	                                
	                   String AccountID = lC.nodeFromKey(senddata,"Numerex:sAcctNbr>","</Numerex:sAcctNbr>");
	                  Application.showMessage("accountNumber is ::"+AccountID);            
	                   if(AccountID.equals(c.getValue("ACC_input")))
	              {
	                     Application.showMessage("Recieve XML is::  \n"+ recievedata);
	                     Application.showMessage("SEND XML is :: \n"+senddata);
	                     //System.out.printf("SEND XML is %s \n",senddata);
	                     Application.showMessage("*******Validation Point 1 :: WebServicall-Jasper_Validate********");
	                     Application.showMessage("Validation is Successfull with Input Account Number"+AccountID);
	                     callFound=1;
	              }
	              else
	              {
	                     continue;
	              }
	              
	                   if(callFound==1)
	                   {
	             
	                          String ICCID= lC.nodeFromKey(senddata,"<Numerex:sICCID>","</Numerex:sICCID>");
	                         Application.showMessage("ICCID from sent data is ::"+ICCID); 
	                          
	                         if(ICCID==null)
	                          {
	                                 c.report(" ICCID from sent data is NULL");
	                                 continue;
	                          }
	                          else
	                          {
	                             
	                                    v1=1;
	                                  
	                     }
	                         
	                         String CCentrl =lC.nodeFromKey(senddata,"<Numerex:sDICE>","</Numerex:sDICE>"); 
                             
                             
                             if(CCentrl.equals(null))
                             {
                                   c.report(" Ccentral number is NULL"); 
                                   continue;
                             }
                             else
                             {
                               
                               Application.showMessage("Ccentral number fetched from jasper is::"+CCentrl);
                               Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                                
                               
                               if(CCentrl.equals(c.getValue("CcentralNum")))
                               {
                                   
                                      Application.showMessage("Jasper call found"+CCentrl);
                                      v2=1;
                               }
                               else
                               {
                                      continue;
                               }
                               
	                                                    

	                        
	                   break;
	                   }
	             }
	         
	         
	         if(v1*callFound*v2 ==1)
	         {
	              Application.showMessage("WebService Call :: Jasper call is Success[All validation points are Success]");
	         }
	         else
	         {
	               //OutPut=0;
	               c.put("result", "false");
	               c.report("WebService Call ::Jasper call is Failed with Validation Errors");
	         }
	         st.close();
	          
			    }}}
				    
	       catch (SQLException e)
	       {
	           System.out.println("Connection Failed! Check output console");
	              e.printStackTrace();
	              return;
	       }
	}


	public void queryLocation_ValidateDST(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
	       Thread.sleep(3000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
	       ResultSet  rs;
	       int callFound=0,v1=0;
	       String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-------------------------------------------------");
	     Application.showMessage("*****Query Location _Validate Function******");       
	     Application.showMessage("-------------------------------------------------");
	         
	       try
	       {
	              Statement st =lC. dBConnect(input, c);  
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	              
	        
	            //rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	              Application.showMessage("Your Recieve XML is NULL \n");
	              
	              String senddata =lC.extractXml(rs,xmldata2);
	              Application.showMessage("Your Recieve XML is::\n"+senddata);
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
	//--------------checking dst falg=Y/N--------------------------------------

	                           String DSTFlag= lC.nodeFromKey(recievedata,"<typ:DST>","</typ:DST>");
	                         Application.showMessage("DSTFlag is :: "+DSTFlag); 
	                          c.put("DSTFlag", DSTFlag);
	                         
	//------------------state=AZ or NJ/PA                      
	                          String State= lC.nodeFromKey(recievedata,"<typ:state>","</typ:state>").trim();
	                         Application.showMessage("State is :: "+State); 
	                          c.put("State", State);
	                         
	                          break;
	                
	                   }
	             }
	         }
	         if(v1*callFound==1)
	         {
	              Application.showMessage("WebService Call :: Query Location is Success[All validation points are Success]");
	         }
	         else
	         {
	              // OutPut=0;
	               c.put("result", "false");
	               c.report("WebService Call :: Query Location is Failed with Validation Errors");
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
    /*
     * 
     *          DDS status......US332454 update status of XH LOB to A once OP recives completed install order
     * 
     */
	public void processHomeSecurityInfoA_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
		 ResultSet  rs;
		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
		 String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	         
		try
		{
			 Statement st =lC. dBConnect(input, c);	
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	        	
	        
	            String rowmsg = rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	            	Application.showMessage("Your Recieve XML is NULL \n");
	            	
	            	String senddata =lC.extractXml(rs,xmldata2);
	            	Application.showMessage("Your Recieve XML is::\n"+senddata);
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
			            	 if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("ACTIVE"))
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
	         st.close();
		}	
		catch (SQLException e)
		{
		    System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}
	 }
	
	
	public void processHomeSecurityInfo_Active_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
	{
	       Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
	     LibraryRtp  lC= new LibraryRtp ();
	       ResultSet  rs;
	       int callFound=0,v1=0,v2=0,v3=0,v4=0;
	       String xmldata1 ="receive_data";
	     String xmldata2 ="SEND_DATA";
	    
	     Application.showMessage("-----------------------------------------------------");
	     Application.showMessage("*****processHomeSecurityInfo_Validate Function******");       
	     Application.showMessage("----------------------------------------------------");
	         
	       try
	       {
	              Statement st =lC. dBConnect(input, c);  
	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo' order by creation_time desc)where rownum <= 20");
	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
	         {
	              
	        
	            //rowmsg=rs.getString(1);
	            if(rs.getBlob("receive_data")==null)
	            {
	            
	              Application.showMessage("Your Recieve XML is NULL \n");
	              
	              String senddata =lC.extractXml(rs,xmldata2);
	              Application.showMessage("Your Recieve XML is::\n"+senddata);
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
	                         
	              

	                         String lastName_DDS= lC.nodeFromKey(senddata,"<typesDDS:lastName>","</typesDDS:lastName>");
	                         Application.showMessage("lastName is ::"+lastName_DDS); 
	                          
	              
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
	                             if(homeSecurityLOBStatus_DDS.equalsIgnoreCase("ACTIVE"))
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
	         
	         if(v1*callFound*v3*v4 ==1)
	         {
	              Application.showMessage("WebService Call :: processHomeSecurityInfo is Success[All validation points are Success]");
	         }
	         else
	         {
	              // OutPut=0;
	               c.put("result", "false");
	               c.report("WebService Call ::processHomeSecurityInfo_Validate is Failed with Validation Errors");
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

	
	/*
	 * 
	 *     No Worklisting
	 */

        
        public void getDatasetUsingUserData2(Object input, ScriptingContext c)throws InterruptedException, ClassNotFoundException, IOException, SQLException 
{
                        
                           LibraryRtp_UpDown lU=new LibraryRtp_UpDown();
   //vC.execution(input, c);
         int v1=0,i=0;
   Date todayDate = new Date();
   TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
   DateFormat todayDateFormat = new SimpleDateFormat("dd-MMM-YY");
   todayDateFormat.setTimeZone(timeZone);
  String strTodayDate = todayDateFormat.format(todayDate);
   Application.showMessage("Todays Date as per EST is::"+strTodayDate);

   java.sql.Statement st=lU.dBConnect(input, c);  
   ResultSet rs;
   rs=st.executeQuery("select * from cwmessagelog where user_data2='"+c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber")+"' AND creation_time > '"+strTodayDate+"'");
   while(rs.next())
   {
          String operation=rs.getString("OPERATION");
          Application.showMessage("OPeration is ::"+operation);
          
          if(operation.equals("ucontrolsrv:AccountServicePortType/createAccount"))
          {
               
               Application.showMessage("Extra call createAccount Found");
             v1=1;             
              continue;
                                
          }
          else if(operation.equals("srvDDS:DirectoryDataServicePortType/processHomeSecurityInfo"))
          {
               
               Application.showMessage("Extra call processHomeSecurityInfo Found");
             v1=1;
             continue;
          }
          else if(operation.equals("hss:HomeSecurityServicePort/createCMSAccountID"))
          {
               Application.showMessage("Extra call createCMSAccountID Found");
              v1=1;
              continue;
          }
         
          else if(operation.equals(" hss:HomeSecurityServicePort/getCustomerPermitRequirements"))
          {
               Application.showMessage("Extra call getCustomerPermitRequirements Found");
              v1=1;
              continue;
          }
          
          
          else if(operation.equals("ls:LocationServicePort/queryLocation"))
          {
               Application.showMessage("Extra call queryLocation Found");
              v1=1;
              continue;
          }
          
          else if(operation.equals("COPSServices:Comcast/SetAccountBasicInformation"))
          {
               Application.showMessage("Extra call SetAccountBasicInformation Found");
              v1=1;
              continue;
          }
          
          
          else if(operation.equals("intrado:frlResource/responderInfo"))
          {
               Application.showMessage("Extra call responderInfo Found");
              v1=1;
              continue;
          }
          
          else if(operation.equals("ls:LocationServicePort/modifyHomeSecurity"))
          {
               Application.showMessage("Extra call modifyHomeSecurity Found");
              v1=1;
              continue;
          }
          
          
          else if(operation.equals("COPSServices:Comcast/SetAccountAuthorityInformation"))
          {
               Application.showMessage("Extra call SetAccountAuthorityInformation Found");
              v1=1;
              continue;
          }
          
          else if(operation.equals("cmb:commonOrderService/orderUpdate"))
          {
               Application.showMessage("Extra call OrderUpdate Found");
              v1=1;
              continue;
          }
          
          
         if(v1==1)
         {
                        // OutPut=0;
              c.report("Extra Call Found");
              break;
         }
         else
         {
             Application.showMessage(i+"th row data is not an Extra call ");
         }
         i++;
   
   
    }
	}
	
        
        /*
         * 
         *    Jasper And Numerix Calls...!
         * 
         */
        
        
        public void Jasper_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
        
        {
               
               Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
               LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
               ResultSet  rs;
               String xmldata1 ="receive_data";
	          String xmldata2 ="SEND_DATA";
	          int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
	          Application.showMessage("----------------------------------------------------------");
	          Application.showMessage("*****jasper Function******");    
	          Application.showMessage("----------------------------------------------------------");
	         // Application.showMessage("Value is"+c.getValue("ACC_input"));
	               try
	               {
	                     Statement st =lC. dBConnect(input, c);
	                     String acc = c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: XML_AccNumber");
	                     Application.showMessage("Account number is ::"+acc);
	                     
	                      
	
	                 rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'jaspersrv:TerminalPortType/EditTerminal' and USER_DATA2 ='"+acc+"' order by creation_time desc)where rownum <= 20");
	                 while (rs.next() && rs.getString(1)!= c.getValue("BaseMsgid"))
	                       {
	                           
	                            String rowmsg = rs.getString(1);             
	                           if(rs.getBlob("receive_data")==null)
	                           {
	                           
	                             System.out.printf("Your Recieve XML is NULL \n");
	                             Application.showMessage("Your Recieve XML is NULL \n");
	                             String senddata =lC.extractXml(rs,xmldata2);
	                                 System.out.printf("SEND XML is  \n",senddata );   
	                                 Application.showMessage("SEND XML is  \n"+ senddata);                                        
	                           }
	                           else if(rs.getBlob("SEND_DATA")==null)
	                           {
	                             System.out.printf("Your SEND XML is NULL \n");  
	                             String recievedata=lC.extractXml(rs,xmldata1);
	                                  System.out.printf("RECIEVE XML is \n",recievedata);
	                                 Application.showMessage("Your Recieve XML is NULL \n");
	                                 Application.showMessage("Recieve XML is  \n"+ recievedata);                                                          
	                                 String billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
	                                                    
	                                  Application.showMessage("Your send data is null"); 
	                           }
	                           else
	                                 {
	                             
	                               String recievedata = lC.extractXml(rs,xmldata1); 
	                              // Application.showMessage("Recieve XML is  \n"+ recievedata);
	                                 System.out.printf("RECIEVE XML is %s \n",recievedata); 
	                                 
	                                 String senddata = lC.extractXml(rs,xmldata2);
	                                 System.out.printf("SEND XML is %s \n",senddata);
	                                
	                                 
	                                 String ICCID = lC.nodeFromKey(senddata,"<jaspersrv:iccid>","</jaspersrv:iccid>");
	                                     
	                                      
	                                 if(ICCID==null)
	                                 {
	                                        c.report("ICCID value from JasperCall is ::NULL \n");
	                                        break;
	                                 }
	                                 else
	                                 {
	                                   
	                                   Application.showMessage("ICCID value from JasperCall is::"+ICCID);
	                                   
	                                   
	                                        if(ICCID.equals(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: iccId")))
	                                   {
	                                          Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
	                                          Application.showMessage("Validation is Successfull with Input ICCID"+ICCID);
	                                          callFound=1;
	                                   }
	                                   else
	                                   {
	                                          c.report("Iccid validation failed");
	                                          break;
	                                   }
	                                   
	                                   if(callFound==1)
	                                   {
	                                           String Jasp_version= lC.nodeFromKey(senddata,"<jaspersrv:version>","</jaspersrv:version><jaspersrv:licenseKey>");
	                                                   
	                                           Application.showMessage("SEND Xml Jasp_version is ::"+Jasp_version);
	                                           
	                                           String Jas_licenseKey = lC.nodeFromKey(senddata,"<jaspersrv:licenseKey>","</jaspersrv:licenseKey><jaspersrv:iccid>");
	                                           Application.showMessage("SEND Xml Jas_licenseKey is ::"+Jas_licenseKey); 
	                                                     
	
	                                           String Jas_targetValue= lC.nodeFromKey(senddata,"<jaspersrv:targetValue>","</jaspersrv:targetValue>");
	                                                   
	                                           Application.showMessage("SEND Xml Jas_targetValue is ::"+Jas_targetValue);
	                                           
	                                           String Jas_changeType = lC.nodeFromKey(senddata,"<jaspersrv:changeType>","</jaspersrv:changeType></jaspersrv:EditTerminalRequest>");
	                                           Application.showMessage("SEND Xml Jas_changeType is ::"+Jas_changeType); 
	                                                     
	                                   
	                                   /*recv data*/
	                                   
	                                           String Jas_Version = lC.nodeFromKey(recievedata,"<ns2:version xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">","</ns2:version><ns2:build xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
	                                           Application.showMessage("SEND Xml Jas_Version is ::"+Jas_Version); 
	                                   
	                                           String Jas_build = lC.nodeFromKey(recievedata,"<ns2:build xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">","</ns2:build><ns2:timestamp xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
	                                           Application.showMessage("SEND Xml Jas_build is ::"+Jas_build); 
	                                           
	                                           String Jas_iccid = lC.nodeFromKey(recievedata,"<ns2:iccid xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">","</ns2:iccid><ns2:effectiveDate xmlns:ns2=\"http://api.jasperwireless.com/ws/schema\">");
	                                           Application.showMessage("SEND Xml Jas_iccid is ::"+Jas_iccid); 
	                                           
	                                       }        
	                                                      
	                                   // ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));                       
	                                     }
	                            
	                                  }
	                   
	                                      Application.showMessage("********WebService Call::Jasper Valiadted ********");      
	                                   st.close();
	 
	                             }
	                   }
	               catch (SQLException e)
	               {
	                   /*Application.showMessage("Connection Failed! Check output console");*/
	                     e.printStackTrace();
	                     return;
	               }
	 }

        
        
        public void Numerix_SuspendUnit(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
        
        {
               
               Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
               LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
               ResultSet  rs;
               String xmldata1 ="receive_data";
          String xmldata2 ="SEND_DATA";
          int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
          Application.showMessage("----------------------------------------------------------");
          Application.showMessage("*****Numerix_SuspendUnit Function******");    
          Application.showMessage("----------------------------------------------------------");
         
               try
               {
                     Statement st =lC. dBConnect(input, c);
                 rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:NumerexSimulatorLogMsg/suspendUnit' order by creation_time desc)where rownum <= 20");
                 while (rs.next() && rs.getString(1)!= c.getValue("BaseMsgid"))
                       {
                           
                            String rowmsg = rs.getString(1);             
                           if(rs.getBlob("receive_data")==null)
                           {
                           
                             System.out.printf("Your Recieve XML is NULL \n");
                             Application.showMessage("Your Recieve XML is NULL \n");
                             String senddata =lC.extractXml(rs,xmldata2);
                                 System.out.printf("SEND XML is  \n",senddata );   
                                 Application.showMessage("SEND XML is  \n"+ senddata);                                        
                           }
                           else if(rs.getBlob("SEND_DATA")==null)
                           {
                             System.out.printf("Your SEND XML is NULL \n");  
                             String recievedata=lC.extractXml(rs,xmldata1);
                                 System.out.printf("RECIEVE XML is \n",recievedata);
                                 Application.showMessage("Your Recieve XML is NULL \n");
                                 Application.showMessage("Recieve XML is  \n"+ recievedata);                                                          
                                String billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
                                                    
                                 if(billingArrangementID_rtp==null)
                                 {
                                        Application.showMessage("billingArrangementID value is NULL");
                                        
                                      // billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");

                                        
                                        continue;
                                 }
                                 else
                                 {
                                   Application.showMessage("billingArrangementID value from v is::"+billingArrangementID_rtp);
                                   Application.showMessage(c.getValue("ACC_input"));
                                   Application.showMessage(billingArrangementID_rtp);
                                   if(billingArrangementID_rtp == c.getValue("ACC_input"))
                                   {
                                          Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                          Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_rtp);
                                   }
                                    else
                                   {
                                          continue;
                                   }                                                             
                                 }                     
                           }
                           else
                                 {
                             
                               String recievedata = lC.extractXml(rs,xmldata1); 
                              // Application.showMessage("Recieve XML is  \n"+ recievedata);
                                 System.out.printf("RECIEVE XML is %s \n",recievedata); 
                                 
                                 String senddata = lC.extractXml(rs,xmldata2);
                                 System.out.printf("SEND XML is %s \n",senddata);
                                
                                 
                                 String CCentrl =lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>"); 
                              
                                      
                                 if(CCentrl.equals(null))
                                 {
                                        System.out.printf("Call not found \n");
                                       continue;
                                 }
                                 else
                                 {
                                   
                                   Application.showMessage("Ccentral number fetched from numerix activateUnit is::"+CCentrl);
                                   Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                                   //Application.showMessage(c.getValue("ACC_input"));
                                   //Application.showMessage(billingArrangementID_getCus);
                                   
                                   
                                   /* String Numerx_sUnitKey= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                            
                                     Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey);
                                   */
                                   
                                   if(CCentrl.equals(c.getValue("CcentralNum")))
                                   {
                                       
                                          Application.showMessage("Numerix_SuspendUnit call found"+CCentrl);
                                          callFound=1;
                                   }
                                   else
                                   {
                                          continue;
                                   }
                                   
                                   if(callFound==1)
                                   {
                                          String Numerx_iAccountId= lC.nodeFromKey(senddata,"<Numerex:iAccountId>","</Numerex:iAccountId>");
                                                   
                                           Application.showMessage("SEND Xml Numerx_iAccountId is ::"+Numerx_iAccountId);
                                           
                             /* String Numerx_sUnitKey1= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                                   
                                           Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey1);
                                   */
                                   /*recv data*/
                                   
                                           String Numerix_activateUnitResult = lC.nodeFromKey(recievedata,"<activateUnitResult xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</activateUnitResult>");
                                           Application.showMessage("SEND Xml Numerix_activateUnitResult is ::"+Numerix_activateUnitResult); 
                                   
                                           String Numerix_iErrorCode = lC.nodeFromKey(recievedata,"<iErrorCode xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</iErrorCode>");
                                           Application.showMessage("SEND Xml Numerix_iErrorCode is ::"+Numerix_iErrorCode); 
                                           
                                           String Numerix_sErrorMsg = lC.nodeFromKey(recievedata," <sErrorMsg xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</sErrorMsg></activateUnitResponse>");
                                           Application.showMessage("SEND Xml Numerix_sErrorMsg is ::"+Numerix_sErrorMsg); 
                                           
                                   }        
                                                     
                                   // ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));                       
                            }
                           
                                 }
                   
                Application.showMessage("********WebService Call::Numerix_SuspendUnit Valiadted ********");   
                                  st.close();
 
                       }
                 
               
               
               }
               catch (SQLException e)
               {
                     //OutPut=0;
                   Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                     return;
               }
       }
        
 public void Numerix_DeactivateUnit(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
        
        {
               
               Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
               LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
               ResultSet  rs;
               String xmldata1 ="receive_data";
          String xmldata2 ="SEND_DATA";
          int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
          Application.showMessage("----------------------------------------------------------");
          Application.showMessage("*****Numerix_DeactivateUnit Function******");    
          Application.showMessage("----------------------------------------------------------");
         // Application.showMessage("Value is"+c.getValue("ACC_input"));
               try
               {
                     Statement st =lC. dBConnect(input, c);
                 rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'Numerex:NMRXCCSoap/activateUnit' order by creation_time desc)where rownum <= 20");
                 while (rs.next() && rs.getString(1)!= c.getValue("BaseMsgid"))
                       {
                           
                            String rowmsg = rs.getString(1);             
                           if(rs.getBlob("receive_data")==null)
                           {
                           
                             System.out.printf("Your Recieve XML is NULL \n");
                             Application.showMessage("Your Recieve XML is NULL \n");
                             String senddata =lC.extractXml(rs,xmldata2);
                                 System.out.printf("SEND XML is  \n",senddata );   
                                 Application.showMessage("SEND XML is  \n"+ senddata);                                        
                           }
                           else if(rs.getBlob("SEND_DATA")==null)
                           {
                             System.out.printf("Your SEND XML is NULL \n");  
                             String recievedata=lC.extractXml(rs,xmldata1);
                                 System.out.printf("RECIEVE XML is \n",recievedata);
                                 Application.showMessage("Your Recieve XML is NULL \n");
                                 Application.showMessage("Recieve XML is  \n"+ recievedata);                                                          
                                String billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
                                                    
                                 if(billingArrangementID_rtp==null)
                                 {
                                        Application.showMessage("billingArrangementID value is NULL");
                                        
                                      // billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");

                                        
                                        continue;
                                 }
                                 else
                                 {
                                   Application.showMessage("billingArrangementID value from v is::"+billingArrangementID_rtp);
                                   Application.showMessage(c.getValue("ACC_input"));
                                   Application.showMessage(billingArrangementID_rtp);
                                   if(billingArrangementID_rtp == c.getValue("ACC_input"))
                                   {
                                          Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                          Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_rtp);
                                   }
                                    else
                                   {
                                          continue;
                                   }                                                             
                                 }                     
                           }
                           else
                                 {
                             
                               String recievedata = lC.extractXml(rs,xmldata1); 
                              // Application.showMessage("Recieve XML is  \n"+ recievedata);
                                 System.out.printf("RECIEVE XML is %s \n",recievedata); 
                                 
                                 String senddata = lC.extractXml(rs,xmldata2);
                                 System.out.printf("SEND XML is %s \n",senddata);
                                
                                 
                                 String CCentrl =lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>"); 
                              
                                      
                                 if(CCentrl.equals(null))
                                 {
                                        System.out.printf("Call not found \n");
                                       continue;
                                 }
                                 else
                                 {
                                   
                                   Application.showMessage("Ccentral number fetched from numerix activateUnit is::"+CCentrl);
                                   Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                                   //Application.showMessage(c.getValue("ACC_input"));
                                   //Application.showMessage(billingArrangementID_getCus);
                                   
                                   
                                   /* String Numerx_sUnitKey= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                            
                                     Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey);
                                   */
                                   
                                   if(CCentrl.equals(c.getValue("CcentralNum")))
                                   {
                                       
                                          Application.showMessage("Numerix activateUnit call found"+CCentrl);
                                          callFound=1;
                                   }
                                   else
                                   {
                                          continue;
                                   }
                                   
                                   if(callFound==1)
                                   {
                                          String Numerx_iAccountId= lC.nodeFromKey(senddata,"<Numerex:iAccountId>","</Numerex:iAccountId>");
                                                   
                                           Application.showMessage("SEND Xml Numerx_iAccountId is ::"+Numerx_iAccountId);
                                           
                             /* String Numerx_sUnitKey1= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                                   
                                           Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey1);
                                   */
                                   /*recv data*/
                                   
                                           String Numerix_activateUnitResult = lC.nodeFromKey(recievedata,"<activateUnitResult xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</activateUnitResult>");
                                           Application.showMessage("SEND Xml Numerix_activateUnitResult is ::"+Numerix_activateUnitResult); 
                                   
                                           String Numerix_iErrorCode = lC.nodeFromKey(recievedata,"<iErrorCode xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</iErrorCode>");
                                           Application.showMessage("SEND Xml Numerix_iErrorCode is ::"+Numerix_iErrorCode); 
                                           
                                           String Numerix_sErrorMsg = lC.nodeFromKey(recievedata," <sErrorMsg xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</sErrorMsg></activateUnitResponse>");
                                           Application.showMessage("SEND Xml Numerix_sErrorMsg is ::"+Numerix_sErrorMsg); 
                                           
                                   }        
                                                     
                                   // ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));                       
                            }
                           
                                 }
                   
                Application.showMessage("********WebService Call::Jasper Valiadted ********");   
                                  st.close();
 
                       }
                 
               
               
               }
               catch (SQLException e)
               {
                     //OutPut=0;
                   Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                     return;
               }
       }
        
        
        
        
 public void Numerix_ResumeUnit(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
        
        {
               
               Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
               LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
               ResultSet  rs;
               String xmldata1 ="receive_data";
          String xmldata2 ="SEND_DATA";
          int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
          Application.showMessage("----------------------------------------------------------");
          Application.showMessage("*****Numerix Function******");    
          Application.showMessage("----------------------------------------------------------");
         // Application.showMessage("Value is"+c.getValue("ACC_input"));
               try
               {
                     Statement st =lC. dBConnect(input, c);
                 rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cosims:NumerexSimulatorLogMsg/resumeUnit' order by creation_time desc)where rownum <= 20");
                 while (rs.next() && rs.getString(1)!= c.getValue("BaseMsgid"))
                       {
                           
                            String rowmsg = rs.getString(1);             
                           if(rs.getBlob("receive_data")==null)
                           {
                           
                             System.out.printf("Your Recieve XML is NULL \n");
                             Application.showMessage("Your Recieve XML is NULL \n");
                             String senddata =lC.extractXml(rs,xmldata2);
                                 System.out.printf("SEND XML is  \n",senddata );   
                                 Application.showMessage("SEND XML is  \n"+ senddata);                                        
                           }
                           else if(rs.getBlob("SEND_DATA")==null)
                           {
                             System.out.printf("Your SEND XML is NULL \n");  
                             String recievedata=lC.extractXml(rs,xmldata1);
                                 System.out.printf("RECIEVE XML is \n",recievedata);
                                 Application.showMessage("Your Recieve XML is NULL \n");
                                 Application.showMessage("Recieve XML is  \n"+ recievedata);                                                          
                                String billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
                                                    
                                 if(billingArrangementID_rtp==null)
                                 {
                                        Application.showMessage("billingArrangementID value is NULL");
                                        
                                      // billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");

                                        
                                        continue;
                                 }
                                 else
                                 {
                                   Application.showMessage("billingArrangementID value from v is::"+billingArrangementID_rtp);
                                   Application.showMessage(c.getValue("ACC_input"));
                                   Application.showMessage(billingArrangementID_rtp);
                                   if(billingArrangementID_rtp == c.getValue("ACC_input"))
                                   {
                                          Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                          Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_rtp);
                                   }
                                    else
                                   {
                                          continue;
                                   }                                                             
                                 }                     
                           }
                           else
                                 {
                             
                               String recievedata = lC.extractXml(rs,xmldata1); 
                              // Application.showMessage("Recieve XML is  \n"+ recievedata);
                                 System.out.printf("RECIEVE XML is %s \n",recievedata); 
                                 
                                 String senddata = lC.extractXml(rs,xmldata2);
                                 System.out.printf("SEND XML is %s \n",senddata);
                                
                                 
                                 String CCentrl =lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>"); 
                              
                                      
                                 if(CCentrl.equals(null))
                                 {
                                        System.out.printf("Call not found \n");
                                       continue;
                                 }
                                 else
                                 {
                                   
                                   Application.showMessage("Ccentral number fetched from numerix activateUnit is::"+CCentrl);
                                   Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                                   //Application.showMessage(c.getValue("ACC_input"));
                                   //Application.showMessage(billingArrangementID_getCus);
                                   
                                   
                                   /* String Numerx_sUnitKey= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                            
                                     Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey);
                                   */
                                   
                                   if(CCentrl.equals(c.getValue("CcentralNum")))
                                   {
                                       
                                          Application.showMessage("Numerix activateUnit call found"+CCentrl);
                                          callFound=1;
                                   }
                                   else
                                   {
                                          continue;
                                   }
                                   
                                   if(callFound==1)
                                   {
                                          String Numerx_iAccountId= lC.nodeFromKey(senddata,"<Numerex:iAccountId>","</Numerex:iAccountId>");
                                                   
                                           Application.showMessage("SEND Xml Numerx_iAccountId is ::"+Numerx_iAccountId);
                                           
                             /* String Numerx_sUnitKey1= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                                   
                                           Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey1);
                                   */
                                   /*recv data*/
                                   
                                           String Numerix_activateUnitResult = lC.nodeFromKey(recievedata,"<activateUnitResult xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</activateUnitResult>");
                                           Application.showMessage("SEND Xml Numerix_activateUnitResult is ::"+Numerix_activateUnitResult); 
                                   
                                           String Numerix_iErrorCode = lC.nodeFromKey(recievedata,"<iErrorCode xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</iErrorCode>");
                                           Application.showMessage("SEND Xml Numerix_iErrorCode is ::"+Numerix_iErrorCode); 
                                           
                                           String Numerix_sErrorMsg = lC.nodeFromKey(recievedata," <sErrorMsg xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</sErrorMsg></activateUnitResponse>");
                                           Application.showMessage("SEND Xml Numerix_sErrorMsg is ::"+Numerix_sErrorMsg); 
                                           
                                   }        
                                                     
                                   // ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));                       
                            }
                           
                                 }
                   
                Application.showMessage("********WebService Call::Jasper Valiadted ********");   
                                  st.close();
 
                       }
                 
               
               
               }
               catch (SQLException e)
               {
                     //OutPut=0;
                   Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                     return;
               }
       }
        
        
        
        
 public void Numerix_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
        
        {
               
               Thread.sleep(20000); // Think time in JVM [waits for 10 secs here]
               LibraryRtp_UpDown  lC= new LibraryRtp_UpDown ();
               ResultSet  rs;
               String xmldata1 ="receive_data";
          String xmldata2 ="SEND_DATA";
          int ValidationSuccess=0,callFound=0,v1=0,v2=0,v3=0;
          Application.showMessage("----------------------------------------------------------");
          Application.showMessage("*****Numerix Function******");    
          Application.showMessage("----------------------------------------------------------");
         // Application.showMessage("Value is"+c.getValue("ACC_input"));
               try
               {
                     Statement st =lC. dBConnect(input, c);
                 rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'Numerex:NMRXCCSoap/activateUnit' order by creation_time desc)where rownum <= 20");
                 while (rs.next() && rs.getString(1)!= c.getValue("BaseMsgid"))
                       {
                           
                            String rowmsg = rs.getString(1);             
                           if(rs.getBlob("receive_data")==null)
                           {
                           
                             System.out.printf("Your Recieve XML is NULL \n");
                             Application.showMessage("Your Recieve XML is NULL \n");
                             String senddata =lC.extractXml(rs,xmldata2);
                                 System.out.printf("SEND XML is  \n",senddata );   
                                 Application.showMessage("SEND XML is  \n"+ senddata);                                        
                           }
                           else if(rs.getBlob("SEND_DATA")==null)
                           {
                             System.out.printf("Your SEND XML is NULL \n");  
                             String recievedata=lC.extractXml(rs,xmldata1);
                                 System.out.printf("RECIEVE XML is \n",recievedata);
                                 Application.showMessage("Your Recieve XML is NULL \n");
                                 Application.showMessage("Recieve XML is  \n"+ recievedata);                                                          
                                String billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");
                                                    
                                 if(billingArrangementID_rtp==null)
                                 {
                                        Application.showMessage("billingArrangementID value is NULL");
                                        
                                      // billingArrangementID_rtp = lC.nodeFromKey(recievedata,"<typ:billingArrangementID xmlns:typ=\"http://xml.comcast.com/homesecurity/types\">","</typ:billingArrangementID>");

                                        
                                        continue;
                                 }
                                 else
                                 {
                                   Application.showMessage("billingArrangementID value from v is::"+billingArrangementID_rtp);
                                   Application.showMessage(c.getValue("ACC_input"));
                                   Application.showMessage(billingArrangementID_rtp);
                                   if(billingArrangementID_rtp == c.getValue("ACC_input"))
                                   {
                                          Application.showMessage("*******Validation Point 1 :: WebServicall-rtpTestInterface********");
                                          Application.showMessage("Validation is Successfull with Input Account Number"+billingArrangementID_rtp);
                                   }
                                    else
                                   {
                                          continue;
                                   }                                                             
                                 }                     
                           }
                           else
                                 {
                             
                               String recievedata = lC.extractXml(rs,xmldata1); 
                              // Application.showMessage("Recieve XML is  \n"+ recievedata);
                                 System.out.printf("RECIEVE XML is %s \n",recievedata); 
                                 
                                 String senddata = lC.extractXml(rs,xmldata2);
                                 System.out.printf("SEND XML is %s \n",senddata);
                                
                                 
                                 String CCentrl =lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>"); 
                              
                                      
                                 if(CCentrl.equals(null))
                                 {
                                        System.out.printf("Call not found \n");
                                       continue;
                                 }
                                 else
                                 {
                                   
                                   Application.showMessage("Ccentral number fetched from numerix activateUnit is::"+CCentrl);
                                   Application.showMessage("Ccentral from input is::"+c.getValue("CcentralNum"));                                    
                                   //Application.showMessage(c.getValue("ACC_input"));
                                   //Application.showMessage(billingArrangementID_getCus);
                                   
                                   
                                   /* String Numerx_sUnitKey= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                            
                                     Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey);
                                   */
                                   
                                   if(CCentrl.equals(c.getValue("CcentralNum")))
                                   {
                                       
                                          Application.showMessage("Numerix activateUnit call found"+CCentrl);
                                          callFound=1;
                                   }
                                   else
                                   {
                                          continue;
                                   }
                                   
                                   if(callFound==1)
                                   {
                                          String Numerx_iAccountId= lC.nodeFromKey(senddata,"<Numerex:iAccountId>","</Numerex:iAccountId>");
                                                   
                                           Application.showMessage("SEND Xml Numerx_iAccountId is ::"+Numerx_iAccountId);
                                           
                             /* String Numerx_sUnitKey1= lC.nodeFromKey(senddata,"<Numerex:sUnitKey>","</Numerex:sUnitKey>");
                                                   
                                           Application.showMessage("SEND Xml Numerx_sUnitKey is ::"+Numerx_sUnitKey1);
                                   */
                                   /*recv data*/
                                   
                                           String Numerix_activateUnitResult = lC.nodeFromKey(recievedata,"<activateUnitResult xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</activateUnitResult>");
                                           Application.showMessage("SEND Xml Numerix_activateUnitResult is ::"+Numerix_activateUnitResult); 
                                   
                                           String Numerix_iErrorCode = lC.nodeFromKey(recievedata,"<iErrorCode xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</iErrorCode>");
                                           Application.showMessage("SEND Xml Numerix_iErrorCode is ::"+Numerix_iErrorCode); 
                                           
                                           String Numerix_sErrorMsg = lC.nodeFromKey(recievedata," <sErrorMsg xmlns=\"http://webservice.numerexpress.com/NMRXCC/\">","</sErrorMsg></activateUnitResponse>");
                                           Application.showMessage("SEND Xml Numerix_sErrorMsg is ::"+Numerix_sErrorMsg); 
                                           
                                   }        
                                                     
                                   // ResultSet rs1 = st1.executeQuery("select utl_raw.cast_to_varchar2(dbms_lob.substr(SEND_DATA)) from CWDEVOP.CWMESSAGELOG where MSGID="+rs.getString(1));                       
                            }
                           
                                 }
                   
                Application.showMessage("********WebService Call::Jasper Valiadted ********");   
                                  st.close();
 
                       }
                 
               
               
               }
               catch (SQLException e)
               {
                     //OutPut=0;
                   Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                     return;
               }
       }
        
        
        

    	public void orderUpdateCos_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
    	{
    		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
    	     LibraryRtp  lC= new LibraryRtp ();
    		 ResultSet  rs;
    		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
    		// String xmldata1 ="receive_data";
    	     String xmldata2 ="SEND_DATA";
    	    
    	     Application.showMessage("-------------------------------------------------------------");
    	     Application.showMessage("***********orderUpdateCos_Validate Function**************");       
    	     Application.showMessage("-------------------------------------------------------------");
    	         
    		try
    		{
    			 Statement st =lC. dBConnect(input, c);	
    	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
    	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
    	         {
    	        	
    	        
    	            String rowmsg = rs.getString(1);
    	            if(rs.getBlob("receive_data")==null)
    	            {
    	            
    	            	Application.showMessage("Your Recieve XML is NULL \n");
    	            	
    	            	String senddata = lC.extractXml(rs,xmldata2);           
    		           
    		          	           
    	                String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
    	 	            Application.showMessage("accountNumber is ::"+accountNumber_ou); 
    	 	            if(accountNumber_ou==null)
    		            {
    	 	        	   Application.showMessage("Send Xml billingOrderId is NULL");
    			            continue;
    		             }
    	 	           else if(accountNumber_ou.equals(c.getValue("ACC_input")))
    	            	{
    		            	
    		            	Application.showMessage("SEND XML is :: \n"+senddata);
    	            		//System.out.printf("SEND XML is %s \n",senddata);
    	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
    	            		Application.showMessage("Validation is Successfull with House Number :"+accountNumber_ou);
    	            		callFound=1;
    	            	}
    	            	else
    	            	{
    	            		continue;
    	            	}
    		            
    		            if(callFound==1)
    		            {
    		      
    		 	            
    		            	String Service;
    		 	            String vomInstance= lC.nodeFromKey(senddata,"<value name=\"vomInstance\">","</value><value name=\"ordSource\">");
    		 	            Application.showMessage("vomInstance is ::"+vomInstance); 

    		 	           
    		 	            
    		 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
    		 	            {
    		 	        	   
    		 	        	  String ordSource= lC.nodeFromKey(senddata,"</value><value name=\"ordSource\">","</value><value name=\"billingOrderId\">");
    			 	          Application.showMessage("ordSource is ::"+ordSource); 
    		 	              String billingOrderId= lC.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
    		 	              Application.showMessage("billingOrderId is ::"+billingOrderId); 
    		 	            
    			 	           if(billingOrderId==null)
    				            {
    					            c.report("Send Xml billingOrderId is NULL");
    					            continue;
    				            }
    				            else
    				            {
    				            	
    				            	 if(billingOrderId.equals(c.getValue("CcentralNum")))
    					             {
    					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingOrderId********");
    					            	 Application.showMessage("Validation is Successfull with billingOrderId::"+" "+billingOrderId);
    					            	 v1=1;
    					             }
    					             else
    					             {
    					            	 c.report("billingOrderId at Send Xml not Validated as "+billingOrderId);
    					             }
    				            }
    			 	           
    			 	          if(senddata.contains("Hav Market"))
		            	       {
		            	          Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
    			 	            Application.showMessage("Service is ::"+Service); 
		            	       }
		            	    else
		                    	{
		            		      Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
		            		     Application.showMessage("Service is  :: "+Service);
		                    	}
    			 	          
    			 	          
    			 	            
    			 	           if(Service==null)
    				            {
    					            c.report("Send Xml Service is NULL");
    					            continue;
    				            }
    				            else
    				            {
    				            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
    				            	// HandleInvalidServices hI=new HandleInvalidServices();
    				            	 String sNewService= c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: NEWSERVICE").trim();
    				            	 if(Service.equals(sNewService.trim()))
    					             {
    					            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
    					            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
    					            	 v4=1;
    					            	 c.setValue("Product",Service);
    					             }
    					             else
    					             {
    					            	 c.report("Service at Send Xml not Validated as "+Service);
    					             }
    				            }	
    		 	            
    		 	            }
    		 	            else
    		 	            {
    		 	            	v1=1;
    		 	            	
    		 	            	
    		 	            	if(senddata.contains("Hav Market"))
 		            	       {
 		            	          Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
    		 	 	            Application.showMessage("Service is ::"+Service); 
 		            	       }
 		            	    else
 		                    	{
 		            		      Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
 		            		     Application.showMessage("Service is  :: "+Service);
 		                    	}
     			 	          
    		 	 	            
    		 	 	           if(Service==null)
    		 		            {
    		 			            c.report("Send Xml Service is NULL");
    		 			            continue;
    		 		            }
    		 		            else
    		 		            {
    		 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
    		 		            	 if(Service.equals(c.getValue("RTPDataSourceCollections","RTP_UpDownInputs: NEWSERVICE").trim()))
    		 			             {
    		 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
    		 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
    		 			            	 v4=1;
    		 			            	 c.setValue("Product",Service);
    		 			             }
    		 			             else
    		 			             {
    		 			            	 c.report("Service at Send Xml not Validated as "+Service);
    		 			             }
    		 		            }	
    		 	            }
    		 	          
    		 	            String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
    		 	            Application.showMessage("inputChannel is ::"+inputChannel);
    		 	            
    		 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
    		 	            Application.showMessage("ordType is ::"+ordType);
    		 	            
    		 	           if(ordType==null)
    			            {
    				            c.report("Send Xml ordType is NULL");
    				            continue;
    			            }
    			            else
    			            {
    			            	 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
    			            	 if(ordType.equals("CHG"))
    				             {
    				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
    				            	 Application.showMessage("Validation is Successfull with order type::"+" "+ordType);
    				            	 v2=1;
    				             }
    				             else
    				             {
    				            	 c.report("ordType at Send Xml not Validated as "+ordType);
    				             }
    			            }	
    		 	            
    		 	            
    		 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
    		 	            Application.showMessage("customerType is ::"+customerType); 
    		 	            
    		 	           if(customerType==null)
    			            {
    				            c.report("Send Xml customerType is NULL");
    				            continue;
    			            }
    			            else
    			            {
    			            	 Application.showMessage("customerType from Send Xml   is ::"+" "+customerType);
    			            	 if(customerType.equals("RES"))
    				             {
    				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
    				            	 Application.showMessage("Validation is Successfull with customerType::"+" "+customerType);
    				            	 v3=1;
    				             }
    				             else
    				             {
    				            	 c.report("customerType at Send Xml not Validated as "+customerType);
    				             }
    			            }	
    		 	            
    		 	            
    		 	            
    		 	           	 	           
    		            break;
    		            }
    	            }
    	               
    	         }
    	         if(v1*callFound*v2*v3*v4 ==1)
    	         {
    	         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
    	         	c.setValue("IsRemoveGroup", "false");
    	         	c.setValue("IsUpdateTier", "false");
    	         	c.setValue("IsAddGroup", "false");
    	         	
    	         }
    	         else
    	         {
    	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
    	        	 c.setValue("IsRemoveGroup", "false");
    	        	 c.setValue("IsUpdateTier", "false");
    	        	 c.setValue("IsAddGroup", "false");
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
        
     //-----------------------------
    	
    	public void orderUpdateDis_Validate(Object input, ScriptingContext c) throws InterruptedException, IOException, ClassNotFoundException 
    	{
    		Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
    	     LibraryRtp  lC= new LibraryRtp ();
    		 ResultSet  rs;
    		 int callFound=0,v1=0,v2=0,v3=0,v4=0;
    		// String xmldata1 ="receive_data";
    	     String xmldata2 ="SEND_DATA";
    	    
    	     Application.showMessage("-------------------------------------------------------------");
    	     Application.showMessage("***********OrderUpdate_Validate Function**************");       
    	     Application.showMessage("-------------------------------------------------------------");
    	         
    		try
    		{
    			 Statement st =lC. dBConnect(input, c);	
    	         rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
    	         while (rs.next()&& rs.getString(1)!= c.getValue("BaseMsgid"))
    	         {
    	        	
    	        
    	            String rowmsg = rs.getString(1);
    	            if(rs.getBlob("receive_data")==null)
    	            {
    	            
    	            	Application.showMessage("Your Recieve XML is NULL \n");
    	            	
    	            	String senddata = lC.extractXml(rs,xmldata2);           
    		           
    		          	           
    	                String accountNumber_ou= lC.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"billingSystem\">");
    	 	            Application.showMessage("accountNumber is ::"+accountNumber_ou); 
    	 	           Application.showMessage("accountNumber from Install is ::"+c.getValue("ACC_input")); 
    	 	            
    	 	           if(accountNumber_ou==null)
    		            {
    	 	        	   Application.showMessage("Send Xml billingOrderId is NULL");
    			            continue;
    		            }
    	 	           else if(accountNumber_ou.equals(c.getValue("ACC_input")))
    	            	{
    		            	
    		            	Application.showMessage("SEND XML is :: \n"+senddata);
    	            		//System.out.printf("SEND XML is %s \n",senddata);
    	            		Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update********");
    	            		Application.showMessage("Validation is Successfull with House Number :"+accountNumber_ou);
    	            		callFound=1;
    	            	}
    	            	else
    	            	{
    	            		continue;
    	            	}
    		            
    		            if(callFound==1)
    		            {
    		      
    		 	            

    		 	            String vomInstance= lC.nodeFromKey(senddata,"<value name=\"vomInstance\">","</value><value name=\"ordSource\">");
    		 	            Application.showMessage("vomInstance is ::"+vomInstance); 

    		 	           
    		 	            
    		 	           if(c.getValue("IsDemi").equalsIgnoreCase("true"))
    		 	            {
    		 	            String billingOrderId= lC.nodeFromKey(senddata,"</value><value name=\"billingOrderId\">","</value><value name=\"inputChannel\">");
    		 	            Application.showMessage("billingOrderId is ::"+billingOrderId); 
    		 	            
    			 	           if(billingOrderId==null)
    				            {
    					            c.report("Send Xml billingOrderId is NULL");
    					            continue;
    				            }
    				            else
    				            {
    				            	
    				            	 if(billingOrderId.equals(c.getValue("CcentralNum")))
    					             {
    					            	 Application.showMessage("*******Validation Point 2 :: WebServicall-billingOrderId********");
    					            	 Application.showMessage("Validation is Successfull with billingOrderId::"+" "+billingOrderId);
    					            	 v1=1;
    					             }
    					             else
    					             {
    					            	 c.report("billingOrderId at Send Xml not Validated as "+billingOrderId);
    					             }
    				            }
    			 	           
    			 	            String Service= lC.nodeFromKey(senddata,"Service = ","Hav Market = U").trim();
    			 	            Application.showMessage("Service is ::"+Service); 
    			 	            
    			 	          /* if(Service==null)
    				            {
    					            c.report("Send Xml Service is NULL");
    					            continue;
    				            }
    				            else
    				            {
    				            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
    				            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
    					             {
    					            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
    					            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
    					            	 v4=1;
    					            	 c.setValue("Product",Service);
    					             }
    					             else
    					             {
    					            	 c.report("Service at Send Xml not Validated as "+Service);
 }
    				            }	
    		 	            */
    		 	            }
    		 	            else
    		 	            {
    		 	            	v1=1;
    		 	            	
    		 	            	
    		 	                String Service= lC.nodeFromKey(senddata,"Service = ","</note><description>").trim();
    		 	 	            Application.showMessage("Service is ::"+Service); 
    		 	 	            
    		 	 	          /* if(Service==null)
    		 		            {
    		 			            c.report("Send Xml Service is NULL");
    		 			            continue;
    		 		            }
    		 		            else
    		 		            {
    		 		            	 Application.showMessage("Service from Send Xml   is ::"+" "+Service);
    		 		            	 if(Service.equals(c.getValue("sSERVICEorCURRENTSERVICE").trim()))
    		 			             {
    		 			            	 Application.showMessage("*******Validation Point 5 :: WebServicall-Service********");
    		 			            	 Application.showMessage("Validation is Successfull with Service::"+" "+Service);
    		 			            	 v4=1;
    		 			            	 c.setValue("Product",Service);
    		 			             }
    		 			             else
    		 			             {
    		 			            	 c.report("Service at Send Xml not Validated as "+Service);
    		 			             }
    		 		            }*/	
    		 	            }
    		 	          
    		 	            String inputChannel= lC.nodeFromKey(senddata,"</value><value name=\"inputChannel\">","</value><value name=\"ordType\">");
    		 	            Application.showMessage("inputChannel is ::"+inputChannel);
    		 	            
    		 	            String ordType= lC.nodeFromKey(senddata,"</value><value name=\"ordType\">","</value><value name=\"active\">");
    		 	            Application.showMessage("ordType is ::"+ordType);
    		 	            
    		 	           if(ordType==null)
    			            {
    				            c.report("Send Xml ordType is NULL");
    				            continue;
    			            }
    			            else
    			            {
    			            	 Application.showMessage("ordType from Send Xml   is ::"+" "+ordType);
    			            	 if(ordType.equals("DIS"))
    				             {
    				            	 Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
    				            	 Application.showMessage("Validation is Successfull with lt_fireNumber::"+" "+ordType);
    				            	 v2=1;
    				             }
    				             else
    				             {
    				            	 c.report("lt_fireNumber at Send Xml not Validated as "+ordType);
    				             }
    			            }	
    		 	            
    		 	            
    		 	            String customerType= lC.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"productType\">");
    		 	            Application.showMessage("customerType is ::"+customerType); 
    		 	            
    		 	           if(customerType==null)
    			            {
    				            c.report("Send Xml customerType is NULL");
    				            continue;
    			            }
    			            else
    			            {
    			            	 Application.showMessage("customerType from Send Xml   is ::"+" "+customerType);
    			            	 if(customerType.equals("RES"))
    				             {
    				            	 Application.showMessage("*******Validation Point 4 :: WebServicall-customerType********");
    				            	 Application.showMessage("Validation is Successfull with customerType::"+" "+customerType);
    				            	 v3=1;
    				             }
    				             else
    				             {
    				            	 c.report("customerType at Send Xml not Validated as "+customerType);
    				             }
    			            }	
    		 	            
    		 	            
    		 	           	 	           
    		            break;
    		            }
    	            }
    	               
    	         }
    	         if(callFound*v2 ==1)
    	         {
    	        	 c.setValue("IsDemi", "false");
    	         	Application.showMessage("WebService Call :: OrderUpdate_Validate is Success[All validation points are Success]");
    	         }
    	         else
    	         {
    	        	 c.setValue("IsDemi", "false");
    	        	 c.report("WebService Call ::OrderUpdate_Validate is Failed with Validation Errors");
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
    	
       
    	 }








