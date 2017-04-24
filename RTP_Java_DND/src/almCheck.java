

import java.io.BufferedReader;

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;

import java.io.PrintStream;

import java.io.Reader;

import com.parasoft.api.ScriptingContext;

 

public class almCheck {

    public static final boolean VERSIONED = false;

    public static String AlmConfigpath = "C:/ALMConfig/ConfigFiles/TestCaseId's";

    static String File = "";

    public static final String SIK = almCheck.nodeFromKey(almCheck.readfromConfigFile(), "SIK =", "RTPREG =").trim();

    public static final String RTP = almCheck.nodeFromKey(almCheck.readfromConfigFile(), "RTPREG =", "IGNORE =").trim();

    public static final String IGNORE = almCheck.nodeFromKey(almCheck.readfromConfigFile(), "IGNORE =", "XHSSIK =").trim();

    public static final String XHSSIK = almCheck.nodeFromKey(almCheck.readfromConfigFile(), "XHSSIK =", "VERSIONED =").trim();

    

 

    almCheck() {

    }

 

    public static final String readfromConfigFile() {

        FileReader in = null;

        try {

            in = new FileReader(String.valueOf(AlmConfigpath) + ".txt");

        }

        catch (FileNotFoundException e) {

            e.printStackTrace();

        }

        BufferedReader br = new BufferedReader(in);

        try {

            String data;

            while ((data = br.readLine()) != null) {

                File = String.valueOf(File) + data;

            }

        }

        catch (IOException e) {

            e.printStackTrace();

        }

        try {

            br.close();

        }

        catch (IOException e) {

            e.printStackTrace();

        }

        return File;

    }

 

    public static String nodeFromKey(String xmlStr, String str1, String str2) {

        int startPosition = 0;

        int endPosition = 0;

        startPosition = xmlStr.indexOf(str1) + str1.length();

        if (startPosition == -1) {

            System.out.printf("No Value found for ::%s\n", str1);
			System.out.printf("No  ::%s\n", str1);

            return null;

        }

        if (xmlStr.indexOf(str1) == -1) {

            System.out.printf("No Value found for ::%s\n", str1);

            return null;

        }

        endPosition = xmlStr.indexOf(str2, startPosition);

        if (endPosition == -1) {

            System.out.printf("No Value found for ::%s\n", str2);

            return null;

        }

        String resultval = xmlStr.substring(startPosition, endPosition);

        return resultval;

    }
    
    public String findtestcaseID(Object input,ScriptingContext c)
    {
    	String testcases=null;
    	if(c.getValue("Flow").equalsIgnoreCase("SIK"))
    	{
    		testcases=SIK;
    	}
    	else if(c.getValue("Flow").equalsIgnoreCase("RTP"))
    	{
    		testcases=RTP;
    	}
    	else if(c.getValue("Flow").equalsIgnoreCase("IGNORE"))
    	{
    		testcases=IGNORE;
    	}
    	else if(c.getValue("Flow").equalsIgnoreCase("XHSSIK"))
    	{
    		testcases=XHSSIK;
    	}
		return testcases;
    	
    }

}

 
