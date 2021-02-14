package com.example.remembertask.model

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.remembertask.MainActivity
import com.example.remembertask.view.HomeActivity

object Utils {

    fun SetData(context: Context,title: String, hours: String, description: String){
        Toast.makeText(context, "$title $hours $description", Toast.LENGTH_SHORT).show()
    }

    fun OpenCreateTask(context: Context){
            val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)

    }

}