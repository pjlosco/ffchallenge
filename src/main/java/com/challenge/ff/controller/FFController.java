package com.challenge.ff.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.challenge.ff.model.Movie;
import com.challenge.ff.model.Review;
import com.challenge.ff.model.Schedule;
import com.challenge.ff.model.Showtime;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FFController {

	Schedule schedule;
	Map<Movie, String> movieInfo;
	Map<Movie, List<Review>> reviewMap;

	FFController() {
		// TODO import data if file found

		// else
		this.schedule = new Schedule();
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
		Showtime showtime = new Showtime(time, admissionPrice);
		schedule.addShowtime(date, showtime, movie);
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
		Showtime showtime = new Showtime(time, admissionPrice);
		schedule.updateShowtime(date, showtime, movie);
		return true;
	}

	/**
	 * An endpoint in which their customers (i.e. moviegoers) can fetch movie times
	 */
	@GetMapping("/getshowtimes")
	@ResponseBody
	public String getShowtimes(@RequestParam(name="date") LocalDate date, @RequestParam(name="movie") int movie) {
		List<Showtime> showtimes = schedule.getShowtimeList(date, movie);
		JsonArray jsonShowtimesArray = new JsonArray();
		for (Showtime showtime : showtimes) {
			JsonObject showtimeJsonObject = new JsonObject();
			showtimeJsonObject.addProperty("time", showtime.getTimeOfShow().toString());
			showtimeJsonObject.addProperty("price", showtime.getAdmissionPrice());
			jsonShowtimesArray.add(showtimeJsonObject);
		}
		JsonObject jsonResultObject = new JsonObject();
		jsonResultObject.add("Showtimes", jsonShowtimesArray);
		return jsonResultObject.getAsString();
	}

	/**
	 * An endpoint in which their customers (i.e. moviegoers) can fetch details about one of their movies
	 * (e.g. name, description, release date, rating, IMDb rating, and runtime).
	 * Even though there's a limited offering, please use the OMDb APIs (detailed below) to demonstrate how to communicate across APIs.
	 */
	@GetMapping("/movie")
	@ResponseBody
	public String getMovieDetails(@RequestParam(name="movie") int movieNumber) {
		Movie movie = Movie.movieLookup(movieNumber);
		String details = movieInfo.get(movie);
		if (details != null && !details.isEmpty()) {
			return details;
		} else {
			return getInfo(movie.getImdbID());
		}
	}

	private String getInfo(String imdbID) {
		String details = "";
		String key = "e4f33820";
		try {
			String uri = "http://www.omdbapi.com/?apikey=" + key + "&i=" + imdbID;
			URL url = new URL(uri);
			URLConnection con = url.openConnection();
			InputStream in = con.getInputStream();
			details = IOUtils.toString(in);
		} catch (IOException e) {
		}
		return details;
	}


	/**
	 * An endpoint in which their customers (i.e. moviegoers) can leave a review rating (from 1-5 stars) about a particular movie
	 */
	@GetMapping("/review")
	@ResponseBody
	public boolean review(@RequestParam(name="movie") int movieNumber,
						  @RequestParam(name="rating") int rating,
						  @RequestParam(name="author") String author,
						  @RequestParam(name="explanation") String explanation) {
		Review review = new Review(rating, author, explanation);
		Movie movie = Movie.movieLookup(movieNumber);
		List<Review> reviews = reviewMap.get(movie);
		reviews.add(review);
		reviewMap.put(movie,reviews);
		return true;
	}

	/**
	 * And adding anything else that you think will be useful for the user...
	 */
}
