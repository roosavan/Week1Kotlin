package com.example.viikkotehtava1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.viikkotehtava1.local.AppDatabase
import com.example.viikkotehtava1.model.TaskEntity
import com.example.viikkotehtava1.repository.TaskRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TaskRepository

    private val _allTasks: Flow<List<TaskEntity>>

    private val _tasks = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasks: StateFlow<List<TaskEntity>> = _tasks.asStateFlow()

    private var currentFilter: FilterType = FilterType.ALL

    init {
        val taskDao = AppDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        _allTasks = repository.allTasks

        viewModelScope.launch {
            _allTasks.collect { taskList ->
                applyFilter(currentFilter, taskList)
            }
        }
    }

    fun addTask(task: TaskEntity) {
        viewModelScope.launch {
            repository.insert(task)
        }
    }

    fun toggleDone(taskId: Int) {
        viewModelScope.launch {
            val currentTasks = _tasks.value
            val task = currentTasks.find { it.id == taskId }
            task?.let {
                repository.update(it.copy(done = !it.done))
            }
        }
    }

    fun updateTask(taskId: Int, title: String, description: String) {
        viewModelScope.launch {
            val currentTasks = _tasks.value
            val task = currentTasks.find { it.id == taskId }
            task?.let {
                repository.update(
                    it.copy(
                        title = title,
                        description = description
                    )
                )
            }
        }
    }

    fun removeTask(taskId: Int) {
        viewModelScope.launch {
            repository.deleteById(taskId)
        }
    }

    fun filterByDone(done: Boolean) {
        currentFilter = if (done) FilterType.DONE else FilterType.NOT_DONE
        viewModelScope.launch {
            _allTasks.collect { taskList ->
                applyFilter(currentFilter, taskList)
            }
        }
    }

    fun sortByDueDate() {
        viewModelScope.launch {
            val sorted = _tasks.value.sortedBy { it.dueDate }
            _tasks.value = sorted
        }
    }

    fun showAll() {
        currentFilter = FilterType.ALL
        viewModelScope.launch {
            _allTasks.collect { taskList ->
                applyFilter(FilterType.ALL, taskList)
            }
        }
    }

    private fun applyFilter(filter: FilterType, taskList: List<TaskEntity>) {
        _tasks.value = when (filter) {
            FilterType.ALL -> taskList
            FilterType.DONE -> taskList.filter { it.done }
            FilterType.NOT_DONE -> taskList.filter { !it.done }
        }
    }

    private enum class FilterType {
        ALL, DONE, NOT_DONE
    }
}