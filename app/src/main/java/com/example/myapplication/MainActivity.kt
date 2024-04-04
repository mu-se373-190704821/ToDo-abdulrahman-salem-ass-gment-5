package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TaskList()
                }
            }
        }
    }
}

@Composable
fun TaskList() {
    var tasks by remember { mutableStateOf(listOf("Android")) }
    Column {
        Row(
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically // Aligns items vertically in the center
        ) {
            var newTask by remember { mutableStateOf("") }
            TextField(
                value = newTask,
                onValueChange = { newTask = it },
                label = { Text("Enter a task") }
            )
            Button(
                onClick = {
                    if (newTask.isNotEmpty()) {
                        tasks = tasks.toMutableList().apply { add(newTask) }
                        newTask = ""
                    }
                },
                modifier = Modifier.padding(start = 1.dp) // Adds padding to the button
            ) {
                Text("ADD")
            }
        }
        LazyColumn {
            items(tasks.size) { index ->
                Task(
                    name = tasks[index],
                    onDeleteClick = {
                        tasks = tasks.toMutableList().apply { removeAt(index) }
                    }
                )
            }
        }
    }
}

@Composable
fun Task(name: String, modifier: Modifier = Modifier, onDeleteClick: () -> Unit) {
    val checkedState = remember { mutableStateOf(false) }

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = { checkedState.value = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = name,
                modifier = Modifier.padding(8.dp)
                    .alpha(if (checkedState.value) 0.5f else 1.0f) // Adjust opacity based on checked state
            )
        }
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TaskPreview() {
    MyApplicationTheme {
        TaskList()
    }
}
