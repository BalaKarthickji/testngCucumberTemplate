package testRunners;



import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/com/features/RishInsurer" }, glue = {
		"stepDefinitions", "appHooks" })
public class Runner3 extends MyRunner {

}
