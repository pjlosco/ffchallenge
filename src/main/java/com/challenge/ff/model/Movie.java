package com.challenge.ff.model;

import io.micrometer.core.instrument.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class Movie {

    static final String key = "e4f33820";

    public enum MovieData {

        FF1("The Fast and the Furious", "tt0232500"),
        FF2("2 Fast 2 Furious", "tt0322259"),
        FF3("The Fast and the Furious Tokyo Drift", "tt0463985"),
        FF4("Fast & Furious", "tt1013752"),
        FF5("Fast Five", "tt1596343"),
        FF6("Fast & Furious 6", "tt1905041"),
        FF7("Furious 7", "tt2820852"),
        FF8("The Fate of the Furious", "tt4630562");

        String title;
        String imdbID;
        MovieData(String title, String imdbID){
            this.title = title;
            this.imdbID = imdbID;
        }

        public String getTitle() {
            return title;
        }

        public String getImdbID() {
            return imdbID;
        }
    }

    int number;
    MovieData movieData;

    public Movie(int number) {
        this.number = number;
        this.movieData = movieLookup(number);
    }

    public Movie(int number, MovieData data) {
        this.number = number;
        this.movieData = data;
    }

    public String getInfo() {
        try {
            String uri = "http://www.omdbapi.com/?apikey=" + key + "&i=" + this.movieData.imdbID;
            URL url = new URL(uri);
            URLConnection con = url.openConnection();
            InputStream in = con.getInputStream();
            return IOUtils.toString(in);
        } catch (IOException e) {

        }
        return null;
    }


    public Movie.MovieData movieLookup(int movie) {
        if (movie < 1 || movie > 8) {
            throw new IndexOutOfBoundsException("Input is not valid");
        }
        switch (number) {
            case 1:
                return Movie.MovieData.FF1;
            case 2:
                return Movie.MovieData.FF2;
            case 3:
                return Movie.MovieData.FF3;
            case 4:
                return Movie.MovieData.FF4;
            case 5:
                return Movie.MovieData.FF5;
            case 6:
                return Movie.MovieData.FF6;
            case 7:
                return Movie.MovieData.FF7;
            case 8:
                return Movie.MovieData.FF8;
        }
        return null;
    }
}
