package QAEnvironmentValidate;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class SuspendGlobalProcess
{
public void suspendingGlobalProcess() throws InterruptedException
{
	WebDriver driver3= new FirefoxDriver();
	System.out.println(" ****Suspending Global Process In UI ****");
	 File dir = new File("C:\\OCRscreenshots\\SuspendProcessValidation");
	 
		
	 if(dir.exists())
	 {
		 
	 }
	 else
	 {
		 dir.mkdirs();
	 }
	 myTestClass tc=new  myTestClass();
	 tc.EnvLoginIn("http://10.253.90.218:7901/cwf/", driver3);
	 tc.ClickOnMenuBar(driver3, 5);
	 tc.ClickOnMenuBarOptions(driver3,4,"Monitoring");
	 tc.ClickOnMenuBarSuBOptions(driver3, 2);
	 MoveToFramesusProcess(driver3);
	 selctingOptionsfromMenus(driver3, 3, 5, 2, "type");
	 driver3.switchTo().defaultContent();
	 clickONserActDetailsbutton(driver3,1);
	 MoveToFramesusProcess(driver3);
	 selctingOptionsfromMenus(driver3, 0, 0, 1, "contents");
	 driver3.switchTo().defaultContent();
	 clickONserActDetailsbutton(driver3,10);
	 clickONserActDetailsbuttonSUBOPTIONS(driver3,1);
	 String newdir=dir.toString().concat("\\").concat("QA4").concat("_").concat("SuspendProcess").concat(".png");
		tc.savescreenshot( driver3,newdir);
	 System.out.println(" Suspending Global Process In UI  is Completed");
	 driver3.close();
	 
}

public void MoveToFramesusProcess(WebDriver driver) throws InterruptedException
{
	int validatesusconfig=0;
	do
	{
		try{
		String tableid=driver.findElement(By.xpath("//table")).getAttribute("id");
		String tabletdid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td")).getAttribute("id");
		String divid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[1]")).getAttribute("id");
		String frameid=driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[1][@id='"+ divid+"']/iframe")).getAttribute("id");
	//	System.out.println("dYNAMIC fRAME ::"+driver.findElement(By.xpath(".//*[@id='"+ tableid+"']/tbody/tr[4]/td[@id='"+ tabletdid+"']/table/tbody/tr[2]/td/div[1][@id='"+ divid+"']/iframe")).getAttribute("id"));
		driver.switchTo().frame(driver.findElement(By.id(frameid)));
		break;
			}
			catch(Exception e){
				
				Thread.sleep(1000);
				validatesusconfig++;
			}
	}while(validatesusconfig<=120);
}

//-------------------------

public void selctingOptionsfromMenus(WebDriver driver,int rows,int columns,int option,String fields) throws InterruptedException
{
	int doubleclickrow=0;
	do
	{
		
		try
		{
			String formid=driver.findElement(By.xpath("//form")).getAttribute("id");
			String formtableid=driver.findElement(By.xpath("//form[@id='"+ formid+"']/table")).getAttribute("id");
			String formInsidetableid=driver.findElement(By.xpath("//form[@id='"+ formid+"']/table[@id='"+ formtableid+"']/tbody/tr/td/table")).getAttribute("id");
	
		switch(fields)
		{
		case "type":
		case "priority":
		case "status":
			
			String formInsidetableid1=driver.findElement(By.xpath("//form[@id='"+ formid+"']/table[@id='"+ formtableid+"']/tbody/tr/td/table[@id='"+ formInsidetableid+"']/tbody/tr[1]/td/div/div/table")).getAttribute("class");
			
			driver.findElement(By.xpath("//form[@id='"+ formid+"']/table[@id='"+ formtableid+"']/tbody/tr/td/table[@id='"+ formInsidetableid+"']/tbody/tr[1]/td/div/div/table[@class='"+ formInsidetableid1+"']/tbody/tr["+rows+"]/td["+columns+"]/select/option["+option+"]")).click();
			
			break;
		case "contents":
			driver.findElement(By.xpath("//form[@id='"+ formid+"']/table[@id='"+ formtableid+"']/tbody/tr/td/table[@id='"+ formInsidetableid+"']/tbody/tr[3]/td/table/tbody/tr[2]/td/div/table/tbody/tr["+option+"]")).click();
			break;
		default:
			break;
			
		}
	break;
		
		}
		catch(Exception e)
		{
			
			Thread.sleep(1000);
			doubleclickrow++;
		}
		
	}while(doubleclickrow<=60);
}

//------------------------------

public void clickONserActDetailsbutton(WebDriver driver,int columns) throws InterruptedException
{
	
	int search=0;
	do{
		

	try
	{
		
	driver.findElement(By.xpath("//table[@id='cwtab_2']/tbody/tr[7]/td/div/table/tbody/tr/td["+columns+"]/span")).click();
	
	break;
	}
	catch(Exception e)
	{
		
		search++;
		Thread.sleep(1000);
	}
}while(search<=60);
}

//----------------------------------------


public void clickONserActDetailsbuttonSUBOPTIONS(WebDriver driver,int columns) throws InterruptedException
{
	int search=0;
	do{
		

	try
	{
		
	driver.findElement(By.xpath("//div[@id='cwPM1']/div/table/tbody/tr["+columns+"]/td/span")).click();
	break;
	}
	catch(Exception e)
	{
		System.out.println("catch");
		search++;
		Thread.sleep(1000);
	}
}while(search<=60);
}
}
