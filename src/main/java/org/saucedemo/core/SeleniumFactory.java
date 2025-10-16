package org.saucedemo.core;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
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
        String remoteUrl = System.getProperty("selenium.remote.url", "").trim();
        String browserName = prop.getProperty("browser").trim();
        System.out.println("Browser name is : " + browserName);

        try {
            // --- Remote execution case (e.g., GitHub Actions) ---
            if (!remoteUrl.isEmpty()) {
                System.out.println("🔗 Running tests on remote Selenium Grid: " + remoteUrl);
                switch (browserName.toLowerCase()) {
                    case "firefox":
                        FirefoxOptions firefoxOptions = new FirefoxOptions();
                        driver.set(new RemoteWebDriver(new URL(remoteUrl), firefoxOptions));
                        break;

                    case "edge":
                        EdgeOptions edgeOptions = new EdgeOptions();
                        driver.set(new RemoteWebDriver(new URL(remoteUrl), edgeOptions));
                        break;

                    case "chrome":
                    default:
                        ChromeOptions chromeOptions = new ChromeOptions();
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--no-sandbox");
                        chromeOptions.addArguments("--disable-dev-shm-usage");
                        driver.set(new RemoteWebDriver(new URL(remoteUrl), chromeOptions));
                        break;
                }
            }
            // --- Local execution case ---

             else {
                System.out.println("💻 Running tests locally...");

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
            }
        }
        catch (Exception e) {
            System.err.println("Error initializing WebDriver: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriver", e);
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



            // --- Ensure screenshot folder exists ---
            File screenshotDir = new File(System.getProperty("user.dir") + "/screenshot");
            // --- Delete existing folder if it exists ---
            if (screenshotDir.exists()) {
                deleteFolderRecursively(screenshotDir);
            }

            // --- Create fresh folder ---
            screenshotDir.mkdirs();

            // --- Save screenshot to file ---
            String fileName = System.currentTimeMillis() + ".png";
            File outputFile = new File(screenshotDir, fileName);
            ImageIO.write(screenshot.getImage(), "PNG", outputFile);
            System.out.println("Screenshot saved: " + outputFile.getAbsolutePath());

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


    /**
     * Utility method to delete a folder recursively
     */
    private static void deleteFolderRecursively(File folder) {
        File[] allContents = folder.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteFolderRecursively(file);
            }
        }
        folder.delete();
    }

}
