package com.example.viikkotehtava1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viikkotehtava1.domain.MockTasks
import com.example.viikkotehtava1.domain.Task
import com.example.viikkotehtava1.domain.addTask
import com.example.viikkotehtava1.domain.filterByDone
import com.example.viikkotehtava1.domain.sortByDueDate
import com.example.viikkotehtava1.domain.toggleDone
import com.example.viikkotehtava1.ui.theme.Viikkotehtava1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Viikkotehtava1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TaskListScreen(modifier: Modifier = Modifier) {
    var tasks by remember { mutableStateOf(MockTasks) }
    var allTasks by remember { mutableStateOf(MockTasks) }
    var showingOnlyDone by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                val nextId = (allTasks.maxOfOrNull { it.id } ?: 0) + 1
                val newTask = Task(
                    id = nextId,
                    title = "New task $nextId",
                    description = "Created from UI",
                    priority = 1,
                    dueDate = "2026-01-15",
                    done = false
                )

                allTasks = addTask(allTasks, newTask)
                tasks = if (showingOnlyDone) filterByDone(allTasks, true) else allTasks
            }) {
                Text("Add task")
            }

            Button(onClick = {
                allTasks = toggleDone(allTasks, id = 2)
                tasks = if (showingOnlyDone) filterByDone(allTasks, true) else allTasks
            }) {
                Text("Toggle id=2")
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Button(onClick = {
                showingOnlyDone = true
                tasks = filterByDone(allTasks, true)
            }) {
                Text("Show done")
            }

            Button(onClick = {
                showingOnlyDone = false
                tasks = allTasks
            }) {
                Text("Show all")
            }

            Button(onClick = {
                allTasks = sortByDueDate(allTasks)
                tasks = if (showingOnlyDone) filterByDone(allTasks, true) else allTasks
            }) {
                Text("Sort by due")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "My Tasks",
        )
        Spacer(modifier = Modifier.height(12.dp))

        tasks.forEach { task ->
            Text(text = "${task.id} - ${task.title} (done=${task.done}, due=${task.dueDate})")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskListPreview() {
    Viikkotehtava1Theme {
        TaskListScreen()
    }
}