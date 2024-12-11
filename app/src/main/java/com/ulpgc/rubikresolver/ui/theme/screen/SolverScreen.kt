package com.ulpgc.rubikresolver.ui.theme.screen

import android.opengl.GLSurfaceView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.opengl.renderer.SolverRenderer


@Composable
fun SolverScreen() {
    val renderer = SolverRenderer(mockCube)
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Color(0xFF29A2FF)),
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            AndroidView(
                factory = { context ->
                    GLSurfaceView(context).apply {
                        setEGLContextClientVersion(2)
                        setRenderer(renderer)
                    }
                })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Prev")
            }
            Button(
                onClick = {
                    renderer.nextMove()
                },
                modifier = Modifier.padding(8.dp)
            ) {
                Text("Next")
            }
        }
    }
}

const val stringCube = "DUUBULDBFRBFRRULLLBRDFFFBLURDBFDFDRFRULBLUFDURRBLBDUDL"
val mockCube = RubikCube.RubikBuilder
    .stringToCube(stringCube)
    .build()
