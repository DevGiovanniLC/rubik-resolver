package com.ulpgc.rubikresolver.opengl.objects

import android.opengl.GLES20
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Square {
    private var coordinatesPerVertex = 3
    private val vertexCoordinates = floatArrayOf(
        -0.5f, 0.5f, 0.0f,      // top left
        -0.5f, -0.5f, 0.0f,      // bottom left
        0.5f, -0.5f, 0.0f,       // bottom right
        0.5f, 0.5f, 0.0f        // top right
    )
    private var color = floatArrayOf(0.63671875f, 0.76953125f, 0.22265625f, 1.0f)

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
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
            GLES20.glUniform4fv(colorHandle, 1, color, 0)
        }

        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix").also {
            GLES20.glUniformMatrix4fv(it, 1, false, mvpMatrix, 0)
        }

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount)
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }
}