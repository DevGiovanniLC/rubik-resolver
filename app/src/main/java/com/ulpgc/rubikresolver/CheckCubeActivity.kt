package com.ulpgc.rubikresolver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulpgc.rubikresolver.components.FaceGroup
import com.ulpgc.rubikresolver.components.MainButton
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.services.arrayOfCharToColors
import com.ulpgc.rubikresolver.services.arrayOfColorToChar

class CheckCubeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainComponent()
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainComponent() {
        Surface(color = Color(0xFF29A2FF)) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                RubikCross()
                Spacer(modifier = Modifier.height(100.dp))
                MainButton(
                    text = "Solve it!",
                    onClick = { }
                )
            }
        }
    }



    @Composable
    fun RubikCross() {
        val upFace = RubikCube.RubikBuilder.getFace(RubikCube.Face.UP)
        val rightFace = RubikCube.RubikBuilder.getFace(RubikCube.Face.RIGHT)
        val frontFace = RubikCube.RubikBuilder.getFace(RubikCube.Face.FRONT)
        val downFace = RubikCube.RubikBuilder.getFace(RubikCube.Face.DOWN)
        val leftFace = RubikCube.RubikBuilder.getFace(RubikCube.Face.LEFT)
        val backFace = RubikCube.RubikBuilder.getFace(RubikCube.Face.BACK)


        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        //val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        val faceSize = screenWidth / 4
        val tileSize = faceSize / 4
        val space = faceSize*0.82f

        Box(
            modifier = Modifier
                .background(Color(0xFF29A2FF)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.wrapContentSize()
            ) {

                Row {
                    Spacer(modifier = Modifier.width(space))
                    FaceGroup(colorArray = arrayOfCharToColors(upFace), tileModifier = Modifier.size(tileSize))
                }

                // Fila central
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    FaceGroup(colorArray = arrayOfCharToColors(leftFace), tileModifier = Modifier.size(tileSize))
                    FaceGroup(colorArray = arrayOfCharToColors(frontFace), tileModifier = Modifier.size(tileSize))
                    FaceGroup(colorArray = arrayOfCharToColors(rightFace), tileModifier = Modifier.size(tileSize))
                    FaceGroup(colorArray = arrayOfCharToColors(backFace), tileModifier = Modifier.size(tileSize))
                }

                Row {
                    Spacer(modifier = Modifier.width(space))
                    FaceGroup(colorArray = arrayOfCharToColors(downFace), tileModifier = Modifier.size(tileSize))
                }
            }

        }
    }
}



