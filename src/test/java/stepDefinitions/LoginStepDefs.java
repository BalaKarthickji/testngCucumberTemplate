package stepDefinitions;


import com.pages.LoginPage;
import com.qa.factory.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class LoginStepDefs {
	
	
	LoginPage lp = new LoginPage(DriverFactory.getDriver());
	
	@Given("I want to write a step with precondition")
	public void i_want_to_write_a_step_with_precondition() throws InterruptedException {
		//OpenURL
		DriverFactory.getDriver().get("https://1careafrica-dev.syncierapps.com/");
	    Assert.assertTrue(lp.getPageTitle(), "The homepage is not displayed");
	   
	   // Assert.assertEquals(pageTitle, "Login Page", "Page does not open");
	   // Assert.assertEquals(pageTitle, "Email field is not displayed");
	}

	@Given("some other precondition")
	public void some_other_precondition() {

	}

	@When("I complete action")
	public void i_complete_action() {

	}

	@When("some other action")
	public void some_other_action() {

	}

	@When("yet another action")
	public void yet_another_action() {

	}

	@Then("I validate the outcomes")
	public void i_validate_the_outcomes() {

	}

	@Then("check more outcomes")
	public void check_more_outcomes() {

	}

}
