import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.qameta.allure.selenide.LogType;
import org.testng.annotations.BeforeClass;

import java.util.logging.Level;


public class WileyConfigUI {

    @BeforeClass
    public void setUp() {
        Configuration.startMaximized = true;
        Configuration.headless=true;
        Configuration.baseUrl = "https://www.wiley.com/en-us";
        Configuration.savePageSource = false;
        Configuration.screenshots = false;
        Configuration.browser = "chrome";
        SelenideLogger.addListener("AllureReport", new AllureSelenide().includeSelenideSteps(true)
                .savePageSource(false)
                .enableLogs(LogType.PROFILER, Level.FINER)
                .screenshots(true));

    }
}
