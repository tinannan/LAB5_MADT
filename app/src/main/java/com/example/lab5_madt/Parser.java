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
    // Parse XML data and extract currency info
    public static List<String> parseXML(InputStream xmlData) {
        List<String> currencyData = new ArrayList<>();

        try {
            // Initialize the DocumentBuilderFactory and DocumentBuilder
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML data
            Document document = builder.parse(xmlData);
            document.getDocumentElement().normalize();

            // Extract "item" elements, which contain currency info
            NodeList rateList = document.getElementsByTagName("item");

            // Loop through each "item" node
            for (int i = 0; i < rateList.getLength(); i++) {
                Element rateElement = (Element) rateList.item(i);

                // Get currency code and rate
                String code = rateElement.getElementsByTagName("targetCurrency").item(0).getTextContent();
                String rate = rateElement.getElementsByTagName("exchangeRate").item(0).getTextContent();

                // Format the data as "USD - 1.235"
                currencyData.add(code + " - " + rate);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error parsing XML", e);
        }

        return currencyData;
    }
}
