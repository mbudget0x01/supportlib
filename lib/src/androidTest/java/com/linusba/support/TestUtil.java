package com.linusba.support;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Helper Class for Test functions. Only static
 */
public class TestUtil {

    /**
     * Private constructor as we don't need Instances
     */
    private TestUtil(){}

    /**
     * Gets the AppContext
     * @return AppContext
     */
    public static Context getAppContext(){
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
