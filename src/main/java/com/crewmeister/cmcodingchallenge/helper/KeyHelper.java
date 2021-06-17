package com.crewmeister.cmcodingchallenge.helper;

import com.crewmeister.cmcodingchallenge.models.TimeSeriesKey;

public class KeyHelper {

    public String CreateTimeSeriesKey(String code){

        return new TimeSeriesKey(code).toString();
    }

}
