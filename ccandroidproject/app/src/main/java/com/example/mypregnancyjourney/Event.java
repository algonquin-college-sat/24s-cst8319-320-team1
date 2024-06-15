package com.example.mypregnancyjourney;

public class Event {
    private String date;
    private String event1;
    private String event2;
    private String event3;

    public Event(String date, String event1, String event2, String event3) {
        this.date = date;
        this.event1 = event1;
        this.event2 = event2;
        this.event3 = event3;
    }

    public String getDate() {
        return date;
    }

    public String getEvent1() {
        return event1;
    }

    public String getEvent2() {
        return event2;
    }

    public String getEvent3() {
        return event3;
    }
}
