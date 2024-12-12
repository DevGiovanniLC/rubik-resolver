package com.ulpgc.rubikresolver.services

import com.ulpgc.rubikresolver.model.RubikCube
import com.ulpgc.rubikresolver.model.RubikCube.Face.BACK
import com.ulpgc.rubikresolver.model.RubikCube.Face.DOWN
import com.ulpgc.rubikresolver.model.RubikCube.Face.FRONT
import com.ulpgc.rubikresolver.model.RubikCube.Face.LEFT
import com.ulpgc.rubikresolver.model.RubikCube.Face.RIGHT
import com.ulpgc.rubikresolver.model.RubikCube.Face.UP

class RubikCubeMovement {
    companion object {
        fun applyMove(cube: RubikCube, move: String): RubikCube {
            val front = cube.getFace(FRONT.value)
            val up = cube.getFace(UP.value)
            val right = cube.getFace(RIGHT.value)
            val back = cube.getFace(BACK.value)
            val down = cube.getFace(DOWN.value)
            val left = cube.getFace(LEFT.value)
            return when (move) {
                "U" -> RubikCube.RubikBuilder
                    .setFace(DOWN.value, down)
                    .setFace(UP.value, arrayOf(
                        arrayOf(
                            up[2][0],
                            up[1][0],
                            up[0][0],
                        ),
                        arrayOf(
                            up[2][1],
                            up[1][1],
                            up[0][1],
                        ),
                        arrayOf(
                            up[2][2],
                            up[1][2],
                            up[0][2],
                        ),
                    ))
                    .setFace(FRONT.value, arrayOf(
                        right[0],
                        front[1],
                        front[2],
                    ))
                    .setFace(RIGHT.value, arrayOf(
                        back[0],
                        right[1],
                        right[2],
                    ))
                    .setFace(BACK.value, arrayOf(
                        left[0],
                        back[1],
                        back[2],
                    ))
                    .setFace(LEFT.value, arrayOf(
                        front[0],
                        left[1],
                        left[2],
                    ))
                    .build()

                "U'" -> RubikCube.RubikBuilder
                    .setFace(DOWN.value, down)
                    .setFace(UP.value, arrayOf(
                        arrayOf(
                            up[0][2],
                            up[1][2],
                            up[2][2],
                            ),
                        arrayOf(
                            up[0][1],
                            up[1][1],
                            up[2][1],
                            ),
                        arrayOf(
                            up[0][0],
                            up[1][0],
                            up[2][0],
                            ),
                    ))
                    .setFace(FRONT.value, arrayOf(
                        left[0],
                        front[1],
                        front[2],
                    ))
                    .setFace(RIGHT.value, arrayOf(
                        front[0],
                        right[1],
                        right[2],
                    ))
                    .setFace(BACK.value, arrayOf(
                        right[0],
                        back[1],
                        back[2],
                    ))
                    .setFace(LEFT.value, arrayOf(
                        back[0],
                        left[1],
                        left[2],
                    ))
                    .build()

                "D" -> RubikCube.RubikBuilder
                    .setFace(UP.value, up)
                    .setFace(DOWN.value, arrayOf(
                        arrayOf(
                            down[2][0],
                            down[1][0],
                            down[0][0],
                        ),
                        arrayOf(
                            down[2][1],
                            down[1][1],
                            down[0][1],
                        ),
                        arrayOf(
                            down[2][2],
                            down[1][2],
                            down[0][2],
                        ),
                    ))
                    .setFace(FRONT.value, arrayOf(
                        front[0],
                        front[1],
                        left[2],
                    ))
                    .setFace(RIGHT.value, arrayOf(
                        right[0],
                        right[1],
                        front[2],
                    ))
                    .setFace(BACK.value, arrayOf(
                        back[0],
                        back[1],
                        right[2],
                    ))
                    .setFace(LEFT.value, arrayOf(
                        left[0],
                        left[1],
                        back[2],
                    ))
                    .build()

                "D'" -> RubikCube.RubikBuilder
                    .setFace(UP.value, up)
                    .setFace(DOWN.value, arrayOf(
                        arrayOf(
                            down[0][2],
                            down[1][2],
                            down[2][2],
                        ),
                        arrayOf(
                            down[0][1],
                            down[1][1],
                            down[2][1],
                        ),
                        arrayOf(
                            down[0][0],
                            down[1][0],
                            down[2][0],
                        ),
                    ))
                    .setFace(FRONT.value, arrayOf(
                        front[0],
                        front[1],
                        right[2],
                    ))
                    .setFace(RIGHT.value, arrayOf(
                        right[0],
                        right[1],
                        back[2],
                    ))
                    .setFace(BACK.value, arrayOf(
                        back[0],
                        back[1],
                        left[2],
                    ))
                    .setFace(LEFT.value, arrayOf(
                        left[0],
                        left[1],
                        front[2],
                    ))
                    .build()

                "L" -> RubikCube.RubikBuilder
                    .setFace(RIGHT.value, right)
                    .setFace(LEFT.value, arrayOf(
                        arrayOf(
                            left[2][0],
                            left[1][0],
                            left[0][0],
                        ),
                        arrayOf(
                            left[2][1],
                            left[1][1],
                            left[0][1],
                        ),
                        arrayOf(
                            left[2][2],
                            left[1][2],
                            left[0][2],
                        ),
                    ))
                    .setFace(UP.value, arrayOf(
                        arrayOf(
                            back[2][2],
                            up[0][1],
                            up[0][2],
                        ),
                        arrayOf(
                            back[1][2],
                            up[1][1],
                            up[1][2],
                        ),
                        arrayOf(
                            back[0][2],
                            up[2][1],
                            up[2][2],
                        ),
                    ))
                    .setFace(DOWN.value, arrayOf(
                        arrayOf(
                            front[0][0],
                            down[0][1],
                            down[0][2],
                        ),
                        arrayOf(
                            front[1][0],
                            down[1][1],
                            down[1][2],
                        ),
                        arrayOf(
                            front[2][0],
                            down[2][1],
                            down[2][2],
                        ),
                    ))
                    .setFace(FRONT.value, arrayOf(
                        arrayOf(
                            up[0][0],
                            front[0][1],
                            front[0][2],
                        ),
                        arrayOf(
                            up[1][0],
                            front[1][1],
                            front[1][2],
                        ),
                        arrayOf(
                            up[2][0],
                            front[2][1],
                            front[2][2],
                        ),
                    ))
                    .setFace(BACK.value, arrayOf(
                        arrayOf(
                            back[0][0],
                            back[0][1],
                            down[2][0],
                        ),
                        arrayOf(
                            back[1][0],
                            back[1][1],
                            down[1][0],
                        ),
                        arrayOf(
                            back[2][0],
                            back[2][1],
                            down[0][0],
                        ),
                    ))
                    .build()

                "L'" ->
                    RubikCube.RubikBuilder
                    .setFace(RIGHT.value, right)
                    .setFace(LEFT.value, arrayOf(
                        arrayOf(
                            left[0][2],
                            left[1][2],
                            left[2][2],
                        ),
                        arrayOf(
                            left[0][1],
                            left[1][1],
                            left[2][1],
                        ),
                        arrayOf(
                            left[0][0],
                            left[1][0],
                            left[2][0],
                        ),
                    ))
                    .setFace(UP.value, arrayOf(
                        arrayOf(
                            front[0][0],
                            up[0][1],
                            up[0][2],
                        ),
                        arrayOf(
                            front[1][0],
                            up[1][1],
                            up[1][2],
                        ),
                        arrayOf(
                            front[2][0],
                            up[2][1],
                            up[2][2],
                        ),
                    ))
                    .setFace(DOWN.value, arrayOf(
                        arrayOf(
                            back[2][2],
                            down[0][1],
                            down[0][2],
                        ),
                        arrayOf(
                            back[1][2],
                            down[1][1],
                            down[1][2],
                        ),
                        arrayOf(
                            back[0][2],
                            down[2][1],
                            down[2][2],
                        ),
                    ))
                    .setFace(FRONT.value,
                        arrayOf(
                        arrayOf(
                            down[0][0],
                            front[0][1],
                            front[0][2],
                        ),
                        arrayOf(
                            down[1][0],
                            front[1][1],
                            front[1][2],
                        ),
                        arrayOf(
                            down[2][0],
                            front[2][1],
                            front[2][2],
                        ),
                    ))
                    .setFace(BACK.value, arrayOf(
                        arrayOf(
                            back[0][0],
                            back[0][1],
                            up[2][0],
                        ),
                        arrayOf(
                            back[1][0],
                            back[1][1],
                            up[1][0],
                        ),
                        arrayOf(
                            back[2][0],
                            back[2][1],
                            up[0][0],
                        ),
                    ))
                    .build()

                "R" -> RubikCube.RubikBuilder
                    .setFace(LEFT.value, left)
                    .setFace(RIGHT.value,
                        arrayOf(
                            arrayOf(
                                right[2][0],
                                right[1][0],
                                right[0][0],
                            ),
                            arrayOf(
                                right[2][1],
                                right[1][1],
                                right[0][1],
                            ),
                            arrayOf(
                                right[2][2],
                                right[1][2],
                                right[0][2],
                            ),
                        ))
                    .setFace(UP.value, arrayOf(
                        arrayOf(
                            up[0][0],
                            up[0][1],
                            front[0][2],
                        ),
                        arrayOf(
                            up[1][0],
                            up[1][1],
                            front[1][2],
                        ),
                        arrayOf(
                            up[2][0],
                            up[2][1],
                            front[2][2],
                        ),
                    ))
                    .setFace(DOWN.value, arrayOf(
                        arrayOf(
                            down[0][0],
                            down[0][1],
                            back[2][0],
                        ),
                        arrayOf(
                            down[1][0],
                            down[1][1],
                            back[1][0],
                        ),
                        arrayOf(
                            down[2][0],
                            down[2][1],
                            back[0][0],
                        ),
                    ))
                    .setFace(FRONT.value, arrayOf(
                        arrayOf(
                            front[0][0],
                            front[0][1],
                            down[0][2],
                        ),
                        arrayOf(
                            front[1][0],
                            front[1][1],
                            down[1][2],
                        ),
                        arrayOf(
                            front[2][0],
                            front[2][1],
                            down[2][2],
                        ),
                    ))
                    .setFace(BACK.value, arrayOf(
                        arrayOf(
                            up[2][2],
                            back[0][1],
                            back[0][2],
                        ),
                        arrayOf(
                            up[1][2],
                            back[1][1],
                            back[1][2],
                        ),
                        arrayOf(
                            up[0][2],
                            back[2][1],
                            back[2][2],
                        ),
                    ))
                    .build()

                "R'" -> RubikCube.RubikBuilder
                    .setFace(LEFT.value, left)
                    .setFace(RIGHT.value,
                        arrayOf(
                            arrayOf(
                                right[0][2],
                                right[1][2],
                                right[2][2],
                            ),
                            arrayOf(
                                right[0][1],
                                right[1][1],
                                right[2][1],
                            ),
                            arrayOf(
                                right[0][0],
                                right[1][0],
                                right[2][0],
                            ),
                        ))
                    .setFace(UP.value, arrayOf(
                        arrayOf(
                            up[0][0],
                            up[0][1],
                            back[2][0],
                        ),
                        arrayOf(
                            up[1][0],
                            up[1][1],
                            back[1][0],
                        ),
                        arrayOf(
                            up[2][0],
                            up[2][1],
                            back[0][0],
                        ),
                    ))
                    .setFace(DOWN.value, arrayOf(
                        arrayOf(
                            down[0][0],
                            down[0][1],
                            front[0][2],
                        ),
                        arrayOf(
                            down[1][0],
                            down[1][1],
                            front[1][2],
                        ),
                        arrayOf(
                            down[2][0],
                            down[2][1],
                            front[2][2],
                        ),
                    ))
                    .setFace(FRONT.value, arrayOf(
                        arrayOf(
                            front[0][0],
                            front[0][1],
                            up[0][2],
                        ),
                        arrayOf(
                            front[1][0],
                            front[1][1],
                            up[1][2],
                        ),
                        arrayOf(
                            front[2][0],
                            front[2][1],
                            up[2][2],
                        ),
                    ))
                    .setFace(BACK.value, arrayOf(
                        arrayOf(
                            down[2][2],
                            back[0][1],
                            back[0][2],
                        ),
                        arrayOf(
                            down[1][2],
                            back[1][1],
                            back[1][2],
                        ),
                        arrayOf(
                            down[0][2],
                            back[2][1],
                            back[2][2],
                        ),
                    ))
                    .build()

                "F" -> RubikCube.RubikBuilder
                    .setFace(BACK.value, back)
                    .setFace(FRONT.value, arrayOf(
                        arrayOf(
                            front[2][0],
                            front[1][0],
                            front[0][0],
                        ),
                        arrayOf(
                            front[2][1],
                            front[1][1],
                            front[0][1],
                        ),
                        arrayOf(
                            front[2][2],
                            front[1][2],
                            front[0][2],
                        ),
                    ))
                    .setFace(UP.value, arrayOf(
                        up[0],
                        up[1],
                        arrayOf(
                            left[2][2],
                            left[1][2],
                            left[0][2],
                        ),
                    ))
                    .setFace(DOWN.value, arrayOf(
                        arrayOf(
                            right[2][0],
                            right[1][0],
                            right[0][0],
                        ),
                        down[1],
                        down[2],
                    ))
                    .setFace(RIGHT.value, arrayOf(
                        arrayOf(
                            up[2][0],
                            right[0][1],
                            right[0][2],
                        ),
                        arrayOf(
                            up[2][1],
                            right[1][1],
                            right[1][2],
                        ),
                        arrayOf(
                            up[2][2],
                            right[2][1],
                            right[2][2],
                        ),
                    ))
                    .setFace(LEFT.value, arrayOf(
                        arrayOf(
                            left[0][0],
                            left[0][1],
                            down[0][0],
                        ),
                        arrayOf(
                            left[1][0],
                            left[1][1],
                            down[0][1],
                        ),
                        arrayOf(
                            left[2][0],
                            left[2][1],
                            down[0][2],
                        ),
                    ))
                    .build()

                "F'" -> RubikCube.RubikBuilder
                    .setFace(BACK.value, back)
                    .setFace(FRONT.value, arrayOf(
                        arrayOf(
                            front[0][2],
                            front[1][2],
                            front[2][2],
                        ),
                        arrayOf(
                            front[0][1],
                            front[1][1],
                            front[2][1],
                        ),
                        arrayOf(
                            front[0][0],
                            front[1][0],
                            front[2][0],
                        ),
                    ))
                    .setFace(UP.value, arrayOf(
                        up[0],
                        up[1],
                        arrayOf(
                            right[0][0],
                            right[1][0],
                            right[2][0],
                        ),
                    ))
                    .setFace(DOWN.value, arrayOf(
                        arrayOf(
                            left[0][2],
                            left[1][2],
                            left[2][2],
                        ),
                        down[1],
                        down[2],
                    ))
                    .setFace(RIGHT.value, arrayOf(
                        arrayOf(
                            down[0][2],
                            right[0][1],
                            right[0][2],
                        ),
                        arrayOf(
                            down[0][1],
                            right[1][1],
                            right[1][2],
                        ),
                        arrayOf(
                            down[0][0],
                            right[2][1],
                            right[2][2],
                        ),
                    ))
                    .setFace(LEFT.value, arrayOf(
                        arrayOf(
                            left[0][0],
                            left[0][1],
                            up[2][2],
                        ),
                        arrayOf(
                            left[1][0],
                            left[1][1],
                            up[2][1],
                        ),
                        arrayOf(
                            left[2][0],
                            left[2][1],
                            up[2][0],
                        ),
                    ))
                    .build()

                "B" -> RubikCube.RubikBuilder
                    .setFace(FRONT.value, front)
                    .setFace(BACK.value, arrayOf(
                        arrayOf(
                            back[2][0],
                            back[1][0],
                            back[0][0],
                        ),
                        arrayOf(
                            back[2][1],
                            back[1][1],
                            back[0][1],
                        ),
                        arrayOf(
                            back[2][2],
                            back[1][2],
                            back[0][2],
                        ),
                    ))
                    .setFace(UP.value, arrayOf(
                        arrayOf(
                            right[0][2],
                            right[1][2],
                            right[2][2],
                        ),
                        up[1],
                        up[2],
                    ))
                    .setFace(DOWN.value, arrayOf(
                        down[0],
                        down[1],
                        arrayOf(
                            left[0][0],
                            left[1][0],
                            left[2][0],
                        ),
                    ))
                    .setFace(RIGHT.value, arrayOf(
                        arrayOf(
                            right[0][0],
                            right[0][1],
                            down[2][2],
                        ),
                        arrayOf(
                            right[1][0],
                            right[1][1],
                            down[2][1],
                        ),
                        arrayOf(
                            right[2][0],
                            right[2][1],
                            down[2][0],
                        ),
                    ))
                    .setFace(LEFT.value, arrayOf(
                        arrayOf(
                            up[0][2],
                            left[0][1],
                            left[0][2],
                        ),
                        arrayOf(
                            up[0][1],
                            left[1][1],
                            left[1][2],
                        ),
                        arrayOf(
                            up[0][0],
                            left[2][1],
                            left[2][2],
                        ),
                    ))
                    .build()

                "B'" -> RubikCube.RubikBuilder
                    .setFace(FRONT.value, front)
                    .setFace(BACK.value, arrayOf(
                        arrayOf(
                            back[0][2],
                            back[1][2],
                            back[2][2],
                        ),
                        arrayOf(
                            back[0][1],
                            back[1][1],
                            back[2][1],
                        ),
                        arrayOf(
                            back[0][0],
                            back[1][0],
                            back[2][0],
                        ),
                    ))
                    .setFace(UP.value, arrayOf(
                        arrayOf(
                            left[2][0],
                            left[1][0],
                            left[0][0],
                        ),
                        up[1],
                        up[2],
                    ))
                    .setFace(DOWN.value, arrayOf(
                        down[0],
                        down[1],
                        arrayOf(
                            right[2][2],
                            right[1][2],
                            right[0][2],
                        ),
                    ))
                    .setFace(RIGHT.value, arrayOf(
                        arrayOf(
                            right[0][0],
                            right[0][1],
                            up[0][0],
                        ),
                        arrayOf(
                            right[1][0],
                            right[1][1],
                            up[0][1],
                        ),
                        arrayOf(
                            right[2][0],
                            right[2][1],
                            up[0][2],
                        ),
                    ))
                    .setFace(LEFT.value, arrayOf(
                        arrayOf(
                            down[2][0],
                            left[0][1],
                            left[0][2],
                        ),
                        arrayOf(
                            down[2][1],
                            left[1][1],
                            left[1][2],
                        ),
                        arrayOf(
                            down[2][2],
                            left[2][1],
                            left[2][2],
                        ),
                    ))
                    .build()

                else -> {
                    cube
                }
            }
        }
    }

}
