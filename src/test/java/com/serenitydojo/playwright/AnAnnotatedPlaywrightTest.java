package com.serenitydojo.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.junit.Options;
import com.microsoft.playwright.junit.OptionsFactory;
import com.microsoft.playwright.junit.UsePlaywright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@UsePlaywright(AnAnnotatedPlaywrightTest.MyOptions.class)
public class AnAnnotatedPlaywrightTest {

    public static class MyOptions implements OptionsFactory {

        @Override
        public Options getOptions() {
            return new Options().setHeadless(false);
        }
    }

    @Test
    void showThePageTitle(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");

        assertThat(page.title()).contains("Practice");
    }

    @Test
    void searchByKeyword(Page page) {
        page.navigate("https://practicesoftwaretesting.com/");

        page.locator("[placeholder='Search']").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        assertThat(page.locator(".card").count()).isGreaterThan(0);
    }
}
