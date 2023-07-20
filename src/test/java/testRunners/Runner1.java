package testRunners;



import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/com/features/Medical" }, glue = {
		"stepDefinitions", "appHooks" })
public class Runner1 extends MyRunner {

}
