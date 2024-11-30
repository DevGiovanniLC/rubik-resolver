package com.ulpgc.rubikresolver

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageFormat
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.YuvImage
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
import androidx.compose.material3.Text
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
import org.opencv.core.Mat
import java.io.ByteArrayOutputStream

@ExperimentalMaterial3Api
class CameraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if(!hasRequiredPermissions()){
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
            ) { padding ->
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
                it.toString()
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
          object : OnImageCapturedCallback(){
              override fun onCaptureSuccess(image: ImageProxy) {
                  super.onCaptureSuccess(image)

                  val rotationDegrees = image.imageInfo.rotationDegrees
                  val bitmap = image.toBitmap()
                  val matrix = Matrix()
                  matrix.postRotate(rotationDegrees.toFloat())
                  val correctedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)

                  onPhotoTaken(correctedBitmap)
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

        // Process image with OpenCV
        processImage(bitmap)
    }

    private fun processImage(bitmap: Bitmap) {
        // Process image with OpenCV
        val mat = Mat()
        Utils.bitmapToMat(bitmap, mat)

        // Detect tiles
        detectTiles(mat)

        Utils.matToBitmap(mat, bitmap)

        // Display processed image

    }

    private fun detectTiles(mat: Mat) {
        // Detect tiles

    }

    fun ImageProxy.toBitmap(): Bitmap {
        val yBuffer = planes[0].buffer
        val uBuffer = planes[1].buffer
        val vBuffer = planes[2].buffer

        val ySize = yBuffer.remaining()
        val uSize = uBuffer.remaining()
        val vSize = vBuffer.remaining()

        val nv21 = ByteArray(ySize + uSize + vSize)

        // Copy Y, U, and V buffers into NV21 array
        yBuffer.get(nv21, 0, ySize)
        vBuffer.get(nv21, ySize, vSize)
        uBuffer.get(nv21, ySize + vSize, uSize)

        // Convert NV21 format to Bitmap
        val yuvImage = YuvImage(nv21, ImageFormat.NV21, width, height, null)
        val out = ByteArrayOutputStream()
        yuvImage.compressToJpeg(Rect(0, 0, width, height), 100, out)
        val imageBytes = out.toByteArray()
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }
}