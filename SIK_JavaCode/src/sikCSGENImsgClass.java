import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class sikCSGENImsgClass 
{
	sikLibraryClass sl=new sikLibraryClass();
	  public int OutPut=1;
	  int trackcode=0;
	  
	  public void readValuesfromsikRetailswivelCSGExcel(Object input, ScriptingContext c)throws IOException
      {
             Application.showMessage("_______READING VALUES FROM INPUT EXCEL_________");
                 String XML_EndPoint=c.getValue("SwivelCSG", "Swivel_csgDBSettings: XML_Enpoint");
                 String DB_Host=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_HOST");
                 String dB_Password=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_Password");
                 String dB_Username=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_UserName");
                 String dB_Port=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_Port");
                 String dB_serviceName=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_ServiceName");
                 String dB_connectionName=c.getValue("SwivelCSG", "Swivel_csgDBSettings: DB_ConnectionName");
                 String dB_PasswordCommon=c.getValue("SwivelCSG", "Swivel_csgDBSettings: Common_DBPassword");
                 String dB_UsernameCommon=c.getValue("SwivelCSG", "Swivel_csgDBSettings: Common_DBUserName");
                 
           String InputAccountNumber = c.getValue("SwivelCSG", "sik_SwivelCSG: AccountId_ITEMLIST");
           String InputOrderID = c.getValue("SwivelCSG", "sik_SwivelCSG: OrderID");
           String InputLocation_ID = c.getValue("SwivelCSG", "sik_SwivelCSG: Location_ID");
           String InputEmail = c.getValue("SwivelCSG", "sik_SwivelCSG: Email");
           String InputFname = c.getValue("SwivelCSG", "sik_SwivelCSG: FirstName");
           String InputLname = c.getValue("SwivelCSG", "sik_SwivelCSG: LastName");
           String InputcustType = c.getValue("SwivelCSG", "sik_SwivelCSG: custType");
           String InputCustomerId = c.getValue("SwivelCSG", "sik_SwivelCSG: CustomerId");
           String InputTN = c.getValue("SwivelCSG", "sik_SwivelCSG: TN");
           String InputCSG3XPAYMENT = c.getValue("SwivelCSG", "sik_SwivelCSG: CSG_3X_PAYMENT");
           String InputBillCode = c.getValue("SwivelCSG", "sik_SwivelCSG: BillCode");
           String Inputcode = c.getValue("SwivelCSG", "sik_SwivelCSG: code");
           String InputisNegative = c.getValue("SwivelCSG", "sik_SwivelCSG: NegativeFlow");
           String InputisErrorText = c.getValue("SwivelCSG", "sik_SwivelCSG: ErrorCodeExpected");
           
           
           
           c.setValue("OrderID", InputOrderID);
                 c.setValue("accountNumber", InputAccountNumber);
                 c.setValue("Email", InputEmail);
                 c.setValue("Fname", InputFname);
                 c.setValue("Lname", InputLname);
                 c.setValue("locationID", InputLocation_ID);
                 c.setValue("custType", InputcustType);
                 c.setValue("CustomerId", InputCustomerId);
                 c.setValue("TN", InputTN);
                 c.put("CSG_3X_PAYMENT", InputCSG3XPAYMENT);
                 c.put("BillCode", InputBillCode);
                 c.put("code", Inputcode);
                 c.put("isNegative", InputisNegative);
                 c.put("ErrorText", InputisErrorText);
                 
                 
                 
                 
                  Application.showMessage("InputAccountNumber is ::"+InputAccountNumber);
                  Application.showMessage("InputOrderID is ::"+InputOrderID);
                  Application.showMessage("InputEmail is ::"+InputEmail);
                  Application.showMessage("InputFname is ::"+InputFname);
                  Application.showMessage("InputLname is ::"+InputLname);
                  Application.showMessage("InputLocation_ID is ::"+InputLocation_ID);
                  Application.showMessage("InputcustType is ::"+InputcustType);
                  Application.showMessage("InputCustomerId is ::"+InputCustomerId);
                  Application.showMessage("InputTN is ::"+InputTN);
                  Application.showMessage("InputCSG3XPAYMENT is ::"+InputCSG3XPAYMENT);
                  Application.showMessage("InputBillCode is ::"+InputBillCode);
                  Application.showMessage("InputInputcode is ::"+Inputcode);
                  Application.showMessage("InputisNegative is ::"+InputisNegative);
                  Application.showMessage("InputisNegative is ::"+InputisErrorText);
                  
           
           
           
           // DB Data
                 Application.showMessage("Endpoint is ::"+XML_EndPoint);
                 Application.showMessage("DB_HOST is ::"+DB_Host);
                 Application.showMessage("dB_Password is ::"+dB_Password);
                 Application.showMessage("dB_Username is ::"+dB_Username);
                 Application.showMessage("dB_Port is ::"+dB_Port);
                 Application.showMessage("dB_serviceName is ::"+dB_serviceName);
                 Application.showMessage("dB_connectionName is ::"+dB_connectionName);
                 c.setValue("DB_Host", DB_Host);
                 c.setValue("dB_Password",dB_Password);
                 c.setValue("dB_Username",dB_Username);
                 c.setValue("dB_Port",dB_Port);
                 c.setValue("dB_serviceName",dB_serviceName);
                 c.setValue("XML_EndPoint",XML_EndPoint);
                 c.setValue("dB_PasswordCommon",dB_PasswordCommon);
                 c.setValue("dB_UsernameCommon",dB_UsernameCommon);
                 
                 
                
                 
                 
                 
      }


       public void csgENImsg_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
       {
             // Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
              sikLibraryClass sL = new sikLibraryClass();
              ResultSet  rs;
              int LocNull=0;
              int OrderIdNull=0;
              int PhoneNull=0;
              int CustInfoNull=0;
              int callFound=0,v1=0,v2=0,v3=0,v4=0;
              String xmldata1 ="receive_data";
              String xmldata2 ="SEND_DATA";
              String Bid=c.getValue("BaseMsgid");
             

          
            Application.showMessage("-----------------------------------------------------");
            Application.showMessage("***********csgENImsg_Validate Function**************");       
            Application.showMessage("----------------------------------------------------");
            HashMap Operation=sL.findingoperations(input, c);
            c.setValue("OPERATIONVALIDATION",(String) Operation.get("CSGENIMessage"));
            	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CSGENIMessage"));
              try
              {
               // Statement st =sL. dBConnect(input, c);  
                //rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:CSGENIMessageServicePort/processCSGENIMessage' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
            	  rs=sL.reduceThinkTimeSIK(input, c);
                while (rs.next())
                {
         
                     
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
                          
                          //String accountNumber_DDS=sL.GetValueByXPath(recievedata,"/bfcopcom:msgInfo/bfcopcom:parameters/bfcopcom:parameters[2]/bfcopcom:value");
                          
                          
                          
                             String OrderId_csgeni= sL.nodeFromKey(recievedata,"<OrderId>","</OrderId>");
                               Application.showMessage("Order ID is ::"+OrderId_csgeni); 
                                
                                if(OrderId_csgeni==null)
                                 {
                                      OrderIdNull=1;
                                      c.put("pOrderIdNull", OrderIdNull);
                                      Application.showMessage("OrderID is empty for the customer..!!");
                                       break;
                                 }
                                else
                                {
                                  Application.showMessage("Order iD from Input Xcel is "+c.getValue("OrderID"));
                                       if(OrderId_csgeni.equals(c.getValue("OrderID").trim()))
                                   {
                                         Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                         Application.showMessage("SEND XML is :: \n"+senddata);
                                          //System.out.printf("SEND XML is %s \n",senddata);
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
                                          Application.showMessage("Validation is Successfull with Order ID"+OrderId_csgeni);
                                       v2=1;
                                       callFound=1;
                                       
                                       Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                         Application.showMessage("SEND XML is :: \n"+senddata);
                                   }
                                   else
                                   {
                                          c.report("OrderID Validation Failed!!!");
                                          break;
                                   }
                                 
                                 }
                          if(callFound==1)
                           {
                             
                            String accId_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/ItemList/Item/AccountId");
                         // String accId_csgeni= sL.nodeFromKey(recievedata,"</ServiceIdentifier><AccountId>","</AccountId><BillingIdentifier>");
                         Application.showMessage("accountNumber is ::"+accId_csgeni);   
                         
                          if(accId_csgeni==null)
                          {
                                 c.report("Send Xml accId_csgeni is NULL");
                                 continue;
                          }
                         else
                         {
                                 if(accId_csgeni.equals(c.getValue("SwivelCSG","sik_SwivelCSG: AccountId_ITEMLIST")))
                            {
                                   
                                   //System.out.printf("SEND XML is %s \n",senddata);
                                   Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
                                   Application.showMessage("Validation is Successfull with Input accountNumber"+accId_csgeni);
                                   callFound=1;
                            }
                            else
                            {
                                   continue;
                            }
                          
                          }
                         
                          
                                 String LocationId_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Location/LocationId");
  
                            //String LocationId_csgeni= sL.nodeFromKey(recievedata,"<Location><LocationId>","</LocationId><Address>");
                                Application.showMessage("accountNumber is ::"+LocationId_csgeni);     
                                 if(LocationId_csgeni==null)
                                 {
                                   Application.showMessage("LocationID is empty for the customer..!!");
                                  LocNull=1;
                                  c.put("pLocNull", LocNull);
                                       continue;
                                 }
                                else
                                {
                                       if(LocationId_csgeni.equals(c.getValue("locationID")))
                                   {
                                         Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                         Application.showMessage("SEND XML is :: \n"+senddata);
                                          //System.out.printf("SEND XML is %s \n",senddata);
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
                                          Application.showMessage("Validation is Successfull with locationID"+LocationId_csgeni);
                                       v1=1;
                                   }
                                   else
                                   {
                                          
                                          c.report("locationID Validation Failed!!!");
                                          break;
                                   }
                                 
                                 }
                                
                               
                                
                               // String Homeph_csgeni= sL.nodeFromKey(recievedata,"<Phone><Home>","</Home><Business>");
                                
                                String Homeph_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/Phone/Home");
                               
                                Application.showMessage("Home Phone is ::"+Homeph_csgeni); 
                                
                                String Bussinessph_csgeni= sL.nodeFromKey(recievedata,"</Home><Business>","</Business></Phone>");
                               Application.showMessage("Bussiness Phone is ::"+Bussinessph_csgeni); 
                                
                                if(Homeph_csgeni==null && Bussinessph_csgeni==null)
                                 {
                                     
                                        Application.showMessage("Home and Bussiness Phone Numbers are empty for the customer..!!");
                                       PhoneNull=1;
                                      c.put("pPhoneNull", PhoneNull);
                                         continue;
                                 }
                                
                                String CustomerId_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/CustomerId");
                              // String CustomerId_csgeni= sL.nodeFromKey(recievedata,"<CustomerId>","</CustomerId>");
                               Application.showMessage("CustomerId is ::"+CustomerId_csgeni); 
                                
                               // String Firstn_csgeni= sL.nodeFromKey(recievedata,"<Name><First>","</First><Last>");
                              String Firstn_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/Name/First");
                               Application.showMessage("First Name is ::"+OrderId_csgeni); 
                                
                                //String LastN_csgeni= sL.nodeFromKey(recievedata,"</First><Last>","</Last>");
                              String LastN_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/Name/Last");
                               Application.showMessage("Last Name is ::"+OrderId_csgeni); 
                                
                                if(CustomerId_csgeni==null || Firstn_csgeni==null ||LastN_csgeni==null  )
                                 {
                                      CustInfoNull=1;
                                      c.put("pCustInfoNull", CustInfoNull);
                                       continue;
                                 }
                                else
                                {
                                       if(CustomerId_csgeni.equals(c.getValue("CustomerId")))
                                   {
                                         Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                         Application.showMessage("SEND XML is :: \n"+senddata);
                                          //System.out.printf("SEND XML is %s \n",senddata);
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
                                          Application.showMessage("Validation is Successfull with Customer ID"+CustomerId_csgeni);
                                       v3=1;
                                   }
                                   else
                                   {
                                          c.report("CustomerId Validation Failed!!!");
                                   }
                                 
                                 }
                               
                             
                          break;
                          }
                    }
                }
                
                if(v1*callFound ==1)
                {
                     Application.showMessage("WebService Call :: bfcRequest_Validate is Success[All validation points are Success]");
                }
                else
                {
                      c.put("result", "false");
                      c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
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
       

       public int csgENImsg_Validate1(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathException, SAXException 
       {
              Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
            sikLibraryClass sL = new sikLibraryClass();
              ResultSet  rs;
              int LocNull=0;
              int OrderIdNull=0;
              int PhoneNull=0;
              int CustInfoNull=0;
              int callFound=0,v1=0,v2=0,v3=0,v4=0;
              String xmldata1 ="receive_data";
              String xmldata2 ="SEND_DATA";
           
            Application.showMessage("-----------------------------------------------------");
            Application.showMessage("***********csgENImsg_Validate Function**************");       
            Application.showMessage("----------------------------------------------------");
            HashMap Operation=sL.findingoperations(input, c);
            c.setValue("OPERATIONVALIDATION",(String) Operation.get("CSGENIMessage"));
            	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("CSGENIMessage"));
              try
              {
              //  Statement st =sL. dBConnect(input, c);  
             //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'op:CSGENIMessageServicePort/processCSGENIMessage' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
                          Application.showMessage("senddata is ::"+senddata); 
                          Application.showMessage("recievedata is ::"+recievedata); 
                          
                          //String accountNumber_DDS=sL.GetValueByXPath(recievedata,"/bfcopcom:msgInfo/bfcopcom:parameters/bfcopcom:parameters[2]/bfcopcom:value");
                          
                          
                          
                             String OrderId_csgeni= sL.nodeFromKey(recievedata,"<OrderId>","</OrderId>");
                               Application.showMessage("Order ID is ::"+OrderId_csgeni); 
                                
                                if(OrderId_csgeni==null)
                                 {
                                      OrderIdNull=1;
                                      c.put("pOrderIdNull", OrderIdNull);
                                      Application.showMessage("OrderID is empty for the customer..!!");
                                       break;
                                 }
                                else
                                {
                                  Application.showMessage("Order iD from Input Xcel is "+c.getValue("OrderID"));
                                       if(OrderId_csgeni.equals(c.getValue("OrderID").trim()))
                                   {
                                         Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                         Application.showMessage("SEND XML is :: \n"+senddata);
                                          //System.out.printf("SEND XML is %s \n",senddata);
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
                                          Application.showMessage("Validation is Successfull with Order ID"+OrderId_csgeni);
                                       v2=1;
                                       callFound=1;
                                       
                                       Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                         Application.showMessage("SEND XML is :: \n"+senddata);
                                   }
                                   else
                                   {
                                          c.report("OrderID Validation Failed!!!");
                                          break;
                                   }
                                 
                                 }
                          if(callFound==1)
                           {
                             
                            String accId_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/ItemList/Item/AccountId");
                         // String accId_csgeni= sL.nodeFromKey(recievedata,"</ServiceIdentifier><AccountId>","</AccountId><BillingIdentifier>");
                         Application.showMessage("accountNumber is ::"+accId_csgeni);   
                         
                          if(accId_csgeni==null)
                          {
                                 c.report("Send Xml accId_csgeni is NULL");
                                 continue;
                          }
                         else
                         {
                                 if(accId_csgeni.equals(c.getValue("SwivelCSG","sik_SwivelCSG: AccountId_ITEMLIST")))
                            {
                                   
                                   //System.out.printf("SEND XML is %s \n",senddata);
                                   Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
                                   Application.showMessage("Validation is Successfull with Input accountNumber"+accId_csgeni);
                                   callFound=1;
                            }
                            else
                            {
                                   continue;
                            }
                          
                          }
                         
                          
                                 String LocationId_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Location/LocationId");
  
                            //String LocationId_csgeni= sL.nodeFromKey(recievedata,"<Location><LocationId>","</LocationId><Address>");
                                Application.showMessage("accountNumber is ::"+LocationId_csgeni);     
                                 if(LocationId_csgeni==null)
                                 {
                                   Application.showMessage("LocationID is empty for the customer..!!");
                                  LocNull=1;
                                  c.put("pLocNull", LocNull);
                                       continue;
                                 }
                                else
                                {
                                       if(LocationId_csgeni.equals(c.getValue("locationID")))
                                   {
                                         Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                         Application.showMessage("SEND XML is :: \n"+senddata);
                                          //System.out.printf("SEND XML is %s \n",senddata);
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
                                          Application.showMessage("Validation is Successfull with locationID"+LocationId_csgeni);
                                       v1=1;
                                   }
                                   else
                                   {
                                          
                                          c.report("locationID Validation Failed!!!");
                                          break;
                                   }
                                 
                                 }
                                
                               
                                
                               // String Homeph_csgeni= sL.nodeFromKey(recievedata,"<Phone><Home>","</Home><Business>");
                                
                                String Homeph_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/Phone/Home");
                               
                                Application.showMessage("Home Phone is ::"+Homeph_csgeni); 
                                
                                String Bussinessph_csgeni= sL.nodeFromKey(recievedata,"</Home><Business>","</Business></Phone>");
                               Application.showMessage("Bussiness Phone is ::"+Bussinessph_csgeni); 
                                
                                if(Homeph_csgeni==null && Bussinessph_csgeni==null)
                                 {
                                     
                                        Application.showMessage("Home and Bussiness Phone Numbers are empty for the customer..!!");
                                       PhoneNull=1;
                                      c.put("pPhoneNull", PhoneNull);
                                        continue;
                                 }
                                
                                String CustomerId_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/CustomerId");
                              // String CustomerId_csgeni= sL.nodeFromKey(recievedata,"<CustomerId>","</CustomerId>");
                               Application.showMessage("CustomerId is ::"+CustomerId_csgeni); 
                                
                               // String Firstn_csgeni= sL.nodeFromKey(recievedata,"<Name><First>","</First><Last>");
                              String Firstn_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/Name/First");
                               Application.showMessage("First Name is ::"+OrderId_csgeni); 
                                
                                //String LastN_csgeni= sL.nodeFromKey(recievedata,"</First><Last>","</Last>");
                              String LastN_csgeni = sL.GetValueByXPath(recievedata, "/msg/body/OrderDetailUpdated/Customer/Name/Last");
                               Application.showMessage("Last Name is ::"+OrderId_csgeni); 
                                
                                if(CustomerId_csgeni==null || Firstn_csgeni==null ||LastN_csgeni==null  )
                                 {
                                      CustInfoNull=1;
                                      c.put("pCustInfoNull", CustInfoNull);
                                       continue;
                                 }
                                else
                                {
                                       if(CustomerId_csgeni.equals(c.getValue("CustomerId")))
                                        {
                                         Application.showMessage("Recieve XML is::  \n"+ recievedata);
                                         Application.showMessage("SEND XML is :: \n"+senddata);
                                          //System.out.printf("SEND XML is %s \n",senddata);
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-CSGeNI Call********");
                                          Application.showMessage("Validation is Successfull with Customer ID"+CustomerId_csgeni);
                                          v3=1;
                                         }
                                       else
                                         {
                                          c.report("CustomerId Validation Failed!!!");
                                         }
                                 
                                 }
                               
                             
                          break;
                          }
                    }
                }
                
                if(v1*callFound ==1)
                {
                     Application.showMessage("WebService Call :: bfcRequest_Validate is Success[All validation points are Success]");
                }
                else
                {
                	  OutPut=0;
                      c.put("result", "false");
                      c.report("WebService Call ::bfcRequest_Validate is Failed with Validation Errors");
                }
             //   st.close();
              }      
              catch (SQLException e)
              {
                  Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                    
              }
              return  OutPut;
       }
       
       
       
       
       
       
       
       public int QueryLocation_Validate1(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
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
                   //  Statement st =sL. dBConnect(input, c);  
             //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 50");
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
                         else if(GT_loc.equals(c.getValue("locationID")))
                     {
                            Application.showMessage("Recieve XML is::  \n"+ recievedata);
                            Application.showMessage("SEND XML is :: \n"+senddata);
                            //System.out.printf("SEND XML is %s \n",senddata);
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
                    
                                 String typ_customerType= sL.nodeFromKey(recievedata,"<typ:customerType>","</typ:customerType><typ:locationStatus>");
                                Application.showMessage("typ_customerType is ::"+typ_customerType);
                                
                                
                                 
                                 String LegacyLocationID_GT= sL.nodeFromKey(recievedata,"<typ:legacyLocationIDType><typ:LegacyLocationID>","</typ:LegacyLocationID><typ:LegacyLocationIDSource>");
                                Application.showMessage("LegacyLocationID_GT is ::"+LegacyLocationID_GT); 
                                 
                                 String headendNetworkAddress_GT= sL.nodeFromKey(recievedata,"<typ:headendNetworkAddress>","</typ:headendNetworkAddress>");
                                Application.showMessage("headendNetworkAddress_GT is ::"+headendNetworkAddress_GT);                              
                                 c.put("GL_headendNetworkAddress", headendNetworkAddress_GT);
                                
                                    //-------------------------------------------------------------------//
                                      //
                                      //                                   Fetching Get Location Address.
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
                                
                               
                                 
                             
                                   String FranchiseTaxArea= sL.nodeFromKey(recievedata,"<typ:Agent>","</typ:Agent>");
                                       Application.showMessage("FranchiseTaxArea is ::"+FranchiseTaxArea); 
                                        c.put("GL_ftaAgent", FranchiseTaxArea);
                                
                                 
                                 
                                   
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
                	  OutPut=0;
                      c.put("result", "false");
                      c.report("WebService Call ::getLocation_Validate is Failed with Validation Errors");
                }
             //   st.close();
              }      
              catch (SQLException e)
              {
                  Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                    
              }
              return  OutPut;
       }
       
       
       
       
       public int submitOrder_ValidateOld(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException, ParserConfigurationException 
       {
              Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
            sikLibraryClass sL = new sikLibraryClass();
              ResultSet  rs;
              int callFound=0,v1=0,v2=0,v3=0,v4=0;
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
                   //  Statement st =sL. dBConnect(input, c);  
               // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
                                       
                          String sik_AccountNumber= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
                     
                     //String sik_AccountNumber = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:AccountNumber");
                     
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
                            v1=1;
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
                                       String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
                                             Application.showMessage("OrderID is::"+OrderID_sik); 
                                              c.put("pOrderID_sik", OrderID_sik);      
                                 }
                                 else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-213"))
                                 {
                                      c.put("MFlag","1");
                                      
                                        String orderid_modify= sL.nodeFromKey(recievedata,"</code><text>Duplicate Order - Single Not Exact Match: Duplicate order detected; matched order #","</text></message></messages");
                               Application.showMessage("Modify Order ID found is :: "+orderid_modify);
                               c.put("MOrderID", orderid_modify);
                                 }
                                 else
                                 {
                                      Application.showMessage(" Failure for Submit..!");
                                      break;
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
                                     String sik_Email= sL.nodeFromKey(senddata,"<sik:Email>","</sik:Email>");
                                     
                                     //String sik_Email = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");

                                       Application.showMessage("sik_Email is ::"+c.getValue("Email")); 
                                        if(sik_Email==null)
                                       {
                                              c.report(" sik_Email is NULL");
                                              continue;
                                       }
                                       else
                                       {
                                          Application.showMessage("sik_Email from Send Xml  from Submit Order is ::"+" "+sik_Email);
                                          if(sik_Email.trim().equalsIgnoreCase(c.getValue("Email").trim()))
                                               {
                                                 Application.showMessage("*******Validation Point 4 :: WebServicall-Email********");
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
                                
                                //String sik_FirstName = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:FirstName");
                               
                                 Application.showMessage("firstName is ::"+sik_FirstName);
                                
                                 if(sik_FirstName==null)
                                 {
                                       c.report("Send Xml FirstName is NULL");
                                       continue;
                                 }
                                 else
                                 {
                                    Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+sik_FirstName);
                                    if(sik_FirstName.equals(c.getValue("Fname")))
                                        {
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
                                          Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_FirstName);
                                          v2=1;
                                        }
                                        else
                                        {
                                          c.report("FirstName at Send Xml not Validated as "+sik_FirstName);
                                        }
                                 }        

                                String sik_LastName= sL.nodeFromKey(senddata,"<sik:LastName>","</sik:LastName>");
                               //String sik_LastName = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:LastName");
                                Application.showMessage("lastName is ::"+sik_LastName); 
                                 
                                if(sik_LastName==null)
                                 {
                                       c.report("Send Xml LastName is NULL");
                                       continue;
                                 }
                                 else
                                 {
                                    if(sik_LastName.equals(c.getValue("Lname")))
                                        {
                                          Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
                                          Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_LastName);
                                          v4=1;
                                        }
                                        else
                                        {
                                          c.report("LastName at Send Xml not Validated as "+sik_LastName);
                                        }
                                 }

                               
                                 
                                 String sik_ShipmentPriority= sL.nodeFromKey(senddata,"<sik:ShipmentPriority>","</sik:ShipmentPriority>");
                                Application.showMessage("ShipmentPriority is ::"+sik_ShipmentPriority); 
                                 
                                 //String sik_FTA= sL.nodeFromKey(senddata,"<sik:FTA>","</sik:FTA>");
                                //Application.showMessage("sik_FTA is ::"+sik_FTA);
                                
                                 
                                 
                                 
                                 Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));

                                //Application.showMessage("FTA from Get Loc" +c.get("GL_ftaAgent"));
                                //Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
                                

                              
                                 String sik_Address1= sL.nodeFromKey(senddata,"<sik:Address1>","</sik:Address1>");
                                Application.showMessage("sik_Address1 is ::"+sik_Address1); 
                                 
                                // String sik_Address2= sL.nodeFromKey(senddata,"<sik:Address2>","</sik:Address2>");
                               //Application.showMessage("sik_Address2 is ::"+sik_Address2); 
                                 
                                 String sik_City= sL.nodeFromKey(senddata,"<sik:City>","</sik:City>");
                                Application.showMessage("sik_City is ::"+sik_City); 
                                 
                                 String sik_State= sL.nodeFromKey(senddata,"<sik:State>","</sik:State>");
                                Application.showMessage("sik_State is ::"+sik_State); 
                                 
                                 String sik_Zip= sL.nodeFromKey(senddata,"<sik:Zip>","</sik:Zip>");
                                Application.showMessage("sik_Zip is ::"+sik_Zip); 
                                 
                                 String sik_IsAlternateAddress= sL.nodeFromKey(senddata,"<sik:IsAlternateAddress>","</sik:IsAlternateAddress>");
                                Application.showMessage("sik_IsAlternateAddress is ::"+sik_IsAlternateAddress); 
                                 
                                 
                                 
                                 String sik_ProductCode= sL.nodeFromKey(senddata,"<sik:ProductCode>","</sik:ProductCode>");
                                Application.showMessage("sik_ProductCode is ::"+sik_ProductCode); 
                                                                                  
                                 String sik_DNCSIPAddress= sL.nodeFromKey(senddata,"<sik:DNCSIPAddress>","</sik:DNCSIPAddress>");
                                Application.showMessage("sik_DNCSIPAddress is ::"+sik_DNCSIPAddress); 
                                                      
                                 String sik_HeadEndVendor= sL.nodeFromKey(senddata,"<sik:HeadEndVendor>","</sik:HeadEndVendor>");
                                Application.showMessage("sik_HeadEndVendor is ::"+sik_HeadEndVendor); 
                                                             
                                 String sik_E911_acceptance= sL.nodeFromKey(senddata,"<sik:E911_acceptance>","</sik:E911_acceptance>");
                                Application.showMessage("sik_E911_acceptance is ::"+sik_E911_acceptance); 
                                           
                                 String sik_AgentID= sL.nodeFromKey(senddata,"<sik:AgentID>","</sik:AgentID>");
                                Application.showMessage("sik_AgentID is ::"+sik_AgentID); 
                                 
                                 String sik_WorkOrderID= sL.nodeFromKey(senddata,"<sik:WorkOrderID>","</sik:WorkOrderID>");
                                Application.showMessage("sik_WorkOrderID is ::"+sik_WorkOrderID); 
                                 c.put("psik_WorkOrderID", sik_WorkOrderID);  
                                                      
                                 
                                
                                               
                                 
                          break;
                          }
                    }
                }
                
                if(v1*callFound*v2*v3*v4*trackcode ==1)
                {
                     Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
                }
                else
                {
                	  OutPut=0;
                      c.put("result", "false");
                      c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
                }
              //  st.close();
              }      
              catch (SQLException e)
              {
                  Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                    
              }
              return  OutPut;
       }
       
       public void sendToSIK_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
       {
              Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
             sikLibraryClass sL = new sikLibraryClass();
              ResultSet  rs;
              int callFound=0,v1=0,v2=0,v3=0,v4=0;
              String xmldata1 ="receive_data";
              String xmldata2 ="SEND_DATA";
           
            Application.showMessage("-----------------------------------------------------");
            Application.showMessage("************SEND TO SIK_Validate Function************");       
            Application.showMessage("----------------------------------------------------");
                
              try
              {
                     Statement st =sL. dBConnect(input, c);  
                rs = st.executeQuery("select * from (select msgid, operation, USER_ID  from cwmessagelog where operation = 'swivelSIKCSG:sendCSGToSIKQueue/sendToSIKQueue' order by creation_time desc)where rownum <= 20");
                while (rs.next())
                {
                     
               
                  String rowmsg;
                        rowmsg = rs.getString("MSGID");
                        String userID=rs.getString("USER_ID");
                        String OPERATION=rs.getString("OPERATION");
                        
                        if(userID==null)
                        {
                              Application.showMessage("User ID is Null");
                        }
                        
                        else if(userID.equals("JMS-CSGSIK"))
                        {
                              c.put("sendToSIKQueueFound","true");
                        }
                                 
                       
                                
                if((boolean) c.get("sendToSIKQueueFound").equals("true"))
                {
                     Application.showMessage("WebService Call :: sendToSIKQueueFound is Success[All validation points are Success]");
                }
                else
                {
                      c.put("result", "false");
                      c.report("WebService Call ::sendToSIKQueueFound is Failed with Validation Errors");
                }
                st.close();
                }
              }
              catch (SQLException e)
              {
                  Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                     return;
              }
       }
       
       
       public int orderUpdate_Validate1(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
       {
              Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
              sikLibraryClass sL = new sikLibraryClass();
              ResultSet  rs;
              int callFound=0,v1=0,v2=0,v3=0,v4=0;
              String xmldata1 ="receive_data";
              String xmldata2 ="SEND_DATA";
           
              Application.showMessage("-----------------------------------------------------");
              Application.showMessage("**********orderUpdate_Validate Function************");       
              Application.showMessage("----------------------------------------------------");
              HashMap Operation=sL.findingoperations(input, c);
              c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
              	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
              try
              {
              //  Statement st =sL. dBConnect(input, c);  
              //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"'order by creation_time desc)where rownum <= 20");
            	  rs=sL.reduceThinkTimeSIK(input, c);
                while (rs.next())
                {
                 
                   if(rs.getBlob("receive_data")==null)
                   {
                   
                     Application.showMessage("Your Recieve XML is NULL \n");
                     
                     String senddata =sL.extractXml(rs,xmldata2);
                     Application.showMessage("Your Recieve XML is::\n"+senddata);
                  
                                 
                         
                          
                            // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
                     
                     

                          String ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"corpId\">");
                         Application.showMessage("accountNumber is ::"+ou_AccountNumberid);    
                          if(ou_AccountNumberid== null)
                         {
                           continue;
                         }
                         else if(ou_AccountNumberid.equals(c.getValue("accountNumber")))
                         {
                            
                            //System.out.printf("SEND XML is %s \n",senddata);
                            Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
                            Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
                            callFound=1;
                     }
                     else
                     {
                            continue;
                     }
                     

                          if(callFound==1)
                          {
                    
                                 String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value></header>");
                                Application.showMessage("productType is ::"+ou_productType);
                                
                                 if(ou_productType==null)
                                 {
                                       c.report("Send Xml productType is NULL");
                                       continue;
                                 }
                                 else
                                 {
                                    Application.showMessage("productType Send  is ::"+" "+ou_productType);
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

                                String ou_billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
                                Application.showMessage("billingSystem is ::"+ou_billingSystem); 
                                 
                                if(ou_billingSystem==null)
                                 {
                                       c.report("Send Xml billingSystem is NULL");
                                       continue;
                                 }
                                 else
                                 {
                                    Application.showMessage("BillingSystem from Send Xml is ::"+" "+ou_billingSystem);
                                    if(ou_billingSystem.equals("CSG"))
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
                                else if(ordStatus.equals("INI") || ordStatus.equals("COM"))
                                {
                                    Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                    Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                    v3=1;
                                }
                                else
                                {
                                    c.report("ordStatus at Send Xml not Validated as "+ordStatus);
                                }
                                  
                                
                                 String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
                                 Application.showMessage("ordType is ::"+ordType); 
                                 if(ordType==null)
                                 {
                                       c.report("Send Xml ordType is NULL");
                                       
                                 }
                                else if(ordType.equals("NEW"))
                                  {
                                    Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                    Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
                                    v4=1;
                                  }
                                  else
                                  {
                                    c.report("ordStatus at Send Xml not Validated as "+ordType);
                                  }   
                                 
//                              String accountNumber_ou= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
//                              Application.showMessage("accountNumber is ::"+accountNumber_ou); 
//                              if(accountNumber_ou.equals(c.getValue("accountNumber")))
//                                {
//                                  Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
//                                  Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
//                                  v4=1;
//                                }
//                                else
//                                {
//                                  c.report("accountNumber at Send Xml not Validated as "+accountNumber_ou);
//                                }    
                                 
                                 String order_id= sL.nodeFromKey(senddata,"<value name=\"shipOrderId\">","</value><value name=\"submitDate\">");
                                 Application.showMessage("action is ::"+order_id); 
                                 if(order_id.equals(c.get("pOrderID_sik")))
                                 {
                                     Application.showMessage("Order ID validation is success");
                                       
                                 }
                                
                                

                                
                          break;
                          }
                    }
                }
                
                if(v1*callFound*v2*v3*v4 ==1)
                {
                     Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
                }
                else
                {
                	  OutPut=0;
                      c.put("result", "false");
                      c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
                }
                //st.close();
              }      
              catch (SQLException e)
              {
                  Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                   
              }
              return  OutPut;
       }
       
       
       public int ModifyOrder_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, ParserConfigurationException, SAXException 
       {
              //Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
            sikLibraryClass sL = new sikLibraryClass();
              ResultSet  rs;
              int callFound=0,v1=0, v2 = 0;
              String xmldata1 ="receive_data";
            String xmldata2 ="SEND_DATA";
           
            Application.showMessage("-----------------------------------------------------");
            Application.showMessage("********ModifyOrderRequest_Validate Function******");       
            Application.showMessage("----------------------------------------------------");
            HashMap Operation=sL.findingoperations(input, c);
            c.setValue("OPERATIONVALIDATION",(String) Operation.get("ModifyOrder"));
            	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("ModifyOrder"));  
              try
              {
                   //  Statement st =sL. dBConnect(input, c);  
              //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/ModifyOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
                          String receiveDataTrim=sL.RemoveNameSpaces(recievedata);
                         
                          String sik_PhoneModify= sL.nodeFromKey(senddata,"<sik:Phone>","</sik:Phone><sik:Email>");
                         Application.showMessage("sik_PhoneModify is ::"+sik_PhoneModify); 
                          if(sik_PhoneModify== null)
                         {
                           continue;
                         }
                         else if(sik_PhoneModify.equals(c.getValue("TN")))
                     {
                            Application.showMessage("Recieve XML is::  \n"+ recievedata);
                            Application.showMessage("SEND XML is :: \n"+senddata);
                            //System.out.printf("SEND XML is %s \n",senddata);
                            Application.showMessage("*******Validation Point 1 :: WebServicall-ModifyOrder Call********");
                            Application.showMessage("Validation is Successfull with Input Account Number"+sik_PhoneModify);
                            callFound=1;
                     }
                     else
                     {
                            continue;
                     }
                     
                          if(callFound==1)
                          {
                    
                        	  //-----****Validation for Trackcode*****--------------
                              
                              List<String> retreiveTrackcodes=sl.getRateCodes(input, c);
                                                                                           
                              if(retreiveTrackcodes.size()>0)
                               {
                             	 Application.showMessage("**********Validation Point :: KIT Code - OT0PD***********");
                             	int TrackValue=sL.getTrackingDetails(input, c,senddata);
                                Application.showMessage("TrackValue:::"+TrackValue);
                             	 
                              //String TrackValue=sl.getAttributeName(input, c,senddata, "sik:ExternalOrderData", "Reason");
                              //List<String> retrieveTrackValues=sl.extractValue(TrackValue, input, c);
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
                                 String sik_EmailModify= sL.nodeFromKey(senddata,"</sik:Phone><sik:Email>","</sik:Email></sik:ContactInformation>");
                                Application.showMessage("oft_shoppingAction is ::"+sik_EmailModify);
                                
                                 if(sik_EmailModify==null)
                                 {
                                       c.report("Send Xml FirstName is NULL");
                                       continue;
                                 }
                                 else
                                 {
                                    if(sik_EmailModify.equals(c.getValue("Email")))
                                        {
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-ModifyOrder********");
                                          Application.showMessage("Validation is Successfull with sik_EmailModify::"+" "+sik_EmailModify);
                                          v1=1;
                                        }
                                        else
                                        {
                                          c.report("sik_EmailModify at Send Xml not Validated as "+sik_EmailModify);
                                        }
                                 }        

                                String Modify_errText= sL.nodeFromKey(receiveDataTrim,"<text>","</text></message></messages></serviceFault>");
                                Application.showMessage("Modify_errText is ::"+Modify_errText); 
                                 
                               
                             
                                if(Modify_errText==null)
                                 {
                                       c.report(" Modify_errText is NULL");
                                        continue;
                                 }
                                 else
                                 {
                                    if(Modify_errText.equalsIgnoreCase("Order cannot be modified.: Cannot Modify Overnight Orders After 1 Minutes"))
                                        {
                                          Application.showMessage("*******Validation Point 5 :: WebServicall-Modify_errText********");
                                          Application.showMessage("Need to Check GetOrderDetails::"+" "+Modify_errText);
                                          v2=1;
                                          c.put("NeedGetOrderCheck", "true");
                                        }
                                        else if (Modify_errText.equalsIgnoreCase("Order cannot be modified.: Sent To 3PL"))
                                        {
                                        	
                                        	Application.showMessage("*******Validation Point 5 :: WebServicall-Modify_errText********");
                                            Application.showMessage("Need to Check GetOrderDetails::"+" "+Modify_errText);
                                            v2=1;
                                            c.put("NeedGetOrderCheck", "true");
                                         
                                        }
                                        else if (Modify_errText.equalsIgnoreCase("Order cannot be modified.: Order Cancelled"))
                                        {
                                        	
                                        	Application.showMessage("*******Validation Point 5 :: WebServicall-Modify_errText********");
                                            Application.showMessage("Need to Check GetOrderDetails::"+" "+Modify_errText);
                                            v2=1;
                                            c.put("NeedGetOrderCheck", "true");
                                         
                                        }
                                        else
                                        {
                                         Application.showMessage("Modify is within 1 min..");
                                        }
                                 }
                                        

                                
                          break;
                          }
                    }
                }
                
                if(v1*v2*callFound*trackcode ==1)
                {
                     Application.showMessage("WebService Call :: ModifyOrder is Success[All validation points are Success]");
                }
                else
                {
                	  OutPut=0;
                      c.put("result", "false");
                      c.report("WebService Call ::ModifyOrder is Failed with Validation Errors");
                }
              //  st.close();
              }      
              catch (SQLException e)
              {
                  Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                   
              }
              return  OutPut;
       }
       
       
       
       public int getOrderDetails_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
       {
            Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
            sikLibraryClass sL = new sikLibraryClass();
            ResultSet  rs;
            int callFound=0,v1=0, v2 = 0;
            String xmldata1 ="receive_data";
            String xmldata2 ="SEND_DATA";
           
            Application.showMessage("-----------------------------------------------------");
            Application.showMessage("********ModifyOrderRequest_Validate Function******");       
            Application.showMessage("----------------------------------------------------");
                
              try
              {
                 Statement st =sL. dBConnect(input, c);  
                 rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/GetOrderDetails' order by creation_time desc)where rownum <= 20");
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
                         
                          String GetOrder_OrderID= sL.nodeFromKey(senddata,"<sik:Request><sik:OrderID>","</sik:OrderID></sik:Request></sik:GetOrderDetails>");
                         Application.showMessage("sik_PhoneModify is ::"+GetOrder_OrderID); 
                          if(GetOrder_OrderID== null)
                         {
                           continue;
                         }
                         else if(GetOrder_OrderID.equals(c.get("MOrderID")))
                     {
                            Application.showMessage("Recieve XML is::  \n"+ recievedata);
                            Application.showMessage("SEND XML is :: \n"+senddata);
                            //System.out.printf("SEND XML is %s \n",senddata);
                            Application.showMessage("*******Validation Point 1 :: WebServicall-getOrder Call********");
                            Application.showMessage("Validation is Successfull with GetOrder_OrderID"+GetOrder_OrderID);
                            callFound=1;
                     }
                     else
                     {
                            continue;
                     }
                     
                          if(callFound==1)
                          {
                    
                                 String GetOrder_Add= sL.nodeFromKey(recievedata,"<ShippingAddress><Address1>","</Address1>");
                                Application.showMessage("GetOrder_Addrerss is ::"+GetOrder_Add);
                                
                                 if(GetOrder_Add==null)
                                 {
                                       c.report("Send Xml GetOrder_Address is NULL");
                                       continue;
                                 }
                                 else
                                 {
                                    if(GetOrder_Add.trim().equals(c.getValue("Email")))  // To be fixed
                                        {
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-GetOrder_Add********");
                                          Application.showMessage("Validation is Successfull with GetOrder_Add::"+" "+GetOrder_Add);
                                          v1=1;
                                        }
                                        else
                                        {
                                          c.report("GetOrder_Add at Send Xml not Validated as "+GetOrder_Add);
                                        }
                                 }        

                                String GetOrder_fname= sL.nodeFromKey(recievedata,"<FirstName>","</FirstName>");
                                Application.showMessage("GetOrder_fname is ::"+GetOrder_fname);
                                
                                 String GetOrder_lname= sL.nodeFromKey(recievedata,"<LastName>","</LastName>");
                                 Application.showMessage("GetOrder_lname is ::"+GetOrder_lname);
                                
                                 String GetOrder_ph= sL.nodeFromKey(recievedata,"<Phone>","</Phone>");
                                 Application.showMessage("GetOrder_ph is ::"+GetOrder_ph);
                                
                                 String GetOrder_email= sL.nodeFromKey(recievedata,"<Email>","</Email>");
                                 Application.showMessage("GetOrder_email is ::"+GetOrder_email);
                                
                                 String GetOrder_Orderdate= sL.nodeFromKey(recievedata,"<OrderDate>","</OrderDate>");
                                 Application.showMessage("GetOrder_Orderdate is ::"+GetOrder_Orderdate);
                                        

                                
                          break;
                          }
                    }
                }
                
                if(v1*v2*callFound ==1)
                {
                     Application.showMessage("WebService Call :: ModifyOrder is Success[All validation points are Success]");
                }
                else
                {
                	  OutPut=0;
                      c.put("result", "false");
                      c.report("WebService Call ::ModifyOrder is Failed with Validation Errors");
                }
                st.close();
              }      
              catch (SQLException e)
              {
                  Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                     
              }
              return  OutPut;
       }

 /*
  * 
  *           Newly fixed calls
  *       
  */
   
       
       
       public int submitOrder_Validate1(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
       {
            //  Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
              sikLibraryClass sL = new sikLibraryClass();
              ResultSet  rs;
              int callFound=0,v1=0,v2=0,v3=0,v4=0;
              String xmldata1 ="receive_data";
              String xmldata2 ="SEND_DATA";
              
              sL.printStart(input, c, "SUBMIT ORDER VALIDATION");
              HashMap Operation=sL.findingoperations(input, c);
              c.setValue("OPERATIONVALIDATION",(String) Operation.get("SubmitOrder"));
              	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("SubmitOrder"));
              try
              {
                  //   Statement st =sL. dBConnect(input, c);  
                 //    rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
            	  rs=sL.reduceThinkTimeSIK(input, c);
                     while (rs.next())
                     {
                                
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
                                 
                	   String recieveData =sL.extractXml(rs,xmldata1);   	            	   
   	                   String recieve_wf= sL.RemoveNameSpaces(recieveData);
   	                   Application.showMessage("Wellformed sendXML::"+recieve_wf);
   	                   
   	                   String Senddata =sL.extractXml(rs,xmldata2);
	      	           String Senddata_wf= sL.RemoveNameSpaces(Senddata);
	      	           Application.showMessage("Wellformed sendXML::"+Senddata_wf);
                                       
                       String sik_AccountNumber= sL.nodeFromKey(Senddata_wf,"<AccountNumber>","<AccountNumber>");
                     
                     //String sik_AccountNumber = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:AccountNumber");
                     
                       Application.showMessage("accountNumber is ::"+sik_AccountNumber); 
                          
                         if(sik_AccountNumber==null)
                         {
                          continue;     
                         }
                         else if(sik_AccountNumber.equals(c.getValue("accountNumber")))
                         {
                           
                            Application.showMessage("*******Validation Point 1 :: WebServicall-processHomeSecurityInfo_Validatet Call********");
                            Application.showMessage("Validation is Successfull with Input Account Number"+sik_AccountNumber);
                            callFound=1;
                            v1=1;
                         }
                         else
                         {
                            continue;
                         }
                     
                         if(callFound==1)
                         {
                                 String cct_code= sL.nodeFromKey(recieveData,"<cct:message><cct:code>","</cct:code><cct:text>");
                                 String cct_text= sL.nodeFromKey(recieveData,"</cct:code><cct:text>","</cct:text></cct:message>");
                                 Application.showMessage("MOdify Text is :: "+cct_text);
                                 if(cct_code==null)
                                 {
                                              String OrderID_sik= sL.nodeFromKey(recieveData,"<OrderID>","</OrderID>");
                                              Application.showMessage("OrderID is::"+OrderID_sik); 
                                              c.put("pOrderID_sik", OrderID_sik);  
                                              c.setValue("OrderIDToCancel", OrderID_sik);
                                 }
                                 else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-213"))
                                 {
                                              c.put("MFlag","1");
                                      
                                              String orderid_modify= sL.nodeFromKey(recieveData,"</code><text>Duplicate Order - Single Not Exact Match: Duplicate order detected; matched order #","</text></message></messages");
                                              Application.showMessage("Modify Order ID found is :: "+orderid_modify);
                                              c.put("MOrderID", orderid_modify);
                                 }
                                 else
                                 {
                                      Application.showMessage(" Failure for Submit..!");
                                      break;
                                 }

                                String OrderType=sL.GetValueByXPath(Senddata_wf, "/SubmitOrder/Request/ExternalOrderData/@OrderType");
             	            	Application.showMessage("OrderType  is ::"+OrderType);
             	            	v2=sL.validationPointIgnoreCase(OrderType, "INSTALL", input, c);
             	            	
             	            	String ShipmentPriority=sL.GetValueByXPath(Senddata_wf, ".//ShipmentPriority");
             	            	Application.showMessage("ShipmentPriority  is ::"+ShipmentPriority);
             	            	v3=sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	             	            	
             	            	String AccountNumber=sL.GetValueByXPath(Senddata_wf, ".//AccountNumber");
             	            	Application.showMessage("AccountNumber  is ::"+AccountNumber);
             	            	sL.validationPointIgnoreCase(AccountNumber, c.getValue("accountNumber"), input, c);
             	            	            	            	
             	            	String FirstName=sL.GetValueByXPath(Senddata_wf, ".//FirstName");
             	            	Application.showMessage("FirstName  is ::"+FirstName);
             	            	v4=sL.validationPointIgnoreCase(FirstName, c.getValue("Fname").trim(), input, c);
             	            	
             	            	
             	            	String LastName=sL.GetValueByXPath(Senddata_wf, ".//LastName");
             	            	Application.showMessage("LastName  is ::"+LastName);
             	            	sL.validationPointIgnoreCase(LastName, c.getValue("Lname").trim(), input, c);
             	            	             	            	
             	            	String Phone=sL.GetValueByXPath(Senddata_wf, ".//Phone");
             	            	Application.showMessage("Phone  is ::"+Phone);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	String OrderIDSIK=sL.GetValueByXPath(recieve_wf, ".//OrderID");
            	            	Application.showMessage("OrderID  is ::"+OrderIDSIK);
            	            	c.setValue("OrderIDToCancel", OrderIDSIK);
             	            	
             	            	String Email=sL.GetValueByXPath(Senddata_wf, ".//Email");
             	            	Application.showMessage("Email  is ::"+Email);
             	            	sL.validationPointIgnoreCase(Email, c.getValue("Email").trim(), input, c);
             	            	
             	                    	            	
             	            	
             	            	/*String Email=sL.GetValueByXPath(Senddata_wf, ".//Email");
             	            	Application.showMessage("Email  is ::"+Email);
             	            	validationPointIgnoreCase(Email, "INSTALL");
             	            	
             	            	String Email=sL.GetValueByXPath(Senddata_wf, ".//Email");
             	            	Application.showMessage("Email  is ::"+Email);
             	            	validationPointIgnoreCase(Email, "INSTALL");*/
             	            	
             	            	
             	            	String Address1=sL.GetValueByXPath(Senddata_wf, ".//Address1");
             	            	Application.showMessage("Address1  is ::"+Address1);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String City=sL.GetValueByXPath(Senddata_wf, ".//City");
             	            	Application.showMessage("City  is ::"+City);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String State=sL.GetValueByXPath(Senddata_wf, ".//State");
             	            	Application.showMessage("State  is ::"+State);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String Zip=sL.GetValueByXPath(Senddata_wf, ".//Zip");
             	            	Application.showMessage("Zip  is ::"+Zip);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String IsAlternateAddress=sL.GetValueByXPath(Senddata_wf, ".//IsAlternateAddress");
             	            	Application.showMessage("IsAlternateAddress  is ::"+IsAlternateAddress);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String DNCSIPAddress=sL.GetValueByXPath(Senddata_wf, ".//DNCSIPAddress");
             	            	Application.showMessage("DNCSIPAddress  is ::"+DNCSIPAddress);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String HeadEndVendor=sL.GetValueByXPath(Senddata_wf, ".//HeadEndVendor");
             	            	Application.showMessage("HeadEndVendor  is ::"+HeadEndVendor);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String E911_acceptance=sL.GetValueByXPath(Senddata_wf, ".//E911_acceptance");
             	            	Application.showMessage("E911_acceptance  is ::"+E911_acceptance);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String AgentID=sL.GetValueByXPath(Senddata_wf, ".//AgentID");
             	            	Application.showMessage("AgentID  is ::"+AgentID);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String WorkOrderID=sL.GetValueByXPath(Senddata_wf, ".//WorkOrderID");
             	            	Application.showMessage("WorkOrderID  is ::"+WorkOrderID);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String DuplicateOverride=sL.GetValueByXPath(Senddata_wf, ".//DuplicateOverride");
             	            	Application.showMessage("DuplicateOverride  is ::"+DuplicateOverride);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String ProductCode1=sL.GetValueByXPath(Senddata_wf, "/SubmitOrder/Request/Products/Product/ProductCode");
             	            	Application.showMessage("ProductCode1  is ::"+ProductCode1);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
             	            	
             	            	
             	            	String ProductCode2=sL.GetValueByXPath(Senddata_wf, "/SubmitOrder/Request/Products/Product[2]/ProductCode");
             	            	Application.showMessage("ProductCode2  is ::"+ProductCode2);
             	            	//sL.validationPointIgnoreCase(ShipmentPriority, "STANDARD", input, c);
   
                                c.put("psik_WorkOrderID", WorkOrderID);  
                                                      
                                 
                                
                                               
                                 
                          break;
                          }
                    }
                }
                
                if(v1*callFound*v2*v3*v4 ==1)
                {
                     Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
                }
                else
                {
                	  OutPut=0;
                      c.put("result", "false");
                      c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
                }
               // st.close();
                sL.printEnd(input, c, "SUBMIT ORDER VALIDATION");
              }      
              catch (SQLException e)
              {
                  Application.showMessage("Connection Failed! Check output console");
                     e.printStackTrace();
                    
              }
              return  OutPut;
       }
       
       
       public void orderUpdateNegative_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
       {
              
              ResultSet  rs;
              String Bid=c.getValue("BaseMsgid");
              long Baseid=Long.parseLong(Bid);
              

              Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
            sikLibraryClass sL = new sikLibraryClass();
              
              int callFound=0,v1=0,v2=0,v3=0;
              String xmldata1 ="receive_data";
            String xmldata2 ="SEND_DATA";
           
            Application.showMessage("-----------------------------------------------------");
            Application.showMessage("**********orderUpdate_Validate Function************");       
            Application.showMessage("----------------------------------------------------");
            HashMap Operation=sL.findingoperations(input, c);
            c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
            	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));
              try
              {
                   //  Statement st =sL. dBConnect(input, c);  
               // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
            	  rs=sL.reduceThinkTimeSIK(input, c);
                while (rs.next())
                {
                     
               
                      long rowmsg;
                      rowmsg = Long.parseLong(rs.getString(1));
                      Application.showMessage("The Base msgid is ::"+Baseid);
                      Application.showMessage("Current Row msgid is ::"+rowmsg);
                                 
                     if(Baseid==rowmsg)break;

                   if(rs.getBlob("receive_data")==null)
                   {
                   
                     Application.showMessage("Your Recieve XML is NULL \n");
                     
                     String senddata =sL.extractXml(rs,xmldata2);
                     Application.showMessage("Your Recieve XML is::\n"+senddata);
                  
                                 
                         
                          
                            // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
                     
                     

                          String ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
                         Application.showMessage("accountNumber is ::"+ou_AccountNumberid);    
                          if(ou_AccountNumberid== null)
                         {
                           continue;
                         }
                         else if(ou_AccountNumberid.equals(c.getValue("accountNumber")))
                     {
                            
                            //System.out.printf("SEND XML is %s \n",senddata);
                            Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
                            Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
                            callFound=1;
                            v1=1;
                     }
                     else
                     {
                            continue;
                     }
                     

                          if(callFound==1)
                          {
                    
                                 String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"customerType\">");
                                Application.showMessage("productType is ::"+ou_productType);
                                
                                 if(ou_productType==null)
                                 {
                                       c.report("Send Xml productType is NULL");
                                       continue;
                                 }
                                 else
                                 {
                                    Application.showMessage("productType Send  is ::"+" "+ou_productType);
                                    if(ou_productType.equalsIgnoreCase("SIK"))
                                        {
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
                                          Application.showMessage("Validation is Successfull with ou_productType::"+" "+ou_productType);
                                          v2=1;
                                        }
                                        else
                                        {
                                          c.report("ou_productType at Send Xml not Validated as "+ou_productType);
                                        }
                                 }        



                                String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"errorText\">");
                                Application.showMessage("customerType is ::"+ou_customerType); 
                                 if(ou_customerType==null)
                                 {
                                       c.report(" ou_customerType is NULL");
                                       
                                 }
                                
                                
                                 String senddata_wf = sL.RemoveNameSpaces(senddata);
                                Application.showMessage("Well formed XML is "+senddata_wf);
                               // String ordType= sL.GetValueByXPath(senddata_wf, "//comRequest/header/value[6]");
                               String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
                               // Application.showMessage("ordType is ::"+ordType); 
                                Application.showMessage("ordType is ::"+ordType);
                                 if(ordType==null)
                                 {
                                       c.report("Send Xml ordType is NULL");
                                       
                                 }
                                else if(ordType.equals("ERR") ||ordType.equals("NEW") )
                                  {
                                    Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                    Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
                                    v3=1;
                                  }
                                  else
                                  {
                                    c.report("orderType at Send Xml not Validated as "+ordType);
                                  }   
                                 
                                 String details= sL.nodeFromKey(senddata,"<value name=\"details\">","</value><value name=\"errorText\">");
                                Application.showMessage("Details  is ::"+details); 
                        
                                
                                // String errCode= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[3]");

                                String errCode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"details\">");
                                Application.showMessage("Error Code is ::"+errCode); 
                                 c.put("ErrorCodeOu",errCode);
                              
                                // String errmsg= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[4]");
                                String errmsg= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorSource\">");
                                Application.showMessage("Error Message  is ::"+errmsg); 
                                 c.put("ErrorTextOu",errmsg);
