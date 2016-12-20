import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static junit.framework.TestCase.assertTrue;

public class ProfilePage extends BasePage {

    public ProfilePage(WebDriver driver, WebDriverWait wait, Config config) {
        super(driver, wait, config);
    }

    public static final String PROFILE_TAB_TEXT = "profile";
    public static final String LOGIN_BUTTON_ID = "button_sign_in";
    public static final String SINGUP_BUTTON_ID = "button_create_account";
    public static final String SIGNIN_HEADER_ID = "text_signin";

    public void navigateToProfilePage() {
        findElementByName(PROFILE_TAB_TEXT).click();
        assertTrue(findElementById(LOGIN_BUTTON_ID).isDisplayed());
    }

    public void navigateToLoginPage() {
        findElementById(LOGIN_BUTTON_ID).click();
        assertTrue(findElementById(SIGNIN_HEADER_ID).isDisplayed());
    }

    public void login(String email, String pwd) {

    }
}
