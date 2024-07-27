package org.example.ui;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

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

    @AfterStep
    public void afterStep(Scenario scenario) {

        try {
            byte[] screenshot = testContext.getPage().screenshot();
            scenario.attach(screenshot, "image/png", scenario.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}