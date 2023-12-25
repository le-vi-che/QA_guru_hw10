package hw10;

import com.codeborne.selenide.Configuration;
import data.Lang;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class ZenitParamTests extends TestBase {

    @BeforeEach
    void beforeEach() {
        Configuration.baseUrl = "https://il.fc-zenit.ru/";
    }


    @Tag("REGRESSION")
    @Tag("WEB")
    @EnumSource(Lang.class)
    @ParameterizedTest(name = "Проверка изменения языка названия вкладки")
    void mainPageHasCorrectChapterDiscription(Lang lang) {
        open("");
        $(".navbar-top").find(byText(lang.name())).click();
        $(".header-logos").shouldHave(text(lang.description));
    }

    static Stream<Arguments> selenideSiteShouldDisplayCorrectButtons() {
        return Stream.of(
                Arguments.of(
                        Lang.DE,
                        List.of("HEIM", "SPIELE", "MANNSCHAFTEN ", "VEREIN", "FAN GUIDE", "Twitter", "Zenit TV", "KARTEN", "ONLINE SHOP")
                        ),
                Arguments.of(
                        Lang.CN,
                        List.of("主页", "比赛", "球队", "俱乐部", "球迷指南", "Twitter", "Zenit TV", "青训营", "球票", "商店")
                         )
        );
    }

    @MethodSource
    @ParameterizedTest
    void selenideSiteShouldDisplayCorrectButtons(Lang lang, List<String> expectedButtons) {
        open("");
        $(".navbar-top").find(byText(lang.name())).click();
        $$("#topNavigation li").filter(visible)
                .shouldHave(texts(expectedButtons));
    }
}