package com.crewmeister.cmcodingchallenge.service;

import com.crewmeister.cmcodingchallenge.entity.CurrencyInfo;
import com.crewmeister.cmcodingchallenge.entity.CurrencyTimeValue;

import java.util.List;

public interface CurrencyInformationService {
    public List<CurrencyInfo> getAllCurrencies();
    public List<CurrencyTimeValue> getAllCurrencyTimeSeries();
    public List<CurrencyTimeValue> getCurrencyValuesOnADay(String date);
    public String getCurrencyConversionValueOnADay(String currencyCode,String date);
}
