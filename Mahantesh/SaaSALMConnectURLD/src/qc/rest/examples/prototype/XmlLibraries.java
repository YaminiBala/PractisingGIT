/*
 * Decompiled with CFR 0_110.
 */
package qc.rest.examples.prototype;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlLibraries {
    public String RemoveNameSpaces(String sIpStr) {
        String sRet = sIpStr;
        Pattern pP = Pattern.compile("<[a-zA-Z]*:");
        Matcher mM = pP.matcher(sRet);
        sRet = mM.replaceAll("<");
        pP = Pattern.compile("</[a-zA-Z]*:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll("</");
        pP = Pattern.compile(" xmlns.*?(\"|').*?(\"|')");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        pP = Pattern.compile(" xsi:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        pP = Pattern.compile("sik:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        pP = Pattern.compile("bfcopcom:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        pP = Pattern.compile("comt:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        pP = Pattern.compile("SignatureRequired=\"FALSE\"");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        pP = Pattern.compile("type=\" SingleComponentProduct\"");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
        return sRet;
    }

    public String getsysTime() {
        Date todayDate = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd H:m:s");
        todayDateFormat.setTimeZone(timeZone);
        String strTodayDate = todayDateFormat.format(todayDate);
        System.out.println("Todays Date as per EST is::" + strTodayDate);
        return strTodayDate;
    }

    public String getsysTimeLog() {
        Date todayDate = new Date();
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat hrs = new SimpleDateFormat("H");
        SimpleDateFormat min = new SimpleDateFormat("m");
        SimpleDateFormat sec = new SimpleDateFormat("s");
        hrs.setTimeZone(timeZone);
        min.setTimeZone(timeZone);
        sec.setTimeZone(timeZone);
        todayDateFormat.setTimeZone(timeZone);
        String strTodayDate = todayDateFormat.format(todayDate);
        String LogDate = String.valueOf(strTodayDate) + "_" + hrs.format(todayDate) + "hrs" + min.format(todayDate) + "min" + sec.format(todayDate) + "sec";
        System.out.println("Todays Date as per EST is::" + LogDate);
        return LogDate;
    }

    public String testDuration(String strtTime, String endTime) throws ParseException {
        String durationTime = null;
        SimpleDateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd H:m:s");
        Date date1 = todayDateFormat.parse(strtTime);
        Date date2 = todayDateFormat.parse(endTime);
        long diff = date2.getTime() - date1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / 60000 % 60;
        long diffHours = diff / 3600000 % 24;
        long diffDays = diff / 86400000;
        System.out.print(String.valueOf(diffDays) + " days, ");
        System.out.print(String.valueOf(diffHours) + " hours, ");
        System.out.print(String.valueOf(diffMinutes) + " minutes, ");
        System.out.print(String.valueOf(diffSeconds) + " seconds.");
        durationTime = String.valueOf(diffSeconds);
        return durationTime;
    }

    public String GetValueByXPath(String sXml, String sxpath) throws SAXException, IOException, XPathExpressionException {
        String val = null;
        try {
            InputSource source = new InputSource(new StringReader(sXml));
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setIgnoringElementContentWhitespace(false);
            Document doc = null;
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(source);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile(sxpath);
            Node oNode = (Node)expr.evaluate(doc, XPathConstants.NODE);
            val = oNode.getTextContent();
            xpath = null;
            expr = null;
            oNode = null;
            doc = null;
            builder = null;
        }
        catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        return val;
    }

    public String nodeFromKey(String xmlStr, String str1, String str2) {
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
        System.out.printf("Result value is :: %s\n", resultval);
        return resultval;
    }
}

