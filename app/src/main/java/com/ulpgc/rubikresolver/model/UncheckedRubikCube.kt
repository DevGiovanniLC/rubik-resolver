package com.ulpgc.rubikresolver.model

import com.ulpgc.rubikresolver.model.RubikCube.Face

open class UncheckedRubikCube(private val cube: Array<Array<Array<Char>>>) {

    fun getFace(face: Int): Array<Array<Char>> {

        when {
            face >= cube.size  -> { throw RubikFaceError("Face $face is not set") }
            face < 0  -> { throw RubikFaceError("Face $face is not set") }
        }

        val faceValue = cube[face]


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