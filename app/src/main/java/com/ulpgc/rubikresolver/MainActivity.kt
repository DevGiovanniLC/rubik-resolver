@file:OptIn(ExperimentalMaterial3Api::class)

package com.ulpgc.rubikresolver

import android.content.Context
import android.content.Intent
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.ulpgc.rubikresolver.components.MainButton
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.opengl.renderer.HomepageRenderer
import org.opencv.android.OpenCVLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ContextProvider.init(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (OpenCVLoader.initLocal()) Log.d("OpenCV", "OpenCV loaded")
        else Log.d("OpenCV", "[ERROR] OpenCV not loaded")

        setContent {
            MainComponent()
        }

    }

    @Preview(showBackground = true)
    @Composable
    fun MainComponent() {
        //val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        Surface(color = Color(0xFF29A2FF)) {
            val solvedCube = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
            val cube = RubikCube.RubikBuilder.stringToCube(solvedCube).build()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = screenHeight * 0.05f, bottom = screenHeight * 0.05f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(screenHeight * 0.05f)
            ) {
                Title()

                OpenGLCanvas(
                    renderer = HomepageRenderer(cube),
                    modifier = Modifier
                        .weight(1f)
                )

                ButtonMenu()
            }
        }
    }

    @Composable
    fun Title() {
        Text(
            text = "Rubik Solver",
            style = TextStyle(
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 4f
                ),
                background = Color.Transparent
            )
        )
    }

    @Composable
    fun ButtonMenu() {
        val context = LocalContext.current

        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .padding(bottom = 40.dp)
                .padding(start = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainButton(
                text = "Start",
                onClick = { startActivity(Intent(context, CameraActivity::class.java)) }
            )
        }
    }

    @Composable
    fun OpenGLCanvas(renderer: HomepageRenderer, modifier: Modifier) {
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

    object ContextProvider {
        private lateinit var appContext: Context

        fun init(context: Context) {
            appContext = context.applicationContext
        }
    }
}
