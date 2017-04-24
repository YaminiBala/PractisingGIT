package QAEnvironmentValidate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import QAEnvironmentValidate.MaintenanceSIKValidate;

public class myTestClass

{
	//WebDriver driver = new FirefoxDriver();
	HomeSecurityConfigValidation ucont=new HomeSecurityConfigValidation();
	MaintenanceSIKValidate sik=new MaintenanceSIKValidate();
	SuspendGlobalProcess sus = new SuspendGlobalProcess();
	String winHandleBefore;
	
	
	
	public void EnvMaintenanceSIK(WebDriver driver) throws InterruptedException
	{
		
		Thread.sleep(2000);
		int i=0;
		do
		{
		try
		{
		driver.findElement(By.xpath(".//*[@id='cwiE$B_3']/span")).click();
		break;
		}
		catch(Exception e)
		{
			try
			{
			System.out.println("It is in catch");
			driver.findElement(By.xpath(".//*[@id='cwi9$B_3']/span")).click();
			break;
			}
			catch(Exception e1)
			{
				Thread.sleep(1000);
				i++;
			}
		}
		}while(i<=120);
		//Thread.sleep(1000);
		int sik=0;
		do
		{
		try
		{
			String divid=driver.findElement(By.xpath("//div[2]")).getAttribute("id");
			System.out.println("sikid1"+divid);
			String divid1=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div")).getAttribute("id");
			System.out.println("sikid2"+divid1);
			String tableid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table")).getAttribute("id");
			System.out.println("sikid3"+tableid);
			String tbodyid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table[@id='"+ tableid+"']/tbody/tr[6]/td")).getAttribute("id");
			System.out.println("siki4"+tbodyid);
			String sikid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table[@id='"+ tableid+"']/tbody/tr[6]/td[@id='"+ tbodyid+"']/table/tbody/tr/td")).getAttribute("id");
			System.out.println("sikid"+sikid);
		driver.findElement(By.xpath(".//*[@id='"+ sikid+"']/span")).click();
		break;
		}
		catch(Exception e)
		{
			Thread.sleep(1000);
			System.out.println("It is in catch-clicking sik");
			sik++;
			//driver.findElement(By.xpath(".//*[@id='cwi9$M1_3']/span")).click();
		}
		}while(sik<=120);
	}
	//---------
	
