import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

public class GoogleSearchTest {

    private static final String URL_HOME = "https://www.google.com";
    private static final String SEARCH_VALUE = "купить кофемашину bork c804";
    private static final String SEARCH_LINK = "www.mvideo.ru";

    @Test(description = "Открытие google.com. Запрос")
    public void testOpenUrl() {
        open(URL_HOME);
        $(By.name("q")).setValue(SEARCH_VALUE).pressEnter();
    }

    @Test(description = "Количество результатов поиска", dependsOnMethods = "testOpenUrl")
    public void testSearchResultCount() {
        $$("div#rso > .g").shouldHave(CollectionCondition.sizeGreaterThan(10));
    }

    @Test(description = "Поиск элемента в массиве запросов, содержащих конкретный домен", dependsOnMethods = "testSearchResultCount")
    public void testFindNecessaryLink() {
        boolean actual =  $$x("//cite[text()]").stream()
                .anyMatch(e -> e.is(Condition.matchText(SEARCH_LINK)));

        assertTrue(actual);
    }
}
