import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.Utils;

public class MenuNavigationTest extends BaseTest {

    private WebDriver driver;
    private MenuNavigationPage menuNavigationPage;

    @BeforeClass
    public void setupSpec() {
        super.loadConfig();
    }

    @BeforeMethod
    public void setupMethod() {
        driver = super.setup();
        WebDriverWait wait = new WebDriverWait(driver, 40);
        menuNavigationPage = new MenuNavigationPage(driver, wait, config);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void MenuOptionsTest() {
        menuNavigationPage.navigateToMainMenu();
        menuNavigationPage.checkForTheListOfMenuOptions(Utils
                .getListFromCommaSeperateString(config.getString("menuOptions")));
    }

}