//                              //String errMade=KittyperRateCodeTableValidation(input, c);
//                              if(errMade.equalsIgnoreCase(errmsg))
//                              {
//                                Application.showMessage("Error Message got validated with MapBase value as:"+errMade);
//                            
//                              }
//                              else
//                              {
//                                c.report("Error Message failed::"+errMade);
//                              }
                              
                                // String errsrc= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[5]");
                                String errsrc= sL.nodeFromKey(senddata,"<value name=\"errorSource\">","</value><value name=\"orderType\">");
                                Application.showMessage("Error Source  is ::"+errsrc); 
                                 
                                 c.put("ErrorSourceOu",errsrc);
                               
                                 CommonDBValidate(input, c);
                               

                                
                          break;
                          }
                    }
                }
                
                if(v1*callFound*v2*v3==1)
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
              public void orderUpdateNegative_SubmitOrderValidate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
              {
                     
                     ResultSet  rs;
                     String Bid=c.getValue("BaseMsgid");
                     long Baseid=Long.parseLong(Bid);
                     

                     Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
                   sikLibraryClass sL = new sikLibraryClass();
                     
                     int callFound=0,v1=0,v2=0,v3=0;
                     String xmldata1 ="receive_data";
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
                      // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
                    	 rs=sL.reduceThinkTimeSIK(input, c);
                       while (rs.next())
                       {
                            
                      
                             long rowmsg;
                             rowmsg = Long.parseLong(rs.getString(1));
                             Application.showMessage("The Base msgid is ::"+Baseid);
                             Application.showMessage("Current Row msgid is ::"+rowmsg);
                                        
                           

                          if(rs.getBlob("receive_data")==null)
                          {
                          
                            Application.showMessage("Your Recieve XML is NULL \n");
                            
                            String senddata =sL.extractXml(rs,xmldata2);
                            Application.showMessage("Your Recieve XML is::\n"+senddata);
                         
                                        
                                
                                 
                                   // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
                            
                            

                                 String ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
                                Application.showMessage("accountNumber is ::"+ou_AccountNumberid);    
                                Application.showMessage("accountNumber from xl sheet ::"+c.getValue("accountNumber"));
                                 if(ou_AccountNumberid== null)
                                {
                                  continue;
                                }
                                else if(ou_AccountNumberid.equals(c.getValue("accountNumber")))
                            {
                                   
                                   //System.out.printf("SEND XML is %s \n",senddata);
                                   Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
                                   Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
                                   callFound=1;
                                   v1=1;
                            }
                            else
                            {
                                   continue;
                            }
                            

                                 if(callFound==1)
                                 {
                           
                                        String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"customerType\">");
                                       Application.showMessage("productType is ::"+ou_productType);
                                       
                                        if(ou_productType==null)
                                        {
                                              c.report("Send Xml productType is NULL");
                                              continue;
                                        }
                                        else
                                        {
                                           Application.showMessage("productType Send  is ::"+" "+ou_productType);
                                           if(ou_productType.equalsIgnoreCase("SIK"))
                                               {
                                                 Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
                                                 Application.showMessage("Validation is Successfull with ou_productType::"+" "+ou_productType);
                                                 v2=1;
                                               }
                                               else
                                               {
                                                 c.report("ou_productType at Send Xml not Validated as "+ou_productType);
                                               }
                                        }        



                                       String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"errorText\">");
                                       Application.showMessage("customerType is ::"+ou_customerType); 
                                        if(ou_customerType==null)
                                        {
                                              c.report(" ou_customerType is NULL");
                                              
                                        }
                                       
                                       
                                        String senddata_wf = sL.RemoveNameSpaces(senddata);
                                       Application.showMessage("Well formed XML is "+senddata_wf);
                                      // String ordType= sL.GetValueByXPath(senddata_wf, "//comRequest/header/value[6]");
                                      String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"billingOrderId\">");
                                      // Application.showMessage("ordType is ::"+ordType); 
                                       Application.showMessage("ordType is ::"+ordType);
                                        if(ordType==null)
                                        {
                                              c.report("Send Xml ordType is NULL");
                                              
                                        }
                                       else if(ordType.equals("ERR") ||ordType.equals("NEW") )
                                         {
                                           Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                           Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
                                           v3=1;
                                         }
                                         else
                                         {
                                           c.report("orderType at Send Xml not Validated as "+ordType);
                                         }   
                                        
                                        /*String details= sL.nodeFromKey(senddata,"<value name=\"details\">","</value><value name=\"errorText\">");
                                       Application.showMessage("Details  is ::"+details);*/ 
                               
                                       
                                       // String errCode= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[3]");

                                       String errCode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"details\">");
                                       Application.showMessage("Error Code is ::"+errCode); 
                                        c.put("ErrorCodeOu",errCode);
                                     
                                       // String errmsg= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[4]");
                                       String errmsg= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorCode\">");
                                       Application.showMessage("Error Message  is ::"+errmsg); 
                                        c.put("ErrorTextOu",errmsg);
