package com.crewmeister.cmcodingchallenge.service.impl;

import com.crewmeister.cmcodingchallenge.entity.CurrencyInfo;
import com.crewmeister.cmcodingchallenge.entity.CurrencyTimeValue;
import com.crewmeister.cmcodingchallenge.helper.KeyHelper;
import com.crewmeister.cmcodingchallenge.helper.XMLParser;
import com.crewmeister.cmcodingchallenge.repository.CurrencyInfoRepository;
import com.crewmeister.cmcodingchallenge.repository.CurrencyTimeValueRepository;
import com.crewmeister.cmcodingchallenge.service.CurrencyInformationService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


@Service
public class CurrencyInformationServiceImpl implements CurrencyInformationService {
    private Logger logger =  LoggerFactory.getLogger(CurrencyInformationServiceImpl.class);
    //Values from application.properties file
    @Value("${bundesbankServiceUrl}")
    private String bundesbankServiceUrl;
    @Value("${CurrencyCodelistKey}")
    private String currencyCodeKey;
    @Value("${TimeSeriesflowRefKey}")
    private String timeSeriesflowRefKey;

    @Autowired
    private CurrencyInfoRepository currencyInfoRepository;
    @Autowired
    private CurrencyTimeValueRepository currencyTimeValueRepository;

    private XMLParser xmlParser ;
    private KeyHelper keyHelper;
    public CurrencyInformationServiceImpl(){
        keyHelper = new KeyHelper();
        xmlParser= new XMLParser();
    }
    @Override
    public List<CurrencyInfo> getAllCurrencies(){
        try{
            logger.info("Return from H2 if Currency info exists in H2");
            if(currencyInfoRepository.count()!=0) {
                    return currencyInfoRepository.findAll();
            }
            else
            {
                logger.info("Populate H2 from API");
                RestTemplate restTemplate = new RestTemplate();
                List<CurrencyInfo> currencyInfoList = new ArrayList<>();
                String route = "/metadata/codelist/BBK/"+currencyCodeKey;
                URI uri = new URI(bundesbankServiceUrl+route);
                logger.info("Get all codelists for code CL_BBK_STD_CURRENCY");
                ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
                if(result.getStatusCodeValue()== HttpStatus.OK.value())
                {
                    currencyInfoList= xmlParser.getDetailsOfCurrency(result.getBody());
                    currencyInfoRepository.saveAll(currencyInfoList);
                }

                logger.info("Result status code is not 200");
                return  currencyInfoList;
            }
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            return  null;
        }

    }
    public List<CurrencyTimeValue> getAllCurrencyTimeSeries()
    {
        try{
            logger.info("Return from H2 if Currency Time Value exists in H2");
            if(currencyTimeValueRepository.count()!=0) {
                return currencyTimeValueRepository.findAll();
            }
            else
            {
                logger.info("Populate H2 from API");
                RestTemplate restTemplate = new RestTemplate();
                List<CurrencyTimeValue> currencyTimeValueList = new ArrayList<>();

                //For simplicity of this challenge, only using AUD and USD because the data being returned is a lot
                //This should ideally be a loop on all the currencyCodes returned
                ArrayList<String> codes = new ArrayList<>();
                codes.add("AUD");
                codes.add("USD");

                for (String code:codes) {
                    String key = keyHelper.CreateTimeSeriesKey(code);
                    String url= bundesbankServiceUrl+"/data/"+timeSeriesflowRefKey+"/"+key+"?detail=full";
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Accept", "*/*");
                    headers.set("Accept-Encoding", "gzip, deflate, br");
                    headers.set("User-Agent", "test");
                    HttpEntity<String> entity = new HttpEntity<>(headers);
                    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

                    if(response.getStatusCodeValue()==HttpStatus.OK.value())
                    {
                        List<CurrencyTimeValue> list =  xmlParser.GetTimeSeriesInformation(response.getBody(),code);
                        currencyTimeValueList.addAll(list);
                        currencyTimeValueRepository.saveAll(currencyTimeValueList);
                    }

                }
                return  currencyTimeValueList;
            }


        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public List<CurrencyTimeValue> getCurrencyValuesOnADay(String date) {
        try{
            if(currencyTimeValueRepository.count()==0)
            {
                logger.info("Populating H2 before find, in case of real world problems, can use cache");
                getAllCurrencyTimeSeries();
            }
            return currencyTimeValueRepository.findByDate(date);
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    @Override
    public String getCurrencyConversionValueOnADay(String currencyCode, String date) {

        try {

            logger.info("Check if Currency info exists in H2");
            if(currencyInfoRepository.count()==0)
            {
                logger.info("Fetch data in H2");
                getAllCurrencies();
            }
            logger.info("Check if currencyCode is Valid");
            if(currencyInfoRepository.findByCurrencyID(currencyCode).getCurrencyID().equals(currencyCode))
            {
                logger.info("Currency Code is valid");
                logger.info("Check if currencyTimeValueRepository exists in H2");
                if(currencyTimeValueRepository.count()==0)
                {
                    logger.info("Populate values for currencyTimeValueRepository in H2");
                    getAllCurrencyTimeSeries();
                }
                logger.info("Return value from H2");
                if(currencyTimeValueRepository.findByCurrencyCodeAndDate(currencyCode,date)!= null)
                    return currencyTimeValueRepository.findByCurrencyCodeAndDate(currencyCode,date).getConversionValue();
            }
            return  null;
        }
        catch (Exception ex)
        {
            logger.error(ex.getMessage());
            throw ex;

        }
    }
}
