package QAEnvironmentValidate;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import QAEnvironmentValidate.myTestClass;
public class MaintenanceSIKValidate 
{
	
	public void EnvironmentSIKCorpDef(String corp,WebDriver driver) throws InterruptedException
	{	
		//EnvBasicMethods test=new EnvBasicMethods();
		// test.ClickOnMenuBar(driver, 4);
	//	test.ClickOnMenuBarOptions(driver,6,"Maintenance");
		//test.ClickOnMenuBarSuBOptions(driver, 1);
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
		driver.findElement(By.xpath("//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[3]/td[1]/input")).sendKeys(corp);
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
	
	List<String> envcheck=new ArrayList<String>();
	envcheck.add(driver.findElement(By.xpath(".//*[@id='f.0000']")).getText().toString());	
	envcheck.add(driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[3]/td[3]/input")).getAttribute("value").toString());	
	Boolean Active=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[7]/td[1]/input")).isEnabled();
	envcheck.add(Active.toString());
	envcheck.add(driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[7]/td[3]/input")).getAttribute("value").toString());
	Boolean ComtracDDP=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[11]/td[1]/input[1]")).isSelected();
	envcheck.add(ComtracDDP.toString());
	Boolean CSGENI=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[11]/td[3]/input[1]")).isSelected();
	envcheck.add(CSGENI.toString());
	Boolean Dotcom=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[15]/td[1]/input")).isEnabled();
	envcheck.add(Dotcom.toString());
	Boolean MarketTrial=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[15]/td[3]/input[1]")).isSelected();
	envcheck.add(MarketTrial.toString());
	Boolean CSGSIKREMODEL=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[19]/td[1]/input[1]")).isSelected();
	envcheck.add(CSGSIKREMODEL.toString());
	Boolean DotcomSIKREMODEL=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[19]/td[3]/input[1]")).isSelected();
	envcheck.add(DotcomSIKREMODEL.toString());
	Boolean OPXHSQUEUE=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[23]/td[1]/input[1]")).isSelected();
	envcheck.add( OPXHSQUEUE.toString());
	Boolean opsCHEDULED=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[31]/td[1]/input[1]")).isSelected();
	envcheck.add(opsCHEDULED.toString());
	Boolean AddressUpdateEnabled=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[31]/td[3]/input[1]")).isSelected();
	envcheck.add(AddressUpdateEnabled.toString());
	Boolean isCVREnabled=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[35]/td[1]/input[1]")).isSelected();
	envcheck.add(isCVREnabled.toString());
	Boolean ReleaseB=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[35]/td[3]/input[1]")).isSelected();
	envcheck.add(ReleaseB.toString());
	envcheck.add(driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[39]/td[1]/input")).getAttribute("value").toString());	
	Boolean Homestreamcall=driver.findElement(By.xpath(".//*[@id='CwSearch']/table/tbody/tr/td/div/table/tbody/tr[39]/td[3]/input[1]")).isSelected();
	envcheck.add(Homestreamcall.toString());
	for (int c=0;c<envcheck.size();c++)
	{
		System.out.println(""+envcheck.get(c));
	}
	Thread.sleep(1000);
	driver.switchTo().defaultContent();
	}	
	
}
