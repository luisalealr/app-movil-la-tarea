package com.example.latarea.ui.activities.tasks.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.latarea.ui.theme.LaTareaTheme
import androidx.compose.ui.graphics.Color
import com.example.latarea.data.model.NoteResponse

@Composable
fun NotesCard(note: NoteResponse) {

    var isChecked by remember { mutableStateOf(false) }

    LaTareaTheme {
        Box(
            modifier = Modifier
                .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(8.dp)
                .fillMaxWidth(0.45f)
                .height(75.dp)
        ) {
            Row {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
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