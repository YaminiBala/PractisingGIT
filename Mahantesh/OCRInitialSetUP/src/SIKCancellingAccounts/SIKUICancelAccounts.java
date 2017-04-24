package SIKCancellingAccounts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import QAEnvironmentValidate.myTestClass;

public class SIKUICancelAccounts
{
	public void sikcancels() throws InterruptedException, IOException
	{
	WebDriver driver1=new FirefoxDriver();
	myTestClass my=new myTestClass();
	// String driverPath = "IE driver path";
		//System.setProperty("webdriver.ie.driver", "C:/IEDriverServer.exe");
	//WebDriver driver1=new InternetExplorerDriver();
	File dir = new File("C:\\OCRscreenshots\\SIKCancelAccounts");
	 
	
	 if(dir.exists())
	 {
		 
	 }
	 else
	 {
		 dir.mkdirs();
	 }
	login(driver1);
	clickOnMenuBarOptions(driver1,4);
	adminOptionsValidate( driver1,1);
	forcedOrderCancellationValidate(driver1,1);
	String newdir=dir.toString().concat("\\").concat("SIKCancelAccount").concat(".png");
	my.savescreenshot( driver1,newdir);
	Thread.sleep(5000);
	driver1.close();
	
	}
public void login(WebDriver driver1) throws InterruptedException, IOException
{
	Thread.sleep(5000);
	
	driver1.manage().window().maximize();
	driver1.manage().deleteAllCookies();
	Runtime.getRuntime().exec("C:/crede.exe");
	Thread.sleep(5000);
	driver1.get("http://sikuiint.cable.comcast.com/");
}

//-------------------

public void clickOnMenuBarOptions(WebDriver driver1,int listval) throws InterruptedException
{
	int time=0;
	do
	{
		try
		{
			
			String formid=driver1.findElement(By.xpath("//form[@id='aspnetForm']/div[3]/div/ul/li["+listval+"]/a")).getText();
			System.out.println("sikMenuBar"+formid);
			driver1.findElement(By.xpath("//form[@id='aspnetForm']/div[3]/div/ul/li["+listval+"]/a")).click();
			
			
	
	break;
		}
		catch(Exception e)
		{
			System.out.println("It is in catch");
			time++;
			Thread.sleep(1000);
		}
	}while(time<=120);
}

//--------------------------------

public void adminOptionsValidate(WebDriver driver1,int adminoptions) throws InterruptedException
{
	int time=0;
	do
	{
		try
		{
			
			String formid=driver1.findElement(By.xpath("//form[@id='aspnetForm']/div[5]/div/div[2]/a["+adminoptions+"]")).getText();
			System.out.println("Adminoptions"+formid);
			driver1.findElement(By.xpath("//form[@id='aspnetForm']/div[5]/div/div[2]/a["+adminoptions+"]")).click();
			
			
	
	break;
		}
		catch(Exception e)
		{
			System.out.println("It is in catch");
			time++;
			Thread.sleep(1000);
		}
	}while(time<=120);
}
//----------
public String readingexcels(String FilePath,String sheetname) throws IOException
{
	//InputStream fs = new FileInputStream(FilePath);
	FileInputStream fs = new FileInputStream(FilePath);	
	HSSFWorkbook wb = new HSSFWorkbook(fs);
	HSSFSheet sheet = wb.getSheetAt(0);
	String value1 = "s";
	int j=0;
	int i=1;
	Cell cell = null;
	int coulmnssize=1;
	for(int rows=1;rows<=coulmnssize;coulmnssize++)
	{
		 try
		    {
			 Row row = sheet.getRow(coulmnssize); 
			 cell = row.getCell(0);
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
		 
    Row row = sheet.getRow(i);   
        cell = row.getCell(0);
   
    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	String Value=value1.concat(cell.getStringCellValue()).concat(","); 
    value1=Value;
    }
    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
    System.out.println("numeric: " + cell.getNumericCellValue());
    }
    }
    

	 System.out.println("string: " + value1.replace("s", ""));
	return value1.replace("s", "");
	
}
//--------

public String readingexcelsOrderid(String FilePath,String sheetname) throws IOException
{
	//InputStream fs = new FileInputStream(FilePath);
	FileInputStream fs = new FileInputStream(FilePath);	
	HSSFWorkbook wb = new HSSFWorkbook(fs);
	HSSFSheet sheet = wb.getSheetAt(0);
	String value1 = "s";
	int j=0;
	int i=1;
	Cell cell = null;
	int coulmnssize=1;
	for(int rows=1;rows<=coulmnssize;coulmnssize++)
	{
		 try
		    {
			 Row row = sheet.getRow(coulmnssize); 
			 cell = row.getCell(1);
			 if(row.getCell(1)==null || row.getCell(1).equals("null"))
			 {
				 break;
			 }
		    }
		    catch(Exception e)
		 {
		    	break;
		 }
		
	}
	System.out.println("The value of coulmnsize of order id is"+coulmnssize);
	for( i=1;i<coulmnssize;i++)
	{
		 
    Row row = sheet.getRow(i);   
        cell = row.getCell(1);
   
    if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	String Value=value1.concat(cell.getStringCellValue()).concat(","); 
    value1=Value;
    }
    if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
    System.out.println("numeric: " + cell.getNumericCellValue());
    }
    }
    

	 System.out.println("string: " + value1.replace("s", ""));
	return value1.replace("s", "");
	
}
//---------
public void forcedOrderCancellationValidate(WebDriver driver1,int orderidsize) throws InterruptedException, IOException
{
	String accno=readingexcels("C:/Users/441870/workspace/CancelAccounts.xls","SIK");
	String orderid=readingexcelsOrderid("C:/Users/441870/workspace/CancelAccounts.xls","SIK");
	int time=0;
	do
	{
		try
		{
			if(orderid!=null || !orderid.equalsIgnoreCase("null") || orderid.isEmpty())
			{
			driver1.findElement(By.xpath("//form[@id='aspnetForm']/div[5]/div/div/table/tbody/tr[1]/td[2]/textarea")).sendKeys(orderid);
			driver1.findElement(By.xpath("//form[@id='aspnetForm']/div[5]/div/div/table/tbody/tr[1]/td[3]/input")).click();
			
			}
			
			if(accno!=null || !accno.equalsIgnoreCase("null") || accno.isEmpty())
			{
			driver1.findElement(By.xpath("//form[@id='aspnetForm']/div[5]/div/div/table/tbody/tr[2]/td[2]/textarea")).sendKeys(accno);
			driver1.findElement(By.xpath("//form[@id='aspnetForm']/div[5]/div/div/table/tbody/tr[2]/td[3]/input")).click();
			
			}
	
	break;
		}
		catch(Exception e)
		{
			System.out.println("It is in catch");
			time++;
			Thread.sleep(1000);
		}
	}while(time<=120);
}
}
