package com.obedigital.simpletodo.ui.tasks

import androidx.lifecycle.ViewModel
import com.obedigital.simpletodo.database.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskDao: TaskDao
) : ViewModel() {
}