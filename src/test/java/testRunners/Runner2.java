package testRunners;



import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "src/test/resources/com/features/test" }, glue = {
		"stepDefinitions", "appHooks" })
public class Runner2 extends MyRunner {

}
