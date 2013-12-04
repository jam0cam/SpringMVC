package com.example.controller;

import com.example.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jitse
 * Date: 12/4/13
 * Time: 12:58 PM
 */

public class Util {

    public static String getMonthName(int i) {
        if (i == 1 || i == 13) {
            return "Jan";
        } else if (i == 2) {
            return "Feb";
        } else if (i == 3) {
            return "Mar";
        } else if (i == 4) {
            return "Apr";
        } else if (i == 5) {
            return "May";
        } else if (i == 6) {
            return "Jun";
        } else if (i == 7) {
            return "Jul";
        } else if (i == 8) {
            return "Aug";
        } else if (i == 9) {
            return "Sep";
        } else if (i == 10) {
            return "Oct";
        } else if (i == 11) {
            return "Nov";
        } else if (i == 12 || i == 0) {
            return "Dec";
        }

        return "???";
    }

    /**
     * 0 = Jan, 11 = Dec
     * @param begin
     * @param end
     * @return
     */
    public static List<String> getMonthList(int begin, int end) {
        List<String> rval = new ArrayList<String>();

        for (int i=begin; i<=end; i++) {
            rval.add(getMonthName(i+1));
        }

        return rval;
    }

    public static void trimRegister(Player user) {
        user.setPassword(user.getPassword().trim());
        user.setEmail(user.getEmail().trim());
        user.setName(user.getName().trim());
    }

}
