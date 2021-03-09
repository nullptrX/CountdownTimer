/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.model.MyViewModel
import com.example.androiddevchallenge.model.Status
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography

@Composable
fun Countdown(modifier: Modifier, timeMillis: Int) {
    val model: MyViewModel = viewModel()

    LaunchedEffect(model.status) {
        when (model.status) {
            Status.Running -> {
                if (model.remainTime > 0) {
                    model.resume()
                } else {
                    model.start(timeMillis)
                }
            }
            Status.Pausing -> {
                model.pause()
            }
            Status.Stop -> {
                model.cancel()
            }
            Status.Idle -> {
            }
        }
    }

    val elapsed = model.remainTime
    val angle = if (timeMillis > 0) 360f * elapsed / timeMillis else 0f

    Box(modifier = modifier) {
        CountdownBackground(angle)
        CountdownElapsedTime(elapsed)
    }
}

@Composable
private fun BoxScope.CountdownElapsedTime(elapsed: Int) {

    val (hou, min, sec) = remember(elapsed / 1000) {
        val elapsedInSec = elapsed / 1000
        val hou = elapsedInSec / 3600
        val min = elapsedInSec / 60 - hou * 60
        val sec = elapsedInSec % 60
        Triple(hou, min, sec)
    }
    Row(
        modifier = Modifier
            .wrapContentSize()
            .align(Alignment.Center),
    ) {
        if (hou > 0) {
            CountdownUnit(
                hou.formatTime(),
            )
        }
        CountdownUnit(
            num = min.formatTime(),
        )
        CountdownUnit(
            num = sec.formatTime(),
            label = "",
        )
    }
}

@Composable
private fun BoxScope.CountdownBackground(angle: Float) {
    val ring = Stroke(width = 20.dp.value, cap = StrokeCap.Round)
    val ringColor = MyTheme.colors.progress
    val ringSecondColor = MyTheme.colors.progressSecondary
    Canvas(
        modifier = Modifier
            .align(Alignment.Center)
            .padding(16.dp)
            .size(300.dp)
    ) {
        drawArc(
            startAngle = 270f,
            sweepAngle = 360f,
            color = ringSecondColor,
            useCenter = false,
            style = ring,
        )
        if (angle > 0) {
            drawArc(
                startAngle = 270f,
                sweepAngle = angle,
                color = ringColor,
                useCenter = false,
                style = ring,
            )
        }
    }
}

@Composable
fun RowScope.CountdownUnit(
    num: String,
    label: String = ":",
    textColor: Color = MyTheme.colors.textPrimary,
    fontSize: TextUnit = 70.sp,
    labelSize: TextUnit = 50.sp,
    textAlign: TextAlign = TextAlign.Center
) {

    Text(
        text = num,
        Modifier
            .align(Alignment.CenterVertically),
        textAlign = textAlign,
        fontSize = fontSize,
        fontFamily = FontFamily.Monospace,
        color = textColor,
        style = typography.subtitle1
    )
    if (label.isNotEmpty()) {
        Text(
            label,
            Modifier
                .align(Alignment.CenterVertically),
            textAlign = textAlign,
            fontSize = labelSize,
            fontFamily = FontFamily.Serif,
            color = textColor,
            style = typography.subtitle1
        )
    }
}

private fun Int.formatTime() = String.format("%02d", this)

@Preview
@Composable
fun CountdownPreview() {
    Countdown(timeMillis = 0, modifier = Modifier.wrapContentSize())
}
