package testRunners;



import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = { "login.feature2" }, glue = {
		"com.qa.stepDefs", "com.qa.executors" }, plugin = { "rerun:target/failedrerun1.txt" })
public class Runner2 extends MyRunner {

}
