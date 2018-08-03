package appmanager;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;

import static appmanager.ApplicationManager.getProperties;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by a.oreshnikova on 02.08.2018.
 */

public class DriverConfiguration {

    private ApplicationManager app;

    DriverConfiguration(ApplicationManager app) {
        this.app = app;
        setDriver();
        setDriverProperties();
    }

    public WebDriver getDriver() {
        return WebDriverRunner.getWebDriver();
    }

    public void stopDriver() {
        WebDriverRunner.closeWebDriver();
    }

    private String getBrowser() {
        return getProperties("web.browser");
    }

    private void setDriver() {
        switch (getBrowser()) {
            case "chrome":
                Configuration.browser = WebDriverRunner.CHROME;
                break;
            default:
                throw new ExceptionInInitializerError("Браузер " + getBrowser() + "в текущей конфигурации отсутствует");
        }
    }

    private void setDriverProperties() {
        Configuration.fastSetValue = true;
        //Configuration.reopenBrowserOnFail = false;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
        Configuration.startMaximized = true;
        Configuration.clickViaJs = true;
    }

    public void goToUrl() {
        open(getProperties("web.url"));
    }
}
