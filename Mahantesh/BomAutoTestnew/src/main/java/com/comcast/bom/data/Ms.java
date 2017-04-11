package com.comcast.bom.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.comcast.utils.ComcastTest;

public class Ms extends ComcastTest{
	

	
	 
	
	
	
	public  void CallMS() throws ClassNotFoundException, SQLException
	{
		  CustomerDetails customerDetails;
		int ID;
		 int orderid = 0;
		 customerDetails = CustomerDetails
	 				.loadFromDatatable(dataTable);
		 String acc=customerDetails.accountNumber;
		try
        {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            String userName = "offshore";
            String password = "pass2012";
            String url = "jdbc:sqlserver://10.251.108.162:8152;DatabaseName=SIKOMS";
            Connection con = DriverManager.getConnection(url, userName, password);
            Connection con1 = DriverManager.getConnection(url, userName, password);
            Connection con2 = DriverManager.getConnection(url, userName, password);
            Statement s1 = con.createStatement();
            Statement s2 = con1.createStatement();
            Statement s3 = con2.createStatement();
           // ResultSet rs2=s3.executeQuery("use SIKOMS");
           
            ResultSet rs1= s2.executeQuery("Select OrderID from sikutilitydata where AccountNumber='"+acc+"' order by OrderID Desc");
           
            
            while (rs1.next()){
            	 orderid = rs1.getInt("OrderID");
            	
            	
            	System.out.println("SIK order id:-"+orderid);
            	                                         
                         }
            
            
           /* while (rs2.next()){
            	//int orderid = rs2.getInt("OrderID");
            	
            	
            	//System.out.println(orderid);
            	
             
              
               
                 
                }
            */
            ResultSet rs = s1.executeQuery("SELECT DISTINCT OOPCD.GROUPID, OOPCD.ID, FG.FULFILLMENTCODE,OOPCD.ONLINEORDERSPRODUCTCODESID FROM SIKOMS.ONLINEORDERSPRODUCTCODEDEVICES OOPCD,SIKOMS.ONLINEORDERSFINISHEDGOODS OOFG, SIKOMS.FINISHEDGOODS FG WHERE OOPCD.ONLINEORDERSPRODUCTCODESID = OOFG.ONLINEORDERSPRODUCTCODESID AND OOFG.FINISHEDGOODID = FG.ID AND OOPCD.ONLINEORDERSPRODUCTCODESID IN (SELECT ID FROM SIKOMS.ONLINEORDERSPRODUCTCODES WHERE ONLINEORDERSID IN ( "+orderid+" )) ORDER BY OOPCD.GROUPID");    
         while (rs.next()){
        	 
        	 
                	int groupid = rs.getInt("GROUPID");
                	 ID = rs.getInt("ID");
                	String FULFILLMENTCODE = rs.getString("FULFILLMENTCODE");
                	
                	System.out.println("Group id:-"+groupid);
                	//System.out.println("product id:-"+ID);
                 
                	System.out.println("Fulfillment code:-"+FULFILLMENTCODE);
                   
                     int newid=ID+1;
                     int oldid=ID;
                     
                     
                     if(FULFILLMENTCODE=="VID-COMM-DCX3200-MPEG4"||FULFILLMENTCODE=="VID-COMM-MOTRNG150CNMR-MP"||FULFILLMENTCODE=="RMA-CommEasyReturns"||FULFILLMENTCODE=="VID-COMM-RNG110RF-MPEG4"||FULFILLMENTCODE=="VID-COMM-RNG110CHD-MPEG4"||FULFILLMENTCODE=="VID-COMM-RNG110-MPEG4"||FULFILLMENTCODE=="VID-COMM-MOTRNG150N-MPEG4"||FULFILLMENTCODE=="VID-COMM-MOTRNG150CNMR-MP")
                     {
                    	 
                    	System.out.println("success"); 
                     }
                     System.out.println(oldid);
                     System.out.println(newid);
                    }
         
         
       
         
           /* 
            while (rs.next()){
            	
           	 for (int i = 1; i < rs.getMetaData().getColumnCount() + 1; i++) {
                    System.out.print(" " + rs.getMetaData().getColumnName(i) + "=" + rs.getObject(i));
                  
                
                  }
           	 
           	 
            }
      */
         
      
               
              
                System.out.print("connected");    
           

        

        } catch (Exception e)
        {
            e.printStackTrace();
        }
		
		
		
		
	}

}