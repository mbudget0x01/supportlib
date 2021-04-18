package com.linusba.support.ringtone.ringtoneprovider;

import android.net.Uri;

public interface IRingtoneProvider {
    /**
     * Starts playing a ringtone in a loop
     * @param ringtone uri to the ringtone file
     */
    void startRingtone(Uri ringtone);

    /**
     * Makes this class stop to play the ringtone
     */
    void stopRingtone();

    /**
     * Indicates if the ringtone is playing
     * @return true if is playing
     */
    boolean isPlaying();
}
