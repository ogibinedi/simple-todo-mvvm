package com.obedigital.simpletodo.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.obedigital.simpletodo.database.PreferencesManager
import com.obedigital.simpletodo.database.SortOrder
import com.obedigital.simpletodo.database.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    taskDao: TaskDao,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    val searchQuery = MutableStateFlow("")

    // val sortOrder = MutableStateFlow(SortOrder.BY_DATE)
    // val hideCompleted = MutableStateFlow(false)
    val preferencesFlow = preferencesManager.preferencesFlow

    private  val tasksFlow = combine(
        searchQuery,
        preferencesFlow
    ) {
        query, filterPreferences -> Pair(query, filterPreferences)
    }.flatMapLatest {
        (query, filterPreferences) ->
        taskDao.getAllTasks(query, filterPreferences.sortOrder, filterPreferences.hideCompleted)
    }

    fun onSortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }
    // val tasks = taskDao.getAllTasks("bla").asLiveData()
    val tasks = tasksFlow.asLiveData()
}
