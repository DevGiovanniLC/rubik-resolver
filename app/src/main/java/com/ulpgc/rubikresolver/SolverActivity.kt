package com.ulpgc.rubikresolver

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.ui.theme.screen.SolverScreen

class SolverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var cube : RubikCube? = null
            var isError = false

            try {
                cube = RubikCube.RubikBuilder.build()
            }catch (e: Exception){
                isError = true
                startActivity(
                    Intent(this, ErrorActivity::class.java)
                        .putExtra("error", e.toString())
                )
            }
            if (cube is RubikCube) {
                SolverScreen(cube)
            }
        }
    }
}
