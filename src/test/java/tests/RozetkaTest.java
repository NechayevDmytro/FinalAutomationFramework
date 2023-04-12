package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class RozetkaTest extends BaseTest {

    @Test(description = "Check that phones filtering by screen size works correct.", groups = {"screensize"})
    public void checkScreenSize() {
        mobilePhonesPage = mainPage
                .clickPhonesAndElectronics()
                .clickMobilePhones()
                .clickCheckbox();
        Assert.assertEquals(mobilePhonesPage.getHeadingText(), "Мобільні телефони 5.55 - 6 Дюймів", "Mistake!");

        String screenSize = mobilePhonesPage
                .clickFirstPhone()
                .clickCharacteristics()
                .getScreenSize();
        double actualScreenSize = Double.parseDouble(screenSize);
        Assert.assertTrue(5.55 <= actualScreenSize && actualScreenSize <= 6, "Mistake!");
    }

    @Test(description = "Check that phones filtering by price works correct.", groups = {"prices"})
    public void checkPrices() {
        int minPrice = Integer.parseInt(getPropertyValue("min_price"));
        int maxPrice = Integer.parseInt(getPropertyValue("max_price"));
        List<Integer> list =  mainPage
                .clickPhonesAndElectronics()
                .clickMobilePhones()
                .setMinAndMaxPrice(minPrice, maxPrice)
                .getPricesList();
        for (Integer price : list) {
            Assert.assertTrue(minPrice <= price && price <= maxPrice, "Mistake!");
        }
    }
}
