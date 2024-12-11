package com.ulpgc.rubikresolver.opengl.renderer

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.model.RubikCube.Face.*
import com.ulpgc.rubikresolver.opengl.objects.GLRubikCube
import com.ulpgc.rubikresolver.services.RubikCubeMovement
import com.ulpgc.rubikresolver.services.RubikSolver
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SolverRenderer(private var cube: RubikCube) : GLSurfaceView.Renderer {
    private lateinit var glCube: GLRubikCube
    private val viewMatrix = FloatArray(16)
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    private var animate = false
    private var animationFace = FRONT
    private var animateCounterClockwise = 1
    private var rotationAngle = 0f
    private val solver = RubikSolver()
    private lateinit var solutionMoves: List<String>
    private var currentMove = 0
    override fun onSurfaceCreated(unused: GL10, config: EGLConfig?) {
        // Set the background frame color
        GLES20.glClearColor(0.157f, 0.627f, 1.0f, 1.0f)
        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 4.0f, 4.0f, 4.0f, 2.0f, 2.0f, 2.0f, 0f, 1.0f, 0f)
        glCube = GLRubikCube(cube)
        solutionMoves = expandSolution(solver.solve(cube))
    }

    private fun expandSolution(solve: List<String>): List<String> {
        val expandedMoves = mutableListOf<String>()

        for (move in solve) {
            if (move.length == 1 || (move.length == 2) && move[1] == '\'') {
                expandedMoves.add(move)
                continue
            }
            expandedMoves.add(move.take(move.length - 1))
            expandedMoves.add(move.take(move.length - 1))
        }
        return expandedMoves
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
            rotationAngle = 0f
            cube = RubikCubeMovement.applyMove(cube, solutionMoves[currentMove - 1])
            glCube = GLRubikCube(cube)
            animate = false
            return
        }
        rotationAngle += 1.5f
        glCube.rotateFace(animationFace, rotationAngle * animateCounterClockwise)
    }

    private fun performMove(move: Char, clockwise: Boolean = false) {
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

    fun nextMove() {
        if (currentMove >= solutionMoves.size || animate) {
            return
        }
        val move = solutionMoves[currentMove]
        performMove(move)
        currentMove++
    }
}