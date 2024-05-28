package com.jer.storyapp.authentication.view.upload

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.jer.storyapp.R
import com.jer.storyapp.authentication.ViewModelFactory
import com.jer.storyapp.authentication.getImageUri
import com.jer.storyapp.authentication.reduceFileImage
import com.jer.storyapp.authentication.uriToFile
import com.jer.storyapp.authentication.view.main.MainActivity
import com.jer.storyapp.databinding.ActivityUploadBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private val uploadViewModel by viewModels<UploadViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamera.setOnClickListener {
            startCamera()
        }

        binding.btnGalerry.setOnClickListener {
            startGallery()
        }

        binding.btnUpload.setOnClickListener {
            uploadPicture()
        }


    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showPicture()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }


    private fun startCamera(){
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) {isSuccess ->
        if (isSuccess) {
            showPicture()
        }
    }


    private fun showPicture() {
        currentImageUri?.let {
            binding.ivHolder.setImageURI(it)
        }
    }

    private fun uploadPicture() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this).reduceFileImage()
            val description = binding.tfInputDescription.text.toString().trim()

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            uploadViewModel.getSession().observe(this) { user ->
                uploadViewModel.addStory(user.token, multipartBody, requestBody)
            }

            uploadViewModel.fileUploadResponse.observe(this) {
                if (it.error) {
                    showToast(it.message)
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    showToast(it.message)
                    startActivity(intent)
                    finish()
                }
            }
        } ?: showToast(getString(R.string.warning_if_empty))
    }


    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}