package com.ulpgc.rubikresolver

import com.ulpgc.rubikresolver.model.RubikCube
import org.junit.Assert.assertEquals
import org.junit.Test

class RubikCubeTest {
    @Test
    fun toStringTest() {
        val cube = RubikCube.RubikBuilder
            .setFace(RubikCube.Face.FRONT, arrayOf(
                arrayOf('R', 'R', 'R'),
                arrayOf('R', 'R', 'R'),
                arrayOf('R', 'R', 'R')
            ))
            .setFace(RubikCube.Face.BACK, arrayOf(
                arrayOf('O', 'O', 'O'),
                arrayOf('O', 'O', 'O'),
                arrayOf('O', 'O', 'O')
            ))
            .setFace(RubikCube.Face.LEFT, arrayOf(
                arrayOf('B', 'B', 'B'),
                arrayOf('B', 'B', 'B'),
                arrayOf('B', 'B', 'B')
            ))
            .setFace(RubikCube.Face.RIGHT, arrayOf(
                arrayOf('G', 'G', 'G'),
                arrayOf('G', 'G', 'G'),
                arrayOf('G', 'G', 'G')
            ))
            .setFace(RubikCube.Face.TOP, arrayOf(
                arrayOf('Y', 'Y', 'Y'),
                arrayOf('Y', 'Y', 'Y'),
                arrayOf('Y', 'Y', 'Y')
            ))
            .setFace(RubikCube.Face.BOTTOM, arrayOf(
                arrayOf('W', 'W', 'W'),
                arrayOf('W', 'W', 'W'),
                arrayOf('W', 'W', 'W')
            ))
            .build()
        assertEquals("RRRRRRRRROOOOOOOOOBBBBBBBBBGGGGGGGGGYYYYYYYYYWWWWWWWWW", cube.toString())
    }

    fun toStringTest2() {
        val cube = RubikCube.RubikBuilder
            .setFace(RubikCube.Face.FRONT, arrayOf(
                arrayOf('G', 'G', 'G'),
                arrayOf('O', 'R', 'R'),
                arrayOf('G', 'W', 'W')
            ))
            .setFace(RubikCube.Face.BACK, arrayOf(
                arrayOf('W', 'Y', 'W'),
                arrayOf('G', 'O', 'G'),
                arrayOf('R', 'B', 'B')
            ))
            .setFace(RubikCube.Face.LEFT, arrayOf(
                arrayOf('G', 'W', 'Y'),
                arrayOf('Y', 'B', 'G'),
                arrayOf('O', 'B', 'W')
            ))
            .setFace(RubikCube.Face.RIGHT, arrayOf(
                arrayOf('Y', 'R', 'B'),
                arrayOf('B', 'G', 'W'),
                arrayOf('O', 'O', 'Y')
            ))
            .setFace(RubikCube.Face.TOP, arrayOf(
                arrayOf('R', 'O', 'R'),
                arrayOf('O', 'Y', 'Y'),
                arrayOf('O', 'R', 'R')
            ))
            .setFace(RubikCube.Face.BOTTOM, arrayOf(
                arrayOf('O', 'R', 'B'),
                arrayOf('W', 'W', 'B'),
                arrayOf('Y', 'Y', 'B')
            ))
            .build()
        assertEquals("GGGORRGWWWYWGOGRBBGWYYBGOBWYRBBGWOOYROROYYORRORBWWBYYB", cube.toString())
    }
}