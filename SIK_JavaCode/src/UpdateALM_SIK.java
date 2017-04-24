import java.util.HashMap;

import com.comcast.neto.alm.ALMTestResultUpdater;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;




	public class UpdateALM_SIK 
	{

	       sikLibraryClass lR=new sikLibraryClass();
	       
	       public void updateALMPositiveScenario( Object input,ScriptingContext c) throws Exception,ArrayIndexOutOfBoundsException
	       {


	    	   HashMap readingValues=ALMTestResultUpdater.readFile("Z:/OM-OP/ALMConfig/ConfigFiles/TestSetId's.txt");          
	            String T2=lR.getTimeInstance();
	            Application.showMessage("T2 is ::"+T2);
	              String TestCaseName=c.getValue("SwivelCSG", "sik_SwivelCSG: TestCaseName");
	              Application.showMessage("TestCaseName is::"+TestCaseName);
	              Application.showMessage("Start time is:: "+c.get("T1").toString());
	            //lR.UpdateDurationText(c.get("T1").toString(), T2,TestCaseName, "C:/Duration/Duration.txt");
	              String cycleid=(String) readingValues.get(c.getValue("flow").trim());
	               // String cycleid=c.getValue("SwivelCSG", "sik_SwivelCSG: TestSetID");
	                String testid =c.getValue("SwivelCSG", "sik_SwivelCSG: TestCaseID");
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
	                	//"No Run"
	                	         ALMTestResultUpdater.updateRunStatus(cycleid,testid, "No Run");
	                             ALMTestResultUpdater.updateRunStatus(cycleid,testid, "Passed");
	                             
	                           // }
	                        
	                }
	                           
	       }
	       
	       public void updateALMNegativeScenario( Object input,ScriptingContext c) throws Exception,ArrayIndexOutOfBoundsException
	       {
	    	   HashMap readingValues=ALMTestResultUpdater.readFile("Z:/OM-OP/ALMConfig/ConfigFiles/TestSetId's.txt");          
	              String T2=lR.getTimeInstance();
	                     String TestCaseName=c.getValue("SwivelCSG", "sik_SwivelCSG: TestCaseName");
	                     Application.showMessage("TestCaseName is::"+TestCaseName);
	               //lR.UpdateDurationText(c.get("T1").toString(), T2,TestCaseName, "C:/Duration/Duration.txt");
	                     String cycleid=(String) readingValues.get(c.getValue("flow").trim());
	               // String cycleid=c.getValue("SwivelCSG", "sik_SwivelCSG: TestSetID");
	                String testid =c.getValue("SwivelCSG", "sik_SwivelCSG: TestCaseID");
	                Application.showMessage("testID is::"+testid);
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
	                              
	                             
	                           // } 
	                     
	                }

	              
	       }
	


}
