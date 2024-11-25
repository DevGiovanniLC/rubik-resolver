package com.ulpgc.rubikresolver.opengl.renderer

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import androidx.compose.ui.graphics.Color
import com.ulpgc.rubikresolver.model.Cube
import com.ulpgc.rubikresolver.opengl.objects.GLCube
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SolverRenderer : GLSurfaceView.Renderer {
    private lateinit var mCube: GLCube
    private val viewMatrix = FloatArray(16)
    private val vPMatrix = FloatArray(16)
    private val projectionMatrix = FloatArray(16)
    override fun onSurfaceCreated(unused: GL10, config: EGLConfig?) {
        // Set the background frame color
        GLES20.glClearColor(0.157f, 0.627f, 1.0f, 1.0f)

        val red = Color(0.8f, 0.0f, 0.0f, 1.0f)
        val orange = Color(1.0f, 0.5f, 0.0f, 1.0f)
        val blue = Color(0.0f, 0.0f, 0.8f, 1.0f)
        val green = Color(0.0f, 0.8f, 0.0f, 1.0f)
        val yellow = Color(1.0f, 1.0f, 0.0f, 1.0f)
        val white = Color(1.0f, 1.0f, 1.0f, 1.0f)

        val cube = Cube(
            frontColor = red,
            backColor = orange,
            leftColor = blue,
            rightColor = green,
            upColor = yellow,
            downColor = white
        )
        mCube = GLCube(cube)
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

        mCube.draw(vPMatrix)
    }
}