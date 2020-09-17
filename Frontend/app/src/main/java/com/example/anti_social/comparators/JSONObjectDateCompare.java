package com.example.anti_social.comparators;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * A class to be used to help sort the array of posts served to the end user.
 */
public class JSONObjectDateCompare implements Comparator<JSONObject> {
    @Override
    public int compare(JSONObject first, JSONObject second){
        Date firstDate, secondDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
            firstDate = sdf.parse(first.getString("updatedAt").replaceAll("T"," "));
            secondDate = sdf.parse(second.getString("updatedAt").replaceAll("T"," "));
            System.out.print("First Date: "+firstDate+"\nSecond Date: "+secondDate);
            return secondDate.compareTo(firstDate);
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
        return -2;
    }
}