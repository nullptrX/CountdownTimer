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
package com.example.androiddevchallenge.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.model.MyViewModel
import com.example.androiddevchallenge.model.Status
import com.example.androiddevchallenge.ui.theme.darkCancel
import com.example.androiddevchallenge.ui.theme.darkGreen
import com.example.androiddevchallenge.ui.theme.darkYellow
import com.example.androiddevchallenge.ui.theme.green
import com.example.androiddevchallenge.ui.theme.white
import com.example.androiddevchallenge.ui.theme.yellow
import com.example.androiddevchallenge.ui.widget.Button
import com.example.androiddevchallenge.ui.widget.Countdown
import com.example.androiddevchallenge.ui.widget.TimePicker

@Composable
fun HomePage() {
    val model: MyViewModel = viewModel()
    Column(modifier = Modifier.fillMaxSize()) {

        Box(modifier = Modifier.size(50.dp))

        if (model.status == Status.Idle) {
            TimePicker(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 16.dp)
                    .height(320.dp)
            )
        } else {
            Countdown(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(320.dp),
                timeMillis = model.elapsedTime
            )
        }
        Row(modifier = Modifier.padding(horizontal = 16.dp)) {
            Button(
                text = "Cancel",
                textColor = white,
                backgroundColor = darkCancel,
                enable = Status.Idle != model.status,
                onClick = {
                    model.status = Status.Stop
                },
            )
            val text = when (model.status) {
                Status.Running -> "Pause"
                Status.Pausing -> "Resume"
                else -> "Start"
            }
            val color = when (model.status) {
                Status.Idle, Status.Pausing -> darkGreen
                else -> darkYellow
            }
            val textColor = when (model.status) {
                Status.Idle, Status.Pausing -> green
                else -> yellow
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                text = text,
                textColor = textColor,
                backgroundColor = color,
                enable = model.elapsedTime > 0,
                onClick = {
                    if (Status.Running == model.status) {
                        model.status = Status.Pausing
                    } else {
                        model.status = Status.Running
                    }
                },
            )
        }
    }
}
