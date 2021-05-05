package com.linusba.support.test.integration;

import android.app.PendingIntent;
import android.content.Context;

import androidx.annotation.Nullable;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.linusba.support.widget.AppWidgetCoordinator;
import com.linusba.support.widget.CallbackAppWidgetProvider;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Tests the Callback Mechanism for AppWidgets
 */
@RunWith(AndroidJUnit4.class)
public class AppWidgetCallbackTest {

    private static final String CHANGED_PROPERTY_NAME = "test.property";
    private final MyAppWidgetProvider myAppWidgetProvider = new MyAppWidgetProvider();
    //do not remove
    private final AppWidgetCoordinator appWidgetCoordinator = new AppWidgetCoordinator();

    /**
     * Tests the Callback fo a onPropertyChanged Callback
     */
    @Test
    public void onPropertyChangedCallbackTest() {
        // Context of the app under test.
        Context context = IntegrationTestUtil.getAppContext();
        MyAppWidgetProvider.subscribeOnPropertyChangedCallback(context, MyAppWidgetProvider.class);
        try {
            AppWidgetCoordinator.sendOnPropertyChanged(context,CHANGED_PROPERTY_NAME);
        } catch (PendingIntent.CanceledException e) {
            assertTrue(e.getMessage(),true);
        }
        MyAppWidgetProvider.unsubscribeOnPropertyChangedCallback(context,MyAppWidgetProvider.class);

    }

    /**
     * Tests the local Callback Mechanism if no callback is subscribed
     */
    @Test
    public void onPropertyChangedLocalTest(){
        Context context = IntegrationTestUtil.getAppContext();
        assertFalse(AppWidgetCoordinator.classHasCallbackRegistered(context, MyAppWidgetProvider.class));
        myAppWidgetProvider.onPropertyChanged(context, CHANGED_PROPERTY_NAME);
    }

    /**
     * Tries to subscribe a class which is not allowed
     */
    @Test
    public void wrongClassSubscription(){
        Context context = IntegrationTestUtil.getAppContext();
        try {
            CallbackAppWidgetProvider.subscribeOnPropertyChangedCallback(context, String.class);
        }catch (IllegalArgumentException ex) {
            assertEquals(ex.getClass(), IllegalArgumentException.class);
            return;
        }
        assertTrue("No exception thrown!",true);
    }

    /**
     *  Subclass of {@link CallbackAppWidgetProvider} for tests
     */
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
