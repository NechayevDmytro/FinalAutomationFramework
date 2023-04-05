package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage{

    @FindBy(xpath = "(//ul[contains(@class, 'menu-categories_type_main')]/li)[2]")
    private WebElement phonesAndElectronics;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public PhonesAndElectronicsPage clickPhonesAndElectronics() {
        wait.until(ExpectedConditions.visibilityOf(phonesAndElectronics));
        phonesAndElectronics.click();
        return new PhonesAndElectronicsPage(driver);
    }
}
