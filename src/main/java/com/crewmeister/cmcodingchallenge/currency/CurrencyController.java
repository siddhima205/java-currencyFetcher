package com.crewmeister.cmcodingchallenge.currency;

import com.crewmeister.cmcodingchallenge.entity.CurrencyInfo;
import com.crewmeister.cmcodingchallenge.entity.CurrencyTimeValue;


import com.crewmeister.cmcodingchallenge.service.CurrencyInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/api")
public class CurrencyController {

    Logger logger = LoggerFactory.getLogger(CurrencyController.class);
    private CurrencyInformationService currencyInformationService;


   public CurrencyController(CurrencyInformationService currencyInformationService) {
        this.currencyInformationService = currencyInformationService;
    }
    /*
       API purpose: Demo
        */
    @GetMapping("/currencies")
    public ResponseEntity<ArrayList<CurrencyConversionRates>> getCurrencies() {
        ArrayList<CurrencyConversionRates> currencyConversionRates = new ArrayList<CurrencyConversionRates>();
       currencyConversionRates.add(new CurrencyConversionRates(2.5));

      return new ResponseEntity<ArrayList<CurrencyConversionRates>>(currencyConversionRates, HttpStatus.OK);
    }
    /*
           API purpose: to get a list of all available currencies
            */
    @GetMapping("/currencies/all")
    public ResponseEntity<List<CurrencyInfo>> getAllCurrencyNames(){
        logger.info("Get all CurrencyNames");
        List<CurrencyInfo> res = currencyInformationService.getAllCurrencies();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    /*
           API purpose: to get all EUR-FX exchange rates at all available dates as a collection
            */
    @GetMapping("/currencies/date/all")
    public ResponseEntity<List<CurrencyTimeValue>> getAllCurrencyTimeSeries(){
        logger.info("Get all Currency time series ");
        logger.info("For simplicity, only getting values of AUD and USD for now");
        List<CurrencyTimeValue> res = currencyInformationService.getAllCurrencyTimeSeries();
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    /*
        API purpose: to get the EUR-FX exchange rate at particular day
        Sample input - Date ="2021-05-21"
         */
    @GetMapping("/currencies/date/{date}")
    public ResponseEntity<List<CurrencyTimeValue>> getCurrencyAtTime(@PathVariable String date){
        logger.info("Get all Currency time series on a date");
        logger.info("For simplicity, only getting values of AUD and USD for now");
        List<CurrencyTimeValue> res = currencyInformationService.getCurrencyValuesOnADay(date);
        if( res == null || res.isEmpty())
        {
            //Status Code 404 : CurrencyCode and Date not found
            return  new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /*
    API purpose: to get a foreign exchange amount for a given currency converted to EUR on a particular day
     Sample input - CurrencyCode = "AUD" Date ="2021-05-21"
     */
    @GetMapping("/currencies/{currencycode}/{date}")
    public ResponseEntity<String> getCurrencyConversionValueOnADay(@PathVariable String currencycode, @PathVariable String date){
        logger.info("Get Currency Conversion on a day");
        logger.info("For simplicity, only getting values of AUD and USD for now");
        logger.info("CurrencyCode= "+currencycode+" Date = "+date);
        String res = currencyInformationService.getCurrencyConversionValueOnADay(currencycode,date);
        if(res=="")
        {
            //Status Code 404 : CurrencyCode and Date not found
            return  new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
