/*
 * Decompiled with CFR 0_110.
 */
package qc.rest.examples.infrastructure;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;

public class Constants {
    public static final boolean VERSIONED = false;
    public static String AlmConfigpath = "C:/ALMConfig/ConfigFiles/AlmConfig";
    static String File = "";
    public static final String HOST = Constants.nodeFromKey(Constants.readfromConfigFile(), "HOST =", "PORT").trim();
    public static final String PORT = Constants.nodeFromKey(Constants.readfromConfigFile(), "PORT =", "USERNAME").trim();
    public static final String USERNAME = Constants.nodeFromKey(Constants.readfromConfigFile(), "USERNAME=", "PASSWORD").trim();
    public static final String PASSWORD = Constants.nodeFromKey(Constants.readfromConfigFile(), "PASSWORD=", "DOMAIN").trim();
    public static final String DOMAIN = Constants.nodeFromKey(Constants.readfromConfigFile(), "DOMAIN=", "PROJECT=").trim();
    public static final String PROJECT = Constants.nodeFromKey(Constants.readfromConfigFile(), "PROJECT=", "VERSIONED=").trim();

    private Constants() {
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
}

