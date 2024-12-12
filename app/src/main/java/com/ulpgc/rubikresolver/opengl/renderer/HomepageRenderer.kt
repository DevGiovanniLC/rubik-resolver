package com.ulpgc.rubikresolver.opengl.renderer

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.model.RubikCube.Face.*
import com.ulpgc.rubikresolver.opengl.objects.GLRubikCube
import com.ulpgc.rubikresolver.services.RubikCubeMovement
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.random.Random

class HomepageRenderer(cube: RubikCube) : GLSurfaceView.Renderer {
    private val viewMatrix = FloatArray(16)
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private var isAnimationInProgress = false
    private var animationFace = FRONT
    private var animateCounterClockwise = 1
    private var rotationAngle = 0f
    private var appliedMove = ""
    private var currentCube: RubikCube
    private lateinit var glCube: GLRubikCube
    private val legalMoves = arrayOf("F", "F'", "B", "B'", "L", "L'", "R", "R'", "U", "U'", "D", "D'")

    init {
        currentCube = RubikCube.RubikBuilder.stringToCube(cube.toString()).build()
    }

    override fun onSurfaceCreated(unused: GL10, config: EGLConfig?) {
        // Set the background frame color
        GLES20.glClearColor(0.157f, 0.627f, 1.0f, 1.0f)
        // Set the camera position (View matrix)
        glCube = GLRubikCube(currentCube)
        randomMove()
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

        // Create a rotation for the cube
        val rotationMatrix = FloatArray(16)
        Matrix.setIdentityM(rotationMatrix, 0)
        val angleInDegrees = System.currentTimeMillis() % 14400 / 40.0f
        Matrix.rotateM(rotationMatrix, 0, angleInDegrees, -0.4f, 1f, -0.8f)

        // Rotation Matrix
        Matrix.multiplyMM(vPMatrix, 0, vPMatrix, 0, rotationMatrix, 0)

        animate()
        glCube.draw(vPMatrix)
    }

    private fun animate() {
        if (!isAnimationInProgress) {
            randomMove()
        }
        if (rotationAngle >= 90) {
            rotationAngle = 0f
            currentCube = RubikCubeMovement.applyMove(currentCube, appliedMove)
            glCube.updateCube(currentCube)
            isAnimationInProgress = false
        }
        if (isAnimationInProgress) {
            rotationAngle += 1.5f
            glCube.rotateFace(animationFace, rotationAngle * animateCounterClockwise)
        }
    }

    private fun performMove(move: Char, clockwise: Boolean = false) {
        animateCounterClockwise = if (clockwise) -1 else 1
        animationFace = when (move) {
            'F' -> FRONT
            'B' -> BACK
            'L' -> LEFT
            'R' -> RIGHT
            'U' -> UP
            'D' -> DOWN
            else -> {
                isAnimationInProgress = false; FRONT
            }
        }
    }

    private fun performMove(move: String) {
        when (move) {
            "F" -> performMove('F', true)
            "F'" -> performMove('F', false)
            "B" -> performMove('B', true)
            "B'" -> performMove('B', false)
            "L" -> performMove('L', true)
            "L'" -> performMove('L', false)
            "R" -> performMove('R', true)
            "R'" -> performMove('R', false)
            "U" -> performMove('U', true)
            "U'" -> performMove('U', false)
            "D" -> performMove('D', true)
            "D'" -> performMove('D', false)
        }
    }

    private fun randomMove() {
        if (isAnimationInProgress) {
            return
        }
        isAnimationInProgress = true
        val randomIndex = Random.nextInt(legalMoves.size)
        val move = legalMoves[randomIndex]
        performMove(move)
        appliedMove = move
    }
}
