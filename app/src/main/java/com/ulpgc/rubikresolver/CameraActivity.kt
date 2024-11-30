package com.ulpgc.rubikresolver

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ulpgc.rubikresolver.components.CameraPreview
import com.ulpgc.rubikresolver.ui.theme.RubikResolverTheme
import org.opencv.android.Utils
import org.opencv.core.Core
import org.opencv.core.CvType
import org.opencv.core.Mat
import org.opencv.core.MatOfPoint
import org.opencv.core.Point
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import kotlin.math.pow
import kotlin.math.sqrt

@ExperimentalMaterial3Api
class CameraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(this, CAMERA_PERMISSIONS, 0)
        }

        setContent {
            CameraScreen()
        }
    }

    @Composable
    private fun CameraScreen() {
        var mockImage by remember { mutableStateOf<Bitmap?>(null) }
        RubikResolverTheme {
            val scaffoldState = rememberBottomSheetScaffoldState()
            val controller = remember {
                LifecycleCameraController(applicationContext).apply {
                    setEnabledUseCases(
                        CameraController.IMAGE_CAPTURE
                    )
                }
            }


            BottomSheetScaffold(
                scaffoldState = scaffoldState,
                sheetPeekHeight = 0.dp,
                sheetContent = {}
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    CameraPreview(
                        controller = controller,
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 100.dp)
                            .height(100.dp)
                            .width(100.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(
                            onClick = {
                                takePhoto(
                                    controller = controller,
                                    onPhotoTaken = { bitmap ->
                                        onTakePhoto(bitmap)
                                        mockImage = bitmap
                                    },
                                )
                            },
                            modifier = Modifier.size(50.dp),
                            shape = CircleShape,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {

                        }
                    }
                    if (mockImage != null) {
                        Image(
                            bitmap = mockImage!!.asImageBitmap(),
                            contentDescription = "Mock image",
                            modifier = Modifier.size(400.dp)
                        )
                    }
                }
            }


        }
    }


    private fun hasRequiredPermissions(): Boolean {
        return CAMERA_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }


    private fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object : OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val rotationDegrees = image.imageInfo.rotationDegrees
                    val bitmap = image.toBitmap()
                    val matrix = Matrix()
                    matrix.postRotate(rotationDegrees.toFloat())
                    val correctedBitmap =
                        Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

                    onPhotoTaken(processRubikCubeFace(correctedBitmap))
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Error taking photo", exception)
                }

            }
        )
    }

    private fun onTakePhoto(bitmap: Bitmap) {
        val width = bitmap.width
        val height = bitmap.height
        val config = bitmap.config
        val byteCount = bitmap.byteCount
        val density = bitmap.density

        Log.d("BitmapProperties", "Width: $width, Height: $height")
        Log.d("BitmapProperties", "Config: $config, ByteCount: $byteCount")
        Log.d("BitmapProperties", "Density: $density")

    }

    private fun processRubikCubeFace(bitmap: Bitmap): Bitmap {
        val colorRanges = mapOf(
            'R' to Pair(Scalar(0.0, 120.0, 70.0), Scalar(5.0, 255.0, 255.0)),  // Red
            'G' to Pair(Scalar(35.0, 100.0, 70.0), Scalar(65.0, 255.0, 255.0)), // Green
            'B' to Pair(Scalar(90.0, 150.0, 0.0), Scalar(130.0, 255.0, 255.0)), // Blue
            'Y' to Pair(Scalar(25.0, 100.0, 100.0), Scalar(35.0, 255.0, 255.0)), // Yellow
            'O' to Pair(Scalar(7.0, 100.0, 20.0), Scalar(14.0, 255.0, 255.0)),  // Orange
            'W' to Pair(Scalar(0.0, 0.0, 180.0), Scalar(180.0, 40.0, 255.0))     // White
        )

        val mat = Mat()
        Utils.bitmapToMat(bitmap, mat)
        val hsvImage = Mat()
        Imgproc.cvtColor(mat, hsvImage, Imgproc.COLOR_RGB2HSV)

        val colorContours = mutableListOf<MatOfPoint>()

        for ((_, hsv) in colorRanges) {
            colorContours.addAll(findContours(hsvImage, hsv))
        }

        println("Contours found: ${colorContours.size}")

        val sortedContours = mutableListOf<MatOfPoint>()
        for (contour in colorContours) {
            val area = Imgproc.contourArea(contour)
            if (area < 50000 || area > 600000) continue

            print("made it here")
            val boundingRect = Imgproc.boundingRect(contour)
            val aspectRatio = boundingRect.width.toDouble() / boundingRect.height
            if (aspectRatio > 1.2 || aspectRatio < 0.8) continue

            addToSortedContours(sortedContours, contour)
        }

        println("Sorted contours: ${sortedContours.size}")
        println("Contours: $sortedContours")

        var i = 0
        for (contour in sortedContours) {
            i++
            val rect = Imgproc.boundingRect(contour)
            val averageColor = getAverageColor(hsvImage, contour)
            val nearestColor = getNearestColor(averageColor, colorRanges)

            Imgproc.rectangle(
                mat,
                Point(rect.x.toDouble(), rect.y.toDouble()),
                Point((rect.x + rect.width).toDouble(), (rect.y + rect.height).toDouble()),
                Scalar(36.0, 255.0, 12.0),
                5
            )
            Imgproc.putText(
                mat,
                "#$i $nearestColor",
                Point(rect.x.toDouble(), (rect.y - 5).toDouble()),
                Imgproc.FONT_HERSHEY_SIMPLEX,
                2.7,
                Scalar(255.0, 255.0, 255.0), 2
            )
        }
        val resultBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(mat, resultBitmap)

        return resultBitmap
    }

    private fun findContours(hsvImage: Mat, hsvColor: Pair<Scalar, Scalar>): List<MatOfPoint> {
        val mask = Mat()
        Core.inRange(hsvImage, hsvColor.first, hsvColor.second, mask)

        val kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, Size(5.0, 5.0))
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_CLOSE, kernel)
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, kernel)

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
        return mean
    }

    private fun getNearestColor(color: Scalar, colors: Map<Char, Pair<Scalar, Scalar>>): Char {
        var nearestColor = ' '
        var minDistance = Double.MAX_VALUE
        if (color.`val`[0] > 170) {
            color.`val`[0] = 180 - color.`val`[0]
        }
        for ((key, value) in colors) {
            val referenceColor = value.second

            val distance = sqrt(
                (color.`val`[0] - referenceColor.`val`[0]).pow(2.0) +
                        (color.`val`[1] - referenceColor.`val`[1]).pow(2.0) +
                        (color.`val`[2] - referenceColor.`val`[2]).pow(2.0)
            )

            if (distance < minDistance) {
                minDistance = distance
                nearestColor = key
            }
        }
        println("Color: $color, Nearest: $nearestColor")
        return nearestColor
    }
}