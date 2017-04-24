package QAEnvironmentValidate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HomeSecurityConfigValidation {
	
	public void UcontrolValidation(WebDriver driver) throws InterruptedException
	{
		int i=0,j=0,k=0;
		ClickOnHomeSecurityIcons(driver,8) ;
		do
		{
			try
			{
		Boolean simulatorenabled=driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/table[2]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input[1]")).isSelected();
		System.out.println("simulatorEnabled"+simulatorenabled);
		if(simulatorenabled)
		{
			driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/table[2]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input[2]")).click();
		}
		break;
			}
			catch(Exception e)
			{
				i++;
				Thread.sleep(1000);
			}
		}while(i<=100);
		do
		{
			try
			{
		String CLSConfig=driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/div[2]/div[2]/div[2]/table[11]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/textarea")).getText();
		System.out.println("CLSConfig"+CLSConfig);
		
		//driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/div[2]/div[2]/div[2]/table[11]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/textarea")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/div[2]/div[2]/div[2]/table[11]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/textarea")).clear();
		break;
			}
			catch(Exception e)
			{
				j++;
				Thread.sleep(1000);
			}
		}while(j<=100);
		do
		{
			try
			{
		String eventtriggers=driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/div[2]/div[2]/div[2]/table[8]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input")).getText();
		System.out.println("eventtriggers"+eventtriggers);
		driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/div[2]/div[2]/div[2]/table[8]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input")).clear();
		break;
			}
			catch(Exception e)
			{
				k++;
				Thread.sleep(1000);
			}
		}while(k<=100);
	}
	
	//--------------
	
	public void OtherMiscellaneousValidation(WebDriver driver) throws InterruptedException
	{
		ClickOnHomeSecurityIcons(driver,12) ;
		int i=0;
		do
		{
			try
			{
		Boolean FoxtrotEnabled=driver.findElement(By.xpath(".//*[@id='CwSearch']/table[1]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input[1]")).isSelected();
		System.out.println("FoxtrotEnabled"+FoxtrotEnabled);
		if(FoxtrotEnabled==false)
		{
			driver.findElement(By.xpath(".//*[@id='CwSearch']/table[1]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input[1]")).click();
		}
		break;
			}
			catch(Exception e)
			{
				i++;
				Thread.sleep(1000);
			}
		}while(i<=100);
	}
	
	//-------------
	public void RTPValidation(WebDriver driver) throws InterruptedException
	{
		ClickOnHomeSecurityIcons(driver,14) ;
		int j=0;
		do
		{
			try
			{
		String cvrvaluereleaseB=driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/div[2]/table[2]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/textarea")).getText();
		System.out.println("cvrvaluereleaseB"+cvrvaluereleaseB);
		driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/div[2]/table[2]/tbody/tr/td/div/table/tbody/tr[3]/td[1]/textarea")).clear();;
		
		String cvrvaluenonreleaseB=driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/div[2]/table[2]/tbody/tr/td/div/table/tbody/tr[7]/td[1]/textarea")).getText();
		System.out.println("cvrvaluereleaseB"+cvrvaluenonreleaseB);
		driver.findElement(By.xpath(".//*[@id='CwSearch']/div/div[2]/div[2]/table[2]/tbody/tr/td/div/table/tbody/tr[7]/td[1]/textarea")).clear();
		break;
			}
			catch(Exception e)
			{
				j++;
				Thread.sleep(1000);
			}
		}while(j<=100);
	
	}
	//----------
	public void SaveConfigsValidation(WebDriver driver,String tableid) throws InterruptedException
	{
	driver.switchTo().defaultContent();
	int j=0;
	do
	{
		try
		{
	driver.findElement(By.xpath(".//*[@id='"+tableid+"']/tbody/tr[7]/td/div/table/tbody/tr/td[4]/span")).click();
	
	break;
		}
		catch(Exception e)
		{
			j++;
			Thread.sleep(1000);
		}
	}while(j<=100);
	driver.close();
	}
	//---------
	
	public void ClickOnHomeSecurityIcons(WebDriver driver,int tabular) throws InterruptedException
	{
		int HomeSecuritytabs=0;
		do
		{
			
			try
			{
		String tableiducontrol=driver.findElement(By.xpath("//table")).getAttribute("id");
		String tabletdidframe=driver.findElement(By.xpath(".//*[@id='"+ tableiducontrol+"']/tbody/tr/td/table")).getAttribute("id");
		String tabletdid1frame=driver.findElement(By.xpath(".//*[@id='"+ tableiducontrol+"']/tbody/tr/td/table[@id='"+ tabletdidframe+"']/tbody/tr[1]/td")).getAttribute("id");
		String HomeSecurityUppertabs=driver.findElement(By.xpath(".//*[@id='"+ tableiducontrol+"']/tbody/tr/td/table[@id='"+ tabletdidframe+"']/tbody/tr[1]/td[@id='"+ tabletdid1frame+"']/table/tbody/tr/td["+tabular+"]")).getAttribute("id");
		
		driver.findElement(By.xpath("//*[@id='"+ HomeSecurityUppertabs+"']")).click();
		break;
			}
			catch(Exception e)
			{
				System.out.println("Ucontrol Exception");
				HomeSecuritytabs++;
				Thread.sleep(1000);
			}
		}while(HomeSecuritytabs<=120);
	}
	
	public void RTPValidationsHomesecurity() throws InterruptedException
	{
		WebDriver driver5 = new FirefoxDriver();
		Map map=new HashMap();
		myTestClass tsss=new myTestClass();
		List<String> url=new ArrayList<String>();
		url.add("http://10.253.90.218:7901/cwf");
		url.add("http://10.252.115.108:7901/cwf/");
		 File dir = new File("C:\\OCRscreenshots\\HomeSecurityUIValidation");
		 map.put("Environment(0)", "DTDEV");
		map.put("Environment(1)", "QA4");
			
		 if(dir.exists())
		 {
			 
		 }
		 else
		 {
			 dir.mkdirs();
		 }
		
		Map val=new HashMap();
		for(int i=0;i<url.size();i++)
		{
		tsss.EnvLoginIn(url.get(i), driver5);
		 
		tsss.EnvMaintenanceConfiguration(driver5);
		val=tsss.EnvironmentConfigHomesecValidate(driver5);
		UcontrolValidation(driver5);
		String envname="Environment("+i+")";
		 String newdir=dir.toString().concat("\\").concat((String) map.get(envname)).concat("_").concat("Ucontrol").concat(".png");
		tsss.savescreenshot( driver5,newdir);
		OtherMiscellaneousValidation(driver5);
		String newdir1=dir.toString().concat("\\").concat((String) map.get(envname)).concat("_").concat("Othemiscellaneous").concat(".png");
		tsss.savescreenshot( driver5,newdir1);
		RTPValidation(driver5);
		
		String newdir2=dir.toString().concat("\\").concat((String) map.get(envname)).concat("_").concat("RTP").concat(".png");
		tsss.savescreenshot( driver5,newdir2);
		SaveConfigsValidation(driver5,val.get("TableId").toString());
		//driver5.close();
		
		driver5.switchTo().window(val.get("winValue").toString());
		}
		driver5.close();
		Thread.sleep(10000);
	}

}
