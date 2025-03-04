package com.serenitydojo.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ASimplePlaywrightTest {

    @Test
    void shouldShowThePageTitle() {
        // TODO: Write your first playwright test here
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch();
        Page page = browser.newPage();

        page.navigate("https://practicesoftwaretesting.com/");

        Assertions.assertThat(page.title()).contains("Practice");

        browser.close();
        playwright.close();
    }
}
