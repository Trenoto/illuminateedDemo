package com.faberald.seleniumTest;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AutoTest {
    private WebDriver driver;
    private String mainPage;

    public AutoTest(String url) {
        driver = new ChromeDriver();
        driver.get(url);
        this.mainPage = driver.getCurrentUrl();
    }

    public void loginTest(String username, String password) throws InterruptedException{
        System.out.println("===========================================");
        System.out.println("Login Test");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("button_next")).click();
        Thread.sleep(1000);

        try{
            System.out.println(driver.switchTo().alert().toString());
        }catch (NoAlertPresentException ex){
            System.out.println("No pop-up Alter Window Present");
        }

        System.out.println("===========================================\n\n");
    }

    public void backToLastPage(){
        driver.navigate().back();
    }

    public void openHyperLink(String hyperText){
        System.out.println("******************************");
        System.out.println("HyperLink text : " + hyperText);
        try {
            driver.findElement(new By.ByLinkText(hyperText)).click();
        }catch (org.openqa.selenium.NoSuchElementException ex){
            System.out.println(ex + "Can't locate " + hyperText);
        }

        System.out.println("Redirect to " + driver.getCurrentUrl());
        System.out.println("******************************\n");

    }

    public void testhyperLink(String[] hyperTexts) throws InterruptedException{
        System.out.println("===========================================");
        System.out.println("hyper Link Test");
        for (String hyperText: hyperTexts){
            openHyperLink(hyperText);
            Thread.sleep(1000);
            System.out.println("Back to prev page");
            backToLastPage();
            try {
                Assert.assertTrue(driver.getCurrentUrl().equals(mainPage));
            }catch(AssertionError ex){
                System.out.print("Minor Exception Occurs: Can't back to prev page in 1 click\n");
                driver.get(mainPage);
                Thread.sleep(1000);
            }

        }

        System.out.println("===========================================\n\n");

    }

    public void testSocialBtn() throws InterruptedException{
        System.out.println("===========================================");
        System.out.println("Button Test");
        String mainHandler = driver.getWindowHandle();

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

        driver.switchTo().window(mainHandler);

        System.out.println("===========================================\n\n");
    }
}
