package com.example.mypregnancyjourney;

public class Timeslot {
    private int id;
    private String time;
    private boolean isBooked;
    private String date;

    public Timeslot(int id, String time, boolean isBooked) {
        this.id = id;
        this.time = time;
        this.isBooked = isBooked;
    }

    public Timeslot(int id, String time, boolean isBooked, String date) {
        this.id = id;
        this.time = time;
        this.isBooked = isBooked;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public boolean getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(boolean booked) {
        isBooked = booked;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
