package ui.defs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.Collections;

public class Hooks {
    public static WebDriver driver;

    @Before
    public void initWebdriver(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@UI")) {
            WebDriverManager.chromedriver().arch64().browserVersion("111").setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("disable_notifications", "start-maximized", "incognito");
            options.setExperimentalOption("excludeSwitches", Collections.singleton("enable-automation"));
            driver = new ChromeDriver(options);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.getSourceTagNames().contains("@UI")) {
            driver.quit();
        }
    }
}
