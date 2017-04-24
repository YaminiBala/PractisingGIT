<%-- 
    Document   : Success page
    Created on : Jul 21, 2015, 3:00:09 AM
    Author     : comcast
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="LogExtract" scope="page" class="com.DownloadFile" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Success Page</title>
</head>
<script>
	function doalert() {
		DownloadFile
		df = new DownloadFile();

		return false;
	}
</script>
<body>
	<form id="download" METHOD="POST" ACTION="/LogExtractor/LogExtract">
		<div
			style="width: 1340px; top: 1px; position: absolute; color: greenyellow; background-color: #ffffff; border: 2px transparent; padding: 5px;">
			<TABLE>
				<TR>
					<TD style="width: 380px;"></TD>
					<TD>
						<CENTER>
<img src="image/mainBanner.png" alt="" />
</CENTER>
					</TD>
					<TD style="width: 380px;"></TD>
				</TR>
			</TABLE>

		</div>

		</div>

		<div id="div3"
			style="width: 700px; top: 230px; background-color: #ffffff; position: absolute; left: 340px; height: 110px; border: 1px darkcyan solid">

			<h5>
				<font
					style="font-family: consolas; font-size: 15px; color: dimgray; top: 10px; left: 50px; position: absolute;">
					We have successfully generated logs!! Click below button to
					download files <input type="submit" value="Download"> <!-- <a href="" id="filn" download="test" onclick="doalert();return false;">click here!!</a> -->

				</font>
			</h5>
		</div>
	</form>
</body>
</html>
