package com.example.demo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Component
public class ReadURL {

    private static final Logger LOG = LoggerFactory.getLogger(ReadURL.class);

    public String getLink(String wantedURLPrefix, String fileHistory) throws IOException {

        boolean foundLink = false;

        Path pathToFileHistory = Paths.get(fileHistory);
        if(!Files.exists(pathToFileHistory)){
            LOG.info("File browsing history is not available");
        }

        //Get file history
        File openTextURL = new File(String.valueOf(pathToFileHistory));

        //Parse html file to Jsoup parser
        Document doc = Jsoup.parse(openTextURL, "utf-8");

        //Read all the node "a" from html file
        Elements links = doc.select("a");

        //Extract the href attribute of each node "a"
        for (Element link : links){
            String linkHref = link.attr("href");
            //Extract the wanted link
            if (linkHref.contains(wantedURLPrefix)){
                LOG.info("Found link : " + linkHref);
                foundLink = true;
                return linkHref;
            }
        }

        if (!foundLink){
            LOG.info("There's no link contains " + wantedURLPrefix);
        }
        return null;
    }

    public void createBrowsingHistory(String scriptLocation) throws IOException {
        Path pathToFileBrowsingHistoryView = Paths.get(scriptLocation);
        if(!Files.exists(pathToFileBrowsingHistoryView)){
            LOG.info("Folder BrowsingHistoryView is not available");
        }

        String input = pathToFileBrowsingHistoryView + "\\BrowsingHistoryView.exe";
        String output = pathToFileBrowsingHistoryView + "\\report.html";

        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("cmd.exe", "/c",
                input + " /VisitTimeFilterType 5 /VisitTimeFilterValue 5 /shtml " + output).start();
    }
}
