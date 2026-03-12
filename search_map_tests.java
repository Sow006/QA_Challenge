package com.searchmap.tests;

import com.searchmap.pages.SearchMapPage;
import com.searchmap.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * SearchMapTests
 *
 * Three smoke-suite test cases covering the core behaviours
 * of the Search & Map component:
 *
 *   SF-01  Keyword search returns results and map pins match
 *   SF-03  Applying a filter narrows results and pins stay in sync
 *   RL-02  Clicking an address shows correct data in the detail panel
 */
public class SearchMapTests extends BaseTest {

    private SearchMapPage page;
    private static final String BASE_URL = "https://your-app.com/search";

    @BeforeMethod
    public void openPage() {
        page = new SearchMapPage(getDriver());
        page.open(BASE_URL);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SF-01: Keyword search returns results; list count matches map pin count
    // ─────────────────────────────────────────────────────────────────────────

    @Test(groups = {"smoke", "regression"})
    public void SF01_keywordSearchReturnsResultsAndPinsMatch() {
        page.search("pharmacy");

        int listCount = page.getResultCount();
        int pinCount  = page.getMapPinCount();

        Assert.assertTrue(listCount > 0,
            "SF-01 FAIL: Result list is empty after searching 'pharmacy'.");

        Assert.assertEquals(pinCount, listCount,
            "SF-01 FAIL: Pin count (" + pinCount + ") does not match " +
            "list count (" + listCount + ").");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // SF-03: Applying a filter narrows the results; pins update to match
    // ─────────────────────────────────────────────────────────────────────────

    @Test(groups = {"smoke", "regression"})
    public void SF03_filterNarrowsResultsAndPinsStayInSync() {
        page.search("clinic");
        int beforeFilter = page.getResultCount();

        page.selectCategory("General Practice");
        int afterFilter = page.getResultCount();
        int pinCount    = page.getMapPinCount();

        Assert.assertTrue(afterFilter <= beforeFilter,
            "SF-03 FAIL: Result count did not decrease after applying filter. " +
            "Before: " + beforeFilter + "  After: " + afterFilter);

        Assert.assertEquals(pinCount, afterFilter,
            "SF-03 FAIL: Pin count (" + pinCount + ") does not match " +
            "filtered result count (" + afterFilter + ").");
    }

    // ─────────────────────────────────────────────────────────────────────────
    // RL-02: Clicking an address shows the correct data in the detail panel
    // ─────────────────────────────────────────────────────────────────────────

    @Test(groups = {"smoke", "regression"})
    public void RL02_clickAddressShowsCorrectDetailData() {
        page.search("pharmacy");

        String expectedTitle = page.getResultTitleAt(0);
        page.clickResultAt(0);
        String detailTitle = page.getDetailTitle();

        Assert.assertTrue(page.isDetailVisible(),
            "RL-02 FAIL: Detail panel did not open after clicking an address.");

        Assert.assertEquals(detailTitle, expectedTitle,
            "RL-02 FAIL: Detail shows '" + detailTitle +
            "' but selected address was '" + expectedTitle + "'.");

        Assert.assertTrue(page.isSelectedPinVisible(),
            "RL-02 FAIL: No pin entered selected state after clicking an address.");
    }
}
