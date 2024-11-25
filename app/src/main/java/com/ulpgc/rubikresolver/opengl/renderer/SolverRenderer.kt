package com.ulpgc.rubikresolver.opengl.renderer

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.os.SystemClock
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.opengl.objects.GLRubikCube
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SolverRenderer : GLSurfaceView.Renderer {
    private lateinit var mCube: GLRubikCube
    private val viewMatrix = FloatArray(16)
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    override fun onSurfaceCreated(unused: GL10, config: EGLConfig?) {
        // Set the background frame color
        GLES20.glClearColor(0.157f, 0.627f, 1.0f, 1.0f)

        val mockCube = RubikCube.RubikBuilder
            .setFace(
                RubikCube.Face.FRONT, arrayOf(
                    arrayOf('R', 'R', 'R'),
                    arrayOf('R', 'R', 'R'),
                    arrayOf('R', 'R', 'R')
                ))
            .setFace(
                RubikCube.Face.BACK, arrayOf(
                    arrayOf('O', 'O', 'O'),
                    arrayOf('O', 'O', 'O'),
                    arrayOf('O', 'O', 'O')
                ))
            .setFace(
                RubikCube.Face.LEFT, arrayOf(
                    arrayOf('B', 'B', 'B'),
                    arrayOf('B', 'B', 'B'),
                    arrayOf('B', 'B', 'B')
                ))
            .setFace(
                RubikCube.Face.RIGHT, arrayOf(
                    arrayOf('G', 'G', 'G'),
                    arrayOf('G', 'G', 'G'),
                    arrayOf('G', 'G', 'G')
                ))
            .setFace(
                RubikCube.Face.TOP, arrayOf(
                    arrayOf('Y', 'Y', 'Y'),
                    arrayOf('Y', 'Y', 'Y'),
                    arrayOf('Y', 'Y', 'Y')
                ))
            .setFace(
                RubikCube.Face.BOTTOM, arrayOf(
                    arrayOf('W', 'W', 'W'),
                    arrayOf('W', 'W', 'W'),
                    arrayOf('W', 'W', 'W')
                ))
            .build()
        mCube = GLRubikCube(mockCube)
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
        Matrix.setLookAtM(viewMatrix, 0, 4.0f, 4.0f, 4.0f, 2.0f, 2.0f, 2.0f, 0f, 1.0f, 0f)

        val rotationMatrix = FloatArray(16)
        val time = SystemClock.uptimeMillis() % 4000L
        val angleInDegrees = 0.09f * time.toInt() % 360
        Matrix.setRotateM(rotationMatrix, 0, angleInDegrees, 0f, 1f, 0f)

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0)
        Matrix.multiplyMM(vPMatrix, 0, vPMatrix, 0, rotationMatrix, 0)
        val translateMatrix = FloatArray(16)
        Matrix.setIdentityM(translateMatrix, 0)
        Matrix.translateM(translateMatrix, 0, 0.0f, 2.0f, 0.0f)

        mCube.draw(vPMatrix)
    }

}