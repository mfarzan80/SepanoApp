package com.sepano.app.data

sealed class ApiResult<out T>(val status: ApiStatus, val data: T?, var exception: Exception?) {

    data class Success<out R>(val _data: R?, val _exception: Exception? = null) : ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        exception = null
    )

    data class Error(val _exception: Exception) : ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        exception = _exception
    )

    class Loading() : ApiResult<Nothing>(
        status = ApiStatus.LOADING,
        data = null,
        exception = null
    )


    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$exception]"
            is Loading -> "Loading"
        }
    }


}
