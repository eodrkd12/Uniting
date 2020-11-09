package com.uniting.android.Cafeteria

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_write_review.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class WriteReviewActivity : PSAppCompatActivity() {

    private val PICK_FROM_ALBUM = 1
    private val PERMISSIONS_REQUEST_CODE = 100
    var imagePath : String? = null
    var imageCaptureUri: Uri? = null
    var imageCheck = false

    private val requiredPermission = arrayOf(
        android.Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_review)

        val cafeNo = intent.getIntExtra("cafe_no", 0)
        rating_insert_review.rating = intent.getFloatExtra("rating", 0.0f)

        text_add_photo.setOnClickListener {
            checkPermissions()
        }

        text_write_review.setOnClickListener {
            when(imageCheck) {
                false -> {
                    if(edit_review_content.length() == 0) {
                        Toast.makeText(this, "글 내용을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Retrofit.insertReview(cafeNo, edit_review_content.text.toString(), rating_insert_review.rating.toInt(), "noimage", "") {
                            if(it.result == "success") {
                                Toast.makeText(this, "리뷰 작성을 완료 했습니다.", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            else {
                                Toast.makeText(this, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                true -> {
                    if(edit_review_content.length() == 0) {
                        Toast.makeText(this, "글 내용을 확인해주세요.", Toast.LENGTH_SHORT).show()
                    }
                    else {
                        Retrofit.insertReview(cafeNo, edit_review_content.text.toString(), rating_insert_review.rating.toInt(), "image", imagePath!!) {
                            if(it.result == "success") {
                                Toast.makeText(this, "리뷰 작성을 완료 했습니다.", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            else {
                                Toast.makeText(this, "서버와의 통신오류", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun checkPermissions(){
        val readStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if(readStoragePermission == PackageManager.PERMISSION_GRANTED) {
            photoFromGallery()
        }
        else {
            ActivityCompat.requestPermissions(this, requiredPermission, PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == requiredPermission.size) {
            var checkResult = true

            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    checkResult = false
                    break
                }
            }

            if(checkResult) {
                photoFromGallery()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }

        when (requestCode) {
            PICK_FROM_ALBUM -> {
                imageCaptureUri = data!!.data
                try {
                    bindingPicture(imageCaptureUri!!)
                    imageCheck = true
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun photoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    private fun makeCacheFile(bitmap : Bitmap) {
        val storage : File = this.cacheDir
        val fileName = "test.jpg"

        var tempFile = File(storage, fileName)

        tempFile.createNewFile()
        tempFile.deleteOnExit()

        val out = FileOutputStream(tempFile)

        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out)
        imagePath = tempFile.path
    }

    private fun getRealPathFromURI(contentUri: Uri): String? {
        var columnIndex = 0
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(contentUri, proj, null, null, null)
        if (cursor!!.moveToFirst()) {
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        return cursor.getString(columnIndex)
    }

    private fun bindingPicture(uri : Uri) {
        val imagePath: String = getRealPathFromURI(uri)!!
        var exif: ExifInterface? = null
        try {
            exif = ExifInterface(imagePath)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val exifOrientation = exif!!.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        val exifDegree = exifOrientationToDegrees(exifOrientation)

        val resizedBitmap = resize(this, uri, 500)

        image_write_review.visibility = View.VISIBLE
        image_write_review.setImageBitmap(rotate(resizedBitmap!!,exifDegree.toFloat()))

        makeCacheFile(rotate(resizedBitmap!!, exifDegree.toFloat())!!)
    }

    private fun rotate(src: Bitmap, degree: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degree)
        return Bitmap.createBitmap(src, 0, 0, src.width, src.height, matrix, true)
    }

    private fun exifOrientationToDegrees(exifOrientation: Int): Int {

        when(exifOrientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> {
                return 90
            }
            ExifInterface.ORIENTATION_ROTATE_180 -> {
                return 180
            }
            ExifInterface.ORIENTATION_ROTATE_270 -> {
                return 270
            }
        }
        return 0
    }

    private fun resize(context: Context, uri: Uri, resize: Int): Bitmap? {
        var resizeBitmap: Bitmap? = null
        val options = BitmapFactory.Options()

        try {
            BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, options) // 1번
            var width = options.outWidth
            var height = options.outHeight
            var sampleSize = 1

            while (true) { //2번
                if (width / 2 < resize || height / 2 < resize) break
                width /= 2
                height /= 2
                sampleSize *= 2
            }

            options.inSampleSize = sampleSize
            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri), null, options) //3번
            resizeBitmap = bitmap

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return resizeBitmap
    }
}