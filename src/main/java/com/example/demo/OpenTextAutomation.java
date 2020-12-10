package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpenTextAutomation implements CommandLineRunner {

    @Autowired
    private ReadURL readURL;

    @Autowired
    private RunOpenText runOpenText;

    @Value("${file.report.path}")
    private String fileHistoryLocation;

    @Value("${file.script.path}")
    private String fileBrowsingHistoryViewLocation;

    private String openTextURL;

    public static void main(String[] args) {
        SpringApplication.run(OpenTextAutomation.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        readURL.createBrowsingHistory(fileBrowsingHistoryViewLocation);
        Thread.sleep(2000);
        openTextURL = readURL.getLink("youtube", fileHistoryLocation);
        Thread.sleep(2000);
        runOpenText.openOpenTextWindow(openTextURL);
        Thread.sleep(2000);
    }
}