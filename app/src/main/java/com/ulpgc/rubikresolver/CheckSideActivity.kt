package com.ulpgc.rubikresolver

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulpgc.rubikresolver.components.ColorPalette
import com.ulpgc.rubikresolver.components.FaceButtonGroup
import com.ulpgc.rubikresolver.components.IconButton


class CheckSideActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainContent() {
    var selectedColor by remember { mutableStateOf(Color.Red) }
    val faceColor = remember {
        Array(3) {
            Array(3) { mutableStateOf(Color.Gray) }
        }
    }

    //val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    Surface(color = Color(0xFF29A2FF)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(50.dp),
            modifier = Modifier.padding(16.dp)
                .fillMaxSize()
                .padding(top = screenHeight * 0.1f)
        ) {
            FaceButtonGroup(colorArray = faceColor, tileModifier = Modifier.size(screenHeight * 0.08f) ,selectedColor = selectedColor)
            ColorPalette(onColorSelected = { color ->
                selectedColor = color
            })

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(R.drawable.x, 90.dp, onClick = {})
                IconButton(R.drawable.check, 90.dp, onClick = {})
                IconButton(R.drawable.left_arrow, 90.dp, onClick = {})
            }
        }
    }
}
