import org.openqa.selenium.By;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class YandexAuthorization {
    @Test
    public void testYandex() {
    open("https://yandex.ru/");
        $x("//span[text()='Войти в почту']").isEnabled();
    }
}
