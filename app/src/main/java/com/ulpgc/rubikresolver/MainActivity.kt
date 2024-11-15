@file:OptIn(ExperimentalMaterial3Api::class)

package com.ulpgc.rubikresolver

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ulpgc.rubikresolver.components.IconButton
import com.ulpgc.rubikresolver.components.MainButton
import com.ulpgc.rubikresolver.ui.theme.RubikResolverTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RubikResolverTheme {
                MainFrame()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MainFrame(){
    Surface(color = Color(0xFF29A2FF)) {
        Column(Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Title()

            Image(
                painter = painterResource(id = R.drawable.cube_model),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )

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

@Composable
fun ButtonMenu() {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }

    val context = LocalContext.current


    Column(
        verticalArrangement = Arrangement.spacedBy(25.dp),
        modifier = Modifier
            .padding(bottom = 40.dp )
            .padding(start = 10.dp )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainButton(
            text = "Start",
            onClick = { launcher.launch(Intent(context, CameraActivity::class.java)) }
        )

        MainButton(
            text = "Tutorial",
            onClick = { launcher.launch(Intent(context, CameraActivity::class.java)) }
        )


        Spacer(modifier = Modifier.weight(1f))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(R.drawable.gear, imageSize = 80.dp, onClick = {})
        }
    }

}
