package com.example.remembertask.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.remembertask.R

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //if(intent.action == "android.intent.action.BOOT_COMPLETED"){
            var mediaPlayer = MediaPlayer.create(context, R.raw.song)
            mediaPlayer.start()
        //}

    }
}