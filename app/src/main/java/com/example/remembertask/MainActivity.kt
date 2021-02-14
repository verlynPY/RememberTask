package com.example.remembertask

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProviders
import com.example.remembertask.model.Repository.TaskDataBase
import com.example.remembertask.model.Task
import com.example.remembertask.model.Utils
import com.example.remembertask.view.TimeTask
import com.example.remembertask.viewmodel.MainViewModel
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private val DarkColors = darkColors(
        primary = Color(0,220,130),
        secondary = Color(0,0,255)
    )
    private val LightColors = lightColors(
        primary = Color(0X0DFFAD),
        primaryVariant = Color(255,0,0),
        secondary = Color(0,0,255),
    )

    val TAG = "remembertask"
    private var viewModel: MainViewModel? = null
    var Hora: String? = "00"
    var Minute: String? = "00"
    var AM_PM: String? = "PM"

    lateinit var picker: MaterialTimePicker
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        var dataBaseIntance = TaskDataBase.getDataBaseIntance(this)
        viewModel?.setInstance(dataBaseIntance)
        viewModel?.listTask?.observe(this, androidx.lifecycle.Observer {
           Toast.makeText(applicationContext, "$it", Toast.LENGTH_SHORT).show()
        })
        viewModel?.getTask()
        setContent {
            MaterialTheme(colors = if (isSystemInDarkTheme()) DarkColors else LightColors) {
                Column(
                    modifier = Modifier.absolutePadding(
                        left = 30.dp,
                        top = 40.dp,
                        right = 30.dp
                    )
                ) {
                    var title = remember { mutableStateOf("") }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Title", fontSize = 25.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.absolutePadding(top = 20.dp, bottom = 5.dp)
                        )
                        TextField(
                            value = title.value,
                            onValueChange = { title.value = it },
                            placeholder = {
                                Text(
                                    "Title", fontSize = 14.sp, fontWeight = FontWeight.SemiBold
                                )
                            },
                            textStyle = MaterialTheme.typography.subtitle2,
                            backgroundColor = Color(0XFF17282C),
                            modifier = Modifier
                                .preferredHeight(22.dp)
                                .fillMaxWidth(),
                            activeColor = Color(0X0DFFAD),
                        )
                    }
                    var time = remember { mutableStateOf("") }
                    var date = remember { mutableStateOf("") }
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Hours", fontSize = 25.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.absolutePadding(top = 20.dp, bottom = 5.dp)
                        )
                        Box(
                            modifier = Modifier
                                .preferredHeight(50.dp)
                                .preferredWidth(120.dp)
                                .background(Color(0XFF17282C))
                                .clickable(onClick = { ShowTimePicker() })
                        ) {
                            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                                    Text(text = "$Hora : ", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                                    Text(text = "$Minute ", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                                    Text(text = "AM", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                                }
                            }

                        }

                    }
                    var description = remember { mutableStateOf("") }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                    ) {
                        Text(
                            text = "Description", fontSize = 25.sp, fontWeight = FontWeight.Bold,
                            modifier = Modifier.absolutePadding(top = 20.dp, bottom = 5.dp)
                        )
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .preferredHeight(22.dp),
                            value = description.value,
                            onValueChange = { description.value = it },
                            backgroundColor = Color(0XFF17282C),
                            placeholder = {
                                Text(
                                    "Description",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            },
                            maxLines = Int.MAX_VALUE
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier
                            .fillMaxHeight()
                            .absolutePadding(bottom = 20.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            FloatingActionButton(onClick = {
                                SaveData(
                                    title.value.toString(),
                                    time.value.toString(),
                                    date.value,
                                    description.value.toString()
                                )
                            }) {
                                Icon(vectorResource(id = R.drawable.save))
                            }
                        }

                    }
                }

            }
        }
    }

    fun ShowTimePicker(){
        picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(10)
                .setTitleText("Select Appointment time")
                .build()
        picker.show(supportFragmentManager, "TAG")
        picker.addOnPositiveButtonClickListener {
            Hora = picker.hour.toString()
            Minute = picker.minute.toString()

            Toast.makeText(applicationContext, "$Hora", Toast.LENGTH_SHORT).show()

          /*  val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = System.currentTimeMillis()
                set(Calendar.HOUR_OF_DAY, 10)
                set(Calendar.MINUTE, 59)
            }
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, AlarmReceiver::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT)
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent)*/
        }

    }

    fun SaveData(Title: String, Hours: String, Data: String, Description: String){
        var task = Task(Title = Title,Hours = Hora,Data = Data,Description = Description, Minutes = Minute)
        viewModel?.saveTask(task)
        finish()
    }

}
