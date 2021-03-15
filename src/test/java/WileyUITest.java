import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Point;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import static com.codeborne.selenide.Condition.readonly;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class WileyUITest extends WileyConfigUI {

    private final static String BASE_URL = "https://www.wiley.com/en-us";
    private static final List<String> whoWeServe = new ArrayList<>(Arrays.asList(
            "Students",
            "Instructors",
            "Book Authors",
            "Professionals",
            "Researchers",
            "Institutions",
            "Librarians",
            "Corporations",
            "Societies",
            "Journal Editors",
            "Bookstores", //В задании нет, но на данный момент есть на сайте
            "Government"
    ));

    @Test(description = "1")
    public void testCheckSubHeadSize() {
        open(Configuration.baseUrl);
        $("ul.navigation-menu-items.initialized > li:nth-child(1)").hover();
        $$("#Level1NavNode1 > ul > li.dropdown-item > a").shouldHave(CollectionCondition.size(12));
        //На данный момент на сайте 12 элементов
    }

    @Test(description = "2", dependsOnMethods = "testCheckSubHeadSize")
    public void testAssertListSubHead() {
        $$("#Level1NavNode1 > ul > li.dropdown-item > a").shouldHave(CollectionCondition.texts(whoWeServe));
    }

    @Test(description = "3",dependsOnMethods = "testAssertListSubHead")
    public void testCoordinatesSearchResults() {
        $("input#js-site-search-input").click();
        $("input#js-site-search-input").setValue("Java");
        $("aside.ui-autocomplete > section.searchresults-section ").shouldBe(visible);

        Point searchField = $("input#js-site-search-input").getCoordinates().inViewPort();
        Point resultBlock = $("aside.ui-autocomplete").getCoordinates().inViewPort();

        int getSearchFieldHeight = Integer.parseInt($("input#js-site-search-input")
                .getCssValue("height").replace("px", ""));

        assertEquals(searchField.getX(), resultBlock.getX());
        assertEquals(searchField.getY(), resultBlock.getY() - getSearchFieldHeight);
    }

    @Test(description = "4", dependsOnMethods = "testCoordinatesSearchResults")
    public void testCheckSearchResultsSize() {
        $("span.input-group-btn > button").click();
        if ($("form.country-location-form").isDisplayed()) {
            $("button.changeLocationConfirmBtn").click();
        }
        $$("h3.product-title > a > span").shouldHave(CollectionCondition.size(10));
    }

    @Test(description = "5", dependsOnMethods = "testCheckSearchResultsSize")
    public void testAssertSearchResultsJAVA() {
        assertTrue($$("h3.product-title > a > span").texts()
                .stream()
                .allMatch(Predicate.isEqual("Java")));
    }

    @Test(description = "6", dependsOnMethods = "testAssertSearchResultsJAVA")
    public void testCheckPrintAndE_BOOKButtons() {

        if ($("form.country-location-form").isDisplayed()) {
            $("button.changeLocationConfirmBtn").click();
        }
        String printAHref = "a[href$='Print']";
        String eBookAHref = "a[href$='E-Book']";
        $$("div#eBundlePlpTabMainTabPanel")
                .forEach(e -> {

                    doesPrintOrE_BookHasAddToCartButton(e, printAHref, eBookAHref);
                    //Будет падать, т.к. есть отсутствующие товары. Оставил проверку, как по заданию
                });

    }

    @Test(description = "7", dependsOnMethods = "testAssertSearchResultsJAVA")
    public void testCheckO_BOOKButtons() {
        open("https://www.wiley.com/en-us/search?pq=Java%7Crelevance");
        //TODO: Сделать
    }

    private void doesPrintOrE_BookHasAddToCartButton(SelenideElement bundleTabPanelSelector,
                                                    String PRINTTabSelector, String E_BOOKTabSelector) {
        List<String> addToCart = new ArrayList<>();
        String addToCartBtn = "button.add-to-cart-button";

        if (bundleTabPanelSelector.find(PRINTTabSelector).exists()) {
            bundleTabPanelSelector.find(PRINTTabSelector).click();
            addToCart.add(bundleTabPanelSelector.find(addToCartBtn).getText());
        }

        if (bundleTabPanelSelector.find(E_BOOKTabSelector).exists()) {
            bundleTabPanelSelector.find(E_BOOKTabSelector).click();
            addToCart.add(bundleTabPanelSelector.find(addToCartBtn).getText());
        }

        assertTrue(addToCart.stream()
                .anyMatch(r -> r.matches("ADD TO CART")), addToCart.toString());
    }

}
