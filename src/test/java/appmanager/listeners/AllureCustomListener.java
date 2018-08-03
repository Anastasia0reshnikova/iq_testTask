package appmanager.listeners;


import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import io.qameta.allure.junit4.AllureJunit4;
import org.junit.runner.notification.Failure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;

/**
 * Created by a.oreshnikova on 02.08.2018.
 */

public class AllureCustomListener extends AllureJunit4 {

    @Override
    public void testFailure(Failure failure) throws Exception {
        super.testFailure(failure);
        if (!failure.getDescription().isSuite()) { // check is needed to avoid double attaching
            if (WebDriverRunner.hasWebDriverStarted()) {
                try {
                    attachScr();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Attachment(value = "[FAILED]", type = "image/png")
    private  byte[] attachScr() throws IOException {
        WebDriver driver = WebDriverRunner.getWebDriver();
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
