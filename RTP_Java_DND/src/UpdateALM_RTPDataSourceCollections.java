
import java.io.FileNotFoundException;
import java.util.HashMap;

import com.comcast.neto.alm.ALMTestResultUpdater;
import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class UpdateALM_RTPDataSourceCollections 
{

                LibraryRtp sL=new LibraryRtp();
                
                public void updateALMPositiveScenario( Object input,ScriptingContext c) throws Exception,ArrayIndexOutOfBoundsException,FileNotFoundException
                {
                	        HashMap readingValues=ALMTestResultUpdater.readFile("Z:/OM-OP/ALMConfig/ConfigFiles/TestSetId's.txt");
                                   String T2=sL.getTimeInstance();
                                   String TestCaseName=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: TestCaseName");
                                   Application.showMessage("Test CaseName is ::"+TestCaseName);                    
                                  // Application.showMessage("Time is ::"+c.get("T1").toString());
                                  // sL.UpdateDurationText(c.get("T1").toString(), T2,TestCaseName, "D:/Duration/Duration.txt");
                                  // Application.showMessage("Location where execution time is stored is ::"+"D:/Duration/Duration.txt");
                                  // String cycleid=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: TestSetID");
                                   String cycleid=(String) readingValues.get(c.getValue("flow").trim());
                                   String testid =c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: TestCaseID");
                                   Application.showMessage("TestSetID is::"+cycleid);
                                   Application.showMessage("TestCaseID is::"+testid);
                                   
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
                                	  Application.showMessage("test case Passed");
                                                                  ALMTestResultUpdater.updateRunStatus(cycleid,testid, "Passed");
                                                                  
                                                                // }
                                                
                                  }
                                                               
                }
                
                public void updateALMNegativeScenario( Object input,ScriptingContext c) throws Exception,ArrayIndexOutOfBoundsException,FileNotFoundException
                {
                	HashMap readingValues=ALMTestResultUpdater.readFile("Z:/OM-OP/ALMConfig/ConfigFiles/TestSetId's.txt");          
                	String T2=sL.getTimeInstance();
                    String TestCaseName=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: TestCaseName");
                    Application.showMessage("Test CaseName is ::"+TestCaseName);                    
                   // Application.showMessage("Time is ::"+c.get("T1").toString());
                    //sL.UpdateDurationText(c.get("T1").toString(), T2,TestCaseName, "D:/Duration/Duration.txt");
                    //Application.showMessage("Location where execution time is stored is ::"+"D:/Duration/Duration.txt");
                  //  String cycleid=c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: TestSetID");
                    String cycleid=(String) readingValues.get(c.getValue("flow").trim());
                    String testid =c.getValue("RTPDataSourceCollections", "RTP_UpDownInputs: TestCaseID");
                    Application.showMessage("TestSetID is::"+cycleid);
                    Application.showMessage("TestCaseID is::"+testid);
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
                            	  ALMTestResultUpdater.updateRunStatus(cycleid,testid, "No Run");
                            	  ALMTestResultUpdater.updateRunStatus(cycleid,testid, "Failed");
                                                
                              }

                                
                }
}
