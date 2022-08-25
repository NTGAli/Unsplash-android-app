package com.example.pic.view.custom

import android.app.Dialog
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.Window
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.pic.R
import com.example.pic.data.remote.NetworkResult
import com.example.pic.util.loadImage
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

fun View.imgPreview(imgLink: String?) {
    val dialog = Dialog(this.context)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(R.layout.dialog_image_preview)
    val imgPreview: ImageView = dialog.findViewById(R.id.img_preview_feed)
    loadImage(imgPreview, imgLink!!)
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.setCancelable(true)
    dialog.show()
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








