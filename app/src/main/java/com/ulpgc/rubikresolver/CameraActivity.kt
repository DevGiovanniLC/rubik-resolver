package com.ulpgc.rubikresolver

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ulpgc.rubikresolver.components.CameraPreview
import com.ulpgc.rubikresolver.components.IconButton
import com.ulpgc.rubikresolver.services.fillFace
import com.ulpgc.rubikresolver.ui.theme.RubikResolverTheme
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.imgproc.Imgproc

@ExperimentalMaterial3Api
class CameraActivity : ComponentActivity() {
    private val detectedColors = mutableListOf<Char>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        var cubeFace = Array(3) { Array(3) { mutableStateOf('F') }}
        var cubeState = intent.getIntExtra("cubeState", 0)

        enableEdgeToEdge()
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(this, CAMERA_PERMISSIONS, 0)
        }

        setContent {
            CameraScreen(cubeFace, cubeState)
        }
    }

    @Composable
    private fun CameraScreen(cubeFace: Array<Array<MutableState<Char>>>, cubeState: Int) {
        RubikResolverTheme {
            val scaffoldState = rememberBottomSheetScaffoldState()
            val processedBitmapState = remember { mutableStateOf<Bitmap?>(null) }
            val controller = remember {
                LifecycleCameraController(applicationContext).apply {
                    setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                    setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(applicationContext))
                    { imageProxy ->
                        processImageProxy(imageProxy, processedBitmapState)
                    }
                }
            }
            val screenHeight = LocalConfiguration.current.screenHeightDp.dp
            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = 0.dp,
                sheetContent = {}
            ) {

                Surface(color = Color(0xFF29A2FF), modifier = Modifier.fillMaxSize()) {
                    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(50.dp)) {
                        FaceText(cubeState)
                        Box(
                            modifier = Modifier
                                .size(screenHeight * 0.6f),
                            contentAlignment = Alignment.Center
                        ) {

                            CameraPreview(
                                controller = controller,
                                modifier = Modifier
                                    .fillMaxSize(),
                            )

                            processedBitmapState.value?.let { bitmap ->
                                Image(
                                    bitmap.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }

                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                            val context = LocalContext.current
                            IconButton(R.drawable.camera, 70.dp, onClick = {
                                val colors = detectedColors.take(9)
                                val stringFace = fillFace(colors.toTypedArray())

                                startActivity(
                                    Intent(context, CheckSideActivity::class.java)
                                        .putExtra("cubeState", cubeState)
                                        .putExtra("cubeFace", stringFace)
                                )
                            })

                            IconButton(R.drawable.edit, 70.dp, onClick = {
                                startActivity(Intent(context, CheckCubeActivity::class.java))
                            })
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun FaceText(cubeState : Int){
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        var text = ""
        when(cubeState){
            0 -> text = "Up Face"
            1 -> text = "Right Face"
            2 -> text = "Front Face"
            3 -> text = "Down Face"
            4 -> text = "Left Face"
            5 -> text = "Back Face"
        }
        Text(
            text = text,
            style = TextStyle(
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(3f, 3f),
                    blurRadius = 4f
                ),
                background = Color.Transparent
            ),
            modifier = Modifier.padding(top = screenHeight * 0.1f)
        )
    }

    private fun processImageProxy(
        imageProxy: ImageProxy,
        processedBitmapState: MutableState<Bitmap?>
    ) {
        val rotationDegrees = imageProxy.imageInfo.rotationDegrees
        val bitmap = imageProxy.toBitmap()
        val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
        val correctedBitmap =
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

        val processedBitmap = processRubikCubeFace(correctedBitmap)

        processedBitmapState.value = processedBitmap

        imageProxy.close()
    }



    private fun hasRequiredPermissions(): Boolean {
        return CAMERA_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it.toString()
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    private fun processRubikCubeFace(bitmap: Bitmap): Bitmap {
        val colorRanges = mapOf(
            'O' to Pair(Scalar(8.0, 160.0, 150.0), Scalar(24.0, 255.0, 255.0)),  // Orange
            'R' to Pair(Scalar(0.0, 170.0, 150.0), Scalar(5.0, 255.0, 255.0)),  // Red
            'G' to Pair(Scalar(45.0, 200.0, 150.0), Scalar(80.0, 255.0, 255.0)), // Green
            'B' to Pair(Scalar(84.0, 150.0, 150.0), Scalar(130.0, 255.0, 255.0)), // Blue
            'Y' to Pair(Scalar(25.0, 180.0, 150.0), Scalar(44.0, 255.0, 255.0)), // Yellow
            'W' to Pair(Scalar(0.0, 0.0, 100.0), Scalar(180.0, 50.0, 255.0)),     // White
        )

        val mat = Mat()
        Utils.bitmapToMat(bitmap, mat)
        val hsvImage = Mat()
        Imgproc.cvtColor(mat, hsvImage, Imgproc.COLOR_RGB2HSV)

        val colorContours = mutableListOf<MatOfPoint>()

        for ((color, hsv) in colorRanges) {
            colorContours.addAll(findContours(hsvImage, hsv, color))
        }

        val sortedContours = mutableListOf<MatOfPoint>()
        for (contour in colorContours) {
            val area = Imgproc.contourArea(contour)
            if (area < 4000 || area > 8000) continue

            val boundingRect = Imgproc.boundingRect(contour)
            val aspectRatio = boundingRect.width.toDouble() / boundingRect.height
            if (aspectRatio > 1.3 || aspectRatio < 0.7) continue

            addToSortedContours(sortedContours, contour)
        }

        var i = 0
        detectedColors.clear()
        for (contour in sortedContours) {
            i++
            val rect = Imgproc.boundingRect(contour)
            val averageColor = getAverageColor(hsvImage, contour)
            val nearestColor = identifyColor(averageColor, colorRanges)

            detectedColors.add(nearestColor)

            Imgproc.rectangle(
                mat,
                Point(rect.x.toDouble(), rect.y.toDouble()),
                Point((rect.x + rect.width).toDouble(), (rect.y + rect.height).toDouble()),
                Scalar(36.0, 255.0, 12.0),
                5
            )
            Imgproc.putText(
                mat,
                "$i $nearestColor",
                Point(rect.x.toDouble() + 10, (rect.y + 50).toDouble()),
                Imgproc.FONT_HERSHEY_SIMPLEX,
                1.0,
                Scalar(255.0, 255.0, 255.0), 2
            )
        }
        val resultBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mat, resultBitmap)

        return resultBitmap
    }

    private fun findContours(
        hsvImage: Mat,
        hsvColor: Pair<Scalar, Scalar>,
        color: Char
    ): List<MatOfPoint> {
        val mask = Mat()
        Core.inRange(hsvImage, hsvColor.first, hsvColor.second, mask)

        if (color == 'R') {
            val redMask = Mat()
            Core.inRange(
                hsvImage,
                Scalar(150.0, 170.0, 100.0),
                Scalar(180.0, 255.0, 255.0),
                redMask
            )
            Core.add(mask, redMask, mask)
        }

        val contours = mutableListOf<MatOfPoint>()
        Imgproc.findContours(
            mask,
            contours,
            Mat(),
            Imgproc.RETR_EXTERNAL,
            Imgproc.CHAIN_APPROX_SIMPLE
        )
        return contours

    }

    private fun addToSortedContours(sortedContours: MutableList<MatOfPoint>, contour: MatOfPoint) {
        val rect = Imgproc.boundingRect(contour)
        val insertIndex = sortedContours.indexOfFirst { Imgproc.boundingRect(it).y > rect.y }
        if (insertIndex >= 0) {
            sortedContours.add(insertIndex, contour)
        } else {
            sortedContours.add(contour)
        }
    }

    private fun getAverageColor(image: Mat, contour: MatOfPoint): Scalar {
        val mask = Mat.zeros(image.size(), CvType.CV_8UC1)
        Imgproc.drawContours(mask, listOf(contour), -1, Scalar(255.0), Core.FILLED)

        val mean = Core.mean(image, mask)
        mask.release()
        return mean
    }

    private fun identifyColor(color: Scalar, colors: Map<Char, Pair<Scalar, Scalar>>): Char {
        var determinedColor = 'R'

        for ((key, value) in colors) {
            val isInHueRange =
                color.`val`[0] >= value.first.`val`[0] && color.`val`[0] <= value.second.`val`[0]
            val isInSaturationRange =
                color.`val`[1] >= value.first.`val`[1] && color.`val`[1] <= value.second.`val`[1]
            val isInValueRange =
                color.`val`[2] >= value.first.`val`[2] && color.`val`[2] <= value.second.`val`[2]

            if (isInHueRange && isInSaturationRange && isInValueRange) {
                determinedColor = key
                break
            }
        }
        return determinedColor
    }
}