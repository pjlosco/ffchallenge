package com.challenge.ff.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Schedule {
    Map<LocalDate, Map<Movie, List<Showtime>>> movieDateMap;


    public void resetAllShowtimes(List<Showtime> showtimes) {
        List<Showtime> showtimeList = showtimes;
        // TODO
    }

    public List<Showtime> getShowtimeList(LocalDate date, int movie) {
        // TODO
        return null;
    }

    public void addShowtime(Showtime showtime) {
        // TODO
//        if (showtimeList.isEmpty()) {
//            showtimeList = new ArrayList<Showtime>();
//            showtimeList.add(showtime);
//        } else {
//            // TODO
//        }
    }



}
