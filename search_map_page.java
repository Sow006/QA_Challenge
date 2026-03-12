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

    // Search & Filter
    private final By searchInput     = By.cssSelector("#b4-b2-b1-Input_SearchText");
    private final By searchButton    = By.cssSelector("#b4-b1-b1-Column2 > div > button > div");
    private final By filterCategory  = By.cssSelector("#b4-b1-b15-OptionsContainer > div > div.\"list.list-group\".OSFillParent > div:nth-child(2) > span");
    private final By clearFiltersBtn = By.cssSelector("#b4-b1-b3-l1-415_0-\$b2 > div > span");

    // Result List
    private final By resultItems     = By.cssSelector("[data-testid='result-item']");
    private final By emptyState      = By.cssSelector("[data-testid='empty-state']");
    private final By resultList      = By.cssSelector("[data-testid='result-list']");

    // Map
    private final By mapPins         = By.cssSelector("[data-testid='map-pin']");
    private final By selectedPin     = By.cssSelector("[data-testid='map-pin-selected']");
    private final By zoomInBtn       = By.cssSelector("[data-testid='map-zoom-in']");
    private final By zoomOutBtn      = By.cssSelector("[data-testid='map-zoom-out']");

    // Detail Panel
    private final By detailPanel     = By.cssSelector("[data-testid='detail-panel']");
    private final By detailTitle     = By.cssSelector("[data-testid='detail-title']");
    private final By detailClose     = By.cssSelector("[data-testid='detail-close']");

    // Error
    private final By errorMessage    = By.cssSelector("[data-testid='error-message']");

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

    public void clearFilters() {
        driver.findElement(clearFiltersBtn).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(resultList));
    }

    public void zoomIn(int times) {
        for (int i = 0; i < times; i++) {
            driver.findElement(zoomInBtn).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(resultList));
        }
    }

    public void zoomOut(int times) {
        for (int i = 0; i < times; i++) {
            driver.findElement(zoomOutBtn).click();
            wait.until(ExpectedConditions.presenceOfElementLocated(resultList));
        }
    }

    public void clickResultAt(int index) {
        List<WebElement> items = wait.until(
            ExpectedConditions.visibilityOfAllElementsLocatedBy(resultItems));
        items.get(index).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(detailPanel));
    }

    public void closeDetail() {
        driver.findElement(detailClose).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(detailPanel));
    }

    public String getSearchKeyword()         { return driver.findElement(searchInput).getAttribute("value"); }
    public String getSelectedCategory()      { return new Select(driver.findElement(filterCategory)).getFirstSelectedOption().getText(); }
    public int    getResultCount()           { return driver.findElements(resultItems).size(); }
    public int    getMapPinCount()           { return driver.findElements(mapPins).size(); }
    public String getResultTitleAt(int i)    { return driver.findElements(resultItems).get(i).getAttribute("data-title"); }
    public String getDetailTitle()           { return wait.until(ExpectedConditions.visibilityOfElementLocated(detailTitle)).getText(); }
    public boolean isDetailVisible()         { return !driver.findElements(detailPanel).isEmpty(); }
    public boolean isEmptyStateVisible()     { return !driver.findElements(emptyState).isEmpty(); }
    public boolean isErrorVisible()          { return !driver.findElements(errorMessage).isEmpty(); }
    public boolean isSelectedPinVisible()    { return !driver.findElements(selectedPin).isEmpty(); }
}
