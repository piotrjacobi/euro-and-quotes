package com.example.euroandquotes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import static com.example.euroandquotes.Main.downloadJsonFromURL;
import static com.example.euroandquotes.Main.jsonPathToString;

@Controller
public class EuroAndQuotesController {

    @GetMapping("/")
    public String viewHomePage(ModelMap map) {
        String infoFromApiCurrency = downloadJsonFromURL("http://api.nbp.pl/api/exchangerates/rates/a/eur?format=json");
        String infoFromApiQuotes = downloadJsonFromURL("https://quote-garden.herokuapp.com/quotes/random?format=json");

        String code = jsonPathToString (infoFromApiCurrency, "$.code");
        System.out.println(code);

        String date = jsonPathToString (infoFromApiCurrency, "$.rates[0].effectiveDate");
        date = "Date: "+date;
        System.out.println(date);

        String value = jsonPathToString (infoFromApiCurrency, "$.rates[0].mid");
        value="Exchange rate: "+value;
        System.out.println(value);

        String quoteText = jsonPathToString (infoFromApiQuotes, "$.quoteText");
        if (quoteText.equals("no data available")) {
            quoteText=quoteText;
        } else {
            quoteText = "\"" + quoteText + "\"";
            System.out.println(quoteText);
        }

        String quoteAuthor = jsonPathToString (infoFromApiQuotes, "$.quoteAuthor");
        if (quoteAuthor.equals("")) {
            quoteAuthor="Author: unknow";
        } else {
            quoteAuthor = "Author: " + quoteAuthor;
        }
        System.out.println(quoteAuthor);

        map.put("codeModel", code );
        map.put("dateModel", date );
        map.put("valueModel", value );
        map.put("quoteTextModel", quoteText );
        map.put("quoteAuthorModel", quoteAuthor );

        return "index"; }

}
