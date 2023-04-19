package ui.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CharacteristicsPage extends BasePage {

    @FindBy(xpath = "//span[contains(text(), 'Діагональ екрана')]/../../dd//a")
    private WebElement screenSize;

    public CharacteristicsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Get screen size value")
    public String getScreenSize() {
        wait.until(ExpectedConditions.not(ExpectedConditions.stalenessOf(screenSize)));
        return screenSize.getText();
    }
}
