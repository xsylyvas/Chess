package com.example.chess;

import android.content.Context;
import android.media.MediaPlayer;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundPlayer {

    public static void playMoveCheckSound(Context context) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.movecheck);

        if (mediaPlayer != null) {
            mediaPlayer.start();
            // Чекаємо на завершення відтворення
            mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.release());
        }
    }
}
