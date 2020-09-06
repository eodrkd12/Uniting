package com.uniting.android.Cafeteria

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.uniting.android.Class.PSAppCompatActivity
import com.uniting.android.Class.UserInfo
import com.uniting.android.R
import com.uniting.android.Singleton.Retrofit
import kotlinx.android.synthetic.main.activity_write_review.*
import java.io.FileNotFoundException
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

        val cafeteriaName = intent.getStringExtra("cafeteriaName")
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
                        Retrofit.insertReview(cafeteriaName!!, edit_review_content.text.toString(), rating_insert_review.rating.toInt(), "noimage", "") {
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
                        Retrofit.insertReview(cafeteriaName!!, edit_review_content.text.toString(), rating_insert_review.rating.toInt(), "image", imagePath!!) {
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
                imagePath = getPath(imageCaptureUri!!)

                try {
                    val imageBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageCaptureUri)
                    image_write_review.visibility = View.VISIBLE
                    image_write_review.setImageBitmap(imageBitmap)
                    imageCheck = true
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }

        }
    }

    fun photoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.setType("image/*")
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    fun getPath(uri: Uri) : String {
        val projection =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = managedQuery(uri, projection, null, null, null)
        startManagingCursor(cursor)
        val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        return cursor.getString(columnIndex)
    }
}