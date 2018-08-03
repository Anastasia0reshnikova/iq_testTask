package tests.ui;

import appmanager.ApplicationManager;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * Created by a.oreshnikova on 02.08.2018.
 */

public class TestBase {

    public static ApplicationManager app;

    @BeforeClass
    public static void setUp(){
        app = new ApplicationManager();
    }

    @Before
    public void openStartPage() {
        app.driver().goToUrl();
    }

    @AfterClass
    public static void tearDown() {
        if (app.driver() != null) {
            app.driver().stopDriver();
        }
    }
}
