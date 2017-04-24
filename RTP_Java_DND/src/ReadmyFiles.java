import java.io.*;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;
public class ReadmyFiles
{
   
	public void readWriteFile(Object input, ScriptingContext c) throws IOException
	{
		
	  String req1=getdatafromFile("FirstFileName", input, c);
	  String res1=getdatafromFile("2ndFileName", input, c);
	  String res2=getdatafromFile("2ndFileName", input, c);
	  String res3=getdatafromFile("2ndFileName", input, c);
	  String res4=getdatafromFile("2ndFileName", input, c);
	  String FileData="Request 1 is ::"+req1+"/n"+"Response 1"+"res1";
	  
	  WritedatatoFile("outPutFile", FileData, input, c);
	  
		
		
	}
	
	
	public String getdatafromFile(String FileName,Object input, ScriptingContext c) throws IOException
	{
		FileReader in =new FileReader("/C:/phtz/"+FileName+".txt");
		BufferedReader br = new BufferedReader(in);
		String data=null;
		String File=null;
		Application.showMessage("File Data is::");
		while ((data = br.readLine()) != null)   
		{
			File=data;
			Application.showMessage(File);
     
        }
		br.close();
		return File;
	}
	
	
	public void WritedatatoFile(String FileName,String FileData,Object input, ScriptingContext c) throws IOException
	{
		FileWriter out=new FileWriter("/C:/phtz/"+FileName+".txt");
		
		BufferedWriter bw=new BufferedWriter(out);
		bw.write(FileData);
	   		
	}
	
}
