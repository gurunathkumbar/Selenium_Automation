package org.sausedemo.base;

import org.openqa.selenium.WebDriver;
import org.saucedemo.core.SeleniumFactory;
import org.saudemo.pageobjects.LoginPage;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class Base {

    SeleniumFactory sf;
    protected Properties prop;

    protected LoginPage loginPage;

    WebDriver driver;

    @Parameters({ "browser" })
    @BeforeTest
    public void setup(String browserName) {
        sf = new SeleniumFactory();

        prop = sf.init_prop();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }

        driver = sf.initBrowser(prop);
        driver.navigate().to(prop.getProperty("url"));
        driver.manage().window().maximize();
        loginPage = new LoginPage(driver);
    }

    @AfterTest
    public void tearDown() {
        driver.close();
    }
}
