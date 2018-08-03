package appmanager.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import models.User;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

/**
 * Created by a.oreshnikova on 02.08.2018.
 */

//---------------------------- Страница профиля ----------------------------
public class ProfilePage {

    private SelenideElement userName = $(".SidebarProfile__UserName");
    private SelenideElement userEmail = $(".SidebarProfile__UserEmail");

    private SelenideElement balanceText = $(".SidebarProfileBalance__text");
    private SelenideElement balanceValue = $(".SidebarProfileBalance__value");

    //-----------------------------------------------------------------------------------------------------------

    @Step("Получить имя и почту пользователя")
    public User getProfileInfo() {
        User user = new User();
        user.email(userEmail.shouldBe(visible, enabled).getText());
        user.userName(userName.shouldBe(visible, enabled).getText());
        return user;
    }

    @Step("Получить данные по балансу счета")
    public String getAccount() {
        balanceText.shouldBe(visible).shouldHave(text("Реальный счет"));
        return balanceValue.shouldBe(visible).getText();
    }
}
