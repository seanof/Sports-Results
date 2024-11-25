package com.seanof.sportsresults.data.remote

sealed class ApiResult<T>(val data: T? = null, val error: String? = null) {
    class Success<T>(results: T):ApiResult<T>(data = results)
    class Error<T>(error: String):ApiResult<T>(error = error)
    class Loading<T>:ApiResult<T>()
    class Idle<T>:ApiResult<T>()
}
