package demo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import base.BaseTest;

public class Demo1 extends BaseTest {
	private Logger logger = LogManager.getLogger(this.getClass());

	@Test
	public void test1() {
		loginPage.clickLoginButton();
		logger.info("Login Button clicked");
	}

}
