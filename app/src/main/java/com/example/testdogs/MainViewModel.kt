package com.example.testdogs

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val dogImage = MutableLiveData<DogImage>()
    private var compositeDisposable = CompositeDisposable()
    private val isLoading = MutableLiveData<Boolean>()
    private val isNetworkError = MutableLiveData<Boolean>()

    fun getDogImage(): LiveData<DogImage> = dogImage

    fun getIsLoading(): LiveData<Boolean> = isLoading

    fun getIsNetworkError(): LiveData<Boolean> = isNetworkError

    internal fun loadImage() {
         val disposable = loadImageRx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isLoading.value = true
                            isNetworkError.value = false}
            .doOnError { isNetworkError.value = true }
            .doAfterTerminate { isLoading.value = false }
            .subscribe({
                dogImage.value = it
            }, {
                Log.d(Constants.TAG, "Error: ${it.message}")
            })
        compositeDisposable.add(disposable)
    }

    private fun loadImageRx(): Single<DogImage> {
        return ApiFactory.getApiService().loadImage()
//        Single.fromCallable(Callable {
//                val url = Constants.DOGE_URL
//                // установка соединения и приведение к HttpURLConnection
//                val httpURLConnection =
//                    URL(url).openConnection() as HttpURLConnection
//                // считывание всего текста
//                val result = httpURLConnection.inputStream.bufferedReader().readText()
//
//                val json = JSONObject(result)
//                val message = json.getString(Constants.KEY_MESSAGE)
//                val status = json.getString(Constants.KEY_STATUS)
//                return@Callable DogImage(message, status)
//        })
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}