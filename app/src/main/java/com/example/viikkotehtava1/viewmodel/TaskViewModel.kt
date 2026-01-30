package com.example.viikkotehtava1.viewmodel

import androidx.lifecycle.ViewModel
import com.example.viikkotehtava1.model.MockTasks
import com.example.viikkotehtava1.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TaskViewModel : ViewModel() {

    private val _allTasks = MutableStateFlow<List<Task>>(emptyList())

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())

    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        _allTasks.value = MockTasks
        _tasks.value = MockTasks
    }

    fun addTask(task: Task) {
        _allTasks.value = _allTasks.value + task
        _tasks.value = _allTasks.value
    }

    fun addTask(title: String) {
        val trimmed = title.trim()
        if (trimmed.isEmpty()) return

        val nextId = (_allTasks.value.maxOfOrNull { it.id } ?: 0) + 1
        addTask(
            Task(
                id = nextId,
                title = trimmed,
                description = "",
                priority = 1,
                dueDate = "2026-01-22",
                done = false
            )
        )
    }

    fun toggleDone(taskId: Int) {
        _allTasks.value = _allTasks.value.map { task ->
            if (task.id == taskId) {
                task.copy(done = !task.done)
            } else {
                task
            }
        }
        _tasks.value = _allTasks.value
    }

    fun updateTask(taskId: Int, title: String, description: String) {
        _allTasks.value = _allTasks.value.map { task ->
            if (task.id == taskId) {
                task.copy(title = title, description = description)
            } else {
                task
            }
        }
        _tasks.value = _allTasks.value
    }

    fun removeTask(taskId: Int) {
        _allTasks.value = _allTasks.value.filter { it.id != taskId }
        _tasks.value = _allTasks.value
    }

    fun filterByDone(done: Boolean) {
        _tasks.value = _allTasks.value.filter { it.done == done }
    }

    fun sortByDueDate() {
        _allTasks.value = _allTasks.value.sortedBy { it.dueDate }
        _tasks.value = _tasks.value.sortedBy { it.dueDate }
    }

    fun showAll() {
        _tasks.value = _allTasks.value
    }
}