package org.saudemo.test;

import org.saucedemo.constants.Constants;
import org.sausedemo.base.Base;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends Base {

    @Test(priority = 1)
    public void loginPageNavigationTest() {
        boolean isLoggedIn = loginPage.doLogin(prop.getProperty("username").trim(), prop.getProperty("password").trim());
        String actLoginPageTitle = loginPage.getLoginPageTitle();
        System.out.println("page act title: " + actLoginPageTitle);
        Assert.assertEquals(actLoginPageTitle, Constants.LOGIN_PAGE_TITLE);
        Assert.assertTrue(isLoggedIn , " Log In to application is failed");
    }
}
