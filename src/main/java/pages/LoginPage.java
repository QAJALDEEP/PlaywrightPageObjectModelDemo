package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {

	private Page page;
	private Locator loginButton;

	public LoginPage(Page page) {
		this.page = page;
		loginButton = this.page.locator("'Login'");
	}

	public void clickLoginButton() {
		loginButton.click();
	}

}
