package com.example.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * User: jitse
 * Date: 12/26/13
 * Time: 6:48 PM
 */
public class Test {

    public static void main(String[] args){

        Date now = new Date();

        DateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
        formatter.setTimeZone(TimeZone.getTimeZone("EST"));

        System.out.println(formatter.format(now));

    }

}
