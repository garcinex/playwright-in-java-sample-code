package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.*;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class ASimplePlaywrightTest {

    private static Page page;
    private static Browser browser;
    private static Playwright playwright;
    private static BrowserContext browserContext;

    @BeforeAll
    public static void setUpBrowser() {
        playwright = Playwright.create();
        browser = playwright
                .chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--start-maximized", "--no-sandbox", "--disable-gpu")));
        browserContext = browser.newContext();
    }

    @BeforeEach
    void setUp() {
        page = browser.newPage();
        page.navigate("https://practicesoftwaretesting.com/");
    }

    @AfterAll
    public static void tearDown() {
        browser.close();
        playwright.close();
    }

    @Test
    void showThePageTitle() {
        assertThat(page.title()).contains("Practice");
    }

    @Test
    void searchByKeyword() {
        page.locator("[placeholder='Search']").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        assertThat(page.locator(".card").count()).isGreaterThan(0);
    }
}
