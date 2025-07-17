# Selenium_Automation
Automated functional testing framework built with Selenium WebDriver in Java, designed to support robust cross-browser testing, full-page screenshot capture, and rich HTML reporting via ExtentReports.

# 🚀 **Features**

Parallel-capable test framework using TestNG and ThreadLocal<WebDriver>

Cross-browser support: Chrome, Firefox, Edge

Full-page screenshots with AShot (for any browser)

Detailed ExtentReports including embedded screenshots on failure and success

Page Object Model for maintainable test code

Base classes & utilities for WebDriver management, reporting, and screenshots


# ⚙️ **Prerequisites**

Java 8+

Maven 3+

ChromeDriver, GeckoDriver, or EdgeDriver binaries in PATH

Internet access to download dependencies

# 🛠 **Install & Run**

Clone the repo
git clone https://github.com/gurunathkumbar/Selenium_Automation.git
cd Selenium_Automation
Run tests

mvn clean test
View reports

ExtentReport HTML: test-output/ExtentReport.html

Screenshots: Saved in /screenshot directory with embedded images in the report

# 📝 **Usage Example (SampleTest.java)**

@Test

public void googleHomePageTest() {

    WebDriver driver = DriverManager.getDriver();
    
    driver.get("https://www.dummywebsite.com");
    
    Assert.assertTrue(driver.getTitle().contains("dummysiteTitle"), "Title validation");
    
}

Failures automatically trigger screenshot capture and report embedding.

# ⚒️ **How It Works**

DriverManager holds a ThreadLocal<WebDriver> instance set up via TestNG's @BeforeMethod

ScreenshotUtil captures full-page screenshots using AShot — compatible with all browsers

ExtentTestListener generates ExtentReports at runtime:

onTestStart: Create new report entry

onTestFailure: Capture screenshot + attach to HTML

onTestSuccess: Optionally embed success screenshots

onFinish: Flush report

# 🚩 **Customize & Extend**

Add browsers: Update BaseTest to support Edge, Firefox, or remote/grid sessions

Add pages: Use Page Object Model to structure your locators & flows

Enhance reports: Add logs, exception screenshots, or video capture support

CI Integration: Integrate with Jenkins/GitHub Actions to publish reports automatically

# 🧭 **License**

This project is open-source under the MIT License. Feel free to customize and integrate into your workflow.
