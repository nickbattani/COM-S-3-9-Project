package com.example.anti_social.comparators;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CompareTest {
    @Test
    public void testCompare() throws JSONException {
        JSONObject one = mock(JSONObject.class);
        JSONObject two = mock(JSONObject.class);
        JSONObjectDateCompare test = new JSONObjectDateCompare();
        when(one.getString("updatedAt")).thenReturn("2020-03-04T15:34:59");
        when(two.getString("updatedAt")).thenReturn("2020-03-09T21:24:27");
        assertEquals(1, test.compare(one, two));
    }
}