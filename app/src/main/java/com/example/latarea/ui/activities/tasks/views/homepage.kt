package com.example.latarea.ui.activities.tasks.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.latarea.ui.activities.tasks.components.TaskCard
import com.example.latarea.ui.activities.tasks.data.Option
import com.example.latarea.ui.activities.tasks.data.options
import com.example.latarea.ui.theme.LaTareaTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Preview
@Composable
fun HomePage(){
    LaTareaTheme {
        Box(modifier = Modifier.fillMaxSize()){
            Scaffold (
                topBar = {TopBar()},
                bottomBar = {BottomBar()}
            ){ innerPadding ->
                Column(modifier = Modifier.padding(innerPadding).padding(horizontal = 18.dp, vertical = 5.dp)) {
                    TaskCard()
                }
            }
        }
    }

}

@Preview
@Composable
fun BottomBar(){
    LaTareaTheme {
        LazyRow (
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(items = options) { option ->
                BottomBarItems(
                    option = option,
                )
            }
        }
    }
}


@Composable
fun BottomBarItems(option: Option){
    LaTareaTheme {
        Column(
            modifier = Modifier
                .height(50.dp)
                .width(65.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(option.imageResourceId),
                contentDescription = null,
                modifier = Modifier.height(35.dp)
            )
            Text(
                text = option.description,
                style = MaterialTheme.typography.labelSmall,
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
fun TopBar(){

    val calendario = Calendar.getInstance()
    val formato = SimpleDateFormat("EEEE d 'de' MMMM", Locale("es", "ES"))
    val fechaFormateada = formato.format(calendario.time).replaceFirstChar { it.uppercase() }

    LaTareaTheme {
        Column {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hoy",
                    style = MaterialTheme.typography.displayMedium
                )
                Text(
                    text = fechaFormateada,
                    style = MaterialTheme.typography.labelMedium
                )
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 2.dp),
                thickness = 1.dp,
                color = Color.Gray
            )
        }
    }
}