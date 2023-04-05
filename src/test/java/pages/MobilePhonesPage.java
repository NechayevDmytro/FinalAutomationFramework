package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MobilePhonesPage extends BasePage {

    @FindBy(xpath = "(//span[contains(text(), 'Діагональ екрана')]/../..//a)[4]")
    private WebElement fiveAndHalfToSixInchesScreenSizeCheckbox;

    @FindBy(xpath = "//h1[contains(@class, 'catalog-heading')]")
    private WebElement headingText;

    @FindBy(xpath = "(//div[@class='goods-tile__inner'])[1]")
    private WebElement firstPhone;

    public MobilePhonesPage(WebDriver driver) {
        super(driver);
    }

    public void clickCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(fiveAndHalfToSixInchesScreenSizeCheckbox));
        fiveAndHalfToSixInchesScreenSizeCheckbox.click();
        wait.until(ExpectedConditions.attributeContains(fiveAndHalfToSixInchesScreenSizeCheckbox, "class", "checked"));
    }

    public String getHeadingText() {
        return headingText.getText();
    }

    public PhoneProductPage clickFirstPhone() {
        jse.executeScript("arguments[0].scrollIntoView();", firstPhone);
        firstPhone.click();
        return new PhoneProductPage(driver);
    }
}
