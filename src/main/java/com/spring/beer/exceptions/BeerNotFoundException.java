package com.spring.beer.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Error: Beer is not found.")
public class BeerNotFoundException extends RuntimeException {

}
