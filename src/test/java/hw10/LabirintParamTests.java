package hw10;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class LabirintParamTests extends TestBase {

    @BeforeEach
    void beforeEach() {
        Configuration.baseUrl = "https://www.labirint.ru/";
    }

    @ValueSource (strings = {
            "java", "python"
    })
    @ParameterizedTest(name = "Поисковый запрос {0} не должен отдавать пустой список результатов")
    @Tag("SMOKE")
    void searchResultsShouldNotBeEmptyTest(String searchQuery) {
        open("");
        $("#search-field").setValue(searchQuery).pressEnter();
        $$("[class='product-card need-watch watched gtm-watched']")
                .shouldBe(sizeGreaterThan(0));
    }

       @CsvSource(value = {
            "genres/2829/ | Книги на английском языке",
            "genres/3437/ | Книги на испанском языке"
    }, delimiter = '|')
    @ParameterizedTest(name = "Для url {0},должен быть текст{1}")
    @Tag("WEB")
    @Tag("Regression")
    void searchResultsShouldContainExpectedUrlTest(String urlChapter, String expectedText) {
        open("");
        open(urlChapter);
        $(".genre-name").shouldHave(text(expectedText));
    }

}
