package org.saucedemo.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import java.io.*;
import java.util.Base64;
import java.util.Properties;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


public class SeleniumFactory {

    Properties prop;


    private static  ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }


    public WebDriver initBrowser(Properties prop){
    String browserName = prop.getProperty("browser").trim();
    System.out.println("Browser name is : " + browserName);

    switch (browserName.toLowerCase()) {
        case "firefox":
            driver.set(WebDriverManager.firefoxdriver().create());
            break;
        case "safari":
            driver.set(WebDriverManager.safaridriver().create());
            break;
        case "chrome":
            driver.set(WebDriverManager.chromedriver().create());
            break;
        case "edge":
            driver.set(WebDriverManager.edgedriver().create());
            break;
        case "chromium":
            driver.set(WebDriverManager.chromiumdriver().create());
            break;
        default:
            System.out.println("please pass the right browser name...... Currently, it's running in headless mode by default");
            //TODO: change to headless mode
            driver.set(WebDriverManager.chromedriver().create());
            break;
    }

       return getDriver();


}

    public Properties init_prop() {

        try {
            FileInputStream ip = new FileInputStream("./src/test/resources/config/config.properties");
            prop = new Properties();
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;

    }

    public static String takeScreenshot(WebDriver driver) throws IOException {
        // Create screenshot with viewport stitching (for full-page capture)
        try {
            Screenshot screenshot = new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(500))  // increased delay
                    .takeScreenshot(driver);

            // Save screenshot
            String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
            File outputFile = new File(path);
            ImageIO.write(screenshot.getImage(), "PNG", outputFile);

            // Convert to Base64
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "png", baos);
            byte[] imageBytes = baos.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
