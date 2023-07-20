package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.util.ElementActions;

public class LoginPage {
 private WebDriver driver;
 private By emailID = By.id("email");
 private By password = By.xpath("[][][][][]");
 private By ok = By.cssSelector(".ok_button");
 private By admin = By.xpath("//*[@id=\"mat-radio-2\"]");
 
 public LoginPage(WebDriver driver) {
	 this.driver=driver;
 }
 
 
 public void getPageTitle() throws InterruptedException {
	 ElementActions.clickElementJS(driver, admin, 30);
	 ElementActions.clickElementJS(driver, ok, 30);
	 ElementActions.waitForpageLoad(driver, 20);
	 
	// ElementActions.sendKeysElement(driver, emailID, "Test", 30);
	 //return ElementActions.isElementDisplayed(driver, password);
	 
 }
 
}
