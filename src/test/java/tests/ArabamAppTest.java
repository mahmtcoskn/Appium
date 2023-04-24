package tests;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ArabamAppTest {

    // Arabam kac para bolumune tiklayalim
    // Aracimin fiyatini merak ediyorum bolumune tiklayalim
    // Wolkswagen markasini secelim
    // yil secimi yapalim
    // model secimi yapalim
    // govde tipini secelim
    // yakit tipini secelim
    // vites tipini secelim
    // Versiyon secimi yapalim
    // aracin km bilgilerini girelim
    // aracin rengini secelim
    // opsiyel donanim (varsa) secelim
    // degisen bilgisi ekleyerek tramer kaydi belirtelim
    // aracimizin fiyatinin 500.000 tl den fazla oldugunu test edelim
    // uygulamayi kapatalim

    AndroidDriver<AndroidElement> driver; //android cihazlarin driveri
    final String cihazAdi="Mahmut";
    final String platformIsmi="Android";
    final String version="10.0";
    final String automation="UiAutomator2";

    @BeforeTest
    public void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,cihazAdi);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME,platformIsmi);
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION,version);
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME,automation);
        capabilities.setCapability("appPackage","com.dogan.arabam");  // hangi uygulama uzerinde calismak istedigimizi apk infodan
        capabilities.setCapability("appActivity","com.dogan.arabam.presentation.feature.home.HomeActivity");  // uygulamada hangi sayfayi gormek veya baslatmak istedgimiz
        // genelde Main.Activity ya da Homepage.activity olarak apk info icersinde activities kisminda gorulur
        capabilities.setCapability(MobileCapabilityType.NO_RESET,false);
        // eger false kullanirsak uygulama calistiktan sonra yapilacak adimlari gerceklestirir uygulamayi islem bittikten sonra SIFIRLAR
        // eger true olursa uygulama calistiktan sonra yapilacak adimlari gerceklestirir uygulamayi islem bittikten sonra SIFIRLAMAZ
        driver=new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }
    @Test
    public void arabamTest() throws InterruptedException {

        // İlan ver bolumune tiklayalim
        // driver.findElement(By.xpath("//*[@text='İlan ver']")).click();

        // Arabam kac para bolumune tiklayalim
        driver.findElement(By.xpath("//*[@text='Arabam kaç para?']")).click();

        // Aracimin fiyatini merak ediyorum bolumune tiklayalim
        AndroidElement fiyatMerak = driver.findElement(By.xpath("//*[@text='Aracımın fiyatını merak ediyorum']"));
        fiyatMerak.click();

        // Volkswagen markasini secelim
        Thread.sleep(1000);
        TouchAction action = new TouchAction<>(driver);
        action.press(PointOption.point(547,2056))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(503,433)).release().perform();
        Thread.sleep(1000);
        driver.findElementByXPath("//*[@text='Volkswagen']").click();

        /*
         action.press(PointOption.point(537,381)).
                 waitAction(WaitOptions.waitOptions(Dration.ofMillis(500))).
                 moveTo(PointOption.point(543,1732)).release().perform();

           Eger ki bizler daha onceden kaydirma islemi gerceklestirmissek tam tersi haraketini gerceklestirmek icin yazdigimiz
           koordinat degerlerini tam tersi olacak sekilde yazmak o islemin zittini gerceklestirir.
      */

        // yil secimi yapalim
        driver.findElementByXPath("//*[@text='2011']").click();

        // model secimi yapalim
        driver.findElementByXPath("//*[@text='Passat']").click();

        // govde tipini secelim
        driver.findElementByXPath("//*[@text='Sedan']").click();

        // yakit tipini secelim
        driver.findElementByXPath("//*[@text='Benzin']").click();

        // vites tipini secelim
        driver.findElementByXPath("//*[@text='Yarı Otomatik']").click();

        // Versiyon secimi yapalim
        Thread.sleep(1000);
        action.press(PointOption.point(499,1900)).release().perform();

        // aracin km bilgilerini girelim
        if (driver.isKeyboardShown()){
            driver.getKeyboard().pressKey("190000");
        }
        else {
            driver.findElementById("com.dogan.arabam:id/et_km").sendKeys("150000");
        }
        driver.findElementById("com.dogan.arabam:id/btn_price_prediction_submit").click();

        // aracin rengini secelim
        driver.findElementByXPath("//*[@text='Gri (metalik)']").click();

        // opsiyonel donanim (varsa) secelim
        driver.findElementById("com.dogan.arabam:id/btnNext").click();

        // degisen bilgisi ekleyerek tramer kaydi belirtelim
        AndroidElement kaput=driver.findElementById("com.dogan.arabam:id/iv_B01001");
        kaput.click();
        Thread.sleep(1000);
        driver.findElementByXPath("(//*[@text='Boyalı'])[2]").click();
        Thread.sleep(1000);
        driver.findElementById("com.dogan.arabam:id/btn_next").click();

        // tramer kaydi yok kismina tiklayalim
        driver.findElementById("com.dogan.arabam:id/rbHasNoTramerEntry").click();
        driver.findElementById("com.dogan.arabam:id/btnNext").click();

        // aracimizin fiyatinin 500.000 tl den fazla oldugunu test edelim
        String avaragePrice= driver.findElementById("com.dogan.arabam:id/tvAveragePrice").getText();

        //588.500 TL
        String lastPrice=avaragePrice.replaceAll("\\D","");
        Assert.assertTrue(Integer.parseInt(lastPrice)>500000);

        // uygulamayi kapatalim
        driver.closeApp();

    }
}