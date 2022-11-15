package com.obedigital.simpletodo.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.obedigital.simpletodo.database.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    taskDao: TaskDao
) : ViewModel() {
    val searchQuery = MutableStateFlow("")
    private  val tasksFlow = searchQuery.flatMapLatest {
        taskDao.getAllTasks(it)
    }
//    val tasks = taskDao.getAllTasks("bla").asLiveData()
    val tasks = tasksFlow.asLiveData()
}