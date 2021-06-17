package com.crewmeister.cmcodingchallenge.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CurrencyTimeValue {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String currencyCode;
    private String date;
    private String conversionValue;

    public CurrencyTimeValue() {}
    public CurrencyTimeValue(String currencyCode, String date, String conversionValue)
    {
        this.currencyCode=currencyCode;
        this.date=date;
        this.conversionValue=conversionValue;
    }

    public String getCurrencyCode(){
        return currencyCode;
    }

    public String getConversionValue() {
        return conversionValue;
    }

    public String getDate() {
        return date;
    }

    public void setConversionValue(String conversionValue) {
        this.conversionValue = conversionValue;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
