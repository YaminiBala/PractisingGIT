package com;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

/**
 * Servlet implementation class LogExtract
 */
@WebServlet("/LogExtract")
public class LogExtract extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int dbcon;
	int envir;
	String envname;
	String startDate;
	String EndDate;
	String Accoutnumber;
	String location;
	String filename;
  File file;
  int download=0;
	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public Connection connection = null;
	public String xml1 = null;
	String xmldata1 = "receive_data";
	String recievedata = null;
	String xmldata2 = "SEND_DATA";
	public String host;
	public String username;
	public String password;
	public String port;
	public String sid;
	ResultSet rs;
	public Properties prop;
	public String trackingNumber;

	public LogExtract() {
		super();

		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public  void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/*LogExtract logExt = new LogExtract();
		String filePath = "D://logExtractor";
		String filename = "";
		File file;
		String logExtractor = "-------------------------Log Extractor------------------------";
		String Completion = "----------------------------------------------------------------";
		String sendData = "**********************SendData*****************************";
		String receiveData = "******************receiveData*********************************";
		String br = "\n";
		String rowMsgID = "rowMsgID:::";
		String OperationName = "Operation Name:::";
		Date date = new Date();
		Format formatter = new SimpleDateFormat("YMMdHMS");
		File dir = new File(filePath);
		dir.mkdirs();
		if (filename != null && !filename.isEmpty()) {
			file = new File(dir, filename + "_" + formatter.format(date) + ".txt");
		} else {
			file = new File(dir, "WebServiceLog_" + formatter.format(date) + ".txt");
		}
		OutputStream outputStream = new FileOutputStream(file);

		outputStream.write(logExtractor.getBytes());
		outputStream.write(br.getBytes());
		outputStream.write(br.getBytes());
		try {

			ResultSet result = logExt.dbconnection("QA1");
			
				while (result.next()) {

					String rwmsg = result.getString(1);
					outputStream.write(rowMsgID.getBytes());
					outputStream.write(rwmsg.getBytes());
					outputStream.write(br.getBytes());
					String operation = result.getString(4);
					outputStream.write(OperationName.getBytes());
					outputStream.write(operation.getBytes());
					outputStream.write(br.getBytes());
					outputStream.write(br.getBytes());

					Blob blob_receiveData = result.getBlob("receive_data");
					Blob blob_sendData = result.getBlob("send_data");
					byte[] bdata_receive = blob_receiveData.getBytes(1, (int) blob_receiveData.length());
					byte[] blob_send = blob_sendData.getBytes(1, (int) blob_sendData.length());
					InputStream inputStream_receiveData = blob_receiveData.getBinaryStream();
					InputStream inputStream_sendData = blob_sendData.getBinaryStream();

					while ((inputStream_sendData.read(blob_send)) != -1) {
						outputStream.write(sendData.getBytes());
						outputStream.write(br.getBytes());
						outputStream.write(br.getBytes());
						outputStream.write(blob_send);

						outputStream.write(br.getBytes());
						outputStream.write(br.getBytes());

					}
					while ((inputStream_receiveData.read(bdata_receive)) != -1) {
						outputStream.write(receiveData.getBytes());
						outputStream.write(br.getBytes());
						outputStream.write(br.getBytes());
						outputStream.write(bdata_receive);

						outputStream.write(br.getBytes());
						outputStream.write(br.getBytes());

					}
					outputStream.write(Completion.getBytes());
					outputStream.write(br.getBytes());
					outputStream.write(br.getBytes());

					outputStream.flush();
					inputStream_receiveData.close();
					inputStream_sendData.close();

					System.out.println("File saved");

				}
				outputStream.close();
				connection.close();
			
		} catch (SQLException ex) {

			ex.printStackTrace();
		} catch (IOException ex) {

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */}
		
	public void getinputValues(String envname,String startDate,String EndDate,String Accoutnumber,String location,String filename,String trackingNumber) throws ParseException, java.text.ParseException, IOException
	{
		this.envname=envname;
		this.startDate=startDate;
		this.EndDate=EndDate;
		this.Accoutnumber=Accoutnumber;
		this.location=location;
		this.filename=filename;
		this.trackingNumber=trackingNumber;
		System.out.println("location::"+location);
		System.out.println("location::"+filename);
		calendarformatdate(startDate,EndDate);
		if(envname.contains("PERF"))
		{
		storeLogs(envname,location,filename,"","","",trackingNumber);
		}
		else
		{
			storeLogs(envname,location,filename,Accoutnumber,startDate,EndDate,trackingNumber);	
		}
		
		
		
		
	}
		public void calendarformatdate(String startdate,String enddate) throws ParseException, java.text.ParseException
		{
			if(startdate==null || startdate.isEmpty() || enddate==null || enddate.isEmpty())
			{
				System.out.println("Either Start date or enddate is empty");
				dbcon=0;
				
			}
			else{
			 startDate=startdate.replace("T"," ");
			System.out.println(startDate);
			EndDate=enddate.replace("T", " ");
					SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 Date stdate = sdfSource.parse(startDate);
			 Date endate=sdfSource.parse(EndDate);
			SimpleDateFormat print = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startDate = print.format(stdate);
					  EndDate=print.format(endate);
			 System.out.println(startDate);
			 System.out.println(EndDate);
			 dbcon=1;
			
			}
			
		}
		public void storeLogs(String envname,String location,String filenme,String accountnumber,String startdate,String endDate,String trackingNumber) throws IOException
		{
			LogExtract logExt = new LogExtract();
			String filePath = location;
			if(filePath.contains("/")||filePath.contains("\""))
					{
				filePath.replaceAll(filePath, "//")	;
					}
			System.out.println(filePath);
			
			String filename = filenme;
			
			String logExtractor = "-------------------------Log Extractor------------------------";
			String Completion = "----------------------------------------------------------------";
			String sendData = "**********************SendData*****************************";
			String receiveData = "******************receiveData*********************************";
			String br = "\n";
			String rowMsgID = "rowMsgID:::";
			 String OperationName = "Operation Name:::";
			Date date = new Date();
			Format formatter = new SimpleDateFormat("0");
			File dir = new File(filePath);
			dir.mkdirs();
			if (filename != null && !filename.isEmpty()) {
				file = new File(dir, filename + "_" + formatter.format(date) + ".txt");
			} else if(accountnumber!=null&& !accountnumber.equalsIgnoreCase("")) {
				file = new File(dir, accountnumber+".txt");
			}
			else if(startdate!=null&&!startdate.equalsIgnoreCase("")&&endDate!=null && !endDate.equalsIgnoreCase(""))
			{
				String startdate1=startdate.replaceAll(":","");
				String endDate1=endDate.replaceAll(":","");
				file = new File(dir, startdate1+"_"+endDate1+".txt");
			}
			else if(trackingNumber!=null && !trackingNumber.equalsIgnoreCase(""))
			{
				file = new File(dir, trackingNumber+".txt");	
			}
			/*else
			{
				file = new File(dir, "logs_" + formatter.format(date) + ".txt");
			}*/
			OutputStream outputStream = new FileOutputStream(file);
			InputStream inputStream_receiveData =null;
			InputStream inputStream_sendData =null;

			outputStream.write(logExtractor.getBytes());
			outputStream.write(br.getBytes());
			outputStream.write(br.getBytes());
			try {

				//rs= logExt.dbconnection(envname,accountnumber,startdate,endDate);
				String sql = null;
				readconfigfile(envname);
				String accountNum=accountnumber.trim();
				System.out.println("jdbc:oracle:thin:@" + host + ":" + port + ":" + sid +username +password);
						Class.forName("oracle.jdbc.driver.OracleDriver");

					
					connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + sid +"",username,password);
					//String sql = "select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='2015-08-12 03:30:04' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='2015-08-12 04:37:04'";
					//String sql = "select * from cwmessagelog where to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
					if(envname.contains("DTA")|| envname.equalsIgnoreCase("COMMON")||envname.contains("SYMLITE"))
					{
						if((startdate.isEmpty()&& endDate.isEmpty())||startdate==null && endDate==null )
						{
							sql="select * from cwmessagelog where USER_DATA1 ='"+accountNum+"'";
						}
						else if(endDate.isEmpty()|| endDate==null)
						{
						 sql="select * from cwmessagelog where USER_DATA1 ='"+accountNum+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'";
						}
						else if(accountNum.isEmpty()||accountNum==null)
						{
						
						sql="select * from cwmessagelog where  to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
						}
						else
						{
						sql="select * from cwmessagelog where USER_DATA1 ='"+accountNum+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
						}
						//String sql = "select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
						}
					else if (envname.contains("PERF"))
					{
						System.out.println("trackingnumber::"+trackingNumber);
						
						sql="select * from om_report_trans_details ortdetails left join om_report_trans_data ortdata on ortdetails.td_id = ortdata.td_ref_id join cwmessagelog cwlog on ortdetails.MSG_ID = cwlog.msgid where ortdetails.tracking_id ='"+trackingNumber+"' ORDER BY MSG_ID";
						
						//sql="select * from om_report_trans_details where tracking_ID ='"+trackingNumber+"'";
						//sql="select * from cwmessagelog where MSGID in(select MSGID from om_report_trans_details where MSGID='31293131723857')";
						
						
						/*if((startdate.isEmpty()&& endDate.isEmpty())||startdate==null && endDate==null )
						{
							sql="select * from om_report_trans_details where tracking_ID ='"+trackingNumber+"'";
						}
						else if(endDate.isEmpty()|| endDate==null)
						{
						 sql="select * from om_report_trans_details where tracking_ID ='"+trackingNumber+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'";
						}
						else if(trackingNumber.isEmpty()||trackingNumber==null)
						{
						
						sql="select * from cwmessagelog where  to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
						}
						else
						{
						sql="select * from om_report_trans_details where USER_DATA1 ='"+trackingNumber+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
						}
						//String sql = "select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
						}*/
					}
					
					else
					{
					if((startdate.isEmpty()&& endDate.isEmpty())||startdate==null && endDate==null )
					{
						sql="select * from cwmessagelog where USER_DATA2 ='"+accountNum+"'";
					}
					else if(endDate.isEmpty()|| endDate==null)
					{
					 sql="select * from cwmessagelog where USER_DATA2 ='"+accountNum+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'";
					}
					else if(accountNum.isEmpty()||accountNum==null)
					{
					
					sql="select * from cwmessagelog where  to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
					}
					else
					{
					sql="select * from cwmessagelog where USER_DATA2 ='"+accountNum+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
					}
					//String sql = "select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
					}
					PreparedStatement statement = connection.prepareStatement(sql);

					rs = statement.executeQuery();	
					
				
					while (rs.next()) {
						
                    System.out.println(rs.getString(1));
                    
						String rwmsg = rs.getString(1);
						outputStream.write(rowMsgID.getBytes());
						outputStream.write(rwmsg.getBytes());
						outputStream.write(br.getBytes());
						//String operation=rs.getString(4);
						String operation;
						if(trackingNumber!=null&&trackingNumber.isEmpty())
						{
							operation = rs.getString(4);
						}
						else
						{
						operation = rs.getString(3);
						}
						outputStream.write(OperationName.getBytes());
						outputStream.write(operation.getBytes());
						outputStream.write(br.getBytes());
						outputStream.write(br.getBytes());
 if(rs.getBlob("receive_data")==null) 
 {
	 Blob blob_sendData = rs.getBlob("send_data");
	 byte[] blob_send = blob_sendData.getBytes(1, (int) blob_sendData.length());
	  inputStream_sendData = blob_sendData.getBinaryStream();

		while ((inputStream_sendData.read(blob_send)) != -1) {
			outputStream.write(sendData.getBytes());
			outputStream.write(br.getBytes());
			outputStream.write(br.getBytes());
			outputStream.write(blob_send);

			outputStream.write(br.getBytes());
			outputStream.write(br.getBytes());

		}
		
		inputStream_sendData.close();
 }
  else if(rs.getBlob("send_data")==null)
  {
	  Blob blob_receiveData = rs.getBlob("receive_data"); 
	  byte[] bdata_receive = blob_receiveData.getBytes(1, (int) blob_receiveData.length());
	   inputStream_receiveData = blob_receiveData.getBinaryStream();
	  while ((inputStream_receiveData.read(bdata_receive)) != -1) {
			outputStream.write(receiveData.getBytes());
			outputStream.write(br.getBytes());
			outputStream.write(br.getBytes());
			outputStream.write(bdata_receive);

			outputStream.write(br.getBytes());
			outputStream.write(br.getBytes());

		}
	  
	  inputStream_receiveData.close();
  }
  else if(rs.getBlob("send_data")==null && rs.getBlob("receive_data")==null )
	  	 { System.out.println("error in send and Receive XML");
	  	 }
  else
  {
 
						Blob blob_receiveData = rs.getBlob("receive_data");
 						Blob blob_sendData = rs.getBlob("send_data");
 
												byte[] bdata_receive = blob_receiveData.getBytes(1, (int) blob_receiveData.length());					
						
						byte[] blob_send = blob_sendData.getBytes(1, (int) blob_sendData.length());
						
						 inputStream_receiveData = blob_receiveData.getBinaryStream();
						 inputStream_sendData = blob_sendData.getBinaryStream();

						while ((inputStream_sendData.read(blob_send)) != -1) {
							outputStream.write(sendData.getBytes());
							outputStream.write(br.getBytes());
							outputStream.write(br.getBytes());
							outputStream.write(blob_send);

							outputStream.write(br.getBytes());
							outputStream.write(br.getBytes());

						}
						while ((inputStream_receiveData.read(bdata_receive)) != -1) {
							outputStream.write(receiveData.getBytes());
							outputStream.write(br.getBytes());
							outputStream.write(br.getBytes());
							outputStream.write(bdata_receive);

							outputStream.write(br.getBytes());
							outputStream.write(br.getBytes());

						}
						 inputStream_sendData.close();
						 inputStream_receiveData.close();
						 }

 
						outputStream.write(Completion.getBytes());
						outputStream.write(br.getBytes());
						outputStream.write(br.getBytes());
  						outputStream.flush();
  						dbcon=1;
						System.out.println("File saved");

					 
					}				
					
					outputStream.close();
					//connection.close();
				
			} catch (SQLException ex) {

				ex.printStackTrace();
			} catch (IOException ex) {

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		}
		
	   

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		HttpSession session=request.getSession();
	    File f = (File) session.getAttribute("fileName");
	    System.out.println(f.getName());
		
		//String filename = "C:\\logs\\logExtractor\\logs_2015083118922.txt";
		//File f=file;
		
		response.setContentType("application/octet-stream");
		//String disHeader = "Attachment; Filename=\"" + filename + "\"";
		String disHeader = "Attachment; Filename=\"" + f.getName() + "\"";
		response.setHeader("Content-Disposition", disHeader);
		//File fileToDownload = new File(filename);

		InputStream in = null;
		ServletOutputStream outs = response.getOutputStream();

		try {
		in = new BufferedInputStream
		(new FileInputStream(f));
		int ch;
		download=1;
		while ((ch = in.read()) != -1) {
		outs.print((char) ch);
		}
		}
		finally {
		if (in != null) in.close(); // very important
		}

		outs.flush();
		outs.close();
		in.close();
		
		

	}

	public ResultSet dbconnection(String dbConnectionName,String AccountNumber,String startDate,String endDate) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
		readconfigfile(dbConnectionName);
		String accountNum=AccountNumber.trim();
		System.out.println("jdbc:oracle:thin:@" + host + ":" + port + ":" + sid +username +password);
		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			
			connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + sid +"",username,password);
			//String sql = "select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='2015-08-12 03:30:04' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='2015-08-12 04:37:04'";
			//String sql = "select * from cwmessagelog where to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
			String sql="select * from cwmessagelog where USER_DATA2 ='"+accountNum+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";
			//String sql = "select * from cwmessagelog where operation = 'ucontrolsrv:AccountServicePortType/getAccount' and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') >='"+startDate+"'and to_char(CREATION_TIME, 'yyyy-mm-dd hh24:mi:ss') <='"+endDate+"'";

			PreparedStatement statement = connection.prepareStatement(sql);

			rs = statement.executeQuery();	
			while(rs.next())
			{
				System.out.println(rs.getString(1));
			}
			System.out.println("Connection SuccessFull");
			

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return rs;

	}

	public void readconfigfile(String dbConnectionName) {
		// TODO Auto-generated method stub
		Properties prop = null;

		InputStream iS = null;
		try {
			File file = new File("C:\\Users\\comcast\\workspace\\LogExtractor\\WebContent\\WEB-INF\\dbconfig.properties");
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				if (key.equalsIgnoreCase(dbConnectionName)) {
					String value = properties.getProperty(dbConnectionName);
					String[] valueSplit = value.split(",");
					host = valueSplit[0];
					username = valueSplit[1];
					password = valueSplit[2];
					port = valueSplit[3];
					sid = valueSplit[4];
					System.out.println(host);
					envir=1;

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void DownloadFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		 
	/*if (envir==1 && dbcon==1)
	{*/
		
		//String success="success"; 
	 // request.setAttribute("filename", "filenamt.txt");
		
		
		String filename = "C:\\logs\\logExtractor\\logs_2015083118922.txt";
		File f=file;
		response.setContentType("application/octet-stream");
		//String disHeader = "Attachment; Filename=\"" + filename + "\"";
		String disHeader = "Attachment; Filename=\"" + f.getName() + "\"";
		response.setHeader("Content-Disposition", disHeader);
		File fileToDownload = new File(filename);

		InputStream in = null;
		ServletOutputStream outs = response.getOutputStream();

		try {
		in = new BufferedInputStream
		(new FileInputStream(file));
		int ch;
		download=1;
		while ((ch = in.read()) != -1) {
		outs.print((char) ch);
		}
		}
		finally {
		if (in != null) in.close(); // very important
		}

		outs.flush();
		outs.close();
		in.close();
		
		

		
	/*}   
	else
	{
		request.getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
		
		
	}*/
	}
	public void status(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(envir==1 && dbcon==1 )
			
		{
			//ServletContext sc = getServletContext();
			HttpSession session=request.getSession();
		     session.setAttribute("fileName", file);
		 	response.sendRedirect("/LogExtractor/SuccessPage.jsp");
	        //RequestDispatcher rd = sc.getRequestDispatcher("/LogExtractor/SuccessPage.jsp");
	        //rd.forward(request, response);
			//response.sendRedirect("/LogExtractor/SuccessPage.jsp");
		}
		else
		{
			response.sendRedirect("/LogExtractor/ErrorPage.jsp");
			
		}
	}
}

