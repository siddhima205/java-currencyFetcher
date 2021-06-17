package com.crewmeister.cmcodingchallenge.models;

import java.sql.Time;

public class TimeSeriesKey {
    private String frequency;
    private String currencyCodes;
    private String currencyDenominator;
    private String contentOfTimeSeries;
    private String rateType;
    private String suffix;

    public TimeSeriesKey(String currencyCodes)
    {
        this.frequency = "D";//Daily
        this.currencyCodes= currencyCodes;//3 letter code
        this.currencyDenominator ="EUR"; // EURO for all apis now
        this.contentOfTimeSeries="BB";//ECB euro reference exchange rates
        this.rateType = "AC";//Middle
        this.suffix="000";//Standard

    }
    @Override
    public String toString() {
        return frequency+"."+currencyCodes+"."+currencyDenominator+"."+contentOfTimeSeries+"."+rateType+"."+suffix;
        }


}
