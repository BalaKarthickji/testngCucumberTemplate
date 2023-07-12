package com.qa.factory;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public WebDriver driver;
	private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

	public WebDriver init_driver(String Browser) {

		if (Browser.equalsIgnoreCase("chrome")) {

			ChromeOptions options = new ChromeOptions();
			HashMap<String, Integer> contentSettings = new HashMap<>();
			HashMap<String, Object> profile = new HashMap<>();
			HashMap<String, Object> prefs = new HashMap<>();
			contentSettings.put("geolocation", 1);
			contentSettings.put("notifications", 1);
			profile.put("managed_default_content_settings", contentSettings);
			prefs.put("profile", profile);
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
			options.addArguments("kiosk-printing");
			options.addArguments("--disable-notifications");
			
			WebDriverManager.chromedriver().setup();
			tlDriver.set(new ChromeDriver(options));

		} else if (Browser.equalsIgnoreCase("firefox")) {
		
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("geo.enabled", true);
			profile.setPreference("geo.provider.use_corelocation", true);
			profile.setPreference("geo.prompt.testing", true);
			profile.setPreference("geo.prompt.testing.allow", true);
			profile.setPreference("geo.wifi.uri",
					"data:application/json , { \"status\": \"OK\", \"accuracy\": 100.0, \"location\": { \"lat\": 18.975080, \"lng\": 72.825838, \"latitude\": 18.975080, \"longitude\": 72.825838, \"accuracy\": 100.0 } }");
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(profile);
			WebDriverManager.firefoxdriver().setup();
			tlDriver.set(new FirefoxDriver(options));
		} else if (Browser.equalsIgnoreCase("edge")) {
			HashMap<String, Object> edgePrefs = new HashMap<String, Object>();
			edgePrefs.put("profile.default_content_settings.popups", 1);
			edgePrefs.put("profile.default_content_setting_values.notifications", 1);
			edgePrefs.put("profile.default_content_setting_values.automatic_downloads", 1);
			edgePrefs.put("profile.content_settings.pattern_pairs.*,*.multiple-automatic-downloads", 1);
			EdgeOptions egdeOptions = new EdgeOptions();
			egdeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			egdeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			WebDriverManager.edgedriver().setup();
			tlDriver.set(new EdgeDriver(egdeOptions));
			
		}
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		return getDriver();
	}

	public static synchronized WebDriver getDriver() {
		return tlDriver.get();
	}

	public static void setDriver(WebDriver driver2) {
		        tlDriver.set(driver2);
		    }
	}

