package com.ulpgc.rubikresolver.ui.theme.screen

import android.opengl.GLSurfaceView
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.ulpgc.rubikresolver.R
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.opengl.renderer.SolverRenderer


@Composable
fun SolverScreen() {
    val renderer = SolverRenderer(mockCube)
    Column(
        modifier = Modifier
            .fillMaxSize()
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
                .height(100.dp)
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            ActionButton(
                onClick = { renderer.previousMove() },
                icon = R.drawable.arrow_left,
                description = "previous move"
            )
            ActionButton(
                onClick = {
                    renderer.toggleAnimation()
                },
                icon = R.drawable.play_pause,
                description = "play/pause"
            )
            ActionButton(
                onClick = { renderer.nextMove() },
                icon = R.drawable.arrow_right,
                description = "next move"
            )
        }
    }
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
    icon: Int,
    description: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier.padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = description
        )
    }
}

const val stringCube = "DUUBULDBFRBFRRULLLBRDFFFBLURDBFDFDRFRULBLUFDURRBLBDUDL"
val mockCube = RubikCube.RubikBuilder
    .stringToCube(stringCube)
    .build()
