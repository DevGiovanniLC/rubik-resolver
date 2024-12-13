@file:OptIn(ExperimentalMaterial3Api::class)

package com.ulpgc.rubikresolver

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulpgc.rubikresolver.components.ColorPalette
import com.ulpgc.rubikresolver.components.FaceButtonGroup
import com.ulpgc.rubikresolver.components.IconButton
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.services.arrayOfCharColorToColor
import com.ulpgc.rubikresolver.services.arrayOfColorToChar
import com.ulpgc.rubikresolver.services.removeMutableState
import com.ulpgc.rubikresolver.services.stringToMutableStateArray


class CheckSideActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cubeState = intent.getIntExtra("cubeState", 0)
        val cubeFace = intent.getStringExtra("cubeFace")
        val faceString = stringToMutableStateArray(cubeFace)
        val faceColor = arrayOfCharColorToColor(faceString)
        enableEdgeToEdge()
        setContent {
            MainComponent(faceColor, cubeState)
        }
    }


    @Composable
    fun MainComponent(array: Array<Array<MutableState<Color>>>, cubeState: Int = 0) {
        var selectedColor by remember { mutableStateOf(Color.Red) }

        //val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        Surface(color = Color(0xFF29A2FF)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(50.dp),
                modifier = Modifier
                    .padding(top = screenHeight * 0.1f)
                    .fillMaxSize()
            ) {

                val context = LocalContext.current
                FaceText(cubeState)
                FaceButtonGroup(
                    colorArray = array,
                    tileModifier = Modifier.size(screenHeight * 0.08f),
                    selectedColor = selectedColor
                )
                ColorPalette(onColorSelected = { color ->
                    selectedColor = color
                })

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(R.drawable.x, 90.dp, onClick = {
                        RubikCube.RubikBuilder.reset()
                        startActivity(
                            Intent(context, MainActivity::class.java)
                        )
                    })
                    IconButton(R.drawable.check, 90.dp, onClick = {
                        RubikCube.RubikBuilder.setFace(cubeState, removeMutableState(
                            arrayOfColorToChar(array)))
                        if (cubeState == RubikCube.Face.BACK.value) {
                            startActivity(
                                Intent(context, CheckCubeActivity::class.java)
                            )
                        }else{
                            startActivity(
                                Intent(context, CameraActivity::class.java)
                                    .putExtra("cubeState", cubeState+1)
                            )
                        }
                    })

                    IconButton(R.drawable.left_arrow, 90.dp, onClick = {
                        startActivity(
                            Intent(context, CameraActivity::class.java)
                                .putExtra("cubeState", cubeState)
                        )
                    })
                }
            }
        }
    }

    @Composable
    fun FaceText(cubeState : Int){
        var text = ""
        when(cubeState){
            0 -> text = "Yellow Face\n(Red bottom)"
            1 -> text = "Green Face\n(White bottom)"
            2 -> text = "Red Face\n(White bottom)"
            3 -> text = "White Face\n(Orange bottom)"
            4 -> text = "Blue Face\n(White bottom)"
            5 -> text = "Orange Face\n(White bottom)"
        }
        Text(
            text = text,
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
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
}

