package com.ighorosipov.multitimer.feature.timer.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ighorosipov.multitimer.R

@Composable
fun ItemTimer(
    time: String,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit,
    onPlayClick: () -> Unit,
    onPauseClick: () -> Unit,
    onStopClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                onItemClick()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = time,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Button(onClick = {
            onPlayClick()
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_play), contentDescription = "play")
        }
        Button(onClick = {
            onPauseClick()
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_pause), contentDescription = "pause")
        }
        Button(onClick = {
            onStopClick()
        }) {
            Icon(painter = painterResource(id = R.drawable.ic_stop), contentDescription = "stop")
        }
    }
}