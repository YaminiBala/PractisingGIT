<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html>
<%@ page import="com.LogExtract"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order Management Log Extractor</title>
<script src="datetimepicker_css.js"></script>
</head>
<body>
	<script text="javascript/text">
            function call(selectApp,selectedEnv)
            {
               var sel= document.getElementById('selectApp').value;
               
                  switch(sel)
                    {
                      case 'OP' :
                                                      
                        var select = document.getElementById('selectedEnv');
                        var OP = "DTDEV,QA1,QA4";
                        var options = OP.split(",");
                        for(var i=0; i<options.length; i++)
                          {
                        	 
                        	select.options[i] = new Option(options[i],options[i]);
                        	
                         
                        }                                       
                        break;
                      case 'BFC':
                        
                        var BFC = "PERF1,PERF2";
                        var options = BFC.split(",");
                        var select = document.getElementById('selectedEnv');
                        
                         for(var i=0; i<options.length; i++)
                         {
                        	 
                         select.options[i] = new Option(options[i], options[i]);  //new Option("Text", "Value")
                        
                        
       }
                         break;
                      case 'DTA':
                          var dta = "DTAQA1,DTAQA2";
                          var options = dta.split(",");
                          var select = document.getElementById('selectedEnv');
                          
                           for(var i=0; i<options.length; i++)
                           {
                        	    
                           select.options[i] = new Option(options[i], options[i]);  //new Option("Text", "Value")
                           
                           
                           }
                           break;
                      case 'COMMON':
                          
                          var Common = "COMMON";
                          var options = Common.split(",");
                          var select = document.getElementById('selectedEnv');
                          
                           for(var i=0; i<options.length; i++)
                           {
                           
                           select.options[i] = new Option(options[i], options[i]);  //new Option("Text", "Value")
                           }
                           break;
                      case 'SYMLITE':
                          var symlite = "SYMLITEDEV4,SYMLITEDEV5,SYMDEV2COM,SYMDEV4COM";
                          var options = symlite.split(",");
                          var select = document.getElementById('selectedEnv');
                          
                           for(var i=0; i<options.length; i++)
                           {
                              
                           select.options[i] = new Option(options[i], options[i]);  //new Option("Text", "Value")
                           }
                           break;
                    }
            
    }
    function environment()
    {
        var sel= document.getElementById('selectedEnv').value;               
               
               }
    
            </script>

	<form id="logExtractor" method="POST" action="logExtractor.jsp">
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
		<div id="div1"
			style="width: 225px; top: 3px; background-color: #ffffff; left: 3px; height: 225px; position: Fixed; background-image: url(image/BoxImg/notepads.jpg); border: #ffffff">
			<select name="selectApp" id="selectApp" size="5" onclick="call()"
				style="top: 80px; left: 70px; position: Fixed;">
				<option value="OP">OP</option>
				<option value="DTA">DTA</option>
				<option value="BFC">BFC</option>
				<option value="COMMON">COMMON</option>
				<option value="SYMLITE">SYMLITE</option>
			</select>
		</div>

		<div id="div2"
			style="width: 228px; top: 3px; background-color: #ffffff; left: 1100px; height: 220px; position: Fixed; background-image: url(image/BoxImg/images.png); border: #ffffff">

			<h5>
				<font
					style="font-family: consolas; top: 40px; left: 1117px; position: Fixed;">
					Configured Env's::</font>
			</h5>

			<select name="selectedEnv" id="selectedEnv" onclick="environment()"
				size="3" style="top: 80px;width: 110px; left: 1160px; position: Fixed;">

				<!-- option value="DTDEV">DTDEV</option>
                <option value="QA11">QA1</option>
                <option value="QA3">QA3</option>-->
			</select>

		</div>

		<div id="div3"
			style="width: 350px; top: 230px; background-color: #ffffff; position: absolute; left: 240px; height: 210px; border: 1px darkcyan solid">
			<h5>
				<font
					style="font-family: consolas; font-size: 15px; color: dimgray; top: 10px; left: 50px; position: absolute;">Search
					with Account Number</font>
			</h5>
			<h5>
				<font
					style="font-family: consolas; font-size: 12px; color: black; top: 50px; left: 30px; position: absolute;">Account
					Number::</font>
			</h5>
			<input type="text" name="Acc_Input" value="" size="25px"
				style="font-family: consolas; font-size: 12px; color: black; top: 50px; left: 150px; position: absolute;" />

			<h5>
				<font
					style="font-family: consolas; font-size: 12px; color: black; top: 90px; left: 30px; position: absolute;">Start
					Time::</font>
			</h5>
			<!-- <input type="datetime-local" step="1" name="strt_Input" value="0" size="30px" style=" font-family:consolas; font-size:12px; color:  black; top:90px; left:150px; position: absolute;"/> -->
			<input type="Text" id="strt_Input" name="strt_Input" maxlength="25"
				size="25"
				style="font-family: consolas; font-size: 12px; color: black; top: 90px; left: 150px; position: absolute;" />
			<img src="image/cal.gif"
				onclick="javascript:NewCssCal ('strt_Input','YYYYMMDD','dropdown',true,'24',true)"
				style="top: 90px; left: 330px; position: absolute; cursor: pointer" />


			<h5>
				<font
					style="font-family: consolas; font-size: 12px; color: black; top: 130px; left: 30px; position: absolute;">End
					Time::</font>
			</h5>
			<!-- input type="datetime-local" step="1" name="end_Input" value="0" size="30px" style=" font-family:consolas; font-size:12px; color:  black; top:130px; left:150px; position: absolute;"/>-->
			<input type="Text" id="end_Input" name="end_Input" maxlength="25"
				size="25"
				style="font-family: consolas; font-size: 12px; color: black; top: 130px; left: 150px; position: absolute;" />
			<img src="image/cal.gif"
				onclick="javascript:NewCssCal ('end_Input','YYYYMMDD','dropdown',true,'24',true)"
				style="top: 130px; left: 330px; position: absolute; cursor: pointer" />
		</div>

		<div id="div4"
			style="width: 350px; top: 230px; background-color: #ffffff; position: absolute; left: 730px; height: 210px; border: 1px darkcyan solid">
			<h5>
				<font
					style="font-family: consolas; font-size: 15px; color: dimgray; top: 10px; left: 50px; position: absolute;">Search
					with Tracking ID</font>
			</h5>
			<h5>
				<font
					style="font-family: consolas; font-size: 12px; color: black; top: 85px; left: 30px; position: absolute;">Tracking
					ID::</font>
			</h5>
			<input type="text" name="Track_Input" value="" size="25px"
				style="font-family: consolas; font-size: 12px; color: black; top: 85px; left: 150px; position: absolute;" />

			<!-- <h5><font style=" font-family:consolas; font-size:12px; color:  black; top:90px; left:30px; position: absolute;">Start  Time::</font></h5>
               <input type="Text" id="strt" name ="strt" maxlength="25" size="25" style=" font-family:consolas; font-size:12px; color:  black; top:90px; left:150px; position: absolute;"/>
               <img src="image/cal.gif" onclick="javascript:NewCssCal ('strt','YYYYMMDD','dropdown',true,'24',true)" style="top:90px; left:330px; position: absolute;cursor:pointer"/>
                <!-- <input type="text" name="strt_Input" value="0" size="30px" style=" font-family:consolas; font-size:12px; color:  black; top:90px; left:150px; position: absolute;"/>
                            
                <h5><font style=" font-family:consolas; font-size:12px; color:  black; top:130px; left:30px; position: absolute;">End  Time::</font></h5>
                <input type="Text" id="end" name ="end" maxlength="25" size="25" style=" font-family:consolas; font-size:12px; color:  black; top:130px; left:150px; position: absolute;"/>
                <img src="image/cal.gif" onclick="javascript:NewCssCal ('end','YYYYMMDD','dropdown',true,'24',true)" style="top:130px; left:330px; position: absolute;cursor:pointer"/> -->
			<!-- <input type="text" name="end_Input" value="0" size="30px" style=" font-family:consolas; font-size:12px; color:  black; top:130px; left:150px; position: absolute;"/>-->

		</div>

		<div id="div5"
			style="width: 798px; top: 410px; background-color: #ffffff; position: absolute; left: 260px; height: 195px; border: #ffffff; background-image: url(image/BoxImg/text_box.png);">

			<h5>
				<font
					style="font-family: consolas; font-size: 14px; color: black; top: 10px; left: 310px; position: absolute;">
					Generate Logs and Save</font>
			</h5>
			<h5>
				<font
					style="font-family: consolas; font-size: 12px; color: black; top: 50px; left: 50px; position: absolute;">
					Location to Store Logs::</font>
			</h5>
			<input type="text" name="location" value="C:/logs/logExtractor"
				size="70px"
				style="font-family: consolas; font-size: 12px; color: black; top: 50px; left: 250px; position: absolute; width: 300px; border: 1px darkcyan solid" />


			<input type="text" name="FileName" value="logs" size="40px"
				style="font-family: consolas; font-size: 12px; color: black; top: 75px; left: 250px; position: absolute; width: 300px; border: 1px darkcyan solid" />

			<h5>
				<font
					style="font-family: consolas; font-size: 12px; color: black; top: 100px; left: 250px; position: absolute;">Single
					Text File</font>
			</h5>
			<input type="radio" name="LogRadio" value="singletxt"
				style="font-family: consolas; font-size: 12px; color: black; top: 100px; left: 370px; position: absolute; border: 1px darkcyan solid">

			<h5>
				<font
					style="font-family: consolas; font-size: 12px; color: black; top: 100px; left: 430px; position: absolute;">Multi
					XML's</font>
			</h5>
			<input type="radio" name="LogRadio" value="Multitxt" disabled
				style="font-family: consolas; font-size: 12px; color: black; top: 100px; left: 510px; position: absolute; border: 1px darkcyan solid">
			<h5>
				<font
					style="font-family: consolas; font-size: 12px; color: black; top: 75px; left: 50px; position: absolute;">
					File Name::</font>
			</h5>
			<h5>
				<font
					style="font-family: consolas; font-size: 12px; color: black; top: 100px; left: 50px; position: absolute;">
					Logs Format::</font>
			</h5>

			<input type="submit" value="Generate Logs" name="LogsButton"
				style="top: 150px; background-color: #ffffff; position: absolute; left: 680px; border: 1px darkcyan solid" />

		</div>




	</form>

</body>
</html>
