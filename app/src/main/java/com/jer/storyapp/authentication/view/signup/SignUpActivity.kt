package com.jer.storyapp.authentication.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.jer.storyapp.authentication.ViewModelFactory
import com.jer.storyapp.authentication.view.login.LoginViewModel
import com.jer.storyapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var signupEditText: SignupEditText
//    private lateinit var viewModel: RegistViewModel
    private val viewModel by viewModels<SignupViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        signupEditText = binding.passwordEditText

//        val userRepository = UserRepository(UserPreference.getInstance(application.dataStore)) // Inisialisasi UserPreference sesuai kebutuhan Anda
//        viewModel = ViewModelProvider(this, ViewModelFactory(userRepository)).get(RegistViewModel::class.java)
//        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(RegistViewModel::class.java)
//        viewModel = ViewModelProvider(this).get(RegistViewModel::class.java)

//        viewModel = ViewModelProvider(this, YourViewModelFactory(userRepository)).get(YourViewModel::class.java)
//        viewModel = ViewModelProvider(this).get(RegistViewModel::class.java)

        viewModel.registerResult.observe(this) {
            val email = binding.emailEditText.text.toString()
            if (it.error){
                AlertDialog.Builder(this).apply {
                    setTitle("Peringatan")
                    setMessage(it.message)
                    setPositiveButton("OK") {_,_ ->

                    }
                    create()
                    show()
                }
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Akun dengan email: $email sudah jadi bre. Langsung login ae biar bisa belajar coding.")
                    setPositiveButton("Lanjut") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }

            }
        }
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val pw = binding.passwordEditText.text.toString()
            viewModel.registerUser(name, email, pw)
        }





        setupView()
//        setupAction()
        playAnimation()
//        setError()
        signupEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            var error : String? = null
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    setError("Password tidak boleh kurang dari 8 karakter", null)
                } else {
                    error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })


            // Handle registerResponse di sini
            // Misalnya, tampilkan pesan sukses atau kesalahan kepada pengguna
//            setupAction()
//            binding.signupButton.setOnClickListener {
//                val name = binding.nameEditText.text.toString()
//                val email = binding.emailEditText.text.toString()
//                viewModel.registerUser(name, email, "sample_password")
//            }

    }


    private fun setError(s: String, nothing: Nothing?) {}

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val titleTextView = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val signupButton = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)



        AnimatorSet().apply {
//            startDelay = 100
            startDelay = 3000
            playSequentially(
                titleTextView,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signupButton)
            start()

        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

//    private fun setupAction() {
//
//    }







}