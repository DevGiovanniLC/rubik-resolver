package com.ulpgc.rubikresolver.opengl.objects

import android.opengl.GLES20
import androidx.compose.ui.graphics.Color
import com.ulpgc.rubikresolver.model.Cube
import java.nio.ByteBuffer
import java.nio.ByteOrder

class GLCube(private val cubeModel: Cube) {
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

    private val faceIndex: HashMap<String, Int> = hashMapOf(
        "front" to 0,
        "back" to 4,
        "left" to 8,
        "right" to 12,
        "top" to 16,
        "bottom" to 20
    )

    private var mPositionHandle: Int = 0
    private var mColorHandle: Int = 0
    private var mvpMatrixHandle: Int = 0

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

    @Suppress("SpellCheckingInspection")
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

        paintCube(mvpMatrix, cubeModel)
        drawContour(mvpMatrix)
        GLES20.glDisableVertexAttribArray(mPositionHandle)
    }

    private fun paintCube(mvpMatrix: FloatArray, cubeModel: Cube) {
        GLES20.glEnable(GLES20.GL_POLYGON_OFFSET_FILL)
        paintFace(mvpMatrix, cubeModel.frontColor, faceIndex["front"]!!)
        paintFace(mvpMatrix, cubeModel.backColor, faceIndex["back"]!!)
        paintFace(mvpMatrix, cubeModel.leftColor, faceIndex["left"]!!)
        paintFace(mvpMatrix, cubeModel.rightColor, faceIndex["right"]!!)
        paintFace(mvpMatrix, cubeModel.upColor, faceIndex["top"]!!)
        paintFace(mvpMatrix, cubeModel.downColor, faceIndex["bottom"]!!)
        GLES20.glDisableVertexAttribArray(GLES20.GL_POLYGON_OFFSET_FILL)
    }

    private fun paintFace(mvpMatrix: FloatArray, faceColor: Color, colorIndex: Int) {
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
            GLES20.glUniform4fv(colorHandle, 1, composeColor(faceColor), 0)
        }
        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix").also {
            GLES20.glUniformMatrix4fv(it, 1, false, mvpMatrix, 0)
        }
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, colorIndex, 4)
    }

    private fun drawContour(mvpMatrix: FloatArray) {
        GLES20.glEnable(GLES20.GL_DEPTH_TEST)
        mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
            GLES20.glUniform4fv(colorHandle, 1, composeColor(Color.Black), 0)
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
    }

    private fun composeColor(color: Color): FloatArray {
        return floatArrayOf(
            color.red, color.green, color.blue, color.alpha
        )
    }
}