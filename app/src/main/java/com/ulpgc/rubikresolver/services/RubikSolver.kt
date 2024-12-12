package com.ulpgc.rubikresolver.services

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.services.javaRubikSolver.Search

class RubikSolver : RubikCubeSolver {
    override fun solve(cube: RubikCube): List<String> {
        val string = solveToString(cube)
        return string.split(" ").filter { it.isNotEmpty() }
    }

    override fun solveToString(cube: RubikCube): String {
        val stringCube = cube.toString()
        val search = Search().solution(stringCube,  1000, 1000, 0, 0)
        return search.toString()
    }

}

fun arrayOfCharToColors(array: Array<Array<Char>>): Array<Array<MutableState<Color>>> {
    return array.map { row ->
        row.map { color ->
            when (color) {
                'D' -> mutableStateOf(Color.White)
                'L' -> mutableStateOf(Color.Blue)
                'F' -> mutableStateOf(Color.Red)
                'U' -> mutableStateOf(Color.Yellow)
                'R' -> mutableStateOf(Color.Green)
                'B' -> mutableStateOf(Color(0xFFFF9800))
                else -> mutableStateOf(Color.Gray)
            }
        }.toTypedArray()
    }.toTypedArray()
}

fun arrayOfCharPositionToCharColor(array: Array<Array<MutableState<Char>>>): Array<Array<MutableState<Char>>> {
    return array.map { row ->
        row.map { charState ->
            when (charState.value) {
                'D' -> mutableStateOf('W')
                'L' -> mutableStateOf('B')
                'F' -> mutableStateOf('R')
                'U' -> mutableStateOf('Y')
                'R' -> mutableStateOf('G')
                'B' -> mutableStateOf('O')
                else -> mutableStateOf('X')
            }
        }.toTypedArray()
    }.toTypedArray()
}



fun arrayOfColorToChar(array: Array<Array<MutableState<Color>>>): Array<Array<MutableState<Char>>> {
    return array.map { row ->
        row.map { color ->
            when (color.value) {
                Color.White -> mutableStateOf('D')
                Color.Blue -> mutableStateOf('L')
                Color.Red -> mutableStateOf('F')
                Color.Yellow -> mutableStateOf('U')
                Color.Green -> mutableStateOf('R')
                Color(0xFFFF9800) -> mutableStateOf('B')
                else -> mutableStateOf('X')
            }
        }.toTypedArray()
    }.toTypedArray()
}

fun arrayOfCharColorToColor(array: Array<Array<MutableState<Char>>>): Array<Array<MutableState<Color>>> {
    return array.map { row: Array<MutableState<Char>> ->
        row.map { colorState: MutableState<Char> ->
            when (colorState.value) {
                'W' -> mutableStateOf(Color.White)
                'B' -> mutableStateOf(Color.Blue)
                'R' -> mutableStateOf(Color.Red)
                'Y' -> mutableStateOf(Color.Yellow)
                'G' -> mutableStateOf(Color.Green)
                'O' -> mutableStateOf(Color(0xFFFF9800)) // Orange
                else -> mutableStateOf(Color.Gray)
            }
        }.toTypedArray()
    }.toTypedArray()
}



fun faceToString(face: Array<Array<MutableState<Char>>>): String {
    return face.joinToString("\n") { row ->
        row.joinToString(" ") { charState -> charState.value.toString() }
    }
}


fun stringToMutableStateArray(string: String?): Array<Array<MutableState<Char>>> {
    if (string == null) return Array(3) { Array(3) { mutableStateOf(' ') } }

    val rows = string.split("\n").filter { it.isNotEmpty() }

    return Array(rows.size) { rowIndex ->
        rows[rowIndex].split(" ").map { char -> mutableStateOf(char[0]) }.toTypedArray()
    }
}

inline fun <reified T>removeMutableState(face: Array<Array<MutableState<T>>>): Array<Array<T>> {
    return face.map { row ->
        row.map { charState: MutableState<T> -> charState.value }
            .toTypedArray()
    }.toTypedArray()
}

inline fun <reified T>addMutableState(grid: Array<Array<T>>): Array<Array<MutableState<T>>> {
    return grid.map { row ->
        row.map { t ->
            mutableStateOf(t)
        }.toTypedArray()
    }.toTypedArray()
}

fun fillFace(face: Array<Char>): String {
    return face.toMutableList()
        .apply {
            while (size < 9) {
                add('X')
            }
        }
        .chunked(3)
        .joinToString("\n") { chunk -> chunk.joinToString(" ") }
}
