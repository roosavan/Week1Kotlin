package com.example.viikkotehtava1.repository

import com.example.viikkotehtava1.local.TaskDao
import com.example.viikkotehtava1.model.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun insert(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: TaskEntity) {
        taskDao.deleteTask(task)
    }

    suspend fun deleteById(taskId: Int) {
        taskDao.deleteTaskById(taskId)
    }

    fun getTasksByDoneStatus(isDone: Boolean): Flow<List<TaskEntity>> {
        return taskDao.getTasksByDoneStatus(isDone)
    }
}