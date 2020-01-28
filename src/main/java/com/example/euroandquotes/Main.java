package com.example.euroandquotes;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
    }

    public static String jsonPathToString(String infoFromApi, String jsonPath) {
        if (infoFromApi.equals("failed")) {
            return "no data available";
        } else {
            Object parseData = Configuration.defaultConfiguration().jsonProvider().parse(infoFromApi);
            String jsonPathObjectToString = JsonPath.read(parseData, jsonPath).toString(); // $.rates[0].mid $.rates[0].effectiveDate $.quoteAuthor $.quoteText
            return jsonPathObjectToString;
        }
    }

    public static String downloadJsonFromURL(String urlText) { // json to String
        try {
            URL myUrl = new URL(urlText);
            StringBuilder jsonText = new StringBuilder();
            try (InputStream myInputStream = myUrl.openStream();
                 Scanner scanner = new Scanner(myInputStream)) {
                while (scanner.hasNextLine()) {
                    jsonText.append(scanner.nextLine());
                }
                return jsonText.toString();
            }
        } catch (IOException e) {
            System.err.println("Failed to get content from URL " + urlText + " due to exception: " + e.getMessage());
            return "failed";
        }
    }
}