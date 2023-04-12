package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import pages.MainPage;
import pages.MobilePhonesPage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class BaseTest {
    protected WebDriver driver;
    protected MainPage mainPage;
    protected MobilePhonesPage mobilePhonesPage;

    @BeforeMethod(alwaysRun = true)
    public void initBrowser() {
        WebDriverManager.chromedriver().arch64().browserVersion("111").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("disable_notifications", "start-maximized", "incognito");
        options.setExperimentalOption("excludeSwitches", Collections.singleton("enable-automation"));
        driver = new ChromeDriver(options);
    }

    @BeforeMethod(dependsOnMethods = {"initBrowser"}, alwaysRun = true)
    public void openRztk() {
        driver.get("https://rozetka.com.ua/");
        mainPage = new MainPage(driver);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    public String getPropertyValue(String propName) {
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/data.properties");
            prop.load(fis);
        } catch (IOException ignored) {}
        return prop.getProperty(propName);
    }
}
