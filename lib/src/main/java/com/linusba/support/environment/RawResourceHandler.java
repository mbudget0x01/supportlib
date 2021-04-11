package com.linusba.support.environment;

import android.app.Activity;

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
     * @param ressourceId e.g. R.raw.xyz.json
     * @param activity The calling Activity for Context
     * @return The content of the resource as String
     * @throws IOException In Case something goes wrong loading.
     * @see android.R.raw
     */
    public static String loadStringResource(int ressourceId, Activity activity) throws IOException {
        InputStream inputStream = activity.getResources().openRawResource(ressourceId);
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
            throw new IOException("Could not load Ressource " + ressourceId, e);
        }
        return s.toString();
    }
}
