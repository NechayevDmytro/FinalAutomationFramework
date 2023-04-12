package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class MobilePhonesPage extends BasePage {

    @FindBy(xpath = "(//span[contains(text(), 'Діагональ екрана')]/../..//a)[4]")
    private WebElement fiveAndHalfToSixInchesScreenSizeCheckbox;

    @FindBy(xpath = "//h1[contains(@class, 'catalog-heading')]")
    private WebElement headingText;

    @FindBy(xpath = "(//div[@class='goods-tile__inner'])[1]")
    private WebElement firstPhone;

    @FindBy(xpath = "//input[@formcontrolname='min']")
    private WebElement minPrice;

    @FindBy(xpath = "//input[@formcontrolname='max']")
    private WebElement maxPrice;

    @FindBy(xpath = "//span[@class='goods-tile__price-value']")
    private List<WebElement> pricesList;

    public MobilePhonesPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 5.55\" - 6\" screen size checkbox")
    public MobilePhonesPage clickCheckbox() {
        wait.until(ExpectedConditions.visibilityOf(fiveAndHalfToSixInchesScreenSizeCheckbox));
        fiveAndHalfToSixInchesScreenSizeCheckbox.click();
        wait.until(ExpectedConditions.or(
                        ExpectedConditions.attributeContains(fiveAndHalfToSixInchesScreenSizeCheckbox, "class", "checked"),
                        ExpectedConditions.attributeContains(fiveAndHalfToSixInchesScreenSizeCheckbox, "class", "active"))
                );
        wait.until(new Function<WebDriver, Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                while(driver.findElement(By.xpath("//h1[contains(@class, 'catalog-heading')]")).getText()
                        .equals("Мобільні телефони")) {continue;}
                return true;
            }
        });
        return this;
    }

    public String getHeadingText() {
        return headingText.getText();
    }

    @Step("Click on 1st phone picture")
    public PhoneProductPage clickFirstPhone() {
        jse.executeScript("arguments[0].scrollIntoView();", firstPhone);
        firstPhone.click();
        return new PhoneProductPage(driver);
    }

    @Step("Set {0} grn as minimal price and {1} grn as maximal price")
    public MobilePhonesPage setMinAndMaxPrice(int min, int max) {
        minPrice.clear();
        minPrice.sendKeys(String.valueOf(min), Keys.TAB, String.valueOf(max), Keys.ENTER);
        return this;
    }

    @Step("Get list of price")
    public List<Integer> getPricesList() {
        wait.until(ExpectedConditions.visibilityOfAllElements(pricesList));
        List<Integer> stringList = new ArrayList<>();
        for (WebElement el : pricesList) {
            String str = el.getText().replace(" ", "");
            String finalString = str.substring(0, str.length() - 1);
            stringList.add(Integer.parseInt(finalString));
        }
        return stringList;
    }
}
