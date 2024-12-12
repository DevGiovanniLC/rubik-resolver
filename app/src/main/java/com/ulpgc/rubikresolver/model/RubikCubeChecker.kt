package com.ulpgc.rubikresolver.model

import com.ulpgc.rubikresolver.model.RubikCube.Face
import kotlin.collections.forEach

class RubikCubeChecker(private val rubikCube: UncheckedRubikCube) {
    init {
        rubikCube.getCube().forEach { face ->
            if (face.any { row -> row.any { it == ' '} }) {
                throw RubikCubeError("One or more faces contain empty values or null")
            }

            checkCenter()
            checkCorners()
            checkEdges()
        }
    }

    private fun checkCenter(): Boolean {
        if (!checkCenterDifference(
            rubikCube.getFace(Face.UP.value)[1][1],
            rubikCube.getFace(Face.DOWN.value)[1][1],
            rubikCube.getFace(Face.LEFT.value)[1][1],
            rubikCube.getFace(Face.RIGHT.value)[1][1],
            rubikCube.getFace(Face.FRONT.value)[1][1],
            rubikCube.getFace(Face.BACK.value)[1][1]
        )) {
            throw RubikCubeError("Center color is not correct ${rubikCube.getFace(Face.UP.value)[1][0]} ${rubikCube.getFace(Face.DOWN.value)[1][0]} ${rubikCube.getFace(Face.LEFT.value)[1][0]} ${rubikCube.getFace(Face.RIGHT.value)[1][0]} ${rubikCube.getFace(Face.FRONT.value)[1][0]} ${rubikCube.getFace(Face.BACK.value)[1][0]} must be different")
        }

        return true
    }

    private fun checkEdges(): Boolean {
        val frontEdges = listOf(
            charArrayOf(rubikCube.getFace(Face.FRONT.value)[1][0],rubikCube.getFace(Face.LEFT.value)[1][2]),
            charArrayOf(rubikCube.getFace(Face.FRONT.value)[0][1],rubikCube.getFace(Face.UP.value)[2][1]),
            charArrayOf(rubikCube.getFace(Face.FRONT.value)[1][2],rubikCube.getFace(Face.RIGHT.value)[1][0]),
            charArrayOf(rubikCube.getFace(Face.FRONT.value)[2][1],rubikCube.getFace(Face.DOWN.value)[0][1]),
        )

        val backEdges = listOf(
            charArrayOf(rubikCube.getFace(Face.BACK.value)[1][0],rubikCube.getFace(Face.RIGHT.value)[1][2]),
            charArrayOf(rubikCube.getFace(Face.BACK.value)[0][1],rubikCube.getFace(Face.UP.value)[0][1]),
            charArrayOf(rubikCube.getFace(Face.BACK.value)[1][2],rubikCube.getFace(Face.LEFT.value)[1][0]),
            charArrayOf(rubikCube.getFace(Face.BACK.value)[2][1],rubikCube.getFace(Face.DOWN.value)[2][1]),
        )

        val leftAndRightEdges = listOf(
            charArrayOf(rubikCube.getFace(Face.LEFT.value)[0][1],rubikCube.getFace(Face.UP.value)[1][0]),
            charArrayOf(rubikCube.getFace(Face.LEFT.value)[2][1],rubikCube.getFace(Face.DOWN.value)[1][0]),
            charArrayOf(rubikCube.getFace(Face.RIGHT.value)[0][1],rubikCube.getFace(Face.UP.value)[1][2]),
            charArrayOf(rubikCube.getFace(Face.RIGHT.value)[2][1],rubikCube.getFace(Face.DOWN.value)[1][2]),
        )

        for (edge in frontEdges) {
            if (!checkEdgesDifference(edge[0], edge[1])) {
                throw RubikCubeError("Front edge color is not correct ${edge[0]} ${edge[1]} must be different")
            }
        }

        for (edge in backEdges) {
            if (!checkEdgesDifference(edge[0], edge[1])) {
                throw RubikCubeError("Back edge color is not correct ${edge[0]} ${edge[1]} must be different")
            }
        }

        for (edge in leftAndRightEdges) {
            if (!checkEdgesDifference(edge[0], edge[1])) {
                throw RubikCubeError("Left or right edge color is not correct ${edge[0]} ${edge[1]} must be different")
            }
        }

        return true
    }


