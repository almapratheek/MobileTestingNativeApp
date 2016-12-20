import com.typesafe.config.Config;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class MenuNavigationPage extends BasePage {

    public MenuNavigationPage(WebDriver driver, WebDriverWait wait, Config config) {
        super(driver, wait, config);
    }

    public static final String MAIN_MENU_ICON_ID = "toolbar_navigation";
    public static final String WELCOME_MESSAGE_ID = "label_welcome";
    public static final String MENU_OPTIONS_CLASS = "android.widget.CheckedTextView";

    public void navigateToMainMenu() {
        findElementById(MAIN_MENU_ICON_ID).click();
        assertTrue(findElementById(WELCOME_MESSAGE_ID).isDisplayed());
    }

    public void checkForTheListOfMenuOptions(List<String> listOfMenuOptions) {
        List<String> menuOptions = findElementsByClass(MENU_OPTIONS_CLASS)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
        assertTrue(menuOptions.equals(listOfMenuOptions));
    }
}
