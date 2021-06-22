package com.fes.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.fes.View.Interface.TimeDateCallBack;

import java.util.Calendar;

public class DateTimePicker {

    Activity mActivity;
    String DateSignature="yyyy-M-D";String TimeSignature="HH:mm:ss";
    public DateTimePicker()
    { }
    public DateTimePicker(Activity activity)
    {
        this.mActivity=activity;
    }
    public void setDateSignature(String signature)
    {
        this.DateSignature=signature;
    }
    public void setTimeSignature(String signature)
    {
        this.TimeSignature=signature;
    }
    public boolean showTimeDatePicker(boolean Datepicker, boolean Timepicker,TimeDateCallBack timeDateCallBack)
    {
        if (Datepicker)
        {
            try {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(mActivity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                timeDateCallBack.onSuccess(year+ "-" +(monthOfYear + 1) + "-" +dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }catch (Exception e)
            {
                return false;
            }
        }else if (Timepicker)
        {
            try {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(mActivity,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                timeDateCallBack.onSuccess(hourOfDay+ ":" +minute + ":" +"00");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }catch (Exception e)
            {
                return false;
            }

        }
        return true;
    }
}
