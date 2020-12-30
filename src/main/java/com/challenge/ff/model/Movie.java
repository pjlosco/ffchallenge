package com.challenge.ff.model;

public enum Movie {

    FF1("The Fast and the Furious", "tt0232500", 1),
    FF2("2 Fast 2 Furious", "tt0322259", 2),
    FF3("The Fast and the Furious Tokyo Drift", "tt0463985", 3),
    FF4("Fast & Furious", "tt1013752", 4),
    FF5("Fast Five", "tt1596343", 5),
    FF6("Fast & Furious 6", "tt1905041", 6),
    FF7("Furious 7", "tt2820852", 7),
    FF8("The Fate of the Furious", "tt4630562", 8);

    String title;
    String imdbID;
    int number;

    Movie(String title, String imdbID, int number) {
        this.title = title;
        this.imdbID = imdbID;
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public String getImdbID() {
        return imdbID;
    }

    public int getNumber() {
        return number;
    }

    public static Movie movieLookup(int movie) {
        if (movie < 1 || movie > 8) {
            throw new IndexOutOfBoundsException("Input is not valid");
        }
        switch (movie) {
            case 1:
                return Movie.FF1;
            case 2:
                return Movie.FF2;
            case 3:
                return Movie.FF3;
            case 4:
                return Movie.FF4;
            case 5:
                return Movie.FF5;
            case 6:
                return Movie.FF6;
            case 7:
                return Movie.FF7;
            case 8:
                return Movie.FF8;
        }
        return null;
    }
}
