@file:OptIn(ExperimentalMaterial3Api::class)

package com.ulpgc.rubikresolver

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulpgc.rubikresolver.components.MainButton
import com.ulpgc.rubikresolver.ui.theme.RubikResolverTheme
import org.opencv.android.OpenCVLoader

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ContextProvider.init(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (OpenCVLoader.initDebug()) Log.d("OpenCV", "OpenCV loaded")
        else Log.d("OpenCV", "[ERROR] OpenCV not loaded")

        setContent {
            RubikResolverTheme {
                MainComponent()
            }
        }

    }
    @Preview(showBackground = true)
    @Composable
    fun MainComponent(){
        //val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        Surface(color = Color(0xFF29A2FF)) {
            Column(Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(screenHeight * 0.05f),
            ) {
                Spacer(modifier = Modifier.height(screenHeight * 0.25f))

                Title()

                Image(
                    painter = painterResource(id = R.drawable.cube_model),
                    contentDescription = null,
                    modifier = Modifier.size(screenHeight* 0.15f)
                )

                ButtonMenu()
            }
        }
    }



    @Composable
    fun Title(){
        Text(
            text = "Solve it!",
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

        val screenHeight = LocalConfiguration.current.screenHeightDp.dp



        val context = LocalContext.current


        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier
                .padding(bottom = 40.dp )
                .padding(start = 10.dp )
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainButton(
                text = "Start",
                onClick = { startActivity(Intent(context, CameraActivity::class.java)) }
            )
/*
            MainButton(
                text = "Check Side",
                onClick = { startActivity(Intent(context, CheckSideActivity::class.java)) }
            )

            MainButton(
                text = "Check Cube",
                onClick = { startActivity(Intent(context, CheckCubeActivity::class.java)) }
            )

            MainButton(
                text = "OpenGL",
                onClick = { startActivity(Intent(context, SolverActivity::class.java)) }
            )

            Spacer(modifier = Modifier.weight(1f))


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(R.drawable.gear, imageSize = screenHeight * 0.12f, onClick = {})
            }
*/
        }
    }

    object ContextProvider {
        lateinit var appContext: Context

        fun init(context: Context) {
            appContext = context.applicationContext
        }
    }
}
