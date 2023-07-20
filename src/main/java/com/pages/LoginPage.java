package com.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.util.ElementActions;

public class LoginPage {
 private WebDriver driver;
 private By emailID = By.id("email");
 private By password = By.xpath("[][][][][]");
 private By ok = By.xpath("//button[@class='ok_button']");
 private By admin = By.xpath("//*[@id='mat-radio-2']//input");
 private By searchButton = By.xpath("//img[fgd@src='assets/images/search.svg']");
 
 public LoginPage(WebDriver driver) {
	 this.driver=driver;
 }
 
 
 public boolean getPageTitle() throws InterruptedException {
     Thread.sleep(5000);

	 ElementActions.clickElementJS(driver, admin, 30);
     ElementActions.clickElementJS(driver, ok, 30);
	 return ElementActions.isElementDisplayed(driver, searchButton, 20);

	 
	// ElementActions.sendKeysElement(driver, emailID, "Test", 30);
	 //return ElementActions.isElementDisplayed(driver, password);
	 
 }
 
}
