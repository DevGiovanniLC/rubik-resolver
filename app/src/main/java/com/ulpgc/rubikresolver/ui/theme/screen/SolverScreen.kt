package com.ulpgc.rubikresolver.ui.theme.screen

import android.opengl.GLSurfaceView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.ulpgc.rubikresolver.opengl.renderer.SolverRenderer


@Composable
fun SolverScreen() {
    AndroidView(
        factory = { context ->
            GLSurfaceView(context).apply {
                setEGLContextClientVersion(2)
                setRenderer(SolverRenderer())
            }
        })
}
