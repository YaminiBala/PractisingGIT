package com.comcast.bom.data;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

public class XML_Parsing {
	public static String RemoveNameSpaces(String sIpStr) 
	{
        String sRet = sIpStr;
        Pattern pP = Pattern.compile("<[a-zA-Z]*:");
        Matcher mM = pP.matcher(sRet);
        sRet = mM.replaceAll("<");

        pP = Pattern.compile("</[a-zA-Z]*:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll("</");
        
        pP = Pattern.compile(" xmlns.*?(\"|\').*?(\"|\')");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" "); 

        pP = Pattern.compile(" xsi:");
        mM = pP.matcher(sRet);
        sRet = mM.replaceAll(" ");
       // bfcopcom
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
        
        pP = Pattern.compile("<[a-zA-Z]*[0-9]*:");
        mM = pP.matcher(sRet);
       sRet = mM.replaceAll("<");

       pP = Pattern.compile("</[a-zA-Z]*[0-9]*:");
       mM = pP.matcher(sRet);
       sRet = mM.replaceAll("</");
        
        return sRet;
   }
	
	public static String GetValueByXPath(String sXml, String sxpath) throws SAXException, IOException, XPathExpressionException,NullPointerException
	 {
		 String val=null;
		 try
		 {
			 //sXml.replaceAll(" xsi:", " ");
		     InputSource source = new InputSource(new StringReader(sXml));
		     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		     factory.setNamespaceAware(false);    
		     factory.setIgnoringElementContentWhitespace(false);
	      
		     DocumentBuilder builder;        
		     Document doc = null;
		 
			 builder = factory.newDocumentBuilder();  
			 doc = builder.parse(source);
			 XPathFactory xpathFactory = XPathFactory.newInstance();            
		     XPath xpath = xpathFactory.newXPath(); 
		     XPathExpression expr = xpath.compile(sxpath);
		     Node oNode= (Node) expr.evaluate(doc,XPathConstants.NODE);
		     if( oNode==null)
		     {
		    	 val="NOSIK"; 
		     }
		     else
		     {
		    	 val =oNode.getTextContent(); 
	 
		     }
		   
		     xpath=null; expr=null; oNode=null; doc=null; builder=null;
		     
		 }
		 catch (NullPointerException | ParserConfigurationException | SAXException | IOException e) 
		 {           
			 e.printStackTrace();       
		 }
		
			 return val;
		 	 
	 }
}
