package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.microsoft.playwright.Page;

import pages.LoginPage;
import playwrightconfig.PlaywrightConfig;
import utils.FileConstant;

public class BaseTest {

	protected Page page;
	protected LoginPage loginPage;
	private Logger logger = LogManager.getFormatterLogger(BaseTest.class);

	PlaywrightConfig pc;

	public BaseTest() {
		pc = new PlaywrightConfig();
	}

	@BeforeSuite
	public void doBeforeSuite() {
		String browserName = FileConstant.getPropertyValue(FileConstant.getAppConfigFilePath(), "browserName");
		String baseUrl = FileConstant.getPropertyValue(FileConstant.getAppConfigFilePath(), "baseUrl");
		page = pc.openBrowser(browserName).navigateTo(baseUrl);
		logger.info(browserName+ " launched...");
		logger.info("Navigated to " + baseUrl);
		loginPage = new LoginPage(page);
	}

	@AfterSuite
	public void tearDown() {
		pc.closeBrowser();
	}

}