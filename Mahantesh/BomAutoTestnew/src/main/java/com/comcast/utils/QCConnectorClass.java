package com.comcast.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;

import com.mercury.qualitycenter.otaclient.ClassFactory;
import com.mercury.qualitycenter.otaclient.IAttachment;
import com.mercury.qualitycenter.otaclient.IAttachmentFactory;
import com.mercury.qualitycenter.otaclient.IBaseFactory;
import com.mercury.qualitycenter.otaclient.IExtendedStorage;
import com.mercury.qualitycenter.otaclient.IList;
import com.mercury.qualitycenter.otaclient.IRun;
import com.mercury.qualitycenter.otaclient.IRunFactory;
import com.mercury.qualitycenter.otaclient.ITDConnection;
import com.mercury.qualitycenter.otaclient.ITSTest;
import com.mercury.qualitycenter.otaclient.ITestSet;
import com.mercury.qualitycenter.otaclient.ITestSetFolder;
import com.mercury.qualitycenter.otaclient.ITestSetTreeManager;
import com4j.Com4jObject;

public class QCConnectorClass {
	private String qcURL;
	private String qcUserName;
	private String qcPassword;
	private String qcDomain;
	private String qcProject;
	private String qcTestLabPath;
	private TestSettings testSettings;
	
	
	public QCConnectorClass(){
		testSettings= new TestSettings();
		this.qcURL=testSettings.getQCUrl();
		this.qcUserName=testSettings.getQCUserName();
		this.qcPassword=testSettings.getQCPassword();
		this.qcDomain=testSettings.getQCDomain();
		this.qcProject=testSettings.getQCProject();
		this.qcTestLabPath=testSettings.getQCTestLabPath();
	}
	
	public void updateQCStatus(String TCId, String Status, String reportPath,
			String testCaseQCName) {
		ITDConnection itdc = ClassFactory.createTDConnection();
		System.out.println(qcURL);
		try{
			itdc.initConnectionEx(qcURL);	
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		

		if (itdc.connected()) {
			try {
				itdc.connectProjectEx(qcDomain, qcProject, qcUserName, qcPassword);
			} catch (Throwable e) {
				e.printStackTrace();
				return;
			}

			/*
			 * Get a test set instance
			 */

		}
		ITestSetTreeManager tstm = itdc.testSetTreeManager().queryInterface(
				ITestSetTreeManager.class);
		ITestSetFolder tsf = tstm.nodeByPath(qcTestLabPath).queryInterface(
				ITestSetFolder.class);
		
		IList testSets = tsf.findTestSets("", false,"");
	
		for (Com4jObject testSetObj : testSets) {
			ITestSet testSet = testSetObj.queryInterface(ITestSet.class);
			System.out.println(("Test Set Instance: " + testSet.name()));
			IBaseFactory testFactory = testSet.tsTestFactory().queryInterface(
					IBaseFactory.class);
			IList testInstances = testFactory.newList("");
			ITSTest testInstance;
			for (Com4jObject testInstanceObj : testInstances) {
				testInstance = testInstanceObj.queryInterface(ITSTest.class);

				if (testInstance.testId().equals(TCId)) {
					System.out.println("TC Found");
					IRunFactory runfactory = testInstance.runFactory()
							.queryInterface(IRunFactory.class);
					IRun run = runfactory.addItem("RunNew").queryInterface(
							IRun.class);

					run.status(Status);
					File dir = new File(reportPath);

					try {
						System.out.println("Getting all files in "
								+ dir.getCanonicalPath()
								+ " including those in subdirectories");
						List<File> files = (List<File>) FileUtils.listFiles(
								dir, TrueFileFilter.INSTANCE,
								TrueFileFilter.INSTANCE);
						byte[] b;

						FileOutputStream fout = new FileOutputStream(reportPath
								+ testCaseQCName + ".zip");
						ZipOutputStream zout = new ZipOutputStream(
								new BufferedOutputStream(fout));

						for (int i = 0; i < files.size(); i++) {
						if(files.get(i).getName().contains(testCaseQCName)){
							b = new byte[1024];
							FileInputStream fin = new FileInputStream(
									files.get(i));
							zout.putNextEntry(new ZipEntry(files.get(i)
									.getName()));
							int length;
							while (((length = fin.read(b, 0, 1024))) > 0) {
								zout.write(b, 0, length);
							}
							zout.closeEntry();
							fin.close();	
						}
							
						}
						zout.close();

					} catch (Exception e) {
						System.out.println("Exception caught");
					}
					String fileName = new File(reportPath + testCaseQCName
							+ ".zip").getName();
					String folderName = new File(reportPath + testCaseQCName
							+ ".zip").getParent();
					try {
						IAttachmentFactory attachfac = run.attachments()
								.queryInterface(IAttachmentFactory.class);
						IAttachment attach = attachfac.addItem(fileName)
								.queryInterface(IAttachment.class);
						IExtendedStorage extAttach = attach.attachmentStorage()
								.queryInterface(IExtendedStorage.class);
						extAttach.clientPath(folderName);
						extAttach.save(fileName, true);
						attach.description("Actual");
						attach.post();
						attach.refresh();
					} catch (Exception e) {
						System.out.println("QC Exceptione : " + e.getMessage());
					}

					run.post();
				}

			}

		}
	}

}
