package graph.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Json {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

}
