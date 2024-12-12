package com.ulpgc.rubikresolver

import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.services.RubikSolver
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RubikSolverTest {
    @Before
    fun setUp() {
        RubikCube.RubikBuilder.reset()
    }

    @Test
    fun solvedCubeTest() {
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

        val solver = RubikSolver()
        val solution = solver.solveToString(cube)
        val solutionExpected = ""
        assertEquals(solutionExpected,solution)
    }

    @Test
    fun unsolvedOneMoveCubeTest() {
        val cube = RubikCube.RubikBuilder
            .setFace(RubikCube.Face.UP.value, arrayOf(
                arrayOf('U', 'U', 'F'),
                arrayOf('U', 'U', 'F'),
                arrayOf('U', 'U', 'F')))
            .setFace(RubikCube.Face.FRONT.value, arrayOf(
                arrayOf('F', 'F', 'D'),
                arrayOf('F', 'F', 'D'),
                arrayOf('F', 'F', 'D')))
            .setFace(RubikCube.Face.DOWN.value, arrayOf(
                arrayOf('D', 'D', 'B'),
                arrayOf('D', 'D', 'B'),
                arrayOf('D', 'D', 'B')))
            .setFace(RubikCube.Face.BACK.value, arrayOf(
                arrayOf('U', 'B', 'B'),
                arrayOf('U', 'B', 'B'),
                arrayOf('U', 'B', 'B')))
            .setFace(RubikCube.Face.RIGHT.value, arrayOf(
                arrayOf('R', 'R', 'R'),
                arrayOf('R', 'R', 'R'),
                arrayOf('R', 'R', 'R')))
            .setFace(RubikCube.Face.LEFT.value, arrayOf(
                arrayOf('L', 'L', 'L'),
                arrayOf('L', 'L', 'L'),
                arrayOf('L', 'L', 'L')))
            .build()
        print(cube.getFace(RubikCube.Face.BACK.value).flatten())
        val solver = RubikSolver()
        val solution = solver.solveToString(cube)
        val solutionExpected = "R' "
        assertEquals(solutionExpected,solution)
    }

    @Test
    fun stringSolvedCubeTest() {
        val solved = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(solved).build()
        assertEquals(solved,cube.toString())

        val solver = RubikSolver().solveToString(cube)
        val solutionExpected = ""
        assertEquals(solutionExpected,solver)
    }

    @Test
    fun stringUnSolveDCubeTest() {
        val stringCube = "DUUBULDBFRBFRRULLLBRDFFFBLURDBFDFDRFRULBLUFDURRBLBDUDL"
        val cube = RubikCube.RubikBuilder
            .stringToCube(stringCube)
            .build()

        assertEquals(stringCube,cube.toString())

        val solver = RubikSolver().solveToString(cube)
        val solutionExpected = "R2 U2 B2 L2 F2 U' L2 R2 B2 R2 D B2 F L' F U2 F' R' D' L2 R' "
        assertEquals(solutionExpected,solver)
    }

}