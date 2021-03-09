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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.model.MyViewModel
import com.example.androiddevchallenge.model.Status
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.white

@Composable
fun TimePicker(modifier: Modifier) {
    val model: MyViewModel = viewModel()
    val time = model.time
    val enabled = Status.Idle == model.status

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(70.dp)
                    .shadow(30.dp, shape = CircleShape),
                imageVector = Icons.Default.Timer,
                contentDescription = "Time Picker",
                colorFilter = ColorFilter.tint(white)
            )
        }
        LabelSlider(
            modifier = Modifier.padding(top = 16.dp),
            label = "H",
            value = time.hour,
            enabled = enabled,
            range = 0f..24f,
        ) {
            time.hour = it.toInt()
            model.elapsedTime = time.value
        }

        LabelSlider(
            modifier = Modifier.padding(top = 16.dp),
            label = "M",
            value = time.minute,
            enabled = enabled,
            range = 0f..60f
        ) {
            time.minute = it.toInt()
            model.elapsedTime = time.value
        }

        LabelSlider(
            modifier = Modifier.padding(top = 16.dp),
            label = "S",
            value = time.second,
            enabled = enabled,
            range = 0f..60f
        ) {
            time.second = it.toInt()
            model.elapsedTime = time.value
        }
    }
}

@Composable
private fun LabelSlider(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: String,
    value: Int,
    range: ClosedFloatingPointRange<Float>,
    onSliderChange: (Float) -> Unit
) {
    BoxWithConstraints {
        Row(modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                label,
                style = MaterialTheme.typography.h6,
                fontSize = 16.sp,
                color = MyTheme.colors.textPrimary,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Slider(
                value = value.toFloat(),
                enabled = enabled,
                onValueChange = onSliderChange,
                valueRange = range,
                steps = range.endInclusive.toInt(),
                modifier = Modifier.weight(1f),
                colors = SliderDefaults.colors(
                    activeTickColor = Color.Unspecified,
                    inactiveTickColor = Color.Unspecified,
                    activeTrackColor = white,
                    thumbColor = white,
                )
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                Modifier
                    .width(30.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    value.toString(),
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.h6,
                    color = MyTheme.colors.textPrimary,
                )
            }
        }
    }
}
