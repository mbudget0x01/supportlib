package com.linusba.support.environment;

import android.content.Context;

import androidx.annotation.RawRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Provides functionality to handle RAW resource Files
 * @see android.R.raw
 */
public class RawResourceHandler {

    /**
     * Returns the content of e raw Resource e.g. a json file.
     * @param resourceId e.g. R.raw.xyz.json
     * @param context The calling Context
     * @return The content of the resource as String
     * @throws IOException In Case something goes wrong loading.
     * @see android.R.raw
     */
    public static String loadStringResource(@RawRes int resourceId, Context context) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        StringBuilder s = new StringBuilder();
        try {
            while (line != null){
                    line = bufferedReader.readLine();
                    if (line != null){
                        s.append(line).append("\n");}
            }
        } catch (IOException e) {
            throw new IOException("Could not load Ressource " + resourceId, e);
        }
        return s.toString();
    }
}
