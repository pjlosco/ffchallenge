package com.challenge.ff.model;

import java.time.LocalTime;

public class Showtime {

    private LocalTime timeOfShow;
    private double admissionPrice;

    public Showtime(LocalTime timeOfShow, double admissionPrice) {
        this.timeOfShow = timeOfShow;
        this.admissionPrice = admissionPrice;
    }

    public LocalTime getTimeOfShow() {
        return timeOfShow;
    }

    public double getAdmissionPrice() {
        return admissionPrice;
    }
}
