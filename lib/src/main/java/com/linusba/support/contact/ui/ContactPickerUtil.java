package com.linusba.support.contact.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Utility to easy access a Contact Picker from the UI
 */
public class ContactPickerUtil {


    /**
     * Parses the {@link ContactResponse} from the given Intent
     * @param data the intent to look in
     * @param context Context of the Application
     * @return Instance of {@link ContactResponse} or null if none choosen or error
     */
    static ContactResponse parseResponse(@Nullable Intent data, Context context) {
        ContactResponse pickedContact = null;

        if (data == null) {
            return null;
        }
        Uri uri = data.getData();
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            pickedContact = new ContactResponse(cursor.getString(nameIndex), cursor.getString(phoneIndex));
        }
        cursor.close();
        return pickedContact;
    }





    /*---------------------------------------------------------------------------------------------

        Legacy stuff
        TODO: delete in next major version

     */


    /**
     * Displays a ContactPicker for Activity Result
     *
     * @param activity    activity to handle the result
     * @param requestCode requestCode for the result
     */
    @Deprecated
    public static void displayContactPicker(Activity activity, int requestCode) {
        activity.startActivityForResult(getIntent(), requestCode);
    }

    /**
     * Displays a ContactPicker for Activity Result
     *
     * @param fragment    fragment to handle the result
     * @param requestCode requestCode for the result
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static void displayContactPicker(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getIntent(), requestCode);
    }

    static Intent getIntent() {
        return new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
    }

    /**
     * Handles a ContactPicker response
     *
     * @param data    Intent from Activity Result
     * @param context for reference
     * @return null if not successful else Instance of Picked Contact
     */
    @Deprecated
    @Nullable
    public static ContactResponse handleContactPickerResponse(@Nullable Intent data, Context context) {
        return parseResponse(data, context);
    }
}
