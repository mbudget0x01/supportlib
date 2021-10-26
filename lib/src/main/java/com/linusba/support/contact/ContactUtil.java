package com.linusba.support.contact;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.RequiresPermission;

/**
 * Utility Class holding various contact related functions
 */
public class ContactUtil {

    /**
     * Returns the Display Name to a Phone Number. If multiple exist, it takes the first one
     * @param phoneNumber phone Number as String
     * @param context The Context to resolve in
     * @return null if nothing was found or the display name
     */
    @RequiresPermission(Manifest.permission.READ_CONTACTS)
    public static String getContactNameByNumber(final String phoneNumber, Context context)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
        String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

        String contactName=null;
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }

        return contactName;
    }
}
