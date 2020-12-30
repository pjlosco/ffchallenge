package com.challenge.ff.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
        this.loadFromFile();
    }

    protected void loadFromFile() {
        try {
            URL scheduleDirectory = getClass().getClassLoader().getResource("schedule");
            if (scheduleDirectory != null) {
                File[] dateFolders = (new File(scheduleDirectory.toURI())).listFiles();
                Map<Movie, List<Showtime>> movieTimes = new HashMap<Movie, List<Showtime>>();
                for (File dateFolder : dateFolders) {
                    for (int movie = 1; movie <= Movie.values().length; movie++) {
                        File movieSchedule = new File(dateFolder.getPath() + "/" + movie + "/data.csv");
                        BufferedReader csvReader = new BufferedReader(new FileReader(movieSchedule));
                        String row = csvReader.readLine();
                        List<Showtime> showtimeList = new ArrayList<Showtime>();
                        while (row != null) {
                            String[] data = row.split(",");
                            LocalTime timeOfShow = LocalTime.parse(data[0]);
                            double admissionPrice = Double.parseDouble(data[1]);
                            showtimeList.add(new Showtime(timeOfShow, admissionPrice));

                            row = csvReader.readLine();
                        }
                        csvReader.close();
                        movieTimes.put(Movie.movieLookup(movie), showtimeList);
                    }
                    movieDateMap.put(LocalDate.parse(dateFolder.getName()), movieTimes);
                }
            }
        } catch (URISyntaxException | IOException e) {
            logger.error("Could not load data");
        }
    }

    public void writeToFile() {
        for (Map.Entry<LocalDate, Map<Movie, List<Showtime>>> entry : movieDateMap.entrySet()) {
            URL scheduleDirectory = getClass().getClassLoader().getResource("schedule");
            if (scheduleDirectory == null) {
                // TODO
            }

            String dateDirectory = entry.getKey().toString();
            Map<Movie, List<Showtime>> movieMap = entry.getValue();
        }
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
