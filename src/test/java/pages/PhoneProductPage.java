package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PhoneProductPage extends BasePage {

    @FindBy(xpath = "//a[contains(text(), 'Характеристики')]")
    private WebElement characteristics;

    public PhoneProductPage(WebDriver driver) {
        super(driver);
    }

    public CharacteristicsPage clickCharacteristics() {
        wait.until(ExpectedConditions.visibilityOf(characteristics));
        characteristics.click();
        return new CharacteristicsPage(driver);
    }
}
