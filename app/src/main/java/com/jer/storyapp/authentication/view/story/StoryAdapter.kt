package com.jer.storyapp.authentication.view.story

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.jer.storyapp.authentication.data.api.ListStoryItem
import com.jer.storyapp.databinding.ItemListStoryBinding

class StoryAdapter(private val context: Context): PagingDataAdapter<ListStoryItem, StoryAdapter.ViewHolder>(DIFF_CALLBACK) {


    class ViewHolder(private val binding: ItemListStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListStoryItem) {
            binding.titleList.text = item.name
            binding.descList.text = item.description

            Glide.with(itemView.context)
                .load(item.photoUrl)
                .into(binding.imgList)

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailStoryActivity::class.java)
                if (item != null) {
                    intent.putExtra("ID", item.id)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }

//        holder.itemView.setOnClickListener{
//            val intent = Intent(context, DetailStoryActivity::class.java)
//            if (item != null) {
//                intent.putExtra("ID", item.id)
//            }
//            context.startActivity(intent)
//        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem == newItem
            }
        }
    }


}