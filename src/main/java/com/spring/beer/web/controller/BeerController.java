package com.spring.beer.web.controller;

import com.spring.beer.repositories.BeerRepository;
import com.spring.beer.services.BeerService;
import com.spring.beer.web.mappers.BeerMapper;
import com.spring.beer.web.model.BeerDto;
import com.spring.beer.web.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    @GetMapping("orders")
    public BeerPagedList listOrders(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                    @RequestParam(value = "pageSize", required = false) Integer pageSize){

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        return beerService.listBeers(PageRequest.of(pageNumber, pageSize));
    }


    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId){
        return new ResponseEntity(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@Valid @RequestBody BeerDto beerDto){
        return new ResponseEntity(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping({"/{beerId}"})
    public ResponseEntity updateBeerById(@PathVariable("beerId") UUID beerId,@Valid @RequestBody BeerDto beerDto){

        return new ResponseEntity(beerService.updateBeer(beerId,beerDto), HttpStatus.NO_CONTENT);
    }
}
