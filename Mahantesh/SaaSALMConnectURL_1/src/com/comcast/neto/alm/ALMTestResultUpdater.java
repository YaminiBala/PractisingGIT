
package com.comcast.neto.alm;
import com.hp.alm.otaclient.*;
import com4j.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import qc.rest.examples.infrastructure.Constants;
import qc.rest.examples.infrastructure.Response;
import qc.rest.examples.prototype.Attachments;
import qc.rest.examples.prototype.EntityUpdater;
import qc.rest.examples.prototype.XmlLibraries;

public class ALMTestResultUpdater {
    static String almDomain = Constants.DOMAIN;
    static String almProject = Constants.PROJECT;
    static String UserName = Constants.USERNAME;
    static String Password = Constants.PASSWORD;

    public static void main(String[] args) throws Exception {
        XmlLibraries xL = new XmlLibraries();
        String l = xL.getsysTimeLog();
        readingAttachmentsfromTestResources();
       // HashMap readingvalue= readFile("C:/ALMConfig/ConfigFiles/OP_TestSetId.txt");
      //  System.out.println("Gertting value is"+readingvalue.entrySet());
       // ALMTestResultUpdater.updateRunStatus("47113", "294114", "Passed");
      //  ALMTestResultUpdater.insertAttachment("69702", "163278", "C:/ALMConfig/ConfigFiles/AlmConfig.txt");
        System.out.println("Sysytem time is " + l);
    }
    
    //---
    public static void readingAttachmentsfromTestResources() throws IOException
    {
      String url = "https://almcomcast.saas.hpe.com/qcbin";  
      String username = UserName;
      String password = Password;
      String domain = almDomain;
      String project = almProject;
      String   ResourceFolderID="ALMConfig";
      boolean b = false;
      boolean a = false;
      boolean c = false;

                File createDirectory = new File("C:\\ALMConfig\\ConfigFiles");
                File DTATestsetFile = new File("C:\\ALMConfig\\ConfigFiles\\DTA_TestSetId.txt");
                File OPTestsetFile = new File("C:\\ALMConfig\\ConfigFiles\\OP_TestSetId.txt");
                try{
                if (!createDirectory.exists()) {
			b = createDirectory.mkdirs();
		}
                if (DTATestsetFile.exists() && OPTestsetFile.exists()) {
			a = DTATestsetFile.delete();
                                c=OPTestsetFile.delete();
		}
                else if (DTATestsetFile.exists() && !OPTestsetFile.exists())
                {
                    a = DTATestsetFile.delete();
                }
                else if (OPTestsetFile.exists() && !DTATestsetFile.exists())
                {
                     c=OPTestsetFile.delete();
                }
                }
                catch(Exception e){
    		
    		e.printStackTrace();
    		
    	       }
                
                
                //------ALM Connection
                
               ITDConnection6 itdc = ClassFactory.createTDConnection();
               itdc.initConnectionEx(url);
               itdc.connectProjectEx(domain, project, username, password);
               IQCResourceFolderFactory resourceFolderFactory = itdc.qcResourceFolderFactory().queryInterface(IQCResourceFolderFactory.class);
               IList folders = resourceFolderFactory.newList("");
               for(Com4jObject rec : folders)
               {
                IQCResourceFolder resourceFolder = rec.queryInterface(IQCResourceFolder.class);
                //  System.out.println("resource folder Id:::"+resourceFolder.name());
                List<String> listresources=new ArrayList<String>();
                listresources.add(resourceFolder.name());  
              if(resourceFolder.name().toString().equals(ResourceFolderID))
               {   
              Com4jObject objResourceFactory = resourceFolder.qcResourceFactory();
             IQCResourceFactory resourceFactory = objResourceFactory.queryInterface(IQCResourceFactory.class);
             IList resources = resourceFactory.newList("");
              for(Com4jObject objResource : resources)
              {
                IQCResource resource  = objResource.queryInterface(IQCResource.class);  ;
                IResourceStorage resourceStorage = resource.queryInterface(IResourceStorage.class);                
                resourceStorage.downloadResource("C:\\ALMConfig\\ConfigFiles", true);
              }
    }
     
}
     
    }
    
