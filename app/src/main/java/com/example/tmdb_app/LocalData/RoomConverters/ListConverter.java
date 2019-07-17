package com.example.tmdb_app.LocalData.RoomConverters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ListConverter {

    @TypeConverter
    public String fromLongList(List<Long> genreIds) {
        if (genreIds == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Long>>() {}.getType();
        String json = gson.toJson(genreIds, type);
        return json;
    }

    @TypeConverter
    public List<Long> toLongList(String countryLangString) {
        if (countryLangString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Long>>() {}.getType();
        List<Long> countryLangList = gson.fromJson(countryLangString, type);
        return countryLangList;
    }
}
