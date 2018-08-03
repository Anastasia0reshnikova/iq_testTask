package tests.api;

import client.LoginService;
import com.google.gson.Gson;
import org.assertj.core.api.SoftAssertions;
import org.junit.Before;

/**
 * Created by a.oreshnikova on 02.08.2018.
 */
public class ApiTestBase {

    public LoginService loginService = new LoginService();
    public Gson gson = new Gson();
    public SoftAssertions softly;

    @Before
    public void cleanAssertions() {
        softly = new SoftAssertions();
    }

}
