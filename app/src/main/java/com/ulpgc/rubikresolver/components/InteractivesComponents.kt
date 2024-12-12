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
import com.ulpgc.rubikresolver.R

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
    modifier: Modifier = Modifier
) {
    val buttonShape = RoundedCornerShape(10.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .border(width = 2.dp, color = Color.Black, shape = buttonShape)
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = buttonShape,
            modifier = modifier.height(50.dp)
        ) {
            Text(text = text, color = Color.Black)
        }
    }
}

@Composable
fun IconButton(image:Int = R.drawable.gear,  imageSize: Dp = 70.dp,onClick: () -> Unit, modifier: Modifier = Modifier) {
    val buttonShape = CircleShape

    Box(
        modifier = modifier
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
                contentDescription = "Icon button",
                modifier = Modifier.size(imageSize)

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
                            modifier = Modifier.size(50.dp),
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
    selectedColor: Color,
    tileModifier: Modifier = Modifier,
) {

    Column(modifier = Modifier.padding(16.dp)) {
        colorArray.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { columnIndex, cell ->
                    val optionId = "$rowIndex/$columnIndex"

                    TileButton(
                        isSelected = true,
                        color = cell.value,
                        modifier = tileModifier,
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
fun FaceGroup(
    colorArray: Array<Array<MutableState<Color>>>,
    onClick: () -> Unit = {},
    tileModifier: Modifier = Modifier,
) {

    Column(modifier = Modifier.padding(2.dp)) {
        colorArray.forEachIndexed { rowIndex, row ->
            Row {
                row.forEachIndexed { columnIndex, cell ->
                    val optionId = "$rowIndex/$columnIndex"

                    TileButton(
                        isSelected = true,
                        color = cell.value,
                        modifier = tileModifier,
                        onClick = {
                            onClick()
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
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .border(2.dp, if (isSelected) Color.Black else Color.Gray)
            .background(color)
            .clickable(onClick = onClick)
    )
}

