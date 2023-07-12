package appHooks;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.qa.factory.DriverFactory;
import com.qa.util.ConfigReader;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import java.nio.file.Files;

public class Hooks {

	public ConfigReader configReader;
	public Properties prop;
	public DriverFactory driverFactory;
	public WebDriver driver;

	//String val = System.getProperty("browser");

	//@Before(value = "@chrome", order = 0)
	@Before
	public void setup() throws Exception {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
		driver =DriverFactory.getDriver();
		//driver.get(prop.getProperty("SiteURL"));
		/*
		 * driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		 * driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
		 */
		try {
			Thread.sleep(1000);
			ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
			if (tab.size() != 1) {
				driver.close();
				driver.switchTo().window(tab.get(0));
			}
		} catch (Exception e) {
			throw new Exception("Child tab is not closed");
		}
	}

	/*@Before(value = "@firefox", order = 0)
	public void setupff() {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
		driver =DriverFactory.getDriver();
		driver.get(prop.getProperty("SiteURL"));
	 *//*configReader = new ConfigReader();
		prop = configReader.init_prop();
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver("firefox");
		DriverFactory.getDriver().get(prop.getProperty("SiteURL"));//
	}

	@Before(value = "@edge", order = 0)
	public void setuped() {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
		driver =DriverFactory.getDriver();
		driver.get(prop.getProperty("SiteURL"));
		 //	configReader = new ConfigReader();
		prop = configReader.init_prop();
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver("edge");
		DriverFactory.getDriver().get(prop.getProperty("SiteURL"));//
	}
		 */

	/*	@After(order = 0)
	public void quitBrowser() {
		driver.quit();
	}*/

	@After(value = "@checkTabs")
	public void checkTabs() throws Exception {
		try {
			Thread.sleep(1000);
			ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
			if (tab.size() != 1) {
				driver.close();
				driver.switchTo().window(tab.get(0));
			}
		} catch (Exception e) {
			throw new Exception("Child tab is not closed");
		}
	}

	@After(order = 0)
	public void tearDown(Scenario scenario) throws IOException {
		if (scenario.isFailed()) {
			String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			String ScreenshotName = scenario.getName().replaceAll(" ", "_");
			File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcePath, "image/png", ScreenshotName + dateName);
			String path = "./FailedTestsScreenshots";
			File directory = new File(path);
			if (!Files.exists(Paths.get(path))) {
				directory.mkdir();
			}
			String destination = Paths.get("").toAbsolutePath().toString() + "/FailedTestsScreenshots/" + dateName
					+ ".png";
			File finalDestination = new File(destination);
			FileHandler.copy(source, finalDestination);
		}
	}

}