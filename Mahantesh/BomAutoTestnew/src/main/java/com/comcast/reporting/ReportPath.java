package com.comcast.reporting;

import java.io.File;


import com.comcast.utils.FrameworkConstants;
import com.comcast.utils.TestUtils;

public class ReportPath {
	private static ReportPath instance = null;
	private String reportPath;
	protected TestUtils testutils;

	@SuppressWarnings("unused")
	protected ReportPath() {
		String jenkinsBuildTag = System.getenv("BUILD_TAG");
		if (jenkinsBuildTag == null) {
			String finalfolder = "Result_" + TestUtils.getTimeStamp();
			reportPath = TestUtils.getRelativePath() + File.separator + FrameworkConstants.RESULT_FOLDER + File.separator + finalfolder;
		} else {
			reportPath = TestUtils.getRelativePath() + File.separator + FrameworkConstants.RESULT_FOLDER + File.separator + jenkinsBuildTag;
		}
		
		boolean success;
		success = (new File(reportPath)).mkdirs();
		
		System.out.println("HTML Report Path: " + reportPath);
	}

	/**
	 * Get the current report folder
	 * @return
	 */
	public String getReportPath(){
		return reportPath;
	}
	
	public static synchronized ReportPath getInstance() {
		if (instance == null) {
			instance = new ReportPath();
		}
		return instance;
	}
}
