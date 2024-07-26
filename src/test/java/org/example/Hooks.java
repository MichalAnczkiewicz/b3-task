package org.example;

import com.microsoft.playwright.Page;
import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

public class Hooks {

    private Page page;

    public Hooks() {
    }

    public Hooks(Page page) {
        this.page = page;
    }

    @BeforeStep
    public void beforeStep(Scenario scenario) {
        // Before step logic if needed
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        try {
            byte[] screenshot = page.screenshot();
            scenario.attach(screenshot, "image/png", scenario.getName() + Math.random() * 1000000 );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
