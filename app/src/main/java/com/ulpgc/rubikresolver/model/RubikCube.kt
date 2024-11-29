package com.ulpgc.rubikresolver.model

data class RubikCube private constructor (private val cube: Array<Array<Array<Char>>>) {


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



    object RubikBuilder {
        private val cube: Array<Array<Array<Char>>> = Array(6) { Array(3) { Array(3) { ' ' } } }

        fun setFace(faceName: RubikCube.Face, faceValue: Array<Array<Char>>){

            if (faceValue.size != 3 || faceValue[0].size != 3) {
                throw RubikFaceError("Face value must be a 3x3 array")
            }

            if (faceValue.all { row -> row.all { it == ' ' || it == null } }) {
                throw RubikFaceError("Face cannot be filled with empty values or null")
            }

            if (faceValue.any { row -> row.any { it == null } }) {
                throw RubikFaceError("Face cannot contain null values")
            }

            this.cube[faceName.value] = faceValue
        }

        fun build(): RubikCube {

            cube.forEach { face ->
                if (face.any { row -> row.any { it == ' ' || it == null } }) {
                    throw RubikCubeError("One or more faces contain empty values or null")
                }
            }

            return RubikCube(cube)
        }
    }

    enum class Face(val value: Int) {
        FRONT(0),
        BACK(1),
        LEFT(2),
        RIGHT(3),
        TOP(4),
        BOTTOM(5)
    }
}