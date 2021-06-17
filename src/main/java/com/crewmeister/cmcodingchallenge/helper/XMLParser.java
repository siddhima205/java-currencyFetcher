package com.crewmeister.cmcodingchallenge.helper;

import com.crewmeister.cmcodingchallenge.entity.CurrencyInfo;
import com.crewmeister.cmcodingchallenge.entity.CurrencyTimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import static com.crewmeister.cmcodingchallenge.helper.Constants.*;

public class XMLParser {

    private Logger logger = LoggerFactory.getLogger(XMLParser.class);



    public  Document PrepareXMLDocument(String result)
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(result));
            Document doc = dBuilder.parse(is);
            return  doc;
        }
        catch (Exception ex)
        {
            logger.error("Error in creating document type");
            return null;
        }

    }

    public List<CurrencyInfo> getDetailsOfCurrency(String result)
    {
        try
        {
            Document doc = PrepareXMLDocument(result);
            List<CurrencyInfo> currencyInfoList= new ArrayList<>();
            NodeList nList = doc.getElementsByTagName(CURRENCY_NODE_NAME);
            logger.info("Parsing currency names into currencyInfo ");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String currencyID = eElement.getAttribute(CURRENCY_CODE_ID);
                    String currencyEn = eElement.getElementsByTagName(CURRENCY_NAME_TAG).item(0).getTextContent();
                    String currencyDE = eElement.getElementsByTagName(CURRENCY_NAME_TAG).item(1).getTextContent();
                    currencyInfoList.add(new CurrencyInfo(currencyID,currencyEn,currencyDE));
                }
            }

            return currencyInfoList;
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            return  null;
        }
    }


    public List<CurrencyTimeValue> GetTimeSeriesInformation(String result, String currencyCode){
        try {
            Document doc = PrepareXMLDocument(result);
            List<CurrencyTimeValue> currencyTimeValues =new ArrayList<>();
            logger.info("Parsing time series information into currencyTimeValues ");
            NodeList nList = doc.getElementsByTagName(TIME_SERIES_NODE);
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Element date =(Element)(eElement.getElementsByTagName(TIME_DATE_ELEMENT).item(0));
                    String dateValue = date.getAttribute(VALUE_ATTRIBUTE);
                    Element value =(Element)(eElement.getElementsByTagName(TIME_CURRENCY_ELEMENT).item(0));
                    String currencyValue="";
                    if(value!=null)
                    {
                        currencyValue  = value.getAttribute(VALUE_ATTRIBUTE);
                    }
                    currencyTimeValues.add(new CurrencyTimeValue(currencyCode,dateValue,currencyValue));

                }

            }

            return  currencyTimeValues;
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            return  null;

        }
    }

}
