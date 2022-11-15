package com.obedigital.simpletodo.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.obedigital.simpletodo.database.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    taskDao: TaskDao
) : ViewModel() {
    val tasks = taskDao.getAllTasks().asLiveData()
}