package RTPCancellingAccounts;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//import org.apache.xmlbeans.XmlException;

import com.eviware.soapui.config.ProjectConfig;
import com.eviware.soapui.impl.wsdl.AbstractWsdlModelItem;
import com.eviware.soapui.impl.wsdl.WsdlProject;
import com.eviware.soapui.model.iface.MessageExchange;
import com.eviware.soapui.model.support.PropertiesMap;
import com.eviware.soapui.model.testsuite.TestCase;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestRunner;
import com.eviware.soapui.model.testsuite.TestRunner.Status;
import com.eviware.soapui.model.testsuite.TestStep;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.model.testsuite.TestSuite;
import com.eviware.soapui.support.SoapUIException;
import com.eviware.soapui.tools.SoapUITestCaseRunner;

import junit.framework.Assert;

public class RunningSoapProj 
{
	public void testSoapUI(String filepath,String service) throws Exception {
		
	  
	    try {
	    	
	    	List<String> reads=readingexcels(filepath, service);
	    	for(int red=0;red<reads.size();red++)
	    	{
	    		System.out.println(reads.get(red));
	    	
	    	String uniqueID=reads.get(red);
	    	 TestRunner testRunner;	    	
	              WsdlProject project = new WsdlProject("C:/Users/441870/Desktop/soap/RTPTestInterface-soapui-project.xml");
	            //  System.out.println("It is loadd");
	             // project.setPropertyValue("UniqueID",uniqueID);
	              project.setPropertyValue("UniqueID",uniqueID);
	              System.out.println(project.getPropertyNames());
	              System.out.println(project.getProperty("UniqueID"));
	              System.out.println( project.getPropertyValue("UniqueID"));
	              if(service.equalsIgnoreCase("Demi"))
	            		  {
	            	  project.setPropertyValue("service1","10096");
	            	  project.setPropertyValue("service2","20145");
	            		  }
	              else
	              {
	            	  project.setPropertyValue("service1","10300029");
	            	  project.setPropertyValue("service2","10400138");
	              }
	              List<TestSuite> testSuiteList = project.getTestSuiteList();
	             
	              for (TestSuite ts : testSuiteList)
	              {
	                 // System.out.println("****Running Test suite " + ts.getName() + "********");                                                     
	                  List<TestCase> testCaseList = ts.getTestCaseList();                                                     
	                  for (TestCase testcase : testCaseList)
	                  {
	                //    System.out.println("****Running Test Case " + testcase.getName()+ "*****");
                        List<TestStep> teststeps=testcase.getTestStepList();
                        for (TestStep testst : teststeps)
                        {        
	                //    System.out.println("****Running Test Case " + testst.getName()+ "*****");	
	                
                        }
                          
                        
	                     testRunner = testcase.run(new PropertiesMap(), false);
                          
	               Assert.assertEquals(TestRunner.Status.FINISHED, testRunner.getStatus());
	               for (TestStepResult tcr : ((TestCaseRunner) testRunner).getResults()) {
	            	   
	               String request = ((MessageExchange) tcr).getRequestContent();
	               String response = ((MessageExchange)tcr).getResponseContent();
	             //  System.out.println("::::::::::::::"+request);
	               System.out.println(response);
	               System.out.println("*******"+reads.get(red)+"::Completed::"+"*******");
	               System.out.println("-------------------------------------------------");
	                     }
	                  }
	             }
	        }
	    }catch (Exception e) {                                                          
	             e.printStackTrace();
	    }
	}
	

	
	//-------------
	
	public  void RTPCancels() throws Exception
	{
		RunningSoapProj  rs=new RunningSoapProj ();
		FileOutputStream f = new FileOutputStream("C:\\OCRscreenshots\\RTPCancelAccountsDemi.txt");
	     
	     System.setOut(new PrintStream(f));
		rs.testSoapUI("C:/Users/441870/workspace/CancelAccounts.xls", "Demi");
		rs.testSoapUI("C:/Users/441870/workspace/CancelAccounts.xls", "Insight");
	}
	
	//---------
	
	
	public List<String> readingexcels(String FilePath,String serviceType) throws IOException
	{
		
		List<String> values=new ArrayList<String>();
		HSSFSheet sheet;
		FileInputStream fs = new FileInputStream(FilePath);			
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		//HSSFSheet sheet = wb.getSheetAt(2);
		if(serviceType.equalsIgnoreCase("Demi"))
		{
		 sheet =wb.getSheet("RTPAccDemi");
		}
		else
		{
			sheet =wb.getSheet("RTPAccInsight");
		}
		String value1 = "s";
		int j=0;
		int i=1;
		Cell cell = null;
		int coulmnssize=1;
		for(int rows=1;rows<=coulmnssize;coulmnssize++)
		{
			 try
			    {
				 HSSFRow row = sheet.getRow(coulmnssize); 
				 cell = row.getCell(0);
				 System.out.println("rows are"+row.getCell(0) );
				 if(row.getCell(0)==null || row.getCell(0).equals("null"))
				 {
					 break;
				 }
			    }
			    catch(Exception e)
			 {
			    	break;
			 }
			
		}
		
		for( i=1;i<coulmnssize;i++)
		{
			 
	    HSSFRow row = sheet.getRow(i);   
	    cell = row.getCell(0);
	   
	    if (cell.getCellType() == Cell.CELL_TYPE_STRING) 
	    {
		values.add(cell.getStringCellValue());  
	    
	    }
	    
	    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC)
	    {    	
	    	
	    	
	    }
	    
	    }   

		
		return values;
		
	}
}
