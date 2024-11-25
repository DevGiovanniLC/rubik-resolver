package com.ulpgc.rubikresolver.opengl.renderer

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.model.RubikCube.Face.*
import com.ulpgc.rubikresolver.opengl.objects.GLRubikCube
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SolverRenderer : GLSurfaceView.Renderer {
    private lateinit var glCube: GLRubikCube
    private val viewMatrix = FloatArray(16)
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private var animate = false
    private var animationFace = FRONT
    private var animateCounterClockwise = 1
    private var rotationAngle = 0f
    override fun onSurfaceCreated(unused: GL10, config: EGLConfig?) {
        // Set the background frame color
        GLES20.glClearColor(0.157f, 0.627f, 1.0f, 1.0f)
        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 4.0f, 4.0f, 4.0f, 2.0f, 2.0f, 2.0f, 0f, 1.0f, 0f)
        val mockCube = RubikCube.RubikBuilder
            .setFace(
                RubikCube.Face.FRONT, arrayOf(
                    arrayOf('F', 'F', 'F'),
                    arrayOf('F', 'F', 'F'),
                    arrayOf('F', 'F', 'F')
                )
            )
            .setFace(
                RubikCube.Face.BACK, arrayOf(
                    arrayOf('B', 'B', 'B'),
                    arrayOf('B', 'B', 'B'),
                    arrayOf('B', 'B', 'B')
                )
            )
            .setFace(
                RubikCube.Face.LEFT, arrayOf(
                    arrayOf('L', 'L', 'L'),
                    arrayOf('L', 'L', 'L'),
                    arrayOf('L', 'L', 'L')
                )
            )
            .setFace(
                RubikCube.Face.RIGHT, arrayOf(
                    arrayOf('R', 'R', 'R'),
                    arrayOf('R', 'R', 'R'),
                    arrayOf('R', 'R', 'R')
                )
            )
            .setFace(
                RubikCube.Face.UP, arrayOf(
                    arrayOf('U', 'U', 'U'),
                    arrayOf('U', 'U', 'U'),
                    arrayOf('U', 'U', 'U')
                )
            )
            .setFace(
                RubikCube.Face.DOWN, arrayOf(
                    arrayOf('D', 'D', 'D'),
                    arrayOf('D', 'D', 'D'),
                    arrayOf('D', 'D', 'D')
                )
            )
            .build()
        glCube = GLRubikCube(mockCube)
        performMove('D', true)
    }

    override fun onSurfaceChanged(unused: GL10, width: Int, height: Int) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height)
        val aspectRatio: Float = width.toFloat() / height.toFloat()

        Matrix.frustumM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, 0.5f, 10.0f)
    }

    override fun onDrawFrame(unused: GL10) {
        // Redraw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT)

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 3.0f, 2.4f, 3.0f, 0f, 0f, 0f, 0f, 1f, 0f)

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)

        animate()
        glCube.draw(vPMatrix)
    }

    private fun animate() {
        if (!animate) {
            return
        }
        if (rotationAngle >= 90) {
            animate = false
            rotationAngle = 0f
            return
        }
        rotationAngle += 1.5f
        glCube.rotateFace(animationFace, rotationAngle * animateCounterClockwise)
    }

    fun performMove(move: Char, clockwise: Boolean = false) {
        animate = true
        animateCounterClockwise = if (clockwise) -1 else 1
        animationFace = when (move) {
            'F' -> FRONT
            'B' -> BACK
            'L' -> LEFT
            'R' -> RIGHT
            'U' -> UP
            'D' -> DOWN
            else -> {
                animate = false; FRONT
            }
        }
    }
}