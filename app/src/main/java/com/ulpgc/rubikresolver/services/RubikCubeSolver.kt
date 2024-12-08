package com.ulpgc.rubikresolver.services

import com.ulpgc.rubikresolver.model.RubikCube

interface RubikCubeSolver {
    fun solve(cube: RubikCube): List<String>
    fun solveToString(cube: RubikCube): String
}