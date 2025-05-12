package com.example.latarea.ui.activities.tasks.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.latarea.ui.theme.LaTareaTheme

@Preview
@Composable
fun TaskCard(){

    var isChecked by remember { mutableStateOf(false) }

    LaTareaTheme {
        Box(
            modifier = Modifier
                .border(2.dp,Color.Red,RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.errorContainer)
                .padding(8.dp)
                .width(480.dp)
                .height(75.dp)
        ){
            Row {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                )
                Column {
                    Text(
                        text = "Realizar codelab de móviles",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Spacer(modifier = Modifier.padding(2.dp))
                    Text(
                        text = "En este campo iría el inicio de la descripción de la actividad",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Text(
                        text = "Fecha límite: 28/03/2024",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }

}