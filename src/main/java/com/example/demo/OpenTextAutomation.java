package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenTextAutomation implements CommandLineRunner {

    @Autowired
    private ReadBrowsingHistory readBrowsingHistory;

    @Autowired
    private RunOpenText runOpenText;

    private String openTextURL;

    public static void main(String[] args) {
        SpringApplication.run(OpenTextAutomation.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        readBrowsingHistory.createBrowsingHistory();
        Thread.sleep(2000);
        openTextURL = readBrowsingHistory.getLink("youtube");
        Thread.sleep(2000);
        runOpenText.openOpenTextWindow(openTextURL);
        Thread.sleep(2000);
    }
}