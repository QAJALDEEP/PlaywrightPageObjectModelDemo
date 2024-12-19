package extentreportsconfig;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

    // ThreadLocal for managing ExtentReports instance per thread
    private static ThreadLocal<ExtentReports> extentReportsThreadLocal = new ThreadLocal<>();

    public static ExtentReports getExtentReports() {
        if (extentReportsThreadLocal.get() == null) {
            extentReportsThreadLocal.set(createHtmlReport());
        }
        return extentReportsThreadLocal.get();
    }

    private static ExtentReports createHtmlReport() {
        ExtentReports extentReport = new ExtentReports();
        Date date = new Date();
        String fileName = date.toString().replace(":", "_").replace(" ", "_") + ".html";

        // Ensure the directory exists
        String reportDirectoryPath = System.getProperty("user.dir") + File.separator + "htmlreports";
        File reportDirectory = new File(reportDirectoryPath);
        if (!reportDirectory.exists()) {
            reportDirectory.mkdirs();
        }

        File extentReportFile = new File(reportDirectory + File.separator + fileName);
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(extentReportFile);

        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setReportName("Test Automation Report");
        sparkReporter.config().setDocumentTitle("Test Automation Report");
        sparkReporter.config().setTimeStampFormat("dd/MM/yyyy hh:mm:ss");

        extentReport.attachReporter(sparkReporter);

        extentReport.setSystemInfo("Operating System", System.getProperty("os.name"));
        extentReport.setSystemInfo("Username", System.getProperty("user.name"));
        extentReport.setSystemInfo("Java Version", System.getProperty("java.version"));

        return extentReport;
    }

    public static void unload() {
        extentReportsThreadLocal.remove();
    }
}
