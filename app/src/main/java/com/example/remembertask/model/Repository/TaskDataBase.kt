package com.example.remembertask.model.Repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.remembertask.model.Repository.DBCredential.DB_NAME
import com.example.remembertask.model.Repository.DBCredential.DB_VERSION
import com.example.remembertask.model.Task

@Database(entities = [Task::class], version = DB_VERSION)
abstract class TaskDataBase: RoomDatabase() {
    abstract fun taskDao(): TaskDao

    companion object{

        private var databaseinstance: TaskDataBase? = null
        fun getDataBaseIntance(context: Context): TaskDataBase =
            databaseinstance ?: synchronized(this) {
                databaseinstance ?: buildDataBaseInstance(context).also {
                    databaseinstance = it
                }
            }

        private fun buildDataBaseInstance(context: Context) =
            Room.databaseBuilder(context, TaskDataBase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()


    }
}
