package com.example.cameraxdemo.base

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Matrix
import android.graphics.Rect
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.CameraX
import androidx.camera.core.Preview
import androidx.camera.core.PreviewConfig
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraxdemo.*
import kotlinx.android.synthetic.main.fragment_camera.*
import org.jetbrains.anko.dip
private const val REQUEST_CODE_PERMISSIONS = 10
class CameraFragment:BaseFragment<ViewModel>() {
    //请求的权限
    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    override fun setLayoutRes(): Int = R.layout.fragment_camera

    override fun initView() {


        //请求摄像头权限
        if (allPermissionsGranted()) {
            sv.post { startCamera() }
        } else {
            ActivityCompat.requestPermissions(
                activity as Activity, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Every time the provided texture view changes, recompute layout
        sv.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
            updateTransform()
        }
        avater.roundRectDrawable(R.drawable.avater)
        avaters.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                if (parent?.getChildAdapterPosition(view) != 0) {
                    outRect?.left = context!!.dip(-5)
                }
            }
        })
        AvaterAdapter().bindToRecyclerView(avaters)

        CommentAdapter().bindToRecyclerView(content)
    }

    override fun initData() {
    }

    override fun initEvent() {
    }

    override fun initObserve() {
    }


    /**
     * Process result from permission request dialog box, has the request
     * been granted? If yes, start Camera. Otherwise display a toast
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                sv.post { startCamera() }
            } else {
                Toast.makeText(
                    context,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
//                finish()
            }
        }
    }

    /**
     * 检查所有权限是否被授权
     */
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context!!, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {

        // Create configuration object for the sv use case
        val previewConfig = PreviewConfig.Builder().apply {
            setLensFacing(CameraX.LensFacing.FRONT)
//            setTargetResolution(Size(640, 480))
        }.build()


        // Build the sv use case
        val preview = Preview(previewConfig)

        // Every time the sv is updated, recompute layout
        preview.setOnPreviewOutputUpdateListener {

            // To update the SurfaceTexture, we have to remove it and re-add it
            val parent = sv.parent as ViewGroup
            parent.removeView(sv)
            parent.addView(sv, 0)

            sv.surfaceTexture = it.surfaceTexture
            updateTransform()
        }

        // Bind use cases to lifecycle
        // If Android Studio complains about "this" being not a LifecycleOwner
        // try rebuilding the project or updating the appcompat dependency to
        // version 1.1.0 or higher.
        CameraX.bindToLifecycle(this, preview)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        // Compute the center of the view finder
        val centerX = sv.width / 2f
        val centerY = sv.height / 2f

        // Correct preview output to account for display rotation
        val rotationDegrees = when (sv.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }
        matrix.postRotate(-rotationDegrees.toFloat(), centerX, centerY)

        // Finally, apply transformations to our TextureView
        sv.setTransform(matrix)
    }
}