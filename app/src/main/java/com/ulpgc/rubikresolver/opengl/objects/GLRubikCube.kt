package com.ulpgc.rubikresolver.opengl.objects

import android.opengl.Matrix
import androidx.compose.ui.graphics.Color
import com.ulpgc.rubikresolver.model.Cube
import com.ulpgc.rubikresolver.model.RubikCube

class GLRubikCube(private val rubikCubeModel: RubikCube) {
    private val cubes: Array<Cube> = Array(27) { Cube() }
    private val glCubes: Array<GLCube> = Array(27) { GLCube(cubes[it]) }

    init {
        paintFace(RubikCube.Face.FRONT) { index -> index % 9 in 6..8 } // Front face
        paintFace(RubikCube.Face.BACK) { index -> index % 9 in 0..2 }  // Back face
        paintFace(RubikCube.Face.LEFT) { index -> index % 3 == 0 }     // Left face
        paintFace(RubikCube.Face.RIGHT) { index -> index % 3 == 2 }    // Right face
        paintFace(RubikCube.Face.UP) { index -> index / 9 == 2 }      // Top face
        paintFace(RubikCube.Face.DOWN) { index -> index / 9 == 0 }   // Bottom face
    }

    private fun paintFace(face: RubikCube.Face, filter: (Int) -> Boolean) {
        val faceCubes = cubes.filterIndexed { index, _ -> filter(index) }
        val faceColors = rubikCubeModel.getFace(face).flatten()
        for ((cube, charColor) in faceCubes.zip(faceColors)) {
            when (face) {
                RubikCube.Face.FRONT -> cube.frontColor = toColor(charColor)
                RubikCube.Face.BACK -> cube.backColor = toColor(charColor)
                RubikCube.Face.LEFT -> cube.leftColor = toColor(charColor)
                RubikCube.Face.RIGHT -> cube.rightColor = toColor(charColor)
                RubikCube.Face.UP -> cube.upColor = toColor(charColor)
                RubikCube.Face.DOWN -> cube.downColor = toColor(charColor)
            }
        }
    }

    private fun toColor(charColor: Char): Color {
        return when (charColor) {
            'F' -> Color.Red
            'R' -> Color.Green
            'L' -> Color.Blue
            'U' -> Color.Yellow
            'B' -> Color(1.0f, 0.5f, 0.0f, 1.0f)
            'D' -> Color.White
            else -> Color.Black
        }
    }

    fun draw(mvpMatrix: FloatArray) {
        for (i in 0 until 27) {
            val translationMatrix = FloatArray(16)
            val position = calculatePosition(i)
            Matrix.setIdentityM(translationMatrix, 0)
            Matrix.translateM(translationMatrix, 0, position[0], position[1], position[2])
            val cubeMatrix = FloatArray(16)
            Matrix.multiplyMM(cubeMatrix,  0, mvpMatrix, 0, translationMatrix,0)
            glCubes[i].draw(cubeMatrix)
        }
    }

    private fun calculatePosition(index: Int): FloatArray {
        val x = index % 3 - 1
        val y = index / 9 - 1
        val z = index / 3 % 3 - 1
        return floatArrayOf(x.toFloat(), y.toFloat(), z.toFloat())
    }
}