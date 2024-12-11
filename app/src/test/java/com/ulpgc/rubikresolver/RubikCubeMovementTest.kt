package com.ulpgc.rubikresolver

import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.services.RubikCubeMovement
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RubikCubeMovementTest {
    @Before
    fun setUp() {
        RubikCube.RubikBuilder.reset()
    }

    @Test
    fun movementUTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"U")
        val expected = "UUUUUUUUUBBBRRRRRRRRRFFFFFFDDDDDDDDDFFFLLLLLLLLLBBBBBB"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementUInverseTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"U'")
        val expected = "UUUUUUUUUFFFRRRRRRLLLFFFFFFDDDDDDDDDBBBLLLLLLRRRBBBBBB"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementDTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"D")
        val expected = "UUUUUUUUURRRRRRFFFFFFFFFLLLDDDDDDDDDLLLLLLBBBBBBBBBRRR"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementDInverseTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"D'")
        val expected = "UUUUUUUUURRRRRRBBBFFFFFFRRRDDDDDDDDDLLLLLLFFFBBBBBBLLL"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementLTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"L")
        val expected = "BUUBUUBUURRRRRRRRRUFFUFFUFFFDDFDDFDDLLLLLLLLLBBDBBDBBD"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementLInverseTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"L'")
        val expected = "FUUFUUFUURRRRRRRRRDFFDFFDFFBDDBDDBDDLLLLLLLLLBBUBBUBBU"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementRTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"R")
        val expected = "UUFUUFUUFRRRRRRRRRFFDFFDFFDDDBDDBDDBLLLLLLLLLUBBUBBUBB"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementRInverseTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"R'")
        val expected = "UUBUUBUUBRRRRRRRRRFFUFFUFFUDDFDDFDDFLLLLLLLLLDBBDBBDBB"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementFTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"F")
        val expected = "UUUUUULLLURRURRURRFFFFFFFFFRRRDDDDDDLLDLLDLLDBBBBBBBBB"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementFInverseTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"F'")
        val expected = "UUUUUURRRDRRDRRDRRFFFFFFFFFLLLDDDDDDLLULLULLUBBBBBBBBB"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementBTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"B")
        val expected = "RRRUUUUUURRDRRDRRDFFFFFFFFFDDDDDDLLLULLULLULLBBBBBBBBB"
        assertEquals(expected,movedCube.toString())
    }

    @Test
    fun movementBInverseTest() {
        val initialState = "UUUUUUUUURRRRRRRRRFFFFFFFFFDDDDDDDDDLLLLLLLLLBBBBBBBBB"
        val cube = RubikCube.RubikBuilder.stringToCube(initialState).build()
        val movedCube = RubikCubeMovement.applyMove(cube,"B'")
        val expected = "LLLUUUUUURRURRURRUFFFFFFFFFDDDDDDRRRDLLDLLDLLBBBBBBBBB"
        assertEquals(expected,movedCube.toString())
    }
}