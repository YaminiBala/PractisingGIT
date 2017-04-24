
package com.comcast.neto.alm;

import java.io.BufferedReader;
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

    public static void main(String[] args) throws Exception {
        XmlLibraries xL = new XmlLibraries();
        String l = xL.getsysTimeLog();
        

       
     //  HashMap readingvalue=readFile("W:/TestCaseId's.txt");
       // System.out.println("Gertting value is"+readingvalue.entrySet());
       // System.out.println("Gertting value is"+readingvalue.get("SIK"));

       // ALMTestResultUpdater.updateRunStatus("47113", "294114", "Passed");
        ALMTestResultUpdater.readAttachment("50690", "258869", "C:/ALMConfig/ConfigFiles/AlmConfig.txt");
        System.out.println("Sysytem time is " + l);
        
    }
    
          public static HashMap readFile(String path) throws IOException {
          BufferedReader br = new BufferedReader(new FileReader(path));
          List<String> added=new ArrayList<String>();
          List<String> Groupservices=new ArrayList<String>();
          int j=0;
          HashMap map = new HashMap();
          try {
              StringBuilder sb = new StringBuilder();
              String line = br.readLine();

              while (line != null) {
                  sb.append(line+" = ");
                  added.add(sb.toString());
                  sb.append("\n");
                  line = br.readLine();
              }
            
              
              for(int i=0;i<added.size();i++)
              {
              Groupservices=Arrays.asList(added.get(i).split(" = "));          
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
        System.out.println("XML_wf is ::" + XML_wf);
        String count = xL.nodeFromKey(XML_wf, "TotalResults=\"", "\"><Entity Type=\"run\">");
        System.out.println("No:of Runs are::" + count);
        String RunsID = xL.GetValueByXPath(XML_wf, "/Entities/Entity[" + count + "]/Fields/Field[26]/Value");
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
    
    public static void readAttachment(String cycleid, String testid, String attachmentPath) throws Exception {
        byte[] filedata;
        XmlLibraries xL = new XmlLibraries();
        Attachments at = new Attachments();
        String lastupdateTime = xL.getsysTimeLog();
        EntityUpdater rest = new EntityUpdater(almDomain, almProject);
        rest.loginCall();
        Response instanceXML = rest.getInstanceIdFromRun(cycleid, testid);
        String outXML = instanceXML.toString();
        String XML_wf = xL.RemoveNameSpaces(outXML);
        System.out.println("XML_wf is ::" + XML_wf);
        String count = xL.nodeFromKey(XML_wf, "TotalResults=\"", "\"><Entity Type=\"run\">");
        System.out.println("No:of Runs are::" + count);
        String RunsID = xL.GetValueByXPath(XML_wf, "/Entities/Entity[" + count + "]/Fields/Field[26]/Value");
        System.out.println("RunsID is ::" + RunsID);
        if (attachmentPath.contains(".txt")) {
            filedata = at.convertTobytes(attachmentPath);
        } else {
            String attachmentPathTemp = String.valueOf(attachmentPath) + ".txt";
            filedata = at.convertTobytes(attachmentPathTemp);
        }
        rest.CreateTCAttachment(RunsID, filedata, "Log_" + lastupdateTime + ".txt");
        rest.readTCAttachment(RunsID, filedata, "Log_" + lastupdateTime + ".txt");
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

