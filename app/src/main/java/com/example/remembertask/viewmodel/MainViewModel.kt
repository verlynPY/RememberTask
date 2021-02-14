package com.example.remembertask.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.remembertask.model.Repository.TaskDataBase
import com.example.remembertask.model.Task
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {

    protected val compositeDisposable = CompositeDisposable()

    private var dataBaseInstance: TaskDataBase? = null

    var listTask = MutableLiveData<List<Task>>()

    fun setInstance(dataBaseInstance: TaskDataBase){
        this.dataBaseInstance = dataBaseInstance
    }

    fun saveTask(task: Task){

        dataBaseInstance?.taskDao()?.insertTask(task)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({

            },{

            })?.let {
                compositeDisposable.add(it)
            }
    }

    fun getTask(){

        dataBaseInstance?.taskDao()?.getAllTask()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                if(!it.isNullOrEmpty()){
                    listTask.postValue(it)
                }
                else{
                    listTask.postValue(arrayListOf())
                }
                it?.forEach{
                    Log.e(TAG, it.Title.toString())
                }
            },{

            })?.let {
                compositeDisposable.add(it)
            }

    }

}