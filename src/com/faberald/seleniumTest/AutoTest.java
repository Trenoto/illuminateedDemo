package com.faberald.seleniumTest;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.NoSuchElementException;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AutoTest {
    private WebDriver driver;
    private String mainPage;
    private String mainHandler;

    public AutoTest(String url) {
        driver = new ChromeDriver();
        driver.get(url);
        this.mainPage = driver.getCurrentUrl();
        this.mainHandler = driver.getWindowHandle();
    }

    /**
     * Test static web contents, to see if there is title/header/footer
     */
    public void staticWebTest(){
        System.out.println("===========================================");
        System.out.println("Static Web-Content test\n");
        try {
            System.out.println("Title: " + driver.getTitle());
        }catch(Exception e){
            System.out.println("No Title");
        }

        try{
            System.out.println("Header: "+ driver.findElement(By.tagName("h1")).getText().toString() );
        }catch(NoSuchElementException e){
            System.out.println("No h1 header");
        }

        try{
            System.out.println("Footer: "+ driver.findElement(By.id("footer")).findElement(By.id("footer-copyright")).getText().toString());
        }catch(NoSuchElementException e){
            System.out.println("No footer");
        }

        System.out.println("===========================================\n\n");
    }

    /**
     * *
     * Simple login test, test input text box's function
     * It will redirect to a new page when login success
     * it should display an error message when login fail
     * @param  username a string
     * @param  password a string
     * *
     */
    public void loginTest(String username, String password) throws InterruptedException{
        System.out.println("===========================================");
        System.out.println("Login Test\n");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("button_next")).click();
        Thread.sleep(1000);
        if (driver.getCurrentUrl().equals(mainPage))
            System.out.println("Login Failed " + driver.findElement(By.xpath("//*[@id='invalid_login']")).getText().toString());
        else
            System.out.println("Login Succeed");

        try{
            System.out.println(driver.switchTo().alert().toString());
        }catch (NoAlertPresentException ex){
            System.out.println("*No pop-up Alter Window Present*");
        }

        System.out.println("===========================================\n\n");
    }

    /**
     * *
     * Test back function
     * *
     */
    public void backToLastPage(){
        System.out.println("Back to prev page");
        driver.navigate().back();
    }

    /**
     * *
     * Test a hyperlink
     * It will display the exception and error message when element is not found
     * @param  hyperText(String) a link text that appears in HTML file
     * @return      void no return
     * *
     */
    public void openHyperLink(String hyperText) throws InterruptedException{
        System.out.println("******************************");
        System.out.println("HyperLink text : " + hyperText);
        try {
            driver.findElement(new By.ByLinkText(hyperText)).click();
            System.out.println("Redirect to " + driver.getCurrentUrl());
        }catch (NoSuchElementException ex){
            System.out.println(ex + "Can't locate " + hyperText);
        }
        Thread.sleep(1000);
        backToLastPage();
        System.out.println("******************************\n");

    }

    /**
     * *
     * Test all hyperlink
     * It will show a error message when browser can't back to prev page in 1 click
     * @param  hyperTexts(String[]) a array contains a set of hyper texts
     * *
     */
    public void testhyperLink(String[] hyperTexts) throws Exception{
        System.out.println("===========================================");
        System.out.println("hyper Link Test\n");
        for (String hyperText: hyperTexts){
            openHyperLink(hyperText);
            Thread.sleep(1000);
            try {
                Assert.assertTrue(driver.getCurrentUrl().equals(mainPage));
            }catch(AssertionError ex){
                System.out.print("Minor Exception Occurs: Can't back to prev page in 1 click\n");
                File pic = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(pic,new File("./screenshot.png"));
                driver.get(mainPage);
                Thread.sleep(1000);
            }

        }

        System.out.println("===========================================\n\n");

    }

    /**
     * *
     * Test all Buttons
     * *
     */
    public void testSocialBtn() throws InterruptedException{
        System.out.println("===========================================");
        System.out.println("Button Test\n");

        List<WebElement> btns = driver.findElement(By.className("social_media")).findElements(By.tagName("a"));

        for (WebElement btn: btns){
            btn.click();
            Thread.sleep(100);
        }
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> iterator = handles.iterator();

        iterator.next();
        while(iterator.hasNext()){
            driver.switchTo().window(iterator.next());
            System.out.println("Redirect to : " + driver.getTitle() + " URL is : " + driver.getCurrentUrl());
            Thread.sleep(100);
        }
        System.out.println("===========================================\n\n");
        driver.switchTo().window(mainHandler);

    }

    /**
     * *
     * Test google Login Page
     * *
     */
    public void testGoogleLogin() throws InterruptedException{
        System.out.println("===========================================");
        System.out.println("Google Login Test\n");
        driver.findElement(By.xpath("//*[@id='ied-login-logo']")).click();
        Thread.sleep(1000);
        Set<String> handles = driver.getWindowHandles();
        Iterator<String> iterator = handles.iterator();

        iterator.next();
        while(iterator.hasNext()){
            driver.switchTo().window(iterator.next());
            System.out.println("Redirect to : " + driver.getTitle() + " URL is : " + driver.getCurrentUrl());
            Thread.sleep(500);
        }

        System.out.println("===========================================\n\n");
        driver.switchTo().window(mainHandler);
    }

    /**
     * *
     * End Test and close the browser window
     * *
     */
    public void endTest(){
        driver.close();
    }

}
