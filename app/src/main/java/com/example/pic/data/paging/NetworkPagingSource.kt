package com.example.pic.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NetworkPagingSource<T : Any>(private val backend: suspend (Int) -> Response<List<T>>) :
    PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val nextPageNumber = params.key ?: 1


        try {
            val response = backend.invoke(nextPageNumber)

            return if (response.isSuccessful) {
                val body = response.body()!!

                LoadResult.Page(
                    data = body,
                    prevKey = null, // Only paging forward.
                    nextKey = nextPageNumber.plus(1)

                )
            } else {
                val errorBody = response.errorBody()
                LoadResult.Error(
                    RuntimeException(errorBody.toString())
                )
            }
        }catch (e: HttpException){
            return LoadResult.Error(e)
        }catch (e: IOException){
            return LoadResult.Error(e)
        }catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

}