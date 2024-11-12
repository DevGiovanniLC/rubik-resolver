package com.ulpgc.rubikresolver.data

data class RubikCube (private val cube: Array<Array<Array<Char>>>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RubikCube

        return cube.contentDeepEquals(other.cube)
    }

    override fun hashCode(): Int {
        return cube.contentDeepHashCode()
    }

    override fun toString(): String {
        return cube.joinToString("") { face ->
            face.joinToString("") { row ->
                row.joinToString("")
            }
        }
    }
}