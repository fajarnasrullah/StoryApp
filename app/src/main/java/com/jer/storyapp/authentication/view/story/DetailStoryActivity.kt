package com.jer.storyapp.authentication.view.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.jer.storyapp.R
import com.jer.storyapp.authentication.ViewModelFactory
import com.jer.storyapp.authentication.data.api.Story
import com.jer.storyapp.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    private val viewModel by viewModels<DetailStoryViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra(ID).toString()


        viewModel.getDetailResult.observe(this) {
            setDetailStory(it)
        }

        viewModel.getSession().observe(this) {
            viewModel.getDetail(it.token, id)
        }

    }

    private fun setDetailStory(story: Story) {
        binding.apply {
            Glide
                .with(root.context)
                .load(story.photoUrl)
                .into(imageDetailStory)
            titleDetailStory.text = story.name
            descDetailStory.text = story.description

        }

    }

    companion object {
        const val ID = "ID"
    }
}