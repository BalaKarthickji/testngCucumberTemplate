package com.qa.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import com.google.common.collect.Sets;
import com.paulhammant.ngwebdriver.NgWebDriver;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ElementActions {
	@SuppressWarnings("rawtypes")
	private static TouchAction touchAction;
	private static AndroidDriver<MobileElement> androidDriver;
	// TODO: future support
	private static IOSDriver<MobileElement> iosDriver;
	private static AppiumDriver<MobileElement> appiumDriver;
	private static String USER_NAME = System.getProperty("user.name"); // user dir
	private static final String KEY_BOARD_OPEN_CMD[] = new String[] {
			"/Users/" + USER_NAME + "/Library/Android/sdk/platform-tools/adb", "shell", "dumpsys", "input_method", "|",
			"grep", "mInputShown" };

	public static void verifyLink(String urlLink) {
		try {
			URL link = new URL(urlLink);
			HttpURLConnection httpConn = (HttpURLConnection) link.openConnection();
			httpConn.setConnectTimeout(2000);
			httpConn.connect();
			if (httpConn.getResponseCode() == 200) {
			
			}
			if (httpConn.getResponseCode() >= 400) {
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean Upload_File(WebDriver driver, By locator, String path) throws InterruptedException {
		try {
			String filepath = System.getProperty("user.dir") + path;
			System.out.println("File path is" + filepath);
			driver.findElement(locator).sendKeys(filepath);
		} catch (Exception e) {
		
			return false;
		}
		return true;
	}

	public static void SelectAndSendKeysRobot(WebDriver driver, By locator, String InputValue, int Timeout)
			throws AWTException {
		Robot robot = new Robot();
		robot.delay(500);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}   

	public static boolean SelectAndSendKeys(WebDriver driver, By locator, String InputValue, int Timeout)
			throws Exception {
		if (ElementActions.verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				WebElement element = driver.findElement((locator));
				String Value = element.getAttribute("value");
				if (!Value.isEmpty()) {
					element.click();
					Actions dummy = new Actions(driver);
					dummy.doubleClick(element).build().perform();
				}
				Thread.sleep(500);
				element.sendKeys(InputValue);
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static String GetSelectedOptions(WebDriver driver, By element) {
		String SelectedOption = null;
		try {
			WebElement ele = driver.findElement(element);
			Select select = new Select(ele);
			WebElement option = select.getFirstSelectedOption();
			SelectedOption = option.getText();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SelectedOption;
	}

	public static String GetSelectedOption(WebElement element) {
		String SelectedOption = null;
		try {
			Select select = new Select(element);
			WebElement option = select.getFirstSelectedOption();
			SelectedOption = option.getText();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return SelectedOption;
	}

	public static boolean ListOfElementsDisplayed(WebElement[] elements) {
		boolean Value = false;
		try {
			for (WebElement CurrentElement : elements) {
				if (CurrentElement.isDisplayed()) {
					Value = true;
				} else {
					Value = false;
					break;
				}
			}
		} catch (Exception e) {
		
			Value = false;
			e.printStackTrace();
		}
		return Value;
	}

	public static boolean ListOfElementsDisplayed(WebDriver driver, By[] elements, int TimeOut) {
		boolean Value = false;
		try {
			for (By CurrentElement : elements) {
				Value = verifyElementDisplayed(driver, CurrentElement, TimeOut);
			}

		} catch (Exception e) {
			
			Value = false;
			e.printStackTrace();
		}
		return Value;
	}

	public static List<WebElement> GetlistOfOptions(WebDriver driver, By element) {
		WebElement ele = driver.findElement(element);
		Select dropdown = new Select(ele);
		List<WebElement> allOptions = dropdown.getOptions();
		return allOptions;
	}

	public static void scrollToElement(WebDriver driver, By locator, String pixelValue) throws Exception {
		Properties config = new Properties();
		FileInputStream ip = new FileInputStream("src/test/resources/config/config.properties");
		config.load(ip);
		ElementActions.waitUntilVisibilityOfElementByXpath(driver, locator, 10);
		WebElement QtyPlusButton = driver.findElement((locator));
		String BrowserName = config.getProperty("BROWSER");
		if (BrowserName.equalsIgnoreCase("IE")) {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", QtyPlusButton);
		} else {
			Actions actions = new Actions(driver);
			actions.moveToElement(QtyPlusButton);
			actions.build().perform();
		}
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Thread.sleep(1000);
		jse.executeScript("window.scrollBy(0," + pixelValue + ")", "");
		Thread.sleep(500);
	}

	public static void scrollIntoView(WebDriver driver, By locator) throws InterruptedException {
		ElementActions.waitUntilVisibilityOfElementByXpath(driver, locator, 10);
		WebElement QtyPlusButton = driver.findElement((locator));
		((JavascriptExecutor) driver)
				.executeScript("arguments[0].scrollIntoView({behavior: 'smooth',block: 'center'});", QtyPlusButton);
		Thread.sleep(800);
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,20)");
		Thread.sleep(500);
	}

	public static void scrollToTop(WebDriver driver, String pixelValue) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Thread.sleep(1000);
		jse.executeScript("window.scrollBy(0," + pixelValue + ")", "");
		Thread.sleep(1000);
	}

	public static boolean verifyButtonNotenabled(WebDriver driver, By locator, int Timeout) {
		boolean status = false;
		waitUntilVisibilityOfElementByXpath(driver, locator, Timeout);
		try {
			String Classes = driver.findElement((locator)).getAttribute("class");
			if (Classes.contains("disabled") || Classes.contains("Disabled"))
				status = true;
			else
				status = false;
		} catch (Exception e) {
			System.out.println("Button is enabled");
			System.out.println("Button is enabled");
			status = false;
		}
		return status;
	}

	public static boolean clickElement(WebDriver driver, By locator, int Timeout) throws InterruptedException {
		if (verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				waitUntilElementIsClickableByXpath(driver, locator, Timeout);
				JSWaiter.waitJQueryAngular();
				driver.findElement((locator)).click();
				JSWaiter.waitJQueryAngular();
			} catch (Exception e) {
				// Assert.assertTrue(false, "Not able to click element with xpath " + locator);
				return false;
			}
			return true;
		} else {
			System.out.println("Unable to click element" + locator);
			return false;
		}
	}

	public static void clickElementCSSSelector(WebDriver driver, By locator) throws InterruptedException {
		if (verifyElementDisplayedCSSSelector(driver, locator, 10)) {
			waitUntilElementIsClickableByXpath(driver, locator, 10);
			try {
				driver.findElement((locator)).click();
			} catch (Exception e) {
				System.out.println("Not able to click element with CSS" + locator);
			}
		}
	}

	public static void clickSendKeysElement(WebDriver driver, By locator) throws InterruptedException {
		if (verifyElementDisplayed(driver, locator, 10)) {
			waitUntilElementIsClickableByXpath(driver, locator, 10);
			driver.findElement((locator)).click();
		}
	}

	public static boolean sendKeysElementJS(WebDriver driver, By locator, String InputValue, int Timeout)
			throws InterruptedException {
		if (verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				String Value = driver.findElement((locator)).getAttribute("value");
				if (!Value.isEmpty()) {
					driver.findElement((locator)).click();
					driver.findElement((locator)).clear();
				}
				WebElement web1l = driver.findElement(locator);
				JavascriptExecutor js1 = (JavascriptExecutor) driver;
				js1.executeScript("arguments[0].value='" + InputValue + "';", web1l);
			} catch (Exception e) {
				System.out.println("Not able tsend keys" + locator);
				return false;
			}
			return true;
		} else {
			System.out.println("Not able tsend keys" + locator);
			return false;
		}

	}

	public static boolean sendKeysElement(WebDriver driver, By locator, String InputValue, int Timeout)
			throws InterruptedException {
		if (verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				String Value = driver.findElement((locator)).getAttribute("value");
				if (!Value.isEmpty()) {
					JSWaiter.waitJQueryAngular();
					driver.findElement((locator)).click();
					driver.findElement((locator)).clear();
				}
				driver.findElement((locator)).sendKeys(InputValue);
				JSWaiter.waitJQueryAngular();
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			System.out.println("Not able tsend keys" + locator);
			return false;
		}

	}

	public static boolean checkTime(String startTime, String endTime, String checkTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
		LocalTime startLocalTime = LocalTime.parse(startTime, formatter);
		LocalTime endLocalTime = LocalTime.parse(endTime, formatter);
		LocalTime checkLocalTime = LocalTime.parse(checkTime, formatter);

		boolean isInBetween = false;
		if (endLocalTime.isAfter(startLocalTime)) {
			if (startLocalTime.isBefore(checkLocalTime) && endLocalTime.isAfter(checkLocalTime)) {
				isInBetween = true;
			}
		} else if (checkLocalTime.isAfter(startLocalTime) || checkLocalTime.isBefore(endLocalTime)) {
			isInBetween = true;
		}

		if (isInBetween) {
			return true;
		} else {
			return false;
		}
	}


	public static boolean sendKeysElement(WebDriver driver, By locator, Keys InputValue, int Timeout)
			throws InterruptedException {
		if (verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				String Value = driver.findElement((locator)).getAttribute("value");
				if (!Value.isEmpty()) {
					driver.findElement((locator)).click();
					driver.findElement((locator)).clear();
				}
				driver.findElement((locator)).sendKeys(InputValue);
			} catch (Exception e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static boolean clearText(WebDriver driver, By locator, int Timeout) throws InterruptedException {
		if (verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				driver.findElement((locator)).clear();
			} catch (Exception e) {
				System.out.println("Element is not displayed with Locator" + locator);
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static void sendKeysElementCSSSelector(WebDriver driver, By locator, String InputValue)
			throws InterruptedException {
		if (verifyElementDisplayedCSSSelector(driver, locator, 10)) {
			driver.findElement((locator)).clear();
			driver.findElement((locator)).sendKeys(InputValue);
		}
	}

	public static boolean isCheckBox(WebDriver driver, By locator, String attribute) throws InterruptedException {
		boolean status = false;
		if (verifyElementDisplayed(driver, locator, 10)) {
			if (driver.findElement((locator)).getAttribute(attribute).equalsIgnoreCase("true")) {
				status = true;
			}
		}
		return status;
	}

	public static String getElementText(WebDriver driver, By x, String attribute, int Timeout)
			throws InterruptedException {
		String eleValue = null;
		if (verifyElementDisplayed(driver, x, Timeout)) {
			if (attribute != null) {
				String eleValue1 = driver.findElement((x)).getAttribute(attribute);
				eleValue = eleValue1.trim();
			} else {
				eleValue = driver.findElement((x)).getText();		}
		}
		return eleValue;
	}

	public static String getElementText(WebDriver driver, WebElement e, String attribute, int Timeout)
			throws InterruptedException {
		String eleValue = null;
		if (verifyElementDisplayed(driver, e, Timeout)) {
			if (attribute != null) {
				String eleValue1 = e.getAttribute(attribute);
				eleValue = eleValue1.trim();
			} else {
				eleValue = e.getText();
			}
		}
		return eleValue;
	}

	public static String Retrieve_Text(WebDriver driver, WebElement element) {
		String textOnElement;
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
		textOnElement = element.getText();
		return textOnElement;
	}

	public static boolean isElementSeleted(WebDriver driver, By locator) {
		WebElement dropdownOption = driver.findElement((locator));
		if (dropdownOption.isSelected()) {
			return true;
		} else
			return false;
	}

	public static String getElementTextCSSSelector(WebDriver driver, By locator, String attribute)
			throws InterruptedException {
		String eleValue = null;
		if (verifyElementDisplayedCSSSelector(driver, locator, 1)) {
			if (attribute != null) {
				eleValue = driver.findElement((locator)).getAttribute(attribute);
			} else {
				eleValue = driver.findElement((locator)).getText();
			}
		}
		return eleValue;
	}

	public static Boolean compareElementText(WebDriver driver, By locator, String attribute, String expText)
			throws InterruptedException {
		String eleValue = null;
		Boolean status = false;
		if (verifyElementDisplayed(driver, locator, 1)) {
			if (attribute != null) {
				eleValue = driver.findElement((locator)).getAttribute(attribute);
			} else {
				eleValue = driver.findElement((locator)).getText();
			}
		}
		if (eleValue.equalsIgnoreCase(expText)) {
			status = true;
		}
		return status;
	}

	public static Boolean compareElementTextCSSSelector(WebDriver driver, By locator, String attribute, String expText)
			throws InterruptedException {
		String eleValue = null;
		Boolean status = false;
		if (verifyElementDisplayedCSSSelector(driver, locator, 1)) {
			if (attribute != null) {
				eleValue = driver.findElement((locator)).getText();
			} else {
				eleValue = driver.findElement((locator)).getAttribute(attribute);
			}
		}
		if (eleValue.equalsIgnoreCase(expText)) {
			status = true;
		}
		return status;
	}

	public static String selectDropdownOption(WebDriver driver, By locator, String option, String expValue)
			throws InterruptedException {
		String actValue = null;
		waitForpageLoad(driver, 30);
		WebElement dropdown = driver.findElement((locator));
		dropdown.isEnabled();
		List<WebElement> listvalues = dropdown.findElements(By.tagName(option));
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.elementToBeClickable((locator))).click();
		for (int i = 0; i <= listvalues.size() - 1; i++) {
			actValue = listvalues.get(i).getAttribute("innerText").trim();
			if (actValue.contains(expValue)) {
				try {
					driver.findElement(By.xpath("//option[contains(text(),'" + actValue + "')]")).click();
				} catch (Exception e) {
					try {
						driver.findElement(By.xpath("//option[contains(@value,'" + actValue + "')]")).click();
					} catch (Exception e1) {
						driver.findElement(By.xpath("//option[@label='" + actValue + "']")).click();
					}
				}
				break;
			} else {
				continue;
			}
		}
		return actValue;
	}

	/*
	 * public static boolean selectDropDown(WebDriver driver, By locator, DropDown
	 * types, String value) throws InterruptedException { boolean status = false;
	 * String type = types.toString(); if
	 * (ElementActions.verifyElementDisplayed(driver, locator, 10)) { try { Select
	 * select = new Select(driver.findElement(locator)); switch (type) { case
	 * "index": select.selectByIndex(Integer.parseInt(value)); status =
	 * verifyElementDisplayed(driver, select.getFirstSelectedOption(), 10); break;
	 * case "value": select.selectByValue(value); status =
	 * verifyElementDisplayed(driver, select.getFirstSelectedOption(), 10); break;
	 * case "visibleText": select.selectByVisibleText(value); status =
	 * verifyElementDisplayed(driver, select.getFirstSelectedOption(), 10); break;
	 * default: break; } } catch (Exception e) { } } return status; }
	 */

	public static boolean SelectDropDownOptionByVisibleText(WebDriver driver, By locator, String selectOptionName) {
		try {
			Thread.sleep(800);
			WebElement ele = driver.findElement(locator);
			Select dropdown = new Select(ele);
			dropdown.selectByVisibleText(selectOptionName);
			verifyElementDisplayed(driver, locator, 10);
		} catch (Exception e) {
			System.out.println("EXCEPTION: Error while selecting drop down option " + e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	public static boolean verifyElementSelected(WebDriver driver, By locator, String selectOptionName, int timeOut) {
		try {
			waitUntilInVisibilityOfElementByXpath(driver, locator, timeOut);
			verifyElementDisplayed(driver, locator, 10);
			WebElement elem = driver.findElement(locator);
			Select dropdowns = new Select(elem);
			Thread.sleep(1500);
			System.out.println("adada malada" + dropdowns.getFirstSelectedOption().getText());
			dropdowns.getFirstSelectedOption().getText().equalsIgnoreCase(selectOptionName);
		} catch (Exception e) {
			System.out.println("EXCEPTION: Error while selecting drop down option " + e.getMessage());
			e.printStackTrace();
		}
		return true;
	}

	public static boolean SelectDropDownOptionByVisibleText(WebDriver driver, By locator, String selectOptionName,
			int Timeout) throws InterruptedException {
		boolean status = false;
		if (verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				Thread.sleep(800);
				WebElement ele = driver.findElement(locator);
				Select dropdown = new Select(ele);
				dropdown.selectByVisibleText(selectOptionName);
				status = verifyElementDisplayed(driver, locator, 10);
				Thread.sleep(800);
			} catch (Exception e) {
				System.out.println("EXCEPTION: Error while selecting drop down option " + e.getMessage());
				e.printStackTrace();
			}
		}
		return status;
	}

	public static void SelectDropDownOptionByValue(WebDriver driver, By locator, String selectOptionName) {
		try {
			WebElement ele = driver.findElement(locator);
			Select dropdown = new Select(ele);
			dropdown.selectByValue(selectOptionName);
			Thread.sleep(800);
		} catch (Exception e) {
			System.out.println("EXCEPTION: Error while selecting drop down option " + locator + e.getMessage());
			e.printStackTrace();
		}
	}

	public static boolean SelectDropDownOptionByValue(WebDriver driver, By locator, String selectOptionName,
			int Timeout) throws InterruptedException {
		if (verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				WebElement ele = driver.findElement(locator);
				Select dropdown = new Select(ele);
				dropdown.selectByValue(selectOptionName);
				Thread.sleep(800);
			} catch (Exception e) {
				System.out.println("EXCEPTION: Error while selecting drop down option " + e.getMessage());
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static NgWebDriver getNgWebDriver(WebDriver driver){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		return (new NgWebDriver(js));
	}

	public static boolean waitForNewWindow(WebDriver driver, int timeout){
		boolean flag = false;
		int counter = 0;
		while(!flag){
			try {
				Set<String> winId = driver.getWindowHandles();
				if(winId.size() > 1){
					flag = true;
					return flag;
				}
				Thread.sleep(1000);
				counter++;
				if(counter > timeout){
					return flag;
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
		return flag;
	}

	public static void SelectDropDownOptionByIndex(WebDriver driver, By element, int selectOptionIndex) {
		try {
			WebElement ele = driver.findElement(element);
			Select dropdown = new Select(ele);
			dropdown.selectByIndex(selectOptionIndex);
			Thread.sleep(800);
		} catch (Exception e) {
			System.out.println("EXCEPTION: Error while selecting drop down option " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static boolean SelectDropDownOptionByIndex(WebDriver driver, By locator, int selectOptionIndex, int Timeout)
			throws InterruptedException {

		if (verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				WebElement ele = driver.findElement(locator);
				Select dropdown = new Select(ele);
				dropdown.selectByIndex(selectOptionIndex);
				Thread.sleep(800);
			} catch (Exception e) {
				System.out.println("EXCEPTION: Error while selecting drop down option " + e.getMessage());
				e.printStackTrace();
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static String verifyElementColor(WebDriver driver, By locator, String Attribute, String colorCode)
			throws InterruptedException {
		verifyElementDisplayed(driver, locator, 10); // background-color:
		WebElement ele = driver.findElement((locator));
		String colorrgb = ((JavascriptExecutor) driver).executeScript(
				"return window.getComputedStyle(arguments[0], ':before').getPropertyValue(" + Attribute + ");", ele)
				.toString();
		System.out.println(colorrgb);
		if (colorrgb.contentEquals(colorCode))
			;
		return colorrgb;
	}

	public static boolean verifyElementDisplayed(WebDriver driver, By locator, int Timeout)
			throws InterruptedException {
		boolean status = false;
		try {
			JSWaiter.waitJQueryAngular();
			waitUntilVisibilityOfElementByXpath(driver, locator, Timeout);
			JSWaiter.waitJQueryAngular();
			if (driver.findElement((locator)).isDisplayed())
				status = true;
		} catch (Exception e) {
			System.out.println("Element is not displayed " + locator);
			status = false;
		}
		return status;
	}

	public static boolean verifyElementDisplayed(WebDriver driver, WebElement locator, int Timeout)
			throws InterruptedException {
		boolean status = false;
		try {
			waitUntilVisibilityOfElementByXpath(driver, locator, Timeout);
			if (locator.isDisplayed())
				status = true;
		} catch (Exception e) {
			System.out.println("Element is not displayed " + locator);
			status = false;
		}
		return status;
	}

	public static boolean isAlertPresent(WebDriver driver, int Timeout) {
		boolean foundAlert = false;
		WebDriverWait wait = new WebDriverWait(driver, Timeout);
		try {
			wait.until(ExpectedConditions.alertIsPresent());
			foundAlert = true;
		} catch (TimeoutException eTO) {
			System.out.println("Element is not displayed " + eTO.getMessage());
			foundAlert = false;
		}
		return foundAlert;
	}

	public static boolean verifyElementDisplayedCSSSelector(WebDriver driver, By locator, int Timeout)
			throws InterruptedException {
		boolean status = false;
		waitUntilVisibilityOfElementByXpath(driver, locator, Timeout);
		try {
			if (driver.findElement((locator)).isDisplayed())
				status = true;
		} catch (Exception e) {
			System.out.println("Element is not displayed " + locator);
			status = false;
		}
		return status;
	}

	public static boolean verifyElementNotDisplayed(WebDriver driver, By locator, int Timeout)
			throws InterruptedException {
		String Browser = "Chrome";
		if (Browser.equalsIgnoreCase("Mobile") || Browser.contains("Mobile")) {
			boolean status = true;
			WebDriverWait wait = new WebDriverWait(driver, Timeout);
			try {
				wait.until(ExpectedConditions.visibilityOfElementLocated((locator)));
				status = false;
			} catch (Exception e) {
				status = true;
			}
			return status;
		} else {
			boolean status = false;
			WebDriverWait wait = new WebDriverWait(driver, Timeout);
			try {
				wait.until(ExpectedConditions.invisibilityOfElementLocated((locator)));
				status = true;
			} catch (Exception e) {
				status = false;
			}
			return status;
		}
	}

	public static boolean verifyElementNotEnabled(WebDriver driver, By locator, int Timeout) {
		boolean status = false;
		waitUntilVisibilityOfElementByXpath(driver, locator, Timeout);
		try {
			if (driver.findElement((locator)).isEnabled())
				status = false;
			else
				status = true;
		} catch (Exception e) {
			System.out.println("Element is not enabled " + locator);
			status = false;
		}
		return status;
	}

	public static void hover(WebDriver driver, By locator) throws InterruptedException {
		verifyElementDisplayed(driver, locator, 10);
		WebElement element = driver.findElement((locator));
		Actions builder = new Actions(driver);
		builder.moveToElement(element).perform();
	}

	// Highlight the Particular Element
	public static void highlight(WebDriver driver, By locator) {
		waitForpageLoad(driver, 5);
		WebElement ele = driver.findElement((locator));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].style.border='3px solid red'", ele);
		waitForpageLoad(driver, 5);
		js.executeScript("arguments[0].style.border=''", ele);
	}

	// Scroll To And Highlight the Particular Element
	public static void ScrollToAndHighlight(WebDriver driver, By locator) {
		waitForpageLoad(driver, 5);
		WebElement ele = driver.findElement((locator));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
		js.executeScript("arguments[0].style.border='3px solid red'", ele);
		waitForpageLoad(driver, 5);
		js.executeScript("arguments[0].style.border=''", ele);
	}

	public static String generateEmailId(String email) {
		String allchars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuvwxyz";
		StringBuilder builder = new StringBuilder();
		Random rnd = new Random();
		while (builder.length() < 10) {
			int index = (int) (rnd.nextFloat() * allchars.length());
			builder.append(allchars.charAt(index));
		}
		String emailid = builder.toString() + "@" + email.split("\\@")[1];
		return emailid;
	}

	public static void driverWait(WebDriver driver, int waitTime) {
		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
	}

	public static void waitForpageLoad(WebDriver driver, int waitTime) {
		driver.manage().timeouts().pageLoadTimeout(waitTime, TimeUnit.SECONDS);
	}

	public static boolean waitUntilVisibilityOfElementByXpath(WebDriver driver, By locator, int Timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static boolean waitUntilVisibilityOfElementByXpath(WebDriver driver, WebElement locator, int Timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Timeout);
			wait.until(ExpectedConditions.visibilityOf(locator));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void waitUntilTextToBePresentByXpath(WebDriver driver, By locator, String text, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Timeout);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
	}

	public static boolean waitUntilElementIsClickableByXpath(WebDriver driver, By locator, int Timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Timeout);
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void waitUntilElementIsClickableByXpath(WebDriver driver, WebElement locator, int Timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Timeout);
		wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static boolean waitUntilInVisibilityOfElementByXpath(WebDriver driver, By locator, int Timeout) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Timeout);
			wait.until(ExpectedConditions.invisibilityOfElementLocated((locator)));
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void selectOptionFromListWithContainTxt(List<WebElement> elements, String text) {
		try {
			if (text != null && !text.isEmpty()) {
				for (int i = 0; i < elements.size(); i++) {
					if (elements.get(i).getText().toLowerCase().contains(text.toLowerCase())) {
						elements.get(i).click();
						break;
					}
				}
			} else {
				Collections.shuffle(elements);
				elements.get(0).click();
			}
		} catch (Exception e) {
			
					e.printStackTrace();
		}
	}

	public static void selectRandomOption(List<WebElement> elements, String ignoreText) {
		try {
			Collections.shuffle(elements);
			if (ignoreText != null && !ignoreText.isEmpty()) {
				for (int i = 0; i < elements.size(); i++) {
					if (!elements.get(i).getText().matches(ignoreText)) {
						elements.get(i).click();
						break;
					}
				}
			} else {
				Collections.shuffle(elements);
				elements.get(0).click();
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION: Error while selecting option from list with equal text " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void selectDate(WebDriver driver, By element, String date) {
		try {
			clickElement(driver, element, 10);
			date = date.replaceAll("/", "-");
			String dateParts[] = date.split("-");
			String day = "1", month = null, year = null;
			if (!dateParts[0].matches("0|00|null")) {
				day = dateParts[0];
			} else {
				//day = String.valueOf(RandomData_Generation.RandomNumber(27, 1));
			}
			if (!dateParts[1].matches("0|00|null")) {
				month = dateParts[1];
			}
			if (!dateParts[2].matches("0|00|0000|null")) {
				year = dateParts[2];
			}
			List<WebElement> yearList = driver.findElements(By.xpath("//select[@data-handler='selectYear']/option"));
			selectOptionFromListWithEqualTxt(yearList, year);
			List<WebElement> monthList = driver.findElements(By.xpath("//select[@data-handler='selectMonth']/option"));
			selectOptionFromListWithEqualTxt(monthList, month);
			driver.findElement(By.xpath("//td[@data-handler='selectDay']//a[contains(text(),'" + day + "')]")).click();
		} catch (Exception e) {
			System.out.println("EXCEPTION: Error while selecting date from calendar " + element);
			e.printStackTrace();
		}
	}

	public static void selectInsuranceDate(WebDriver driver, By element, String date) {
		try {
			clickElementJS(driver, element, 20);
			date = date.replaceAll("/", "-");
			String dateParts[] = date.split("-");
			String day = "1", month = null, year = null;
			if (!dateParts[0].matches("0|00|null")) {
				day = dateParts[0];
			} else {
				//day = String.valueOf(RandomData_Generation.RandomNumber(27, 1));
			}
			if (!dateParts[1].matches("0|00|null")) {
				month = dateParts[1];
			}
			if (!dateParts[2].matches("0|00|0000|null")) {
				year = dateParts[2];
			}
			verifyElementDisplayed(driver, By.xpath("//table//td/div[1]"), 10);
			List<WebElement> yearList = driver.findElements(By.xpath("//table//td/div[1]"));
			WebElement ele = driver.findElement(By.xpath("//table//tr[1]//td[1]/div[1]"));
			String year1 = getElementText(driver, ele, "innerText", 10);
			if (year1.startsWith("20")) {
				ElementActions.clickElement(driver, By.xpath("//button[@aria-label='Previous 20 years']"), 10);
				verifyElementDisplayed(driver, By.xpath("//table//td/div[1]"), 10);
				yearList = driver.findElements(By.xpath("//table//td/div[1]"));
			}
			selectOptionFromListWithEqualTxt(yearList, year);
			List<WebElement> monthList = driver.findElements(By.xpath("//table//td/div[1]"));
			selectOptionFromListWithEqualTxt(monthList, month);
			List<WebElement> dayList = driver.findElements(By.xpath("//table//td/div[1]"));
			selectOptionFromListWithEqualTxt(dayList, day);

		} catch (Exception e) {
			System.out.println("EXCEPTION: Error while selecting date from calendar " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void selectOptionFromListWithEqualTxt(List<WebElement> elements, String text) {
		try {
			if (text != null && !text.isEmpty()) {
				for (int i = 0; i < elements.size(); i++) {
					if (elements.get(i).getText().equalsIgnoreCase(text)) {
						elements.get(i).click();
						Thread.sleep(2000);
						break;
					}
				}
			} else {
				Collections.shuffle(elements);
				elements.get(0).click();
			}
		} catch (Exception e) {
			System.out.println("EXCEPTION: Error while selecting option from list with equal text " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void writeConfFile(String scenario, String order) {
		try {
			FileWriter fstream = new FileWriter("src/main/resources/output.txt", true);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Date:" + new Date());
			out.newLine();
			out.write("ScenarioName:" + scenario);
			out.newLine();
			out.write("OrderNo.:" + order);
			out.newLine();
			out.close();
		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public static void addScreenShotToReport(WebDriver driver) throws Exception {
		Properties config = new Properties();
		FileInputStream ip = new FileInputStream("src/main/resources/config.properties");
		config.load(ip);
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = Paths.get("").toAbsolutePath().toString() + "/FailedTestsScreenshots/" + dateName + ".png";
		File finalDestination = new File(destination);
		FileHandler.copy(source, finalDestination);
	}

	public static void waitForBrowserToLoad(WebDriver driver) {
		Boolean readyStateComplete = false;
		for (int i = 0; i < 60; i++) {
			try {
				Thread.sleep(1000);
				if (!readyStateComplete) {
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					readyStateComplete = executor.executeScript("return document.readyState").equals("complete");
					break;
				} else {
					System.out.println(readyStateComplete);
					continue;
				}
			} catch (Exception e) {
				continue;
			}
		}
	}

	public static String replace(String str, int index, char replace) {
		if (str == null) {
			return str;
		} else if (index < 0 || index >= str.length()) {
			return str;
		}
		char[] chars = str.toCharArray();
		chars[index] = replace;
		return String.valueOf(chars);
	}

	public static int getHeight(WebDriver driver, By locator) {
		WebElement element = driver.findElement((locator));
		int Height = element.getSize().getHeight();
		return Height;
	}

	public static int getWidth(WebDriver driver, By locator) {
		WebElement element = driver.findElement((locator));
		int Width = element.getSize().getWidth();
		return Width;
	}

	public static String getScenarioName() throws Exception {
		Properties config = new Properties();
		FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
		config.load(ip);
		String InitScenarioNumber = config.getProperty("ScenarioNumber");
		int IntScenarioNumber = Integer.parseInt(InitScenarioNumber);
		int ScenarioNumber = IntScenarioNumber + 1;
		String FinalScenarioNumber = Integer.toString(ScenarioNumber);
		String ScenarioID = "Scenario " + FinalScenarioNumber;
		System.out.println("The scenario ID for this test case is :" + ScenarioID);
		FileOutputStream out = new FileOutputStream("./src/test/resources/config/config.properties");
		config.setProperty("ScenarioNumber", FinalScenarioNumber);
		config.setProperty("ScenarioID", ScenarioID);
		config.store(out, null);
		out.close();
		return ScenarioID;
	}

	public static String ColorValidation(WebDriver driver, By locator) throws Exception {
		String color = driver.findElement((locator)).getCssValue("color");
		String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");
		int hexValue1 = Integer.parseInt(hexValue[0]);
		hexValue[1] = hexValue[1].trim();
		int hexValue2 = Integer.parseInt(hexValue[1]);
		hexValue[2] = hexValue[2].trim();
		int hexValue3 = Integer.parseInt(hexValue[2]);
		String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
		return actualColor;
	}

	public static String fontValidation(WebDriver driver, By locator) throws Exception {
		String font = driver.findElement((locator)).getCssValue("font-family");
		return font;
	}

	public static String BackgroundColorValidation(WebDriver driver, By locator) throws Exception {
		String color = driver.findElement((locator)).getCssValue("background-color");
		String[] hexValue = color.replace("rgba(", "").replace(")", "").split(",");
		int hexValue1 = Integer.parseInt(hexValue[0]);
		hexValue[1] = hexValue[1].trim();
		int hexValue2 = Integer.parseInt(hexValue[1]);
		hexValue[2] = hexValue[2].trim();
		int hexValue3 = Integer.parseInt(hexValue[2]);
		String actualColor = String.format("#%02x%02x%02x", hexValue1, hexValue2, hexValue3);
		return actualColor;
	}

	private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, int timeoutInSeconds) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
		webDriverWait.withTimeout(Duration.ofSeconds(timeoutInSeconds));
		try {
			webDriverWait.until(waitCondition);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void switchToNewTab(WebDriver driver, int tabno) {
		try {
			((JavascriptExecutor) driver).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(tabno));
		} catch (Exception e) {
			System.out.println("@failed in @switchToNewTab " + e.getCause() + "/n" + e.getMessage());
		}
	}

	public static void switchToNextTab(WebDriver driver) throws Exception {
		try {
			Thread.sleep(2000);
			ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
			driver.switchTo().window(tab.get(1));
		} catch (Exception e) {
			throw new Exception("Child tab is not displayed");
		}
	}

	public static void switchToThirdTab(WebDriver driver) throws Exception {
		try {
			Thread.sleep(2000);
			ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
			driver.switchTo().window(tab.get(2));
		} catch (Exception e) {
			throw new Exception("Child tab is not displayed");
		}
	}

	public static void closeCurrentTab(WebDriver driver) throws Exception {
		try {
			Thread.sleep(2000);
			ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
			if (tab.size() != 1) {
				driver.close();
				driver.switchTo().window(tab.get(0));
			}
		} catch (Exception e) {
			throw new Exception("Child tab is not closed");
		}
	}

	public static void switchToPreviousTab(WebDriver driver) throws Exception {
		try {
			Thread.sleep(1000);
			ArrayList<String> tab = new ArrayList<>(driver.getWindowHandles());
			if (tab.size() != 1) {
				driver.switchTo().window(tab.get(0));
			}
		} catch (Exception e) {
			throw new Exception("Child tab is not displayed");
		}
	}

	public static boolean clickElementJS(WebDriver driver, By locator, int Timeout) throws InterruptedException {
		if (verifyElementDisplayed(driver, locator, Timeout)) {
			try {
				waitUntilElementIsClickableByXpath(driver, locator, Timeout);
				WebElement element = driver.findElement((locator));
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} catch (Exception e) {
				// Assert.assertTrue(false, "Not able to click element with xpath " + locator);
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public static void untilJqueryIsDone(WebDriver driver, int timeoutInSeconds) {
		String Browser = "Chrome";
		if (Browser.equalsIgnoreCase("Mobile") || Browser.contains("Mobile")) {
		} else {
			until(driver, (d) -> {
				Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) driver)
						.executeScript("return jQuery.active==0");
				if (!isJqueryCallDone) {

				}
				return isJqueryCallDone;
			}, timeoutInSeconds);
		}
	}

	public static void EnterKeys(WebDriver driver, By locator, Keys enter, int Timeout) throws InterruptedException {
		verifyElementDisplayed(driver, locator, Timeout);
		String Value = driver.findElement((locator)).getAttribute("value");
		if (!Value.isEmpty()) {
			driver.findElement((locator)).click();
			driver.findElement((locator)).clear();
		}
		driver.findElement((locator)).sendKeys(enter);
	}

	public static void DoubleClick_Element(WebDriver driver, By locator) throws InterruptedException {
		verifyElementDisplayed(driver, locator, 10);
		WebElement element = driver.findElement((locator));
		Actions builder = new Actions(driver);
		builder.doubleClick(element).perform();
	}

	public static int getCountOfOptions(WebDriver driver, By locator) throws InterruptedException {
		verifyElementDisplayed(driver, locator, 10);
		Select s = new Select(driver.findElement(locator));
		List<WebElement> countOpt = s.getOptions();
		int size = countOpt.size();
		return size;
	}

	/*
	 * public static By getElement(String text, ElementText span) { By result =
	 * By.xpath("//*[normalize-space()='" + text + "']"); String service =
	 * span.toString(); switch (service) { case "span": result =
	 * By.xpath("//span[normalize-space()='" + text + "']"); break;
	 * 
	 * case "link": result = By.xpath("//a[normalize-space()='" + text + "']");
	 * break;
	 * 
	 * case "header": result = By.xpath("//h1[normalize-space()='" + text + "']");
	 * break;
	 * 
	 * case "table": result = By.xpath("//td[contains(normalize-space(),'" + text +
	 * "')]"); break;
	 * 
	 * default: break; } return result; }
	 */

	public static void ZoomInOut(WebDriver driver, String percentage) throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("document.body.style.zoom = '" + percentage + "';");
	}

	public static void uploadImageRobot(WebDriver driver, By locator, String imagepath)
			throws AWTException, InterruptedException {
		WebElement browse = driver.findElement(locator);
		browse.click();
		Thread.sleep(5000);
		Robot robot = new Robot();
		String filepath = System.getProperty("user.dir") + imagepath;
		System.out.println("filepath is " + filepath);
		StringSelection str = new StringSelection(filepath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		/*
		 * robot.keyPress(KeyEvent.VK_ENTER); robot.keyRelease(KeyEvent.VK_ENTER);
		 * robot.keyPress(KeyEvent.VK_CONTROL); robot.keyPress(KeyEvent.VK_V);
		 * robot.keyRelease(KeyEvent.VK_V); robot.keyRelease(KeyEvent.VK_CONTROL);
		 * robot.keyPress(KeyEvent.VK_ENTER); robot.keyRelease(KeyEvent.VK_ENTER);
		 */
	}

	public static void selectDate(WebDriver driver, By locator, String targetDate, String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat targetDateFormat = new SimpleDateFormat(dateFormat);
		Date formattedTargetDate;
		try {
			targetDateFormat.setLenient(false);
			formattedTargetDate = targetDateFormat.parse(targetDate);
			calendar.setTime(formattedTargetDate);
			int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
			int targetMonth = calendar.get(Calendar.MONTH);
			int targetYear = calendar.get(Calendar.YEAR);
			String actualDate = ElementActions.getElementText(driver, locator, "innerText", 10);
			calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(actualDate));
			int actualMonth = calendar.get(Calendar.MONTH);
			int actualYear = calendar.get(Calendar.YEAR);
			while (targetMonth < actualMonth || targetYear < actualYear) {
				// click prev button
				actualDate = ElementActions.getElementText(driver, locator, "innerText", 10);
				calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(actualDate));
				actualMonth = calendar.get(Calendar.MONTH);
				actualYear = calendar.get(Calendar.YEAR);
			}
			while (targetMonth > actualMonth || targetYear > actualYear) {
				// click next button
				actualDate = ElementActions.getElementText(driver, locator, "innerText", 10);
				calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(actualDate));
				actualMonth = calendar.get(Calendar.MONTH);
				actualYear = calendar.get(Calendar.YEAR);
			}
		} catch (Exception e) {

		}

	}

	// TOUOCH ACTION FUNCTIONS ***

	public static boolean isElementDisplayed(WebDriver driver, By by) {
		try {
			return driver.findElement(by).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isElementDisplayed(WebDriver driver, By by, int wait) {
		try {
			ElementActions.waitForVisible(driver, by, wait);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static void waitForVisible(WebDriver driver, By by, int t) {
		WebDriverWait wait = new WebDriverWait(driver, t);
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	// element = point where to start swipe-left, percent is portion of x - swipe
	public static void swipeLeftActionPercentAndroid(WebDriver driver, By element, double per) {
		WebElement el = driver.findElement(element);
		Point elPoint = el.getLocation();
		Dimension screenSize = driver.manage().window().getSize();

		int startX = Math.toIntExact(Math.round(screenSize.getWidth() * per));
		int endX = 0;

		touchAction.press(PointOption.point(startX, elPoint.getY()))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
				.moveTo(PointOption.point(endX, elPoint.getY())).release();
		androidDriver = ((AndroidDriver<MobileElement>) driver);
		androidDriver.performTouchAction(touchAction);
	}

	// element = point where to start swipe-left, percent is portion of x - swipe
	public static void swipeLeftActionPercentAndroid(WebDriver driver, By element, double per, int speed) {
		WebElement el = driver.findElement(element);
		Point elPoint = el.getLocation();
		Dimension screenSize = driver.manage().window().getSize();

		int startX = Math.toIntExact(Math.round(screenSize.getWidth() * per));
		int endX = 0;

		touchAction.press(PointOption.point(startX, elPoint.getY()))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(speed)))
				.moveTo(PointOption.point(endX, elPoint.getY())).release();
		androidDriver = ((AndroidDriver<MobileElement>) driver);
		androidDriver.performTouchAction(touchAction);
	}

	// element = point where to start swipe-right, percent is portion of x - swipe
	public static void swipeRightActionPercentAndroid(WebDriver driver, By element, double per) {
		WebElement el = driver.findElement(element);
		Point elPoint = el.getLocation();
		Dimension screenSize = driver.manage().window().getSize();

		int startX = 0;
		int endX = Math.toIntExact(Math.round(screenSize.getWidth() * per));

		touchAction.press(PointOption.point(startX, elPoint.getY()))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
				.moveTo(PointOption.point(endX, elPoint.getY())).release();

		androidDriver = ((AndroidDriver<MobileElement>) driver);
		androidDriver.performTouchAction(touchAction);
	}

	// till the element not displayed, keep swiping-up, with 10 passes
	public static void swipeUpActionAndroid(WebDriver driver, By element) {

		Dimension screenSize = driver.manage().window().getSize();
		int endY = 0;
		int count = 0;
		while (!isElementDisplayed(driver, element, 8) && count < 10) {

			int startY = Math.toIntExact(Math.round(screenSize.getHeight() * 0.8));
			int startX = Math.toIntExact(Math.round(screenSize.getWidth() * 0.8));

			touchAction.press(PointOption.point(startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).moveTo(PointOption.point(startX, endY))
					.release();

			androidDriver = ((AndroidDriver<MobileElement>) driver);
			androidDriver.performTouchAction(touchAction);
			++count;
		}

	}

	// till the element not displayed, keep swiping-up, with 10 passes
	// speed in millis
	// percent is movement
	public static void swipeUpActionAndroid(WebDriver driver, By element, float percent, int speed) {
		Dimension screenSize = driver.manage().window().getSize();
		int endY = 0;
		int count = 0;
		while (!isElementDisplayed(driver, element, 8) && count < 10) {

			int startY = Math.toIntExact(Math.round(screenSize.getHeight() * percent));
			int startX = Math.toIntExact(Math.round(screenSize.getWidth() * percent));

			touchAction.press(PointOption.point(startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(speed)))
					.moveTo(PointOption.point(startX, endY)).release();
			androidDriver = ((AndroidDriver<MobileElement>) driver);
			androidDriver.performTouchAction(touchAction);
			++count;
		}

	}

	public static void swipeUpActionAndroid(WebDriver driver, By element, float percent, int speed, int counter) {
		Dimension screenSize = driver.manage().window().getSize();
		int endY = 0;
		int count = 0;
		while (!isElementDisplayed(driver, element, 8) && count < counter) {

			int startY = Math.toIntExact(Math.round(screenSize.getHeight() * percent));
			int startX = Math.toIntExact(Math.round(screenSize.getWidth() * percent));

			touchAction.press(PointOption.point(startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(speed)))
					.moveTo(PointOption.point(startX, endY)).release();
			androidDriver = ((AndroidDriver<MobileElement>) driver);
			androidDriver.performTouchAction(touchAction);
			++count;
		}

	}

	// Scroll up once
	public static void scrollUpAndroid(WebDriver driver) {

		Dimension screenSize = driver.manage().window().getSize();
		int endY = 0;

		int startY = Math.toIntExact(Math.round(screenSize.getHeight() * 0.4));
		int startX = Math.toIntExact(Math.round(screenSize.getWidth() * 0.4));

		touchAction.press(PointOption.point(startX, startY))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(6000))).moveTo(PointOption.point(startX, endY))
				.release();

		androidDriver = ((AndroidDriver<MobileElement>) driver);
		androidDriver.performTouchAction(touchAction);
	}

	// till the element not displayed, keep swiping-down, with 10 passes
	public static void swipeDownActionAndroid(WebDriver driver, By element) {

		int count = 0;
		while (!isElementDisplayed(driver, element, 8) && count < 10) {

			int startY = 200;
			int endY = 700;
			int startX = 500;

			touchAction.press(PointOption.point(startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(300))).moveTo(PointOption.point(startX, endY))
					.release();

			androidDriver = ((AndroidDriver<MobileElement>) driver);
			androidDriver.performTouchAction(touchAction);
			++count;
		}

	}

	// till the element not displayed, keep swiping-down, with 10 passes
	// speed in millis
	public static void swipeDownActionAndroid(WebDriver driver, By element, int speed) {

		int count = 0;
		while (!isElementDisplayed(driver, element, 8) && count < 10) {

			int startY = 500;
			int endY = 1200;
			int startX = 500;

			touchAction.press(PointOption.point(startX, startY))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(speed)))
					.moveTo(PointOption.point(startX, endY)).release();

			androidDriver = ((AndroidDriver<MobileElement>) driver);
			androidDriver.performTouchAction(touchAction);
			++count;
		}
	}

	// ADB SUPPORT

	public static synchronized Map<String, String> getKeyboardState() {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Process process = Runtime.getRuntime().exec(KEY_BOARD_OPEN_CMD);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = reader.readLine();
			map = fromStringToMap(line.trim(), " ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	public static Map<String, String> fromStringToMap(String string, String splitBy) {
		Map<String, String> map = new HashMap<String, String>();
		String[] keyValuePairs = string.split(splitBy);
		for (String pair : keyValuePairs) {
			String[] entry = pair.split("=");
			map.put(entry[0].trim(), entry[1].trim());
		}
		return map;
	}

	public static void ADB_Toggle_Airplane(WebDriver driver) throws IOException, InterruptedException {
		try {
			// AirplaneModeToggle();
			Thread.sleep(5000);
			
			clickElement(driver, By.xpath("//android.widget.CheckBox | //android.widget.Switch"), 10);
			Thread.sleep(3000);
		} catch (Exception err) {
			err.printStackTrace();
		}

	}

	public static void ADB_Toggle_Airplane() {
		
		
	}

	// this method will scroll down until last page and get the list of all the
	// elements till end
	public Set<String> scrollDownAndGetListOfAllElementsTillEnd(WebDriver driver, By by) throws IOException {
		int count = 15;
		Set<String> new_set;
		Set<String> temp = ElementActions.getAllElementsTextSet(driver, by);
		for (int i = 0; i < count; i++) {
			Set<String> currentSet = ElementActions.getAllElementsTextSet(driver, by);
			Set<String> union = Sets.union(temp, currentSet);
			
			new_set = ElementActions.getAllElementsTextSet(driver, by);
			if (compare2Sets(new_set, currentSet)) {
				return union;
			}
		}
		return null;
	}

	// this method will compare 2 sets and return boolean val
	public boolean compare2Sets(Set<String> obj1, Set<String> obj2) {
		int count = 0;
		for (String s1 : obj1) {
			for (String s2 : obj2) {
				if (s1.equals(s2)) {
					count++;
				}
			}
		}
		if (count == obj1.size() && count == obj1.size()) {
			return true;
		} else {
			return false;
		}
	}

	public static Set<String> getAllElementsTextSet(WebDriver driver, By by) {
		ElementActions.waitForVisible(driver, by, 20);
		List<WebElement> webElements = getAllElements(driver, by);
		Set<String> textList = new HashSet<String>();
		for (WebElement ele : webElements)
			textList.add(ele.getText());
		return textList;
	}

	public static List<WebElement> getAllElements(WebDriver driver, By by) {
		return driver.findElements(by);
	}

	public static void clickSendKeysElement(WebDriver driver, By locator, String value) throws InterruptedException {
		waitUntilVisibilityOfElementByXpath(driver, locator,10);
		driver.findElement((locator)).click();
		driver.findElement(locator).sendKeys(value);
	}

	public static void clickElement(WebDriver driver, By locator) throws InterruptedException {
		waitUntilVisibilityOfElementByXpath(driver, locator,10);
			driver.findElement(locator).click();
		}


	public static void selectAnAngulardate(WebDriver driver, By locator, int Timeout, int days,String pattern)
			throws InterruptedException {
		waitUntilVisibilityOfElementByXpath(driver, locator,10);
		String s;
		Date date;
		Format formatter;
		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		calendar.add(Calendar.DATE, days);
		date = calendar.getTime();
		formatter = new SimpleDateFormat(pattern);
		s = formatter.format(date);
		System.out.println("The date is "+s);
		driver.findElement(locator).sendKeys(s);
	}

}
