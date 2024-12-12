package com.ulpgc.rubikresolver.services

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.services.javaRubikSolver.Search

class RubikSolver : RubikCubeSolver {
    override fun solve(cube: RubikCube): List<String> {
        val string = solveToString(cube)
        return string.split(" ").filter { it != "" }
    }

    override fun solveToString(cube: RubikCube): String {
        var stringCube = cube.toString()
        val search = Search().solution(stringCube,  1000, 1000, 0, 0)
        return search.toString()
    }

}

fun arrayOfCharToColors(array: Array<Array<Char>>): Array<Array<MutableState<Color>>> {
    return array.map { row ->
        row.map { color ->
            when (color) {
                'U' -> mutableStateOf(Color.White)
                'R' -> mutableStateOf(Color.Blue)
                'F' -> mutableStateOf(Color.Red)
                'D' -> mutableStateOf(Color.Yellow)
                'L' -> mutableStateOf(Color.Green)
                'B' -> mutableStateOf(Color(0xFFFF9800))
                else -> mutableStateOf(Color.Gray)
            }
        }.toTypedArray()
    }.toTypedArray()
}


fun arrayOfColorToChar(array: Array<Array<MutableState<Color>>>): Array<Array<MutableState<Char>>> {
    return array.map { row ->
        row.map { color ->
            when (color.value) {
                Color.White -> mutableStateOf('U')
                Color.Blue -> mutableStateOf('R')
                Color.Red -> mutableStateOf('F')
                Color.Yellow -> mutableStateOf('D')
                Color.Green -> mutableStateOf('L')
                Color(0xFFFF9800) -> mutableStateOf('B')
                else -> mutableStateOf('F')
            }
        }.toTypedArray()
    }.toTypedArray()
}

fun arrayOfCharColorToColor(array: Array<Array<MutableState<Char>>>): Array<Array<MutableState<Color>>> {
    return array.map { row: Array<MutableState<Char>> ->
        row.map { colorState: MutableState<Char> ->
            when (colorState.value) {
                'w' -> mutableStateOf(Color.White)
                'b' -> mutableStateOf(Color.Blue)
                'r' -> mutableStateOf(Color.Red)
                'y' -> mutableStateOf(Color.Yellow)
                'g' -> mutableStateOf(Color.Green)
                'o' -> mutableStateOf(Color(0xFFFF9800)) // Orange
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

fun fillFace(face: Array<Char>): String {
    return face.toMutableList() // Convertir a lista mutable
    .apply {
        while (size < 9) {
            add('x') // Caracter con el que se rellena
        }
    }
    .toTypedArray().joinToString("")
}
