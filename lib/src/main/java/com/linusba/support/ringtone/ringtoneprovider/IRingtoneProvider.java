package com.linusba.support.ringtone.ringtoneprovider;

import android.net.Uri;

public interface IRingtoneProvider {
    void startRingtone(Uri ringtone);
    void stopRingtone();
    boolean isPlaying();
}
