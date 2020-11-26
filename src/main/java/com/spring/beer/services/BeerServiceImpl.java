package com.spring.beer.services;

import com.spring.beer.domain.Beer;
import com.spring.beer.exceptions.BeerNotFoundException;
import com.spring.beer.repositories.BeerRepository;

import com.spring.beer.web.mappers.BeerMapper;
import com.spring.beer.web.model.BeerDto;
import com.spring.beer.web.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public BeerPagedList listBeers(Pageable pageable) {
        Page<Beer> beerPage = beerRepository.findAll(pageable);

        return new BeerPagedList(beerPage
                .stream()
                .map(beerMapper::beerToBeerDto)
                .collect(Collectors.toList()), PageRequest.of(
                beerPage.getPageable().getPageNumber(),
                beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());

    }

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return beerMapper.beerToBeerDto(
                beerRepository.findById(beerId).orElseThrow(BeerNotFoundException::new)
        );
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return beerMapper.beerToBeerDto(
                beerRepository.save(beerMapper.beerDtoToBeer(beerDto))
        );
    }

    @Override
    public BeerDto updateBeer(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(BeerNotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beerRepository.save(beer));
    }
}
