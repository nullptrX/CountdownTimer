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
package com.example.androiddevchallenge.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

private val LightColorPalette = MyColors(
    background = white,
    textPrimary = black,
    textSecondary = Color.DarkGray,
    progress = yellow,
    progressSecondary = darkBlack,
)

private val DarkColorPalette = MyColors(
    background = black,
    textPrimary = white,
    textSecondary = Color.LightGray,
    progress = yellow,
    progressSecondary = darkBlack,
)

private val LocalColorsProvider = compositionLocalOf {
    LightColorPalette
}

@Stable
object MyTheme {
    val colors: MyColors
        @Composable
        get() = LocalColorsProvider.current
}

@Composable
fun MyTheme(darkTheme: Boolean = true, content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    CompositionLocalProvider(LocalColorsProvider provides colors) {
        MaterialTheme(
            typography = typography,
            shapes = shapes,
            content = content,
        )
    }
}

@Stable
class MyColors(
    background: Color,
    progress: Color,
    progressSecondary: Color,
    textPrimary: Color,
    textSecondary: Color,
) {
    var progress: Color by mutableStateOf(progress)
        private set
    var progressSecondary: Color by mutableStateOf(progressSecondary)
        private set
    var background: Color by mutableStateOf(background)
        private set
    var textPrimary: Color by mutableStateOf(textPrimary)
        private set
    var textSecondary: Color by mutableStateOf(textSecondary)
        private set
}
