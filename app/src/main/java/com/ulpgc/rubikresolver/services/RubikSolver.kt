package com.ulpgc.rubikresolver.services

import androidx.compose.ui.graphics.Color
import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.services.javaRubikSolver.Search

class RubikSolver : RubikCubeSolver {
    override fun solve(cube: RubikCube): List<String> {
        val string = solveToString(cube)
        return string.split(" ")
    }

    override fun solveToString(cube: RubikCube): String {
        var stringCube = cube.toString()
        val search = Search().solution(stringCube,  1000, 1000, 0, 0)
        return search.toString()
    }

}

fun arrayOfCharToColors(array: Array<Array<Char>>): Array<Array<Color>> {
    return array.map { row ->
        row.map { color ->
            when (color) {
                'U' -> Color.White
                'R' -> Color.Blue
                'F' -> Color.Red
                'D' -> Color.Yellow
                'L' -> Color.Green
                'B' -> Color(0xFFFF9800)
                else -> Color.Black
            }
        }.toTypedArray()
    }.toTypedArray()
}

fun arrayOfColorToChar(array: Array<Array<Color>>): Array<Array<Char>> {
    return array.map { row ->
        row.map { color ->
            when (color) {
                Color.White -> 'U'
                Color.Blue -> 'R'
                Color.Red -> 'F'
                Color.Yellow -> 'D'
                Color.Green -> 'L'
                Color(0xFFFF9800) -> 'B'
                else -> ' '
            }
        }.toTypedArray()
    }.toTypedArray()
}