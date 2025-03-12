package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Arrays;

public class ASimplePlaywrightTest {

    private static Playwright playwright;
    private static Browser browser;
    private static BrowserContext browserContext;

    Page page;

    @BeforeAll
    public static void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--no-sandbox","--disable-extensions","--disable-gpu"))
        );
    }

    @BeforeEach
    public void setUp() {
        browserContext = browser.newContext();
        page = browserContext.newPage();

        page.navigate("https://practicesoftwaretesting.com");
    }

    @AfterAll
    public static void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    void shouldShowThePageTitle() {
        Assertions.assertThat(page.title()).contains("Practice Software Testing");
    }

    @Test
    void shouldShowSearchTermsInTheTitle() {
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        Assertions.assertThat(page.locator(".card-title").count()).isGreaterThan(0);
    }

    @DisplayName("Locating elements by text")
    @Test
    void byText() {
        page.getByText("Bolt Cutters").click();

        PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
    }

    @DisplayName("Locating elements by alt text")
    @Test
    void byAltText() {
        page.getByAltText("Combination Pliers").click();

        PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
    }

    @DisplayName("Using title")
    @Test
    void byTitle() {
        page.getByAltText("Combination Pliers").click();

        page.getByTitle("Practice Software Testing - Toolshop").click();
    }
}
