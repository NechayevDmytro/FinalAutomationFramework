package pages;

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

    public String getScreenSize() {
        wait.until(ExpectedConditions.visibilityOf(screenSize));
        return screenSize.getText();
    }
}
