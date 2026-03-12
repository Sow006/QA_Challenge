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

    private final By searchInput    = By.cssSelector("[data-testid='search-input']");
    private final By searchButton   = By.cssSelector("[data-testid='search-btn']");
    private final By filterCategory = By.cssSelector("[data-testid='filter-category']");
    private final By resultList     = By.cssSelector("[data-testid='result-list']");
    private final By resultItems    = By.cssSelector("[data-testid='result-item']");
    private final By emptyState     = By.cssSelector("[data-testid='empty-state']");
    private final By mapPins        = By.cssSelector("[data-testid='map-pin']");
    private final By selectedPin    = By.cssSelector("[data-testid='map-pin-selected']");
    private final By detailPanel    = By.cssSelector("[data-testid='detail-panel']");
    private final By detailTitle    = By.cssSelector("[data-testid='detail-title']");

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
