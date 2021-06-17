package com.crewmeister.cmcodingchallenge.entity;

import javax.persistence.*;

@Entity
public class CurrencyInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
    @Column
    private String currencyID;
    @Column
    private String currencyNameEN;
    @Column
    private String currencyNameDE;

    public CurrencyInfo(){}
    public CurrencyInfo(String currencyID,String currencyNameEN,String currencyNameDE)
    {
        this.currencyID=currencyID;
        this.currencyNameEN=currencyNameEN;
        this.currencyNameDE=currencyNameDE;
    }



    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getCurrencyNameEN() {
        return currencyNameEN;
    }

    public void setCurrencyNameEN(String currencyNameEN) {
        this.currencyNameEN = currencyNameEN;
    }

    public String getCurrencyNameDE() {
        return currencyNameDE;
    }

    public void setCurrencyNameDE(String currencyNameDE) {
        this.currencyNameDE = currencyNameDE;
    }
}
