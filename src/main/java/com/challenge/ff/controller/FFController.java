package com.challenge.ff.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.challenge.ff.model.Movie;
import com.challenge.ff.model.Review;
import com.challenge.ff.model.Schedule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FFController {

	Schedule movieDateMap;
	Map<Integer, List<Review>> reviewMap;

	FFController() {
		// TODO import data if file found

		// else
		this.movieDateMap = new HashMap<>();
		this.reviewMap = new HashMap<>();
	}

	@GetMapping("/")
	@ResponseBody
	public String index() {
		return "Index";
	}

	/**
	 * Add showtime
	 */
	@GetMapping("/add")
	@ResponseBody
	public boolean addShowtime(@RequestParam(name="movie") int movie, @RequestParam(name="datetime") LocalDateTime timeOfShow, @RequestParam(name="price") double admissionPrice) {
		LocalDate date = timeOfShow.toLocalDate();
		LocalTime time = timeOfShow.toLocalTime();
		// todo get movie at date, add showtime if not existing, put updated movie back in map
		movieDateMap.get(date).get()
		return true;
	}

	/**
	 * An internal endpoint in which they (i.e. the cinema owners) can update show times and prices for their movie catalog
	 */
	@GetMapping("/update")
	@ResponseBody
	public boolean updateShowtime(@RequestParam(name="movie") int movie, @RequestParam(name="datetime") LocalDateTime timeOfShow, @RequestParam(name="price") double admissionPrice) {
		LocalDate date = timeOfShow.toLocalDate();
		LocalTime time = timeOfShow.toLocalTime();
		// todo get movie at date, get showtime if existing, put updated movie back in map, else nothing
		return true;
	}

	/**
	 * An endpoint in which their customers (i.e. moviegoers) can fetch movie times
	 */
	@GetMapping("/getshowtimes")
	@ResponseBody
	public boolean getShowtimes(@RequestParam(name="date") LocalDate date) {
		// todo get move list for date
		return true;
	}

	/**
	 * An endpoint in which their customers (i.e. moviegoers) can fetch details about one of their movies
	 * (e.g. name, description, release date, rating, IMDb rating, and runtime).
	 * Even though there's a limited offering, please use the OMDb APIs (detailed below) to demonstrate how to communicate across APIs.
	 */
	@GetMapping("/movie")
	@ResponseBody
	public String getMovieDetails(@RequestParam(name="movie") int movieNumber) {
		Movie movie = new Movie(movieNumber);
		return movie.getInfo();
	}

	/**
	 * An endpoint in which their customers (i.e. moviegoers) can leave a review rating (from 1-5 stars) about a particular movie
	 */
	@GetMapping("/review")
	@ResponseBody
	public boolean review(@RequestParam(name="movie") int movie, @RequestParam(name="rating") int rating) {
		// todo add review to map
		return true;
	}

	/**
	 * And adding anything else that you think will be useful for the user...
	 */
}
