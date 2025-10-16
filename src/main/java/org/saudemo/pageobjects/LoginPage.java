package org.saudemo.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;





public class LoginPage {

    private WebDriver driver;

    @FindBy(xpath="//input[@id='user-name']")
    private WebElement userNameTxt;
    @FindBy(xpath="//input[@id='password']")
    private WebElement pwdTxt ;
    @FindBy(xpath="//input[@id='login-button']")
    private WebElement loginBtn ;
    @FindBy(xpath="//div[@class='bm-burger-button']/button")
    private WebElement openMenuBtn ;
    @FindBy(xpath="//a[@id='logout_sidebar_link']")
    private WebElement logoutLink ;



    public LoginPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }
    public String getLoginPageTitle() {
        return driver.getTitle();
    }

    public boolean doLogin(String appUserName, String appPassword) {
        System.out.println("App creds: " + appUserName + ":" + appPassword);
                userNameTxt.sendKeys(appUserName);
                pwdTxt.sendKeys(appPassword);
                loginBtn.click();
                openMenuBtn.click();
                if(openMenuBtn.isDisplayed()){
                    System.out.println("user is logged in successfully....");
                    return true;
                }else {
                    System.out.println("user is not logged in successfully....");
                    return false;
                }

    }




}
