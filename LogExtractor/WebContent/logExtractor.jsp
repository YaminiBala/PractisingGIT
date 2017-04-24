<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="com.LogExtract"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SuccessPage</title>
</head>
<body>
	<jsp:useBean id="LogExtractor" scope="page" class="com.LogExtract" />

	<%
		String name = request.getParameter("selectedEnv");

		String accountNumber = request.getParameter("Acc_Input");

		String trackingNumber = request.getParameter("Track_Input");

		String location = request.getParameter("location");

		String filname = request.getParameter("FileName");

		out.println(trackingNumber);

		out.println(location);

		out.println(filname);

		String stdt;
		String enddate;
		if (request.getParameter("strt_Input") != null) {
			stdt = request.getParameter("strt_Input");
			out.println(stdt);
		} else {
			stdt = "";
		}

		if (request.getParameter("end_Input") != null) {
			enddate = request.getParameter("end_Input");
			out.println(enddate);
		} else {
			enddate = "";
		}

		LogExtract lg = new LogExtract();

		lg.getinputValues(name, stdt, enddate, accountNumber, location, filname, trackingNumber);
		lg.status(request, response);

		request.removeAttribute("name");
	%>




	<script>
		
	</script>
<body>



</body>
</html>
