@file:OptIn(ExperimentalMaterial3Api::class)

package com.ulpgc.rubikresolver

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
        var cubeState = intent.getIntExtra("cubeState", 0)
        var cubeFace = intent.getStringExtra("cubeFace")
        var faceString = stringToMutableStateArray(cubeFace)
        var faceColor = arrayOfCharColorToColor(faceString)

        enableEdgeToEdge()
        setContent {
            MainComponent(faceColor, cubeState)
        }
    }



    @Composable
    fun MainComponent(array: Array<Array<MutableState<Color>>>, cubeState: Int = 0) {
        var selectedColor by remember { mutableStateOf(Color.Red) }
        var faceColor = array

        //val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp

        Surface(color = Color(0xFF29A2FF)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(50.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .padding(top = screenHeight * 0.1f)
            ) {



                val context = LocalContext.current
                FaceText(cubeState)
                FaceButtonGroup(colorArray = faceColor, tileModifier = Modifier.size(screenHeight * 0.08f) ,selectedColor = selectedColor)
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
                        RubikCube.RubikBuilder.setFace(cubeState, removeMutableState(arrayOfColorToChar(faceColor)))
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
            0 -> text = "Up Face"
            1 -> text = "Right Face"
            2 -> text = "Front Face"
            3 -> text = "Down Face"
            4 -> text = "Left Face"
            5 -> text = "Back Face"
        }
        Text(
            text = text,
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
}

