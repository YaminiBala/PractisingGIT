
import java.util.HashMap;

import com.comcast.neto.alm.ALMTestResultUpdater;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class UpdateALM_RTP 
{

	LibraryRtp sL=new LibraryRtp();

	
	
	public void updateALMPositiveScenario( Object input,ScriptingContext c) throws Exception,ArrayIndexOutOfBoundsException
	{
		
		HashMap readingValues=ALMTestResultUpdater.readFile("Z:/OM-OP/ALMConfig/ConfigFiles/TestSetId's.txt");
	
		   String T2=sL.getTimeInstance();
		   String TestCaseName=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseName");
		   Application.showMessage("Test CaseName is ::"+TestCaseName);
		   
		  
		   //Application.showMessage("Time is ::"+c.get("T1").toString());
		   //sL.UpdateDurationText(c.get("T1").toString(), T2,TestCaseName, "C:/Duration/Duration.txt");
		   String cycleid=(String) readingValues.get(c.getValue("flow").trim());
		  // String cycleid=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestSetID");
		   String testid =c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseID");
		   Application.showMessage("testID is::"+testid);
		   Application.showMessage("cycleid is::"+cycleid);
		 // String[] TestCases=new String[100];
		 // TestCases=sL.TestCaseGroup(testid,c,input);
		 // Application.showMessage("TestCases is::"+TestCases[1]);
		  if(cycleid.isEmpty() || cycleid.equalsIgnoreCase("null"))
		  {
			  Application.showMessage("ALM no need to update for this iteration..!CycleID is Empty");
		  }
		  else if(testid.isEmpty() ||testid.equalsIgnoreCase("null") )
		  {
			  Application.showMessage("ALM no need to update for this iteration..!TestID is Empty");
		  }
		  else
		  {
			  
			    /*for(int i=0; i<TestCases.length;i++)
				 {
			    	 */
			      ALMTestResultUpdater.updateRunStatus(cycleid,testid, "No Run");
				  ALMTestResultUpdater.updateRunStatus(cycleid,testid, "Passed");
				  
				// }
				/*  if(newCycleId.isEmpty())
				  {
					  Application.showMessage("ALM no need to update for this iteration..!CycleID is Empty");
				  }
				  else if(testid.isEmpty())
				  {
					  Application.showMessage("ALM no need to update for this iteration..!TestID is Empty");
				  }
				  else
				  {
					  
					    
					      ALMTestResultUpdater.updateRunStatus(newCycleId,testid, "No Run");
						  ALMTestResultUpdater.updateRunStatus(newCycleId,testid, "Passed");
				  }*/
			 
		  }
		 		
	}
	
	public void updateALMNegativeScenario( Object input,ScriptingContext c) throws Exception,ArrayIndexOutOfBoundsException
	{
		
		HashMap readingValues=ALMTestResultUpdater.readFile("Z:/OM-OP/ALMConfig/ConfigFiles/TestSetId's.txt");
		   String cName=c.getContextName();
		   Application.showMessage("cName ::"+cName);
		  
		   String cName1=c.getContextType();
		   Application.showMessage("cName1 ::"+cName1);
		  
		   String T2=sL.getTimeInstance();
		   String TestCaseName=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseName");
		   Application.showMessage("Test CaseName is ::"+TestCaseName);
		   
		  
		//   Application.showMessage("Time is ::"+c.get("T1").toString());
		//   sL.UpdateDurationText(c.get("T1").toString(), T2,TestCaseName, "C:/Duration/Duration.txt");
		   String cycleid=(String) readingValues.get(c.getValue("flow").trim());
		  // String cycleid=c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestSetID");
		   String testid =c.getValue("RTPNormalScenariosdS", "RTP_InputParams: TestCaseID");
		   Application.showMessage("testID is::"+testid);
		   Application.showMessage("cycleid is::"+cycleid);
		  //String[] TestCases = new String[100];
		 // TestCases=sL.TestCaseGroup(testid,c,input);
		//  Application.showMessage("TestCases is::"+TestCases[1]);
		   if(cycleid.isEmpty() || cycleid.equalsIgnoreCase("null"))
			  {
				  Application.showMessage("ALM no need to update for this iteration..!CycleID is Empty");
			  }
			  else if(testid.isEmpty() ||testid.equalsIgnoreCase("null") )
			  {
				  Application.showMessage("ALM no need to update for this iteration..!TestID is Empty");
			  }
		  else
		  {
			  Application.showMessage("Updating ALM...!");
			/*  for(int i=0; i<TestCases.length;i++)
				 {*/
				   ALMTestResultUpdater.updateRunStatus(cycleid, testid, "Failed");
				   
				  
			//} 
			 
		  }

		
	}
}
