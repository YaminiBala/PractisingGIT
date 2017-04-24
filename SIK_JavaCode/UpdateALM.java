
import com.comcast.neto.alm.ALMTestResultUpdater;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class UpdateALM 
{

	sikLibraryClass sL=new sikLibraryClass();
	
	public void updateALMPositiveScenario( Object input,ScriptingContext c) throws Exception,ArrayIndexOutOfBoundsException
	{
		
		   String cName=c.getContextName();
		   Application.showMessage("cName ::"+cName);
		  
		   String cName1=c.getContextType();
		   Application.showMessage("cName1 ::"+cName1);
		
		   String T2=sL.getTimeInstance();
		   String TestCaseName=c.getValue("dS_SIK", "Sik_Dotcom: TestCaseName");
		   Application.showMessage("Test CaseName is ::"+TestCaseName);
		   
		  
		   Application.showMessage("Time is ::"+c.get("T1").toString());
		   sL.UpdateDurationText(c.get("T1").toString(), T2,TestCaseName, "C:/Duration/Duration.txt");
		 
		   String cycleid=c.getValue("dS_SIK", "Sik_Dotcom: TestSetID");
		   String testid =c.getValue("dS_SIK", "Sik_Dotcom: TestCaseID");
		   Application.showMessage("testID is::"+testid);
		 // String[] TestCases=new String[100];
		 // TestCases=sL.TestCaseGroup(testid,c,input);
		 // Application.showMessage("TestCases is::"+TestCases[1]);
		  if(cycleid.isEmpty())
		  {
			  Application.showMessage("ALM no need to update for this iteration..!CycleID is Empty");
		  }
		  else if(testid.isEmpty())
		  {
			  Application.showMessage("ALM no need to update for this iteration..!TestID is Empty");
		  }
		  else
		  {
			  
			    /*for(int i=0; i<TestCases.length;i++)
				 {
			    	 */
			      
				  ALMTestResultUpdater.updateRunStatus(cycleid,testid, "Passed");
				  
				// }
			 
		  }
		 		
	}
	
	public void updateALMNegativeScenario( Object input,ScriptingContext c) throws Exception,ArrayIndexOutOfBoundsException
	{
		
		   String cName=c.getContextName();
		   Application.showMessage("cName ::"+cName);
		  
		   String cName1=c.getContextType();
		   Application.showMessage("cName1 ::"+cName1);
		  
		   String T2=sL.getTimeInstance();
		   String TestCaseName=c.getValue("DataTable", "TestCaseName");
		   Application.showMessage("Test CaseName is ::"+TestCaseName);
		   
		   String FileName=c.getValue("DataTable", "DurationCaptureFile");
		   Application.showMessage("DurationCaptureFile is ::"+FileName);
		   Application.showMessage("Time is ::"+c.get("T1").toString());
		   sL.UpdateDurationText(c.get("T1").toString(), T2,TestCaseName, "C:/Duration/Duration.txt");
		
		  String cycleid=c.getValue("dS_SIK", "Sik_Dotcom: TestSetID");
		  String testid =c.getValue("dS_SIK", "Sik_Dotcom: TestCaseID");
		  Application.showMessage("testID is::"+testid);
		  //String[] TestCases = new String[100];
		 // TestCases=sL.TestCaseGroup(testid,c,input);
		//  Application.showMessage("TestCases is::"+TestCases[1]);
		  if(cycleid.isEmpty())
		  {
			  Application.showMessage("ALM no need to update for this iteration..!CycleID is Empty");
		  }
		  else if(testid.isEmpty())
		  {
			  Application.showMessage("ALM no need to update for this iteration..!TestID is Empty");
		  }
		  else
		  {
			  Application.showMessage("Updating ALM...!");
			/*  for(int i=0; i<TestCases.length;i++)
				 {*/
				   ALMTestResultUpdater.updateRunStatus(cycleid, testid, "Failed");
				   
				  
				// } 
			 
		  }

		
	}
}
