package com.ulpgc.rubikresolver.opengl.objects

import android.opengl.Matrix
import androidx.compose.ui.graphics.Color
import com.ulpgc.rubikresolver.model.Cube
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.model.RubikCube.Face
import com.ulpgc.rubikresolver.model.RubikCube.Face.*

class GLRubikCube(private val rubikCubeModel: RubikCube) {
    private val cubes: Array<Cube> = Array(27) { Cube() }
    private val glCubes: Array<GLCube> = Array(27) { GLCube(cubes[it]) }
    private var rotatingFace: Face? = null
    private var rotatingAngle: Float = 0f

    init {
        paintFace(RubikCube.Face.FRONT) { index -> index % 9 in 6..8 } // Front face
        paintFace(RubikCube.Face.BACK) { index -> index % 9 in 0..2 }  // Back face
        paintFace(RubikCube.Face.LEFT) { index -> index % 3 == 0 }     // Left face
        paintFace(RubikCube.Face.RIGHT) { index -> index % 3 == 2 }    // Right face
        paintFace(RubikCube.Face.UP) { index -> index / 9 == 2 }      // Top face
        paintFace(RubikCube.Face.DOWN) { index -> index / 9 == 0 }   // Bottom face
    }

    fun draw(mvpMatrix: FloatArray) {
        for (i in 0 until 27) {
            val translationMatrix = FloatArray(16)
            val position = calculatePosition(i)
            Matrix.setIdentityM(translationMatrix, 0)
            Matrix.translateM(translationMatrix, 0, position[0], position[1], position[2])

            val cubeMatrix = FloatArray(16)
            Matrix.setIdentityM(cubeMatrix, 0)
            Matrix.multiplyMM(cubeMatrix, 0, mvpMatrix, 0, cubeMatrix, 0)

            val rotationMatrix = FloatArray(16)
            rotatingFace?.let { face ->
                if (isCubeOnFace(face, i)) {
                    Matrix.setIdentityM(rotationMatrix, 0)

                    // Rotate around the correct axis
                    when (face) {
                        FRONT ->
                            Matrix.setRotateM(rotationMatrix, 0, rotatingAngle, 0f, 0f, 1f)
                        BACK ->
                            Matrix.setRotateM(rotationMatrix, 0, rotatingAngle, 0f, 0f, -1f)
                        LEFT ->
                            Matrix.setRotateM(rotationMatrix, 0, rotatingAngle, -1f, 0f, 0f)
                        RIGHT ->
                            Matrix.setRotateM(rotationMatrix, 0, rotatingAngle, 1f, 0f, 0f)
                        UP ->
                            Matrix.setRotateM(rotationMatrix, 0, rotatingAngle, 0f, 1f, 0f)
                        DOWN ->
                            Matrix.setRotateM(rotationMatrix, 0, rotatingAngle, 0f, -1f, 0f)
                    }

                    // Combine rotation with cube matrix
                    Matrix.multiplyMM(cubeMatrix, 0, cubeMatrix, 0, rotationMatrix, 0)
                }
            }
            Matrix.multiplyMM(cubeMatrix, 0, cubeMatrix, 0, translationMatrix, 0)
            glCubes[i].draw(cubeMatrix)
        }
    }

    private fun isCubeOnFace(face: Face, i: Int): Boolean {
        return glCubes[i] in getCubesFromFace(face)
    }

    private fun getCubesFromFace(face: Face): List<GLCube> {
        return when(face) {
            FRONT -> glCubes.filterIndexed { index, _ -> index % 9 in 6..8 }
            BACK -> glCubes.filterIndexed { index, _ -> index % 9 in 0..2 }
            LEFT -> glCubes.filterIndexed { index, _ -> index % 3 == 0 }
            RIGHT -> glCubes.filterIndexed { index, _ -> index % 3 == 2 }
            UP -> glCubes.filterIndexed { index, _ -> index / 9 == 2 }
            DOWN -> glCubes.filterIndexed { index, _ -> index / 9 == 0 }
        }
    }

    fun rotateFace(face: Face, angle: Float) {
        rotatingFace = face
        rotatingAngle = angle
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

    private fun calculatePosition(index: Int): FloatArray {
        val x = index % 3 - 1
        val y = index / 9 - 1
        val z = index / 3 % 3 - 1
        return floatArrayOf(x.toFloat(), y.toFloat(), z.toFloat())
    }

    private fun paintFace(face: Face, filter: (Int) -> Boolean) {
        val faceCubes = cubes.filterIndexed { index, _ -> filter(index) }
        val faceColors = rubikCubeModel.getFace(face).flatten()
        for ((cube, charColor) in faceCubes.zip(faceColors)) {
            when (face) {
                FRONT -> cube.frontColor = toColor(charColor)
                BACK -> cube.backColor = toColor(charColor)
                LEFT -> cube.leftColor = toColor(charColor)
                RIGHT -> cube.rightColor = toColor(charColor)
                UP -> cube.upColor = toColor(charColor)
                DOWN -> cube.downColor = toColor(charColor)
            }
        }
    }
}