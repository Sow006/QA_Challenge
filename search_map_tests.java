package com.searchmap.tests;

import com.searchmap.pages.SearchMapPage;
import com.searchmap.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class SearchMapTests extends BaseTest {

    private SearchMapPage page;
    private static final String BASE_URL = "https://gruppenplatz.healthycloud.de/HC_GP_Public_Pages/";

    @BeforeMethod
    public void openPage() {
        page = new SearchMapPage(getDriver());
        page.open(BASE_URL);
    }


    @Test
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
