package com.denofdevelopers.volumecontrol.util;

import android.content.Context;
import android.media.AudioManager;

import com.denofdevelopers.volumecontrol.app.App;

public class AudioControlHelper {

    private static AudioManager audioManager = (AudioManager) App.get().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

    private AudioControlHelper() {
    }

    public static int getCurrentVolume() {
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_RING);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        return 100 * currentVolume / maxVolume;
    }

    public static void setVolume(int percentage) {
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        int per = (int) Math.round(percentage / 100.0 * maxVolume);
        audioManager.setStreamVolume(AudioManager.STREAM_RING, per, 0);
    }

    public static void increaseVolume() {
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
    }

    public static void decreaseVolume() {
        audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
    }
}
