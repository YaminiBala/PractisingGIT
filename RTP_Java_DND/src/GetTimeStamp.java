import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.parasoft.api.Application;
import com.parasoft.api.ScriptingContext;


public class GetTimeStamp {

	public void gettimeStamp(Object input,ScriptingContext c) throws ParseException
	{
		Date todayDate = new Date();
		  Application.showMessage("Today's Date::"+todayDate.getTime());
		String Time;
		String Time2;
			String dbefore=c.get("T1").toString();
			Application.showMessage("Dbefore:::"+dbefore);
			Application.showMessage(c.get("T1").toString());
			TimeZone timeZone = TimeZone.getTimeZone("US/Eastern");
        DateFormat todayDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");         
        todayDateFormat.setTimeZone(timeZone);
        Time=todayDateFormat.format(todayDate);
        Date d2=todayDateFormat.parse(dbefore);         
        Application.showMessage("Before Processing::"+d2);
        Date d1=todayDateFormat.parse(Time);
        Application.showMessage("After Processing::"+d1);		
		long difference = d1.getTime() - d2.getTime();
		System.out.println(difference/1000);
	 Application.showMessage("Time After Completion::"+difference/1000);
	 
	}
   
}
