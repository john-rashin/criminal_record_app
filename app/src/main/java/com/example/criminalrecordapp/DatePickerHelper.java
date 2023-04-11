package com.example.criminalrecordapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.widget.DatePicker;
import java.util.Calendar;

public class DatePickerHelper {

    private Context context;
    private OnDateSetListener listener;

    public DatePickerHelper(Context context, OnDateSetListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String date = year + "/" + (month+1) + "/" + day;
                listener.onDateSet(date);
            }
        }, year, month, day);
        dialog.show();
    }

    public interface OnDateSetListener {
        void onDateSet(String date);
    }
}

