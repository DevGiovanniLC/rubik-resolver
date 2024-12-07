package com.ulpgc.rubikresolver

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ulpgc.rubikresolver.components.RadioGroupExample
import com.ulpgc.rubikresolver.model.RubikCube


class CheckSideActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            Scaffold(modifier = Modifier.fillMaxSize()) {
                Main()
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun Main() {
    //val rubikCube = RubikCube.RubikBuilder.stringToCube("UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB").build()
   // val face =

    //SquareRadioButtonGroup()
}


@Composable
fun SquareRadioButtonGroup(colorArray: Array<Array<Color>>) {
    val options = listOf("0/0", "0/1", "0/2")
    var selectedOption by remember { mutableStateOf<String?>(null) }

    val colors = listOf(Color.Red, Color.Green, Color.Blue)

    Column(modifier = Modifier.padding(16.dp)) {
        options.forEach { option ->
            SquareRadioButton(
                isSelected = selectedOption == option,
                color = Color.Black,
                onClick = { selectedOption = option },
                label = option
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun SquareRadioButton(
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit,
    label: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(color, RoundedCornerShape(4.dp))
                .border(
                    BorderStroke(
                        width = if (isSelected) 4.dp else 2.dp,
                        color = if (isSelected) Color.White else Color.Black
                    ),
                    RoundedCornerShape(4.dp)
                )
        )
        Spacer(modifier = Modifier.width(8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSquareRadioButtonGroup() {
    //SquareRadioButtonGroup()
}