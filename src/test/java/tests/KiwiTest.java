package tests;

import Pages.KiwiPage;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.Driver;

import java.time.Duration;

public class KiwiTest {

    // uygulamanin yuklendigi dogrulanir
    // uygulamanin basariyla acildigi dogrulanir
    // misafir olarak devam et e tiklanir
    // ardinda gelecek olan 3 adimada yesil butona basilarak devam edilir
    // Trip type, one way olarak secilir
    // kalkis ulkesi secenegine tiklanir ve default olan ulke kaldirilir
    // kalkis yapilacak ulke/sehir girilir ve sec e tiklanir
    // varis ulkesi secenegine tiklanir ve gidilecek ulke girilir
    // gidis tarihi mayis ayinin 21 i olarak secilir ve set date e tiklanir
    // search butonuna tiklanir
    // en ucuz ve aktarmasiz filtrelemeleri yapilir
    // gelen bilet fiyati kaydedilir ve kullanicin telefonuna sms olarak gonderilir

    AndroidDriver<AndroidElement> driver = Driver.getAndroidDriver();

    KiwiPage page=new KiwiPage();

    @Test
    public void kiwiTest() throws InterruptedException {

        // uygulamanin yuklendigi dogrulanir
        Assert.assertTrue(driver.isAppInstalled("com.skypicker.main"),"uygulama yuklenemedi");

        // uygulamanin basariyla acildigi dogrulanir
        String yaziTextActual= page.asAGuest.getText();
        String yaziTextExpected="Continue as a guest";
        Assert.assertEquals(yaziTextActual,yaziTextExpected,"uygulama basarili bir sekilde baslatilamadi");

        // misafir olarak devam et e tiklanir
        page.asAGuest.click();

        // ardinda gelecek olan 3 adimada yesil butona basilarak devam edilir
        page.ucButtonTiklama(0,3,538,2029,500);

        // Trip type,one way olarak secilir
        page.returnButton.click();
        page.oneWay.click();

        // kalkis ulkesi secenegine tiklanir ve default olan ulke kaldirilir
        page.kalkisButonu.click();
        page.defaultUlkeSILME.click();

        // kalkis yapilacak ulke/sehir girilir ve sec e tiklanir
        driver.getKeyboard().pressKey("izmir");
        page.izmir.click();
        page.choose.click();

        // varis ulkesi secenegine tiklanir ve gidilecek ulke girilir
        page.anyWhere.click();
        driver.getKeyboard().pressKey("berlin");
        page.berlin.click();
        page.choose.click();
        page.anyTimeButtonClick();

        // gidis tarihi secilir ve set date e tiklanir
        Thread.sleep(2000);
        TouchAction action=new TouchAction<>(driver);
        action.press(PointOption.point(530,1647)).
                waitAction(WaitOptions.waitOptions(Duration.ofMillis(500))).
                moveTo(PointOption.point(557,848)).release().perform();
        Thread.sleep(1500);
        action.press(PointOption.point(123,1258)).release().perform();
        page.setDateButton.click();

        // search butonuna tiklanir
        page.searchButton.click();

        // en  ucuz ve aktarmasiz filtrelemeleri yapilir
        page.bestPrice.click();
        page.cheapest.click();
        page.stops.click();
        page.nonStop.click();

        // gelen bilet fiyati kaydedilir ve kullanicin telefonuna sms olarak gonderilir
        String price= page.ticketPrice.getText();
        driver.sendSMS("5555555555",price);

    }
}