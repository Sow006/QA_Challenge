package com.searchmap.tests;

import com.searchmap.pages.SearchMapPage;
import com.searchmap.utils.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ZoomFilterContractTests extends BaseTest {

    private SearchMapPage page;
    private static final String BASE_URL = "https://gruppenplatz.healthycloud.de/HC_GP_Public_Pages/";

    @BeforeMethod
    public void openPage() {
        page = new SearchMapPage(getDriver());
        page.open(BASE_URL);
        // Put the component into a state with active search and filter
        page.search("clinic");
        page.selectCategory("General Practice");
    }


    @Test
    public void SF06_filterPersistsAfterZoomIn() {
        String filterBefore = page.getSelectedCategory();

        page.zoomIn(2);

        String filterAfter = page.getSelectedCategory();

        Assert.assertEquals(filterAfter, filterBefore,
            "SF-06 FAIL: Category filter changed after zoom in. " +
            "Before: '" + filterBefore + "'  After: '" + filterAfter + "'");
    }

    @Test
    public void SF07_keywordPersistsThroughZoom() {
        String keywordBefore = page.getSearchKeyword();

        page.zoomIn(2);
        page.zoomOut(2);

        String keywordAfter = page.getSearchKeyword();

        Assert.assertEquals(keywordAfter, keywordBefore,
            "SF-07 FAIL: Keyword changed after zoom. " +
            "Before: '" + keywordBefore + "'  After: '" + keywordAfter + "'");
    }

    @Test
    public void MZ04_listCountMatchesPinCountAfterZoom() {
        page.zoomIn(2);

        int listCount = page.getResultCount();
        int pinCount  = page.getMapPinCount();

        Assert.assertEquals(pinCount, listCount,
            "MZ-04 FAIL: List and map are out of sync after zoom. " +
            "List: " + listCount + "  Pins: " + pinCount);
    }
}
