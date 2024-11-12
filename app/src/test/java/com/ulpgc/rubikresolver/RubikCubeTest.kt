package com.ulpgc.rubikresolver

import com.ulpgc.rubikresolver.data.RubikCube
import org.junit.Assert.assertEquals
import org.junit.Test

class RubikCubeTest {
    @Test
    fun toStringTest() {
        val cube = RubikCube(
            arrayOf(
                arrayOf(
                    arrayOf('R', 'R', 'R'),
                    arrayOf('R', 'R', 'R'),
                    arrayOf('R', 'R', 'R')
                ),
                arrayOf(
                    arrayOf('O', 'O', 'O'),
                    arrayOf('O', 'O', 'O'),
                    arrayOf('O', 'O', 'O')
                ),
                arrayOf(
                    arrayOf('B', 'B', 'B'),
                    arrayOf('B', 'B', 'B'),
                    arrayOf('B', 'B', 'B')
                ),
                arrayOf(
                    arrayOf('G', 'G', 'G'),
                    arrayOf('G', 'G', 'G'),
                    arrayOf('G', 'G', 'G')
                ),
                arrayOf(
                    arrayOf('Y', 'Y', 'Y'),
                    arrayOf('Y', 'Y', 'Y'),
                    arrayOf('Y', 'Y', 'Y')
                ),
                arrayOf(
                    arrayOf('W', 'W', 'W'),
                    arrayOf('W', 'W', 'W'),
                    arrayOf('W', 'W', 'W')
                )
            )
        )
        assertEquals("RRRRRRRRROOOOOOOOOBBBBBBBBBGGGGGGGGGYYYYYYYYYWWWWWWWWW", cube.toString())
    }
}