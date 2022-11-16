package com.obedigital.simpletodo.ui.form

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.obedigital.simpletodo.database.Task
import com.obedigital.simpletodo.database.TaskDao

class TaskFormViewModel @ViewModelInject constructor(
    private val taskDao: TaskDao,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {
    val task = state.get<Task>("task")

    var taskName = state.get<String>("taskName") ?: task?.name ?: ""
        set(value) {
            field = value
            state.set("taskName", value)
        }

    var taskImportant = state.get<Boolean>("taskImportant") ?: task?.important ?: false
        set(value) {
            field = value
            state.set("taskImportant", value)
        }
}