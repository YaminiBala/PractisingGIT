
package com.comcast.neto.alm;

import java.io.PrintStream;
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
        ALMTestResultUpdater.updateRunStatus("47113", "294100", "Passed");
      //  ALMTestResultUpdater.insertAttachment("69702", "163278", "C:/ALMConfig/ConfigFiles/AlmConfig.txt");
        System.out.println("Sysytem time is " + l);
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

