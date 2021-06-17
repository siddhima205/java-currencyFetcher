package com.crewmeister.cmcodingchallenge.repository;

import com.crewmeister.cmcodingchallenge.entity.CurrencyTimeValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CurrencyTimeValueRepository  extends CrudRepository<CurrencyTimeValue,Long> {
    List<CurrencyTimeValue> findByCurrencyCode(String currencyCode);
    CurrencyTimeValue findByCurrencyCodeAndDate(String currencyCode,String date);
    List<CurrencyTimeValue> findByDate(String date);
    List<CurrencyTimeValue> findAll();
}
