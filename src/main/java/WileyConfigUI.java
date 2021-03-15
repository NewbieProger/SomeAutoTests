import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeClass;


public class WileyConfigUI {

    @BeforeClass
    public void setUp() {
        Configuration.headless = false;
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://www.wiley.com/en-us";
        Configuration.savePageSource = false;
        Configuration.screenshots = false;
        Configuration.browser = "chrome";
        SelenideLogger.addListener("AllureReport", new AllureSelenide().includeSelenideSteps(true)
                .savePageSource(false)
                .screenshots(true));
        WebDriverRunner.addListener(new HighLighterListener());

    }
}
