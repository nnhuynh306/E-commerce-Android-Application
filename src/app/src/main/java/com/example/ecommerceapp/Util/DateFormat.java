package com.example.ecommerceapp.Util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateFormat {
    @SuppressLint("SimpleDateFormat")
    public static final SimpleDateFormat ddmmyyyy =new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat ddmmmyyyy =new SimpleDateFormat("dd-MMM-yyyy");
    public static final SimpleDateFormat mmddyyyy =new SimpleDateFormat("MM dd, yyyy");
    public static final SimpleDateFormat emmmddyyyy=new SimpleDateFormat("E, MMM dd yyyy");
    public static final SimpleDateFormat emmmddyyyyhhmmss =new SimpleDateFormat("E, MMM dd yyyy HH:mm:ss");
    public static final SimpleDateFormat ddmmyyyyhhmmss=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
}
