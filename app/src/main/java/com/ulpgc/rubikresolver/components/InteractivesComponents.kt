package com.ulpgc.rubikresolver.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulpgc.rubikresolver.R
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.services.arrayOfCharToColors

@Preview
@Composable
fun MockComponentsPreview(){
    Surface(color = Color(0xFF29A2FF)) {
        Column(
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = Modifier.padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainButton(
                text = "mock",
                onClick = { }
            )

            IconButton(R.drawable.gear, 90.dp, onClick = {})
        }
    }

}


@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
) {
    val buttonShape = RoundedCornerShape(10.dp)


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(width = 120.dp, height = 50.dp)
            .border(width = 2.dp, color = Color.Black, shape = buttonShape)
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = buttonShape,
            modifier = Modifier.size(width = 120.dp, height = 50.dp)
        ) {
            Text(text = text, color = Color.Black, fontSize = 14.sp)
        }
    }
}

@Composable
fun IconButton(image:Int = R.drawable.gear, imageSize: Dp = 70.dp,onClick: () -> Unit) {
    val buttonShape = CircleShape

    Box(
        modifier = Modifier
            .size(imageSize)
            .border(width = 2.dp, color = Color.Black, shape = buttonShape) // Aplica borde circular
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = buttonShape,
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
            )
        }
    }
}

@Composable
fun ColorPalette(
    onColorSelected: (Color) -> Unit
) {
    var selectedOption by remember { mutableStateOf(Color.Red) }

    val colors = listOf(
        listOf(
            Color.Red, Color.Green, Color.Blue,
        ),
        listOf(
            Color.Yellow, Color(0xFFFF9800), Color.White
        )
    )

    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(Color.Gray, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Column() {
            colors.forEach { row ->
                Row(modifier = Modifier.padding(10.dp)) {
                    row.forEach { color ->
                        val optionId = color
                        TileButton(
                            isSelected = selectedOption == optionId,
                            color = color,
                            onClick = {
                                selectedOption = color
                                onColorSelected(color)
                            }
                        )

                        Spacer(modifier = Modifier.width(10.dp))
                    }
                }
            }
        }
    }
}



@Composable
fun FaceButtonGroup(
    colorArray: Array<Array<MutableState<Color>>>,
    selectedColor: Color
) {

    Column(modifier = Modifier.padding(16.dp)) {
        colorArray.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { columnIndex, cell ->
                    val optionId = "$rowIndex/$columnIndex"

                    TileButton(
                        isSelected = true,
                        color = cell.value,
                        onClick = {
                            cell.value = selectedColor
                        }
                    )

                    Spacer(modifier = Modifier.width(1.dp))
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}

@Composable
fun TileButton(
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(65.dp)
            .border(2.dp, if (isSelected) Color.Black else Color.Gray)
            .background(color)
            .clickable(onClick = onClick)
    )
}

