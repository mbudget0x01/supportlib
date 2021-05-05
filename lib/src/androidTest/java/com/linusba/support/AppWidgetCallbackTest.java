package com.linusba.support;

import android.app.PendingIntent;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.linusba.support.widget.AppWidgetCoordinator;
import com.linusba.support.widget.CallbackAppWidgetProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Tests the Callback Mechanism for AppWidgets
 */
@RunWith(AndroidJUnit4.class)
public class AppWidgetCallbackTest {

    private static final String CHANGED_PROPERTY_NAME = "test.property";

    /**
     * Tests the Callback fo a onPropertyChanged Callback
     */
    @Test
    public void onPropertyChangedCallbackTest() {
        // Context of the app under test.
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        MyAppWidgetProvider myAppWidgetProvider = new MyAppWidgetProvider();
        MyAppWidgetProvider.subscribeOnPropertyChangedCallback(context, MyAppWidgetProvider.class);
        AppWidgetCoordinator appWidgetCoordinator = new AppWidgetCoordinator();
        try {
            AppWidgetCoordinator.sendOnPropertyChanged(context,CHANGED_PROPERTY_NAME);
        } catch (PendingIntent.CanceledException e) {
            assertTrue(e.getMessage(),true);
        }

        MyAppWidgetProvider.unsubscribeOnPropertyChangedCallback(context,MyAppWidgetProvider.class);

    }

    private static class MyAppWidgetProvider extends CallbackAppWidgetProvider{
        public MyAppWidgetProvider(){
            super();
        }

        @Override
        public void onPropertyChanged(Context context, @Nullable String property) {
            assertEquals(CHANGED_PROPERTY_NAME,property);
        }
    }
}
