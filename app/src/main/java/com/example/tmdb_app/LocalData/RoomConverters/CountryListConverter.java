package com.example.tmdb_app.LocalData.RoomConverters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class CountryListConverter {
    @TypeConverter
    public String fromStringList(List<String> countryNames) {
        if (countryNames == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        String json = gson.toJson(countryNames, type);
        return json;
    }

    @TypeConverter
    public List<String> toStringList(String countryList) {
        if (countryList == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<String>>() {}.getType();
        List<String> countryFinalList = gson.fromJson(countryList, type);
        return countryFinalList;
    }
}
