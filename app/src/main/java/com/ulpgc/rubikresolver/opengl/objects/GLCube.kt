package com.ulpgc.rubikresolver.opengl.objects

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder

class GLCube {
    private var coordinatesPerVertex = 3
    private val vertexCoordinates = floatArrayOf(
        // front
        -0.5f, 0.5f, 0.5f,      // top left front
        -0.5f, -0.5f, 0.5f,     // bottom left front
        0.5f, -0.5f, 0.5f,      // bottom right front
        0.5f, 0.5f, 0.5f,       // top right front

        // back
        -0.5f, 0.5f, -0.5f,     // top left back
        -0.5f, -0.5f, -0.5f,    // bottom left back
        0.5f, -0.5f, -0.5f,     // bottom right back
        0.5f, 0.5f, -0.5f,       // top right back

        // left
        -0.5f, 0.5f, 0.5f,      // top left front
        -0.5f, -0.5f, 0.5f,     // bottom left front
        -0.5f, -0.5f, -0.5f,    // bottom left back
        -0.5f, 0.5f, -0.5f,     // top left back

        // right
        0.5f, 0.5f, 0.5f,       // top right front
        0.5f, -0.5f, 0.5f,      // bottom right front
        0.5f, -0.5f, -0.5f,     // bottom right back
        0.5f, 0.5f, -0.5f,      // top right back

        // top
        -0.5f, 0.5f, 0.5f,      // top left front
        0.5f, 0.5f, 0.5f,       // top right front
        0.5f, 0.5f, -0.5f,      // top right back
        -0.5f, 0.5f, -0.5f,     // top left back

        // bottom
        -0.5f, -0.5f, 0.5f,     // bottom left front
        0.5f, -0.5f, 0.5f,      // bottom right front
        0.5f, -0.5f, -0.5f,     // bottom right back
        -0.5f, -0.5f, -0.5f     // bottom left back
    )
    private var color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)
    private var colorBlack = floatArrayOf(0.0f, 0.0f, 0.0f, 1.0f)

    private var mPositionHandle: Int = 0
    private var mColorHandle: Int = 0
    private var mvpMatrixHandle: Int = 0

    private val vertexCount: Int = vertexCoordinates.size / coordinatesPerVertex
    private val vertexStride: Int = coordinatesPerVertex * 4 // 4 bytes per vertex

    private var mProgram: Int

    init {
        val vertexShader: Int = loadShader(GLES20.GL_VERTEX_SHADER, getVertexShaderCode())
        val fragmentShader: Int = loadShader(GLES20.GL_FRAGMENT_SHADER, getFragmentShaderCode())

        mProgram = GLES20.glCreateProgram().also {
            GLES20.glAttachShader(it, vertexShader)
            GLES20.glAttachShader(it, fragmentShader)
            GLES20.glLinkProgram(it)
        }
    }

    private fun getVertexShaderCode(): String {
        return """
        uniform mat4 uMVPMatrix;
        attribute vec4 vPosition;
        void main() {
            gl_Position = uMVPMatrix * vPosition;
        }
        """.trimIndent()
    }

    private fun getFragmentShaderCode(): String {
        return """
        precision mediump float;
        uniform vec4 vColor;
        void main() {  
            gl_FragColor = vColor;
        }
        """.trimIndent()
    }

    private val vertexBuffer = ByteBuffer.allocateDirect(vertexCoordinates.size * 4).run {
        order(ByteOrder.nativeOrder())
        asFloatBuffer().apply {
            put(vertexCoordinates)
            position(0)
        }
    }

    private fun loadShader(type: Int, shaderCode: String): Int {
        return GLES20.glCreateShader(type).also { shader ->
            GLES20.glShaderSource(shader, shaderCode)
            GLES20.glCompileShader(shader)
        }
    }

    fun draw(mvpMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)
        mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition").also {
            GLES20.glEnableVertexAttribArray(it)
            GLES20.glVertexAttribPointer(
                it,
                coordinatesPerVertex,
                GLES20.GL_FLOAT,
                false,
                vertexStride,
                vertexBuffer
            )
        }
/*
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
            GLES20.glUniform4fv(colorHandle, 1, color, 0)
        }

        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix").also {
            GLES20.glUniformMatrix4fv(it, 1, false, mvpMatrix, 0)
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount)
*/

        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
            GLES20.glUniform4fv(colorHandle, 1, colorBlack, 0)
        }
        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix").also {
            GLES20.glUniformMatrix4fv(it, 1, false, mvpMatrix, 0)
        }

        GLES20.glLineWidth(10.0f)
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 4)
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 4, 4)
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 8, 4)
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 12, 4)
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 16, 4)
        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 20, 4)
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }
}