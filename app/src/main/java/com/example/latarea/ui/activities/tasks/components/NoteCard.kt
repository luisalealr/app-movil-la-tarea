package com.example.latarea.ui.activities.tasks.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.latarea.ui.theme.LaTareaTheme
import androidx.compose.ui.graphics.Color
import com.example.latarea.data.model.NoteResponse

@Composable
fun NotesCard(note: NoteResponse, onChecked: () -> Unit, onClick: () -> Unit) {

    LaTareaTheme {
        Box(
            modifier = Modifier
                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(8.dp)
                .fillMaxWidth(0.45f)
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
                        text = note.title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}