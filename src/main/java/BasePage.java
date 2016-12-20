
import com.typesafe.config.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final Config config;

    public BasePage(WebDriver driver, WebDriverWait wait, Config config) {
        this.driver = driver;
        this.wait = wait;
        this.config = config;
    }

    public WebElement findElementByXpath(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public WebElement findElementById(String id) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(id)));
    }

    public WebElement findElementByClass(String className) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(className)));
    }

    public WebElement findElementByName(String name) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(name)));
    }

    public List<WebElement> findElementsByXpath(String xpath) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(xpath)));
    }

    public List<WebElement> findElementsByClass(String className) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(className)));
    }

    public void hover(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element);
        actions.build().perform();
    }

    public String switchWindow() {
        String windowBefore = driver.getWindowHandle();
        driver.getWindowHandles().forEach(win -> driver.switchTo().window(win));
        return windowBefore;
    }

    public void selectFromDropDown(String dropDownLocator, String valueToSelect) {
        Select dropDown = new Select(findElementByXpath(dropDownLocator));
        dropDown.selectByValue(valueToSelect);
    }

    public String convertDateToStringFormat(LocalDate date) {
        return date.getMonth().toString().substring(0,3) + " " + date.getDayOfMonth() + ", " + date.getYear();
    }

    public void selectProvidedOptionFromElementsList(String elementsListLocator, Predicate<WebElement> filterCondition) {
        findElementsByXpath(elementsListLocator)
                .stream()
                .filter(filterCondition)
                .findFirst().get().click();
    }

    public WebElement findElementByXpathWithRetry(String xpath, int noOfRetry) throws InterruptedException {
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        }catch (StaleElementReferenceException e) {
            if(noOfRetry > 0) {
                Thread.sleep(2000);
                element = findElementByXpathWithRetry(xpath, noOfRetry-1);
            }
        }
        return element;
    }

    public WebElement presenceOfElementByXpathWithRetry(String xpath, int noOfRetry) throws InterruptedException {
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
        }catch (StaleElementReferenceException e) {
            if(noOfRetry > 0) {
                Thread.sleep(2000);
                element = presenceOfElementByXpathWithRetry(xpath, noOfRetry-1);
            }
        }
        return element;
    }

    public WebElement findElementWithRetry(By by, int noOfRetry) throws InterruptedException {
        int count = 0;
        WebElement element = null;
        try {
            element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        }catch (StaleElementReferenceException e) {
            if(count < noOfRetry) {
                Thread.sleep(2000);
                element = findElementWithRetry(by, noOfRetry-1);
            }
        }
        return element;
    }

    //manual click through javascript
    //use when the element you want to click is not reliable
    public void clickItem(WebElement element) throws Exception {
        try {
            element.click();
        } catch (StaleElementReferenceException e){
            By by = By.id(element.getAttribute("id"));
            element = findElementWithRetry(by, 10);
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("arguments[0].click();", element);
        } catch (Exception e){
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("arguments[0].click();", element);
        }
    }
}