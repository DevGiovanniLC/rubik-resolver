package com.ulpgc.rubikresolver

import android.Manifest
import android.content.Intent
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ulpgc.rubikresolver.components.CameraPreview
import com.ulpgc.rubikresolver.components.IconButton
import com.ulpgc.rubikresolver.services.faceToString
import com.ulpgc.rubikresolver.ui.theme.RubikResolverTheme

@ExperimentalMaterial3Api
class CameraActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        var cubeFace = Array(3) { Array(3) { mutableStateOf('r') }}
        var cubeState = intent.getIntExtra("cubeState", 0)

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
                ) { padding ->
                    Surface(color = Color(0xFF29A2FF)) {

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            FaceText(cubeState)
                            Box(
                                modifier = Modifier
                                    .size(500.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CameraPreview(
                                    controller = controller,
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                            }

                            val context = LocalContext.current

                            IconButton(R.drawable.camera, imageSize = 90.dp, onClick = {
                                takePhoto(
                                    controller = controller,
                                    onPhotoTaken = { bitmap ->
                                        onTakePhoto(bitmap)
                                    },
                                )

                                startActivity(
                                    Intent(context, CheckSideActivity::class.java)
                                        .putExtra("cubeState", cubeState)
                                        .putExtra("cubeFace", faceToString(cubeFace))
                                )
                            }, modifier = Modifier.padding(20.dp))
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

    @Composable
    private fun FaceText(cubeState : Int){
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
            )
        )
    }
}