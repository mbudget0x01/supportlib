package com.linusba.support.test.integration;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Helper Class for Test functions. Only static
 */
public class IntegrationTestUtil {

    /**
     * Private constructor as we don't need Instances
     */
    private IntegrationTestUtil(){}

    /**
     * Gets the AppContext
     * @return AppContext
     */
    public static Context getAppContext(){
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
