package com.example.mypregnancyjourney;

import android.graphics.Color;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    private HashSet<CalendarDay> datesWithEvents;

    public EventDecorator(HashSet<CalendarDay> dates) {
        this.datesWithEvents = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return datesWithEvents.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, Color.BLACK));
    }
}
