package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class ReadBrowsingHistory {

    private static final Logger LOG = LoggerFactory.getLogger(ReadBrowsingHistory.class);

    @Value("${file.history.report.path}")
    private String fileHistoryReportLocation;

    @Value("${file.history.program.path}")
    private String fileHistoryProgramLocation;


    public String getLink(String wantedURLPrefix) throws IOException {

        boolean foundURL = false;
        String returnURL = null;

        // Check if the file exists
        Path pathToFileHistory = Paths.get(fileHistoryReportLocation);
        if(!Files.exists(pathToFileHistory)){
            LOG.error("File browsing history is not available");
            return null;
        }

        // Get file browsing history
        File browsingHistoryFile = new File(String.valueOf(pathToFileHistory));

        // Parse html file to Jsoup parser
        Document doc = Jsoup.parse(browsingHistoryFile, "utf-8");

        //Read all the node "a" from html file
        Elements URLs = doc.select("a");

        //Extract the href attribute of each node "a"
        for (Element URL : URLs){
            String linkHref = URL.attr("href");
            //Extract the LATEST link having the wanted prefix
            if (linkHref.contains(wantedURLPrefix)){
                LOG.info("Found URL : " + linkHref);
                foundURL = true;
                returnURL = linkHref;
            }
        }

        if (!foundURL){
            LOG.error("There's no URL contains " + wantedURLPrefix);
            return null;
        } else {
            LOG.info("Open URL: " + returnURL);
            return returnURL;
        }
    }

    public void createBrowsingHistory() throws IOException {
        // Check if the program BrowsingHistoryView exists
        Path pathToFileHistoryProgram = Paths.get(fileHistoryProgramLocation);
        if(!Files.exists(pathToFileHistoryProgram)){
            LOG.error("Program BrowsingHistoryView is not available");
        }

        // Check if the file browsing history exists
        Path pathToFileHistoryReport = Paths.get(fileHistoryReportLocation);
        if(!Files.exists(pathToFileHistoryReport)){
            LOG.error("File browsing history is not available");
        }

        ProcessBuilder processBuilder = new ProcessBuilder();

        // Create a file recording the last 5 minutes browsing history
        processBuilder.command("cmd.exe", "/c",
                        pathToFileHistoryProgram +
                        " /VisitTimeFilterType 5 /VisitTimeFilterValue 5 /shtml " +
                        pathToFileHistoryReport).start();
    }
}
