package com.challenge.ff.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class FFController {
	Logger logger = LoggerFactory.getLogger(FFController.class);
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

	Schedule schedule;
	Map<Movie, String> movieInfo;
	Map<Movie, List<Review>> reviewMap;

	FFController() {
		logger.info("Creating new FF controller");
		this.schedule = new Schedule();
		this.movieInfo = new HashMap<Movie, String>();
		this.reviewMap = new HashMap<Movie, List<Review>>();
	}

	@GetMapping("/save")
	@ResponseBody
	public void save() {
		schedule.writeToFile();
	}


	@GetMapping("/")
	@ResponseBody
	public String index() {
		return "Index";
	}

	/**
	 * Add showtime
	 * TODO - switch to post mapping
	 * curl -X POST --header "Content-Type: application/json" http://localhost:9000/add --data '{"movie":"1","date":"2020-12-22","time":"08:00:00","price":"1"}'
	 */
	@GetMapping(value="/add")
	@ResponseBody
	public boolean addShowtime(@RequestParam String movie, @RequestParam String date, @RequestParam String time, @RequestParam String price) {
		logger.info("Adding showtime");
		LocalDate localDate = LocalDate.parse(date, dateFormatter);
		LocalTime localTime = LocalTime.parse(time, timeFormatter);
		Showtime showtime = new Showtime(localTime, Double.parseDouble(price));
		schedule.addShowtime(localDate, showtime, Integer.parseInt(movie));
		return true;
	}

	/**
	 * An internal endpoint in which they (i.e. the cinema owners) can update show times and prices for their movie catalog
	 */
	@GetMapping("/update")
	@ResponseBody
	public boolean updateShowtime(@RequestParam String movie, @RequestParam String date, @RequestParam String time, @RequestParam String price) {
		LocalDate localDate = LocalDate.parse(date, dateFormatter);
		LocalTime localTime = LocalTime.parse(time, timeFormatter);
		Showtime showtime = new Showtime(localTime, Double.parseDouble(price));
		schedule.updateShowtime(localDate, showtime, Integer.parseInt(movie));
		return true;
	}

	/**
	 * An endpoint in which their customers (i.e. moviegoers) can fetch movie times
	 * ex; http://localhost:9000/getshowtimes?date=2020-12-22&movie=1
	 */
	@GetMapping("/getshowtimes")
	@ResponseBody
	public String getShowtimes(@RequestParam String date, @RequestParam String movie) {
		logger.info("Retrieving showtimes");
		LocalDate localDate = LocalDate.parse(date, dateFormatter);
		List<Showtime> showtimes = schedule.getShowtimeList(localDate, Integer.parseInt(movie));
		JsonArray jsonShowtimesArray = new JsonArray();
		for (Showtime showtime : showtimes) {
			JsonObject showtimeJsonObject = new JsonObject();
			showtimeJsonObject.addProperty("time", showtime.getTimeOfShow().toString());
			showtimeJsonObject.addProperty("price", showtime.getAdmissionPrice());
			jsonShowtimesArray.add(showtimeJsonObject);
		}
		JsonObject jsonResultObject = new JsonObject();
		jsonResultObject.add("showtimes", jsonShowtimesArray);
		String result = jsonResultObject.toString();
		logger.info("Result: " + result);
		return result;
	}

	/**
	 * An endpoint in which their customers (i.e. moviegoers) can fetch details about one of their movies
	 * (e.g. name, description, release date, rating, IMDb rating, and runtime).
	 * Even though there's a limited offering, please use the OMDb APIs (detailed below) to demonstrate how to communicate across APIs.
	 */
	@GetMapping("/moviedetails")
	@ResponseBody
	public String getMovieDetails(@RequestParam String movie) {
		Movie ffmovie = Movie.movieLookup(Integer.parseInt(movie));
		String details = movieInfo.get(ffmovie);
		if (details != null && !details.isEmpty()) {
			return details;
		} else {
			return getInfo(ffmovie.getImdbID());
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
	public boolean review(@RequestParam String movieNumber,
						  @RequestParam String rating,
						  @RequestParam String author,
						  @RequestParam String explanation) {
		Review review = new Review(Integer.parseInt(rating), author, explanation);
		Movie movie = Movie.movieLookup(Integer.parseInt(movieNumber));
		List<Review> reviews = reviewMap.get(movie);
		reviews.add(review);
		reviewMap.put(movie,reviews);
		return true;
	}

	/**
	 * And adding anything else that you think will be useful for the user...
	 */
}
