package com.searchmap.utils;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void setUp() { DriverManager.createDriver(); }

    @AfterMethod
    public void tearDown() { DriverManager.quitDriver(); }

    protected WebDriver getDriver() { return DriverManager.getDriver(); }
}
