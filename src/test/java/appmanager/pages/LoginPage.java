package appmanager.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.User;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

/**
 * Created by a.oreshnikova on 02.08.2018.
 */

//---------------------------- Страница авторизации ----------------------------
public class LoginPage {

    private SelenideElement tabEnter = $x("//div[@class='SidebarTab__text']/span[.='Войти']");

    private SelenideElement loginField = $("input[name='email']");
    private SelenideElement passwordField = $("input[name='password']");
    private SelenideElement buttonEnter = $x("//button/span[.='Войти']");

    private SelenideElement loginFormError = $(".SidebarLogin__error");

    private SelenideElement buttonLogOut = $x("//button/span[.='Выйти']");

    //-----------------------------------------------------------------------------------------------------------

    @Step("Авторизоваться в приложении")
    public void auth(User user) {
        tabEnter.shouldBe(visible).click();
        loginField.shouldBe(visible).click();
        loginField.sendKeys(user.email());
        passwordField.shouldBe(visible).click();
        passwordField.sendKeys(user.password());
        buttonEnter.shouldBe(visible).click();
    }

    @Step("Выйти из приложения")
    public void logOut() {
        buttonLogOut.shouldBe(visible).click();
    }

    @Step("Получить тект ошибки у поля логин")
    public String getLoginFieldError() {
        return loginField.parent().parent().$(".iqInput__error").shouldBe(visible).getText();
    }

    @Step("Получить тект ошибки у поля пароль")
    public String getPasswordFieldError() {
        return passwordField.parent().parent().$(".iqInput__error").shouldBe(visible).getText();
    }

    @Step("Получить тект ошибки на форме авторизации")
    public String getLoginFormError() {
        return loginFormError.shouldBe(visible).getText();
    }
}
