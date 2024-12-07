package com.ulpgc.rubikresolver.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

            RadioGroupExample()
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
    val buttonShape = CircleShape // Forma circular

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
                    .size(imageSize) // Ajusta el tamaño de la imagen
            )
        }
    }
}



@Composable
fun RadioGroupExample() {
    // Lista de opciones
    val options = listOf("Opción 1", "Opción 2", "Opción 3")

    // Estado para la opción seleccionada
    var selectedOption by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Selecciona una opción:")

        // Itera sobre las opciones para crear un "RadioGroup"
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    onClick = { selectedOption = option }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(option)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Muestra la opción seleccionada
        Text(
            text = "Seleccionaste: ${selectedOption ?: "Ninguna"}",
        )
    }
}