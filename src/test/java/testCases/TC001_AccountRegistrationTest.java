package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	// public WebDriver driver;

	@Test(groups={"Regression","Master"})
	public void verify_account_registration() {
		logger.info("*** Starting TC001_AccountRegistration ***");
		try {
		HomePage hp = new HomePage(driver);
		hp.clickMyaccount();
		logger.info("Clicked on My account link");
		hp.clickRegister();
		logger.info("Clicked on My account link");
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		logger.info("Providing Customer Details");
		regpage.setFristName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString() + "@gmail.com");
		regpage.setTelephone(randomNumeric());
		String pwd = randomAlphaNumeric();
		regpage.setPassword(pwd);

		regpage.setConfirmPassword(pwd);
		regpage.setPrivatePolicy();
		regpage.clickContinue();
		logger.info(" Validating Expected message...");
		String confmsg = regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
		}else {
			logger.error("Test failed..");
			logger.debug("Debug logs..");
			Assert.assertTrue(false);
		}

		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}catch(Exception e) {
			
			Assert.fail();
		}
		logger.info(" Finished TC001_AccountRegistration...");
	}

}
