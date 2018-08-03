package tests.api;

import com.google.gson.JsonObject;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import models.Data;
import models.Error;
import models.Errors;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import retrofit2.Response;

import java.io.IOException;

/**
 * Created by a.oreshnikova on 02.08.2018.
 */

@Epic("API")
@Feature("Авторизация")
@DisplayName("Провека метода login")
@RunWith(DataProviderRunner.class)
public class LoginApiTest extends ApiTestBase{

    //Тест, написанный при помощи Rest-assured, но у них баг при работе с multiPart
    //RestAssured problem: https://github.com/rest-assured/rest-assured/issues/910
    @Test @Ignore
    public void simpleTest() {
        RestAssured.baseURI = "https://auth.iqoption.com/api/v1.0/login";
//        MultiPartSpecification email = new MultiPartSpecBuilder("test_user@gmail.com").controlName("email")
//                .mimeType("text/xml").build();
//        MultiPartSpecification password = new MultiPartSpecBuilder("123456Hi").controlName("password")
//                .mimeType("text/xml").build();
        RestAssured
                .given().relaxedHTTPSValidation().contentType("multipart/form-data; boundary=----WebKitFormBoundaryqJXEVsjGmZBGxgc3")
                .header(new Header("Accept-Encoding", "gzip, deflate, br"))
                .header(new Header("Referer", "https://eu.iqoption.com/ru"))
                .multiPart("email", "test_user@gmail.com").multiPart("password", "123456Hi")
                .multiPart("google_client_id", "1250965975.1532970811").log().all()
                .post().then().log().all();
    }

    @DisplayName("Проверка успешной авторизации")
    @Test
    public void verifyValidLogin() {
        Response<JsonObject> responseBody = loginService
                .doLogin("test_user@gmail.com", "123456Hi", "1250965975.1532970811");
        Data session = gson.fromJson(responseBody.body().getAsJsonObject("data"), Data.class);
        softly.assertThat(responseBody.code()).isEqualTo(200);
        softly.assertThat(session.getSsid()).isNotEmpty();
        softly.assertAll();
    }

    @DisplayName("Проверка авторизации и ответа при запросе с несуществующим email")
    @Test
    public void verifyLoginWithBadEmail() throws IOException {
        Error expectedError = new Error().code(202).title("Invalid credentials");
        Response<JsonObject> responseBody = loginService
                .doLogin("test@gmail.com", "123456hi", "1250965975.1532970811");
        Errors session = gson.fromJson(responseBody.errorBody().string(), Errors.class);
        softly.assertThat(responseBody.code()).isEqualTo(403);
        softly.assertThat(session.errors()).containsOnly(expectedError);
        softly.assertAll();
    }

    @DataProvider
    public static Object[][] incorrectEmail() {
        return new Object[][] {
                {"12345678"},
                {"test@mail"},
                {"testmail.ru"},
                {""},
        };
    }

    @DisplayName("Проверка авторизации и ответа при запросе с некорректным/пустым email")
    @Test
    @UseDataProvider("incorrectEmail")
    public void verifyLoginWithIncorrectEmail(String email) throws IOException {
        Error expectedError = new Error().code(1).title("Invalid email");
        Response<JsonObject> responseBody = loginService
                .doLogin(email, "123456hi", "1250965975.1532970811");
        Errors session = gson.fromJson(responseBody.errorBody().string(), Errors.class);
        softly.assertThat(responseBody.code()).isEqualTo(400);
        softly.assertThat(session.errors()).containsOnly(expectedError);
        softly.assertAll();
    }

    @DisplayName("Проверка авторизации и ответа при запросе с пустым password")
    @Test
    public void verifyLoginWithEmptyPassword() throws IOException {
        Error expectedError = new Error().code(2).title("Invalid password");
        Response<JsonObject> responseBody = loginService
                .doLogin("test_user@gmail.com", "", "1250965975.1532970811");
        Errors session = gson.fromJson(responseBody.errorBody().string(), Errors.class);
        softly.assertThat(responseBody.code()).isEqualTo(400);
        softly.assertThat(session.errors()).containsOnly(expectedError);
        softly.assertAll();
    }

    @DisplayName("Проверка авторизации и ответа при запросе с некорректным password")
    @Test
    public void verifyLoginWithBadPassword() throws IOException {
        Error expectedError = new Error().code(202).title("Invalid credentials");
        Response<JsonObject> responseBody = loginService
                .doLogin("test_user@gmail.com", "123456", "1250965975.1532970811");
        Errors session = gson.fromJson(responseBody.errorBody().string(), Errors.class);
        softly.assertThat(responseBody.code()).isEqualTo(403);
        softly.assertThat(session.errors()).containsOnly(expectedError);
        softly.assertAll();
    }
}
