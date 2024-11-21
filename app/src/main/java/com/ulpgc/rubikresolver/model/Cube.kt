package com.ulpgc.rubikresolver.model

import androidx.compose.ui.graphics.Color

data class Cube(
    var frontColor: Color = Color.Black,
    var backColor: Color = Color.Black,
    var leftColor: Color = Color.Black,
    var rightColor: Color = Color.Black,
    var upColor: Color = Color.Black,
    var downColor: Color = Color.Black
) {
}