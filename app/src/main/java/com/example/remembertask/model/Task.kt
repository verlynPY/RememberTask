package com.example.remembertask.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.remembertask.model.Task.Companion.TABLE_NAME

@Entity (tableName = TABLE_NAME)
data class Task (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val Id: Int? = null,
    @ColumnInfo(name= TITLE)
    val Title: String?,
    @ColumnInfo(name = DESCRIPTION)
    val Description: String?,
    @ColumnInfo(name = DATA)
    val Data: String?,
    @ColumnInfo(name = HOURS)
    val Hours: String?,
    @ColumnInfo(name = MINUTES)
    val Minutes: String?,
){
    companion object{
        const val TABLE_NAME = "Task"
        const val ID = "Id"
        const val TITLE = "Title"
        const val DESCRIPTION = "Description"
        const val DATA = "Data"
        const val HOURS = "Hours"
        const val MINUTES = "Minutes"
    }
}