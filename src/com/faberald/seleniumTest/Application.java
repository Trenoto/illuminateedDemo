package com.faberald.seleniumTest;

import java.io.FileOutputStream;
import java.io.PrintStream;

public class Application {
    public static void main(String[] args)  throws Exception{
        AutoTest test1 = new AutoTest("https://demo.illuminateed.com");
        String[] hypertexts = new String[]{"Forgot your password?","www.illuminateed.com", "release notes", "Back to School Illuminations Newsletter",
                                        "Webinars", "8/17", "8/31", "8/17,8/24,8/31,9/7,9/14,9/21,9/28", "8/24",
                                        "9/7,9/20/10/2,10/20", "More", "8/15-8/17", "9/13", "9/19-9/20",
                                        "9/19", "10/23-10/24",  "more", "11/14", "11/6-11/7", "2/1-2/2"};

        PrintStream out = new PrintStream(new FileOutputStream("./output.txt"));
        System.setOut(out);
        System.out.println("------------------------Test Begin------------------------");
        test1.staticWebTest();
        test1.loginTest("testuser","testPassword");
        test1.testGoogleLogin();
        test1.testSocialBtn();
        test1.testhyperLink(hypertexts);
        test1.endTest();
        System.out.println("------------------------ Test End ------------------------");
    }
}
