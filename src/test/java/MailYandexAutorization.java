import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.Assert.assertEquals;

public class MailYandexAutorization {

    private static final String URL_HOME = "https://yandex.ru/";
    private static final String URL_AUTORIZATION = "https://passport.yandex.ru/auth";
    private static final String PHONE_LINK = "https://passport.yandex.ru/auth/phone";
    private static final String E_MAIL = "***";
    private static final String PASS = "***";
    private static final boolean expectedTrue = true;
    private static final boolean expectedFalse = false;

    @Test(description = "Открытие страницы Яндекс и переход на страницу авторизации в почту")
    public void testOpenYandexAutorization() {
        open(URL_HOME);
        $x("//span[text()='Войти в почту']").parent().click();
        switchTo().window("Авторизация");

        assertEquals(expectedTrue, url().startsWith(URL_AUTORIZATION));
    }

    @Test(description = "Ввод логина, переход на следующий шаг", dependsOnMethods = "testOpenYandexAutorization")
    public void testSetLoginClickEnter() {
        $("#passp-field-login").click();
        $("#passp-field-login").setValue(E_MAIL);
        $(".passp-sign-in-button").click();
    }

    @Test(description = "Проверка наличия логина в поле 'E-mail'", dependsOnMethods = "testSetLoginClickEnter")
    public void testAssertLoginIsDisplayed() {
        $(".CurrentAccount-displayName").shouldHave(Condition.text(E_MAIL));
    }

    @Test(description = "Ввод пароля. Переход на следующую форму", dependsOnMethods = "testAssertLoginIsDisplayed")
    public void testSetPasswordClickEnter() {
        $("#passp-field-passwd").click();
        $("#passp-field-passwd").setValue(PASS);
        $(".passp-sign-in-button").click();
    }

    @Test(description = "Отказ от привязки номера телефона", dependsOnMethods = "testSetPasswordClickEnter")
    public void testDeclinePhoneLinkSuggestion() {
        if (url().startsWith(PHONE_LINK)) {
            $(".passp-title").lastChild().shouldHave(Condition.text("Привяжите номер телефона, чтобы дополнительно защитить свой аккаунт."));
            $(".passp-button[data-t='phone_skip']").click();
        }
    }

    @Test(description = "Открытие страницы почтового ящика. Валидируется наличие папки 'Входящие'", dependsOnMethods = "testDeclinePhoneLinkSuggestion")
    public void testCheckEmailOpened() {
        assertEquals(expectedTrue, $(".mail-FolderList > a[href='#inbox']").isDisplayed());
    }
}