package extentreportsconfig;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class ExtentListeners implements ITestListener {

    private static ThreadLocal<ExtentTest> extentTestThreadLocal = new ThreadLocal<>();
    private ExtentReports extentReports;

    @Override
    public void onStart(ITestContext context) {
        // Initialize ExtentReports at the start of the suite
        extentReports = ExtentManager.getExtentReports();
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test entry in the report for each test
        ExtentTest extentTest = extentReports.createTest(result.getMethod().getMethodName());
        extentTestThreadLocal.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log success in the report
        extentTestThreadLocal.get().pass("Test Passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log failure in the report and attach the exception
        ExtentTest test = extentTestThreadLocal.get();
        if (test != null) {
            test.fail("Test Failed: " + result.getMethod().getMethodName());
            test.fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log skipped test
        ExtentTest test = extentTestThreadLocal.get();
        if (test != null) {
            test.skip("Test Skipped: " + result.getMethod().getMethodName());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the report at the end of the suite
        if (extentReports != null) {
            extentReports.flush();
        }
        // Clean up ThreadLocal to avoid memory leaks
        extentTestThreadLocal.remove();
    }

    public static ExtentTest getTest() {
        return extentTestThreadLocal.get();
    }
}
