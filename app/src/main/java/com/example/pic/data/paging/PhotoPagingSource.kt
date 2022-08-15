package com.example.pic.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pic.model.res.Feed
import com.example.pic.data.remote.UnsplashApi
import com.example.pic.util.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import java.io.IOException
import java.lang.RuntimeException



class PhotoPagingSource(var api: UnsplashApi) : PagingSource<Int, Feed>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Feed> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.getAllImages(position, params.loadSize)
            val body = response.body()
            if (response.isSuccessful){
                LoadResult.Page(
                    data = body ?: listOf(),
                    prevKey = null,
                    nextKey = if (body.isNullOrEmpty()) null else position + 1
                )
            }

            else{
                return LoadResult.Error(RuntimeException(response.message()))
            }
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, Feed>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}