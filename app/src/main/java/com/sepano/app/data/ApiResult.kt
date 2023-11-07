package com.sepano.app.data

sealed class ApiResult<out T>(val status: ApiStatus, val data: T?, var exception: Exception?) {

    data class Success<out R>(val _data: R?, val _exception: Exception? = null) : ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        exception = null
    )

    data class Error(val _message: Exception) : ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        exception = _message
    )

    class Loading() : ApiResult<Nothing>(
        status = ApiStatus.LOADING,
        data = null,
        exception = null
    )

    class Empty() : ApiResult<Nothing>(
        status = ApiStatus.EMPTY,
        data = null,
        exception = null
    )

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$exception]"
            is Loading -> "Loading"
            is Empty -> "Empty"
        }
    }


}
