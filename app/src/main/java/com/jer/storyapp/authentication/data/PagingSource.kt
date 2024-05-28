package com.jer.storyapp.authentication.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jer.storyapp.authentication.data.api.ApiService
import com.jer.storyapp.authentication.data.api.ListStoryItem

class PagingSource( private val apiService: ApiService, val token: String) :
    PagingSource<Int, ListStoryItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPostition ->
            val anchorPage = state.closestPageToPosition(anchorPostition)
            anchorPage?.prevKey?.plus(1)?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {

        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val response = apiService.getStories(token, position, params.loadSize)
            LoadResult.Page(
                data = response.listStory,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position -1,
                nextKey = if (response.listStory.isEmpty()) null else position +1
            )
        }catch (e: Exception){
            Log.e("TAG", "load: ${e.localizedMessage}")
            return LoadResult.Error(e)
        }
    }

    companion object {


        const val INITIAL_PAGE_INDEX = 1
    }
}