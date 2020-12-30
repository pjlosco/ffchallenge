package com.challenge.ff.model;

import java.time.LocalDate;
import java.util.*;

public class Schedule {
    Map<LocalDate, Map<Movie, List<Showtime>>> movieDateMap;

    /**
     * Create new schedule
     */
    public Schedule() {
        movieDateMap = new HashMap<>();
    }

    /**
     * Imports from file
     * @param filePath
     */
    public Schedule(String filePath) {

    }

    private Map<Movie, List<Showtime>> newMovieListMap() {
        Map<Movie, List<Showtime>> movieListMap = new HashMap<>();
        for (Movie movie : Arrays.asList(Movie.values())) {
            List<Showtime> showtimes = new ArrayList<>();
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
        Movie movie = Movie.movieLookup(movieNumber);
        Map<Movie, List<Showtime>> movieListMap = movieDateMap.get(date);
        if (movieListMap == null) {
            return new ArrayList<>();
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
            List<Showtime> showtimes = new ArrayList<>();
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
