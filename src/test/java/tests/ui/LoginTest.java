package tests.ui;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import models.User;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by a.oreshnikova on 02.08.2018.
 */

@Feature("Авторизация")
@DisplayName("Проверка авторизации пользователя в приложении")
@RunWith(DataProviderRunner.class)
public class LoginTest extends TestBase {

    @DisplayName("Проверка успешной авторизации в приложении")
    @Test
    public void loginTest() {
        User user = new User().email("test_user@gmail.com").password("123456Hi").userName("Test Test");
        app.loginPage().auth(user);
        assertThat(app.profilePage().getProfileInfo())
                .as("Ошибка! Авторизация не выполнена.").isEqualToComparingOnlyGivenFields(user);
        assertThat(app.profilePage().getAccount())
                .as("Ошибка! Не верный баланс счета после авторизации.").isEqualTo("€0.00");
        app.loginPage().logOut();
    }

    @DataProvider
    public static Object[][] incorrectEmail() {
        return new Object[][] {
                {new User().email("12345678").password("123456Hi"), "Неверный e-mail"},
                {new User().email("test@mail.r").password("123456Hi"), "Неверный e-mail"},
                {new User().email("testmail.ru").password("123456Hi"), "Неверный e-mail"},
                {new User().email("").password("123456Hi"), "Поле не заполнено"},
        };
    }

    @DisplayName("Проверка авторизации и ошибок при некорректном/пустом поле логин")
    @Test
    @UseDataProvider("incorrectEmail")
    public void loginWithIncorrectEmailTest(User user, String errorText) {
        app.loginPage().auth(user);
        assertThat(app.loginPage().getLoginFieldError())
                .as("Не верный текст ошибки у поля логин").isEqualToIgnoringCase(errorText);
    }

    @DataProvider
    public static Object[][] emptyPassword() {
        return new Object[][] {
                {new User().email("test_user@gmail.com").password(""), "Поле не заполнено"},
        };
    }

    @DisplayName("Проверка авторизации и ошибок при пустом поле пароль")
    @Test
    @UseDataProvider("emptyPassword")
    public void loginWithIncorrectPasswordTest(User user, String errorText) {
        app.loginPage().auth(user);
        assertThat(app.loginPage().getPasswordFieldError())
                .as("Не верный текст ошибки у поля пароль").isEqualToIgnoringCase(errorText);
    }

    @DataProvider
    public static Object[][] incorrectData() {
        return new Object[][] {
                {new User().email("test_user@gmail.com").password("123456"), "Неправильный логин или пароль"},
                {new User().email("test@gmail.com").password("123456Hi"), "Неправильный логин или пароль"},
        };
    }

    @DisplayName("Проверка авторизации и ошибок при неверном логине/пароле")
    @Test
    @UseDataProvider("incorrectData")
    public void loginWithIncorrectData(User user, String errorText) {
        app.loginPage().auth(user);
        assertThat(app.loginPage().getLoginFormError())
                .as("Не верный текст ошибки на форме авторизации").isEqualToIgnoringCase(errorText);
    }
}
