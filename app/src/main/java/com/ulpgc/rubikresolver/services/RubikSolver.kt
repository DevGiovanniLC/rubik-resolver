package com.ulpgc.rubikresolver.services

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