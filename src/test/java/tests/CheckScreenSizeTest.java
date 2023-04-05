package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CharacteristicsPage;
import pages.MobilePhonesPage;
import pages.PhoneProductPage;
import pages.PhonesAndElectronicsPage;

public class CheckScreenSizeTest extends BaseTest {

    @Test
    public void checkScreenSize() {
        PhonesAndElectronicsPage phonesAndElectronicsPage = mainPage.clickPhonesAndElectronics();
        MobilePhonesPage mobilePhonesPage = phonesAndElectronicsPage.clickMobilePhones();
        mobilePhonesPage.clickCheckbox();
        Assert.assertEquals(mobilePhonesPage.getHeadingText(), "Мобільні телефони 5.55 - 6 Дюймів", "Mistake!");

        PhoneProductPage phoneProductPage = mobilePhonesPage.clickFirstPhone();
        CharacteristicsPage characteristicsPage = phoneProductPage.clickCharacteristics();
        double actualScreenSize = Double.parseDouble(characteristicsPage.getScreenSize());
        Assert.assertTrue(5.55 <= actualScreenSize && actualScreenSize <= 6, "Mistake!");
    }
}
