package playwrightconfig;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class PlaywrightConfig {

	private static ThreadLocal<Playwright> tlpw = new ThreadLocal<>();
	private static ThreadLocal<Browser> tlbr = new ThreadLocal<>();
	private static ThreadLocal<Page> tlpg = new ThreadLocal<>();
	private static ThreadLocal<BrowserContext> tlBrowserContext = new ThreadLocal<>();

	public static Playwright getPlaywright() {
		return tlpw.get();
	}

	public static Browser getBrowser() {
		return tlbr.get();
	}

	public static Page getPage() {
		return tlpg.get();
	}

	public static BrowserContext getBrowserContext() {
		return tlBrowserContext.get();
	}

	public PlaywrightConfig openBrowser(String browserName) {
		tlpw.set(Playwright.create());
		ArrayList<String> arguments = new ArrayList<>();
		arguments.add("--start-maximized");
		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			tlbr.set(getPlaywright().chromium()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments)));
			break;
		case "firefox":
			tlbr.set(getPlaywright().firefox()
					.launch(new BrowserType.LaunchOptions().setHeadless(false).setArgs(arguments)));
		default:
			break;
		}
		tlBrowserContext.set(tlbr.get().newContext(new Browser.NewContextOptions().setViewportSize(null)));
		return new PlaywrightConfig();
	}

	public Page navigateTo(String baseUrl) {
		tlpg.set(tlBrowserContext.get().newPage());
		tlpg.get().navigate(baseUrl);
		return tlpg.get();
	}

	public void closeBrowser() {
		if (tlpg.get() != null) {
			tlpg.get().close();
		}
		if (tlBrowserContext.get() != null) {
			tlBrowserContext.get().close();
		}
		if (tlbr.get() != null) {
			tlbr.get().close();
		}
		if (tlpw.get() != null) {
			tlpw.get().close();
		}
		tlpg.remove();
		tlBrowserContext.remove();
		tlbr.remove();
		tlpw.remove();
	}
	

	public static String takeScreenshot() {
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		//getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		
		byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
		String base64Path = Base64.getEncoder().encodeToString(buffer);
		
		return base64Path;
	}

}