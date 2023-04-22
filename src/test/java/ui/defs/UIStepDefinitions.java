package ui.defs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import ui.pages.MainPage;
import ui.pages.MobilePhonesPage;
import ui.pages.PhonesAndElectronicsPage;

import java.util.List;

import static ui.defs.Hooks.driver;

public class UIStepDefinitions {
    private MainPage mainPage;
    private PhonesAndElectronicsPage phonesAndElectronicsPage;
    private MobilePhonesPage mobilePhonesPage;
    private List<Integer> pricesList;
    private int min;
    private int max;

    @Given("I open Rozetka website")
    public void iOpenRozetkaWebsite() {
        driver.get("https://rozetka.com.ua/");
        mainPage = new MainPage(driver);
    }

    @And("I click Smartphones and Electronics from left menu")
    public void iClickSmartphonesAndElectronicsFromLeftMenu() {
        phonesAndElectronicsPage = mainPage.clickPhonesAndElectronics();
    }

    @And("I click Mobile phones")
    public void iClickMobilePhones() {
        mobilePhonesPage = phonesAndElectronicsPage.clickMobilePhones();
    }

    @When("I set minimal price as {int} and maximal price as {int}")
    public void iSetMinimalPriceAsAndMaximalPriceAs(int min, int max) {
        this.min = min;
        this.max = max;
        mobilePhonesPage.setMinAndMaxPrice(min, max);
        pricesList = mobilePhonesPage.getPricesList();
    }

    @Then("on the result page all phones' price is inside the range")
    public void onTheResultPageAllPhonesPriceIsInsideTheRange() {
        for (Integer price : pricesList) {
            Assert.assertTrue(min <= price && price <= max, "Mistake!");
        }
    }
}
