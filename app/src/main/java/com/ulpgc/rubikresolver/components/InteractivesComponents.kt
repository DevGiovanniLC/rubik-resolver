package com.ulpgc.rubikresolver.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun MockComponentsPreview(){

    MainButton(
        text = "mock",
        onClick = { }
    )

}

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier.height(50.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = text, color = Color.Black)
    }
}