    private fun checkCorners(): Boolean {

        val leftUPFrontCorner = charArrayOf(
            rubikCube.getFace(Face.FRONT.value)[0][0],
            rubikCube.getFace(Face.UP.value)[2][0],
            rubikCube.getFace(Face.LEFT.value)[0][2]
        )

        val rightUPFrontCorner = charArrayOf(
            rubikCube.getFace(Face.FRONT.value)[0][2],
            rubikCube.getFace(Face.UP.value)[2][2],
            rubikCube.getFace(Face.RIGHT.value)[0][0]
        )

        val leftDownFrontCorner = charArrayOf(
            rubikCube.getFace(Face.FRONT.value)[2][0],
            rubikCube.getFace(Face.DOWN.value)[0][0],
            rubikCube.getFace(Face.LEFT.value)[2][2]
        )
        val rightDownFrontCorner = charArrayOf(
            rubikCube.getFace(Face.FRONT.value)[2][2],
            rubikCube.getFace(Face.DOWN.value)[0][2],
            rubikCube.getFace(Face.RIGHT.value)[2][0]
        )

        val leftUPBackCorner = charArrayOf(
            rubikCube.getFace(Face.BACK.value)[0][2],
            rubikCube.getFace(Face.UP.value)[0][0],
            rubikCube.getFace(Face.LEFT.value)[0][0]
        )

        val rightUPBackCorner = charArrayOf(
            rubikCube.getFace(Face.BACK.value)[0][0],
            rubikCube.getFace(Face.UP.value)[0][2],
            rubikCube.getFace(Face.RIGHT.value)[0][2]
        )

        val leftDownBackCorner = charArrayOf(
            rubikCube.getFace(Face.BACK.value)[2][2],
            rubikCube.getFace(Face.DOWN.value)[2][0],
            rubikCube.getFace(Face.LEFT.value)[2][0]
        )

        val rightDownBackCorner = charArrayOf(
            rubikCube.getFace(Face.BACK.value)[2][0],
            rubikCube.getFace(Face.DOWN.value)[2][2],
            rubikCube.getFace(Face.RIGHT.value)[2][2]
        )

        if (!checkCornerDifference(leftUPFrontCorner)) {
            throw RubikCubeError("Left up front corner color is not correct ${leftUPFrontCorner[0]} ${leftUPFrontCorner[1]} ${leftUPFrontCorner[2]} must be different")
        }

        if(!checkCornerDifference(rightUPFrontCorner)) {
            throw RubikCubeError("Right up front corner color is not correct ${rightUPFrontCorner[0]} ${rightUPFrontCorner[1]} ${rightUPFrontCorner[2]} must be different")
        }

        if(!checkCornerDifference(leftDownFrontCorner)) {
            throw RubikCubeError("Left down front corner color is not correct ${leftDownFrontCorner[0]} ${leftDownFrontCorner[1]} ${leftDownFrontCorner[2]} must be different")
        }

        if(!checkCornerDifference(rightDownFrontCorner)) {
            throw RubikCubeError("Right down front corner color is not correct ${rightDownFrontCorner[0]} ${rightDownFrontCorner[1]} ${rightDownFrontCorner[2]} must be different")
        }

        if(!checkCornerDifference(leftUPBackCorner)) {
            throw RubikCubeError("Left up back corner color is not correct ${leftUPBackCorner[0]} ${leftUPBackCorner[1]} ${leftUPBackCorner[2]} must be different")
        }

        if(!checkCornerDifference(rightUPBackCorner)) {
            throw RubikCubeError("Right up back corner color is not correct ${rightUPBackCorner[0]} ${rightUPBackCorner[1]} ${rightUPBackCorner[2]} must be different")
        }

        if(!checkCornerDifference(leftDownBackCorner)) {
            throw RubikCubeError("Left down back corner color is not correct ${leftDownBackCorner[0]} ${leftDownBackCorner[1]} ${leftDownBackCorner[2]} must be different")
        }

        if(!checkCornerDifference(rightDownBackCorner)) {
            throw RubikCubeError("Right down back corner color is not correct ${rightDownBackCorner[0]} ${rightDownBackCorner[1]} ${rightDownBackCorner[2]} must be different")
        }

        return true
    }

    private fun checkCornerDifference(corner: CharArray): Boolean {
        return corner[0] != corner[1] && corner[0] != corner[2] && corner[1] != corner[2]
    }

    private fun checkEdgesDifference(a: Char, b: Char): Boolean {
        return a != b
    }
    private fun checkCenterDifference(a: Char, b: Char, c: Char, d: Char, e: Char, f: Char): Boolean {
        return a != b && c != d && e != f && a != c && b != d
    }
}