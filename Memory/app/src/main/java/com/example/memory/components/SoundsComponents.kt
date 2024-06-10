package com.example.memory.components

import android.media.MediaPlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.memory.R

class SoundEffects () {
    private var mediaPlayer: MediaPlayer? = null

    @Composable
    fun PlaySoundEffect(sound: Int) {
        mediaPlayer = MediaPlayer.create(LocalContext.current, sound)
        mediaPlayer?.start()
        mediaPlayer?.setVolume(0.5F, 0.5F)
    }

    @Composable
    fun EndSoundEffect() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}


