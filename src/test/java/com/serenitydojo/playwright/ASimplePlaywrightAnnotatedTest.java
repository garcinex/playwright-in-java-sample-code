package com.serenitydojo.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

@UsePlaywright(HeadlessChromeOptions.class)
public class ASimplePlaywrightAnnotatedTest {

    @Test
    void shouldShowThePageTitle(Page page) {
        Assertions.assertThat(page.title()).contains("Practice Software Testing");
    }

    @Test
    void shouldShowSearchTermsInTheTitle(Page page) {
        page.locator("[placeholder=Search]").fill("Pliers");
        page.locator("button:has-text('Search')").click();

        Assertions.assertThat(page.locator(".card-title").count()).isGreaterThan(0);
    }

    @DisplayName("Locating elements by text")
    @Test
    void byText(Page page) {
        page.getByText("Bolt Cutters").click();

        PlaywrightAssertions.assertThat(page.getByText("MightyCraft Hardware")).isVisible();
    }

    @DisplayName("Locating elements by alt text")
    @Test
    void byAltText(Page page) {
        page.getByAltText("Combination Pliers").click();

        PlaywrightAssertions.assertThat(page.getByText("ForgeFlex Tools")).isVisible();
    }

    @DisplayName("Using title")
    @Test
    void byTitle(Page page) {
        page.getByAltText("Combination Pliers").click();

        page.getByTitle("Practice Software Testing - Toolshop").click();
    }

    @Test
    void getProductNames(Page page) {
        List<String> strings = page.getByTestId("product-name").allTextContents();

        Assertions.assertThat(strings).isNotEmpty();
    }

    @Test
    void filterByProductNames(Page page) {
        List<String> strings = page.getByTestId("product-name")
                .filter(new Locator.FilterOptions().setHasText("Sander"))
                .allTextContents();

        Assertions.assertThat(strings).isNotEmpty();
    }

    @Test
    void findHome(Page page) {
        page.getByRole(AriaRole.MENUBAR, new Page.GetByRoleOptions().setName("Main Menu"))
                .getByRole(AriaRole.MENUITEM, new Locator.GetByRoleOptions().setName("Contact"))
                .click();
    }

    @Test
    void findOutOfStock(Page page) {
        page.locator(".card")
                .filter(new Locator.FilterOptions().setHas(page.getByText("Out of stock")))
                .getByTestId("product-name")
                .allTextContents();

        page.locator(".card")
                .filter(new Locator.FilterOptions().setHasText("Out of stock"))
                .getByTestId("product-name")
                .allTextContents();
    }
}
