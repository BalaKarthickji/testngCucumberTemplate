package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.util.ElementActions;

public class LoginPage {
 private WebDriver driver;
 private By emailID = By.id("email");
 private By password = By.xpath("[][][][][]");
 private By ok = By.cssSelector(".ok_button");
 private By admin = By.xpath("//*[@id='mat-radio-2']//input");
 
 public LoginPage(WebDriver driver) {
	 this.driver=driver;
 }
 
 
 public void getPageTitle() throws InterruptedException {
     Thread.sleep(5000);

	 ElementActions.clickElementJS(driver, admin, 30);
     Thread.sleep(5000);
     System.out.println(driver.getCurrentUrl());
	 ElementActions.clickElementJS(driver, ok, 30);

	 
	// ElementActions.sendKeysElement(driver, emailID, "Test", 30);
	 //return ElementActions.isElementDisplayed(driver, password);
	 
 }
 
}
