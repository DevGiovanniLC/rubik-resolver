package com.ulpgc.rubikresolver.model

data class RubikCube private constructor (private val cube: Array<Array<Array<Char>>>):
    UncheckedRubikCube(cube) {

    override fun toString(): String {
        return cube.joinToString("") { face ->
            face.joinToString("") { row ->
                row.joinToString("")
            }
        }
    }

    object RubikBuilder {
        private var cube: Array<Array<Array<Char>>> = Array(6) { Array(3) { Array(3) { ' ' } } }

        fun setFace(faceName: Face, faceValue: Array<Array<Char>>): RubikBuilder{

            if (faceValue.size != 3 || faceValue[0].size != 3) {
                throw RubikFaceError("Face value must be a 3x3 array")
            }

            if (faceValue.any { row -> row.any { it == ' ' } }) {
                throw RubikFaceError("Face cannot be filled with empty values or null")
            }


            this.cube[faceName.value] = faceValue
            return this
        }

        fun stringToCube(stringCube: String): RubikBuilder {
            val up = arrayOf(
                stringCube.substring(0, 3).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(3, 6).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(6, 9).toCharArray().map { it }.toTypedArray()
            )
            setFace(Face.UP, up)

            val right = arrayOf(
                stringCube.substring(9, 12).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(12, 15).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(15, 18).toCharArray().map { it }.toTypedArray()
            )
            setFace(Face.RIGHT, right)

            val front = arrayOf(
                stringCube.substring(18, 21).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(21, 24).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(24, 27).toCharArray().map { it }.toTypedArray()
            )
            setFace(Face.FRONT, front)

            val down = arrayOf(
                stringCube.substring(27, 30).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(30, 33).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(33, 36).toCharArray().map { it }.toTypedArray()
            )
            setFace(Face.DOWN, down)

            val left = arrayOf(
                stringCube.substring(36, 39).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(39, 42).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(42, 45).toCharArray().map { it }.toTypedArray()
            )
            setFace(Face.LEFT, left)

            val back = arrayOf(
                stringCube.substring(45, 48).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(48, 51).toCharArray().map { it }.toTypedArray(),
                stringCube.substring(51, 54).toCharArray().map { it }.toTypedArray()
            )
            setFace(Face.BACK, back)

            return this
        }

        fun reset(): RubikBuilder {
            cube = Array(6) { Array(3) { Array(3) { ' ' } } }
            return this
        }

        internal fun buildUncheckedRubikCube(): UncheckedRubikCube {
            return RubikCube(cube)
        }


        fun build(): RubikCube {


            RubikCubeChecker(buildUncheckedRubikCube())


            return RubikCube(cube)
        }
    }



    enum class Face(val value: Int) {
        UP(0),  // U ->  white
        RIGHT(1),   // R ->  blue
        FRONT(2),  // F -> red
        DOWN(3),   // D -> yellow
        LEFT(4),   // L -> green
        BACK(5),   // B -> orange
    }
}

