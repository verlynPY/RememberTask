@file:Suppress("DEPRECATION")

package com.example.remembertask.view

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.remembertask.model.AlarmReceiver
import com.example.remembertask.model.AlarmService
import com.example.remembertask.model.Repository.TaskDataBase
import com.example.remembertask.model.Task
import com.example.remembertask.model.Utils.OpenCreateTask
import com.example.remembertask.viewmodel.MainViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*
import kotlin.collections.ArrayList


class HomeActivity : AppCompatActivity() {
    private val DarkColors = darkColors(
        primary = Color(0,220,130),
        primaryVariant = Color(180,180,180),
        secondary = Color(0,220,180)
    )
    private val LightColors = lightColors(
        primary = Color(0X0DFFAD),
        primaryVariant = Color(255,0,0),
        secondary = Color(10,10,15),
    )

    val TAG = "remembertask.view"

    lateinit var picker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var viewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //startService(intent)

        var result: ArrayList<Task>? = null
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        var databaseInstance = TaskDataBase.getDataBaseIntance(this)
        viewModel?.setInstance(databaseInstance)
        viewModel?.listTask?.observe(this, Observer {
            result = it as ArrayList<Task>
        })
        viewModel?.getTask()
        setContent {
            MaterialTheme(colors = if (isSystemInDarkTheme()) DarkColors else LightColors) {
               // Surface(color = MaterialTheme.colors.primary) {
                Column(modifier = Modifier.padding(10.dp)){
                    Switch()
                    Profile()
                    Search()
                    CardAdd(onClick = { OpenCreateTask(this@HomeActivity) })

                    LazyColumn{

                        if(result!=null){
                            itemsIndexed(items = result!!){index, result ->
                                CardTask(task = result)
                                    SendHours(result.Hours!!.toInt(), result.Minutes!!.toInt())
                                /*val calendar: Calendar = Calendar.getInstance().apply {
                                    timeInMillis = System.currentTimeMillis()
                                    set(Calendar.HOUR_OF_DAY, result.Hours!!.toInt())
                                    set(Calendar.MINUTE, (result.Minutes!!.toInt() - 2))
                                }
                                alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                                val intent = Intent(this@HomeActivity, AlarmReceiver::class.java)
                                pendingIntent = PendingIntent.getBroadcast(this@HomeActivity, 6, intent,
                                        PendingIntent.FLAG_UPDATE_CURRENT)
                                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                                        AlarmManager.INTERVAL_DAY, pendingIntent)*/

                            }
                        }
                    }


                }

                //}

            }

        }
    }

    fun SendHours(Hours: Int, Minutes: Int){
        var intent = Intent(this, AlarmService::class.java)
        var bundle = Bundle()
        bundle.putInt("Hours", Hours)
        bundle.putInt("Minutes", Minutes)
        intent.putExtras(bundle)
        startService(intent)
    }


}
