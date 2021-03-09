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
package com.example.androiddevchallenge.model

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    var status: Status by mutableStateOf(Status.Idle)

    val time: Time by mutableStateOf(Time())

    var elapsedTime: Int by mutableStateOf(0)

    var remainTime: Int by mutableStateOf(0)
        private set

    private var animator = Animatable(0f)

    suspend fun start(millis: Int) {
        elapsedTime = millis
        if (animator.isRunning) {
            animator.stop()
        }
        animator = Animatable(elapsedTime.toFloat())
        animator.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = elapsedTime,
                easing = LinearEasing,
            ),
            block = {
                updateRemainTime()
            }
        )
    }

    private fun updateRemainTime() {
        remainTime = animator.value.toInt()
        if (remainTime == 0) {
            status = Status.Idle
        }
    }

    suspend fun pause() {
        animator.stop()
        updateRemainTime()
    }

    suspend fun resume() {
        animator.animateTo(
            targetValue = 0f,
            animationSpec = tween(
                durationMillis = elapsedTime,
                easing = LinearEasing,
            ),
            block = {
                updateRemainTime()
            }
        )
    }

    suspend fun cancel() {
        animator.snapTo(0f)
        remainTime = 0
        status = Status.Idle
    }
}

class Time() {
    var hour: Int by mutableStateOf(0)
    var minute: Int by mutableStateOf(0)
    var second: Int by mutableStateOf(0)
    val value: Int
        get() {
            return (hour * 3600 + minute * 60 + second) * 1000
        }
}
