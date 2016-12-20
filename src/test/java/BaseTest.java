import com.google.common.base.Throwables;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected Config config;

    protected void loadConfig() {
        String configToLoad = System.getProperty("configName");
        if(configToLoad==null || configToLoad.isEmpty()) configToLoad = "prod";
        config = ConfigFactory.load(configToLoad + ".properties");
    }

    protected WebDriver setup() {
        WebDriver driver = null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        try {
            capabilities.setCapability("deviceName", "LGK371c1616900");
            capabilities.setCapability("browserName", "Android");
            capabilities.setCapability("platformVersion", "6.0");
            capabilities.setCapability("appPackage", "com.petsmart.consumermobile");
            capabilities.setCapability("appActivity", "com.pk.ui.activity.MainActivity");
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        }catch(MalformedURLException e) {
            Throwables.propagate(e);
        }
        return driver;
    }
}