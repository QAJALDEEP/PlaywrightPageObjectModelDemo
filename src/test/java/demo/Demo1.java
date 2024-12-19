package demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;

import base.BaseTest;
import extentreportsconfig.ExtentListeners;

public class Demo1 extends BaseTest {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Test
	public void test1() {
		ExtentTest log = ExtentListeners.getTest();
		loginPage.clickLoginButton();
		log.info("Hello !!");
		logger.info("Login Button clicked");
	}

}
