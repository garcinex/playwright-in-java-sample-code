package com.serenitydojo.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class ASimplePlaywrightTest {

    private Page page;
    private Browser browser;
    private Playwright playwright;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright
                .chromium()
                .launch(new BrowserType.LaunchOptions()
                        .setHeadless(false)
                        .setArgs(Arrays.asList("--start-maximized", "--no-sandbox", "--disable-gpu")));
        page = browser.newPage();

        page.navigate("https://practicesoftwaretesting.com/");
    }

    @AfterEach
    void tearDown() {
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
