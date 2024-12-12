package com.ulpgc.rubikresolver


import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.model.RubikCubeError
import com.ulpgc.rubikresolver.model.RubikFaceError
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before


class RubikCubeTest {

    @Before
    fun setUp() {
        RubikCube.RubikBuilder.reset()
    }

    @Test(expected = RubikCubeError::class)
    fun toStringEmptyCubeErrorTest() {
        val cube = RubikCube.RubikBuilder.build()
    }

    @Test(expected = RubikFaceError::class)
    fun toStringEmptyFacesErrorTest() {
        val cube = RubikCube.RubikBuilder
            .setFace(RubikCube.Face.UP.value, arrayOf(
                arrayOf(' ', ' ', ' '),
                arrayOf(' ', ' ', ' '),
                arrayOf(' ', ' ', ' ')))
    }

    @Test(expected = RubikFaceError::class)
    fun toStringEmptyPartOfFaceErrorTest() {
        val cube = RubikCube.RubikBuilder
            .setFace(RubikCube.Face.UP.value, arrayOf(
                arrayOf('U', 'U', 'U'),
                arrayOf(' ', ' ', ' '),
                arrayOf(' ', ' ', ' ')))
    }

    @Test(expected = RubikCubeError::class)
    fun toStringOnlyOneFaceErrorTest() {
        val cube = RubikCube.RubikBuilder
            .setFace(RubikCube.Face.UP.value, arrayOf(
                arrayOf('U', 'U', 'U'),
                arrayOf('U', 'U', 'U'),
                arrayOf('U', 'U', 'U')))
            .build()
    }



    @Test
    fun toStringCorrectTest() {
        val cube = RubikCube.RubikBuilder
            .setFace(RubikCube.Face.UP.value, arrayOf(
                arrayOf('U', 'U', 'U'),
                arrayOf('U', 'U', 'U'),
                arrayOf('U', 'U', 'U')))
            .setFace(RubikCube.Face.RIGHT.value, arrayOf(
                arrayOf('R', 'R', 'R'),
                arrayOf('R', 'R', 'R'),
                arrayOf('R', 'R', 'R')))
            .setFace(RubikCube.Face.DOWN.value, arrayOf(
                arrayOf('D', 'D', 'D'),
                arrayOf('D', 'D', 'D'),
                arrayOf('D', 'D', 'D')))
            .setFace(RubikCube.Face.LEFT.value, arrayOf(
                arrayOf('L', 'L', 'L'),
                arrayOf('L', 'L', 'L'),
                arrayOf('L', 'L', 'L')))
            .setFace(RubikCube.Face.BACK.value, arrayOf(
                arrayOf('B', 'B', 'B'),
                arrayOf('B', 'B', 'B'),
                arrayOf('B', 'B', 'B')))
            .setFace(RubikCube.Face.FRONT.value, arrayOf(
                arrayOf('F', 'F', 'F'),
                arrayOf('F', 'F', 'F'),
                arrayOf('F', 'F', 'F')))
            .build()
        val expected = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        assertEquals(expected,cube.toString())

    }

}