package com.searchmap.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchMapPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By searchInput    = By.cssSelector("#b4-b2-b1-Input_SearchText");
    private final By searchButton   = By.cssSelector("#b4-b1-b1-Column2 > div > button > div");
    private final By filterCategory = By.cssSelector("#b4-b1-b8-OptionsContainer > div > div.\"list.list-group\".OSFillParent > div:nth-child(2) > span");
    private final By resultList     = By.cssSelector("#b4-b1-b18-SidebarContainer > div");
    private final By resultItems    = By.cssSelector("#b4-b1-b18-l1-420_191-b2-Column2");
    private final By emptyState     = By.cssSelector("#b4-b1-b18-b4-b1-Content");
    private final By mapPins        = By.cssSelector("#b4-b1-b18-b6-MapContainer");
    private final By selectedPin    = By.cssSelector("#b4-b1-b18-b6-MapContainer > div.leaflet-pane.leaflet-map-pane > div.leaflet-pane.leaflet-marker-pane > img:nth-child(3)");
    private final By detailPanel    = By.cssSelector("#b4-b1-b18-l1-420_209-AddressContainer > div.margin-top-base > a > span");
    private final By detailTitle    = By.cssSelector("#b2-Content > a.ThemeGrid_MarginGutter.text-decoration-underline > span");

    public SearchMapPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput));
    }

    public void search(String keyword) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
        input.clear();
        input.sendKeys(keyword);
        driver.findElement(searchButton).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(resultList));
    }

    public void selectCategory(String value) {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(filterCategory)))
            .selectByVisibleText(value);
        wait.until(ExpectedConditions.presenceOfElementLocated(resultList));
    }

    public void clickResultAt(int index) {
        List<WebElement> items = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(resultItems));
        items.get(index).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(detailPanel));
    }

    public int    getResultCount()        { return driver.findElements(resultItems).size(); }
    public int    getMapPinCount()        { return driver.findElements(mapPins).size(); }
    public String getResultTitleAt(int i) { return driver.findElements(resultItems).get(i).getAttribute("data-title"); }
    public String getDetailTitle()        { return wait.until(ExpectedConditions.visibilityOfElementLocated(detailTitle)).getText(); }
    public boolean isDetailVisible()      { return !driver.findElements(detailPanel).isEmpty(); }
    public boolean isEmptyStateVisible()  { return !driver.findElements(emptyState).isEmpty(); }
    public boolean isSelectedPinVisible() { return !driver.findElements(selectedPin).isEmpty(); }
}
