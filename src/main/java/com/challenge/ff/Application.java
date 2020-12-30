package com.challenge.ff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Challenge
 * A small cinema, which only plays movies from the Fast & Furious franchise, is looking to develop a mobile/web app for their users. Specifically, they wish to support the following functions:
 *
 * An internal endpoint in which they (i.e. the cinema owners) can update show times and prices for their movie catalog
 * An endpoint in which their customers (i.e. moviegoers) can fetch movie times
 * An endpoint in which their customers (i.e. moviegoers) can fetch details about one of their movies (e.g. name, description, release date, rating, IMDb rating, and runtime). Even though there's a limited offering, please use the OMDb APIs (detailed below) to demonstrate how to communicate across APIs.
 * An endpoint in which their customers (i.e. moviegoers) can leave a review rating (from 1-5 stars) about a particular movie
 * And adding anything else that you think will be useful for the user...
 * You will be responsible for the first iteration of API which will be used by another engineer, who is developing a studio cinema mobile application. To assist him/her, your design should do the following:
 *
 * Creating a persistence layer to save results for certain actions (e.g. creating an new reviews)
 * Present API documentation leveraging OpenAPI/Swagger 2.0 standard
 */

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
