package com.spring.beer.services;

import com.spring.beer.web.model.BeerDto;
import com.spring.beer.web.model.BeerPagedList;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerService {

    BeerPagedList listBeers(Pageable pageable);

    BeerDto getBeerById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
}
