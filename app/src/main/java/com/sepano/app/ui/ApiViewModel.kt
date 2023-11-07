package com.sepano.app.ui

import androidx.lifecycle.ViewModel
import com.sepano.app.data.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow

open class ApiViewModel<T> : ViewModel() {
    val state: MutableStateFlow<ApiResult<T>> = MutableStateFlow(ApiResult.Success(null))


    fun startLoading() {

        state.value = ApiResult.Loading()
    }


    fun error(exception: Exception) {

        state.value = ApiResult.Error(exception)
    }

    fun success(data: T) {

        state.value = ApiResult.Success(data)
    }


}