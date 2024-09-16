package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.activation.DataSource;

import org.apache.commons.mail.DataSourceResolver;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.apache.poi.sl.image.ImageHeaderBitmap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	 String repName;
	 
	 @Override
	 public void onStart(ITestContext testContext) {
		 
		/* SimpleDateFormat df=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		 Date dt = new Date();
		 String currentdatetimestamp =df.format(dt); 
		 */
		 
	     String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
	     repName = "Test-Report-" + timeStamp + ".html";
	     sparkReporter	= new ExtentSparkReporter(".\\reports\\"+repName);//specify location
	     sparkReporter.config().setDocumentTitle("OpenCart Automation report");//Title of Report
	     sparkReporter.config().setReportName("OpenCart Functional Tetsing");//name of report
	     sparkReporter.config().setTheme(Theme.DARK);
	     
	     extent = new ExtentReports();
	     extent.attachReporter(sparkReporter);
	     extent.setSystemInfo("Application","Opencart");
	     extent.setSystemInfo("Module","Admin");
	     extent.setSystemInfo("SubModule","Customers");
	     extent.setSystemInfo("User Name",System.getProperty("usern.name"));
	     extent.setSystemInfo("Environment","QA");
	     
	     String os = testContext.getCurrentXmlTest().getParameter("os");
	     extent.setSystemInfo("Operating system",os);
	     String browser = testContext.getCurrentXmlTest().getParameter("browser");
	     extent.setSystemInfo("Browser",browser);
	     List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
	     if(!includedGroups.isEmpty()) {
	    	 extent.setSystemInfo("Groups", includedGroups.toString());
	     }
	 }
	     
	     public void onTestSuccess(ITestResult result) {
	    	 test = extent.createTest(result.getTestClass().getName());
	    	 test.assignCategory(result.getMethod().getGroups());// to display groups in report
	    	 test.log(Status.PASS, result.getName()+" Got Succefully Executed");
	    	 
	     }
	     
	     public void onTestFailure(ITestResult result) {
	    	 test = extent.createTest(result.getTestClass().getName());
	    	 test.assignCategory(result.getMethod().getGroups());// to display groups in report
	    	 
	    	 test.log(Status.FAIL, result.getName()+" Got Failed");
	    	 test.log(Status.INFO,result.getThrowable().getMessage());
	    	  
	    	 try {
	    		 String imgPath = new BaseClass().captureScreen(result.getName());
	    		 test.addScreenCaptureFromPath(imgPath);
	    	 }
	    	 catch(Exception e) {
	    		 e.printStackTrace();
	    	 }
	    	 
	    	 
	    }
	     
	     public void onTestSkipped(ITestResult result) {
	    	 test = extent.createTest(result.getTestClass().getName());
	    	 test.assignCategory(result.getMethod().getGroups());// to display groups in report
	    	 test.log(Status.SKIP, result.getName()+" Got Skipped");
	    	 test.log(Status.INFO,result.getThrowable().getMessage());
	     }
	     
     public void onFinish(ITestContext testContext) {
	      extent.flush();
	      
	      String pathOfExtentReport =System.getProperty("user.dir")+"\\reports\\"+repName;
	      File extentReport = new File(pathOfExtentReport);
	      try {
	    	  Desktop.getDesktop().browse(extentReport.toURI());
	    	  
	      }catch(IOException e) {
	    	  e.printStackTrace();
	      }
	      }
     }
//	      
//	      try {
//	    	  URL url = new URL("file:///"+System.getProperty("user.dir")+"\\reports\\"+repName);
//	    	  //Create the email message
//	    	  ImageHtmlEmail email = new ImageHtmlEmail();
//	    	  email.setDataSourceResolver(new DataSourceUrlResolver(url));
//	    	  email.setHostName("smtp.gmail.com");
//	    	  email.setSmtpPort(465);
//	    	  email.setAuthenticator(new DefaultAuthenticator("robinhoodaks@gmail.com", "realmext@19#"));
//	    	  email.setSSLOnConnect(true);
//	    	  email.setFrom("robinhoodaks@gmail.com");//sender
//	    	  email.setSubject("Test Results");
//	    	  email.setMsg("Please find attached report..");
//	    	  email.addTo("akshaymadlur4@gmail.com");//Receiver
//	    	  email.attach(url,"extent report", "please check report...");
//	    	  email.send();
//	      }
//	      catch(Exception e) {
//	    	  e.printStackTrace();
//	     
//	     
//	 }
//	     }
	     

	     
