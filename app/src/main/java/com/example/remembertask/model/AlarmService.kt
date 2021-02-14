package com.example.remembertask.model

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*

class AlarmService : Service() {

    lateinit var picker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        StartAlarm(intent)
        return START_NOT_STICKY
    }

    override fun onStart(intent: Intent?, startId: Int) {

        StartAlarm(intent)

    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    fun StartAlarm(intent: Intent?){
        var bundle = intent!!.extras
        var Hours = bundle!!.getInt("Hours")
        var Minutes = bundle!!.getInt("Minutes")
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, Hours.toInt())
            set(Calendar.MINUTE, (Minutes!!.toInt() - 2))
        }

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 6, intent,
            PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent)
    }
}