	public void EnvironmentSIKCorpDef(String corp,WebDriver driver) throws InterruptedException
	{	
		//EnvBasicMethods test=new EnvBasicMethods();
		// test.ClickOnMenuBar(driver, 4);
	//	test.ClickOnMenuBarOptions(driver,6,"Maintenance");
		ClickOnMenuBarSuBOptions(driver, 1);
	Thread.sleep(1000);
	
	int enterincorp=0;
	do
	{
		try{
		String tableid=driver.findElement(By.xpath("//table")).getAttribute("id");
		String tabletdid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td")).getAttribute("id");
		String divid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div")).getAttribute("id");
		String frameid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[@id='"+ divid+"']/iframe")).getAttribute("id");
		System.out.println(""+driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[@id='"+ divid+"']/iframe")).getAttribute("id"));
		driver.switchTo().frame(driver.findElement(By.id(frameid)));
		System.out.println("Moved to new Frame");
	//	driver.findElement(By.xpath("//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input")).sendKeys(corp);
		driver.findElement(By.xpath("//form[@id='cwForm']/table/tbody/tr/td/table/tbody/tr[2]/td/div/table/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input")).sendKeys(corp);
		break;
			}
			catch(Exception e){
				System.out.println("It is in catch-corp sending");
				Thread.sleep(1000);
				enterincorp++;
			}
	}while(enterincorp<=120);
	
	//------
	
	driver.switchTo().defaultContent();
	Thread.sleep(1000);
	int search=0;
	do
	{
		try{
		String tableid=driver.findElement(By.xpath("//table")).getAttribute("id");
		String tabletdid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td")).getAttribute("id");
		String divid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td[@id='"+ tabletdid+"']/div")).getAttribute("id");
		String frametableid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td[@id='"+ tabletdid+"']/div[@id='"+ divid+"']/table")).getAttribute("id");
		String frameid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td[@id='"+ tabletdid+"']/div[@id='"+ divid+"']/table[@id='"+ frametableid+"']/tbody/tr/td")).getAttribute("id");
		System.out.println(""+driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td[@id='"+ tabletdid+"']/div[@id='"+ divid+"']/table[@id='"+ frametableid+"']/tbody/tr/td")).getAttribute("id"));
		driver.findElement(By.xpath(".//*[@id='"+ frameid+"']/span")).click();
		break;
			}
			catch(Exception e){
				System.out.println("It is in catch-serach after entering corp");
				Thread.sleep(1000);
				search++;
			}
	}while(search<=120);
	
	//--------------
	int row=0;
do
{
	try{
	String tableid=driver.findElement(By.xpath("//table")).getAttribute("id");
	String tabletdid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td")).getAttribute("id");
	String divid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div")).getAttribute("id");
	String frameid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[@id='"+ divid+"']/iframe")).getAttribute("id");
	System.out.println("moving to row frame"+driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[@id='"+ divid+"']/iframe")).getAttribute("id"));
	driver.switchTo().frame(driver.findElement(By.id(frameid)));
	break;
		}
		catch(Exception e){
			System.out.println("It is in catch--moving to row frame");
			Thread.sleep(1000);
			row++;
		}
}while(row<=120);
//-----
int doubleclickrow=0;
do
{
	
	try
	{
String formid=driver.findElement(By.xpath("//form")).getAttribute("id");
String formtableid=driver.findElement(By.xpath("//form[@id='"+ formid+"']/table")).getAttribute("id");
String formInsidetableid=driver.findElement(By.xpath("//form[@id='"+ formid+"']/table[@id='"+ formtableid+"']/tbody/tr/td/table")).getAttribute("id");
String formInsidetableid1=driver.findElement(By.xpath("//form[@id='"+ formid+"']/table[@id='"+ formtableid+"']/tbody/tr/td/table[@id='"+ formInsidetableid+"']/tbody/tr[4]/td/table")).getAttribute("id");
String formInsidetableid2=driver.findElement(By.xpath("//form[@id='"+ formid+"']/table[@id='"+ formtableid+"']/tbody/tr/td/table[@id='"+ formInsidetableid+"']/tbody/tr[4]/td/table[@id='"+ formInsidetableid1+"']/tbody/tr[2]/td/div")).getAttribute("id");
String formInsidetableid3=driver.findElement(By.xpath("//form[@id='"+ formid+"']/table[@id='"+ formtableid+"']/tbody/tr/td/table[@id='"+ formInsidetableid+"']/tbody/tr[4]/td/table[@id='"+ formInsidetableid1+"']/tbody/tr[2]/td/div[@id='"+ formInsidetableid2+"']/table")).getAttribute("class");
String rowframeid=driver.findElement(By.xpath("//form[@id='"+ formid+"']/table[@id='"+ formtableid+"']/tbody/tr/td/table[@id='"+ formInsidetableid+"']/tbody/tr[4]/td/table[@id='"+ formInsidetableid1+"']/tbody/tr[2]/td/div[@id='"+ formInsidetableid2+"']/table[@class='"+ formInsidetableid3+"']/tbody/tr")).getAttribute("id");
System.out.println("double click row"+rowframeid);
Actions action = new Actions(driver);
	//WebElement element=driver.findElement(By.xpath(".//*[@id='cwrR3_H']"));
WebElement element=driver.findElement(By.xpath(".//*[@id='"+ rowframeid+"']"));
	action.doubleClick(element).perform();
	break;
	}
	catch(Exception e)
	{
		System.out.println("Catch is in double click row");
		Thread.sleep(1000);
		doubleclickrow++;
	}
	
}while(doubleclickrow<=60);
	driver.switchTo().defaultContent();
	Thread.sleep(1000);
	
	int validatesikconfig=0;
	do
	{
		try{
		String tableid=driver.findElement(By.xpath("//table")).getAttribute("id");
		String tabletdid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td")).getAttribute("id");
		String divid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[2]")).getAttribute("id");
		String frameid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[2][@id='"+ divid+"']/iframe")).getAttribute("id");
		System.out.println("validatesikconfig::"+driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[2][@id='"+ divid+"']/iframe")).getAttribute("id"));
		driver.switchTo().frame(driver.findElement(By.id(frameid)));
		break;
			}
			catch(Exception e){
				System.out.println("It is in catch-validate sik");
				Thread.sleep(1000);
				validatesikconfig++;
			}
	}while(validatesikconfig<=120);
	
	int check=0;
	do
		{
		try
		
	{
	
	driver.findElement(By.xpath(".//*[@id='f.0000']")).getText().toString();	
	
	driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[3]/td[3]/input"));	
	
	WebElement Active=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[7]/td[1]/input"));
	
	WebElement biller=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[7]/td[3]/input"));
	
	WebElement ComtracDDP=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[11]/td[1]/input[1]"));
	
	WebElement CSGENI=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[11]/td[3]/input[1]"));
	
	WebElement Dotcom=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[15]/td[1]/input"));
	
	WebElement MarketTrial=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[15]/td[3]/input[1]"));
	
	WebElement CSGSIKREMODEL=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[19]/td[1]/input[1]"));
	
	WebElement DotcomSIKREMODEL=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[19]/td[3]/input[1]"));
	
	WebElement OPXHSQUEUE=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[23]/td[1]/input[1]"));
	
	
	WebElement opsCHEDULED=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[31]/td[1]/input[1]"));
	corpValuesValidation(driver,opsCHEDULED,corp,"Selected","nonreleaseB");
	System.out.println("opsCHEDULED");
	
	WebElement AddressUpdateEnabled=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[31]/td[3]/input[1]"));
	corpValuesValidation(driver,AddressUpdateEnabled,corp,"Selected","nonreleaseB");

	
	WebElement isCVREnabled=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[35]/td[1]/input[1]"));
	corpValuesValidation(driver,isCVREnabled,corp,"Selected","nonreleaseB");

	
	WebElement  ReleaseB=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[35]/td[3]/input[1]"));
	corpValuesValidation(driver,ReleaseB,corp,"Selected","releaseB");

	WebElement  cvrVal=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[39]/td[1]/input"));
	corpValuesValidation( driver,cvrVal,corp,"Text","3");

	
	WebElement Homestreamcall=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[39]/td[3]/input[1]"));
	corpValuesValidation(driver,Homestreamcall,corp,"Selected","nonreleaseB");
	
	
	break;
	}
	catch(Exception e)
	{
		System.out.println("finding new serror");
		check++;
		Thread.sleep(1000);
	}
		}while(check<=60);
	
	Thread.sleep(1000);
	driver.switchTo().defaultContent();
	//sus.clickONserActDetailsbutton(driver, 14);
	int search1=0;
	do
	{
		try{
		String tableid=driver.findElement(By.xpath("//table")).getAttribute("id");
		
		String tabletdid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td")).getAttribute("id");
		
		String divid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td[@id='"+ tabletdid+"']/div")).getAttribute("id");
		
		String frametableid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td[@id='"+ tabletdid+"']/div[@id='"+ divid+"']/table")).getAttribute("id");
		
		String frameid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td[@id='"+ tabletdid+"']/div[@id='"+ divid+"']/table[@id='"+ frametableid+"']/tbody/tr/td[14]")).getAttribute("id");
		
		System.out.println(""+driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[7]/td[@id='"+ tabletdid+"']/div[@id='"+ divid+"']/table[@id='"+ frametableid+"']/tbody/tr/td[14]")).getAttribute("id"));
	//	System.out.println(frameid);
		driver.findElement(By.xpath(".//*[@id='"+ frameid+"']/img")).click();
		driver.findElement(By.xpath(".//*[@id='"+ frameid+"']")).click();
		//System.out.println(frameid);
		break;
			}
			catch(Exception e){
				System.out.println("It is in catch-serach after entering corp");
				Thread.sleep(1000);
				search1++;
			}
	}while(search1<=120);
	}	
	
	
	//------------
	public void corpValuesValidation(WebDriver driver,WebElement elemnt, String corp,String options,String releaseselect)
	{
		
		switch(options)
		{
		
		case "Text":
			
			if(!elemnt.getAttribute("value").equalsIgnoreCase(releaseselect))
			{
			elemnt.sendKeys(Keys.chord(Keys.CONTROL, "a"),releaseselect);
			System.out.println("text present"+elemnt.getAttribute("value"));
			}
			break; 
			
		case "Enabled":
			
			if(!elemnt.isEnabled())
				elemnt.click();
			break;
			
		case "Selected":
			
			switch(releaseselect)
			{
			case "nonreleaseB":
				
				if(!elemnt.isSelected())
					elemnt.click();
				break;
			case "releaseB":
				
				switch(corp)
				{
				case "99996":
				case "821010":
					if(!elemnt.isSelected())
						elemnt.click();
					break;
				case "99920":
				case "872010":
					if(elemnt.isSelected())
						System.out.println("Element is selected");
						elemnt=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[35]/td[3]/input[2]"));
						elemnt.click();
					break;
				default:
					System.out.println("Selected corp is not present");
				}
			default:
				System.out.println("Given Release B string  is not Correct");
			}
		
			
	
		
	default:
		  System.out.println("Given Option string  is not Correct");
		}
	}
	//------------
	public void ClickOnMenuBar(WebDriver driver,int menubarnum) throws InterruptedException
	{
		int i=0;
		do
		{
		try
		{
		String tableid=driver.findElement(By.xpath("//table")).getAttribute("id");
		
		driver.findElement(By.xpath("//table[@id='"+ tableid+"']/tbody/tr[2]/td/div/table/tbody/tr/td["+menubarnum+"]/span")).click();
		//driver.findElement(By.xpath("//table[@id='cwtab_2']/tbody/tr[2]/td/div/table/tbody/tr/td[5]/span")).click();
		break;
		}
		catch(Exception e)
		{
			Thread.sleep(1000);
			
			i++;
		}
		}while(i<=120);
	}
	
	//-----------
	public void ClickOnMenuBarOptions(WebDriver driver,int menubaroptnum,String menubar) throws InterruptedException
	{
		int i=0;
		String menubaroptid;
		do
		{
		try
		{
			String divid=driver.findElement(By.xpath("//div[2]")).getAttribute("id");
			
			String divid1=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div")).getAttribute("id");
			
			String tableid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table")).getAttribute("id");
			
			String tbodyid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table[@id='"+ tableid+"']/tbody/tr[ "+menubaroptnum+"]/td")).getAttribute("id");
			
			if(menubar.equalsIgnoreCase("Monitoring") || menubar.equalsIgnoreCase("Maintenance") || menubar.equalsIgnoreCase("Development"))
			{
			 menubaroptid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table[@id='"+ tableid+"']/tbody/tr["+menubaroptnum+"]/td[@id='"+ tbodyid+"']/table/tbody/tr/td")).getAttribute("id");
			
			}
			else
			{
				 menubaroptid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table[@id='"+ tableid+"']/tbody/tr[ "+menubaroptnum+"]/td")).getAttribute("id");
			}
		driver.findElement(By.xpath(".//*[@id='"+ menubaroptid+"']/span")).click();
		break;
		}
		catch(Exception e)
		{
			Thread.sleep(1000);
			i++;
		}
		}while(i<=120);
	}
	
	//------------
	public void ClickOnMenuBarSuBOptions(WebDriver driver,int menubaroptnum) throws InterruptedException
	{
		int i=0;
		String menubaroptid;
		do
		{
		try
		{
			String divid=driver.findElement(By.xpath("//div[3]")).getAttribute("id");
			
			String divid1=driver.findElement(By.xpath("//div[3][@id='cwPM2']/div")).getAttribute("id");
			
			String tableid=driver.findElement(By.xpath("//div[3][@id='cwPM2']/div[@id='"+ divid1+"']/table")).getAttribute("id");
			
			String tbodyid=driver.findElement(By.xpath("//div[3][@id='cwPM2']/div[@id='"+ divid1+"']/table[@id='"+ tableid+"']/tbody/tr[ "+menubaroptnum+"]/td")).getAttribute("id");
			
			
		driver.findElement(By.xpath(".//*[@id='"+ tbodyid+"']/span")).click();
		break;
		}
		catch(Exception e)
		{
			Thread.sleep(1000);
			i++;
		}
		}while(i<=120);
	}
	
	//-------------
	
	public void EnvMaintenanceConfiguration(WebDriver driver) throws InterruptedException
	{
		
		Thread.sleep(2000);
		int i=0;
		do
		{
		try
		{
		driver.findElement(By.xpath(".//*[@id='cwiE$B_3']/span")).click();
		break;
		}
		catch(Exception e)
		{
			try
			{
			System.out.println("It is in catch");
			driver.findElement(By.xpath(".//*[@id='cwi9$B_3']/span")).click();
			break;
			}
			catch(Exception e1)
			{
				Thread.sleep(1000);
				i++;
			}
		}
		}while(i<=120);
		//Thread.sleep(1000);
		int config=0;
		do
		{
		try
		{
			String divid=driver.findElement(By.xpath("//div[2]")).getAttribute("id");
			System.out.println("sikid1"+divid);
			String divid1=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div")).getAttribute("id");
			System.out.println("sikid2"+divid1);
			String tableid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table")).getAttribute("id");
			System.out.println("sikid3"+tableid);
			String tbodyid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table[@id='"+ tableid+"']/tbody/tr[2]/td")).getAttribute("id");
			System.out.println("siki4"+tbodyid);
			String configid=driver.findElement(By.xpath("//div[2][@id='cwPM1']/div[@id='"+ divid1+"']/table[@id='"+ tableid+"']/tbody/tr[2]/td[@id='"+ tbodyid+"']/table/tbody/tr/td")).getAttribute("id");
			System.out.println("sikid"+configid);
		driver.findElement(By.xpath(".//*[@id='"+ configid+"']/span")).click();
		break;
		}
		catch(Exception e)
		{
			Thread.sleep(1000);
			System.out.println("It is in catch-clicking sik");
			config++;
			//driver.findElement(By.xpath(".//*[@id='cwi9$M1_3']/span")).click();
		}
		}while(config<=120);
	}
	
	
	//--------------------
	public void EnvLoginIn(String URL,WebDriver driver) throws InterruptedException
	{
	//	WebDriver driver = new FirefoxDriver();
		driver.manage().window().maximize();
	driver.manage().deleteAllCookies();
	driver.get(URL);
	List<WebElement> editorFrame=driver.findElements(By.xpath("//iframe"));
	for(WebElement iframe : editorFrame )
	{
		System.out.println("Frames :::: "+iframe.getAttribute("id"));
		
	}
	System.out.println("Current URl:"+driver.getCurrentUrl());
	System.out.println("Current source:"+driver.getPageSource());
	System.out.println("Current Title:"+driver.getTitle());
	driver.switchTo().frame(driver.findElement(By.id("cwf_5")));
	driver.findElement(By.xpath("//input[@class='CwfFF']")).sendKeys("qaadmin");
	driver.findElement(By.xpath(".//*[@id='CwSearch']/div/table/tbody/tr[13]/td[5]/input")).sendKeys("tester123");	
	driver.findElement(By.xpath("//input[@type='button' and @value='Login']")).click();	
	driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS); 
	driver.switchTo().defaultContent();
	Thread.sleep(2000);	
	int i=0;
	do
	{
	try{
	driver.switchTo().frame(driver.findElement(By.id("cwf_7")));
	break;
	}catch(Exception e)
	{
		try
		{
		System.out.println("It is in catch");
		driver.switchTo().frame(driver.findElement(By.id("cwf_C")));
		break;
		}
		catch(Exception e1)
		{
			Thread.sleep(1000);
			i++;
		}
	}
	}while(i<=120);
	
	System.out.println(driver.findElement(By.xpath("//*[@id='CwSearch']/div/table/tbody/tr[7]/td[1]/input")).isDisplayed());
	driver.findElement(By.xpath(".//*[@id='CwSearch']/div/table/tbody/tr[3]/td[1]/select/option[@value='op:orderproc']")).click();
	driver.findElement(By.xpath(".//*[@id='CwSearch']/div/table/tbody/tr[7]/td[1]/input")).click();
	
	driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	driver.switchTo().defaultContent();
		
	}
	
	//--------------------
	
	//----------------
	public Map EnvironmentConfigHomesecValidate(WebDriver driver) throws InterruptedException
	{
		Thread.sleep(1000);
		int time=0;
		do
		{
			try
			{
				
				String divid1=driver.findElement(By.xpath("//div[3][@id='cwPM2']/div")).getAttribute("id");
				System.out.println("sikid2"+divid1);
				String tableid=driver.findElement(By.xpath("//div[3][@id='cwPM2']/div[@id='"+ divid1+"']/table")).getAttribute("id");
				System.out.println("sikid3"+tableid);
				String tbodyid=driver.findElement(By.xpath("//div[3][@id='cwPM2']/div[@id='"+ divid1+"']/table[@id='"+ tableid+"']/tbody/tr[6]/td")).getAttribute("id");
				System.out.println("siki4"+tbodyid);
				
		driver.findElement(By.xpath(".//*[@id='"+ tbodyid+"']/span")).click();
		break;
			}
			catch(Exception e)
			{
				System.out.println("It is in catch");
				time++;
				Thread.sleep(1000);
			}
		}while(time<=120);
		
		
		//------
		winHandleBefore = driver.getWindowHandle();
		Map map=new HashMap();
		map.put("winValue", winHandleBefore);
		int changingframe=0;
		do
		{
			try{
				for(String winHandle : driver.getWindowHandles()){
					System.out.println("winHandle"+winHandle);
					Thread.sleep(5000);
					 

					driver.switchTo().window(winHandle);
				  
				  
				}
			String tableid=driver.findElement(By.xpath("//table")).getAttribute("id");
			System.out.println("tableid"+tableid);
			String tabletdid=driver.findElement(By.xpath(".//*[@id='"+tableid+"']/tbody/tr[4]/td")).getAttribute("id");
			System.out.println("tabletdid"+tabletdid);
			String frameid=driver.findElement(By.xpath(".//*[@id='"+tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/iframe")).getAttribute("id");
			System.out.println("frameid"+frameid);
			driver.switchTo().frame(driver.findElement(By.id(frameid)));
			map.put("TableId", tableid);
			System.out.println("Successfully moved to frame");
			/*String tableiducontrol=driver.findElement(By.xpath("//table")).getAttribute("id");
			String tabletdidframe=driver.findElement(By.xpath(".//*[@id='"+ tableiducontrol+"']/tbody/tr/td/table")).getAttribute("id");
			String tabletdid1frame=driver.findElement(By.xpath(".//*[@id='"+ tableiducontrol+"']/tbody/tr/td/table[@id='"+ tabletdidframe+"']/tbody/tr[1]/td")).getAttribute("id");
			String ucontrol=driver.findElement(By.xpath(".//*[@id='"+ tableiducontrol+"']/tbody/tr/td/table[@id='"+ tabletdidframe+"']/tbody/tr[1]/td[@id='"+ tabletdid1frame+"']/table/tbody/tr/td[8]")).getAttribute("id");
			//driver.findElement(By.xpath("//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input")).sendKeys(corp);
			driver.findElement(By.xpath("//*[@id='"+ ucontrol+"']")).click();*/
			break;
				}
				catch(Exception e){
					System.out.println("It is in homesecurity catch frame");
					Thread.sleep(1000);
					changingframe++;
				}
		}while(changingframe<=120);
		return map;
		
	}
	//---------------------
	public void EnvironmentCorpConfigValidate(WebDriver driver) throws InterruptedException
	{
		Map map=new HashMap();
		map.put("Environment(0)", "DTDEV");
		map.put("Environment(1)", "QA4");
		List<String> url=new ArrayList<String>();
		List<String> corp=new ArrayList<String>();
		url.add("http://10.253.90.218:7901/cwf");
		url.add("http://10.252.115.108:7901/cwf/");
		//url.add("http://omop-dt-a1q.ula.comcast.net:8001/cwf/");
		String dirname = "newDir";
		 File dir = new File("C:\\OCRscreenshots\\CorpValidation");
		
		 if(dir.exists())
		 {
			 
		 }
		 else
		 {
			 dir.mkdirs();
		 }
		
		corp.add("99920");
		corp.add("872010");
		corp.add("99996");
		corp.add("821010");
		for(int i=0;i<url.size();i++)
		{
			String Url=url.get(i).toString();
			System.out.println("URLLLLL"+Url);
			EnvLoginIn(Url, driver);
			for(int j=0;j<corp.size();j++)
			{
				Thread.sleep(2000);
				EnvMaintenanceSIK(driver);
				
				
			//	sik.EnvironmentSIKCorpDef(corp.get(j).toString(),driver);
				EnvironmentSIKCorpDef(corp.get(j).toString(), driver);
				Thread.sleep(1000);
				String envname="Environment("+i+")";
				 String newdir=dir.toString().concat("\\").concat((String) map.get(envname)).concat("_").concat(corp.get(j).toString()).concat(".png");
				savescreenshot( driver,newdir);
				System.out.println("*************------------------***************");
			}
		/*	EnvMaintenanceConfiguration(driver);
			EnvironmentConfigHomesecValidate(driver);
			ucont.UcontrolValidation(driver);
			ucont.OtherMiscellaneousValidation(driver);
			ucont.RTPValidation(driver);
			ucont.SaveConfigsValidation(driver);
			driver.close();
			driver.switchTo().window(winHandleBefore);
			Thread.sleep(10000);*/
			//sus.suspendingGlobalProcess(driver);
			
		}
		driver.close();
	}
	//---------------
	
	public void savescreenshot(WebDriver driver,String directory) {

        try {

               File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

               FileUtils.copyFile(scrFile,

                            new File(directory));

        } catch (IOException e) {

               // TODO Auto-generated catch block

               e.printStackTrace();

        }

 }


	//-----------
	
	
	
	
}	

