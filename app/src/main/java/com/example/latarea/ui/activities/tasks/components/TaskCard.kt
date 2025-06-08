package com.example.latarea.ui.activities.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.latarea.data.model.TaskResponse
import com.example.latarea.ui.activities.tasks.utils.ParseColor
import com.example.latarea.ui.theme.LaTareaTheme

@Composable
fun TaskCard(task: TaskResponse, onChecked: () -> Unit, onClick: () -> Unit) {
    LaTareaTheme {
        Box(
            modifier = Modifier
                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(ParseColor(task.colorHexa.toString()))
                .padding(8.dp)
                .width(480.dp)
                .height(75.dp)
                .clickable { onClick() }
        ) {
            Row {
                Checkbox(
                    checked = false,
                    onCheckedChange = { isChecked ->
                        if (isChecked) {
                            onChecked() // eliminamos directamente
                        }
                    }
                )
                Column {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = task.description,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = "Fecha límite: " + task.expiresAt,
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
