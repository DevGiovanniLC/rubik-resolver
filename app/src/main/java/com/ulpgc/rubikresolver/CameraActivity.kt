package com.ulpgc.rubikresolver

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ulpgc.rubikresolver.components.CameraPreview
import com.ulpgc.rubikresolver.ui.theme.RubikResolverTheme

@ExperimentalMaterial3Api
class CameraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if(!hasRequiredPermissions()){
            ActivityCompat.requestPermissions(this, CAMERA_PERMISSIONS, 0)
        }

        setContent {
            RubikResolverTheme {
                val scaffoldState = rememberBottomSheetScaffoldState()
                val controller = remember{
                    LifecycleCameraController(applicationContext).apply{
                        setEnabledUseCases(
                            CameraController.IMAGE_CAPTURE
                        )
                    }
                }


                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = 0.dp,
                    sheetContent = {}
                ){
                    padding ->
                    Box(modifier = Modifier
                        .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ){

                        CameraPreview(controller = controller,
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
                                        },
                                    )
                                },
                                modifier = Modifier.size(50.dp),
                                shape = CircleShape,
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                            ) {

                            }
                        }
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
                  onPhotoTaken(image.toBitmap())
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

}