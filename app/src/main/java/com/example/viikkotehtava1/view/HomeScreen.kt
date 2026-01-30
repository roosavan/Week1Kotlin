package com.example.viikkotehtava1.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viikkotehtava1.viewmodel.TaskViewModel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import com.example.viikkotehtava1.model.Task

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val vm = viewModel<TaskViewModel>()
    val tasks by vm.tasks.collectAsState()

    var newTitle by remember { mutableStateOf("") }
    var selectedTask by remember { mutableStateOf<Task?>(null) }

    Column(modifier = modifier.padding(16.dp)) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 56.dp),
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("New task") },
                singleLine = true
            )

            Button(
                modifier = Modifier.height(56.dp),
                onClick = {
                    vm.addTask(newTitle)
                    newTitle = ""
                }
            ) {
                Text("Add")
            }
        }

        Spacer(Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { vm.showAll() }
            ) { Text("Show all") }

            Button(
                modifier = Modifier.weight(1f),
                onClick = { vm.filterByDone(true) }
            ) { Text("Show done") }
        }

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = { vm.filterByDone(false) }
            ) { Text("Show not done") }

            Button(
                modifier = Modifier.weight(1f),
                onClick = { vm.sortByDueDate() }
            ) { Text("Sort by due") }
        }

        Spacer(Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks, key = { it.id }) { task ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    onClick = { selectedTask = task }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { vm.toggleDone(task.id) }
                        )
                        Text(
                            modifier = Modifier.weight(1f),
                            text = task.title
                        )
                        TextButton(onClick = { vm.removeTask(task.id) }) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }

    selectedTask?.let { task ->
        DetailDialog(
            task = task,
            onDismiss = { selectedTask = null },
            onUpdate = { title, description ->
                vm.updateTask(task.id, title, description)
                selectedTask = null
            },
            onDelete = {
                vm.removeTask(task.id)
                selectedTask = null
            }
        )
    }
}