//                                     //String errMade=KittyperRateCodeTableValidation(input, c);
//                                     if(errMade.equalsIgnoreCase(errmsg))
//                                     {
//                                       Application.showMessage("Error Message got validated with MapBase value as:"+errMade);
//                                   
//                                     }
//                                     else
//                                     {
//                                       c.report("Error Message failed::"+errMade);
//                                     }
                                     
                                       // String errsrc= sL.GetValueByXPath(senddata_wf, "//comRequest/wlRequest/worklist/value[5]");
                                       /*String errsrc= sL.nodeFromKey(senddata,"<value name=\"errorSource\">","</value><value name=\"orderType\">");
                                       Application.showMessage("Error Source  is ::"+errsrc); 
                                        
                                        c.put("ErrorSourceOu",errsrc);*/
                                      
                                        CommonDBValidate(input, c);
                                      

                                       
                                 break;
                                 }
                           }
                       }
                       
                       if(v1*callFound*v2*v3==1)
                       {
                            Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
                       }
                       else
                       {
                             c.put("result", "false");
                             c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
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
       
       
       public void orderUpdateMissingInfo_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException, XPathExpressionException, SAXException 
       {
              Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
            sikLibraryClass sL = new sikLibraryClass();
              ResultSet  rs;
              int callFound=0,v1=0,v2=0,v3=0;
              String xmldata1 ="receive_data";
            String xmldata2 ="SEND_DATA";
           
               String Bid=c.getValue("BaseMsgid");
              long Baseid=Long.parseLong(Bid);
            Application.showMessage("-----------------------------------------------------");
            Application.showMessage("**********orderUpdate_Validate Function************");       
            Application.showMessage("----------------------------------------------------");
            HashMap Operation=sL.findingoperations(input, c);
            c.setValue("OPERATIONVALIDATION",(String) Operation.get("orderUpdate"));
            	     Application.showMessage("OPERATIONValidation is "+(String) Operation.get("orderUpdate"));  
              try
              {
                  //   Statement st =sL. dBConnect(input, c);  
               // rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' order by creation_time desc)where rownum <= 20");
            	  rs=sL.reduceThinkTimeSIK(input, c);
                while (rs.next())
                {
                     
               
                	String rowmsg;
                    rowmsg = rs.getString(1);
                             
                   
                      Application.showMessage("The Base msgid is ::"+c.getValue("BaseMsgid"));
                      Application.showMessage("Current Row msgid is ::"+rowmsg);
                                 
                      if(rowmsg.equals(c.getValue("BaseMsgid")))break;
                   if(rs.getBlob("receive_data")==null)
                   {
                   
                     Application.showMessage("Your Recieve XML is NULL \n");
                     
                     String senddata =sL.extractXml(rs,xmldata2);
                     Application.showMessage("Your Recieve XML is::\n"+senddata);
                  
                                 
                         
                          
                            // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
                     
                     

                          String ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
                         Application.showMessage("accountNumber is ::"+ou_AccountNumberid);    
                          if(ou_AccountNumberid== null)
                         {
                           continue;
                         }
                         else if(ou_AccountNumberid.equals(c.getValue("accountNumber")))
                     {
                            
                            //System.out.printf("SEND XML is %s \n",senddata);
                            Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
                            Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
                            callFound=1;
                            v1=1;
                     }
                     else
                     {
                            continue;
                     }
                     

                          if(callFound==1)
                          {
                    
                                 String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value><value name=\"customerType\">");
                                Application.showMessage("productType is ::"+ou_productType);
                                
                                 if(ou_productType==null)
                                 {
                                       c.report("Send Xml productType is NULL");
                                       continue;
                                 }
                                 else
                                 {
                                    Application.showMessage("productType Send  is ::"+" "+ou_productType);
                                    if(ou_productType.equalsIgnoreCase("SIK"))
                                        {
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-Order Update********");
                                          Application.showMessage("Validation is Successfull with ou_productType::"+" "+ou_productType);
                                          v2=1;
                                        }
                                        else
                                        {
                                          c.report("ou_productType at Send Xml not Validated as "+ou_productType);
                                        }
                                 }        



                                String ou_customerType= sL.nodeFromKey(senddata,"<value name=\"customerType\">","</value><value name=\"errorText\">");
                                Application.showMessage("customerType is ::"+ou_customerType); 
                                 if(ou_customerType==null)
                                 {
                                       c.report(" ou_customerType is NULL");
                                       
                                 }
                                
                                
                                 String senddata_wf = sL.RemoveNameSpaces(senddata);
                                Application.showMessage("Well formed XML is "+senddata_wf);
                            
                                 String ordType= sL.GetValueByXPath(senddata_wf, "/comRequest/header/value[6]");
                               
                                // String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
                                Application.showMessage("ordType is ::"+ordType); 
                                 if(ordType==null)
                                 {
                                       c.report("Send Xml ordType is NULL");
                                       
                                 }
                                else if(ordType.equals("NEW"))
                                  {
                                    Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                    Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
                                    v3=1;
                                  }
                                  else
                                  {
                                    c.report("orderType at Send Xml not Validated as "+ordType);
                                  }   
                                 
                                 String details= sL.nodeFromKey(senddata,"<value name=\"details\">","</value></worklist>");
                                Application.showMessage("Details  is ::"+details); 
                        
                                
                                 String errCode= sL.GetValueByXPath(senddata_wf, "/comRequest/wlRequest/worklist/value[3]");

                               // String errCode= sL.nodeFromKey(senddata,"<value name=\"errorCode\">","</value><value name=\"details\">");
                                Application.showMessage("Error Code is ::"+errCode); 
                                 c.put("ErrorCodeOu",errCode);
                              
                                //String errmsg= sL.GetValueByXPath(senddata_wf, "//cod:comRequest/header/value[3]");
                               String errmsg= sL.nodeFromKey(senddata,"<value name=\"errorText\">","</value><value name=\"errorCode\">");
                                Application.showMessage("Error Message  is ::"+errmsg); 
                                 c.put("ErrorTextOu",errmsg);
                              
                                 CommonDBValidate(input, c);
                               

                                
                          break;
                          }
                    }
                }
                
                if(v1*callFound*v2*v3==1)
                {
                     Application.showMessage("WebService Call :: orderUpdate_Validate is Success[All validation points are Success]");
                }
                else
                {
                      c.put("result", "false");
                      c.report("WebService Call ::orderUpdate_Validate is Failed with Validation Errors");
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

       
       
       
       
       
       public void CommonDBValidate(Object input,ScriptingContext c) throws ClassNotFoundException, IOException, SQLException
       {
              sikLibraryClass  lC= new sikLibraryClass ();
              ResultSet rs1;
                
              
              try
              {
                     
            	  
                     Statement st1 =lC.dBConnectCommon(input, c);
                     
                     
                //Application.showMessage("Account Number is "+c.getValue("RTPNormalScenariosdS","RTP_InputParams: XML_AccNumber"));
                String Acc= c.getValue("SwivelCSG", "sik_SwivelCSG: AccountId_ITEMLIST");
                Application.showMessage("AccountNumber ::"+Acc);
                
                     rs1 = st1.executeQuery("select * from(select * from cwpworklist where ACCOUNTNUMBER ='"+Acc+"'order by creation_date desc)where rownum < 2");
                     if(rs1==null)
                     {
                           Application.showMessage("Null Record set found in WorKlist DB for AccountNumber'"+Acc);
                     }
             
                while(rs1.next())
          {
                String errorCode= rs1.getString("ERRORCODE");
            // String errorSource=rs1.getString("ERRORSOURCE");
            // Application.showMessage("Error Source from common is" +errorSource);
             String errorText=rs1.getString("ERRORTEXT");
             String PARTICIPANTOPERATION=rs1.getString("PARTICIPANTOPERATION");
            // String BILLINGSYSTEM=rs1.getString("BILLINGSYSTEM");
             String SERVICETYPE=rs1.getString("SERVICETYPE");
             String ORDER_ID=rs1.getString("ORDER_ID");
                Application.showMessage("Error Code is ::"+errorCode);
               // Application.showMessage("errorSource is ::"+errorSource);
                Application.showMessage("PARTICIPANTOPERATION is ::"+PARTICIPANTOPERATION);
                Application.showMessage("errorText is ::"+errorText);
               //Application.showMessage("BILLINGSYSTEM is ::"+BILLINGSYSTEM);
                //Application.showMessage("SERVICETYPE is ::"+SERVICETYPE);
               // Application.showMessage("ORDER_ID is ::"+ORDER_ID);
                 //String errMade=KittyperRateCodeTableValidation(input, c);
                  
                Application.showMessage("****** Validating Common Data Base worklisting********");
                if(errorText.equals(c.get("ErrorTextOu")))
                {
                      Application.showMessage("Error text from order update is validated successfully with error text from common DB");
                }
                else
                {
                      Application.showMessage("ERROR TEXT IS NOT VALIDATED");
                }
                
                if(errorCode.equals(c.get("ErrorCodeOu")))
                {
                      Application.showMessage("Error code from order update is validated successfully with error code from common DB"); 
                }
                else
                {
                      Application.showMessage("ERROR CODE IS NOT VALIDATED");
                }
                
//              if(errorSource.equals(c.get("ErrorSourceOu")))
//              {
//                    Application.showMessage("Error SOURCE from order update is validated successfully with error Source from common DB"); 
//              }
//              else
//              {
//                    Application.showMessage("ERROR SOURCE IS NOT VALIDATED");
//              }
//              
                
          }
                
                
                
                st1.close();
              }
              catch (SQLException e)
              {
                  System.out.println("Connection Failed! Check output console");
                     e.printStackTrace();
                     return;
              }
              
              
       }
       

public void QueryLocation_Validate(Object input, ScriptingContext c) throws InterruptedException, ClassNotFoundException, IOException 
       {
             // Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
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
             //   rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'ls:LocationServicePort/queryLocation' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 50");
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
                         else if(GT_loc.equals(c.getValue("locationID")))
                     {
                            Application.showMessage("Recieve XML is::  \n"+ recievedata);
                            Application.showMessage("SEND XML is :: \n"+senddata);
                            //System.out.printf("SEND XML is %s \n",senddata);
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
                    
                                 String typ_customerType= sL.nodeFromKey(recievedata,"<typ:customerType>","</typ:customerType><typ:locationStatus>");
                                Application.showMessage("typ_customerType is ::"+typ_customerType);
                                
                                
                                 
                                 String LegacyLocationID_GT= sL.nodeFromKey(recievedata,"<typ:legacyLocationIDType><typ:LegacyLocationID>","</typ:LegacyLocationID><typ:LegacyLocationIDSource>");
                                Application.showMessage("LegacyLocationID_GT is ::"+LegacyLocationID_GT); 
                                 
                                 String headendNetworkAddress_GT= sL.nodeFromKey(recievedata,"<typ:headendNetworkAddress>","</typ:headendNetworkAddress>");
                                Application.showMessage("headendNetworkAddress_GT is ::"+headendNetworkAddress_GT);                              
                                 c.put("GL_headendNetworkAddress", headendNetworkAddress_GT);
                                
                                    //-------------------------------------------------------------------//
                                      //
                                      //                                   Fetching Get Location Address.
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
                                
                               
                                 
                             
                                   String FranchiseTaxArea= sL.nodeFromKey(recievedata,"<typ:Agent>","</typ:Agent>");
                                       Application.showMessage("FranchiseTaxArea is ::"+FranchiseTaxArea); 
                                        c.put("GL_ftaAgent", FranchiseTaxArea);
                                
                                 
                                 
                                   
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
              //  st.close();
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
             // Thread.sleep(4000); // Think time in JVM [waits for 10 secs here]
            sikLibraryClass sL = new sikLibraryClass();
              ResultSet  rs;
              int callFound=0,v1=0,v2=0,v3=0,v4=0;
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
              //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'sik:OrderSoap/SubmitOrder' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
                                       
                          String sik_AccountNumber= sL.nodeFromKey(senddata,"<sik:AccountNumber>","</sik:AccountNumber>");
                     
                     //String sik_AccountNumber = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:AccountNumber");
                     
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
                            v1=1;
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
                                 if(cct_code.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                 {
                                       String OrderID_sik= sL.nodeFromKey(recievedata,"<OrderID>","</OrderID>");
                                             Application.showMessage("OrderID is::"+OrderID_sik); 
                                             if(OrderID_sik.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                             {
                                            	 c.setValue("CancelReq", "NO");
                                             }
                                             else
                                             {
                                              c.put("pOrderID_sik", OrderID_sik);
                                              c.setValue("OrderIDToCancel",OrderID_sik );
                                              c.setValue("CancelReq", "YES");
                                             }
                                 }
                                 else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-213")) 
                                 {
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
                                 else if (cct_code.equalsIgnoreCase("Order-SubmitOrder-211"))
                                 {
                                	
        		                	   String receiveTrim=sL.RemoveNameSpaces(recievedata);
        		                	   
        		                	   if(receiveTrim.equalsIgnoreCase("Duplicate Order - Multiple Match: Can only order 4 of GD_VID-STD"));
        		                	   {
        		                		   Application.showMessage("Submit Order Validated with Duplicate Order - Multiple Match: Can only order 4 of GD_VID-STD");
        		                	   }
                                 }
                                 else
                                 {
                                	  c.setValue("CancelReq", "NO");
                                      Application.showMessage(" Failure for Submit..!");
                                      break;
                                 }
  //-----****Validation for Trackcode*****--------------
                                 
                                 List<String> retreiveTrackcodes=sL.getRateCodes(input, c);
                                                                                              
                                 if(retreiveTrackcodes.size()>0)
                                  {
                                	 Application.showMessage("**********Validation Point :: KIT Code - OT0PD***********");
                                	 int TrackValue=sL.getTrackingDetails(input, c,senddata);
                                     Application.showMessage("TrackValue:::"+TrackValue);
                                     //List<String> retrieveTrackValues=sL.extractValue(TrackValue, input, c);
                                 if(TrackValue==retreiveTrackcodes.size())
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
                                     
                                     //String sik_Email = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:Email");

                                       Application.showMessage("sik_Email is ::"+c.getValue("Email")); 
                                        if(sik_Email==null)
                                       {
                                              c.report(" sik_Email is NULL");
                                              continue;
                                       }
                                       else
                                       {
                                          Application.showMessage("sik_Email from Send Xml  from Submit Order is ::"+" "+sik_Email);
                                          if(sik_Email.trim().equalsIgnoreCase(c.getValue("Email").trim()))
                                               {
                                                 Application.showMessage("*******Validation Point 4 :: WebServicall-Email********");
                                                 Application.showMessage("Validation is Successfull with Email::"+" "+sik_Email);
                                                 v3=1;
                                               }
                                          else if(c.getValue("Email").isEmpty() || c.getValue("Email").equals(null) && sik_Email.equalsIgnoreCase("NO Data Fetched from in-between strings..!Check the strings..!"))
                                          {
                                        	  Application.showMessage("*******Validation Point 4 :: WebServicall-Email********");
                                        	  v3=1;
                                          }
                                         
                                               else 
                                               {
                                                Application.showMessage("Email at Send Xml not Validated as "+sik_Email);
                                                continue;
                                               }
                                       }
                                       
                             //    String sik_FirstName= sL.nodeFromKey(senddata,"<sik:FirstName>","</sik:FirstName>");
                                
                                //String sik_FirstName = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:FirstName");
                               
                               //  Application.showMessage("firstName is ::"+sik_FirstName);
                                
                               /*  if(sik_FirstName==null)
                                 {
                                       c.report("Send Xml FirstName is NULL");
                                       continue;
                                 }*/
                                /* else
                                 {
                                    Application.showMessage("Account Number from Send Xml  from processHomeSecurityInfo_Validate is ::"+" "+sik_FirstName);
                                    if(sik_FirstName.equals(c.getValue("Fname")))
                                        {
                                          Application.showMessage("*******Validation Point 2 :: WebServicall-billingArrangementIDRes_CMSAccountID********");
                                          Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_FirstName);
                                          v2=1;
                                        }
                                        else
                                        {
                                          c.report("FirstName at Send Xml not Validated as "+sik_FirstName);
                                        }
                                 }        

                                String sik_LastName= sL.nodeFromKey(senddata,"<sik:LastName>","</sik:LastName>");
                               //String sik_LastName = sL.GetValueByXPath(recievedata, "/sik:SubmitOrder/sik:Request/sik:CustomerInformation/sik:ContactInformation/sik:LastName");
                                Application.showMessage("lastName is ::"+sik_LastName); 
                                 
                                if(sik_LastName==null)
                                 {
                                       c.report("Send Xml LastName is NULL");
                                       continue;
                                 }
                                 else
                                 {
                                    if(sik_LastName.equals(c.getValue("Lname")))
                                        {
                                          Application.showMessage("*******Validation Point 3 :: WebServicall-lastName_DDS********");
                                          Application.showMessage("Validation is Successfull with FirstName::"+" "+sik_LastName);
                                          v4=1;
                                        }
                                        else
                                        {
                                          c.report("LastName at Send Xml not Validated as "+sik_LastName);
                                        }
                                 }*/

                               
                                 
                                 String sik_ShipmentPriority= sL.nodeFromKey(senddata,"<sik:ShipmentPriority>","</sik:ShipmentPriority>");
                                Application.showMessage("ShipmentPriority is ::"+sik_ShipmentPriority); 
                                 
                                 //String sik_FTA= sL.nodeFromKey(senddata,"<sik:FTA>","</sik:FTA>");
                                //Application.showMessage("sik_FTA is ::"+sik_FTA);
                                
                                 
                                 
                                 
                                 Application.showMessage("SHIP_City from bfc" +c.get("SHIP_City"));

                                //Application.showMessage("FTA from Get Loc" +c.get("GL_ftaAgent"));
                                //Application.showMessage("FTA from Get Loc" +c.get("GL_FranchiseTaxArea"));
                                

                              
                                 String sik_Address1= sL.nodeFromKey(senddata,"<sik:Address1>","</sik:Address1>");
                                Application.showMessage("sik_Address1 is ::"+sik_Address1); 
                                 
                                // String sik_Address2= sL.nodeFromKey(senddata,"<sik:Address2>","</sik:Address2>");
                               //Application.showMessage("sik_Address2 is ::"+sik_Address2); 
                                 
                                 String sik_City= sL.nodeFromKey(senddata,"<sik:City>","</sik:City>");
                                Application.showMessage("sik_City is ::"+sik_City); 
                                 
                                 String sik_State= sL.nodeFromKey(senddata,"<sik:State>","</sik:State>");
                                Application.showMessage("sik_State is ::"+sik_State); 
                                 
                                 String sik_Zip= sL.nodeFromKey(senddata,"<sik:Zip>","</sik:Zip>");
                                Application.showMessage("sik_Zip is ::"+sik_Zip); 
                                 
                                 String sik_IsAlternateAddress= sL.nodeFromKey(senddata,"<sik:IsAlternateAddress>","</sik:IsAlternateAddress>");
                                Application.showMessage("sik_IsAlternateAddress is ::"+sik_IsAlternateAddress); 
                                 
                                 
                                 
                                 String sik_ProductCode= sL.nodeFromKey(senddata,"<sik:ProductCode>","</sik:ProductCode>");
                                Application.showMessage("sik_ProductCode is ::"+sik_ProductCode); 
                                                                                  
                                 String sik_DNCSIPAddress= sL.nodeFromKey(senddata,"<sik:DNCSIPAddress>","</sik:DNCSIPAddress>");
                                Application.showMessage("sik_DNCSIPAddress is ::"+sik_DNCSIPAddress); 
                                                      
                                 String sik_HeadEndVendor= sL.nodeFromKey(senddata,"<sik:HeadEndVendor>","</sik:HeadEndVendor>");
                                Application.showMessage("sik_HeadEndVendor is ::"+sik_HeadEndVendor); 
                                                             
                                 String sik_E911_acceptance= sL.nodeFromKey(senddata,"<sik:E911_acceptance>","</sik:E911_acceptance>");
                                Application.showMessage("sik_E911_acceptance is ::"+sik_E911_acceptance); 
                                           
                                 String sik_AgentID= sL.nodeFromKey(senddata,"<sik:AgentID>","</sik:AgentID>");
                                Application.showMessage("sik_AgentID is ::"+sik_AgentID); 
                                 
                                 String sik_WorkOrderID= sL.nodeFromKey(senddata,"<sik:WorkOrderID>","</sik:WorkOrderID>");
                                Application.showMessage("sik_WorkOrderID is ::"+sik_WorkOrderID); 
                                 c.put("psik_WorkOrderID", sik_WorkOrderID);  
                                                      
                                 
                                
                                               
                                 
                          break;
                          }
                    }
                }
                
                if(v1*callFound*v3 ==1)
                {
                     Application.showMessage("WebService Call :: submitOrder_Validate is Success[All validation points are Success]");
                }
                else
                {
                      c.put("result", "false");
                      c.report("WebService Call ::submitOrder_Validate is Failed with Validation Errors");
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
                   
                 //  Thread.sleep(2000); // Think time in JVM [waits for 10 secs here]
                   sikLibraryClass sL = new sikLibraryClass();
                   ResultSet  rs;
                   int callFound=0,v1=0,v2=0,v3=0,v4=0;
                   String xmldata1 ="receive_data";
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
                     //  rs = st.executeQuery("select * from (select * from cwmessagelog where operation = 'cmb:commonOrderService/orderUpdate' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') > '"+c.get("BaseTime").toString()+"' order by creation_time desc)where rownum <= 20");
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
                         
                                        
                                
                                 
                                   // String ou_AccountNumber= sL.GetValueByXPath(senddata,"//cod:comRequest/header/value[10]");
                            
                            

                                 String ou_AccountNumberid= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"corpId\">");
                                Application.showMessage("accountNumber is ::"+ou_AccountNumberid);    
                                 if(ou_AccountNumberid== null)
                                {
                                  continue;
                                }
                                else if(ou_AccountNumberid.equals(c.getValue("accountNumber")))
                            {
                                   
                                   //System.out.printf("SEND XML is %s \n",senddata);
                                   Application.showMessage("*******Validation Point 1 :: WebServicall-order Update Call********");
                                   Application.showMessage("Validation is Successfull with Input Account Number"+ou_AccountNumberid);
                                   callFound=1;
                            }
                            else
                            {
                                   continue;
                            }
                            
                                // String ou_guid= sL.nodeFromKey(senddata,"<value name=\"guid\">","</value><value name=\"address\">");
                               // Application.showMessage("guid is ::"+ou_guid);           
////                             if(ou_guid.equals(c.getValue("serviceRequestId")))
////                        {
////                               
////                               //System.out.printf("SEND XML is %s \n",senddata);
////                               Application.showMessage("*******Validation Point 1 :: WebServicall-Order Update Call********");
////                               Application.showMessage("Validation is Successfull with Input serviceRequestId"+ou_guid);
////                               callFound=1;
////                        }
//                          else
//                          {
//                                 continue;
//                          }
                                 if(callFound==1)
                                 {
                           
                                        String ou_productType= sL.nodeFromKey(senddata,"<value name=\"productType\">","</value></header>");
                                       Application.showMessage("productType is ::"+ou_productType);
                                       
                                        if(ou_productType==null)
                                        {
                                              c.report("Send Xml productType is NULL");
                                              continue;
                                        }
                                        else
                                        {
                                           Application.showMessage("productType Send  is ::"+" "+ou_productType);
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

                                       String ou_billingSystem= sL.nodeFromKey(senddata,"<value name=\"billingSystem\">","</value><value name=\"active\">");
                                       Application.showMessage("billingSystem is ::"+ou_billingSystem); 
                                        
                                       if(ou_billingSystem==null)
                                        {
                                              c.report("Send Xml billingSystem is NULL");
                                              continue;
                                        }
                                        else
                                        {
                                           Application.showMessage("BillingSystem from Send Xml is ::"+" "+ou_billingSystem);
                                           if(ou_billingSystem.equals("CSG"))
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
                                       else if(ordStatus.equals("INI"))
                                         {
                                           Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                           Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                           v3=1;
                                         }
                                         else if(ordStatus.equals("COM"))
                                         {
                                           Application.showMessage("*******Validation Point 3 :: WebServicall-ordStatus********");
                                           Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordStatus);
                                           v3=1;
                                         }
                                      
                                         else
                                         {
                                           c.report("ordStatus at Send Xml not Validated as "+ordStatus);
                                         }
                                         
                                       
                                        String ordType= sL.nodeFromKey(senddata,"<value name=\"ordType\">","</value><value name=\"ordSource\">");
                                       Application.showMessage("ordType is ::"+ordType); 
                                        if(ordType==null)
                                        {
                                              c.report("Send Xml ordType is NULL");
                                              
                                        }
                                       else if(ordType.equals("NEW"))
                                         {
                                           Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
                                           Application.showMessage("Validation is Successfull with ordStatus::"+" "+ordType);
                                           v4=1;
                                         }
                                         else
                                         {
                                           c.report("ordStatus at Send Xml not Validated as "+ordType);
                                         }   
                                        
//                                     String accountNumber_ou= sL.nodeFromKey(senddata,"<value name=\"accountNumber\">","</value><value name=\"guid\">");
//                                     Application.showMessage("accountNumber is ::"+accountNumber_ou); 
//                                     if(accountNumber_ou.equals(c.getValue("accountNumber")))
//                                       {
//                                         Application.showMessage("*******Validation Point 3 :: WebServicall-ordType********");
//                                         Application.showMessage("Validation is Successfull with accountNumber::"+" "+accountNumber_ou);
//                                         v4=1;
//                                       }
//                                       else
//                                       {
//                                         c.report("accountNumber at Send Xml not Validated as "+accountNumber_ou);
//                                       }    
                                        
                                        String order_id= sL.nodeFromKey(senddata,"<value name=\"shipOrderId\">","</value><value name=\"submitDate\">");
                                       Application.showMessage("action is ::"+order_id); 
                                        if(order_id.equals(c.get("pOrderID_sik")))
                                        {
                                            Application.showMessage("Order ID validation is success");
                                              
                                        }
                                       
                                       

                                       
                                 break;
                                 }
                           }
                       }
                       
                       if(v1*v2*v3 ==1)
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


}
