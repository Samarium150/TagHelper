package io.github.samarium150.minecraft.mod.taghelper.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public final class GeneralUtil {
    
    public final static String MOD_ID = "taghelper";
    
    public final static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    private GeneralUtil() { }
    
    public static String getPrettyDisplay(String json) {
        return gson.toJson(JsonParser.parseString(json));
    }
}
