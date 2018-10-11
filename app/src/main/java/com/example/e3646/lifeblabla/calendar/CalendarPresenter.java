package com.example.e3646.lifeblabla.calendar;


import static com.google.common.base.Preconditions.checkNotNull;

public class CalendarPresenter implements CalendarContract.Presenter {

    private CalendarContract.View mCalendarView;

    public CalendarPresenter(CalendarContract.View calendarView) {
        mCalendarView = checkNotNull(calendarView);
        mCalendarView.setPresenter(this);
    }



    @Override
    public void start() {

    }


}
