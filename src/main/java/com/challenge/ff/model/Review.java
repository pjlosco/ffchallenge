package com.challenge.ff.model;

public class Review {

    private int rating;
    private String author;
    private String explanation;

    public Review(int rating, String author, String explanation) {
        if (rating < 1 || rating > 5) {
            throw new RuntimeException("Error with input on rating");
        } else {
            this.rating = rating;
            this.author = author;
            this.explanation = explanation;
        }
    }

    public int getRating() {
        return rating;
    }

    public String getExplanation() {
        return explanation;
    }
}
