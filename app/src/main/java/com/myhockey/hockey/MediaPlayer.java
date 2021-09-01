package com.myhockey.hockey;

import android.content.Context;

public class MediaPlayer {

    public android.media.MediaPlayer mySound = null;
    public android.media.MediaPlayer mySoundSFX = null;
    public android.media.MediaPlayer mySoundEndHorn = null;

    public void play(Context context, int music) {

        if (mySound == null) {
            mySound = android.media.MediaPlayer.create(context.getApplicationContext(), music);
            mySound.setLooping(true);
            mySound.start();
        }
    }

    public void playSFX(Context context, int music) {

        if (mySoundSFX == null) {
            mySoundSFX = android.media.MediaPlayer.create(context.getApplicationContext(), music);
            mySoundSFX.start();
        }
    }

    public void playEndHorn(Context context, int music) {

        if (mySoundEndHorn == null) {
            mySoundEndHorn = android.media.MediaPlayer.create(context.getApplicationContext(), music);
            mySoundEndHorn.start();
        }
    }

}
