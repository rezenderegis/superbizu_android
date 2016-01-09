package com.bizu;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.bizu.question.service.ReadData;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
        ReadData rd = new ReadData();
        rd.ReadDataFromDB();
        System.out.println("Executando teste");

    }
}