package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.util.ElementActions;

public class LoginPage {
 private WebDriver driver;
 private By emailID = By.id("email");
 private By password = By.xpath("[][][][][]");
 
 
 
 public LoginPage(WebDriver driver) {
	 this.driver=driver;
 }
 
 
 public boolean getPageTitle() throws InterruptedException {
	 ElementActions.clickElement(driver, emailID, 30);
	 ElementActions.sendKeysElement(driver, emailID, "Test", 30);
	 return ElementActions.isElementDisplayed(driver, password);
	 
 }
 
}
