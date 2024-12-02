package com.ulpgc.rubikresolver.model

import com.ulpgc.rubikresolver.model.RubikCube.Face
import kotlin.collections.get

open class UncheckedRubikCube(private val cube: Array<Array<Array<Char>>>) {

    fun getFace(face: Face): Array<Array<Char>> {

        when {
            face.value >= cube.size  -> { throw RubikFaceError("Face $face is not set") }
            face.value < 0  -> { throw RubikFaceError("Face $face is not set") }
        }

        val faceValue = cube[face.value]


        return faceValue
    }

    internal fun getCube(): Array<Array<Array<Char>>> {
        return cube
    }


    override fun toString(): String {
        return cube.joinToString("") { face ->
            face.joinToString("") { row ->
                row.joinToString("")
            }
        }
    }
}