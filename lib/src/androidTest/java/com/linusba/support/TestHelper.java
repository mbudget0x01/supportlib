package com.linusba.support;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

/**
 * Helper Class for Test functions
 */
public class TestHelper {
    /**
     * Gets the AppContext
     * @return AppContext
     */
    public static Context getAppContext(){
        return InstrumentationRegistry.getInstrumentation().getTargetContext();
    }
}
