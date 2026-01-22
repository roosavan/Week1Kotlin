package com.example.viikkotehtava1.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.viikkotehtava1.domain.MockTasks
import com.example.viikkotehtava1.domain.Task
import com.example.viikkotehtava1.domain.addTask
import com.example.viikkotehtava1.domain.filterByDone
import com.example.viikkotehtava1.domain.sortByDueDate
import com.example.viikkotehtava1.domain.toggleDone

class TaskViewModel : ViewModel() {

    private var allTasks = mutableStateOf(listOf<Task>())

    var tasks = mutableStateOf(listOf<Task>())
        private set

    init {
        allTasks.value = MockTasks
        tasks.value = MockTasks
    }

    fun addTask(task: Task) {
        allTasks.value = addTask(allTasks.value, task)
        tasks.value = allTasks.value
    }

    fun addTask(title: String) {
        val trimmed = title.trim()
        if (trimmed.isEmpty()) return

        val nextId = (allTasks.value.maxOfOrNull { it.id } ?: 0) + 1
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

    fun toggleDone(id: Int) {
        allTasks.value = toggleDone(allTasks.value, id)
        tasks.value = allTasks.value
    }

    fun removeTask(id: Int) {
        allTasks.value = allTasks.value.filterNot { it.id == id }
        tasks.value = allTasks.value
    }

    fun filterByDone(done: Boolean) {
        tasks.value = filterByDone(allTasks.value, done)
    }

    fun sortByDueDate() {
        allTasks.value = sortByDueDate(allTasks.value)
        tasks.value = allTasks.value
    }

    fun showAll() {
        tasks.value = allTasks.value
    }
}