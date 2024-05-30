package com.example.mypregnancyjourney;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

public class TodayDecorator implements DayViewDecorator {

    private CalendarDay today;

    public TodayDecorator() {
        today = CalendarDay.today();
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return today != null && day.equals(today);
    }

    @Override
    public void decorate(DayViewFacade view) {

        view.setBackgroundDrawable(new ColorDrawable(Color.RED));
    }
}