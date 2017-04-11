package com.comcast.bulkOM;

import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

public class Common {

	
	@Test
	public void testcommon()
	{
		final FirefoxProfile firefoxProfile = new FirefoxProfile();
	    firefoxProfile.setPreference("xpinstall.signatures.required", false);
	   WebDriver driver = new FirefoxDriver(firefoxProfile);
	}
   
}
