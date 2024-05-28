package com.jer.storyapp.authentication.view.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jer.storyapp.R
import com.jer.storyapp.authentication.ViewModelFactory
import com.jer.storyapp.authentication.data.api.ListStoryItem
import com.jer.storyapp.authentication.view.maps.MapsActivity
import com.jer.storyapp.authentication.view.welcome.WelcomeActivity
import com.jer.storyapp.authentication.view.story.StoryAdapter
import com.jer.storyapp.authentication.view.upload.UploadActivity
import com.jer.storyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

//    private val viewModel by viewModels<MainViewModel> {
//        ViewModelFactory.getInstance(this)
//    }

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
//    private  var token: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
//            LoginViewModel::class.java)

        viewModel.getSession().observe(this) { user ->
            val getToken = user.token
            if (user.isLogin){
                setStory(getToken)
            }else{
                startActivity(Intent(this,WelcomeActivity::class.java))
                finish()
            }
//            viewModel.storyUser(user.token)
//            if (!user.isLogin) {
//                // Pengguna belum login, arahin ke aktivitas  yang sesuai
//                startActivity(Intent(this, WelcomeActivity::class.java))
//                finish()
//            }


        }

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(itemDecoration)





//        viewModel.storyItem.observe(this) {itemStory ->
//            setStory(itemStory)
//        }

        binding.fab.setOnClickListener {
            val intentSendFile = Intent(this, UploadActivity::class.java)
            startActivity(intentSendFile)
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.btnMaps -> {
                    val intent = Intent(this, MapsActivity:: class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }


        setupView()
        setupAction()
        playAnimation()

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.menu_item, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId){
//            R.id.btnMaps -> startActivity(Intent(this,MapsActivity::class.java))
//        }
//        return super.onOptionsItemSelected(item)
//
//    }


    private fun playAnimation(){
//        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
//            duration = 6000
//            repeatCount = ObjectAnimator.INFINITE
//            repeatMode = ObjectAnimator.REVERSE
//        }.st0rt()

//        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
//        val messageTextView = ObjectAnimator.ofFloat(binding.messageTextView, View.ALPHA, 1f).setDuration(500)
        val logoutButton = ObjectAnimator.ofFloat(binding.logoutButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            startDelay = 3000
            playSequentially(
//                nameTextView,
//                messageTextView,
                logoutButton)
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



    private fun setupAction() {
        binding.logoutButton.setOnClickListener {
            viewModel.logout()
        }
    }
    private fun setStory(token: String) {
        val adapter = StoryAdapter(this)

        binding.recyclerView.adapter = adapter

        viewModel.storyUser(token).observe(this,{
            adapter.submitData(lifecycle,it)
        })
//
    }
}