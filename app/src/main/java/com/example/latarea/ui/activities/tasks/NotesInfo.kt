package com.example.latarea.ui.activities.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.latarea.data.helper.TokenManager
import com.example.latarea.data.model.NoteResponse
import com.example.latarea.data.network.RetrofitClient
import com.example.latarea.ui.activities.tasks.home.HomePage
import com.example.latarea.ui.activities.tasks.model.NotesViewModel
import com.example.latarea.ui.activities.tasks.model.NotesViewModelFactory
import com.example.latarea.ui.theme.LaTareaTheme

@Composable
fun NoteInfo(taskId: Int, navController: NavController) {

    val noteApi = RetrofitClient.noteApi
    val factoryNote = NotesViewModelFactory(noteApi)
    val notesViewModel: NotesViewModel = viewModel(factory = factoryNote)
    val context = LocalContext.current

    var token by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        token = TokenManager.getToken(context)
        token?.let {
            notesViewModel.getNoteById(taskId, it)
        }
    }

    val note by notesViewModel.selectedNote

    HomePage(navController) {
        if (note != null) {
            NoteScreen(note, navController)
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }
}

@Composable
fun NoteScreen(note: NoteResponse?, navController: NavController) {
    LaTareaTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${note?.title}",
                    style = MaterialTheme.typography.displayMedium
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(6.dp)
            ) {
                Text(
                    text = "${note?.description}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = Int.MAX_VALUE,
                    overflow = TextOverflow.Visible
                )
            }
        }
    }
}