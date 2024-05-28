package com.jer.storyapp.authentication.view.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jer.storyapp.R
import com.jer.storyapp.authentication.data.api.ListStoryItem
import com.jer.storyapp.authentication.view.login.LoginViewModel
import com.jer.storyapp.databinding.ActivityStoryBinding

class StoryActivity : AppCompatActivity() {

//    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)
//
//        binding = ActivityStoryBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val layoutManager = LinearLayoutManager(this)
//        binding.rvStory.layoutManager = layoutManager
//        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
//        binding.rvStory.addItemDecoration(itemDecoration)
//
//        val token = intent.getStringExtra("token") ?: ""
//
//
//        val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(LoginViewModel::class.java)
//
//        viewModel.storyResult.observe(this) {itemStory ->
//            setStory(itemStory)
//        }
//
//        viewModel.storyUser(token)



    }

//    private fun setStory(itemStory: List<ListStoryItem>) {
//        val adapter = StoryAdapter(this)
//        adapter.submitList(itemStory)
//        binding.rvStory.adapter = adapter
//    }

}