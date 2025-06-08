package com.example.latarea.ui.activities.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.latarea.data.helper.TokenManager
import com.example.latarea.data.model.SubjectResponse
import com.example.latarea.data.model.TaskResponse
import com.example.latarea.data.network.RetrofitClient
import com.example.latarea.ui.activities.schedule.createSubject.model.SubjectViewModel
import com.example.latarea.ui.activities.schedule.createSubject.model.SubjectViewModelFactory
import com.example.latarea.ui.activities.tasks.home.HomePage
import com.example.latarea.ui.activities.tasks.model.TaskViewModel
import com.example.latarea.ui.activities.tasks.model.TaskViewModelFactory
import com.example.latarea.ui.activities.tasks.utils.ParseColor
import com.example.latarea.ui.theme.LaTareaTheme

val defaultSubject = SubjectResponse(
    id = -1,
    title = "Sin materia",
    colorHexa = "CCCCCC",
    userId = "user3442",
    scheduleBlockId = -1
)


@Composable
fun TaskInfo(taskId: Int, navController: NavController) {

    val taskApi = RetrofitClient.taskApi
    val factoryTask = TaskViewModelFactory(taskApi)
    val tasksViewModel: TaskViewModel = viewModel(factory = factoryTask)

    val subjectApi = RetrofitClient.subjectApi
    val factorySubject = SubjectViewModelFactory(subjectApi)
    val subjectViewModel: SubjectViewModel = viewModel(factory = factorySubject)
    val context = LocalContext.current

    var token by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        token = TokenManager.getToken(context)
        token?.let {
            tasksViewModel.getTaskById(taskId, it)
        }
    }

    val task by tasksViewModel.selectedTask

    LaunchedEffect(true) {
        token?.let {
            token = TokenManager.getToken(context)
            subjectViewModel.getSubjectById(task?.subjectId, it)
        }
    }

    val subject by subjectViewModel.selectedSubject

    HomePage(navController) {
        if (task != null) {
            if(subject == null){
                TaskScreen(task, defaultSubject, navController)
            }else{
                TaskScreen(task, subject, navController)
            }
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
fun TaskScreen(task: TaskResponse?, subject: SubjectResponse?, navController: NavController) {
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
                    text = "${task?.title}",
                    style = MaterialTheme.typography.displayMedium
                )
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar tarea",
                    modifier = Modifier.clickable {
                        navController.navigate("taskUpdate/${task?.id}")
                    }
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
                    text = "${task?.description}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = Int.MAX_VALUE,
                    overflow = TextOverflow.Visible
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(6.dp)
            ) {
                Text(
                    text = "${subject?.title}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(ParseColor(task?.colorHexa ?: "D3D3D3"))
                        .border(
                            width = 1.dp,
                            color = Color.DarkGray,
                            shape = CircleShape
                        )
                )
                Text(
                    text = "Esta tarea vence el: "
                )
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${task?.expiresAt}",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }

        }

    }

}