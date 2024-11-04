package com.ulpgc.rubikresolver

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulpgc.rubikresolver.components.MainButton
import com.ulpgc.rubikresolver.ui.theme.RubikResolverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RubikResolverTheme {

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainFrame(){
    Surface(color = Color(0xFF29A2FF)) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(40.dp))
            Title()
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
            color = Color.White, // Color del texto
            shadow = Shadow(
                color = Color.Black, // Color del borde/sombra
                offset = Offset(3f, 3f), // Desplazamiento del borde/sombra
                blurRadius = 4f // Difuminado del borde/sombra
            ),
            background = Color.Transparent // Asegúrate de que el fondo sea transparente
        )
    )
}

@Composable
fun ButtonMenu() {


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) {  }

    val context = LocalContext.current // Obtén el contexto

    var shouldNavigate by remember { mutableStateOf(false) }
    MainButton(
        text = "Start",
        onClick = {shouldNavigate = true }
    )

    if (shouldNavigate) {
        val intent = Intent(context, CameraActivity::class.java)
        launcher.launch(intent)
        shouldNavigate = false
    }

}