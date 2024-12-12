package com.ulpgc.rubikresolver

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.ulpgc.rubikresolver.components.IconButton


class ErrorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val error = intent.getStringExtra("error") ?: "No error message provided"
        setContent {
            Surface(color = Color(0xFF29A2FF)) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(50.dp),
                ) {
                    val context = LocalContext.current
                    TextError(error)
                    IconButton(R.drawable.edit, 70.dp, onClick = {
                        startActivity(Intent(context, CheckCubeActivity::class.java))
                    })
                }
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        startActivity(
            Intent(this, CheckCubeActivity::class.java)
        )
    }
}


@Composable
private fun TextError(string: String){
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Text(
        text = string,
        style = TextStyle(
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            shadow = Shadow(
                color = Color.Black,
                offset = Offset(3f, 3f),
                blurRadius = 4f
            ),
            background = Color.Transparent
        ),
        modifier = Modifier.padding(top = screenHeight * 0.1f)
    )
}