package com.example.foodapp;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonParser {
    public static Map<String, Dish> parseJson(Context context, String filename) {
        String jsonString = loadJsonFromAssets(context, filename);

        if (jsonString == null) {
            System.err.println("JSON file could not be read: " + filename);
            return null;
        }

        Gson gson = new Gson();
        try {
            return gson.fromJson(jsonString, new TypeToken<Map<String, Dish>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String loadJsonFromAssets(Context context, String filename) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