          public static HashMap readFile(String path) throws IOException {
          BufferedReader br = new BufferedReader(new FileReader(path));
          List<String> added=new ArrayList<String>();
          List<String> Groupservices=new ArrayList<String>();
          int j=0;
          int Equalsymbol=0;
          String check="=";
          String dr = null;
          HashMap map = new HashMap();
          try {
              StringBuilder sb = new StringBuilder();
              String line = br.readLine();
              

              while (line != null ) 
              {
                  line = line.trim();
                  sb.append(line+"=");
                  line = br.readLine();
                
              }
              for(int sym=0;sym<=Equalsymbol;sym++)
              {
                  String value="=";
                   check=check.concat("=").trim();
                  if(sb.toString().contains(check))
                  {
                      dr=sb.toString().replaceAll(check, "").trim();                    
                      Equalsymbol++;
                  }
                  else
                  {
                      dr=sb.toString().trim();
                  }
              }
              System.out.println("added"+dr);
              added.add(dr.trim());             
              for(int i=0;i<added.size();i++)
              {
              Groupservices=Arrays.asList(added.get(i).split("="));          
               }
             
              for(int k=0;k<added.size();k++)
              {   
                  
              map.put(Groupservices.get(k+j).trim(), Groupservices.get(k+j+1).trim());              
              j++;        
              }
              
              return map;
          } finally {
              br.close();
          }
      }

    
    
    //----

    public static void updateRunStatus(String cycleid, String testid, String status) throws Exception {
        XmlLibraries xL = new XmlLibraries();
        String lastupdateTime = xL.getsysTime();
        EntityUpdater rest = new EntityUpdater(almDomain, almProject);
        
        rest.loginCall();
        Response instanceXML = rest.getInstanceId(cycleid, testid);
        String outXML = instanceXML.toString();
        String XML_wf = xL.RemoveNameSpaces(outXML);
        String Instanceid = xL.GetValueByXPath(XML_wf, "/Entities/Entity/Fields/Field/Value");
        System.out.println("Test Instanceid is ::" + Instanceid);
        rest.updateTestCase(Instanceid, "status", status);
        rest.logoutCall();
    }

    public static void insertAttachment(String cycleid, String testid, String attachmentPath) throws Exception {
        byte[] filedata;
        XmlLibraries xL = new XmlLibraries();
        Attachments at = new Attachments();
        String lastupdateTime = xL.getsysTimeLog();
        EntityUpdater rest = new EntityUpdater(almDomain, almProject);
        rest.loginCall();
        Response instanceXML = rest.getInstanceIdFromRun(cycleid, testid);
        String outXML = instanceXML.toString();
        String XML_wf = xL.RemoveNameSpaces(outXML);
        String count = xL.nodeFromKey(XML_wf, "TotalResults=\"", "\"><Entity Type=\"run\">");
        System.out.println("No:of Runs are::" + count);
        String RunsID = xL.GetValueByXPath(XML_wf, "/Entities/Entity[" + count + "]/Fields/Field[4]/Value");
        System.out.println("RunsID is ::" + RunsID);
        if (attachmentPath.contains(".txt")) {
            filedata = at.convertTobytes(attachmentPath);
        } else {
            String attachmentPathTemp = String.valueOf(attachmentPath) + ".txt";
            filedata = at.convertTobytes(attachmentPathTemp);
        }
        rest.CreateTCAttachment(RunsID, filedata, "Log_" + lastupdateTime + ".txt");
        rest.logoutCall();
    }

    public void writeDuration(String cycleid, String testid, String TimeTaken) throws Exception {
        XmlLibraries xL = new XmlLibraries();
        Attachments at = new Attachments();
        String lastupdateTime = xL.getsysTimeLog();
        EntityUpdater rest = new EntityUpdater(almDomain, almProject);
        rest.loginCall();
        Response instanceXML = rest.getInstanceIdFromRun(cycleid, testid);
        String outXML = instanceXML.toString();
        String XML_wf = xL.RemoveNameSpaces(outXML);
        String RunsID = xL.GetValueByXPath(XML_wf, "/Entities/Entity/Fields/Field[5]/Value");
        System.out.println("RunsID is ::" + RunsID);
        byte[] filedata = TimeTaken.getBytes();
        rest.RunDuration(RunsID, filedata);
        rest.logoutCall();
    }

    public static String[] TestCaseGroup(String TestCasedata) {
        String[] TestCase = TestCasedata.split(",");
        int i = 0;
        while (i < TestCase.length) {
            System.out.println(String.valueOf(i) + " TestCaseID::" + TestCase[i]);
            ++i;
        }
        return TestCase;
    }
}

