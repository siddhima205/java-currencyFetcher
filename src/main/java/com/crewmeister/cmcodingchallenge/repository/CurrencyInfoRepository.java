package com.crewmeister.cmcodingchallenge.repository;


import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;

import com.crewmeister.cmcodingchallenge.entity.CurrencyInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CurrencyInfoRepository extends CrudRepository<CurrencyInfo, Long>  {
	
	CurrencyInfo findByCurrencyID(String currencyID);
	CurrencyInfo findByCurrencyNameEN(String currencyNameEN);
	List<CurrencyInfo> findAll();
}
