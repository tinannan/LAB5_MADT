package com.example.lab5_madt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class Parser {
    private static final String TAG = "Parser";

    public static List<String> parseXML(InputStream xmlData) {
        List<String> currencyData = new ArrayList<>();

        try {
            // Initialize the DocumentBuilderFactory and DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // parse XML data
            Document document = builder.parse(xmlData);
            document.getDocumentElement().normalize();


            NodeList rateList = document.getElementsByTagName("item");


            for (int i = 0; i < rateList.getLength(); i++) {
                Element rateElement = (Element) rateList.item(i);

                // get currency code and rate
                String code = rateElement.getElementsByTagName("targetCurrency").item(0).getTextContent();
                String rate = rateElement.getElementsByTagName("exchangeRate").item(0).getTextContent();

                // format the data
                currencyData.add(code + " - " + rate);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error parsing XML", e);
        }

        return currencyData;
    }
}
