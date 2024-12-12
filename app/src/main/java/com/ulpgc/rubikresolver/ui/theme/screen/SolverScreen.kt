package com.ulpgc.rubikresolver.ui.theme.screen

import android.content.Intent
import android.opengl.GLSurfaceView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.ulpgc.rubikresolver.MainActivity
import com.ulpgc.rubikresolver.R
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.opengl.renderer.SolverRenderer
import kotlin.math.max
import kotlin.math.min


@Preview
@Composable
fun PreviewSolverScreen() {
    val solvedCube: String = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
    val cube = RubikCube.RubikBuilder.stringToCube(solvedCube).build()
    SolverScreen(cube)
}

@Composable
fun SolverScreen(cube: RubikCube) {
    val renderer = SolverRenderer(cube)
    val currentSolutionIndex: MutableIntState = remember { renderer.currentMoveIndex }
    val solutionMoves: List<String> = remember { renderer.solutionMoves }

    Surface(
        color = Color(0xFF29A2FF),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, bottom = 64.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Header()
            DynamicSolutionMoves(
                solutionMoves = solutionMoves,
                currentSolutionIndex = currentSolutionIndex
            )
            OpenGLCanvas(renderer, modifier = Modifier.weight(1f))
            ActionBar(renderer)
        }
    }
}

@Composable
fun Header() {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            ActionButton(
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    context.startActivity(intent)
                },
                icon = R.drawable.home,
                description = "cube"
            )
        }
        Box(
            modifier = Modifier.weight(2f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Solution",
                fontSize = 32.sp,
                color = Color.White,
                fontWeight = Bold
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun ActionBar(renderer: SolverRenderer) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ActionButton(
            onClick = { renderer.firstMove() },
            icon = R.drawable.arrow_collapse_left,
            description = "first move"
        )
        ActionButton(
            onClick = { renderer.previousMove() },
            icon = R.drawable.arrow_left,
            description = "previous move"
        )
        ActionButton(
            onClick = { renderer.toggleAnimation() },
            icon = R.drawable.play_pause,
            description = "play/pause"
        )
        ActionButton(
            onClick = { renderer.nextMove() },
            icon = R.drawable.arrow_right,
            description = "next move"
        )
        ActionButton(
            onClick = { renderer.lastMove() },
            icon = R.drawable.arrow_collapse_right,
            description = "last move"
        )
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
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = description
        )
    }
}

@Composable
fun DynamicSolutionMoves(
    solutionMoves: List<String>,
    currentSolutionIndex: MutableIntState
) {
    var currentIndex = currentSolutionIndex.intValue
    // Fixed range of 5 moves
    currentIndex = max(currentIndex, 0)
    currentIndex = min(currentIndex, solutionMoves.size - 1)

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in currentIndex - 2 until currentIndex + 3) {
            var text = "  "
            var color = Color.Black

            if (i >= 0 && i < solutionMoves.size) {
                text = solutionMoves[i]
                if (text.length == 1) {
                    text += " "
                }
            }
            if (i == currentIndex) {
                color = Color.White
            }
            Text(
                text = text,
                modifier = Modifier.padding(8.dp),
                color = color,
                fontSize = 32.sp,
                fontWeight = Bold,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Composable
fun OpenGLCanvas(renderer: SolverRenderer, modifier: Modifier) {
    Box(
        modifier = modifier
    ) {
        AndroidView(
            factory = { context ->
                GLSurfaceView(context).apply {
                    setEGLContextClientVersion(2)
                    setRenderer(renderer)
                }
            })
    }
}

