package com.example.remembertask.model.Repository

import androidx.room.*
import com.example.remembertask.model.Task
import com.example.remembertask.model.Task.Companion.TABLE_NAME
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task): Completable

    @Delete
    fun deletetask(task: Task)

    @Update
    fun updateTask(task: Task)

    @Query("SELECT * FROM $TABLE_NAME")
    fun getAllTask(): Single<List<Task>>
}