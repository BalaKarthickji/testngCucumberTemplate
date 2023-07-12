package testRunners;



import com.qa.factory.DriverFactory;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;
import org.apache.logging.log4j.ThreadContext;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

@CucumberOptions(plugin = { "pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"json:target/cucumber-reports/report.json" })

public class MyRunner {

	private static final ThreadLocal<TestNGCucumberRunner> testNGCucumberRunner = new ThreadLocal<>();
	public Properties prop;
	public WebDriver driver;
	public DriverFactory driverFactory;
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	LocalDateTime now;
	LocalDateTime later;

	public static TestNGCucumberRunner getRunner() {
		return testNGCucumberRunner.get();
	}

	public static void setRunner(TestNGCucumberRunner testNGCucumberRunner1) {
		testNGCucumberRunner.set(testNGCucumberRunner1);
	}

	@Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "scenarios")
	public void scenario(PickleWrapper pickle, FeatureWrapper cucumberFeature) throws Throwable {
		getRunner().runScenario(pickle.getPickle());
	}

	@DataProvider
	public Object[][] scenarios() {
		return getRunner().provideScenarios();
	}

    @Parameters({"browser"})
    @BeforeClass(alwaysRun = true)
    public void setUpCucumber(String browser) {
        ThreadContext.put("ROUTINGKEY", browser);
        driverFactory = new DriverFactory();
        driver = driverFactory.init_driver(browser);
        setRunner(new TestNGCucumberRunner(this.getClass()));
    }

	@AfterClass(alwaysRun = true)
	public void tearDownClass() {
		if (DriverFactory.getDriver() != null) {
			DriverFactory.getDriver().quit();
			DriverFactory.setDriver(null);
		}
	}
	@BeforeSuite
	public void TestTriggered() {
		
	}

	@AfterSuite
	public void TestCompletion() {
			if (testNGCucumberRunner != null)
				getRunner().finish();
		later = LocalDateTime.now();
		String path = "./cookies";
		File directory = new File(path);
		if (!Files.exists(Paths.get(path))) {
			directory.mkdir();
		}
		File[] files = directory.listFiles();
		if (files.length > 0) {
			for (File f : files) {
				if (f.getName().contains("browser.data")) {
					f.delete();
				}
			}
		}
		
	}
}
