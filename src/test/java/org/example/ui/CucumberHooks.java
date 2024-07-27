package org.example.ui;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class CucumberHooks {
    private final TestContext testContext;

    public CucumberHooks(TestContext context) {
        this.testContext = context;
    }

    @Before
    public void setup() {
        testContext.setUp();
    }

    @After
    public void teardown() {
        testContext.tearDown();
    }
}