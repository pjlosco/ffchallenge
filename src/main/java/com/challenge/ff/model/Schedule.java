package com.challenge.ff.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.*;

public class Schedule {

    Logger logger = LoggerFactory.getLogger(Schedule.class);

    Map<LocalDate, Map<Movie, List<Showtime>>> movieDateMap;

    /**
     * Create new schedule
     */
    public Schedule() {
        logger.info("Creating new schedule");
        movieDateMap = new HashMap<LocalDate, Map<Movie, List<Showtime>>>();
    }

    /**
     * Imports from file
     * @param filePath
     */
    public Schedule(String filePath) {

    }

    private Map<Movie, List<Showtime>> newMovieListMap() {
        Map<Movie, List<Showtime>> movieListMap = new HashMap<Movie, List<Showtime>>();
        for (Movie movie : Arrays.asList(Movie.values())) {
            List<Showtime> showtimes = new ArrayList<Showtime>();
            movieListMap.put(movie,  showtimes);
        }
        return movieListMap;
    }

    public void resetShowtimes(List<Showtime> showtimes, LocalDate date, int movieNumber) {
        Movie movie = Movie.movieLookup(movieNumber);
        Map<Movie, List<Showtime>> movieListMap = movieDateMap.get(date);
        if (movieListMap != null) {
            movieListMap.put(movie, showtimes);
        }
        movieDateMap.put(date, movieListMap);
    }

    public List<Showtime> getShowtimeList(LocalDate date, int movieNumber) {
        logger.info("Getting showtimes from schedule");
        Movie movie = Movie.movieLookup(movieNumber);
        Map<Movie, List<Showtime>> movieListMap = movieDateMap.get(date);
        if (movieListMap == null) {
            logger.info("No show times found");
            return new ArrayList<Showtime>();
        } else {
            return movieListMap.get(movie);
        }
    }

    public void addShowtime(LocalDate date, Showtime newShowtime, int movieNumber) {
        addOrUpdateShowtime(date, newShowtime, movieNumber, false);
    }

    public void updateShowtime(LocalDate date, Showtime newShowtime, int movieNumber) {
        addOrUpdateShowtime(date, newShowtime, movieNumber, true);
    }

    private void addOrUpdateShowtime(LocalDate date, Showtime newShowtime, int movieNumber, boolean update) {
        Movie movie = Movie.movieLookup(movieNumber);
        if (movieDateMap.get(date) == null) {
            Map<Movie, List<Showtime>> movieListMap = newMovieListMap();
            List<Showtime> showtimes = new ArrayList<Showtime>();
            showtimes.add(newShowtime);
            movieListMap.put(movie, showtimes);
            movieDateMap.put(date, movieListMap);
        } else {
            Map<Movie, List<Showtime>> movieListMap = movieDateMap.get(date);
            List<Showtime> showtimeList = movieListMap.get(movie);
            for (Showtime showtime : showtimeList) {
                if (showtime.getTimeOfShow().equals(newShowtime.getTimeOfShow())) {
                    if (update) {
                        showtimeList.remove(showtime);
                        showtimeList.add(newShowtime);
                    }
                } else {
                    showtimeList.add(newShowtime);
                }
            }
            movieListMap.put(movie, showtimeList);
        }
    }

}
