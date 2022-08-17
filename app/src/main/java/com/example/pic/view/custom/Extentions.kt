package com.example.pic.view.custom

import android.content.res.Resources
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.pic.data.remote.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

val Int.px: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiToBeCalled: suspend () -> Response<T>): LiveData<NetworkResult<T>> {


    return liveData(dispatcher) {

        try {
            emit(NetworkResult.Loading())

            val response: Response<T> = apiToBeCalled()
            if (response.isSuccessful) {
                emit(NetworkResult.Success(data = response.body()))
            } else {
                emit(
                    NetworkResult.Error(message = response.errorBody().toString())
                )

            }
        }
        catch (e: HttpException) {
            emit(NetworkResult.Error(message = e.message))
        } catch (e: IOException) {
            emit(NetworkResult.Error(message = "Check Your connection"))
        } catch (e: Exception) {
            emit(NetworkResult.Error(message = "Something went wrong"))
        }
    }

}








